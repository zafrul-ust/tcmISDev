package com.tcmis.internal.airgas.process;

import com.tcmis.internal.airgas.beans.StockQueryInputBean;
import com.tcmis.internal.airgas.beans.StockQueryResultViewBean;
import com.tcmis.internal.airgas.beans.OrderSubmitInputBean;
import com.tcmis.internal.airgas.beans.OrderSubmitResultViewBean;

import java.util.Vector;
import java.net.*;

public class AirgasProcess {

 	private class Conn {
		java.io.DataInputStream  input ;
		java.io.DataOutputStream output;
		Socket socket;
		byte[] buffer = new byte[1024];
		byte[] blen = new byte[2];

 		public Conn(String s) {
 			try {
// test ,larry's box, test airgas services.
 				socket = new Socket("192.168.18.80",8224);
// production, data center, production airgas services.
// 				socket = new Socket("192.168.11.21",8224);
 				input  = new java.io.DataInputStream(socket.getInputStream());
 				output = new java.io.DataOutputStream(socket.getOutputStream());
 				send(s);
 			} catch(Exception ex){}
 		}
 	 	public void send(String s){
 	 		try {
 	 			if( s == null ) s = "";
 	 			byte[] utfarr = s.getBytes("UTF-8");
 	 			int len = utfarr.length;
 	 			blen[0] = (byte)(len%256);
 	 			blen[1] = (byte)(len/256);
 	 			output.write(blen);
 	 			output.write(utfarr);
 	 			System.out.println("send="+utfarr.length+"+"+s);
 	 		} catch(Exception ex){}
 	 	}
 	 	public String recv(){
 	 		try {

 	 			input.read(blen); 	 			
                int ll = blen[1] * 256 + blen[0];
                String rs = new String();
                while (ll > 0)
                {   int trytoread = 1024;
                	if( trytoread > ll ) trytoread=ll; 
                    int len = input.read(buffer,0,trytoread);
                    rs += new String(buffer,0,len,"UTF-8");
                    ll -= len;
                }
 	 			System.out.println("recv="+rs);
 	 			return rs;
 	 		} catch(Exception ex){}
 	 		return "";
 	 	}
 	 	public void close(){
 	 		try {
 	 		input.close();
 			output.close();
 			socket.close();
 	 		} catch(Exception ex){} 
 	 	}
//		Service = new ServerSocket(PortNumber);
//		serviceSocket = Service.accept();
//      close sequence, input,output, serversocket, server service.
 	}

 	// here is the socket programming test code..
 public Vector<StockQueryResultViewBean> StockQuery(StockQueryInputBean inputBean) {
		Vector v = new Vector();
		StockQueryResultViewBean bean = new StockQueryResultViewBean();
		bean.setQtyAvailable("");
		v.add(bean);
	try {
		Conn conn = new Conn("StockQuery");
 		conn.send(inputBean.getInventoryLocation());
   		conn.send(inputBean.getRegion());
   		conn.send(inputBean.getTicketPrinter());
   		conn.send(inputBean.getAccount());
		conn.send(inputBean.getPartNum());
		
		System.out.println("Received Number of results:"+conn.recv());
		bean.setAirgasPN(conn.recv());
		bean.setAvailDate(conn.recv());
		bean.setContractType(conn.recv());
		bean.setCustPrice(conn.recv());
		bean.setErrorMessage(conn.recv());
		bean.setHazardData(conn.recv());
		bean.setItemType(conn.recv());
		bean.setMinSellQty(conn.recv());
		bean.setQtyAvailable(conn.recv());
		System.out.println("conn over");
		conn.close();
		
	}catch(Exception ex){
		ex.printStackTrace();
	}
	if( bean.getQtyAvailable() == null || bean.getQtyAvailable().length() == 0 )
		bean.setQtyAvailable("-1");
	return v;
 }


