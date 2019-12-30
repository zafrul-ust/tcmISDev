
//Title:        Helpers
//Version:      
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian
//Description:  Classes to help development


package radian.tcmis.client.share.helpers;


/*
 * @(#)TableSorter.java	1.5 97/12/17
 *
 * Copyright (c) 1997 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */

/**
 * A sorter for TableModels. The sorter has a model (conforming to TableModel) 
 * and itself implements TableModel. TableSorter does not store or copy 
 * the data in the TableModel, instead it maintains an array of 
 * integers which it keeps the same size as the number of rows in its 
 * model. When the model changes it notifies the sorter that something 
 * has changed eg. "rowsAdded" so that its internal array of integers 
 * can be reallocated. As requests are made of the sorter (like 
 * getValueAt(row, col) it redirects them to its model via the mapping 
 * array. That way the TableSorter appears to hold another copy of the table 
 * with the rows in a different order. The sorting algorthm used is stable 
 * which means that it does not move around rows when its comparison 
 * function returns 0 to denote that they are equivalent. 
 *
 * @version 1.5 12/17/97
 * @author Philip Milne
 */

import java.util.*;
import java.text.SimpleDateFormat;

import javax.swing.*;


// Imports for picking up mouse events from the JTable. 

import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class TableSorter extends TableMap
{
    int             indexes[];
    Vector          sortingColumns = new Vector();
    boolean         ascending = true;
    int compares;
    Hashtable colTypes = new Hashtable();
    TableModel T;
    boolean kits = false;
    int[] sameCol = {0};

    //CellAttribute cellAtt;       //8-01


    public TableSorter()
    {
        indexes = new int[0]; // For consistency.
    }

    public TableSorter(TableModel model)
    {
        setModel(model);
        //cellAtt = (CellAttribute)((AttributiveCellTableModel)getModel()).getCellAttribute();           //8-01
    }
    /*
    public CellAttribute getCellAttribute() {
      return cellAtt;
    }  */

    public void setModel(TableModel model) {
        //System.out.println("passed thru setModel on sorter");
        super.setModel(model);
        T = model;
        reallocateIndexes();
    }

    public void setColTypeFlag(long flag) {
         long i = flag;
         int j;
         long jj;
         int k = T.getColumnCount();
         int l = 0;
         while (i > 0) {
           jj = (long) Math.pow(10,(k-1));
           j = (int) (i / (Math.pow(10,(k-1))));
           colTypes.put(new Integer(l),new Integer(j));
           i = i - (jj * j);
           k--;
           l++;
         }
       }

    public void setColTypeFlag(String flag) {
      Integer i;
      int x = 0;
      String s = "";
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
          i = new Integer(1);
        }
        colTypes.put(new Integer(x++),i);
      }
    }

    public int compareRowsByColumn(int row1, int row2, int column)
    {
//        Class type = model.getColumnClass(column);
        int c = ((Integer) colTypes.get(new Integer(column))).intValue();
        TableModel data = T;

        // Check for nulls

        Object o1 = data.getValueAt(row1, column);
        Object o2 = data.getValueAt(row2, column); 

        // If both values are null return 0
        if (o1 == null && o2 == null) {
            return 0; 
        }
        /*else if (o1 == null) { // Define null less than everything.
            return -1;
        }
        else if (o2 == null) {
            return 1;
        } */
        else if (o1 == null) { // Define null greater than everything.
            return 1;
        }
        else if (o2 == null) {
            return -1;
        }


/* We copy all returned values from the getValue call in case
an optimised model is reusing one object to return many values.
The Number subclasses in the JDK are immutable and so will not be used in
this way but other subclasses of Number might want to do this to save
space and avoid unnecessary heap allocation.
*/
//        if (type.getSuperclass() == java.lang.Number.class)
        switch(c) {
            case 2:
                Number n21 = (Number)data.getValueAt(row1, column);
                double d21 = n21.doubleValue();
                Number n22 = (Number)data.getValueAt(row2, column);
                double d22 = n22.doubleValue();

                if (d21 < d22)
                    return -1;
                else if (d21 > d22)
                    return 1;
                else
                    return 0;
            case 3:
                String first = data.getValueAt(row1, column).toString();
                String second = data.getValueAt(row2, column).toString();
                if(first.equals(second)){
                  return 0;
                }else if(first.length() < 1) {
                  return 1;
                }else if(second.length() < 1) {
                  return -1;
                }

                SimpleDateFormat S = new SimpleDateFormat("MMM dd, yyyy");
                try {
                    Date d31 = S.parse(data.getValueAt(row1, column).toString());
                    long n31 = d31.getTime();
                    Date d32 = S.parse(data.getValueAt(row2, column).toString());
                    long n32 = d32.getTime();

                    if (n31 < n32)
                        return -1;
                    else if (n31 > n32)
                        return 1;
                    else return 0;
                } catch (Exception e) {
                    return 0;
                }
            case 1:
                String s1 = data.getValueAt(row1, column).toString();
                String s2 = data.getValueAt(row2, column).toString();

                //System.out.println("-------- compareRowsByColumn string s1: "+s1+" s2: "+s2+" ascending: "+this.ascending);

                //this block ensurse that empty strings are
                //at the bottom of the column
                if(s1.length() < 1) return 1;
                if(s2.length() < 1) return -1;


                int result = s1.compareTo(s2);

                if (result < 0)
                    return -1;
                else if (result > 0)
                    return 1;
                else return 0;
            case 4:
                Boolean bool1 = (Boolean)data.getValueAt(row1, column);
                boolean b1 = bool1.booleanValue();
                Boolean bool2 = (Boolean)data.getValueAt(row2, column);
                boolean b2 = bool2.booleanValue();

                if (b1 == b2)
                    return 0;
                else if (b2) // Define false > true
                    return 1;
                else
                    return -1;
            case 5:  // JLabel String
                JLabel label1 = (JLabel) data.getValueAt(row1, column);
                String s11 = label1.getText();
                JLabel label2 = (JLabel) data.getValueAt(row2, column);
                String s12 = label2.getText();

                //this block ensurse that empty strings are
                //at the bottom of the column
                if(s11.length() < 1) return 1;
                if(s12.length() < 1) return -1;

                int result1 = s11.compareTo(s12);

                if (result1 < 0)
                    return -1;
                else if (result1 > 0)
                    return 1;
                else return 0;
            case 6:   // JLabel Number
                JLabel label21 = (JLabel) data.getValueAt(row1, column);
                Double d1 = new Double(label21.getText());
                JLabel label22 = (JLabel) data.getValueAt(row2, column);
                Double d2 = new Double(label22.getText());

                double d31 = d1.doubleValue();
                double d32 = d2.doubleValue();

                if (d31 < d32)
                    return -1;
                else if (d31 > d32)
                    return 1;
                else
                    return 0;
            case 7:  // JLabel date
                SimpleDateFormat S1 = new SimpleDateFormat("MMM dd, yyyy");
                JLabel label31 = (JLabel) data.getValueAt(row1, column);
                String s31 = label31.getText();
                JLabel label32 = (JLabel) data.getValueAt(row2, column);
                String s32 = label32.getText();
                try {
                    Date d41 = S1.parse(s31);
                    long n41 = d41.getTime();
                    Date d42 = S1.parse(s32);
                    long n42 = d42.getTime();

                    if (n41 < n42)
                        return -1;
                    else if (n41 > n42)
                        return 1;
                    else return 0;
                } catch (Exception e) {
                    return 0;
                }
            case 8:   //numeric String
                double ns1 = 0.00;
                try{
                  ns1 = Double.valueOf(data.getValueAt(row1, column).toString()).doubleValue();
                }catch(Exception e){}

                double ns2 = 0.00;
                try{
                   ns2 = Double.valueOf(data.getValueAt(row2, column).toString()).doubleValue();
                }catch(Exception e){}

                if (ns1 < ns2)
                    return -1;
                else if (ns1 > ns2)
                    return 1;
                else
                    return 0;
            case 9:   // Numeric String
                Double D1;
                Double D2;
                String SS1 = data.getValueAt(row1, column).toString();
                String SS2 = data.getValueAt(row2, column).toString();

                try{
                  D1 = new Double(SS1);
                }catch(Exception e){
                  D1 = new Double(-999.99);
                }
                try{
                  D2 = new Double(SS2);
                }catch(Exception e){
                  D2 = new Double(-999.99);
                }
                double dd31 = D1.doubleValue();
                double dd32 = D2.doubleValue();

                if (dd31 < dd32)
                    return -1;
                else if (dd31 > dd32)
                    return 1;
                else
                    return 0;
            default:
                Object v1 = data.getValueAt(row1, column);
                String sd1 = v1.toString();
                Object v2 = data.getValueAt(row2, column);
                String sd2 = v2.toString();
                int dresult = sd1.compareTo(sd2);

                if (dresult < 0)
                    return -1;
                else if (dresult > 0)
                    return 1;
                else return 0;
        }
    }

    public int compare(int row1, int row2){
      compares++;
      for(int level = 0; level < sortingColumns.size(); level++) {
        Integer column = (Integer)sortingColumns.elementAt(level);
        int result = compareRowsByColumn(row1, row2, column.intValue());
        if (result != 0)
        return ascending ? result : -result;
      }
      return 0;
    }

    public void  reallocateIndexes(){
      int rowCount = T.getRowCount();

      // I added this because reallocating indexes were being called after a
      // cell gets updated and it was reindexing all data. rfz 9/14/98
      if (indexes != null && indexes.length > 1) return;
      // Set up a new array of indexes with the right number of elements
      // for the new data model.
      indexes = new int[rowCount];

      // Initialise with the identity mapping.
      for(int row = 0; row < rowCount; row++)
          indexes[row] = row;
    }

    public void tableChanged(TableModelEvent e)
    {
        //System.out.println("passed thru tableChanged on sorter");
        reallocateIndexes();

        super.tableChanged(e);

        //to call back the client
        doTableAudits();
    }

    public void doTableAudits(){}
    
    public void checkModel()
    {
        if (indexes.length != T.getRowCount()) {
            //System.out.println("Sorter not informed of a change in model.");
        }
    }

     public void  sort(Object sender)
    //8-01-01public void  sort()
    {
        checkModel();

        compares = 0;
        // n2sort();
        // qsort(0, indexes.length-1);
        shuttlesort((int[])indexes.clone(), indexes, 0, indexes.length);
    }

    public void n2sort() {
        for(int i = 0; i < getRowCount(); i++) {
            for(int j = i+1; j < getRowCount(); j++) {
                if (compare(indexes[i], indexes[j]) == -1) {
                    swap(i, j);
                }
            }
        }
    }

    // This is a home-grown implementation which we have not had time
    // to research - it may perform poorly in some circumstances. It
    // requires twice the space of an in-place algorithm and makes
    // NlogN assigments shuttling the values between the two
    // arrays. The number of compares appears to vary between N-1 and
    // NlogN depending on the initial order but the main reason for
    // using it here is that, unlike qsort, it is stable.
    public void shuttlesort(int from[], int to[], int low, int high) {
      /*
      System.out.println("Here in shuttlesort high: "+high+ " low: "+low);
      for (int t = 0; t < indexes.length; t++) {
        System.out.println("to :"+to[t]);
        System.out.println("from :"+from[t]);
      } */

        if (high - low < 2) {
            return;
        }
        int middle = (low + high)/2;
        shuttlesort(to, from, low, middle);
        shuttlesort(to, from, middle, high);

        int p = low;
        int q = middle;

        /* This is an optional short-cut; at each recursive call,
        check to see if the elements in this subset are already
        ordered.  If so, no further comparisons are needed; the
        sub-array can just be copied.  The array must be copied rather
        than assigned otherwise sister calls in the recursion might
        get out of sinc.  When the number of elements is three they
        are partitioned so that the first set, [low, mid), has one
        element and and the second, [mid, high), has two. We skip the
        optimisation when the number of elements is three or less as
        the first compare in the normal merge will produce the same
        sequence of steps. This optimisation seems to be worthwhile
        for partially ordered lists but some analysis is needed to
        find out how the performance drops to Nlog(N) as the initial
        order diminishes - it may drop very quickly.  */

        if (high - low >= 4 && compare(from[middle-1], from[middle]) <= 0) {
            for (int i = low; i < high; i++) {
                to[i] = from[i];
            }
            return;
        }

        // A normal merge. 

        for(int i = low; i < high; i++) {
            if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
                to[i] = from[p++];
            }
            else {
                to[i] = from[q++];
            }
        }
         /*
        for (int l = 0; l < to.length; l++) {
          System.out.println("----- shuttlesort index: " +indexes[l]);
          Integer column = (Integer)sortingColumns.elementAt(l);
          System.out.println("----- shuttlesort index: "+T.getValueAt(l, column.intValue()));
        }     */
    }

    public void swap(int i, int j) {
        int tmp = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = tmp;
    }

    // The mapping only affects the contents of the data rows.
    // Pass all requests to these rows through the mapping array: "indexes".

    public Object getValueAt(int aRow, int aColumn)
    {
    //// // System.out.println("passed thru getValueAt on sorter");
        checkModel();
        return T.getValueAt(indexes[aRow], aColumn);
    }

    public void setValueAt(Object aValue, int aRow, int aColumn)
    {
    //// // System.out.println("passed thru setValueAt on sorter");
        checkModel();
        T.setValueAt(aValue, indexes[aRow], aColumn);
    }

    public void sortByColumn(int column) {
        sortByColumn(column, true);
        super.tableChanged(new TableModelEvent(this));
        // now organize kits
        // // System.out.println("kits:"+kits+"   col:"+sameCol);
        if (!kits) return;
        organizeKits(sameCol);
        super.tableChanged(new TableModelEvent(this));
    }

    public void sortByColumn(int column, boolean ascending) {
        this.ascending = ascending;
        sortingColumns.removeAllElements();
        sortingColumns.addElement(new Integer(column));
        //System.out.println("----- in sortbycolumn calling sort:");
        sort(this);
             /*
        for (int l = 0; l < indexes.length; l++) {
          System.out.println("----- sortByColumn index: " +indexes[l]);
          System.out.println("----- sortByColumn values: "+T.getValueAt(indexes[l], column));
        }      */

        //System.out.println("----- in sortbycolumn after sort called:");
        super.tableChanged(new TableModelEvent(this));

        //System.out.println("kits:"+kits+"   col:"+sameCol);
        if (!kits) return;
        organizeKits(sameCol);

        super.tableChanged(new TableModelEvent(this));
        //System.out.println("----- in sortbycolumn DONE:");
    }
    // There is no-where else to put this.
    // Add a mouse listener to the Table to trigger a table sort 
    // when a column heading is clicked in the JTable. 
    public void addMouseListenerToHeaderInTable(JTable table) {
        final TableSorter sorter = this;
        final JTable tableView = table;
        tableView.setColumnSelectionAllowed(false);
        //System.out.println("Passing thru sorting");
        MouseAdapter listMouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //System.out.println("passed thru mouseClicked on sorter");
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

    public void setKits(boolean flag,int[] colnum){
        kits = true;
        sameCol = colnum;
    }

    public void setKits(boolean flag){
        kits = false;
    }

    void organizeKits(int[] col){
        TableModel data = T;

        for (int i=0;i<indexes.length-1;i++){
           for( int j=0;j<indexes.length;j++){
              if (i < j){
                 boolean doIt = true;
                 for (int k=0;k<col.length;k++){
                   if(!this.getValueAt(i,col[k]).toString().equals(this.getValueAt(j,col[k]).toString())){
                     doIt = false;
                     break;
                   }
                 }
                 if (doIt) insertIndex(i+1,j);
              }
           }
        }

    }

    void insertIndex(int where,int from){
        // keep value
        int temp = indexes[from];
        // move everyone down
        for (int i=from;i>where;i--){
           indexes[i] = indexes[i-1];
        }
        indexes[where] = temp;
    }

}


























