<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- Add any other javascript you need for the page here -->

<title>
	<c:set var="regionName" value=""/>
    <c:set var="inventoryGroupName" value="${param.inventoryGroup}"/>


<c:if test="${!empty beanColl}">
    <c:set var="definedShelfLifeItem" value="N"/>
    <c:forEach var="singlebean" items="${beanColl}" varStatus="status">
        <c:out value="${singlebean.definedShelfLifeItem}"/>
        <c:if test="${singlebean.definedShelfLifeItem == 'Y'}">
            <c:set var="definedShelfLifeItem" value="Y"/>
        </c:if>
    </c:forEach>    
</c:if>

<c:if test="${!empty beanColl}">
	<c:set var="regionName" value="${beanColl[0].opsRegionName}"/>
    <c:set var="inventoryGroupName" value="${beanColl[0].inventoryGroupName}"/>    
</c:if>

	<c:set var="lineItem" value=""/>
	<c:if test="${!empty param.scratchPadLineItem}">
		<c:set var="lineItem" value="-${param.scratchPadLineItem}"/>
	</c:if>
	<c:set var="orderType" value="SP"/>
    <c:if test="${ param.orderType == 'Quote'}">
		<c:set var="orderType" value="Q"/>
    </c:if>
    <c:if test="${ param.orderType == 'MR'}">
		<c:set var="orderType" value="MR"/>
    </c:if>

    ${param.curpath} &gt;
        <c:choose>
	        <c:when test="${param.searchKey == 'IG' }">
				<fmt:message key="label.showitemactivityig"/>: ${inventoryGroupName}
	        </c:when>
	        <c:when test="${param.searchKey == 'REGION' }">
				<fmt:message key="label.showitemactivityregion"/>: ${regionName}
	        </c:when>
	        <c:when test="${param.searchKey == 'GLOBAL' }">
				<fmt:message key="label.showitemactivityglobal"/>
	        </c:when>
	        <c:otherwise>
			<fmt:message key="title.showallocatableig">
				<fmt:param>
					${inventoryGroupName}
				</fmt:param>
				<fmt:param>
					${orderType}
				</fmt:param>
				<fmt:param>
				 	${param.orderPrNumber}
				</fmt:param>
				<fmt:param>
				 	${lineItem}
				</fmt:param>
			</fmt:message>
	        </c:otherwise>
        </c:choose>

<%--
            <c:if test="${!empty param.scratchPadLineItem}">
    <fmt:message key="label.showallocatable"/> ${param.searchKey} : ${param.orderPrNumber}-${param.scratchPadLineItem}
	    	</c:if>
            <c:if test="${empty param.scratchPadLineItem}">
    ${param.previousPage} &gt;
    <fmt:message key="label.showallocatable"/> ${param.searchKey} : ${param.orderPrNumber}
	    	</c:if>
--%>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
<%--TCMISPROD-2788 Changed - label.forcequalityhold to label.forceslhold--%> 
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
totallinecharges:"<fmt:message key="label.totallinecharges"/>",
alreadyselected:"<fmt:message key="label.alreadyselected"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
price:'<fmt:message key="label.price"/>',
description:'<fmt:message key="label.feedescription"/>',
confirmallocatemore:'<fmt:message key="label.confirmallocatemore"/>',
cannotallocatemore:'<fmt:message key="label.cannotallocatemore"/>',
positiveinteger:'<fmt:message key="label.positiveinteger"/>',
positivenumber:'<fmt:message key="label.positivenumber"/>',
cannotbemore:'<fmt:message key="label.cannotbemore"/>',
xxpositiveinteger:'<fmt:message key="label.xxpositiveinteger"/>',
xxnonnegativeinteger:'<fmt:message key="label.xxnonnegativeinteger"/>',
allocqty:'<fmt:message key="label.allocqty"/>',
showlegend:"<fmt:message key="label.showlegend"/>",
mustbeanumber:'<fmt:message key="label.mustbeanumber"/>',
msggg:'<fmt:message key="lebel.allocateiqsppk"/> ',
positiveinteger:'<fmt:message key="label.xxpositiveinteger"/> ',
forcequalityhold:'<fmt:message key="label.forceslhold"/> ',
alertallocatemore:'<fmt:message key="label.alertallocatemore"/> ',
alertforceallocatemore:'<fmt:message key="label.alertforceallocatemore"/> '
};

windowCloseOnEsc = true;

function closeifdone() {
	if( !showErrorMessage && '${updateComplete}' == 'updateComplete' && '${param.previousPage}' == 'AllocationAnalysis' ) {
        try {
    		opener.$("action").value = 'submitSearch';
    		opener.parent.showPleaseWait();
			opener.document.genericForm.submit();
	    }
        catch(ex){}
		window.close();
	} 
	else if( !showErrorMessage && '${updateComplete}' == 'updateComplete' && '${param.previousPage}' == 'Transfers' ) {
        try { 
    		  var now = new Date();
    		  opener.parent.document.getElementById("startSearchTime").value = now.getTime();
    		  //if(opener.parent.isValidSearchForm())
    		  {
    			opener.parent.$('uAction').value = 'search';
    			opener.parent.document.genericForm.target='resultFrame';
    			opener.parent.showPleaseWait();
    			opener.parent.document.genericForm.submit();
    		  }

	    }
        catch(ex){}
		window.close();
	} 
	else if( !showErrorMessage && '${updateComplete}' == 'updateComplete' ) {
		if( '${param.scratchPadLineItem}' == '' )
		{
		    try {
				opener.parent.allocatedone();
			} catch(ex){}
		}
		else {
               try {
			opener.savealloline($('callbackparam').value,$('totalallo').value);
/*                var orderType = opener.$v('orderType');
		        if( orderType =='Quote' )
			     opener.submitSave();
		        else if( orderType =='MR' )
			     opener.saveMR();
		        else  */
			     opener.submitSave();
	          }
              catch(ex){}
		}
		window.close();
	}
}

function validateQty() {
	
 if(commonValidate('other'))
 	 _simpleUpdate();
}


function forceBuy()
{
	
	if(commonValidate('forceBuy'))
	 	_simpleUpdate('uAction','forceBuy');
}

