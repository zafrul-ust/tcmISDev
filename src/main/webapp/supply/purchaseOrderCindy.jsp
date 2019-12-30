<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<tcmis:gridFontSizeCss overflow="hidden"/>
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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/supply/purchaseordercindy.js"></script>  

<!-- These are for the Grid-->
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script src="/js/calendar/util.js"></script>
<script src="/js/calendar/rolloverImages.js"></script>

<script src="/js/jquery/jquery-1.6.4.js"></script>


<title>
<fmt:message key="label.haastcm"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
invalidPrice:'<fmt:message key="label.invalidPrice"/>',invalidId:'<fmt:message key="label.invalidId"/>',
invalidShipDate:'<fmt:message key="label.invalidShipDate"/>',invalidDockDate:'<fmt:message key="label.invalidDockDate"/>',
invalidTotal:'<fmt:message key="label.invalidTotal"/>',
dateError:'<fmt:message key="msg.dockDateShipDateOrder"/>',
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",  
label1:"<fmt:message key='label.itemid'/>",
label2:"<fmt:message key="label.itemdescription"/>",
label3:"<fmt:message key="label.quantity"/>",
label4:"<fmt:message key="label.unitprice"/>",
label5:"<fmt:message key="label.remove"/>",
confirmPO:"<fmt:message key="label.confirmPO"/> ",
ok:"<fmt:message key="label.ok"/> ",
flowdown:"<fmt:message key="label.flowdown"/> ",
revisiondate:"<fmt:message key="label.revisiondate"/> ",
description:"<fmt:message key="label.description"/> ",
catalog:"<fmt:message key="label.catalog"/> ",
company:"<fmt:message key="label.company"/> ",
type:"<fmt:message key="label.type"/> ",
chooseprintpo:"<fmt:message key="label.chooseprintpo"/>",
on:"<fmt:message key="label.on"/>",
removeLine:"<fmt:message key="label.removeLine"/>",
undoRemove:"<fmt:message key="label.undoRemove"/>",
linehistory:"<fmt:message key="label.linehistory"/>",
enterlineexpeditenotes:"<fmt:message key="label.enterlineexpeditenotes"/>",
lineexpeditinghistory:"<fmt:message key="label.lineexpeditinghistory"/>",
itemexpeditinghistory:"<fmt:message key="label.itemexpeditinghistory"/>",
editsourcinginfo:"<fmt:message key="label.editsourcinginfo"/>",
savepoline:"<fmt:message key="label.savepoline"/>",
savelookaheadchanges:"<fmt:message key="label.savelookaheadchanges"/>",
savespecschanges:"<fmt:message key="label.savespecschanges"/>",
saveflowdownschanges:"<fmt:message key="label.saveflowdownschanges"/>",
savesdschanges:"<fmt:message key="label.savesdschanges"/>",
showlegend:"<fmt:message key="label.specscolorlegend"/>",
errors:"<fmt:message key="label.errors"/>",
showmrlineallocationreport:"<fmt:message key="label.showmrlineallocationreport"/>"
};


with(milonic=new menuname("rightClickLineItem")){
    top="offset=2"
        style = contextStyle;
        margin=3        
        aI("text=");
}

with(milonic=new menuname("rightClickAssociatedPr")){
    top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.showmrlineallocationreport"/>;url=javascript:getMrLineAllocationReport();");
        aI("text=<fmt:message key="label.showsuggestedsupplier"/>;url=javascript:getSuggestedSupplier();");
        aI("text=<fmt:message key="label.showlpranges"/>;url=javascript:showLPPranges();");
        aI("text=<fmt:message key="label.showschedule"/>;url=javascript:showSchedule();");
}

with(milonic=new menuname("rightClickSpec")){
    top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.viewspec"/>;url=javascript:viewSpecDoc();");
}

with(milonic=new menuname("rightClickMsds")){
    top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.viewmsds"/>;url=javascript:viewMsds();");
}

with(milonic=new menuname("rightClickFlowdowns")){
    top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.viewflowdown"/>;url=javascript:viewFlowdown();");
}

with(milonic=new menuname("rightClickTcmBuys")){
    top="offset=2"
        style = contextStyle;
        margin=3
        aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:viewPurchaseOrder();");
}

<%-- Initialize the RCMenus --%>
drawMenus();

var minutes = 0;
var seconds = 0;

function displayNoSearchSecDurationLocal() {
       var startSearchTime = document.getElementById("startSearchTime");
       var now = new Date();
       var endSearchTime = now.getTime();

       //the duration is in milliseconds
       var searchDuration = (endSearchTime*1)-(startSearchTime.value*1);
       if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
       }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
       }	      
	}

function setFooter(footer,whichGrid)
{
	if (minutes != 0) {
    footer.innerHTML = messagesData.recordFound+": "+($v(whichGrid)-1)+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
  }else {
    footer.innerHTML = messagesData.recordFound+": "+($v(whichGrid)-1)+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
  }
}

//<!--TODO: Not required. remove after testing-->
//<c:set var="readOnlyPo" value="N" />
//<tcmis:facilityPermission indicator="true" userGroupId="ReadOnlyPo" facilityId="${POHeaderBean.hubName}">
//<c:set var="readOnlyPo" value="Y" />
//</tcmis:facilityPermission>

//<!--TODO: Not required. remove after testing-->
//<c:set var="sourcing" value="N" />
//<tcmis:facilityPermission indicator="true" userGroupId="Sourcing" facilityId="${POHeaderBean.hubName}">
//<c:set var="sourcing" value="Y" />
//</tcmis:facilityPermission>

//<!--TODO: Not required. remove after testing-->
//<c:set var="noPermission" value="N" />
//<c:if test="${ !(readOnlyPo == 'Y' || sourcing == 'Y') }">
//<c:set var="noPermission" value="Y" />
//</c:if>

//<!--TODO: Not required. remove after testing-->
//<c:if test="${POHeaderBean.hubName == null && noPermission =='Y' }" >
//    <c:set var="readonly" value="Y" />
//</c:if>

//this is noted as readonly in the old PO code.
<c:set var="purchasing" value="N" />
<tcmis:facilityPermission indicator="true" userGroupId="Purchasing" facilityId="${POHeaderBean.hubName}">
<c:set var="purchasing" value="Y" />
</tcmis:facilityPermission>


var readOnlyGrids = 'N';
<c:if test="${readOnlyGrids != null && readOnlyGrids }">
   readOnlyGrids = 'Y';
</c:if>

<c:set var="allowCurrencyupdate" value="N" />
<tcmis:facilityPermission indicator="true" userGroupId="chgCurrency" facilityId="${POHeaderBean.hubName}">
     <c:set var="allowCurrencyupdate" value="Y" />
</tcmis:facilityPermission>

<c:set var="allowPaymentTermsUpdate" value="N" />
<tcmis:facilityPermission indicator="true" userGroupId="PaymentTerms" facilityId="${POHeaderBean.hubName}" >
     <c:set var="allowPaymentTermsUpdate" value="Y" />
</tcmis:facilityPermission>

<c:set var="allowPOCreditConfirmaiton" value="true" />
<c:if test="${POHeaderBean.opsEntityId == 'HAASTCMUSA'}">
    <c:set var="allowPOCreditConfirmaiton" value="false" />
    <tcmis:facilityPermission indicator="true" userGroupId="confPoCreditCardTerms" facilityId="${POHeaderBean.hubName}">
         <c:set var="allowPOCreditConfirmaiton" value="true" />
    </tcmis:facilityPermission>
</c:if>

<c:set var="isUserPOConfirmationApprover" value="N" />  
<tcmis:facilityPermission indicator="true" userGroupId="POConfirmationApprover" facilityId="All" companyId="Radian">
   	<c:set var="isUserPOConfirmationApprover" value="Y" />
</tcmis:facilityPermission>

<c:set var="allowPOFinancialConfirmaiton" value="N" /> 
<c:if test="${outsideUSAFYAudit || isUserPOConfirmationApprover == 'Y'}">
     <c:set var="allowPOFinancialConfirmaiton" value="Y" />
</c:if>

//<!--TODO: above commented conditions are replaced with this-->
<c:set var="readonly" value="N" />
<c:if test="${readOnlyGrids != null && readOnlyGrids}">
    <c:set var="readonly" value="Y" />
</c:if>

<c:if test="${purchasing == 'N' }">
   <c:set var="readonly" value="Y" />
</c:if>

<c:if test="${POHeaderBean == null }">
    <c:set var="readonly" value="N" />
</c:if>

<c:set var="varIsUSBuyer" value="N"/>
<c:if test="${!outsideUSAFYAudit}">
   <c:set var="varIsUSBuyer" value="Y"/>
</c:if>

<c:set var="everConfirmed" value="${POHeaderBean.everConfirmed}"/>
<c:set var="vouchered" value="${POHeaderBean.vouchered}"/>
<c:set var="vouchered" value ='${fn:toUpperCase(vouchered)}'/>         
<c:set var="changeSupplier" value="${POHeaderBean.changeSupplier}"/>
<c:set var="intercompanyMr" value="${POHeaderBean.intercompanyMr}"/>
<c:set var="receivedStatus" value="${POHeaderBean.receivedStatus}"/>


//outsideUSAFYAudit=<c:out value="${outsideUSAFYAudit}"/>
//isUserPOConfirmationApprover=<c:out value="${isUserPOConfirmationApprover}"/>
//allowPOFinancialConfirmaiton=<c:out value="${allowPOFinancialConfirmaiton}"/>
//readOnlyGrids=<c:out value="${readOnlyGrids}"/>
//readonly=<c:out value="${readonly}"/> 
//vouchered=<c:out value="${vouchered}"/> 
//POHeaderBean.hubName=<c:out value="${POHeaderBean.hubName}"/>
//allowCurrencyupdate=<c:out value="${allowCurrencyupdate}"/>
//allowPaymentTermsUpdate=<c:out value="${allowPaymentTermsUpdate}"/>
//allowPOCreditConfirmaiton=<c:out value="${allowPOCreditConfirmaiton}"/>
//changeSupplier=<c:out value="${changeSupplier }"/>
//fn:length(intercompanyMr)=<c:out value="${fn:length(intercompanyMr)}"/>
//POHeaderBean.blnProblemWithBuy=<c:out value="${POHeaderBean.blnProblemWithBuy}"/>
//orderTotalInUSD=<c:out value="${orderTotalInUSD}"/>
//POHeaderBean.requiresFinancialApproval=<c:out value="${POHeaderBean.requiresFinancialApproval}"/>
//varIsUSBuyer=<c:out value="${varIsUSBuyer}"/>

function resizeMainPage() {
	var ht = window.innerHeight
		|| document.documentElement.clientHeight
		|| document.body.clientHeight;
	var wt = window.innerWidth
		|| document.documentElement.clientWidth
		|| document.body.clientWidth;
	var mainPageStyle = document.getElementById("mainPage").style; 
	mainPageStyle.height = ht+"px";
	mainPageStyle.width= wt+"px";
}

function myOnloadCall()
{	
    var el = document.body;
    if (el.addEventListener){
        el.addEventListener('mousedown', handleRightClick, false);
    } else if (el.attachEvent){
        el.attachEvent('onmousedown', handleRightClick);
    }

    <c:if test="${poHeaderLoaded == 'N'}">
       reSearchPoNumber();
    </c:if>
    
	if ($v('po') == "" ) {
		return;
	}
	initializeLineItemGrid();
	//getPoLineDetails();
	<c:if test="${numOfPoLines != null && numOfPoLines > 0 }">
	   //initialGridLoads();
	   //TODO - Initial show hide of the divs should be based on the item type. 
	   //Add logic to check it. Add logic to highlight the first row in the line item grid by default. 
	   $('materialTypeItem').style.display = ""; 
	   $('lineItemDetailRows').style.display = "";
	   $('addChargesList').style.display = "none";
	   lineItemGrid.selectRow(0,true,false,true); //by default select the first row of the grid and call the onrowselect method.	
	</c:if>

	<c:choose>
	    <c:when test="${POHeaderBean.requiresFinancialApproval == 'Y'}">
	        <c:if test="${allowPOFinancialConfirmaiton != null && (allowPOFinancialConfirmaiton == 'Y' || allowPOFinancialConfirmaiton == 'y' )}">
	          if (window['confirmlink'] && window['financialapprovallink'] ) {		          
	        	    $('confirmlink').style.display = 'none';  
	        	    $('financialapprovallink').style.display = '';
	          }	            
	        </c:if>
	    </c:when>    
	    <c:otherwise>
	        <c:if test="${orderTotalInUSD != null && orderTotalInUSD > 15000 }">
	           <c:if test="${allowPOFinancialConfirmaiton != null && (allowPOFinancialConfirmaiton == 'Y' || allowPOFinancialConfirmaiton == 'y' )}">
	              if (window['confirmlink']) {		         
	                 $('confirmlink').style.display = 'none';  
	              }
	           </c:if> 
		    </c:if> 
	    </c:otherwise>
    </c:choose>

    <c:choose>
       <c:when test="${POHeaderBean.opsEntityId == 'HAASTCMUSA' && POHeaderBean.paymentTerms == 'Credit Card'}">    
            <c:if test="${allowPOCreditConfirmaiton != null && allowPOCreditConfirmaiton}" >
            if (window['confirmlink']) {
                  $("confirmlink").style["display"] = "none";
            }
            </c:if>
       </c:when>    
       <c:otherwise>
       if (window['confirmlink']) {
                  $("confirmlink").style["display"] = "";
       }
       </c:otherwise>
    </c:choose>
    
    resizeMainPage();
	closeTransitWin();	
}

function handleRightClick(e) {
    if(e.button === 2) {
    	toggleContextMenu('contextMenu');        
    }
}

function initialGridLoads() 
{
	changePoTotalPrice();
	if($v('isUSBuyer') == 'Y') {
		changePoTotalPriceInUSD();//TODO: check during testing if this is required here
	} 
	getSpecs();	
    getFlowdowns();
    getAssociatedPrs();    
    getTcmBuys();    
}

function collapseAllGrids() 
{
	collapseLookAheadGrid();
	collapseItemNotesGrid();
	collapsePartNotesGrid();	
	collapseMsdsGrid();
	collapseClientBuysGrid();
}


/*
function getPoHeaderLineDetails(poHeaderLoaded)
{
	alert(poHeaderLoaded);	
	var po = $v("po");
	
	if (po != null && po != "") {
		
		if (poHeaderLoaded != "Y") {
		    j$.ajax({
	 	      type: "POST",
	 	      url: "/tcmIS/supply/purchaseorder.do",
	 		  data: {
	 			   uAction:'poHeaderSearch',
	 			   radianPo:(po==""?null:po)
	 			  },
	 	      success: initHeader
	 	  	  });
		}
		
		 j$.ajax({
	 	     type: "POST", 	    
	 	      url: "/tcmIS/supply/purchaseorder.do",
	 		 data: {
	 			   uAction:'poLineDetailSearch',
	 			   radianPo:(po==""?null:po)
	 			  },
	 	  success: initPoLineDetails
	 	  	});	 
	}
}
*/



var lineItemGridConfig = {
		divName:'lineItemBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'lineItemJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'lineItemGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'lineItemConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: onLineItemRightClick,
		onBeforeSelect: checkForChanges,
		onRowSelect: selectRow
		};

