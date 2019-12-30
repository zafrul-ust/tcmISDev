package radian.tcmis.client.share.gui;

//import com.borland.dx.dataset.*;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.util.Hashtable;
import java.util.Vector;
import java.net.URLEncoder;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.client.share.helpers.ClientHelpObjs;
import radian.tcmis.client.share.httpCgi.TcmisHttpConnection;


public class PartMetaPopUp extends JPopupMenu {
  private CmisApp cmis;
  private String partNo;
  private String facID;
  private String partGroupNo;
  private String catalogID;
  private String catalogCompanyId = "";
  private String comment;
  private boolean showComment;
  private String packaging;
  private String tradeName;
  private String mfg;
  private String materialID;
  private String itemType;
  private String selworkarea = "";
  private String workAreaDesc = "";
  private boolean allCatalog = false;
  Hashtable approvedProcess;
  private String baselineReset = "";
  private String baselineExpiration = "";
  private String inventoryGroup = "";
  private String inventoryGroupName = "";
  private String medianLeadTime = "";

  public PartMetaPopUp(CmisApp cmis,String partNo, String fac, String partGroupNo, String catalog, String comment, boolean showComment, String itemType) {
    super();
    this.cmis = cmis;
    this.partNo = partNo;
    this.facID = fac;
    this.partGroupNo = partGroupNo;
    this.catalogID = catalog;
    this.comment = comment;
    this.showComment = showComment;
    this.itemType = itemType;
  }

  public void setCatalogCompanyId(String s) {
    this.catalogCompanyId = s;
  }

  public void setWorkarea(String s) {
      this.selworkarea = s;
  }
  public void setPackaging(String s) {
    this.packaging = s;
  }
  public void setTradeName(String s) {
    this.tradeName = s;
  }
  public void setMfg(String s) {
    this.mfg = s;
  }
  public void setMaterialID(String s) {
    this.materialID = s;
  }
  public void setAllCatalog(boolean b) {
    this.allCatalog = b;
  }
  public void setWorkAreaDesc(String s) {
    this.workAreaDesc = s;
  }

  public void setBaselineReset(String s) {
    this.baselineReset = s;
  }

  public void setBaselineExpiration(String s) {
    this.baselineExpiration = s;
  }

  public void setInventoryGroup(String s) {
    this.inventoryGroup = s;
  }

  public void setInventoryGroupName(String s) {
    this.inventoryGroupName = s;
  }

  public void setMedianLeadTime(String s) {
    this.medianLeadTime = s;
  }

