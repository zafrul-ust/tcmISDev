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
<script type="text/javascript" src="/js/common/msds/msdsdocument.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<c:set var="pageTitle" value='${param.materialId}'/>
<c:if test="${!empty param.customerMixtureNumber}" >
	<c:set var="pageTitle" value='${param.customerMixtureNumber}'/>
</c:if>

<title>
 <fmt:message key="label.documentsfor"/> <c:out value="${pageTitle}"/>
</title>

<c:set var="includeDeleted" value='${includeDeleted}'/>
<c:set var="personnelId" value='${personnelId}'/>

<c:set var="updatePermission" value='N'/>
<c:if test="${param.readonly ne 'Y'}">
<c:if test="${personnelId != '-1'}" >
<tcmis:facilityPermission indicator="true" userGroupId="msdsDocuments" facilityId="${param.facilityId}">
<c:set var="updatePermission" value='Y'/>
</tcmis:facilityPermission>
</c:if>
<%-- if called from haas module than allow users to update--%>
<c:set var="module"><tcmis:module /></c:set>
<c:if test="${module eq 'haas' || module eq 'catalog' || module eq 'cv'}" >
    <c:set var="updatePermission" value='Y'/>
</c:if>
</c:if>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
norowselected:"<fmt:message key="error.norowselected"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->

	
	with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.viewdocument"/>;url=javascript:viewDocument();");
    }

drawMenus();
	
var config = [
{
  columnId:"permission"
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.delete"/>',
  width:4,
  type:'hch',
  align:'center'  
},
{ columnId:"documentTypeName",
  columnName:'<fmt:message key="label.type"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"documentName",
  columnName:'<fmt:message key="label.name"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"enteredByName",
  columnName:'<fmt:message key="label.enteredby"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"enteredDate",
  columnName:'<fmt:message key="label.entereddate"/>',
  width:8,
  hiddenSortingColumn:'hiddenDocumentDateTime',
  sorting:'int'
},
{
  columnId:"hiddenDocumentDateTime",
  sorting:'int'
},

{ columnId:"deletedByName",
  <c:if test="${!empty includeDeleted}" >
  columnName:'<fmt:message key="label.deletedby"/>',
  </c:if>
  tooltip:"Y",
  width:12
},
{ columnId:"deleteDate",
  <c:if test="${!empty includeDeleted}" >
  columnName:'<fmt:message key="label.deleteddate"/>',
  </c:if>
  width:8,
  hiddenSortingColumn:'hiddenDeletedDateTime',
  sorting:'int'
},
{
  columnId:"hiddenDeletedDateTime",
  sorting:'int'
},
{
    columnId:"globalDocument",
    <c:if test="${empty param.companyId && param.calledFrom != 'kitManagement'}">
        columnName:'<fmt:message key="label.global"/>',
    </c:if>
    width:5
},
{
  columnId:"documentId"
},
{
  columnId:"documentUrl"
},
{
  columnId:"materialId"
},
{
  columnId:"companyId"
}
]

function login() {
	$("uAction").value = "login";
	document.genericForm.submit();
}


</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="resizeFrames()" onunload="closeAllchildren();">
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
<div id="resultGridDiv" style="display: none;">
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
      <span id="showlegendLink"><fmt:message key="label.documentsfor"/>: <c:out value="${pageTitle}" /></span>
      <br /><br />
      <%-- this is not working as expected so I am commenting it out for now tngo Jul 12 2013
      <c:if test="${personnelId == '-1'}" >
      	<a href="#" onclick="login();"><fmt:message key="label.login"/></a>
      </c:if>
      --%>
      <c:if test="${!empty msdsDocumentViewBeanCollection}" >
      <c:if test="${updatePermission == 'Y'}" >
      <a href="#" onclick="update();"><fmt:message key="label.update"/></a>
      </c:if>
      </c:if>
      <c:if test="${updatePermission == 'Y'}" >
      | <a href="#" onclick="addNewMsdsDocument();"><fmt:message key="receiptdocument.button.addnewdocument"/></a>&nbsp;|
      </c:if>&nbsp;
       <input type="checkbox" name="includeDeleted" value="" id="includeDeleted" onclick="includeDeletedDocuments();" <c:if test="${!empty includeDeleted}" > checked </c:if> />
      <span class="optionTitleBoldLeft"><fmt:message key="label.includedeleteddoc"/></span>&nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
   <div class="dataContent">
     <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="msdsDocumentViewBean" style="width:100%;height:600px;" style="display: none;"></div>
