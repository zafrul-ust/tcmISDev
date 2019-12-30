<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>


<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
		<meta http-equiv="refresh" content="3600" />

		<link rel="shortcut icon"
			href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

		<link rel="stylesheet" type="text/css" href="/css/haasMenu.css"></link>
		<link rel="stylesheet" type="text/css" href="/css/tabs.css"></link>
		<tcmis:fontSizeCss />
		<script src="/js/common/commonutil.js" language="JavaScript"></script>
		<script src="/js/common/tabs.js" language="JavaScript"></script>
		<script src="/js/common/disableKeys.js" language="JavaScript"></script>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
		<%@ include file="/common/rightclickmenudata.jsp"%>
		<script type="text/javascript" src="/js/common/startapplication.js"></script>
		<c:set var="module">
			<tcmis:module />
		</c:set>
		
		<tcmis:singleSignOutLogout />
		
		<c:choose>
			<c:when test="${module == 'customer'}">
				${headerData}
			</c:when>
			<c:otherwise>
					<c:if test="${!standalone && module != 'usgov' && (empty requestedPage || requestedPage != 'checklog') }">
						<script>
							//Ensure this frame is the parent frame
							var standalone = "${standalone}";
							if ("true" != standalone && !window.name.endsWith('TcmisApplication')) {
								top.location='home.do';
							}
					
							if (top!=self) {
						  		top.location=self.document.location;
							}
						</script>
				</c:if>
			</c:otherwise>
		</c:choose>

		<title>
			<c:if test="${module != 'boeing'}">
				<fmt:message key="${module}" />
			</c:if>
			<fmt:message key="label.tcmis" />	
		</title> 
		<script language="JavaScript" type="text/javascript">		
		<c:set var="browserLocale" value="<%=request.getLocale()%>"/>
		<c:set var="defaultLocale" value="en_US"/>
   		<c:forEach var="localeBean" items="${vvLocaleBeanCollection}"  varStatus="status">
         		<c:if test="${localeBean.localeCode == browserLocale }">
					<c:set var="defaultLocale" value="${browserLocale}"/>     
	     		</c:if>
    		</c:forEach>
		<c:if test="${!empty param.langSetting}">
			<c:set var="defaultLocale" value="${param.langSetting}"/>
		</c:if>	

		var i18nloginlabel = new Array();

		<c:forEach var="localeBean" items="${vvLocaleBeanCollection}" varStatus="status">
			<fmt:setLocale value="${localeBean.localeCode}" scope="page"/>
			i18nloginlabel["${localeBean.localeCode}"] = {
				label_login:"<fmt:message key="label.logonid"/>",
				label_pass:"<fmt:message key="label.password"/>",
				label_lang:"<fmt:message key="label.language"/>",
				label_forgetpassword:"<fmt:message key="label.forgetpassword"/>",
				btn_login:"<fmt:message key="label.login"/>",
				btn_reset:"<fmt:message key="label.reset"/>"
			};
		</c:forEach>
		
		<fmt:setLocale value="${defaultLocale}" scope="page"/>

		function localeChange() {
			var localarr = i18nloginlabel[ document.getElementById('langSetting').value ] ;
			if( localarr == null ) 
				localarr = i18nloginlabel['en_US'] ;
			document.getElementById('label_login').innerHTML = localarr.label_login;
			document.getElementById('label_pass').innerHTML  = localarr.label_pass;
			document.getElementById('label_lang').innerHTML = localarr.label_lang;
			document.getElementById('label_forgetpassword').innerHTML = localarr.label_forgetpassword;
			document.getElementById('submitSearch').value= localarr.btn_login;
			document.getElementById('reset').value = localarr.btn_reset;
		}

		function getNewPassword() {
			<c:if test="${module == 'customer'}"> var loc = "/tcmIS/customer/forgetpassword.do?logonId="+$v("logonId"); </c:if> 
			<c:if test="${module != 'customer'}"> var loc = "/tcmIS/haas/forgetpassword.do?logonId="+$v("logonId")+"&langSetting="+$v("langSetting"); </c:if> 	
		 	openWinGeneric(loc,"forgetPassword","500","300","yes","80","80");
		}

		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
		};

		function onLoad() {
			<c:if test="${module == 'customer'  && !empty bodyData}">
				var portalImage = document.getElementById('portal');
				portalImage.src="/images/buttons/empty.gif";
				portalImage.style.width = "1px";
			
				x = document.getElementById('loginDiv');
				y = document.getElementById('loginTable');
				x.appendChild(y);
				document.loginForm.logonId.focus();
			</c:if>
			document.loginForm.logonId.focus();
		}
		
		
		</script>
