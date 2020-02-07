package com.tcmis.client.api.process;

import com.tcmis.client.api.beans.EcommerceShoppingCartBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import org.json.JSONArray;
import org.json.JSONObject;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class EcommerceCheckoutProcess extends BaseProcess {
    private GenericSqlFactory factory = null;

    public EcommerceCheckoutProcess(String client) {
        super(client);
        factory = new GenericSqlFactory(new DbManager(getClient()));
    }

    public String getBrowserPostFromPayloadId(String payloadId)  {
        try {
            return factory.selectSingleValue("select browser_form_post from punchout_setup_request where payload_id = '" + payloadId + "'");
        } catch(Exception ex) { ex.printStackTrace() ; return "";}
    }

    public String sendShoppingCart(CatalogInputBean inputBean, Collection<ShoppingCartBean> shoppingCartBeans) throws Exception {
        BigDecimal totalShoppingCartPrice = new BigDecimal(0);
        String totalShoppingCartCurrency = "USD";
        JSONArray itemInJSONArray = new JSONArray();
        for(ShoppingCartBean shoppingCart: shoppingCartBeans) {
            if (shoppingCart.getCatalogPrice() != null)
                totalShoppingCartPrice = totalShoppingCartPrice.add(shoppingCart.getCatalogPrice());
            if (!StringHandler.isBlankString(shoppingCart.getCurrencyId()))
                totalShoppingCartCurrency = shoppingCart.getCurrencyId();
            getDetailJSONObject(inputBean,shoppingCart,itemInJSONArray);
        }

        JSONObject shoppingCartJSON = new JSONObject();
        shoppingCartJSON.put("Header",getHeaderJSONObject(inputBean.getPayloadId()));

        JSONObject punchOutOrderMessageHeaderJSON = new JSONObject();
        punchOutOrderMessageHeaderJSON.put("operationAllowed","create");
        JSONObject punchOutOrderMessageHeaderTotalJSON = new JSONObject();
        JSONObject punchOutOrderMessageHeaderTotalMoneyJSON = new JSONObject();
        punchOutOrderMessageHeaderTotalMoneyJSON.put("value",totalShoppingCartPrice.toString());
        punchOutOrderMessageHeaderTotalMoneyJSON.put("currency",totalShoppingCartCurrency);
        punchOutOrderMessageHeaderTotalJSON.put("Money",punchOutOrderMessageHeaderTotalMoneyJSON);
        punchOutOrderMessageHeaderJSON.put("Total",punchOutOrderMessageHeaderTotalJSON);

        JSONObject punchOutOrderMessageJSON = new JSONObject();
        punchOutOrderMessageJSON.put("BuyerCookie",inputBean.getBrowserCookie());
        punchOutOrderMessageJSON.put("PunchOutOrderMessageHeader",punchOutOrderMessageHeaderJSON);
        punchOutOrderMessageJSON.put("ItemIn",itemInJSONArray);

        JSONObject messageJSON = new JSONObject();
        messageJSON.put("PunchOutOrderMessage",punchOutOrderMessageJSON);

        shoppingCartJSON.put("Message",messageJSON);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss-hh:mm");
        Random random = new Random();
        shoppingCartJSON.put("payloadID",format.format(new Date())+random.nextInt()+"@www.tcmis.com");
        shoppingCartJSON.put("timestamp",format.format(new Date()));

        ResourceLibrary library = new ResourceLibrary("ecommerce");
        String[] responseData = NetHandler.getHttpPost(library.getString("send_shopping_cart_url"),shoppingCartJSON);
        if (!"200".equals(responseData[NetHandler.RESPONSE_CODE])) {
            log.debug("POST to WDI encountered error:"+responseData[NetHandler.RESPONSE_CODE]+" JSON:"+shoppingCartJSON.toString());
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Unable to send shopping cart to ecommerce system","Error while trying to send shopping cart: "+responseData[NetHandler.RESPONSE_MESSAGE]);
        }else {
            log.debug("Successfully POST to WDI:"+responseData[NetHandler.RESPONSE_CODE]+" JSON:"+shoppingCartJSON.toString());
        }
        return responseData[NetHandler.RESPONSE_MESSAGE];
    }

    private void getDetailJSONObject(CatalogInputBean inputBean, ShoppingCartBean shoppingCartBean, JSONArray itemInJSONArray) throws Exception {
        JSONObject itemInJSON = new JSONObject();
        JSONObject itemIDJSON = new JSONObject();
        itemIDJSON.put("SupplierPartID",shoppingCartBean.getCatPartNo());
        StringBuilder catalogData = new StringBuilder(shoppingCartBean.getCatalogCompanyId());
        catalogData.append("|").append(shoppingCartBean.getCatalogId()).append("|").append(shoppingCartBean.getPartGroupNo());
        catalogData.append("|").append(shoppingCartBean.getStockingMethod()).append("|").append(shoppingCartBean.getFacilityId());
        catalogData.append("|").append(shoppingCartBean.getApplication()).append("|").append(shoppingCartBean.getInventoryGroup());
        catalogData.append("|").append(shoppingCartBean.getCritical());
        itemIDJSON.put("SupplierPartAuxiliaryID",catalogData.toString());
        itemInJSON.put("ItemID",itemIDJSON);

        JSONObject itemDetailJSON = new JSONObject();
        JSONObject itemDetailUnitPriceJSON = new JSONObject();
        JSONObject itemDetailUnitPriceMoneyJSON = new JSONObject();
        itemDetailUnitPriceMoneyJSON.put("value",StringHandler.emptyIfNull(shoppingCartBean.getCatalogPrice()));
        String currencyId = "USD";
        if (!StringHandler.isBlankString(shoppingCartBean.getCurrencyId()))
            currencyId = shoppingCartBean.getCurrencyId();
        itemDetailUnitPriceMoneyJSON.put("currency",currencyId);
        itemDetailUnitPriceJSON.put("Money",itemDetailUnitPriceMoneyJSON);
        itemDetailJSON.put("UnitPrice",itemDetailUnitPriceJSON);

        JSONObject itemDescriptionJSON = new JSONObject();
        String partDescription = "";
        if ("true".equals(shoppingCartBean.getCritical()))
            partDescription = "*Critical* "+shoppingCartBean.getPartDescription();
        else
            partDescription = shoppingCartBean.getPartDescription();
        itemDescriptionJSON.put("value",partDescription);
        itemDescriptionJSON.put("lang",inputBean.getEcommerceLanguage());
        itemDetailJSON.put("Description",itemDescriptionJSON);

        itemDetailJSON.put("UnitOfMeasure","EA");

        EcommerceShoppingCartBean ecommerceShoppingCartBean = getAdditionalDataForShoppingCart(shoppingCartBean);
        if (ecommerceShoppingCartBean.getUnspsc() != null) {
            JSONObject itemDetailClassificationJSON = new JSONObject();
            itemDetailClassificationJSON.put("value", ecommerceShoppingCartBean.getUnspsc());
            itemDetailClassificationJSON.put("domain", "UNSPSC");
            itemDetailJSON.put("Classification", itemDetailClassificationJSON);
        }else
            itemDetailJSON.put("Classification", JSONObject.NULL);
        itemDetailJSON.put("ManufacturerName",StringHandler.emptyIfNull(ecommerceShoppingCartBean.getMfgDesc()));
        itemDetailJSON.put("LeadTime","");
        itemInJSON.put("ItemDetail",itemDetailJSON);

        JSONObject supplierJSON = new JSONObject();
        supplierJSON.put("value","");
        supplierJSON.put("domain","");
        itemInJSON.put("SupplierID",JSONObject.NULL);
        itemInJSON.put("quantity",shoppingCartBean.getQuantity());

        itemInJSONArray.put(itemInJSON);
    }

    private EcommerceShoppingCartBean getAdditionalDataForShoppingCart(ShoppingCartBean shoppingCartBean) throws Exception {
        StringBuilder query = new StringBuilder("select item.unspsc, mfg.mfg_desc from catalog_part_item_group cpig, item, part p, material m, manufacturer mfg");
        query.append(" where cpig.company_id = ").append(SqlHandler.delimitString(shoppingCartBean.getCatalogCompanyId()));
        query.append(" and cpig.catalog_id = ").append(SqlHandler.delimitString(shoppingCartBean.getCatalogId()));
        query.append(" and cpig.cat_part_no = ").append(SqlHandler.delimitString(shoppingCartBean.getCatPartNo()));
        query.append(" and cpig.part_group_no = ").append(shoppingCartBean.getPartGroupNo()).append(" and cpig.priority = 1 and cpig.status = 'A'");
        query.append(" and cpig.item_id = item.item_id and cpig.item_id = p.item_id and p.material_id = m.material_id and m.mfg_id = mfg.mfg_id");
        return (EcommerceShoppingCartBean) factory.selectSingleBean(query.toString(),EcommerceShoppingCartBean.class).orElse(null);
    }

    public JSONObject getHeaderJSONObject(String payloadId) throws Exception {
        JSONObject headerJSON = new JSONObject();
        JSONObject headerFromJSON = new JSONObject();
        JSONObject headerToJSON = new JSONObject();
        JSONObject headerSenderJSON = new JSONObject();
        JSONObject headerCredentialJSON = new JSONObject();
        JSONObject headerSenderCredentialJSON = new JSONObject();
        //from section
        headerCredentialJSON.put("Identity","");
        headerCredentialJSON.put("domain","");
        headerFromJSON.put("Credential",headerCredentialJSON);
        headerJSON.put("From",headerFromJSON);
        //to section
        headerToJSON.put("Credential",headerCredentialJSON);
        headerJSON.put("To",headerToJSON);
        //sender section
        headerSenderCredentialJSON.put("Identity","");
        headerSenderCredentialJSON.put("domain","");
        headerSenderCredentialJSON.put("SharedSecret","");
        headerSenderJSON.put("Credential",headerSenderCredentialJSON);
        if (!StringHandler.isBlankString(payloadId))
            headerSenderJSON.put("UserAgent",getBrowserPostFromPayloadId(payloadId));
        else
            headerSenderJSON.put("UserAgent","");
        headerJSON.put("Sender",headerSenderJSON);

        return headerJSON;
    }

}