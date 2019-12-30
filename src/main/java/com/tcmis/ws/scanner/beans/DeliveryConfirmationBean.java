package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.TimeZone;
import java.util.Date;

public class DeliveryConfirmationBean extends BaseDataBean {
	private BigDecimal personnelId;
    private BigDecimal shipmentId;
    private Date confirmationDate;

    public DeliveryConfirmationBean() {

    }

    public DeliveryConfirmationBean(JSONObject jsonData) throws BaseException {
		try {
            this.setPersonnelId(new BigDecimal(jsonData.getString("UserID")));
            this.setShipmentId(new BigDecimal(jsonData.getString("ShipmentID")));
			this.setConfirmationDate(DateHandler.getDateFromString("ddMMMyyyy'T'hh:mmaaa",jsonData.getString("ConfirmationDate"), TimeZone.getTimeZone("GMT")));
		}
		catch (Exception e) {
			throw new BaseException(e);
		}
	}

    public BigDecimal getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
        this.personnelId = personnelId;
    }

    public BigDecimal getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(BigDecimal shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

}
