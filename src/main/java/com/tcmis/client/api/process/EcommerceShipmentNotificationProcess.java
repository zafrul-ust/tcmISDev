package com.tcmis.client.api.process;

import com.tcmis.client.api.beans.EcommerceShipmentNotificationBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import radian.tcmis.common.util.BeanHandler;
import radian.tcmis.common.util.StringHandler;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

public class EcommerceShipmentNotificationProcess extends BaseProcess {
    private GenericSqlFactory factory = null;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss-hh:mm");

    public EcommerceShipmentNotificationProcess(String client) {
        super(client);
        factory = new GenericSqlFactory(new DbManager(getClient()));
    }

    public JSONArray getShipmentNotificationData(JSONObject input) throws Exception {
        JSONArray shipmentNotificationJSONArray = new JSONArray();

        EcommerceCheckoutProcess ecommerceCheckoutProcess = new EcommerceCheckoutProcess(this.getClient());
        JSONObject headerJSON = ecommerceCheckoutProcess.getHeaderJSONObject("");
        try {
            log.debug("Shipment Notification output JSON:"+input.toString());
            Iterator iter = getShipmentData(input).iterator();
            while (iter.hasNext()) {
                EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean = (EcommerceShipmentNotificationBean)iter.next();
                //
                JSONObject poJSON = new JSONObject();
                JSONObject requestJSON = new JSONObject();
                JSONObject shipNoticeRequestJSON = new JSONObject();
                JSONObject shipNoticeHeaderJSON = new JSONObject();
                JSONObject shipControlJSON = new JSONObject();
                JSONObject shipNoticePortionJSON = new JSONObject();
                JSONObject orderReferenceJSON = new JSONObject();
                JSONArray shipNoticeItemArray = new JSONArray();
                //header data
                poJSON.put("Header",headerJSON);
                //
                getShipNoticeHeader(shipNoticeHeaderJSON,ecommerceShipmentNotificationBean);
                shipNoticeRequestJSON.put("ShipNoticeHeader",shipNoticeHeaderJSON);
                //
                getShipControl(shipControlJSON);
                shipNoticeRequestJSON.put("ShipControl",shipControlJSON);
                //
                getOrderReference(orderReferenceJSON,ecommerceShipmentNotificationBean);
                shipNoticePortionJSON.put("OrderReference",orderReferenceJSON);
                //
                Iterator detailIter = ecommerceShipmentNotificationBean.getDetailData().iterator();
                while (detailIter.hasNext()) {
                    EcommerceShipmentNotificationBean detailBean = (EcommerceShipmentNotificationBean)detailIter.next();
                    getShipNoticeItem(shipNoticeItemArray,detailBean);
                }
                shipNoticePortionJSON.put("ShipNoticeItem",shipNoticeItemArray);
                shipNoticeRequestJSON.put("ShipNoticePortion",shipNoticePortionJSON);
                requestJSON.put("ShipNoticeRequest",shipNoticeRequestJSON);
                requestJSON.put("deploymentMode","");
                poJSON.put("Request",requestJSON);
                //
                String[] payloadTimestamp = StringUtils.split(ecommerceShipmentNotificationBean.getPayloadId(),"|");
                poJSON.put("payloadID",payloadTimestamp[0]);
                if (payloadTimestamp.length == 2)
                    poJSON.put("timestamp",payloadTimestamp[1]);
                else
                    poJSON.put("timestamp","");
                poJSON.put("lang","en-US");
                shipmentNotificationJSONArray.put(poJSON);
            }
        }catch (Exception e) {
            log.error("Get Shipment Notification Data",e);
        }
        log.debug("Shipment Notification output JSON:"+shipmentNotificationJSONArray.toString());
        return shipmentNotificationJSONArray;
    }

