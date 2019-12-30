package radian.tcmis.both1.helpers;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

//import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

/**
 * @version 1.0 11/22/98
 */
public class AttributiveCellRendererLine extends JLabel
    implements TableCellRenderer {
  protected static Border noFocusBorder;
  protected Border columnBorder;
  protected boolean useSetAlign = false;
  protected int align;

  public AttributiveCellRendererLine() {
    noFocusBorder = new EmptyBorder(1, 2, 1, 2);
    setOpaque(true);
    setBorder(noFocusBorder);
  }

  public AttributiveCellRendererLine(int align) {
    noFocusBorder = new EmptyBorder(1, 2, 1, 2);
    setOpaque(true);
    setBorder(noFocusBorder);
    this.align = align;
    useSetAlign = true;
   }

  public Component getTableCellRendererComponent(JTable table, Object value,
                 boolean isSelected, boolean hasFocus, int row, int column) {
    Color foreground = null;
    Color background = null;
    Font font = null;

    boolean sel = false;
    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    //12-15-01
    Boolean crit = new Boolean(false);
    boolean aribaRepair = false;
    for (int i=0;i<table.getModel().getColumnCount();i++){
      if (table.getColumnName(i).equalsIgnoreCase("Critical")){
        crit = (Boolean)table.getModel().getValueAt(row,i);
        break;
      }else if (table.getColumnName(i).equalsIgnoreCase("Requestor")){
        aribaRepair = true;
        break;
      }
    }


    TableModel model = table.getModel();
    if (model instanceof AttributiveCellTableModel) {
      CellAttribute cellAtt = ((AttributiveCellTableModel)model).getCellAttribute();
      if (cellAtt instanceof ColoredCell) {
        foreground = ((ColoredCell)cellAtt).getForeground(row,column);
        background = ((ColoredCell)cellAtt).getBackground(row,column);
      }
      if (cellAtt instanceof CellFont) {
        font = ((CellFont)cellAtt).getFont(row,column);
      }
    }

    if (sel) {
      if (crit.booleanValue()) {
        setForeground(table.getSelectionForeground());
        setBackground(Color.red);
      }else if (aribaRepair) {
        setForeground(table.getSelectionForeground());
        setBackground(Color.green);
      }else {
        setForeground((foreground != null) ? foreground:table.getSelectionForeground());
        //setBackground(table.getSelectionBackground());
        setBackground(new Color(150,150,255));
      }
    } else {
      if (crit.booleanValue()) {
        setForeground(table.getForeground());
        setBackground(Color.pink);
      }else {
        setForeground((foreground != null) ? foreground:table.getForeground());
        setBackground((background != null) ? background:table.getBackground());
      }
    }
    setFont((font != null) ? font : table.getFont());

    if (hasFocus) {
      setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
      if (table.isCellEditable(row, column)) {
        setForeground((foreground != null) ? foreground
                      : UIManager.getColor("Table.focusCellForeground") );
        setBackground( UIManager.getColor("Table.focusCellBackground") );
      }
    } else {
      if (value instanceof CellBorder) {
        Border border = ((CellBorder)value).getBorder();
        setBorder(border);
      } else {
        if (columnBorder != null) {
          setBorder(columnBorder);
        } else {
          setBorder(noFocusBorder);
        }
      }
    }
    setOpaque(true);
    setValue(value);
    return this;
  }

  protected void setValue(Object value) {
    setText((value == null) ? "" : value.toString());
    if (useSetAlign) {
      setAlign(align);
    }
  }

  protected void setAlign(int val) {
    if (val == 1) {
      setHorizontalAlignment(JLabel.RIGHT);                //aligning everything right
    }else if (val == 2) {
      setHorizontalAlignment(JLabel.LEFT);                //aligning everything left
    }else {
      setHorizontalAlignment(JLabel.CENTER);                //aligning everything center
    }
  }

  public void setColumnBorder(Border border) {
    columnBorder = border;
  }

  public Border getColumnBorder() {
    return columnBorder;
  }
}


/*
  public Component getTableCellRendererComponent(JTable table, Object value,
                 boolean isSelected, boolean hasFocus, int row, int column) {
    Color foreground = null;
    Color background = null;
    Font font = null;

    boolean sel = false;
    int[] is = table.getSelectedRows();
    for(int i=0;i<is.length;i++){
      if(is[i] == row) {
        sel = true;
        break;
      }
    }

    TableModel model = table.getModel();
    if (model instanceof AttributiveCellTableModel) {
      CellAttribute cellAtt = ((AttributiveCellTableModel)model).getCellAttribute();
      if (cellAtt instanceof ColoredCell) {
        foreground = ((ColoredCell)cellAtt).getForeground(row,column);
        background = ((ColoredCell)cellAtt).getBackground(row,column);
      }
      if (cellAtt instanceof CellFont) {
        font = ((CellFont)cellAtt).getFont(row,column);
      }
    }

    if (sel) {
    //if (isSelected) {
      setForeground((foreground != null) ? foreground
                          : table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground((foreground != null) ? foreground
                          : table.getForeground());
      setBackground((background != null) ? background
                          : table.getBackground());
    }
    setFont((font != null) ? font : table.getFont());

    if (hasFocus) {
      setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
      if (table.isCellEditable(row, column)) {
        setForeground((foreground != null) ? foreground
                      : UIManager.getColor("Table.focusCellForeground") );
        setBackground( UIManager.getColor("Table.focusCellBackground") );
      }
    } else {
      if (value instanceof CellBorder) {
        Border border = ((CellBorder)value).getBorder();
        setBorder(border);
      } else {
        if (columnBorder != null) {
          setBorder(columnBorder);
        } else {
          setBorder(noFocusBorder);
        }
      }
    }
    //setOpaque(true);
    setValue(value);
    return this;
  }
*/