var lineItemConfig = [
	{
		columnId:"permission"
	},
	{ 
		  columnId:"line",
		  columnName:'<fmt:message key="label.line"/>',
		  width:3,
		  align:'center'
	},
	{ 
		  columnId:"ghsCompliant",
		  columnName:'<fmt:message key="label.ghscompliant"/>',
		  width:3,
		  align:'center'
    },
	{ 
		  columnId:"itemId",
		  columnName:'<fmt:message key="label.itemid"/>',
		  attachHeader:'<fmt:message key="label.itemid"/>',
		  type:'hed',		  
		  width:6,
		  align:'right'
	},
	{ 
          columnId:"itemIdLookup",
          columnName:'#cspan', 
          attachHeader:'<fmt:message key="label.lookup"/>', 
          width:4, 
          align:'left', 
          submit: false
    },
	{ 
		  columnId:"type",
		  columnName:'<fmt:message key="label.type"/>',
		  width:4, 
          align:'center'
	},	
	{     
		  columnId: "needDatePermission" 
	},    
	{ 
		  columnId:"needDate",
		  columnName:'<fmt:message key="label.needdate"/>',
		  type:'hcal',
		  onBlur:poLineDateChangedRowLevel,
		  width:7,
          align:'center',
		  permission: true
	},	
	{     
        columnId: "promisedDatePermission" 
    },
	{ 
		  columnId:"promisedDate",
		  columnName:'<fmt:message key="label.promised.ship.date"/>',
		  type:'hcal',
		  onBlur:poLineDateChangedRowLevel,
		  width:7,
          align:'center',
          permission: true
	},
	{     
        columnId: "projectedDatePermission" 
    },	
	{ 
		  columnId:"projectedDate",
		  columnName:'<fmt:message key="label.projecteddelivery"/>',
		  type:'hcal',
		  onBlur:poLineDateChangedRowLevel,
		  width:7,
		  align:'center',
          permission: true
	},
	{     
        columnId: "quantityPermission" 
    },	
	{ 
		  columnId:"quantity",
		  columnName:'<fmt:message key="label.qty"/>',
		  type:'hed',
		  onChange:populateUnitPrice,
		  align:'right',
		  width:5,
          permission: true
	},	
	{ 
		  columnId:"uom",
		  columnName:'<fmt:message key="label.uom"/>',
		  width:13,
		  tooltip:true
	},
	{     
        columnId: "unitPriceDispPermission" 
    },	
	{ 
		  columnId:"unitPriceDisp",
		  columnName:'<fmt:message key="label.unitprice"/>',
		  onChange:populateUnitPrice,
		  align:'right',
		  type:'hed',
		  width:6,
          permission: true
	},	
	{ 
		  columnId:"extendedPriceDisp",
		  columnName:'<fmt:message key="label.extendedprice"/>',
		  align:'right',
		  width:6
	},
	{     
        columnId: "currencyPermission" 
    },  
    {
		  columnId:"currency",
		  columnName:'<fmt:message key="label.currency"/>',
		  align:'right',
		  type:'hcoro',
		  width:7,
		  size:7,
		  onChange:populateUnitPrice,
		  permission: true
    },	
	{ 
		  columnId:"quantityReceived",
		  columnName:'<fmt:message key="label.received"/>',
		  align:'right',
		  width:5
	},	
	{ 
		  columnId:"quantityVoc",
		  columnName:'<fmt:message key="label.vouchered"/>',
		  align:'right',
		  width:5
	},	
	{ 
		  columnId:"quantityReturned",
		  columnName:'<fmt:message key="label.returned"/>',
		  align:'right',
		  width:5
	},	
	{ 
		  columnId:"status",                                  // line status
		  columnName:'<fmt:message key="label.status"/>',
		  width:9,
		  align:'center'
	},
	{ 
		  columnId:"amendment"
	},
	{
		  columnId:"poLineStatus"    // original line status
	},
    {
        columnId:"poLine"     // poline in 1000's
    },
    {
        columnId:"isMaterialTypeItem"
    },
    {
        columnId:"lastConfirmed"
    },
    {
        columnId:"secondarySupplierAddress"
    },
    {
        columnId:"itemDescription"
    },    
    {
        columnId:"mpn"
    },
    {
        columnId:"supplierPn"
    },
    {
        columnId:"dpas"
    },
    {
        columnId:"shelfLife"
    },
    {
        columnId:"shelfLifeBasis"
    },
    {
        columnId:"supplierDetail"
    },    
    {
        columnId:"verifiedOn"
    },
    {
        columnId:"itemVerifiedBy"
    },
    {
        columnId:"lineNotes"
    },
    {
        columnId:"deliveryComments"
    },
    {
        columnId:"everConfirmed"
    },
    {
        columnId:"validItem"
    },
    {
        columnId:"differentSupplierOnLine"
    },
    {
        columnId:"secondarySupplierOnPo"
    },
    {
        columnId:"changeSupplier"
    },
    {
        columnId:"itemUnitPrice"
    },
    {
        columnId:"shipFromLocationId"
    },    
    {
        columnId:"relatedPoLines"
    },
    {
        columnId:"prShipTo"
    },
    {
        columnId:"poLineNumber"          //poline in units. not in 1000's.
    },
    {
        columnId:"seqNumber"
    },
    {
        columnId:"lineChange"
    },
    {
        columnId:"ammendmentcomments"
    },
    {
        columnId:"lineArchived"
    },
    {
    	columnId:"canAssignAddCharge"
    },
    {
        columnId:"supplierQty"
    },
    {
    	columnId:"supplierUnitPrice"
    },
    {
        columnId:"supplierExtPrice"
    },
    {
        columnId:"lineTotalPrice"
    },
    { 
        columnId:"unitPrice"      
    },  
    {
        columnId:"extendedPrice"
    },
    {
        columnId:"oldNeedDate"
    },
    { 
        columnId:"oldPromisedDate"      
    },  
    {
        columnId:"oldProjectedDate"
    },
    {
        columnId:"msdsRequestedDate"
    },
    {
        columnId:"flowdownChanged"
    },
    {
        columnId:"specChanged"
    },
    {
        columnId:"lookAheadChanged"
    },
    {
        columnId:"chargeListChanged"
    },
    {
        columnId:"quantityVouchered"
    },
    {
        columnId:"supplier"
    },
    {
        columnId:"secondarySupplier"
    },
    {
        columnId:"oldItemId"
    },
    {
        columnId:"oldQuantity"
    },
    {
        columnId:"oldUnitPriceDisp"
    },
    {
        columnId:"oldCurrency"
    },
    {
        columnId:"oldSupplierPn"
    },
    {
        columnId:"oldDpas"
    },
    {
        columnId:"oldShelfLife"
    },
    {
    	columnId:"currencyConversionRate"
    },
    {
        columnId:"poLineClosed"
    }
];

/*
function getPoLineDetails()
{
	var po  =  document.getElementById("po");
	
    j$.ajax({
        type: "POST",
        url: "/tcmIS/supply/purchaseorder.do",
        data: {        	
            action:'searchlike',
            radianPo:(po.value==""?null:po.value)
            },
            success: initializeLineItemGrid
            });
}
*/


<c:set var="lineItemDataCount" value='${0}'/>
var lineItemJsonData = {
        rows:[
        <c:forEach var="bean" items="${poLineDetailCol}" varStatus="listStatus">
	        <fmt:formatDate var="needDate" value="${bean.needDate}" pattern="${dateFormatPattern}"/>
	        <fmt:formatDate var="promisedDate" value="${bean.vendorShipDate}" pattern="${dateFormatPattern}"/>
	        <fmt:formatDate var="projectedDate" value="${bean.promisedDate}" pattern="${dateFormatPattern}"/>       
	        <fmt:formatDate var="lastConfirmed" value="${bean.lastConfirmed}" pattern="${dateFormatPattern}"/>
	        <fmt:formatDate var="dateItemVerified" value="${bean.dateItemVerified}" pattern="${dateFormatPattern}"/>
	        
	        <c:set var="blnWriteAccess" value='Y'/>
	        <c:if test="${bean.blnPoLineReadOnly == true}">
	           <c:set var="blnWriteAccess" value='N'/>
	        </c:if>
	        <c:set var="blnItemIdEditable" value='Y'/>
            <c:if test="${bean.itemIdEditable == false}">
               <c:set var="blnItemIdEditable" value='N'/>
            </c:if>
	        <c:set var="blnMaterialItem" value='N'/>
	        <c:if test="${bean.isMaterialTypeItem == 'Y'}">
	           <c:set var="blnMaterialItem" value='Y'/>
	        </c:if>
	        <c:set var="blnNotVouchered" value='Y'/>
	        <c:if test="${bean.quantityVouchered != '' && bean.quantityVouchered > 0}">
	            <c:set var="blnNotVouchered" value='N'/>
	        </c:if>
	        <c:set var="editCurrency" value="Y" />
	        <c:if test="${readonly == 'Y' || allowCurrencyupdate == 'N' || vouchered == 'Y'}">
	           <c:set var="editCurrency" value="N" />
	        </c:if>
	        <c:if test="${listStatus.index > 0}">,</c:if>
	        {id:${listStatus.index+1},
	         data:[
	          '${blnWriteAccess}',
	          //'Y', //give always full permission
	          '${bean.lineAmendmentNum}',
	          '${bean.ghsCompliant}',         
	          '${bean.itemId}',
	          '<c:if test="${blnItemIdEditable == 'Y'}"><input name="lookupItemId" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="getItemDetail();"></c:if>',
	          '${bean.itemType}',
	          '<c:choose><c:when test="${blnMaterialItem == 'Y' && blnWriteAccess == 'Y'}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
	          '${needDate}',  //need date
	          '<c:choose><c:when test="${blnMaterialItem == 'Y' && blnWriteAccess == 'Y'}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
	          '${promisedDate}', //promised date
	          '<c:choose><c:when test="${blnMaterialItem == 'Y' && blnWriteAccess == 'Y'}">Y</c:when><c:otherwise>N</c:otherwise></c:choose>',
	          '${projectedDate}',  //projected date
	          //'${blnNotVouchered}',
	          'Y', // qty always editable
	          '${bean.quantity}',
	          '<tcmis:jsReplace value="${bean.packaging}"/>',
	          '${blnNotVouchered}',
	          '${bean.unitPrice}',	          
	          '${bean.extendedPrice}',
	          '${editCurrency}', //currency permission
	          '${bean.currencyId}',
	          '${bean.quantityReceived}',
	          '${bean.quantityVouchered}',
	          '${bean.quantityReturned}',
	          '${bean.status}',
	          '${bean.amendment}',
	          '${bean.poLineStatus}',
	          '${bean.poLine}',                         //full po line number in 1000's
	          '${bean.isMaterialTypeItem}',
	          '${lastConfirmed}',
	          '${bean.secondarySupplierAddress}',
	          '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
	          '${bean.mfgPartNo}',
	          '${bean.supplierPartNo}',
	          '${bean.dpasRating}',
	          '${bean.remainingShelfLifePercent}',
	          '${bean.shelfLifeBasisMsg}',
	          '${bean.shipFromLocationId}',
	          '${dateItemVerified}',
	          '${bean.itemVerifiedBy}',
	          '<tcmis:jsReplace value="${bean.poLineNote}" processMultiLines="true"/>',
	          '<tcmis:jsReplace value="${bean.deliveryComments}" processMultiLines="true"/>',
	          '${bean.everConfirmed}',
	          '${bean.validItem}',
	          '${bean.differentSupplierOnLine}',
	          '${bean.secondarySupplierOnPo}',
	          '${bean.changeSupplier}',
	          '${bean.unitPrice}',
	          '${bean.shipFromLocationId}',
	          '<tcmis:jsReplace value="${bean.relatedPoLines}" processMultiLines="true"/>',
	          '${bean.prShipTo}',
	          '${bean.poLineNumber}',       //poline in units. not in 1000's.
	          '${bean.seqNumber}',
	          'N',
	          '',
	          '',
	          '${bean.canAssignAddCharge}',
	          '${bean.supplierQty}',
	          '${bean.supplierUnitPrice}',
	          '${bean.supplierExtPrice}',
	          '${bean.lineTotalPrice}',
	          '${bean.unitPrice}',
	          '${bean.extendedPrice}',          
	          '${needDate}',  //old need date
	          '${promisedDate}', //old promised date
	          '${projectedDate}',  //old projected date
	          '${bean.msdsRequestedDate}', //send msds checkbox value
	          'N',
	          'N',
	          'N',
	          'N',
	          '${bean.quantityVouchered}',
	          '${bean.supplier}',
	          '${bean.secondarySupplier}',
	          '${bean.itemId}',
	          '${bean.quantity}',
	          '${bean.unitPrice}',
	          '${bean.currencyId}',
	          '${bean.supplierPartNo}',
              '${bean.dpasRating}',
              '${bean.remainingShelfLifePercent}',
              '${bean.currencyConversionRate}',
              '${bean.poLineClosed}'
	          ]}
	         <c:set var="lineItemDataCount" value='${lineItemDataCount+1}'/>
         </c:forEach>
        ]};


var currency = [
        {text:'<fmt:message key="label.pleaseselect"/>',value:''}
        <c:forEach var="vvCurr" items="${poCurrency}" varStatus="status"> 
        ,{text:'${vvCurr.currencyId}',value:'${vvCurr.currencyId}'}
        </c:forEach>    
        ];  


var chargesListGridConfig = {
        divName:'chargesListBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'chargesListJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'chargesListGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'chargesListConfig',        // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,             // this page has rowSpan > 1 or not.
        submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
        singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
        noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        };

var chargesListConfig = [
{
    columnId:"permission"
},
{ 
    columnId:"selectPo",
    columnName:'<fmt:message key="label.select"/>',
    width:5,
    type:'hchstatus' ,
    onChange:chargeGridUpdated
},
{ 
    columnId:"poLineNumber",
    columnName:'<fmt:message key="label.line"/>',
    width:25
},       
{ 
    columnId:"itemId",
    columnName:'<fmt:message key="label.itemid"/>',
    width:25
},   
{ 
    columnId:"description",
    columnName:'<fmt:message key="label.description"/>',
    width:95
}, 
{
    columnId:"poLine"
},
{
	columnId:"poAddChargeLine"
},
{
    columnId: "changed"
}
];

<c:set var="poLineItemListColCount" value='${0}'/>
var chargesListJsonData = {
        rows:[
        <c:forEach var="bean" items="${poLineItemListCol}" varStatus="poLineItemListStatus">                    
	        <c:if test="${poLineItemListStatus.index > 0}">,</c:if>
	        {id:${poLineItemListStatus.index+1},
	         data:[
	          'Y',
	          false,         
	          '${bean.poLineNumber}',
	          '${bean.itemId}',
	          '<tcmis:jsReplace value="${bean.description}"  processMultiLines="true"/>',
	          '${bean.poLine}',
	          '',
	          'N'
	          ]}
	         <c:set var="poLineItemListColCount" value='${poLineItemListColCount+1}'/>
         </c:forEach>
        ]};


