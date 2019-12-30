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

<tcmis:fontSizeCss />

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/customConfirm.js" language="JavaScript"></script>
<script src="/js/common/resultiframeresize.js" language="JavaScript"></script>

<!-- For Calendar support -->
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script src="/js/supply/buyorders.js" language="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 

<title>
<fmt:message key="buyorders.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
blanketOrderMessage:"<fmt:message key="buyorders.label.blanketorderavailable"/>",
swaPoMessage:"<fmt:message key="buyorders.label.swasamepo"/>",
dbuyMessage:"<fmt:message key="buyorders.label.dbuywarning"/>",
noRowSelected:"<fmt:message key="label.norowselected"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
showsuggestedsupplier:"<fmt:message key="label.showsuggestedsupplier"/>",
showitempurchasehistory:"<fmt:message key="label.showitempurchasehistory"/>",
showdeliveryschedule:"<fmt:message key="label.showdeliveryschedule"/>",
mismatchcomments:"<fmt:message key="label.mismatchcomments"/>",
viewpurchaseorder:"<fmt:message key="label.viewpurchaseorder"/>",
purchaseorder:"<fmt:message key="label.purchaseorder"/>",    
showblankets:"<fmt:message key="label.showblankets"/>",   
surplusinventory:"<fmt:message key="surplusinventory.title"/>",    
showbuyordertransfer:"<fmt:message key="label.showbuyordertransfer"/>",
createPoForSupplier:"<fmt:message key="label.createpoforsupplier"/>",
createPoWithNoSupplier:"<fmt:message key="label.createpowithnosupplier"/>",
noSupplier:"<fmt:message key="label.nosupplier"/>",
cannotCombineEdiWbuyOrders:"<fmt:message key="label.cannotcombineediwbuyorders"/>",
createPoMessage:"<fmt:message key="buyorders.label.createpoerror"/>",
itemNotes:"<fmt:message key="label.itemnotes"/>",
shipinfo:"<fmt:message key="label.shipinfo"/>",		
supplieritemnotes:"<fmt:message key="supplieritemnotes.title"/>",
leadtimeplots:"<fmt:message key="label.leadtimeplotpart1"/>",
quickview:"<fmt:message key="quickQuote"/>",
showsourcinginfo:"<fmt:message key="label.editsourcinginfo"/>",
continueMsg:"<fmt:message key="label.continue"/>",
talktoexpertreview:"<fmt:message key="label.talktoexpertreview"/>",
expertReviewConfirmMsg:"<fmt:message key="label.annexxivconfirmmsg"/>",
buyorder:"<fmt:message key="label.bonumber"/>",
showsourcinginfo:"<fmt:message key="label.preferredsupplierpricing"/>",
continueMsg:"<fmt:message key="label.continue"/>",
talktoexpertreview:"<fmt:message key="label.talktoexpertreview"/>",
expertReviewConfirmMsg:"<fmt:message key="label.annexxivconfirmmsg"/>",
buyorder:"<fmt:message key="label.bonumber"/>"}

    with(milonic=new menuname("buyOrdersMenu")){
         top="offset=2"
         style = contextStyle;
         margin=3
         aI("text=<fmt:message key="label.showsuggestedsupplier"/>;url=javascript:getSuggestedSupplier();"); 
         aI("text=<fmt:message key="label.showitempurchasehistory"/>;url=javascript:showItemTcmBuys();");
		 aI("text=<fmt:message key="label.itemnotes"/>;url=javascript:editItemNotes();");
		 aI("text=<fmt:message key="label.shipinfo"/>;url=javascript:showShippingInfo();");
		 aI("text=<fmt:message key="label.editsourcinginfo"/>;url=javascript:showSourcingInfo();");
    }
    
    drawMenus();
var titles = {
		1:'<fmt:message key="label.purchasingnote"/>',
		2:'<fmt:message key="label.externallinenote"/>',// use status.current.notes or status.current.notesShort 
		3:'<fmt:message key="label.internallinenote"/>',
		4:'<fmt:message key="label.shiptonote"/>'
	};


    function showAllNotes(selectedRowId,ind)  
    { 
      var comments = document.getElementById("note"+ind+"_"+ selectedRowId).value;
      var title =  titles[ind] +" : "+ $v('prNumber'+selectedRowId);
      parent.showAllNotes(comments, title);  
    }
        
// -->
</script>
</head>

<c:if test="${buyPageViewBeanCollection != null}">
<bean:size collection="${buyPageViewBeanCollection}" id="resultSize"/>
</c:if>
<body bgcolor="#ffffff" onload="myOnload('<c:out value="${resultSize}"/>', '<c:out value="${requestScope.poNumber}"/>');">

