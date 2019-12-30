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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>

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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/client/catalog/materialrequest.js"></script>
<script type="text/javascript" src="/js/common/ordertracking/showmrapprovaldetail.js"></script>    

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>	
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
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
<script src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<title>
<fmt:message key="label.materialrequest"/>
</title>

</head>

<body bgcolor="#ffffff"  onload="myOnLoad('${param.action}');" onresize="resizeFrames()" onunload="closeAllchildren();">

<tcmis:form action="/materialrequest.do" onsubmit="return submitFrameOnlyOnce();">

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="homeCompanyLogin" value="${personnelBean.companyId == 'Radian'}"/>
homeCompanyLogin = ${homeCompanyLogin};

<%-- Define the right click menus --%>
<c:choose>
<c:when test="${homeCompanyLogin}">
with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
}
</c:when>
<c:otherwise>
with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=");
}
</c:otherwise>
</c:choose>

drawMenus();

var approverRequired = false;
var prRulesColl = new Array();
<c:forEach var="prRulesBean" items="${materialRequestData.prRulesColl}" varStatus="status">
	prRulesColl[${status.index}]={
		chargeType:'${prRulesBean.chargeType}',
		poRequired:'${prRulesBean.poRequired}',
	    prAccountRequired:'${prRulesBean.prAccountRequired}',
		chargeDisplay1:'${prRulesBean.chargeDisplay1}',
		chargeDisplay2:'${prRulesBean.chargeDisplay2}',
		chargeDisplay3:'${prRulesBean.chargeDisplay3}',
		chargeDisplay4:'${prRulesBean.chargeDisplay4}',
		chargeLabel1:'${prRulesBean.chargeLabel1}',
		chargeLabel2:'${prRulesBean.chargeLabel2}',
		chargeLabel3:'${prRulesBean.chargeLabel3}',
		chargeLabel4:'${prRulesBean.chargeLabel4}',
		chargeAllowNull1:'${prRulesBean.chargeAllowNull1}',
		chargeAllowNull2:'${prRulesBean.chargeAllowNull2}',
		chargeAllowNull3:'${prRulesBean.chargeAllowNull3}',
		chargeAllowNull4:'${prRulesBean.chargeAllowNull4}',
		<c:choose>
			<c:when test='${"ByFacility" eq prRulesBean.unitPriceRequired}'>
				// Display ByFacility, checking user permissions for 'editPODataOnMR' on companyId/facilityId '${personnelBean.companyId}/${prRulesBean.facilityId}'
		  		<c:set var="unitPriceRequired" value='Display'/>
			  	<tcmis:facilityPermission indicator="true" userGroupId="editPODataOnMR" companyId="${personnelBean.companyId}" facilityId="${prRulesBean.facilityId}">
				// Required ByFacility
					<c:set var="unitPriceRequired" value='Required'/>
			 	</tcmis:facilityPermission>
			</c:when>
			<c:otherwise>
			// Actual pr_rules value
			<c:set var="unitPriceRequired" value='${prRulesBean.unitPriceRequired}'/>
			</c:otherwise>
		</c:choose>
		unitPriceRequired:'${unitPriceRequired}',
		poSeqRequired:'${prRulesBean.poSeqRequired}',
		customerRequisition:'${prRulesBean.customerRequisition}',
		chargeAllowEdit1:'${prRulesBean.chargeAllowEdit1}',
		chargeAllowEdit2:'${prRulesBean.chargeAllowEdit2}',
		chargeAllowEdit3:'${prRulesBean.chargeAllowEdit3}',
		chargeAllowEdit4:'${prRulesBean.chargeAllowEdit4}',
        directedChargeTypeEditable:'${prRulesBean.directedChargeTypeEditable}',
        maxChargeNumberToDisplay:'${prRulesBean.maxChargeNumberToDisplay}',
        allocateByChargeId1:'${prRulesBean.allocateByChargeId1}',
        allocateByChargeId2:'${prRulesBean.allocateByChargeId2}',
        allocateByChargeId3:'${prRulesBean.allocateByChargeId3}',
        allocateByChargeId4:'${prRulesBean.allocateByChargeId4}'
    };
	<c:if test="${prRulesBean.approverRequired == 'y'}">approverRequired = true;</c:if>
</c:forEach>

var currencyColl = new Array();
<c:forEach var="tmpBean" items="${materialRequestData.currencyColl}" varStatus="status">
	currencyColl[${status.index}]={
		currencyId:'${tmpBean.currencyId}',
		currencyDescription:'${tmpBean.currencyDescription}',
	   exchangeRate:'${tmpBean.exchangeRate}'
	};
</c:forEach>

var deliveryTypeColl = new Array();
<c:forEach var="tmpBean" items="${materialRequestData.deliveryTypeColl}" varStatus="status">
	deliveryTypeColl[${status.index}]={
		deliveryType:'${tmpBean.deliveryType}',
		description:'${tmpBean.description}'
	};
</c:forEach>

var dockDeliveryPointColl = new Array();
var facAccountSysPoForDirectColl = new Array();
var facAccountSysPoForIndirectColl = new Array();
var prAccountColl = new Array();
var chargeNumberForDirectColl = new Array();
var chargeNumberForIndirectColl = new Array();
<c:forEach var="lineItemBean" items="${materialRequestData.lineItemColl}" varStatus="status">
	dockDeliveryPointColl['${lineItemBean.lineItem}']={
		dockColl:[
			<c:forEach var="tmpBean" items="${lineItemBean.dockDeliveryPointColl}" varStatus="status2">
			 	<c:if test="${status2.index != 0 }">,</c:if>
				{
				dockLocationId:'${tmpBean.dockLocationId}',
				dockDesc:'${tmpBean.dockDesc}',
				deliveryPointColl:[
					 <c:forEach var="tmpBean2" items="${tmpBean.deliveryPointColl}" varStatus="status3">
						<c:if test="${status3.index != 0 }">,</c:if>
						{
							deliveryPoint: '<tcmis:jsReplace value="${tmpBean2.deliveryPoint}"/>',
							deliveryPointDesc: '<tcmis:jsReplace value="${tmpBean2.deliveryPointDesc}"/>'
						}
					 </c:forEach>
				]
				}
			</c:forEach>
		]
	};

	facAccountSysPoForDirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.facAccountSysPoForDirectColl}" varStatus="status2">
			<c:if test="${status2.index != 0 }">,</c:if>
			{
				poNumber:'<tcmis:jsReplace value="${tmpBean.poNumber}"/>'
			}
		</c:forEach>
	];

	facAccountSysPoForIndirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.facAccountSysPoForIndirectColl}" varStatus="status2">
			 <c:if test="${status2.index != 0 }">,</c:if>
			 {
			 	poNumber:'<tcmis:jsReplace value="${tmpBean.poNumber}"/>'
			 }
		</c:forEach>
	];

	prAccountColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.prAccountColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				accountNumber:'${tmpBean.accountNumber}',
		   	accountNumber2:'${tmpBean.accountNumber2}',
				accountNumber3:'${tmpBean.accountNumber3}',
				accountNumber4:'${tmpBean.accountNumber4}',
				percentage:'${tmpBean.percentage}',
				chargeType:'${tmpBean.chargeType}'
			}
		</c:forEach>
	];

	chargeNumberForDirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.chargeNumberForDirectColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				chargeNumber1:'${tmpBean.chargeNumber1}',
		   	chargeNumber2:'${tmpBean.chargeNumber2}',
				chargeNumber3:'${tmpBean.chargeNumber3}',
				chargeNumber4:'${tmpBean.chargeNumber4}',
				percent:'${tmpBean.percent}'
			}
		</c:forEach>
	];

	chargeNumberForIndirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.chargeNumberForIndirectColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				chargeNumber1:'${tmpBean.chargeNumber1}',
		   	chargeNumber2:'${tmpBean.chargeNumber2}',
				chargeNumber3:'${tmpBean.chargeNumber3}',
				chargeNumber4:'${tmpBean.chargeNumber4}',
				percent:'${tmpBean.percent}'
			}
		</c:forEach>
	];

