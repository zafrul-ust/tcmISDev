package radian.tcmis.client.share.gui;


import java.awt.*;
import javax.swing.*;


public class StaticJLabel extends JLabel {

    public StaticJLabel(){
        super();
        this.setForeground(Color.black);
        this.setFont(new Font("Dialog", 0, 10));
    }

    public StaticJLabel(String l){
       this();
       setText(l);
    }

}
