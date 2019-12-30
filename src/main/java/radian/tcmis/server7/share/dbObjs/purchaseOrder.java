package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 */

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.DbHelpers;

import java.util.*;
import java.sql.*;


public class purchaseOrder {

    protected TcmISDBModel db;
    private   Vector vMsdsNoList = null;

    public purchaseOrder() {
        vMsdsNoList = new Vector();
    }

    public purchaseOrder(TcmISDBModel db ){
        this.db = db;
        vMsdsNoList = new Vector();
    }

    public void setDB(TcmISDBModel db){
        this.db = db;
    }

    public void setMsdsNoList(Vector vMsdsNo ){
        this.vMsdsNoList = vMsdsNoList;
    }
    public Vector getMsdsNoList( ){
        return this.vMsdsNoList;
    }

    // Nawaz -020/05/02
    public Vector getAllopenOrder(String companyID,
                                  String CatalogDesc,
                                  String SortBy, String Status) throws Exception
    {
        boolean Open_Orders = false;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        Hashtable summary = new Hashtable();

        String sort_order = "";

        String user_query = "select car.request_id,car.qc_status,car.company_id,p.last_name || ', ' || p.first_name full_name, p.phone, p.email,car.catalog_id,to_char(car.qc_date,'dd Month yyyy') qcdate, to_char(car.SUBMIT_DATE,'dd Month yyyy') submitdate ";
        user_query += " from customer.catalog_add_request_new car,personnel p ";
        user_query += "where car.requestor = p.personnel_id  and car.company_id = p.company_id ";

        if ("ALL".equalsIgnoreCase(companyID))
        {
	  user_query += " and ( (car.company_id = 'SEAGATE' and car.request_status = 5) or (car.company_id <> 'SEAGATE' and car.request_status = 6)) ";
        }
	else if ("Seagate".equalsIgnoreCase(companyID))
	{
	  user_query += "and car.company_id = '"+companyID+"' and car.request_status = 5 ";
	}
        else
        {
            user_query += "and car.company_id = '"+companyID+"' and car.request_status = 6 ";
        }

	/*if ("ALL".equalsIgnoreCase(companyID))
        {

        }
	if ("Seagate".equalsIgnoreCase(companyID))
	{
	  user_query += "and car.request_status = 5 ";
	}
	else
	{
	  user_query += "and car.request_status = 6 ";
	}*/

        if ("ALL".equalsIgnoreCase(CatalogDesc))
        {
        }
        else
        {
            user_query += "and car.catalog_id = '"+CatalogDesc+"' ";
        }

        if ("Pending QC".equalsIgnoreCase(Status))
        {
            user_query += "and car.qc_status = 'Pending QC' ";
        }
        else if ("Pending MSDS".equalsIgnoreCase(Status))
        {
            user_query += "and car.qc_status = 'Pending MSDS' ";
        }
        else
        {
            user_query += "and car.qc_status = 'Pending QC' ";
        }


        if ("1".equalsIgnoreCase(SortBy))
        {
          user_query += " order by car.company_id,car.catalog_id,car.SUBMIT_DATE,car.QC_DATE ";
        }
        else
        {
          user_query += " order by car.SUBMIT_DATE,car.QC_DATE,car.company_id,car.catalog_id ";
        }


        /*if ( SearchText.equalsIgnoreCase("NONE"))
        {
            user_query += " from receiving_view where BRANCH_PLANT = " + Branch_id + sort_order;
        }
        else
        {
            user_query += " from receiving_view where BRANCH_PLANT = " + Branch_id + "and lower(RADIAN_PO||ITEM_ID||ITEM_DESCRIPTION||LAST_SUPPLIER) like lower('%" + SearchText + "%')" + sort_order;
        }*/

        int count = 0;
        /*summary.put("TOTAL_NUMBER", new Integer(count) );
        result.addElement(summary);*/

        int num_rec = 0;
        try
        {
            dbrs = db.doQuery(user_query);
            rs=dbrs.getResultSet();

            int k = 0;
            while (rs.next())
            {
                num_rec++;

                Hashtable hD = new Hashtable();
                hD.put("REQUEST_ID",rs.getString("REQUEST_ID")==null?"":rs.getString("REQUEST_ID"));
                hD.put("REQUEST_DATE",rs.getString("submitdate")==null?"":rs.getString("submitdate"));
                hD.put("FULL_NAME",rs.getString("FULL_NAME")==null?"":rs.getString("FULL_NAME"));
                hD.put("FACILITY_NAME",rs.getString("CATALOG_ID")==null?"":rs.getString("CATALOG_ID"));
                hD.put("STATUS",rs.getString("QC_STATUS")==null?"":rs.getString("QC_STATUS"));
                hD.put("PHONE",rs.getString("PHONE")==null?"":rs.getString("PHONE") );
                hD.put("EMAIL",rs.getString("EMAIL")==null?"":rs.getString("EMAIL") );
                hD.put("COMPANY",rs.getString("COMPANY_ID")==null?"":rs.getString("COMPANY_ID") );
                hD.put("QCDATE",rs.getString("qcdate")==null?"":rs.getString("qcdate") );
                hD.put("MATERIAL_ID"," ");

                result.addElement(hD);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = null;
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    //Get Header Information for a particular request Id
    public Hashtable getHeaderData(String requestID)
    {
        Hashtable ResultData = new Hashtable();

        DBResultSet dbrs = null;
        ResultSet rs = null;

        String detailquery = "select car.request_id,car.ENGINEERING_EVALUATION,car.CAT_PART_NO,p.last_name || ', ' || p.first_name full_name, ";
        //String detailquery = "select distinct i.request_id,i.company_id,p.last_name || ', ' || p.first_name full_name, ";
        detailquery += "p.phone, p.email,car.company_id, car.catalog_id, to_char(car.SUBMIT_DATE,'dd Month yyyy') submitdate ";
        detailquery += "from customer.catalog_add_request_new car,personnel p where ";
        //detailquery += "i.request_id = car.request_id and car.requestor = p.personnel_id and ";
        detailquery += " car.requestor = p.personnel_id and car.company_id = p.company_id  and ";
        detailquery += "car.request_id = "+requestID+" ";

        try
        {
            dbrs = db.doQuery(detailquery);
            rs=dbrs.getResultSet();
            //System.out.print("Finished The Header Querry\n ");

            while(rs.next())
            {
                //Request ID
                ResultData.put("REQUEST_ID",BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_ID")));
                //Requestor
                ResultData.put("REQUESTOR",BothHelpObjs.makeSpaceFromNull(rs.getString("FULL_NAME")));
                //Phone
                ResultData.put("REQUESTOR_PHONE",BothHelpObjs.makeSpaceFromNull(rs.getString("PHONE")));
                //email
                ResultData.put("REQUESTOR_EMAIL",BothHelpObjs.makeSpaceFromNull(rs.getString("EMAIL")));
                // COMPANY
                ResultData.put("COMPANY",BothHelpObjs.makeSpaceFromNull(rs.getString("COMPANY_ID")));
                //CATALOGID
                ResultData.put("CATALOGID",BothHelpObjs.makeSpaceFromNull(rs.getString("CATALOG_ID")));
                // DATE SUBMITTED
                ResultData.put("DATE_SUBMITTED",BothHelpObjs.makeSpaceFromNull(rs.getString("SUBMITDATE")));
                // EVALUATION
                ResultData.put("EVALUATION",BothHelpObjs.makeSpaceFromNull(rs.getString("ENGINEERING_EVALUATION")));
		//CAT_PART_NO
                ResultData.put("CAT_PART_NO",BothHelpObjs.makeSpaceFromNull(rs.getString("CAT_PART_NO")));

            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
            return ResultData;
        }
        finally
        {
            dbrs.close();
        }

        return ResultData;
    }

    //Get details that are to be shown for a particular request_id
    public Hashtable getMSDSData(String requestID, String CompanyID)
    {
        Hashtable ResultData = new Hashtable();
        Vector result = new Vector();
        DBResultSet dbrs = null;
	DBResultSet compdbrs = null;
        ResultSet rs = null;
	ResultSet comprs = null;

	Hashtable MaterialData = new Hashtable();
	Hashtable MSDSData = new Hashtable();
	Hashtable CompositionData = new Hashtable();
	Vector compresult = new Vector();

	String msdsdetailquery = "select to_char(m.REVISION_DATE,'dd-MON-yy') REVISION_DATE,to_char(m.REVISION_DATE,'mm/dd/yyyy') REVISION_DATE1, x.*,mat.GROUND_DOT LANDDOT,mat.AIR_DOT AIRDOT,mat.WATER_DOT WATERDOT,mat.ERG,m.* ";
	msdsdetailquery += " from  (select * from global.msds where revision_date = fx_msds_most_recent_date(material_id)) m, ";
	msdsdetailquery += " (select MATERIAL_DESC,MANUFACTURER,MATERIAL_ID,PART_ID,MFG_ID,MFG_TRADE_NAME from customer.catalog_add_item_qc where request_id = "+requestID+") x,global.material mat where x.material_id  = m.material_id (+) ";
	msdsdetailquery += " and x.material_id  = mat.material_id (+) and m.ON_LINE(+) = 'Y' ";

        int totalrecords = 0;
	int comprecords = 0;

	/*try
	{
	  compdbrs = db.doQuery("select xh.* from global.composition xh where material_id = 126759 and revision_date = '01-AUG-01' and rownum <3");
	  comprs=compdbrs.getResultSet();

	  ResultSetMetaData rsMeta1 = comprs.getMetaData();
	  System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
	  /*for(int i =1; i<=rsMeta1.getColumnCount(); i++)
	  {
	  //System.out.print("'\"+"+rsMeta.getColumnName(i).toString().toLowerCase()+"+\"',");
	  //System.out.print("Msgd.append(\"<TD WIDTH=\\\"10%\\\" \"+Color+\"l\\\"><INPUT type=\\\"text\\\" CLASS=\\\"HEADER\\\" name=\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+TabNumber+\"\\\" value=\\\"");
	  //System.out.print("Msgd.append(DataH.get(\""+rsMeta1.getColumnName(i).toString()+"\")");
	  //System.out.println("Msgd.append(\"\\\" SIZE=\\\"5\\\"></TD>\\\n\");");

	  System.out.println("eval(casnumcomp"+i+"  =  \"window.document.ComponentForm\" + (p+1).toString() + \"."+rsMeta1.getColumnName(i).toString().toLowerCase()+"\" +c.toString() +p.toString() ); ");
	  System.out.println("( curvacasnumcomp"+i+" =  (eval(casnumcomp"+i+".toString()).value ) ); ");
	  System.out.println("mainFormvaluecasnum"+i+"  =  eval(\"window.document.MainForm."+rsMeta1.getColumnName(i).toString().toLowerCase()+"\" +c.toString() + p.toString() ); ");
	  System.out.println("mainFormvaluecasnum"+i+".value = curvacasnumcomp"+i+"; ");

	  //System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = \"\"; ");
          //System.out.println("try{ "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = request.getParameter((\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+loop)).toString();}catch (Exception e){ "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = \"\";} ");
          //System.out.println("compositionData.remove(\""+rsMeta1.getColumnName(i).toString()+"\"); ");
          //System.out.println("compositionData.put(\""+rsMeta1.getColumnName(i).toString()+"\", "+rsMeta1.getColumnName(i).toString().toLowerCase()+".trim() ); ");

	  }*/

	  /*for(int i =1; i<=rsMeta1.getColumnCount(); i++)
	  {
	    System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = DataH.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");
	    //System.out.print(""+rsMeta.getColumnName(i).toString()+"='\"+"+rsMeta.getColumnName(i).toString().toLowerCase()+"+\"',");
	    //System.out.println("MsgHE.append(\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+TabNumber+\"\\\" VALUE=\\\"\"+DataH.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString()+\"\\\">xxn\");");
	  }
	  for(int i =1; i<=rsMeta1.getColumnCount(); i++)
	  {
	    //System.out.print(""+rsMeta1.getColumnName(i).toString()+",");
	  }
	  for(int i =1; i<=rsMeta1.getColumnCount(); i++)
	  {
	    //System.out.print("'\"+"+rsMeta1.getColumnName(i).toString().toLowerCase()+"+\"',");

	    System.out.println("Msgtemp.append(\"opener.document.ComponentForm\"+(compn+1)+\"."+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+compn+\"\");  ");
	    System.out.println("Msgtemp.append(\".value = '';\\xxn\");  ");

	  }
	}
	catch (Exception e11)
	{
	    e11.printStackTrace();
	}
	finally
	{
	    compdbrs.close();
	}
*/
        try
        {
            dbrs = db.doQuery(msdsdetailquery);
            rs=dbrs.getResultSet();
            //System.out.print("Finished The Querry\n ");
	    String materialid = "";
	    String revisionDate = "";

	    /*//Get Column Name and put them in a hashtable
	    ResultSetMetaData rsMeta = rs.getMetaData();
	    for(int i =1; i<=rsMeta.getColumnCount(); i++)
	    {
	    //System.out.println(""+rsMeta.getColumnName(i).toString()+"   "+rsMeta.getColumnTypeName(i).toString());
	    //System.out.println("//"+rsMeta.getColumnName(i).toString()+"");
	    //System.out.println("ResultData.put(\""+rsMeta.getColumnName(i).toString()+"\",rs.getString(\""+rsMeta.getColumnName(i).toString()+"\")==null?\"\":rs.getString(\""+rsMeta.getColumnName(i).toString()+"\"));");
	    //System.out.println("MsgHE.append(\"<INPUT TYPE=\\\"hidden\\\" NAME=\\\"cccc\"+TabNumber+\"\\\" VALUE=\\\"\"+DataH.get(\""+rsMeta.getColumnName(i).toString()+"\").toString()+\"\\\">xxn\");");

	    //System.out.println("String grade= \"\";");
            //System.out.println("try{ grade = request.getParameter((\"grade\"+loop)).toString();}catch (Exception e){ grade = \"\";}");
            //System.out.println("msdsData.remove(\""+rsMeta.getColumnName(i).toString()+"\");");
            //System.out.println("msdsData.put(\""+rsMeta.getColumnName(i).toString()+"\", grade.trim() );");

	    //System.out.println("String "+rsMeta.getColumnName(i).toString().toLowerCase()+" = BothHelpObjs.makeBlankFromNull(DataH.get(\""+rsMeta.getColumnName(i).toString()+"\").toString());");
	    }

	    for(int i =1; i<=rsMeta.getColumnCount(); i++)
	    {
	    System.out.print(""+rsMeta.getColumnName(i).toString()+",");
	    //System.out.print(""+rsMeta.getColumnName(i).toString()+"='\"+"+rsMeta.getColumnName(i).toString().toLowerCase()+"+\"',");
	    }
	    for(int i =1; i<=rsMeta.getColumnCount(); i++)
	    {
	    //System.out.print("'\"+"+rsMeta.getColumnName(i).toString().toLowerCase()+"+\"',");

	    System.out.println("Msgtemp.append(\"opener.document.ComponentForm\"+(compn+1)+\"."+rsMeta.getColumnName(i).toString().toLowerCase()+"\"+compn+\"\");  ");
	    System.out.println("Msgtemp.append(\".value = '\"+rs.getString(\""+rsMeta.getColumnName(i).toString()+"\")==null?\"\":rs.getString(\""+rsMeta.getColumnName(i).toString()+"\")+\"';\\xxn\");  ");

	    }*/


            while(rs.next())
            {
            totalrecords +=1;
            ResultData = new Hashtable();

	    MaterialData = new Hashtable();
	    MSDSData = new Hashtable();
	    compresult = new Vector();

	    materialid = "";
	    revisionDate = "";

	    comprecords = 0;

	    //PART_ID
	    ResultData.put("PART_ID",rs.getString("PART_ID")==null?"":rs.getString("PART_ID"));

	    //UPDATE_FLAG
            ResultData.put("UPDATE_FLAG",new Boolean(true));

	    materialid = rs.getString("MATERIAL_ID")==null?"":rs.getString("MATERIAL_ID");
	    revisionDate = rs.getString("REVISION_DATE")==null?"":rs.getString("REVISION_DATE");

	    ResultData.put("MATERIAL_ID",materialid);

	    //Material Data
	    //MATERIAL_DESC
	    MaterialData.put("MATERIAL_DESC",rs.getString("MATERIAL_DESC")==null?"":rs.getString("MATERIAL_DESC"));
	    //MANUFACTURER
	    MaterialData.put("MANUFACTURER",rs.getString("MANUFACTURER")==null?"":rs.getString("MANUFACTURER"));
	    //MATERIAL_ID
	    MaterialData.put("MATERIAL_ID",materialid);
	    //MFG_ID
	    MaterialData.put("MFG_ID",rs.getString("MFG_ID")==null?"":rs.getString("MFG_ID"));
	    //MFG_TRADE_NAME
	    MaterialData.put("MFG_TRADE_NAME",rs.getString("MFG_TRADE_NAME")==null?"":rs.getString("MFG_TRADE_NAME"));
	    //LANDDOT
	    MaterialData.put("LANDDOT",rs.getString("LANDDOT")==null?"":rs.getString("LANDDOT"));
	    //AIRDOT
	    MaterialData.put("AIRDOT",rs.getString("AIRDOT")==null?"":rs.getString("AIRDOT"));
	    //WATERDOT
	    MaterialData.put("WATERDOT",rs.getString("WATERDOT")==null?"":rs.getString("WATERDOT"));
	    //MATERIAL_ID
	    MaterialData.put("MATERIAL_ID",rs.getString("MATERIAL_ID")==null?"":rs.getString("MATERIAL_ID"));
	    //ERG
	    MaterialData.put("ERG",rs.getString("ERG")==null?"":rs.getString("ERG"));

	    //MATERIAL_DATA
	    ResultData.put("MATERIAL_DATA",MaterialData);

	    //MSDS DATA
	    //REVISION_DATE
	    MSDSData.put("REVISION_DATE",rs.getString("REVISION_DATE1")==null?"":rs.getString("REVISION_DATE1"));
	    //CONTENT
	    MSDSData.put("CONTENT",rs.getString("CONTENT")==null?"":rs.getString("CONTENT"));
	    //EMERGENCY_PHONE
	    //MSDSData.put("EMERGENCY_PHONE",rs.getString("EMERGENCY_PHONE")==null?"":rs.getString("EMERGENCY_PHONE"));
	    //SPECIFIC_GRAVITY
	    MSDSData.put("SPECIFIC_GRAVITY",rs.getString("SPECIFIC_GRAVITY")==null?"":rs.getString("SPECIFIC_GRAVITY"));
	    //HEALTH
	    MSDSData.put("HEALTH",rs.getString("HEALTH")==null?"":rs.getString("HEALTH"));
	    //FLAMMABILITY
	    MSDSData.put("FLAMMABILITY",rs.getString("FLAMMABILITY")==null?"":rs.getString("FLAMMABILITY"));
	    //REACTIVITY
	    MSDSData.put("REACTIVITY",rs.getString("REACTIVITY")==null?"":rs.getString("REACTIVITY"));
	    //SPECIFIC_HAZARD
	    MSDSData.put("SPECIFIC_HAZARD",rs.getString("SPECIFIC_HAZARD")==null?"":rs.getString("SPECIFIC_HAZARD"));
	    //COMPATIBILITY
	    //MSDSData.put("COMPATIBILITY",rs.getString("COMPATIBILITY")==null?"":rs.getString("COMPATIBILITY"));
	    //STORAGE_TEMP
	    //MSDSData.put("STORAGE_TEMP",rs.getString("STORAGE_TEMP")==null?"":rs.getString("STORAGE_TEMP"));
	    //PPE
	    //MSDSData.put("PPE",rs.getString("PPE")==null?"":rs.getString("PPE"));
	    //SIGNAL_WORD
	    //MSDSData.put("SIGNAL_WORD",rs.getString("SIGNAL_WORD")==null?"":rs.getString("SIGNAL_WORD"));
	    //TARGET_ORGAN
	    //MSDSData.put("TARGET_ORGAN",rs.getString("TARGET_ORGAN")==null?"":rs.getString("TARGET_ORGAN"));
	    //ROUTE_OF_ENTRY
	    //MSDSData.put("ROUTE_OF_ENTRY",rs.getString("ROUTE_OF_ENTRY")==null?"":rs.getString("ROUTE_OF_ENTRY"));
	    //SKIN
	    //MSDSData.put("SKIN",rs.getString("SKIN")==null?"":rs.getString("SKIN"));
	    //EYES
	    //MSDSData.put("EYES",rs.getString("EYES")==null?"":rs.getString("EYES"));
	    //INHALATION
	    //MSDSData.put("INHALATION",rs.getString("INHALATION")==null?"":rs.getString("INHALATION"));
	    //INJECTION
	    //MSDSData.put("INJECTION",rs.getString("INJECTION")==null?"":rs.getString("INJECTION"));
	    //BOILING_POINT
	    //MSDSData.put("BOILING_POINT",rs.getString("BOILING_POINT")==null?"":rs.getString("BOILING_POINT"));
	    //FLASH_POINT
	    MSDSData.put("FLASH_POINT",rs.getString("FLASH_POINT")==null?"":rs.getString("FLASH_POINT"));
	    //PH
	    //MSDSData.put("PH",rs.getString("PH")==null?"":rs.getString("PH"));
	    //FREEZING_POINT
	    //MSDSData.put("FREEZING_POINT",rs.getString("FREEZING_POINT")==null?"":rs.getString("FREEZING_POINT"));
	    //DENSITY
	    MSDSData.put("DENSITY",rs.getString("DENSITY")==null?"":rs.getString("DENSITY"));
	    //INGESTION
	    //MSDSData.put("INGESTION",rs.getString("INGESTION")==null?"":rs.getString("INGESTION"));
	    //ON_LINE
	    //MSDSData.put("ON_LINE",rs.getString("ON_LINE")==null?"":rs.getString("ON_LINE"));
	    //DENSITY_UNIT
	    MSDSData.put("DENSITY_UNIT",rs.getString("DENSITY_UNIT")==null?"":rs.getString("DENSITY_UNIT"));
	    //PHYSICAL_STATE
	    MSDSData.put("PHYSICAL_STATE",rs.getString("PHYSICAL_STATE")==null?"":rs.getString("PHYSICAL_STATE"));
	    //VOC_UNIT
	    MSDSData.put("VOC_UNIT",rs.getString("VOC_UNIT")==null?"":rs.getString("VOC_UNIT"));
	    //TSCA_12B
	    //MSDSData.put("TSCA_12B",rs.getString("TSCA_12B")==null?"":rs.getString("TSCA_12B"));
	    //SARA_311_312_ACUTE
	    //MSDSData.put("SARA_311_312_ACUTE",rs.getString("SARA_311_312_ACUTE")==null?"":rs.getString("SARA_311_312_ACUTE"));
	    //SARA_311_312_CHRONIC
	    //MSDSData.put("SARA_311_312_CHRONIC",rs.getString("SARA_311_312_CHRONIC")==null?"":rs.getString("SARA_311_312_CHRONIC"));
	    //SARA_311_312_FIRE
	    //MSDSData.put("SARA_311_312_FIRE",rs.getString("SARA_311_312_FIRE")==null?"":rs.getString("SARA_311_312_FIRE"));
	    //SARA_311_312_PRESSURE
	    //MSDSData.put("SARA_311_312_PRESSURE",rs.getString("SARA_311_312_PRESSURE")==null?"":rs.getString("SARA_311_312_PRESSURE"));
	    //SARA_311_312_REACTIVITY
	    //MSDSData.put("SARA_311_312_REACTIVITY",rs.getString("SARA_311_312_REACTIVITY")==null?"":rs.getString("SARA_311_312_REACTIVITY"));
	    //OSHA_HAZARD
	    //MSDSData.put("OSHA_HAZARD",rs.getString("OSHA_HAZARD")==null?"":rs.getString("OSHA_HAZARD"));
	    //TSCA_LIST
	    //MSDSData.put("TSCA_LIST",rs.getString("TSCA_LIST")==null?"":rs.getString("TSCA_LIST"));
	    //MIXTURE
	    //MSDSData.put("MIXTURE",rs.getString("MIXTURE")==null?"":rs.getString("MIXTURE"));
	    //VOC
	    MSDSData.put("VOC",rs.getString("VOC")==null?"":rs.getString("VOC"));
	    //VOC_LOWER
	    MSDSData.put("VOC_LOWER",rs.getString("VOC_LOWER")==null?"":rs.getString("VOC_LOWER"));
	    //VOC_UPPER
	    MSDSData.put("VOC_UPPER",rs.getString("VOC_UPPER")==null?"":rs.getString("VOC_UPPER"));
	    //REVIEWED_BY
	    //MSDSData.put("REVIEWED_BY",rs.getString("REVIEWED_BY")==null?"":rs.getString("REVIEWED_BY"));
	    //REVIEW_DATE
	    //MSDSData.put("REVIEW_DATE",rs.getString("REVIEW_DATE")==null?"":rs.getString("REVIEW_DATE"));
	    //REMARK
	    //MSDSData.put("REMARK",rs.getString("REMARK")==null?"":rs.getString("REMARK"));
	    //FLASH_POINT_UNIT
	    MSDSData.put("FLASH_POINT_UNIT",rs.getString("FLASH_POINT_UNIT")==null?"":rs.getString("FLASH_POINT_UNIT"));
	    //SOLIDS
	    MSDSData.put("SOLIDS",rs.getString("SOLIDS")==null?"":rs.getString("SOLIDS"));
	    //SOLIDS_LOWER
	    MSDSData.put("SOLIDS_LOWER",rs.getString("SOLIDS_LOWER")==null?"":rs.getString("SOLIDS_LOWER"));
	    //SOLIDS_UPPER
	    MSDSData.put("SOLIDS_UPPER",rs.getString("SOLIDS_UPPER")==null?"":rs.getString("SOLIDS_UPPER"));
	    //SOLIDS_UNIT
	    MSDSData.put("SOLIDS_UNIT",rs.getString("SOLIDS_UNIT")==null?"":rs.getString("SOLIDS_UNIT"));
	    //SOLIDS_PERCENT
	    MSDSData.put("SOLIDS_PERCENT",rs.getString("SOLIDS_PERCENT")==null?"":rs.getString("SOLIDS_PERCENT"));
	    //SOLIDS_LOWER_PERCENT
	    MSDSData.put("SOLIDS_LOWER_PERCENT",rs.getString("SOLIDS_LOWER_PERCENT")==null?"":rs.getString("SOLIDS_LOWER_PERCENT"));
	    //SOLIDS_UPPER_PERCENT
	    MSDSData.put("SOLIDS_UPPER_PERCENT",rs.getString("SOLIDS_UPPER_PERCENT")==null?"":rs.getString("SOLIDS_UPPER_PERCENT"));
	    //VOC_LOWER_PERCENT
	    MSDSData.put("VOC_LOWER_PERCENT",rs.getString("VOC_LOWER_PERCENT")==null?"":rs.getString("VOC_LOWER_PERCENT"));
	    //VOC_UPPER_PERCENT
	    MSDSData.put("VOC_UPPER_PERCENT",rs.getString("VOC_UPPER_PERCENT")==null?"":rs.getString("VOC_UPPER_PERCENT"));
	    //VOC_PERCENT
	    MSDSData.put("VOC_PERCENT",rs.getString("VOC_PERCENT")==null?"":rs.getString("VOC_PERCENT"));

	    //MSDS_DATA
	    ResultData.put("MSDS_DATA",MSDSData);

	    //Getting Composition Data
	    if (materialid.length() > 0)
	    {
	      try
	      {
		compdbrs = db.doQuery("select * from global.composition where material_id = "+materialid+" and revision_date = '"+revisionDate+"' order by lower(CAS_NUMBER) ");
		comprs=compdbrs.getResultSet();
		//System.out.println("Finished The Querry\n ");

		/*ResultSetMetaData rsMeta1 = comprs.getMetaData();
		for(int i =1; i<=rsMeta1.getColumnCount(); i++)
		{
		//System.out.print("'\"+"+rsMeta.getColumnName(i).toString().toLowerCase()+"+\"',");
		System.out.print("Msgd.append(\"<TD WIDTH=\\\"10%\\\" COLSPAN=\\\"2\\\" \"+Color+\"l\\\"><INPUT type=\\\"text\\\" CLASS=\\\"HEADER\\\" name=\""+rsMeta1.getColumnName(i).toString().toLowerCase()+"\"+TabNumber+\"\\\" value=\\\"");
		System.out.print("Msgd.append(DataH.get(\""+rsMeta1.getColumnName(i).toString()+"\")");
		System.out.print("Msgd.append(\"\\\" SIZE=\\\"5\\\"></TD>\\\n");
		}*/

		while(comprs.next())
		{
		  comprecords ++;
		  CompositionData = new Hashtable();

		  //PERCENT
		  CompositionData.put("PERCENT",comprs.getString("PERCENT")==null?"":comprs.getString("PERCENT"));
		  //PERCENT_AS_CAS
		  //CompositionData.put("PERCENT_AS_CAS",comprs.getString("PERCENT_AS_CAS")==null?"":comprs.getString("PERCENT_AS_CAS"));
		  //PERCENT_LOWER
		  CompositionData.put("PERCENT_LOWER",comprs.getString("PERCENT_LOWER")==null?"":comprs.getString("PERCENT_LOWER"));
		  //PERCENT_UPPER
		  CompositionData.put("PERCENT_UPPER",comprs.getString("PERCENT_UPPER")==null?"":comprs.getString("PERCENT_UPPER"));
		  //HAZARDOUS
		  CompositionData.put("HAZARDOUS",comprs.getString("HAZARDOUS")==null?"":comprs.getString("HAZARDOUS"));
		  //CAS_NUMBER
		  CompositionData.put("CAS_NUMBER",comprs.getString("CAS_NUMBER")==null?"":comprs.getString("CAS_NUMBER"));
		  //TRADE_SECRET
		  CompositionData.put("TRADE_SECRET",comprs.getString("TRADE_SECRET")==null?"":comprs.getString("TRADE_SECRET"));
		  //REMARK
		  CompositionData.put("REMARK",comprs.getString("REMARK")==null?"":comprs.getString("REMARK"));
		  //MSDS_CHEMICAL_NAME
		  CompositionData.put("MSDS_CHEMICAL_NAME",comprs.getString("MSDS_CHEMICAL_NAME")==null?"":comprs.getString("MSDS_CHEMICAL_NAME"));

		  CompositionData.put("LINE_STATUS","");

		  compresult.addElement(CompositionData);
	      }
	      }
	      catch (Exception e11)
	      {
		  e11.printStackTrace();
	      }
	      finally
	      {
		  compdbrs.close();
	      }

	      //System.out.println("No of Composition Records "+comprecords);
	      for (int rem=comprecords; rem < 25; rem++)
	      {
		  CompositionData = new Hashtable();

		  //PERCENT
		  CompositionData.put("PERCENT","");
		  //PERCENT_LOWER
		  CompositionData.put("PERCENT_LOWER","");
		  //PERCENT_UPPER
		  CompositionData.put("PERCENT_UPPER","");
		  //HAZARDOUS
		  CompositionData.put("HAZARDOUS","");
		  //CAS_NUMBER
		  CompositionData.put("CAS_NUMBER","");
		  //TRADE_SECRET
		  CompositionData.put("TRADE_SECRET","");
		  //REMARK
		  CompositionData.put("REMARK","");
		  //MSDS_CHEMICAL_NAME
		  CompositionData.put("MSDS_CHEMICAL_NAME","");

		  CompositionData.put("LINE_STATUS","");

		  compresult.addElement(CompositionData);
	      }

            }
	    else
	    {
	      for (int rem=0; rem < 25; rem++)
	      {
		  CompositionData = new Hashtable();

		  //PERCENT
		  CompositionData.put("PERCENT","");
		  //PERCENT_LOWER
		  CompositionData.put("PERCENT_LOWER","");
		  //PERCENT_UPPER
		  CompositionData.put("PERCENT_UPPER","");
		  //HAZARDOUS
		  CompositionData.put("HAZARDOUS","");
		  //CAS_NUMBER
		  CompositionData.put("CAS_NUMBER","");
		  //TRADE_SECRET
		  CompositionData.put("TRADE_SECRET","");
		  //REMARK
		  CompositionData.put("REMARK","");
		  //MSDS_CHEMICAL_NAME
		  CompositionData.put("MSDS_CHEMICAL_NAME","");

		  CompositionData.put("LINE_STATUS","");

		  compresult.addElement(CompositionData);
	      }
	    }

	    ResultData.put("COMPOSITION_DATA",compresult);

	    result.addElement(ResultData);
          }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            dbrs.close();
        }

        Hashtable recsum  = new Hashtable();
        recsum.put("TOTAL_NUMBER", new Integer(totalrecords) );
	//ITEM_ID
        recsum.put("ITEM_ID","");
        //PART_DESC
        recsum.put("PART_DESC","");
        recsum.put("DATA",result );
        recsum.put("REQUEST_ID",requestID );

        return recsum;

    }

    //Get details that are to be shown for a particular request_id
    public Hashtable getHashtableData(String requestID, String CompanyID)
    {
        Hashtable ResultData = new Hashtable();

        Vector result = new Vector();

        DBResultSet dbrs = null;
        ResultSet rs = null;

        String detailquery = "select x.*,p.SHELF_LIFE_DAYS MFG_SHELF_LIFE,p.RECERT,p.SIZE_VARIES,p.ITEM_VERIFIED from \n";
        detailquery +="(select cai.item_id,i.company_id,i.REQUEST_ID,i.MATERIAL_DESC,i.MANUFACTURER, \n";
        detailquery +="i.MATERIAL_ID,i.PART_ID,i.PART_SIZE, \n";
        detailquery +="i.SIZE_UNIT,i.PKG_STYLE,i.MFG_CATALOG_ID,i.CASE_QTY,i.DIMENSION, \n";
        detailquery +="i.GRADE,i.MFG_TRADE_NAME,i.NETWT,i.NETWT_UNIT,i.COMPONENTS_PER_ITEM,i.SAMPLE_ONLY, \n";
        detailquery +="i.MFG_SHELF_LIFE_BASIS,i.MFG_STORAGE_TEMP,i.COMMENTS,i.MATERIAL_TYPE, \n";
        detailquery +="i.MFG_ID from customer.catalog_add_item_qc i, customer.catalog_add_request_new car,catalog_add_item cai \n";
        detailquery +="where i.request_id = car.request_id and car.request_id = cai.request_id and cai.line_item = 1 and i.request_id = cai.request_id and i.line_item = cai.line_item) x, \n";
        detailquery +="global.part p \n";
        detailquery +="where \n";
        detailquery +="x.item_id = p.item_id(+) and \n";
        detailquery +="x.material_id = p.material_id(+) and \n";
        detailquery +="x.company_id = '"+CompanyID+"' and \n";
        detailquery +="x.request_id = "+requestID+" order by p.PART_ID asc \n";

        int totalrecords = 0;

        String ItemID = "";

        try
        {
            dbrs = db.doQuery(detailquery);
            rs=dbrs.getResultSet();
            //System.out.print("Finished The Querry\n ");

            while(rs.next())
            {
                totalrecords +=1;

                ResultData = new Hashtable();

                ItemID = BothHelpObjs.makeBlankFromNull(rs.getString("ITEM_ID"));

                ResultData.put("REQUEST_ID",BothHelpObjs.makeBlankFromNull(rs.getString("REQUEST_ID")));
                //MANUFACTURER
                ResultData.put("MANUFACTURER",BothHelpObjs.makeBlankFromNull(rs.getString("MANUFACTURER")));
                //MANUFACTURER_ID
                ResultData.put("MANUFACTURER_ID",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_ID")));
                //GRADE
                ResultData.put("GRADE",BothHelpObjs.makeBlankFromNull(rs.getString("GRADE")));
                //MATERIAL_DESC
                ResultData.put("MATERIAL_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_DESC")));
                //MFG_TRADE_NAME
                ResultData.put("TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_TRADE_NAME")));
                //Material ID
                ResultData.put("MATERIAL_ID",BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID")));
                //MFG_CATALOG_ID
                ResultData.put("MFG_PART_NO",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_CATALOG_ID")));
                //shelf life
                ResultData.put("SHELF_LIFE",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_SHELF_LIFE")));
                //Shelf Life Basis
                ResultData.put("SHELF_LIFE_BASIS",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_SHELF_LIFE_BASIS")));
                //storage temperature
                ResultData.put("STORAGE_TEMP",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_STORAGE_TEMP")));
                //NO OF COMPONENTS
                ResultData.put("NO_OF_COMPONENTS",BothHelpObjs.makeBlankFromNull(rs.getString("COMPONENTS_PER_ITEM")));
                //PART SIZE
                ResultData.put("PART_SIZE",BothHelpObjs.makeBlankFromNull(rs.getString("PART_SIZE")));
                //PART_SIZE_UNIT
                ResultData.put("PART_SIZE_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("SIZE_UNIT")));
                //PACKAGING STYLE
                ResultData.put("PACKAGING_STYLE",BothHelpObjs.makeBlankFromNull(rs.getString("PKG_STYLE")));
                //Dimension
                ResultData.put("DIMENSION",BothHelpObjs.makeBlankFromNull(rs.getString("DIMENSION")));
                //NET WEIGHT
                ResultData.put("NET_WEIGHT",BothHelpObjs.makeBlankFromNull(rs.getString("NETWT")));
                //NET WEIGHT UNIT
                ResultData.put("NET_WEIGHT_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("NETWT_UNIT")));
                //COMMENTS
                ResultData.put("COMMENTS",BothHelpObjs.makeBlankFromNull(rs.getString("COMMENTS")));
                //SAMPLE SIZE
                ResultData.put("SAMPLE_SIZE",BothHelpObjs.makeBlankFromNull(rs.getString("SAMPLE_ONLY")));
                // CATEGORY
                ResultData.put("CATEGORY",BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_TYPE")));
                //NO_OF_CHEMICAL_COMP
                ResultData.put("NO_OF_CHEMICAL_COMP"," ");
                //PART ID
                ResultData.put("PART_ID",BothHelpObjs.makeBlankFromNull(rs.getString("PART_ID")));
                //UPDATE_FLAG
                ResultData.put("UPDATE_FLAG",new Boolean(true));
                //Recerts
                ResultData.put("RECERTS",BothHelpObjs.makeBlankFromNull(rs.getString("RECERT")));
                //Size Varies
                ResultData.put("SIZE_VARIES",BothHelpObjs.makeBlankFromNull(rs.getString("SIZE_VARIES")));

		//ITEM_VERIFIED
		ResultData.put("ITEM_VERIFIED",BothHelpObjs.makeBlankFromNull(rs.getString("ITEM_VERIFIED")));


                result.addElement(ResultData);
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            dbrs.close();
        }

        Hashtable recsum  = new Hashtable();
        recsum.put("TOTAL_NUMBER", new Integer(totalrecords) );
        //ITEM_ID
        recsum.put("ITEM_ID",ItemID);
        //PART_DESC
        recsum.put("PART_DESC","");

        recsum.put("DATA",result );

        recsum.put("REQUEST_ID",requestID );

        return recsum;

    }

    //Get details that are to be shown Original Submitted Data for a particular request_id
    public Hashtable getOrigHashtableData(String requestID, String CompanyID)
    {
        Hashtable ResultData = new Hashtable();

        Vector result = new Vector();

        DBResultSet dbrs = null;
        ResultSet rs = null;

        String detailquery = "select distinct i.REQUEST_ID,i.MATERIAL_DESC,i.MANUFACTURER,i.MATERIAL_ID,i.PART_SIZE,i.SIZE_UNIT,i.PKG_STYLE,i.MFG_CATALOG_ID,i.CASE_QTY,i.DIMENSION, \n";
        detailquery +="i.GRADE,i.MFG_TRADE_NAME,i.NETWT,i.NETWT_UNIT,i.COMPONENTS_PER_ITEM,i.SAMPLE_ONLY \n";
        detailquery +="from \n";
        detailquery +="customer.catalog_add_item i \n";
        detailquery +="where \n";
        //detailquery +="i.company_id = '"+CompanyID+"' and \n";
        detailquery +="i.request_id = "+requestID+" \n";

        int totalrecords = 0;

        try
        {
            dbrs = db.doQuery(detailquery);
            rs=dbrs.getResultSet();
            //System.out.print("Finished The Querry\n ");

            while(rs.next())
            {
                totalrecords +=1;
                ResultData = new Hashtable();
                ResultData.put("REQUEST_ID",BothHelpObjs.makeSpaceFromNull(rs.getString("REQUEST_ID")));
                //MANUFACTURER
                ResultData.put("MANUFACTURER",BothHelpObjs.makeSpaceFromNull(rs.getString("MANUFACTURER")));
                //MANUFACTURER_ID
                //ResultData.put("MANUFACTURER_ID",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_ID")));
                //GRADE
                ResultData.put("GRADE",BothHelpObjs.makeSpaceFromNull(rs.getString("GRADE")));
                //MATERIAL_DESC
                ResultData.put("MATERIAL_DESC",BothHelpObjs.makeSpaceFromNull(rs.getString("MATERIAL_DESC")));
                //MFG_TRADE_NAME
                ResultData.put("TRADE_NAME",BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_TRADE_NAME")));
                //Material ID
                ResultData.put("MATERIAL_ID",BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID")));
                //MFG_CATALOG_ID
                ResultData.put("MFG_PART_NO",BothHelpObjs.makeSpaceFromNull(rs.getString("MFG_CATALOG_ID")));
                //shelf life
                ResultData.put("SHELF_LIFE","");
                //Shelf Life Basis
                ResultData.put("SHELF_LIFE_BASIS","");
                //storage temperature
                ResultData.put("STORAGE_TEMP","");
                //NO OF COMPONENTS
                ResultData.put("NO_OF_COMPONENTS",BothHelpObjs.makeSpaceFromNull(rs.getString("COMPONENTS_PER_ITEM")));
                //PART SIZE
                ResultData.put("PART_SIZE",BothHelpObjs.makeSpaceFromNull(rs.getString("PART_SIZE")));
                //PART_SIZE_UNIT
                ResultData.put("PART_SIZE_UNIT",BothHelpObjs.makeSpaceFromNull(rs.getString("SIZE_UNIT")));
                //PACKAGING STYLE
                ResultData.put("PACKAGING_STYLE",BothHelpObjs.makeSpaceFromNull(rs.getString("PKG_STYLE")));
                //Dimension
                ResultData.put("DIMENSION",BothHelpObjs.makeSpaceFromNull(rs.getString("DIMENSION")));
                //NET WEIGHT
                ResultData.put("NET_WEIGHT",BothHelpObjs.makeSpaceFromNull(rs.getString("NETWT")));
                //NET WEIGHT UNIT
                ResultData.put("NET_WEIGHT_UNIT",BothHelpObjs.makeSpaceFromNull(rs.getString("NETWT_UNIT")));
                //COMMENTS
                //ResultData.put("COMMENTS",BothHelpObjs.makeSpaceFromNull(rs.getString("COMMENTS")));
                //SAMPLE SIZE
                ResultData.put("SAMPLE_SIZE",BothHelpObjs.makeSpaceFromNull(rs.getString("SAMPLE_ONLY")));
                // CATEGORY
                //ResultData.put("CATEGORY",BothHelpObjs.makeSpaceFromNull(rs.getString("MATERIAL_TYPE")));
                //NO_OF_CHEMICAL_COMP
                ResultData.put("NO_OF_CHEMICAL_COMP","");

                result.addElement(ResultData);
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            dbrs.close();
        }

        Hashtable recsum  = new Hashtable();
        recsum.put("TOTAL_NUMBER", new Integer(totalrecords) );
        //ITEM_ID
        recsum.put("ITEM_ID","");
        //PART_DESC
        recsum.put("PART_DESC","");

        recsum.put("DATA",result );

        return recsum;

    }
    public boolean UpdateLine( Hashtable DataH,
                              String loginID, String requestID, String action )
    {
        // get main information
        boolean result = true;

        String mfg_id = DataH.get("MANUFACTURER_ID").toString();
        String manufacturer = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("MANUFACTURER").toString());
        String material_id = DataH.get("MATERIAL_ID").toString();
        String material_desc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("MATERIAL_DESC").toString());
        String grade = DataH.get("GRADE").toString();
        String mfg_shelf_life = DataH.get("SHELF_LIFE").toString();
        String mfg_storage_temp = DataH.get("STORAGE_TEMP").toString();
        String shelf_life_basis = DataH.get("SHELF_LIFE_BASIS").toString();
        String noofcomponents = DataH.get("NO_OF_COMPONENTS").toString();
        String part_size = DataH.get("PART_SIZE").toString();
        String size_units = DataH.get("PART_SIZE_UNIT").toString();
        String pkg_style = DataH.get("PACKAGING_STYLE").toString();
        String dimension = DataH.get("DIMENSION").toString();
        String net_wt = DataH.get("NET_WEIGHT").toString();
        String net_weight_unit = DataH.get("NET_WEIGHT_UNIT").toString();
        String mfg_part_no = DataH.get("MFG_PART_NO").toString();
        String Comments = DataH.get("COMMENTS").toString();
        String material_category = DataH.get("CATEGORY").toString();
        String sample_size = DataH.get("SAMPLE_SIZE").toString();
        String trade_name = DataH.get("TRADE_NAME").toString();

        String partid = DataH.get("PART_ID").toString();

        String query  = "update customer.catalog_add_item_qc set \n";
        query += "MATERIAL_DESC='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"', PART_SIZE='"+part_size+"', \n";
        query +="SIZE_UNIT='"+size_units+"', PKG_STYLE='"+pkg_style+"', MFG_CATALOG_ID='"+mfg_part_no+"', DIMENSION='"+dimension+"', \n";
        query +="GRADE='"+grade+"', MFG_TRADE_NAME='"+trade_name+"', NETWT='"+net_wt+"', NETWT_UNIT='"+net_weight_unit+"', COMPONENTS_PER_ITEM='"+noofcomponents+"', \n";
        query +="SAMPLE_ONLY='"+sample_size+"', MFG_STORAGE_TEMP='"+mfg_storage_temp+"', COMMENTS='"+Comments+"',MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', \n";
        query +="mfg_shelf_life='"+mfg_shelf_life+"', mfg_id='"+mfg_id+"' \n";

        //MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', MATERIAL_TYPE='"+material_category+"',
        query += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+"";

	String catadditemquery  = "update customer.catalog_add_item set \n";
        catadditemquery += "MATERIAL_DESC='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"', PART_SIZE='"+part_size+"', \n";
        catadditemquery +="SIZE_UNIT='"+size_units+"', PKG_STYLE='"+pkg_style+"', MFG_CATALOG_ID='"+mfg_part_no+"', DIMENSION='"+dimension+"', \n";
        catadditemquery +="GRADE='"+grade+"', MFG_TRADE_NAME='"+trade_name+"', NETWT='"+net_wt+"', NETWT_UNIT='"+net_weight_unit+"', COMPONENTS_PER_ITEM='"+noofcomponents+"', \n";
        catadditemquery +="SAMPLE_ONLY='"+sample_size+"' ";

        //MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', MATERIAL_TYPE='"+material_category+"',
        catadditemquery += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+"";

        try
        {
            //System.out.println(query);
            db.doUpdate(query);
	    if ("Process".equalsIgnoreCase(action))
	    {
	      db.doUpdate(catadditemquery);
	    }
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }

        if (partid.equalsIgnoreCase("5"))
        {
            result = false;
        }

        return result;
    }
    //
    public boolean updateMsdsData( Hashtable DataH,
                              String loginID, String requestID , String action)
    {
        // get main information
        boolean result = true;

	/**
	 *
	 * 1. Date format differences when inserting revision date in msds
	 * 2. Adding new columns for HMIS HFRS ratings
	 * 3. updating composition data
	 */
	Hashtable materialData = null;
	Hashtable msdsData = null;
	Hashtable compositionData = null;
	Vector ofCompositionData = null;

	materialData = (Hashtable) DataH.get("MATERIAL_DATA");
	msdsData = (Hashtable) DataH.get("MSDS_DATA");
	ofCompositionData = (Vector) DataH.get("COMPOSITION_DATA");

	String material_desc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("MATERIAL_DESC").toString());
	String manufacturer = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("MANUFACTURER").toString());
	String material_id = materialData.get("MATERIAL_ID").toString();
	String mfg_id = materialData.get("MFG_ID").toString();
	String mfg_trade_name = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("MFG_TRADE_NAME").toString());
	String landdot = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("LANDDOT").toString());
	String airdot = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("AIRDOT").toString());
	String waterdot = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(materialData.get("WATERDOT").toString());
	String erg = materialData.get("ERG").toString();

	String revision_date = msdsData.get("REVISION_DATE").toString();
	String content = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(msdsData.get("CONTENT").toString());
	//String emergency_phone = msdsData.get("EMERGENCY_PHONE").toString();
	String specific_gravity = msdsData.get("SPECIFIC_GRAVITY").toString();
	String health = msdsData.get("HEALTH").toString();
	String flammability = msdsData.get("FLAMMABILITY").toString();
	String reactivity = msdsData.get("REACTIVITY").toString();
	String specific_hazard = msdsData.get("SPECIFIC_HAZARD").toString();
	String flash_point = msdsData.get("FLASH_POINT").toString();
	//String ph = msdsData.get("PH").toString();
	//String freezing_point = msdsData.get("FREEZING_POINT").toString();
	String density = msdsData.get("DENSITY").toString();
	//String ingestion = msdsData.get("INGESTION").toString();
	//String on_line = msdsData.get("ON_LINE").toString();
	String density_unit = msdsData.get("DENSITY_UNIT").toString();
	String physical_state = msdsData.get("PHYSICAL_STATE").toString();
	String voc_unit = msdsData.get("VOC_UNIT").toString();
	String voc = msdsData.get("VOC").toString();
	String voc_lower = msdsData.get("VOC_LOWER").toString();
	String voc_upper = msdsData.get("VOC_UPPER").toString();
	//String reviewed_by = msdsData.get("REVIEWED_BY").toString();
	//String review_date = msdsData.get("REVIEW_DATE").toString();
	//String remark = msdsData.get("REMARK").toString();
	String flash_point_unit = msdsData.get("FLASH_POINT_UNIT").toString();
	String solids = msdsData.get("SOLIDS").toString();
	String solids_lower = msdsData.get("SOLIDS_LOWER").toString();
	String solids_upper = msdsData.get("SOLIDS_UPPER").toString();
	String solids_unit = msdsData.get("SOLIDS_UNIT").toString();

	String partid = DataH.get("PART_ID").toString();

	boolean updateorinsert = false;

	try
        {


          String materialquery = "";
	  String msdsquery  = "";

	  if (material_id.length() > 0)
	  {
	  materialquery  = "update global.material set \n";
	  materialquery += "MATERIAL_DESC='"+material_desc+"', \n";  //MANUFACTURER='"+manufacturer+"',
	  if (mfg_id.length() > 0){materialquery += "MFG_ID="+mfg_id+",";}
	  materialquery += " TRADE_NAME='"+mfg_trade_name+"',GROUND_DOT='"+landdot+"',";
	  if (erg.length() > 0) {materialquery += "ERG="+erg+",";}
	  materialquery += "AIR_DOT='"+airdot+"',WATER_DOT='"+waterdot+"' \n";
	  materialquery += " where MATERIAL_ID = "+material_id+" ";
	  }

	  String catadditemqcupdate  = "update customer.catalog_add_item_qc set \n";
	  catadditemqcupdate += "MATERIAL_DESC='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"',MFG_ID='"+mfg_id+"',MFG_TRADE_NAME='"+mfg_trade_name+"' \n";
	  catadditemqcupdate += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+"";

	  String catadditemupdate  = "update customer.catalog_add_item set \n";
	  catadditemupdate += "MATERIAL_DESC='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"',MFG_TRADE_NAME='"+mfg_trade_name+"' \n";
	  catadditemupdate += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+"";

	   if (revision_date.length() == 10)
	   {
	     int test_number = DbHelpers.countQuery(db,"select count(*) from global.msds where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY')");

	     if (test_number > 0)
	     {
		updateorinsert = true;
	     }

	     if (updateorinsert)
	     {
		msdsquery  = "update global.msds set \n";
		msdsquery += "CONTENT='"+content+"',ON_LINE='Y',SPECIFIC_GRAVITY="+(specific_gravity.length()==0?"null":specific_gravity)+",HEALTH='"+health+"',FLAMMABILITY='"+flammability+"', \n";
		msdsquery += "REACTIVITY='"+reactivity+"',SPECIFIC_HAZARD='"+specific_hazard+"',FLASH_POINT='"+flash_point+"',FLASH_POINT_UNIT='"+flash_point_unit+"',DENSITY="+(density.length()==0?"null":density)+", \n";
		msdsquery += "DENSITY_UNIT='"+density_unit+"',PHYSICAL_STATE='"+physical_state+"',VOC="+(voc.length()==0?"null":voc)+",VOC_LOWER="+(voc_lower.length()==0?"null":voc_lower)+",VOC_UPPER="+(voc_upper.length()==0?"null":voc_upper)+", \n";
		msdsquery += "VOC_UNIT='"+voc_unit+"',SOLIDS="+(solids.length()==0?"null":solids)+",SOLIDS_LOWER="+(solids_lower.length()==0?"null":solids_lower)+",SOLIDS_UPPER="+(solids_upper.length()==0?"null":solids_upper)+",SOLIDS_UNIT='"+solids_unit+"' \n";
		msdsquery += " ,REVIEWED_BY="+loginID+",REVIEW_DATE=sysdate where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY') \n"; //'"+revision_date+"'";

	     }
	     else
	     {
	      msdsquery  = "insert into global.msds (MATERIAL_ID,REVISION_DATE,CONTENT,SPECIFIC_GRAVITY,HEALTH,FLAMMABILITY,REACTIVITY,SPECIFIC_HAZARD,FLASH_POINT,DENSITY,ON_LINE,DENSITY_UNIT, \n";
	      msdsquery += "PHYSICAL_STATE,VOC_UNIT,VOC,VOC_LOWER,VOC_UPPER,FLASH_POINT_UNIT,SOLIDS,SOLIDS_LOWER,SOLIDS_UPPER,SOLIDS_UNIT,REVIEWED_BY,REVIEW_DATE) values \n";
	      msdsquery += "("+material_id+",to_date('"+revision_date+"','MM/DD/YYYY'),'"+content+"',"+(specific_gravity.length()==0?"null":specific_gravity)+",'"+health+"','"+flammability+"','"+reactivity+"','"+specific_hazard+"','"+flash_point+"', \n";
	      msdsquery += " "+(density.length()==0?"null":density)+",'Y','"+density_unit+"','"+physical_state+"','"+voc_unit+"',"+(voc.length()==0?"null":voc)+","+(voc_lower.length()==0?"null":voc_lower)+","+(voc_upper.length()==0?"null":voc_upper)+",'"+flash_point_unit+"', \n";
	      msdsquery += ""+(solids.length()==0?"null":solids)+","+(solids_lower.length()==0?"null":solids_lower)+","+(solids_upper.length()==0?"null":solids_upper)+",'"+solids_unit+"',"+loginID+",sysdate) \n";
	     }
	    }

	    if (material_id.length() > 0)
	    {
	    db.doUpdate(materialquery);
	    }

	    db.doUpdate(catadditemqcupdate);

	    if ("Process".equalsIgnoreCase(action))
	    {
	    db.doUpdate(catadditemupdate);
	    }

	    if (revision_date.length() == 10)
	    {
	      db.doUpdate(msdsquery);
	    }

	    if ( (material_id.length() > 0) && (revision_date.length() == 10) )
	    {
	      db.doUpdate("delete from global.composition where MATERIAL_ID = "+material_id+" and REVISION_DATE=to_date('"+revision_date+"','MM/DD/YYYY') ");

	      //System.out.println("Comp Sizein Update "+ofCompositionData.size());
	      for (int rem=0; rem < 25; rem++)
	      {
	        compositionData = new Hashtable();
		compositionData = (Hashtable) ofCompositionData.elementAt(rem);

		String cas_number = compositionData.get("CAS_NUMBER").toString();

		if (cas_number.length() > 0)
		{
		String percent = compositionData.get("PERCENT").toString();
		String percent_lower = compositionData.get("PERCENT_LOWER").toString();
		String percent_upper = compositionData.get("PERCENT_UPPER").toString();
		String hazardous = compositionData.get("HAZARDOUS").toString();
		String trade_secret = compositionData.get("TRADE_SECRET").toString();
		String remark = compositionData.get("REMARK").toString();
		String msds_chemical_name = compositionData.get("MSDS_CHEMICAL_NAME").toString();

		String updatecomp = "";
		updatecomp = "insert into global.composition (MATERIAL_ID,REVISION_DATE,PERCENT,PERCENT_LOWER,PERCENT_UPPER,HAZARDOUS,CAS_NUMBER,TRADE_SECRET,REMARK,MSDS_CHEMICAL_NAME,CHEMICAL_ID) values \n";
		updatecomp += "("+material_id+",to_date('"+revision_date+"','MM/DD/YYYY'),"+(percent.length()==0?"null":percent)+","+(percent_lower.length()==0?"null":percent_lower)+","+(percent_upper.length()==0?"null":percent_upper)+",'"+hazardous+"','"+cas_number+"','"+trade_secret+"','"+remark+"','"+msds_chemical_name+"','"+msds_chemical_name+"') \n";

		//System.out.println(updatecomp);
		db.doUpdate(updatecomp);
		}
		else
		{
		  continue;
		}
	      }
	    }

            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }

        if (partid.equalsIgnoreCase("5"))
        {
            result = false;
        }

        return result;
    }

    public boolean processrequest( Hashtable DataH,
                                  String loginID,
                                  String requestID,
                                  String itemID, boolean updateorinsert )
    {
        // get main information
        //System.out.println("Enter Method processrequest : "+ DataH);
        boolean result = true;
        PreparedStatement pstmt = null;

	/**
	 * 
	 * 1. Something about shelf life when a -1 is entered in shelf life
	 * 2.column names are different in hawktst and tcmprod
	 * 3.Update item_verifed by and stuff which is newly added to tcmprod
	 */

        String mfg_id = DataH.get("MANUFACTURER_ID").toString();
        String manufacturer = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("MANUFACTURER").toString());
        String material_id = DataH.get("MATERIAL_ID").toString();
        String material_desc = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("MATERIAL_DESC").toString());
        String grade = DataH.get("GRADE").toString();
        String mfg_shelf_life = DataH.get("SHELF_LIFE").toString();
        String mfg_storage_temp = DataH.get("STORAGE_TEMP").toString();
        String shelf_life_basis = DataH.get("SHELF_LIFE_BASIS").toString();
        String noofcomponents = DataH.get("NO_OF_COMPONENTS").toString();
        String part_size = DataH.get("PART_SIZE").toString();
        String size_units = DataH.get("PART_SIZE_UNIT").toString();
        String pkg_style = DataH.get("PACKAGING_STYLE").toString();
        String dimension = DataH.get("DIMENSION").toString();
        String net_wt = DataH.get("NET_WEIGHT").toString();
        String net_weight_unit = DataH.get("NET_WEIGHT_UNIT").toString();
        String mfg_part_no = DataH.get("MFG_PART_NO").toString();
        String Comments = DataH.get("COMMENTS").toString();
        String material_category = DataH.get("CATEGORY").toString();
        String sample_size = DataH.get("SAMPLE_SIZE").toString();
        String trade_name = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(DataH.get("TRADE_NAME").toString());

        String partid = DataH.get("PART_ID").toString();

        String recerts  = DataH.get("RECERTS").toString();
        String sizevaries = DataH.get("SIZE_VARIES").toString();
	String itemverified = DataH.get("ITEM_VERIFIED").toString();

        Connection con = db.getConnection();

        try
        {
            if (updateorinsert)
            {
                //Update
                pstmt = con.prepareStatement("update global.part set PART_ID=?,MATERIAL_ID=?,GRADE=?,PKG_STYLE=?,PART_SIZE=?,SIZE_UNIT=?,SHELF_LIFE_DAYS=?,SHELF_LIFE_BASIS=?,MFG_PART_NO=?,STORAGE_TEMP=?,NET_WT_UNIT=?,NET_WT=?,DIMENSION=?,RECERT=?,COMPONENTS_PER_ITEM=?,SIZE_VARIES=?,STOCKING_TYPE=?,ITEM_VERIFIED_BY=?,DATE_ITEM_VERIFIED=sysdate,ITEM_VERIFIED=? where ITEM_ID = ? and MATERIAL_ID = ?");
                pstmt.setInt(1, Integer.parseInt(partid));
                pstmt.setInt(2, Integer.parseInt(material_id));

                if (grade.trim().length() == 0)
                {
                    pstmt.setNull(3, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(3,grade);
                }

                if ("None".equalsIgnoreCase(pkg_style))
                {
                    pstmt.setNull(4, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(4,pkg_style);
                }

                if (part_size.trim().length() == 0)
                {
                    pstmt.setNull(5, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(5, Integer.parseInt(part_size));
                }

                if ("None".equalsIgnoreCase(size_units))
                {
                    pstmt.setNull(6, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(6,size_units);
                }

                if (mfg_shelf_life.trim().length() == 0)
                {
                    pstmt.setNull(7, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(7, Integer.parseInt(mfg_shelf_life));
                }

                if ("None".equalsIgnoreCase(shelf_life_basis))
                {
                    pstmt.setNull(8, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(8,shelf_life_basis);
                }

                pstmt.setString(9, mfg_part_no);
                pstmt.setString(10, mfg_storage_temp);


                if ("None".equalsIgnoreCase(net_weight_unit))
                {
                    pstmt.setNull(11, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(11,net_weight_unit);
                }

                if (net_wt.trim().length() == 0)
                {
                    pstmt.setNull(12, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(12, Integer.parseInt(net_wt));
                }

                if (dimension.trim().length() == 0)
                {
                    pstmt.setNull(13, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(13,dimension);
                }

                if ("N".equalsIgnoreCase(recerts))
                {
                    pstmt.setNull(14, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(14,recerts);
                }

                if (noofcomponents.trim().length() == 0)
                {
                    pstmt.setNull(15, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(15, Integer.parseInt(noofcomponents));
                }


                if ("N".equalsIgnoreCase(sizevaries))
                {
                    pstmt.setNull(16, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(16,sizevaries);
                }

		pstmt.setString(17,"S");
		pstmt.setString(18,loginID);

		if ("N".equalsIgnoreCase(itemverified))
                {
                    pstmt.setNull(19, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(19,itemverified);
                }

                pstmt.setInt(20, Integer.parseInt(itemID));
                pstmt.setInt(21, Integer.parseInt(material_id));

                pstmt.executeQuery();
            }
            else
            {
                //Update
                pstmt = con.prepareStatement("insert into global.part (PART_ID,MATERIAL_ID,GRADE,PKG_STYLE,PART_SIZE,SIZE_UNIT,SHELF_LIFE_DAYS,SHELF_LIFE_BASIS,MFG_PART_NO,STORAGE_TEMP,NET_WT_UNIT,NET_WT,DIMENSION,RECERT,COMPONENTS_PER_ITEM,SIZE_VARIES,ITEM_ID,STOCKING_TYPE,ITEM_VERIFIED_BY,DATE_ITEM_VERIFIED,ITEM_VERIFIED) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?) " );

		pstmt.setInt(1, Integer.parseInt(partid));
                pstmt.setInt(2, Integer.parseInt(material_id));

                if (grade.trim().length() == 0)
                {
                    pstmt.setNull(3, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(3,grade);
                }

                if ("None".equalsIgnoreCase(pkg_style))
                {
                    pstmt.setNull(4, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(4,pkg_style);
                }

                if (part_size.trim().length() == 0)
                {
                    pstmt.setNull(5, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(5, Integer.parseInt(part_size));
                }

                if ("None".equalsIgnoreCase(size_units))
                {
                    pstmt.setNull(6, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(6,size_units);
                }

                if (mfg_shelf_life.trim().length() == 0)
                {
                    pstmt.setNull(7, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(7, Integer.parseInt(mfg_shelf_life));
                }

                if ("None".equalsIgnoreCase(shelf_life_basis))
                {
                    pstmt.setNull(8, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(8,shelf_life_basis);
                }

                pstmt.setString(9, mfg_part_no);
                pstmt.setString(10, mfg_storage_temp);


                if ("None".equalsIgnoreCase(net_weight_unit))
                {
                    pstmt.setNull(11, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(11,net_weight_unit);
                }

                if (net_wt.trim().length() == 0)
                {
                    pstmt.setNull(12, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(12, Integer.parseInt(net_wt));
                }

                if (dimension.trim().length() == 0)
                {
                    pstmt.setNull(13, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(13,dimension);
                }

                if ("N".equalsIgnoreCase(recerts))
                {
                    pstmt.setNull(14, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(14,recerts);
                }

                if (noofcomponents.trim().length() == 0)
                {
                    pstmt.setNull(15, java.sql.Types.INTEGER);
                }
                else
                {
                    pstmt.setInt(15, Integer.parseInt(noofcomponents));
                }


                if ("N".equalsIgnoreCase(sizevaries))
                {
                    pstmt.setNull(16, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(16,sizevaries);
                }
                pstmt.setInt(17, Integer.parseInt(itemID));
		pstmt.setString(18,"S");

		pstmt.setString(19,loginID);

		if ("N".equalsIgnoreCase(itemverified))
                {
                    pstmt.setNull(20, java.sql.Types.VARCHAR);
                }
                else
                {
                    pstmt.setString(20,itemverified);
                }

                pstmt.executeQuery();
            }

	    /*String query  = "update global.part set \n";
            query += "PART_ID='"+material_desc+"', MANUFACTURER='"+manufacturer+"', MATERIAL_ID='"+material_id+"', PART_SIZE='"+part_size+"', \n";
            query +="SIZE_UNIT='"+size_units+"', PKG_STYLE='"+pkg_style+"', MFG_CATALOG_ID='"+mfg_part_no+"', DIMENSION='"+dimension+"', \n";
            query +="GRADE='"+grade+"', MFG_TRADE_NAME='"+trade_name+"', NETWT='"+net_wt+"', NETWT_UNIT='"+net_weight_unit+"', COMPONENTS_PER_ITEM='"+noofcomponents+"', \n";
            query +="SAMPLE_ONLY='"+sample_size+"', MFG_STORAGE_TEMP='"+mfg_storage_temp+"', COMMENTS='"+Comments+"',MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', \n";
            query +="mfg_shelf_life='"+mfg_shelf_life+"', mfg_id='"+mfg_id+"' \n";

            //MFG_SHELF_LIFE_BASIS='"+shelf_life_basis+"', MATERIAL_TYPE='"+material_category+"',
            query += " where REQUEST_ID = "+requestID+" and PART_ID = "+partid+"";

            System.out.println(query);
            db.doUpdate(query);
            //vLotSeqList.addElement(LOT_UID);*/
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }

        if (partid.equalsIgnoreCase("5"))
        {
            result = false;
        }
        return result;
    }
    //

    public String getRequestStatus(String request_id1,String CompanyId)
    {
        //String query = "select STATUS from catalog_add_item_qc where request_id = "+request_id1+"";
        String query = "select QC_STATUS from customer.catalog_add_request_new where request_id = "+request_id1+"";
        //String query = "select QC_STATUS from customer.catalog_add_request_new where company_id = '"+CompanyId+"' and request_id = "+request_id1+"";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String result = "";

        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                result = BothHelpObjs.makeSpaceFromNull(rs.getString("QC_STATUS"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            dbrs.close();
        }
        return result;
    }

}


//CONTENT='"+content+"',	EMERGENCY_PHONE='"+emergency_phone+"',REVISION_DATE=to_date(" +"'"+revision_date+"'" + "," + "'MM/DD/YYYY'" +"),
	      //COMPATIBILITY='"+compatibility+"',STORAGE_TEMP='"+storage_temp+"',PPE='"+ppe+"',SIGNAL_WORD='"+signal_word+"',TARGET_ORGAN='"+target_organ+"',ROUTE_OF_ENTRY='"+route_of_entry+"',SKIN='"+skin+"',
	      //EYES='"+eyes+"',INHALATION='"+inhalation+"',INJECTION='"+injection+"',BOILING_POINT='"+boiling_point+"',
	      //PH='"+ph+"',FREEZING_POINT='"+freezing_point+"',INGESTION='"+ingestion+"',ON_LINE='"+on_line+"',
	      //TSCA_12B='"+tsca_12b+"',SARA_311_312_ACUTE='"+sara_311_312_acute+"',SARA_311_312_CHRONIC='"+sara_311_312_chronic+"',SARA_311_312_FIRE='"+sara_311_312_fire+"',
	      //SARA_311_312_PRESSURE='"+sara_311_312_pressure+"',SARA_311_312_REACTIVITY='"+sara_311_312_reactivity+"',OSHA_HAZARD='"+osha_hazard+"',
	      //TSCA_LIST='"+tsca_list+"',MIXTURE='"+mixture+"',
	      //REVIEWED_BY='"+reviewed_by+"',REVIEW_DATE='"+review_date+"',REMARK='"+remark+"',FLASH_POINT_UNIT='"+flash_point_unit+"',
	      //SOLIDS_PERCENT='"+solids_percent+"',SOLIDS_LOWER_PERCENT='"+solids_lower_percent+"',
	      //SOLIDS_UPPER_PERCENT='"+solids_upper_percent+"',VOC_LOWER_PERCENT='"+voc_lower_percent+"',
	      //VOC_UPPER_PERCENT='"+voc_upper_percent+"',VOC_PERCENT='"+voc_percent+"',
