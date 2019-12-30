package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.Component;
import java.util.*;
import radian.tcmis.client.share.httpCgi.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;


public class ItemCatComponentMetaPopUp extends JPopupMenu {
  CmisApp cmis;
  String matID;
  String specID= "";
  String facID;
  Integer itemID;
  String onLine;
  boolean evalDetail = false;
  String workArea;
  boolean showEngEval = false;
  String facilityDesc = "";
  String workAreaDesc = "";
  private String imageUrl;
  private String mfgLitUrl;

  public ItemCatComponentMetaPopUp(CmisApp cmis,String itemID, String facID, String workArea) {
    super();
    this.cmis = cmis;
    this.itemID = new Integer(itemID);
    this.facID = facID;
    this.workArea = workArea;
    this.evalDetail = true;
  }

  public ItemCatComponentMetaPopUp(CmisApp cmis,String matID, String itemID, String facID, String onLine, boolean showEngEval) {
    super();
    this.cmis = cmis;
    this.matID = matID;
    this.facID = facID;
    this.itemID = new Integer(itemID);
    this.onLine = onLine;
    this.evalDetail = false;
    this.showEngEval = showEngEval;
  }

  public boolean loadData() {
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG_TABLE, cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION", "GET_ITEM_DOCS_INFO"); //String, String
    query.put("ITEM_ID", itemID); //String, Integer
    query.put("FACILITY_ID", facID); //String, String

    Hashtable result = cgi.getResultHash(query);
    if (result == null && !cmis.getMain().stopped) {
      GenericDlg.showAccessDenied(cmis.getMain());
      return false;
    }
    Boolean b = (Boolean) result.get("SUCCEED");
    if (b.booleanValue()) {
      JMenuItem mi;
      ItemCatCompMetaActionListener mal = new ItemCatCompMetaActionListener(this);

      if (!evalDetail) {
        //view msds
        mi = new JMenuItem("View MSDS");
        mi.setActionCommand("MSDS");
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
        if (!"Y".equalsIgnoreCase(onLine)) {
          mi.setEnabled(false);
        }
        //show evaluation
        mi = new JMenuItem("Show Evaluation");
        mi.setActionCommand("Evaluation");
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
        mi.setEnabled(showEngEval);
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

      }else {
        mi = new JMenuItem("Detail");
        mi.setActionCommand("Detail");
        mi.setFont(new java.awt.Font("Dialog", 0, 10));
        add(mi);
        mi.addActionListener(mal);
      }
    }
    return b.booleanValue();
  } //end of method


  public void setFacilityDesc(String s) {
    this.facilityDesc = s;
  }
  public void setWorkAreaDesc(String s) {
    this.workAreaDesc = s;
  }

  void goShowEvaluation() {
    EngineeringEvalDlg engEval = new EngineeringEvalDlg(cmis.getMain(),"Engineering Evaluation - Item: "+itemID,itemID.toString());
    engEval.setParent(cmis);
    engEval.loadIt();
    CenterComponent.centerCompOnScreen(engEval);
    engEval.setVisible(true);
  }

  void goDetail() {
    EngineeringEvalDetailDlg engEvalDetail = new EngineeringEvalDetailDlg(cmis.getMain(),"Detail - Facility: "+facilityDesc+"  Work Area: "+workAreaDesc,itemID.toString(),facID,workArea);
    engEvalDetail.setParent(cmis);
    engEvalDetail.loadIt();
    CenterComponent.centerCompOnScreen(engEvalDetail);
    engEvalDetail.setVisible(true);
  }

  void goImage(){
    try {
      // From the stand alone application
      new URLCall(imageUrl,cmis,false);
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

  void goUrl(ActionEvent e) {
    int kind = 0;

    if (e.getActionCommand().toString().startsWith("MSDS")) {
      //System.out.println("View MSDS clicked:");
      kind = URLGrab.MSDS;
    }

    ClientHelpObjs.goURL(cmis,matID,specID,this.cmis.getResourceBundle().getString("APP_NAME"),facID,itemID.toString(),kind,false);
  }

  public void show(Component comp, int x, int y) {
    if (loadData()) {
      // make sure the entire menu is showing
      // this is a real kludge
      int offset = 185;
      int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
      if (comp.getLocationOnScreen().y + y > screenH - offset) {
        y = screenH - offset - comp.getLocationOnScreen().y;
      }
      super.show(comp, x, y);
    }
  } //end of method
} //end of class

class ItemCatCompMetaActionListener implements ActionListener {
  ItemCatComponentMetaPopUp you;
  public ItemCatCompMetaActionListener(ItemCatComponentMetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().startsWith("MSDS")) {
      you.goUrl(e);
    }else if (e.getActionCommand().startsWith("Evaluation")) {
      you.goShowEvaluation();
    }else if (e.getActionCommand().startsWith("Detail")) {
      you.goDetail();
    }else if (e.getActionCommand().toString().startsWith("Mfg Lit.")) {
      you.goMfgLit();
    }else if (e.getActionCommand().toString().startsWith("Image")) {
      you.goImage();
    }
  }
}