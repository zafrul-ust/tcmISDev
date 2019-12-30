<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %> 
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<!-- Add any other javascript you need for the page here -->
<script src="/js/hub/receiptdocument.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">
<!--
var windowCloseOnEsc = true;

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",validvalues:"<fmt:message key="label.validvalues"/>",
name:"<fmt:message key="label.name"/>",document:"<fmt:message key="label.document"/>",file:"<fmt:message key="label.file"/>",
date:"<fmt:message key="label.date"/>",type:"<fmt:message key="label.type"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
uploadfile:"<fmt:message key="error.uploadfile"/>",required:"<fmt:message key="errors.required"/>"};

function showMessages()
{
  	if (showErrorMessage)
  	{
	    var errorMessagesArea = document.getElementById("errorMessagesArea");
	    errorMessagesArea.style.display="";
    }
}

function inputValidation() {
	document.getElementById('uAction').value = 'uploadDocument';
	var thefile = document.getElementById('theFile');
	if (document.getElementById('contactLogId').value == null ||
			document.getElementById('contactLogId').value == "") {
		alert("There is no contact log associated with this document.");
		return false;
	}
	else if ($v('theFile') == "") {
		alert(messagesData.uploadfile);
		return false;
	}
	else if ($v('documentName') == "") {
		alert(messagesData.required.replace("{0}", messagesData.name));
		return false;
	}
	return true;
}

function myUnload() {
	var loc = parent.opener.location.pathname;
	var params = parent.opener.location.search;
	parent.opener.location.assign(loc+params);
	window.close();
}

// -->
</script>

<title>
 <fmt:message key="label.uploadfile"/>
</title>
</head>
<body bgcolor="#ffffff" onload="showMessages()" onunload="myUnload()">
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaBody" class="bd">
 		<c:choose>
  			<c:when test="${empty errorMessage  && result != null}">
   				<fmt:message key="label.successfulupload"/>
  			</c:when>
  			<c:when test="${!empty errorMessage}">
   				${errorMessage}
  			</c:when>
 		</c:choose>
	</div>
</div>
<script type="text/javascript">
<c:choose>
  <c:when test="${errorMessage == null && result != null}">
   showErrorMessage = true;
  </c:when>
  <c:when test="${errorMessage != null}">
   showErrorMessage = true;
  </c:when>
  <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->
<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">
<tcmis:form action="/contactlogdocumentupload.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.name"/>:
</td>
<td width="30%" class="optionTitleBoldLeft" colspan=2>
 <input id="documentName" name="documentName" type="text" class="inputBox"/>
</td>
</tr>
<tr>
<td width="5%" class="optionTitleBoldRight">
 <fmt:message key="label.date"/>:
</td>
<td width="30%" class="optionTitleBoldLeft" colspan=2>
 <input type="text" readonly name="documentDate" id="documentDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}" />" 
   onClick="return getCalendar(document.genericForm.documentDate, null, null, null, document.genericForm.documentDate.value);" size="8" maxlength="10" class="inputBox pointer">
</td>
</tr>
<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.file"/>:
</td>
<td width="30%" class="optionTitleBoldLeft" colspan=2>
 <html:file property="theFile" styleId="theFile" value="" size="50"/>
</td>
</tr>
<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.type"/>:
</td>
<td width="30%" class="optionTitleBoldLeft" colspan=2>
 <select id="contactDocumentType" name="contactDocumentType" class="selectBox">
  <c:forEach var="vvMsdsDocumentTypeBean" items="${vvContactDocumentTypeBeanCollection}" varStatus="status">
    <option value="<c:out value="${status.current.documentType}"/>"><c:out value="${status.current.documentTypeDesc}"/></option>
  </c:forEach>
 </select>
</td>
</tr>
<tr>
<td></td>
<td class="optionTitleBoldLeft">
 <input type="submit" name="uploadButton" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
  value="<fmt:message key="label.uploadfile"/>" onclick="return inputValidation();"/>
</td>
<td>
 <input type="button" name="cancelButton" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
  value="<fmt:message key="label.cancel"/>" onclick="window.close();"/>
</td>
</tr>


</table>
</div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	 <input name="uAction" id="uAction" type="hidden" value=""/>
	 <input name="contactLogId" id="contactLogId" type="hidden" value="${contactLogId}"/>
</div>
 
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->
<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
</body>
</html:html>
