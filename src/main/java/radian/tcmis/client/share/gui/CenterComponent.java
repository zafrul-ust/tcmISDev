
//Title:        CenterComponent
//Version:      
//Copyright:    Copyright (c) 1998
//Author:       Chuck King
//Company:      Radian International
//Description:  Used for centering a component.

package radian.tcmis.client.share.gui;

import java.awt.*;
import java.awt.Component;

public class CenterComponent extends Object {
  public static boolean centerCompOnComp(Component parent, Component child) {
// CBK - begin
    if (parent != null && parent.isShowing()) {
// CBK - end
      child.setLocation((parent.getSize().width - child.getSize().width) / 2 + parent.getLocationOnScreen().x, (parent.getSize().height - child.getSize().height) / 2 + parent.getLocationOnScreen().x);
      return true;
    }
    return centerCompOnScreen(child);
  }

  public static boolean centerCompOnScreen(Component child) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension childSize = child.getSize();
    if (childSize.height > screenSize.height)
      childSize.height = screenSize.height;
    if (childSize.width > screenSize.width)
      childSize.width = screenSize.width;
    child.setLocation((screenSize.width - childSize.width) / 2, (screenSize.height - childSize.height) / 2);
    return true;
  }

  public static boolean centerCompOnScreenNHigher(Component child,int up) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension childSize = child.getSize();
    if (childSize.height > screenSize.height)
      childSize.height = screenSize.height;
    if (childSize.width > screenSize.width)
      childSize.width = screenSize.width;
    int hi =  (screenSize.height - childSize.height) / 2;
    if (up > 0) hi = hi / up;
    child.setLocation((screenSize.width - childSize.width) / 2, hi);
    return true;
  }

}




























