package  radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import com.borland.jbcl.layout.*;

public class ReportOptionDlg extends JDialog
{
    JPanel panel1 = new JPanel();
    JButton okB = new JButton();

    String facId = null;
    String workArea = null;
    String searchText = null;
    String currentScreen = null;
    CmisApp cmis = null;
    JButton CancelB = new JButton();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JLabel fileTypeL = new JLabel();
    JRadioButton pdfR = new JRadioButton();
    JRadioButton csvR = new JRadioButton();
    JLabel methodL = new JLabel();
    JRadioButton activeR = new JRadioButton();
    JRadioButton batchR = new JRadioButton();

    //trong 12/6/00 waste order: list
    String submitDate = null;
    String reSubmitDate = null;

    // Nawaz

    // Search type constanst
    public static final int KEYWORD = 0;
    public static final int CAS_NUM_SEARCH = 1;
    public static final int LIST = 2;
    public static final int ALL = 3;
    public static final int VOC = 4;

    // order by constants
    public static final int ORDER_BY_DEL_POINT = 0;
    public static final int ORDER_BY_WORK_AREA = 1;
    public static final int ORDER_BY_PART_NUM = 2;

    // group by constants
    public static final String[] groupString = new String[]
        {
        "Facility","Work Area","Cas #/SARA Group","Delivery Point","By Month"
    };
    public static final String[] groupStringLabel = new String[]
        {
        "Facility","Work Area","Cas #/SARA Group","Delivery Point","Month"
    };
    public static final String[] groupStringVoc = new String[]
        {
        "Facility","Work Area","By Month"
    };
    public static final String[] groupStringLabelVoc = new String[]
        {
        "Facility","Work Area","Month"
    };
    public static final int GROUP_BY_FAC = 0;
    public static final int GROUP_BY_WORK_AREA = 1;
    public static final int GROUP_BY_CAS_NUM = 2;
    public static final int GROUP_BY_DEL_POINT = 3;
    public static final int GROUP_BY_MONTH = 4;
    public static final String FACILITY = groupString[GROUP_BY_FAC];
    public static final String WORK_AREA = groupString[GROUP_BY_WORK_AREA];
    public static final String CAS_NUM = groupString[GROUP_BY_CAS_NUM];
    public static final String DEL_POINT = groupString[GROUP_BY_DEL_POINT];
    public static final String MONTH = groupString[GROUP_BY_MONTH];

    Vector reportData;
    String GroupDesc = null;
    String chemListId = "";
    String chemListDesc = "";
    String facName = "";
    String workAreaId = "";
    String workAreaDesc = "";
    String begMonth = "";
    String begYear = "";
    String endMonth = "";
    String endYear = "";
    String keyword = "";
    String casNum = "";
    int sortBy = 2;
    int chemType = 3;
    int[] groupBy;

    //catalog screen
    boolean allFac = false;
    boolean engEvalCat = false;
    boolean active = false;
    boolean approved = false;
    String catalogType = "";

    //Admin Work Area
    boolean showPDF = false;

    //order track by Dmitriy
    String requestor = null;
    String approver = null;
    String approverName = null;
    String searchType = null;
    String searchContent = null;
    String days = null;
    boolean draft = false;
    boolean delivered = false;
    boolean critical = false;
    boolean pendingApproval = false;
    boolean rejected = false;
    boolean deliveredAfter = false;
    boolean needApproval = false;
    boolean anyStatus = false;

    //New Chem Track by Dmitriy

    String  requestId = null;
    boolean newPartNumber = false;
    boolean newGroup = false;
    boolean reject = false;
    boolean newMaterial = false;
    boolean newSize = false;
    boolean newApproval = false;
    boolean approvedC = false;
    boolean banned = false;
    boolean showAllRequests = false;

    //Nawaz 01/09/02
    boolean issued = false;
    boolean archived = false;
    String userFac = null;
    String item = null;
    boolean fac,ware,eD,pD,oH,oO;
    String facility_name = null;
    String workarea_name = null;
    String selection_status = null;
    String need_approval = null;
    String order_byinven = null;

    //Inventory
    String wareHouse = null;
    boolean onHand = false;
    boolean onOrder = false;
    String expDate = null;
    String exp2Date = null;
    String byDate = null;
    boolean allParts = false;

    //user profile
    String userIdProfile = null;
    String browser = null;

    //waste Catalog
    String generatePt = null;
    boolean previousTransfer = false;

    //waste Track Win
    String daysSincePickup = null;
    boolean transferred = false;

    //waste management
    String storageArea = null;
    String vendorId = null;
    //String vendorName = null;
    String requestorName = null;//________________________________Changes By Dmitriy____________________

    //waste order track
    String vendorName = null;
    String afterT = null;
    String beforeT = null;
    String wasteOrder = null;
    boolean copy1 = false;
    boolean COD = false;
    boolean generate = false;
    boolean submit = false;
    boolean cancel = false;
    boolean pickup = false;
    boolean invoice = false;
  StaticJLabel batchrepname = new StaticJLabel();
  JTextField batchnameT = new JTextField();


    public ReportOptionDlg(Frame frame)
    {
        super(frame, "Report Option", true);
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.getContentPane().add(panel1);
        pack();
    }

    //trong 12/6/00 waste order: list
    public void setSubmitDate(String s)
    {
        this.submitDate = s;
    }
    public void setReSubmitDate(String s)
    {
        this.reSubmitDate = s;
    }


