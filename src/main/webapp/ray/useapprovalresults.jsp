<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/ray/useapprovalresults.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="useapprovalstatus.title"/>
</title>

<script language="JavaScript" type="text/javascript"><!--


//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    recordFound:"<fmt:message key="label.recordFound"/>",
    searchDuration:"<fmt:message key="label.searchDuration"/>",
    pleaseselect:"<fmt:message key="label.pleaseselect"/>",
    turnoverrideon:"<fmt:message key="label.turnoverrideon"/>",
    turnoverrideoff:"<fmt:message key="label.turnoverrideoff"/>",
    willactivatepartsapproved:"<fmt:message key="label.willactivatepartsapproved"/>",
    makeselectedworkareamanaged:"<fmt:message key="label.makeselectedworkareamanaged"/>",
    willmakepartsactivatemsg:"<fmt:message key="label.willmakepartsactivatemsg"/>",
    limit1quantity:"<fmt:message key="label.limit1quantity"/>",
    limit1days:"<fmt:message key="label.limit1days"/>",
    limit1msg:"<fmt:message key="label.limit1msg"/>",
    limit2quantity:"<fmt:message key="label.limit2quantity"/>",
    limit2days:"<fmt:message key="label.limit2days"/>",
    limit2msg:"<fmt:message key="label.limit2msg"/>",
    orderquantity:"<fmt:message key="label.orderquantity"/>",
    orderqtytype:"<fmt:message key="label.orderqtytype"/>",
    estimatedcovereage:"<fmt:message key="useapprovalstatus.label.estimatedcovereage"/>",
    minutes:"<fmt:message key="label.minutes"/>",
    seconds:"<fmt:message key="label.seconds"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};

<c:set var="isItmanagedWorkArea"  value= '${managedWorkArea}'/>
<c:set var="selectedFacilityId" value='${param.facilityId}'/>

<c:forEach var="facAppUserGrpOvBean" items="${facAppUserGrpOvBeanColl}" varStatus="status">
 <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
  <c:choose>
   <c:when test="${currentFacilityId == selectedFacilityId}" >
    <c:set var="facilityDockObjBeanJspCollection" value='${status.current.facDockVar}'/>
   </c:when>
  </c:choose>
</c:forEach>


var dockLocationId = new Array(
         <c:forEach var="dBean" items="${facilityDockObjBeanJspCollection}" varStatus="status1">
		  <c:if test="${status1.index > 0}">,</c:if>
		  {value:"${status1.current.dockLocationId}",text:'<tcmis:jsReplace value="${status1.current.dockDesc}"/>'}
		</c:forEach>
);



var mwOrderQuantityRule = new Array(
          {value:"",text:'Select'},
          <c:forEach var="vvUseApprovalOrderQtyRuleBean" items="${vvUseApprovalOrderQtyRuleBeanColl}" varStatus="status2">
		  <c:if test="${status2.index > 0}">,</c:if>
		  {value:"${status2.current.orderQuantityRule}",text:'<tcmis:jsReplace value="${status2.current.orderQuantityRuleDesc}"/>'}
		</c:forEach>
);		

var dockdparr = [
   <c:forEach var="facilityDockObjBean" items="${facilityDockObjBeanJspCollection}" varStatus="facdockstatus">
    	  <c:if test="${ facdockstatus.index !=0 }">,</c:if>
        	 	  {  id: '${facdockstatus.current.dockLocationId}',
        	 	   name: '<tcmis:jsReplace value="${facdockstatus.current.dockDesc}"/>',
        	 	   coll: [ <c:forEach var="deliveryPointObjBean" items="${facdockstatus.current.deliveryPointVar}" varStatus="dpstatus">
         				   <c:if test="${ dpstatus.index !=0 }">,</c:if>
	    	    	 	   {
	    	    			id:'${dpstatus.current.deliveryPoint}',
	    	    			name:'<tcmis:jsReplace value="${dpstatus.current.deliveryPointDesc}"/>'
	    	    		   }
         		   		   </c:forEach>
        	 	 		  ]
        		   }
     </c:forEach>
          	    
   ];