function commonValidate(source)
{
	  var rowsNum = haasGrid.getRowsNum();
	  if ($v("totalLines")*1 > 0 && '${param.previousPage}' == '') {

	 	var flag = false;
		  for (var p = 1; p < (rowsNum+1) ; p ++)
		  {
			  if(cellValue(p,"allocation").trim().length > 0 && cellValue(p,"allocation") != '&nbsp;') {
			  	  flag = true;
			  }
		  }

		if (flag == false) {
			alert(messagesData.validvalues+"  "+messagesData.allocqty);
			return false;
		}
	  }
	  else {
	  	  for (var p = 1; p < (rowsNum+1) ; p ++)
		  {
			  if(cellValue(p,"allocation").trim().length == 0 && cellValue(p,"originalAllocation").trim().length > 0) {
			      haasGrid.cells(p, haasGrid.getColIndexById("allocation")).setValue('0');
			  }
		  }
	  }

		if(source == 'forceBuy' && '${param.orderQuantity}' != '' ) {
			var orderquant = '${param.orderQuantity}'*1;
			var shipquant = '${param.shipped}'*1;
			var pickquant = '${param.pickList}' || 0;
			var openquant = orderquant-shipquant-pickquant;
			var total = 0;
			 for(var i=0;i<haasGrid.getRowsNum();i++){
				 var val = $v('allocation'+(i+1));//cellValue(i+1,'allocation');
				 if( val ) {
					 total += val*1;
				 }
			 }
			 if(total >= openquant)
			 {
					var msg = messagesData.alertforceallocatemore.replace(/[{]0[}]/g,total);
					alert(msg);
					return false;
			 }
			 else
				$('replenishQty').value = openquant - total;
		}

	  return true;
}

function changeValue(element) {

	if (element.value == 'No')
		element.value = 'Yes';
	else
		element.value='No';
}



<c:set var="itemType" value="${param.itemType}"/>
<c:set var="tempw" value="${param.unitOfMeasure}"/>
<c:set var="currencyId" value="${param.currencyId}"/>

function refreshUOS() {
	$('uAction').value = 'refreshUOS';
	document.genericForm.submit();
}

function loadRowSpans()
{ //return ;
 if (!haasGrid)
    return;

 for(var i=0;i<haasGrid.getRowsNum();i++){
   try
   {
     var rowSpan = lineMap[i];
//	 alert( i+":"+ lineMap[i] );
     if( rowSpan == null || rowSpan == 1 ) continue;

     haasGrid.setRowspan(i+1,1,rowSpan*1);
     haasGrid.setRowspan(i+1,2,rowSpan*1);
     haasGrid.setRowspan(i+1,3,rowSpan*1);
     haasGrid.setRowspan(i+1,4,rowSpan*1);
//     haasGrid.setRowspan(i+1,5,rowSpan*1);
// This number depends on situations...
<c:if test="${itemType != 'MV'}">
haasGrid.setRowspan(i+1,23,rowSpan*1);
haasGrid.setRowspan(i+1,24,rowSpan*1);
</c:if>
<c:if test="${itemType == 'MV'}">
	haasGrid.setRowspan(i+1,25,rowSpan*1);
	haasGrid.setRowspan(i+1,26,rowSpan*1);
</c:if>
   }
   catch (ex)
   {
     //alert("here 269");
   }
 }
 try {
	   for(var i=0;i<haasGrid.getRowsNum();i++) {
			var rowSpan = lineMap[i];
		   	if( rowSpan ) {
			   	lastm = i; lasts = rowSpan;
		   	}
		var val = cellValue(i+1,'shelfLifeRequired').trim();
		var qty = cellValue(i+1,'remainingShelfLifePercent').trim();
		var alloqty = cellValue(i+1,'allocation');

		var specMatch = false;
		if( cellValue(i+1,'specMatch') == 'Y' )
			specMatch = true
		var slfok = false

		if( qty != '' && val*1 <= qty*1 )
				slfok = true;
		<c:if test="${empty param.scratchPadLineItem}">
			alloqty = false;
		</c:if>

		if(cellValue(i+1,'source').trim() == 'Issue-MR')
			color = 'green';
	    else if( alloqty && alloqty != '&nbsp;' && specMatch && slfok )
				color = 'green';
		else if( alloqty && alloqty != '&nbsp;' )
		  		color = 'yellow';
		else if( lineMap4[i] )
				color="red";
		else
			continue;


<c:if test="${itemType == 'MV'}">
	if( i == lastm ) {
			start = 5; end = 20;
		}
	else if( i > lastm && i < lastm + lasts ) {
			start = 1; end = 21;
		}
	else {
			start = 5; end = 21; // or start = 8 end = 10 if you don't whole line.
	}
</c:if>
<c:if test="${itemType != 'MV'}">
	if( i == lastm ) {
			start = 5; end = 18;
		}
	else if( i > lastm && i < lastm + lasts ) {
			start = 1; end = 19;
		}
	else {
			start = 5; end = 19; // or start = 8 end = 10 if you don't whole line.
	}
</c:if>
 		    for(j=0;j<end;j++)
	      			haasGrid.rowsAr[ i+1 ].cells[start+j].style.background = color;
			for(j=0;j<end;j++)
            {
             if (color == 'yellow')
             {
                haasGrid.rowsAr[ i+1 ].cells[start+j].style.color="black";
             }
             else
             {
               haasGrid.rowsAr[ i+1 ].cells[start+j].style.color="white";
             }
            }
         }
 }catch(ex){}

 /*Need to call this only if the grid has rowspans > 1*/
 //haasGrid._fixAlterCss();
}

with(milonic=new menuname("allocationMenu")){
         top="offset=2"
         style = contextWideStyle;
         margin=3
         aI("text=<fmt:message key="label.showpricelistinfo"/>;url=javascript:showPriceList();");
}

with(milonic=new menuname("allocationReceiptMenu")){
    top="offset=2"
    style = contextWideStyle;
    margin=3
    aI("text=<fmt:message key="label.showpricelistinfo"/>;url=javascript:showPriceList();");
    aI("text=<fmt:message key="label.receiptspecs"/>;url=javascript:receiptSpecs();");
 	aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
}

with(milonic=new menuname("receiptDocMenu")){
    top="offset=2"
    style = contextWideStyle;
    margin=3
    aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
}

drawMenus();

var children = new Array();

function showPriceList() {
	var loc =
		  "/tcmIS/distribution/showpricelist.do?uAction=search" +
		  "&searchField=partName&searchMode=is&searchArgument=" + $v('labelSpec') +
		  "&priceGroupId="+$v('priceGroupId')+
		  "&opsEntityId="+$v('opsEntityId');
   	var winId= 'showPriceList';  //+cellValue(selectedRowId,'prNumber');

	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"900","600","yes","50","50","20","20","no");
}

