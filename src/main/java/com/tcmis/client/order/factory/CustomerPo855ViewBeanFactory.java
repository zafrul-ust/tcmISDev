package com.tcmis.client.order.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.order.beans.CustomerPo855ViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: CustomerPo855ViewBeanFactory <br>
 * @version: 1.0, Nov 17, 2005 <br>
 *****************************************************************************/

public class CustomerPo855ViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
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
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID = "SUGGESTED_DBUY_CONTRACT_ID";
  public String ATTRIBUTE_SUGGESTED_QUANTITY = "SUGGESTED_QUANTITY";
  public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
  public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_APPLICATION = "APPLICATION";
  public String ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER = "INTERCHANGE_CONTROL_NUMBER";
  public String ATTRIBUTE_SUM_QTY_FOR_ALL_LINES = "SUM_QTY_FOR_ALL_LINES";
  //public String ATTRIBUTE_ORIGINAL_QUANTITY_ORDERED = "ORIGINAL_QUANTITY_ORDERED";
  //public String ATTRIBUTE_ORIGINAL_UNIT_PRICE = "ORIGINAL_UNIT_PRICE";
  //public String ATTRIBUTE_ORIG_REQUESTED_DELIVERY_DATE = "ORIG_REQUESTED_DELIVERY_DATE";
  //public String ATTRIBUTE_ORIG_QUANTITY = "ORIG_QUANTITY";
  public String ATTRIBUTE_FROM_LOAD_ID = "FROM_LOAD_ID";
  public String ATTRIBUTE_FROM_LOAD_LINE = "FROM_LOAD_LINE";


  //table name
  public String TABLE = "CUSTOMER_PO_855_VIEW";
  public String EXOSTAR_TABLE = "EXOSTAR_RESPONSE_VIEW";

  //constructor
  public CustomerPo855ViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("customerPoNo")) {
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
    else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    }
    else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    }
    else if (attributeName.equals("suggestedDbuyContractId")) {
      return ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID;
    }
    else if (attributeName.equals("suggestedQuantity")) {
      return ATTRIBUTE_SUGGESTED_QUANTITY;
    }
    else if (attributeName.equals("accountSysId")) {
      return ATTRIBUTE_ACCOUNT_SYS_ID;
    }
    else if (attributeName.equals("chargeType")) {
      return ATTRIBUTE_CHARGE_TYPE;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("application")) {
      return ATTRIBUTE_APPLICATION;
    }
    else if (attributeName.equals("interchangeControlNumber")) {
      return ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER;
    }
    else if (attributeName.equals("sumQtyForAllLines")) {
      return ATTRIBUTE_SUM_QTY_FOR_ALL_LINES;
    }
