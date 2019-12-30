

//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public final class CheckBoxCellEditor extends DefaultCellEditor {

      Boolean value  = null;

      public CheckBoxCellEditor(JCheckBox x){
        super(x);
      }

      public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
	        if (((MouseEvent)anEvent).getClickCount() == 1){
		        return true;
          }
        }
        return false;
      }

      public boolean shouldSelectCell(EventObject anEvent) {
        return true;
      }
      /*
      public boolean stopCellEditing() {
        return true;
      }
      */
      public void cancelCellEditing() {
      }


      public Component getTableCellEditorComponent(JTable table, Object value,
					  boolean isSelected, int row, int column){
            table.setRowSelectionInterval(row,row);
            table.repaint();
            ((JCheckBox)editorComponent).setSelected(((Boolean)value).booleanValue());
            return (JCheckBox) editorComponent;
      }

      public Object getCellEditorValue() {
        return new Boolean(((JCheckBox)editorComponent).isSelected());
      }


}


