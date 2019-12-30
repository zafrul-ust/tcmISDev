package radian.tcmis.client.share.gui.admin;

import javax.swing.*;
import radian.tcmis.client.share.gui.*;
import radian.tcmis.client.share.httpCgi.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import radian.tcmis.client.share.helpers.*;
import java.util.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AdminWastePickupLocation extends AdminInputPanel {
  FacilityCombo facC = new FacilityCombo();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel holderP = new JPanel();
  JPanel jPanel4 = new JPanel();
  JButton addB = new JButton();
  JButton editB = new JButton();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  StaticJLabel facL = new StaticJLabel("Facility:");
  StaticJLabel jLabel1 = new StaticJLabel();
  JTable myTable = new JTable();
  boolean facCLoaded = false;
  boolean tableLoaded = false;
  boolean dbDataLoaded = false;
  boolean ADD_FLAG;
  Object[][] data;
  Vector myHVector;
  String facId;
  String currentId;

  public AdminWastePickupLocation(AdminWin aw) {
    super(aw,"Waste Pickup Location");
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    addB.setIcon(ResourceLoader.getImageIcon("images/button/add.gif"));
    addB.setMaximumSize(new Dimension(110, 35));
    addB.setPreferredSize(new Dimension(80, 35));
    addB.setText("Add");
    addB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        addB_actionPerformed(e);
      }
    });

    editB.setIcon(ResourceLoader.getImageIcon("images/button/delete.gif"));
    editB.setMaximumSize(new Dimension(110, 35));
    editB.setPreferredSize(new Dimension(80, 35));
    editB.setText("Edit");
    editB.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        editB_actionPerformed(e);
      }
    });
    jPanel4.setLayout(verticalFlowLayout1);
    jPanel1.setLayout(gridBagLayout2);
    jPanel2.setLayout(gridBagLayout3);
    holderP.setLayout(borderLayout1);
    jPanel4.setMaximumSize(new Dimension(120, 180));
    jPanel4.setMinimumSize(new Dimension(120, 180));
    jPanel4.setPreferredSize(new Dimension(120, 180));
    this.add(jPanel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,0));
    facL.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel1.add(facL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(15, 0, 0, 3), 0, 0));
    facC.setMaximumSize(new Dimension(120,27));
    facC.setMinimumSize(new Dimension(120,27));
    facC.setPreferredSize(new Dimension(120,27));
    facC.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        facC_actionPerformed(e);
      }
    });

    jPanel1.add(facC, new GridBagConstraints2(1, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 3, 0, 3), 0, 0));

    this.add(jPanel2, new GridBagConstraints2(0, 1, 1, 1, 3.0, 3.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
    jPanel2.add(holderP, new GridBagConstraints2(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(12,5,15,5), 0, 0));
    holderP.add(jScrollPane1, BorderLayout.CENTER);
    jPanel2.add(jPanel4, new GridBagConstraints2(2, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 15), 0, 0));
    jPanel2.add(jLabel1, new GridBagConstraints2(0, 0, 1, 1, 0.0, .0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(15, 15, 0, 0), 0, 0));

    jPanel4.add(addB, null);
    jPanel4.add(editB, null);

    ClientHelpObjs.makeDefaultColors(this);
    validate();
  }

  public void setFacCombo(FacilityCombo fc) {
    this.getButton().setText("Waste Pickup Location");
    jLabel1.setText("Waste Pickup Locations");

    facC.setCmisApp(getCmisApp());
    facC.setUseAllFacilities(false);
    if(!getCmisApp().getGroupMembership().isSuperUser()){
      facC.restrictToAdminFacilities(true);
    }
    fc.loadAnotherOne(facC);
    facC.setSelectedIndex(0);
    facCLoaded = true;
    loaded();
  }

  public void loaded() {
    if (dbDataLoaded && facCLoaded) {
      this.enableButton(true);
      facC.setSelectedFacId(getCmisApp().getPrefFac());
      loadTable();
    }
  }

  public void loadIt() {
    this.startLoad();
  }

  public void loadTable() {
    CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",getCmisApp());
    Hashtable query = new Hashtable();
    String action = "GET_LOCATION_DATA";
    facId = facC.getSelectedFacId();
    query.put("ACTION",action);
    query.put("FAC_ID", facId);
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      if (!this.oopsReload) {
        oopsReload = true;
        loadThisPanel();
        return;
      }
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }

    loadDataForTable(result);
    buildTable();
    ClientHelpObjs.setEnabledContainer(this,true);
    this.revalidate();
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
    facC.setEnabled(true);
    editB.setEnabled(false);
  }

  void loadDataForTable(Hashtable result) {
    myHVector = (Vector)result.get("WASTE_LOCATION_DATA");
    int cols = 2;
    data = new Object[myHVector.size()][cols];

    int descpt = 1;
    int iden = 0;
    Hashtable tempH = new Hashtable();
    for (int i = 0; i < myHVector.size(); i++) {
      tempH = (Hashtable)myHVector.elementAt(i);
      data[i][iden] = tempH.get("ID");
      data[i][descpt] = tempH.get("DESC");
    }
  }

  void buildTable() {
    String[] colHead = new String[] {" ID ", "Description"};

    holderP.remove(jScrollPane1);
    holderP.revalidate();

    CmisTableModel dtm;
    if (data == null || data.length < 1) {
      dtm = new CmisTableModel(colHead);
    } else {
      dtm = new CmisTableModel(colHead,data);
    }

    dtm.setEditableFlag(0);
    TableSorter sorter = new TableSorter(dtm);
    sorter.setColTypeFlag("119");
    myTable = null;
    myTable = new JTable(sorter);
    myTable.setCellSelectionEnabled(false);
    myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    myTable.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {performEditing();}
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
      });

    sorter.addMouseListenerToHeaderInTable(myTable);
    jScrollPane1 = new JScrollPane(myTable);
    holderP.add(jScrollPane1,BorderLayout.CENTER);
    myTable.revalidate();
    jScrollPane1.revalidate();
    holderP.revalidate();
    this.revalidate();
  }

  void performEditing() {
    editB.setEnabled(true);
  }

  public void loadThisPanel() {
    dbDataLoaded = true;
    loaded();
  }

  void addB_actionPerformed(ActionEvent e) {
    ADD_FLAG = true;
    String currentDesc = "";          //sending an empty string
    currentId = "";                   //sending an empty string
    AddWasteDlg d = new AddWasteDlg(this,new JFrame("TEST"), "Adding Waste Pickup Location",facId,currentId,currentDesc,ADD_FLAG);
    CenterComponent.centerCompOnScreen(d);
    d.show();
  }

  void editB_actionPerformed(ActionEvent e) {
    ADD_FLAG = false;
    int selectedR = myTable.getSelectedRow();
    String currentDesc = (String)data[selectedR][1];
    currentId = (String)data[selectedR][0];
    AddWasteDlg d = new AddWasteDlg(this,new JFrame("TEST"), "Editing Waste Pickup Location",facId,currentId,currentDesc,ADD_FLAG);
    CenterComponent.centerCompOnScreen(d);
    d.show();
  }

  public void updateEditing(String updateDesc) {
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",getCmisApp());
    Hashtable query = new Hashtable();
    String action = "CHANGE_PICKUP_LOCATION_DESC";
    facId = facC.getSelectedFacId();
    Integer locationId = new Integer(currentId);
    query.put("ACTION",action);
    query.put("LOCATION_ID", locationId);
    query.put("FAC_ID",facId);
    query.put("LOCATION_DESC",updateDesc);
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }

    loadDataForTable(result);
    buildTable();
    ClientHelpObjs.setEnabledContainer(this,true);
    this.revalidate();
    facC.setEnabled(true);
  }

  public void updateAdd(String updateDesc) {
    ClientHelpObjs.setEnabledContainer(this,false);
    TcmisHttpConnection cgi = new TcmisHttpConnection("Waste",getCmisApp());
    Hashtable query = new Hashtable();
    String action = "ADD_PICKUP_LOCATION";
    facId = facC.getSelectedFacId();
    query.put("ACTION",action);
    query.put("USER_ID",(new Integer(getCmisApp().getUserId().intValue())).toString());
    query.put("FAC_ID",facId);
    query.put("LOCATION_DESC",updateDesc);
    Hashtable result = cgi.getResultHash(query);
    if (result == null) {
      GenericDlg.showAccessDenied(getCmisApp().getMain());
      ClientHelpObjs.setEnabledContainer(this,true);
      return;
    }

    loadDataForTable(result);
    buildTable();
    ClientHelpObjs.setEnabledContainer(this,true);
    this.revalidate();
    facC.setEnabled(true);
  }


  void facC_actionPerformed(ActionEvent e) {
    if(facC.isLoading())return;
    Waste_pickup_location_thread wt = new Waste_pickup_location_thread(this);
    wt.start();
  }
}

  class Waste_pickup_location_thread extends Thread {
    AdminWastePickupLocation awpl;
    Waste_pickup_location_thread(AdminWastePickupLocation awpl) {
      super();
      this.awpl = awpl;
    }

    public void run() {
      awpl.loadTable();
    }
  }
