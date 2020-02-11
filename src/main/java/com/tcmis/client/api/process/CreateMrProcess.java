package com.tcmis.client.api.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collection;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.client.api.beans.EcommerceShipmentNotificationBean;
import com.tcmis.client.api.factory.CreateMrDataMapper;
import com.tcmis.client.api.factory.ICreateMrDataMapper;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.LineItemViewBean;
import com.tcmis.client.common.beans.MaterialRequestHeaderViewBean;
import com.tcmis.client.common.beans.MaterialRequestInputBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.client.order.beans.RequestLineItemBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;

public class CreateMrProcess extends GenericProcess {

	private static final int CATALOG_COMPANY_ID = 0;
	private static final int FACILITY_ID = 4;
	private static final int WORKAREA = 5;
	private static final int CATALOG_ID = 1;
	private static final int PART_GROUP_NO = 2;
	private static final int STOCKING_METHOD = 3;
	private static final int INVENTORY_GROUP = 6;
	private static final int CRITICAL = 7;
	private static final char AUXILIARY_DELIMITER = '|';
	private static final char PAYLOAD_DELIMITER = '|';
	private static final String EXAMPLE_PACKAGING = "EA";
	
	public static final int PAYLOAD_ID = 0;
	public static final int TIMESTAMP = 1;
	
	public static final String NEW_ACTION = "new";
	public static final String UPDATE_ACTION = "update";
	public static final String DELETE_ACTION = "delete";
	public static final String CANCEL_ACTION = "cancel";
	
	private ICreateMrDataMapper database;
	private ResourceLibrary commonResources;
	
