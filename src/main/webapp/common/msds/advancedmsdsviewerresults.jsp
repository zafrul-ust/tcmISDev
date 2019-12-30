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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/common/msds/advancedmsdsviewerresults.js"></script>

<title>
    <fmt:message key="title.msdsviewer"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--

var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [0,1,2,3,4,5];
var rowSpanLvl2Map = new Array();
var rowSpanLvl2Cols = [6,7,8];

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=YYY;url=javascript:doNothing();");
}

drawMenus();

var newMsdsViewer = true;

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    recordFound:"<fmt:message key="label.recordFound"/>",
    searchDuration:"<fmt:message key="label.searchDuration"/>",
    minutes:"<fmt:message key="label.minutes"/>",
	errors:"<fmt:message key="label.errors"/>",
    seconds:"<fmt:message key="label.seconds"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    viewkitmsds:"<fmt:message key="label.viewkitmsds"/>",
    viewmsds:"<fmt:message key="label.viewmsds"/>",
    showstoragelocations:"<fmt:message key="label.showstoragelocations"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};

var config = [
{ columnId:"customerMsdsDbName",
    <c:if test="${showMSDS == 'Y'}">
        columnName:'<fmt:message key="label.msdsdatabase"/>',
    </c:if>
    align:"left"
},
{ columnId:"materialIdDisplay",
  columnName:'<fmt:message key="label.materialid"/>',
  width:8
},
{ columnId:"materialDesc",
  columnName:'<fmt:message key="label.description"/>',
  tooltip:"Y",
  align:"left",
  width:15
},
{ columnId:"casNumber",
  <c:if test="${showCASNumber == 'Y'}">
    columnName:'<fmt:message key="label.casnumber"/>',
  </c:if>
  tooltip:"Y",
  align:"left",
  width:10
},
{ columnId:"tradeName",
  columnName:'<fmt:message key="label.tradename"/>',
  tooltip:"Y",
  align:"left",
  width:15
},
{ columnId:"mfgDesc",
  columnName:'<fmt:message key="label.manufacturer"/>',
  tooltip:"Y",
  align:"left",
  width:12
},
{ columnId:"customerMsdsNumber",
  <c:if test="${showMSDS == 'Y'}">
    columnName:'<fmt:message key="label.msds"/>',
  </c:if>
  align:"left",
  width:6
},
{
  columnId:"lastMsdsRevisionDate",
  align:"center"
},
{ columnId:"msdsApprovalCode",
  <c:if test="${showMSDS == 'Y' && showFacilityUseCode == 'Y'}">
    columnName:'<fmt:message key="label.msdsapprovalcode"/>',
  </c:if>
  tooltip:"Y",
  align:"left",
  width:8
},
{ columnId:"customerMixtureNumber",
  <c:if test="${showMixture == 'Y'}">
    columnName:'<fmt:message key="label.kitmsds"/>',
  </c:if>
  tooltip:"Y",
  align:"left",
  width:6
},
{ columnId:"mixRatio",
  <c:if test="${showMixture == 'Y'}">
    columnName:'<fmt:message key="label.mixratio"/>',
  </c:if>
  align:"right",
  width:6
},
{ columnId:"kitApprovalCode",
  <c:if test="${showMixture == 'Y' && showFacilityUseCode == 'Y'}">
    columnName:'<fmt:message key="label.kitapprovalcodes"/>',
  </c:if>
  tooltip:"Y",
  align:"left",
  width:8
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  align:"left",
  width:5
},
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.partno"/>',
  tooltip:"Y",
  align:"left",
  width:18
},
{ columnId:"partApprovalCode",
  <c:if test="${showFacilityUseCode == 'Y'}">
    columnName:'<fmt:message key="label.partapprovalcodes"/>',
  </c:if>
  tooltip:"Y",
  align:"left",
  width:12
},
{ columnId:"materialId"
},
{ columnId:"msdsOnLine"
},
{ columnId:"customerMsdsNumber"
},
{ columnId:"customerMsdsDb"
},
{ columnId:"companyId"
}

];

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="setTimeout('resizeFrames();',55)">
<tcmis:form action="/advancedmsdsviewerresults.do" onsubmit="return submitFrameOnlyOnce();">
		<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>
</div>

<script type="text/javascript">
   <c:choose>
   <c:when test="${empty tcmISError && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>
    //-->
</script>
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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

<div class="interface" id="resultsPage">
<div class="backGroundContent"> <!-- start of backGroundContent -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<div id="resultGridDiv" style="display: none;">

<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
        <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
   			<a href="javascript:opener.showLegend()"><fmt:message key="label.showlegend"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">

