<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>
<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>  

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
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- For Calendar support for column type hcal-->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/receivingqcchecklist.js"></script>  


<!-- These are for the Grid-->
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<script src="/js/jquery/jquery-1.6.4.js"></script>


<title>
<fmt:message key="receivingQcCheckList"/> - ${param.search}
</title>
<style type="text/css">
#wrapper {
    width:100%;
    min-width:100%;
    max-width:100%;
    height:90%;
}
#slide-wrap {
    overflow:auto;
 	width:100%;
 	height:90%;
}
#inner-wrap {
    float:left;
    margin-right:-32767px;/*Be safe with Opera's limited neg margin of -32767px*/
}
</style>

<script language="JavaScript" type="text/javascript">

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
entryvalues:"<fmt:message key="label.entryvalues"/>",
docswithnonames:"<fmt:message key="label.docswithnonames"/>",
orderedspecs:"<fmt:message key="label.orderedspecs"/>",
orderedflowdowns:"<fmt:message key="label.orderedflowdowns"/>",
inbounddocuments:"<fmt:message key="label.inbounddocuments"/>",
receiptdocuments:"<fmt:message key="label.receiptdocuments"/>",
notes:"<fmt:message key="label.notes"/>",
catalog:"<fmt:message key="label.catalog"/>",
images:"<fmt:message key="label.images"/>",
msds:"<fmt:message key="label.msds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
forreceipt:"<fmt:message key="label.forreceipt"/>",
actsupshpdate:"<fmt:message key="label.actsupshpdate"/>",
dom:"<fmt:message key="receivedreceipts.label.dom"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
dos:"<fmt:message key="label.manufacturerdos"/>",
shipbeforemanufacture:'<fmt:message key="label.shipbeforemanufacture"/>',
qastatement:"<fmt:message key="label.qastatement"/>",
lotstatus:"<fmt:message key="label.lotstatus"/>",
cannotbe:"<fmt:message key="label.cannotbe"/>",
incoming:"<fmt:message key="label.incoming"/>",
nopermissionstoqcstatus:"<fmt:message key="label.nopermissionstoqcstatus"/>",
mfglot:"<fmt:message key="label.mfglot"/>",
expiredate:"<fmt:message key="label.expiredate"/>",
expdatelessthanminexpdatecontinuewithconfirm:"<fmt:message key="label.expdatelessthanminexpdatecontinuewithconfirm"/>",
pleaseaddnotesforthereverting:"<fmt:message key="label.pleaseaddnotesforthereverting"/>",
confirming:"<fmt:message key="label.confirming"/>",
revertstatusconfirm:"<fmt:message key="label.revertstatusconfirm"/>",
noreceiptdocs:"<fmt:message key="label.noreceiptdocs"/>",
updateerr:"<fmt:message key="error.db.update"/>",
docswithnotype:"<fmt:message key="label.docswithnotype"/>",
marsdetail:"<fmt:message key="label.marsdetail"/>",
associatedPRs:"<fmt:message key="label.associatedprs"/>",
errors:"<fmt:message key="label.errorss"/>",
enterDocIds:"<fmt:message key="label.enterdocids"/>",
revertingImage:"<fmt:message key="label.revertingimage"/>",
labelHistory:"<fmt:message key="label.labelhistory"/>",
indefinite:"<fmt:message key="label.indefinite"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
dom:"<fmt:message key="receivedreceipts.label.dom"/>",
manufacturerdos:"<fmt:message key="receivedreceipts.label.manufacturerdos"/>"
};

