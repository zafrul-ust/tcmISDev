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
import java.awt.Component;
import javax.swing.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.client.share.gui.*;


public class DeleteProcessMetaPopUp extends JPopupMenu {
  CmisApp cmis;
  boolean deleteProcess = false;
  MultiSpanCellTable tmpTable;
  int row;

  public DeleteProcessMetaPopUp(CmisApp cmis, MultiSpanCellTable t, int r, boolean b) {
    super();
    this.cmis = cmis;
    this.tmpTable = t;
    this.row = r;
    deleteProcess = b;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception{
    JMenuItem mi;
    DeleteProcessMetaActionListener mal = new DeleteProcessMetaActionListener(this);
    if (deleteProcess) {
      mi = new JMenuItem("Delete");
      mi.setActionCommand("Delete");
    }else {
      mi = new JMenuItem("Cannot Delete");
      mi.setActionCommand("Cannot Delete");
      mi.setEnabled(false);
    }
    mi.setFont(new java.awt.Font("Dialog", 0, 10));
    add(mi);
    mi.addActionListener(mal);
  } //end of method

  void goAction(ActionEvent e) {
    if (e.getActionCommand().toString().equals("Delete")) {
      AttributiveCellTableModel model = (AttributiveCellTableModel)tmpTable.getModel();
      model.removeRow(row);
    }
  }

  public void show(Component comp, int x, int y) {
    // make sure the entire menu is showing
    // this is a real kludge
    int offset = 185;
    int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
    if(comp.getLocationOnScreen().y + y > screenH - offset) {
      y = screenH - offset - comp.getLocationOnScreen().y;
    }
    super.show(comp,x,y);
  }
}

class DeleteProcessMetaActionListener implements ActionListener {
  DeleteProcessMetaPopUp you;
  public DeleteProcessMetaActionListener(DeleteProcessMetaPopUp you){
    this.you = you;
  }
  public void actionPerformed(ActionEvent e) {
    you.goAction(e);
  }
}
