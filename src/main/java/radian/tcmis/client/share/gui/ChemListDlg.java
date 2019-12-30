
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.*;
//import com.borland.jbcl.view.*;
import java.beans.*;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class ChemListDlg extends JDialog {
  CmisApp cmis;
  String listId;
  String listDesc;
  JPanel panel1 = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  JPanel bottomP = new JPanel();
  JPanel topP = new JPanel();
  JButton okB = new JButton();
  XYLayout xYLayout2 = new XYLayout();
  JPanel textP = new JPanel();
  StaticJLabel jLabel1 = new StaticJLabel();
  DataJLabel idL = new DataJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  DataJLabel descL = new DataJLabel();
  XYLayout xYLayout3 = new XYLayout();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jsp = new JScrollPane();
  JButton printB = new JButton();
  JTable myTable;
  String[] colHead = new String[]{"CAS Number","Chemical Name"};

  public ChemListDlg(CmisApp cmis, ChemListsCombo clc) {
    super(cmis.getMain(), "Chemical List", true);
    this.cmis = cmis;
    this.listId = clc.getSelectedListId();
    this.listDesc = clc.getSelectedListDesc();
    try  {
      jbInit();
      pack();
      getData();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    okB.setMaximumSize(new Dimension(80, 35));
    okB.setMinimumSize(new Dimension(80, 35));
    okB.setPreferredSize(new Dimension(80, 35));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    printB.setMaximumSize(new Dimension(92, 35));
    printB.setMinimumSize(new Dimension(92, 35));
    printB.setPreferredSize(new Dimension(92, 35));
    printB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));

    this.setResizable(false);
    this.getContentPane().setSize(520,400);
    panel1.setLayout(xYLayout1);
    xYLayout1.setHeight(420);
    xYLayout1.setWidth(520);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    idL.setText(listId);
    descL.setText(listDesc);
    descL.setToolTipText(descL.getText());
    topP.setLayout(xYLayout2);
    textP.setLayout(xYLayout3);
    jLabel1.setText("List ID:");
    jLabel3.setText("List Description:");
    jPanel1.setLayout(borderLayout1);
    printB.setText("Print");
    printB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        printB_actionPerformed(e);
      }
    });
    getContentPane().add(panel1);
    panel1.add(bottomP, new XYConstraints(10, 366, 500, 45));
    bottomP.add(okB, null);
    bottomP.add(printB, null);
    panel1.add(topP, new XYConstraints(10, 10, 500, 345));
    topP.add(textP, new XYConstraints(0, 0, 500, 80));
    textP.add(jLabel1, new XYConstraints(146, 21, -1, -1));
    textP.add(idL, new XYConstraints(184, 20, -1, -1));
    textP.add(jLabel3, new XYConstraints(104, 44, -1, -1));
    textP.add(descL, new XYConstraints(184, 43, 290, -1));
    topP.add(jPanel1, new XYConstraints(10, 80, 480, 265));
    jPanel1.add(jsp, BorderLayout.CENTER);
  }
  void getData(){
    ChemListDlgLoadThread c = new ChemListDlgLoadThread(this);
    c.start();
  }
  void loadData(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_LIST_CHEMICALS");
      query.put("LIST_ID",listId); //String, String
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Vector v = (Vector)result.get("LIST_CHEMICALS");

      buildTable(v,(Hashtable)result.get("TABLE_STYLE"));

      this.repaint();
     ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){e.printStackTrace();}
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
  }

  void buildTable(Vector v,Hashtable Result1){
    jPanel1.removeAll();
    //jsp.removeAll();
    jsp = new JScrollPane();
    Object[][] oa = new Object[v.size()][2];
    for(int i=0;i<v.size();i++){
      Hashtable h = (Hashtable)v.elementAt(i);
      oa[i][0] = h.get("CAS_NUM").toString();
      oa[i][1] = h.get("DESC").toString();
    }

    CmisTableModel dtm = new CmisTableModel(colHead,oa);
    dtm.setEditableFlag(0);
    TableSorter sorter = new TableSorter(dtm);
    sorter.setColTypeFlag("11");
    myTable = new JTable(sorter);
    sorter.addMouseListenerToHeaderInTable(myTable);

     //Nawaz 09/19/01
        AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
        //Hashtable chargeTableStyle = (Hashtable)Result1.get("TABLE_STYLE");
        Color color = (Color)Result1.get("COLOR");
        Integer t = (Integer)Result1.get("INSET_TOP");
        Integer l = (Integer)Result1.get("INSET_LEFT");
        Integer b = (Integer)Result1.get("INSET_BOTTOM");
        Integer r = (Integer)Result1.get("INSET_RIGHT");
        Integer a = (Integer)Result1.get("INSET_ALIGN");
        renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
        TableColumnModel cm = myTable.getColumnModel();
        for (int i = 0; i < myTable.getColumnCount(); i++) {
          cm.getColumn(i).setCellRenderer(renderers[0]);
        }
        //font and foreground and background color for columns heading
        String fontName = (String)Result1.get("FONT_NAME");
        Integer fontStyle = (Integer)Result1.get("FONT_STYLE");
        Integer fontSize = (Integer)Result1.get("FONT_SIZE");
        myTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)Result1.get("FOREGROUND"),(Color)Result1.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = myTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }

    myTable.getColumn(colHead[0]).setPreferredWidth(136);
    myTable.getColumn(colHead[1]).setPreferredWidth(324);
    myTable.setCellSelectionEnabled(false);
    jsp.getViewport().setView(myTable);
    myTable.setVisible(true);
    jsp.setVisible(true);
    myTable.validate();
    jsp.revalidate();
    jPanel1.add(jsp,BorderLayout.CENTER);
    jPanel1.revalidate();
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    this.setVisible(false);
  }

  Vector getListData() {
    Vector v = new Vector(myTable.getRowCount());
    for (int i = 0; i < myTable.getRowCount(); i++) {
      String[] oa = new String[2];
      oa[0] = (String)myTable.getModel().getValueAt(i,0);
      oa[1] = (String)myTable.getModel().getValueAt(i,1);
      v.addElement(oa);
    }
    return v;
  } //end of method

  void printB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      try{
        Vector v = getListData();
        if (v.size() < 1) {
          GenericDlg.showMessage("No Data to print.");
          return;
        }
        TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObj",cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION","PRINT_CHEMICAL_LIST");
        query.put("LIST_ID",this.listId);
        query.put("LIST_DESC",this.listDesc);
        query.put("LIST_DATA",v);
        Hashtable result = cgi.getResultHash(query);
        if(result == null){
          GenericDlg.showAccessDenied(cmis.getMain());
          return;
        }
        Boolean b = (Boolean)result.get("SUCCEEDED");
        if (b.booleanValue()) {
          try {
            // From the stand alone application
            String url = (String)result.get("URL");
            new URLCall(url,cmis);
            return;
          } catch (Exception eee) {
            eee.printStackTrace();
            return;
          }
        }else {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg(result.get("MSG").toString());
          gd.setVisible(true);
          return;
        }
      }catch (Exception ee) {
        ee.printStackTrace();
        return;
      }
  } //end of method
} //end of class
class ChemListDlgLoadThread extends Thread {
  ChemListDlg parent;
  public ChemListDlgLoadThread(ChemListDlg parent){
     super("ChemListDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadData();
  }
}
