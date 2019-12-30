package radian.tcmis.common.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/***************************************************************************
 * CLASSNAME: TagHandler<BR>
 * Function: <BR>
 ***************************************************************************/
public final class TagHandler {
  private TagHandler() {}

  /*************************************************************************
   * Evaluates attribute value using the JSTL EL engine.
   *************************************************************************/
  public static Object evaluateObject(String name,
                                      String value,
                                      Tag tag,
                                      PageContext pageContext)
      throws JspException {

    Object object = null;
    if (value != null) {
      object =
          ExpressionEvaluatorManager.
          evaluate(name, value, Object.class, tag, pageContext);
    }
    return object;
  }

  /*************************************************************************
   * Evaluates attribute value using the JSTL EL engine.
   *************************************************************************/
  public static String evaluateString(String name,
                                      String value,
                                      Tag tag,
                                      PageContext pageContext)
      throws JspException {

    Object object = null;
    if (value != null) {
      object =
          ExpressionEvaluatorManager.
          evaluate(name, value, String.class, tag, pageContext);
    }
    return (String)object;
  }

  /*************************************************************************
   * Evaluates attribute value using the JSTL EL engine.
   *************************************************************************/
  public static Integer evaluateInteger(String name,
                                        String value,
                                        Tag tag,
                                        PageContext pageContext)
      throws JspException {
    Object object = null;
    if (value != null) {
      object = ExpressionEvaluatorManager.
          evaluate(name, value, Integer.class, tag, pageContext);
    }
    return (Integer)object;
  }

  /*************************************************************************
   * Evaluates attribute value using the JSTL EL engine.
   *************************************************************************/
  public static Boolean evaluateBoolean(String name,
                                        String value,
                                        Tag tag,
                                        PageContext pageContext)
      throws JspException {

    Object object = null;
    if (value != null) {
      object = ExpressionEvaluatorManager.
          evaluate(name, value, Boolean.class, tag,
                   pageContext);
    }
    return (Boolean)object;
  }
}
