<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


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

<link rel="stylesheet" type="text/css" href="/css/haasMenu.css"></link>
<link rel="stylesheet" type="text/css" href="/css/tabs.css"></link>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%--<script src="/js/common/formchek.js" language="JavaScript"></script>--%>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/tabs.js" language="JavaScript"></script>
<script src="/js/common/google.js" language="JavaScript"></script>

<script src="/js/common/sessiontimeout.js" language="JavaScript"></script>
<%--<script src="/js/common/common_misc.js" language="JavaScript"></script>--%>
<%--<script src="/js/common/common_util.js" language="JavaScript"></script>
<script src="/js/common/common_error.js" language="JavaScript"></script>--%>
<%--<script src="/js/common/cnt.js" language="JavaScript"></script>--%>

<script src="/js/common/applicationIframe.js" language="JavaScript"></script>
<script src="/js/version.js" language="JavaScript"></script>

<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/applicationcontextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<!-- Add any other javascript you need for the page here -->


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

<c:set var="companyName" value='${sessionScope.personnelBean.companyName}'/>
<c:set var="module"><tcmis:module /></c:set>
<c:set var="firstname" value='${personnelBean.firstName}'/>
<c:set var="lastname" value='${personnelBean.lastName}'/>
<c:set var="personnelId" value='${personnelBean.personnelId}'/>

<title>
<c:if test="${personnelBean.companyId != 'BOEING_CO'}"> <c:out value="${companyName}" /> </c:if><fmt:message key="label.tcmis"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

//Sending GA to capture geograaphic, browser data
ga('create', 'UA-100598449-1', 'auto');
ga('send', 'pageview');

//Ensure this frame is the parent frame
<c:if test="${!personnelBean.standalone && module != 'usgov' && param.redirectToHome != 'Y' && module != 'customer'}">
if (!window.name.endsWith('TcmisApplication')) {
	top.location='home.do';
}
</c:if>
if (top!=self) {
  top.location=self.document.location;
}

