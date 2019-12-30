package radian.tcmis.client.share.gui.spanCellTable;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.0 11/26/98
 */
public class SpanCellTable extends JFrame {
  SpanCellTable() {
    super( "Multi-Span Cell Example" );

    /*
    Vector colName = new Vector();
    for (int i = 0; i < 5; i++) {
      colName.addElement("COL_"+i);
    }
    Vector dataV = new Vector();
    for (int j = 0; j < 10; j++) {
      dataV.addElement("DATA_"+j);
    }
    AttributiveCellTableModel ml = new AttributiveCellTableModel(dataV,colName);
    */
    //AttributiveCellTableModel ml = new AttributiveCellTableModel(10,6);
    AttributiveCellTableModel ml = new AttributiveCellTableModel(10,6) {
      public Object getValueAt(int row, int col) {
        return "" + row + ","+ col;
      }
    };
    
    final CellSpan cellAtt =(CellSpan)ml.getCellAttribute();
    final MultiSpanCellTable table = new MultiSpanCellTable( ml );
    JScrollPane scroll = new JScrollPane( table );

    JButton b_one   = new JButton("Combine");
    b_one.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int[] columns = table.getSelectedColumns();
        int[] rows    = table.getSelectedRows();
        cellAtt.combine(rows,columns);
        table.clearSelection();
        table.revalidate();
        table.repaint();
      }
    });
    JButton b_split = new JButton("Print");
    b_split.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Here it is: "+table.getValueAt(table.getSelectedRow(),table.getSelectedColumn()));
      }
    });/*
    JButton b_split = new JButton("Split");
    b_split.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int column = table.getSelectedColumn();
        int row    = table.getSelectedRow();
        cellAtt.split(row,column);
        table.clearSelection();
        table.revalidate();
        table.repaint();
      }
    }); */
    JPanel p_buttons = new JPanel();
    p_buttons.setLayout(new GridLayout(2,1));
    p_buttons.add(b_one);
    p_buttons.add(b_split);

    Box box = new Box(BoxLayout.X_AXIS);
    box.add(scroll);
    box.add(new JSeparator(SwingConstants.HORIZONTAL));
    box.add(p_buttons);
    getContentPane().add( box );
    setSize( 400, 200 );
    setVisible(true);
  }

  public static void main(String[] args) {
    SpanCellTable frame = new SpanCellTable();
    frame.addWindowListener( new WindowAdapter() {
      public void windowClosing( WindowEvent e ) {
        System.exit(0);
      }
    });
  }

}


