package com.tcmis.client.api.process;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	private static final int PAYLOAD_ID = 0;
	private static final int TIMESTAMP = 1;
	
	private ICreateMrDataMapper database;
	
	public CreateMrProcess(String client, Locale locale) {
		super(client, locale);
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
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				
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
			database.setEndUserContactInfo(prNumber, header.getString("endUser"), header.getString("contactInfo"));
			confirmation = buildConfirmation(json, prNumber);
			
			MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(), this.getLocale());
	    	MaterialRequestInputBean mrInputBean = new MaterialRequestInputBean();
	    	BeanHandler.copyAttributes(inputBean, mrInputBean);
	    	mrInputBean.setPrNumber(prNumber);
	    	
	    	LoginProcess loginProcess = new LoginProcess(this.getClient());
	    	PersonnelBean personnelBean = new PersonnelBean();
	    	personnelBean.setPersonnelId(json.getInt("personnelId"));
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
	
	private JSONObject buildConfirmation(JSONObject requestBody, BigDecimal prNumber) throws BaseException {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			RequestLineItemBean mrData = prNumber==null?null:getDatabase().getRequestLineItemByPrNumber(prNumber);
			String[] payloadTs = getPayloadTs(mrData, requestBody);
			String confirmationType = mrData == null?"reject":"accept";
			
			JSONObject confirmation = new JSONObject() {{
				this.put("payloadID", payloadTs[PAYLOAD_ID]);
				this.put("timestamp", payloadTs[TIMESTAMP]);
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
					this.put("deploymentMode", "");
					this.put("ConfirmationRequest", new JSONObject() {{
						this.put("ConfirmationHeader", new JSONObject() {{
							this.put("value", "");
							this.put("noticeDate", format.format(new Date()));
							this.put("type", confirmationType);
							this.put("operation", requestBody.getJSONObject("mrHeader").getString("operation"));
							this.put("confirmID", mrData==null?"":mrData.getPrNumber().intValue());
						}});
						this.put("OrderReference", new JSONObject() {{
							this.put("orderDate", mrData==null?"":mrData.getRequiredDatetime());
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
	
	private String[] getPayloadTs(RequestLineItemBean mrData, JSONObject requestBody) throws JSONException {
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
			int httpMessage = NetHandler.sendHttpsPost(library.getString("confirm_order_url"), null, null, confirmationRequest.toString(), null);
			if (httpMessage != 200) {
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Unable to send order confirmation to ecommerce system",
						"Error while trying to send order confirmation.\n"
								+ "Status Code: " + httpMessage + "\n"
								+ "Request Body: " + confirmationRequest.toString(2));
			}
			else {
				log.debug("Successfully POST to WDI");
			}
		} catch(Exception e) {
			log.info("Exceptional completion");
			log.info(e);
		}
	}
}
