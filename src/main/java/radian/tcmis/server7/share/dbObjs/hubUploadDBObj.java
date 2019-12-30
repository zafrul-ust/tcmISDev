package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        Haas TCM
 * Description:  Your description
 * Copyright:    Copyright (c) 2004
 * Company:      Your Company
 * @author Nawaz Shaik
 * @version
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import radian.tcmis.server7.share.db.DBResultSet;

public class hubUploadDBObj
{
  protected TcmISDBModel db;

  public hubUploadDBObj( TcmISDBModel db )
  {
    this.db=db;
  }

  public hubUploadDBObj()
  {

  }

	public Hashtable douploaddata(Hashtable inData) throws Exception {
	 Hashtable result = new Hashtable();
	 String personnelid = inData.get("USER_ID").toString();
	 Vector updata = (Vector) (inData.get("UPLOAD_DATA"));
	 String curcompany_id = "";
	 Vector binids = new Vector();
	 boolean upresult = true;
	 CallableStatement cs = null;
	 Connection connect1 = null;
	 String uploadid = "";
	 String personnelIdMessage = "";
	 String mainHubNumber = "";

	 //System.out.println(updata);
    try
    {
	  String query="select inventory_count_upload_id.nextval UPLOAD_ID from dual";
	  DBResultSet dbrs=null;
	  ResultSet rs=null;

	  try
	  {
		dbrs=db.doQuery( query );
		rs=dbrs.getResultSet();
		while ( rs.next() )
		{
		  uploadid= ( rs.getString( "UPLOAD_ID" ) == null ? "" : rs.getString( "UPLOAD_ID" ) );
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
	  }
	  finally
	  {
		if ( dbrs != null ){dbrs.close();}
	  }

      int i=0;
      for ( int loop=0; loop < updata.size(); loop++ )
      {
        Hashtable hD=new Hashtable();
        hD= ( Hashtable ) updata.elementAt( loop );

        String bin_id=hD.get( "BIN" ).toString();
        String receipt_Id=hD.get( "RECEIPT_ID" ).toString();
        String qty= hD.get( "QTY" ).toString();
        String timestamp= hD.get( "TIMESTAMP" ).toString();
        String tmppersoid= hD.get( "PERSONNEL_ID" ).toString();
				//Using the personnelId of the person logged into totcmIs to do tha upload when I do not get a personnel Id from the scanner
		    if (tmppersoid == null || tmppersoid.length() == 0)
				{
				 tmppersoid = personnelid;
				 personnelIdMessage = "Did not get a personnel ID for who did the scan upon upload. Using the personnel Id of the person logged into to tcmIS as the scanner ID.";
				}
				String hubnum= hD.get( "HUB" ).toString();
				mainHubNumber = hubnum;

		String insertdata = "insert into  inventory_count_scan_stage (UPLOAD_ID, RECEIPT_ID, SCAN_DATETIME, COUNTER_ID, SCANNED_BIN, COUNTED_QUANTITY,HUB) values \n";
		insertdata += "("+uploadid+","+receipt_Id+",to_date('" + timestamp + "','YYYY/MM/DD hh24:mi:ss'),"+tmppersoid+",'"+bin_id+"',"+qty+","+hubnum+") \n";

		try
		{
		  db.doUpdate( insertdata );
		}
		catch ( Exception ex )
		{
		  ex.printStackTrace();
		  radian.web.emailHelpObj.sendnawazemail("Error from inserting into inventory_count_scan_stage.","Error from inserting into inventory_count_scan_stage\n\n"+ex.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
		  upresult = false;
		}
      }
    }
    catch ( Exception e234 )
    {
      e234.printStackTrace();
      upresult =false;
    }

	try
	{
	  connect1=db.getConnection();
	  cs=connect1.prepareCall( "{call p_inventory_count_scan(?)}" );
	  cs.setInt( 1,Integer.parseInt( uploadid ) );
	  cs.execute();

	  /*String errorcode = cs.getString(9);
		 System.out.println("Result from Picklist procedure Error Code:  "+errorcode);
		 if (errorcode == null)
		 {
		 result = true;
		 }
		 else
		 {
		 result = false;
		 }*/
	}
	catch ( Exception ex )
	{
	  ex.printStackTrace();
	  radian.web.emailHelpObj.sendnawazemail( "Error calling p_inventory_count_scan",
											  "Error calling p_inventory_count_scan.\n\n" + ex.getMessage() + "\n\nPersonnel ID:   " + personnelid + "" );
	  upresult=false;
	}
	finally
	{
		if (cs != null) {
		  try {
				  cs.close();
		  } catch (Exception se) {se.printStackTrace();}
		}
	}

	String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	wwwHome = wwwHome.substring(0, wwwHome.length() - 1);
	String resulturl = wwwHome + radian.web.tcmisResourceLoader.gethubcountreporturl();
	/*radian.web.emailHelpObj.sendnawazemail("Hub Scanner Upload Results",""+personnelIdMessage+"\n\nThe upload has been processed successfully, please go to the URL below to look at the results.\n\n\n"+resulturl+"UserAction=Search&uploadId="+uploadid+"\n\n\nPersonnel ID : "+personnelid+"");*/
	radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Hub Scanner Upload Results",""+personnelIdMessage+"\n\nThe upload has been processed successfully, please go to the URL below to look at the results.\n\n\n"+resulturl+"UserAction=Search&uploadId="+uploadid+"",Integer.parseInt(personnelid),true);
	//Email Dave Costain
	if ("2102".equalsIgnoreCase(mainHubNumber))
	{
	radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Hub Scanner Upload Results",""+personnelIdMessage+"\n\nThe upload has been processed successfully, please go to the URL below to look at the results.\n\n\n"+resulturl+"UserAction=Search&uploadId="+uploadid+"",Integer.parseInt("220"),true);
	}
	result.put("SUCCESS",new Boolean(upresult));
	return result;
 }
} //end of class

