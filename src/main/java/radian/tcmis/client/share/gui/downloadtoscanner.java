package radian.tcmis.client.share.gui;

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description



import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.beans.*;
import javax.swing.tree.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import com.borland.jbcl.layout.*;
import javax.swing.table.*;
import javax.swing.event.*;

import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.*;
//import radian.tcmis.client.share.reports.appData.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import java.io.PrintStream;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.*;

//public class downloadtoscanner extends JDialog {
public class downloadtoscanner extends JPanel {

  TableSorter sorter = new TableSorter();

  JPanel downLoadP = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();
  Hashtable templateInfoH = null;
  String prNumber;
  String status;
  String[] colHeadR = {"Scan Work Areas","ID"};
  String[] colHeadL = {"Work Areas","ID"};
  String[] colHeadR1 = {"Scan Cabinets","ID"};
  String[] colHeadL1 = {"Cabinets","ID"};
  Vector cablist = new Vector();
  Vector waclist = new Vector();
  final static int MAXSELECTEDROW = 50;
  CmisApp cmis ;

  boolean foundfile = false;
  JComboBox hubC = new JComboBox();
  StaticJLabel hubL = new StaticJLabel("Hub: ");

  boolean facComboLoaded = false;
  boolean wareComboLoaded = false;
  boolean prefWareLoaded = false;
  Hashtable prefWareHash;


  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  JScrollPane reportListJSP = new JScrollPane();
  JPanel bottomP2 = new JPanel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JPanel reportListP = new JPanel();
  JButton backB = new JButton();
  JScrollPane waclistJSP = new JScrollPane();
  JPanel waclistP = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton forwardB = new JButton();
  BorderLayout borderLayout6 = new BorderLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton backB1 = new JButton();
  JScrollPane cablistJSP = new JScrollPane();
  BorderLayout borderLayout7 = new BorderLayout();
  JPanel reportFieldP = new JPanel();
  JPanel cablistP = new JPanel();
  JButton forwardB1 = new JButton();
  BorderLayout borderLayout5 = new BorderLayout();
  JScrollPane reportFieldJSP = new JScrollPane();
  JPanel bottomP1 = new JPanel();

  GridBagLayout gridBagLayoutT1 = new GridBagLayout();
  JPanel topP1 = new JPanel();

  JButton downB = new JButton();
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  CmisTable wacRightTable;
  CmisTable cableftTable;
  CmisTable cabrightTable;
  CmisTable wacLeftTable;
  CmisTable facLeftTable;
  CmisTable facRightTable;

  JButton searchB = new JButton();

  //facility table
  JPanel facPanel = new JPanel();
  JPanel facLeftP = new JPanel();
  JScrollPane facLeftJSP = new JScrollPane();
  JPanel facRightP = new JPanel();
  JScrollPane facRightJSP = new JScrollPane();
  JButton waB = new JButton();
  JButton facForwardB = new JButton();
  JButton facBackB = new JButton();

  String[] facColHeadL = {"Facilities","ID"};
  String[] facColHeadR = {"Scan Facilities","ID"};
  int[] facColWidths = {50,0};
  BorderLayout borderLayout61 = new BorderLayout();
  BorderLayout borderLayout62 = new BorderLayout();
  GridBagLayout gridBagLayout40 = new GridBagLayout();
  JButton okB1 = new JButton();

  JTabbedPane cabinetMgmtTab = new JTabbedPane();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  uploadtoscanner ults;

  public downloadtoscanner(CmisApp cmis) {
    this.cmis = cmis;
    ults = new uploadtoscanner(cmis);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }



  public void setParent(CmisApp c){
    cmis = c;
  }

  public void loadIt(){
    downloadtoscannerLoadThread x = new downloadtoscannerLoadThread(this);
    x.start();
  }

