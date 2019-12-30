<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<tcmis:loggedIn indicator="true" forwardPage="/internalreport/index.jsp"/>
<html:html lang="true">
<head>
<title>
  <bean:message key="excel.title"/>
</title>
</head>
<body bgcolor="white">

<html:errors/>
<logic:present name="errorBean" scope="request">
  <font face=Verdana size=2 color="red">
    <b><bean:message key="excel.label.oracleerror"/>: <bean:write name="errorBean" property="cause"/></b>
  </font>
</logic:present>
<h3>
  <html:img src="/images/logo_s.gif" border="1" align="top"/>
  <font face=Verdana size=2>
    <bean:message key="label.haastcm"/>
  </font>
</h3>
<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="shipmenthistory.title"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<A HREF="/tcmIS/Hub/Home" STYLE="color:#FFFFFF"><fmt:message key="label.home"/></A>
</TD>
</TR>
</TABLE>

<table border="0">
  <tr>
    <th align="left">
      <font face=Verdana size=3>
        <bean:message key="excel.header"/>
      </font>
    </th>
    <th align="right">
      <font face=Verdana size=2>
        <html:link forward="changepassword">
          <bean:message key="changepassword.link.message"/>
        </html:link>
&nbsp;&nbsp;
        <html:link forward="logout">
          <bean:message key="label.logout"/>
        </html:link>
      </font>
    </th>
  </tr>
  <logic:notPresent name="userObjectBeanCollection">
  <tr>
    <td colspan="2" >
      <font face=Verdana size=2>
        <bean:message key="excel.message.notpresent"/>
      </font>
    </td>
  </tr>
  </logic:notPresent>
  <logic:present name="userObjectBeanCollection">
  <logic:empty name="userObjectBeanCollection">
  <tr>
    <td colspan="2" >
      <font face=Verdana size=2>
        <bean:message key="excel.message.notpresent"/>
      </font>
    </td>
  </tr>
  </logic:empty>
  <logic:notEmpty name="userObjectBeanCollection">
  <tr>
    <td colspan="2" align="left">
      <font face=Verdana size=2>
        <html:link forward="showgenericreporthelp" target="new">
          <bean:message key="label.help"/>
        </html:link>
      </font>
    </td>
  </tr>
  <html:form action="/runreportrelay.do" target="_blank" onsubmit="return validateExcelForm(this);">
  <tr>
    <td width="50%">
      <font face=Verdana size=2>
        <b><bean:message key="excel.label.query"/>:</b>
      </font>
    </td>
    <td width="50%">
&nbsp;
<%--
      <font face=Verdana size=2>
        <b><bean:message key="label.comment"/>:</b>
      </font>
--%>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <html:textarea property="query" cols="60" rows="10"/>
      </font>
    </td>

  </tr>
  <tr>
    <td colspan="2" >
<table border="0">
<tr>
<td valign="top">
      <font face=Verdana size=2>
        <html:submit property="submit">
          <bean:message key="excel.label.submit"/>
        </html:submit>
      </font>
</td>
<td valign="top">
      <font face=Verdana size=2>
        <html:submit property="saveQuery">
          <bean:message key="excel.label.savequery"/>
        </html:submit>
  </html:form>
      </font>
</td>
<td valign="top">
  <html:form action="/viewquery.do" target="new" >
      <font face=Verdana size=2>
        <html:submit property="viewQuery">
          <bean:message key="excel.label.viewquery"/>
        </html:submit>
      </font>
  </html:form>
</td>
</tr>
</table>
    </td>
  </tr>

  <tr><td colspan="2"><hr width="100%" size="1" NOSHADE></td></tr>
  <html:form action="/showgenericreportinput.do">
<!--
  <tr>
    <td colspan="2">
      <font face=Verdana size=3>
        <b><bean:message key="excel.message.present"/></b>
      </font>
    </td>
  </tr>
-->
        <tr>
          <td colspan="2">
            <font face=Verdana size=2>
              <b>Tables</b>&nbsp;&nbsp;&nbsp;&nbsp;
              <html:submit>
                <bean:message key="excel.detaillabel.submit"/>
              </html:submit>
            </font>
          </td>
        </tr>
  <tr>
    <td valign="top" width="35%">
      <font face=Verdana size=1>
        <html:select property="tableName" size="10">
          <html:options collection="userObjectBeanCollection"
                        property="tableName"
                        labelProperty="tableName"/>
        </html:select>

      </font>
    </td>
    <td width="65%" align="left">
    <logic:present name="userObjectBeanDetailCollection">
            <table border="0">
              <tr>
                <td width="40%">
                  <font face=Verdana size=2>
                    <b><i><bean:message key="viewdetail.label.tablename"/></i></b>
                  </font>
                </td>
                <td width="60%">
                  <font face=Verdana size=2>
                    <b><i><bean:message key="label.comment"/></i></b>
                  </font>
                </td>
              </tr>
              <logic:iterate id="UserObjectBean"
                             name="userObjectBeanDetailCollection"
                             indexId="colCount">
              <tr>
                <td width="40%">
                  <font face=Verdana size=2>
                    <bean:write name="UserObjectBean" property="tableName"/>
                  </font>
                </td>
                <td width="60%">
                  <font face=Verdana size=2>
                    <bean:write name="UserObjectBean" property="comments"/>
                  </font>
                </td>
              </tr>
              <tr>
                <td colspan="3">
                  <hr width="100%" size="1" NOSHADE>
                </td>
              </tr>
              <tr>
                <td width="40%">
                  <font face=Verdana size=2>
                    <b><i><bean:message key="viewdetail.label.columnname"/></i></b>
                  </font>
                </td>
                <td width="20%">
                  <font face=Verdana size=2>
                    <b><i><bean:message key="viewdetail.label.datatype"/></i></b>
                  </font>
                </td>
                <td width="40%">
                  <font face=Verdana size=2>
                    <b><i><bean:message key="label.comment"/></i></b>
                  </font>
                </td>
              </tr>
              <logic:iterate id="UserColCommentsBean"
                             name="UserObjectBean"
                             property="userColCommentsBeanCollection">
              <tr>
                <td width="40%">
                  <font face=Verdana size=2>
                    <bean:write name="UserColCommentsBean" property="columnName"/>
                  </font>
                </td>
                <td width="20%">
                  <font face=Verdana size=2>
                    <bean:write name="UserColCommentsBean" property="dataType"/>
                  </font>
                </td>
                <td width="40%">
                  <font face=Verdana size=2>
                    <bean:write name="UserColCommentsBean" property="comments"/>
                  </font>
                </td>
              </tr>
            </logic:iterate>
            </logic:iterate>
            </table>
          </logic:present>
          </td>
        </tr>

    </html:form>
  </logic:notEmpty>
  </logic:present>
</table>
</body>
<%--
<html:javascript formName="excelForm" dynamicJavascript="true" staticJavascript="true"/>
--%>
</html:html>
