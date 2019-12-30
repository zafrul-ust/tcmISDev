package com.tcmis.internal.hub.beans;
import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DockOvBean
    extends BaseDataBean {

  private String dock;

  //constructor
  public DockOvBean() {
  }

  //setters
  public void setDock(String dock) {
    this.dock = dock;
  }

  //getters
  public String getDock() {
    return dock;
  }
}
