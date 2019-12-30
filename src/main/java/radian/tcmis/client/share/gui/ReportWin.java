//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components

package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import java.util.*;
import javax.swing.*;

import radian.tcmis.client.share.helpers.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class ReportWin extends JPanel {

  CmisApp cmis;
  JPanel tocP = new JPanel();
  JPanel rightSide = new JPanel();
  JPanel inputP = new JPanel();
  JPanel doitP = new JPanel();
  JButton doitB = new JButton();
  ReportInputPanel[] reports;
  ReportInputPanel activePanel;
  ButtonGroup bg = new ButtonGroup();
  StaticJLabel titleL = new StaticJLabel();
  JPanel titleP = new JPanel();
  FacilityCombo facC;
  //trong 1-29-01
  // A - active, I - inactive, B - both
  //String workAreaStatus = "B";
  String currentScreen = "ReportWin";

  //StaticJLabel reportL = new StaticJLabel("Reports");

  JButton openTemplateB = new JButton();
  JButton saveTemplateB = new JButton();
  JButton clearTemplateB = new JButton();

  boolean facCLoaded = false;
  boolean reloading = false;
  boolean loading = true;
  PaneLayout paneLayout1 = new PaneLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  public ReportWin(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception {
    doitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));

    openTemplateB.setIcon(ResourceLoader.getImageIcon("images/button/open.gif"));
    saveTemplateB.setIcon(ResourceLoader.getImageIcon("images/button/save.gif"));
    clearTemplateB.setIcon(ResourceLoader.getImageIcon(
        "images/button/refresh.gif"));

    facC = new FacilityCombo(cmis);
    facC.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equalsIgnoreCase("loaded")) {
          facCLoaded();
        }
      }
    });

    titleL.setText("Please wait, Loading Facility Information");
    titleP.setMinimumSize(new Dimension(203, 20));
    titleP.setPreferredSize(new Dimension(203, 20));
    titleP.add(titleL);

    clearTemplateB.setText("Refresh");
    clearTemplateB.addActionListener(new ReportWin_clearTemplateB_actionAdapter(this));
    clearTemplateB.setEnabled(false);
    clearTemplateB.setVisible(false);
    doitP.add(clearTemplateB);

    openTemplateB.setText("Open Template");
    openTemplateB.addActionListener(new ReportWin_openTemplateB_actionAdapter(this));
    openTemplateB.setEnabled(false);
    openTemplateB.setVisible(false);
    doitP.add(openTemplateB);

    saveTemplateB.setText("Save Template");
    saveTemplateB.addActionListener(new ReportWin_saveTemplateB_actionAdapter(this));
    saveTemplateB.setEnabled(false);
    saveTemplateB.setVisible(false);
    doitP.add(saveTemplateB);

    doitB.setText("Generate Report");
    doitB.addActionListener(new ReportWinButtonActionListener(this));
    doitB.setEnabled(false);
    doitB.setVisible(false);
    doitP.add(doitB);

    reports = cmis.getReportPanels();
    sortReports();
    this.setLayout(gridBagLayout1);
    this.setSize(750, 450);

    rightSide.setLayout(gridBagLayout2);
    rightSide.setBorder(new BevelBorder(BevelBorder.RAISED));

    tocP.setBorder(new BevelBorder(BevelBorder.RAISED));
    tocP.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 5, 5, true, false));
    inputP.setLayout(new BorderLayout());

    for (int i = 0; i < reports.length; i++) {
      JToggleButton jtb = new JToggleButton(reports[i].getTitle(), false);
      jtb.setSize(50, -1);
      jtb.addActionListener(new ReportWinToggleButtonActionListener(this,reports[i], jtb));
      jtb.setEnabled(false);
      bg.add(jtb);
      tocP.add(jtb);
    }

    facC.setCurrentScreen(this.currentScreen);
    facC.loadIt();

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    this.add(tocP, new GridBagConstraints2(0, 0, 1, 2, 0.0, 1.0
                                           , GridBagConstraints.NORTHWEST,
                                           GridBagConstraints.VERTICAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
    this.add(rightSide, new GridBagConstraints2(1, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.NORTHWEST,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 250,
                                                200));

    rightSide.add(titleP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
                                                  ,
                                                  GridBagConstraints.NORTHWEST,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(0, 0, 0, 0), 200,
                                                  0));
    rightSide.add(inputP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
                                                  ,
                                                  GridBagConstraints.NORTHWEST,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0, 0, 0, 0), 250,
                                                  165));
    rightSide.add(doitP, new GridBagConstraints2(0, 2, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.SOUTHWEST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 0, 0), 200, 0));

    ClientHelpObjs.makeDefaultColors(this);
  }

  public void setParent(Object parent) {
    this.cmis = (CmisApp) parent;
  }

  public void enableButtons() {
    ClientHelpObjs.setEnabledContainer(tocP, true);
    //cmis.getMain().stopImage();
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
  }

  void sortReports() {
    ReportInputPanel[] hold = new ReportInputPanel[reports.length];
    Vector rad = new Vector();
    Vector shr = new Vector();
    Vector clnt = new Vector();
    for (int i = 0; i < reports.length; i++) {
      if (reports[i].getOwner().equalsIgnoreCase("Radian")) {
        rad.addElement(reports[i]);
      } else if (reports[i].getOwner().equalsIgnoreCase("Share")) {
        shr.addElement(reports[i]);
      } else {
        clnt.addElement(reports[i]);
      }
    }
    int xx = 0;
    for (int x = 0; x < rad.size(); x++) {
      reports[xx++] = (ReportInputPanel) rad.elementAt(x);
    }
    for (int x = 0; x < shr.size(); x++) {
      reports[xx++] = (ReportInputPanel) shr.elementAt(x);
    }
    for (int x = 0; x < clnt.size(); x++) {
      reports[xx++] = (ReportInputPanel) clnt.elementAt(x);
    }
  }

  public void loadItAction() {
    loadScreenData();
    //cmis.getMain().stopImage();
    loading = false;
    tocP.setVisible(true);
    inputP.setVisible(true);
    doitP.setVisible(true);
    this.validate();
    this.repaint();
  }

  public void loadIt() {
    ReportWinLoadThread rw = new ReportWinLoadThread(this);
    rw.start();
  }

  void loadScreenData() {
  }

  void facCLoaded() {
    if (facCLoaded) {
      return;
    }
    facCLoaded = true;
    for (int x = 0; x < reports.length; x++) {
      reports[x].setFacCombo(facC);
    }
    titleL.setText("Please Select a Report");
    enableButtons();
  }

  public void viewReportClicked() {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    System.out.println("in report win");
    String tmsg = "Generating Report please wait....";
    cmis.getMain().countLabel.put(new Integer(Main.REPORT), tmsg);
    cmis.getMain().setStatusBarText( (String) cmis.getMain().countLabel.get(new
        Integer(Main.REPORT)));
    activePanel.showReport();
  }

  public void selectButtonClicked(JToggleButton jtb, ReportInputPanel rip) {
    inputP.removeAll();
    inputP.validate();
    if (jtb.isSelected()) {
      rip.loadScreen();

      titleL.setText(rip.getTitle());
      doitB.setEnabled(true);
      doitB.setVisible(true);

      String whichReport = rip.getTitle();
      if (!whichReport.startsWith("Formatted")) {
        clearTemplateB.setEnabled(true);
        clearTemplateB.setVisible(true);
        openTemplateB.setEnabled(true);
        openTemplateB.setVisible(true);
        saveTemplateB.setEnabled(true);
        saveTemplateB.setVisible(true);
      } else {
        clearTemplateB.setEnabled(false);
        clearTemplateB.setVisible(false);
        openTemplateB.setEnabled(false);
        openTemplateB.setVisible(false);
        saveTemplateB.setEnabled(false);
        saveTemplateB.setVisible(false);
      }

      doitP.validate();
      doitP.repaint();
      inputP.add(rip, BorderLayout.CENTER);
      inputP.validate();
      rip.setVisible(true);
      rip.repaint();
      activePanel = rip;
    } else {
      titleL.setText("Please Select a Report");
      doitB.setEnabled(false);
      doitB.setVisible(false);

      clearTemplateB.setEnabled(false);
      clearTemplateB.setVisible(false);
      openTemplateB.setEnabled(false);
      openTemplateB.setVisible(false);
      saveTemplateB.setEnabled(false);
      saveTemplateB.setVisible(false);

      doitP.validate();
      doitP.repaint();
      inputP.add(new JPanel(), BorderLayout.CENTER);
      inputP.validate();
      activePanel = null;
    }
    repaint();
  }

  public void stateChanged(String label) {
    //put something here...
  }

  void clearTemplateB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    activePanel.clearTemplate();
    String newTitle = activePanel.getTitle();
    titleL.setText(newTitle);
  }

  void openTemplateB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    activePanel.openTemplate();
    String newTitle = activePanel.getTitle();
    titleL.setText(newTitle);
  }

  void saveTemplateB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    activePanel.saveTemplate();
    String newTitle = activePanel.getTitle();
    titleL.setText(newTitle);
  }
}

