<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<tcmis:fontSizeCss />
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%@ include file="/common/locale.jsp" %>

<script type="text/javascript" src="/js/common/commonutil.js"></script>

<html:html lang="true">

<title>
<fmt:message key="manualConsumptionImport"/>
</title>

<head>
<script LANGUAGE = "JavaScript">
<!--

var messagesData = new Array();
messagesData={
pleaseuploadfile:"<fmt:message key="label.pleaseuploadfile"/>",
pleaseuploadcorrectfile:"<fmt:message key="label.pleaseuploadcorrectfile"/>"
};

function submitRequest() {
	$("fileName").value = $v("theFile");
	
	var splitResults = $v("fileName").split(".");
	if($("fileName") == null || $v("fileName").length == 0) {
		alert(messagesData.pleaseuploadfile);
		return false;
	}
	else if($("fileName") != null && (splitResults[splitResults.length-1] != "csv" && splitResults[splitResults.length-1] != "txt")) {
		alert(messagesData.pleaseuploadcorrectfile);
		document.fileUploadForm.reset();
		return false;
	}
	else {
		$("uAction").value = "save";
		document.fileUploadForm.submit();
	}
}

if('${done}' == 'OK') {
	alert('${result}');
}

// -->
</script>
</head>
<body>
<tcmis:form action="/manualconsumptionimport.do" onsubmit="return submitSearchOnlyOnce();" enctype="multipart/form-data">
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<br/>
&nbsp;File: <input type="file" id="theFile" name="theFile" class="inputBox" size="60" value="">
<br/>
<br/>
&nbsp;<INPUT type="button" value="Submit" name="Submit" onclick="return submitRequest();">&nbsp;&nbsp;
<INPUT type="reset">
</div>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;"> 
	<INPUT ID="uAction" NAME="uAction" VALUE="">
	<INPUT ID="fileName" NAME="fileName" VALUE="">
</div> 
 
</tcmis:form>
</body>
</html:html>