<%-- Define the right click menus --%>
with(milonic=new menuname("rightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.openf6"/>;url=javascript:doF6();");
}

<%-- Define the right click menus --%>
with(milonic=new menuname("specRightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="catalogspec.label.viewspec"/>;url=javascript:doF8();");
}

<%-- Define the right click menus --%>
with(milonic=new menuname("catalogRightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.qualitysummary"/>;url=javascript:qualitySummary();");
}

<%-- Define the right click menus --%>
with(milonic=new menuname("msdsRCMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.openmsds"/>;url=javascript:openMsds();");
}

<%-- Define the right click menus --%>
with(milonic=new menuname("flowDownMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.viewflowdown"/>;url=javascript:openFlowDown();");
}

<%-- Define the right click menus --%>
with(milonic=new menuname("deleteImgMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.deleteimage"/>;url=javascript:deleteImg();");
}

<%-- Define the right click menus --%>
with(milonic=new menuname("deleteRightClickMenu")){
	top="offset=2"
	style = contextStyle;
	margin=3
	aI("text=<fmt:message key="label.delete"/>;url=javascript:deleteInboundRow();");
}


<%-- Initialize the RCMenus --%>
drawMenus();

rowSpanMap = new Array();
rowSpanClassMap = new Array();
rowSpanCols = new Array();

var msdsGridConfig = {
		divName:'msdsBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'msdsJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'msdsGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'msdsConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: msdsRCMenu // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var msdsConfig = [
	{
		  columnId:"permission"
	}, 
	{ 
		  columnId:"materialDescription",
		  columnName:'<fmt:message key="label.componentdescription"/>',
		  tooltip:"Y",
		  sorting:'na',
		  width:20
	},
	{ 
		  columnId:"manufacturer",
		  columnName:'<fmt:message key="label.manufacturer"/>',
		  sorting:'na',
		  width:10
	},
	{ 
		  columnId:"revisionDate",
		  columnName:'<fmt:message key="label.latestrevisiondate"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"content"
	}
	
];


var componentGridConfig = {
		divName:'componentBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'componentJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'componentGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'componentConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: doNothing // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var componentConfig = [
	{
		  columnId:"permission"
	}, 
	{ 
		  columnId:"materialDesc",
		  columnName:'<fmt:message key="label.componentdescription"/>',
		  tooltip:"Y",
		  sorting:'na',
		  width:17
	},
	{ 	  //// PACKAGING IS BEING RESIZED BY INDEX! PLEASE ADJUST function resizePackage() INDEX WHEN ADDING NEW COLS! 
		  columnId:"packaging",
		  columnName:'<fmt:message key="label.packaging"/>',
		  tooltip:"Y",
		  sorting:'na',
		  width:37
	},
	{ 
		  columnId:"mfgLot",
		  columnName:'<fmt:message key="label.mfglot"/>',
		  align:"center",
		  type:'hed',
		  size:12,
		  maxlength:36,
		  sorting:'na',
		  width:20
	},
	{ 
		  columnId:"expireDateDisplay",
		  columnName:'<fmt:message key="label.expiredate"/>',
		  sorting:'na',
		  width:10
	},
	{columnId:"expireDate" },
	{columnId:"componentId" },
	{ 
		  columnId:"mfgLabelExpireDateDisplay",
		  columnName:'<fmt:message key="label.labelexpiredate"/>',
		  sorting:'na',
		  width:10
	},
	{columnId:"mfgLabelExpireDate" },
	{columnId:"dateOfRepackaging"},//Can't do type hcal because need to block against DOM and DOR that are not in a grid
	{columnId:"dateOfRepackagingDisplay",
		columnName:'<fmt:message key="label.dateofrepackaging"/>',
		sorting:'na',
		width:10 
    }

];

var catalogGridConfig = {
		divName:'catalogBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'catalogJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'catalogGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'catalogConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: showCatMenu // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var catalogConfig = [
	{
		  columnId:"permission"
	}, 
	{ 
		  columnId:"companyShortNameCatalogId",
		  columnName:'<fmt:message key="label.companycatalog"/>',
		  sorting:'na',
		  width:10
	},
	{ 
		  columnId:"catPartNo",
		  columnName:'<fmt:message key="label.partnumber"/>',
		  sorting:'na',
		  toolTip:"Y",
		  width:14
	},
	{ 
		  columnId:"stocked",
		  columnName:'<fmt:message key="label.stocking"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"shelfLifeStorageTemp",
		  columnName:'<fmt:message key="catalog.label.shelflife"/>',
		  sorting:'na',
		  width:14
	},
	{ 
		  columnId:"shelfLifeBasis",
		  columnName:'<fmt:message key="label.basis"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"specList",
		  columnName:'<fmt:message key="label.specificationlist"/>',
		  sorting:'na',
		  width:20
	},
	{ 
		  columnId:"pwA300",
		  columnName:'<fmt:message key="label.pwa300"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"companyId"
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
		  columnId:"rpSlRq",
		  columnName:'<fmt:message key="label.rpslrq"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"incomingTesting",
		  columnName:'<fmt:message key="label.incomingtesting"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"recert",
		  columnName:'<fmt:message key="label.recert"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"recertInstructions",
		  columnName:'<fmt:message key="label.recertinstructions"/>',
		  sorting:'na',
		  toolTip:"Y",
		  width:7
	},
	{ 
		  columnId:"partRevision",
		  columnName:'<fmt:message key="label.partrevision"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"catalogId"
	}
	
];

var flowDownGridConfig = {
		divName:'flowDownBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'flowDownJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'flowDownGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'flowDownConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: flowDownMenu
		};

var flowDownConfig = [
	{
		  columnId:"permission"
	}, 
	{ 
		  columnId:"flowDown",
		  columnName:'<fmt:message key="label.flowdown"/>',
		  sorting:'na',
		  width:10
	},
	{ 
		  columnId:"revisionDate",
		  columnName:'<fmt:message key="label.revisiondate"/>',
		  sorting:'na',
		  width:10
	},
	{ 
		  columnId:"description",
		  columnName:'<fmt:message key="label.description"/>',
		  sorting:'na',
		  width:20
	},
	{ 
		  columnId:"catalog",
		  columnName:'<fmt:message key="label.catalog"/>',
		  sorting:'na',
		  width:10
	},
	{ 
		  columnId:"company",
		  columnName:'<fmt:message key="label.company"/>',
		  sorting:'na',
		  width:10
	},
	{ 
		  columnId:"type",
		  columnName:'<fmt:message key="label.type"/>',
		  sorting:'na',
		  width:10
	},
	{
		 columnId:"onLine"
	},
	{
		 columnId:"content"
	}
	
];

var specGridConfig = {
		divName:'specBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'specJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'specGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'specConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: onSpecRightClickMenu
		};

var specConfig = [
	{
		  columnId:"permission"
	}, 
	{ 
		  columnId:"savedCoc",
		  columnName:'<fmt:message key="label.saved"/>',
		  attachHeader:'<fmt:message key="label.coc"/>',
		  sorting:'na', 
		  type:'hchstatus',
		  width:3
	},
	{ 
		  columnId:"savedCoa",
		  columnName:'#cspan',
		  attachHeader:'<fmt:message key="label.coa"/>', 
		  sorting:'na',
		  type:'hchstatus',
		  width:3
	},
	{ 
		  columnId:"specIdDisplay",
		  columnName:'<fmt:message key="label.spec"/>',
		  sorting:'na',
		  width:5
	},
	{ 
		  columnId:"specLibraryDesc",
		  columnName:'<fmt:message key="label.speclibrary"/>',
		  sorting:'na',
		  toolTip:"Y",
		  width:7
	},
	{ 
		  columnId:"currentCocRequirement",
		  columnName:'<fmt:message key="label.curreq"/>',
		  attachHeader:'<fmt:message key="label.coc"/>',
		  sorting:'na',
		  type:'hchstatus',
		  width:3
	},
	{ 
		  columnId:"currentCoaRequirement",
		  columnName:'#cspan',
		  attachHeader:'<fmt:message key="label.coa"/>', 
		  type:'hchstatus',
		  sorting:'na',
		  width:3
	},
	{ 
		  columnId:"cocReqAtSave",
		  columnName:'<fmt:message key="label.reqlastsav"/>',
		  attachHeader:'<fmt:message key="label.coc"/>',
		  type:'hchstatus',
		  sorting:'na',
		  width:3
	},
	{ 
		  columnId:"coaReqAtSave",
		  columnName:'#cspan',
		  attachHeader:'<fmt:message key="label.coa"/>', 
		  type:'hchstatus',
		  sorting:'na',
		  width:3
	},
	{
		columnId:"content"
	},
	{
		columnId:"specId"
	}
	
];

<%--  This is the value array for type:'hcoro' pulldown 'deliveryType' --%> 

var documentType = new  Array(
	{value:'', text:'<fmt:message key="label.pleaseselect"/>'}
	<c:forEach var="receiptDocumentType" items="${vvReceiptDocumentTypeBeanCollection}" varStatus="status">
		,
		{value:'${receiptDocumentType.documentType}', text:'<fmt:message key="${receiptDocumentType.jspLabel}"/>'}
	</c:forEach>
);

<%--  This is the value array for type:'hcoro' pulldown 'deliveryType' --%> 
<c:set var="companyCount" value="0"/>
var companyId = new  Array(
		{text:'<fmt:message key="label.haasgroupintl"/>',value:'Radian'}
		<c:if test="${fn:length(companyIdsCollection) > 0 }">,</c:if>
		<c:forEach var="company" items="${companyIdsCollection}" varStatus="status">
			<c:if test="${status.index > 0 }">,</c:if>
			{value:'${company.companyId}', text:'${company.companyName}'}
			<c:set var="companyCount" value="${companyCount+1}"/>
		</c:forEach>
);

var inboundDocGridConfig = {
		divName:'inboundDocBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'inboundDocJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'inboundDocGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'inboundDocConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: onInboundRightclick	<%--  a javascript function to be called on right click with rowId & cellId as args --%>

		};

var inboundDocConfig = [
            {
         		  columnId:"permission"
         	}, 
         	{
         		  columnId:"inboundShipmentDocumentIdPermission"
         	},
        	{ 
	       		  columnId:"inboundShipmentDocumentId",
		       	  columnName:'<fmt:message key="label.documentid"/>',
		       	  sorting:'na',
	       		  type:'hed',
		       	  width:10,
		          permission:true
	       	},
        	{ 
	       		  columnId:"documentDateDisplay",
	       		  columnName:'<fmt:message key="label.documentdate"/>',
	       		  sorting:'na',
	       		  width:10
	       	},
         	{
       		  columnId:"documentNamePermission"
       		},
        	{ 
	       		  columnId:"documentName",
	       		  columnName:'<fmt:message key="label.name"/>',
	       		  sorting:'na',
	       		  type:'hed',
	       		  //permission: true,
	       		  width:10,
		          permission:true
	       	},
         	{
	       		  columnId:"documentTypePermission"
	       	},
        	{ 
	       		  columnId:"documentType",
	       		  columnName:'<fmt:message key="label.type"/>',
	       		  type:'hcoro',
	       		  onChange:autoFillName,
	       		  sorting:'na',
	       		  //permission: true,
	       		  width:10,
		          permission:true
	       	},
         	{
	       		  columnId:"companyIdPermission"
	       	},
        	{ 
	       		  columnId:"companyId",
	       		  columnName:'<fmt:message key="label.company"/>',
	       		  type:'hcoro',
	       		  sorting:'na',
	       		  //permission: true,
	       		  width:10,
		          permission:true
	       	},
         	{ 
         		  columnId:"fileName"
         	},
        	{ 
	       		  columnId:"inboundShipmentId"
	       	},
        	{ 
	       		  columnId:"initialScanUserName"
	       	},
	       	{ 
	       		  columnId:"dateUpdated"
	       	},
	       	{ 
	       		  columnId:"dateInserted"
	       	},
	       	{ 
	       		  columnId:"receiptId"
	       	},
        	{ 
	       		  columnId:"documentDate"
        	},
        	{ 
	       		  columnId:"originalDocumentName"
      		},
      		{ 
	       		  columnId:"isAddDocRow"
    		}
      		
	       	
];


var receiptDocGridConfig = {
		divName:'receiptDocBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'receiptDocJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'receiptDocGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'receiptDocConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:true, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: onRightclick
		};

var receiptDocConfig = [
            {
         		  columnId:"permission"
         	}, 
        	{ 
	       		  columnId:"documentType",
	       		  columnName:'<fmt:message key="label.type"/>',
	       		  type:'hcoro',
	       		  sorting:'na',
	       		  width:10
	       	},

        	{ 
	       		  columnId:"companyId",
	       		  columnName:'<fmt:message key="label.company"/>',
	       		  sorting:'na',
	       		  width:10
	       	},
        	{ 
	       		  columnId:"documentName",
	       		  columnName:'<fmt:message key="label.name"/>',
	       		  sorting:'na',
	       		  width:10
	       	},
        	{ 
	       		  columnId:"documentDateDisplay",
	       		  columnName:'<fmt:message key="label.documentdate"/>',
	       		  sorting:'na',
	       		  width:10
	       	},
	        {
       		 	 columnId:"documentUrl"
       		},
            {
      		 	 columnId:"documentId"
      		}	
];


var associatedPRsGridConfig = {
		divName:'associatedPRsBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'associatedPRsJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'associatedPRsGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'associatedPRsConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:true, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: doNothing
		};

var associatedPRsConfig = [
            {
         		  columnId:"permission"
         	},
        	{ 
	       		  columnId:"prNumber",
	       		  columnName:'<fmt:message key="label.prnumber"/>',
	       		  width:5
	       	},
        	{ 
	       		  columnId:"mrLine",
	       		  columnName:'<fmt:message key="label.mrline"/>',
	       		  tooltip:"Y",
	       		  width:10
	       	},
        	{ 
	       		  columnId:"partNumber",
	       		  columnName:'<fmt:message key="label.partnumber"/>',
	       		  tooltip:"Y",
	       		  width:10
	       	},
        	{ 
	       		  columnId:"specs",
	       		  columnName:'<fmt:message key="label.specs"/>',
	       		  width:5
	       	},
        	{ 
	       		  columnId:"type",
	       		  columnName:'<fmt:message key="label.type"/>',
	       		  width:4
	       	},
        	{ 
	       		  columnId:"purchasingNote",
	       		  columnName:'<fmt:message key="label.purchasingnotes"/>',
	       		  tooltip:"Y",
	       		  width:10
	       	},
        	{ 
	       		  columnId:"notes",
	       		  columnName:'<fmt:message key="label.ordernotes"/>',
	       		  tooltip:"Y",
	       		  width:10
	       	},
        	{ 
	       		  columnId:"requestor",
	       		  columnName:'<fmt:message key="label.reuqestphemail"/>',
	       		  tooltip:"Y",
	       		  width:10
	       	},
        	{ 
	       		  columnId:"csr",
	       		  columnName:'<fmt:message key="label.csr"/>',
	       		  width:10
	       	},
        	{ 
	       		  columnId:"mrQyt",
	       		  columnName:'<fmt:message key="label.mr.qty"/>',
	       		  width:3
	       	},
        	{ 
	       		  columnId:"buyQyt",
	       		  columnName:'<fmt:message key="label.buyquantity"/>',
	       		  width:4
	       	},
        	{ 
	       		  columnId:"uom",
	       		  columnName:'<fmt:message key="label.uom"/>',
	       		  width:3
	       	},
        	{ 
	       		  columnId:"needed",
	       		  columnName:'<fmt:message key="label.needed"/><br><fmt:message key="label.dateformat"/>',
	       		  tooltip:"Y",
	       		  width:6
	       	},
	    	{ 
	       		  columnId:"facilityId",
	       		  columnName:'<fmt:message key="label.facilityid"/>',
	       		  tooltip:"Y",
	       		  width:6
	       	},
	    	{ 
	       		  columnId:"shipToDeliveryPoint",
	       		  columnName:'<fmt:message key="label.deliverypoint"/>',
	       		  tooltip:"Y",
	       		  width:10
	       	}
	       	
];


var containerLabelGridConfig = {
		divName:'containerLabelBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'containerLabelJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'containerLabelGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'containerLabelConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
		submitDefault:true, // the fields in grid are defaulted to be submitted or not.
		noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		variableHeight:false,
		selectChild: 1
		};

var containerLabelConfig = [
            	 {
         		  columnId:"permission"
         		 },
        		 { 
	       		  columnId:"receiptId",
	       		  columnName:'<fmt:message key="label.receiptid"/>',
	       		  width:5
	       		 },
	       		 { 
		    	  columnId:"containerLabelId"
	       		 },
		    	 {
		    	  columnId:"materialDesc"
		    	 },
				 {
		    	 columnId:"mfgLot",
	       		  columnName:'<fmt:message key="label.mfglot"/>',
	       		  tooltip:"Y",
	       		  width:10
				 },
				 {
			      	 columnId:"expireDate",
		       		  columnName:'<fmt:message key="label.expiredate"/>',
		       		  width:7
			      	 
			     },
		    	 {
		    	 columnId:"lotStatus"
		    	 },
		    	 {
			     columnId:"partId"
		    	 },
		    	 {
				     columnId:"partNumbers",
		       		  columnName:'<fmt:message key="label.partnumbers"/>',
		       		tooltip:"Y",
		       		  width:10
			     },
			     {
			     columnId:"compMfgLot"
			     },
			     {
			     columnId:"compDateInserted"
			     },
			     {
			     columnId:"compExpireDate"
			     },
			     {
			     columnId:"specDetail"
			     },
			     {
			     columnId:"specList"
			     },
			     {
		    	 columnId:"vendorShipDate"
			     },
		    	 {
		    	 columnId:"dateOfReceipt",
	       		  columnName:'<fmt:message key="label.dateofreceipt"/>',
	       		  width:7
		    	 },
		    	 {
		    	 columnId:"dateOfManufacture",
	       		  columnName:'<fmt:message key="label.dateofmanufacture"/>',
	       		  width:7
		    	 },
		    	 {
		    	 columnId:"dateOfShipment"
		    	 },
		    	 {
		    	 columnId:"mfgLabelExpireDate"
		    	 },
		      	 {
		    	 columnId:"storageTemp",
	       		  columnName:'<fmt:message key="label.storagetemp"/>',
	       		  width:7
		      	 },
		    	 {
		    	 columnId:"recertNumber",
	       		  columnName:'<fmt:message key="label.recertnumber"/>',
	       		  width:5
		    	 },
		    	 {
		    	 columnId:"certifiedByName",
	       		  columnName:'<fmt:message key="label.certifiedby"/>',
	       		  width:5
		    	 },
		    	 {
		    	 columnId:"certificationDate"
		    	 },
		    	 {
		    	 columnId:"qaStatement"
		    	 },
		    	 {
		    	 columnId:"qualityTrackingNumber"
		    	 },
		    	 {
		       	 columnId:"quantityPrinted"
		    	 },
		       	 {
		    	 columnId:"lastIdPrinted"	 
		       	 },
		       	 {
		    	 columnId:"printUserName",//put at end
	       		  columnName:'<fmt:message key="label.printusername"/>',
	       		  width:7	 
		    	 },
		    	 {
		    	 columnId:"dateInserted",//put at end
	       		  columnName:'<fmt:message key="label.dateinserted"/>',
	       			tooltip:"Y",
	       		  width:7
		    	 },
		    	 {
		    	 columnId:"labelColor"
		    	 }
	]

function resizePackage()
{
	componentGrid.setColWidth(2, j$('#searchMaskTable').width() * .49);
}

</script>
</head>



<body bgcolor="#ffffff" onload="myOnLoad();<c:if test="${!empty param.submitDocumentLabelsPrint}">printReceivingQcDocLabels(${param.sourceHub});</c:if><c:if test="${!empty param.submitReceiptLabelsPrint}">printReceivingQcReceiptLabels(${param.sourceHub});</c:if><c:if test="${!empty param.submitPrint}">printReceivingQcLabels(${param.sourceHub});</c:if>" onresize="setTimeout('resizeFrames();',55);setTimeout('resizePackage()',65);" onunload="closeAllchildren();">
<tcmis:form action="/receivingqcchecklist.do" onsubmit="return submitFrameOnlyOnce();">

<c:set var="receivingQcPermission" value=''/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" facilityId="${sourceHub}">
 	<c:set var="receivingQcPermission" value='Yes'/>
</tcmis:inventoryGroupPermission>

<c:set var="canUpdate" value='N'/>

<c:if test="${receivingQcPermission == 'Yes' && headerInfo[0].qcDate == null && fn:toLowerCase(headerInfo[0].receivingStatus)  == 'qc ready' && fn:toLowerCase(headerInfo[0].qcOk)  == 'y'}">
<c:set var="canUpdate" value='Y'/>
</c:if>
 
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

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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

<div class="interface" id="mainPage" style="">
	<%-- Search Section --%>
	<div id="searchFrameDiv">
		<div class="contentArea">
					<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<div class="roundcont filterContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
						</div>
						<div class="roundContent"><%-- Insert all the search option within this div --%>
							<select name="printType" id="printType" class="selectBox">
							        <option value="RL"><fmt:message key="receivedreceipts.button.receivinglabels" /></option>
							        <option value="EL"><fmt:message key="label.containerlabels" /></option>
							        <option value="DL"><fmt:message key="label.documentlabels" /></option>
							        <option value="RDL"><fmt:message key="label.receiptdetaillables" /></option>
							  </select>
							  <input type="hidden" name="labelQuantity" id="labelQuantity" size="1" value="${headerInfo[0].quantityReceived}" />
							  <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" id="printBtn" value="<fmt:message key="label.print" />" onclick="selectPrintType()"/>
							<c:if test="${canUpdate == 'Y'}"> 
							<input type="checkbox" name="binned" value="Binned" id="binned" class="radioBtns"/><fmt:message key="label.binned"/>
							| <a href="#" onclick="confirmReceivingQcChecklistReport()"><fmt:message key="label.confirm"/></a>
							</c:if> 
						 	<c:if test="${headerInfo[0].itemType == 'ML' && headerInfo[0].newChemRequestId == null}"> | <a href="#" onclick="changeMlItem(); return false;"><fmt:message key="label.changeitem" /></a></c:if>
						 	| <a href="#" onclick="receiptSpecs()"><fmt:message key="label.receiptspecs"/></a>
						 	| <a href="#" onclick="showRadianPo()"><fmt:message key="label.viewpurchaseorder"/></a>
						 	<c:if test="${headerInfo[0].incomingTesting == 'Y'}">
						 	| <a href="#" onclick="startMARStest()"><fmt:message key="label.startmarstest"/></a>
						 	</c:if>
						 	| <a href="#" onclick="showPreviousReceivedQc()"><fmt:message key="label.showreceipthistory"/></a>	
						 	<c:choose>
							 	<c:when test="${headerInfo[0].qcDate == null}">
							 		| <a href="#" onclick="reverseReceipt()"><fmt:message key="reversereceipt.title"/></a>
							 	</c:when>
							 	<c:otherwise>
							 		| <a href="#" onclick="printChecklistReport()"><fmt:message key="label.printqvr"/></a>
							 	</c:otherwise>
						 	</c:choose>	
						 
						 <c:choose>
						 	<c:when test="${canUpdate == 'N'}"> 
						 		<br/><input type="checkbox" name="skipKitLabels" value="Yes" id="skipKitLabels" class="radioBtns"/><fmt:message key="receivedreceipts.label.skipkitlabels"/>
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.receiptid" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].receiptId}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.inventorygroup" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].inventoryGroupName}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.supplier" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].supplierName}</td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.lotstatus" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].lotStatus}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.bin" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].bin}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.promisedate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].promisedDate}" pattern="${dateFormatPattern}"/></td>
										<%-- <td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.mfglot" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].mfgLot}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receivingqc.label.origlot" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].origMfgLot}</td>
										--%>
									</tr>			
									<tr>
									<c:choose>
										   <c:when test="${headerInfo[0].docType == 'IT' && !empty headerInfo[0].customerRmaId}" >	     		
										     	<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.rma" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].customerRmaId}</td>
										   </c:when>      
										   <c:when test="${headerInfo[0].docType == 'IT'}" >   
										   		<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transferRequest" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].transferRequestId}</td>
										   </c:when>
										   <c:when test="${headerInfo[0].docType == 'IA' && !empty headerInfo[0].poNumber }" >                 
												<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.customerowenedinventory" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].poNumber }</td>
											</c:when>  
										   <c:when test="${headerInfo[0].docType == 'IA'}" >
										   		<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.return" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].returnPrNumber}-${headerInfo[0].returnLineItem}</td>
										   </c:when>
										   <c:otherwise>
										   		<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.podaskline" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].radianPo} - ${headerInfo[0].poLine}</td>
										   </c:otherwise>
									 </c:choose>					
										<td class="optionTitleBoldRight" width="1%" nowrap>
											<c:if test="${headerInfo[0].docType != 'IT' && headerInfo[0].docType != 'IA'}"> 
												<fmt:message key="label.quantityordered" />:
											</c:if>
										</td>
										<td class="optionTitleLeft">
											<c:if test="${headerInfo[0].docType != 'IT' && headerInfo[0].docType != 'IA'}"> 
												${headerInfo[0].quantityOrdered}
											</c:if>
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.revisedpromisedate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].revisedDate}" pattern="${dateFormatPattern}"/></td>	
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.item" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].itemId}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.quantityreceived" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].quantityReceived} <c:if test="${headerInfo[0].mvItem != 'N'}">(${headerInfo[0].quantityReceived} X ${headerInfo[0].costFactor} ${headerInfo[0].purchasingUnitOfMeasure} ${headerInfo[0].displayPkgStyle})</c:if></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.arrivalscanusername" />:</td>
										<td class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].arrivalScanUserName}" processMultiLines="false" /></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receivedreceipts.label.dor" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].dateOfReceipt}" pattern="${dateFormatPattern}"/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.unitlabeledper129p" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].unitLabelPrinted}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.arrivalscandate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].arrivalScanDate}" type="both"/></td>
										<%-- <td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receiving.label.packagedqty" /> x <fmt:message key="receiving.label.packagedsize" />:</td>
										<td class="optionTitleLeft"><c:if test="${headerInfo[0].mvItem != 'N'}">${headerInfo[0].quantityReceived} X ${headerInfo[0].costFactor} ${headerInfo[0].purchasingUnitOfMeasure} ${headerInfo[0].displayPkgStyle}</c:if></td>					
										--%>	
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.manufacturerdos" />:</td>
										<td  class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].dateOfShipment}" pattern="${dateFormatPattern}"/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.carrier" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].carrierParentShortName}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transactions.receivername" />:</td>
										<td class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].receiverName}" processMultiLines="false" /></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.actsupshpdate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].vendorShipDate}" pattern="${dateFormatPattern}"/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receiving.label.deliveryticket" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].deliveryTicket}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.receiverdate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].receivedDate}" pattern="${dateFormatPattern}"/></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receivedreceipts.label.dom" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].dateOfManufacture}" pattern="${dateFormatPattern}"/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.certificationnumber" />:</td>
										<c:set var="certificationNumber" value="${headerInfo[0].certificationNumber}"/>
										<c:if test="${certificationNumber == null || certificationNumber == ''}"><c:set var="certificationNumber" value="(N\A)"/></c:if>
										<td class="optionTitleLeft"><c:if test="${headerInfo[0].qcDate != null}">${certificationNumber}</c:if></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.qcuser" />:</td>
										<td class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].qcUserName}" processMultiLines="false" /></td>
									</tr>								
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.minexpdate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].minimumExpireDate}" pattern="${dateFormatPattern}"/></td>	
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.incomingtesting" />:</td>
										<td class="optionTitleLeft"><input type="checkbox" name="incomingTesting" value="" id="incomingTesting" class="radioBtns" disabled="disabled" <c:if test="${headerInfo[0].incomingTesting == 'Y'}">checked</c:if> /></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.qcdate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].qcDate}" pattern="${dateFormatPattern}"/></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transactions.storagetemp" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].storageTemp}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.hazcomlabel" />:</td>
										<td class="optionTitleLeft"><input type="checkbox" name="hazcomLabelFlag" value="${headerInfo[0].hazcomLabelFlag}" id="hazcomLabelFlag" class="radioBtns" disabled="disabled"  <c:if test="${headerInfo[0].hazcomLabelFlag == 'Y'}">checked</c:if>/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.newmsdsreceived" />:</td>
										<td class="optionTitleLeft"><input type="checkbox" name="newMsdsRevReceivedFlag" value="${headerInfo[0].newMsdsRevReceivedFlag}" id="newMsdsRevReceivedFlag" class="radioBtns" disabled="disabled"  <c:if test="${headerInfo[0].newMsdsRevReceivedFlag == 'Y'}">checked</c:if>/></td>				
									</tr>	
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.cagecode" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].supplierCageCode}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.addqualitynote" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].qualityTrackingNumber}</td>
									</tr>	
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<c:choose>
           									<c:when test="${(headerInfo[0].orderQtyUpdateOnReceipt == 'y' || headerInfo[0].orderQtyUpdateOnReceipt == 'Y')}" >
												<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receiving.label.closepoline" />:</td>
												<td class="optionTitleLeft">
												     <input type="checkbox" name="closePoLine" id="closePoLine" value="${headerInfo[0].orderQtyUpdateOnReceipt}" disabled="disabled"  <c:if test="${headerInfo[0].closePoLine != null}">checked</c:if>/>
												</td>
											</c:when>
											<c:otherwise>
									             <td>&nbsp;</td><td>&nbsp;</td>
									        </c:otherwise>
								        </c:choose>
									</tr>	
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.description" />:</td>
										<td colspan="9" class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].lineDesc}" processMultiLines="true" /></td>							
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.internalreceiptnotes" />:</td>
										<td colspan="9" class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].internalReceiptNotes}" processMultiLines="true" /></td>							
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transactions.receiptnotes" />:</td>
										<td colspan="9" class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].notes}" processMultiLines="true" /></td>
									</tr>
								</table>
						 	</c:when>
						 	<c:otherwise>
						 	<c:set var="statusUpdatePermission" value=''/>
							  <tcmis:inventoryGroupPermission indicator="true" userGroupId="PickStatusUpd" inventoryGroup="${headerInfo[0].inventoryGroup}">
							   <c:set var="statusUpdatePermission" value='Yes'/>
							  </tcmis:inventoryGroupPermission>	 
							  <c:set var="qualityControlItem" value='${headerInfo[0].qualityControlItem}'/>
							    <c:set var="onlynonPickableStatusPermission" value=''/>
								  <tcmis:inventoryGroupPermission indicator="true" userGroupId="onlynonPickableStatus" inventoryGroup="${headerInfo[0].inventoryGroup}">
								   <c:set var="onlynonPickableStatusPermission" value='Yes'/>
								  </tcmis:inventoryGroupPermission>			 	
						 	| <a href="#" onclick="revertStatus()"><fmt:message key="label.revertimage"/></a>			
      						<br/><input type="checkbox" name="skipKitLabels" value="Yes" id="skipKitLabels" class="radioBtns"/><fmt:message key="receivedreceipts.label.skipkitlabels"/>
			
						 		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.receiptid" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].receiptId}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.inventorygroup" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].inventoryGroupName}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.supplier" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].supplierName}</td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.lotstatus" />:</td>
										<td class="optionTitleLeft">
										      <select name="lotStatus" id="lotStatus" class="selectBox" onchange="checkReceiptStatus()">
									              <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
										               <c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
										                <c:choose>
										                 <c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}">
										                 </c:when>
										                 <c:otherwise>
										                  <c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
										                     <c:set var="lotStatusValue" value=''/>
										                  </c:if>
										                 </c:otherwise>
										                </c:choose>
										                <c:set var="jspLabel" value=""/>
										   				<c:if test="${fn:length(vvlotstatus.current.jspLabel) > 0}"><c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set>
										   				<c:set var="jspLabelEsc"><fmt:message key="${jspLabel}"/></c:set>
										   				</c:if>
										                <c:choose>
										                <c:when test="${headerInfo[0].lotStatus == vvlotstatus.current.lotStatus}" >
										                 <c:if test="${vvlotstatus.current.lotStatus != 'Write Off Approved' && vvlotstatus.current.lotStatus != 'Write Off Rejected' }">
										                    <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>" selected="selected"><tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" /></option>
										                   </c:if>
										                </c:when>
										                <c:otherwise>
										                 <c:if test="${vvlotstatus.current.lotStatus != 'Write Off Approved' && vvlotstatus.current.lotStatus != 'Write Off Rejected' }">
										                    <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" /></option>
										                 </c:if>
										                </c:otherwise>
									               		</c:choose>
									              </c:forEach>
								             </select>
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.bin" />:</td>
										<td class="optionTitleLeft"> 
										<select class="selectBox" name="bin" id="bin">          
											<option value="${headerInfo[0].bin}">${headerInfo[0].bin}</option>
										</select>
										<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin0" value="+" onclick="showVvHubBins(0)"/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.promisedate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].promisedDate}" pattern="${dateFormatPattern}"/></td>
										<%-- <td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.mfglot" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].mfgLot}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receivingqc.label.origlot" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].origMfgLot}</td>
										--%>
									</tr>			
									<tr>
									<c:choose>
										   <c:when test="${headerInfo[0].docType == 'IT' && !empty headerInfo[0].customerRmaId}" >	     		
										     	<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.rma" />:</td>
												<td class="optionTitleLeft" width="1%">${headerInfo[0].customerRmaId}</td>
										   </c:when>      
										   <c:when test="${headerInfo[0].docType == 'IT'}" >   
										   		<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transferRequest" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].transferRequestId}</td>
										   </c:when>
										   <c:when test="${headerInfo[0].docType == 'IA' && !empty headerInfo[0].poNumber}" >                 
												<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.customerowenedinventory" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].poNumber }</td>
											</c:when>  
										   <c:when test="${headerInfo[0].docType == 'IA'}" >
										   		<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.return" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].returnPrNumber}-${headerInfo[0].returnLineItem}</td>
										   </c:when>
										   <c:otherwise>
										   		<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.podaskline" />:</td>
												<td class="optionTitleLeft">${headerInfo[0].radianPo} - ${headerInfo[0].poLine}</td>
										   </c:otherwise>
									 </c:choose>					
										<td class="optionTitleBoldRight" width="1%" nowrap>
											<c:if test="${headerInfo[0].docType != 'IT' && headerInfo[0].docType != 'IA'}"> 
												<fmt:message key="label.quantityordered" />:
											</c:if>
										</td>
										<td class="optionTitleLeft" width="1%" >
											<c:if test="${headerInfo[0].docType != 'IT' && headerInfo[0].docType != 'IA'}"> 
												${headerInfo[0].quantityOrdered}
											</c:if>
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.revisedpromisedate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].revisedDate}" pattern="${dateFormatPattern}"/></td>	
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.item" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].itemId}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.quantityreceived" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].quantityReceived} <c:if test="${headerInfo[0].mvItem != 'N'}">(${headerInfo[0].quantityReceived} X ${headerInfo[0].costFactor} ${headerInfo[0].purchasingUnitOfMeasure} ${headerInfo[0].displayPkgStyle})</c:if></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.arrivalscanusername" />:</td>
										<td class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].arrivalScanUserName}" processMultiLines="false" /></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receivedreceipts.label.dor" />:</td>
										<td class="optionTitleLeft" width="1%" >									
										    <input name='beforeSystemDate'
												id='beforeSystemDate' type="hidden"
												value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
							                 <input type="text" name="dateOfReceipt" id="dateOfReceipt" 
							                   onClick="return getCalendar(document.genericForm.dateOfReceipt,document.genericForm.date60,null,null,document.genericForm.today);" value="<fmt:formatDate value="${headerInfo[0].dateOfReceipt}" pattern="${dateFormatPattern}"/>" size="10" maxlength="10" class="inputBox pointer">																
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.unitlabeledper129p" />:</td>
										<td class="optionTitleLeft"> 
											<c:if test="${headerInfo[0].polchemIg == 'Y' && headerInfo[0].doNumberRequired=='N'}"><input type="checkbox" name="unitLabelPrinted" value="${headerInfo[0].unitLabelPrinted}" id="unitLabelPrinted" class="radioBtns" onchange="(this.checked ? this.value='Y':this.value='N');unitLabelPartNumber();"/></c:if>
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.arrivalscandate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].arrivalScanDate}" type="both"/></td>
								
										<%-- <td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receiving.label.packagedqty" /> x <fmt:message key="receiving.label.packagedsize" />:</td>
										<td class="optionTitleLeft"><c:if test="${headerInfo[0].mvItem != 'N'}">${headerInfo[0].quantityReceived} X ${headerInfo[0].costFactor} ${headerInfo[0].purchasingUnitOfMeasure} ${headerInfo[0].displayPkgStyle}</c:if></td>					
										--%>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.manufacturerdos" />:</td>
										<td  class="optionTitleLeft" width="1%" >
											<input type="text" name="dateOfShipment" id="dateOfShipment" 
                   								onClick="return getCalendar(document.genericForm.dateOfShipment,null,null,document.genericForm.dateOfManufacture,document.genericForm.today);" value="<fmt:formatDate value="${headerInfo[0].dateOfShipment}" pattern="${dateFormatPattern}"/>" size="10" maxlength="10" class="inputBox pointer">
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.carrier" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].carrierParentShortName}</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transactions.receivername" />:</td>
										<td class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].receiverName}" processMultiLines="false" /></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.actsupshpdate" />:</td>
										<td class="optionTitleLeft" width="1%" >
										   <input type="text" readonly name="vendorShipDate" id="vendorShipDate"
									           onClick="return getCalendar(document.genericForm.vendorShipDate,null,null,null,(document.genericForm.dateOfReceipt.value=='')?document.genericForm.today:document.genericForm.dateOfReceipt);"
								               value="<fmt:formatDate value="${headerInfo[0].vendorShipDate}" pattern="${dateFormatPattern}"/>" size="10" maxlength="10" class="inputBox pointer">
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receiving.label.deliveryticket" />:</td>
										<td class="optionTitleLeft">
										    <input type="text" name="deliveryTicket" id="deliveryTicket" value="<c:out value="${headerInfo[0].deliveryTicket}"/>" size="15" maxlength="50" class="inputBox">
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.receiverdate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].receivedDate}" pattern="${dateFormatPattern}"/></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receivedreceipts.label.dom" />:</td>
										<td class="optionTitleLeft" width="1%" >
											<input type="text" name="dateOfManufacture" id="dateOfManufacture" 
                   								onclick="return getCalendar(document.genericForm.dateOfManufacture,null,null,null,(document.genericForm.dateOfShipment.value=='')?document.genericForm.today:document.genericForm.dateOfShipment);" value="<fmt:formatDate value="${headerInfo[0].dateOfManufacture}" pattern="${dateFormatPattern}"/>" size="10" maxlength="10" class="inputBox pointer">
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.certificationnumber" />:</td>
										<c:set var="certificationNumber" value="${headerInfo[0].certificationNumber}"/>
										<c:if test="${certificationNumber == null || certificationNumber == ''}"><c:set var="certificationNumber" value="(N\A)"/></c:if>
										<td class="optionTitleLeft"><c:if test="${headerInfo[0].qcDate != null}">${certificationNumber}</c:if></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.qcuser" />:</td>
										<td class="optionTitleLeft"><tcmis:jsReplace value="${headerInfo[0].qcUserName}" processMultiLines="false" /></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.minexpdate" />:</td>
										<td class="optionTitleLeft" width="1%" ><fmt:formatDate value="${headerInfo[0].minimumExpireDate}" pattern="${dateFormatPattern}"/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.incomingtesting" />:</td>
										<td class="optionTitleLeft"><input type="checkbox" name="incomingTesting" value="" id="incomingTesting" class="radioBtns" disabled="disabled" <c:if test="${headerInfo[0].incomingTesting == 'Y'}">checked</c:if>/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.qcdate" />:</td>
										<td class="optionTitleLeft"><fmt:formatDate value="${headerInfo[0].qcDate}" pattern="${dateFormatPattern}"/></td>
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transactions.storagetemp" />:</td>
										<td class="optionTitleLeft">${headerInfo[0].storageTemp}</td>
							
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.hazcomlabel" />:</td>
										<td class="optionTitleLeft"><input type="checkbox" name="hazcomLabelFlag" value="${headerInfo[0].hazcomLabelFlag}" id="hazcomLabelFlag" class="radioBtns" disabled="disabled" <c:if test="${headerInfo[0].hazcomLabelFlag == 'Y'}">checked</c:if>/></td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.newmsdsreceived" />:</td>
										<td class="optionTitleLeft"><input type="checkbox" name="newMsdsRevReceivedFlag" value="${headerInfo[0].newMsdsRevReceivedFlag}" id="newMsdsRevReceivedFlag" class="radioBtns" disabled="disabled"   <c:if test="${headerInfo[0].newMsdsRevReceivedFlag == 'Y'}">checked</c:if>/></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.cagecode" />:</td>					
										<td class="optionTitleLeft">		
											<input type="text" name="supplierCageCode" id="supplierCageCode" value="${headerInfo[0].supplierCageCode}" size="20" maxlength="8" class="inputBox"/>					
										</td>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.addqualitynote" />:</td>
										<td class="optionTitleLeft" width="1%" >
										         <input type="text" name="qualityTrackingNumber" id="qualityTrackingNumber" value="${headerInfo[0].qualityTrackingNumber}" size="10" maxlength="30" class="inputBox"/>					
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<c:choose>
           									<c:when test="${(headerInfo[0].orderQtyUpdateOnReceipt == 'y' || headerInfo[0].orderQtyUpdateOnReceipt == 'Y')}" >
												<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="receiving.label.closepoline" />:</td>
												<td class="optionTitleLeft">
												     <input type="checkbox" name="closePoLine" id="closePoLine" value="${headerInfo[0].orderQtyUpdateOnReceipt}" onchange="(this.checked ? this.value='Y':this.value='N');"  <c:if test="${headerInfo[0].closePoLine != null}">checked</c:if>/>
												</td>
											</c:when>
											<c:otherwise>
									             <td>&nbsp;</td><td>&nbsp;</td>
									        </c:otherwise>
								        </c:choose>
									</tr>		
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.description" />:</td>
										<td colspan="9" class="optionTitleLeft" width="1%" ><tcmis:jsReplace value="${headerInfo[0].lineDesc}" processMultiLines="true" /></td>							
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="label.internalreceiptnotes" />:</td>
										<td colspan="9" class="optionTitleLeft" width="1%" >
										    <textarea name="internalReceiptNotes" id="internalReceiptNotes" cols="113" rows="2" class="inputBox"><tcmis:jsReplace value="${headerInfo[0].internalReceiptNotes}" processMultiLines="true" /></textarea>
										</td>						
									</tr>
									<tr>
										<td class="optionTitleBoldRight" width="1%" nowrap><fmt:message key="transactions.receiptnotes" />:</td>
										<td colspan="9" class="optionTitleLeft" width="1%" >
										    <textarea name="notes" id="notes" cols="113" rows="2" class="inputBox"><tcmis:jsReplace value="${headerInfo[0].notes}" processMultiLines="true" /></textarea>
										</td>
									</tr>
								</table>
						 	</c:otherwise> 
						 </c:choose>
					
			
						<c:set var="componentDataCount" value='${0}'/>
						<c:set var="componentDivHeight" value='${75}'/>
						<script type="text/javascript">
						<!--
						var componentJsonData = new Array();
						var componentJsonData = {
						rows:[
						 <c:forEach var="bean" items="${headerInfo}" varStatus="status">
							<c:set var="componentExpireDate"><fmt:formatDate value="${bean.expireDate}" pattern="${dateFormatPattern}"/></c:set>
							<c:set var="componentExpireDateDisplay" value="${componentExpireDate}"/>
						    <c:if test="${componentExpireDate == '01-Jan-3000'}"><c:set var="componentExpireDateDisplay"><fmt:message key="label.indefinite"/></c:set></c:if>
						    
							<c:set var="mfgLabelExpireDate"><fmt:formatDate value="${bean.mfgLabelExpireDate}" pattern="${dateFormatPattern}"/></c:set>
							<c:set var="mfgLabelExpireDateDisplay" value="${mfgLabelExpireDate}"/>
						    <c:if test="${mfgLabelExpireDate == '01-Jan-3000'}"><c:set var="mfgLabelExpireDateDisplay"><fmt:message key="label.indefinite"/></c:set></c:if>
				     
				                <c:if test="${status.index > 0}">,</c:if>
                                {id:${status.index +1},
					            data:[
					            '${canUpdate}',
					       		'<tcmis:jsReplace value="${bean.materialDesc}" processMultiLines="true" />',
					            '<tcmis:jsReplace value="${bean.packaging}" processMultiLines="true" />',
					            '<tcmis:jsReplace value="${bean.mfgLot}" processMultiLines="true" />',
					           <c:choose>
						           <c:when test="${canUpdate == 'Y'}" >
						           		'<input class="inputBox pointer" id="expireDateDisplay${componentDataCount}" type="text" value="${componentExpireDateDisplay}" size="9" readonly onClick="return myGetCalendarE(${componentDataCount})" onchange="setExpDateChanged(${componentDataCount})"/>',	           
						           </c:when>
						           <c:otherwise>
						           		'${componentExpireDateDisplay}',
						           </c:otherwise>
					           </c:choose>
				           		'<fmt:formatDate value="${bean.expireDate}" pattern="${dateFormatPattern}"/>',
				           	    '${bean.componentId}',
				                <c:choose>
						           <c:when test="${canUpdate == 'Y'}" >
						           		'<input class="inputBox pointer" id="mfgLabelExpireDateDisplay${componentDataCount}" type="text" value="${mfgLabelExpireDateDisplay}" size="9" readonly onClick="return myGetCalendarMFG(${componentDataCount})" onchange="setMFGExpDateChanged(${componentDataCount})"/>',	           
						           </c:when>
						           <c:otherwise>
						           		'${mfgLabelExpireDateDisplay}',
						           </c:otherwise>
					           </c:choose>
				           		'${mfgLabelExpireDate}',
				           		'${bean.dateOfRepackaging}',
				           	    <c:choose>
						           <c:when test="${canUpdate == 'Y'}" >
						           		'<input class="inputBox pointer" id="dateOfRepackagingDisplay${componentDataCount}" type="text" value="<fmt:formatDate value="${bean.dateOfRepackaging}" pattern="${dateFormatPattern}"/>" size="9" readonly onClick="return myGetCalendarDOP(${componentDataCount})" onchange="setDOPChanged(${componentDataCount})"/>',	           
						           </c:when>
						           <c:otherwise>
						           		'<fmt:formatDate value="${bean.dateOfRepackaging}" pattern="${dateFormatPattern}"/>',
						           </c:otherwise>
					           </c:choose>

					            ]}
					           <c:set var="componentDataCount" value='${componentDataCount+1}'/>
					           <c:set var="componentDivHeight" value='${componentDivHeight+28}'/>
				        </c:forEach>
						]};
						
						 //-->
						</script>
						<div id="componentBean" style="width:97%;height:${componentDivHeight}px;"></div>
					</div>
						<div class="roundbottom">
							<div class="roundbottomright">
								<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
		</div>
	</div>