<!-- Search results start -->
<c:set var="colorClass" value=''/>
<c:if test="${msdsDocumentViewBeanCollection != null}" >
<script type="text/javascript">

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty msdsDocumentViewBeanCollection}" >
/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="msdsDocumentViewBean" items="${msdsDocumentViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>


<c:set var="showUpdateLink" value='Y'/>

<%-- if user has permission but the record was deleted than read only--%>
<c:if test="${updatePermission == 'Y'}" >
<c:if test="${!empty msdsDocumentViewBean.deletedByName}" >
  <c:set var="updatePermission" value='N'/>
</c:if>
</c:if>

       
/* fmt:formatDate, to formate your date to locale pattern */
/* pattern="${dateFormatPattern}" or pattern="${dateTimeFormatPattern}" */
<fmt:formatDate var="fmtEnteredDate" value="${status.current.enteredOn}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtDeletedDate" value="${status.current.deletedOn}" pattern="${dateFormatPattern}"/>

/* Get time for hidden date column. This is for sorting purpose. */
<c:set var="enteredDateTime" value="${status.current.enteredOn.time}"/>
<c:set var="deletedDateTime" value="${status.current.deletedOn.time}"/>

<c:set var="globalDocumentVal" value=""/>
<c:if test="${msdsDocumentViewBean.companyId == 'TCM_OPS'}" >
    <c:set var="globalDocumentVal" value="Y"/>
</c:if>


/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 <c:if test="${!empty status.current.deletedByName}">'class': 'grid_lightgray',</c:if>
 data:['${updatePermission}','','${status.current.documentTypeName}',
	 '<tcmis:jsReplace value="${status.current.documentName}" />',
	 '<tcmis:jsReplace value="${status.current.enteredByName}" />','${fmtEnteredDate}','${enteredDateTime}',
	 '<tcmis:jsReplace value="${status.current.deletedByName}" />','${fmtDeletedDate}','${deletedDateTime}',
	 '${globalDocumentVal}','${status.current.documentId}','${status.current.documentUrl}','${status.current.materialId}',
	 '${status.current.companyId}'
	  ]}

  <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};
</c:if>
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty msdsDocumentViewBeanCollection}" >
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
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<%--Store the search parameters.--%>
<input name="updateDocuments" id="updateDocuments" value="" type="hidden"/>
<input name="showDocuments" id="showDocuments" value="" type="hidden"/>
<input name="materialId" id="materialId" value="<tcmis:jsReplace value="${param.materialId}"/>" type="hidden"/>
<input name="companyId" id="companyId" value="<tcmis:jsReplace value="${param.companyId}"/>" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden"  name="calledFrom" id="calledFrom" value="<tcmis:jsReplace value="${param.calledFrom}"/>"/>
<input type="hidden"  name="mixtureId" id="mixtureId" value="<tcmis:jsReplace value="${param.mixtureId}"/>"/>
<input type="hidden"  name="mixtureRevisionDate" id="mixtureRevisionDate" value="<tcmis:jsReplace value="${param.mixtureRevisionDate}"/>"/>
<input type="hidden"  name="customerMixtureNumber" id="customerMixtureNumber" value="<tcmis:jsReplace value="${param.customerMixtureNumber}"/>"/>
<input name="documentTypeSource" id="documentTypeSource" value="<tcmis:jsReplace value="${param.documentTypeSource}"/>" type="hidden"/>

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

