package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.dbObjs.*;


public class TrackObj extends TcmisServletObj
{
    //Client Version
    String    function  = null;
    Hashtable mySendObj = null;
    Hashtable inData    = null;

    //Raj chg 9/22/2001  Formatting logic is now done in TrackView.java

    public TrackObj(ServerResourceBundle b, TcmISDBModel d )
    {
        super();
        bundle = b;
        db = d;
    }

    //
    protected void resetAllVars()
    {
        function = null;
        inData = null;
    }

    //
    protected void getResult()
    {
        mySendObj = new Hashtable();
        // using myRecvObj you get the function the way you want
        inData = (Hashtable)myRecvObj;
        function = inData.get("ACTION").toString();
        if(function.equalsIgnoreCase("GET_FLOAT_INFO"))
        {
            getFloatInfo();
        }
        else if(function.equalsIgnoreCase("SEARCH"))
        {
            doSearch();
        }
        else if(function.equalsIgnoreCase("GET_USER_NAME"))
        {
            getUserName();
        }
        else if(function.equalsIgnoreCase("UPDATE_CRITICAL"))
        {
            updateCritical();
        }
        else if(function.equalsIgnoreCase("GET_SCHEDULE_DELIVERY"))
        {
            getScheduleDelivery();
        }
        else if(function.equalsIgnoreCase("GET_MR_ALLOCATION_DATA"))
        {
            getMRAllocationData();
        }
        else if (function.equalsIgnoreCase("PRINT_ORDER_STATUS")) {
          printOrderStatus();
        }
    }

  protected void printOrderStatus() {
    OrderStatusReportGenerator osrg = new OrderStatusReportGenerator();
    String url = osrg.buildOrderStatus((Vector)inData.get("MR_ALLOCATION_INFO"),(String)inData.get("FACILITY"),(String)inData.get("REQUESTOR"));
    if (url.length() > 1) {
      mySendObj.put("SUCCEEDED", new Boolean(true));
      mySendObj.put("URL",url);
      mySendObj.put("MSG","");
    }else {
      mySendObj.put("SUCCEEDED", new Boolean(false));
      mySendObj.put("MSG","Error message here.");
    }
  }

    void getMRAllocationData() {
      //System.out.println("----------- indata: "+inData);
      try {
        RequestsScreen rs = new RequestsScreen(db);
		  if (inData.containsKey("LINE_ITEM")) {
			  rs.setLineItem((String)inData.get("LINE_ITEM"));
		  }

		  mySendObj.put("MR_ALLOCATION_INFO",rs.getMRAllocationInfo(Integer.parseInt((String)inData.get("PR_NUMBER"))));
        mySendObj.put("SUCCEED",new Boolean(true));
      }catch (Exception e) {
        mySendObj.put("SUCCEED",new Boolean(false));
      }
    }

    //1-25-03
    void getScheduleDelivery() {
      try {
        TrackView tv = new TrackView(db);
        mySendObj.put("SCHEDULE_DELIVERY_INFO",tv.getScheduleDelivery((String)inData.get("USER_ID"),(String)inData.get("PR_NUMBER"),(String)inData.get("LINE_ITEM")));
        mySendObj.put("NOW",Calendar.getInstance());
        mySendObj.put("SUCCEED",new Boolean(true));
      }catch (Exception e) {
        mySendObj.put("SUCCEED",new Boolean(false));
      }
    }

    //
    void getUserName()
    {
        try
        {
            Personnel p = new Personnel(db);
            p.setPersonnelId(Integer.parseInt(inData.get("USER_ID").toString()));
            p.load();
            mySendObj.put("NAME",p.getFullName());
            TrackView tv = new TrackView(db);
            mySendObj.put("HEADER_COL",tv.getColHeader());
            mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());

            Vector v = new Vector(4);
            v.addElement("Part");
            v.addElement("Description");
            v.addElement("Material Request No.");
            v.addElement("Item");
            mySendObj.put("SEARCH_TYPE",v);
            Vector v1 = new Vector(2);
            v1.addElement("is");
            v1.addElement("contains");
            mySendObj.put("SEARCH_CONTENT",v1);
            mySendObj.put("DAYS","30");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //
    void doSearch()
    {
        try {
          //rather going to TrackingTable then to TrackView, I am going straight to TrackView
          TrackView tv = new TrackView(db);
          mySendObj.put("SEARCH_DATA",tv.getTableDataNew(inData));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

  void getFloatInfo() {
    try {
      TrackFloat tf = new TrackFloat(db);
      mySendObj.put("SEARCH_DATA",tf.getOrderDetailInfo((String)inData.get("MR_LINE"),(String)inData.get("ITEM_ID")));
      mySendObj.put("TABLE_STYLE",BothHelpObjs.getTableStyle());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  protected void updateCritical() {
    try {
      RequestLineItem rli = new RequestLineItem(db);
      String mr = inData.get("MR_NUM").toString();
      Boolean crit = (Boolean)inData.get("CRIT");
      int userId = Integer.parseInt(inData.get("USER_ID").toString());
      String line = inData.get("LINE_ITEM").toString();
      String query = "update request_line_item set critical = '"+(crit.booleanValue()?"Y":"N")+"'"+
                     ",last_updated = SYSDATE ,last_updated_by="+userId+" where pr_number = "+mr+ " and line_item = "+line;
      db.doUpdate(query);
      mySendObj.put("RESULT",crit);
    }catch(Exception e) {
      e.printStackTrace();
      mySendObj.put("RESULT",new Boolean(false));
    }
  }

    //
    protected void print(TcmisOutputStreamServer out)
    {
        try
        {
            out.sendObject((Hashtable) mySendObj);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}//end
