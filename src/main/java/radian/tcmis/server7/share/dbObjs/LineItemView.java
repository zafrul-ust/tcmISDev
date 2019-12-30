/*
SQLWKS> describe line_item_view
Column Name                    Null?    Type
------------------------------ -------- ----
PR_NUMBER                      NOT NULL NUMBER(38)
LINE_ITEM                      NOT NULL VARCHAR2(30)
APPLICATION                             VARCHAR2(30)
QUANTITY                                NUMBER(38)
ITEM_ID                        NOT NULL NUMBER(38)
FAC_PART_NO                             VARCHAR2(30)
MATERIAL                                VARCHAR2(80)
MFG                                     VARCHAR2(60)
GRADE                                   VARCHAR2(30)
PKG_STYLE                               VARCHAR2(30)
PART_SIZE                               NUMBER
SIZE_UNIT                               VARCHAR2(30)
SHELF_LIFE                              NUMBER
SHELF_LIFE_UNIT                         VARCHAR2(30)
REQUIRED_DATETIME                       DATE
RELEASE_NUMBER                          VARCHAR2(30)
PO_NUMBER                               VARCHAR2(30)
CRITICAL                                VARCHAR2(1)
NOTES                                   VARCHAR2(120)
RELAX_SHELF_LIFE
*/

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;
import java.math.BigDecimal;

public class LineItemView {

   protected TcmISDBModel db;
   protected Integer pr_number;
   protected String line_item;
   protected String application;
   protected String application_desc;
//   protected Integer quantity;
   protected BigDecimal quantity;
   protected String charge_number;
   protected Integer item_id;
   protected String item_desc;
   protected String fac_part_no;
   protected String material;
   protected String mfg;
   protected String grade;
   protected String pkg_style;
   protected Float part_size;
   protected String size_unit;
   protected Float shelf_life;
   protected String shelf_life_unit;
   protected String required_datetime;
   protected String release_number;
   protected String po_number;
   protected String critical;
   protected String notes;
   protected String ship_to_location_id;
   protected String delivery_point;
   protected String delivery_type;
   protected String delivery_qty;
   protected String delivery_frequency;
   protected String charge_type;
   protected String relax_shelf_life;
   protected String scrap;
   protected String exampleItemId;
   protected String useApprover;
   protected String useApprovalStatus;
   protected String useApprovalDate;
   protected String useApprovalComment;
   protected String dpas;
   protected String applicationDisplay;
   protected String cancelStatus;
   protected String unitPrice;
   protected String itemType;
   protected String facilityID;
   protected String catalogID;
   protected String partGroupNo;
   protected String inventoryGroup;
   protected String currencyID;
   protected String catalogPrice;
   protected String orderQuantityRule;
   protected String customerRequisitionNumber;
   protected String currentCatalogPrice;
   protected String currentCurrencyId;
   protected BigDecimal totalQtyIssued;
   protected BigDecimal totalInvoicePrepQty;
   protected String unitOfSale;
   protected String unitOfSaleQuantityPerEach;
   protected String unitOfSalePrice;
   protected String auditUnitOfSale;
   protected String auditUnitOfSaleQuantityPerEach;
   protected String partDescription;
   protected String poQty;
   protected String itemDisplayPkgStyle;
   protected BigDecimal totalQtyAllocated;
   protected BigDecimal totalQtyDelivered;
	protected String listApprovalStatus;

	protected String doc_num;
   protected String doc_state;

   protected boolean cabinetMMReplenishment = false;
   protected boolean dropShipOverride = false;

   protected String requestedItemType;
	protected String catalogCompanyId;
	protected String companyId;

	public LineItemView()  throws java.rmi.RemoteException {
   }

