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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->

<!-- Popup window support -->
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>

<title>
<fmt:message key="label.catalogaddqcselectitem"/>
</title>

<script language="JavaScript" type="text/javascript">

var windowCloseOnEsc = true;

var messagesData = {
	days: "<fmt:message var="days" key="label.days"/>"
};

var itemMatchConfig = [
		{columnId:"permission"},
		{columnId:"itemId", submit: false},
		{columnId:"itemDisplay",columnName:'<fmt:message key="label.itemidtype"/>',width: 5},
		{columnId:"partId",columnName:'<fmt:message key="label.part"/>',width: 5},
		{columnId:"materialId", columnName:'<fmt:message key="label.materialid"/>'},
		{columnId:"mfgPartNo", columnName:'<fmt:message key="label.mpn"/>'},
		{columnId:"packaging", columnName:'<fmt:message key="label.packaging"/>'},
		{columnId:"dimension", columnName:'<fmt:message key="label.dimension"/>'},
		{columnId:"grade", columnName:'<fmt:message key="label.grade"/>'},
		{columnId:"netwt", columnName:'<fmt:message key="label.weight"/>'},
		{columnId:"shelfLifeBasisDisp"},
		{columnId:"shelfLife", columnName:'<fmt:message key="label.shelflife"/>'},
		{columnId:"storageTemp", columnName:'<fmt:message key="label.storagetemp"/>'},
		{columnId:"itemVerified", columnName:'<fmt:message key="label.itemverified"/>'},
		{columnId:"comments", columnName:'<fmt:message key="label.revisioncomments"/>'},
		{columnId:"stockingType", columnName:'<fmt:message key="label.stockingtype"/>'},
		{columnId:"materialDesc", columnName:'<fmt:message key="label.materialdesc"/>'}
];

var itemGridConfig = {
		divName:'itemDiv',
		beanData:'itemJsonData',
		beanGrid:'itemGrid',
		config:'itemMatchConfig',
		rowspan:true,
		noSmartRender: false,
		submitDefault:false,
		selectChild: 1
};

var itemMatchGridConfig = {
		divName:'itemMatchDiv',
		beanData:'itemMatchJsonData',
		beanGrid:'itemMatchGrid',
		config:'itemMatchConfig',
		rowSpan:false,
		noSmartRender: false,
		submitDefault:false,
		onRowSelect:doOnRowSelected
		//selectChild: 1
};

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [1,2,10,11,13];

function doOnRowSelected(rowid) {
	$("itemId").value = cellValue(rowid, "itemId");
	$("itemVerified").value = cellValue(rowid, "itemVerified");
}

function selectItem(itemId) {
	loc = "/tcmIS/purchase/showtcmbuys?Action=viewforpage&itemID=";
    loc = loc + itemId;
    loc = loc + "&shiptoLoc=";
    openWinGeneric(loc,"tcmbuyhistory","800","450","yes","50","50")
}

function myOnload(unload) {
	if (unload) {
		parent.opener.setItemId("${param.itemId}", "${param.itemVerified}");
		window.close();
	}
	else {
		fillInGrids();
	}
}

function myUnload() {
	parent.opener.hideTransitDialog();
}

function fillInGrids() {
	resultOnLoadWithGrid(itemGridConfig);
	resultOnLoadWithGrid(itemMatchGridConfig);
}

function createItem() {
	j$.ajax({
		url:"itemmatcher.do",
		cache:false,
		data:{uAction: "newitem"},
		success: function(data) {
			if (data != null && data != "") {
				$("itemId").value = data;
				returnItem();
			}
		}
	});
}

function returnItem() {
	/* j$.ajax({
		url:"itemmatcher.do",
		cache:false,
		data:{uAction: "select", requestId:$v("requestId"), itemId:$v("itemId")},
		success: function(data) {
			
		}
	}); */
	
	if ($v("itemId").length > 0) {
		parent.opener.setItemId($v("itemId"), $v("itemVerified"));
		window.close();
	}
}
</script>