<c:if test="${param.redirectToHome == 'Y'}">window.name = '_redirectToHomeTcmisApplication';</c:if>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
timeoutmessage1:"<fmt:message key="label.timeoutmessage1"/>",timeoutmessage2:"<fmt:message key="label.timeoutmessage2"/>",
timeoutmessage3:"<fmt:message key="label.timeoutmessage3"/>",timeoutmessage4:"<fmt:message key="label.timeoutmessage4"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var pageData = new Array();
pageData["sourcing"]="<fmt:message key="sourcing"/>";
pageData["customerCatalog"]="<fmt:message key="customerCatalog"/>";
pageData["seagateLabelInfo"]="<fmt:message key="seagateLabelInfo"/>";
pageData["customerPurchaseOrders"]="<fmt:message key="customerPurchaseOrders"/>";
pageData["customerBuyOrders"]="<fmt:message key="customerBuyOrders"/>";
pageData["wasteGeneration"]="<fmt:message key="wasteGeneration"/>";
pageData["wastePickupManagement"]="<fmt:message key="wastePickupManagement"/>";
pageData["wasteStorage"]="<fmt:message key="wasteStorage"/>";
pageData["wasteTracking"]="<fmt:message key="wasteTracking"/>";
pageData["userProfile"]="<fmt:message key="userProfile"/>";
pageData["batchReport"]="<fmt:message key="batchReport"/>";
pageData["sqlReport"]="<fmt:message key="sqlReport"/>";
pageData["dailyInventoryReport"]="<fmt:message key="dailyInventoryReport"/>";
pageData["invoiceInventoryDetail"]="<fmt:message key="invoiceInventoryDetail"/>";
pageData["opsInvoiceInventoryDetail"]="<fmt:message key="opsInvoiceInventoryDetail"/>";
pageData["cabinetInventory"]="<fmt:message key="cabinetInventory"/>";
pageData["stockedInventoryReport"]="<fmt:message key="stockedInventoryReport"/>";
pageData["costBookUsage"]="<fmt:message key="costBookUsage"/>";
pageData["costReport"]="<fmt:message key="costReport"/>";
pageData["passthroughReport"]="<fmt:message key="passthroughReport"/>";
pageData["formattedUsage"]="<fmt:message key="formattedUsage"/>";
pageData["catalogAddTracking"]="<fmt:message key="catalogAddTracking"/>";
pageData["formattedVocUsage"]="<fmt:message key="formattedVocUsage"/>";
pageData["formattedMonthlyVocUsage"]="<fmt:message key="formattedMonthlyVocUsage"/>";
pageData["adHocWaste"]="<fmt:message key="adHocWaste"/>";
pageData["adHocUsage"]="<fmt:message key="adHocUsage"/>";
pageData["workAreaUsage"]="<fmt:message key="workAreaUsage"/>";
pageData["receiving"]="<fmt:message key="receiving"/>";
pageData["receivingQc"]="<fmt:message key="receivingQc"/>";
pageData["repackaging"]="<fmt:message key="repackaging"/>";
pageData["newJacket"]="<fmt:message key="newJacket"/>";
pageData["oldJacket"]="<fmt:message key="oldJacket"/>";
pageData["newReceivingInformation"]="<fmt:message key="newReceivingInformation"/>";
pageData["oldReceivingInformation"]="<fmt:message key="oldReceivingInformation"/>";
pageData["rmaProcess"]="<fmt:message key="rmaProcess"/>";
pageData["picking"]="<fmt:message key="picking"/>";
pageData["pickingQc"]="<fmt:message key="pickingQc"/>";
pageData["shipConfirm"]="<fmt:message key="shipConfirm"/>";
pageData["shipmentHistory"]="<fmt:message key="shipmentHistory"/>";
pageData["customerReturns"]="<fmt:message key="customerReturns"/>";
pageData["genericBillOfLading"]="<fmt:message key="genericBillOfLading"/>";
pageData["itemCount"]="<fmt:message key="itemCount"/>";
pageData["itemManagement"]="<fmt:message key="itemManagement"/>";
pageData["binLabels"]="<fmt:message key="binLabels"/>";
pageData["reconciliation"]="<fmt:message key="reconciliation"/>";
pageData["logistics"]="<fmt:message key="logistics"/>";
pageData["logisticsNew"]="<fmt:message key="logisticsNew"/>";
pageData["levelManagement"]="<fmt:message key="levelManagement"/>";
pageData["transactions"]="<fmt:message key="transactions"/>";
pageData["minMaxLevels"]="<fmt:message key="minMaxLevels"/>";
pageData["cabinetLabels"]="<fmt:message key="cabinetLabels"/>";
pageData["cabinetDefinitions"]="<fmt:message key="cabinetDefinitions"/>";
pageData["cabinetScanning"]="<fmt:message key="cabinetScanning"/>";
pageData["cabinetManagement"]="<fmt:message key="cabinetManagement"/>";
pageData["cabinetCount"]="<fmt:message key="cabinetCount"/>";
pageData["forceBuy"]="<fmt:message key="forceBuy"/>";
pageData["allocationAnalysis"]="<fmt:message key="allocationAnalysis"/>";
pageData["purchaseOrder"]="<fmt:message key="purchaseOrder"/>";
pageData["printedPoList"]="<fmt:message key="printedPoList"/>";
pageData["searchPos"]="<fmt:message key="searchPos"/>";
pageData["newSupplierRequest"]="<fmt:message key="newSupplierRequest"/>";
pageData["ediOrderStatus"]="<fmt:message key="ediOrderStatus"/>";
pageData["supplierSearch"]="<fmt:message key="supplierSearch"/>";
pageData["binsToScan"]="<fmt:message key="binsToScan"/>";
pageData["hubUpload"]="<fmt:message key="hubUpload"/>";
pageData["peiProjectsList"]="<fmt:message key="peiProjectsList"/>";
pageData["newPeiProject"]="<fmt:message key="newPeiProject"/>";
pageData["accountsPayable"]="<fmt:message key="accountsPayable"/>";
pageData["supplierInvoiceReport"]="<fmt:message key="supplierInvoiceReport"/>";
pageData["supplierShipping"]="<fmt:message key="supplierShipping"/>";
pageData["wasteOrders"]="<fmt:message key="wasteOrders"/>";
pageData["wBuy"]="<fmt:message key="wBuy"/>";
pageData["buyerItemNotes"]="<fmt:message key="buyerItemNotes"/>";
pageData["dotShippingInfo"]="<fmt:message key="shippingInfo"/>";
pageData["msdsMaintenance"]="<fmt:message key="msdsMaintenance"/>";
pageData["catalogAddProcess"]="<fmt:message key="catalogAddProcess"/>";
pageData["msdsCheckOnline"]="<fmt:message key="msdsCheckOnline"/>";
pageData["generateItemSequence"]="<fmt:message key="generateItemSequence"/>";
pageData["catalog"]="<fmt:message key="catalog"/>";
pageData["orderTracking"]="<fmt:message key="orderTracking"/>";
pageData["dropshipReceiving"]="<fmt:message key="dropshipReceiving"/>";
pageData["workAreaManagement"]="<fmt:message key="workAreaManagement"/>";
pageData["customerLabeling"]="<fmt:message key="customerLabeling"/>";
pageData["msds"]="<fmt:message key="msds"/>";
pageData["newMsds"]="<fmt:message key="msds"/>";
pageData["advancedMsds"]="<fmt:message key="advancedMsds"/>";
pageData["millerMsds"]="<fmt:message key="millerMsds"/>";
pageData["priceQuoteHistory"]="<fmt:message key="priceQuoteHistory"/>";
pageData["requestPriceQuote"]="<fmt:message key="requestPriceQuote"/>";
pageData["priceQuotePage"]="<fmt:message key="priceQuotePage"/>";
pageData["home"]="<fmt:message key="home"/>";
pageData["haasInvoicePrint"]="<fmt:message key="haasInvoicePrint"/>";
pageData["transferRequest"]="<fmt:message key="transferRequest"/>";
pageData["itemCountScanSheet"]="<fmt:message key="itemCountScanSheet"/>";
pageData["specUpdate"]="<fmt:message key="specUpdate"/>";
pageData["tsdfWasteReceiving"]="<fmt:message key="tsdfWasteReceiving"/>";
pageData["tsdfContainerReport"]="<fmt:message key="tsdfContainerReport"/>";
pageData["wasteSubManifestReport"]="<fmt:message key="wasteSubManifestReport"/>";
pageData["deliveryScheduleAdmin"]="<fmt:message key="deliveryScheduleAdmin"/>";
pageData["launchGui"]="<fmt:message key="launchGui"/>";
pageData["itemCountScanUpload"]="<fmt:message key="itemCountScanUpload"/>";
pageData["formattedscaqmdreport"]="<fmt:message key="formattedscaqmdreport"/>";
pageData["bulletin"]="<fmt:message key="bulletin"/>";
pageData["shipInfo"]="<fmt:message key="shipInfo"/>";
pageData["noPickListPicking"]="<fmt:message key="noPickListPicking"/>";
pageData["hotShotFee"]="<fmt:message key="hotShotFee"/>";
pageData["consignmentReport"]="<fmt:message key="consignmentReport"/>";
pageData["distributedCount"]="<fmt:message key="distributedCount"/>";
pageData["newitemManagement"]="<fmt:message key="newitemManagement"/>";
pageData["scannerUpload"]="<fmt:message key="scannerUpload"/>";
pageData["usGovShipmentSelection"]="<fmt:message key="usGovShipmentSelection"/>";
pageData["usGovlabeling"]="<fmt:message key="usGovlabeling"/>";
pageData["freightAdvice"]="<fmt:message key="freightAdvice"/>";
pageData["packshipConfirm"]="<fmt:message key="packshipConfirm"/>";
pageData["carrierPicking"]="<fmt:message key="carrierPicking"/>";
pageData["iuclid"]="<fmt:message key="iuclid"/>";
//pageData["haasMsds"]="<fmt:message key="haasMsds"/>";
pageData["consignedInventoryReport"]="<fmt:message key="consignedInventoryReport"/>";
pageData["itemWeights"]="<fmt:message key="itemWeights"/>";
pageData["newCirJacket"]="<fmt:message key="newCirJacket"/>";
pageData["dlaGasOrderTracking"]="<fmt:message key="dlaGasOrderTracking"/>";
pageData["clientCabinetInventory"]="<fmt:message key="clientCabinetInventory"/>";
pageData["pack"]="<fmt:message key="pack"/>";
pageData["confirm"]="<fmt:message key="confirm"/>";
pageData["chemicalDashBoard"]="<fmt:message key="chemicalDashBoard"/>";
pageData["newBuyOrders"]="<fmt:message key="newBuyOrders"/>";
pageData["custInvReceiving"]="<fmt:message key="custInvReceiving"/>";
pageData["airgasUpload"]="<fmt:message key="airgasUpload"/>";
pageData["buyOrders"]="<fmt:message key="buyOrders"/>";
pageData["customerReceivingHistory"]="<fmt:message key="customerReceivingHistory"/>";
pageData["nboBuilder"]="<fmt:message key="nboBuilder"/>";
pageData["dodaacUpdate"]="<fmt:message key="dodaacUpdate"/>";
pageData["adHocMaterialmatrix"]="<fmt:message key="adHocMaterialmatrix"/>";
pageData["partNotes"]="<fmt:message key="partNotes"/>";
pageData["relaxer"]="<fmt:message key="relaxer"/>";
pageData["cabinetCloner"]="<fmt:message key="cabinetCloner"/>";
pageData["polchemOrderTracking"]="<fmt:message key="polchemOrderTracking"/>";
pageData["deliveries"]="<fmt:message key="deliveries"/>";
pageData["newRemitTo"]="<fmt:message key="newRemitTo"/>";
pageData["maxcom"]="<fmt:message key="maxcom"/>";
pageData["pwcResendResponse"]="<fmt:message key="pwcResendResponse"/>";
pageData["resendXBuy"]="<fmt:message key="resendXBuy"/>";
pageData["printerLocation"]="<fmt:message key="printerLocation"/>";
pageData["openPos"]="<fmt:message key="openPos"/>";
pageData["newcatalog"]="<fmt:message key="newcatalog"/>";
pageData["catalogFileUpLoad"]="<fmt:message key="catalogFileUpLoad"/>";
pageData["listPolishPoJackets"]="<fmt:message key="listPolishPoJackets"/>";
pageData["itemHarmonizedCode"]="<fmt:message key="itemHarmonizedCode"/>";
pageData["inventoryByReceipt"]="<fmt:message key="inventoryByReceipt"/>";
pageData["shelfLifeForcast"]="<fmt:message key="shelfLifeForcast"/>";
pageData["noSales"]="<fmt:message key="noSales"/>";
pageData["openPicks"]="<fmt:message key="openPicks"/>";
pageData["nWeekInv"]="<fmt:message key="nWeekInv"/>";
pageData["excessInventory"]="<fmt:message key="excessInventory"/>";
pageData["salesOrders"]="<fmt:message key="salesOrders"/>";
pageData["orderEntry"]="<fmt:message key="orderEntry"/>";
pageData["materialMatrix"]="<fmt:message key="materialMatrix"/>";
pageData["customerReceiptDocuments"]="<fmt:message key="customerReceiptDocuments"/>";
pageData["newShipComfirm"]="<fmt:message key="newShipComfirm"/>";
pageData["transfers"]="<fmt:message key="transfers"/>";
pageData["inventoryAdjustments"]="<fmt:message key="inventoryAdjustments"/>";
pageData["customerReturnTracking"]="<fmt:message key="customerReturnTracking"/>";
pageData["customerSearch"]="<fmt:message key="customerSearch"/>";
pageData["customerRequest"]="<fmt:message key="customerRequest"/>";
pageData["mrRelease"]="<fmt:message key="mrRelease"/>";
pageData["priceList"]="<fmt:message key="priceList"/>";
pageData["freightTracking"]="<fmt:message key="freightTracking"/>";
pageData["truckloadFreight"]="<fmt:message key="truckloadFreight"/>";
pageData["supplierPriceList"]="<fmt:message key="supplierPriceList"/>";
pageData["binManagement"]="<fmt:message key="binManagement"/>";
pageData["mrHistory"]="<fmt:message key="mrHistory"/>";
pageData["invoicePrint"]="<fmt:message key="invoicePrint"/>";
pageData["customerOrderTracking"]="<fmt:message key="customerOrderTracking"/>";
pageData["usgovDlaGasOrderTracking"]="<fmt:message key="usgovDlaGasOrderTracking"/>";
pageData["databaseObjects"]="<fmt:message key="databaseObjects"/>";
pageData["newEdiOrderStatus"]="<fmt:message key="newEdiOrderStatus"/>";
pageData["supplierItemNotes"]="<fmt:message key="supplierItemNotes"/>";
pageData["itemLookup"]="<fmt:message key="itemLookup"/>";
pageData["catalogItemSynonym"]="<fmt:message key="catalogItemSynonym"/>";
pageData["forceCabinetCount"]="<fmt:message key="forceCabinetCount"/>";
pageData["additionalChargeMain"]="<fmt:message key="additionalChargeMain"/>";
pageData["mrCancellation"]="<fmt:message key="mrCancellation"/>";
pageData["inventoryGroupDefinition"]="<fmt:message key="inventoryGroupDefinition"/>";
pageData["itemConsignment"]="<fmt:message key="itemConsignment"/>";
pageData["checkLog"]="<fmt:message key="checkLog"/>";
pageData["pageReleaseLog"]="<fmt:message key="pageReleaseLog"/>";
pageData["customerPartManagement"]="<fmt:message key="customerPartManagement"/>";
pageData["clientCabinetDefinition"]="<fmt:message key="clientCabinetDefinition"/>";
pageData["clientForceCabinetCount"]="<fmt:message key="clientForceCabinetCount"/>";
pageData["clientCabinetManagement"]="<fmt:message key="clientCabinetManagement"/>";
pageData["clientCabinetScanning"]="<fmt:message key="clientCabinetScanning"/>";
pageData["newClientCabinetInventory"]="<fmt:message key="newClientCabinetInventory"/>";
pageData["orderEntryLookup"]="<fmt:message key="orderEntryLookup"/>";
pageData["clientCabinetLabel"]="<fmt:message key="clientCabinetLabel"/>";
pageData["clientCabinetCount"]="<fmt:message key="clientCabinetCount"/>";
pageData["itemDirectSupply"]="<fmt:message key="itemDirectSupply"/>";
pageData["clientCabinetDetail"]="<fmt:message key="clientCabinetDetail"/>";
pageData["dBuyConsolidationFreq"]="<fmt:message key="dBuyConsolidationFreq"/>";
pageData["clientConsignedInventoryReport"]="<fmt:message key="clientConsignedInventoryReport"/>";
pageData["consignmentProcessingUpload"]="<fmt:message key="consignmentProcessingUpload"/>";
pageData["customerPOUpload"]="<fmt:message key="customerPOUpload"/>";
pageData["marsRequestSearch"]="<fmt:message key="marsRequestSearch"/>";
pageData["catalogReport"]="<fmt:message key="catalogReport"/>";
pageData["workAreaScanReport"]="<fmt:message key="workAreaScanReport"/>";  
pageData["msdsSearch"]="<fmt:message key="msdsSearch"/>";
pageData["lookuprunreports"]="<fmt:message key="lookuprunreports"/>";
pageData["chargeNumberSetup"]="<fmt:message key="chargeNumberSetup"/>";
pageData["troubleShootingFAQ"]="<fmt:message key="troubleShootingFAQ"/>";
pageData["cabinetTurns"]="<fmt:message key="cabinetTurns"/>";
pageData["listManagement"]="<fmt:message key="listManagement"/>";
pageData["pickingQCTestOnly"]="<fmt:message key="pickingQCTestOnly"/>";
pageData["adHocInventory"]="<fmt:message key="adHocInventory"/>";
pageData["heTracking"]="<fmt:message key="heTracking"/>";
pageData["usageLogging"]="<fmt:message key="usageLogging"/>";
pageData["usageMonitoring"]="<fmt:message key="usageMonitoring"/>";
pageData["permitManagement"]="<fmt:message key="permitManagement"/>";
pageData["cartManagement"]="<fmt:message key="cartManagement"/>";
pageData["substrateManagement"]="<fmt:message key="substrateManagement"/>";
pageData["methodManagement"]="<fmt:message key="methodManagement"/>";
pageData["containerInventory"]="<fmt:message key="containerInventory"/>";
pageData["containerEntry"]="<fmt:message key="containerEntry"/>";
pageData["shipmentReceiving"]="<fmt:message key="shipmentReceiving"/>";
pageData["manualConsumptionImport"]="<fmt:message key="manualConsumptionImport"/>";
pageData["editBulletin"]="<fmt:message key="editBulletin"/>";
pageData["usageImportInfo"]="<fmt:message key="usageImportInfo"/>";
pageData["quickQuote"]="<fmt:message key="quickQuote"/>";
pageData["chargeNumberManagement"]="<fmt:message key="chargeNumberManagement"/>";
pageData["quickCustomerView"]="<fmt:message key="quickCustomerView"/>";
pageData["dpmsHoldOrders"]="<fmt:message key="dpmsHoldOrders"/>";
pageData["businessPlanningReport"]="<fmt:message key="businessPlanningReport"/>";
pageData["mixtureManagement"]="<fmt:message key="mixtureManagement"/>";
pageData["mixturePermissions"]="<fmt:message key="mixturePermissions"/>";
pageData["quickSupplierView"]="<fmt:message key="quickSupplierView"/>";
pageData["deliveryConfirmation"]="<fmt:message key="deliveryConfirmation"/>";
pageData["workAreaManagementNew"]="<fmt:message key="workAreaManagementNew"/>";
pageData["hetStatus"]="<fmt:message key="hetStatus"/>";
pageData["dlaGasOrderCorrections"]="<fmt:message key="dlaGasOrderCorrections"/>";
pageData["dlaInventoryAdjustments"]="<fmt:message key="dlaInventoryAdjustments"/>";
pageData["vocetMsds"]="<fmt:message key="vocetMsds"/>";
pageData["vocetChemical"]="<fmt:message key="vocetChemical"/>";
pageData["printStaticLabels"]="<fmt:message key="printStaticLabels"/>";
pageData["inventoryImport"]="<fmt:message key="inventoryImport"/>";
pageData["chargeNumberReport"]="<fmt:message key="chargeNumberReport"/>";
pageData["adHocInventoryNew"]="<fmt:message key="adHocInventoryNew"/>";
pageData["adHocMaterialmatrixNew"]="<fmt:message key="adHocMaterialmatrixNew"/>";
pageData["adHocUsageNew"]="<fmt:message key="adHocUsageNew"/>";
pageData["currencyExchangeRate"]="<fmt:message key="currencyExchangeRate"/>";
pageData["facilityStockingLevel"]="<fmt:message key="facilityStockingLevel"/>";
pageData["openMaterialRequests"]="<fmt:message key="openMaterialRequests"/>";
pageData["newReceivingQc"]="<fmt:message key="newReceivingQc"/>";
pageData["newInvoicePrint"]="<fmt:message key="newInvoicePrint"/>";
pageData["newPickingQc"]="<fmt:message key="newPickingQc"/>";
pageData["specLookup"]="<fmt:message key="specLookup"/>";
pageData["materialTransferHistory"]="<fmt:message key="materialTransferHistory"/>";
pageData["newCustomerReturns"]="<fmt:message key="newCustomerReturns"/>";
pageData["msdssUsedAtLocations"]="<fmt:message key="msdssUsedAtLocations"/>";
pageData["materialConsolidation"]="<fmt:message key="materialConsolidation"/>";
pageData["itemConsolidation"]="<fmt:message key="itemConsolidation"/>";
pageData["receivingStatus"]="<fmt:message key="receivingStatus"/>";
pageData["inboundShipmentTracking"]="<fmt:message key="inboundShipmentTracking"/>";
pageData["labelRolls"]="<fmt:message key="labelRolls"/>";
pageData["raytheonInactiveChargeNo"]="<fmt:message key="raytheonInactiveChargeNo"/>";
pageData["cabinetPutAway"]="<fmt:message key="cabinetPutAway"/>";
pageData["workAreaStockTransfer"]="<fmt:message key="workAreaStockTransfer"/>";
pageData["itemImageUpload"]="<fmt:message key="itemImageUpload"/>";
pageData["maxcomSC"]="<fmt:message key="maxcomSC"/>";
pageData["kitManagement"]="<fmt:message key="kitManagement"/>";
pageData["haasOrderTracking"]="<fmt:message key="haasOrderTracking"/>";
pageData["cogsCategoryUpload"]="<fmt:message key="cogsCategoryUpload"/>";
pageData["hubCycleCount"]="<fmt:message key="hubCycleCount"/>";
pageData["customerMsdsQueue"]="<fmt:message key="customerMsdsQueue"/>";
pageData["helpline"]="<fmt:message key="helpline"/>";
pageData["helpdeskticket"]="<fmt:message key="helpdeskticket"/>";
pageData["msdsRevisionReport"]="<fmt:message key="msdsRevisionReport"/>";
pageData["monthlyVocReport"]="<fmt:message key="monthlyVocReport"/>";
pageData["kitIndexingQueue"]="<fmt:message key="kitIndexingQueue"/>";
pageData["deliveryScheduleView"]="<fmt:message key="deliveryScheduleView"/>";
pageData["disbursementAcknowledgement"]="<fmt:message key="disbursementAcknowledgement"/>";
pageData["receivingDocumentReport"]="<fmt:message key="receivingDocumentReport"/>";
pageData["ediOrderErrorsTracking"]="<fmt:message key="ediOrderErrorsTracking"/>";
pageData["ediSupplierPOStatus"]="<fmt:message key="ediSupplierPOStatus"/>";
pageData["shelfLifeManagement"]="<fmt:message key="shelfLifeManagement"/>";
pageData["purchaseOrderNew"]="<fmt:message key="purchaseOrderNew"/>";
pageData["tplReceiving"]="<fmt:message key="receiving"/>";
pageData["tplReceivingQc"]="<fmt:message key="newReceivingQc"/>";
pageData["tplPickingQc"]="<fmt:message key="newPickingQc"/>";
pageData["tplPicking"]="<fmt:message key="picking"/>";
pageData["tplShipConfirm"]="<fmt:message key="shipConfirm"/>";
pageData["tplShipmentHistory"]="<fmt:message key="shipmentHistory"/>";
pageData["tplBinsToScan"]="<fmt:message key="binsToScan"/>";
pageData["tplReconciliation"]="<fmt:message key="reconciliation"/>";
pageData["tplBinManagement"]="<fmt:message key="binManagement"/>";
pageData["tplLogisticsNew"]="<fmt:message key="logisticsNew"/>";
pageData["cylinderManagement"]="<fmt:message key="cylinderManagement"/>";
pageData["tabletPicking"]="<fmt:message key="tabletPicking"/>";
pageData["pickingGroupMgmt"]="<fmt:message key="pickingGroupMgmt"/>";
pageData["pickingStatus"]="<fmt:message key="pickingStatus"/>";
pageData["tmsOrderManagement"]="<fmt:message key="tmsOrderManagement"/>";
pageData["newPurchaseOrder"]="<fmt:message key="purchaseOrderNew"/>";
pageData["hmirsMgmt"]="<fmt:message key="hmirsMgmt"/>";
pageData["CMSPricelist"]="<fmt:message key="CMSPricelist"/>";
pageData["mfrNotificationMgmt"]="<fmt:message key="mfrNotificationMgmt"/>";
pageData["specialChargeManagement"]="<fmt:message key="specialChargeManagement"/>";
pageData["poApproval"]="<fmt:message key="poApproval"/>";
pageData["catAddVendorQueue"]="<fmt:message key="catAddVendorQueue"/>";
pageData["inventoryManagement"]="<fmt:message key="inventoryManagement"/>";
pageData["catalogSupplierManagement"]="<fmt:message key="catalogSupplierManagement"/>";
pageData["customerDetails"]="<fmt:message key="customerDetails"/>";
pageData["cmsCustomerDetails"]="<fmt:message key="customerDetails"/>";
pageData["shelfLifeStoTempSplash"]="<fmt:message key="shelfLifeStoTempSplash"/>";
pageData["freightInvoicing"]="<fmt:message key="freightInvoicing"/>";
pageData["itemStorageSearch"]="<fmt:message key="itemStorageSearch"/>";
pageData["hubStorageRules"]="<fmt:message key="hubStorageRules"/>";
// -->
</script>