   public LineItemView(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setPRNumber(int num) {
     this.pr_number = new Integer(num);
   }

   public void setCabinetMMReplenishment(boolean b) {
     cabinetMMReplenishment = b;
   }

   public void setDropShipOverride(boolean b) {
     dropShipOverride = b;
   }

   public Integer getPRNumber() {
     return this.pr_number;
   }

   public void setReleaseNumber(String num) {
     if (num == null){
       this.release_number = new String("");
     } else {
       this.release_number = new String(num);
     }
   }

   public String getReleaseNumber() {
     return this.release_number;
   }

   public void setLineItem(String id) {
     this.line_item = id;
   }

   public String getLineItem() {
     return this.line_item;
   }

   public void setApplication(String app) {
     this.application = app;
   }

   public String getApplication() {
     return this.application;
   }

   public void setItemId(int id) {
     this.item_id = new Integer(id);
   }

   public Integer getItemId() {
     return this.item_id;
   }

   public void setFacPartNo(String num) {
     this.fac_part_no = num;
   }

   public String getFacPartNo() {
     return this.fac_part_no;
   }

   public void setMaterial(String id) {
     this.material = id;
   }

   public String getDeliveryPoint() {
     return this.delivery_point;
   }
   public void setDeliveryPoint(String s) {
     this.delivery_point = s;
   }

   public String getShipToLocationId() {
     return this.ship_to_location_id;
   }
   public void setShipToLocationId(String s) {
     this.ship_to_location_id = s;
   }

   public String getMaterial() {
     return this.material;
   }

   public void setMfg(String id) {
     this.mfg = id;
   }

   public String getMfg() {
     return this.mfg;
   }

   public void setPartSize(float num) {
     this.part_size = new Float(num);
   }

   public Float getPartSize() {
     return this.part_size;
   }

   public void setPO(String s) {
     if (s == null) {s = new String("");}
     this.po_number = new String(s);
   }

   //trong 3/6/00
   public String getPO() {
     return (BothHelpObjs.makeBlankFromNull(this.po_number));
   }
   public String getDocNum() {
    return (BothHelpObjs.makeBlankFromNull(this.doc_num));
   }
   public void setDocNum(String s) {
    if (s == null) {s = new String("");}
    this.doc_num = new String(s);
   }
   public String getDocState() {
    return (BothHelpObjs.makeBlankFromNull(this.doc_state));
   }
   public void setDocState(String s) {
    if (s == null) {s = new String("");}
    this.doc_state = new String(s);
   }


   public void setQuantity(BigDecimal num) {
     this.quantity = num;
   }

   public BigDecimal getQuantity() {
     return this.quantity;
   }

   public void setGrade(String id) {
     this.grade = id;
   }

   public String getGrade() {
     return this.grade;
   }

   public void setShelfLife(float num) {
     this.shelf_life = new Float(num);
   }

   public Float getShelfLife() {
     return this.shelf_life;
   }

   public void setPkgStyle(String id) {
     this.pkg_style = id;
   }

   public String getPkgStyle() {
     return this.pkg_style;
   }

   public void setSizeUnit(String id) {
     this.size_unit = id;
   }

   public String getSizeUnit() {
     return this.size_unit;
   }

   public void setShelfLifeUnit(String id) {
     this.shelf_life_unit = id;
   }

   public String getShelfLifeUnit() {
     return this.shelf_life_unit;
   }

    public void setRequiredDatetime(String id) {
     this.required_datetime = id;
   }

   public String getRequiredDatetime() {
     return this.required_datetime;
   }

   public void setCritical(String c) {
     this.critical = c;
   }

   public String getCritical() {
     return this.critical;
   }

   public void setScrap(String c) {
     this.scrap = c;
   }

   public String getScrap() {
     return this.scrap;
   }

   public void setExampleItemId(String s) {
    exampleItemId = s;
   }
   public String getExampleItemId() {
    return exampleItemId;
   }

   public void setNotes(String c) {
     this.notes = c;
   }

   public String getNotes() {
     return this.notes;
   }

   public void setDeliveryType(String c) {
     this.delivery_type = c;
   }
   public String getDeliveryType() {
     return this.delivery_type;
   }
   public void setDeliveryQty(String c) {
     this.delivery_qty = c;
   }

   public String getDeliveryQty() {
     return this.delivery_qty;
   }
   public void setDeliveryFrequency(String c) {
     this.delivery_frequency = c;
   }

   public String getDeliveryFrequency() {
     return this.delivery_frequency;
   }
   public void setChargeType(String c) {
     this.charge_type = c;
   }

   public String getChargeType() {
     return this.charge_type;
   }
   public void setRelaxShelfLife(String c) {
     this.relax_shelf_life = c;
   }

   public String getRelaxShelfLife() {
     return this.relax_shelf_life;
   }

   public void setUseApprover(String s) {
    this.useApprover = s;
   }
   public String getUseApprover() {
    return this.useApprover;
   }
   public void setUseApprovalStatus(String s) {
    this.useApprovalStatus = s;
   }
   public String getUseApprovaltatus() {
    return this.useApprovalStatus;
   }
   public void setUseApprovalDate(String s) {
    this.useApprovalDate = s;
   }
   public String getUseApprovalDate() {
    return this.useApprovalDate;
   }
   public void setUseApprovalComment(String s) {
    this.useApprovalComment = s;
   }
   public String getUseApprovalComment() {
    return this.useApprovalComment;
   }

   public void setDPAS(String s) {
    this.dpas = s;
   }
   public String getDPAS() {
    return this.dpas;
   }
   public void setApplicationDisplay(String s) {
    this.applicationDisplay = s;
   }
   public String getApplicationDisplay() {
    return this.applicationDisplay;
   }
   public void setCancelStatus(String s) {
    this.cancelStatus = s;
   }
   public String getCancelStatus() {
    return this.cancelStatus;
   }
   public void setUnitPrice(String s) {
    this.unitPrice = s;
   }
   public String getUnitPrice() {
    return this.unitPrice;
   }
   public void setItemType(String s) {
    this.itemType = s;
   }
   public String getItemType() {
    return this.itemType;
   }
   public void setFacilityID(String s) {
    this.facilityID = s;
   }
   public String getFacilityID() {
    return this.facilityID;
   }
   public void setCatalogID(String s) {
    this.catalogID = s;
   }
   public String getCatalogID() {
    return this.catalogID;
   }
   public void setPartGroupNo(String s) {
    this.partGroupNo = s;
   }
   public String getPartGroupNo() {
    return this.partGroupNo;
   }
   public void setInventoryGroup(String s) {
    this.inventoryGroup = s;
   }
   public String getInventoryGroup() {
    return this.inventoryGroup;
   }

   public void setCurrencyID(String s) {
     this.currencyID = s;
   }
   public String getCurrencyID() {
     return this.currencyID;
   }

   public void setCatalogPrice(String s) {
     this.catalogPrice = s;
   }
   public String getCatalogPrice() {
     return this.catalogPrice;
   }

   public void setOrderQuantityRule(String s) {
     this.orderQuantityRule = s;
   }
   public String getOrderQuantityRule() {
     return this.orderQuantityRule;
   }

   public void setCustomerRequisitionNumber(String s) {
     this.customerRequisitionNumber = s;
   }
   public String getCustomerRequisitionNumber() {
     return this.customerRequisitionNumber;
   }

   public void setCurrentCatalogPrice(String s) {
     this.currentCatalogPrice = s;
   }
   public String getCurrentCatalogPrice() {
     return this.currentCatalogPrice;
   }

   public void setCurrentCurrencyId(String s) {
     this.currentCurrencyId = s;
   }
   public String getCurrentCurrencyId() {
     return this.currentCurrencyId;
   }

   public void setTotalQtyIssued(BigDecimal s) {
     this.totalQtyIssued = s;
   }
   public BigDecimal getTotalQtyIssued() {
     return this.totalQtyIssued;
   }

   public void setTotalInvoicePrepQty(BigDecimal s) {
     this.totalInvoicePrepQty = s;
   }
   public BigDecimal getTotalInvoicePrepQty() {
     return this.totalInvoicePrepQty;
   }

   public void setTotalQtyAllocated(BigDecimal s) {
     this.totalQtyAllocated = s;
   }
   public BigDecimal getTotalQtyAllocated() {
     return this.totalQtyAllocated;
   }

   public void setUnitOfSale(String s) {
     this.unitOfSale = s;
   }
   public String getUnitOfSale() {
     return this.unitOfSale;
   }

   public void setUnitOfSaleQuantityPerEach(String s) {
     this.unitOfSaleQuantityPerEach = s;
   }
   public String getUnitOfSaleQuantityPerEach() {
     return this.unitOfSaleQuantityPerEach;
   }

   public void setUnitOfSalePrice(String s) {
     this.unitOfSalePrice = s;
   }
   public String getUnitOfSalePrice() {
     return this.unitOfSalePrice;
   }

   public void setAuditUnitOfSale(String s) {
     this.auditUnitOfSale = s;
   }
   public String getAuditUnitOfSale() {
     return this.auditUnitOfSale;
   }

   public void setAuditUnitOfSaleQuantityPerEach(String s) {
     this.auditUnitOfSaleQuantityPerEach = s;
   }
   public String getAuditUnitOfSaleQuantityPerEach() {
     return this.auditUnitOfSaleQuantityPerEach;
   }
   public void setPartDescription(String s) {
     this.partDescription = s;
   }
   public String getPartDescription() {
     return partDescription;
   }
   public void setPoQty(String s) {
     this.poQty = s;
   }
   public String getPoQty() {
     return poQty;
   }
   public void setItemDisplayPkgStyle(String s) {
     this.itemDisplayPkgStyle = s;
   }
   public String getItemDisplayPkgStyle() {
     return itemDisplayPkgStyle;
   }

   public void setTotalQtyDelivered(BigDecimal s) {
     this.totalQtyDelivered = s;
   }
   public BigDecimal getTotalQtyDelivered() {
     return totalQtyDelivered;
   }

   public void setRequestedItemType(String s) {
     this.requestedItemType = s;
   }
   public String getRequestedItemType() {
     return requestedItemType;
   }

	public void setListApprovalStatus(String s) {
		this.listApprovalStatus = s;
	}
	public String getListApprovalStatus() {
		return listApprovalStatus;
	}

	public void setCatalogCompanyId(String s) {
		this.catalogCompanyId = s;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCompanyId(String s) {
		this.companyId = s;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getItemDesc()  throws Exception {
     String query = new String("select item_desc from jde_item where item_id = " + this.item_id.toString());
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
             if (rs.next()) {
               this.item_desc = rs.getString("item_desc");
             }


              return this.item_desc;
     }catch (Exception e) {
             e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
   }

   public String getApplicationDesc(String fac)  throws Exception {
     String query = new String("select application_desc from fac_loc_app where application = '" + this.application + "' and facility_id = '"+fac+"'");
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
             dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
             if (rs.next()) {
               this.application_desc = rs.getString("application_desc");
             }


        return this.application_desc;
     }catch (Exception e) {
             e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
   }

   public String getDefNeedByDate() throws Exception {  // on mm/dd/yyyy
       DBResultSet dbrs = null;
       ResultSet rs = null;
       String hub = "";
       boolean doMMDefaultLogic = false;
       try {
        String query = "select preferred_warehouse from facility where facility_id = '"+this.getFacilityID()+"'";
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()) {
          hub = rs.getString("preferred_warehouse");
        }
       }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.sendMail(db,"Error while in getting preferred warehouse (pr_number: "+this.getPRNumber()+")","Error: LineItemView getDefNeedByDate",86030,false);
       }finally {
        dbrs.close();
       }

       Calendar calendar = Calendar.getInstance();
       String tmpQ = "select count(*) from catalog_part_inventory where cat_part_no = '"+this.getFacPartNo()+
                     "' and inventory_group = '"+this.getInventoryGroup()+"' and stocking_method = 'MM'"+
                     " and catalog_id = '"+this.getCatalogID()+"' and part_group_no = "+this.getPartGroupNo();

       if (cabinetMMReplenishment) {
         doMMDefaultLogic = true;
       }else {
         doMMDefaultLogic = HelpObjs.countQuery(db,tmpQ) > 0;
       }
       //if drop ship override then treate line as OOR
       if (dropShipOverride) {
         doMMDefaultLogic = false;
       }

       if (doMMDefaultLogic) {
        //1-17-02 for Seagate if user put request in before 8p.m. then it is the same day delivery otherwise, the next day
        if (db.getClient().equalsIgnoreCase("Seagate")) {
          if (calendar.get(Calendar.HOUR_OF_DAY) > 20) {
            calendar.add(Calendar.DATE,1);
          }
        }else {
          if (hub.equalsIgnoreCase("Salem")) {           // dealling with TIME ZONE
            calendar.add(Calendar.HOUR,1);
          }
          if (hub.equalsIgnoreCase("Phoenix Hub")) {
            calendar.add(Calendar.HOUR,-2);
          }
          if (calendar.get(Calendar.HOUR_OF_DAY) < 13) {
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
              calendar.add(Calendar.DATE,3);
            }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
              calendar.add(Calendar.DATE,2);
            }else {
              calendar.add(Calendar.DATE,1);
            }
          }else {
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
              calendar.add(Calendar.DATE,4);
            }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
              calendar.add(Calendar.DATE,3);
            } else {
              calendar.add(Calendar.DATE,2);
            }
          }
        }//end of not Seagate
       } else {     //2-13-03 everything else is 21 days - if (BothHelpObjs.isOOR(this.getItemType())) {     // 21 days
          if (calendar.get(Calendar.HOUR_OF_DAY) < 13) {
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ||
                (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
              calendar.add(Calendar.DATE,24);
            }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
              calendar.add(Calendar.DATE,23);
            }else {
              calendar.add(Calendar.DATE,21);
            }
          }else {
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
              calendar.add(Calendar.DATE,25);
            }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
              calendar.add(Calendar.DATE,23);
            } else {
              calendar.add(Calendar.DATE,22);
            }
          }
       }
       String bdate = new String((calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.YEAR));
       return bdate;
   }

   public String getCancelStatus(String pr_num,String line_num) throws Exception {
       String query = new String("select CANCEL_STATUS from request_line_item where pr_number = "+pr_num+" and line_item='" + line_num + "'");
       DBResultSet dbrs = null;
      ResultSet rs = null;
       String status="";
       try {
               dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
               if (rs.next()) {
                 status = BothHelpObjs.makeBlankFromNull(rs.getString("CANCEL_STATUS"));
               }
       }catch (Exception e) {
               e.printStackTrace();
       } finally{
             dbrs.close();
     }
       return TrackView.getCorrectStatus(status);
   }
}
