package radian.tcmis.client.share.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import radian.tcmis.both1.helpers.*;
import javax.swing.table.*;



public final class TextCellRender extends JTextArea implements TableCellRenderer {

        public Component getTableCellRendererComponent(
                                JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {

            boolean sel = false;
            int[] is = table.getSelectedRows();
            for(int i=0;i<is.length;i++){
              if(is[i] == row) {
                sel = true;
                break;
              }
            }
            Hashtable h = BothHelpObjs.getTableStyle();
            String fontName = (String)h.get("FONT_NAME");
            Integer fontStyle = (Integer)h.get("FONT_STYLE");
            Integer fontSize = (Integer)h.get("FONT_SIZE");
            setFont(new Font(fontName,fontStyle.intValue(),fontSize.intValue()));
            //setting background color
            String partColor = (String)table.getModel().getValueAt(row,0);
            //System.out.println("-------- part color?: "+partColor);
            if (sel && "y".equalsIgnoreCase(partColor)) {
              //setBackground(table.getSelectionBackground());
              setBackground(new Color(150,150,255));
              setForeground(table.getSelectionForeground());
            }else if (sel) {
                setBackground(new Color(150,150,255));
                //setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }else if ("y".equalsIgnoreCase(partColor)) {
                setBackground((Color)h.get("PART_COLOR"));
                setForeground(table.getSelectionForeground());
            }else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }

            //setText( ( " \n"+((JTextArea)value).getText() ) );
            setText(((JTextArea)value).getText());
            setWrapStyleWord(true);
            setLineWrap(true);

            setOpaque(true);
            this.repaint();
            return this;
        }
}