function receiptSpecs() {
	var receiptId = $v("selectedReceiptId");
	var loc="/tcmIS/distribution/receiptspec.do?receiptId="+receiptId;
	children[children.length] = openWinGeneric(loc,"receiptSpecs","800","400","yes","50","50","20","20","no");
}

function showProjectDocuments(receiptId,rowNumber)
{
// var dataCount =  document.getElementById("selectedDataCount"+selectedRowId+"").value;
 var inventoryGroup = cellValue(selectedRowId,'inventoryGroup');  //$v("inventoryGroup"+dataCount+"");
 var receiptId = $v("selectedReceiptId");
 var loc = "/tcmIS/hub/receiptdocuments.do?receiptId="+receiptId+"&showDocuments=Yes&inventoryGroup="+inventoryGroup+"";
 try {
 	children[children.length] = openWinGeneric(loc,"showAllProjectDocuments","450","300","no","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showAllProjectDocuments","450","300","no","80","80");
 }
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 220);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}

function closeAllchildren()
{
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren();
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array();
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="closeifdone();popupOnLoadWithGrid(_gridConfig,68);loadRowSpans();" onunload="if($v('uAction') != 'search')try{opener.parent.closeTransitWin();}catch(ex){}try{opener.closeTransitWin();}catch(ex){}try{closeAllchildren();}catch(ex){}" onresize="resizeFrames(68)">

<tcmis:form action="/${source}.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
    showErrorMessage = false;
<c:if test="${!empty tcmISError}">
	showErrorMessage = true;
</c:if>
<c:forEach var="errorMsg" items="${tcmISError}">
	<c:if test="${!empty errorMsg}">
		showErrorMessage = true;
	</c:if>
</c:forEach>

function validateForm() {
	if( !haasGrid || !haasGrid.getRowsNum() ) return true;
	if( '${param.orderType}' == 'Quote' ) return true;
	for(var i=0;i<haasGrid.getRowsNum();i++){
			if( cellValue(i+1,'permission') != "Y" ) continue;
			var val = cellValue(i+1,'allocation').trim();
			if( val == '' || val == '&nbsp;') continue;
		    <c:if test="${empty param.scratchPadLineItem}">
		    if( !isPositiveInteger(val,false) ) {
				alert(messagesData.xxpositiveinteger.replace(/[{]0[}]/g,'<fmt:message key="label.allocqty"/>') );
				$('allocation'+(i+1)).focus();
				return false;
		    }
		    </c:if>
		    <c:if test="${!empty param.scratchPadLineItem}">
		    if( !isNonnegativeInteger(val,false) ) {
				alert(messagesData.xxnonnegativeinteger.replace(/[{]0[}]/g,'<fmt:message key="label.allocqty"/>') );
				$('allocation'+(i+1)).focus();
				return false;
			}
		    </c:if>
			var qty = cellValue(i+1,'quantity').trim();
			if( val*1 > qty*1 ) {
				alert( messagesData.cannotallocatemore.replace(/[{]0[}]/g,qty) );
				$('allocation'+(i+1)).focus();
				return false;
			}
	}

    <c:if test="${empty param.scratchPadLineItem}">
	for(var i=0;i<haasGrid.getRowsNum();i++){
		if( cellValue(i+1,'permission') != "Y" ) continue;
		var val = cellValue(i+1,'allocation').trim();
		if( val == '' || val == '&nbsp;') {
//			cell(i+1,'priceoverride').setValue('');
//			cell(i+1,'shelfLifeRequired').setValue('');
			continue;
		}
		val = cellValue(i+1,'priceoverride').trim();
		if( !isFloat(val,true) ) {
			alert(messagesData.positivenumber.replace(/[{]0[}]/g,'<fmt:message key="label.priceoverride"/>') );
			$('priceoverride'+(i+1)).focus();
			return false;
		}
		val = cellValue(i+1,'shelfLifeRequired').trim();
		if( !isSignedFloat(val,false) ) {
			alert(messagesData.mustbeanumber.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>') );
			$('shelfLifeRequired'+(i+1)).focus();
			return false;
		}
		var qty = cellValue(i+1,'remainingShelfLifePercent').trim();
//		if( qty == '' )
			continue;
		<%--if( val*1 > qty*1 ) {
			alert( messagesData.cannotbemore.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>').replace(/[{]1[}]/g,'<fmt:message key="label.rem.shelf.life"/>') );
			$('shelfLifeRequired'+(i+1)).focus();
			return false;
		}--%>
	}
    </c:if>


	if( '${param.orderQuantity}' != '' ) {
		var iq = '${param.orderQuantity}'*1;
		var total = 0;
		 for(var i=0;i<haasGrid.getRowsNum();i++){
			 var val = $v('allocation'+(i+1));//cellValue(i+1,'allocation');
			 if( val ) {
				 total += val*1;
			 }
		 }
<c:if test="${!empty param.scratchPadLineItem}"> // from scratch pad.

var shipped = "${param.shipped}";
var pickList = "${param.pickList}";

if( shipped == "" ) shipped = 0 ;
else shipped = shipped*1;

if( pickList == "" ) pickList = 0 ;
else pickList = pickList*1;

if( ((shipped + pickList) !=0 || '${param.previousPage}' == 'AllocationAnalysis') && total > (iq-shipped-pickList) )  {
//	alert( 'formula: total > (iq-shipped-pickList) total:' + total + ":orderQuantity:"+ iq +":shipped:"+shipped+":pickList:"+pickList);
	alert( messagesData.msggg + (iq-shipped-pickList) );
	return false;
}

</c:if>

<c:set var="isITVar" value="${param.scratchPadLineItem}"/>

		 if( total > iq ) {
	<c:choose>
		<c:when test="${isITVar == 0}">
		var msg = messagesData.alertallocatemore.replace(/[{]0[}]/g,total);
			alert(msg);
		</c:when>
		<c:otherwise>
		var msg = messagesData.confirmallocatemore.replace(/[{]0[}]/g,total).replace(/[{]1[}]/g,iq);
			if(!confirm(msg))
		</c:otherwise>
	</c:choose>
				return false;
			$('totalallo').value = total;
		 }
	}
	return true;
}

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
      --%>
<div id="headerline">
<c:set var="partDesc">
   <tcmis:replace value="${partDesc}" from="<BR>" to=" "/>
</c:set>
<fmt:message key="label.description"/>:
<span class="optionTitleLeft"><c:out value="${partDesc}" escapeXml="true"/></span>
<br>
<c:if test="${param.curpath ne 'Item Lookup'}">
	<fmt:message key="label.specsrequired"/>:
	<span class="optionTitleLeft"><c:out value="${param.specification}" escapeXml="true"/></span>
<br>
</c:if>
<c:if test="${!empty param.scratchPadLineItem}">
<fmt:message key="label.requiredshelflife"/>:
<span class="optionTitleLeft"><c:out value="${param.remainingShelfLifePercent}" escapeXml="true"/></span>
<br>
</c:if>
<br>

<div id="updateResultLink" style="display: none;"> <%-- mainUpdateLinks Begins --%>
<span id="mainUpdateLinks" style="display:">
<span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a> | </span>
<c:if test="${!empty beanColl && param.orderType != 'Quote' && param.searchKey == 'IG'}" >
<a href="#" onclick="validateQty();return false;"><fmt:message key="button.submit"/></a> |
</c:if>
</span>
<a href="#" onclick="$('searchKey').value='IG';_simpleUpdate(null,'search');return false;"><fmt:message key="label.inventorygroup"/></a> |
<a href="#" onclick="$('searchKey').value='REGION';_simpleUpdate(null,'search');return false;"><fmt:message key="label.region"/></a> |
<a href="#" onclick="$('searchKey').value='GLOBAL';_simpleUpdate(null,'search');return false;"><fmt:message key="label.global"/></a> |
<a href="#" onclick="window.close()"><fmt:message key="label.close"/></a>
<c:if test="${ !empty beanColl && param.orderType == 'MR'}">
:<input type="checkbox" name="forceSpecHold" value="No" id="forceSpecHold" onclick="changeValue(this);"/>
<fmt:message key="label.forcespechold"/>
<input type="checkbox" name="forceQualityHold" value="No" id="forceQualityHold" onclick="changeValue(this);"/>
<fmt:message key="label.forceslhold"/>   <%-- TCMISPROD-2788 Changed - label.forcequalityhold to label.forceslhold  --%> 
</c:if>
<c:if test="${!empty beanColl && param.orderType != 'Quote' && param.searchKey == 'IG' && (param.isSpecHold == 'X' || param.isSlHold  == 'X')}" >
| <a href="#" onclick="forceBuy();return false;"><fmt:message key="label.forcebuyorder"/></a>
</c:if>
</div>
</div>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->
<c:if test="${!empty beanColl}" >
<div id="AllocationBean" style="height:400px;display: none;" ></div>
<script type="text/javascript">
<!--

var lineMap3 = new Array();
var lineMap4 = new Array();

function selectRightclick(rowId,cellInd){
//	 haasGrid.selectRowById(rowId,null,false,false);
	 selectRow(rowId,cellInd);

//alert("	rowId" +rowId +'   source:'+cellValue(selectedRowId,'source') +"  cellInd:"+cellInd+"  stocked Index"+  haasGrid.getColIndexById("stocked"));
	 if('${param.curpath}' == 'Item Lookup' && cellValue(selectedRowId,'source').substring(0,7) == 'Receipt' && cellInd > haasGrid.getColIndexById("stocked"))
	 	toggleContextMenu("receiptDocMenu");
	 else if(cellValue(selectedRowId,'source').substring(0,7) == 'Receipt' && cellInd > haasGrid.getColIndexById("stocked"))
	 	toggleContextMenu("allocationReceiptMenu");
	 else if('${param.curpath}' != 'Item Lookup')
	 	toggleContextMenu("allocationMenu");
	 else
	 	toggleContextMenu('contextMenu');
	 	
	}

	var selectedRowId=null;
	var preClass = null;
	var preBack = null;
	var preRowId = null;

	function fixRowColor(thisrow,rowind){
		var cname = "ev_haas rowselected";
		thisrow.className = cname;
	}

	function selectRow(rowId,cellInd){

	    rowId0 = arguments[0];
	    colId0 = arguments[1];

		selectedRowId = rowId;
		var splitReference = cellValue(selectedRowId,'reference').split('-');
		$("selectedReceiptId").value = splitReference[0];

//	    haasGrid.selectRowById(rowId);
	// color style stuff

		if( preRowId != null) {
			if(	lineMap3[preRowId-1] == 1 )
				preClass="odd_haas";
			else
				preClass="ev_haas";
			mymap = lineIdMap[preRowId-1];
			for(var j = 0;j < mymap.length; j++) {
				 var rrid = mymap[j];
				 haasGrid.rowsAr[ rrid*1+1 ].className = preClass;
			}
		}

		mymap = lineIdMap[rowId0-1];
		for(var j = 0;j < mymap.length; j++) {
		    var rrid = mymap[j];
			fixRowColor( haasGrid.rowsAr[ rrid*1+1 ] );//,true,false,false);
		}

	    preRowId      = rowId0;
	//

	}


	var map = null;
	var lineMap = new Array();
	var lineIdMap = new Array();

	<c:set var="idcount" value="0"/>
	<c:set var="speccount" value="0"/>
	<c:set var="last_map" value="0"/>
	<c:set var="prespecList" value=''/>

	<c:forEach var="bean" items="${beanColl}" varStatus="status">

	<c:if test="${ bean.specMatch != 'Y' or (!empty param.remainingShelfLifePercent and !empty bean.remainingShelfLife and param.remainingShelfLifePercent > bean.remainingShelfLife ) }">
	 	lineMap4[${status.index}] = true;
	</c:if>
	 <c:set var="specList" value='${status.current.specDetailList}key'/>
	 <c:if test="${ specList != prespecList }">
	  <c:if test="${ status.index != 0 }">
	  	lineMap[${last_map}] = ${speccount};
	  </c:if>
	  <c:set var="speccount" value="0"/>
	  <c:set var="last_map" value="${status.index}"/>
	  <c:set var="idcount" value="${idcount+1}"/>
	  map = new Array();
	 </c:if>
	  <c:set var="speccount" value="${speccount+1}"/>
	  lineMap3[${status.index}] = ${(idcount+1)%2} ;
	  map[map.length] = ${status.index} ;
	  lineIdMap[${status.index}] = map;
	 <c:set var="prespecList" value='${status.current.specDetailList}key'/>
	</c:forEach>

	lineMap[${last_map}] = ${speccount};

_gridConfig.submitDefault = true;
_gridConfig.divName = 'AllocationBean';
_gridConfig.rowSpan = true;
_gridConfig.beanGrid = 'alloGrid';
_gridConfig.onRowSelect = selectRow;
_gridConfig.onRightClick = selectRightclick;
_gridConfig.evenoddmap = lineMap3;
_gridConfig.noSmartRender = true;

function priceChanged(rowId) {
<c:if test="${empty param.scratchPadLineItem}">
	val = cellValue(rowId,'priceoverride').trim();
	if( !isFloat(val,true) ) {
		alert(messagesData.positivenumber.replace(/[{]0[}]/g,'<fmt:message key="label.priceoverride"/>') );
		$('priceoverride'+rowId).focus();
		return false;
	}
	if( !val ) return true;
	var	val = cellValue(rowId,'shelfLifeRequired').trim();
	if( !isSignedFloat(val,true) ) {
		alert(messagesData.mustbeanumber.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>') );
		$('shelfLifeRequired'+rowId).focus();
		return false;
	}
	var qty = cellValue(rowId,'remainingShelfLifePercent').trim();
	if( qty == '' ) return true;
	<%--if( val*1 > qty*1 ) {
		alert( messagesData.cannotbemore.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>').replace(/[{]1[}]/g,'<fmt:message key="label.rem.shelf.life"/>') );
		$('shelfLifeRequired'+rowId).focus();
		return false;
	}--%>
</c:if>
}

function shelfLifeChanged(rowId) {
<c:if test="${empty param.scratchPadLineItem}">
	var	val = cellValue(rowId,'shelfLifeRequired').trim();
	if( !isSignedFloat(val,true) ) {
		alert(messagesData.mustbeanumber.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>') );
		$('shelfLifeRequired'+rowId).focus();
		return false;
	}
	var qty = cellValue(rowId,'remainingShelfLifePercent').trim();
	if( qty == '' ) return true;
	<%--if( val*1 > qty*1 ) {
		alert( messagesData.cannotbemore.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>').replace(/[{]1[}]/g,'<fmt:message key="label.rem.shelf.life"/>') );
		$('shelfLifeRequired'+rowId).focus();
		return false;
	}--%>
</c:if>
return true;
}

function allocationChanged(rowId,columnId,value) {
//	alert(rowId+":"+columnId+":"+value);
		var val = cellValue(rowId,'allocation').trim();
		if( val == '' || val == '&nbsp;') return false;
	    <c:if test="${empty param.scratchPadLineItem}">
	    if( !isPositiveInteger(val,false) ) {
			alert(messagesData.xxpositiveinteger.replace(/[{]0[}]/g,'<fmt:message key="label.allocqty"/>') );
			$('allocation'+rowId).focus();
			return false;
	    }
	    </c:if>
	    <c:if test="${!empty param.scratchPadLineItem}">
	    if( !isNonnegativeInteger(val,false) ) {
			alert(messagesData.xxnonnegativeinteger.replace(/[{]0[}]/g,'<fmt:message key="label.allocqty"/>') );
			$('allocation'+rowId).focus();
			return false;
		}
	    </c:if>
		var qty = cellValue(rowId,'quantity').trim();
		if( val*1 > qty*1 ) {
			alert( messagesData.cannotallocatemore.replace(/[{]0[}]/g,qty) );
			$('allocation'+rowId).focus();
			return false;
		}

<c:if test="${empty param.scratchPadLineItem}">

	var val = cellValue(rowId,'allocation').trim();
	if( val == '' || val == '&nbsp;') {
//		cell(rowId,'priceoverride').setValue('');
//		cell(rowId,'shelfLifeRequired').setValue('');
		return false;
	}
//	val = cellValue(rowId,'priceoverride').trim();
//	if( !isFloat(val,false) ) {
//		alert(messagesData.positivenumber.replace(/[{]0[}]/g,'<fmt:message key="label.priceoverride"/>') );
//		return false;
//	}
	val = cellValue(rowId,'shelfLifeRequired').trim();
	if( !isSignedFloat(val,false) ) {
		alert(messagesData.mustbeanumber.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>') );
		$('shelfLifeRequired'+rowId).focus();
		return false;
	}
	var qty = cellValue(rowId,'remainingShelfLifePercent').trim();
	if( qty == '' ) return false;
	<%--if( val*1 > qty*1 ) {
		alert( messagesData.cannotbemore.replace(/[{]0[}]/g,'<fmt:message key="label.requiredshelflife"/>').replace(/[{]1[}]/g,'<fmt:message key="label.rem.shelf.life"/>') );
		$('shelfLifeRequired'+rowId).focus();
		return false;
	}--%>
</c:if>

return false;
}
<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>

var jsonData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>

        <fmt:formatDate var="expireDate" value="${bean.expireDate}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="expireYear" value="${bean.expireDate}" pattern="yyyy"/>
    	<c:set var="expireDateD" value="${expireDate}"/>
        <c:if test="${expireYear == '3000'}">
        	<c:set var="expireDateD"><fmt:message key="label.indefinite"/></c:set>
        </c:if>
        <c:set var="remainingShelfLife" value="${bean.remainingShelfLife}"/>
        
        <c:choose>
	         <c:when test="${bean.definedShelfLifeItem == 'Y' && bean.expireDate.time >= endSearchTime-86400000}">
	          	<c:set var="remainingShelfLife" value="100"/>
	         </c:when>
	         <c:when test="${bean.definedShelfLifeItem == 'Y'}">
	          	<c:set var="remainingShelfLife" value="0"/>
	         </c:when>
        	 <c:otherwise>
        	 </c:otherwise>
        </c:choose>
        
        <c:if test="${!empty remainingShelfLife}">
			<c:set var="remainingShelfLife" value="${remainingShelfLife}%"/>
	    </c:if>
		<c:set var="shelfLifeRequired" value="${param.remainingShelfLifePercent}"/>

        <c:set var="type" value="${bean.source}"/>
		<c:set var="reference" value="${bean.reference}"/>
		<c:set var="allocation" value=""/>
        <c:if test="${!empty bean.prNumber}">
    		<c:set var="type" value="${bean.source}-${bean.needType}"/>
	    	<c:set var="reference" value="${bean.reference}-${bean.prNumber}-${bean.lineItem}"/>
    		<c:if test="${param.orderPrNumber == bean.prNumber && param.scratchPadLineItem == bean.lineItem}">
	    		<c:set var="allocation" value="${bean.quantity}"/>
    		</c:if>
    		<c:if test="${bean.source == 'Issue'}">
	    		<c:set var="allocation" value=""/>
    		</c:if>
        </c:if>

        <c:set var="priceoverride" value=""/>
        <c:if test="${empty param.scratchPadLineItem}">
	      <%--<c:set var="priceoverride" value="${bean.unitPrice}"/>--%>
          <fmt:formatNumber var="priceoverride" value="${bean.unitPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
        </c:if>

		<c:set var="p" value='N'/>
		<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${bean.inventoryGroup}">
        <c:choose>
         <c:when test="${!empty bean.prNumber && (bean.prNumber != param.orderPrNumber)}">
          <tcmis:inventoryGroupPermission indicator="true" userGroupId="stealAllocation" inventoryGroup="${bean.inventoryGroup}">
		   <c:set var="p" value='Y'/>
		  </tcmis:inventoryGroupPermission>
         </c:when>
        <c:otherwise>
          <c:set var="p" value='Y'/>
        </c:otherwise>
        </c:choose>
        </tcmis:inventoryGroupPermission>

<%-- line item and not green --%>
    <c:if test="${!empty param.scratchPadLineItem && !empty param.remainingShelfLifePercent and !empty bean.remainingShelfLife and param.remainingShelfLifePercent > bean.remainingShelfLife}">
	   <c:if test="${ !(param.orderPrNumber == bean.prNumber && param.scratchPadLineItem == bean.lineItem) }">
		    <c:set var="p" value='N'/>
	   </c:if>
    </c:if>

        <c:set var="mfgLot" value="${bean.mfgLot}"/>

		<c:if test="${bean.source == 'PO'}">
        <fmt:formatDate var="promisedDate" value="${bean.promisedDate}" pattern="${dateFormatPattern}"/>
        <c:if test="${!empty promisedDate}">
        	<c:set var="mfgLot">
        	<fmt:message key="label.due">
        		<fmt:param>
        			${promisedDate}
        		</fmt:param>
        	</fmt:message>
          	</c:set>
		</c:if>
        </c:if>

		<fmt:formatDate var="fmtDateOfReceipt" value="${bean.dateOfReceipt}" pattern="${dateFormatPattern}"/>

		<c:set var="unitPrice" value="${bean.unitPrice}"/>

        <c:if test="${!empty unitPrice}">
     <%--   <c:if test="${bean.hasPriceBreak == 'Y' && !empty unitPrice}">
	        <c:set var="unitPrice" value="${unitPrice}*"/>--%>
            <fmt:formatNumber var="unitPrice" value="${unitPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
        </c:if>

<%-- Larry note: latest change, if from scratch pad and has inv permission, always allow to allocate --%>
<c:if test="${!empty param.scratchPadLineItem}">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${bean.inventoryGroup}">
        <c:choose>
         <c:when test="${!empty bean.prNumber && (bean.prNumber != param.orderPrNumber)}">
          <tcmis:inventoryGroupPermission indicator="true" userGroupId="stealAllocation" inventoryGroup="${bean.inventoryGroup}">
		   <c:set var="p" value='Y'/>
		  </tcmis:inventoryGroupPermission>
         </c:when>
        <c:otherwise>
          <c:set var="p" value='Y'/>
        </c:otherwise>
        </c:choose>
</tcmis:inventoryGroupPermission>
</c:if>
<%-- Emd Larry note. --%>

<c:if test="${!empty bean.docType && (bean.docType == 'MR')}">
	<c:set var="p" value='N'/>
</c:if>
<c:if test="${!empty bean.source && (bean.source == 'Issue')}">
	<c:set var="p" value='N'/>
</c:if>

<c:set var="dropShipAppend" value = ''/>

<c:if test="${fn:trim(bean.poIsDropship) == 'Y'}">
	<c:set var="p" value='N'/>
	<c:set var="dropShipAppend">(<fmt:message key="label.dropship"/>)</c:set>
</c:if>

<c:if test="${param.searchKey != 'IG'}"><c:set var="p" value='N'/></c:if>

<fmt:formatNumber var="unitCostFinal" value="${bean.unitCost}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
<c:if test="${itemType == 'MV' && !empty bean.sourceAmount}">
<c:set var="costPerUos" value='${bean.unitCost /bean.sourceAmount }' />
<c:set var="basePricePerUos" value='${bean.unitPrice /bean.unitsPerItem }' />
<fmt:formatNumber var="costPerUos" value="${costPerUos}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
<fmt:formatNumber var="basePricePerUos" value="${basePricePerUos}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
</c:if>
{
 id:${status.index+1},
          data:[
 '${p}',
 '<tcmis:jsReplace value="${bean.specDetailList}" processMultiLines="true" />',
 '${bean.shelfLife}',
 '${bean.regionAvailable}',
 '${bean.globalAvailable}',
 '<tcmis:jsReplace value="${bean.opsRegionName}"/>',
 '${bean.inventoryGroup}',
 '<tcmis:jsReplace value="${bean.inventoryGroupName}"/>',
 '${bean.stocked}',
 '${type} ${dropShipAppend}',
 '${reference}',
 '${mfgLot}',
 '${bean.lotStatus}',
 '<tcmis:jsReplace value="${bean.specFullList}" processMultiLines="true" />',
 '${expireDateD}',
 <c:if test="${bean.definedShelfLifeItem == 'Y'}">'<fmt:formatDate value="${bean.sellByDate}" pattern="${dateFormatPattern}"/>',</c:if>
 <c:if test="${bean.definedShelfLifeItem == 'N'}">'',</c:if>
 '${fmtDateOfReceipt}',
 '${remainingShelfLife}',
 '${bean.quantity}',
 '${shelfLifeRequired}',
 '${allocation}',
 '${allocation}',
 '${unitCostFinal}',
<c:if test="${itemType == 'MV'}">
'${bean.unitsPerItem}',
'${costPerUos}',
</c:if>
 '${priceoverride}',
 <c:choose>
 <c:when test="${itemType == 'MV' && !empty unitPrice}">
  '${basePricePerUos}',
 </c:when>
 <c:when test="${itemType != 'MV' && !empty unitPrice}">
  '${unitPrice}',
 </c:when>
<c:otherwise>
 '',
</c:otherwise>
</c:choose>
 '${bean.hasPriceBreak}',
 '${param.promisedDate}',
 '${bean.prNumber}',
 '${bean.lineItem}',
 '${bean.docType}',
 '${bean.docNum}',
 '${bean.docLine}',
 '${bean.docQuantityPerItem}',
 '${bean.remainingShelfLife}',
 '${expireDate}',
 '${bean.specMatch}',
 '${bean.needType}',
 '${bean.meetSpec}',
 '${bean.meetShelfLife}',
 '${bean.qualityHold}'
 ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
     </c:forEach>
    ]};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
var config = [
  {
  	columnId:"permission",
  	columnName:''
  },

  {
	  	columnId:"specList",
	  	columnName:'<fmt:message key="label.spec"/>',
      tooltip:"Y"
  },
{ columnId:"shelfLife",
  columnName:'<fmt:message key="label.shelflife"/>',
  align:"center",
  tooltip:"Y",
  width:5
},
{ columnId:"regionOnHand"

<c:if test="${param.searchKey != 'REGION' && param.searchKey != 'GLOBAL' }">
  ,
  columnName:'<fmt:message key="label.region"/>',
  align:'right',
  width:4
</c:if>

},
 {
		columnId:"globalOnHand"
<c:if test="${param.searchKey != 'GLOBAL' }">
			,
		columnName:'<fmt:message key="label.global"/>',
		align:'right',
		width:4
</c:if>
	 },
{     columnId:"region"
<c:if test="${param.searchKey == 'GLOBAL' }">
	  ,
	  columnName:'<fmt:message key="label.region"/>',
	  tooltip:"Y",
      width:4
</c:if>
},
{
	columnId:"inventoryGroup"
},
{
	columnId:"inventoryGroupName"
<c:if test="${param.searchKey == 'REGION' || param.searchKey == 'GLOBAL'}">
	,
  	columnName:'<tcmis:jsReplace value="${inventorygroup}"/>',
  	tooltip:"Y"
</c:if>

},
{
	  	columnId:"stocked",
	  	columnName:'IG <fmt:message key="label.stocked"/>',
        align:"center",
	    width:4
},
{
  	columnId:"source",
  	columnName:'<fmt:message key="label.type"/>',
    width:6

},
{
  	columnId:"reference",
  	columnName:'<fmt:message key="label.reference"/>',
    tooltip:"Y",
  	width:8
},
{
  	columnId:"mfgBatch",
  	columnName:'<fmt:message key="label.mfgbatch"/>',
    tooltip:"Y"
},
{
  	columnId:"lotStatus",
  	columnName:'<fmt:message key="label.lotstatus"/>'
},
{
  	columnId:"specReference",
  	columnName:'<fmt:message key="label.spec"/>',
    tooltip:"Y"
},
{
  	columnId:"expireDateD",
  	columnName:'<fmt:message key="label.expiredate"/>',
  	width:7
},
{
  	columnId:"sellByDate"
  	<c:if test="${'Y' == definedShelfLifeItem}">
  	,
  	columnName:'<fmt:message key="label.sellbydate"/>',
  	width:7
  	</c:if>
},
{
  	columnId:"dateOfReceipt",
  	columnName:'<fmt:message key="label.dor"/>',
  	width:7
},
{
	columnId:"remainingShelfLifePercentD",
	columnName:'<fmt:message key="label.rem.shelf.life"/>',
    align:"center",
    width:3
},
{
  	columnId:"quantity",
  	columnName:'<fmt:message key="label.qty"/>',
  	align:'right',
    width:3
},
  {
		columnId:"shelfLifeRequired"
<c:if test="${empty param.scratchPadLineItem && !(param.orderType == 'Quote') && (param.curpath ne 'Item Lookup')}">
		,
		columnName:'<fmt:message key="label.requiredshelflife"/>',
		type:'hed',
	  	onChange:shelfLifeChanged,
		width:5,
      	size:2
</c:if>
},
  {
	  	columnId:"allocation"
<c:if test="${!(param.orderType == 'Quote') && (param.curpath ne 'Item Lookup')}">
		,
	  	columnName:'<fmt:message key="label.allocqty"/>',
	  	type:'hed',
	  	width:5,
	  	onChange:allocationChanged,
        size:2
</c:if>
  },
  {
	  	columnId:"originalAllocation"
  },
  {
	  	columnId:"unitCost",
	  	columnName:'<fmt:message key="label.unitcost"/> (${currencyId})',
	  	align:'right',
	  	width:4
  },
<c:if test="${itemType == 'MV'}">
{
<c:set var="uosDropDown">
UOS:  <select name="unitOfMeasure" id="unitOfMeasure" onChange="refreshUOS()">
<c:forEach var="uosbean" items="${uosColl}" varStatus="statusU">
	<c:set var="selected" value=""/>
	<c:if test="${uosbean.unitOfMeasure == tempw}">
	<c:set var="selected" value="selected"/>
	<c:set var="unitsPerItem" value="${uosbean.unitsPerItem}"/>
	</c:if>
	<option ${selected}>${uosbean.unitOfMeasure}</option>
</c:forEach>
</select>
</c:set>
	columnName:'<tcmis:jsReplace value="${uosDropDown}" processMultiLines="true" />',
//	columnName:'UOS',
  	columnId:'unitsPerItem',
  	attachHeader:'${tempw} per item',
  	align:'right',
  	width:4
},
{
  	columnId:'sourceAmount',
  	columnName:'#cspan',
  	attachHeader:'Cost per ${tempw}',
  	align:'right',
  	width:4
},
{
	columnId:"priceoverride"
<c:if test="${empty param.scratchPadLineItem && !(param.orderType == 'Quote') && (param.curpath ne 'Item Lookup')}">
	,
  	columnName:'#cspan',
	attachHeader:'Price per ${tempw}',
	type:'hed',
  	onChange:priceChanged,
	width:5,
    size:4
</c:if>
},
{
  	columnId:"unitPrice"
  	<c:if test="${empty param.scratchPadLineItem && !(param.orderType == 'Quote') && (param.curpath ne 'Item Lookup')}">
    ,columnName:'#cspan',
  	attachHeader:'Base Price per ${tempw}',
  	align:'left',
	width:4
    </c:if>
},
{
	columnId:"hasPriceBreak"
    <c:if test="${empty param.scratchPadLineItem && !(param.orderType == 'Quote') && (param.curpath ne 'Item Lookup')}">
    ,
	columnName:'Price Break',
    width:4
    </c:if>
},
</c:if>
<c:if test="${itemType != 'MV'}">
  {
		columnId:"priceoverride"
<c:if test="${empty param.scratchPadLineItem && !(param.orderType == 'Quote') && (param.curpath ne 'Item Lookup')}">
		,
		columnName:'<fmt:message key="label.priceoverride"/>',
		type:'hed',
	  	onChange:priceChanged,
		width:5,
        size:4
</c:if>
  },
  {
	  	columnId:"unitPrice"
	  	<c:if test="${param.curpath ne 'Item Lookup'}">
	  		,
	  		columnName:'<fmt:message key="label.baseunitprice"/> (${currencyId})',
	  		align:'left',
    			width:4
	    	</c:if>
  },
  {
  	columnId:"hasPriceBreak"
//  	,columnName:'Price Break',
//      width:4
},
</c:if>
//hidden variable here
  {
		columnId:"promisedDate"
},
  {
		columnId:"prNumber"
},
{
	columnId:"lineItem"
},
{
	columnId:"docType"
},
{
	columnId:"docNum"
},
{
	columnId:"docLine"
},
{
	columnId:"docQuantityPerItem"
},
{
  	columnId:"remainingShelfLifePercent"
},
{
  	columnId:"expireDate"
},
{
	columnId:"specMatch"
},
{
	columnId:"needType"
},
{
	columnId:"meetSpec"
},
{
	columnId:"meetShelfLife"
},
{
	columnId:"qualityHold"
}

];

// -->
</script>
</c:if>

<!-- If the collection is empty say no data found -->

<c:if test="${empty beanColl}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<!-- Search results end -->
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="source" id="source" value="${source}"/>  
<input type="hidden" name="selectedReceiptId" id="selectedReceiptId" value=""/>  

<!--This sets usually there every page.-->
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>
<input name="curpath" id="curpath" value="${param.curpath}" type="hidden"/>
<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
<input name="itemId" id="itemId" value="${param.itemId}" type="hidden"/>
<%-- 
<input name="refInventoryGroup" id="refInventoryGroup" value="${param.refInventoryGroup}" type="hidden"/>
--%>
<input name="incoTerms" id="incoTerms" type="hidden" value="${param.incoTerms}"/>
<input name="priceGroupId" id="priceGroupId" type="hidden" value="${param.priceGroupId}"/>
<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>
<input name="specList" id="specList" value="${param.specList}" type="hidden"/>
<input name="shipToCompanyId" id="shipToCompanyId" value="${param.shipToCompanyId}" type="hidden"/>
<input name="shipToLocationId" id="shipToLocationId" value="${param.shipToLocationId}" type="hidden"/>
<input name="billToCompanyId" id="billToCompanyId" value="${param.billToCompanyId}" type="hidden"/>
<input name="billToLocationId" id="billToLocationId" value="${param.billToLocationId}" type="hidden"/>
<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>
<input name="opsCompanyId" id="opsCompanyId" value="${param.opsCompanyId}" type="hidden"/>
<input name="remainingShelfLifePercent" id="remainingShelfLifePercent" value="${param.remainingShelfLifePercent}" type="hidden"/>
<input name="shipComplete" id="shipComplete" value="${param.shipComplete}" type="hidden"/>
<input name="consolidateShipment" id="consolidateShipment" value="${param.consolidateShipment}" type="hidden"/>
<input name="currencyId" id="currencyId" value="${param.currencyId}" type="hidden"/>
<input name="orderPrNumber" id="orderPrNumber" value="${param.orderPrNumber}" type="hidden"/>
<input name="scrap" id="scrap" value="${param.scrap}" type="hidden"/>
<input name="scratchPadLineItem" id="scratchPadLineItem" value="${param.scratchPadLineItem}" type="hidden"/>
<input name="totalallo" id="totalallo" value="${param.totalallo}" type="hidden"/>
<input name="callbackparam" id="callbackparam" value="${param.callbackparam}" type="hidden"/>
<input name="searchKey" id="searchKey" value="${param.searchKey}" type="hidden"/>
<input name="orderType" id="orderType" value="${param.orderType}" type="hidden"/>
<input name="promisedDate" id="promisedDate" value="${param.promisedDate}" type="hidden"/>
<input name="needDate" id="needDate" value="<fmt:formatDate value="${needDate}" pattern="${dateFormatPattern}"/>" type="hidden"/>    
<input name="specDetailList" id="specDetailList" value="${param.specDetailList}" type="hidden"/>
<input name="specCocList" id="specCocList" value="${param.specCocList}" type="hidden"/>
<input name="specCoaList" id="specCoaList" value="${param.specCoaList}" type="hidden"/>
<input name="specLibraryList" id="specLibraryList" value="${param.specLibraryList}" type="hidden"/>
<input name="itemType" id="itemType" value="${param.itemType}" type="hidden"/>
<input name="labelSpec" id="labelSpec" value="${param.labelSpec}" type="hidden"/>
<input name="unitsPerItem" id="unitsPerItem" value="${unitsPerItem}" type="hidden"/>
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input name="shipped" id="shipped" value="${param.shipped}" type="hidden"/>
<input name="pickList" id="pickList" value="${param.pickList}" type="hidden"/>
<input name="previousPage" id="previousPage" value="${param.previousPage}" type="hidden"/>
<input name="specification" id="specification" value="${param.specification}" type="hidden"/>
<input name="partDesc" id="partDesc" value="${partDesc}" type="hidden"/>

<input name="mrNumber" id="mrNumber" value="${param.orderPrNumber}" type="hidden"/>
<input name="mrLineItem" id="mrLineItem" value="${param.scratchPadLineItem}" type="hidden"/>
<input name="application" id="application" value="${param.application}" type="hidden"/>
<input name="catPartNo" id="catPartNo" value="${param.catPartNo}" type="hidden"/>
<input name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}" type="hidden"/>
<input name="catalogId" id="catalogId" value="${param.catalogId}" type="hidden"/>
<input name="partGroupNo" id="partGroupNo" value="${param.partGroupNo}" type="hidden"/>
<input name="replenishQty" id="replenishQty" type="hidden" value=""/> 
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" style="background-color:green;">&nbsp;</td><td class="legendText"><fmt:message key="label.remainingshelflifegreaterthanshelflifeandspecsmatch"/>
    																	<div><fmt:message key="label.quantityonpicklist"/></div></td></tr>
    <tr><td width="100px" style="background-color:yellow">&nbsp;</td><td class="legendText"><fmt:message key="label.remainingshelflifegreaterthanshelflifeorspecsmatch"/></td></tr>
    <tr><td width="100px" style="background-color:red">&nbsp;</td><td class="legendText"><fmt:message key="label.remainingshelflifenotgreaterthanshelflifeandspecsnotmatch"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
 <script type="text/javascript">
 <!--
  showUpdateLinks = <c:choose><c:when test="${param.curpath ne 'Item Lookup'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;
//-->
</script>

</body>
</html>
