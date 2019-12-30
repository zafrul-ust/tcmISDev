package radian.tcmis.client.share.gui;


import java.awt.*;
import javax.swing.*;



public final class CheckBoxListCellRenderer extends JCheckBox implements ListCellRenderer {

        public Component getListCellRendererComponent(
                                JList list, Object value,int index,
                                boolean isSelected, boolean hasFocus) {
            setOpaque(true);

            JCheckBox cValue = (JCheckBox) value;

            if (cValue != null && cValue.getText().length()>0){
               this.setText(cValue.getText());
            }

            if (isSelected) {
                setSelected(true);
	              super.setForeground(list.getSelectionForeground());
	              super.setBackground(list.getSelectionBackground());
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
	          } else {
                setForeground(list.getForeground());
	              setBackground(list.getBackground());
                setSelected(false);
            }

            setHorizontalAlignment(JLabel.CENTER);
            this.repaint();
            return this;
        }

}




