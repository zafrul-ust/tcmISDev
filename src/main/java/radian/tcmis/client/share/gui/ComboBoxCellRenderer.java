package radian.tcmis.client.share.gui;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */
import java.awt.*;
import javax.swing.*;


public class ComboBoxCellRenderer extends TcmisCellRenderer {
  public Component getTableCellRendererComponent(
                          JTable table, Object value,
                          boolean isSelected, boolean hasFocus,
                          int row, int column) {

    setOpaque(true);

    int critCol=-1;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("crit.") || table.getColumnName(i).equalsIgnoreCase("Critical")){
          critCol = i;
          break;
        }
      }
    }
    int canCol=-1;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("canceled")){
          canCol = i;
          break;
        }
      }
    }
    int reqCanCol=-1;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
        if (table.getColumnName(i).equalsIgnoreCase("cancel requested")){
          reqCanCol = i;
          break;
        }
      }
    }

    boolean crit = ((Boolean)table.getValueAt(row,critCol)).booleanValue();
    boolean sel = false;
    boolean cancel= (canCol>-1?((Boolean)table.getValueAt(row,canCol)).booleanValue():false);
    boolean reqCancel= (reqCanCol>-1?((Boolean)table.getValueAt(row,reqCanCol)).booleanValue():false);

    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    Component comp = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
    try {
      if(((JComboBox)value).getSelectedItem()!=null){
        ((JLabel)comp).setText(((JComboBox)value).getSelectedItem().toString());
      } else {
        ((JLabel) comp).setText(((JComboBox)value).getItemAt(0).toString());
      }
    } catch (Exception e){
      ((JLabel) comp).setText("");
    }

    String dpas = ((JComboBox)table.getModel().getValueAt(row,column)).getSelectedItem().toString();

    //setting color
    //setBackground(Color.white);

    //System.out.println("in request win: "+sel);

    if(sel)
    {
            if (!dpas.startsWith("No"))
            {
              setBackground(Color.green);
              setForeground(table.getForeground());
             //System.out.println("Selected");
             // if a critical row is selected it should be red
            } else if(reqCancel) {
             //System.out.println(" req cancel");
              setBackground(Color.orange);
              setForeground(table.getForeground());
            } else if(cancel) {
              //System.out.println("cancel");
              setBackground(Color.black);
              setForeground(Color.white);
            } else if(crit) {
              //System.out.println("bol");
              setBackground(Color.red);
              setForeground(table.getForeground());
            } else{
              //System.out.println("non");
              setForeground(table.getSelectionForeground());
              setBackground(table.getSelectionBackground());
              //setBackground(Color.red);
            }
    }
    else if (!dpas.startsWith("No"))
    {
      setBackground(new Color(165,255,165));
      setForeground(table.getForeground());
    }
    else if(reqCancel)
    {
      //System.out.println("not Selected reqcancel");
      setBackground(Color.orange);
      setForeground(table.getForeground());
    } else if(cancel) {
      //System.out.println("not Selected cancel");
      setBackground(Color.lightGray);
      setForeground(table.getForeground());
    }
    else if(crit)
    {
        // System.out.println("not Selected bol");
      setBackground(Color.pink);
      setForeground(table.getForeground());
    }
    else
    {
     //System.out.println("not Selected non");
     setForeground(Color.black);
     setBackground(Color.white);
    }

    /*if (!dpas.startsWith("No") && sel)
    {
      System.out.println("---------10 Dpas and Selected");
      setBackground(Color.magenta);
      setForeground(Color.red);
      System.out.println("---------10-11");
      //setForeground(table.getForeground());
    }
    else if (!dpas.startsWith("No"))
    {
      System.out.println("---------11 Dpas but not selected");
      //setBackground(new Color(165,255,165));
      setBackground(Color.yellow);
      setForeground(table.getForeground());
    }
    else if(sel && crit && reqCancel)
    {
    System.out.println("---------12");
      setBackground(Color.yellow);
      setForeground(table.getForeground());
    }
    else if(sel && crit && cancel)
    {
    System.out.println("---------13");
      setBackground(Color.black);
      setForeground(Color.white);
    } else if (reqCancel){
    System.out.println("---------14");
      setBackground(Color.orange);
      setForeground(table.getForeground());
    } else if (cancel && sel){
    System.out.println("---------15");
      setBackground(Color.black);
      setForeground(Color.white);
    } else if (cancel){
    System.out.println("---------16");
      setBackground(Color.lightGray);
      setForeground(table.getForeground());
    }
    else if(sel && crit)
    {
    System.out.println("---------17 Critical and Selected");
      setBackground(Color.red);
      setForeground(table.getForeground());
    }
    else if(sel)
    {
    System.out.println("---------18 Selected");
      //setBackground(new Color(204,204,255));
      setBackground(Color.lightGray);
      setForeground(table.getForeground());
    }
    else if (crit)
    {
    System.out.println("---------19 Critical");
      setBackground(Color.pink);
      setForeground(table.getForeground());
    }  else{
    System.out.println("---------20 Not selected and no color");
      //System.out.println("in request not selected : ");
      setBackground(Color.white);
      setForeground(table.getForeground());
    }*/

    setOpaque(true);
    return this;
  }
}
