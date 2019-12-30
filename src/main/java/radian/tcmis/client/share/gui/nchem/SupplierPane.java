//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Your description

package radian.tcmis.client.share.gui.nchem;

import java.awt.*;
import radian.tcmis.client.share.gui.*;

import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.GridBagConstraints2;


class SupplierPane extends JPanel {

    StaticJLabel jLabel7 = new StaticJLabel();
    public JTextField supNameT = new JTextField();
    StaticJLabel jLabel8 = new StaticJLabel();
    public JTextField supContT = new JTextField();
    StaticJLabel jLabel9 = new StaticJLabel();
    public JTextField supEmailT = new JTextField();
    StaticJLabel jLabel10 = new StaticJLabel();
    public JTextField supPhoneT = new JTextField();
    StaticJLabel jLabel11 = new StaticJLabel();
    public JTextField supFaxT = new JTextField();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
  JCheckBox freeSample = new JCheckBox();

    public SupplierPane() {
     try  {
      jbInit();
     }
     catch(Exception ex) {
      ex.printStackTrace();
     }
    }

    private void jbInit() throws Exception {
       jLabel7.setText("Supplier Name:");
       jLabel8.setText("Contact Name:");
       jLabel9.setText("Contact email:");
       jLabel10.setText("Contact phone:");
       jLabel11.setText("Contact FAX:");
       setLayout(gridBagLayout2);
       freeSample.setText("Supplier Offers as Free Sample");
       freeSample.setFont(new java.awt.Font("Dialog", 0, 10));
       add(jLabel7, new GridBagConstraints2(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 11, 0, 0), 0, 0));
       add(supNameT, new GridBagConstraints2(0, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 11, 0, 0), 0, 0));
       add(jLabel8, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 11, 0, 0), 0, 0));
       add(supContT, new GridBagConstraints2(2, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 11, 0, 15), 0, 0));
       add(jLabel9, new GridBagConstraints2(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 11, 0, 0), 0, 0));
       add(supEmailT, new GridBagConstraints2(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 11, 21, 0), 0, 0));
       add(jLabel10, new GridBagConstraints2(1, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 11, 0, 0), 0, 0));
       add(supPhoneT, new GridBagConstraints2(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 11, 21, 0), 0, 0));
       add(jLabel11, new GridBagConstraints2(3, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 12, 0, 0), 0, 0));
       add(supFaxT, new GridBagConstraints2(3, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 12, 21, 13), 0, 0));
       this.add(freeSample, new GridBagConstraints2(4, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

       setBorder(ClientHelpObjs.groupBox("Suggested Supplier"));

       //trong 5/6
       freeSample.setSelected(false);
       freeSample.setVisible(false);

    }

    //trong 5/6
    public void showFreeSample(boolean f) {
      //System.out.println("------------------- my eval flag in load dyn data in supplier: ");
      jLabel7.setText("Supplier Name: *");
      jLabel8.setText("Contact Name: *");
      jLabel10.setText("Contact phone: *");
      freeSample.setVisible(true);
      freeSample.setSelected(f);
    }
    //5-13-01
    public void showFreeSampleNotRequired(boolean f) {
      jLabel7.setText("Supplier Name: ");
      jLabel8.setText("Contact Name: ");
      jLabel10.setText("Contact phone: ");
      freeSample.setVisible(true);
      freeSample.setSelected(f);
    }

    //trong 3/25
    public void allowEdit(boolean f){
      supNameT.setEnabled(f);
      supContT.setEnabled(f);
      supEmailT.setEnabled(f);
      supPhoneT.setEnabled(f);
      supFaxT.setEnabled(f);
      //trong 5/6
      freeSample.setEnabled(f);
    }


}