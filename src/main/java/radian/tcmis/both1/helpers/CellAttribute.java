package radian.tcmis.both1.helpers;

//import java.awt.*;
import java.awt.Dimension;

/**
 * @version 1.0 11/22/98
 */

public interface CellAttribute {

  public void addColumn();

  public void addRow();

  public void insertRow(int row);

  public Dimension getSize();

  public void setSize(Dimension size);

  public void initValue();

  public void setSpan(int[] span, int row, int column);


}
