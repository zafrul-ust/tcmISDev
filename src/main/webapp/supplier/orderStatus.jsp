<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.tcmis.common.ui.web.db.RowViewBean,
                 com.tcmis.common.ui.web.db.FieldViewBean,
                 com.tcmis.common.ui.web.db.HeaderBean" %>

<html:html lang="true">
<head>
  <title>Haas TCM</title>
  <link rel="STYLESHEET" type="text/css" href="/css/global.css">
</head>

<body>

<table width="100%">
<tr>
    <%-- Banner --%>
    <td width="100%">
      <img src="/images/tcmtitlegif.gif">
    </td>
</tr>
</table>


<br>
<table width='100%'>

<logic:present name="AcknowledgementBean">
  <tr>
    <logic:iterate id="header" name="AcknowledgementBean" property="headersIter" type="HeaderBean">
    <td class="heading"><bean:write name="header" property="header"/></td>
    </logic:iterate>
  </tr>

  <logic:iterate id="row" name="AcknowledgementBean" property="rowsIter" type="RowViewBean">
  <tr>
     <logic:iterate id="field" name="row" property="fieldsIter" type="FieldViewBean">
     <td class='<bean:write name="field" property="style"/>'>
        <bean:write name="field" property="value"/>
     </td>
     </logic:iterate>
  </tr>
  <tr><td>&nbsp;</td></tr>
  </logic:iterate>
</logic:present>

</table>

<br>

</body>

</html:html>
