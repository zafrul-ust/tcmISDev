<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon"
	href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss /> <!-- CSS for YUI -->

<link rel="stylesheet" type="text/css"
	href="/yui/build/container/assets/container.css" />

<!-- Add any other stylesheets you need for the page here --> <%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%> <script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script	type="text/javascript" src="/js/menu/mmenudom.js"></script> 
<script	type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
<script	type="text/javascript" src="/js/menu/contextmenu.js"></script> 
<!-- For Calendar support -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%> <!-- Add any other javascript you need for the page here --> <!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%> <!-- This is for the YUI, uncomment if you will use this --> <%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title><fmt:message key="label.splitreceipt" /></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
	nosplitperm:"<fmt:message key="label.nosplitperm"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};

function doneAndClose(){
	try {
		window.opener.refreshSearch();
	}
	catch(e){}
	window.close();

	// cindy test. 3
}

function doneAndClose2(){
	try {
		window.opener.refreshSearch();
	}
	catch(e){}
	// cindy test. 3
}

<c:if test="${ !empty done }">
	<c:if test="${ empty errmsg }">
		window.onunload = function(){doneAndClose2();}
	</c:if>
</c:if>

// to print labels.
function generatelabels(entered, doAllLabels,paperSize) {
    {
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_GenerateLabels','650','600','yes');
    setTimeout("generatelabelsCallback()", 1000);
    }
    return false;
}

function generatelabelsCallback() {
	    if ( !$('paper_0').checked && !$('paper_0').checked)
	    {
	     	$("paperSize").value = "receiptDetail";
	    }

		if ( $("printKitLabels").checked) {
			$("skipKitLabels").value = "Yes";
		}
		else {
			$("skipKitLabels").value = "No";
		}

		ids = $v('labelReceipts');// ids is a list of ids, like 123,456    
		$('labelReceipts').value = ids;
//		alert( ids ) ;    
	    if (ids.trim().length > 0)
	    {
			var oriTarget = document.genericForm.target;
			var oriAction = document.genericForm.action;
			document.genericForm.action = "/tcmIS/hub/printreceiptboxlabels.do?FromLogisticsNew=Y&printLabelsNow=true";
//			haasGrid.parentFormOnSubmit(); //prepare grid for data sending
			document.genericForm.target = "_GenerateLabels";
//			alert(document.genericForm.action); 
			document.genericForm.submit();		 

			document.genericForm.target = oriTarget; 
			document.genericForm.action = oriAction; 
	    }
	}
//if ( "NonConforming".equalsIgnoreCase(key) || "Customer Purchase".equalsIgnoreCase(key) || "Write Off Requested".equalsIgnoreCase(key) || "3PL Purchase".equalsIgnoreCase(key) )
<%--
var pickablestatus = new Array();

<c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
	pickablestatus['${bean.lotStatus}'] = 'Y';
</c:forEach>

delete pickablestatus['NonConforming'];
delete pickablestatus['Customer Purchase'];
delete pickablestatus['Write Off Requested'];
delete pickablestatus['3PL Purchase'];
--%>

var allowedinvgrp =  new Array();

<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="stat">
<c:forEach var="hub" items="${opsEntity.hubIgCollection}" varStatus="stat1">
	<c:if test="${hub.hub == param.hub}">
		<c:forEach var="inventoryGroup" items="${hub.inventoryGroupCollection}" varStatus="stat2">
			<tcmis:inventoryGroupPermission indicator="true" userGroupId="Inventory" inventoryGroup="${inventoryGroup.inventoryGroup}">
				allowedinvgrp['${inventoryGroup.inventoryGroup}'] = 'Y';
			</tcmis:inventoryGroupPermission>
		</c:forEach>
	</c:if>
</c:forEach>
</c:forEach>

