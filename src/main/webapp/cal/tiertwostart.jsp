<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html lang="true">
<head>
<title>
  <bean:message key="tiertwo.title"/>
</title>
</head>
<body bgcolor="white">

<html:errors/>
<h3>
  <html:img src="/images/logo_s.gif" border="1" align="top"/>
  <font face=Verdana size=2>
    <bean:message key="tiertwo.title"/>
  </font>
</h3>
<html:form action="/tiertwo1.do">
<table border="0">
  <tr>
    <th >
      <bean:message key="tiertwo.header"/>
    </th>
    <th>
      <font face=Verdana size=2>
        <html:link forward="logout">
          <bean:message key="label.logout"/>
        </html:link>
      </font>
    </th>
  </tr>
<%-- first section, user picks facility and year --%>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.facility"/>
      </font>
    </td>
    <td >
      <font face=Verdana size=2>
        <logic:present name="facilityBeanCollection" scope="request">
          <html:select property="facilityId">
            <html:options collection="facilityBeanCollection"
                          property="facilityId"
                          labelProperty="facilityId"/>
          </html:select>
        </logic:present>
        <html:hidden property="facilityId" value="Dallas"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.year"/>:
      </font>
    </td>
    <td >
      <font face=Verdana size=2>
          <html:select property="year">
            <html:option value="2004"/>
            <html:option value="2003"/>
            <html:option value="2002"/>
            <html:option value="2001"/>
            <html:option value="2000"/>
          </html:select>
      </font>
    </td
  </tr>

  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <html:submit property="submit">
          <bean:message key="tiertwo.button.next"/>
        </html:submit>
      </font>
    </td>
  </tr>
</table>
</html:form>
</body>
</html:html>





