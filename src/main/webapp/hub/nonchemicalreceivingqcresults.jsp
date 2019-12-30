<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/nonchemicalreceivingqcresults.js"></script>


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
<script type="text/javascript" src="/js/hub/bindata.js"></script>

<title>
    <fmt:message key="label.receivingqc"/> 
</title>
<script language="JavaScript" type="text/javascript">
<!--


with(milonic=new menuname("nonreceivingqcMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
  aI("text=<fmt:message key="receivinghistory.label.approved.itemtitle"/>;url=javascript:showPreviousReceivedQc();");
  aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:'<fmt:message key="label.recordFound"/>',
searchDuration:'<fmt:message key="label.searchDuration"/>',
minutes:'<fmt:message key="label.minutes"/>',
seconds:'<fmt:message key="label.seconds"/>',
validvalues:'<fmt:message key="label.validvalues"/>',
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
expiredate:"<fmt:message key="label.expiredate"/>",
all:"<fmt:message key="label.all"/>",
mfglot:"<fmt:message key="label.mfglot"/>",
forreceipt:"<fmt:message key="label.forreceipt"/>",
incoming:"<fmt:message key="label.incoming"/>",
transferreceiptid:"<fmt:message key="label.transferreceiptid"/>",
bin:"<fmt:message key="label.bin"/>",
qtybeingreceived:"<fmt:message key="label.qtybeingreceived"/>",
qtyreceivednotmatch:"<fmt:message key="label.qtyreceivednotmatch"/>",
packagedqtyreceived:"<fmt:message key="label.packagedqtyreceived"/>",
checkpackagedsize:"<fmt:message key="label.checkpackagedsize"/>",
lotstatus:"<fmt:message key="label.lotstatus"/>",
cannotbe:"<fmt:message key="label.cannotbe"/>",
incoming:"<fmt:message key="label.incoming"/>",
viewpurchaseorder:"<fmt:message key="label.viewpurchaseorder"/>", 
nopermissionstoqcstatus:"<fmt:message key="label.nopermissionstoqcstatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
nopermissiontochangestatus:"<fmt:message key="label.nopermissiontochangestatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
labelquantity:"<fmt:message key="label.labelquantity"/>",
mustBeInteger:"<fmt:message key="label.errorinteger"/>",
mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
actsupshpdate:"<fmt:message key="label.actsupshpdate"/>",
potitle:"<fmt:message key="receivinghistory.label.approved.potitle"/>",
previouspotitle:"<fmt:message key="receivinghistory.label.potitle"/>",
itemtitle:"<fmt:message key="receivinghistory.label.approved.itemtitle"/>",
receiptspecs:"<fmt:message key="label.receiptspecs"/>",
viewaddreceipts:"<fmt:message key="pickingqc.viewaddreceipts"/>",
viewcustomerreturnrequest:"<fmt:message key="label.viewcustomerreturnrequest"/>",
viewrma:"<fmt:message key="label.viewrma"/>",
itemnotes:"<fmt:message key="itemnotes.title"/>",
receivingchecklist:"<fmt:message key="label.receivingchecklist"/>",
customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
indefinite:"<fmt:message key="label.indefinite"/>",
expdatelessthanminexpdate:"<fmt:message key="label.expdatelessthanminexpdate"/>",
qastatement:"<fmt:message key="label.qastatement"/>",
norowselected:"<fmt:message key="label.norowselected"/>",
sendinghubwillbealtered:"<fmt:message key="label.sendinghubwillbealtered"/>",
startmarstest:"<fmt:message key="label.startmarstest"/>",
marsdetail:"<fmt:message key="label.marsdetail"/>",
groupReceiptDoc:"<fmt:message key="label.groupReceiptDoc"/>",
shipbeforemanufacture:'<fmt:message key="label.shipbeforemanufacture"/>',
dom:"<fmt:message key="receivedreceipts.label.dom"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
dos:"<fmt:message key="label.manufacturerdos"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};


var gridConfig = {
	divName:'ReceivingQcBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, 
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	singleClickEdit:true,
	selectChild: 1,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	//onBeforeSorting:_onBeforeSorting
  }; 



<c:set var="receivingQcPermission" value=''/>
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" facilityId="${param.sourceHubName}">
   <c:set var="receivingQcPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>
 
<c:if test="${!empty receivingQcViewRelationBeanCollection}">  
	<c:if test="${receivingQcPermission == 'Yes'}">
	 showUpdateLinks = true;
	 </c:if>
</c:if>


var config = [

{ columnId:"radianPoDisplay",columnName:'<fmt:message key="label.po"/>',width:5},
{ columnId:"poLine",columnName:'<fmt:message key="label.poline"/>',width:5},
{ columnId:"inventoryGroupName",columnName:'<fmt:message key="label.inventorygroup"/>'},
{ columnId:"lineDesc",columnName:'<fmt:message key="label.description"/>',width:17,tooltip:"Y"},
<c:if test="${receivingQcPermission == 'Yes'}">
{ columnId:"okPermission"},
{ columnId:"ok",columnName:'<fmt:message key="label.ok"/>',type:'hch',width:3,permission:true},
{ columnId:"mfgLot",columnName:'<fmt:message key="receiving.label.supplieref"/>'},
{ columnId:"origMfgLot",columnName:'<fmt:message key="receivingqc.label.origlot"/>'},
{ columnId:"endDate",columnName:'<fmt:message key="receivingqc.label.enddate"/>'},
{ columnId:"dateOfReceipt",columnName:'<fmt:message key="receivedreceipts.label.dor"/>'},
{ columnId:"bin",columnName:'<fmt:message key="label.bin"/>',width:11},
{ columnId:"receiptId",columnName:'<fmt:message key="label.receiptid"/>',align:"center",width:7},
{ columnId:"transferReceiptId",columnName:'<fmt:message key="receivingqc.label.transreceiptid"/>',align:"center",width:5},
{ columnId:"quantityReceived",columnName:'<fmt:message key="label.quantityreceived"/>',align:"center",width:5},
{ columnId:"packaging",columnName:'<fmt:message key="label.packaging"/>',width:15,tooltip:"Y"},
{ columnId:"notesPermission"},
{ columnId:"notes",columnName:'<fmt:message key="label.notes"/>',type:'txt',permission:true,width:10},
{ columnId:"deliveryTicketPermission"},
{ columnId:"deliveryTicket",columnName:'<fmt:message key="receiving.label.deliveryticket"/>',width:13,type:'hed',permission:true},
{ columnId:"reverseReceiptDisplay",columnName:'<fmt:message key="label.reverse"/>',align:"center"},
</c:if>
{ columnId:"radianPo"},
{ columnId:"itemId"},
{ columnId:"branchPlant"},
{ columnId:"inventoryGroup"},
{ columnId:"qcOk"},
{ columnId:"manageKitsAsSingleUnit"},
{ columnId:"mvItem"},
{ columnId:"docType"}
];	


//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);checklinks();">
<tcmis:form action="/receivingqcresults.do" onsubmit="return submitFrameOnlyOnce();">
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

<div id="ReceivingQcBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="colorClass" value=''/>
<c:if test="${empty receivingQcViewRelationBeanCollection}">
		<%-- If the collection is empty say no data found --%> 
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
			<tr>
				<td width="100%"><fmt:message key="main.nodatafound" /></td>
			</tr>
		</table>
</c:if>
<c:if test="${!empty receivingQcViewRelationBeanCollection}">
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
<c:set var="showUpdateLink" value='N'/>

var lineMap = new Array();
var lineMap3 = new Array();

function reverseReceipt(rowid) {
	var receiptId = cellValue(rowid,"receiptId");
    if (receiptId.trim().length > 0) {
		loc = "/tcmIS/hub/showreversereceipt.do?receiptId=";
		loc = loc + receiptId;
		
		try {
			children[children.length] = openWinGeneric(loc,
					"Reverse_Receiving", "300", "150", "no")
		} catch (ex) {
			openWinGeneric(loc, "Reverse_Receiving", "300", "150", "no")
		}
		return true;
	}
	return false;
}


<c:set var="gridind" value="0"/>

<c:forEach var="bean" items="${receivingQcViewRelationBeanCollection}" varStatus="status">
	
	<c:set var="colorIndex" value="${colorIndex+1}"/>
	
	<bean:size collection="${bean.kitCollection}" id="resultSize"/>
	<c:if test="${resultSize > 0}">
	    lineMap[${gridind}]  = ${resultSize};
	    <c:forEach var="bean2" items="${bean.kitCollection}" varStatus="status2">
			lineMap3[${gridind}] = ${colorIndex%2} ;
			<c:set var="gridind" value="${gridind+1}"/> 
	    </c:forEach>
	</c:if>
	<c:if test="${resultSize == 0}">
		lineMap[${gridind}]  = 1;
		lineMap3[${gridind}] = ${colorIndex%2};
		<c:set var="gridind" value="${gridind+1}"/>	
	</c:if>

</c:forEach>

/*Storing the data to be displayed in a JSON object array.*/
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${receivingQcViewRelationBeanCollection}" varStatus="status">
	     	     
	     <c:set var="kitCollection"  value='${bean.kitCollection}'/>
	     <bean:size id="listSize" name="kitCollection"/>
		 <c:set var="mvItem" value='${bean.mvItem}'/>
		 <c:set var="manageKitsAsSingleUnit" value='${bean.manageKitsAsSingleUnit}'/>
		 <c:set var="docType" value='${bean.docType}'/>
		
				  		 
		 <c:set var="inventoryGroupPermission" value=''/>
		  <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${status.current.inventoryGroup}">
		   <c:set var="inventoryGroupPermission" value='Yes'/>
		  </tcmis:inventoryGroupPermission>
		
		  
		   <c:set var="checkBoxPermission" value='N'/>
			     <tcmis:inventoryGroupPermission indicator="true" userGroupId="transferReconciliation" inventoryGroup="${status.current.inventoryGroup}">
				   <c:set var="checkBoxPermission" value='Y'/>
			</tcmis:inventoryGroupPermission> 
			
								  
				 
	    <c:choose>
		<c:when test="${status.index % 2 == 0}" >
		  <c:set var="colorClass" value='grid_white'/>
		</c:when>
		<c:otherwise>
		   <c:set var="colorClass" value='grid_lightblue'/>
		</c:otherwise>
		</c:choose>
		  <c:set var="critical" value='${status.current.critical}'/>
		  <c:if test="${critical == 'Y' || critical == 'y'}">
		   <c:set var="colorClass" value='grid_red'/>
		  </c:if>
		
		  <c:if test="${critical == 'S' || critical == 's'}">
		   <c:set var="colorClass" value='grid_pink'/>
		  </c:if>
		
		  <c:set var="excess" value='${status.current.excess}'/>
		  <c:if test="${excess == 'YES' || excess == 'Yes'}">
		   <c:set var="colorClass" value='grid_orange'/>
		  </c:if>
				  
		  <c:set var="updateStatus" value='${status.current.updateStatus}'/>
		  <c:if test="${updateStatus == 'NO' || updateStatus == 'Error'}">
		   <c:set var="colorClass" value='grid_error'/>
		  </c:if>
                   
          <c:choose>
		   <c:when test="${docType == 'IT'}" >
		     <c:set var="radianPoDisplay"  value="TR ${bean.transferRequestId}"/>
		   </c:when>
		   <c:when test="${docType == 'IA'}" >
		     <c:set var="radianPoDisplay" value="${bean.returnPrNumber}-${bean.returnLineItem}"/>
		   </c:when>
		   <c:otherwise>
		     <c:set var="radianPoDisplay" value="${bean.radianPo}"/>
		   </c:otherwise>
		  </c:choose>
		 		  
		  <c:choose>
		   <c:when test="${docType == 'IT' || docType == 'IA'}">
		    <c:set var="poLineDisplay" value=""/>
		   </c:when>
		    <c:otherwise>
		     <c:set var="poLineDisplay" value="${bean.poLine}"/>
		   </c:otherwise>
		  </c:choose>
	  <bean:size collection="${bean.kitCollection}" id="resultSize"/>
	     <c:if test="${resultSize > 0}">

		<c:forEach var="kbean" items="${bean.kitCollection}" varStatus="kitstatus">
		 <c:set var="kitUpdateStatus" value='${kbean.updateStatus}'/>
		   <c:if test="${kitUpdateStatus == 'NO' || kitUpdateStatus == 'Error'}">
             <c:set var="colorClass" value='error'/>
           </c:if>
         
	      
          <c:if test="${status.current.ok != null}" >
	          <c:set var="checkBoxChecked" value='true'/>
	       </c:if>
	       
	       
             <c:if test="${listSize > 1 && manageKitsAsSingleUnit == 'N'}">
              <c:set var="itemDesc" value='${kbean.materialDesc}'/>
            </c:if>
            
            <c:if test="${manageKitsAsSingleUnit != 'N'}">
              <c:set var="itemDesc" value='${bean.lineDesc}'/>
            </c:if>
            
          
        	         
          <fmt:formatDate var="formattedEndDate" value="${bean.endDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfReceipt" value="${bean.dateOfReceipt}" pattern="${dateFormatPattern}"/>
         
	        
	         <c:set var="mfgLot"  value="${kbean.mfgLot}" />
	         <c:set var="lotStatus"  value="${bean.lotStatus}" />
	         <c:set var="bin"  value="${bean.bin}" />
	         
	         <c:if test="${status.current.closePoLine != null}" >
              <c:set var="checkCloseBoxChecked" value='true'/>
              </c:if>
	          
		 
          <c:choose> 
            <c:when test="${mvItem != 'Y'}">
                <c:set var="quantityReceived"  value="${kbean.quantityReceived}" />
                <c:set var="labelQuantity"  value="${kbean.quantityReceived}" />
                <c:set var="packagingQuantity"  value="" />
	        </c:when>
	        <c:otherwise>
		       <c:set var="quantityReceived"  value="${bean.totalMvQuantityReceived}" /> 
		       <c:set var="labelQuantity"  value="${kbean.labelQuantity}" />
		       <c:set var="packagingQuantity"  value="${kbean.quantityReceived} X ${kbean.costFactor} ${kbean.purchasingUnitOfMeasure} ${kbean.displayPkgStyle}" />
		    </c:otherwise>
		  </c:choose>
         
            
		<c:if test="${dataCount > 0}">,</c:if>
		<c:set var="dataCount" value='${dataCount+1}'/>
		
	     
	      <c:set var="reverseReceiptDisplay">
		    <input  class="smallBtns" id="reverseButton${dataCount}" type="button"  value="<fmt:message key="label.reverse"/>"  onClick="return reverseReceipt(${dataCount})" />
	      </c:set>
	     
	 
          		       
        
		{ id:${dataCount},'class':"${colorClass}",
		 data:[
		  '${radianPoDisplay}',
		  '${poLineDisplay}',
		  '${bean.inventoryGroupName}',
		  '<tcmis:jsReplace value="${itemDesc}" processMultiLines="true"/>',
	      <c:if test="${receivingQcPermission == 'Yes'}">
	       <c:choose>
           <c:when test="${(docType == 'IT' && kbean.qcOk == 'N' && checkBoxPermission == 'Y')}" >
            'Y',
           </c:when>
          <c:when test="${!empty kbean.origMfgLot && !(kbean.mfgLot == kbean.origMfgLot)}" >
           'N',
          </c:when>
          <c:otherwise>
            'Y',
          </c:otherwise>
		 </c:choose>  
          '',
          '<tcmis:jsReplace value="${mfgLot}" />',
          '${bean.origMfgLot}',
          '${formattedEndDate}', 
          '${formattedDateOfReceipt}', 
          '<tcmis:jsReplace value="${bin}"/>',
		  '${bean.receiptId}',
		  '${bean.transferReceiptId}',
          '${quantityReceived}',
		  '<tcmis:jsReplace value="${kbean.packaging}" processMultiLines="true"/>',
		   <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             'Y',
           </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
		 '<tcmis:jsReplace value="${bean.notes}" processMultiLines="true"/>',
		 <c:choose>
            <c:when test="${inventoryGroupPermission == 'Yes'}">
             'Y',
           </c:when>
            <c:otherwise>
             'N', 
            </c:otherwise>
           </c:choose>
		 '${bean.deliveryTicket}',
		 '${reverseReceiptDisplay}',
		  </c:if>
		 '${bean.radianPo}',
		 '${bean.itemId}',
		 '${bean.branchPlant}',
		 '${bean.inventoryGroup}',
		 '${bean.qcOk}',
		 '${bean.manageKitsAsSingleUnit}',
		 '${bean.mvItem}',
		 '${bean.docType}'
		  ]
		}
		</c:forEach>
    </c:if>  
 </c:forEach>
]};
//-->
</script>
		
</c:if>				

  <!-- Hidden element start --> 
   <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="sourceHubName" id="sourceHubName" value="<c:out value="${selectedHubName}"/>">
   <input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>">
   <input type="hidden" name="paperSize" id="paperSize" value="31">
   <input type="hidden" name="userAction" id="userAction" value="">
   <input type="hidden" name="category" id="category" value="${param.category}"/>
   <input type="hidden" name="searchWhat" id="searchWhat" value="${param.searchWhat}"/>
   <input type="hidden" name="search" id="search" value="${param.search}"/>
   <input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
   <input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
   <input name="minHeight" id="minHeight" type="hidden" value="210"/>
   <input type="hidden" name="submitPrint" id="submitPrint" value="">
   <input type="hidden" name="submitDocumentLabelsPrint" id="submitDocumentLabelsPrint" value="">
   <input type="hidden" name="submitReceiptLabelsPrint" id="submitReceiptLabelsPrint" value="">
   <input type="hidden" name="submitReceive" id="submitReceive" value="">
   <input name='tomorrow' id='tomorrow' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'/>
   <input name='day60' id='day60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-59" datePattern="${dateFormatPattern}"/>'  />

    

</div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>