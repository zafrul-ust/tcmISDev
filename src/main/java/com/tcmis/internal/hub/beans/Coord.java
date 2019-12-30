package com.tcmis.internal.hub.beans;

/**
 * Simple bean to use for testing indexed tags.
 */
public class Coord
    implements java.io.Serializable {
  private int x;
  private int y;

  public Coord() {}

  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return (x);
  }

  public int getY() {
    return (y);
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public String toString() {
    return ("Coord[" + "x=" + x + ";y=" + y + "]");
  }
}
