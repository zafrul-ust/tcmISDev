package radian.tcmis.client.share.gui;

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

public class EngineeringEvalDetailDlg extends JDialog {

  JPanel panel1 = new JPanel();

  CmisApp cmis ;
  JScrollPane evalJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel middleP = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  JButton closeB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  String itemID = null;
  MultiSpanCellTable engEvalDetailTable = null;
  String[] engEvalCols = {"Date","Qty","Name","Phone","Email"};
  int[] engEvalColWidths = {100,40,100,100,150};
  int[] engEvalColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING};
  String facID = null;
  String workArea = null;

  public EngineeringEvalDetailDlg(JFrame fr, String title,String itemID,String facID,String workArea) {
    super(fr, title, false);
    this.itemID = itemID;
    this.facID = facID;
    this.workArea = workArea;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(500, 450));
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
    EngineeringEvalDetailDlgLoadThread x = new EngineeringEvalDetailDlgLoadThread(this);
    x.start();
  }

  void jbInit() throws Exception {
    ImageIcon ss = ResourceLoader.getImageIcon("images/button/submit.gif");
    ss = ResourceLoader.getImageIcon("images/button/cancel.gif");
    closeB.setFont(new java.awt.Font("Dialog", 0, 10));
    closeB.setMaximumSize(new Dimension(97, 24));
    closeB.setMinimumSize(new Dimension(97, 24));
    closeB.setPreferredSize(new Dimension(97, 24));
    closeB.setIcon(ss);
    panel1.setLayout(borderLayout4);

    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));

    jPanel6.setLayout(gridBagLayout1);
    middleP.setLayout(borderLayout5);

    closeB.setText("Close");
    closeB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeB_actionPerformed(e);
      }
    });

    bottomP.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(middleP, new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 15, 40, 15), 0, 0));
    middleP.add(evalJSP, BorderLayout.CENTER);

    jPanel6.add(bottomP, new GridBagConstraints(0, 1, GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 10, 5), 0, 0));
    bottomP.add(closeB, BorderLayout.EAST);
  }

  void loadItAction() {
    try {
      CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
      TcmisHttpConnection cgi = new TcmisHttpConnection("CatalogNew",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_EVAL_DETAIL"); //String, String
      query.put("ITEM_ID",this.itemID);   //String, String
      query.put("FACILITY_ID",this.facID);    //String, String
      query.put("WORK_AREA",this.workArea);    //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Boolean b = (Boolean)result.get("RETURN_CODE");
      if (!b.booleanValue()) {
        GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
        gd.setMsg((String)result.get("RETURN_MSG"));
        gd.setVisible(true);
        return;
      }
      buildTable((Hashtable)result.get("ENG_EVAL_DETAIL"));
      this.validate();
    } catch (Exception e) {
        e.printStackTrace();
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
    }finally {
      CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    }
  } //end of method

  void buildTable(Hashtable searchResult) {
    AttributiveCellTableModel model = new AttributiveCellTableModel(engEvalCols,0);
    if (searchResult == null) {
      model = new AttributiveCellTableModel(engEvalCols,0);
    }else {
      engEvalCols = (String[])searchResult.get("HEADER_COLS");
      engEvalColWidths = (int[])searchResult.get("COL_WIDTHS");
      engEvalColTypes = (int[])searchResult.get("COL_TYPES");
      Vector dataV = (Vector)searchResult.get("EVAL_DETAIL");
      model = new AttributiveCellTableModel(engEvalCols,dataV.size());
      ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
      //remove row - perpare for coloring of alternate lines
      for (int k = dataV.size() -1; k >= 0; k--){
        model.removeRow(k);
      }
      //fill table
      for (int i = 0; i < dataV.size(); i++) {
        String[] oa = (String[])dataV.elementAt(i);
        model.addRow(oa);
        //color alternate row
        if (i % 2 == 0) {
          int[] rows = new int[]{i};
          for (int k = 0; k < model.getColumnCount(); k++) {
            int[] cols = new int[]{k};
            cellAtt.setBackground(new Color(224,226,250),rows,cols);
          }
        }
      }
    }
    model.setColumnPrefWidth(engEvalColWidths);
    model.setColumnType(engEvalColTypes);
    engEvalDetailTable = new MultiSpanCellTable(model);
    engEvalDetailTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    engEvalDetailTable.setIntercellSpacing(new Dimension(0,0));
    AttributiveCellRendererLine[] engEvalRenderers = new AttributiveCellRendererLine[1];
    Hashtable catStyle = cmis.getMain().search.getCatTableStyle();
    Color color = (Color)catStyle.get("CATALOG_COLOR");
    Integer t = (Integer)catStyle.get("CATALOG_INSET_TOP");
    Integer l = (Integer)catStyle.get("CATALOG_INSET_LEFT");
    Integer b = (Integer)catStyle.get("CATALOG_INSET_BOTTOM");
    Integer r = (Integer)catStyle.get("CATALOG_INSET_RIGHT");
    Integer a = (Integer)catStyle.get("CATALOG_INSET_ALIGN");
    engEvalRenderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
    TableColumnModel m = engEvalDetailTable.getColumnModel();
    int i = 0;
    for (i = 0; i < engEvalDetailTable.getColumnCount(); i++) {
      m.getColumn(i).setCellRenderer(engEvalRenderers[0]);
    }
    //font and foreground and background color for columns heading
    String catFontName = (String)catStyle.get("CATALOG_FONT_NAME");
    Integer catFontStyle = (Integer)catStyle.get("CATALOG_FONT_STYLE");
    Integer catFontSize = (Integer)catStyle.get("CATALOG_FONT_SIZE");
    engEvalDetailTable.setFont(new Font(catFontName,catFontStyle.intValue(),catFontSize.intValue()));
    MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer((Color)catStyle.get("CATALOG_FOREGROUND"),(Color)catStyle.get("CATALOG_BACKGROUND"),engEvalRenderers[0].getBorder());
    Enumeration enum1 = engEvalDetailTable.getColumnModel().getColumns();
    while (enum1.hasMoreElements()) {
      ((TableColumn)enum1.nextElement()).setHeaderRenderer(renderer);
    }
    // set column widths
    engEvalDetailTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        //setting this allow us to set the width of table
    for(i=0;i<engEvalDetailTable.getColumnCount();i++){
      int width = model.getColumnWidth(i);
      engEvalDetailTable.getColumn(model.getColumnName(i)).setPreferredWidth(width);
      engEvalDetailTable.getColumn(model.getColumnName(i)).setWidth(width);
      if (width==0){
        engEvalDetailTable.getColumn(model.getColumnName(i)).setMinWidth(width);
        engEvalDetailTable.getColumn(model.getColumnName(i)).setMaxWidth(width);
      }
    }

    middleP.remove(evalJSP);
    evalJSP = new JScrollPane(engEvalDetailTable);
    middleP.add(evalJSP, BorderLayout.CENTER);
    middleP.validate();
  }


  void closeB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }
}

class EngineeringEvalDetailDlgLoadThread extends Thread {
  EngineeringEvalDetailDlg parent;
  public EngineeringEvalDetailDlgLoadThread(EngineeringEvalDetailDlg parent){
     super("EngineeringEvalDetailDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}
