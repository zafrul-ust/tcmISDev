package radian.tcmis.client.share.gui;


import java.awt.*;
import java.util.*;
import java.text.*;
//import com.borland.jbcl.dataset.*;
//import com.borland.jbcl.view.*;

import javax.swing.*;

import javax.swing.table.*;

public class CalendarCellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

          Object o = table.getModel().getValueAt(row,column);

          if(table.isRowSelected(row)) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
          }else{
            setForeground(Color.black);
            setBackground(Color.white);
          }
          String s;
          Calendar c = (Calendar)o;
          DateFormatSymbols dfs = new DateFormatSymbols();
          String[] months = dfs.getMonths();
          String monthName = months[c.get(Calendar.MONTH)];
          s = monthName +" "+c.get(Calendar.DAY_OF_MONTH) + ", "+c.get(Calendar.YEAR);
          this.setValue(s);
          this.setForeground(Color.black);
          this.setFont(new Font(this.getFont().getName(),this.getFont().PLAIN, this.getFont().getSize()));
          //this.setHorizontalAlignment(4);
         // this.setHorizontalTextPosition(4);
          return this;
        }


}