</c:forEach>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
lineitemdetail:"<fmt:message key="label.lineitemdetail"/>",
missingdeliverto:"<fmt:message key="label.missingdeliverto"/>",
missingqty:"<fmt:message key="label.missingqty"/>",
invalidqtypart1:"<fmt:message key="label.invalidqtypart1"/>",
invalidqtypart2:"<fmt:message key="label.invalidqtypart2"/>",
missingdeliverdate:"<fmt:message key="label.missingdeliverdate"/>",
missingpo:"<fmt:message key="label.missingpo"/>",
hqty:"<fmt:message key="label.qty"/>",
hcritical:"<fmt:message key="label.critical"/>",
hnotes:"<fmt:message key="label.notes"/>",
poline:"<fmt:message key="label.poline"/>",	
missingpoqty:"<fmt:message key="label.missingpoqty"/>",
missingpouom:"<fmt:message key="label.missingpouom"/>",
missingpounitprice:"<fmt:message key="label.missingpounitprice"/>",
missingcurrency:"<fmt:message key="label.missingcurrency"/>",
missingchargenumber:"<fmt:message key="label.missingchargenumber"/>",
missingpercent:"<fmt:message key="label.missingpercent"/>",
invalidpercent:"<fmt:message key="label.invalidpercent"/>",
unitpricedisplayerror:"<fmt:message key="label.unitpricedisplayerror"/>",
missingcustreq:"<fmt:message key="label.missingcustreq"/>",
materialonhandtitle:"<fmt:message key="label.materialonhandtitle"/>",
pickoption:"<fmt:message key="label.pickoption"/>",
lineapprovedby:"<fmt:message key="label.lineapprovedby"/>",
financeapproverinformation:"<fmt:message key="label.financeapproverinformation"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",	 
itemInteger:"<fmt:message key="error.item.integer"/>",
catalog:"<fmt:message key="label.catalog"/>",
submit:"<fmt:message key="button.submit"/>",
save:"<fmt:message key="label.save"/>",
deletemr:"<fmt:message key="label.delete"/>",
deleteline:"<fmt:message key="label.deleteline"/>",
approve:"<fmt:message key="label.approve"/>",
release:"<fmt:message key="label.release"/>",
approveline:"<fmt:message key="label.approveline"/>",
reject:"<fmt:message key="label.reject"/>",
rejectline:"<fmt:message key="label.rejectline"/>",
allocation:"<fmt:message key="ordertracking.label.mrallocation"/>",
requestcancel:"<fmt:message key="label.requestcancel"/>",
approvecancel:"<fmt:message key="label.approvecancel"/>",
approvecancelconfirmmsg:"<fmt:message key="label.approvecancelconfirmmsg"/>",
rejectcancelconfirmmsg:"<fmt:message key="label.rejectcancelconfirmmsg"/>",
rejectcancel:"<fmt:message key="label.rejectcancel"/>",
qtyinpolinetable:"<fmt:message key="label.qtyinpolinetable"/>",
approveqtychange:"<fmt:message key="label.approveqtychange"/>",
approveqtychangeconfirmmsg:"<fmt:message key="label.approveqtychangeconfirmmsg"/>",
approve:"<fmt:message key="label.approve"/>",
returntocart:"<fmt:message key="label.returntocart"/>",
printthispage:"<fmt:message key="label.printthispage"/>",
sendtofinancialapprover:"<fmt:message key="label.sendtofinancialapprover"/>",
sendtouseapprover:"<fmt:message key="label.sendtouseapprover"/>",
sendtoreleaser:"<fmt:message key="label.sendtoreleaser"/>",
information:"<fmt:message key="label.information"/>",		
changedeliverytypewarn:"<fmt:message key="label.changedeliverytypewarn"/>",
shipto:"<fmt:message key="label.shipto"/>",
comments:"<fmt:message key="label.comments"/>",
nocopy:"<fmt:message key="error.nocopy"/>",
mrtotalcostapprovaldetail:"<fmt:message key="label.mrtotalcostapprovaldetail"/>",
currentLineApprovalDetail:"<fmt:message key="label.currentlineapprovaldetail"/>",
allLinesApprovalDetail:"<fmt:message key="label.alllinesapprovaldetail"/>",
mrTotalCostApprovalDetail:"<fmt:message key="label.mrtotalcostapprovaldetail"/>",
selectone:"<fmt:message key="label.selectOne"/>",
transferFromProgram:"<fmt:message key="label.transferfromprogram"/>", 
confirmCancellation:"<fmt:message key="label.confirmcancellation"/>",
cancelMrConfirmMsg:"<fmt:message key="label.cancelmrconfirmmsg"/>",
missingOwnerSegmentId:"<fmt:message key="label.missingownersegmentid"/>",
mrallocation:"<fmt:message key="ordertracking.label.mrallocation"/>",
mrlineallocation:"<fmt:message key="label.mrlineallocation"/>",
qtylessthanissuedandpo:"<fmt:message key="label.qtylessthanissuedandpo"/>",
xExist:"<fmt:message key="error.xexist"/>",
orderName:"<fmt:message key="label.ordername"/>",
maxLength:"<fmt:message key="errors.maxlength"/>"

     <c:if test="${  empty financeApprover}">,
	<c:choose>
	<c:when test="${hasAdmin eq true}">
	approverlabel:"<fmt:message key="label.use.approvers"/>"  +":",
	firstline:"<fmt:message key="label.nolist.approvers"/>"
	</c:when>
	<c:otherwise>
	approverlabel:"<fmt:message key="label.line.pending.approvers"/>" +":",
	firstline:''
	</c:otherwise>
	</c:choose>
	</c:if>

	<c:if test="${financeApprover eq true}">,
	<c:choose>
	<c:when test="${hasAdmin eq true}">
	approverlabel:"<fmt:message key="label.use.approvers"/>"  +":",
	firstline:"<fmt:message key="label.nolist.approvers"/>"
	</c:when>
	<c:otherwise>
	approverlabel:"<fmt:message key="label.request.finance.approval"/>" +":",
	firstline:''
	</c:otherwise>
	</c:choose>
	</c:if>

};
// -->
 </script>

<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
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

<tcmis:userPagePermission indicator="true" pageId="newcatalog">
 <script type="text/javascript">
 <!--
  returnToCartOk = true;
 //-->
 </script>
