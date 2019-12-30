
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.GridBagConstraints2;

//public class PartPane extends  JPanel {
public class PartPane extends  NewChemPanel {

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  StaticJLabel jLabel1 = new StaticJLabel();
  JComboBox catalogC = new JComboBox();

  StaticJLabel jLabel2 = new StaticJLabel();
  JTextField partNumT = new JTextField();
  JPanel typeP = new JPanel();
  JPanel shelfP = new JPanel();
  JPanel flowP = new JPanel();
  JScrollPane specP = new JScrollPane();
  JTabbedPane specTab = new JTabbedPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel2 = new JPanel();

  Integer specsNum = new Integer(1);
  JTabbedPane flowTabPane = new JTabbedPane();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel qualityTab = new JPanel();
  JPanel packTab = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  JButton addQualB4 = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JButton delQualB4 = new JButton();
  JList qFlowList = new JList(new DefaultListModel());
  JPanel jPanel5 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  StaticJLabel jLabel4 = new StaticJLabel();
  DataJLabel unit1L = new DataJLabel();
  JTextField initQtyT = new JTextField();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  JRadioButton oorR = new JRadioButton();
  JPanel jPanel6 = new JPanel();
  JButton addQualB5 = new JButton();
  GridBagLayout gridBagLayout8 = new GridBagLayout();
  JButton delQualB5 = new JButton();
  JList pFlowList = new JList(new DefaultListModel());
  JPanel jPanel7 = new JPanel();
  StaticJLabel jLabel5 = new StaticJLabel();
  DataJLabel unit2L = new DataJLabel();
  GridBagLayout gridBagLayout9 = new GridBagLayout();
  JRadioButton mmR = new JRadioButton();
  JPanel jPanel8 = new JPanel();

  StaticJLabel jLabel3 = new StaticJLabel();
  JTextField replacePartNumT = new JTextField();

  boolean catalogCLoaded=false;
  boolean dataLoaded=false;
  NewChem nchem = null;
  ButtonGroup typeGroup = new ButtonGroup();

  JScrollPane qFlowScroolPane = null;
  JScrollPane pFlowScroolPane = null;
  BorderLayout borderLayout6 = new BorderLayout();

  boolean myEval = false;
  String catalogId = null;
  Hashtable dyn = new Hashtable();
  String userId;

  JScrollPane partDescSCPane = new JScrollPane();
  JTextArea partDesc = new JTextArea();
  StaticJLabel partDescL = new StaticJLabel("Catalog Part Desc (to appear in catalog) - 250 chars max:");
  JRadioButton consumableRB = new JRadioButton(" Consumable");
  JRadioButton nonConsumableRB = new JRadioButton(" Non-Consumable");

  JRadioButton mfgRB = new JRadioButton();
  JRadioButton userRB = new JRadioButton();
  StaticJLabel manageL = new StaticJLabel("Manage By: ");
  JRadioButton sameShelfLifeRB = new JRadioButton();
  JRadioButton separateShelfLifeRB = new JRadioButton();
  JTabbedPane shelfLifeTabPane = new JTabbedPane();
  JPanel shelfLifePanel = new JPanel();
  BorderLayout borderLayout7 = new BorderLayout();
  int numberOfComponent = 1;
  Vector temperatureRangeV = new Vector(39);
  Vector shelfLifePeriodV = new Vector(39);
  ShelfLifeStorageTempPane shelfLifeStorageTempPanel;

  DataJLabel categoryStatusL = new DataJLabel();
  JButton categoryB = new JButton();
  JButton categoryStatusB = new JButton();
  CmisApp cmis;
  Collection selectedCategory = new Vector();
  String categoryDelimiter = "";
  String categoryStatus = "";
  String categoryStatusForButton = "";
  boolean categoryOption = false;
  boolean categoryEditable = false;
  JButton partNumberCommentB = new JButton();
  String partNumberComments = "";
  Hashtable selectedCategoryComment = new Hashtable(11);
  JButton addStdMfgCertB = new JButton();
  JButton addSpecB = new JButton();
  JPanel specButtonsP = new JPanel();
  GridBagLayout gridBagLayoutSpecButton = new GridBagLayout();
  boolean showLabelColor = false;
  Vector labelColorV = new Vector(31);
  boolean cigRestricted = false;

