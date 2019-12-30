<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

<script src="/js/version.js" language="JavaScript"></script>

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
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/userprofile.js"></script>


<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<c:set var="module"><tcmis:module /></c:set>
<title>
	<fmt:message key="label.userProfile"/>
</title>

<script language="JavaScript" type="text/javascript">
	var altCompanyId = new Array(""
		<c:forEach var="companyB" items="${companyBean}">
			,"<tcmis:jsReplace value="${companyB.companyId}"/>"
		</c:forEach>
	);
	
	var altCompanyName = new Array(""
		<c:forEach var="companyB" items="${companyBean}">
			,"<tcmis:jsReplace value="${companyB.companyName}"/>"
		</c:forEach>
	);
	
	var requiredFTitle = new Array( 	
			"<fmt:message key="label.firstname"/>",
			"<fmt:message key="label.lastname"/>",
			"<fmt:message key="label.email"/>",
			"<fmt:message key="label.password"/>",
			"<fmt:message key="label.confirm"/> <fmt:message key="label.password"/>",
			"<fmt:message key="label.logonid"/>"
	);
	
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		createUser:"<fmt:message key="label.createUser"/>",
		required:"<fmt:message key="msg.errorHeader"/>",
		cancel:"<fmt:message key="label.cancel"/>",
		passwordEqual:"<fmt:message key="label.passwordEqual"/>",
		password:"<fmt:message key="label.password"/>",
		pleaseSelectAHub:"<fmt:message key="label.pleaseselectahub"/>",
		logonidexist:"<fmt:message key="error.logonidexist"/>"
	};
</script>

<c:set var="showEnvironment" value="false" />
<c:forEach var="userGroup" items="${personnelBean.permissionBean.userGroupMemberBeanCollection}">
	<c:if test="${userGroup.userGroupId == 'developers' || userGroup.userGroupId == 'operationSupport'}">
		<c:set var="showEnvironment" value="true" />
	</c:if>
</c:forEach>
</head>

