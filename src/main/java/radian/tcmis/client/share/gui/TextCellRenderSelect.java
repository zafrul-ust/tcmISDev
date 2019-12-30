package radian.tcmis.client.share.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import radian.tcmis.both1.helpers.*;
import javax.swing.table.*;



public final class TextCellRenderSelect extends JTextArea implements TableCellRenderer {

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

            //2-05-02
            Hashtable keyCol = BothHelpObjs.getColKey();
            int APP_COL = 0;
            int AVAL_COL = 0;
            AVAL_COL = ((Integer)keyCol.get("Bulk Gas")).intValue();
            APP_COL = ((Integer)keyCol.get("Status")).intValue();

            //System.out.println("-------- selected?: "+sel);
            if (sel && "y".equalsIgnoreCase(partColor)) {
              setBackground(table.getSelectionBackground());
              //setForeground(table.getSelectionForeground());
              setBackground(new Color(150,150,255));
            }else if (sel) {
                setBackground(table.getSelectionBackground());
                //setForeground(table.getSelectionForeground());
                setBackground(new Color(150,150,255));
            }else if ("y".equalsIgnoreCase(partColor)) {
                if (!table.getValueAt(row,AVAL_COL).toString().equalsIgnoreCase("0") &&
                     table.getValueAt(row,APP_COL).toString().equalsIgnoreCase("approved")) {    //2-05-02  NEED to find a way to get the available qty col and others
                  setBackground((Color)h.get("BULK_GAS_COLOR"));
                }else {
                  setBackground((Color)h.get("PART_COLOR"));
                }
                setForeground(table.getSelectionForeground());
            }else {
                setForeground(table.getForeground());
                if (!table.getValueAt(row,AVAL_COL).toString().equalsIgnoreCase("0") &&
                     table.getValueAt(row,APP_COL).toString().equalsIgnoreCase("approved")) {    //2-05-02  NEED to find a way to get the available qty col and others
                  setBackground((Color)h.get("BULK_GAS_COLOR"));
                }else {
                  setBackground(table.getBackground());
                }
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