</head>



<body bgcolor="#ffffff" onmouseup="toggleContextMenuToNormal()" onload="openStartingPages('${timeout}', '${timeoutWait}')" onresize="verifySize()">

<script language="JavaScript" type="text/javascript">
<!--
with(milonic=new menuname("appcontextMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=<fmt:message key="label.closetab"/>;url=javascript:closeTab();");
<c:if test="${!personnelBean.standalone}">
	aI("text=<fmt:message key="label.addtabtostartup"/>;url=javascript:addPageToStartup();");
	aI("text=<fmt:message key="label.removepagefromstartup"/>;url=javascript:removePageFromStartup();");
	aI("text=<fmt:message key="label.closetabstotheright"/>;url=javascript:closeTabstotheRight();");
	aI("text=<fmt:message key="label.closeothertabs"/>;url=javascript:closeOtherTabs();");
</c:if>
}
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
<tcmis:menuTag />
// -->
</script>

<form name="logoutForm" id="logoutForm" action="application.do">
 <input name="action" id="action" type="hidden">
 <input name="lostSession" id="lostSession" type="hidden">
</form>

<div id="interface" >
  <c:set var="module">
   <tcmis:module/>
  </c:set>

<script language="JavaScript" type="text/javascript">
<!--
 var module = "${module}";
 var companyName = "${companyName}";
 var personnelId = "${personnelId}";
 var hostEnv = "Prod";