  public PartPane(boolean showLabelColor, boolean cigRestricted) {
    super();
    this.showLabelColor = showLabelColor;
    this.cigRestricted = cigRestricted;
    shelfLifeStorageTempPanel = new ShelfLifeStorageTempPane(showLabelColor);
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void setCmisApp( CmisApp cmis) {
    this.cmis = cmis;
  }

  public void setNumberOfComponent(int i) {
    numberOfComponent = i;
    if ((userRB.isSelected() || cigRestricted) && separateShelfLifeRB.isSelected()) {
      showShelfLifeStorageTab();
    }
  } //end of method

  private void jbInit() throws Exception {

    this.setLayout(borderLayout1);

    specTab.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        specTab_mouseClicked(e);
      }
    });

    jPanel1.setLayout(gridBagLayout6);
    jLabel1.setText("Catalog:");
    jLabel2.setText("User Part Number:");
    jLabel3.setText("Replaces Part Number:");
    consumableRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        consumableRB_actionPerformed(e);
      }
    });
    nonConsumableRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nonConsumableRB_actionPerformed(e);
      }
    });

    categoryStatusL.setText("TBD");
    categoryB.setMaximumSize(new Dimension(160,21));
    categoryB.setPreferredSize(new Dimension(160,21));
    categoryB.setMinimumSize(new Dimension(160,21));
    categoryB.setText("Select/View Category");
    categoryB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        categoryB_actionPerformed(e);
      }
    });
    categoryStatusB.setMaximumSize(new Dimension(148,21));
    categoryStatusB.setPreferredSize(new Dimension(148,21));
    categoryStatusB.setMinimumSize(new Dimension(148,21));
    categoryStatusB.setText("Reasons");
    categoryStatusB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        categoryStatusB_actionPerformed(e);
      }
    });
    partNumberCommentB.setMaximumSize(new Dimension(160,18));
    partNumberCommentB.setPreferredSize(new Dimension(160,18));
    partNumberCommentB.setMinimumSize(new Dimension(160,18));
    partNumberCommentB.setText("Part Number Comments");
    partNumberCommentB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        partNumberCommentB_actionPerformed(e);
      }
    });

    typeP.setLayout(borderLayout6);
    shelfP.setLayout(gridBagLayout4);
    flowP.setLayout(borderLayout3);
    qualityTab.setLayout(borderLayout4);
    packTab.setLayout(borderLayout5);
    //setting action buttions for quality
    addQualB4.setText("Search");
    addQualB4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addQualB4_actionPerformed(e);
      }
    });
    delQualB4.setText("Delete");
    delQualB4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delQualB4_actionPerformed(e);
      }
    });
    jPanel5.setLayout(gridBagLayout2);
    //trong 3/20
    oorR.setSelected(true);
    if (oorR.isSelected()) {
      jLabel4.setText("Initial Order Quantity: *");
    }else {
      jLabel4.setText("Estimated Quarterly Usage Rate: *");
    }
    unit1L.setText("units");
    oorR.setText("OOR");

    //setting action buttons for packaging
    jPanel6.setLayout(gridBagLayout7);
    addQualB5.setText("Search");
    addQualB5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addQualB5_actionPerformed(e);
      }
    });
    delQualB5.setText("Delete");
    delQualB5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delQualB5_actionPerformed(e);
      }
    });
    jPanel7.setLayout(gridBagLayout8);
    shelfP.setMinimumSize(new Dimension(250, 40));
    shelfP.setPreferredSize(new Dimension(250, 40));
    flowP.setMinimumSize(new Dimension(250, 95));
    flowP.setPreferredSize(new Dimension(250, 95));

    initQtyT.setMaximumSize(new Dimension(75,21));
    initQtyT.setPreferredSize(new Dimension(75,21));
    initQtyT.setMinimumSize(new Dimension(75,21));

    mmR.setText("MinMax");
    mmR.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        mmR_mouseClicked(e);
      }
    });

    //flow list
    qFlowScroolPane = new JScrollPane(qFlowList);
    qFlowList.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    qFlowList.setToolTipText("Click on the ADD button to add a flow down.");
    qFlowList.setSelectionBackground(qFlowList.getBackground());
    qFlowList.setSelectionForeground(qFlowList.getForeground());


    //flow list
    pFlowScroolPane = new JScrollPane(pFlowList);
    pFlowList.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    pFlowList.setToolTipText("Click on the ADD button to add a flow down.");
    pFlowList.setSelectionBackground(pFlowList.getBackground());
    pFlowList.setSelectionForeground(pFlowList.getForeground());

    jPanel8.setLayout(gridBagLayout9);
    oorR.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        oorR_mouseClicked(e);
      }
    });

    partNumT.setMaximumSize(new Dimension(155, 21));
    partNumT.setMinimumSize(new Dimension(155, 21));
    partNumT.setPreferredSize(new Dimension(155, 21));
    replacePartNumT.setMaximumSize(new Dimension(155, 21));
    replacePartNumT.setMinimumSize(new Dimension(155, 21));
    replacePartNumT.setPreferredSize(new Dimension(155, 21));
    specTab.setFont(new java.awt.Font("Dialog", 0, 10));
    flowTabPane.setFont(new java.awt.Font("Dialog", 0, 10));
    mfgRB.setText("Manufacturer\'s Recommendation");
    mfgRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mfgRB_actionPerformed(e);
      }
    });
    userRB.setText("User Override");
    userRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        userRB_actionPerformed(e);
      }
    });
    sameShelfLifeRB.setSelected(false);
    sameShelfLifeRB.setText("Item");
    sameShelfLifeRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        sameShelfLifeRB_actionPerformed(e);
      }
    });
    separateShelfLifeRB.setSelected(true);
    separateShelfLifeRB.setText("Components");
    separateShelfLifeRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        separateShelfLifeRB_actionPerformed(e);
      }
    });

    partDesc.setLineWrap(true);
    partDesc.setWrapStyleWord(true);
    partDescSCPane.setPreferredSize(new Dimension(160, 25));
    partDescSCPane.setMinimumSize(new Dimension(160, 25));
    typeP.setMinimumSize(new Dimension(300, 30));
    typeP.setPreferredSize(new Dimension(300, 30));
    specP.setMinimumSize(new Dimension(250, 95));
    specP.setPreferredSize(new Dimension(250, 95));

    shelfLifePanel.setLayout(borderLayout7);
    this.add(jPanel1, BorderLayout.CENTER);
    //catalog
    jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 18, 0, 0), 0, 0));
    jPanel1.add(catalogC, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 65, 0, 10), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 18, 0, 0), 0, 0));
    jPanel1.add(partNumT, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 110, 0, 10), 0, 0));
    jPanel1.add(jLabel3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    jPanel1.add(replacePartNumT, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 120, 0, 5), 0, 0));
    //part number comments
    jPanel1.add(partNumberCommentB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 18, 0, 10), 0, 0));
    //part desc
    jPanel1.add(partDescSCPane, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(20, 12, 0, 0), 0, 0));
    partDescSCPane.getViewport().add(partDesc, null);
    partDescSCPane.setBorder(ClientHelpObjs.groupBox("Catalog Part Desc(to appear in catalog)-250 chars max"));
    //type
    jPanel1.add(typeP, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 12, 0, 0), 0, 0));
    typeP.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(oorR, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel6.add(mmR, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 60, 0, 0), 0, 0));
    jPanel6.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(27, 0, 0, 0), 0, 0));
    jPanel6.add(initQtyT, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 0, 5, 0), 0, 0));
    jPanel6.add(unit1L, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 80, 5, 0), 0, 0));
    //shelf life
    jPanel1.add(shelfP, new GridBagConstraints(2, 2, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));
    shelfP.add(mfgRB, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    shelfP.add(userRB, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 182, 0, 0), 0, 0));
    shelfP.add(manageL, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 35, 0, 0), 0, 0));
    shelfP.add(sameShelfLifeRB, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 100, 0, 0), 0, 0));
    shelfP.add(separateShelfLifeRB, new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 150, 0, 0), 0, 0));
    shelfP.add(shelfLifeTabPane, new GridBagConstraints(0, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    shelfLifeTabPane.setVisible(false);
    shelfP.add(shelfLifePanel, new GridBagConstraints(0, 2, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    shelfLifePanel.add(shelfLifeStorageTempPanel,BorderLayout.CENTER);
    shelfLifePanel.setVisible(false);
    //flow down
    jPanel1.add(flowP, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 12, 5, 0), 0, 0));
    flowP.add(flowTabPane, BorderLayout.CENTER);
    flowTabPane.add(qualityTab, "Quality");
    qualityTab.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(qFlowScroolPane, new GridBagConstraints2(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
    jPanel5.add(addQualB4, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 4, 1, 8), 0, 0));
    jPanel5.add(delQualB4, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 1, 4), 0, 0));
    flowTabPane.add(packTab, "Packaging");
    packTab.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(pFlowList, new GridBagConstraints2(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(4, 4, 4, 4), 0, 0));
    jPanel7.add(addQualB5, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 4, 1, 8), 0, 0));
    jPanel7.add(delQualB5, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 1, 4), 0, 0));
    //spec
    jPanel1.add(specP, new GridBagConstraints(2, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 0, 5, 5), 0, 0));
    addStdMfgCertB.setText("Add a Std Mfg Cert");
    addStdMfgCertB.setMaximumSize(new Dimension(125, 21));
    addStdMfgCertB.setMinimumSize(new Dimension(125, 21));
    addStdMfgCertB.setPreferredSize(new Dimension(125, 21));
    addStdMfgCertB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addStdMfgCertB_actionPerformed(e);
      }
    });
    addSpecB.setText("Add a new Spec");
    addSpecB.setMaximumSize(new Dimension(125, 21));
    addSpecB.setMinimumSize(new Dimension(125, 21));
    addSpecB.setPreferredSize(new Dimension(125, 21));
    addSpecB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addSpecB_actionPerformed(e);
      }
    });
    specButtonsP.setLayout(gridBagLayoutSpecButton);
    specButtonsP.add(addStdMfgCertB, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    specButtonsP.add(addSpecB, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    specP.getViewport().add(specButtonsP,null);
    //end of spec

    typeP.setBorder(ClientHelpObjs.groupBox("Ordering Type"));
    shelfP.setBorder(ClientHelpObjs.groupBox("Shelf life @ Storage Temperature "));
    flowP.setBorder(ClientHelpObjs.groupBox("Flowdown"));
    specP.setBorder(ClientHelpObjs.groupBox("Specification"));
    specP.setToolTipText("Right mouse click to add/delete Spec.");

    typeGroup.add(oorR);
    typeGroup.add(mmR);
    oorR.setSelected(true);
    setQtyText(oorR.isSelected());    //trong 3/30

    //default to manufacturer's recommended
    //this.setShelfLifeStorageTempOption("Mfg");

    //Colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    flowTabPane.setSelectedIndex(0);
  } //end of method

  public void allowEdit(boolean f) {
    SpecPane sp = (SpecPane)specTab.getSelectedComponent();
    if (sp != null) {
      sp.allowEdit(true);
    }
    addSpecB.setEnabled(f);
    addStdMfgCertB.setEnabled(f);
    catalogC.setEnabled(f);
    partNumT.setEnabled(f);
    replacePartNumT.setEnabled(f);
    oorR.setEnabled(f);
    mmR.setEnabled(f);
    initQtyT.setEnabled(f);
    pFlowList.setEnabled(f);
    qFlowList.setEnabled(f);
    addQualB4.setEnabled(f);
    delQualB4.setEnabled(f);
    addQualB5.setEnabled(f);
    delQualB5.setEnabled(f);
    partDesc.setEnabled(f);
    manageL.setEnabled(f);
    allowEditShelfLifeNStorageOverride(f);
    consumableRB.setEnabled(f);
    nonConsumableRB.setEnabled(f);
  } //end of method

  public void allowEditShelfLifeNStorageOverride(boolean f) {
    if (cigRestricted) {
      f = false;
    }
    mfgRB.setEnabled(f);
    userRB.setEnabled(f);
    sameShelfLifeRB.setEnabled(f);
    separateShelfLifeRB.setEnabled(f);
    if (userRB.isSelected() || cigRestricted) {
      if (sameShelfLifeRB.isSelected()) {
        shelfLifeStorageTempPanel.allowEdit(f);
      }else {
        for (int i = 0; i < shelfLifeTabPane.getComponentCount(); i++) {
          ShelfLifeStorageTempPane sstp = (ShelfLifeStorageTempPane)shelfLifeTabPane.getComponentAt(i);
          sstp.allowEdit(f);
        }
      }
    }
  } //end of method

  //trong 3/30/00
  public void setEvalflag() {
    jLabel4.setText("Initial Order Quantity: *");
    mmR.setEnabled(false);
    mmR.setSelected(false);
    oorR.setSelected(true);
    //jLabel5.setEnabled(false);
    //unit2L.setEnabled(false);
    jLabel2.setEnabled(false);
    partNumT.setEnabled(false);
    jLabel3.setEnabled(false);
    replacePartNumT.setEnabled(false);
    myEval = true;
    //jLabel4.setForeground(Color.blue);
  }
  //5-13-01
  public void setEvalflagNotRequired() {
    jLabel4.setText("Initial Order Quantity: ");
    mmR.setEnabled(false);
    mmR.setSelected(false);
    oorR.setSelected(true);
    //jLabel5.setEnabled(false);
    //unit2L.setEnabled(false);
    jLabel2.setEnabled(false);
    partNumT.setEnabled(false);
    jLabel3.setEnabled(false);
    replacePartNumT.setEnabled(false);
    myEval = true;
    //jLabel4.setForeground(Color.blue);
  }


  public String auditQty() {
    String msg = null;
    String num = initQtyT.getText();
    try {
      Integer n = new Integer(num);
      if (n.intValue() <= 0) {
        msg = "Please enter only positive integer \n for " + jLabel4.getText() + ".";
        return msg;
      }
    } catch(Exception e) {
        msg = "Please enter only numeric integer \n for " + jLabel4.getText() + ".";
        e.printStackTrace();
        return msg;
    }
    return msg;
  }

  public boolean isEngEval(){
    return myEval;
  }
  public void setEngEval(boolean b){
    this.myEval = b;
  }
  public void setCatalogId(String s) {
    this.catalogId = s;
  }


  public void setDynData(Hashtable scrData) {
    dyn = scrData;
  }
  public void setUserId(String user) {
    userId = user;
  }

  //trong 11-12-00
  public void printScreenData() {

  }

  void catalogC_actionPerformed(ActionEvent e) {
    String tmpCatalogID = getCatalogID();
    displayCatalogPartConsumableOption(tmpCatalogID);
  }

  void displayCatalogPartCategoryOption() {
    jPanel1.remove(categoryStatusB);
    jPanel1.remove(categoryStatusL);
    jPanel1.remove(categoryB);
    categoryStatusB.setText(categoryStatusForButton);
    jPanel1.add(categoryStatusB, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 0), 0, 0));
    jPanel1.add(categoryB, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 165, 0, 10), 0, 0));
    this.repaint();
  } //end of method

  void partNumberCommentB_actionPerformed(ActionEvent e) {
    if (nchem.view == nchem.PROTECT_VIEWER) {
      if (partNumberComments.length() < 1) {
        GenericDlg.showMessage("Part Number Comments is empty.");
      }else {
        GenericDlg.showMessage(partNumberComments);
      }
    }else {
      EditDlg RjW = new EditDlg(cmis.getMain(),"Edit/New Comments",true, "Enter Comments:");
      RjW.setText(partNumberComments);
      RjW.setVisible(true);
      //add new comment to table
      if(!BothHelpObjs.isBlankString(RjW.getText())){
        partNumberComments = RjW.getText();
      }
      RjW.dispose();
    }
  } //end of method

  void categoryB_actionPerformed(ActionEvent e) {
    CategoryDlg cDlg = new CategoryDlg(cmis,"Select a Category",true,selectedCategory,categoryDelimiter,categoryEditable,selectedCategoryComment);
    cDlg.loadIt();
    cDlg.setVisible(true);
    if (categoryEditable) {
      if (cDlg.okFlag) {
        boolean categoryChanged = true;
        Collection tempCategory = cDlg.getSelectedCategory();
        selectedCategoryComment = cDlg.getCategoryComments();
        if (categoryChanged) {
          selectedCategory = tempCategory;
          nchem.saveData(false);
        }
      } //end of okFlag
    }
  }

  void categoryStatusB_actionPerformed(ActionEvent e) {
    DisplayTextDlg dlg = new DisplayTextDlg(cmis.getMain(),"Reasons");
    dlg.setMsg(categoryStatus);
    dlg.setVisible(true);
  }

  public String auditSelectedCategory() {
    String msg = null;
    if (selectedCategory.size() < 1) {
      msg = "Please select a category.";
    }
    return msg;
  }

  String getCategoryDelimiter() {
    return categoryDelimiter;
  }

  void setCategoryDelimiter(String s) {
    categoryDelimiter = s;
  }

  Collection getSelectedCategory() {
    return selectedCategory;
  }

  void setSelectedCategory(Collection col) {
    selectedCategory = col;
  }

  Hashtable getSelectedCategoryComment() {
    return selectedCategoryComment;
  }

  void setSelectedCategoryComment(Hashtable h) {
    selectedCategoryComment = h;
  }

  boolean getCategoryOption() {
    return categoryOption;
  }

  void setCategoryOption(boolean b) {
    categoryOption = b;
  }

  void setCategoryStatus(String s) {
    categoryStatus = s;
  }

  void setCategoryStatusForButton(String s) {
    categoryStatusForButton = s;
  }

  String getCategoryStatus() {
    return categoryStatus;
  }


  void consumableRB_actionPerformed(ActionEvent e) {
    if (consumableRB.isSelected()) {
      consumableRB.setSelected(true);
      nonConsumableRB.setSelected(false);
    }else {
      consumableRB.setSelected(true);
      nonConsumableRB.setSelected(false);
    }
  }

  void nonConsumableRB_actionPerformed(ActionEvent e) {
    if (nonConsumableRB.isSelected()) {
      consumableRB.setSelected(false);
      nonConsumableRB.setSelected(true);
    }else {
      consumableRB.setSelected(false);
      nonConsumableRB.setSelected(true);
    }
  }

  void displayCatalogPartConsumableOption(String tmpCatalogID) {
    if (nchem.catalogConsumableOptionH.containsKey(tmpCatalogID)) {
      if ("Y".equalsIgnoreCase( (String) nchem.catalogConsumableOptionH.get(tmpCatalogID))) {
        jPanel1.add(consumableRB, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 18, 0, 0), 0, 0));
        jPanel1.add(nonConsumableRB, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 115, 0, 10), 0, 0));
      }
    }
  } //end of method

  public void setConsumableOption(String consumableOption) {
    String tmpCatalogID = getCatalogID();
    if (nchem.catalogConsumableOptionH.containsKey(tmpCatalogID)) {
      if ("Y".equalsIgnoreCase( (String) nchem.catalogConsumableOptionH.get(tmpCatalogID))) {
        if ("Y".equalsIgnoreCase(consumableOption)) {
          consumableRB.setSelected(true);
          nonConsumableRB.setSelected(false);
        }else {
          consumableRB.setSelected(false);
          nonConsumableRB.setSelected(true);
        }
      }
    }
  } //end of method

  public String getConsumableOption() {
    String result = "N";
    String tmpCatalogID = getCatalogID();
    if (nchem.catalogConsumableOptionH.containsKey(tmpCatalogID)) {
      if ("Y".equalsIgnoreCase( (String) nchem.catalogConsumableOptionH.get(tmpCatalogID))) {
        if (consumableRB.isSelected()) {
          result = "Y";
        }else {
          result = "N";
        }
      }
    }
    return result;
  } //end of method

  public Hashtable getFacAppForSelectedCatalog() {
    Hashtable catFacAppInfo = (Hashtable)dyn.get("CATALOG_FAC_WA_INFO");
    return ((Hashtable)catFacAppInfo.get(getCatalogID()));
  }
  String getCatalogID() {
    Hashtable catFacAppInfo = (Hashtable)dyn.get("CATALOG_FAC_WA_INFO");
    Vector v = (Vector)catFacAppInfo.get("CATALOG_ID");
    return ((String)v.elementAt(catalogC.getSelectedIndex()));
  }

  String getCatalogCompanyId() {
    Hashtable catFacAppInfo = (Hashtable)dyn.get("CATALOG_FAC_WA_INFO");
    Vector v = (Vector)catFacAppInfo.get("CATALOG_COMPANY_ID");
    return ((String)v.elementAt(catalogC.getSelectedIndex()));
  }


  public void selectCatalogID(String catalogID) {
    Hashtable catFacAppInfo = (Hashtable)dyn.get("CATALOG_FAC_WA_INFO");
    Vector v = (Vector)catFacAppInfo.get("CATALOG_ID");
    catalogC.setSelectedIndex(v.indexOf(catalogID));
  }
  public void addAndSelectCatalog(String catalogID) {
    if (BothHelpObjs.isBlankString(catalogID)) {
      catalogC.setSelectedIndex(0);
    }else {
      Hashtable catFacAppInfo = (Hashtable) dyn.get("CATALOG_FAC_WA_INFO");
      Vector v = (Vector) catFacAppInfo.get("CATALOG_ID");
      if (!v.contains(catalogID)) {
        v.addElement(catalogID);
        Vector descV = (Vector) catFacAppInfo.get("CATALOG_DESC");
        descV.addElement(catalogID);
      }
      catalogC.setSelectedIndex(v.indexOf(catalogID));
    }
  }

  public  void setPane(){
    /*
    for (int i=0;i<specsNum.intValue();i++){
      SpecPane dummy = new SpecPane(false);
      dummy.setCmis(nchem.getCmis());
      specTab.addTab("Spec "+(i+1),dummy);
    }*/
    //default to manufacturer's recommended
    this.setShelfLifeStorageTempOption("Mfg");

    jPanel1.remove(catalogC);
    Hashtable catFacAppInfo = (Hashtable)dyn.get("CATALOG_FAC_WA_INFO");
    catalogC = ClientHelpObjs.loadChoiceFromVector(catalogC,(Vector)catFacAppInfo.get("CATALOG_DESC"));
    catalogC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        catalogC_actionPerformed(e);
      }
    });
    catalogC.revalidate();
    jPanel1.add(catalogC, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 65, 0, 10), 0, 0));
    jPanel1.validate();
  }   //end of method

  public void setNewChem(NewChem n){
     nchem = n;
  }

  public void setSpecsNum(Integer i){
    this.specsNum = i;
  }

  //trong 11-9-00
  public void setPeriod(Vector p){
    shelfLifePeriodV.removeAllElements();
    for (int i = 0; i < p.size(); i++) {
      shelfLifePeriodV.addElement((String)p.elementAt(i));
    }
    shelfLifePeriodV.trimToSize();
  }

  public void setUnitText(String t){
    unit1L.setText(t);
    unit2L.setText(t);
  }

  public void setTemperature(Vector p){
    temperatureRangeV.removeAllElements();
    for (int i = 0; i < p.size(); i++) {
      temperatureRangeV.addElement((String)p.elementAt(i));
    }
    temperatureRangeV.trimToSize();
  } //end of method

  public void setLabelColor(Vector p){
    labelColorV.removeAllElements();
    for (int i = 0; i < p.size(); i++) {
      labelColorV.addElement((String)p.elementAt(i));
    }
    labelColorV.trimToSize();
  } //end of method

  public void addSpecB_actionPerformed(ActionEvent e){
    addSpec();
  }

  public void addStdMfgCertB_actionPerformed(ActionEvent e){
    addStandardMfgCert();
  }

  public void addStandardMfgCert(){
     if (specTab.getTabCount() == 0) {
       specP.getViewport().remove(specButtonsP);
       specP.getViewport().add(specTab, null);
     }
     SpecPane dummy = new SpecPane(true);
     dummy.setCmis(nchem.getCmis());
     dummy.setItemID(nchem.getItemID());
     dummy.setSpecID("Std Mfg Cert");
     specTab.insertTab("SMC",null,dummy,"",0);
     //rename other specs
     int numberOfSpec = 0;
     for (int i=0;i<specTab.getTabCount();i++){
       if (!"SMC".equalsIgnoreCase(specTab.getTitleAt(i))) {
         specTab.setTitleAt(i, "Spec " + (++numberOfSpec));
       }
     }
     specTab.setSelectedIndex(0);
  } //end of method

  public void addSpec(){
     if (specTab.getTabCount() == 0) {
       specP.getViewport().remove(specButtonsP);
       specP.getViewport().add(specTab, null);
     }
     SpecPane dummy = new SpecPane(false);
     dummy.setCmis(nchem.getCmis());
     dummy.setItemID(nchem.getItemID());
     dummy.setSpecID("");
     //find of of specs
     int numberOfSpec = 0;
     for (int i=0;i<specTab.getTabCount();i++){
       if (!"SMC".equalsIgnoreCase(specTab.getTitleAt(i))) {
         numberOfSpec++;
       }
     }
     specTab.addTab("Spec "+(numberOfSpec+1),dummy);
     specTab.setSelectedIndex(specTab.getTabCount()-1);
  }

  public void deleteSpec(){
     specTab.removeTabAt(specTab.getSelectedIndex());
     //rename other specs
     int numberOfSpec = 0;
     for (int i=0;i<specTab.getTabCount();i++){
       if (!"SMC".equalsIgnoreCase(specTab.getTitleAt(i))) {
         specTab.setTitleAt(i, "Spec " + (++numberOfSpec));
       }
     }
     //display add buttons if all specs and cert was removed
     if (specTab.getTabCount() == 0) {
       specP.getViewport().remove(specTab);
       specP.getViewport().add(specButtonsP,null);
     }
  }

  void specTab_mouseClicked(MouseEvent e) {
    if (e.isMetaDown() && ((SpecPane)specTab.getComponentAt(0)).specIdT.isEnabled()) {
       PartPane_MetaPop pop = new PartPane_MetaPop(this);
       pop.show(this.specP,e.getX(),e.getY());
    }
  }

  void catalogCLoaded(){
    catalogCLoaded = true;
  }

  public  boolean isCatalogCLoaded(){
    return catalogCLoaded;
  }

  public Vector getQualityFlow(){
    Vector v = new Vector();
    for (int i=0;i<qFlowList.getModel().getSize();i++){
       v.addElement((String)qFlowList.getModel().getElementAt(i));
    }
    return v;
  }

  public Vector getPackagingFlow(){
    Vector v = new Vector();
    for (int i=0;i<pFlowList.getModel().getSize();i++){
       v.addElement((String)pFlowList.getModel().getElementAt(i));
    }
    return v;
  }

  public boolean isLoadDone(){
    if(dataLoaded && catalogCLoaded){
      return true;
    }
    return false;
  }

  public JTabbedPane getSpecTabs(){
    return this.specTab;
  }

  public Vector getSpecs(){
    Vector v = new Vector(getSpecTabs().getTabCount());
    for (int i=0;i<getSpecTabs().getTabCount();i++){
       Hashtable spec = new Hashtable();
       spec.put("SPEC_ID",((SpecPane)getSpecTabs().getComponentAt(i)).getSpecID());
       spec.put("NAME",((SpecPane)getSpecTabs().getComponentAt(i)).specIdT.getText());
       spec.put("REVISION",((SpecPane)getSpecTabs().getComponentAt(i)).specRevT.getText());
       spec.put("AMENDMENT",((SpecPane)getSpecTabs().getComponentAt(i)).specAmenT.getText());
       spec.put("TITLE",((SpecPane)getSpecTabs().getComponentAt(i)).specTitleT.getText());
       spec.put("COC",new Boolean(((SpecPane)getSpecTabs().getComponentAt(i)).coc.isSelected()));
       spec.put("COA",new Boolean(((SpecPane)getSpecTabs().getComponentAt(i)).coa.isSelected()));
       spec.put("SPEC_LIBRARY",((SpecPane)getSpecTabs().getComponentAt(i)).getSpecLibrary());
       v.addElement(spec);
    }
    //no specification
    if (v.size() == 0) {
      Hashtable spec = new Hashtable();
      spec.put("SPEC_ID","No Specification");
      spec.put("NAME","");
      spec.put("REVISION","");
      spec.put("AMENDMENT","");
      spec.put("TITLE","");
      spec.put("COC",new Boolean(false));
      spec.put("COA",new Boolean(false));
      spec.put("SPEC_LIBRARY","");
      v.addElement(spec);
    }
    return v;
  }

  //trong 5/5
  void addVectorToQFlowList(Vector f){
     DefaultListModel dlm = (DefaultListModel) qFlowList.getModel();
     for (int i = 0; i < f.size(); i++){
        String n = (String)f.elementAt(i);
        if (dlm.contains(n)) continue;
        dlm.addElement(n);
     }
     //trong 3/20
     if (qFlowList.getFirstVisibleIndex() > -1)
      catalogC.setEnabled(false);
  }

  void addHashToQFlowList(Hashtable h){
     Enumeration E;
     DefaultListModel dlm = (DefaultListModel) qFlowList.getModel();
     for (E=h.keys();E.hasMoreElements();){
        String n = (String) E.nextElement();
        if (dlm.contains(n)) continue;
        dlm.addElement(n);
     }
     //trong 3/20
     if (qFlowList.getFirstVisibleIndex() > -1)
      catalogC.setEnabled(false);
  }
  void addHashToPFlowList(Hashtable h){
     Enumeration E;
     DefaultListModel dlm = (DefaultListModel) pFlowList.getModel();
     for (E=h.keys();E.hasMoreElements();){
        String n = (String) E.nextElement();
        if (dlm.contains(n)) continue;
        dlm.addElement(n);
     }
     //trong 3/20
     if (pFlowList.getFirstVisibleIndex() > -1)
      catalogC.setEnabled(false);

  }

  void addQualB4_actionPerformed(ActionEvent e) {
    SearchFlowDlg ncfd = new SearchFlowDlg(nchem.getCmis().getMain(),true);
    ncfd.setGrandParent(nchem.getCmis());
    String requestor=(String) dyn.get("REQUESTOR");
    if (!requestor.equalsIgnoreCase(userId)) {
      ncfd.setCatalog(dyn.get("CATALOG_ID").toString()); //trong 3/20
      ncfd.flowTypeCombo.setSelectedIndex(flowTabPane.getSelectedIndex());
    } else {
      //ncfd.setCatalog((String)catalogC.getSelectedItem()); //trong  3-14-01
      ncfd.setCatalog(getCatalogID()); //4-10-02
      ncfd.flowTypeCombo.setSelectedIndex(flowTabPane.getSelectedIndex());
    }
    ncfd.setVisible(true);
    addHashToQFlowList(ncfd.getFlowDown());
  }

  void addQualB5_actionPerformed(ActionEvent e) {
    SearchFlowDlg ncfd = new SearchFlowDlg(nchem.getCmis().getMain(),true);
    ncfd.setGrandParent(nchem.getCmis());
    String requestor=(String) dyn.get("REQUESTOR");
    if (!requestor.equalsIgnoreCase(userId)) {
      ncfd.setCatalog(dyn.get("CATALOG_ID").toString()); //trong 3/20
      ncfd.flowTypeCombo.setSelectedIndex(flowTabPane.getSelectedIndex());
    } else {
      //ncfd.setCatalog((String)catalogC.getSelectedItem()); //3-14-02
      ncfd.setCatalog(getCatalogID()); //4-10-02
      ncfd.flowTypeCombo.setSelectedIndex(flowTabPane.getSelectedIndex());
    }
    ncfd.setVisible(true);
    addHashToQFlowList(ncfd.getFlowDown());
  }

  // trong 3/20 packaging
  void delQualB5_actionPerformed(ActionEvent e) {
     int del = pFlowList.getSelectedIndex();
     if (del < 0) {
      GenericDlg.showMessage("Please select a line from flowdown");
      return;
     }
     //remove selected row
     ((DefaultListModel) pFlowList.getModel()).removeElementAt(del);

     pFlowList.revalidate();
     //if there are no more flowdown, allow user to choose another catalog
     if (pFlowList.getFirstVisibleIndex() < 0)
        catalogC.setEnabled(true);
  }

  // trong 3/20  quality
  void delQualB4_actionPerformed(ActionEvent e) {
     int del = qFlowList.getSelectedIndex();
     if (del < 0) {
      GenericDlg.showMessage("Please select a line from flowdown");
      return;
     }
     //remove selected row
     ((DefaultListModel) qFlowList.getModel()).removeElementAt(del);
     qFlowList.revalidate();

     //if there are no more flowdown, allow user to choose another catalog
     if (qFlowList.getFirstVisibleIndex() < 0)
      catalogC.setEnabled(true);
  }

  void oorR_mouseClicked(MouseEvent e) {
     setQtyText(oorR.isEnabled());
  }

  void mmR_mouseClicked(MouseEvent e) {
     setQtyText(oorR.isEnabled());
  }

  void setQtyText(boolean oor){
     if (oorR.isSelected() && myEval){
      //trong 3/20
      jLabel4.setText("Initial Order Quantity: *");
     } else {
      //trong 3/20
      jLabel4.setText("Estimated Quarterly Usage Rate: *");
     }
  }

  void sameShelfLifeRB_actionPerformed(ActionEvent e) {
    sameShelfLifeRB.setSelected(true);
    separateShelfLifeRB.setSelected(false);
    shelfLifePanel.setVisible(true);
    shelfLifeTabPane.setVisible(false);
    showShelfLifeStoragePanel();
  }

  void separateShelfLifeRB_actionPerformed(ActionEvent e) {
    sameShelfLifeRB.setSelected(false);
    separateShelfLifeRB.setSelected(true);
    shelfLifePanel.setVisible(false);
    shelfLifeTabPane.setVisible(true);
    showShelfLifeStorageTab();
  }

  void mfgRB_actionPerformed(ActionEvent e) {
    if (mfgRB.isSelected()) {
      mfgRB.setSelected(true);
      userRB.setSelected(false);
    }else {
      mfgRB.setSelected(true);
      userRB.setSelected(false);
    }
    showUserOverRide(false);
  }

  void userRB_actionPerformed(ActionEvent e) {
    if (userRB.isSelected()) {
      userRB.setSelected(true);
      mfgRB.setSelected(false);
    }else {
      userRB.setSelected(true);
      mfgRB.setSelected(false);
    }
    showUserOverRide(true);
  }

  void showUserOverRide(boolean flag) {
    manageL.setVisible(flag);
    sameShelfLifeRB.setVisible(flag);
    separateShelfLifeRB.setVisible(flag);
    //if user override
    if (flag) {
      if (separateShelfLifeRB.isSelected()) {
        shelfLifePanel.setVisible(false);
        shelfLifeTabPane.setVisible(true);
        showShelfLifeStorageTab();
      }else {
        shelfLifePanel.setVisible(true);
        shelfLifeTabPane.setVisible(false);
        showShelfLifeStoragePanel();
      }
    }else {
      shelfLifePanel.setVisible(false);
      shelfLifeTabPane.setVisible(false);
    }
  } //end of method

  void showShelfLifeStorageTab() {
    int tmpVal = numberOfComponent - shelfLifeTabPane.getComponentCount();
    //if the number of component in section 1 is greater than add missing components to shelf life tab
    if (tmpVal > 0) {
      for (int i = 0; i < tmpVal; i++) {
        ShelfLifeStorageTempPane dummy = new ShelfLifeStorageTempPane(showLabelColor);
        dummy.setCmis(nchem.getCmis());
        dummy.setPeriod(shelfLifePeriodV);
        dummy.setTemperature(temperatureRangeV);
        if (showLabelColor) {
          dummy.setLabelColor(labelColorV);
        }
        dummy.setDataLoaded(true);
        shelfLifeTabPane.addTab(""+(shelfLifeTabPane.getComponentCount()+1),dummy);
      }
    }
    shelfLifeTabPane.setFont(userRB.getFont());
  } //end of method

  void showShelfLifeStoragePanel() {
    if (!shelfLifeStorageTempPanel.dataLoaded) {
      shelfLifeStorageTempPanel.setCmis(nchem.getCmis());
      shelfLifeStorageTempPanel.setPeriod(shelfLifePeriodV);
      shelfLifeStorageTempPanel.setTemperature(temperatureRangeV);
      if (showLabelColor) {
        shelfLifeStorageTempPanel.setLabelColor(labelColorV);
      }
      shelfLifeStorageTempPanel.setDataLoaded(true);
      shelfLifeStorageTempPanel.showBasis();
    }
  } //end of method

  void loadShelfLifeStorage(Hashtable hash) {
    if (userRB.isSelected() || cigRestricted) {
      if ("Y".equalsIgnoreCase((String) hash.get("SAME_SHELF_LIFE_STORAGE_TEMP"))) {
        sameShelfLifeRB.setSelected(true);
        separateShelfLifeRB.setSelected(false);
      }else {
        sameShelfLifeRB.setSelected(false);
        separateShelfLifeRB.setSelected(true);
      }
      showUserOverRide(true);
    }
  } //end of method

  String auditShelfLife() {
    String msg = null;
    if (userRB.isSelected()) {
     if (sameShelfLifeRB.isSelected()) {
       msg = auditSameShelfLife();
     }else {
       msg = auditSeparateShelfLife();
     }
    }
    return msg;
  } //end of method

  String auditSeparateShelfLife() {
    String msg = null;
    for (int i = 0; i < shelfLifeTabPane.getComponentCount(); i++) {
      ShelfLifeStorageTempPane sstp = (ShelfLifeStorageTempPane)shelfLifeTabPane.getComponentAt(i);
      msg = sstp.auditShelfLife();
      if (msg != null) {
        break;
      }
    }
    return msg;
  } //end of method

  String auditSameShelfLife() {
    return shelfLifeStorageTempPanel.auditShelfLife();
  } //end of method

  String auditTemp() {
    String msg = null;
    if (userRB.isSelected()) {
     if (sameShelfLifeRB.isSelected()) {
       msg = auditSameTemp();
     }else {
       msg = auditSeparateTemp();
     }
    }
    return msg;
  } //end of method

  String auditSeparateTemp() {
    String msg = null;
    for (int i = 0; i < shelfLifeTabPane.getComponentCount(); i++) {
      ShelfLifeStorageTempPane sstp = (ShelfLifeStorageTempPane)shelfLifeTabPane.getComponentAt(i);
      msg = sstp.auditTemp();
      if (msg != null) {
        break;
      }
    }
    return msg;
  } //end of method

  String auditSameTemp() {
    return shelfLifeStorageTempPanel.auditTemp();
  } //end of method

  String auditLabelColor() {
    String msg = null;
    if (userRB.isSelected()) {
     if (sameShelfLifeRB.isSelected()) {
       msg = auditSameLabelColor();
     }else {
       msg = auditSeparateLabelColor();
     }
    }
    return msg;
  } //end of method

  String auditSeparateLabelColor() {
    String msg = null;
    for (int i = 0; i < shelfLifeTabPane.getComponentCount(); i++) {
      ShelfLifeStorageTempPane sstp = (ShelfLifeStorageTempPane)shelfLifeTabPane.getComponentAt(i);
      msg = sstp.auditLabelColor();
      if (msg != null) {
        break;
      }
    }
    return msg;
  } //end of method

  String auditSameLabelColor() {
    return shelfLifeStorageTempPanel.auditLabelColor();
  } //end of method


  void saveShelfLifeStorageTemp(Hashtable matHash) {
    if (userRB.isSelected() || cigRestricted) {
      if (sameShelfLifeRB.isSelected()) {
        saveSameShelfLifeStorageTemp(matHash);
      }else {
        saveSeparateShelfLifeStorageTemp(matHash);
      }
    }
  } //end of method

  void saveSeparateShelfLifeStorageTemp(Hashtable matHash) {
    for (int i = 0; i < shelfLifeTabPane.getComponentCount(); i++) {
      ShelfLifeStorageTempPane sstp = (ShelfLifeStorageTempPane)shelfLifeTabPane.getComponentAt(i);
      sstp.saveShelfLifeStorageTemp((Hashtable) matHash.get("KIT_"+i));
    }
  } //end of method

  void saveSameShelfLifeStorageTemp(Hashtable matHash) {
    for (int i = 0; i < numberOfComponent; i++) {
      shelfLifeStorageTempPanel.saveShelfLifeStorageTemp((Hashtable)matHash.get("KIT_"+i));
    }
  } //end of method

  void setDefaultShelfLifeStorageTemp(Hashtable hash) {
    if (userRB.isSelected() || cigRestricted) {
      if ("Y".equalsIgnoreCase((String) hash.get("SAME_SHELF_LIFE_STORAGE_TEMP"))) {
        setSameDefaultShelfLifeStorageTemp(hash);
      }else {
        setSeparateDefaultShelfLifeStorageTemp(hash);
      }
    }
  } //end of method

  void setSeparateDefaultShelfLifeStorageTemp(Hashtable hash) {
    for (int i = 0; i < shelfLifeTabPane.getComponentCount(); i++) {
      ShelfLifeStorageTempPane sstp = (ShelfLifeStorageTempPane)shelfLifeTabPane.getComponentAt(i);
      sstp.setDefaultShelfLifeStorageTemp((Hashtable) hash.get("ITEM_"+i));
    }
  } //end of method

  void setSameDefaultShelfLifeStorageTemp(Hashtable hash) {
    shelfLifeStorageTempPanel.setDefaultShelfLifeStorageTemp((Hashtable) hash.get("ITEM_"+0));
  } //end of method

  void setShelfLifeStorageTempOption(String s) {
    if ("Mfg".equalsIgnoreCase(s)) {
      mfgRB.setSelected(true);
      userRB.setSelected(false);
      if (cigRestricted) {
        showUserOverRide(true);
      }else {
        showUserOverRide(false);
      }
    }else {
      userRB.setSelected(true);
      mfgRB.setSelected(false);
      showUserOverRide(true);
    }
  } //end of method
} //end of class

