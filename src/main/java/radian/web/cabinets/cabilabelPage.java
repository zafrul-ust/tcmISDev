package radian.web.cabinets;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;

import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.BarCodeHelpObj;
import radian.web.barcode.ZPLBarCodeGenerator;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.datasrcmgrs.jdbcdatasrc.JDBCHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 01-09-04 - Not double closing db
 * 04-26-04 - Changes to be able to print receipt component labels for kits
 * 06-25-04 Adding log statements to trace a memory usage issue
 * 06-30-04 Changing Printer Path to be based on personnel ID
 */

public class cabilabelPage
{
	ServerResourceBundle bundle=null;
	TcmISDBModel db = null;
	private String paperSize = "";
	private Vector invengrpvec = new Vector();
	private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");

	public cabilabelPage(ServerResourceBundle b, TcmISDBModel d)
	{
		bundle = b;
		db = d;
	}

	public cabilabelPage(TcmISDBModel d)
	{
		db = d;
	}

	public cabilabelPage()
	{

	}

	public void setpaperSize(String id)
	{
		this.paperSize = id;
	}

	private String getpaperSize()  throws Exception
	{
		return this.paperSize;
	}

	public String generateLabels(Vector cabidnums,Vector cab8numbs,String gencase,String HubNoforlabel,String personnelId,String printerPath,String printerRes, String facilityName) throws ServletException,IOException
	{
		String Query_Statement = "";
		Vector dataV1 = new Vector();
		Vector dataV2 = new Vector();

		StringBuffer MsgSB = new StringBuffer();

		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);

		String updateprintdate = "";
		String wwwHome=radian.web.tcmisResourceLoader.gethosturl();
		String url = wwwHome + "labels/cabinetlab"+tmpReqNum.toString()+".pdf";
		String jnlpurl = wwwHome + "labels/cabinetlab"+tmpReqNum.toString()+".jnlp";

		if (cabidnums.size() == 0)
		{
			return "";
		}

		String allowedcabs="";
		try
		{

			for ( int jk=0; jk < cabidnums.size(); jk++ )
			{
				String cabsstmp= ( String ) ( cabidnums.elementAt( jk ) );
				allowedcabs+="" + cabsstmp + ",";
			}
			allowedcabs=allowedcabs.substring( 0,allowedcabs.length() - 1 );

			if  ("generatecabbinlabels".equalsIgnoreCase(gencase) || "generatebinlistlabels".equalsIgnoreCase(gencase) )
			{
				dataV1 = getcabbindata(gencase, allowedcabs, facilityName);
				dataV2 = getcabData(allowedcabs);
			}
			else if  ("generatebaseline".equalsIgnoreCase(gencase) )
			{

			}
			else
			{
				dataV1 = getcabData(allowedcabs);
			}
		} //End Try
		catch ( Exception e )
		{
			e.printStackTrace();
			return "";
		}

		dataV1.trimToSize();

		DBResultSet dbrs=null;
		ResultSet rs=null;
		try
		{
			String papersize=this.getpaperSize().trim();
			/*String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " + personnelId + " and x.LABEL_STOCK = '" + papersize + "'";
		  String printerPath = "";
		  String printerRes = "";
          try
		  {
			dbrs=db.doQuery( iszplprinter );
			rs=dbrs.getResultSet();

			while ( rs.next() )
			{
			  printerPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
			  printerRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );
            }
		  }
		  catch ( Exception e )
		  {
			e.printStackTrace();
		  }
		  finally
		  {
			if ( dbrs != null ) { dbrs.close();}
		  }*/

			if ( printerPath.trim().length() > 0 && !"generatebaseline".equalsIgnoreCase(gencase) )
				//if ( "31".equalsIgnoreCase( papersize ) && !"generatebaseline".equalsIgnoreCase(gencase) && "2118".equalsIgnoreCase(HubNoforlabel))
			{
				ZPLBarCodeGenerator obj=new ZPLBarCodeGenerator( db );
				obj.buildCabZpl( dataV1,tmpReqNum.toString(),gencase,papersize,HubNoforlabel,dataV2,personnelId,printerPath,printerRes,invengrpvec );
				url=jnlpurl;
			}
			else
			{
				if (dataV1.size() > 0 || "generatebaseline".equalsIgnoreCase(gencase) )
					buildTest( dataV1,"cabinetlab" + tmpReqNum.toString() + ".pdf",gencase,allowedcabs,HubNoforlabel,dataV2 );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.out.println( "*** Error in calling barcode generator***" );
			return "";
		}

		//System.out.println(url);
		return url;
	}

