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
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<title><fmt:message key="label.overrideapproval"/> - <c:out value="${param.pickingUnitId}"/></title>
<script language="JavaScript" type="text/javascript"/>
<!--
var messagesData = {
			recordFound:"<fmt:message key="label.recordFound"/>",
    		searchDuration:"<fmt:message key="label.searchDuration"/>",
    		minutes:"<fmt:message key="label.minutes"/>",
    		seconds:"<fmt:message key="label.seconds"/>",
            total:"<fmt:message key="label.total"/>",
			pleaseSelect:"<fmt:message key="error.norowselected"/>",
			none:"<fmt:message key="label.none"/>",
			validQty:"<fmt:message key="label.validvalues"/> <fmt:message key="label.quantity"/>",
			noteRequired:"<fmt:message key="label.pleaseenternotes"/>"
};

var qytChgLimit = parseInt('${param.qty}') || 0;

function submitUpdate() {
	try {
		if( $('packQtyOverride').checked ) { 
			if( !isInteger($v("overrideQty"), false ) ) {
				alert( messagesData.validQty );
				return; 
			}
			var inputQty = parseInt( $v("overrideQty") ); 
			if( inputQty < 0 || inputQty > qytChgLimit ) {
				alert( messagesData.validQty );
				return; 
			}
		}
	}
 	catch(ex)
 	{}


	if ($v("overrideNote").length > 0) {
		$("uAction").value="update";
		document.genericForm.submit();
	}
	else {
		alert(messagesData.noteRequired);
	}
}

function onload() {
	var action = "<c:out value="${param.uAction}"/>";
	if (showErrorMessage) {
		opener.document.getElementById("errorMessagesAreaBody").innerHTML = document.getElementById("errorMessagesAreaBody").innerHTML;
		opener.parent.messagesData.errors = messagesData.errors;
		opener.showErrorMessages();
		window.close();
	} else if ("update" === action) {
		opener.reloadResults();
		window.close();
	}
}
// -->
</script>
</head>
<body onload="onload();">
<tcmis:form action="/overrideapproval.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
	${tcmISError}<br/>
	<c:forEach var="tcmisError" items="${tcmISErrors}">
		${tcmisError}<br/>
	</c:forEach>
</div>
<script type="text/javascript">
<!--
	var showErrorMessage = false;
	<c:if test="${not empty tcmISErrors or not empty tcmISError}">
		messagesData.errors = "<fmt:message key="label.errors"/>";
		showErrorMessage = true;
	</c:if>
