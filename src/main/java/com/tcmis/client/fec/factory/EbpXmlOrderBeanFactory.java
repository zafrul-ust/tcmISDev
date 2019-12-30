package com.tcmis.client.fec.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.fec.beans.EbpXmlOrderBean;


/******************************************************************************
 * CLASSNAME: EbpXmlOrderBeanFactory <br>
 * @version: 1.0, Sep 7, 2005 <br>
 *****************************************************************************/


public class EbpXmlOrderBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BUYERORDERNUMBER = "BUYERORDERNUMBER";
	public String ATTRIBUTE_ISSUEDATE = "ISSUEDATE";
	public String ATTRIBUTE_CURRENCY = "CURRENCY";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_BUYER_ADDR1 = "BUYER_ADDR1";
	public String ATTRIBUTE_BUYER_ZIP = "BUYER_ZIP";
	public String ATTRIBUTE_BUYER_CITY = "BUYER_CITY";
	public String ATTRIBUTE_BUYER_STATE = "BUYER_STATE";
	public String ATTRIBUTE_BUYER_COUNTRY = "BUYER_COUNTRY";
	public String ATTRIBUTE_BUYER_CONTACT = "BUYER_CONTACT";
	public String ATTRIBUTE_SELLER_PARTYID = "SELLER_PARTYID";
	public String ATTRIBUTE_SELLER_NAME = "SELLER_NAME";
	public String ATTRIBUTE_SELLER_ADDR1 = "SELLER_ADDR1";
	public String ATTRIBUTE_SELLER_CITY = "SELLER_CITY";
	public String ATTRIBUTE_SELLER_STATE = "SELLER_STATE";
	public String ATTRIBUTE_SELLER_ZIP = "SELLER_ZIP";
	public String ATTRIBUTE_SELLER_COUNTRY = "SELLER_COUNTRY";
	public String ATTRIBUTE_SHIPTO_PARTYID = "SHIPTO_PARTYID";
	public String ATTRIBUTE_SHIPTO_NAME1 = "SHIPTO_NAME1";
	public String ATTRIBUTE_SHIPTO_NAME2 = "SHIPTO_NAME2";
	public String ATTRIBUTE_SHIPTO_NAME3 = "SHIPTO_NAME3";
	public String ATTRIBUTE_SHIPTO_ADDR1 = "SHIPTO_ADDR1";
	public String ATTRIBUTE_SHIPTO_CITY = "SHIPTO_CITY";
	public String ATTRIBUTE_SHIPTO_STATE = "SHIPTO_STATE";
	public String ATTRIBUTE_SHIPTO_ZIP = "SHIPTO_ZIP";
	public String ATTRIBUTE_SHIPTO_COUNTRY = "SHIPTO_COUNTRY";
	public String ATTRIBUTE_BILLTO_PARTYID = "BILLTO_PARTYID";
	public String ATTRIBUTE_BILLTO_NAME = "BILLTO_NAME";
	public String ATTRIBUTE_PAYMENT_TERM = "PAYMENT_TERM";
	public String ATTRIBUTE_PAYMENT_MEAN = "PAYMENT_MEAN";
	public String ATTRIBUTE_CARD_NUMBER = "CARD_NUMBER";
	public String ATTRIBUTE_CARD_TYPE = "CARD_TYPE";
	public String ATTRIBUTE_CARD_HOLDER_NAME = "CARD_HOLDER_NAME";
	public String ATTRIBUTE_CARD_EXP_DATE = "CARD_EXP_DATE";
	public String ATTRIBUTE_LINE_NO = "LINE_NO";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_MFG_PARTNUM = "MFG_PARTNUM";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_RECIPIENT_ID = "RECIPIENT_ID";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_UNIT_PRICE_CURRENCY = "UNIT_PRICE_CURRENCY";
	public String ATTRIBUTE_LINE_TOTAL_AMOUNT = "LINE_TOTAL_AMOUNT";
	public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_ITEM_PKG = "ITEM_PKG";
	public String ATTRIBUTE_BUYER_PARTNUM = "BUYER_PARTNUM";
	public String ATTRIBUTE_TOTAL_LINES = "TOTAL_LINES";
	public String ATTRIBUTE_TOTAL_AMOUNT = "TOTAL_AMOUNT";
	public String ATTRIBUTE_CONTACT_NAME = "CONTACT_NAME";
	public String ATTRIBUTE_CONTACT_PHONE = "CONTACT_PHONE";
	public String ATTRIBUTE_CONTACT_FAX = "CONTACT_FAX";
	public String ATTRIBUTE_CONTACT_EMAIL = "CONTACT_EMAIL";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_STATUS_DATE = "STATUS_DATE";
	public String ATTRIBUTE_ERROR = "ERROR";

	//table name
	public String TABLE = "EBP_XML_ORDER";


	//constructor
	public EbpXmlOrderBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("buyerordernumber")) {
			return ATTRIBUTE_BUYERORDERNUMBER;
		}
		else if(attributeName.equals("issuedate")) {
			return ATTRIBUTE_ISSUEDATE;
		}
		else if(attributeName.equals("currency")) {
			return ATTRIBUTE_CURRENCY;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("buyerAddr1")) {
			return ATTRIBUTE_BUYER_ADDR1;
		}
		else if(attributeName.equals("buyerZip")) {
			return ATTRIBUTE_BUYER_ZIP;
		}
		else if(attributeName.equals("buyerCity")) {
			return ATTRIBUTE_BUYER_CITY;
		}
		else if(attributeName.equals("buyerState")) {
			return ATTRIBUTE_BUYER_STATE;
		}
		else if(attributeName.equals("buyerCountry")) {
			return ATTRIBUTE_BUYER_COUNTRY;
		}
		else if(attributeName.equals("buyerContact")) {
			return ATTRIBUTE_BUYER_CONTACT;
		}
		else if(attributeName.equals("sellerPartyid")) {
			return ATTRIBUTE_SELLER_PARTYID;
		}
		else if(attributeName.equals("sellerName")) {
			return ATTRIBUTE_SELLER_NAME;
		}
		else if(attributeName.equals("sellerAddr1")) {
			return ATTRIBUTE_SELLER_ADDR1;
		}
		else if(attributeName.equals("sellerCity")) {
			return ATTRIBUTE_SELLER_CITY;
		}
		else if(attributeName.equals("sellerState")) {
			return ATTRIBUTE_SELLER_STATE;
		}
		else if(attributeName.equals("sellerZip")) {
			return ATTRIBUTE_SELLER_ZIP;
		}
		else if(attributeName.equals("sellerCountry")) {
			return ATTRIBUTE_SELLER_COUNTRY;
		}
		else if(attributeName.equals("shiptoPartyid")) {
			return ATTRIBUTE_SHIPTO_PARTYID;
		}
		else if(attributeName.equals("shiptoName1")) {
			return ATTRIBUTE_SHIPTO_NAME1;
		}
		else if(attributeName.equals("shiptoName2")) {
			return ATTRIBUTE_SHIPTO_NAME2;
		}
		else if(attributeName.equals("shiptoName3")) {
			return ATTRIBUTE_SHIPTO_NAME3;
		}
		else if(attributeName.equals("shiptoAddr1")) {
			return ATTRIBUTE_SHIPTO_ADDR1;
		}
		else if(attributeName.equals("shiptoCity")) {
			return ATTRIBUTE_SHIPTO_CITY;
		}
		else if(attributeName.equals("shiptoState")) {
			return ATTRIBUTE_SHIPTO_STATE;
		}
		else if(attributeName.equals("shiptoZip")) {
			return ATTRIBUTE_SHIPTO_ZIP;
		}
		else if(attributeName.equals("shiptoCountry")) {
			return ATTRIBUTE_SHIPTO_COUNTRY;
		}
		else if(attributeName.equals("billtoPartyid")) {
			return ATTRIBUTE_BILLTO_PARTYID;
		}
		else if(attributeName.equals("billtoName")) {
			return ATTRIBUTE_BILLTO_NAME;
		}
		else if(attributeName.equals("paymentTerm")) {
			return ATTRIBUTE_PAYMENT_TERM;
		}
		else if(attributeName.equals("paymentMean")) {
			return ATTRIBUTE_PAYMENT_MEAN;
		}
		else if(attributeName.equals("cardNumber")) {
			return ATTRIBUTE_CARD_NUMBER;
		}
		else if(attributeName.equals("cardType")) {
			return ATTRIBUTE_CARD_TYPE;
		}
		else if(attributeName.equals("cardHolderName")) {
			return ATTRIBUTE_CARD_HOLDER_NAME;
		}
		else if(attributeName.equals("cardExpDate")) {
			return ATTRIBUTE_CARD_EXP_DATE;
		}
		else if(attributeName.equals("lineNo")) {
			return ATTRIBUTE_LINE_NO;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("mfgPartnum")) {
			return ATTRIBUTE_MFG_PARTNUM;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("recipientId")) {
			return ATTRIBUTE_RECIPIENT_ID;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("unitPriceCurrency")) {
			return ATTRIBUTE_UNIT_PRICE_CURRENCY;
		}
		else if(attributeName.equals("lineTotalAmount")) {
			return ATTRIBUTE_LINE_TOTAL_AMOUNT;
		}
		else if(attributeName.equals("mrNumber")) {
			return ATTRIBUTE_MR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("itemPkg")) {
			return ATTRIBUTE_ITEM_PKG;
		}
		else if(attributeName.equals("buyerPartnum")) {
			return ATTRIBUTE_BUYER_PARTNUM;
		}
		else if(attributeName.equals("totalLines")) {
			return ATTRIBUTE_TOTAL_LINES;
		}
		else if(attributeName.equals("totalAmount")) {
			return ATTRIBUTE_TOTAL_AMOUNT;
		}
		else if(attributeName.equals("contactName")) {
			return ATTRIBUTE_CONTACT_NAME;
		}
		else if(attributeName.equals("contactPhone")) {
			return ATTRIBUTE_CONTACT_PHONE;
		}
		else if(attributeName.equals("contactFax")) {
			return ATTRIBUTE_CONTACT_FAX;
		}
		else if(attributeName.equals("contactEmail")) {
			return ATTRIBUTE_CONTACT_EMAIL;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("statusDate")) {
			return ATTRIBUTE_STATUS_DATE;
		}
		else if(attributeName.equals("error")) {
			return ATTRIBUTE_ERROR;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, EbpXmlOrderBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(EbpXmlOrderBean ebpXmlOrderBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerordernumber", "SearchCriterion.EQUALS",
			"" + ebpXmlOrderBean.getBuyerordernumber());

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


	public int delete(EbpXmlOrderBean ebpXmlOrderBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerordernumber", "SearchCriterion.EQUALS",
			"" + ebpXmlOrderBean.getBuyerordernumber());

		return delete(criteria, conn);
	}
