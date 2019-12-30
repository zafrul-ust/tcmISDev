package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 * 12-22-03 - Calling p_line_item_allocate after creating an MR
 * 01-26-04 - Creating MR's only for the COMPANY_ID working on. Limitation is at one time Only One Company Cabinets can be scanned
 * 02-16-04 - Changed the query to return only receipts which are not expired @ sysdate. For purto rico only receipts that do not expire till after next week.
 * 03-12-04 - Getting the personnel ID of the person doing the upload in error emails
 * 05-07-04 - Using the personnel ID of the person logged in as the requestor.
 * 06-04-04 - Sending an email with all the results after scanner upload is done
 * 06-28-04 - Fixing a bug
 * 07-28-04 - Sending email to the user if an error occurs.
 * 09-20-04 - Creating the MRs in the order of item_id
 * 06-13-05 - Fixed a bug which was causing Hub with cabinets for multiple companys no generate the required replenishment MRs
 */

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Vector;
import java.util.HashMap;
import java.math.*;
import radian.tcmis.server7.share.db.DBResultSet;
import com.tcmis.internal.hub.process.CabinetCountProcess;

public class scannerDownloadDBObj
{
	protected TcmISDBModel db;

	public scannerDownloadDBObj( TcmISDBModel db )
	{
		this.db=db;
	}

	public scannerDownloadDBObj()
	{

	}

