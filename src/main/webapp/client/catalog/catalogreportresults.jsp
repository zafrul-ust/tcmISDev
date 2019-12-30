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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catalogreportresults.js"></script>
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<c:set var="module">
	 <tcmis:module/>
</c:set>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();

messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
partno:"<fmt:message key="label.partno"/>",
partinv:"<fmt:message key="label.partinv"/>",
reorderpoint:"<fmt:message key="label.reorderpoint"/>",
stockinglevel:"<fmt:message key="label.stockinglevel"/>",
iteminv:"<fmt:message key="label.iteminv"/>",
spec:"<fmt:message key="label.specification"/>",
cartcomment:"<fmt:message key="label.cartcomment"/>",
approvedworkareas:"<fmt:message key="approvedworkareas.label.title"/>",
workareacomments:"<fmt:message key="workareacomments.label.title"/>",
partcomments:"<fmt:message key="label.partcomments"/>",
testdefinition:"<fmt:message key="label.testdefinition"/>",
baselinereset:"<fmt:message key="label.baselinereset"/>",
itemid:"<fmt:message key="label.itemid"/>",
inventory:"<fmt:message key="label.inventory"/>",
mfglit:"<fmt:message key="label.mfglit"/>",
image:"<fmt:message key="label.image"/>",
viewmsds:"<fmt:message key="label.viewmsds"/>",
addtocart:"<fmt:message key="label.addtocart"/>",
baselineexp:"<fmt:message key="label.baselineexp"/>",
catalog:"<fmt:message key="label.catalog"/>",
part:"<fmt:message key="label.part"/>",
description:"<fmt:message key="label.description"/>",
type:"<fmt:message key="label.type"/>",
price:"<fmt:message key="label.price"/>",
partuom:"<fmt:message key="label.partuom"/>",
qtyOfUomPerItem:"<fmt:message key="catalog.label.qtyofuomperitem"/>",
status:"<fmt:message key="label.status"/>",
shelflife:"<fmt:message key="catalog.label.shelflife"/>",
item:"<fmt:message key="label.item"/>",
componentdescription:"<fmt:message key="inventory.label.componentdescription"/>",
packaging:"<fmt:message key="inventory.label.packaging"/>",
grade:"<fmt:message key="label.grade"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
mfgpartno:"<fmt:message key="label.mfgpartno"/>",
comments:"<fmt:message key="label.comments"/>",
leadtimeplotpart1:"<fmt:message key="label.leadtimeplotpart1"/>",
leadtimeplotpart2:"<fmt:message key="label.leadtimeplotpart2"/>",
qualitysummary:"<fmt:message key="label.qualitysummary"/>",
baseline:"<fmt:message key="label.baseline"/>",
modifyQpl:"<fmt:message key="label.modifyqpl"/>",
newPart:"<fmt:message key="label.newpart"/>",
newPartFromExistingPart:"<fmt:message key="label.newpartfromcurrentpart"/>",
waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>",	
directedCharge:"<fmt:message key="label.directedCharge"/>",
invalidChargeNumbers:"<fmt:message key="label.invalidchargenumbers"/>",
invalidChargeNumberAddTolist:"<fmt:message key="label.invalidchargenumberaddtolist"/>",
editPartDirectedCharge:"<fmt:message key="label.editpartdirectedcharge"/>",
view:"<fmt:message key="label.view"/>",
kitMsds:"<fmt:message key="label.kitmsds"/>",
days:"<fmt:message key="label.days"/>",
newWorkAreaApproval:"<fmt:message key="label.newworkareaapproval"/>",
materialCategory:"<fmt:message key="label.materialCategory"/>",
materialSubcategory:"<fmt:message key="label.materialSubcategory"/>"
};

var newMsdsViewer = false;

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>