class ReportWinLoadThread extends Thread {
  ReportWin parent;

  public ReportWinLoadThread(ReportWin parent) {
    super("ReportWinLoadThread");
    this.parent = parent;
  }

  public void run() {
    parent.loadItAction();
  }
}

class NoReportSelected extends JPanel {
  GridBagLayout gbl = new GridBagLayout();
  StaticJLabel l = new StaticJLabel();

  NoReportSelected() {
    super();
    this.setLayout(gbl);
    l.setText("Please Select a Report");
    this.add(l);
    this.validate();
  }
}

class ReportWinToggleButtonActionListener implements ActionListener {
  ReportWin rw;
  ReportInputPanel rip;
  JToggleButton jtb;
  ReportWinToggleButtonActionListener(ReportWin rw, ReportInputPanel rip,
                                      JToggleButton jtb) {
    this.rw = rw;
    this.rip = rip;
    this.jtb = jtb;
  }

  public void actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    rw.selectButtonClicked(jtb, rip);
  }
}

class ReportWinButtonActionListener implements ActionListener {
  ReportWin rw;
  ReportWinButtonActionListener(ReportWin rw) {
    this.rw = rw;
  }

  public void actionPerformed(ActionEvent e) {
    rw.viewReportClicked();
  }
}

class ReportWin_openTemplateB_actionAdapter implements java.awt.event.ActionListener {
  ReportWin adaptee;

  ReportWin_openTemplateB_actionAdapter(ReportWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.openTemplateB_actionPerformed(e);
  }
}

class ReportWin_saveTemplateB_actionAdapter implements java.awt.event.ActionListener {
  ReportWin adaptee;

  ReportWin_saveTemplateB_actionAdapter(ReportWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.saveTemplateB_actionPerformed(e);
  }
}

class ReportWin_clearTemplateB_actionAdapter implements java.awt.event.ActionListener {
  ReportWin adaptee;

  ReportWin_clearTemplateB_actionAdapter(ReportWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.clearTemplateB_actionPerformed(e);
  }
}
