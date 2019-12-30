<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"/>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<%@ include file="/common/locale.jsp" %>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<%--
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catalogadduseapproval.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>	
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- These are for the Grid-->
<%--
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<%--
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
--%>

<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
	<fmt:message key="label.addedituseapproval"/>
</title>

</head>


<body bgcolor="#ffffff" onload="editOnLoad('${param.uAction}');closeAllchildren();" onunload="checkClose('${param.uAction}');">

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
workarea:"<fmt:message key="label.workarea"/>",
useProcessDesc:"<fmt:message key="label.useprocessdesc"/>",
estimateAnnualUsage:"<fmt:message key="label.estimateannualusage"/>",
restriction1:"<fmt:message key="label.restriction1"/>",
restriction2:"<fmt:message key="label.restriction2"/>",
allGroups:"<fmt:message key="label.all"/>",
allWorkAreas:"<fmt:message key="label.allWorkAreas"/>",
allExceptControlled:"<fmt:message key="label.allexceptcontrolled"/>",
selectOne:"<fmt:message key="label.selectOne"/>",
workAreaGroup:"<fmt:message key="label.workareagroup"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
wasteWaterDischarge:"<fmt:message key="label.wastewaterdischarge"/>",
genericError:"<fmt:message key="generic.error"/>"};

var altApplicationUseGroup = new Array(
<c:forEach var="applicationUseGroupBean" items="${useApprovalData.workAreaColl}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
    {
        applicationUseGroupId:'<tcmis:jsReplace value="${applicationUseGroupBean.applicationUseGroupId}" processMultiLines="true"/>',
        applicationUseGroupName:'<tcmis:jsReplace value="${applicationUseGroupBean.applicationUseGroupName}: ${applicationUseGroupBean.applicationUseGroupDesc}" processMultiLines="true"/>',
        test:'<tcmis:jsReplace value="${applicationUseGroupBean.applicationUseGroupDesc}" processMultiLines="true"/>'
    }
</c:forEach>
);

var altApplication = new Array();
<c:forEach var="applicationUseGroupBean" items="${useApprovalData.workAreaColl}" varStatus="status">
    <c:set var="applicationCount" value='${0}'/>
    altApplication['<tcmis:jsReplace value="${applicationUseGroupBean.applicationUseGroupId}" processMultiLines="true"/>'] = new Array(
     <c:forEach var="tmpBean" items="${applicationUseGroupBean.applicationColl}" varStatus="status1">
        <c:if test="${status1.index > 0}">,</c:if>
        {
            application:'<tcmis:jsReplace value="${tmpBean.application}" processMultiLines="true"/>',
            applicationDesc:'<tcmis:jsReplace value="${tmpBean.applicationDesc}" processMultiLines="true"/>',
            inventoryGroup:'<tcmis:jsReplace value="${tmpBean.inventoryGroup}" processMultiLines="true"/>',
            inventoryGroupName:'<tcmis:jsReplace value="${tmpBean.inventoryGroupName}" processMultiLines="true"/>'
        }
     </c:forEach>
    );
</c:forEach>

var emissionPointsColl = new Array();
<c:forEach var="emissionPoint" items="${useApprovalData.emissionPointColl}" varStatus="status">
	try {
		emissionPointsColl['${emissionPoint.application}'].push(
			{
				id:'<tcmis:jsReplace value="${emissionPoint.appEmissionPoint}"/>${useApprovalData.emissionPointIdSeparator}<tcmis:jsReplace value="${emissionPoint.facEmissionPoint}"/>',
				name:'<tcmis:jsReplace value="${emissionPoint.facEmissionPoint}"/> - <tcmis:jsReplace value="${emissionPoint.appEmissionPoint}"/>: <tcmis:jsReplace value="${emissionPoint.appEmissionPointDesc}" processMultiLines="true" />'
			}
		);
	} catch (e) {
		emissionPointsColl['${emissionPoint.application}'] = new Array(
			{
				id:'<tcmis:jsReplace value="${emissionPoint.appEmissionPoint}"/>${useApprovalData.emissionPointIdSeparator}<tcmis:jsReplace value="${emissionPoint.facEmissionPoint}"/>',
				name:'<tcmis:jsReplace value="${emissionPoint.facEmissionPoint}"/> - <tcmis:jsReplace value="${emissionPoint.appEmissionPoint}"/>: <tcmis:jsReplace value="${emissionPoint.appEmissionPointDesc}" processMultiLines="true" />'
			}
		);
	}