<c:set var="facilityId" value="${param.facilityId}"/>
var config = [
{ 
  columnId:"catalogId"
},
{
  columnId:"catalogDesc",
  columnName:'<fmt:message key="label.catalog"/>',
  width:8
},
{ 
  columnId:"catPartNo",
  columnName:'<fmt:message key="label.part"/>',
  width:10
},
{ 
  columnId:"partDescription",
  columnName:'<fmt:message key="label.description"/>',
  tooltip:"Y",
  width:15
},
{ 
  columnId:"specList",
  columnName:'<fmt:message key="label.spec"/>',
  width:8
},
{
  columnId:"materialCategory",
  columnName:'<fmt:message key="label.materialcategory"/>',
  width:15
},
{
  columnId:"materialSubcategory",
  columnName:'<fmt:message key="label.materialsubcategory"/>',
  width:15
},
<c:if test="${facilityId == 'Palmdale'}">
{ 
  columnId:"otr",
  columnName:'<fmt:message key="label.otr"/>',
  width:3
},
</c:if>
{ 
  columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width:8
},
{ 
  columnId:"materialId",
  columnName:'<fmt:message key="label.materialid"/>',
  width:8
},
{ 
  columnId:"materialDesc",
  columnName:'<fmt:message key="inventory.label.componentdescription"/>',
  tooltip:"Y",
  width:15
},
{ 
  columnId:"grade",
  columnName:'<fmt:message key="label.grade"/>',
  width:10
},
{ 
  columnId:"packaging",
  columnName:'<fmt:message key="inventory.label.packaging"/>',
  tooltip:"Y",
  width:15
},
{ 
  columnId:"mfgDesc",
  columnName:'<fmt:message key="label.manufacturer"/>',
  tooltip:"Y",
  width:15
},
{ 
  columnId:"shelfLifeDisplay",
  columnName:'<fmt:message key="catalog.label.shelflife"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"mfgPartNo",
  columnName:'<fmt:message key="label.mfgpartno"/>',
  width:8
},
{ 
  columnId:"approvalStatus"
},
{ 
  columnId:"stockingMethod"
},
{ 
  columnId:"finalPriceDisplay"
},
{ 
  columnId:"unitOfSale"
},
{ 
  columnId:"qtyOfUomPerItem"
},
{ 
  columnId:"catalogCompanyId"
},
{ 
  columnId:"partGroupNo"
},
{ 
  columnId:"inventoryGroup"
},
{ 
  columnId:"applicationPermission"
},
{ 
  columnId:"minPriceStartDate"
},
{ 
  columnId:"maxPriceStartDate"
},
{ 
  columnId:"minPriceEndDate"
},
{ 
  columnId:"maxPriceEndDate"
},
{ 
  columnId:"inventoryGroupName"
},
{ 
  columnId:"finalPrice"
},
{ 
  columnId:"medianSupplyLeadTime"
},
{ 
  columnId:"currencyId"
},
{ 
  columnId:"comments"
},
{ 
  columnId:"orderQuantity"
},
{ 
  columnId:"orderQuantityRule"
},
{ 
  columnId:"minBuy"
},
{ 
  columnId:"unitPrice"
},
{ 
  columnId:"availableQtyForFee"
},
{ 
  columnId:"serviceFeeRow"
},
{ 
  columnId:"medianMrLeadTime"
},
{ 
  columnId:"unitConversionOption"
},
{ 
  columnId:"itemType"
},
{ 
  columnId:"companyId"
},
{ 
  columnId:"incomingTesting"
}
];


with(milonic=new menuname("addToCartMenu")){
 top="offset=2"
 style=submenuStyle;
 itemheight=17;
 aI("text=<fmt:message key="label.partno"/>;url=javascript:doNothing();");
 aI("text=<fmt:message key="label.partno"/>;url=javascript:doNothing();");
 aI("text=<fmt:message key="label.partno"/>;url=javascript:doNothing();");
 }
 
 with(milonic=new menuname("comments")){
 style=submenuStyle;
 itemheight=17;
 aI("text=<fmt:message key="label.comments"/>;url=javascript:doNothing();");
 aI("text=<fmt:message key="label.comments"/>;url=javascript:doNothing();");
}


with(milonic=new menuname("addToCartMenu3")){
 top="offset=2"
  style = contextStyle;
  margin=3
 aI( "text="+messagesData.viewmsds+";url=javascript:viewMSDS();");
}



drawMenus();


var gridConfig = {
		divName:'prCatalogScreenSearchBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,
		submitDefault:false,					 // this page has rowSpan > 1 or not.
		noSmartRender:false,
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:rightClickRow // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)		 
	};


// -->
</script>

</head>

<body bgcolor="#ffffff" onload="newinit();" onunload="closeAllchildren();" >
<tcmis:form action="/catalogreportresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:set var="catalogAddPermission" value="N"/>
<%--todo don't release this to production until ready
<tcmis:facilityPermission indicator="true" userGroupId="CreateNewChemical" companyId="${personnelBean.companyId}" facilityId="${param.facilityId}">
	<c:set var="catalogAddPermission" value="Y"/>
