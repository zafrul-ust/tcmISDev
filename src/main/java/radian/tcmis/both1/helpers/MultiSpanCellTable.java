package radian.tcmis.both1.helpers;

import java.util.*;
//import java.awt.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

/**
 * @version 1.0 11/26/98
 */

import radian.tcmis.client.share.helpers.TableSorter;

import java.awt.event.*;

public class MultiSpanCellTable extends JTable {
  String colTypeFlag;
  AttributiveCellTableModel m;

  public MultiSpanCellTable(TableModel model) {
    super(model);
    m = (AttributiveCellTableModel)model;
    setUI(new MultiSpanCellTableUI());
    getTableHeader().setReorderingAllowed(false);
    setCellSelectionEnabled(true);
    setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
  }

  public void editingStopped() {
    //System.out.println("--------- here i am in multispancelltable");
    ((JTable)this).getCellEditor().stopCellEditing();
  }

  public void addMouseListenerToHeaderInTable(JTable table, TableSorter sort) {
      //System.out.println("--------- here in addmouselistener multicellspantable");
        final TableSorter sorter = sort;
        final JTable tableView = table;
        tableView.setColumnSelectionAllowed(false);
        //System.out.println("Passing thru sorting");
        MouseAdapter listMouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //System.out.println("passed thru mouseClicked on multicellspantable");
                tableView.editingStopped(new ChangeEvent(tableView));   //6-21-01 stop editing before sorting table
                TableColumnModel columnModel = tableView.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int column = tableView.convertColumnIndexToModel(viewColumn);
                if(e.getClickCount() == 1 && column != -1) {
                    //System.out.println("Passing thru sorting 222");
                    int shiftPressed = e.getModifiers()&InputEvent.SHIFT_MASK;
                    boolean ascending = (shiftPressed == 0);
                    sorter.sortByColumn(column, ascending);
                }
             }
         };
        JTableHeader th = tableView.getTableHeader();
        th.addMouseListener(listMouseListener);
        //System.out.println("addmouselistener done");
    }

    /*
    public void addMouseListenerToHeaderInTable(JTable table) {
      System.out.println("--------- here in addmouselistener multicellspantable");
        final TableSorterNew sorter = new TableSorterNew(m);
        final JTable tableView = table;
        tableView.setColumnSelectionAllowed(false);
        //System.out.println("Passing thru sorting");
        MouseAdapter listMouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("passed thru mouseClicked on multicellspantable");
                tableView.editingStopped(new ChangeEvent(tableView));   //6-21-01 stop editing before sorting table
                TableColumnModel columnModel = tableView.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int column = tableView.convertColumnIndexToModel(viewColumn);
                if(e.getClickCount() == 1 && column != -1) {
                    System.out.println("Passing thru sorting 222");
                    int shiftPressed = e.getModifiers()&InputEvent.SHIFT_MASK;
                    boolean ascending = (shiftPressed == 0);
                    //((AttributiveCellTableModel)tableView.getModel()).sortByColumn(column, ascending);
                    sorter.sortByColumn(column, ascending);
                }
             }
         };
        JTableHeader th = tableView.getTableHeader();
        th.addMouseListener(listMouseListener);
        System.out.println("addmouselistener done");
    }   */



  public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
    Rectangle sRect = super.getCellRect(row,column,includeSpacing);
    if ((row <0) || (column<0) ||
        (getRowCount() <= row) || (getColumnCount() <= column)) {
        return sRect;
    }
    CellSpan cellAtt = (CellSpan)((AttributiveCellTableModel)getModel()).getCellAttribute();
    if (! cellAtt.isVisible(row,column)) {
      int temp_row    = row;
      int temp_column = column;
      row    += cellAtt.getSpan(temp_row,temp_column)[CellSpan.ROW];
      column += cellAtt.getSpan(temp_row,temp_column)[CellSpan.COLUMN];
    }
    int[] n = cellAtt.getSpan(row,column);

    int index = 0;
    int columnMargin = getColumnModel().getColumnMargin();
    Rectangle cellFrame = new Rectangle();
    int aCellHeight = rowHeight + rowMargin;
    cellFrame.y = row * aCellHeight;
    cellFrame.height = n[CellSpan.ROW] * aCellHeight;

    Enumeration enumeration = getColumnModel().getColumns();
    while (enumeration.hasMoreElements()) {
      TableColumn aColumn = (TableColumn)enumeration.nextElement();
      cellFrame.width = aColumn.getWidth() + columnMargin;
      if (index == column) break;
      cellFrame.x += cellFrame.width;
      index++;
    }
    for (int i=0;i< n[CellSpan.COLUMN]-1;i++) {
      TableColumn aColumn = (TableColumn)enumeration.nextElement();
      cellFrame.width += aColumn.getWidth() + columnMargin;
    }



    if (!includeSpacing) {
      Dimension spacing = getIntercellSpacing();
      cellFrame.setBounds(cellFrame.x +      spacing.width/2,
                          cellFrame.y +      spacing.height/2,
                          cellFrame.width -  spacing.width,
                          cellFrame.height - spacing.height);
    }
    return cellFrame;
  }


  private int[] rowColumnAtPoint(Point point) {
    int[] retValue = {-1,-1};
    int row = point.y / (rowHeight + rowMargin);
    if ((row <0)||(getRowCount() <= row)) return retValue;
    int column = getColumnModel().getColumnIndexAtX(point.x);

    CellSpan cellAtt = (CellSpan)((AttributiveCellTableModel)getModel()).getCellAttribute();

    if (cellAtt.isVisible(row,column)) {
      retValue[CellSpan.COLUMN] = column;
      retValue[CellSpan.ROW   ] = row;
      return retValue;
    }
    retValue[CellSpan.COLUMN] = column + cellAtt.getSpan(row,column)[CellSpan.COLUMN];
    retValue[CellSpan.ROW   ] = row + cellAtt.getSpan(row,column)[CellSpan.ROW];
    return retValue;
  }


  public int rowAtPoint(Point point) {
    return rowColumnAtPoint(point)[CellSpan.ROW];
  }
  public int columnAtPoint(Point point) {
    return rowColumnAtPoint(point)[CellSpan.COLUMN];
  }



  public void columnSelectionChanged(ListSelectionEvent e) {
    repaint();
  }

  public void valueChanged(ListSelectionEvent e) {
    int firstIndex = e.getFirstIndex();
    int  lastIndex = e.getLastIndex();
    if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
      repaint();
    }
    Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
    int numCoumns = getColumnCount();
    int index = firstIndex;
    for (int i=0;i<numCoumns;i++) {
      dirtyRegion.add(getCellRect(index, i, false));
    }
    index = lastIndex;
    for (int i=0;i<numCoumns;i++) {
      dirtyRegion.add(getCellRect(index, i, false));
    }
    repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
  }

  public void setColTypeFlag(long flag) {
      colTypeFlag = String.valueOf(flag);
    }
    public void setColTypeFlag(String flag) {
      colTypeFlag = flag;
    }

    public void setAlign() {
      if(colTypeFlag == null) return;
      AttributiveCellRenderer DCR = new AttributiveCellRenderer();
      //this doesn't seeem to work either -- DefaultTableCellRenderer DCR = new DefaultTableCellRenderer();
      TableColumn TCol;
      String flag = colTypeFlag;
      Integer one = new Integer(1);
      Integer i;
      String s = "";
      int l = 0;
      while(flag.length() > 0) {
        if(flag.length() > 1) {
          s = flag.substring(0,1);
          flag = flag.substring(1);
        }else{
          s = flag;
          flag = "";
        }
        try{
          i = new Integer(s);
        }catch(Exception e) {
          i = one;
        }

        JLabel la = new JLabel();
        Color b = la.getForeground();
        DCR.setForeground(b);

        //right here, I is the col type (Integer)
        String S = this.getColumnName(l);
        TCol = this.getColumn(S);
        if (i.intValue() == 2) {
          //DCR.setHorizontalAlignment(JLabel.RIGHT);
          DCR.setHorizontalAlignment(JLabel.CENTER);
        }
        if (TCol.getClass().getName().equalsIgnoreCase("String")  ||
            TCol.getClass().getName().equalsIgnoreCase("Integer") ||
            TCol.getClass().getName().equalsIgnoreCase("Double") ){
           TCol.setCellRenderer(DCR);
        }
        l++;
      }
  }

}

