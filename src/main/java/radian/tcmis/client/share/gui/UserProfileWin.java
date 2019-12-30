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
import java.io.*;
import java.text.SimpleDateFormat;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.gui.admin.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.* ;
import java.beans.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class UserProfileWin extends JPanel implements FilenameFilter{
  private CmisApp parent;
  private boolean personalChanged = false;
  private boolean loading = true;
  private int uid = 0;
  private String pFac = "";
  boolean newUser = false;
  private String selectedFac = "";
  private ImageIcon ss = new ImageIcon();
  private Vector adminV = new Vector();
  private Vector newChemV = new Vector();
  private Vector managerV = new Vector();
  private Vector reportV = new Vector();
  private Vector costReportV = new Vector();
  private Vector releasersV = new Vector();
  private Hashtable approversH = new Hashtable(); //hastable and amt
  private Hashtable facXref;
  private Vector myFacilityIDV = new Vector();
  private Hashtable myFacilityActiveList = new Hashtable();

  //labels, buttons and text fields
  //right side
  private StaticJLabel label4 = new StaticJLabel();
  private JTextField fnameT = new JTextField();
  private StaticJLabel label5 = new StaticJLabel();
  private JTextField miT = new JTextField();
  private StaticJLabel label6 = new StaticJLabel();
  private JTextField lnameT = new JTextField();
  private StaticJLabel label3 = new StaticJLabel();
  private JTextField userT = new JTextField();
  private StaticJLabel label20 = new StaticJLabel();
  private StaticJLabel uidL = new StaticJLabel();
  private StaticJLabel label13 = new StaticJLabel();
  private JTextField emailT = new JTextField();
  private StaticJLabel label10 = new StaticJLabel();
  private JTextField phoneT = new JTextField();
  private StaticJLabel label11 = new StaticJLabel();
  private JTextField altphoneT = new JTextField();
  private StaticJLabel label12 = new StaticJLabel();
  private JTextField pagerT = new JTextField();
  private StaticJLabel label14 = new StaticJLabel();
  private JTextField faxT = new JTextField();
  private JButton bPChange = new JButton();
  private JButton addUserB = new JButton();
  private JButton deleteUserB = new JButton();
  private JButton updateB = new JButton();
  private JButton lookupB = new JButton();
  //right side
  private StaticJLabel label2 = new StaticJLabel();
  private JComboBox facC = new JComboBox();
  private ButtonGroup checkboxGroup3 = new ButtonGroup();
  private StaticJLabel label22 = new StaticJLabel();
  private JRadioButton yesPFacC = new JRadioButton();
  private JRadioButton noPFacC = new JRadioButton();
  private JButton facB = new JButton();
  private ButtonGroup bg4 = new ButtonGroup();
  //private JButton delFacB = new JButton();
  private ButtonGroup checkboxGroup2 = new ButtonGroup();
  private StaticJLabel label18 = new StaticJLabel();
  private JRadioButton relYesC = new JRadioButton();
  private JRadioButton relNoC = new JRadioButton();
  private ButtonGroup bg6 = new ButtonGroup();
  private StaticJLabel jLabel3 = new StaticJLabel();
  private JRadioButton ncNoRB = new JRadioButton();
  private JRadioButton ncYesRB = new JRadioButton();
  private JButton useAppGrpB = new JButton();
  private ButtonGroup bg5 = new ButtonGroup();
  private StaticJLabel jLabel2 = new StaticJLabel();
  private JRadioButton adminYesB = new JRadioButton();
  private JRadioButton adminNoB = new JRadioButton();
  private JButton fiAppB = new JButton();
  private ButtonGroup managerBg = new ButtonGroup();
  private StaticJLabel managerOpt = new StaticJLabel();
  private JRadioButton managerNoRB = new JRadioButton();
  private JRadioButton managerYesRB = new JRadioButton();
  private JButton useAppB = new JButton();
  private ButtonGroup reportBg = new ButtonGroup();
  private StaticJLabel jLabel4 = new StaticJLabel();
  private JRadioButton reportRB = new JRadioButton();
  private JRadioButton reportNoRB = new JRadioButton();
  private StaticJLabel costReportL = new StaticJLabel();
  private JRadioButton costReportRB = new JRadioButton();
  private JRadioButton costReportNoRB = new JRadioButton();
  private ButtonGroup costReportBg = new ButtonGroup();

  //panels and layout manager
  private BorderLayout borderLayout2 = new BorderLayout();
  private JPanel jPanel1 = new JPanel();
  private GridBagLayout gridBagLayout12 = new GridBagLayout();
  private JPanel leftPanel = new JPanel();
  private GridLayout gridLayout1 = new GridLayout(1,1,1,1);
  private JPanel jPanel5 = new JPanel();
  private BorderLayout borderLayout5 = new BorderLayout();
  private JPanel groupBox1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JPanel groupBox5 = new JPanel();
  private GridLayout gridLayout4 = new GridLayout(2,2,1,1);
  private JPanel rightPanel = new JPanel();
  private GridLayout gridLayout2 = new GridLayout();
  private JPanel groupBox3 = new JPanel();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
        //work area info
  private JPanel workAreaP = new JPanel();
  private GridBagLayout gridBagLayout33 = new GridBagLayout();
  private JScrollPane leftJSP = new JScrollPane();
  private JScrollPane rightJSP = new JScrollPane();
  private JButton selectAllB = new JButton();
  private JButton clearAllB = new JButton();
  private JButton moveRightB = new JButton();
  private JButton moveLeftB = new JButton();
  private MultiSpanCellTable leftTable;
  private MultiSpanCellTable rightTable;
  private int createMRCol = 2;
  private Hashtable workAreaInfoH;
  private Hashtable workAreaPrefCreateH;


  public UserProfileWin(CmisApp cmis) {
    this.parent = cmis;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void jbInit() throws Exception {
    this.setSize(new Dimension(818, 676));
    this.setLayout(borderLayout2);
    fnameT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        fnameT_propertyChange(e);
      }
    });
    lnameT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        lnameT_propertyChange(e);
      }
    });
    miT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        miT_propertyChange(e);
      }
    });
    userT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        userT_propertyChange(e);
      }
    });
    uidL.setText("-");
    uidL.setFont(new Font("Dialog", 1, 12));

    label22.setText("Preferred:");
    yesPFacC.setText("Yes");
    facC.setMaximumSize(new Dimension(500, 19));
    useAppGrpB.setMaximumSize(new Dimension(140, 23));
    useAppGrpB.setMinimumSize(new Dimension(140, 23));
    useAppGrpB.setPreferredSize(new Dimension(140, 23));
    useAppGrpB.setHorizontalTextPosition(SwingConstants.CENTER);

    fiAppB.setMaximumSize(new Dimension(140, 23));
    fiAppB.setMinimumSize(new Dimension(140, 23));
    fiAppB.setPreferredSize(new Dimension(140, 23));
    fiAppB.setText("Financial Approval");
    fiAppB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fiAppB_actionPerformed(e);
      }
    });
    useAppB.setMaximumSize(new Dimension(140, 23));
    useAppB.setMinimumSize(new Dimension(140, 23));
    useAppB.setPreferredSize(new Dimension(140, 23));
    useAppB.setText("Use Approval");
    useAppB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        useAppB_actionPerformed(e);
      }
    });
    facB.setMaximumSize(new Dimension(90, 21));
    facB.setMinimumSize(new Dimension(90, 21));
    facB.setPreferredSize(new Dimension(90, 21));
    moveRightB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        moveRightB_actionPerformed(e);
      }
    });
    moveLeftB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        moveLeftB_actionPerformed(e);
      }
    });
    selectAllB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selectAllB_actionPerformed(e);
      }
    });
    clearAllB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearAllB_actionPerformed(e);
      }
    });
    jLabel4.setText("tcmIS Reports:");
    reportRB.setText("Yes");
    reportNoRB.setText("No");
    costReportRB.setText("Yes");
    costReportNoRB.setText("No");
    costReportL.setText("Cost Report:");
    reportBg.add(reportRB);
    reportBg.add(reportNoRB);
    reportRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        reportRB_actionPerformed(e);
      }
    });
    reportNoRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        reportNoRB_actionPerformed(e);
      }
    });
    costReportBg.add(costReportRB);
    costReportBg.add(costReportNoRB);
    costReportRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        costReportRB_actionPerformed(e);
      }
    });
    costReportNoRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        costReportNoRB_actionPerformed(e);
      }
    });


    checkboxGroup3.add(yesPFacC);
    yesPFacC.addMouseListener(new UserProfileWin_yesPFacC_mouseAdapter(this));
    noPFacC.setText("No");
    checkboxGroup3.add(noPFacC);
    noPFacC.addMouseListener(new UserProfileWin_noPFacC_mouseAdapter(this));

    ss = ResourceLoader.getImageIcon("images/button/delete.gif","Del");
    label2.setText("Facilities:");
    groupBox1.setLayout(gridBagLayout1);
    groupBox1.setBorder(ClientHelpObjs.groupBox("Personal Information"));
    groupBox3.setLayout(gridBagLayout3);
    groupBox3.setBorder(ClientHelpObjs.groupBox("Profile"));
    label3.setText("Logon ID:");
    label4.setText("First Name:");
    label5.setText("M.I.");
    label6.setText("Last Name:");
    label10.setText("Phone:");
    label11.setText("Alt. Phone:");
    label12.setText("Pager:");
    label13.setText("E-Mail:");
    label14.setText("Fax:");

    facB.setText("Edit List");
    facB.setRolloverEnabled(true);
    //ss = ResourceLoader.getImageIcon("images/button/add_small.gif","Adds");
    //facB.setIcon(ss);
    facB.addActionListener(new UserProfileWin_facB_actionAdapter(this));

    bPChange.setText("Change Password");
    bPChange.setRolloverEnabled(true);
    bPChange.addKeyListener(new UserProfileWin_bPChange_keyAdapter(this));
    bPChange.addActionListener(new UserProfileWin_bPChange_actionAdapter(this));
    ss = ResourceLoader.getImageIcon("images/button/pchange.gif","Change");
    bPChange.setIcon(ss);

    addUserB.setText("New");
    addUserB.setFont(new Font("Dialog", 0, 10));
    addUserB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/add.gif","Add");
    addUserB.setIcon(ss);
    addUserB.addActionListener(new UserProfileWin_addUserB_actionAdapter(this));

    deleteUserB.setText("Delete");
    deleteUserB.setFont(new Font("Dialog", 0, 10));
    deleteUserB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/delete.gif","Delete");
    deleteUserB.setIcon(ss);
    deleteUserB.addActionListener(new UserProfileWin_deleteUserB_actionAdapter(this));

    updateB.setText("Update");
    updateB.setFont(new Font("Dialog", 0, 10));
    updateB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/submit.gif","submit");
    updateB.setIcon(ss);
    updateB.addActionListener(new UserProfileWin_updateB_actionAdapter(this));

    lookupB.setText("Lookup");
    lookupB.setFont(new Font("Dialog", 0, 10));
    lookupB.setRolloverEnabled(true);
    ss = ResourceLoader.getImageIcon("images/button/search_small.gif","search_small");
    lookupB.setIcon(ss);
    lookupB.addActionListener(new UserProfileWin_lookupB_actionAdapter(this));

    ss = ResourceLoader.getImageIcon("images/button/add_small.gif","add_small");
    facC.setPreferredSize(new Dimension(210, 21));
    facC.setMinimumSize(new Dimension(210, 21));
    jLabel3.setText("Create New Chem:");
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    ncYesRB.setText("Yes");
    ncNoRB.setText("No");

    managerOpt.setText("Waste Manager:");
    managerOpt.setHorizontalAlignment(SwingConstants.RIGHT);
    managerYesRB.setText("Yes");
    managerNoRB.setText("No");

    jLabel2.setText("Administrator:");
    adminNoB.setText("No");
    adminYesB.setText("Yes");
    label22.setHorizontalAlignment(SwingConstants.RIGHT);
    useAppGrpB.setText("User Groups");
    useAppGrpB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        useAppGrpB_actionPerformed(e);
      }
    });

    bg5.add(adminYesB);
    bg5.add(adminNoB);
    adminYesB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        adminYesB_actionPerformed(e);
      }
    });
    adminNoB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        adminNoB_actionPerformed(e);
      }
    });
    bg6.add(ncYesRB);
    bg6.add(ncNoRB);
    ncYesRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ncYesRB_actionPerformed(e);
      }
    });
    ncNoRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ncNoRB_actionPerformed(e);
      }
    });

    managerBg.add(managerYesRB);
    managerBg.add(managerNoRB);
    managerYesRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        managerYesRB_actionPerformed(e);
      }
    });
    managerNoRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        managerNoRB_actionPerformed(e);
      }
    });

    altphoneT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        altphoneT_propertyChange(e);
      }
    });
    phoneT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        phoneT_propertyChange(e);
      }
    });
    emailT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        emailT_propertyChange(e);
      }
    });
    pagerT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        pagerT_propertyChange(e);
      }
    });
    faxT.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        faxT_propertyChange(e);
      }
    });

    label18.setText("Releaser:");
    label18.setHorizontalAlignment(SwingConstants.RIGHT);
    relYesC.setText("Yes");
    checkboxGroup2.add(relYesC);
    relYesC.addItemListener(new UserProfileWin_relYesC_itemAdapter(this));
    relNoC.setText("No");
    checkboxGroup2.add(relNoC);
    relNoC.addItemListener(new UserProfileWin_relNoC_itemAdapter(this));
    label20.setText("ID:");
    groupBox5.setLayout(gridLayout4);
    groupBox5.setBorder(ClientHelpObjs.groupBox("User Actions"));
    facC.addItemListener(new UserProfileWin_facC_itemAdapter(this));
    jPanel1.setLayout(gridBagLayout12);

    leftPanel.setLayout(gridLayout1);
    rightPanel.setLayout(gridLayout2);
    this.add(jPanel1, BorderLayout.CENTER);
    //left panel
    jPanel5.setLayout(borderLayout5);
    jPanel5.add(groupBox1, BorderLayout.NORTH);
    groupBox1.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 2, 0, 0), 0, 0));
    groupBox1.add(fnameT, new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 139, 0));
    groupBox1.add(label5, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 3, 0, 0), 0, 0));
    groupBox1.add(miT, new GridBagConstraints2(5, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 6, 0, 7), 16, 0));
    groupBox1.add(label6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 0, 0), 0, 0));
    groupBox1.add(lnameT, new GridBagConstraints(1, 1, 5, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 7), 222, 0));
    groupBox1.add(label3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 0, 0), 0, 0));
    groupBox1.add(userT, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 0), 78, 0));
    groupBox1.add(label20, new GridBagConstraints2(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 17, 0, 0), 0, 0));
    groupBox1.add(uidL, new GridBagConstraints2(3, 3, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 7, 0, 0), 35, 0));
    groupBox1.add(label13, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 0, 0), 0, 0));
    groupBox1.add(emailT, new GridBagConstraints2(1, 5, 6, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 7), 0, 0));
    groupBox1.add(label10, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 0, 0), 0, 0));
    groupBox1.add(phoneT, new GridBagConstraints2(1, 6, 5, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 7), 0, 0));
    groupBox1.add(label11, new GridBagConstraints2(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 0, 0), 0, 0));
    groupBox1.add(altphoneT, new GridBagConstraints2(1, 7, 5, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 7), 0, 0));
    groupBox1.add(label12, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 0, 0), 0, 0));
    groupBox1.add(pagerT, new GridBagConstraints2(1, 8, 5, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 7), 0, 0));
    groupBox1.add(label14, new GridBagConstraints2(0, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 2, 0, 0), 0, 0));
    groupBox1.add(faxT, new GridBagConstraints2(1, 9, 5, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 7), 0, 0));
    groupBox1.add(bPChange, new GridBagConstraints2(0, 10, 6, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(18, 8, 1, 7), 210, 5));

    jPanel5.add(groupBox5, BorderLayout.SOUTH);
    groupBox5.add(deleteUserB);
    groupBox5.add(addUserB);
    groupBox5.add(updateB);
    groupBox5.add(lookupB);
    leftPanel.add(jPanel5);

    //right panel
    groupBox3.add(label2, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    groupBox3.add(facC, new GridBagConstraints2(1, 0, 3, 1, 0.5, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0));
    groupBox3.add(facB, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 0, 0));
    //work area info
    workAreaP.setLayout(gridBagLayout33);
    moveRightB.setMaximumSize(new Dimension(35, 35));
    moveRightB.setMinimumSize(new Dimension(35, 35));
    moveRightB.setPreferredSize(new Dimension(35, 35));
    ss = ResourceLoader.getImageIcon("images/button/next1.gif","move right");
    moveRightB.setIcon(ss);
    moveLeftB.setMaximumSize(new Dimension(35, 35));
    moveLeftB.setMinimumSize(new Dimension(35, 35));
    moveLeftB.setPreferredSize(new Dimension(35, 35));
    ss = ResourceLoader.getImageIcon("images/button/prev1.gif","move left");
    moveLeftB.setIcon(ss);
    selectAllB.setText("Select All");
    selectAllB.setMaximumSize(new Dimension(90, 19));
    selectAllB.setMinimumSize(new Dimension(90, 19));
    selectAllB.setPreferredSize(new Dimension(90, 19));
    clearAllB.setText("Clear All");
    clearAllB.setMaximumSize(new Dimension(80, 19));
    clearAllB.setMinimumSize(new Dimension(80, 19));
    clearAllB.setPreferredSize(new Dimension(80, 19));

    groupBox3.add(workAreaP,  new GridBagConstraints(0, 0, 5, 1, 1.0, 1.0
           ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(30, 3, 0, 0), 0, 0));
    workAreaP.add(leftJSP,  new GridBagConstraints(0, 0, 1, 1, 1.5, 1.0
           ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    workAreaP.add(moveRightB,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
           ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    workAreaP.add(moveLeftB,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
           ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(85, 5, 5, 5), 0, 0));
    workAreaP.add(rightJSP,  new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
           ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 5, 0), 0, 0));
    workAreaP.add(selectAllB,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
           ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
    workAreaP.add(clearAllB,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
           ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));

    String[] colHeads = {"ID","Work Area"};
    AttributiveCellTableModel model = new AttributiveCellTableModel(colHeads,0);
    leftTable = new MultiSpanCellTable(model);
    leftJSP.getViewport().setView(leftTable);
    rightTable = new MultiSpanCellTable(model);
    //rightTable = new MultiSpanCellTable(model);
    rightJSP.getViewport().setView(rightTable);
    //end of work areas info
    groupBox3.add(label22, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    groupBox3.add(yesPFacC, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(noPFacC, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(label18, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    groupBox3.add(relYesC, new GridBagConstraints2(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 0, 0));
    groupBox3.add(relNoC, new GridBagConstraints2(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(ncNoRB, new GridBagConstraints2(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(ncYesRB, new GridBagConstraints2(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 0, 0));
    groupBox3.add(jLabel3, new GridBagConstraints2(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    groupBox3.add(adminYesB, new GridBagConstraints2(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 0, 0));
    groupBox3.add(adminNoB, new GridBagConstraints2(2, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(jLabel2, new GridBagConstraints2(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
    groupBox3.add(managerNoRB, new GridBagConstraints2(2, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(managerYesRB, new GridBagConstraints2(1, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 0, 0));
    groupBox3.add(managerOpt, new GridBagConstraints2(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    groupBox3.add(useAppGrpB, new GridBagConstraints2(3, 5, 2, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    groupBox3.add(fiAppB, new GridBagConstraints2(3, 6, 2, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    groupBox3.add(useAppB, new GridBagConstraints2(3, 7, 2, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    groupBox3.add(reportRB, new GridBagConstraints2(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 0, 0));
    groupBox3.add(reportNoRB, new GridBagConstraints2(2, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(jLabel4, new GridBagConstraints2(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    groupBox3.add(costReportRB, new GridBagConstraints2(1, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 0, 0), 0, 0));
    groupBox3.add(costReportNoRB, new GridBagConstraints2(2, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
    groupBox3.add(costReportL, new GridBagConstraints2(0, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    rightPanel.add(groupBox3, null);
    //putting it all together
    jPanel1.add(leftPanel,  new GridBagConstraints(0, 0, 1, 1, 1.5, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(rightPanel,  new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    ClientHelpObjs.makeDefaultColors(this);
  }

  public void loadIt(){
    UserProfileLoadThread iT = new UserProfileLoadThread(this);
    iT.start();
  }

  public void loadItAction(){
     jPanel1.setVisible(false);
     if (uid == 0){ // first way around
       uid = parent.getUserId().intValue();
       userT.setText(parent.getUserLogon());
     }
     try{
        TcmisHttpConnection cgi = new TcmisHttpConnection("UserProfileNew",parent);
        Hashtable query = new Hashtable();
        query.put("ACTION","LOAD_SCREEN");                 // String, String
        query.put("USER_ID",(new Integer(uid))); // String, Integer
        Hashtable result = cgi.getResultHash(query);
        if (result == null){
           String no = new String("Access denied. Wait a minute and try again.\nIf problem persist, please restart this application.");
           GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
           err.setMsg(no);
           err.setVisible(true);
           return;
        }

        Hashtable tmpH = (Hashtable) result.get("DATA");
        uidL.setText((String)tmpH.get("PERSONNEL_ID"));
        fnameT.setText((String)tmpH.get("FIRST_NAME"));
        lnameT.setText((String)tmpH.get("LAST_NAME"));
        miT.setText((String)tmpH.get("MID_INITIAL"));
        emailT.setText((String)tmpH.get("EMAIL"));
        phoneT.setText((String)tmpH.get("PHONE"));
        pagerT.setText((String)tmpH.get("PAGER"));
        faxT.setText((String)tmpH.get("FAX"));
        altphoneT.setText((String)tmpH.get("ALT_PHONE"));
        String pFactmp = (String)tmpH.get("PREF_FACILITY_ID");
        userT.setText((String)tmpH.get("LOGON_ID"));

        approversH = new Hashtable();
        Vector tmpFacDescV = (Vector) tmpH.get("FACILITY_DESC");
        myFacilityIDV = (Vector) tmpH.get("FACILITY_ID");
        //Facility
        facXref = new Hashtable();
        for (int i=0;i<tmpFacDescV.size(); i++){
          //I am setting the desc as the keys and facility ID as values
          facXref.put(tmpFacDescV.elementAt(i),myFacilityIDV.elementAt(i));
        }
        if (facC.getItemCount() > 0)  facC.removeAllItems();
        facC = ClientHelpObjs.loadChoiceFromVector(facC,tmpFacDescV);
        facC = ClientHelpObjs.choiceSort(facC);
        //determine pref facility
        if (myFacilityIDV.contains(pFactmp)) {
          pFac = (String)tmpFacDescV.elementAt(myFacilityIDV.indexOf(pFactmp));
        }
        facC.setSelectedItem(pFac);
        selectedFac = new String((String)facXref.get(facC.getSelectedItem()));
        //now define work areas info
        workAreaPrefCreateH = (Hashtable) tmpH.get("WORK_AREA_PREF");
        workAreaInfoH = (Hashtable) tmpH.get("WORK_AREA_INFO");
        //load all facilities for administrator, new chem, waste manager, create report and releaser
        adminV = (Vector)tmpH.get("ADMIN");
        newChemV = (Vector)tmpH.get("CREATE_NEW_CHEM");
        managerV = (Vector)tmpH.get("WASTE_MANAGER");
        reportV = (Vector)tmpH.get("CREATE_REPORT");
        myFacilityActiveList = (Hashtable)tmpH.get("FACILITY_ACTIVE_LIST");
        costReportV = (Vector)tmpH.get("COST_REPORT");
        releasersV = (Vector)tmpH.get("RELEASER");

        // Set enable fields
        setSecurity();
        //set approvers and releasers radio
        setLogicalRadio();
        //set work area
        setWorkAreaInfo();
        synchronized (this) {
          loading = false;
        }
     } catch (Exception e) {
        e.printStackTrace();
     }
     jPanel1.setVisible(true);
     this.validate();
     this.repaint();
  } //end of method

  void saveSelectedWorkAreaData() {
    if (rightTable.getRowCount() > 0) {
      //get right table data
      //if (workAreaPrefCreateH.containsKey(selectedFac)) {
        Vector rightDataV = new Vector(rightTable.getRowCount());
        for (int k = 0; k < rightTable.getRowCount(); k++) {
          Object[] oa = new Object[3];
          oa[0] = (String)rightTable.getModel().getValueAt(k,0);
          oa[1] = (String)rightTable.getModel().getValueAt(k,1);
          oa[2] = new Boolean("Yes".equalsIgnoreCase(((JComboBox) rightTable.getModel().getValueAt(k,2)).getSelectedItem().toString()));
          rightDataV.addElement(oa);
        }
        workAreaPrefCreateH.put(selectedFac,rightDataV);
      }
    //}
  } //end of method

  void setWorkAreaInfo() {
    String facId = null;
    try{
      if(facC.getSelectedIndex() < 0 && facC.getItemCount() > 0) facC.setSelectedIndex(0);
      facId = new String((String)facXref.get(facC.getSelectedItem()));
    } catch (Exception e){};
    if(BothHelpObjs.isBlankString(facId)){
      return;
    }
    selectedFac = new String(facId);
    //first get left table data
    Vector leftDataV;
    if (workAreaInfoH.containsKey(selectedFac)) {
      leftDataV = (Vector) workAreaInfoH.get(selectedFac);
    }else {
      leftDataV = new Vector(1);
    }
    //next get right table data
    Vector rightDataV = null;
    if (workAreaPrefCreateH.containsKey(selectedFac)) {
      Vector origRightDataV = (Vector) workAreaPrefCreateH.get(selectedFac);
      rightDataV = new Vector(origRightDataV.size());
      for (int k = 0; k < origRightDataV.size(); k++) {
        Object[] oa = (Object[]) origRightDataV.elementAt(k);
        Object[] oa2 = new Object[3];
        oa2[0] = oa[0];
        oa2[1] = oa[1];
        oa2[2] = oa[2];
        rightDataV.addElement(oa2);
      }
    }else {
      rightDataV = new Vector(1);
    }
    //check to see if All work areas in list
    addAllWorkAreas(leftDataV,rightDataV);
    //finally build tables
    buildLeftTable(leftDataV,leftDataV.size()+rightDataV.size());
    buildRightTable(rightDataV,leftDataV.size()+rightDataV.size());
  } //end of method

  void addAllWorkAreas(Vector leftDataV, Vector rightDataV) {
    //if All work areas is in the right data then don't need to add it to the left data
    //otherwise add All work areas to the left data if it doesn't exist
    if (rightDataV.size() > 0) {                       //there are data in the right
      Object[] tmpRightOA = (Object[]) rightDataV.firstElement();
      String tmpRightFacID = (String)tmpRightOA[0];
      if (!"All".equalsIgnoreCase(tmpRightFacID)) {    //right data does not contain All Work Areas
        if (leftDataV.size() > 0) {                    //there are data in the left data
          Object[] tmpLeftOA = (Object[]) leftDataV.firstElement();
          String tmpLeftFacID = (String) tmpLeftOA[0];
          if (!"All".equalsIgnoreCase(tmpLeftFacID)) {  //if All Work Areas in list then add to the top of the list
            Object[] tmpAllOA = new Object[2];
            tmpAllOA[0] = "All";
            tmpAllOA[1] = "All Work Areas";
            leftDataV.insertElementAt(tmpAllOA,0);    //put it into the first position
          }
        }else {
          //add All to left data
          Object[] tmpAllOA = new Object[2];
          tmpAllOA[0] = "All";
          tmpAllOA[1] = "All Work Areas";
          leftDataV.insertElementAt(tmpAllOA,0);    //put it into the first position
        }
      }
    }else {                                         //there are no data on the right
      if (leftDataV.size() > 0) {                    //there are data in the left data
        Object[] tmpLeftOA = (Object[]) leftDataV.firstElement();
        String tmpLeftFacID = (String) tmpLeftOA[0];
        if (!"All".equalsIgnoreCase(tmpLeftFacID)) {  //if All Work Areas in list then add to the top of the list
          Object[] tmpAllOA = new Object[2];
          tmpAllOA[0] = "All";
          tmpAllOA[1] = "All Work Areas";
          leftDataV.insertElementAt(tmpAllOA,0);    //put it into the first position
        }
      }else {
        //I am not suppose to be here.  There have to have at one active work area for an active facility
      }
    }
  } //end of method

  void buildLeftTable(Vector dataV, int numberOfWorkArea) {
    leftJSP.getViewport().remove(leftTable);
    String[] colHeads = {" \nID","Work\nArea"};
    int[] colWidths = {0,200};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};
    //build table model
    AttributiveCellTableModel ctm = new AttributiveCellTableModel(colHeads,numberOfWorkArea);
    for (int j = ctm.getRowCount() - 1; j >= 0; j--) {
      ctm.removeRow(j);
    }
    ColoredCell cellAtt = (ColoredCell) ctm.getCellAttribute();
    ctm.setColumnType(colTypes);
    for (int ii = 0; ii < dataV.size(); ii++) {
      Object[] oa = (Object[]) dataV.elementAt(ii);
      ctm.insertRow(ii, oa);
      //this is where I alternate the color between row
      if (ii % 2 == 0) {
        int[] rows = new int[] {ii};
        for (int j = 0; j < ctm.getColumnCount(); j++) {
          int[] cols = new int[] {j};
          cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
        }
      }
    }
    ctm.setColumnType(colTypes);
    ctm.setColumnPrefWidth(colWidths);
    leftTable = new MultiSpanCellTable(ctm);
    leftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    leftTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    leftTable.setCellSelectionEnabled(false);
    leftTable.getTableHeader().setReorderingAllowed(true);
    leftTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
    leftTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = leftTable.getColumnModel();
    for (int i = 0; i < leftTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    leftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = leftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    // set column widths
    for(int i=0;i<leftTable.getColumnCount();i++){
      int width = ctm.getColumnWidth(i);
      leftTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
      leftTable.getColumn(ctm.getColumnName(i)).setWidth(width);
      if (width==0){
        leftTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
        leftTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
      }
    }
    leftJSP.getViewport().setView(leftTable);
  } //end of method

  void buildRightTable(Vector dataV, int numberOfWorkArea) {
    rightJSP.getViewport().remove(rightTable);
    String[] colHeads = {" \nID","Work\nArea","Create\nMR"};
    int[] colWidths = {0,150,60};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};
    //build table model
    AttributiveCellTableModel ctm = new AttributiveCellTableModel(colHeads,numberOfWorkArea);
    for (int j = ctm.getRowCount() - 1; j >= 0; j--) {
      ctm.removeRow(j);
    }
    ColoredCell cellAtt = (ColoredCell) ctm.getCellAttribute();
    ctm.setColumnType(colTypes);
    for (int ii = 0; ii < dataV.size(); ii++) {
      Object[] oa = (Object[]) dataV.elementAt(ii);
      oa[createMRCol] = getComboBoxDetail(((Boolean)oa[createMRCol]).booleanValue());
      ctm.insertRow(ii, oa);
      //this is where I alternate the color between row
      if (ii % 2 == 0) {
        int[] rows = new int[] {ii};
        for (int j = 0; j < ctm.getColumnCount(); j++) {
          int[] cols = new int[] {j};
          cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
        }
      }
    }
    //if user is not an admin then don't allow him/her to change create MR
    String facID = (String)facXref.get(facC.getSelectedItem());
    boolean isAdmin = parent.getGroupMembership().isSuperUser() || parent.getGroupMembership().isAdministrator(facID);
    if (!isAdmin) {
      ctm.setEditableFlag(0);
    }else {
      ctm.setEditableFlag(BothHelpObjs.mypow(2,createMRCol));
    }
    ctm.setColumnType(colTypes);
    ctm.setColumnPrefWidth(colWidths);
    rightTable = new MultiSpanCellTable(ctm);
    rightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rightTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    rightTable.setCellSelectionEnabled(false);
    rightTable.getTableHeader().setReorderingAllowed(true);
    rightTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
    rightTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
    Color color = (Color)tableStyle.get("COLOR");
    Integer t = (Integer)tableStyle.get("INSET_TOP");
    Integer l = (Integer)tableStyle.get("INSET_LEFT");
    Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)tableStyle.get("INSET_RIGHT");
    Integer a = (Integer)tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = rightTable.getColumnModel();
    for (int i = 0; i < rightTable.getColumnCount(); i++) {
      if (i == createMRCol ) {
        //cm.getColumn(i).setCellRenderer(new SimpleCheckBoxCellRenderer());
        //cm.getColumn(i).setCellEditor(new SimpleCheckBoxCellEditor(new JCheckBox()));
        cm.getColumn(i).setCellRenderer(new JComboBoxCellRenderer());
        cm.getColumn(i).setCellEditor(new JComboBoxCellEditor(new JComboBox()));
      }else{
        cm.getColumn(i).setCellRenderer(renderers[0]);
      }
    }

    //font and foreground and background color for columns heading
    String fontName = (String)tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
    rightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = rightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    // set column widths
    for(int i=0;i<rightTable.getColumnCount();i++){
      int width = ctm.getColumnWidth(i);
      rightTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
      rightTable.getColumn(ctm.getColumnName(i)).setWidth(width);
      if (width==0){
        rightTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
        rightTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
      }
    }
    rightJSP.getViewport().setView(rightTable);
  } //end of method

  //add to the last row of table
  void addRowToTable(MultiSpanCellTable myTable,Object[] data) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    int row = model.getRowCount();
    model.insertRow(row,data);
    //show difference color for alternate row
    if (row % 2 == 0) {
      int[] rows = new int[]{row};
      for (int j = 0; j < model.getColumnCount(); j++) {
        int[] cols = new int[]{j};
        cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
      }
    }
    myTable.repaint();
  }  //end of method

  //add to table with the specified index
  void addRowToTable(MultiSpanCellTable myTable,Object[] data, int index) {
    AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
    ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
    model.insertRow(index,data);
    //show difference color for alternate row
    if (index % 2 == 0) {
      int[] rows = new int[]{index};
      for (int j = 0; j < model.getColumnCount(); j++) {
        int[] cols = new int[]{j};
        cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
      }
    }
    myTable.repaint();
  }  //end of method


  JCheckBox getCheckBoxDetail(boolean b) {
    JCheckBox j = new JCheckBox();
    j.setSelected(b);
    System.out.println("------ selected: "+j.isSelected());
    j.setHorizontalAlignment(JLabel.CENTER);
    j.revalidate();
    return j;
  } //end of method

  JComboBox getComboBoxDetail(boolean b) {
    JComboBox j = new JComboBox();
    Vector v = null;
    boolean closedFacility = false;
    try{
      String facId = null;
      if(facC.getSelectedIndex() < 0 && facC.getItemCount() > 0) facC.setSelectedIndex(0);
      facId = new String((String)facXref.get(facC.getSelectedItem()));
      closedFacility = "c".equalsIgnoreCase((String)myFacilityActiveList.get(facId));
    } catch (Exception e){
      e.printStackTrace();
      closedFacility = true;
    };
    if (closedFacility) {
      v = new Vector(1);
      v.addElement("No");
      j = ClientHelpObjs.loadChoiceFromVector(j,v);
    }else {
      v = new Vector(2);
      v.addElement("No");
      v.addElement("Yes");
      j = ClientHelpObjs.loadChoiceFromVector(j, v);
      if (b) {
        j.setSelectedItem("Yes");
      }else {
        j.setSelectedIndex(0);
      }
    }
    j.setEditable(false);
    j.setMaximumRowCount(v.size());
    j.revalidate();
    return j;
  } //end of method


  void setSecurity(){
    boolean superuser = parent.groupMembership.isSuperUser();
    boolean admin = false;
    Vector af = parent.getGroupMembership().getAdminFacilities();
    for(int i=0;i<af.size();i++){
      if(facXref.containsKey(af.elementAt(i).toString())){
        admin = true;
        break;
      }
    }
    boolean adminAtSomeFac = af.size() > 0;

    if (superuser){
      turnAllFieldsProtected(0);
    } else if (admin){
      turnAllFieldsProtected(1);
    } else if (adminAtSomeFac){
      turnAllFieldsProtected(4);
    } else if (uid == parent.getUserId().intValue()){
      turnAllFieldsProtected(2);
    } else {
      turnAllFieldsProtected(3);
    }
    if (newUser){
      userT.setEnabled(true);
      bPChange.setEnabled(false);
    }
  }

  void turnAllFieldsProtected(int level){
       facC.setEnabled(true);
       userT.setEnabled(false);
       fnameT.setEnabled(false);
       miT.setEnabled(false);
       lnameT.setEnabled(false);
       emailT.setEnabled(false);
       phoneT.setEnabled(false);
       altphoneT.setEnabled(false);
       pagerT.setEnabled(false);
       faxT.setEnabled(false);
       facB.setEnabled(false);
       relYesC.setEnabled(false);
       relNoC.setEnabled(false);
       yesPFacC.setEnabled(false);
       noPFacC.setEnabled(false);
       addUserB.setEnabled(false);
       deleteUserB.setEnabled(false);
       updateB.setEnabled(false);
       useAppGrpB.setEnabled(false);
       fiAppB.setEnabled(false);
       useAppB.setEnabled(false);
       adminYesB.setEnabled(false);
       adminNoB.setEnabled(false);
       bPChange.setEnabled(false);
       ncYesRB.setEnabled(false);
       ncNoRB.setEnabled(false);
       managerYesRB.setEnabled(false);
       managerNoRB.setEnabled(false);
       reportRB.setEnabled(false);
       reportNoRB.setEnabled(false);
       costReportRB.setEnabled(false);
       costReportNoRB.setEnabled(false);
       switch (level){
         case 0: // super user
          deleteUserB.setEnabled(true);
         case 1: // admin user
          useAppGrpB.setEnabled(true);
          fiAppB.setEnabled(true);
          useAppB.setEnabled(true);
          addUserB.setEnabled(true);
          facB.setEnabled(true);
         case 2:  // self
          bPChange.setEnabled(true);
          fnameT.setEnabled(true);
          miT.setEnabled(true);
          lnameT.setEnabled(true);
          emailT.setEnabled(true);
          phoneT.setEnabled(true);
          altphoneT.setEnabled(true);
          pagerT.setEnabled(true);
          faxT.setEnabled(true);
          yesPFacC.setEnabled(true);
          noPFacC.setEnabled(true);
          updateB.setEnabled(true);
          lookupB.setEnabled(true);
        case 3:  //view only
          break;
        case 4: // admin user for facility that this
                 // person is not authorized for
          addUserB.setEnabled(true);
          facB.setEnabled(true);
          updateB.setEnabled(true);
        default:
       }
       return;
  }

  public void stateChanged(String label){
    //S.setLabel(label);
    if (!label.equals("Done")){
     parent.getMain().countLabel.put(new Integer(Main.USER_PROFILE),"");
     parent.getMain().setStatusBarText((String)parent.getMain().countLabel.get(new Integer(Main.USER_PROFILE)));
    }
  }

  protected void updateScreen(){
    //Personnal Information
    String msg = null;
    String incompleteReason = "Incomplete";
    String resultFlag = null;
    ClientHelpObjs.setEnabledContainer(this,false);
    try {
        //first save work area data
        saveSelectedWorkAreaData();
        TcmisHttpConnection cgi = new TcmisHttpConnection("UserProfileNew",parent);
        Hashtable query = new Hashtable();
        query.put("ACTION","UPDATE"); //String, String
        query.put("USER_ID",new Integer(uid)); //String, Integer
        query.put("FIRST_NAME",fnameT.getText());
        query.put("LAST_NAME",lnameT.getText());
        query.put("MI",miT.getText());
        query.put("LOGON_ID",userT.getText());
        query.put("PHONE",phoneT.getText());
        query.put("ALT_PHONE",altphoneT.getText());
        query.put("PAGER",pagerT.getText());
        query.put("EMAIL",emailT.getText());
        query.put("FAX",faxT.getText());
        if(facXref.containsKey(pFac)){
          query.put("PREF_FACILITY_ID",facXref.get(pFac).toString());
        }else{
          query.put("PREF_FACILITY_ID",facXref.get(facC.getItemAt(0)).toString());
        }
        query.put("FACILITY_ID",myFacilityIDV);
        query.put("WORK_AREA",workAreaPrefCreateH);
        query.put("RELEASER",releasersV);
        query.put("CREATE_NEW_CHEM",newChemV);
        query.put("ADMIN",adminV);
        query.put("WASTE_MANAGER",managerV);
        query.put("CREATE_REPORT",reportV);
        query.put("COST_REPORT",costReportV);
        Hashtable result = cgi.getResultHash(query);
        if (result == null){
           String no = new String("Access denied. Your session is corrupted, please restart the application.");
           GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
           err.setMsg(no);
           err.setVisible(true);
           ClientHelpObjs.setEnabledContainer(this,true);
           return;
        }
        resultFlag = (String) result.get("MSG");
        if ("Incomplete".equalsIgnoreCase(resultFlag)) {
          incompleteReason = (String)result.get("REASON");
        }
    } catch (Exception e){
        e.printStackTrace();
        resultFlag = null;
    }

    if (resultFlag == null || resultFlag.equalsIgnoreCase("Error") || BothHelpObjs.isBlankString(resultFlag)){
      msg = new String("An error has occurred, please retry.");
    } else {
         if(newUser){
           //put up the password box here
           changePassword();
           bPChange.setEnabled(true);
           msg = new String("Record successfully added.");
           newUser = false;
        }else{
           msg = new String("Record successfully updated.");
        }
        if  (uid == parent.getUserId().intValue())  {
          parent.getMain().profileChanged.addElement(new Integer(parent.getMain().USER_PROFILE));
          parent.getMain().profileChanged.addElement(new Integer(parent.getMain().SEARCHPT));
          parent.getMain().profileChanged.addElement(new Integer(parent.getMain().TRACKING));
          parent.getMain().profileChanged.addElement(new Integer(parent.getMain().INVENTORY));
          parent.setXfac(facXref);
        }
    }

    //show incomplete reason if status is incomplete
    if ("Incomplete".equalsIgnoreCase(resultFlag)) {
      msg = incompleteReason;
    }

    GenericDlg dlg = new GenericDlg(parent.getMain(),"Message",true);
    dlg.setMsg(msg);
    dlg.setVisible(true);
    ClientHelpObjs.setEnabledContainer(this,true);
    setSecurity();
    //set approvers and releasers radio
    setLogicalRadio();
    //set work area
    setWorkAreaInfo();
    //I DON'T NEED TO RELOAD DATA COZ I ALREADY HAVE IT  9-16-03 -- this.loadIt();
  }

  void setLogicalRadio(){
    String facId = null;
    try{
      if(facC.getSelectedIndex() < 0 && facC.getItemCount() > 0) facC.setSelectedIndex(0);
      facId = new String((String)facXref.get(facC.getSelectedItem()));
    } catch (Exception e){};
    if(BothHelpObjs.isBlankString(facId)){
      return;
    }
    selectedFac = new String(facId);
    try{
      if (releasersV.contains(facId)){
        relYesC.setSelected(true);
      } else {
        relNoC.setSelected(true);
      }
    } catch (Exception e){
       relNoC.setSelected(true);
    };

    try{
      if (pFac.equals(facC.getSelectedItem())){
        yesPFacC.setSelected(true);
      } else {
        noPFacC.setSelected(true);
      }
    } catch (Exception e){noPFacC.setSelected(true);};

    try{
      if (adminV.contains(facId)){
        adminYesB.setSelected(true);
      } else {
         adminNoB.setSelected(true);
      }
    } catch (Exception e){
       adminNoB.setSelected(true);
    }
    try{
      if (newChemV.contains(facId)){
        ncYesRB.setSelected(true);
      } else {
        ncNoRB.setSelected(true);
      }
    } catch (Exception e){
       ncNoRB.setSelected(true);
    }
    try{
      if (managerV.contains(facId)){
        managerYesRB.setSelected(true);
      } else {
        managerNoRB.setSelected(true);
      }
    } catch (Exception e){
       managerNoRB.setSelected(true);
    }
    try{
      if (reportV.contains(facId)){
        reportRB.setSelected(true);
      } else {
        reportNoRB.setSelected(true);
      }
    } catch (Exception e){
       reportNoRB.setSelected(true);
    }
    try{
      if (costReportV.contains(facId)){
        costReportRB.setSelected(true);
      } else {
        costReportNoRB.setSelected(true);
      }
    } catch (Exception e){
       costReportNoRB.setSelected(true);
    }

    //enable/disable based on auth level of user
    boolean b = parent.getGroupMembership().isSuperUser() || parent.getGroupMembership().isAdministrator(facId);
    if (b) {
      if ("c".equalsIgnoreCase((String)myFacilityActiveList.get(facId))) {
        selectAllB.setEnabled(false);
        relYesC.setEnabled(false);
        relNoC.setEnabled(false);
        ncYesRB.setEnabled(false);
        ncNoRB.setEnabled(false);
        managerYesRB.setEnabled(false);
        managerNoRB.setEnabled(false);
      }else {
        selectAllB.setEnabled(b);
        relYesC.setEnabled(b);
        relNoC.setEnabled(b);
        ncYesRB.setEnabled(b);
        ncNoRB.setEnabled(b);
        managerYesRB.setEnabled(b);
        managerNoRB.setEnabled(b);
      }
      moveLeftB.setEnabled(b);
      moveRightB.setEnabled(b);
      clearAllB.setEnabled(b);
      yesPFacC.setEnabled(b);
      noPFacC.setEnabled(b);
      adminYesB.setEnabled(b);
      adminNoB.setEnabled(b);
      reportRB.setEnabled(b);
      reportNoRB.setEnabled(b);
      costReportRB.setEnabled(b);
      costReportNoRB.setEnabled(b);
    }else {
      relYesC.setEnabled(b);
      relNoC.setEnabled(b);
      adminYesB.setEnabled(b);
      adminNoB.setEnabled(b);
      ncYesRB.setEnabled(b);
      ncNoRB.setEnabled(b);
      managerYesRB.setEnabled(b);
      managerNoRB.setEnabled(b);
      reportRB.setEnabled(b);
      reportNoRB.setEnabled(b);
      costReportRB.setEnabled(b);
      costReportNoRB.setEnabled(b);
      boolean self = parent.getUserId().toString().equals(uidL.getText());
      yesPFacC.setEnabled(b || self);
      noPFacC.setEnabled(b || self);
      selectAllB.setEnabled(b);
      clearAllB.setEnabled(b);
      moveLeftB.setEnabled(b);
      moveRightB.setEnabled(b);
    }
  } //end of method

  void updateRelRadio(){
    if (facXref == null || facC.getSelectedItem() == null || facXref.get(facC.getSelectedItem()) == null) return;
    String facId = new String(facXref.get(facC.getSelectedItem()).toString());
    if (relYesC.isSelected()){
       if (!releasersV.contains(facId)){
         releasersV.addElement(facId);
       }
    } else {
       if (releasersV.contains(facId)){
         releasersV.removeElement(facId);
       }
    }
  }

  void updateAdminRadio(){
    if (facXref == null || facC.getSelectedItem() == null || facXref.get(facC.getSelectedItem()) == null) return;
    String facId = new String(facXref.get(facC.getSelectedItem()).toString());
    if (adminYesB.isSelected()){
       if (!adminV.contains(facId)){
         adminV.addElement(facId);
       }
    } else {
       if (adminV.contains(facId)){
         adminV.removeElement(facId);
       }
    }
  }

  void updateNewChemRadio(){
    if (facXref == null || facC.getSelectedItem() == null || facXref.get(facC.getSelectedItem()) == null) return;
    String facId = new String(facXref.get(facC.getSelectedItem()).toString());
    if (ncYesRB.isSelected()){
       if (!newChemV.contains(facId)){
         newChemV.addElement(facId);
       }
    } else {
       if (newChemV.contains(facId)){
         newChemV.removeElement(facId);
       }
    }
  }

  void updateManagerOptRadio(){
    if (facXref == null || facC.getSelectedItem() == null || facXref.get(facC.getSelectedItem()) == null) return;
    String facId = new String(facXref.get(facC.getSelectedItem()).toString());
    if (managerYesRB.isSelected()){
      if (!managerV.contains(facId)){
         managerV.addElement(facId);
       }
    } else {
       if (managerV.contains(facId)){
         managerV.removeElement(facId);
       }
    }
  }

  void updateReportOptRadio(){
    if (facXref == null || facC.getSelectedItem() == null || facXref.get(facC.getSelectedItem()) == null) return;
    String facId = new String(facXref.get(facC.getSelectedItem()).toString());

    if (reportRB.isSelected()){
      if (!reportV.contains(facId)){
         reportV.addElement(facId);
       }
    } else {
       if (reportV.contains(facId)){
         reportV.removeElement(facId);
       }
    }
  }

  void updateCostReportOptRadio(){
    if (facXref == null || facC.getSelectedItem() == null || facXref.get(facC.getSelectedItem()) == null) return;
    String facId = new String(facXref.get(facC.getSelectedItem()).toString());

    if (costReportRB.isSelected()){
      if (!costReportV.contains(facId)){
         costReportV.addElement(facId);
       }
    } else {
       if (costReportV.contains(facId)){
         costReportV.removeElement(facId);
       }
    }
  }

  String getShipAddress(String locId){
     String resultLoc = new String("");
     try{
        TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO,parent);
        Hashtable query = new Hashtable();
        query.put("ACTION","GET_LOCATION"); //String, String
        query.put("LOCID",locId);
        Hashtable result = cgi.getResultHash(query);
        if (result == null){
           String no = new String("Access denied. Your session is corrupted, please restart the application.");
           GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
           err.setMsg(no);
           err.setVisible(true);
           return null;
        }
        Vector tmpV = (Vector) result.get("LOCATION");
        tmpV.trimToSize();
        for (int i=0;i<tmpV.size();i++){
          resultLoc = resultLoc + (resultLoc.length()==0?"":"\n")+(tmpV.elementAt(i)==null?"":tmpV.elementAt(i).toString());
        }

        return resultLoc;
     } catch (Exception e) { e.printStackTrace(); }
     return null;
  }

  void changePassword(){
     ChangePasswordDlg cp = new ChangePasswordDlg(parent.getMain(),"Password Change",true);
     cp.setParent(parent);
     cp.userT.setText(this.userT.getText());
     cp.setPersonnelId(new Integer(uidL.getText().trim()));
     cp.setVisible(true);
     return;
  }


  void facC_itemStateChanged(ItemEvent e) {
     if (loading) return;
     if (e.getStateChange() == e.SELECTED) {
       saveSelectedWorkAreaData();
       setLogicalRadio();
       //set work area
       setWorkAreaInfo();
     }
     facC.repaint();
  }

  void updateB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if (loading) return;
    updateScreen();
  }

  public boolean accept(File file, String name){
      return name.lastIndexOf(".exe") > 0;
  }

  void updateFacilityList(int lastSelectedIndex, Vector facIDV, Hashtable newFacIDList, Hashtable workAreaDataH) {
    Vector tmpNewFacDescV = new Vector();
    //first determine which facilities removed from list
    //and remove data
    for (int i = 0; i < facIDV.size(); i++ ) {
      String tmpFacID = (String) facIDV.elementAt(i);
      if (!newFacIDList.containsKey(tmpFacID)) {
        workAreaInfoH.remove(tmpFacID);
        workAreaPrefCreateH.remove(tmpFacID);
        if (releasersV.contains(tmpFacID)) {
          releasersV.removeElementAt(releasersV.indexOf(tmpFacID));
        }
        if (newChemV.contains(tmpFacID)) {
          newChemV.removeElementAt(newChemV.indexOf(tmpFacID));
        }
        if (adminV.contains(tmpFacID)) {
          adminV.removeElementAt(adminV.indexOf(tmpFacID));
        }
        if (managerV.contains(tmpFacID)) {
          managerV.removeElementAt(managerV.indexOf(tmpFacID));
        }
        if (reportV.contains(tmpFacID)) {
          reportV.removeElementAt(reportV.indexOf(tmpFacID));
        }
        if (costReportV.contains(tmpFacID)) {
          costReportV.removeElementAt(costReportV.indexOf(tmpFacID));
        }
      }
    }
    //next update new facilities
      //reset facXref and facility combobox
    if (facXref != null) {
      facXref = null;
    }
    facXref = new Hashtable(newFacIDList.size());
    facC.removeAllItems();
    myFacilityIDV.removeAllElements();
    Enumeration enum1 = newFacIDList.keys();
    while (enum1.hasMoreElements()) {
      String tmpFacID = (String) enum1.nextElement();
      String tmpFacDesc = (String) newFacIDList.get(tmpFacID);
      facXref.put(tmpFacDesc,tmpFacID);
      facC.addItem(tmpFacDesc);
      myFacilityIDV.addElement(tmpFacID);
      //work area data
      if (!workAreaInfoH.containsKey(tmpFacID)) {
        tmpNewFacDescV.addElement(tmpFacDesc);
        Vector waDataV = (Vector) workAreaDataH.get(tmpFacID);
        Vector newWADataV = new Vector(waDataV.size());
        for (int k = 0; k < waDataV.size(); k++) {
          Object[] oa = (Object[]) waDataV.elementAt(k);
          Object[] oa2 = new Object[2];
          oa2[0] = oa[0];
          oa2[1] = oa[1];
          newWADataV.addElement(oa2);
        }
        workAreaInfoH.put(tmpFacID, newWADataV);
      }
    }
    //finally show data
    facC = ClientHelpObjs.choiceSort(facC);
      //set the selected facility to the first (alpha order) new facility
    if (tmpNewFacDescV.size() > 0 ) {
      tmpNewFacDescV = BothHelpObjs.sort(tmpNewFacDescV);
      facC.setSelectedItem(tmpNewFacDescV.elementAt(0));
    }else {
      if (facC.getItemCount() > lastSelectedIndex) {
        if (lastSelectedIndex > 0) {
          facC.setSelectedIndex(lastSelectedIndex-1);     //select the previous facility
        }else {
          facC.setSelectedIndex(lastSelectedIndex);       //select the next facility
        }
      }else {
        facC.setSelectedIndex(facC.getItemCount()-1);     //select the last facility
      }
    }
    loading = false;
    setLogicalRadio();
    //set work area
    setWorkAreaInfo();
  }  //end of method

  void facB_actionPerformed(ActionEvent e) {
    //get current facilities ID
    int lastSelectedIndex = facC.getSelectedIndex();
    UserNewFacilityDlg unfd = new UserNewFacilityDlg(parent.getMain(),"Edit User facilities list",myFacilityIDV);
    unfd.setParent(parent);
    unfd.loadIt();
    if (unfd.getLoaded()) {
      CenterComponent.centerCompOnScreen(unfd);
      unfd.setVisible(true);
      if (!unfd.getCancelled()) {
        myFacilityActiveList = null;
        myFacilityActiveList = unfd.getFacilityActiveList();
        //update DATA
        updateFacilityList(lastSelectedIndex,myFacilityIDV,unfd.getNewFacilityList(),unfd.getWorkAreaData());
      }
    }
    unfd.dispose();
  } //end of method

  void relYesC_itemStateChanged(ItemEvent e) {
    if (loading) return;
    updateRelRadio();
  }

  void relNoC_itemStateChanged(ItemEvent e) {
    if (loading) return;
    updateRelRadio();
  }

  public void printScreenData() {
    ReportOptionDlg rod = new ReportOptionDlg(parent.getMain());
    rod.setCmisApp(parent);
    rod.setUserIdProfile(uidL.getText());
    rod.setBrowser("");
    rod.setScreen("UP");
    rod.loadIt();
  }

  void lookupB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    SearchPersonnel dlg = new SearchPersonnel(parent.getMain(),"Search",true);
    dlg.setGrandParent(parent);
    dlg.setPersonType("PERSONNEL");
    dlg.setVisible(true);
    if (dlg.getValueId() != null){
       uid = dlg.getValueId().intValue();
       this.loadIt();
    }

  }

  void bPChange_actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        changePassword();
  }

  void bPChange_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         changePassword();
    }
  }

  void yesPFacC_mouseClicked(MouseEvent e) {
    pFac = facC.getSelectedItem().toString();
  }

  void noPFacC_mouseClicked(MouseEvent e) {
    if (!noPFacC.isSelected()){
      pFac = facC.getItemAt(0).toString();
    }
  }

  void addUserB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    NewUser dlg = new NewUser(parent.getMain(),"New User",true);
    dlg.setGrandParent(parent);
    Hashtable facTmp = new Hashtable();
    try {
        TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO,parent);
        Hashtable query = new Hashtable();
        query.put("ACTION","GET_ALL_FACS"); //String, String
        Hashtable result = cgi.getResultHash(query);
        if (result == null){
           String no = new String("Access denied. Your session is corrupted, please restart the application.");
           GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
           err.setMsg(no);
           err.setVisible(true);
           return;
        }
        Vector tV1  = (Vector) result.get("FACIDS");
        Vector tV2  = (Vector) result.get("FACNAMES");

        for (int i=0;i<tV2.size();i++){
          if(parent.getGroupMembership().isAdministrator(tV1.elementAt(i).toString()) || parent.getGroupMembership().isSuperUser()){
            if ("y".equalsIgnoreCase((String)myFacilityActiveList.get(tV1.elementAt(i).toString()))) {
              facTmp.put(tV2.elementAt(i), tV1.elementAt(i));
            }
          }
        }
    } catch (Exception e1){e1.printStackTrace(); return;}

    dlg.setFacs(facTmp);
    CenterComponent.centerCompOnComp(this,dlg);
    dlg.loadIt();
    dlg.show();
    if (dlg.getUId() == null) return;
    uid = dlg.getUId().intValue();
    newUser = true;
    this.loadIt();
  }

  void deleteUserB_actionPerformed(ActionEvent e) {
     ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
     String msg = new String("Are you sure you want to delete this user?");
     ConfirmDlg dlg = new ConfirmDlg(parent.getMain(),"Confirm ?",true);
     dlg.setMsg(msg);
     dlg.setVisible(true);
     if (!dlg.getConfirmationFlag()) return;
     //delete user

     try {
        TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.PERSONNEL,parent);
        Hashtable query = new Hashtable();
        query.put("ACTION","DELETE_USER"); //String, String
        query.put("UID",(new Integer(uid)).toString());
        Hashtable result = cgi.getResultHash(query);
        if (result == null){
           String no = new String("Access denied. Your session is corrupted, please restart the application.");
           GenericDlg err = new GenericDlg(parent.getMain(),"Denied",true);
           err.setMsg(no);
           err.setVisible(true);
           return;
        }
        msg  = ((Vector) result.get("MSG")).elementAt(0).toString();
        if (msg.equalsIgnoreCase("OK")){
           msg = new String("User successfully deleted.");
        } else {
           msg = new String("An error has occured, please try again.");
        }
    } catch (Exception e1){e1.printStackTrace(); msg = new String(e1.getMessage());}

    GenericDlg no = new GenericDlg(parent.getMain(),"Information",false);
    no.setMsg(msg);
    no.show();
    uid = 0;
    loadIt();
    return;
  }

  void fnameT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }
  void lnameT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }
  void miT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }
  void userT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }
  void faxT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }
  void pagerT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }

  void emailT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }

  void phoneT_propertyChange(PropertyChangeEvent e) {
       personalChanged = true;
  }
  void altphoneT_propertyChange(PropertyChangeEvent e) {
      personalChanged = true;
  }

  void adminNoB_actionPerformed(ActionEvent e) {
    updateAdminRadio();
  }
  void adminYesB_actionPerformed(ActionEvent e) {
    updateAdminRadio();
  }
  void ncNoRB_actionPerformed(ActionEvent e) {
    updateNewChemRadio();
  }
  void ncYesRB_actionPerformed(ActionEvent e) {
    updateNewChemRadio();
  }

  void managerYesRB_actionPerformed(ActionEvent e) {
    updateManagerOptRadio();
  }
  void managerNoRB_actionPerformed(ActionEvent e) {
    updateManagerOptRadio();
  }


  void useAppGrpB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String name;
    if(BothHelpObjs.isBlankString(lnameT.getText()) ||
       BothHelpObjs.isBlankString(fnameT.getText())) {
       name = "-";
    }else{
      name = lnameT.getText() + ", "+fnameT.getText();
    }
    AddMemberToGroupsDlg amgd = new AddMemberToGroupsDlg(parent.getMain(),true,parent,name,uidL.getText());
    amgd.loadIt();
    CenterComponent.centerCompOnScreen(amgd);
    amgd.setVisible(true);
  }

  void reportRB_actionPerformed(ActionEvent e) {
    updateReportOptRadio();
  }

  void reportNoRB_actionPerformed(ActionEvent e) {
    updateReportOptRadio();
  }

  void costReportRB_actionPerformed(ActionEvent e) {
    updateCostReportOptRadio();
  }

  void costReportNoRB_actionPerformed(ActionEvent e) {
    updateCostReportOptRadio();
  }

  void fiAppB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    AddFinanceApprovalDlg afad = new AddFinanceApprovalDlg(parent.getMain(),"Financial Approvers",false,parent);
    CenterComponent.centerCompOnScreen(afad);
    afad.setVisible(true);
  }

  void useAppB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String name;
    if(BothHelpObjs.isBlankString(lnameT.getText()) ||
       BothHelpObjs.isBlankString(fnameT.getText())) {
       name = "-";
    }else{
      name = lnameT.getText() + ", "+fnameT.getText();
    }
    Vector currentFacId = new Vector();
    for (int i = 0; i < facC.getItemCount(); i++) {
      currentFacId.addElement(facC.getItemAt(i).toString());
    }
    AddUseApprovalDlg augd = new AddUseApprovalDlg(parent.getMain(),true,parent,name,uidL.getText(),currentFacId);
    augd.loadIt();
    CenterComponent.centerCompOnScreen(augd);
    augd.setVisible(true);
  }

  boolean checkAllWorkAreasOK(Vector leftDataV, Vector rightDataV, int[] selectedRows) {
    boolean result = false;
    //First check to see if All in selected lists
    boolean selectedItemContainAll = false;
    for (int i = 0; i < selectedRows.length; i++) {
      String tmpFacID = (String) leftTable.getModel().getValueAt(selectedRows[i], 0);
      if ("All".equalsIgnoreCase(tmpFacID)) {
        selectedItemContainAll = true;
        break;
      }
    }
    if (selectedRows.length == 1) {
      if (rightDataV.size() > 0) {
        if (selectedItemContainAll) {          //right table contains data and user want to move All Work Areas over
          result = false;
        }else {
          Object[] rightDataOA = (Object[]) rightDataV.firstElement();
          String tmpRightFacID = (String) rightDataOA[0];
          if ("All".equalsIgnoreCase(tmpRightFacID)) {
            result = false;
          }else {
            result = true;
          }
        }
      }else {                                  //right table does not contains any data
        result = true;
      }
    }else {
      if (selectedItemContainAll) {            //if user selected several work areas and All is included
        result = false;
      }else {                                  //else check to see if All already on the right table
        if (rightDataV.size() > 0) {           //if right table has data
          Object[] rightDataOA = (Object[]) rightDataV.firstElement();
          String tmpRightFacID = (String) rightDataOA[0];
          if ("All".equalsIgnoreCase(tmpRightFacID)) {
            result = false;
          }else {
            result = true;
          }
        }else {                                //no work area is on the right table
          result = true;
        }
      }
    }                                          //end of user selected more than one work area to move
    return result;
  }

  void moveRightB_actionPerformed(ActionEvent e) {
    int[] is = leftTable.getSelectedRows();
    if (is.length < 1) {
      GenericDlg.showMessage("Select work area(s) that you want to add to user list.");
      return;
    }
    //first get left table data
    Vector leftDataV;
    if (workAreaInfoH.containsKey(selectedFac)) {
      leftDataV = (Vector) workAreaInfoH.get(selectedFac);
    }else {
      leftDataV = new Vector(is.length);
    }
    //next get right table data
    Vector rightDataV = null;
    if (workAreaPrefCreateH.containsKey(selectedFac)) {
      rightDataV = (Vector) workAreaPrefCreateH.get(selectedFac);
    }else {
      rightDataV = new Vector(is.length);
    }
    //first check to make sure that if All Work Areas is selected then others can't be selected
    if (!checkAllWorkAreasOK(leftDataV,rightDataV,is)) {
      GenericDlg.showMessage("All Work Areas cannot be moved over with other work areas.");
      return;
    }
    //move selected rows
    for (int i = 0 ; i < is.length; i++) {
      Object[] oa = new Object[3];
      String tmpFacID = (String) leftTable.getModel().getValueAt(is[i],0);
      oa[0] = tmpFacID;
      oa[1] = (String) leftTable.getModel().getValueAt(is[i],1);
      oa[2] = getComboBoxDetail(true);
      if ("All".equalsIgnoreCase(tmpFacID)) {
        addRowToTable(rightTable,oa,0);
        oa[2] = new Boolean(true);
        rightDataV.insertElementAt(oa,0);
      }else {
        addRowToTable(rightTable,oa);
        oa[2] = new Boolean(true);
        rightDataV.addElement(oa);
      }
    }
    //next delete selected rows
    for (int i = is.length-1 ; i >= 0  ; i--) {
      removeRowFromTable(leftTable,is[i]);
      leftDataV.removeElementAt(is[i]);
    }
  } //end of method

  void moveLeftB_actionPerformed(ActionEvent e) {
    int[] is = rightTable.getSelectedRows();
    if (is.length < 1) {
      GenericDlg.showMessage("Select work area(s) that you want to remove from user list.");
      return;
    }
    //first get left table data
    Vector leftDataV;
    if (workAreaInfoH.containsKey(selectedFac)) {
      leftDataV = (Vector) workAreaInfoH.get(selectedFac);
    }else {
      leftDataV = new Vector(is.length);
    }
    //next get right table data
    Vector rightDataV = null;
    if (workAreaPrefCreateH.containsKey(selectedFac)) {
      rightDataV = (Vector) workAreaPrefCreateH.get(selectedFac);
    }else {
      rightDataV = new Vector(is.length);
    }
    //now move selected rows
    for (int i = 0 ; i < is.length; i++) {
      Object[] oa = new Object[2];
      String tmpFacID = (String) rightTable.getModel().getValueAt(is[i],0);
      oa[0] = tmpFacID;
      oa[1] = (String) rightTable.getModel().getValueAt(is[i],1);
      if ("All".equalsIgnoreCase(tmpFacID)) {      //put int the first position
        addRowToTable(leftTable, oa, 0);
        leftDataV.insertElementAt(oa,0);
      }else {
        addRowToTable(leftTable, oa);
        leftDataV.addElement(oa);
      }
    }
    //next delete selected rows
    for (int i = is.length-1 ; i >= 0  ; i--) {
      removeRowFromTable(rightTable,is[i]);
      rightDataV.removeElementAt(is[i]);
    }
  } //end of method

  void removeRowFromTable(MultiSpanCellTable myTable,int row){
      AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
      model.removeRow(row);
  }

  void selectAllB_actionPerformed(ActionEvent e) {
    for (int i = 0; i < rightTable.getRowCount(); i++) {
      JComboBox j = (JComboBox) rightTable.getModel().getValueAt(i,createMRCol);
      j.setSelectedItem("Yes");
      rightTable.getModel().setValueAt(j, i, createMRCol);
    }
  } //end of method

  void clearAllB_actionPerformed(ActionEvent e) {
    for (int i = 0; i < rightTable.getRowCount(); i++) {
      JComboBox j = (JComboBox) rightTable.getModel().getValueAt(i,createMRCol);
      j.setSelectedItem("No");
      rightTable.getModel().setValueAt(j, i, createMRCol);
    }
  } //end of method
} //end of class

class UserProfileLoadThread extends Thread {
  UserProfileWin parent;

  public UserProfileLoadThread(UserProfileWin parent){
     super("UserProfileLoadThread");
     this.parent = parent;
  }

  public void run(){
     parent.loadItAction();
  }
}

class UserProfileWin_relYesC_itemAdapter implements java.awt.event.ItemListener{
  UserProfileWin adaptee;

  UserProfileWin_relYesC_itemAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.relYesC_itemStateChanged(e);
  }
}

class UserProfileWin_relNoC_itemAdapter implements java.awt.event.ItemListener {
  UserProfileWin adaptee;

  UserProfileWin_relNoC_itemAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.relNoC_itemStateChanged(e);
  }
}

class UserProfileWin_yesPFacC_mouseAdapter extends java.awt.event.MouseAdapter {
  UserProfileWin adaptee;

  UserProfileWin_yesPFacC_mouseAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.yesPFacC_mouseClicked(e);
  }
}

class UserProfileWin_noPFacC_mouseAdapter extends java.awt.event.MouseAdapter {
  UserProfileWin adaptee;

  UserProfileWin_noPFacC_mouseAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.noPFacC_mouseClicked(e);
  }
}

class UserProfileWin_bPChange_actionAdapter implements java.awt.event.ActionListener{
  UserProfileWin adaptee;

  UserProfileWin_bPChange_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.bPChange_actionPerformed(e);
  }
}

class UserProfileWin_bPChange_keyAdapter extends java.awt.event.KeyAdapter {
  UserProfileWin adaptee;

  UserProfileWin_bPChange_keyAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.bPChange_keyPressed(e);
  }
}

class UserProfileWin_facB_actionAdapter implements java.awt.event.ActionListener{
  UserProfileWin adaptee;

  UserProfileWin_facB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.facB_actionPerformed(e);
  }
}

class UserProfileWin_facC_itemAdapter implements java.awt.event.ItemListener{
  UserProfileWin adaptee;

  UserProfileWin_facC_itemAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent e) {
    adaptee.facC_itemStateChanged(e);
  }
}

class UserProfileWin_updateB_actionAdapter implements java.awt.event.ActionListener{
  UserProfileWin adaptee;

  UserProfileWin_updateB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.updateB_actionPerformed(e);
  }
}

class UserProfileWin_lookupB_actionAdapter implements java.awt.event.ActionListener{
  UserProfileWin adaptee;

  UserProfileWin_lookupB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.lookupB_actionPerformed(e);
  }
}

