

//Title:        TCM - CMIS
//Version:      
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import java.awt.Component;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.EventObject;
import javax.swing.*;

public final class TextCellEditor implements TableCellEditor {

      ScrollText editorComponent;
      public TextCellEditor(ScrollText sc){
         editorComponent = sc;
      }

      public boolean isCellEditable(EventObject anEvent) {
        /*
        if (anEvent instanceof MouseEvent) {
	        if (((MouseEvent)anEvent).getClickCount() == 1){
		        return true;
          }
        }
        */
        return true;
      }

      public boolean shouldSelectCell(EventObject anEvent) {
        return true;
      }

      
      public void cancelCellEditing() {

      }


      public void addCellEditorListener(CellEditorListener p0){
         
      };

      public void removeCellEditorListener(CellEditorListener p0){};

      public boolean stopCellEditing(){return true;};
      
      public Component getTableCellEditorComponent(JTable table, Object value,
					  boolean isSelected, int row, int column){
            table.setRowSelectionInterval(row,row);
            table.repaint();
            ScrollText st = new ScrollText();
            st.setText(((JTextArea)value).getText());
            return st;
      }

      public Object getCellEditorValue() {
        return new String(((ScrollText)editorComponent).getText());
      }
}

