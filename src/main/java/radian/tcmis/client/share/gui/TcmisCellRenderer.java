package radian.tcmis.client.share.gui;

import java.awt.*;


//import com.borland.jbcl.view.*;

import javax.swing.*;
import javax.swing.table.*;
import java.text.*;


public class TcmisCellRenderer extends DefaultTableCellRenderer{
  public static final Color DEFAULT_SELECTED_COLOR = new Color(204,204,255);


  public void normalFontFormat() {
    this.setFont(new Font(this.getFont().getName(),this.getFont().PLAIN, this.getFont().getSize()));
  }

  public void selectedFormat(boolean b){
    if(b) {
      setForeground(Color.black);
      setBackground(DEFAULT_SELECTED_COLOR);
    }else{
      setForeground(Color.black);
      setBackground(Color.white);
    }
  }

  public void currencyFormat(Object o) {
    String s;
    if(o instanceof String){
      s = (String) o;
      try{
        NumberFormat.getInstance().parse(s);
      }catch(Exception e) {
        setValue(s);
        setHorizontalAlignment(2);
        setHorizontalTextPosition(2);
        return;
      }
    }else if(o instanceof Double){
      s = ((Double)o).toString();
    }else if(o instanceof Integer){
      s = ((Integer)o).toString();
    }else if(o == null){
     s = "";
    }else{
      s = o.toString();
      try{
        NumberFormat.getInstance().parse(s);
      }catch(Exception e) {
        setValue(s);
        setHorizontalAlignment(2);
        setHorizontalTextPosition(2);
        return;
      }
    }
    int dot = s.indexOf(".");
    if(dot < 0) {
      if(s.length() < 1) {
        s = "";
      }else{
        s = s + ".00";
      }
    }else if(dot == 0) {
      s = "0" + s + "00";
    }else{
      s = s+"00";
    }
    if(s.length() > 0) {
      s = s.substring(0,s.indexOf(".")+3);
    }
    setValue(s);
    setHorizontalAlignment(4);
    setHorizontalTextPosition(4);
  }

  public void checkBoxFormat(Object o) {
    setOpaque(true);
    setHorizontalAlignment(JLabel.CENTER);
  }

}