  public boolean loadData() {
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG_TABLE,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_INVENTORY_SPEC_INFO"); //String, String
    query.put("PART_NO",partNo);  //String, Integer
    query.put("FACILITY_ID",facID);
    query.put("CATALOG_ID",catalogID);
    query.put("CATALOG_COMPANY_ID",catalogCompanyId);
    query.put("PART_GROUP_NO",partGroupNo);
    query.put("ITEM_TYPE",itemType);
    query.put("SEL_WORK_AREA",selworkarea);
    query.put("INVENTORY_GROUP",inventoryGroup);
    Hashtable result = cgi.getResultHash(query);
    if (result == null && !cmis.getMain().stopped){
      GenericDlg.showAccessDenied(cmis.getMain());
      return false;
    }
    CursorChange.setCursor(cmis.getMain(), Cursor.DEFAULT_CURSOR);
    Boolean b = (Boolean)result.get("SUCCEED");
    if (b.booleanValue()) {
      JMenuItem mi;
      PartMetaActionListener mal = new PartMetaActionListener(this);

      mi = new JMenuItem("Part No : "+partNo);
      mi.setFont(new java.awt.Font("Dialog", 1, 12));
      add(mi);

      JMenu subMenu = null;
      //don't show part inventory, reorder point, stocking level, and item inventory if inventory group is unknown
      if (!BothHelpObjs.isBlankString(inventoryGroup)) {
        //part inventory
        mi = new JMenuItem("Part Inventory:    " + result.get("PART_INVENTORY").toString());
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
        //reorder point
        mi = new JMenuItem("Reorder Point:    " + result.get("REORDER_POINT").toString());
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
        //stocking level
        mi = new JMenuItem("Stocking Level:  " + result.get("STOCKING_LEVEL").toString());
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
        //Item Inventory
        subMenu = new JMenu("Item Inventory");
        String[] itemText = (String[]) result.get("ITEM_INVENTORY");
        if (itemText.length > 0) {
          for (int i = 0; i < itemText.length; i++) {
            mi = new JMenuItem(itemText[i]);
            mi.setActionCommand("Item-" + itemText[i]);
            mi.setFont(new java.awt.Font("Dialog", 0, 10));
            subMenu.add(mi);
            mi.addActionListener(mal);
          }
        } else {
          subMenu.setEnabled(false);
        }
        subMenu.setFont(new java.awt.Font("Dialog", 0, 10));
        add(subMenu);
      }

      //SPEC
      subMenu = new JMenu("Specification");
      String[] specText = (String[])result.get("SPEC");
      String[] specOnLine = (String[])result.get("SPEC_ON_LINE");
      if (specText.length > 0 ) {
        for (int i = 0; i < specText.length; i++) {
          mi = new JMenuItem(specText[i]);
          mi.setActionCommand("Spec-"+specText[i]);
          mi.setFont(new java.awt.Font("Dialog", 0, 10));
          if (!"Y".equalsIgnoreCase(specOnLine[i])) {
            mi.setEnabled(false);
          }else {
            mi.setEnabled(true);
          }
          subMenu.add(mi);
          mi.addActionListener(mal);
        }
      }else{
        subMenu.setEnabled(false);
      }
      subMenu.setFont(new java.awt.Font("Dialog", 0, 10));
      add(subMenu);

      //Quality flow down
      Vector flowDownV = (Vector)result.get("FLOW_DOWN");
      Vector flowDownURLV = (Vector)result.get("FLOW_DOWN_URL");
      if (flowDownV.size() > 0) {
        subMenu = new JMenu("Quality");
        for (int i = 0; i < flowDownV.size(); i++) {
          mi = new JMenuItem((String)flowDownV.elementAt(i));
          String flowDownURL = (String)flowDownURLV.elementAt(i);
          if (flowDownURL.length() > 1) {
            mi.setActionCommand("Quality-"+flowDownURL);
          }
          mi.setFont(new java.awt.Font("Dialog", 0, 10));
          subMenu.add(mi);
          mi.addActionListener(mal);
        }
        subMenu.setFont(new java.awt.Font("Dialog", 0, 10));
        add(subMenu);
      }

      //Comments
      if (showComment) {
        mi = new JMenuItem("Edit Comment");
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
      }

      //APPROVED_WRKAREAS
      mi = new JMenuItem("Approved Work Areas");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener( mal );

      //work area part comments
      if (selworkarea.length() > 0) {
        mi = new JMenuItem("Work Area Comments");
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
      }

      //part number comments
      mi = new JMenuItem("Part Number Comments");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
      /*haven't figure out to make it work YET!!!
      //for some dummp reason it will not work if the display pane for the menu
      //is outside for the main frame
      //this will work if I added the below codes to ItemMetaPopup
      mi.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
          CursorChange.setCursor(cmis.getMain(), Cursor.HAND_CURSOR);
        }
        public void mouseExited(MouseEvent e) {
          CursorChange.setCursor(cmis.getMain(), Cursor.DEFAULT_CURSOR);
        }
      });
      */

      //APPROVED_PROCESS
      approvedProcess = (Hashtable)result.get("APPROVED_PROCESS");
      if (approvedProcess.containsKey("PROCESS_ID") && !allCatalog) {
        Vector v = (Vector)approvedProcess.get("PROCESS_ID");
        if (v.size() > 0) {
          mi = new JMenuItem("Approved Process");
          mi.setFont(new java.awt.Font("Dialog", 0, 10));
          add(mi);
          mi.addActionListener( mal );
        }
      } //end of approved process

      //Baseline dates
      if (cmis.displayBaselineDate) {
        //reset
        mi = new JMenuItem("Baseline Reset: " + baselineReset);
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
        //expiration
        mi = new JMenuItem("Baseline Expiration: " + baselineExpiration);
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
      } //end of Baseline
      //leadtime chart
      if (cmis.displayLeadTimeChart && inventoryGroup.length() > 0) {
        String tmp = "Lead Time Plots";
        if (medianLeadTime.length() > 0) {
          try {
            float f = Float.parseFloat(medianLeadTime);
            if (f <= 1.0) {
              tmp += " (Median: "+medianLeadTime+" day)";
            }else {
              tmp += " (Median: "+medianLeadTime+" days)";
            }
          }catch (Exception e) {
            tmp += " (Median: "+medianLeadTime+" days)";
          }
        }
        mi = new JMenuItem(tmp);
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
      } //end of Baseline

    }else {    //end of not succeeded
      GenericDlg err = new GenericDlg(cmis.getMain(),"ERROR",true);
      err.setMsg((String)result.get("MSG"));
      err.show();
    }
    return b.booleanValue();
  }