</tcmis:userPagePermission>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
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
		<span id="submitSpan" style="display: none"></span>
		<span id="saveSpan" style="display: none"></span>
		<span id="deleteSpan" style="display: none"></span>
		<span id="deleteLineSpan" style="display: none"></span>
		<span id="approveSpan" style="display: none"></span>
        <span id="approveChargeDataSpan" style="display: none"></span>
		<span id="approveChargeDataLineSpan" style="display: none"></span>
        <span id="releaseSpan" style="display: none"></span>
		<span id="approveUseSpan" style="display: none"></span>
		<span id="approveUseLineSpan" style="display: none"></span>
		<span id="rejectSpan" style="display: none"></span>
		<span id="rejectLineSpan" style="display: none"></span>
		<span id="allocationSpan" style="display: none"></span>
		<span id="requestCancelSpan" style="display: none"></span>
		<span id="approveCancelSpan" style="display: none"></span>
		<span id="rejectCancelSpan" style="display: none"></span>
		<span id="approveQtyChangeSpan" style="display: none"></span>
		<span id="returnToCartSpan" style="display: none"></span>
		<span id="printScreenSpan" style="display: none"></span>
		<span id="mrTotalCostApprovalDetailSpan" style="display: none"></span>
		<c:if test="${materialRequestData.allowOneTimeShipTo == 'Y' && (materialRequestData.canEditMr == 'Y' || 'Y' == materialRequestData.orderMaintenanceHeaderPermission)}">&nbsp;| <a href="#" onclick="getNewShipTo(); return false;"><fmt:message key="newshiptoaddress.title"/></a></c:if>
		<span id="updateResultLink" style="display: none">

		</span>

		<br>
		<br>

		<fmt:formatDate var="formattedRequestDate" value="${materialRequestData.requestDate}" pattern="${dateFormatPattern}"/>
		<fieldset><legend class="optionTitle"><fmt:message key="label.materialrequest"/>:&nbsp;${materialRequestData.prNumber}(${materialRequestData.prStatusDesc})&nbsp;&nbsp;<fmt:message key="label.daterequested"/>: ${formattedRequestDate}&nbsp;&nbsp;<fmt:message key="workareacomments.label.by"/>: ${materialRequestData.requestorName}</legend>
		<table  width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100%"  class="optionTitleBoldCenter" colspan="4">
					<%--
					<fmt:formatDate var="formattedRequestDate" value="${materialRequestData.requestDate}" pattern="${dateFormatPattern}"/>
					<fmt:message key="label.materialrequest"/>:&nbsp;${materialRequestData.prNumber}(${materialRequestData.prStatus})&nbsp;&nbsp;<fmt:message key="label.daterequested"/>: ${formattedRequestDate}&nbsp;&nbsp;<fmt:message key="workareacomments.label.by"/>: ${materialRequestData.requestorName}
					--%>
				</td>
			</tr>
			<tr>
				<td width="25%"  class="optionTitleBoldRight">
					&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft">
					&nbsp;
				</td>
				<td width="25%"  class="optionTitleBoldRight">
					<fmt:message key="label.company"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft">
			    	${materialRequestData.companyId}
				</td>
				<td width="25%"  class="optionTitleBoldRight" nowrap>
					&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft" nowrap>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td width="25%"  class="optionTitleBoldRight">
					<fmt:message key="label.enduser"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft">
					<c:choose>
						<c:when test="${materialRequestData.canEditMr == 'Y' || 'Y' == materialRequestData.orderMaintenanceHeaderPermission}">
							<input name="endUser" id="endUser" type="text" class="inputBox" value="${materialRequestData.endUser}" size="30"/>
						</c:when>
						<c:otherwise>
							${materialRequestData.endUser}
							<input name="endUser" id="endUser" type="hidden" class="inputBox" value="${materialRequestData.endUser}" size="30"/>
						</c:otherwise>
					 </c:choose>
				</td>
				<td width="25%"  class="optionTitleBoldRight">
					<fmt:message key="label.accountsysid"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft">
			    	${materialRequestData.accountSysDesc}
				</td>
				<td width="25%"  class="optionTitleBoldRight" nowrap>
					<span id="shiptoLabel"><c:if test="${materialRequestData.updatable == 'Y'}"><fmt:message key="label.shipto"/>:&nbsp;</c:if></span>
				</td>
				<td width="25%"  class="optionTitleLeft" nowrap>
					<span id="addressLine1Span"><c:if test="${materialRequestData.updatable == 'Y'}">${materialRequestData.addressLine1}</c:if></span>
				</td>
			</tr>
			<tr>
				<td width="25%"  class="optionTitleBoldRight">
					<fmt:message key="searchmsds.label.department"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft">
					<c:choose>
						<c:when test="${materialRequestData.canEditMr == 'Y' || 'Y' == materialRequestData.orderMaintenanceHeaderPermission}">
							<input name="department" id="department" type="text" class="inputBox" value="${materialRequestData.department}" size="30"/>
						</c:when>
						<c:otherwise>
							${materialRequestData.department}
							<input name="department" id="department" type="hidden" class="inputBox" value="${materialRequestData.department}"/>
						</c:otherwise>
					 </c:choose>
				</td>
				<td width="25%"  class="optionTitleBoldRight">
					<fmt:message key="label.facility"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft">
			    	${materialRequestData.facilityName}
				</td>
				<td width="25%"  class="optionTitleBoldRight">
					&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft" nowrap>
					<span id="addressLine2Span"><c:if test="${materialRequestData.updatable == 'Y'}">${materialRequestData.addressLine2}</c:if></span>
				</td>
			</tr>
			<tr>
				<td width="25%"  class="optionTitleBoldRight">
					<fmt:message key="label.contactinfo"/>:&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft">
					<c:choose>
						<c:when test="${materialRequestData.canEditMr == 'Y' || 'Y' == materialRequestData.orderMaintenanceHeaderPermission}">
							<input name="contactInfo" id="contactInfo" type="text" class="inputBox" value="${materialRequestData.contactInfo}" size="30"/>
						</c:when>
						<c:otherwise>
							${materialRequestData.contactInfo}
							<input name="contactInfo" id="contactInfo" type="hidden" class="inputBox" value="${materialRequestData.contactInfo}"/>
						</c:otherwise>
					 </c:choose>
				</td>
				<td width="25%"   class="optionTitleBoldRight">
					<span id ="extPriceLabel">
						<fmt:message key="label.extprice"/>:&nbsp;
					</span>
				</td>
				<td width="25%" class="optionTitleLeft">
					<span id="extPrice" name="extPrice">
					</span>
				</td>
				<td width="25%"  class="optionTitleBoldRight">
					&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft" nowrap>
					<span id="addressLine3Span"><c:if test="${materialRequestData.updatable == 'Y'}">${materialRequestData.addressLine3}</c:if></span>
				</td>
			</tr>
			<tcmis:featureReleased feature="ReorderMR" scope="${materialRequestData.facilityId}" companyId="${materialRequestData.companyId}">
				<tr>
					<td width="25%"  class="optionTitleBoldRight">
						<fmt:message key="label.ordername"/>:&nbsp;
					</td>
					<td width="25%"  class="optionTitleLeft">
						<c:choose>
							<c:when test="${materialRequestData.canEditMr == 'Y' || 'Y' == materialRequestData.orderMaintenanceHeaderPermission}">
								<input name="orderName" id="orderName" type="text" class="inputBox" value="<c:out value="${materialRequestData.orderName}"/>" size="30"/>
							</c:when>
							<c:otherwise>
								<c:out value="${materialRequestData.orderName}"/>
								<input name="orderName" id="orderName" type="hidden" class="inputBox" value="<c:out value="${materialRequestData.orderName}"/>"/>
							</c:otherwise>
						 </c:choose>
					</td>
				</tr>
			</tcmis:featureReleased>
			<tr>
				<td colspan="5">
					&nbsp;
				</td>
				<td width="25%"  class="optionTitleLeft" nowrap>
					<span id="addressSpan"><c:if test="${materialRequestData.updatable == 'Y'}">${materialRequestData.city}, ${materialRequestData.stateName}, ${materialRequestData.zip}, ${materialRequestData.countryName}</c:if></span>
				</td>
			</tr>
		</table>
		</fieldset>

		</div> <%-- mainUpdateLinks Ends --%>
	</div> <%-- boxhead Ends --%>

<!-- Input Dialog Window Begins -->
	 <%-- relax shelf life --%>
<div id="inputDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="left"  width="100%">
				<fmt:message key="label.line"/>&nbsp;<span id="materialLine" style="display: none;"></span>:
			</td>
		</tr>
		<tr>
			<td align="left"  width="100%">
				<fmt:message key="label.thereisstock"/><br>&nbsp;<span id="daysId" style="display: none;"></span>&nbsp;<fmt:message key="label.daysshelflife"/>
			</td>
		</tr>
		<tr>
			<td align="left"  width="100%">
				<input type="radio" name="deliveryOption"  id="deliveryOption" value="Send" /><fmt:message key="label.sendfromavailablestock"/>
			</td>
		</tr>
		<tr>
			<td align="left"  width="100%">
				<input type="radio" name="deliveryOption"   id="deliveryOption" value="DontSend" /><fmt:message key="label.dontsendfromavailablestock"/>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center">
				<input name="continue"  id="continue"  type="button" value="<fmt:message key="label.continue"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="continueBtn();"/>
				<%--
				&nbsp;&nbsp;
				<input name="cancel"  id="cancel"  type="button" value="<fmt:message key="label.cancel"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="cancelBtn();"/>
				--%>
			</td>
		</tr>
	</table>
</div>

	 <%-- transition --%>
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
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

	 <%-- message --%>
<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="center" width="100%">
				<textarea cols="70" rows="5" class="inputBox" readonly="true" name="messageText" id="messageText"></textarea>
			</td>
		</tr>
		<tr>
			<td align="center" width="100%">
			<input name="closeMessageB"  id="closeMessageB"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
			</td>
		</tr>
	</table>
</div>

	 <%-- message --%>
<div id="cancelRequestMessageDailogWin" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr id="cancelWarningMsgRow">
            <td align="left" width="100%" id="cancelWarningMsg">
			</td>		
		</tr>
        <tr><td>&nbsp;</td></tr>
        <tr>
			<td align="left" width="100%">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <fmt:message key="label.reason"/>:<br>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <textarea cols="75" rows="10" class="inputBox" name="cancelRequestMessageText" id="cancelRequestMessageText"></textarea>
			</td>
		</tr>
		<tr>
		<td width="100%" align="center">
			<input name="closeRequestCancelMessageB"  id="closeRequestCancelMessageB" type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
            <input name="cancelClose"  id="cancelClose" type="button" value="<fmt:message key="label.cancel"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="cancelCloseWin();"/>
		</td>
		</tr>
	</table>
