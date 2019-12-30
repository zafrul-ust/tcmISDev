<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	errors:"<fmt:message key="label.errors"/>",
	recordFound:"<fmt:message key="label.recordFound"/>",
	searchDuration:"<fmt:message key="label.searchDuration"/>",
	minutes:"<fmt:message key="label.minutes"/>",
	seconds:"<fmt:message key="label.seconds"/>",
	pleaseselect:"<fmt:message key="label.pleaseselect"/>",  
	totallinecharges:"<fmt:message key="label.totallinecharges"/>", 
	alreadyselected:"<fmt:message key="label.alreadyselected"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	price:'<fmt:message key="label.price"/>',
	description:'<fmt:message key="label.feedescription"/>'
};

<c:set var="module">
	<tcmis:module/>
</c:set>

windowCloseOnEsc = true;
var lineMap3 = new Array();

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="if(window['gridConfig']){popupOnLoadWithGrid(gridConfig);setTimeout('loadRowSpans()',100);}"  onresize="resizeFrames()">

<tcmis:form action="/showpricelist.do" onsubmit="return submitFrameOnlyOnce();">

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
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
&gt;
<a href="#" onclick="alert('show add line url??');return false;"><fmt:message key="label.addline"/></a>	
<a href="#" onclick="alert('show all previous orders url??');return false;"><fmt:message key="label.showpreviousordersforcustomer"/></a>
<a href="#" onclick="alert('show previous orders url??');return false;"><fmt:message key="label.showallpreviousorders"/> --NEED CUSTOMER NAME--</a>
      --%>      
      &nbsp;
      <div id="headerline"><span id="mainUpdateLinks" style="display:"></span> 
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->

<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty beanCollection}" >
<div id="PriceListViewBean" style="height:400px;"></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>
/*Storing the data to be displayed in a JSON object array.*/

<c:choose>
	<c:when test="${module == 'distribution'}">
		var rowSpanCols = [1,2,3,4,5,6,7,8];
		var rowSpanLvl2Cols = [9,10,11];
	</c:when>
	<c:otherwise>
		var rowSpanCols = [1,2,3,4,5,6,7];
		var rowSpanLvl2Cols = [8,9,10];
	</c:otherwise>
</c:choose>

