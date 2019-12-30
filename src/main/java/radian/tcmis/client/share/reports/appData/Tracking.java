package radian.tcmis.client.share.reports.appData;

import java.util.*;
import javax.swing.table.*;
/** this is a template for setting up an object to be used as a table by
    the ERW reporting programs.*/
public class Tracking {
  /*public static final int NUM_COLUMNS = 16;
  public static final int STATUS = 0;
  public static final int ITEM = 1;
  public static final int FACILITY = 2;
  public static final int PARTNUM = 3;
  public static final int TYPE = 4;
  public static final int NEEDBY = 5;
  public static final int REQDELQTY = 6;
  public static final int LASTDEL = 7;
  public static final int NEXTDELQTY = 8;
  public static final int NEXTDELDATE = 9;
  public static final int MATREQNUM = 10;
  public static final int SALESORDER = 11;
  public static final int PO = 12;
  public static final int REQUESTOR = 13;
  public static final int APPROVER = 14;
  public static final int DESC = 15;
  public static final int CRIT = 16;
  public static final int CANCEL = 17; */

  String status;
  String facility;
  String partNum;
  String type;
  String needBy;
  String reqDelQty;
  String lastDel;
  String nextDelQty;
  String nextDelDate;
  String matReqNum;
  String salesOrder;
  String po;
  String requestor;
  String approver;
  String desc;
  String crit;
  String itemId;
  String cancel;

  public Tracking(String status,String itemId,String facility,String partNum,String type,String needBy,String reqDelQty,String lastDel,String nextDelQty,String nextDelDate,String matReqNum,String salesOrder,String po,String requestor,String approver,String desc,String crit,String cancel) {
    this.status = status;
    this.itemId = itemId;
    this.facility = facility;
    this.partNum = partNum;
    this.type = type;
    this.needBy = needBy;
    this.reqDelQty = reqDelQty;
    this.lastDel = lastDel;
    this.nextDelQty = nextDelQty;
    this.nextDelDate = nextDelDate;
    this.matReqNum = matReqNum;
    this.salesOrder = salesOrder;
    this.po = po;
    this.requestor = requestor;
    this.approver = approver;
    this.desc = desc;
    if(crit.equalsIgnoreCase("true")){
      this.crit = "Critical";
    }else{
      this.crit = "Normal";
    }
  }

  public String getstatus(){return status;}
  public String getfacility(){return facility;}
  public String getitemId(){return itemId;}
  public String getpartNum(){return partNum;}
  public String gettype(){return type;}
  public String getneedBy(){return needBy;}
  public String getreqDelQty(){return reqDelQty;}
  public String getlastDel(){return lastDel;}
  public String getnextDelQty(){return nextDelQty;}
  public String getnextDelDate(){return nextDelDate;}
  public String getmatReqNum(){return matReqNum;}
  public String getsalesOrder(){return salesOrder;}
  public String getpo(){return po;}
  public String getrequestor(){return requestor;}
  public String getapprover(){return approver;}
  public String getdesc(){return desc;}
  public String getcrit(){return crit;}
  public String getcancel(){
    if(cancel == null || !cancel.equalsIgnoreCase("true")){
      return "";
    }
    return "Yes";
  }

  public static Vector getFieldVector() {
    Vector v = new Vector();
    v.addElement("status = getstatus");
    v.addElement("facility = getfacility");
    v.addElement("itemId = getitemId");
    v.addElement("partNum = getpartNum");
    v.addElement("type = gettype");
    v.addElement("needBy = getneedBy");
    v.addElement("reqDelQty = getreqDelQty");
    v.addElement("lastDel = getlastDel");
    v.addElement("nextDelQty = getnextDelQty");
    v.addElement("nextDelDate = getnextDelDate");
    v.addElement("matReqNum = getmatReqNum");
    v.addElement("salesOrder = getsalesOrder");
    v.addElement("po = getpo");
    v.addElement("requestor = getrequestor");
    v.addElement("approver = getapprover");
    v.addElement("desc = getdesc");
    v.addElement("crit = getcrit");
    v.addElement("cancel = getcancel");
    return v;
  }

  public static Vector getVector(TableModel oa, int itemCol, int facCol, int partNumCol,
                                                int typeCol, int needDateCol,
                                                int deliveredCol, int lastDelCol,
                                                int nextDelCol, int mrCol, int soCol,
                                                int poCol, int requesterCol, int approverCol,
                                                int critCol, int cancelCol,int statusCol,
                                                int nextDelQtyCol,int descCol) {
  for(int i=0;i<oa.getColumnCount();i++){
    System.out.println("column:"+i+" header:"+oa.getColumnName(i));
  }
    Vector v = new Vector();
    for(int i=0;i<oa.getRowCount();i++) {
      v.addElement(new Tracking(oa.getValueAt(i,statusCol).toString(),
                                 oa.getValueAt(i,itemCol).toString(),
                                 oa.getValueAt(i,facCol).toString(),
                                 oa.getValueAt(i,partNumCol).toString(),
                                 oa.getValueAt(i,typeCol).toString(),
                                 oa.getValueAt(i,needDateCol).toString(),
                                 oa.getValueAt(i,deliveredCol).toString(),
                                 oa.getValueAt(i,lastDelCol).toString(),
                                 oa.getValueAt(i,nextDelQtyCol).toString(),
                                 oa.getValueAt(i,nextDelCol).toString(),
                                 oa.getValueAt(i,mrCol).toString(),
                                 oa.getValueAt(i,soCol).toString(),
                                 oa.getValueAt(i,poCol).toString(),
                                 oa.getValueAt(i,requesterCol).toString(),
                                 oa.getValueAt(i,approverCol).toString(),
                                 oa.getValueAt(i,descCol).toString(),
                                 oa.getValueAt(i,critCol).toString(),
                                 oa.getValueAt(i,cancelCol).toString()));
    }
    return v;
  }
}
