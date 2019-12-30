package radian.tcmis.client.share.gui;


import java.awt.*;
import javax.swing.*;

import javax.swing.table.*;

class CurrencyCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

          Object o = table.getModel().getValueAt(row,column);

          if(table.getSelectedRow() == row) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
          }else{
            setForeground(Color.black);
            setBackground(Color.white);
          }

          String s;
          if(o instanceof String){
            s = (String) o;
          }else if(o instanceof Double){
            s = ((Double)o).toString();
          }else if(o instanceof Integer){
            s = ((Integer)o).toString();
          }else if(o == null){
            s = "";
          }else{
            s = o.toString();
          }
          int dot = s.indexOf(".");
          if(dot < 0) {
            if(s.length() < 1) {
              s = "";
            }else{
              s = s + ".00";
            }
          }else if(dot == 0) {
            s = "0" + s + "00";
          }else{
            s = s+"00";
          }

          if(s.length() > 0) {
            s = s.substring(0,s.indexOf(".")+3);
          }
          this.setValue(s);
          this.setForeground(Color.black);
          this.setFont(new Font(this.getFont().getName(),this.getFont().PLAIN, this.getFont().getSize()));
          this.setHorizontalAlignment(4);
          this.setHorizontalTextPosition(4);

          return this;
        }


}
