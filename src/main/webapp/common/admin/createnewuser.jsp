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
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1"/>
	<meta http-equiv="expires" content="-1"/>
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:fontSizeCss />
	
	<%--<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
	<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />--%>
	
	<script SRC="/js/common/formchek.js" language="JavaScript"></script>
	<script SRC="/js/common/commonutil.js" language="JavaScript"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script src="/js/common/disableKeys.js" language="JavaScript"></script>
	<!-- This handels the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<script SRC="/js/common/admin/createnewuser.js" language="JavaScript"></script>
	<%-- Add any other javascript you need for the page here --%>
	
	<title>
	<fmt:message key="newUser"/>
	</title>
	
	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			missingData:"<fmt:message key="error.missingdata"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
		};
	</script>
</head>

<body bgcolor="#ffffff" onload="onMyLoad();">
	<tcmis:form action="/createnewuser.do" onsubmit="return submitOnlyOnce();">
		<div id="transitPage" style="display: none;text-align:center;">
			<p>
				<br/><br/><br/><fmt:message key="label.pleasewait"/>
			</p>
		 </div>
		 
		 <div class="interface" id="mainPage">
		 	<div class="contentArea">
		 		<!-- Search Option Begins -->
		 		<table id="searchMaskTable" width="400" border="0" cellpadding="0" cellspacing="0">
		 			<tr>
		 				<td>
		 					<div class="roundcont filterContainer">
		 						<div class="roundright">
		 							<div class="roundtop">
		 								<div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
		 							</div>
		 							<div class="roundContent">
		 								<!-- Insert all the search option within this div -->
		 								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		 									<tr>
		 										<td class="optionTitleBoldRight" nowrap>
		 											<fmt:message key="label.logonid"/>:
		 										</td>
		 										<td class="optionTitleBoldLeft">
		 											<input name="newLogonId" id="newLogonId" type="text" class="inputBox" size="20"/>
		 										</td>
		 									</tr>
		 									<tr>
		 										<td class="optionTitleBoldRight" nowrap>
		 											<fmt:message key="label.firstname"/>: *
		 										</td>
		 										<td class="optionTitleBoldLeft">
		 											<input name="firstName" id="firstName" type="text" class="inputBox" size="20"/>
		 										</td>
		 									</tr>
		 									<tr>
		 										<td class="optionTitleBoldRight" nowrap>
		 											<fmt:message key="label.lastname"/>: *
		 										</td>
		 										<td class="optionTitleBoldLeft">
		 											<input name="lastName" id="lastName" type="text" class="inputBox" size="20"/>
		 										</td>
		 									</tr>
		 									<tr>
		 										<td class="optionTitleBoldRight" nowrap>
		 											<fmt:message key="label.email"/>: *
		 										</td>
		 										<td class="optionTitleBoldLeft">
		 											<input name="email" id="email" type="text" class="inputBox" size="20"/>
		 										</td>
		 									</tr>
		 									<tr>
		 										<td class="optionTitleBoldRight" nowrap>
		 											<fmt:message key="label.phone"/>:
		 										</td>
		 										<td class="optionTitleBoldLeft">
		 											<input name="phone" id="phone" type="text" class="inputBox" size="20"/>
		 										</td>
		 									</tr>
		 									<tr>
		 										<td colspan="2" width="20%" class="optionTitleBoldCenter">
		 											<input name="createNew" id="createNew" type="submit" class="inputBtns" value="<fmt:message key="label.createUser"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return createNewUser()"/>
		 											<input type="button" name="cancel" id="cancel" value="<fmt:message key="button.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="parent.window.close()"/>
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
		 				</td>
		 			</tr>
		 		</table>
		 		<!-- Search Option Ends -->
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="action" id="action" value="" type="hidden"/>
					<input name="companyId" id="companyId" value="${companyId}" type="hidden"/>
					<input name="newPersonnelId" id="newPersonnelId" value="${newPersonnelId}" type="hidden"/>
					<input name="tcmisError" id="tcmisError" value="${tcmISError}" type="hidden"/>
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of content area -->
		</div>
		<!-- close of interface -->
	</tcmis:form>
</body>
</html:html>

