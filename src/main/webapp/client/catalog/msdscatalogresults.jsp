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
<script type="text/javascript" src="/js/client/catalog/msdscatalog.js"></script>


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

var messagesData = new Array();
messagesData = {
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    recordFound:"<fmt:message key="label.recordFound"/>",
    searchDuration:"<fmt:message key="label.searchDuration"/>",
    minutes:"<fmt:message key="label.minutes"/>",
    seconds:"<fmt:message key="label.seconds"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>",
    viewkitmsds:"<fmt:message key="label.viewkitmsds"/>",
    viewmsds:"<fmt:message key="label.viewmsds"/>",
    showstoragelocations:"<fmt:message key="label.showstoragelocations"/>",
    addeditsynonym:"<fmt:message key="label.addeditsynonym"/>",
    viewComponentMsds:"<fmt:message key="label.viewcomponentmsds"/>",
    viewapproval:"<fmt:message key="label.viewapproval"/>",
    cataddreq:"<fmt:message key="label.cataddreq"/>",
    grandfatheredMaterial:"<fmt:message key="label.grandfathermaterial"/>",
    printHazCommLabels:"<fmt:message key="label.printhazcommlabels"/>",
    printsecondarylabelinformation:"<fmt:message key="label.printsecondarylabelinformation"/>",
    secondarylabelinformation:"<fmt:message key="label.secondarylabelinformation"/>",
    labelquantity:"<fmt:message key="label.labelquantity"/>",    
    endDate:"<fmt:message key="label.enddate"/>",
    startDate:"<fmt:message key="label.startDate"/>",
    addApprovalCode:"<fmt:message key="label.addapprovalcode"/>"
};

var newMsdsViewer = false;
<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>

var printHazCommLabels = false;
<tcmis:featureReleased feature="PrintHazCommLabels" scope="ALL">
  printHazCommLabels = true;
</tcmis:featureReleased>

var lmcoModule = false;
<c:if test="${module == 'lmco'}">
lmcoModule = true;
</c:if>

var showFacilityUseCode = '${showFacilityUseCode}';

var config = [
{
    columnId:"customerMixtureNumber",
    <c:if test="${isCompanyUsesCustomerMSDS == 'Y'}">
        columnName:'<fmt:message key="label.kitmsds"/>',
    </c:if>
    width:8
},
{
    columnId:"mixtureDesc",
    <c:if test="${isCompanyUsesCustomerMSDS == 'Y'}">
        columnName:'<fmt:message key="label.kitdesc"/>',
    </c:if>
    width:15,
    tooltip:"Y"
},
{
    columnId:"approvalCode",
    <c:if test="${showFacilityUseCode == 'Y'}">
        columnName:'<fmt:message key="label.approvalcode"/>',
    </c:if>
    width:8
},
{
    columnId:"customerMsdsNumber",
    <c:if test="${isCompanyUsesCustomerMSDS == 'Y'}">
        columnName:'<fmt:message key="label.msds"/>',
    </c:if>
    width:8
},
{
    columnId:"materialDesc",
	columnName:'<fmt:message key="label.materialdesc"/>',
	width:30,
    tooltip:"Y"
},
{
    columnId:"mixRatio",
    <c:if test="${isCompanyUsesCustomerMSDS == 'Y'}">
        columnName:'<fmt:message key="label.mixratio"/>',
    </c:if>
    width:8
},
{
    columnId:"materialId",
    columnName:'<fmt:message key="label.materialid"/>'
},
{
    columnId:"mfgDesc",
	columnName:'<fmt:message key="label.manufacturer"/>',
    width:15,
    tooltip:"Y"
},
{
    columnId:"tradeName",
	columnName:'<fmt:message key="label.tradename"/>',
	width:30,
    tooltip:"Y"
},
{
    columnId:"msdsOnLine"
},
{
    columnId:"customerMsdsDb"
},
{
    columnId:"weightPercent"
}
];


