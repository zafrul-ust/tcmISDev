package radian.tcmis.both1.helpers;

import java.util.*;
import javax.swing.table.*;


public class DbTableModel extends DefaultTableModel {

       int[] width = {0};
       int[] type = {0};
       int editableFlag  = 0;  // to be used to find if a column is editable
       Vector cellEditable = null; // to be used for cell editable flag

       public DbTableModel(){
              super();
       }

       public DbTableModel(String[] cols){
              super(cols,0);
       }

       public DbTableModel(String[] cols,Object[][]rows){
              super((Object[][])rows,(Object[])cols);
       }

       public void setEditableFlag(int b){
              this.editableFlag = b;
       }

       public void setCellEditableFlag(Vector h){
              this.cellEditable = h;
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

              if (this.cellEditable == null) return flag;

              /* for stand alone cells */
              String key = new String((new Integer(r)).toString()+(new Integer(c)).toString());
              if (this.cellEditable.contains(key)){
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
        public String getColumnTypesString(){
          String s = "";
          for (int i = 0; i < type.length; i++) {
            s = s+ String.valueOf(type[i]);
          }
          return s;
        }

}