    private Vector getShipmentData (JSONObject input) throws Exception {
        Vector shipmentDataV = new Vector();
        String limitTimeFrame = input.optString("minuteSince","60");
        String prNumberString = StringHandler.emptyIfNull(input.optString("prNumbers"));
        StringBuilder query = new StringBuilder("select i.pr_number,i.line_item,i.quantity,i.source_hub,i.inventory_group,i.shipment_id,i.item_id,");
        query.append("rli.application,rli.ship_to_location_id,rli.fac_part_no,rli.po_number,rli.release_number,rli.payload_id,");
        query.append("rli.catalog_price,rli.currency_id,rli.release_date,pr.end_user,pr.contact_info,");
        query.append("l.location_desc,l.address_line_1,l.address_line_2,l.address_line_3,l.country_abbrev,l.state_abbrev,");
        query.append("l.city,l.zip,fi.part_description");
        query.append(" from issue i, request_line_item rli, purchase_request pr, location l, fac_item fi");
        query.append(" where ship_confirm_date > sysdate - ").append(limitTimeFrame).append("/24/60");
        if (!StringHandler.isBlankString(prNumberString))
            query.append(" and i.pr_number in (").append(prNumberString).append(")");
        query.append(" and rli.payload_id is not null and i.company_id = pr.company_id and i.pr_number = pr.pr_number");
        query.append(" and pr.company_id = rli.company_id and pr.pr_number = rli.pr_number");
        query.append(" and i.line_item = rli.line_item and rli.company_id = l.company_id");
        query.append(" and rli.ship_to_location_id = l.location_id and rli.catalog_company_id = fi.company_id");
        query.append(" and rli.catalog_id = fi.facility_id and rli.fac_part_no = fi.fac_part_no");
        query.append(" order by pr_number,to_number(line_item)");
        factory.setBeanObject(new EcommerceShipmentNotificationBean());
        Iterator iter = factory.selectQuery(query.toString()).iterator();
        String currentPoNumber = "";
        while (iter.hasNext()) {
            EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean = (EcommerceShipmentNotificationBean)iter.next();
            if (!currentPoNumber.equals(ecommerceShipmentNotificationBean.getPoNumber())) {
                EcommerceShipmentNotificationBean detailBean = new EcommerceShipmentNotificationBean();
                BeanHandler.copyAttributes(ecommerceShipmentNotificationBean,detailBean);
                Collection detailBeans = new Vector();
                detailBeans.add(detailBean);
                ecommerceShipmentNotificationBean.setDetailData(detailBeans);
                shipmentDataV.add(ecommerceShipmentNotificationBean);
            }else {
                EcommerceShipmentNotificationBean myBean = (EcommerceShipmentNotificationBean)shipmentDataV.lastElement();
                Collection detailBeans = myBean.getDetailData();
                detailBeans.add(ecommerceShipmentNotificationBean);
            }
            currentPoNumber = ecommerceShipmentNotificationBean.getPoNumber();
        }
        return shipmentDataV;
    }

