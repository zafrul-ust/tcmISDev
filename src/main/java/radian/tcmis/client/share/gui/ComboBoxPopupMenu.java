package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ComboBoxPopupMenu extends JDialog {
   public ComboBoxPopupMenu(Frame frame) {
        super(frame);
        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig", "Fish", "Horse", "Cow", "Bee", "Skunk" };
        final ScrollablePopupMenu petList=new ScrollablePopupMenu(petStrings);
        petList.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                System.out.println(petList.getSelectedItem());
           }
        });
        final JLabel lab=new JLabel(" ");
        lab.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
                petList.showPopup(e.getX(),e.getY());
           }
        });
        getContentPane().add(lab);
        getContentPane().add(petList);
        this.setSize(200,300);
        CenterComponent.centerCompOnScreen(this);
   }
}