<tcmis:form action="/buyordersresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links. Set the variable you use in javascript to true.-->
 <script type="text/javascript">
 <!--
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder">
  showUpdateLinks = true;
  showCreatePoResultLink = true;
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrderNotes">
  showUpdateLinks = true;
</tcmis:inventoryGroupPermission>

 <tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrderChangeSupplyPath"  inventoryGroup="${param.inventoryGroup}">
  showConvertLinks = true;
  </tcmis:inventoryGroupPermission>
  //-->
  </script>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:if test="${MaxDataFilled eq 'True'}">
        <fmt:message key="label.maxdata">
            <fmt:param value="${param.maxData}"/>
        </fmt:message>
    </c:if>
</div>

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
	<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
parent.messagesData.errors = "<fmt:message key="label.errors"/>";
<c:if test="${not empty MaxDataFilled}">
	showErrorMessage = true;
	parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
</c:if>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${buyPageViewBeanCollection != null}" >
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
   <c:if test="${!empty buyPageViewBeanCollection}" >
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">


    <c:forEach var="buyPageViewBean" items="${buyPageViewBeanCollection}" varStatus="status">

    <c:if test="${status.index % 5 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="2%"><fmt:message key="label.grab"/><br>(<fmt:message key="label.bpo"/>)</th>
    <th width="5%"><fmt:message key="label.prnumber"/></th>
    <th width="5%"><fmt:message key="label.prcreated"/></th>
    <th width="5%"><fmt:message key="label.shipto"/></th>
    <th width="3%"><fmt:message key="label.inventorygroup"/></th>
     <th width="5%"><fmt:message key="label.partnumber"/></th>
	 <th width="5%"><fmt:message key="label.specs"/></th>
	 <th width="3%"><fmt:message key="label.item"/></th>
    <th width="10%"><fmt:message key="label.itemdescription"/></th>
    <th width="3%"><fmt:message key="label.type"/></th>
    <th width="3%"><fmt:message key="label.buytype"/></th>
    <th width="3%"><fmt:message key="label.buyquantity"/></th>
    <th width="5%"><fmt:message key="label.uom"/></th>
    <th width="5%"><fmt:message key="label.needed"/></th>
    <th width="5%"><fmt:message key="label.custpromisedate"/></th>
    <th width="5%"><fmt:message key="label.buyer"/></th>
    <th width="5%"><fmt:message key="label.status"/></th>
    <th width="3%"><fmt:message key="label.buyordernotes"/></th>
	<th width="3%"><fmt:message key="label.po"/></th>
	<th width="15%"><fmt:message key="label.purchasingnote"/></th>
    <th width="7%"><fmt:message key="label.manufacturer"/></th>
    <th width="3%"><fmt:message key="label.preferredsupplier"/><br>(<fmt:message key="label.selectsupplier"/>)</th>
    <th width="7%"><fmt:message key="label.lastsupplier"/></th>
    <th width="5%"><fmt:message key="label.company"/></th>
    <th width="5%"><fmt:message key="label.facility"/></th>
    <th width="3%"><fmt:message key="label.mrline"/></th>
    <th width="3%"><fmt:message key="label.mrquantity"/></th>
    <th width="8%"><fmt:message key="label.requestor"/><br><fmt:message key="label.phone"/><br><fmt:message key="label.email"/></th>
    <th width="5%"><fmt:message key="label.csr"/></th>
    <th width="5%"><fmt:message key="label.clientpo"/></th>
    <th width="15%"><fmt:message key="label.externallinenote"/></th>
    <th width="15%"><fmt:message key="label.internallinenote"/></th>
    <th width="15%"><fmt:message key="label.shiptonote"/></th>
    <th width="3%"><fmt:message key="label.mm"/></th>
    <th width="3%"><fmt:message key="label.reorderpoint"/></th>
    <th width="3%"><fmt:message key="label.inventory"/></th>
    <th width="3%"><fmt:message key="label.openpoquantity"/></th>
    <th width="5%"><fmt:message key="label.date.created"/></th>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>


     <c:if test="${status.current.priority == 1}" >
      <c:set var="colorClass" value='red'/>
     </c:if>
     <c:if test="${status.current.priority == 2}" >
      <c:set var="colorClass" value='yellow'/>
     </c:if>
     <c:if test="${status.current.priority == 3}" >
      <c:set var="colorClass" value='green'/>
     </c:if>

     <c:if test="${status.current.critical == 'Y' || status.current.critical == 'y'}" >
      <c:set var="colorClass" value='red'/>
     </c:if>
     <c:if test="${status.current.critical == 'S' || status.current.critical == 'L'}" >
      <c:set var="colorClass" value='pink'/>
     </c:if>
     <c:if test="${status.current.itemItemType == 'MX'}">
      <c:set var="colorClass" value='lightgray'/>
     </c:if>

     <c:if test="${status.current.engineeringEvaluation == 'Y' || status.current.engineeringEvaluation == 'y'}" >
      <c:set var="colorClass" value='purple'/>
     </c:if>

	  <c:if test="${status.current.cancelStatus == 'rqcancel' ||
                   status.current.cancelStatus == 'canceled' ||
                   status.current.requestLineStatus == 'Cancelled' ||
                   status.current.requestLineStatus == 'Pending Cancellation' ||
                   status.current.status == 'Closed' ||
                   status.current.status == 'Cancel'}" >
        <c:set var="colorClass" value='black'/>
	  </c:if>

	 <c:set var="cancelMR" value="false"/>
	  <c:if test="${status.current.cancelStatus == 'rqcancel' ||
                   status.current.cancelStatus == 'canceled' ||
                   status.current.requestLineStatus == 'Cancelled' ||
                   status.current.requestLineStatus == 'Pending Cancellation'}" >
		  <c:set var="cancelMR" value="true"/>
	  </c:if>

	 <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow('${status.index}')">
	<input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" /> 
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].mrNumber" id="mrNumber<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.mrNumber}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].mrLineItem" id="mrLineItem<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.mrLineItem}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].partId" id="partId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.partId}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].requestId" id="requestId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.requestId}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].catalogId" id="catalogId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.catalogId}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].prNumber" id="prNumber<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.prNumber}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].itemId" id="itemId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.itemId}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].itemDesc" id="itemDesc<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.itemDesc}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].radianPo" id="radianPo<c:out value="${status.index}"/>" type="hidden" value="${status.current.radianPo}">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].bpoDetail" id="bpoDetail<c:out value="${status.index}"/>" type="hidden" value="${status.current.bpoDetail}">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].surplusInventory" id="surplusInventory<c:out value="${status.index}"/>" type="hidden" value="${status.current.surplusInventory}">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].consolidationAllowed" id="consolidationAllowed<c:out value="${status.index}"/>" type="hidden" value="${status.current.consolidationAllowed}">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].opsEntityId" id="opsEntityId<c:out value="${status.index}"/>" type="hidden" value="${status.current.opsEntityId}">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].inventoryGroup" id="inventoryGroup<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.inventoryGroup}"/>">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].inventoryGroupName" id="inventoryGroupName<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.inventoryGroupName}"/>">

     <td width="2%">
