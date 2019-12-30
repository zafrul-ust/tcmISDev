//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.borland.jbcl.layout.GridBagConstraints2;
import com.borland.jbcl.layout.VerticalFlowLayout;
import radian.tcmis.both1.helpers.AttributiveCellRenderer;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.both1.helpers.DbTableModel;
import radian.tcmis.both1.helpers.MultiLineHeaderRenderer;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import radian.tcmis.client.share.gui.delivery.DeliverySchedule;
import radian.tcmis.client.share.helpers.ClientHelpObjs;
import radian.tcmis.client.share.helpers.TableSorter;
import radian.tcmis.client.share.httpCgi.TcmisHttpConnection;
import java.awt.*;

public class RequestsWin extends JPanel {

  Integer selectedLine = new Integer(0);
  int rNum = 0;
  CmisApp cmis;
  Vector lineItems;
  Hashtable headerInfo;
  Vector delInfo;
  ReqTable rliTable;
  JTable matDescTable;
  CmisTableModel rliCtm;

  Hashtable requestDataH = null;
  //MultiSpanCellTable rliTableNew;
  int PART_COL = 0;
  int QTY_COL = 0;
  int ITEM_COL = 0;
  int NOTE_COL = 0;
  int DESC_COL = 0;
  int WORK_AREA_COL = 0;
  int CRITICAL_COL = 0;
  int CANCEL_COL = 0;
  int TYPE_COL = 0;
  int REQ_CANCEL_COL = 0;
  int USE_APPROVAL_STATUS_COL = 0;
  int DPAS_COL = 0;
  int LINE_COL = 0;
  int ORDER_QUANTITY_RULE_COL = 0;
  int CUSTOMER_REQUISITION_COL = 0;
  int LIST_APPROVAL_STATUS_COL = 0;
  Boolean dpasFlag = new Boolean(false);

  CmisTable chargeNumTable;

  Object[][] summaryData;
  String rejectionReason = "";
  Calendar now;
  boolean loadingDetail = false;
  boolean loadingSummary = false;
  boolean loadingScreenData = false;
  boolean updatingDelTypeC = false;
  boolean dataChanged = false;

  //trong 3/28
  Integer ronum = null;
  Integer rocol = null;
  Integer mylinenum1 = null;

  final String NEED_DEL_TO = "Select one";

  String[] descCols = new String[] {
      "Material Desc.", "Pkg", "Grade"};
  //                                     0            1      2

  // gui variables
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel topP = new JPanel();
  JPanel middleP = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  StaticJLabel jLabel1 = new StaticJLabel();
  StaticJLabel jLabel2 = new StaticJLabel();
  StaticJLabel jLabel3 = new StaticJLabel();
  DataJLabel facL = new DataJLabel();
  JTextField endUserT = new JTextField();
  JTextField deptT = new JTextField();
  StaticJLabel jLabel5 = new StaticJLabel();
  StaticJLabel jLabel7 = new StaticJLabel();
  DataJLabel dateL = new DataJLabel();
  DataJLabel priceL = new DataJLabel();
  JButton submitB = new JButton();
  JButton delMRB = new JButton();
  JButton saveRejB = new JButton();
  JButton scheduleB = new JButton();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane lineJsp = new JScrollPane();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  StaticJLabel ePriceL = new StaticJLabel();
  JPanel jPanel5 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane descJsp = new JScrollPane();
  StaticJLabel dockL = new StaticJLabel();
  JComboBox dockC = new JComboBox();
  StaticJLabel deliverC = new StaticJLabel();
  JComboBox delC = new JComboBox();
  JButton delLineB = new JButton();
  JPanel chargeP = new JPanel();
  JComboBox delTypeC = new JComboBox();
  JTextField dateT = new JTextField();
  BorderLayout borderLayout3 = new BorderLayout();
  JScrollPane chargeJsp = new JScrollPane();
  ButtonGroup bg1 = new ButtonGroup();
  ButtonGroup bg2 = new ButtonGroup();
  JRadioButton directRB = new JRadioButton();
  JRadioButton indirectRB = new JRadioButton();
  StaticJLabel jLabel4 = new StaticJLabel();
  StaticJLabel poL = new StaticJLabel();
  StaticJLabel refL = new StaticJLabel();
  JComboBox poC = new JComboBox();
  JTextField refT = new JTextField();
  JPanel delToP = new JPanel();
  JPanel deliveryP = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JButton cancelB = new JButton();

  //trong 4/7
  DataJLabel accSysIdL = new DataJLabel();

  ColorCellRenderer colorTableRenderer = new ColorCellRenderer();
  CheckBoxCellRenderer checkTableRenderer = new CheckBoxCellRenderer();
  JButton returnCartB = new JButton();
  JButton ccardB = new JButton();
  JCheckBox scrapC = new JCheckBox();
  //****************************************************************

  JButton financeB = new JButton();
  boolean useApprovalDlgOpen = false;
  boolean financeDlgOpen = false;
  boolean listApprovalDlgOpen = false;
  StaticJLabel jLabel8 = new StaticJLabel();
  JTextField contactT = new JTextField();

  Vector origMRQuantityV = new Vector();
  JTextField poT = new JTextField();

  JButton copyB = new JButton();
  JButton undoB = new JButton();

  StaticJLabel unitPriceL = new StaticJLabel();
  JTextField unitPriceT = new JTextField();
  JComboBox currencyC = new JComboBox();
  StaticJLabel poQtyL = new StaticJLabel();
  JTextField poQtyT = new JTextField();
  StaticJLabel poUomL = new StaticJLabel();
  JTextField poUomT = new JTextField();

  MRJTextFieldFocusListener mrJTextFieldFocusListener = null;
  RequestsWin_Currency_itemAdapter currencyItemAdapter;

