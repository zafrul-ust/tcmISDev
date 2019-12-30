package radian.tcmis.client.share.gui;

import java.awt.event.*;
import javax.swing.*;
import radian.tcmis.client.share.helpers.*;

public class TcmisButton extends JButton{
    public TcmisButton(){
      super();
      this.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
           ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
         }
      });
    }
}