<c:set var="hasPermission" value="false"/>
<c:set var="hasPermissionChangeNote" value="false"/>
<c:set var="hasPermissionChangeSupplyPath" value="false"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasPermission" value="true"/>
<c:set var="hasPermissionChangeNote" value="true"/>
<c:choose>
<c:when test="${status.current.lockStatus == 'Y' ||                
                status.current.status == 'Confirmed' ||
                status.current.status == 'Closed' ||
                status.current.status == 'Cancel' ||
                status.current.status == 'ProblemDBuy' ||
                status.current.status == 'ProblemWBuy' ||
                status.current.status == 'InProgressWBuy' ||
                status.current.status == 'InProgressDBuy' ||
                status.current.branchPlant == null ||
                empty status.current.branchPlant ||
                status.current.inventoryGroup == null ||
                empty status.current.inventoryGroup ||
                status.current.frozen == 'Y'}">
	<c:set var="hasPermission" value="false"/> 
</c:when>
</c:choose>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrderNotes" inventoryGroup="${status.current.inventoryGroup}">
	<c:set var="hasPermissionChangeNote" value="true"/>
</tcmis:inventoryGroupPermission>

<%--
If the user has special permisisons, they can change DBuy/Wbuy buy orders to a different status to make it manual PO.
--%>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrderChangeSupplyPath" inventoryGroup="${status.current.inventoryGroup}">
	<c:set var="hasPermissionChangeSupplyPath" value="true"/>
  <c:choose>
  <c:when test="${status.current.status == 'WBuyReview' ||
                  status.current.status == 'NewWBuy' ||
                  status.current.status == 'ProblemDBuy' ||
                  status.current.status == 'ProblemWBuy' ||
                  status.current.status == 'InProgressWBuy' ||
                  status.current.status == 'InProgressDBuy'}">
    <c:set var="hasPermission" value="true"/> 
  </c:when>
  </c:choose>
</tcmis:inventoryGroupPermission>

