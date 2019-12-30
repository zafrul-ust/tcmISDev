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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>

<script src="/js/report/publishtemplate.js" language="JavaScript"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>	
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>	
<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.reporttemplate"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
genericError:"<fmt:message key="generic.error"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseentertemplatename:"<fmt:message key="label.pleaseentertemplatename"/>",
selectUserGroup:"<fmt:message key="label.selectausergroup"/>",
enterUser:"<fmt:message key="label.enterauser"/>",
selectUser:"<fmt:message key="label.selectauser"/>",
templateexistwarning:"<fmt:message key="label.templateexistwarning"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="onMyLoad();">

<tcmis:form action="/publishtemplate.do" onsubmit="return SubmitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

var publishIndividualTemplate = false;
var publishUserGroupTemplate = false;
var publishCompanyTemplate = false;


//-->
</script>

<c:set var="userPermission" value='N'/>
<c:set var="userGroupPermission" value='N'/>
<c:set var="companyPermission" value='N'/>
<tcmis:facilityPermission indicator="true" userGroupId="PublishIndividualTemplate" companyId="${personnelBean.companyId}">
	<c:set var="userPermission" value='Y'/>
	<script type="text/javascript">
	<!--
		publishIndividualTemplate = true;
	//-->
	</script>
</tcmis:facilityPermission>

<c:if test="${!empty userGroupColl}">
	<c:set var="userGroupPermission" value='Y'/>
	<script type="text/javascript">
	<!--
		publishUserGroupTemplate = true;
	//-->
	</script>
</c:if>

<tcmis:facilityPermission indicator="true" userGroupId="PublishCompanyTemplate" companyId="${personnelBean.companyId}">
	<c:set var="companyPermission" value='Y'/>
	<script type="text/javascript">
	<!--
		publishCompanyTemplate = true;
	//-->
	</script>
</tcmis:facilityPermission>


