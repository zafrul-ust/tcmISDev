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
<link rel="stylesheet" type="text/css" href="/css/tabs.css" />
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<script type="text/javascript" src="/js/catalog/mfrnotification.js"></script>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid-->
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title><fmt:message key="label.mfrnotification"/></title>
<script language="JavaScript" type="text/javascript">
<!-- 
var dhxWins = null;
var transitWin = null;

var messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
or:"<fmt:message key="label.or"/>",
errors:"<fmt:message key="label.errors"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
searchError:"<fmt:message key="label.hmirssearchreq"/>",
waitingFor:"<fmt:message key="label.waitingforinputfrom"/>",
loading:"<fmt:message key="label.loadingpleasewait"/>",
saving:"<fmt:message key="label.saving"/>",
approving:"<fmt:message key="label.approving"/>",
rejecting:"<fmt:message key="label.rejecting"/>",
item:"<fmt:message key="label.itemsearch"/>",
material:"<fmt:message key="label.materialsearch"/>",
mfr:"<fmt:message key="label.manufacturersearch"/>",
uploaddocument:"<fmt:message key="label.viewuploaddocuments"/>",
deleting:"<fmt:message key="label.removing"/>",
mfrrequired:"<fmt:message key="label.mfrrequired"/>",
acquiredmfrrequired:"<fmt:message key="label.acquiredmfrrequired"/>",
materialrequired:"<fmt:message key="label.materialrequired"/>",
itemrequired:"<fmt:message key="label.itemrequired"/>",
categoryrequired:"<fmt:message key="label.categoryrequired"/>",
requestsaved:"<fmt:message key="label.requestsaved"/>",
rebrandeddescrequired:"<fmt:message key="label.rebrandeddescrequired"/>",
requestSavedOn:"<fmt:message key="label.requestsavedon"/>",
recordsFound:"<fmt:message key="label.recordFound"/>",
mfgUrl:"<fmt:message key="label.mfgurl"/>",
phone:"<fmt:message key="label.phone"/>",
contact:"<fmt:message key="label.contact"/>",
email:"<fmt:message key="label.email"/>",
productCode:"<fmt:message key="label.productcodes"/>",
materialDesc:"<fmt:message key="label.materialdesc"/>",
tradeName:"<fmt:message key="label.tradename"/>",
mfgPartNo:"<fmt:message key="label.mfgpartno"/>",
grade:"<fmt:message key="label.grade"/>",
tooLong:"<fmt:message key="label.toolong"/>",
limit:"<fmt:message key="label.limit"/>",
locale:"<fmt:message key="label.locale"/>",
fieldsRequired:"<fmt:message key="label.fieldrequired"/>",
tempUnits:"<fmt:message key="label.units"/>",
minTemp:"<fmt:message key="label.minstoragetemp"/>",
maxTemp:"<fmt:message key="label.maxstoragetemp"/>",
maxLessThanMin:"<fmt:message key="label.maxlessthanmintemp"/>",
invalidValues:"<fmt:message key="label.validvalues"/>",
shelfLife:"<fmt:message key="label.shelflife(days)"/>",
multipleMfrError:"<fmt:message key="label.disallowmultiplemfr"/>"};

var categorySelections = [];
var children = [];

var rowSpanMap = [];
var rowSpanClassMap = [];
var rowSpanCols = [];
var rowSpanLvl2Map = null;
var rowSpanLvl2Cols = null;	

var materialRowSpanCols = [0,1,2,3,4,5,6,7,8,9,18];

var itemRowSpanCols = [0,1,2,3,4,5,6,7,8,9,10];
var itemRowSpanLvl2Cols = [11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28];	

//-->
</script>
</head>
<body onunload="closeAllChildren();" onresize="resizeFrames(0);">
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td align="center" id="transitLabel"></td></tr>
		<tr><td align="center"><img src="/images/rel_interstitial_loading.gif" align="middle"></td></tr>
	</table>
</div>

<div class="interface" id="mainPage" style="position:relative;overflow:auto;">
<tcmis:form action="mfrnotificationmain.do" onsubmit="return submitSearchOnlyOnce();">
<div class="boxhead" style="z-index:1; padding-left:5px">
	<div id="saveMessage" style="width:100%;text-align:center;color:red;font-weight:bold;display:none"></div>
	<c:set var="submitted" value="${(empty notification || empty notification.dateSubmitted)?'N':'Y'}"/>
	<c:set var="qcComplete" value="${(empty notification || empty notification.qcDate)?'N':'Y'}"/>
	<c:set var="userIsSubmitter" value="${(not empty notification && personnelBean.personnelId eq notification.insertedBy)?'Y':'N'}"/>
	<c:if test="${qcComplete eq 'N'}">
	<span id="saveButton"><a href="#" onclick="mfrNotification.saveRequest();"><fmt:message key="label.save"/></a> |</span>
	<c:if test="${submitted eq 'N'}">
	<span id="submitButton"><a href="#" onclick="mfrNotification.submitRequest();"><fmt:message key="label.submit"/></a> |</span>
	</c:if><c:if test="${submitted eq 'Y' && userIsSubmitter eq 'N'}">
	<span id="approveButton"><a href="#" onclick="mfrNotification.approveRequest();"><fmt:message key="label.approve"/></a> |</span>
	<span id="rejectButton"><a href="#" onclick="mfrNotification.rejectNotification();"><fmt:message key="label.reject"/></a> |</span>
	</c:if>
	<span id="deleteButton"><a href="#" onclick="mfrNotification.deleteNotification();"><fmt:message key="label.deleterequest"/></a> |</span>
	</c:if>
	<div style="position:relative;display:inline;z-index:1">
		<a id="internalCommentBtn" href="#"><fmt:message key="label.internalcomments"/></a>
		<div id="internalCommentPopup" style="position:absolute;width:400px;z-index:1;display:none;background-color:#e5e5e5;padding:5px;border:1px solid gray;">
			<span style="float:right;cursor:pointer">x</span>
			<textarea id="internalComments" name="internalComments" rows="10" class="inputBox" style="width:95%;position:relative" ${qcComplete eq 'Y'?'readonly':''}><c:out value="${notification.internalComments}"/></textarea>
		</div>
	</div>
