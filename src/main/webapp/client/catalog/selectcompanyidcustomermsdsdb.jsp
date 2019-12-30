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
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<title><fmt:message key="label.selectcatalog" /></title>
<script language="JavaScript" type="text/javascript">

windowCloseOnEsc = true;

function load()
{
	var v = opener.jsonobj;
	var selectbox = document.getElementById("selectedCompanyIdCustomerMsdsDb");
	for(var i = 0; i < v.length; i++)
	{
		var optn = document.createElement("OPTION");
		optn.text = v[i].catalogDesc;
		optn.value = v[i].companyIdCustomerMsds;
		selectbox.options.add(optn);
	}
}
function selectedOption()
{
	if($v("selectedCompanyIdCustomerMsdsDb") == "")
	{
		alert("<fmt:message key="label.selectcatalog"/>");
		return false;
	}
	else
	{
		var selectedCompanyIdCustomerMsdsDb = $v("selectedCompanyIdCustomerMsdsDb");
		eval('opener.parent.${param.callBack}(selectedCompanyIdCustomerMsdsDb)'); 
		
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
<body onload="load();" onunload="opener.parent.parent.closeTransitWin();">
 <table id="outer" cellpadding="0" cellspacing="0">
  <tr><td id="middle">
   <div id="centered" style="margin-left: auto; margin-right:auto;text-align: center;">
    		<fmt:message key="label.selectcatalog" />:&nbsp;
		<select name="selectedCompanyIdCustomerMsdsDb" id="selectedCompanyIdCustomerMsdsDb" class="selectBox" >
			<option value = ""><fmt:message key="label.pleaseselect" /></option>
		</select>&nbsp;
		<a href="#" onclick="selectedOption();"><fmt:message key="label.returncatalog" /></a>
   </div>
  </td></tr>
 </table>
</body>
</html>