</div>

	 <%-- use approval --%>
<div id="showApprovedByArea" class="successMessages" style="display:none;overflow: auto;">
	<table width="100%" border="0" cellpadding="5" cellspacing="5" class="tableNoData" id="resultsPageTable">
		 <tr>
		 	<td class="optionTitleBoldRight" width="50%"><fmt:message key="label.approvedby"/>: </td>
		 	<td width="50%" id="approvedName"></td>
		 </tr>
		 <tr>
		 	<td class="optionTitleBoldRight" width="50%"><fmt:message key="label.phone"/>:</td>
		 	<td width="50%" id="approverPhone"></td>
		 </tr>
		 <tr>
		 	<td class="optionTitleBoldRight" width="50%"><fmt:message key="label.email"/>:</td>
		 	<td width="50%" id="approverEmail"></td>
		 </tr>
		 <tr>
		 	<td class="optionTitleBoldRight" width="50%"><fmt:message key="label.reason"/>:</td>
		 	<td rowspan="2" width="50%" id="approvalReason">&nbsp;</td>
		 </tr>
		 <tr>
		 	<td>&nbsp;</td>
		 </tr>
		 <tr>
		 	<td align="center" colspan="2">
		 		<input name="ok" id="ok" type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="okBtn();"/>
		 	</td>
		 </tr>
	</table>
</div>

<!--  Approver Data Start -->
<div id="ApproverDataWinArea" class="errorMessages" style="display: none;width: 650px; height: 250px; overflow: auto;">
	<table  border="0" cellpadding="0" cellspacing="0">
		<tr id="noApproverMessageRow" style="display: none">
			<td id="noApproverMessageData">
			</td>
		</tr>
		<tr>
			<td>
				<div id=approverDataInnerDiv class="errorMessages" style="width: 500px; height: 200px; overflow: auto;">
				</div>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center"  width="100%">
				<input name="continue1"  id="continue1"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeOk();"/>
			</td>
		</tr>
	</table>

	<script language="JavaScript" type="text/javascript">
	<!--
		var gridConfig = {
			divName:'approverDataInnerDiv', // the div id to contain the grid.
			beanData:'jsonApproverMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'beanApproverGrid',     // the grid's name, as in beanGrid.attachEvent...
			config:'pendingApproversConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:false,			 // this page has rowSpan > 1 or not.
			submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
			onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
			onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		//	onBeforeSorting:_onBeforeSorting
		};

		var pendingApproversConfig = [
		  {
			columnId:"name",
			columnName:'',
			width:12
		  },
		  {
			columnId:"email",
			columnName:'<fmt:message key="label.email"/>',
			width:14
		  },
		  {
			columnId:"phone",
			columnName:'<fmt:message key="label.phone"/>',
			width:12
		  }
		  ];
	//-->
	</script>
</div>

	 <!--  Approver Data win Start -->
<div id="ListApproverDataWinArea" class="errorMessages" style="display: none;width: 800px; height: 250px; overflow: auto;">
	<table  border="0" cellpadding="0" cellspacing="0">
	 <tr id="noListApproverMessageRow" style="display: none" width="100%">
		 <td align="center" width="100%">
             <fmt:message key="label.noapproverneeded"/>
         </td>
	 </tr>
	 <tr id="listApproverData" style="display: none">
	 	<td>
	 		<div id=listApproverDataInnerDiv class="errorMessages" style="width: 780px; height: 200px; overflow: auto;">
	 		</div>
	 	</td>
	 </tr>
	 <tr width="100%">
		 	<td width="100%">&nbsp;</td>
	 </tr>
	 <tr width="100%">
	 	<td align="center"  width="100%">
	 		<input name="continue3"  id="continue3"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="showApproverListWinClose();"/>
	 	</td>
	 </tr>
	 </table>


	 <script language="JavaScript" type="text/javascript">

	 <!--
	 var gridConfig = {
		 divName:'listApproverDataInnerDiv', // the div id to contain the grid.
		 beanData:'jsonListApproverMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		 beanGrid:'beanListApproverGrid',     // the grid's name, as in beanGrid.attachEvent...
		 config:'listApproverConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		 rowSpan:false,			 // this page has rowSpan > 1 or not.
		 submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
		 onRowSelect:null,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		 onRightClick:null  // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	 //	onBeforeSorting:_onBeforeSorting
	 };

<c:set var="approvalDate">
 <fmt:message key="label.approvaldate"/>
</c:set>

<c:set var="approvalStatus">
 <fmt:message key="label.approvalstatus"/>
</c:set>

<c:set var="approvalComments">
 <fmt:message key="label.approvalcomments"/>
</c:set>

<c:set var="approvalType">
 <fmt:message key="label.approvaltype"/>
</c:set>

var listApproverConfig = [
     {
        columnId:"mrLine",
        columnName:'<fmt:message key="label.mrline"/>',
        width:10
    },
   {
		columnId:"approvalType",
		columnName:'<tcmis:jsReplace value="${approvalType}"/>',
		width:20
	},
	{
		columnId:"approvalStatus",
		columnName:'<tcmis:jsReplace value="${approvalStatus}"/>',
		width:10
	},
	{
		columnId:"approver",
		columnName:'<fmt:message key="label.approver"/>',
		width:10
	},
    {
		columnId:"email",
		columnName:'<fmt:message key="label.email"/>',
		width:10
	},
    {
		columnId:"phone",
		columnName:'<fmt:message key="label.phone"/>',
		width:10
	},    
    {
		columnId:"approvalDate",
		columnName:'<tcmis:jsReplace value="${approvalDate}"/>',
		sorting:"int",
		hiddenSortingColumn:"happrovalDate"
	},
	{
		columnId:"approvalComments",
		columnName:'<tcmis:jsReplace value="${approvalComments}"/>',
		width:12
	}
];

<%-- hold for program --%>
<c:set var="showOnHoldForProgram" value="${tcmis:isFeatureReleased(personnelBean,'ShowHoldForProgram', materialRequestData.facilityId)}"/>
<c:if test="${showOnHoldForProgram == 'false'}">
    <c:set var="showOnHoldForProgram" value='N'/>
</c:if>
<c:if test="${showOnHoldForProgram == 'true'}">
    <c:set var="showOnHoldForProgram" value='Y'/>
</c:if>
<c:set var="hasOnHoldForProgramPermission" value='N'/>
<%-- allocate by mfg lot --%>
<c:set var="showAllocateByMfgLot" value="${tcmis:isFeatureReleased(personnelBean,'ShowAllocateByMfgLot', materialRequestData.facilityId)}"/>
<c:if test="${showAllocateByMfgLot == 'false'}">
    <c:set var="showAllocateByMfgLot" value='N'/>
</c:if>
<c:if test="${showAllocateByMfgLot == 'true'}">
    <c:set var="showAllocateByMfgLot" value='Y'/>
</c:if>


<c:set var="hasAllocateByMfgLotPermission" value='N'/>
<c:if test="${materialRequestData.viewType != 'VIEW'}">
    <c:if test="${showOnHoldForProgram == 'Y'}">
        <c:set var="hasOnHoldForProgramPermission" value='Y'/>
    </c:if>
</c:if>
<c:if test="${materialRequestData.viewType == 'REQUEST'}">
    <c:if test="${showAllocateByMfgLot == 'Y'}">
        <tcmis:facilityPermission indicator="true" userGroupId="SetMrAllocateByMfgLot" companyId="${personnelBean.companyId}" facilityId="${materialRequestData.facilityId}">
            <c:set var="hasAllocateByMfgLotPermission" value='Y'/>
        </tcmis:facilityPermission>
    </c:if>
</c:if>
<%-- can edit owner segment id--%>
<c:set var="canSetMrOnHoldForProgram" value='N'/>  
<c:if test="${materialRequestData.viewType != 'VIEW'}">
    <c:if test="${showOnHoldForProgram == 'Y'}">
        <tcmis:facilityPermission indicator="true" userGroupId="SetMrOnHoldForProgram" companyId="${personnelBean.companyId}" facilityId="${materialRequestData.facilityId}">
            <c:set var="canSetMrOnHoldForProgram" value='Y'/>
        </tcmis:facilityPermission>
    </c:if>
