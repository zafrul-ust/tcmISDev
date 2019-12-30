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
<script type="text/javascript" src="/js/catalog/vendorcontactlogmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title><fmt:message key="label.contactlog"/></title>
<script language="JavaScript" type="text/javascript"/>
<!-- 
windowCloseOnEsc = true;

var messagesData = new Array();
messagesData={
	waitingFor:"<fmt:message key="label.waitingforinputfrom"/>",
	document:"<fmt:message key="label.document"/>",
	revDateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.revisiondate"/></fmt:param></fmt:message>",
	material:"<fmt:message key="label.materialsearch"/>",
	msdsMaintenance:"<fmt:message key="msdsMaintenance"/>",
	editmsds:"<fmt:message key="label.editmdsdmaintenance"/>",
	deletemsds:"<fmt:message key="label.deletemsds"/>",
	contactPurpose:"<fmt:message key="label.purpose"/>",
	contactStatus:"<fmt:message key="label.status"/>",
	contactLogType:"<fmt:message key="label.method"/>",
	contactName:"<fmt:message key="label.contactname"/>",
	fieldsRequired:"<fmt:message key="label.atleastonerequired"/>",
	newcontactlog:"New contact log"
};
// -->
</script>
</head>

<body onload="loadLayoutWin('','vendorContactLog');myOnload('${loadAction}');" onunload="unloadWindow();" onresize="resizeFrames()">

<div id="transitDailogWin" class="optionTitleBoldCenter" style="display: none;overflow: auto;position: absolute;">
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

	<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="vendorcontactlogresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<c:if test="${param.qItemStatus eq 'Assigned' || param.qItemStatus eq 'Pending QC'}">
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
						<div class="boxhead"> <%-- boxhead Begins --%>
							<a href="javascript:submit('save')"><fmt:message key="button.submit"/></a>&nbsp;|
							<a href="javascript:uploadDocuments()"><fmt:message key="label.viewuploaddocuments"/></a>&nbsp;|
							<a href="javascript:cancel()"><fmt:message key="label.cancel"/></a>&nbsp;
							<br/>
							<c:choose>
							<c:when test="${contactLogBean.contactLogId == null}">
								<span>New contact log</span>
							</c:when>
							<c:otherwise>
								<span id="contactLogIdDisplay">Editing log # <c:out value="${contactLogBean.contactLogId}"/></span>
							</c:otherwise>
							</c:choose>
						</div> <%-- boxhead Ends --%>
						<table width="75%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
					      	<tr>
					        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.purpose"/>:</td>
					        	<td width="10%" >
					        	<select name="contactPurpose" id="contactPurpose"  class="selectBox" >
					        		<c:forEach var="purpose" items="${contactPurposeColl}" varStatus="pStatus">
					        			<option <c:if test="${pStatus.current == contactLogBean.contactPurpose}">selected</c:if> value="${pStatus.current}">${pStatus.current}</option>
					        		</c:forEach>
					        	</select></td>
					      	
					        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.method"/>:</td>
					        	<td width="10%" >
					        	<select name="contactLogType" id="contactLogType"  class="selectBox" >
					        		<c:forEach var="logType" items="${contactTypeColl}" varStatus="tStatus">
					        			<option <c:if test="${tStatus.current == contactLogBean.contactLogType}">selected</c:if> value="${tStatus.current}">${tStatus.current}</option>
					        		</c:forEach>
					        	</select></td>
					        </tr>
					        <tr>
					        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.status"/>:</td>
					        	<td width="10%" >
					        	<select name="contactStatus" id="contactStatus"  class="selectBox" >
					                <c:set var="selectedStatus" value="${contactLogBean.contactStatus}"/>
					                <c:forEach var="logStatus" items="${contactStatusColl}" varStatus="sStatus">
					        			<option <c:if test="${sStatus.current == selectedStatus}">selected</c:if> value="${sStatus.current}">${sStatus.current}</option>
					        		</c:forEach>
					        	</select></td>
					        	
					        	<td class="optionTitleBoldRight" width="10%"><fmt:message key="label.contactname"/>:</td>
					        	<td width="10%" ><input name="contactName" id="contactName" type="text" class="inputBox" value="${contactLogBean.contactName}"/></td>
					      	</tr>
					      	<tr>
					        	<td class="optionTitleBoldRight" width="10%" ><fmt:message key="label.notes"/>:</td>
					        	<td colspan="3" ><input name="contactReference" id="contactReference" type="text" class="inputBox" size="75" value="${contactLogBean.contactReference}"/></td>
					      	</tr>
					    </table>
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
</c:if>
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
	<input name="qId" id="qId" type="hidden" value="<tcmis:jsReplace value="${param.qId}"/>" />
	<input name="contactLogId" id="contactLogId" type="hidden" value="${contactLogBean.contactLogId}"/>
	<input name="qItemStatus" id="qItemStatus" type="hidden" value="<tcmis:jsReplace value="${param.qItemStatus}"/>"/>
</div>
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	<br><br><br><fmt:message key="label.pleasewait"/>
	<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
						<%-- boxhead Begins --%>
						<div class="boxhead">
							<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
								Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
							<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
								<span id="updateResultLink" style="display: none"></span>
							</div>
						</div>
						<div class="dataContent"> 
							<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
						</div>
						<%-- result count and time --%>
						<div id="footer" class="messageBar"></div>
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
<!-- Search results end -->
</div>
</div>
<!-- Result Frame Ends -->
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
</body>
</html:html>