function initChargesListGrid() {    
    $('chargesListBean').style.width = '99%';
    $('chargesListBean').style.height = '250px';    
    chargesListGridConfig.beanData = 'chargesListJsonData';
    window['chargesListJsonData'] = chargesListJsonData;
    chargesListGridConfig.divName = 'chargesListBean';
    chargesListGridConfig.beanGrid = 'chargesListGrid'; 
    initGridWithGlobal(chargesListGridConfig);
}



function initHeader(res)
{
	var results = jQuery.parseJSON(res);
	
	$('supplierLine1').innerHTML = results.supplierName;
	$('supplierId').value =  results.supplier;
	$('hubName').value =  results.branchPlant;
	$('po').value = results.radianPo;
	$('supplierLine2').innerHTML = results.supplrAddressLine1Display;
	$('supplierLine3').innerHTML = results.supplrAddressLine2Display;
	$('supplierline4').innerHTML = results.supplrAddressLine3Display;
	$('buyer').innerHTML = results.buyerName;
	$('buyerId').value = results.buyerId;
	$('carrierLine1').innerHTML = "/"+results.carrierCompanyId;
	$('carrierID').value = results.carrier;
	$('carrierLine2').innerHTML = results.carrierName + "/" + results.carrierAccount;
	$('orderTaker').value = results.supplierContactName;
	$('mfgPhoneNo').innerHTML= results.supplierPhone;
	$('contactPhoneNo').innerHTML = results.supplierContactPhone.length > 0 ? results.supplierContactPhone : "" ;
	$('contactFaxNo').innerHTML = results.supplierContactFax.length > 0 ? results.supplierContactFax : "" ;
	$('supplierContactEmail').innerHTML = results.email;
	$('shipToLine1').innerHTML = results.shipToAddressLine1Display;
	$('shipToLine2').innerHTML = results.shipToAddressLine2Display;
	$('shipToLine3').innerHTML = results.shipToAddressLine3Display;
	$('shiptoline4').innerHTML = results.shipToAddressLine4Display;
	$('shiptoline5').innerHTML = results.shipToCompanyId;
	$('suppRating').value = results.qualificationLevel;
	$('suppRatingSpan').innerHTML = results.qualificationLevel;
	$('invenGrp').value = results.inventoryGroup;
	$('invenGrpSpan').innerHTML = results.inventoryGroup;
	$('validShipTo').value = results.shipToLocationId.length > 0 ?"Yes":"No";
	$('shipToCompanyId').value = results.shipToCompanyId;
	$("shipToId").value = results.shipToLocationId;
	$("paymentTerms").value = results.paymentTerms;	
	$("selectPaymentTerms").value = results.paymentTerms;
	$("fob").value = results.freightOnBoard;	
	$("selectFob").value = results.freightOnBoard;
	$("acceptedDate").value = results.dateAccepted;	 
	$("opsEntityId").value = results.opsEntityId;
	$("dbuyLockStatus").value = results.dbuyLockStatus;
	$("transport").value = results.transport;
	$("supplierDefaultPaymentTerms").value = results.supplierDefaultPaymentTerms;
}

/*
function initPoLineDetails(res)
{
	lineItemJsonData = jQuery.parseJSON(res);
	initGridWithGlobal(lineItemGridConfig);
	initChargesListGrid(lineItemJsonData.poLineRows);
	$('lastconfirmed').innerHTML = lineItemJsonData.rows[0].promisedDate;
    $('detailline').value = lineItemJsonData.rows[0].poLineNumber;
    $('mpn').value = lineItemJsonData.rows[0].mfgPartNo;
    $('supplierpn').value = lineItemJsonData.rows[0].supplietPartNo;
    $('dpas').value = lineItemJsonData.rows[0].dpasRating;
    $('shelflife').value = lineItemJsonData.rows[0].remaingingShelfLifePercent;
    $('shelfLifeBasis').innerHTML = lineItemJsonData.rows[0].shelfLifeBasis;
    $('supplierdetail').value = lineItemJsonData.rows[0].shipFromLocationId;
    $('itemdesc').innerHTML = lineItemJsonData.rows[0].itemDesc;
    $('secondarysuppliercell').innerHTML = lineItemJsonData.rows[0].secondarySupplierAddress;
    $('verifiedOn').innerHTML = lineItemJsonData.rows[0].dateItemVerified;
    $('itemVerifiedBy').innerHTML = lineItemJsonData.rows[0].itemVerifiedBy;
    $('lineNotes').value = lineItemJsonData.rows[0].poLineNote;
    $('deliveryComments').value = lineItemJsonData.rows[0].deliveryComments;    
    $('currency').value = lineItemJsonData.rows[0].currencyId;    
}
*/

function calculateGridHeight(totalNumOfRows) {
	var height = 0;
	if (totalNumOfRows > 8)
	    height = 38+260;
	else if (totalNumOfRows == 0)
		height = 50+35; 
	else 
	    height = 38+(26*totalNumOfRows)+22;//33.125
    return height;
}


function getLookAheads()
{   
    var HubName  =  document.getElementById("hubName");
    var validShiptoid  =  document.getElementById("validShipTo");
    var validpo =  document.getElementById("validPo");
	var po  =  document.getElementById("po");

    if (po.value.trim().length > 0 && validpo.value == "Yes")
    {           
        var lineItemGridSelectedRow = "";        
        lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
        if(!lineItemGridSelectedRow)
            lineItemGridSelectedRow = 1;
        
        var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
        var amendmentno  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'amendment');
        var poline = gridCellValue(lineItemGrid,lineItemGridSelectedRow,'poLine');
        var lookAheadStatus  =  $v('lookAheadStatus');     
        var lookAheadLoaded  =  $v('lookAheadLoaded');     

        if ( (itemID != null && itemID != "") && (lookAheadStatus == "No"))
        {   
            expandLookAheadGrid();
                        
			var shiptocompanyid = $v('shipToCompanyId');
			var shiptoid  =  $v('shipToId');
			
			if (lookAheadLoaded == "No") 
			{
        	    j$.ajax({
        	    	  type: "POST",
        	    	  url: "/tcmIS/supply/polookaheadcall.do",
        	    	  data: {
        	    		  radianPo:(po.value==""?null:po.value),
        	    		  itemId:(itemID == ""?null:itemID),
        	    		  amendment:(amendmentno == ""? null:amendmentno),
        	    		  poLine:(poline == ""? null:poline),
        	    		  shipToCompanyId:(shiptocompanyid == "" ? null:shiptocompanyid),
        	    		  shipToLocationId:(shiptoid == "" ? null: shiptoid),
        	    		  rowNumber: lineItemGridSelectedRow
        	    		  },
        	    	  success: initLookAheadGrid
         	  	});
        	   $("lookAheadLoaded").value = "Yes";
            }
        }
        else if ( lookAheadStatus == "Yes" )
        {   
            collapseLookAheadGrid();
        }
    }
}

function expandLookAheadGrid() {
    $('lookAheadStatus').value = "Yes";
    $('lookAheadBean').style.display = "";
    $('lookAheadLink').style.display = "";
    var lookaheadimg = document.getElementById("lookaheadimg");
    lookaheadimg.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("lookAheadTransitPage").style["display"] = "";
}

function collapseLookAheadGrid() {
    $('lookAheadStatus').value = "No";
    $('lookAheadBean').style.display = "none";
    $('lookAheadLink').style.display = "none";
    var lookaheadimg = document.getElementById("lookaheadimg");
    lookaheadimg.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("lookAheadTransitPage").style["display"] = "none";
}

function initLookAheadGrid(results)
{
    var jsonData = jQuery.parseJSON(results);    
    var rowNumber = jsonData.poLineNumber;
    $('lookAheadBean').style.width = '99%';
    var totalNumOfRows = jsonData.NoOfRows;
    if (totalNumOfRows == 0)
    	$('lookAheadLink').style.display = "none";
    else 
    	$('lookAheadLink').style.display = "";
    var height = calculateGridHeight(totalNumOfRows);
    $('lookAheadBean').style.height = height+'px';
    lookAheadGridConfig.beanData = 'lookAheadJsonData';
    window['lookAheadJsonData'] = jsonData;    
    lookAheadGridConfig.divName = 'lookAheadBean';
    lookAheadGridConfig.beanGrid = 'lookAheadGrid'; 
    initGridWithGlobal(lookAheadGridConfig);
}

var lookAheadGridConfig = {
		divName:'lookAheadBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'lookAheadJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'lookAheadGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'lookAheadConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};
		
var lookAheadConfig = [
{
    columnId:"permission"
},
{ 
	columnId:"companyId",
	columnName:'<fmt:message key="label.company"/>',
	width:6
},
{ 
	columnId:"catalogId",
	columnName:'<fmt:message key="label.catalogid"/>',
	width:6
},       
{ 
	columnId:"inventoryGroup",
	columnName:'<fmt:message key="label.inventorygroup"/>',
	width:8
},       
{ 
	columnId:"catPartNo",
	columnName:'<fmt:message key="label.partno"/>',
	width:8
},       
{ 
	columnId:"lookAheadDays",
	columnName:'<fmt:message key="label.lookaheaddays"/>',
	type:"hed",
	size:8,
	width:8,	
    onChange:lookAheadGridUpdated
},       
{ 
    columnId:"partGroupNo"    
},
{
    columnId: "changed"
}

];


function getItemNotes()
{
    var lineItemGridSelectedRow = "";
   
    lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
    if(!lineItemGridSelectedRow)
        lineItemGridSelectedRow = 1;
    
    var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
    var itemnotestatus  =  $v("itemNotesStatus");
    var itemNotesLoaded  =  $v("itemNotesLoaded");   

    if ((itemID.length > 0 && itemID *1 >0) && (itemnotestatus == "No")) 
    {
        expandItemNotesGrid();
        if (itemNotesLoaded == "No")
        {
	   	    j$.ajax({
	 	     type: "POST",
	 	     url: "/tcmIS/supply/poitemnotescall.do",
	 		   data: {
	 			   poLine: lineItemGridSelectedRow * 1000,
	 			   itemId:(itemID == ""? null:itemID),
	 			   rowNumber:lineItemGridSelectedRow
	 			  },
	 	     success: initItemNotesGrid
	 	  	});
	 	  	$("itemNotesLoaded").value = "Yes";
        }
    } 
	else if ( itemnotestatus == "Yes" ) {    	
        collapseItemNotesGrid();
    }
}
function expandItemNotesGrid() {
    $('itemNotesStatus').value = "Yes";
    $('itemNotesBean').style.display = "";
    $('itemNotesLink').style.display = "";
    var itemnotesimage = document.getElementById("itemnotesimage");
    itemnotesimage.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("itemNotesTransitPage").style["display"] = "";
}
function collapseItemNotesGrid() {
    $('itemNotesStatus').value = "No";
    $('itemNotesBean').style.display = "none";
    $('itemNotesLink').style.display = "none";
    var itemnotesimage = document.getElementById("itemnotesimage");
    itemnotesimage.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("itemNotesTransitPage").style["display"] = "none";
}

function initItemNotesGrid(results)
{
	var jsonData = jQuery.parseJSON(results);    
    var rowNumber = jsonData.poLineNumber;   
    $('itemNotesBean').style.width = '99%';
    var totalNumOfRows = jsonData.NoOfRows;
    var height = calculateGridHeight(totalNumOfRows);
    $('itemNotesBean').style.height = height+'px';
	itemNotesJsonData = jsonData;
	itemNotesGridConfig.divName = 'itemNotesBean';
	itemNotesGridConfig.beanGrid = 'itemNotesGrid'; 
	initGridWithGlobal(itemNotesGridConfig);				
}

var itemNotesGridConfig = {
		divName:'itemNotesBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'itemNotesJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'itemNotesGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'itemNotesConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:true // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false		
		};

		
var itemNotesConfig = [
{
    columnId:"permission"
},
{ 
	columnId:"transDate",
	columnName:'<fmt:message key="label.date"/>',
	width:8
},
{ 
	columnId:"fullName",
	columnName:'<fmt:message key="label.buyer"/>',
	width:9
},       
{ 
	columnId:"comments",
	columnName:'<fmt:message key="label.comments"/>',
	width:25
},       
{ 
	columnId:"transactionDate"
} 
];


function getPartNotes()
{  
    var lineItemGridSelectedRow = "";
    
    lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
    if(!lineItemGridSelectedRow)
        lineItemGridSelectedRow = 1;
       
    var invengrp  =  document.getElementById("invenGrp").value;
    var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');

    var partNotesStatus  =  $v("partNotesStatus");
    var partNotesLoaded  =  $v("partNotesLoaded");
    
    if ( (itemID != null && itemID != "")  && (invengrp != null && invengrp != "") && (partNotesStatus == "No"))
    {   
        expandPartNotesGrid();

        if(partNotesLoaded == "No")
        {       
	     	 j$.ajax({
	     	     type: "POST",
	     	     url: "/tcmIS/supply/popartnotescall.do",
	     		   data: {
	     			   itemId:(itemID == ""?null:itemID),
	     			   poLine: lineItemGridSelectedRow * 1000,
	     			   inventoryGroup:(invengrp == ""?null:invengrp),
	                   rowNumber:lineItemGridSelectedRow
	     			  },
	     	     success: initPartNotesGrid
	     	 });
	     	$("partNotesLoaded").value= "Yes";
        }
    }
    else if ( partNotesStatus == "Yes" )
    {   
        collapsePartNotesGrid();
    }
}
function expandPartNotesGrid() 
{
    $('partNotesStatus').value = "Yes";
    $('partNotesBean').style.display = "";
    var partnotesimage = document.getElementById("partnotesimage");
    partnotesimage.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("partNotesTransitPage").style["display"] = "";
}
function collapsePartNotesGrid() 
{
    $('partNotesStatus').value = "No";
    $('partNotesBean').style.display = "none";
    var partnotesimage = document.getElementById("partnotesimage");
    partnotesimage.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("partNotesTransitPage").style["display"] = "none";
}

function initPartNotesGrid(results)
{
	var jsonData = jQuery.parseJSON(results);    
    var rowNumber = jsonData.poLineNumber;    
    $('partNotesBean').style.width = '99%';
    var totalNumOfRows = jsonData.NoOfRows;
    var height = calculateGridHeight(totalNumOfRows);
    $('partNotesBean').style.height = height+'px';
    partNotesJsonData = jsonData;
	partNotesGridConfig.divName = 'partNotesBean';
	partNotesGridConfig.beanGrid = 'partNotesGrid';   
	initGridWithGlobal(partNotesGridConfig);
}

var partNotesGridConfig = {
		divName:'partNotesBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'partNotesJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'partNotesGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'partNotesConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};
		
var partNotesConfig = [
{
    columnId:"permission"
},
{ 
	columnId:"company",
	columnName:'<fmt:message key="label.company"/>',
	width:10
},
{ 
	columnId:"catalog",
	columnName:'<fmt:message key="label.catalog"/>',
	width:10
},       
{ 
	columnId:"partNum",
	columnName:'<fmt:message key="label.partnum"/>',
	width:10
},       
{ 
	columnId:"comments",	
	columnName:'<fmt:message key="label.comments"/>',
	width:20
} 
];	

