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
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/expertreviewitem.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<title>
<fmt:message key="label.expertreviewlist"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
pleaseSelect:"<fmt:message key="error.norowselected"/>",
shipinfo: "<fmt:message key="label.shipinfo"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var showErrors = false;

with(milonic=new menuname("rightClickMenu")){
	top = "offset=2";
	style = submenuStyle;
	margin = 3;
	aI("text=<fmt:message key="label.manageexpertreviewentities"/>;url=javascript:showEntityList();");
}

drawMenus();

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var columnConfig = [
{ columnId:"permission",
  columnName:'',
  submit:false
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  type:"hchstatus",
  align:"center",
  width:4,
  onChange: setModified,
  submit:true
},
{
	columnId:"status"
},
{ columnId:"expertReviewId",
  columnName:'<fmt:message key="label.list"/>',
  tooltip:"Y",
  width: 4,
  align:"center",
  submit:true
},
{ columnId:"description",
  columnName:'<fmt:message key="label.description"/>',
  width: 20,
  tooltip:"Y"
},
{
  columnId:"opsEntityId"
},
{ columnId:"operatingEntityName",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width: 20,
  tooltip:"Y"
},
{ 
  columnId:"createdBy"
},
{ columnId:"createdByName",
  columnName:'<fmt:message key="label.createdby"/>',
  tooltip:"Y"
},
{ columnId:"createdDate",
  columnName:'<fmt:message key="label.datecreated"/>',
  tooltip:"Y",
  width: 10
},
{ 
  columnId:"lastUpdatedBy"
},
{ columnId:"lastUpdatedByName",
  columnName:'<fmt:message key="label.lastupdatedby"/>',
  tooltip:"Y"
},
{ columnId:"lastUpdatedDate",
  columnName:'<fmt:message key="label.lastupdated"/>',
  tooltip:"Y",
  width:10
},
{
	columnId:"modified",
	type:"hchstatus",
	submit:true
}
];

var gridConfig = {
		divName:'expertReviewItemBean',
		beanData:'jsonMainData',
		beanGrid:'beanGrid',
		config:'columnConfig',
		rowSpan:true,
		noSmartRender: false,
		submitDefault:true,
		onRightClick: selectRightClick
};

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();	
var rowSpanCols = [0,1,2,3,4,7,8,9,10,11,12,13];

// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="resizeFrames();">

<tcmis:form action="/expertreviewitem.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"><html:errors/>${tcmisError}</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmisError }">
     showErrors = false;
    </c:when>
    <c:otherwise>
     showErrors = true;
    </c:otherwise>
   </c:choose>   
    //-->
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<table>
<tr>
<td class="optionTitleBoldRight"><fmt:message key="label.item"/>:</td>
<td><input id="item" name="item" type="text" class="inputBox" readonly value="<tcmis:jsReplace value="${param.item}" processMultiLines="false" />"/></td>
</tr>
<tr>
<td class="optionTitleBoldRight"><fmt:message key="label.description"/>:</td>
<td><tcmis:jsReplace value="${itemDesc}" processMultiLines="false" /></td>
</tr>
</table>
<div class="backGroundContent">

<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv"> 
<!-- Search results start -->
<div class="roundcont filterContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>        
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateLink"><a href="#" onclick="updateLists();"><fmt:message key="label.update"/></a> |</span>
      <span id="cancelLink"><a href="#" onclick="cancel();"><fmt:message key="label.cancel"/></a></span>
      </div>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
		<div id="expertReviewItemBean" style="width:100%;height:300px;" style="display: none;"></div>
	</div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>  
</div><!-- Result Frame Ends -->


<c:if test="${expertReviewItemColl != null}">
<c:set var="editable" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="ExpertReviewAdmin" opsEntityId="">
<c:set var="editable" value='Y'/>
</tcmis:opsEntityPermission>
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty expertReviewItemColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="expertReviewItem" items="${expertReviewItemColl}" varStatus="status">
<c:if test="${dataCount >= 1}">,</c:if>
      
<fmt:formatDate var="fmtLastUpdated" value="${status.current.lastUpdatedDate}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="fmtDateCreated" value="${status.current.dateCreated}" pattern="${dateTimeFormatPattern}"/>

/*The row ID needs to start with 1 per their design.*/
{ id:${dataCount+1},
 data:['${editable}',
 	${status.current.status eq 'A'},'${status.current.status}',
 	'${status.current.expertReviewId}',
 	'<tcmis:jsReplace value="${status.current.description}" processMultiLines="false"/>',
 	'<tcmis:jsReplace value="${status.current.opsEntityId}" processMultiLines="false"/>',
 	'<tcmis:jsReplace value="${status.current.operatingEntityName}" processMultiLines="false"/>',
 	'${status.current.createdBy}',
 	'<tcmis:jsReplace value="${status.current.createdByName}" processMultiLines="false"/>',
 	'${fmtDateCreated}',
 	'${status.current.lastUpdatedBy}',
 	'<tcmis:jsReplace value="${status.current.lastUpdatedByName}" processMultiLines="false"/>',
 	'${fmtLastUpdated}',
 	${false}
 ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<script language="JavaScript" type="text/javascript">
<c:forEach var="erBean" items="${expertReviewItemColl}" varStatus="status">
<c:set var="currentKey" value='${erBean.expertReviewId}' />
<c:choose>
	<c:when test="${status.first}">
		<c:set var="rowSpanStart" value='0' />
		<c:set var="rowSpanCount" value='1' />
		rowSpanMap[0] = 1;
		rowSpanClassMap[0] = 1;
	</c:when>
	<c:when test="${currentKey == previousKey}">
		rowSpanMap[${rowSpanStart}]++;
		rowSpanMap[${status.index}] = 0;
		rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
	</c:when>
	<c:otherwise>
		<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
		<c:set var="rowSpanStart" value='${status.index}' />
		rowSpanMap[${status.index}] = 1;
		rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
	</c:otherwise>
</c:choose>
<c:set var="previousKey" value='${currentKey}' />
</c:forEach>
</script>
<!-- If the collection is empty say no data found -->

<c:if test="${empty expertReviewItemColl}">
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



    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
        <input name="uAction" id="uAction" value="" type="hidden"/>
        <input name="minHeight" id="minHeight" type="hidden" value="100"/>
        <input name="expertReviewer" id="expertReviewer" type="hidden" value="${readonly}"/>
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
<c:if test="${editable == 'Y'}">
<script type="text/javascript">
    <!--
   	 showUpdateLinks = true;
    //-->
</script>
</c:if>

</tcmis:form>
</body>
</html:html>