class PartPane_MetaPop extends JPopupMenu {

    PartPane parent = null;
    JMenuItem addM = new JMenuItem("Add a new Spec");
    JMenuItem deleteM = new JMenuItem("Delete Cert/Spec");
    JMenuItem addStdMfgCertM = new JMenuItem("Add a Std Mfg Cert");

    public PartPane_MetaPop(PartPane p){
       super("Add / Delete Parts");
       parent = p;
       add(addM);
       add(deleteM);
       addM.setActionCommand("Add");
       deleteM.setActionCommand("Del");
       add(addStdMfgCertM);
       addStdMfgCertM.setActionCommand("StdMfgCert");
       PartPane_MetaActionListener mal = new PartPane_MetaActionListener(parent);
       addM.addActionListener(mal);
       deleteM.addActionListener(mal);
       addStdMfgCertM.addActionListener(mal);
       addM.setEnabled(true);
       deleteM.setEnabled(true);
       boolean containStdMfgCert = false;
       for (int i=0;i<parent.specTab.getTabCount();i++){
         if ("SMC".equalsIgnoreCase(parent.specTab.getTitleAt(i))) {
           containStdMfgCert = true;
         }
       }
       addStdMfgCertM.setEnabled(!containStdMfgCert);
    } //end of method
} //end of class

class PartPane_MetaActionListener implements ActionListener {
  PartPane parent = null;

  public PartPane_MetaActionListener(PartPane p){
      parent = p;
  }

  public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().toString().startsWith("Add")) {
        parent.addSpec();
      } else if (e.getActionCommand().toString().equalsIgnoreCase("Del")) {
        parent.deleteSpec();
      } else if (e.getActionCommand().toString().equalsIgnoreCase("StdMfgCert")) {
        parent.addStandardMfgCert();
      }
  }
}
