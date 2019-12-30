package com.tcmis.client.dla.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.dla.beans.DlaGasOrderCorrectionsBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;



public class DlaGasOrderCorrectionsProcess extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public DlaGasOrderCorrectionsProcess(String client, Locale locale) {
		super(client, locale);
	}

	public DlaGasOrderCorrectionsProcess(String client) {
		super(client);
	}

	/* Method notes
	 * Getting data by DLA contract number (Hudson=SPE4A616D0226,HAAS=SPM4AR07D0100)
	 * the pr_number from RLI
	 * the asn records to update and the ship to in RLIE
	 * put them together in for the grid header asn data first then asn detail data
	 * */
	@SuppressWarnings("unchecked")
	public Collection<DlaGasOrderCorrectionsBean> getSearchResult(DlaGasOrderCorrectionsBean input, PersonnelBean user) throws BaseException {
		DbManager dbManager = null;
		Connection conn = null;
		GenericSqlFactory factory = null;
		Vector<DlaGasOrderCorrectionsBean> dateBeans = null;
		try
		{
			dbManager = new DbManager(getClient(), getLocale());
			conn = dbManager.getConnection();
			factory = new GenericSqlFactory(dbManager, new DlaGasOrderCorrectionsBean());
			HashMap<BigDecimal,String> asnIdToShipId = new HashMap<BigDecimal,String>();
			String prNumber = factory.selectSingleValue("select pr_number from rli  where company_id = 'USGOV' and po_number = '"+input.getContractId()+"-"+input.getReleaseNum()+"'",conn);
			dateBeans = (Vector<DlaGasOrderCorrectionsBean>)(factory.selectQuery("SELECT oa.*, rlie.ship_to_dodaac FROM customer.outbound_asn oa, REQUEST_LINE_ITEM_EXTENSION rlie WHERE "
					+ "oa.release_num  = rlie.release_num and rlie.company_id = 'USGOV' and rlie.trading_partner_id = 'DTDN'  AND oa.release_num = '" + input.getReleaseNum() + "' and rlie.pr_number = " 
					+ prNumber + " AND oa.CUSTOMER_PO_NO = '"+input.getContractId()+"'",conn));	
			StringBuilder asnIds = new StringBuilder();
			int addCount = 1;
			for(DlaGasOrderCorrectionsBean outboundAsnBean : dateBeans)
			{
				outboundAsnBean.setPrNumber(new BigDecimal(prNumber));
				outboundAsnBean.setIsHeaderRecord(true);
				BigDecimal asnId = outboundAsnBean.getAsnId();
				asnIds.append(asnId);
				if(addCount != dateBeans.size())
					asnIds.append(",");
				asnIdToShipId.put(asnId, outboundAsnBean.getUsgovShipmentId());
				++addCount;
			}
			
			Vector<DlaGasOrderCorrectionsBean> caseBeans = (Vector<DlaGasOrderCorrectionsBean>)(factory.selectQuery("select B.CASE_RFID, i.PR_NUMBER from tcm_ops.issue i, tcm_ops.box b, tcm_ops.box_shipment_setup x where i.packing_group_id = b.packing_group_id and i.pr_number = "+prNumber+" and i.line_item = 1 and x.box_id = b.box_id"));
			Vector<DlaGasOrderCorrectionsBean> detailBeans = (Vector<DlaGasOrderCorrectionsBean>)(factory.selectQuery("select * from customer.outbound_asn_detail where asn_id in (" + asnIds.toString() +")",conn));
			int caseBeansCount = 0;
			for(DlaGasOrderCorrectionsBean bean: detailBeans)
			{
				if(caseBeans.size() > 0)
				{
					bean.setCaseRfid(caseBeans.elementAt(caseBeansCount).getCaseRfid());
					bean.setPrNumber(caseBeans.elementAt(caseBeansCount++).getPrNumber());
				}
				bean.setIsHeaderRecord(false);
				bean.setUsgovShipmentId(asnIdToShipId.get(bean.getAsnId()));
			}
			dateBeans.addAll(detailBeans);
		}catch(Exception e)
		{}
		finally
		{
			dbManager.returnConnection(conn);
			dbManager = null;
			conn = null;
			factory = null;
		}

		return dateBeans;
	}
	/* Method notes
	 * update asn records and the ship to in RLIE if header row
	 * else update asn detail data
	 * */
	public Collection update(Vector<DlaGasOrderCorrectionsBean> inputLines) throws BaseException {
		Vector errorMessages = new Vector();
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DlaGasOrderCorrectionsBean());
		Connection conn = dbManager.getConnection();
		try{
			for (DlaGasOrderCorrectionsBean inputBean : inputLines) {
				if (inputBean.getOkDoUpdate().equalsIgnoreCase("true")) 
				{ 	
					if(inputBean.getIsHeaderRecord())
					{	
						try {
								
							StringBuilder query = new StringBuilder("update customer.outbound_asn set estimate_delivery_date = "+ DateHandler.getOracleToDateFunction(inputBean.getEstimateDeliveryDate()));
								query.append(", insp_dodaac = '" + StringHandler.emptyIfNull(inputBean.getInspDodaac()));						
								query.append("', insp_address_line_1 = '" +  StringHandler.emptyIfNull(inputBean.getInspAddressLine1()));
								query.append("', insp_address_line_2 = '" +  StringHandler.emptyIfNull(inputBean.getInspAddressLine2()));
								query.append("', insp_address_line_3 = '" +  StringHandler.emptyIfNull(inputBean.getInspAddressLine3()));
								query.append("', INSP_REQUIRED = '" +  StringHandler.emptyIfNull(inputBean.getInspRequired()));
								query.append("', insp_city = '" +  StringHandler.emptyIfNull(inputBean.getInspCity()));
								query.append("', insp_email = '" +  StringHandler.emptyIfNull(inputBean.getInspEmail()));
								query.append("', insp_phone = '" +  StringHandler.emptyIfNull(inputBean.getInspPhone()));
								query.append("', insp_name = '" +  StringHandler.emptyIfNull(inputBean.getInspName()));
								query.append("', insp_state = '" +  StringHandler.emptyIfNull(inputBean.getInspState()));
								query.append("', insp_zip = '" +  StringHandler.emptyIfNull(inputBean.getInspZip()));
								query.append("', INSP_COUNTRY = '" +  StringHandler.emptyIfNull(inputBean.getInspCountry()));
								query.append("', INSP_FAX_NUMBER = '" +  StringHandler.emptyIfNull(inputBean.getInspFaxNumber()));
								query.append("', INSP_PHONE_EXTENSION = " + inputBean.getInspPhoneExtension());
								query.append(", ship_from_location_id = '" +  StringHandler.emptyIfNull(inputBean.getShipFromLocationId()));
								query.append("', ship_from_cage_code = '" +  StringHandler.emptyIfNull(inputBean.getShipFromCageCode()));
								query.append("', BILL_OF_LADING = '" +  StringHandler.emptyIfNull(inputBean.getBillOfLading()));
								query.append("', CARRIER_NAME = '" +  StringHandler.emptyIfNull(inputBean.getCarrierName()));
								query.append("', CONTRACT_OFFICE = '" +  StringHandler.emptyIfNull(inputBean.getContractOffice()));
								query.append("', CONTRACT_OFFICER_NAME = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficerName()));
								query.append("', CONTRACT_OFFICE_ADDRESS1 = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeAddress1()));
								query.append("', CONTRACT_OFFICE_ADDRESS2 = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeAddress2()));
								query.append("', CONTRACT_OFFICE_ALTERNATE_NAME = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeAlternateName()));
								query.append("', CONTRACT_OFFICE_CITY = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeCity()));
								query.append("', CONTRACT_OFFICE_CODE = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeCode()));
								query.append("', CONTRACT_OFFICE_COUNTRY = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeCountry()));
								query.append("', CONTRACT_OFFICE_STATE = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeState()));
								query.append("', CONTRACT_OFFICE_ZIP = '" +  StringHandler.emptyIfNull(inputBean.getContractOfficeZip()));		
								query.append("', DELIVERY_TICKET = '" +  StringHandler.emptyIfNull(inputBean.getDeliveryTicket()));
								query.append("', ITEM_DESCRIPTION = '" +  StringHandler.emptyIfNull(inputBean.getItemDescription()));
								query.append("', PAYOFFICE_ADDITIONAL_NAME = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeAdditionalName()));
								query.append("', PAYOFFICE_ADDRESS1 = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeAddress1()));
								query.append("', PAYOFFICE_ADDRESS2 = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeAddress2()));
								query.append("', PAYOFFICE_CITY = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeCity()));
								query.append("', PAYOFFICE_COUNTRY = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeCountry()));
								query.append("', PAYOFFICE_ID = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeId()));
								query.append("', PAYOFFICE_NAME = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeName()));
								query.append("', PAYOFFICE_STATE = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeState()));
								query.append("', PAYOFFICE_ZIP = '" +  StringHandler.emptyIfNull(inputBean.getPayofficeZip()));
								query.append("', POLINE_QTY_SHIPPED_IN_SHIPMENT = " + inputBean.getPolineQtyShippedInShipment());			
								query.append(", SHIPMENT_ID = " + inputBean.getShipmentId());
								query.append(", SHIPTO_ADDRESS1 = '" +  StringHandler.emptyIfNull(inputBean.getShiptoAddress1()));
								query.append("', SHIPTO_ADDRESS2 = '" +  StringHandler.emptyIfNull(inputBean.getShiptoAddress2()));
								query.append("', SHIPTO_ADDRESS3 = '" +  StringHandler.emptyIfNull(inputBean.getShiptoAddress3()));
								query.append("', SHIPTO_CITY = '" +  StringHandler.emptyIfNull(inputBean.getShiptoCity()));
								query.append("', SHIPTO_COUNTRY = '" +  StringHandler.emptyIfNull(inputBean.getShiptoCountry()));
								query.append("', SHIPTO_PARTY_ID = '" +  StringHandler.emptyIfNull(inputBean.getShiptoPartyId()));
								query.append("', SHIPTO_PARTY_NAME = '" +  StringHandler.emptyIfNull(inputBean.getShiptoPartyName()));			
								query.append("', SHIPTO_STATE = '" +  StringHandler.emptyIfNull(inputBean.getShiptoState()));
								query.append("', SHIPTO_ZIP = '" +  StringHandler.emptyIfNull(inputBean.getShiptoZip()));
								query.append("', SHIP_DATE = " +  DateHandler.getOracleToDateFunction(inputBean.getShipDate()));
								query.append(", TRACKING_NUMBER = '" +  StringHandler.emptyIfNull(inputBean.getTrackingNumber()));
								query.append("', TRANSACTION_REF_NUM = '" +  StringHandler.emptyIfNull(inputBean.getTransactionRefNum()));
								query.append("', TRANSPORTATION_CONTROL_NUM = '" +  StringHandler.emptyIfNull(inputBean.getTransportationControlNum()));
								query.append("', USGOV_SHIPMENT_ID = '" +  StringHandler.emptyIfNull(inputBean.getUsgovShipmentId()));			
								
								if("ASNONORI".equalsIgnoreCase(inputBean.getSendOptions()))
								{
									query.append("', STATUS2 = 'NEW");
									query.append("', STATUS_DETAIL2 = '856 SEND AS NONORIGIN INSPEC");
									query.append("', date_sent2 = '");
								}
								else if("ASORI".equalsIgnoreCase(inputBean.getSendOptions()))
								{
									query.append("', status = 'NEW");
									query.append("', status_detail = '");
									query.append("', date_sent = '");
								}
								query.append("' where release_num = '"+inputBean.getReleaseNum()+"' AND asn_id = "+inputBean.getAsnId());
								query.append(" AND USGOV_SHIPMENT_ID = '"+inputBean.getOldUsgovShipmentId()).append("'");
								factory.deleteInsertUpdate(query.toString(),conn);
								factory.deleteInsertUpdate(new StringBuilder("update REQUEST_LINE_ITEM_EXTENSION set ship_to_dodaac = '").append(inputBean.getShiptoDodaac()).append("' WHERE company_id = 'USGOV' and trading_partner_id = 'DTDN' and release_num = '").append(inputBean.getReleaseNum()).append("' and pr_number = ").append(inputBean.getPrNumber()).toString(),conn);
							}
							catch (Exception e) {
								errorMsg = "Error updating outbound release_num "+inputBean.getReleaseNum();
								errorMessages.add(errorMsg);
							}
						}
						else
						{
							try {
								StringBuilder asnDetailQry = new StringBuilder("update customer.outbound_asn_detail SET box_id =  '"+ StringHandler.emptyIfNull(inputBean.getBoxId())+"',  box_rfid = '"+ StringHandler.emptyIfNull(inputBean.getBoxRfid()+"'")); 
								asnDetailQry.append(", PALLET_ID = '" +  StringHandler.emptyIfNull(inputBean.getPalletId()));
								asnDetailQry.append("', PALLET_RFID = '" +  StringHandler.emptyIfNull(inputBean.getPalletRfid()));
								asnDetailQry.append("', POLINE_QTY_SHIPPED_IN_BOX = '" +  StringHandler.emptyIfNull(inputBean.getPolineQtyShippedInBox()));
								asnDetailQry.append("', UNIT_PRICE = " + inputBean.getUnitPrice());
								asnDetailQry.append(", UOM = '" +  StringHandler.emptyIfNull(inputBean.getUom()));
								asnDetailQry.append("' WHERE asn_id = "+inputBean.getAsnId().toPlainString()+" and box_id = '"+inputBean.getOldBoxId() +"' and  box_rfid = '"+inputBean.getOldBoxRfid()+"'");
								factory.deleteInsertUpdate(asnDetailQry.toString(),conn);
							}
							
							catch (Exception e) {
								errorMsg = "Error updating outbound detail for release_num "+inputBean.getReleaseNum();
								errorMessages.add(errorMsg);
							}
						}
	
				}
			}
		}
		catch (Exception e) {
				errorMsg = "An error occured during update";
				errorMessages.add(errorMsg);
		}
		finally
		{
			dbManager.returnConnection(conn);
			conn = null;
			factory = null;
			dbManager = null;
		}

		return (errorMessages.size() > 0 ? errorMessages : null);
	}
	
	/**
	 * Retrieves invoices of an order based on prNumber and shipmentId
	 * @param prNumber
	 * @param shipmentId
	 * @return a string, if not empty, contains all invoices separated by a comma
	 * @throws Exception
	 */
	public String getInvoices(String prNumber, String shipmentId) throws Exception {
		String invoicesStr = "";
		if (prNumber != null && shipmentId != null) {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		
			try {
				StringBuilder query = new StringBuilder("SELECT fx_dla_invoice_number");
				query.append("(").append(prNumber).append(",").append(shipmentId).append(") FROM DUAL");
				invoicesStr = factory.selectSingleValue(query.toString());
			}catch (Exception e) {
				log.error(e);
				throw e;
			}
		}
				
		return invoicesStr;
	}
	
	/**
	 * Resubmit an invoice
	 * @param invoiceId
	 * @return a collection of generic error messages
	 * @throws Exception
	 */
	public Collection resubmitInvoice(String invoiceId) throws Exception {
		Vector<String> errorMsgs = new Vector<String>();
		String procedureName = "P_RESET_INVOICE_SUBMISSION";
		
		if (invoiceId != null && !invoiceId.isEmpty()) {
			try{
				DbManager dbManager = new DbManager(getClient(),this.getLocale());
				GenericSqlFactory factory = new GenericSqlFactory(dbManager);
				Vector<String> cin = new Vector<String>(1);
				cin.addElement(invoiceId);
				Collection<Integer> cout = new Vector<Integer>();
				cout.add(new Integer(java.sql.Types.VARCHAR));
				Vector<String> error = (Vector<String>) factory.doProcedure(procedureName, cin, cout);
				if(error.size()>0 && error.get(0) != null) {
			     	 String errorCode = error.get(0);
			     	 log.info("Error in Procedure " + procedureName + ": " + invoiceId + " Error Code " + errorCode + " ");
			     	errorMsgs.add(errorCode);
				}
			} catch(Exception e){
				errorMsgs.add("Error in resubmitting invoice " + invoiceId);
				log.error(e);
			}
		}
		
		return errorMsgs;
	}
 }