<div class="backGroundContent"> <!-- start of backGroundContent -->
	<div id="newChemTabs">
	 <ul id="firstTabList">
	 </ul>
	</div>
<!-- Result Frame Begins -->
<div id="resultFrameDiv1" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv1" >

<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<div id="section1" class="roundcont filterContainer" style="width:100%">
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
      </div>
       <div id="docUpdateLink" style="display: none"> <%-- mainUpdateLinks Begins --%>
            		<a href="#" onclick="updateDocs();"><fmt:message key="label.update" /></a>
            		| <a href="#" onclick="addDoc();"><fmt:message key="label.add" /></a>		
      </div>
      
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>
	<table class="tableSearch" border="0" style="width:100%">
		<tr>
    		<td>
			  	<div id="tabsdiv1">
				 	<%-- data for header tab here --%>
				 	<div id="tab0">
				<div style="width:85%; height: 250px; overflow-y: scroll;">
				 		<c:if test="${showHubSpecificNotes == 'Y'}">
				 			    <a href="#" onclick="openOldReceivingInformation()"><fmt:message key="oldReceivingInformation"/></a>
						 		<a href="#" onclick="openNewReceivingInformation()"><fmt:message key="newReceivingInformation"/></a><br/><br/>
					 			<span class="optionTitleLeft"><fmt:message key="label.hubnotes" />:</span>						      
						        	<table>		
							        	<tr>
											<td class="optionTitleLeft"><fmt:message key="label.shelflifehubentered" />:</td>
											<td class="optionTitleLeft">${hubSpecificNotesColl[0].shelfLife}</td>	
										</tr>				
										<tr>
											<td class="optionTitleLeft"><fmt:message key="label.storagerequirementhubentered" />:</td>
											<td class="optionTitleLeft">${hubSpecificNotesColl[0].storageReqs}</td>	
										</tr>
										<tr>
											<td class="optionTitleLeft"><fmt:message key="label.testrequirementhubentered" />:</td>
											<td class="optionTitleLeft">${hubSpecificNotesColl[0].reqTesting}</td>	
										</tr>
								 	</table>
							 	<span class="optionTitleLeft"><fmt:message key="label.noteshubentered" />:</span><br/>	
								<c:forEach var="bean" items="${hubSpecificNotesColl}" varStatus="status">
									<tcmis:jsReplace value="${bean.notes}" processMultiLines="true" /><br/>
							    </c:forEach>	    						    	    
						</c:if>	
						
						<c:if test="${!empty engEvalColl}">				 	
							 	${engEvalColl[0].companyId} <fmt:message key="label.engevalfor" /> ${engEvalColl[0].facilityId} <fmt:message key="label.requestedby" /> ${engEvalColl[0].requestor}, <fmt:message key="label.part" />: ${engEvalColl[0].facPartNo}, <fmt:message key="label.mr" />: ${engEvalColl[0].prNumber}-${engEvalColl[0].lineItem}	
								<br/>
						</c:if>	
						
						<c:if test="${!empty headerInfo[0].deliveryComments}">				 	
							 	<span class="optionTitleLeft"><fmt:message key="label.receivingnotes" />:</span>	
								<tcmis:jsReplace value="${headerInfo[0].deliveryComments}" processMultiLines="true" /><br/>
						</c:if>	
						<c:if test="${!empty poNotesColl}">	
								<span class="optionTitleLeft"><fmt:message key="label.ponotes" />:</span>						      
					        	<table>	
						        	<c:forEach var="bean" items="${poNotesColl}" varStatus="status">
						        	<tr>
										<td class="optionTitleLeft">${bean.poNoteDate}</td>
										<td class="optionTitleLeft"><tcmis:jsReplace value="${bean.poNote}" processMultiLines="true" /></td>	
									</tr>
							        </c:forEach>						        
							 	</table>
						</c:if>	
						<c:if test="${!empty customerOrderInternalOrderNotes}">	
						 		<span class="optionTitleLeft"><fmt:message key="label.customerordernotes" />:</span>
						 		<c:forEach var="bean" items="${customerOrderInternalOrderNotes}" varStatus="status">
						 		<tcmis:jsReplace value="${bean.customerOrderNote}" processMultiLines="true" /><br/>
						 		</c:forEach><br/>	
						</c:if>			
						<c:if test="${!empty customerOrderInternalOrderNotes}">	
						 		<span class="optionTitleLeft"><fmt:message key="label.internalnotes" />:</span>
						 		<c:forEach var="bean" items="${customerOrderInternalOrderNotes}" varStatus="status">
						 		<tcmis:jsReplace value="${bean.internalNote}" processMultiLines="true" /><br/>
						 		</c:forEach>	
						</c:if>
						<c:if test="${!empty saicVerifiedInfo}">
							<c:set var="saicVerifiedVar" value='Please collect and forward NSN Size Data to Operations Manager'/>
							<c:if test="${saicVerifiedInfo[0].verifiedBy != null && saicVerifiedInfo[0].verifiedBy != ''}">
								<c:set var="saicVerifiedVar">
									NSN size Data OK(verified by ${saicVerifiedInfo[0].verifiedBy} on <fmt:formatDate value="${saicVerifiedInfo[0].verifiedDate}" pattern="${dateFormatPattern}"/>)
								</c:set>
							</c:if>
							 <br/>${saicVerifiedVar}<br/>
						</c:if>				
						<c:if test="${!empty saicPONotes}">
									<br/><span class="optionTitleLeft"><fmt:message key="label.saicponotes" />:</span><br/>
							 		<c:forEach var="bean" items="${saicPONotes}" varStatus="status">
							 			    	 	${bean.clauseNumber}: ${bean.clauseShortText}<br/>
							 		</c:forEach>
						</c:if>
					 	</div>
				 	</div>
				 	<div id="tab1">
				 		<div id="catalogBean" style="width:85%;height:250px;">
				 				<div id="catalogBeanTransitPage" style="display: none;" class="optionTitleBoldCenter"><br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle"></div>
						</div>
							<script type="text/javascript">
							<!--
							catalogJsonData = new Array();
							catalogJsonData.rows = new Array();
							 //-->
							</script>
					 </div>
					 <div id="tab3">						
						<div id="inboundDocBean" style="width:85%;height:250px;"></div>
							<c:set var="inboundDocDataCount" value='${0}'/>
							<c:set var="inboundDocDataNoFileNameCount" value='${0}'/>
							<script type="text/javascript">
							<!--
							var inboundDocJsonData = new Array();
							var inboundDocJsonData = {
							rows:[
								<c:forEach var="bean" items="${inboundDocDetailColl}" varStatus="status">
					                <c:if test="${status.index > 0}">,</c:if>
					    			<c:set var="edit" value='Y'/>
					    			<c:choose>
						    			 <c:when test="${fn:length(bean.documentFileName) == 0}">
						    			 	<c:set var="edit" value='N'/>
						    			 	<c:set var="inboundDocDataNoFileNameCount" value='${inboundDocDataNoFileNameCount+1}'/>
						    			 </c:when>
						    			 <c:when test = "${canUpdate == 'Y'}">
						    			 	<c:set var="edit" value='Y'/>
										 </c:when>
										 <c:otherwise>
											<c:set var="edit" value='N'/>
										 </c:otherwise>
					    			 </c:choose>
	                                {id:${status.index +1},
						            data:[
							          '${edit}',
							          'N',
							          '${bean.inboundShipmentDocumentId}',
							   		   '<fmt:formatDate value="${bean.documentFileDate}" pattern="${dateFormatPattern}"/>',
							   		   '${edit}',
							           '<tcmis:jsReplace value="${bean.documentName}" processMultiLines="false" />',
							           '${edit}',
							           '${bean.documentType}',
							           '${edit}',
							           '${bean.companyId}',
							           '<c:if test="${!fn:startsWith(bean.documentFileName, 'http')}">${documentLocationUrl}</c:if>${bean.documentFileName}',
							           '${bean.inboundShipmentId}',
							           '${bean.initialScanUserName}',
							           '<fmt:formatDate value="${bean.dateUpdated}" pattern="${dateFormatPattern}"/>',
							           '<fmt:formatDate value="${bean.dateInserted}" pattern="${dateFormatPattern}"/>',         
							           '${param.search}',
							           '<fmt:formatDate value="${bean.documentFileDate}" pattern="${dateFormatPattern}"/>',
							           '${bean.documentFileName}',
							           'N'
										]}
						           <c:set var="inboundDocDataCount" value='${inboundDocDataCount+1}'/>
						        </c:forEach>
							]};
							<c:choose>
							<c:when test = "${inboundDocDataNoFileNameCount == inboundDocDataCount}">
								var showUpdateLink = false;
							</c:when>
							<c:when test = "${canUpdate == 'Y'}">
								var showUpdateLink = true;
							</c:when>
							<c:otherwise>
								var showUpdateLink = false;
							</c:otherwise>
						</c:choose>
					
							 //-->
							</script>
					 	</div>
				 	<div id="tab4" style="height:250px;">
				 		<div id="wrapper">
					    <div id="slide-wrap">
					        <div id="inner-wrap"> 				        
					        	<table id="imagesTbl">
						        	<tr>
						        	<c:forEach var="bean" items="${imageDocDetailColl}" varStatus="status">
										<td id ="${bean.documentId}" style="padding-left: 10px; padding-right: 10px;"><a href="#" onclick="children[children.length] = openWinGeneric('receiptImages.do?receiptId=${bean.receiptId}&image=${status.count}','receiptImageViewer', '700', '760', 'yes', '80', '80', 'no')"><img src="/tcmIS/haas/thumbnail.do?image=${bean.documentUrl}"/></a></td>
							        </c:forEach>						        
						        	</tr>
								 	<tr>
								 		<c:forEach var="bean" items="${imageDocDetailColl}" varStatus="status">
											<td style="padding-left: 10px; padding-right: 10px;">${bean.documentTypeDisplay}</td>	
										</c:forEach>								 		
								 	</tr>
							 	</table>
				 			</div>
				 		</div>
				 		</div>		
				 	</div>
				 	
				 	<div id="tab5">
						<div id="receiptDocBean" style="width:85%;height:250px;"></div>
							<c:set var="receiptDocDataCount" value='${0}'/>
							<script type="text/javascript">
							<!--
							var receiptDocJsonData = new Array();
							var receiptDocJsonData = {
							rows:[
								<c:forEach var="bean" items="${receiptDocDetailColl}" varStatus="status">
					                <c:if test="${status.index > 0}">,</c:if>
	                                {id:${status.index +1},
						            data:[
							          'N',	        
							           '${bean.documentType}',
							           '<tcmis:jsReplace value="${bean.companyName}" processMultiLines="false" />',
							           '<tcmis:jsReplace value="${bean.documentName}" processMultiLines="false" />',
							           '<fmt:formatDate value="${bean.documentDate}" pattern="${dateFormatPattern}"/>',
							           '${bean.documentUrl}',
							           '${bean.documentId}'
										]}
						           <c:set var="receiptDocDataCount" value='${receiptDocDataCount+1}'/>
						        </c:forEach>
							]};
							
							 //-->
							</script>
					 </div> 	
					 	<div id="tab9">
							<div id="containerLabelBean" style="width:88%;height:250px;">
								<div id="containerLabelTransitPage" style="display: none;" class="optionTitleBoldCenter"><br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle"></div>
							</div>
							
							<script type="text/javascript">
							<!--
							containerLabelJsonData = new Array();
							containerLabelJsonData.rows = new Array();
							 //-->
							</script>
					 	</div>			 	
				 </div>
				</td> 	
			</tr>	
		</table>
        
   <%-- result count and time --%>
   <div id="footer1" class="messageBar"></div>