	public CreateMrProcess(String client, Locale locale) {
		super(client, locale);
		commonResources = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public CreateMrProcess(String client, Locale locale, ICreateMrDataMapper database) {
		super(client, locale);
		this.database = database;
	}
	
	private ICreateMrDataMapper getDatabase() {
		if (database == null) {
			this.database = new CreateMrDataMapper(dbManager);
		}
		return database;
	}
	
	public JSONObject transformJson(JSONObject json, String companyId) throws BaseException {
		JSONObject jsonOut = new JSONObject();
		try {
			JSONObject orderRequestHeader = json.getJSONObject("Request").getJSONObject("OrderRequest").getJSONObject("OrderRequestHeader");
			String addressId = Objects.toString(orderRequestHeader.getJSONObject("ShipTo").getJSONObject("Address").get("addressID"));
			jsonOut.put("personnelId", getDatabase().getPersonnelIdFromAddressId(addressId));
			jsonOut.put("version", json.getString("version"));
			jsonOut.put("lang", json.getString("lang"));
			
			String facilityId = null;
			String accountSysId = null;
			JSONArray mrLines = new JSONArray();
			JSONArray itemOutArray = json.getJSONObject("Request").getJSONObject("OrderRequest").getJSONArray("ItemOut");
			for (int i = 0; i < itemOutArray.length(); i++) {
				JSONObject itemOut = itemOutArray.getJSONObject(i);
				JSONObject mrLine = new JSONObject();
				
				String auxiliaryId = itemOut.getJSONObject("ItemID").getString("SupplierPartAuxiliaryID");
				String[] auxiliaryFields = StringUtils.split(auxiliaryId, AUXILIARY_DELIMITER);
				if (facilityId == null) {
					facilityId = auxiliaryFields[FACILITY_ID];
				}
				Optional<CatalogInputBean> prRules = getDatabase().getPrRulesByCompanyFacility(companyId, facilityId);
				if (accountSysId == null) {
					accountSysId = prRules.map(CatalogInputBean::getAccountSysId).orElse(null);
				}
				
				JSONObject itemDetail = itemOut.getJSONObject("ItemDetail");
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss-hh:mm");
				
				mrLine.put("lineItem", i+1);
				mrLine.put("poNumber", orderRequestHeader.getString("orderID"));
				mrLine.put("releaseNumber", String.valueOf(itemOut.getInt("lineNumber")));
				mrLine.put("dateNeeded", formatter.parse(orderRequestHeader.getString("orderDate")));
				mrLine.put("quantity", itemOut.getInt("quantity"));
				mrLine.put("catalogPrice", itemDetail.getJSONObject("UnitPrice").getJSONObject("Money").getDouble("value"));
				mrLine.put("baselinePrice", itemDetail.getJSONObject("UnitPrice").getJSONObject("Money").getDouble("value"));
				mrLine.put("currencyId", itemDetail.getJSONObject("UnitPrice").getJSONObject("Money").getString("currency"));
				mrLine.put("partDescription", itemDetail.getJSONObject("Description").getString("value"));
				mrLine.put("catalogCompanyId", auxiliaryFields[CATALOG_COMPANY_ID]);
				mrLine.put("facilityId", facilityId);
				mrLine.put("catalogId", auxiliaryFields[CATALOG_ID]);
				mrLine.put("critical", auxiliaryFields[CRITICAL].toLowerCase());
				mrLine.put("partGroupNo", auxiliaryFields[PART_GROUP_NO]);
				mrLine.put("stockingMethod", auxiliaryFields[STOCKING_METHOD]);
				mrLine.put("inventoryGroup", auxiliaryFields[INVENTORY_GROUP]);
				mrLine.put("chargeType", prRules.map(CatalogInputBean::getChargeType).orElse(""));
				mrLine.put("catPartNo", itemOut.getJSONObject("ItemID").getString("SupplierPartID"));
				mrLine.put("examplePackaging", EXAMPLE_PACKAGING);
				mrLine.put("application", auxiliaryFields[WORKAREA]);
				
				mrLines.put(mrLine);
			}
			jsonOut.put("mrLines", mrLines);
			
			JSONObject mrHeader = new JSONObject();
				mrHeader.put("accountSysId",  accountSysId==null?JSONObject.NULL:accountSysId);
				mrHeader.put("payloadId", json.getString("payloadID")+PAYLOAD_DELIMITER+json.getString("timestamp"));
				mrHeader.put("companyId", companyId);
				mrHeader.put("facilityId", facilityId);
				JSONObject shipToAddress = orderRequestHeader.getJSONObject("ShipTo").getJSONObject("Address");
				mrHeader.put("endUser", shipToAddress.getJSONObject("PostalAddress").getJSONArray("DeliverTo").get(0));
				mrHeader.put("contactInfo", shipToAddress.getJSONObject("Email").getString("value"));
				mrHeader.put("operation", orderRequestHeader.get("type"));
				mrHeader.put("deploymentMode",json.getJSONObject("Request").getString("deploymentMode"));
			jsonOut.put("mrHeader", mrHeader);
		} catch(JSONException e) {
			throw new BaseException(e);
		} catch(ParseException e) {
			throw new BaseException(e);
		}
		return jsonOut;
	}
	
	public JSONObject createMr(JSONObject json) {
		JSONObject confirmation = null;
		try {
			BigDecimal personnelId = new BigDecimal(json.getInt("personnelId"));
			
			JSONObject header = json.getJSONObject("mrHeader");
			CatalogInputBean inputBean = (CatalogInputBean)BeanHandler.getJsonBeans(header, new CatalogInputBean(), this.getLocaleObject());
			
			JSONArray mrLines = json.getJSONArray("mrLines");
			List<ShoppingCartBean> cartLineList = new ArrayList<>();
			if (mrLines.length() == 0) {
				throw new BaseException("Need at least one MR line");
			}
			for (int i = 0; i < mrLines.length(); i++) {
				JSONObject order = mrLines.getJSONObject(i);
				ShoppingCartBean cartBean = (ShoppingCartBean)BeanHandler.getJsonBeans(order, new ShoppingCartBean(), this.getLocaleObject());
				cartBean.setDateNeeded((Date)order.get("dateNeeded"));
				cartLineList.add(cartBean);
			}
			ShoppingCartProcess process = new ShoppingCartProcess(this.getClient(), this.getLocale());
			BigDecimal prNumber = process.buildNewRequest(inputBean,cartLineList, personnelId);
			confirmation = buildConfirmation(json, prNumber);
			
			MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(), this.getLocale());
	    	MaterialRequestInputBean mrInputBean = new MaterialRequestInputBean();
	    	BeanHandler.copyAttributes(inputBean, mrInputBean);
	    	mrInputBean.setPrNumber(prNumber);
	    	mrInputBean.setEndUser(header.getString("endUser"));
	    	mrInputBean.setContactInfo(header.getString("contactInfo"));
	    	
	    	LoginProcess loginProcess = new LoginProcess(this.getClient());
	    	PersonnelBean personnelBean = new PersonnelBean();
	    	personnelBean.setPersonnelId(json.getInt("personnelId"));
	    	personnelBean.setClient(this.getClient());
	    	personnelBean = loginProcess.loginWeb(personnelBean, false);
	    	MaterialRequestHeaderViewBean mrData = mrProcess.getMrData(mrInputBean, personnelBean);
			List<MaterialRequestInputBean> mrLineList = streamRawtypeCollection(mrData.getLineItemColl(), LineItemViewBean.class)
	    			.flatMap(this::copyLineItemToMaterialRequestInputBean)
	    			.collect(Collectors.toList());
			String errorMsg = mrProcess.submitMr(mrInputBean,mrLineList,personnelBean);
			if ( ! "OK".equals(errorMsg)) {
				throw new BaseException(errorMsg);
			}
		} catch(Exception e) {
			try {
				confirmation = buildConfirmation(json, null);
			} catch(BaseException e2) {
				throw new RuntimeException(e2);
			}
		}
		return confirmation;
	}
	
