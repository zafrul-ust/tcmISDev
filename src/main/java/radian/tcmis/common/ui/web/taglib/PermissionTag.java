package radian.tcmis.common.ui.web.taglib;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import radian.tcmis.internal.admin.beans.PersonnelBean;
import radian.tcmis.common.util.TagHandler;

/*****************************************************************************
 * Custom tag to check if user has permission. Checks first for userGroupId and
 * then for inventoryGroup. If inventoryGroup is not defined tag will only check
 * for userGroupId. Indicator will reverse result if set to false.
 *
 * The attributes of the tag are as follows:<p>
 * <b>indicator</b> - indicator of whether to reverse result<br>
 * <b>userGroupId</b> - userGroupId to check for<br>
 * <b>inventoryGroup</b> - inventoryGroup to check for<br>
 *
 ****************************************************************************/
public class PermissionTag extends TagSupport {
  private String indicator;
  private String userGroupId;
  private String facilityId;
  private String inventoryGroup;

  //setters
  public void setIndicator(String indicator) {
    this.indicator = indicator;
  }

  public void setUserGroupId(String userGroupId) {
    this.userGroupId = userGroupId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  //getters
  public String getIndicator() {
    return this.indicator;
  }

  public String getUserGroupId() {
    return this.userGroupId;
  }

  public String getFacilityId() {
    return this.facilityId;
  }

  public String getInventoryGroup() {
    return this.inventoryGroup;
  }

  public int doStartTag() throws JspException {
    HttpSession session = pageContext.getSession();
    PersonnelBean currentUser = (PersonnelBean) session.getAttribute("personnelBean");

    boolean valid = false;

    if(currentUser == null) {
      throw new JspException(
          "PermissionTag: User must be logged in");
    }

    if(indicator == null || indicator.trim().length() < 1 ||
       (!indicator.equalsIgnoreCase("false") && !indicator.equalsIgnoreCase("true"))) {
      throw new JspException(
          "PermissionTag: indicator attribute must be \"true\" or \"false\"");
    }
    else if(userGroupId == null || userGroupId.trim().length() < 1) {
      throw new JspException(
          "PermissionTag: userGroupdId attribute is required");
    }
    else if(currentUser == null) {
      valid = false;
    }
    else {
      evaluate();
      valid = currentUser.hasPermission(userGroupId,
                                        facilityId,
                                        inventoryGroup);
    }

    if(indicator.equalsIgnoreCase("false")) {
      valid = !valid;
    }

    if (valid) {
      return EVAL_BODY_INCLUDE;
    }
    else {
      return SKIP_BODY;
    }

  }

  public int doEndTag() {
    setIndicator(null);
    setUserGroupId(null);
    setFacilityId(null);
    setInventoryGroup(null);
    return EVAL_PAGE;
  }

  public void release() {
    super.release();
    setIndicator(null);
    setUserGroupId(null);
    setFacilityId(null);
    setInventoryGroup(null);
  }

  private void evaluate() throws JspException {
      String  s = null;

      s = TagHandler.evaluateString("userGroupId", this.getUserGroupId(), this, this.pageContext);
      if(s != null) {
        this.setUserGroupId(s);
      }
      s = TagHandler.evaluateString("facilityId", this.getFacilityId(), this, this.pageContext);
      if(s != null) {
        this.setFacilityId(s);
      }
      s = TagHandler.evaluateString("inventoryGroup", this.getInventoryGroup(), this, this.pageContext);
      if(s != null) {
        this.setInventoryGroup(s);
      }
  }

}
