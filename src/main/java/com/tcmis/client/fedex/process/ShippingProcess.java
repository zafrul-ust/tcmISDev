package com.tcmis.client.fedex.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sun.misc.BASE64Decoder;

import com.tcmis.client.fedex.beans.FedexShippingInfoBean;
import com.tcmis.client.fedex.beans.PackageDetailBean;
import com.tcmis.client.fedex.beans.ProcessShipmentReplyBean;
import com.tcmis.client.fedex.factory.FedexDangerousGoodsViewBeanFactory;
import com.tcmis.client.fedex.factory.FedexShippingInfoBeanFactory;
import com.tcmis.client.ups.beans.BoxShipmentSetupBean;
import com.tcmis.client.ups.beans.UpsDangerousGoodsViewBean;
import com.tcmis.client.ups.factory.BoxShipmentSetupBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.NumberHandler;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.print.beans.PrintLabelInputBean;
import com.tcmis.supplier.shipping.beans.MslBoxViewBean;
import com.tcmis.supplier.shipping.factory.MslBoxViewBeanFactory;

public class ShippingProcess extends BaseProcess {
	Log						log			= LogFactory.getLog(getClass());
	private ResourceLibrary	library		= null;
	private DbManager		dbManager	= null;
	private String			backupDirectoryPath;
	private String			fileFormat;
	private String			key;
	private String			password;
	private String			accountNumber;
	private String			meterNumber;
	private String			fedexUrl;
	private String[]		boxIdArray;

	public ShippingProcess(String client) throws Exception {
		super(client);
		library = new ResourceLibrary("fedex");
		dbManager = new DbManager(client);
		key = library.getString("key");
		password = library.getString("password");
		accountNumber = library.getString("accountnumber");
		meterNumber = library.getString("meternumber");
		fileFormat = library.getString("fileformat");
		backupDirectoryPath = library.getString("backupfile.dir");
		fedexUrl = library.getString("url");
	}

	public void getFedexLabel(LabelInputBean inputcBean) throws BaseException, Exception {
		ZplDataProcess zplDataProcess = new ZplDataProcess(getClient());
		PersonnelBean personnelBean = new PersonnelBean();

		inputcBean.setPaperSize("UPS");

		inputcBean.setSkipKitLabels("");

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean, inputcBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);

		FedexShippingInfoBeanFactory factory = new FedexShippingInfoBeanFactory(dbManager);
		Collection<FedexShippingInfoBean> c = factory.select(new SearchCriteria("packingGroupId", SearchCriterion.EQUALS, inputcBean.getPackingGroupId().toString()), null);

		if ((c == null) || (c.size() == 0)) {
			throw new NoDataException("No shipments for packingGroupId=" + inputcBean.getPackingGroupId());
		}

		BigDecimal totalWeight = new BigDecimal("0");
		int packageCount = 0;
		packageCount = c.size();
		boolean hazmatShipment = false;
		for (FedexShippingInfoBean tempBean : c) {
			if (tempBean.getWeight() == null) {
				throw new NoDataException("No Weight Information for packingGroupId=" + inputcBean.getPackingGroupId());
			}

			totalWeight = totalWeight.add(tempBean.getWeight());

			if ((tempBean.getHazmatFlag() != null) && (tempBean.getHazmatFlag().equalsIgnoreCase("Y"))) {
				hazmatShipment = true;
			}

		}

		UpsDangerousGoodsViewBean upsDangerousGoodsViewBean = null;
		if (hazmatShipment) {
			FedexDangerousGoodsViewBeanFactory dgdFactory = new FedexDangerousGoodsViewBeanFactory(dbManager);

			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("referenceNumber", SearchCriterion.EQUALS, inputcBean.getPackingGroupId().toString());
			criteria.addCriterion("identificationNumber", SearchCriterion.IS_NOT, null);

			SortCriteria scriteria = new SortCriteria();
			scriteria.addCriterion("hazardClass");

			Collection dgdCollection = dgdFactory.select(criteria, scriteria);
			Vector v = new Vector(dgdCollection);
			upsDangerousGoodsViewBean = (UpsDangerousGoodsViewBean) ((UpsDangerousGoodsViewBean) v.elementAt(0)).clone();
		}

