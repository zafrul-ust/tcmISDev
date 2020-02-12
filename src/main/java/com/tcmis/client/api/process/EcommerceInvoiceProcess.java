package com.tcmis.client.api.process;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.icu.text.SimpleDateFormat;
import com.tcmis.client.api.beans.InvoicePrintRollinsViewBean;
import com.tcmis.client.api.beans.InvoicePrtRollinsLineViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

public class EcommerceInvoiceProcess extends BaseProcess {
	private GenericSqlFactory factory = null;
	private SimpleDateFormat dateFormat;

	public EcommerceInvoiceProcess(String client) {
		super(client);
		factory = new GenericSqlFactory(new DbManager(getClient()));
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX");
	}

	public void submitInvoices(JSONArray invoices) throws Exception {
		// sanitize and delimit invoice strings
		StringBuilder whereClause = new StringBuilder();

		for(int i=0; i<invoices.length(); i++) {
			if(i != 0)
				whereClause.append(",");

			whereClause.append(SqlHandler.delimitString(invoices.getString(i)));
		}

		String insertQuery = "INSERT INTO invoice_submission (invoice, po_number, invoice_amount, date_sent) \r\n"
				+ "     SELECT invoice, po_number, invoice_amount, sysdate \r\n"
				+ "	   FROM invoice_print_rollins_view \r\n"
				+ "      WHERE invoice_id in (" + whereClause.toString() + ")";

		factory.deleteInsertUpdate(insertQuery.toString());
	}

	public Collection getUnsentInvoices() throws Exception {
		Collection<InvoicePrintRollinsViewBean> invoices;
		Collection<InvoicePrtRollinsLineViewBean> invoiceLines;

		SearchCriteria criteria = new SearchCriteria();
		SortCriteria sortCriteria = new SortCriteria();

		// retrieve invoices
		factory.setBeanObject(new InvoicePrintRollinsViewBean());

		invoices = factory.select(new SearchCriteria(), new SortCriteria(), "invoice_print_rollins_view");

		// retrieve invoice lines for each invoice
		factory.setBeanObject(new InvoicePrtRollinsLineViewBean());

		for(InvoicePrintRollinsViewBean invoice : invoices) {
			criteria = new SearchCriteria();
			criteria.addCriterion("invoiceId", SearchCriterion.EQUALS, invoice.getInvoiceId());

			invoiceLines = factory.select(criteria, sortCriteria, "invoice_prt_rollins_line_view");

			invoice.setInvoiceLines(invoiceLines);
		}

		return invoices;
	}

	private JSONObject getHeaderJSON() throws Exception {
		JSONObject header = new JSONObject();
		JSONObject from = new JSONObject();
		JSONObject to = new JSONObject();
		JSONObject sender = new JSONObject();
		JSONObject credential;

		// From
		credential = new JSONObject();
		credential.put("Identity", "");
		credential.put("domain", "");
		from.put("Credential",credential);

		header.put("From", from);

		// To
		to.put("Credential", credential);

		header.put("To",to);

		// Sender
		credential = new JSONObject();
		credential.put("Identity", "");
		credential.put("domain", "");
		credential.put("SharedSecret", "");
		sender.put("Credential", credential);
		sender.put("UserAgent","");

		header.put("Sender",sender);

		return header;
	}

