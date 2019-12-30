package radian.tcmis.both1.helpers;

//import java.awt.*;
import java.awt.Font;

/**
 * @version 1.0 11/22/98
 */

public interface CellFont {
  public Font getFont(int row, int column);
  public void setFont(Font font, int row, int column);
  public void setFont(Font font, int[] rows, int[] columns);
}
