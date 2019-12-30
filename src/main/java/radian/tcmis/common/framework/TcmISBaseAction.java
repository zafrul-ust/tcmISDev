package radian.tcmis.common.framework;

import java.util.List;
import java.util.Locale;
import java.util.Iterator;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import radian.tcmis.internal.admin.beans.PersonnelBean;
import radian.tcmis.common.exceptions.BaseException;
import radian.tcmis.common.util.Globals;

/******************************************************************************
 * An abstract Action class that all report action classes should
 * extend.
 *****************************************************************************/
abstract public class TcmISBaseAction
    extends Action
    implements Globals {
  protected Log log = LogFactory.getLog(this.getClass());

/*****************************************************************************
 * Default execute method all actions must implement.
 *****************************************************************************/
  public ActionForward execute(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    ActionForward actionForward = null;
    try {
      actionForward = executeAction(mapping, form, request, response);
    }
    catch (BaseException be) {
      log.fatal("APPLICATION ERROR:" +
                be.getMessage());
      actionForward = processExceptions(request, mapping, be);
    }
    catch (Throwable e) {
      System.out.println("UNEXPECTED SYSTEM ERROR:" + e.getMessage());
      log.fatal("UNEXPECTED SYSTEM ERROR:" + e.getMessage());
      e.printStackTrace(System.out);
      actionForward = mapping.findForward(Globals.SYSTEM_ERROR_PAGE);
    }
    //catch strange things...
    if (actionForward == null) {
      actionForward = mapping.findForward(Globals.GENERIC_ERROR_PAGE);
    }
    return actionForward;
  }

/*****************************************************************************
 * Handles every <code>BaseException</code> thrown by the application
*****************************************************************************/
  protected ActionForward processExceptions(HttpServletRequest request,
                                            ActionMapping mapping,
                                            BaseException be) {
    ActionErrors errors = new ActionErrors();
    ActionForward forward = null;

    Locale locale = request.getLocale();
    //set default if none exists
    if (locale == null) {
      locale = Locale.getDefault();
    }
    //process first exception
    processBaseException(errors, be, locale);
    //forward to input page or if none exists to generic error page
    String inputPage = mapping.getInput();
    if (inputPage == null) {
      forward = new ActionForward(Globals.GENERIC_ERROR_PAGE);
    }
    else {
      forward = new ActionForward(inputPage);
    }

    //see if there are more than one exception
    List exceptions = be.getExceptions();
    if (exceptions != null && exceptions.size() != 0) {
      Iterator iterator = exceptions.iterator();
      while (iterator.hasNext()) {
        BaseException subException = (BaseException) iterator.next();
        processBaseException(errors, subException, locale);
      }
    }
    //save errors and forward to view
    saveErrors(request, errors);
    return forward;
  }

/*****************************************************************************
 * Creates the <code>ActionError</code> objects
*****************************************************************************/
  protected void processBaseException(ActionErrors actionErrors,
                                      BaseException be,
                                      Locale locale) {

//    ActionError actionError = null;
//    String errorKey = be.getMessageKey();
//    Object[] arguments = be.getMessageArgs();
//    if (arguments != null && arguments.length > 0) {
//      actionError = new ActionError(errorKey, arguments);
//    }
//    else {
//      actionError = new ActionError(errorKey);
//    }
//    actionErrors.add(ActionErrors.GLOBAL_ERROR, actionError);
  }


/******************************************************************************
 * The actual work method that must be overridden by all subclasses.
******************************************************************************/
  public abstract ActionForward executeAction(ActionMapping mapping,
                                              ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws
      Exception;

/*****************************************************************************
 * Retrieve a session object based on the request and the attribute name.
******************************************************************************/
  protected Object getSessionObject(HttpServletRequest request,
                                    String attributeName) {
    Object sessionObject = null;

    // Don't create a session if one isn't already present
    HttpSession session = request.getSession(false);
    sessionObject = session.getAttribute(attributeName);
    return sessionObject;
  }

/*****************************************************************************
 * Set a session object based on the attribute name.
******************************************************************************/
    protected void setSessionObject(HttpServletRequest request,
                                    Object object,
                                    String attributeName) {
      // Don't create a session if one isn't already present
      HttpSession session = request.getSession(false);
      session.setAttribute(attributeName, object);
      return;
    }


/*****************************************************************************
 * Retrieve an object from the application scope by its name. This is
 * a convenience method.
******************************************************************************/
  protected Object getApplicationObject(String attrName) {
    return servlet.getServletContext().getAttribute(attrName);
  }

/*****************************************************************************
 * Set an object in the application scope by its name. This is
 * a convenience method.
******************************************************************************/
    protected void setApplicationObject(String attrName, Object object) {
      servlet.getServletContext().setAttribute(attrName, object);
      return;
    }


/*****************************************************************************
 * Check if there is a non-null <code>PersonnelBean</code> in the session scope.
 * This is a convenience method.
******************************************************************************/
  protected boolean isLoggedIn(HttpServletRequest request) {
    boolean flag = false;
    PersonnelBean bean = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      try {
        bean = (PersonnelBean) session.getAttribute("personnelBean");
        int i = bean.getPersonnelId();
        flag = true;
      }
      catch (Exception e) {
        //ignore
      }
    }
    //add error message if not logged in
    if (!flag) {
      ActionMessages messages = new ActionMessages();
      messages.add(ActionMessages.GLOBAL_MESSAGE,
                 new ActionMessage("error.session.invalid"));
      saveErrors(request, messages);
    }
    return flag;
  }

/*****************************************************************************
 * Tries to get the client stored in a <code>PersonnelBean</code> in
 * the session scope. This is a convenience method.
******************************************************************************/
  protected String getClient(HttpServletRequest request) throws BaseException {
    String client = null;
    PersonnelBean bean = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      try {
        bean = (PersonnelBean) session.getAttribute("personnelBean");
        client = bean.getClient();
      }
      catch (Exception e) {
        throw new BaseException("");
      }
    }
    else {
      BaseException be = new BaseException();
      be.setMessageKey("error.login.invalid");
      throw be;
    }
    return client;
  }

