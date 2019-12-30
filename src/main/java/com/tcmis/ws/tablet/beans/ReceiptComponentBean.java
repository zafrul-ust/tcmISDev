package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ReceiptDescriptionViewBean <br>
 * 
 * @version: 1.0, Aug 23, 2005 <br>
 *****************************************************************************/
public class ReceiptComponentBean extends BaseDataBean {
	private static SimpleDateFormat	dateParser	= new SimpleDateFormat("MM/dd/yyyy");
	public static Log				log			= LogFactory.getLog(ReceiptComponentBean.class);
	
	private String					bin;
	private Date					expireDate;
	private BigDecimal				itemId;
	private Date					mfgLabelExpireDate;
	private String					mfgLot;
	private BigDecimal				partId;
	private String					quantity;
	private BigDecimal				receiptId;

	// constructor
	public ReceiptComponentBean() {
	}

	// constructor
	public ReceiptComponentBean(JSONObject json) {
		try {
			// this.receiptId = getNumber(json, "receiptId");
			this.partId = getNumber(json, "partId");
			this.expireDate = getDate(json, "expireDate");
			this.mfgLot = getString(json, "mfgLot");
			this.bin = getString(json, "bin");
			this.itemId = getNumber(json, "itemId");
			this.quantity = getString(json, "quantity");
			this.mfgLabelExpireDate = getDate(json, "mfgLabelExpireDate");
		}
		catch (Exception e) {
			log.error("Error creating ReceiptComponentBean from JSON Object", e);
		}
	}

	public String getBin() {
		return bin;
	}

	private Date getDate(JSONObject json, String key) {
		String value = null;
		try {
			value = getString(json, key);
			return StringHandler.isBlankString(value) ? null : dateParser.parse(value);
		}
		catch (Exception e) {
			log.error("Error parsing date from JSON Object, field: " + key + ", value: " + value, e);
			return null;
		}
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getExpireDateString() {
		return expireDate == null ? "" : dateParser.format(expireDate);
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public Date getMfgLabelExpireDate() {
		return mfgLabelExpireDate;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	private BigDecimal getNumber(JSONObject json, String key) {
		Double value = null;
		try {
			value = json.getDouble(key);
			return new BigDecimal(value);
		}
		catch (Exception e) {
			log.error("Error parsing BigDecimal from JSON Object, field: " + key + ", value: " + value, e);
			return null;
		}
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public String getQuantity() {
		return quantity;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	private String getString(JSONObject json, String key) {
		try {
			return json.getString(key);
		}
		catch (Exception e) {
			log.error("Error retrieving String from JSON Object, field: " + key, e);
			return null;
		}
	}

	public boolean hasExpireDate() {
		return expireDate != null;
	}

	public boolean hasReceiptId() {
		return receiptId != null;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setExpireDateString(String date) {
		try {
			this.expireDate = dateParser.parse(date);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setMfgLabelExpireDate(Date mfgLabelExpireDate) {
		this.mfgLabelExpireDate = mfgLabelExpireDate;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
}
