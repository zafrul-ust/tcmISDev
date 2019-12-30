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
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support 
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/chargenumbersetup.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="label.chargenumbersetup"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2,3,4,5,6];

var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.entryvalues"/>",
chargenumberinuse1:"<fmt:message key="label.chargenumberinuse1"/>",
chargenumberinuse2:"<fmt:message key="label.chargenumberinuse2"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
showUpdateLinks = false;
<c:set var="dCharge" value=""/>
<c:set var="iCharge" value=""/>
<c:set var="poReq" value=""/>
<c:set var="prAcctReq" value=""/>
<c:forEach var="chargeColLabelsBean" items="${chargeColLabels}" varStatus="status">
	<c:choose>
		<c:when test="${chargeColLabelsBean.chargeType == 'd'}">
			<c:set var="dCharge" value="${chargeColLabelsBean}"/>
		</c:when>
		<c:otherwise>
			<c:set var="iCharge" value="${chargeColLabelsBean}"/>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${poReq == '' && (chargeColLabelsBean.poRequired == 'p' || chargeColLabelsBean.poRequired == 'P')}">
		<c:set var="poReq" value="p"/>
	</c:if>
	
	<c:if test="${prAcctReq == '' && (chargeColLabelsBean.prAccountRequired == 'y' || chargeColLabelsBean.prAccountRequired == 'Y')}">
		<c:set var="prAcctReq" value="y"/>
	</c:if>
</c:forEach>

var config = [
{ 
  	columnId:"permission"
},
{
  	columnId:"reportingEntityId",
  	columnName:'<fmt:message key="label.workarea"/>',
    width:14
},
{
  	columnId:"department",
  	columnName:'<fmt:message key="label.department"/>',
    width:7
},
{
  	columnId:"chargeTypeDefault",
  	columnName:'<fmt:message key="label.defaultchargetype"/>',
    width:5
},
{
	columnId:"dPo"
	<c:if test="${poReq == 'p' || poReq == 'P'}">
	,
	columnName:'<fmt:message key="label.direct"/>',
	attachHeader:'<fmt:message key="label.po"/>',
  	width:7  
	</c:if>
},
{
	columnId:"dLine"
	<c:if test="${poReq == 'p' || poReq == 'P'}">
	,
	columnName:'#cspan',
	attachHeader:'<fmt:message key="label.line"/>',
  	width:2
	</c:if>
},
{
	columnId:"iPo"
	<c:if test="${poReq == 'p' || poReq == 'P'}">
	,
	columnName:'<fmt:message key="label.indirect"/>',
	attachHeader:'<fmt:message key="label.po"/>',
  	width:7 
	</c:if> 
},
{
	columnId:"iLine"
	<c:if test="${poReq == 'p' || poReq == 'P'}">
	,
	columnName:'#cspan',
	attachHeader:'<fmt:message key="label.line"/>',
  	width:2 
	</c:if> 
},
<c:if test="${prAcctReq == 'Y' || prAcctReq == 'y'}">
	<c:if test="${dCharge != null && dCharge != ''}">
		<c:if test="${!empty dCharge.chargeLabel1}">
		{ 	columnId:"dChargeNumber1",
			columnName:'<fmt:message key="label.direct"/>',
			attachHeader:'${dCharge.chargeLabel1}',
		  	width:10  
		},
		</c:if>
		<c:if test="${!empty dCharge.chargeLabel2}">
		<c:choose>
			<c:when test = "${empty dCharge.chargeLabel1}">
			{ 	columnId:"dChargeNumber2",
				columnName:'<fmt:message key="label.direct"/>',
				attachHeader:'${dCharge.chargeLabel2}',
			  	width:10
			},
			</c:when>
			<c:otherwise>
			{ 	columnId:"dChargeNumber2",
				columnName:'#cspan',
				attachHeader:'${dCharge.chargeLabel2}',
				width:10
			},
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${!empty dCharge.chargeLabel3}">
		<c:choose>
			<c:when test = "${empty dCharge.chargeLabel1 && empty dCharge.chargeLabel2}">
			{ 	columnId:"dChargeNumber3",
				columnName:'<fmt:message key="label.direct"/>',
				attachHeader:'${dCharge.chargeLabel3}',
				width:10
			},
			</c:when>
			<c:otherwise>
			{ 	columnId:"dChargeNumber3",
				columnName:'#cspan',
				attachHeader:'${dCharge.chargeLabel3}',
				width:10
			},
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${!empty dCharge.chargeLabel4}">
		<c:choose>
			<c:when test = "${empty dCharge.chargeLabel1 && empty dCharge.chargeLabel2 && empty dCharge.chargeLabel3}">
			{ 	columnId:"dChargeNumber4",
				columnName:'<fmt:message key="label.direct"/>',
				attachHeader:'${dCharge.chargeLabel4}',
				width:10
			},
			</c:when>
			<c:otherwise>
			{ 	columnId:"dChargeNumber4",
				columnName:'#cspan',
				attachHeader:'${dCharge.chargeLabel4}',
				width:10
			},
			</c:otherwise>
		</c:choose>
		</c:if>
	</c:if>
	<c:if test="${iCharge != null && iCharge != ''}">
		<c:if test="${!empty iCharge.chargeLabel1}">
		{ 	columnId:"iChargeNumber1",
			columnName:'<fmt:message key="label.indirect"/>',
			attachHeader:'${iCharge.chargeLabel1}',
		  	width:10  
		},
		</c:if>
		<c:if test="${!empty iCharge.chargeLabel2}">
		<c:choose>
			<c:when test = "${empty iCharge.chargeLabel1}">
			{ 	columnId:"iChargeNumber2",
				columnName:'<fmt:message key="label.indirect"/>',
				attachHeader:'${iCharge.chargeLabel2}',
			  	width:10
			},
			</c:when>
			<c:otherwise>
			{ 	columnId:"iChargeNumber2",
				columnName:'#cspan',
				attachHeader:'${iCharge.chargeLabel2}',
				width:10
			},
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${!empty iCharge.chargeLabel3}">
		<c:choose>
			<c:when test = "${empty iCharge.chargeLabel1 && empty iCharge.chargeLabel2}">
			{ 	columnId:"iChargeNumber3",
				columnName:'<fmt:message key="label.indirect"/>',
				attachHeader:'${iCharge.chargeLabel3}',
				width:10
			},
			</c:when>
			<c:otherwise>
			{ 	columnId:"iChargeNumber3",
				columnName:'#cspan',
				attachHeader:'${iCharge.chargeLabel3}',
				width:10
			},
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${!empty iCharge.chargeLabel4}">
		<c:choose>
			<c:when test = "${empty iCharge.chargeLabel1 && empty iCharge.chargeLabel2 && empty iCharge.chargeLabel3}">
			{ 	columnId:"iChargeNumber4",
				columnName:'<fmt:message key="label.indirect"/>',
				attachHeader:'${iCharge.chargeLabel4}',
				width:10
			},
			</c:when>
			<c:otherwise>
			{ 	columnId:"iChargeNumber4",
				columnName:'#cspan',
				attachHeader:'${iCharge.chargeLabel4}',
				width:10
			},
			</c:otherwise>
		</c:choose>
		</c:if>
	</c:if>
</c:if>
{ 	columnId:"percent",
	columnName:'<fmt:message key="label.percent"/>',
	width:5
}
];