function getSpecs()
{   
    var HubName  =  document.getElementById("hubName").value;    
    var invengrp  =  document.getElementById("invenGrp").value;
    var po  =  document.getElementById("po").value;
    var lineItemGridSelectedRow = "";
   
    lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
	if(!lineItemGridSelectedRow)
	 	lineItemGridSelectedRow = 1;
    
	var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
	var amendmentno  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'amendment');
	var specstatus  = $v("specsStatus");
    var specsLoaded = $v("specsLoaded");
    if ( (itemID != null && itemID != "") && (specstatus == "No"))
    {
       expandSpecsGrid();

       var shiptocompanyid = $v('shipToCompanyId');
       if (specsLoaded == "No"){
    	   j$.ajax({
               type: "POST",
               url: "/tcmIS/supply/pospeccall.do",
         	   data: { hub:(HubName == ""?null:HubName),
		         	   shipToCompanyId: (shiptocompanyid == ""?null:shiptocompanyid),
		         	   itemId:(itemID == ""?null:itemID),
		         	   radianPo:(po == ""?null:po),
		         	   poLine: lineItemGridSelectedRow * 1000,
		         	   amendment:(amendmentno == ""?null:amendmentno),
		         	   inventoryGroup:(invengrp == ""?null:invengrp),
		         	   rowNumber:lineItemGridSelectedRow,
		         	   readOnlyGrids: readOnlyGrids
		         	 },
             success: initSpecsGrid
           });
           $("specsLoaded").value = "Yes";   
         }
    }
    else if ( specstatus == "Yes" ) {   
        collapseSpecsGrid();
    }
}

function expandSpecsGrid() {
    $('specsStatus').value = "Yes";
    $('specsBean').style.display = "";
    $('specsLink').style.display = "";
    var specimage = document.getElementById("specimage");
    specimage.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("specsTransitPage").style["display"] = "";
}

function collapseSpecsGrid() {
    $('specsStatus').value = "No";
    $('specsBean').style.display = "none";
    $('specsLink').style.display = "none";
    var specimage = document.getElementById("specimage");
    specimage.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("specsTransitPage").style["display"] = "none";
}

function initSpecsGrid(results)
{
	var jsonData = jQuery.parseJSON(results);	       
    var rowNumber = jsonData.poLineNumber;	
    $('specsBean').style.width = '99%';
    var totalNumOfRows = jsonData.NoOfRows;
    if (totalNumOfRows == 0)
    	$('specsLink').style.display = "none";
    else 
    	$('specsLink').style.display = "";
    var height = calculateGridHeight(totalNumOfRows);
    height = (height*1)+38;
    $('specsBean').style.height = height+'px';
    specsGridConfig.beanData = 'specsJsonData';
    window['specsJsonData'] = jQuery.parseJSON(results);     
    specsGridConfig.divName = 'specsBean';
    specsGridConfig.beanGrid = 'specsGrid';        
    initGridWithGlobal(specsGridConfig);   
}

var specsGridConfig = {
		divName:'specsBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'specsJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'specsGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'specsConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: onSpecRightClickMenu
		};

var specsConfig = [
	{
		  columnId:"permission"
	},	
	{ 
		  columnId:"savedCocStr",
		  columnName:'<fmt:message key="label.saved"/>',
		  attachHeader:'<fmt:message key="label.coc"/>',
		  sorting:'na', 
		  type:'hchstatus',
		  width:5,
		  align:'center',
		  onChange:specGridDataUpdated
	},	
	{ 
		  columnId:"savedCoaStr",
		  columnName:'#cspan',
		  attachHeader:'<fmt:message key="label.coa"/>', 
		  sorting:'na',
		  type:'hchstatus',
		  width:5,
		  align:'center',
		  onChange:specGridDataUpdated
	},
	{ 
		  columnId:"specIdDisplay",
		  columnName:'<fmt:message key="label.spec"/>',
		  sorting:'na',
		  width:15,
		  submit:false
	},
	{ 
		  columnId:"specLibraryDesc",
		  columnName:'<fmt:message key="label.speclibrary"/>',
		  sorting:'na',
		  toolTip:"Y",
		  width:15
	},
	{
        columnId:"currentCocRequirementStrPermission"
    },
    {
        columnId:"currentCoaRequirementStrPermission"
    }, 
	{ 
		  columnId:"currentCocRequirementStr",
		  columnName:'<fmt:message key="label.curreq"/>',
		  attachHeader:'<fmt:message key="label.coc"/>',
		  sorting:'na',
		  type:'hchstatus',
		  width:5,
		  align:'center',
		  permission: true
	},	
	{ 
		  columnId:"currentCoaRequirementStr",
		  columnName:'#cspan',
		  attachHeader:'<fmt:message key="label.coa"/>', 
		  type:'hchstatus',
		  sorting:'na',
		  width:5,
		  align:'center',
		  permission: true
	},
	{
        columnId:"cocReqAtSaveStrPermission"
    },
    {
        columnId:"coaReqAtSaveStrPermission"
    },
	{ 
		  columnId:"cocReqAtSaveStr",
		  columnName:'<fmt:message key="label.reqlastsav"/>',
		  attachHeader:'<fmt:message key="label.coc"/>',
		  type:'hchstatus',
		  sorting:'na',
		  width:5,
		  align:'center',
		  permission: true
	},
	{ 
		  columnId:"coaReqAtSaveStr",
		  columnName:'#cspan',
		  attachHeader:'<fmt:message key="label.coa"/>', 
		  type:'hchstatus',
		  sorting:'na',
		  width:5,
		  align:'center',
		  permission: true
	},
	{
		columnId:"content"
	},
	{
		columnId:"specId"
	},
	{
        columnId:"detail"
    },
    {
        columnId:"color"
    },
    {
        columnId:"specLibrary"
    },
    {
        columnId: "changed"
    },
    {
        columnId: "onLine"
    },
    {
    	columnId: "poLineNumber"
    }
	
];


function getFlowdowns()
{
    var HubName  =  document.getElementById("hubName").value;
    var validShiptoid  =  document.getElementById("validShipTo");
    var validpo =  document.getElementById("validPo");
	var invengrp  =  document.getElementById("invenGrp").value;
    var po  =  document.getElementById("po").value;

    lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
    if(!lineItemGridSelectedRow)
        lineItemGridSelectedRow = 1;
    
	var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
	var amendmentno  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'amendment');

    var flowdownsStatus  = $v('flowdownsStatus');
    var flowdownsLoaded  = $v('flowdownsLoaded');
    
    if ((itemID != null && itemID != "")&& (flowdownsStatus == "No"))        
    {   
        expandFlowdownsGrid();
        var shiptocompanyid = $v('shipToCompanyId');
        var shipToId  =  $v("shipToId");
        //alert(HubName + " and " + shiptocompanyid  + " and " +   itemID  + " and " +  po  + " and " +  lineItemGridSelectedRow  + " and " +   amendmentno  + " and " + invengrp); 
        
        if (flowdownsLoaded == "No")
        {
	        j$.ajax({
	            type: "POST",
	            url: "/tcmIS/supply/poflowdowncall.do",
	        	   data: {
	         	   hub:(HubName == ""?null:HubName),
	        	   shipToCompanyId: (shiptocompanyid == ""?null:shiptocompanyid),
	        	   itemId:(itemID == ""?null:itemID),
	        	   radianPo:(po == ""?null:po),
	        	   poLine: lineItemGridSelectedRow * 1000,
	        	   amendment:(amendmentno == ""?null:amendmentno),
	        	   inventoryGroup:(invengrp == ""?null:invengrp),
	        	   shipToLocationId:(shipToId == "" ? null: shipToId),	        	   
                   rowNumber:lineItemGridSelectedRow,
                   readOnlyGrids:readOnlyGrids
	        	  },
	            success: initFlowdownsGrid
	          });
	          
	        $('flowdownsLoaded').value = "Yes"; //once loaded do not reload until data changed
        }
    }
    else if ( flowdownsStatus == "Yes" )
    {
        collapseFlowdownsGrid();
    }  
}

function expandFlowdownsGrid() {
    $('flowdownsStatus').value = "Yes";
    $('flowdownsBean').style.display = "";
    $('flowdownsLink').style.display = "";
    var flowdownimage = document.getElementById("flowdownimage");
    flowdownimage.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("flowdownsTransitPage").style["display"] = "";
}

function collapseFlowdownsGrid() {
    $('flowdownsStatus').value = "No";
    $('flowdownsBean').style.display = "none";
    $('flowdownsLink').style.display = "none";
    var flowdownimage = document.getElementById("flowdownimage");
    flowdownimage.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("flowdownsTransitPage").style["display"] = "none";
}

function initFlowdownsGrid(results)
{
    var jsonData = jQuery.parseJSON(results);    
    var rowNumber = jsonData.poLineNumber;    
    var totalNumOfRows = jsonData.NoOfRows;    
    if (totalNumOfRows == 0)
    	$('flowdownsLink').style.display = "none";
    else 
    	$('flowdownsLink').style.display = "";
    $('flowdownsBean').style.width = '99%';
    var totalNumOfRows = jsonData.NoOfRows;
    var height = calculateGridHeight(totalNumOfRows);
    $('flowdownsBean').style.height = height+'px';
    flowdownsGridConfig.beanData = 'flowdownsJsonData';
    window['flowdownsJsonData'] = jQuery.parseJSON(results);
    flowdownsGridConfig.divName = 'flowdownsBean';
    flowdownsGridConfig.beanGrid = 'flowdownsGrid';
    initGridWithGlobal(flowdownsGridConfig);
}

var flowdownsGridConfig = {
		divName:'flowdownsBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'flowdownsJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'flowdownsGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'flowdownsConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow
		noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		onRightClick: onFlowdownsRightClickMenu
		};

var flowdownsConfig = [
   	{
   		  columnId:"permission"
   	},
   	{ 
        columnId:"ok",
        columnName:'<fmt:message key="label.ok"/>',
        type:'hchstatus',
        sorting:'na',
        width:3,
        onChange:flowdownsGridUpdated
    },
   	{ 
   		  columnId:"flowDownDisp",
   		  columnName:'<fmt:message key="label.flowdown"/>',
   		  sorting:'na',
   		  width:10,
   		  submit:false
   	},
   	{ 
   		  columnId:"revisionDate",
   		  columnName:'<fmt:message key="label.revisiondate"/>',
   		  sorting:'na',
   		  width:10
   	},
   	{ 
   		  columnId:"flowDownDesc",
   		  columnName:'<fmt:message key="label.description"/>',
   		  sorting:'na',
   		  width:20
   	},
   	{ 
   		  columnId:"catalogId",
   		  columnName:'<fmt:message key="label.catalog"/>',
   		  sorting:'na',
   		  align:'center',
   		  width:10
   	},
   	{ 
   		  columnId:"companyId",
   		  columnName:'<fmt:message key="label.company"/>',
   		  sorting:'na',
   	      align:'center',
   		  width:8
   	},
   	{ 
   		  columnId:"flowDownType",
   		  columnName:'<fmt:message key="label.type"/>',
   		  sorting:'na',
   		  align:'center',
   		  width:8
   	},
   	{
        columnId:"flowDown"
    },
   	{
   		 columnId:"onLine"
   	},
   	{
   		 columnId:"content"
   	},
   	{
   	    columnId: "changed"
   	}
];

function getMsds()
{   
   var invengrp  =  document.getElementById("invenGrp").value;
   var HubName  =  document.getElementById("hubName").value;   
   var po   =  document.getElementById("po").value;
   
   lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
   if(!lineItemGridSelectedRow)
       lineItemGridSelectedRow = 1;
   
   var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
   var amendmentno = gridCellValue(lineItemGrid,lineItemGridSelectedRow,'amendment');

   var msdsStatus = $v('msdsStatus');
   var msdsLoaded = $v('msdsLoaded');
   
   if ((itemID != null && itemID != "") && (invengrp.length > 0)  && (msdsStatus == "No"))
   {	   
	   expandMsdsGrid();
	   if (msdsLoaded == "No") 
	   {
		   j$.ajax({
		         type: "POST",
		         url: "/tcmIS/supply/pomsdscall.do",
		         data: {
		           itemId:(itemID == ""?null:itemID),
		           inventoryGroup:(invengrp == ""?null:invengrp),
		           radianPo:(po == ""?null:po),
		           poLine: lineItemGridSelectedRow * 1000,
		           amendment:(amendmentno == ""?null:amendmentno),
		           rowNumber:lineItemGridSelectedRow
		           },
		         success: initMsdsGrid
		        });
	      $('msdsLoaded').value = "Yes";
	   }
   }
   else if ( msdsStatus == "Yes" )
   {
       collapseMsdsGrid();
   }
}

function expandMsdsGrid() {
    $('msdsStatus').value = "Yes";
    $('msdsBean').style.display = "";
    $('msdsLink').style.display = "";
    $('msdsCheck').style.display = "";
    var msdssearchimage = document.getElementById("msdssearchimage");
    msdssearchimage.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("msdsTransitPage").style["display"] = "";
}

function collapseMsdsGrid() {
    $('msdsStatus').value = "No";
    $('msdsBean').style.display = "none";
    $('msdsLink').style.display = "none";
    $('msdsCheck').style.display = "none";
    var msdssearchimage = document.getElementById("msdssearchimage");
    msdssearchimage.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("msdsTransitPage").style["display"] = "none";
}


function initMsdsGrid(results)
{
    var jsonData = jQuery.parseJSON(results);    
    var rowNumber = jsonData.poLineNumber;
    $('msdsBean').style.width = '99%';
    var totalNumOfRows = jsonData.NoOfRows;
    var height = calculateGridHeight(totalNumOfRows);
    //height = (height*1)+10;
    $('msdsBean').style.height = height+'px';
    msdsGridConfig.beanData = 'msdsJsonData';
    window['msdsJsonData'] = jsonData; 
    msdsGridConfig.divName = 'msdsBean';
    msdsGridConfig.beanGrid = 'msdsGrid';
    msdsJsonData = jQuery.parseJSON(results);
    initGridWithGlobal(msdsGridConfig);
}

var msdsGridConfig = {
        divName:'msdsBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'msdsJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'msdsGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'msdsConfig',        // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,             // this page has rowSpan > 1 or not.
        submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
        singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
        noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        onRightClick:onMsdsRightClickMenu
        };

var msdsConfig = [
{
    columnId:"permission"
},
{ 
    columnId:"description",
    columnName:'<fmt:message key="label.component"/>',
    width:15
},
{ 
    columnId:"packaging",
    columnName:'<fmt:message key="label.packaging"/>',
    width:15
},       
{ 
    columnId:"manufacturer",
    columnName:'<fmt:message key="label.mfg"/>',
    width:15
},       
{ 
    columnId:"revisionDateDisp",
    columnName:'<fmt:message key="label.msdsdate"/>',    
    width:10
},
{
    columnId:"revisionDate"
},
{
	columnId:"itemId"
},
{
	columnId:"onLine"
},
{
	columnId:"content"
},
{
    columnId:"poLineNumber"
}
];  

