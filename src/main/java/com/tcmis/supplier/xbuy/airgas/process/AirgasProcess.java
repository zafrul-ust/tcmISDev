package com.tcmis.supplier.xbuy.airgas.process;

import com.tcmis.supplier.xbuy.airgas.beans.StockQueryInputBean;
import com.tcmis.supplier.xbuy.airgas.beans.StockQueryResultViewBean;
import com.tcmis.supplier.xbuy.airgas.beans.OrderSubmitInputBean;
import com.tcmis.supplier.xbuy.airgas.beans.OrderSubmitResultViewBean;

import java.util.Vector;
import java.net.*;

public class AirgasProcess {

 	private class Conn {
		java.io.DataInputStream  input ;
		java.io.DataOutputStream output;
		Socket socket;
		byte[] buffer = new byte[1024];
		byte[] blen = new byte[2];
		public boolean isProduction = true;
		
 		public Conn(String s) {
 			try {
// 			 production, data center, production airgas services.
/* 				if( isProduction )
 						socket = new Socket("192.168.11.21",8224);
// test ,larry's box, test airgas services.
 				else 
						socket = new Socket("192.168.18.80",8224);
*/ 						socket = new Socket("192.168.11.21",8224);

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
 	 			//System.out.println("send="+utfarr.length+"+"+s);
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
 	 			//System.out.println("recv="+rs);
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
		Vector rv = new Vector();
		
		int iNum = -1;
		int num = -1;
		int good = -1;
	try {
//		Conn conn = new Conn("StockQuery");
		
		String[] parts = inputBean.getPartNum().split(",");
		if( parts == null || parts.length == 0 ) { return v; }
		int numTran = (parts.length+99) / 100;
		
		for( int currentTran = 0; currentTran < numTran; currentTran++) {
			Conn conn = new Conn("StockQuery");
		conn.send(inputBean.getInventoryLocation());
   		conn.send(inputBean.getRegion());
   		conn.send(inputBean.getTicketPrinter());
   		conn.send(inputBean.getAccount());
		
		if( currentTran == (numTran -1) && ((parts.length%100) != 0) ) 
			iNum = parts.length%100;
		else 
			iNum = 100;
		
		conn.send(new Integer(iNum).toString());
		for(int i = 0;i <iNum; i++ ) {
			conn.send(parts[i+currentTran*100]);
		}
//		conn.send(inputBean.getPartNum());
		String rNum = conn.recv();
		System.out.println("Received Number of results:"+rNum);
		if( rNum.equals("") ) { conn.close(); return v; }
		num = Integer.parseInt(rNum);
		if( num != iNum ) { conn.close(); return v; }
		for(int i = 0; i < num; i++ ) {
			StockQueryResultViewBean bean = new StockQueryResultViewBean();
			bean.setAirgasPN(conn.recv());
			bean.setAvailDate(conn.recv());
			bean.setContractType(conn.recv());
			bean.setCustPrice(conn.recv());
			bean.setErrorMessage(conn.recv());
			bean.setHazardData(conn.recv());
			bean.setItemType(conn.recv());
			bean.setMinSellQty(conn.recv());
			
			String qty = conn.recv();
			if( qty == null || qty.length() == 0 )
				qty = "-1";
			bean.setQtyAvailable(qty);
			//System.out.println(i+":"+currentTran+":"+bean.getQtyAvailable());
			v.add(bean);
		}
		conn.close();
		}
		//System.out.println("conn over");
		good = 0 ;
		
	}catch(Exception ex){
		ex.printStackTrace();
	}
	if( iNum != -1 && num != -1 && good != -1 ) rv = v;
	return rv;
 }


 public Vector<OrderSubmitResultViewBean> OrderSubmit(OrderSubmitInputBean inputBean) {
	Vector v = new Vector();
	OrderSubmitResultViewBean r = new OrderSubmitResultViewBean();
	r.setOrder_number("");
	v.add(r);
	Conn conn = null;
	try {
		conn = new  Conn("OrderSubmit");
// customer po is haas po.
		inputBean.setCustPO(inputBean.getHaaspo()); 
// shipto account is our account??
		inputBean.setShipto_Account(inputBean.getAccount());
		
//      order_partnum == partnum??
		inputBean.setOrder_partNum(inputBean.getPartNum());
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
	conn.send(inputBean.getShipto_Name()); //11
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
	r.setApprovingParty(conn.recv()); //38
    //    billTo.account;
    //    billTo.billToAddress1;
    //    billTo.billToAddress2;
    //    billTo.billToCity;
    //    billTo.billToEmail;
    //    billTo.billToState;
    //    billTo.billToZip;
    //    billTo.name;
	r.setBillTo_account(conn.recv()); //20
	r.setBillTo_billToAddress1(conn.recv()); //32
	r.setBillTo_billToAddress2(conn.recv()); //33
	r.setBillTo_billToCity(conn.recv()); //34
//5
	r.setBillTo_billToEmail(conn.recv()); //37
	r.setBillTo_billToState(conn.recv()); //35
	r.setBillTo_billToZip(conn.recv()); //36
	r.setBillTo_name(conn.recv()); //31
    //    branch.id;
    //    branch.name;
    //    branch.number;
	r.setBranch_id(conn.recv()); //17
//10
	r.setBranch_name(conn.recv()); //18
	r.setBranch_number(conn.recv()); //19
	r.setBuyerName_Last(conn.recv()); //41
	r.setBuyerName_Middle(conn.recv());
	r.setBuyerName_First(conn.recv()); //42
//15
	r.setCustPO(conn.recv());       //2
	r.setErrorMessage(conn.recv()); //1
	r.setNotes(conn.recv()); //43
	r.setNotifications(conn.recv()); //39
	r.setOrderDate(conn.recv());    //3
//20
	/* payment, only one obj */
	r.setCreditCardType(conn.recv());
	r.setPaymentType(conn.recv());
	
	r.setPaymentMethod(conn.recv());
	r.setPayTerms(conn.recv()); //27
	r.setRequiredDate(conn.recv()); //44
//25
	r.setShippingInst(conn.recv());

// ship to info
		r.setShipto_Name(conn.recv()); //11
		r.setShipto_ShipToAddress1(conn.recv()); //12
		r.setShipto_ShipToAddress2(conn.recv()); //13
		r.setShipto_ShipToCity(conn.recv()); //14
//30
		r.setShipto_ShipToState(conn.recv()); //15
		r.setShipto_ShipToZip(conn.recv()); //16
// total info 
		r.setDiscounts(conn.recv());
		r.setHandling(conn.recv());
		r.setOrderTotal(conn.recv()); //10
//35
		r.setShipChrg(conn.recv());
		r.setTax(conn.recv());
	
	r.setUserID(conn.recv()); //46
    r.setOrder_number(conn.recv()); //21
    r.setOrder_orderName(conn.recv()); //22
//40
    r.setOrder_ridgewoodOrder(conn.recv()); //23
    r.setOrder_sequence(conn.recv()); //24
    r.setOrder_status(conn.recv()); //25
    r.setOrder_type(conn.recv()); //26

    // order
    String numOrder = conn.recv();
    System.out.println("Received number of order lines:"+numOrder);
    if( numOrder.equals("1")) {
//  45	
  	// carrier info
  	r.setCarrierInfo_DateShipped(conn.recv()); //9
  	r.setCarrierInfo_Name(conn.recv()); //30
  	r.setCarrierInfo_PackageId(conn.recv());
  	// order lines, assuming only one line 

  	r.setComments(conn.recv());
  	r.setCustPartNum(conn.recv());
//  50
  	r.setDescription(conn.recv());
  	r.setExtendedPrice(conn.recv()); //28
//  no fee line on production site
/*
 *   	if( !conn.isProduction )
  		r.setFeeLine(conn.recv());
*/
  	r.setItemChangeCoded(conn.recv());
  	r.setItemNumber(conn.recv()); //7
//  55
  	r.setLineNumber(conn.recv()); //6
  	r.setLineStatus(conn.recv());
  	r.setOrderPrice(conn.recv()); //29
  	r.setOrderQty(conn.recv()); //4
  	r.setPartNum(conn.recv()); //8
//  60
  	r.setLine_requiredDate(conn.recv()); //45
  	r.setSelect(conn.recv());
  	r.setSelectMessage(conn.recv());
  	r.setShippedQty(conn.recv()); //5
  	r.setUom(conn.recv()); //40
    }
    else {
    	r.setOrder_number("-1");
    	r.setErrorMessage("No response back");
    }
	
	} catch (Exception ex){
		ex.printStackTrace();
	}
	finally {
		try{
		if( conn != null ) conn.close();
		}catch(Exception ex){}
	}
	if( r.getOrder_number() == null || r.getOrder_number().length() == 0 )
		r.setOrder_number("-1");
	return v;
}
 
}
