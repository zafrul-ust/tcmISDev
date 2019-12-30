<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon"
	href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%>
<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<%@ include file="/common/rightclickmenudata.jsp"%>
<title><fmt:message key="label.shippingname" /> <fmt:message key="label.select" /></title>
<script language="JavaScript" type="text/javascript">
function load()
{
	var v = window.opener.shippingNames;
	window.opener.showTransitWin();
	var selectbox = document.getElementById("selectNames");
	for(var i = 0; i < v.length; i++)
	{
		var optn = document.createElement("OPTION");
		optn.text = v[i];
		optn.value = v[i];
		selectbox.options.add(optn);
	}
}
function selectedOption()
{
	if($v("selectNames") == "")
	{
		alert("<fmt:message key="label.validvalues"/>" + " <fmt:message key="label.shippingname"/>");
		return false;
	}
	else
	{
		window.opener.properShippingName = $v("selectNames");
		//window.opener.closeTransitWin();
		window.opener.returnValues();
		window.close();
	}
}

</script>
<style type="text/css" media="screen">
  body, html {height: 100%; padding: 0px; margin: 0px;}
  #outer {width: 100%; height: 80%; overflow: visible; padding: 0px; margin: 0px;}
  #middle {vertical-align: middle}
  #centered 
 </style>
</head>
<body onload="load()" onunload="opener.closeTransitWin()">
 <table id="outer" cellpadding="0" cellspacing="0">
  <tr><td id="middle">
   <div id="centered" style="margin-left: auto; margin-right:auto;text-align: center;">
    		<fmt:message key="label.selectshippingname" />:&nbsp;
		<select name="selectNames" id="selectNames" class="selectBox" >
			<option value = ""><fmt:message key="label.pleaseselect" /></option>
		</select>&nbsp;
		<a href="#" onclick="selectedOption();"><fmt:message key="label.selectedshippingname" /></a>
   </div>
  </td></tr>
 </table>
</body>
</html>