<div id="msdsViewerBeanDiv" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${!empty msdsViewerBeanCollection}" >
<script language="JavaScript" type="text/javascript">
<!--
<c:set var="parCount" value="0"/>

<c:set var="dataCount" value='${0}'/>
<c:set var="prePar" value=""/>

 <c:if test="${!empty msdsViewerBeanCollection}" >
//  var jsonMainData = new Array();
//  The color stuff is not working at this moment, I will use
//  javascript to fix it.
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${msdsViewerBeanCollection}" varStatus="status">
  		<c:set var="materialIdDisplay" value="${p.materialId}"/>
	    <c:if test="${p.msdsOnLine == 'Y' }">
	        <c:set var="materialIdDisplay">${p.materialId} <img src="/images/buttons/document.gif" onclick="showMsds=true;" /></c:set>
	    </c:if>
	    <fmt:formatDate var="fmtRevisionDate" value="${status.current.lastMsdsRevisionDate}" pattern="${dateFormatPattern}"/>
        <c:set var="tmpMsdsApprovalCode" value="${p.msdsApprovalCode}"/>
        <c:if test="${showMixture == 'Y' && !empty p.customerMixtureNumber}">
            <c:set var="tmpMsdsApprovalCode" value=""/>
        </c:if>

        <c:set var="tmpMixRatioUnit" value="${p.mixRatioSizeUnit}"/>
        <c:if test="${p.mixRatioSizeUnit == '%(v/v)'}">
            <c:set var="tmpMixRatioUnit"><fmt:message key="report.label.percentByVolume"/></c:set>
        </c:if>
         <c:if test="${p.mixRatioSizeUnit == '%(w/w)'}">
            <c:set var="tmpMixRatioUnit"><fmt:message key="report.label.percentByWeight"/></c:set>
        </c:if>

        <c:if test="${status.index != 0 }">,</c:if>
	    { id:${status.index +1},
	        data:[
	                '${p.customerMsdsDb}',
	                '${materialIdDisplay}',
	                '<tcmis:jsReplace value="${p.materialDesc}" processMultiLines="true"/>',
                    '${p.casNumberList}',
                    '<tcmis:jsReplace value="${p.tradeName}" processMultiLines="true"/>',
	                '<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>',
	                '${p.customerMsdsNumber}',
                    '${fmtRevisionDate}',
                    '${p.msdsApprovalCode}',
	                '${p.customerMixtureNumber}',
                    '<fmt:formatNumber value="${p.mixRatioAmount}" pattern="${totalcurrencyformat}"/> ${tmpMixRatioUnit}',
                    '${p.kitApprovalCode}',
	                '${p.itemId}',
                    '<tcmis:jsReplace value="${p.catPartNo}" processMultiLines="true"/>',
					'<tcmis:jsReplace value="${p.partApprovalCode}" processMultiLines="true"/>',
	                '${p.materialId}',
                    '${p.msdsOnLine}',
                    '${p.customerMsdsNumber}',
	                '${p.customerMsdsDb}',
                    '${p.companyId}'
	        ]}
  </c:forEach>
  ]};

 <%-- determining rowspan --%>
