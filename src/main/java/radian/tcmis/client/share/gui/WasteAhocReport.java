package radian.tcmis.client.share.gui;

/*
/* Title:
/* Version:
/* Description:
/*
 *  Copyright 2000
 *
 *  <URS - Radian Corporation>
 *  All rights reserved
 */

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

import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.*;
//import radian.tcmis.client.share.reports.appData.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
public class WasteAhocReport extends ReportInputPanel
//public class WasteAhocReport extends JPanel
{

    //FacilityCombo facC = new FacilityCombo(cmis);
    //WorkAreaCombo waC = facC.getWorkAreaCombo();
    JComboBox facC = new JComboBox();
    JComboBox waC = new JComboBox();

    GridBagLayout gridBagLayout1 = new GridBagLayout();
    ButtonGroup bg = new ButtonGroup();
    Vector baseField = new Vector();
    CmisTable rightTable;
    CmisTable leftTable;
    String[] colHeadR = {"Report Fields"};
    String[] colHeadL = {"Base Fields"};
    final static int MAXSELECTEDROW = 50;
    Hashtable templateInfoH = null;
    JScrollPane reportFieldJSP = new JScrollPane();
    JButton forwardB = new JButton();
    JButton backB = new JButton();
    JPanel rightP = new JPanel();
    JScrollPane baseFieldJSP = new JScrollPane();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    JRadioButton interactiveR = new JRadioButton();
    GridBagLayout gridBagLayout3 = new GridBagLayout();
    JPanel leftP = new JPanel();
    JRadioButton batchR = new JRadioButton();
    JPanel bottomP = new JPanel();
    GridBagLayout gridBagLayout4 = new GridBagLayout();
    MonthCombo endMonthC = new MonthCombo(-1);
    YearCombo endYearC = new YearCombo(-2, 6, 0);
    JRadioButton profileRB = new JRadioButton();
    StaticJLabel end = new StaticJLabel();
    JPanel topP = new JPanel();
    //JComboBox waC = new JComboBox();
    StaticJLabel workarea = new StaticJLabel();
    StaticJLabel begin = new StaticJLabel();
    YearCombo begYearC = new YearCombo(-2, 6, -1);
    JRadioButton allRB = new JRadioButton();
    MonthCombo begMonC = new MonthCombo(0);
    //JComboBox facilitycombo = new JComboBox();
    GridBagLayout gridBagLayoutT = new GridBagLayout();
    StaticJLabel facility = new StaticJLabel();
    JComboBox vendorC = new JComboBox();
    JComboBox accumeptC = new JComboBox();
    StaticJLabel jLabel1 = new StaticJLabel();
    StaticJLabel jLabel2 = new StaticJLabel();
    JButton searchProfileB = new JButton();
    StaticJLabel jLabel3 = new StaticJLabel();
    DataJLabel mgtOptL = new DataJLabel();
    JButton mgtOptB = new JButton();
    JTextArea mgtOptT = new JTextArea();
    JTextField profileT = new JTextField();
    String profileID = "";
    String lastVendor = "";

    String mgmtOptID = "";
    JCheckBox hubWasteC = new JCheckBox();

    boolean firstTime = true;
    StaticJLabel batchNameL = new StaticJLabel();
    JTextField batchNameT = new JTextField();

    /**
     *  Constructor for the WasteAhocReport object
     *
     *@param  cmis  Description of Parameter
     */
    public WasteAhocReport(CmisApp cmis)
    {
        this(cmis, false);
    }


    /**
     *  Constructor for the WasteAhocReport object
     *
     *@param  cmis         Description of Parameter
     *@param  isVocReport  Description of Parameter
     */
    public WasteAhocReport(CmisApp cmis, boolean isVocReport)
    {
        super(cmis, "Share", "Ad Hoc Waste ");
        /*
        try
        {
            jbInit();
            loadIt();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } */
    }

    public void loadScreen() {
        if (firstTime) {
            try
            {
                jbInit();
                loadIt();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            firstTime = false;
        }
    }

    public void loadIt() {
        WasteAhocReportLoadThread ahrlt = new WasteAhocReportLoadThread(this,
                                                                        "load");
        ahrlt.start();
    }
    public void loadItAction(String action) {
        CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
        try {
            ClientHelpObjs.setEnabledContainer(this,false);
            TcmisHttpConnection cgi = new TcmisHttpConnection("WasteAhocReport",
                                                              cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","GET_WASTE_TEMPLATE_INFO"); //String, String
            query.put("USER_ID",cmis.getUserId());          //String, Integer      4-25-01
            Hashtable result = cgi.getResultHash(query);
            if (result == null){
                GenericDlg.showAccessDenied(cmis.getMain());
                ClientHelpObjs.setEnabledContainer(this,true);
                return;
            }

            templateInfoH = (Hashtable)result.get("WASTE_TEMPLATE_INFO");
            //Nawaz 09/20/01
            templateInfoH.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
            buildTable();
            buildComboBoxes();
            begYearC.removeAllItems();
            begYearC.reFresh(cmis.yearOfReport.intValue(),cmis.numberOfYear.intValue(),-1);
            endYearC.removeAllItems();
            endYearC.reFresh(cmis.yearOfReport.intValue(),cmis.numberOfYear.intValue(),0);
        } catch (Exception e) {
            e.printStackTrace();
            GenericDlg.showAccessDenied(cmis.getMain());
            ClientHelpObjs.setEnabledContainer(this,true);
            return;
        }
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        ClientHelpObjs.setEnabledContainer(this,true);
        radioButtonClicked();
    }

    public void loadTemplate(String template) {
        CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
        try {
            ClientHelpObjs.setEnabledContainer(this,false);
            TcmisHttpConnection cgi = new TcmisHttpConnection("WasteAhocReport",
                                                              cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","LOAD_WASTE_TEMPLATE_INFO"); //String, String
            query.put("USER_ID",cmis.getUserId());    //String, Integer
            query.put("TEMPLATE",template);           //String, String
            Hashtable result = cgi.getResultHash(query);
            if (result == null){
                GenericDlg.showAccessDenied(cmis.getMain());
                ClientHelpObjs.setEnabledContainer(this,true);
                return;
            }

            Hashtable infoH = (Hashtable)result.get("TEMPLATE_INFO");
            //Nawaz 09/20/01
            infoH.put("TABLE_STYLE",(Hashtable)result.get("TABLE_STYLE"));
            loadData(infoH);

        } catch (Exception e) {
            e.printStackTrace();
            GenericDlg.showAccessDenied(cmis.getMain());
            ClientHelpObjs.setEnabledContainer(this,true);
            return;
        }
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        ClientHelpObjs.setEnabledContainer(this,true);
        //doesn't work setComboColor();
        radioButtonClicked();
        super.setReportTitle("Ad Hoc Usage - "+template);
        //doesn't work ClientHelpObjs.makeDefaultColors(this);
    }

    public void loadData(Hashtable data) {
        String vendor = (String)data.get("VENDOR");
        //String vendor = (String)data.get("VENDOR_DESC");
        String type = (String)data.get("TYPE");
        String beginY = (String)data.get("BEGIN_YEAR");
        String beginM = (String)data.get("BEGIN_MONTH");
        String endY = (String)data.get("END_YEAR");
        String endM = (String)data.get("END_MONTH");
        String facID = data.get("FACILITY_ID").toString();
        String workArea = data.get("WORK_AREA").toString();
        String method = (String)data.get("METHOD");
        String accumept = (String)data.get("ACCUME_PT");
        Vector report = (Vector)data.get("REPORT_FIELDS");
        String profile = (String)data.get("PROFILE");

        String hubWaste = (String)data.get("HUB_WASTE");
        if ("Y".equalsIgnoreCase(hubWaste)) {
          hubWasteC.setSelected(true);
        }else {
          hubWasteC.setSelected(false);
        }

        //4-28-01
        String myProfileId = BothHelpObjs.makeBlankFromNull((String)data.get("PROFILE_ID"));
        String myMgmt = BothHelpObjs.makeBlankFromNull((String)data.get("MGMT_OPTION"));
        this.mgtOptT.setText(myMgmt);
        String myMgmtId = BothHelpObjs.makeBlankFromNull((String)data.get("MGMT_OPTION_ID"));
        this.mgmtOptID = myMgmtId;

        //selecting report type
        if (type.equalsIgnoreCase("Profile"))
        {
            allRB.setSelected(false);
            profileRB.setSelected(true);
        }
        else
        {
            allRB.setSelected(true);
            profileRB.setSelected(false);
        }

        if (method.equalsIgnoreCase("Active"))
        {
            interactiveR.setSelected(true);
            batchR.setSelected(false);
        }
        else
        {
            interactiveR.setSelected(false);
            batchR.setSelected(true);
        }

        //Integer fa = new Integer(facID);
        //Integer wa = new Integer(workArea);
        //facC.setSelectedIndex(fa.intValue());
        //waC.setSelectedIndex(wa.intValue());
        facC.setSelectedItem(facID);
        waC.setSelectedItem(workArea);
        accumeptC.setSelectedItem(accumept);
        vendorC.setSelectedItem(vendor);

        profileT.setText(profile);
        this.profileID = myProfileId;

        //date
        Integer b1 = new Integer(beginY);
        Integer bm = new Integer(beginM);
        Integer ey = new Integer(endY);
        Integer em = new Integer(endM);
        begMonC.setSelectedIndex(bm.intValue());
        begYearC.setSelectedIndex(b1.intValue());
        endMonthC.setSelectedIndex(em.intValue());
        endYearC.setSelectedIndex(ey.intValue());

        {
            baseFieldJSP.getViewport().remove(leftTable);
            reportFieldJSP.getViewport().remove(rightTable);

            DbTableModel modelR = new DbTableModel(colHeadR);
            DbTableModel modelL = new DbTableModel(colHeadL);

            //make a copy of base_fields
            Object[] oaL1 = new Object[colHeadR.length];
            for (int i = 0; i < report.size(); i++)
            {
                oaL1[0] = report.elementAt(i);
                modelR.addRow(oaL1);
            }

            Object[] oaL3 = new Object[colHeadL.length];
            for (int i = 0; i < baseField.size(); i++)
            {
                oaL3[0] = baseField.elementAt(i);
                if (!(report.contains(oaL3[0])))
                {
                    modelL.addRow(oaL3);
                }
            }

            rightTable = new CmisTable(modelR);
              //Nawaz 09/19/01
            AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
            Hashtable chargeTableStyle = (Hashtable)data.get("TABLE_STYLE");
            Color color = (Color)chargeTableStyle.get("COLOR");
            Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
            Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
            Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
            Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
            Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
            renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
            TableColumnModel cm = rightTable.getColumnModel();
            for (int i = 0; i < rightTable.getColumnCount(); i++) {
              cm.getColumn(i).setCellRenderer(renderers[0]);
            }
            //font and foreground and background color for columns heading
            String fontName = (String)chargeTableStyle.get("FONT_NAME");
            Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
            Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
            rightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
            MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
            Enumeration enum1 = rightTable.getColumnModel().getColumns();
            while (enum1.hasMoreElements()) {
              ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
            }

            rightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            leftTable = new CmisTable(modelL);
            //Nawaz 09/19/01
            cm = leftTable.getColumnModel();
            for (int i = 0; i < leftTable.getColumnCount(); i++) {
              cm.getColumn(i).setCellRenderer(renderers[0]);
            }
            leftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
            tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
            enum1 = leftTable.getColumnModel().getColumns();
            while (enum1.hasMoreElements()) {
              ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
            }

            leftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            baseFieldJSP.getViewport().setView(leftTable);
            reportFieldJSP.getViewport().setView(rightTable);

        }

    }

    void setComboColor() {
        DataJLabel tmp = new DataJLabel();
        tmp.setText("temp");
        Color keep = tmp.getForeground();
        begMonC.setForeground(keep);
        begYearC.setForeground(keep);
        endMonthC.setForeground(keep);
        endYearC.setForeground(keep);
    }


    /**
     *  Sets the FacCombo attribute of the WasteAhocReport object
     *
     *@param  fc  The new FacCombo value
     */
    public void setFacCombo(FacilityCombo fc)
    {
        /*facC.setUseAllFacilities(true);
        facC.setUseAllWorkAreas(true);
        facC = fc.loadAnotherOne(facC);
        facC.setSelectedItem(cmis.getPrefFac());  */
    }

    public void openTemplate() {
        showOpenTemplateDlg();
    }
    public void showOpenTemplateDlg() {
        OpenTemplateDlg otd = new OpenTemplateDlg(cmis,
                                                  "Open Template",
                                                  true,"WasteAhocReport");
        if (otd.loadData()) {
            otd.setVisible(true);
            if (otd.isChanged()) {
                String selectedTemplate = otd.getSelectedTemplate();
                if (!BothHelpObjs.isBlankString(selectedTemplate)) {
                    //super.setReportTitle("Ad Hoc Usage - "+selectedTemplate);
                    loadTemplate(selectedTemplate);
                }
            }
        }
    }

    public void saveTemplate() {
        showSaveTemplateDlg();
    }
    public void showSaveTemplateDlg() {
        //save selected info
        Hashtable currentDataH = saveSelectedData();
        SaveTemplateDlg std = new SaveTemplateDlg(cmis,
                                                  "Save Template",
                                                  true,"WasteAhocReport");
        String currentT = super.getTitle();
        String currentTemplate = "";
        if (currentT.indexOf("-") > 0) {
            currentTemplate = currentT.substring(currentT.indexOf("-")+2);
            std.updateL.setText(currentTemplate);
            std.updateRB.setSelected(true);
            std.newRB.setSelected(false);
            std.newT.setEnabled(false);
        }else {
            std.newRB.setSelected(true);
            std.newT.setEnabled(true);
            std.updateRB.setSelected(false);
            std.updateRB.setEnabled(false);
        }
        std.setSelectedData(currentDataH);
        std.setVisible(true);
        if (std.isChanged()) {
            String newTemplate = std.getTemplateName();
            if (!newTemplate.equals(currentTemplate)) {
                loadTemplate(newTemplate);
            }
        }
    }

    public void clearTemplate() {
        super.setReportTitle("Ad Hoc Waste");
        reSetAllComponents();
    }

    public void reSetAllComponents() {

        hubWasteC.setSelected(true);
        allRB.setSelected(true);
        profileRB.setSelected(false);

        //facC.setSelectedIndex(0);
        facC.setSelectedItem(cmis.getPrefFac());
        waC.setSelectedIndex(0);
        begMonC.reFresh(0);
        begYearC.removeAllItems();
        begYearC.reFresh(cmis.yearOfReport.intValue(),cmis.numberOfYear.intValue(),-1);
        endMonthC.reFresh(-1);
        endYearC.removeAllItems();
        endYearC.reFresh(cmis.yearOfReport.intValue(),cmis.numberOfYear.intValue(),0);
        vendorC.setSelectedIndex(0);
        accumeptC.setSelectedIndex(0);

        buildTable();
        profileT.setText("");
        profileID = "";
        mgtOptT.setText("");
        mgmtOptID = "";
        interactiveR.setSelected(true);
        batchR.setSelected(false);
        batchNameL.setEnabled(false);
        batchNameT.setEnabled(false);
    }




    /**
     *  Description of the Method
     */
    public void showReport()
    {
        if (!checkDates(new Integer(begMonC.getSelectedIndex()).toString(),
                        begYearC.getSelectedItem().toString(),
                        new Integer(endMonthC.getSelectedIndex()).toString(),
                        endYearC.getSelectedItem().toString()))
        {
            showBadDatesDlg();
            return;
        }

        if (profileRB.isSelected())
        {
            if (BothHelpObjs.isBlankString(profileT.getText()))
            {
                GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
                gd.setMsg("You must select a profile.");
                CenterComponent.centerCompOnScreen(gd);
                gd.setVisible(true);
                return;
            }
        }

        if (rightTable.getRowCount() < 1) {
            GenericDlg gd = new GenericDlg(cmis.getMain(), "Error", true);
            gd.setMsg("Please define report fields.");
            CenterComponent.centerCompOnScreen(gd);
            gd.setVisible(true);
            return;
        }

        String myTitle;
        Hashtable currentDataH = saveSelectedData();

        CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
        try{
            if (interactiveR.isSelected())
            {
                //ClientHelpObjs.setEnabledContainer(this,false);
                TcmisHttpConnection cgi = new TcmisHttpConnection("WasteAhocReport",
                                                                  cmis);
                Hashtable query = new Hashtable();
                query.put("ACTION","CREATE_REPORT");        //String, String
                query.put("SELECTED_DATA",currentDataH);    //String, Hashtable
                query.put("USER_ID",cmis.getMain().getUserId().toString());
                query.put("WHICH_SCREEN","WASTE_USAGE");
                Hashtable result = cgi.getResultHash(query);
                ClientHelpObjs.setEnabledContainer(this,true);
                if (result == null){
                    GenericDlg.showAccessDenied(cmis.getMain());
                    return;
                }
                Boolean suc = (Boolean)result.get("SUCCEED");
                //Boolean display = (Boolean)result.get("DISPLAY");
                String msg = (String)result.get("MSG");
                if (suc.booleanValue())
                {
                    try
                    {
                        String url = (String)result.get("URL");
                        new URLCall(url,cmis);
                        //5-10-01 giving the user some response so they know what to do
                        GenericDlg gd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
                        gd.setMsg(msg);
                        CenterComponent.centerCompOnScreen(gd);
                        gd.setVisible(true);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    GenericDlg gd = new GenericDlg(cmis.getMain(),
                                                   "Error", true);
                    gd.setMsg(msg);
                    CenterComponent.centerCompOnScreen(gd);
                    gd.setVisible(true);
                }
            }
            else
            {


                Hashtable query = new Hashtable();
                query.put("ACTION","CREATE_REPORT");         //String, String
                query.put("WHICH_SCREEN","WASTE_USAGE");
                query.put("USER_ID",cmis.getMain().getUserId().toString());
                query.put("SELECTED_DATA",currentDataH);    //String, Hashtable
                query.put("REPORT_NAME",BothHelpObjs.makeBlankFromNull(batchNameT.getText()));

                TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocBatch",cmis);
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
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            ClientHelpObjs.setEnabledContainer(this,true);
            this.revalidate();
        }
        CursorChange.setCursor(this,Cursor.DEFAULT_CURSOR);
        radioButtonClicked();
    }

    //trong 2-19-01
    /**
           *  Description of the Method
           */
    public Hashtable saveSelectedData() {
        Hashtable currentData = new Hashtable();
        //getting method
        if (interactiveR.isSelected()) {
            currentData.put("METHOD","Active");
        }else {
            currentData.put("METHOD","Batch");
        }

        if (hubWasteC.isSelected()) {
          currentData.put("HUB_WASTE","Y");
        }else {
          currentData.put("HUB_WASTE","N");
        }

        //what type of report
        if (profileRB.isSelected()) {
            currentData.put("TYPE","Profile");
        }else if (allRB.isSelected()) {
            currentData.put("TYPE","All");
        }
        currentData.put("FACILITY_ID",(String)facC.getSelectedItem());
        //currentData.put("FACILITY_ID",new Integer(facC.getSelectedIndex()));
        currentData.put("WORK_AREA",(String)waC.getSelectedItem());
        currentData.put("PROFILE",BothHelpObjs.makeBlankFromNull(profileT.getText()));
        currentData.put("PROFILE_ID",BothHelpObjs.makeBlankFromNull(profileID));
        currentData.put("VENDOR",getVendorID());
        currentData.put("VENDOR_DESC",getVendorDESC());
        currentData.put("ACCUME_PT",accumeptC.getSelectedItem().toString());
        currentData.put("BEGIN_MONTH",new Integer(begMonC.getSelectedIndex()));
        currentData.put("BEGIN_YEAR",new Integer(begYearC.getSelectedIndex()));
        currentData.put("END_MONTH",new Integer(endMonthC.getSelectedIndex()));
        currentData.put("END_YEAR",new Integer(endYearC.getSelectedIndex()));

        currentData.put("BEGIN_YEARN",(begYearC.getSelectedItem()));
        currentData.put("END_YEARN",(endYearC.getSelectedItem()));
        currentData.put("WORK_AREA_NAME",getWorkAreaID());
        //4-28-01
        currentData.put("MGMT_OPTION",BothHelpObjs.makeBlankFromNull(this.mgmtOptID));
        currentData.put("MGMT_OPTION_DESC",BothHelpObjs.makeBlankFromNull(this.mgtOptT.getText()));


        Vector v = new Vector();
        for (int i = 0; i < rightTable.getRowCount(); i++) {
            v.addElement(rightTable.getValueAt(i,0));
        }
        currentData.put("REPORT_FIELDS",v);

        return currentData;
    }

    /**
     *  Description of the Method
     */
    void radioButtonClicked()
    {
        /*groupC.setEnabled(listRB.isSelected());
        listB.setEnabled(listRB.isSelected());
        selectChemB.setEnabled(singleRB.isSelected());
        casT.setEnabled(singleRB.isSelected());
        jLabel6.setEnabled(singleRB.isSelected());*/
        this.batchNameL.setEnabled(this.batchR.isSelected());
        this.batchNameT.setEnabled(this.batchR.isSelected());
    }

    void forwardB_actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (leftTable.getSelectedRow() < 0) {
            GenericDlg g = new GenericDlg(cmis.getMain(),"Select row(s)",true);
            g.setMsg("Please select row(s) that you want to move.");
            g.setVisible(true);
        }else {
            int[] selectedRows = new int[MAXSELECTEDROW];
            selectedRows = leftTable.getSelectedRows();
            Vector myDataV = new Vector();
            GenericDlg g = new GenericDlg(cmis.getMain(),"Error",true);
            boolean contChem = false;
            //save data
            for (int i = 0; i < selectedRows.length; i++) {
                String tmpData = leftTable.getModel().getValueAt(selectedRows[i],0).toString();
                if (tmpData.equalsIgnoreCase("CAS Number") || tmpData.equalsIgnoreCase("Chemical Name")) {
                  contChem = true;
                }
                myDataV.addElement(tmpData);
            }

            //if user select both 'chemical' and 'material weight'
            if (contChem) {
              if (myDataV.contains("Weight (lb.)") || myDataV.contains("Cost") ) {
                String msg = " You may not have both Weight/Cost and speciated columns.";
                msg += "\n Please remove either speciated fields or the Weight/Cost field(s).";
                g.setMsg(msg);
                g.setVisible(true);
                return;
              }
            }

            //check report field table with the same condition as above
            boolean contChem2 = false;
            boolean contWaste = false;
            for (int n = 0; n < rightTable.getRowCount(); n++) {
              String tmpField = rightTable.getModel().getValueAt(n,0).toString();
              if (tmpField.equalsIgnoreCase("CAS Number") || tmpField.equalsIgnoreCase("Chemical Name")) {
                contChem2 = true;
              }else if (tmpField.equalsIgnoreCase("Weight (lb.)") || tmpField.equalsIgnoreCase("Cost")) {
                contWaste = true;
              }
            }
            //if report contains speciated
            if (contChem2) {
              if (myDataV.contains("Weight (lb.)") || myDataV.contains("Cost") ) {
                g.setMsg(" You cannot add Weight/Cost field\n to a report containing speciated columns.");
                g.setVisible(true);
                return;
              }
            }else if (contWaste) {
              if (contChem) {
                g.setMsg(" You cannot add speciated columns\n to a report with Weight/Cost.");
                g.setVisible(true);
                return;
              }
            }

            //removing data from table
            for (int j = 0; j < selectedRows.length; j++ ) {
                removeRowFromTable(leftTable,selectedRows[j]-j);
            }
            //move data to table
            for (int k = 0; k < myDataV.size(); k++) {
                String tmpData2 = (String)myDataV.elementAt(k);
                Object[] oa = new Object[colHeadR.length];
                oa[0] = tmpData2;
                addRowToTable(rightTable,oa,rightTable.getSelectedRow());
            }
        }
    }

    void removeRowFromTable(CmisTable myTable,int row) {
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
    /**
     *  Description of the Method
     *
     *@param  e  Description of Parameter
     */
    void profileRB_actionPerformed(ActionEvent e)
    {
        allRB.setSelected(false);
        profileRB.setSelected(true);
    }


    /**
     *  Description of the Method
     *
     *@param  e  Description of Parameter
     */
    void allRB_actionPerformed(ActionEvent e)
    {
        allRB.setSelected(true);
        profileRB.setSelected(false);
    }


    /**
     *  Description of the Method
     *
     *@exception  Exception  Description of Exception
     */
    private void jbInit() throws Exception
    {
        /*String wDir = new String(System.getProperty("user.dir"));
        String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
*/
        this.setPreferredSize(new Dimension(700, 381));
        bottomP.setLayout(gridBagLayout3);
        bottomP.setPreferredSize(new Dimension(200, 200));
        bottomP.setMinimumSize(new Dimension(10, 400));
        bottomP.setMaximumSize(new Dimension(32767, 1000));
        batchR.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    batchR_actionPerformed(e);
                }
            });
        batchR.setText("Batch Generation");
        leftP.setPreferredSize(new Dimension(160, 80));
        leftP.setMinimumSize(new Dimension(160, 80));
        leftP.setMaximumSize(new Dimension(160, 80));
        leftP.setLayout(borderLayout3);
        interactiveR.setSelected(true);
        interactiveR.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    interactiveR_actionPerformed(e);
                }
            });
        interactiveR.setText("Interactive Generation");
        rightP.setLayout(borderLayout4);
        rightP.setMaximumSize(new Dimension(160, 80));
        rightP.setMinimumSize(new Dimension(160, 80));
        rightP.setPreferredSize(new Dimension(160, 80));
        backB.setIcon(ResourceLoader.getImageIcon("images/button/prev1.gif"));
        backB.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    backB_actionPerformed(e);
                }
            });
        backB.setMaximumSize(new Dimension(35, 35));
        backB.setMinimumSize(new Dimension(35, 35));
        backB.setPreferredSize(new Dimension(35, 35));
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





        this.setLayout(gridBagLayout4);

        endMonthC.setMinimumSize(new Dimension(90, 23));
        endYearC.setMinimumSize(new Dimension(65, 23));
        endYearC.setPreferredSize(new Dimension(65, 23));

        profileRB.addActionListener(new java.awt.event.ActionListener()
                                    {

                /**
                 *  Description of the Method
                 *
                 *@param  e  Description of Parameter
                 */
                public void actionPerformed(ActionEvent e)
                {
                    profileRB_actionPerformed(e);
                }
            });
        profileRB.setText("Profile");
        profileRB.setSelected(false);
        end.setText("End:");
        end.setHorizontalAlignment(SwingConstants.RIGHT);
        topP.setMaximumSize(new Dimension(32767, 1000));
        topP.setMinimumSize(new Dimension(700, 200));
        topP.setPreferredSize(new Dimension(700, 200));
        topP.setLayout(gridBagLayoutT);
        workarea.setText("Work Area :");
        begin.setText("Begin:");
        begYearC.setMinimumSize(new Dimension(65, 23));
        begYearC.setPreferredSize(new Dimension(65, 23));
        allRB.addActionListener(new java.awt.event.ActionListener()
                                {

                /**
                 *  Description of the Method
                 *
                 *@param  e  Description of Parameter
                 */
                public void actionPerformed(ActionEvent e)
                {
                    allRB_actionPerformed(e);
                }
            });
        allRB.setText("All");
        allRB.setSelected(true);
        begMonC.setMinimumSize(new Dimension(90, 23));


        facility.setPreferredSize(new Dimension(43, 17));
        facility.setText("Facility :");
        jLabel1.setText("Accumulation Pt :");
        jLabel2.setText("Vendor :");

        facC.setMaximumSize(new Dimension(180, 23));
        facC.setMinimumSize(new Dimension(180, 23));
        facC.setPreferredSize(new Dimension(180, 23));
        facC.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    loadWorkAreaCombo();
                    loadVendorCombo();
                }
            });
        waC.setMaximumSize(new Dimension(180, 23));
        waC.setMinimumSize(new Dimension(180, 23));
        waC.setPreferredSize(new Dimension(180, 23));
        waC.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    loadAccumulationPtCombo();
                }
            });
        accumeptC.setMaximumSize(new Dimension(180, 23));
        accumeptC.setMinimumSize(new Dimension(180, 23));
        accumeptC.setPreferredSize(new Dimension(180, 23));
        vendorC.setMaximumSize(new Dimension(180, 23));
        vendorC.setMinimumSize(new Dimension(180, 23));
        vendorC.setPreferredSize(new Dimension(180, 23));
        /*vendorC.addItemListener(new java.awt.event.ItemListener() {
          public void itemStateChanged(ItemEvent evt) {
            clearProfile();
          }
        }); */
        searchProfileB.setMaximumSize(new Dimension(35, 23));
        searchProfileB.setMinimumSize(new Dimension(35, 23));
        searchProfileB.setPreferredSize(new Dimension(35, 23));
        searchProfileB.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    searchProfileB_actionPerformed(e);
                }
            });
        searchProfileB.setText("");
        searchProfileB.setIcon(ResourceLoader.getImageIcon("images/button/search_small.gif"));
        jLabel3.setText("Mgt Option:");
        mgtOptL.setText("");
        mgtOptB.setMaximumSize(new Dimension(35, 23));
        mgtOptB.setMinimumSize(new Dimension(35, 23));
        mgtOptB.setPreferredSize(new Dimension(35, 23));
        mgtOptB.setText("");
        mgtOptB.setIcon(ResourceLoader.getImageIcon("images/button/search_small.gif"));
        mgtOptB.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mgtOptB_actionPerformed(e);
                }
            });
        mgtOptT.setLineWrap(true);
        mgtOptT.setEditable(false);
        mgtOptT.setPreferredSize(new Dimension(120, 75));
        mgtOptT.setMaximumSize(new Dimension(120, 75));
        mgtOptT.setRows(2);
        mgtOptT.setBorder(BorderFactory.createEtchedBorder());
        mgtOptT.setBackground(waC.getBackground());
        mgtOptT.setWrapStyleWord(true);
        mgtOptT.setMinimumSize(new Dimension(120, 75));
        //mgtOptT.setText("This is where i put the management option id - description yeah yeah.");
        profileT.setMaximumSize(new Dimension(180, 23));
        profileT.setMinimumSize(new Dimension(180, 23));
        profileT.setPreferredSize(new Dimension(180, 23));
        profileT.setText("");
        profileT.setEditable(false);
        batchNameL.setText("Rpt Name :");
        batchNameL.setEnabled(false);
        batchNameT.setMaximumSize(new Dimension(100, 21));
        batchNameT.setMinimumSize(new Dimension(100, 21));
        batchNameT.setPreferredSize(new Dimension(100, 21));
        batchNameT.setText("");
        batchNameT.setEnabled(false);
        this.add(bottomP, new GridBagConstraints2(0, 2, 1, 1, 1.0, 1.0
                                                  ,
                                                  GridBagConstraints.SOUTH,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0,
                                                             0, 0, 0), 0, 0));
        bottomP.add(forwardB, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                                      ,
                                                      GridBagConstraints.CENTER,
                                                      GridBagConstraints.NONE,
                                                      new Insets(3,
                                                                 3,
                                                                 40, 0), 0, 0));
        bottomP.add(rightP, new GridBagConstraints2(4, 1, 2, 1, 1.0, 1.0
                                                    ,
                                                    GridBagConstraints.CENTER,
                                                    GridBagConstraints.BOTH,
                                                    new Insets(3,
                                                               3, 3, 3), 0, 0));
        rightP.add(reportFieldJSP, BorderLayout.CENTER);
        bottomP.add(backB, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.CENTER,
                                                   GridBagConstraints.NONE,
                                                   new Insets(55,
                                                              3, 10, 0), 0, 0));
        bottomP.add(interactiveR, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
                                                          ,
                                                          GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
        bottomP.add(batchR, new GridBagConstraints2(3, 2, 1, 1, 0.0, 0.0
                                                    ,
                                                    GridBagConstraints.WEST,
                                                    GridBagConstraints.NONE,
                                                    new Insets(3,
                                                               3, 3, 3), 0, 0));
        bottomP.add(leftP, new GridBagConstraints2(1, 1, 2, 1, 1.0, 1.0
                                                   ,
                                                   GridBagConstraints.CENTER,
                                                   GridBagConstraints.BOTH,
                                                   new Insets(3,
                                                              3, 3, 3), 0, 0));
        leftP.add(baseFieldJSP, BorderLayout.CENTER);
        bottomP.add(batchNameL,
                    new GridBagConstraints2(4,
                                            2,
                                            GridBagConstraints.REMAINDER,
                                            1, 0.0, 0.0
                                            ,
                                            GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(0, 20, 0, 0), 0, 0));
        bottomP.add(batchNameT,
                    new GridBagConstraints2(4,
                                            2,
                                            GridBagConstraints.REMAINDER,
                                            1, 0.0, 0.0
                                            ,
                                            GridBagConstraints.CENTER,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 88, 0, 0), 0, 0));
        this.add(topP, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                               ,
                                               GridBagConstraints.NORTH,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(0, 0, 3, 0), 0, 0));
        topP.add(begin, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 3, 3), 0, 0));
        topP.add(begMonC, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0,
                                                             3, 3, 3), 0, 0));
        topP.add(begYearC, new GridBagConstraints2(4, 0, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(0,
                                                              100,
                                                              3, 3), 0, 0));
        topP.add(endMonthC, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
                                                    ,
                                                    GridBagConstraints.WEST,
                                                    GridBagConstraints.NONE,
                                                    new Insets(3,
                                                               3, 3, 3), 0, 0));
        topP.add(endYearC, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(3,
                                                              100,
                                                              3, 3), 0, 0));
        topP.add(end, new GridBagConstraints2(3, 1, 1, 1, 0.0, 0.0
                                              ,
                                              GridBagConstraints.EAST,
                                              GridBagConstraints.NONE,
                                              new Insets(3, 3, 3, 3), 0, 0));
        topP.add(profileRB, new GridBagConstraints2(0, 4, 1, 1, 0.0, 0.0
                                                    ,
                                                    GridBagConstraints.WEST,
                                                    GridBagConstraints.NONE,
                                                    new Insets(5,
                                                               20,
                                                               1, 0), 0, 0));
        topP.add(allRB, new GridBagConstraints2(0, 5, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 20, 0, 0), 0, 0));

        hubWasteC.setText("Exclude Hub Waste");
        hubWasteC.setSelected(true);
        topP.add(hubWasteC, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

        topP.add(vendorC, new GridBagConstraints2(1, 3, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0,
                                                             5, 3, 0), 0, 0));
        topP.add(waC, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
                                              ,
                                              GridBagConstraints.WEST,
                                              GridBagConstraints.NONE,
                                              new Insets(0, 5, 3, 0), 0, 0));
        topP.add(workarea, new GridBagConstraints2(0, 1, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.EAST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(0,
                                                              0, 0, 0), 0, 0));
        topP.add(facility, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.EAST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(3,
                                                              0, 0, 0), 0, 0));
        topP.add(accumeptC, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
                                                    ,
                                                    GridBagConstraints.WEST,
                                                    GridBagConstraints.NONE,
                                                    new Insets(0,
                                                               5, 3, 0), 0, 0));
        topP.add(jLabel1, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0,
                                                             0, 0, 0), 0, 0));
        topP.add(jLabel2, new GridBagConstraints2(0, 3, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0,
                                                             0, 0, 0), 0, 0));
        topP.add(facC, new GridBagConstraints2(1, 0, 2, 1, 0.0, 0.0
                                               ,
                                               GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(3, 5, 3, 0), 0, 0));
        topP.add(searchProfileB, new GridBagConstraints2(2, 4, 1, 1, 0.0, 0.0
                                                         ,
                                                         GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
        topP.add(jLabel3, new GridBagConstraints2(3, 3, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0,
                                                             5, 0, 3), 0, 0));
        topP.add(mgtOptL, new GridBagConstraints2(4, 3, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0,
                                                             3, 0, 0), 0, 0));
        topP.add(mgtOptB, new GridBagConstraints2(4, 3, 1, 1, 0.0, 0.0
                                                  ,
                                                  GridBagConstraints.EAST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0,
                                                             100, 0, 3), 0, 0));
        topP.add(mgtOptT, new GridBagConstraints2(4, 3, 1, 3, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
        topP.add(profileT, new GridBagConstraints2(1, 4, 1, 1, 0.0, 0.0
                                                   ,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(0,
                                                              5, 0, 0), 0, 0));

        DbTableModel modelL = new DbTableModel(colHeadL);
        leftTable = new CmisTable(modelL);
        DbTableModel modelR = new DbTableModel(colHeadR);
        rightTable = new CmisTable(modelR);

        ClientHelpObjs.makeDefaultColors(this);
        reportFieldJSP.getViewport().setView(rightTable);
        baseFieldJSP.getViewport().setView(leftTable);
        bottomP.validate();
        topP.validate();

    }


    void buildTable() {

        baseFieldJSP.getViewport().remove(leftTable);
        reportFieldJSP.getViewport().remove(rightTable);

        DbTableModel modelR = new DbTableModel(colHeadR);

        //get model from database
        //DbTableModel tempModelL = (DbTableModel)templateInfoH.get("BASE_FIELDS");
        baseField = (Vector)templateInfoH.get("BASE_FIELDS");
        DbTableModel modelL = new DbTableModel(colHeadL);
        //make a copy of base_fields
        Object[] oaL = new Object[colHeadL.length];
        for (int i = 0; i < baseField.size(); i++) {
            oaL[0] = baseField.elementAt(i);
            modelL.addRow(oaL);
        }

        rightTable = new CmisTable(modelR);
         //Nawaz 09/19/01
        AttributiveCellRenderer[] renderers = new AttributiveCellRenderer[1];
        Hashtable chargeTableStyle = (Hashtable)templateInfoH.get("TABLE_STYLE");
        Color color = (Color)chargeTableStyle.get("COLOR");
        Integer t = (Integer)chargeTableStyle.get("INSET_TOP");
        Integer l = (Integer)chargeTableStyle.get("INSET_LEFT");
        Integer b = (Integer)chargeTableStyle.get("INSET_BOTTOM");
        Integer r = (Integer)chargeTableStyle.get("INSET_RIGHT");
        Integer a = (Integer)chargeTableStyle.get("INSET_ALIGN");
        renderers[0] = ClientHelpObjs.createRenderer(color, new Insets(t.intValue(),l.intValue(),b.intValue(),r.intValue()),a.intValue());
        TableColumnModel cm = rightTable.getColumnModel();
        for (int i = 0; i < rightTable.getColumnCount(); i++) {
          cm.getColumn(i).setCellRenderer(renderers[0]);
        }
        //font and foreground and background color for columns heading
        String fontName = (String)chargeTableStyle.get("FONT_NAME");
        Integer fontStyle = (Integer)chargeTableStyle.get("FONT_STYLE");
        Integer fontSize = (Integer)chargeTableStyle.get("FONT_SIZE");
        rightTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        MultiLineHeaderRenderer tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
        Enumeration enum1 = rightTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }
        rightTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        leftTable = new CmisTable(modelL);
        //Nawaz 09/19/01

        cm = leftTable.getColumnModel();
        for (int i = 0; i < leftTable.getColumnCount(); i++) {
          cm.getColumn(i).setCellRenderer(renderers[0]);
        }
        leftTable.setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
        tableRenderer = new MultiLineHeaderRenderer((Color)chargeTableStyle.get("FOREGROUND"),(Color)chargeTableStyle.get("BACKGROUND"),renderers[0].getBorder());
        enum1 = leftTable.getColumnModel().getColumns();
        while (enum1.hasMoreElements()) {
          ((TableColumn)enum1.nextElement()).setHeaderRenderer(tableRenderer);
        }

        leftTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        baseFieldJSP.getViewport().setView(leftTable);
        reportFieldJSP.getViewport().setView(rightTable);

    }

    void buildComboBoxes() {
        Hashtable h = (Hashtable)templateInfoH.get("FAC_APP_VENDOR_INFO");
        Vector facIDV = (Vector)h.get("FACILITY_ID");
        if (facC.getItemCount() < 1) {
            facC = ClientHelpObjs.loadChoiceFromVector(facC,facIDV);
        }
        facC.insertItemAt("All Facilities",0);
        loadWorkAreaCombo();
        loadAccumulationPtCombo();
        loadVendorCombo();
        vendorC.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    clearProfile();
                }
            });
    }
    void loadWorkAreaCombo() {
        Hashtable h = (Hashtable)templateInfoH.get("FAC_APP_VENDOR_INFO");
        String mFacID = (String)facC.getSelectedItem();
        waC.removeAllItems();
        if (mFacID.startsWith("All")) {
            waC.addItem("All Work Areas");
        }else {
            Hashtable h1 = (Hashtable)h.get(mFacID);
            Vector v = (Vector)h1.get("WORK_AREA_DESC");
            waC = ClientHelpObjs.loadChoiceFromVector(waC,v);
            //waC.insertItemAt("All Work Areas",0);
            waC.setSelectedIndex(0);
        }
    }
    void loadAccumulationPtCombo() {
        Hashtable h = (Hashtable)templateInfoH.get("FAC_APP_VENDOR_INFO");
        String mFacID = (String)facC.getSelectedItem();
        String mWorkArea = BothHelpObjs.makeBlankFromNull((String)waC.getSelectedItem());
        accumeptC.removeAllItems();
        //5-09-01 before if (mWorkArea.startsWith("All") || BothHelpObjs.isBlankString(mWorkArea)) {
        if (mFacID.startsWith("All") || BothHelpObjs.isBlankString(mWorkArea)) {
            accumeptC.addItem("All Accumulation Pts");
        }else {
            Hashtable h1 = (Hashtable)h.get(mFacID);
            Hashtable innerH = (Hashtable)h1.get("INNER_H");
            //System.out.println("========inner h: "+innerH);

            //Vector v = (Vector)innerH.get(getWorkAreaID());
            String cWa = getWorkAreaID();
            if (cWa.startsWith("All")) {
              cWa = "*All*";
            }
            Vector v = (Vector)innerH.get(cWa);
            accumeptC = ClientHelpObjs.loadChoiceFromVector(accumeptC,BothHelpObjs.sort(v));
            //accumeptC = ClientHelpObjs.loadChoiceFromVector(accumeptC,v);
            accumeptC.insertItemAt("All Accumulation Pts",0);
            accumeptC.setSelectedIndex(0);
        }
    }
    void loadVendorCombo() {
        Hashtable h = (Hashtable)templateInfoH.get("FAC_APP_VENDOR_INFO");
        String mFacID = (String)facC.getSelectedItem();
        vendorC.removeAllItems();
        if (mFacID.startsWith("All")) {
            vendorC.addItem("All Vendors");
        }else {
            Hashtable h1 = (Hashtable)h.get(mFacID);
            Vector v = (Vector)h1.get("VENDOR_DESC");
            vendorC = ClientHelpObjs.loadChoiceFromVector(vendorC,v);
            vendorC.insertItemAt("All Vendors",0);
            vendorC.setSelectedIndex(0);
        }
    }
    String getWorkAreaID() {
        String result = "";
        Hashtable h = (Hashtable)templateInfoH.get("FAC_APP_VENDOR_INFO");
        String mFacID = (String)facC.getSelectedItem();
        int selectedWAI = waC.getSelectedIndex();
        if (selectedWAI == 0 ) {
            result = "All Work Areas";
        }else if (selectedWAI > 0) {
            Hashtable h1 = (Hashtable)h.get(mFacID);
            Vector v = (Vector)h1.get("WORK_AREA_ID");
            //5-09-01 before  result = (String)v.elementAt(selectedWAI-1);
            result = (String)v.elementAt(selectedWAI);
        }
        return result;
    }
    String getVendorID() {
        String result = "";
        Hashtable h = (Hashtable)templateInfoH.get("FAC_APP_VENDOR_INFO");
        String mFacID = (String)facC.getSelectedItem();
        int selectedVI = vendorC.getSelectedIndex();
        if (selectedVI == 0) {
            result = "All Vendors";
        }else if (selectedVI > 0) {
            Hashtable h1 = (Hashtable)h.get(mFacID);
            Vector v = (Vector)h1.get("VENDOR_ID");
            result = (String)v.elementAt(selectedVI-1);
        }
        return result;
    }
    String getVendorDESC() {
        String result = "";
        Hashtable h = (Hashtable)templateInfoH.get("FAC_APP_VENDOR_INFO");
        String mFacID = (String)facC.getSelectedItem();
        int selectedVI = vendorC.getSelectedIndex();
        if (selectedVI == 0) {
            result = "All Vendors";
        }else if (selectedVI > 0) {
            Hashtable h1 = (Hashtable)h.get(mFacID);
            Vector v = (Vector)h1.get("VENDOR_DESC");
            result = (String)v.elementAt(selectedVI-1);
        }
        return result;
    }


    void interactiveR_actionPerformed(ActionEvent e)
    {
        if (interactiveR.isSelected())
        {
            batchR.setSelected(false);
            batchNameL.setEnabled(false);
            batchNameT.setEnabled(false);
        }
        else
        {
            batchR.setSelected(true);
        }
    }

    void batchR_actionPerformed(ActionEvent e)
    {
        if (batchR.isSelected())
        {
            interactiveR.setSelected(false);
            batchNameL.setEnabled(true);
            batchNameT.setEnabled(true);
        }
        else
        {
            interactiveR.setSelected(true);

        }
    }

    void backB_actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (rightTable.getSelectedRow() < 0) {
            GenericDlg g = new GenericDlg(cmis.getMain(),"Select a row",true);
            g.setMsg("Please select row(s) that you want to move.");
            g.setVisible(true);
        }else {
            int[] selectedRows = new int[MAXSELECTEDROW];
            selectedRows = rightTable.getSelectedRows();
            //getting the data from table
            Vector myDataV = new Vector();
            for (int i = 0; i < selectedRows.length; i++) {
                String tmpData = rightTable.getModel().getValueAt(selectedRows[i],0).toString();
                myDataV.addElement(tmpData);
            }
            //remove data from table
            for (int k = 0; k < selectedRows.length; k++) {
                removeRowFromTable(rightTable,selectedRows[k]-k);
            }
            //now move data to
            for (int j = 0; j < myDataV.size(); j++) {
                String tmpData2 = (String)myDataV.elementAt(j);
                Object[] oa = new Object[colHeadR.length];
                oa[0] = tmpData2;
                addRowToTable(leftTable,oa,leftTable.getSelectedRow());
            }
        }
    }

    void clearProfile() {
        String ve = BothHelpObjs.makeBlankFromNull((String)vendorC.getSelectedItem());
        if (!ve.equals(lastVendor)) {
            profileT.setText("");
            profileID = "";
        }
        lastVendor = ve;
    }
    void searchProfileB_actionPerformed(ActionEvent e) {
        ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
        if (vendorC.getSelectedIndex() > 0) {
            WasteProfileSelectDlg wps = new WasteProfileSelectDlg(cmis,
                                                                  (String)vendorC.getSelectedItem());
            wps.setVendorId(getVendorID());
            CenterComponent.centerCompOnScreen(wps);
            //wps.loadItAction();
            wps.setVisible(true);
            if (!wps.canceled) {
                profileT.setText(wps.profileDesc);
                profileID = wps.profileID;
            }
        }else {
            GenericDlg gd = new GenericDlg(cmis.getMain(),"Select a vendor",true);
            gd.setMsg("Please select a vendor.");
            CenterComponent.centerCompOnScreen(gd);
            gd.setVisible(true);
        }
    }

    void mgtOptB_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      WasteMgmtOptionSelectDlg wmps = new WasteMgmtOptionSelectDlg(cmis);
      CenterComponent.centerCompOnScreen(wmps);
      wmps.setVisible(true);
      if (!wmps.canceled) {
        this.mgtOptT.setText(wmps.mgmtOptID+" - "+wmps.mgmtOptDesc);
        mgmtOptID = wmps.mgmtOptID;
      }
    }   //end of method
} //end of class

class WasteAhocReportLoadThread extends Thread{
    WasteAhocReport ahur;
    String action;
    public WasteAhocReportLoadThread(WasteAhocReport ahur,String action){
        super();
        this.ahur = ahur;
        this.action = action;
    }
    public void run(){
        if(action.equalsIgnoreCase("load")){
            ahur.loadItAction("load");
        }else if(action.equalsIgnoreCase("reload")){
            ahur.loadItAction("reload");
        }
    }
}