<c:choose>
  <c:when test="${hasPermission == 'true'}">  
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].branchPlant" id="branchPlant<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.branchPlant}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].facilityId" id="facilityId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.facilityId}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].critical" id="critical<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.critical}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].homeCurrencyId" id="homeCurrencyId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.homeCurrencyId}"/>">      

	<c:if test="${(status.current.radianPo == null || empty status.current.radianPo) && !cancelMR}">
		<c:set var="clickAction" value="checkRowSelection"/>
		<c:if test="${status.current.reachAnnexXiv}">
			<c:set var="clickAction" value="checkRowSelectionIntercept"/>
		</c:if>
		<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].rowNumber" id="rowNumber<c:out value="${status.index}"/>" type="checkbox" value="<c:out value="${status.index}"/>" onclick="${clickAction}(<c:out value="${status.index}"/>,'<c:out value="${status.current.bpoDetail}"/>');" <c:out value="${status.current.itemItemType eq 'MX'?'disabled':''}"/>>
	</c:if>

 </c:when>
</c:choose>     


	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].shipToLocationId" id="shipToLocationId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.shipToLocationId}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].shipToCompanyId" id="shipToCompanyId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.shipToCompanyId}"/>">
	<fmt:formatDate var="formattedNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].needDate" id="needDate<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${formattedNeedDate}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].orderQuantity" id="orderQuantity<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.orderQuantity}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].companyId" id="companyId<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.companyId}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].supplyPath" id="supplyPath<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.supplyPath}"/>">
	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].customerPoNumber" id="customerPoNumber<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.raytheonPo}"/>">
	
<c:if test="${status.current.bpoDetail == 'Y'}">
	<br>
	<%--
	<a href="" onclick="showOpenBpo('<c:out value="${status.current.prNumber}"/>'); return false;">blanket</a>
	--%>
	<fmt:message key="label.blanket"/>
</c:if>

<c:if test="${status.current.surplusInventory == 'Y'}">
	<br>
	<%--
	<a href="" onclick="showSurplusInventory('<c:out value="${status.current.itemId}"/>'); return false;">Surplus</a>
	--%>
	<fmt:message key="label.surplus"/>
</c:if>
     </td>

	  <fmt:formatDate var="formattedDateIssued" value="${status.current.dateIssued}" pattern="${dateTimeFormatPattern}"/>
	  <td width="2%"><c:out value="${status.current.prNumber}"/></td>
	  <td width="3%"><c:out value="${formattedDateIssued}"/>
     </td>
     <td width="5%"><c:out value="${status.current.shipToLocationDesc}"/></td>
<td width="3%">
		  <c:out value="${status.current.inventoryGroupName}"/>
		  <%--
		  <c:if test="${status.current.consolidationAllowed == 'Y'}">
			<a href="#" onclick="showBuyOrderTransfer('<c:out value="${status.current.prNumber}"/>','<c:out value="${status.current.shipToLocationId}"/>','<c:out value="${status.current.inventoryGroup}"/>','<c:out value="${status.current.companyId}"/>','transferRoute','<c:out value="${status.current.itemId}"/>','<c:out value="${status.current.itemType}"/>','<c:out value="${status.current.orderQuantity}"/>'); return false;">
		  </c:if>
			 (<c:out value="${status.current.inventoryGroupName}"/>)
			 <c:if test="${status.current.consolidationAllowed == 'Y'}">
			 </a>
		  </c:if>
	  		--%>
     </td>
	  <td width="5%">
		<%--
		<a href="#" onclick="getSuggestedSupplier('<c:out value="${status.current.partId}"/>','<c:out value="${status.current.requestId}"/>','<c:out value="${status.current.catalogId}"/>'); return false;">
			<c:out value="${status.current.partId}"/>
		</a>
		--%>
		<c:out value="${status.current.partId}"/>  
	  </td>
	  <td width="5%">
		<c:out value="${status.current.specList}"/>
	  </td>
	  <td width="3%">
		  <%--
		<a href="#" onclick="showItemTcmBuys('<c:out value="${status.current.itemId}"/>','<c:out value="${status.current.shipToLocationId}"/>'); return false;">
		<c:out value="${status.current.itemId}"/>
		</a>
		<br>
		<input type="image" name="itemNotes" src="/images/ponotes.gif" alt="Item Notes" onclick="editItemNotes('<c:out value="${status.current.itemId}"/>'); return false;" border="0">
		--%>
		<c:out value="${status.current.itemId}"/>
		<c:if test="${status.current.stocked == 'Y'}">
			<br>(<fmt:message key="label.stocked"/>)
		</c:if>
     </td>
     <td width="15%" title="${status.current.itemDesc}">
        <c:choose>
         <c:when test="${status.current.branchPlant == 2124}">
	        <c:out value="${status.current.itemDesc}"/>
        </c:when>
        <c:otherwise>
                 ${status.current.itemDescShort}
        </c:otherwise>
        </c:choose>
     </td>
     <td width="3%"><c:out value="${status.current.itemType}"/></td>
     <td width="3%"><c:if test="${status.current.buyTypeFlag == 'Y'}"><c:out value="${status.current.buyType}"/></c:if></td>
     <td width="3%" align="right"><c:out value="${status.current.orderQuantity}"/></td>
     <td width="15%" title="${status.current.sizeUnit}">
         <c:choose>
         <c:when test="${status.current.branchPlant == 2124}">
	        <c:out value="${status.current.sizeUnit}"/>
        </c:when>
        <c:otherwise>
                 ${status.current.sizeUnitShort}
        </c:otherwise>
        </c:choose>
     </td>
	  <fmt:formatDate var="formattedNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
	 <td width="5%" nowrap><c:out value="${formattedNeedDate}"/>
		  <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].deliveryType" id="deliveryType[<c:out value="${status.index}"/>]" type="hidden" value="${status.current.deliveryType}">
		  <c:if test="${status.current.deliveryType == 'Y'}">
			  <br>(<fmt:message key="label.schedule"/>)
			  <%--
			  <a href="#" onclick="showSchedule('<c:out value="${status.current.mrNumber}"/>','<c:out value="${status.current.mrLineItem}"/>'); return false;"><fmt:message key="label.schedule"/></a>
			  --%>
	      </c:if>
	 </td>
	  <fmt:formatDate var="formattedPromiseDate" value="${status.current.promiseDate}" pattern="${dateFormatPattern}"/>
	 <td width="5%" nowrap><c:out value="${formattedPromiseDate}"/>
	 </td>
     <td width="5%">