with ( milonic=new menuname("rightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI("text=" );
}

with(milonic=new menuname("requestId")){
	style=submenuStyle;
	itemheight=17;
	//has to have at least one menu item, this is a place holder.
	//we will dynamically add items in javascript
	aI("text=test;");
}

drawMenus();

function doNothing() {

}

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="newinit();">
<tcmis:form action="/catalogresults.do" onsubmit="return submitFrameOnlyOnce();">

<script language="JavaScript" type="text/javascript">
<!--

var setGrandfatheredMaterialPermission = false;
<tcmis:facilityPermission indicator="true" userGroupId="GrandfatherMaterial" companyId="${personnelBean.companyId}" facilityId="${param.facilityId}">
    setGrandfatheredMaterialPermission = true;
</tcmis:facilityPermission>

<c:set var="parCount" value="0"/>

<c:set var="dataCount" value='${0}'/>
var map = null;
var lineMap = new Array();
var lineIdMap = new Array();
var lineMap3 = new Array();

<c:if test="${!empty msdsCatalogBeanCollection}" >
 <c:if test="${!empty msdsCatalogBeanCollection}" >
  var jsonMainData = new Array();
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${msdsCatalogBeanCollection}" varStatus="status">
	  <c:if test="${status.index != 0 }">,</c:if>
	<c:set var="curPar" value="${status.current.customerMixtureNumber}"/>
	<c:if test="${!(curPar eq prePar)}">
		<c:set var="parCount" value="${parCount+1}"/>
	</c:if>
    <c:set var="tmpMixRatioUnit" value="${p.mixRatioSizeUnit}"/>
    <c:if test="${p.mixRatioSizeUnit == '%(v/v)'}">
        <c:set var="tmpMixRatioUnit"><fmt:message key="report.label.percentByVolume"/></c:set>
    </c:if>
     <c:if test="${p.mixRatioSizeUnit == '%(w/w)'}">
        <c:set var="tmpMixRatioUnit"><fmt:message key="report.label.percentByWeight"/></c:set>
    </c:if>      

    { id:${status.index +1},
        data:[
                '${p.customerMixtureNumber}',
                '<tcmis:jsReplace value="${p.mixtureDesc}" processMultiLines="true"/>',
                '${p.approvalCode}',
                '${p.customerMsdsNumber}',
                '<tcmis:jsReplace value="${p.materialDesc}" processMultiLines="true"/>',
                '<fmt:formatNumber value="${p.mixRatioAmount}" pattern="${totalcurrencyformat}"/> ${tmpMixRatioUnit}',
                '${p.materialId}',
			    '<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>',
				'<tcmis:jsReplace value="${p.tradeName}" processMultiLines="true"/>',
                '${p.msdsOnLine}',
                '${p.customerMsdsDb}'
        ]}
		<c:set var="numberOfKit" value="${status.current.numberOfKitPerMsds}"/>
		<c:if test="${!(numberOfKit eq -1)}">
			<c:set var="dataCount" value='${dataCount+1}'/>
		</c:if>
		<c:set var="prePar" value="${status.current.customerMixtureNumber}"/>

     </c:forEach>
  ]};

  </c:if>

</c:if>

var altCatalogFacility = new Array(
<c:forEach var="catalogFacilityBean" items="${catalogFacilityCollection}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {
		catalogCompanyId:'<tcmis:jsReplace value="${status2.current.catalogCompanyId}"/>',
		catalogId:'<tcmis:jsReplace value="${status2.current.catalogId}"/>',
		catalogDesc:'<tcmis:jsReplace value="${status2.current.catalogDesc}"/>',
		catalogAddAllowed:'<tcmis:jsReplace value="${status2.current.catalogAddAllowed}"/>'
	}
</c:forEach>
);

// -->
</script>

<%-- determining rowspan --%>
<c:set var="itemCount" value='0'/>
<c:forEach var="p" items="${msdsCatalogBeanCollection}" varStatus="status">
	<c:set var="numberOfKit" value="${status.current.numberOfKitPerMsds}"/>
	<script language="JavaScript" type="text/javascript">
	<!--
	   lineMap[${status.index}] = ${numberOfKit} ;
		<c:if test="${!(numberOfKit eq -1)}">
	      <c:set var="itemCount" value='${itemCount+1}'/>
			map = new Array();
		</c:if>
	   lineMap3[${status.index}] = ${itemCount} % 2;
	   map[map.length] =  ${status.index} ; lineIdMap[${status.index}] = map;
	// -->
	</script>
</c:forEach>


<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<%--
<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >
--%>
 <script type="text/javascript">
 <!--
  showUpdateLinks = false;  
 //-->
 </script>
<%--</tcmis:facilityPermission>--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
${tcmisError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

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

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<%--NEW - there is no results table anymore--%>  
<div id="msdsCatalogScreenSearchBean" style="width:100%;%;height:400px;" style="display: none;"></div>


<c:if test="${msdsCatalogBeanCollection != null}" >
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty msdsCatalogBeanCollection}" >
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
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
<input name="applicationId" id="applicationId" value="${param.applicationId}" type="hidden"/>
<input name="companyId" id="companyId" value="${personnelBean.companyId}" type="hidden"/>    
<input name="showMixture" id="showMixture" value="${showMixture}" type="hidden"/>    
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<!--
<tcmis:saveRequestParameter/>
-->
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html>