</c:forEach>

var wasteWaterDischargeColl = new Array(
	{
		id:'',
		name:'<fmt:message key="label.selectOne"/>'
	},
	{
		id:'N',
		name:'<fmt:message key="label.no"/>'
	},
	{
		id:'Y',
		name:'<fmt:message key="label.yes"/>'
	}
);

<c:set var="showWasteWaterDischarge" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowWasteWaterDischarge', useApprovalData.facilityId, useApprovalData.companyId)}"/>
var showWasteWaterDischarge = ${showWasteWaterDischarge};
<c:set var="showEmissionPoints" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowEmissionPoints', useApprovalData.facilityId, useApprovalData.companyId)}"/>
var showEmissionPoints = ${showEmissionPoints};
</script>


<tcmis:form action="/catalogaddrequest.do" onsubmit="" target="_self">

<div class="interface" id="mainPage" style="">

<%-- transition --%>
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

	 <%-- message --%>
<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="center" width="100%">
				<textarea cols="70" rows="5" class="inputBox" readonly="true" name="messageText" id="messageText">${tcmISError}</textarea>
			</td>
		</tr>
		<tr>
			<td align="center" width="100%">
			<input name="closeMessageB"  id="closeMessageB"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
			</td>
		</tr>
	</table>
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

<!-- start of contentArea -->
<div class="contentArea">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<div id="searchMaskTable">

	<table class="tableSearch" border="0" width="100%" >
			 <tr>
				<td><a href="#" onclick="submitUpdate(); return false;"><fmt:message key="button.submit"/></a>
					&nbsp;|&nbsp;<a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a>
				</td>
			 </tr>
   </table>

<div>
<%-- Right Section 1 --%>
<!-- Initialize Menus -->

<!-- CSS Tabs -->
<div id="newChemTabs">
 <ul id="mainTabList">
 </ul>
</div>
<!-- CSS Tabs End -->