<c:choose>
  <c:when test="${hasPermission == 'true'}">
			<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldBuyerId" id="oldBuyerId[<c:out value="${status.index}"/>]" type="hidden" value="<c:out value="${status.current.buyerId}"/>">

        <select name="buyOrdersInputBean[<c:out value="${status.index}"/>].buyerId" class="selectBox" id="buyerId<c:out value="${status.index}"/>">
			 <c:if test="${status.current.radianPo == null}">
				<option value=""></option>
			 </c:if>
			 <c:if test="${status.current.buyerId != null}">
				<option value="<c:out value="${status.current.buyerId}"/>" selected><c:out value="${status.current.buyerName}"/></option>
			 </c:if>
			 <c:set var="buyerBeanCollection" value='${status.current.buyerDropDown}'/>
			 <c:forEach var="buyerBean" items="${buyerBeanCollection}" varStatus="status1">
				  <c:if test="${status.current.buyerId != status1.current.buyerId}">
					  <option value="<c:out value="${status1.current.buyerId}"/>"><c:out value="${status1.current.buyerName}"/></option>
				  </c:if>
			 </c:forEach>
        </select>
  </c:when>
  <c:otherwise>
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldBuyerId" id="oldBuyerId[<c:out value="${status.index}"/>]" type="hidden" value="<c:out value="${status.current.buyerId}"/>">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].buyerId" id="buyerId<c:out value="${status.index}"/>" value="<c:out value="${status.current.buyerId}"/>" type="hidden">
    <c:out value="${status.current.buyerName}"/>
  </c:otherwise>
</c:choose>
     </td>
     <td width="5%">

