

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Waste request Detail Screen. Tab is named Waste Management Request

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.math.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;

import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
//import radian.tcmis.client.share.reports.*;
import radian.tcmis.client.share.gui.delivery.*;

import java.beans.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class WastePickupRequest extends JPanel {
  Hashtable requestDataH = null;
  boolean deleteFlag = true;
  Integer selectedLine = new Integer(0);
  int rNum = 0;
  int deletedRow;
  CmisApp cmis;
  Vector lineItems;
  public Hashtable headerInfo;
  Vector delInfo = new Vector();
  CmisTable rliTable;
  TableSorter sorter = new TableSorter();
  JTable matDescTable;
  JTable chargeNumTable;
  Object[][] summaryData;
  String rejectionReason = "";
  Calendar now;
  JPanel panel1 = new JPanel();
  //trong 5/11
  CmisTable containerTable;
  Hashtable containerId;
  Hashtable NewCatalogData;

  boolean loadingDetail = false;
  boolean loadingSummary = false;
  boolean loadingScreenData = false;
  boolean updatingDelTypeC = false;
  boolean firstTime = true;
  Boolean wasteManager;
  Boolean wastePickup;

  int replaceContainerCol = 0;
  int qtyCol = 0;
  int notesCol = 0;
  int profileCol = 0;
  int pickupCol = 0;
  int workAreaIdCol = 0;
  int generateDescCol = 0;
  int packagingCol = 0;
  int vendorCol = 0;
  int descCol = 0;
  int vendorProfileCol = 0;
  int wasteTagCol = 0;
  final String NEED_DEL_TO = "Select one";
  final int CONTAINER_TABLE_CONTAINER_ID_COL = 0;
  final int CONTAINER_TABLE_ACCUMULATION_DATE_COL = 1;

  // gui variables
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel topP = new JPanel();
  JPanel middleP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel facilityL = new StaticJLabel();
  DataJLabel facT = new DataJLabel();
  StaticJLabel accSysL = new StaticJLabel();
  StaticJLabel requesterL = new StaticJLabel();
  StaticJLabel lppL = new StaticJLabel();
  //DataJLabel dateReqL = new DataJLabel();
  DataJLabel accSysT = new DataJLabel();
  DataJLabel reqT = new DataJLabel();
  DataJLabel priceT = new DataJLabel();
  JButton submitB = new JButton();
  JButton delWRB = new JButton();
  JButton saveRejB = new JButton();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane lineJsp = new JScrollPane();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  StaticJLabel ePriceL = new StaticJLabel();
  StaticJLabel transferFrom = new StaticJLabel();

  FacilityCombo facC = new FacilityCombo();
  JComboBox transferFromC = new JComboBox();
 // JComboBox dockC = new JComboBox();  //*****

  JButton delLineB = new JButton();
  JPanel chargeP = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JScrollPane chargeJsp = new JScrollPane();
  ButtonGroup bg1 = new ButtonGroup();
  ButtonGroup bg2 = new ButtonGroup();
  JRadioButton directRB = new JRadioButton();
  JRadioButton indirectRB = new JRadioButton();

  WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
 // CheckBoxCellRenderer checkTableRenderer = new CheckBoxCellRenderer();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout5 = new GridBagLayout();
  JPanel jPanel3 = new JPanel();
  GridBagLayout gridBagLayout6 = new GridBagLayout();
  JComboBox transferToC = new JComboBox();
  StaticJLabel transferTo = new StaticJLabel();
  JButton printLabelB = new JButton();
  JPanel containerP = new JPanel();
  JScrollPane containerJsp = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JTextField dateSealF = new JTextField();
  StaticJLabel dateSealedL = new StaticJLabel();
  StaticJLabel dateL = new StaticJLabel();
  StaticJLabel transferDateL = new StaticJLabel();
  JRadioButton amR = new JRadioButton();
  JTextField transferDateT = new JTextField();
  JRadioButton pmR = new JRadioButton();
  JPanel rightP = new JPanel();
  GridBagLayout gridBagLayout7 = new GridBagLayout();
  JPanel leftP = new JPanel();
  GridBagLayout gridBagLayout8 = new GridBagLayout();
  JButton ccardB = new JButton();
  JComboBox poC = new JComboBox();
  StaticJLabel poL = new StaticJLabel("BPO/PO: ");
  JPanel poP = new JPanel();
  StaticJLabel refL = new StaticJLabel("Line: ");
  JTextField refT = new JTextField();

  GridBagLayout gridBagLayout9 = new GridBagLayout();

  public WastePickupRequest(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void setPrNum(int rNum){
    this.rNum = rNum;
  }

  public void setRequestDataH(Hashtable h){
    this.requestDataH = h;
  }

  public void jbInit() throws Exception{
    this.setLayout(gridBagLayout1);


    jPanel1.setMaximumSize(new Dimension(300, 150));
    jPanel1.setPreferredSize(new Dimension(250, 230));
    jPanel1.setMinimumSize(new Dimension(120, 170));

    chargeP.setMaximumSize(new Dimension(200, 150));
    chargeP.setPreferredSize(new Dimension(100, 130));
    chargeP.setMinimumSize(new Dimension(120, 120));
    directRB.setText("Direct");
    directRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        directRB_actionPerformed(e);
      }
    });
    indirectRB.setText("Indirect");
    indirectRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        indirectRB_actionPerformed(e);
      }
    });

    jPanel1.setLayout(gridBagLayout5);
    delWRB.setActionCommand("Delete WR");
    jPanel3.setLayout(gridBagLayout6);
    transferToC.setMaximumSize(new Dimension(190, 21));
    transferToC.setMinimumSize(new Dimension(190, 21));
    transferToC.setPreferredSize(new Dimension(190, 21));
    transferTo.setFont(new java.awt.Font("Dialog", 0, 10));
    transferTo.setText("Transfer To:");
    printLabelB.setText("Print Labels");
    printLabelB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        printLabelB_actionPerformed(e);
      }
    });
    containerP.setMaximumSize(new Dimension(120, 120));
    containerP.setMinimumSize(new Dimension(120, 120));
    containerP.setPreferredSize(new Dimension(120, 120));
    containerP.setLayout(borderLayout2);
    containerJsp.setMaximumSize(new Dimension(120, 120));
    containerJsp.setMinimumSize(new Dimension(120, 120));
    containerJsp.setPreferredSize(new Dimension(120, 120));
    /*poC.setMaximumSize(new Dimension(150, 21));
    poC.setMinimumSize(new Dimension(150, 21));
    poC.setPreferredSize(new Dimension(150, 21));
    poL.setFont(new java.awt.Font("Dialog", 0, 10));
    poL.setText("Po Number:");  */
    dateSealF.setMaximumSize(new Dimension(80, 21));
    dateSealF.setMinimumSize(new Dimension(80, 21));
    dateSealF.setPreferredSize(new Dimension(80, 21));
    dateSealedL.setFont(new java.awt.Font("Dialog", 0, 10));
    dateSealedL.setText("Date Sealed:");
    dateL.setFont(new java.awt.Font("Dialog", 0, 10));
    dateL.setText("mm/dd/yyyy");
    transferDateL.setText("Requested Transfer:");
    amR.setText("AM");
    amR.setFont(new java.awt.Font("Dialog", 0, 10));
    amR.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        amR_actionPerformed(e);
      }
    });
    transferDateT.setMaximumSize(new Dimension(80, 21));
    transferDateT.setMinimumSize(new Dimension(80, 21));
    transferDateT.setPreferredSize(new Dimension(80, 21));
    pmR.setText("PM");
    pmR.setFont(new java.awt.Font("Dialog", 0, 10));
    pmR.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        pmR_actionPerformed(e);
      }
    });
    rightP.setLayout(gridBagLayout7);
    leftP.setLayout(gridBagLayout8);
    ccardB.setMaximumSize(new Dimension(200, 23));
    ccardB.setMinimumSize(new Dimension(200, 23));
    ccardB.setPreferredSize(new Dimension(200, 23));
    ccardB.setText("Credit Card Information");
    ccardB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        ccardB_actionPerformed(e);
      }
    });
    poP.setLayout(gridBagLayout9);
    poC.setMaximumSize(new Dimension(32767, 21));
    poC.setMinimumSize(new Dimension(124, 21));
    poC.setPreferredSize(new Dimension(128, 21));
    bg2.add(directRB);
    bg2.add(indirectRB);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    repaint();
    topP.setMaximumSize(new Dimension(32767, 100));
    topP.setPreferredSize(new Dimension(10, 100));
    topP.setLayout(gridBagLayout2);
    topP.setMinimumSize(new Dimension(10, 100));
    middleP.setMaximumSize(new Dimension(32767, 100));
    middleP.setPreferredSize(new Dimension(450, 160));
    middleP.setLayout(borderLayout1);
    middleP.setMinimumSize(new Dimension(10, 100));
    ePriceL.setText("99,999 @ 99.99 / LUPP = 99,999.99");
    ePriceL.setHorizontalAlignment(SwingConstants.CENTER);
    ePriceL.setMaximumSize(new Dimension(200, 13));
    ePriceL.setPreferredSize(new Dimension(200, 13));
    ePriceL.setMinimumSize(new Dimension(200, 13));

    transferFrom.setText("Transfer From:");
    transferFrom.setHorizontalAlignment(SwingConstants.RIGHT);
    transferFromC.setMaximumSize(new Dimension(190, 21));
    transferFromC.setPreferredSize(new Dimension(190, 21));
    transferFromC.setMinimumSize(new Dimension(190, 21));
    transferFromC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        transferFromC_actionPerformed(e);
      }
    });

    delLineB.setMaximumSize(new Dimension(125, 23));
    delLineB.setText("Delete Line");
    delLineB.setPreferredSize(new Dimension(125, 23));
    delLineB.setMinimumSize(new Dimension(125, 23));
    delLineB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delLineB_actionPerformed(e);
      }
    });
    jPanel2.setLayout(gridBagLayout3);
    facilityL.setText("Facility:");
    facilityL.setHorizontalAlignment(SwingConstants.RIGHT);
    facilityL.setMaximumSize(new Dimension(42, 15));
    facT.setMaximumSize(new Dimension(100, 19));
    facT.setPreferredSize(new Dimension(100, 19));
    facT.setMinimumSize(new Dimension(100, 19));
    chargeP.setLayout(borderLayout3);

    //jLabel5.setText("Date Requested:");
    accSysL.setText("Accounting System:");
    accSysL.setHorizontalAlignment(SwingConstants.RIGHT);
    requesterL.setText("Requester:");
    requesterL.setHorizontalAlignment(SwingConstants.RIGHT);
    lppL.setText("Extended LPP:");
    lppL.setHorizontalAlignment(SwingConstants.RIGHT);
    /*
    dateReqL.setMaximumSize(new Dimension(100, 15));
    dateReqL.setPreferredSize(new Dimension(100, 15));
    dateReqL.setMinimumSize(new Dimension(100, 15));
    */
    accSysT.setMaximumSize(new Dimension(100, 15));
    accSysT.setPreferredSize(new Dimension(100, 15));
    accSysT.setMinimumSize(new Dimension(100, 15));
    reqT.setMaximumSize(new Dimension(100, 15));
    reqT.setPreferredSize(new Dimension(100, 15));
    reqT.setMinimumSize(new Dimension(100, 15));
    priceT.setMaximumSize(new Dimension(100, 15));
    priceT.setPreferredSize(new Dimension(100, 15));
    priceT.setMinimumSize(new Dimension(100, 15));
    submitB.setText("Submit");

    //ePriceL.setFont(new Font(ePriceL.getFont().getName(),ePriceL.getFont().getStyle(),ePriceL.getFont().getSize() -2));
    submitB.setPreferredSize(new Dimension(125, 23));
    submitB.setMinimumSize(new Dimension(125, 23));
    submitB.setMaximumSize(new Dimension(125, 23));
    submitB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitB_actionPerformed(e);
      }
    });
    delWRB.setMaximumSize(new Dimension(125, 23));
    delWRB.setText("Delete WR");
    delWRB.setPreferredSize(new Dimension(125, 23));
    delWRB.setMinimumSize(new Dimension(125, 23));
    delWRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delWRB_actionPerformed(e);
      }
    });
    saveRejB.setMaximumSize(new Dimension(125, 23));
    saveRejB.setText("Save");
    saveRejB.setPreferredSize(new Dimension(125, 23));
    saveRejB.setMinimumSize(new Dimension(125, 23));
    saveRejB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveRejB_actionPerformed(e);
      }
    });

    chargeP.setLayout(new BorderLayout());  //****

    jPanel2.setPreferredSize(new Dimension(325, 100));
    jPanel2.setMinimumSize(new Dimension(325, 100));

    //jPanel2.setPreferredSize(new Dimension(412, 100));
    //jPanel2.setMinimumSize(new Dimension(412, 100));
    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(facilityL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 3), 0, 0));
    topP.add(facT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 1, 3), 0, 0));
    topP.add(accSysL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 5), 0, 0));
    topP.add(requesterL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 3), 0, 0));
    topP.add(lppL, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(accSysT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(reqT, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(priceT, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(submitB, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(delWRB, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(saveRejB, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(delLineB, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(ccardB, new GridBagConstraints(4, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));

    this.add(middleP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 1, 3), 0, 0));
    middleP.add(lineJsp, BorderLayout.CENTER);

    this.add(jPanel2, new GridBagConstraints2(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 6, 5, 0), 0, 0));





    /* before trong
    jPanel1.add(transferFromC, new GridBagConstraints2(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
    jPanel1.add(transferFrom, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 5, 0, 15), 0, 0));
    jPanel1.add(transferToC, new GridBagConstraints2(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
    jPanel1.add(transferTo, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel1.add(dateSealedL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(dateSealF, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(dateL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 85, 0, 0), 0, 0));
    */

    /*jPanel1.add(poC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
    jPanel1.add(poL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 5, 5, 15), 0, 0));
    */
    rightP.add(chargeP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 6, 10, 0), 0, 0));
    chargeP.add(chargeJsp, BorderLayout.CENTER);
    jPanel2.add(leftP, new GridBagConstraints2(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    leftP.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 3, 3), 0, 0));
    jPanel1.add(transferFromC, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
    jPanel1.add(transferFrom, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 0, 5), 0, 0));
    jPanel1.add(transferToC, new GridBagConstraints2(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
    jPanel1.add(transferTo, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 0, 5), 0, 0));
    jPanel1.add(dateSealedL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 0, 5), 0, 0));
    jPanel1.add(dateSealF, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
    jPanel1.add(dateL, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 85, 0, 0), 0, 0));
    jPanel1.add(transferDateL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 3, 5), 0, 0));
    jPanel1.add(amR, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 85, 0, 0), 0, 0));
    jPanel1.add(transferDateT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(pmR, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 125, 0, 0), 0, 0));
    leftP.add(containerP, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    containerP.add(containerJsp, BorderLayout.CENTER);
    containerP.add(printLabelB, BorderLayout.NORTH);
    jPanel2.add(rightP, new GridBagConstraints(0, 0, 1, 1, 0.5, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(-5, 0, 5, 5), 0, 0));
    rightP.add(jPanel3, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jPanel3.add(ePriceL, new GridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(3, 0, 0, 3), 0, 0));
    jPanel3.add(indirectRB, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(3, 5, 0, 3), 0, 0));
    jPanel3.add(directRB, new GridBagConstraints2(0, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(3, 5, 0, 3), 0, 0));

    /*
    jPanel2.add(transferDateL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    jPanel2.add(amR, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel2.add(transferDateT, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel2.add(pmR, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 50, 0, 0), 0, 0));
    */
    setButtonText(true);
  }

   public void printScreenData(){
    forceStopEditing();

    saveCurrentDetail();

    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",cmis);
      Hashtable query = new Hashtable();
      query.put("USER_ID",cmis.getUserId().toString());     //String, String
      query.put("ACTION","PRINT_SCREEN");         //String, String
      query.put("DETAIL_INFO",lineItems);     //String, Vector
      query.put("DATE",headerInfo.get("DATE").toString());      //String, String
      query.put("FAC_NAME",headerInfo.get("FAC_NAME").toString());    //String, String
      query.put("SELECTED_LINE",selectedLine);    //String, Integer
      query.put("REQ_NUM",(new Integer(rNum))); //String, Integer
      headerInfo.remove("TABLE");
      headerInfo.put("EXTENDED_LPP",BothHelpObjs.makeBlankFromNull(priceT.getText()));     //String, String
      query.put("HEADER_INFO",headerInfo);        //String, Hashtable
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Hashtable h = (Hashtable)result.get("RETURN_CODE");
      Boolean b = (Boolean)h.get("SUCCEEDED");
      if (b.booleanValue()) {
        try {
          // From the stand alone application
          String url = (String)result.get("URL");
          new URLCall(url,cmis);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(h.get("MSG").toString());
        gd.setVisible(true);
      }

    }catch (Exception ee) {
      ee.printStackTrace();
    }

    this.repaint();
    this.revalidate();

  }

  public void loadIt(){
    WastePickupRequestLoadThread iT = new WastePickupRequestLoadThread(this);
    iT.start();
  }

  public void loadItAction(){
    ClientHelpObjs.setEnabledContainer(this,false);
    //this is a kludge to stop the screen from freezing occaisionally
    try{
      Thread.sleep(200);
    }catch(Exception e){}

    loadingScreenData = true;
    try{
      Hashtable result = null;
      if (requestDataH == null) {
        TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",cmis);
        Hashtable query = new Hashtable();
        query.put("ACTION","GET_WASTE_REQUEST_DATA");
        query.put("REQ_NUM",(new Integer(rNum)));
        result = cgi.getResultHash(query);
      }else {
        result = requestDataH;
      }

      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      now = (Calendar)result.get("NOW");
      Hashtable screenData = (Hashtable)result.get("SCREEN_DATA");

      lineItems = (Vector)screenData.get("LINE_ITEMS");
      headerInfo = (Hashtable)screenData.get("HEADER_INFO");

      Hashtable NewCatalogData = new Hashtable();
      headerInfo.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));

      selectedLine = new Integer(1);
      wasteManager = (Boolean)screenData.get("WASTE_MANAGER");
      wastePickup = (Boolean)screenData.get("WASTE_PICKUP");
      containerId = (Hashtable)screenData.get("CONTAINER_TABLE");

      //getting column
      Hashtable keyCols = (Hashtable)result.get("COLUMN_KEY");
      qtyCol = Integer.parseInt((String) keyCols.get("Qty"));
      notesCol = Integer.parseInt((String) keyCols.get("Notes"));
      profileCol = Integer.parseInt((String) keyCols.get("Rad Profile"));
      replaceContainerCol = Integer.parseInt((String) keyCols.get("Replace Container"));
      pickupCol = Integer.parseInt((String) keyCols.get("Transferred"));
      workAreaIdCol = Integer.parseInt((String) keyCols.get("Work Area Id"));
      generateDescCol = Integer.parseInt((String) keyCols.get("Work Area"));
      packagingCol = Integer.parseInt((String) keyCols.get("Packaging"));
      vendorCol = Integer.parseInt((String) keyCols.get("Vendor"));
      descCol = Integer.parseInt((String) keyCols.get("Description"));
      vendorProfileCol = Integer.parseInt((String) keyCols.get("Vendor Profile"));
      wasteTagCol = Integer.parseInt((String) keyCols.get("Waste Tag #"));

      buildHeaderSection();
      buildLineSummaryTable();
      setLabelsEnabled(true);
      setViewLevel();
      this.repaint();
      loadingScreenData = false;
      this.revalidate();
      this.repaint();

      // Save initial settings on Hash
      Integer requesterId = (Integer)headerInfo.get("REQ_ID");
      if("REQUEST".equalsIgnoreCase(headerInfo.get("VIEW_TYPE").toString()) && requesterId.equals(cmis.getUserId())){
        saveDefaultData();
      }

    }catch(Exception e){e.printStackTrace();}
    ClientHelpObjs.makeDefaultColors(this);
    ClientHelpObjs.setComboBoxUpdateUi(this);
  } //end of method

  void setLabelsEnabled(boolean b){
    facilityL.setEnabled(b);
    accSysL.setEnabled(b);
    requesterL.setEnabled(b);
    lppL.setEnabled(b);
    facT.setEnabled(b);
   // noteL.setEnabled(b);
   // noteT.setEnabled(b);
    //dateReqL.setEnabled(b);
    accSysT.setEnabled(b);
    transferTo.setEnabled(b);
    printLabelB.setEnabled(b);
    transferToC.setEnabled(b);
    dateSealF.setEnabled(b);
    dateSealedL.setEnabled(b);
    dateL.setEnabled(b);
    //poC.setEnabled(b);
    //poL.setEnabled(b);
    transferDateT.setEnabled(b);
    transferDateL.setEnabled(b);
    amR.setEnabled(b);
    pmR.setEnabled(b);

    reqT.setEnabled(b);
    priceT.setEnabled(b);
    transferFrom.setEnabled(b);
    transferFromC.setEnabled(b);
    ePriceL.setEnabled(b);
  }

  void buildHeaderSection(){
    String status = headerInfo.get("STATUS_DESC").toString();
    String reqNum = headerInfo.get("REQ_NUM").toString();
    String date = headerInfo.get("DATE").toString();
    topP.setBorder(ClientHelpObjs.groupBox("Waste Management Request - "+reqNum+" ("+status+")      " +date));

    facT.setText(headerInfo.get("FAC_NAME").toString());
    //dateReqL.setText(headerInfo.get("DATE").toString());
    accSysT.setText(headerInfo.get("ACC_SYS_ID").toString());
    reqT.setText(headerInfo.get("REQ_NAME").toString());
    setExtendedPrice();
    setButtonText(((Integer)headerInfo.get("REQ_ID")).intValue() == cmis.getUserId().intValue());
  }

  DbTableModel buildTableModel() {
    String[] colHeads = (String[])headerInfo.get("TABLE_HEADERS");
    int[] colWidths = (int[]) headerInfo.get("TABLE_WIDTHS");
    int[] colTypes = (int[]) headerInfo.get("TABLE_TYPES");
    Vector data = (Vector) headerInfo.get("TABLE_DATA");

    DbTableModel ctm = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      ctm.addRow((Object[]) data.elementAt(i));
    }
    ctm.setColumnPrefWidth(colWidths);
    ctm.setColumnType(colTypes);
    return ctm;
  }  //end of method

  void buildLineSummaryTable(){
    loadingSummary = true;
    middleP.setBorder(ClientHelpObjs.groupBox("Waste Request Line Items"));

    DbTableModel model = buildTableModel();
    //if the user is a waste manager/pickup then allow him/her to select picked up column
    int num = 0;
    if (wasteManager.booleanValue() || wastePickup.booleanValue()) {
      num = mypow(2,pickupCol) + mypow(2,qtyCol) + mypow(2,wasteTagCol);
      model.setEditableFlag(num);
    } else { //otherwise, only allow user to edit replace_container column.
      num = mypow(2,qtyCol) + mypow(2,wasteTagCol);
      model.setEditableFlag(num);
    }

    middleP.remove(lineJsp);
    sorter = new TableSorter(model);
    rliTable = new CmisTable(sorter);
    sorter.setColTypeFlag(model.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(rliTable);
    rliTable.setCellSelectionEnabled(false);
    rliTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    rliTable.getTableHeader().setReorderingAllowed(false);
    rliTable.getTableHeader().setResizingAllowed(true);
    rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();
    WasteCheckBoxCellRenderer checkTableRenderer = new WasteCheckBoxCellRenderer();
    rliTable.setDefaultRenderer(String.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Double.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Boolean.class, checkTableRenderer);
    // need to go back to allow waste managers to create and pickup waste with one step
    JCheckBox check = new JCheckBox();
    check.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          check_actionPerformed(e);
        }
      });
    check.setHorizontalAlignment(JLabel.CENTER);
    rliTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));

    rliTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e){
        listSelectionChanged();
      }
    });
    rliTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseClicked(MouseEvent e) {
        tableClicked(e);
      }
    });

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)headerInfo.get("TABLE_STYLE");
    Color color = (Color)chargeTableStyle.get("COLOR");
    Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    rliTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = rliTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    lineJsp = new JScrollPane(rliTable);
    for(int i=0;i<rliTable.getColumnCount();i++){
      if (model.getColumnWidth(i)==0){
         rliTable.getColumn(rliTable.getColumnName(i)).setWidth(model.getColumnWidth(i));
         rliTable.getColumn(rliTable.getColumnName(i)).setMaxWidth(model.getColumnWidth(i));
         rliTable.getColumn(rliTable.getColumnName(i)).setMinWidth(model.getColumnWidth(i));
      }
      rliTable.getColumn(rliTable.getColumnName(i)).setPreferredWidth(model.getColumnWidth(i));
    }
    rliTable.validate();
    rliTable.revalidate();
    rliTable.repaint();
    lineJsp.validate();
    middleP.add(lineJsp);
    middleP.validate();

    loadingSummary = false;

    if(loadingScreenData){
      setSelectedRow(selectedLine.intValue()-1);
    }
    buildDetailSection();
  }

  public int mypow(int y, int x) {
   int value = 1;
   for (int i = 0; i < x; i++) {
    value *= y;
   }
   return value;
  }

  void setSelectedRow(int x){
    ListSelectionModel lsm = rliTable.getSelectionModel();
    lsm.setSelectionInterval(x,x);
    rliTable.scrollRectToVisible(rliTable.getCellRect(x,0,false));
  }

  //2-8-01
  void setSelectedRowForChargeTable(int row,int col){
    chargeNumTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    chargeNumTable.setCellSelectionEnabled(false);
    ListSelectionModel clsm = chargeNumTable.getSelectionModel();
    clsm.setSelectionInterval(row,row);
    chargeNumTable.scrollRectToVisible(chargeNumTable.getCellRect(row,0,false));
    chargeNumTable.requestFocus();
    chargeNumTable.editCellAt(row,0);
  }


  void setExtendedPrice(){
    double f = 0.00;
    for(int i=0;i<lineItems.size();i++){
      Hashtable h = (Hashtable)lineItems.elementAt(i);
      try{
        if(h.get("UNIT_PRICE").toString().equalsIgnoreCase("requested")) continue;
        int qty = Integer.parseInt(h.get("QTY").toString());

        //not even worry about that right now  2-11-00
        //float unitPrice = Float.valueOf(h.get("UNIT_PRICE").toString()).floatValue();
        float price = (Float.valueOf(h.get("UNIT_PRICE").toString()).floatValue()) * qty;
        f = f + price;
      }catch(Exception e){e.printStackTrace();}
    }
    priceT.setText(formatPrice(f)+" "+headerInfo.get("CURRENCY_ID").toString());
  }

  String formatPrice(double d){
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setMinimumIntegerDigits(1);
    return nf.format(d).toString();
  }

  void saveDefaultData() {
    //first go ahead and save the first row data
    saveCurrentDetail();
    if (rliTable.getRowCount() > 1) {
      for (int i = 1; i < rliTable.getRowCount(); i++) {
        selectedLine = new Integer(i+1);
        buildDetailSection();
        saveCurrentDetail();
      }
      selectedLine = new Integer(1);
      buildDetailSection();
    }
  } //end of method

  void saveCurrentDetail(){
    if(loadingScreenData){
      return;
    }
    Hashtable h = getItemHash(selectedLine);
    int row = 0;
    for(int i=0;i<rliTable.getRowCount();i++){
      String s = rliTable.getModel().getValueAt(i,1).toString();
      Integer I = new Integer(s);
      if(I.intValue() == selectedLine.intValue()){
        row = i;
      }
    }
    //qty & notes & radian_profile_id
    h.put("QTY",new Integer(rliTable.getModel().getValueAt(row,qtyCol).toString()));
    h.put("REPLACE_CONTAINER",new Integer(rliTable.getModel().getValueAt(row,replaceContainerCol).toString()));
    h.put("PROFILE_ID",new Integer(rliTable.getModel().getValueAt(row,profileCol).toString()));
    h.put("TRANSFERRED",new Boolean(rliTable.getModel().getValueAt(row,pickupCol).toString()));
    h.put("WORK_AREA_ID",new String(rliTable.getModel().getValueAt(row,workAreaIdCol).toString()));
    h.put("VENDOR",new String(rliTable.getModel().getValueAt(row,vendorCol).toString()));
    h.put("VENDOR_PROFILE",new String(rliTable.getModel().getValueAt(row,vendorProfileCol).toString()));
    h.put("PACKAGING",new String(rliTable.getModel().getValueAt(row,packagingCol).toString()));
    h.put("DESCRIPTION",new String(rliTable.getModel().getValueAt(row,descCol).toString()));
    h.put("WASTE_TAG",new String(rliTable.getModel().getValueAt(row,wasteTagCol).toString()));
    if (headerInfo.get("VIEW_TYPE").toString().equalsIgnoreCase("REQUEST")) {
      Hashtable locH = (Hashtable)h.get("WASTE_LOCATION");
      Vector fromFacilityId = (Vector)locH.get("FROM_FACILITY_ID");
      Vector fromLocationId = (Vector)locH.get("FROM_LOCATION");
      Vector toFacilityId = (Vector)locH.get("TO_FACILITY_ID");
      Vector toLocationId = (Vector)locH.get("TO_LOCATION");

      h.put("FROM_LOCATION_SELECTED",transferFromC.getSelectedItem());
      int pos = transferFromC.getSelectedIndex();
      h.put("FROM_FACILITY_SELECTED_ID",fromFacilityId.elementAt(pos));
      h.put("FROM_LOCATION_SELECTED_ID",fromLocationId.elementAt(pos));
      h.put("TO_LOCATION_SELECTED",transferToC.getSelectedItem());
      pos = transferToC.getSelectedIndex();
      h.put("TO_FACILITY_SELECTED_ID",toFacilityId.elementAt(pos));
      h.put("TO_LOCATION_SELECTED_ID",toLocationId.elementAt(pos));
    }else {
      h.put("FROM_LOCATION_SELECTED",transferFromC.getSelectedItem());
      h.put("TO_LOCATION_SELECTED",transferToC.getSelectedItem());
    }
    h.put("SEAL_DATE",BothHelpObjs.makeBlankFromNull(dateSealF.getText()));

    h.put("REQUESTED_TRANSFER_DATE",BothHelpObjs.makeBlankFromNull(transferDateT.getText()));
    h.put("REQUESTED_TRANSFER_TIME_AM",new Boolean(amR.isSelected()));

    //container id and accumulation start date
    int line = selectedLine.intValue() - 1;
    //Vector containerV = (Vector)containerId.get("LINE"+line+"ID");
    //h.put("CONTAINER_IDS",containerV);
    Vector containerV = new Vector(containerTable.getRowCount());
    Vector aDate = new Vector(containerTable.getRowCount());
    for (int jj = 0; jj < containerTable.getRowCount(); jj++) {
      containerV.addElement(containerTable.getValueAt(jj,CONTAINER_TABLE_CONTAINER_ID_COL).toString());
      aDate.addElement(containerTable.getValueAt(jj,CONTAINER_TABLE_ACCUMULATION_DATE_COL).toString());
    }
    h.put("CONTAINER_IDS",containerV);
    h.put("ACCUMULATION_DATE",aDate);
    containerId.put("LINE"+line+"ID",containerV);
    containerId.put("LINE"+line+"DATE",aDate);

    //charge type
    if( directRB.isVisible()) {
      h.put("CHARGE_TYPE",directRB.isSelected()?"d":"i");
    }else{
      h.put("CHARGE_TYPE","d");
    }

    //po_required
    String type = (String)h.get("CHARGE_TYPE");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String s = (String)accSysTypeH.get("PO_REQUIRED");
    String prAccR = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if(s.equalsIgnoreCase("p")) {
      if(poC.getItemCount() > 0 ) {
         h.put("PO",poC.getSelectedItem().toString());
      }else{
         h.put("PO","");
      }
      h.put("RELEASE_NUM",refT.getText());
    }else{
      h.put("PO","");
      h.put("RELEASE_NUM","");
    }

    //chargenum
    if ("y".equalsIgnoreCase(prAccR)) {   //trong 7-19
      Vector v = new Vector();
      h.put("CHARGE_NUM",v);
      v = new Vector();
      int numCols = chargeNumTable.getColumnCount();
      for(int i=0;i<chargeNumTable.getRowCount();i++){
        Hashtable f = new Hashtable();
        int mc = 0;
        f.put("ACCT_NUM_1",chargeNumTable.getModel().getValueAt(i,mc++).toString().trim());
        if(numCols == 3){
          f.put("ACCT_NUM_2",chargeNumTable.getModel().getValueAt(i,mc++).toString().trim());
        }
        f.put("PERCENTAGE",chargeNumTable.getModel().getValueAt(i,mc++).toString().trim());
        v.addElement(f);
      }
      if(h.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")){
        h.put("CHARGE_NUM_INDIRECT",v);
      }else if(h.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")){
        h.put("CHARGE_NUM_DIRECT",v);
      }else{
        System.out.println("SHOULDN'T BE HERE");
      }
    } //end of if
  }  //end of method

  void buildDetailSection(){
    loadingDetail = true;
    jPanel2.setBorder(ClientHelpObjs.groupBox("Line Item Detail - Line "+selectedLine.toString()));

    // reset componentes
    String temp = "";
    Hashtable h = getItemHash(selectedLine);

    Integer I = new Integer(h.get("QTY").toString());
    //not worry about the actual price right now 2-11-00
    Double D = new Double(h.get("UNIT_PRICE").toString());
    //Double D = new Double(164.00);
    setLineExtPrice(I.intValue(),D.doubleValue(),h.get("CURRENCY_ID").toString());

    String sealDate = (String)h.get("SEAL_DATE");
    dateSealF.setText(sealDate);

    String requestedTransferDate = (String)h.get("REQUESTED_TRANSFER_DATE");
    transferDateT.setText(requestedTransferDate);
    Boolean selectAm = (Boolean)h.get("REQUESTED_TRANSFER_TIME_AM");
    if (selectAm.booleanValue()) {
      amR.setSelected(true);
      pmR.setSelected(false);
    }else {
      pmR.setSelected(true);
      amR.setSelected(false);
    }

    //trong 5/11
    Hashtable myH = (Hashtable)headerInfo.get("ACC_TYPE_H");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable)myH.get(accSysId);

    String numChargeTypes = (String)innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);

    String viewType = headerInfo.get("VIEW_TYPE").toString();
    if (viewType.equalsIgnoreCase("request")) {
      Vector chargeTypesV = (Vector)innerH.get("CHARGE_TYPE");
      String myChargeType1 = "";
      String myChargeType2 = "";
      String myDirectChargeType = "";
      String myIndirectChargeType = "";
      if (chargeTypesV.size() == 1) {              //only have direct charge type
        myChargeType1 = (String)chargeTypesV.firstElement();
      }else if (chargeTypesV.size() == 2) {       //container both direct and indirect charge type
        myChargeType1 = (String)chargeTypesV.firstElement();
        myChargeType2 = (String)chargeTypesV.lastElement();
      }
      //making sure that I assign the right charge type
      if (!BothHelpObjs.isBlankString(myChargeType1)) {
        if (myChargeType1.equalsIgnoreCase("d")) {
          myDirectChargeType = myChargeType1;
        }else if (myChargeType1.equalsIgnoreCase("i")) {
          myIndirectChargeType = myChargeType1;
        }
      }
      if (!BothHelpObjs.isBlankString(myChargeType2)) {
        if (myChargeType2.equalsIgnoreCase("d")) {
          myDirectChargeType = myChargeType2;
        }else if (myChargeType2.equalsIgnoreCase("i")) {
          myIndirectChargeType = myChargeType2;
        }
      }
      String directedChargeI = "";
      String directedChargeD = "";
      if (!BothHelpObjs.isBlankString(myDirectChargeType)) {
        directedChargeD = (String)h.get("DIRECTED_TYPE"+myDirectChargeType);
      }
      if (!BothHelpObjs.isBlankString(myIndirectChargeType)) {
        directedChargeI = (String)h.get("DIRECTED_TYPE"+myIndirectChargeType);
      }

      if( number.intValue()== 2) {
        directRB.setVisible(true);
        indirectRB.setVisible(true);
        String di = h.get("CHARGE_TYPE").toString();
        if (di.equalsIgnoreCase("d")) {
          if (directedChargeI.equalsIgnoreCase("B")) {
            directRB.setSelected(true);
            directRB.setEnabled(false);
            indirectRB.setEnabled(false);
          }else {
            directRB.setSelected(true);
            directRB.setEnabled(true);
            indirectRB.setEnabled(true);
          }
        }else {
          if (directedChargeD.equalsIgnoreCase("B")) {
            indirectRB.setSelected(true);
            directRB.setEnabled(false);
            indirectRB.setEnabled(false);
          }else {
            indirectRB.setSelected(true);
            directRB.setEnabled(true);
            indirectRB.setEnabled(true);
          }
        }
      }else{
        directRB.setVisible(false);
        indirectRB.setVisible(false);
      }
    }else {
      if( number.intValue()== 2) {
        directRB.setVisible(true);
        indirectRB.setVisible(true);
        String di = h.get("CHARGE_TYPE").toString();
        if(BothHelpObjs.isBlankString(di) || di.equalsIgnoreCase("d")){
          directRB.setSelected(true);
        }else{
          indirectRB.setSelected(true);
        }
        directRB.setEnabled(viewType.equalsIgnoreCase("request"));
        indirectRB.setEnabled(viewType.equalsIgnoreCase("request"));
      }else{
        directRB.setVisible(false);
        indirectRB.setVisible(false);
      }
    }

    //from locations drop down
    Hashtable locH = (Hashtable)h.get("WASTE_LOCATION");
    Vector fromFacilityId = (Vector)locH.get("FROM_FACILITY_ID");
    Vector fromLocation = (Vector)locH.get("FROM_DESC");
    Vector fromLocationId = (Vector)locH.get("FROM_LOCATION");
    if (!fromLocationId.elementAt(0).toString().equalsIgnoreCase("Select One") && fromLocationId.size() > 1) {
      fromFacilityId.insertElementAt("Select One",0);
      fromLocationId.insertElementAt("Select One",0);
      fromLocation.insertElementAt("Select One",0);
    }
    //load drop down
    transferFromC.removeAllItems();
    transferFromC = ClientHelpObjs.loadChoiceFromVector(transferFromC,fromLocation);
    //look to see if one is preselected
    String fromSelectedFacilityId = (String)h.get("FROM_FACILITY_SELECTED_ID");
    String fromLocSel = (String)h.get("FROM_LOCATION_SELECTED_ID");
    if (BothHelpObjs.isBlankString(fromLocSel)) {
      fromLocSel = (String)h.get("WASTE_LOCATION_ID");
    }
    int s = 0;
    for (int x = 0; x < fromLocationId.size(); x++) {
      String tmpx = fromLocationId.elementAt(x).toString();
      if (!BothHelpObjs.isBlankString(fromSelectedFacilityId)) {
        String tmpf = fromFacilityId.elementAt(x).toString();
        if (tmpx.equals(fromLocSel) && tmpf.equals(fromSelectedFacilityId)) {
          s = x;
          break;
        }
      }else {
        if (tmpx.equals(fromLocSel)) {
          s = x;
          break;
        }
      }
    }
    fromLocSel = fromLocation.elementAt(s).toString();
    transferFromC.setSelectedItem(fromLocSel);

    //to locations drop down
    Vector toFacilityId = (Vector)locH.get("TO_FACILITY_ID");
    Vector toLocation = (Vector)locH.get("TO_DESC");
    Vector toLocationId = (Vector)locH.get("TO_LOCATION");
    if (!toLocationId.elementAt(0).toString().equalsIgnoreCase("Select One") && toLocationId.size() > 1) {
      toFacilityId.insertElementAt("Select One",0);
      toLocationId.insertElementAt("Select One",0);
      toLocation.insertElementAt("Select One",0);
    }
    //load drop down
    transferToC.removeAllItems();
    transferToC = ClientHelpObjs.loadChoiceFromVector(transferToC,toLocation);
    //look to see if one is preselected
    String toSelectedFacilityId = (String)h.get("TO_FACILITY_SELECTED_ID");
    String toLocSel = (String)h.get("TO_LOCATION_SELECTED_ID");
    if (!BothHelpObjs.isBlankString(toLocSel)) {
      int p = 0;
      for (int j = 0; j < toLocationId.size(); j++) {
        String tmp = toLocationId.elementAt(j).toString();
        if (!BothHelpObjs.isBlankString(toSelectedFacilityId)) {
          String tmpf = toFacilityId.elementAt(j).toString();
          if (tmp.equals(toLocSel) && tmpf.equals(toSelectedFacilityId)) {
            p = j;
            break;
          }
        }else {
          if (tmp.equals(toLocSel)) {
            p = j;
            break;
          }
        }
      }
      toLocSel = toLocation.elementAt(p).toString();
      transferToC.setSelectedItem(toLocSel);
    }

    //po_required
    String type = (String)h.get("CHARGE_TYPE");
    if (BothHelpObjs.isBlankString(type)) {
      type = "d";
    }
    Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H");
    type = type.toLowerCase();
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String needPo = (String)accSysTypeH.get("PO_REQUIRED");

    //6-17-02 use the same logic as the MR screen
    //11-06-01 don't know how to handle po at this point
    if(needPo.equalsIgnoreCase("p")){
      Vector v = (Vector)h.get("ALL_POS");
      if(!BothHelpObjs.isBlankString((String)h.get("PO"))){
        if(!v.contains(h.get("PO").toString())){
          v.addElement(h.get("PO").toString());
        }
      }
      v = BothHelpObjs.sort(v);
      poC = ClientHelpObjs.loadChoiceFromVector(poC,v);
      if(!BothHelpObjs.isBlankString((String)h.get("PO"))){
        poC.setSelectedItem(h.get("PO").toString());
      }else{
        poC.setSelectedIndex(0);
      }
      poC.setEditable(false);
      poC.validate();
      //release number
      if(BothHelpObjs.isBlankString(h.get("RELEASE_NUM").toString())) {
        refT.setText("");
      }else{
        refT.setText(h.get("RELEASE_NUM").toString());
      }

      chargeP.remove(chargeJsp);
      poP.add(poL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 3, 3, 3), 0, 0));
      poP.add(poC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 3), 0, 0));
      poP.add(refL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 3, 3, 3), 0, 0));
      poP.add(refT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 3), 0, 0));
      chargeJsp = new JScrollPane(poP);
      chargeJsp.setBorder(null);
      chargeP.add(chargeJsp,BorderLayout.CENTER);
      chargeP.validate();

      refT.setEnabled(false);
      ccardB.setEnabled(false);
    }
    if (needPo.equalsIgnoreCase("c")) {
      ccardB.setEnabled(true);
    }else {
      ccardB.setEnabled(false);
    }

    //don't show credit card info to anyone after submitted
    if (!viewType.equalsIgnoreCase("request")) {
      ccardB.setEnabled(false);
    }

    //10-30-01 it turn out that we do need to record charge info for waste
    String prAccountRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prAccountRequired.equalsIgnoreCase("y")) {
      buildChargeNumTable();
      chargeP.setVisible(true);
    //11-06-01 don't know how to handle PO at this point
    }else if ("p".equalsIgnoreCase(needPo) || "b".equalsIgnoreCase(needPo)) {
      chargeP.setVisible(true);
    }else {
      chargeP.setVisible(false);
    }

    //trong 5/11
    buildContainerTable();
    loadingDetail = false;
  }

  void buildContainerTable(){
    int line = selectedLine.intValue() - 1;
    String[] colHeads = {"ID","Date"};
    int[] colWidths = {45,75};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,BothHelpObjs.TABLE_COL_TYPE_STRING};
    DbTableModel model = new DbTableModel(colHeads);
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    model.setEditableFlag(BothHelpObjs.mypow(2, CONTAINER_TABLE_ACCUMULATION_DATE_COL));
    Vector containerV = (Vector)containerId.get("LINE"+line+"ID");
    Vector aDate = (Vector)containerId.get("LINE"+line+"DATE");
    for (int i = 0; i < containerV.size(); i++) {
      Object[] oa = new Object[colHeads.length];
      oa[CONTAINER_TABLE_CONTAINER_ID_COL] = containerV.elementAt(i).toString();
      oa[CONTAINER_TABLE_ACCUMULATION_DATE_COL] = aDate.elementAt(i).toString();
      model.addRow(oa);
    }
    containerP.remove(containerJsp);
    containerTable = new CmisTable(model);
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)headerInfo.get("TABLE_STYLE");
    Color color = (Color)chargeTableStyle.get("COLOR");
    Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    //columns width
    for(int i=0;i<containerTable.getColumnCount();i++){
      containerTable.getColumn(containerTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }

    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    containerTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = containerTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    containerTable.setCellSelectionEnabled(true);
    containerJsp = new JScrollPane(containerTable);
    //containerTable.revalidate();
    //containerTable.repaint();
    containerP.add(containerJsp);
    containerTable.validate();
    containerJsp.validate();
    containerP.validate();
  }

  void buildEmptyChargeNumTable(){
    chargeP.setVisible(true);   //trong 7-19
    Hashtable tmph = getItemHash(selectedLine);
    String[] chargeCols = new String[]{};

    //trong 4/7
    Hashtable myH = (Hashtable)headerInfo.get("ACC_TYPE_H");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable)myH.get(accSysId);
    Hashtable accTypeH = (Hashtable)headerInfo.get("PACK");

    Vector chargeV = new Vector();
    Vector colHeader = new Vector();

    String numChargeTypes = (String)innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);
    String mt = "";
    if (number.intValue() == 2) {
      if (directRB.isSelected()) {
        mt = "d";
      }else {
        mt = "i";
      }
    }else {
      mt = "d";
    }
    String accType = accSysId + mt;
    Hashtable hh = (Hashtable)accTypeH.get(accType);
    colHeader = (Vector)hh.get("LABELS");

    chargeCols = new String[colHeader.size()];
    for(int i=0;i<colHeader.size();i++){
      chargeCols[i] = colHeader.elementAt(i).toString();
    }

    // determine the number of rows
    int numRows = 2;
    Object[][] oa = new Object[numRows][colHeader.size()];

    // set all columns to empty strings
    for(int i=0;i<oa.length;i++){
      for(int j=0;j<chargeCols.length;j++){
        oa[i][j] = "";
      }
    }

    int[] colWidths = new int[]{0};
    if(colHeader.size() == 3){
      colWidths = new int[]{71,71,48};
    }else{
      colWidths = new int[]{142,48};
    }

    CmisTableModel ctm;
    ctm = new CmisTableModel(chargeCols,oa);

    chargeNumTable = new JTable(ctm);
    chargeNumTable.setCellSelectionEnabled(true);

    chargeP.remove(chargeJsp);
    chargeP.validate();
    chargeJsp = new JScrollPane(chargeNumTable);
    chargeP.add(chargeJsp,BorderLayout.CENTER);
    rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    for(int i=0;i<chargeNumTable.getColumnCount();i++){
      chargeNumTable.getColumn(chargeNumTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }
    chargeNumTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseClicked(MouseEvent e) {
        int selectedRow = chargeNumTable.getSelectedRow();
        buildChargeNumTable();
        setSelectedRowForChargeTable(selectedRow,0);
      }
    });
    chargeNumTable.validate();
    chargeJsp.validate();
    chargeP.validate();
  }

  void buildChargeNumTable(){
    Hashtable tmph = getItemHash(selectedLine);
    String[] chargeCols = new String[]{};

    Hashtable myH = (Hashtable)headerInfo.get("ACC_TYPE_H");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable)myH.get(accSysId);
    Hashtable accTypeH = (Hashtable)headerInfo.get("PACK");
    String viewType = headerInfo.get("VIEW_TYPE").toString();

    Vector chargeV = new Vector();
    Vector colHeader = new Vector();

    Integer editable = new Integer(0);
    String numChargeTypes = (String)innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);
    if( number.intValue()== 2) {
      if(directRB.isSelected()){
        if (viewType.equalsIgnoreCase("REQUEST")) {
          if (tmph.get("DIRECTED_TYPEd").toString().equalsIgnoreCase("Y")) {
            chargeV = (Vector)tmph.get("DIRECTED_CHARGE_NUMBERd");
          }else {
            chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
          }
        }else {
          chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
        }
        String accType = accSysId + "d";
        Hashtable hh = (Hashtable)accTypeH.get(accType);
        colHeader = (Vector)hh.get("LABELS");
        editable = (Integer)hh.get("EDIT_TABLE");
      }else{
        if (viewType.equalsIgnoreCase("REQUEST")) {
          if (tmph.get("DIRECTED_TYPEi").toString().equalsIgnoreCase("Y")) {
            chargeV = (Vector)tmph.get("DIRECTED_CHARGE_NUMBERi");
          }else {
            chargeV = (Vector)tmph.get("CHARGE_NUM_INDIRECT");
          }
        }else {
          chargeV = (Vector)tmph.get("CHARGE_NUM_INDIRECT");
        }
        String accType = accSysId + "i";
        Hashtable hh = (Hashtable)accTypeH.get(accType);
        colHeader = (Vector)hh.get("LABELS");
        editable = (Integer)hh.get("EDIT_TABLE");
      }
    }else{
      if (viewType.equalsIgnoreCase("REQUEST")) {
        if (tmph.get("DIRECTED_TYPEd").toString().equalsIgnoreCase("Y")) {
          chargeV = (Vector)tmph.get("DIRECTED_CHARGE_NUMBERd");
        }else {
          chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
        }
      }else {
        chargeV = (Vector)tmph.get("CHARGE_NUM_DIRECT");
      }
      String accType = accSysId + "d";
      Hashtable hh = (Hashtable)accTypeH.get(accType);
      colHeader = (Vector)hh.get("LABELS");
      editable = (Integer)hh.get("EDIT_TABLE");
    }

    chargeCols = new String[colHeader.size()];
    for(int i=0;i<colHeader.size();i++){
      chargeCols[i] = colHeader.elementAt(i).toString();
    }

    // determine the number of rows
    int numRows = 0;
    if(viewType.equalsIgnoreCase("REQUEST")){
      boolean canEditD = false;
      boolean canEditI = false;
      if( number.intValue()== 2) {
        canEditD = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        canEditI = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_I")).booleanValue();
        if (directRB.isSelected()) {
          if (canEditD) {
            numRows = 35;
          }else {
            numRows = chargeV.size();
          }
        }else {
          if (canEditI) {
            numRows = 35;
          }else {
            numRows = chargeV.size();
          }
        }
      }else {
        canEditD = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        if (canEditD) {
          numRows = 35;
        }else {
          numRows = chargeV.size();
        }
      }
    }else{
      numRows = chargeV.size();
    }
    Object[][] oa = new Object[numRows][colHeader.size()];

    // set all columns to empty strings
    for(int i=0;i<oa.length;i++){
      for(int j=0;j<chargeCols.length;j++){
        oa[i][j] = "";
      }
    }
    for(int i=0;i<chargeV.size();i++){
      Hashtable h = (Hashtable)chargeV.elementAt(i);
      int r = 0;
      oa[i][r++] = h.get("ACCT_NUM_1").toString();
      if(colHeader.size() == 3){
        oa[i][r++] = h.get("ACCT_NUM_2").toString();
      }
      //if there is only one charge number then set default percent to 100
      if (chargeV.size() == 1) {
        oa[i][r++] = "100";
      }else {
        oa[i][r++] = h.get("PERCENTAGE").toString();
      }
    }
    int[] colWidths = new int[]{0};
    if(colHeader.size() == 3){
      colWidths = new int[]{71,71,48};
    }else{
      colWidths = new int[]{142,48};
    }

    DbTableModel ctm = new DbTableModel(chargeCols,oa);
    if(viewType.equalsIgnoreCase("REQUEST")){
      boolean bd = false;
      boolean bi = false;
      String myDI = "";
      String myDD = "";
      if( number.intValue()== 2) {
        bd = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        bi = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_I")).booleanValue();
        myDI = (String)tmph.get("DIRECTED_TYPEi");
        myDD = (String)tmph.get("DIRECTED_TYPEd");
        if (directRB.isSelected()) {
          if (bd) {
            if(chargeCols.length == 2) ctm.setEditableFlag(3);
            if(chargeCols.length == 3) ctm.setEditableFlag(editable.intValue());
          }else {
            if (myDD.equalsIgnoreCase("Y")) {
              ctm.setEditableFlag(0);
            }else {
              if(chargeCols.length == 2) ctm.setEditableFlag(2);
              if(chargeCols.length == 3) ctm.setEditableFlag(4);
            }
          }
        }else {
          if (bi) {
            if(chargeCols.length == 2) ctm.setEditableFlag(3);
            if(chargeCols.length == 3) ctm.setEditableFlag(editable.intValue());
          }else {
            if (myDI.equalsIgnoreCase("Y")) {
              ctm.setEditableFlag(0);
            }else {
              if(chargeCols.length == 2) ctm.setEditableFlag(2);
              if(chargeCols.length == 3) ctm.setEditableFlag(4);
            }
          }
        }
      }else {
        bd = ((Boolean)tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        myDD = (String)tmph.get("DIRECTED_TYPEd");
        if (bd) {
          if(chargeCols.length == 2) ctm.setEditableFlag(3);
          if(chargeCols.length == 3) ctm.setEditableFlag(editable.intValue());
        }else {
          if (myDD.equalsIgnoreCase("Y")) {
            ctm.setEditableFlag(0);
          }else {
            if(chargeCols.length == 2) ctm.setEditableFlag(2);
            if(chargeCols.length == 3) ctm.setEditableFlag(4);
          }
        }
      }
    }else{
      ctm.setEditableFlag(0);
    }
    //chargeNumTable = new JTable(ctm);
    TableSorter sorter = new TableSorter(ctm);
    chargeNumTable = new CmisTable(sorter);
    sorter.setColTypeFlag(ctm.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(chargeNumTable);

    //Nawaz 09/19/01
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)headerInfo.get("TABLE_STYLE");
    Color color = (Color)chargeTableStyle.get("COLOR");
    Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    chargeNumTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = chargeNumTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    chargeNumTable.setCellSelectionEnabled(true);

    chargeP.remove(chargeJsp);
    chargeP.validate();
    chargeJsp = new JScrollPane(chargeNumTable);
    chargeP.add(chargeJsp,BorderLayout.CENTER);
    //rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    for(int i=0;i<chargeNumTable.getColumnCount();i++){
      chargeNumTable.getColumn(chargeNumTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }

    chargeNumTable.validate();
    chargeJsp.validate();
    chargeP.validate();
  }

  Hashtable getItemHash(Integer line){
    String s = line.toString();
    for(int i=0;i<lineItems.size();i++){
      Hashtable r = (Hashtable)lineItems.elementAt(i);
      if(r.get("LINE_NUM").toString().equals(s))return r;
    }
    return new Hashtable();
  }

  boolean submitRequest(String action){
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",cmis);
      Hashtable query = new Hashtable();
      query.put("USER_ID",cmis.getUserId().toString());
      query.put("ACTION",action);
      query.put("DETAIL_INFO",lineItems);
      query.put("DATE",headerInfo.get("DATE").toString());
      query.put("FAC_NAME",headerInfo.get("FAC_NAME").toString());
      query.put("SELECTED_LINE",selectedLine);
      query.put("REQ_NUM",(new Integer(rNum))); //String, String
      headerInfo.remove("TABLE");
      query.put("HEADER_INFO",headerInfo);

      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return false;
      }

      Hashtable h = (Hashtable)result.get("RETURN_CODE");
      Boolean b = (Boolean)h.get("SUCCEEDED");
      if(!b.booleanValue()){
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg(h.get("MSG").toString());
        gd.setVisible(true);
      }
      ClientHelpObjs.setComboBoxUpdateUi(this);
      return b.booleanValue();
    }catch(Exception e){
       e.printStackTrace();
    }
    return false;
  } //end of method

  void chickenPotPie(){
    // do this to make sure all the item hashtables have info entered
    // I couldn't think of a better name
    for(int i=0;i<lineItems.size();i++){
      Hashtable h = getItemHash(new Integer(i+1));
      if(!h.contains("DELIVERY_QTY") || h.get("DELIVERY_QTY") == null){
        h.put("DELIVERY_QTY",new String(h.get("QTY").toString()));
      }
      if(!h.contains("DEL_TYPE") || h.get("DEL_TYPE") == null){
        h.put("DEL_TYPE","On or before");
      }
      if(!h.contains("DEL_FREQ") || h.get("DEL_FREQ") == null){
        h.put("DEL_FREQ","Once");
      }
    }
  }

  void goDeleteWR(){
    ConfirmDlg md = new ConfirmDlg(cmis.getMain(),"Delete Waste Request",true);
    md.setMsg("Do you want to delete this waste request?");
    md.setVisible(true);
    if(md.getConfirmationFlag()){
      if(submitRequest("DELETE_WR")){
        cmis.getMain().goWasteRequest(2,rNum);
      }else{
        cmis.getMain().goWasteRequest(0,rNum);
      }
    }
  }

  void goDeleteLine(){
    DbTableModel model = (DbTableModel)headerInfo.get("TABLE");
    if(lineItems.size() == 1){
      ConfirmDlg cd = new ConfirmDlg(cmis.getMain(),"Delete Waste Request",true);
      cd.setMsg("There is only one line on this waste request, delete this waste request anyway?");
      cd.setVisible(true);
      if(cd.getConfirmationFlag()){
        if(submitRequest("DELETE_WR")){
          cmis.getMain().goWasteRequest(2,rNum);
        }else{
          cmis.getMain().goWasteRequest(0,rNum);
        }
      }
      return;
    }

    ConfirmDlg md = new ConfirmDlg(cmis.getMain(),"Delete Line Item",true);
    md.setMsg("Do you want to delete line item "+selectedLine.toString()+"?");
    md.setVisible(true);
    if(md.getConfirmationFlag()){
      submitRequest("DELETE_LINE");
      cmis.getMain().goWasteRequest(0,rNum);
    }
  }

  void goSaveWR(){
    forceStopEditing();

    saveCurrentDetail();
    if (submitRequest("SAVE")) {
      cmis.getMain().goWasteRequest(0,rNum);
    }
  }
  void goSubmitRequest(){
    if(headerInfo.get("VIEW_TYPE").toString().equalsIgnoreCase("VIEW"))return;

    forceStopEditing();

    saveCurrentDetail();

    if(!preSubmitAudits()){
      return;
    }

    if (submitRequest("SUBMIT")) {
      cmis.getMain().goWasteRequest(0,rNum);
    }
  }


  void auditFailed(int i){
    selectedLine = new Integer(i);
    setSelectedRow(selectedLine.intValue()-1);
    buildDetailSection();
  }

  boolean preSubmitAudits(){
    String msg = "";
    while(true){
      // do any header info audits first...
      // if there are any
      // now do line item audits
      for(int i=0;i<lineItems.size();i++){
        Hashtable r = (Hashtable)lineItems.elementAt(i);

        // From and To audits
        if(r.get("FROM_LOCATION_SELECTED").toString().equalsIgnoreCase("Select One")) {
          msg = "Please select 'Transfer From' for this line item(s).";
          auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
          break;
        }
        if(r.get("TO_LOCATION_SELECTED").toString().equalsIgnoreCase("Select One")){
          msg = "Please select 'Transfer To' for this line item(s).";
          auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
          break;
        }

        // make sure user enter seal date
        String sealD = BothHelpObjs.makeBlankFromNull(r.get("SEAL_DATE").toString());
        if(BothHelpObjs.isBlankString(sealD)) {
          msg = "Please enter 'Seal Date' for this line item(s).";
          auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
          break;
        }else if (!BothHelpObjs.isDateFormatRight(sealD)) {
          msg = "Please enter 'Seal Date' in the following format 'mm/dd/yyyy'.";
          auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
          break;
        }
        String requestedTransferD = BothHelpObjs.makeBlankFromNull(r.get("REQUESTED_TRANSFER_DATE").toString());
        if(BothHelpObjs.isBlankString(requestedTransferD)) {
          msg = "Please enter 'Requested Transfer Date' for this line item(s).";
          auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
          break;
        }else if (!BothHelpObjs.isDateFormatRight(requestedTransferD)) {
          msg = "Please enter 'Requested Transfer Date' in the following format 'mm/dd/yyyy'.";
          auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
          break;
        }
        //accumulation start date - make sure user enters date in the right format
        Vector aDate = (Vector)r.get("ACCUMULATION_DATE");
        Vector containerId = (Vector)r.get("CONTAINER_IDS");
        for (int j = 0; j < aDate.size(); j++) {
          if (!BothHelpObjs.isBlankString((String)aDate.elementAt(j))) {
            if (!BothHelpObjs.isDateFormatRight((String)aDate.elementAt(j))) {
              msg = "Please enter 'Date' for container #"+containerId.elementAt(j).toString()+" in the following format 'mm/dd/yyyy'.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
          }
        }

        // charge num audit
        Vector cn = new Vector();
        if(r.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")){
          cn = (Vector)r.get("CHARGE_NUM_INDIRECT");
        }else if(r.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")){
          cn = (Vector)r.get("CHARGE_NUM_DIRECT");
        }

        int cTotal = 0;
        String accSysId = (String)headerInfo.get("ACC_SYS_ID");
        String type = (String)r.get("CHARGE_TYPE");
        Hashtable pack = (Hashtable)headerInfo.get("PACK");
        String key =  accSysId + type;
        Hashtable innerH = (Hashtable)pack.get(key);
        String pra = (String)innerH.get("PR_ACCOUNT_REQUIRED");
        String needPo = (String)innerH.get("PO_REQUIRED");
        if ("y".equalsIgnoreCase(pra)) {
          //holder so I can check to see if user type in the same charge number
          Vector chargeNumberAuditV = new Vector(cn.size());
          for(int j=0;j<cn.size();j++){
            Hashtable cHash = (Hashtable)cn.elementAt(j);
            String a1 = cHash.get("ACCT_NUM_1").toString();
            String a2;
            if(cHash.get("ACCT_NUM_2") == null){
              a2 = "";
            }else{
              a2 = cHash.get("ACCT_NUM_2").toString();
            }
            String ps = cHash.get("PERCENTAGE").toString();
            if(BothHelpObjs.isBlankString(ps))continue;

            //users can't enter the same charge number twice
            if (chargeNumberAuditV.contains(a1+a2)) {
              msg = "You entered the same charge number twice.\nPlease check your charge numbers and re-submit.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
            chargeNumberAuditV.addElement(a1+a2);

            //users must enter a percentage for charge number
            if(BothHelpObjs.isBlankString(a1) &&
              BothHelpObjs.isBlankString(a2)){
              if(BothHelpObjs.isBlankString(ps)){
                continue;
              }else{
                msg = "You must enter a charge number with each percentage.";
                auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                break;
              }
            }

            int p = 0;
            try{
              p = Integer.parseInt(ps);
            }catch(Exception e){
              msg = "You must enter only numbers in the percentage column.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
            if(p<0){
              msg = "You cannot enter negative numbers in the percentage column.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
            cTotal = cTotal + p;
          }
        } //end of pr_account_required
        //audit credit card info
        if ("c".equalsIgnoreCase(needPo)) {
          Hashtable creditCardInfo = (Hashtable)headerInfo.get("CREDIT_CARD_INFO");
          String creditCardNumber = (String)creditCardInfo.get("CREDIT_CARD_NUMBER");
          if (BothHelpObjs.isBlankString(creditCardNumber)) {
            msg = "Please enter credit card information for the given line item.";
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          }else {
            break;
          }
        } //end of credit card audit

        if(BothHelpObjs.isBlankString(msg)){
          if(!pra.equalsIgnoreCase("y")){
            continue;
          }
          if(cTotal != 100){
            msg = "The sum of charge percentages must be 100.";
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          }
        }else{
          break;
        }
      }
      break;
    }
    if(msg.length() > 0){
      GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
      gd.setMsg(msg);
      gd.setVisible(true);
      return false;
    }
    return true;
  }

  void forceStopEditing(){
    if(rliTable.isEditing()){
      rliTable.getCellEditor().stopCellEditing();
    }
    if (chargeNumTable != null) {
      if(chargeNumTable.isEditing()){
        chargeNumTable.getCellEditor().stopCellEditing();
      }
    }
    if (containerTable != null) {
      if(containerTable.isEditing()){
        containerTable.getCellEditor().stopCellEditing();
      }
    }
  }

  void setViewLevel(){
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    String status   = headerInfo.get("STATUS_DESC").toString();
    boolean t = true;
    boolean f = false;
    Integer requesterId = (Integer)headerInfo.get("REQ_ID");
    if(viewType.equalsIgnoreCase("REQUEST") && requesterId.equals(cmis.getUserId())){
      submitB.setEnabled(t);
      delWRB.setEnabled(t);
      saveRejB.setEnabled(t);
      delLineB.setEnabled(t);
      printLabelB.setEnabled(!"Draft".equalsIgnoreCase(status));
      transferFromC.setEnabled(t);
      transferToC.setEnabled(t);
      dateSealF.setEnabled(t);
      transferDateT.setEnabled(t);
      amR.setEnabled(t);
      pmR.setEnabled(t);
    }else{
      submitB.setEnabled(false);
      delWRB.setEnabled(false);
      saveRejB.setEnabled(false);
      delLineB.setEnabled(false);
      transferFromC.setEnabled(false);
      transferToC.setEnabled(false);
      printLabelB.setEnabled(!"Draft".equalsIgnoreCase(status));
      dateSealF.setEnabled(false);
      transferDateT.setEnabled(false);
      amR.setEnabled(false);
      pmR.setEnabled(false);
    }
    this.revalidate();
  }

  void setButtonText(boolean isRequester){
    delLineB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","DeleteItem"));
    if(isRequester){
      submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));
      submitB.setText("Submit");
      saveRejB.setIcon(ResourceLoader.getImageIcon("images/button/save.gif","Save"));
      saveRejB.setText("Save");
      delWRB.setIcon(ResourceLoader.getImageIcon("images/button/deleterequest.gif","DeleteRequest"));
      delWRB.setText("Delete");
    }else{
      submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));
      submitB.setText("Submit");
      saveRejB.setIcon(ResourceLoader.getImageIcon("images/button/save.gif","Save"));
      saveRejB.setText("Save");
      delWRB.setIcon(ResourceLoader.getImageIcon("images/button/deleterequest.gif","DeleteRequest"));
      delWRB.setText("Delete");
    }
  }

  void submitB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    goSubmitRequest();
  }

  void setLineExtPrice(int i, double d, String currencyID){
    Double D = new Double(d);
    double dd = i * d;
    ePriceL.setText(i+" @ "+formatPrice(D.doubleValue())+"/Unit = "+formatPrice(dd)+" "+currencyID);
  }

  void transferFromC_actionPerformed(ActionEvent e) {
      //System.out.println("***do something");
  }
  void saveRejB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    goSaveWR();
  }

  void delWRB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goDeleteWR();
  }

  void delLineB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    goDeleteLine();
  }

  void check_actionPerformed(ActionEvent e) {
    rliTable.repaint();
  }

  void tableClicked(MouseEvent e) {
    int col = ((JTable)e.getSource()).columnAtPoint(new Point(e.getX(),e.getY()));
    int row = ((JTable)e.getSource()).rowAtPoint(new Point(e.getX(),e.getY()));

    if (e.isMetaDown()) {
      doRightClicked(e);
    }

    deletedRow = row;
   // selectedLine = new Integer(row+1);
    if(row < 0) return; // this happens if there is only one line and the user
                        // makes a selection from the PO combo box

    String viewType = headerInfo.get("VIEW_TYPE").toString();

    if (rliTable.isEditing()){
      rliTable.getCellEditor().stopCellEditing();
    }
    this.tableEditingStopped();       //trong 8-17
    //this.setSelectedRow(row);

    if(col == notesCol && e.getClickCount() > 1){
      EditDlg RjW = new EditDlg(cmis.getMain(),"Enter Notes",true, "Enter notes:");
      Hashtable h = getItemHash(selectedLine);
     // Hashtable h = getItemHash(new Integer(deletedRow+1));
      String tmp = h.get("NOTES").toString();
      if(!BothHelpObjs.isBlankString(tmp))RjW.setText(tmp);
      RjW.setVisible(true);
      if(!BothHelpObjs.isBlankString(RjW.getText())){
        rliTable.getModel().setValueAt("   +",row,col);
        h.put("NOTES",RjW.getText());
      }else{
        rliTable.getModel().setValueAt("",row,col);
        h.put("NOTES","");
      }
      RjW.dispose();
    }
  }

   // this
  //protected void tableEditingStopped(int row,int col){
  protected void tableEditingStopped(){
    int changedRow = 0;
    for(int i=0;i<rliTable.getRowCount();i++){
      Hashtable h = getItemHash(new Integer(i+1));
      String s = rliTable.getModel().getValueAt(i,qtyCol).toString();
      if(!s.equals(h.get("QTY").toString())){
        try{
          int x = Integer.parseInt(s);
          if(x < 1){
            GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
            gd.setMsg("You must enter a valid integer greater than zero for quantity.");
            gd.setVisible(true);
            return;
          }
        }catch(Exception e){
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg("You must enter a valid integer greater than zero for quantity.");
          gd.setVisible(true);
          return;
        }
        Integer I = new Integer(s);
        h.put("QTY",I.toString());

        changedRow = i+1;

        if(rliTable.getModel().getValueAt(i,1).toString().equals(h.get("LINE_NUM").toString())){
          //not to worry about unit_price now
          Double D = new Double(h.get("UNIT_PRICE").toString());
          //Double D = new Double(164.00);
          setLineExtPrice(I.intValue(),D.doubleValue(),h.get("CURRENCY_ID").toString());
        }
      }
    }
    setExtendedPrice();

    if (changedRow != 0) {
      selectedLine = new Integer(changedRow);
      goChangeQty();
    }
  }
  void goChangeQty(){
    forceStopEditing();

    saveCurrentDetail();
    if (submitRequest("CHANGE_QTY")) {
      cmis.getMain().goWasteRequest(0,rNum);
    }
  }



  private void doRightClicked(MouseEvent e){
     //Table clicked
    JFrame F;
    int catSel = rliTable.getSelectedRow();
    WasteMetaPopUp mpu = new WasteMetaPopUp(cmis,
          Integer.parseInt(rliTable.getModel().getValueAt(catSel,profileCol).toString())); // Waste Profile Id

    panel1.add(mpu);
    Vector myContIdV = new Vector();
    myContIdV.addElement(containerTable.getModel().getValueAt(0,0).toString());
    mpu.setContainerId(myContIdV);
    mpu.setWastePickupRequest(this);
    mpu.setFromWastePickupRequest(true);
    mpu.setFacilityId(this.facT.getText());
    mpu.setPackaging(rliTable.getModel().getValueAt(catSel,packagingCol).toString());    //packaging
    mpu.enableMenu("ChgProfile");
    mpu.show(rliTable,e.getX(),e.getY());
    return;
  }

  void listSelectionChanged() {
    int col = rliTable.getSelectedColumn();
    int row = rliTable.getSelectedRow();
    int clickedLineNum = Integer.parseInt(rliTable.getModel().getValueAt(row,1).toString());
    //trong 7-19
    Hashtable h = getItemHash(selectedLine);
    String type = (String)h.get("CHARGE_TYPE");
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String prAccountRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if(selectedLine.intValue() == clickedLineNum ){
    }else{
      forceStopEditing();
      tableEditingStopped();    //trong 8-25
      saveCurrentDetail();
      selectedLine = new Integer(clickedLineNum);
      buildDetailSection();
    }
    //System.out.println("================== GOT HERE");
  }

  void chargeTypeChanged(){
     //trong 10-30-01
    String type = "";
    if (directRB.isSelected()) {
      type = "d";
    }else {
      type = "i";
    }
    Hashtable h = getItemHash(selectedLine);
    String accSysId = (String)headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable)headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable)packH.get(key1);
    String prAccountRequired = (String)accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    String poRequired = (String)accSysTypeH.get("PO_REQUIRED");
    if (prAccountRequired.equalsIgnoreCase("y")) {
      buildEmptyChargeNumTable();
    }else {
      chargeP.setVisible(false);
    }
    if (poRequired.equalsIgnoreCase("p")) {
      Vector v = (Vector)h.get("ALL_POS");
      if(!BothHelpObjs.isBlankString((String)h.get("PO"))){
        if(!v.contains(h.get("PO").toString())){
          v.addElement(h.get("PO").toString());
        }
      }
      v = BothHelpObjs.sort(v);
      if (poC.getItemCount() > 0) poC.removeAllItems();
      poC = ClientHelpObjs.loadChoiceFromVector(poC,v);
      if(!BothHelpObjs.isBlankString((String)h.get("PO"))){
        poC.setSelectedItem(h.get("PO").toString());
      }else{
        poC.setSelectedIndex(0);
      }
      ccardB.setEnabled(false);
      poC.setEditable(false);

      //release number
      if(BothHelpObjs.isBlankString(h.get("RELEASE_NUM").toString())) {
        refT.setText("");
      }else{
        refT.setText(h.get("RELEASE_NUM").toString());
      }

      chargeP.remove(chargeJsp);
      poP.add(poL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 3, 3, 3), 0, 0));
      poP.add(poC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 3), 0, 0));
      poP.add(refL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 3, 3, 3), 0, 0));
      poP.add(refT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 3), 0, 0));
      chargeJsp = new JScrollPane(poP);
      chargeJsp.setBorder(null);
      chargeP.add(chargeJsp,BorderLayout.CENTER);
      chargeP.validate();
      chargeP.setVisible(true);
    }else if (poRequired.equalsIgnoreCase("c")) {
      ccardB.setEnabled(true);
    }else {
      ccardB.setEnabled(false);
    }
  }


  void indirectRB_actionPerformed(ActionEvent e) {
    chargeTypeChanged();
  }
  void directRB_actionPerformed(ActionEvent e) {
    chargeTypeChanged();
  }

  //trong 8-7
  void printLabelB_actionPerformed(ActionEvent e) {
    int line = selectedLine.intValue() - 1;
    if(rliTable.isEditing()){
      rliTable.getCellEditor().stopCellEditing();
    }
    tableEditingStopped();
    setSelectedRow(line);
    Vector containerV = (Vector)containerId.get("LINE"+line+"ID");
    GetLabelTypeDlg getLabelTypeDlg = new GetLabelTypeDlg(cmis.getMain(),"Select a label");
    getLabelTypeDlg.setParent(cmis);
    CenterComponent.centerCompOnScreen(getLabelTypeDlg);
    getLabelTypeDlg.setVisible(true);
    if (getLabelTypeDlg.okClicked()) {
      String labelType = "traveler";
      if (getLabelTypeDlg.hazardousRB.isSelected()) {
        labelType = "hazardous";
      }
      goUrl(containerV,labelType);
    }
  }

  public void goUrl(Vector containerId, String labelType) {
    int kind = 0;
    kind = URLGrab.LABEL;
    ClientHelpObjs.goLabelURL(cmis,this.cmis.getResourceBundle().getString("APP_NAME"),containerId,kind,labelType);
  }

  void pmR_actionPerformed(ActionEvent e) {
    if (pmR.isSelected()) {
      amR.setSelected(false);
    }else {
      amR.setSelected(true);
    }
  }

  void amR_actionPerformed(ActionEvent e) {
    if (amR.isSelected()) {
      pmR.setSelected(false);
    }else {
      pmR.setSelected(true);
    }
  }

  void ccardB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    CreditCardInfoDlg cc = new CreditCardInfoDlg(cmis.getMain(),"Credit Card Information",this,(Hashtable)headerInfo.get("CREDIT_CARD_INFO"),(String)headerInfo.get("REQ_NAME"));
    cc.setParent(cmis);
    cc.loadIt();
    CenterComponent.centerCompOnScreen(cc);
    cc.setVisible(true);
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
  }


}

class WastePickupRequestLoadThread extends Thread {
 WastePickupRequest parent;
  public WastePickupRequestLoadThread(WastePickupRequest parent){
     super("WastePickupRequestLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

