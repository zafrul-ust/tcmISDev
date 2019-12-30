package radian.tcmis.internal.snoop.beans;

import java.util.Date;
import radian.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UserEnvLogBean <br>
 * @version: 1.0, Dec 3, 2004 <br>
 *****************************************************************************/

public class UserEnvLogBean
    extends BaseDataBean {

  private int personnelId;
  private Date timeStamp = new Date();
  private String detail;

  //constructor
  public UserEnvLogBean() {
  }

  //setters
  public void setPersonnelId(int personnelId) {
    this.personnelId = personnelId;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  //getters
  public int getPersonnelId() {
    return personnelId;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public String getDetail() {
    return detail;
  }

}