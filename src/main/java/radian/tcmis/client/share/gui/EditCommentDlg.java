//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components

package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.httpCgi.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class EditCommentDlg extends JDialog {
  StaticJLabel rejLabel = new StaticJLabel();
  public JTextArea rejArea = new JTextArea();
  JButton okB = new JButton();
  String label = new String(" ");
  JPanel paneM = new JPanel();
  JButton cancelB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  String partNo;
  String facID;
  String partGroupNo;
  String catalogID;
  String catalogCompanyId = "";
  CmisApp parent;

  public EditCommentDlg(CmisApp cmis,Frame frame, String title, boolean modal, String label, String partNo, String fac, String partGroupNo, String catalog) {
      super(frame, title, modal);
      this.parent = cmis;
      this.label= label;
      this.partNo = partNo;
      this.facID = fac;
      this.partGroupNo = partGroupNo;
      this.catalogID = catalog;
      try {
        jbInit();
        getContentPane().add(paneM);
        pack();
        CenterComponent.centerCompOnScreen(this);

         //colors and fonts
         ClientHelpObjs.makeDefaultColors(this);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
  }

  public void setCatalogCompanyId(String s) {
    this.catalogCompanyId = s;
  }

  public void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    okB.setText("Ok");
    okB.setMaximumSize(new Dimension(120, 23));
    okB.setMinimumSize(new Dimension(100, 23));
    okB.setPreferredSize(new Dimension(100, 23));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    okB.addActionListener(new EditCommentDlg_okB_actionAdapter(this));
    okB.addKeyListener(new EditCommentDlg_okB_keyAdapter(this));
    rejLabel.setText(label);
    paneM.setLayout(gridBagLayout1);
    cancelB.setMaximumSize(new Dimension(100, 23));
    cancelB.setMinimumSize(new Dimension(100, 23));
    cancelB.setPreferredSize(new Dimension(100, 23));
    cancelB.setText("Cancel");
    cancelB.addActionListener(new EditCommentDlg_cancelB_actionAdapter(this));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    paneM.setMinimumSize(new Dimension(400, 300));
    paneM.setPreferredSize(new Dimension(400, 300));
    rejArea.setLineWrap(true);
    rejArea.setWrapStyleWord(true);
    rejArea.setMargin(new Insets(3, 3, 3, 3));
    rejArea.setDoubleBuffered(true);
    paneM.add(rejLabel, new GridBagConstraints2(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 10, 0, 5), 0, 0));
    paneM.add(rejArea, new GridBagConstraints2(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 5), 0, 0));
    paneM.add(okB, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 80, 5, 0), 0, 0));
    paneM.add(cancelB, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 15, 5, 5), 0, 0));

  }

  public String getText() {
    return rejArea.getText();
  }

  public void setText(String text) {
    rejArea.setText(text);
  }

  boolean updateData() {
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.CATALOG_TABLE,parent);
    Hashtable query = new Hashtable();
    query.put("ACTION","UPDATE_COMMENT"); //String, String
    query.put("USER_ID",parent.getUserId());  //String, Integer
    query.put("FACILITY_ID",facID);
    query.put("PART_NO",partNo);
    query.put("PART_GROUP_NO",partGroupNo);
    query.put("CATALOG_ID",catalogID);
    query.put("CATALOG_COMPANY_ID",catalogCompanyId);
    query.put("COMMENT",rejArea.getText());
    Hashtable result = cgi.getResultHash(query);
    if (result == null && !parent.getMain().stopped){
      GenericDlg.showAccessDenied(parent.getMain());
      return false;
    }
    return true;
  }

  void okB_keyPressed(KeyEvent e) {
    //ok
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (updateData()) {
        this.setVisible(false);
      }
    }
  }

  void okB_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      if (updateData()) {
        this.setVisible(false);
      }
  }

  void this_windowClosing(WindowEvent e) {
    this.setVisible(false);
  }

  void cancelB_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}

class EditCommentDlg_okB_keyAdapter extends java.awt.event.KeyAdapter {
  EditCommentDlg adaptee;

  EditCommentDlg_okB_keyAdapter(EditCommentDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.okB_keyPressed(e);
  }
}

class EditCommentDlg_okB_actionAdapter implements java.awt.event.ActionListener {
  EditCommentDlg adaptee;

  EditCommentDlg_okB_actionAdapter(EditCommentDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class EditCommentDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  EditCommentDlg adaptee;

  EditCommentDlg_cancelB_actionAdapter(EditCommentDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}