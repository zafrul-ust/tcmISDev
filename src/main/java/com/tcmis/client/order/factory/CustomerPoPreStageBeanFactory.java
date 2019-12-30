package com.tcmis.client.order.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.order.beans.CustomerPoPreStageBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;

/******************************************************************************
 * CLASSNAME: CustomerPoPreStageBeanFactory <br>
 * @version: 1.0, Aug 23, 2005 <br>
 *****************************************************************************/

public class CustomerPoPreStageBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
  public String ATTRIBUTE_CUSTOMER_PO_LINE_NO = "CUSTOMER_PO_LINE_NO";
  public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
  public String ATTRIBUTE_LINE_SEQUENCE = "LINE_SEQUENCE";
  public String ATTRIBUTE_CHANGE_ORDER_SEQUENCE = "CHANGE_ORDER_SEQUENCE";
  public String ATTRIBUTE_QUANTITY = "QUANTITY";
  public String ATTRIBUTE_QUANTITY_LEFT = "QUANTITY_LEFT";
  public String ATTRIBUTE_UOM = "UOM";
  public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
  public String ATTRIBUTE_DATE_ISSUED = "DATE_ISSUED";
  public String ATTRIBUTE_HAAS_ITEM_NO = "HAAS_ITEM_NO";
  public String ATTRIBUTE_MANUFACTURER_PART_NUM = "MANUFACTURER_PART_NUM";
  public String ATTRIBUTE_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
  public String ATTRIBUTE_CUSTOMER_PO_LINE_NOTE = "CUSTOMER_PO_LINE_NOTE";
  public String ATTRIBUTE_REQUESTED_DELIVERY_DATE = "REQUESTED_DELIVERY_DATE";
  public String ATTRIBUTE_ESTIMATED_DOCK_DATE = "ESTIMATED_DOCK_DATE";
  public String ATTRIBUTE_TOTAL_AMOUNT_ON_PO = "TOTAL_AMOUNT_ON_PO";
  public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";
  public String ATTRIBUTE_UNIT_PRICE_CODE = "UNIT_PRICE_CODE";
  public String ATTRIBUTE_ACCEPTANCE_CODE = "ACCEPTANCE_CODE";
  public String ATTRIBUTE_CHANGE_TYPE_CODE = "CHANGE_TYPE_CODE";
  public String ATTRIBUTE_CHANGE_SUB_TYPE_CODE = "CHANGE_SUB_TYPE_CODE";
  public String ATTRIBUTE_BUYER_PHONE = "BUYER_PHONE";
  public String ATTRIBUTE_BUYER_FAX = "BUYER_FAX";
  public String ATTRIBUTE_BUYER_EMAIL = "BUYER_EMAIL";
  public String ATTRIBUTE_DOCUMENT_CONTROL_NUMBER = "DOCUMENT_CONTROL_NUMBER";
  public String ATTRIBUTE_TRANSPORT = "TRANSPORT";
  public String ATTRIBUTE_TRANSPORTER_ACCOUNT = "TRANSPORTER_ACCOUNT";
  public String ATTRIBUTE_TRADING_PARTNER = "TRADING_PARTNER";
  public String ATTRIBUTE_TRADING_PARTNER_ID = "TRADING_PARTNER_ID";
  public String ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID = "CUSTOMER_HAAS_CONTRACT_ID";
  public String ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO = "CUSTOMER_HAAS_ACCOUNT_NO";
  public String ATTRIBUTE_CUSTOMER_PAYMENT_TERMS = "CUSTOMER_PAYMENT_TERMS";
  public String ATTRIBUTE_CUSTOMER_PO_NOTE = "CUSTOMER_PO_NOTE";
  public String ATTRIBUTE_BUYER_NAME_ON_PO = "BUYER_NAME_ON_PO";
  public String ATTRIBUTE_BUYER_ADDRESS1 = "BUYER_ADDRESS1";
  public String ATTRIBUTE_BUYER_ADDRESS2 = "BUYER_ADDRESS2";
  public String ATTRIBUTE_BUYER_CITY = "BUYER_CITY";
  public String ATTRIBUTE_BUYER_STATE = "BUYER_STATE";
  public String ATTRIBUTE_BUYER_ZIP = "BUYER_ZIP";
  public String ATTRIBUTE_BUYER_COUNTRY = "BUYER_COUNTRY";
  public String ATTRIBUTE_SELLER_NAME_ON_PO = "SELLER_NAME_ON_PO";
  public String ATTRIBUTE_SELLER_ID_ON_PO = "SELLER_ID_ON_PO";
  public String ATTRIBUTE_SELLER_ADDRESS1 = "SELLER_ADDRESS1";
  public String ATTRIBUTE_SELLER_ADDRESS2 = "SELLER_ADDRESS2";
  public String ATTRIBUTE_SELLER_CITY = "SELLER_CITY";
  public String ATTRIBUTE_SELLER_STATE = "SELLER_STATE";
  public String ATTRIBUTE_SELLER_ZIP = "SELLER_ZIP";
  public String ATTRIBUTE_SELLER_COUNTRY = "SELLER_COUNTRY";
  public String ATTRIBUTE_SHIPTO_PARTY_NAME = "SHIPTO_PARTY_NAME";
  public String ATTRIBUTE_SHIPTO_PARTY_ID = "SHIPTO_PARTY_ID";
  public String ATTRIBUTE_SHIPTO_ADDRESS1 = "SHIPTO_ADDRESS1";
  public String ATTRIBUTE_SHIPTO_ADDRESS2 = "SHIPTO_ADDRESS2";
  public String ATTRIBUTE_SHIPTO_ADDRESS3 = "SHIPTO_ADDRESS3";
  public String ATTRIBUTE_SHIPTO_CITY = "SHIPTO_CITY";
  public String ATTRIBUTE_SHIPTO_STATE = "SHIPTO_STATE";
  public String ATTRIBUTE_SHIPTO_ZIP = "SHIPTO_ZIP";
  public String ATTRIBUTE_SHIPTO_COUNTRY = "SHIPTO_COUNTRY";
  public String ATTRIBUTE_BILLTO_PARTY = "BILLTO_PARTY";
  public String ATTRIBUTE_BILLTO_PARTY_ID = "BILLTO_PARTY_ID";
  public String ATTRIBUTE_BILLTO_NAME2 = "BILLTO_NAME2";
  public String ATTRIBUTE_BILLTO_ADDRESS1 = "BILLTO_ADDRESS1";
  public String ATTRIBUTE_BILLTO_ADDRESS2 = "BILLTO_ADDRESS2";
  public String ATTRIBUTE_BILLTO_ADDRESS3 = "BILLTO_ADDRESS3";
  public String ATTRIBUTE_BILLTO_CITY = "BILLTO_CITY";
  public String ATTRIBUTE_BILLTO_STATE = "BILLTO_STATE";
  public String ATTRIBUTE_BILLTO_ZIP = "BILLTO_ZIP";
  public String ATTRIBUTE_BILLTO_COUNTRY = "BILLTO_COUNTRY";
  public String ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER = "INTERCHANGE_CONTROL_NUMBER";
  public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
  public String ATTRIBUTE_LOAD_ID = "LOAD_ID";
  public String ATTRIBUTE_LOAD_LINE = "LOAD_LINE";
  public String ATTRIBUTE_SCHEDULE_QUANTITY = "SCHEDULE_QUANTITY";
  public String ATTRIBUTE_SCHEDULE_UOM = "SCHEDULE_UOM";
  public String ATTRIBUTE_PO_TYPE_CODE = "PO_TYPE_CODE";
  public String ATTRIBUTE_PROCESSING_COMMENTS = "PROCESSING_COMMENTS";
  public String ATTRIBUTE_PART_SHORT_DESC = "PART_SHORT_DESC";
  public String ATTRIBUTE_PRE_860 = "PRE_860";
  public String ATTRIBUTE_ORIGINAL_QTY = "ORIGINAL_QTY";
  public String ATTRIBUTE_ORIGINAL_UOM = "ORIGINAL_UOM";
  public String ATTRIBUTE_DELIVERY_TYPE_CODE = "DELIVERY_TYPE_CODE";
  public String ATTRIBUTE_BUYER_ID_ON_PO = "BUYER_ID_ON_PO";
  public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
  public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
  public String ATTRIBUTE_FREIGHT_ON_BOARD_NOTES = "FREIGHT_ON_BOARD_NOTES";
  public String ATTRIBUTE_PAYMENT_TERMS_NOTES = "PAYMENT_TERMS_NOTES";
  public String ATTRIBUTE_BUYER_PARTY_NAME = "BUYER_PARTY_NAME";
  public String ATTRIBUTE_SELLER_PARTY_NAME = "SELLER_PARTY_NAME";
  public String ATTRIBUTE_SHIPTO_CONTACT_NAME = "SHIPTO_CONTACT_NAME";
  public String ATTRIBUTE_PRICE_BASIS_UOM = "PRICE_BASIS_UOM";
  public String ATTRIBUTE_PRICE_BASIS_QUANTITY = "PRICE_BASIS_QUANTITY";
  public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
  public String ATTRIBUTE_PARTIAL_SHIPMENT = "PARTIAL_SHIPMENT";
  public String ATTRIBUTE_BUYER_REGION = "BUYER_REGION";
  public String ATTRIBUTE_SELLER_REGION = "SELLER_REGION";
  public String ATTRIBUTE_BILLTO_REGION = "BILLTO_REGION";
  public String ATTRIBUTE_SHIPTO_REGION = "SHIPTO_REGION";
  public String ATTRIBUTE_PO_UPDATE_DATE = "PO_UPDATE_DATE";
  public String ATTRIBUTE_CARRIER = "CARRIER";
  public String ATTRIBUTE_ORIGINAL_DATE_ISSUED = "ORIGINAL_DATE_ISSUED";
  public String ATTRIBUTE_SHIPTO_PHONE = "SHIPTO_PHONE";
  public String ATTRIBUTE_SHIPTO_EMAIL = "SHIPTO_EMAIL";
  public String ATTRIBUTE_DELIVERY_NOTE = "DELIVERY_NOTE";
  public String ATTRIBUTE_RELEASE_NUM = "RELEASE_NUM";
  public String ATTRIBUTE_CUSTOMER_ORDER_TYPE = "CUSTOMER_ORDER_TYPE";
  public String ATTRIBUTE_CUSTOMER_PICKUP = "CUSTOMER_PICKUP";
  public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
  public String ATTRIBUTE_SHIPPING_NOTES = "SHIPPING_NOTES";
  public String ATTRIBUTE_CHARGE_NUMBER1 = "CHARGE_NUMBER1";
  public String ATTRIBUTE_CHARGE_NUMBER2 = "CHARGE_NUMBER2";
  public String ATTRIBUTE_CHARGE_NUMBER3 = "CHARGE_NUMBER3";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_OWNER_SEGMENT_ID = "OWNER_SEGMENT_ID";

  //table name
  public String TABLE = "CUSTOMER_PO_PRE_STAGE";

  //constructor
  public CustomerPoPreStageBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("customerPoNo")) {
      return ATTRIBUTE_CUSTOMER_PO_NO;
    }
    else if (attributeName.equals("customerPoLineNo")) {
      return ATTRIBUTE_CUSTOMER_PO_LINE_NO;
    }
    else if (attributeName.equals("transactionType")) {
      return ATTRIBUTE_TRANSACTION_TYPE;
    }
    else if (attributeName.equals("lineSequence")) {
      return ATTRIBUTE_LINE_SEQUENCE;
    }
    else if (attributeName.equals("changeOrderSequence")) {
      return ATTRIBUTE_CHANGE_ORDER_SEQUENCE;
    }
    else if (attributeName.equals("quantity")) {
      return ATTRIBUTE_QUANTITY;
    }
    else if (attributeName.equals("quantityLeft")) {
      return ATTRIBUTE_QUANTITY_LEFT;
    }
    else if (attributeName.equals("uom")) {
      return ATTRIBUTE_UOM;
    }
    else if (attributeName.equals("unitPrice")) {
      return ATTRIBUTE_UNIT_PRICE;
    }
    else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    }
    else if (attributeName.equals("catalogId")) {
      return ATTRIBUTE_CATALOG_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("partGroupNo")) {
      return ATTRIBUTE_PART_GROUP_NO;
    }
    else if (attributeName.equals("dateIssued")) {
      return ATTRIBUTE_DATE_ISSUED;
    }
    else if (attributeName.equals("haasItemNo")) {
      return ATTRIBUTE_HAAS_ITEM_NO;
    }
    else if (attributeName.equals("manufacturerPartNum")) {
      return ATTRIBUTE_MANUFACTURER_PART_NUM;
    }
    else if (attributeName.equals("itemDescription")) {
      return ATTRIBUTE_ITEM_DESCRIPTION;
    }
    else if (attributeName.equals("customerPoLineNote")) {
      return ATTRIBUTE_CUSTOMER_PO_LINE_NOTE;
    }
    else if (attributeName.equals("requestedDeliveryDate")) {
      return ATTRIBUTE_REQUESTED_DELIVERY_DATE;
    }
    else if (attributeName.equals("estimatedDockDate")) {
      return ATTRIBUTE_ESTIMATED_DOCK_DATE;
    }
    else if (attributeName.equals("totalAmountOnPo")) {
      return ATTRIBUTE_TOTAL_AMOUNT_ON_PO;
    }
    else if (attributeName.equals("dateInserted")) {
      return ATTRIBUTE_DATE_INSERTED;
    }
    else if (attributeName.equals("unitPriceCode")) {
      return ATTRIBUTE_UNIT_PRICE_CODE;
    }
    else if (attributeName.equals("acceptanceCode")) {
      return ATTRIBUTE_ACCEPTANCE_CODE;
    }
    else if (attributeName.equals("changeTypeCode")) {
      return ATTRIBUTE_CHANGE_TYPE_CODE;
    }
    else if (attributeName.equals("changeSubTypeCode")) {
      return ATTRIBUTE_CHANGE_SUB_TYPE_CODE;
    }
    else if (attributeName.equals("buyerPhone")) {
      return ATTRIBUTE_BUYER_PHONE;
    }
    else if (attributeName.equals("buyerFax")) {
      return ATTRIBUTE_BUYER_FAX;
    }
    else if (attributeName.equals("buyerEmail")) {
        return ATTRIBUTE_BUYER_EMAIL;
    }
    else if (attributeName.equals("documentControlNumber")) {
      return ATTRIBUTE_DOCUMENT_CONTROL_NUMBER;
    }
    else if (attributeName.equals("transport")) {
      return ATTRIBUTE_TRANSPORT;
    }
    else if (attributeName.equals("transporterAccount")) {
      return ATTRIBUTE_TRANSPORTER_ACCOUNT;
    }
    else if (attributeName.equals("tradingPartner")) {
      return ATTRIBUTE_TRADING_PARTNER;
    }
    else if (attributeName.equals("tradingPartnerId")) {
      return ATTRIBUTE_TRADING_PARTNER_ID;
    }
    else if (attributeName.equals("customerHaasContractId")) {
      return ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID;
    }
    else if (attributeName.equals("customerHaasAccountNo")) {
      return ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO;
    }
    else if (attributeName.equals("customerPaymentTerms")) {
      return ATTRIBUTE_CUSTOMER_PAYMENT_TERMS;
    }
    else if (attributeName.equals("customerPoNote")) {
      return ATTRIBUTE_CUSTOMER_PO_NOTE;
    }
    else if (attributeName.equals("buyerNameOnPo")) {
      return ATTRIBUTE_BUYER_NAME_ON_PO;
    }
    else if (attributeName.equals("buyerAddress1")) {
      return ATTRIBUTE_BUYER_ADDRESS1;
    }
    else if (attributeName.equals("buyerAddress2")) {
      return ATTRIBUTE_BUYER_ADDRESS2;
    }
    else if (attributeName.equals("buyerCity")) {
      return ATTRIBUTE_BUYER_CITY;
    }
    else if (attributeName.equals("buyerState")) {
      return ATTRIBUTE_BUYER_STATE;
    }
    else if (attributeName.equals("buyerZip")) {
      return ATTRIBUTE_BUYER_ZIP;
    }
    else if (attributeName.equals("buyerCountry")) {
      return ATTRIBUTE_BUYER_COUNTRY;
    }
    else if (attributeName.equals("sellerNameOnPo")) {
      return ATTRIBUTE_SELLER_NAME_ON_PO;
    }
    else if (attributeName.equals("sellerIdOnPo")) {
      return ATTRIBUTE_SELLER_ID_ON_PO;
    }
    else if (attributeName.equals("sellerAddress1")) {
      return ATTRIBUTE_SELLER_ADDRESS1;
    }
    else if (attributeName.equals("sellerAddress2")) {
      return ATTRIBUTE_SELLER_ADDRESS2;
    }
    else if (attributeName.equals("sellerCity")) {
      return ATTRIBUTE_SELLER_CITY;
    }
    else if (attributeName.equals("sellerState")) {
      return ATTRIBUTE_SELLER_STATE;
    }
    else if (attributeName.equals("sellerZip")) {
      return ATTRIBUTE_SELLER_ZIP;
    }
    else if (attributeName.equals("sellerCountry")) {
      return ATTRIBUTE_SELLER_COUNTRY;
    }
    else if (attributeName.equals("shiptoPartyName")) {
      return ATTRIBUTE_SHIPTO_PARTY_NAME;
    }
    else if (attributeName.equals("shiptoPartyId")) {
      return ATTRIBUTE_SHIPTO_PARTY_ID;
    }
    else if (attributeName.equals("shiptoAddress1")) {
      return ATTRIBUTE_SHIPTO_ADDRESS1;
    }
    else if (attributeName.equals("shiptoAddress2")) {
      return ATTRIBUTE_SHIPTO_ADDRESS2;
    }
    else if (attributeName.equals("shiptoAddress3")) {
      return ATTRIBUTE_SHIPTO_ADDRESS3;
    }
    else if (attributeName.equals("shiptoCity")) {
      return ATTRIBUTE_SHIPTO_CITY;
    }
    else if (attributeName.equals("shiptoState")) {
      return ATTRIBUTE_SHIPTO_STATE;
    }
    else if (attributeName.equals("shiptoZip")) {
      return ATTRIBUTE_SHIPTO_ZIP;
    }
    else if (attributeName.equals("shiptoCountry")) {
      return ATTRIBUTE_SHIPTO_COUNTRY;
    }
    else if (attributeName.equals("billtoParty")) {
      return ATTRIBUTE_BILLTO_PARTY;
    }
    else if (attributeName.equals("billtoPartyId")) {
      return ATTRIBUTE_BILLTO_PARTY_ID;
    }
    else if (attributeName.equals("billtoName2")) {
      return ATTRIBUTE_BILLTO_NAME2;
    }
    else if (attributeName.equals("billtoAddress1")) {
      return ATTRIBUTE_BILLTO_ADDRESS1;
    }
    else if (attributeName.equals("billtoAddress2")) {
      return ATTRIBUTE_BILLTO_ADDRESS2;
    }
    else if (attributeName.equals("billtoAddress3")) {
      return ATTRIBUTE_BILLTO_ADDRESS3;
    }
    else if (attributeName.equals("billtoCity")) {
      return ATTRIBUTE_BILLTO_CITY;
    }
    else if (attributeName.equals("billtoState")) {
      return ATTRIBUTE_BILLTO_STATE;
    }
    else if (attributeName.equals("billtoZip")) {
      return ATTRIBUTE_BILLTO_ZIP;
    }
    else if (attributeName.equals("billtoCountry")) {
      return ATTRIBUTE_BILLTO_COUNTRY;
    }
    else if (attributeName.equals("interchangeControlNumber")) {
      return ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER;
    }
    else if (attributeName.equals("currencyId")) {
      return ATTRIBUTE_CURRENCY_ID;
    }
    else if (attributeName.equals("loadId")) {
      return ATTRIBUTE_LOAD_ID;
    }
    else if (attributeName.equals("loadLine")) {
      return ATTRIBUTE_LOAD_LINE;
    }
    else if (attributeName.equals("scheduleQuantity")) {
      return ATTRIBUTE_SCHEDULE_QUANTITY;
    }
    else if (attributeName.equals("scheduleUom")) {
      return ATTRIBUTE_SCHEDULE_UOM;
    }
    else if (attributeName.equals("poTypeCode")) {
      return ATTRIBUTE_PO_TYPE_CODE;
    }
    else if (attributeName.equals("processingComments")) {
      return ATTRIBUTE_PROCESSING_COMMENTS;
    }
    else if (attributeName.equals("partShortDesc")) {
      return ATTRIBUTE_PART_SHORT_DESC;
    }
    else if (attributeName.equals("pre860")) {
      return ATTRIBUTE_PRE_860;
    }
    else if (attributeName.equals("originalQty")) {
      return ATTRIBUTE_ORIGINAL_QTY;
    }
    else if (attributeName.equals("originalUom")) {
      return ATTRIBUTE_ORIGINAL_UOM;
    }
    else if (attributeName.equals("deliveryTypeCode")) {
      return ATTRIBUTE_DELIVERY_TYPE_CODE;
    }
    else if (attributeName.equals("buyerIdOnPo")) {
      return ATTRIBUTE_BUYER_ID_ON_PO;
    }
    else if (attributeName.equals("freightOnBoard")) {
      return ATTRIBUTE_FREIGHT_ON_BOARD;
    }
    else if (attributeName.equals("paymentTerms")) {
      return ATTRIBUTE_PAYMENT_TERMS;
    }
    else if (attributeName.equals("freightOnBoardNotes")) {
      return ATTRIBUTE_FREIGHT_ON_BOARD_NOTES;
    }
    else if (attributeName.equals("paymentTermsNotes")) {
      return ATTRIBUTE_PAYMENT_TERMS_NOTES;
    }
    else if (attributeName.equals("buyerPartyName")) {
      return ATTRIBUTE_BUYER_PARTY_NAME;
    }
    else if (attributeName.equals("sellerPartyName")) {
      return ATTRIBUTE_SELLER_PARTY_NAME;
    }
    else if (attributeName.equals("shiptoContactName")) {
      return ATTRIBUTE_SHIPTO_CONTACT_NAME;
    }
    else if (attributeName.equals("priceBasisUom")) {
      return ATTRIBUTE_PRICE_BASIS_UOM;
    }
    else if (attributeName.equals("priceBasisQuantity")) {
      return ATTRIBUTE_PRICE_BASIS_QUANTITY;
    }
    else if (attributeName.equals("requestorName")) {
      return ATTRIBUTE_REQUESTOR_NAME;
    }
    else if (attributeName.equals("partialShipment")) {
      return ATTRIBUTE_PARTIAL_SHIPMENT;
    }
    else if (attributeName.equals("buyerRegion")) {
      return ATTRIBUTE_BUYER_REGION;
    }
    else if (attributeName.equals("sellerRegion")) {
      return ATTRIBUTE_SELLER_REGION;
    }
    else if (attributeName.equals("billtoRegion")) {
      return ATTRIBUTE_BILLTO_REGION;
    }
    else if (attributeName.equals("shiptoRegion")) {
      return ATTRIBUTE_SHIPTO_REGION;
    }
    else if (attributeName.equals("poUpdateDate")) {
      return ATTRIBUTE_PO_UPDATE_DATE;
    }
    else if (attributeName.equals("carrier")) {
      return ATTRIBUTE_CARRIER;
    }
    else if (attributeName.equals("originalDateIssued")) {
      return ATTRIBUTE_ORIGINAL_DATE_ISSUED;
    }
    else if(attributeName.equals("shiptoPhone")) {
        return ATTRIBUTE_SHIPTO_PHONE;
    }
    else if(attributeName.equals("shiptoEmail")) {
        return ATTRIBUTE_SHIPTO_EMAIL;
    }
    else if(attributeName.equals("deliveryNote")) {
        return ATTRIBUTE_DELIVERY_NOTE;
    }
    else if(attributeName.equals("releaseNum")) {
        return ATTRIBUTE_RELEASE_NUM;
    }
    else if(attributeName.equals("customerOrderType")) {
        return ATTRIBUTE_CUSTOMER_ORDER_TYPE;
    }
    else if(attributeName.equals("customerPickup")) {
        return ATTRIBUTE_CUSTOMER_PICKUP;
    }
    else if(attributeName.equals("catalogCompanyId")) {
        return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
    else if(attributeName.equals("ATTRIBUTE_SHIPPING_NOTES")) {
        return ATTRIBUTE_SHIPPING_NOTES;
    }
    else if (attributeName.equals("ATTRIBUTE_CHARGE_NUMBER1")) {
    	return ATTRIBUTE_CHARGE_NUMBER1;
    }
    else if (attributeName.equals("ATTRIBUTE_CHARGE_NUMBER2")) {
    	return ATTRIBUTE_CHARGE_NUMBER2;
    }
    else if (attributeName.equals("ATTRIBUTE_CHARGE_NUMBER3")) {
    	return ATTRIBUTE_CHARGE_NUMBER3;
    }    
    else if (attributeName.equals("ATTRIBUTE_APPLICATION")) {
    	return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("ATTRIBUTE_OWNER_SEGMENT_ID")) {
    	return ATTRIBUTE_OWNER_SEGMENT_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CustomerPoPreStageBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CustomerPoPreStageBean customerPoPreStageBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("customerPoNo", "SearchCriterion.EQUALS",
     "" + customerPoPreStageBean.getCustomerPoNo());
    Connection connection = null;
    int result = 0;
    try {
     connection = this.getDbManager().getConnection();
     result = this.delete(criteria, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int delete(CustomerPoPreStageBean customerPoPreStageBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("customerPoNo", "SearchCriterion.EQUALS",
     "" + customerPoPreStageBean.getCustomerPoNo());
    return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = delete(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
  }

  //insert
  public int insert(CustomerPoPreStageBean customerPoPreStageBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(customerPoPreStageBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(CustomerPoPreStageBean customerPoPreStageBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_COMPANY_ID + "," +
        ATTRIBUTE_CUSTOMER_PO_NO + "," +
        ATTRIBUTE_CUSTOMER_PO_LINE_NO + "," +
        ATTRIBUTE_TRANSACTION_TYPE + "," +
        ATTRIBUTE_LINE_SEQUENCE + "," +
        ATTRIBUTE_CHANGE_ORDER_SEQUENCE + "," +
        ATTRIBUTE_QUANTITY + "," +
        ATTRIBUTE_QUANTITY_LEFT + "," +
        ATTRIBUTE_UOM + "," +
        ATTRIBUTE_UNIT_PRICE + "," +
        ATTRIBUTE_STATUS + "," +
        ATTRIBUTE_CATALOG_ID + "," +
        ATTRIBUTE_CAT_PART_NO + "," +
        ATTRIBUTE_PART_GROUP_NO + "," +
        ATTRIBUTE_DATE_ISSUED + "," +
        ATTRIBUTE_HAAS_ITEM_NO + "," +
        ATTRIBUTE_MANUFACTURER_PART_NUM + "," +
        ATTRIBUTE_ITEM_DESCRIPTION + "," +
        ATTRIBUTE_CUSTOMER_PO_LINE_NOTE + "," +
        ATTRIBUTE_REQUESTED_DELIVERY_DATE + "," +
        ATTRIBUTE_ESTIMATED_DOCK_DATE + "," +
        ATTRIBUTE_TOTAL_AMOUNT_ON_PO + "," +
        ATTRIBUTE_DATE_INSERTED + "," +
        ATTRIBUTE_UNIT_PRICE_CODE + "," +
        ATTRIBUTE_ACCEPTANCE_CODE + "," +
        ATTRIBUTE_CHANGE_TYPE_CODE + "," +
        ATTRIBUTE_CHANGE_SUB_TYPE_CODE + "," +
        ATTRIBUTE_BUYER_PHONE + "," +
        ATTRIBUTE_BUYER_FAX + "," +
        ATTRIBUTE_BUYER_EMAIL + "," +
        ATTRIBUTE_DOCUMENT_CONTROL_NUMBER + "," +
        ATTRIBUTE_TRANSPORT + "," +
        ATTRIBUTE_TRANSPORTER_ACCOUNT + "," +
        ATTRIBUTE_TRADING_PARTNER + "," +
        ATTRIBUTE_TRADING_PARTNER_ID + "," +
        ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "," +
        ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO + "," +
        ATTRIBUTE_CUSTOMER_PAYMENT_TERMS + "," +
        ATTRIBUTE_CUSTOMER_PO_NOTE + "," +
        ATTRIBUTE_BUYER_NAME_ON_PO + "," +
        ATTRIBUTE_BUYER_ADDRESS1 + "," +
        ATTRIBUTE_BUYER_ADDRESS2 + "," +
        ATTRIBUTE_BUYER_CITY + "," +
        ATTRIBUTE_BUYER_STATE + "," +
        ATTRIBUTE_BUYER_ZIP + "," +
        ATTRIBUTE_BUYER_COUNTRY + "," +
        ATTRIBUTE_SELLER_NAME_ON_PO + "," +
        ATTRIBUTE_SELLER_ID_ON_PO + "," +
        ATTRIBUTE_SELLER_ADDRESS1 + "," +
        ATTRIBUTE_SELLER_ADDRESS2 + "," +
        ATTRIBUTE_SELLER_CITY + "," +
        ATTRIBUTE_SELLER_STATE + "," +
        ATTRIBUTE_SELLER_ZIP + "," +
        ATTRIBUTE_SELLER_COUNTRY + "," +
        ATTRIBUTE_SHIPTO_PARTY_NAME + "," +
        ATTRIBUTE_SHIPTO_PARTY_ID + "," +
        ATTRIBUTE_SHIPTO_ADDRESS1 + "," +
        ATTRIBUTE_SHIPTO_ADDRESS2 + "," +
        ATTRIBUTE_SHIPTO_ADDRESS3 + "," +
        ATTRIBUTE_SHIPTO_CITY + "," +
        ATTRIBUTE_SHIPTO_STATE + "," +
        ATTRIBUTE_SHIPTO_ZIP + "," +
        ATTRIBUTE_SHIPTO_COUNTRY + "," +
        ATTRIBUTE_BILLTO_PARTY + "," +
        ATTRIBUTE_BILLTO_PARTY_ID + "," +
        ATTRIBUTE_BILLTO_NAME2 + "," +
        ATTRIBUTE_BILLTO_ADDRESS1 + "," +
        ATTRIBUTE_BILLTO_ADDRESS2 + "," +
        ATTRIBUTE_BILLTO_ADDRESS3 + "," +
        ATTRIBUTE_BILLTO_CITY + "," +
        ATTRIBUTE_BILLTO_STATE + "," +
        ATTRIBUTE_BILLTO_ZIP + "," +
        ATTRIBUTE_BILLTO_COUNTRY + "," +
        ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER + "," +
        ATTRIBUTE_CURRENCY_ID + "," +
        ATTRIBUTE_LOAD_ID + "," +
        ATTRIBUTE_LOAD_LINE + "," +
        ATTRIBUTE_SCHEDULE_QUANTITY + "," +
        ATTRIBUTE_SCHEDULE_UOM + "," +
        ATTRIBUTE_PO_TYPE_CODE + "," +
        ATTRIBUTE_PROCESSING_COMMENTS + "," +
        ATTRIBUTE_PART_SHORT_DESC + "," +
        ATTRIBUTE_PRE_860 + "," +
        ATTRIBUTE_ORIGINAL_QTY + "," +
        ATTRIBUTE_ORIGINAL_UOM + "," +
        ATTRIBUTE_DELIVERY_TYPE_CODE + "," +
        ATTRIBUTE_BUYER_ID_ON_PO + "," +
        ATTRIBUTE_FREIGHT_ON_BOARD + "," +
        ATTRIBUTE_PAYMENT_TERMS + "," +
        ATTRIBUTE_FREIGHT_ON_BOARD_NOTES + "," +
        ATTRIBUTE_PAYMENT_TERMS_NOTES + "," +
        ATTRIBUTE_BUYER_PARTY_NAME + "," +
        ATTRIBUTE_SELLER_PARTY_NAME + "," +
        ATTRIBUTE_SHIPTO_CONTACT_NAME + "," +
        ATTRIBUTE_PRICE_BASIS_UOM + "," +
        ATTRIBUTE_PRICE_BASIS_QUANTITY + "," +
        ATTRIBUTE_REQUESTOR_NAME + "," +
        ATTRIBUTE_PARTIAL_SHIPMENT + "," +
        ATTRIBUTE_BUYER_REGION + "," +
        ATTRIBUTE_SELLER_REGION + "," +
        ATTRIBUTE_BILLTO_REGION + "," +
        ATTRIBUTE_SHIPTO_REGION + ","+
        ATTRIBUTE_PO_UPDATE_DATE + ","+
        ATTRIBUTE_CARRIER + "," +
        ATTRIBUTE_ORIGINAL_DATE_ISSUED + "," +
        ATTRIBUTE_SHIPTO_PHONE + "," +
	    ATTRIBUTE_SHIPTO_EMAIL + "," +
        ATTRIBUTE_RELEASE_NUM + "," +
        ATTRIBUTE_CUSTOMER_ORDER_TYPE + "," +
        ATTRIBUTE_CUSTOMER_PICKUP + "," +
        ATTRIBUTE_DELIVERY_NOTE + "," +
        ATTRIBUTE_SHIPPING_NOTES + "," +
        ATTRIBUTE_CHARGE_NUMBER1 + "," +
        ATTRIBUTE_CHARGE_NUMBER2 + "," +
        ATTRIBUTE_CHARGE_NUMBER3 + "," +
        ATTRIBUTE_APPLICATION + "," +
        ATTRIBUTE_OWNER_SEGMENT_ID + "," +
        ATTRIBUTE_CATALOG_COMPANY_ID + ")" +
        " values (" +
        SqlHandler.delimitString(customerPoPreStageBean.getCompanyId()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoNo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoLineNo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getTransactionType()) + "," +
        customerPoPreStageBean.getLineSequence() + "," +
        customerPoPreStageBean.getChangeOrderSequence() + "," +
        customerPoPreStageBean.getQuantity() + "," +
        customerPoPreStageBean.getQuantityLeft() + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getUom()) + "," +
        customerPoPreStageBean.getUnitPrice() + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getStatus()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCatalogId()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCatPartNo()) + "," +
        customerPoPreStageBean.getPartGroupNo() + "," +
        DateHandler.getOracleToDateFunction(customerPoPreStageBean.getDateIssued()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getHaasItemNo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getManufacturerPartNum()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getItemDescription()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoLineNote()) + "," +
        DateHandler.getOracleToDateFunction(customerPoPreStageBean.
                                            getRequestedDeliveryDate()) + "," +
        DateHandler.getOracleToDateFunction(customerPoPreStageBean.getEstimatedDockDate()) +
        "," +
        customerPoPreStageBean.getTotalAmountOnPo() + "," +
        DateHandler.getOracleToDateFunction(customerPoPreStageBean.getDateInserted()) +
        "," +
        SqlHandler.delimitString(customerPoPreStageBean.getUnitPriceCode()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getAcceptanceCode()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getChangeTypeCode()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getChangeSubTypeCode()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerPhone()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerFax()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerEmail()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getDocumentControlNumber()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getTransport()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getTransporterAccount()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getTradingPartner()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getTradingPartnerId()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerHaasContractId()) +
        "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerHaasAccountNo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerPaymentTerms()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoNote()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerNameOnPo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerAddress1()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerAddress2()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerCity()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerState()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerZip()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerCountry()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerNameOnPo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerIdOnPo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerAddress1()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerAddress2()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerCity()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerState()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerZip()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerCountry()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoPartyName()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoPartyId()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoAddress1()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoAddress2()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoAddress3()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoCity()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoState()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoZip()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoCountry()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoParty()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoPartyId()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoName2()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoAddress1()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoAddress2()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoAddress3()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoCity()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoState()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoZip()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoCountry()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getInterchangeControlNumber()) +
        "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCurrencyId()) + "," +
        customerPoPreStageBean.getLoadId() + "," +
        customerPoPreStageBean.getLoadLine() + "," +
        customerPoPreStageBean.getScheduleQuantity() + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getScheduleUom()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getPoTypeCode()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getProcessingComments()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getPartShortDesc()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getPre860()) + "," +
        customerPoPreStageBean.getOriginalQty() + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getOriginalUom()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getDeliveryTypeCode()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerIdOnPo()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getFreightOnBoard()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getPaymentTerms()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getFreightOnBoardNotes()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getPaymentTermsNotes()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerPartyName()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerPartyName()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoContactName()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getPriceBasisUom()) + "," +
        customerPoPreStageBean.getPriceBasisQuantity() + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getRequestorName()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getPartialShipment()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBuyerRegion()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getSellerRegion()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getBilltoRegion()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoRegion()) + ","+
        SqlHandler.delimitString(customerPoPreStageBean.getPoUpdateDate()) + ","+
        SqlHandler.delimitString(customerPoPreStageBean.getCarrier()) + ","+
        DateHandler.getOracleToDateFunction(customerPoPreStageBean.getOriginalDateIssued()) + ","+
        SqlHandler.delimitString(customerPoPreStageBean.getShiptoPhone()) + "," +
		SqlHandler.delimitString(customerPoPreStageBean.getShiptoEmail()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getReleaseNum()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerOrderType()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getCustomerPickup()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getDeliveryNote()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getShippingNotes()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getChargeNumber1()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getChargeNumber2()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getChargeNumber3()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getApplication()) + "," +
        SqlHandler.delimitString(customerPoPreStageBean.getOwnerSegmentId()) + "," +
    	SqlHandler.delimitString(customerPoPreStageBean.getCatalogCompanyId()) + ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(CustomerPoPreStageBean customerPoPreStageBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(customerPoPreStageBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CustomerPoPreStageBean customerPoPreStageBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CUSTOMER_PO_NO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoNo()) + "," +
     ATTRIBUTE_CUSTOMER_PO_LINE_NO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoLineNo()) + "," +
     ATTRIBUTE_TRANSACTION_TYPE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getTransactionType()) + "," +
     ATTRIBUTE_LINE_SEQUENCE + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getLineSequence()) + "," +
     ATTRIBUTE_CHANGE_ORDER_SEQUENCE + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getChangeOrderSequence()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getQuantity()) + "," +
     ATTRIBUTE_QUANTITY_LEFT + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getQuantityLeft()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getUom()) + "," +
     ATTRIBUTE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getUnitPrice()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getStatus()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCatalogId()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCatPartNo()) + "," +
     ATTRIBUTE_PART_GROUP_NO + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getPartGroupNo()) + "," +
     ATTRIBUTE_DATE_ISSUED + "=" +
      DateHandler.getOracleToDateFunction(customerPoPreStageBean.getDateIssued()) + "," +
     ATTRIBUTE_HAAS_ITEM_NO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getHaasItemNo()) + "," +
     ATTRIBUTE_MANUFACTURER_PART_NUM + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getManufacturerPartNum()) + "," +
     ATTRIBUTE_ITEM_DESCRIPTION + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getItemDescription()) + "," +
     ATTRIBUTE_CUSTOMER_PO_LINE_NOTE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoLineNote()) + "," +
     ATTRIBUTE_REQUESTED_DELIVERY_DATE + "=" +
      DateHandler.getOracleToDateFunction(customerPoPreStageBean.getRequestedDeliveryDate()) + "," +
     ATTRIBUTE_ESTIMATED_DOCK_DATE + "=" +
       DateHandler.getOracleToDateFunction(customerPoPreStageBean.getEstimatedDockDate()) + "," +
     ATTRIBUTE_TOTAL_AMOUNT_ON_PO + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getTotalAmountOnPo()) + "," +
     ATTRIBUTE_DATE_INSERTED + "=" +
       DateHandler.getOracleToDateFunction(customerPoPreStageBean.getDateInserted()) + "," +
     ATTRIBUTE_UNIT_PRICE_CODE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getUnitPriceCode()) + "," +
     ATTRIBUTE_ACCEPTANCE_CODE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getAcceptanceCode()) + "," +
     ATTRIBUTE_CHANGE_TYPE_CODE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getChangeTypeCode()) + "," +
     ATTRIBUTE_CHANGE_SUB_TYPE_CODE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getChangeSubTypeCode()) + "," +
     ATTRIBUTE_BUYER_PHONE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerPhone()) + "," +
     ATTRIBUTE_BUYER_FAX + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerFax()) + "," +
     ATTRIBUTE_DOCUMENT_CONTROL_NUMBER + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getDocumentControlNumber()) + "," +
     ATTRIBUTE_TRANSPORT + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getTransport()) + "," +
     ATTRIBUTE_TRANSPORTER_ACCOUNT + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getTransporterAccount()) + "," +
     ATTRIBUTE_TRADING_PARTNER + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getTradingPartner()) + "," +
     ATTRIBUTE_TRADING_PARTNER_ID + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getTradingPartnerId()) + "," +
     ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "=" +
       SqlHandler.delimitString(customerPoPreStageBean.getCustomerHaasContractId()) + "," +
     ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCustomerHaasAccountNo()) + "," +
     ATTRIBUTE_CUSTOMER_PAYMENT_TERMS + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCustomerPaymentTerms()) + "," +
     ATTRIBUTE_CUSTOMER_PO_NOTE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCustomerPoNote()) + "," +
     ATTRIBUTE_BUYER_NAME_ON_PO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerNameOnPo()) + "," +
     ATTRIBUTE_BUYER_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerAddress1()) + "," +
     ATTRIBUTE_BUYER_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerAddress2()) + "," +
     ATTRIBUTE_BUYER_CITY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerCity()) + "," +
     ATTRIBUTE_BUYER_STATE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerState()) + "," +
     ATTRIBUTE_BUYER_ZIP + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerZip()) + "," +
     ATTRIBUTE_BUYER_COUNTRY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerCountry()) + "," +
     ATTRIBUTE_SELLER_NAME_ON_PO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerNameOnPo()) + "," +
     ATTRIBUTE_SELLER_ID_ON_PO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerIdOnPo()) + "," +
     ATTRIBUTE_SELLER_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerAddress1()) + "," +
     ATTRIBUTE_SELLER_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerAddress2()) + "," +
     ATTRIBUTE_SELLER_CITY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerCity()) + "," +
     ATTRIBUTE_SELLER_STATE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerState()) + "," +
     ATTRIBUTE_SELLER_ZIP + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerZip()) + "," +
     ATTRIBUTE_SELLER_COUNTRY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerCountry()) + "," +
     ATTRIBUTE_SHIPTO_PARTY_NAME + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoPartyName()) + "," +
     ATTRIBUTE_SHIPTO_PARTY_ID + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoPartyId()) + "," +
     ATTRIBUTE_SHIPTO_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoAddress1()) + "," +
     ATTRIBUTE_SHIPTO_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoAddress2()) + "," +
     ATTRIBUTE_SHIPTO_ADDRESS3 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoAddress3()) + "," +
     ATTRIBUTE_SHIPTO_CITY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoCity()) + "," +
     ATTRIBUTE_SHIPTO_STATE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoState()) + "," +
     ATTRIBUTE_SHIPTO_ZIP + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoZip()) + "," +
     ATTRIBUTE_SHIPTO_COUNTRY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoCountry()) + "," +
     ATTRIBUTE_BILLTO_PARTY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoParty()) + "," +
     ATTRIBUTE_BILLTO_PARTY_ID + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoPartyId()) + "," +
     ATTRIBUTE_BILLTO_NAME2 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoName2()) + "," +
     ATTRIBUTE_BILLTO_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoAddress1()) + "," +
     ATTRIBUTE_BILLTO_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoAddress2()) + "," +
     ATTRIBUTE_BILLTO_ADDRESS3 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoAddress3()) + "," +
     ATTRIBUTE_BILLTO_CITY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoCity()) + "," +
     ATTRIBUTE_BILLTO_STATE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoState()) + "," +
     ATTRIBUTE_BILLTO_ZIP + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoZip()) + "," +
     ATTRIBUTE_BILLTO_COUNTRY + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoCountry()) + "," +
     ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER + "=" +
       SqlHandler.delimitString(customerPoPreStageBean.getInterchangeControlNumber()) + "," +
     ATTRIBUTE_CURRENCY_ID + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getCurrencyId()) + "," +
     ATTRIBUTE_LOAD_ID + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getLoadId()) + "," +
     ATTRIBUTE_LOAD_LINE + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getLoadLine()) + "," +
     ATTRIBUTE_SCHEDULE_QUANTITY + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getScheduleQuantity()) + "," +
     ATTRIBUTE_SCHEDULE_UOM + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getScheduleUom()) + "," +
     ATTRIBUTE_PO_TYPE_CODE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getPoTypeCode()) + "," +
     ATTRIBUTE_PROCESSING_COMMENTS + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getProcessingComments()) + "," +
     ATTRIBUTE_PART_SHORT_DESC + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getPartShortDesc()) + "," +
     ATTRIBUTE_PRE_860 + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getPre860()) + "," +
     ATTRIBUTE_ORIGINAL_QTY + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getOriginalQty()) + "," +
     ATTRIBUTE_ORIGINAL_UOM + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getOriginalUom()) + "," +
     ATTRIBUTE_DELIVERY_TYPE_CODE + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getDeliveryTypeCode()) + "," +
     ATTRIBUTE_BUYER_ID_ON_PO + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerIdOnPo()) + "," +
     ATTRIBUTE_FREIGHT_ON_BOARD + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getFreightOnBoard()) + "," +
     ATTRIBUTE_PAYMENT_TERMS + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getPaymentTerms()) + "," +
     ATTRIBUTE_FREIGHT_ON_BOARD_NOTES + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getFreightOnBoardNotes()) + "," +
     ATTRIBUTE_PAYMENT_TERMS_NOTES + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getPaymentTermsNotes()) + "," +
     ATTRIBUTE_BUYER_PARTY_NAME + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerPartyName()) + "," +
     ATTRIBUTE_SELLER_PARTY_NAME + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerPartyName()) + "," +
     ATTRIBUTE_SHIPTO_CONTACT_NAME + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoContactName()) + "," +
     ATTRIBUTE_PRICE_BASIS_UOM + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getPriceBasisUom()) + "," +
     ATTRIBUTE_PRICE_BASIS_QUANTITY + "=" +
      StringHandler.nullIfZero(customerPoPreStageBean.getPriceBasisQuantity()) + "," +
     ATTRIBUTE_REQUESTOR_NAME + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getRequestorName()) + "," +
     ATTRIBUTE_PARTIAL_SHIPMENT + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getPartialShipment()) + "," +
     ATTRIBUTE_BUYER_REGION + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBuyerRegion()) + "," +
     ATTRIBUTE_SELLER_REGION + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getSellerRegion()) + "," +
     ATTRIBUTE_BILLTO_REGION + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getBilltoRegion()) + "," +
     ATTRIBUTE_SHIPTO_REGION + "=" +
      SqlHandler.delimitString(customerPoPreStageBean.getShiptoRegion()) + " " +
     "where " + ATTRIBUTE_CUSTOMER_PO_NO + "=" +
      customerPoPreStageBean.getCustomerPoNo();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection customerPoPreStageBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CustomerPoPreStageBean customerPoPreStageBean = new CustomerPoPreStageBean();
      load(dataSetRow, customerPoPreStageBean);
      customerPoPreStageBeanColl.add(customerPoPreStageBean);
    }
    return customerPoPreStageBeanColl;
  }
}