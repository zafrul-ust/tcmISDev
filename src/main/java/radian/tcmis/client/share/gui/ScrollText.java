package radian.tcmis.client.share.gui;

import java.awt.*;
import javax.swing.*;



public class ScrollText extends JScrollPane{
  JTextArea jta = new JTextArea();

  public ScrollText() {
    super();
    setAll();

  }

  public ScrollText(Component p0) {
    super(p0);
    setAll();
  }

  public ScrollText(Component p0, int p1, int p2) {
    super(p0,p1,p2);
    setAll();
  }

  public ScrollText(int p0,int p1) {
    super(p0,p1);
    setAll();
  }

  protected void setAll(){
    jta = new JTextArea();
    getViewport().setView(jta);
    setOpaque(true);
    this.setHorizontalScrollBarPolicy(this.HORIZONTAL_SCROLLBAR_NEVER);
  }

  public void setText(String t){
    jta.setText(t);
  }

  public String getText(){
    return jta.getText();
  }

}