<c:choose>
  <c:when test="${hasPermission == 'true'}">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldStatus" id="oldStatus[<c:out value="${status.index}"/>]" type="hidden" value="<c:out value="${status.current.status}"/>">
        <select name="buyOrdersInputBean[<c:out value="${status.index}"/>].status" class="selectBox" id="status<c:out value="${status.index}"/>" onchange="statusChanged('<c:out value="${status.index}"/>');">
			 	<c:if test="${status.current.status != null}">
					<option value="<c:out value="${status.current.status}"/>" selected><c:out value="${status.current.status}"/></option>
			 	</c:if>

				 <c:forEach var="statusBean" items="${vvBuyOrderStatusBeanCollection}" varStatus="status1">
					<c:if test="${status1.current.buypageAssignable == 'Y'}">
						<c:choose>
							<c:when test="${status.current.supplyPath == 'Dbuy' || status.current.supplyPath == 'Wbuy'}">
								<c:if test="${status.current.status != 'MMReview' && (status.current.supplyPath == status1.current.supplyPath ||
												(empty status1.current.supplyPath && hasPermissionChangeSupplyPath == 'true'))}">
								 <c:if test="${status.current.status != status1.current.status}">
									 <c:choose>
										<c:when test="${status1.current.status == 'Cancel'}">
												<c:if test="${cancelMR}">
													<c:if test="${status.current.radianPo == null || empty status.current.radianPo}">
														<option value="<c:out value="${status1.current.status}"/>"><c:out value="${status1.current.status}"/></option>
													</c:if>
												</c:if>
										</c:when>
										<c:otherwise>
											<option value="<c:out value="${status1.current.status}"/>"><c:out value="${status1.current.status}"/></option>
										</c:otherwise>
									 </c:choose>

								 </c:if>
							 	</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${status.current.supplyPath == status1.current.supplyPath ||
						  						  empty status1.current.supplyPath}">
									 <c:if test="${status.current.status != status1.current.status}">
										 <c:choose>
											<c:when test="${status1.current.status == 'Cancel'}">
													<c:if test="${cancelMR}">
														<c:if test="${status.current.radianPo == null || empty status.current.radianPo}">
															<option value="<c:out value="${status1.current.status}"/>"><c:out value="${status1.current.status}"/></option>
														</c:if>
													</c:if>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${status1.current.status == 'New'}">
														<c:if test="${(status.current.radianPo == null || empty status.current.radianPo) || status.current.status == 'RFQ' || status.current.status == 'Problem' || status.current.status == 'Client'}">
															<option value="<c:out value="${status1.current.status}"/>"><c:out value="${status1.current.status}"/></option>
														</c:if>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${status1.current.status}"/>"><c:out value="${status1.current.status}"/></option>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										 </c:choose>
									 </c:if>
					  	 		</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
                     
                 </c:forEach>
			 	<tcmis:opsEntityPermission indicator="true" userGroupId="closeBuyOrders">
					<c:if test="${status.current.radianPo == null || empty status.current.radianPo}">
                     <option value="Closed"><fmt:message key="label.close"/></option>
                   </c:if>
                 </tcmis:opsEntityPermission>
				 
        </select>
  </c:when>
  <c:otherwise>
	 <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldStatus" id="oldStatus[<c:out value="${status.index}"/>]" type="hidden" value="<c:out value="${status.current.status}"/>">
	 <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].status" id="status<c:out value="${status.index}"/>" value="<c:out value="${status.current.status}"/>" type="hidden">
    <c:out value="${status.current.status}"/>
  </c:otherwise>
</c:choose>
<c:if test="${status.current.status == 'ProblemDBuy'}">
<fmt:message key="label.mismatchcomments"/>
<%--
<a href="#" onclick="showMismatch('<c:out value="${status.current.radianPo}"/>'); return false;"><fmt:message key="label.mismatchcomments"/></a>
--%>
</c:if>
</td>

<td width="3%">
<c:choose>
  <c:when test="${hasPermissionChangeNote == 'true'}">
  <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldComments" id="oldComments[<c:out value="${status.index}"/>]" type="hidden" value="<c:out value="${status.current.comments}"/>">
  <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].prNumber" id="prNumber<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.prNumber}"/>">
  <textarea class="inputBox" name="buyOrdersInputBean[<c:out value="${status.index}"/>].comments" id="buyOrdersInputBean[<c:out value="${status.index}"/>].comments" rows="3" cols="25"><c:out value="${status.current.comments}"/></textarea>
  </c:when>
  <c:otherwise>
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldComments" id="oldComments[<c:out value="${status.index}"/>]" type="hidden" value="<c:out value="${status.current.comments}"/>">
    <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].comments" id="buyOrdersInputBean[<c:out value="${status.index}"/>].comments" value="<c:out value="${status.current.comments}"/>" type="hidden">
    <c:out value="${status.current.comments}"/>
  </c:otherwise>
</c:choose>
</td>

<td width="5%">
    <c:out value="${status.current.radianPo}"/>
    <%--
    <a href="#" onclick="showRadianPo('<c:out value="${status.current.radianPo}"/>'); return false;">
    <c:out value="${status.current.radianPo}"/>
    </a>
    --%>
</td>
<td width="15%">
	<c:if test="${!empty status.current.linePurchasingNote}">
     <img src="/images/buttons/plus.gif" alt="<c:out value="${status.current.linePurchasingNote}"/>" title="<c:out value="${status.current.linePurchasingNote}"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showAllNotes(${status.index},1)"/>
     <input type="hidden" id="note1_${status.index}" value="<c:out value="${status.current.linePurchasingNote}"/>" />
     </c:if>
</td>
<td width="7%"><c:out value="${status.current.mfgId}"/></td>
	
