<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

		<%@ include file="/common/locale.jsp"%>
		<tcmis:gridFontSizeCss />

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
		<%@ include file="/common/rightclickmenudata.jsp"%>

		<!-- Add any other javascript you need for the page here -->
		<script type="text/javascript" src="/js/distribution/customerlookupsearchresults.js"></script>

		<!-- These are for the Grid, uncomment if you are going to use the grid -->
		<%--NEW - dhtmlX grid files--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
		<%--Uncomment below if you are providing header menu to switch columns on/off--%>
		<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
		<%--Uncomment the below if your grid has rwospans >1--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>

		<!-- Add any other javascript you need for the page here -->
		<script type="text/javascript" src="/js/jquery/jquery-1.9.1.min.js"></script>
		<script src="/js/common/objects/responseObj.js"></script>
<script language="JavaScript" type="text/javascript">
	$.ajaxSetup({
		cache : false
	});

	$(document).ready(function() {
		var height = $(window).height();
		$('#resultFrame').height(height - 300);

		initPopupGridWithGlobal(catalogGridConfig);
		initPopupGridWithGlobal(inventoryGroupGridConfig);
		initPopupGridWithGlobal(invoiceGroupGridConfig);
		initPopupGridWithGlobal(serviceFeeGridConfig);
		initPopupGridWithGlobal(markupRuleGridConfig);
		initPopupGridWithGlobal(shippingGridConfig);
		initPopupGridWithGlobal(billingGridConfig);
		fixDivsSoTheyScroll();

	});

	var messagesData = new Array();
	messagesData = {
		alert : "<fmt:message key="label.alert"/>",
		and : "<fmt:message key="label.and"/>",
		validvalues : "<fmt:message key="label.validvalues"/>",
		submitOnlyOnce : "<fmt:message key="label.submitOnlyOnce"/>",
		recordFound : "<fmt:message key="label.recordFound"/>",
		searchDuration : "<fmt:message key="label.searchDuration"/>",
		minutes : "<fmt:message key="label.minutes"/>",
		seconds : "<fmt:message key="label.seconds"/>",
		selectedcustomer : "<fmt:message key="label.selectedcustomer"/>",
		itemInteger : "<fmt:message key="error.item.integer"/>",
		customerdetails : "<fmt:message key="customerdetails.title"/>",
		type : "<fmt:message key="label.type"/>"
	};

	function setSpan(span, val) {
		span.text(!val ? "" : val);
	}

	var categoryColConfig = [ {
		columnId : "catalogId",
		columnName : '<fmt:message key="label.catalogid"/>',
		tooltip : "Y",
		width : 16
	}, {
		columnId : "wescoOwned",
		columnName : '<fmt:message key="label.wescoownedcabinets"/>',
		type : "hchstatus",
		width : 12,
		align : "center",
		sort : "haasHch"
	}, {
		columnId : "useCabinets",
		columnName : '<fmt:message key="label.usecabinets"/>',
		type : "hchstatus",
		width : 12,
		align : "center",
		sort : "haasHch"
	}, {
		columnId : "scabPutaway",
		columnName : '<fmt:message key="label.cabinetscanputaway"/>',
		type : "hchstatus",
		width : 12,
		align : "center",
		sort : "haasHch"
	} ];

	var catalogGridConfig = {
		divName : 'catalogGridDiv',
		beanData : 'catalogJsonMainData',
		beanGrid : 'catalogGrid',
		noSmartRender : true,
		rowSpan : false,
		config : categoryColConfig
	};

	var catalogJsonMainData = new Array();
	catalogJsonMainData = {
		rows : [ {
			id : 1,
			data : [ '', false, false, false ]
		} ]
	};

	var inventoryGroupColConfig = [ {
		columnId : "inventoryGroupId",
		columnName : '<fmt:message key="label.inventorygroupid"/>',
		tooltip : "Y",
		width : 16
	}, {
		columnId : "hub",
		columnName : '<fmt:message key="label.hubbranchplant"/>',
		width : 12
	}, {
		columnId : "itemCounting",
		columnName : '<fmt:message key="label.itemcounting"/>',
		type : "hchstatus",
		width : 12,
		align : "center",
		sort : "haasHch"
	}, {
		columnId : "containerlabels",
		columnName : '<fmt:message key="label.containerlabels"/>',
		type : "hchstatus",
		width : 12,
		align : "center",
		sort : "haasHch"
	} ];

	var inventoryGroupGridConfig = {
		divName : 'inventoryGroupGridDiv',
		beanData : 'inventoryGroupJsonMainData',
		beanGrid : 'inventoryGroupGrid',
		noSmartRender : true,
		rowSpan : false,
		config : inventoryGroupColConfig
	};

	var inventoryGroupJsonMainData = new Array();
	inventoryGroupJsonMainData = {
		rows : [ {
			id : 1,
			data : [ '', '', false, false ]
		} ]
	};

	var invoiceGroupColConfig = [ {
		columnId : "invoiceGroupId",
		columnName : '<fmt:message key="label.invoicegroup"/>',
		tooltip : "Y",
		width : 16
	}, {
		columnId : "accountSys",
		columnName : '<fmt:message key="label.accountsystem"/>',
		tooltip : "Y",
		width : 12
	}, {
		columnId : "paymentTerms",
		columnName : '<fmt:message key="label.paymentterms"/>',
		tooltip : "Y",
		width : 10
	}, {
		columnId : "pricingmethod",
		columnName : '<fmt:message key="label.pricingmethod"/>',
		tooltip : "Y",
		width : 16
	} ];

	var invoiceGroupGridConfig = {
		divName : 'invoiceGroupGridDiv',
		beanData : 'invoiceGroupJsonMainData',
		beanGrid : 'invoiceGroupGrid',
		config : invoiceGroupColConfig
	};

	var invoiceGroupJsonMainData = new Array();
	invoiceGroupJsonMainData = {
		rows : [ {
			id : 1,
			data : [ '', '', '', '' ]
		} ]
	};

	var serviceFeeColConfig = [ {
		columnId : "po",
		columnName : '<fmt:message key="label.purchaseorder"/>',
		tooltip : "Y",
		width : 8
	}, {
		columnId : "line",
		columnName : '<fmt:message key="label.line"/>',
		tooltip : "Y",
		width : 8
	}, {
		columnId : "part",
		columnName : '<fmt:message key="label.part"/>',
		tooltip : "Y",
		width : 8
	}, {
		columnId : "partDesc",
		columnName : '<fmt:message key="label.partdescription"/>',
		tooltip : "Y",
		width : 16
	}, {
		columnId : "Price",
		columnName : '<fmt:message key="label.price"/>',
		tooltip : "Y",
		width : 8
	} ];

	var serviceFeeGridConfig = {
		divName : 'serviceFeeGridDiv',
		beanData : 'serviceFeeJsonMainData',
		beanGrid : 'serviceFeeGrid',
		noSmartRender : true,
		rowSpan : false,
		config : serviceFeeColConfig
	};

	var serviceFeeJsonMainData = new Array();
	serviceFeeJsonMainData = {
		rows : [ {
			id : 1,
			data : [ '', '', '', '', '' ]
		} ]
	};

	var markupRuleColConfig = [ {
		columnId : "itemType",
		columnName : '<fmt:message key="label.itemtype"/>',
		tooltip : "Y",
		width : 10
	}, {
		columnId : "buyType",
		columnName : '<fmt:message key="label.buytype"/>',
		tooltip : "Y",
		width : 10
	}, {
		columnId : "orderType",
		columnName : '<fmt:message key="label.ordertype"/>',
		tooltip : "Y",
		width : 10
	}, {
		columnId : "cost",
		columnName : '<fmt:message key="label.catalogunitcost"/>',
		tooltip : "Y",
		width : 10
	}, {
		columnId : "percentage",
		columnName : '<fmt:message key="label.percentage"/>',
		tooltip : "Y",
		width : 10
	} ];

	var markupRuleGridConfig = {
		divName : 'markupRuleGridDiv',
		beanData : 'markupRuleJsonMainData',
		beanGrid : 'markupRuleGrid',
		noSmartRender : true,
		rowSpan : false,
		config : markupRuleColConfig
	};

	var markupRuleJsonMainData = new Array();
	markupRuleJsonMainData = {
		rows : [ {
			id : 1,
			data : [ '', '', '', '', '' ]
		} ]
	};

	var shippingColConfig = [ {
		columnId : "location",
		columnName : '<fmt:message key="label.location"/>'
	}, {
		columnId : "address",
		columnName : '<fmt:message key="label.address"/>',
		tooltip : "Y",
		width : 40
	} ];

	var shippingGridConfig = {
		divName : 'shippingGridDiv',
		beanData : 'shippingJsonMainData',
		beanGrid : 'shippingGrid',
		noSmartRender : true,
		rowSpan : false,
		config : shippingColConfig
	};

	var shippingJsonMainData = new Array();
	shippingJsonMainData = {
		rows : [ {
			id : 1,
			data : [ '', '' ]
		} ]
	};

	var billingColConfig = [ {
		columnId : "location",
		columnName : '<fmt:message key="label.location"/>'
	}, {
		columnId : "address",
		columnName : '<fmt:message key="label.address"/>',
		tooltip : "Y",
		width : 40
	} ];

	var billingGridConfig = {
		divName : 'billingGridDiv',
		beanData : 'billingJsonMainData',
		beanGrid : 'billingGrid',
		noSmartRender : true,
		rowSpan : false,
		config : billingColConfig
	};

	var billingJsonMainData = new Array();
	billingJsonMainData = {
		rows : [ {
			id : 1,
			data : [ '', '' ]
		} ]
	};

	function doSearch(companyId, facilityId) {
		var URL = "/tcmIS/haas/customerDetails.do?companyId=" + companyId
				+ "&facilityId=" + facilityId + "&action=";
		$.get(URL + "getOperationalHeader", fillOperationalHeader);
		$.get(URL + "getInvoiceHeader", fillInvoiceHeader);
		$.get(URL + "getCatalogs", fillCatalogs);
		$.get(URL + "getInventoryGroups", fillInventoryGroups);
		$.get(URL + "getInvoiceGroups", fillInvoiceGroups);
		$.get(URL + "getServiceFees", fillServiceFees);
		$.get(URL + "getShippingAddresses", fillShipping);
		$.get(URL + "getBillingAddresses", fillBilling);
		$.get(URL + "getMarkupRules", fillMarkupFees);
	}

	function printPage() {
		var mywindow = window.open('', 'PRINT', 'height=400,width=600');
		mywindow.document.write('<html><head>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/dhtmlxGrid/codebase/dhtmlxgrid.css"></link>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.css"></link>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/dhtmlxLayout/codebase/dhtmlxlayout.css"></link>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/dhtmlxLayout/codebase/skins/dhtmlxlayout_dhx_blue.css"></link>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/dhtmlxWindows/codebase/dhtmlxwindows.css"></link>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/dhtmlxWindows/codebase/skins/dhtmlxwindows_dhx_blue.css"></link>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/css/dhtmlxgrid/haasGlobalMedium.css"></link>');
		mywindow.document
				.write('<link rel="stylesheet" type="text/css" href="/css/dhtmlxgrid/haasdhtmlxgridMedium.css"></link>');
		mywindow.document.write('<title>' + document.title + '</title>');
		mywindow.document.write('</head><body >');
		mywindow.document.write('<h1>' + document.title + '</h1>');
		mywindow.document.write(document
				.getElementById('customerDetailsContent').innerHTML);
		mywindow.document.write('</body></html>');

		mywindow.document.close(); // necessary for IE >= 10
		mywindow.focus(); // necessary for IE >= 10*/

		mywindow.print();
		mywindow.close();

		return true;

	}

	function fillOperationalHeader(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			setSpan($("#operatingEntityId"),
					response.operationalHeader.opsEntityId);
			setSpan($("#operatingEntityName"),
					response.operationalHeader.operatingEntityName);
			setSpan($("#companyId"), response.operationalHeader.companyId);
			setSpan($("#facilityId"), response.operationalHeader.facilityId);
		}
	};

	function fillInvoiceHeader(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			setSpan($("#billingEntity"), response.invoiceHeader.billingEntity);
			setSpan($("#companyName"), response.invoiceHeader.companyName);
			setSpan($("#facility"), response.invoiceHeader.facilityName);
			setSpan($("#currencyId"), response.invoiceHeader.currencyId);
		}
	};

	function fixDivsSoTheyScroll() {
		$("div[class='xhdr']").css('position', 'static');
		$("div[class='objbox']").css('position', 'static');
	}

	function resizeGridDiv(gridName) {
		var hdrHeight = $("#" + gridName + " > div[class='xhdr']").height();
		var tableSelector = "#" + gridName
		+ " > div[class='objbox'] > table[class='obj row20px']"; 
		var tblHeight = $(tableSelector).height();
		var tblWidth = $(tableSelector).width();
		$("#" + gridName).height(hdrHeight + tblHeight + 1);
		$("#" + gridName).width(tblWidth);
	}

	function fillCatalogs(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			// Yes, the double calls are on purpose
			// they allow us to get what the grid size SHOULD be
			catalogGrid.clearAll();
			catalogGrid.parse(response.catalogs, "json");
			resizeGridDiv("catalogGridDiv");
			catalogGrid.clearAll();
			catalogGrid.parse(response.catalogs, "json"); 
			resizeGridDiv("catalogGridDiv");
			fixDivsSoTheyScroll();
		}
	};

	function fillInventoryGroups(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			inventoryGroupGrid.clearAll();
			inventoryGroupGrid.parse(response.inventoryGroups, "json");
			resizeGridDiv("inventoryGroupGridDiv");
			inventoryGroupGrid.clearAll();
			inventoryGroupGrid.parse(response.inventoryGroups, "json");
			resizeGridDiv("inventoryGroupGridDiv");
			fixDivsSoTheyScroll();
		}
	};

	function fillInvoiceGroups(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			invoiceGroupGrid.clearAll();
			invoiceGroupGrid.parse(response.invoiceGroups, "json");
			resizeGridDiv("invoiceGroupGridDiv");
			invoiceGroupGrid.clearAll();
			invoiceGroupGrid.parse(response.invoiceGroups, "json");
			resizeGridDiv("invoiceGroupGridDiv");
			fixDivsSoTheyScroll();
		}
	};

	function fillServiceFees(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			serviceFeeGrid.clearAll();
			serviceFeeGrid.parse(response.serviceFees, "json");
			resizeGridDiv("serviceFeeGridDiv");
			serviceFeeGrid.clearAll();
			serviceFeeGrid.parse(response.serviceFees, "json");
			resizeGridDiv("serviceFeeGridDiv");
			fixDivsSoTheyScroll();
		}
	};

	function fillMarkupFees(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			markupRuleGrid.clearAll();
			markupRuleGrid.parse(response.markupRules, "json");
			resizeGridDiv("markupRuleGridDiv");
			markupRuleGrid.clearAll();
			markupRuleGrid.parse(response.markupRules, "json");
			resizeGridDiv("markupRuleGridDiv");
			fixDivsSoTheyScroll();
		}
	};

	function fillShipping(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			shippingGrid.clearAll();
			shippingGrid.parse(response.addresses, "json");
			resizeGridDiv("shippingGridDiv");
			shippingGrid.clearAll();
			shippingGrid.parse(response.addresses, "json");
			resizeGridDiv("shippingGridDiv");
			fixDivsSoTheyScroll();
		}
	};

	function fillBilling(data) {
		var response = new responseObj(data);
		if (response.isOK()) {
			billingGrid.clearAll();
			billingGrid.parse(response.addresses, "json");
			resizeGridDiv("billingGridDiv");
			billingGrid.clearAll();
			billingGrid.parse(response.addresses, "json");
			resizeGridDiv("billingGridDiv");
			fixDivsSoTheyScroll();
		}
	};
