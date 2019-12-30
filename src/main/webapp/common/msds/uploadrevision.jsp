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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<script type="text/javascript" src="/js/common/fileUpload/validatefileextension.js"></script>

<html:html lang="true">
<head>
<script LANGUAGE = "JavaScript">
<!--

var messagesData = new Array();
messagesData={
filetypenotallowed:"<fmt:message key="label.filetypenotallowed"/>",
pleaseuploadfile:"<fmt:message key="label.pleaseuploadfile"/>"
};

function submitRequest() {
	$("fileName").value = $v("theFile");
	if($("fileName") == null || $v("fileName").length == 0) {
		alert(messagesData.pleaseuploadfile);
		return false;
	}

	return validateFileExtension();
}

if('${done}' == 'OK') {
	alert('<fmt:message key="label.successfulupload"/>');
	window.close();
}
// -->
</script>
</head>
<body>
<tcmis:form action="/uploadrevision.do" onsubmit="return submitSearchOnlyOnce();" enctype="multipart/form-data">



<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
	<html:errors/>
	<c:if test="${done == 'OK'}">
		<fmt:message key="label.successfulupload"/>
	</c:if>
	<c:if test="${!empty errMsg}">
		${errMsg}
	</c:if>
</div>


<c:set var="module">
	<tcmis:module/>
</c:set>
<table width="100%" class="tableSearch" border="0" cellpadding="0" cellspacing="0">
<tr>
  <td width="1%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.materialid"/>:&nbsp;</td>
  <td width="50%" class="optionTitleLeft">${param.materialId}</td>
</tr>
<tr>
  <td width="1%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.client"/>:&nbsp;</td>
  <td width="50%" class="optionTitleLeft">${module}</td>
</tr>
<tr>
  <td width="1%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.revisiondate"/>:&nbsp;</td>
  <td width="50%" class="optionTitleLeft">
  	<input readonly type="text" name="revisionDate" id="revisionDate" class="inputBox"  value="" onClick="return getCalendar(document.msdsFileUploadForm.revisionDate);"
		        maxlength="10" size="9"/>
  </td>
</tr>
<tr>
  <td width="1%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.file"/>:<span style='font-size:12.0pt;color:red'>*</span> &nbsp;</td>
  <td width="50%" class="optionTitleLeft" >
 <%--  <html:file property="theFile" styleId="theFile"  value="c:\consignmentcount.csv" size="50" styleClass="HEADER"/> --%>
  	<input type="file" id="theFile" name="theFile" class="inputBox" size="60" value="">
  </td>
</tr>
<tr>
  <td colspan="2" class="optionTitleBoldLeft" nowrap>
	<BR><BR><fmt:message key="label.provideinfomsg"/>
  </td>
</tr>
<tr>
  <td width="1%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.name"/>:&nbsp;</td>
  <td width="50%" class="optionTitleLeft"><INPUT TYPE="text" NAME="name" ID="name" class="inputBox" value="" size="40" MAXLENGTH="50"></td>
</tr>
<tr>
  <td width="1%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.phone"/>:&nbsp;</td>
  <td width="50%" class="optionTitleLeft"><INPUT TYPE="text" NAME="phone" ID="phone" class="inputBox" value="" size="11" MAXLENGTH="20"></td>
</tr>
<tr>
  <td width="1%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.email"/>:&nbsp;</td>
  <td width="50%" class="optionTitleLeft"><INPUT TYPE="text" NAME="email" ID="email" value="" class="inputBox" size="40" MAXLENGTH="50"></td>
</tr>
<tr>
  <td colspan="2" class="optionTitleBoldLeft" nowrap><fmt:message key="label.comments"/>:&nbsp;</td>
</tr>
<tr>
  <td colspan="2" nowrap><TEXTAREA name="comments" class="inputBox" rows="15" cols="50"></TEXTAREA></td>
</tr>
</table>
 
<BR>
<INPUT type="submit" value="Submit" name="Submit" onclick="return submitRequest();">&nbsp;&nbsp;
<INPUT type="reset">


     
     
     
     
     
    </div> <%-- boxhead Ends --%>

	</div> 
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>












<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;"> 
	<INPUT TYPE="hidden" NAME="uAction" VALUE="submitRevision">
	<INPUT TYPE="hidden" NAME="materialId" VALUE="${param.materialId}">
	<INPUT TYPE="hidden" NAME="client" VALUE="${module}">
	<INPUT TYPE="hidden" NAME="facility" VALUE="${param.facility}">
	<INPUT TYPE="hidden" NAME="catpartno" VALUE="${param.catpartno}">
	<INPUT TYPE="hidden" ID="fileName" NAME="fileName" VALUE="">
</div> 
 
</tcmis:form>
</body>
</html:html>