	private Vector getcabData( String allowedcabs )
	{
		Vector dataV=new Vector();
		DBResultSet dbrs=null;
		ResultSet searchRs=null;
		Hashtable data=new Hashtable();
		BarCodeHelpObj BarcodeConverter=new BarCodeHelpObj();

		String query="select lpad(APPLICATION_ID,8,'0') CABINET_ID1,APPLICATION_ID CABINET_ID,APPLICATION_DESC CABINET_NAME,INVENTORY_GROUP,LOCATION_DETAIL";
		query += " from customer.fac_loc_app";
		query +=" where APPLICATION_ID in (" + allowedcabs +  ") order by APPLICATION_ID";

		try
		{
			dbrs=db.doQuery( query );
			searchRs=dbrs.getResultSet();
			data=new Hashtable();

			String binBarcodeRId="";
			String cabBarcodeRId="";

			while ( searchRs.next() )
			{
				String tmpcabid1=searchRs.getString( "CABINET_ID1" ) == null ? "" : searchRs.getString( "CABINET_ID1" );
				String tmpcabid=searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" );
				data=new Hashtable();

				cabBarcodeRId=com.tcmis.common.util.BarCodeHandler.Code128b( "CAB" + tmpcabid1 );
				data.put( "DETAIL_0",cabBarcodeRId );
				data.put( "DETAIL_1", ( "CAB" + tmpcabid1 ) );
				data.put( "DETAIL_2",searchRs.getString( "CABINET_NAME" ) == null ? "" : searchRs.getString( "CABINET_NAME" ) );
				data.put( "DETAIL_3",searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" ) );
                data.put( "LOCATION_DETAIL",searchRs.getString( "LOCATION_DETAIL" ) == null ? "" : searchRs.getString( "LOCATION_DETAIL" ) );
                String inventorygrp = searchRs.getString("INVENTORY_GROUP")==null?" ":searchRs.getString("INVENTORY_GROUP");
				if ( !invengrpvec.contains( inventorygrp ) )
				{
					invengrpvec.add(inventorygrp);
				}
				data.put("INVENTORY_GROUP",inventorygrp);

				dataV.addElement( data );
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
		return dataV;
	}

	private Vector getcabbindata( String gencase,String allowedcabs, String facilityName)
	{
		Vector dataV=new Vector();
		DBResultSet dbrs=null;
		ResultSet searchRs=null;
		Hashtable data=new Hashtable();
		BarCodeHelpObj BarcodeConverter=new BarCodeHelpObj();

		String query="";
		if  ("generatebinlistlabels".equalsIgnoreCase(gencase) )
		{
			query="select MATERIAL_ID,LOCATION_DETAIL,INVENTORY_GROUP,REORDER_POINT,KANBAN_REORDER_QUANTITY,STOCKING_LEVEL,QC_DOC,CABINET_NAME,PACKAGING, MATERIAL_ID_STRING, MFG_DESC,lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,ITEM_ID,BIN_NAME,NUM_CAT_PARTS,CAT_PART_NO,PART_DESCRIPTION,PART_SHORT_NAME from Cabinet_bin_label_view where BIN_ID in (" + allowedcabs + ")";
			if (facilityName != null && facilityName.length() > 0) {
				query += " and FACILITY_ID = '" + facilityName + "' ";
			}
			query += " order by BIN_ID";
		}
		else
		{
			query="select MATERIAL_ID,LOCATION_DETAIL,INVENTORY_GROUP,REORDER_POINT,KANBAN_REORDER_QUANTITY,STOCKING_LEVEL,QC_DOC,CABINET_NAME,PACKAGING, MATERIAL_ID_STRING, MFG_DESC,lpad(BIN_ID,8,'0') BIN_ID1,BIN_ID,lpad(CABINET_ID,8,'0') CABINET_ID1,CABINET_ID,ITEM_ID,BIN_NAME,NUM_CAT_PARTS,CAT_PART_NO,PART_DESCRIPTION,PART_SHORT_NAME from Cabinet_bin_label_view where CABINET_ID in (" + allowedcabs + ")";
			if (facilityName != null && facilityName.length() > 0) {
				query += " and FACILITY_ID = '" + facilityName + "' ";
			}
			query += " order by CABINET_ID";
		}

		try
		{
			dbrs=db.doQuery( query );
			searchRs=dbrs.getResultSet();
			data=new Hashtable();

			String binBarcodeRId="";
			String cabBarcodeRId="";

			while ( searchRs.next() )
			{
				String tmpcabid=searchRs.getString( "CABINET_ID1" ) == null ? "" : searchRs.getString( "CABINET_ID1" );
				String tmpbinId=searchRs.getString( "BIN_ID1" ) == null ? "" : searchRs.getString( "BIN_ID1" );
				int tmpnoofprts=searchRs.getInt( "NUM_CAT_PARTS" );
				String tmppartId=searchRs.getString( "CAT_PART_NO" ) == null ? "" : searchRs.getString( "CAT_PART_NO" );
				String tmppartdesc=searchRs.getString( "PART_DESCRIPTION" ) == null ? "" : searchRs.getString( "PART_DESCRIPTION" );
				String tmpitemid=searchRs.getString( "ITEM_ID" ) == null ? "" : searchRs.getString( "ITEM_ID" );
				String mfgname =searchRs.getString( "MFG_DESC" ) == null ? "" : searchRs.getString( "MFG_DESC" );
				String msdsnum=searchRs.getString( "MATERIAL_ID_STRING" ) == null ? "" : searchRs.getString( "MATERIAL_ID_STRING" );
				String packging=searchRs.getString( "PACKAGING" ) == null ? "" : searchRs.getString( "PACKAGING" );
				String partShortName=searchRs.getString( "PART_SHORT_NAME" ) == null ? "" : searchRs.getString( "PART_SHORT_NAME" );

				data=new Hashtable();
				binBarcodeRId=com.tcmis.common.util.BarCodeHandler.Code128b( tmpcabid + "-" + tmpbinId );
				//System.out.println("binBarcodeRId   "+tmpcabid + "-" + tmpbinId+"  Converts to  "+binBarcodeRId);
				data.put( "DETAIL_0",binBarcodeRId );
				data.put( "DETAIL_1",tmpcabid + "-" + tmpbinId );
				data.put( "ITEM_ID",tmpitemid);
				data.put( "CAT_PART_NO",tmppartId);
				data.put( "DETAIL_2",searchRs.getString( "BIN_NAME" ) == null ? "" : searchRs.getString( "BIN_NAME" ) );
				if ( tmpnoofprts > 1 )
				{
					data.put( "DETAIL_3","Multiple / Multiple" );
					data.put( "DETAIL_4","Multiple" );
				}
				else
				{
					data.put( "DETAIL_3",tmpitemid + " / " + tmppartId );
					data.put( "DETAIL_4",""+tmppartdesc+"" );
				}

				data.put( "DETAIL_5",searchRs.getString( "CABINET_ID" ) == null ? "" : searchRs.getString( "CABINET_ID" ) );
				data.put( "DETAIL_6",searchRs.getString( "BIN_ID" ) == null ? "" : searchRs.getString( "BIN_ID" ) );
				data.put( "DETAIL_7",mfgname );
				data.put( "DETAIL_8",""+msdsnum+"" );
				data.put( "DETAIL_9",""+packging+"" );
				data.put( "DETAIL_10",searchRs.getString( "QC_DOC" ) == null ? "" : searchRs.getString( "QC_DOC" ) );
				data.put( "DETAIL_11",searchRs.getString( "CABINET_NAME" ) == null ? "" : searchRs.getString( "CABINET_NAME" ) );
				data.put( "DETAIL_12", ( "CAB" + tmpcabid ) );
				data.put( "DETAIL_13",searchRs.getString( "REORDER_POINT" ) == null ? "" : searchRs.getString( "REORDER_POINT" ) );
				data.put( "DETAIL_14",searchRs.getString( "STOCKING_LEVEL" ) == null ? "" : searchRs.getString( "STOCKING_LEVEL" ) );
				data.put( "KANBAN_REORDER_QUANTITY",searchRs.getString( "KANBAN_REORDER_QUANTITY" ) == null ? "" : searchRs.getString( "KANBAN_REORDER_QUANTITY" ) );
				String inventorygrp = searchRs.getString("INVENTORY_GROUP")==null?" ":searchRs.getString("INVENTORY_GROUP");
				if ( !invengrpvec.contains( inventorygrp ) )
				{
					invengrpvec.add(inventorygrp);
				}
				data.put("INVENTORY_GROUP",inventorygrp);
				data.put("PART_SHORT_NAME",partShortName);
                data.put("LOCATION_DETAIL", searchRs.getString("LOCATION_DETAIL") == null ? "" : searchRs.getString("LOCATION_DETAIL"));
                data.put("MATERIAL_ID", searchRs.getString("MATERIAL_ID") == null ? "" : searchRs.getString("MATERIAL_ID"));
                dataV.addElement( data );
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

		dataV.trimToSize();
		return dataV;
	}

	public void buildTest( Vector AllTheData,String NameofFile,String gencase,String wherecabs,String HubNo,Vector CabData )  throws Exception
	{
		ACJEngine en=new ACJEngine();
		en.setDebugMode( true );
		en.setX11GfxAvailibility( false );
		en.setTargetOutputDevice( ACJConstants.PDF );
		OutputStream os=null;
		TemplateManager tm=null;
        String companyId="";

        ACJOutputProcessor eClient=new ACJOutputProcessor();

		String fontmappath=radian.web.tcmisResourceLoader.getfontpropertiespath();
		String templatpath=radian.web.tcmisResourceLoader.getlabeltempplatepath();
		String writefilepath=radian.web.tcmisResourceLoader.getsavelabelpath();
		String papersize=this.getpaperSize().trim();

		eClient.setPathForFontMapFile( fontmappath );

		try
		{
			if ( "generatecabbinlabels".equalsIgnoreCase( gencase ) )
			{
				if ("31".equalsIgnoreCase(papersize))
				{
					if("2118".equalsIgnoreCase(HubNo))
					{
						en.readTemplate( "" + templatpath + "cabinetandbin312118.erw" );
					}
					else
					{
						en.readTemplate( "" + templatpath + "cabinetandbin31.erw" );
					}
				}
				else
				{
					if("2118".equalsIgnoreCase(HubNo))
					{
						en.readTemplate( "" + templatpath + "cabinetbin" + papersize + "2118.erw" );
					}
					else
					{
						en.readTemplate( "" + templatpath + "cabinetbin" + papersize + ".erw" );
					}
				}
			}
			else if ( "generatebinlistlabels".equalsIgnoreCase( gencase ) )
			{
				if("2118".equalsIgnoreCase(HubNo))
				{
					en.readTemplate( "" + templatpath + "cabinetbin" + papersize + "2118.erw" );
				}
				else
				{
					en.readTemplate( "" + templatpath + "cabinetbin" + papersize + ".erw" );
				}
			}
			else if ( "generatebaseline".equalsIgnoreCase( gencase ) )
			{
				DBResultSet dbrs = null;
                String workAreaBaselineTemplate = "";
                ResultSet rs = null;
				try
				{
					String query="select distinct f.company_id,f.work_area_baseline_template from customer.facility f, customer.fac_loc_app fla";
                    query +=" where f.company_id = fla.company_id and f.facility_id = fla.facility_id and fla.application_id in (" + wherecabs +  ") ";
					dbrs=db.doQuery( query );
					rs=dbrs.getResultSet();
					while ( rs.next() )
					{
						companyId = ( rs.getString( "company_id" ) == null ? "" : rs.getString( "company_id" ) );
                        workAreaBaselineTemplate = ( rs.getString( "work_area_baseline_template" ) == null ? "" : rs.getString( "work_area_baseline_template" ) );
                        break;
                    }
				}
				catch ( Exception e )
				{
					//e.printStackTrace();
				}
				finally
				{
					if ( dbrs != null ){dbrs.close();}
				}

                if (workAreaBaselineTemplate.length() == 0) {
                    workAreaBaselineTemplate = "cabinetbaseline.erw";
                }
                en.readTemplate(""+templatpath+workAreaBaselineTemplate);
			}else{
				en.readTemplate( "" + templatpath + "cabinet" + papersize + ".erw" );
			}
		}
		catch ( Exception e )
		{
			System.out.println( "ERROR loading template" );
			//e.printStackTrace();
			throw e;
		}

		tm=en.getTemplateManager();
		if ( "generatebaseline".equalsIgnoreCase( gencase ) )
		{
			JDBCHandler ds=new JDBCHandler();
			String dbDriver="oracle.jdbc.driver.OracleDriver";
			String dbUrl = radian.web.databaseResourceLoader.getproddburl();
			String tcmUserName = "";
			String tcmPasswd = "";

            if ("LOCKHEED".equalsIgnoreCase(companyId)) {
                tcmUserName = radian.web.databaseResourceLoader.getlmcouserid();
			    tcmPasswd = radian.web.databaseResourceLoader.getlmcopassword();
            }else {
                tcmUserName= radian.web.databaseResourceLoader.getopsuserid();
			    tcmPasswd=radian.web.databaseResourceLoader.getopspassword();
            }
            ds.connect( dbDriver,dbUrl,tcmUserName,tcmPasswd,true );
			en.setDataSource( ds );
            tm.setWHEREClause( "SEC_00","STATUS = 'A' and CABINET_ID in (" + wherecabs + ")" );
			try
			{
				eClient.setReportData( en.generateReport() );
			}
			catch ( Exception ex )
			{
				System.out.println( "ERROR generateReport "+ex.getMessage() );
				//ex.printStackTrace();
				throw ex;
			}
		}
		else
		{
			//reoprtlog.info("buildTest    Start "+NameofFile.toString()+"    Size: "+AllTheData.size()+" ");
			try
			{
				AppDataHandler ds=new AppDataHandler();
				//register table...
				RegisterTable[] rt=getData( AllTheData );
				for ( int i=0; i < rt.length; i++ )
				{
					Vector v1=rt[i].getData();
					Vector v2=rt[i].getMethods();
					ds.RegisterTable( rt[i].getData(),rt[i].getName(),rt[i].getMethods(),rt[i].getWhere() );
				}

				if ( "generatecabbinlabels".equalsIgnoreCase( gencase ) && "31".equalsIgnoreCase(papersize))
				{
					RegisterTable[] rt1=getcabData( CabData );
					for ( int i=0; i < rt1.length; i++ )
					{
						Vector v1=rt1[i].getData();
						Vector v2=rt1[i].getMethods();
						ds.RegisterTable( rt1[i].getData(),rt1[i].getName(),rt1[i].getMethods(),rt1[i].getWhere() );
					}
				}
				en.setDataSource( ds );
			}
			catch ( Exception e )
			{
				e.printStackTrace();
				throw e;
			}

			try
			{
				eClient.setReportData( en.generateReport() );
			}
			catch ( Exception ex )
			{
				System.out.println( "\n\n---------ERROR: While generating report" );
				//ex.printStackTrace();
				throw ex;
			}

		}

		try
		{
			eClient.setPDFProperty( "FileName","" + writefilepath + "" + NameofFile + "" );
			eClient.generatePDF();
		}
		catch ( Exception e )
		{
			System.out.println( "\n\n---------ERROR generating report" );
			//e.printStackTrace();
			throw e;
		}
		//reoprtlog.info("buildTest    Done  "+NameofFile.toString()+"");
	}

	public RegisterTable[] getData( Vector reportData1 )  throws Exception
	{
		RegisterTable[] rt=new RegisterTable[1];

		try
		{
			rt[0]=new RegisterTable( cabinetData.getVector( reportData1,14 ),"BARCODE",cabinetData.getFieldVector( 14 ),null );
		}
		catch ( Exception e1 )
		{
			//e1.printStackTrace();
			throw e1;
		}
		return rt;
	}

	public RegisterTable[] getcabData( Vector reportData1 ) throws Exception
	{
		RegisterTable[] rt=new RegisterTable[1];

		try
		{
			rt[0]=new RegisterTable( cabinetData.getVector( reportData1,14 ),"CABINET",cabinetData.getFieldVector( 14 ),null );
		}
		catch ( Exception e1 )
		{
			//e1.printStackTrace();
			throw e1;
		}
		return rt;
	}

}

