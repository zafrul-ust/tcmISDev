package com.tcmis.client.kilfrost.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:44:56 PM
 * To change this template use File | Settings | File Templates.
 */
import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;


/******************************************************************************
 * CLASSNAME: OrderBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class OrderBean extends BaseDataBean {
//order header
	private String orderNumber;
    private String orderDateString;
    private Date orderDate;
    private String purchaseOrder;
    private String orderStatus;
//order details
//    private String partNumber;
//    private BigDecimal quantity;
//customer details
    private String customerPickup;
    private String customerName;
    private String contactName;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String stateProvence;
    private String zip;
    private String country;
    private String phone;
    private String email;
    private String shippingNote;
//delivery note
    private String deliveryNote;

	private Collection<OrderLineBean> orderLineBeanCollection = new Vector();


    //constructor
	public OrderBean() {
	}

	//setters
	public void setOrderNumber(String s) {
		this.orderNumber = s;
	}

    public void setOrderDateString(String s) {
		this.orderDateString = s;
        try {
            DateHandler.getDateFromString("yyyy-MM-dd", s);
        }
        catch(Exception e){
            System.out.println(new Date() + "-Invalid date in com.tcmis.client.kilfrost.beans.OrderBean:"+s);
        }
    }

    public void setOrderDate(Date d) {
		this.orderDate = d;
	}

	public void setPurchaseOrder(String s) {
		this.purchaseOrder = s;
	}

/*	public void setPartNumber(String s) {
		this.partNumber = s;
	}

	public void setQuantity(BigDecimal b) {
		this.quantity = b;
	}
*/
	public void setCustomerName(String s) {
		this.customerName = s;
	}

	public void setContactName(String s) {
		this.contactName = s;
	}

	public void setAddress1(String s) {
		this.address1 = s;
	}

    public void setAddress2(String s) {
		this.address2 = s;
	}

	public void setAddress3(String s) {
		this.address3 = s;
	}

    public void setStateProvence(String s) {
		this.stateProvence = s;
	}

    public void setCity(String s) {
		this.city = s;
	}

    public void setZip(String s) {
		this.zip = s;
	}

    public void setCountry(String s) {
		this.country = s;
	}

    public void setPhone(String s) {
		this.phone = s;
	}

    public void setEmail(String s) {
		this.email = s;
	}

    public void setDeliveryNote(String s) {
		this.deliveryNote = s;
	}

    public void setOrderLineBeanCollection(Collection<OrderLineBean> c) {
        this.orderLineBeanCollection = c;
    }

    public void addOrderLineBean(OrderLineBean orderLineBean) {
        this.orderLineBeanCollection.add(orderLineBean);
    }

    //getters
	public String getOrderNumber() {
		return this.orderNumber;
	}

    public String getOrderDateString() {
		return this.orderDateString;
	}

    public Date getOrderDate() {
		return this.orderDate;
	}

/*	public String getPartNumber() {
		return this.partNumber;
	}

    public BigDecimal getQuantity() {
        return this.quantity;
    }
*/
    public String getCustomerName() {
        return this.customerName;
    }

    public String getContactName() {
        return this.contactName;
    }

    public String getAddress1() {
        return this.address1;
    }

    public String getAddress2() {
        return this.address2;
    }

	public String getAddress3() {
        return this.address3;
    }

    public String getCity() {
        return this.city;
    }

    public String getStateProvence() {
		return this.stateProvence;
	}

    public String getZip() {
        return this.zip;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDeliveryNote() {
        return this.deliveryNote;
    }

    public Collection<OrderLineBean> getOrderLineBeanCollection() {
        return this.orderLineBeanCollection;
    }

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public String getCustomerPickup() {
		return customerPickup;
	}

	public void setCustomerPickup(String customerPickup) {
		this.customerPickup = customerPickup;
	}

	public String getShippingNote() {
		return shippingNote;
	}

	public void setShippingNote(String shippingNote) {
		this.shippingNote = shippingNote;
	}

}