    private void getShipNoticeItem(JSONArray shipNoticeItemArray, EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean) throws Exception {
        JSONObject itemsJSON = new JSONObject();
        //
        JSONObject itemIdJSON = new JSONObject();
        itemIdJSON.put("SupplierPartID",ecommerceShipmentNotificationBean.getFacPartNo());
        itemsJSON.put("ItemID",itemIdJSON);
        //
        JSONObject shipNoticeItemDetailJSON = new JSONObject();
        JSONObject unitPriceJSON = new JSONObject();
        JSONObject moneyJSON = new JSONObject();
        if (ecommerceShipmentNotificationBean.getCatalogPrice() != null)
            moneyJSON.put("value",ecommerceShipmentNotificationBean.getCatalogPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
        else
            moneyJSON.put("value","");
        moneyJSON.put("currency",ecommerceShipmentNotificationBean.getCurrencyId());
        unitPriceJSON.put("Money",moneyJSON);
        shipNoticeItemDetailJSON.put("UnitPrice",unitPriceJSON);
        //
        JSONObject descriptionJSON = new JSONObject();
        descriptionJSON.put("value",ecommerceShipmentNotificationBean.getPartDescription());
        descriptionJSON.put("lang","en-US");
        shipNoticeItemDetailJSON.put("Description",descriptionJSON);
        //
        shipNoticeItemDetailJSON.put("UnitOfMeasure","EA");
        itemsJSON.put("ShipNoticeItemDetail",shipNoticeItemDetailJSON);
        //
        itemsJSON.put("UnitOfMeasure","EA");
        itemsJSON.put("shipNoticeLineNumber",ecommerceShipmentNotificationBean.getReleaseNumber());
        itemsJSON.put("lineNumber",ecommerceShipmentNotificationBean.getReleaseNumber());
        itemsJSON.put("quantity",ecommerceShipmentNotificationBean.getQuantity());
        //
        shipNoticeItemArray.put(itemsJSON);
    }

    private void getOrderReference (JSONObject orderReferenceJSON, EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean) throws Exception {
        JSONObject documentReferenceJSON = new JSONObject();
        documentReferenceJSON.put("value","");
        documentReferenceJSON.put("payloadID",ecommerceShipmentNotificationBean.getPayloadId().substring(0,ecommerceShipmentNotificationBean.getPayloadId().indexOf("@")));
        orderReferenceJSON.put("DocumentReference",documentReferenceJSON);
        orderReferenceJSON.put("orderDate",format.format(ecommerceShipmentNotificationBean.getReleaseDate()));
        orderReferenceJSON.put("orderID",ecommerceShipmentNotificationBean.getPoNumber());
    }

    private void getShipControl(JSONObject shipControlJSON) throws Exception {
        JSONObject carrierIdentifier = new JSONObject();
        carrierIdentifier.put("value","");
        carrierIdentifier.put("domain","");
        shipControlJSON.put("CarrierIdentifier",carrierIdentifier);
        //
        JSONObject shipmentIdentifierJSON = new JSONObject();
        shipmentIdentifierJSON.put("value","");
        shipmentIdentifierJSON.put("trackingNumberDate","");
        shipControlJSON.put("ShipmentIdentifier",shipmentIdentifierJSON);
        //
        JSONObject transportInformationJSON = new JSONObject();
        transportInformationJSON.put("ShippingContractNumber","");
        JSONObject shippingInstructionJSON = new JSONObject();
        JSONObject descriptionJSON = new JSONObject();
        descriptionJSON.put("value","");
        descriptionJSON.put("lang","");
        shippingInstructionJSON.put("Description",descriptionJSON);
    }

    private void getShipNoticeHeader(JSONObject shipNoticeHeaderJSON, EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean) throws Exception {
        JSONArray contactJSONArray = new JSONArray();
        JSONObject sendFromJSON = new JSONObject();
        getSendFromData(sendFromJSON,ecommerceShipmentNotificationBean);
        contactJSONArray.put(sendFromJSON);
        JSONObject sendToJSON = new JSONObject();
        getSendToData(sendToJSON,ecommerceShipmentNotificationBean);
        contactJSONArray.put(sendToJSON);
        shipNoticeHeaderJSON.put("Contact",contactJSONArray);
        //
        JSONObject extrinsicJSON = new JSONObject();
        extrinsicJSON.put("value","");
        extrinsicJSON.put("name","");
        shipNoticeHeaderJSON.put("Extrinsic",extrinsicJSON);
        //
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss-hh:mm");
        shipNoticeHeaderJSON.put("noticeDate",format.format(new Date()));
        shipNoticeHeaderJSON.put("operation","notice");
        shipNoticeHeaderJSON.put("shipmentID",ecommerceShipmentNotificationBean.getShipmentId());
    }

    private void getSendFromData(JSONObject sendFromJSON, EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean) throws Exception {
        EcommerceShipmentNotificationBean hubAddressBean = getHubAddress(ecommerceShipmentNotificationBean.getSourceHub());
        //
        JSONObject nameJSON = new JSONObject();
        String toHeaderValue = "";
        nameJSON.put("value",hubAddressBean.getLocationDesc());
        nameJSON.put("lang","en");
        sendFromJSON.put("Name",nameJSON);
        //
        JSONObject postalAddressJSON = new JSONObject();
        getPostalJSON(hubAddressBean,postalAddressJSON,"");
        sendFromJSON.put("PostalAddress",postalAddressJSON);
        //
        sendFromJSON.put("role","shipFrom");
        sendFromJSON.put("addressID","");
    }

    private EcommerceShipmentNotificationBean getHubAddress(String sourceHub) throws Exception {
        EcommerceShipmentNotificationBean hubAddressBean = new EcommerceShipmentNotificationBean();
        DbManager dbManager = new DbManager("TCM_OPS");
        GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager, new EcommerceShipmentNotificationBean());
        StringBuilder query = new StringBuilder("select distinct l.location_desc,l.address_line_1,l.address_line_2,l.address_line_3,l.country_abbrev,l.state_abbrev,l.city,l.zip");
        query.append(" from location l, hub where hub.branch_plant = '").append(sourceHub).append("'");
        query.append(" and hub.hub_name = l.location_id");
        Iterator iter = genericSqlFactory.selectQuery(query.toString()).iterator();
        while (iter.hasNext()) {
            hubAddressBean = (EcommerceShipmentNotificationBean)iter.next();
            break;
        }
        return hubAddressBean;
    }

