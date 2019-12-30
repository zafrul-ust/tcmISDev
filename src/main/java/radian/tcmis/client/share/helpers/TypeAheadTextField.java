package radian.tcmis.client.share.helpers;

import radian.tcmis.client.share.gui.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class TypeAheadTextField extends JTextField implements Runnable {
    protected String last = "";
    protected String current = "";
    protected Thread thread = null;
    protected int caret;
    protected String nextString = "";
    private boolean busy = false;
    protected boolean updating = false;
    protected CmisApp parent = null;
    protected ThreadGroup tg = null;
    protected int wait = 0;
    protected final int DEFAULT_WAIT = 1000;

    public TypeAheadTextField () {
      super();
      addListeners();
      tg = new ThreadGroup("myTG");
      setWait(DEFAULT_WAIT);
    }

    void addListeners(){
      this.addKeyListener(new TypeAhead_keyListener(this));
      this.addFocusListener(new TypeAheadFocusListener(this));
    }

    public void changed() {
      if (updating) {
        updating = false;
        return;
      }
      if (busy) return;

      //stop thread if it's running...
      if (thread != null && thread.isAlive()) {
        synchronized(thread) {
          tg.stop();
          //thread.stop();
        }
        busy = false;
      }

      thread = new Thread(tg,this,"nextName");
      thread.start();
    }

    public void run() {
      String mySeed = "";
      try {
        Thread.sleep(wait);
      } catch (Exception e) {}

      caret = this.getCaretPosition();
      current = this.getText();
      current.trim();
      nextString = this.getNextString();

      busy = true;
      this.setUpdating(true);
      this.setText(nextString);
      this.last = new String(nextString);

      //this.setCaretPosition(caret);
      try{
        this.select(caret,this.getText().length());
      }catch(Exception e){}
      current = new String(last);
      busy = false;
      this.setUpdating(false);

    }

    public void setUpdating() {
      // this tells it to ignore the next change
      updating = true;
    }

    public void setUpdating(boolean b) {
      updating = b;
    }
    public void backSpaceTyped(){}

    protected abstract String getNextString() ;

    public boolean isBusy() {
      return busy;
    }

    public void setParent(CmisApp parent) {
      this.parent = parent;
    }
    public int getWait() {
      return wait;
    }
    public void setWait(int i) {
      wait = i;
    }
    public void setText(String s){
      updating = true;
      super.setText(s);
      updating = false;
    }
    public void focusLost() {
      this.setSelectionEnd(0);
      this.setCaretPosition(this.getText().length());
    }
}
class TypeAhead_keyListener implements KeyListener {
  TypeAheadTextField adaptee;

  TypeAhead_keyListener(TypeAheadTextField nm) {
    adaptee = nm;
  }

  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == e.VK_BACK_SPACE) {
      adaptee.backSpaceTyped();
      return;
    }
    adaptee.changed();
  }
  public void keyTyped(KeyEvent e) {
  }
  public void keyReleased(KeyEvent e) {
  }
}
class TypeAheadFocusListener implements FocusListener {
  TypeAheadTextField adaptee;

  TypeAheadFocusListener(TypeAheadTextField nm) {
    adaptee = nm;
  }
  public void focusGained(FocusEvent e) {}
  public void focusLost(FocusEvent e) {
    adaptee.focusLost();
  }
}

