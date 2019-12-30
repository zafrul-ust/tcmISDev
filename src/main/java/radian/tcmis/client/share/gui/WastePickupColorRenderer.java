package radian.tcmis.client.share.gui;


import java.awt.*;


//import com.borland.dx.dataset.*;
//import com.borland.jbcl.view.*;

import javax.swing.*;
import javax.swing.table.*;

public class WastePickupColorRenderer extends DefaultTableCellRenderer {

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

	          if(isSelected) {
               //System.out.println("Selected");
              if(((Boolean) table.getModel().getValueAt(row,bolCol)).booleanValue()) {
                //System.out.println("bol");
                setForeground(table.getSelectionForeground());
                //setForeground(Color.black);
                setBackground(Color.orange);
              }else{
                //System.out.println("non");
                setForeground(table.getSelectionForeground());
	              setBackground(table.getSelectionBackground());
                //setForeground(Color.black);
	              //setBackground(Color.white);
              }

            } else if(((Boolean) table.getModel().getValueAt(row,bolCol)).booleanValue()) {
                //System.out.println("not Selected bol");
                setForeground(table.getForeground());
                //setForeground(Color.black);
                setBackground(Color.yellow);
            }else{
                //System.out.println("not Selected non");
	              setForeground(Color.black);
	              setBackground(Color.white);
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

            return this;
        }
}
