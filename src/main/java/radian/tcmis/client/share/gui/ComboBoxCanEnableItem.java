package radian.tcmis.client.share.gui;

class ComboBoxCanEnableItem implements ComboBoxCanEnable {
  Object  obj;
  boolean isEnable;

  ComboBoxCanEnableItem(Object obj,boolean isEnable) {
    this.obj      = obj;
    this.isEnable = isEnable;
  }

  ComboBoxCanEnableItem(Object obj) {
    this(obj, true);
  }

  public boolean isEnabled() {
    return isEnable;
  }

  public void setEnabled(boolean isEnable) {
    this.isEnable = isEnable;
  }

  public String toString() {
    return obj.toString();
  }
}