/*
    else if (attributeName.equals("originalQuantityOrdered")) {
      return ATTRIBUTE_ORIGINAL_QUANTITY_ORDERED;
    }
    else if (attributeName.equals("originalUnitPrice")) {
      return ATTRIBUTE_ORIGINAL_UNIT_PRICE;
    }
    else if (attributeName.equals("origRequestedDeliveryDate")) {
      return ATTRIBUTE_ORIG_REQUESTED_DELIVERY_DATE;
    }
    else if (attributeName.equals("origQuantity")) {
      return ATTRIBUTE_ORIG_QUANTITY;
    }
*/
    else if (attributeName.equals("fromLoadId")) {
      return ATTRIBUTE_FROM_LOAD_ID;
    }
    else if (attributeName.equals("fromLoadLine")) {
      return ATTRIBUTE_FROM_LOAD_LINE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CustomerPo855ViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CustomerPo855ViewBean customerPo855ViewBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("customerPoNo", "SearchCriterion.EQUALS",
     "" + customerPo855ViewBean.getCustomerPoNo());
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
   public int delete(CustomerPo855ViewBean customerPo855ViewBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("customerPoNo", "SearchCriterion.EQUALS",
     "" + customerPo855ViewBean.getCustomerPoNo());
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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(CustomerPo855ViewBean customerPo855ViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(customerPo855ViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(CustomerPo855ViewBean customerPo855ViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
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
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_LINE_ITEM + "," +
     ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID + "," +
     ATTRIBUTE_SUGGESTED_QUANTITY + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "," +
     ATTRIBUTE_CHARGE_TYPE + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_APPLICATION + "," +
     ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER + "," +
     ATTRIBUTE_SUM_QTY_FOR_ALL_LINES + "," +
     ATTRIBUTE_ORIGINAL_QUANTITY_ORDERED + "," +
     ATTRIBUTE_ORIGINAL_UNIT_PRICE + "," +
     ATTRIBUTE_ORIG_REQUESTED_DELIVERY_DATE + "," +
     ATTRIBUTE_ORIG_QUANTITY + "," +
     ATTRIBUTE_FROM_LOAD_ID + "," +
     ATTRIBUTE_FROM_LOAD_LINE + ")" +
     " values (" +
     SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoNo()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoLineNo()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getTransactionType()) + "," +
     customerPo855ViewBean.getLineSequence() + "," +
     customerPo855ViewBean.getChangeOrderSequence() + "," +
     customerPo855ViewBean.getQuantity() + "," +
     customerPo855ViewBean.getQuantityLeft() + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getUom()) + "," +
     customerPo855ViewBean.getUnitPrice() + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getStatus()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCatalogId()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCatPartNo()) + "," +
     customerPo855ViewBean.getPartGroupNo() + "," +
     DateHandler.getOracleToDateFunction(customerPo855ViewBean.getDateIssued()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getHaasItemNo()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getManufacturerPartNum()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getItemDescription()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoLineNote()) + "," +
     DateHandler.getOracleToDateFunction(customerPo855ViewBean.getRequestedDeliveryDate()) + "," +
       DateHandler.getOracleToDateFunction(customerPo855ViewBean.getEstimatedDockDate()) + "," +
     customerPo855ViewBean.getTotalAmountOnPo() + "," +
     DateHandler.getOracleToDateFunction(customerPo855ViewBean.getDateInserted()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getUnitPriceCode()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getAcceptanceCode()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getChangeTypeCode()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getChangeSubTypeCode()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerPhone()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerFax()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getDocumentControlNumber()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getTransport()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getTransporterAccount()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getTradingPartner()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getTradingPartnerId()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCustomerHaasContractId()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCustomerHaasAccountNo()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCustomerPaymentTerms()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoNote()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerNameOnPo()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerAddress1()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerAddress2()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerCity()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerState()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerZip()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBuyerCountry()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerNameOnPo()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerIdOnPo()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerAddress1()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerAddress2()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerCity()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerState()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerZip()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getSellerCountry()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoPartyName()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoPartyId()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoAddress1()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoAddress2()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoAddress3()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoCity()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoState()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoZip()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getShiptoCountry()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoParty()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoPartyId()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoName2()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoAddress1()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoAddress2()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoAddress3()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoCity()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoState()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoZip()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getBilltoCountry()) + "," +
     customerPo855ViewBean.getPrNumber() + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getLineItem()) + "," +
     customerPo855ViewBean.getSuggestedDbuyContractId() + "," +
     customerPo855ViewBean.getSuggestedQuantity() + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getAccountSysId()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getChargeType()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(customerPo855ViewBean.getApplication()) + "," +
       SqlHandler.delimitString(customerPo855ViewBean.getInterchangeControlNumber()) + "," +
     customerPo855ViewBean.getSumQtyForAllLines() + "," +
     customerPo855ViewBean.getOriginalQuantityOrdered() + "," +
     customerPo855ViewBean.getOriginalUnitPrice() + "," +
     DateHandler.getOracleToDateFunction(customerPo855ViewBean.getOrigRequestedDeliveryDate()) + "," +
     customerPo855ViewBean.getOrigQuantity() + "," +
     customerPo855ViewBean.getFromLoadId() + "," +
     customerPo855ViewBean.getFromLoadLine() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(CustomerPo855ViewBean customerPo855ViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(customerPo855ViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(CustomerPo855ViewBean customerPo855ViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_CUSTOMER_PO_NO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoNo()) + "," +
     ATTRIBUTE_CUSTOMER_PO_LINE_NO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoLineNo()) + "," +
     ATTRIBUTE_TRANSACTION_TYPE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getTransactionType()) + "," +
     ATTRIBUTE_LINE_SEQUENCE + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getLineSequence()) + "," +
     ATTRIBUTE_CHANGE_ORDER_SEQUENCE + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getChangeOrderSequence()) + "," +
     ATTRIBUTE_QUANTITY + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getQuantity()) + "," +
     ATTRIBUTE_QUANTITY_LEFT + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getQuantityLeft()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getUom()) + "," +
     ATTRIBUTE_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getUnitPrice()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getStatus()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCatalogId()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCatPartNo()) + "," +
     ATTRIBUTE_PART_GROUP_NO + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getPartGroupNo()) + "," +
     ATTRIBUTE_DATE_ISSUED + "=" +
      DateHandler.getOracleToDateFunction(customerPo855ViewBean.getDateIssued()) + "," +
     ATTRIBUTE_HAAS_ITEM_NO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getHaasItemNo()) + "," +
     ATTRIBUTE_MANUFACTURER_PART_NUM + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getManufacturerPartNum()) + "," +
     ATTRIBUTE_ITEM_DESCRIPTION + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getItemDescription()) + "," +
     ATTRIBUTE_CUSTOMER_PO_LINE_NOTE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoLineNote()) + "," +
     ATTRIBUTE_REQUESTED_DELIVERY_DATE + "=" +
      DateHandler.getOracleToDateFunction(customerPo855ViewBean.getRequestedDeliveryDate()) + "," +
     ATTRIBUTE_ESTIMATED_DOCK_DATE + "=" +
       DateHandler.getOracleToDateFunction(customerPo855ViewBean.getEstimatedDockDate()) + "," +
     ATTRIBUTE_TOTAL_AMOUNT_ON_PO + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getTotalAmountOnPo()) + "," +
     ATTRIBUTE_DATE_INSERTED + "=" +
       DateHandler.getOracleToDateFunction(customerPo855ViewBean.getDateInserted()) + "," +
     ATTRIBUTE_UNIT_PRICE_CODE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getUnitPriceCode()) + "," +
     ATTRIBUTE_ACCEPTANCE_CODE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getAcceptanceCode()) + "," +
     ATTRIBUTE_CHANGE_TYPE_CODE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getChangeTypeCode()) + "," +
     ATTRIBUTE_CHANGE_SUB_TYPE_CODE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getChangeSubTypeCode()) + "," +
     ATTRIBUTE_BUYER_PHONE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerPhone()) + "," +
     ATTRIBUTE_BUYER_FAX + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerFax()) + "," +
     ATTRIBUTE_DOCUMENT_CONTROL_NUMBER + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getDocumentControlNumber()) + "," +
     ATTRIBUTE_TRANSPORT + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getTransport()) + "," +
     ATTRIBUTE_TRANSPORTER_ACCOUNT + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getTransporterAccount()) + "," +
     ATTRIBUTE_TRADING_PARTNER + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getTradingPartner()) + "," +
     ATTRIBUTE_TRADING_PARTNER_ID + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getTradingPartnerId()) + "," +
     ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCustomerHaasContractId()) + "," +
     ATTRIBUTE_CUSTOMER_HAAS_ACCOUNT_NO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCustomerHaasAccountNo()) + "," +
     ATTRIBUTE_CUSTOMER_PAYMENT_TERMS + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCustomerPaymentTerms()) + "," +
     ATTRIBUTE_CUSTOMER_PO_NOTE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getCustomerPoNote()) + "," +
     ATTRIBUTE_BUYER_NAME_ON_PO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerNameOnPo()) + "," +
     ATTRIBUTE_BUYER_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerAddress1()) + "," +
     ATTRIBUTE_BUYER_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerAddress2()) + "," +
     ATTRIBUTE_BUYER_CITY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerCity()) + "," +
     ATTRIBUTE_BUYER_STATE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerState()) + "," +
     ATTRIBUTE_BUYER_ZIP + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerZip()) + "," +
     ATTRIBUTE_BUYER_COUNTRY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBuyerCountry()) + "," +
     ATTRIBUTE_SELLER_NAME_ON_PO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerNameOnPo()) + "," +
     ATTRIBUTE_SELLER_ID_ON_PO + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerIdOnPo()) + "," +
     ATTRIBUTE_SELLER_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerAddress1()) + "," +
     ATTRIBUTE_SELLER_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerAddress2()) + "," +
     ATTRIBUTE_SELLER_CITY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerCity()) + "," +
     ATTRIBUTE_SELLER_STATE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerState()) + "," +
     ATTRIBUTE_SELLER_ZIP + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerZip()) + "," +
     ATTRIBUTE_SELLER_COUNTRY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getSellerCountry()) + "," +
     ATTRIBUTE_SHIPTO_PARTY_NAME + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoPartyName()) + "," +
     ATTRIBUTE_SHIPTO_PARTY_ID + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoPartyId()) + "," +
     ATTRIBUTE_SHIPTO_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoAddress1()) + "," +
     ATTRIBUTE_SHIPTO_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoAddress2()) + "," +
     ATTRIBUTE_SHIPTO_ADDRESS3 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoAddress3()) + "," +
     ATTRIBUTE_SHIPTO_CITY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoCity()) + "," +
     ATTRIBUTE_SHIPTO_STATE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoState()) + "," +
     ATTRIBUTE_SHIPTO_ZIP + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoZip()) + "," +
     ATTRIBUTE_SHIPTO_COUNTRY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getShiptoCountry()) + "," +
     ATTRIBUTE_BILLTO_PARTY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoParty()) + "," +
     ATTRIBUTE_BILLTO_PARTY_ID + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoPartyId()) + "," +
     ATTRIBUTE_BILLTO_NAME2 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoName2()) + "," +
     ATTRIBUTE_BILLTO_ADDRESS1 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoAddress1()) + "," +
     ATTRIBUTE_BILLTO_ADDRESS2 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoAddress2()) + "," +
     ATTRIBUTE_BILLTO_ADDRESS3 + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoAddress3()) + "," +
     ATTRIBUTE_BILLTO_CITY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoCity()) + "," +
     ATTRIBUTE_BILLTO_STATE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoState()) + "," +
     ATTRIBUTE_BILLTO_ZIP + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoZip()) + "," +
     ATTRIBUTE_BILLTO_COUNTRY + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getBilltoCountry()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getPrNumber()) + "," +
     ATTRIBUTE_LINE_ITEM + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getLineItem()) + "," +
     ATTRIBUTE_SUGGESTED_DBUY_CONTRACT_ID + "=" +
       StringHandler.nullIfZero(customerPo855ViewBean.getSuggestedDbuyContractId()) + "," +
     ATTRIBUTE_SUGGESTED_QUANTITY + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getSuggestedQuantity()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getAccountSysId()) + "," +
     ATTRIBUTE_CHARGE_TYPE + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getChargeType()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getFacilityId()) + "," +
     ATTRIBUTE_APPLICATION + "=" +
      SqlHandler.delimitString(customerPo855ViewBean.getApplication()) + "," +
     ATTRIBUTE_INTERCHANGE_CONTROL_NUMBER + "=" +
       SqlHandler.delimitString(customerPo855ViewBean.getInterchangeControlNumber()) + "," +
     ATTRIBUTE_SUM_QTY_FOR_ALL_LINES + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getSumQtyForAllLines()) + "," +
     ATTRIBUTE_ORIGINAL_QUANTITY_ORDERED + "=" +
       StringHandler.nullIfZero(customerPo855ViewBean.getOriginalQuantityOrdered()) + "," +
     ATTRIBUTE_ORIGINAL_UNIT_PRICE + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getOriginalUnitPrice()) + "," +
     ATTRIBUTE_ORIG_REQUESTED_DELIVERY_DATE + "=" +
      DateHandler.getOracleToDateFunction(customerPo855ViewBean.getOrigRequestedDeliveryDate()) + "," +
     ATTRIBUTE_ORIG_QUANTITY + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getOrigQuantity()) + "," +
     ATTRIBUTE_FROM_LOAD_ID + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getFromLoadId()) + "," +
     ATTRIBUTE_FROM_LOAD_LINE + "=" +
      StringHandler.nullIfZero(customerPo855ViewBean.getFromLoadLine()) + " " +
     "where " + ATTRIBUTE_CUSTOMER_PO_NO + "=" +
      customerPo855ViewBean.getCustomerPoNo();
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
    Collection customerPo855ViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CustomerPo855ViewBean customerPo855ViewBean = new CustomerPo855ViewBean();
      load(dataSetRow, customerPo855ViewBean);
      customerPo855ViewBeanColl.add(customerPo855ViewBean);
    }
    return customerPo855ViewBeanColl;
  }

  public Collection getExostarOrders(SearchCriteria criteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = getExostarOrders(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection getExostarOrders(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection customerPo855ViewBeanColl = new Vector();
    String query = "select * from " + EXOSTAR_TABLE + " " +
                   getWhereClause(criteria);
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CustomerPo855ViewBean customerPo855ViewBean = new CustomerPo855ViewBean();
      load(dataSetRow, customerPo855ViewBean);
      customerPo855ViewBeanColl.add(customerPo855ViewBean);
    }
    return customerPo855ViewBeanColl;
  }
/*
  public Collection getChangeOrders() throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = getChangeOrders(connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection getChangeOrders(Connection conn) throws BaseException {
    Collection customerPo855ViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
                   "where from_load_id in (select load_id from customer_po_pre_stage where change_order_sequence > 0)";
    if (log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CustomerPo855ViewBean customerPo855ViewBean = new CustomerPo855ViewBean();
      load(dataSetRow, customerPo855ViewBean);
      customerPo855ViewBeanColl.add(customerPo855ViewBean);
    }
    return customerPo855ViewBeanColl;
  }
*/
}