
//Title:        TCM - CMIS
//Version:      
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components


package radian.tcmis.client.share.gui;

import javax.swing.*;
import java.awt.event.*;


public class TableOrganizer {

  JTable jTable;

  public TableOrganizer() {

  }

  public TableOrganizer(JTable t) {
    jTable = t;
    jTable.getTableHeader().setReorderingAllowed(true);
    jTable.getTableHeader().setResizingAllowed(true);
    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //jTable.setCellSelectionEnabled(false);
    jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //addMouseListenerRowSelection();
  }

  public void setJTable(JTable t){
    jTable = t;
  }

  public JTable getJTable(){
     return jTable;
  }

  public void addMouseListenerRowSelection() {
        final JTable tableView = jTable;
        MouseAdapter listRowSelectListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

             }
         };
        //TableUI tUi = tableView.getUI();
        jTable.addMouseListener(listRowSelectListener);
    }
}




