		int packageSequence = 1;
		String masterTrackingNumber = "";
		for (FedexShippingInfoBean fedexShippingInfoBean : c) {
			fedexShippingInfoBean.setTotalWeight(totalWeight);
			if ((packageCount > 1) && (packageSequence > 1)) {
				fedexShippingInfoBean.setMasterTrackingNumber(masterTrackingNumber);
			}

			String request = getProcessShipmentRequest(fedexShippingInfoBean, upsDangerousGoodsViewBean, packageCount, packageSequence);

			File requestFile = FileHandler.saveTempFile(request, "req", ".xml", backupDirectoryPath);
			ProcessShipmentReplyParser processShipmentReplyParser = new ProcessShipmentReplyParser(getClient());
			String response = NetHandler.getHttpPostResponse(fedexUrl, null, null, request);

			File responseFile = FileHandler.saveTempFile(response, "res", ".xml", backupDirectoryPath);
			ProcessShipmentReplyBean processShipmentReplyBean = processShipmentReplyParser.parse(responseFile, packageCount, packageSequence);
			log.info("Packing Group " + inputcBean.getPackingGroupId() + " Severity " + processShipmentReplyBean.getSeverity() + " Message  " + processShipmentReplyBean.getMessage() + "");

			if ((!processShipmentReplyBean.getSeverity().equalsIgnoreCase("NOTE")) && (!processShipmentReplyBean.getSeverity().equalsIgnoreCase("SUCCESS")) && (!processShipmentReplyBean.getSeverity().equalsIgnoreCase("WARNING"))) {
				BulkMailProcess bulkMailProcess = new BulkMailProcess(getClient());
				bulkMailProcess.sendBulkEmail(new BigDecimal("13616"), "Error geting Fedex Label for Packing Group " + inputcBean.getPackingGroupId().toString() + " Severeity " + processShipmentReplyBean.getSeverity() + " ",
						"Error geting Fedex Label\n\nPacking Group " + inputcBean.getPackingGroupId().toString() + " Severeity " + processShipmentReplyBean.getSeverity() + "\n\n Message  " + processShipmentReplyBean.getMessage(), false);

				bulkMailProcess.sendBulkEmail(new BigDecimal("11862"), "Error geting Fedex Label for Packing Group " + inputcBean.getPackingGroupId().toString() + " Severeity " + processShipmentReplyBean.getSeverity() + " ",
						"Error geting Fedex Label\n\nPacking Group " + inputcBean.getPackingGroupId().toString() + " Severeity " + processShipmentReplyBean.getSeverity() + "\n\n Message  " + processShipmentReplyBean.getMessage(), false);

				bulkMailProcess.sendBulkEmail(new BigDecimal("11653"), "Error geting Fedex Label for Packing Group " + inputcBean.getPackingGroupId().toString() + " Severeity " + processShipmentReplyBean.getSeverity() + " ",
						"Error geting Fedex Label\n\nPacking Group " + inputcBean.getPackingGroupId().toString() + " Severeity " + processShipmentReplyBean.getSeverity() + "\n\n Message  " + processShipmentReplyBean.getMessage(), false);

				throw new NoDataException("" + processShipmentReplyBean.getMessage() + "");
			}

			int i = 0;
			for (PackageDetailBean packageDetailBean : processShipmentReplyBean.getPackageDetailBeanCollection()) {
				if ((packageCount > 1) && (packageSequence == 1)) {
					masterTrackingNumber = packageDetailBean.getTrackingNumber();
					fedexShippingInfoBean.setMasterTrackingNumber(masterTrackingNumber);
				}
				else if ((packageCount == 1) && (packageSequence == 1)) {
					masterTrackingNumber = packageDetailBean.getTrackingNumber();
					fedexShippingInfoBean.setMasterTrackingNumber(masterTrackingNumber);
				}
				else {
					fedexShippingInfoBean.setMasterTrackingNumber(masterTrackingNumber);
				}

				File image = File.createTempFile("label", "." + fileFormat, new File(backupDirectoryPath));
				FileOutputStream out = new FileOutputStream(image, true);
				out.write(new BASE64Decoder().decodeBuffer(packageDetailBean.getLabelImage()));
				out.close();
				packageDetailBean.setLabelFilePath(image.getAbsolutePath());
				packageDetailBean.setFile(image);
				String s = saveShippingInfo(fedexShippingInfoBean, processShipmentReplyBean, packageDetailBean);

				if ("OK".equalsIgnoreCase(s)) {
					try {
						PrintHandler.print(printerPath, image.getAbsolutePath());
					}
					catch (Exception e) {
						log.fatal("Error printing Fedex label:" + e.getMessage(), e);
					}
				}
			}
			packageSequence++;
		}
	}

	private String saveShippingInfo(FedexShippingInfoBean fedexShippingInfoBean, ProcessShipmentReplyBean processShipmentReplyBean, PackageDetailBean packageDetailBean) throws BaseException, Exception {
		String result = "";
		GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);

		Collection<String> resultCollection = null;
		Collection inArgs = new Vector(7);
		inArgs.add(fedexShippingInfoBean.getBoxId());
		inArgs.add("");
		inArgs.add(fedexShippingInfoBean.getMasterTrackingNumber());
		inArgs.add("Fedex Ground");
		inArgs.add("");
		inArgs.add("");
		inArgs.add("");
		if (log.isDebugEnabled())
		;
		Collection inArgs2 = new Vector(7);
		inArgs2.add(NumberHandler.emptyIfNull(packageDetailBean.getNetFreight()));
		inArgs2.add(NumberHandler.emptyIfNull(packageDetailBean.getTotalSurcharges()));
		inArgs2.add(NumberHandler.emptyIfNull(packageDetailBean.getNetCharge()));
		inArgs2.add(NumberHandler.emptyIfNull(packageDetailBean.getBillingWeight()));
		inArgs2.add(packageDetailBean.getLabelFilePath());
		inArgs2.add("");

		inArgs2.add(packageDetailBean.getTrackingNumber());
		if (log.isDebugEnabled())
		;
		Collection outArgs = new Vector(1);
		outArgs.add(new Integer(12));
		resultCollection = procFactory.doProcedure("P_BOX_UPDATE", inArgs, outArgs, inArgs2);
		for (String s : resultCollection) {
			result = s;
			if (!"OK".equalsIgnoreCase(s)) {
				log.error("Error calling P_BOX_UPDATE:" + inArgs + " " + inArgs2);
				log.error("Procedure message:" + s);
			}
		}

		return result;
	}

	public String getProcessShipmentRequest(FedexShippingInfoBean fedexShippingInfoBean, UpsDangerousGoodsViewBean upsDangerousGoodsViewBean, int packageCount, int packageSequence) throws Exception {
		StringWriter sw = new StringWriter();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element processShipmentRequestElement = document.createElement("ns:ProcessShipmentRequest");
			processShipmentRequestElement.setAttribute("xmlns:ns", "http://fedex.com/ws/ship/v3");
			processShipmentRequestElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			processShipmentRequestElement.setAttribute("xsi:schemaLocation", "http://fedex.com/ws/ship/v3/ShipService_v3.xsd");
			document.appendChild(processShipmentRequestElement);
			Element webAuthenticationDetailElement = document.createElement("ns:WebAuthenticationDetail");
			Element userCredentialElement = document.createElement("ns:UserCredential");
			Element keyElement = document.createElement("ns:Key");

			keyElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierSecurityKey())));
			userCredentialElement.appendChild(keyElement);
			Element passwordElement = document.createElement("ns:Password");

			passwordElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierPassword())));
			userCredentialElement.appendChild(passwordElement);
			webAuthenticationDetailElement.appendChild(userCredentialElement);
			processShipmentRequestElement.appendChild(webAuthenticationDetailElement);

			Element clientDetailElement = document.createElement("ns:ClientDetail");
			Element accountNumberElement = document.createElement("ns:AccountNumber");

			accountNumberElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierAccount())));

			clientDetailElement.appendChild(accountNumberElement);
			Element meterNumberElement = document.createElement("ns:MeterNumber");

			meterNumberElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierMeterNumber())));
			clientDetailElement.appendChild(meterNumberElement);
			processShipmentRequestElement.appendChild(clientDetailElement);

			Element transactionDetailElement = document.createElement("ns:TransactionDetail");
			Element customerTransactionIdElement = document.createElement("ns:CustomerTransactionId");
			customerTransactionIdElement.appendChild(document.createTextNode(""));
			transactionDetailElement.appendChild(customerTransactionIdElement);
			processShipmentRequestElement.appendChild(transactionDetailElement);

			Element versionElement = document.createElement("ns:Version");
			Element serviceIdElement = document.createElement("ns:ServiceId");
			serviceIdElement.appendChild(document.createTextNode("ship"));
			versionElement.appendChild(serviceIdElement);
			Element majorElement = document.createElement("ns:Major");
			majorElement.appendChild(document.createTextNode("3"));
			versionElement.appendChild(majorElement);
			Element intermediateElement = document.createElement("ns:Intermediate");
			intermediateElement.appendChild(document.createTextNode("0"));
			versionElement.appendChild(intermediateElement);
			Element minorElement = document.createElement("ns:Minor");
			minorElement.appendChild(document.createTextNode("0"));
			versionElement.appendChild(minorElement);
			processShipmentRequestElement.appendChild(versionElement);

			Element requestedShipmentElement = document.createElement("ns:RequestedShipment");

			Element shipTimestampElement = document.createElement("ns:ShipTimestamp");
			shipTimestampElement.appendChild(document.createTextNode(new StringBuilder(DateHandler.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ssZ")).insert(22, ":").toString()));
			requestedShipmentElement.appendChild(shipTimestampElement);
			Element dropoffTypeElement = document.createElement("ns:DropoffType");
			dropoffTypeElement.appendChild(document.createTextNode("REGULAR_PICKUP"));
			requestedShipmentElement.appendChild(dropoffTypeElement);
			Element serviceTypeElement = document.createElement("ns:ServiceType");
			serviceTypeElement.appendChild(document.createTextNode("FEDEX_GROUND"));

			requestedShipmentElement.appendChild(serviceTypeElement);
			Element packagingTypeElement = document.createElement("ns:PackagingType");
			packagingTypeElement.appendChild(document.createTextNode("YOUR_PACKAGING"));
			requestedShipmentElement.appendChild(packagingTypeElement);

			Element totalWeightTypeElement = document.createElement("ns:TotalWeight");
			Element totalWeightUnitsElement = document.createElement("ns:Units");
			totalWeightUnitsElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getWeightUm())));
			totalWeightTypeElement.appendChild(totalWeightUnitsElement);
			Element totalWeightValueElement = document.createElement("ns:Value");
			totalWeightValueElement.appendChild(document.createTextNode(StringHandler.xmlEncode("" + fedexShippingInfoBean.getTotalWeight() + "")));
			totalWeightTypeElement.appendChild(totalWeightValueElement);
			totalWeightTypeElement.appendChild(document.createTextNode(""));
			requestedShipmentElement.appendChild(totalWeightTypeElement);

			Element shipperElement = document.createElement("ns:Shipper");
			Element shipperContactElement = document.createElement("ns:Contact");

			Element shipperContactCompanyNameElement = document.createElement("ns:CompanyName");
			shipperContactCompanyNameElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getShipFromCompany())));
			shipperContactElement.appendChild(shipperContactCompanyNameElement);
			Element shipperContactPhoneElement = document.createElement("ns:PhoneNumber");

			shipperContactPhoneElement.appendChild(document.createTextNode(StringHandler.xmlEncode(StringHandler.emptyIfNull(fedexShippingInfoBean.getShipFromPhone()))));
			shipperContactElement.appendChild(shipperContactPhoneElement);
			shipperElement.appendChild(shipperContactElement);
			Element shipperAddressElement = document.createElement("ns:Address");
			Element shipperAddressStreet1Element = document.createElement("ns:StreetLines");
			shipperAddressStreet1Element.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getShipFromAdd1())));
			shipperAddressElement.appendChild(shipperAddressStreet1Element);
			Element shipperAddressStreet2Element = document.createElement("ns:StreetLines");
			shipperAddressStreet2Element.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getShipFromAdd2())));
			shipperAddressElement.appendChild(shipperAddressStreet2Element);
			Element shipperAddressCityElement = document.createElement("ns:City");
			shipperAddressCityElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getShipFromCity())));
			shipperAddressElement.appendChild(shipperAddressCityElement);
			Element shipperAddressStateElement = document.createElement("ns:StateOrProvinceCode");
			shipperAddressStateElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getShipFromState())));
			shipperAddressElement.appendChild(shipperAddressStateElement);
			Element shipperAddressZipElement = document.createElement("ns:PostalCode");
			shipperAddressZipElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getShipFromZip())));
			shipperAddressElement.appendChild(shipperAddressZipElement);
			Element shipperAddressCountryElement = document.createElement("ns:CountryCode");
			shipperAddressCountryElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getShipFromCountry())));
			shipperAddressElement.appendChild(shipperAddressCountryElement);

			shipperElement.appendChild(shipperAddressElement);
			requestedShipmentElement.appendChild(shipperElement);

			Element recipientElement = document.createElement("ns:Recipient");
			Element recipientContactElement = document.createElement("ns:Contact");

			Element recipientContactCompanyNameElement = document.createElement("ns:CompanyName");
			recipientContactCompanyNameElement.appendChild(document.createTextNode(fedexShippingInfoBean.getShipToCompany()));
			recipientContactElement.appendChild(recipientContactCompanyNameElement);
			Element recipientContactPhoneElement = document.createElement("ns:PhoneNumber");

			recipientContactPhoneElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(fedexShippingInfoBean.getShipFromPhone())));
			recipientContactElement.appendChild(recipientContactPhoneElement);
			recipientElement.appendChild(recipientContactElement);
			Element recipientAddressElement = document.createElement("ns:Address");
			Element recipientAddressStreet1Element = document.createElement("ns:StreetLines");
			recipientAddressStreet1Element.appendChild(document.createTextNode(fedexShippingInfoBean.getShipToAdd1()));
			recipientAddressElement.appendChild(recipientAddressStreet1Element);
			Element recipientAddressStreet2Element = document.createElement("ns:StreetLines");
			recipientAddressStreet2Element.appendChild(document.createTextNode(fedexShippingInfoBean.getShipToAdd2()));
			recipientAddressElement.appendChild(recipientAddressStreet2Element);
			Element recipientAddressCityElement = document.createElement("ns:City");
			recipientAddressCityElement.appendChild(document.createTextNode(fedexShippingInfoBean.getShipToCity()));
			recipientAddressElement.appendChild(recipientAddressCityElement);
			Element recipientAddressStateElement = document.createElement("ns:StateOrProvinceCode");
			recipientAddressStateElement.appendChild(document.createTextNode(fedexShippingInfoBean.getShipToState()));
			recipientAddressElement.appendChild(recipientAddressStateElement);
			Element recipientAddressZipElement = document.createElement("ns:PostalCode");
			recipientAddressZipElement.appendChild(document.createTextNode(fedexShippingInfoBean.getShipToZip()));
			recipientAddressElement.appendChild(recipientAddressZipElement);
			Element recipientAddressCountryElement = document.createElement("ns:CountryCode");
			recipientAddressCountryElement.appendChild(document.createTextNode(fedexShippingInfoBean.getShipToCountry()));
			recipientAddressElement.appendChild(recipientAddressCountryElement);

			recipientElement.appendChild(recipientAddressElement);
			requestedShipmentElement.appendChild(recipientElement);

			Element shippingChargesPaymentElement = document.createElement("ns:ShippingChargesPayment");
			Element paymentTypeElement = document.createElement("ns:PaymentType");
			paymentTypeElement.appendChild(document.createTextNode("SENDER"));
			shippingChargesPaymentElement.appendChild(paymentTypeElement);
			Element payorElement = document.createElement("ns:Payor");
			Element payorAccountNumberElement = document.createElement("ns:AccountNumber");

			payorAccountNumberElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierAccount())));
			payorElement.appendChild(payorAccountNumberElement);
			Element payorCountryCodeElement = document.createElement("ns:CountryCode");
			payorCountryCodeElement.appendChild(document.createTextNode(fedexShippingInfoBean.getFedexCountryCode()));
			payorElement.appendChild(payorCountryCodeElement);
			shippingChargesPaymentElement.appendChild(payorElement);
			requestedShipmentElement.appendChild(shippingChargesPaymentElement);

			Element labelSpecificationElement = document.createElement("ns:LabelSpecification");
			Element labelFormatTypeElement = document.createElement("ns:LabelFormatType");
			labelFormatTypeElement.appendChild(document.createTextNode("COMMON2D"));
			labelSpecificationElement.appendChild(labelFormatTypeElement);
			Element imageTypeElement = document.createElement("ns:ImageType");
			imageTypeElement.appendChild(document.createTextNode(fileFormat));
			labelSpecificationElement.appendChild(imageTypeElement);
			Element labelStockTypeElement = document.createElement("ns:LabelStockType");
			labelStockTypeElement.appendChild(document.createTextNode("STOCK_4X6"));
			labelSpecificationElement.appendChild(labelStockTypeElement);
			Element labelPrintingOrientationElement = document.createElement("ns:LabelPrintingOrientation");
			labelPrintingOrientationElement.appendChild(document.createTextNode("TOP_EDGE_OF_TEXT_FIRST"));
			labelSpecificationElement.appendChild(labelPrintingOrientationElement);
			Element customerSpecificDetailElement = document.createElement("ns:CustomerSpecifiedDetail");
			Element maskedDataElement = document.createElement("ns:MaskedData");
			maskedDataElement.appendChild(document.createTextNode("SHIPPER_ACCOUNT_NUMBER"));
			customerSpecificDetailElement.appendChild(maskedDataElement);
			labelSpecificationElement.appendChild(customerSpecificDetailElement);
			requestedShipmentElement.appendChild(labelSpecificationElement);

			Element rateRequestTypesElement = document.createElement("ns:RateRequestTypes");
			rateRequestTypesElement.appendChild(document.createTextNode("ACCOUNT"));
			requestedShipmentElement.appendChild(rateRequestTypesElement);

			if ((packageCount > 1) && (packageSequence > 1)) {
				Element masterTrackingIdElement = document.createElement("ns:MasterTrackingId");
				Element trackingNumberElement = document.createElement("ns:TrackingNumber");
				trackingNumberElement.appendChild(document.createTextNode(fedexShippingInfoBean.getMasterTrackingNumber()));
				masterTrackingIdElement.appendChild(trackingNumberElement);
				requestedShipmentElement.appendChild(masterTrackingIdElement);
			}

			Element packageCountElement = document.createElement("ns:PackageCount");
			packageCountElement.appendChild(document.createTextNode("" + packageCount + ""));
			requestedShipmentElement.appendChild(packageCountElement);

			Element requestedPackagesElement = document.createElement("ns:RequestedPackages");
			Element sequenceNumberElement = document.createElement("ns:SequenceNumber");
			sequenceNumberElement.appendChild(document.createTextNode("" + packageSequence + ""));
			requestedPackagesElement.appendChild(sequenceNumberElement);

			Element weightElement = document.createElement("ns:Weight");
			Element unitsElement = document.createElement("ns:Units");
			unitsElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getWeightUm())));
			weightElement.appendChild(unitsElement);
			Element valueElement = document.createElement("ns:Value");
			valueElement.appendChild(document.createTextNode(StringHandler.xmlEncode("" + fedexShippingInfoBean.getWeight() + "")));
			weightElement.appendChild(valueElement);
			requestedPackagesElement.appendChild(weightElement);

			Element customerReferencesElement = document.createElement("ns:CustomerReferences");
			Element customerReferenceTypeElement = document.createElement("ns:CustomerReferenceType");
			customerReferenceTypeElement.appendChild(document.createTextNode("CUSTOMER_REFERENCE"));
			customerReferencesElement.appendChild(customerReferenceTypeElement);
			Element customerReferenceValueElement = document.createElement("ns:Value");
			customerReferenceValueElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getOrderNo()) + " " + StringHandler.xmlEncode(fedexShippingInfoBean.getBoxId())));
			customerReferencesElement.appendChild(customerReferenceValueElement);
			requestedPackagesElement.appendChild(customerReferencesElement);

			if ((fedexShippingInfoBean.getHazmatFlag() != null) && (fedexShippingInfoBean.getHazmatFlag().equalsIgnoreCase("Y"))) {
				Element specialServicesRequestedElement = document.createElement("ns:SpecialServicesRequested");
				Element specialServiceTypesElement = document.createElement("ns:SpecialServiceTypes");
				specialServiceTypesElement.appendChild(document.createTextNode("DANGEROUS_GOODS"));
				specialServicesRequestedElement.appendChild(specialServiceTypesElement);
				Element dangerousGoodsDetailElement = document.createElement("ns:DangerousGoodsDetail");

				Element hazMatCertificateDataElement = document.createElement("ns:HazMatCertificateData");
				Element dotProperShippingNameElement = document.createElement("ns:DotProperShippingName");
				dotProperShippingNameElement.appendChild(document.createTextNode(StringHandler.xmlEncode(upsDangerousGoodsViewBean.getShippingName())));
				hazMatCertificateDataElement.appendChild(dotProperShippingNameElement);
				Element dotHazardClassOrDivisionElement = document.createElement("ns:DotHazardClassOrDivision");
				dotHazardClassOrDivisionElement.appendChild(document.createTextNode(StringHandler.xmlEncode(upsDangerousGoodsViewBean.getHazardClass())));
				hazMatCertificateDataElement.appendChild(dotHazardClassOrDivisionElement);
				Element dotIdNumberElement = document.createElement("ns:DotIdNumber");
				dotIdNumberElement.appendChild(document.createTextNode(StringHandler.xmlEncode(upsDangerousGoodsViewBean.getIdentificationNumber())));
				hazMatCertificateDataElement.appendChild(dotIdNumberElement);
				Element dotLabelTypeElement = document.createElement("ns:DotLabelType");
				dotLabelTypeElement.appendChild(document.createTextNode(StringHandler.xmlEncode(StringHandler.emptyIfNull(upsDangerousGoodsViewBean.getDotLabelType()))));
				hazMatCertificateDataElement.appendChild(dotLabelTypeElement);
				Element packingGroupElement = document.createElement("ns:PackingGroup");
				packingGroupElement.appendChild(document.createTextNode(StringHandler.xmlEncode(StringHandler.emptyIfNull(upsDangerousGoodsViewBean.getPackingGroupNumber()))));
				hazMatCertificateDataElement.appendChild(packingGroupElement);
				Element quantityElement = document.createElement("ns:Quantity");
				quantityElement.appendChild(document.createTextNode(StringHandler.xmlEncode("" + fedexShippingInfoBean.getHazWeight() + "")));
				hazMatCertificateDataElement.appendChild(quantityElement);
				Element hazUnitsElement = document.createElement("ns:Units");
				hazUnitsElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getWeightUm())));
				hazMatCertificateDataElement.appendChild(hazUnitsElement);
				Element hazContactNumberElement = document.createElement("ns:TwentyFourHourEmergencyResponseContactNumber");
				hazContactNumberElement.appendChild(document.createTextNode(upsDangerousGoodsViewBean.getEmergencyPhone()));
				hazMatCertificateDataElement.appendChild(hazContactNumberElement);
				Element hazContactNameElement = document.createElement("ns:TwentyFourHourEmergencyResponseContactName");
				hazContactNameElement.appendChild(document.createTextNode("HAAS"));
				hazMatCertificateDataElement.appendChild(hazContactNameElement);
				dangerousGoodsDetailElement.appendChild(hazMatCertificateDataElement);
				specialServicesRequestedElement.appendChild(dangerousGoodsDetailElement);
				requestedPackagesElement.appendChild(specialServicesRequestedElement);
			}

			requestedShipmentElement.appendChild(requestedPackagesElement);
			processShipmentRequestElement.appendChild(requestedShipmentElement);

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(sw);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e);
		}
		return sw.toString();
	}

	public void voidShipment(String cancelTrackingNumber, FedexShippingInfoBean fedexShippingInfoBean) throws BaseException, Exception {
		log.info("Canceling FedEx tracking number: " + cancelTrackingNumber);
		FileHandler.saveTempFile(getCancelRequest(cancelTrackingNumber, fedexShippingInfoBean), "void_request", ".xml", library.getString("backupfile.dir"));
		String response = NetHandler.getHttpPostResponse(fedexUrl, null, null, getCancelRequest(cancelTrackingNumber, fedexShippingInfoBean));
		FileHandler.saveTempFile(response, "void_response", ".xml", library.getString("backupfile.dir"));
	}

	public String getCancelRequest(String cancelTrackingNumber, FedexShippingInfoBean fedexShippingInfoBean) throws Exception {
		StringWriter sw = new StringWriter();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element processShipmentRequestElement = document.createElement("ns:DeleteShipmentRequest");
			processShipmentRequestElement.setAttribute("xmlns:ns", "http://fedex.com/ws/ship/v3");
			processShipmentRequestElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			processShipmentRequestElement.setAttribute("xsi:schemaLocation", "http://fedex.com/ws/ship/v3/ShipService_v3.xsd");
			document.appendChild(processShipmentRequestElement);
			Element webAuthenticationDetailElement = document.createElement("ns:WebAuthenticationDetail");
			Element userCredentialElement = document.createElement("ns:UserCredential");
			Element keyElement = document.createElement("ns:Key");

			keyElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierSecurityKey())));
			userCredentialElement.appendChild(keyElement);
			Element passwordElement = document.createElement("ns:Password");

			passwordElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierPassword())));
			userCredentialElement.appendChild(passwordElement);
			webAuthenticationDetailElement.appendChild(userCredentialElement);
			processShipmentRequestElement.appendChild(webAuthenticationDetailElement);

			Element clientDetailElement = document.createElement("ns:ClientDetail");
			Element accountNumberElement = document.createElement("ns:AccountNumber");

			accountNumberElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierAccount())));
			clientDetailElement.appendChild(accountNumberElement);
			Element meterNumberElement = document.createElement("ns:MeterNumber");

			meterNumberElement.appendChild(document.createTextNode(StringHandler.xmlEncode(fedexShippingInfoBean.getCarrierMeterNumber())));
			clientDetailElement.appendChild(meterNumberElement);
			processShipmentRequestElement.appendChild(clientDetailElement);

			Element transactionDetailElement = document.createElement("ns:TransactionDetail");
			Element customerTransactionIdElement = document.createElement("ns:CustomerTransactionId");
			customerTransactionIdElement.appendChild(document.createTextNode(""));
			transactionDetailElement.appendChild(customerTransactionIdElement);
			processShipmentRequestElement.appendChild(transactionDetailElement);

			Element versionElement = document.createElement("ns:Version");
			Element serviceIdElement = document.createElement("ns:ServiceId");
			serviceIdElement.appendChild(document.createTextNode("ship"));
			versionElement.appendChild(serviceIdElement);
			Element majorElement = document.createElement("ns:Major");
			majorElement.appendChild(document.createTextNode("3"));
			versionElement.appendChild(majorElement);
			Element intermediateElement = document.createElement("ns:Intermediate");
			intermediateElement.appendChild(document.createTextNode("0"));
			versionElement.appendChild(intermediateElement);
			Element minorElement = document.createElement("ns:Minor");
			minorElement.appendChild(document.createTextNode("0"));
			versionElement.appendChild(minorElement);
			processShipmentRequestElement.appendChild(versionElement);

			Element shipTimestampElement = document.createElement("ns:ShipTimestamp");
			shipTimestampElement.appendChild(document.createTextNode(new StringBuilder(DateHandler.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ssZ")).insert(22, ":").toString()));
			processShipmentRequestElement.appendChild(shipTimestampElement);

			Element trackingNumberElement = document.createElement("ns:TrackingNumber");
			trackingNumberElement.appendChild(document.createTextNode(cancelTrackingNumber));
			processShipmentRequestElement.appendChild(trackingNumberElement);

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(sw);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e);
		}
		return sw.toString();
	}

	public void reprint(PrintLabelInputBean inputBean) throws BaseException, Exception {
		MslBoxViewBeanFactory mslBoxViewBeanFactory = new MslBoxViewBeanFactory(dbManager);
		Collection<MslBoxViewBean> coll = mslBoxViewBeanFactory.select(new SearchCriteria("packingGroupId", SearchCriterion.EQUALS, inputBean.getPackingGroupId().toString()), null);
		BoxShipmentSetupBeanFactory factory = new BoxShipmentSetupBeanFactory(dbManager);
		for (MslBoxViewBean mslBoxViewBean : coll) {
			Collection<BoxShipmentSetupBean> c = factory.select(new SearchCriteria("boxId", SearchCriterion.EQUALS, mslBoxViewBean.getBoxId()), null);
			for (BoxShipmentSetupBean bean : c)
				PrintHandler.print(inputBean.getPrinterName(), bean.getCarrierLabelFilename());
		}
	}
}