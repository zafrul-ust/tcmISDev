

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class SearchPersonnelMult extends JDialog {
  //Table
  final String[] searchCols = {"User ID","First Name","Last Name"};
  final Long colTypeFlag = new Long("211");

  int searchEditableFlag = 0;
  int row;

  Hashtable picks = new Hashtable();


  TableOrganizer tOrg = new TableOrganizer();
  TableSorter  sorterSearch = new TableSorter();
  CmisTableModel cmisTModelSearch = new CmisTableModel();
  JScrollPane jsp = new JScrollPane();

  //search
  String personType;
  String facility;
  Integer valueId;

  XYLayout xYLayout1 = new XYLayout();
  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JTextField queryT = new JTextField();
  JRadioButton lastC = new JRadioButton();
  JRadioButton firstC = new JRadioButton();
  JRadioButton idC = new JRadioButton();
  ButtonGroup checkboxGroup1 = new ButtonGroup();
  JButton searchB = new JButton();
  JButton cancelB = new JButton();
  JButton okB = new JButton();
  CmisApp cmis = null;
  CmisTable searchTable = new CmisTable();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton applyB = new JButton();
  boolean canceled = false;

  public SearchPersonnelMult(Frame frame, String title, boolean modal) {
    super(frame, "Name Search", modal);
    try {
      jbInit();
      pack();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void setCmis(CmisApp m){
    cmis = m;
  }

  private void jbInit() throws Exception{
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR") ;
*/
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    xYLayout1.setWidth(500);
    xYLayout1.setHeight(300);
    queryT.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        queryT_keyPressed(e);
      }
    });
    lastC.setText("Last Name");
    jPanel1.setLayout(gridBagLayout1);
    okB.setMaximumSize(new Dimension(79, 25));
    applyB.setMaximumSize(new Dimension(79, 25));
    applyB.setText("Apply");
    applyB.setPreferredSize(new Dimension(79, 25));
    applyB.setMinimumSize(new Dimension(79, 25));
    applyB.addActionListener(new SearchPersonnelMult_applyB_actionAdapter(this));
    applyB.setIcon(ResourceLoader.getImageIcon("images/button/submit.gif"));
    applyB.setEnabled(false);
    checkboxGroup1.add(lastC);
    searchB.setText("Search");
    searchB.addActionListener(new SearchPersonnelMult_searchB_actionAdapter(this));
    searchB.setIcon(ResourceLoader.getImageIcon("images/button/search.gif"));
    cancelB.setText("Cancel");
    cancelB.addActionListener(new SearchPersonnelMult_cancelB_actionAdapter(this));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    okB.setText("Ok");
    okB.setPreferredSize(new Dimension(79, 25));
    okB.addActionListener(new SearchPersonnelMult_okB_actionAdapter(this));
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    okB.setMinimumSize(new Dimension(79, 25));
    firstC.setText("First Name");
    checkboxGroup1.add(firstC);
    idC.setText("User ID");
    checkboxGroup1.add(idC);
    panel1.setLayout(xYLayout1);
    panel2.setLayout(new BorderLayout());
    panel1.add(panel2, new XYConstraints(5, 64, 490, 173));
    panel1.add(queryT, new XYConstraints(14, 12, 182, -1));
    panel1.add(lastC, new XYConstraints(201, 12, -1, -1));
    panel1.add(firstC, new XYConstraints(299, 12, -1, -1));
    panel1.add(idC, new XYConstraints(391, 12, -1, -1));
    panel1.add(jPanel1, new XYConstraints(5, 240, 490, 57));
    jPanel1.add(searchB, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 25, 5, 5), 0, 0));
    jPanel1.add(okB, new GridBagConstraints2(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(cancelB, new GridBagConstraints2(3, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 25), 0, 0));
    jPanel1.add(applyB, new GridBagConstraints2(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    panel2.add(jsp, BorderLayout.CENTER);
    //build table
    buildTable();
    queryT.requestFocus();
    this.getContentPane().add(panel1);

    //colors and fonts
    ClientHelpObjs.makeDefaultColors(this);

    this.pack();
  }

  void tableClicked(){
    applyB.setEnabled(searchTable.getSelectedRowCount()>0);
  }

  void buildTable(){
    cmisTModelSearch = new CmisTableModel(searchCols);
    sorterSearch = new TableSorter(cmisTModelSearch);
    sorterSearch.setColTypeFlag(colTypeFlag.longValue());
    searchTable = new CmisTable(sorterSearch);

    tOrg = new TableOrganizer(searchTable);
    searchTable = (CmisTable) tOrg.getJTable();

    searchTable.getColumn("User ID").setWidth(90);
    searchTable.getColumn("First Name").setWidth(200);
    searchTable.getColumn("Last Name").setWidth(200);

    searchTable.sizeColumnsToFit(0);
    searchTable.revalidate();

    jsp =  new JScrollPane(searchTable);

    panel2.add(jsp,BorderLayout.CENTER);
    panel2.validate();

    //set check mark
    lastC.setSelected(true);
  }

  public void setFacility(String fac){
     facility = fac;
  }

  public void setPersonType(String type){
     personType = type;
  }
  public Hashtable getSelections(){
    return picks;
  }

  void cancelB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(confirmCancel()){
      picks = new Hashtable();
      canceled = true;
      setVisible(false);
    }
  }
  public boolean getCanceled(){
    return canceled;
  }
  void okB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    applyCurrentSelections();
    this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    picks = new Hashtable();
    canceled = true;
  }

  void searchB_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    doSearch();
  }

  boolean confirmCancel(){
    if(picks == null || picks.size() == 0)return true;
    ConfirmDlg cd = new ConfirmDlg(cmis.getMain(),"Cancel Selection",true);
    cd.setMsg("Click OK to cancel your selections.");
    cd.setVisible(true);
    return cd.getConfirmationFlag();
  }

  void doSearch() {
    applyB.setEnabled(false);
    String searchBy;
    try {
      TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO, cmis);

      Hashtable myQuery = new Hashtable();
      myQuery.put("ACTION","SEARCH_PERSONNEL");
      myQuery.put("PERSONTYPE",personType==null?"PERSONNEL":personType);
      myQuery.put("FACILITY",facility==null?"":facility);
      myQuery.put("QUERY",queryT.getText());

      if (lastC.isSelected()){
        searchBy = "LASTNAME";
      } else if (firstC.isSelected()){
        searchBy = "FIRSTNAME";
      } else {
        searchBy = "ID";
      }
      myQuery.put("SEARCHBY",searchBy);

      //if (cmis.getResourceBundle().getString("DEBUG").equals("true")) // // System.out.println("query:" + myQuery);
      Hashtable result = cgi.getResultHash(myQuery);
      Vector v = (Vector)result.get("DATA");
      Object[][] data = HttpFormatter.vectorTo2DArray(v,3);


      if (data == null || data.length == 0){
       String no = new String("No records found.");
       GenericDlg err = new GenericDlg(cmis.getMain(),"Not Found",true);
       err.setMsg(no);
       err.setVisible(true);
       cmisTModelSearch = new CmisTableModel(searchCols);
     } else  {
       cmisTModelSearch = new CmisTableModel(searchCols,data);
     }


     sorterSearch = new TableSorter(cmisTModelSearch);
     sorterSearch.setColTypeFlag(colTypeFlag.longValue());
     searchTable = new CmisTable (sorterSearch);
     tOrg = new TableOrganizer(searchTable);
     searchTable = (CmisTable) tOrg.getJTable();
     searchTable.setColTypeFlag(colTypeFlag.longValue());

     searchTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
     searchTable.getColumn("User ID").setWidth(90);
     searchTable.getColumn("First Name").setWidth(200);
     searchTable.getColumn("Last Name").setWidth(200);
     searchTable.sizeColumnsToFit(0);
     searchTable.revalidate();

     panel2.remove(jsp);
     panel2.validate();
     jsp =  new JScrollPane(searchTable);
     searchTable.addMouseListener(new MouseListener(){
       public void mouseClicked(MouseEvent e){tableClicked();}
       public void mouseExited(MouseEvent e){}
       public void mouseEntered(MouseEvent e){}
       public void mousePressed(MouseEvent e){}
       public void mouseReleased(MouseEvent e){}
     });

     panel2.add(jsp, BorderLayout.CENTER);
     this.validate();
     panel2.validate();
     ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    } catch (Exception e1) { e1.printStackTrace(); }
  }

  void applyCurrentSelections(){
    int[] sel = searchTable.getSelectedRows();
    for(int i=0;i<sel.length;i++){
      Integer id = new Integer(cmisTModelSearch.getValueAt(sel[i],0).toString());
      String valueLast = cmisTModelSearch.getValueAt(sel[i],2).toString();
      String valueFirst = cmisTModelSearch.getValueAt(sel[i],1).toString();
      picks.put(id.toString(),valueLast +", "+valueFirst);
    }
  }
  void queryT_keyPressed(KeyEvent e) {
     if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       doSearch();
     }
  }

  void applyB_actionPerformed(ActionEvent e) {
    if(searchTable.getSelectedRowCount() < 1)return;
    applyCurrentSelections();
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_DONE);
  }
}

class SearchPersonnelMult_cancelB_actionAdapter implements java.awt.event.ActionListener{
  SearchPersonnelMult adaptee;

  SearchPersonnelMult_cancelB_actionAdapter(SearchPersonnelMult adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}
class SearchPersonnelMult_okB_actionAdapter implements java.awt.event.ActionListener{
  SearchPersonnelMult adaptee;

  SearchPersonnelMult_okB_actionAdapter(SearchPersonnelMult adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class SearchPersonnelMult_searchB_actionAdapter implements java.awt.event.ActionListener{
  SearchPersonnelMult adaptee;

  SearchPersonnelMult_searchB_actionAdapter(SearchPersonnelMult adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.searchB_actionPerformed(e);
  }
}

class SearchPersonnelMult_applyB_actionAdapter implements java.awt.event.ActionListener {
  SearchPersonnelMult adaptee;


  SearchPersonnelMult_applyB_actionAdapter(SearchPersonnelMult adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.applyB_actionPerformed(e);
  }
}
