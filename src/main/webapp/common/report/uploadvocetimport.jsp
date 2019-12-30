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
<fmt:message key="label.uploadvocetdata"/>
</title>

<script type="text/javascript">
<!--

var showErrorMessage = false;
var research = false;
useLayout=false;
var windowCloseOnEsc = true;


var closeNewListWin = false;

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:when test="${ 'OK' == done}">
    showErrorMessage = true;
    research = true;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
name:"<fmt:message key="label.name"/>",
desc:"<fmt:message key="label.description"/>",
maximumallowedtext:"<fmt:message key="label.maximumallowedtext"><fmt:param>${50}</fmt:param></fmt:message>",
pleaseuploadcorrectfile:"<fmt:message key="label.pleaseuploadcorrectfile"/> (csv)",
pleaseuploadfile:"<fmt:message key="label.pleaseuploadfile"/>"

};

function goback() {
	if(research) {
		var target = window.opener;//.parent;
		target.$('uploadSeqId').value="${uploadId}";
		target.$('facilityId').value = $v('facilityId');
		target.$('vocetCategoryId').value = '';
		target.$('entryType').value = '';
		target.$('entryEndDate').value = '';
		target.$('entryStartDate').value = '';
		target.$('searchText').value = '';
		target.submitSearchForm();
	}
		window.close();
}

function showExample() {
	openWinGeneric("/template/ManualVocetTemplate.csv","uploadVocetExample","250","150","yes","80","80");
}

function submitRequest() {
	$("documentName").value = $v("theFile");
	
	$("uAction").value = "save";
	
	var splitResults = $v("documentName").split(".");
	if($("documentName") == null || $v("documentName").length == 0) {
		alert(messagesData.pleaseuploadfile);
		return false;
	}
	else if($("documentName") != null && (splitResults[splitResults.length-1] != "csv" && splitResults[splitResults.length-1] != "txt")) {
		alert(messagesData.pleaseuploadcorrectfile);
		document.forms[0].reset();
		return false;
	}
	return true;
}


/*
<c:if test="${closeNewListWin == 'Y'}" >
closeNewListWin = true;

// content of your Javascript goes here
var jsonobj = new Array();
<c:forEach var="listBean" items="${listColl}" varStatus="status">
	
	jsonobj[${status.index}]={
		  	   	   listId:		'${status.current.listId}',
		  	   	   listName:		'<tcmis:jsReplace value="${status.current.listName}" />',
		  	   	   listDescription:		'<tcmis:jsReplace value="${status.current.listDescription}" />'
			   		   
	};
</c:forEach>

<c:if test="${param.callback != null}">
eval('parent.${param.callback}()');
</c:if>
  
  opener.updateListDropDown(jsonobj,${newListId});

</c:if>
*/
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="newListWinClose();" onunload="parent.opener.stopShowingWait();">

<tcmis:form action="/uploadvocetimport.do" onsubmit="return submitSearchOnlyOnce();" enctype="multipart/form-data">

<div id="transitPage" style="display: none;text-align:center;">
<p><br><br><br><fmt:message key="label.pleasewait"/></p>
</div>

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
<br/>
<c:if test="${!empty uploadId}">
Upload Id: ${uploadId}
<br/>
</c:if>
<input type="button" name="closeme" id="closeme" value="close" onclick="goback()"/>"
</div>
<!-- Error Messages Ends -->


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
<legend><fmt:message key="label.uploadvocetdata"/>:</legend>
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
       <input name="submitSave" id="submitSave" type="submit" class="inputBtns" value="<fmt:message key="label.process"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitRequest()">
       <input name="example" id="example" type="button" class="inputBtns" value="<fmt:message key="label.examplefile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return showExample()">
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
<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>
<input name="documentName" id="documentName" value=""/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>
