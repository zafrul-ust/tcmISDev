

//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Your Name
//Company:      Your Company
//Description:  Your description


package radian.tcmis.client.share.gui;


import java.awt.*;
import javax.swing.*;
import java.awt.Component;

public class  CursorChange {

       public static void setCursor(Container C, int cursorType){
          Component[] comps = C.getComponents();
          Cursor cursor = new Cursor(cursorType);

          C.setCursor(cursor);

          if (C instanceof JDialog) CursorChange.setCursor(((JDialog) C).getContentPane(),cursorType);
          else if (C instanceof JFrame) CursorChange.setCursor(((JFrame) C).getContentPane(),cursorType);
          else if (C instanceof JInternalFrame) CursorChange.setCursor(((JInternalFrame) C),cursorType);

          for (int i =0;i<comps.length;i++) {
              comps[i].setCursor(cursor);
              if (comps[i] instanceof Container){
                  CursorChange.setCursor((Container) comps[i],cursorType);
              }
          }
          return;
       }

}
