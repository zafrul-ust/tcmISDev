<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<tcmis:loggedIn indicator="true" forwardPage="/ray/index.jsp"/>
<html:html lang="true">
<head>
<title>
  <bean:message key="excel.title"/>
</title>
</head>
<body bgcolor="white">

<html:errors/>
<h3>
  <html:img src="/images/logo_s.gif" border="1" align="top"/>
  <font face=Verdana size=2>
    <bean:message key="label.haastcm"/>
  </font>
</h3>

<table border="0" width="100%">
  <tr>
    <th width="50%" align="left">
      <bean:message key="viewquery.header"/>
    </th>
    <th width="50%" align="right">
      <font face=Verdana size=2>
        <html:link forward="logout">
          <bean:message key="label.logout"/>
        </html:link>
      </font>
    </th>
  </tr>
  <logic:notPresent name="usersSavedQueriesBeanCollection">
  <tr>
    <td colspan="2" align="left">
      <font face=Verdana size=2>
        <bean:message key="viewquery.message.notpresent"/>
      </font>
    </td>
  </tr>
  </logic:notPresent>
  <logic:present name="usersSavedQueriesBeanCollection">
  <logic:empty name="usersSavedQueriesBeanCollection">
  <tr>
    <td colspan="2" align="left">
      <font face=Verdana size=2>
        <bean:message key="viewquery.message.notpresent"/>
      </font>
    </td>
  </tr>
  </logic:empty>
  <logic:notEmpty name="usersSavedQueriesBeanCollection">
  <tr>
   <td width="40%" align="left">
     <font face=Verdana size=2>
       <b><i><bean:message key="viewquery.label.queryname"/></i></b>
     </font>
   </td>
   <td colspan="2" width="60%" align="left">
     <font face=Verdana size=2>
       <b><i><bean:message key="viewquery.label.querytext"/></i></b>
     </font>
   </td>
  </tr>
  <logic:iterate id="UserSavedQueriesBean" name="usersSavedQueriesBeanCollection">
  <tr>
    <td align="left">
      <font face=Verdana size=3>
        <bean:write name="UserSavedQueriesBean" property="queryName"/>
      </font>
    </td>
    <td align="left">
      <font face=Verdana size=3>
        <html:textarea name="UserSavedQueriesBean"
                       property="query"
                       cols="60"
                       rows="3"
                       readonly="true"/>
      </font>
    </td>
    <td align="left">
      <font face=Verdana size=3>
        <html:form action="/showupdatequery.do">
        <html:hidden name="UserSavedQueriesBean" property="queryName"/>
        <html:submit>
          <bean:message key="label.edit"/>
        </html:submit>
        </html:form>
      </font>
    </td>
    <td align="left">
      <font face=Verdana size=3>
        <html:form action="/deletequery.do">
        <html:hidden name="UserSavedQueriesBean" property="queryName"/>
        <html:submit>
          <bean:message key="label.delete"/>
        </html:submit>
        </html:form>
      </font>
    </td>
    <td align="left">
      <font face=Verdana size=3>
        <html:form action="/runreportrelay.do" target="_blank">
        <html:hidden name="UserSavedQueriesBean" property="query"/>
        <html:submit property="submit">
          <bean:message key="excel.label.submit"/>
        </html:submit>
        </html:form>
      </font>
    </td>
  </logic:iterate>
  </logic:notEmpty>
  </logic:present>
</table>
</body>
</html:html>