<div id="section1" class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

	 <table class="tableSearch" border="0">
		  <tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.facility"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
				 ${param.facilityName}
			 </td>
		 </tr>
         <tr id="applicationUseGroupSpan" style="display: none">
             <td class="optionTitleBoldRight">
                 <fmt:message key="label.approvalcode"/>:*
             </td>
             <td class="optionTitleBoldLeft">
                 <select class="selectBox" name="applicationUseGroupId" id="applicationUseGroupId" onchange="applicationUseGroupChanged()">

                 </select>
             </td>
         </tr>
         <tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.workarea"/>:*
			 </td>
			 <td class="optionTitleBoldLeft">
				 <select class="selectBox" name="workArea" id="workArea" onchange="setDefaultInventoryGroup()">
                    
				 </select>
				</td>
		 </tr>
		<tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.inventorygroup"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
					 <select class="selectBox" name="inventoryGroup" id="inventoryGroup">
						<c:set var="selectedInventoryGroup" value='${useApprovalData.inventoryGroup}'/>
						<c:set var="igColl" value='${useApprovalData.inventoryGroupColl}'/>
						<bean:size id="igSize" name="igColl"/>
						<c:if test="${igSize > 1}">
							<option value="All" selected><fmt:message key="label.allinventorygroups"/></option>
						</c:if>

						<c:forEach var="inventoryGroupDefinitionBean" items="${useApprovalData.inventoryGroupColl}" varStatus="status1">
							<c:set var="currentInventoryGroup" value='${inventoryGroupDefinitionBean.inventoryGroup}'/>
							<c:choose>
								<c:when test="${currentInventoryGroup == selectedInventoryGroup}">
									<option value="<c:out value="${inventoryGroupDefinitionBean.inventoryGroup}"/>" selected><c:out value="${inventoryGroupDefinitionBean.inventoryGroupName}" escapeXml="false"/></option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value="${inventoryGroupDefinitionBean.inventoryGroup}"/>"><c:out value="${inventoryGroupDefinitionBean.inventoryGroupName}" escapeXml="false"/></option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					 </select>
				 </td>
		 </tr>		  
		<tr>
			<td class="optionTitleBoldRight">
				<fmt:message key="label.requeststatus"/>:
			</td>
			<td class="optionTitleBoldLeft">
				<c:set var="selectedApprovedStatus" value='${useApprovalData.approvalStatus}'/>
				 <c:set var="selectedApproved" value='selected'/>
				 <c:set var="selectedUnapproved" value=''/>
				 <c:if test="${selectedApprovedStatus == 'Approved'}">
					<c:set var="selectedApproved" value='selected'/>
					<c:set var="selectedUnapproved" value=''/>
				 </c:if>
				 <c:if test="${selectedApprovedStatus == 'unapproved'}">
					<c:set var="selectedApproved" value=''/>
					<c:set var="selectedUnapproved" value='selected'/>
				 </c:if>
				<select name="approvalStatus" id="approvalStatus" class="selectBox">
					<option value="approved" ${selectedApproved}>
						<fmt:message key="label.approved"/>
					</option>
					<option value="unapproved" ${selectedUnapproved}>
						<fmt:message key="label.unapproved"/>
					</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight">
				 <fmt:message key="label.useprocessdesc"/>:*
			</td>
			<td class="optionTitleBoldLeft">
				 <textarea cols="40" rows="3" class="inputBox" name="processDesc" id="processDesc">${useApprovalData.processDesc}</textarea>
			</td>
		</tr>
		<tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.usergroup"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
					 <select class="selectBox" name="userGroupId" id="userGroupId">
						<c:set var="selectedUserGroupId" value='${useApprovalData.userGroupId}'/>
						<c:forEach var="approvalUserGroupBean" items="${useApprovalData.userGroupColl}" varStatus="status1">
							<c:set var="currentUserGroupId" value='${status1.current.userGroupId}'/>
							<c:choose>
								<c:when test="${currentUserGroupId == selectedUserGroupId}">
									<option value="<c:out value="${status1.current.userGroupId}"/>" selected><c:out value="${status1.current.userGroupDesc}" escapeXml="false"/></option>
								</c:when>
								<c:when test="${fn:length(selectedUserGroupId) == 0 && currentUserGroupId == 'All'}">
									<option value="<c:out value="${status1.current.userGroupId}"/>" selected><c:out value="${status1.current.userGroupDesc}" escapeXml="false"/></option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value="${status1.current.userGroupId}"/>"><c:out value="${status1.current.userGroupDesc}" escapeXml="false"/></option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					 </select>
				 </td>
		 </tr>
		 <tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.estimateannualusage"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
				 <input class="inputBox" type="text" name="estimatedAnnualUsage" id="estimatedAnnualUsage" value="${useApprovalData.estimatedAnnualUsage}" size="5">
			 </td>
		 </tr>
		 <tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.restriction1"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
				 <input class="inputBox" type="text" name="quantity1" id="quantity1" value="${useApprovalData.quantity1}" size="5">
				 &nbsp;<fmt:message key="label.per"/>&nbsp;
				 <input class="inputBox" type="text" name="per1" id="per1" value="${useApprovalData.per1}" size="5">
				 <c:set var="selectedPeriod1" value='${useApprovalData.period1}'/>
				 <c:set var="selectedDays1" value='selected'/>
				 <c:set var="selectedMonths1" value=''/>
				 <c:if test="${selectedPeriod1 == 'days'}">
					<c:set var="selectedDays1" value='selected'/>
					<c:set var="selectedMonths1" value=''/>
				 </c:if>
				 <c:if test="${selectedPeriod1 == 'months'}">
					<c:set var="selectedDays1" value=''/>
					<c:set var="selectedMonths1" value='selected'/>
				 </c:if>
				 <select name="period1" id="period1" class="selectBox">
					<option value="days" ${selectedDays1}>
						<fmt:message key="label.days"/>
					</option>
					<option value="months" ${selectedMonths1}>
						<fmt:message key="label.months"/>
					</option>
				</select>
			 </td>
		 </tr>
		 <tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.restriction2"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
				 <input class="inputBox" type="text" name="quantity2" id="quantity2" value="${useApprovalData.quantity2}" size="5">
				 &nbsp;<fmt:message key="label.per"/>&nbsp;
				 <input class="inputBox" type="text" name="per2" id="per2" value="${useApprovalData.per2}" size="5">
				 <c:set var="selectedPeriod2" value='${useApprovalData.period2}'/>
				 <c:set var="selectedDays2" value='selected'/>
				 <c:set var="selectedMonths2" value=''/>
				 <c:if test="${selectedPeriod2 == 'days'}">
					<c:set var="selectedDays2" value='selected'/>
					<c:set var="selectedMonths2" value=''/>
				 </c:if>
				 <c:if test="${selectedPeriod2 == 'months'}">
					<c:set var="selectedDays2" value=''/>
					<c:set var="selectedMonths2" value='selected'/>
				 </c:if>
				 <select name="period2" id="period2" class="selectBox">
					<option value="days" ${selectedDays2}>
						<fmt:message key="label.days"/>
					</option>
					<option value="months" ${selectedMonths2}>
						<fmt:message key="label.months"/>
					</option>
				</select>
			 </td>
		 </tr>
		<c:if test= "${showWasteWaterDischarge}" >
			<tr>
				<td class="optionTitleBoldRight">
					<fmt:message key="label.wastewaterdischarge"/>:*
				</td>
				<td class="optionTitleBoldLeft">
					<select name="wasteWaterDischarge" id="wasteWaterDischarge" class="selectBox"></select>
				</td>
			</tr>
		</c:if>
		<c:if test= "${showEmissionPoints}" >
			<tr>
				<td class="optionTitleBoldRight">
					<fmt:message key="label.emissionpoint"/>:
				</td>
				<td class="optionTitleBoldLeft">
					<select name="emissionPointsSel" id="emissionPointsSel" class="selectBox" onchange="emissionPointsChanged();"></select>
					<input name="emissionPointsMultiSelTxt" id="emissionPointsMultiSelTxt" value="" class="inputBox"  style="display: none" readonly="readonly" size="33"/>
					<input name="emissionPoints" id="emissionPoints" value="" type="hidden" />	
					<button id="emissionPointsMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onmouseout="this.className='listButton'" name="None" value="" onclick="popMultiSel('emissionPoints');return false;" style="display:none;"></button>
					&nbsp;&nbsp;&nbsp;
					<span id="emissionPointsSpan"></span>
				</td>
			</tr>
		</c:if>
	</table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>