//Larry Note: Port this function too.
// It was filtered on logistics page already.
function checkallowstatus()
{
 var lotstatus =  document.getElementById("lotStatus").value;
// var origlotstatus =  document.getElementById("origlotstatus");
 var qualitycntitem =  document.getElementById("qualityControlItem").value;
// var originvgrp =  document.getElementById("originvgrp");
 var invengrp =  document.getElementById("inventoryGroup").value;
 if (qualitycntitem.value == "Y")
 {
        var allowedinvengrp = false;
//        var pickstatus = false;
//	if( 'Y' == pickablestatus[lotstatus])
//	   pickstatus = true;
	if( 'Y' == allowedinvgrp[invengrp])
   		allowedinvgrp = true;
//	if (pickstatus == true && !allowedinvengrp)
	if ( !allowedinvengrp)
	{
		alert(messagesData.nosplitperm);
        return false;
    }
 }
return true;
}


function CheckSplitQtyValue()
{   
	if( !checkallowstatus() ) return;
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    finalMsgt = messagesData.validvalues + "\n\n";

    var QtyAvailable  =  $v('quantity');//document.getElementById("QtyAvailable").value;
    var maxsplitqty  =  $v('quantity');
    maxsplitqty.value = QtyAvailable;

    //var maxsplitqty  =  window.document.receiving.maxsplitqty.value;
    var v  =  $v('splitQuantity');
    //eval( testqty = "window.document.receiving.splitqty")
    //var v = (eval(testqty.toString())).value;
    if ( !$v('bin') ) {
//        finalMsgt = finalMsgt + "<fmt:message key="errors.selecta"/>".replace(/[{]0[}]/,"<fmt:message key="label.bin"/>") + "\n";
        finalMsgt = finalMsgt + "<fmt:message key="label.bin"/>" + "\n";
    }
    if ( !(isFloat(v)) )
    {
        finalMsgt = finalMsgt + "<fmt:message key="label.qtytosplit"/>\n";
        allClear = 1;
        $('splitQuantity').value ="";
    }
    else if ( v*1 < 0 || v*1 == 0)
    {
        finalMsgt = finalMsgt + "<fmt:message key="label.qtytosplit"/>\n";
        allClear = 1;
        $('splitQuantity').value ="";
    }
    else if ( v*1 > QtyAvailable*1 )
    {   
        finalMsgt = finalMsgt + " <fmt:message key="label.qtytosplit"/> <= <fmt:message key="label.quantityonhand"/>\n";
        allClear = 1;
        $('splitQuantity').value ="";
    }

    if (allClear >= 1)
    {
     alert(finalMsgt);
	 return;
    }
    $('uAction').value = 'split';
    document.genericForm.submit();
}

function getBin(value,text,rowid) {
	   obj = document.getElementById("bin");
	   var index = obj.length;
	   obj.options[index]=new Option(text,value);
	   obj.options[index].selected = true; 
}

