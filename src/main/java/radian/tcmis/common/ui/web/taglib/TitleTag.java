package radian.tcmis.common.ui.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/*****************************************************************************
 * Custom tag to build the <HEAD> and <TITLE> section of the page. It also
 * includes the shortcut icon, CSS file and any javascript file passed as
 * an argument.
 *
 * The attributes of the tag are as follows:<p>
 * <b>cssFile</b> - CSS file to link
 * <b>javaScriptFile</b> - Javascript file to include
 * <b>pageTitle</b> - Title of the page<br>
 *
 ****************************************************************************/
public class TitleTag extends TagSupport  {
  private String cssFile;
  private String javaScriptFile;
  private String pageTitle;

  //setters
  public void setCssFile(String cssFile) {
    this.cssFile = cssFile;
  }

  public void setJavaScriptFile(String javaScriptFile) {
    this.javaScriptFile = javaScriptFile;
  }

  public void setPageTitle(String pageTitle) {
    this.pageTitle = pageTitle;
  }

  //getters
  public String getCssFile() {
    return this.cssFile;
  }

  public String getJavaScriptFile() {
    return this.javaScriptFile;
  }

  public String getPageTitle() {
    return this.pageTitle;
  }

  public int doStartTag() throws JspException {
	/*BodyContent bc = getBodyContent();
	String body = bc.getString();
	bc.clearBody();*/
	JspWriter out = pageContext.getOut();

	try
	{
	  out.println( "<HEAD>\n" );
	  out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
	  //out.println(body);
	  //out.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\"" + body + "images/buttons/tcmIS.ico\"></LINK>\n" );
	  out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
	  out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
	  out.println( "<TITLE>" + this.getPageTitle() + "</TITLE>\n" );

	  if ( this.getCssFile().length() > 1 )
	  {
	  out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/" + this.getCssFile() + "\"></LINK>\n" );
	  }

	  if ( this.getJavaScriptFile().length() > 1 )
	  {
		out.println( "<SCRIPT SRC=\"/clientscripts/" + this.getJavaScriptFile() + "\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n" );
	  }
	}
	catch ( Exception e )
	{
	  // Ignore.
	}

	return SKIP_BODY;
  }

  public int doEndTag() {
    setCssFile(null);
    setJavaScriptFile(null);
    setPageTitle(null);
    return EVAL_PAGE;
  }

  public void release() {
    super.release();
    setCssFile(null);
    setJavaScriptFile(null);
    setPageTitle(null);
  }
}