    // Nawaz
    //Admin Work Area
    public void setShowPDF(boolean b)
    {
        this.showPDF = b;
    }
    public void setGroupDesc(String s)
    {
        GroupDesc = new String(s);
    }
    public void setFacilityWorkArea(FacilityCombo fc)
    {
        this.facId = fc.getSelectedFacId();
        this.facName = fc.getSelectedFacName();
        this.workAreaId = fc.getWorkAreaCombo().getSelectedWorkAreaID();
        this.workAreaDesc = fc.getWorkAreaCombo().getSelectedItem().toString();
    }
    public void setFacilityWorkArea(String fID, String fDesc, String wID, String wDesc) {
      this.facId = fID;
      this.facName = fDesc;
      this.workAreaId = wID;
      this.workAreaDesc = wDesc;
    }

    public void setBegMonthYear(String m,String y)
    {
        begMonth = new String(m);
        begYear = new String(y);
    }
    public void setEndMonthYear(String m,String y)
    {
        endMonth = new String(m);
        endYear = new String(y);
    }
    public void setChemListId(String s)
    {
        chemListId = new String(s);
    }
    public void setChemListDesc(String s)
    {
        chemListDesc = new String(s);
    }
    public void setSortBy(int i)
    {
        sortBy = i;
    }
    public void setGroupBy(int[] i)
    {
        groupBy = i;
    }
    public void setChemType(int i)
    {
        chemType = i;
    }
    public void setKeyword(String s)
    {
        keyword = s;
    }
    public void setCasNum(String s)
    {
        casNum = s;
    }

    //Nawaz 01/09/02
    public void setIssued(boolean b)
    {
        this.issued = b;
    }
    public void setArchived(boolean b)
    {
        this.archived = b;
    }
     public void setOrderBy(String s)
    {
        order_byinven = s;
    }


    public void setUserFac(String s) { this.userFac= s; }
    public void setItem(String s) { this.item= s; }
    public void setFacBol(boolean b) { this.fac= b; }
    public void setWareBol(boolean b) { this.ware= b; }
    public void setExpBol(boolean b) { this.eD= b; }
    public void setPromBol(boolean b) { this.pD= b; }
    public void setOnHoldBol(boolean b) { this.oH= b; }
    public void setOnOrderBol(boolean b) { this.oO= b; }

    public void setFacName(String s) { this.facility_name= s; }
    public void setWaName(String s) { this.workarea_name= s; }
    public void setselection_status(String s) { this.selection_status= s; }
    public void setneed_approval (String s) { this.need_approval= s; }

    //order track
    public void setRequestor(String s)
    {
        this.requestor = s;
    }
    public void setApprover(String s)
    {
        this.approver = s;
    }
    public void setApproverName(String s)
    {
        this.approverName = s;
    }
    public void setDraft(boolean b)
    {
        this.draft = b;
    }
    public void setDelivered(boolean b)
    {
        this.delivered = b;
    }
    public void setCriticalOnly(boolean b)
    {
        this.critical = b;
    }
    public void setPendingApproval(boolean b)
    {
        this.pendingApproval = b;
    }
    public void setRejected(boolean b)
    {
        this.rejected = b;
    }
    public void setDeliveredAfterThirtyDays(boolean b)
    {
        this.deliveredAfter = b;
    }
    public void setNeedApproval(boolean b)
    {
        this.needApproval = b;
    }
    public void setAnyStatus(boolean b)
    {
        this.anyStatus = b;
    }
    //New Chem Track by Dmitriy
    public void setRequestId(String s)
    {
        this.requestId= s;
    }
    public void setNewPartNumber(boolean b)
    {
        this.newPartNumber = b;
    }
    public void setNewGroup(boolean b)
    {
        this.newGroup = b;
    }
    public void setReject(boolean b)
    {
        this.reject = b;
    }
    public void setNewMaterial(boolean b)
    {
        this.newMaterial = b;
    }
    public void setNewSize(boolean b)
    {
        this.newSize = b;
    }
    public void setNewApproval(boolean b)
    {
        this.newApproval = b;
    }
    public void setApprovedC(boolean b)
    {
        this.approvedC = b;
    }
    public void setBanned(boolean b)
    {
        this.banned = b;
    }
    public void setShowAllRequests(boolean b)
    {
        this.showAllRequests = b;
    }


    //Inventory
    public void setWareHouse(String s)
    {
        this.wareHouse = s;
    }
    public void setOnHand(boolean b)
    {
        this.onHand = b;
    }
    public void setOnOrder(boolean b)
    {
        this.onOrder = b;
    }
    public void setExpDate(String s)
    {
        this.expDate = s;
    }
    public void setExp2Date(String s)
    {
        this.exp2Date = s;
    }
    public void setByDate(String s)
    {
        this.byDate = s;
    }
    public void setAllParts(boolean b)
    {
        this.allParts = b;
    }


    //user profile
    public void setUserIdProfile(String s)
    {
        this.userIdProfile = s;
    }
    public void setBrowser(String s)
    {
        this.browser = s;
    }

    //waste order track

    public void setAfter(String s)
    {
        this.afterT = s;
    }
    public void setBefore(String s)
    {
        this.beforeT = s;
    }
    public void setWasteOrder(String s)
    {
        this.wasteOrder = s;
    }
    public void setCopy1(boolean b)
    {
        this.copy1 = b;
    }
    public void setCOD(boolean b)
    {
        this.COD = b;
    }
    public void setGenerated(boolean b)
    {
        this.generate = b;
    }
    public void setSubmitted(boolean b)
    {
        this.submit = b;
    }
    public void setCancelled(boolean b)
    {
        this.cancel = b;
    }
    public void setPickup(boolean b)
    {
        this.pickup = b;
    }
    public void setInvoiced(boolean b)
    {
        this.invoice = b;
    }