//-->
</script>
<div class="roundcont filterContainer">
	<div class="roundright">
		<div class="roundtop">
			<div class="roundtopright">
				<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
			</div>
		</div>
		<div class="roundContent">
		<div class="boxhead">
			<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
				Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
			<div id="mainUpdateLinks"> <%-- mainUpdateLinks Begins --%>
				<a href="#" onclick="submitUpdate()"><fmt:message key="label.update"/></a>
			</div>
		</div>
		<table id="packerOverride">
			<tr>
				<th style="padding:5px 10px;border-bottom:1px solid gray"><fmt:message key="label.override"/></th>
				<th style="padding:5px 10px;border-bottom:1px solid gray"><fmt:message key="label.approver"/></th>
				<th style="padding:5px 10px;border-bottom:1px solid gray"><fmt:message key="label.approvaldate"/></th>
			</tr>
			<tr>
				<td style="padding:5px 10px">
					<label for="packerOverride">
						<input type="checkbox" id="packerOverride" name="packerOverride" class="radioBtns" 
							${overrideApprovalBean.packerOverride?'checked disabled':''}/>
						<fmt:message key="label.packeroverride"/>
					</label>
				</td>
				<td style="padding:5px 10px">
					<span class="optionTitleRight">${overrideApprovalBean.packerOverrideApproverName}</span>
				</td>
				<td style="padding:5px 10px">
					<fmt:formatDate var="packerDate" value="${overrideApprovalBean.packerOverrideDate}" pattern="${dateTimeFormatPattern}"/>
					<span class="optionTitleRight">${packerDate}</span>
				</td>
			</tr>
			<tr>
				<td style="padding:5px 10px">
					<label for="labelOverride">
						<input type="checkbox" id="labelOverride" name="labelOverride" class="radioBtns" 
							${overrideApprovalBean.labelOverride?'checked disabled':''}/>
						<fmt:message key="label.labeloverride"/>
					</label>
				</td>
				<td style="padding:5px 10px">
					<span class="optionTitleRight">${overrideApprovalBean.labelOverrideApproverName}</span>
				</td>
				<td style="padding:5px 10px">
					<fmt:formatDate var="labelDate" value="${overrideApprovalBean.labelOverrideDate}" pattern="${dateTimeFormatPattern}"/>
					<span class="optionTitleRight">${labelDate}</span>
				</td>
			</tr>
			<tr>
				<td style="padding:5px 10px">
					<label for="qtyOverride">
						<input type="checkbox" id="qtyOverride" name="qtyOverride" class="radioBtns" 
							${overrideApprovalBean.qtyOverride?'checked disabled':''}/>
						<fmt:message key="label.qtyoverride"/>
					</label>
					<input type="hidden" name="rid" id="rid" value="${param.rid}"/>				
				</td>
				<td style="padding:5px 10px">
					<span class="optionTitleRight">${overrideApprovalBean.qtyOverrideApproverName}</span>
				</td>
				<td style="padding:5px 10px">
					<fmt:formatDate var="qtyDate" value="${overrideApprovalBean.qtyOverrideDate}" pattern="${dateTimeFormatPattern}"/>
					<span class="optionTitleRight">${qtyDate}</span>
				</td>
			</tr>
			<c:if test="${overrideApprovalBean.allowRidOverride eq 'Y'}">
			<tr>
				<td style="padding:5px 10px">
					<c:if test="${fn:length(overrideApprovalBean.validRids[0]) eq 0}">
		   					<fmt:message key="label.changeRidToNoAvailable">
             					<fmt:param value="${param.rid}"/>
          					</fmt:message>
					</c:if>
					<c:if test="${fn:length(overrideApprovalBean.validRids[0]) gt 0}">
					<label for="changeRid">
						<input type="checkbox" id="changeRid" name="changeRid" class="radioBtns" 
							${overrideApprovalBean.changeRid?'checked disabled':''}/>
							<fmt:message key="label.changeRidTo">
             					<fmt:param value="${param.rid}"/>
          					</fmt:message>
					</label>
					<select name="toRid" id="toRid" class="selectBox">
					<c:forEach var="rid" items="${overrideApprovalBean.validRids[0]}">
						<option value="${rid}">${rid}</option>
					</c:forEach>
					</select>
					</c:if>
				</td>
			</tr>
			</c:if>
			<c:if test="${overrideApprovalBean.allowPackerQtyOverride eq 'Y'}">
			<tr>
				<td style="padding:5px 10px">
					<label for="packQtyOverride">
						<input type="checkbox" id="packQtyOverride" name="packQtyOverride" class="radioBtns" 
							${overrideApprovalBean.packQtyOverride?'checked disabled':''}/>
						<fmt:message key="label.packQtyOverrideTo"/> &lt;= ${param.qty}
					</label>
					<input name="overrideQty" id="overrideQty" type="text" class="inputBox" value="" maxlength="7"/>
					<input type="hidden" name="qty" id="qty" value="${param.qty}"/>				
				</td>
			</tr>
			</c:if>
		</table>
		<hr/>
			<div class="optionTitleBoldLeft"><fmt:message key="label.history"/></div>
			<c:choose>
			<c:when test="${empty overrideApprovalBean.overrideNote}">
				<div class="optionTitleLeft"><fmt:message key="label.none"/></div>
			</c:when>
			<c:otherwise>
				<div class="optionTitleLeft"><c:out value="${overrideApprovalBean.overrideNote}"/></div>
			</c:otherwise>
			</c:choose>
		<hr/>
			<div class="optionTitleBoldLeft"><fmt:message key="label.notes"/></div>
			<textarea id="overrideNote" name="overrideNote" rows="4" style="width:80%"></textarea>
			</div>
		<div class="roundbottom">
			<div class="roundbottomright">
				<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
			</div>
		</div>
	</div>
</div>
				
<!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="uAction" id="uAction" value=""/>
	<input type="hidden" name="pickingUnitId" id="pickingUnitId" value="<tcmis:jsReplace value="${param.pickingUnitId}"/>"/>
	<input type="hidden" name="issueId" id="issueId" value="<tcmis:jsReplace value="${param.issueId}"/>"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>
</div>
</tcmis:form>
</body>
</html:html>