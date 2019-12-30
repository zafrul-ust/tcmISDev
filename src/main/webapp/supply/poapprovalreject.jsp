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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<fmt:message var="rejectpo" key="label.rejectpo"/>
<title><c:out value="${rejectpo}"/> <c:out value="${param.poNumber}"/></title>
<script language="JavaScript" type="text/javascript">
<!-- 
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

function submitRejection() {
	$("uAction").value = "rejectPo";
	document.genericForm.submit();
}

function resultOnload(rejectionSuccess) {
	if (rejectionSuccess == "Y") {
		parent.opener.doRefresh("<fmt:message key="label.poapproval"/>");
		window.close();
	}
}

function cancelReject() {
	window.close();
}


//-->
</script>
</head>
<body onload="return resultOnload('<c:out value="${rejectionSuccess}"/>');" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="poapprovalreject.do" onsubmit="return submitSearchOnlyOnce();">
<!-- Search Option Begins -->
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
					<div class="roundContent">
						<div>
							<label class="optionTitleBold"><fmt:message key="label.statuscode"/>: </label><br/>
							<select id="poApprovalCode" name="poApprovalCode" class="selectBox">
							<c:forEach var="code" items="${poApprovalCodes}" varStatus="status">
								<c:if test="${'Rejected' eq code.poApprovalStatus}">
								<option value="<c:out value="${code.poApprovalCode}"/>"><fmt:message key="${code.poApprovalDescription}"/></option>
								</c:if>
							</c:forEach>
							</select>
						</div>
						<div style="margin:20px 0">
							<label class="optionTitleBoldLeft"><fmt:message key="label.comments"/>: </label><br/>
							<textarea id="actionComment" name="actionComment" class="inputBox" rows="4" cols="50"></textarea>
						</div>
						<div>
							<input type="button" id="reject" name="reject" value="${rejectpo}" class="inputBtns" onclick="submitRejection()"/>
							<input type="button" id="cancel" name="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onclick="cancelReject()"/>
						</div>
					</div>
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
<!-- Build this section only if there is an error message to display. For the search section, we show the error messages within its frame -->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
	<div class="spacerY">&nbsp;
		<div id="searchErrorMessagesArea" class="errorMessages">
			<html:errors />
		</div>
	</div>
</c:if>
<!-- Error Messages Ends -->
<!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="uAction" id="uAction" value=""/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<input name="radianPo" id="radianPo" type="hidden" value="<c:out value="${param.poNumber}"/>" />
</div>
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

</div>
<!-- close of interface -->
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
	<div id="errorMessagesAreaBody" class="bd">
		<html:errors/>
	</div>
</div>
<%-- show legend Ends --%>
</body>
</html:html>