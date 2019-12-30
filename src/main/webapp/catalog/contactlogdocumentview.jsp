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
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<!-- Add any other javascript you need for the page here -->
<%--<script type="text/javascript" src="/js/common/msds/msdsdocument.js"></script>--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>

<title>
 <fmt:message key="label.documentsfor"/> <c:out value="${param.materialId}"/>
</title>

<c:set var="includeDeleted" value='${includeDeleted}'/>
<c:set var="personnelId" value='${personnelId}'/>

<c:set var="updatePermission" value='N'/>
<c:if test="${personnelId != '-1'}" >
<tcmis:facilityPermission indicator="true" userGroupId="msdsDocuments" facilityId="${param.facilityId}">
<c:set var="updatePermission" value='Y'/>
</tcmis:facilityPermission>
</c:if>
<%-- if called from haas module than allow users to update--%>
<c:set var="module"><tcmis:module /></c:set>
<c:if test="${module == 'catalog' || module == 'cv' || module == 'haas'}" >
    <c:set var="updatePermission" value='Y'/>
</c:if>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var windowCloseOnEsc = true;
var children = new Array();

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
viewDocument:"<fmt:message key="label.viewdocument"/>",
deleteDocument:"<fmt:message key="label.deletedocument"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->

	
with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
	aI("text=messagesData.viewDocument;url=javascript:viewDocument();");
	aI("text=messagesData.deleteDocument;url=javascript:deleteDocument();");
}

drawMenus();
	
var config = [
{
  columnId:"permission"
},
{ columnId:"documentType",
  columnName:'<fmt:message key="label.type"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"documentName",
  columnName:'<fmt:message key="label.name"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"personnelName",
  columnName:'<fmt:message key="label.enteredby"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"insertDate",
  columnName:'<fmt:message key="label.dateinserted"/>',
  width:8
},
{ columnId:"documentDate",
  columnName:'<fmt:message key="label.documentdate"/>',
  width:8
},
{
  columnId:"documentId"
},
{
  columnId:"documentUrl"
},
{
  columnId:"contactLogId"
}
];

var documentGridConfig = {
		divName:'contactLogDocumentViewBean',
		beanData:'jsonMainData',
		beanGrid:'documentGrid',
		config:'config',
		rowSpan:false,
		onRowSelect: onDocumentSelected,
		onRightClick:showDocumentMenu,
		submitDefault:true
	};

function onDocumentSelected(rowId, colId){}

function showDocumentMenu(rowId, colId){
	//onMaterialSelected(rowId, colId);
	updateDocumentMenu();
	toggleContextMenu('rightClickMenu');
}

function replaceMenu(menuname,menus){
	var i = mm_returnMenuItemCount(menuname);

	for(;i> 1; i-- )
		mm_deleteItem(menuname,i);

	for( i = 0; i < menus.length; ){
		var str = menus[i];

		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
		if( i == 1 ) mm_deleteItem(menuname,1);
	}
}

function updateDocumentMenu() {
	var menu = new Array();
	
	if (documentGrid.getSelectedRowId() !== undefined &&
			documentGrid.getSelectedRowId() != null) {
		var documentUrl = gridCellValue(documentGrid,documentGrid.getSelectedRowId(),"documentUrl");
		
		menu[menu.length] = "text="+messagesData.viewDocument+" ;url=javascript:viewDocument('"+documentUrl+"');";
	}
	
	menu[menu.length] = "text="+messagesData.deleteDocument+" ;url=javascript:deleteDocument()";
	
	replaceMenu('rightClickMenu', menu);
}

function deleteDocument(contactLogId) {
	var rowLogId = gridCellValue(documentGrid,documentGrid.getSelectedRowId(),"contactLogId");
	var rowDocId = gridCellValue(documentGrid,documentGrid.getSelectedRowId(),"documentId");
	j$.ajax({
		url:"contactlogdocumentview.do",
		cache:false,
		data:{uAction: "deleteDocument", contactLogId:rowLogId, documentId:rowDocId},
		success: function(data) {
			documentGrid.deleteRow(documentGrid.getSelectedRowId());
		}
	});
}

function login() {
	$("uAction").value = "login";
	document.genericForm.submit();
}

function reload() {
	window.location.assign("contactlogdocumentview.do?contactLogId="+$v("contactLogId"));
}

function resultOnLoad(){
	document.getElementById("mainUpdateLinks").style.display = "";
	document.getElementById("contactLogDocumentViewBean").style.display = "";
	initGridWithGlobal(documentGridConfig);
}

function addNewMsdsDocument() {
	var loc = "contactlogdocumentupload.do?contactLogId="+$v("contactLogId");
	children[children.length] = openWinGeneric(loc,"contactLogDocumentUpload","550","200","yes","150","150","20","20","no");
}

function closeAllchildren() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren(); 
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array(); 
}

function viewDocument(doc) {
    if (doc.length > 1) {
	    children[children.length] = openWinGeneric(doc,"VIEW_FILE","800","650","yes");
    }
}

function myUnload() {
	parent.opener.stopShowingWait();
	closeAllchildren();
	// we should never be adding a draft to any contact log that is not a draft
	//parent.opener.setStatusToDraft();
}

</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="resizeFrames()" onunload="myUnload();" onmouseup="toggleContextMenuToNormal();">
<tcmis:form action="/showmsdsdocuments.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv">
<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> 
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="showlegendLink"><fmt:message key="label.documentsfor"/>: <c:out value="${param.contactLogId}" /></span>
      <br /><br />
      <c:if test="${updatePermission == 'Y'}" >
        <a href="#" onclick="addNewMsdsDocument();"><fmt:message key="receiptdocument.button.addnewdocument"/></a>&nbsp;
      </c:if>
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
   <div class="dataContent">
     <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="contactLogDocumentViewBean" style="width:100%; height:200px;" style="display: none;"></div>
<!-- Search results start -->
<c:set var="colorClass" value=''/>
<c:if test="${documentList != null}" >
<script type="text/javascript">

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty documentList}" >
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="contactLogDocumentViewBean" items="${documentList}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>


<c:set var="showUpdateLink" value='Y'/>
<fmt:formatDate var="fmtDocumentDate" value="${status.current.documentDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtInsertDate" value="${status.current.insertDate}" pattern="${dateFormatPattern}"/>

/* fmt:formatDate, to formate your date to locale pattern */
/* pattern="${dateFormatPattern}" or pattern="${dateTimeFormatPattern}" */

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${updatePermission}',
     '${status.current.contactDocumentType}',
	 '<tcmis:jsReplace value="${status.current.documentName}" />',
	 '<tcmis:jsReplace value="${status.current.personnelName}" />',
	 '${fmtInsertDate}',
	 '${fmtDocumentDate}',
	 '${status.current.documentId}','${status.current.documentUrl}','${status.current.contactLogId}'
	  ]}

  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};
</c:if>
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty documentList}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
</c:if>
<!-- Search results end -->
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>
    <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
  </div>
 </div>
</td>
</tr>
</table>

<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="updateDocuments" id="updateDocuments" value="" type="hidden"/>
<input name="showDocuments" id="showDocuments" value="" type="hidden"/>
<input name="companyId" id="contactLogId" value="${param.contactLogId}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>
