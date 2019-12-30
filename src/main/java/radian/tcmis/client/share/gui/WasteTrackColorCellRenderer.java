package radian.tcmis.client.share.gui;


import java.awt.*;


//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;

import javax.swing.*;
import javax.swing.table.*;

public class WasteTrackColorCellRenderer extends DefaultTableCellRenderer {

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


            //System.out.println("========== what going on isselected: "+isSelected);

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
              if ((ageCol > -1?((Boolean)table.getModel().getValueAt(row,ageCol)).booleanValue():false)){
                setForeground(table.getSelectionForeground());
                setBackground(Color.red);
              }else{
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
              }
            }else{
              if ((ageCol > -1?((Boolean)table.getModel().getValueAt(row,ageCol)).booleanValue():false)){
                setForeground(table.getSelectionForeground());
                setBackground(Color.pink);
              }else{
                setForeground(table.getSelectionForeground());
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

            setOpaque(true);
            setValue(value);

            return this;
        }
}