<%-- Define the grid options--%>
var gridConfig = {
	divName: 'chargeNumberBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
	beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
	beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
	config: config,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
	rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
	submitDefault:true, // the fields in grid are defaulted to be submitted or not.
	noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
	variableHeight:false,
	selectChild: 1
	};

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
}

drawMenus();

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);">
<tcmis:form action="/chargenumbersetupresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
     ${tcmISError}<br/>
    <c:forEach var="tcmisError" items="${tcmISErrors}">
      ${tcmisError}<br/>
    </c:forEach>
    <c:if test="${param.maxData == fn:length(chargeNoColl)}">
     <fmt:message key="label.maxdata">
      <fmt:param value="${param.maxData}"/>
     </fmt:message>
    </c:if>
</div>
<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
<c:when test="${empty tcmISErrors and empty tcmISError}">
 <c:choose>
  <c:when test="${param.maxData == fn:length(chargeNoColl)}">
    showErrorMessage = true;
    parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
  </c:when>
  <c:otherwise>
    showErrorMessage = false;
  </c:otherwise>
 </c:choose>
</c:when>
<c:otherwise>
  parent.messagesData.errors = "<fmt:message key="label.errors"/>";
  showErrorMessage = true;
  </c:otherwise>
</c:choose>
//-->

</script>
<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="chargeNumberBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:choose>
<c:when test="${empty searchResults}">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
		<tr>
			<td width="100%">
				<fmt:message key="main.nodatafound"/>
			</td>
		</tr>
	</table>
