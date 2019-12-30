package radian.tcmis.server7.share.servlets;

//ACJEngine

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.sql.ResultSet;
import java.math.BigDecimal;

import com.actuate.ereport.common.IViewerInterface;
import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.both1.reports.CatalogApproval;
import radian.tcmis.both1.reports.Fate;
import radian.tcmis.both1.reports.MaterialSize;
import radian.tcmis.both1.reports.QualityPackaging;
import radian.tcmis.both1.reports.RegisterTable;
import radian.tcmis.both1.reports.Specification;
import radian.tcmis.server7.share.dbObjs.CatalogAddItemNew;
import radian.tcmis.server7.share.dbObjs.CatalogAddRequestNew;
import radian.tcmis.server7.share.dbObjs.EngEval;
import radian.tcmis.server7.share.dbObjs.Facility;
import radian.tcmis.server7.share.dbObjs.NChem;
import radian.tcmis.server7.share.dbObjs.NewChemApproval;
import radian.tcmis.server7.share.dbObjs.NewChemTrackTable;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.ProcessDetailDBObj;
import radian.tcmis.server7.share.dbObjs.PurchaseRequest;
import radian.tcmis.server7.share.dbObjs.RequestsScreen;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.VVs;
import radian.tcmis.server7.share.factory.PartCategoryCriteriaBeanFactory;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.db.DBResultSet;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;

/**
 * <p>Title:New Chem Object</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 *
 * @author Trong Ngo
 * @version 1.0
 *          <p/>
 *          09-15-03 Getting the template Path info from the tcmISresourceBundle
 *          06-25-04 Adding log statements to trace a memory usage issue
 */

public class NChemObj extends TcmisServletObj {
    //Client Version
    String function = null;
    Hashtable mySendObj = new Hashtable();
    Hashtable inData = null;

    //report
    ACJEngine erw = null;
    OutputStream os = null;
    TemplateManager tm = null;
    ACJOutputProcessor ec = null;
    AppDataHandler ds = new AppDataHandler();
    private static org.apache.log4j.Logger reoprtlog = org.apache.log4j.Logger.getLogger("tcmis.web");
    //Table Main Tracking
    static final String[] colHeads = new String[]{"Status", "Item", "Facility", "Part Num.", "Type", "Needed", "Delivered",
            "Last Del.", "Next Qty", "Next Date", "MR", "SO", "PO", "Requester",
            "Approver", "Description", "Crit.", "Notes", "Canceled", "DetailScreen", "LineItem",
            "MatID", "SpecID", "SpecOn", "MSDSOn", "RealNotes", "Cancel Requested"};
    static final int[] colWidths = new int[]{66, 66, 94, 100, 42, 65, 65,
            65, 65, 65, 50, 70, 110, 90,
            90, 200, 49, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    static final String colTypes = new String("111113132311111141412111114");

    static final Hashtable keyCols = new Hashtable();
    static final String[] mainCols = {"STATUS", "ITEM_ID", "FAC_ID", "PN_COL", "TYPE", "NEEDED", "DELIVERED",
            "LAST_DEL", "NEXT_QTY", "NEXT_DATE", "MR_NUM_COL", "SO_COL", "PO", "REQUESTER",
            "APPROVER", "DESCRIPTION", "CRIT_COL", "NOTES", "CANCELED", "SCREEN_TYPE", "LINE_ITEM",
            "MAT_ID", "SPEC_ID", "SPEC_ONLINE", "MSDS_ONLINE", "REAL_NOTES", "CANCEL_REQ"};

    public NChemObj(ServerResourceBundle b, TcmISDBModel d) {
        super();
        //System.out.println("\n\n----------- Nchemobj");
        bundle = b;
        db = d;
    }


    protected void resetAllVars() {
        function = null;
        inData = null;
        for (int i = 0; i < mainCols.length; i++) keyCols.put(mainCols[i], String.valueOf(i));
    }

    protected void getResult() throws Exception {
        long sTime = new java.util.Date().getTime();
        mySendObj = new Hashtable();
        // using myRecvObj you get the function the way you want
        inData = (Hashtable) myRecvObj;
        function = inData.get("ACTION").toString();

        //System.out.println("\n\n=========== my part screen data: "+inData);

        String evalStat = (String) inData.get("EVAL_TYPE");  //trong
        if (function.equalsIgnoreCase("BUILD_NEW_MATERIAL")) {
            buildNewRequest();
            loadScreen();
        } else if (function.equalsIgnoreCase("BUILD_NEW_SIZE")) {
            buildNewSize(null, true);
            loadScreen();
        } else if (function.equalsIgnoreCase("BUILD_NEW_PART")) {
            if (evalStat.equalsIgnoreCase("convertEval") || evalStat.equalsIgnoreCase("oldEval")) {
                buildNewEval(null);
            } else {
                buildNewPart(null);
                loadScreen();
            }
        } else if (function.equalsIgnoreCase("BUILD_NEW_APPROVAL")) {
            buildNewApproval(null);
            loadScreen();
        } else if (function.equalsIgnoreCase("LOAD_SCREEN")) {
            loadScreen();
        } else if (function.equalsIgnoreCase("SUBMIT")) {
            updateData();
        } else if (function.equalsIgnoreCase("DRAFT")) {
            saveData();
        } else if (function.equalsIgnoreCase("DELETE")) {
            delete();
        } else if (function.equalsIgnoreCase("APPROVE")) {
            approveRoles();
        } else if (function.equalsIgnoreCase("REJECT")) {
            rejectRequest();
        } else if (function.equalsIgnoreCase("NA")) {
            approveRoles();
        } else if (function.equalsIgnoreCase("SEND_EVAL_FOR_APPROVAL")) {
            sendEvalForApproval();
        } else if (function.equalsIgnoreCase("GET_TABLE_EVAL_DATA")) {
            getTableEvalData();
        } else if (function.equalsIgnoreCase("PRINT_PART_SCREEN")) {
            printPartScreen();
        } else if (function.equalsIgnoreCase("PRINT_MATERIAL_SCREEN")) {
            printMaterialScreen();
        } else if (function.equalsIgnoreCase("PRINT_APPROVAL_SCREEN")) {
            printApprovalScreen();
        } else if (function.equalsIgnoreCase("PROCESS_DETAIL")) {
            getProcessDetail();
        } else if (function.equalsIgnoreCase("GET_EMISSION_POINT_EDIT_INFO")) {
            getEmissionPtEditInfo();
        } else if (function.equalsIgnoreCase("UPDATE_EMISSION_POINT")) {
            updateEmissionPoint();
        } else if (function.equalsIgnoreCase("GET_MATERIAL_USED_EDIT_INFO")) {
            getMaterialUsedEditInfo();
        } else if (function.equalsIgnoreCase("GET_WASTE_STREAM_EDIT_INFO")) {
            getWasteStreamEditInfo();
        } else if (function.equalsIgnoreCase("GET_TRAINING_EDIT_INFO")) {
            getTrainingEditInfo();
        } else if (function.equalsIgnoreCase("GET_PPE_EDIT_INFO")) {
            getPPEEditInfo();
        } else if (function.equalsIgnoreCase("SAVE_PROCESS_DETAIL")) {
            goSaveProcessDetail();
        } else if (function.equalsIgnoreCase("REMOVE_APPLICATION_LOCK")) {
            goRemoveApplicationLock();
        } else if (function.equalsIgnoreCase("RESET_APPLICATION_LOCK")) {
            goResetApplicationLock();
        } else if (function.equalsIgnoreCase("REQUEST_PROCESS_CHANGE")) {
            goRequestProcessChange();
        } else if (function.equalsIgnoreCase("GET_CATEGORY")) {
            getCategory();
        }

        long eTime = new java.util.Date().getTime();
        long min = (eTime - sTime);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new java.util.Date(min));
        //System.out.println("\n\n---- NChemObj: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds");
        //System.out.println("\n\n=----------- out data: "+mySendObj);
    }

    public void getCategory() {
        try {
            PartCategoryCriteriaBeanFactory factory = new PartCategoryCriteriaBeanFactory(db.getClient());
            mySendObj.put("CATEGORY", factory.selectByCategorySystem(new radian.tcmis.common.util.SearchCriteria(), db));
            mySendObj.put("MSG", "OK");
        }
        catch (Exception e) {
            //System.out.println("ERROR:" + e.getMessage());
            mySendObj.put("MSG", "tcmIS encountered a problem while trying to load data.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS's CSR");
        }
    }

