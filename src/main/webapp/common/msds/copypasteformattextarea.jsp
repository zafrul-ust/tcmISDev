<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<script language="JavaScript" type="text/javascript">
<!--

// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;

function sendToParent()
{
	var val = $v('formatArea');
	if(val.length > 0)
	{
		var send = val.replace(/\s+/g,',');
		send = send.replace(/(^,?|,?$)/g,'');
		opener.document.getElementById('searchText').value = send;
	}
	window.close();
}
//-->
</script>
</head>
<body>
<table>
<tr>
<td>
<a name="ok" id="ok" href="javascript:sendToParent();"><fmt:message key="label.ok"/></a> | 
<a name="cancel" id="cancel" href="javascript:window.close();"><fmt:message key="label.cancel"/></a>
</td>
</tr>
<tr><td>
<textarea id="formatArea" cols="35" rows="20"></textarea>
</td>
</tr>
</table>
</body>
</html:html>