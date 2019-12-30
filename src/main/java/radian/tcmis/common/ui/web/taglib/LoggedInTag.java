package radian.tcmis.common.ui.web.taglib;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import radian.tcmis.internal.admin.beans.PersonnelBean;

/*****************************************************************************
 * Custom tag to check if user is logged in. Forwards to page specified if
 * validation fails, that is, if loggedIn attribute is set to true and user
 * is not logged in user will be forwarded to page specified by forwardPage
 * attribute.<p>
 *
 * The attributes of the tag are as follows:<p>
 * <b>indicator</b> - indicator of whether user has to be logged in or not<br>
 * <b>forwardPage</b> - page to forward to if validation fails<br>
 *
 ****************************************************************************/
public class LoggedInTag extends TagSupport {

  private String indicator = "";
  private String forwardPage = "";

  // getters
  public String getIndicator() {
    return this.indicator;
  }

  public String getForwardPage() {
    return this.forwardPage;
  }

  // setters
  public void setIndicator(String newIndicator) throws JspException {
    newIndicator = newIndicator.toLowerCase();

    // check to be sure that attribute was passed a valid value
    if (!newIndicator.equals("true") &&
        !newIndicator.equals("false") ) {
      throw new JspException(
          "LoggedInTag: indicator attribute must be true | false ");
    }
    this.indicator = newIndicator.toLowerCase();
  }

  public void setForwardPage(String forwardPage) throws JspException {
    if(forwardPage != null && forwardPage.length() > 0) {
      this.forwardPage = forwardPage;
    }
    else {
      throw new JspException(
          "LoggedInTag: forwardPage attribute is required ");
    }
  }

  public int doStartTag() throws JspException {
    HttpSession session = pageContext.getSession();
    PersonnelBean currentUser = (PersonnelBean) session.getAttribute("personnelBean");

    boolean valid = true;

    // if user has not logged in, and is required to be logged in, make valid false
    if (currentUser == null && indicator.equals("true")) {
      valid = false;
    }
    // if user is logged in, and is required to not be logged in, make valid false
    else if (currentUser != null && indicator.equals("false")) {
      valid = false;
    }

    if (valid) {
      return EVAL_BODY_INCLUDE;
    }
    else {
      try {
        pageContext.forward(forwardPage);
      }
      catch (Exception e) {
        throw new JspException(e.toString());
      }
      return SKIP_BODY;
    }
  }

  public int doEndTag() {
    indicator = "";
    forwardPage = "";
    return EVAL_PAGE;
  }

  public void release() {
    super.release();
    indicator = "";
    forwardPage = "";
  }
}