function getAssociatedPrs()
{  
   var invengrp  =  document.getElementById("invenGrp").value;
   var HubName  =  document.getElementById("hubName").value;   
   var po   =  document.getElementById("po").value;
   var opsEntityId = $v("opsEntityId");
   var lineItemGridSelectedRow = "";
   
   lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
   if(!lineItemGridSelectedRow)
       lineItemGridSelectedRow = 1;
      
   var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
   var associatedPrsStatus = $v("associatedPrsStatus");   
   var associatedPrsLoaded = $v("associatedPrsLoaded"); 
   if ((itemID != null && itemID != "") && (invengrp.length > 0) && (associatedPrsStatus == "No"))
   {   
       expandAssociatedPRsGrid();

       if (associatedPrsLoaded == "No") 
       {
	        j$.ajax({
		         type: "POST",
		         url: "/tcmIS/supply/poassociatedprscall.do",
		         data: {
		           itemId:(itemID == ""?null:itemID),
		           inventoryGroup:(invengrp == ""?null:invengrp),
		           hub:(HubName == ""?null:HubName),                      
		           radianPo:(po == ""?null:po),
		           opsEntityId:(opsEntityId == ""?null:opsEntityId),
		           poLine: lineItemGridSelectedRow * 1000,
		           rowNumber:lineItemGridSelectedRow
		           },
		         success: initAssociatedPrsGrid
		        });        
	        $("associatedPrsLoaded").value = "Yes";
        }
   } 
   else if (associatedPrsStatus == 'Yes') 
   {   
       collapseAssociatedPRsGrid();
   }
}

function expandAssociatedPRsGrid() {
    $('associatedPrsStatus').value = "Yes";
    $('associatedPrsBean').style.display = "";
    $('associatedPrsLink').style.display = "";
    var associatedPrsimage = document.getElementById("associatedPrsimage");
    associatedPrsimage.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("associatedPrsTransitPage").style["display"] = "";
}

function collapseAssociatedPRsGrid() {
    $('associatedPrsStatus').value = "No";
    $('associatedPrsBean').style.display = "none";
    $('associatedPrsLink').style.display = "none";
    var associatedPrsimage = document.getElementById("associatedPrsimage");
    associatedPrsimage.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("associatedPrsTransitPage").style["display"] = "none";
}

function initAssociatedPrsGrid(results)
{   
	var jsonData = jQuery.parseJSON(results);   
	var rowNumber = jsonData.poLineNumber;	    
    $('associatedPrsBean').style.width = '99%';
    var totalNumOfRows = jsonData.NoOfRows;
    var height = calculateGridHeight(totalNumOfRows);
    $('associatedPrsBean').style.height = height+'px'; 
    associatedPrsGridConfig.beanData = 'associatedPrsJsonData';
    window['associatedPrsJsonData'] = jsonData; 
    associatedPrsGridConfig.divName = 'associatedPrsBean';
    associatedPrsGridConfig.beanGrid = 'associatedPrsNewGrid';
    associatedPrsJsonData = jsonData;  
    $("totalItemQty").value = jsonData.totalItemQty;
    initGridWithGlobal(associatedPrsGridConfig);
}

var associatedPrsGridConfig = {
        divName:'associatedPrsBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'associatedPrsJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'associatedPrsGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'associatedPrsConfig',        // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,             // this page has rowSpan > 1 or not.
        submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
        singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
        noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        onRightClick:onAssocicatedPrsRightClick        
        };
        
var associatedPrsConfig = [
{
    columnId:"permission"
},
{ 
    columnId:"prnumber",
    columnName:'<fmt:message key="label.prnumber"/>',
    width:5
},
{ 
    columnId:"mrline",
    columnName:'<fmt:message key="label.mrline"/>',
    width:5
},     
{ 
    columnId:"partnumber",
    columnName:'<fmt:message key="label.partnumber"/>',
    width:6
}, 
{ 
    columnId:"specs",
    columnName:'<fmt:message key="label.specs"/>',
    width:6
},     
{ 
    columnId:"type",
    columnName:'<fmt:message key="label.type"/>',
    width:6
},
{ 
    columnId:"lpp",    
    columnName:'<fmt:message key="label.lpp"/>',
    width:6
},
{ 
    columnId:"notes",
    columnName:'<fmt:message key="label.notes"/>',
    width:6
},
{ 
    columnId:"purchasingnote",
    columnName:'<fmt:message key="label.purchasingnote"/>',
    width:10
},       
{ 
    columnId:"buyordernotes",
    columnName:'<fmt:message key="label.buyordernotes"/>',
    width:10
},       
{ 
    columnId:"reuqestphemail",    
    columnName:'<fmt:message key="label.reuqestphemail"/>',
    width:8
},
{ 
    columnId:"csr",
    columnName:'<fmt:message key="label.csr"/>',
    width:7
},
{ 
    columnId:"mrqty",
    columnName:'<fmt:message key="label.mr.qty"/>',
    width:5
},       
{ 
    columnId:"buyquantity",
    columnName:'<fmt:message key="label.buyquantity"/>',
    width:5
},       
{ 
    columnId:"uom",    
    columnName:'<fmt:message key="label.uom"/>',
    width:5
},
{ 
    columnId:"needed",
    columnName:'<fmt:message key="label.needed"/>',
    width:8
},       
{ 
    columnId:"shipto",
    columnName:'<fmt:message key="label.shipto"/>',
    width:8
},       
{ 
    columnId:"facility",    
    columnName:'<fmt:message key="label.facility"/>',
    width:8
},
{ 
    columnId:"deliverypoint",    
    columnName:'<fmt:message key="label.deliverypoint"/>',
    width:8
},
{ 
    columnId:"hub",
    columnName:'<fmt:message key="label.hub"/></BR>(<fmt:message key="label.invengroup"/>)',
    width:8
},
{ 
    columnId:"clientpo",
    columnName:'<fmt:message key="label.clientpo"/>',
    width:8
},       
{ 
    columnId:"mm",
    columnName:'<fmt:message key="label.mm"/>',
    width:5
},       
{ 
    columnId:"reorderpoint",    
    columnName:'<fmt:message key="label.reorderpoint"/>',
    width:5
},
{ 
    columnId:"inventory",
    columnName:'<fmt:message key="label.inventory"/>',
    width:5
},       
{ 
    columnId:"openpoquantity",    
    columnName:'<fmt:message key="label.openpoquantity"/>',
    width:5
},
{
    columnId:"remainingShelfLifePercent"
},
{
    columnId:"requestId"
},
{
    columnId:"catalogId"
},
{
    columnId:"deliveryType"
},
{
    columnId:"mrLineItem"
},
{
    columnId:"companyId"
},
{
    columnId:"mrNumber"
}
];  

function getTcmBuys()
{	   
    var lineItemGridSelectedRow = "";
    
    lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
    if(!lineItemGridSelectedRow)
        lineItemGridSelectedRow = 1;

    var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
    var poLine  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'poLine');
    var supplierid = document.getElementById("supplierId").value;
    var tcmbuystatus  = $v('tcmBuysStatus');
    var tcmBuysLoaded  = $v('tcmBuysLoaded');
    var opsEntityId = $v("opsEntityId");
    
    if ((itemID != null && itemID != "") && (tcmbuystatus == "No") )
    {        
        expandTcmBuysGrid();
        if (tcmBuysLoaded == "No") 
        {   
	        j$.ajax({
	            type: "POST",
	            url: "/tcmIS/supply/potcmbuyscall.do",
	            data: {
	               itemId:(itemID == ""?null:itemID),
	               supplierId:supplierid,
	               opsEntityId:(opsEntityId == ""?null:opsEntityId),
	               poLine: poLine,
	               rowNumber:lineItemGridSelectedRow
	              },
	            success: initTcmBuysGrid
	          });
	        $('tcmBuysLoaded').value = "Yes";
        }    
    }
    else if ( tcmbuystatus == "Yes" )
    {
        collapseTcmBuysGrid();
    }
}

function expandTcmBuysGrid() {
    $('tcmBuysStatus').value = "Yes";
    $('tcmBuysBean').style.display = "";
    var tcmbuysimage = document.getElementById("tcmbuysimage");
    tcmbuysimage.setAttribute("src",'/images/minus.jpg');
    //window.document.getElementById("tcmBuysTransitPage").style["display"] = "";
}

function collapseTcmBuysGrid() {
    $('tcmBuysStatus').value = "No";
    $('tcmBuysBean').style.display = "none";
    var tcmbuysimage = document.getElementById("tcmbuysimage");
    tcmbuysimage.setAttribute("src",'/images/plus.jpg');
    //window.document.getElementById("tcmBuysTransitPage").style["display"] = "none";
}

function initTcmBuysGrid(results)
{
    var jsonData = jQuery.parseJSON(results);   
    var rowNumber = jsonData.poLineNumber;
    //var tcmBuysNewGrid = new dhtmlXGridObject('tcmBuysBean');   
    //initGridWithConfig(tcmBuysNewGrid,tcmBuysConfig,true,true);//initialize grid
    //tcmBuysNewGrid.parse(jsonData,"json");
    //$('tcmBuysBean').style.width = '85%';
    //$('tcmBuysBean').style.height = '250px';
	    
    $('tcmBuysBean').style.width = '99%';
    //$('tcmBuysBean').style.height = '250px';
    var totalNumOfRows = jsonData.NoOfRows;
    var height = calculateGridHeight(totalNumOfRows);
    $('tcmBuysBean').style.height = height+'px'; 
    tcmBuysGridConfig.beanData = 'tcmBuysJsonData';
    window['tcmBuysJsonData'] = jsonData; 
    tcmBuysGridConfig.divName = 'tcmBuysBean';
    tcmBuysGridConfig.beanGrid = 'tcmBuysGrid';
    tcmBuysJsonData = jQuery.parseJSON(results);
    initGridWithGlobal(tcmBuysGridConfig);
}

var tcmBuysGridConfig = {
        divName:'tcmBuysBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'tcmBuysJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'tcmBuysGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'tcmBuysConfig',        // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,             // this page has rowSpan > 1 or not.
        submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
        singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
        noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        onRightClick:onTcmBuysRightClick   
        };

        
var tcmBuysConfig = [
{
    columnId:"permission"
},
{ 
    columnId:"buyer",
    columnName:'<fmt:message key="label.buyer"/>',
    width:9,
    tooltip:true
},
{ 
    columnId:"pon",
    columnName:'<fmt:message key="label.pon"/>',
    width:5,
    tooltip:true
},       
{ 
    columnId:"cuspon",
    columnName:'<fmt:message key="label.cuspon"/>',
    width:7,
    tooltip:true
},   
{ 
    columnId:"shipto",
    columnName:'<fmt:message key="label.shipto"/>',
    width:6,
    tooltip:true
},
{ 
    columnId:"carrier",
    columnName:'<fmt:message key="label.carrier"/>',
    width:4,
    tooltip:true
},       
{ 
    columnId:"supplier",
    columnName:'<fmt:message key="label.supplier"/>',
    width:13,
    tooltip:true
},  
{ 
    columnId:"ordertakenby",
    columnName:'<fmt:message key="label.ordertakenby"/>',
    width:9,
    tooltip:true
},
{ 
    columnId:"phoneno",
    columnName:'<fmt:message key="label.phoneno"/>',
    width:8
},       
{ 
    columnId:"qty",
    columnName:'<fmt:message key="label.qty"/>',
    align:'right',
    width:5
},
{ 
    columnId:"qtyrecvd",
    columnName:'<fmt:message key="label.qtyreceived"/>',
    align:'right',
    width:5
},    
{ 
    columnId:"unitprice",
    columnName:'<fmt:message key="label.unitprice"/>',
    align:'right',
    width:8
},
{ 
    columnId:"orderdate",
    columnName:'<fmt:message key="label.orderdate"/></BR>(<fmt:message key="label.lastconfirmed"/>)',
    width:11
},
{ 
    columnId:"originalconfirmdate",
    columnName:'<fmt:message key="label.originalconfirmdate"/>',
    width:11
}, 
{ 
    columnId:"receivedate",
    columnName:'<fmt:message key="label.receivedate"/>',
    width:7
}
];

function getClientBuys()
{
	var lineItemGridSelectedRow = "";
	
    lineItemGridSelectedRow = lineItemGrid.getSelectedRowId();
    if(!lineItemGridSelectedRow)
        lineItemGridSelectedRow = 1;
	
    var itemID  =  gridCellValue(lineItemGrid,lineItemGridSelectedRow,'itemId');
    var clientbuystatus  =  $v('clientBuysStatus');
    var clientBuysLoaded  =  $v('clientBuysLoaded');
    //alert(clientbuystatus +" and " + clientBuysLoaded);
    if ((itemID != null && itemID != "") && (clientbuystatus == "No") )
    {   
        expandClientBuysGrid();
        if (clientBuysLoaded == "No") 
        {
	        j$.ajax({
	         type: "POST",
	         url: "/tcmIS/supply/poclientbuyscall.do",
	         data: {
	           itemId:(itemID == ""?null:itemID),
	           poLine: lineItemGridSelectedRow * 1000,
               rowNumber:lineItemGridSelectedRow
	           },
	         success: initClientBuysGrid
	        });
	        $("clientBuysLoaded").value= "Yes";
        }        
    }
    else if ( clientbuystatus == "Yes" )
    {  
       collapseClientBuysGrid();
    }
}

function expandClientBuysGrid() {
    $('clientBuysStatus').value = "Yes";
    $('clientBuysBean').style.display = "";
    //window.document.getElementById("clientBuysTransitPage").style["display"] = "";
    var clientbuysimage = document.getElementById("clientbuysimage");
    clientbuysimage.setAttribute("src",'/images/minus.jpg');
}

function collapseClientBuysGrid() {
    $('clientBuysStatus').value = "No";
    $('clientBuysBean').style.display = "none";
    //window.document.getElementById("clientBuysTransitPage").style["display"] = "none";
    var clientbuysimage = document.getElementById("clientbuysimage");
    clientbuysimage.setAttribute("src",'/images/plus.jpg');
}

function initClientBuysGrid(results)
{
    var jsonData = jQuery.parseJSON(results);   
    var rowNumber = jsonData.poLineNumber;
    $('clientBuysBean').style.width = '99%';
    $('clientBuysBean').style.height = '250px';
    var totalNumOfRows = jsonData.NoOfRows;
    var height = calculateGridHeight(totalNumOfRows);
    $('clientBuysBean').style.height = height+'px'; 
    clientBuysGridConfig.divName = 'clientBuysBean';
    clientBuysGridConfig.beanGrid = 'clientBuysGrid';
    clientBuysJsonData = jQuery.parseJSON(results);
    initGridWithGlobal(clientBuysGridConfig); 
}

var clientBuysGridConfig = {
        divName:'clientBuysBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
        beanData:'clientBuysJsonData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
        beanGrid:'clientBuysGrid',     // the grid's name, as in beanGrid.attachEvent...
        config:'clientBuysConfig',        // the column config var name, as in var config = { [ columnId:..,columnName...
        rowSpan:false,             // this page has rowSpan > 1 or not.
        submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
        singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
        noSmartRender:false // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
        };

        