<td width="5%">
					<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].reasonId" id="reasonId_${status.index}" type="hidden" value="-1"/>
					<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].catalogPrice" id="catalogPrice_${status.index}" type="hidden" value="${status.current.catalogPrice}"/>
					<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].bestPriceDbuyContractId" id="bestPriceDbuyContractId_${status.index}" type="hidden" value=""/>
<c:choose>
  <c:when test="${hasPermission == 'true'}">
	  <c:choose>
			<c:when test="${status.current.radianPo == null}">
				<select name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" class="selectBox" id="poSupplierId<c:out value="${status.index}"/>" onclick="poSupplierClicked('${status.index}')" onchange="setPoSupplierName('${status.index}')">
					<c:choose>
						<c:when test="${status.current.preferredSupplier != null}">
							<c:choose>
							<c:when test="${status.current.selectedSupplier == null || status.current.preferredSupplier eq status.current.selectedSupplier}">
								<option value="<c:out value="${status.current.preferredSupplier}"/>" selected><c:out value="${status.current.preferredSupplierName}"/></option>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" value="${status.current.preferredSupplierName}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" value="${status.current.preferredSupplierName}" type="hidden"/>
							</c:when>
							<c:otherwise>
								<!-- <option value="<c:out value="${status.current.preferredSupplier}"/>"><c:out value="${status.current.preferredSupplierName}"/></option> -->
							    <option value="<c:out value="${status.current.selectedSupplier}"/>" selected><c:out value="${status.current.selectedSupplierName}"/></option>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
							</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<option value="<c:out value="${status.current.selectedSupplier}"/>" selected><c:out value="${status.current.selectedSupplierName}"/></option>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" id="buyOrdersInputBean[<c:out value="${status.index}"/>].oldPoSupplierName" value="${status.current.selectedSupplierName}" type="hidden"/>
						</c:otherwise>
					</c:choose>
				</select>
					<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="loadPoSupplierB<c:out value="${status.index}"/>" id="loadPoSupplierB<c:out value="${status.index}"/>" value="<fmt:message key="label.more"/>" onclick="poSupplierClicked('${status.index}')"/>
					<c:if test="${status.current.activeDbuyCount >1 }">
						<a href="#" onclick="showSourcingInfo(<c:out value="${status.index}"/>);">***</a>
					</c:if>
					
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
	   <br>
		<c:choose>
			 <c:when test="${status.current.radianPo == null}">
				<span id="poSelectedSupplierName<c:out value="${status.index}"/>">
					&nbsp;
				</span>
			 </c:when>
			 <c:otherwise>
				<span id="poSelectedSupplierName<c:out value="${status.index}"/>">
					(${status.current.currentSupplierName})
				</span>
			 </c:otherwise>
		</c:choose>
  </c:when>
  <c:otherwise>
    &nbsp;
	 <input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" id="poSupplierId[<c:out value="${status.index}"/>]" type="hidden" value="">
  </c:otherwise>
</c:choose>
</td>

     <td width="7%"><c:out value="${status.current.lastSupplierName}"/></td>

     <td width="5%"><c:out value="${status.current.companyId}"/></td>

	  <c:choose>
		  <c:when test="${status.current.facility == ' ; '}">
			 <td width="5%">&nbsp;</td>
		  </c:when>
		  <c:otherwise>
			 <td width="5%"><c:out value="${status.current.facility}"/></td>
		  </c:otherwise>
	  </c:choose>

     <td width="3%">
<c:if test="${status.current.mrNumber != null}">
<c:out value="${status.current.mrNumber}"/>-<c:out value="${status.current.mrLineItem}"/>
</c:if>
     </td>
     <td width="3%" align="right"><c:out value="${status.current.mrQuantity}"/></td>
     <td width="8%"><c:out value="${status.current.requestorFirstName}"/> <c:out value="${status.current.requestorLastName}"/><br><c:out value="${status.current.phone}"/><br><c:out value="${status.current.email}"/></td>
     <td width="5%"><c:out value="${status.current.csrName}"/></td>
     <td width="3%"><c:out value="${status.current.raytheonPo}"/></td>
     <td width="15%" title="<c:out value="${status.current.notes}"/>">
     	<c:if test="${!empty status.current.notes}">
     <img src="/images/buttons/plus.gif" alt="<c:out value="${status.current.notes}"/>" title="<c:out value="${status.current.notes}"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showAllNotes(${status.index},2)"/>
     <input type="hidden" id="note2_${status.index}" value="<c:out value="${status.current.notes}"/>" />
     	</c:if>
     </td>
     <td width="15%">
     	<c:if test="${!empty status.current.lineInternalNote}">
     <img src="/images/buttons/plus.gif" alt="<c:out value="${status.current.lineInternalNote}"/>" title="<c:out value="${status.current.lineInternalNote}"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showAllNotes(${status.index},3)"/>
     <input type="hidden" id="note3_${status.index}" value="<c:out value="${status.current.lineInternalNote}"/>" />
     	</c:if>
     </td>
     <td width="15%">
     	<c:if test="${!empty status.current.shiptoNote}">
     <img src="/images/buttons/plus.gif" alt="<c:out value="${status.current.shiptoNote}"/>" title="<c:out value="${status.current.shiptoNote}"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showAllNotes(${status.index},4)"/>
     <input type="hidden" id="note4_${status.index}" value="<c:out value="${status.current.shiptoNote}"/>" />
     	</c:if>
     </td>
     <td width="3%" align="right"><c:out value="${status.current.stockingLevel}"/></td>
     <td width="3%" align="right"><c:out value="${status.current.reorderPoint}"/></td>
     <td width="3%" align="right"><c:out value="${status.current.availableQuantity}"/></td>
     <td width="3%" align="right"><c:out value="${status.current.openPoQuantity}"/></td>
     
     <fmt:formatDate var="formattedReleaseDate" value="${status.current.releaseDate}" pattern="${dateTimeFormatPattern}"/>
	 <td width="5%" nowrap><c:out value="${formattedReleaseDate}"/></td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty buyPageViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>


