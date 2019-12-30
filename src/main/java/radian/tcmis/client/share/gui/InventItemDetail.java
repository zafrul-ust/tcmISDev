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


public class InventItemDetail extends JDialog {

  MultiSpanCellTable inventoryTable;
  TableSorter sorter = new TableSorter();

  JPanel panel1 = new JPanel();
  JPanel jPanel1 = new JPanel();
  JButton okB = new JButton();
  BorderLayout borderLayout3 = new BorderLayout();
  XYLayout xYLayout1 = new XYLayout();

  String facID;
  String hub;
  String itemID;
  JFrame currentFrame;
  int HUB_COL = 0;

  CmisApp cmis ;
  JScrollPane partJSP = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  XYLayout xYLayout4 = new XYLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel bottomP = new JPanel();
  JButton cancelB = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();

  //trong 9-21
  StaticJLabel noteL = new StaticJLabel();

  public InventItemDetail(JFrame fr, String title, String facilityId, String hub, String item) {
    super(fr, title, false);
    this.facID = facilityId;
    this.hub = hub;
    this.itemID = item;
    this.currentFrame = fr;
    try  {
      jbInit();
      pack();
      this.setSize(new Dimension(400, 300));
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
    /*
    InventItemDetailLoadThread x = new InventItemDetailLoadThread(this);
    x.start();  */
    loadItAction();
  }

  void jbInit() throws Exception {
    String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
    ImageIcon ss = new ImageIcon(imagesDir+"/button/ok.gif");
    okB.setIcon(ss);
    ss = new ImageIcon(imagesDir+"/button/cancel.gif");
    cancelB.setIcon(ss);
    panel1.setLayout(borderLayout4);
    jPanel1.setLayout(xYLayout1);
    okB.setText("Done");
    okB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okB_actionPerformed(e);
      }
    });
    /*
    inventoryTable = new MultiSpanCellTable();
    partJSP.getViewport().setView(inventoryTable);
      */
    panel1.setMaximumSize(new Dimension(520, 380));
    panel1.setMinimumSize(new Dimension(520, 380));
    panel1.setPreferredSize(new Dimension(520, 380));

    jPanel6.setLayout(gridBagLayout1);
    jPanel7.setLayout(borderLayout5);

    cancelB.setText("Cancel");
    cancelB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelB_actionPerformed(e);
      }
    });

    bottomP.setLayout(borderLayout1);
    getContentPane().add(panel1);
    panel1.add(jPanel6, BorderLayout.CENTER);
    noteL.setText("Note - To see item inventory detail.  Double click on the row.");
    jPanel6.add(noteL, new GridBagConstraints2(0, 0, 0, 0, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 15, 5, 5), 0, 0));
    jPanel6.add(jPanel7, new GridBagConstraints2(0, 0, 0, 0, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(40, 15, 50, 15), 0, 0));
    jPanel7.add(partJSP, BorderLayout.CENTER);
    jPanel6.add(bottomP, new GridBagConstraints2(1, 1, 0, 0, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    bottomP.add(okB, BorderLayout.WEST);
    //bottomP.add(cancelB, BorderLayout.EAST);
  }

  void loadItAction() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    try {
      ClientHelpObjs.setEnabledContainer(this,false);
      TcmisHttpConnection cgi = new TcmisHttpConnection("InventoryObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_INVENTORY_ITEM_DETAIL"); //String, String
      //query.put("FAC_ID",facilityId); //String, String
      //query.put("HUB",hub); //String, String
      query.put("ITEM",itemID); //String, String
      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        ClientHelpObjs.setEnabledContainer(this,true);
        return;
      }

      //partJSP.getViewport().remove(inventoryTable);

      Hashtable infoH = (Hashtable)result.get("DATA");
      AttributiveCellTableModel ctm = (AttributiveCellTableModel)infoH.get("DATA_MODEL");
      inventoryTable = new MultiSpanCellTable(ctm);
      inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
      /*unable to put the hand cursor when user enter the warehouse column
      inventoryTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
        public void mouseMoved(MouseEvent e) {
          Point p = e.getPoint();
          int colSel = inventoryTable.columnAtPoint(p);
          if (colSel == 0) {
            CursorChange.setCursor(currentFrame,Cursor.HAND_CURSOR);
          }else {
            CursorChange.setCursor(currentFrame,Cursor.DEFAULT_CURSOR);
          }
        }
      });*/
      inventoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
        /*
        public void mouseEntered(MouseEvent e) {
          Point p = e.getPoint();
          int colSel = inventoryTable.columnAtPoint(p);
          System.out.println("--------- entered colsel: "+colSel);
          if (colSel == 0) {
            CursorChange.setCursor(cmis.getMain(),Cursor.HAND_CURSOR);
          }
        }
        public void mouseExited(MouseEvent e) {
          Point p = e.getPoint();
          int colSel = inventoryTable.columnAtPoint(p);
          System.out.println("--------- exit colsel: "+colSel);
          if (colSel == 0) {
            CursorChange.setCursor(cmis.getMain(),Cursor.DEFAULT_CURSOR);
          }
        }    */
        public void mouseClicked(MouseEvent e) {
          tableClicked(e);
        }
      });

      AttributiveCellRendererLine[] renderers = new AttributiveCellRendererLine[1];
      Hashtable tableStyle = (Hashtable)result.get("TABLE_STYLE");
      Color color = (Color)tableStyle.get("COLOR");
      Integer t = (Integer)tableStyle.get("INSET_TOP");
      Integer l = (Integer)tableStyle.get("INSET_LEFT");
      Integer b = (Integer)tableStyle.get("INSET_BOTTOM");
      Integer r = (Integer)tableStyle.get("INSET_RIGHT");
      Integer a = (Integer)tableStyle.get("INSET_ALIGN");
      renderers[0] = ClientHelpObjs.createRendererLine(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
      TableColumnModel cm = inventoryTable.getColumnModel();
      for (int i = 0; i < inventoryTable.getColumnCount(); i++) {
          cm.getColumn(i).setCellRenderer(renderers[0]);
      }
      //font and foreground and background color for columns heading
      String fontName = (String)tableStyle.get("FONT_NAME");
      Integer fontStyle = (Integer)tableStyle.get("FONT_STYLE");
      Integer fontSize = (Integer)tableStyle.get("FONT_SIZE");
      inventoryTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
      MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)tableStyle.get("FOREGROUND"),(Color)tableStyle.get("BACKGROUND"),renderers[0].getBorder());
      Enumeration enum1 = inventoryTable.getColumnModel().getColumns();
      while (enum1.hasMoreElements()) {
        ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
      }
      inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      inventoryTable.setCellSelectionEnabled(false);
      inventoryTable.getTableHeader().setReorderingAllowed(true);

      // set column widths
      for(int i=0;i<inventoryTable.getColumnCount();i++){
        int width = ctm.getColumnWidth(i);
        inventoryTable.getColumn(ctm.getColumnName(i)).setPreferredWidth(width);
        inventoryTable.getColumn(ctm.getColumnName(i)).setWidth(width);
        if (width==0){
          inventoryTable.getColumn(ctm.getColumnName(i)).setMinWidth(width);
          inventoryTable.getColumn(ctm.getColumnName(i)).setMaxWidth(width);
        }
      }

      partJSP.getViewport().setView(inventoryTable);
      if (inventoryTable.getModel().getRowCount()<=0){
        GenericDlg g = new GenericDlg(cmis.getMain(),"Not found",true);
        g.setMsg("No Records found.");
        g.setVisible(true);
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
  }

  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  }

  void updateInventoryRequest(Vector updateV) {

  }

  void tableClicked(MouseEvent e) {
    int row   = inventoryTable.getSelectedRow();
    System.out.println("----------- selected row: "+row);
    if (e.getClickCount() >= 2) {
      InventFloat IF = new InventFloat(cmis.getMain(),cmis);
      Integer item = new Integer(itemID);
      IF.setItem(item.intValue());
      IF.setHub(hub);
      IF.setFacilityId(facID);
      IF.loadIt();
    }
  }

  void InventoryTable_mouseClicked(MouseEvent e) {
    int row   = inventoryTable.getSelectedRow();

  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    setVisible(false);
  //  goViewReport();
  }
}

class InventItemDetailLoadThread extends Thread {
  InventItemDetail parent;
  public InventItemDetailLoadThread(InventItemDetail parent){
     super("InventItemDetailLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadItAction();
  }
}

class Inventory_Edit_mouseAdapter extends java.awt.event.MouseAdapter {
  InventItemDetail adaptee;
  Inventory_Edit_mouseAdapter(InventItemDetail adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseClicked(MouseEvent e) {
    adaptee.InventoryTable_mouseClicked(e);
  }
  public void mouseReleased(MouseEvent e) {
  }
}