    //waste management ________________________________________Dmitriy_____________________________
    public void setStorageArea(String s)
    {
        this.storageArea = s;
    }
    public void setVendorId(String s)
    {
        this.vendorId = s;
    }
    public void setVendorName(String s)
    {
        this.vendorName = s;
    }
    public void setRequestorName(String s)
    {
        this.requestorName = s;
    }

    //waste track win
    public void setDaysSincePickup(String s)
    {
        this.daysSincePickup = s;
    }
    public void setTransferred(boolean b)
    {
        this.transferred = b;
    }

    //waste catalog
    public void setGenerationPt(String s)
    {
        this.generatePt = s;
    }
    public void setPreviousTransfer(boolean b)
    {
        this.previousTransfer = b;
    }

    //catalog screen
    public void setActive(boolean b) {
      this.active = b;
    }
    public void setApproved(boolean b) {
      this.approved = b;
    }
    public void setCatalogType(String s) {
      this.catalogType = s;
    }

    public void setEngEvalCat(boolean b)
    {
        this.engEvalCat = b;
    }
    public void setAllFacilities(boolean b)
    {
        this.allFac = b;
    }
    public void setFacId(String s)
    {
        facId = new String(s);
    }

    public void setWorkArea(String s)
    {
        workArea = new String(s);
    }
    public void setSearchText(String s)
    {
        searchText = new String(s);
    }
    public void setSearchType(String s) {
      searchType = s;
    }
    public void setDays(String s) {
      days = s;
    }

    public void setSearchContent(String s) {
      searchContent = s;
    }
    public void setScreen(String s)
    {
        currentScreen = s;
    }

    public void setCmisApp(CmisApp c)
    {
        cmis = c;
    }

