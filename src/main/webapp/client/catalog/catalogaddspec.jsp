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
<script type="text/javascript" src="/js/client/catalog/catalogaddspec.js"></script>
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
	<fmt:message key="label.addeditspec"/>
</title>

</head>


<body bgcolor="#ffffff" onLoad="editOnLoad('${param.uAction}');closeAllchildren();" onunload="checkClose('${param.uAction}');">

<script language="JavaScript" type="text/javascript">
<!--

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
spec:"<fmt:message key="label.spec"/>",
title:"<fmt:message key="label.title"/>",	
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
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

	<table class="tableSearch" border="0">
			 <tr>
				<td><a href="#" onclick="submitUpdate(); return false;"><fmt:message key="button.submit"/></a>
					&nbsp;|&nbsp;<a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a>
				</td>
			 </tr>
   </table>

<div>

<div id="section1" class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

	 <table class="tableSearch" border="0">
		<tr>
			<td>
				<span id="specSpan" style="display: none">
					<table class="tableSearch" border="0">
						 <tr>
							 <td class="optionTitleBoldRight">
								 <fmt:message key="label.spec"/>:*
							 </td>
							 <td class="optionTitleBoldLeft">
								 <input class="inputBox" type="text" name="specName" id="specName" value="${specData.specName}" size="50" maxlength="23">
							 </td>
						 </tr>
						 <tr>
							 <td class="optionTitleBoldRight">
								 <fmt:message key="label.revision"/>:
							 </td>
							 <td class="optionTitleBoldLeft">
								 <input class="inputBox" type="text" name="specVersion" id="specVersion" value="${specData.specVersion}" size="50" maxlength="15">
							 </td>
						 </tr>
						 <tr>
							 <td class="optionTitleBoldRight">
								 <fmt:message key="label.amendment"/>:
							 </td>
							 <td class="optionTitleBoldLeft">
								 <input class="inputBox" type="text" name="specAmendment" id="specAmendment" value="${specData.specAmendment}" size="50" maxlength="10">
							 </td>
						 </tr>
						 <tr>
							 <td class="optionTitleBoldRight">
								 <fmt:message key="label.title"/>:*
							 </td>
							 <td class="optionTitleBoldLeft">
								 <input class="inputBox" type="text" name="specTitle" id="specTitle" value="${specData.specTitle}" size="50" maxlength="100">
							 </td>
						 </tr>
						 <tr>
							 <c:set var="tmpCoc" value='${specData.coc}'/>
							 <c:set var="cocChecked" value=''/>
							 <c:if test="${tmpCoc == 'Y'}">
								<c:set var="cocChecked" value='checked="checked"'/>
							 </c:if>

							 <c:set var="tmpCoa" value='${specData.coa}'/>
							 <c:set var="coaChecked" value=''/>
							 <c:if test="${tmpCoa == 'Y'}">
								<c:set var="coaChecked" value='checked="checked"'/>
							 </c:if>

							 <td class="optionTitleBoldRight">
								 <input type="checkbox" name="coc" id="coc" value="Y" class="radioBtns" ${cocChecked}><fmt:message key="label.coc"/>
							 </td>
							 <td class="optionTitleBoldLeft">
								 <input type="checkbox" name="coa" id="coa" value="Y" class="radioBtns" ${coaChecked}><fmt:message key="label.coa"/>
							 </td>
						</tr>
					</table>
				</span>
			</td>
		</tr>

		 <tr>
			 <td>
				 <span id="smcSpan" style="display: none">
					 <table class="tableSearch" border="0">
						<tr>
							<td class="optionTitleBoldCenter">
								 <fmt:message key="label.standardmfgcert"/>
							</td>
						</tr>
						<tr>
							<c:set var="tmpCoc" value='${specData.coc}'/>
							 <c:set var="cocChecked" value=''/>
							 <c:if test="${tmpCoc == 'Y'}">
								<c:set var="cocChecked" value='checked="checked"'/>
							 </c:if>

							 <c:set var="tmpCoa" value='${specData.coa}'/>
							 <c:set var="coaChecked" value=''/>
							 <c:if test="${tmpCoa == 'Y'}">
								<c:set var="coaChecked" value='checked="checked"'/>
							 </c:if>

							<td class="optionTitleBoldCenter">
								 <input type="checkbox" name="smcCoc" id="smcCoc" value="Y" class="radioBtns" ${cocChecked}><fmt:message key="label.coc"/>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldCenter">
								 <input type="checkbox" name="smcCoa" id="smcCoa" value="Y" class="radioBtns" ${coaChecked}><fmt:message key="label.coa"/>
							</td>
						</tr>
					</table>
				 </span>
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
<input type="hidden" name="requestId" id="requestId" value='${specData.requestId}'/>
<input type="hidden" name="companyId" id="companyId" value='${specData.companyId}'/>
<input type="hidden" name="specId" id="specId" value='${specData.specId}'/>	
<input type="hidden" name="calledFrom" id="calledFrom" value='${param.calledFrom}'/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

</body>
</html:html>