	public JSONArray getJSON(Collection<InvoicePrintRollinsViewBean> invoices) throws Exception {
		JSONArray results = new JSONArray();
		JSONObject resultsElement;

		JSONObject cXML;
		JSONObject header = getHeaderJSON();
		JSONObject request;

		JSONObject invoiceDetailRequest;
		JSONObject invoiceDetailRequestHeader;

		JSONArray invoicePartner;
		JSONObject contact;
		JSONObject name;
		JSONObject postalAddress;
		JSONObject country;

		JSONObject invoiceDetailShipping;
		JSONArray shippingContacts;
		JSONObject invoiceDetailPaymentTerm;

		JSONObject invoiceDetailOrder;

		JSONObject invoiceDetailOrderInfo;
		JSONObject orderReference;
		JSONObject documentReference;

		JSONArray invoiceDetailItem;
		JSONObject item;
		JSONObject unitPrice;
		JSONObject invoiceDetailItemReference;
		JSONObject itemId;
		JSONObject subtotalAmount;

		JSONObject invoiceDetailSummary;
		JSONObject tax;
		JSONObject taxDetail;
		JSONObject taxAmount;
		JSONObject specialHandlingAmount;
		JSONObject shippingAmount;
		JSONObject grossAmount;
		JSONObject netAmount;
		JSONObject dueAmount;

		JSONObject money;
		JSONObject description;

		String[] payloadIdSplit;
		String[] countrySplit;

		Random random = new Random();
		for(InvoicePrintRollinsViewBean invoice : invoices) {
			// an invoice may not have lines if it hasn't been fully confirmed
			if(invoice.getInvoiceLines() != null && !invoice.getInvoiceLines().isEmpty()) {
				resultsElement = new JSONObject();
				cXML = new JSONObject();

				InvoicePrtRollinsLineViewBean firstLine = invoice.getInvoiceLines().iterator().next();

				payloadIdSplit = StringHandler.emptyIfNull(firstLine.getPayloadId()).split("|");

				cXML.put("payloadID", dateFormat.format(new Date())+random.nextInt()+"@www.tcmis.com");
				cXML.put("timestamp", dateFormat.format(new Date()));
				cXML.put("version", "");
				cXML.put("lang", "en");
				cXML.put("Header", header);

				request = new JSONObject();
				request.put("deploymentMode","");

				invoiceDetailRequest = new JSONObject();

				// InvoiceDetailRequestHeader
				invoiceDetailRequestHeader = new JSONObject();

				invoiceDetailRequestHeader.put("invoiceDate", invoice.getInvoiceDate() != null ? dateFormat.format(invoice.getInvoiceDate()) : "");
				invoiceDetailRequestHeader.put("invoiceID", invoice.getInvoiceId());

				invoiceDetailRequestHeader.put("operation", "");
				invoiceDetailRequestHeader.put("purpose", "");
				invoiceDetailRequestHeader.put("InvoiceDetailHeaderIndicator", "");
				invoiceDetailRequestHeader.put("InvoiceDetailLineIndicator", "");

				// InvoicePartner
				invoicePartner = new JSONArray();

				// Bill To
				contact = new JSONObject();
				name = new JSONObject();
				postalAddress = new JSONObject();
				country = new JSONObject();

				contact.put("role", "billTo");

				name.put("value", StringHandler.emptyIfNull(invoice.getBillToName()));
				name.put("lang", "en");
				contact.put("Name", name);

				postalAddress.put("Street", StringHandler.emptyIfNull(invoice.getBillToAddressLine2()));
				postalAddress.put("City", StringHandler.emptyIfNull(invoice.getBillToCity()));
				postalAddress.put("State", StringHandler.emptyIfNull(invoice.getBillToState()));
				postalAddress.put("PostalCode", StringHandler.emptyIfNull(invoice.getBillToZip()));

				countrySplit = StringHandler.emptyIfNull(invoice.getBillToCountry()).split(":");

				country.put("value", countrySplit.length >= 2 ? countrySplit[1] : "");
				country.put("isoCountryCode", countrySplit.length >= 2 ? countrySplit[0] : "");
				postalAddress.put("Country", country);

				contact.put("PostalAddress", postalAddress);

				invoicePartner.put(new JSONObject().put("Contact", contact));

				// Sold To
				// Same as Bill To
				contact = new JSONObject();

				contact.put("role", "soldTo");

				contact.put("Name", name);
				contact.put("PostalAddress", postalAddress);

				invoicePartner.put(new JSONObject().put("Contact", contact));

				// From
				contact = new JSONObject();
				name = new JSONObject();
				postalAddress = new JSONObject();
				country = new JSONObject();

				contact.put("role", "from");

				name.put("value", StringHandler.emptyIfNull(invoice.getOperatingEntityName()));
				name.put("lang", "en");
				contact.put("Name", name);

				postalAddress.put("Street", StringHandler.emptyIfNull(invoice.getOpsEntityAddressLine3()));
				postalAddress.put("City", StringHandler.emptyIfNull(invoice.getOpsEntityCity()));
				postalAddress.put("State", StringHandler.emptyIfNull(invoice.getOpsEntityState()));
				postalAddress.put("PostalCode", StringHandler.emptyIfNull(invoice.getOpsEntityZip()));

				countrySplit = StringHandler.emptyIfNull(invoice.getOpsEntityCountry()).split(":");

				country.put("value", countrySplit.length >= 2 ? countrySplit[1] : "");
				country.put("isoCountryCode", countrySplit.length >= 2 ? countrySplit[0] : "");

				postalAddress.put("Country", country);

				contact.put("PostalAddress", postalAddress);

				invoicePartner.put(new JSONObject().put("Contact", contact));

				// Remit To
				// Same as From
				contact = new JSONObject();

				contact.put("role", "remitTo");
				contact.put("PostalAddress", postalAddress);

				invoicePartner.put(new JSONObject().put("Contact", contact));

				// end Invoice Partner
				invoiceDetailRequestHeader.put("InvoicePartner", invoicePartner);

				// InvoiceDetailShipping
				invoiceDetailShipping = new JSONObject();
				shippingContacts = new JSONArray();

				// Ship To
				name = new JSONObject();
				postalAddress = new JSONObject();
				country = new JSONObject();

				contact.put("role", "shipTo");

				name.put("value", StringHandler.emptyIfNull(invoice.getShipToName()));
				name.put("lang", "en");
				contact.put("Name", name);

				postalAddress.put("Street", StringHandler.emptyIfNull(invoice.getShipToAddressLine3()));
				postalAddress.put("City", StringHandler.emptyIfNull(invoice.getShipToCity()));
				postalAddress.put("State", StringHandler.emptyIfNull(invoice.getShipToState()));
				postalAddress.put("PostalCode", StringHandler.emptyIfNull(invoice.getShipToZip()));

				countrySplit = StringHandler.emptyIfNull(invoice.getShipToCountry()).split(":");

				country.put("value", countrySplit.length >= 2 ? countrySplit[0] : "");
				country.put("isoCountryCode", countrySplit.length >= 2 ? countrySplit[1] : "");

				postalAddress.put("Country", country);

				contact.put("PostalAddress", postalAddress);

				shippingContacts.put(contact);

				// Ship From
				name = new JSONObject();
				postalAddress = new JSONObject();
				country = new JSONObject();

				contact.put("role", "shipFrom");

				name.put("value", StringHandler.emptyIfNull(invoice.getShipFromName()));
				name.put("lang", "en");
				contact.put("Name", name);

				postalAddress.put("Street", StringHandler.emptyIfNull(invoice.getShipFromAddressLine3()));
				postalAddress.put("City", StringHandler.emptyIfNull(invoice.getShipFromCity()));
				postalAddress.put("State", StringHandler.emptyIfNull(invoice.getShipFromState()));
				postalAddress.put("PostalCode", StringHandler.emptyIfNull(invoice.getShipFromZip()));

				countrySplit = StringHandler.emptyIfNull(invoice.getShipFromCountry()).split(":");

				country.put("value", countrySplit.length >= 2 ? countrySplit[1] : "");
				country.put("isoCountryCode", countrySplit.length >= 2 ? countrySplit[0] : "");

				postalAddress.put("Country", country);

				contact.put("PostalAddress", postalAddress);

				shippingContacts.put(contact);

				// end InvoiceShippingDetail
				invoiceDetailShipping.put("Contact", shippingContacts);
				invoiceDetailRequestHeader.put("InvoiceDetailShipping", invoiceDetailShipping);

				// InvoiceDetailPaymentTerm
				invoiceDetailPaymentTerm = new JSONObject();

				// invoiceDetailPaymentTerm.put("value", "");
				invoiceDetailPaymentTerm.put("payInNumberOfDays", StringHandler.emptyIfNull(invoice.getPaymentTerms()));
				invoiceDetailPaymentTerm.put("percentageRate", "");
				invoiceDetailPaymentTerm.put("value", "");

				invoiceDetailRequestHeader.put("InvoiceDetailPaymentTerm", invoiceDetailPaymentTerm);

				// end InvoiceDetailRequestHeader
				invoiceDetailRequest.put("InvoiceDetailRequestHeader", invoiceDetailRequestHeader);

				// InvoiceDetailOrder
				invoiceDetailOrder = new JSONObject();

				invoiceDetailOrderInfo = new JSONObject();
				orderReference = new JSONObject();
				documentReference = new JSONObject();

				documentReference.put("payloadID", payloadIdSplit.length >= 2 ? payloadIdSplit[0] : "");
				documentReference.put("value", "");

				orderReference.put("orderID", StringHandler.emptyIfNull(firstLine.getCustomerPo()));
				orderReference.put("orderDate", JSONObject.NULL);
				orderReference.put("DocumentReference", documentReference);

				invoiceDetailOrderInfo.put("OrderReference", orderReference);
				invoiceDetailOrder.put("InvoiceDetailOrderInfo", invoiceDetailOrderInfo);

				// InvoiceDetailItem
				invoiceDetailItem = new JSONArray();

				for(InvoicePrtRollinsLineViewBean invoiceLine : invoice.getInvoiceLines()) {
					item = new JSONObject();
					unitPrice = new JSONObject();
					invoiceDetailItemReference = new JSONObject();
					itemId = new JSONObject();
					subtotalAmount = new JSONObject();
					grossAmount = new JSONObject();
					netAmount = new JSONObject();
					tax = new JSONObject();
					taxDetail = new JSONObject();
					taxAmount = new JSONObject();

					item.put("invoiceLineNumber", invoiceLine.getInvoiceLine() != null ? invoiceLine.getInvoiceLine() : "");
					item.put("quantity", invoiceLine.getUnitOfSaleQuantity() != null ? invoiceLine.getUnitOfSaleQuantity() : "");
					item.put("UnitOfMeasure", invoiceLine.getUnitOfSale() != null ? invoiceLine.getUnitOfSale() : "");

					// Unit Price
					money = new JSONObject();
					money.put("value", invoiceLine.getUnitOfSalePrice() != null ? invoiceLine.getUnitOfSalePrice() : "");
					money.put("currency", StringHandler.emptyIfNull(invoiceLine.getCurrencyId()));

					unitPrice.put("Money", money);
					item.put("UnitPrice", unitPrice);

					itemId.put("SupplierPartID", StringHandler.emptyIfNull(invoiceLine.getCustomerPartNo()));

					description = new JSONObject();
					description.put("value", StringHandler.emptyIfNull(invoiceLine.getPartDescription()));
					description.put("lang", "en");

					invoiceDetailItemReference.put("ItemID", itemId);
					invoiceDetailItemReference.put("Description", description);
					invoiceDetailItemReference.put("lineNumber", StringHandler.emptyIfNull(invoiceLine.getCustomerPoLine()));

					item.put("InvoiceDetailItemReference", invoiceDetailItemReference);

					// Subtotal
					money = new JSONObject();
					money.put("value", invoiceLine.getMaterialAmount() != null ? invoiceLine.getMaterialAmount() : "");
					money.put("currency", StringHandler.emptyIfNull(invoiceLine.getCurrencyId()));

					subtotalAmount.put("Money", money);
					item.put("SubtotalAmount", subtotalAmount);

					invoiceDetailItem.put(item);
				}
				// end InvoiceDetailItem
				invoiceDetailOrder.put("InvoiceDetailItem", invoiceDetailItem);

				// end InvoiceDetailOrder
				invoiceDetailRequest.put("InvoiceDetailOrder", invoiceDetailOrder);

				// InvoiceDetailSummary
				invoiceDetailSummary = new JSONObject();
				subtotalAmount = new JSONObject();
				tax = new JSONObject();
				taxDetail = new JSONObject();
				taxAmount = new JSONObject();
				specialHandlingAmount = new JSONObject();
				shippingAmount = new JSONObject();
				grossAmount = new JSONObject();
				netAmount = new JSONObject();
				dueAmount = new JSONObject();

				// SubtotalAmount
				money = new JSONObject();
				money.put("value", invoice.getTotalMaterialAmount() != null ? invoice.getTotalMaterialAmount() : "");
				money.put("currency", StringHandler.emptyIfNull(invoice.getCurrencyId()));

				subtotalAmount.put("Money", money);
				invoiceDetailSummary.put("SubtotalAmount", subtotalAmount);

				// Tax
				money = new JSONObject();
				money.put("value", invoice.getTotalTaxAmount() != null ? invoice.getTotalTaxAmount() : "");
				money.put("currency", StringHandler.emptyIfNull(invoice.getCurrencyId()));

				description = new JSONObject();
				description.put("value", StringHandler.emptyIfNull(invoice.getTaxDescription()));
				description.put("lang", "en");

				tax.put("Money", money);
				tax.put("Description", description);

				// TaxDetail
				taxAmount.put("Money", money);

				taxDetail.put("category", StringHandler.emptyIfNull(invoice.getTaxCategory()));
				taxDetail.put("purpose", "tax");
				taxDetail.put("TaxAmount", taxAmount);

				description = new JSONObject();
				description.put("value", StringHandler.emptyIfNull(invoice.getTaxDescription()));
				description.put("lang", "en");

				taxDetail.put("Description", description);

				tax.put("TaxDetail", taxDetail);

				invoiceDetailSummary.put("Tax", tax);
				// end Tax

				// SpecialHandlingAmount
				money = new JSONObject();
				money.put("value", invoice.getTotalAddCharges() != null ? invoice.getTotalAddCharges() : "");
				money.put("currency", StringHandler.emptyIfNull(invoice.getCurrencyId()));

				specialHandlingAmount.put("Money", money);
				invoiceDetailSummary.put("SpecialHandlingAmount", specialHandlingAmount);

				// ShippingAmount
				money = new JSONObject();
				money.put("value", invoice.getTotalServiceFee() != null ? invoice.getTotalServiceFee() : "");
				money.put("currency", StringHandler.emptyIfNull(invoice.getCurrencyId()));

				shippingAmount.put("Money", money);
				invoiceDetailSummary.put("ShippingAmount", shippingAmount);

				// GrossAmount
				money = new JSONObject();
				money.put("value", invoice.getInvoiceAmount() != null ? invoice.getInvoiceAmount() : "");
				money.put("currency", StringHandler.emptyIfNull(invoice.getCurrencyId()));

				grossAmount.put("Money", money);
				invoiceDetailSummary.put("GrossAmount", grossAmount);

				// NetAmount
				// Same as GrossAmount
				netAmount.put("Money", money);
				invoiceDetailSummary.put("NetAmount", netAmount);

				// DueAmount
				// Same as GrossAmount
				dueAmount.put("Money", money);
				invoiceDetailSummary.put("DueAmount", dueAmount);

				// end InvoiceDetailSummary
				invoiceDetailRequest.put("InvoiceDetailSummary", invoiceDetailSummary);

				// end InvoiceDetailRequest
				request.put("InvoiceDetailRequest", invoiceDetailRequest);

				// end cXML
				cXML.put("Request", request);
				//resultsElement.put("cXML", cXML);
				//results.put(resultsElement);
				results.put(cXML);
			}
		}

		return results;
	}
}