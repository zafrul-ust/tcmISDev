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
<table border="0" width="50%">
  <tr>
    <th colspan="2">
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
  <tr>
    <td colspan="3">
      <font face=Verdana size=2>
<logic:equal name="TierTwoInputBean"  property="dataFlag" value="true" scope="request">
        <bean:message key="option.label.messagedata"/>
</logic:equal>
<logic:notEqual name="TierTwoInputBean"  property="dataFlag" value="true" scope="request">
        <bean:message key="option.label.messagenodata"/>
</logic:notEqual>
      </font>
    </td>
  </tr>
  <logic:present name="TierTwoInputBean" scope="request">
  <tr>
    <td colspan="3">
      <font face=Verdana size=2><bean:message key="label.facility"/>:
        <bean:write name="TierTwoInputBean" property="facilityId"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <font face=Verdana size=2><bean:message key="tiertwo.label.year"/>:
        <bean:write name="TierTwoInputBean" property="year"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
<html:form action="/tiertwooption.do">
<html:hidden name="TierTwoInputBean" property="facilityId"/>
<html:hidden name="TierTwoInputBean" property="year"/>
<html:hidden name="TierTwoInputBean" property="locationId"/>
      <font face=Verdana size=2>
        <html:submit property="submitEdit">
          <bean:message key="label.edit"/>
        </html:submit>
      </font>
</html:form>
    </td>
<logic:equal name="TierTwoInputBean"  property="dataFlag" value="true" scope="request">
    <td >
<html:form action="/tiertwooption.do" target="new">
<html:hidden name="TierTwoInputBean" property="facilityId"/>
<html:hidden name="TierTwoInputBean" property="year"/>
<html:hidden name="TierTwoInputBean" property="locationId"/>
      <font face=Verdana size=2>
        <html:submit property="submitView">
          <bean:message key="label.view"/>
        </html:submit>
      </font>
</html:form>
    </td>
    <td >
<html:form action="/tiertwooption.do">
<html:hidden name="TierTwoInputBean" property="facilityId"/>
<html:hidden name="TierTwoInputBean" property="year"/>
<html:hidden name="TierTwoInputBean" property="locationId"/>
      <font face=Verdana size=2>
        <html:submit property="submitPublish">
          <bean:message key="option.button.publish"/>
        </html:submit>
      </font>
</html:form>
    </td>
</logic:equal>

  </tr>
  </logic:present>
</table>
</html:form>
</body>
</html:html>





