
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;

public class ChemListsCombo extends JComboBox {
  Vector listId = new Vector();
  Vector listDesc = new Vector();
  Vector listIdDesc = new Vector();
  CmisApp cmis;
  Object holdDesc = "";
  boolean loading = false;
  boolean loaded = false;
  boolean reloading = false;


  public ChemListsCombo(CmisApp cmis) {
    super();
    this.cmis = cmis;
    this.setMaximumSize(new Dimension(150,20));
    try{jbInit();}catch(Exception e){}
  }

  CmisApp getCmis() {
    return cmis;
  }
  Vector getListIds() {
    return (Vector)listId.clone();
  }
  Vector getListDesc() {
    return (Vector)listDesc.clone();
  }

  public String getListIdDesc(String s) {
    for(int i=0;i<listId.size();i++) {
      if(s.equals(listId.elementAt(i).toString())) {
        return listIdDesc.elementAt(i).toString();
      }
    }
    return "";
  }

  public String getSelectedListId() {
    if(this.getSelectedIndex()<0)return "";
    String s = this.getSelectedItem().toString();
    for(int i=0;i<listId.size();i++) {
      if(s.equals(listIdDesc.elementAt(i).toString())) {
        return listId.elementAt(i).toString();
      }
    }
    return "";
  }
  public String getSelectedListDesc() {
    if(this.getSelectedIndex()<0)return "";
    String s = this.getSelectedItem().toString();
    for(int i=0;i<listId.size();i++) {
      if(s.equals(listIdDesc.elementAt(i).toString())) {
        return listDesc.elementAt(i).toString();
      }
    }
    return "";
  }
  public void reload() {
    loaded = false;
    holdDesc = this.getSelectedItem();
    reloading = true;
    loadIt();
  }

  public void loadIt(){
    ChemListsComboLoadThread aw = new ChemListsComboLoadThread(this);
    aw.start();
  }

  void loadListData() {
    loading = true;
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.REPORT,cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","LISTS"); //String, String
    //query.put("USERID",(new Integer(cmis.getUserId().intValue())).toString()); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
    }

    Vector v = (Vector)result.get("DATA");
    Object[][] oa = BothHelpObjs.get2DArrayFromVector(v,2);
    listId = new Vector();
    listDesc = new Vector();
    listIdDesc = new Vector();
    for(int i=0;i<oa.length;i++) {
      listId.addElement(oa[i][0]);
      listDesc.addElement(oa[i][1]);
      String temp = new String( oa[i][0].toString() + " - " + oa[i][1].toString());
      listIdDesc.addElement(temp.length()>70?(temp.substring(0,70)+" ..."):temp);
    }

    loadThis();
    if(reloading){
      reloading = false;
      this.setSelectedItem(holdDesc);
    }
    loading = false;
    loaded = true;
  }

  void loadThis() {
    if (this.getItemCount() > 0) this.removeAllItems();
    ClientHelpObjs.loadChoiceFromVector(this,listIdDesc);
    ClientHelpObjs.choiceSort(this);
  }

  public ChemListsCombo() {
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  void this_actionPerformed(ActionEvent e) {
    try{
      this.setToolTipText(this.getSelectedItem().toString());
    }catch(Exception ex){
      this.setToolTipText(null);
    }
  }
}

class ChemListsComboLoadThread extends Thread {
 ChemListsCombo parent;

  public ChemListsComboLoadThread(ChemListsCombo parent){
     super("ChemListsComboLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadListData();
  }
}

