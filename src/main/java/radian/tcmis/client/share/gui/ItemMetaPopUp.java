package radian.tcmis.client.share.gui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;
import radian.tcmis.client.share.httpCgi.TcmisHttpConnection;
import radian.tcmis.both1.helpers.BothHelpObjs;


public class ItemMetaPopUp extends JPopupMenu {
  private CmisApp cmis;
  private String itemID;
  private String facID;
  private String partNo;
  private String partGroupNo;
  private String catalogId;
  private String catalogCompanyId = "";
  private String packaging;
  private String tradeName;
  private String mfg;
  private String workAreaID;
  private boolean allCatalog;
  private String imageUrl;
  private String mfgLitUrl;
  private String inventoryGroup = "";

  public ItemMetaPopUp(CmisApp cmis,String itemID, String facID, String partNo, String catalogID, String partGroupNo) {
    super();
    this.cmis = cmis;
    this.itemID = itemID;
    this.facID = facID;
    this.partNo = partNo;
    this.catalogId = catalogID;
    this.partGroupNo = partGroupNo;
  }

  public void setCatalogCompanyId(String s) {
    this.catalogCompanyId = s;
  }

  public void setInventoryGroup(String s) {
    this.inventoryGroup = s;
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
  public void setAllCatalog(boolean b) {
    this.allCatalog = b;
  }
  public void setWorkArea(String s) {
    workAreaID = s;
  }

  public boolean loadData() {
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG_TABLE,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","GET_ITEM_INVENTORY_INFO"); //String, String
    query.put("ITEM_ID",itemID);  //String, String
    query.put("FACILITY_ID",facID);  //String, String
    query.put("SEL_WORK_AREA",workAreaID);  //String, String
    query.put("CATALOG_ID",catalogId);  //String, String
    query.put("CATALOG_COMPANY_ID",catalogCompanyId);
    query.put("PART_NO",partNo);  //String, String

    Hashtable result = cgi.getResultHash(query);
    if (result == null && !cmis.getMain().stopped){
      GenericDlg.showAccessDenied(cmis.getMain());
      return false;
    }
    Boolean b = (Boolean)result.get("SUCCEED");
    if (b.booleanValue()) {
      JMenuItem mi;
      ItemMetaActionListener mal = new ItemMetaActionListener(this);

      mi = new JMenuItem("Item ID:    "+itemID);
      mi.setFont(new java.awt.Font("Dialog", 1, 12));
      add(mi);

      mi = new JMenuItem("Inventory:   "+result.get("ITEM_INVENTORY").toString());
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
      //manufacturer literature
      mfgLitUrl = (String)result.get("MFG_LIT_URL");
      mi = new JMenuItem("Mfg Lit.");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
      mi.setEnabled(!BothHelpObjs.isBlankString(mfgLitUrl));
      //item image
      imageUrl = (String)result.get("IMAGE_URL");
      mi = new JMenuItem("Image");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener(mal);
      mi.setEnabled(!BothHelpObjs.isBlankString(imageUrl));
      //APPROVED_WRKAREAS
      mi = new JMenuItem("Approved Work Areas");
      mi.setFont(new java.awt.Font("Dialog", 0, 10));
      add(mi);
      mi.addActionListener( mal );
    }else {
      GenericDlg err = new GenericDlg(cmis.getMain(),"ERROR",true);
      err.setMsg((String)result.get("MSG"));
      err.show();
    }
    return b.booleanValue();
  }

  void goImage(){
    try {
      // From the stand alone application
      new URLCall(imageUrl,cmis,false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  /*
  void goItemInventoryFloat() {
    InventFloat IF = new InventFloat(cmis.getMain(),cmis);
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
         "&catalogId=" + catalogId +
         "&partGroupNo=" + partGroupNo;
     new URLCall(url, cmis, false);
   } catch (Exception e) {
     e.printStackTrace();
   }
 } //end of method


  void goMfgLit() {
    try {
      // From the stand alone application
      new URLCall(mfgLitUrl,cmis,false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method

  void goApprovedWorkAreas() {
    ApprovedWorkAreaDlg awaDlg = new ApprovedWorkAreaDlg(cmis,catalogId,facID,partNo,partGroupNo,allCatalog,catalogCompanyId);
    CenterComponent.centerCompOnScreen(awaDlg);
    awaDlg.setVisible(true);
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

class ItemMetaActionListener implements ActionListener {
  ItemMetaPopUp you;
  public ItemMetaActionListener(ItemMetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().toString().startsWith("Inventory")) {
        you.goItemInventoryFloat();
      }else if (e.getActionCommand().toString().startsWith("Mfg Lit.")) {
        you.goMfgLit();
      }else if (e.getActionCommand().toString().startsWith("Image")) {
        you.goImage();
      }else if (e.getActionCommand().toString().startsWith("Approved Work Areas")) {
        you.goApprovedWorkAreas();
      }
  }
}