var dockDeliveryPoint = new Array();

<c:forEach var="bean" items="${useApprovalStatusViewBeanCollection}" varStatus="statusb">
	dockDeliveryPoint[${statusb.index + 1}] = buildNewDp('${bean.dockLocationId}');
</c:forEach>


var config = [
{columnId:"okPermission"}, 
{columnId:"mwLimitQuantityPeriod1Permission"},{columnId:"mwDaysPeriod1Permission"}, 
{columnId:"mwLimitQuantityPeriod2Permission"},{columnId:"mwDaysPeriod2Permission"}, 
{columnId:"mwProcessDescPermission"},{columnId:"mwOrderQuantityPermission"}, 
{columnId:"mwEstimateOrderQuantityPrdPermission"},{columnId:"mwOrderQuantityRulePermission"},
{columnId:"customerDeliverToPermission"},{columnId:"dockLocationIdPermission"},
{columnId:"deliveryPointBarcodePermission"},{columnId:"dockDeliveryPointPermission"},  
{columnId:"catalogId",columnName:'<fmt:message key="label.catalog"/>',width:6}, 
{columnId:"facPartNo",columnName:'<fmt:message key="label.partnumber"/>',width:8},
{columnId:"partDescription",columnName:'<fmt:message key="label.partdescription"/>',width:20,tooltip:"Y"},
{columnId:"approvalStatus",columnName:'<fmt:message key="useapprovalstatus.label.approvedstatus"/>',width:6},
{columnId:"limitQuantityPeriod1Display",columnName:'<fmt:message key="useapprovalstatus.label.limit1"/>',width:9},
{columnId:"limitQuantityPeriod2Display",columnName:'<fmt:message key="useapprovalstatus.label.limit2"/>',width:9},
{columnId:"processDesc",columnName:'<fmt:message key="useapprovalstatus.label.processdesc"/>',width:12,tooltip:"Y"},
{columnId:"ok",columnName:'<fmt:message key="label.active"/>',align:'center',type:'hchstatus',onChange:useApprovalChanged,width:3},
{columnId:"mwLimitQuantityPeriod1",columnName:'<fmt:message key="useapprovalstatus.label.limit1"/>',attachHeader:'<fmt:message key="label.every"/>',type:'hed',width:3},
{columnId:"mwDaysPeriod1",columnName:'#cspan',attachHeader:'<fmt:message key="label.days"/>',type:'hed',onChange:useApprovalChanged,width:3},
{columnId:"mwLimitQuantityPeriod2",columnName:'<fmt:message key="useapprovalstatus.label.limit2"/>',attachHeader:'<fmt:message key="label.every"/>',type:'hed',onChange:useApprovalChanged,width:3},
{columnId:"mwDaysPeriod2",columnName:'#cspan',attachHeader:'<fmt:message key="label.days"/>',type:'hed',onChange:useApprovalChanged,width:3},
{columnId:"mwProcessDesc",columnName:'<fmt:message key="useapprovalstatus.label.processdesc"/>',type:"txt",onChange:useApprovalChanged,width:15,tooltip:"Y"},
{columnId:"mwOrderQuantity",columnName:'<fmt:message key="label.orderqty"/>',type:'hed',onChange:useApprovalChanged,width:5},
{columnId:"packaging",columnName:'<fmt:message key="useapprovalstatus.label.packaging"/>',width:15,tooltip:"Y"},
{columnId:"mwEstimateOrderQuantityPrd",columnName:'<fmt:message key="useapprovalstatus.label.estimatedcovereage"/> (<fmt:message key="label.days"/>)',type:'hed',onChange:useApprovalChanged,width:6},
{columnId:"mwOrderQuantityRule",columnName:'<fmt:message key="label.orderqtytype"/>',type:'hcoro',onChange:useApprovalChanged,width:7},
{columnId:"customerDeliverTo",columnName:'<fmt:message key="useapprovalstatus.label.customershiptocode"/>',type:'hed',onChange:automatedFeedChanged,width:8},
{columnId:"dockLocationId",columnName:'<fmt:message key="useapprovalstatus.label.tcmisdock"/>',onChange:docLocChanged,type:'hcoro',onChange:automatedFeedChanged,width:12},
{columnId:"deliveryPointBarcode",columnName:'<fmt:message key="useapprovalstatus.label.customerdeliverto"/>',type:'hed',onChange:automatedFeedChanged,width:8},
{columnId:"dockDeliveryPoint",columnName:'<fmt:message key="useapprovalstatus.label.tcmisdeliverto"/>',type:'hcoro',onChange:automatedFeedChanged,width:25},
{columnId:"barcodeRequesterName",columnName:'<fmt:message key="useapprovalstatus.label.requestor"/>',attachHeader:'<fmt:message key="label.value"/>',tooltip:"Y",onChange:automatedFeedChanged,width:8},
{columnId:"barcodeRequesterLookup",columnName:'#cspan',attachHeader:'<fmt:message key="label.lookup"/>',width:6},
{columnId:"barcodeRequester"},
{columnId:"limitQuantityPeriod1"},
{columnId:"limitQuantityPeriod2"},
{columnId:"partGroupNo"},
{columnId:"facilityId"},
{columnId:"application"},
{columnId:"companyId"},
{columnId:"catalogCompanyId"},
{columnId:"userGroupId"},
{columnId:"mwApprovalId"},
{columnId:"automatedFeedChanged"},
{columnId:"useApprovalChanged"}
];