// -->
</script>
    
<!-- Header Begins -->
<div id="appHeaderSection" class="header">
  <div class="alignLeft"><%@ include file="/common/application/clientlogo.jsp" %></div>
  <div class="alignRight">
   
   <fmt:message key="label.loggedinas"/>: <span class="optionTitleBold"><c:out value="${firstname}" />&nbsp;<c:out value="${lastname}" /></span>
   <c:if test="${!personnelBean.openCustomer}">
	   <fmt:message key="label.forcompany"/>
	   <span class="optionTitleBold">
	    <c:out value="${companyName}" />
	   </span>
	   <c:if test="${!personnelBean.standalone}">
		<c:set var="hostUrlValue">${hostUrl}</c:set>
		<c:choose>
			<c:when test="${fn:contains(hostUrlValue,'www')}">
			</c:when>
		    <c:when test="${fn:contains(hostUrlValue,'apps')}">
			</c:when>
	        	<c:otherwise>
				:&nbsp;&nbsp;<span class="whichServer" ><tcmis:environment/></span>
				<script language="JavaScript" type="text/javascript">
				<!--				 
				 var hostEnv = "Not Prod";
				// -->
				</script>
			</c:otherwise>
		</c:choose>
	</c:if>
   </c:if>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="logOut" onclick="logout(); return false;"><fmt:message key="label.logout"/></a>
   <c:if test="${module != 'cv'}">
   	| <a href="javascript:opentcmISHelp('${module}');"><fmt:message key="label.usualhelp"/></a>
   </c:if>
 </div>
  </div>