var clientBuysConfig = [
{
    columnId:"permission"
},
{ 
    columnId:"client",
    columnName:'<fmt:message key="label.client"/>',
    width:7
},
{ 
    columnId:"catalog",
    columnName:'<fmt:message key="label.catalog"/>',
    width:7
},       
{ 
    columnId:"buyer",
    columnName:'<fmt:message key="label.buyer"/>',
    width:9
},   
{ 
    columnId:"pon",
    columnName:'<fmt:message key="label.pon"/>',
    width:5,
    tooltip:true
},
{ 
    columnId:"facilitypn",
    columnName:'<fmt:message key="label.facilitypn"/>',
    width:9
},       
{ 
    columnId:"supplier",
    columnName:'<fmt:message key="label.supplier"/>',
    width:13,
    tooltip:true
},  
{ 
    columnId:"manufacturer",
    columnName:'<fmt:message key="label.manufacturer"/>',
    width:13,
    tooltip:true
},
{ 
    columnId:"mfgpn",
    columnName:'<fmt:message key="label.mfgpn"/>',
    width:9
},       
{ 
    columnId:"qty",
    columnName:'<fmt:message key="label.qty"/>',
    width:6,
    align:'right'
},  
{ 
    columnId:"uom",
    columnName:'<fmt:message key="label.uom"/>',
    width:9,
    tooltip:true
},
{ 
    columnId:"orderdate",
    columnName:'<fmt:message key="label.orderdate"/>',
    width:7,
    align:'center'
},       
{ 
    columnId:"unitprice",
    columnName:'<fmt:message key="label.unitprice"/>',
    width:8,
    align:'right'
},       
{ 
    columnId:"itemId" 
}
];  

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="myOnloadCall();" onresize="resizeMainPage();">

<tcmis:form action="/purchaseorder.do" onsubmit="return submitSearchOnlyOnce();">

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>


<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  ${errorMessage}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
    ${status.current}<br/>
  </c:forEach>
</div>

<script language="JavaScript" type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError and empty requestScope['org.apache.struts.action.ERROR'] and empty errorMessage}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;    
   </c:otherwise>
</c:choose>

if (showErrorMessage == true ){
	showErrorMessages();
}

//-->
</script>
<!-- Error Messages Ends -->


