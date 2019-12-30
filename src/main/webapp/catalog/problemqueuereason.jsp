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
<title><fmt:message key="label.reportasproblem"/> <c:out value="${param.qId}"/></title>
<script language="JavaScript" type="text/javascript">
<!-- 
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
type:"<fmt:message key="label.type"/>",
reason:"<fmt:message key="label.reason"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

function submitProblem() {
	$("uAction").value = "submit";
	return validate();
}

function validate() {
	var problemType = $v("problemType");
	var comments = $v("comments");
	if (problemType && problemType.length > 0 &&
			comments && comments.length > 0) {
		$("notifyAssignee").disabled = "";
		$("notifyCatalog").disabled = "";
		return true;
	}
	alert("Please enter a Problem Type and a Reason.");
	return false;
}

function resultOnload(success) {
	if (success == "Y") {
		try {
			parent.opener.problemReported();
		} catch (e) {
			//if called from Catalog Add QC
			parent.opener.parent.autoSubmitSearchForm();
		}
		window.close();
	}
}

function cancelReject() {
	window.close();
}

function returnValueToOpener() {
    if (validate()) {
        parent.opener.callBackValue($v("problemType"),$v("comments"));
	    window.close();
	}else {
	    alert(messagesData.validvalues+" "+messagesData.type+" "+messagesData.and+" "+messagesData.reason);
	}
}

//-->
</script>
</head>
<body onload="return resultOnload('<c:out value="${success?'Y':'N'}"/>');" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="problemqueuereason.do" onsubmit="return submitSearchOnlyOnce();">
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
							<span class="optionTitleBold"><fmt:message key="label.task"/>: </span>
							<span><c:out value="${param.task}"/></span>
						</div>
						<div style="margin:10px 0px 10px 0px">
							<label class="optionTitleBold"><fmt:message key="label.type"/>: </label>
							<select id="problemType" name="problemType" class="selectBox">
								<option value=""><fmt:message key="label.pleaseselect"/></option>
							<c:forEach var="code" items="${problemTypes}" varStatus="status">
								<option value="<c:out value="${code.problemType}"/>">${code.problemType}</option>
							</c:forEach>
							</select>
						</div>
						<div>
							<c:choose>
								<c:when test="${empty param.reAssignTo}">
									<c:set var="assigneeChecked" value="${param.status ne 'Assigned'}"/>
								</c:when>
								<c:otherwise>
									<c:set var="assigneeChecked" value="${true}"/>
								</c:otherwise>
							</c:choose>
							<c:set var="notifyDisabled" value="${param.status ne 'Pending QC' || not empty param.reAssignTo?'disabled':''}"/>
							<fieldset style="border:none"> 
								<legend class="optionTitleBold" style="white-space:nowrap;"><fmt:message key="label.notify"/>:</legend>
							<input id="notifyAssignee" name="notify" type="radio" class="radioBtns" value="assignee" ${notifyDisabled} ${assigneeChecked?'checked':''}/>
								<label class="optionTitleBold" for="notifyAssignee"><fmt:message key="label.assignee"/></label>
							<input id="notifyCatalog" name="notify" type="radio" class="radioBtns" value="catalog" ${notifyDisabled} ${not assigneeChecked?'checked':''}/>
								<label class="optionTitleBold" for="notifyCatalog"><fmt:message key="label.masterdata"/></label>
							</fieldset>
						</div>
						<div style="margin:20px 0">
							<label class="optionTitleBoldLeft"><fmt:message key="label.reason"/>: </label><br/>
							<textarea id="comments" name="comments" class="inputBox" rows="4" cols="50"></textarea>
						</div>
						<div>
						    <c:if test="${param.returnValueToOpener == 'Y'}">
						         <input type="button" id="submit" name="submit" value="<fmt:message key="label.submit"/>" class="inputBtns" onclick="returnValueToOpener()"/>
						    </c:if>
						    <c:if test="${param.returnValueToOpener != 'Y'}">
                               <input type="submit" id="submit" name="submit" value="<fmt:message key="label.submit"/>" class="inputBtns" onclick="return submitProblem()"/>
                               <input type="button" id="cancel" name="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onclick="cancelReject()"/>
                            </c:if>
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
	<input name="qId" id="qId" type="hidden" value="<tcmis:jsReplace value="${param.qId}"/>" />
	<input name="catalogAddRequestId" id="catalogAddRequestId" type="hidden" value="<tcmis:jsReplace value="${param.catalogAddRequestId}"/>" />
	<input name="status" id="status" type="hidden" value="<tcmis:jsReplace value="${param.status}"/>"/>
	<input name="task" id="task" type="hidden" value="<tcmis:jsReplace value="${param.task}"/>" />
	<input name="reAssignTo" id="reAssignTo" type="hidden" value="<tcmis:jsReplace value="${param.reAssignTo}"/>" />
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