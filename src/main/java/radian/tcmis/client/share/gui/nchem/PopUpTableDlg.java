package radian.tcmis.client.share.gui.nchem;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

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
import radian.tcmis.client.share.gui.*;

public class PopUpTableDlg extends JDialog {

  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  CmisApp cmis ;
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

  public PopUpTableDlg( JFrame fr, String title) {
    super(fr, title, true);
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(600, 300));
      this.setResizable(false);
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setParent(CmisApp c){
    cmis = c;
  }

  void jbInit() throws Exception {
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

  void buildTable(Vector dataV) {
    //build table model
    String[] colHeads = {"Request","Part Number","Description","Packaging"};
    int[] colWidths = {50,150,200,130};
    int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING,
                      BothHelpObjs.TABLE_COL_TYPE_STRING};

    AttributiveCellTableModel ctm = new AttributiveCellTableModel(colHeads,dataV.size());
    for (int j = ctm.getRowCount() - 1; j >= 0; j--) {
      ctm.removeRow(j);
    }
    ColoredCell cellAtt = (ColoredCell) ctm.getCellAttribute();
    ctm.setColumnType(colTypes);
    int count = 0;
    for (int ii = 0; ii < dataV.size(); ii++) {
      Object[] oa = (Object[]) dataV.elementAt(ii);
      ctm.insertRow(count, oa);
      //this is where I alternate the color between row
      int cond = count % 2;
      if (cond == 0) {
        int[] rows = new int[] {count};
        for (int j = 0; j < ctm.getColumnCount(); j++) {
          int[] cols = new int[] {j};
          cellAtt.setBackground(new Color(224, 226, 250), rows, cols);
        }
      }
      count++;
    }
    ctm.setColumnType(colTypes);
    ctm.setColumnPrefWidth(colWidths);
    MultiSpanCellTable myTable = new MultiSpanCellTable(ctm);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    myTable.setCellSelectionEnabled(false);
    myTable.getTableHeader().setReorderingAllowed(true);
    myTable.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
    myTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
    Hashtable tableStyle = BothHelpObjs.getTableStyle();
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
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        closeWindow();
      }
    });
  } //end of method

  void closeWindow() {
    this.setVisible(false);
  }

  void okB_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
} //end of class