// 	
--></script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/useapprovalresults.do" onsubmit="return submitFrameOnlyOnce();">
<tcmis:facilityPermission indicator="true" userGroupId="useapprovalupdate" facilityId="${selectedFacilityId}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:facilityPermission>
 <!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
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

<div id="useApprovalStatusViewBean" style="width:100%;height:500px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${useApprovalStatusViewBeanCollection != null}">
<script type="text/javascript">
<!--
<c:if test="${!empty useApprovalStatusViewBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${useApprovalStatusViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="readonly" value='N'/> 
<c:set var="limitQuantityPeriod1" value='${bean.limitQuantityPeriod1}'/>
<c:set var="limitQuantityPeriod2" value='${bean.limitQuantityPeriod2}'/>
<c:set var="mwLimitQuantityPeriod1" value='${bean.mwLimitQuantityPeriod1}'/>
<c:set var="mwLimitQuantityPeriod2" value='${bean.mwLimitQuantityPeriod2}'/>
<c:set var="mwEstimateOrderQuantityPrd" value='${bean.mwEstimateOrderQuantityPrd}'/>
<c:set var="mwApprovalId" value='${bean.mwApprovalId}'/>
<c:set var="mwDaysPeriod1" value='${bean.mwDaysPeriod1}'/>
<c:set var="mwDaysPeriod2" value='${bean.mwDaysPeriod2}'/>
<c:set var="mwLimitQuantityPeriod1" value='${bean.mwLimitQuantityPeriod1}'/>
<c:set var="mwOrderQuantityRule" value='${bean.mwOrderQuantityRule}'/>
<c:set var="selectedDock" value='${bean.dockLocationId}'/>

<c:set var="barcodeRequesterDisplay">
	<input class="lookupBtn" type="button" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" onclick="getBarcodeRequester(${dataCount+1})"/>
	<input class="smallBtns" type="button" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearBarcodeRequester(${dataCount+1})"/> 
</c:set>
<tcmis:jsReplace var="barcodeRequesterDisplay" value="${barcodeRequesterDisplay}"  processMultiLines="true" />
<tcmis:facilityPermission indicator="true" userGroupId="useapprovalupdate" facilityId="${selectedFacilityId}">

 <c:set var="readonly" value='Y'/> 