    public void goRequestProcessChange() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("REQ_INFO", pd.goRequestProcessChange(inData));
    }

    public void goRemoveApplicationLock() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("MSG", pd.goRemoveProcessLock(inData));
    }

    public void goResetApplicationLock() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("MSG", pd.goResetProcessLock(inData));
    }

    public void goSaveProcessDetail() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("MSG", pd.goSaveProcessDetail(inData));
    }

    public void getTrainingEditInfo() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("EDIT_INFO", pd.getTrainingEditInfo(inData));
    }

    public void getPPEEditInfo() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("EDIT_INFO", pd.getPPEEditInfo(inData));
    }

    public void getWasteStreamEditInfo() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("EDIT_INFO", pd.getWasteStreamEditInfo(inData));
    }

    public void getMaterialUsedEditInfo() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("EDIT_INFO", pd.getMaterialUsedEditInfo(inData));
    }

    public void updateEmissionPoint() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("MSG", pd.updateEmissionPoint(inData));
    }

    public void getEmissionPtEditInfo() {
        ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
        mySendObj.put("EDIT_INFO", pd.getEmissionPtEditInfo(inData));
    }

    public void getProcessDetail() {
        String viewLevel = (String) inData.get("VIEW_LEVEL");
        String msg = "Record successfully";
        if (!"Viewer".equalsIgnoreCase(viewLevel)) {
            try {
                NChem nchem = new NChem(db);
                Hashtable h = (Hashtable) inData.get("CAT_ADD_DATA");
                msg = nchem.updateScreenData(h, false);
            } catch (Exception e) {
                e.printStackTrace();
                msg = "tcmIS encountered a problem while trying to load data.\nAn email has been sent to system administrator.\nPlease wait a minute and try again.\nIf problem persists contact tcmIS support @ 512-519-3918";
            }
        }
        if (msg.startsWith("Record successfully")) {
            ProcessDetailDBObj pd = new ProcessDetailDBObj(db);
            mySendObj.put("PROCESS_DATA", pd.getProcessDetail(inData));
        } else {
            Hashtable h = new Hashtable(1);
            h.put("MSG", msg);
            mySendObj.put("PROCESS_DATA", h);
        }
    } //end of method

    //trong 11-14-00
    /**
     * This method will use ACJEngine to generate a report and put it on our server (eagle/condor).
     * The method return the url of which reports can be found.
     */
    public void printApprovalScreen() {
        //System.out.println("\n\n----------- in NChemObjs.printApprovalScreen");
        String myResult = buildApprovalReport();
        if (myResult == null || BothHelpObjs.isBlankString(myResult)) {
            mySendObj.put("SUCCESS", new Boolean(false));
            mySendObj.put("MESSAGE", "Error: Unable to generate report.");
        } else {
            mySendObj.put("SUCCESS", new Boolean(true));
            mySendObj.put("URL", myResult);
        }
    }

    /**
     * This method will takes the data from the client, put it in the right form and register it with ACJEngine.
     */
    public String buildApprovalReport() {
        String url = "";
        Hashtable apprHash = (Hashtable) inData.get("APPROVAL_DATA");
        Hashtable allData = (Hashtable) apprHash.get("ALL_DATA");

        //System.out.println("\n\nValule of All Data "+allData);

        Vector approvalData = new Vector();
        Enumeration E;
        for (E = allData.keys(); E.hasMoreElements();) {
            Hashtable h = (Hashtable) allData.get(E.nextElement());
            approvalData.addElement(h);
        }

        // find how many parts -- all this just to get the material desc for each part
        Hashtable matHash = (Hashtable) inData.get("MATERIAL_DATA");
        Vector partDesc = new Vector();
        int parts = 0;
        for (E = matHash.keys(); E.hasMoreElements();) {
            String key = (String) E.nextElement();
            if (key.startsWith("KIT_")) parts++;
        }
        for (int i = 0; i < parts; i++) {
            Hashtable h = (Hashtable) matHash.get("KIT_" + i);
            String tmpDesc = (String) h.get("matDescT");
            partDesc.addElement(tmpDesc);
        }

        erw = new ACJEngine();
        erw.setDebugMode(true);
        erw.setX11GfxAvailibility(false);
        erw.setTargetOutputDevice(ACJConstants.PDF);
        ec = new ACJOutputProcessor();
        String fontmappath = radian.web.tcmisResourceLoader.getfontpropertiespath();
        ec.setPathForFontMapFile(fontmappath);

        try {
            String templatpath = radian.web.tcmisResourceLoader.getreportstempplatepath();
            erw.readTemplate("" + templatpath + "CatalogApproval.erw");
        } catch (Exception e) {
            //System.out.println("ERROR loading template");
            e.printStackTrace();
            return url;
        }

        tm = erw.getTemplateManager();
        //overloading the preset labels in ACJEngine
        tm.setLabel("MR_NUM", (String) inData.get("REQ_ID"));
        tm.setLabel("STATUS", (String) inData.get("STATUS"));
        tm.setLabel("REQ_DATE", (String) inData.get("DATE"));
        tm.setLabel("REQUESTOR", (String) inData.get("VIEW"));
        //Nawaz 01-17-02
        tm.setLabel("LAB012", (String) inData.get("FACILITY_NAME"));

        //System.out.println("\n\n================ got here: "+approvalData);
        RegisterTable[] rt = new RegisterTable[2];
        rt[0] = new RegisterTable(CatalogApproval.getVector(approvalData), "APPROVAL",
                CatalogApproval.getFieldVector(), null);
        rt[1] = new RegisterTable(Fate.getVector(approvalData, partDesc), "FATE",
                Fate.getFieldVector(), null);

        for (int k = 0; k < rt.length; k++) {
            Vector v1 = rt[k].getData();
            Vector v2 = rt[k].getMethods();
            String name = rt[k].getName();
            String where = rt[k].getWhere();
            ds.RegisterTable(v1, name, v2, where);
        }
        return (generateReport("Approval"));
    }

    //trong 11-13-00
    /**
     * This method will use ACJEngine to generate a report and put it on our server (eagle/condor).
     * The method return the url of which reports can be found.
     */
    public void printMaterialScreen() {
        String myResult = buildMaterialReport();
        if (myResult == null || BothHelpObjs.isBlankString(myResult)) {
            mySendObj.put("SUCCESS", new Boolean(false));
            mySendObj.put("MESSAGE", "Error: Unable to generate report.");
        } else {
            mySendObj.put("SUCCESS", new Boolean(true));
            mySendObj.put("URL", myResult);
        }
    }

    /**
     * This method will takes the data from the client, put it in the right form and register it with ACJEngine.
     */
    public String buildMaterialReport() {
        String url = "";
        Hashtable matHash = (Hashtable) inData.get("MATERIAL_DATA");
        Hashtable supplier = (Hashtable) matHash.get("SUPPLIER");
        Vector kits = new Vector();

        erw = new ACJEngine();
        erw.setDebugMode(true);
        erw.setX11GfxAvailibility(false);
        erw.setTargetOutputDevice(ACJConstants.PDF);
        ec = new ACJOutputProcessor();
        String fontmappath = radian.web.tcmisResourceLoader.getfontpropertiespath();
        ec.setPathForFontMapFile(fontmappath);


        try {
            String templatpath = radian.web.tcmisResourceLoader.getreportstempplatepath();
            erw.readTemplate("" + templatpath + "MaterialSize.erw");
        } catch (Exception e) {
            //System.out.println("ERROR loading template");
            e.printStackTrace();
            return url;
        }

        //System.out.println("\n\n================ my data: "+supplier+" -- "+matHash);
        tm = erw.getTemplateManager();
        //overloading the preset labels in ACJEngine
        tm.setLabel("MR_NUM", (String) inData.get("REQ_ID"));
        tm.setLabel("STATUS", (String) inData.get("STATUS"));
        tm.setLabel("REQ_DATE", (String) inData.get("DATE"));
        tm.setLabel("REQUESTOR", (String) inData.get("VIEW"));
        tm.setLabel("LAB015", (String) supplier.get("supNameT"));
        tm.setLabel("LAB020", (String) supplier.get("supContT"));
        tm.setLabel("LAB024", (String) supplier.get("supPhoneT"));
        tm.setLabel("LAB023", (String) supplier.get("supEmailT"));
        tm.setLabel("LAB025", (String) supplier.get("supFaxT"));

        //System.out.println("\n\n================ got here");
        // find how many parts
        int parts = 0;
        Enumeration E;
        for (E = matHash.keys(); E.hasMoreElements();) {
            String key = (String) E.nextElement();
            if (key.startsWith("KIT_")) parts++;
        }
        for (int i = 0; i < parts; i++) {
            Hashtable h = (Hashtable) matHash.get("KIT_" + i);
            kits.addElement(h);
        }

        //System.out.println("\n\n================ got here: "+kits);
        RegisterTable[] rt = new RegisterTable[1];
        rt[0] = new RegisterTable(MaterialSize.getVector(kits), "MATERIAL",
                MaterialSize.getFieldVector(), null);

        for (int k = 0; k < rt.length; k++) {
            Vector v1 = rt[k].getData();
            Vector v2 = rt[k].getMethods();
            String name = rt[k].getName();
            String where = rt[k].getWhere();
            ds.RegisterTable(v1, name, v2, where);
        }
        return (generateReport("Material"));
    }

    //trong 11-12-00
    /**
     * This method will use ACJEngine to generate a report and put it on our server (eagle/condor).
     * The method return the url of which reports can be found.
     */
    public void printPartScreen() {
        String myResult = buildPartReport();
        if (myResult == null || BothHelpObjs.isBlankString(myResult)) {
            mySendObj.put("SUCCESS", new Boolean(false));
            mySendObj.put("MESSAGE", "Error: Unable to generate report.");
        } else {
            mySendObj.put("SUCCESS", new Boolean(true));
            mySendObj.put("URL", myResult);
        }
    }

    /**
     * This method will takes the data from the client, put it in the right form and register it with ACJEngine.
     */
    public String buildPartReport() {
        Hashtable partData = (Hashtable) inData.get("PART_DATA");
        Hashtable part = (Hashtable) partData.get("PART_DATA");
        String url = "";
        erw = new ACJEngine();
        erw.setDebugMode(true);
        erw.setX11GfxAvailibility(false);
        erw.setTargetOutputDevice(ACJConstants.PDF);
        ec = new ACJOutputProcessor();
        String fontmappath = radian.web.tcmisResourceLoader.getfontpropertiespath();
        ec.setPathForFontMapFile(fontmappath);
        try {
            String templatpath = radian.web.tcmisResourceLoader.getreportstempplatepath();
            erw.readTemplate("" + templatpath + "CatalogPart.erw");
        } catch (Exception e) {
            //System.out.println("ERROR loading template");
            e.printStackTrace();
            return url;
        }

        tm = erw.getTemplateManager();

        //overloading the preset labels in ACJEngine
        tm.setLabel("LAB005", (String) inData.get("REQ_ID"));
        tm.setLabel("LAB006", (String) inData.get("STATUS"));
        tm.setLabel("LAB007", (String) inData.get("DATE"));
        tm.setLabel("LAB008", (String) inData.get("VIEW"));
        tm.setLabel("LAB009", (String) part.get("catalogC"));
        tm.setLabel("LAB038", (String) part.get("partNumT"));
        if ("OOR".equalsIgnoreCase(part.get("OORMM").toString())) {
            tm.setLabel("MINMAX", "");
        } else {
            tm.setLabel("OOR", "");
        }
        String qtyUnit = (String) part.get("initQty");
        qtyUnit += " " + (String) part.get("UNIT");
        tm.setLabel("LAB016", qtyUnit);
        String shelfLife = (String) part.get("shelfT");
        shelfLife += " " + (String) part.get("shelfC");
        tm.setLabel("SHELF_LIFE", shelfLife);
        String basis = (String) part.get("shelfBasis");
        if ("M".equalsIgnoreCase(basis)) {
            tm.setLabel("LAB024", "");
            tm.setLabel("LAB025", "");
        } else if ("S".equalsIgnoreCase(basis)) {
            tm.setLabel("LAB023", "");
            tm.setLabel("LAB025", "");
        } else {
            tm.setLabel("LAB023", "");
            tm.setLabel("LAB024", "");
        }
        tm.setLabel("LAB028", (String) part.get("tempC"));

        Vector spec = (Vector) partData.get("SPECS");
        Hashtable flowDown = (Hashtable) partData.get("FLOW_DOWN");
        RegisterTable[] rt = new RegisterTable[3];
        rt[0] = new RegisterTable(Specification.getVector(spec), "SPEC",
                Specification.getFieldVector(), null);
        rt[1] = new RegisterTable(QualityPackaging.getVector(flowDown, "QUALITY_FLOW"), "QUALITY",
                QualityPackaging.getFieldVector(), null);
        rt[2] = new RegisterTable(QualityPackaging.getVector(flowDown, "PACKAGE_FLOW"), "PACKAGING",
                QualityPackaging.getFieldVector(), null);

        for (int k = 0; k < rt.length; k++) {
            Vector v1 = rt[k].getData();
            Vector v2 = rt[k].getMethods();
            String name = rt[k].getName();
            String where = rt[k].getWhere();
            ds.RegisterTable(v1, name, v2, where);
        }
        return (generateReport("CatalogPart"));
    }

    public String generateReport(String screen) {
        String userId = (String) inData.get("USER_ID");
        String url = "";
        //String HomeDirectory = bundle.getString("ERW_REPORT_DIR_ACTIVE");
        String HomeDirectory = radian.web.tcmisResourceLoader.getsaveltempreportpath();
        Random ran = new Random();
        int tmp = ran.nextInt();
        Integer tmp2 = new Integer(tmp);
        reoprtlog.info("generateReport   Start " + tmp2.toString() + " ");
        erw.setCacheOption(true, "" + HomeDirectory + "" + userId + screen + tmp2.toString() + ".joi");
        try {
            erw.setDataSource(ds);
        } catch (Exception e) {
            //System.out.println("ERROR setting data source");
            e.printStackTrace();
            return url;
        }

        try {
            IViewerInterface ivi = erw.generateReport();
            if (!ec.setReportData(ivi, null)) System.exit(0);
        } catch (Exception ex) {
            //System.out.println("ERROR: While generating report");
            ex.printStackTrace();
            return url;
        }


        try {
            ec.setPDFProperty("ZipCompressed", new Boolean(false));
            /*ec.setPDFFontMap("Serif",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
            ec.setPDFFontMap("lucidasans",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
            ec.setPDFFontMap("SansSerif",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
            ec.setPDFFontMap("Dialog",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");
            ec.setPDFFontMap("lucidabright",java.awt.Font.PLAIN,"/usr/java/jdk/jre/lib/fonts/LucidaSansRegular.ttf");*/
            ec.generatePDF("" + HomeDirectory + "" + userId + screen + tmp2.toString() + ".pdf", null);
        } catch (Exception e) {
            //System.out.println("ERROR generating report");
            e.printStackTrace();
            return url;
        }
        reoprtlog.info("generateReport    Done  " + tmp2.toString() + "");
        return ("/reports/temp/" + userId + screen + tmp2.toString() + ".pdf");
    }


    //trong 3/8/00
    public void sendEvalForApproval() {
        try {
            String pr_number = (String) inData.get("REQ_NUM");
            Integer num = new Integer(pr_number);
            PurchaseRequest pr = new PurchaseRequest(db);
            pr.setPRNumber(num.intValue());
            pr.load();
            inData.put("REQ_ID", pr.getRequestId().toString());
            //got to change status on new chem -> 'pending-approval'
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            car.setRequestId(Integer.parseInt((String) inData.get("REQ_ID")));
            car.insert("REQUEST_STATUS", String.valueOf(car.PENDING_APPROVAL), car.INT);

            sendEvalConfirmationEmail();       //trong 4/26
        } catch (Exception e) {
            e.printStackTrace();
            HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
            mySendObj.put("MESSAGE", new Boolean(false));
        }
        mySendObj.put("MESSAGE", new Boolean(true));
    }

    public void getTableEvalData() {
        try {
            EngEval eEval = new EngEval(db);
            /* 4-27-03
            DbTableModel evalT = eEval.getEvalTableData(inData);
            mySendObj.put("DATA",evalT);
            */
            mySendObj.put("DATA", eEval.getEvalTableData(inData));
        } catch (Exception e) {
            e.printStackTrace();
            HelpObjs.monitor(1, "Error object(" + this.getClass().getName() + "): " + e.getMessage(), null);
            mySendObj.put("MESSAGE", new Boolean(false));
        }
    }

    public String genericSql(String query, String columnName) {
        String result = "";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getString(columnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbrs.close();
        }
        return result;
    }

    protected String buildNewRequest() throws Exception {
        String user_id = (String) inData.get("USER_ID");
        String query = "insert into catalog_add_request_new ";
        String columns = "(request_id,requestor,request_date,request_status,starting_view,catalog_id,eng_eval_facility_id,catalog_company_id,";
        String values = "";
        String tmpItemID = "";
        int nx = 0;
        try {
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            nx = car.getNext();
            values = " values (" + nx + "," + user_id + ",sysdate,0,0,";
            if (nx == 0) {
                mySendObj.put("MESSAGE", "No CatalogAddRequestNew Produced");
                return null;
            }
            Boolean isEval = (Boolean) inData.get("IS_EVAL");
            String facID = (String) inData.get("CAT_ID");     //cat_id really facility_id
            String defaultCatalogID = (String) inData.get("CATALOG_ID");
            String defaultCatalogCompanyId = (String) inData.get("CATALOG_COMPANY_ID");
            Facility fac = new Facility(db);
            Hashtable catalogInfoH = fac.getCatalogForFacility(facID);
            String catalogID = "";
            String catalogCompanyId = "";
            //check to see if default catalog is valid for facility
            //if not than leave catalog_id = null otherwise set catalog_id = selected catalog_id
            if (catalogInfoH.containsKey(defaultCatalogID + defaultCatalogCompanyId)) {
                catalogID = defaultCatalogID;
                catalogCompanyId = defaultCatalogCompanyId;
            }
            //if eval pick the first catalog from list
            if (isEval.booleanValue()) {
                Enumeration enur = catalogInfoH.keys();
                while (enur.hasMoreElements()) {
                    String[] oa = (String[]) catalogInfoH.get(enur.nextElement().toString());
                    catalogCompanyId = oa[0];
                    catalogID = oa[1];
                    break;
                }
            }
            values += "'" + catalogID + "','" + facID + "','" + catalogCompanyId + "',";

            //create catalog add request record
            if (isEval.booleanValue() ||
                    "Y".equalsIgnoreCase(genericSql("select cat_add_single_app from facility where facility_id ='" + facID + "'", "cat_add_single_app"))) {
                if (isEval.booleanValue()) {
                    String eval_type = (String) inData.get("EVAL_TYPE");
                    columns += "eng_eval_work_area,account_sys_id,";
                    values += "'" + (String) inData.get("WORK_AREA") + "','" + (String) inData.get("ACC_SYS_ID") + "',";
                    if (eval_type.equalsIgnoreCase("newEval")) {
                        columns += "eval_type,";
                        values += "'new',";
                    } else {
                        columns += "eval_type,";
                        values += "'reorder',";
                        tmpItemID = (String) inData.get("ITEM_ID");        //so I can stamp request_line_item with the correct item
                    }
                    columns += "engineering_evaluation,eval_status)";
                    values += "'Y','Pending Use Approval')";
                } else {  //if cat add single app
                    columns += "eng_eval_work_area)";
                    values += "'" + (String) inData.get("WORK_AREA") + "')";
                }
            } else {
                //replace last commas with close parathesis
                columns = columns.substring(0, columns.length() - 1) + ")";
                values = values.substring(0, values.length() - 1) + ")";
            }
            HelpObjs.insertUpdateTable(db, query + columns + values);

            // Add a record on catalogAddItem just to register for tracking
            query = "insert into catalog_add_item (request_id,part_id,line_item,starting_view) values(" + nx + ",1,1,0)";
            HelpObjs.insertUpdateTable(db, query);

            //4-11-02 if engineering evaluation then create purchase_request and request_line_item
            if (isEval.booleanValue() && (HelpObjs.countQuery(db, "select count(*) from tcmis_feature where feature = 'createMR' and feature_mode = 1") > 0)) {
                buildEngEvalMRRequest(nx, tmpItemID, catalogID);
                //for eng eval auto insert into catalog_add_spec and catalog_add_user_group
                HelpObjs.insertUpdateTable(db, "insert into catalog_add_spec(spec_id,request_id,spec_name,spec_title,spec_source,line_item)" +
                        " values('No Specification'," + nx + ",'No Specification','No Specification','catalog_add_spec',1)");
                HelpObjs.insertUpdateTable(db, "insert into catalog_add_user_group(request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,quantity_2,per_2,period_2)" +
                        " values(" + nx + ",'" + facID + "','" + (String) inData.get("WORK_AREA") + "','All','Engineering Evaluation',0,0,'days',0,0,'days')");
            }

            //clear for gabage collector
            user_id = null;
            query = null;
            columns = null;
            values = null;
            catalogInfoH = null;
            isEval = null;
            facID = null;
            fac = null;
        } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Error while in BuildNewRequest", e.getMessage());
            mySendObj.put("MESSAGE", "Error on inserting CatalogAddRequestNew. Error: " + e.getMessage());
            return null;
        }

        Integer temp = new Integer(nx);
        mySendObj.put("REQ_ID", temp.toString());

        //prepare for load screen data
        inData.put("REQ_ID", temp.toString());

        return temp.toString();
    }

    //4-11-02
    protected void buildEngEvalMRRequest(int requestID, String tmpItemID, String catalog_id) throws Exception {
        try {
            int prNumber = HelpObjs.getNextValFromSeq(db, "pr_seq");
            if (prNumber > 0) {
                //first create purchase_request record
                String query = "insert into purchase_request " +
                        "(pr_status,request_date,engineering_evaluation,pr_number,requestor,request_id,facility_id,account_sys_id) ";
                query += "values('compchk3',sysdate,'Y'," + prNumber + "," + (String) inData.get("USER_ID") + "," + requestID + ",'" + (String) inData.get("CAT_ID") + "','" + (String) inData.get("ACC_SYS_ID") + "')";
                HelpObjs.insertUpdateTable(db, query);

                //next create request_line_item
                String dbClient = db.getClient();
                String tmpPartNo = dbClient.toUpperCase() + "-EVAL-";
                Integer tmpSeq = new Integer(HelpObjs.getNextValFromSeq(db, "eval_seq"));
                if (tmpSeq.intValue() == 0) {
                    radian.web.emailHelpObj.sendtrongemail("Error while getting next eval seq for (request_id: " + requestID + " - " + tmpPartNo + ")", "Error on failed to get the next eval seq. Error: ");
                    mySendObj.put("MESSAGE", "Error on failed to get the next eval seq. Error: ");
                    return;
                }
                tmpPartNo += tmpSeq.toString();
                if (BothHelpObjs.isBlankString(tmpItemID)) {
                    tmpItemID = "442554";
                }
                query = "insert into request_line_item (pr_number,line_item,application,fac_part_no,item_id,item_type,requested_item_type,request_line_status,category_status,inventory_group,application_desc,ship_to_location_id,delivery_point,catalog_id,part_group_no) ";
                query += "select " + prNumber + ",1,'" + (String) inData.get("WORK_AREA") + "','" + tmpPartNo + "'," + tmpItemID + ",'OOR','OOR','Draft Eval','Draft Eval'," +
                        "fla.inventory_group,fla.application_desc,decode(fla.dock_selection_rule,'FIXED',fla.location_id,'')" +
                        ",decode(fla.delivery_point_selection_rule,'FIXED',fla.delivery_point,''),'" + catalog_id + "',1 from fac_loc_app fla where facility_id = '" + (String) inData.get("CAT_ID") + "' and application = '" + (String) inData.get("WORK_AREA") + "'";
                HelpObjs.insertUpdateTable(db, query);

                //last update cat_part_no and item_id in catalog_add_request_new
                query = "update catalog_add_request_new set cat_part_no = '" + tmpPartNo + "' where request_id = " + requestID;
                HelpObjs.insertUpdateTable(db, query);
                query = "update catalog_add_item set item_id = " + tmpItemID + " where line_item = 1 and request_id = " + requestID;
                HelpObjs.insertUpdateTable(db, query);
                //clear for gabage collector
                query = null;
                dbClient = null;
                tmpSeq = null;
                tmpPartNo = null;
            } else {
                radian.web.emailHelpObj.sendtrongemail("Error while in BuildEngEvalMRRequest", "Error on failed to get the next purchase request seq. Error: ");
                mySendObj.put("MESSAGE", "Error on failed to get the next seq. Error: ");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Error while in BuildEngEvalMRRequest", "Error on creating purchase_request and request_line_item. Error: " + e.getMessage());
            mySendObj.put("MESSAGE", "Error on creating purchase_request and request_line_item. Error: " + e.getMessage());
            return;
        }
    }


    protected void buildNewSize(Integer req, boolean newSizePackaging) throws Exception {
        String item_id = (String) inData.get("ITEM_ID");
        Integer req_id = (req == null ? new Integer(buildNewRequest()) : req);
        String query = "";
        try {
            NChem nchem = new NChem(db);
            Hashtable hh = nchem.getMaterialForItem(item_id);
            Vector mats = (Vector) hh.get("MATERIAL_ID");
            Vector matDesc = (Vector) hh.get("MATERIAL_DESC");
            Vector mfg = (Vector) hh.get("MFG_DESC");
            Vector tradeName = (Vector) hh.get("TRADE_NAME");
            Vector mfgPartNo = (Vector) hh.get("MFG_PART_NO");
            Vector msdsNo = (Vector) hh.get("MSDS_NO");
            Vector compPerItem = (Vector) hh.get("COMP_PER_ITEM");
            Vector grade = (Vector) hh.get("GRADE");
            Vector partSize = (Vector) hh.get("PART_SIZE");
            Vector sizeUnit = (Vector) hh.get("SIZE_UNIT");
            Vector pkgStyle = (Vector) hh.get("PKG_STYLE");
            Vector dimension = (Vector) hh.get("DIMENSION");
            Vector netWt = (Vector) hh.get("NET_WT");
            Vector netWtUnit = (Vector) hh.get("NET_WT_UNIT");
            //since I create a record in catalog_add_item in buildNewRequest, then I will update material info
            if (mats.size() > 0) {
                if (!newSizePackaging) {
                    query = "update catalog_add_item set material_id = '" + (String) mats.elementAt(0) + "',material_desc = '" + HelpObjs.singleQuoteToDouble((String) matDesc.elementAt(0)) + "',";
                    query += "manufacturer = '" + HelpObjs.singleQuoteToDouble((String) mfg.elementAt(0)) + "',mfg_trade_name = '" + HelpObjs.singleQuoteToDouble((String) tradeName.elementAt(0)) + "',";
                    query += "mfg_catalog_id = '" + HelpObjs.singleQuoteToDouble((String) mfgPartNo.elementAt(0)) + "',customer_msds_number = '" + (String) msdsNo.elementAt(0) + "',components_per_item = " + (String) compPerItem.elementAt(0);
                    query += ",part_size = " + (String) partSize.elementAt(0) + ",size_unit = '" + (String) sizeUnit.elementAt(0) + "',pkg_style = '" + (String) pkgStyle.elementAt(0) + "'";
                    query += ",dimension = '" + HelpObjs.singleQuoteToDouble((String) dimension.elementAt(0)) + "',netwt = '" + HelpObjs.singleQuoteToDouble((String) netWt.elementAt(0)) + "',netwt_unit = '" + (String) netWtUnit.elementAt(0) + "'";
                    query += ",grade = '" + HelpObjs.singleQuoteToDouble((String) grade.elementAt(0)) + "',starting_view = 1 where part_id = 1 and line_item = 1 and request_id = " + req_id.intValue();
                } else {
                    query = "update catalog_add_item set material_id = '" + (String) mats.elementAt(0) + "',material_desc = '" + HelpObjs.singleQuoteToDouble((String) matDesc.elementAt(0)) + "',";
                    query += "manufacturer = '" + HelpObjs.singleQuoteToDouble((String) mfg.elementAt(0)) + "',";
                    query += "mfg_trade_name = '" + HelpObjs.singleQuoteToDouble((String) tradeName.elementAt(0)) + "',customer_msds_number = '" + (String) msdsNo.elementAt(0) + "'";
                    query += ",grade = '" + HelpObjs.singleQuoteToDouble((String) grade.elementAt(0)) + "',starting_view = 1 where part_id = 1 and line_item = 1 and request_id = " + req_id.intValue();
                }
                HelpObjs.insertUpdateTable(db, query);
            }
            //if kits then fill the rest
            for (int i = 1; i < mats.size(); i++) {
                if (!newSizePackaging) {
                    query = "insert into catalog_add_item (request_id,line_item,starting_view,part_id,material_id,material_desc,manufacturer,mfg_trade_name,mfg_catalog_id,customer_msds_number,components_per_item,grade,part_size,size_unit,pkg_style,dimension,netwt,netwt_unit)";
                    query += " values (" + req_id.intValue() + ",1,1," + (i + 1) + ",'" + (String) mats.elementAt(i) + "','" + HelpObjs.singleQuoteToDouble((String) matDesc.elementAt(i)) + "','" + HelpObjs.singleQuoteToDouble((String) mfg.elementAt(i)) + "','";
                    query += HelpObjs.singleQuoteToDouble((String) tradeName.elementAt(i)) + "','" + HelpObjs.singleQuoteToDouble((String) mfgPartNo.elementAt(i)) + "','" + (String) msdsNo.elementAt(i) + "'," + (String) compPerItem.elementAt(i);
                    query += ",'" + HelpObjs.singleQuoteToDouble((String) grade.elementAt(i)) + "'," + (String) partSize.elementAt(i) + ",'" + (String) sizeUnit.elementAt(i) + "','" + (String) pkgStyle.elementAt(i) + "','";
                    query += HelpObjs.singleQuoteToDouble((String) dimension.elementAt(i)) + "','" + HelpObjs.singleQuoteToDouble((String) netWt.elementAt(i)) + "','" + (String) netWtUnit.elementAt(i) + "')";
                } else {
                    query = "insert into catalog_add_item (request_id,line_item,starting_view,part_id,material_id,material_desc,manufacturer,mfg_trade_name,customer_msds_number,grade)";
                    query += " values (" + req_id.intValue() + ",1,1," + (i + 1) + ",'" + (String) mats.elementAt(i) + "','" + HelpObjs.singleQuoteToDouble((String) matDesc.elementAt(i)) + "','" + HelpObjs.singleQuoteToDouble((String) mfg.elementAt(i)) + "','";
                    query += HelpObjs.singleQuoteToDouble((String) tradeName.elementAt(i)) + "','" + (String) msdsNo.elementAt(i) + "','" + HelpObjs.singleQuoteToDouble((String) grade.elementAt(i)) + "')";
                }
                HelpObjs.insertUpdateTable(db, query);
            }

            query = "update catalog_add_request_new set request_status = 1,starting_view = 1 where request_id = " + req_id.intValue();
            HelpObjs.insertUpdateTable(db, query);
            //clear for gabage collector
            query = null;
            item_id = null;
            req_id = null;
            nchem = null;
            mats = null;
            matDesc = null;
            mfg = null;
            tradeName = null;
            mfgPartNo = null;
            msdsNo = null;
        } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Error while in BuildNewSize", e.getMessage());
        }
    } //end of method

    protected void buildNewPart(Integer req) throws Exception {
        String item_id = (String) inData.get("ITEM_ID");
        Integer req_id = (req == null ? new Integer(buildNewRequest()) : req);

        buildNewSize(req_id, false);
        try {
            String query = "";
            //if this is a reorder of an engineering evaluation then see if there is a previous order for the given facility,workarea and item.
            //if so then pre-populate request with the last ordered request
            Boolean isEval = (Boolean) inData.get("IS_EVAL");
            if (isEval.booleanValue()) {
                Vector insertUpdateStatements = new Vector(4);
                String facID = (String) inData.get("CAT_ID");     //cat_id really facility_id
                String workA = (String) inData.get("WORK_AREA");
                query = "select max(carn.request_id) from catalog_add_request_new carn, catalog_add_item cai" +
                        " where carn.company_id = cai.company_id and carn.request_id = cai.request_id and carn.request_status > 4" +
                        " and carn.eng_eval_facility_id = '" + facID + "' and carn.eng_eval_work_area = '" + workA + "' and cai.item_id = " + item_id;
                String oldReqID = HelpObjs.getValueFromTable(db, query);
                if (!BothHelpObjs.isBlankString(oldReqID) && !"0".equalsIgnoreCase(oldReqID)) {
                    //first update catalog_add_request_new
                    query = "update catalog_add_request_new set (starting_view,shelf_life_source,manage_kits_as_single_unit) = " +
                            "(select 2,shelf_life_source,manage_kits_as_single_unit from" +
                            " catalog_add_request_new where request_id = " + oldReqID + ") where request_id = " + req_id;
                    insertUpdateStatements.addElement(query);
                    //udpate shelf life and storage temp
                    query = "update catalog_add_item cai set (starting_view,item_id,shelf_life_days,shelf_life_basis,storage_temp,vendor_contact_name,vendor_contact_email," +
                            "vendor_contact_phone,vendor_contact_fax,suggested_vendor) = (select 2," + item_id +
                            ",shelf_life_days,shelf_life_basis,storage_temp,vendor_contact_name,vendor_contact_email,vendor_contact_phone,vendor_contact_fax,suggested_vendor" +
                            " from catalog_add_item a where request_id = " + oldReqID + " and cai.part_id = a.part_id and cai.line_item = a.line_item) where line_item = 1 and request_id = " + req_id;
                    insertUpdateStatements.addElement(query);
                    /*
                       //third populate catalog_add_user_group
                    query = "insert into catalog_add_user_group (request_id,facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,"+
                            "quantity_2,per_2,period_2) (select "+req_id+",facility_id,work_area,user_group_id,process_desc,quantity_1,per_1,period_1,"+
                            "quantity_2,per_2,period_2 from catalog_add_user_group where request_id = "+oldReqID+")";
                    insertUpdateStatements.addElement(query);
                    //next populate catalog_add_spec
                    query = "insert into catalog_add_spec (spec_id,request_id,spec_name,spec_title,spec_version,spec_amendment,spec_date,content,on_line)"+
                            " (select spec_id,"+req_id+",spec_name,spec_title,spec_version,spec_amendment,spec_date,content,on_line"+
                            " from catalog_add_spec where request_id = "+oldReqID+")";
                    insertUpdateStatements.addElement(query);
                    //last populate catalog_add_fate_new
                    query = "insert into catalog_add_fate_new (request_id,fate_id,fate_percentage,user_group_id,part_id,facility_id,work_area)"+
                            " (select "+req_id+",fate_id,fate_percentage,user_group_id,part_id,facility_id,work_area"+
                            " from catalog_add_fate_new where request_id = "+oldReqID+")";
                    insertUpdateStatements.addElement(query);
                    */

                    db.doMultipleUpdate(insertUpdateStatements);
                } else {
                    query = "update catalog_add_request_new set request_status = 2,starting_view = 2 where request_id = " + req_id.intValue();
                    HelpObjs.insertUpdateTable(db, query);
                }
            } else {
                //set shelf life source and manage_kits_as_single_unit
                CatalogAddRequestNew carn = new CatalogAddRequestNew(db);
                carn.setDefaultShelfLifeSource(req_id.intValue(), item_id);
                carn = null;
                //set item_id and shelf life and storage temp for item
                CatalogAddItemNew cai = new CatalogAddItemNew(db);
                cai.setDefaultShelfNStorageTemp(req_id.intValue(), item_id);
                cai = null;
                //if user ask to create new facility part from 'MS' item, then leave the status and starting_view to new size/packaging
                if (HelpObjs.countQuery(db, "select count(*) from item where item_type in ('MA','OB') and item_id = " + item_id) > 0) {
                    query = "update catalog_add_request_new set request_status = 2,starting_view = 2 where request_id = " + req_id.intValue();
                    HelpObjs.insertUpdateTable(db, query);
                }
            }  //end of not eng eval
            //clear for gabage collector
            query = null;
            item_id = null;
            req_id = null;
        } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Error while in BuildNewPart", e.getMessage());
        }
    }  //end of method

    //trong
    protected void buildNewEval(Integer req) throws Exception {
        Integer req_id = (req == null ? new Integer(buildNewRequest()) : req);

        buildNewEvalSize(req_id);
        CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
        catR.setRequestId(req_id.intValue());
        catR.insert("request_status", "2", catR.INT);
        catR.insert("STARTING_VIEW", "2", CatalogAddRequestNew.INT);

        //trong 1-15-01
        String myEType = (String) inData.get("EVAL_TYPE");
        if (myEType.equalsIgnoreCase("convertEval")) {
            catR.insert("eval_type", "convert", CatalogAddRequestNew.STRING);
        } else if (myEType.equalsIgnoreCase("oldEval")) {
            catR.insert("eval_type", "reorder", CatalogAddRequestNew.STRING);
        }

        //clear flag
        catR.insert("REQUEST_REJECTED", "", catR.STRING);
        //clear roles
        this.clearAllRoles(catR.getRequestId().intValue());
    }

    protected void buildNewEvalSize(Integer req) throws Exception {
        String myReq = (String) inData.get("REQ_ID");
        Integer old_req_id = new Integer(myReq);

        CatalogAddItemNew catI = new CatalogAddItemNew(db);
        catI.setRequestId(old_req_id.intValue());
        Vector v = catI.retrieveEvalData();
        for (int i = 0; i < v.size(); i++) {
            Hashtable h = (Hashtable) v.elementAt(i);
            catI.updateDataFromPerviousRequest(req, h);
        }

        CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
        catR.setRequestId(old_req_id.intValue());
        catR.load();
        catR.setRequestId(req.intValue());
        Vector myV = new Vector();
        myV.addElement(catR.getCatalogId());
        myV.addElement(catR.getCatPartNum());
        myV.addElement(catR.getStocked());
        myV.addElement(catR.getInit90Day());
        myV.addElement(catR.getSuggestedVendor());
        myV.addElement(catR.getVendorPartNo());
        myV.addElement(catR.getContactName());
        myV.addElement(catR.getContactEmail());
        myV.addElement(catR.getContactPhone());
        myV.addElement(catR.getContactFax());
        myV.addElement(catR.getEngineeringEval());
        myV.addElement(catR.getShelfLifeSource());
        myV.addElement(catR.getManageKitsAsSingleUnit());
        catR.insert(myV);
        catR.insertIntoCatalogAddUserGroup(old_req_id.toString(), req.toString());
        catR.insertIntoCatalogAddFateNew(old_req_id.toString(), req.toString());
    }

    protected void buildNewApproval(Integer req) throws Exception {
        String item_id = (String) inData.get("ITEM_ID");
        String facid = (String) inData.get("CAT_ID");
        String fac_part_no = (String) inData.get("CAT_PART_NO");
        String partGroupNo = (String) inData.get("PART_GROUP_NO");
        String item_type = (String) inData.get("ITEM_TYPE");
        String defaultCatalogID = (String) inData.get("CATALOG_ID");
        String defaultCatalogCompanyId = (String) inData.get("CATALOG_COMPANY_ID");
        Integer req_id = (req == null ? new Integer(buildNewRequest()) : req);
        buildNewPart(req_id);

        try {
            String query = "update catalog_add_request_new set";
            CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
            catR.setRequestId(req_id.intValue());
            query += " cat_part_no = '" + fac_part_no + "',part_group_no = " + partGroupNo + ",catalog_id = '" + defaultCatalogID + "',catalog_company_id = '" + defaultCatalogCompanyId + "',";

            //setting customer_msds_number in catalog_add_item from seagate_part_stage_view
            String tmpSql = "select customer_cat_add_process from catalog_facility where catalog_id = '" + defaultCatalogID +
                    "' and facility_id ='" + facid + "' and catalog_company_id ='" + defaultCatalogCompanyId + "'";
            if ("CRA".equalsIgnoreCase(genericSql(tmpSql, "customer_cat_add_process"))) {
                CatalogAddItemNew catI = new CatalogAddItemNew(db);
                catI.setCustomerMSDSFromSeagatePartStageView(defaultCatalogID, fac_part_no, req_id.intValue());
            }

            //use the same part_description as in fac_item
            query += "(part_description,consumable) = (select part_description,consumable from fac_item" +
                    " where fac_part_no = '" + fac_part_no + "' and facility_id = '" + defaultCatalogID + "' and company_id = '" + defaultCatalogCompanyId + "'),";

            if (!BothHelpObjs.isBlankString(item_type)) {
                if ("newMM".equalsIgnoreCase(item_type) || "IncMM".equalsIgnoreCase(item_type)) {
                    item_type = "MM";
                    query += "stocked = '" + item_type + "',";
                } else if ("exitMM".equalsIgnoreCase(item_type)) {
                    item_type = "OOR";
                    query += "stocked = '" + item_type + "',";
                }
            }

            //1-16-03 get catalog item ID and catalog part directed charge number
            if (HelpObjs.countQuery(db, "Select count(*) from catalog where directed_charge_by_part = 'Y' and catalog_id = '" + defaultCatalogID + "' and company_id = '" + defaultCatalogCompanyId + "'") > 0) {
                NChem nchem = new NChem(db);
                String[] tmpCatValue = nchem.getCatalogItemIDNChargeNo(defaultCatalogID, fac_part_no, partGroupNo, item_id);
                if (tmpCatValue[0].length() > 1 && tmpCatValue[1].length() > 0) {
                    query += "catalog_item_id = '" + tmpCatValue[0] + "',cat_part_directed_chrg_no = '" + tmpCatValue[1] + "',";
                } else {
                    radian.web.emailHelpObj.sendtrongemail("Error while new approval (request ID: " + req_id.intValue() + ")", defaultCatalogID + ":" + fac_part_no + ":" + partGroupNo + ":" + item_id);
                }
            }
            query += "request_status = 3,starting_view = 3 where request_id = " + req_id.intValue();
            HelpObjs.insertUpdateTable(db, query);
            //add catalog_add_flow_down for request
            query = "insert into catalog_add_flow_down (request_id,flow_down,catalog_id) " +
                    "select a.request_id,b.flow_down,a.catalog_id from catalog_add_request_new a,fac_item_flow_down b" +
                    " where a.catalog_id = b.catalog_id and a.catalog_company_id = b.company_id and a.cat_part_no = b.fac_part_no and request_id = " + req_id.intValue();
            HelpObjs.insertUpdateTable(db, query);

            //clear for gabage collector
            //countQuery = null;
            query = null;
            item_id = null;
            facid = null;
            fac_part_no = null;
            partGroupNo = null;
            item_type = null;
            req_id = null;
            defaultCatalogID = null;
            //facO = null;
            //catid = null;
            catR = null;
        } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Error while in BuildNewApproval", e.getMessage());
        }
    }  //end of method

    protected void getRoleNeedingApproval() {
        try {
            NewChemApproval nca = new NewChemApproval(db);
            Vector big1 = nca.getRoleNeedingApproval((String) inData.get("REQ_ID"));
            if (big1 != null && big1.size() > 0) {
                mySendObj.put("NEEDING_APPROVAL", "Flag for client to know whether to do this or not.");
                mySendObj.put("ROLES", (Vector) big1.elementAt(0));
                mySendObj.put("STATUS", (Vector) big1.elementAt(1));
                mySendObj.put("APPROVERS", (Vector) big1.elementAt(2));
                mySendObj.put("DATES", (Vector) big1.elementAt(3));
                mySendObj.put("REASONS", (Vector) big1.elementAt(4));
                mySendObj.put("APPROVERS_NUMS", (Vector) big1.elementAt(5));
                mySendObj.put("ROLE_TYPE", (Vector) big1.elementAt(6));
                mySendObj.put("SUPER_USER", (Vector) big1.elementAt(7));
            }
            //System.out.println("\n\n"+mySendObj.get("ROLES")+"\n"+mySendObj.get("STATUS")+"\n"+mySendObj.get("APPROVERS_NUMS"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    void loadScreen() {
        try {
            String req_id = (String) inData.get("REQ_ID");
            String user_id = (String) inData.get("USER_ID");

            CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
            Integer req = new Integer(req_id);

            String catAddSingleApp = genericSql("select f.cat_add_single_app from facility f, catalog_add_request_new carn where f.facility_id = carn.eng_eval_facility_id and carn.request_id =" + req_id, "cat_add_single_app");
            if ("Y".equalsIgnoreCase(catAddSingleApp)) {
                catR.setRequestId(req.intValue());
                catR.load();
                Facility fac = new Facility(db);
                fac.setFacilityId(catR.getEngEvalFacilityId());
                fac.load();
                mySendObj.put("FACILITY_ID", fac.getFacilityName());
                mySendObj.put("WORK_AREA", fac.getFacAppDesc(catR.getEngEvalFacilityId(), catR.getEngEvalWorkArea()));
                mySendObj.put("CATALOG_ID", catR.getCatalogId());
                mySendObj.put("CATALOG_COMPANY_ID", catR.getCatalogCompanyId());
            }


            boolean eflag = catR.isEngEval(req.intValue());             //trong 1-15-01
            if (eflag) {
                String eval = new String("Y");
                catR.setRequestId(req.intValue());
                catR.insert("engineering_evaluation", eval, catR.STRING);
                //trong 4/3 sending facility_id and work area so i could freeze it in the approver pane (section 3 of new chem)
                catR.load();
                mySendObj.put("FACILITY_ID", catR.getEngEvalFacilityId());
                Facility fac = new Facility(db);
                mySendObj.put("WORK_AREA", fac.getFacAppDesc(catR.getEngEvalFacilityId(), catR.getEngEvalWorkArea()));
                mySendObj.put("CATALOG_ID", catR.getCatalogId());
                mySendObj.put("CATALOG_COMPANY_ID", catR.getCatalogCompanyId());
                RequestsScreen rs = new RequestsScreen(db);
                if (rs.createMR()) {
                    mySendObj.put("ENG_EVAL_MR_INFO", rs.getEngEvalMRInfo(req.intValue()));
                }
            }
            NChem nchem = new NChem(db);
            Hashtable h = nchem.getScreenData(req_id, user_id);
            mySendObj.put("SCREEN_DATA", h);
            mySendObj.put("ENG_EVAL_TYPE", catR.getEngEvalType(req.intValue()));
            mySendObj.put("CAT_ADD_SINGLE_APP", catAddSingleApp);

            this.getRoleNeedingApproval();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void print(TcmisOutputStreamServer out) {
        try {
            out.sendObject((Hashtable) mySendObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 4-27-03 I can't find where this is used
    protected DbTableModel buildTable(Hashtable h){
      Vector headV = new Vector();
      for(int i=0;i<colHeads.length;i++) headV.addElement(colHeads[i]);
      DbTableModel ctm = new DbTableModel();
      ctm.setDataVector((Vector) h.get("TABLE_DATA"),headV);
      return ctm;
    }*/

    void clearAllRoles(int req) throws Exception {
        if (req == 0 || req == 1) return;
        NewChemApproval nca = new NewChemApproval(db);
        nca.setReqId(req); // key1
        nca.clearAllRoles();
        return;
    }

    void updateData() {
        String engEval = (String) inData.get("EVAL_TYPE");
        try {
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            NChem nchem = new NChem(db);
            String msg = nchem.updateScreenData(inData, true);
            if (msg.startsWith("Error")) {
                mySendObj.put("MESSAGE", msg);
                return;
            }

            String query = "update catalog_add_request_new set submit_date = sysdate";
            if (engEval.equalsIgnoreCase("normal")) {
                car.setRequestId(Integer.parseInt((String) inData.get("REQ_ID")));
                HelpObjs.insertUpdateTable(db, query + " where request_id = " + (String) inData.get("REQ_ID"));
                //we have to run their approval before running our
                String tmpSql = "select cf.customer_cat_add_process from catalog_facility cf, catalog_add_request_new carn" +
                        " where cf.catalog_id = carn.catalog_id and cf.facility_id = carn.eng_eval_facility_id" +
                        " and cf.catalog_company_id = carn.catalog_company_id and carn.request_id =" + (String) inData.get("REQ_ID");
                if ("CRA".equalsIgnoreCase(genericSql(tmpSql, "customer_cat_add_process"))) {
                    String queryString = nchem.buildURLString((String) inData.get("REQ_ID"));
                    mySendObj.put("CRA_DATA", queryString);
                    mySendObj.put("SEAGATE_URL", radian.web.tcmisResourceLoader.getSeagateCRAURL());
                }
                //sendConfirmationEmailNew();
                setNextStatus((String) inData.get("REQ_ID"), inData.get("USER_ID").toString());
            } else if (engEval.equalsIgnoreCase("convertEval")) {
                //with the new item catalog I should not reach this condition - no exception.
                car.setRequestId(Integer.parseInt((String) inData.get("REQ_ID")));
                query += ",engineering_evaluation = 'C'";
                HelpObjs.insertUpdateTable(db, query + " where request_id = " + (String) inData.get("REQ_ID"));
                //sendConfirmationEmailNew();
                setNextStatus((String) inData.get("REQ_ID"), inData.get("USER_ID").toString());
            } else {
                car.setRequestId(Integer.parseInt((String) inData.get("REQ_ID")));
                query += ",engineering_evaluation = 'Y'";
                HelpObjs.insertUpdateTable(db, query + " where request_id = " + (String) inData.get("REQ_ID"));
                //set status at creation - buildEngEvalMRRequest
                HelpObjs.insertUpdateTable(db, "update request_line_item set request_line_status = 'Pending Use Approval',category_status = 'Open'" +
                        " where pr_number = (select pr_number from purchase_request where request_id = " + (String) inData.get("REQ_ID") + ")");
                //sendConfirmationEmailNew();
                setNextStatus((String) inData.get("REQ_ID"), inData.get("USER_ID").toString());
            }
            sendMSDSSpecEmail(nchem.getSpecIDs(car.getRequestId().toString()));
        } catch (Exception e) {
            mySendObj.put("MESSAGE", "Error updating the DB:" + e.getMessage());
            e.printStackTrace();
        }
    }

    void saveData() {
        try {
            NChem nchem = new NChem(db);
            String msg = nchem.updateScreenData(inData, false);
            mySendObj.put("MESSAGE", msg);
            if (inData.containsKey("CATEGORY_OPTION")) {
                Boolean b = (Boolean) inData.get("CATEGORY_OPTION");
                if (b.booleanValue()) {
                    int reqID = Integer.parseInt((String) inData.get("REQ_ID"));
                    mySendObj.put("CATEGORY_STATUS", nchem.getCategoryStatus(reqID, "Y"));
                    mySendObj.put("CATEGORY_STATUS_FOR_BUTTON", nchem.getCategoryStatus(reqID, "N"));
                }
            }
        } catch (Exception e) {
            mySendObj.put("MESSAGE", "Error updating the DB:" + e.getMessage());
            e.printStackTrace();
        }
    }

    void sendMSDSEmail(String emsg) {
        try {
            String user_id = (String) inData.get("USER_ID");
            Integer uID = new Integer(user_id);
            String esub = "";
            esub = new String("Please send MSDS to.");
            HelpObjs.sendMail(db, esub, emsg, uID.intValue(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method will determine whether to send user asking for MSDS and/or Spec
    void sendMSDSSpecEmail(Vector specIDs) {
        String requestID = (String) inData.get("REQ_ID");
        try {
            String user_id = (String) inData.get("USER_ID");
            Integer uID = new Integer(user_id);
            String esub = "";
            String emsg = "Record successfully stored.\n";

            //check to see if we have MSDS if not then ask user for it.
            CatalogAddItemNew ci = new CatalogAddItemNew(db);
            ci.setRequestId(Integer.parseInt(requestID));
            Vector allItems = ci.retrieve();
            Vector hasMSDSV = new Vector(10);
            if (allItems != null) {
                for (int i = 0; i < allItems.size(); i++) {
                    ci = (CatalogAddItemNew) allItems.elementAt(i);
                    Integer myMaterialID = ci.getMaterialId();
                    String msds = "N";
                    if (myMaterialID.intValue() > 0) {
                        msds = VVs.hasMSDS(db, myMaterialID) ? "Y" : "N";
                    }
                    hasMSDSV.addElement(msds);
                }
            }

            //Engineering evaluation
            boolean isEval = HelpObjs.countQuery(db, "select count(*) from catalog_add_request_new where lower(engineering_evaluation) = 'y' and request_id = " + requestID) > 0;

            //check to see if we have Spec if not then ask user for it.
            boolean needSpec = false;
            if (specIDs.contains("No Specification") || specIDs.size() < 1) {
                needSpec = false;
            } else {
                String specID = "";
                for (int i = 0; i < specIDs.size(); i++) {
                    specID += "'" + specIDs.elementAt(i) + "',";
                }
                //removing the last commas ,
                specID = specID.substring(0, specID.length() - 1);
                int specCount = HelpObjs.countQuery(db, "select count(*) from spec where spec_id in (" + specID + ")");
                if (specCount < specIDs.size()) {
                    needSpec = true;
                }
            }


            String tmpSql = "select cf.customer_cat_add_process from catalog_facility cf, catalog_add_request_new carn" +
                    " where cf.catalog_id = carn.catalog_id and cf.facility_id = carn.eng_eval_facility_id" +
                    " and cf.catalog_company_id = carn.catalog_company_id and carn.request_id =" + requestID;
            String customerCatAddProcess = genericSql(tmpSql, "customer_cat_add_process");

            if (hasMSDSV.size() == 0 || hasMSDSV.contains("N")) {  //need MSDS from user
                if (needSpec) {
                    emsg += "You can fax new MSDS and SPEC to (512)519-3990\n" +
                            "If the above number does not work please use (512)519-3901\n" +
                            "Or you can e-mail MSDS and SPEC to msds.queue@haasgroupintl.com\n" +
                            "Please enter the following on fax cover page or email header\n" +
                            "ATTN: REQUEST " + requestID;
                    if (isEval) {
                        emsg += "-Engineering Evaluation non-catalog";
                    }
                    esub = new String("Please send MSDS and SPEC to.");
                } else {
                    emsg += "You can fax new MSDS to (512)519-3990\n" +
                            "If the above number does not work please use (512)519-3901\n" +
                            "Or you can e-mail MSDS to msds.queue@haasgroupintl.com\n" +
                            "Please enter the following on fax cover page or email header\n" +
                            "ATTN: REQUEST " + requestID;
                    if (isEval) {
                        emsg += "-Engineering Evaluation non-catalog";
                    }
                    esub = new String("Please send MSDS to.");
                }
                //if add additional message below
                if ("CRA".equalsIgnoreCase(customerCatAddProcess)) {
                    emsg += "\nYour request is not complete until you fill out and submit the CRA webpage\nwhich will automatically load once you click OK.";
                }
                HelpObjs.sendMail(db, esub, emsg, uID.intValue(), false);
                mySendObj.put("MESSAGE", emsg);
            } else if (needSpec) {   //need Spec
                emsg += "You can fax new SPEC to (512)519-3990\n" +
                        "If the above number does not work please use (512)519-3901\n" +
                        "Or you can e-mail SPEC to msds.queue@haasgroupintl.com\n" +
                        "Please enter the following on fax cover page or email header\n" +
                        "ATTN: REQUEST " + requestID;
                if (isEval) {
                    emsg += "-Engineering Evaluation non-catalog";
                }
                esub = new String("Please send SPEC to.");
                //if add additional message below
                if ("CRA".equalsIgnoreCase(customerCatAddProcess)) {
                    emsg += "\nYour request is not complete until you fill out and submit the CRA webpage\nwhich will automatically load once you click OK.";
                }
                HelpObjs.sendMail(db, esub, emsg, uID.intValue(), false);
                mySendObj.put("MESSAGE", emsg);
            } else {       //does not need MSDS or Spec
                //if add additional message below
                if ("CRA".equalsIgnoreCase(customerCatAddProcess)) {
                    mySendObj.put("MESSAGE", "Records successfully stored.\nYour request is not complete until you fill out and submit the CRA webpage\nwhich will automatically load once you click OK.");
                } else {
                    mySendObj.put("MESSAGE", "Records successfully stored.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Error: while trying to send MSDS email", "Problem occur while trying to send user asking for MSDS/Spec email (" + requestID + ")");
        }
    }

    void setNextStatus(String reqID, String userid) {
        try {
            //set next status
            NewChemApproval nca = new NewChemApproval(db);
            //first set carn.cat_add_part_needs_review flag
            nca.setCatPartNeedsReview(reqID, userid);
            //then set next status
            String[] result = nca.setNextStatus(reqID);
            String done = result[0];
            String eval = result[1];
            String nextGroup = result[2];
            String possRequest = result[3];
            //send email to user if the end of approval process is reach.  Otherwise, continue to the next approval group
            if ("Done".equalsIgnoreCase(done)) {
                //check to see, if it is an engineering eval go to approve material request.
                //got to code the MR submit logic into here....
                boolean addPartToCatalog = true;
                if ("y".equalsIgnoreCase(eval)) {
                    nca.sendUserEmail(reqID);
                    //for eng eval call p_cat_add_item
                    addItemToTable(reqID);
                    //next do MR logics
                    RequestsScreen rs = new RequestsScreen(db);
                    if (rs.createMR()) { //if approve and MR was create then do MR submit logic
                        goEvalMRSubmit(reqID, userid);
                    }
                } else {
                    //add additional facilities for POSS request
                    if ("Y".equalsIgnoreCase(possRequest)) {
                        nca.updatePossFacility(reqID);
                    }

                    boolean partSeqRequired = true;
                    //first check to see if tcmIS need to generate a part_no
                    //if request doesn't contains a part_no
                    if (HelpObjs.countQuery(db, "select count(*) from catalog_add_request_new where (cat_part_no is null or vsize(cat_part_no) < 2 or ascii(cat_part_no) = 160 or cat_part_no like '%?%')  and request_id = " + reqID) > 0) {
                        Calendar cal = Calendar.getInstance();
                        Integer temp = new Integer(cal.get(cal.YEAR));
                        String partNo = "";
                        if ("FEC".equalsIgnoreCase(db.getClient())) {
                            partNo = temp.toString().substring(3) + db.getClient().toUpperCase() + "-";
                        } else {
                            StringBuilder query = new StringBuilder("select cat_add_part_no_prefix from catalog_facility cf, catalog_add_request_new carn");
                            query.append(" where cf.company_id = carn.company_id and cf.catalog_company_id = carn.catalog_company_id and");
                            query.append(" cf.catalog_id = carn.catalog_id and cf.facility_id = carn.eng_eval_facility_id and carn.request_id = ").append(reqID);
                            String catAddPartNoPrefix = genericSql(query.toString(), "cat_add_part_no_prefix");
                            //no format or prefix required
                            if (BothHelpObjs.isBlankString(catAddPartNoPrefix)) {
                                partNo = temp.toString().substring(2) + db.getClient().toUpperCase() + "-";
                            } else {
                                //if PWR then it need the following format PWR-XXXXX-XXXX
                                if ("PWR".equals(catAddPartNoPrefix)) {
                                    partNo = catAddPartNoPrefix + "-";
                                    BigDecimal tmp = new BigDecimal(cal.getTimeInMillis());
                                    //add XXXXX-XXXX
                                    partNo += tmp.toString().substring(4, 9) + "-" + tmp.toString().substring(9);
                                    partSeqRequired = false;
                                } else {
                                    partNo = catAddPartNoPrefix + "-";
                                }
                            }

                        }
                        if (HelpObjs.countQuery(db, "select count(*) from catalog_facility cf, catalog_add_request_new carn where cf.cat_part_add_auto_gen_null_pn = 'N' and cf.catalog_id = carn.catalog_id and cf.facility_id = carn.eng_eval_facility_id and carn.request_id =" + reqID) > 0) {
                            radian.web.emailHelpObj.sendtrongemail("Can't automatically create catalog part no: " + reqID, "\nApprover: " + userid);
                            mySendObj.put("MESSAGE", "Error occurred while setting request to next status.\nAn e-mail has been sent to system admin.");
                            return;
                        }
                        if (partSeqRequired) {
                            Integer partSeq = new Integer(HelpObjs.getNextValFromSeq(db, "part_seq"));
                            partNo += partSeq.toString();
                        }
                        HelpObjs.insertUpdateTable(db, "update catalog_add_request_new set cat_part_no = '" + partNo + "' where request_id = " + reqID);

                        if (HelpObjs.countQuery(db, "select count(*) from catalog_add_request_new carn where carn.company_id = 'UTC' and carn.catalog_id = 'PWA AM' and" +
                                " carn.eng_eval_facility_id = 'PWA- TEC' and carn.request_id = " + reqID) > 0) {
                            addPartToCatalog = false;
                        }

                    } else {
                        if (HelpObjs.countQuery(db, "select count(*) from catalog_add_request_new carn where carn.company_id = 'UTC' and carn.catalog_id = 'PWA AM' and" +
                                " carn.eng_eval_facility_id = 'PWA- TEC' and carn.request_id = " + reqID) > 0) {
                            //determine whether this is a MRO or chemical request
                            String tmpPartNo = genericSql("select cat_part_no from catalog_add_request_new where request_id = " + reqID, "cat_part_no");
                            String tmp2 = tmpPartNo.substring(0, 8);  // I am getting ABC 1234 from part no
                            String[] tmp3 = tmp2.split(" ");
                            if (tmp3.length == 2) {
                                if ("TEC".equalsIgnoreCase(tmp3[0])) {
                                    try {
                                        BigDecimal number2 = new BigDecimal(tmp3[1]);
                                        if (number2.intValue() >= 5000) {
                                            //MRO part add it to exception table so that the feed will not change it
                                            db.doUpdate("insert into pwa_catalog_item_id_exception (catalog_item_id,comments) values ('" + tmpPartNo + "','Non-MSDS material thru catalog adds')");
                                        } else {
                                            //NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
                                            db.doUpdate("delete from catalog_add_user_group where request_id = " + reqID);
                                        }
                                    } catch (Exception ee) {
                                        //NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
                                        db.doUpdate("delete from catalog_add_user_group where request_id = " + reqID);
                                    }
                                } else {
                                    //NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
                                    db.doUpdate("delete from catalog_add_user_group where request_id = " + reqID);
                                }
                            } else {
                                //NOT MRO part remove catalog_add_user_group if use_approval is control by pratt feed
                                db.doUpdate("delete from catalog_add_user_group where request_id = " + reqID);
                            }
                        }
                    }

                    //update temporary part in catalog
                    Hashtable carnInfo = nca.getCatalogAddRequestInfo(reqID);
                    if ("Y".equalsIgnoreCase((String) carnInfo.get("CREATE_TEMPORARY_ITEM"))) {
                        updateCatalogPart(reqID);
                    } else {
                        //add part to catalog
                        if (addPartToCatalog) {
                            createNewCatalogPart(nca, reqID);
                        }
                    }
                }
            } else {
                //sending email to the next group/group seq
                if (nextGroup.equalsIgnoreCase("New group")) {
                    nca.sendApproversEmail(reqID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    void sendConfirmationEmailNew() {
        String reqID = (String) inData.get("REQ_ID");
        try {
            NewChemApproval nca = new NewChemApproval(db);
            String[] tmp = nca.setNextStatus(reqID);
            String nextGroup = tmp[2];
            if (nextGroup.equalsIgnoreCase("New group")) {
                //System.out.println("\n-------- not sending email to approver - TESTING");
                nca.sendApproversEmail(reqID);
            } else if (nextGroup.equalsIgnoreCase("Done")) {
                nca.sendUserEmail(reqID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.sendtrongemail("Send confirmation email failed (request ID: " + reqID + ")", "Unable to send email to chemical approvers");
        }
    }

    void sendConfirmationEmail() {
        //Send email  to the approvers
        try {
            String reqid = (String) inData.get("REQ_ID");
            String user_id = (String) inData.get("USER_ID");
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            car.setRequestId(Integer.parseInt(reqid));
            car.load();
            Personnel p = new Personnel(db);
            p.setPersonnelId(car.getRequestor().intValue());
            p.load();

            //build the material table data
            NewChemTrackTable nctt = new NewChemTrackTable(db);
            Vector big1 = nctt.getApprDetailData(reqid);
            Vector roleV = new Vector();
            Vector statusV = new Vector();
            Vector appV = new Vector();
            Vector dateV = new Vector();
            Vector reasonV = new Vector();
            Vector appNumV = new Vector();
            Vector roleTypeV = new Vector();
            if (big1 != null && big1.size() > 0) {
                roleV = (Vector) big1.elementAt(0);
                statusV = (Vector) big1.elementAt(1);
                appV = (Vector) big1.elementAt(2);
                dateV = (Vector) big1.elementAt(3);
                reasonV = (Vector) big1.elementAt(4);
                appNumV = (Vector) big1.elementAt(5);
                roleTypeV = (Vector) big1.elementAt(6);
            }

            String esub = "";
            String emsg = "";

            esub = new String("tcmIS New Chemical Request " + reqid + " is waiting for your approval.");
            emsg = new String("Status     : WAITING APPROVAL\n");
            emsg = emsg + "Request Id : " + reqid + "\n";
            emsg = emsg + "Requestor  : " + p.getFullName() + "\n";
            emsg = emsg + "Facility   : " + car.getCatalogId() + "\n";
            emsg = emsg + "Part Number: " + car.getCatPartNum() + "\n\n";

            emsg = emsg + "Roles:\n--------------------------\n";

            for (int i = 0; i < roleV.size(); i++) {
                emsg = emsg + "Name  : " + (roleV.elementAt(i) == null ? "" : roleV.elementAt(i)) + "\n";
                emsg = emsg + "Status: " + (statusV.elementAt(i) == null ? "" : statusV.elementAt(i)) + "\n";
                emsg = emsg + "Date  : " + (dateV.elementAt(i) == null ? "" : BothHelpObjs.formatDate("toCharfromDB", dateV.elementAt(i).toString())) + "\n";
                emsg = emsg + "By    : ";

                Personnel p2 = new Personnel(db);
                p2.setPersonnelId(Integer.parseInt(appNumV.elementAt(i).toString()));
                p2.load();
                emsg = emsg + (p2.getFullName() == null ? "" : p2.getFullName()) + "\n";
                emsg = emsg + "Notes : " + (reasonV.elementAt(i) == null ? "" : reasonV.elementAt(i)) + "\n\n";
            }

            Vector hol = new Vector();
            for (int i = 0; i < appNumV.size(); i++) {
                int nn = Integer.parseInt(appNumV.elementAt(i).toString());
                if (!hol.contains(new Integer(nn))) {
                    hol.addElement(new Integer(nn));
                    HelpObjs.sendMail(db, esub, emsg, nn, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendEvalConfirmationEmail() {
        //Send email  to the approvers
        try {
            String reqid = (String) inData.get("REQ_ID");
            String user_id = (String) inData.get("USER_ID");
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            car.setRequestId(Integer.parseInt(reqid));
            car.load();
            Personnel p = new Personnel(db);
            p.setPersonnelId(car.getRequestor().intValue());
            p.load();

            //build the material table data
            NewChemTrackTable nctt = new NewChemTrackTable(db);
            Vector big1 = nctt.getApprDetailData(reqid);
            Vector roleV = new Vector();
            Vector statusV = new Vector();
            Vector appV = new Vector();
            Vector dateV = new Vector();
            Vector reasonV = new Vector();
            Vector appNumV = new Vector();
            Vector roleTypeV = new Vector();
            if (big1 != null && big1.size() > 0) {
                roleV = (Vector) big1.elementAt(0);
                statusV = (Vector) big1.elementAt(1);
                appV = (Vector) big1.elementAt(2);
                dateV = (Vector) big1.elementAt(3);
                reasonV = (Vector) big1.elementAt(4);
                appNumV = (Vector) big1.elementAt(5);
                roleTypeV = (Vector) big1.elementAt(6);
            }

            String esub = "";
            String emsg = "";

            esub = new String("tcmIS New ENGINEERING EVALUATION Request " + reqid + " is waiting for your approval.");
            emsg = new String("Status     : WAITING APPROVAL\n");
            emsg = emsg + "Request Id : " + reqid + "\n";
            emsg = emsg + "Requestor  : " + p.getFullName() + "\n";
            emsg = emsg + "Facility   : " + car.getCatalogId() + "\n";
            emsg = emsg + "Part Number: " + car.getCatPartNum() + "\n\n";

            emsg = emsg + "Roles:\n--------------------------\n";

            for (int i = 0; i < roleV.size(); i++) {
                emsg = emsg + "Name  : " + (roleV.elementAt(i) == null ? "" : roleV.elementAt(i)) + "\n";
                emsg = emsg + "Status: " + (statusV.elementAt(i) == null ? "" : statusV.elementAt(i)) + "\n";
                emsg = emsg + "Date  : " + (dateV.elementAt(i) == null ? "" : BothHelpObjs.formatDate("toCharfromDB", dateV.elementAt(i).toString())) + "\n";
                emsg = emsg + "By    : ";

                Personnel p2 = new Personnel(db);
                p2.setPersonnelId(Integer.parseInt(appNumV.elementAt(i).toString()));
                p2.load();
                emsg = emsg + (p2.getFullName() == null ? "" : p2.getFullName()) + "\n";
                emsg = emsg + "Notes : " + (reasonV.elementAt(i) == null ? "" : reasonV.elementAt(i)) + "\n\n";
            }

            Vector hol = new Vector();
            for (int i = 0; i < appNumV.size(); i++) {
                int nn = Integer.parseInt(appNumV.elementAt(i).toString());
                if (!hol.contains(new Integer(nn))) {
                    hol.addElement(new Integer(nn));
                    HelpObjs.sendMail(db, esub, emsg, nn, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //trong 3/8/00
    void sendConfirmationEmailTest() {
        //Send email  to the approvers
        try {
            String reqid = (String) inData.get("REQ_ID");
            String user_id = (String) inData.get("USER_ID");
            CatalogAddRequestNew car = new CatalogAddRequestNew(db);
            car.setRequestId(Integer.parseInt(reqid));
            car.load();
            Personnel p = new Personnel(db);
            p.setPersonnelId(car.getRequestor().intValue());
            p.load();

            //build the material table data
            NewChemTrackTable nctt = new NewChemTrackTable(db);
            Vector big1 = nctt.getApprDetailData(reqid);
            Vector roleV = new Vector();
            Vector statusV = new Vector();
            Vector appV = new Vector();
            Vector dateV = new Vector();
            Vector reasonV = new Vector();
            Vector appNumV = new Vector();
            Vector roleTypeV = new Vector();
            if (big1 != null && big1.size() > 0) {
                roleV = (Vector) big1.elementAt(0);
                statusV = (Vector) big1.elementAt(1);
                appV = (Vector) big1.elementAt(2);
                dateV = (Vector) big1.elementAt(3);
                reasonV = (Vector) big1.elementAt(4);
                appNumV = (Vector) big1.elementAt(5);
                roleTypeV = (Vector) big1.elementAt(6);
            }

            String esub = "";
            String emsg = "";

            esub = new String("tcmIS New Chemical Request " + reqid + " is waiting for your approval.");
            emsg = new String("Status     : WAITING APPROVAL\n");
            emsg = emsg + "Request Id : " + reqid + "\n";
            emsg = emsg + "Requestor  : " + p.getFullName() + "\n";
            emsg = emsg + "Facility   : " + car.getCatalogId() + "\n";
            emsg = emsg + "Part Number: " + car.getCatPartNum() + "\n\n";

            emsg = emsg + "Roles:\n--------------------------\n";

            for (int i = 0; i < roleV.size(); i++) {
                emsg = emsg + "Name  : " + (roleV.elementAt(i) == null ? "" : roleV.elementAt(i)) + "\n";
                emsg = emsg + "Status: " + (statusV.elementAt(i) == null ? "" : statusV.elementAt(i)) + "\n";
                emsg = emsg + "Date  : " + (dateV.elementAt(i) == null ? "" : BothHelpObjs.formatDate("toCharfromDB", dateV.elementAt(i).toString())) + "\n";
                emsg = emsg + "By    : ";

                Personnel p2 = new Personnel(db);
                p2.setPersonnelId(Integer.parseInt(appNumV.elementAt(i).toString()));
                p2.load();
                emsg = emsg + (p2.getFullName() == null ? "" : p2.getFullName()) + "\n";
                emsg = emsg + "Notes : " + (reasonV.elementAt(i) == null ? "" : reasonV.elementAt(i)) + "\n\n";
            }
            radian.web.emailHelpObj.sendtrongemail(esub, emsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void delete() {
        try {
            //if this request contains process data then reset other request(s) that
            if (HelpObjs.countQuery(db, "select count(*) from ca_process where request_id = " + (String) inData.get("REQ_ID")) > 0) {
                ProcessDetailDBObj processD = new ProcessDetailDBObj(db);
                processD.resetOtherRequestWProcess((String) inData.get("REQ_ID"), (String) inData.get("USER_ID"));
            }

            Vector v = new Vector(2);
            v.addElement((String) inData.get("REQ_ID"));
            v.addElement("ERROR_VAL");
            String val = db.doInvoiceProcedure("p_del_new_chem_req", v);     //I can run the same procedure on sql worksheet (execute p_del_new_chem_req_test(request_id))
            //System.out.println("----- error: "+val);
            if ("OK".equalsIgnoreCase(val)) {
                mySendObj.put("MESSAGE", "Record deleted.");
                mySendObj.put("SUCCEED", new Boolean(true));
            } else {
                mySendObj.put("MESSAGE", "An error occurred while deleting record.\nAn e-mail was sent to system admin.");
                mySendObj.put("SUCCEED", new Boolean(false));
                radian.web.emailHelpObj.sendtrongemail("Error while deleting new chem add (request_id: " + (String) inData.get("REQ_ID") + ")", val);
            }
        } catch (Exception e) {
            mySendObj.put("MESSAGE", "An error occurred while deleting record.\nAn e-mail was sent to system admin.");
            mySendObj.put("SUCCEED", new Boolean(false));
            radian.web.emailHelpObj.sendtrongemail("Error while deleting new chem add (request_id: " + (String) inData.get("REQ_ID") + ")", e.getMessage());
        }
    }


    public String approveRequest(Hashtable approvedData) throws Exception {
        String result = "OK";
        String reqID = (String) approvedData.get("REQ_ID");
        String userid = (String) approvedData.get("USER_ID");
        try {
            //insert record into catalog_add_approval
            String query = "";
            String[] oa = (String[]) approvedData.get("ROLES_DATA");
            String role = oa[0];
            String comment = oa[1];
            query = "insert into catalog_add_approval (company_id,request_id,facility_id,catalog_id,catalog_company_id,approval_role,chemical_approver,status,approval_date,reason)" +
                    " select company_id,request_id,eng_eval_facility_id,catalog_id,catalog_company_id,'" + role + "'," + userid + ",'Approved',sysdate,'" + HelpObjs.singleQuoteToDouble(comment) + "' from catalog_add_request_new where request_id = " + reqID;
            HelpObjs.insertUpdateTable(db, query);
            boolean approvedByItemQc = false;
            if ("Item QC".equalsIgnoreCase(role)) {
                approvedByItemQc = true;
                CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
                catR.setRequestId(Integer.parseInt(reqID));
                catR.load();
                if ("y".equalsIgnoreCase(catR.getEngineeringEval())) {
                    try {
                        db.doUpdate("update request_line_item set item_id = " + catR.getItemId().toString() + " where pr_number = (select pr_number from purchase_request where request_id = " + reqID + ")");
                    } catch (Exception ee) {
                        result = "Error";
                        radian.web.emailHelpObj.sendtrongemail("Updating item ID for request line item failed", "Updating item ID for request line item failed (request: " + reqID + ")");
                    }
                }
            }
            //set next status
            if (approvedByItemQc) {
                //update catalog_add_item.item_qc_status
                query = "update catalog_add_item set item_qc_status = 'Approved' where request_id = "+reqID;
                HelpObjs.insertUpdateTable(db, query);
                CatalogAddRequestProcess catalogAddProcess = new CatalogAddRequestProcess(db.getClient().toUpperCase());
                catalogAddProcess.approveRequestFromNChemObj(new BigDecimal(reqID), new BigDecimal(userid));
            } else {
                setNextStatus(reqID, userid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error";
            radian.web.emailHelpObj.sendtrongemail("Error while trying to approving request: " + reqID, e.getMessage() + "\nApprover: " + userid);
        }
        return result;
    }

    //for eng eval add item to component_inventory_group
    void addItemToTable(String reqID) {
        try {
            //call store procedure to add item to table
            NChem nchem = new NChem(db);
            Vector v = new Vector();
            v.addElement(reqID);
            v.addElement(nchem.getFacilityDefaultIg(reqID));
            v.addElement("error_val");
            String val = db.doInvoiceProcedure("p_add_catalog_add_item", v);     //the naming of this method is my bad.  I didn't think ahead
            //System.out.println("--------- value from p_add_catalog_add_item procedure: "+val);
            if (!BothHelpObjs.isBlankString(val)) {
                String esub = "Error while calling store procedure to add item to table (request #" + reqID + ")";
                String emsg = "Procedure: p_add_catalog_add_item has an error.\n" + val + "\n" +
                        "Check data then run execute customer.p_add_catalog_add_item('COMPANY',requestID,inventoryGroup)";
                emsg += val;
                HelpObjs.sendMail(db, esub, emsg, 86030, false);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            String esub = "Error while addItemToTable approving request (request #" + reqID + ")";
            String emsg = "Procedure: p_add_catalog_add_item throw an exception.\n" +
                    "Check data then run execute customer.p_add_catalog_add_item('COMPANY',requestID,inventoryGroup)";
            HelpObjs.sendMail(db, esub, emsg, 86030, false);
        }
    } //end of method

    void createNewCatalogPart(NewChemApproval nca, String reqID) {
        try {
            //call store procedure to add part to catalog
            Vector v = new Vector();
            v.addElement(reqID);
            v.addElement("error_val");
            String val = db.doInvoiceProcedure("p_add_cat_part_request", v);     //the naming of this method is my bad.  I didn't think ahead
            //System.out.println("--------- value from procedure: "+val+"*");
            if (!BothHelpObjs.isBlankString(val) && !val.startsWith("OK")) {
                String esub = "Error while calling store procedure to add part to catalog (request #" + reqID + ")";
                String emsg = "Procedure: p_add_cat_part_request has an error.\n" +
                        "Check data then run execute customer.p_add_cat_part_request_test('COMPANY',requestID)";
                emsg += val;
                HelpObjs.sendMail(db, esub, emsg, 86030, false);
            } else {
                nca.sendUserEmail(reqID);     //don't send email to user if it is not ready
                //if this request is for a new MM part then notify hub personnel
                if (HelpObjs.countQuery(db, "select count(*) from catalog_add_request_new where stocked = 'MM' and starting_view < 3 and request_id = " + reqID) > 0) {
                    nca.sendHubEmail(reqID);
                }   //end of if new MM
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            String esub = "Error while createNewCatalogPart approving request (request #" + reqID + ")";
            String emsg = "Procedure: p_add_cat_part_request throw an exception.\n" +
                    "Check data then run execute customer.p_add_cat_part_request_test('COMPANY',requestID)";
            HelpObjs.sendMail(db, esub, emsg, 86030, false);
        }
    } //end of method

    void updateCatalogPart(String reqID) {
        try {
            //call store procedure to update part to catalog
            Vector v = new Vector();
            v.addElement(reqID);
            v.addElement("error_val");
            String val = db.doInvoiceProcedure("pkg_catalog_item.p_update_temporary_item_shell", v);     //the naming of this method is my bad.  I didn't think ahead
            if (!val.startsWith("OK")) {
                String esub = "Error while calling store procedure to update part to catalog (request #" + reqID + ")";
                String emsg = "Procedure: pkg_catalog_item.p_update_temporary_item_shell has an error.\n" +
                        "Check data then run execute pkg_catalog_item.p_update_temporary_item_shell('COMPANY',requestID)";
                emsg += val;
                HelpObjs.sendMail(db, esub, emsg, 86030, false);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            String esub = "Error while updateCatalogPart approving request (request #" + reqID + ")";
            String emsg = "Procedure: pkg_catalog_item.p_update_temporary_item throw an exception.\n" +
                    "Check data then run execute pkg_catalog_item.p_update_temporary_item('COMPANY',requestID)";
            HelpObjs.sendMail(db, esub, emsg, 86030, false);
        }
    } //end of method

    void POSSRevise(NewChemApproval nca, String reqID) {
        try {
            CatalogAddRequestNew carn = new CatalogAddRequestNew(db);
            Hashtable h = carn.getPOSSInfo(reqID);
            String catID = (String) h.get("CATALOG_ID");
            String catPartNo = (String) h.get("CAT_PART_NO");
            String partGroupNo = (String) h.get("PART_GROUP_NO");

            //first update fac_item
            String query = "update fac_item set";
            if (!BothHelpObjs.isBlankString((String) h.get("SHELF_LIFE"))) {
                query += " shelf_life = " + (String) h.get("SHELF_LIFE") + "," +
                        " shelf_life_basis = '" + (String) h.get("SHELF_LIFE_BASIS") + "'," +
                        " shelf_life_unit = '" + (String) h.get("SHELF_LIFE_UNIT") + "',";
            }
            if (!BothHelpObjs.isBlankString((String) h.get("PART_DESCRIPTION"))) {
                query += " part_description = '" + (String) h.get("PART_DESCRIPTION") + "',";
            }
            //if something need to be change then removing the last commas ',' and update changes
            if (query.indexOf(",") > 1) {
                query = query.substring(0, query.indexOf(','));
                query += " where fac_part_no = '" + catPartNo + "' and facility_id = '" + catID + "'";
                db.doUpdate(query);
            }
            //next update catalog_part_item_group
            query = "select count(*) from catalog_part_item_group where catalog_id = '" + catID + "' and cat_part_no = '" + catPartNo + "'" +
                    " and part_group_no = " + (String) h.get("PART_GROUP_NO") + " and status = 'A'";
            //if part exist then update, otherwise insert
            if (HelpObjs.countQuery(db, query) > 0) {
                query = "update catalog_part_item_group set item_id = " + (String) h.get("ITEM_ID") + " where catalog_id = '" + catID + "' and cat_part_no = '" + catPartNo + "'" +
                        " and part_group_no = " + partGroupNo + " and status = 'A'";
                db.doUpdate(query);
            } else {
                query = "insert into catalog_part_item_group (catalog_id,cat_part_no,part_group_no,status,priority,item_id)" +
                        " values('" + catID + "','" + catPartNo + "'," + partGroupNo + ",'A',1," + (String) h.get("ITEM_ID") + ")";
                db.doUpdate(query);
            }

            //check catalog_part_inventory
            query = "select count(*) from catalog_part_inventory where catalog_id = '" + catID + "' and cat_part_no = '" + catPartNo + "'" +
                    " and part_group_no = " + (String) h.get("PART_GROUP_NO") + " and inventory_group = 'Tucson Ray'";
            //if part does not exist then insert record
            if (HelpObjs.countQuery(db, query) == 0) {
                query = "insert into catalog_part_inventory (catalog_id,cat_part_no,inventory_group,part_group_no,reorder_point,stocking_level,stocking_method)" +
                        " values('" + catID + "','" + catPartNo + "','Tucson Ray'," + partGroupNo + ",0,0,'" + (String) h.get("STOCKED") + "')";
                db.doUpdate(query);
            }

            //then update fac_spec
            //first delete record from fac_spec
            query = "delete from fac_spec where facility_id = '" + catID + "' and fac_part_no = '" + catPartNo + "'" +
                    " and spec_library = '" + (String) h.get("SPEC_LIBRARY") + "'";
            db.doUpdate(query);
            //next insert into fac_spec from catalog_add_spec
            query = "insert into fac_spec (facility_id,fac_part_no,spec_id,spec_library)" +
                    " select carn.catalog_id,carn.cat_part_no,cas.spec_id,'" + (String) h.get("SPEC_LIBRARY") + "' spec_library" +
                    " from catalog_add_request_new carn, catalog_add_spec cas" +
                    " where carn.request_id = cas.request_id and carn.request_id = " + reqID;
            db.doUpdate(query);

            //finally send user email
            nca.sendUserEmail(reqID);     //don't send email to user if it is not ready
            //if this request is for a new MM part then notify hub personnel
            if (HelpObjs.countQuery(db, "select count(*) from catalog_add_request_new where stocked = 'MM' and starting_view < 3 and request_id = " + reqID) > 0) {
                nca.sendHubEmail(reqID);
            }   //end of if new MM
        } catch (Exception ee) {
            ee.printStackTrace();
            String esub = "Error while update POSS revise (request #" + reqID + ")";
            String emsg = "POSS revise\n" + ee.getMessage();
            radian.web.emailHelpObj.sendtrongemail(esub, emsg);
        }
    } //end of method

    protected void rejectRequest() throws Exception {
        String reqID = (String) inData.get("REQ_ID");
        String userid = (String) inData.get("USER_ID");
        try {
            //insert record into catalog_add_approval
            String query = "";
            String fac = (String) inData.get("FAC_ID");
            Vector rolesData = (Vector) inData.get("ROLES_DATA");
            for (int i = 0; i < rolesData.size(); i++) {
                String[] oa = (String[]) rolesData.elementAt(i);
                String role = oa[0];
                String comment = oa[1];
                query = "insert into catalog_add_approval (company_id,request_id,facility_id,catalog_id,catalog_company_id,approval_role,chemical_approver,status,approval_date,reason)" +
                        " select company_id,request_id,'" + fac + "',catalog_id,catalog_company_id,'" + role + "'," + userid + ",'Rejected',sysdate,'" + HelpObjs.singleQuoteToDouble(comment) + "'" +
                        " from catalog_add_request_new where request_id = " + reqID;
                HelpObjs.insertUpdateTable(db, query);
            }

            query = "update catalog_add_request_new set request_status = 7,approval_group_seq = 0,request_rejected = 'Y'";
            if (HelpObjs.countQuery(db, "select count(*) from catalog_add_request_new where lower(engineering_evaluation) = 'y' and request_id = " + reqID) > 0) {
                query += ",eval_status = 'Rejected'";
                //also change purchase request to rejected this inturn (trigger) request line item to rejected as well
                HelpObjs.insertUpdateTable(db, "update purchase_request set pr_status = 'rejected' where request_id = " + reqID);
            }
            query += " where request_id = " + reqID;
            HelpObjs.insertUpdateTable(db, query);

            //remove cat_add_process_lock
            //if this request contains process data then reset other request(s) that
            if (HelpObjs.countQuery(db, "select count(*) from ca_process where request_id = " + (String) inData.get("REQ_ID")) > 0) {
                ProcessDetailDBObj processD = new ProcessDetailDBObj(db);
                processD.resetOtherRequestWProcess((String) inData.get("REQ_ID"), (String) inData.get("USER_ID"));
            }

            //set next status
            NewChemApproval nca = new NewChemApproval(db);
            nca.sendUserEmail(reqID);

            //mySendObj.put("FULL_STATUS","Done");
            mySendObj.put("MESSAGE", "Request processed.");
        } catch (Exception e) {
            e.printStackTrace();
            mySendObj.put("MESSAGE", "Error occurred while rejecting request.\nAn e-mail has been sent to system admin.");
            radian.web.emailHelpObj.sendtrongemail("Error while trying to reject request: " + reqID, e.getMessage() + "\nApprover: " + userid);
        }
    }  //end of method

    protected void approveRoles() throws Exception {
        String reqID = (String) inData.get("REQ_ID");
        String userid = (String) inData.get("USER_ID");
        boolean approvedNA = "NA".equalsIgnoreCase((String) inData.get("ACTION"));
        try {
            //first I need to save data
            NChem nchem = new NChem(db);
            String msg = nchem.updateScreenData(inData, false);
            if (msg.startsWith("Error")) {
                mySendObj.put("MESSAGE", msg);
                return;
            }

            //insert record into catalog_add_approval
            String query = "";
            String fac = (String) inData.get("FAC_ID");
            Vector rolesData = (Vector) inData.get("ROLES_DATA");
            for (int i = 0; i < rolesData.size(); i++) {
                String[] oa = (String[]) rolesData.elementAt(i);
                String role = oa[0];
                String comment = oa[1];
                String tmpStatus = "Approved";
                if (approvedNA) {
                    tmpStatus = "N/A";
                }
                query = "insert into catalog_add_approval (company_id,request_id,facility_id,catalog_id,catalog_company_id,approval_role,chemical_approver,status,approval_date,reason)" +
                        " select company_id,request_id,'" + fac + "',catalog_id,catalog_company_id,'" + role + "'," + userid + ",'" + tmpStatus + "',sysdate,'" + HelpObjs.singleQuoteToDouble(comment) + "'" +
                        " from catalog_add_request_new where request_id = " + reqID;
                HelpObjs.insertUpdateTable(db, query);
                if ("Item QC".equalsIgnoreCase(role)) {
                    CatalogAddRequestNew catR = new CatalogAddRequestNew(db);
                    catR.setRequestId(Integer.parseInt(reqID));
                    catR.load();
                    if ("y".equalsIgnoreCase(catR.getEngineeringEval())) {
                        try {
                            db.doUpdate("update request_line_item set item_id = " + catR.getItemId().toString() + " where pr_number = (select pr_number from purchase_request where request_id = " + reqID + ")");
                        } catch (Exception ee) {
                            radian.web.emailHelpObj.sendtrongemail("Updating item ID for request line item failed", "Updating item ID for request line item failed (request: " + reqID + ")");
                        }
                    }
                }
            }
            //set next status
            setNextStatus(reqID, userid);
            mySendObj.put("MESSAGE", "Request processed.");
        } catch (Exception e) {
            e.printStackTrace();
            mySendObj.put("MESSAGE", "Error occurred while approving request.\nAn e-mail has been sent to system admin.");
            radian.web.emailHelpObj.sendtrongemail("Error while trying to approving request: " + reqID, e.getMessage() + "\nApprover: " + userid);
        }
    }  //end of method

    //trong 5-03-01 if an eval from swa then don't create sales order
    public void goEvalMRSubmit(String reqId, String userId) {
        Integer req_id = new Integer(reqId);
        Integer user_id = new Integer(userId);
        try {
            PurchaseRequest pr = new PurchaseRequest(db);
            Integer pr_number = pr.getEvalPrNumber(req_id.intValue());
            RequestsScreen rs = new RequestsScreen(db);
            Vector allInfo = rs.getScreenData(user_id.intValue(), pr_number.intValue());
            Hashtable headerInfo = new Hashtable();
            headerInfo = (Hashtable) allInfo.elementAt(0);
            Vector detailInfo = (Vector) allInfo.elementAt(1);

            Hashtable h = rs.submitRequest(new String("Submit"), headerInfo, detailInfo, userId, this.getBundle().get("DB_CLIENT").toString());
            Boolean b = (Boolean) h.get("RETURN_CODE");
            Hashtable retH = new Hashtable();
            retH.put("SUCCEEDED", b);
            if (b.booleanValue()) {
                if (h.get("NEXT_STATUS").toString().equalsIgnoreCase("posubmit")) {
                    rs.setReleaseInfo(headerInfo, detailInfo);
                    if (!db.getClient().equalsIgnoreCase("SWA")) {
                        //System.out.println("\n\n------ EVERYTHING SEEM TO RUN OKAY - DON'T CALL SALES ORDER CREATION PROCESS FOR TEST\n\n");
                        //  Since this is an engineering evaluation - DO I NEED TO ALLOCATE??
                        try {
                            String[] args = new String[1];
                            args[0] = pr_number.toString();
                            db.doProcedure("p_mr_allocate", args);
                        } catch (Exception dbe) {
                            radian.web.emailHelpObj.sendtrongemail("p_mr_allocate error (pr_number: " + pr_number.toString() + ")", "Error occured while trying to call procedure");
                        }
                    }
                }
            } else {
                String esub = "";
                String emsg = "";
                esub = new String("tcmIS New ENGINEERING EVALUATION Request " + reqId + " submit failed.");
                emsg = emsg + "tcmIS unable to update purchase request and request line item for Eng Eval.";
                radian.web.emailHelpObj.sendtrongemail(esub, emsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


