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
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",validvalues:"<fmt:message key="label.validvalues"/>",
name:"<fmt:message key="label.name"/>",document:"<fmt:message key="label.document"/>",file:"<fmt:message key="label.file"/>",
pleaseuploadxlsfile:"<fmt:message key="label.uploadexcelfile"/>",
date:"<fmt:message key="label.date"/>",type:"<fmt:message key="label.type"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function showMessages()
{
  	if (showErrorMessage)
  	{
	    var errorMessagesArea = document.getElementById("errorMessagesArea");
	    errorMessagesArea.style.display="";
    }
}

function inputValidation() {
	var extension = $v('theFile').split('.').pop();
	if( $v('theFile') == '' || ( extension != 'xlsx' && extension != 'xls')) {
		alert(messagesData.pleaseuploadxlsfile);
		return false;
	}
	return true;
}

function doCreateTemplate() 
{
	$('userAction').value = 'getUploadNewBinsTemplate';
	document.newBinsUploadForm.target='uploadnewbinstemplate';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','uploadnewbinstemplate','650','600','yes');
	document.newBinsUploadForm.submit();

}

function uploadNewBins()
{
	$('userAction').value = 'uploadNewBins';
	if(showErrorMessage)$('errorMessagesAreaBody').innerHTML='';
	return inputValidation();	
}

// -->
</script>

<title>
 <fmt:message key="hubbin.new.title"/>
</title>
</head>
<body bgcolor="#ffffff" onload="showMessages()">
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaBody" class="bd">
 		<c:choose>
  			<c:when test="${empty errorMessage  && result != null}">
   				<fmt:message key="hubbin.exceluploadsuccess"/>
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
<tcmis:form action="/newbinsupload.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">

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
<fmt:message key="label.file"/>:
</td>
<td width="30%" class="optionTitleBoldLeft">
 <html:file property="theFile" styleId="theFile" value="" size="50" />
</td>
</tr>

<tr>
<td colspan="2" class="optionTitleBoldLeft">
 <input type="submit" name="uploadButton" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
  value="<fmt:message key="label.uploadfile"/>" onclick="return uploadNewBins();"/>
  <input name="createNewTemplateBtn" type="button" class="inputBtns" value="<fmt:message key="label.uploadbinstemplate"/>" id="createNewTemplateBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return doCreateTemplate()"/>
  <input type="hidden" name="branchPlant" id="branchPlant" value="<c:out value="${param.branchPlant}"/>"/>
  <input type="hidden" name="userAction" id="userAction" value=""/>
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