<div class="interface" id="mainPage" style="position:relative;overflow:auto;">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		   <td width="200"><img src="/images/tcmtitlegif.gif" align="left" /></td>
		   <td><img src="/images/tcmistcmis32.gif" align="right" /></td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	    <tr>
	        <td width="70%" class="headingl"><fmt:message key="label.web.dbuy.po.num"/><c:out value="${POHeaderBean.radianPo}"/></td>
	        <td width="30%" class="headingr" align="right"></td>
	    </tr>
	</table>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer" style="width:100%">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none"/> </div>
   </div>
   <div class="roundContent" >
   <div class="boxhead">
          	<span id="getNewPoLink"><a href="#" onclick="getNewPo(this.form);"><fmt:message key="label.new"/></a></span> |
			<c:if test="${readonly == 'N'}">			
				<span id="saveHeaderLink"><a href="javascript:saveHeaderInformation();"><fmt:message key="label.saveheader"/></a></span> |
				<span id="printPoLink"><a href="#" onclick="return printPo();"><fmt:message key="label.print"/></a></span> |
			</c:if>
			<span id="poNotesLink"><a href="#" onclick="showPoNotes()"><fmt:message key="label.ponotes"/></a></span> |
            <span id="emailNotesLink"><a href="#" onclick="showEmailNotes()"><fmt:message key="label.emailnotes"/></a></span>
 	   	<br/><br/>
   </div>
 
    <!-- Insert all the search option within this div -->
	<table width="99%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<tbody>
		    <tr>
		      <td class="optionTitleBoldLeft" colspan="3">&nbsp;&nbsp;&nbsp;<fmt:message key="label.po"/>:&nbsp;&nbsp;
		          <input name="po" id="po" class="inputBox" onchange="invalidatePo()" value="${POHeaderBean.radianPo}"/>&nbsp;
                  <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="submitSearch" id="submitSearch" value="..." align="right" onclick="submitSearchForm('page');"/>&nbsp;
                  <fmt:message key="label.web.dbuy.po.num"/><c:out value="${POHeaderBean.radianPo}"/>
                  <br/>
		      </td>               
		    </tr>		    
			<tr>
			   <td valign="top" width="33%">
			     <fieldset style="border: 2px groove">
                    <legend class="optionTitleBold"><fmt:message key="label.supplier"/></legend>
                        <table width="100%" class="tableSearch" id="poHeaderTable1" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.supplier"/>: </td>
	                            <td class="optionTitleLeft">
                                    <input name="supplierId" id="supplierId" type="text" class="inputBox" <c:if test="${readonly == 'Y' || (changeSupplier == 'N' || fn:length(intercompanyMr) > 0 )}">readonly</c:if> value="${POHeaderBean.supplier}" onchange="headerChanged()"/>
                                    <c:choose>                                                                        
                                    <c:when test="${readonly == 'Y' || (changeSupplier == 'N' || fn:length(intercompanyMr) > 0 )}">
                                        &nbsp;   
                                    </c:when>
                                    <c:otherwise>
                                        <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" id="searchsupplierlike"  value="..." align="left" onclick="getSupplier(this.form)"/>
                                    </c:otherwise>
                                    </c:choose>
                                </td>
	                        </tr>
	                        <tr>
	                           <td class="optionTitleBoldRight">&nbsp;</td>
				                <td id="supplierLine1" class="optionTitleLeft">${POHeaderBean.supplierName}</td> 
				            </tr>
				            <tr>
				                <td class="optionTitleBoldRight">&nbsp;</td>
				                <td id="supplierLine2" class="optionTitleLeft">${POHeaderBean.supplrAddressLine1Display}</td>
				             </tr>
				             <tr>              
				                <td class="optionTitleBoldRight">&nbsp;</td>             
                                <td id="supplierLine3" class="optionTitleLeft">${POHeaderBean.supplrAddressLine2Display}</td>
                             </tr>
                             <tr>
				                <td class="optionTitleBoldRight">&nbsp;</td>
				                <td id="supplierLine4" class="optionTitleLeft">${POHeaderBean.supplrAddressLine3Display}</td>
				             </tr>
                             <tr>
				                <td class="optionTitleBoldRight" nowrap><fmt:message key="label.supplierphonenumber"/>:</td>
				                <td id="supplierLine5" class="optionTitleLeft"><span id="mfgPhoneNo">${POHeaderBean.supplierPhone}</span></td>
				             </tr>
                             <!-- <tr>
				                <td class="optionTitleBoldRight"><fmt:message key="label.supplieremail"/>:</td>
				                <td id="supplieremail" class="optionTitleLeft"><a href="mailto:${POHeaderBean.supplierEmail}">${POHeaderBean.supplierEmail}</a></td>
	                        </tr> -->
	                        <tr>
	                           <td class="optionTitleBoldRight"><fmt:message key="label.supplierstatus"/>:</td>
	                           <td id="suppRatingCell" class="optionTitleLeft">
                                   <span id="suppRatingSpan">${POHeaderBean.qualificationLevel}</span>
                                   <input id="suppRating" type="hidden" name="suppRating" value="${POHeaderBean.qualificationLevel}" />
                                </td>
	                        </tr>
	                        <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.ordertaker"/>:</td>
                                <td class="optionTitleLeft">                   
                                    <input id="orderTakerID" type="hidden" class="inputBox" name="orderTakerID" value="${POHeaderBean.supplierContactId}"/> 
                                    <input onchange="invalidateOrderTaker()" id="orderTaker" <c:if test="${readonly == 'Y'}">readonly</c:if> class="inputBox" name="orderTaker" value="${POHeaderBean.supplierContactName}"/>
                                    <c:if test="${readonly == 'N'}">
                                       <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchOrderTaker" id="searchOrderTaker"  value="..." align="left" onclick="getOrderTaker()"/>
                                    </c:if>
                                </td>
                           </tr>
                           <tr> 
                                <td class="optionTitleBoldRight"><fmt:message key="label.phonenumber"/>:</td>
                                <td class="optionTitleLeft">
                                    <c:set var="supplierContactPhone" value="${POHeaderBean.supplierContactPhone}"/><span id="contactPhoneNo">${supplierContactPhone}</span>
                                </td>
                           </tr>
                           <tr>     
                                <td class="optionTitleBoldRight"><fmt:message key="label.fax"/>:</td>
                                <td class="optionTitleLeft">
                                    <c:set var="supplierContactFax" value="${POHeaderBean.supplierContactFax}"/><span id="contactFaxNo">${supplierContactFax}</span>
                                </td>
                           </tr>
                           <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.email"/>:</td>
                                <td id="supplierContactEmail" class="optionTitleLeft"><a href="mailto:${POHeaderBean.email}">${POHeaderBean.email}</a></td>
                           </tr>
	                   </table>
                    </fieldset>
			   </td>
			   <td valign="top" width="33%">
				   <fieldset style="border: 2px groove">
	                    <legend class="optionTitleBold"><fmt:message key="label.shipping"/></legend>
	                        <table width="100%" class="tableSearch" id="poHeaderTable2" border="0" cellpadding="0" cellspacing="0">
	                            <tr>
	                                <td class="optionTitleBoldRight" ><fmt:message key="label.shipto"/>:</td>
	                                <td class="optionTitleLeft">
                                       <input id="attachedPr" type=hidden name="attachedPr" value="${POHeaderBean.attachedPr}" />
                                       <input id="shipToCompanyId" type=hidden name="shipToCompanyId" value="${POHeaderBean.shipToCompanyId}"/> 
                                       <input id="shipToFacilityId" type=hidden name="shipToFacilityId" value="${POHeaderBean.shipToFacilityId}"/> 
                                       <input id="shipToLocationId" type=hidden name="shipToLocationId" value="${POHeaderBean.shipToLocationId}"/>
                                       <input onchange="headerChanged()" id="shipToId" class="inputBox" <c:if test="${readonly == 'Y' || vouchered == 'Y' }">readonly</c:if> name="shipToId" value="${POHeaderBean.shipToLocationId}"/>
                                       <c:if test="${readonly == 'N'}">
                                          <c:if test="${vouchered != 'Y' }">
                                        <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchshiptolike" id="searchshiptolike"  value="..." align="left" onclick="getShipTo()"/>
                                        </c:if>
                                       </c:if>
                                    </td>
	                            </tr>
	                            <tr>
                                    <td class="optionTitleBoldRight" >&nbsp;</td>
                                    <td id="shipToLine1" class="optionTitleLeft" >${POHeaderBean.shipToAddressLine1Display}</td>
                                </tr>
	                            <tr> 
	                                <td class="optionTitleBoldRight" >&nbsp;</td>
	                                <td id="shipToLine2" class="optionTitleLeft" >${POHeaderBean.shipToAddressLine2Display}</td>
	                           </tr>
	                           <tr> 
                                    <td class="optionTitleBoldRight">&nbsp;</td>
                                    <td id="shipToLine3" class="optionTitleLeft">${POHeaderBean.shipToAddressLine3Display}</td>
                               </tr>
                               <tr> 
                                    <td class="optionTitleBoldRight">&nbsp;</td>
                                    <td id="shipToLine4" class="optionTitleLeft">${POHeaderBean.shipToAddressLine4Display}</td>
                               </tr>
                               <tr>
                                    <td class="optionTitleBoldRight" nowrap><fmt:message key="label.shiptocompanypopage"/>:</td>                
                                    <td id="shipToLine5" class="optionTitleLeft"><span id=shiptoline5>${POHeaderBean.shipToCompanyId}</span></td>
                                </tr>
	                           <tr>
	                                <td id="shippingCell" class="optionTitleBoldRight"> <fmt:message key="label.shipping"/>: </td>
	                                <td class="optionTitleLeft">
                                       <input id="carrieraccount" type=hidden name="carrieraccount"/>
                                       <input onchange="headerChanged()" id="carrierID" class="inputBox" <c:if test="${readonly == 'Y' || vouchered == 'Y'}">readonly</c:if> name="carrierID" value="${POHeaderBean.carrier}"/>
                                       <c:if test="${readonly == 'N'}">
                                       <c:if test="${vouchered != 'Y' }">
                                        <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchCarrier" id="searchCarrier"  value="..." align="left" onclick="getCarrier()"/>
                                       </c:if>
                                       </c:if>
                                    </td>
	                           </tr>	                           
                               <tr>
                                    <td class="optionTitleBoldRight" >&nbsp;</td>     
	                                <td id="carrierLine2" class="optionTitleLeft">${POHeaderBean.carrierName} <c:if test="${! empty POHeaderBean.carrierAccount}">/${POHeaderBean.carrierAccount}</c:if></td>
	                           </tr>
	                           <tr>
                                    <td class="optionTitleBoldRight">&nbsp;</td>
                                    <c:choose>
                                        <c:when test="${POHeaderBean.carrierCompanyId == 'Radian' }">
                                        <td id="carrierLine1" class="optionTitleLeft">${POHeaderBean.carrierHub}/Haas TCM</td>
                                        </c:when>
                                        <c:otherwise>
                                        <td id="carrierLine1" class="optionTitleLeft">${POHeaderBean.carrierHub}/${POHeaderBean.carrierCompanyId}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    
                               </tr>
	                            <tr>
	                                <td class="optionTitleBoldRight"><fmt:message key="label.tradeTerms"/>:&nbsp;</td>
	                                <td class="optionTitleLeft">
	                                  <input id="fob" type="hidden" name="fob" value = "${POHeaderBean.freightOnBoard}"/>
	                                   <select name="selectFob" id="selectFob" size=1 class="selectBox" onchange="fobChanged()" <c:if test="${readonly == 'Y' || vouchered == 'Y'}">disabled</c:if> >
	                                        <option value="None"><fmt:message key="label.pleaseselect"/></option>
	                                        <c:forEach var="vvFreightOnBoardBean" items="${poFobCol}" varStatus="status">
	                                               <option value="${vvFreightOnBoardBean.freightOnBoard}" <c:if test="${POHeaderBean.freightOnBoard == vvFreightOnBoardBean.freightOnBoard}"> selected </c:if>><c:out value="${vvFreightOnBoardBean.description}"/></option>
	                                        </c:forEach>
	                                   </select>
	                                </td>
	                           </tr>
	                       </table>
	                 </fieldset>
			     </td>
                 <td valign="top" width="33%">
                    <fieldset style="border: 2px groove">
                        <legend class="optionTitleBold"><fmt:message key="label.status"/></legend>
                        <table width="100%" class="tableSearch" id="poHeaderTable7" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.critical"/>:</td>
                                <td class="optionTitleLeft"><select id="criticalPo" class="selectBox" size="1" name="criticalPo" onchange="headerChanged()"  <c:if test="${readonly == 'Y'}">disabled</c:if> >
                                        <option value='N' <c:if test="${POHeaderBean.critical == 'N'}">selected</c:if>>N</option>
                                        <option value='Y' <c:if test="${POHeaderBean.critical == 'Y'}">selected</c:if>>Critical</option>
                                        <option value='S' <c:if test="${POHeaderBean.critical == 'S'}">selected</c:if>>AOG</option>
                                        <option value='L' <c:if test="${POHeaderBean.critical == 'L'}">selected</c:if>>LD</option>
                                    </select>
                                </td>
                           </tr>
                           <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.hub"/>:&nbsp;</td>
                                <td class="optionTitleLeft">
                                   <input id="hubFullName" type="hidden" name="hubFullName"/>
                                   <input id="hubName" type=hidden name="hubName" value="${POHeaderBean.branchPlant}" />
                                   <span id="hubNameSpan">${HubName}</span>                               
                               </td>
                           </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:</td>
                                 <td class="optionTitleLeft">
                                    <input id="invenGrp" type="hidden" name="invenGrp" value="${POHeaderBean.inventoryGroup}" />
                                    <span id="invenGrpSpan">${POHeaderBean.inventoryGroup}</span>
                                 </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.buyer"/>:</td>
                                <td id="buyerIdCell"  class="optionTitleLeft">
                                    <c:choose>
                                        <c:when test="${readonly == 'Y'}">
                                            <b><span id="buyer">${POHeaderBean.buyerName}</span></b>
                                            <input id="buyerId" type="hidden" name="buyerId" value="${POHeaderBean.buyer}" />
                                        </c:when>
                                        <c:otherwise>
                                            <select name="buyerId" id="buyerId" size=1 class="selectBox">
                                                <option value="-1"><fmt:message key="label.pleaseselect"/></option>
                                                <c:forEach var="vvPoBuyerNamesCol" items="${poBuyerNamesCol}" varStatus="status">
                                                       <option value="${vvPoBuyerNamesCol.personnelId}" <c:if test="${POHeaderBean.buyer == vvPoBuyerNamesCol.personnelId}"> selected </c:if>><c:out value="${vvPoBuyerNamesCol.lastName}"/></option>
                                                </c:forEach>
                                           </select>                                        
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                           </tr>
                           <tr>
                                <td class="optionTitleBoldRight" ><fmt:message key="label.customerpo"/>:</td>
                                <td class="optionTitleLeft">
                                   <input id="customerPo" class="inputBox" maxlength="30" name="customerPo" <c:if test="${readonly == 'Y' || vouchered == 'Y'}">readonly</c:if> value="${POHeaderBean.customerPo}"/>
                                </td>                            
                            </tr>
                            <tr>
                                 <td class="optionTitleBoldRight" > <fmt:message key="label.dateconfirmed"/>:</td>
                                 <td class="optionTitleLeft">
                                    <fmt:formatDate var="dateSent" value="${POHeaderBean.dateSent}" pattern="${dateFormatPattern}"/>
                                    <input id="faxdate" type="hidden" readonly size="15" name="faxdate" value="${dateSent}"/>
                                    <span id="faxdateSpan">${dateSent}</span>
                                 </td>
                            </tr>
                            <tr>
                                 <td class="optionTitleBoldRight" ><fmt:message key="label.dateaccepted"/>:</td>
                                 <td class="optionTitleLeft" >
                                    <fmt:formatDate var="dateAccepted" value="${POHeaderBean.dateAccepted}" pattern="${dateFormatPattern}"/>
                                    <input id="acceptedDate" type="hidden" name="acceptedDate" value="${dateAccepted}" />
                                    <span id="acceptedDateSpan">${dateAccepted}</span>
                                 </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" ><fmt:message key="label.consignedpo"/>:</td>
                                <td class="optionTitleLeft" >
                                   <input id="consignedPo" name="consignedPo" type="checkbox" <c:if test="${receivedStatus == 'Y' }"> disabled</c:if> class="radioBtns" <c:if test="${POHeaderBean.consignedPo == 'y'}">checked</c:if> value="y" />
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap><fmt:message key="label.paymentterms"/>:</td>
                                <td class="optionTitleLeft">
                                	<input id="paymentTerms" type="hidden" name="paymentTerms" value="${POHeaderBean.paymentTerms}" />
                                    <select name="selectPaymentTerms" id="selectPaymentTerms" class="selectBox" onchange="checkForCrediCartConf()" <c:if test="${(readonly == 'Y' || allowPaymentTermsUpdate == 'N') && POHeaderBean.radianPo != null}">disabled</c:if> >
                                         <option value=""><fmt:message key="label.pleaseselect"/></option>
                                         <c:forEach var="vvPaymentTermsBean" items="${poPaymentTermsCol}" varStatus="status">
                                                <option value="${vvPaymentTermsBean.paymentTerms}" <c:if test="${POHeaderBean.paymentTerms == vvPaymentTermsBean.paymentTerms}"> selected </c:if>><c:out value="${vvPaymentTermsBean.paymentTerms}"/></option>
                                         </c:forEach>
                                    </select>
                                </td>
                            </tr>                                    
                            <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.totalamount"/>:</td>
                                <td class="optionTitleLeft" > <input id="totalCost" type="hidden" name="totalCost"/> <span id="totalCostSpan"></span> &nbsp;
                                   <!-- <select name="currency" id="currency" onchange="currencyChanged()" class="selectBox" <c:if test="${readonly == 'Y' || allowCurrencyupdate == 'N' || vouchered == 'Y'}">disabled</c:if>>
                                        <option value=""><fmt:message key="label.pleaseselect"/></option>
                                        <c:forEach var="vvCurrencyBean" items="${poCurrency}" varStatus="status">
                                               <option value="${vvCurrencyBean.currencyId}" ><c:out value="${vvCurrencyBean.currencyId}"/></option>
                                        </c:forEach>
                                   </select>
                                    -->
                               </td>
                            </tr>
                            <c:if test="${varIsUSBuyer == 'Y'}">
                            <tr>
                                <td class="optionTitleBoldRight"><fmt:message key="label.totalamountinusd"/>:</td>
                                <td class="optionTitleLeft" > <input id="totalCostInUSD" type="hidden" name="totalCostInUSD"/> <span id="totalCostInUSDSpan"></span> &nbsp;</td>
                            </tr>                           
                            </c:if> 
                        </table>
				 </fieldset>
               </td>
			</tr>			
		</tbody>
	</table>
	<!--  template starts here -->
 
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>
<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->
<!-- <form name='goConfirmForm' action='goconfirm.do'>-->
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
	<div id="newChemTabs">
	 <ul id="firstTabList">
	 </ul>
	</div>
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<input type='hidden' name="radianPo" id="radianPo" value='<c:out value="${POHeaderBean.radianPo}"/>' />
  <c:if test="${POHeaderBean.radianPo != null}">
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent" id="poGrids">
  
    <div class="boxhead">          
          <input type="hidden" name="poAction" id="poAction" value="save" />
          
          <%--<c:if test="${(noPermission == 'Y' || readonly == 'N') && everConfirmed == 'N'}">
            <c:if test="${empty ConfirmedBean && everConfirmed == 'N'}">--%>
            <c:if test="${readonly == 'N'}">
                   <span id="addbuyorderslink"><a href="#" onclick="getBuyOrders();"><fmt:message key="label.addbuyorders"/></a></span>
                   <span id="addlineitemlink"> | <a href="#" onclick="addLineItem();"><fmt:message key="label.addMaterial"/></a></span>
                   <span id="addlinechargelink"> | <a href="#" onclick="addLineCharge();"><fmt:message key="label.addCharge"/></a></span>
                   <!-- <span id="removelinelink"> | <a href="#" onclick="return removeLine();"><fmt:message key="label.removeLine"/></a></span>
                   <span id="undoremovelink"> | <a href="#" onclick="return unRemoveLine();"><fmt:message key="label.undoRemove"/></a></span> -->                   
                   <!--  <span id="savelink"> | <a href="#" onclick="return submitPurchaseOrder('save');"><fmt:message key="label.savepoline"/></a></span>-->
                   <span id="savelink"> | <a href="#" onclick="javascript:savePoLineChangesFromLink();"><fmt:message key="label.savepoline"/></a></span>
                   <c:choose>
	                   <c:when test="${!POHeaderBean.blnProblemWithBuy}">
	                       <span id="confirmlink"> | <a href="#" onclick="return submitPurchaseOrder('confirm');"><fmt:message key="label.confirmPO"/></a></span>
	                   </c:when>
	                   <c:otherwise>
	                       <span id="resendwbuypolink"> | <a href="#" onclick="resendWBuyPO();"><fmt:message key="label.resendwbuypobutton"/></a></span>
	                   </c:otherwise>
                   </c:choose>
                   <c:if test="${readonly != 'N' && purchasing == 'Y'}">
                      <span id="financialapprovallink" style="display: none"> | <a href="#" onclick="javascript:performFinancialApproval();"><fmt:message key="label.financialapproval"/></a></span>
                   </c:if>                   
                   <span id="printlink"> | <a href="#" onclick="return printPo();"><fmt:message key="label.print"/></a></span>
                   <c:if test="${everConfirmed == 'Y'}">
                    <span id="poexpeditenoteslink"> | <a href="#" onclick="showPoExpediteNotes();"><fmt:message key="label.poexpeditenotes"/></a></span>
                   </c:if> 
             </c:if>                
            <%--</c:if>
          </c:if>--%>
	</div>
	<div id="lineItemBean" style="width:99%;height:250px;"></div>	
	<img id="spinner" style="display:none" src="/images/ajax-loader.gif" />

     <div id="lineItemDetailRows" style="display:none">
	     <c:if test="${POHeaderBean.radianPo != null}">
		   <table width="99%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		      <tr>
		          <td align="left">
                     <fieldset style="border: 2px groove">
                        <legend class="optionTitleBold"><fmt:message key="label.linedetails"/></legend>                        
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.line"/>:</td>
                                <td class="optionTitleLeft"><span id="detailPoLineNumber"/></td>
                            </tr>                        
                            <tr>
	                            <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.mpn"/>:</td>
			                    <td class="optionTitleLeft"><span id="mpn"/></td>
			                </tr>
			                <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.supplierpn"/>:</td>
                                <td class="optionTitleLeft">
                                    <input onchange="secondarySupplierPNChanged()" id="supplierPn" maxlength="30" class="inputBox" name="supplierPn"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.dpas"/>:</td>
                                <td class="optionTitleLeft">
                                    <input onchange="secondarySupplierPNChanged()" id="dpas" maxlength="4" class="inputBox" name="dpas"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><input id="slHidden" type="hidden" value="" name="slHidden" /><fmt:message key="label.shelflife"/>:</td>
                                <td class="optionTitleLeft">
                                    <input onchange="checkShelfLife()" id="shelfLife" class="inputBox" value="" name="shelfLife"/><span class="optionTitleBold">%</span>&nbsp;<span id="shelfLifeBasis"/>
                                    
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.lastconfirmed"/>:</td>
                                <td class="optionTitleLeft"><span id="lastconfirmed"/></td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.desc"/>:</td>
                                <td class="optionTitleLeft"><span id="itemDesc"/></td>
                            </tr>  
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.suppliershiplocation"/>:</td>
                                <td class="optionTitleLeft">
                                    <input onchange="changeSuppTotalPrice()" id="supplierQty" type=hidden name="supplierQty" /> 
			                        <input onchange="setLineUnconfirmed()" id="supplierPkg" maxlength=30 type=hidden name="supplierPkg" /> 
			                        <input onchange="changeSuppTotalPrice()" id="supplierUnitPrice" type=hidden name="supplierUnitPrice" /> 
			                        <input id="supplierExtPrice" type=hidden value="" name="supplierExtPrice" />
			                        <span id="shipFromLocationId"/>&nbsp;
                                    <input name="enterSoleSource" type="button" class="inputBtns" value=<fmt:message key="label.solesourcepricequote"/> id="enterSoleSource" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="enterSoleSourcePriceQuote()"/> 
			                     </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" valign="top" width="10%"><fmt:message key="label.secondarysupplier"/>:</td>
                                <td class="optionTitleLeft">
                                    <div id="secondarySupplierEdit" style="display:none">
                                        <c:if test="${readonly == 'N'}">
                                            <input onchange="setLineUnconfirmed()" id="secondarySupplier" maxlength="30" size="12" name="secondarySupplier"/>&nbsp;
                                            <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchSecondarySupplier" id="searchSecondarySupplier"  value="..." align="left" onclick="getSecondarySupplier()"/>
                                        </c:if>
                                    </div>&nbsp;&nbsp;
                                    <span id="secondarySupplierAddress"></span>
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight" nowrap="nowrap" width="10%"><fmt:message key="label.verifiedby"/>:</td>
                                <td class="optionTitleLeft"><span id="itemVerifiedBy" /></td>
                            </tr> 
                        </table>
                    </fieldset>
                 </td>                 
	          </tr>
			</table>
	     </c:if>
	 </div>
	 <br/>
         <div id="materialTypeItem" style="display:none">
			<c:if test="${poLineDetailCol != null}">
				<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tableSearch" id="tableSearch">
					<tr valign="top">
						<td><input type="hidden" id="lookAheadStatus" name="lookAheadStatus" value="No" />
						    <input type="hidden" id="lookAheadLoaded" name="lookAheadLoaded" value="No" />
							<b><fmt:message key="label.lookahead"/>: </b>
							 <a href="javascript:getLookAheads()">
							     <img id="lookaheadimg" alt="<fmt:message key="label.lookahead"/>" src="/images/plus.jpg"/>
							</a>							
						</td>
						<td><div class="boxhead" id="lookAheadLink" style="display:none" ><c:if test="${readonly == 'N'}">
                                <a href="javascript:saveLookaheadInfo()"><fmt:message key="label.savelookaheadchanges"/></a></c:if>
                            </div>
						  <div id="lookAheadBean" style="display:none">
						      <div id="lookAheadTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
						  </div>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td><b><fmt:message key="label.linenotes"/>: </b>
						</td>
						<td>
						  <c:if test="${readonly == 'N'}">                        
                            <span class="boxhead" id="cannedcomments">
                                <a href="javascript:getCannedComments()"><fmt:message key="label.addcanned"/></a>&nbsp;|&nbsp;                          
                               <a href="javascript:savePoLineChangesFromLink()"><fmt:message key="label.savechanges"/></a>
                            </span>
                            <br/>
                          </c:if>
						  <textarea onchange="setLineUnconfirmed()" rows=5 cols=130 id="lineNotes" name="lineNotes" ></textarea>
						&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td><b><fmt:message key="label.deliverynotes"/>:</b></td>
						<td><textarea onchange="setLineUnconfirmed()" cols=130 id="deliveryComments" name="deliveryComments"></textarea>
						&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td><input type="hidden" id="itemNotesStatus" name="itemNotesStatus" value="No" />
						    <input type="hidden" id="itemNotesLoaded" name="itemNotesLoaded" value="No" />
						    <b><fmt:message key="label.itemnotes"/>: </b>
						    <a href="javascript:getItemNotes()" >
						      <img id="itemnotesimage" alt="<fmt:message key="label.itemnotes"/>" src="/images/plus.jpg" />
						    </a>
						</td>
						<td><div id="itemNotesLink" style="display:none" >
						      <c:if test="${readonly == 'N'}"><span class="boxhead" id="additemnotes"><a href="javascript:addItemNotes()"><fmt:message key="label.additemnotes"/></a></span><br />                             
                            </c:if></div>
						    <div id="itemNotesBean" style="display:none">
						       <div id="itemNotesTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
						    </div>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td><input type="hidden" id="partNotesStatus" name="partNotesStatus" value="No" />
						    <input type="hidden" id="partNotesLoaded" name="partNotesLoaded" value="No" />
							<b><fmt:message key="label.partnotes"/>: </b><a href="javascript:getPartNotes()">
							<img id="partnotesimage" alt="<fmt:message key="label.partnotes"/>" src="/images/plus.jpg"/></a>				
						</td>
						<td><div id="partNotesBean" style="display:none">
						      <div id="partNotesTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
						    </div>
					    </td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
		                <td><input type="hidden" id="associatedPrsStatus" name="associatedPrsStatus" value="No" />
		                    <input type="hidden" id="associatedPrsLoaded" name="associatedPrsLoaded" value="No" />
			                <b><fmt:message key="label.associatedprs"/>: </b><a href="javascript:getAssociatedPrs()">
			                <img id="associatedPrsimage" alt="<fmt:message key="label.associatedprs"/>" src="/images/plus.jpg"/></a>
		                </td>
		                <td>
		                  <div id="associatedPrsLink" style="display:none" >
                                <span class="boxhead" id="editAssociatedPrs"><a href="javascript:editAssociatedPrs()"><fmt:message key="label.edit"/></a></span><br />                            
		                  </div>
		                  <div id="associatedPrsBean" style="display:none">
		                      <div id="associatedPrsTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
		                  </div>
		                </td>
		            </tr>
		            <tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td><input type="hidden" id="specsStatus" name="specsStatus" value="No" />
						    <input type="hidden" id="specsLoaded" name="specsLoaded" value="No" />
							<b><fmt:message key="label.specs"/>: </b><a href="javascript:getSpecs()">
							<img id="specimage" alt="<fmt:message key="label.specs"/>" src="/images/plus.jpg"/></a>
						</td>
						<td><div class="boxhead"  id="specsLink" style="display:none" >
							    <a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a><c:if test="${readonly == 'N'}">&nbsp;|&nbsp;						    
	                            <a href="javascript:saveSpecsInfo()"><fmt:message key="label.savespecschanges"/></a></c:if>
                            </div> 
						  <div id="specsBean" style="display:none">
						      <div id="specsTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
						  </div>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
						<td><input type="hidden" id="flowdownsStatus" name="flowdownsStatus" value="No" />
						    <input type="hidden" id="flowdownsLoaded" name="flowdownsLoaded" value="No" />
							<b><fmt:message key="label.flow.down"/>: </b><a href="javascript:getFlowdowns()">
							<img id="flowdownimage" alt="<fmt:message key="label.flow.down"/>" src="/images/plus.jpg"/></a>				
						</td>
						<td>
						  <div class="boxhead" id="flowdownsLink" style="display:none" ><c:if test="${readonly == 'N'}">
                               <a href="javascript:saveFlowdownsInfo()"><fmt:message key="label.saveflowdownschanges"/></a></c:if>
                          </div> 
						  <div id="flowdownsBean" style="display:none">
						      <div id="flowdownsTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
						  </div>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr valign="top">
		                <td><input type="hidden" id="msdsStatus" name="msdsStatus" value="No" />
		                    <input type="hidden" id="msdsLoaded" name="msdsLoaded" value="No" />
			                <b><fmt:message key="label.sds"/>: </b><a href="javascript:getMsds()">
			                <img id="msdssearchimage" alt="<fmt:message key="label.sds"/>" src="/images/plus.jpg"/></a>                
		                </td>
		                <td>
		                  <div class="boxhead" id="msdsLink" style="display:none" ><c:if test="${readonly == 'N'}">
                               <a href="javascript:saveMsdsInfo()"><fmt:message key="label.savesdschanges"/></a></c:if>
                          </div>
		                  <div style="display:none;font-family:Arial, Helvetica, sans-serif;color:#ffffff;text-align:center;font-size:12px;background:url(../../../../images/mmenu/tabletop3.gif) repeat-x left top" id="msdsCheck">
		                      <fmt:message key="label.askedsuppsendmsds"/>: <input type='checkbox' id='sendMsdsCheckBox' onclick="setLineUnconfirmed()" name='sendMsdsCheckBox' <c:if test="${readonly == 'Y'}"> disabled </c:if> value="Yes"   />
		                  </div>	                
    		              <div id="msdsBean" style="display:none">
    		                   <div id="msdsTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
    		              </div>
		                </td>
		            </tr>
		            <tr><td colspan="2">&nbsp;</td></tr>
		            <tr  valign="top">
		                <td><input type="hidden" id="tcmBuysStatus" name="tcmBuysStatus" value="No" />
		                    <input type="hidden" id="tcmBuysLoaded" name="tcmBuysLoaded" value="No" />
			                <b><fmt:message key="label.tcmbuys"/>: </b><a href="javascript:getTcmBuys();">
			                <img id="tcmbuysimage" alt="<fmt:message key="label.tcmbuys"/>" src="/images/plus.jpg"/></a>                
		                </td>
		                <td><div id="tcmBuysBean" style="display:none">
		                      <div id="tcmBuysTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
		                    </div>
	                    </td>
		            </tr>
		            <tr><td colspan="2">&nbsp;</td></tr>
		            <tr valign="top">
		                <td><input type="hidden" id="clientBuysStatus" name="clientBuysStatus" value="No" />
		                    <input type="hidden" id="clientBuysLoaded" name="clientBuysLoaded" value="No" />
			                <b><fmt:message key="label.clientbuys"/>: </b><a href="javascript:getClientBuys()">
			                <img id="clientbuysimage" alt="<fmt:message key="label.clientbuys"/>" src="/images/plus.jpg"/></a>                
		                </td>
		                <td><div id="clientBuysBean" style="display:none">
		                      <div id="clientBuysTransitPage" style="display: none;" class="optionTitleBoldCenter"><br/><br/><br/><fmt:message key="label.pleasewait" /> <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/></div>
		                    </div>
		                </td>
		            </tr>            
				</table>			
			</c:if>
		  </div>		
	 
	<c:if test="${poLineDetailCol != null}">
		<div id="addChargesList" style="display:none">
		   <table width="99%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		      <tr>
                  <td>
                    <fieldset style="border: 2px groove">
                        <legend class="optionTitleBold" width="10%" nowrap="nowrap"><fmt:message key="label.linedetails"/></legend>
                        <table width="100%" class="tableSearch" id="poHeaderTable7" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="optionTitleBoldRight"width="10%" nowrap="nowrap"> <fmt:message key="label.line"/>:</td>
                            <td class="optionTitleLeft"><span id="detailLineAlt"/></td>                          
                        </tr>                        
                        <tr>
		                    <td class="optionTitleBoldRight" width="10%" nowrap="nowrap"><fmt:message key="label.lastconfirmed"/>:</td>
		                    <td class="optionTitleLeft"><span id="lastconfirmedalt"/></td>		                    
		                </tr>
		                <tr>
		                    <td class="optionTitleBoldRight" width="10%" nowrap="nowrap"><fmt:message key="label.desc"/>:</td>
		                    <td class="optionTitleLeft"><span id="itemdescalt"></span></td>
		                </tr>
		                <tr>
		                    <td class="optionTitleBoldRight" width="10%" nowrap="nowrap"><fmt:message key="label.secondarysupplier"/>:</td>
		                    <td class="optionTitleLeft">
                                <div id="secondarySupplierEdit" style="display:none">
                                    <c:if test="${readonly == 'N'}">
                                        <input type="text" onchange="setLineUnconfirmed()" id="secondarySupplier" maxlength="30" size="12" name="secondarySupplier"/>&nbsp;
                                        <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchSecondarySupplier" id="searchSecondarySupplier"  value="..." align="left" onclick="getSecondarySupplier()"/>
                                    </c:if>
                                </div>&nbsp;&nbsp;
                                <span id="secondarySupplierAddress"/>
                            </td>
		                </tr>
                        </table>
                    </fieldset>
                 </td>
              </tr>	            
	        </table>	        
	        <div id="addChargesDivBlock" style="display:none">
		        <table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tableSearch" id="tableSearch">
		            <tr valign="top">          
		                <td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;
		                    <input type="hidden" id="chargesListStatus" name="chargesListStatus" value="No" />
	                        <input type="hidden" id="chargesListLoaded" name="chargesListLoaded" value="No" />                   
		                    <div id="chargesListBean" style="width:100%;height:250px;"></div><img id="spinner" style="display:none" src="/images/ajax-loader.gif" />
		                </td>
		            </tr>
		        </table>
	        </div>	        
		</div>
	</c:if>	
	</div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
   </c:if>