<div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
    </div>

</div>
<!-- results end -->
</div>

</div><!-- close of tab section 1 -->

</div> <!-- close of background 1 -->

<div class="backGroundContent"> <!-- start of backGroundContent -->
	<div id="newChemTabs">
	 <ul id="secondTabList">
	 </ul>
	</div>
<!-- Result Frame Begins -->
<div id="resultFrameDiv2" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv2" style="width:100%">

<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<div id="section2" class="roundcont filterContainer" style="width:100%">
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
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>
	<table class="tableSearch" border="0" style="width:100%">
		<tr>
    		<td>
			  	<div id="tabsdiv2">
				 	<%-- data for header tab here --%>		 
					<div id="tab2">					
							<div id="msdsBean" style="width:88%;height:250px;">
								<div id="msdsTransitPage" style="display: none;" class="optionTitleBoldCenter"><br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle"></div>
							</div>
							
							<script type="text/javascript">
							<!--
							msdsJsonData = new Array();
							msdsJsonData.rows = new Array();
							 //-->
							</script>
				 	</div>
				 	<div id="tab6">
						<div id="flowDownBean" style="width:88%;height:250px;">
						 	<div id="flowDownBeanTransitPage" style="display: none;" class="optionTitleBoldCenter">
								<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
							</div>
						 	</div>
						 		<script type="text/javascript">
								<!--
									flowDownJsonData = new Array();
									flowDownJsonData.rows = new Array();
								 //-->
								</script>
					 	</div>	 
					 	<div id="tab7">
					 		<div id="specBean" style="width:85%;height:250px;">
						 	<div id="specBeanTransitPage" style="display: none;" class="optionTitleBoldCenter">
								<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
							</div>
						 	</div>
						 		<script type="text/javascript">
								<!--
						 			specJsonData = new Array();
									specJsonData.rows = new Array();
								 //-->
								</script>
					
					 	</div>	 	
					 	
						<div id="tab8">
							<div id="associatedPRsBean" style="width:88%;height:250px;">
								<div id="associatedPRsTransitPage" style="display: none;" class="optionTitleBoldCenter"><br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle"></div>
							</div>

							<script type="text/javascript">
							<!--
							associatedPRsJsonData = new Array();
							associatedPRsJsonData.rows = new Array();
							 //-->
							</script>
					 	</div>	 		 
				 </div>
				</td> 	
			</tr>	
		</table>
        
   <%-- result count and time --%>
   <div id="footer2" class="messageBar">

   </div>

