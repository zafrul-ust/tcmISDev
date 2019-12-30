package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;

public class WasteOrder
{
    protected TcmISDBModel db;

    public static final int STRING = 0;
    public static final int DATE = 2;
    public static final int INT = 1;
    public static final int NULLVAL = 3;

    protected int orderNo;
    protected String facilityId;
    protected int requesterId;
    protected String originalSubmitDate;
    protected String resubmitDate;
    protected String cancelDate;
    protected String vendorId;
    protected String storageLocationId;
    protected String poNumber;
    protected String releaseNumber;
    protected String requestDate;

    protected String Shp_id;

    public WasteOrder(TcmISDBModel db)
    {
        this.db = db;
    }
    public WasteOrder()
    {
    }
    public void setDb(TcmISDBModel db)
    {
        this.db = db;
    }

    // get Methods
    public int getOrderNo()
    {
        return orderNo;
    }
    public String getFacilityId()
    {
        return facilityId;
    }
    public int getRequesterId()
    {
        return requesterId;
    }
    public String getOriginalSubmitDate()
    {
        return originalSubmitDate;
    }
    public String getVendorId()
    {
        return vendorId;
    }
    public String getResubmitDate()
    {
        return resubmitDate;
    }
    public String getCancelDate()
    {
        return cancelDate;
    }
    public String getStorageLocationId()
    {
        return storageLocationId;
    }
    public String getPoNumber()
    {
        return poNumber;
    }
    public String getReleaseNumber()
    {
        return releaseNumber;
    }
    public String getRequestDate()
    {
        return requestDate;
    }

    // set Methods
    public void setOrderNo(int s)
    {
        orderNo = s;
    }
    public void setFacilityId(String s)
    {
        facilityId = s;
    }
    public void setRequesterId(int s)
    {
        requesterId = s;
    }
    public void setOriginalSubmitDate(String s)
    {
        originalSubmitDate = s;
    }
    public void setVendorId(String s)
    {
        vendorId = s;
    }
    public void setResubmitDate(String s)
    {
        resubmitDate = s;
    }
    public void setCancelDate(String s)
    {
        cancelDate = s;
    }
    public void setStorageLocationId(String s)
    {
        storageLocationId = s;
    }
    public void setPoNumber(String s)
    {
        poNumber = s;
    }
    public void setReleaseNumber(String s)
    {
        releaseNumber = s;
    }
    public void setRequestDate(String s)
    {
        requestDate = s;
    }


