
//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;


import javax.swing.table.*;
import java.util.*;

public class CmisTableModel extends DefaultTableModel {

       int editableFlag  = 0;  // to be used to find if a column is editable
       Vector cellNotEditable = null; // to be used for cell editable flag

       public CmisTableModel(){
              super();
       }

       public CmisTableModel(String[] cols){
              super(cols,0);
       }

       public CmisTableModel(String[] cols,Object[][]rows){
              super((Object[][])rows,(Object[])cols);
       }

       public CmisTableModel(Vector p0, Vector p1) {
              super(p0,p1);
       }

       public void setEditableFlag(int b){
              this.editableFlag = b;
       }

       public void setCellEditableFlag(Vector h){
              this.cellNotEditable = h;
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

       /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            if (getValueAt(0, c)==null) return (new String()).getClass();
            return getValueAt(0, c).getClass();
        }

        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue,row,column);
        }


}





