 public Vector<OrderSubmitResultViewBean> OrderSubmit(OrderSubmitInputBean inputBean) {
	Vector v = new Vector();
	OrderSubmitResultViewBean r = new OrderSubmitResultViewBean();
	r.setOrder_number("");
	v.add(r);
	try {
		Conn conn = new  Conn("OrderSubmit");
	
// customer po is haas po.
		inputBean.setCustPO(inputBean.getHaaspo()); 
// shipto account is our account??
		inputBean.setShipto_Account(inputBean.getAccount());
		
//      order_partnum == partnum??
//		inputBean.setOrder_partNum(inputBean.getPartNum());
// begin send params.
//0		
 		conn.send(inputBean.getInventoryLocation());
   		conn.send(inputBean.getRegion());
   		conn.send(inputBean.getTicketPrinter());
   		conn.send(inputBean.getAccount());
		conn.send(inputBean.getPartNum());
//5
	conn.send(inputBean.getApprovingParty());
	conn.send(inputBean.getBillToAcct());
	conn.send(inputBean.getBuyerFirstName());
	conn.send(inputBean.getBuyerLastName());
	conn.send(inputBean.getBuyerMiddleName());
//10
	conn.send(inputBean.getChartOfAccts());
	conn.send(inputBean.getConfirmationType());
	conn.send(inputBean.getCustPO());
	conn.send(inputBean.getDeliveryFlag());
	conn.send(inputBean.getNotes());
//15
	conn.send(inputBean.getOrderDate());

	// order lines, only one line 	
	conn.send(inputBean.getOrderName());
	conn.send(inputBean.getOrderNumber());
	conn.send(inputBean.getOrderType());
	
	// bill to , only one obj 
	conn.send(inputBean.getBillto_City());
//20
	conn.send(inputBean.getBillto_Country());
	conn.send(inputBean.getBillto_Customerid());
	conn.send(inputBean.getBillto_Email());
	conn.send(inputBean.getBillto_Name());
	conn.send(inputBean.getBillto_State());
//25
	conn.send(inputBean.getBillto_Street());
	conn.send(inputBean.getBillto_Zip());
	
	// ship to , only one obj 
	conn.send(inputBean.getShipto_Account());
	conn.send(inputBean.getShipto_Address1());
	conn.send(inputBean.getShipto_Address2());
//30
	conn.send(inputBean.getShipto_Name());
	conn.send(inputBean.getShipto_City());
	conn.send(inputBean.getShipto_State());
	conn.send(inputBean.getShipto_Zip());
	
	// payment 
	conn.send(inputBean.getPaymentCreditCardNumber());
//35
	conn.send(inputBean.getPaymentCreditCardType());
	conn.send(inputBean.getPaymentCVNum());
	conn.send(inputBean.getPaymentExpDate());
	conn.send(inputBean.getPaymentMethod());
	conn.send(inputBean.getPaymentName());
//40
	conn.send(inputBean.getPaymentOrigID());
	conn.send(inputBean.getPaymentType());
	conn.send(inputBean.getPickupBranch());
	conn.send(inputBean.getPoRelease());
	conn.send(inputBean.getSharesecret());
//45
	conn.send(inputBean.getShipChrg());
	conn.send(inputBean.getShipDate());
	conn.send(inputBean.getShippingInst());
	conn.send(inputBean.getStatus());
	conn.send(inputBean.getTaxableFlag());
//50
	conn.send(inputBean.getUseCustPartNum());
	conn.send(inputBean.getUserID());
	conn.send(inputBean.getUserInfo());
	
	// orderline        
    conn.send(inputBean.getOrder_comments());
    conn.send(inputBean.getOrder_companyName());
//55
    conn.send(inputBean.getOrder_custPartNum());
    conn.send(inputBean.getOrder_dateShipped());
    conn.send(inputBean.getOrder_description());
    conn.send(inputBean.getOrder_extendedPrice());
    conn.send(inputBean.getOrder_gasVolume());
//60
    conn.send(inputBean.getOrder_itemChangeCoded());
    conn.send(inputBean.getOrder_itemNum());
    conn.send(inputBean.getOrder_lineStatus());
    conn.send(inputBean.getOrder_orderPrice());
    conn.send(inputBean.getOrder_orderQty());
//65
    conn.send(inputBean.getOrder_packageID());
    conn.send(inputBean.getOrder_partNum());
    conn.send(inputBean.getOrder_priorityCode());
    conn.send(inputBean.getOrder_requiredDate());
    conn.send(inputBean.getOrder_returnQty());
//70    
    conn.send(inputBean.getOrder_select());
    conn.send(inputBean.getOrder_shipQty());
    conn.send(inputBean.getOrder_uom());

	// start receiving result
	
//start receiving result	
//0
	r.setApprovingParty(conn.recv());
    //    billTo.account;
    //    billTo.billToAddress1;
    //    billTo.billToAddress2;
    //    billTo.billToCity;
    //    billTo.billToEmail;
    //    billTo.billToState;
    //    billTo.billToZip;
    //    billTo.name;
	r.setBillTo_account(conn.recv());
	r.setBillTo_billToAddress1(conn.recv());
	r.setBillTo_billToAddress2(conn.recv());
	r.setBillTo_billToCity(conn.recv());
//5
	r.setBillTo_billToEmail(conn.recv());
	r.setBillTo_billToState(conn.recv());
	r.setBillTo_billToZip(conn.recv());
	r.setBillTo_name(conn.recv());
    //    branch.id;
    //    branch.name;
    //    branch.number;
	r.setBranch_id(conn.recv());
//10
	r.setBranch_name(conn.recv());
	r.setBranch_number(conn.recv());
	r.setBuyerName_Last(conn.recv());
	r.setBuyerName_Middle(conn.recv());
	r.setBuyerName_First(conn.recv());
//15
	r.setCustPO(conn.recv());
	r.setErrorMessage(conn.recv());
	r.setNotes(conn.recv());
	r.setNotifications(conn.recv());
	r.setOrderDate(conn.recv());

//20	
	// carrier info
	r.setCarrierInfo_DateShipped(conn.recv());
	r.setCarrierInfo_Name(conn.recv());
	r.setCarrierInfo_PackageId(conn.recv());
	// order lines, assuming only one line 

	r.setComments(conn.recv());
	r.setCustPartNum(conn.recv());
//25
	r.setDescription(conn.recv());
	r.setExtendedPrice(conn.recv());
	r.setFeeLine(conn.recv());
	r.setItemChangeCoded(conn.recv());
	r.setItemNumber(conn.recv());
//30
	r.setLineNumber(conn.recv());
	r.setLineStatus(conn.recv());
	r.setOrderPrice(conn.recv());
	r.setOrderQty(conn.recv());
	r.setPartNum(conn.recv());
//35
	r.setRequiredDate(conn.recv());
	r.setSelect(conn.recv());
	r.setSelectMessage(conn.recv());
	r.setShippedQty(conn.recv());
	r.setUom(conn.recv());
//40
	/* payment, only one obj */
	r.setCreditCardType(conn.recv());
	r.setPaymentType(conn.recv());
	
	r.setPaymentMethod(conn.recv());
	r.setPayTerms(conn.recv());
	r.setRequiredDate(conn.recv());
//45
	r.setShippingInst(conn.recv());

// ship to info
		r.setShipto_Name(conn.recv());
		r.setShipto_ShipToAddress1(conn.recv());
		r.setShipto_ShipToAddress2(conn.recv());
		r.setShipto_ShipToCity(conn.recv());
//50
		r.setShipto_ShipToState(conn.recv());
		r.setShipto_ShipToZip(conn.recv());
// total info 
		r.setDiscounts(conn.recv());
		r.setHandling(conn.recv());
		r.setOrderTotal(conn.recv());
//55
		r.setShipChrg(conn.recv());
		r.setTax(conn.recv());
	
	r.setUserID(conn.recv());
    // order
    System.out.println("Received number of order lines:"+conn.recv());
    r.setOrder_number(conn.recv());
    r.setOrder_orderName(conn.recv());
//60
    r.setOrder_ridgewoodOrder(conn.recv());
    r.setOrder_sequence(conn.recv());
    r.setOrder_status(conn.recv());
    r.setOrder_type(conn.recv());

	} catch (Exception ex){
		ex.printStackTrace();
	}

	if( r.getOrder_number() == null || r.getOrder_number().length() == 0 )
		r.setOrder_number("-1");
	return v;
}
 
}