  public RequestsWin(CmisApp cmis) {
    this.cmis = cmis;
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setPrNum(int rNum) {
    this.rNum = rNum;
  }

  public void setRequestDataH(Hashtable h) {
    this.requestDataH = h;
  }

  public void jbInit() throws Exception {
    jLabel4.setText("Del. Instructions:");
    delToP.setLayout(new BorderLayout());
    poL.setText("PO:");
    refL.setText("Line:");
    poC.setMaximumSize(new Dimension(130, 21));
    poC.setPreferredSize(new Dimension(130, 21));
    poC.setMinimumSize(new Dimension(130, 21));
    refT.setPreferredSize(new Dimension(80, 19));
    refT.setMaximumSize(new Dimension(80, 19));
    refT.setMinimumSize(new Dimension(80, 19));
    poT.setPreferredSize(new Dimension(130, 19));
    poT.setMaximumSize(new Dimension(130, 19));
    poT.setMinimumSize(new Dimension(130, 19));

    this.setLayout(gridBagLayout1);

    chargeP.setMaximumSize(new Dimension(300, 150));
    chargeP.setPreferredSize(new Dimension(150, 130));
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

    copyB.setText("Copy");
    copyB.setFont(new java.awt.Font("Dialog", 0, 10));
    copyB.setMaximumSize(new Dimension(65, 21));
    copyB.setMinimumSize(new Dimension(65, 21));
    copyB.setPreferredSize(new Dimension(65, 21));
    copyB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        copyB_actionPerformed(e);
      }
    });
    undoB.setText("Undo");
    undoB.setFont(new java.awt.Font("Dialog", 0, 10));
    undoB.setMaximumSize(new Dimension(75, 21));
    undoB.setMinimumSize(new Dimension(75, 21));
    undoB.setPreferredSize(new Dimension(75, 21));
    undoB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        undoB_actionPerformed(e);
      }
    });

    deliveryP.setLayout(borderLayout4);
    delTypeC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delTypeC_actionPerformed(e);
      }
    });
    cancelB.setFont(new java.awt.Font("Dialog", 0, 10));
    cancelB.setMaximumSize(new Dimension(115, 23));
    cancelB.setMinimumSize(new Dimension(115, 23));
    cancelB.setPreferredSize(new Dimension(115, 23));
    cancelB.setText("Request Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    scheduleB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        scheduleB_actionPerformed(e);
      }
    });
    scheduleB.setMaximumSize(new Dimension(80, 23));
    scheduleB.setMinimumSize(new Dimension(80, 23));
    scheduleB.setPreferredSize(new Dimension(80, 23));

    delC.setMaximumSize(new Dimension(100, 23));
    delC.setMinimumSize(new Dimension(100, 23));
    delC.setPreferredSize(new Dimension(100, 23));

    returnCartB.setFont(new java.awt.Font("Dialog", 0, 10));
    returnCartB.setMaximumSize(new Dimension(115, 23));
    returnCartB.setMinimumSize(new Dimension(115, 23));
    returnCartB.setPreferredSize(new Dimension(115, 23));
    returnCartB.setText("Return to Cart");
    returnCartB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        returnCartB_actionPerformed(e);
      }
    });
    ccardB.setFont(new java.awt.Font("Dialog", 0, 10));
    ccardB.setMaximumSize(new Dimension(250, 23));
    ccardB.setMinimumSize(new Dimension(250, 23));
    ccardB.setPreferredSize(new Dimension(250, 23));
    ccardB.setText("Enter/Edit Credit Card Information");
    ccardB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ccardB_actionPerformed(e);
      }
    });
    saveRejB.setFont(new java.awt.Font("Dialog", 0, 10));
    delMRB.setFont(new java.awt.Font("Dialog", 0, 10));
    delLineB.setFont(new java.awt.Font("Dialog", 0, 10));
    submitB.setFont(new java.awt.Font("Dialog", 0, 10));
    scrapC.setText(" Scrap/Obsolete");
    scrapC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        scrapC_actionPerformed(e);
      }
    });
    deliveryP.setMinimumSize(new Dimension(80, 23));
    deliveryP.setPreferredSize(new Dimension(80, 23));

    financeB.setText("Ext. Unit Price :");
    financeB.setMaximumSize(new Dimension(90, 30));
    financeB.setMinimumSize(new Dimension(90, 30));
    financeB.setPreferredSize(new Dimension(90, 30));
    financeB.setRequestFocusEnabled(false);
    financeB.setHorizontalAlignment(SwingConstants.RIGHT);
    financeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        financeB_actionPerformed(e);
      }
    });
    financeB.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        CursorChange.setCursor(cmis.getMain(), Cursor.HAND_CURSOR);
      }

      public void mouseExited(MouseEvent e) {
        CursorChange.setCursor(cmis.getMain(), Cursor.DEFAULT_CURSOR);
      }
    });
    financeB.setFont(new java.awt.Font("Dialog", 0, 10));
    financeB.setBorder(null);
    financeB.setMargin(new Insets(0, 0, 0, 0));

    jLabel8.setText("Contact Info.");
    contactT.setMaximumSize(new Dimension(100, 19));
    contactT.setMinimumSize(new Dimension(100, 19));
    contactT.setPreferredSize(new Dimension(100, 19));
    bg2.add(directRB);
    bg2.add(indirectRB);
    scheduleB.setText("View Schedule");

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);
    repaint();
    topP.setMaximumSize(new Dimension(32767, 100));
    topP.setPreferredSize(new Dimension(10, 100));
    topP.setLayout(gridBagLayout2);
    topP.setMinimumSize(new Dimension(10, 100));
    middleP.setMaximumSize(new Dimension(32767, 100));
    middleP.setPreferredSize(new Dimension(10, 115));
    middleP.setLayout(borderLayout1);
    middleP.setMinimumSize(new Dimension(10, 100));
    ePriceL.setText("99,999 @ 99.99 / LUPP = 99,999.99");
    ePriceL.setHorizontalAlignment(SwingConstants.CENTER);
    ePriceL.setMaximumSize(new Dimension(200, 13));
    ePriceL.setPreferredSize(new Dimension(200, 13));
    ePriceL.setMinimumSize(new Dimension(200, 13));
    jPanel5.setMaximumSize(new Dimension(250, 65));
    jPanel5.setPreferredSize(new Dimension(250, 65));
    jPanel5.setMinimumSize(new Dimension(250, 65));
    dockL.setText("Dock:");
    dockL.setHorizontalAlignment(SwingConstants.RIGHT);
    dockC.setMaximumSize(new Dimension(150, 23));
    dockC.setPreferredSize(new Dimension(150, 23));
    dockC.setMinimumSize(new Dimension(150, 23));
    dockC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dockC_actionPerformed(e);
      }
    });
    deliverC.setText("Deliver to:");
    deliverC.setHorizontalAlignment(SwingConstants.RIGHT);
    delToP.setMaximumSize(new Dimension(150, 23));
    delToP.setPreferredSize(new Dimension(150, 23));
    delToP.setMinimumSize(new Dimension(150, 23));

    delLineB.setMaximumSize(new Dimension(115, 23));
    delLineB.setText("Delete Line");
    delLineB.setPreferredSize(new Dimension(115, 23));
    delLineB.setMinimumSize(new Dimension(115, 23));
    delLineB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delLineB_actionPerformed(e);
      }
    });
    jPanel5.setLayout(borderLayout2);
    jPanel2.setLayout(gridBagLayout3);
    jLabel1.setText("Facility:");
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setMaximumSize(new Dimension(42, 15));
    jLabel2.setText("End User:");
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("Department:");
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    facL.setMaximumSize(new Dimension(100, 19));
    facL.setPreferredSize(new Dimension(100, 19));
    facL.setMinimumSize(new Dimension(100, 19));
    endUserT.setPreferredSize(new Dimension(100, 19));
    endUserT.setMaximumSize(new Dimension(100, 19));
    endUserT.setMinimumSize(new Dimension(100, 19));
    deptT.setPreferredSize(new Dimension(100, 19));
    deptT.setMaximumSize(new Dimension(100, 19));
    deptT.setMinimumSize(new Dimension(100, 19));
    chargeP.setLayout(borderLayout3);
    dateT.setToolTipText("Use MM/DD/YYYY format");

    dateT.setPreferredSize(new Dimension(80, 23));
    dateT.setMaximumSize(new Dimension(80, 23));
    dateT.setMinimumSize(new Dimension(80, 23));
    delTypeC.setMaximumSize(new Dimension(150, 23));
    delTypeC.setPreferredSize(new Dimension(150, 23));
    delTypeC.setMinimumSize(new Dimension(150, 23));
    jLabel5.setText("Accounting System:");
    jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel7.setText("Extended LPP:");
    jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
    dateL.setMaximumSize(new Dimension(100, 15));
    dateL.setPreferredSize(new Dimension(100, 15));
    dateL.setMinimumSize(new Dimension(100, 15));
    priceL.setMaximumSize(new Dimension(100, 15));
    priceL.setPreferredSize(new Dimension(100, 15));
    priceL.setMinimumSize(new Dimension(100, 15));
    submitB.setText("Submit");

    //ePriceL.setFont(new Font(ePriceL.getFont().getName(),ePriceL.getFont().getStyle(),ePriceL.getFont().getSize() -2));
    submitB.setPreferredSize(new Dimension(115, 23));
    submitB.setMinimumSize(new Dimension(115, 23));
    submitB.setMaximumSize(new Dimension(115, 23));
    submitB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitB_actionPerformed(e);
      }
    });
    delMRB.setMaximumSize(new Dimension(115, 23));
    delMRB.setText("Delete MR");
    delMRB.setPreferredSize(new Dimension(115, 23));
    delMRB.setMinimumSize(new Dimension(115, 23));
    delMRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        delMRB_actionPerformed(e);
      }
    });
    saveRejB.setMaximumSize(new Dimension(115, 23));
    saveRejB.setText("Save");
    saveRejB.setPreferredSize(new Dimension(115, 23));
    saveRejB.setMinimumSize(new Dimension(115, 23));
    saveRejB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveRejB_actionPerformed(e);
      }
    });

    this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel1, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 5, 3), 0, 0));
    topP.add(jLabel2, new GridBagConstraints2(0, 0, 1, 2, 0.0, 0.0
                                              , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel3, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(facL, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 5, 3), 0, 0));
    topP.add(endUserT, new GridBagConstraints2(1, 0, 1, 2, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(deptT, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(jLabel5, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 5), 0, 0));

    topP.add(financeB, new GridBagConstraints(2, 2, 1, 2, 0.0, 0.0
                                              , GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(20, 3, 0, 3), 0, 0));
    topP.add(accSysIdL, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 3, 3, 3), 0, 0));
    topP.add(priceL, new GridBagConstraints2(3, 3, 2, 2, 0.0, 0.0
                                             , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(4, 3, 0, 3), 0, 0));
    topP.add(submitB, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(delMRB, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(saveRejB, new GridBagConstraints2(6, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    topP.add(delLineB, new GridBagConstraints2(5, 2, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    topP.add(cancelB, new GridBagConstraints2(6, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    topP.add(returnCartB, new GridBagConstraints2(4, 2, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 3, 3), 0, 0));
    topP.add(ccardB, new GridBagConstraints2(4, 3, 3, 1, 0.0, 0.0
                                             , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
    topP.add(jLabel8, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    topP.add(contactT, new GridBagConstraints2(1, 3, 1, 2, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    this.add(middleP, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
                                              , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 3, 3), 0, 0));
    middleP.add(lineJsp, BorderLayout.CENTER);

    jPanel2.setPreferredSize(new Dimension(412, 100));
    jPanel2.setMinimumSize(new Dimension(412, 100));
    this.add(jPanel2, new GridBagConstraints2(0, 2, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    //row 1
    jPanel2.add(ePriceL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 3, 3), 0, 0));
    jPanel2.add(scrapC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.2
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 3, 0), 0, 0));
    jPanel2.add(jLabel4, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.2
                                                 , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    jPanel2.add(delTypeC, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    jPanel2.add(deliveryP, new GridBagConstraints2(4, 0, 2, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 3), 0, 0));
    deliveryP.add(dateT, BorderLayout.NORTH);
    deliveryP.add(scheduleB, BorderLayout.SOUTH);

    //row 2
    jPanel2.add(directRB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.2
                                                  , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 5, 3, 3), 0, 0));
    jPanel2.add(indirectRB, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 85, 3, 3), 0, 0));
    jPanel2.add(copyB, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.2
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 160, 3, 3), 0, 0));
    jPanel2.add(undoB, new GridBagConstraints2(0, 1, 2, 1, 0.0, 0.2
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 240, 3, 3), 0, 0));
    jPanel2.add(dockL, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    jPanel2.add(dockC, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    jPanel2.add(poL, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.2
                                             , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel2.add(poC, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    jPanel2.add(poT, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));

    //row 3
    jPanel2.add(chargeP, new GridBagConstraints2(0, 2, 2, 3, 1.0, 0.6
                                                 , GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(3, 6, 10, 0), 0, 0));
    chargeP.add(chargeJsp, BorderLayout.CENTER);
    jPanel2.add(refL, new GridBagConstraints2(4, 2, 1, 1, 0.0, 0.2
                                              , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
    jPanel2.add(refT, new GridBagConstraints2(5, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));

    jPanel2.add(deliverC, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.2
                                                  , GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(3, 0, 3, 3), 0, 0));
    jPanel2.add(delToP, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.2
                                                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 3), 0, 0));
    delToP.add(delC, BorderLayout.CENTER);

    //row 4
    poQtyL.setText("PO Qty:");
    poQtyT.setPreferredSize(new Dimension(50, 19));
    poQtyT.setMaximumSize(new Dimension(50, 19));
    poQtyT.setMinimumSize(new Dimension(50, 19));
    poQtyT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        unitPriceT_actionPreformed();
      }
    });

    poUomL.setText("PO UOM:");
    poUomT.setPreferredSize(new Dimension(50, 19));
    poUomT.setMaximumSize(new Dimension(50, 19));
    poUomT.setMinimumSize(new Dimension(50, 19));

    unitPriceL.setText("PO Unit Price:");
    unitPriceT.setPreferredSize(new Dimension(50, 19));
    unitPriceT.setMaximumSize(new Dimension(50, 19));
    unitPriceT.setMinimumSize(new Dimension(50, 19));
    mrJTextFieldFocusListener = new MRJTextFieldFocusListener(this);
    unitPriceT.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        unitPriceT_actionPreformed();
      }
    });

    currencyC.setPreferredSize(new Dimension(60, 19));
    currencyC.setMaximumSize(new Dimension(60, 19));
    currencyC.setMinimumSize(new Dimension(60, 19));
    jPanel2.add(poQtyL, new GridBagConstraints2(2, 3, 4, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 10, 3, 0), 0, 0));
    jPanel2.add(poQtyT, new GridBagConstraints2(2, 3, 4, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 50, 3, 0), 0, 0));
    jPanel2.add(poUomL, new GridBagConstraints2(2, 3, 4, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 105, 3, 0), 0, 0));
    jPanel2.add(poUomT, new GridBagConstraints2(2, 3, 4, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 155, 3, 0), 0, 0));

    jPanel2.add(unitPriceL, new GridBagConstraints2(2, 3, 4, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 210, 3, 0), 0, 0));
    jPanel2.add(unitPriceT, new GridBagConstraints2(2, 3, 4, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 285, 3, 0), 0, 0));
    jPanel2.add(currencyC, new GridBagConstraints2(2, 3, 4, 1, 0.0, 0.2
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 340, 3, 0), 0, 0));

    //material detail
    jPanel2.add(jPanel5, new GridBagConstraints2(2, 4, 4, 1, 1.0, 0.4
                                                 , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 10, 5), 0, 0));
    jPanel5.add(descJsp, BorderLayout.CENTER);

    setButtonText("");
  }

  void unitPriceT_focusLost() {
    //System.out.println("------- unit price lost focus");
  } //end of method

  void unitPriceT_actionPreformed() {
    try {
      double tmpPoUnitPrice = Double.parseDouble(unitPriceT.getText().trim());
      double tmpPoQty = Double.parseDouble(poQtyT.getText().trim());
      saveCurrentDetail();
      setExtendedPrice();
      Hashtable h = getItemHash(selectedLine);
      BigDecimal quantity = new BigDecimal(h.get("QTY").toString());
      double tmp = (tmpPoQty/quantity.doubleValue())*tmpPoUnitPrice;
      setLineExtPrice(quantity, new BigDecimal(tmp), (String) currencyC.getSelectedItem(),(String)h.get("ITEM_DISPLAY_PKG_STYLE"));
    } catch (Exception e) {
      //DO NOTHING....
      //GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      //gd.setMsg("PO Unit Price must be a real number. (Example 13.75)");
      //gd.setVisible(true);
    }
  } //end of method

  public void printScreenData() {
    PrintReportLoadThread printR = new PrintReportLoadThread(this);
    printR.start();
  }

  public void printScreenDataAction() {
    //this is a kludge to stop the screen from freezing occaisionally
    try {
      Thread.sleep(200);
    } catch (Exception e) {}
    forceStopEditing();
    saveCurrentDetail();

    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialRequest", cmis);
      Hashtable query = new Hashtable();
      query.put("USER_ID", cmis.getUserId().toString());
      query.put("FUNCTION", "PRINTSCREEN");
      query.put("DETAIL_INFO", lineItems);
      headerInfo.put("END_USER", endUserT.getText());
      headerInfo.put("DEPARTMENT", deptT.getText());
      headerInfo.put("NUMBER_OF_LINES", new Integer(lineItems.size()));
      headerInfo.put("EXTENDED_LPP", priceL.getText());
      query.put("HEADER_INFO", headerInfo);
      query.put("REQ_NUM", (new Integer(rNum)).toString()); //String, Integer
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      Hashtable h = (Hashtable) result.get("RETURN_CODE");
      Boolean b = (Boolean) h.get("SUCCEEDED");
      if (b.booleanValue()) {
        try {
          // From the stand alone application
          String url = (String) result.get("URL");
          new URLCall(url, cmis);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg(h.get("MSG").toString());
        gd.setVisible(true);
      }

    } catch (Exception ee) {
      ee.printStackTrace();
    }

    this.repaint();
    this.revalidate();
  }

  public void loadIt() {
    RequestsWinLoadThread iT = new RequestsWinLoadThread(this);
    iT.start();
  }

  public void loadItAction() {
    ClientHelpObjs.setEnabledContainer(this, false);
    //this is a kludge to stop the screen from freezing occaisionally
    try {
      Thread.sleep(200);
    } catch (Exception e) {}

    loadingScreenData = true;
    try {
      Hashtable result = null;
      if (requestDataH == null) {
        TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialRequest", cmis);
        Hashtable query = new Hashtable();
        query.put("USER_ID", cmis.getUserId().toString());
        query.put("FUNCTION", "GET_SCREEN_DATA");
        query.put("REQ_NUM", (new Integer(rNum)).toString()); //String, String
        result = cgi.getResultHash(query);
      } else {
        result = requestDataH;
      }

      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }
      now = (Calendar) result.get("NOW");

      lineItems = (Vector) result.get("DETAIL_INFO");
      for (int q = 0; q < lineItems.size(); q++) {
        Hashtable rh = (Hashtable) lineItems.elementAt(q);

      }
      headerInfo = (Hashtable) result.get("HEADER_INFO");
      dpasFlag = (Boolean) headerInfo.get("DPAS_FLAG"); //11-15-01

      selectedLine = new Integer(1);
      buildHeaderSection();
      buildLineSummaryTable();

      setLabelsEnabled(true);
      setViewLevel();
      this.repaint();
      Boolean evalflag = (Boolean) headerInfo.get("EVAL");
      if (evalflag.booleanValue()) {
        returnCartB.setEnabled(false);
      }

      //1-22-03 adding order status button here
      if ("submitted".equalsIgnoreCase( (String) headerInfo.get("STATUS_DESC"))) {
        returnCartB.setText("MR Alloc Report");
        returnCartB.setEnabled(true);
      }

      loadingScreenData = false;
      this.revalidate();
      this.repaint();
    } catch (Exception e) {
      e.printStackTrace();
    }

    String viewType = headerInfo.get("VIEW_TYPE").toString();
    GenericDlg gd = new GenericDlg(cmis.getMain(), "Need Your Approval", true);
    if (viewType.equalsIgnoreCase("USE_APPROVE")) {
      gd.setMsg("This request requires your use approval.\nPlease review and approve or reject.");
      gd.setVisible(true);
    } else if (viewType.equalsIgnoreCase("APPROVE")) {
      gd.setMsg("This request requires your financial approval.\nPlease review and approve or reject.");
      gd.setVisible(true);
    } else if (viewType.equalsIgnoreCase("RELEASE")) {
      gd.setMsg("This request requires your release.\nReject or release.");
      gd.setVisible(true);
    }

    ClientHelpObjs.makeDefaultColors(this);
    ClientHelpObjs.setComboBoxUpdateUi(this);
    submitB.requestFocus();
    if (scrapC.isSelected()) {
      scrapC.setForeground(Color.red);
    } else {
      scrapC.setForeground(Color.black);
    }

    //9-22-03 go thru every line and save initial data
    if (viewType.equalsIgnoreCase("REQUEST") ||
        viewType.equalsIgnoreCase("APPROVE") ||
        viewType.equalsIgnoreCase("RELEASE") ||
        viewType.equalsIgnoreCase("USE_APPROVE")) {
      saveDefaultData();
      if (viewType.equalsIgnoreCase("APPROVE") || viewType.equalsIgnoreCase("RELEASE") ||
          viewType.equalsIgnoreCase("USE_APPROVE")) {
        origMRQuantityV.removeAllElements();
        getMRQuantity(origMRQuantityV);
        origMRQuantityV.trimToSize();
      }
    }
    //
    setRequestLineTableEditableFlag();
  } //end of method

  void saveDefaultData() {
    //first go ahead and save the first row data
    saveCurrentDetail();
    if (rliTable.getRowCount() > 1) {
      for (int i = 1; i < rliTable.getRowCount(); i++) {
        selectedLine = new Integer(i + 1);
        buildDetailSection();
        saveCurrentDetail();
      }
      selectedLine = new Integer(1);
      buildDetailSection();
    }
  } //end of method

  void getMRQuantity(Vector v) {
    for (int i = 0; i < rliTable.getRowCount(); i++) {
      v.addElement(rliTable.getModel().getValueAt(i, this.QTY_COL).toString());
    }
  } //end of method

  void setLabelsEnabled(boolean b) {
    jLabel1.setEnabled(b);
    jLabel2.setEnabled(b);
    jLabel3.setEnabled(b);
    jLabel8.setEnabled(b);
    jLabel4.setEnabled(b);
    jLabel5.setEnabled(b);
    jLabel7.setEnabled(b);
    facL.setEnabled(b);
    dateL.setEnabled(b);
    priceL.setEnabled(b);
    dockL.setEnabled(b);
    deliverC.setEnabled(b);
    refL.setEnabled(b);
    poL.setEnabled(b);
    ePriceL.setEnabled(b);
    unitPriceL.setEnabled(b);
    poQtyL.setEnabled(b);
    poUomL.setEnabled(b);
  }

  void buildHeaderSection() {

    //trong 4/8
    Hashtable myH = (Hashtable) headerInfo.get("ACC_TYPE_H");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    accSysIdL.setText(accSysId);
    accSysIdL.setEnabled(true);

    //String status = headerInfo.get("STATUS").toString();
    String status = headerInfo.get("STATUS_DESC").toString();
    String reqNum = headerInfo.get("REQ_NUM").toString();
    //reqL.setText(headerInfo.get("REQUESTOR").toString());
    topP.setBorder(ClientHelpObjs.groupBox("Checkout - Material Request No: " + reqNum + " (" + status + ")" + "   Date Requested: " + headerInfo.get("DATE").toString() + "   By: " + headerInfo.get("REQUESTOR").toString()));

    facL.setText(headerInfo.get("FACILITY").toString());
    endUserT.setText(headerInfo.get("END_USER").toString());
    deptT.setText(headerInfo.get("DEPARTMENT").toString());
    contactT.setText(headerInfo.get("CONTACT_INFO").toString());
    setExtendedPrice();
    setButtonText(headerInfo.get("VIEW_TYPE").toString());
  }

  JComboBox getDPASComboBox(String s) {
    Hashtable h = (Hashtable) headerInfo.get("DPAS_VALUES");
    JComboBox j = new JComboBox();
    j = ClientHelpObjs.loadChoiceFromVector(j, (Vector) h.get("DPAS_DESC"));
    j.setSelectedItem(getDPASDesc(s));
    j.setEditable(false);
    j.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        rliTable.repaint();
      }
    });
    j.revalidate();
    return j;
  }

  String getDPASDesc(String s) {
    Hashtable h = (Hashtable) headerInfo.get("DPAS_VALUES");
    Vector v = (Vector) h.get("DPAS_DESC");
    Vector ID = (Vector) h.get("DPAS_ID");
    int index = 0;
    for (int i = 0; i < ID.size(); i++) {
      String tmp = (String) ID.elementAt(i);
      if (tmp.equalsIgnoreCase(s)) {
        index = i;
        break;
      }
    }
    return (v.elementAt(index).toString());
  }

  String getDPASID(int row) {
    Hashtable h = (Hashtable) headerInfo.get("DPAS_VALUES");
    Vector v = (Vector) h.get("DPAS_ID");
    JComboBox dpasC = ( (JComboBox) rliTable.getValueAt(row, DPAS_COL));

    return (v.elementAt(dpasC.getSelectedIndex()).toString());
  }

  void buildLineSummaryTable() {
    loadingSummary = true;
    middleP.setBorder(ClientHelpObjs.groupBox("Request Line Items"));

    Hashtable tableStyle = (Hashtable) headerInfo.get("TABLE_STYLE");
    Hashtable keyCol = (Hashtable) tableStyle.get("KEY_COLUMNS");
    PART_COL = ( (Integer) keyCol.get("Part #")).intValue();
    QTY_COL = ( (Integer) keyCol.get("Qty")).intValue();
    ITEM_COL = ( (Integer) keyCol.get("Item #")).intValue();
    NOTE_COL = ( (Integer) keyCol.get("Notes")).intValue();
    DESC_COL = ( (Integer) keyCol.get("Description")).intValue();
    WORK_AREA_COL = ( (Integer) keyCol.get("Work Area")).intValue();
    CRITICAL_COL = ( (Integer) keyCol.get("Crit.")).intValue();
    CANCEL_COL = ( (Integer) keyCol.get("Canceled")).intValue();
    TYPE_COL = ( (Integer) keyCol.get("Type")).intValue();
    REQ_CANCEL_COL = ( (Integer) keyCol.get("Cancel Requested")).intValue();
    USE_APPROVAL_STATUS_COL = ( (Integer) keyCol.get("Status")).intValue();
    DPAS_COL = ( (Integer) keyCol.get("DPAS")).intValue();
    LINE_COL = ( (Integer) keyCol.get("Line")).intValue();
    ORDER_QUANTITY_RULE_COL = ( (Integer) keyCol.get("Order Quantity Rule")).intValue();
    CUSTOMER_REQUISITION_COL = ( (Integer) keyCol.get("Cust. Req.")).intValue();
    LIST_APPROVAL_STATUS_COL = ( (Integer) keyCol.get("List Status")).intValue();

    String[] tableCols = (String[]) tableStyle.get("TABLE_COLUMNS");
    int[] tableColWidths = (int[]) tableStyle.get("TABLE_COL_WIDTHS");
    String tableColTypes = (String) tableStyle.get("TABLE_COL_TYPES");

    Object[][] oa = new Object[lineItems.size()][tableCols.length];
    //keep track of which line item has 'Fixed' qty
    Vector orderQuantityFixedKey = new Vector(lineItems.size());
    for (int i = 0; i < lineItems.size(); i++) {
      Hashtable h = (Hashtable) lineItems.elementAt(i);
      oa[i][0] = new Integer(h.get("LINE_NUM").toString());

      if (dpasFlag.booleanValue()) {
        oa[i][DPAS_COL] = getDPASComboBox(h.get("DPAS").toString());
      } else {
        oa[i][DPAS_COL] = "";
      }

      oa[i][ITEM_COL] = h.get("ITEM_NUM").toString();
      oa[i][PART_COL] = h.get("PART_NUM").toString();
      oa[i][QTY_COL] = h.get("QTY").toString();
      oa[i][CRITICAL_COL] = (Boolean) h.get("CRIT_FLAG");
      oa[i][NOTE_COL] = BothHelpObjs.isBlankString( (String) h.get("NOTES")) ? "" : "+";
      oa[i][TYPE_COL] = h.get("ITEM_TYPE").toString();
      // Work area
      String wa = h.get("WORK_AREA").toString();
      if (!BothHelpObjs.isBlankString( (String) h.get("WORK_AREA_DESC"))) {
        wa = wa + " - " + h.get("WORK_AREA_DESC").toString();
      }
      oa[i][WORK_AREA_COL] = wa;
      oa[i][DESC_COL] = h.get("ITEM_DESC").toString();
      oa[i][CANCEL_COL] = new Boolean( ( (String) h.get("CANCEL_STATUS")).equalsIgnoreCase("Canceled"));
      oa[i][REQ_CANCEL_COL] = new Boolean( ( (String) h.get("CANCEL_STATUS")).equalsIgnoreCase("Cancel Req."));
      oa[i][USE_APPROVAL_STATUS_COL] = (String) h.get("USE_APPROVAL_STATUS");
      oa[i][CUSTOMER_REQUISITION_COL] = (String) h.get("CUSTOMER_REQUISITION_NUMBER");
      String orderQuantityRule = (String) h.get("ORDER_QUANTITY_RULE");
      oa[i][ORDER_QUANTITY_RULE_COL] = orderQuantityRule;
      if ("Fixed".equalsIgnoreCase(orderQuantityRule)) {
        String key = (new Integer(i)).toString() + (new Integer(this.QTY_COL)).toString();
        orderQuantityFixedKey.addElement(key);
      }
      oa[i][LIST_APPROVAL_STATUS_COL] = (String) h.get("LIST_APPROVAL_STATUS");
    }
    headerInfo.put("ORDER_QUANTITY_FIXED_KEY",orderQuantityFixedKey);

    if (lineItems.size() < 1) {
      rliCtm = new CmisTableModel(tableCols);
    } else {
      rliCtm = new CmisTableModel(tableCols, oa);
    }

    middleP.remove(lineJsp);
    TableSorter sorter = new TableSorter(rliCtm);
    sorter.setColTypeFlag(tableColTypes);
    rliTable = new ReqTable(sorter);
    rliTable.setParent(cmis.getMain());
    rliTable.setReqWin(this);
    //
    rliTable.getTableHeader().setResizingAllowed(true);
    rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    rliTable.setCellSelectionEnabled(false);
    rliTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    rliTable.getTableHeader().setReorderingAllowed(false);

    rliTable.setDefaultRenderer(String.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Integer.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Double.class, colorTableRenderer);
    rliTable.setDefaultRenderer(Boolean.class, checkTableRenderer);

    rliTable.setDefaultEditor(JComboBox.class, new RequestWin_BoxCellEditor(new JComboBox()));
    rliTable.setDefaultRenderer(JComboBox.class, new ComboBoxCellRenderer());

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Color color = (Color) tableStyle.get("COLOR");
    Integer t = (Integer) tableStyle.get("INSET_TOP");
    Integer l = (Integer) tableStyle.get("INSET_LEFT");
    Integer b = (Integer) tableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) tableStyle.get("INSET_RIGHT");
    Integer a = (Integer) tableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
    //font and foreground and background color for columns heading
    String fontName = (String) tableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer) tableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer) tableStyle.get("FONT_SIZE");
    rliTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer( (Color) tableStyle.get("FOREGROUND"), (Color) tableStyle.get("BACKGROUND"), renderers[0].getBorder());
    Enumeration enum1 = rliTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    JCheckBox check = new JCheckBox();
    check.setHorizontalAlignment(JLabel.CENTER);
    rliTable.setDefaultEditor(Boolean.class, new CheckBoxCellEditor(check));
    rliTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        listSelectionChanged();
      }
    });

    rliTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();
        int colSel = rliTable.columnAtPoint(p);
        if (colSel == DPAS_COL) {
          CursorChange.setCursor(cmis.getMain(), Cursor.HAND_CURSOR);
        } else {
          CursorChange.setCursor(cmis.getMain(), Cursor.DEFAULT_CURSOR);
        }
      }
    });
    rliTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(MouseEvent e) {
        Point p = e.getPoint();
        int colSel = rliTable.columnAtPoint(p);
        if (colSel == DPAS_COL) {
          CursorChange.setCursor(cmis.getMain(), Cursor.HAND_CURSOR);
        }
      }

      public void mouseExited(MouseEvent e) {
        Point p = e.getPoint();
        int colSel = rliTable.columnAtPoint(p);
        if (colSel == DPAS_COL) {
          CursorChange.setCursor(cmis.getMain(), Cursor.DEFAULT_CURSOR);
        }
      }

      public void mouseClicked(MouseEvent e) {
        tableClicked(e);
      }
    });
    lineJsp = new JScrollPane(rliTable);
    for (int i = 0; i < rliTable.getColumnCount(); i++) {
      if (tableColWidths[i] == 0) {
        rliTable.getColumn(rliTable.getColumnName(i)).setWidth(tableColWidths[i]);
        rliTable.getColumn(rliTable.getColumnName(i)).setMaxWidth(tableColWidths[i]);
        rliTable.getColumn(rliTable.getColumnName(i)).setMinWidth(tableColWidths[i]);
      }
      rliTable.getColumn(rliTable.getColumnName(i)).setPreferredWidth(tableColWidths[i]);
    }
    rliTable.validate();
    rliTable.revalidate();
    rliTable.repaint();
    lineJsp.validate();
    middleP.add(lineJsp);
    middleP.validate();
    loadingSummary = false;
    if (loadingScreenData) {
      setSelectedRow(selectedLine.intValue() - 1);
    }
    buildDetailSection();
  }

  boolean isCustomerRequisitionEditable() {
    boolean result = false;
    String type = "d";
    if (directRB.isVisible()) {
      type = directRB.isSelected() ? "d" : "i";
    } else {
      type = "d";
    }
    Hashtable myh = (Hashtable) headerInfo.get("ACC_TYPE_H");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    if (packH.containsKey(key1)) {
      Hashtable accSysTypeH = (Hashtable) packH.get(key1);
      if (accSysTypeH.containsKey("CUSTOMER_REQUISITION")) {
        String customerRequisition = (String) accSysTypeH.get("CUSTOMER_REQUISITION");
        if ("Required".equalsIgnoreCase(customerRequisition)) {
          result = true;
        }
      }
    }
    return result;
  } //end of method

  void setRequestLineTableEditableFlag() {
    //first try to see if customer requisition is editable or not
    boolean customerRequisitionEditable = isCustomerRequisitionEditable();

    String viewType = headerInfo.get("VIEW_TYPE").toString();
    Vector orderQuantityFixedKey = (Vector) headerInfo.get("ORDER_QUANTITY_FIXED_KEY");

    if (viewType.equalsIgnoreCase("REQUEST")) {
      Boolean myEvalflag = (Boolean) headerInfo.get("EVAL");
      if (myEvalflag.booleanValue()) {
        rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
        delC.setEnabled(false); //trong 4/3
      } else {
        if (dpasFlag.booleanValue() || customerRequisitionEditable) {
          if (dpasFlag.booleanValue() && customerRequisitionEditable) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, DPAS_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }else if (dpasFlag.booleanValue()) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, DPAS_COL));
          }else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }
        } else {
          rliCtm.setEditableFlag(BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CRITICAL_COL));
        }
        //don't allow users to change quantity if it is defined as 'Fixed'
        for (int j = 0; j < orderQuantityFixedKey.size(); j++) {
          rliCtm.addCellNotEditable( (String) orderQuantityFixedKey.elementAt(j));
        }
      }
    } else if (viewType.equalsIgnoreCase("APPROVE")) {
      //since I am allowing finance approver(s) to edit MR
      //well finance approver(s) in EditMR user group
      if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() || ((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue()) {
        if (customerRequisitionEditable) {
          if (((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue() ) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }
        }else {
          if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue()) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL));
          } else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
          }
        }
        //don't allow users to change quantity if it is defined as 'Fixed'
        for (int j = 0; j < orderQuantityFixedKey.size(); j++) {
          rliCtm.addCellNotEditable( (String) orderQuantityFixedKey.elementAt(j));
        }
      } else {
        rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
      }
    } else if (viewType.equalsIgnoreCase("RELEASE")) {
      //since I am allowing releaser(s) to edit MR
      //well releaser(s) in EditMR user group
      if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() || ((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue()) {
        if (customerRequisitionEditable) {
          if (((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue() ) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }
        }else {
          if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue()) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL));
          } else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
          }
        }
        //don't allow users to change quantity if it is defined as 'Fixed'
        for (int j = 0; j < orderQuantityFixedKey.size(); j++) {
          rliCtm.addCellNotEditable( (String) orderQuantityFixedKey.elementAt(j));
        }
      } else {
        if (customerRequisitionEditable) {
          rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
        }else {
          rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
        }
      }
    } else if (viewType.equalsIgnoreCase("USE_APPROVE")) {
      //since I am allowing use approver(s) to edit MR
      //well use approver(s) in EditMR user group
      if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() || ((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue()) {
        if (customerRequisitionEditable) {
          if (((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue() ) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }
        }else {
          if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue()) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL));
          } else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
          }
        }
        //don't allow users to change quantity if it is defined as 'Fixed'
        for (int j = 0; j < orderQuantityFixedKey.size(); j++) {
          rliCtm.addCellNotEditable( (String) orderQuantityFixedKey.elementAt(j));
        }
      } else {
        rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
      }
    } else {
      if ( ((Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() || ((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue() ) {
        if (customerRequisitionEditable) {
          if (((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue() ) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, CUSTOMER_REQUISITION_COL));
          }
        }else {
          if (((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue() ) {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL) + BothHelpObjs.mypow(2, QTY_COL));
          }else {
            rliCtm.setEditableFlag(BothHelpObjs.mypow(2, CRITICAL_COL));
          }
        }
        //don't allow users to change quantity if it is defined as 'Fixed'
        for (int j = 0; j < orderQuantityFixedKey.size(); j++) {
          rliCtm.addCellNotEditable( (String) orderQuantityFixedKey.elementAt(j));
        }
      } else {
        rliCtm.setEditableFlag(0);
      }
    }
  } //end of method

  void setSelectedRow(int x) {
    ListSelectionModel lsm = rliTable.getSelectionModel();
    lsm.setSelectionInterval(x, x);
    rliTable.scrollRectToVisible(rliTable.getCellRect(x, 0, false));
  }

  /*
     The extended price is equal to the sum of unit_price of non-po line items and
     the sum of po_unit_price of po line items.  If non-po currency (price_group currency)
     is different than po currency then display "----".  If po line items contain different
     currency then display "----"
   */
  void setExtendedPrice() {
    Boolean containsFillUpBulk = (Boolean) headerInfo.get("CONTAINS_FILL_UP_BULK");
    if (containsFillUpBulk.booleanValue()) {
      priceL.setText(" tbd ");
    } else {
      double nonPoExtendedPrice = 0.00;
      double poExtendedPrice = 0.00;
      boolean hasPrice = false;
      Vector poCurrency = new Vector(11);
      for (int i = 0; i < lineItems.size(); i++) {
        Hashtable h = (Hashtable) lineItems.elementAt(i);
        //do not add the price to extended price when line item has been rejected
        String stat = (String) h.get("USE_APPROVAL_STATUS");
        if ("rejected".equalsIgnoreCase(stat)) {
          continue;
        }

        //po_required
        String type = (String) h.get("CHARGE_TYPE");
        if (BothHelpObjs.isBlankString(type)) {
          type = "d";
        }
        Hashtable myh = (Hashtable) headerInfo.get("ACC_TYPE_H");
        String accSysId = (String) headerInfo.get("ACC_SYS_ID");
        String key1 = accSysId + type;
        Hashtable packH = (Hashtable) headerInfo.get("PACK");
        Hashtable accSysTypeH = (Hashtable) packH.get(key1);
        String needPo = (String) accSysTypeH.get("PO_REQUIRED");
        String unitPriceRequired = (String) accSysTypeH.get("UNIT_PRICE_REQUIRED");
        String releaserRequired = (String) accSysTypeH.get("RELEASER_REQUIRED");
        String poSeqRequired = (String) accSysTypeH.get("PO_SEQ_REQUIRED");
        if ("p".equalsIgnoreCase(needPo)) {
          if ("Required".equalsIgnoreCase(unitPriceRequired) || "Display".equalsIgnoreCase(unitPriceRequired)) {
            try {
              if (h.containsKey("PO_UNIT_PRICE"+type)) {
                if (!BothHelpObjs.isBlankString(h.get("PO_UNIT_PRICE"+type).toString())) {
                  double tmpPrice = getPoExtendedPrice(h, type);
                  if (tmpPrice != -12345.6) {
                    poExtendedPrice += tmpPrice;
                    hasPrice = true;
                  }
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }else {
            double tmpPrice = getNonPoExtendedPrice(h);
            if (tmpPrice != -12345.6) {
              nonPoExtendedPrice += tmpPrice;
              hasPrice = true;
            }
          }
        } else {
          double tmpPrice = getNonPoExtendedPrice(h);
          if (tmpPrice != -12345.6) {
            nonPoExtendedPrice += tmpPrice;
            hasPrice = true;
          }
        }
      }
      // setting extended price
      String currencyID = (String) headerInfo.get("CURRENCY_ID");
      if (currencyID.length() > 0) {
        if (hasPrice) {
          priceL.setText(formatPrice(poExtendedPrice + nonPoExtendedPrice) + " " + currencyID);
        }else {
          priceL.setText("");
        }
      } else {
        if (hasPrice) {
          priceL.setText(formatPrice(nonPoExtendedPrice));
        }else {
          priceL.setText("");
        }
      }

      String finStat = (String) headerInfo.get("PENDING_FINANCE");
      if (finStat.equalsIgnoreCase("pending")) {
        financeB.setText("<html><font face = \"Dialog\" size = \"1\">Ext. Unit Price<p><font color = \"#0000aa\">&nbsp;&nbsp;&nbsp;(<u>pending</u>)   :</font></font></html>");
        financeB.setEnabled(true);
      } else if (finStat.equalsIgnoreCase("approved")) {
        financeB.setText("<html><font face = \"Dialog\" size = \"1\">Ext. Unit Price<p><font color = \"#0000aa\">&nbsp;&nbsp;&nbsp;(<u>approved</u>)  :</font></font></html>");
        financeB.setEnabled(true);
      } else if (finStat.equalsIgnoreCase("rejected")) {
        financeB.setText("<html><font face = \"Dialog\" size = \"1\">Ext. Unit Price<p><font color = \"#0000aa\">&nbsp;&nbsp;&nbsp;(<u>rejected</u>)  :</font></font></html>");
        financeB.setEnabled(true);
      } else {
        financeB.setText("<html><font face = \"Dialog\" size = \"1\">Ext. Unit Price :</font></html>");
      }
    }
  }

  //return -12345.6 as a flag to whether there is an error in the data or price is empty
  double getPoExtendedPrice(Hashtable h, String type) {
    double result = -12345.6;
    if (!BothHelpObjs.isBlankString(h.get("PO_UNIT_PRICE"+type).toString()) &&
        !"Select One".equalsIgnoreCase((String) h.get("PO_UNIT_PRICE_CURRENCY_ID"+type)) &&
        !BothHelpObjs.isBlankString(h.get("PO_UNIT_PRICE_CURRENCY_ID"+type).toString()) &&
        !BothHelpObjs.isBlankString(h.get("PO_QTY"+type).toString())) {
      try {
        Hashtable tmpCurrencyList = (Hashtable) headerInfo.get("CURRENCY_LIST");
        Vector currencyIDV = (Vector) tmpCurrencyList.get("CURRENCY_ID");
        Vector conversionFactorV = (Vector) tmpCurrencyList.get("CONVERSION_FACTOR");
        BigDecimal qty = new BigDecimal(h.get("QTY").toString());
        BigDecimal price = new BigDecimal(h.get("PO_UNIT_PRICE" + type).toString());
        BigDecimal poQty = new BigDecimal(h.get("PO_QTY" + type).toString());
        BigDecimal conversionFactor = new BigDecimal( (String) conversionFactorV.elementAt(currencyIDV.indexOf( (String) h.get("PO_UNIT_PRICE_CURRENCY_ID" + type))));
        float t = poQty.floatValue()*(price.floatValue()*conversionFactor.floatValue());
        BigDecimal product = new BigDecimal(t);
        //BigDecimal product = qty.multiply(((poQty.divide(qty,BigDecimal.ROUND_HALF_EVEN)).multiply(price.multiply(conversionFactor))));
        result = product.floatValue();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  } //end of method

  //return -12345.6 as a flag to whether there is an error in the data or price is empty
  double getNonPoExtendedPrice(Hashtable h) {
    double result = -12345.6;
    if (!BothHelpObjs.isBlankString(h.get("UNIT_PRICE").toString())) {
      try {
        BigDecimal qty = new BigDecimal(h.get("QTY").toString());
        BigDecimal price = new BigDecimal(h.get("UNIT_PRICE").toString());
        BigDecimal conversionFactor = (BigDecimal) h.get("CONVERSION_FACTOR");
        BigDecimal product = qty.multiply(price.multiply(conversionFactor));
        result = product.floatValue();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return result;
  } //end of method

  String formatPrice(double d) {
    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(2);
    nf.setMinimumFractionDigits(2);
    nf.setMinimumIntegerDigits(1);
    return nf.format(d).toString();
  }

  void saveCurrentDetail() {
    if (loadingScreenData) {
      return;
    }
    Hashtable h = getItemHash(selectedLine);
    int row = 0;
    for (int i = 0; i < rliTable.getRowCount(); i++) {
      String s = rliTable.getModel().getValueAt(i, 0).toString();
      Integer I = new Integer(s);
      if (I.intValue() == selectedLine.intValue()) {
        row = i;
      }
    }
    //qty
    h.put("QTY", rliTable.getModel().getValueAt(row, QTY_COL).toString().trim());
    //crit
    h.put("CRIT_FLAG", (Boolean) rliTable.getModel().getValueAt(row, CRITICAL_COL));
    //part_no
    h.put("PART_NUM", (String) rliTable.getModel().getValueAt(row, PART_COL));
    //DPAS
    if (dpasFlag.booleanValue()) {
      h.put("DPAS", getDPASID(row));
    } else {
      h.put("DPAS", "None");
    }
    //charge type
    if (directRB.isVisible()) {
      h.put("CHARGE_TYPE", directRB.isSelected() ? "d" : "i");
    } else {
      h.put("CHARGE_TYPE", "d");
    }
    //order quantity rule
    h.put("ORDER_QUANTITY_RULE", (String) rliTable.getModel().getValueAt(row, ORDER_QUANTITY_RULE_COL));
    //customer requisition number
    h.put("CUSTOMER_REQUISITION_NUMBER", ((String) rliTable.getModel().getValueAt(row, CUSTOMER_REQUISITION_COL)).trim());

    //po_required
    String type = (String) h.get("CHARGE_TYPE");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable) packH.get(key1);
    String s = (String) accSysTypeH.get("PO_REQUIRED");
    String unitPriceRequired = (String) accSysTypeH.get("UNIT_PRICE_REQUIRED");
    String releaserRequired = (String) accSysTypeH.get("RELEASER_REQUIRED");
    String poSeqRequired = (String) accSysTypeH.get("PO_SEQ_REQUIRED");
    h.put("UNIT_PRICE_STATUS"+type,unitPriceRequired);
    if (s.equalsIgnoreCase("p")) {
      if (poC.isVisible()) {
        if (poC.getItemCount() > 0) {
          h.put("PO"+type, poC.getSelectedItem().toString());
        } else {
          h.put("PO"+type, "");
        }
      } else {
        String tmpPO = poT.getText().trim();
        if (tmpPO.length() > 0) {
          h.put("PO"+type, tmpPO);
        } else {
          h.put("PO"+type, "");
        }
      }
      if ("U".equalsIgnoreCase(poSeqRequired)) {
        h.put("RELEASE_NUM"+type, refT.getText().trim());
      } else {
        h.put("RELEASE_NUM"+type, "");
      }
      if ("Required".equalsIgnoreCase(unitPriceRequired) || "Display".equalsIgnoreCase(unitPriceRequired)) {
        //po unit price
        String tmpUnitPrice = unitPriceT.getText().trim();
        if (!BothHelpObjs.isBlankString(tmpUnitPrice)) {
          try {
            BigDecimal bd = new BigDecimal(tmpUnitPrice);
            h.put("PO_UNIT_PRICE"+type, tmpUnitPrice);
            h.put("PO_UNIT_PRICE_CURRENCY_ID"+type, currencyC.getSelectedItem().toString());
            h.put("UNIT_PRICE", tmpUnitPrice);
            h.put("CURRENCY_ID", currencyC.getSelectedItem().toString());
            h.put("UNIT_PRICE_UPDATE_FROM_PO_UNIT_PRICE", new Boolean(true));
          } catch (Exception e) {
            h.put("PO_UNIT_PRICE"+type, "");
            h.put("PO_UNIT_PRICE_CURRENCY_ID"+type, currencyC.getSelectedItem().toString());
            //reset unit_price to 0 if unit_price was updated by po_unit_price
            if (h.containsKey("UNIT_PRICE_UPDATE_FROM_PO_UNIT_PRICE")) {
              if ( ( (Boolean) h.get("UNIT_PRICE_UPDATE_FROM_PO_UNIT_PRICE")).booleanValue()) {
                h.put("UNIT_PRICE", h.get("CURRENT_CATALOG_PRICE").toString());
                h.put("CURRENCY_ID", h.get("CURRENT_CURRENCY_ID").toString());
                h.put("UNIT_PRICE_UPDATE_FROM_PO_UNIT_PRICE", new Boolean(false));
              }
            }
          }
        } else {
          h.put("PO_UNIT_PRICE"+type, "");
          h.put("PO_UNIT_PRICE_CURRENCY_ID"+type, currencyC.getSelectedItem().toString());
          //reset unit_price to 0 if unit_price was updated by po_unit_price
          if (h.containsKey("UNIT_PRICE_UPDATE_FROM_PO_UNIT_PRICE")) {
            if ( ( (Boolean) h.get("UNIT_PRICE_UPDATE_FROM_PO_UNIT_PRICE")).booleanValue()) {
              h.put("UNIT_PRICE", h.get("CURRENT_CATALOG_PRICE").toString());
              h.put("CURRENCY_ID", h.get("CURRENT_CURRENCY_ID").toString());
              h.put("UNIT_PRICE_UPDATE_FROM_PO_UNIT_PRICE", new Boolean(false));
            }
          }
        }
        //po qty
        if ("Display".equalsIgnoreCase(unitPriceRequired)) {
          BigDecimal poQty;
          BigDecimal quantity = new BigDecimal(h.get("QTY").toString());
          String poUosqpe = "1";
          if (h.containsKey("PO_UOSQPE" + type)) {
            poUosqpe = h.get("PO_UOSQPE" + type).toString();
          }
          if ("1".equalsIgnoreCase(poUosqpe)) {
            poQty = quantity;
          } else {
            poQty = quantity.multiply(new BigDecimal(poUosqpe));
          }
          h.put("PO_QTY"+type,poQty.toString());
        }else {
          String tmpPoQty = poQtyT.getText().trim();
          if (!BothHelpObjs.isBlankString(tmpPoQty)) {
            try {
              BigDecimal bd = new BigDecimal(tmpPoQty);
              h.put("PO_QTY" + type, tmpPoQty);
            } catch (Exception ee) {
              h.put("PO_QTY" + type, "");
            }
          } else {
            h.put("PO_QTY" + type, "");
          }
        }
        //po uom
        String tmpPoUom = poUomT.getText().trim();
        if (!BothHelpObjs.isBlankString(tmpPoUom)) {
          h.put("PO_UOM"+type,tmpPoUom.toUpperCase());
        }else {
           h.put("PO_UOM"+type,"");
        }
      } else {      //po unit price is not required
        h.put("PO_UNIT_PRICE"+type, "");
        h.put("PO_UNIT_PRICE_CURRENCY_ID"+type, "");
        h.put("PO_QTY"+type,"");
        h.put("PO_UOM"+type,"");
      }
    } else {       //po is not required
      h.put("PO"+type, "");
      h.put("RELEASE_NUM"+type, "");
      h.put("PO_UNIT_PRICE"+type, "");
      h.put("PO_UNIT_PRICE_CURRENCY_ID"+type, "");
      h.put("PO_QTY"+type,"");
      h.put("PO_UOM"+type,"");
    }

    //delivery info
    h.put("DELIVERY_TYPE", delTypeC.getSelectedItem().toString());
    h.put("DELIVERY_SCHEDULE", delInfo);
    //date formatting
    h.put("DEL_DATE", dateT.getText());
    //dock
    h.put("DOCK", dockC.getSelectedItem().toString());
    //deliverTo
    String dTo = delC.getSelectedItem().toString();
    if (dTo.equalsIgnoreCase(NEED_DEL_TO)) {
      h.put("DELIVER_TO", "");
      h.put("DELIVER_TO_NAME", "");
    } else {
      h.put("DELIVER_TO", getDelPointFromText(dockC.getSelectedItem().toString(), delC.getSelectedItem().toString(), h));
      h.put("DELIVER_TO_NAME", delC.getSelectedItem().toString());
    }
    //scrap flag
    h.put("SCRAP_FLAG", new Boolean(scrapC.isSelected()));
    //trong 7/19
    String prAccR = (String) accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    //chargenum
    if (prAccR.equalsIgnoreCase("y")) { //trong 7-19
      Vector v = new Vector();
      h.put("CHARGE_NUM", v);
      v = new Vector();
      int numCols = chargeNumTable.getColumnCount();
      for (int i = 0; i < chargeNumTable.getRowCount(); i++) {
        Hashtable f = new Hashtable();
        int mc = 0;
        f.put("ACCT_NUM_1", chargeNumTable.getModel().getValueAt(i, mc++).toString().trim());
        if (numCols == 3) {
          f.put("ACCT_NUM_2", chargeNumTable.getModel().getValueAt(i, mc++).toString().trim());
        }
        f.put("PERCENTAGE", chargeNumTable.getModel().getValueAt(i, mc++).toString().trim());
        v.addElement(f);
      }
      if (h.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")) {
        h.put("CHARGE_NUM_INDIRECT", v);
      } else if (h.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")) {
        h.put("CHARGE_NUM_DIRECT", v);
      } else {
        System.out.println("SHOULDN'T BE HERE");
      }
    } //end trong 7-19
  } //end of method

  //2-8-01
  void setSelectedRowForChargeTable(int row, int col) {
    chargeNumTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    chargeNumTable.setCellSelectionEnabled(false);
    /*chargeNumTable.setColumnSelectionAllowed(true);  */
    ListSelectionModel clsm = chargeNumTable.getSelectionModel();
    clsm.setSelectionInterval(row, row);
    chargeNumTable.scrollRectToVisible(chargeNumTable.getCellRect(row, 0, false));
    chargeNumTable.requestFocus();
    chargeNumTable.editCellAt(row, 0);
  }

  //2-8-01  saving the charge number that the user entered NOT using it right now maybe later.
  //        p.s. when use need to think thru the theory again
  void saveCurrentChargeNumbers(String type) {
    Hashtable h = getItemHash(selectedLine);
    if (chargeNumTable.isEditing()) {
      chargeNumTable.getCellEditor().stopCellEditing();
    }
    //charge type
    //Hashtable myh = (Hashtable)headerInfo.get("ACC_TYPE_H");
    //String numChargeTypes = (String)myh.get("NUM_CHARGE_TYPES");
    //Integer number = new Integer(numChargeTypes);

    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable) packH.get(key1);

    String prAccR = (String) accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prAccR.equalsIgnoreCase("y")) {
      Vector v = new Vector();
      int numCols = chargeNumTable.getColumnCount();
      for (int i = 0; i < chargeNumTable.getRowCount(); i++) {
        Hashtable f = new Hashtable();
        int mc = 0;
        f.put("ACCT_NUM_1", chargeNumTable.getModel().getValueAt(i, mc++).toString());
        if (numCols == 3) {
          f.put("ACCT_NUM_2", chargeNumTable.getModel().getValueAt(i, mc++).toString());
        }
        f.put("PERCENTAGE", chargeNumTable.getModel().getValueAt(i, mc++).toString());
        v.addElement(f);
      }
      if (type.equalsIgnoreCase("i")) {
        h.put("CHARGE_NUM_INDIRECT", v);
      } else if (type.equalsIgnoreCase("d")) {
        h.put("CHARGE_NUM_DIRECT", v);
      } else {
        System.out.println("SHOULDN'T BE HERE");
        //h.put("CHARGE_NUM",v);
      }
    }
  }

  void buildDetailSection() {
    loadingDetail = true;
    jPanel2.setBorder(ClientHelpObjs.groupBox("Line Item Detail - Line " + selectedLine.toString()));

    // reset componentes
    if (delC.getItemCount() > 0) {
      delC.removeAllItems();
    }
    if (dockC.getItemCount() > 0) {
      dockC.removeAllItems();
    }
    if (delTypeC.getItemCount() > 0) {
      updatingDelTypeC = true;
      delTypeC.removeAllItems();
      updatingDelTypeC = false;
    }
    if (poC.getItemCount() > 0) {
      poC.removeAllItems();
    }
    refT.setText("");
    dateT.setText("");

    String temp = "";
    Hashtable h = getItemHash(selectedLine);

    if (h.containsKey("DELIVERY_SCHEDULE")) {
      delInfo = (Vector) h.get("DELIVERY_SCHEDULE");
    } else {
      delInfo = new Vector();
    }
    updatingDelTypeC = true;

    Vector deliveryTypes = (Vector) headerInfo.get("DEL_TYPES");
    Vector tempDeliveryTypes = new Vector(deliveryTypes.size());
    for (int j = 0; j < deliveryTypes.size(); j++) {
      String tmpDelType = (String) deliveryTypes.elementAt(j);
      if ("Fixed".equalsIgnoreCase( (String) h.get("ORDER_QUANTITY_RULE"))) {
        if (!"Schedule".equalsIgnoreCase(tmpDelType)) {
          tempDeliveryTypes.addElement(tmpDelType);
        }
      } else {
        tempDeliveryTypes.addElement(tmpDelType);
      }
    }
    delTypeC = ClientHelpObjs.loadChoiceFromVector(delTypeC, tempDeliveryTypes);

    temp = h.get("DELIVERY_TYPE").toString();
    updatingDelTypeC = false;
    if (BothHelpObjs.isBlankString(temp)) {
      delTypeC.setSelectedIndex(0);
    } else {
      delTypeC.setSelectedItem(temp);
    }

    temp = h.get("DEL_DATE").toString();
    dateT.setText(BothHelpObjs.isBlankString(temp) ? "" : temp);
    setDeliveryPanel();

    Boolean scrap = (Boolean) h.get("SCRAP_FLAG");
    scrapC.setSelected(scrap.booleanValue());

    //10-03-02  dock
    setDockCombo();
    if (dockC.getItemCount() > 0) {
      temp = h.get("DOCK").toString();
      if (BothHelpObjs.isBlankString(temp)) {
        dockC.setSelectedIndex(0);
      } else {
        dockC.setSelectedItem(temp);
      }
    }
    //delivery to
    setDeliverToCombo();

    boolean holding = delTypeC.getSelectedItem().toString().equalsIgnoreCase("hold");
    delTypeC.validate();
    dockC.setVisible(!holding);
    dockC.validate();
    delC.setVisible(!holding);
    delC.validate();
    dateT.setEnabled(!holding);
    // end delivery info

    //trong 4/7
    Hashtable myH = (Hashtable) headerInfo.get("ACC_TYPE_H");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable) myH.get(accSysId);
    accSysIdL.setText(accSysId);

    String numChargeTypes = (String) innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);

    //trong 2-1-01
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    if (viewType.equalsIgnoreCase("request")) {
      Vector chargeTypesV = (Vector) innerH.get("CHARGE_TYPE");
      String myChargeType1 = "";
      String myChargeType2 = "";
      String myDirectChargeType = "";
      String myIndirectChargeType = "";
      if (chargeTypesV.size() == 1) { //only have direct charge type
        myChargeType1 = (String) chargeTypesV.firstElement();
      } else if (chargeTypesV.size() == 2) { //container both direct and indirect charge type
        myChargeType1 = (String) chargeTypesV.firstElement();
        myChargeType2 = (String) chargeTypesV.lastElement();
      }
      //making sure that I assign the right charge type
      if (!BothHelpObjs.isBlankString(myChargeType1)) {
        if (myChargeType1.equalsIgnoreCase("d")) {
          myDirectChargeType = myChargeType1;
        } else if (myChargeType1.equalsIgnoreCase("i")) {
          myIndirectChargeType = myChargeType1;
        }
      }
      if (!BothHelpObjs.isBlankString(myChargeType2)) {
        if (myChargeType2.equalsIgnoreCase("d")) {
          myDirectChargeType = myChargeType2;
        } else if (myChargeType2.equalsIgnoreCase("i")) {
          myIndirectChargeType = myChargeType2;
        }
      }
      String directedChargeI = "";
      String directedChargeD = "";
      if (!BothHelpObjs.isBlankString(myDirectChargeType)) {
        directedChargeD = (String) h.get("DIRECTED_TYPE" + myDirectChargeType);
      }
      if (!BothHelpObjs.isBlankString(myIndirectChargeType)) {
        directedChargeI = (String) h.get("DIRECTED_TYPE" + myIndirectChargeType);
      }
      //System.out.println("======== chargeType direct:-"+directedChargeD+"-indirect:-"+directedChargeI+"-");

      if (number.intValue() == 2) {
        directRB.setVisible(true);
        indirectRB.setVisible(true);
        String di = h.get("CHARGE_TYPE").toString();
        //System.out.println("&&&&&&& charge type:-"+di+"-"+directedChargeD+"+"+directedChargeI);
        if (di.equalsIgnoreCase("d")) {
          if (directedChargeI.equalsIgnoreCase("B")) {
            directRB.setSelected(true);
            indirectRB.setSelected(false);
            directRB.setEnabled(false);
            indirectRB.setEnabled(false);
          } else {
            directRB.setSelected(true);
            directRB.setEnabled(true);
            indirectRB.setEnabled(true);
          }
        } else {
          if (directedChargeD.equalsIgnoreCase("B")) {
            indirectRB.setSelected(true);
            directRB.setEnabled(false);
            indirectRB.setEnabled(false);
          } else {
            indirectRB.setSelected(true);
            directRB.setEnabled(true);
            indirectRB.setEnabled(true);
          }
        }
        //displayChargeType(directedChargeD,directedChargeI);
        if ("y".equalsIgnoreCase(directedChargeD) || "y".equalsIgnoreCase(directedChargeI)) {
          directRB.setSelected("y".equalsIgnoreCase(directedChargeD));
          indirectRB.setSelected("y".equalsIgnoreCase(directedChargeI));
          indirectRB.setEnabled(false);
          directRB.setEnabled(false);
        } else {
          indirectRB.setEnabled(true);
          directRB.setEnabled(true);
        }
      } else {
        directRB.setVisible(false);
        indirectRB.setVisible(false);
      }
      //determine whether to show copy and undo buttons or not
      if (rliTable.getRowCount() > 1) {
        //since the scrap flag is always there then the copy button should be there as well
        copyB.setEnabled(true);
        copyB.setVisible(true);
        undoB.setEnabled(true);
        undoB.setVisible(true);
        //}
        //since I am go thru here for all lines
        if ("y".equalsIgnoreCase(directedChargeD) || "y".equalsIgnoreCase(directedChargeI)) {
          h.put("LINE_USES_DIRECTED_CHARGE", new Boolean(true));
        } else {
          h.put("LINE_USES_DIRECTED_CHARGE", new Boolean(false));
        }
      } else { //end of determining whether to show copy and undo buttons or not
        copyB.setEnabled(false);
        copyB.setVisible(false);
        undoB.setEnabled(false);
        undoB.setVisible(false);
      }
    } else { //view is not requested
      if (number.intValue() == 2) {
        directRB.setVisible(true);
        indirectRB.setVisible(true);
        String di = h.get("CHARGE_TYPE").toString();
        if (BothHelpObjs.isBlankString(di) || di.equalsIgnoreCase("d")) {
          directRB.setSelected(true);
        } else {
          indirectRB.setSelected(true);
        }
        BigDecimal totalInvoicePrepQty = (BigDecimal)h.get("TOTAL_INVOICE_PREP_QTY");
        directRB.setEnabled(((Boolean)headerInfo.get("CAN_EDIT_MR")).booleanValue() && totalInvoicePrepQty.floatValue()==0);
        indirectRB.setEnabled(((Boolean)headerInfo.get("CAN_EDIT_MR")).booleanValue() && totalInvoicePrepQty.floatValue()==0);
      } else {
        directRB.setVisible(false);
        indirectRB.setVisible(false);
      }
    }

    //po_required
    String type = (String) h.get("CHARGE_TYPE");
    if (BothHelpObjs.isBlankString(type)) {
      type = "d";
    }
    Hashtable myh = (Hashtable) headerInfo.get("ACC_TYPE_H");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable) packH.get(key1);
    String needPo = (String) accSysTypeH.get("PO_REQUIRED");
    String unitPriceRequired = (String) accSysTypeH.get("UNIT_PRICE_REQUIRED");
    String releaserRequired = (String) accSysTypeH.get("RELEASER_REQUIRED");
    String poSeqRequired = (String) accSysTypeH.get("PO_SEQ_REQUIRED");
    h.put("UNIT_PRICE_STATUS"+type,unitPriceRequired);
    if ("p".equalsIgnoreCase(needPo)) {
      buildPoDetail(h,type,poSeqRequired,unitPriceRequired);
      ccardB.setEnabled(false);
      ccardB.setVisible(false);
    } else if ("c".equalsIgnoreCase(needPo)) {
      ccardB.setEnabled(true);
      ccardB.setVisible(true);
      poC.setVisible(false);
      poL.setVisible(false);
      refT.setVisible(false);
      refL.setVisible(false);
      unitPriceL.setVisible(false);
      unitPriceT.setVisible(false);
      currencyC.setVisible(false);
      poT.setVisible(false);
      poQtyL.setVisible(false);
      poQtyT.setVisible(false);
      poUomL.setVisible(false);
      poUomT.setVisible(false);
    } else {
      ccardB.setEnabled(false);
      ccardB.setVisible(false);
      poC.setVisible(false);
      poL.setVisible(false);
      refT.setVisible(false);
      refL.setVisible(false);
      unitPriceL.setVisible(false);
      unitPriceT.setVisible(false);
      currencyC.setVisible(false);
      poT.setVisible(false);
      poQtyL.setVisible(false);
      poQtyT.setVisible(false);
      poUomL.setVisible(false);
      poUomT.setVisible(false);
    }

    //release number
    if (h.get("RELEASE_NUM"+type) == null) {
      refT.setText("");
    } else {
      refT.setText(h.get("RELEASE_NUM"+type).toString());
    }

    //line extended price
    if ("Required".equalsIgnoreCase(unitPriceRequired) || "Display".equalsIgnoreCase(unitPriceRequired)) {
      BigDecimal quantity = new BigDecimal(h.get("QTY").toString());
      if (!BothHelpObjs.isBlankString(h.get("UNIT_PRICE").toString())) {
        try {
          BigDecimal unitPrice = new BigDecimal(h.get("UNIT_PRICE").toString());
          if (BothHelpObjs.isBlankString(h.get("PO_QTY"+type).toString())) {
            ePriceL.setText("");
          }else {
            BigDecimal poQty = new BigDecimal(h.get("PO_QTY" + type).toString());
            BigDecimal poUnitPrice = new BigDecimal(h.get("PO_UNIT_PRICE" + type).toString());
            float t = (poQty.floatValue()/quantity.floatValue())*poUnitPrice.floatValue();
            BigDecimal tmp = new BigDecimal(t);
            setLineExtPrice(quantity, tmp, (String) h.get("CURRENCY_ID"), (String) h.get("ITEM_DISPLAY_PKG_STYLE"));
          }
        }catch (Exception ee) {
          ePriceL.setText("");
        }
      }else {
        ePriceL.setText("");
      }
    }else {
      if (!BothHelpObjs.isBlankString(h.get("UNIT_PRICE").toString())) {
        BigDecimal unitPrice = new BigDecimal(h.get("UNIT_PRICE").toString());
        BigDecimal quantity = new BigDecimal(h.get("QTY").toString());
        setLineExtPrice(quantity, unitPrice, (String) h.get("CURRENCY_ID"), (String) h.get("ITEM_DISPLAY_PKG_STYLE"));
      }else {
        ePriceL.setText("");
      }
    }

    String tmpViewType = (String) headerInfo.get("VIEW_TYPE");
    if (!tmpViewType.equalsIgnoreCase("REQUEST")) {
      if (tmpViewType.equalsIgnoreCase("RELEASE") || tmpViewType.equalsIgnoreCase("APPROVE") || tmpViewType.equalsIgnoreCase("USE_APPROVE")) {
        //since I am allowing releaser(s) / finance approver(s) / use approver(s) to edit MR
        //well releaser(s) / finance approver(s) / use approver(s) in EditMR user group
        if (! ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
          delTypeC.setEnabled(false);
          dateT.setEnabled(false);
          dockC.setEnabled(false);
          delC.setEnabled(false);
          scrapC.setEnabled(false);
        }
      } else {
        delTypeC.setEnabled(false);
        dateT.setEnabled(false);
        dockC.setEnabled(false);
        delC.setEnabled(false);
        scrapC.setEnabled(false);
      }
    }

    // Cancellation button
    //trong 4/10
    String doc_num = (String) h.get("DOC_NUM");

    boolean authorized = cmis.getUserId().intValue() == ( (Integer) headerInfo.get("REQUESTOR_ID")).intValue() ||
        cmis.getGroupMembership().isGroupMember("CSR", headerInfo.get("HUB").toString()) ||
        cmis.getGroupMembership().isAdministrator(headerInfo.get("FACILITY_ID").toString());
    //boolean isSubmitted = (headerInfo.get("STATUS_DESC").toString().equalsIgnoreCase("submitted") && doc_num.length() > 0);
    boolean isSubmitted = (headerInfo.get("STATUS_DESC").toString().equalsIgnoreCase("submitted"));
    boolean notCancelled = ! (h.containsKey("CANCEL_STATUS") &&
                              (h.get("CANCEL_STATUS").toString().equalsIgnoreCase("Canceled") ||
                               h.get("CANCEL_STATUS").toString().equalsIgnoreCase("Cancel Req.")));
    cancelB.setEnabled(authorized && isSubmitted && notCancelled);

    buildMatDescTable();

    //trong 7-19
    String prAccountRequired = (String) accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    if (prAccountRequired.equalsIgnoreCase("y")) {
      buildChargeNumTable();
    } else {
      chargeP.setVisible(false);
    }

    loadingDetail = false;
  } //end of method

  void displayChargeType(String directedD, String directedI) {
    if (directedD.equalsIgnoreCase("Y") && directedI.equalsIgnoreCase("Y")) {
      directRB.setSelected(true);
      directRB.setEnabled(true);
      indirectRB.setEnabled(true);
    } else if (directedD.equalsIgnoreCase("Y") && directedI.equalsIgnoreCase("N")) {
      directRB.setSelected(true);
      directRB.setEnabled(true);
      indirectRB.setEnabled(true);
    } else if (directedD.equalsIgnoreCase("Y") && directedI.equalsIgnoreCase("B")) {
      directRB.setSelected(true);
      directRB.setEnabled(false);
      indirectRB.setEnabled(false);
    } else if (directedD.equalsIgnoreCase("N") && directedI.equalsIgnoreCase("Y")) {
      indirectRB.setSelected(true);
      directRB.setEnabled(true);
      indirectRB.setEnabled(true);
    } else if (directedD.equalsIgnoreCase("N") && directedI.equalsIgnoreCase("N")) {
      directRB.setSelected(true);
      directRB.setEnabled(true);
      indirectRB.setEnabled(true);
    } else if (directedD.equalsIgnoreCase("N") && directedI.equalsIgnoreCase("B")) {
      directRB.setSelected(true);
      directRB.setEnabled(false);
      indirectRB.setEnabled(false);
    } else if (directedD.equalsIgnoreCase("B") && (directedI.equalsIgnoreCase("Y") || directedI.equalsIgnoreCase("N"))) {
      indirectRB.setSelected(true);
      directRB.setEnabled(false);
      indirectRB.setEnabled(false);
    } else {
      directRB.setSelected(true);
      directRB.setEnabled(true);
      indirectRB.setEnabled(true);
    }
  }

  void setDeliveryPanel() {
    deliveryP.removeAll();
    deliveryP.revalidate();
    String s = delTypeC.getSelectedItem().toString();
    if (s.equalsIgnoreCase("hold")) {
      // do nothing, we just want a blank panel
    } else if (s.equalsIgnoreCase("Schedule")) {
      deliveryP.add(scheduleB, BorderLayout.CENTER);
      deliveryP.revalidate();
    } else {
      deliveryP.add(dateT, BorderLayout.CENTER);
      deliveryP.revalidate();
    }
    deliveryP.repaint();
  }

  void buildMatDescTable() {
    Hashtable h = getItemHash(selectedLine);
    Vector v = (Vector) h.get("MAT_DESC");

    boolean contFillUpBulk = false;
    Object[][] oa = new Object[v.size()][3];
    for (int i = 0; i < v.size(); i++) {
      Hashtable h1 = (Hashtable) v.elementAt(i);

      /*10-02-01 implementing line wrap
             oa[i][0] = new JTextArea(h1.get("DESC").toString());
             oa[i][1] = new JTextArea(h1.get("PKG").toString());
             oa[i][2] = new JTextArea(h1.get("GRADE").toString());
       */

      oa[i][0] = h1.get("DESC").toString();
      String ttmp = h1.get("PKG").toString();
      if (ttmp.indexOf("fill up bulk") >= 0) {
        contFillUpBulk = true;
      }
      oa[i][1] = ttmp;
      oa[i][2] = h1.get("GRADE").toString();

    }

    //CmisTableModel ctm;
    DbTableModel ctm;
    if (v.size() < 1) {
      ctm = new DbTableModel(descCols);
    } else {
      ctm = new DbTableModel(descCols, oa);
    }
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    ctm.setEditableFlag(0);
    matDescTable = new JTable(ctm);
    matDescTable.setCellSelectionEnabled(false);

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable matlTableStyle = (Hashtable) headerInfo.get("TABLE_STYLE");
    Color color = (Color) matlTableStyle.get("COLOR");
    Integer t = (Integer) matlTableStyle.get("INSET_TOP");
    Integer l = (Integer) matlTableStyle.get("INSET_LEFT");
    Integer b = (Integer) matlTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) matlTableStyle.get("INSET_RIGHT");
    Integer a = (Integer) matlTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
    TableColumnModel cm = matDescTable.getColumnModel();
    for (int i = 0; i < matDescTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    /*10-02-01
         matDescTable.setDefaultRenderer(JTextArea.class,new TextCellRender());      //implementing line wrap
         int height = matDescTable.getRowHeight() * 2;
         matDescTable.setRowHeight(height);
     */

    //font and foreground and background color for columns heading
    String fontName = (String) matlTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer) matlTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer) matlTableStyle.get("FONT_SIZE");
    matDescTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer( (Color) matlTableStyle.get("FOREGROUND"), (Color) matlTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    Enumeration enum1 = matDescTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    jPanel5.remove(descJsp);

    descJsp = new JScrollPane(matDescTable);
    jPanel5.add(descJsp, BorderLayout.CENTER);
    matDescTable.sizeColumnsToFit(0);
    int[] colWidths = new int[] {
        297, 118, 89};
    for (int i = 0; i < matDescTable.getColumnCount(); i++) {
      matDescTable.getColumn(matDescTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }
    matDescTable.validate();
    descJsp.validate();
    jPanel5.validate();

    //4-25-01
    if (contFillUpBulk) {
      ePriceL.setText(" ");
    }

  }

  void buildEmptyChargeNumTable() {
    chargeP.setVisible(true); //trong 7-19
    Hashtable tmph = getItemHash(selectedLine);
    String[] chargeCols = new String[] {};

    //trong 4/7
    Hashtable myH = (Hashtable) headerInfo.get("ACC_TYPE_H");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable) myH.get(accSysId);
    Hashtable accTypeH = (Hashtable) headerInfo.get("PACK");

    Vector chargeV = new Vector();
    Vector colHeader = new Vector();

    String numChargeTypes = (String) innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);
    String mt = "";
    if (number.intValue() == 2) {
      if (directRB.isSelected()) {
        mt = "d";
      } else {
        mt = "i";
      }
    } else {
      mt = "d";
    }
    String accType = accSysId + mt;
    Hashtable hh = (Hashtable) accTypeH.get(accType);
    colHeader = (Vector) hh.get("LABELS");

    chargeCols = new String[colHeader.size()];
    for (int i = 0; i < colHeader.size(); i++) {
      chargeCols[i] = colHeader.elementAt(i).toString();
    }

    // determine the number of rows
    int numRows = 2;
    Object[][] oa = new Object[numRows][colHeader.size()];

    // set all columns to empty strings
    for (int i = 0; i < oa.length; i++) {
      for (int j = 0; j < chargeCols.length; j++) {
        oa[i][j] = "";
      }
    }

    int[] colWidths = new int[] {
        0};
    if (colHeader.size() == 3) {
      colWidths = new int[] {
          71, 71, 48};
    } else {
      colWidths = new int[] {
          142, 48};
    }

    CmisTableModel ctm;
    ctm = new CmisTableModel(chargeCols, oa);

    chargeNumTable = new CmisTable(ctm);
    chargeNumTable.setCellSelectionEnabled(true);

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Color color = new Color(255, 255, 255);
    Integer t = new Integer(0);
    Integer l = new Integer(1);
    Integer b = new Integer(1);
    Integer r = new Integer(1);
    Integer a = new Integer(0);
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
    TableColumnModel cm = chargeNumTable.getColumnModel();
    for (int i = 0; i < chargeNumTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = "Dialog";
    Integer fontStyle = new Integer(0);
    Integer fontSize = new Integer(10);
    chargeNumTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer(new Color(255, 255, 255), new Color(0, 0, 66), renderers[0].getBorder());
    Enumeration enum1 = chargeNumTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    chargeP.remove(chargeJsp);
    chargeP.validate();
    chargeJsp = new JScrollPane(chargeNumTable);
    chargeP.add(chargeJsp, BorderLayout.CENTER);
    //rliTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    for (int i = 0; i < chargeNumTable.getColumnCount(); i++) {
      chargeNumTable.getColumn(chargeNumTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }
    chargeNumTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
      }

      public void mouseClicked(MouseEvent e) {
        //System.out.println("============ empty charge table mouse clicked");
        int selectedRow = chargeNumTable.getSelectedRow();
        int selectedCol = chargeNumTable.getSelectedColumn();
        buildChargeNumTable();
        setSelectedRowForChargeTable(selectedRow, selectedCol);
      }
    });
    chargeNumTable.validate();
    chargeJsp.validate();
    chargeP.validate();
  }

  void buildPoDetail(Hashtable h, String type, String poSeqRequired, String unitPriceRequired) {
    //if no PO then allow user to enter PO
    if ( ( (Boolean) h.get("EDIT_PO")).booleanValue()) {
      String tmpPO = (String) h.get("PO"+type);
      if (!BothHelpObjs.isBlankString(tmpPO)) {
        poT.setText(tmpPO);
      } else {
        poT.setText("");
      }
      poT.setEnabled(true);
      poT.setVisible(true);
      poC.setVisible(false);
    } else {
      Vector v = (Vector) h.get("ALL_POS");
      if (!BothHelpObjs.isBlankString( (String) h.get("PO"+type))) {
        if (!v.contains(h.get("PO"+type).toString())) {
          v.addElement(h.get("PO"+type).toString());
        }
      }
      v = BothHelpObjs.sort(v);
      //making sure the Select a PO is the first one on list
      if (v.contains("Select a PO")) {
        v.remove("Select a PO");
        v.insertElementAt("Select a PO", 0);
      }
      if (poC.getItemCount() > 0) {
        poC.removeAllItems();
      }
      poC = ClientHelpObjs.loadChoiceFromVector(poC, v);
      if (!BothHelpObjs.isBlankString( (String) h.get("PO"+type))) {
        poC.setSelectedItem(h.get("PO"+type).toString());
      } else {
        poC.setSelectedIndex(0);
      }
      poC.setEnabled(true);
      poC.setVisible(true);
      poT.setEnabled(false);
      poT.setVisible(false);
    }
    poC.setEditable(false);
    poL.setVisible(true);
    ccardB.setEnabled(false);
    ccardB.setVisible(false);
    if ("U".equalsIgnoreCase(poSeqRequired)) {
      if (h.containsKey("RELEASE_NUM"+type)) {
        refT.setText(h.get("RELEASE_NUM" + type).toString());
      }else {
        refT.setText("");
      }
      refT.setVisible(true);
      refL.setVisible(true);
      refT.setEnabled(true);
    } else {
      refT.setVisible(false);
      refL.setVisible(false);
      refT.setEnabled(false);
    }
    if ("Required".equalsIgnoreCase(unitPriceRequired) || "Display".equalsIgnoreCase(unitPriceRequired)) {
      //if po unit price is display then populate data from line item
      if ("Display".equalsIgnoreCase(unitPriceRequired)) {
        if (BothHelpObjs.isBlankString(h.get("PO_UNIT_PRICE"+type).toString())) {
          h.put("PO_UNIT_PRICE"+type, h.get("CURRENT_CATALOG_PRICE").toString());
        }
        if (BothHelpObjs.isBlankString(h.get("PO_UNIT_PRICE_CURRENCY_ID"+type).toString())) {
          h.put("PO_UNIT_PRICE_CURRENCY_ID"+type, h.get("CURRENT_CURRENCY_ID").toString());
        }
        if (BothHelpObjs.isBlankString(h.get("PO_QTY"+type).toString())) {
          h.put("PO_QTY"+type,h.get("QTY").toString());
        }
        if (BothHelpObjs.isBlankString(h.get("PO_UOM"+type).toString())) {
          h.put("PO_UOM"+type,"EA");
        }
        if (BothHelpObjs.isBlankString(h.get("PO_UOSQPE"+type).toString())) {
          h.put("PO_UOSQPE"+type,"1");
        }
        //
        h.put("PO_QTY_FROM_DISPLAY"+type,"");
      }
      //populating po unit price and currency
      Hashtable tmpCurrencyList = (Hashtable) headerInfo.get("CURRENCY_LIST");
      Vector currencyIDV = (Vector) tmpCurrencyList.get("CURRENCY_ID");
      currencyC.removeItemListener(currencyItemAdapter);
      currencyC.removeAllItems();
      if (!currencyIDV.contains("Select One")) {
        currencyIDV.insertElementAt("Select One", 0);
        ( (Vector) tmpCurrencyList.get("CURRENCY_DESC")).insertElementAt("Select One", 0);
        ( (Vector) tmpCurrencyList.get("CONVERSION_FACTOR")).insertElementAt("Select One", 0);
      }
      currencyC = ClientHelpObjs.loadChoiceFromVector(currencyC, currencyIDV);
      if (currencyIDV.contains( (String) h.get("PO_UNIT_PRICE_CURRENCY_ID"+type))) {
        currencyC.setSelectedItem( (String) h.get("PO_UNIT_PRICE_CURRENCY_ID"+type));
      }
      if (h.containsKey("PO_UNIT_PRICE"+type)) {
        unitPriceT.setText( (String) h.get("PO_UNIT_PRICE" + type));
      }else {
        unitPriceT.setText("");
      }
      //populating po qty
      if (h.containsKey("PO_QTY"+type)) {
        poQtyT.setText( (String) h.get("PO_QTY" + type));
      }else {
        poQtyT.setText("");
      }
      //populating po uom
      if (h.containsKey("PO_UOM"+type)) {
        poUomT.setText( (String) h.get("PO_UOM" + type));
      }else {
        poUomT.setText("");
      }
      //displaying po data fields
      unitPriceL.setVisible(true);
      unitPriceT.setVisible(true);
      currencyC.setVisible(true);
      currencyItemAdapter = new RequestsWin_Currency_itemAdapter(this);
      currencyC.addItemListener(currencyItemAdapter);
      poQtyL.setVisible(true);
      poQtyT.setVisible(true);
      poUomL.setVisible(true);
      poUomT.setVisible(true);
      if ("Required".equalsIgnoreCase(unitPriceRequired)) {
        unitPriceT.setEnabled(true);
        currencyC.setEnabled(true);
        poQtyT.setEnabled(true);
        poUomT.setEnabled(true);
      } else {     //display only
        unitPriceT.setEnabled(false);
        currencyC.setEnabled(false);
        poQtyT.setEnabled(false);
        poUomT.setEnabled(false);
      }

      //set extended price and line item price
      if (!BothHelpObjs.isBlankString(unitPriceT.getText().trim())) {
        unitPriceT_actionPreformed();
      }
    } else {       //po unit price is not required
      unitPriceL.setVisible(false);
      unitPriceT.setVisible(false);
      currencyC.setVisible(false);
      unitPriceT.setEnabled(false);
      currencyC.setEnabled(false);
      poQtyL.setVisible(false);
      poQtyT.setVisible(false);
      poUomL.setVisible(false);
      poUomT.setVisible(false);
    }

    //if user is not allow to edit MR then disable PO info
    BigDecimal totalInvoicePrepQty = (BigDecimal)h.get("TOTAL_INVOICE_PREP_QTY");
    if (!((Boolean)headerInfo.get("CAN_EDIT_MR")).booleanValue() ||
        totalInvoicePrepQty.floatValue() > 0) {
      poC.setEnabled(false);
      poT.setEnabled(false);
      refT.setEnabled(false);
      unitPriceT.setEnabled(false);
      currencyC.setEnabled(false);
      poQtyT.setEnabled(false);
      poUomT.setEnabled(false);
    }
  } //end of method

  void buildChargeNumTable() {
    chargeP.setVisible(true);
    Hashtable tmph = getItemHash(selectedLine);
    String[] chargeCols = new String[] {};
    //
    Hashtable myH = (Hashtable) headerInfo.get("ACC_TYPE_H");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    Hashtable innerH = (Hashtable) myH.get(accSysId);
    Hashtable accTypeH = (Hashtable) headerInfo.get("PACK");
    String viewType = headerInfo.get("VIEW_TYPE").toString();

    Vector chargeV = new Vector();
    Vector colHeader = new Vector();

    Integer editable = new Integer(0);
    String numChargeTypes = (String) innerH.get("NUM_CHARGE_TYPES");
    Integer number = new Integer(numChargeTypes);
    BigDecimal totalInvoicePrepQty = (BigDecimal)tmph.get("TOTAL_INVOICE_PREP_QTY");
    if (number.intValue() == 2) {
      if (directRB.isSelected()) {
        if (((Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() &&
            totalInvoicePrepQty.floatValue() == 0) {
          if (tmph.get("DIRECTED_TYPEd").toString().equalsIgnoreCase("Y")) {
            chargeV = (Vector) tmph.get("DIRECTED_CHARGE_NUMBERd");
          } else {
            chargeV = (Vector) tmph.get("CHARGE_NUM_DIRECT");
          }
        } else {
          chargeV = (Vector) tmph.get("CHARGE_NUM_DIRECT");
        }
        String accType = accSysId + "d";
        Hashtable hh = (Hashtable) accTypeH.get(accType);
        colHeader = (Vector) hh.get("LABELS");
        editable = (Integer) hh.get("EDIT_TABLE");
      } else {
        if (((Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() &&
            totalInvoicePrepQty.floatValue() == 0) {
          if (tmph.get("DIRECTED_TYPEi").toString().equalsIgnoreCase("Y")) {
            chargeV = (Vector) tmph.get("DIRECTED_CHARGE_NUMBERi");
          } else {
            chargeV = (Vector) tmph.get("CHARGE_NUM_INDIRECT");
          }
        } else {
          chargeV = (Vector) tmph.get("CHARGE_NUM_INDIRECT");
        }
        String accType = accSysId + "i";
        Hashtable hh = (Hashtable) accTypeH.get(accType);
        colHeader = (Vector) hh.get("LABELS");
        editable = (Integer) hh.get("EDIT_TABLE");
      }
    } else {
      if (((Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() &&
            totalInvoicePrepQty.floatValue() == 0) {
        if (tmph.get("DIRECTED_TYPEd").toString().equalsIgnoreCase("Y")) {
          chargeV = (Vector) tmph.get("DIRECTED_CHARGE_NUMBERd");
        } else {
          chargeV = (Vector) tmph.get("CHARGE_NUM_DIRECT");
        }
      } else {
        chargeV = (Vector) tmph.get("CHARGE_NUM_DIRECT");
      }
      String accType = accSysId + "d";
      Hashtable hh = (Hashtable) accTypeH.get(accType);
      colHeader = (Vector) hh.get("LABELS");
      editable = (Integer) hh.get("EDIT_TABLE");
    }

    chargeCols = new String[colHeader.size()];
    for (int i = 0; i < colHeader.size(); i++) {
      chargeCols[i] = colHeader.elementAt(i).toString();
    }

    // determine the number of rows
    int numRows = 0;
    if (((Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() &&
            totalInvoicePrepQty.floatValue() == 0) {
      boolean canEditD = false;
      boolean canEditI = false;
      if (number.intValue() == 2) {
        canEditD = ( (Boolean) tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        canEditI = ( (Boolean) tmph.get("CAN_EDIT_CHARGE_NUM_I")).booleanValue();
        if (directRB.isSelected()) {
          if (canEditD) {
            numRows = 35;
          } else {
            numRows = chargeV.size();
          }
        } else {
          if (canEditI) {
            numRows = 35;
          } else {
            numRows = chargeV.size();
          }
        }
      } else {
        canEditD = ( (Boolean) tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        if (canEditD) {
          numRows = 35;
        } else {
          numRows = chargeV.size();
        }
      }
    } else {
      numRows = chargeV.size();
    }
    Object[][] oa = new Object[numRows][colHeader.size()];

    // set all columns to empty strings
    for (int i = 0; i < oa.length; i++) {
      for (int j = 0; j < chargeCols.length; j++) {
        oa[i][j] = "";
      }
    }

    for (int i = 0; i < chargeV.size(); i++) {
      Hashtable h = (Hashtable) chargeV.elementAt(i);
      int r = 0;
      oa[i][r++] = h.get("ACCT_NUM_1").toString();
      if (colHeader.size() == 3) {
        oa[i][r++] = h.get("ACCT_NUM_2").toString();
      }
      //if there is only one charge number then set default percent to 100
      if (chargeV.size() == 1) {
        oa[i][r++] = "100";
      } else {
        oa[i][r++] = h.get("PERCENTAGE").toString();
      }
    }
    int[] colWidths = new int[] {
        0};
    if (colHeader.size() == 3) {
      colWidths = new int[] {
          71, 71, 48};
    } else {
      colWidths = new int[] {
          142, 48};
    }

    DbTableModel ctm = new DbTableModel(chargeCols, oa);

    if (((Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue() &&
            totalInvoicePrepQty.floatValue() == 0) {
      boolean bd = false;
      boolean bi = false;
      String myDI = "";
      String myDD = "";
      if (number.intValue() == 2) {
        bd = ( (Boolean) tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        bi = ( (Boolean) tmph.get("CAN_EDIT_CHARGE_NUM_I")).booleanValue();
        myDI = (String) tmph.get("DIRECTED_TYPEi");
        myDD = (String) tmph.get("DIRECTED_TYPEd");
        if (directRB.isSelected()) {
          if (bd) {
            if (chargeCols.length == 2) {
              ctm.setEditableFlag(3);
            }
            if (chargeCols.length == 3) {
              ctm.setEditableFlag(editable.intValue());
            }
          } else {
            if (myDD.equalsIgnoreCase("Y")) {
              ctm.setEditableFlag(0);
            } else {
              if (chargeCols.length == 2) {
                ctm.setEditableFlag(2);
              }
              if (chargeCols.length == 3) {
                ctm.setEditableFlag(4);
              }
            }
          }
        } else {
          if (bi) {
            if (chargeCols.length == 2) {
              ctm.setEditableFlag(3);
            }
            if (chargeCols.length == 3) {
              ctm.setEditableFlag(editable.intValue());
            }
          } else {
            if (myDI.equalsIgnoreCase("Y")) {
              ctm.setEditableFlag(0);
            } else {
              if (chargeCols.length == 2) {
                ctm.setEditableFlag(2);
              }
              if (chargeCols.length == 3) {
                ctm.setEditableFlag(4);
              }
            }
          }
        }
      } else {
        bd = ( (Boolean) tmph.get("CAN_EDIT_CHARGE_NUM_D")).booleanValue();
        myDD = (String) tmph.get("DIRECTED_TYPEd");
        if (bd) {
          if (chargeCols.length == 2) {
            ctm.setEditableFlag(3);
          }
          if (chargeCols.length == 3) {
            ctm.setEditableFlag(editable.intValue());
          }
        } else {
          if (myDD.equalsIgnoreCase("Y")) {
            ctm.setEditableFlag(0);
          } else {
            if (chargeCols.length == 2) {
              ctm.setEditableFlag(2);
            }
            if (chargeCols.length == 3) {
              ctm.setEditableFlag(4);
            }
          }
        }
      }
    } else {
      ctm.setEditableFlag(0);
    }
    TableSorter sorter = new TableSorter(ctm);
    chargeNumTable = new CmisTable(sorter);
    sorter.setColTypeFlag(ctm.getColumnTypesString());
    sorter.addMouseListenerToHeaderInTable(chargeNumTable);

    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable) headerInfo.get("TABLE_STYLE");
    Color color = (Color) chargeTableStyle.get("COLOR");
    Integer t = (Integer) chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer) chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer) chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer) chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer) chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(), l.intValue(), b.intValue(), r.intValue()), a.intValue());
    TableColumnModel cm = chargeNumTable.getColumnModel();
    for (int i = 0; i < chargeNumTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String) chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer) chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer) chargeTableStyle.get("FONT_SIZE");
    chargeNumTable.setFont(new Font(fontName, fontStyle.intValue(), fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer( (Color) chargeTableStyle.get("FOREGROUND"), (Color) chargeTableStyle.get("BACKGROUND"), renderers[0].getBorder());
    Enumeration enum1 = chargeNumTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ( (TableColumn) enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }

    chargeNumTable.setCellSelectionEnabled(true);
    chargeP.remove(chargeJsp);
    chargeP.validate();
    chargeJsp = new JScrollPane(chargeNumTable);
    chargeP.add(chargeJsp, BorderLayout.CENTER);
    for (int i = 0; i < chargeNumTable.getColumnCount(); i++) {
      chargeNumTable.getColumn(chargeNumTable.getColumnName(i)).setPreferredWidth(colWidths[i]);
    }
    //adding mouse action listener to table
    chargeNumTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        chargeTableClicked(e);
      }
    });

    chargeNumTable.validate();
    chargeJsp.validate();
    chargeP.validate();
  }

  Hashtable getItemHash(Integer line) {
    String s = line.toString();
    for (int i = 0; i < lineItems.size(); i++) {
      Hashtable r = (Hashtable) lineItems.elementAt(i);
      if (r.get("LINE_NUM").toString().equals(s)) {
        return r;
      }
    }
    return new Hashtable();
  }

  void setDockCombo() {
    dockC.removeAllItems();
    Hashtable itemHash = getItemHash(selectedLine);
    Vector v = null;
    //if first time save original dock
    if (!itemHash.containsKey("ORIGINAL_DOCK")) {
      v = (Vector) itemHash.get("ALL_DOCKS");
      Vector tmp = new Vector(v.size());
      for (int j = 0; j < v.size(); j++) {
        tmp.addElement( (String) v.elementAt(j));
      }
      itemHash.put("ORIGINAL_DOCK", tmp);
    } else { //copy original into all_delivery_to
      v = (Vector) itemHash.get("ORIGINAL_DOCK");
      Vector tmp = new Vector(v.size());
      for (int j = 0; j < v.size(); j++) {
        tmp.addElement( (String) v.elementAt(j));
      }
      itemHash.put("ALL_DOCKS", tmp);
      v = tmp;
    }

    if (scrapC.isSelected()) {
      if (itemHash.containsKey("SCRAP_OBSOLETE_DOCK")) {
        Vector tmpScrapDock = (Vector) itemHash.get("SCRAP_OBSOLETE_DOCK");
        for (int i = 0; i < tmpScrapDock.size(); i++) {
          String scrapDock = (String) tmpScrapDock.elementAt(i);
          if (!v.contains(scrapDock)) {
            v.addElement(scrapDock);
          }
        }
      } //end of if hashtable contain scrap dock
    } //end of if scrap
    dockC = ClientHelpObjs.loadChoiceFromVector(dockC, v);
    ClientHelpObjs.makeDefaultColors(dockC);
  }

  void setDeliverToCombo() {
    delC.setVisible(false);
    if (dockC == null || dockC.getSelectedIndex() < 0) {
      if (delC.getItemCount() > 0) {
        delC.removeAllItems();
      }
      delC.setVisible(true);
      return;
    }
    String dock = dockC.getSelectedItem().toString();
    Hashtable itemHash = getItemHash(selectedLine);
    Vector v = null;
    //if first time save original delivery point
    if (!itemHash.containsKey("ORIGINAL_DELIVER_TO")) {
      v = (Vector) itemHash.get("ALL_DELIVER_TO");
      Vector tmp = new Vector(v.size());
      for (int j = 0; j < v.size(); j++) {
        tmp.addElement( (Hashtable) v.elementAt(j));
      }
      itemHash.put("ORIGINAL_DELIVER_TO", tmp);
    } else { //copy original into all_delivery_to
      v = (Vector) itemHash.get("ORIGINAL_DELIVER_TO");
      Vector tmp = new Vector(v.size());
      for (int j = 0; j < v.size(); j++) {
        tmp.addElement( (Hashtable) v.elementAt(j));
      }
      itemHash.put("ALL_DELIVER_TO", tmp);
      v = tmp;
    }
    Vector out = new Vector();

    //if scrap is selected than add scrap/obsolete delivery point to "ALL_DELIVER_TO"
    if (scrapC.isSelected()) {
      if (itemHash.containsKey("SCRAP_OBSOLETE_DEL_TO")) {
        if (v.size() == 1) {
          Vector tmpScrapDelTo = (Vector) itemHash.get("SCRAP_OBSOLETE_DEL_TO");
          for (int i = 0; i < tmpScrapDelTo.size(); i++) {
            v.addElement( (Hashtable) tmpScrapDelTo.elementAt(i));
          }
        }
      } //end of if hashtable contain scrap delivery point
    } //end of if scrap

    for (int i = 0; i < v.size(); i++) {
      Hashtable h = (Hashtable) v.elementAt(i);
      if (h.get("DOCK").toString().equals(dock)) {
        out.addElement(getDelPointText(dock, h.get("DEL_POINT").toString(), itemHash));
      }
    }
    out = BothHelpObjs.sort(out);
    if (out.size() > 1) {
      out.insertElementAt(NEED_DEL_TO, 0);

    }
    delToP.remove(delC);
    delC = new JComboBox(out);

    if (itemHash.get("DELIVER_TO") != null &&
        !BothHelpObjs.isBlankString( (String) itemHash.get("DELIVER_TO"))) {
      delC.setSelectedItem(getDelPointText(dock, itemHash.get("DELIVER_TO").toString(), itemHash));
    } else {
      delC.setSelectedItem(NEED_DEL_TO);
    }
    delToP.add(delC, BorderLayout.CENTER);
    delC.setVisible(true);
    delC.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        delC_itemStateChanged(e);
      }
    });
    ClientHelpObjs.makeDefaultColors(delC);
  } //end of method

  JComboBox getPoComboBox() {
    JComboBox j = new JComboBox();
    j.setMaximumRowCount(9);
    j.revalidate();
    return j;
  }

  JComboBox loadComboBox(JComboBox inBox) {
    Hashtable h = this.getItemHash(selectedLine);
    String s = headerInfo.get("NEEDS_PO").toString();
    if (BothHelpObjs.isBlankString(s)) {
      s = "A";
    }
    boolean loadIt = false;
    if (s.equalsIgnoreCase("a")) {
      loadIt = true;
    } else if (loadingSummary) {
      loadIt = s.equalsIgnoreCase("d"); //default to direct charge
    } else {
      loadIt = s.equalsIgnoreCase(h.get("CHARGE_TYPE").toString());
    }
    return inBox;
  }

  boolean submitRequest(String action) {
    saveCurrentDetail();
    if (headerInfo.get("VIEW_TYPE").toString().equalsIgnoreCase("REQUEST")) {
      chickenPotPie();
    }
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialRequest", cmis);
      Hashtable query = new Hashtable();
      query.put("USER_ID", cmis.getUserId().toString());
      query.put("FUNCTION", action);
      query.put("DETAIL_INFO", lineItems);
      headerInfo.put("END_USER", endUserT.getText());
      headerInfo.put("DEPARTMENT", deptT.getText());
      headerInfo.put("CONTACT_INFO", contactT.getText());
      headerInfo.put("USE_REJECTED_LINE", selectedLine);
      headerInfo.put("PAY_LOAD_ID", cmis.getPayLoadID());
      headerInfo.put("ARIBA_LOGON", new Boolean(cmis.getAribaLogon()));
      query.put("HEADER_INFO", headerInfo);
      query.put("SELECTED_LINE", selectedLine);
      query.put("REQ_NUM", (new Integer(rNum)).toString()); //String, String
      query.put("REJECT_REASON", rejectionReason);
      if ( (action.equalsIgnoreCase("APPROVE") || action.equalsIgnoreCase("USE_APPROVE") ||
            action.equalsIgnoreCase("RELEASE")) && ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
        Vector newMRQuantityV = new Vector();
        getMRQuantity(newMRQuantityV);
        newMRQuantityV.trimToSize();
        query.put("NEW_MR_QUANTITY", newMRQuantityV);
        query.put("ORIG_MR_QUANTITY", origMRQuantityV);
      }
      for (int q = 0; q < lineItems.size(); q++) {
        Hashtable rh = (Hashtable) lineItems.elementAt(q);
      }
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        return false;
      }
      Hashtable h = (Hashtable) result.get("RETURN_CODE");
      Boolean b = (Boolean) h.get("SUCCEEDED");

      if ("submit".equalsIgnoreCase(action) || "Update".equalsIgnoreCase(action)) {
        ronum = (Integer) h.get("ERROR_ROW");
        rocol = (Integer) h.get("ERROR_COL");
        mylinenum1 = (Integer) h.get("ERROR_LINE");
      }

      Boolean evalflag = (Boolean) headerInfo.get("EVAL");
      if (evalflag.booleanValue()) {
        if (!b.booleanValue()) {
          GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
          gd.setMsg(h.get("MSG").toString());
          gd.setVisible(true);
        }
      } else {
        if (!b.booleanValue()) {
          GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
          gd.setMsg(h.get("MSG").toString());
          gd.setVisible(true);
        }

        if (result.containsKey("HEADER_INFO")) {
          requestDataH = new Hashtable(3);
          requestDataH.put("HEADER_INFO", (Hashtable) result.get("HEADER_INFO"));
          requestDataH.put("DETAIL_INFO", (Vector) result.get("DETAIL_INFO"));
          requestDataH.put("NOW", (Calendar) result.get("NOW"));
        }

        if ("SUBMIT".equalsIgnoreCase(action)) {
          Boolean postingMsg = (Boolean) result.get("POSTING_MSG");
          if (postingMsg.booleanValue()) {
            //sendPost message to their server
            //11-13-01 test - posting message and run browser
            try {
              //System.out.println("------- posting message .... ");
              new URLCall(BothHelpObjs.sendPostWriteToFile( (String) result.get("URL"), (String) result.get("XML_DOC"), "application/x-www-form-urlencoded", "PunchOut.html"), cmis, true);
            } catch (Exception ee) {
              ee.printStackTrace();
              GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
              gd.setMsg("Problem occurred while posting message to server.");
              gd.setVisible(true);
            }
          } //end of posting msg
        }
      } //end of not engineering evaluation
      return b.booleanValue();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  void chickenPotPie() {
    // do this to make sure all the item hashtables have info entered
    // I couldn't think of a better name
    for (int i = 0; i < lineItems.size(); i++) {
      Hashtable h = getItemHash(new Integer(i + 1));
      if (!h.contains("DELIVERY_QTY") || h.get("DELIVERY_QTY") == null) {
        h.put("DELIVERY_QTY", new String(h.get("QTY").toString()));
      }
      if (!h.contains("DEL_TYPE") || h.get("DEL_TYPE") == null) {
        h.put("DEL_TYPE", "On or before");
      }
      if (!h.contains("DEL_FREQ") || h.get("DEL_FREQ") == null) {
        h.put("DEL_FREQ", "Once");
      }
    }
  }

  void goDeleteMR() {
    ConfirmDlg md = new ConfirmDlg(cmis.getMain(), "Delete Material Request", true);
    md.setMsg("Do you want to delete this material request?");
    md.setVisible(true);
    if (md.getConfirmationFlag()) {
      if (submitRequest("DELETE_MR")) {
        cmis.getMain().goRequest(2, rNum);
      } else {
        loadIt();
      }
    }
  }

  void goDeleteLine() {
    if (lineItems.size() == 1) {
      ConfirmDlg cd = new ConfirmDlg(cmis.getMain(), "Delete Material Request", true);
      cd.setMsg("There is only one line on this material request, would you like to delete this material request?");
      cd.setVisible(true);
      if (cd.getConfirmationFlag()) {
        if (submitRequest("DELETE_MR")) {
          cmis.getMain().goRequest(2, rNum);
        } else {
          loadIt();
        }
      }
      return;
    }
    ConfirmDlg md = new ConfirmDlg(cmis.getMain(), "Delete Line Item", true);
    md.setMsg("Do you want to delete line item " + selectedLine.toString() + "?");
    md.setVisible(true);
    if (md.getConfirmationFlag()) {
      submitRequest("DELETE_LINE");
      loadIt();
    }
  }

  void goRejectUsage() {
    EditDlg RjW = new EditDlg(cmis.getMain(), "Reject Comment", true, "Enter reason(s) for rejection:");
    RjW.setVisible(true);
    if (!BothHelpObjs.isBlankString(RjW.getText())) {
      headerInfo.put("REJECT_COMMENT", RjW.getText());
    } else {
      headerInfo.put("REJECT_COMMENT", "");
    }
    RjW.dispose();

    if (submitRequest("REJECT_USE_APPROVE")) {
      loadIt();
    }
  }

  void goRejectLineUsage() {
    EditDlg RjW = new EditDlg(cmis.getMain(), "Reject Comment", true, "Enter reason(s) for rejection:");
    RjW.setVisible(true);
    if (!BothHelpObjs.isBlankString(RjW.getText())) {
      headerInfo.put("REJECT_COMMENT", RjW.getText());
    } else {
      headerInfo.put("REJECT_COMMENT", "");
    }
    RjW.dispose();
    headerInfo.put("USE_APPROVAL_REJECT_LINE", new Integer(rliTable.getSelectedRow()));
    if (submitRequest("REJECT_LINE_USE_APPROVE")) {
      //1-24-03
      //cmis.getMain().goRequest(0,rNum);
      loadIt();
    }
  }

  void goApproveUsage(String type) {
    //since I am allowing use approver(s) to edit MR
    //well use approver(s) in EditMR user group
    if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
      forceStopEditing();
      saveCurrentDetail();
      if (!preSubmitAudits()) {
        return;
      }
      //make sure the use approver does not change qty to go above approved dollar limit
      if (!auditApprovedDollarLimit()) {
        return;
      }
    }
    //keep track of whether approver approved the whole MR or certain line only
    headerInfo.put("USE_APPROVED_TYPE", type);
    if ("Line".equalsIgnoreCase(type)) {
      headerInfo.put("USE_APPROVAL_APPROVED_LINE", new Integer(rliTable.getSelectedRow()));
    }
    //finally send request to server
    if (submitRequest("USE_APPROVE")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Approved", true);
      gd.setMsg("Request successfully approved.");
      gd.setVisible(true);

      loadIt();
    }
  }

  boolean auditApprovedDollarLimit() {
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    boolean approverRequired = false;
    for (int i = 0; i < lineItems.size(); i++) {
      Hashtable h = (Hashtable) lineItems.elementAt(i);
      String key1 = accSysId + (String) h.get("CHARGE_TYPE");
      Hashtable accSysTypeH = (Hashtable) packH.get(key1);
      String needApprover = (String) accSysTypeH.get("APPROVER_REQUIRED");
      if ("y".equalsIgnoreCase(needApprover)) {
        approverRequired = true;
      }
    }
    if (approverRequired) {
      try {
        String temp = priceL.getText();
        temp = temp.substring(0,temp.indexOf(" "));
        Float f = new Float(temp);
        Float approvedDollarLimit = (Float) headerInfo.get("APPROVED_DOLLAR_LIMIT");
        if (f.floatValue() > approvedDollarLimit.floatValue()) {
          GenericDlg.showMessage("The extended price of this request is greater than approved value: " + approvedDollarLimit.floatValue() + "\nPlease reduce quantity and try again.");
          return false;
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  } //end of method

  void goApproveRequest() {
    //since I am allowing finance approver(s) to edit MR
    //well finance approver(s) in EditMR user group
    forceStopEditing();
    if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
      saveCurrentDetail();
      if (!preSubmitAudits()) {
        return;
      }
    }

    if (submitRequest("APPROVE")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Approved", true);
      gd.setMsg("Request successfully approved.");
      gd.setVisible(true);

      loadIt();
    }
  }

  void goReleaseRequest() {
    //since I am allowing releaser(s) to edit MR
    //well releaser(s) in EditMR user group
    //if (((Boolean)headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
    forceStopEditing();
    saveCurrentDetail();
    if (!preSubmitAudits()) {
      return;
    }
    //}

    if (submitRequest("RELEASE")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Released", true);
      gd.setMsg("Request successfully released.");
      gd.setVisible(true);

      loadIt();
    }
  }

  void goSaveMR() {
    forceStopEditing();
    if (submitRequest("SAVE")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Save", true);
      gd.setMsg("Request successfully saved.");
      gd.setVisible(true);
    }

    loadIt();

  } //end of method

  void goRejectMR() {
    // get rejection reason
    submitRequest("REJECT");
    loadIt();
  }

  void goUpdateMR() {
    forceStopEditing();
    saveCurrentDetail();
    if (!preSubmitAudits()) {
      return;
    }
    //update data
    if (submitRequest("UPDATE")) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Update", true);
      gd.setMsg("Request successfully updated.");
      gd.setVisible(true);

      loadIt();
    } else {
      auditFailed(mylinenum1.intValue());
      ListSelectionModel lsm3 = chargeNumTable.getSelectionModel();
      lsm3.setSelectionInterval(ronum.intValue(), ronum.intValue());
      chargeNumTable.scrollRectToVisible(chargeNumTable.getCellRect(ronum.intValue(), 0, false));
    }
  } //end of method

  void goSubmitRequest() {
    if (headerInfo.get("VIEW_TYPE").toString().equalsIgnoreCase("VIEW")) {
      return;
    }
    forceStopEditing();
    saveCurrentDetail();
    if (!preSubmitAudits()) {
      return;
    }

    try {
      //trong 3/8/00
      Boolean evalflag = (Boolean) headerInfo.get("EVAL");
      if (evalflag.booleanValue()) {
        forceStopEditing();
        if (submitRequest("SAVE")) {
          sendEvalForApproval();
        } else {
          auditFailed(mylinenum1.intValue());
          ListSelectionModel lsm2 = chargeNumTable.getSelectionModel();
          lsm2.setSelectionInterval(ronum.intValue(), ronum.intValue());
          chargeNumTable.scrollRectToVisible(chargeNumTable.getCellRect(ronum.intValue(), 0, false));
        }
      } else {
        if (submitRequest("SUBMIT")) {
          Hashtable tmpHeader = (Hashtable) requestDataH.get("HEADER_INFO");
          String tmpStatus = (String) tmpHeader.get("STATUS");
          if ("compchk".equalsIgnoreCase(tmpStatus) || "compchk2".equalsIgnoreCase(tmpStatus)) {
            //waiting for finance approvers, otherwise waiting for use approvers
            GenericDlg gd = new GenericDlg(cmis.getMain(), "Submit", true);
            if ("compchk".equalsIgnoreCase(tmpStatus)) {
              gd.setMsg(
                  "Your order is above your dollar limit and has been sent for finance approval.\nClick the 'Pending' under Ext. Unit Price to see approvers and status.\nRemember, orders for min/max parts must be released to us by 1:00p.m\nat your designated hub to be filled for next day delivery.");
            } else {
              gd.setMsg("Your order is above your use limit and has been sent for use approval.\nClick the 'Pending' on line to see approvers and status.\nRemember, orders for min/max parts must be released to us by 1:00p.m\nat your designated hub to be filled for next day delivery.");
            }
            gd.setVisible(true);
          } else if ("approved".equalsIgnoreCase(tmpStatus)) {
            GenericDlg gd = new GenericDlg(cmis.getMain(), "Submit", true);
            gd.setMsg("Your order has been submitted for release.\nRemember, orders for min/max parts must be released to us by 1:00p.m\nat your designated hub to be filled for next day delivery.");
            gd.setVisible(true);
          } else {
            showMrAllocation(tmpHeader);
            /*
            MRAllocationReport dlg = new MRAllocationReport(cmis.getMain(), "MR Allocation Report", true, (Vector) tmpHeader.get("MR_ALLOCATION_INFO"),
                (String) tmpHeader.get("REQUESTOR"), (String) tmpHeader.get("FACILITY"));
            dlg.setGrandParent(cmis);
            dlg.setVisible(true);
            */
          }

          if (cmis.getAribaLogon()) {
            cmis.setAribaLogon(false);
            cmis.exit();
          }

          //1-24-03
          loadIt();
        } else {
          auditFailed(mylinenum1.intValue());
          ListSelectionModel lsm3 = chargeNumTable.getSelectionModel();
          lsm3.setSelectionInterval(ronum.intValue(), ronum.intValue());
          chargeNumTable.scrollRectToVisible(chargeNumTable.getCellRect(ronum.intValue(), 0, false));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of goSubmitRequest

  void showMrAllocation(Hashtable tmpHeader){
    try {
      String client = cmis.getResourceBundle().get("CLIENT_INITIALS").toString();
      if ("Boeing".equalsIgnoreCase(client)) {
       client = "ula";
      }
      String url = "https://www.tcmis.com/tcmIS/"+client.toLowerCase()+"/mrallocationreportmain.do?companyId="+cmis.companyId+
                   "&mrNumber="+tmpHeader.get("REQ_NUM").toString();
      new URLCall(url,cmis,false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  } //end of method


  //trong 3/8/00
  public void sendEvalForApproval() {
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj", cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION", new String("SEND_EVAL_FOR_APPROVAL"));
      query.put("USER_ID", cmis.getUserId().toString());
      query.put("REQ_NUM", (new Integer(rNum)).toString()); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null) {
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Boolean success = (Boolean) result.get("MESSAGE");
      if (success.booleanValue()) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Success", true);
        gd.setMsg("Your request is submitted for \nuse and financial approval - \nafterwords it will be sent directly to our buyer!");
        gd.setVisible(true);
        ClientHelpObjs.setEnabledContainer(this, true);
        endUserT.setEnabled(false);
        deptT.setEnabled(false);
        contactT.setEnabled(false);
        submitB.setEnabled(false);
        ccardB.setEnabled(false);
        ccardB.setVisible(false);
        delMRB.setEnabled(false);
        saveRejB.setEnabled(false);
        delLineB.setEnabled(false);
        delTypeC.setEnabled(false);
        dateT.setEnabled(false);
        dockC.setEnabled(false);
        delC.setEnabled(false);
        poC.setEnabled(false);
        refT.setEnabled(false);
        unitPriceT.setEnabled(false);
        currencyC.setEnabled(false);
        poC.setEditable(false);
        poT.setEnabled(false);
        returnCartB.setEnabled(false);
        scrapC.setEnabled(false);
        poQtyT.setEnabled(false);
        poUomT.setEnabled(false);
        return;
      } else {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "ERROR", true);
        gd.setMsg("Unable to send request to approver!\n Contact TCMIS personel if problem continue.");
        gd.setVisible(true);
        ClientHelpObjs.setEnabledContainer(this, true);
        setViewLevel();
        return;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  String getDelPointText(String dock, String delP, Hashtable itemHash) {
    //System.out.println("dock:"+dock+" delivery point:"+delP);
    Vector v = (Vector) itemHash.get("ALL_DELIVER_TO");
    for (int i = 0; i < v.size(); i++) {
      Hashtable h = (Hashtable) v.elementAt(i);
      if (h.get("DEL_POINT").toString().equalsIgnoreCase(delP) &&
          h.get("DOCK").toString().equalsIgnoreCase(dock)) {
        return h.get("DEL_POINT_DESC").toString();
      }
    }
    return "";
  }

  String getDelPointFromText(String dock, String delPointText, Hashtable itemHash) {
    Vector v = (Vector) itemHash.get("ALL_DELIVER_TO");
    for (int i = 0; i < v.size(); i++) {
      Hashtable h = (Hashtable) v.elementAt(i);
      if (h.get("DOCK").toString().equalsIgnoreCase(dock)) {
        String s = h.get("DEL_POINT_DESC").toString();
        if (s.equals(delPointText)) {
          return h.get("DEL_POINT").toString();
        }
      }
    }
    return "";
  }

  boolean showConfirmSubmitDlg(String nextStatus) {
    ConfirmDlg cd = new ConfirmDlg(cmis.getMain(), true);
    String s = nextStatus;
    String msg = "";
    String title = "";
    if (s.equalsIgnoreCase("approve")) {
      msg = "This request will be submitted for approval.  Do you want to continue?";
      title = "Send to Approver";
    } else if (s.equalsIgnoreCase("release")) {
      msg = "This request will be submitted for release.  Do you want to continue?";
      title = "Send to Releaser";
    } else {
      msg = "You are about to submit this Material request.  Do you want to continue?";
      title = "Submit Request";
    }
    cd.setMsg(msg);
    cd.setTitle(title);
    cd.setVisible(true);
    return cd.getConfirmationFlag();
  }

  void auditFailed(int i) {
    selectedLine = new Integer(i);
    setSelectedRow(selectedLine.intValue() - 1);
    buildDetailSection();
  }

  //trong 3/28
  void auditFailed2(int i) {
    selectedLine = new Integer(i);
    // setSelectedRow(selectedLine.intValue()-1);
    buildDetailSection();
  }

  boolean preSubmitAudits() {
    String msg = "";
    String tmpViewType = (String) headerInfo.get("VIEW_TYPE");
    while (true) {
      // do any header info audits first...
      // if there are any
      // now do line item audits
      for (int i = 0; i < lineItems.size(); i++) {
        Hashtable r = (Hashtable) lineItems.elementAt(i);

        // Deliver to audits
        if (r.get("DELIVER_TO") == null ||
            BothHelpObjs.isBlankString( (String) r.get("DELIVER_TO"))) {
          msg = "You must select a location to deliver the material.";
          auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
          break;
        }

        //audit qty, make sure that qty can't be less than zero
        try {
          int qty = Integer.parseInt(r.get("QTY").toString());
          if (tmpViewType.equalsIgnoreCase("APPROVE") ||
              tmpViewType.equalsIgnoreCase("RELEASE") ||
              tmpViewType.equalsIgnoreCase("USE_APPROVE")) {
            //do nothing
          } else {
            if (qty < 0) {
              msg = "You must enter a whole number greater than or equal to zero for quantity.";
              break;
            }
          }
          if (((Boolean)headerInfo.get("CAN_EDIT_MR_QTY")).booleanValue()) {
            //if users can change qty.  new qty >= total_qty_issued
            BigDecimal totalQtyIssued = (BigDecimal) r.get("TOTAL_QTY_ISSUED");
            if (qty < totalQtyIssued.intValue()) {
              msg = "You must enter a whole number greater than or equal to "+totalQtyIssued.intValue()+" (issued quantity) for quantity.";
              break;
            }
          }
        } catch (Exception e) {
          msg = "You must enter a whole number greater than or equal to zero for quantity.";
          break;
        }

        //audit delivery schedule else audit date
        if ("Schedule".equalsIgnoreCase( (String) r.get("DELIVERY_TYPE"))) {
          String as = auditSchedule( (Vector) r.get("DELIVERY_SCHEDULE"), new BigDecimal(r.get("QTY").toString()));
          if (!BothHelpObjs.isBlankString(as)) {
            msg = as;
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          }
        } else {
          // Making sure that the date entered is correct!!!
          String deliveryDate = (String) r.get("DEL_DATE");
          if (!BothHelpObjs.isDateFormatRight(deliveryDate)) {
            msg = " The date you entered is invalid.\n Please check and re-enter another date\n in the following format: mm/dd/yyyy (i.e. 09/21/2003).";
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          }
          //audit date if requestor is entering the data
          if ("REQUEST".equalsIgnoreCase(tmpViewType)) {
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            String cdate = new String( (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
            try {
              long n1 = d.parse(deliveryDate);
              long n2 = d.parse(cdate);
              if (cal.get(Calendar.HOUR) <= 13) {
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                  if (((Boolean)headerInfo.get("ALLOW_WEEKEND_DELIVERIES")).booleanValue()) {
                    cal.add(Calendar.DATE, 1);
                  }else {
                    cal.add(Calendar.DATE, 3);
                  }
                } else {
                  cal.add(Calendar.DATE, 1);
                }
                cdate = new String( (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
                if (d.parse(cdate) > d.parse(deliveryDate)) {
                  msg = " The date you entered is invalid.\n Please check and re-enter another date.\n Note:  MM requests must be released to your Hub\n before 1:00 p.m. to be delivered by the next business day.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              } else {
                if ( (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
                    (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
                  if (((Boolean)headerInfo.get("ALLOW_WEEKEND_DELIVERIES")).booleanValue()) {
                    cal.add(Calendar.DATE, 2);
                  }else {
                    cal.add(Calendar.DATE, 4);
                  }
                } else {
                  cal.add(Calendar.DATE, 2);
                }
                cdate = new String( (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE) + "/" + cal.get(Calendar.YEAR));
                if (d.parse(cdate) > d.parse(deliveryDate)) {
                  msg = " The date you entered is invalid.\n Please check and re-enter another date.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }
            } catch (Exception e) {
              msg = " The date you entered is invalid.\n Please check and re-enter another date\n in the following format: mm/dd/yyyy (i.e. 09/21/2003).";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
          } //end of if view type is REQUEST
        } //end of else not schedule

        //trong 7-19
        String type = (String) r.get("CHARGE_TYPE");
        String accSysId = (String) headerInfo.get("ACC_SYS_ID");
        String key1 = accSysId + type;
        Hashtable packH = (Hashtable) headerInfo.get("PACK");
        Hashtable accSysTypeH = (Hashtable) packH.get(key1);
        String validCharge1 = (String) accSysTypeH.get("VALID_1");
        String validCharge2 = (String) accSysTypeH.get("VALID_2");
        String pra = (String) accSysTypeH.get("PR_ACCOUNT_REQUIRED");
        String needPo = (String) accSysTypeH.get("PO_REQUIRED");
        String unitPriceRequired = (String) accSysTypeH.get("UNIT_PRICE_REQUIRED");
        String releaserRequired = (String) accSysTypeH.get("RELEASER_REQUIRED");
        String poSeqRequired = (String) accSysTypeH.get("PO_SEQ_REQUIRED");
        String customerRequisition = (String) accSysTypeH.get("CUSTOMER_REQUISITION");
        boolean containChargeNumber = false;
        // charge num audit
        Vector cn = new Vector();
        if (r.get("CHARGE_TYPE").toString().equalsIgnoreCase("i")) {
          cn = (Vector) r.get("CHARGE_NUM_INDIRECT");
        } else if (r.get("CHARGE_TYPE").toString().equalsIgnoreCase("d")) {
          cn = (Vector) r.get("CHARGE_NUM_DIRECT");
        }
        int cTotal = 0;
        //holder so I can check to see if user type in the same charge number
        Vector chargeNumberAuditV = new Vector(cn.size());
        for (int j = 0; j < cn.size(); j++) {
          Hashtable cHash = (Hashtable) cn.elementAt(j);
          String a1 = cHash.get("ACCT_NUM_1").toString();
          String a2;
          if (cHash.get("ACCT_NUM_2") == null) {
            a2 = "";
          } else {
            a2 = cHash.get("ACCT_NUM_2").toString();
          }
          String ps = cHash.get("PERCENTAGE").toString();
          if (BothHelpObjs.isBlankString(ps)) {
            continue;
          }
          //users can't enter the same charge number twice
          if (chargeNumberAuditV.contains(a1 + a2)) {
            msg = "You entered the same charge number twice.\nPlease check your charge numbers and re-submit.";
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          }
          chargeNumberAuditV.addElement(a1 + a2);

          //users must enter a percentage for charge number
          if (BothHelpObjs.isBlankString(a1) &&
              BothHelpObjs.isBlankString(a2)) {
            if (BothHelpObjs.isBlankString(ps)) {
              continue;
            } else {
              msg = "You must enter a charge number with each percentage.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
          }
          int p = 0;
          try {
            p = Integer.parseInt(ps);
          } catch (Exception e) {
            msg = "You must enter only numbers in the percentage column.";
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          }
          if (p < 0) {
            msg = "You cannot enter negative numbers in the percentage column.";
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          }
          cTotal = cTotal + p;
          //the flag below will tell me whether or not user enter some charge number
          if (a1.length() > 0 || a2.length() > 0) {
            containChargeNumber = true;
          }
        } //end of for loop (looping thru charge numbers

        //audit credit card info
        if ("c".equalsIgnoreCase(needPo)) {
          Hashtable creditCardInfo = (Hashtable) headerInfo.get("CREDIT_CARD_INFO");
          String creditCardNumber = (String) creditCardInfo.get("CREDIT_CARD_NUMBER");
          if (BothHelpObjs.isBlankString(creditCardNumber)) {
            msg = "Please enter credit card information for the given line item.";
            auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
            break;
          } else {
            break;
          }
        } //end of credit card audit

        //audit PO info
        if ("p".equalsIgnoreCase(needPo)) {
          if (tmpViewType.equalsIgnoreCase("RELEASE")) {
            if ("y".equalsIgnoreCase(releaserRequired)) {
              //releaser must enter a PO
              if (poT.isVisible()) {
                String tmpPO = (String) r.get("PO" + type);
                if (tmpPO.length() < 1) {
                  msg = "Please enter a PO (30 chars maximum) for the given line item.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                } else {
                  if (tmpPO.length() > 30) {
                    msg = "Your PO is more than 30 characters.\nPlease enter a PO (30 chars maximum) for the given line item.";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                }
              } else {
                if ("Select a PO".equalsIgnoreCase( (String) r.get("PO" + type))) {
                  msg = "Please select a PO for the given line item.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }
              //releaser must enter something for po line
              if ("U".equalsIgnoreCase(poSeqRequired)) {
                if (BothHelpObjs.isBlankString( (String) r.get("RELEASE_NUM"+type))) {
                  msg = "Please enter something for 'Line:'";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }
              //releaser must enter a unit price and pick a currencry
              if ("Required".equalsIgnoreCase(unitPriceRequired)) {
                //po unit price
                String tmpVal = (String) r.get("PO_UNIT_PRICE"+type);
                if (BothHelpObjs.isBlankString(tmpVal)) {
                  msg = "Please enter a PO Unit Price";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                try {
                  double d = Double.parseDouble(tmpVal);
                  if (d < 0.0) {
                    msg = "PO Unit Price must be greater or equal to '0' (zero)";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                } catch (Exception eup) {
                  msg = "PO Unit Price must be a real number. (Example 13.75)";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                //currency
                if ("Select One".equalsIgnoreCase( (String) r.get("PO_UNIT_PRICE_CURRENCY_ID"+type))) {
                  msg = "Please select a currency for PO Unit Price.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                //po qty
                tmpVal = (String) r.get("PO_QTY"+type);
                if (BothHelpObjs.isBlankString(tmpVal)) {
                  msg = "Please enter a PO Qty";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                try {
                  double d = Double.parseDouble(tmpVal);
                  if (d <= 0.0) {
                    msg = "PO Qty must be greater than '0' (zero)";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                } catch (Exception eup) {
                  msg = "PO Qty must be a real number. (Example 13.75)";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                //po uom
                tmpVal = (String) r.get("PO_UOM"+type);
                if (BothHelpObjs.isBlankString(tmpVal)) {
                  msg = "Please enter a PO UOM";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }else {
                  if (tmpVal.length() > 3) {
                    msg = "Your PO UOM is more than 3 characters.\nPlease enter a PO UOM (3 chars maximum) for the given line item.";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                }
              }
            } //end of if releaser_required = y
          } else {  //else very body else
            //if releaser is not required then whoever is looking at this must enter the following data
            if (!"y".equalsIgnoreCase(releaserRequired)) {
              //whoever looking at this must enter a PO
              if (poT.isVisible()) {
                String tmpPO = (String) r.get("PO" + type);
                if (tmpPO.length() < 1) {
                  msg = "Please enter a PO (30 chars maximum) for the given line item.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                } else {
                  if (tmpPO.length() > 30) {
                    msg = "Your PO is more than 30 characters.\nPlease enter a PO (30 chars maximum) for the given line item.";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                }
              } else {
                if ("Select a PO".equalsIgnoreCase( (String) r.get("PO" + type))) {
                  msg = "Please select a PO for the given line item.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }
              //whoever looking at this must enter something for po line
              if ("U".equalsIgnoreCase(poSeqRequired)) {
                if (BothHelpObjs.isBlankString( (String) r.get("RELEASE_NUM"+type))) {
                  msg = "Please enter something for 'Line:'";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }
              //whoever looking at this must enter a unit price and pick a currencry
              if ("Required".equalsIgnoreCase(unitPriceRequired)) {
                String tmpVal = (String) r.get("PO_UNIT_PRICE"+type);
                if (BothHelpObjs.isBlankString(tmpVal)) {
                  msg = "Please enter a PO Unit Price";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                try {
                  double d = Double.parseDouble(tmpVal);
                  if (d < 0.0) {
                    msg = "PO Unit Price must be greater or equal to '0' (zero)";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                } catch (Exception eup) {
                  msg = "PO Unit Price must be a real number. (Example 13.75)";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                //currency
                if ("Select One".equalsIgnoreCase( (String) r.get("PO_UNIT_PRICE_CURRENCY_ID"+type))) {
                  msg = "Please select a currency for PO Unit Price.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                //po qty
                tmpVal = (String) r.get("PO_QTY"+type);
                if (BothHelpObjs.isBlankString(tmpVal)) {
                  msg = "Please enter a PO Qty";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                try {
                  double d = Double.parseDouble(tmpVal);
                  if (d <= 0.0) {
                    msg = "PO Qty must be greater than '0' (zero)";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                } catch (Exception eup) {
                  msg = "PO Qty must be a real number. (Example 13.75)";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
                //po uom
                tmpVal = (String) r.get("PO_UOM"+type);
                if (BothHelpObjs.isBlankString(tmpVal)) {
                  msg = "Please enter a PO UOM";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }else {
                  if (tmpVal.length() > 3) {
                    msg = "Your PO UOM is more than 3 characters.\nPlease enter a PO UOM (3 chars maximum) for the given line item.";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                }
              }
            }else { //end of if releaser_required != y
              //releaser is required therefore the following is optional but the format must be correct
              //po 30 chars
              String tmpVal = "";
              if (poT.isVisible()) {
                tmpVal = (String) r.get("PO" + type);
                if (tmpVal.length() > 30) {
                  msg = "Your PO is more than 30 characters.\nPlease enter a PO (30 chars maximum) for the given line item.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }
              // po line 30 chars
              tmpVal = (String) r.get("RELEASE_NUM"+type);
              if ("U".equalsIgnoreCase(poSeqRequired)) {
                if (tmpVal.length() > 30) {
                  msg = "Your PO Line is more than 30 characters.\nPlease enter a PO Line (30 chars maximum) for the given line item.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }

              if ("Required".equalsIgnoreCase(unitPriceRequired)) {
                //po unit price
                tmpVal = (String) r.get("PO_UNIT_PRICE"+type);
                if (!BothHelpObjs.isBlankString(tmpVal)) {
                  try {
                    double d = Double.parseDouble(tmpVal);
                    if (d < 0.0) {
                      msg = "PO Unit Price must be greater or equal to '0' (zero)";
                      auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                      break;
                    }
                  } catch (Exception eup) {
                    msg = "PO Unit Price must be a real number. (Example 13.75)";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                }
                //po qty
                tmpVal = (String) r.get("PO_QTY"+type);
                if (!BothHelpObjs.isBlankString(tmpVal)) {
                  try {
                    double d = Double.parseDouble(tmpVal);
                    if (d <= 0.0) {
                      msg = "PO Qty must be greater than '0' (zero)";
                      auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                      break;
                    }
                  } catch (Exception eup) {
                    msg = "PO Qty must be a real number. (Example 13.75)";
                    auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                    break;
                  }
                }
                //po uom
                tmpVal = (String) r.get("PO_UOM"+type);
                if (tmpVal.length() > 3) {
                  msg = "Your PO UOM is more than 3 characters.\nPlease enter a PO UOM (3 chars maximum) for the given line item.";
                  auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                  break;
                }
              }
            } //end of releaser is required info in this section is optional, but has to be in the right format
          } //end of viewtype is other than releaser
          //if po_unit_price = Display then user cannot submit without catalog_price
          if ("Display".equalsIgnoreCase(unitPriceRequired)) {
            String tmpUnitPrice = (String) r.get("PO_UNIT_PRICE"+type);
            if (BothHelpObjs.isBlankString(tmpUnitPrice)) {
              msg = "We cannot process with an empty PO Unit Price.\nThis PO Price should have been prefilled."+
                    "\nPlease contact your CSR to have this fixed and/or\ndelete this line and Submit again.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
          } //end of unit price = Display
        } //end of po audit

        //customer requisition number
        if (tmpViewType.equalsIgnoreCase("REQUEST") || tmpViewType.equalsIgnoreCase("RELEASE")) {
          if ("Required".equalsIgnoreCase(customerRequisition)) {
            String tmpUnitPrice = (String) r.get("CUSTOMER_REQUISITION_NUMBER");
            if (BothHelpObjs.isBlankString(tmpUnitPrice)) {
              msg = "Please enter a Cust. Req.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
          }
        }

        if (BothHelpObjs.isBlankString(msg)) {
          if (!pra.equalsIgnoreCase("y")) {
            continue;
          }
          if (validCharge1.equalsIgnoreCase("y") || validCharge2.equalsIgnoreCase("y")) {
            if (cTotal != 100) {
              msg = "The sum of charge percentages must be 100.";
              auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
              break;
            }
          } else { //validation charge number not required
            if (containChargeNumber) {
              if (cTotal != 100) {
                msg = "The sum of charge percentages must be 100.";
                auditFailed(Integer.parseInt(r.get("LINE_NUM").toString()));
                break;
              }
            } //end of container charge number
          }
        } else {
          break;
        }
      }
      break;
    }
    if (msg.length() > 0) {
      GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
      gd.setMsg(msg);
      gd.setVisible(true);
      return false;
    }

    for (int i = 0; i < lineItems.size(); i++) {
      Hashtable h = (Hashtable) lineItems.elementAt(i);
      if ( ( (Boolean) h.get("CAN_RELAX_SHELF_LIFE")).booleanValue()) {
        RelaxShelfLifeDlg rsld = new RelaxShelfLifeDlg(cmis.getMain(), h, cmis);
        CenterComponent.centerCompOnScreen(rsld);
        rsld.setVisible(true);
        if (rsld.getProceedFlag().equalsIgnoreCase("cancel")) {
          return false;
        }
      }
    }
    return true; //no error go ahead to proceed
  }

//  String auditSchedule(Vector v,int q){
//    if(q != DeliverySchedule.getQtyForSchedule(v)){
//      return "The quantity ordered and the quantity scheduled must be the same.";
//    }
//    return "";
//  }

  String auditSchedule(Vector v, BigDecimal q) {
    if (q.compareTo(DeliverySchedule.getQtyForSchedule(v)) != 0) {
      return "The quantity ordered and the quantity scheduled must be the same.";
    }
    return "";
  }

  void forceStopEditing() {
    if (rliTable.isEditing()) {
      rliTable.getCellEditor().stopCellEditing();
    }

    //trong 7-19
    Hashtable h = getItemHash(selectedLine);
    String type = (String) h.get("CHARGE_TYPE");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable) packH.get(key1);
    String prAccountRequired = (String) accSysTypeH.get("PR_ACCOUNT_REQUIRED");

    if (prAccountRequired.equalsIgnoreCase("y")) { //trong 7-19
      if (chargeNumTable.isEditing()) {
        chargeNumTable.getCellEditor().stopCellEditing();
      }
    } //trong 7-19

  }

  void setViewLevelContactInfo(boolean val) {
    endUserT.setEnabled(val);
    deptT.setEnabled(val);
    contactT.setEnabled(val);
  }

  void setViewLevelDeliveryInfo(boolean val) {
    delTypeC.setEnabled(val);
    dateT.setEnabled(val);
    dockC.setEnabled(val);
    delC.setEnabled(val);
  }

  void setViewLevel() {
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    String status = headerInfo.get("STATUS_DESC").toString();
    String ecommerce = headerInfo.get("ECOMMERCE").toString();
    boolean t = true;
    boolean f = false;
    if (viewType.equalsIgnoreCase("REQUEST")) {
      setViewLevelContactInfo(t);
      if ("y".equalsIgnoreCase(ecommerce)) {
        if (cmis.getAribaLogon()) {
          submitB.setEnabled(t);
        } else {
          submitB.setEnabled(f);
        }
      } else {
        submitB.setEnabled(t);
      }
      delMRB.setEnabled(t);
      delLineB.setEnabled(t);
      saveRejB.setEnabled(t);
      returnCartB.setEnabled(t);
      setViewLevelDeliveryInfo(t);
      scrapC.setEnabled(t);
    } else if (viewType.equalsIgnoreCase("APPROVE")) {
      if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
        setViewLevelContactInfo(t);
        if ("y".equalsIgnoreCase(ecommerce)) {
          if (cmis.getAribaLogon()) {
            submitB.setEnabled(t);
          } else {
            submitB.setEnabled(f);
          }
        } else {
          submitB.setEnabled(t);
        }
        delMRB.setEnabled(f);
        delLineB.setEnabled(f);
        saveRejB.setEnabled(t);
        returnCartB.setEnabled(f);
        setViewLevelDeliveryInfo(f);
        scrapC.setEnabled(t);
      } else {
        setViewLevelContactInfo(f);
        submitB.setEnabled(t);
        delMRB.setEnabled(f);
        delLineB.setEnabled(f);
        saveRejB.setEnabled(t);
        returnCartB.setEnabled(f);
        setViewLevelDeliveryInfo(f);
        scrapC.setEnabled(f);
      }
    } else if (viewType.equalsIgnoreCase("RELEASE")) {
      if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
        setViewLevelContactInfo(t);
        if ("y".equalsIgnoreCase(ecommerce)) {
          if (cmis.getAribaLogon()) {
            submitB.setEnabled(t);
          } else {
            submitB.setEnabled(f);
          }
        } else {
          submitB.setEnabled(t);
        }
        delMRB.setEnabled(f);
        delLineB.setEnabled(f);
        saveRejB.setEnabled(t);
        returnCartB.setEnabled(f);
        setViewLevelDeliveryInfo(f);
        scrapC.setEnabled(t);
      } else {
        setViewLevelContactInfo(f);
        submitB.setEnabled(t);
        delMRB.setEnabled(f);
        delLineB.setEnabled(f);
        saveRejB.setEnabled(t);
        returnCartB.setEnabled(f);
        setViewLevelDeliveryInfo(f);
        scrapC.setEnabled(f);
      }
    } else if (viewType.equalsIgnoreCase("USE_APPROVE")) {
      if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
        setViewLevelContactInfo(t);
        if ("y".equalsIgnoreCase(ecommerce)) {
          if (cmis.getAribaLogon()) {
            submitB.setEnabled(t);
          } else {
            submitB.setEnabled(f);
          }
        } else {
          submitB.setEnabled(t);
        }
        delMRB.setEnabled(t);
        delLineB.setEnabled(t);
        saveRejB.setEnabled(t);
        returnCartB.setEnabled(t);
        setViewLevelDeliveryInfo(f);
        scrapC.setEnabled(t);
      } else {
        setViewLevelContactInfo(f);
        submitB.setEnabled(t);
        delMRB.setEnabled(t);
        delLineB.setEnabled(t);
        saveRejB.setEnabled(f);
        returnCartB.setEnabled(t);
        setViewLevelDeliveryInfo(f);
        scrapC.setEnabled(f);
      }
    } else {  //view type = view
      //if user is has edit MR permission
      if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
        setViewLevelContactInfo(t);
        if ("y".equalsIgnoreCase(ecommerce)) {
          if (cmis.getAribaLogon()) {
            submitB.setEnabled(t);
          } else {
            submitB.setEnabled(f);
          }
        } else {
          submitB.setEnabled(t);
        }
        delMRB.setEnabled(f);
        delLineB.setEnabled(f);
        saveRejB.setEnabled(f);
        returnCartB.setEnabled(f);
        setViewLevelDeliveryInfo(f);
        scrapC.setEnabled(f);
      }else {
        setViewLevelContactInfo(false);
        submitB.setEnabled(false);
        delMRB.setEnabled(false);
        delLineB.setEnabled(false);
        saveRejB.setEnabled(false);
        returnCartB.setEnabled(false);
        setViewLevelDeliveryInfo(false);
        scrapC.setEnabled(false);
      }
    }
    scheduleB.setEnabled(t);
    this.revalidate();
  }

  void setButtonText(String viewType) {
    delLineB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif", "DeleteItem"));
    if (viewType.equalsIgnoreCase("REQUEST")) {
      submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif", "Submit"));
      submitB.setText("Submit");
      saveRejB.setIcon(ResourceLoader.getImageIcon("images/button/save.gif", "Save"));
      saveRejB.setText("Save");
      delMRB.setIcon(ResourceLoader.getImageIcon("images/button/deleterequest.gif", "DeleteRequest"));
      delMRB.setText("Delete");
    } else if (viewType.equalsIgnoreCase("APPROVE")) {
      submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif", "Approve"));
      submitB.setText("Approve");
      saveRejB.setIcon(ResourceLoader.getImageIcon("images/button/open.gif", "Reject"));
      saveRejB.setText("Reject");
      delMRB.setIcon(ResourceLoader.getImageIcon("images/button/deleterequest.gif", "DeleteRequest"));
      delMRB.setText("Delete");
    } else if (viewType.equalsIgnoreCase("RELEASE")) {
      submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif", "Release"));
      submitB.setText("Release");
      saveRejB.setIcon(ResourceLoader.getImageIcon("images/button/open.gif", "Reject"));
      saveRejB.setText("Reject");
      delMRB.setIcon(ResourceLoader.getImageIcon("images/button/deleterequest.gif", "DeleteRequest"));
      delMRB.setText("Delete");
    } else if (viewType.equalsIgnoreCase("USE_APPROVE")) {
      submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif", "Approve"));
      submitB.setText("Approve");
      returnCartB.setText("Approve Line");
      delMRB.setIcon(ResourceLoader.getImageIcon("images/button/minus.gif", "Reject"));
      delMRB.setText("Reject");
      delLineB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif", "DeleteRequest"));
      delLineB.setText("Reject Line");
    } else {
      submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif", "Submit"));
      if (!BothHelpObjs.isBlankString(viewType)) {
        if ( ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue()) {
          submitB.setText("Update");
        } else {
          submitB.setText("Submit");
        }
      }else {
        submitB.setText("Submit");
      }
      saveRejB.setIcon(ResourceLoader.getImageIcon("images/button/save.gif", "Save"));
      saveRejB.setText("Save");
      delMRB.setIcon(ResourceLoader.getImageIcon("images/button/deleterequest.gif", "DeleteRequest"));
      delMRB.setText("Delete");
    }
  } //end of method

  void dockC_actionPerformed(ActionEvent e) {
    if (loadingDetail) {
      return;
    }
    setDeliverToCombo();
    resetDockDeliveryPointCopiedFlag();
  }

  void submitB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    if (viewType.equalsIgnoreCase("request")) {
      goSubmitRequest();
    } else if (viewType.equalsIgnoreCase("approve")) {
      goApproveRequest();
    } else if (viewType.equalsIgnoreCase("release")) {
      goReleaseRequest();
    } else if (viewType.equalsIgnoreCase("use_approve")) {
      goApproveUsage("All");
    } else if ("Update".equalsIgnoreCase(submitB.getText())) {
      goUpdateMR();
    }
  }

  // this is called from the ReqTable object
  protected void tableEditingStopped(int row, int col) {
    if (col == CRITICAL_COL) {
      this.repaint();
    }
    for (int i = 0; i < rliTable.getRowCount(); i++) {
      Hashtable h = getItemHash(new Integer(i + 1));
      String s = rliTable.getModel().getValueAt(i, QTY_COL).toString();
      if (!s.equals(h.get("QTY").toString())) {
        try {
          BigDecimal x = new BigDecimal(s);
          String tmpViewType = (String) headerInfo.get("VIEW_TYPE");
          if (tmpViewType.equalsIgnoreCase("APPROVE") ||
              tmpViewType.equalsIgnoreCase("RELEASE") ||
              tmpViewType.equalsIgnoreCase("USE_APPROVE")) {
            //do nothing
          } else {
            if (x.compareTo(new BigDecimal("0")) < 0) {
              GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
              gd.setMsg("You must enter a valid whole number greater than or equal to zero for quantity.");
              gd.setVisible(true);
              return;
            }
          }
        } catch (Exception e) {
          GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
          gd.setMsg("You must enter a valid whole number greater than or equal to zero for quantity.");
          gd.setVisible(true);
          return;
        }
        BigDecimal quantity = new BigDecimal(s);
        h.put("QTY", quantity.toString());
        if (i == row) {

          if (h.get("DEL_FREQ") == null ||
              h.get("DEL_FREQ").toString().equalsIgnoreCase("once")) {
            h.put("DELIVERY_QTY", quantity.toString());
          }
        }
        //if this is the selected row
        if (rliTable.getModel().getValueAt(i, 0).toString().equals(h.get("LINE_NUM").toString())) {
          if (!BothHelpObjs.isBlankString(h.get("UNIT_PRICE").toString())) {
            if (this.poQtyT.isVisible()) {
              try {
                BigDecimal unitPrice = new BigDecimal(h.get("UNIT_PRICE").toString());
                String type = (String) h.get("CHARGE_TYPE");
                if (BothHelpObjs.isBlankString(h.get("PO_QTY" + type).toString())) {
                  ePriceL.setText("");
                } else {
                  BigDecimal poQty = new BigDecimal(h.get("PO_QTY" + type).toString());
                  BigDecimal poUnitPrice = new BigDecimal(h.get("PO_UNIT_PRICE" + type).toString());
                  float t = (poQty.floatValue()/quantity.floatValue())*poUnitPrice.floatValue();
                  BigDecimal tmp = new BigDecimal(t);
                  if (!containFillUpBulk()) {
                    setLineExtPrice(quantity, tmp, (String) h.get("CURRENCY_ID"), (String) h.get("ITEM_DISPLAY_PKG_STYLE"));
                  }
                }
              } catch (Exception e) {
                e.printStackTrace();
                ePriceL.setText("");
              }
            }else {
              BigDecimal unitPrice = new BigDecimal(h.get("UNIT_PRICE").toString());
              setLineExtPrice(quantity, unitPrice, (String) h.get("CURRENCY_ID"), (String) h.get("ITEM_DISPLAY_PKG_STYLE"));
            }
          }else {
            ePriceL.setText("");
          }
        } //end of this is the selected row
      }
    }
    setExtendedPrice();
  }

  //4-25-01
  boolean containFillUpBulk() {
    boolean contFillUpBulk = false;
    int count = matDescTable.getModel().getRowCount();
    for (int j = 0; j < matDescTable.getModel().getRowCount(); j++) {
      String temp = (String) matDescTable.getModel().getValueAt(j, 1);
      if (temp.indexOf("fill up bulk") >= 0) {
        contFillUpBulk = true;
      }
    }
    return contFillUpBulk;
  }

  void setLineExtPrice(BigDecimal quantity, BigDecimal unitPrice, String currencyID, String uom) {
    BigDecimal totalPrice = quantity.multiply(unitPrice);
    if (currencyID.length() > 0) {
      if (uom.length() > 0) {
        ePriceL.setText(quantity + " @ " + formatPrice(unitPrice.doubleValue()) +
                        "/"+uom+" = " + formatPrice(totalPrice.doubleValue()) + " " + currencyID);
      }else {
        ePriceL.setText(quantity + " @ " + formatPrice(unitPrice.doubleValue()) +
                        "/Unit = " + formatPrice(totalPrice.doubleValue()) + " " + currencyID);
      }
    } else {
      if (uom.length() > 0) {
        ePriceL.setText(quantity + " @ " + formatPrice(unitPrice.doubleValue()) +
                        "/"+uom+" = " + formatPrice(totalPrice.doubleValue()));
      }else {
        ePriceL.setText(quantity + " @ " + formatPrice(unitPrice.doubleValue()) +
                        "/Unit = " + formatPrice(totalPrice.doubleValue()));
      }
    }

  } //end of method

  void saveRejB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    if (viewType.equalsIgnoreCase("request")) {
      goSaveMR();
    } else {
      goRejectMR();
    }
  }

  void delMRB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    if (viewType.equalsIgnoreCase("use_approve")) {
      goRejectUsage();
    } else {
      goDeleteMR();
    }
  }

  void delLineB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    String viewType = headerInfo.get("VIEW_TYPE").toString();
    if (viewType.equalsIgnoreCase("use_approve")) {
      goRejectLineUsage();
    } else {
      goDeleteLine();
    }
  }

  void tableClicked(MouseEvent e) {
    int col = ( (JTable) e.getSource()).columnAtPoint(new Point(e.getX(), e.getY()));
    int row = ( (JTable) e.getSource()).rowAtPoint(new Point(e.getX(), e.getY()));

    if (row < 0) {
      return; // this happens if there is only one line and the user
    }
    // makes a selection from the PO combo box

    String viewType = headerInfo.get("VIEW_TYPE").toString();

    if (rliTable.isEditing()) {
      rliTable.getCellEditor().stopCellEditing();
    }
    if (col == NOTE_COL && e.getClickCount() > 1) {
      if (viewType.equalsIgnoreCase("REQUEST") ||
          ( (viewType.equalsIgnoreCase("APPROVE") ||
             viewType.equalsIgnoreCase("RELEASE") ||
             viewType.equalsIgnoreCase("USE_APPROVE")) ||
           ( (Boolean) headerInfo.get("CAN_EDIT_MR")).booleanValue())) {
        EditDlg RjW = new EditDlg(cmis.getMain(), "Enter Notes", true, "Enter notes:");
        Hashtable h = getItemHash(selectedLine);
        String tmp = h.get("NOTES").toString();
        if (!BothHelpObjs.isBlankString(tmp)) {
          RjW.setText(tmp);
        }
        RjW.setVisible(true);
        if (!BothHelpObjs.isBlankString(RjW.getText())) {
          rliTable.getModel().setValueAt("+", row, col);
          h.put("NOTES", RjW.getText());
        } else {
          rliTable.getModel().setValueAt("", row, col);
          h.put("NOTES", "");
        }
        RjW.dispose();
      } else {
        if (rliTable.getModel().getValueAt(row, col).toString().indexOf("+") > -1) {
          Hashtable h = getItemHash(selectedLine);
          String tmp = h.get("NOTES").toString();
          if (!BothHelpObjs.isBlankString(tmp)) {
            DisplayTextDlg gd = new DisplayTextDlg(cmis.getMain(), "Notes");
            gd.setMsg(tmp);
            gd.setVisible(true);
          }
        }
      }
    }
    //user click on status column
    if (col == USE_APPROVAL_STATUS_COL && e.getClickCount() >= 1 && !useApprovalDlgOpen) {
      String status = rliTable.getModel().getValueAt(row, col).toString();
      String title;
      if (status.equalsIgnoreCase("approved")) {
        title = "Line Approved By:";
        UseApprovalApprovedRejectedDlg us = new UseApprovalApprovedRejectedDlg(this, cmis.getMain(), title, new Integer(rNum).toString(), rliTable.getModel().getValueAt(row, this.LINE_COL).toString(), status);
        us.setParent(cmis);
        us.loadIt();
        CenterComponent.centerCompOnScreen(us);
        us.setVisible(true);
      } else if (status.equalsIgnoreCase("rejected")) {
        title = "Line Rejected By:";
        UseApprovalApprovedRejectedDlg us = new UseApprovalApprovedRejectedDlg(this, cmis.getMain(), title, new Integer(rNum).toString(), rliTable.getModel().getValueAt(row, this.LINE_COL).toString(), status);
        us.setParent(cmis);
        us.loadIt();
        CenterComponent.centerCompOnScreen(us);
        us.setVisible(true);
      } else {
        title = "Line Pending the following quantity approver(s):";
        //UseApprovalDlg us = new UseApprovalDlg(this,cmis.getMain(),title,new Integer(rNum).toString(),rliTable.getModel().getValueAt(row,this.PART_COL).toString(),status);
        UseApprovalDlg us = new UseApprovalDlg(this, cmis.getMain(), title, new Integer(rNum).toString(), rliTable.getModel().getValueAt(row, this.LINE_COL).toString(), status);
        us.setParent(cmis);
        us.loadIt();
        CenterComponent.centerCompOnScreen(us);
        us.setVisible(true);
      }
      useApprovalDlgOpen = true;
    } //end of if

    //user click on status column
    if (col == LIST_APPROVAL_STATUS_COL && e.getClickCount() >= 1 && !listApprovalDlgOpen) {
      String status = rliTable.getModel().getValueAt(row, col).toString();
      String title;
      title = "Approver(s):";
      ListApprovalDlg us = new ListApprovalDlg(this, cmis.getMain(), title, new Integer(rNum).toString(), rliTable.getModel().getValueAt(row, this.LINE_COL).toString(), status);
      us.setParent(cmis);
      us.loadIt();
      CenterComponent.centerCompOnScreen(us);
      us.setVisible(true);
      listApprovalDlgOpen = true;
    } //end of if


    //enable "Approve Line"
    if ("Approve Line".equals(returnCartB.getText())) {
      returnCartB.setEnabled(true);
    }

  } //end of method

  void listSelectionChanged() {
    int col = rliTable.getSelectedColumn();
    int row = rliTable.getSelectedRow();
    int clickedLineNum = Integer.parseInt(rliTable.getModel().getValueAt(row, 0).toString());

    //trong 7-19
    Hashtable h = getItemHash(selectedLine);
    String type = (String) h.get("CHARGE_TYPE");
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable) packH.get(key1);
    String prAccountRequired = (String) accSysTypeH.get("PR_ACCOUNT_REQUIRED");

    if (selectedLine.intValue() == clickedLineNum) {
    } else {
      if (prAccountRequired.equalsIgnoreCase("y")) { //trong 7-19
        if (chargeNumTable.isEditing()) {
          chargeNumTable.getCellEditor().stopCellEditing();
        }
      } //trong 7-19
      saveCurrentDetail();
      selectedLine = new Integer(clickedLineNum);
      buildDetailSection();
      ClientHelpObjs.makeDefaultColors(this);
      if (scrapC.isSelected()) {
        scrapC.setForeground(Color.red);
      } else {
        scrapC.setForeground(Color.black);
      }
    }
    setExtendedPrice();
    //
    setRequestLineTableEditableFlag();
  }

  void chargeTypeChanged() {
    //first save po info
    saveCurrentPoInfo();

    String type = "";
    if (directRB.isSelected()) {
      type = "d";
    } else {
      type = "i";
    }
    Hashtable h = getItemHash(selectedLine);
    String accSysId = (String) headerInfo.get("ACC_SYS_ID");
    String key1 = accSysId + type;
    Hashtable packH = (Hashtable) headerInfo.get("PACK");
    Hashtable accSysTypeH = (Hashtable) packH.get(key1);
    String prAccountRequired = (String) accSysTypeH.get("PR_ACCOUNT_REQUIRED");
    String poRequired = (String) accSysTypeH.get("PO_REQUIRED");
    String unitPriceRequired = (String) accSysTypeH.get("UNIT_PRICE_REQUIRED");
    String releaserRequired = (String) accSysTypeH.get("RELEASER_REQUIRED");
    String poSeqRequired = (String) accSysTypeH.get("PO_SEQ_REQUIRED");
    h.put("UNIT_PRICE_STATUS"+type,unitPriceRequired);
    if (prAccountRequired.equalsIgnoreCase("y")) {
      buildChargeNumTable();
    } else {
      chargeP.setVisible(false);
    }
    if ("p".equalsIgnoreCase(poRequired)) {
      buildPoDetail(h,type,poSeqRequired,unitPriceRequired);
    } else if ("c".equalsIgnoreCase(poRequired)) {
      ccardB.setEnabled(true);
      ccardB.setVisible(true);
      poC.setVisible(false); //trong 4/9
      poL.setVisible(false);
      refT.setVisible(false);
      refL.setVisible(false);
      unitPriceL.setVisible(false);
      unitPriceT.setVisible(false);
      currencyC.setVisible(false);
      poT.setVisible(false);
      poQtyL.setVisible(false);
      poQtyT.setVisible(false);
      poUomL.setVisible(false);
      poUomT.setVisible(false);
    } else {
      ccardB.setEnabled(false);
      ccardB.setVisible(false);
      poC.setVisible(false); //trong 4/9
      poL.setVisible(false);
      refT.setVisible(false);
      refL.setVisible(false);
      unitPriceL.setVisible(false);
      unitPriceT.setVisible(false);
      currencyC.setVisible(false);
      poT.setVisible(false);
      poQtyL.setVisible(false);
      poQtyT.setVisible(false);
      poUomL.setVisible(false);
      poUomT.setVisible(false);
    }

    //reset charge number copied flag because user try to change what was copied
    resetChargeNumberCopiedFlag();
    //
    setRequestLineTableEditableFlag();
  } //end of method

  void saveCurrentPoInfo() {
    Hashtable h = getItemHash(selectedLine);
    //charge type that I want to save it the unselected type
    String type = "d";
    if (directRB.isVisible()) {
      if (directRB.isSelected()) {
        type = "i";
      }else {
        type = "d";
      }
    }
    if (poT.isVisible()) {
      h.put("PO"+type,poT.getText().trim());
    }
    if (refT.isVisible()) {
      h.put("RELEASE_NUM"+type,refT.getText().trim());
    }
    if (unitPriceT.isVisible()) {
      h.put("PO_UNIT_PRICE"+type,unitPriceT.getText().trim());
    }
    if (currencyC.isVisible()) {
      h.put("PO_UNIT_PRICE_CURRENCY_ID"+type,currencyC.getSelectedItem().toString());
    }
    if (poQtyT.isVisible()) {
      h.put("PO_QTY"+type,poQtyT.getText().trim());
    }
    if (poUomT.isVisible()) {
      h.put("PO_UOM"+type,poUomT.getText().trim());
    }

  } //end of method

  void currencyC_itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == e.SELECTED) {
      unitPriceT_actionPreformed();
    }
  } //end of method


  void indirectRB_actionPerformed(ActionEvent e) {
    chargeTypeChanged();
  }

  void directRB_actionPerformed(ActionEvent e) {
    chargeTypeChanged();
  }

  void undoB_actionPerformed(ActionEvent e) {
    ConfirmNewDlg cdlg = new ConfirmNewDlg(this.cmis.getMain(), "Confirmation", true);
    cdlg.setMsg("Undo unedited copied line(s).");
    cdlg.setVisible(true);
    if (!cdlg.yesFlag) {
      System.out.println("------- cancel");
    } else {
      String msg = undoUneditLines();
      GenericDlg.showMessage(msg);
    }
    if (cdlg != null) {
      cdlg = null;
    }
  } //end of method

  void copyB_actionPerformed(ActionEvent e) {
    ConfirmNewDlg cdlg = new ConfirmNewDlg(this.cmis.getMain(), "Confirmation", true);
    cdlg.setMsg("Copy Charge #, Scrap/Obsolete flag, Dock and Deliver To to blank line(s).");
    cdlg.setVisible(true);
    if (!cdlg.yesFlag) {
      System.out.println("------- cancel");
    } else {
      String msg = applyDataToOtherLines();
      GenericDlg.showMessage(msg);
    }
    if (cdlg != null) {
      cdlg = null;
    }
  } //end of method

  String undoUneditLines() {
    String result = "Successfully undo unedited copied line(s)";
    if (rliTable.getRowCount() > 1) {
      //stop editing line and charge table
      if (rliTable.isEditing()) {
        rliTable.getCellEditor().stopCellEditing();
      }
      if (chargeNumTable.isEditing()) {
        chargeNumTable.getCellEditor().stopCellEditing();
      }
      //save current data incase user try to override what was copied
      Hashtable tmpLineH = getItemHash(selectedLine);
      if (tmpLineH.containsKey("CHARGE_NUMBER_COPIED") || tmpLineH.containsKey("DOCK_DELIVERY_POINT_COPIED")) {
        boolean chargeNumCopied = false;
        boolean dockDelCopied = false;
        if (tmpLineH.containsKey("CHARGE_NUMBER_COPIED")) {
          chargeNumCopied = ( (Boolean) tmpLineH.get("CHARGE_NUMBER_COPIED")).booleanValue();
        }
        if (tmpLineH.containsKey("DOCK_DELIVERY_POINT_COPIED")) {
          dockDelCopied = ( (Boolean) tmpLineH.get("DOCK_DELIVERY_POINT_COPIED")).booleanValue();
        }
        if (chargeNumCopied && dockDelCopied) {
          //DON'T have to save current data coz it will get reset anyway
          System.out.println("--------- don't save current data");
        } else {
          //otherwise, save current data coz user try to overwrite copied data
          System.out.println("--------- saving current data");
          saveCurrentDetail();
        }
      }

      boolean undoApplied = false;
      for (int i = 0; i < rliTable.getRowCount(); i++) {
        Hashtable h = getItemHash(new Integer(i + 1));
        //first charge number info
        if (h.containsKey("CHARGE_NUMBER_COPIED")) {
          Boolean tmpCopied = (Boolean) h.get("CHARGE_NUMBER_COPIED");
          if (tmpCopied.booleanValue()) {
            result = undoCopiedChargeNumber(h, i + 1);
            if (!"OK".equalsIgnoreCase(result)) {
              break;
            }
            result = "Successfully undo unedited copied line(s)";
            undoApplied = true;
          }
        } // end of if line charge number is copied
        //dock and delivery point
        if (h.containsKey("DOCK_DELIVERY_POINT_COPIED")) {
          Boolean tmpCopied = (Boolean) h.get("DOCK_DELIVERY_POINT_COPIED");
          if (tmpCopied.booleanValue()) {
            //next copy dock and delivery point
            result = undoCopiedDockDeliveryPoint(h, i + 1);
            if (!"OK".equalsIgnoreCase(result)) {
              break;
            }
            result = "Successfully undo unedited copied line(s)";
            undoApplied = true;
          }
        } //end of if line dock and delivery point is copied
        //scrap flag
        if (h.containsKey("SCRAP_FLAG_COPIED")) {
          Boolean tmpCopied = (Boolean) h.get("SCRAP_FLAG_COPIED");
          if (tmpCopied.booleanValue()) {
            //next copy scrap flag
            result = undoCopiedScrapFlag(h, i + 1);
            if (!"OK".equalsIgnoreCase(result)) {
              break;
            }
            result = "Successfully undo unedited copied line(s)";
            undoApplied = true;
          }
        } //end of if line scrap flag is copied
      } //loop thru each line
      if (undoApplied) {
        buildDetailSection();
      }
    } //end of if
    return result;
  } //end of method

  String undoCopiedDockDeliveryPoint(Hashtable h, int lineNum) {
    String result = "OK";
    if (h.containsKey("DOCK_FOR_UNDO")) {
      System.out.println("----- undo copied dock and delivery point info for undo function");
      h.put("DOCK", (String) h.get("DOCK_FOR_UNDO"));
      h.put("DELIVER_TO", (String) h.get("DELIVER_TO_FOR_UNDO"));
      h.put("DELIVER_TO_NAME", (String) h.get("DELIVER_TO_NAME_FOR_UNDO"));
      //remove backup
      h.remove("DOCK_FOR_UNDO");
      h.remove("DELIVER_TO_FOR_UNDO");
      h.remove("DELIVER_TO_NAME_FOR_UNDO");
      //reset copied flag
      h.put("DOCK_DELIVERY_POINT_COPIED", new Boolean(false));
    }
    return result;
  } //end of method

  String copyDockDeliveryPoint(Hashtable h, int lineNum) {
    String result = "OK";
    String dTo = delC.getSelectedItem().toString();
    //first check to make sure the selected dock and delivery point is valid for line item
    //if getDelPointFromText return an empty string then dock and delivery point is not valid for line item
    if (dTo.equalsIgnoreCase(NEED_DEL_TO)) {
      result = "Please select a 'Delivery to' then click on 'Copy' again.";
      return result;
    }
    String tmpDeliveryTo = getDelPointFromText(dockC.getSelectedItem().toString(), delC.getSelectedItem().toString(), h);
    if (BothHelpObjs.isBlankString(tmpDeliveryTo)) {
      //DON'T copy dock and delivery point to this line
      System.out.println("------ don't copy dock and delivery point to line: " + lineNum);
      return "OK";
    }
    //don't override if delivery to already set
    if (!BothHelpObjs.isBlankString( (String) h.get("DELIVER_TO"))) {
      if (!NEED_DEL_TO.equalsIgnoreCase( (String) h.get("DELIVER_TO"))) {
        System.out.println("------- don't override delivery to for line: " + lineNum);
        return "OK";
      }
    }
    //determine whether to create a copy of charge number for the undo function
    //if line does not contain charge numbers for undo that make copy
    if (!h.containsKey("DOCK_FOR_UNDO")) {
      System.out.println("----- copying dock and delivery point info for undo function to line: " + lineNum);
      h.put("DOCK_FOR_UNDO", (String) h.get("DOCK"));
      h.put("DELIVER_TO_FOR_UNDO", (String) h.get("DELIVER_TO"));
      h.put("DELIVER_TO_NAME_FOR_UNDO", (String) h.get("DELIVER_TO_NAME"));
    }
    //copy dock and delivery point info so screen to line
    //dock
    h.put("DOCK", dockC.getSelectedItem().toString());
    //deliverTo
    if (dTo.equalsIgnoreCase(NEED_DEL_TO)) {
      h.put("DELIVER_TO", "");
      h.put("DELIVER_TO_NAME", "");
    } else {
      h.put("DELIVER_TO", tmpDeliveryTo);
      h.put("DELIVER_TO_NAME", delC.getSelectedItem().toString());
    }
    h.put("DOCK_DELIVERY_POINT_COPIED", new Boolean(true));
    return result;
  } //end of method

  void delC_itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == e.SELECTED) {
      resetDockDeliveryPointCopiedFlag();
    }
  } //end of method

  void resetDockDeliveryPointCopiedFlag() {
    System.out.println("---------- user charge dock or delivery point reset copied flag");
    Hashtable h = getItemHash(selectedLine);
    if (h.containsKey("DOCK_DELIVERY_POINT_COPIED")) {
      if (h.containsKey("DOCK_FOR_UNDO")) {
        //remove backup
        h.remove("DOCK_FOR_UNDO");
        h.remove("DELIVER_TO_FOR_UNDO");
        h.remove("DELIVER_TO_NAME_FOR_UNDO");
      }
      //reset copied flag
      h.put("DOCK_DELIVERY_POINT_COPIED", new Boolean(false));
    }
  } //end of method

  String copyScrapFlag(Hashtable h, int lineNum) {
    String result = "OK";
    //determine whether to create a copy of scrap flag for the undo function
    //if line does not contain scrap flag for undo that make copy
    if (!h.containsKey("SCRAP_FLAG_FOR_UNDO")) {
      System.out.println("----- copying scrap flag for undo function to line: " + lineNum);
      h.put("SCRAP_FLAG_FOR_UNDO", (Boolean) h.get("SCRAP_FLAG"));
    }
    //copy scrap flag from screen to line
    h.put("SCRAP_FLAG", new Boolean(scrapC.isSelected()));
    h.put("SCRAP_FLAG_COPIED", new Boolean(true));
    return result;
  } //end of method

  void resetScrapCopiedFlag() {
    System.out.println("---------- user change scrap reset copied flag");
    Hashtable h = getItemHash(selectedLine);
    if (h.containsKey("SCRAP_FLAG_COPIED")) {
      if (h.containsKey("SCRAP_FLAG_FOR_UNDO")) {
        //remove backup
        h.remove("SCRAP_FLAG_FOR_UNDO");
      }
      //reset copied flag
      h.put("SCRAP_FLAG_COPIED", new Boolean(false));
    }
  } //end of method

  String undoCopiedScrapFlag(Hashtable h, int lineNum) {
    String result = "OK";
    if (h.containsKey("SCRAP_FLAG_FOR_UNDO")) {
      System.out.println("----- undo copied scrap flag for undo function");
      h.put("SCRAP_FLAG", (Boolean) h.get("SCRAP_FLAG_FOR_UNDO"));
      //remove backup
      h.remove("SCRAP_FLAG_FOR_UNDO");
      //reset copied flag
      h.put("SCRAP_FLAG_COPIED", new Boolean(false));
    }
    return result;
  } //end of method

  String undoCopiedChargeNumber(Hashtable h, int lineNum) {
    String result = "OK";
    if (h.containsKey("CHARGE_TYPE_FOR_UNDO")) {
      System.out.println("----- undo copied charge info for undo function");
      h.put("CHARGE_TYPE", (String) h.get("CHARGE_TYPE_FOR_UNDO"));
      h.put("CHARGE_NUM", (Vector) h.get("CHARGE_NUM_FOR_UNDO"));
      h.put("CHARGE_NUM_DIRECT", (Vector) h.get("CHARGE_NUM_DIRECT_FOR_UNDO"));
      h.put("CHARGE_NUM_INDIRECT", (Vector) h.get("CHARGE_NUM_INDIRECT_FOR_UNDO"));
      //remove backup
      h.remove("CHARGE_TYPE_FOR_UNDO");
      h.remove("CHARGE_NUM_FOR_UNDO");
      h.remove("CHARGE_NUM_DIRECT_FOR_UNDO");
      h.remove("CHARGE_NUM_INDIRECT_FOR_UNDO");
      //reset copied flag
      h.put("CHARGE_NUMBER_COPIED", new Boolean(false));
    }
    return result;
  } //end of method

  String applyDataToOtherLines() {
    String result = "Data copied to other line(s)";
    int currentSelectedLine = rliTable.getSelectedRow();
    if (rliTable.getRowCount() > 1) {
      for (int i = 0; i < rliTable.getRowCount(); i++) {
        if (i == currentSelectedLine) {
          continue;
        }
        Hashtable h = getItemHash(new Integer(i + 1));
        //first copy charge number info
        result = copyChargeNumber(h, i + 1);
        if (!"OK".equalsIgnoreCase(result)) {
          break;
        }
        //next copy dock and delivery point
        result = copyDockDeliveryPoint(h, i + 1);
        if (!"OK".equalsIgnoreCase(result)) {
          break;
        }
        //also copy scrap flag
        result = copyScrapFlag(h, i + 1);
        if (!"OK".equalsIgnoreCase(result)) {
          break;
        }
        //display message to users
        result = "Data copied to other line(s)";
      }
    } //end of if

    return result;
  } //end of method

  void chargeTableClicked(MouseEvent e) {
    resetChargeNumberCopiedFlag();
  } //end of method

  void resetChargeNumberCopiedFlag() {
    Hashtable h = getItemHash(selectedLine);
    if (h.containsKey("CHARGE_NUMBER_COPIED")) {
      //remove backup
      h.remove("CHARGE_TYPE_FOR_UNDO");
      h.remove("CHARGE_NUM_FOR_UNDO");
      h.remove("CHARGE_NUM_DIRECT_FOR_UNDO");
      h.remove("CHARGE_NUM_INDIRECT_FOR_UNDO");
      //reset copied flag
      h.put("CHARGE_NUMBER_COPIED", new Boolean(false));
    }
  } //end of method

  boolean chargeTableContainsChargeNumber() {
    boolean result = false;
    int percent = 0;
    int numCols = chargeNumTable.getColumnCount();
    for (int i = 0; i < chargeNumTable.getRowCount(); i++) {
      Hashtable f = new Hashtable();
      int mc = 0;
      String charge1 = "";
      String charge2 = "";
      String per = "";
      if (numCols == 2) {
        charge1 = chargeNumTable.getModel().getValueAt(i, mc++).toString();
        per = chargeNumTable.getModel().getValueAt(i, mc++).toString();
        if (!BothHelpObjs.isBlankString(per)) {
          //if the is something is in percent then there must be something for charge number
          if (BothHelpObjs.isBlankString(charge1)) {
            break;
          }
          try {
            percent += Integer.parseInt(per);
          } catch (Exception e) {
            break;
          }
        }
      } else {
        charge1 = chargeNumTable.getModel().getValueAt(i, mc++).toString();
        charge2 = chargeNumTable.getModel().getValueAt(i, mc++).toString();
        per = chargeNumTable.getModel().getValueAt(i, mc++).toString();
        if (!BothHelpObjs.isBlankString(per)) {
          //if the is something is in percent then there must be something for charge number
          if (BothHelpObjs.isBlankString(charge1) || BothHelpObjs.isBlankString(charge2)) {
            break;
          }
          try {
            percent += Integer.parseInt(per);
          } catch (Exception e) {
            break;
          }
        }
      } //end of charge number has three columns
    } //end of looping thru charge table
    //make sure the percent is added up to equal to 100
    if (percent == 100) {
      result = true;
    } else {
      result = false;
    }
    return result;
  } //end of method

  //this method return true if the something in percentage column and it does not has to add up to 100
  boolean chargeVectorContainsChargeNumber(Hashtable h) {
    boolean result = false;
    Vector tmpChargeNumberV = new Vector(0);
    if (directRB.isVisible()) {
      if (directRB.isSelected()) {
        tmpChargeNumberV = (Vector) h.get("CHARGE_NUM_DIRECT");
      } else {
        tmpChargeNumberV = (Vector) h.get("CHARGE_NUM_INDIRECT");
      }
    } else {
      tmpChargeNumberV = (Vector) h.get("CHARGE_NUM_DIRECT");
    }
    for (int i = 0; i < tmpChargeNumberV.size(); i++) {
      Hashtable f = (Hashtable) tmpChargeNumberV.elementAt(i);
      String per = (String) f.get("PERCENTAGE");
      if (!BothHelpObjs.isBlankString(per)) {
        result = true;
        break;
      }
    } //end of looping thru charge vector
    return result;
  } //end of method

  String copyChargeNumber(Hashtable h, int lineNum) {
    String result = "OK";
    //stop editing charge table
    if (chargeNumTable.isEditing()) {
      chargeNumTable.getCellEditor().stopCellEditing();
    }
    //skip line if line uses directed charge
    if (h.containsKey("LINE_USES_DIRECTED_CHARGE")) {
      if ( ( (Boolean) h.get("LINE_USES_DIRECTED_CHARGE")).booleanValue()) {
        System.out.println("------ skipping line: " + lineNum + " due to directed charge");
        result = "OK";
        return result;
      }
    }
    //skip if user entered something for charge number
    if (chargeVectorContainsChargeNumber(h)) {
      System.out.println("------ skipping line: " + lineNum + " because user entered something for charge");
      result = "OK";
      return result;
    }
    //next check to see if user enter charge number below copy
    if (!chargeNumTable.isVisible()) {
      System.out.println("----- charge number not required for line: " + lineNum);
      result = "OK";
      return result;
    }
    //otherwise
    if (!chargeTableContainsChargeNumber()) {
      result = "Please enter charge number(s) and percent(s) then click on 'Copy' again.";
      return result;
    }
    //determine whether to create a copy of charge number for the undo function
    //if line does not contain charge numbers for undo that make copy
    if (!h.containsKey("CHARGE_TYPE_FOR_UNDO")) {
      System.out.println("----- copying charge info for undo function to line: " + lineNum);
      h.put("CHARGE_TYPE_FOR_UNDO", (String) h.get("CHARGE_TYPE"));
      h.put("CHARGE_NUM_FOR_UNDO", (Vector) h.get("CHARGE_NUM"));
      h.put("CHARGE_NUM_DIRECT_FOR_UNDO", (Vector) h.get("CHARGE_NUM_DIRECT"));
      h.put("CHARGE_NUM_INDIRECT_FOR_UNDO", (Vector) h.get("CHARGE_NUM_INDIRECT"));
    }
    //copy charge number info so screen to line
    String tmpCT = "d";
    if (directRB.isVisible()) {
      if (directRB.isSelected()) {
        tmpCT = "d";
        h.put("CHARGE_NUM_DIRECT", getChargeNumberFromTable());
      } else {
        tmpCT = "i";
        h.put("CHARGE_NUM_INDIRECT", getChargeNumberFromTable());
      }
    } else {
      h.put("CHARGE_NUM_DIRECT", getChargeNumberFromTable());
    }
    h.put("CHARGE_TYPE", tmpCT);
    h.put("CHARGE_NUMBER_COPIED", new Boolean(true));
    return result;
  } //end of method

  Vector getChargeNumberFromTable() {
    Vector v = new Vector();
    int numCols = chargeNumTable.getColumnCount();
    for (int i = 0; i < chargeNumTable.getRowCount(); i++) {
      Hashtable f = new Hashtable();
      int mc = 0;
      f.put("ACCT_NUM_1", chargeNumTable.getModel().getValueAt(i, mc++).toString());
      if (numCols == 3) {
        f.put("ACCT_NUM_2", chargeNumTable.getModel().getValueAt(i, mc++).toString());
      }
      f.put("PERCENTAGE", chargeNumTable.getModel().getValueAt(i, mc++).toString());
      v.addElement(f);
    }
    return v;
  } //end of method

  void delTypeC_actionPerformed(ActionEvent e) {
    if (updatingDelTypeC) {
      return;
    }
    setDeliveryPanel();
  }

  void scheduleB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    saveCurrentDetail();
    Hashtable h = getItemHash(selectedLine);

    //1-23-03 if MM or newMM or exitMM or virtualMM then prompt message below
    if ("request".equalsIgnoreCase( (String) headerInfo.get("VIEW_TYPE"))) {
      if (BothHelpObjs.isMinMax( (String) h.get("ITEM_TYPE"))) {
        ScheduleMMDlg smmd = new ScheduleMMDlg(cmis.getMain(), h, cmis);
        CenterComponent.centerCompOnScreen(smmd);
        smmd.setVisible(true);
        if (smmd.getProceedFlag().equalsIgnoreCase("cancel")) {
          return;
        }
      }
    }

    boolean submitted = headerInfo.get("STATUS").toString().equalsIgnoreCase("posubmit");
    DeliverySchedule ds = new DeliverySchedule(cmis,
                                               delInfo,
                                               new BigDecimal(h.get("QTY").toString()),
                                               headerInfo.get("REQ_NUM").toString(),
                                               h.get("PART_NUM").toString(),
                                               h.get("ITEM_DESC").toString(),
                                               headerInfo.get("STATUS_DESC").toString(),
                                               h.get("ITEM_TYPE").toString(),
                                               h.get("ITEM_NUM").toString(),
                                               now,
                                               submitted,
                                               h.get("LINE_NUM").toString());
    CenterComponent.centerCompOnScreen(ds);
    boolean viewonly = false;
    if (cmis.getUserId().intValue() == ( (Integer) headerInfo.get("REQUESTOR_ID")).intValue() ||
        cmis.getGroupMembership().isSuperUser()) {
      //do nothing
    } else {
      viewonly = true;
      ds.setViewOnly(true);
    }
    ds.setVisible(true);
    if (viewonly || ds.isCanceled()) {
      return;
    }
    if (ds.qtyChanged()) {
      rliTable.getModel().setValueAt(String.valueOf(ds.getQty()), rliTable.getSelectedRow(), QTY_COL);
      if (!BothHelpObjs.isBlankString(h.get("UNIT_PRICE").toString())) {
        if (this.poQtyT.isVisible()) {
          try {
            BigDecimal unitPrice = new BigDecimal(h.get("UNIT_PRICE").toString());
            BigDecimal quantity = ds.getQty();
            String type = (String) h.get("CHARGE_TYPE");
            if (BothHelpObjs.isBlankString(h.get("PO_QTY" + type).toString())) {
              ePriceL.setText("");
            } else {
              BigDecimal poQty = new BigDecimal(h.get("PO_QTY" + type).toString());
              BigDecimal poUnitPrice = new BigDecimal(h.get("PO_UNIT_PRICE" + type).toString());
              float t = (poQty.floatValue()/quantity.floatValue())*poUnitPrice.floatValue();
              BigDecimal tmp = new BigDecimal(t);
              setLineExtPrice(quantity, tmp, (String) h.get("CURRENCY_ID"), (String) h.get("ITEM_DISPLAY_PKG_STYLE"));
            }
          } catch (Exception ee) {
            ePriceL.setText("");
          }
        }else {
          BigDecimal unitPrice = new BigDecimal(h.get("UNIT_PRICE").toString());
          BigDecimal quantity = ds.getQty();
          setLineExtPrice(quantity, unitPrice, (String) h.get("CURRENCY_ID"), (String) h.get("ITEM_DISPLAY_PKG_STYLE"));
        }
      }else {
        ePriceL.setText("");
      }
      setExtendedPrice();
    }
    delInfo = ds.getDeliverySchedule();
  }

  void cancelB_actionPerformed(ActionEvent e) {
    // confirm cancellation
    int row = rliTable.getSelectedRow();
    String line = rliTable.getModel().getValueAt(row, 0).toString();
    String item = rliTable.getModel().getValueAt(row, ITEM_COL).toString();
    CancelConfirmDlg cf = new CancelConfirmDlg(cmis.getMain(), "Confirm Cancellation", true);
    cf.setMsg("Do you really want to cancel this line " + line + " (item=" + item + ")?\n\nEnter your password and click \"Yes\" to confirm.");
    cf.setPassTVisible(true);
    cf.setNoButtonText("No");
    cf.setYesButtonText("Yes");
    cf.setVisible(true);
    if (cf.getConfirmationFlag() && cf.getPassT() != null
        && !cf.getPassT().trim().equals(cmis.getLogonPass().trim())) {
      GenericDlg dlg = new GenericDlg(cmis.getMain(), "Not confirmed", true);
      dlg.setMsg("You need to enter your password to confirm cancellation.");
      dlg.setVisible(true);
      return;
    }

    if (cf.getConfirmationFlag()) {
      String reasons = cf.reasonT.getText();
      markCancellationRequested(line, reasons);
    }
  }

  void markCancellationRequested(String line, String reasons) {
    TcmisHttpConnection cgi = new TcmisHttpConnection("MaterialRequest", cmis);
    Hashtable query = new Hashtable();
    query.put("FUNCTION", "CANCEL_ORDER"); //String, String
    query.put("USER_ID", cmis.getUserId().toString());
    query.put("REQ_NUM", (new Integer(rNum)).toString()); //String, String
    query.put("LINE_NUM", line); //String, String
    query.put("CANCEL_NOTES", reasons); //String, String
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      GenericDlg.showAccessDenied(cmis.getMain());
      ClientHelpObjs.setEnabledContainer(this, true);
      return;
    }
    String msg = result.get("MSG").toString();
    GenericDlg dlg = new GenericDlg(cmis.getMain(), "Results", true);
    dlg.setMsg(msg);
    dlg.setVisible(true);
  }

  void ccardB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    CursorChange.setCursor(this, Cursor.WAIT_CURSOR);
    CreditCardInfoDlg cc = new CreditCardInfoDlg(cmis.getMain(), "Credit Card Information", this, (Hashtable) headerInfo.get("CREDIT_CARD_INFO"), (String) headerInfo.get("REQUESTOR"));
    cc.setParent(cmis);
    cc.setEditable("Request".equalsIgnoreCase(headerInfo.get("VIEW_TYPE").toString()));
    cc.loadIt();
    CenterComponent.centerCompOnScreen(cc);
    cc.setVisible(true);
    CursorChange.setCursor(this, Cursor.DEFAULT_CURSOR);
  }

  void returnCartB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if ("MR Alloc Report".equalsIgnoreCase(returnCartB.getText())) {
      showMrAllocation(headerInfo);
      /*
      MRAllocationReport dlg = new MRAllocationReport(cmis.getMain(), "MR Allocation Report", true, (Vector) headerInfo.get("MR_ALLOCATION_INFO"),
          (String) headerInfo.get("REQUESTOR"), (String) headerInfo.get("FACILITY"));
      dlg.setGrandParent(cmis);
      dlg.setVisible(true);
      */
    } else if ("Approve Line".equalsIgnoreCase(returnCartB.getText())) {
      if (rliTable.getSelectedRow() < 0) {
        GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
        gd.setMsg("Please select a line that you want to approve.");
        gd.setVisible(true);
        return;
      }
      goApproveUsage("Line");
    } else {
      cmis.getMain().goSearchAction(4, this.rNum);
    }
  }

  void scrapC_actionPerformed(ActionEvent e) {
    if (scrapC.isSelected()) {
      scrapC.setForeground(Color.red);
    } else {
      scrapC.setForeground(Color.black);
    }
    setDockCombo();
    setDeliverToCombo();
    resetScrapCopiedFlag();
  }

  void financeB_actionPerformed(ActionEvent e) {
    String finStat = (String) headerInfo.get("PENDING_FINANCE");
    String title = "";
    if (finStat.equalsIgnoreCase("pending")) {
      if (!financeDlgOpen) {
        title = "Request Pending Finance Approval:";
        FinanceApproverListDlg fald = new FinanceApproverListDlg(this, cmis.getMain(), title, new Integer(rNum).toString());
        fald.setParent(cmis);
        fald.loadIt();
        CenterComponent.centerCompOnScreen(fald);
        fald.setVisible(true);
      }
      financeDlgOpen = true;
    } else if (finStat.equalsIgnoreCase("approved") || finStat.equalsIgnoreCase("rejected")) {
      if (!financeDlgOpen) {
        title = "Finance Approver Information:";
        FinanceApproverDlg fad = new FinanceApproverDlg(this, cmis.getMain(), title, new Integer(rNum).toString(), finStat);
        fad.setParent(cmis);
        fad.loadIt();
        CenterComponent.centerCompOnScreen(fad);
        fad.setVisible(true);
      }
      financeDlgOpen = true;
    }
  } //end of method
} //end of class

class PrintReportLoadThread extends Thread {
  RequestsWin parent;
  public PrintReportLoadThread(RequestsWin parent) {
    super("PrintReportLoadThread");
    this.parent = parent;
  }

  public void run() {
    parent.printScreenDataAction();
  }
}

class RequestsWinLoadThread extends Thread {
  RequestsWin parent;
  public RequestsWinLoadThread(RequestsWin parent) {
    super("RequestsWinLoadThread");
    this.parent = parent;
  }

  public void run() {
    parent.loadItAction();
  }
}

class RequestWin_BoxCellEditor extends DefaultCellEditor implements
    ItemListener, ActionListener {

  protected JComboBox currentBox = null;

  public RequestWin_BoxCellEditor(JComboBox box) {
    super(box);
    currentBox = box;
  }

  // These methods implement the TableCellEditor interface
  public Component getTableCellEditorComponent(JTable table, Object value,
                                               boolean isSelected, int row, int column) {

    //((JComboBox)editorComponent).setSelectedItem(((JComboBox)value).getSelectedItem());
    editorComponent = (JComboBox) value;
    ( (JComboBox) editorComponent).setFont(table.getFont());
    ( (JComboBox) editorComponent).addItemListener(this);
    ( (JComboBox) editorComponent).addActionListener(this);
    currentBox = (JComboBox) editorComponent;
    currentBox.addItemListener(this);
    currentBox.addActionListener(this);
    // rfz allow editing .... currentBox.setEditable(false);
    table.setRowSelectionInterval(row, row);
    table.repaint();

    return editorComponent;
  }

  public Object getCellEditorValue() {
    return currentBox;
  }

  public boolean isCellEditable(EventObject evt) {
    if (evt instanceof MouseEvent) {
      if ( ( (MouseEvent) evt).getClickCount() >=
          getClickCountToStart()) {
        return true;
      }
    }
    return false;
  }

  public void cancelCellEditing() {
    fireEditingStopped();
  }

  public void itemStateChanged(ItemEvent e) {
    fireEditingStopped();
  }

  public void actionPerformed(ActionEvent e) {
    fireEditingStopped();
  }
}

class MRJTextFieldFocusListener implements java.awt.event.FocusListener {
  RequestsWin adaptee;
  MRJTextFieldFocusListener(RequestsWin adaptee) {
    this.adaptee = adaptee;
  }

  public void focusLost(FocusEvent e) {
    adaptee.unitPriceT_focusLost();
  }

  public void focusGained(FocusEvent e) {
    //do nothing
  }
}

class RequestsWin_Currency_itemAdapter implements java.awt.event.ItemListener {
  RequestsWin adaptee;

  RequestsWin_Currency_itemAdapter(RequestsWin adaptee) {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e) {
    if (e.getStateChange() == e.SELECTED) {
      if (!BothHelpObjs.isBlankString(adaptee.unitPriceT.getText().trim())) {
        adaptee.unitPriceT_actionPreformed();
      }
    }
  }
}

