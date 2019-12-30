package radian.tcmis.client.share.gui;

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;
import java.beans.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class FinanceApproverListDlg extends JDialog {

  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String prNumber;

  CmisApp cmis ;
  RequestsWin requestWin;
  JScrollPane partJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();

  public FinanceApproverListDlg(RequestsWin rw, JFrame fr, String title, String prNum) {
    super(fr, title, false);
    this.requestWin = rw;
    this.prNumber = prNum;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(450, 300));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setParent(CmisApp c){
    cmis = c;
  }

  public void loadIt(){
    FinanceApproverListLoadThread x = new FinanceApproverListLoadThread(this);
    x.start();
  }
  void jbInit() throws Exception {
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    */
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/ok.gif");
    okB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("OK");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });

    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));
    panel1.setBorder(ClientHelpObjs.groupBox(""));

    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(borderLayout5);


    bottomP.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(25, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);

    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
  }

  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.MATERIAL_REQUEST,cmis);
      Hashtable query = new Hashtable();
      query.put("FUNCTION","GET_FINANCE_APPROVER_INFO"); //String, String
      query.put("USER_ID",cmis.getUserId());
      query.put("REQ_NUM",this.prNumber);
      query.put("STATUS","PENDING");
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      Boolean bool = (Boolean)result.get("RETURN_CODE");
      if (bool.booleanValue()) {
        Hashtable useApprovalH = (Hashtable) result.get("FINANCE_APPROVER_INFO");
        //build table model
        String[] colHeads = (String[]) useApprovalH.get("TABLE_HEADERS");
        int[] colWidths = (int[]) useApprovalH.get("TABLE_WIDTHS");
        int[] colTypes = (int[]) useApprovalH.get("TABLE_TYPES");
        AttributiveCellTableModel ctm = new AttributiveCellTableModel(colHeads,50);
        for (int j = ctm.getRowCount() - 1; j >= 0; j--) {
          ctm.removeRow(j);
        }
        ColoredCell cellAtt = (ColoredCell) ctm.getCellAttribute();
        ctm.setColumnType(colTypes);
        int count = 0;
        Vector dataV = (Vector) useApprovalH.get("TABLE_DATA");
        for (int ii = 0; ii < dataV.size(); ii++) {
          Object[] oa = (Object[]) dataV.elementAt(ii);
          ctm.insertRow(count, oa);
          //this is where I alternate the color between row
          int cond = count % 2;
          if (cond == 0) {
            int[] rows = new int[] {
                count};
            for (int j = 0; j < ctm.getColumnCount(); j++) {
              int[] cols = new int[] {
                  j};
              cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
            }
          }
          count++;
        }
        ctm.setColumnType(colTypes);
        ctm.setColumnPrefWidth(colWidths);
        //AttributiveCellTableModel ctm = (AttributiveCellTableModel)useApprovalH.get("DATA_MODEL");
        MultiSpanCellTable myTable = new MultiSpanCellTable(ctm);
        myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        myTable.setCellSelectionEnabled(false);
        myTable.getTableHeader().setReorderingAllowed(true);
        myTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
        myTable.setIntercellSpacing(new Dimension(0,0));
        AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
        Hashtable tableStyle = (Hashtable)result.get("TABLE_STYLE");
        Color color = (Color)tableStyle.get("COLOR");
        Integer t = (Integer)tableStyle.get("INSET_TOP");
        Integer l = (Integer)tableStyle.get("INSET_LEFT");
        Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
        Integer r = (Integer)tableStyle.get("INSET_RIGHT");
        Integer a = (Integer)tableStyle.get("INSET_ALIGN");
        renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
        TableColumnModel cm = myTable.getColumnModel();
        for (int i = 0; i < myTable.getColumnCount(); i++) {
          cm.getColumn(i).setCellRenderer(renderers[0]);
        }
        //font and foreground and background color for columns heading
        String fontName = (String)tableStyle.get("FONT_NAME");
        Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
        Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
        myTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = myTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }

        // set column widths
        for(int i=0;i<myTable.getColumnCount();i++){
          int width = ctm.getColumnWidth(i);
          myTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
          myTable.getColumn(ctm.getColumnName(i)).setWidth(width);
          if (width==0){
            myTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
            myTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
          }
        }
        partJSP.getViewport().setView(myTable);

      }else {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("MSG"));
        gd.setVisible(true);
      }
      ClientHelpObjs.setEnabledContainer(this,true);
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
    }
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        closeWindow();
      }
    });
  }

  void closeWindow() {
    requestWin.financeDlgOpen = false;
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    requestWin.financeDlgOpen = false;
    this.setVisible(false);
  }
}

class FinanceApproverListLoadThread extends Thread {
  FinanceApproverListDlg parent;
  public FinanceApproverListLoadThread(FinanceApproverListDlg parent){
     super("FinanceApproverListLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}
