<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>
<script type="text/javascript" src="/js/ordertracking.js"></script>

<title>
<fmt:message key="approvedworkareas.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var children = new Array();

function showLegend() {
	children[children.length] = openWinGeneric("approvedworkareaslegend.do?companyId="+$v('companyId')+"&facilityId="+$v('facilityId'),"shownewlist","570","360","yes","80","80");

}

function closeAllchildren() 
{ 
	{
		try {
			for(var n=0;n<children.length;n++) {
				children[n].closeAllchildren(); 
			}
		} catch(ex)
		{
		}
		if(!window.closed)
			window.close();
	} 
} 
// -->
</script>
</head>

<body bgcolor="#ffffff">

<%--
<tcmis:form action="/pagename.do" onsubmit="return submitOnlyOnce();">
--%>

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="approvedworkareas.label.title"/>
</td>
</tr>
</table>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
<table width="400" border="0" cellpadding="0" cellspacing="3" class="tableResultsInfo">
<tr colspan="5" class="alignCenter">
    <td width="5%" class="alignRight">
        <b><fmt:message key="label.catalogid"/>:</b>&nbsp;
    </td>

    <td width="35%" class="alignLeft">
        <c:out value='${param.catalogId}'/>
    </td>
</tr>

<c:if test="${param.allCatalog != 'Y'}">
    <tr colspan="5" class="alignCenter">
        <td width="5%" class="alignRight">
        <b><fmt:message key="label.facilityid"/>:</b>&nbsp;
        </td>
        <td width="35%" class="alignLeft">
        <c:out value='${param.facilityId}'/>
        </td>
    </tr>
</c:if>

<tr colspan="5" class="alignCenter">
    <td width="5%" class="alignRight">
    <b><fmt:message key="label.partnumber"/>:</b>&nbsp;
    </td>

    <td width="35%" class="alignLeft">
    <c:out value='${param.facPartNo}'/>
    </td>
</tr>

    <tr colspan="5" class="alignRight">
<td>
  <button name="containerlabels" id="containerlabels" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.close"/>
  </button>
</td>
</tr>
</table>

   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
${tcmISError}
</div>
<!-- Error Messages Ends -->


<c:if test="${approvedWorkAreasBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">

    <div class="dataContent">

<br/>

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<table width="100%">
<tr>
<td width="100%">
 <c:if test="${!empty approvedWorkAreasBeanCollection}" >
<table width="700" border="0" cellpadding="0" cellspacing="0" class="tableResults">
<c:if test="${useCodeRequired}">
    <tr>
        <td>
            <a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a>
        </td>
    </tr>
</c:if>
<tr align="center">
    <c:if test="${param.allCatalog == 'Y'}">
        <th width="20%"><fmt:message key="label.facility"/></th>
    </c:if>
    <th width="10%"><fmt:message key="label.workareas"/></th>
    <th width="10%"><fmt:message key="label.usergroup"/></th>
    <th width="10%"><fmt:message key="approvedworkareas.label.restriction1"/></th>
    <th width="10%"><fmt:message key="approvedworkareas.label.restriction2"/></th>
    <th width="10%"><fmt:message key="approvedworkareas.label.members"/></th>
    <c:if test="${useCodeRequired}">
	    <th width="10%"><fmt:message key="label.workareaapprovalcodes"/></th>
	    <th width="10%"><fmt:message key="label.begindate"/></th>
	    <th width="10%"><fmt:message key="label.enddate"/></th>
    </c:if>
</tr>
<c:forEach var="approvedWorkAreasBean" items="${approvedWorkAreasBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='alt'/>
  </c:otherwise>
</c:choose>

  <fmt:formatDate var="fmtStartDate" value="${status.current.startDate}" pattern="${dateFormatPattern}"/>
  <fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
  
<tr align="center">
  <c:if test="${param.allCatalog == 'Y'}">
       <td <c:out value="${colorClass}"/> width="20%"><c:out value="${status.current.facilityDesc}"/></td>
  </c:if>
  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.applicationDescDisplay}"/></td>
  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.userGroupId}"/></td>
  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.limit1}"/></td>
  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.limit2}"/></td>
  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.userGroupMembers}"/></td>
  <c:if test="${useCodeRequired}">
	  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${status.current.applicationUseGroupName}"/></td>  
	  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${fmtStartDate}"/></td>
	  <td <c:out value="${colorClass}"/> width="10%"><c:out value="${fmtExpireDate}"/></td>
  </c:if>
</tr>


</c:forEach>

   <!-- If the collection is empty say no data found -->

</table>
</c:if>

   <c:if test="${empty approvedWorkAreasBeanCollection}" >

    <table width="400" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
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
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input type="hidden" name="partGroupNo" id="partGroupNo" value="${param.partGroupNo}"/>
 <input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}"/>
 <input type="hidden" name="catalogId" id="catalogId" value="${param.catalogId}"/>
 <input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
 <input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
  <input type="hidden" name="facPartNo" id="facPartNo" value="${param.facPartNo}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
<%-- 
</tcmis:form>
 --%>
</body>
</html>