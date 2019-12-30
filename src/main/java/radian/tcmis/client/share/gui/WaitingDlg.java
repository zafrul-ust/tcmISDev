
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;


public class WaitingDlg extends JDialog {
  JPanel panel1 = new JPanel();
  javax.swing.JProgressBar jpb = new javax.swing.JProgressBar();
  javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
  javax.swing.JLabel msg = new javax.swing.JLabel();
  XYLayout xYLayout1 = new XYLayout();

  String msgText = "";
  WaitingDlgPBarThread myThread;
  
  public WaitingDlg(Frame frame, String title,String msg) {
    super(frame, title, true);
    msgText = msg;
    try  {
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    panel1.setLayout(xYLayout1);
    panel1.setMaximumSize(new Dimension(300, 160));
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText("Please Wait");
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    xYLayout1.setHeight(160);
    xYLayout1.setWidth(300);
    msg.setHorizontalTextPosition(SwingConstants.CENTER);
    msg.setText("Loading Data");
    if(msgText.length() > 0){
      msg.setText(msgText);
    }
    msg.setHorizontalAlignment(SwingConstants.CENTER);
    getContentPane().add(panel1);
    jpb.setMinimum(0);
    jpb.setMaximum(100);
    jpb.setForeground(Color.darkGray);

    panel1.add(jpb, new XYConstraints(35, 99, 230, 24));
    panel1.add(jLabel1, new XYConstraints(0, 16, 300, 22));
    panel1.add(msg, new XYConstraints(0, 50, 300, -1));
  }
  public void setVisible(boolean b){
    if(b){
      System.out.println("going startJPB");
      startJPB();
    }else{
      stopJPB();
    }
    super.setVisible(b);
  }

  void startJPB(){
    myThread = new WaitingDlgPBarThread(this);
    myThread.start();
  }
  void stopJPB(){
    myThread.stop();
  }
  public void sweep(){
    while(true){
      for(int i=0;i<jpb.getMaximum();i++){
        jpb.setValue(i);
        jpb.repaint();
        try{
          Thread.sleep(300);
        }catch(Exception e){}
      }
      for(int i=100;i>jpb.getMinimum();i--){
        jpb.setValue(i);
        jpb.repaint();
        try{
          Thread.sleep(300);
        }catch(Exception e){}
      }
    }
  }
}

class WaitingDlgPBarThread extends Thread{
  WaitingDlg wd;
  public WaitingDlgPBarThread(WaitingDlg wd){
    this.wd = wd;
  }
  public void run(){
    wd.sweep();
  }
}

 