</div>
	<div class="haasTabs">
		<ul id="tabList">
			<li>
				<span id="selectorLink" class="selectedTab" style="padding-top: 0; padding-bottom: 0;">
				<span id="selectorLinkSpan" class="selectedSpanTab" style="padding-top: 0; padding-bottom: 1px;">
					<img class="iconImage" src="/images/spacer14.gif"/> +
					<select id="tabSelect" style="font-size:8pt;" ${qcComplete eq 'Y'?'disabled="disabled"':''}>
						<option><fmt:message key="label.category"/></option>
						<c:forEach var="category" items="${categories}" varStatus="status">
							<option value="category${category.mfrReqCategoryId}" ${category.selected?'disabled':''}><c:out value="${category.mfrReqCategoryDesc}"/></option>
						</c:forEach>
					</select>
				</span>
				</span>
			</li>
		</ul>
	</div>
<c:forEach var="category" items="${categories}" varStatus="status">
<c:set var="inJsp" value="${true}" />
<c:set var="categoryId" value="category${category.mfrReqCategoryId}" />
<c:set var="request" value="${notificationRequest[status.index]}" />
<%@ include file="/catalog/mfrnotificationsearch.jsp" %>
<c:choose>
<c:when test="${category.manufacturerData}">
<%@ include file="/catalog/mfraffectednotification.jsp" %>
<c:if test="${category.materialData}">
<%@ include file="/catalog/materialaffectednotification.jsp" %>
</c:if>
</c:when>
<c:when test="${category.materialData}">
<%@ include file="/catalog/materialaffectednotification.jsp" %>
</c:when>
<c:when test="${category.itemData}">
<%@ include file="/catalog/itemaffectednotification.jsp" %>
</c:when>
</c:choose>
<script type="text/javascript">
<!--
categorySelections[categorySelections.length] = {"id":"${categoryId}", "desc":"${category.mfrReqCategoryDesc}", "selected": <c:out value="${category.selected}"/>};
//-->
</script>
</c:forEach>

<c:set var="notificnId" value="${empty notificationId?param.notificationId:notificationId}"/>
<input type="hidden" id="notificationId" name="notificationId" value="<tcmis:jsReplace value="${notificnId}" processMultiLines="false"/>"/>
<input type="hidden" id="mfrReqCategoryId" name="mfrReqCategoryId" value=""/>
<input type="hidden" id="selectedCategories" name="selectedCategories" value=""/>
<input type="hidden" id="uAction" name="uAction" value=""/>
<input type="hidden" id="requestApproved" name="requestApproved" value="${notification.status eq 'Approved'?'Y':'N'}"/>
<input type="hidden" id="requestRejected" name="requestRejected" value="${notification.status eq 'Rejected'?'Y':'N'}"/>
<c:if test="${not empty notification}">
<fmt:formatDate var="fmtSubmitDate" value="${notification.dateSubmitted}" pattern="${dateTimeFormatPattern}"/>
<fmt:formatDate var="fmtApproveDate" value="${notification.qcDate}" pattern="${dateTimeFormatPattern}"/>
</c:if>
<fmt:formatDate var="fmtCurrentDateTime" value="${now}" pattern="${dateTimeFormatPattern}"/>
<input type="hidden" id="dateSubmitted" name="dateSubmitted" value="${not empty notification?fmtSubmitDate:''}"/>
<input type="hidden" id="insertedBy" name="insertedBy" value="${not empty notification?notification.insertedBy:''}"/>
<input type="hidden" id="status" name="status" value="${not empty notification?notification.status:''}"/>
<input type="hidden" id="requestApproveDate" name="requestApproveDate" value="${not empty notification?fmtApproveDate:''}"/>
<input type="hidden" id="requestApproveUser" name="requestApproveUser" value="${not empty notification?notification.qcUser:''}"/>
<input type="hidden" id="currentDateTime" name="currentDateTime" value="${fmtCurrentDateTime}"/>
<input type="hidden" id="pageUploadCode" name="pageUploadCode" value=""/>
</tcmis:form>
</div>
<!-- close of interface -->
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
	<div id="errorMessagesAreaBody" class="bd">
		<html:errors/>${tcmisError}
	</div>
</div>
<script type="text/javascript">
	<!--
		mfrNotification.showErrorMessage = <c:out value="${empty tcmisError?'false':'true'}"/>;
	//-->
</script>
</body>
</html:html>