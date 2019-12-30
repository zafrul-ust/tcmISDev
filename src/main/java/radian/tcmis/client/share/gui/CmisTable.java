
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;

import java.util.Vector;
import java.awt.*;
import radian.tcmis.client.share.gui.Main;
import radian.tcmis.client.share.helpers.*;
import javax.swing.*;
import javax.swing.table.*;


public class CmisTable extends JTable{

       Main parent;
       String colTypeFlag;

       public CmisTable(){
         super();
       }
       public CmisTable(int i, int j){
         super(i,j);
       }
       public CmisTable(Object[][] o1, Object[] o2){
         super(o1,o2);
       }
       public CmisTable(TableModel t){
         super(t);
       }
       public CmisTable(TableModel t,Main p){
         super(t);
         parent = p;
       }
       public CmisTable(TableModel t, TableColumnModel tc){
         super(t,tc);
       }
       public CmisTable(TableModel t, TableColumnModel tc, ListSelectionModel l){
         super(t,tc,l);
       }
       public CmisTable(Vector v, Vector v1){
         super(v,v1);
       }

       public CmisTable(TableSorter T){
         super(T);
       }

    public void setColTypeFlag(long flag) {
      colTypeFlag = String.valueOf(flag);
    }
    public void setColTypeFlag(String flag) {
      colTypeFlag = flag;
    }

    public void setAlign() {
      if(colTypeFlag == null) return;
      DefaultTableCellRenderer DCR = new DefaultTableCellRenderer();
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
          DCR.setHorizontalAlignment(JLabel.RIGHT);
        }
        if (TCol.getClass().getName().equalsIgnoreCase("String")  ||
            TCol.getClass().getName().equalsIgnoreCase("Integer") ||
            TCol.getClass().getName().equalsIgnoreCase("Double") ){
           TCol.setCellRenderer(DCR);
        }
        l++;
      }
  }


  /*public void setAlign() {
     javax.swing.table.DefaultTableCellRenderer DCR;
     TableColumn TCol;
     long i = colTypeFlag;
     int j;
     long jj;
     int k = this.getModel().getColumnCount();
     int l = 0;
     while (i > 0) {
       jj = (long) Math.pow(10,(k-1));
       j = (int) (i / (Math.pow(10,(k-1))));
       if (j == 2) {
         String S = this.getColumnName(l);
         DCR = new javax.swing.table.DefaultTableCellRenderer();
         DCR.setHorizontalAlignment(JLabel.RIGHT);
         TCol = this.getColumn(S);
         if (TCol.getClass().getName().equalsIgnoreCase("String")) TCol.setCellRenderer(DCR);
       }
       i = i - (jj * j);
       k--;
       l++;
     }
  }*/
}






