	public void updateMr(JSONObject json) throws BaseException {
		Connection conn = null;
		try {
			MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(), this.getLocale());
			
			JSONObject mrHeader = json.getJSONObject("mrHeader");
			String companyId = mrHeader.getString("companyId");
			JSONArray lines = json.getJSONArray("mrLines");

			conn = this.dbManager.getConnection();
			for (int i = 0; i < lines.length(); i++) {
				JSONObject line = lines.getJSONObject(i);
				Optional<RequestLineItemBean> rliWrap = getDatabase().getPrLineFromPoLine(
						line.getString("poNumber"),
						line.getString("releaseNumber"),
						companyId,
						mrHeader.getString("facilityId"),
						line.getString("catalogCompanyId"),
						line.getString("catalogId"));
				
				if (rliWrap.isPresent()) {
					RequestLineItemBean rli = rliWrap.get();
					if (CANCEL_ACTION.equals(mrHeader.getString("operation")) ||
							DELETE_ACTION.equals(mrHeader.getString("operation"))) {
						BigDecimal personnelId = new BigDecimal(json.getInt("personnelId"));
						rli.setCancelRequester(personnelId);
						getDatabase().cancelMrLine(rli);
					}
					else if (UPDATE_ACTION.equals(mrHeader.getString("operation"))) {
						rli.setQuantity(new BigDecimal(line.getInt("quantity")));
						getDatabase().updateMrLine(rli);
					}
					mrProcess.setFactoryConnection(this.factory, conn);
					mrProcess.allocateLineItem(rli.getPrNumber(), rli.getLineItem(), companyId, companyId);
				}
			}
		} catch(Exception e) {
			log.info(e);
			throw new BaseException(e);
		} finally {
			this.dbManager.returnConnection(conn);
		}
	}
	
	private JSONObject buildConfirmation(JSONObject requestBody, BigDecimal prNumber) throws BaseException {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss-hh:mm");
			RequestLineItemBean mrData = prNumber==null?null:getDatabase().getRequestLineItemByPrNumber(prNumber);
			String[] payloadTs = getPayloadTs(mrData, requestBody);
			String confirmationType = mrData == null?"reject":"accept";
			Random random = new Random();
			JSONObject confirmation = new JSONObject() {{
				this.put("payloadID", format.format(new Date())+random.nextInt()+"@www.tcmis.com");
				this.put("timestamp", format.format(new Date()));
				this.put("version", requestBody.getString("version"));
				this.put("lang", requestBody.getString("lang"));
				this.put("Header", new JSONObject() {{
					this.put("From", new JSONObject() {{
						this.put("Credential", new JSONObject() {{
							this.put("Identity", "");
							this.put("domain", "");
						}});
					}});
					this.put("To", new JSONObject() {{
						this.put("Credential", new JSONObject() {{
							this.put("Identity", "");
							this.put("domain", "");
						}});
					}});
					this.put("Sender", new JSONObject() {{
						this.put("UserAgent", "");
						this.put("Credential", new JSONObject() {{
							this.put("Identity", "");
							this.put("domain", "");
						}});
					}});
				}});
				this.put("Request", new JSONObject() {{
					this.put("deploymentMode", requestBody.getJSONObject("mrHeader").getString("deploymentMode"));
					this.put("ConfirmationRequest", new JSONObject() {{
						this.put("ConfirmationHeader", new JSONObject() {{
							this.put("value", "");
							this.put("noticeDate", format.format(new Date()));
							this.put("type", confirmationType);
							this.put("operation", requestBody.getJSONObject("mrHeader").getString("operation"));
							this.put("confirmID", mrData==null?"":mrData.getPrNumber().intValue());
						}});
						this.put("OrderReference", new JSONObject() {{
							this.put("orderDate", JSONObject.NULL);
							this.put("orderID", mrData==null?"":mrData.getPoNumber());
							this.put("DocumentReference", new JSONObject() {{
								this.put("value", "");
								this.put("payloadID", payloadTs[PAYLOAD_ID]);
							}});
						}});
					}});
				}});
			}};
			return confirmation;
		} catch(JSONException e) {
			log.error(String.format("Error: Unable to generate Order Confirmation request body: Cause - %s; Message - %s", e.getCause(), e.getMessage()));
			throw new BaseException(e);
		}
	}
	
	public String[] getPayloadTs(RequestLineItemBean mrData, JSONObject requestBody) throws JSONException {
		String[] payloadTs = null;
		if (mrData == null) {
			payloadTs = StringUtils.split(requestBody.getJSONObject("mrHeader").getString("payloadId"),PAYLOAD_DELIMITER);
		}
		else {
			payloadTs = StringUtils.split(mrData.getPayloadId(),PAYLOAD_DELIMITER);
		}
		return payloadTs;
	}
	
	@SuppressWarnings("unchecked")
	private <T> Stream<T> streamRawtypeCollection(@SuppressWarnings("rawtypes") Collection coll, Class<T> type) {
		return coll.stream().map(type::cast);
	}
	
	private Stream<MaterialRequestInputBean> copyLineItemToMaterialRequestInputBean(LineItemViewBean origin) {
		MaterialRequestInputBean dest = new MaterialRequestInputBean();
		try {
			BeanHandler.copyAttributes(origin, dest);
			dest.setQty(origin.getQuantity());
			dest.setCritical(Boolean.toString("Y".equalsIgnoreCase(dest.getCritical())));
		} catch(BeanCopyException e) {
			log.error(String.format("Error while copying attributes; Cause - %s; Message - %s", e.getCause(), e.getMessage()));
			log.error(e);
			return Stream.empty();
		}
		return Stream.of(dest);
	}
	
	public void confirmOrder(JSONObject confirmationRequest, Throwable ex) {
		try {
			ResourceLibrary library = new ResourceLibrary("ecommerce");
			String confirmOrderUrl = library.getString("confirm_order_url");
			StringBuilder requestDetails = new StringBuilder("Posting Order Confirmation\n")
					.append("URL: ").append(confirmOrderUrl).append("\n")
					.append("Request Body: ").append(confirmationRequest.toString(4));
			log.debug(requestDetails.toString());
			
			int httpMessage = NetHandler.sendHttpPost(confirmOrderUrl, confirmationRequest);
			if (httpMessage != 200) {
				log.debug("Failed to POST to WDI");
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Unable to send order confirmation to ecommerce system",
						"Error while trying to send order confirmation.\n"
								+ "Status Code: " + httpMessage + "\n"
								+ "Request Body: " + confirmationRequest.toString(2));
			}
			else {
				log.debug("Successfully POST to WDI");
				BigDecimal prNumber = new BigDecimal(confirmationRequest
						.getJSONObject("Request")
						.getJSONObject("ConfirmationRequest")
						.getJSONObject("ConfirmationHeader")
						.getInt("confirmID"));

				if (getDatabase().isSendOrderConfirmationEmail(prNumber)) {
					EcommerceShipmentNotificationBean request = getDatabase().getRequestByPrNumber(prNumber);
					Collection<EcommerceShipmentNotificationBean> requestLines = getDatabase().getRequestLinesByPrNumber(prNumber);
					sendOrderConfirmationEmail(request, requestLines);
				}
			}
		} catch(Exception e) {
			log.info("Exceptional completion");
			log.info(e);
		}
	}
	
	private void sendOrderConfirmationEmail(EcommerceShipmentNotificationBean request, Collection<EcommerceShipmentNotificationBean> requestLines) {
		StringBuilder lines = new StringBuilder();
		for (EcommerceShipmentNotificationBean rli : requestLines) {
			lines.append("\n").append(commonResources.getString("label.lineitem"))
					.append(" : ").append(rli.getLineItem())
					.append("\n").append(commonResources.getString("label.partnumber"))
					.append(" : ").append(rli.getFacPartNo())
					.append("\n").append(commonResources.getString("label.partdescription"))
					.append(" : ").append(rli.getPartDescription())
					.append("\n").append(commonResources.getString("label.quantity"))
					.append(": ").append(rli.getQuantity()).append("\n");
		}

		StringBuilder body = new StringBuilder(commonResources.getString("label.requestcreated"))
				.append("\n\n").append(commonResources.getString("label.requestor"))
				.append(" : ").append(request.getEndUser())
				.append("\n").append(commonResources.getString("label.email"))
				.append(" : ").append(request.getContactInfo())
				.append("\n").append(commonResources.getString("label.po"))
				.append(" : ").append(request.getPoNumber())
				.append("\n\n").append(commonResources.getString("label.materialrequest"))
				.append(" : ").append(request.getPrNumber())
				.append(lines);
		
		StringBuilder subject = new StringBuilder()
				.append(commonResources.format("label.orderconfirmed", 
						request.getPrNumber(), 
						request.getApplicationDesc(), 
						request.getEndUser(),
						request.getPoNumber()));
		
		String recipient = request.getContactInfo();
		
		MailProcess.sendEmail(recipient, null, "deverror@tcmis.com", subject.toString(), body.toString());
	}
}
