
//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:

package radian.tcmis.client.share.gui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

//import radian.tcmis.client.share.reports.*;
import radian.tcmis.client.share.helpers.*;
import com.borland.jbcl.layout.*;

public class ReportGroupDlg extends JDialog {

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton button1 = new JButton();
  JButton button2 = new JButton();
  Border border1;
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridLayout gridLayout1 = new GridLayout();

  CmisApp cmis;
  int[] groupBy;
  JComboBox c1 = new JComboBox();
  JComboBox c2 = new JComboBox();
  JComboBox c3 = new JComboBox();
  JComboBox c4 = new JComboBox();
  XYLayout xYLayout1 = new XYLayout();
  final String NONE = "--";

  boolean isVoc = false;

  public ReportGroupDlg(CmisApp cmis,int[] groupBy,boolean isVoc) {
    super(cmis.getMain(), "Set Report Grouping", true);
    this.cmis = cmis;
    this.groupBy = groupBy;
    this.isVoc = isVoc;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    pack();
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createRaisedBevelBorder();
    jPanel1.setLayout(gridLayout1);
    panel2.setBorder(border1);
    panel2.setLayout(xYLayout1);
    button1.setText("OK");
    button1.addActionListener(new ReportGroupDlg_button1_actionAdapter(this));
    button2.setText("Cancel");
    loadComboBoxes();
    this.setResizable(false);

    gridLayout1.setHgap(4);
    button2.addActionListener(new ReportGroupDlg_button2_actionAdapter(this));
    this.addWindowListener(new ReportGroupDlg_this_windowAdapter(this));
    panel1.setLayout(gridBagLayout1);
    c1.setMaximumSize(new Dimension(150, 24));
    c1.setMinimumSize(new Dimension(150, 24));
    c1.setPreferredSize(new Dimension(150, 24));
    c2.setMaximumSize(new Dimension(150, 24));
    c2.setMinimumSize(new Dimension(150, 24));
    c2.setPreferredSize(new Dimension(150, 24));
    c3.setMaximumSize(new Dimension(150, 24));
    c3.setMinimumSize(new Dimension(150, 24));
    c3.setPreferredSize(new Dimension(150, 24));
    c4.setMaximumSize(new Dimension(150, 24));
    c4.setMinimumSize(new Dimension(150, 24));
    c4.setPreferredSize(new Dimension(150, 24));
    panel1.setMaximumSize(new Dimension(401, 302));
    panel1.setMinimumSize(new Dimension(401, 302));
    panel1.setPreferredSize(new Dimension(401, 302));
    panel1.add(panel2, new GridBagConstraints2(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    panel2.add(c1, new XYConstraints(96, 73, -1, -1));
    panel2.add(c2, new XYConstraints(116, 103, -1, -1));
    panel2.add(c3, new XYConstraints(136, 133, -1, -1));
    panel2.add(c4, new XYConstraints(156, 163, -1, -1));
    panel1.add(jPanel1, new GridBagConstraints2(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 8, 4, 8), 0, 0));
    jPanel1.add(button1, null);
    jPanel1.add(button2, null);
    getContentPane().add(panel1);
  }

  void loadComboBoxes(){
    String[] myGroupString;
    if(isVoc){
      myGroupString = new String[] {"Facility","Work Area","By Month"};
    }else{
      myGroupString = new String[] {"Facility","Work Area","CAS #/SARA Group","Delivery Point","By Month"};
    }

    for(int i=0;i<myGroupString.length;i++){
      c1.addItem(myGroupString[i]);
      c2.addItem(myGroupString[i]);
      c3.addItem(myGroupString[i]);
      c4.addItem(myGroupString[i]);
    }
    c1.addItem(NONE);
    c2.addItem(NONE);
    c3.addItem(NONE);
    c4.addItem(NONE);
    c1.setSelectedIndex(c1.getItemCount()-1);
    c2.setSelectedIndex(c2.getItemCount()-1);
    c3.setSelectedIndex(c3.getItemCount()-1);
    c4.setSelectedIndex(c4.getItemCount()-1);
    try{c1.setSelectedIndex(groupBy[0]);}catch(Exception e1){}
    try{c2.setSelectedIndex(groupBy[1]);}catch(Exception e1){}
    try{c3.setSelectedIndex(groupBy[2]);}catch(Exception e1){}
    try{c4.setSelectedIndex(groupBy[3]);}catch(Exception e1){}

    if((c1.getItemCount()-1)<4){
      c4.setVisible(false);
    }
  }

  public int[] getGroupBy(){
    return groupBy;
  }

  boolean loadGroupBy(){
    Vector v = new Vector();
    if(!c1.getSelectedItem().toString().equals(NONE))v.addElement(new Integer(c1.getSelectedIndex()));
    if(!c2.getSelectedItem().toString().equals(NONE))v.addElement(new Integer(c2.getSelectedIndex()));
    if(!c3.getSelectedItem().toString().equals(NONE))v.addElement(new Integer(c3.getSelectedIndex()));
    if(!c4.getSelectedItem().toString().equals(NONE))v.addElement(new Integer(c4.getSelectedIndex()));
    if(v.size() < 1) {
      showErrorDlg("You must select at least one group.");
      return false;
    }
    for(int i=0;i<v.size()-1;i++){
      Integer i1 = (Integer)v.elementAt(i);
      Integer i2 = (Integer)v.elementAt(i+1);
      if(i1.intValue() == i2.intValue()){
        showErrorDlg("Each group must be unique.");
        return false;
      }
    }
    groupBy = new int[v.size()];
    for(int i=0;i<v.size();i++){
      groupBy[i] = ((Integer)v.elementAt(i)).intValue();
    }
    return true;
  }

  void showErrorDlg(String s){
    GenericDlg gd = new GenericDlg(cmis.getMain(),"Error",true);
    gd.setMsg(s);
    CenterComponent.centerCompOnScreen(gd);
    gd.setVisible(true);
  }

  // OK
  void button1_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    if(!loadGroupBy()) return;
    dispose();
  }

  // Cancel
  void button2_actionPerformed(ActionEvent e) {
    ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
    dispose();
  }

  void this_windowClosing(WindowEvent e) {
    dispose();
  }
}

class ReportGroupDlg_button1_actionAdapter implements ActionListener {
  ReportGroupDlg adaptee;

  ReportGroupDlg_button1_actionAdapter(ReportGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button1_actionPerformed(e);
  }
}

class ReportGroupDlg_button2_actionAdapter implements ActionListener {
  ReportGroupDlg adaptee;

  ReportGroupDlg_button2_actionAdapter(ReportGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button2_actionPerformed(e);
  }
}

class ReportGroupDlg_this_windowAdapter extends WindowAdapter {
  ReportGroupDlg adaptee;

  ReportGroupDlg_this_windowAdapter(ReportGroupDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}