var gridConfig = {
		divName:'PriceListViewBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		noSmartRender:false,
		submitDefault:true    // the fields in grid are defaulted to be submitted or not.
	};

	var config = [
		{columnId:"permission"},
		{columnId:"opsEntityId"},
		{columnId:"priceGroupName", columnName:'<fmt:message key="label.pricegroupid"/>', width:5},
		{columnId:"catalogCompanyName", columnName:'<fmt:message key="label.catalogcompany"/>', width:6},
	        {columnId:"catalogName", columnName:'<fmt:message key="label.catalog"/>'},
		<c:if test="${module == 'distribution'}">  
			{ columnId:"partName", columnName:'<fmt:message key="label.catalogitem"/>', sorting:"haasStr", tooltip:true, width:6},
	           	{ columnId:"specList", columnName:'<fmt:message key="label.specification"/>', tooltip:true},
	           	{ columnId:"description", columnName:'<fmt:message key="label.description"/>', sorting:"haasStr", tooltip:true, width:14},
		</c:if>
		<c:if test="${module != 'distribution'}">  
	             	{columnId:"partName", columnName:'<fmt:message key="label.catpartno"/>', width:6 },
	             	{columnId:"description", columnName:'<fmt:message key="label.description"/>', width:20},
		</c:if>  
		{columnId:"startDateDisplay",columnName:'<fmt:message key="label.startDate"/>', width:7},
		{columnId:"baselinePrice", columnName:'<fmt:message key="label.baseline"/>', type:'hed', align:'right', width:5},
	        {columnId:"currencyId", columnName:'<fmt:message key="label.currency"/>', width:6},
		{columnId:"breakQuantityPermission"},
		{columnId:"breakQuantity", columnName:'<fmt:message key="label.qty"/>', type:"hed", align:'right', permission:true, width:3},
		{columnId:"catalogPrice", columnName:'<fmt:message key="label.unitprice"/>', type:"hed", align:'right', width:4},
		{columnId:"dup"},
		{columnId:"catPartNo"},
		{columnId:"companyId"},
		{columnId:"catalogId"},
		{columnId:"partGroupNo"},
		{columnId:"startDate"},
		{columnId:"oldStartDate"},
		{columnId:"endDate"},
		{columnId:"oldBreakQuantity"},
		{columnId:"oldCatalogPrice"},
		{columnId:"catalogCompanyId"},
		{columnId:"changed"},
		{columnId:"isParent"}
	];


	var map = null;
	var lineMap = new Array();
	var line2Map = new Array();
	var lineIdMap = new Array();
	var rowIdMap = new Array();
	var partMap1 = new Array();
	var partMap2 = new Array();

	<c:set var="gridind" value="0"/>
	<c:set var="prePar" value=""/>
	<c:set var="samePartCount" value="0"/>
	<c:set var="colorIndex" value="-1"/>
	<c:forEach var="bean" items="${beanCollection}" varStatus="status">
		<c:set var="curPar" value="${bean.priceGroupId}-${bean.partName}-${bean.specListId}"/>
		<tcmis:jsReplace value="${curPar}" var="jsCurPar"/>
		<c:if test="${ curPar == prePar }">
			<c:set var="samePartCount" value="${samePartCount+1}"/>
			partMap2['${jsCurPar}'] = partMap1['${jsCurPar}'];
			partMap1['${jsCurPar}'] = ${gridind+1};
		</c:if>
		<c:if test="${ curPar != prePar }">
			<c:set var="samePartCount" value="1"/>
			<c:set var="firstLineIndex" value="${gridind}"/>
			<c:set var="colorIndex" value="${colorIndex+1}"/>
			partMap1['${jsCurPar}'] = ${gridind+1};
		</c:if>
		
		<bean:size collection="${status.current.priceBreakCollection}" id="resultSize"/>
	    	lineMap3[${gridind}] = ${colorIndex%2} ;
		line2Map[${gridind}] = ${resultSize+1};
		<c:if test="${ curPar == prePar }">
			lineMap[${firstLineIndex}] += ${resultSize+1};
		</c:if>
		<c:if test="${ curPar != prePar }">
			lineMap[${gridind}] = ${resultSize+1};
			map = new Array();
		</c:if>
		<c:set var="gridind" value="${gridind+1}"/> 
		lineIdMap[""+${gridind}] = map;
		map[map.length] = ${gridind} ;
	    	rowIdMap[rowIdMap.length] = ${gridind} ;

		<c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
			lineMap3[${gridind}] = ${colorIndex%2} ;
			<%--
			<c:if test="${status2.index==0}">
				line2Map[${gridind}] = ${resultSize};
			</c:if>
			--%>
			<c:set var="gridind" value="${gridind+1}"/> 
			lineIdMap[""+${gridind}] = map;
			map[map.length] = ${gridind} ;
			rowIdMap[rowIdMap.length] = ${gridind} ;
		</c:forEach>
		<c:set var="prePar" value="${curPar}"/>
	</c:forEach>

	<c:set var="todayval">
		<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="yyyy-MM-dd"/>
	</c:set>
			
	var jsonMainData = new Array();
	var jsonMainData = {
	rows:[
	<c:forEach var="bean" items="${beanCollection}" varStatus="status">
	<fmt:formatDate var="startDatev" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
	<fmt:formatDate var="endDatev" value="${bean.endDate}" pattern="${dateFormatPattern}"/>
	<fmt:formatDate var="startDate" value="${bean.startDate}" pattern="${dateFormatPattern}"/>
	<fmt:formatDate var="expireDate" value="${bean.endDate}" pattern="${dateFormatPattern}"/>
	<fmt:formatDate var="expireYear" value="${bean.endDate}" pattern="yyyy"/>
	<c:if test="${expireYear == '3000'}">
		<c:set var="expireDate"><fmt:message key="label.indefinite"/></c:set>
	</c:if>
	<bean:size collection="${status.current.priceBreakCollection}" id="resultSize"/>

	<c:if test="${dataCount > 0}">,</c:if>
	<c:set var="dataCount" value='${dataCount+1}'/>
	<c:set var="dup">
	<%--
	<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="<fmt:message key="receiving.label.duplicateline"/>" onclick="dupline(${dataCount})" />
	--%>
	<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
	</c:set>

	<fmt:formatDate var="startDateVal" value="${bean.startDate}" pattern="yyyy-MM-dd"/>
<%--
	<c:if test="${ startDateVal >= todayval }">
		<c:set var="startDate">
			<input class="inputBox pointer" id="startDateDisplay${dataCount}" type="text" value="${startDate}" size="8" readonly onClick="return myGetCalendar(${dataCount})"/>
		</c:set>
	</c:if>
--%>
	<tcmis:jsReplace value="${dup}" var="dup"/>
	<tcmis:jsReplace value="${bean.specList}" var="specList"/>

	{ id:${dataCount},
	 data:[
	  'N',
	  '${bean.opsEntityId}',
	  '<tcmis:jsReplace value="${bean.priceGroupName}" />', 
	  '<tcmis:jsReplace value="${bean.catalogCompanyName}" />',  
	  '<tcmis:jsReplace value="${bean.catalogName}" />', 
	  '<tcmis:jsReplace value="${bean.partName}" />',
<c:if test="${module == 'distribution'}">  
      '${bean.specListId}:${specList}',
</c:if>
	  '<tcmis:jsReplace value="${bean.partDesc}"  processMultiLines="true"/>',
	  '${startDate}',
	  '${bean.baselinePrice}',
	  '${bean.currencyId}',
	  'N',
	  '1',
	  '${bean.catalogPrice}',
	  '${dup}',
	  '<tcmis:jsReplace value="${bean.catPartNo}" />',
	  '${bean.companyId}',
	  '${bean.catalogId}',
	  '${bean.partGroupNo}',
	  '${startDatev}',
	  '${startDatev}',
	  '${endDatev}',
	  '1',
	  '${bean.catalogPrice}',
	  '${bean.catalogCompanyId}',
	  '',
	  'Y'
	  ]
	}

	  <c:forEach var="bean2" items="${status.current.priceBreakCollection}" varStatus="status2">
	  <c:if test="${dataCount > 0}">,</c:if>
	  <c:set var="dataCount" value='${dataCount+1}'/>
	  <c:set var="dup">
	  <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="Add CPP" onclick="dupline(${dataCount})" />
	  </c:set>
	  <tcmis:jsReplace value="${dup}" var="dup"/>
	  <tcmis:jsReplace value="${bean.specList}" var="specList"/>
	  
	  { id:${dataCount},
	   data:[
	    'N',
	    '${bean.opsEntityId}',
	    '${bean.priceGroupId}', 
	    '<tcmis:jsReplace value="${bean.catalogCompanyName}" />', 
	    '<tcmis:jsReplace value="${bean.catalogName}" />', 
	    '<tcmis:jsReplace value="${bean.partName}" />',
	    <c:if test="${module == 'distribution'}">  
		'${bean.specListId}:${specList}',
		</c:if>
	    '<tcmis:jsReplace value="${bean.partDesc}" processMultiLines="true" />',  
	    '${expireDate}',
	    '${bean.baselinePrice}',
	    '${bean.currencyId}',
	    'N',
	    '${bean2.breakQuantity}',
	    '${bean2.catalogPrice}',
	    '${dup}',
	    '<tcmis:jsReplace value="${bean.catPartNo}" />',
	    '${bean.companyId}',
	    '${bean.catalogId}',
	    '${bean.partGroupNo}',
	    '${startDatev}',
	    '${startDatev}',
	    '${endDatev}',
	    '${bean2.breakQuantity}',
	    '${bean2.catalogPrice}',
	    '${bean.catalogCompanyId}',
	    '',
	    'N'
	    ]
	  }
	  </c:forEach>

	</c:forEach>
	]};

//-->
var rowSpanLvl2Map = line2Map;
</script>

<!-- end of grid div. -->
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
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

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value="update"/>
<input type="hidden" name="searchField" id="searchField" value="${param.searchField}"/>
<input type="hidden" name="searchMode" id="searchMode" value="${param.searchMode}"/>
<input type="hidden" name="searchArgument" id="searchArgument" value="${param.searchArgument}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
</div>

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${hasApprovalPermission == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
</script>
</c:if>

</body>
</html>
