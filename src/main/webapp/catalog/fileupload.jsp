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
<script src="/js/common/fileUpload/validatefileextension.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",validvalues:"<fmt:message key="label.validvalues"/>",
name:"<fmt:message key="label.name"/>",document:"<fmt:message key="label.document"/>",file:"<fmt:message key="label.file"/>",
filename:"<fmt:message key="label.filename"/>",pleaseuploadfile:"<fmt:message key="label.pleaseuploadfile"/>",filetypenotallowed:"<fmt:message key="label.filetypenotallowed"/>",
date:"<fmt:message key="label.date"/>",type:"<fmt:message key="label.type"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function showMessages()
{
  	if (showErrorMessage)
  	{
	    var errorMessagesArea = document.getElementById("errorMessagesArea");
	    errorMessagesArea.style.display="";
    }
}

function viewfile() {
    openWinGenericViewFile('/${param.location}/${param.filename}','_view_uploaded_file','650','600','yes');
//    document.genericForm.target='_${param.filename}';
//    var a = window.setTimeout("document.genericForm.submit();",500);
}

function inputValidation() {
	$("errorMessagesArea").style.display="none";
	
	if( $v('filename') == '') {
		alert(messagesData.validvalues +" "+ messagesData.filename);
		return false;
	}
	
	if( $v('theFile') == '') {
		alert(messagesData.pleaseuploadfile);
		return false;
	}

	return true;
}

// -->
</script>

<title>
 <fmt:message key="label.uploadfile"/>
</title>
</head>
<body bgcolor="#ffffff" onload="showMessages()">
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
<tcmis:form action="/fileupload.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">

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
<fmt:message key="label.location"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">
  <select name="location" class="selectBox" id="companyId">
    <option value="msds" <c:if test="${param.location eq 'msds'}">selected</c:if>>MSDS</option>
    <option value="OpSupport/Raytheon/spec_drawings" <c:if test="${param.location eq 'OpSupport/Raytheon/spec_drawings'}">selected</c:if>>Spec</option>      
    <option value="OpSupport/Raytheon/flowdowns" <c:if test="${param.location eq 'OpSupport/Raytheon/flowdowns'}">selected</c:if>>Raytheon Flowdowns</option>
    <option value="OpSupport/USGOV/flowdowns" <c:if test="${param.location eq 'OpSupport/Raytheon/flowdowns'}">selected</c:if>>USGOV Flowdowns</option>
    <option value="OpSupport/UTC/HSD/flowdowns" <c:if test="${param.location eq 'OpSupport/Raytheon/flowdowns'}">selected</c:if>>UTC HSD Flowdowns</option>
    <option value="OpSupport/UTC/PWA/flowdowns" <c:if test="${param.location eq 'OpSupport/Raytheon/flowdowns'}">selected</c:if>>UTC PWA Flowdowns</option>
    <option value="item_mfg_literature" <c:if test="${param.location eq 'item_mfg_literature'}">selected</c:if>>NIST/Mfg Literature</option>
    <option value="item_image" <c:if test="${param.location eq 'item_image'}">selected</c:if>>Item Image</option>
    <option value="templates/labels" <c:if test="${param.location eq 'templates/labels'}">selected</c:if>>Label Template</option>
    <option value="images/buyersig" <c:if test="${param.location eq 'images/buyersig'}">selected</c:if>>Buyer Signatures</option>
    <option value="tcmIS/help" <c:if test="${param.location eq 'tcmIS/help'}">selected</c:if>>Bulletin Images</option> 
    <option value="OpSupport/Dana/flowdowns" <c:if test="${param.location eq 'msds/OpSupport/Dana/flowdowns'}">selected</c:if>>Dana Flowdowns</option>     
 </select>
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.filename"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <input name="filename" id="filename" type="text" class="inputBox" value="" size="30" maxlength="30">
</td>
</tr>

<tr>
<td width="5%" class="optionTitleBoldRight">
<fmt:message key="label.file"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <html:file property="theFile" styleId="theFile" value="" size="50" />
</td>
</tr>

<tr>
<td colspan="2" class="optionTitleBoldLeft">
 <input type="submit" name="uploadButton" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
  value="<fmt:message key="label.uploadfile"/>" onclick="return (inputValidation() && validateFileExtension()) "/>
<c:if test="${! empty param.filename && result != null }">
 <input type="button" name="uploadButton" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
  value="View Uploaded File" onclick="viewfile()"/>
</c:if>
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

</tcmis:form>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
</body>
</html:html>
