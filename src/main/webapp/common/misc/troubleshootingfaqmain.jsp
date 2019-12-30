<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

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
<link rel="shortcut icon"
	href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%> <!--Use this tag to get the correct CSS class.
	This looks at what the user's preferred font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss /> <%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script> 
<script type="text/javascript" src="/js/common/commonutil.js"></script> <!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> <!-- This handles which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
<script	type="text/javascript" src="/js/menu/mmenudom.js"></script> 
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
<%@ include file="/common/rightclickmenudata.jsp"%> 

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here --> 
<script type="text/javascript" src="/js/common/standardGridmain.js"></script> 
<script type="text/javascript" src="/js/common/admin/troubleshootingfaqmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title><fmt:message key="label.troubleshootingfaq" /></title>

<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData=
			{alert:"<fmt:message key="label.alert"/>",
			all:"<fmt:message key="label.all"/>",
			errors:"<fmt:message key="label.errors"/>",      
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			searchInput:"<fmt:message key="label.searchargumentrequired"/>"};
		
		/*Define any arrays you need to do connected dropdowns.*/
		
		// -->
	</script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout.
It is important to send the pageId if this page is going to open in a tab in the application.
You can also call any functions you need to do your search initialization for drop downs.
No need to pass page name as it is not used.
-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','troubleShootingFAQ');document.getElementById('searchArgument').focus()"
	onresize="resizeFrames()">

<div class="interface" id="mainPage" style=""><!-- Search div Begins -->
<div id="searchFrameDiv"><!-- start of contentArea -->
<div class="contentArea">
<tcmis:form
	action="/troubleshootingfaqresults.do"
	onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

	<!-- Search Option Begins -->
	<%--Change the width to what you want your page to span.--%>
	<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<div class="roundcont filterContainer">
			<div class="roundright">
			<div class="roundtop">
			<div class="roundtopright">
				<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
			</div>
			</div>
			<div class="roundContent"><!-- Insert all the search option within this div -->
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableSearch">
				<tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.page" />:</td>
					<td nowrap class="optionTitleBoldLeft">
						<select name="pageField" class="selectBox" id="pageField">
						<option value=""><fmt:message key="label.pleaseselect" /></option>
						<c:forEach var="allPageNames" items="${pageCollection}" varStatus="status">
							<option value="${status.current.pageId}" ><fmt:message key="${status.current.pageId}"/></option>
						</c:forEach>
					</select> &nbsp;
				</td>
				</tr>
				<tr>
					<td class="optionTitleBoldRight"><fmt:message
						key="label.search" />:</td>
					<td nowrap class="optionTitleBoldLeft">
						<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="40" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<html:submit property="submitSearch"
							styleId="submitSearch" styleClass="inputBtns"
							onmouseover="this.className='inputBtns inputBtnsOver'"
							onmouseout="this.className='inputBtns'"
							onclick="return submitSearchForm();">
							<fmt:message key="label.search" />
						</html:submit> 
						<html:button property="createExcel" styleId="submitSearch"
							styleClass="inputBtns"
							onmouseover="this.className='inputBtns inputBtnsOver'"
							onmouseout="this.className='inputBtns'"
							onclick="createXSL(); return false;">
							<fmt:message key="label.createexcelfile" />
						</html:button>
					</td>
				</tr>
			</table>
			<!-- End search options --></div>
			<div class="roundbottom">
				<div class="roundbottomright">
					<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
				</div>
			</div>
			</div>
			</div>
			</td>
		</tr>
	</table>
	<!-- Search Option Ends -->

	<!-- Error Messages Begins -->
	<!-- Build this section only if there is an error message to display.
					     For the search section, we show the error messages within its frame
					-->
	<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
		<div class="spacerY">&nbsp;
		<div id="searchErrorMessagesArea" class="errorMessages"><html:errors />
		</div>
		</div>
	</c:if>
	<!-- Error Messages Ends -->

	<!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;">
	<input name="uAction" id="uAction" type="hidden" value="search">
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
	<input name="searchHeight" id="searchHeight" type="hidden" value="108"></div>
	<!-- Hidden elements end -->
</tcmis:form></div>
<!-- close of contentArea --></div>
<!-- Search div Ends --> <!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;"><!-- Transit Page Begins -->
<div id="transitPage" style="display: none;"
	class="optionTitleBoldCenter"><br><br><br><fmt:message
	key="label.pleasewait" /> <br><br><br><img
	src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;"><!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td>
		<div class="roundcont contentContainer">
		<div class="roundright">
		<div class="roundtop">
			<div class="roundtopright">
				<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
			</div>
		</div>
		<div class="roundContent">
		<div class="boxhead"><%-- boxhead Begins --%> <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											     Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											     Please keep the <span></span> on the same line this will avoid extra virtical space.
											 --%> <%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
		<div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%>
		<div id="updateResultLink" style="display: none">
			<a href="#" onclick="resultFrame.submitUpdate();"><fmt:message key="label.update" /></a> 
			|&nbsp;
			<a href="#" onclick="resultFrame.addNewDatabaseObjectLine()"><fmt:message key="label.addline" /></a></div>
		</div>
		<%-- mainUpdateLinks Ends --%></div>
		<%-- boxhead Ends --%>

		<div class="dataContent"><iframe scrolling="no" id="resultFrame"
			name="resultFrame" width="100%" height="" frameborder="0"
			marginwidth="0" src="/blank.html"></iframe></div>
		<%-- result count and time --%>
		<div id="footer" class="messageBar">&nbsp;</div>
		</div>

		<div class="roundbottom">
		<div class="roundbottomright">
			<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
		</div>
		</div>
		</div>
		</td>
	</tr>
</table>
<!-- results end --></div>
<!-- Result Frame Ends --></div>

</div>
<!-- close of interface -->

<%-- You can build your error messages below.
	     Similar divs would have to be built to show any other messages in a inline pop-up.
	     Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages"
	style="display: none; overflow: auto;"></div>

</body>
</html:html>