</head>
<body onload="myOnload(${unload});" onunload="myUnload();">
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
			<tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td align="center" id="transitLabel"></td></tr>
			<tr><td align="center"><img src="/images/rel_interstitial_loading.gif" align="middle"></td></tr>
		</table>
	</div>
	<!-- open contentArea -->
	<div class="contentArea">
		<tcmis:form action="/catalogitemqcdetails.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
			<div class="roundcont contentContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="roundtopright">
						<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
					</div>
				</div>
				<div class="roundContent">
					<div class="boxhead">
						<span id="createItem">
							<a href="#" onclick="createItem();"><fmt:message key="label.new"/></a> |
							<a href="#" onclick="returnItem();"><fmt:message key="label.return"/></a>
						</span><br/>
						<fmt:message key="label.catalogaddqcselectitem"/>
					</div>
					<div>
						<label for="itemId"><fmt:message key="label.itemid"/></label>
						<input type="text" id="itemId" name="itemId" class="inputBox" readonly/>
						<c:choose>
						<c:when test="${empty param.qId}">
						<label for="requestId"><fmt:message key="label.forrequestid"/>: <c:out value="${param.requestId}"/></label>
						</c:when>
						<c:otherwise>
						<label for="qId"><fmt:message key="label.forqid"/>: <c:out value="${param.qId}"/></label>
						</c:otherwise>
						</c:choose>
					</div>
					<div id="itemDiv" style="height:150px;"></div>
						<script type="text/javascript">
						<!--
						<c:set var="dataCount" value='0'/>
						var itemJsonData = {
							rows:[
								<c:forEach var="comp" items="${componentCollection}" varStatus="status">
									<c:choose>
									<c:when test="${empty status.current.minTemp and empty status.current.maxTemp}">
										<c:set var="mfgStorageTemp" value=""/>
									</c:when>
									<c:when test="${empty status.current.minTemp}">
										<c:set var="mfgStorageTemp" value="< ${status.current.maxTemp} &deg;${status.current.tempUnits}"/>
									</c:when>
									<c:when test="${empty status.current.maxTemp}">
										<c:set var="mfgStorageTemp" value="> ${status.current.minTemp} &deg;${status.current.tempUnits}"/>
									</c:when>
									<c:otherwise>
										<c:set var="mfgStorageTemp" value="${status.current.minTemp} - ${status.current.maxTemp} &deg;${status.current.tempUnits}"/>
									</c:otherwise>
									</c:choose>
									<c:if test="${status.index > 0}">,</c:if>
									<c:set var="currentPermission" value='N'/>
									<c:set var="componentPackaging" value="${status.current.componentsPerItem} x ${status.current.partSize} ${status.current.sizeUnit} ${status.current.pkgStyle} ${status.current.dimension}"/>
									{ id:${status.index + 1},
									 data:['${currentPermission}',
										'${param.itemId}',
										'${param.itemId} <c:if test="${not empty param.itemId}">(${param.itemType})</c:if>',
										'${status.current.partId}',
										'${status.current.materialId}',
										'<tcmis:jsReplace value="${status.current.mfgPartNo}" processMultiLines="true"/>',
										'<tcmis:jsReplace value="${componentPackaging}" processMultiLines="true"/>',
										'<tcmis:jsReplace value="${status.current.dimension}" processMultiLines="true"/>',
										'<tcmis:jsReplace value="${status.current.grade}" processMultiLines="true"/>',
										'${status.current.netwt} ${status.current.netwtUnit}',
										'<fmt:message var="shelfLifeBasisDisp" key="${status.current.shelfLifeBasisDisp}"/>',
										<c:if test="${empty status.current.shelfLifeDays}">
										'',
										</c:if>
										<c:if test="${not empty status.current.shelfLifeDays}">
										'${status.current.shelfLifeDays} ${days} : ${shelfLifeBasisDisp}',
										</c:if>
										'${mfgStorageTemp}',
										'${status.current.itemVerified}',
										'<tcmis:jsReplace value="${status.current.comments}" processMultiLines="true"/>',
										'${status.current.stockingType}',
										'<tcmis:jsReplace value="${status.current.materialDesc}" processMultiLines="true"/>'
									 ]}
									 <c:set var="dataCount" value='${dataCount+1}'/>
								 </c:forEach>
								]};
						
						<c:forEach var="comp" items="${componentCollection}" varStatus="status">
						<c:set var="currentKey" value='${param.itemId}' />
						<c:choose>
							<c:when test="${status.first}">
								<c:set var="rowSpanStart" value='0' />
								<c:set var="rowSpanCount" value='1' />
								rowSpanMap[0] = 1;
								rowSpanClassMap[0] = 1;
							</c:when>
							<c:when test="${currentKey == previousKey}">
								rowSpanMap[${rowSpanStart}]++;
								rowSpanMap[${status.index}] = 0;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
							</c:when>
							<c:otherwise>
								<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
								<c:set var="rowSpanStart" value='${status.index}' />
								rowSpanMap[${status.index}] = 1;
								rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
							</c:otherwise>
						</c:choose>
						<c:set var="previousKey" value='${currentKey}' />
						</c:forEach>

					//-->
					</script>
				</div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
			</div>
			
			<div class="roundcont contentContainer">
			<div class="roundright">
				<div class="roundtop">
					<div class="roundtopright">
						<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
					</div>
				</div>
				<div class="roundContent">
					<div class="boxhead"></div>
					<div id="itemMatchDiv" style="height:450px;"></div>
					<script type="text/javascript">
					<!--
					
					var itemMatchJsonData = {
						rows:[
							<c:forEach var="match" items="${itemMatchCollection}" varStatus="status">
								<c:choose>
								<c:when test="${empty status.current.minTemp and empty status.current.maxTemp}">
									<c:set var="mfgStorageTemp" value=""/>
								</c:when>
								<c:when test="${empty status.current.minTemp}">
									<c:set var="mfgStorageTemp" value="< ${status.current.maxTemp} &deg;${status.current.tempUnits}"/>
								</c:when>
								<c:when test="${empty status.current.maxTemp}">
									<c:set var="mfgStorageTemp" value="> ${status.current.minTemp} &deg;${status.current.tempUnits}"/>
								</c:when>
								<c:otherwise>
									<c:set var="mfgStorageTemp" value="${status.current.minTemp} - ${status.current.maxTemp} &deg;${status.current.tempUnits}"/>
								</c:otherwise>
								</c:choose>
								<c:if test="${status.index > 0}">,</c:if>
								<c:set var="currentPermission" value='N'/>
								<c:set var="dataCount" value='${dataCount+1}'/>
								{ id:${status.index + dataCount},
								 data:['${currentPermission}',
									'${status.current.itemId}',
									<%--'<a href="#" onclick="selectItem(${status.current.itemId})">${status.current.itemId} (${status.current.itemType})</a>',--%>
									'${status.current.itemId} (${status.current.itemType})',
									'${status.current.partId}',
									'${status.current.materialId}',
									'<tcmis:jsReplace value="${status.current.mfgPartNo} ${status.current.mfgPartNoExtension}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${status.current.packaging}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${status.current.dimension}" processMultiLines="true"/>',
									'<tcmis:jsReplace value="${status.current.grade}" processMultiLines="true"/>',
									'${status.current.netWt} ${status.current.netWtUnit}',
									'<fmt:message var="shelfLifeBasisDisp" key="${status.current.shelfLifeBasisDisp}"/>',
									<c:if test="${empty status.current.shelfLifeDays}">
									'',
									</c:if>
									<c:if test="${not empty status.current.shelfLifeDays}">
									'${status.current.shelfLifeDays} ${days} : ${shelfLifeBasisDisp}',
									</c:if>
									'${mfgStorageTemp}',
									'${status.current.itemVerified}',
									'<tcmis:jsReplace value="${status.current.revisionComments}" processMultiLines="true"/>',
									'${status.current.stockingType}',
									'<tcmis:jsReplace value="${status.current.materialDesc}" processMultiLines="true"/>'
								 ]}
							 </c:forEach>
							]};

					<c:set var="rowSpanCount" value='0' />
						<%-- determining rowspan --%>
						<c:forEach var="row" items="${itemMatchCollection}" varStatus="status">
						<c:set var="currentKey" value='${row.itemId}' />
							<c:choose>
								<c:when test="${status.first}">
									<c:set var="rowSpanStart" value='0' />
									<c:set var="rowSpanCount" value='1' />
									<c:set var="prevSpanCount" value="${rowSpanCount}"/>
									rowSpanMap[0] = 1;
									rowSpanClassMap[0] = 1;
								</c:when>
								<c:when test="${currentKey == previousKey}">
									rowSpanMap[${rowSpanStart}]++;
									rowSpanMap[${status.index}] = 0;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:when>
								<c:otherwise>
									<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
									<c:set var="rowSpanStart" value='${status.index}' />
									rowSpanMap[${status.index}] = 1;
									rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
								</c:otherwise>
							</c:choose>
							<c:set var="previousKey" value='${currentKey}' />

						</c:forEach> 
					//-->
					</script>
				</div>
				<div id="footer"></div>
				<div class="roundbottom">
					<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
			</div>
			</div>
			<!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
				<input name="uAction" id="uAction" type="hidden"/>
				<input type="hidden" id="totalLines" name="totalLines" value="0"/>
				<input type="hidden" id="itemVerified" name="itemVerified"/>
				<input name="requestId" id="requestId" type="hidden" value='<tcmis:jsReplace value="${param.requestId}"/>'/>
				<input name="qId" id="qId" type="hidden" value='<tcmis:jsReplace value="${param.qId}"/>'/>
				<input name="lineItem" id="lineItem" type="hidden" value='<tcmis:jsReplace value="${param.lineItem}"/>'/>
			</div>
			<!-- Hidden elements end -->
			
		</tcmis:form>
	</div>
	<!-- close of contentArea -->
		

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