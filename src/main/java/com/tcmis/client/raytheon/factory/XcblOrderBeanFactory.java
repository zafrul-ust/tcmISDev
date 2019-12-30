package com.tcmis.client.raytheon.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.raytheon.beans.XcblOrderBean;
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
 * CLASSNAME: XcblOrderBeanFactory <br>
 * @version: 1.0, Jul 20, 2005 <br>
 *****************************************************************************/

public class XcblOrderBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_BUYER_ORDER_NUMBER = "BUYER_ORDER_NUMBER";
  public String ATTRIBUTE_ORDER_ISSUE_DATE = "ORDER_ISSUE_DATE";
  public String ATTRIBUTE_ACCOUNT_CODE_REF_NUM = "ACCOUNT_CODE_REF_NUM";
  public String ATTRIBUTE_PURPOSE = "PURPOSE";
  public String ATTRIBUTE_REQUEST_RESPONSE = "REQUEST_RESPONSE";
  public String ATTRIBUTE_ORDER_TYPE = "ORDER_TYPE";
  public String ATTRIBUTE_ORDER_CURRENCY = "ORDER_CURRENCY";
  public String ATTRIBUTE_ORDER_LANGUAGE = "ORDER_LANGUAGE";
  public String ATTRIBUTE_ORDER_PARTY_AGENCY = "ORDER_PARTY_AGENCY";
  public String ATTRIBUTE_ORDER_PARTY_IDENT = "ORDER_PARTY_IDENT";
  public String ATTRIBUTE_ORDER_PARTY_NAME1 = "ORDER_PARTY_NAME1";
  public String ATTRIBUTE_ORDER_PARTY_NAME2 = "ORDER_PARTY_NAME2";
  public String ATTRIBUTE_ORDER_PARTY_NAME3 = "ORDER_PARTY_NAME3";
  public String ATTRIBUTE_ORDER_PARTY_STREET = "ORDER_PARTY_STREET";
  public String ATTRIBUTE_ORDER_PARTY_POSTAL_CODE = "ORDER_PARTY_POSTAL_CODE";
  public String ATTRIBUTE_ORDER_PARTY_CITY = "ORDER_PARTY_CITY";
  public String ATTRIBUTE_ORDER_PARTY_REGION = "ORDER_PARTY_REGION";
  public String ATTRIBUTE_ORDER_PARTY_COUNTRY = "ORDER_PARTY_COUNTRY";
  public String ATTRIBUTE_ORDER_PARTY_CONTACT_NAME = "ORDER_PARTY_CONTACT_NAME";
  public String ATTRIBUTE_ORDER_PARTY_CONTACT_FUNCTION = "ORDER_PARTY_CONTACT_FUNCTION";
  public String ATTRIBUTE_SELLER_AGENCY = "SELLER_AGENCY";
  public String ATTRIBUTE_SELLER_IDENT = "SELLER_IDENT";
  public String ATTRIBUTE_SELLER_NAME1 = "SELLER_NAME1";
  public String ATTRIBUTE_SELLER_NAME2 = "SELLER_NAME2";
  public String ATTRIBUTE_SELLER_STREET = "SELLER_STREET";
  public String ATTRIBUTE_SELLER_POSTAL_CODE = "SELLER_POSTAL_CODE";
  public String ATTRIBUTE_SELLER_CITY = "SELLER_CITY";
  public String ATTRIBUTE_SELLER_REGION = "SELLER_REGION";
  public String ATTRIBUTE_SELLER_COUNTRY = "SELLER_COUNTRY";
  public String ATTRIBUTE_SHIP_TO_AGENCY = "SHIP_TO_AGENCY";
  public String ATTRIBUTE_SHIP_TO_IDENT = "SHIP_TO_IDENT";
  public String ATTRIBUTE_SHIP_TO_NAME1 = "SHIP_TO_NAME1";
  public String ATTRIBUTE_SHIP_TO_NAME2 = "SHIP_TO_NAME2";
  public String ATTRIBUTE_SHIP_TO_STREET = "SHIP_TO_STREET";
  public String ATTRIBUTE_SHIP_TO_POSTAL_CODE = "SHIP_TO_POSTAL_CODE";
  public String ATTRIBUTE_SHIP_TO_CITY = "SHIP_TO_CITY";
  public String ATTRIBUTE_SHIP_TO_REGION = "SHIP_TO_REGION";
  public String ATTRIBUTE_SHIP_TO_COUNTRY = "SHIP_TO_COUNTRY";
  public String ATTRIBUTE_SHIP_TO_CONTACT_NAME = "SHIP_TO_CONTACT_NAME";
  public String ATTRIBUTE_SHIP_TO_CONTACT_FUNCTION = "SHIP_TO_CONTACT_FUNCTION";
  public String ATTRIBUTE_BILL_TO_AGENCY = "BILL_TO_AGENCY";
  public String ATTRIBUTE_BILL_TO_IDENT = "BILL_TO_IDENT";
  public String ATTRIBUTE_BILL_TO_NAME1 = "BILL_TO_NAME1";
  public String ATTRIBUTE_BILL_TO_NAME2 = "BILL_TO_NAME2";
  public String ATTRIBUTE_BILL_TO_STREET = "BILL_TO_STREET";
  public String ATTRIBUTE_BILL_TO_POSTAL_CODE = "BILL_TO_POSTAL_CODE";
  public String ATTRIBUTE_BILL_TO_CITY = "BILL_TO_CITY";
  public String ATTRIBUTE_BILL_TO_REGION = "BILL_TO_REGION";
  public String ATTRIBUTE_BILL_TO_COUNTRY = "BILL_TO_COUNTRY";
  public String ATTRIBUTE_TERMS_OF_DELIVERY_FUNCTION = "TERMS_OF_DELIVERY_FUNCTION";
  public String ATTRIBUTE_TRANSPORT_TERMS = "TRANSPORT_TERMS";
  public String ATTRIBUTE_SHIPMENT_METHOD_OF_PAYMENT = "SHIPMENT_METHOD_OF_PAYMENT";
  public String ATTRIBUTE_LOCATION_QUALIFIER = "LOCATION_QUALIFIER";
  public String ATTRIBUTE_LOCATION_AGENCY = "LOCATION_AGENCY";
  public String ATTRIBUTE_PAYMENT_TERM = "PAYMENT_TERM";
  public String ATTRIBUTE_PAYMENT_TERMS_NOTE = "PAYMENT_TERMS_NOTE";
  public String ATTRIBUTE_PAYMENT_MEAN = "PAYMENT_MEAN";
  //public String ATTRIBUTE_ORDER_PARTY_PHONE_NUMBER = "ORDER_PARTY_PHONE_NUMBER";
  //public String ATTRIBUTE_ORDER_PARTY_FAX_NUMBER = "ORDER_PARTY_FAX_NUMBER";
  //public String ATTRIBUTE_SHIP_TO_PHONE_NUMBER = "SHIP_TO_PHONE_NUMBER";
  //public String ATTRIBUTE_SHIP_TO_FAX_NUMBER = "SHIP_TO_FAX_NUMBER";
  public String ATTRIBUTE_NOTES = "NOTES";
  public String ATTRIBUTE_MONETARY_AMOUNT = "MONETARY_AMOUNT";
  public String ATTRIBUTE_ID = "ID";

  //table name
  public String TABLE = "XCBL_ORDER";

  //constructor
  public XcblOrderBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("buyerOrderNumber")) {
      return ATTRIBUTE_BUYER_ORDER_NUMBER;
    }
    else if (attributeName.equals("orderIssueDate")) {
      return ATTRIBUTE_ORDER_ISSUE_DATE;
    }
    else if (attributeName.equals("accountCodeRefNum")) {
      return ATTRIBUTE_ACCOUNT_CODE_REF_NUM;
    }
    else if (attributeName.equals("purpose")) {
      return ATTRIBUTE_PURPOSE;
    }
    else if (attributeName.equals("requestResponse")) {
      return ATTRIBUTE_REQUEST_RESPONSE;
    }
    else if (attributeName.equals("orderType")) {
      return ATTRIBUTE_ORDER_TYPE;
    }
    else if (attributeName.equals("orderCurrency")) {
      return ATTRIBUTE_ORDER_CURRENCY;
    }
    else if (attributeName.equals("orderLanguage")) {
      return ATTRIBUTE_ORDER_LANGUAGE;
    }
    else if (attributeName.equals("orderPartyAgency")) {
      return ATTRIBUTE_ORDER_PARTY_AGENCY;
    }
    else if (attributeName.equals("orderPartyIdent")) {
      return ATTRIBUTE_ORDER_PARTY_IDENT;
    }
    else if (attributeName.equals("orderPartyName1")) {
      return ATTRIBUTE_ORDER_PARTY_NAME1;
    }
    else if (attributeName.equals("orderPartyName2")) {
      return ATTRIBUTE_ORDER_PARTY_NAME2;
    }
    else if (attributeName.equals("orderPartyName3")) {
      return ATTRIBUTE_ORDER_PARTY_NAME3;
    }
    else if (attributeName.equals("orderPartyStreet")) {
      return ATTRIBUTE_ORDER_PARTY_STREET;
    }
    else if (attributeName.equals("orderPartyPostalCode")) {
      return ATTRIBUTE_ORDER_PARTY_POSTAL_CODE;
    }
    else if (attributeName.equals("orderPartyCity")) {
      return ATTRIBUTE_ORDER_PARTY_CITY;
    }
    else if (attributeName.equals("orderPartyRegion")) {
      return ATTRIBUTE_ORDER_PARTY_REGION;
    }
    else if (attributeName.equals("orderPartyCountry")) {
      return ATTRIBUTE_ORDER_PARTY_COUNTRY;
    }
    else if (attributeName.equals("orderPartyContactName")) {
      return ATTRIBUTE_ORDER_PARTY_CONTACT_NAME;
    }
    else if (attributeName.equals("orderPartyContactFunction")) {
      return ATTRIBUTE_ORDER_PARTY_CONTACT_FUNCTION;
    }
    else if (attributeName.equals("sellerAgency")) {
      return ATTRIBUTE_SELLER_AGENCY;
    }
    else if (attributeName.equals("sellerIdent")) {
      return ATTRIBUTE_SELLER_IDENT;
    }
    else if (attributeName.equals("sellerName1")) {
      return ATTRIBUTE_SELLER_NAME1;
    }
    else if (attributeName.equals("sellerName2")) {
      return ATTRIBUTE_SELLER_NAME2;
    }
    else if (attributeName.equals("sellerStreet")) {
      return ATTRIBUTE_SELLER_STREET;
    }
    else if (attributeName.equals("sellerPostalCode")) {
      return ATTRIBUTE_SELLER_POSTAL_CODE;
    }
    else if (attributeName.equals("sellerCity")) {
      return ATTRIBUTE_SELLER_CITY;
    }
    else if (attributeName.equals("sellerRegion")) {
      return ATTRIBUTE_SELLER_REGION;
    }
    else if (attributeName.equals("sellerCountry")) {
      return ATTRIBUTE_SELLER_COUNTRY;
    }
    else if (attributeName.equals("shipToAgency")) {
      return ATTRIBUTE_SHIP_TO_AGENCY;
    }
    else if (attributeName.equals("shipToIdent")) {
      return ATTRIBUTE_SHIP_TO_IDENT;
    }
    else if (attributeName.equals("shipToName1")) {
      return ATTRIBUTE_SHIP_TO_NAME1;
    }
    else if (attributeName.equals("shipToName2")) {
      return ATTRIBUTE_SHIP_TO_NAME2;
    }
    else if (attributeName.equals("shipToStreet")) {
      return ATTRIBUTE_SHIP_TO_STREET;
    }
    else if (attributeName.equals("shipToPostalCode")) {
      return ATTRIBUTE_SHIP_TO_POSTAL_CODE;
    }
    else if (attributeName.equals("shipToCity")) {
      return ATTRIBUTE_SHIP_TO_CITY;
    }
    else if (attributeName.equals("shipToRegion")) {
      return ATTRIBUTE_SHIP_TO_REGION;
    }
    else if (attributeName.equals("shipToCountry")) {
      return ATTRIBUTE_SHIP_TO_COUNTRY;
    }
    else if (attributeName.equals("shipToContactName")) {
      return ATTRIBUTE_SHIP_TO_CONTACT_NAME;
    }
    else if (attributeName.equals("shipToContactFunction")) {
      return ATTRIBUTE_SHIP_TO_CONTACT_FUNCTION;
    }
    else if (attributeName.equals("billToAgency")) {
      return ATTRIBUTE_BILL_TO_AGENCY;
    }
    else if (attributeName.equals("billToIdent")) {
      return ATTRIBUTE_BILL_TO_IDENT;
    }
    else if (attributeName.equals("billToName1")) {
      return ATTRIBUTE_BILL_TO_NAME1;
    }
    else if (attributeName.equals("billToName2")) {
      return ATTRIBUTE_BILL_TO_NAME2;
    }
    else if (attributeName.equals("billToStreet")) {
      return ATTRIBUTE_BILL_TO_STREET;
    }
    else if (attributeName.equals("billToPostalCode")) {
      return ATTRIBUTE_BILL_TO_POSTAL_CODE;
    }
    else if (attributeName.equals("billToCity")) {
      return ATTRIBUTE_BILL_TO_CITY;
    }
    else if (attributeName.equals("billToRegion")) {
      return ATTRIBUTE_BILL_TO_REGION;
    }
    else if (attributeName.equals("billToCountry")) {
      return ATTRIBUTE_BILL_TO_COUNTRY;
    }
    else if (attributeName.equals("termsOfDeliveryFunction")) {
      return ATTRIBUTE_TERMS_OF_DELIVERY_FUNCTION;
    }
    else if (attributeName.equals("transportTerms")) {
      return ATTRIBUTE_TRANSPORT_TERMS;
    }
    else if (attributeName.equals("shipmentMethodOfPayment")) {
      return ATTRIBUTE_SHIPMENT_METHOD_OF_PAYMENT;
    }
    else if (attributeName.equals("locationQualifier")) {
      return ATTRIBUTE_LOCATION_QUALIFIER;
    }
    else if (attributeName.equals("locationAgency")) {
      return ATTRIBUTE_LOCATION_AGENCY;
    }
    else if (attributeName.equals("paymentTerm")) {
      return ATTRIBUTE_PAYMENT_TERM;
    }
    else if (attributeName.equals("paymentTermsNote")) {
      return ATTRIBUTE_PAYMENT_TERMS_NOTE;
    }
    else if (attributeName.equals("paymentMean")) {
      return ATTRIBUTE_PAYMENT_MEAN;
    }
