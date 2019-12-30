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
package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.httpCgi.*;
//import radian.tcmis.client.share.reports.*;
//import radian.tcmis.client.share.reports.appData.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;


public class AdHocVocHourly extends ReportInputPanel
//public class AdHocVocHourly extends JPanel
{
    //JPanel {  //
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    StaticJLabel jLabel1 = new StaticJLabel();
    JComboBox facC = new JComboBox();
    MonthCombo endMonthC = new MonthCombo(-1);
    YearCombo endYearC = new YearCombo(-2, 6, 0);    //-2: this year minus 2 , 6: display 6 years, 0: selected index
    StaticJLabel jLabel2 = new StaticJLabel();
    ButtonGroup bg = new ButtonGroup();
    JPanel bottomP = new JPanel();
    GridBagLayout gridBagLayout2 = new GridBagLayout();

    boolean isVoc = false;
    JRadioButton interactiveR = new JRadioButton();
    JRadioButton batchR = new JRadioButton();
    String[] colHeadR = {"Report Fields","Compatibility","Color"};
    String[] colHeadL = {"Base Fields","Compatibility","Color"};
    Vector baseField = new Vector();
    Vector compatibility = new Vector();    //6-15-01
    Vector color = new Vector();

    //final static int MAXSELECTEDROW = 50;
    Hashtable templateInfoH = null;
    String mfgID = "";
    String lastFacID = "";

    boolean firstTime = true;
    StaticJLabel batchNameL = new StaticJLabel();
    JTextField batchNameT = new JTextField();

    ItemListener repCatItemListener;
    ItemListener repWorkAreaItemListener;
    boolean isWACItemListen = false;
    StaticJLabel jLabel3 = new StaticJLabel();

    JRadioButton hourlyRB = new JRadioButton();
    JRadioButton fiveHour6ARB = new JRadioButton();
    JRadioButton fiveHour7ARB = new JRadioButton();

    /**
     *  Constructor for the AdHocVocHourly object
     *
     *@param  cmis  Description of Parameter
     */
    public AdHocVocHourly(CmisApp cmis)
    {
        this(cmis, false);
    }


    /**
     *  Constructor for the AdHocVocHourly object
     *
     *@param  cmis         Description of Parameter
     *@param  isVocReport  Description of Parameter
     */
    public AdHocVocHourly(CmisApp cmis, boolean isVocReport)
    {
        super(cmis, "Share", "Formatted Monthly VOC");
        isVoc = isVocReport;
    }

    public void loadScreen()
    {
        if (firstTime)
        {
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

    public void loadIt()
    {
        AdHocVocHourlyThread ahrlt = new AdHocVocHourlyThread(this,"load");
        ahrlt.start();
    }
    public void loadItAction(String action)
    {
        CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
        try
        {
            ClientHelpObjs.setEnabledContainer(this,false);
            TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocHourlyVocReport",cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","GET_USAGE_TEMPLATE_INFO"); //String, String
            query.put("USER_ID",cmis.getUserId());          //String, Integer
            Hashtable result = cgi.getResultHash(query);
            if (result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
                ClientHelpObjs.setEnabledContainer(this,true);
                return;
            }

            templateInfoH = (Hashtable)result.get("USAGE_TEMPLATE_INFO");
            buildComboBoxes();
            facilityChanged();
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
        radioButtonClicked();
        setComboColor();
    }

    public void loadTemplate(String template)
    {
        CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
        try
        {
            ClientHelpObjs.setEnabledContainer(this,false);
            TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocHourlyVocReport",
                                                              cmis);
            Hashtable query = new Hashtable();
            query.put("ACTION","LOAD_USAGE_TEMPLATE_INFO"); //String, String
            query.put("USER_ID",cmis.getUserId());    //String, Integer
            query.put("TEMPLATE",template);           //String, String
            Hashtable result = cgi.getResultHash(query);
            if (result == null)
            {
                GenericDlg.showAccessDenied(cmis.getMain());
                ClientHelpObjs.setEnabledContainer(this,true);
                return;
            }

            Hashtable infoH = (Hashtable)result.get("TEMPLATE_INFO");
            loadData(infoH);

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
        radioButtonClicked();
        super.setReportTitle("Ad Hoc Usage - "+template);
        //doesn't work ClientHelpObjs.makeDefaultColors(this);
    }

    public void loadData(Hashtable data)
    {
        String mfg = (String)data.get("MANUFACTURER");
        String mfgId = (String)data.get("MANUFACTURER_ID");
        String partN = (String)data.get("PART_NO");
        String type = (String)data.get("TYPE");
        String beginY = (String)data.get("BEGIN_YEAR");
        String beginM = (String)data.get("BEGIN_MONTH");
        String endY = (String)data.get("END_YEAR");
        String endM = (String)data.get("END_MONTH");
        String facID = (String)data.get("FACILITY_ID");
        String workArea = (String)data.get("WORK_AREA");
        String method = (String)data.get("METHOD");
        String unitOp = (String)data.get("UNIT_OP");
        String process = (String)data.get("PROCESS");
        String emissionPt = (String)data.get("EMISSION_POINT");
        String category = (String)data.get("CATEGORY");
        String CASList = (String)data.get("CAS_LIST");
        Vector report = (Vector)data.get("REPORT_FIELDS");

        //Vector base = (Vector)data.get("BASE_FIELD");

        //selecting report type

        //selecting conditions
        //manufacturer
        //part no

        if (facID.startsWith("All"))
        {
            facC.setSelectedIndex(0);
        }
        else
        {
            facC.setSelectedItem(facID);
        }
        facC.revalidate();
        facC.setSelectedItem(facID);
        //date
        Integer ey = new Integer(endY);
        Integer em = new Integer(endM);
        endYearC.setSelectedIndex(ey.intValue());
        endYearC.revalidate();

    }

    void setComboColor()
    {
        DataJLabel tmp = new DataJLabel();
        tmp.setText("temp");
        Color keep = tmp.getForeground();
        //Color keep = new Color(250,250,200);
        facC.setForeground(keep);
        endMonthC.setForeground(keep);
        endYearC.setForeground(keep);
    }


    /**
     *  Sets the FacCombo attribute of the AdHocVocHourly object
     *
     *@param  fc  The new FacCombo value
     */
    public void setFacCombo(FacilityCombo fc)
    {
    }

    public void openTemplate()
    {
        showOpenTemplateDlg();
    }
    public void showOpenTemplateDlg()
    {
        OpenTemplateDlg otd = new OpenTemplateDlg(cmis,
                                                  "Open Template",
                                                  true,"AdHocHourlyVocReport");
        if (otd.loadData())
        {
            otd.setVisible(true);
            if (otd.isChanged())
            {
                String selectedTemplate = otd.getSelectedTemplate();
                if (!BothHelpObjs.isBlankString(selectedTemplate))
                {
                    //super.setReportTitle("Ad Hoc Usage - "+selectedTemplate);
                    loadTemplate(selectedTemplate);
                }
            }
        }
    }

    public void saveTemplate()
    {
        showSaveTemplateDlg();
    }
    public void showSaveTemplateDlg()
    {
        //save selected info
        Hashtable currentDataH = saveSelectedData();
        SaveTemplateDlg std = new SaveTemplateDlg(cmis,"Save Template",true,"AdHocHourlyVocReport");
        String currentT = super.getTitle();
        String currentTemplate = "";
        if (currentT.indexOf("-") > 0)
        {
            currentTemplate = currentT.substring(currentT.indexOf("-")+2);
            std.updateL.setText(currentTemplate);
            std.updateRB.setSelected(true);
            std.newRB.setSelected(false);
            std.newT.setEnabled(false);
        }
        else
        {
            std.newRB.setSelected(true);
            std.newT.setEnabled(true);
            std.updateRB.setSelected(false);
            std.updateRB.setEnabled(false);
        }
        std.setSelectedData(currentDataH);
        std.setVisible(true);
        if (std.isChanged())
        {
            String newTemplate = std.getTemplateName();
            if (!newTemplate.equals(currentTemplate))
            {
                loadTemplate(newTemplate);
            }
        }
    }

    public void clearTemplate()
    {
        super.setReportTitle("Formatted Monthly VOC");
        reSetAllComponents();
    }

    public void reSetAllComponents()
    {
        //re-setting top
        radioButtonClicked();

        //re-setting middle
        facC.setSelectedItem(cmis.getPrefFac());
        endMonthC.reFresh(-1);

        //re-setting combo
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
        //System.out.println("now i am in adhoc:");
        String myTitle;
        Hashtable currentDataH = saveSelectedData();

        CursorChange.setCursor(this,Cursor.WAIT_CURSOR);
        try
        {
            if (interactiveR.isSelected())
            {
                TcmisHttpConnection cgi = new TcmisHttpConnection("AdHocHourlyVocReport",cmis);
                Hashtable query = new Hashtable();
                query.put("ACTION","CREATE_REPORT");        //String, String
                query.put("SELECTED_DATA",currentDataH);    //String, Hashtable
                query.put("WHICH_SCREEN","FORMATTED_VOC_MONTHLY_USAGE");
                query.put("USER_ID",cmis.getUserId().toString());      //String, String
                Hashtable result = cgi.getResultHash(query);
                if (result == null)
                {
                    GenericDlg.showAccessDenied(cmis.getMain());
                    return;
                }
                Boolean suc = (Boolean)result.get("SUCCEED");
                String msg = (String)result.get("MSG");
                if (suc.booleanValue())
                {
                    try
                    {
                        String url = (String)result.get("URL");
                        new URLCall(url,cmis);
                        //5-10-01 give the user some response so they know what to do.
                        GenericDlg gd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
                        gd.setMsg(msg);
                        gd.setVisible(true);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    GenericDlg gd = new GenericDlg(cmis.getMain(),"Error", true);
                    gd.setMsg(msg);
                    CenterComponent.centerCompOnScreen(gd);
                    gd.setVisible(true);
                }
            }
            else
            {
                Hashtable query = new Hashtable();
                query.put("ACTION","CREATE_REPORT");         //String, String
                query.put("WHICH_SCREEN","FORMATTED_VOC_MONTHLY_USAGE");
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
                GenericDlg gd = new GenericDlg(cmis.getMain(),"Action Succeeded",true);
                gd.setMsg(" Your report has been sent to the server for processing,\n an email will notify you upon completion.\n You may now resume other activities within tcmIS.\n Batch report(s) that is older than two weeks will be deleted.");
                gd.setVisible(true);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
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
    public Hashtable saveSelectedData()
    {
        Hashtable currentData = new Hashtable();
        //getting method
        if (interactiveR.isSelected())
        {
            currentData.put("METHOD","Active");
        }
        else
        {
            currentData.put("METHOD","Batch");
        }
        //what type of report
        currentData.put("FACILITY_ID",facC.getSelectedItem().toString());
        currentData.put("END_MONTH",new Integer(endMonthC.getSelectedIndex()));
        currentData.put("END_YEAR",new Integer(endYearC.getSelectedIndex()));
        currentData.put("END_YEARN",(endYearC.getSelectedItem()));
        if (hourlyRB.isSelected()) {
          currentData.put("REPORT_TYPE","HOURLY");
        }else if (this.fiveHour6ARB.isSelected()) {
          currentData.put("REPORT_TYPE","FIVE_HOUR_6A");
        }else {
          currentData.put("REPORT_TYPE","FIVE_HOUR_7A");
        }
        return currentData;
    }


    /**
     *  Description of the Method
     */
    void radioButtonClicked()
    {
        this.batchNameL.setEnabled(this.batchR.isSelected());
        this.batchNameT.setEnabled(this.batchR.isSelected());
    }

    void reSetColoredCell(CmisTable myTable) {
      AttributiveCellTableModel model = (AttributiveCellTableModel)myTable.getModel();
      ColoredCell cellAtt = (ColoredCell)model.getCellAttribute();
      for (int i = 0; i < myTable.getRowCount(); i++) {
        cellAtt.setForeground((Color)myTable.getModel().getValueAt(i,2),i,0);
      }
      myTable.repaint();
    }

    /**
     *  Description of the Method
     *
     *@exception  Exception  Description of Exception
     */
    private void jbInit() throws Exception
    {
        jLabel2.setText("Month:");
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("Facility:");
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        //casT.setEditable(false);
        interactiveR.setText("Interactive Generation");
        interactiveR.addActionListener(new java.awt.event.ActionListener()
                                       {
                public void actionPerformed(ActionEvent e)
                {
                    interactiveR_actionPerformed(e);
                }
            }
        );
        batchR.setText("Batch Generation");
        batchR.addActionListener(new java.awt.event.ActionListener()
                                 {

                public void actionPerformed(ActionEvent e)
                {
                    batchR_actionPerformed(e);
                }
            }
        );
        endMonthC.setMaximumSize(new Dimension(32767, 22));
        endMonthC.setMinimumSize(new Dimension(90, 22));
        endMonthC.setPreferredSize(new Dimension(90, 22));
        endMonthC.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            endMonthC_actionPerformed(e);
          }
        });


        batchNameL.setText("Rpt Name :");
        batchNameL.setEnabled(false);
        batchNameT.setMaximumSize(new Dimension(90, 21));
        batchNameT.setMinimumSize(new Dimension(90, 21));
        batchNameT.setPreferredSize(new Dimension(90, 21));
        batchNameT.setText("");
        batchNameT.setEnabled(false);

        bottomP.setAlignmentX((float) 0.0);
        bottomP.setMaximumSize(new Dimension(32767, 1000));
        bottomP.setMinimumSize(new Dimension(10, 400));
        bottomP.setPreferredSize(new Dimension(200, 400));
        bottomP.setLayout(gridBagLayout2);
        facC.setMaximumSize(new Dimension(120, 22));
        facC.setMinimumSize(new Dimension(120, 22));
        facC.setPreferredSize(new Dimension(120, 22));

        this.setLayout(new BorderLayout());
        jLabel3.setText("Year");
        this.setAlignmentX((float) 1.0);
        hourlyRB.setText("Hourly");
        hourlyRB.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            hourlyRB_actionPerformed(e);
          }
        });
        fiveHour6ARB.setText("Five - Hour Average 433.6A (Paint Booth)");
        fiveHour6ARB.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            fiveHour6ARB_actionPerformed(e);
          }
        });
        fiveHour7ARB.setText("Five - Hour Average 433.7A (Surface Coating)");
        fiveHour7ARB.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            fiveHour7ARB_actionPerformed(e);
          }
        });

        this.add(bottomP, BorderLayout.SOUTH);
        bottomP.add(jLabel1, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 3, 3), 0, 0));
        bottomP.add(facC, new GridBagConstraints2(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 55, 3, 0), 0, 0));

        bottomP.add(hourlyRB, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(100, 10, 3, 3), 0, 0));
        bottomP.add(fiveHour6ARB, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(100, 95, 3, 3), 0, 0));
        bottomP.add(fiveHour7ARB, new GridBagConstraints(2, 1, 5, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(100, 3, 3, 3), 0, 0));

        bottomP.add(interactiveR, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(100, 10, 3, 3), 0, 0));
        bottomP.add(batchR, new GridBagConstraints(2, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(100, 3, 3, 3), 0, 0));
        bottomP.add(batchNameL, new GridBagConstraints(6, 2, GridBagConstraints.REMAINDER, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(100, 5, 0, 0), 0, 0));
        bottomP.add(batchNameT, new GridBagConstraints(6, 2, GridBagConstraints.REMAINDER, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(100, 70, 0, 3), 0, 0));
        bottomP.add(jLabel2, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
        bottomP.add(endMonthC, new GridBagConstraints2(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));

        endYearC.setMaximumSize(new Dimension(32767, 22));
        endYearC.setMinimumSize(new Dimension(65, 22));
        endYearC.setPreferredSize(new Dimension(65, 22));
        endYearC.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            endYearC_actionPerformed(e);
          }
        });
        bottomP.add(endYearC, new GridBagConstraints2(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 3, 3), 0, 0));

        hourlyRB.setSelected(true);
        bottomP.validate();
        this.validate();

        radioButtonClicked();
        interactiveR.setSelected(true);
        DbTableModel modelL = new DbTableModel(colHeadL);
        DbTableModel modelR = new DbTableModel(colHeadR);
        ClientHelpObjs.makeDefaultColors(this);
    }

    void buildComboBoxes()
    {
        Hashtable facApp = (Hashtable)templateInfoH.get("FACILITY_WORK_AREA");
        facC.removeAllItems();
        facC = ClientHelpObjs.loadChoiceFromVector(facC,(Vector)facApp.get("FACILITY"));
        facC.setSelectedItem(cmis.getPrefFac());
        facC.addItemListener(new java.awt.event.ItemListener(){
          public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange() == evt.SELECTED) {
              facilityChanged();
            }
          }
        });

        endYearC.removeAllItems();
        endYearC.reFresh(cmis.yearOfReport.intValue(),cmis.numberOfYear.intValue(),0);
    }

    void facilityChanged() {
        if ("Dallas Love".equalsIgnoreCase(facC.getSelectedItem().toString())) {
          fiveHour6ARB.setEnabled(true);
          fiveHour7ARB.setEnabled(true);
        }else {
          hourlyRB.setSelected(true);
          fiveHour6ARB.setEnabled(false);
          fiveHour7ARB.setEnabled(false);
          fiveHour6ARB.setSelected(false);
          fiveHour7ARB.setSelected(false);
        }
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
            batchNameL.setEnabled(true);
            batchNameT.setEnabled(true);
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

    void hourlyRB_actionPerformed(ActionEvent e) {
      if (hourlyRB.isSelected()) {
        fiveHour6ARB.setSelected(false);
        fiveHour7ARB.setSelected(false);
      }else {
        hourlyRB.setSelected(true);
      }
    }

    void fiveHour6ARB_actionPerformed(ActionEvent e) {
      if (fiveHour6ARB.isSelected()) {
        hourlyRB.setSelected(false);
        fiveHour7ARB.setSelected(false);
      }else {
        fiveHour6ARB.setSelected(true);
      }
    }

    void fiveHour7ARB_actionPerformed(ActionEvent e) {
      if (fiveHour7ARB.isSelected()) {
        hourlyRB.setSelected(false);
        fiveHour6ARB.setSelected(false);
      }else {
        fiveHour7ARB.setSelected(true);
      }
    }

  void endMonthC_actionPerformed(ActionEvent e) {
  }

  void endYearC_actionPerformed(ActionEvent e) {
  }

class Report_Category_actionAdapter implements java.awt.event.ItemListener{
  AdHocVocHourly adaptee;

  Report_Category_actionAdapter(AdHocVocHourly adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent evt) {
    //System.out.println("======= item state change report category ");
  }

}

class Report_WorkArea_actionAdapter implements java.awt.event.ItemListener{
  AdHocVocHourly adaptee;

  Report_WorkArea_actionAdapter(AdHocVocHourly adaptee) {
    this.adaptee = adaptee;
  }

  public void itemStateChanged(ItemEvent evt) {
    //System.out.println("======= item state change work area ");
  }
}


}
class AdHocVocHourlyThread extends Thread
{
    AdHocVocHourly ahur;
    String action;
    public AdHocVocHourlyThread(AdHocVocHourly ahur,String action)
    {
        super();
        this.ahur = ahur;
        this.action = action;
    }
    public void run()
    {
        if(action.equalsIgnoreCase("load"))
        {
            ahur.loadItAction("load");
        }
        else if(action.equalsIgnoreCase("reload"))
        {
            ahur.loadItAction("reload");
        }
    }
}
