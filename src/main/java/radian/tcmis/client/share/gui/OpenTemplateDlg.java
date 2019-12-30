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
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.httpCgi.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class OpenTemplateDlg extends JDialog
{
  CmisApp cmis;
  JPanel panel1 = new JPanel();
  JButton bOk = new JButton();

  Frame parent;
  public JComboBox templateC = new JComboBox();
  JButton jButton1 = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  boolean changeFlag = false;
  String selectedTemplate = "";
  String ScreenName = "";


  public OpenTemplateDlg(CmisApp cmis, String title, boolean modal,String screen) {
    super(cmis.getMain(), title, modal);
    parent = cmis.getMain();
    ScreenName = screen;
    this.cmis = cmis;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  /*
  public OpenTemplateDlg(Frame frame) {
    this(frame, "", false);
  }

  public OpenTemplateDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public OpenTemplateDlg(Frame frame, String title) {
    this(frame, title, false);
  } */

  boolean loadData(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection(ScreenName,cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_TEMPLATES");
      query.put("USER_ID",cmis.getUserId()); //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return false;
      }
      Vector v = (Vector)result.get("LIST_TEMPLATES");
      if (v.size() <= 0 || v == null) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"No Records Found",true);
        gd.setMsg("There were no template saved under your name.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
        return false;
      }else {
        templateC = ClientHelpObjs.loadChoiceFromVector(templateC,v);
        templateC.setSelectedIndex(0);
        this.repaint();
      }
      //ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){
      e.printStackTrace();
      return false;
    }
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
    return true;
  }


  private void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    panel1.addKeyListener(new OpenTemplateDlg_panel1_keyAdapter(this));
    bOk.setMaximumSize(new Dimension(80, 24));
    bOk.setMinimumSize(new Dimension(80, 24));
    bOk.setPreferredSize(new Dimension(80, 24));
    bOk.setActionCommand("");
    bOk.setText("Ok");
    bOk.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    bOk.addKeyListener(new OpenTemplateDlg_bOk_keyAdapter(this));
    bOk.addActionListener(new OpenTemplateDlg_bOk_actionAdapter(this));

    panel1.setLayout(gridBagLayout1);
    panel1.setMaximumSize(new Dimension(340, 180));
    panel1.setMinimumSize(new Dimension(340, 180));
    panel1.setPreferredSize(new Dimension(340, 180));
    jButton1.setMaximumSize(new Dimension(120, 24));
    jButton1.setMinimumSize(new Dimension(120, 24));
    jButton1.setPreferredSize(new Dimension(120, 24));
    jButton1.setText("Cancel");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
      }
    });
    jButton1.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));

    panel1.add(templateC, new GridBagConstraints2(0, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 20, 5, 20), 0, 0));
    panel1.add(bOk, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(100, 90, 5, 5), 0, 0));
    panel1.add(jButton1, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(100, 5, 5, 5), 0, 0));
    bOk.requestFocus();

    getContentPane().add(panel1);



    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    pack();
    CenterComponent.centerCompOnScreen(this);
  }


  void bOk_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     selectedTemplate = templateC.getSelectedItem().toString();
     if (selectedTemplate.startsWith("Select")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Select a Template",true);
      gd.setMsg("Please select a template.");
      CenterComponent.centerCompOnScreen(gd);
      gd.setVisible(true);
     }else {
      changeFlag = true;
      setVisible(false);
     }
  }

  void bOk_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      selectedTemplate = templateC.getSelectedItem().toString();
      if (selectedTemplate.startsWith("Select")) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Select a Template",true);
        gd.setMsg("Please select a template.");
        CenterComponent.centerCompOnScreen(gd);
        gd.setVisible(true);
      }else {
        changeFlag = true;
        setVisible(false);
      }
    }
  }

  void panel1_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      changeFlag = false;
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

  boolean isChanged() {
    return changeFlag;
  }

  String getSelectedTemplate() {
    return selectedTemplate;
  }

}

class OpenTemplateDlg_bOk_actionAdapter implements java.awt.event.ActionListener{
  OpenTemplateDlg adaptee;

  OpenTemplateDlg_bOk_actionAdapter(OpenTemplateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bOk_actionPerformed(e);
  }
}

class OpenTemplateDlg_bOk_keyAdapter extends java.awt.event.KeyAdapter {
  OpenTemplateDlg adaptee;

  OpenTemplateDlg_bOk_keyAdapter(OpenTemplateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bOk_keyPressed(e);
  }
}

class OpenTemplateDlg_panel1_keyAdapter extends java.awt.event.KeyAdapter {
  OpenTemplateDlg adaptee;

  OpenTemplateDlg_panel1_keyAdapter(OpenTemplateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.panel1_keyPressed(e);
  }
}