/*****************************************************************************
 * Tries to get the company id stored in a <code>PersonnelBean</code> in
 * the session scope. This is a convenience method.
******************************************************************************/
  protected String getCompanyId(HttpServletRequest request) throws
      BaseException {
    String companyId = null;
    PersonnelBean bean = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      try {
        bean = (PersonnelBean) session.getAttribute("personnelBean");
        companyId = bean.getCompanyId();
      }
      catch (Exception e) {
        throw new BaseException("");
      }
    }
    else {
      BaseException be = new BaseException();
      be.setMessageKey("error.login.invalid");
      throw be;
    }
    return companyId;
  }

/*****************************************************************************
 * Tries to get the client stored in a <code>PersonnelBean</code> in
 * the session scope. This is a convenience method.
******************************************************************************/
  protected int getPersonnelId(HttpServletRequest request) throws BaseException {
    int personnelId = 0;
    PersonnelBean bean = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      try {
        bean = (PersonnelBean) session.getAttribute("personnelBean");
        personnelId = bean.getPersonnelId();
      }
      catch (Exception e) {
        throw new BaseException("");
      }
    }
    else {
      BaseException be = new BaseException();
      be.setMessageKey("error.login.invalid");
      throw be;
    }
    return personnelId;
  }

/*****************************************************************************
 * Tries to get the logonId stored in a <code>PersonnelBean</code> in
 * the session scope. This is a convenience method.
******************************************************************************/
  protected String getLogonId(HttpServletRequest request) throws BaseException {
    String logonId = null;
    PersonnelBean bean = null;
    HttpSession session = request.getSession(false);
    if (session != null) {
      try {
        bean = (PersonnelBean) session.getAttribute("personnelBean");
        logonId = bean.getLogonId();
      }
      catch (Exception e) {
        throw new BaseException("");
      }
    }
    else {
      BaseException be = new BaseException();
      be.setMessageKey("error.login.invalid");
      throw be;
    }
    return logonId;
  }

  /*****************************************************************************
   * Tries to get the logonId stored in a <code>PersonnelBean</code> in
   * the session scope. This is a convenience method.
  ******************************************************************************/
    protected boolean hasPermission(HttpServletRequest request,
                                    String userGroupId,
                                    String facilityId,
                                    String inventoryGroup) throws BaseException {
      boolean flag = false;
      String logonId = null;
      PersonnelBean bean = null;
      HttpSession session = request.getSession(false);
      if (session != null) {
        try {
          bean = (PersonnelBean) session.getAttribute("personnelBean");
          flag = bean.hasPermission(userGroupId, facilityId, inventoryGroup);
        }
        catch (Exception e) {
          throw new BaseException("");
        }
      }
      else {
        BaseException be = new BaseException();
        be.setMessageKey("error.login.invalid");
        throw be;
      }
      return flag;
    }

    /*****************************************************************************
     * Tries to get the logonId stored in a <code>PersonnelBean</code> in
     * the session scope. This is a convenience method.
    ******************************************************************************/
      protected boolean hasPermission(HttpServletRequest request,
                                      String userGroupId,
                                      String facilityId) throws BaseException {
        boolean flag = false;
        String logonId = null;
        PersonnelBean bean = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
          try {
            bean = (PersonnelBean) session.getAttribute("personnelBean");
            flag = bean.hasPermission(userGroupId, facilityId);
          }
          catch (Exception e) {
            throw new BaseException("");
          }
        }
        else {
          BaseException be = new BaseException();
          be.setMessageKey("error.login.invalid");
          throw be;
        }
        return flag;
      }

}