</div>

</div>
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
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" />
<input type="hidden" name="personnelId" id="personnelId" value='${personnelBean.personnelId}'/>
<input type="hidden" name="requestId" id="requestId" value='${useApprovalData.requestId}'/>
<input type="hidden" name="companyId" id="companyId" value='${useApprovalData.companyId}'/>
<input type="hidden" name="facilityId" id="facilityId" value='${useApprovalData.facilityId}'/>
<input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value='${useApprovalData.inventoryGroup}'/>
<input type="hidden" name="selectedApplicationUseGroupId" id="selectedApplicationUseGroupId" value='${useApprovalData.applicationUseGroupId}'/> 
<input type="hidden" name="selectedWorkArea" id="selectedWorkArea" value='${useApprovalData.workArea}'/>
<input type="hidden" name="hasApplicationUseGroup" id="hasApplicationUseGroup" value='${useApprovalData.hasApplicationUseGroup}'/>
<input type="hidden" name="calledFrom" id="calledFrom" value='<tcmis:jsReplace value="${param.calledFrom}"/>'/>
<input type="hidden" name="allowAllApps" id="allowAllApps" value='<tcmis:jsReplace value="${param.allowAllApps}"/>'/>
<input type="hidden" name="applicationDesc" id="applicationDesc" value="<tcmis:jsReplace value="${param.applicationDesc}"/>"/>
<input type="hidden" name="application" id="application" value="<tcmis:jsReplace value="${param.application}"/>"/>
<input type="hidden" name="storageAction" id="storageAction" value=""/>
<input type="hidden" name="selectedWasteWaterDischarge" id="selectedWasteWaterDischarge" value='${useApprovalData.wasteWaterDischarge}'/>
<input type="hidden" name="selectedEmissionPoints" id="selectedEmissionPoints" value='${useApprovalData.emissionPoints}'/>
<input type="hidden" name="specificUseApprovalRequired" id="specificUseApprovalRequired" value='${useApprovalData.specificUseApprovalRequired}'/>
<input type="hidden" name="emissionPointIdSeparator" id="emissionPointIdSeparator" value="${useApprovalData.emissionPointIdSeparator}"/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

</body>
</html:html>