</c:if>

<c:set var="dateNeeded">
	<fmt:message key="label.dateneeded"/>
</c:set>

var qualityIdLen = 10;
var catPartAttrLen = 10;
var qualityIdLabelColumnHeader = '${qualityIdLabelColumnHeader}';
var catPartAttrColumnHeader = '${catPartAttrColumnHeader}';

// set hidden later is more troublesome.
if( '--Hide--' ==	qualityIdLabelColumnHeader )	
	qualityIdLen = 0;
if( '--Hide--' ==	catPartAttrColumnHeader )	
	catPartAttrLen = 0;

var config = [
  {
  	columnId:"permission"
  },
// Assign different permissions for different coulmns
  {
  	columnId:"notesPermission"
  },
  {
  	columnId:"lineItem",
  	columnName:'<fmt:message key="label.line"/>',
    width:3
  },
  {
  	columnId:"inventoryGroup"
  },
  <c:if test="${homeCompanyLogin}">
	  {
	  	columnId:"inventoryGroupName",
	  	columnName:'<fmt:message key="label.inventorygroup"/>',
	    width:8
	  },
  </c:if>
  {
  	columnId:"requestLineStatus",
  	columnName:'<fmt:message key="label.status"/>',
    width:8
  },
  {
  	columnId:"workArea",
  	columnName:'<fmt:message key="label.workarea"/>',
    sorting:'int',
    width:8
  },
  {
  	columnId:"catPartNo",
  	columnName:'<fmt:message key="label.part"/>',
  	tooltip:"Y",
    width:7
  },
  {
  	columnId:"partDescription",
  	columnName:'<fmt:message key="label.description"/>',
    width:20
  },
  {
  	columnId:"itemType",
  	columnName:'<fmt:message key="label.type"/>',
  	align:'center',
  	width:5
  },
  {
  	columnId:"qty",
  	columnName:'<fmt:message key="label.qty"/>',
// <c:if test="${homeCompanyLogin}">  	
//  	onChange:checkPOLineQty,
 </c:if> 	
  	type:'hed',
  	align:'center',
  	width:5
  },
  {
  	columnId:"origQty"
  },
  {
  	columnId:"packaging",
  	columnName:'<fmt:message key="label.packaging"/>',
  	tooltip:"Y",
  	width:12
  },
  {
  	columnId:"critical",
  	columnName:'<fmt:message key="label.critical"/>',
  	onChange:changeCritical,
  	type:'hchstatus',
    align:'center',
    width:4
  },
  {columnId:"holdAsCustomerOwnedPermission"
  },
  {
  	columnId:"holdAsCustomerOwned",
    <c:if test="${showOnHoldForProgram == 'Y'}">
      columnName:'<fmt:message key="label.holdforprogram"/>',
      onChange:changeHoldAsCustomerOwned,
  	  type:'hchstatus',
      align:'center',
      width:4,
    </c:if>
    permission:true
  },
  {columnId:"holdAsCustomerOwnedDataField"
  },
  {columnId:"ownerSegmentIdPermission"
  },
  {
  	columnId:"ownerSegmentId",
    <c:if test="${showOnHoldForProgram == 'Y'}">
      columnName:'<fmt:message key="label.owner"/>',
  	  type:'hcoro',
      size:10,
      width:4,
    </c:if>
    permission:true
  },
  {columnId:"allocateByMfgLotPermission"
  },
  { columnId:"allocateByMfgLot",
      <c:if test="${showAllocateByMfgLot == 'Y'}">
        columnName:'<fmt:message key="label.mfglot"/>',
        type: 'hcoro',
      </c:if>
      permission:true
  },
  {
  	columnId:"notes",
  	columnName:'<fmt:message key="label.notes"/>',
  	type:'txt',
  	tooltip:"Y",
  	width:40
  },
    {columnId:"scrap"
  },
  {columnId:"catalogPrice"
  },
  {columnId:"unitPrice"
  },
  {columnId:"currencyId"
  },
  {columnId:"orderQuantityRule"
  },
  {columnId:"sizeUnit"
  },

  <c:if test="${homeCompanyLogin}">
  	{
  		columnId:"relaxShelfLifeFlag",
	  	columnName:'<fmt:message key="label.relaxshelflife"/>',
	  	onChange:setHiddenColumn,
	  	type:'hchstatus',
	    align:'center',
	    width:4
  	},
  	{
  		columnId:"origRelaxShelfLifeFlag",
	  	type:'hchstatus'
  	},
  	{
	  	columnId:"dropShipOverride",
	  	columnName:'<fmt:message key="label.dropship"/>',
	  	type:'hchstatus',
	    align:'center',
	    width:4
  	},
  	{
	  	columnId:"origDropShipOverride",
	  	type:'hchstatus'
  	},
  	{
	  	columnId:"scrapFlag",
	  	columnName:'<fmt:message key="label.scrap"/>',
	  	onChange:setHiddenColumn,
	  	type:'hchstatus',
	    align:'center',
	    width:4
  	},
  	{ 
  		columnId:"totalPicked",
		  columnName:'<fmt:message key="label.picked"/>',
		  sorting:"int",
		  width:6,
		  hiddenSortingColumn:"htotalShipped"
	},
	{ 
		columnId:"totalShipped",
		  columnName:'<fmt:message key="label.delivered"/>',
		  width:6,
		  sorting:"int"	 
	},
	{ 
		columnId:"lastShipped",
		columnName:'<fmt:message key="label.lastdelivered"/>',
		sorting:"int",
		hiddenSortingColumn:"hlastShipped",
		  width:7		 
	},
	{ 
		columnId:"htotalShipped",
		sorting:"int"
	},
	{ 
		columnId:"hlastShipped",
		sorting:"int"
	},
  </c:if>
  {
   columnId:"shipToLocationIdDesc",
   columnName:'<fmt:message key="label.dock"/>'
  },
  {
   columnId:"deliveryPointDesc",
   columnName:'<fmt:message key="label.deliverto"/>'
  },
  {
   columnId:"deliveryType",
   columnName:'<fmt:message key="label.deliverytype"/>'
  },
  {
   columnId:"defaultNeedByDate",
   columnName:'<tcmis:jsReplace value="${dateNeeded}"/>'
  },
  <c:if test="${homeCompanyLogin}">
  	{
	   columnId:"cancelRequestorName",
	   columnName:'<fmt:message key="label.cancel"/> <fmt:message key="label.requestor"/>'
	},
	{
	   columnId:"cancelApproverName",
	   columnName:'<fmt:message key="label.cancel"/> <fmt:message key="label.approver"/>'
	},
  </c:if>
  {
	columnId:"shipToLocationId"
  },
  {
	columnId:"deliveryPoint"
  },
  {columnId:"chargeType"
  },
  {columnId:"unitOfSaleQuantityPerEach"
  },
  {columnId:"unitOfSale"
  },
  {columnId:"unitOfSalePrice"
  },
  {columnId:"unitPriceRequired"
  },
  {columnId:"poNumber"
  },
  {columnId:"directedChargeForDirect"
  },
  {columnId:"directedChargeForIndirect"
  },
  {columnId:"chargeNumbers"
  },
  {columnId:"totalQuantityIssued"
  }, 
  {columnId:"relaxShelfLife"
  },
  {columnId:"lineFlag"
  },
  {columnId:"cancelStatus"
  },
  {columnId:"canRelaxShelfLife"
  },
  {columnId:"remainingShelfLife"
  },
  {
   columnId:"useApprovalStatus"
  },
  {
   columnId:"listApprovalStatus"
  },
  {
   columnId:"chargeApprovalStatus"
  },
  {columnId:"releaseNumber"
  },
  {columnId:"approveRejectComments"
	 <c:if test="${homeCompanyLogin}">
	 	,
		 columnName:'<fmt:message key="label.cancel"/> <fmt:message key="label.comment"/>',
		 width:40
  	 </c:if>
  },
  {columnId:"chargeNumbersFromDirectedChargeD"
  },
  {columnId:"chargeNumbersFromDirectedChargeI"
  },
  {columnId:"canEditLineChargeData"
  },
  {columnId:"lineDefaultChargeType"
  },
  {columnId:"application"
  },
  {columnId:"dockSelectionRule"
  },
  {columnId:"deliveryPointSelectionRule"
  },
  {columnId:"allocateByChargeNumber1"
  },
  {columnId:"allocateByChargeNumber2"
  },
  {columnId:"allocateByChargeNumber3"
  },
  {columnId:"allocateByChargeNumber4"
  },
  {columnId:"lineAllocateByMfgLotPerm"
  },
  {columnId:"orderMaintenancePerm"
  },
  { columnId:"qualityId",
	columnName:qualityIdLabelColumnHeader,
	width:qualityIdLen
  },
  { columnId:"catPartAttribute",
	columnName:catPartAttrColumnHeader,
	width:catPartAttrLen
  }        	

];

