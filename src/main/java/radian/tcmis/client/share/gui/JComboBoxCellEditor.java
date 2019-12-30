package radian.tcmis.client.share.gui;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class JComboBoxCellEditor extends DefaultCellEditor implements ItemListener, ActionListener {
  protected JComboBox currentBox = null;

  public JComboBoxCellEditor(JComboBox box) {
    super(box);
    currentBox = box;
  }

  // These methods implement the TableCellEditor interface
  public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
    editorComponent = (JComboBox) value;
    ((JComboBox)editorComponent).setFont(table.getFont());
    ((JComboBox)editorComponent).addItemListener(this);
    ((JComboBox)editorComponent).addActionListener(this);
    currentBox = (JComboBox) editorComponent;
    currentBox.addItemListener(this);
    currentBox.addActionListener(this);
    // rfz allow editing .... currentBox.setEditable(false);
    table.setRowSelectionInterval(row,row);
    table.repaint();
    return editorComponent;
  }

  public Object getCellEditorValue() {
    return currentBox;
  }

  public boolean isCellEditable(EventObject evt) {
    if (evt instanceof MouseEvent) {
      if (((MouseEvent)evt).getClickCount() >= getClickCountToStart()) {
        return true;
      }
    }
    return false;
  }

  public void cancelCellEditing() {
    fireEditingStopped();
  }

  public void itemStateChanged(ItemEvent e){
    fireEditingStopped();
  }

  public void actionPerformed(ActionEvent e) {
    fireEditingStopped();
  }
}