</script>
<style>
fieldset {
	border: 1px solid #000000 !important;
	padding: 1em;
	width: 96%
}

@media print {
	#customerDetailsContent {
		height: auto;
		overflow-y: auto;
	}
}
</style>
<title><fmt:message key="customerdetails.title" /></title>

</head>
<body bgcolor="#E5E5E5">

	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display: none;">
		<html:errors />
	</div>

	<div class="interface" id="customerDetailsContent" style="overflow-y: auto;">
		<div class="backgroundcontent">
			<fieldset>
				<legend class="msdsSectionHeader">
					<fmt:message key="label.operatingdetails" />
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td class="optionTitleBoldLeft" nowrap><fmt:message key="label.operatingentityid" />:</td>
						<td class="optionTitleBoldLeft" nowrap><fmt:message key="label.operatingentity" />:</td>
						<td class="optionTitleBoldLeft" nowrap><fmt:message key="label.companyid" />:</td>
						<td class="optionTitleBoldLeft" nowrap><fmt:message key="label.facilityid" />:</td>
					</tr>
					<tr>
						<td class="optionTitleLeft" nowrap><span name="operatingEntityId" id="operatingEntityId"></span></td>
						<td class="optionTitleLeft" nowrap><span name="operatingEntityName" id="operatingEntityName"></span></td>
						<td class="optionTitleLeft" nowrap><span name="companyId" id="companyId"></span></td>
						<td class="optionTitleLeft" nowrap><span name="facilityId" id="facilityId"></span></td>
					</tr>
				</table>
				</P>
				<div id="catalogGridDiv" style="height: 120px; width: 100%"></div>
				</P>
				<div id="inventoryGroupGridDiv" style="height: 120px; width: 100%"></div>
			</fieldset>
			</P>
			<fieldset>
				<legend class="msdsSectionHeader">
					<fmt:message key="label.invoicingandpricing" />
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="25%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.wescobillingentity" />:</td>
						<td width="25%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.companyname" />:</td>
						<td width="25%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.facility" />:</td>
						<td width="25%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.currencyid" />:</td>
					</tr>
					<tr>
						<td class="optionTitleLeft" nowrap><span name="billingEntity" id="billingEntity"></span></td>
						<td class="optionTitleLeft" nowrap><span name="companyName" id="companyName"></span></td>
						<td class="optionTitleLeft" nowrap><span name="facility" id="facility"></span></td>
						<td class="optionTitleLeft" nowrap><span name="currencyId" id="currencyId"></span></td>
					</tr>
				</table>
				</P>
				<div id="invoiceGroupGridDiv" style="height: 120px; width: 100%"></div>
			</fieldset>
			</P>
			<fieldset>
				<legend class="msdsSectionHeader">
					<fmt:message key="label.shippingaddresses" />
				</legend>
				<div id="shippingGridDiv" style="height: 160px; width: 100%"></div>
			</fieldset>
			</P>
			<fieldset>
				<legend class="msdsSectionHeader">
					<fmt:message key="label.servicefees" />
				</legend>
				<div id="serviceFeeGridDiv" style="height: 120px; width: 100%"></div>
			</fieldset>
			</P>
			<fieldset>
				<legend class="msdsSectionHeader">
					<fmt:message key="label.servicefeemarkuprules" />
				</legend>
				<div id="markupRuleGridDiv" style="height: 120px; width: 100%"></div>
			</fieldset>
			</P>
			<fieldset>
				<legend class="msdsSectionHeader">
					<fmt:message key="label.billingaddresses" />
				</legend>
				<div id="billingGridDiv" style="height: 160px; width: 100%"></div>
			</fieldset>

		</div>
	</div>
	<!-- close of interface -->

</body>
</html:html>