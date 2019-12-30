//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.httpCgi.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class SaveTemplateDlg extends JDialog {
  CmisApp cmis;
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  JButton jButton1 = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JRadioButton updateRB = new JRadioButton();
  DataJLabel updateL = new DataJLabel();
  JRadioButton newRB = new JRadioButton();
  JTextField newT = new JTextField();

  String templateName = null;
  boolean changeFlag = false;
  Hashtable selectedData;
  String ScreenName = "";
  public SaveTemplateDlg(CmisApp cmis, String title, boolean modal,String screen) {
    super(cmis.getMain(), title, modal);
    parent = cmis.getMain();
    ScreenName = screen;
    this.cmis = cmis;
    try {
      jbInit();
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
  public SaveTemplateDlg(Frame frame) {
    this(frame, "", false);
  }

  public SaveTemplateDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public SaveTemplateDlg(Frame frame, String title) {
    this(frame, title, false);
  } */

  private void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel1.addKeyListener(new SaveTemplateDlg_panel1_keyAdapter(this));
    bOk.setMaximumSize(new Dimension(80, 24));
    bOk.setMinimumSize(new Dimension(80, 24));
    bOk.setPreferredSize(new Dimension(80, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new SaveTemplateDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new SaveTemplateDlg_bOk_actionAdapter(this));

    panel1.setLayout(gridBagLayout1);
    panel1.setMaximumSize(new Dimension(300, 180));
    panel1.setMinimumSize(new Dimension(300, 180));
    panel1.setPreferredSize(new Dimension(300, 180));
    jButton1.setMaximumSize(new Dimension(110, 24));
    jButton1.setMinimumSize(new Dimension(110, 24));
    jButton1.setPreferredSize(new Dimension(110, 24));
    jButton1.setText("Cancel");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton1.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));

    updateRB.setText("  Update:");
    updateRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        updateRB_actionPerformed(e);
      }
    });
    newRB.setText("  New:     ");
    newRB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        newRB_actionPerformed(e);
      }
    });
    newT.setMinimumSize(new Dimension(4, 24));
    newT.setPreferredSize(new Dimension(4, 24));
    panel1.add(bOk, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 0, 5, 5), 0, 0));
    panel1.add(jButton1, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(20, 10, 5, 10), 0, 0));
    panel1.add(updateRB, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 15, 10, 5), 0, 0));
    panel1.add(updateL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 10, 5), 0, 0));
    panel1.add(newRB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(20, 15, 20, 5), 0, 0));
    panel1.add(newT, new GridBagConstraints2(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 20, 5), 0, 0));
    bOk.requestFocus();

    getContentPane().add(panel1);



    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    pack();
    CenterComponent.centerCompOnScreen(this);
  }

  public void setButtonFocus(boolean b){
      bOk.requestFocus();
  }

  boolean updateTemplate(String action){
    boolean bol = false;
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection(ScreenName,cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","UPDATE_TEMPLATE");
      query.put("SAVE_ACTION",action);
      query.put("USER_ID",cmis.getUserId()); //String, Integer
      query.put("TEMPLATE_NAME",this.templateName);  //String, String
      query.put("SELECTED_DATA",selectedData);    //String, Hashtable
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        bol = false;
      }
      Boolean b = (Boolean)result.get("SUCCESS");
      String msg = (String)result.get("MSG");
      Boolean override = (Boolean)result.get("OVERRIDE");
      if (!b.booleanValue()) {
        if (override.booleanValue()) {
          ConfirmDlg cfdlg = new ConfirmDlg(cmis.getMain(),"Template Exist",true);
          cfdlg.textArea1.setText(msg);
          CenterComponent.centerCompOnScreen(cfdlg);
          cfdlg.setVisible(true);
          if (cfdlg.yesFlag) {
            if (updateTemplate("update")) {
              setVisible(false);
            }
          }else {
            changeFlag = false;
            bol = false;
          }
        }else {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"ERROR",true);
          gd.setMsg(msg);
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
          changeFlag = false;
          bol = false;
        }
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"DONE",true);
        gd.setMsg(msg);
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        changeFlag = true;
        bol = true;
      }
    }catch(Exception e){
      e.printStackTrace();
      changeFlag = false;
      bol = false;
    }
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
    if (BothHelpObjs.isBlankString(updateL.getText())) {
      updateRB.setEnabled(false);
    }else {
      newT.setEnabled(newRB.isSelected());
    }
    return bol;
  }

  void bOk_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     if (newRB.isSelected()) {
      if (BothHelpObjs.isBlankString(newT.getText())) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"ERROR",true);
        gd.setMsg("Please enter a new name for template.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return;
      }else {
        setTemplateName(newT.getText());
        changeFlag = true;
        if (updateTemplate("new")) {
          setVisible(false);
        }
      }
     }else {
      setTemplateName(updateL.getText());
      changeFlag = true;
      if (updateTemplate("update")) {
        setVisible(false);
      }
     }
  }

  void bOk_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (newRB.isSelected()) {
        if (BothHelpObjs.isBlankString(newT.getText())) {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"ERROR",true);
          gd.setMsg("Please enter a new name for template.");
          CenterComponent.centerCompOnScreen(gd);
          gd.setVisible(true);
          return;
        }else {
          setTemplateName(newT.getText());
          changeFlag = true;
          if (updateTemplate("new")) {
            setVisible(false);
          }
        }
      }else {
        setTemplateName(updateL.getText());
        changeFlag = true;
        if (updateTemplate("update")) {
          setVisible(false);
        }
      }
     }
  }

  void setTemplateName(String s) {
    this.templateName = s;
  }
  public String getTemplateName() {
    return this.templateName;
  }
  public boolean isChanged() {
    return this.changeFlag;
  }
  public void setSelectedData(Hashtable h) {
    this.selectedData = h;
  }


  void panel1_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         setVisible(false);
     }
  }

  void this_windowClosing(WindowEvent e) {
    changeFlag = false;
    this.setVisible(false);
  }

  void jButton1_actionPerformed(ActionEvent e) {
    changeFlag = false;
    this.setVisible(false);
  }

  void updateRB_actionPerformed(ActionEvent e) {
    if (updateRB.isSelected()) {
      newRB.setSelected(false);
      newT.setEnabled(false);
    }else {
      newRB.setSelected(true);
      newT.setEnabled(true);
    }
  }

  void newRB_actionPerformed(ActionEvent e) {
    if (newRB.isSelected()) {
      newT.setEnabled(true);
      updateRB.setSelected(false);
    }else {
      updateRB.setSelected(true);
      newT.setEnabled(false);
    }
  }

}

class SaveTemplateDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  SaveTemplateDlg adaptee;

  SaveTemplateDlg_bOk_actionAdapter(SaveTemplateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class SaveTemplateDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  SaveTemplateDlg adaptee;

  SaveTemplateDlg_bOk_keyAdapter(SaveTemplateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class SaveTemplateDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  SaveTemplateDlg adaptee;

  SaveTemplateDlg_panel1_keyAdapter(SaveTemplateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}