</head>

<body bgcolor="#ffffff" onload="onLoad();">
	<tcmis:form action="/login.do" onsubmit="return submitOnlyOnce();" onreset="document.loginForm.logonId.focus()">
		<c:choose>
			<c:when test="${module == 'customer' && !empty bodyData}">
				${bodyData}
				<span id="loginTable">
			</c:when>
			<c:otherwise>
				<div id="interface" id="mainPage">
					<div class="header">
						<div class="alignLeft"><%@ include
								file="/common/application/clientlogo.jsp"%></div>
						<div class="alignRight"></div>
					</div>
					<div class="menus">
						<div class="menuContainer">&nbsp;</div>
						<div class="tabMenu">
							<div class="title">&nbsp;</div>
						</div>
					</div>
					<div class="contentArea">
						<div class="spacerY">&nbsp;</div>
						<table id="searchMaskTable" width="950" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="175" height="400" valign="top"></c:otherwise> </c:choose>

									<div class="roundcont filterContainer">
										<div class="roundright">
											<div class="roundtop">
												<div class="roundtopright">
													<img src="/images/rndBoxes/borderTL_filter.gif" alt=""
														width="15" height="10" class="corner_filter"
														style="display: none" />
												</div>
											</div>
											<div class="roundContent">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" class="tableSearch">
													<tr valign="top">
														<td width="5%" height="20" class="optionTitleBoldRight" nowrap="nowrap">
															<span id="label_login"><fmt:message key="label.logonid" /></span>:</td>
														<td width="80%" class="optionTitleLeft">
															<input class="inputBox" type="text" name="logonId" id="logonId"
															value="<tcmis:inputTextReplace value="${param.logonId}" processMultiLines="true" />"
															size="10" size="15"></td>
													</tr>
													<tr valign="top">
														<td height="20" class="optionTitleBoldRight"><span
															id="label_pass"><fmt:message key="label.password" /></span>:</td>
														<td class="optionTitleLeft">
															<input class="inputBox" type="password" name="password" id="password" AUTOCOMPLETE="off" value="" size="10" size="15"></td>
													</tr>
													<c:if test="${initParam.standalone != 'true'}">
														<tr valign="top">
															<td height="20" class="optionTitleBoldRight"
																nowrap="nowrap"><span id="label_lang"> <fmt:message
																		key="label.language" /></span>:</td>
															<td class="optionTitleLeft">
																<select class="selectBox" name="langSetting" id="langSetting" onChange="localeChange()">
																	<c:forEach var="localeBean" items="${vvLocaleBeanCollection}" varStatus="status">
																		<option value="${localeBean.localeCode}"
																			<c:if test="${localeBean.localeCode == defaultLocale}">selected="selected"</c:if>>${localeBean.localeDescription}</option>
																	</c:forEach>
															</select></td>
														</tr>
													</c:if>
													<tr valign="top">
														<td class="">
															<html:submit property="search"
																styleId="submitSearch" styleClass="smallBtns"
																onmouseover="this.className='smallBtns smallBtnsOver'"
																onmouseout="this.className='smallBtns'">
																<fmt:message key="label.login" />
															</html:submit></td>
														<td class="">
															<html:reset property="reset"
																styleId="reset" styleClass="smallBtns"
																onmouseover="this.className='smallBtns smallBtnsOver'"
																onmouseout="this.className='smallBtns'">
																<fmt:message key="label.reset" />
															</html:reset></td>
													</tr>
												<%-- 	<c:if test="${module == 'customer'}">  --%>
														<tr valign="middle">
															<td width="5%" colspan="2" class="">&nbsp;
																<div class="boxhead">
																	<a href="#" onclick="getNewPassword()">
																		<span id="label_forgetpassword"><fmt:message key="label.forgetpassword" /></span>
																	</a>
																</div>
															</td>
														</tr>
												<%--	</c:if>   --%>
													<tr valign="middle">
														<td width="5%" colspan="2" class="">&nbsp;</td>
													</tr>
													<tr valign="top">
														<td width="5%" colspan="2" class="">
															<div id="errorMessagesArea" class="errorMessages">
																<html:errors />
																<c:if test="${!empty lostSession}">
																	<c:choose>
																		<c:when test="${lostSession == 'lostSession'}">
																			<fmt:message key="label.timeoutmessage1" /></BR>
																			<fmt:message key="label.timeoutmessage2" /> ${(timeout / 1000) / 60} 
																			<fmt:message key="label.timeoutmessage3" /></BR>
																			<fmt:message key="label.timeoutmessage4" />
																		</c:when>
																		<c:otherwise>
																			<fmt:message key="label.sessionlost" />
																		</c:otherwise>
																	</c:choose>
																</c:if>
															</div>
														</td>
													</tr>
													<c:if test="${module != 'customer'  || empty bodyData}">
														<tr valign="middle">
															<td width="5%" colspan="2" class="">&nbsp;</td>
														</tr>
														<tr valign="middle">
															<td width="5%" colspan="2" class="">&nbsp;</td>
														</tr>
														<tr valign="middle">
															<td width="5%" colspan="2" class="">&nbsp;</td>
														</tr>
														<tr valign="middle">
															<td width="5%" colspan="2" class="">&nbsp;</td>
														</tr>
													</c:if>
												</table>
											</div>
											<div class="roundbottom">
												<div class="roundbottomright">
													<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
												</div>
											</div>
										</div>
									</div> <c:choose>
										<c:when test="${module == 'customer'  && !empty bodyData}">
											</span>
										</c:when>
										<c:otherwise></td>
								<td width="10" valign="top">&nbsp;</td>
								<td width="765" valign="top">
									<c:if test="${connectionPoolMessageBeanCollection != null}">
										<div class="roundcont filterContainer">
											<div class="roundright">
												<div class="roundtop">
													<div class="roundtopright">
														<img src="/images/rndBoxes/borderTL_filter.gif" alt=""
															width="15" height="10" class="corner_filter"
															style="display: none" />
													</div>
												</div>
												<div class="roundContent">
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
														<tr>
															<td class="optionTitleBoldRight">Message</td>
														</tr>
													</table>
												</div>
												<div class="roundbottom">
													<div class="roundbottomright">
														<img src="/images/rndBoxes/borderBL.gif" alt="" width="15"
															height="15" class="corner" style="display: none" />
													</div>
												</div>
											</div>
										</div>
									</c:if></td>
							</tr>
						</table>
			</c:otherwise>
		</c:choose>
		<div id="hiddenElements" style="display: none;">
		<%@ include file="/common/application/dbclient.jsp"%>
			<html:hidden property="home" styleId="home" value="" />
			<html:hidden property="requestedPage" styleId="requestedPage" value="${requestScope.requestedPage}" />
			<html:hidden property="requestedURLWithParameters" styleId="requestedURLWithParameters" value="${requestScope.requestedURLWithParameters}" />
		</div>
		<c:if test="${module != 'customer' || empty bodyData}">
			</div>
			</div>
		</c:if>
	</tcmis:form>
</body>
</html:html>