	public Hashtable douploaddata( Hashtable inData ) throws Exception {
		Hashtable result=new Hashtable();
		Vector updata= ( Vector ) ( inData.get( "UPLOAD_DATA" ) );
		String lastCompanyId = "";
	String queryCompanyId = "";
		Vector binids = new Vector();
		boolean upresult = true;
		String personnelid = "";

		//Manually going in to fix data comment out code below
		personnelid = inData.get( "USER_ID" ).toString();
		try
		{
			//Clean temporary table
			String cleantmptable="delete from cabinet_inventory_count_stage";
			try
			{
				db.doUpdate( cleantmptable );
			}
			catch ( Exception ex )
			{
				ex.printStackTrace();
				upresult=false;
			}

			int i=0;
			for ( int loop=0; loop < updata.size(); loop++ )
			{
				Hashtable hD=new Hashtable();
				hD= ( Hashtable ) updata.elementAt( loop );

				String bin_id=hD.get( "BIN_ID" ).toString();
				String receipt_Id=hD.get( "RECEIPT_ID" ).toString();
				String qty= hD.get( "QTY" ).toString();
				String timestamp= hD.get( "TIMESTAMP" ).toString();
				String tmppersoid= hD.get( "PERSONNEL_ID" ).toString();
				String compid= hD.get( "COMPANY_ID" ).toString();
			if (compid.trim().length() > 0 && !compid.equalsIgnoreCase(lastCompanyId))
		{
			if (queryCompanyId.length() > 0)
			{
			queryCompanyId += "," + "'"+compid+"'";
			}
			else
			{
			queryCompanyId="'"+compid+"'";
			}
			lastCompanyId = compid;
		}
				//timestamp format yyyy/MM/DD hh:MM:ss
				// the table to insert is cabinet_inventory_count
				String insertdata = "insert into cabinet_inventory_count_stage (BIN_ID,COUNT_DATETIME,RECEIPT_ID,PERSONNEL_ID,COUNT_QUANTITY,COMPANY_ID) values \n";
				insertdata += "("+bin_id+",to_date('" + timestamp + "','YYYY/MM/DD hh24:mi:ss'),"+receipt_Id+","+tmppersoid+","+qty+",'"+compid+"') \n";

				try
				{
					db.doUpdate( insertdata );
			if (!binids.contains(bin_id))
			{
				binids.add(bin_id);
			}
				}
				catch ( Exception ex )
				{
					ex.printStackTrace();
			radian.web.emailHelpObj.sendnawazemail("Error from inserting into cabinet_inventory_count_stage This count might already be entered","Error from inserting into cabinet_inventory_count_stage This count might already be entered\n\n"+ex.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
					upresult = false;
				}
			}
		}
		catch ( Exception e234 )
		{
			e234.printStackTrace();
			upresult =false;
		}

		/*Removed this because it was causing duplicate picks, if they click update twice right next to each other*/
		/*//Delete any scans that have already been moved over to cabinet_inventory_count table. This is because people foget to do a download
		//to their scanner before going out to scan. doing a download to the scanner
		//clears out scans that have already been processed in the database.
		String removeDuplicates ="delete from cabinet_inventory_count_stage where (BIN_ID, COUNT_DATETIME, RECEIPT_ID, COMPANY_ID, PERSONNEL_ID, COUNT_QUANTITY, ";
		removeDuplicates +="nvl(DATE_PROCESSED,sysdate+6757)) in (select y.BIN_ID, y.COUNT_DATETIME, y.RECEIPT_ID, y.COMPANY_ID, y.PERSONNEL_ID, y.COUNT_QUANTITY, ";
		removeDuplicates +="nvl(y.DATE_PROCESSED,sysdate+6757) from cabinet_inventory_count x,cabinet_inventory_count_stage y where x.COUNT_DATETIME = y.COUNT_DATETIME and ";
		removeDuplicates +="x.bin_id = y.bin_id and x.receipt_id = y.receipt_id and x.personnel_id  = "+personnelid+" and x.COUNT_DATETIME > sysdate -30 ) ";

		try
		{
			db.doUpdate( removeDuplicates );
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
			radian.web.emailHelpObj.sendnawazemail("Error when moving to cabinet_inventory_count table from the stage table","Error when moving to cabinet_inventory_count table from the stage table\n\n"+ex.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
			radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Error in Cabinet Scanner Upload","An error occured while uploading scanner data.\nSome of the data you are trynig to upload has already been uploaded.\nThis might have caused if you did not download to the scanner before doing a scan.\n The Tech Center has been informed and will let you know when it is resolved.\n You don't need to upload the scan again.\nContact Tech Center if you have any questions.",Integer.parseInt(personnelid),true);
			upresult=false;
		}*/

        if (queryCompanyId.equalsIgnoreCase("BOEING"))
        {
           String deleteDupFromSatgeTable = "delete from cabinet_inventory_count_stage where (BIN_ID, COUNT_DATETIME, RECEIPT_ID, COMPANY_ID, PERSONNEL_ID, COUNT_QUANTITY, nvl(DATE_PROCESSED,sysdate+6757)) in ( ";
           deleteDupFromSatgeTable +=" select y.BIN_ID, y.COUNT_DATETIME, y.RECEIPT_ID, y.COMPANY_ID, y.PERSONNEL_ID, y.COUNT_QUANTITY, nvl(y.DATE_PROCESSED,sysdate+6757) from cabinet_inventory_count x,cabinet_inventory_count_stage y where x.COUNT_DATETIME = y.COUNT_DATETIME and ";
           deleteDupFromSatgeTable +=" x.bin_id = y.bin_id and x.receipt_id = y.receipt_id ";
           deleteDupFromSatgeTable +=" and x.personnel_id  = "+personnelid+" and x.COUNT_DATETIME > sysdate -10) ";

           try
 		   {
			db.doUpdate( deleteDupFromSatgeTable );
		   }
		   catch ( Exception ex )
		   {
			ex.printStackTrace();
		    radian.web.emailHelpObj.sendnawazemail("Error delete from cabinet_inventory_count_stage for duplicates","delete from cabinet_inventory_count_stage for duplicates\n\n"+ex.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
		   }
        }

        //Move from temporary table to permanent tbale
		String movetopermtable ="insert into cabinet_inventory_count (bin_id,count_datetime,receipt_id,company_id,personnel_id, count_quantity,date_processed) ";
		movetopermtable += "select bin_id,count_datetime,receipt_id,company_id,personnel_id,sum(count_quantity),date_processed from cabinet_inventory_count_stage ";
		movetopermtable += "group by bin_id,count_datetime,receipt_id,company_id,personnel_id, date_processed ";

		try
		{
			db.doUpdate( movetopermtable );
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
		radian.web.emailHelpObj.sendnawazemail("Error when moving to cabinet_inventory_count table from the stage table","Error when moving to cabinet_inventory_count table from the stage table\n\n"+ex.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
		radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Error in Cabinet Scanner Upload","An error occured while uploading scanner data.\nSome of the data you are trynig to upload has already been uploaded.\nThis might have caused if you did not download to the scanner before doing a scan.\n The Tech Center has been informed and will let you know when it is resolved.\n You don't need to upload the scan again.\nContact Tech Center if you have any questions.",Integer.parseInt(personnelid),true);
			upresult=false;
		}
			//END of Manually fixing data

		DBResultSet dbrs=null;
		ResultSet searchRs=null;
		Vector resultdata=new Vector();
		Hashtable DataH=new Hashtable();

		//try
		//{
		String binsStringfromArray="";
		int cabadded=0;
		Collection binIdsScanned = new Vector();
		binIdsScanned = binids;

		for ( int loop=0; loop < binids.size(); loop++ )
		{
		String binidtmp= ( String ) binids.elementAt( loop );

		if ( cabadded > 0 )
		{
			binsStringfromArray+=",";
		}

		binsStringfromArray+="'" + binidtmp + "'";
		cabadded++;
		}

			HashMap createMrResult = new HashMap();
			String mrsCreated = "";
			String errorMessage = "";

			if (binsStringfromArray.trim().length() > 0)
			{
			CabinetCountProcess cabinetCountProcess=new CabinetCountProcess( "TCM_OPS" );
			BigDecimal personnelId = new BigDecimal(""+personnelid+"");

			Boolean createMrSuccess = new Boolean(false);
			try {
			 createMrResult = cabinetCountProcess.createMaterialRequest(binIdsScanned,lastCompanyId, personnelId);
			 createMrSuccess = (Boolean) createMrResult.get("SUCESS");
			 if (!createMrSuccess.booleanValue()) {
				errorMessage += (String) createMrResult.get("ERROR");
				upresult = false;
			 }
			 mrsCreated = (String) createMrResult.get("CREATEDMRLIST");
			}
			catch (Exception ex2) {
			 errorMessage += ex2.getMessage();
             radian.web.emailHelpObj.sendnawazemail("Error call to create MRs from old code","Error call to create MRs from old code\n\n"+ex2.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
            }
			}

			/*//Manually fixing data comment out line below and uncomment below that line
			String cabmrview="select * from cabinet_mr_create_view where ORDERING_APPLICATION in (select distinct APPLICATION from cabinet_bin where bin_id in ("+binsStringfromArray+")) and MR_QUANTITY > 0 order by ITEM_ID";
			//String cabmrview="select * from cabinet_mr_create_view where company_id = 'BOEING' and ORDERING_APPLICATION in (select distinct APPLICATION from cabinet_bin where bin_id in (select distinct bin_id from cabinet_inventory_count_stage where PERSONNEL_ID = 10146)) and MR_QUANTITY > 0";
			//personnelid = "10146";
			//System.out.println(""+cabmrview+"\n\n Personnel Id  "+personnelid+"");
			int count = 0;
			dbrs=db.doQuery( cabmrview );
			searchRs=dbrs.getResultSet();

			while ( searchRs.next() )
			{
				DataH=new Hashtable();

				//COMPANY_ID
				DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID" ) == null ? "" : searchRs.getString( "COMPANY_ID" ) );
				//CATALOG_ID
				DataH.put( "CATALOG_ID",searchRs.getString( "CATALOG_ID" ) == null ? "" : searchRs.getString( "CATALOG_ID" ) );
				//ITEM_ID
				DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" ) );
				//CAT_PART_NO
				DataH.put( "CAT_PART_NO",searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" ) );
				//ORDERING_FACILITY
				DataH.put( "ORDERING_FACILITY_ID",searchRs.getString( "ORDERING_FACILITY" ) == null ? "" : searchRs.getString( "ORDERING_FACILITY" ) );
				//ORDERING_APPLICATION
				DataH.put( "ORDERING_APPLICATION",searchRs.getString( "ORDERING_APPLICATION" ) == null ? "" : searchRs.getString( "ORDERING_APPLICATION" ) );
				//PART_GROUP_NO
				DataH.put( "PART_GROUP_NO",searchRs.getString( "PART_GROUP_NO" ) == null ? "" : searchRs.getString( "PART_GROUP_NO" ) );
				//INVENTORY_GROUP
				DataH.put( "INVENTORY_GROUP",searchRs.getString( "INVENTORY_GROUP" ) == null ? "" : searchRs.getString( "INVENTORY_GROUP" ) );
				//MR_QUANTITY
				DataH.put( "QUANTITY_RECEIVED",searchRs.getString( "MR_QUANTITY" ) == null ? "" : searchRs.getString( "MR_QUANTITY" ) );
				//STOCKED
				DataH.put( "STOCKED",searchRs.getString( "STOCKED" ) == null ? "" : searchRs.getString( "STOCKED" ) );
				//UNIT_PRICE
				DataH.put( "UNIT_PRICE",searchRs.getString( "UNIT_PRICE" ) == null ? "" : searchRs.getString( "UNIT_PRICE" ) );
				//CATALOG_PRICE
				DataH.put( "CATALOG_PRICE",searchRs.getString( "CATALOG_PRICE" ) == null ? "" : searchRs.getString( "CATALOG_PRICE" ) );
				//ACCOUNT_SYS_ID
				DataH.put( "ACCOUNT_SYS_ID",searchRs.getString( "ACCOUNT_SYS_ID" ) == null ? "" : searchRs.getString( "ACCOUNT_SYS_ID" ) );
				//PACKAGING
				DataH.put("PACKAGING","Cabinet Count");
				//REQUESTOR
				DataH.put("REQUESTOR",""+personnelid+"");
				//CABINET_REPLENISHMENT
				DataH.put("CABINET_REPLENISHMENT","Y");
				//CURRENCY_ID
				DataH.put( "CURRENCY_ID",searchRs.getString( "currency_id" ) == null ? "" : searchRs.getString( "currency_id" ) );
				resultdata.addElement( DataH );
				count++;
			}
			}
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
			upresult=false;
		}
		finally
		{
			if ( dbrs != null )
			{
				dbrs.close();
			}
		}

		NoBuyOrderPo nbpObj=new NoBuyOrderPo();
		String[] resulthg=new String[3];
	String finalmrlist = "";
		for ( int loop=0; loop < resultdata.size(); loop++ )
		{
			Hashtable mrhD =new Hashtable();
			mrhD = ( Hashtable ) resultdata.elementAt( loop );

			try
			{
				resulthg=nbpObj.processRequest( mrhD );
			}
			catch ( Exception ew )
			{
				ew.printStackTrace();
				upresult=false;
			}

			String resultok=resulthg[0].toString();
			String prnumber=resulthg[1].toString();
			String lineItem=resulthg[2].toString();

			if ( !"OK".equalsIgnoreCase( resultok ) )
			{
				upresult=false;
		radian.web.emailHelpObj.senddeveloperemail("Something Went wrong while Creating MR for Cabinet Scanner Count PR " + prnumber + "    " + lineItem+ "","\nSomething Went wrong while Creating MR for Cabinet Count\n Result is not OK\nOK " + resultok + "   PR " + prnumber + "    " + lineItem+ "\n\nCheck Logs.");
			}
		else
		{
		try
		{
			finalmrlist += prnumber + ",";
			String[] args=new String[2];
			args[0]=prnumber;
			args[1]=lineItem;
			db.doProcedure( "p_line_item_allocate",args );
		}
		catch ( Exception dbe )
		{
			radian.web.emailHelpObj.sendnawazemail( "p_line_item_allocate error (pr_number: " + prnumber + " line: " + lineItem + ")",
									"Error occured while trying to call procedure \n\nErr:\n" + dbe.getMessage() + "\n\nPersonnel ID:   "+personnelid+"" );
		 }
		}
		}
*/

        String updatecabmr= "update cabinet_inventory_count c set date_processed =sysdate  where bin_id in ("+binsStringfromArray+") and date_processed is null";

        /*String updatecabmr= "update cabinet_inventory_count c set date_processed =sysdate  where date_processed is null and ( BIN_ID, COUNT_DATETIME, RECEIPT_ID) ";
		updatecabmr+=" in (select c1.BIN_ID,c1.COUNT_DATETIME, c1.RECEIPT_ID from cabinet_part_item_bin b, cabinet_mr_create_view e ,cabinet_inventory_count c1	 ";
		updatecabmr+=" where  b.bin_id=c1.bin_id and b.company_id = e.company_id (+) and b.facility_id = e.ordering_facility (+) and b.application = e.ordering_application (+) ";
		updatecabmr+=" and b.catalog_id = e.catalog_id (+) and b.cat_part_no = e.cat_part_no (+) and b.part_group_no = e.part_group_no (+)  ";
		updatecabmr+=" and nvl(e.count_datetime,c1.count_datetime - 1) < c1.count_datetime) ";*/

		try
		{
			db.doUpdate( updatecabmr );
		}
		catch ( Exception ex )
		{
		radian.web.emailHelpObj.sendnawazemail("Cabinet Scanner Count Error from Updating after creating MR's","Error from Updating after creating MR's\n\n"+ex.getMessage()+"\n\nPersonnel ID:   "+personnelid+"");
		radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Error in Cabinet Scanner Upload","An error occured while uploading scanner data.\nThis error might cause duplicate MRs to be created.\nTech Center has been informed, you will be notified when it is fixed.\n You don't need to upload the scan again.\nCall the Tech Center if you have any questions.",Integer.parseInt(personnelid),true);
			upresult = false;
		}


	/*if (finalmrlist.length() > 0)
	{
		finalmrlist=finalmrlist.substring( 0,finalmrlist.length() - 1 );
	}*/

	String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
	wwwHome = wwwHome.substring(0, wwwHome.length() - 1);
	String resulturl = wwwHome + radian.web.tcmisResourceLoader.getcabupconfirmurl();
	radian.web.emailHelpObj.sendnawazemail("Cabinet Scanner Upload Results "+upresult+"","The upload has been processed successfully, please go to the URL below to look at the results.\n\n\n"+resulturl+"UserAction=Search&mrslist="+mrsCreated+"\n\nerrorMessage:"+errorMessage+"\n\nScanned Bin Ids- "+binsStringfromArray+"\n\nPersonnel ID : "+personnelid+"");
	if (upresult || mrsCreated.length() > 0)
	{
	 radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,"Cabinet Scanner Upload Results","The upload has been processed successfully, please go to the URL below to look at the results.\n\n\n"+resulturl+"UserAction=Search&mrslist="+mrsCreated+"",Integer.parseInt(personnelid),true);
	}
		result.put("SUCCESS",new Boolean(upresult));
		return result;
	 }

	public Hashtable getdownloadInfo( Hashtable inData ) throws Exception
		{
			Hashtable result=new Hashtable();
			String personnelid = inData.get("USER_ID").toString();
			Vector cabids = (Vector)(inData.get("CAB_IDS"));
			String selHub = inData.get("SELECTED_HUB").toString();

			String allowedcabs = "";
			for ( int jk=0; jk < cabids.size(); jk++ )
			{
					String cabsstmp = ( String ) ( cabids.elementAt(jk) );
					allowedcabs += "'"+cabsstmp+ "',";
			}
			allowedcabs = allowedcabs.substring(0,allowedcabs.length()-1);

			System.out.println(allowedcabs);

			String query="select lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID,rpad(COMPANY_ID,30,' ') COMPANY_ID1 from cabinet_bin_item_view where CABINET_ID in ("+allowedcabs+") and item_id is not null and status = 'A' order by BIN_ID,ITEM_ID asc";
			DBResultSet dbrs=null;
			ResultSet searchRs=null;
			Hashtable resultdata=new Hashtable();
			Vector cabId =new Vector();
			Vector cabDesc = new Vector();
			Vector receiptids = new Vector();
			Vector logonids = new Vector();
            Vector legacyReceiptIds = new Vector();
            Vector allCabinetBins = new Vector();

            try
			{
				dbrs=db.doQuery( query );
				searchRs=dbrs.getResultSet();

			 while ( searchRs.next() )
			 {
			 Hashtable DataH =new Hashtable();
			 //BIN_ID
			 DataH.put( "BIN_ID",searchRs.getString( "BIN_ID" ) == null ? "00000000" : searchRs.getString( "BIN_ID" ) );
			 //BIN_ID1
			 DataH.put( "BIN_ID1",searchRs.getString( "BIN_ID1" ) == null ? "00000000" : searchRs.getString( "BIN_ID1" ) );
			 //CABINET_ID
			 DataH.put( "CABINET_ID",searchRs.getString( "CABINET_ID" ) == null ? "00000000" : searchRs.getString( "CABINET_ID" ) );
			 //CABINET_ID1
			 DataH.put( "CABINET_ID1",searchRs.getString( "CABINET_ID1" ) == null ? "00000000" : searchRs.getString( "CABINET_ID1" ) );
			 //COMPANY_ID
			 DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID1" ) == null ? "000000000000000000000000000000" : searchRs.getString( "COMPANY_ID1" ) );
			 //BIN_NAME
			 DataH.put("BIN_NAME",searchRs.getString("BIN_NAME")==null?"":searchRs.getString("BIN_NAME"));
			 //ITEM
			 DataH.put("ITEM_ID",searchRs.getString("ITEM_ID")==null?"00000000":searchRs.getString("ITEM_ID"));
			 //ITEM1
			 DataH.put("ITEM_ID1",searchRs.getString("ITEM_ID1")==null?"00000000":searchRs.getString("ITEM_ID1"));

			 cabDesc.add(DataH);
			 }
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				if ( dbrs != null )
				{
					dbrs.close();
				}
			}

            /*All cabinet bins, including inactive*/

            try
			{
                String allBinQuery="select lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID,rpad(COMPANY_ID,30,' ') COMPANY_ID1,STATUS,COUNT_TYPE from cabinet_bin_item_view where count_type = 'R' and CABINET_ID in ("+allowedcabs+") and item_id is not null ";
                allBinQuery +=" union all select lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(decode(CATALOG_DESC,'Haas Products', item_id, FX_BEST_BIN (bin_id)),8,'0') ITEM_ID1,decode(CATALOG_DESC,'Haas Products', item_id, FX_BEST_BIN (bin_id)) item_id,rpad(COMPANY_ID,30,' ') COMPANY_ID1,STATUS,COUNT_TYPE from cabinet_bin_item_view where count_type = 'I' and CPIG_STATUS in ('A','D') and CABINET_ID in ("+allowedcabs+") and item_id is not null ";
                allBinQuery +=" union all select lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,BIN_NAME,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID,rpad(COMPANY_ID,30,' ') COMPANY_ID1,STATUS,COUNT_TYPE from cabinet_bin_item_view where count_type = 'K' and CPIG_STATUS = 'A' and CABINET_ID in ("+allowedcabs+") and item_id is not null order by CABINET_ID,BIN_ID,ITEM_ID asc";                
                dbrs=db.doQuery( allBinQuery );
				searchRs=dbrs.getResultSet();

			 while ( searchRs.next() )
			 {
			 Hashtable DataH =new Hashtable();
			 //BIN_ID
			 DataH.put( "BIN_ID",searchRs.getString( "BIN_ID" ) == null ? "00000000" : searchRs.getString( "BIN_ID" ) );
			 //BIN_ID1
			 DataH.put( "BIN_ID1",searchRs.getString( "BIN_ID1" ) == null ? "00000000" : searchRs.getString( "BIN_ID1" ) );
			 //CABINET_ID
			 DataH.put( "CABINET_ID",searchRs.getString( "CABINET_ID" ) == null ? "00000000" : searchRs.getString( "CABINET_ID" ) );
			 //CABINET_ID1
			 DataH.put( "CABINET_ID1",searchRs.getString( "CABINET_ID1" ) == null ? "00000000" : searchRs.getString( "CABINET_ID1" ) );
			 //COMPANY_ID
			 DataH.put( "COMPANY_ID",searchRs.getString( "COMPANY_ID1" ) == null ? "000000000000000000000000000000" : searchRs.getString( "COMPANY_ID1" ) );
			 //BIN_NAME
			 DataH.put("BIN_NAME",searchRs.getString("BIN_NAME")==null?"":searchRs.getString("BIN_NAME"));
			 //ITEM
			 DataH.put("ITEM_ID",searchRs.getString("ITEM_ID")==null?"00000000":searchRs.getString("ITEM_ID"));
			 //ITEM1
			 DataH.put("ITEM_ID1",searchRs.getString("ITEM_ID1")==null?"00000000":searchRs.getString("ITEM_ID1"));
             //STATUS
             DataH.put("STATUS",searchRs.getString("STATUS")==null?"I":searchRs.getString("STATUS"));
             //COUNT_TYPE
             DataH.put("COUNT_TYPE",searchRs.getString("COUNT_TYPE")==null?"I":searchRs.getString("COUNT_TYPE"));
                 
             allCabinetBins.add(DataH);
			 }
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				if ( dbrs != null )
				{
					dbrs.close();
				}
			}

            try
			{
				String query1="select distinct lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID from cabinet_receipt_item_view where CABINET_ID in ("+allowedcabs+") ";
/*				if ("HSD- Puerto Rico Hub".equalsIgnoreCase(selHub)) {
				 query1 += " and EXPIRE_DATE > sysdate+7";
				}
				else if ("Decatur Hub".equalsIgnoreCase(selHub)) {
				 query1 += " and EXPIRE_DATE > sysdate+14";
				}
				else if ("Phoenix Hub".equalsIgnoreCase(selHub)) {
				 query1 += " and EXPIRE_DATE > sysdate+21";
				}
				else {
				 query1 += " and EXPIRE_DATE > sysdate";
				}*/
				query1 += " and item_id is not null order by RECEIPT_ID ASC";

				dbrs=db.doQuery( query1 );
				searchRs=dbrs.getResultSet();

			 while ( searchRs.next() )
			 {
			 Hashtable DataH =new Hashtable();
			 //RECEIPT_ID
			 DataH.put( "RECEIPT_ID",searchRs.getString( "RECEIPT_ID" ) == null ? "00000000" : searchRs.getString( "RECEIPT_ID" ) );
			 //RECEIPT_ID1
			 DataH.put( "RECEIPT_ID1",searchRs.getString( "RECEIPT_ID1" ) == null ? "00000000" : searchRs.getString( "RECEIPT_ID1" ) );
			 //ITEM_ID
			 DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "00000000" : searchRs.getString( "ITEM_ID" ) );
			 //ITEM_ID1
			 DataH.put( "ITEM_ID1",searchRs.getString( "ITEM_ID1" ) == null ? "00000000" : searchRs.getString( "ITEM_ID1" ) );

			 receiptids.add(DataH);
			 }
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				if ( dbrs != null )
				{
					dbrs.close();
				}
			}

            try
			{
				String query1="select distinct CUSTOMER_RECEIPT_ID,lpad(RECEIPT_ID,8,'0') RECEIPT_ID1,RECEIPT_ID,lpad(ITEM_ID,8,'0') ITEM_ID1,ITEM_ID from cabinet_receipt_item_view where CABINET_ID in ("+allowedcabs+") and CUSTOMER_RECEIPT_ID is not null";
				query1 += " and item_id is not null order by CUSTOMER_RECEIPT_ID ASC";

				dbrs=db.doQuery( query1 );
				searchRs=dbrs.getResultSet();

			 while ( searchRs.next() )
			 {
			 Hashtable DataH =new Hashtable();
			 //RECEIPT_ID
			 DataH.put( "RECEIPT_ID",searchRs.getString( "RECEIPT_ID" ) == null ? "00000000" : searchRs.getString( "RECEIPT_ID" ) );
			 //RECEIPT_ID1
			 DataH.put( "RECEIPT_ID1",searchRs.getString( "RECEIPT_ID1" ) == null ? "00000000" : searchRs.getString( "RECEIPT_ID1" ) );
			 //ITEM_ID
			 DataH.put( "ITEM_ID",searchRs.getString( "ITEM_ID" ) == null ? "00000000" : searchRs.getString( "ITEM_ID" ) );
			 //ITEM_ID1
			 DataH.put( "ITEM_ID1",searchRs.getString( "ITEM_ID1" ) == null ? "00000000" : searchRs.getString( "ITEM_ID1" ) );
             //CUSTOMER_RECEIPT_ID
             DataH.put( "CUSTOMER_RECEIPT_ID",searchRs.getString( "CUSTOMER_RECEIPT_ID" ) == null ? "0000000000" : searchRs.getString( "CUSTOMER_RECEIPT_ID" ).trim() );
             legacyReceiptIds.add(DataH);
			 }
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				if ( dbrs != null )
				{
					dbrs.close();
				}
			}

            try
			{
				String query1="select rpad(LOGON_ID,20,' ') LOGON_ID,lpad(PERSONNEL_ID,8,'0') PERSONNEL_ID from personnel where personnel_id in (select personnel_id from user_group_member where user_group_id = 'Scanner Fucntions' and facility_id = '"+selHub+"') and company_id = 'Radian' order by LOGON_ID";

				dbrs=db.doQuery( query1 );
				searchRs=dbrs.getResultSet();

			 while ( searchRs.next() )
			 {
			 Hashtable DataH =new Hashtable();
			 //LOGON_ID
			 DataH.put( "LOGON_ID",searchRs.getString( "LOGON_ID" ) == null ? "00000000000000000000" : searchRs.getString( "LOGON_ID" ).toUpperCase() );
			 //PERSONNEL_ID
			 DataH.put( "PERSONNEL_ID",searchRs.getString( "PERSONNEL_ID" ) == null ? "00000000" : searchRs.getString( "PERSONNEL_ID" ) );

			 logonids.add(DataH);
			 }
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			finally
			{
				if ( dbrs != null )
				{
					dbrs.close();
				}
			}

			StringBuffer downfile =new StringBuffer();
			String linefeedd = "";
			linefeedd += (char)(13);
			linefeedd += (char)(10);

			downfile.append( "FD|LINKERR.TXT" + linefeedd );
			downfile.append( "NO|001|SR|1" + linefeedd );
			downfile.append( "IF|&99|=|0|+1|ERROR6" + linefeedd );
			downfile.append( "NO|001|TT" + linefeedd );
			downfile.append( "NO|001|TF|recptitems.csv|A" + linefeedd );
			downfile.append( "IF|&99|=|0|+1|ERROR1" + linefeedd );
			downfile.append( "NO|001|TF|cabmgmt.csv|B" + linefeedd );
			downfile.append( "IF|&99|=|0|+1|ERROR2" + linefeedd );
			downfile.append( "NO|001|TF|personnelids.csv|E" + linefeedd );
			downfile.append( "IF|&99|=|0|+1|ERROR5" + linefeedd );
			downfile.append( "SK|RESETFILES" + linefeedd );
			downfile.append( "LB|ERROR1" + linefeedd );
			downfile.append( "FC|ERROR1.TXT|LINKERR.TXT" + linefeedd );
			downfile.append( "SK|QUIT" + linefeedd );
			downfile.append( "LB|ERROR2" + linefeedd );
			downfile.append( "FC|ERROR2.TXT|LINKERR.TXT" + linefeedd );
			downfile.append( "SK|QUIT" + linefeedd );
			downfile.append( "LB|ERROR5" + linefeedd );
			downfile.append( "FC|ERROR5.TXT|LINKERR.TXT" + linefeedd );
			downfile.append( "SK|QUIT" + linefeedd );
			downfile.append( "LB|ERROR6" + linefeedd );
			downfile.append( "FC|ERROR6.TXT|LINKERR.TXT" + linefeedd );
			downfile.append( "SK|QUITPCONLY" + linefeedd );
			downfile.append( "LB|RESETFILES" + linefeedd );
			downfile.append( "NO|001|FD|C" + linefeedd );
			downfile.append( "NO|001|FD|D" + linefeedd );
			downfile.append( "LB|QUIT" + linefeedd );
			downfile.append( "NO|001|QX|1" + linefeedd );
			downfile.append( "LB|QUITPCONLY" + linefeedd );
			downfile.append( "QX" + linefeedd );

			//downfile.append( "QX" + linefeedd );

			cabDesc.trimToSize();
			receiptids.trimToSize();
			logonids.trimToSize();

			result.put("CAB_BINS",cabDesc);
			result.put("RECEIPT_IDS",receiptids);
			result.put("LOGON_IDS",logonids);
			result.put("DOWNLOADSCRIPT",downfile);
            result.put("LEGACY_RECEIPT_IDS",legacyReceiptIds);
            result.put("ALL_CAB_BINS",allCabinetBins);

			return result;
		}

	public Hashtable getcabinetInfo( Hashtable inData ) throws Exception
	{
		Hashtable result=new Hashtable();
		String personnelid = inData.get("USER_ID").toString();
		Vector hubsetdetails = (Vector)(inData.get("WAC_IDS"));

		String allowedwacs = "";
		for ( int jk=0; jk < hubsetdetails.size(); jk++ )
		{
				String wacstmp = ( String ) ( hubsetdetails.elementAt(jk) );
				allowedwacs += "'"+wacstmp+ "',";
		}
		allowedwacs = allowedwacs.substring(0,allowedwacs.length()-1);

		System.out.println(allowedwacs);
		String query="select * from hub_cabinet_view where APPLICATION in ("+allowedwacs+") order by APPLICATION";
		DBResultSet dbrs=null;
		ResultSet rs=null;
		Hashtable resultdata=new Hashtable();
		Vector cabId =new Vector();
		Vector cabDesc = new Vector();

		try
		{
			dbrs=db.doQuery( query );
			rs=dbrs.getResultSet();
			while ( rs.next() )
		 {
			 String tmpFacID=rs.getString( "facility_id" );
			 String tmpappl =rs.getString( "APPLICATION" );
			 String tmpcabId =rs.getString( "CABINET_ID" );
			 String tmpCabDesc =rs.getString( "CABINET_NAME" );

			 cabId.add(tmpcabId);
			 cabDesc.add(""+tmpFacID+"-"+tmpappl+"-"+tmpCabDesc+"");
		 }
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( dbrs != null )
			{
				dbrs.close();
			}
		}

		cabId.trimToSize();
		cabDesc.trimToSize();

		result.put("CAB_IDS",cabId);
		result.put("CAB_DESC",cabDesc);
		return result;
	}

	public Hashtable getinitialInfo( Hashtable inData ) throws Exception
	{
		Hashtable result=new Hashtable();
		String personnelid = inData.get("USER_ID").toString();

		Hashtable hub_list_set  = radian.web.HTMLHelpObj.createHubList(db,personnelid,"Radian");
		Hashtable hubsetdetails = (Hashtable)(hub_list_set.get("HUB_LIST"));

		String allowedhubs = "";
		for ( Enumeration e=hubsetdetails.keys(); e.hasMoreElements(); )
		{
				String branch_plant= ( String ) ( e.nextElement() );
				String hub_name= ( String ) ( hubsetdetails.get( branch_plant ) );
				allowedhubs += "'"+hub_name+ "',";
		}
		allowedhubs = allowedhubs.substring(0,allowedhubs.length()-1);

		String query="select * from hub_cabinet_view where PREFERRED_WAREHOUSE in ("+allowedhubs+") order by PREFERRED_WAREHOUSE";
		DBResultSet dbrs=null;
		ResultSet rs=null;
		Hashtable resultdata =new Hashtable();
		Vector hubID = new Vector();
		Vector hubDesc = new Vector();

		try
		{
			dbrs=db.doQuery( query );
			rs=dbrs.getResultSet();

			String lastHub="";

			while ( rs.next() )
			{
				String tmpFacID=rs.getString( "facility_id" );
				String tmpHub=rs.getString( "PREFERRED_WAREHOUSE" );

				if ( !tmpHub.equalsIgnoreCase( lastHub ) )
				{
					//hub info
					hubID.addElement(tmpHub);
					hubDesc.addElement(tmpHub);

					Hashtable fh=new Hashtable();
					//facility data
					Vector facID=new Vector();
					Vector facDesc=new Vector();
					facID.addElement( tmpFacID );
					facDesc.addElement( rs.getString( "facility_name" ) );
					Hashtable h=new Hashtable();
					//application data
					Vector application=new Vector();
					Vector applicationDesc=new Vector();
					application.addElement( rs.getString( "application" ) );
					applicationDesc.addElement( rs.getString( "application_desc" ) );
					h.put( "APPLICATION",application );
					h.put( "APPLICATION_DESC",applicationDesc );
					fh.put( tmpFacID,h );
					//putting them all together
					fh.put( "FACILITY_ID",facID );
					fh.put( "FACILITY_DESC",facDesc );
					//resultdata.put( "DATA",fh );
					resultdata.put(tmpHub,fh);
				}
				else
				{ //hub already exist
					Hashtable fh= ( Hashtable ) resultdata.get( tmpHub );
					Vector facID= ( Vector ) fh.get( "FACILITY_ID" );
					Vector facDesc= ( Vector ) fh.get( "FACILITY_DESC" );
					if ( !facID.contains( tmpFacID ) )
					{
						facID.addElement( tmpFacID );
						facDesc.addElement( rs.getString( "facility_name" ) );
						Hashtable h=new Hashtable( 3 );
						Vector application=new Vector();
						Vector applicationDesc=new Vector();
						application.addElement( rs.getString( "application" ) );
						applicationDesc.addElement( rs.getString("application_desc")=="."?"":rs.getString("application_desc") );
						h.put( "APPLICATION",application );
						h.put( "APPLICATION_DESC",applicationDesc );
						fh.put( tmpFacID,h );
					}
					else
					{
						Hashtable h= ( Hashtable ) fh.get( tmpFacID );
						Vector application= ( Vector ) h.get( "APPLICATION" );
						Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );
						if ( !application.contains( rs.getString( "application" ) ) )
						{
							application.addElement( rs.getString( "application" ) );
							applicationDesc.addElement( rs.getString("application_desc")=="."?"":rs.getString("application_desc") );
						}
						h.put( "APPLICATION",application );
						h.put( "APPLICATION_DESC",applicationDesc );
						fh.put( tmpFacID,h );
					}
					fh.put( "FACILITY_ID",facID );
					fh.put( "FACILITY_DESC",facDesc );
					//resultdata.put( "DATA",fh );
					resultdata.put(tmpHub,fh);
				}
				lastHub=tmpHub;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if ( dbrs != null )
			{
				dbrs.close();
			}
		}

		hubID.trimToSize();
		hubDesc.trimToSize();

		result.put( "All_DATA",resultdata );
		result.put("HUB_IDS",hubID);
		result.put("HUB_NAMES",hubDesc);

		return result;
	}

} //end of class