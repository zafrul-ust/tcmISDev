<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
CMS Pricelist History
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

windowCloseOnEsc = true;
window.onresize = resizeFrames;

			<%-- Define the columns for the result grid --%>
			var columnConfig = [ 
				{columnId:"permission"},
				{columnId:"catPartNo", columnName:'<fmt:message key="label.partno"/>', width:10},
				{columnId:"itemId", columnName:'<fmt:message key="label.itemid"/>', sorting:'int', tooltip:"Y", width:10 },
				{columnId:"itemDesc", columnName:'<fmt:message key="label.itemdescription"/>', tooltip:"Y", width:20 },
				{columnId:"startDate", columnName:'<fmt:message key="label.startingdate"/>', align:'center', width:8, hiddenSortingColumn: "startDateSort"},
				{columnId:"startDateSort", sorting: "int"}, 	
				{columnId:"endDate", type:'hcal', columnName:'<fmt:message key="label.enddate"/>', align:'center', width:8, hiddenSortingColumn: "endDateSort"},
				{columnId:"endDateSort", sorting: "int"}, 	
				{columnId:"currencyId", columnName:'<fmt:message key="label.currency"/>', width:5},
				{columnId:"catalogPrice", columnName:'<fmt:message key="label.catalogprice"/>',  sorting:'int', width:8},
				{columnId:"lastUpdatedBy", columnName:'<fmt:message key="label.lastUpdatedBy"/>',  sorting:'int', width:12, tooltip:"Y"},
				{columnId:"loadingComments", columnName:'<fmt:message key="label.auditnotes"/>',  tooltip:"Y", width:40}
			]; 

			<%-- Define the grid options--%>
			var gridConfig = {
				divName: 'CMSPricelistBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
				beanData: 'jsonMainData',	<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
				beanGrid: 'mygrid',		<%--  variable to put the grid object in for later use --%>
				config: columnConfig,		<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
				submitDefault: false,
				backgroundRender: true,
				rowSpan: false	 		<%--  true: this page has rowSpan > 1 or not, disables smartrendering & sorting --%>
			};

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid(gridConfig)">
<tcmis:form action="/CMSPricelist.do" onsubmit="return submitFrameOnlyOnce();">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
	showErrorMessage = true;
</c:if>
<c:forEach var="errorMsg" items="${tcmISError}">
	<c:if test="${!empty errorMsg}">
		showErrorMessage = true;
	</c:if>
</c:forEach>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<div id="resultGridDiv" style="display: none;">
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
	  </span>
       &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<c:set var="dataCount" value='0'/>
<!--  result page section start -->
<c:if test="${!empty searchResults}" >

<div id="CMSPricelistBean" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--
		var jsonMainData = {
			rows:[<c:forEach var="row" items="${searchResults}" varStatus="status">
				{ id:${status.count},
				  data:['N',
					'<tcmis:jsReplace value="${row.catPartNo}" processMultiLines="true" />',
					'<tcmis:jsReplace value="${row.itemId}" processMultiLines="true" />',
					'<tcmis:jsReplace value="${row.itemDesc}" processMultiLines="true" />',
					'<fmt:formatDate value="${row.startDate}" pattern="${dateFormatPattern}"/>',
					'${row.startDate.time}',
		            <fmt:formatDate var="formattedEndDate" value="${row.endDate}" pattern="${dateFormatPattern}"/>
					<c:if test="${formattedEndDate == '01-Jan-3000'}"><c:set var="formattedEndDate"><fmt:message key="label.indefinite"/></c:set></c:if>											
					'${formattedEndDate}',
					'${row.endDate.time}',
					'${row.currencyId}',
			        '<fmt:formatNumber value="${row.catalogPrice}"  pattern="${totalcurrencyformat}"/>',
					'<tcmis:jsReplace value="${row.lastUpdatedName}" processMultiLines="true" />',
					'<tcmis:jsReplace value="${row.loadingComments}" processMultiLines="true" />'
				  ]}<c:if test="${!status.last}">,</c:if>
				</c:forEach>
			]};
// -->
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty searchResults}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<!-- Search results end -->
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="totalLines" id="totalLines" value="${fn:length(searchResults)}" />
<input name="uAction" id="uAction" value="" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>

</div>
<!-- Hidden elements end -->
</div> <!-- close of interface -->
</tcmis:form>
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>



</body>
</html>
