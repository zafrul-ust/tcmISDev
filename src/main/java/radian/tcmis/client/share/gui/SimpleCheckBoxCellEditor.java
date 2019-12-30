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

public final class SimpleCheckBoxCellEditor extends DefaultCellEditor {
      Boolean value  = null;
      public SimpleCheckBoxCellEditor(JCheckBox x){
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

      public void cancelCellEditing() {
      }

      public Component getTableCellEditorComponent(JTable table, Object value,
					  boolean isSelected, int row, int column){

            if (value instanceof JCheckBox) {
              editorComponent = (JCheckBox)value;
              System.out.println("------editor here: "+row+":"+column+"-check");
            }else {
              System.out.println("------editor here: "+row+":"+column+"-"+value.toString());
            }
            table.setRowSelectionInterval(row,row);
            table.repaint();
            return (JCheckBox) editorComponent;
      }

      public Object getCellEditorValue() {
        return new Boolean(((JCheckBox)editorComponent).isSelected());
      }
}