</c:when>				
<c:otherwise>
	<c:set var="dataCount" value='0'/>
	<script type="text/javascript">
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
	<c:forEach var="chargeNoBean" items="${searchResults}" varStatus="status">
	<c:set var="showUpdateLink" value='N'/>
	 
	<c:if test="${status.index > 0}">,</c:if>
	
	/*The row ID needs to start with 1 per their design.*/
	{ id:${status.index +1},
	/* Set the color to the rows according to their releaseStatus*/
		 data:['Y',
		 <c:choose>
			 <c:when test ="${chargeNoBean.catPartNo != null}">
			 	'${chargeNoBean.applicationDesc} (${chargeNoBean.catPartNo})',
			 </c:when>
			 <c:otherwise>
			 '${chargeNoBean.applicationDesc}',
			 </c:otherwise>
		 </c:choose>
                '${status.current.deptName}',        
        <c:set var="defaultChargeType" value='Direct'/>
		<c:if test="${status.current.chargeTypeDefault == 'i' || status.current.chargeTypeDefault == 'I'}"><c:set var="defaultChargeType" value='Indirect'/></c:if>
		'${defaultChargeType}',
		 <c:choose>
			 <c:when test ="${chargeNoBean.chargeType == 'd'}">
				 '${status.current.poNumber}',
				 '${status.current.poLine}',
				 '',
				 '',
			 </c:when>
			 <c:otherwise>
				 '',
				 '',
				 '${status.current.poNumber}',
				 '${status.current.poLine}',
			 </c:otherwise>
		 </c:choose>
		<c:if test="${prAcctReq == 'Y' || prAcctReq == 'y'}">
			 <c:choose>
				 <c:when test ="${chargeNoBean.chargeType == 'd'}">
					 <c:if test="${dCharge != null && dCharge != ''}">
						 <c:if test="${!empty dCharge.chargeLabel1}">'${status.current.chargeNumber1}',</c:if>
						 <c:if test="${!empty dCharge.chargeLabel2}">'${status.current.chargeNumber2}',</c:if>
						 <c:if test="${!empty dCharge.chargeLabel3}">'${status.current.chargeNumber3}',</c:if>
						 <c:if test="${!empty dCharge.chargeLabel4}">'${status.current.chargeNumber4}',</c:if>
					 </c:if>
					 <c:if test="${iCharge != null && iCharge != ''}">
						 <c:if test="${!empty iCharge.chargeLabel1}">'',</c:if>
						 <c:if test="${!empty iCharge.chargeLabel2}">'',</c:if>
						 <c:if test="${!empty iCharge.chargeLabel3}">'',</c:if>
						 <c:if test="${!empty iCharge.chargeLabel4}">'',</c:if>
					 </c:if>
				 </c:when>
				 <c:otherwise>
					 <c:if test="${dCharge != null && dCharge != ''}">
						 <c:if test="${!empty dCharge.chargeLabel1}">'',</c:if>
						 <c:if test="${!empty dCharge.chargeLabel2}">'',</c:if>
						 <c:if test="${!empty dCharge.chargeLabel3}">'',</c:if>
						 <c:if test="${!empty dCharge.chargeLabel4}">'',</c:if>
					 </c:if>
					 <c:if test="${iCharge != null && iCharge != ''}">
						 <c:if test="${!empty iCharge.chargeLabel1}">'${status.current.chargeNumber1}',</c:if>
						 <c:if test="${!empty iCharge.chargeLabel2}">'${status.current.chargeNumber2}',</c:if>
						 <c:if test="${!empty iCharge.chargeLabel3}">'${status.current.chargeNumber3}',</c:if>
						 <c:if test="${!empty iCharge.chargeLabel4}">'${status.current.chargeNumber4}',</c:if>
					 </c:if>
				 </c:otherwise>
			 </c:choose>
		 </c:if>
		 '${chargeNoBean.percent}']}
	 <c:set var="dataCount" value='${dataCount+1}'/> 
	 </c:forEach>
	]};
	</script>
</c:otherwise>
</c:choose>
<script type="text/javascript">
<%-- determining rowspan --%>
<c:set var="rowSpanCount" value='0' />
	<%-- determining rowspan --%>
	<c:forEach var="row" items="${searchResults}" varStatus="status">
	<c:set var="tmpCatPartNo" value='' />
	<c:if test="${row.catPartNo != null}"><c:set var="tmpCatPartNo" value='${row.catPartNo}'/></c:if>
	<c:set var="currentKey" value='${row.application}|${row.chargeType}|${tmpCatPartNo}' />
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
</script>
<!-- Search results end -->

<!-- Hidden element start --> 
			
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
	<input name="uAction" id="uAction" type="hidden" value="${param.uAction}" />
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>
    <input name="maxData" id="maxData" type="hidden" value="${param.maxData}"/>

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>