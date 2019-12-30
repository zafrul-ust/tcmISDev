//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.waste;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.appData.*;
import java.beans.*;
import javax.swing.table.*;
import java.util.*;
import java.text.*;
import java.awt.Component;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;
import java.awt.event.*;
import javax.swing.event.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class WasteOrder extends JPanel
{
    CmisApp cmis;
    int orderNo = 0;

    CmisTable wasteOrderTable;
    CmisTable bulkWasteOrderTable;
    DbTableModel bulkModel;
    CmisTable rliTable;
    Hashtable headerInfo = new Hashtable();
    DbTableModel model;
    TableSorter sorter = new TableSorter();

    String containerShippedOn = "";


    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel topP = new JPanel();
    JPanel bottomP = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    StaticJLabel requesterL = new StaticJLabel();
    DataJLabel requesterT = new DataJLabel();
    DataJLabel vendorT = new DataJLabel();
    StaticJLabel vendorL = new StaticJLabel();
    DataJLabel orderT = new DataJLabel();
    StaticJLabel orderL = new StaticJLabel();
    DataJLabel submitT = new DataJLabel();
    StaticJLabel submitL = new StaticJLabel();
    DataJLabel resubmitT = new DataJLabel();
    StaticJLabel resubmitL = new StaticJLabel();
    //DataJLabel cancelT = new DataJLabel();
    //StaticJLabel cancelL = new StaticJLabel();
    JScrollPane jsp = new JScrollPane();
    JScrollPane bulkJsp = new JScrollPane();
    FlowLayout flowLayout1 = new FlowLayout();

    JCheckBox jCheckBox1 = new JCheckBox();
    GridBagLayout gridBagLayout2 = new GridBagLayout();

    JPanel panel1 = new JPanel();

    String days_since_pickup = "";
    int FACILITY_COL = 0;
    int WORK_AREA_COL = 0;
    int VENDOR_COL = 0;
    int WASTE_ITEM_ID_COL = 0;
    int CONTAINER_ID_COL = 0;
    int PACKAGING_COL = 0;
    int WEIGHT_COL = 0;
    int BULK_FACILITY_COL = 0;
    int BULK_VENDOR_COL = 0;
    int TSDF_COL = 0;
    //the maximum number of rows can selected at the same time
    final int MAXSELECTEDROW = 100;

    JTabbedPane wasteOrderTab = new JTabbedPane();
    ImageIcon ss = new ImageIcon();

    //list tab components
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel list = new JPanel();
    JPanel jPanel1 = new JPanel();
    JButton submitB = new JButton();
    JButton cancelB = new JButton();
    JButton editB = new JButton();
    JButton updateB = new JButton();

    JButton addMaterialB = new JButton();
    JButton labPackB = new JButton();

    //manifest tab components
    BorderLayout borderLayout3 = new BorderLayout();
    WasteManifest manifestP;
    WasteManifest manifestTab;
    boolean firstManifest;

    //shipment info tab components
    ShipmentInfoTabPanel shipmentInfoP;
    ShipmentInfoTabPanel shipmentInfoTab;
    boolean firstShip;

    //draft invoice tab components
    BorderLayout borderLayout12 = new BorderLayout();
    WasteDraftInvoice draftInvoiceTab;
    WasteDraftInvoice draftInvoiceP;
    boolean firstDraft;

    //vendor invoice tab components
    WasteVendorInvoice vendorInvoiceTab;
    WasteVendorInvoice vendorInvoiceP;
    boolean firstVendorInvoice;

    JButton msubmitB = new JButton();
    StaticJLabel storageL = new StaticJLabel();
    DataJLabel storageT = new DataJLabel();

    Vector originalContainerId = null;
    Vector originalBulkId = null;
    Integer plannedAmountCol = null;
    Vector plannedAV = null;
    Vector bulkShipIdV = null;
    Integer bulkShipIdCol = null;
    GridBagLayout gridBagLayout3 = new GridBagLayout();
    JTextField jPrefTextField1 = new JTextField();

    // Prefered Pickup Date -Nawaz
    String PrefferedPick_date = "";
    String Bulk_Preferred_date = "";
    String Shipment_Id_cont = "0";
    Integer Shipment_Idcol = null;
    Integer PrefContDate = null;
    Integer PrefBulkDate =null;
    Vector bulkPrefDateV = null;
    JTextArea jTextArea1 = new JTextArea();
    FlowLayout flowLayout2 = new FlowLayout();
    GridBagLayout gridBagLayout4 = new GridBagLayout();

    Vector actSysIDV;
    //lab pack
    JTextArea jTextArea2 = new JTextArea();
    JTextField serviceDateT = new JTextField();
    JTextArea jTextArea3 = new JTextArea();
    JTextField estDrumT = new JTextField();

    WasteColorCellRenderer colorTableRenderer = new WasteColorCellRenderer();

    public WasteOrder(CmisApp cmis)
    {
        this.cmis = cmis;
        shipmentInfoTab = null;
        shipmentInfoTab = new ShipmentInfoTabPanel();
        draftInvoiceTab = null;
        draftInvoiceTab = new WasteDraftInvoice();
        vendorInvoiceTab = null;
        vendorInvoiceTab = new WasteVendorInvoice();
        manifestTab = null;
        manifestTab = new WasteManifest();

        try
        {
            jbInit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void setShipNum(int orderNo)
    {
        this.orderNo = orderNo;
    }

    public void setCmis(CmisApp cmis)
    {
        this.cmis = cmis;
        loadIt();
    }

    public void loadIt()
    {
        WasteOrderLoadThread wtlt = new WasteOrderLoadThread(this,"load");
        wtlt.start();
    }

    private void jbInit() throws Exception
    {
        /*String wDir = new String(System.getProperty("user.dir"));
        String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
        */
        this.setLayout(gridBagLayout1);
        topP.setMaximumSize(new Dimension(140, 150));
        topP.setMinimumSize(new Dimension(140, 150));
        topP.setPreferredSize(new Dimension(140, 150));
        topP.setLayout(gridBagLayout2);
        bottomP.setLayout(borderLayout1);

        wasteOrderTable = new CmisTable();
        jsp.getViewport().setView(wasteOrderTable);
        wasteOrderTable.addMouseListener(new WasteOrder_wasteOrderTable_mouseAdapter(this));

        bulkWasteOrderTable = new CmisTable();
        bulkJsp.getViewport().setView(bulkWasteOrderTable);
        bulkWasteOrderTable.addMouseListener(new BulkWasteOrder_bulkWasteOrderTable_mouseAdapter(this));

        requesterL.setText("Requester:");
        vendorL.setText("Vendor:");
        orderL.setText("Waste Order:");
        submitL.setText("Submitted:");
        resubmitL.setText("Resubmitted:");
        //cancelL.setText("Canceled:");


        storageL.setText("Storage Area:");
        jsp.setMinimumSize(new Dimension(200, 200));
        jsp.setPreferredSize(new Dimension(200, 200));
        bulkJsp.setMinimumSize(new Dimension(200, 100));
        bulkJsp.setPreferredSize(new Dimension(200, 100));
        list.setLayout(gridBagLayout3);
        jPrefTextField1.setPreferredSize(new Dimension(67, 23));
        jPrefTextField1.addActionListener(new java.awt.event.ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            jPrefTextField1_actionPerformed(e);
          }
        });
        jPrefTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            jPrefTextField1_mousePressed();
          }
        });
        jPrefTextField1.setToolTipText("  MM/DD/YYYY  ");

        jTextArea1.setLineWrap(true);
        jTextArea1.setPreferredSize(new Dimension(175, 30));
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setRows(2);
        jTextArea1.setOpaque(false);
        jTextArea1.setBorder(null);
        jTextArea1.setSelectedTextColor(Color.darkGray);
        jTextArea1.setMinimumSize(new Dimension(175, 15));
        jTextArea1.setDoubleBuffered(true);
        jTextArea1.setText("Preferred Container Shipmemt Date:");
        jTextArea1.setToolTipText("");
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font("Dialog", 0, 10));

        jTextArea2.setLineWrap(true);
        jTextArea2.setPreferredSize(new Dimension(165, 30));
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setRows(2);
        jTextArea2.setOpaque(false);
        jTextArea2.setBorder(null);
        jTextArea2.setSelectedTextColor(Color.darkGray);
        jTextArea2.setMinimumSize(new Dimension(160, 15));
        jTextArea2.setDoubleBuffered(true);
        jTextArea2.setText("Preferred Lab Pack Service Date:");
        jTextArea2.setToolTipText("");
        jTextArea2.setEditable(false);
        jTextArea2.setFont(new java.awt.Font("Dialog", 0, 10));
        serviceDateT.setPreferredSize(new Dimension(67, 23));
        serviceDateT.setToolTipText("  MM/DD/YYYY  ");
        serviceDateT.addActionListener(new java.awt.event.ActionListener(){
          public void actionPerformed(ActionEvent e){
            serviceDateT_actionPerformed(e);
          }
        });
        serviceDateT.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            serviceDateT_mousePressed();
          }
        });

        jTextArea3.setLineWrap(true);
        jTextArea3.setPreferredSize(new Dimension(165, 30));
        jTextArea3.setWrapStyleWord(true);
        jTextArea3.setRows(2);
        jTextArea3.setOpaque(false);
        jTextArea3.setBorder(null);
        jTextArea3.setSelectedTextColor(Color.darkGray);
        jTextArea3.setMinimumSize(new Dimension(150, 15));
        jTextArea3.setDoubleBuffered(true);
        jTextArea3.setText("Estimated # of Lab Pack Drums:");
        jTextArea3.setToolTipText("");
        jTextArea3.setEditable(false);
        jTextArea3.setFont(new java.awt.Font("Dialog", 0, 10));
        estDrumT.setPreferredSize(new Dimension(67, 23));
        estDrumT.addActionListener(new java.awt.event.ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            estDrumT_actionPerformed(e);
          }
        });
        estDrumT.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            estDrumT_mousePressed();
          }
        });

        submitB.setFont(new java.awt.Font("Dialog", 0, 10));
        cancelB.setFont(new java.awt.Font("Dialog", 0, 10));
        editB.setFont(new java.awt.Font("Dialog", 0, 10));
        addMaterialB.setFont(new java.awt.Font("Dialog", 0, 10));
        labPackB.setFont(new java.awt.Font("Dialog", 0, 10));
        updateB.setFont(new java.awt.Font("Dialog", 0, 10));
        panel1.setLayout(flowLayout2);
        jPanel1.setLayout(gridBagLayout4);
        wasteOrderTab.setFont(new java.awt.Font("Dialog", 0, 10));
    this.add(bottomP, new GridBagConstraints2(0, 1, 2, 1, 1.0, 1.0
                                                  ,
                                                  GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0,
                                                             5, 5, 5), 0, 265));

        //setting for tab panel
        bottomP.add(wasteOrderTab, BorderLayout.CENTER);
        wasteOrderTab.setTabPlacement(JTabbedPane.TOP);
        ss = ResourceLoader.getImageIcon("images/button/waste_tab.gif","Waste Order");

        buildListTab();  //initailizing all the components for list panel.
        wasteOrderTab.addTab("List",list);
        wasteOrderTab.addTab("Shipment Info",shipmentInfoTab);
        //settting draft invoice panel.
        wasteOrderTab.addTab("Shipment Costs",draftInvoiceTab);
        wasteOrderTab.addChangeListener(new ChangeListener()
                                        {
                public void stateChanged(ChangeEvent e)
                {
                    tab_changedPerformed(e);
                }
            }
        );

        this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 0.0
                                               ,
                                               GridBagConstraints.NORTH,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(0, 0, 0, 0), 0, -95));

        topP.add(requesterL, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
                                                     ,
                                                     GridBagConstraints.EAST,
                                                     GridBagConstraints.NONE,
                                                     new Insets(5,
                                                                5,
                                                                0, 5), 0, 0));
        topP.add(requesterT, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
                                                     ,
                                                     GridBagConstraints.WEST,
                                                     GridBagConstraints.NONE,
                                                     new Insets(5,
                                                                5,
                                                                0, 5), 0, 0));
        topP.add(vendorL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5,
                                                             5, 0, 5), 0, 0));
        topP.add(vendorT, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5,
                                                             5, 0, 5), 0, 0));
        topP.add(orderL, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                                                 ,
                                                 GridBagConstraints.EAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 5, 0, 5), 0, 0));
        topP.add(orderT, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
                                                 ,
                                                 GridBagConstraints.WEST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 5, 0, 5), 0, 0));

        topP.add(submitL, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5,
                                                             5, 0, 5), 0, 0));
        topP.add(submitT, new GridBagConstraints2(5, 0, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(5,
                                                             5, 0, 5), 0, 0));
        topP.add(resubmitL, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
                                                    ,
                                                    GridBagConstraints.EAST,
                                                    GridBagConstraints.NONE,
                                                    new Insets(0,
                                                               5, 0, 5), 0, 0));
        topP.add(resubmitT, new GridBagConstraints2(5, 1, 1, 1, 0.0, 0.0
                                                    ,
                                                    GridBagConstraints.WEST,
                                                    GridBagConstraints.NONE,
                                                    new Insets(0,
                                                               5, 0, 5), 0, 0));
        topP.add(storageL, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.EAST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(0,
                                                              5, 0, 5), 0, 0));
        topP.add(storageT, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(0,
                                                              5, 0, 5), 0, 0));
    }

    void buildListTab()
    {
        editB.setText("Edit List");
        editB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif","Edit"));
        editB.addActionListener(new java.awt.event.ActionListener()
                                {
                public void actionPerformed(ActionEvent e)
                {
                    editB_actionPerformed(e);
                }
            }
        );

        updateB.setText("Update Weight");
        updateB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Update"));
        updateB.addActionListener(new java.awt.event.ActionListener()
                                  {
                public void actionPerformed(ActionEvent e)
                {
                    updateB_actionPerformed(e);
                }
            }
        );

        submitB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif","Submit"));
        submitB.addActionListener(new java.awt.event.ActionListener()
                                  {
                public void actionPerformed(ActionEvent e)
                {
                    submitB_actionPerformed(e);
                }
            }
        );

        addMaterialB.setText("Add Waste Material");
        addMaterialB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
        addMaterialB.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    addMaterialB_actionPerformed(e);
                }
            }
        );

        labPackB.setText("Add Lab Pack Container(s)");
        labPackB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif","Add"));
        labPackB.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    labPackB_actionPerformed(e);
                }
            }
        );

        cancelB.setText("Cancel Submitted Order");
        cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel2.gif",
                                      "Cancel"));
        cancelB.addActionListener(new java.awt.event.ActionListener()
                                  {

                public void actionPerformed(ActionEvent e)
                {
                    cancelB_actionPerformed(e);
                }
            }
        );

        list.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0,
                                                             5, 0, 5), 0, 0));
        //3-27-01 block until ready
        //jPanel1.add(addMaterialB, null);
        updateB.setFont(new java.awt.Font("Dialog", 0, 10));
        updateB.setMaximumSize(new Dimension(135, 25));
        updateB.setMinimumSize(new Dimension(135, 25));
        updateB.setPreferredSize(new Dimension(135, 25));

        submitB.setFont(new java.awt.Font("Dialog", 0, 10));
        submitB.setMaximumSize(new Dimension(150, 25));
        submitB.setMinimumSize(new Dimension(150, 25));
        submitB.setPreferredSize(new Dimension(150, 25));

        addMaterialB.setFont(new java.awt.Font("Dialog", 0, 10));
        addMaterialB.setMaximumSize(new Dimension(190, 25));
        addMaterialB.setMinimumSize(new Dimension(190, 25));
        addMaterialB.setPreferredSize(new Dimension(190, 25));

        labPackB.setFont(new java.awt.Font("Dialog", 0, 10));
        labPackB.setMaximumSize(new Dimension(190, 25));
        labPackB.setMinimumSize(new Dimension(190, 25));
        labPackB.setPreferredSize(new Dimension(190, 25));

        editB.setFont(new java.awt.Font("Dialog", 0, 10));
        editB.setMaximumSize(new Dimension(103, 25));
        editB.setMinimumSize(new Dimension(103, 25));
        editB.setPreferredSize(new Dimension(103, 25));

        jPrefTextField1.setFont(new java.awt.Font("Dialog", 0, 12));
        jPrefTextField1.setMaximumSize(new Dimension(90, 25));
        jPrefTextField1.setMinimumSize(new Dimension(90, 25));
        jPrefTextField1.setPreferredSize(new Dimension(90, 25));

        serviceDateT.setFont(new java.awt.Font("Dialog", 0, 12));
        serviceDateT.setMaximumSize(new Dimension(90, 25));
        serviceDateT.setMinimumSize(new Dimension(90, 25));
        serviceDateT.setPreferredSize(new Dimension(90, 25));

        estDrumT.setFont(new java.awt.Font("Dialog", 0, 12));
        estDrumT.setMaximumSize(new Dimension(90, 25));
        estDrumT.setMinimumSize(new Dimension(90, 25));
        estDrumT.setPreferredSize(new Dimension(90, 25));

        jPanel1.add(jTextArea1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.EAST,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));
        jPanel1.add(jPrefTextField1,new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0,GridBagConstraints.WEST,
                    GridBagConstraints.NONE,new Insets(5, 0, 0, 5), 0, 0));
        jPanel1.add(editB, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));
        jPanel1.add(addMaterialB, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));

        jPanel1.add(jTextArea2, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.EAST,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));
        jPanel1.add(serviceDateT,new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0,GridBagConstraints.WEST,
                    GridBagConstraints.NONE,new Insets(5, 0, 0, 5), 0, 0));
        jPanel1.add(jTextArea3, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0,GridBagConstraints.EAST,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));
        jPanel1.add(estDrumT,new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0,GridBagConstraints.WEST,
                    GridBagConstraints.NONE,new Insets(5, 0, 0, 5), 0, 0));
        jPanel1.add(labPackB, new GridBagConstraints2(2, 1, 2, 1, 0.0, 0.0,GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));

        jPanel1.add(updateB, new GridBagConstraints2(2, 2, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));
        jPanel1.add(submitB, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0,GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,new Insets(5, 5, 0, 5), 0, 0));
        list.add(jsp, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0,GridBagConstraints.CENTER,
                  GridBagConstraints.BOTH,new Insets(5, 5, 0, 5), 0, 0));
        list.add(bulkJsp, new GridBagConstraints2(0, 2, 1, 1, 1.0, 1.0,GridBagConstraints.CENTER,
                  GridBagConstraints.BOTH,new Insets(5,5, 0, 5), 0, 0));

    }


    public void copy1FieldClicked(MouseEvent e)
    {
        // do SOMETHING;
    }

    public void labFieldClicked(MouseEvent e)
    {
        // do SOMETHING;
    }

    void loadItAction(String action)
    {
        Boolean radianWaste = null;
        try
        {
            ClientHelpObjs.setEnabledContainer(this,false);
            TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",
                                                              cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","GET_SHIPMENT_LIST"); //String, String
            query.put("FACILITY_ID",cmis.getPrefFac());
            query.put("USER_ID",cmis.getUserId());
            query.put("ORDER_NO",new Integer(orderNo));

            Hashtable result = cgi.getResultHash(query);
            if (result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
                return;
            }
            requesterT.setText((String)result.get("MANAGER_NAME"));
            requesterT.setEnabled(true);

            Hashtable ht = new Hashtable();
            ht = (Hashtable)result.get("SCREEN_DATA");

            headerInfo = (Hashtable)ht.get("HEADER");
            actSysIDV = (Vector)headerInfo.get("ACT_SYS_ID");
            headerInfo.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
            //determine how to label the addMaterial button
            Boolean containMat = (Boolean)headerInfo.get("CONTAIN_MATERIAL");
            if (containMat.booleanValue()) {
              addMaterialB.setText("Edit Waste Material");
            }

            buildContainerTableModel(ht);
            containerShippedOn = (String)ht.get("CONTAINER_SHIPPED_ON");      //3-13-01

            buildBulkTableModel(ht);
            plannedAmountCol = (Integer) ht.get("PLANNED_AMOUNT_COL");
            bulkShipIdCol = (Integer) ht.get("BULK_SHIPMENT_ID_COL");

            //lab pack option
            this.serviceDateT.setText(BothHelpObjs.makeBlankFromNull(ht.get("LAB_PACK_PREFERRED_SERVICE_DATE").toString()));
            this.estDrumT.setText(BothHelpObjs.makeBlankFromNull(ht.get("LAB_PACK_DRUM_ESTIMATE").toString()));

            Shipment_Idcol = (Integer) ht.get("SHIPMENT_ID_COL");
            PrefContDate = (Integer) ht.get("PREF_COL");
            PrefBulkDate = (Integer) ht.get("BPREFERED_DATE_COL");
            radianWaste = (Boolean)ht.get("RADIAN_WASTE");
            //column
            Hashtable keyCols = (Hashtable)result.get("KEY_COLS");
            FACILITY_COL = ((Integer)keyCols.get("FACILITY_ID")).intValue();
            WORK_AREA_COL = ((Integer)keyCols.get("WORK_AREA")).intValue();
            VENDOR_COL = ((Integer)keyCols.get("VENDOR_ID")).intValue();
            WASTE_ITEM_ID_COL = ((Integer)keyCols.get("WASTE_ITEM_ID")).intValue();
            CONTAINER_ID_COL = ((Integer)keyCols.get("CONTAINER_ID")).intValue();
            PACKAGING_COL = ((Integer)keyCols.get("PACKAGING")).intValue();
            WEIGHT_COL = ((Integer)keyCols.get("WEIGHT")).intValue();
            TSDF_COL = ((Integer)keyCols.get("TSDF")).intValue();

            buildHeaderInfo();
            buildContainerLineSummary();
            buildBulkLineSummary();
            if (wasteOrderTable.getRowCount() > 0 )
            {
                jPrefTextField1.setText(wasteOrderTable.getModel().getValueAt(0,PrefContDate.intValue()).toString());
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ClientHelpObjs.setEnabledContainer(this,true);
            ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
            this.revalidate();
            //this.repaint();
        }

        if (radianWaste.booleanValue())
        {
            wasteOrderTab.addTab("Vendor Invoice",vendorInvoiceTab);
        }
        firstManifest = true;
        firstShip = true;
        firstDraft = true;
        firstVendorInvoice = true;

        //lab pack option
        if ("Y".equalsIgnoreCase((String)headerInfo.get("LAB_PACK_OPTION"))) {
          serviceDateT.setEnabled(true);
          estDrumT.setEnabled(true);
          labPackB.setEnabled(true);
        }else {
          serviceDateT.setEnabled(false);
          estDrumT.setEnabled(false);
          labPackB.setEnabled(false);
        }

        //enable submit button
        if (submitB.getText().toString().startsWith("Resubmit")) {
          submitB.setEnabled(false);
        }
        updateB.setEnabled(false);

        //depending of the status of the shipment users are/not allow to change list tab info
        Vector contShipStatusV = (Vector)headerInfo.get("CONTAINER_SHIPMENT_STATUS");
        Vector bulkShipStatusV = (Vector)headerInfo.get("BULK_SHIPMENT_STATUS");
        if (contShipStatusV.size() > 0) {
          for (int i = 0; i < contShipStatusV.size(); i++) {
            if ("approved".equalsIgnoreCase(contShipStatusV.elementAt(i).toString()) ||
                "invoiced".equalsIgnoreCase(contShipStatusV.elementAt(i).toString()) ||
                "mixed".equalsIgnoreCase(contShipStatusV.elementAt(i).toString()) ||
                "locked".equalsIgnoreCase(contShipStatusV.elementAt(i).toString()) ) {
              labPackB.setEnabled(false);
            }
          }
        }  //end of if contShipStatus
    } //end of method

  void buildContainerTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("SHIPMENT_TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("SHIPMENT_TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("SHIPMENT_TABLE_TYPES");
    Vector data = (Vector) searchData.get("SHIPMENT_TABLE_DATA");

    model = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      model.addRow((Object[]) data.elementAt(i));
    }
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
  }  //end of method

  void buildBulkTableModel(Hashtable searchData) {
    String[] colHeads = (String[])searchData.get("BULK_SHIPMENT_TABLE_HEADERS");
    int[] colWidths = (int[]) searchData.get("BULK_SHIPMENT_TABLE_WIDTHS");
    int[] colTypes = (int[]) searchData.get("BULK_SHIPMENT_TABLE_TYPES");
    Vector data = (Vector) searchData.get("BULK_SHIPMENT_TABLE_DATA");

    bulkModel = new DbTableModel(colHeads);
    for (int i = 0; i < data.size(); i++) {
      bulkModel.addRow((Object[]) data.elementAt(i));
    }
    bulkModel.setColumnPrefWidth(colWidths);
    bulkModel.setColumnType(colTypes);
  }  //end of method

    public void printScreenData()
    {
        Component comp = wasteOrderTab.getSelectedComponent();
        if (comp.equals(list))
        {
            printListCostScreen("LIST");
        }
        else if (comp.equals(shipmentInfoTab))
        {
            shipmentInfoTab.printScreenData(getHeaderInfo());
        }
        else if (comp.equals(draftInvoiceTab))
        {
            printListCostScreen("COST");
        }
        else if (comp.equals(vendorInvoiceTab))
        {
            printListCostScreen("INVOICE");
        }
    }
    public void printListCostScreen(String selectedTab)
    {
        ReportOptionDlg rod = new ReportOptionDlg(cmis.getMain());
        rod.setCmisApp(cmis);
        rod.setRequestorName(requesterT.getText().toString());
        rod.setScreen(selectedTab);
        rod.setVendorName(vendorT.getText().toString());
        rod.setWasteOrder(orderT.getText().toString());
        rod.setRequestorName(requesterT.getText().toString());
        rod.setStorageArea(storageT.getText().toString());

        rod.setSubmitDate(submitT.getText().toString());
        rod.setReSubmitDate(resubmitT.getText().toString());

        rod.loadIt();
    }
    public Hashtable getHeaderInfo()
    {
        Hashtable h = new Hashtable();
        h.put("VENDOR",vendorT.getText().toString());
        h.put("WASTE_ORDER",orderT.getText().toString());
        h.put("REQUESTER",requesterT.getText().toString());
        h.put("STORAGE_AREA",storageT.getText().toString());
        h.put("SUBMITTED",submitT.getText().toString());
        h.put("RESUBMITTED",resubmitT.getText().toString());

        return h;
    }



    public void buildHeaderInfo()
    {
        String submitD = (String)headerInfo.get("DATE_SUBMIT");
        if (BothHelpObjs.isBlankString(submitD))
        {
            submitB.setText("Submit to Vendor");
            submitB.setEnabled(true);
        }
        else
        {
            submitB.setText("Resubmit to Vendor");
            submitB.setEnabled(false);
        }

        storageT.setText((String)headerInfo.get("STORAGE_DESC"));
        vendorT.setText((String)headerInfo.get("VENDOR_NAME"));
        requesterT.setText((String)headerInfo.get("REQUESTER_NAME"));
        submitT.setText((String)headerInfo.get("DATE_SUBMIT"));
        resubmitT.setText((String)headerInfo.get("DATE_RESUBMIT"));
        Integer sNum = new Integer(orderNo);
        orderT.setText(sNum.toString());
        this.revalidate();
    }

    public void buildContainerLineSummary()
    {
        list.remove(jsp);
        int pos = mypow(2,WEIGHT_COL);
        model.setEditableFlag(pos);
        sorter = new TableSorter(model);
        wasteOrderTable = new CmisTable(sorter);
        sorter.setColTypeFlag(model.getColumnTypesString());
        sorter.addMouseListenerToHeaderInTable(wasteOrderTable);
        wasteOrderTable.getTableHeader().setResizingAllowed(true);
        wasteOrderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        wasteOrderTable.setCellSelectionEnabled(false);
        wasteOrderTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        wasteOrderTable.getTableHeader().setReorderingAllowed(true);
        wasteOrderTable.setToolTipText(" Assumed Full for billing. Enter weight for reporting  ");
        wasteOrderTable.addMouseListener(new WasteOrder_wasteOrderTable_mouseAdapter(this));
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
        wasteOrderTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = wasteOrderTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }

        jsp.getViewport().setView(wasteOrderTable);
        for(int i=0;i<wasteOrderTable.getColumnCount();i++)
        {
            if (model.getColumnWidth(i)==0)
            {
                wasteOrderTable.getColumn(wasteOrderTable.getColumnName(i)).setWidth(model.getColumnWidth(i));
                wasteOrderTable.getColumn(wasteOrderTable.getColumnName(i)).setMaxWidth(model.getColumnWidth(i));
                wasteOrderTable.getColumn(wasteOrderTable.getColumnName(i)).setMinWidth(model.getColumnWidth(i));
            }
            wasteOrderTable.getColumn(wasteOrderTable.getColumnName(i)).setPreferredWidth(model.getColumnWidth(i));
        }

        wasteOrderTable.setDefaultRenderer(String.class, colorTableRenderer);
        wasteOrderTable.setDefaultRenderer(Integer.class, colorTableRenderer);
        wasteOrderTable.setDefaultRenderer(Double.class, colorTableRenderer);

        wasteOrderTable.validate();
        wasteOrderTable.revalidate();
        wasteOrderTable.repaint();
        jsp.validate();
        list.add(jsp, new GridBagConstraints2(0, 1, 1, 1, 1.0, 1.0,GridBagConstraints.CENTER,
                  GridBagConstraints.BOTH,new Insets(5, 5, 0, 5), 0, 0));
        list.validate();
    }

    public int mypow(int y, int x)
    {
        int value = 1;
        for (int i = 0; i < x; i++)
        {
            value *= y;
        }
        return value;
    }

    public void buildBulkLineSummary()
    {
        list.remove(bulkJsp);
        int pos = mypow(2,
                        plannedAmountCol.intValue()) + mypow(2,
                                                             PrefBulkDate.intValue());

        bulkModel.setEditableFlag(pos);

        sorter = new TableSorter(bulkModel);
        bulkWasteOrderTable = new CmisTable(sorter);
        sorter.setColTypeFlag(model.getColumnTypesString());
        sorter.addMouseListenerToHeaderInTable(bulkWasteOrderTable);
        bulkWasteOrderTable.getTableHeader().setResizingAllowed(true);
        bulkWasteOrderTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        bulkWasteOrderTable.setCellSelectionEnabled(false);
        bulkWasteOrderTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        bulkWasteOrderTable.getTableHeader().setReorderingAllowed(true);

        //jsp = new JScrollPane(wasteOrderTable);
        bulkWasteOrderTable.addMouseListener(new BulkWasteOrder_bulkWasteOrderTable_mouseAdapter(this));
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
        /*TableColumnModel cm = bulkWasteOrderTable.getColumnModel();
        for (int i = 0; i < bulkWasteOrderTable.getColumnCount(); i++) {
          cm.getColumn(i).setCellRenderer(renderers[0]);
        }*/
        //font and foreground and background color for columns heading
        String fontName = (String)chargeTableStyle.get("FONT_NAME");
        Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
        Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
        bulkWasteOrderTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = bulkWasteOrderTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }
        bulkJsp.getViewport().setView(bulkWasteOrderTable);
        for(int i=0;i<bulkWasteOrderTable.getColumnCount();i++)
        {
            if (bulkModel.getColumnWidth(i)==0)
            {
                bulkWasteOrderTable.getColumn(bulkWasteOrderTable.getColumnName(i)).setWidth(bulkModel.getColumnWidth(i));
                bulkWasteOrderTable.getColumn(bulkWasteOrderTable.getColumnName(i)).setMaxWidth(bulkModel.getColumnWidth(i));
                bulkWasteOrderTable.getColumn(bulkWasteOrderTable.getColumnName(i)).setMinWidth(bulkModel.getColumnWidth(i));
            }
            bulkWasteOrderTable.getColumn(bulkWasteOrderTable.getColumnName(i)).setPreferredWidth(bulkModel.getColumnWidth(i));
        }

        bulkWasteOrderTable.setDefaultRenderer(String.class, colorTableRenderer);
        bulkWasteOrderTable.setDefaultRenderer(Integer.class, colorTableRenderer);
        bulkWasteOrderTable.setDefaultRenderer(Double.class, colorTableRenderer);

        bulkWasteOrderTable.validate();
        bulkWasteOrderTable.revalidate();
        bulkWasteOrderTable.repaint();
        bulkJsp.validate();
        //list.add(bulkJsp);
        list.add(bulkJsp, new GridBagConstraints2(0, 2, 1, 1, 1.0, 1.0,GridBagConstraints.CENTER,
                  GridBagConstraints.BOTH,new Insets(5,5, 0, 5), 0, 0));
        list.validate();
    }


    private void doRightClicked(MouseEvent e)
    {
        //Table clicked
        JFrame F;
        int catSel = wasteOrderTable.getSelectedRow();
        WasteMetaPopUp mpu = new WasteMetaPopUp(cmis,
                                                Integer.parseInt(wasteOrderTable.getModel().getValueAt(catSel,WASTE_ITEM_ID_COL).toString())); // Waste Profile Id

        panel1.add(mpu);

        int[] row = new int[MAXSELECTEDROW];
        row   = wasteOrderTable.getSelectedRows();
        Vector containerV = new Vector();
        for (int i = 0; i < row.length; i++)
        {
            containerV.addElement(wasteOrderTable.getModel().getValueAt(row[i],
                                                                        this.CONTAINER_ID_COL).toString());
        }
        mpu.setContainerId(containerV);
        mpu.enableMenu("PrintLabel");
        if (row.length == 1)
        {
            mpu.enableMenu("ChgProfile");
            String pack = wasteOrderTable.getModel().getValueAt(wasteOrderTable.getSelectedRow(),PACKAGING_COL).toString();
            mpu.setPackaging(pack);
            mpu.setFacilityId(wasteOrderTable.getModel().getValueAt(wasteOrderTable.getSelectedRow(),FACILITY_COL).toString());
            mpu.setFromWasteOrder(true);
            mpu.setWasteOrder(this);
            mpu.setSelectedRow(wasteOrderTable.getSelectedRow());
            mpu.setChangeTsdfProfile("Y".equalsIgnoreCase(wasteOrderTable.getModel().getValueAt(wasteOrderTable.getSelectedRow(),TSDF_COL).toString()));
        }
        mpu.show(wasteOrderTable,e.getX(),e.getY());
        return;
    }

    private void doBulkRightClicked(MouseEvent e)
    {
        //Table clicked
        JFrame F;
        int catSel = bulkWasteOrderTable.getSelectedRow();
        WasteMetaPopUp mpu = new WasteMetaPopUp(cmis,
                                                Integer.parseInt(bulkWasteOrderTable.getModel().getValueAt(catSel,WASTE_ITEM_ID_COL).toString())); // Waste Profile Id

        panel1.add(mpu);

        int[] row = new int[MAXSELECTEDROW];
        row   = bulkWasteOrderTable.getSelectedRows();
        Vector containerV = new Vector();
        for (int i = 0; i < row.length; i++)
        {
            containerV.addElement(bulkWasteOrderTable.getModel().getValueAt(row[i],
                                                                        this.CONTAINER_ID_COL).toString());
        }
        mpu.setContainerId(containerV);
        mpu.show(bulkWasteOrderTable,e.getX(),e.getY());
        return;
    }

    void wasteOrderTable_mouseClicked(MouseEvent e)
    {
        if (e.isMetaDown())
        {
            doRightClicked(e);
        }

        if (wasteOrderTable.getSelectedColumn() == WEIGHT_COL) {
          //6-26-01 depending of the status of the shipment users are/not allow to change list tab info
          Vector contShipStatusV = (Vector)headerInfo.get("CONTAINER_SHIPMENT_STATUS");
          if (contShipStatusV.size() > 0) {
            for (int i = 0; i < contShipStatusV.size(); i++) {
              if ("draft".equalsIgnoreCase(contShipStatusV.elementAt(i).toString()) ||
                  "ammended".equalsIgnoreCase(contShipStatusV.elementAt(i).toString()) ) {
                updateB.setEnabled(true);
              }
            }
          }
        }
    }

    void bulkWasteOrderTable_mouseClicked(MouseEvent e)
    {
        if (e.isMetaDown())
        {
            doBulkRightClicked(e);
        }

        if (bulkWasteOrderTable.getSelectedColumn() == plannedAmountCol.intValue() ||
            bulkWasteOrderTable.getSelectedColumn() == PrefBulkDate.intValue() )
        {
          if (BothHelpObjs.isBlankString(containerShippedOn)) {
            submitB.setEnabled(true);
          }
        }

    }

    void jPrefTextField1_mousePressed()
    {
      if (BothHelpObjs.isBlankString(containerShippedOn)) {
        submitB.setEnabled(true);
      }
      //submitB.setEnabled(true);
     //submitB.setVisible(true);
    }
     void jPrefTextField1_actionPerformed(ActionEvent e)
    {

    }

    void serviceDateT_mousePressed()
    {
      if (BothHelpObjs.isBlankString(containerShippedOn)) {
        submitB.setEnabled(true);
      }
      //submitB.setEnabled(true);
     //submitB.setVisible(true);
    }
    void serviceDateT_actionPerformed(ActionEvent e)
    {

    }
    void estDrumT_mousePressed()
    {
      if (BothHelpObjs.isBlankString(containerShippedOn)) {
        submitB.setEnabled(true);
      }
     //submitB.setEnabled(true);
     //submitB.setVisible(true);
    }
    void estDrumT_actionPerformed(ActionEvent e)
    {

    }

    void labPackB_actionPerformed(ActionEvent e) {
      CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
      //first save the original container list
      originalContainerId = new Vector();
      for (int i = 0; i < wasteOrderTable.getRowCount(); i++) {
        originalContainerId.addElement(wasteOrderTable.getModel().getValueAt(i,this.CONTAINER_ID_COL));
      }

      NewLabPackContainer nlpc = new NewLabPackContainer(cmis.getMain(),"New Lab Pack Container Profile",this,
                                (String)headerInfo.get("FACILITY_ID"),(String)headerInfo.get("STORAGE_ID"),(String)headerInfo.get("VENDOR_ID"));
      nlpc.setParent(cmis);
      nlpc.setOrderNo(orderT.getText());
      nlpc.setShipmentId((String)headerInfo.get("CONTAINER_SHIPMENT_ID"));
      nlpc.loadIt();
      CenterComponent.centerCompOnScreen(nlpc);
      nlpc.setVisible(true);
      CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
    }

    void editB_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        //CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
        if(bulkWasteOrderTable.isEditing()){
          bulkWasteOrderTable.getCellEditor().stopCellEditing();
        }
        //trong 9-21
        originalContainerId = new Vector();
        for (int i = 0; i < wasteOrderTable.getRowCount(); i++)
        {
            originalContainerId.addElement(wasteOrderTable.getModel().getValueAt(i,this.CONTAINER_ID_COL));
        }
        originalBulkId = new Vector();
        for (int i = 0; i < bulkWasteOrderTable.getRowCount(); i++)
        {
            originalBulkId.addElement(bulkWasteOrderTable.getModel().getValueAt(i,this.CONTAINER_ID_COL));
        }

        editShipment();
        cmis.refreshShipmentInfo = true;
        //CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
    }

    void updateB_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        Vector weightV = new Vector();
        if(wasteOrderTable.isEditing()){
          wasteOrderTable.getCellEditor().stopCellEditing();
        }
        originalContainerId = new Vector();
        for (int i = 0; i < wasteOrderTable.getRowCount(); i++)
        {
            originalContainerId.addElement(wasteOrderTable.getModel().getValueAt(i,this.CONTAINER_ID_COL).toString());
            weightV.addElement(wasteOrderTable.getModel().getValueAt(i,
                                                                     WEIGHT_COL).toString());
        }
        if (weightV.size() > 0)
        {
            if (updateContainerWeight(weightV))
            {
                getLineDetail(new Integer(orderNo));
            }
        }
    }
    boolean updateContainerWeight(Vector weightV)
    {
        boolean bool = false;
        try
        {
            TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",
                                                              cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","UPDATE_CONTAINER_WEIGHT");
            query.put("CONTAINER_IDS",originalContainerId);
            query.put("WEIGHT",weightV);
            Hashtable result = cgi.getResultHash(query);
            if(result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
            }

            Boolean b = (Boolean)result.get("RETURN_CODE");
            if(!b.booleanValue())
            {
                GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
                gd.setMsg(result.get("MSG").toString());
                gd.setVisible(true);
            }
            else
            {
                GenericDlg rd = new GenericDlg(cmis.getMain(),
                                               "Action Succeeded",true);
                rd.setMsg("Weight of Container(s) updated");
                rd.setVisible(true);
                bool = true;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bool;
    }


    void editShipment()
    {
        //CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
        String title = "Edit Waste Order for Vendor = " + vendorT.getText();
        title += " and Waste Order = " + orderT.getText();
        ShipmentWasteEdit swe = new ShipmentWasteEdit(cmis.getMain(),
                                                      (String)headerInfo.get("FACILITY_ID"),
                                                      (String)headerInfo.get("STORAGE_ID"),(String)headerInfo.get("VENDOR_ID"),title,orderT.getText());

        /*ShipmentWasteEdit swe = new ShipmentWasteEdit(cmis.getMain(),wasteOrderTable.getModel().getValueAt(0,FACILITY_COL).toString(),
                                                      (String)headerInfo.get("STORAGE_ID"),(String)headerInfo.get("VENDOR_ID"),title,orderT.getText());*/
        //4-23-01
        swe.setContainerShipmentId((String)headerInfo.get("CONTAINER_SHIPMENT_ID"));

        swe.setParent(cmis);
        //trong 9-21
        swe.setWasteOrder(this);
        swe.loadIt();
        CenterComponent.centerCompOnScreen(swe);
        swe.setVisible(true);
        //CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    }

    //trong 9-21
    public void getLineDetail(Integer updateOrderNo)
    {
        Boolean radianWaste = null;
        try
        {
            ClientHelpObjs.setEnabledContainer(this,false);
            TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","GET_SHIPMENT_LIST"); //String, String
            query.put("FACILITY_ID",cmis.getPrefFac());     //that's WRONG
            query.put("USER_ID",cmis.getUserId());
            query.put("ORDER_NO",updateOrderNo);
            Hashtable result = cgi.getResultHash(query);
            if (result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
                return;
            }

            Hashtable ht = new Hashtable();
            ht = (Hashtable)result.get("SCREEN_DATA");
            headerInfo = (Hashtable)ht.get("HEADER");
            headerInfo.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
            //model = (DbTableModel) ht.get("SHIPMENT_TABLE");
            buildContainerTableModel(ht);
            containerShippedOn = (String)ht.get("CONTAINER_SHIPPED_ON");      //3-13-01
            buildBulkTableModel(ht);
            //bulkModel = (DbTableModel) ht.get("BULK_SHIPMENT_TABLE");
            plannedAmountCol = (Integer) ht.get("PLANNED_AMOUNT_COL");
            bulkShipIdCol = (Integer) ht.get("BULK_SHIPMENT_ID_COL");

            radianWaste = (Boolean)ht.get("RADIAN_WASTE");
            this.serviceDateT.setText(BothHelpObjs.makeBlankFromNull(ht.get("LAB_PACK_PREFERRED_SERVICE_DATE").toString()));
            this.estDrumT.setText(BothHelpObjs.makeBlankFromNull(ht.get("LAB_PACK_DRUM_ESTIMATE").toString()));

            Hashtable keyCols = (Hashtable)result.get("KEY_COLS");
            FACILITY_COL = ((Integer)keyCols.get("FACILITY_ID")).intValue();
            WORK_AREA_COL = ((Integer)keyCols.get("WORK_AREA")).intValue();
            VENDOR_COL = ((Integer)keyCols.get("VENDOR_ID")).intValue();
            WASTE_ITEM_ID_COL = ((Integer)keyCols.get("WASTE_ITEM_ID")).intValue();
            CONTAINER_ID_COL = ((Integer)keyCols.get("CONTAINER_ID")).intValue();
            TSDF_COL = ((Integer)keyCols.get("TSDF")).intValue();

            buildContainerLineSummary();
            buildBulkLineSummary();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ClientHelpObjs.setEnabledContainer(this,true);
            ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
            this.revalidate();
            //this.repaint();
        }

        //lab pack
        if ("Y".equalsIgnoreCase((String)headerInfo.get("LAB_PACK_OPTION"))) {
          serviceDateT.setEnabled(true);
          estDrumT.setEnabled(true);
          labPackB.setEnabled(true);
        }else {
          serviceDateT.setEnabled(false);
          estDrumT.setEnabled(false);
          labPackB.setEnabled(false);
        }

        //once again this is for adding waste materials
        if (isListChanged() || isBulkListChanged()) {
          if (BothHelpObjs.isBlankString(containerShippedOn)) {
            submitB.setEnabled(true);
          }else {
            submitB.setEnabled(false);
          }
        }else {
          submitB.setEnabled(false);
        }
        updateB.setEnabled(false);
    }

    public boolean isListChanged()
    {
        boolean listChanged = false;
        if (wasteOrderTable.getRowCount() != originalContainerId.size())
        {
            listChanged = true;
        }
        for (int i = 0; i < wasteOrderTable.getRowCount(); i++)
        {
            String cId = wasteOrderTable.getModel().getValueAt(i,
                                                               this.CONTAINER_ID_COL).toString();
            if (!originalContainerId.contains(cId))
            {
                listChanged = true;
                break;
            }
        }
        return listChanged;
    }
    public boolean isBulkListChanged()
    {
        boolean listChanged = false;
        if (bulkWasteOrderTable.getRowCount() != originalBulkId.size())
        {
            listChanged = true;
        }
        for (int i = 0; i < bulkWasteOrderTable.getRowCount(); i++)
        {
            String cId = bulkWasteOrderTable.getModel().getValueAt(i,
                                                                   this.CONTAINER_ID_COL).toString();
            if (!originalBulkId.contains(cId))
            {
                listChanged = true;
                break;
            }
        }
        return listChanged;
    }

    void tab_changedPerformed(ChangeEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        Component comp = wasteOrderTab.getSelectedComponent();
        if (comp.equals(shipmentInfoTab)
                && (firstShip || cmis.refreshShipmentInfo))
        {
            shipmentInfoTab.setOrderNo(orderNo);
            shipmentInfoTab.setCmis(cmis);
            firstShip = false;
            cmis.refreshShipmentInfo = false;
        }
        else if (comp.equals(draftInvoiceTab)
                && (firstDraft || cmis.refreshShipmentCost))
        {
            draftInvoiceTab.setShipmentNum(orderNo);
            draftInvoiceTab.setCmis(cmis);
            firstDraft = false;
            cmis.refreshShipmentCost = false;
        }
        else if (comp.equals(vendorInvoiceTab)
                && (firstVendorInvoice || cmis.refreshVendorInvoice))
        {
            vendorInvoiceTab.setShipmentNum(orderNo);
            vendorInvoiceTab.setCmis(cmis);
            firstVendorInvoice = true;
            cmis.refreshVendorInvoice = true;
            /* do not refresh every time tab is select
            firstVendorInvoice = false;
            cmis.refreshVendorInvoice = false;*/
        }
    }

    void setSelectedRow(int x)
    {
        ListSelectionModel lsm = bulkWasteOrderTable.getSelectionModel();
        lsm.setSelectionInterval(x,x);
        bulkWasteOrderTable.scrollRectToVisible(bulkWasteOrderTable.getCellRect(x,0,false));
    }
    void setContainerSelectedRow(int x)
    {
        ListSelectionModel lsm = wasteOrderTable.getSelectionModel();
        lsm.setSelectionInterval(x,x);
        wasteOrderTable.scrollRectToVisible(wasteOrderTable.getCellRect(x,0,false));
    }

    void addMaterialB_actionPerformed(ActionEvent e) {
      //editMaterial();
      if (addMaterialB.getText().startsWith("Add")) {
        addMaterial();
      }else {
        editMaterial();
      }
    }
    void editMaterial() {
      String shipID = "";
      String myFacID = (String)headerInfo.get("FACILITY_ID");
      String myStorage = (String)headerInfo.get("STORAGE_ID");
      String myVendorID = (String)headerInfo.get("VENDOR_ID");
      shipID = (String)headerInfo.get("SHIPMENT_ID");

      if (shipID.equalsIgnoreCase("0")) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("tcmIS can not add material to an empty order.\nPlease check your order_no and try again.");
        gd.setVisible(true);
      }else {
        CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
        WasteAddEditMaterialRequest wmr = new WasteAddEditMaterialRequest(cmis.getMain(),"Waste Material for order: "+this.orderNo,this);
        wmr.setParent(cmis);
        wmr.setOrderNo(orderNo);
        wmr.setShipmentID(shipID);
        wmr.setFacilityID(myFacID);
        wmr.setVendorID(myVendorID);
        wmr.loadIt();
        CenterComponent.centerCompOnScreen(wmr);
        wmr.setVisible(true);
        CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
      }
    }
    void addMaterial() {
      String shipID = "";
      String myFacID = (String)headerInfo.get("FACILITY_ID");
      String myStorage = (String)headerInfo.get("STORAGE_ID");
      String myVendorID = (String)headerInfo.get("VENDOR_ID");
      shipID = (String)headerInfo.get("SHIPMENT_ID");

      if (shipID.equalsIgnoreCase("0")) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg("tcmIS can not add material to an empty order.\nPlease check your order_no and try again.");
        gd.setVisible(true);
      }else {
        CursorChange.setCursor(cmis.getMain(),Cursor.WAIT_CURSOR);
        WasteAddEditMaterialRequest wmr = new WasteAddEditMaterialRequest(cmis.getMain(),"Waste Material for order: "+this.orderNo,this);
        wmr.setParent(cmis);
        wmr.setOrderNo(orderNo);
        wmr.setShipmentID(shipID);
        wmr.setFacilityID(myFacID);
        wmr.setVendorID(myVendorID);
        wmr.loadIt();
        CenterComponent.centerCompOnScreen(wmr);
        //wmr.setVisible(true);   //don't display until ready
        wmr.showWasteMaterialCatalog();
        CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
      }
    }

    void submitB_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);

        if(bulkWasteOrderTable.isEditing()){
          bulkWasteOrderTable.getCellEditor().stopCellEditing();
        }
        plannedAV = new Vector();
        bulkShipIdV = new Vector();
        bulkPrefDateV = new Vector();

        for (int i = 0; i < bulkWasteOrderTable.getRowCount(); i++)
        {
            String plannedA = bulkWasteOrderTable.getModel().getValueAt(i,
                                                                        plannedAmountCol.intValue()).toString();
            if (BothHelpObjs.isBlankString(plannedA))
            {
                GenericDlg gd = new GenericDlg(cmis.getMain(),
                                               "Planned Amount.",true);
                gd.setMsg("Please enter a planned amount for \n each bulk shipment(bottom table row#"+i+1+".)");
                CenterComponent.centerCompOnScreen(gd);
                gd.setVisible(true);
                setSelectedRow(i);
                return;
            }
            else
            {
                try
                {
                    int num = Integer.parseInt(plannedA);
                    plannedAV.addElement(plannedA);
                    bulkShipIdV.addElement(bulkWasteOrderTable.getModel().getValueAt(i,bulkShipIdCol.intValue()).toString());

                    //Checking for the correct bulk pickup date.
                    Bulk_Preferred_date = bulkWasteOrderTable.getModel().getValueAt(i,PrefBulkDate.intValue()).toString();
                    if ((Bulk_Preferred_date.length() == 10))
                    {
                        try
                        {
                            Date d = new Date();
                            Calendar cal = Calendar.getInstance();
                            String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
                            if ((d.parse(Bulk_Preferred_date)) <= (d.parse(cdate)))
                            {
                                GenericDlg gd = new GenericDlg(cmis.getMain(),
                                                               "Bulk Preferred Service Date",
                                                               true);
                                gd.setMsg("Please enter a date in the future\n for Bulk Preferred Pickup Date.");
                                CenterComponent.centerCompOnScreen(gd);
                                gd.setVisible(true);
                                return;
                            }
                            else
                            {
                                bulkPrefDateV.addElement(bulkWasteOrderTable.getModel().getValueAt(i,PrefBulkDate.intValue()).toString());
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                            GenericDlg gd = new GenericDlg(cmis.getMain(),
                                                           "Bulk Preferred Pickup Date",
                                                           true);
                            gd.setMsg("An error occured please try again.");
                            CenterComponent.centerCompOnScreen(gd);
                            gd.setVisible(true);
                            return;
                        }

                    }
                    else
                    {
                        if (!(BothHelpObjs.isBlankString(Bulk_Preferred_date)))
                        {
                            GenericDlg gd = new GenericDlg(cmis.getMain(),
                                                           "Bulk Preferred Pickup Date",true);
                            gd.setMsg("Please enter Date in MM/DD/YYYY format.");
                            CenterComponent.centerCompOnScreen(gd);
                            gd.setVisible(true);
                            return;
                        }
                        else
                        {
                            bulkPrefDateV.addElement(bulkWasteOrderTable.getModel().getValueAt(i,PrefBulkDate.intValue()).toString());
                        }
                    }

                }
                catch(Exception ex)
                {
                    GenericDlg gd = new GenericDlg(cmis.getMain(),
                                                   "Planned Amount.",true);
                    gd.setMsg("Please enter a positive number for planned amount \n for each bulk shipment(bottom table row#"+i+1+".)");
                    CenterComponent.centerCompOnScreen(gd);
                    gd.setVisible(true);
                    setSelectedRow(i);
                    return;
                }
            }
        }

        //Checking for the preffred pickup date to be correct and in the future - Nawaz
        if (wasteOrderTable.getRowCount() > 0 ) {
          Shipment_Id_cont = wasteOrderTable.getModel().getValueAt(0,Shipment_Idcol.intValue()).toString();
        }else {
          Shipment_Id_cont = (String)headerInfo.get("CONTAINER_SHIPMENT_ID");       //4-23-01
        }

        PrefferedPick_date = jPrefTextField1.getText().toString();

        if ((PrefferedPick_date.length() == 10))
        {
            //For Java 1.18 on condor The below code in Java 1.2 Does not work

            try
            {
                Date d = new Date();
                Calendar cal = Calendar.getInstance();
                String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
                if ((d.parse(PrefferedPick_date)) <= (d.parse(cdate)))
                {
                    GenericDlg gd = new GenericDlg(cmis.getMain(),
                                                   "Preferred Pickup Date",
                                                   true);
                    gd.setMsg("Please enter a date in the future\n for Preferred Pickup Date.");
                    CenterComponent.centerCompOnScreen(gd);
                    gd.setVisible(true);
                    return;
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                GenericDlg gd = new GenericDlg(cmis.getMain(),
                                               "Preferred Pickup Date",
                                               true);
                gd.setMsg("An error occured please try again.");
                CenterComponent.centerCompOnScreen(gd);
                gd.setVisible(true);
                return;
            }

        }
        else
        {
            if (!(BothHelpObjs.isBlankString(PrefferedPick_date)))
            {
                GenericDlg gd = new GenericDlg(cmis.getMain(),
                                               "Preferred Pickup Date",true);
                gd.setMsg("Please enter Date in MM/DD/YYYY format.");
                CenterComponent.centerCompOnScreen(gd);
                gd.setVisible(true);
                return;
            }
        }

        //audit lab pack
        if (!auditLabPack()) {
          return;
        }

        if (submitB.getText().equalsIgnoreCase("Submit to Vendor"))
        {
            goSubmitRequest("SUBMIT");
        }
        else
        {
            goSubmitRequest("RESUBMIT");
        }
        cmis.getMain().goWasteOrder(0,orderNo);
    }

    boolean auditLabPack() {
      String serviceDate = BothHelpObjs.makeBlankFromNull(this.serviceDateT.getText().toString());
      //if user enter a date than audit date
      SimpleDateFormat numF = new SimpleDateFormat("MM/dd/yyyy");
      if (!BothHelpObjs.isBlankString(serviceDate)) {
        try {
          Date myDate = numF.parse(serviceDate);
          //now check to make sure that the preferred date is not in the pass
          Date d = new Date();
          Calendar cal = Calendar.getInstance();
          String cdate = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
          if ( d.parse(cdate) > d.parse(serviceDate)) {
            GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
            gd.setMsg("The date you entered is invalid.\n Please check and re-enter another date (MM/DD/YYYY).");
            gd.setVisible(true);
            return false;
          }
        }catch (ParseException eeee) {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg("Invalid Date Format. Please enter a preferred service\n date in the following format(MM/DD/YYYY)");
          gd.setVisible(true);
          return false;
        }

        //now check to see if user enter the right format
        if (!BothHelpObjs.isDateFormatRight(serviceDate)) {
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg("Invalid Date Format. Please enter a preferred service\n date in the following format(MM/DD/YYYY)");
          gd.setVisible(true);
          return false;
        }
      }

      //audit the estimated number of drum -- whole number only
      String tempEstDrum = BothHelpObjs.makeBlankFromNull(this.estDrumT.getText());
      if (!BothHelpObjs.isBlankString(tempEstDrum)) {
        try {
          int numDrum = Integer.parseInt(this.estDrumT.getText());
          if (numDrum < 1) {
            GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
            gd.setMsg("Please enter a positive integer for Estimated # of Drums.");
            gd.setVisible(true);
            return false;
          }
        }catch (Exception ee) {
          ee.printStackTrace();
          GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
          gd.setMsg("Please enter a positive integer for Estimated # of Drums.");
          gd.setVisible(true);
          return false;
        }
      }
      return true;
    }


    void cancelB_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        goSubmitRequest("CANCEL");
        cmis.getMain().goWasteOrder(0,orderNo);
    }

    void goSubmitRequest(String action)
    {
        try
        {
            TcmisHttpConnection cgi = new TcmisHttpConnection("WasteManagement",
                                                              cmis);
            Hashtable query = new Hashtable();
            query.put("USER_ID",cmis.getUserId().toString());
            query.put("ACTION",action);
            query.put("ORDER_NO",(new Integer(orderNo))); //String, Integer
            query.put("PLANNED_AMOUNT_VECTOR",plannedAV);
            query.put("BULK_SHIPMENT_ID",bulkShipIdV);
            query.put("PREFERED_PICKUP_DATE",PrefferedPick_date);
            query.put("SHIPMENT_ID_CONT",Shipment_Id_cont);
            query.put("BULK_PREFERED_PICKUP_DATE",bulkPrefDateV);
            query.put("LAB_PACK_PREFERRED_SERVICE_DATE",BothHelpObjs.makeBlankFromNull(this.serviceDateT.getText()));  //String, String
            query.put("LAB_PACK_DRUM_ESTIMATE",BothHelpObjs.makeBlankFromNull(this.estDrumT.getText()));  //String, String

            Hashtable result = cgi.getResultHash(query);
            if(result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
            }

            Hashtable h = (Hashtable)result.get("RETURN_CODE");
            Boolean b = (Boolean)h.get("SUCCEEDED");
            if(!b.booleanValue())
            {
                GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
                gd.setMsg(h.get("MSG").toString());
                gd.setVisible(true);
            }
            else
            {
                GenericDlg rd = new GenericDlg(cmis.getMain(),
                                               "Action Succeeded",true);
                if(action.equalsIgnoreCase("SUBMIT"))
                {
                    rd.setMsg("Waste Order Request was submitted.");
                }
                else if (action.equalsIgnoreCase("RESUBMIT"))
                {
                    rd.setMsg("Waste Order Request was resubmitted.");
                }
                else if (action.equalsIgnoreCase("CANCEL"))
                {
                    rd.setMsg("Waste Order Request was cancelled.");
                }
                else
                {
                    rd.setMsg("Waste Order Request was updated");
                }
                rd.setVisible(true);
            }
            //  return b.booleanValue();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //  return false;
    }



}

