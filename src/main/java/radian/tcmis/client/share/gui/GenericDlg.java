

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

public class GenericDlg extends Component{
  JDialog dialog = null;
  String  message = null;
  JOptionPane pane = null;
  String title = "Error!";
  Frame frame = null;

  public static void showAccessDenied(Main main){
    String no = "Your connection has been dropped please try again,\n"+
                "tcmIS will reconnect if your access is re-established.\n"+
                "If this persists you should try to restart tcmIS.\n"+
                "If you continue to see this error after restarting tcmIS,\n"+
                "please send a message to the tcmIS support staff at tcmis@tcmis.com";
    JOptionPane pane = new JOptionPane(no,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION);
    JDialog dialog = pane.createDialog(main, "Error!");
    dialog.setVisible(true);
  }

  public void showMessage(Main main, String message){
    JOptionPane pane = new JOptionPane(message,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION);
    JDialog dialog = pane.createDialog(main,title);
    dialog.setVisible(true);
  }

  public static void showMessage(String message){
    JOptionPane pane = new JOptionPane(message,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION);
    JDialog dialog = pane.createDialog(new JFrame(), " ");
    dialog.setVisible(true);
  }

  public GenericDlg(Frame frame, String title, boolean modal) {
    this.title = title;
    this.frame = frame;
  }

  public GenericDlg(Frame frame) {
    this(frame, "", false);
  }

  public GenericDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public GenericDlg(Frame frame, String title) {
    this(frame, title, false);
  }

   public void setMsg(String S) {
     message = S;
     pane = new JOptionPane(message,JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION);
     dialog = pane.createDialog(frame, title);
  }

  public void setVisible(boolean b) {
    dialog.setVisible(true);
  }

  public void show(){
    this.setVisible(true);
  }

  public void setTitle(String s){
    this.title=s;
  }

}


























