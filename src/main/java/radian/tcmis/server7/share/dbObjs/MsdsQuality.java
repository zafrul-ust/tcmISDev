package radian.tcmis.server7.share.dbObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 */

import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import java.util.*;
import java.sql.*;


public class MsdsQuality {

    protected TcmISDBModel db;
    private   Vector vMsdsNoList = null;

    public MsdsQuality() {
        vMsdsNoList = new Vector();
    }

    public MsdsQuality(TcmISDBModel db ){
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

    public Vector getAllopenOrder(String companyID, String CatalogDesc, String SortBy, String Status) throws Exception
    {
        boolean Open_Orders = false;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        Hashtable summary = new Hashtable();

        String user_query = "select distinct i.request_id,i.status,i.company_id,p.last_name || ', ' || p.first_name full_name, p.phone, p.email, car.SUBMIT_DATE, car.catalog_id, i.status_date from ";
        user_query += "customer.catalog_add_item_qc i,customer.catalog_add_request_new car,personnel p Where i.request_id = car.request_id and car.requestor = p.personnel_id and i.company_id = car.company_id and ";

        if ("ALL".equalsIgnoreCase(companyID))
        {
        }
        else
        {
          user_query += "i.company_id = '"+companyID+"' ";
        }

        if ("ALL".equalsIgnoreCase(CatalogDesc))
        {
        }
        else
        {
          user_query += "and car.catalog_id = '"+CatalogDesc+"' ";
        }

        if ("OK".equalsIgnoreCase(Status))
        {
          user_query += "and i.status = 'OK' ";
        }
        else if ("OK No MSDS".equalsIgnoreCase(Status))
        {
          user_query += "and i.status = 'OK No MSDS' ";
        }
        else
        {
          user_query += "and i.status = 'In Process' ";
        }


        if ("1".equalsIgnoreCase(Status))
        {
          user_query += "and car.request_status = 5 order by i.company_id,car.catalog_id,car.SUBMIT_DATE,i.status_date ";
        }
        else
        {
          user_query += "and car.request_status = 5 order by car.SUBMIT_DATE,i.status_date, i.company_id,car.catalog_id ";
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
                //
                Hashtable hD = new Hashtable();
                hD.put("SEAGATE_MSDS_NO",rs.getString("SEAGATE_MSDS_NO")==null?"":rs.getString("SEAGATE_MSDS_NO"));
                hD.put("SEAGATE_TRADE_NAME",rs.getString("SEAGATE_TRADE_NAME")==null?"":rs.getString("SEAGATE_TRADE_NAME"));
                hD.put("SEAGATE_REVISION_DATE",rs.getString("SEAGATE_REVISION_DATE1")==null?"":rs.getString("SEAGATE_REVISION_DATE1"));
                hD.put("SEAGATE_IMAGE_URL",rs.getString("SEAGATE_IMAGE_URL")==null?"":rs.getString("SEAGATE_IMAGE_URL"));
                hD.put("SEAGATE_MFG_NAME",rs.getString("SEAGATE_MFG_NAME")==null?"":rs.getString("SEAGATE_MFG_NAME"));
                hD.put("RADIAN_MFG_ID",rs.getString("RADIAN_MFG_ID")==null?"":rs.getString("RADIAN_MFG_ID"));
                hD.put("RADIAN_MATERIAL_ID",rs.getString("RADIAN_MATERIAL_ID")==null?"":rs.getString("RADIAN_MATERIAL_ID"));
                hD.put("PROCESS_ID",rs.getString("PROCESS_ID")==null?"":rs.getString("PROCESS_ID"));
                hD.put("USERCHK", "" );

                /*hD.put("PURCHASE",rs.getString("RADIAN")==null?"":rs.getString("RADIAN"));
                hD.put("PURCHASE",rs.getString("RADIAN")==null?"":rs.getString("RADIAN"));
                hD.put("PURCHASE",rs.getString("RADIAN")==null?"":rs.getString("RADIAN"));
                hD.put("PURCHASE",rs.getString("RADIAN")==null?"":rs.getString("RADIAN"));
                hD.put("PURCHASE",rs.getString("RADIAN")==null?"":rs.getString("RADIAN"));*/

                result.addElement(hD);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for Get All Open Orders !!!");
            result = null;
            throw e;
        }
        finally
        {
            dbrs.close();
        }

        //System.out.println("Exit Method : "+ "getAllopenOrder()" );

        /*Hashtable recsum  = new Hashtable();
        recsum.put("TOTAL_NUMBER", new Integer(num_rec) );
        result.setElementAt(recsum, 0);*/
        //
        return result;
    }
    //end of method

    public boolean UpdateLine( Hashtable hD, String loginID )
    {
        // get main information
        //System.out.println("Enter Method : "+ "HubReceivingTables.UpdateLine()" );
        boolean result = true;
        String seagate_msds_no = (hD.get("SEAGATE_MSDS_NO")==null?" ":hD.get("SEAGATE_MSDS_NO").toString());
        String material_id     = (hD.get("RADIAN_MATERIAL_ID")==null?" ":hD.get("RADIAN_MATERIAL_ID").toString());
        String mfg_id          = (hD.get("RADIAN_MFG_ID")==null?" ":hD.get("RADIAN_MFG_ID").toString());

        System.out.println("Enter Method : "+ "HubReceivingTables.UpdateLine()" +seagate_msds_no+"  "+material_id+"   "+mfg_id);
        /*String trans_type       = (hD.get("TRANS_TYPE")==null?" ":hD.get("TRANS_TYPE").toString());
        String Mfg_lot          = (hD.get("MFG_LOT")==null?" ":hD.get("MFG_LOT").toString());
        String Date_mfgd        = (hD.get("DATE_MFGD")==null?" ":hD.get("DATE_MFGD").toString());
        String Qty_recd         = (hD.get("QTY_RECD")==null?" ":hD.get("QTY_RECD").toString());
        String Sel_bin          = (hD.get("BIN_NAME")==null?" ":hD.get("BIN_NAME").toString());
        String Sel_status       = (hD.get("STATUS_ID")==null?" ":hD.get("STATUS_ID").toString());
        String xfer_req_id      = (hD.get("XFER_REQ_ID")==null?" ":hD.get("XFER_REQ_ID").toString());

        Vector Orac_procedure = new Vector();

        Vector Orac_procedure1 = new Vector();

        //12-06-01
        String Date_Recieved = (hD.get("DATE_RECIEVED")==null?" ":hD.get("DATE_RECIEVED").toString());
        String Expiry_Date = (hD.get("EXPIRE_DATE")==null?" ":hD.get("EXPIRE_DATE").toString());

        try {
            Integer LOT_UID = getNewUid();

            Orac_procedure.addElement("RADIAN");
            Orac_procedure.addElement(Radian_po);
            Orac_procedure.addElement(PO_line);
            Orac_procedure.addElement(Date_Recieved);
            //Orac_procedure.addElement("to_date(" +"'"+Date_Recieved+"'" + "," + "'MM/DD/YYYY'" +")");
            Orac_procedure.addElement(Qty_recd);
            Orac_procedure.addElement(Sel_bin);
            Orac_procedure.addElement(Mfg_lot);
            Orac_procedure.addElement(Sel_status);
            Orac_procedure.addElement(" ");
            //Orac_procedure.addElement("to_date(" +"' '" + "," + "'MM/DD/YYYY'" +")");
            Orac_procedure.addElement(" ");
            Orac_procedure.addElement(LOT_UID.toString());
            Orac_procedure.addElement(trans_type);

            String query  = "insert into receiving_insert_view (RADIAN_PO,PO_LINE,ITEM_ID,MFG_LOT,BIN,TRANSACTION_DATE,";
            query +="DATE_OF_MANUFACTURE,QUANTITY_RECEIVED,RECEIPT_ID,LOT_STATUS,TRANSFER_REQUEST_ID,DATE_OF_RECEIPT,HUB,RECEIVER_ID,DOC_TYPE,EXPIRE_DATE )";
            query += " VALUES (" +Radian_po+ "," + PO_line  + ", " + Item_id + ",'" + Mfg_lot+ "',upper('" + Sel_bin + "')," + "SYSDATE" + "," ;
            query += " to_date(" +"'"+Date_mfgd+"'" + "," + "'MM/DD/YYYY'" +")" + "," + Qty_recd + "," + LOT_UID.toString() + ",'" + Sel_status + "','" + xfer_req_id + "'," ;
            query += " to_date(" +"'"+Date_Recieved+"'" + "," + "'MM/DD/YYYY'" +"), "+Branch_plant+", '"+loginID+"', '"+trans_type+"', to_date(" +"'"+Expiry_Date+"'" + "," + "'MM/DD/YYYY'" +") )";

            System.out.println("\n\n--------- Receiving Insert query is: \n\n"+query);

            db.doUpdate(query);

            try
            {
            String testting = db.doInvoiceProcedure("P_JDE_RECEIVE_PO_OV",Orac_procedure);
            //System.out.println("\n\n--------- Receiving Insert Procedure Result is: \n\n"+testting);
            }
            catch (Exception e)
            {
            System.out.println("\n\n--------- Erros in JDE Receiving Insert Procedure \n\n");
            e.printStackTrace();
            }

            Orac_procedure1.addElement(LOT_UID.toString());
            Orac_procedure1.addElement("A");
            try
            {
            //String testting1 = db.doInvoiceProcedure("P_DUMMY",Orac_procedure1);
            String testting1 = db.doInvoiceProcedure("P_RECEIPT_ALLOCATE",Orac_procedure1);
            //System.out.println("\n\n--------- Receiving Insert Procedure 1 Result is: \n\n"+testting1);
            }
            catch (Exception e)
            {
            System.out.println("\n\n--------- Erros in Receiving Insert Procedure 1 \n\n");
            e.printStackTrace();
            }

            vMsdsNoList.addElement(LOT_UID);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            HelpObjs.monitor(1, "Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            result = false;
        }
        //System.out.println("Exit Method : "+ "HubReceivingTables.UpdateLine()" );*/
        return result;
    }
    //

   }