<body bgcolor="#ffffff" onload="myOnload()">
	<!--Uncomment for production-->
	<tcmis:form action="/userprofilesearch.do" onsubmit="return submitFrameOnlyOnce();" target="searchFrame">
		<!-- Start of interface-->
		<div class="interface" id="searchMainPage">
			<div id="transitPage" class="optionTitleBoldCenter" style="display:none">
				<br><br><br><fmt:message key="label.pleasewait"/>
				<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
			
			<!-- Start of contentArea-->
			<div class="contentArea" id="contentArea">
				<!-- Search Option Begins -->
				<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="roundcont filterContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
									</div>
									<div class="roundContent">
										<!-- Insert all the search option within this div -->
										<!-- Search Option Table Start -->
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch" id="tableSearch">
										<!--  didn't user box head.... -->
											<tr>
												<td id="topLinks" colspan="4">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<a href="#" onclick="updatePersonalInfo()">
															<fmt:message key="label.updatePersonalInfo"/>
														</a>
													</c:if>
													<c:if test="${userId == personnelBean.personnelId }">
														|
														<a href="#" onClick="changePassword()">
															<fmt:message key="label.changePassword"/>
														</a>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount > 0}">
														|
														<a href="#" onClick="resetPassword()">
															<fmt:message key="label.resetpassword"/>
														</a>
													</c:if>
												</td>
											</tr>
											<tr>
												<td id="companyLabel" class="optionTitleBoldRight" style="display:none" nowrap>
													<fmt:message key="label.company"/>:
												</td>
												<td id="companyDropbox" class="optionTitleLeft" style="display:none" nowrap>
													<select name="companyId" id="companyId" class="selectBox">
														<option value="${personnelBean.companyId}">
															${personnelBean.companyName}
														</option>
													</select>
												</td>
												<c:if test="${personnelBean.operatingCompanyEmployee || createNewUserCount > 0}">
													<td id="environmentLabel" class="optionTitleBoldRight" nowrap>
														<fmt:message key="tablet.environment"/>:
													</td>
													<td id="environmentText" class="optionTitleLeft" colspan="3">
														<tcmis:environment/>
													</td>
												</c:if>
											</tr>
											<tr>
												<td id="logonIdLabel" class="optionTitleBoldRight" nowrap>
													<fmt:message key="label.logonid"/>:
												</td>
												<td id="logonIdText" class="optionTitleLeft">
													${personnelBean.logonId}
													<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="personnelLookup" id="personnelLookup" align="right" onClick="lookupPersonnel()"/>
													<c:if test="${createNewUserCount > 0 }">
														<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="<fmt:message key="label.newuser"/>" onclick="showNewUserView(false)" />
													</c:if>
												</td>
												<td id="personnelIdLabel" class="optionTitleBoldRight" style="display:" nowrap>
													<fmt:message key="label.tcmis"/> <fmt:message key="label.id"/>:
												</td>
												<td id="personnelIdText" class="optionTitleLeft">
													${personnelBean.personnelId}
												</td>
												<td id="confirmPasswordLabel" class="optionTitleBoldRight" style="display:none" nowrap>
													<fmt:message key="label.confirm"/> <fmt:message key="label.password"/>: *
												</td>
												<td id="confirmPasswordText" class="optionTitleLeft" style="display:none">
													<input  type="password" class="inputBox" name="confirmPassword" id="confirmPassword" value="" size="20"/>
												</td>
											</tr>
											<tr>
												<td id="firstNameLabel" class="optionTitleBoldRight" nowrap>
													<fmt:message key="label.firstname"/>:
												</td>
												<td class="optionTitleLeft">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<input class="inputBox" name="firstName" id="firstName" value="${personnelBean.firstName}" size="20"/>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
														${personnelBean.firstName}
													</c:if>
												</td>
												<td id="lastNameLabel" class="optionTitleBoldRight" nowrap>
													<fmt:message key="label.lastname"/>:
												</td>
												<td class="optionTitleLeft">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<input class="inputBox" name="lastName" id="lastName" value="${personnelBean.lastName}" size="20"/>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
														${personnelBean.lastName}
													</c:if>
												</td>
												<td class="optionTitleBoldRight">
													<fmt:message key="label.MI"/>:
												</td>
												<td class="optionTitleLeft">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<input class="inputBox" name="midInitial" id="midInitial" value="${personnelBean.midInitial}" size="3"/>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
														${personnelBean.midInitial}
													</c:if>
												</td>
											</tr>
											<tr>
												<td class="optionTitleBoldRight" nowrap>
													<fmt:message key="label.phone"/>:
												</td>
												<td class="optionTitleLeft">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<input class="inputBox" name="phone" id="phone" value="${personnelBean.phone}" size="20"/>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
														${personnelBean.phone}
													</c:if>
												</td>
												<td class="optionTitleBoldRight" nowrap>
													<fmt:message key="label.fax"/>:
												</td>
												<td class="optionTitleLeft">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<input class="inputBox" name="fax" id="fax" value="${personnelBean.fax}" size="20"/>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
														${personnelBean.fax}
													</c:if>
												</td>
												<td class="optionTitleBoldRight" nowrap>
													<fmt:message key="label.cell"/>:
												</td>
												<td class="optionTitleLeft">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<input class="inputBox" name="altPhone" id="altPhone" value="${personnelBean.altPhone}" size="20"/>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
														${personnelBean.altPhone}
													</c:if>
												</td>
											</tr>
											<tr>
												<td id="emailLabel" class="optionTitleBoldRight" nowrap="nowrap">
													<fmt:message key="label.email"/>:
												</td>
												<td class="optionTitleLeft">
													<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
														<input class="inputBox" name="email" id="email" value="${personnelBean.email}" size="20"/>
													</c:if>
													<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
														${personnelBean.email}
													</c:if>
												</td>
												<td class="optionTitleBoldRight" nowrap>
													<c:if test='${!personnelBean.standalone}' >
														<fmt:message key="label.labelPrinter"/>:
													</c:if>
												</td>
												<td class="optionTitleLeft" nowrap="nowrap">
													<c:if test="${!personnelBean.standalone}" >
														<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
															<select class="selectBox" name="printerLocation" id="printerLocation">
																<option value=""><fmt:message key="label.pleaseselect"/></option>
																<c:forEach var="bean" items="${printerLocationBean}" varStatus="status">
																	<option <c:if test="${personnelBean.printerLocation == status.current}">selected="selected"</c:if> >${status.current}</option>
																</c:forEach>
															</select>
															<input type="button" class="smallBtns" name="testLabel" id="testLabel" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="<fmt:message key="label.testlabel"/>" onclick="printTestLabel()" />
														</c:if>
														<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
															${personnelBean.printerLocation}
														</c:if>
													</c:if>
												</td>
												<td class="optionTitleBoldRight" nowrap>
													<fmt:message key="label.fontsize"/>:
												</td>
												<td class="optionTitleLeft">
													<html:select property="fontSizePreference" styleClass="selectBox" value="${personnelBean.fontSizePreference}" styleId="fontSizePreference">
														<html:option value="Smallest" key="label.extrasmall"/>
														<html:option value="Small" key="label.small"/>
														<html:option value="Medium" key="label.medium"/>
														<html:option value="Large" key="label.large"/>
														<html:option value="Largest" key="label.extralarge"/>
													</html:select>
												</td>
											</tr>
											<c:choose>
												<c:when test='${module == "haas"}'>
													<tr>
														<td colspan="2"/>
														<td class="optionTitleBoldRight" nowrap>
															<fmt:message key="label.jdeuserabno"/>:
														</td>
														<td class="optionTitleLeft">
															<c:choose>
																<c:when test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
																	<input class="inputBox" name="jdeUserAddressBookNo" id="jdeUserAddressBookNo" value="${personnelBean.jdeUserAddressBookNo}" size="20"/>
																</c:when>
																<c:otherwise>
																	${personnelBean.jdeUserAddressBookNo}
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:when>
												<c:otherwise>
													<input type="hidden" name="jdeUserAddressBookNo" id="jdeUserAddressBookNo" value="${personnelBean.jdeUserAddressBookNo}"/>
												</c:otherwise>
											</c:choose>
											<c:if test="${!personnelBean.standalone}" > 
												<tr>
													<td colspan="2" class="optionTitleLeft">
														<div id="DIVButtonsId">
															<c:if test="${userId == personnelBean.personnelId || createNewUserCount > 0}">
																<input type="button" name="startUpPage" id="startUpPage" value="<fmt:message key="label.startupPages"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="openStartupPages()"/>
															    <c:if test='${module == "cv" && createNewUserCount > 0}'>
															        <input type="button" name="pagesPage" id="pagesPage" value="<fmt:message key="label.pages"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="openPagesPages()"/>
															    </c:if>
															</c:if>
														</div>
													</td>
													<td colspan="4" class="optionTitleLeft"></td>
												</tr>
											</c:if>
										</table>
										<!-- Search Option Table end -->
									</div>
									
									<div class="roundbottom">
										<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
				<!-- Search Option Ends -->
				
				<!-- Error Messages Begins -->
				<!-- Build this section only if there is an error message to display.
				For the search section, we show the error messages within its frame-->
				<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
					<div class="spacerY">&nbsp;
						<div id="errorMessagesArea" class="errorMessages">
							<html:errors/>
						</div>
					</div>
				</c:if>
				<!-- Error Messages Ends -->
			</div>
			<!-- close of contentArea -->
			
			<!-- Hidden element start -->
			<c:if test="${userId != personnelBean.personnelId && createNewUserCount <= 0}">
				<script language="JavaScript" type="text/javascript">
					var dontShowButtom = true;
				</script>
			</c:if>
			
			<div id="hiddenElements" style="display: none;">
				<input type="hidden" name="action" id="action" value="${action}"/>
				<input type="hidden" name="logonId" id="logonId" value="${personnelBean.logonId}"/>
				<input type="hidden" name="personnelId" id="personnelId" value="${personnelBean.personnelId}"/>
				<input type="hidden" name="firstName" id="firstName" value="${personnelBean.firstName}"/>
				<input type="hidden" name="lastName" id="lastName" value="${personnelBean.lastName}"/>
				<input type="hidden" name="createNewUserCount" id="createNewUserCount" value="${createNewUserCount}"/>
				<input type="hidden" name="isUserCompanyAdmin" id="isUserCompanyAdmin" value="${isUserCompanyAdmin}"/>
				<input type="hidden" name="isUserFacilityAdmin" id="isUserFacilityAdmin" value="${isUserFacilityAdmin}"/>
			</div>
			<!-- Hidden elements end -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
	
	<div id="errorMessagesAreaBody" style="display:none;">
		<html:errors/>
		${tcmISError}
	</div>
	
	<script type="text/javascript">
		/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
		<c:choose>
			<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
				showErrorMessage = false;
			</c:when>
			<c:otherwise>
				showErrorMessage = true;
			</c:otherwise>
		</c:choose>
	</script>
</body>
</html:html>