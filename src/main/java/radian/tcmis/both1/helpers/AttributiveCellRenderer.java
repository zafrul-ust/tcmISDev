package radian.tcmis.both1.helpers;

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
public class AttributiveCellRenderer extends JLabel
    implements TableCellRenderer {
  protected static Border noFocusBorder;
  protected Border columnBorder;
  protected boolean useSetAlign = false;
  protected int align;

  public AttributiveCellRenderer() {
    noFocusBorder = new EmptyBorder(1, 2, 1, 2);
    setOpaque(true);
    setBorder(noFocusBorder);
  }

  public AttributiveCellRenderer(int align) {
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

    //if (sel) {  //this will paint the whole line and I don't want to do this with the detail view
    if (isSelected) {
      setForeground((foreground != null) ? foreground
                          : table.getSelectionForeground());
      //setBackground(table.getSelectionBackground());  new Color(224,226,250)
      setBackground(new Color(150,150,255));
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

