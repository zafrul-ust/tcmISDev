package radian.tcmis.client.share.gui;


import java.awt.*;


//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;

import javax.swing.*;
import javax.swing.table.*;

public class ColorCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

            // On the table ALWAYS  put the cancelled column first and then
            // the cancel requested
            int reqCanCol=-1;
            for (int i=0;i<table.getModel().getColumnCount();i++){
                if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
                   if (table.getColumnName(i).equalsIgnoreCase("cancel requested")){
                      reqCanCol = i;
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

            int bolCol = -1;
            for (int i=0;i<table.getModel().getColumnCount();i++){
                if (table.getColumnClass(i).getName().indexOf("Boolean") > 0 && i!=canCol && i!=reqCanCol){
                      bolCol = i;
                      break;
                }
            }



            boolean dpasCombo = false;
            int dpasCol=-1;
            for (int i=0;i<table.getModel().getColumnCount();i++){
              if (table.getColumnClass(i).getName().indexOf("JComboBox") > 0){
                if (table.getColumnName(i).equalsIgnoreCase("dpas")){
                  dpasCol = i;
                  dpasCombo = true;
                  break;
                }
              }
            }
            for (int i=0;i<table.getModel().getColumnCount();i++){
              if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
                if (table.getColumnName(i).equalsIgnoreCase("dpas")){
                  dpasCol = i;
                  dpasCombo = false;
                  break;
                }
              }
            }
            boolean dpas = false;
            if (dpasCombo) {
              if (dpasCol > 0) {
                dpas = !((JComboBox)table.getModel().getValueAt(row,dpasCol)).getSelectedItem().toString().startsWith("No");
              }else {
                dpas = false;
              }
            }else {
              if(dpasCol > 0) {
                dpas = ((Boolean)table.getValueAt(row,dpasCol)).booleanValue();
              }else {
                dpas = false;
              }
            }

             boolean sel = false;
            int[] is = table.getSelectedRows();
            for(int i=0;i<is.length;i++){
              if(is[i] == row) {
                sel = true;
                break;
              }
            }

            if(sel)
           {
                if (dpas)
                {
                  setBackground(Color.green);
                  setForeground(table.getForeground());
                 //System.out.println("Selected");
                // if a critical row is selected it should be red
                }else if((reqCanCol>-1?((Boolean) table.getModel().getValueAt(row,reqCanCol)).booleanValue():false)) {
                 //System.out.println(" req cancel");
                  setForeground(table.getSelectionForeground());
                  setBackground(Color.yellow);
                } else if((canCol>-1?((Boolean) table.getModel().getValueAt(row,canCol)).booleanValue():false)) {
                  //System.out.println("cancel");
                  setForeground(Color.white);
                  setBackground(Color.black);
                } else if(((Boolean) table.getModel().getValueAt(row,bolCol)).booleanValue()) {
                  //System.out.println("bol");
                  setForeground(table.getSelectionForeground());
                  setBackground(Color.red);
                }else{
                  //System.out.println("non");
                  setForeground(table.getSelectionForeground());
                  setBackground(table.getSelectionBackground());
                }
            }
            else if (dpas)
            {
                setBackground(new Color(165,255,165));
                setForeground(table.getForeground());
            }
            else if((reqCanCol>-1?((Boolean) table.getModel().getValueAt(row,reqCanCol)).booleanValue():false))
            {
                //System.out.println("not Selected reqcancel");
                setForeground(table.getForeground());
                setBackground(Color.orange);
	          } else if((canCol>-1?((Boolean) table.getModel().getValueAt(row,canCol)).booleanValue():false)) {
                //System.out.println("not Selected cancel");
                setForeground(Color.black);
                setBackground(Color.lightGray);
            }
            else if(((Boolean) table.getModel().getValueAt(row,bolCol)).booleanValue())
            {
                // System.out.println("not Selected bol");
                setForeground(table.getForeground());
                setBackground(Color.pink);
            }
            else
            {
                 //System.out.println("not Selected non");
	              setForeground(Color.black);
	              setBackground(Color.white);
/*
                      setForeground(table.getForeground());
                      setBackground(Color.orange);
*/
	    }

            if (value!=null&&value.getClass()!=null&&value.getClass().getName()!=null&&value.getClass().getName().indexOf("String") > 0 ){
              setFont(table.getFont());
              if (value.toString().equals("+")){
                setHorizontalAlignment(JLabel.CENTER);
              }else{
                setHorizontalAlignment(JLabel.LEFT);
              }
            }else{
              setFont(table.getFont());
              setHorizontalAlignment(JLabel.RIGHT);
            }
            setOpaque(true);
            setValue(value);

            return this;
        }


}