  void goLeadTimeChart() {
    int kind = 0;
    try {
      // From the stand alone application
      String client = cmis.getResourceBundle().get("CLIENT_INITIALS").toString();
      if ("Boeing".equalsIgnoreCase(client)) {
       client = "ula";
      }
      String url = "/tcmIS/"+client.toLowerCase()+"/createleadtimechart.do?"+
                   "catPartNo="+URLEncoder.encode(partNo)+"&inventoryGroup="+URLEncoder.encode(inventoryGroup)+
                   "&catalogId="+URLEncoder.encode(catalogID)+"&catalogCompanyId="+URLEncoder.encode(catalogCompanyId)+
                   "&partGroupNo="+URLEncoder.encode(partGroupNo)+"&inventoryGroupName="+URLEncoder.encode(inventoryGroupName);
      new URLCall(url,cmis);
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void goShowQuality(String quality) {
    quality = quality.substring(quality.indexOf("-")+1);
    new URLCall(quality,cmis);
  }

  /*
  void goItemInventoryFloat(String itemID) {
    InventFloat IF = new InventFloat(cmis.getMain(),cmis);
    itemID = itemID.substring(itemID.indexOf("-")+1,itemID.indexOf(":"));
    Integer item = new Integer(itemID);
    IF.setItem(item.intValue());
    IF.setHub("");
    IF.setFacilityId(facID);
    IF.setPackaging(packaging);
    IF.setTradeName(tradeName);
    IF.setMfg(mfg);
    IF.loadIt();
  }
  */

  void goItemInventoryFloat() {
    try {
      String client = cmis.getResourceBundle().get("CLIENT_INITIALS").toString();
      if ("Boeing".equalsIgnoreCase(client)) {
       client = "ula";
      }
      String url = "https://www.tcmis.com/tcmIS/" + client.toLowerCase() + "/inventorydetail.do?catalogCompanyId=" + cmis.companyId +
          "&catPartNo=" + partNo +
          "&inventoryGroup=" + inventoryGroup +
          "&catalogId=" + catalogID +
          "&partGroupNo=" + partGroupNo;
      new URLCall(url, cmis, false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method


  void goPartInventoryFloat(String partNo) {

  }

  void goSpec(String specID) {
    int kind = 0;
    kind = URLGrab.SPEC;
    specID = specID.substring(specID.indexOf("-")+1).trim();
    if (specID.indexOf(" ") > 0) {
      specID = specID.substring(0,specID.indexOf(" "));
    }
    String client = cmis.getResourceBundle().get("CLIENT_INITIALS").toString();
      if ("Boeing".equalsIgnoreCase(client)) {
       client = "ula";
      }
    ClientHelpObjs.goURL(cmis,materialID,specID,client,facID,partNo,kind,true);
  }

  void goEditComment() {
    EditCommentDlg RjW = new EditCommentDlg(cmis,cmis.getMain(),"Edit Comment",true, "Comments:",this.partNo,this.facID,this.partGroupNo,this.catalogID);
    RjW.setCatalogCompanyId(catalogCompanyId);
    if (!BothHelpObjs.isBlankString(comment)) RjW.setText(comment);
    RjW.setVisible(true);
  }

  void goApprovedWorkAreas() {
    ApprovedWorkAreaDlg awaDlg = new ApprovedWorkAreaDlg(cmis,catalogID,facID,partNo,partGroupNo,allCatalog,catalogCompanyId);
    CenterComponent.centerCompOnScreen(awaDlg);
    awaDlg.setVisible(true);
  }

  void goWorkAreaComments() {
    WorkAreaCommentsDlg awaDlg = new WorkAreaCommentsDlg(cmis,catalogID,facID,partNo,partGroupNo,allCatalog,selworkarea,workAreaDesc,catalogCompanyId);
    CenterComponent.centerCompOnScreen(awaDlg);
    awaDlg.setVisible(true);
  }

  void goPartNumberComments() {
    PartNumberCommentsDlg pnDlg = new PartNumberCommentsDlg(cmis,catalogID,partNo,partGroupNo,false,catalogCompanyId);
    CenterComponent.centerCompOnScreen(pnDlg);
    pnDlg.setVisible(true);
  }


  void goApprovedProcess() {
    ApprovedProcessDlg apDlg = new ApprovedProcessDlg(cmis,catalogID,facID,partNo,partGroupNo,selworkarea,workAreaDesc,approvedProcess,catalogCompanyId);
    CenterComponent.centerCompOnScreen(apDlg);
    apDlg.setVisible(true);
  }

  public void show(Component comp, int x, int y) {
    if (loadData()) {
      // make sure the entire menu is showing
      // this is a real kludge
      int offset = 185;
      int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
      if(comp.getLocationOnScreen().y + y > screenH - offset) {
        y = screenH - offset - comp.getLocationOnScreen().y;
      }
      super.show(comp,x,y);
    }
  }  //end of method
}    //end of class

class PartMetaActionListener implements ActionListener {
  PartMetaPopUp you;
  public PartMetaActionListener(PartMetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().toString().startsWith("Item")) {
        //you.goItemInventoryFloat(e.getActionCommand().toString());
        you.goItemInventoryFloat();
      }else if (e.getActionCommand().toString().startsWith("Spec")) {
        you.goSpec(e.getActionCommand().toString());
      }else if (e.getActionCommand().toString().startsWith("Edit")) {
        you.goEditComment();
      }else if (e.getActionCommand().toString().startsWith("Quality")) {
        you.goShowQuality(e.getActionCommand().toString());
      }else if (e.getActionCommand().toString().startsWith("Approved Work Areas")) {
        you.goApprovedWorkAreas();
      }else if (e.getActionCommand().toString().startsWith("Work Area Comments")) {
        you.goWorkAreaComments();
      }else if (e.getActionCommand().toString().startsWith("Part Number Comments")) {
        you.goPartNumberComments();
      }else if (e.getActionCommand().toString().startsWith("Approved Process")) {
        you.goApprovedProcess();
      }else if (e.getActionCommand().toString().startsWith("Lead Time Plots")) {
        you.goLeadTimeChart();
      }

  } //end of method
} //end of classes