<c:if test="${materialRequestData != null}" >
	<c:if test="${materialRequestData.ownerSegmentColl != null}" >
	    <c:set var="ownerSegmentColl" value='${materialRequestData.ownerSegmentColl}'/>
	    <bean:size id="ownerSegmentSize" name="ownerSegmentColl"/>
	    var ownerSegmentId = new Array(
	    <c:if test="${ownerSegmentSize > 1 || ownerSegmentSize == 0}">
	    {
	        text:'<fmt:message key="label.select"/>',
	        value:''
	    }
	    </c:if>
	    <c:if test="${ownerSegmentSize > 1}">
	    ,
	    </c:if>
	    <c:forEach var="ownerSegmentBean" items="${materialRequestData.ownerSegmentColl}" varStatus="status2">
	       <c:if test="${status2.index > 0}">,</c:if>
	       {text:'<tcmis:jsReplace value="${ownerSegmentBean.ownerSegmentDesc}"/>',
	         value:'<tcmis:jsReplace value="${ownerSegmentBean.ownerSegmentId}"/>'
	        }
	    </c:forEach>
	    );
    </c:if>
</c:if>
     //-->
	 </script>
</div>


<!--  Input Dialog End -->



	 <div class="dataContent">
		 <fieldset><legend><fmt:message key="label.requestlineitems"/></legend>
		 <!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
	 	<div id="materialRequestDivId" style="display: none;"></div>
		<!-- Search results start -->
		<c:if test="${materialRequestData != null}" >
		<c:set var="lineItemDataColl" value="${materialRequestData.lineItemColl}"/>
			<script type="text/javascript">
			<!--
                var allocateByMfgLot = new Array();

                <c:set var="dataCount" value='${0}'/>
				<c:if test="${!empty materialRequestData}" >
					/*Storing the data to be displayed in a JSON object array.*/
					var jsonMainData = {
			      rows:[
					  <c:forEach var="lineItem" items="${lineItemDataColl}" varStatus="status">
					  	 <c:set var="orderMaintenancePermission" value='N'/>
					  	 <tcmis:inventoryGroupPermission indicator="true" userGroupId="OrderMaintenance" companyId="${personnelBean.companyId}" inventoryGroup="${lineItem.inventoryGroup}">
					  		<c:set var="orderMaintenancePermission" value='Y'/>
					 	 </tcmis:inventoryGroupPermission>
					 	 
						 <fmt:formatDate var="fmtDate" value="${lineItem.defaultNeedByDate}" pattern="${dateFormatPattern}"/>
						 
						 <c:set var="criticalFlag" value="false"/>
						 <c:if test="${lineItem.critical == 'y'}">
						 	<c:set var="criticalFlag" value="true"/>
						 </c:if>	
						 
						 <c:set var="dropShipOverrideFlag" value="false"/>
						 <c:if test="${lineItem.dropShipOverride == 'Y'}">	
						 	<c:set var="dropShipOverrideFlag" value="true"/>
						 </c:if>

						 <c:set var="scrapFlag" value="false"/>
						 <c:if test="${lineItem.scrap == 'y'}">	
						 	<c:set var="scrapFlag" value="true"/>
						 </c:if>
						 
						 <c:set var="relaxShelfLifeFlag" value="false"/>
						 <c:if test="${lineItem.relaxShelfLife == 'y'}">	
						 	<c:set var="relaxShelfLifeFlag" value="true"/>
						 </c:if>
						 
						 <c:set var="dockDesc" value="${lineItem.shipToLocationId}"/>
						 <c:set var="deliverToDesc" value="${lineItem.deliveryPoint}"/>
						 <c:forEach var="collBean" items="${materialRequestData.lineItemColl}" varStatus="collStatus">
							 <c:forEach var="dock" items="${collBean.dockDeliveryPointColl}" varStatus="dockStatus">
								 <c:if test="${lineItem.shipToLocationId == dock.dockLocationId}">
								 	<c:set var="dockDesc" value="${dock.dockDesc}"/>
									 <c:forEach var="deliverTo" items="${dock.deliveryPointColl}" varStatus="deliverToStatus">
									 	<c:if test="${deliverTo.deliveryPoint == lineItem.deliveryPoint}">
									 		<c:set var="deliverToDesc" value="${deliverTo.deliveryPointDesc}"/>
									 	</c:if>	
									 </c:forEach>
								 </c:if>	 
							  </c:forEach>			 
						  </c:forEach>

                          <c:set var="holdAsCustomerOwnedFlag" value="false"/>
						 <c:if test="${lineItem.holdAsCustomerOwned == 'Y'}">
						 	<c:set var="holdAsCustomerOwnedFlag" value="true"/>
						 </c:if>
						
						  <fmt:formatDate var="fmtLastShipped" value="${lineItem.lastShipped}" pattern="${dateFormatPattern}"/> 
						  <c:set var="lastShippedSortable" value="${lineItem.lastShipped.time}"/> 
						  
						  <c:choose>
						 	<c:when test="${dataCount % 2 == 1}"><c:set var="rowState" value="odd_haas"/></c:when>
						 	<c:otherwise><c:set var="rowState" value="ev_haas"/></c:otherwise>
						 </c:choose>

                          <c:if test="${status.index != 0 }">,</c:if>
						  { id:${status.index +1},
							  <c:if test="${lineItem.critical == 'y'}">
						 		'class':"grid_red ${rowState}",
						 	  </c:if>
							  <c:if test="${lineItem.cancelStatus == 'rqcancel' || lineItem.cancelStatus == 'Canceled'}">
						 		'class':"grid_black ${rowState}",
						 	  </c:if>
							  data:[
							        <c:choose>
										<c:when test = "${(materialRequestData.canEditMrQty == 'Y' || orderMaintenancePermission == 'Y') && lineItem.cancelStatus != 'Canceled'}">
											'Y',
										</c:when>
										<c:otherwise>
											'N',
										</c:otherwise>
									</c:choose>
									 <c:choose>
										<c:when test = "${(materialRequestData.canEditMr == 'Y' || orderMaintenancePermission == 'Y') && lineItem.cancelStatus != 'Canceled'}">
											'Y',
										</c:when>
										<c:otherwise>
											'N',
										</c:otherwise>
									</c:choose>
									'${lineItem.lineItem}',
                                	'${lineItem.inventoryGroup}',
                                    <c:if test="${homeCompanyLogin}">
                                    	'${lineItem.inventoryGroupName}',
                                    </c:if>
                                    '${lineItem.requestLineStatus}',
                                    '<tcmis:jsReplace value="${lineItem.applicationDisplay}" processMultiLines="true"/>',
									'${lineItem.facPartNo}',
									'<tcmis:jsReplace value="${lineItem.partDescription}" processMultiLines="true"/>',
									'${lineItem.itemType}',
									'${lineItem.quantity}',
									'${lineItem.quantity}',
									'<tcmis:jsReplace value="${lineItem.examplePackaging}" processMultiLines="true"/>',
									${criticalFlag},
                                    'N',
                                    ${holdAsCustomerOwnedFlag},
                                    '${lineItem.holdAsCustomerOwned}',
                                    '${hasOnHoldForProgramPermission}',
                                    '${lineItem.ownerSegmentId}',  
                                    '${hasAllocateByMfgLotPermission}',
                                    '${lineItem.allocateByMfgLot}',
                                    '<tcmis:jsReplace value="${lineItem.notes}" processMultiLines="true"/>',
									'${lineItem.scrap}',
									'${lineItem.catalogPrice}',
									'${lineItem.unitPrice}',
									'${lineItem.currencyId}',
									'${lineItem.orderQuantityRule}',
									'${lineItem.sizeUnit}',
									<c:if test="${homeCompanyLogin}">
										 ${relaxShelfLifeFlag},
										 ${relaxShelfLifeFlag},
										 ${dropShipOverrideFlag},
										 ${dropShipOverrideFlag},
										 ${scrapFlag},
										 '${lineItem.totalPicked} <fmt:message key="label.of"/> ${lineItem.quantity}',
										 '${lineItem.totalShipped}',
										 '${fmtLastShipped}',
										 '${lineItem.totalPicked}',
										 '${lastShippedSortable}',
	                                </c:if>
									'${dockDesc}',
									'${deliverToDesc}',
									'${lineItem.deliveryType}',
									'${fmtDate}',
									<c:if test="${homeCompanyLogin}">
										'${lineItem.cancelRequesterName}',
										'${lineItem.cancelApproverName}',
									</c:if>	
									'${lineItem.shipToLocationId}',
									'${lineItem.deliveryPoint}',	
									'${lineItem.chargeType}',
									'${lineItem.unitOfSaleQuantityPerEach}',
									'${lineItem.unitOfSale}',
									'${lineItem.unitOfSalePrice}',
									'',
									'<tcmis:jsReplace value="${lineItem.poNumber}"/>', 
									'${lineItem.directedChargeForDirect}',
									'${lineItem.directedChargeForIndirect}',
									'',
									'${lineItem.totalQuantityIssued}',		
									'${lineItem.relaxShelfLife}',							
									'',
									'${lineItem.cancelStatus}',
									'${lineItem.canRelaxShelfLife}',
									'${lineItem.remainingShelfLife}',
									'${lineItem.useApprovalStatus}',
									'${lineItem.listApprovalStatus}',
                                    '${lineItem.chargeApprovalStatus}',  
                                    '${lineItem.releaseNumber}',
                                    '${lineItem.cancelComment}',
									'${lineItem.chargeNumbersFromDirectedChargeD}',
									'${lineItem.chargeNumbersFromDirectedChargeI}',
									'${lineItem.canEditLineChargeData}',
                                    '${lineItem.lineDefaultChargeType}',
                                    '<tcmis:jsReplace value="${lineItem.application}"/>',
                                    '${lineItem.dockSelectionRule}',
                                    '${lineItem.deliveryPointSelectionRule}',
                                    '${lineItem.allocateByChargeNumber1}',
                                    '${lineItem.allocateByChargeNumber2}',
                                    '${lineItem.allocateByChargeNumber3}',
                                    '${lineItem.allocateByChargeNumber4}',
                                    '${hasAllocateByMfgLotPermission}',
                                    '${orderMaintenancePermission}',
                                    '${status.current.qualityId}',
                                    '${status.current.catPartAttribute}'
                         ]}
					   <c:set var="dataCount" value='${dataCount+1}'/>
                      </c:forEach>
					]};

                       <%-- start of mfg lot collection--%>
                    <c:forEach var="lineItem" items="${lineItemDataColl}" varStatus="status">
                       <c:if test="${hasAllocateByMfgLotPermission == 'Y' && (materialRequestData.canEditMr == 'Y' || 'Y' == materialRequestData.orderMaintenanceHeaderPermission)}">
                           <c:set var="mfgLotColl" value='${lineItem.mfgLotColl}'/>
                           <bean:size id="mfgLotSize" name="mfgLotColl"/>
                           allocateByMfgLot['${lineItem.lineItem}']=[
                            {
                                text:'<fmt:message key="label.norestriction"/>',
                                value:''
                            }
                            <c:forEach var="receiptBean" items="${lineItem.mfgLotColl}" varStatus="status2">
                               ,
                               {
                                   text:'<tcmis:jsReplace value="${receiptBean.mfgLot}"/>',
                                   value:'<tcmis:jsReplace value="${receiptBean.mfgLot}"/>'
                                }
                            </c:forEach>
                            ];
                        </c:if>
                        <c:if test="${hasAllocateByMfgLotPermission == 'N'}">
                            allocateByMfgLot['${lineItem.lineItem}']=[
                            {
                                text:'${lineItem.allocateByMfgLot}',
                                value:'${lineItem.allocateByMfgLot}'
                            }];
                        </c:if>

                    </c:forEach>
                        <%-- end of mfg lot collection --%>

                </c:if>
			//-->
			</script>

			<!-- If the collection is empty say no data found -->
			<c:if test="${empty materialRequestData}" >
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
			 <tr>
			  <td width="100%">
				<fmt:message key="main.nodatafound"/>
			  </td>
			 </tr>
			</table>
			</c:if>

		</c:if>
	</fieldset>