    public Vector getOrderEmailDetail(String orderNo) throws Exception
    {
        String query = "select vendor_profile_id,description,packaging,quantity from waste_order_email_detail_view ";
        query += "where order_no = "+orderNo;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable tmp1 = new Hashtable();
                tmp1.put("VENDOR_PROFILE_ID",
                         BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id")));
                tmp1.put("DESCRIPTION",
                         BothHelpObjs.makeBlankFromNull(rs.getString("description")));
                tmp1.put("PACKAGING",
                         BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
                tmp1.put("QUANTITY",
                         BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
                result.addElement(tmp1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    /*
   public Hashtable submitRequest(String action) throws Exception {
    //send email to vendor and update submit date
   }   */

    public void delete() throws Exception
    {

    }

    //4-28-01
    public static Vector getEmailHeader(TcmISDBModel db,int orderNo) throws Exception {
        String query = "select full_name,phone,email,vendor_facility_id,storage_location,shipment_id,order_notification_url"+
                       " from waste_order_email_header_view where order_no = "+orderNo+
                       " and cancel_date is null order by shipment_id";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while (rs.next()) {
            Hashtable h = new Hashtable();
            h.put("FULL_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("full_name")));
            h.put("PHONE",BothHelpObjs.makeBlankFromNull(rs.getString("phone")));
            h.put("EMAIL",BothHelpObjs.makeBlankFromNull(rs.getString("email")));
            h.put("VENDOR_FAC_ID",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_facility_id")));
            h.put("STORAGE_LOCATION",BothHelpObjs.makeBlankFromNull(rs.getString("storage_location")));
            h.put("SHIPMENT_ID",BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id")));
            h.put("ORDER_NOTIFICATION_URL",BothHelpObjs.makeBlankFromNull(rs.getString("order_notification_url")));
            result.addElement(h);
          }
        }
        catch (Exception e){
          e.printStackTrace();
          throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }


    public void load()throws Exception
    {
        String query = "select * from waste_order where order_no = '"+getOrderNo()+"'";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int myCount = 0;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                setRequesterId(BothHelpObjs.makeZeroFromNull(rs.getString("requester_id")));
                setOriginalSubmitDate(BothHelpObjs.makeBlankFromNull(rs.getString("original_submit_date")));
                setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
                setVendorId(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id")));
                setResubmitDate(BothHelpObjs.makeBlankFromNull(rs.getString("resubmit_date")));
                setStorageLocationId(BothHelpObjs.makeBlankFromNull(rs.getString("storage_location_id")));
                setPoNumber(BothHelpObjs.makeBlankFromNull(rs.getString("po_number")));
                setReleaseNumber(BothHelpObjs.makeBlankFromNull(rs.getString("release_number")));
                setRequestDate(BothHelpObjs.makeBlankFromNull(rs.getString("request_date")));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); throw e;
        }
        finally
        {
            dbrs.close();
        }
    }

    public static WasteOrder insertWasteOrder(TcmISDBModel db,
                                                 int userId,
                                                 Hashtable orderInfo)throws Exception
    {
        try
        {
            int next = DbHelpers.getNextVal(db,"waste_order_seq");
            String query = "insert into waste_order (order_no,requester_id,facility_id,vendor_id,storage_location_id,request_date)";
            query += " values ("+next+","+userId+",'"+(String)orderInfo.get("FACILITY_ID")+"','"+(String)orderInfo.get("VENDOR_ID")+"','"+(String)orderInfo.get("STORAGE_ID")+"',SYSDATE)";
            db.doUpdate(query);
            WasteOrder wo = new WasteOrder(db);
            wo.setOrderNo(next);
            wo.load();
            return wo;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        }
    }

    public static WasteOrder createWasteOrder(TcmISDBModel db,
                                              int userId,
                                              Vector orderReqV,
                                              Hashtable orderInfo) throws Exception
    {
        WasteOrder wo = null;
        WasteShipment ws = null;
        boolean newShipment = true;
        try
        {
            wo = WasteOrder.insertWasteOrder(db,userId,orderInfo);
            String facilityId = (String)orderInfo.get("FACILITY_ID");
            for(int i=0;i<orderReqV.size();i++)
            {
                Hashtable h = (Hashtable)orderReqV.elementAt(i);
                String containerType = ((String)h.get("CONTAINER_TYPE"));
                String cId = ((String)h.get("CONTAINER_ID"));

                if (containerType.equalsIgnoreCase("B")){
                  WasteShipment wsb = null;
                  wsb = WasteShipment.insertWasteShipment(db,wo.getOrderNo());
                  WasteBulkOrder.insertWasteBulkOrder(db,wo.getOrderNo(),cId,wsb.getWasteShipmentId(),facilityId);
                }else if (containerType.equalsIgnoreCase("C")) {
                  if (newShipment)  {
                    ws = WasteShipment.insertWasteShipment(db,wo.getOrderNo());
                    newShipment = false;
                  }
                  Integer containerId = new Integer(cId);
                  WasteContainer wc = new WasteContainer(db);
                  wc.setContainerId(containerId.intValue());
                  Integer tmp = new Integer(wo.getOrderNo());
                  String oi = new String(tmp.toString());
                  wc.insert("order_no",oi,WasteContainer.INT);
                  tmp = new Integer(ws.getWasteShipmentId());
                  String si = new String(tmp.toString());
                  wc.insert("shipment_id",si,WasteContainer.INT);
                }else if (containerType.equalsIgnoreCase("L")) {     //order with lab request
                  if (newShipment) {
                    ws = WasteShipment.insertWasteShipment(db,wo.getOrderNo());
                    newShipment = false;
                  }
                  ws.insert("lab_pack_drum_estimate",cId,WasteShipment.INT);          //note cId -> number of estimated drums
                  String preferredDate = (String)h.get("LAB_PACK_SERVICE_DATE");
                  ws.insert("lab_pack_preferred_service_dat",preferredDate,WasteShipment.DATE);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        }
        return wo;
    }

    public void deleteBulkOrderNo(Vector deleteBulkShipIdV) throws Exception
    {
        String query = "delete from bulk_order where shipment_id in (";
        String tmp = "";
        for (int i = 0; i < deleteBulkShipIdV.size(); i++)
        {
            tmp += deleteBulkShipIdV.elementAt(i).toString() + ",";
        }
        //removing last commas
        tmp = tmp.substring(0,tmp.length()-1);
        query += tmp + ")";

        try
        {
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        }


    }

    public void updateBulkOrderNo(Vector shipIdV) throws Exception
    {
        String query = "update bulk_order set manifest_line_letter = null where shipment_id in (";
        String tmp = "";
        for (int i = 0; i < shipIdV.size(); i++)
        {
            tmp += shipIdV.elementAt(i).toString() + ",";
        }
        //removing last commas
        tmp = tmp.substring(0,tmp.length()-1);
        query += tmp + ")";

        try
        {
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        }
    }

    public Vector getAllShipmentIdForOrder(int orderNo) throws Exception
    {
        String query = "select shipment_id from waste_container where order_no = "+orderNo;
        query += " union";
        query += " select shipment_id from bulk_order where order_no = "+orderNo;
        Vector result = new Vector();
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int myCount = 0;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                result.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id")));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    public void deleteManifestLineItem(Vector shipV) throws Exception
    {
        String query = "delete from manifest_line_item where shipment_id in (";
        String tmp = "";
        for (int i = 0; i < shipV.size(); i++)
        {
            String s = (String)shipV.elementAt(i);
            tmp += s + ",";
        }
        //removing the last commas
        tmp = tmp.substring(0,tmp.length()-1);
        query += tmp + ")";
        try
        {
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
        }
    }

    public void updateOrderRequest(Vector updateV,
                                   int orderNo,
                                   String containerShipmentId) throws Exception
    {
        boolean newShipment = true;
        boolean hasBulk = false;
        boolean cancelContainerShipment = true;
        WasteShipment ws = new WasteShipment(db);
        Hashtable existingBulkId = new Hashtable();
        Vector bulkReusedShipId = new Vector();
        Vector cancelBulkShipId = new Vector();
        try
        {
            //set all container with current order no to null
            Vector shipV = getAllShipmentIdForOrder(orderNo);
            //Integer currentContainerShipmentId = getContainerShipmentId(orderNo);
            Integer currentContainerShipmentId = new Integer(containerShipmentId);        //4-23-01
            Vector bulkShipIdV = new Vector();
            //System.out.println("\n------ got here "+shipV);
            for (int j = 0; j < shipV.size(); j++)
            {
                //System.out.println("\n------ suppose to be here");
                String tmp = shipV.elementAt(j).toString();
                if (!tmp.equals(currentContainerShipmentId.toString()))
                {
                    bulkShipIdV.addElement(tmp);
                }
            }
            //System.out.println("\n\n@@@@@@@@@@@@@@@@@: "+bulkShipIdV);
            if (bulkShipIdV.size() > 0)
            {
                existingBulkId = getBulkIdForShipments(bulkShipIdV);
                hasBulk = true;
            }
            //System.out.println("\n------ got here 222222222222");
            deleteContainerOrderNo(orderNo);
            //System.out.println("\n------ got here 333333333333333");
            //date each container with order no
            for(int i = 0; i < updateV.size(); i++)
            {
                Hashtable h = new Hashtable();
                h = (Hashtable)updateV.elementAt(i);
                String containerType = (String)h.get("CONTAINER_TYPE");
                if (containerType.equalsIgnoreCase("C"))
                {
                    if (newShipment
                            && currentContainerShipmentId.intValue() == 0)
                    {
                        //System.out.println("\n------ got here 44444444444");
                        ws = WasteShipment.insertWasteShipment(db,orderNo);
                        newShipment = false;
                        currentContainerShipmentId = new Integer(ws.getWasteShipmentId());
                    }
                    else
                    {
                        //System.out.println("\n------ got here 555555555");
                        ws.setWasteShipmentId(currentContainerShipmentId.intValue());
                    }
                    Integer order = new Integer(orderNo);
                    String cId = (String)h.get("CONTAINER_ID");
                    Integer containerId = new Integer(cId);
                    WasteContainer wc = new WasteContainer(db);
                    wc.setContainerId(containerId.intValue());
                    wc.insert("order_no",order.toString(),WasteContainer.INT);
                    Integer tmp = new Integer(ws.getWasteShipmentId());
                    String si = new String(tmp.toString());
                    wc.insert("shipment_id",si,WasteContainer.INT);
                    cancelContainerShipment = false;
                    //System.out.println("\n------ got here 6666666666666");
                }
                else
                {
                    String bulkId = (String)h.get("CONTAINER_ID");
                    String facilityId = (String)h.get("FACILITY_ID");
                    if (existingBulkId.containsKey(bulkId))
                    {
                        Integer shipID = new Integer(existingBulkId.get(bulkId).toString());
                        ws.setWasteShipmentId(shipID.intValue());
                        bulkReusedShipId.addElement(shipID);
                        //System.out.println("\n------ bulk reuse: "+bulkReusedShipId);
                    }
                    else
                    {
                        ws = WasteShipment.insertWasteShipment(db,orderNo);
                        WasteBulkOrder.insertWasteBulkOrder(db,
                                                            orderNo,
                                                            bulkId,
                                                            ws.getWasteShipmentId(),facilityId);
                    }
                }
            }

            if (bulkShipIdV.size() > bulkReusedShipId.size())
            {
                for (int k = 0; k < bulkShipIdV.size(); k++)
                {
                    boolean exist = false;
                    String tmpId = bulkShipIdV.elementAt(k).toString();
                    for (int m = 0; m < bulkReusedShipId.size(); m++)
                    {
                        String tmp2 = bulkReusedShipId.elementAt(m).toString();
                        if (tmp2.equals(tmpId))
                        {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist)
                    {
                        cancelBulkShipId.addElement(tmpId);
                    }
                }
            }

            if (cancelBulkShipId.size() > 0)
            {
                //remove all bulk shipment that was taken off the order
                deleteBulkOrderNo(cancelBulkShipId);
                for (int j = 0; j < cancelBulkShipId.size(); j++)
                {
                    //System.out.println("\n------ got here 777777776666666");
                    Integer myShipId = new Integer(cancelBulkShipId.elementAt(j).toString());
                    ws.setWasteShipmentId(myShipId.intValue());
                    ws.insert("cancel_date","nowDate",WasteShipment.DATE);
                }
            }

            if (shipV.size() > 0 )
            {
                updateBulkOrderNo(shipV);
                deleteManifestLineItem(shipV);
            }

            if (cancelContainerShipment
                    && currentContainerShipmentId.intValue() > 0)
            {
                //System.out.println("\n------ got here 777777777777");
                ws.setWasteShipmentId(currentContainerShipmentId.intValue());
                ws.insert("cancel_date","nowDate",WasteShipment.DATE);
            }

            //setting manifest line letter for these shipments
            WasteManifest.setManifestLine(db,orderNo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public Hashtable getBulkIdForShipments(Vector bulkShipIdV) throws Exception
    {
        Hashtable result = new Hashtable();
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String query = "select bulk_id,shipment_id from bulk_order where shipment_id in ";
        String tmp = "(";
        for (int i = 0; i < bulkShipIdV.size(); i++)
        {
            tmp += bulkShipIdV.elementAt(i).toString() +",";
        }
        //removing last commas
        tmp = tmp.substring(0,tmp.length()-1);

        query += tmp + ")";

        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                String ship = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
                String bulk = BothHelpObjs.makeBlankFromNull(rs.getString("bulk_id"));
                result.put(bulk,ship);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

  public Integer getContainerShipmentId(int orNo) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int result = 0;
    String query = "select s.shipment_id from waste_shipment s, bulk_order b";
    query += " where s.shipment_id = b.shipment_id (+) and b.shipment_id is null and s.order_no = "+orNo;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        result = BothHelpObjs.makeZeroFromNull(rs.getString("shipment_id"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
    return (new Integer(result));
  }

    public void deleteContainerOrderNo(int orderNo) throws Exception
    {
        String query = "update waste_container set order_no = null, shipment_id = null, manifest_line_letter = null where order_no = "+orderNo;
        try
        {
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace(); HelpObjs.monitor(1,
                                                  "Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
        }
        finally
        {
        }
    }


    public void insert(String col,String val,int type)  throws Exception
    {
        if(col.equalsIgnoreCase("NOTES"))
        {
            val = HelpObjs.singleQuoteToDouble(val);
        }

        Integer I;
        DBResultSet dbrs = null;
        ResultSet rs = null;

        String query = new String("update waste_order set " + col + "=");
        switch (type)
        {
        case INT:
            I = new Integer(val);
            query = query + I.intValue();
            break;
        case DATE:
            if (val.equals("nowDate"))
            {
                query = query + " SYSDATE";
            }
            else
            {
                query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
            }
            break;
        case STRING:
            query = query + "'" + val + "'";
            break;
        case NULLVAL:
            query = query + null;
            break;
        default:
            query = query + "'" + val + "'";
            break;
        }
        query += " where order_no = " + getOrderNo();
        //System.out.println("&&&&&&&&&&&&&&&& " + query);
        try
        {
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace(); HelpObjs.monitor(1,
                                                  "Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
        }
        finally
        {
        }
    }


    public String getNowDate()  throws Exception
    {
        String query = "select to_char(sysdate,'MM/dd/yyyy') from dual";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String next = new String("");
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                next = rs.getString(1);
                break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(); HelpObjs.monitor(1,
                                                  "Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
        }
        finally
        {
            dbrs.close();
        }
        return next;
    }

    //Changes Made by Nawaz
    public Hashtable getshpidDetail(String orderNo,
                                    String shp_id) throws Exception
    {
        String query = "";

        /*if (type.equalsIgnoreCase("pickup"))
        {
            query = "select vendor_profile_id,description,packaging,quantity from waste_order_email_detail_view ";
            query += "where order_no = "+orderNo+" and shipment_id = "+shp_id+"";
        }
        else if (type.equalsIgnoreCase("material"))
        {
            query = "select vendor_part_no,description,packaging,quantity from waste_order_item_view ";
            query += "where order_no = "+orderNo+" and shipment_id = "+shp_id+"";
        }*/

        query = "select vendor_profile_id,description,packaging,quantity from waste_order_email_detail_view ";
        query += "where order_no = "+orderNo+" and shipment_id = "+shp_id+"";

        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        Vector result1 = new Vector();
        Hashtable ResultD = new Hashtable();
        int numResc = 0;
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable tmp1 = new Hashtable();
                tmp1.put("VENDOR_PROFILE_ID",
                         rs.getString("VENDOR_PROFILE_ID")==null?"":rs.getString("VENDOR_PROFILE_ID"));
                tmp1.put("DESCRIPTION",
                         rs.getString("DESCRIPTION")==null?"":rs.getString("DESCRIPTION"));
                tmp1.put("PACKAGING",
                         rs.getString("PACKAGING")==null?"":rs.getString("PACKAGING"));
                tmp1.put("QUANTITY",
                         rs.getString("QUANTITY")==null?"&nbsp;":rs.getString("QUANTITY"));
                // tmp1.put("SHIPMENT_ID",rs.getString("SHIPMENT_ID")==null?"":rs.getString("SHIPMENT_ID"));
                result.addElement(tmp1);
                numResc += 1;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for shipid detail !!!");
            throw e;
        }
        finally
        {
            dbrs.close();
        }

        ResultD.put("Pickup",result);
        ResultD.put("Number",""+numResc+"");
        return ResultD;
    }

    public Hashtable materialdetails(String orderNo,
                                     String shp_id)
    {

        String query = "";
        query = "select vendor_part_no,description,packaging,quantity from waste_order_item_view ";
        query += "where order_no = "+orderNo+" and shipment_id = "+shp_id+"";
        //result1 = materialdetails(query);

        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        Hashtable ResultD = new Hashtable();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable tmp1 = new Hashtable();
                tmp1.put("VENDOR_PROFILE_ID",
                         rs.getString("VENDOR_PART_NO")==null?"":rs.getString("VENDOR_PART_NO"));
                tmp1.put("DESCRIPTION",
                         rs.getString("DESCRIPTION")==null?"":rs.getString("DESCRIPTION"));
                tmp1.put("PACKAGING",
                         rs.getString("PACKAGING")==null?"":rs.getString("PACKAGING"));
                tmp1.put("QUANTITY",
                         rs.getString("QUANTITY")==null?"&nbsp;":rs.getString("QUANTITY"));
                // tmp1.put("SHIPMENT_ID",rs.getString("SHIPMENT_ID")==null?"":rs.getString("SHIPMENT_ID"));
                result.addElement(tmp1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for shipid detail !!!");
        }
        finally
        {
            dbrs.close();
        }
        ResultD.put("Material",result);
        return ResultD;
    }

    public void insVendorshpId(TcmISDBModel db,
                               String vendor_shipment_id,
                               String planned_shipment_date,
                               String order_no, String Shp_id) throws Exception
    {

        String query = "update waste_shipment set VENDOR_SHIPMENT_ID ='"+vendor_shipment_id+"', PLANNED_SHIP_DATE = to_date('"+planned_shipment_date+"', 'mm/dd/yyyy')";
        query +=" where order_no =" +order_no+" and shipment_id =" +Shp_id+"";
        try
        {
            db.doUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for shipid Update !!!");
            throw e;
        }
        finally
        {
        }
        //System.out.println("!!!!!! Done Doing Shi_Id Update !!!");
    }

    public Vector getorderDetail(String Vendor_Id, String open_order,
                                 String old_order,
                                 String order_text) throws Exception
    {
        String orderNo = "";
        Hashtable resultH = new Hashtable();
        Hashtable resultH1 = new Hashtable();
        Vector order_nosv = new Vector ();
        int K = 0;

        //for (int i=0;i<order_nos.size(); i++)
        {
            //orderNo = order_nos.elementAt(i).toString();
            K = 0;
            String query = " Select  ORDER_NO,shipment_id,vendor_shipment_id,planned_ship_date,preferred_service_date, "+
                "actual_ship_date,cancel_date,BULK_CONTAINER from waste_order_web_view where VENDOR_ID = '"+Vendor_Id+"' "+
                "and ORIGINAL_SUBMIT_DATE is not null ";
                /*" ("+
                " exists "+
                " (select rowid from bulk_order "+
                " where waste_shipment.shipment_id=bulk_order.shipment_id and rownum=1) "+
                " or"+
                " exists "+
                " (select rowid from waste_container where"+
                " waste_shipment.shipment_id=waste_container.shipment_id and rownum=1 )"+
                " )";   //and ORDER_NO="+orderNo+" order by shipment_id";

                String query = " Select  ORDER_NO,shipment_id,vendor_shipment_id,to_char(planned_ship_date,'mm/dd/yyyy')"+
                " planned_ship_date,to_char(preferred_service_date,'mm/dd/yyyy') preferred_service_date, "+
                " to_char(actual_ship_date,'mm/dd/yyyy') actual_ship_date, "+
                " to_char(cancel_date,'mm/dd/yyyy') cancel_date from waste_shipment where "+
                " ("+
                " exists "+
                " (select rowid from bulk_order "+
                " where waste_shipment.shipment_id=bulk_order.shipment_id and rownum=1) "+
                " or"+
                " exists "+
                " (select rowid from waste_container where"+
                " waste_shipment.shipment_id=waste_container.shipment_id and rownum=1 )"+
                " )";   //and ORDER_NO="+orderNo+" order by shipment_id";and ORDER_NO="+orderNo+" */

            if ((open_order.equalsIgnoreCase("openorders"))



                    && (old_order.equalsIgnoreCase("oldorders")))
            {
                query +="and  (DAYS_SINCE_SHIPMENT < "+order_text+") order by order_no,shipment_id"; //VENDOR_ID= '"+Vendor_id+"'
            }
            else if (open_order.equalsIgnoreCase("openorders"))
            {
                query +="and actual_ship_date is null and CANCEL_DATE is null order by order_no,shipment_id"; //and VENDOR_ID= '"+Vendor_id+"'
            }
            else if (old_order.equalsIgnoreCase("oldorders"))
            {
                query +="and ((actual_ship_date is not null or CANCEL_DATE is not null) and DAYS_SINCE_SHIPMENT < "+order_text+") order by order_no,shipment_id"; //VENDOR_ID= '"+Vendor_id+"'
            }
            else
            {
                query +="and actual_ship_date is null and CANCEL_DATE is null order by order_no,shipment_id";
            }

            String query3 = ""; //"select bulk_or_container from waste_order_view where ORDER_NO="+orderNo+" order by shipment_id";

            DBResultSet dbrs = null;
            DBResultSet dbrs3 = null;

            ResultSet rs = null;
            ResultSet rs3 = null;


            Vector result = new Vector();
            Vector result1 = new Vector();
            Hashtable tmp2 = new Hashtable();

            try
            {
                dbrs = db.doQuery(query);
                rs=dbrs.getResultSet();
                //System.out.println("*******************        967  " + rs.getFetchSize());
                while (rs.next())
                {
                    Hashtable tmp1 = new Hashtable();
                    tmp1.put("SHIPMENT_ID",
                             rs.getString("SHIPMENT_ID")==null?"":rs.getString("SHIPMENT_ID"));
                    tmp1.put("VENDOR_SHIPMENT_ID",
                             rs.getString("VENDOR_SHIPMENT_ID")==null?"":rs.getString("VENDOR_SHIPMENT_ID"));
                    tmp1.put("PLANNED_SHIP_DATE",
                             rs.getString("PLANNED_SHIP_DATE")==null?"":rs.getString("PLANNED_SHIP_DATE"));
                    tmp1.put("ACTUAL_SHIP_DATE",
                             rs.getString("ACTUAL_SHIP_DATE")==null?"&nbsp;":rs.getString("ACTUAL_SHIP_DATE"));
                    tmp1.put("CANCEL_DATE",
                             rs.getString("CANCEL_DATE")==null?"&nbsp;":rs.getString("CANCEL_DATE"));
                    tmp1.put("PREFERRED_SERVICE_DATE",
                             rs.getString("PREFERRED_SERVICE_DATE")==null?"&nbsp;":rs.getString("PREFERRED_SERVICE_DATE"));
                    tmp1.put("ORDER_NO",
                             rs.getString("ORDER_NO")==null?"&nbsp;":rs.getString("ORDER_NO"));
                    tmp1.put("BULK_CONT",
                             rs.getString("BULK_CONTAINER")==null?"&nbsp;":rs.getString("BULK_CONTAINER"));
                    result.addElement(tmp1);
                    resultH1.put("SHIP_DETAILS_"+K+"",result);
                    K =  K + 1;
                }
                //System.out.println("*******************        1002  "+K);
                if (K == 0)
                {
                    //System.out.println("*******************   1008");
                }
                else
                {
                    resultH.put("SHIP_DETAILS",resultH1);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                dbrs.close();
            }
            if (!(K == 0))
            {
                //System.out.println("*******************  1023");
                order_nosv.addElement(resultH);
            }

        }
        return order_nosv;
    }

    public String Bulk_or_Container(String order_no,
                                    String ship_id)  throws Exception
    {
        String wherec = "select BULK_CONTAINER from waste_order_web_view where order_no="+order_no+" and shipment_id= "+Shp_id +"";
        String ret = "";
        DBResultSet dbrs = null;


        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(wherec);
            rs=dbrs.getResultSet();
            //System.out.println("*******************        967  " + rs.getFetchSize());
            while (rs.next())
            {
                ret = rs.getString("BULK_CONTAINER");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return ret;
    }


    public Vector getAllopenOrder(String Vendor_id,
                                  String open_order,
                                  String old_order,
                                  String order_text)  throws Exception
    {
        String query = "";
        //Vendor_id = "MAD980523203";
        if (open_order.equalsIgnoreCase("openorders"))
        {
            query = "select distinct order_no,vendor_name from waste_order_view where order_no";
            query +=" is not null and actual_ship_date is null and VENDOR_ID= '"+Vendor_id+"' order by order_no";

        }
        if (old_order.equalsIgnoreCase("oldorders"))
        {
            query = "select distinct order_no,vendor_name from waste_order_view where order_no";
            query +=" is not null and actual_ship_date > sysdate - "+order_text+" and VENDOR_ID= '"+Vendor_id+"' order by order_no";
        }
        if ((old_order.equalsIgnoreCase("oldorders"))
                && (open_order.equalsIgnoreCase("openorders")))
        {
            query = "select distinct order_no,vendor_name from waste_order_view where order_no";
            query +=" is not null and (actual_ship_date is not null or actual_ship_date > sysdate -"+order_text+") and VENDOR_ID= '"+Vendor_id+"' order by order_no";
        }
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable tmp1 = new Hashtable();
                /*tmp1.put("ORDER_NO",
                         rs.getString("ORDER_NO")==null?"":rs.getString("ORDER_NO"));
                tmp1.put("VENDOR_NAME",
                         rs.getString("VENDOR_NAME")==null?"":rs.getString("VENDOR_NAME"));*/
                result.addElement(rs.getString("ORDER_NO")==null?"":rs.getString("ORDER_NO"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for Get All Open Orders !!!");
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    public Vector getAcessParam(String Login_Id)  throws Exception
    {
        //String query = "select vendor_id, vendor_name from waste_vendor_login where login_id ='"+Login_Id+"'";
        //Logon_id for condor.
          String query = "select vendor_id, vendor_name from waste_vendor_login where logon_id ='"+Login_Id+"'";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Hashtable tmp1 = new Hashtable();
                tmp1.put("VENDOR_ID",
                         rs.getString("VENDOR_ID")==null?"":rs.getString("VENDOR_ID"));
                tmp1.put("VENDOR_NAME",
                         rs.getString("VENDOR_NAME")==null?"":rs.getString("VENDOR_NAME"));
                result.addElement(tmp1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for Access Codes !!!");
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    public String getVendorId(String Order_No)  throws Exception
    {
        String Vendor_Id = " ";
        String query = "select vendor_id from waste_order where order_no='"+Order_No+"'";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector result = new Vector();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Vendor_Id = (rs.getString("VENDOR_ID")).toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query Getting Vendor Id !!!");
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return Vendor_Id;
    }

    public boolean IsinVendorLogin(String Logon_Id,
                                   String Vendor_Id) throws Exception
    {
        //String query = "select * from waste_vendor_login where Login_id ='"+Logon_Id+"' and vendor_id='"+Vendor_Id+"'"; //eagle
        String query = "select * from waste_vendor_login where Logon_id ='"+Logon_Id+"' and vendor_id='"+Vendor_Id+"'";   //Condor
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int test = 0;
        Vector result = new Vector();
        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                Vendor_Id = (rs.getString("VENDOR_ID")).toString();
                result.addElement(Vendor_Id);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query Getting Vendor Id !!!");
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        test = result.size();
        //System.out.println("! this the number or vendr...."+test+"");
        if (test > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Vector getOrderHeader(String order_no)  throws Exception
    {
        String query = "select * from waste_order_email_header_view where order_no='"+order_no+"'";
        String query2 = "select to_char(ORIGINAL_SUBMIT_DATE,'mm/dd/yyyy') ORIGINAL_SUBMIT_DATE,"+
            "to_char(RESUBMIT_DATE ,'mm/dd/yyyy') RESUBMIT_DATE  from waste_order_header_view where order_no='"+order_no+"'";

        DBResultSet dbrs = null;
        ResultSet rs = null;
        DBResultSet dbrs2 = null;
        ResultSet rs2 = null;

        Vector result = new Vector();
        try
        {
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();

            dbrs2 = db.doQuery(query2);
            rs2 = dbrs2.getResultSet();

            while (rs.next())
            {
                Hashtable tmp1 = new Hashtable();
                tmp1.put("STORAGE_LOCATION",
                         rs.getString("STORAGE_LOCATION")==null?"&nbsp;":rs.getString("STORAGE_LOCATION"));
                tmp1.put("FULL_NAME",
                         rs.getString("FULL_NAME")==null?"&nbsp;":rs.getString("FULL_NAME"));
                tmp1.put("PHONE",
                         rs.getString("PHONE")==null?"&nbsp;":rs.getString("PHONE"));
                tmp1.put("EMAIL",
                         rs.getString("EMAIL")==null?"&nbsp;":rs.getString("EMAIL"));
                tmp1.put("VENDOR_FACILITY_ID",
                         rs.getString("VENDOR_FACILITY_ID")==null?"&nbsp;":rs.getString("VENDOR_FACILITY_ID"));

                while (rs2.next())
                {
                    tmp1.put("ORIGINAL_SUBMIT_DATE",
                             rs2.getString("ORIGINAL_SUBMIT_DATE")==null?"&nbsp;":rs2.getString("ORIGINAL_SUBMIT_DATE"));
                    tmp1.put("RESUBMIT_DATE",
                             rs2.getString("RESUBMIT_DATE")==null?"&nbsp;":rs2.getString("RESUBMIT_DATE"));
                }

                result.addElement(tmp1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("!!!!!! Error is query for Access Codes !!!");
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    public Vector getwasteorderDetail(String orderNo, String ship_Id) throws Exception
    {
       String query = " Select  vendor_shipment_id,planned_ship_date,preferred_service_date, "+
                      "actual_ship_date from waste_order_web_view where"+
                      " ORDER_NO="+orderNo+" and shipment_id = "+ship_Id+"";

        DBResultSet dbrs = null;

        ResultSet rs = null;

        Hashtable resultH = new Hashtable();
        Vector result = new Vector();
        Vector result1 = new Vector();
        Hashtable tmp2 = new Hashtable();

        try
        {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();

            while (rs.next())
            {
                Hashtable tmp1 = new Hashtable();
                tmp1.put("VENDOR_SHIPMENT_ID",
                         rs.getString("VENDOR_SHIPMENT_ID")==null?"":rs.getString("VENDOR_SHIPMENT_ID"));
                tmp1.put("PLANNED_SHIP_DATE",
                         rs.getString("PLANNED_SHIP_DATE")==null?"":rs.getString("PLANNED_SHIP_DATE"));
                tmp1.put("ACTUAL_SHIP_DATE",
                         rs.getString("ACTUAL_SHIP_DATE")==null?"&nbsp;":rs.getString("ACTUAL_SHIP_DATE"));
                tmp1.put("PREFERRED_SERVICE_DATE",
                         rs.getString("PREFERRED_SERVICE_DATE")==null?"&nbsp;":rs.getString("PREFERRED_SERVICE_DATE"));
                result.addElement(tmp1);
            }
            //resultH.put("SHIP_DETAILS",result);

            /*while (rs3.next())
            {
                Hashtable tmp3 = new Hashtable();
                tmp3.put("BULK_CONT",
                         rs3.getString("bulk_or_container")==null?"&nbsp;":rs3.getString("bulk_or_container"));
                result1.addElement(tmp3);
            }
            resultH.put("BULK_OR_CONT",result1);*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    public Vector getlacpackdate(String orderNo) throws Exception
    {
        Vector lab = new Vector();
        String  where1 = "Select lab_pack_drum_estimate, to_char(lab_pack_preferred_service_dat,'mm/dd/yyyy') lab_pack_preferred_service_dat from Waste_shipment where order_no = "+orderNo+"";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try
        {
            dbrs = db.doQuery(where1);
            rs=dbrs.getResultSet();
            while (rs.next())
            {
                lab.addElement(rs.getString("lab_pack_preferred_service_dat")==null?"":rs.getString("lab_pack_preferred_service_dat"));
                lab.addElement(rs.getString("lab_pack_drum_estimate")==null?"":rs.getString("lab_pack_drum_estimate"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return lab;
    }
  }

    /*
    public Integer getContainerShipmentId(int orNo) throws Exception
    {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int result = 0;
        String query = "select count(*) from waste_container where order_no = "+orNo;
        int count = 0;
        try
        {
            count = DbHelpers.countQuery(db,query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        if (count > 0)
        {
            query = "select distinct(shipment_id) from waste_container where order_no = "+orNo;
            try
            {
                dbrs = db.doQuery(query);
                rs=dbrs.getResultSet();
                while (rs.next())
                {
                    result = BothHelpObjs.makeZeroFromNull(rs.getString("shipment_id"));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                dbrs.close();
            }
        }
        return (new Integer(result));
    }
    public void deleteOrderNo(int orderNo) throws Exception{
     String query = "select * from waste_container where order_no = " +orderNo;
     WasteContainer wc = new WasteContainer(db);
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String val = "";
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         wc.setWasteRequestId(rs.getInt("waste_request_id"));
         wc.setLineItem(rs.getInt("line_item"));
         wc.insert("order_no",val,WasteContainer.NULLVAL);
       }
     }catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
     } finally{
       dbrs.close();
     }
    } */