</tcmis:facilityPermission>
{id:${status.index +1},
 data:['${readonly}',
  'Y','Y','Y','Y','Y','Y','Y','Y',
  '${readonly}',
  '${readonly}',
  '${readonly}',
  '${readonly}',
  '${bean.catalogId}',
  '<tcmis:jsReplace value="${bean.facPartNo}"/>',
  '<tcmis:jsReplace value="${bean.partDescription}" processMultiLines="true" />',
  '${bean.approvalStatus}',
  <c:choose>
   <c:when test="${empty limitQuantityPeriod1}">
    '',
   </c:when>
   <c:otherwise>
    '${bean.limitQuantityPeriod1} <fmt:message key="useapprovalstatus.label.every"/> ${bean.daysPeriod1} <fmt:message key="label.days"/>',
   </c:otherwise>
  </c:choose>
  <c:choose>
   <c:when test="${empty limitQuantityPeriod2}">
    '',
   </c:when>
   <c:otherwise>
    '${bean.limitQuantityPeriod2} <fmt:message key="useapprovalstatus.label.every"/> ${bean.daysPeriod2} <fmt:message key="label.days"/>',
   </c:otherwise>
  </c:choose>
  '<tcmis:jsReplace value="${bean.processDesc}" processMultiLines="true"/>',
  <c:choose>
   <c:when test="${empty mwApprovalId}">
    false,
   </c:when>
   <c:otherwise>
    true,
   </c:otherwise>
  </c:choose>
  '${bean.mwLimitQuantityPeriod1}',
  <c:choose>
   <c:when test="${empty mwDaysPeriod1}">
    '7',
   </c:when>
   <c:otherwise>
    '${bean.mwDaysPeriod1}',
   </c:otherwise>
  </c:choose>
  '${bean.mwLimitQuantityPeriod2}',
  <c:choose>
   <c:when test="${empty mwDaysPeriod2}">
    '30',
   </c:when>
   <c:otherwise>
    '${bean.mwDaysPeriod2}',
   </c:otherwise>
  </c:choose>
  '<tcmis:jsReplace value="${bean.mwProcessDesc}" processMultiLines="true"/>',
  '${bean.mwOrderQuantity}',
  '<tcmis:jsReplace value="${bean.packaging}" processMultiLines="true"/>',
  '${bean.mwEstimateOrderQuantityPrd}',
   <c:choose>
   <c:when test="${empty mwOrderQuantityRule}">
    '',
   </c:when>
   <c:otherwise>
    '${bean.mwOrderQuantityRule}',
   </c:otherwise>
  </c:choose>
  '${bean.customerDeliverTo}',
  '${bean.dockLocationId}',
  '${bean.deliveryPointBarcode}',
  '${bean.dockDeliveryPoint}',
  '${bean.barcodeRequesterName}',
  '${barcodeRequesterDisplay}',
  '${bean.barcodeRequester}',
  '${bean.limitQuantityPeriod1}',
  '${bean.limitQuantityPeriod2}',
  '${bean.partGroupNo}',
  '${bean.facilityId}',
  '${bean.application}',
  '${bean.companyId}',
  '${bean.catalogCompanyId}',
  '${bean.userGroupId}',
  '${bean.mwApprovalId}',
  '',
  ''
   ]}
  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
 //-->
</script>
</c:if>
<!-- If the collection is empty say no data found -->
<c:if test="${empty useApprovalStatusViewBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>



<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="facilityId" id="facilityId" value="<c:out value="${param.facilityId}"/>">
<input type="hidden" name="application" id="application" value="<c:out value="${param.application}"/>">
<input type="hidden" name="managedWorkArea" id="managedWorkArea" value="${managedWorkArea}"/>
<input type="hidden" name="managedUseApproval" id="managedUseApproval" value=""/>
<input type="hidden" name="userGroupId" id="userGroupId" value="<c:out value="${param.userGroupId}"/>">
<input type="hidden" name="searchText" id="searchText" value="<c:out value="${param.searchText}"/>">
<input type="hidden" name="showApprovedOnly" id="showApprovedOnly" value="Y">
<input type="hidden" name="sortBy" id="sortBy" value="FAC_PART_NO">
<input type="hidden" name="uAction" id="uAction" value="">
<input type="hidden" name="updateAllRows" id="updateAllRows" value=""/>
<input type="hidden" name="submitUpdate" id="submitUpdate" value=""/>
</div>

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html>