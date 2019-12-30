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
<html:form action="/tiertwo3.do" onsubmit="return validateTiertwoForm(this);">
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
        <bean:write name="TierTwoInputBean" property="locationId"/>
        <html:hidden name="TierTwoInputBean" property="locationId"/>
      </font>
    </td
  </tr>
<%-- third section of page, only shows after location is picked --%>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.street"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <bean:write name="TierTwoInputBean" property="street"/>
          <html:hidden name="TierTwoInputBean" property="street"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.city"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <bean:write name="TierTwoInputBean" property="city"/>
          <html:hidden name="TierTwoInputBean" property="city"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.county"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <bean:write name="TierTwoInputBean" property="county"/>
          <html:hidden name="TierTwoInputBean" property="county"/>
      </font>
    </td>
  </tr>

  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.state"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <bean:write name="TierTwoInputBean" property="state"/>
          <html:hidden name="TierTwoInputBean" property="state"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.zip"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <bean:write name="TierTwoInputBean" property="zip"/>
          <html:hidden name="TierTwoInputBean" property="zip"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.siccode"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
        <logic:present name="sicBeanCollection" scope="request">
          <html:select property="sicCode">
            <html:options collection="sicBeanCollection"
                          property="sicCode"
                          labelProperty="sicCode"/>
          </html:select>
        </logic:present>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.dunandbradnumber"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text name="TierTwoInputBean" property="dunAndBradstreetNumber"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <b><bean:message key="tiertwo.header.owner"/></b>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.name"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="ownerOperatorName"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.phone"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="ownerOperatorPhone"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.owneraddress"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="ownerOperatorMail"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
        <b><bean:message key="tiertwo.header.contact"/></b>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.name"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergencyName1"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.title"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergencyTitle1"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.phone"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergencyPhone1"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.emergency24hrphone1"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergency24HrPhone1"/>
      </font>
    </td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.name"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergencyName2"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.title"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergencyTitle2"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="label.phone"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergencyPhone2"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.emergency24hrphone2"/>:
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="emergency24HrPhone2"/>
      </font>
    </td>
  </tr>
  <tr>
    <td >
      <font face=Verdana size=2>
        <bean:message key="tiertwo.label.datesigned"/>
      </font>
    </td>
    <td>
      <font face=Verdana size=2>
          <html:text property="dateSigned"/>
      </font>
    </td>
  </tr>

  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
          <bean:message key="tiertwo.label.identical"/>
          <html:checkbox property="identicalToPriorYear" value="Y"/>
          <html:hidden property="process" value="Y"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
          <bean:message key="tiertwo.label.siteplanattached"/>
          <html:checkbox property="sitePlanAttached" value="Y"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
          <bean:message key="tiertwo.label.sitecoordabbrattached"/>
          <html:checkbox property="siteCoordAbbrAttached" value="Y"/>
      </font>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <font face=Verdana size=2>
          <bean:message key="tiertwo.label.dikedescattached"/>
          <html:checkbox property="dikeDescAttached" value="Y"/>
      </font>
    </td>
  </tr>
  </logic:present>
  <tr>
    <td >
      <font face=Verdana size=2>
        <html:submit property="submit">
          <bean:message key="tiertwo.button.generate"/>
        </html:submit>
      </font>
    </td>
    <td >
      <font face=Verdana size=2>
        <html:submit property="submitSave">
          <bean:message key="label.save"/>
        </html:submit>
      </font>
    </td>
  </tr>
</table>
</html:form>
</body>
<%--
<html:javascript formName="tiertwoForm" dynamicJavascript="true" staticJavascript="true"/>
--%>
</html:html>