</td></tr>
</table>
<!-- Search results end -->
<!-- </form>--><!-- end of goConfirmForm  -->

<div class="backGroundContent"> <!-- start of backGroundContent -->
	<div id="newChemTabs">
	 <ul id="secondTabList">
	 </ul>
	</div>

	<div id="transitPage" style="display:none; overflow: auto;" class="optionTitleBoldCenter">
	  <br/><br/><br/><fmt:message key="label.pleasewait"/>
	  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
	</div>
	
	<%-- result count and time --%>
	<div id="footer" class="messageBar"></div>
	<%-- transition --%>
	<div id="transitDailogWin" class="errorMessages" style="display:none">
	    <table width="100%" border="0" cellpadding="2" cellspacing="1">
	        <tr><td>&nbsp;</td></tr>
	        <tr><td>&nbsp;</td></tr>
	        <tr><td>&nbsp;</td></tr>
	        <tr><td align="center" id="transitLabel"></td></tr>
	        <tr><td align="center"><img src="/images/rel_interstitial_loading.gif" align="middle"></td></tr>
	    </table>
	</div>

</div> <!-- close of background 1 -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input type="hidden" name="uAction" id="uAction" value=""/>
 <input type="hidden" name="action" id="action" value=""/>
 <c:set var="validshiptovar" value="No"/>
 <c:if test="${POHeaderBean.shipToLocationId != ''}">
 <c:set var="validshiptovar" value="Yes"/>
 </c:if>
 <input type="hidden" name="validShipTo" id="validShipTo" value="<c:out value="${validshiptovar}"/>" />
 <input type="hidden" name="validPo" id="validPo" value="Yes"/>
 <input type="hidden" name="validSupplier" id="validSupplier" value=""/>
 <input type="hidden" name="validOrderTaker" id="validOrderTaker" value=""/>
 <input type="hidden" name="validCarrier" id="validCarrier" value=""/>
 <input type="hidden" name="dbuyLockStatus" id="dbuyLockStatus" value="${POHeaderBean.dbuyLockStatus}"/>
 <input type="hidden" name="opsEntityId" id="opsEntityId" value="${POHeaderBean.opsEntityId}"/>
 <input type="hidden" name="transport" id="transport" value="${POHeaderBean.transport}"/>
 <input type="hidden" name="nonintegerReceiving" id="nonintegerReceiving" value="${POHeaderBean.nonintegerReceiving}"/>
 <input type="hidden" name="supplierDefaultPaymentTerms" id="supplierDefaultPaymentTerms" value="${POHeaderBean.supplierDefaultPaymentTerms}"/>
 <input type="hidden" name="headerChanged" id="headerChanged" value=""/>
 <input type="hidden" name="supplierCountryAbbrev" id="supplierCountryAbbrev" value="${POHeaderBean.supplierCountryAbbrev}"/>
 <input type="hidden" name="allowPOCreditConfirmaiton" id="allowPOCreditConfirmaiton" value="${allowPOCreditConfirmaiton}"/>
 <input type="hidden" name="rowNumber" id="rowNumber" value=""/>
 <input type="hidden" name="poLine" id="poLine" value=""/>
 <input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
 <input type="hidden" name="endSearchTime" id="endSearchTime" value=""/>
 <input type="hidden" name="isUSBuyer" id="isUSBuyer" value="${varIsUSBuyer}"/>
 <input type="hidden" name="allowPOFinancialConfirmaiton" id="allowPOFinancialConfirmaiton" value="${allowPOFinancialConfirmaiton}"/>
 <input type="hidden" name="currencyExchangeRate" id="currencyExchangeRate" value="${currencyExchangeRate}"/>
 <input type="hidden" name="orderTotalInUSD" id="orderTotalInUSD" value="${orderTotalInUSD}"/>
 <input type="hidden" name="totalItemQty" id="totalItemQty" value=""/> <!-- this holds the total qty of all the associated prs. i.e. the sum of all the order_quantity of the associated PRs -->
  
 <!-- Popup Calendar input options for needDate -->
 <input type="hidden" name="blockBefore_needDate" id="blockBefore_needDate" value=""/>
 <input type="hidden" name="blockAfter_needDate" id="blockAfter_needDate" value=""/>
 <input type="hidden" name="blockBeforeExclude_needDate" id="blockBeforeExclude_needDate" value=""/>
 <input type="hidden" name="blockAfterExclude_needDate" id="blockAfterExclude_needDate" value=""/>
 <input type="hidden" name="inDefinite_needDate" id="inDefinite_needDate" value=""/>

  <!-- Popup Calendar input options for promisedDate -->
 <input type="hidden" name="blockBefore_promisedDate" id="blockBefore_promisedDate" value=""/>
 <input type="hidden" name="blockAfter_promisedDate" id="blockAfter_promisedDate" value=""/>
 <input type="hidden" name="blockBeforeExclude_promisedDate" id="blockBeforeExclude_promisedDate" value=""/>
 <input type="hidden" name="blockAfterExclude_promisedDate" id="blockAfterExclude_promisedDate" value=""/>
 <input type="hidden" name="inDefinite_promisedDate" id="inDefinite_promisedDate" value=""/>
 
  <!-- Popup Calendar input options for projectedDate -->
 <input type="hidden" name="blockBefore_projectedDate" id="blockBefore_projectedDate" value=""/>
 <input type="hidden" name="blockAfter_projectedDate" id="blockAfter_projectedDate" value=""/>
 <input type="hidden" name="blockBeforeExclude_projectedDate" id="blockBeforeExclude_projectedDate" value=""/>
 <input type="hidden" name="blockAfterExclude_projectedDate" id="blockAfterExclude_projectedDate" value=""/>
 <input type="hidden" name="inDefinite_projectedDate" id="inDefinite_projectedDate" value=""/>
 
 
 </div>
<!-- Hidden elements end -->


</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->


</tcmis:form>

        <%-- show legend Begins --%>
        <div id="showLegendArea" style="position:fixed;display: none;">
          <table width="100%" class="tableResults" border="0" cellpadding="0" cellspacing="0">
            <tr><td width="100px" class="green">&nbsp;</td><td class="legendText"><fmt:message key="label.specswithpartonbo"/></td></tr>
            <tr><td width="100px" class="yellow">&nbsp;</td><td class="legendText"><fmt:message key="label.specassociatedwithinv"/></td></tr>
            <tr><td width="100px" class="orange">&nbsp;</td><td class="legendText"><fmt:message key="label.specwithotherinven"/></td></tr>
            <tr><td width="100px" class="red">&nbsp;</td><td class="legendText"><fmt:message key="label.allspecsinallloc"/></td></tr>
            <tr><td width="100px" class="purple">&nbsp;</td><td class="legendText"><fmt:message key="label.obsoletespecs"/></td></tr>
          </table>
        </div>
        
</body>
</html:html>