class UserProfileWin_addUserB_actionAdapter implements java.awt.event.ActionListener {
  UserProfileWin adaptee;

  UserProfileWin_addUserB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.addUserB_actionPerformed(e);
  }
}

class UserProfileWin_deleteUserB_actionAdapter implements java.awt.event.ActionListener {
  UserProfileWin adaptee;

  UserProfileWin_deleteUserB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.deleteUserB_actionPerformed(e);
  }
}

class UserProfileWin_reportRB_actionAdapter implements java.awt.event.ActionListener {
  UserProfileWin adaptee;

  UserProfileWin_reportRB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.reportRB_actionPerformed(e);
  }
}

class UserProfileWin_reportNoRB_actionAdapter implements java.awt.event.ActionListener {
  UserProfileWin adaptee;

  UserProfileWin_reportNoRB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.reportNoRB_actionPerformed(e);
  }
}

class UserProfileWin_costReportRB_actionAdapter implements java.awt.event.ActionListener {
  UserProfileWin adaptee;

  UserProfileWin_costReportRB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.costReportRB_actionPerformed(e);
  }
}

class UserProfileWin_costReportNoRB_actionAdapter implements java.awt.event.ActionListener {
  UserProfileWin adaptee;

  UserProfileWin_costReportNoRB_actionAdapter(UserProfileWin adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.costReportNoRB_actionPerformed(e);
  }
}


