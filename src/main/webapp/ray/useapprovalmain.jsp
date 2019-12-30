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
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/ray/useapprovalmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="useapprovalstatus.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var windowCloseOnEsc = true;

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
searchInput:"<fmt:message key="label.inputSearchText"/>",
errors:"<fmt:message key="label.errors"/>", 
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
facility:"<fmt:message key="label.facility"/>",
workarea:"<fmt:message key="label.workarea"/>",
usergroup:"<fmt:message key="label.usergroup"/>",
mustbeselected:"<fmt:message key="label.mustbeselected"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>"};

var altfacilityId = new Array(
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altapplication = new Array();
var altapplicationdesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="applicationObjBeanCollection" value='${status.current.applicationVar}'/>

<c:set var="applicationCount" value='${0}'/>
altapplication["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"<c:out value="${status1.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.application}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );

<c:set var="applicationCount" value='${0}'/>
altapplicationdesc["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"<c:out value="${status1.current.applicationDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.applicationDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );
 </c:forEach>

var altusergroupid = new Array();
var altusergroupdesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="userGroupObjBeanCollection" value='${status.current.userGroupVar}'/>

<c:set var="userGroupCount" value='${0}'/>
altusergroupid["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="userGroupObjBean" items="${userGroupObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${userGroupCount > 0}">
    ,"<c:out value="${status1.current.userGroupId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.userGroupId}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="userGroupCount" value='${userGroupCount+1}'/>
  </c:forEach>
  );

<c:set var="userGroupCount" value='${0}'/>
altusergroupdesc["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="userGroupObjBean" items="${userGroupObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${userGroupCount > 0}">
    ,"<c:out value="${status1.current.userGroupDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.userGroupDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="userGroupCount" value='${userGroupCount+1}'/>
  </c:forEach>
  );
 </c:forEach>


var altdeliveryPoint = new Array();
var altdeliveryPointDesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="facilityDockObjBeanCollection" value='${status.current.facDockVar}'/>

<c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanCollection}" varStatus="dockStatus">
<c:set var="currentDockLocationId" value='${dockStatus.current.dockLocationId}'/>
<c:set var="deliveryPointObjBeanCollection" value='${dockStatus.current.deliveryPointVar}'/>

<c:set var="deliveryPointCount" value='${0}'/>
altdeliveryPoint["<c:out value="${currentDockLocationId}"/>"] = new Array(
 <c:forEach var="deliveryPointObjBean" items="${deliveryPointObjBeanCollection}" varStatus="deliveryPointStatus">
  <c:choose>
   <c:when test="${deliveryPointCount > 0}">
    ,"<c:out value="${deliveryPointStatus.current.deliveryPoint}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${deliveryPointStatus.current.deliveryPoint}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="deliveryPointCount" value='${deliveryPointCount+1}'/>
  </c:forEach>
  );

<c:set var="deliveryPointCount" value='${0}'/>
altdeliveryPointDesc["<c:out value="${currentDockLocationId}"/>"] = new Array(
 <c:forEach var="deliveryPointObjBean" items="${deliveryPointObjBeanCollection}" varStatus="deliveryPointStatus">
  <c:choose>
   <c:when test="${deliveryPointCount > 0}">
    ,"<c:out value="${deliveryPointStatus.current.deliveryPointDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${deliveryPointStatus.current.deliveryPointDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="deliveryPointCount" value='${deliveryPointCount+1}'/>
  </c:forEach>
  );
  </c:forEach>
 </c:forEach>

<c:set var="orderQuantityRuleCount" value='${0}'/>
var orderQuantityRule = new Array(
<c:forEach var="vvUseApprovalOrderQtyRuleBean" items="${vvUseApprovalOrderQtyRuleBeanColl}" varStatus="statusPriority">
  <c:choose>
   <c:when test="${orderQuantityRuleCount > 0}">
    ,"<c:out value="${statusPriority.current.orderQuantityRule}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${statusPriority.current.orderQuantityRule}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="orderQuantityRuleCount" value='${orderQuantityRuleCount+1}'/>
</c:forEach>
  );

<c:set var="orderQuantityRuleCount" value='${0}'/>
var orderQuantityRuleDesc = new Array(
<c:forEach var="vvUseApprovalOrderQtyRuleBean" items="${vvUseApprovalOrderQtyRuleBeanColl}" varStatus="statusPriority">
  <c:choose>
   <c:when test="${orderQuantityRuleCount > 0}">
    ,"<c:out value="${statusPriority.current.orderQuantityRuleDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${statusPriority.current.orderQuantityRuleDesc}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="orderQuantityRuleCount" value='${orderQuantityRuleCount+1}'/>
</c:forEach>
  );

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="loadLayoutWin('','workAreaManagement');" onresize="resizeFrames()" target="resultFrame">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/useapprovalresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <!-- Search Option Table Start -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityIdChanged()">
  <c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
  <c:set var="currentFacilityId" value='${status.current.facilityId}'/>

  <c:choose>
   <c:when test="${empty selectedFacilityId}" >
    <c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
    <c:set var="applicationObjBeanJspCollection" value='${status.current.applicationVar}'/>
    <c:set var="userGroupObjBeanJspCollection" value='${status.current.userGroupVar}'/>
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
   <c:when test="${currentFacilityId == selectedFacilityId}" >
    <c:set var="applicationObjBeanJspCollection" value='${status.current.applicationVar}'/>
    <c:set var="userGroupObjBeanJspCollection" value='${status.current.userGroupVar}'/>
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}">
    <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>


<td width="10%" class="optionTitleBoldRight">&nbsp;
   <%--<fmt:message key="label.sortorder"/>: --%>
 </td>
 <td width="20%" class="optionTitleBoldLeft">
   <%--<html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
     <html:option value="USER_GROUP_ID,FAC_PART_NO" key="useapprovalstatus.label.usergroupsort"/>
     <html:option value="FAC_PART_NO,USER_GROUP_ID" key="useapprovalstatus.label.partsort"/>
   </html:select>--%>
&nbsp;
<input type="hidden" name="sortBy" id="sortBy" value="FAC_PART_NO">
 </td>

<td width="20%" class="optionTitleBoldLeft">
&nbsp;
</td>

</tr>

<tr>
<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.workarea"/>:
<br><a href="javascript:showUseApprovers()"><fmt:message key="useapprovalstatus.label.showapprovers"/></a>
</td>

<td width="30%" class="optionTitleBoldLeft">
<c:set var="selectedApplication" value='${param.application}'/>
<c:set var="applicationCount" value='${0}'/>
<select name="application" id="application" class="selectBox" onchange="applicationChanged()">
 <option value="All"><fmt:message key="label.pleaseselect"/></option>
  <c:forEach var="applicationObjBean" items="${applicationObjBeanJspCollection}" varStatus="status">
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  <c:set var="currentApplication" value='${status.current.application}'/>

  <c:choose>
   <c:when test="${selectedApplication == currentApplication}">
    <option value="<c:out value="${currentApplication}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentApplication}"/>"><c:out value="${status.current.applicationDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="10%" class="optionTitleBoldRight">
<c:set var="showActiveOnly" value='${param.showActiveOnly}'/>
<%--<c:if test="${empty submitSearch && empty submitUpdate}">
 <c:set var="showApprovedOnly" value='Y'/>
</c:if>--%>

<c:if test="${showActiveOnly != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>
<input type="checkbox" name="showActiveOnly" id="showActiveOnly" class="radioBtns" value="Y" <c:out value="${checkBoxChecked}"/>>
</td>

<td width="20%" class="optionTitleBoldLeft">
<fmt:message key="useapprovalstatus.label.showactiveonly"/>
</td>

<td width="20%" class="optionTitleBoldLeft">
&nbsp;
</td>

</tr>

<tr>
<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.usergroup"/>:
</td>

<td width="30%" class="optionTitleBoldLeft">
<c:set var="selectedUserGroupId" value='${param.userGroupId}'/>
<c:if test="${empty selectedUserGroupId}">
 <c:set var="selectedUserGroupId" value='All'/>
</c:if>

<select name="userGroupId" id="userGroupId" class="selectBox">
 <%--<option value="All"><fmt:message key="label.pleaseselect"/></option>--%>
 <c:forEach var="userGroupObjBean" items="${userGroupObjBeanJspCollection}" varStatus="status">
 <c:set var="currentUserGroupId" value='${status.current.userGroupId}'/>

  <c:choose>
   <c:when test="${selectedUserGroupId == currentUserGroupId}">
    <option value="<c:out value="${currentUserGroupId}"/>" selected><c:out value="${status.current.userGroupDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentUserGroupId}"/>"><c:out value="${status.current.userGroupDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
</select>
</td>

<td width="10%" class="optionTitleBoldRight">
   <fmt:message key="label.keywords"/>:
 </td>

<td width="20%" class="optionTitleBoldLeft">
<html:text property="searchText" styleId="searchText" styleClass="inputBox" size="40" onchange="disableGridData()" onkeypress="return !(window.event && window.event.keyCode == 13);" onkeydown="return !(window.event && window.event.keyCode == 13);"/>
</td>

<td width="20%" class="optionTitleBoldLeft">
<c:set var="showOnlyWithLimits" value='${param.showOnlyWithLimits}'/>
<c:if test="${showOnlyWithLimits != null}" >
 <c:set var="checkBoxChecked" value='checked'/>
</c:if>

<input type="checkbox" name="showOnlyWithLimits" id="showOnlyWithLimits" class="radioBtns" onchange="disableGridData()" value="Y" <c:out value="${checkBoxChecked}"/>>
<fmt:message key="useapprovalstatus.label.showonlywithlimits"/>
</td>
<%--<div id="managedWorkAreaFlag" style="display:none">
<INPUT TYPE="checkbox" name="managedWorkArea" ID="managedWorkArea" value="Y" onClick="updateManagedWorkArea()">
<B><fmt:message key="useapprovalstatus.label.workareaoverride"/></B>
</div>--%>
</tr>

<tr>
<td width="15%" colspan="5" class="optionTitleBoldLeft">
 <input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "submitSearchForm(); return false;">
 <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>

</td>
</tr>
</table>
 <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
    <div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
            <html:errors/>
        </div>
    </div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="updateAllRows" id="updateAllRows" value="">
<input type="hidden" name="facilityId" id="facilityId" value="<c:out value="${param.facilityId}"/>">
<input type="hidden" name="application" id="application" value="<c:out value="${param.application}"/>">
<input type="hidden" name="userGroupId" id="userGroupId" value="<c:out value="${param.userGroupId}"/>">
<input type="hidden" name="searchText" id="searchText" value="<c:out value="${param.searchText}"/>">
<input type="hidden" name="showApprovedOnly" id="showApprovedOnly" value="Y">
<input type="hidden" name="uAction" id="uAction" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
</div>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->



<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>        
   <div class="boxhead"> 
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>     
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
	  <span id="updateResultLink" style="display: none">
	   <a id="update" href="#" onclick="call('update');return false;"><fmt:message key="label.update"/></a>
         |
         <a id="managedWorkAreaLink" class="" href="#" onclick="call('updateManagedWorkArea');return false;"></a>&nbsp;
         |
       <a id="submitUpdateAddAll" href="#" onclick="call('updateActivateAll');return false;"><fmt:message key="useapprovalstatus.label.activateall"/></a>
        |
       <a id="submitDeleteAll" href="#" onclick="call('updateDeleteAll');return false;"><fmt:message key="useapprovalstatus.label.deactivateall"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <span id="manageLink" style="display: none"><fmt:message key="label.managedworkarea"/>
         </span>  
         
       </span>           
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
    
     
   </div>
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
</div>  
</div><!-- Result Frame Ends -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>