   void jbInit() throws Exception {
    cabinetMgmtTab.setFont(new java.awt.Font("Dialog", 0, 10));
    //setting for tab panel
    this.setLayout(gridBagLayout1);
    this.setOpaque(true);
    this.add(cabinetMgmtTab,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    cabinetMgmtTab.setTabPlacement(JTabbedPane.TOP);
    //first set download tab
    buildDownLoadTab();  //initailizing all the components for list panel.
    cabinetMgmtTab.addTab("Down Load to Scanner",downLoadP);
    //next set upload tab
    cabinetMgmtTab.addTab("Up Load from Scanner",ults);
    //then set labels tab
    //cabinetMgmtTab.addTab("Labels",null);
    cabinetMgmtTab.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e) {
        tab_changedPerformed(e);
      }
    });

    ClientHelpObjs.makeDefaultColors(this);
  }   // end of method

  void buildDownLoadTab() {
    ImageIcon ssok = ResourceLoader.getImageIcon("images/button/ok.gif");
    ImageIcon ssc = ResourceLoader.getImageIcon("images/button/cancel.gif");
    downLoadP.setLayout(gridBagLayout2);

    bottomP2.setMaximumSize(new Dimension(32767, 1000));
    bottomP2.setMinimumSize(new Dimension(200, 125));
    bottomP2.setPreferredSize(new Dimension(200, 125));
    bottomP2.setLayout(gridBagLayout4);
    reportListP.setPreferredSize(new Dimension(160, 80));
    reportListP.setMinimumSize(new Dimension(160, 80));
    reportListP.setMaximumSize(new Dimension(160, 80));
    reportListP.setLayout(borderLayout2);
    backB.setIcon(ResourceLoader.getImageIcon("images/button/prev1.gif"));
    backB.addActionListener(new java.awt.event.ActionListener()
                                {
                public void actionPerformed(ActionEvent e)
                {
                    backB_actionPerformed(e);
                }
            });
    backB.setMaximumSize(new Dimension(35, 35));
    backB.setMinimumSize(new Dimension(35, 35));
    backB.setPreferredSize(new Dimension(35, 35));
    waclistP.setLayout(borderLayout6);
    waclistP.setMaximumSize(new Dimension(160, 80));
    waclistP.setMinimumSize(new Dimension(160, 80));
    waclistP.setPreferredSize(new Dimension(160, 80));
    forwardB.setMaximumSize(new Dimension(35, 35));
    forwardB.setMinimumSize(new Dimension(35, 35));
    forwardB.setPreferredSize(new Dimension(35, 35));
    forwardB.setIcon(ResourceLoader.getImageIcon("images/button/next1.gif"));
    forwardB.addActionListener(new java.awt.event.ActionListener()
                                   {
                public void actionPerformed(ActionEvent e)
                {
                    forwardB_actionPerformed(e);
                }
            });
    backB1.setPreferredSize(new Dimension(35, 35));
    backB1.setMinimumSize(new Dimension(35, 35));
    backB1.setMaximumSize(new Dimension(35, 35));
    backB1.addActionListener(new java.awt.event.ActionListener()
                                 {
                public void actionPerformed(ActionEvent e)
                {
                    backB1_actionPerformed(e);
                }
            });
    backB1.setIcon(ResourceLoader.getImageIcon("images/button/prev1.gif"));
    reportFieldP.setLayout(borderLayout5);
    reportFieldP.setMaximumSize(new Dimension(160, 80));
    reportFieldP.setMinimumSize(new Dimension(160, 80));
    reportFieldP.setPreferredSize(new Dimension(160, 80));
    cablistP.setPreferredSize(new Dimension(160, 80));
    cablistP.setMinimumSize(new Dimension(160, 80));
    cablistP.setMaximumSize(new Dimension(160, 80));
    cablistP.setLayout(borderLayout7);
    forwardB1.setMaximumSize(new Dimension(35, 35));
    forwardB1.setMinimumSize(new Dimension(35, 35));
    forwardB1.setPreferredSize(new Dimension(35, 35));
    forwardB1.setIcon(ResourceLoader.getImageIcon("images/button/next1.gif"));
    forwardB1.addActionListener(new java.awt.event.ActionListener()
                                    {
                public void actionPerformed(ActionEvent e)
                {
                    forwardB1_actionPerformed(e);
                }
            });
    bottomP1.setLayout(gridBagLayout3);
    bottomP1.setPreferredSize(new Dimension(200, 125));
    bottomP1.setMinimumSize(new Dimension(200, 125));
    bottomP1.setMaximumSize(new Dimension(32767, 1000));
    topP1.setLayout(gridBagLayoutT1);
    topP1.setPreferredSize(new Dimension(50, 50));
    topP1.setMinimumSize(new Dimension(50, 50));
    topP1.setMaximumSize(new Dimension(32767, 1000));
    //facility
    facForwardB.setMaximumSize(new Dimension(35, 35));
    facForwardB.setMinimumSize(new Dimension(35, 35));
    facForwardB.setPreferredSize(new Dimension(35, 35));
    facForwardB.setIcon(ResourceLoader.getImageIcon("images/button/next1.gif"));
    facForwardB.addActionListener(new java.awt.event.ActionListener()
                                    {
                public void actionPerformed(ActionEvent e)
                {
                    facforwardB_actionPerformed(e);
                }
            });
    facBackB.setPreferredSize(new Dimension(35, 35));
    facBackB.setMinimumSize(new Dimension(35, 35));
    facBackB.setMaximumSize(new Dimension(35, 35));
     facBackB.setIcon(ResourceLoader.getImageIcon("images/button/prev1.gif"));
    facBackB.addActionListener(new java.awt.event.ActionListener()
                                 {
                public void actionPerformed(ActionEvent e)
                {
                    facbackB_actionPerformed(e);
                }
            });
    facLeftP.setLayout(borderLayout62);
    facLeftP.setMaximumSize(new Dimension(160, 80));
    facLeftP.setMinimumSize(new Dimension(160, 80));
    facLeftP.setPreferredSize(new Dimension(160, 80));
    facLeftP.setRequestFocusEnabled(true);
    facRightP.setLayout(borderLayout61);
    facRightP.setMaximumSize(new Dimension(160, 80));
    facRightP.setMinimumSize(new Dimension(160, 80));
    facRightP.setPreferredSize(new Dimension(160, 80));
    facPanel.setMaximumSize(new Dimension(32767, 1000));
    facPanel.setMinimumSize(new Dimension(200, 125));
    facPanel.setPreferredSize(new Dimension(200, 125));
    facPanel.setLayout(gridBagLayout40);

    downB.setFont(new java.awt.Font("Dialog", 1, 10));
    downB.setMaximumSize(new Dimension(119, 21));
    downB.setMinimumSize(new Dimension(112, 21));
    downB.setPreferredSize(new Dimension(112, 21));
    downB.setIcon(ssok);
    downB.setText("Download");
    downB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        downB_actionPerformed(e);
      }
    });

    okB1.setIcon(ssc);
    okB1.setText("Close");
    okB1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB1_actionPerformed(e);
      }
    });

    searchB.setFont(new java.awt.Font("Dialog", 1, 10));
    searchB.setMaximumSize(new Dimension(117, 21));
    searchB.setMinimumSize(new Dimension(117, 21));
    searchB.setPreferredSize(new Dimension(117, 21));
    searchB.setText("Get Cabinets");
    searchB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        searchB_actionPerformed(e);
      }
    });

    waB.setFont(new java.awt.Font("Dialog", 1, 10));
    waB.setMaximumSize(new Dimension(117, 21));
    waB.setMinimumSize(new Dimension(117, 21));
    waB.setPreferredSize(new Dimension(117, 21));
    waB.setText("Get Work Area");
    waB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        waB_actionPerformed(e);
      }
    });
    hubC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hubC_actionPerformed(e);
      }
    });

    hubC.setFont(new java.awt.Font("Dialog", 1, 10));
    hubC.setMinimumSize(new Dimension(180, 21));
    hubC.setPreferredSize(new Dimension(180, 23));
    downLoadP.add(hubL,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 100, 5, 5), 0, 0));
    downLoadP.add(hubC,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 130, 5, 5), 0, 0));
    downLoadP.add(downB,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 400, 5, 5), 0, 0));

    /*
    topP1.add(hubL,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    topP1.add(hubC,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    topP1.add(downB,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    */
    //facility
    downLoadP.add(facPanel,        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 130, 0, 0), 0, 0));
    facPanel.add(facLeftP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    facLeftP.add(facLeftJSP, BorderLayout.CENTER);
    facPanel.add(facForwardB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 10, 40, 10), 0, 0));
    facPanel.add(facBackB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(55, 10, 10, 10), 0, 0));
    facPanel.add(facRightP, new GridBagConstraints2(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    facRightP.add(facRightJSP, BorderLayout.CENTER);

    //work area
    downLoadP.add(waB,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
    downLoadP.add(bottomP2,       new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 130, 0, 0), 0, 0));
    bottomP2.add(waclistP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    waclistP.add(waclistJSP, BorderLayout.CENTER);
    bottomP2.add(forwardB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 10, 40, 10), 0, 0));
    bottomP2.add(backB, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(55, 10, 10, 10), 0, 0));
    bottomP2.add(reportListP, new GridBagConstraints2(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    reportListP.add(reportListJSP, BorderLayout.CENTER);


    //cabinets
    downLoadP.add(searchB,     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 5), 0, 0));
    downLoadP.add(bottomP1,      new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 130, 0, 0), 0, 0));
    bottomP1.add(cablistP, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    cablistP.add(cablistJSP, BorderLayout.CENTER);
    bottomP1.add(forwardB1, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 10, 40, 10), 0, 0));
    bottomP1.add(backB1, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(55, 10, 10, 10), 0, 0));
    bottomP1.add(reportFieldP, new GridBagConstraints2(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
    reportFieldP.add(reportFieldJSP, BorderLayout.CENTER);

    downLoadP.validate();

    DbTableModel modelL2 = new DbTableModel(facColHeadL);
    facLeftTable = new CmisTable(modelL2);
    facLeftJSP.getViewport().setView(facLeftTable);
    DbTableModel modelR2 = new DbTableModel(facColHeadR);
    facRightTable = new CmisTable(modelR2);
    facRightJSP.getViewport().setView(facRightTable);

    DbTableModel modelL = new DbTableModel(colHeadL);
    wacLeftTable = new CmisTable(modelL);
    waclistJSP.getViewport().setView(wacLeftTable);
    DbTableModel modelR = new DbTableModel(colHeadR);
    wacRightTable = new CmisTable(modelR);
    reportListJSP.getViewport().setView(wacRightTable);

    DbTableModel modelL1 = new DbTableModel(colHeadL1);
    cableftTable = new CmisTable(modelL1);
    cablistJSP.getViewport().setView(cableftTable);
    DbTableModel modelR1 = new DbTableModel(colHeadR1);
    cabrightTable = new CmisTable(modelR1);
    reportFieldJSP.getViewport().setView(cabrightTable);

    bottomP1.validate();
  }  //end of method

  void tab_changedPerformed(ChangeEvent e) {
    /*
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    Component comp = cabinetMgmtTab.getSelectedComponent();
    if (comp.equals(upLoadTab)) {
            shipmentInfoTab.setOrderNo(orderNo);
            shipmentInfoTab.setCmis(cmis);
            firstShip = false;
            cmis.refreshShipmentInfo = false;
    }
    */
  }  //end of method


  void loadItAction()
  {

    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    //System.out.println("Here");
    try
        {
            ClientHelpObjs.setEnabledContainer(this,false);
            TcmisHttpConnection cgi = new TcmisHttpConnection("scannerDownload",cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","GET_INITIAL_INFO");
            query.put("USER_ID",cmis.getUserId());
            Hashtable result = cgi.getResultHash(query);
            if (result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
                ClientHelpObjs.setEnabledContainer(this,true);
                return;
            }
            templateInfoH = (Hashtable)result.get("INITIAL_INFO");

            Vector tmpVhub = (Vector)templateInfoH.get("HUB_NAMES");
            hubC = ClientHelpObjs.loadChoiceFromVector(hubC,tmpVhub);

            //buildTable();
            buildfactable();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            GenericDlg.showAccessDenied(cmis.getMain());
            ClientHelpObjs.setEnabledContainer(this,true);
            return;
        }

        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        ClientHelpObjs.setEnabledContainer(this,true);
  }

  void closeWindow() {
    this.setVisible(false);
  }

  void searchB_actionPerformed(ActionEvent e)
  {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    removecabinets();
    try
        {
            ClientHelpObjs.setEnabledContainer(this,false);
            Hashtable resultcab = new Hashtable();
            Vector mywacidDataV=new Vector();
            if ( wacRightTable.getRowCount() == 0)
            {
              GenericDlg g=new GenericDlg( cmis.getMain(),"Select Work Area(s)",true );
              g.setMsg( " Please select Work Area(s) for which you want to get Cabinets." );
              g.setVisible( true );
            }
            else
            {
              for ( int i=0; i < wacRightTable.getRowCount(); i++ )
              {
                String tmpwacidData=wacRightTable.getModel().getValueAt( i,1 ).toString();
                mywacidDataV.addElement( tmpwacidData );
              }

              TcmisHttpConnection cgi=new TcmisHttpConnection( "scannerDownload",cmis );
              Hashtable query=new Hashtable();
              query.put( "ACTION","GET_CABINET_INFO" );
              query.put( "USER_ID",cmis.getUserId() );
              query.put( "WAC_IDS",mywacidDataV );
              resultcab=cgi.getResultHash( query );
              if ( resultcab == null )
              {
                GenericDlg.showAccessDenied( cmis.getMain() );
                ClientHelpObjs.setEnabledContainer( this,true );
                return;
              }
            }

            Hashtable tmpcabHas = ( Hashtable ) resultcab.get( "CABINET_INFO" );
            Vector tmpVcabId = ( Vector ) tmpcabHas.get( "CAB_IDS" );
            Vector tmpVcabIdesc = ( Vector ) tmpcabHas.get( "CAB_DESC" );

            //if no cabinets found display message no reconrds found
            if ( tmpVcabId.size() == 0)
            {
              GenericDlg g=new GenericDlg( cmis.getMain(),"No Cabinets",true );
              g.setMsg( " No Cabinets were found for this Selection." );
              g.setVisible( true );
            }
            else
            {
              for ( int j=0; j < tmpVcabId.size(); j++ )
             {
               String tmpData2= ( String ) tmpVcabId.elementAt( j );
               String tmpData3= ( String ) tmpVcabIdesc.elementAt( j );
               Object[] oa=new Object[colHeadR.length];
               oa[0]=tmpData3;
               oa[1]=tmpData2;

               addRowToTable( cableftTable,oa,cableftTable.getSelectedRow() );
             }

            }
        }
        catch (Exception e11)
        {
            e11.printStackTrace();
            GenericDlg.showAccessDenied(cmis.getMain());
            ClientHelpObjs.setEnabledContainer(this,true);
            return;
        }

        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        ClientHelpObjs.setEnabledContainer(this,true);
  }

  void downB_actionPerformed(ActionEvent e) {
       ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
       runThread();
    }

    void runThread(){
       downloadtoscannerThread st = new downloadtoscannerThread(this);
       st.start();
    }

  void dodownloadstuff( )
  {
    CursorChange.setCursor( this,Cursor.WAIT_CURSOR );
    boolean scrpitresult = true;
    try
    {
      ClientHelpObjs.setEnabledContainer( this,false );
      Hashtable downresult=new Hashtable();

      Vector mywacidDataV=new Vector();
      if ( cabrightTable.getRowCount() == 0 )
      {
        GenericDlg g=new GenericDlg( cmis.getMain(),"Select Cabinet(s)",true );
        g.setMsg( " Please select Cabinet(s) that you want to download to the scanner." );
        g.setVisible( true );
      }
      else
      {
        for ( int i=0; i < wacRightTable.getRowCount(); i++ )
        {
          String tmpwacidData=wacRightTable.getModel().getValueAt( i,1 ).toString();
          mywacidDataV.addElement( tmpwacidData );
        }

        Vector mycabidDataV=new Vector();
        for ( int i=0; i < cabrightTable.getRowCount(); i++ )
        {
          String tmpwacidData=cabrightTable.getModel().getValueAt( i,1 ).toString();
          mycabidDataV.addElement( tmpwacidData );
        }

        TcmisHttpConnection cgi=new TcmisHttpConnection( "scannerDownload",cmis );
        Hashtable query=new Hashtable();
        query.put( "ACTION","GET_DOWNLOAD_INFO" );
        query.put( "USER_ID",cmis.getUserId() );
        query.put( "WAC_IDS",mywacidDataV );
        query.put( "CAB_IDS",mycabidDataV );
        String selectedhub = hubC.getSelectedItem().toString();
        query.put( "SELECTED_HUB",selectedhub );
        downresult=cgi.getResultHash( query );
        if ( downresult == null )
        {
          GenericDlg.showAccessDenied( cmis.getMain() );
          CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
          ClientHelpObjs.setEnabledContainer( this,true );
          return;
        }

        Hashtable tmpcabHas= ( Hashtable ) downresult.get( "DOWNLOAD_INFO" );
        Vector tmpVcabId= ( Vector ) tmpcabHas.get( "CAB_BINS" );
        StringBuffer Msg=new StringBuffer();

        try
        {
          int i=0;
          for ( int loop=0; loop < tmpVcabId.size(); loop++ )
          {
            Hashtable hD=new Hashtable();
            hD= ( Hashtable ) tmpVcabId.elementAt( loop );

            String bin_id=hD.get( "BIN_ID1" ).toString();
            String cabinet_id=hD.get( "CABINET_ID1" ).toString();
            String itemid= hD.get( "ITEM_ID1" ).toString();
            String compid= hD.get( "COMPANY_ID" ).toString();

            Msg.append( "" + cabinet_id + "," );
            Msg.append( "" + bin_id + "," );
            Msg.append( "" + itemid + "," );
            Msg.append( "" + compid + "" );
            Msg.append((char)(13));
            Msg.append((char)(10));
          }
        }
        catch ( Exception e234 )
        {
          e234.printStackTrace();
          scrpitresult = false;
        }

        String file="";
        PrintStream ps=null;

        file="c:\\scannerdownload\\cabmgmt.csv";
        try
        {
          ps=new PrintStream( new FileOutputStream( file ),true );

          String contents="";
          contents+=Msg;
          byte outbuf[];
          outbuf=contents.getBytes();
          ps.write( outbuf );
          ps.close();
        }
        catch ( Exception writee )
        {
          writee.printStackTrace();
          scrpitresult = false;
        }

        /*All cabinet bins*/
        Vector tmpVAllcabId= ( Vector ) tmpcabHas.get( "ALL_CAB_BINS" );
        StringBuffer allMsg=new StringBuffer();
        System.out.println(tmpVAllcabId);

        try
        {
          int i=0;
          for ( int loop=0; loop < tmpVAllcabId.size(); loop++ )
          {
            Hashtable hD=new Hashtable();
            hD= ( Hashtable ) tmpVAllcabId.elementAt( loop );

            String bin_id=hD.get( "BIN_ID1" ).toString();
            String cabinet_id=hD.get( "CABINET_ID1" ).toString();
            String itemid= hD.get( "ITEM_ID1" ).toString();
            String compid= hD.get( "COMPANY_ID" ).toString();
            String status= hD.get( "STATUS" ).toString();
            String countType= hD.get( "COUNT_TYPE" ).toString();

            allMsg.append( "" + cabinet_id + "," );
            allMsg.append( "" + bin_id + "," );
            allMsg.append( "" + itemid + "," );
            allMsg.append( "" + compid + "," );
            allMsg.append( "" + status + "," );
            allMsg.append( "" + countType + "" );
            allMsg.append((char)(13));
            allMsg.append((char)(10));
          }
        }
        catch ( Exception e234 )
        {
          e234.printStackTrace();
          scrpitresult = false;
        }

        String allfile="";
        PrintStream allps=null;

        allfile="c:\\scannerdownload\\allcabbins.csv";
        try
        {
          allps=new PrintStream( new FileOutputStream( allfile ),true );

          String contents="";
          contents+=allMsg;
          byte outbuf[];
          outbuf=contents.getBytes();
          allps.write( outbuf );
          allps.close();
        }
        catch ( Exception writee )
        {
          writee.printStackTrace();
          scrpitresult = false;
        }

        Vector tmprecpids= ( Vector ) tmpcabHas.get( "RECEIPT_IDS" );
        StringBuffer Msgrec=new StringBuffer();

        try
        {
          int i=0;
          if (tmprecpids.size() > 0)
          {
            for ( int loop=0; loop < tmprecpids.size(); loop++ )
            {
              Hashtable hD=new Hashtable();
              hD= ( Hashtable ) tmprecpids.elementAt( loop );

              String itemid1=hD.get( "ITEM_ID1" ).toString();
              String recptid1=hD.get( "RECEIPT_ID1" ).toString();

              Msgrec.append( "" + recptid1 + "," );
              Msgrec.append( "" + itemid1 + "" );
              Msgrec.append((char)(13));
              Msgrec.append((char)(10));
            }
          }
        }
        catch ( Exception e234 )
        {
          e234.printStackTrace();
          scrpitresult = false;
        }

        file="C:\\scannerdownload\\recptitems.csv";
        try
        {
          ps=new PrintStream( new FileOutputStream( file ),true );

          String contents="";
          contents+=Msgrec;
          byte outbuf[];
          outbuf=contents.getBytes();
          ps.write( outbuf );
          ps.close();
        }
        catch ( Exception writee )
        {
          writee.printStackTrace();
          scrpitresult = false;
        }


       Vector tmpLegacyRecpids= ( Vector ) tmpcabHas.get( "LEGACY_RECEIPT_IDS" );
       StringBuffer msgLeagacyRec=new StringBuffer();

        try
        {
          int i=0;
          if (tmpLegacyRecpids.size() > 0)
          {
            for ( int loop=0; loop < tmpLegacyRecpids.size(); loop++ )
            {
              Hashtable hD=new Hashtable();
              hD= ( Hashtable ) tmpLegacyRecpids.elementAt( loop );

              String itemid1=hD.get( "ITEM_ID1" ).toString();
              String recptid1=hD.get( "RECEIPT_ID1" ).toString();
              String legacyReceiptId=hD.get( "CUSTOMER_RECEIPT_ID" ).toString();

              msgLeagacyRec.append( "" + legacyReceiptId + "," );
              msgLeagacyRec.append( "" + recptid1 + "," );
              msgLeagacyRec.append( "" + itemid1 + "" );
              msgLeagacyRec.append((char)(13));
              msgLeagacyRec.append((char)(10));
            }
          }
        }
        catch ( Exception e2345 )
        {
          e2345.printStackTrace();
          scrpitresult = false;
        }

        file="C:\\scannerdownload\\midrcpt.csv";
        try
        {
          ps=new PrintStream( new FileOutputStream( file ),true );

          String contents="";
          contents+=msgLeagacyRec;
          byte outbuf[];
          outbuf=contents.getBytes();
          ps.write( outbuf );
          ps.close();
        }
        catch ( Exception writee )
        {
          writee.printStackTrace();
          scrpitresult = false;
        }

      Vector tmppersoids= ( Vector ) tmpcabHas.get( "LOGON_IDS" );
      StringBuffer Msglogon=new StringBuffer();

      try
      {
        int i=0;
        if (tmppersoids.size() > 0)
        {
          for ( int loop=0; loop < tmppersoids.size(); loop++ )
          {
            Hashtable hD=new Hashtable();
            hD= ( Hashtable ) tmppersoids.elementAt( loop );

            String loginoid=hD.get( "LOGON_ID" ).toString();
            String persoid=hD.get( "PERSONNEL_ID" ).toString();

            Msglogon.append( "" + loginoid + "," );
            Msglogon.append( "" + persoid + "" );
            Msglogon.append((char)(13));
            Msglogon.append((char)(10));
          }
        }
      }
      catch ( Exception e234 )
      {
        e234.printStackTrace();
        scrpitresult = false;
      }

      file="C:\\scannerdownload\\personnelids.csv";
      try
      {
        ps=new PrintStream( new FileOutputStream( file ),true );

        String contents="";
        contents+=Msglogon;
        byte outbuf[];
        outbuf=contents.getBytes();
        ps.write( outbuf );
        ps.close();
      }
      catch ( Exception writee )
      {
        writee.printStackTrace();
        scrpitresult = false;
      }

        StringBuffer contents= ( StringBuffer ) tmpcabHas.get( "DOWNLOADSCRIPT" );
        file="C:\\MCL\\Terminal\\mcllink\\mcllink.cmd";
        try
        {
          ps=new PrintStream( new FileOutputStream( file ),true );
          byte outbuf[];
          outbuf=contents.toString().getBytes();
          ps.write( outbuf );
          ps.close();
        }
        catch ( Exception writee )
        {
          writee.printStackTrace();
          scrpitresult = false;
        }

        /*
        GenericDlg g1=new GenericDlg( cmis.getMain(),"Select Cabinet(s)",true );
        g1.setMsg( "1. Place scanner in cradle.\n2. Turn on scanner.\n3. Logon to the terminal if necessary.\n4. Press CLR until the MAIN MENU is displayed.\n5. Select File Xfer from the main menu.\n6. Press Enter on the scanner.\n7. Click the OK button when the scanner displays 'Waiting...'" );
        g1.setVisible( true );
        */
        ConfirmNewDlg cDlg = new ConfirmNewDlg(cmis.getMain(),"Select Cabinet(s)",true );
        cDlg.setMsg( "1. Place scanner in cradle.\n2. Turn on scanner.\n3. Logon to the terminal if necessary.\n4. Press CLR until the MAIN MENU is displayed.\n5. Select File Xfer from the main menu.\n6. Press Enter on the scanner.\n7. Click the OK button when the scanner displays 'Waiting...'" );
        cDlg.setVisible( true );
        if (!cDlg.yesFlag) {
          CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
          ClientHelpObjs.setEnabledContainer( this,true );
          return;
        }


        scrpitresult = calllinkexe();
        checkforerrfile();
        if ( foundfile )
        {
          scrpitresult=false;
        }
        else
        {
          scrpitresult=true;
        }

        GenericDlg g=new GenericDlg( cmis.getMain(),"Download to Scanner",true );
        if (scrpitresult)
        {
          g.setMsg( " Download to Scanner Succesfull" );
        }
        else
        {
          g.setMsg( " Download to Scanner Failed. Please try again" );
        }
        g.setVisible( true );
      }//End of if for cabinet count
    }
    catch ( Exception e11 )
    {
      e11.printStackTrace();
      GenericDlg.showAccessDenied( cmis.getMain() );
      CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
      ClientHelpObjs.setEnabledContainer( this,true );
      return;
    }

    CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
    ClientHelpObjs.setEnabledContainer( this,true );
  }

  boolean calllinkexe()
  {
    boolean cllmcl=true;
    try
    {
      //String[] args = new String[4];
      String osName=System.getProperty( "os.name" );
      String[] cmd=new String[3];
      System.out.println( osName );

      {
        cmd[0]="cmd.exe";
        cmd[1]="/C";
        //cmd[2]="start c:\\awaztest.bat";
        //cmd[2]="ftp -s:c:\\baemsds.scr";
        cmd[2]="C:\\MCL\\Terminal\\mcllink\\mcllink.exe";

      }

      Runtime rt=Runtime.getRuntime();
      System.out.println( "Execing " + cmd[0] + " " + cmd[1]
                          + " " + cmd[2] );
      Process proc=rt.exec( cmd );
      // any error message?
      StreamGobbler1 errorGobbler=new StreamGobbler1( proc.getErrorStream(),"ERROR" );

      // any output?
      StreamGobbler1 outputGobbler=new StreamGobbler1( proc.getInputStream(),"OUTPUT" );

      // kick them off
      errorGobbler.start();
      outputGobbler.start();

      // any error???
      int exitVal=proc.waitFor();
      System.out.println( "ExitValue: " + exitVal );
      proc.destroy();
    }
    catch ( Throwable t )
    {
      t.printStackTrace();
      cllmcl=false;
    }
    return cllmcl;
  }

  void checkforerrfile()
  {
    boolean founderrfile=false;
    String erromsffromfile="";

    try
    {
      //Looking for the error file
      BufferedReader in=new BufferedReader( new FileReader( "c:\\scannerdownload\\LINKERR.TXT" ) );
      String ln=new String();
      StringTokenizer st=null;
      while ( ( ln=in.readLine() ) != null )
      {
        System.out.println( "Line:" + ln );
        erromsffromfile+=ln;
      }
      founderrfile=true;
      foundfile =true;
      in.close();
    }
    catch ( FileNotFoundException efile )
    {
      efile.printStackTrace();
    }
    catch ( IOException efile )
    {
      efile.printStackTrace();
    }
    finally
    {
    }

    if ( founderrfile )
    {
      TcmISMessageDlg cfd=new TcmISMessageDlg( cmis.getMain(),"TcmIS Messages",true );
      cfd.setMsg( erromsffromfile );
      cfd.noB.setText( "Cancel" );

      cfd.setSize( new Dimension( 450,200 ) );

      cfd.setVisible( true );
      if ( !cfd.getConfirmationFlag() )
      {
        ClientHelpObjs.setEnabledContainer( this,true );
        CursorChange.setCursor( this,Cursor.DEFAULT_CURSOR );
        //return founderrfile;
      }
      else
      {
        System.out.println("Calling Download Stuff again    ");
        calllinkexe();
        checkforerrfile();
        //return founderrfile;
      }
    }
    else
    {
      foundfile = false;
    }
    //return founderrfile;
  }

  void okB1_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }


  void forwardB1_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (cableftTable.getSelectedRow() < 0)
        {
            GenericDlg g = new GenericDlg(cmis.getMain(),"Select row(s)",true);
            g.setMsg(" Please select row(s) that you want to move.");
            g.setVisible(true);
        }
        else
        {
            int[] selectedRows = new int[MAXSELECTEDROW];
            selectedRows = cableftTable.getSelectedRows();
            Vector mycabDataV=new Vector();
            Vector mycabidDataV=new Vector();

            //save data
            for (int i = 0; i < selectedRows.length; i++)
            {
              String tmpwacData=cableftTable.getModel().getValueAt( selectedRows[i],0 ).toString();
              String tmpwacidData=cableftTable.getModel().getValueAt( selectedRows[i],1 ).toString();

              mycabDataV.addElement( tmpwacData );
              mycabidDataV.addElement( tmpwacidData );
            }
            //System.out.println("== forwardB1_actionPerformed data: "+mycabDataV);
            //removing data from table
            for (int j = 0; j < selectedRows.length; j++ )
            {
                //System.out.println("== deleting selected rows: "+selectedRows[j]);
                removeRowFromTable(cableftTable,selectedRows[j]-j);
            }
            //move data to table
            for (int k = 0; k < mycabDataV.size(); k++)
            {
              String tmpData2= ( String ) mycabDataV.elementAt( k );
              String tmpData3= ( String ) mycabidDataV.elementAt( k );
              Object[] oa=new Object[colHeadR.length];
              oa[0]=tmpData2;
              oa[1]=tmpData3;

              addRowToTable(cabrightTable,oa,cabrightTable.getSelectedRow());
            }
        }
    }

    void backB1_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (cabrightTable.getSelectedRow() < 0)
        {
            GenericDlg g = new GenericDlg(cmis.getMain(),"Select a row",true);
            g.setMsg(" Please select row(s) that you want to move.");
            g.setVisible(true);
        }
        else
        {
            int[] selectedRows = new int[MAXSELECTEDROW];
            selectedRows = cabrightTable.getSelectedRows();
            //getting the data from table
            Vector mycabDataV=new Vector();
            Vector mycabidDataV=new Vector();

            for (int i = 0; i < selectedRows.length; i++)
            {
              String tmpwacData=cabrightTable.getModel().getValueAt( selectedRows[i],0 ).toString();
              String tmpwacidData=cabrightTable.getModel().getValueAt( selectedRows[i],1 ).toString();

              mycabDataV.addElement( tmpwacData );
              mycabidDataV.addElement( tmpwacidData );
            }
            //remove data from table
            for (int k = 0; k < selectedRows.length; k++)
            {
                removeRowFromTable(cabrightTable,selectedRows[k]-k);
            }
            //now move data to
            for (int j = 0; j < mycabDataV.size(); j++)
            {
              String tmpData2= ( String ) mycabDataV.elementAt( j );
              String tmpData3= ( String ) mycabidDataV.elementAt( j );
              Object[] oa=new Object[colHeadR.length];
              oa[0]=tmpData2;
              oa[1]=tmpData3;

              addRowToTable(cableftTable,oa,cableftTable.getSelectedRow());
            }
        }
    }

    void forwardB_actionPerformed(ActionEvent e)
      {
          ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
          removecabinets();
          if (wacLeftTable.getSelectedRow() < 0)
          {
              GenericDlg g = new GenericDlg(cmis.getMain(),"Select row(s)",true);
              g.setMsg(" Please select row(s) that you want to move.");
              g.setVisible(true);
          }
          else
          {
              int[] selectedRows = new int[MAXSELECTEDROW];
              selectedRows = wacLeftTable.getSelectedRows();
              Vector mywacDataV=new Vector();
              Vector mywacidDataV=new Vector();

              //save data
              for (int i = 0; i < selectedRows.length; i++)
              {
                String tmpwacData=wacLeftTable.getModel().getValueAt( selectedRows[i],0 ).toString();
                String tmpwacidData=wacLeftTable.getModel().getValueAt( selectedRows[i],1 ).toString();

                mywacDataV.addElement( tmpwacData );
                mywacidDataV.addElement( tmpwacidData );
              }
              //System.out.println("==forwardB_actionPerformed data: "+mywacDataV);
              //removing data from table
              for (int j = 0; j < selectedRows.length; j++ )
              {
                  //System.out.println("== deleting selected rows: "+selectedRows[j]);
                  removeRowFromTable(wacLeftTable,selectedRows[j]-j);
              }
              //move data to table
              for (int k = 0; k < mywacDataV.size(); k++)
              {
                String tmpData2= ( String ) mywacDataV.elementAt( k );
                String tmpData3= ( String ) mywacidDataV.elementAt( k );
                Object[] oa=new Object[colHeadR.length];
                oa[0]=tmpData2;
                oa[1]=tmpData3;

                addRowToTable(wacRightTable,oa,wacRightTable.getSelectedRow());
              }
          }
      }

      void backB_actionPerformed(ActionEvent e)
      {
          ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
          removecabinets();
          if (wacRightTable.getSelectedRow() < 0)
          {
              GenericDlg g = new GenericDlg(cmis.getMain(),"Select a row",true);
              g.setMsg(" Please select row(s) that you want to move.");
              g.setVisible(true);
          }
          else
          {
              int[] selectedRows = new int[MAXSELECTEDROW];
              selectedRows = wacRightTable.getSelectedRows();
              //getting the data from table
              Vector mywacDataV=new Vector();
              Vector mywacidDataV=new Vector();
              for (int i = 0; i < selectedRows.length; i++)
              {
                String tmpwacData=wacRightTable.getModel().getValueAt( selectedRows[i],0 ).toString();
                String tmpwacidData=wacRightTable.getModel().getValueAt( selectedRows[i],1 ).toString();

                mywacDataV.addElement( tmpwacData );
                mywacidDataV.addElement( tmpwacidData );
              }
              //remove data from table
              for (int k = 0; k < selectedRows.length; k++)
              {
                  removeRowFromTable(wacRightTable,selectedRows[k]-k);
              }
              //now move data to
              for (int j = 0; j < mywacDataV.size(); j++)
              {
                String tmpData2= ( String ) mywacDataV.elementAt( j );
                String tmpData3= ( String ) mywacidDataV.elementAt( j );
                Object[] oa=new Object[colHeadR.length];
                oa[0]=tmpData2;
                oa[1]=tmpData3;

                addRowToTable(wacLeftTable,oa,wacLeftTable.getSelectedRow());
              }
          }
      }

      void facforwardB_actionPerformed( ActionEvent e )
      {
        ClientHelpObjs.playSound( ClientHelpObjs.SOUND_BUTTON_ACTION );
        removeworkandcabinets();
        if ( facLeftTable.getSelectedRow() < 0 )
        {
          GenericDlg g=new GenericDlg( cmis.getMain(),"Select row(s)",true );
          g.setMsg( " Please select row(s) that you want to move." );
          g.setVisible( true );
        }
        else
        {
          int[] selectedRows=new int[MAXSELECTEDROW];
          selectedRows=facLeftTable.getSelectedRows();
          Vector myfacDataV=new Vector();
          Vector myfacidDataV=new Vector();

          for ( int i=0; i < selectedRows.length; i++ )
          {
            String tmpfacData=facLeftTable.getModel().getValueAt( selectedRows[i],0 ).toString();
            String tmpfacidData=facLeftTable.getModel().getValueAt( selectedRows[i],1 ).toString();
            //System.out.println("== selected rows: "+tmpfacidData);
            myfacDataV.addElement( tmpfacData );
            myfacidDataV.addElement( tmpfacidData );
          }
          //System.out.println( "==forwardB_actionPerformed data: " + myfacidDataV );
          //removing data from table
          for ( int j=0; j < selectedRows.length; j++ )
          {
            //System.out.println("== deleting selected rows: "+selectedRows[j]);
            removeRowFromTable( facLeftTable,selectedRows[j] - j );
          }
          //move data to table
          for ( int k=0; k < myfacDataV.size(); k++ )
          {
            String tmpData2= ( String ) myfacDataV.elementAt( k );
            String tmpData3= ( String ) myfacidDataV.elementAt( k );
            Object[] oa=new Object[colHeadR.length];
            oa[0]=tmpData2;
            oa[1]=tmpData3;
            addRowToTable( facRightTable,oa,facRightTable.getSelectedRow() );
          }
        }
      }

      void facbackB_actionPerformed( ActionEvent e )
      {
        ClientHelpObjs.playSound( ClientHelpObjs.SOUND_BUTTON_ACTION );
        removeworkandcabinets();
        if ( facRightTable.getSelectedRow() < 0 )
        {
          GenericDlg g=new GenericDlg( cmis.getMain(),"Select a row",true );
          g.setMsg( " Please select row(s) that you want to move." );
          g.setVisible( true );
        }
        else
        {
          int[] selectedRows=new int[MAXSELECTEDROW];
          selectedRows=facRightTable.getSelectedRows();
          //getting the data from table
          Vector myfacDataV=new Vector();
          Vector myfacidDataV=new Vector();

          for ( int i=0; i < selectedRows.length; i++ )
          {
            String tmpfacData=facRightTable.getModel().getValueAt( selectedRows[i],0 ).toString();
            String tmpfacidData=facRightTable.getModel().getValueAt( selectedRows[i],1 ).toString();

            myfacDataV.addElement( tmpfacData );
            myfacidDataV.addElement( tmpfacidData );
          }
          //remove data from table
          for ( int k=0; k < selectedRows.length; k++ )
          {
            removeRowFromTable( facRightTable,selectedRows[k] - k );
          }
          //now move data to
          for ( int j=0; j < myfacDataV.size(); j++ )
          {
            String tmpData2= ( String ) myfacDataV.elementAt( j );
            String tmpData3= ( String ) myfacidDataV.elementAt( j );
            Object[] oa=new Object[colHeadR.length];
            oa[0]=tmpData2;
            oa[1]=tmpData3;

            addRowToTable( facLeftTable,oa,facLeftTable.getSelectedRow() );
          }
        }
      }

    void removeworkandcabinets()
    {
      int waclcount = wacLeftTable.getRowCount();
      int waclcount1 = waclcount;
      for ( int k=0; k < waclcount; k++ )
      {
        waclcount1--;
        removeRowFromTable( wacLeftTable,waclcount1 );
      }

      int wacrcount = wacRightTable.getRowCount();
      waclcount1 = wacrcount;
      for ( int k=0; k < wacrcount; k++ )
      {
        waclcount1--;
        removeRowFromTable( wacRightTable,waclcount1 );
      }

      int cablcount = cableftTable.getRowCount();
      waclcount1 = cablcount;
      for ( int k=0; k < cablcount; k++ )
      {
        waclcount1--;
        removeRowFromTable( cableftTable,waclcount1 );
      }

      int cabrcount = cabrightTable.getRowCount();
      waclcount1 = cabrcount;
      for ( int k=0; k < cabrcount; k++ )
      {
        waclcount1--;
        removeRowFromTable( cabrightTable,waclcount1 );
      }
    }

    void removecabinets()
    {
      int cablcount = cableftTable.getRowCount();
      int waclcount1 = cablcount;
      waclcount1 = cablcount;
      for ( int k=0; k < cablcount; k++ )
      {
        waclcount1--;
        removeRowFromTable( cableftTable,waclcount1 );
      }

      int cabrcount = cabrightTable.getRowCount();
      waclcount1 = cabrcount;
      for ( int k=0; k < cabrcount; k++ )
      {
        waclcount1--;
        removeRowFromTable( cabrightTable,waclcount1 );
      }
    }

    void removeRowFromTable(CmisTable myTable,int row)
    {
        DbTableModel model = (DbTableModel)myTable.getModel();
        model.removeRow(row);
    }

    void addRowToTable(CmisTable myTable,Object[] data, int selectedRow)
    {
        DbTableModel model = (DbTableModel)myTable.getModel();

        if (selectedRow >= 0) {
          model.insertRow(selectedRow+1,data);
        }else {
          model.addRow(data);
        }
    }

  void hubC_actionPerformed(ActionEvent e)
  {
    buildfactable();
  }

  private void buildfactable()
  {
    Vector tmpVhub = (Vector)templateInfoH.get("HUB_NAMES");
    Hashtable alldata = (Hashtable)templateInfoH.get("All_DATA");

    String selectedhub = hubC.getSelectedItem().toString();
    int selectedhubinx = hubC.getSelectedIndex();
    //System.out.println("Here in Hub Changed" +selectedhub+"     "+selectedhubinx);

    facLeftJSP.getViewport().remove(facLeftTable);
    facRightJSP.getViewport().remove(facRightTable);

    waclistJSP.getViewport().remove(wacLeftTable);
    reportListJSP.getViewport().remove(wacRightTable);

    cablistJSP.getViewport().remove(cableftTable);
    reportFieldJSP.getViewport().remove(cabrightTable);

    DbTableModel facModelR = new DbTableModel(facColHeadR);
    DbTableModel modelR = new DbTableModel(colHeadR);
    DbTableModel modelR1 = new DbTableModel(colHeadR1);
    DbTableModel facModelL = new DbTableModel(facColHeadL);
    DbTableModel modelL = new DbTableModel(colHeadL);
    DbTableModel modelL1 = new DbTableModel(colHeadL1);

    Vector hubIDV = (Vector)templateInfoH.get("HUB_IDS");
    String hubID = (String)hubIDV.elementAt(selectedhubinx);
    Hashtable fh = (Hashtable)alldata.get(hubID);
    Vector facIDV= ( Vector ) fh.get( "FACILITY_ID" );
    Vector facDescV= ( Vector ) fh.get( "FACILITY_DESC" );

    //make a copy of base_fields
    Object[] oaL = new Object[colHeadL.length];
    for (int i = 0; i < waclist.size(); i++)
    {
        oaL[0] = waclist.elementAt(i);
        oaL[1] = waclist.elementAt(i);
        modelL.addRow(oaL);
    }

    Object[] oaL1 = new Object[colHeadL1.length];
    for (int i = 0; i < cablist.size(); i++)
    {
        oaL1[0] = cablist.elementAt(i);
        oaL1[1] = cablist.elementAt(i);
        modelL1.addRow(oaL1);
    }

    cabrightTable = new CmisTable(modelR1);
    AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
    Hashtable chargeTableStyle = (Hashtable)BothHelpObjs.getTableStyle();
    Color color = (Color)chargeTableStyle.get("COLOR");
    Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
    Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
    Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
    Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
    Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
    renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel cm = cabrightTable.getColumnModel();
    for (int i = 0; i < cabrightTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    //font and foreground and background color for columns heading
    String fontName = (String)chargeTableStyle.get("FONT_NAME");
    Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
    Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
    cabrightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    Enumeration enum1 = cabrightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    cabrightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    modelR1.setColumnPrefWidth(facColWidths);
    for(int j=0;j<cabrightTable.getColumnCount();j++){
      int width=modelR1.getColumnWidth( j );
      cabrightTable.getColumn( modelR1.getColumnName( j ) ).setPreferredWidth( width );
      cabrightTable.getColumn( modelR1.getColumnName( j ) ).setWidth( width );
      if ( width == 0 )
      {
        cabrightTable.getColumn( modelR1.getColumnName( j ) ).setMinWidth( width );
        cabrightTable.getColumn( modelR1.getColumnName( j ) ).setMaxWidth( width );
      }
    }

    cableftTable = new CmisTable(modelL1);
    cm = cableftTable.getColumnModel();
    for (int i = 0; i < cableftTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    cableftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    enum1 = cableftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    cableftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    modelL1.setColumnPrefWidth(facColWidths);
    for(int j=0;j<cableftTable.getColumnCount();j++){
      int width=modelL1.getColumnWidth( j );
      cableftTable.getColumn( modelL1.getColumnName( j ) ).setPreferredWidth( width );
      cableftTable.getColumn( modelL1.getColumnName( j ) ).setWidth( width );
      if ( width == 0 )
      {
        cableftTable.getColumn( modelL1.getColumnName( j ) ).setMinWidth( width );
        cableftTable.getColumn( modelL1.getColumnName( j ) ).setMaxWidth( width );
      }
    }

    //facility
    Object[] facoaL = new Object[facColHeadL.length];
    for (int i = 0; i < facIDV.size(); i++)
    {
        facoaL[0] = facIDV.elementAt(i);
        facoaL[1] = facDescV.elementAt(i);
        facModelL.addRow(facoaL);
    }

    facLeftTable = new CmisTable(facModelL);
    cm = facLeftTable.getColumnModel();
    for (int i = 0; i < facLeftTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    facLeftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    enum1 = facLeftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    facLeftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    int j = 0;
    facModelL.setColumnPrefWidth(facColWidths);
    for(j=0;j<facLeftTable.getColumnCount();j++){
      int width=facModelL.getColumnWidth( j );
      facLeftTable.getColumn( facModelL.getColumnName( j ) ).setPreferredWidth( width );
      facLeftTable.getColumn( facModelL.getColumnName( j ) ).setWidth( width );
      if ( width == 0 )
      {
        facLeftTable.getColumn( facModelL.getColumnName( j ) ).setMinWidth( width );
        facLeftTable.getColumn( facModelL.getColumnName( j ) ).setMaxWidth( width );
      }
    }

    //
    facRightTable = new CmisTable(facModelR);
    cm = facRightTable.getColumnModel();
    for (int i = 0; i < facRightTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    facRightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    enum1 = facRightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    facRightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    facModelR.setColumnPrefWidth(facColWidths);
    for(j=0;j<facRightTable.getColumnCount();j++){
      int width=facModelR.getColumnWidth( j );
      facRightTable.getColumn( facModelR.getColumnName( j ) ).setPreferredWidth( width );
      facRightTable.getColumn( facModelR.getColumnName( j ) ).setWidth( width );
      if ( width == 0 )
      {
        facRightTable.getColumn( facModelR.getColumnName( j ) ).setMinWidth( width );
        facRightTable.getColumn( facModelR.getColumnName( j ) ).setMaxWidth( width );
      }
    }

    facLeftJSP.getViewport().setView(facLeftTable);
    facRightJSP.getViewport().setView(facRightTable);


    wacRightTable = new CmisTable(modelR);
    cm = wacRightTable.getColumnModel();
    for (int i = 0; i < wacRightTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    wacRightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    enum1 = wacRightTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    wacRightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    modelR.setColumnPrefWidth(facColWidths);
    for(j=0;j<wacRightTable.getColumnCount();j++){
      int width=modelR.getColumnWidth( j );
      wacRightTable.getColumn( modelR.getColumnName( j ) ).setPreferredWidth( width );
      wacRightTable.getColumn( modelR.getColumnName( j ) ).setWidth( width );
      if ( width == 0 )
      {
        wacRightTable.getColumn( modelR.getColumnName( j ) ).setMinWidth( width );
        wacRightTable.getColumn( modelR.getColumnName( j ) ).setMaxWidth( width );
      }
    }

    wacLeftTable = new CmisTable(modelL);
     //Nawaz 09/19/01
    cm = wacLeftTable.getColumnModel();
    for (int i = 0; i < wacLeftTable.getColumnCount(); i++) {
      cm.getColumn(i).setCellRenderer(renderers[0]);
    }
    wacLeftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
    tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
    enum1 = wacLeftTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
    }
    wacLeftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    modelL.setColumnPrefWidth(facColWidths);
    for(j=0;j<wacLeftTable.getColumnCount();j++){
      int width=modelR.getColumnWidth( j );
      wacLeftTable.getColumn( modelL.getColumnName( j ) ).setPreferredWidth( width );
      wacLeftTable.getColumn( modelL.getColumnName( j ) ).setWidth( width );
      if ( width == 0 )
      {
        wacLeftTable.getColumn( modelL.getColumnName( j ) ).setMinWidth( width );
        wacLeftTable.getColumn( modelL.getColumnName( j ) ).setMaxWidth( width );
      }
    }

    waclistJSP.getViewport().setView(wacLeftTable);
    reportListJSP.getViewport().setView(wacRightTable);

    cablistJSP.getViewport().setView(cableftTable);
    reportFieldJSP.getViewport().setView(cabrightTable);

    this.downLoadP.repaint();
    this.downLoadP.validate();
  }

  void waB_actionPerformed(ActionEvent e)
  {
    //System.out.println( "Getting the work Areas" );
    if ( facRightTable.getRowCount() == 0 )
    {
      GenericDlg g=new GenericDlg( cmis.getMain(),"Select Facilities(s)",true );
      g.setMsg( " Please select Facilities(s) for which you want to get Work Areas." );
      g.setVisible( true );
    }
    else
    {
      removeworkandcabinets();

      Vector tmpVhub= ( Vector ) templateInfoH.get( "HUB_NAMES" );
      Hashtable alldata= ( Hashtable ) templateInfoH.get( "All_DATA" );

      String selectedhub=hubC.getSelectedItem().toString();
      int selectedhubinx=hubC.getSelectedIndex();

      Vector myfacidDataV=new Vector();

      for ( int i=0; i < facRightTable.getRowCount(); i++ )
      {
        String tmpfacidData=facRightTable.getModel().getValueAt( i,1 ).toString();
        myfacidDataV.addElement( tmpfacidData );
      }

      Vector hubIDV= ( Vector ) templateInfoH.get( "HUB_IDS" );
      String hubID= ( String ) hubIDV.elementAt( selectedhubinx );
      Hashtable fh= ( Hashtable ) alldata.get( hubID );
      Vector facIDV= ( Vector ) fh.get( "FACILITY_ID" );
      Vector finalappdesc=new Vector();
      Vector finalappid=new Vector();

      for ( int i=0; i < facIDV.size(); i++ )
      {
        String facID= ( String ) facIDV.elementAt( i );

        if ( myfacidDataV.contains( facID ) )
        {
          Hashtable h= ( Hashtable ) fh.get( facID );

          Vector application= ( Vector ) h.get( "APPLICATION" );
          Vector applicationDesc= ( Vector ) h.get( "APPLICATION_DESC" );

          for ( int j=0; j < application.size(); j++ )
          {
            String altWAID= ( String ) application.elementAt( j );
            String altWADesc= ( String ) applicationDesc.elementAt( j );

            finalappdesc.add( "" + facID + "-" + altWAID + ":" + altWADesc + "" );
            finalappid.add( altWAID );

            altWAID="";
            altWADesc="";
          }
        }
      }
      //System.out.println(finalappid);

      for ( int j=0; j < finalappid.size(); j++ )
      {
        String tmpDataid= ( String ) finalappid.elementAt( j );
        String tmpDatades= ( String ) finalappdesc.elementAt( j );
        Object[] oa=new Object[colHeadR.length];
        oa[0]=tmpDatades;
        oa[1]=tmpDataid;

        addRowToTable( wacLeftTable,oa,wacLeftTable.getSelectedRow() );
      }

    }
  }
}  //end of class

class StreamGobbler1 extends Thread
{
    InputStream is;
    String type;

    StreamGobbler1(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                System.out.println(type + ">" + line);
            } catch (IOException ioe)
              {
                ioe.printStackTrace();
              }
    }
}


  class downloadtoscannerThread
     extends Thread
  {
    downloadtoscanner parent;

    boolean runF=true;

    public downloadtoscannerThread( downloadtoscanner parent )
    {
      super( "uploadtoscannerThread" );
      this.parent=parent;
    }

    public void run()
    {
      parent.dodownloadstuff();
    }
  }


class downloadtoscannerLoadThread extends Thread {
  downloadtoscanner parent;
  public downloadtoscannerLoadThread(downloadtoscanner parent){
     super("downloadtoscannerLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}