<div class="roundbottom">

    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
    </div>

</div>
<!-- results end -->
					            <!-- Transit Page Begins -->
					<div id="transitDailogWinUpdate"  style="display:none" class="optionTitleBoldCenter">
					</div>
					<div id="transitDailogWinBody" style="display:none">
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

</div>

</div><!-- close of tab section 1 -->

</div> <!-- close of background 1 -->

</div> <!-- close of interface -->



<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->

<div id="hiddenElements" style="display: none;">
<input name="specTotalLines" id="specTotalLines" value="" type="hidden"/>
<input name="flowDownTotalLines" id="flowDownTotalLines" value="" type="hidden"/>
<input name="inboundDocTotalLines" id="inboundDocTotalLines" value="<c:out value="${inboundDocDataCount + 1}"/>" type="hidden"/>
<input name="receiptDocTotalLines" id="receiptDocTotalLines" value="<c:out value="${receiptDocDataCount + 1}"/>" type="hidden"/>
<input name="catalogTotalLines" id="catalogTotalLines" value="<c:out value="${catalogDataCount + 1}"/>" type="hidden"/>
<input name="msdsTotalLines" id="msdsTotalLines" value="" type="hidden"/>
<input name="associatedPRsTotalLines" id="associatedPRsTotalLines" value="" type="hidden"/>
<input name="containerLabelTotalLines" id="containerLabelTotalLines" value="" type="hidden"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input type="hidden" name="userAction" id="userAction" value=""/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${param.startSearchTime}"/>
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
<input type="hidden" name="searchType" id="searchType" value="${param.searchType}"/>
<input type="hidden" name="searchWhat" id="searchWhat" value="${param.searchWhat}"/>
<input type="hidden" name="search" id="search" value="${param.search}"/>
<input type="hidden" name="sourceHub" id="sourceHub" value="${param.sourceHub}"/>
<input type="hidden" name="sourceHubName" id="sourceHubName" value="${param.sourceHubName}"/>
<input type="hidden" name="sort" id="sort" value="${param.sort}"/>
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input name="personnelId" id="personnelId" type="hidden" value="${personnelBean.personnelId}" />
<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}" />
<input name="receiptId" id="receiptId" type="hidden" value="${headerInfo[0].receiptId}" />
<input name="shipToCompanyId" id="shipToCompanyId" type="hidden" value="${headerInfo[0].shipToCompanyId}" />
<input name="itemId" id="itemId" type="hidden" value="${headerInfo[0].itemId}" />
<input name="radianPo" id="radianPo" type="hidden" value="${headerInfo[0].radianPo}" />
<input name="poLine" id="poLine" type="hidden" value="${headerInfo[0].poLine}" />
<input name="amendment" id="amendment" type="hidden" value="${headerInfo[0].amendment}" />
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${headerInfo[0].inventoryGroup}" />
<input name="hub" id="hub" type="hidden" value="${headerInfo[0].hub}" />
<input name='todayoneyear' id='todayoneyear' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-365" datePattern="${dateFormatPattern}"/>'  />
<input type="hidden" name="qualityControlItem" id="qualityControlItem" value="${headerInfo[0].qualityControlItem}"/>
<input type="hidden" name="lotStatusRootCause" id="lotStatusRootCause" value="${headerInfo[0].lotStatusRootCause}"/>
<input type="hidden" name="responsibleCompanyId" id="responsibleCompanyId" value="${headerInfo[0].responsibleCompanyId}"/>
<input type="hidden" name="lotStatusRootCauseNotes" id="lotStatusRootCauseNotes" value="${headerInfo[0].lotStatusRootCauseNotes}"/>
<input name='date60' id='date60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-60" datePattern="${dateFormatPattern}"/>'/> 
<input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'/>
<input type="hidden" name="manageKitsAsSingleUnit" id="manageKitsAsSingleUnit" value="${headerInfo[0].manageKitsAsSingleUnit}"/>
<input type="hidden" name="mvItem" id="mvItem" value="${headerInfo[0].mvItem}"/>
<input type="hidden" name="quantityReceived" id="quantityReceived" value="${headerInfo[0].quantityReceived}" />
<input type="hidden" name="customerRmaId" id="customerRmaId" value="${headerInfo[0].customerRmaId}" />
<input type="hidden" name="transferRequestId" id="transferRequestId" value="${headerInfo[0].transferRequestId}" />
<input type="hidden" name="returnPrNumber" id="returnPrNumber" value="${headerInfo[0].returnPrNumber}" />
<input type="hidden" name="returnLineItem" id="returnLineItem" value="${headerInfo[0].returnLineItem}" />
<input type="hidden" name="minimumExpireDate" id="minimumExpireDate" value="<fmt:formatDate value="${minimumExpireDate}" pattern="${dateFormatPattern}"/>"/>
<input type="hidden" name="catPartNo" id="catPartNo" value="${headerInfo[0].catPartNo}" />
<input type="hidden" name="catalogId" id="catalogId" value="${headerInfo[0].catalogId}" />
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${headerInfo[0].catalogCompanyId}" />
<input type="hidden" name="submitPrint" id="submitPrint" value="" />
<input type="hidden" name="submitDocumentLabelsPrint" id="submitDocumentLabelsPrint" value="" />
<input type="hidden" name="submitReceiptLabelsPrint" id="submitReceiptLabelsPrint" value="" />
<input type="hidden" name="origLotStatus" id="origLotStatus" value="${headerInfo[0].lotStatus}"/>
<input type="hidden" name="unitLabelCatPartNo" id="unitLabelCatPartNo" value=""/>
<input type="hidden" name="intercompanyPo" id="intercompanyPo" value="${headerInfo[0].intercompanyPo}"/>
<input type="hidden" name="intercompanyPoLine" id="intercompanyPoLine" value="${headerInfo[0].intercompanyPoLine}"/>
<input type="hidden" name="costFactor" id="costFactor" value="${headerInfo[0].costFactor}" />
<input type="hidden" name="openerPage" id="openerPage" value="${param.openerPage}" />
<input type="hidden" name="inboundShipmentId" id="inboundShipmentId" value="${headerInfo[0].inboundShipmentId}" />
<input type="hidden" name="oldLotStatus" id="oldLotStatus" value="${headerInfo[0].oldLotStatus}" />
<input type="hidden" name="docType" id="docType" value="${headerInfo[0].docType}" />

</div>
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</tcmis:form>
<form name="docForm" id="docForm" method="post" action="">
</form>
</body>
</html:html>