</tcmis:facilityPermission>
--%>

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

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${empty prCatalogScreenSearchBeanCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
 </table>
</c:if>

<div id="prCatalogScreenSearchBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:if test="${!empty prCatalogScreenSearchBeanCollection}">
<script type="text/javascript">
<!--
	<c:set var="dataCount" value='${0}'/>
      
    var lineMap3 = new Array();
    var lineMap = new Array();
    var map = new Array();
       
    <c:set var="prePart" value=""/>
   <c:set var="prePos" value="-1"/>
   <c:set var="idcount" value="-1"/>
    <c:forEach var="bean" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">
    <c:set var="curPart" value="${status.current.catPartNo}${status.current.catalogId}${status.current.catalogCompanyId}${status.current.partGroupNo}"/>
     <c:if test="${ curPart != prePart }">
	<c:if test="${! empty prePart}">
		lineMap[${prePos}] = map.length;
	</c:if>
	<c:set var="idcount" value="${idcount+1}"/>
	<c:set var="prePos" value="${status.index}"/>
	map = new Array();
  </c:if>
 lineMap3[${status.index}] = ${idcount%2} ;
 map[map.length] = ${status.index} ;
 <c:set var="prePart" value='${curPart}'/>
</c:forEach>
<c:if test="${! empty prePart}">
lineMap[${prePos}] = map.length;
</c:if>
    	
var jsonMainData = new Array();
jsonMainData = {
  rows:[
      <c:forEach var="p" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">

	  <c:if test="${status.index != 0 }">,</c:if>
	
	  <fmt:formatDate var="fmtDate1" value="${status.current.minPriceStartDate}" pattern="${dateFormatPattern}"/>
	  <fmt:formatDate var="fmtDate2" value="${status.current.maxPriceStartDate}" pattern="${dateFormatPattern}"/>
	  <fmt:formatDate var="fmtDate3" value="${status.current.minPriceEndDate}" pattern="${dateFormatPattern}"/>
	  <fmt:formatDate var="fmtDate4" value="${status.current.maxPriceEndDate}" pattern="${dateFormatPattern}"/>
	  
	  <c:set var="finalPrice" value='' />
	               <c:set var="finalUnitPrice" value='' />
						<c:set var="minCatalogPrice" value='${status.current.minCatalogPrice}' />
						<c:set var="maxCatalogPrice" value='${status.current.maxCatalogPrice}' />
						<c:set var="minUnitPrice" value='${status.current.minUnitPrice}' />
						<c:set var="maxUnitPrice" value='${status.current.maxUnitPrice}' />
							<c:choose>
									<c:when test="${!empty minCatalogPrice && !empty maxCatalogPrice}">
										<c:set var="finalPrice" value='${maxCatalogPrice}' />
									</c:when>
									<c:otherwise>
										<c:if test="${!empty minCatalogPrice}">
											<c:set var="finalPrice" value='${minCatalogPrice}' />
										</c:if>
										<c:if test="${!empty maxCatalogPrice}">
											<c:set var="finalPrice" value='${maxCatalogPrice}' />
										</c:if>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${!empty minUnitPrice && !empty maxUnitPrice}">
										<c:set var="finalUnitPrice" value='${maxUnitPrice}' />
									</c:when>
									<c:otherwise>
										<c:if test="${!empty minUnitPrice}">
											<c:set var="finalUnitPrice" value='${minUnitPrice}' />
										</c:if>
										<c:if test="${!empty maxUnitPrice}">
											<c:set var="finalUnitPrice" value='${maxUnitPrice}' />
										</c:if>
									</c:otherwise>
								</c:choose>
								<c:set var="finalPrice" value='${minCatalogPrice}' />
								<c:set var="finalUnitPrice" value='${minUnitPrice}' />
						
	<c:set var="finalPriceDisplay" value="" />
   	<c:if test="${!empty finalPrice}">
	 <fmt:formatNumber var="finalPriceDisplay" maxFractionDigits="4" minFractionDigits="4">${finalPrice}</fmt:formatNumber>
	 <c:set var="finalPriceDisplay" value="${finalPriceDisplay} ${status.current.currencyId}" />
	</c:if>


		<c:set var="displayTemp" value='${status.current.displayTemp}' />
		<c:set var="shelfLifeDays" value='${status.current.shelfLifeDays}' />
		<c:set var="shelfLifeDisplay" value="" />
			<c:choose>
					<c:when test="${empty displayTemp || displayTemp == ' '}">
 							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${shelfLifeDays != '-1'}">
										<c:set var="shelfLifeBasis" value='${status.current.shelfLifeBasis}' />
										<c:if test="${!empty shelfLifeBasis}">
											<c:set var="shelfLifeDisplay">
											      ${shelfLifeDays} <fmt:message key="label.days" />
											        ${shelfLifeBasis}
											      @ ${displayTemp}
										      </c:set>
										</c:if>
										<c:if test="${empty shelfLifeBasis}">
											<c:set var="shelfLifeDisplay">
											  ${shelfLifeDays} <fmt:message key="label.days" /> @ ${displayTemp}
											   </c:set>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:set var="shelfLifeDisplay">
										<fmt:message key="label.indefinite" /> @ ${displayTemp}
										 </c:set>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
			</c:choose>

		<tcmis:jsReplace var="shelfLifeDisplay" value="${shelfLifeDisplay}" processMultiLines="true"/>
		<tcmis:jsReplace var="componentDesc" value="${p.componentDesc}" processMultiLines="true"/>
	  

      <c:set var="applicationPermission" value="N"/>
	  <tcmis:applicationPermission indicator="true" userGroupId="GenerateOrders" facilityId="${param.facilityId}" application="${param.applicationId}">
	  	<c:set var="applicationPermission" value="Y"/>
      </tcmis:applicationPermission>

	  { id:${status.count},
        data:[
              '${p.catalogId}',
              '<tcmis:jsReplace value="${p.catalogDesc}" processMultiLines="true"/>',  
              '<tcmis:jsReplace value="${p.catPartNo}" processMultiLines="true"/>',
              '<tcmis:jsReplace value="${p.partDescription}" processMultiLines="true"/>',
              '<tcmis:jsReplace value="${p.specList}" processMultiLines="true"/>',
              '<tcmis:jsReplace value="${p.materialCategoryName}" processMultiLines="true"/>',
              '<tcmis:jsReplace value="${p.materialSubcategoryName}" processMultiLines="true"/>',
              <c:if test="${facilityId == 'Palmdale'}">
                <c:choose>
	              <c:when test="${p.hetUsageRecording == 'OTR'}">
                    'Y',
                  </c:when>
                  <c:otherwise>
                     '',
                  </c:otherwise>
                    </c:choose>
              </c:if>
              '${p.itemId}',
              '${p.materialId}',
              '${componentDesc}',
              '<tcmis:jsReplace value="${p.grade}" processMultiLines="true"/>',
              '<tcmis:jsReplace value="${p.componentPackaging}" processMultiLines="true"/>',
			  '<tcmis:jsReplace value="${p.manufacturerName}" processMultiLines="true"/>',
              '${shelfLifeDisplay}',
              '<tcmis:jsReplace value="${p.mfgPartNo}" processMultiLines="true"/>',
			  '${p.approvalStatus}',
			  '${p.stockingMethod}',
			  '${finalPriceDisplay}',
              '${p.unitOfSale}',
              '${p.qtyOfUomPerItem}',
	 		  '${p.catalogCompanyId}',
              '${p.partGroupNo}',
              '${p.inventoryGroup}',
              '${applicationPermission}',
              '${fmtDate1}',
              '${fmtDate2}',
              '${fmtDate3}',
              '${fmtDate4}',
              '<tcmis:jsReplace value="${p.inventoryGroupName}" processMultiLines="true"/>',
              '${finalPrice}',
              '${p.medianSupplyLeadTime}',
              '${p.currencyId}',
              '<tcmis:jsReplace value="${p.comments}" processMultiLines="true"/>',
			  '${p.orderQuantity}',
			  '${p.orderQuantityRule}',
			  '${p.minBuy}',
			  '${finalUnitPrice}',
			  '${p.availableQtyForFee}',
			  '${p.serviceFeeRow}',
			  '${p.medianMrLeadTime}',
			  '${p.unitConversionOption}',
			  '${p.itemType}',
			  '${p.companyId}',
			  '${p.incomingTesting}']}
			  
			  <c:set var="dataCount" value='${dataCount+1}'/>
			 </c:forEach>
		]};
	//-->
</script>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
<input name="applicationId" id="applicationId" value="${param.applicationId}" type="hidden"/>
<input name="catalogAddPermission" id="catalogAddPermission" value="${catalogAddPermission}" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="360"> 
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<tcmis:featureReleased feature="ShowQualitySummary" scope="ALL">
<script type="text/javascript">
  showQualitySummary = true;
</script>
</tcmis:featureReleased>

</tcmis:form>

</body>
</html:html>
