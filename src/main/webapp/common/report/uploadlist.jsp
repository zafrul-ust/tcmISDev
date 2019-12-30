<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/common/report/newlist.js"></script>


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="label.uploadlist"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
name:"<fmt:message key="label.name"/>",
file:"<fmt:message key="label.file"/>",    
desc:"<fmt:message key="label.description"/>",
pleaseuploadcorrectfile:"<fmt:message key="label.pleaseuploadcorrectfile"/> (xls)",
maximumallowedtext:"<fmt:message key="label.maximumallowedtext"><fmt:param>${50}</fmt:param></fmt:message>"

};

function showExample() {
	openWinGeneric("/template/chemlistexample.xls","uploadListExample","250","150","yes","80","80");
}
function checkInput()
{
 	$('documentName').value = $v('theFile');
 	var extension = $v("documentName").split(".").pop();
	var errorMessage = messagesData.validvalues+"\n\n";
	var errorCount = 0;
	
	var theFile = document.getElementById("theFile");
	if (theFile.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.file ;
	 errorCount = 1;
	}
	else if($("documentName") != null && ("xls" != extension && "xlsx" != extension && "csv" != extension)) {
		alert(messagesData.pleaseuploadcorrectfile);
		return false;
	}
	
	if (errorCount >0)
	{
	  alert(errorMessage);
	  return false;
	}
	else
	{
	  return true;
	}
}


var showErrorMessage = false;
useLayout=false;
var windowCloseOnEsc = true;

<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>


var closeNewListWin;

<c:choose>
<c:when test="${closeNewListWin == 'Y'}">
closeNewListWin = true;
</c:when>
<c:otherwise>
closeNewListWin = false;
</c:otherwise>
</c:choose>

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="newListWinClose();" onunload="parent.opener.stopShowingWait();">

<tcmis:form action="/uploadlist.do" onsubmit="return submitSearchOnlyOnce();" enctype="multipart/form-data">

<div id="transitPage" style="display: none;text-align:center;">
<p><br><br><br><fmt:message key="label.pleasewait"/></p>
</div>

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>

<div id="resultGridDiv">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
  <div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">

<fieldset>
<legend><fmt:message key="label.uploadlist"/>:</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
   <tr>
     <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.file"/>:</td>
     <td width="85%" class="optionTitleBoldLeft">
      <html:file property="theFile" styleId="theFile" value="c:\consignmentcount.csv" size="40" styleClass="HEADER"/>
      <input type="hidden" name="documentName" id="documentName" value=""/>
     </td>
    </tr>
    <tr>
     <td width="5%" colspan="2" class="optionTitleBoldLeft"><%--<fmt:message key="label.consignmentcountfilepath"/>--%></td>
    </tr>
    <tr>
     <td width="5%" colspan="2" class="optionTitleBoldLeft">
       <input name="submitSave" id="submitSave" type="submit" class="inputBtns" value="<fmt:message key="label.process"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return checkInput()">
       <input name="example" id="example" type="button" class="inputBtns" value="<fmt:message key="label.example"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return showExample()">
     </td>
    </tr>
</table>
</fieldset>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>

</div><!-- Result Frame Ends -->


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" value="processUpload"/>
<input name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
<input name="newListId" id="newListId" type="hidden" value="${newListId}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>