<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="action" id="action" value="" type="hidden">
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<input name="branchPlant" id="branchPlant" value='<tcmis:jsReplace value="${param.branchPlant}"/>' type="hidden">
<input name="inventoryGroup" id="inventoryGroup" value='<tcmis:jsReplace value="${param.inventoryGroup}"/>' type="hidden">
<input name="facilityId" id="facilityId" value='<tcmis:jsReplace value="${param.facilityId}"/>' type="hidden">
<c:set var="selectedCompanyIdArray" value='${paramValues.companyIdArray}'/>
<select name="companyIdArray" id="companyIdArray" class="selectBox" multiple size="5">
<c:forEach items="${selectedCompanyIdArray}" varStatus="status1">
 <c:set var="selectedCompanyId" value='${selectedCompanyIdArray[status1.index]}'/>
  <option value="<c:out value="${selectedCompanyId}"/>" selected><c:out value="${selectedCompanyId}" escapeXml="false"/></option>
</c:forEach>
<input name="buyerId" id="buyerId" value='<tcmis:jsReplace value="${param.buyerId}"/>' type="hidden">
<input name="searchWhat" id="searchWhat" value='<tcmis:jsReplace value="${param.searchWhat}"/>' type="hidden">
<input name="searchType" id="searchType" value='<tcmis:jsReplace value="${param.searchType}"/>' type="hidden">
<input name="searchText" id="searchText" value='<tcmis:jsReplace value="${param.searchText}"/>' type="hidden">
<c:set var="selectedStatusArray" value='${paramValues.statusArray}'/>
<select name="statusArray" id="statusArray" class="selectBox" multiple size="5">
<c:forEach items="${selectedStatusArray}" varStatus="status1">
 <c:set var="selectedStatus" value='${selectedStatusArray[status1.index]}'/>
  <option value="<c:out value="${selectedStatus}"/>" selected><c:out value="${selectedStatus}" escapeXml="false"/></option>
</c:forEach>
<input name="supplyPath" id="supplyPath" value='<tcmis:jsReplace value="${param.supplyPath}"/>' type="hidden">
<input name="sortBy" id="sortBy" value='<tcmis:jsReplace value="${param.sortBy}"/>' type="hidden">
<input name="boForConfirmedPo" id="boForConfirmedPo" value='<tcmis:jsReplace value="${param.boForConfirmedPo}"/>' type="hidden">
<input name="boForUnconfirmedPo" id="boForUnconfirmedPo" value='<tcmis:jsReplace value="${param.boForUnconfirmedPo}"/>' type="hidden">
<input name="boWithNoPo" id="boWithNoPo" value='<tcmis:jsReplace value="${param.boWithNoPo}"/>' type="hidden">
<input name="confirmedButNoAssociation" id="confirmedButNoAssociation" value='<tcmis:jsReplace value="${param.confirmedButNoAssociation}"/>' type="hidden">
<input name="selectedSupplierForPo" id="selectedSupplierForPo" value="" type="hidden">
<input name="selectedSupplierForPoName" id="selectedSupplierForPoName" value="" type="hidden">
<input name="lastSelectedPr" id="lastSelectedPr" value='<tcmis:jsReplace value="${param.lastSelectedPr}"/>' type="hidden">
<input name="maxData" id="maxData" type="hidden" value='<tcmis:jsReplace value="${param.maxData}"/>'/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>