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
import com.tcmis.client.fec.beans.FecEbpXmlOrderBean;


/******************************************************************************
 * CLASSNAME: FecEbpXmlOrderBeanFactory <br>
 * @version: 1.0, Jul 20, 2005 <br>
 *****************************************************************************/


public class FecEbpXmlOrderBeanFactory extends BaseBeanFactory {

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
	public String ATTRIBUTE_MR_NO = "MR_NO";
	public String ATTRIBUTE_MR_LINE_NO = "MR_LINE_NO";
	public String ATTRIBUTE_ITEM_PKG = "ITEM_PKG";
	public String ATTRIBUTE_BUYER_PARTNUM = "BUYER_PARTNUM";
	public String ATTRIBUTE_TOTAL_LINES = "TOTAL_LINES";
	public String ATTRIBUTE_TOTAL_AMOUNT = "TOTAL_AMOUNT";

	//table name
	public String TABLE = "FEC_EBP_XML_ORDER";


	//constructor
	public FecEbpXmlOrderBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("mrNo")) {
			return ATTRIBUTE_MR_NO;
		}
		else if(attributeName.equals("mrLineNo")) {
			return ATTRIBUTE_MR_LINE_NO;
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
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FecEbpXmlOrderBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(FecEbpXmlOrderBean fecEbpXmlOrderBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerordernumber", "SearchCriterion.EQUALS",
			"" + fecEbpXmlOrderBean.getBuyerordernumber());

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


	public int delete(FecEbpXmlOrderBean fecEbpXmlOrderBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerordernumber", "SearchCriterion.EQUALS",
			"" + fecEbpXmlOrderBean.getBuyerordernumber());

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


//you need to verify the primary key(s) before uncommenting this
	//insert
	public int insert(FecEbpXmlOrderBean fecEbpXmlOrderBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(fecEbpXmlOrderBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(FecEbpXmlOrderBean fecEbpXmlOrderBean, Connection conn)
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
			ATTRIBUTE_MR_NO + "," +
			ATTRIBUTE_MR_LINE_NO + "," +
			ATTRIBUTE_ITEM_PKG + "," +
			ATTRIBUTE_BUYER_PARTNUM + "," +
			ATTRIBUTE_TOTAL_LINES + "," +
			ATTRIBUTE_TOTAL_AMOUNT + ")" +
                        " values ( " +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerordernumber()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getIssuedate()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getCurrency()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerName()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerAddr1()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerZip()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerCity()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerState()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerCountry()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerContact()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerPartyid()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerName()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerAddr1()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerCity()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerState()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerZip()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerCountry()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoPartyid()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoName1()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoName2()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoName3()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoAddr1()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoCity()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoState()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoZip()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoCountry()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBilltoPartyid()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBilltoName()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getPaymentTerm()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getPaymentMean()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getCardNumber()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getCardType()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getCardHolderName()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getCardExpDate()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getLineNo()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getItemId()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getMfgPartnum()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getItemDesc()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getQuantity()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getUom()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getRecipientId()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getUnitPrice()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getUnitPriceCurrency()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getLineTotalAmount()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getMrNo()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getMrLineNo()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getItemPkg()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerPartnum()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getTotalLines()) + "," +
			SqlHandler.delimitString(fecEbpXmlOrderBean.getTotalAmount()) + ")";

		return sqlManager.update(conn, query);
	}

/*

	//update
	public int update(FecEbpXmlOrderBean fecEbpXmlOrderBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(fecEbpXmlOrderBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(FecEbpXmlOrderBean fecEbpXmlOrderBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BUYERORDERNUMBER + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerordernumber()) + "," +
			ATTRIBUTE_ISSUEDATE + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getIssuedate()) + "," +
			ATTRIBUTE_CURRENCY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getCurrency()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerName()) + "," +
			ATTRIBUTE_BUYER_ADDR1 + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerAddr1()) + "," +
			ATTRIBUTE_BUYER_ZIP + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerZip()) + "," +
			ATTRIBUTE_BUYER_CITY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerCity()) + "," +
			ATTRIBUTE_BUYER_STATE + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerState()) + "," +
			ATTRIBUTE_BUYER_COUNTRY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerCountry()) + "," +
			ATTRIBUTE_BUYER_CONTACT + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerContact()) + "," +
			ATTRIBUTE_SELLER_PARTYID + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerPartyid()) + "," +
			ATTRIBUTE_SELLER_NAME + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerName()) + "," +
			ATTRIBUTE_SELLER_ADDR1 + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerAddr1()) + "," +
			ATTRIBUTE_SELLER_CITY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerCity()) + "," +
			ATTRIBUTE_SELLER_STATE + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerState()) + "," +
			ATTRIBUTE_SELLER_ZIP + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerZip()) + "," +
			ATTRIBUTE_SELLER_COUNTRY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getSellerCountry()) + "," +
			ATTRIBUTE_SHIPTO_PARTYID + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoPartyid()) + "," +
			ATTRIBUTE_SHIPTO_NAME1 + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoName1()) + "," +
			ATTRIBUTE_SHIPTO_NAME2 + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoName2()) + "," +
			ATTRIBUTE_SHIPTO_NAME3 + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoName3()) + "," +
			ATTRIBUTE_SHIPTO_ADDR1 + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoAddr1()) + "," +
			ATTRIBUTE_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoCity()) + "," +
			ATTRIBUTE_SHIPTO_STATE + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoState()) + "," +
			ATTRIBUTE_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoZip()) + "," +
			ATTRIBUTE_SHIPTO_COUNTRY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getShiptoCountry()) + "," +
			ATTRIBUTE_BILLTO_PARTYID + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBilltoPartyid()) + "," +
			ATTRIBUTE_BILLTO_NAME + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBilltoName()) + "," +
			ATTRIBUTE_PAYMENT_TERM + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getPaymentTerm()) + "," +
			ATTRIBUTE_PAYMENT_MEAN + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getPaymentMean()) + "," +
			ATTRIBUTE_CARD_NUMBER + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getCardNumber()) + "," +
			ATTRIBUTE_CARD_TYPE + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getCardType()) + "," +
			ATTRIBUTE_CARD_HOLDER_NAME + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getCardHolderName()) + "," +
			ATTRIBUTE_CARD_EXP_DATE + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getCardExpDate()) + "," +
			ATTRIBUTE_LINE_NO + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getLineNo()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getItemId()) + "," +
			ATTRIBUTE_MFG_PARTNUM + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getMfgPartnum()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getItemDesc()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getQuantity()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getUom()) + "," +
			ATTRIBUTE_RECIPIENT_ID + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getRecipientId()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getUnitPrice()) + "," +
			ATTRIBUTE_UNIT_PRICE_CURRENCY + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getUnitPriceCurrency()) + "," +
			ATTRIBUTE_LINE_TOTAL_AMOUNT + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getLineTotalAmount()) + "," +
			ATTRIBUTE_MR_NO + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getMrNo()) + "," +
			ATTRIBUTE_MR_LINE_NO + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getMrLineNo()) + "," +
			ATTRIBUTE_ITEM_PKG + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getItemPkg()) + "," +
			ATTRIBUTE_BUYER_PARTNUM + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getBuyerPartnum()) + "," +
			ATTRIBUTE_TOTAL_LINES + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getTotalLines()) + "," +
			ATTRIBUTE_TOTAL_AMOUNT + "=" + 
				SqlHandler.delimitString(fecEbpXmlOrderBean.getTotalAmount()) + " " +
			"where " + ATTRIBUTE_BUYERORDERNUMBER + "=" +
				StringHandler.nullIfZero(fecEbpXmlOrderBean.getBuyerordernumber());

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

		Collection fecEbpXmlOrderBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FecEbpXmlOrderBean fecEbpXmlOrderBean = new FecEbpXmlOrderBean();
			load(dataSetRow, fecEbpXmlOrderBean);
			fecEbpXmlOrderBeanColl.add(fecEbpXmlOrderBean);
		}

		return fecEbpXmlOrderBeanColl;
	}
}