*/

	public int delete(SearchCriteria criteria)
		throws BaseException {

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


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


	//insert
	public int insert(EbpXmlOrderBean ebpXmlOrderBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(ebpXmlOrderBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(EbpXmlOrderBean ebpXmlOrderBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BUYERORDERNUMBER + "," +
			ATTRIBUTE_ISSUEDATE + "," +
			ATTRIBUTE_CURRENCY + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_BUYER_ADDR1 + "," +
			ATTRIBUTE_BUYER_ZIP + "," +
			ATTRIBUTE_BUYER_CITY + "," +
			ATTRIBUTE_BUYER_STATE + "," +
			ATTRIBUTE_BUYER_COUNTRY + "," +
			ATTRIBUTE_BUYER_CONTACT + "," +
			ATTRIBUTE_SELLER_PARTYID + "," +
			ATTRIBUTE_SELLER_NAME + "," +
			ATTRIBUTE_SELLER_ADDR1 + "," +
			ATTRIBUTE_SELLER_CITY + "," +
			ATTRIBUTE_SELLER_STATE + "," +
			ATTRIBUTE_SELLER_ZIP + "," +
			ATTRIBUTE_SELLER_COUNTRY + "," +
			ATTRIBUTE_SHIPTO_PARTYID + "," +
			ATTRIBUTE_SHIPTO_NAME1 + "," +
			ATTRIBUTE_SHIPTO_NAME2 + "," +
			ATTRIBUTE_SHIPTO_NAME3 + "," +
			ATTRIBUTE_SHIPTO_ADDR1 + "," +
			ATTRIBUTE_SHIPTO_CITY + "," +
			ATTRIBUTE_SHIPTO_STATE + "," +
			ATTRIBUTE_SHIPTO_ZIP + "," +
			ATTRIBUTE_SHIPTO_COUNTRY + "," +
			ATTRIBUTE_BILLTO_PARTYID + "," +
			ATTRIBUTE_BILLTO_NAME + "," +
			ATTRIBUTE_PAYMENT_TERM + "," +
			ATTRIBUTE_PAYMENT_MEAN + "," +
			ATTRIBUTE_CARD_NUMBER + "," +
			ATTRIBUTE_CARD_TYPE + "," +
			ATTRIBUTE_CARD_HOLDER_NAME + "," +
			ATTRIBUTE_CARD_EXP_DATE + "," +
			ATTRIBUTE_LINE_NO + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_MFG_PARTNUM + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_RECIPIENT_ID + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_UNIT_PRICE_CURRENCY + "," +
			ATTRIBUTE_LINE_TOTAL_AMOUNT + "," +
			ATTRIBUTE_MR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_ITEM_PKG + "," +
			ATTRIBUTE_BUYER_PARTNUM + "," +
			ATTRIBUTE_TOTAL_LINES + "," +
			ATTRIBUTE_TOTAL_AMOUNT + "," +
			ATTRIBUTE_CONTACT_NAME + "," +
			ATTRIBUTE_CONTACT_PHONE + "," +
			ATTRIBUTE_CONTACT_FAX + "," +
			ATTRIBUTE_CONTACT_EMAIL + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_STATUS_DATE + "," +
			ATTRIBUTE_ERROR + ")" +
                        " values (" +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerordernumber()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getIssuedate()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getCurrency()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerName()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerAddr1()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerZip()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerCity()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerState()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerCountry()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerContact()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getSellerPartyid()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getSellerName()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getSellerAddr1()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getSellerCity()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getSellerState()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getSellerZip()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getSellerCountry()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoPartyid()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoName1()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoName2()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoName3()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoAddr1()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoCity()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoState()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoZip()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getShiptoCountry()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBilltoPartyid()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBilltoName()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getPaymentTerm()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getPaymentMean()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getCardNumber()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getCardType()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getCardHolderName()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getCardExpDate()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getLineNo()) + "," +
			StringHandler.nullIfZero(ebpXmlOrderBean.getItemId()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getMfgPartnum()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getItemDesc()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getQuantity()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getUom()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getRecipientId()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getUnitPrice()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getUnitPriceCurrency()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getLineTotalAmount()) + "," +
			StringHandler.nullIfZero(ebpXmlOrderBean.getMrNumber()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getLineItem()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getItemPkg()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getBuyerPartnum()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getTotalLines()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getTotalAmount()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getContactName()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getContactPhone()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getContactFax()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getContactEmail()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getStatus()) + "," +
			DateHandler.getOracleToDateFunction(ebpXmlOrderBean.getStatusDate()) + "," +
			SqlHandler.delimitString(ebpXmlOrderBean.getError()) + ")";

		return sqlManager.update(conn, query);
	}

//you need to verify the primary key(s) before uncommenting this
/*

	//update
	public int update(EbpXmlOrderBean ebpXmlOrderBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(ebpXmlOrderBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(EbpXmlOrderBean ebpXmlOrderBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BUYERORDERNUMBER + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerordernumber()) + "," +
			ATTRIBUTE_ISSUEDATE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getIssuedate()) + "," +
			ATTRIBUTE_CURRENCY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getCurrency()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerName()) + "," +
			ATTRIBUTE_BUYER_ADDR1 + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerAddr1()) + "," +
			ATTRIBUTE_BUYER_ZIP + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerZip()) + "," +
			ATTRIBUTE_BUYER_CITY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerCity()) + "," +
			ATTRIBUTE_BUYER_STATE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerState()) + "," +
			ATTRIBUTE_BUYER_COUNTRY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerCountry()) + "," +
			ATTRIBUTE_BUYER_CONTACT + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerContact()) + "," +
			ATTRIBUTE_SELLER_PARTYID + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getSellerPartyid()) + "," +
			ATTRIBUTE_SELLER_NAME + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getSellerName()) + "," +
			ATTRIBUTE_SELLER_ADDR1 + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getSellerAddr1()) + "," +
			ATTRIBUTE_SELLER_CITY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getSellerCity()) + "," +
			ATTRIBUTE_SELLER_STATE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getSellerState()) + "," +
			ATTRIBUTE_SELLER_ZIP + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getSellerZip()) + "," +
			ATTRIBUTE_SELLER_COUNTRY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getSellerCountry()) + "," +
			ATTRIBUTE_SHIPTO_PARTYID + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoPartyid()) + "," +
			ATTRIBUTE_SHIPTO_NAME1 + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoName1()) + "," +
			ATTRIBUTE_SHIPTO_NAME2 + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoName2()) + "," +
			ATTRIBUTE_SHIPTO_NAME3 + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoName3()) + "," +
			ATTRIBUTE_SHIPTO_ADDR1 + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoAddr1()) + "," +
			ATTRIBUTE_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoCity()) + "," +
			ATTRIBUTE_SHIPTO_STATE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoState()) + "," +
			ATTRIBUTE_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoZip()) + "," +
			ATTRIBUTE_SHIPTO_COUNTRY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getShiptoCountry()) + "," +
			ATTRIBUTE_BILLTO_PARTYID + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBilltoPartyid()) + "," +
			ATTRIBUTE_BILLTO_NAME + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBilltoName()) + "," +
			ATTRIBUTE_PAYMENT_TERM + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getPaymentTerm()) + "," +
			ATTRIBUTE_PAYMENT_MEAN + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getPaymentMean()) + "," +
			ATTRIBUTE_CARD_NUMBER + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getCardNumber()) + "," +
			ATTRIBUTE_CARD_TYPE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getCardType()) + "," +
			ATTRIBUTE_CARD_HOLDER_NAME + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getCardHolderName()) + "," +
			ATTRIBUTE_CARD_EXP_DATE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getCardExpDate()) + "," +
			ATTRIBUTE_LINE_NO + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getLineNo()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(ebpXmlOrderBean.getItemId()) + "," +
			ATTRIBUTE_MFG_PARTNUM + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getMfgPartnum()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getItemDesc()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getQuantity()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getUom()) + "," +
			ATTRIBUTE_RECIPIENT_ID + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getRecipientId()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getUnitPrice()) + "," +
			ATTRIBUTE_UNIT_PRICE_CURRENCY + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getUnitPriceCurrency()) + "," +
			ATTRIBUTE_LINE_TOTAL_AMOUNT + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getLineTotalAmount()) + "," +
			ATTRIBUTE_MR_NUMBER + "=" + 
				StringHandler.nullIfZero(ebpXmlOrderBean.getMrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getLineItem()) + "," +
			ATTRIBUTE_ITEM_PKG + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getItemPkg()) + "," +
			ATTRIBUTE_BUYER_PARTNUM + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getBuyerPartnum()) + "," +
			ATTRIBUTE_TOTAL_LINES + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getTotalLines()) + "," +
			ATTRIBUTE_TOTAL_AMOUNT + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getTotalAmount()) + "," +
			ATTRIBUTE_CONTACT_NAME + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getContactName()) + "," +
			ATTRIBUTE_CONTACT_PHONE + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getContactPhone()) + "," +
			ATTRIBUTE_CONTACT_FAX + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getContactFax()) + "," +
			ATTRIBUTE_CONTACT_EMAIL + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getContactEmail()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getStatus()) + "," +
			ATTRIBUTE_STATUS_DATE + "=" + 
				DateHandler.getOracleToDateFunction(ebpXmlOrderBean.getStatusDate()) + "," +
			ATTRIBUTE_ERROR + "=" + 
				SqlHandler.delimitString(ebpXmlOrderBean.getError()) + " " +
			"where " + ATTRIBUTE_BUYERORDERNUMBER + "=" +
				StringHandler.nullIfZero(ebpXmlOrderBean.getBuyerordernumber());

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection ebpXmlOrderBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			EbpXmlOrderBean ebpXmlOrderBean = new EbpXmlOrderBean();
			load(dataSetRow, ebpXmlOrderBean);
			ebpXmlOrderBeanColl.add(ebpXmlOrderBean);
		}

		return ebpXmlOrderBeanColl;
	}
}