    private void jbInit() throws Exception
    {
        panel1.setPreferredSize(new Dimension(450, 231));
        okB.setText("OK");
        okB.addActionListener(new ReportOptionDlg_okB_actionAdapter(this));
        this.addWindowListener(new ReportOptionDlg_this_windowAdapter(this));
        panel1.setLayout(gridBagLayout1);
        CancelB.setText("Cancel");
        CancelB.addActionListener(new ReportOptionDlg_CancelB_actionAdapter(this));
        fileTypeL.setText("File Type:");
        pdfR.setText("PDF (Acrobat)");
        pdfR.addActionListener(new ReportOptionDlg_pdfR_actionAdapter(this));
        //Nawaz 01-17-02
        csvR.setText("XLS (Excel)");

        csvR.addActionListener(new ReportOptionDlg_csvR_actionAdapter(this));
        methodL.setText("Method:");
        activeR.setText("Interactive");
        activeR.addActionListener(new ReportOptionDlg_activeR_actionAdapter(this));
        batchR.setText("Batch");
        batchR.addActionListener(new ReportOptionDlg_batchR_actionAdapter(this));
        batchrepname.setText("Report Name :");
        batchrepname.setEnabled(false);

    batchnameT.setMaximumSize(new Dimension(100, 21));
    batchnameT.setMinimumSize(new Dimension(100, 21));
    batchnameT.setPreferredSize(new Dimension(100, 21));
    batchnameT.setEnabled(false);

    panel1.add(okB, new GridBagConstraints2(1, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 20, 5), 0, 0));
        panel1.add(CancelB, new GridBagConstraints2(1, 7, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 90, 20, 5), 0, 0));
        panel1.add(fileTypeL, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
        panel1.add(pdfR, new GridBagConstraints2(1, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
        panel1.add(csvR, new GridBagConstraints2(1, 3, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        panel1.add(methodL, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
        panel1.add(activeR, new GridBagConstraints2(1, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
        panel1.add(batchR, new GridBagConstraints2(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
    panel1.add(batchrepname, new GridBagConstraints2(2, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    panel1.add(batchnameT, new GridBagConstraints2(2, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 100, 0, 0), 0, 0));

        this.setResizable(false);
        //Nawaz 01-17-02
        pdfR.setSelected(false);
        csvR.setSelected(true);
        activeR.setSelected(true);
        batchR.setSelected(false);
    }

    public synchronized  void loadIt(boolean b)
    {
        if (!b)
        {
            pdfR.setVisible(false);
            pdfR.setSelected(false);
            csvR.setSelected(true);
            csvR.setEnabled(false);;
        }

        ReportOptionDlgLoadThread iT = new ReportOptionDlgLoadThread(this);
        iT.start();
    }

    public synchronized  void loadIt()
    {
        ReportOptionDlgLoadThread iT = new ReportOptionDlgLoadThread(this);
        iT.start();
    }

    public void loadItAction()
    {
        //System.out.println("============ search info: "+this.facId+" "+this.workArea+" "+searchText);
        if ("Catalog".equalsIgnoreCase(currentScreen) ||
            "OT".equalsIgnoreCase(currentScreen) ||
            "Inventory".equalsIgnoreCase(currentScreen) ||
            "NCT".equalsIgnoreCase(currentScreen) ||
            "WC".equalsIgnoreCase(currentScreen) ||
            "WT".equalsIgnoreCase(currentScreen) ||
            "WM".equalsIgnoreCase(currentScreen) ||
            "WOT".equalsIgnoreCase(currentScreen)) {
          pdfR.setVisible(false);
          csvR.setEnabled(true);
          csvR.setSelected(true);
        }

        CenterComponent.centerCompOnScreen(this);
        this.setVisible(true);
    }

    /*
     public void goUrl() {
      int kind = 0;
      kind = URLGrab.PRINTSCREEN;
      Hashtable query = new Hashtable();
      query.put("CURRENT_SCREEN",currentScreen);
      query.put("FACILITY_ID",facId);
      query.put("WORK_AREA",workArea);
      query.put("SEARCH_TEXT",searchText);
      if (pdfR.isSelected()) {
        query.put("FILE_TYPE","PDF");
      }else {
        query.put("FILE_TYPE","CSV");
      }
      if (activeR.isSelected()) {
        query.put("METHOD","ACTIVE");
      }else {
        query.put("METHOD","BATCH");
      }
      query.put("USER_ID",cmis.getMain().getUserId().toString());
      ClientHelpObjs.goReportURL(cmis,this.cmis.getResourceBundle().getString("APP_NAME"),query,kind);
    }*/

    public void goUrl()
    {
        int kind = 0;
        kind = URLGrab.PRINTSCREEN;
        Hashtable query = new Hashtable();
        if (currentScreen.equalsIgnoreCase("Catalog"))
        {
            query = getCatalogInfo();
        }
        else if (currentScreen.equalsIgnoreCase("OT"))
        {//Order Track by
            query = getOrderTrackInfo();
        }
        else if (currentScreen.equalsIgnoreCase("NCT"))
        {//New Chem Track
            query = getNewChemTrackInfo();
        }
        else if (currentScreen.equalsIgnoreCase("WC"))
        {//Waste Catalog
            query = getWasteCatalogInfo();
        }
        else if (currentScreen.equalsIgnoreCase("WT"))
        {//Waste Track Win
            query = getWasteTrackWinInfo();
        }
        else if (currentScreen.equalsIgnoreCase("WM"))
        {//Waste Management
            query = getWasteManagementInfo();
        }
        else if (currentScreen.equalsIgnoreCase("WOT"))
        {//Waste Management
            query = getWasteOrderTrackInfo();
        }
        else if (currentScreen.equalsIgnoreCase("UP"))
        {//User Profile
            query = getUserProfileInfo();
        }
        else if (currentScreen.equalsIgnoreCase("Inventory"))
        {
            query = getInventoryInfo();
        }
        else if (currentScreen.equalsIgnoreCase("Work_Area"))
        {// Admin Work Area
            query = getWorkAreaInfo();
        }
        else if (currentScreen.equalsIgnoreCase("MSDS"))
        {// Usage Report: MSDS revision report
            query = getMSDSInfo();
        }
        else if (currentScreen.equalsIgnoreCase("Approvers"))
        {// Admin screeen Approvers
            query =  getApproverInfo();
        }
        else if (currentScreen.equalsIgnoreCase("Use_App_Group"))
        {// Approver groups
            query =  getUseAppGroupInfo();
        }
        else if (currentScreen.equalsIgnoreCase("New_Chem_App"))
        {// New Chemical Approvers
            query =  getNewChemApp();
        }
        else if (currentScreen.equalsIgnoreCase("Admin_Group"))
        {// Admin groups
            query =  getAdminGroup();
        }
        else if (currentScreen.equalsIgnoreCase("LIST") ||
                currentScreen.equalsIgnoreCase("COST") ||
                currentScreen.equalsIgnoreCase("INVOICE"))
        {// waste order: list && cost && invoice
            query =  getListCost();
        }

        if (pdfR.isSelected())
        {
            query.put("FILE_TYPE","PDF");
        }
        else
        {
            query.put("FILE_TYPE","CSV");
        }
        if (activeR.isSelected())
        {
            query.put("METHOD","ACTIVE");
        }
        else
        {
            query.put("METHOD","BATCH");
            query.put("REPORT_NAME",BothHelpObjs.makeBlankFromNull(batchnameT.getText()));
        }
        query.put("USER_ID",cmis.getMain().getUserId().toString());

        //trong 12-2 --- separating the active and batch
        dispose();
        if (activeR.isSelected())
        {
            ClientHelpObjs.goReportURL(cmis,this.cmis.getResourceBundle().getString("APP_NAME"),query,kind);
        }
        else
        {
            try
            {
                TcmisHttpConnection cgi = new TcmisHttpConnection("PrintScreen",cmis);
                Hashtable result = cgi.getResultHash(query);
                if(result == null)
                {
                    GenericDlg.showAccessDenied(cmis.getMain());
                    return;
                }
                //System.out.println("-------- got here again: "+result);
                GenericDlg gd = new GenericDlg(cmis.getMain(),
                                               "Action Succeeded",true);
                gd.setMsg(" Your report has been sent to the server for processing,\n an email will notify you upon completion.\n You may now resume other activities within tcmIS.\n Batch report(s) that is older than two weeks will be deleted.");
                gd.setVisible(true);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //test 12-8
    void goTest()
    {
        try
        {
            TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObjNew",cmis);
            Hashtable query = new Hashtable();
            query.put("USER_ID",cmis.getMain().getUserId().toString());
            query.put("ACTION","REPORTABLE_USAGE");
            Hashtable result = cgi.getResultHash(query);
            if(result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
                return;
            }
            //System.out.println("-------- got here again: "+result);
            GenericDlg gd = new GenericDlg(cmis.getMain(),
                                           "Action Succeeded",true);
            gd.setMsg(" Your report has been sent to the server for processing,\n an email will notify you upon completion.\n You may now resume other activities within tcmIS.");
            gd.setVisible(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Nawaz - 12/7/00
    void goUsageReport()
    {
        Hashtable query = new Hashtable();
        if (pdfR.isSelected())
        {
            query.put("FILE_TYPE","PDF");
        }
        else
        {
            query.put("FILE_TYPE","CSV");
        }
        if (activeR.isSelected())
        {
            query.put("METHOD","ACTIVE");
        }
        else
        {
            query.put("METHOD","BATCH");
            query.put("REPORT_NAME",BothHelpObjs.makeBlankFromNull(batchnameT.getText()));
        }
        dispose();

        String action = "REPORTABLE_USAGE";
        if(chemType == VOC)action = "VOC_USAGE";
        System.out.println("This is the Action sent to Report Obj   :"+action);
        query.put("ACTION",action); //String, String
        query.put("FACILITY",facId); //String, String
        query.put("WORK_AREA",workAreaId);
        query.put("BEGMONTH",begMonth);
        query.put("BEGYEAR",begYear);
        query.put("ENDMONTH",endMonth);
        query.put("ENDYEAR",endYear);
        query.put("CURRENT_SCREEN",currentScreen);
        // PDF or CSV
        if (pdfR.isSelected())
        {
            query.put("FILE_TYPE","PDF");
        }
        else
        {
            query.put("FILE_TYPE","CSV");
        }
        if (activeR.isSelected())
        {
            query.put("METHOD","ACTIVE");
        }
        else
        {
            query.put("METHOD","BATCH");
            query.put("REPORT_NAME",BothHelpObjs.makeBlankFromNull(batchnameT.getText()));
        }
        query.put("USER_ID",cmis.getMain().getUserId().toString());

        // sort by
        String s = "PART_NUM";
        if(sortBy == ORDER_BY_DEL_POINT)
        {
            s = "DELIVERY_POINT";
        }
        else if(sortBy == ORDER_BY_WORK_AREA)
        {
            s = "WORK_AREA";
        }
        else if(sortBy == ORDER_BY_PART_NUM)
        {
            s = "PART_NUM";
        }
        query.put("SORT_BY",s);

        // group by
        Vector gb = new Vector();
        for(int i=0;i<groupBy.length;i++)
        {
            int j = groupBy[i];
            if(j==GROUP_BY_FAC)
            {
                gb.addElement("FACILITY");
            }
            else if(j==GROUP_BY_WORK_AREA)
            {
                gb.addElement("WORK_AREA");
            }
            else if(j==GROUP_BY_CAS_NUM)
            {
                gb.addElement("CAS_NUM");
            }
            else if(j==GROUP_BY_DEL_POINT)
            {
                gb.addElement("DEL_POINT");
            }
            else if(j==GROUP_BY_MONTH)
            {
                gb.addElement("MONTH");
            }
        }
        query.put("GROUP_BY",gb);

        // chem type
        s = "";
        if(chemType==KEYWORD)
        {
            s = "KEYWORD";
        }
        else if(chemType==CAS_NUM_SEARCH)
        {
            s = "CAS_NUM";
        }
        else if(chemType==LIST)
        {
            s = "LIST";
        }
        else if(chemType==ALL)
        {
            s = "ALL";
        }
        else if(chemType==VOC)
        {
            s = "VOC";
        }

        query.put("SEARCH_TYPE",s);
        query.put("CHEM_DESC",chemListDesc);
        query.put("LIST_ID",chemListId);
        query.put("KEYWORD",keyword);
        query.put("CAS_NUM",casNum);
        query.put("Group_Matrix",groupBy);

        if (activeR.isSelected())
        {

            TcmisHttpConnection cgi = new TcmisHttpConnection("ReportObjNew",cmis);

            Hashtable result = cgi.getResultHash(query);
            if (result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
                return;
            }

            Hashtable h = (Hashtable)result.get("RETURN_CODE");
            Boolean b = (Boolean)h.get("SUCCEEDED");
            if (b.booleanValue())
            {
                try
                {
                    // From the stand alone application
                    String url = (String)result.get("URL");
                    new URLCall(url,cmis);
                    GenericDlg gd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
                    gd.setMsg(h.get("MSG").toString());
                    gd.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
                gd.setMsg(h.get("MSG").toString());
                gd.setVisible(true);
            }

        }

        else
        {
            try
            {
                TcmisHttpConnection cgi = new TcmisHttpConnection("PrintScreen",cmis);
                Hashtable result = cgi.getResultHash(query);
                if(result == null)
                {
                    GenericDlg.showAccessDenied(cmis.getMain());
                    return;
                }
                //System.out.println("-------- got here again: "+result);
                GenericDlg gd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
                gd.setMsg(" Your report has been sent to the server for processing,\n an email will notify you upon completion.\n You may now resume other activities within tcmIS.\n Batch report(s) that is older than two weeks will be deleted.");
                gd.setVisible(true);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    void showNoRecsFound()
    {
        String no = new String("No records were found matching your search criteria.");
        GenericDlg err = new GenericDlg(cmis.getMain(),"No Records Found",true);
        err.setMsg(no);
        err.setVisible(true);
    }
    //trong 12/6/00 waste order: list && cost && invoice
    public Hashtable getListCost()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("VENDOR_NAME",this.vendorName);
        query.put("WASTE_ORDER",this.wasteOrder);
        query.put("REQUESTOR_NAME",this.requestorName);
        query.put("STORAGE_AREA",this.storageArea);
        query.put("SUBMIT_DATE",this.submitDate);
        query.put("RESUBMIT_DATE",this.reSubmitDate);

        return query;
    }

    //Usage Report: MSDS revision report
    public Hashtable getMSDSInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",this.facId);
        return query;
    }

    //admin work Area
    public Hashtable getWorkAreaInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",this.facId);
        return query;
    }
    public Hashtable getApproverInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",this.facId);
        return query;
    }

    public Hashtable getUseAppGroupInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",this.facId);
        query.put("GROUP_DESC",this.GroupDesc);
        return query;
    }

    public Hashtable getNewChemApp()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",this.facId);
        return query;
    }

    public Hashtable getAdminGroup()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",this.facId);
        query.put("GROUP_DESC",this.GroupDesc);
        return query;
    }
    ////--------------------------------------------------------------------

    //changes by Dmitriy
    public Hashtable getNewChemTrackInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("REQUESTOR",requestor);
        query.put("REQUESTOR_NAME",this.requestorName);
        query.put("APPROVER",approver);
        query.put("APPROVER_NAME",approverName);
        query.put("SEARCH_TEXT",searchText);
        query.put("FACILITY_ID",facId);
        query.put("WORK_AREA",workArea);
        query.put("REQUEST_ID",requestId);

        query.put("DRAFT",draft?"T":"F");
        query.put("NEW_PART_NUMBER",newPartNumber?"T":"F");
        query.put("NEW_GROUP",newGroup?"T":"F");
        query.put("PENDING_APPROVAL",pendingApproval?"T":"F");
        query.put("REJECT",reject?"T":"F");
        query.put("NEW_MATERIAL",newMaterial?"T":"F");
        query.put("NEW_SIZE",newSize?"T":"F");
        query.put("NEW_APPROVAL",newApproval?"T":"F");
        query.put("APPROVED_C",approvedC?"T":"F");
        query.put("BANNED",banned?"T":"F");
        query.put("SHOW_ALL_REQUESTS",showAllRequests?"T":"F");
        //Nawaz 01-17-02
        query.put("FACILITY_NAME",this.facility_name);
        query.put("WORK_AREA_NAME",this.workarea_name);
        query.put("STATUS_SELECTION",this.selection_status);
        query.put("NEED_APPROVAL",this.need_approval);
        return query;
    }
    //changes by Dmitriy
    public Hashtable getOrderTrackInfo()
    {
        Hashtable query = new Hashtable();
        query.put("USER_ID",cmis.getUserId());
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("REQUESTOR",requestor);
        query.put("REQUESTOR_NAME",this.requestorName);
        query.put("FACILITY",facId);
        query.put("WORK_AREA",workArea);
        query.put("SEARCH_TYPE",searchType);
        query.put("SEARCH_CONTENT",searchContent);
        query.put("SEARCH_TEXT",searchText);
        query.put("STATUS_CRIT_ONLY",critical?"T":"F");
        query.put("NEED_MY_APPROVAL",needApproval?"T":"F");

        query.put("STATUS_DRAFT",draft?"T":"F");
        query.put("STATUS_OPEN",submit?"T":"F");
        query.put("STATUS_ARCHIVED",archived?"T":"F");
        if (archived) query.put("DAYS",days);
        query.put("STATUS_CANCELLED",cancel?"T":"F");
        query.put("STATUS_ANY",anyStatus?"T":"F");

        /*
        query.put("SEARCH_REQUEST_ID",requestId);
        query.put("APPROVER",approver);
        query.put("APPROVER_NAME",approverName);
        query.put("STATUS_DELIVERED",delivered?"T":"F");
        query.put("STATUS_PEND_APPROVAL",pendingApproval?"T":"F");
        query.put("STATUS_REJECTED",rejected?"T":"F");
        query.put("DELIVERED_AFTER",deliveredAfter?"T":"F");
        //Nawaz 01/09/02
        query.put("STATUS_PEND_ISSUE",issued?"T":"F"); // go as issue also
        query.put("STATUS_ISSUED",issued?"T":"F");
        */

        //Nawaz 01-17-02
        query.put("FACILITY_NAME",this.facility_name);
        query.put("WORK_AREA_NAME",this.workarea_name);

        return query;
    }

    public Hashtable getInventoryInfo()
    {
        Hashtable query = new Hashtable();
        /*query.put("CURRENT_SCREEN",currentScreen);
        query.put("SEARCH_TEXT",this.searchText);
        query.put("FACILITY_ID",this.facId);
        query.put("WAREHOUSE",this.wareHouse);
        query.put("ON_HAND",this.onHand?"T":"F");
        query.put("ON_ORDER",this.onOrder?"T":"F");
        query.put("EXP_DATE",this.expDate);
        query.put("EXP_2_DATE",this.exp2Date);
        query.put("BY_DATE",this.byDate);
        query.put("ALL_PARTS",this.allParts?"T":"F");*/

        query.put("CURRENT_SCREEN",currentScreen);
        query.put("SEARCH_TEXT",this.searchText);
        query.put("FACILITY_ID",this.facId);
        query.put("WAREHOUSE",this.wareHouse);
        query.put("ON_HAND",this.onHand?"T":"F");
        query.put("ON_ORDER",this.onOrder?"T":"F");
        query.put("EXP_VAL",this.expDate);
        query.put("EXP2_VAL",this.exp2Date);
        query.put("PROM_VAL",this.byDate);
        query.put("ALL_PARTS",this.allParts?"T":"F");

        //Nawaz 01-10-02
        query.put("FAC_BOL",this.fac?"T":"F");
        query.put("WARE_BOL",this.ware?"T":"F");
        query.put("EXP_BOL",this.eD?"T":"F");
        query.put("PROM_BOL",this.pD?"T":"F");
        query.put("ON_HOLD_BOL",this.oH?"T":"F");
        query.put("ON_ORDER_BOL",this.oO?"T":"F");
        query.put("USER_FAC",this.userFac);
        query.put("ITEM",this.item);
        query.put("USER_ID",cmis.getUserId().toString());
        //Nawaz 01-17-02
        query.put("FACILITY_NAME",this.facility_name);
        query.put("WORK_AREA_NAME",this.workarea_name);
        //Nawaz 01-30-02
        query.put("ORDER_BY",this.order_byinven);
        System.out.println("Order By"+order_byinven);
        return query;
    }
    public Hashtable getUserProfileInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("USER_ID_PROFILE",this.userIdProfile);
        query.put("BROWSER",this.browser);

        return query;
    }
    public Hashtable getWasteOrderTrackInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",facId);
        query.put("STORAGE_AREA",this.storageArea);
        //query.put("VENDOR_ID",this.vendorId);
        query.put("VENDOR_NAME",this.vendorName);
        query.put("AFTER",afterT);
        query.put("BEFORE",beforeT);
        query.put("WASTE_ORDER",wasteOrder);
        query.put("COPY_1",copy1?"T":"F");
        query.put("COD",COD?"T":"F");
        query.put("GENERATED",generate?"T":"F");
        query.put("SUBMITTED",submit?"T":"F");
        query.put("CANCELLED",cancel?"T":"F");
        query.put("PICKUP",pickup?"T":"F");
        query.put("INVOICED",invoice?"T":"F");

        return query;
    }
    public Hashtable getWasteManagementInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",facId);
        query.put("STORAGE_AREA",this.storageArea);
        query.put("VENDOR_ID",this.vendorId);
        query.put("VENDOR_NAME",this.vendorName);
        query.put("REQUESTOR_NAME",this.requestorName);


        return query;
    }
    public Hashtable getWasteTrackWinInfo()
    {
        Hashtable query = new Hashtable();
        //query.put("REQUESTOR_NAME",this.requestorName);
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",facId);
        query.put("DAYS_SINCE_PICKUP",this.daysSincePickup);
        query.put("TRANSFERRED",transferred?"T":"F");
        query.put("REQUESTOR_NAME",this.requestorName);

        return query;
    }
    public Hashtable getWasteCatalogInfo()
    {
        Hashtable query = new Hashtable();
        query.put("REQUESTOR_NAME",this.requestorName); //______________________________Changes By Dmitriy__________
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",facId);
        query.put("GENERATION_PT",generatePt);
        query.put("PREVIOUS_TRANSFER",previousTransfer?"T":"F");
        query.put("SEARCH_TEXT",searchText);

        return query;
    }
    public Hashtable getCatalogInfo()
    {
        Hashtable query = new Hashtable();
        query.put("CURRENT_SCREEN",currentScreen);
        query.put("FACILITY_ID",facId);
        query.put("WORK_AREA",workArea);
        query.put("SEARCH_TEXT",searchText);
        query.put("ALL_FAC",allFac?"T":"F");
        query.put("ENG_EVAL_CAT",engEvalCat?"T":"F");
        query.put("APPROVED",approved?"T":"F");
        query.put("ACTIVE",active?"T":"F");
        query.put("CATALOG_TYPE",catalogType);
        //Nawaz 01-17-02
        query.put("FACILITY_NAME",this.facility_name);
        query.put("WORK_AREA_NAME",this.workarea_name);

        return query;
    }


    // OK
    void okB_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (currentScreen.equalsIgnoreCase("VocUsage Report"))
        {
            //goTest();
            goUsageReport();
        }
        else
        {
            goUrl();
        }
    }

    void this_windowClosing(WindowEvent e)
    {
        dispose();
    }

    void pdfR_actionPerformed(ActionEvent e)
    {
      pdfR.setSelected(true);
      csvR.setSelected(false);
      /*
        if (pdfR.isSelected())
        {
            csvR.setSelected(false);
        }
        else
        {
            csvR.setSelected(true);
        }
        */
    }

    void csvR_actionPerformed(ActionEvent e)
    {
      csvR.setSelected(true);
      pdfR.setSelected(false);
      /*
        if (csvR.isSelected())
        {
            pdfR.setSelected(false);
        }
        else
        {
            pdfR.setSelected(true);
        }
       */
    }

    void activeR_actionPerformed(ActionEvent e)
    {
      activeR.setSelected(true);
      batchR.setSelected(false);
      batchrepname.setEnabled(false);
      batchnameT.setEnabled(false);

      /*
      if (activeR.isSelected())
        {
            batchR.setSelected(false);
            batchrepname.setEnabled(false);
            batchnameT.setEnabled(false);
        }
        else
        {
            activeR.setSelected(true);
        }
       */
    }

    void batchR_actionPerformed(ActionEvent e)
    {
      batchR.setSelected(true);
      activeR.setSelected(false);
      batchrepname.setEnabled(true);
      batchnameT.setEnabled(true);

      /*
        if (batchR.isSelected())
        {
            activeR.setSelected(false);
            batchrepname.setEnabled(true);
            batchnameT.setEnabled(true);
        }
        else
        {
            batchR.setSelected(true);
        }
       */
    }

    void CancelB_actionPerformed(ActionEvent e)
    {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        dispose();
        String tmsg = " ";
        cmis.getMain().countLabel.put(new Integer(Main.REPORT),tmsg);
        cmis.getMain().setStatusBarText((String)cmis.getMain().countLabel.get(new Integer(Main.REPORT)));
    }
}

class ReportOptionDlg_okB_actionAdapter implements ActionListener
{
    ReportOptionDlg adaptee;

    ReportOptionDlg_okB_actionAdapter(ReportOptionDlg adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okB_actionPerformed(e);
    }
}


class ReportOptionDlg_this_windowAdapter extends WindowAdapter
{
    ReportOptionDlg adaptee;

    ReportOptionDlg_this_windowAdapter(ReportOptionDlg adaptee)
    {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e)
    {
        adaptee.this_windowClosing(e);
    }
}
class ReportOptionDlgLoadThread extends Thread
{
    ReportOptionDlg parent;

    public ReportOptionDlgLoadThread(ReportOptionDlg parent)
    {
        super("ReportOptionDlgLoadThread");
        this.parent = parent;
    }

    public void run()
    {
        parent.loadItAction();
    }
}

class ReportOptionDlg_pdfR_actionAdapter implements java.awt.event.ActionListener
    {
    ReportOptionDlg adaptee;

    ReportOptionDlg_pdfR_actionAdapter(ReportOptionDlg adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.pdfR_actionPerformed(e);
    }
}

class ReportOptionDlg_csvR_actionAdapter implements java.awt.event.ActionListener
    {
    ReportOptionDlg adaptee;

    ReportOptionDlg_csvR_actionAdapter(ReportOptionDlg adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.csvR_actionPerformed(e);
    }
}

class ReportOptionDlg_activeR_actionAdapter implements java.awt.event.ActionListener
    {
    ReportOptionDlg adaptee;

    ReportOptionDlg_activeR_actionAdapter(ReportOptionDlg adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.activeR_actionPerformed(e);
    }
}

class ReportOptionDlg_batchR_actionAdapter implements java.awt.event.ActionListener
    {
    ReportOptionDlg adaptee;

    ReportOptionDlg_batchR_actionAdapter(ReportOptionDlg adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.batchR_actionPerformed(e);
    }
}

class ReportOptionDlg_CancelB_actionAdapter implements java.awt.event.ActionListener
    {
    ReportOptionDlg adaptee;

    ReportOptionDlg_CancelB_actionAdapter(ReportOptionDlg adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.CancelB_actionPerformed(e);
    }
}

/*
  protected void goReport(){
    CursorChange.setCursor(this.getContentPane(),Cursor.WAIT_CURSOR);
    TcmisHttpConnection cgi = new TcmisHttpConnection("NewReport",cmis);
    Hashtable query = new Hashtable();
    query.put("ACTION","PRINT_SCREEN");
    query.put("FACILITY_ID",facId);
    query.put("WORK_AREA",workArea);
    query.put("SEARCH_TEXT",searchText);
    if (pdfR.isSelected()) {
      query.put("FILE_TYPE","PDF");
    }else {
      query.put("FILE_TYPE","CSV");
    }
    if (activeR.isSelected()) {
      query.put("METHOD","ACTIVE");
    }else {
      query.put("METHOD","BATCH");
    }
    query.put("USERID",cmis.getMain().getUserId().toString());

    Hashtable result = cgi.getResultHash(query);

    if (result == null){
         String no = new String("Access denied. Your session is corrupted, please restart the application.");
         GenericDlg err = new GenericDlg(cmis.getMain(),"Denied",true);
         err.setMsg(no);
         err.setVisible(true);
         return;
    }

    this.validate();
    //ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);

  }
*/

/*
public void goUrl() {
    int kind = 0;
    kind = URLGrab.PRINTSCREEN;
    Hashtable query = new Hashtable();
    if (currentScreen.equalsIgnoreCase("Catalog")) {
      query = getCatalogInfo();
    }else if (currentScreen.equalsIgnoreCase("OT")) {      //Order Track by
      query = getOrderTrackInfo();
    }else if (currentScreen.equalsIgnoreCase("NCT")) {      //New Chem Track
      query = getNewChemTrackInfo();
    }else if (currentScreen.equalsIgnoreCase("WC")) {      //Waste Catalog
      query = getWasteCatalogInfo();
    }else if (currentScreen.equalsIgnoreCase("WT")) {      //Waste Track Win
      query = getWasteTrackWinInfo();
    }else if (currentScreen.equalsIgnoreCase("WM")) {      //Waste Management
      query = getWasteManagementInfo();
    }else if (currentScreen.equalsIgnoreCase("WOT")) {      //Waste Management
      query = getWasteOrderTrackInfo();
    }else if (currentScreen.equalsIgnoreCase("UP")) {      //User Profile
      query = getUserProfileInfo();
    }else if (currentScreen.equalsIgnoreCase("Inventory")) {
      query = getInventoryInfo();
    }else if (currentScreen.equalsIgnoreCase("Work_Area")) {       // Admin Work Area
      query = getWorkAreaInfo();
    }else if (currentScreen.equalsIgnoreCase("MSDS")) {       // Usage Report: MSDS revision report
      query = getMSDSInfo();
    }else if (currentScreen.equalsIgnoreCase("Approvers")) {       // Admin screeen Approvers
      query =  getApproverInfo();
    }else if (currentScreen.equalsIgnoreCase("Use_App_Group")) {       // Approver groups
      query =  getUseAppGroupInfo();
    }else if (currentScreen.equalsIgnoreCase("New_Chem_App")) {       // New Chemical Approvers
      query =  getNewChemApp();
    }else if (currentScreen.equalsIgnoreCase("Admin_Group")) {       // Admin groups
      query =  getAdminGroup();
    }

    if (pdfR.isSelected()) {
      query.put("FILE_TYPE","PDF");
    }else {
      query.put("FILE_TYPE","CSV");
    }
    if (activeR.isSelected()) {
      query.put("METHOD","ACTIVE");
    }else {
      query.put("METHOD","BATCH");
    }
    query.put("USER_ID",cmis.getMain().getUserId().toString());

    ClientHelpObjs.goReportURL(cmis,this.cmis.getResourceBundle().getString("APP_NAME"),query,kind);
  }

*/