/*
    else if (attributeName.equals("orderPartyPhoneNumber")) {
      return ATTRIBUTE_ORDER_PARTY_PHONE_NUMBER;
    }
    else if (attributeName.equals("orderPartyFaxNumber")) {
      return ATTRIBUTE_ORDER_PARTY_FAX_NUMBER;
    }
    else if (attributeName.equals("shipToPhoneNumber")) {
      return ATTRIBUTE_SHIP_TO_PHONE_NUMBER;
    }
    else if (attributeName.equals("shipToFaxNumber")) {
      return ATTRIBUTE_SHIP_TO_FAX_NUMBER;
    }
*/
    else if (attributeName.equals("notes")) {
      return ATTRIBUTE_NOTES;
    }
    else if (attributeName.equals("monetaryAmount")) {
      return ATTRIBUTE_MONETARY_AMOUNT;
    }
    else if (attributeName.equals("id")) {
      return ATTRIBUTE_ID;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, XcblOrderBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(XcblOrderBean xcblOrderBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("buyerOrderNumber", "SearchCriterion.EQUALS",
     "" + xcblOrderBean.getBuyerOrderNumber());
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
   public int delete(XcblOrderBean xcblOrderBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("buyerOrderNumber", "SearchCriterion.EQUALS",
     "" + xcblOrderBean.getBuyerOrderNumber());
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
  public int insert(XcblOrderBean xcblOrderBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(xcblOrderBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(XcblOrderBean xcblOrderBean, Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_BUYER_ORDER_NUMBER + "," +
        ATTRIBUTE_ORDER_ISSUE_DATE + "," +
        ATTRIBUTE_ACCOUNT_CODE_REF_NUM + "," +
        ATTRIBUTE_PURPOSE + "," +
        ATTRIBUTE_REQUEST_RESPONSE + "," +
        ATTRIBUTE_ORDER_TYPE + "," +
        ATTRIBUTE_ORDER_CURRENCY + "," +
        ATTRIBUTE_ORDER_LANGUAGE + "," +
        ATTRIBUTE_ORDER_PARTY_AGENCY + "," +
        ATTRIBUTE_ORDER_PARTY_IDENT + "," +
        ATTRIBUTE_ORDER_PARTY_NAME1 + "," +
        ATTRIBUTE_ORDER_PARTY_NAME2 + "," +
        ATTRIBUTE_ORDER_PARTY_NAME3 + "," +
        ATTRIBUTE_ORDER_PARTY_STREET + "," +
        ATTRIBUTE_ORDER_PARTY_POSTAL_CODE + "," +
        ATTRIBUTE_ORDER_PARTY_CITY + "," +
        ATTRIBUTE_ORDER_PARTY_REGION + "," +
        ATTRIBUTE_ORDER_PARTY_COUNTRY + "," +
        ATTRIBUTE_ORDER_PARTY_CONTACT_NAME + "," +
        ATTRIBUTE_ORDER_PARTY_CONTACT_FUNCTION + "," +
        ATTRIBUTE_SELLER_AGENCY + "," +
        ATTRIBUTE_SELLER_IDENT + "," +
        ATTRIBUTE_SELLER_NAME1 + "," +
        ATTRIBUTE_SELLER_NAME2 + "," +
        ATTRIBUTE_SELLER_STREET + "," +
        ATTRIBUTE_SELLER_POSTAL_CODE + "," +
        ATTRIBUTE_SELLER_CITY + "," +
        ATTRIBUTE_SELLER_REGION + "," +
        ATTRIBUTE_SELLER_COUNTRY + "," +
        ATTRIBUTE_SHIP_TO_AGENCY + "," +
        ATTRIBUTE_SHIP_TO_IDENT + "," +
        ATTRIBUTE_SHIP_TO_NAME1 + "," +
        ATTRIBUTE_SHIP_TO_NAME2 + "," +
        ATTRIBUTE_SHIP_TO_STREET + "," +
        ATTRIBUTE_SHIP_TO_POSTAL_CODE + "," +
        ATTRIBUTE_SHIP_TO_CITY + "," +
        ATTRIBUTE_SHIP_TO_REGION + "," +
        ATTRIBUTE_SHIP_TO_COUNTRY + "," +
        ATTRIBUTE_SHIP_TO_CONTACT_NAME + "," +
        ATTRIBUTE_SHIP_TO_CONTACT_FUNCTION + "," +
        ATTRIBUTE_BILL_TO_AGENCY + "," +
        ATTRIBUTE_BILL_TO_IDENT + "," +
        ATTRIBUTE_BILL_TO_NAME1 + "," +
        ATTRIBUTE_BILL_TO_NAME2 + "," +
        ATTRIBUTE_BILL_TO_STREET + "," +
        ATTRIBUTE_BILL_TO_POSTAL_CODE + "," +
        ATTRIBUTE_BILL_TO_CITY + "," +
        ATTRIBUTE_BILL_TO_REGION + "," +
        ATTRIBUTE_BILL_TO_COUNTRY + "," +
        ATTRIBUTE_TERMS_OF_DELIVERY_FUNCTION + "," +
        ATTRIBUTE_TRANSPORT_TERMS + "," +
        ATTRIBUTE_SHIPMENT_METHOD_OF_PAYMENT + "," +
        ATTRIBUTE_LOCATION_QUALIFIER + "," +
        ATTRIBUTE_LOCATION_AGENCY + "," +
        ATTRIBUTE_PAYMENT_TERM + "," +
        ATTRIBUTE_PAYMENT_TERMS_NOTE + "," +
        ATTRIBUTE_PAYMENT_MEAN + "," +
        //ATTRIBUTE_ORDER_PARTY_PHONE_NUMBER + "," +
        //ATTRIBUTE_ORDER_PARTY_FAX_NUMBER + "," +
        //ATTRIBUTE_SHIP_TO_PHONE_NUMBER + "," +
        //ATTRIBUTE_SHIP_TO_FAX_NUMBER + "," +
        ATTRIBUTE_NOTES + "," +
        ATTRIBUTE_MONETARY_AMOUNT + "," +
        ATTRIBUTE_ID + ")" +
        " values (" +
        SqlHandler.delimitString(xcblOrderBean.getBuyerOrderNumber()) + "," +
        DateHandler.getOracleToDateFunction(xcblOrderBean.getOrderIssueDate()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getAccountCodeRefNum()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getPurpose()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getRequestResponse()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderType()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderCurrency()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderLanguage()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyAgency()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyIdent()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyName1()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyName2()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyName3()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyStreet()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyPostalCode()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyCity()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyRegion()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyCountry()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyContactName()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getOrderPartyContactFunction()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerAgency()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerIdent()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerName1()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerName2()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerStreet()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerPostalCode()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerCity()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerRegion()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getSellerCountry()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToAgency()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToIdent()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToName1()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToName2()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToStreet()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToPostalCode()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToCity()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToRegion()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToCountry()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToContactName()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipToContactFunction()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToAgency()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToIdent()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToName1()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToName2()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToStreet()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToPostalCode()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToCity()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToRegion()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getBillToCountry()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getTermsOfDeliveryFunction()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getTransportTerms()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getShipmentMethodOfPayment()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getLocationQualifier()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getLocationAgency()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getPaymentTerm()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getPaymentTermsNote()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getPaymentMean()) + "," +
        //SqlHandler.delimitString(xcblOrderBean.getOrderPartyPhoneNumber()) + "," +
        //SqlHandler.delimitString(xcblOrderBean.getOrderPartyFaxNumber()) + "," +
        //SqlHandler.delimitString(xcblOrderBean.getShipToPhoneNumber()) + "," +
        //SqlHandler.delimitString(xcblOrderBean.getShipToFaxNumber()) + "," +
        SqlHandler.delimitString(xcblOrderBean.getNotes()) + "," +
        xcblOrderBean.getMonetaryAmount() + "," +
        xcblOrderBean.getId() + ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(XcblOrderBean xcblOrderBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(xcblOrderBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(XcblOrderBean xcblOrderBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_BUYER_ORDER_NUMBER + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBuyerOrderNumber()) + "," +
     ATTRIBUTE_ORDER_ISSUE_DATE + "=" +
      DateHandler.getOracleToDateFunction(xcblOrderBean.getOrderIssueDate()) + "," +
     ATTRIBUTE_ACCOUNT_CODE_REF_NUM + "=" +
      SqlHandler.delimitString(xcblOrderBean.getAccountCodeRefNum()) + "," +
     ATTRIBUTE_PURPOSE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getPurpose()) + "," +
     ATTRIBUTE_REQUEST_RESPONSE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getRequestResponse()) + "," +
     ATTRIBUTE_ORDER_TYPE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderType()) + "," +
     ATTRIBUTE_ORDER_CURRENCY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderCurrency()) + "," +
     ATTRIBUTE_ORDER_LANGUAGE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderLanguage()) + "," +
     ATTRIBUTE_ORDER_PARTY_AGENCY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyAgency()) + "," +
     ATTRIBUTE_ORDER_PARTY_IDENT + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyIdent()) + "," +
     ATTRIBUTE_ORDER_PARTY_NAME1 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyName1()) + "," +
     ATTRIBUTE_ORDER_PARTY_NAME2 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyName2()) + "," +
     ATTRIBUTE_ORDER_PARTY_NAME3 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyName3()) + "," +
     ATTRIBUTE_ORDER_PARTY_STREET + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyStreet()) + "," +
     ATTRIBUTE_ORDER_PARTY_POSTAL_CODE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyPostalCode()) + "," +
     ATTRIBUTE_ORDER_PARTY_CITY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyCity()) + "," +
     ATTRIBUTE_ORDER_PARTY_REGION + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyRegion()) + "," +
     ATTRIBUTE_ORDER_PARTY_COUNTRY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyCountry()) + "," +
     ATTRIBUTE_ORDER_PARTY_CONTACT_NAME + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyContactName()) + "," +
     ATTRIBUTE_ORDER_PARTY_CONTACT_FUNCTION + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyContactFunction()) + "," +
     ATTRIBUTE_SELLER_AGENCY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerAgency()) + "," +
     ATTRIBUTE_SELLER_IDENT + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerIdent()) + "," +
     ATTRIBUTE_SELLER_NAME1 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerName1()) + "," +
     ATTRIBUTE_SELLER_NAME2 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerName2()) + "," +
     ATTRIBUTE_SELLER_STREET + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerStreet()) + "," +
     ATTRIBUTE_SELLER_POSTAL_CODE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerPostalCode()) + "," +
     ATTRIBUTE_SELLER_CITY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerCity()) + "," +
     ATTRIBUTE_SELLER_REGION + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerRegion()) + "," +
     ATTRIBUTE_SELLER_COUNTRY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getSellerCountry()) + "," +
     ATTRIBUTE_SHIP_TO_AGENCY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToAgency()) + "," +
     ATTRIBUTE_SHIP_TO_IDENT + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToIdent()) + "," +
     ATTRIBUTE_SHIP_TO_NAME1 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToName1()) + "," +
     ATTRIBUTE_SHIP_TO_NAME2 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToName2()) + "," +
     ATTRIBUTE_SHIP_TO_STREET + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToStreet()) + "," +
     ATTRIBUTE_SHIP_TO_POSTAL_CODE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToPostalCode()) + "," +
     ATTRIBUTE_SHIP_TO_CITY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToCity()) + "," +
     ATTRIBUTE_SHIP_TO_REGION + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToRegion()) + "," +
     ATTRIBUTE_SHIP_TO_COUNTRY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToCountry()) + "," +
     ATTRIBUTE_SHIP_TO_CONTACT_NAME + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToContactName()) + "," +
     ATTRIBUTE_SHIP_TO_CONTACT_FUNCTION + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToContactFunction()) + "," +
     ATTRIBUTE_BILL_TO_AGENCY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToAgency()) + "," +
     ATTRIBUTE_BILL_TO_IDENT + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToIdent()) + "," +
     ATTRIBUTE_BILL_TO_NAME1 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToName1()) + "," +
     ATTRIBUTE_BILL_TO_NAME2 + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToName2()) + "," +
     ATTRIBUTE_BILL_TO_STREET + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToStreet()) + "," +
     ATTRIBUTE_BILL_TO_POSTAL_CODE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToPostalCode()) + "," +
     ATTRIBUTE_BILL_TO_CITY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToCity()) + "," +
     ATTRIBUTE_BILL_TO_REGION + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToRegion()) + "," +
     ATTRIBUTE_BILL_TO_COUNTRY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getBillToCountry()) + "," +
     ATTRIBUTE_TERMS_OF_DELIVERY_FUNCTION + "=" +
      SqlHandler.delimitString(xcblOrderBean.getTermsOfDeliveryFunction()) + "," +
     ATTRIBUTE_TRANSPORT_TERMS + "=" +
      SqlHandler.delimitString(xcblOrderBean.getTransportTerms()) + "," +
     ATTRIBUTE_SHIPMENT_METHOD_OF_PAYMENT + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipmentMethodOfPayment()) + "," +
     ATTRIBUTE_LOCATION_QUALIFIER + "=" +
      SqlHandler.delimitString(xcblOrderBean.getLocationQualifier()) + "," +
     ATTRIBUTE_LOCATION_AGENCY + "=" +
      SqlHandler.delimitString(xcblOrderBean.getLocationAgency()) + "," +
     ATTRIBUTE_PAYMENT_TERM + "=" +
      SqlHandler.delimitString(xcblOrderBean.getPaymentTerm()) + "," +
     ATTRIBUTE_PAYMENT_TERMS_NOTE + "=" +
      SqlHandler.delimitString(xcblOrderBean.getPaymentTermsNote()) + "," +
     ATTRIBUTE_PAYMENT_MEAN + "=" +
      SqlHandler.delimitString(xcblOrderBean.getPaymentMean()) + "," +
     ATTRIBUTE_ORDER_PARTY_PHONE_NUMBER + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyPhoneNumber()) + "," +
     ATTRIBUTE_ORDER_PARTY_FAX_NUMBER + "=" +
      SqlHandler.delimitString(xcblOrderBean.getOrderPartyFaxNumber()) + "," +
     ATTRIBUTE_SHIP_TO_PHONE_NUMBER + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToPhoneNumber()) + "," +
     ATTRIBUTE_SHIP_TO_FAX_NUMBER + "=" +
      SqlHandler.delimitString(xcblOrderBean.getShipToFaxNumber()) + "," +
     ATTRIBUTE_NOTES + "=" +
      SqlHandler.delimitString(xcblOrderBean.getNotes()) + "," +
     ATTRIBUTE_MONETARY_AMOUNT + "=" +
      StringHandler.nullIfZero(xcblOrderBean.getMonetaryAmount()) + "," +
     ATTRIBUTE_ID + "=" +
      StringHandler.nullIfZero(xcblOrderBean.getId()) + " " +
     "where " + ATTRIBUTE_BUYER_ORDER_NUMBER + "=" +
      xcblOrderBean.getBuyerOrderNumber();
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
    Collection xcblOrderBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      XcblOrderBean xcblOrderBean = new XcblOrderBean();
      load(dataSetRow, xcblOrderBean);
      xcblOrderBeanColl.add(xcblOrderBean);
    }
    return xcblOrderBeanColl;
  }
}