<!-- Search results end -->
</div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar">
		<fieldset><legend id="lineItemDetail" name="lineItemDetail"></legend>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" height="230px">
			<tr>
				<td width="45%" valign="top" class="optionTitleBoldLeft">
					<table id="chargeNumberTable"  width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="60%" class="optionTitleBoldLeft" nowrap>
								<span id="lineItemPrice" name="lineItemPrice" value=""></span>
							</td>
							<td width="40%" class="optionTitleBoldCenter" nowrap>
							<span id="scrapObsoleteSpan">
									<input type="checkbox" name="scrapObsolete" id="scrapObsolete" value="" class="radioBtns"><fmt:message key="label.scrapobsolete"/>
								&nbsp;&nbsp;
							</span>
							<span id="copySpan" style="display: none">
								<input name="copyData" id="copyData" type="button" value="<fmt:message key='label.copy'/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"  onclick="copyDataInGrid();">
							</span>
							<span id="undoSpan" style="display: none">
								<input name="undoCopy" id="undoCopy" type="button" value="<fmt:message key='label.undo'/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"  onclick="undoCopyFromGrids();">
							</span>
							</td>
						</tr>
						<tr>
							<td width="100%" class="optionTitleBoldCenter" colspan="2">
								<span id="chargeTypeSpan" name="chargeTypeSpan">
									<input type="radio" name="chargeType" id="chargeTypeD" onClick="directedCheck();" value="direct" class="radioBtns" checked="checked"/><fmt:message key="label.direct"/>
									&nbsp;&nbsp;
									<input type="radio" name="chargeType" id="chargeTypeI" onClick="inDirectedCheck();" value="indirect" class="radioBtns"/><fmt:message key="label.indirect"/>
								</span>
							</td>
						</tr>
						<tr>
							<td width="100%" class="optionTitleBoldLeft" colspan="2">
								<div id="chargeNumber2ColumnsDivId" style="display: none;" height="180px"></div>
								<div id="chargeNumber3ColumnsDivId" style="display: none;" height="180px"></div>
								<div id="chargeNumber4ColumnsDivId" style="display: none;" height="180px"></div>
								<div id="chargeNumber5ColumnsDivId" style="display: none;" height="180px"></div>
							</td>
						</tr>
					</table>
				</td>
				<td width="55%" valign="top" class="optionTitleBoldLeft">
					<table  width="100%" border="0" cellpadding="3" cellspacing="3">
                        <tr>
							<td colspan= "3" class="optionTitleBoldCenter">
								<fmt:message key="label.deliveryinstructions"/>:&nbsp;
							</td>
                        </tr>
                        <tr>
                            <td width="5%" class="optionTitleBoldLeft">&nbsp;</td>
                            <td width="10%" class="optionTitleBoldLeft">&nbsp;</td>
                            <td width="40%" class="optionTitleBoldLeft">
								<select name="deliveryType" id="deliveryType" class="selectBox" onchange="deliveryTypeChanged()">
								</select>
								&nbsp;
								<span id="deliverDateSpan" name="deliverDateSpan" style="display: none">
									<input type="hidden" name="today" id="today" value='<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>' />
									<input class="inputBox pointer" name="deliverDate" id="deliverDate" type="text" value="" size="10" readonly="true" onClick="return getCalendar(document.getElementById('deliverDate'),$('today'));" onchange="setDeliverDate(value);">
								</span>
								<span id="scheduleSpan" name="scheduleSpan" style="display: none">
									<input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key="label.schedule"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'"  onclick="showDeliverySchedule();">
								</span>
							</td>
                        </tr>
						<tr>
                            <td width="5%" class="optionTitleBoldLeft">&nbsp;</td>
                            <td id="dockLabel" width="10%" class="optionTitleBoldRight">
								<c:if test="${materialRequestData.updatable != 'Y'}"><fmt:message key="label.dock"/>:&nbsp;</c:if>
							</td>
							<td id="dockSpan" width="40%" class="optionTitleBoldLeft">
								<c:if test="${materialRequestData.updatable != 'Y'}">
									<select name="dock" id="dock" class="selectBox" onchange="dockChanged(id)">
									</select>
								</c:if>
							</td>
                        </tr>
						<tr>
                            <td width="5%" class="optionTitleBoldLeft">&nbsp;</td>
                            <td id="delivertoLabel" width="10%" class="optionTitleBoldRight">
								<c:if test="${materialRequestData.updatable != 'Y'}"><fmt:message key="label.deliverto"/>:&nbsp;</c:if>
							</td>
							<td id="delivertoSpan" width="40%" class="optionTitleBoldLeft">
								<c:if test="${materialRequestData.updatable != 'Y'}">
									<select name="deliverTo" id="deliverTo" class="selectBox" onchange="setDeliverTo(id)">
									</select>
								</c:if>
							</td>
                        </tr>
                        <tr>
                            <td width="5%" class="optionTitleBoldLeft">
								&nbsp;
							</td>
                            <td width="5%" class="optionTitleBoldLeft">
								&nbsp;
							</td>
                            <td id="lineSpan" width="40%" class="optionTitleBoldLeft">
								<c:if test="${materialRequestData.updatable != 'Y'}"><hr width="200px"></hr></c:if>
							</td>
						</tr>

                   <tr>
							<td width="5%" class="optionTitleBoldLeft">&nbsp;</td>
							<td width="10%" class="optionTitleBoldRight">&nbsp;
								<span id="poLabelSpan" name="poLabelSpan">
									<fmt:message key="label.po"/>:&nbsp;
								</span>
							</td>
							<td width="40%" class="optionTitleBoldLeft">
								<span id="poComboSpan" name="poComboSpan">
									<select name="poCombo" id="poCombo" class="selectBox">
									</select>
								</span>
								<span id="poInputSpan" name="poInputSpan">
									<input name="poInput" id="poInput" type="text" class="inputBox" value="" size="10"/>
								</span>
								&nbsp;&nbsp;&nbsp;
								<span id="poLineSpan" name="poLineSpan">
									<fmt:message key="label.line"/>:&nbsp;
									<input name="poLineInput" id="poLineInput" type="text" class="inputBox" value="" size="10"/>
								</span>
							 </td>
						</tr>
						<tr>
							<td width="5%" class="optionTitleBoldLeft">&nbsp;</td>
							<td width="10%" class="optionTitleBoldRight">&nbsp;
								<span id="poQtyLabelSpan" name="poQtyLabelSpan">
									<fmt:message key="label.poqty"/>:&nbsp;
								</span>
							</td>
							<td width="40%" class="optionTitleBoldLeft">
								<span id="poQtyUomSpan" name="poQtyUomSpan">
									<input name="poQty" id="poQty" type="text" class="inputBox" value="" size="5"/>
									&nbsp;&nbsp;&nbsp;
									<fmt:message key="label.pouom"/>:&nbsp;
									<input name="poUom" id="poUom" type="text" class="inputBox" value="" size="5"/>
								</span>
							 </td>
						</tr>
						<tr>
							<td width="5%" class="optionTitleBoldLeft">&nbsp;</td>
							<td width="10%" class="optionTitleBoldRight">&nbsp;
								<span id="poUnitPriceLabelSpan" name="poUnitPriceLabelSpan">
									<fmt:message key="label.pounitprice"/>:&nbsp;
								</span>
							</td>
							<td width="40%" class="optionTitleBoldLeft">
								<span id="poUnitPriceCurrencySpan" name="poUnitPriceCurrencySpan">
									<input name="poUnitPrice" id="poUnitPrice" type="text" class="inputBox" value="" size="5"/>
									&nbsp;
									<select name="currencyCombo" id="currencyCombo" class="selectBox">
									</select>
								</span>
							 </td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</fieldset>
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
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<%--Store the search parameters.--%>
<input name="action" id="action" value="" type="hidden"/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="prNumber" id="prNumber" type="hidden" value="${param.prNumber}"/>
<input name="companyId" id="companyId" type="hidden" value="${materialRequestData.companyId}"/>
<input name="facilityId" id="facilityId" type="hidden" value="${materialRequestData.facilityId}"/>
<input name="accountSysId" id="accountSysId" type="hidden" value="${materialRequestData.accountSysId}"/>
<input name="containsFillUpBulk" id="containsFillUpBulk" type="hidden" value="${materialRequestData.containsFillUpBulk}"/>
<input name="currencyId" id="currencyId" type="hidden" value="${materialRequestData.currencyId}"/>
<input name="shipToCompanyId" id="shipToCompanyId" type="hidden" value="${materialRequestData.shipToCompanyId}"/>
<input name="addressLine1" id="addressLine1" type="hidden" value="${materialRequestData.addressLine1}"/>
<input name="addressLine2" id="addressLine2" type="hidden" value="${materialRequestData.addressLine2}"/>
<input name="addressLine3" id="addressLine3" type="hidden" value="${materialRequestData.addressLine3}"/>
<input name="city" id="city" type="hidden" value="${materialRequestData.city}"/>
<input name="stateAbbrev" id="stateAbbrev" type="hidden" value="${materialRequestData.stateAbbrev}"/>
<input name="zip" id="zip" type="hidden" value="${materialRequestData.zip}"/>
<input name="countryAbbrev" id="countryAbbrev" type="hidden" value="${materialRequestData.countryAbbrev}"/>
<input name="allowOneTimeShipTo" id="allowOneTimeShipTo" type="hidden" value="${materialRequestData.allowOneTimeShipTo}"/>
<input name="updatable" id="updatable" type="hidden" value="${materialRequestData.updatable}"/>
<input name="oneTimeShipToChanged" id="oneTimeShipToChanged" type="hidden" value="N"/>
<input name="canEditMr" id="canEditMr" type="hidden" value="${materialRequestData.canEditMr}"/>
<input name="canEditMrQty" id="canEditMrQty" type="hidden" value="${materialRequestData.canEditMrQty}"/>
<input name="canMaintainOrderHeader" id="canMaintainOrderHeader" type="hidden" value="${materialRequestData.orderMaintenanceHeaderPermission}"/>
<input name="useApprovalType" id="useApprovalType" type="hidden" value="${materialRequestData.useApprovalType}"/>
<input name="viewType" id="viewType" type="hidden" value="${materialRequestData.viewType}"/>
<input name="prStatus" id="prStatus" type="hidden" value="${materialRequestData.prStatus}"/>
<input name="canRequestCancellation" id="canRequestCancellation" type="hidden" value="${materialRequestData.canRequestCancellation}"/>
<input name="tcmISError" id="tcmISError" type="hidden" value="${tcmISError}"/>
<input name="chargeNumberApprovalNeeded" id="chargeNumberApprovalNeeded" type="hidden" value="${chargeNumberApprovalNeeded}"/>
<input name="showMrAllocationOption" id="showMrAllocationOption" type="hidden" value="${materialRequestData.showMrAllocationOption}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="hideCatalogPrice" id="hideCatalogPrice" value="<c:out value="${tcmis:isFeatureReleased(personnelBean,'HideCatalogPrice', materialRequestData.facilityId)}" escapeXml="false"/>"/>
<input type="hidden" name="allocateByChargeNumber" id="allocateByChargeNumber" value="<c:out value="${tcmis:isFeatureReleased(personnelBean,'AllocateByChargeNumber', materialRequestData.facilityId)}" escapeXml="false"/>"/>
<input name="showOnHoldForProgram" id="showOnHoldForProgram" type="hidden" value="${showOnHoldForProgram}"/>
<input name="canSetMrOnHoldForProgram" id="canSetMrOnHoldForProgram" type="hidden" value="${canSetMrOnHoldForProgram}"/>
<input type="hidden" name="lastName" id="lastName" value="${personnelBean.lastName}"/> 
<input type="hidden" name="firstName" id="firstName" value="${personnelBean.firstName}"/>
<input type="hidden" name="originalOrderName" id="originalOrderName" value="" />
<input type="hidden" name="reorderMRFeature" id="reorderMRFeature" value="<c:out value="${tcmis:isCompanyFeatureReleased(personnelBean, 'ReorderMR', materialRequestData.facilityId, materialRequestData.companyId)}"/>"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>