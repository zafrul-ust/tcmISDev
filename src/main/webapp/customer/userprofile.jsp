<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>
<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --> <tcmis:fontSizeCss /> <!-- CSS for YUI --> <%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%> <!-- Add any other stylesheets you need for the page here --> <%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%> 
<script type="text/javascript" src="/js/common/formchek.js"></script> 
<script type="text/javascript" src="/js/common/commonutil.js"></script> 
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script> 

<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
 
<!-- This handels the menu style and what happens to the right click on the whole page --> 
<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 

<%@ include file="/common/rightclickmenudata.jsp"%> 
<!-- For Calendar support --> <%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%> 
<!-- Add any other javascript you need for the page here --> 
<script type="text/javascript" src="/js/client/customer/opencustomeruserprofile.js"></script> 

<!-- These are for the Grid, uncomment if you are going to use the grid --> <%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%> <!-- This is for the YUI, uncomment if you will use this --> <%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title><fmt:message key="label.userProfile" /></title>

<script language="JavaScript" type="text/javascript">
<!--
var altCompanyId = new Array(""
	<c:forEach var="companyB" items="${companyBean}" varStatus="status">
	,"${status.current.companyId}"
	</c:forEach>
);
var altCompanyName = new Array(""
	<c:forEach var="companyB" items="${companyBean}" varStatus="status">
	,"${status.current.companyName}"
	</c:forEach>
);
var requiredFTitle = new Array( 	"<fmt:message key="label.firstname"/>",
							"<fmt:message key="label.lastname"/>",
							"<fmt:message key="label.email"/>",
							"<fmt:message key="label.password"/>",
							"<fmt:message key="label.confirm"/> <fmt:message key="label.password"/>",
							"<fmt:message key="label.logonid"/>"
						 );
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
createUser:"<fmt:message key="label.createUser"/>",
required:"<fmt:message key="msg.errorHeader"/>",
cancel:"<fmt:message key="label.cancel"/>",
passwordEqual:"<fmt:message key="label.passwordEqual"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="">
<!--Uncomment for production-->
<tcmis:form action="/userprofilemain.do" onsubmit="return submitFrameOnlyOnce();" >

	<div class="interface" id="searchMainPage"><!-- Start of interface-->
	<div id="transitPage" class="optionTitleBoldCenter" style="display: none"><br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
	</div>

	<div class="contentArea" id="contentArea"><!-- Start of contentArea--> <!-- Search Option Begins -->
	<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<div class="roundcont filterContainer">
			<div class="roundright">
			<div class="roundtop">
			<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
			</div>
			<div class="roundContent"><!-- Insert all the search option within this div --> <!-- Search Option Table Start -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch" id="tableSearch">
				<!--  didn't user box head.... -->
				<tr>
					<td colspan="6">
						<div id="DIV_topLinks" class="boxhead">
							<c:if test="${userId == personnelBean.personnelId }">
								<a href="#" onclick="updatePersonalInfo()"><fmt:message key="label.updatePersonalInfo" /></a>
								| <a href="#" onClick="changePassword()"><fmt:message key="label.changePassword" /></a>
							</c:if>
						</div>
					</td>
				</tr>
				<tr>
					<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.logonid" />:</td>
					<td width="23%" class="optionTitleLeft">
						<input type="hidden" name="logonId" id="logonId" value="${personnelBean.logonId}" />
						<div id="DIVlogonId">${personnelBean.logonId}</div>
					</td>
					<td width="10%" class="optionTitleBoldRight">
						<div id="DIVlogonIdLabel" style="display: ">
							<fmt:message key="label.tcmis" /> <fmt:message key="label.id" />:
							</div>
					</td>
					<td width="23%" class="optionTitleLeft">
						<input type="hidden" name="personnelId" id="personnelId" value="${personnelBean.personnelId}" />
						<div id="DIVPersonnelId" style="display: ">${personnelBean.personnelId}</div>
						<div id="DIVPassword" style="display: none"><input class="inputBox" type="password" name="password" id="password" value="" size="20" /></div>
					</td>
					<td class="optionTitleBoldRight">
						<div id="DIVComfirmPasswordLabel" style="display: none"><fmt:message key="label.confirm" /> <fmt:message key="label.password" />:</div>
					</td>
					<td class="optionTitleLeft">
						<div id="DIVComfirmPassword" style="display: none"><input type="password" class="inputBox" name="confirmPassword" id="confirmPassword" value="" size="20" /></div>
					</td>
				</tr>
				<tr>
					<td width="10%" class="optionTitleBoldRight">
						<span><fmt:message key="label.firstname" /></span>:
					</td>
					<td width="23%" class="optionTitleLeft">
						<c:if test="${userId == personnelBean.personnelId}">
							<input class="inputBox" name="firstName" id="firstName" value="${personnelBean.firstName}" size="20" />
						</c:if> 
						<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
							<input type="hidden" name="firstName" id="firstName" value="${personnelBean.firstName}" />		 
		   					${personnelBean.firstName}
						</c:if>
					</td>
					<td width="10%" class="optionTitleBoldRight">
						<span><fmt:message key="label.lastname" /></span>:
					</td>
					<td width="23%" class="optionTitleLeft">
						<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
							<input class="inputBox" name="lastName" id="lastName" value="${personnelBean.lastName}" size="20" />
						</c:if> 
						<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
							<input type="hidden" name="lastName" id="lastName" value="${personnelBean.lastName}" />
		   					${personnelBean.lastName}
						</c:if>
					</td>
					<td width="10%" class="optionTitleBoldRight">
						<fmt:message key="label.MI" />:
					</td>
					<td width="23%" class="optionTitleLeft">
						<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
							<input class="inputBox" name="midInitial" id="midInitial" value="${personnelBean.midInitial}" size="3" />
						</c:if>
						<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
 							${personnelBean.midInitial}
						</c:if>
					</td>
				</tr>
				<tr>
					<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.phone" />:</td>
					<td width="23%" class="optionTitleLeft"><c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
						<input class="inputBox" name="phone" id="phone" value="${personnelBean.phone}" size="20" />
					</c:if> <c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
 		${personnelBean.phone}
		</c:if></td>
					<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.fax" />:</td>
					<td width="23%" class="optionTitleLeft"><c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
						<input class="inputBox" name="fax" id="fax" value="${personnelBean.fax}" size="20" />
					</c:if> <c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
 		${personnelBean.fax}
		</c:if></td>
					<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.cell" />:</td>
					<td width="23%" class="optionTitleLeft"><c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
						<input class="inputBox" name="altPhone" id="altPhone" value="${personnelBean.altPhone}" size="20" />
					</c:if> <c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
 		${personnelBean.altPhone}
		</c:if></td>
				</tr>
				<tr>
					<td width="10%" class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.email" />:</td>
					<td width="23%" class="optionTitleLeft"><c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
						<input class="inputBox" name="email" id="email" value="${personnelBean.email}" size="20" />
					</c:if> <c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
 		${personnelBean.email}
		</c:if></td>
					<td width="10%" class="optionTitleBoldRight">
					</td>
					<td width="23%" class="optionTitleLeft">
					</td>
					<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.fontsize" />:</td>
					<td width="23%" class="optionTitleLeft">
						<c:set var="fontSizePreference" value='${personnelBean.fontSizePreference}' />
						<html:select property="fontSizePreference" styleClass="selectBox" value="${fontSizePreference}" styleId="fontSizePreference">
							<html:option value="Smallest" key="label.extrasmall" />
							<html:option value="Small" key="label.small" />
							<html:option value="Medium" key="label.medium" />
							<html:option value="Large" key="label.large" />
							<html:option value="Largest" key="label.extralarge" />
						</html:select> 
					</td>
				</tr>
			</table>
			<!-- Search Option Table end --></div>
			<div class="roundbottom">
			<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
			</div>
			</div>
			</div>
			</td>
		</tr>
	</table>
	<!-- Search Option Ends --> <!-- Error Messages Begins --> <!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
--> <c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
		<div class="spacerY">&nbsp;
		<div id="errorMessagesArea" class="errorMessages"><html:errors /></div>
		</div>
	</c:if> <!-- Error Messages Ends --></div>
	<!-- close of contentArea --> <!-- Hidden element start --> <c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
		<script language="JavaScript" type="text/javascript">
<!--
	var dontShowButtom = true;
// -->
</script>
	</c:if>
	<div id="hiddenElements" style="display: none;"><input type="hidden" name="action" id="action" value="update" /> <input type="hidden" name="createNewUserCount" id="createNewUserCount" value="${createNewUserCount}" /> <input type="hidden"
		name="isUserCompanyAdmin" id="isUserCompanyAdmin" value="${isUserCompanyAdmin}"
	/> <input type="hidden" name="isUserFacilityAdmin" id="isUserFacilityAdmin" value="${isUserFacilityAdmin}" /></div>
	<!-- Hidden elements end --></div>
	<!-- close of interface -->

</tcmis:form>
<div id="resultFrameDiv"  style="display: none;"></div>
<div id="errorMessagesAreaBody" style="display: none;"><html:errors /> ${tcmISError}</div>
<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
</body>
</html:html>