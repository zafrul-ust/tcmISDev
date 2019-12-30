/*
package radian.tcmis.client.share.gui.admin;

public class AddWasteDlg {

  public AddWasteDlg() {
  }
}    */


package radian.tcmis.client.share.gui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import radian.tcmis.client.share.gui.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;

public class AddWasteDlg extends JDialog {
  public JTextField tf = new JTextField();
 // public JTextField addFacId = new JTextField();
  private String updateDesc;
 // private String newId;
  boolean myflag;
  AdminWastePickupLocation awpl;
  public AddWasteDlg(AdminWastePickupLocation awpl,Frame f,String title,String facilityId, String currentId, String currentDesc,boolean ADD_FLAG) {
    super(f,title,true);
    this.awpl = awpl;
    myflag = ADD_FLAG;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    jPanel2.setLayout(gridBagLayout1);

    /*String wDir = new String(System.getProperty("user.dir"));
    String imagesDir = wDir + System.getProperty("file.separator") + (new ClientResourceBundle()).getString("LOCAL_IMAGES_DIR");
    */
    JButton jButton1 = new JButton("OK");
    jButton1.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    jButton1.setMaximumSize(new Dimension(110,35));
    jButton1.setMinimumSize(new Dimension(110, 35));
    jButton1.setPreferredSize(new Dimension(110, 35));
    jPanel1.add(jButton1);
    JButton jButton2 = new JButton("Cancel");
    jButton2.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    jButton2.setMaximumSize(new Dimension(110, 35));
    jButton2.setMinimumSize(new Dimension(110, 35));
    jButton2.setPreferredSize(new Dimension(110, 35));
    jPanel1.add(jButton2);

    this.getContentPane().add(jPanel1, "Center");
    StaticJLabel label = new StaticJLabel("Description:");
    StaticJLabel id = new StaticJLabel();
    StaticJLabel facL = new StaticJLabel();
    DataJLabel facId = new DataJLabel();
    DataJLabel facLocation = new DataJLabel();

    facId.setText(currentId);
    facLocation.setText(facilityId);

    tf.setText(currentDesc);
    tf.setMaximumSize(new Dimension(180, 21));
    tf.setMinimumSize(new Dimension(180, 21));
    tf.setPreferredSize(new Dimension(180, 21));

    facL.setText("Facility:");
    id.setText("ID:");
    jPanel2.setMaximumSize(new Dimension(320, 100));
    jPanel2.setMinimumSize(new Dimension(320, 100));
    jPanel2.setPreferredSize(new Dimension(320, 100));

    jPanel2.add(facL, new GridBagConstraints2(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 0, 0, 0), 0, 0));
    jPanel2.add(facLocation, new GridBagConstraints2(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 0, 0, 0), 0, 0));
    if(!myflag) {  //if ADD then don't show Id field
      jPanel2.add(id, new GridBagConstraints2(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 0, 0, 0), 0, 0));
      jPanel2.add(facId, new GridBagConstraints2(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(3, 0, 0, 0), 0, 0));
    }
    jPanel2.add(label, new GridBagConstraints2(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 0, 0, 3), 0, 0));
    jPanel2.add(tf, new GridBagConstraints2(2, 2, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 3, 0, 3), 0, 0));
    this.getContentPane().add(jPanel2, "North");

    this.setResizable(false);
    this.pack();
    jButton1.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      updateDesc = tf.getText().toString().substring(0,30);
      AddWasteDlg.this.setVisible(false);
      setDescription();
      }
    });

    jButton2.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        AddWasteDlg.this.setVisible(false);
        }
      });
  }

    public void setDescription() {
      if (myflag)
        awpl.updateAdd(updateDesc);
      else
        awpl.updateEditing(updateDesc);
    }
}