</div>
<!-- Header Ends -->

<!-- Menu Begins -->
<div id="menuSection" class="menus">
  <div class="menuContainer">&nbsp;</div>
  <div class="tabMenu">
    <div class="title">&nbsp;</div>
    <script>
     drawMenus();
    </script>
  </div>
</div>
<!-- Menu Ends-->

<!-- CSS Tabs -->
<div id="appTabs">
 <ul id="mainTabList">
  <%--<li id="homeli"><a href="#" id="homeLink" class="selectedTab" onclick="togglePage('home'); return false;"><span id="homeLinkSpan" class="selectedSpanTab"><img src="/images/home.gif" class="iconImage">Home<br class="brNoHeight"><img src="/images/minwidth.gif" width="8" height="0"></span></a></li>--%>
 </ul>
</div>
<!-- CSS Tabs End -->

<!-- Iframe Tabs -->
<div id="maindivs">
</div>
<!-- Iframe Tabs end -->


<script language="JavaScript" type="text/javascript">
<!--
function startOnload()
{

//openIFrame('home','/tcmIS/index.html','<fmt:message key="label.home"/>','/images/home.gif');
<c:set var="dataCount" value='${0}'/>
<c:set var="selectedTabId" value=''/>

<c:forEach var="personnelStartPageViewBean" items="${startPageViewBeanCollection}" varStatus="status">
	<c:if test="${dataCount == 0}">
		<c:set var="selectedTabId" value="${status.current.pageId}"/>
	</c:if>

	<c:choose>
		<c:when test="${personnelStartPageViewBean.templateId != null}">
			<c:set var="globalLabelLetter"><fmt:message key="${personnelStartPageViewBean.globalizationLabelLetter}"/></c:set>
			openIFrame("<c:out value="${status.current.pageId}"/>","<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />","${globalLabelLetter}${personnelStartPageViewBean.templateId}-${personnelStartPageViewBean.pageName}","<c:out value="${status.current.iconImage}"/>","<c:out value="${status.current.iframe}"/>");
		</c:when>
		<c:otherwise>
	  		openIFrame("<c:out value="${status.current.pageId}"/>","<tcmis:menuUrlPreffix pageUrl="${status.current.pageUrl}" />","<fmt:message key="${status.current.pageId}"/>","<c:out value="${status.current.iconImage}"/>","<c:out value="${status.current.iframe}"/>");
		</c:otherwise>
	</c:choose>
	<c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

<c:if test="${!empty selectedTabId}" >
 togglePage("<c:out value="${selectedTabId}"/>");
</c:if>
}
// -->
</script>

 </div>
<!--close interface -->

<!-- Session timeout Warning Message Begins -->
<div id="warnMessageArea" style="display:none;z-index:1;">
<div id="warnMessageAreaTitle" class="hd"><fmt:message key="label.pleasenote"/></div>
<div id="warnMessageAreaBody" class="alt">
 <div class="header">
  <div class="alignLeft"><img src="/images/logo_HASS.gif" width="44" height="43"><img src="/images/logo.gif" width="174" height="43"></div>
 </div>
 <br>
 <table width="100%" border="0" cellpadding="4" cellspacing="0" class="optionTitle">
  <tr>
   <td width="100%" class="alignLeft">
     <fmt:message key="label.timeoutwarnmessage1"/>
     <br><span id="timer" class="optionTitleBold"></span>.
     <br><br>
     <fmt:message key="label.timeoutwarnmessage2"/>
   </td>
  </tr>
  <tr>
   <td width="100%" class="alignCenter">
     <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="<fmt:message key="label.ok"/>" name="ok" onclick="clearGoToTimeout()" />
   </td>
  </tr>
 </table>
</div>
</div>
<!-- Session timeout Warning Message Ends -->

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);
//-->
</script>
</body>
</html:html>