<div class="interface" id="mainPage">
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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<c:choose>
			<c:when test="${param.action == 'publish'}">
				<tr>
					<td class="optionTitleBoldLeft" width="100%" colspan="3">
				   	<a href="javascript:auditPublishSubmit()"><fmt:message key="label.publishtemplate"/></a> |
						<a href="javascript:closeWindow()"><fmt:message key="label.cancel"/></a>
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldCenter" width="100%" colspan="3">
				   	<fmt:message key="label.publishtemplate"/>:&nbsp;${param.templateName}
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldLeft" width="100%" colspan="3">
						<fmt:message key="label.publishto"/>:
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="optionTitleBoldLeft" width="100%" colspan="3">
				   	<a href="javascript:auditUnpublishSubmit()"><fmt:message key="label.unpublishtemplate"/></a> |
                  <a href="javascript:closeWindow()"><fmt:message key="label.cancel"/></a>
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldCenter" width="100%" colspan="3">
				   	<fmt:message key="label.unpublishtemplate"/>:&nbsp;${param.templateName}
					</td>
				</tr>
				<c:if test="${owner == 'PERSONNEL_ID'}">
					<tr>
						<td class="optionTitleBoldLeft" colspan="3">
							<input type="checkbox" name="myTemplateView" id="myTemplateView" value="Y" class="radio" checked="checked"><fmt:message key="label.removetemplatefrommyview"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${!empty userColl || owner == 'USER_GROUP_ID' || owner == 'COMPANY_ID'}">
					<tr>
						<td class="optionTitleBoldLeft" colspan="3">
							<fmt:message key="label.unpublishfrom"/>:
						</td>
					</tr>
				</c:if>
			</c:otherwise>
		</c:choose>

		<tr>
			<td class="optionTitleBoldLeft" width="10%">
				&nbsp;
			</td>
			<c:choose>
				<c:when test="${param.action == 'publish'}">
					<c:if test="${userPermission == 'Y'}">
						<td class="optionTitleBoldLeft" width="90%" colspan="2">
							<input type="checkbox" name="usersTemplateView" id="usersTemplateView" value="Y" class="radio"><fmt:message key="label.users"/>:&nbsp;
							<input class="inputBox" type="text" name="requestorName" id="requestorName" value="" readonly="true" size="60">
							<input name="userIdList" id="userIdList" type="hidden" value=""/>
							<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" id="selectedRequestor" name="selectedRequestor" value="..." align="right" onClick="searchRequestor()"/>
							<input name="clearB" id="clearB" type="button" class="smallBtns" value="<fmt:message key="label.clear"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" onclick="clearUsers();">
						</td>
					</c:if>
				</c:when>
				<c:otherwise>
				   <c:if test="${owner == 'PERSONNEL_ID' && !empty userColl}">
						<td class="optionTitleBoldLeft" width="20%">
							<input type="checkbox" name="usersTemplateView" id="usersTemplateView" value="Y" class="radio"><fmt:message key="label.users"/>:
						</td>
						<td class="optionTitleBoldLeft" width="70%">
							<select class="selectBox" name="userIdArray" id="userIdArray" multiple size="5" rowspan="5">
								<c:forEach var="tmpBean" items="${userColl}" varStatus="status">
									<option value="<c:out value="${tmpBean.personnelId}"/>"><c:out value="${tmpBean.fullName}"/></option>
								</c:forEach>
							</select>
						</td>
					</c:if>
				</c:otherwise>
			</c:choose>
		</tr>

		<c:choose>
			<c:when test="${param.action == 'publish'}">
				<%-- user group --%>
				<c:if test="${userGroupPermission == 'Y'}">
					<tr>
						<td class="optionTitleBoldLeft" width="10%">
							&nbsp;
						</td>
						<td class="optionTitleBoldLeft" width="20%">
							<input type="checkbox" name="userGroupsTemplateView" id="userGroupsTemplateView" value="Y" class="radio"><fmt:message key="label.usergroup"/>:
						</td>
						<td class="optionTitleBoldLeft" width="70%">
							<select class="selectBox" name="userGroupIdArray" id="userGroupIdArray" multiple size="5" rowspan="5">
								<c:forEach var="tmpBean" items="${userGroupColl}" varStatus="status">
									<option value="<c:out value="${tmpBean.userGroupId}"/>"><c:out value="${tmpBean.userGroupDesc}"/></option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${owner == 'USER_GROUP_ID'}">
					<tr>
						<td class="optionTitleBoldLeft" width="10%">
							&nbsp;
						</td>
						<td class="optionTitleBoldLeft" width="90%" colspan="2">
							<input type="checkbox" name="userGroupsTemplateView" id="userGroupsTemplateView" value="Y" class="radio"><fmt:message key="label.usergroup"/>: ${userGroupDesc}
						</td>
					</tr>
				</c:if>
			</c:otherwise>
		</c:choose>

	  <c:choose>
	  		<c:when test="${param.action == 'publish'}">
				<%-- company --%>
				<c:if test="${companyPermission == 'Y'}">
					<tr>
						<td class="optionTitleBoldLeft" width="10%">
							&nbsp;
						</td>
						<td class="optionTitleBoldLeft" width="90%" colspan="2">
							<input type="checkbox" name="companyTemplateView" id="companyTemplateView" value="Y" class="radio" onclick="companyViewChanged()"><fmt:message key="label.company"/>
						</td>
					</tr>
				</c:if>
			</c:when>
	 		<c:otherwise>
				<%-- company --%>
				<c:if test="${companyPermission == 'Y' && owner == 'COMPANY_ID'}">
					<tr>
						<td class="optionTitleBoldLeft" width="10%">
							&nbsp;
						</td>
						<td class="optionTitleBoldLeft" width="90%" colspan="2">
							<input type="checkbox" name="companyTemplateView" id="companyTemplateView" value="Y" class="radio" onclick="companyViewChanged()"><fmt:message key="label.company"/>
						</td>
					</tr>
				</c:if>
			</c:otherwise>
	 </c:choose>
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
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
	<input type="hidden" name="action" id="action" value='${param.action}'/>
	<input type="hidden" name="reportType" id="reportType" value='${param.reportType}'/>
	<input type="hidden" name="calledFrom" id="calledFrom" value='${param.calledFrom}'/>
	<input type="hidden" name="successFlag" id="successFlag" value='${successFlag}'/>
	<input type="hidden" name="tcmISError" id="tcmISError" value='${tcmISError}'/>
	<input type="hidden" name="templateId" id="templateId" value='${param.templateId}'/>
	<input type="hidden" name="owner" id="owner" value='${owner}'/> 
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
