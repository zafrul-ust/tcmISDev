package radian.tcmis.both1.helpers;

import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.Dimension;


/**
 * @version 1.0 11/22/98
 */

public class AttributiveCellTableModel extends DefaultTableModel {

  protected CellAttribute cellAtt;
  int editableFlag  = 0;
  Vector cellNotEditable = null;
  int[] width = {0};
  int[] type = {0};

  //8-01-01
  int[] indexes;
  TableSorterNew sorter = null;
  public int[] getIndexes() {
    int n = getRowCount();
    if (indexes != null) {
      if (indexes.length == n) {
        return indexes;
      }
    }
    indexes = new int[n];
    for (int i=0; i<n; i++) {
      indexes[i] = i;
    }
    return indexes;
  }

  public AttributiveCellTableModel() {
    this((Vector)null, 0);
  }
  public AttributiveCellTableModel(int numRows, int numColumns) {
    Vector names = new Vector(numColumns);
    names.setSize(numColumns);
    setColumnIdentifiers(names);
    dataVector = new Vector();
    setNumRows(numRows);
    cellAtt = new DefaultCellAttribute(numRows,numColumns);
  }
  public AttributiveCellTableModel(Vector columnNames, int numRows) {
    setColumnIdentifiers(columnNames);
    dataVector = new Vector();
    setNumRows(numRows);
    cellAtt = new DefaultCellAttribute(numRows,columnNames.size());
  }

  public AttributiveCellTableModel(Object[] columnNames, int numRows) {
    this(convertToVector(columnNames), numRows);
  }

  public AttributiveCellTableModel(Vector data, Vector columnNames) {
    setDataVector(data, columnNames);
  }
  public AttributiveCellTableModel(Object[][] data, Object[] columnNames) {
    setDataVector(data, columnNames);
  }

  public void setDataVector(Vector newData, Vector columnNames) {
    super.setDataVector(newData,columnNames);
    /*
    if (newData == null)
      throw new IllegalArgumentException("setDataVector() - Null parameter");
    setColumnIdentifiers(columnNames);
    dataVector = new Vector(0);
    dataVector = newData;
    */
    cellAtt = new DefaultCellAttribute(dataVector.size(),columnIdentifiers.size());
    newRowsAdded(new TableModelEvent(this, 0, getRowCount()-1,TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }

  public void addColumn(Object columnName, Vector columnData) {
    if (columnName == null)
      throw new IllegalArgumentException("addColumn() - null parameter");
    columnIdentifiers.addElement(columnName);
    int index = 0;
    Enumeration enumeration = dataVector.elements();
    while (enumeration.hasMoreElements()) {
      Object value;
      if ((columnData != null) && (index < columnData.size()))
          value = columnData.elementAt(index);
      else
        value = null;
      ((Vector)enumeration.nextElement()).addElement(value);
      index++;
    }

    //
    cellAtt.addColumn();

    fireTableStructureChanged();
  }

  //6-20-01
  public void setEditableFlag(int b) {
    this.editableFlag = b;
  }

  public void removeCellNotEditable(String cell) {
    if (cellNotEditable != null) {
      if (cellNotEditable.contains(cell)) {
        cellNotEditable.removeElement(cell);
      }
    }
  }

  public void addCellNotEditable(String cell) {
    if (cellNotEditable == null) {
      cellNotEditable = new Vector(23);
    }
    if (!cellNotEditable.contains(cell)) {
      cellNotEditable.add(cell);
    }
  }
  /* power 2 to set the flag, i.e. :
          0 = none editable
          2 = cell 0
          6 = cell 0 and 1
          10 = cell 0 and 2
          etc..
       */
  public boolean isCellEditable(int r, int c){
              double p=0;  // power
              double bin = this.editableFlag; // flag for combinations
              boolean flag = false;
              int colNum = getColumnCount();
              for(int i=colNum-1;i>=0;i--){
                      p = Math.pow(2,i);
                      if (bin>=p){
                        flag = true;
                        bin = bin - p;
                      } else {
                        flag = false;
                      }
                      if (i==c) break;
              }
              if (this.cellNotEditable == null) return flag;

              /* for stand alone cells */
              String key = new String((new Integer(r)).toString()+(new Integer(c)).toString());
              if (this.cellNotEditable.contains(key)){
                   flag = false;
              }
              return flag;
       }

    public void setColumnPrefWidth(int[] width){
      this.width = width;
    }
    public int getColumnWidth(int col) {
      return width[col];
    }
    public void setColumnType(int[] type){
      this.type = type;
    }
    public int[] getColumnTypes(){
      return type;
    }


  /*
  public void addRow(Vector rowData) {
    Vector newData = null;
    if (rowData == null) {
      newData = new Vector(getColumnCount());
    }
    else {
      rowData.setSize(getColumnCount());
    }
    dataVector.addElement(newData);

    // Still can't figure out how to do this the right way
    //t[] sp = {1,1};
    //cellAtt.setSpan(sp,getRowCount(),getColumnCount());
    cellAtt.addRow();

    newRowsAdded(new TableModelEvent(this, getRowCount()-1, getRowCount()-1,
       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }

  public void insertRow(int row, Vector rowData) {
    if (rowData == null) {
      rowData = new Vector(getColumnCount());
    }
    else {
      rowData.setSize(getColumnCount());
    }

    dataVector.insertElementAt(rowData, row);

    ///till can't figure out how to do this the right way
    //cellAtt.setSpan();
    cellAtt.insertRow(row);

    newRowsAdded(new TableModelEvent(this, row, row,
       TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
  }  */

  public CellAttribute getCellAttribute() {
    return cellAtt;
  }

  public void setCellAttribute(CellAttribute newCellAtt) {
    int numColumns = getColumnCount();
    int numRows    = getRowCount();
    if ((newCellAtt.getSize().width  != numColumns) ||
        (newCellAtt.getSize().height != numRows)) {
      newCellAtt.setSize(new Dimension(numRows, numColumns));
    }
    cellAtt = newCellAtt;
    fireTableDataChanged();
  }

  /*
  public void changeCellAttribute(int row, int column, Object command) {
    cellAtt.changeAttribute(row, column, command);
  }

  public void changeCellAttribute(int[] rows, int[] columns, Object command) {
    cellAtt.changeAttribute(rows, columns, command);
  }
  */

}


