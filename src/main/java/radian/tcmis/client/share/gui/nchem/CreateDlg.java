package radian.tcmis.client.share.gui.nchem;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author not attributable
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import radian.tcmis.client.share.gui.*;

public class CreateDlg extends JDialog {
  StaticJLabel labelL = new StaticJLabel();
  public JTextArea inputT = new JTextArea();
  JButton B = new JButton();
  JButton cancelB = new JButton();
  String label = new String(" ");
  JPanel paneM = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  boolean auditInput = false;
  int auditLength = 0;
  boolean cancelled = false;

  public CreateDlg(Frame frame, String title, boolean modal) {
    this(frame,title,modal,"");
  }

  public CreateDlg(Frame frame, String title, boolean modal, String label) {
      super(frame, title, modal);
      this.label= label;
      try {
        jbInit();
        getContentPane().add(paneM);
        pack();
        CenterComponent.centerCompOnScreen(this);
         //colors and fonts
         ClientHelpObjs.makeDefaultColors(this);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
  }

  public CreateDlg(Frame frame) {
    this(frame, "", false);
  }

  public CreateDlg(Frame frame, boolean modal) {
    this(frame, "", modal);
  }

  public CreateDlg(Frame frame, String title) {
    this(frame, title, false);
  }

  public void setAuditInput(boolean b, int i) {
    auditInput = b;
    auditLength = i;
  }


  public void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    this.setSize(new Dimension(400,270));
    B.setText("Ok");
    B.setMaximumSize(new Dimension(79, 25));
    B.setMinimumSize(new Dimension(79, 25));
    B.setPreferredSize(new Dimension(79, 25));
    B.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    B.addActionListener(new CreateDlg_B_actionAdapter(this));
    B.addKeyListener(new CreateDlg_B_keyAdapter(this));
    cancelB.setText("Cancel");
    cancelB.setMaximumSize(new Dimension(102, 25));
    cancelB.setMinimumSize(new Dimension(102, 25));
    cancelB.setPreferredSize(new Dimension(102, 25));
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new CreateDlg_cancelB_actionAdapter(this));
    cancelB.addKeyListener(new CreateDlg_cancelB_keyAdapter(this));

    labelL.setMaximumSize(new Dimension(350, 14));
    labelL.setMinimumSize(new Dimension(350, 14));
    labelL.setPreferredSize(new Dimension(350, 14));
    labelL.setText(label);
    paneM.setLayout(gridBagLayout1);
    paneM.add(labelL,    new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    paneM.add(inputT,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    paneM.add(B,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 60, 5, 5), 0, 0));
    paneM.add(cancelB,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 60), 0, 0));
  }

  public boolean audit() {
    if (inputT.getText().length() < 1 || inputT.getText().length() > auditLength) {
      GenericDlg.showMessage("Input text must contains at least 1 character and no more than "+auditLength+" characters.");
      return false;
    }else {
      return true;
    }
  }

  public String getText() {
    return inputT.getText();
  }

  public void setText(String text) {
    inputT.setText(text);
  }

  void B_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (this.auditInput) {
        if (audit()) {
          this.setVisible(false);
        }
      }else {
        this.setVisible(false);
      }
    }
  }

  void B_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      if (this.auditInput) {
        if (audit()) {
          this.setVisible(false);
        }
      }else {
        this.setVisible(false);
      }
  }

  void cancelB_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       cancelled = true;
       this.setVisible(false);
    }
  }

  void cancelB_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      cancelled = true;
      this.setVisible(false);
  }

  void this_windowClosing(WindowEvent e) {
    cancelled = true;
    this.setVisible(false);
  }
}

class CreateDlg_B_keyAdapter extends java.awt.event.KeyAdapter {
  CreateDlg adaptee;

  CreateDlg_B_keyAdapter(CreateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.B_keyPressed(e);
  }
}

class CreateDlg_B_actionAdapter implements java.awt.event.ActionListener {
  CreateDlg adaptee;

  CreateDlg_B_actionAdapter(CreateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.B_actionPerformed(e);
  }
}

class CreateDlg_cancelB_keyAdapter extends java.awt.event.KeyAdapter {
  CreateDlg adaptee;

  CreateDlg_cancelB_keyAdapter(CreateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.cancelB_keyPressed(e);
  }
}

class CreateDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  CreateDlg adaptee;

  CreateDlg_cancelB_actionAdapter(CreateDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

