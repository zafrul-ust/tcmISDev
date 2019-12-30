package radian.tcmis.client.share.gui;

import java.awt.*;


//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;

//import radian.tcmis.ray.client.share.httpCgi.*;


import javax.swing.*;
import javax.swing.table.*;

//import radian.tcmis.ray.client.share.helpers.*;


public class WasteColorCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

            int bolCol = -1;
            for (int i=0;i<table.getModel().getColumnCount();i++){
                if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
                      bolCol = i;
                      break;
                }
            }

            int ageCol=-1;
            for (int i=0;i<table.getModel().getColumnCount();i++){
              if (table.getColumnClass(i).getName().indexOf("Boolean") > 0){
                if (table.getColumnName(i).equalsIgnoreCase("Age Warning")){
                  ageCol = i;
                  break;
                }
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

            //System.out.println("========== what going on isselected: "+sel);

            if(sel)
            {
              if ((ageCol > -1?((Boolean)table.getModel().getValueAt(row,ageCol)).booleanValue():false)) {
                setForeground(table.getForeground());
                setBackground(Color.red);
              }else if((bolCol > -1?((Boolean)table.getModel().getValueAt(row,bolCol)).booleanValue():false)) {
                setForeground(Color.black);
                setBackground(Color.yellow);
              }else{
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
              }
            }else {
              if ((ageCol > -1?((Boolean)table.getModel().getValueAt(row,ageCol)).booleanValue():false)) {
                setForeground(Color.black);
                setBackground(Color.pink);
              } else if((bolCol > -1?((Boolean)table.getModel().getValueAt(row,bolCol)).booleanValue():false)) {
                setForeground(Color.black);
                setBackground(new Color(255,255,185));
              } else{
                setForeground(Color.black);
                setBackground(Color.white);
              }
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

            setValue(value);
            setOpaque(true);
            return this;
        }
}
