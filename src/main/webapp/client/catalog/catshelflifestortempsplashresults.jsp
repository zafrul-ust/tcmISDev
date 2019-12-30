<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catshelflifestortempsplashresults.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->

<%--Uncomment the below if your grid has rowspan > 1 --%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This is to allow copy and paste. works only in IE--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<c:set var="module">
	 <tcmis:module/>
</c:set>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
contextDisabled = true;

var messagesData = new Array();

messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
part:"<fmt:message key="label.part"/>",
description:"<fmt:message key="label.description"/>",
shelflife:"<fmt:message key="catalog.label.shelflife"/>",
componentdescription:"<fmt:message key="inventory.label.componentdescription"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
mfgpartno:"<fmt:message key="label.mfgpartno"/>",
source:"<fmt:message key="transactions.source"/>",
spec:"<fmt:message key="catalogspec.label.spec"/>"
};

var map = null;
var map2 = null;
var prerow = null;
var preClass = null;
var rowSpanMap = new Array();
var rowSpanLvl2Map = new Array();
var rowSpanClassMap = new Array();
var lineIdMap = new Array();
var lineIdMap2 = new Array();

<%-- Define the right click menus --%>
with(milonic=new menuname("rightClickMenu")){
	top="offset=2"
	style = contextStyle;
	 margin=3
	 aI("text=<fmt:message key="label.print"/>;url=javascript:window.print();");	
}


<%-- Initialize the RCMenus --%>
drawMenus();

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="newinit();">
<tcmis:form action="/catshelflifestortempsplashresults.do" onsubmit="return submitFrameOnlyOnce();">

 <script type="text/javascript">
 <!--
  showUpdateLinks = false;  
 //-->
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
${tcmisError}
</div>

<script type="text/javascript">
<!--


/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmisError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
<c:if test="${prCatalogScreenSearchBeanCollection != null}" >
 <c:if test="${!empty prCatalogScreenSearchBeanCollection}" >
//  var jsonMainData = new Array();
//  The color stuff is not working at this moment, I will use
//  javascript to fix it.
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">

	  <c:if test="${status.index != 0 }">,</c:if>
	  <c:set var="curPar" value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}"/>
	  <c:if test="${!(curPar eq prePar)}">
			<c:set var="dataCount" value='${dataCount+1}'/>
	  </c:if>
		<c:set var="storageTemp" value='${status.current.storageTemp}' />
		<c:set var="shelfLife" value='${status.current.shelfLife}' />
		<c:set var="shelfLifeDisplay" value="" />
			<c:choose>
				<c:when test="${facilityOrAllShelflife}">
					<c:set var="shelfLifeDisplay" value="${status.current.shelfLifeList}" />
				</c:when>
				<c:when test="${empty storageTemp || storageTemp == ' '}">
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${shelfLife != 'Indefinite'}">
							<c:set var="shelfLifeBasis" value='${status.current.shelfLifeBasis}' />
							<c:if test="${!empty shelfLifeBasis}">
								<c:set var="shelfLifeDisplay" value="${status.current.shelfLife} ${shelfLifeBasis} @ ${storageTemp}" />
							</c:if>
							<c:if test="${empty shelfLifeBasis}">
								<c:set var="shelfLifeDisplay" value="${status.current.shelfLife} @ ${storageTemp}" />
							</c:if>
						</c:when>
						<c:otherwise>
							<c:set var="shelfLifeDisplay" value="${status.current.shelfLife} @ ${storageTemp}" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
      
      { id:${status.index +1},
        data:[
              {value:'<tcmis:jsReplace value="${p.catPartNo}" processMultiLines="true" processSpaces="true"/>'},
              {value:'<tcmis:jsReplace value="${p.partDescription}" processMultiLines="true"/>'},
              {value:'${shelfLifeDisplay}'},
              {value:'<tcmis:jsReplace value="${p.materialDesc}" processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.mfgPartNo}"  processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.specs}"  processMultiLines="true"/>'},
              {value:'<tcmis:jsReplace value="${p.source}"  processMultiLines="true"/>'}
			]}

		<c:set var="prePar" value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}"/>


	  </c:forEach>
  ]};

  </c:if>
 </c:if>
//-->
</script>
<!-- json data Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<%--NEW - there is no results table anymore--%>  
<div id="prCatalogScreenSearchBean" style="width:100%;%;height:400px;" style="display: none;"></div>


<c:if test="${prCatalogScreenSearchBeanCollection != null}" >
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty prCatalogScreenSearchBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="facilityOrAllShelflife" id="facilityOrAllShelflife" value="${facilityOrAllShelflife}" type="hidden"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

<c:set var="prePar" value=""/>
<c:set var="parCount" value="1"/>
<c:forEach var="p" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">

    <c:set var="curPar" value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}"/>
	<c:set var="curItem" value="itemId${status.current.itemId}"/>
	<script language="JavaScript" type="text/javascript">
	<!--
	
		<c:if test="${!(curPar eq prePar)}">
		   	<c:set var="parCount" value="${parCount+1}"/>
	  			<c:set var="preItem" value=""/>
		   	rowSpanMap[${status.index}] = ${rowCountPart[curPar]};
		</c:if>
		<c:if test="${ !( curItem eq preItem)}">
			rowSpanLvl2Map[${status.index}] = ${rowCountItem[curPar][curItem]};
		</c:if>
	  	rowSpanClassMap[${status.index}] = ${parCount % 2} ;
	// -->
	</script>	
		 <c:set var="prePar" value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}"/>
		<c:set var="preItem" value="itemId${status.current.itemId}"/>

</c:forEach>	


</body>
</html>
