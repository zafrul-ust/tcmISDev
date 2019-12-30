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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/common/admin/startupPages.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="label.startupPages"/> (${MODIFIEEFULLNAME})
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",itemInteger:"<fmt:message key="error.item.integer"/>"};

var config = [
{ columnId:"permission",
  columnName:'',
  width:0
},
{ columnId:"pageName",
  columnName:'<fmt:message key="label.pages"/>',
  sorting:"haasStr",
  width:20,
  submit:true
},
{ columnId:"bStartPage",
  columnName:'<fmt:message key="label.startup"/>',
  sorting:"haasHch",
  type:"hchstatus",
  width:15
},
{ columnId:"strStartPageOrder",
  columnName:'<fmt:message key="label.startupOrder"/>',
  sorting:"haasStr",
  type:"hed",
  width:7,
  submit:true
},
{ columnId:"personnelId",
  columnName:'',
  width:0,
  submit:true
},
{ columnId:"startPage",
  columnName:'',
  width:0,  
  submit:true
},
{ columnId:"pageId",
  columnName:'',
  width:0,
  submit:true
},
{ columnId:"oldPageOrder",
  columnName:'',
  width:0,
  submit:true
},
{ columnId:"oldStartPage",
	  columnName:'',
	  width:0,
	  submit:true
}
];
// -->
 </script>
</head>

<body bgcolor="#ffffff" onload="myResultOnload()" onresize="resizeFrames()">

<tcmis:form action="/startuppages.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

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
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
        <a href="#" onclick="updatePersonalInfo(); return false;"><fmt:message key="label.update"/></a> | 
      </span>
      <a href="#" onclick="createXSL();"><fmt:message key="label.createExcel"/></a> |
      <a href="#" onclick="window.close()"><fmt:message key="label.cancel"/></a> 
      &nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
	</div> <%-- boxhead Ends --%>

<!--  result page section start -->
<div class="dataContent">
<div id="UserPageAdminViewBean" style="width:100%;height:600px;" style="display: none;"></div>

<c:if test="${UserPageAdminViewCollection != null}">
<script type="text/javascript">

<!--
<c:set var="dataCount" value='${0}'/>
<c:set var="adminCount" value='${0}'/>
<c:set var="permission" value='Y'/>
<c:if test="${!empty UserPageAdminViewCollection}" >

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="pageNameViewBean" items="${UserPageAdminViewCollection}" varStatus="status">

<c:if test="${status.current.userAccess == 'Admin' || status.current.userAccess == 'Grant Admin' }" >
	<c:set var="adminCount" value='${1}'/>
</c:if>

<c:if test="${status.current.userAccess == 'View' && status.current.personnelId != userId}">
	<c:set var="permission" value='N'/>
</c:if>

<c:if test="${status.index > 0}">,</c:if>
{ id:${status.index +1},
 data:['${permission}',
  '<fmt:message key="${status.current.pageId}"/>',
  <c:choose>
  <c:when test="${status.current.startPage == 'Y'}">
  true
  </c:when>
  <c:otherwise>
  false
  </c:otherwise>
</c:choose>
,
  '${status.current.startPageOrder}','${status.current.personnelId}','${status.current.startPage}',
  '${status.current.pageId}','${status.current.startPageOrder}','${status.current.startPage}']}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->   
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty UserPageAdminViewCollection}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
<!-- Search results end -->
</c:if>

<c:if test="${adminCount > 0 || userId == personnelId }">
 <script type="text/javascript">
 <!--
 
  showUpdateLinks = true;
  
 //-->
 </script>
</c:if>

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
<input type="hidden" name="personnelId" id="personnelId" value="${personnelId}"/>

<%--Store the search parameters.--%>
<input name="action" id="action" value="" type="hidden"/>

<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">

<input name="minHeight" id="minHeight" type="hidden" value="100">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>
 
</body>
</html:html>