    private void getSendToData(JSONObject sendToJSON, EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean) throws Exception {
        //
        JSONObject nameJSON = new JSONObject();
        String toHeaderValue = "Rollins "+ecommerceShipmentNotificationBean.getApplication()+" PROCUREMENT SERVICES";
        nameJSON.put("value",toHeaderValue);
        nameJSON.put("lang","en");
        sendToJSON.put("Name",nameJSON);
        //
        JSONObject postalAddressJSON = new JSONObject();
        getPostalJSON(ecommerceShipmentNotificationBean,postalAddressJSON,toHeaderValue);
        sendToJSON.put("PostalAddress",postalAddressJSON);
        //
        sendToJSON.put("role","shipTo");
        sendToJSON.put("addressID",ecommerceShipmentNotificationBean.getApplication());
    }

    private void getPostalJSON(EcommerceShipmentNotificationBean ecommerceShipmentNotificationBean, JSONObject postalAddressJSON, String toHeaderValue) throws Exception {
        JSONArray deliverTo = new JSONArray();
        if (!StringHandler.isBlankString(toHeaderValue)) {
            deliverTo.put(StringHandler.emptyIfNull(ecommerceShipmentNotificationBean.getEndUser()));
            deliverTo.put(toHeaderValue);
        }
        postalAddressJSON.put("DeliverTo", deliverTo);
        //
        StringBuilder streetAddress = new StringBuilder("");
        if (!StringHandler.isBlankString(ecommerceShipmentNotificationBean.getAddressLine1()))
            streetAddress.append(ecommerceShipmentNotificationBean.getAddressLine1());
        if (!StringHandler.isBlankString(ecommerceShipmentNotificationBean.getAddressLine2())) {
            if (!StringHandler.isBlankString(streetAddress.toString()))
                streetAddress.append(" ");
            streetAddress.append(ecommerceShipmentNotificationBean.getAddressLine2());
        }
        if (!StringHandler.isBlankString(ecommerceShipmentNotificationBean.getAddressLine3())) {
            if (!StringHandler.isBlankString(streetAddress.toString()))
                streetAddress.append(" ");
            streetAddress.append(ecommerceShipmentNotificationBean.getAddressLine3());
        }
        postalAddressJSON.put("Street",streetAddress.toString().trim());
        postalAddressJSON.put("City",ecommerceShipmentNotificationBean.getCity());
        postalAddressJSON.put("State",ecommerceShipmentNotificationBean.getStateAbbrev());
        postalAddressJSON.put("PostalCode",ecommerceShipmentNotificationBean.getZip());
        //
        JSONObject countryJSON = new JSONObject();
        countryJSON.put("value","United States");
        countryJSON.put("isoCountryCode",ecommerceShipmentNotificationBean.getCountryAbbrev());
        postalAddressJSON.put("Country",countryJSON);
    }

}