<c:set var="rowSpanCount" value='0' />
<c:if test="${showMixture == 'Y'}">
var rowSpanLvl3Map = new Array();
var rowSpanLvl3Cols = [9,10,11];
	<%-- determining rowspan --%>
	<c:forEach var="row" items="${msdsViewerBeanCollection}" varStatus="status">
		<c:set var="currentKey" value='${row.customerMsdsDb}|${row.materialId}' />
		<c:set var="currentKeyLvl2" value='${row.customerMsdsDb}|${row.materialId}|${row.customerMsdsNumber}' />
		<c:set var="currentKeyLvl3" value='${row.customerMsdsDb}|${row.materialId}|${row.customerMsdsNumber}|${row.customerMixtureNumber}' />
		<c:choose>
			<c:when test="${status.first}">
				<c:set var="rowSpanStart" value='0' />
				<c:set var="rowSpanLvl2Start" value='0' />
				<c:set var="rowSpanLvl3Start" value='0' />
				<c:set var="rowSpanCount" value='1' />
				rowSpanMap[0] = 1;
				rowSpanLvl2Map[0] = 1;
				rowSpanLvl3Map[0] = 1;
				rowSpanClassMap[0] = 1;
			</c:when>
			<c:when test="${currentKey == previousKey}">
				rowSpanMap[${rowSpanStart}]++;
				rowSpanMap[${status.index}] = 0;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
				<c:choose>
					<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
						rowSpanLvl2Map[${rowSpanLvl2Start}]++;
						rowSpanLvl2Map[${status.index}] = 0;
						<c:choose>
							<c:when test="${currentKeyLvl3 == previousKeyLvl3}">
								rowSpanLvl3Map[${rowSpanLvl3Start}]++;
								rowSpanLvl3Map[${status.index}] = 0;
							</c:when>
							<c:otherwise>
								<c:set var="rowSpanLvl3Start" value='${status.index}' />
								rowSpanLvl3Map[${status.index}] = 1;
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:set var="rowSpanLvl2Start" value='${status.index}' />
					<%-- level 3 fixed  --%>
						<c:set var="rowSpanLvl3Start" value='${status.index}' />
						rowSpanLvl2Map[${status.index}] = 1;
						rowSpanLvl3Map[${status.index}] = 1;
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
				<c:set var="rowSpanStart" value='${status.index}' />
				<c:set var="rowSpanLvl2Start" value='${status.index}' />
				<c:set var="rowSpanLvl3Start" value='${status.index}' />
				rowSpanMap[${status.index}] = 1;
				rowSpanLvl2Map[${status.index}] = 1;
				rowSpanLvl3Map[${status.index}] = 1;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
			</c:otherwise>
		</c:choose>
		<c:set var="previousKey" value='${currentKey}' />
		<c:set var="previousKeyLvl2" value='${currentKeyLvl2}' />
		<c:set var="previousKeyLvl3" value='${currentKeyLvl3}' />
	</c:forEach> 
</c:if>
<c:if test="${showMixture != 'Y'}">
	<%-- determining rowspan --%>
	<c:forEach var="row" items="${msdsViewerBeanCollection}" varStatus="status">
		<c:set var="currentKey" value='${row.customerMsdsDb}|${row.materialId}' />
		<c:set var="currentKeyLvl2" value='${row.customerMsdsDb}|${row.materialId}|${row.customerMsdsNumber}' />
		<c:choose>
			<c:when test="${status.first}">
				<c:set var="rowSpanStart" value='0' />
				<c:set var="rowSpanLvl2Start" value='0' />
				<c:set var="rowSpanCount" value='1' />
				rowSpanMap[0] = 1;
				rowSpanLvl2Map[0] = 1;
				rowSpanClassMap[0] = 1;
			</c:when>
			<c:when test="${currentKey == previousKey}">
				rowSpanMap[${rowSpanStart}]++;
				rowSpanMap[${status.index}] = 0;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
				<c:choose>
					<c:when test="${currentKeyLvl2 == previousKeyLvl2}">
						rowSpanLvl2Map[${rowSpanLvl2Start}]++;
						rowSpanLvl2Map[${status.index}] = 0;
					</c:when>
					<c:otherwise>
						<c:set var="rowSpanLvl2Start" value='${status.index}' />
						rowSpanLvl2Map[${status.index}] = 1;
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
				<c:set var="rowSpanStart" value='${status.index}' />
				<c:set var="rowSpanLvl2Start" value='${status.index}' />
				rowSpanMap[${status.index}] = 1;
				rowSpanLvl2Map[${status.index}] = 1;
				rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
			</c:otherwise>
		</c:choose>
		<c:set var="previousKey" value='${currentKey}' />
		<c:set var="previousKeyLvl2" value='${currentKeyLvl2}' />
	</c:forEach> 
</c:if>

 </c:if>

// -->
</script>
</c:if> 
<!-- If the collection is empty say no data found -->

<c:if test="${empty msdsViewerBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>

</div>
</div>

<div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
  </div>
</div>  
  </td>
  
</tr>
</table>
<!-- results end -->
 <!-- Search results end --> 

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${rowSpanCount}"/>" type="hidden">
        <input name="uAction" id="uAction" value="" type="hidden"> 
        <input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"> 
        <input name="module" id="module" value="${module}" type="hidden">
        <input name="showMsds" id="showMsds" value="${showMSDS}" type="hidden">
        <input name="showCASNumber" id="showCASNumber" value="${showCASNumber}" type="hidden">
        <input name="showMixture" id="showMixture" value="${showMixture}" type="hidden">
        <input name="showFacilityUseCode" id="showFacilityUseCode" value="${showFacilityUseCode}" type="hidden">
        <input name="minHeight" id="minHeight" type="hidden" value="100">
        <input name="startSearchTime" id="startSearchTime" type="hidden" value="${param.startSearchTime }">
		<!--This sets the end time for time elapesed from the action.-->
		<input name="endSearchTime" id="endSearchTime" type="hidden" value="">
        
        
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>