function addnewBin()
{
	var loc = "/tcmIS/hub/showhubbin.do?callbackparam=&branchPlant=" + $v('hub') + "&userAction=showBins"; 
	try {
		children[children.length] = openWinGeneric(loc, "showVvHubBins", "300",
				"150", "no", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
	}
}

function checkaddbins(){
	var opts = $('bin').options;
	var ind = $('bin').selectedIndex;
	if( opts[ ind ].value == '' ) {
		var loc = "/tcmIS/hub/showhubbin.do?callbackparam=&branchPlant=" + $v('hub') + "&userAction=showBins"; 
		try {
			children[children.length] = openWinGeneric(loc, "showVvHubBins", "300",
					"150", "no", "80", "80");
			} catch (ex) {
				openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
		}
	}
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resizeFrames()" onresize="resizeFrames()">

<!--Uncomment for production-->
<tcmis:form action="/splitreceipt.do"
	onsubmit="return submitOnlyOnce();">

	<!-- Transit Page Begins -->
	<div id="transitPage" class="optionTitleBoldCenter"
		style="display: none;"><br><br><br><fmt:message
		key="label.pleasewait" /> <br><br><br><img
		src="/images/rel_interstitial_loading.gif" align="middle">
	</div>
	<!-- Transit Page Ends -->
<c:forEach var="inbean" items="${coll}" varStatus="status">
	<c:set var="bean" value='${inbean}'/>
</c:forEach>
	<div class="interface" id="mainPage"><!-- start of interface-->

	<div class="contentArea"><!-- start of contentArea--> <!-- Search Option Begins -->
	<table id="searchMaskTable" width="300" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>
			<div class="roundcont filterContainer">
			<div class="roundright">
			<div class="roundtop">
			<div class="roundtopright"><img
				src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15"
				height="10" class="corner_filter" style="display: none" /></div>
			</div>
			<div class="roundContent"><!-- Insert all the search option within this div -->
			<!-- Search Option Table Begins -->

			<table width="100%" cellpadding="0" cellspacing="0" class="tableSearch">
<c:if test="${ empty done }">			
				<tr>
					<td width="20%" class="optionTitleBoldRight" nowrap="nowrap">
						<fmt:message key="label.originalreceiptid" />:
					</td>
					<td width="80%" class="optionTitleBoldLeft">
						${bean.receiptId}
						<input type="hidden" name="receiptId" id="receiptId" value="${param.receiptId}" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.quantityonhand" />:
					</td>
					<td width="80%" class="optionTitleBoldLeft">
						${param.quantity}
						<input type="hidden" name="quantity" id="quantity" value="${param.quantity}" />
					</td>
				</tr>
				<tr>
					<td width="20%" class="optionTitleBoldRight">
						<fmt:message key="label.bin" />:
					</td>
					<td width="20%" class="optionTitleLeft">
						<select	name="bin" id="bin" class="selectBox" ">
						<c:set var="dataCount" value='${0}'/>
						<c:forEach var="bin" items="${bins}" varStatus="status2">
							<option value="${bin.bin}">${bin.bin}</option>
							<c:set var="dataCount" value='${dataCount+1}'/>
						</c:forEach>
						<c:if test="${dataCount == 0}">
							<option value=""><fmt:message key="label.none" /></option>
						</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="optionTitleBoldRight" nowrap>
						<fmt:message key="label.lotstatus" />:
					</td>
					<td>
						<select class="selectBox" name="lotStatus" id="lotStatus">
						   <c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
						    <c:if test="${bean.lotStatus ne 'Write Off Rejected' and bean.lotStatus ne 'Incoming' and bean.lotStatus ne 'Write Off Approved' and bean.lotStatus ne 'NonConforming' and bean.lotStatus ne 'Customer Purchase' and bean.lotStatus ne 'Write Off Requested' and bean.lotStatus ne '3PL Purchase'}">
						   	<c:set var="jspLabel" value=""/>
						    <c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set></c:if>
						     	<c:choose>
									<c:when test="${selectedLotStatus == bean.lotStatus}">
										<option value="<c:out value="${status.current.lotStatus}"/>" selected><fmt:message key="${jspLabel}"/></option>
									</c:when>
									<c:otherwise>
										<option value="${bean.lotStatus}"><fmt:message key="${status.current.jspLabel}"/></option>
									</c:otherwise>
								</c:choose>
							</c:if>								
						  </c:forEach>
						 </select>
					</td>
				</tr>
				<tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.inventorygroup" />:
					</td>
					<td class="optionTitleLeft">
						<select name="inventoryGroup" id="inventoryGroup" class="selectBox">
						<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="stat">
							<c:forEach var="hub" items="${opsEntity.hubIgCollection}" varStatus="stat1">
								<c:if test="${hub.hub == param.hub}">
									<c:forEach var="inventoryGroup"
										items="${hub.inventoryGroupCollection}" varStatus="stat2">
										<c:set var="selected" value='' />
										<c:if
											test="${inventoryGroup.inventoryGroup == param.inventoryGroup}">
											<c:set var="selected" value="selected='selected'" />
										</c:if>
										<option value="${inventoryGroup.inventoryGroup}"${selected}>${inventoryGroup.inventoryGroupName}</option>
									</c:forEach>
								</c:if>
							</c:forEach>
						</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td class="optionTitleBoldRight">
						<fmt:message key="label.qtytosplit" />:
					</td>
					<td class="optionTitleBoldLeft">
						<input class="inputBox"	type="text" name="splitQuantity" id="splitQuantity" value="" size="20" />
					</td>
				</tr>
<c:set var="netDisplay" value=''/>
<c:if test="${ param.netPendingAdj == '0'}">
	<c:set var="netDisplay" value='style="display: none"'/>
</c:if>
				<tr ${netDisplay}>
					<td width="20%" class="optionTitleBoldRight">
						<%-- checked="checked" The default is unchecked--%>
						<input type="checkbox" name="movePendingAdjustment" id="movePendingAdjustment" value="Y" />
						<input type="hidden" name="netPendingAdj" id="netPendingAdj" value="${param.netPendingAdj}" />
					</td>
					<td class="optionTitleBoldLeft">
						<%-- The original receipt has a pending adjustment, do you want to move the adjustment to the new receipt being created.--%>
						<fmt:message key="label.movependingadj"/> (${param.netPendingAdj})					
					</td>
				</tr>
				<td width="90%" class="optionTitleBoldRight">
					<input name="ok" type="button" class="inputBtns" value="<fmt:message key="label.ok"/>" id="ok" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "CheckSplitQtyValue();"/>
    			</td>
				<td class="optionTitleBoldRight" nowrap="nowrap" >
					<input name="addNewBin" id="addNewBin" type="button" value="<fmt:message key="receiving.button.newbin" />" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="addnewBin()"/>
					<input name="cancel" id="cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="window.close()"/>
				</td>				
</c:if>				
<c:if test="${ !empty done }">
	<c:if test="${ empty errmsg }">
			<tr>
				<td colspan="2">
					The quantity was split and the new Receipt Id is : ${receiptId}
		<input type="hidden" name="labelReceipts" id="labelReceipts" value="${receiptId}" />
		<input type="hidden" name="LogisticsBean[0].receiptId" id="LogisticsBean[0].receiptId" value="${receiptId}" />
		<input type="hidden" name="LogisticsBean[0].quantityReceived" id="LogisticsBean[0].quantityReceived" value="${param.splitQuantity}" />
		<input type="hidden" name="LogisticsBean[0].ok" id="LogisticsBean[0].ok" value="Y" />
				</td>
			</tr>
			<tr>
	<td  nowrap="nowrap">
		<input type="radio" name="paper" id="paper_0" onclick="$('paperSize').value='31';" value="31" checked/>3"/1" &nbsp;
		<input type="radio" name="paper" id="paper_1" onclick="$('paperSize').value='038';" value="038" />4-3/16" / 3/8" &nbsp;
		<input type="hidden" name="paperSize" id="paperSize" value="31" />
	</td>
	<td  nowrap="nowrap">
		<input type="checkbox" name="printKitLabels" id="printKitLabels" value="Yes" />
		<fmt:message key="label.skipkitcaseqtylabels"/>
		<input type="hidden" name="skipKitLabels" id="skipKitLabels" value="" />
	</td>
		</tr>    			
<tr>
<td>
			<input name="ok"  id="ok" type="button" class="inputBtns" value="<fmt:message key="receivedreceipts.button.containerlabels"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="generatelabels();"/>
</td>
				<td class="optionTitleBoldLeft">
					<input name="nouse"  id="nouse" type="button" class="inputBtns" value="<fmt:message key="label.close"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="doneAndClose();"/>
    			</td>
</tr>			
	</c:if>
</c:if>
			</table>

			<!-- Search Option Table Ends --></div>
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
	<!-- Search Option Ends -->

	<div class="spacerY">&nbsp;</div>

	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages"><html:errors />
	</div>
	<!-- Error Messages Ends --> <!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;">
	<input type="hidden" name="uAction" id="uAction" value="search" />
	<input type="hidden" name="hub" id="hub" value="${param.hub}" />
	<input type="hidden" name="qualityControlItem" id="qualityControlItem" value="${param.qualityControlItem}" />
	<input type="hidden" name="oriLotStatus" id="oriLotStatus" value="${param.lotStatus}" />
 
	</div>
	<!-- Hidden elements end --></div>
	<!-- close of contentArea --> <!-- Footer message start -->
	<div class="messageBar">&nbsp;</div>
	<!-- Footer message end --></div>
	<!-- close of interface -->
</tcmis:form>
</body>
</html:html>