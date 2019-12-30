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
<html:form action="/tiertwo2.do">
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
  <logic:present name="TierTwoInputBean" scope="request">
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.facility"/>
      </font>
    </td>
    <td >
      <font face=Verdana size=2>
        <bean:write name="TierTwoInputBean" property="facilityId"/>
        <html:hidden name="TierTwoInputBean" property="facilityId"/>
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
        <bean:write name="TierTwoInputBean" property="year"/>
        <html:hidden name="TierTwoInputBean" property="year"/>
        <html:hidden name="TierTwoInputBean" property="dunAndBradstreetNumber"/>
      </font>
    </td
  </tr>
<%-- second section, user picks location based on previously selected facility --%>

  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.location"/>:
      </font>
    </td>
    <td >
      <font face=Verdana size=2>

        <logic:present name="locationBeanCollection" scope="request">
<logic:present name="FacTierIiBean" scope="request">
          <html:select property="locationId" name="FacTierIiBean">
            <html:options collection="locationBeanCollection"
                          property="locationId"
                          labelProperty="locationId"/>
          </html:select>
</logic:present>
<logic:notPresent name="FacTierIiBean" scope="request">
          <html:select property="locationId">
            <html:options collection="locationBeanCollection"
                          property="locationId"
                          labelProperty="locationId"/>
          </html:select>
</logic:notPresent>
        </logic:present>
<%--
          <html:hidden property="locationId" value="Foo"/>
--%>
      </font>
    </td
  </tr>
  </logic:present>
  <tr>
    <td >
      <font face=Verdana size=2>
        <html:submit property="submit" title="foo">
          <bean:message key="tiertwo.button.next"/>
        </html:submit>
      </font>
    </td>
  </tr>
</table>
</html:form>
</body>
</html:html>