class WasteOrderLoadThread extends Thread
{
    WasteOrder wp;
    String action;
    public WasteOrderLoadThread(WasteOrder wp,String action)
    {
        super();
        this.wp = wp;
        this.action = action;
    }
    public void run()
    {
        if(action.equalsIgnoreCase("load"))
        {
            wp.loadItAction("load");
        }
        else if(action.equalsIgnoreCase("reload"))
        {
            wp.loadItAction("reload");
        }
    }
}

class WasteOrder_wasteOrderTable_mouseAdapter extends java.awt.event.MouseAdapter
    {
    WasteOrder adaptee;
    WasteOrder_wasteOrderTable_mouseAdapter(WasteOrder adaptee)
    {
        this.adaptee = adaptee;
    }
    public void mouseClicked(MouseEvent e)
    {
        adaptee.wasteOrderTable_mouseClicked(e);
    }
    public void mouseReleased(MouseEvent e)
    {
    }
}

class BulkWasteOrder_bulkWasteOrderTable_mouseAdapter extends java.awt.event.MouseAdapter
    {
    WasteOrder adaptee;
    BulkWasteOrder_bulkWasteOrderTable_mouseAdapter(WasteOrder adaptee)
    {
        this.adaptee = adaptee;
    }
    public void mouseClicked(MouseEvent e)
    {
        adaptee.bulkWasteOrderTable_mouseClicked(e);
    }
    public void mouseReleased(MouseEvent e)
    {
    }
}

