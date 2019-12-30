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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/pagename.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.purchaseorder"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<%--<tcmis:form action="/pagename.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">--%>

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
		<td width="100%" colspan="6" id="messagerow">
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.supplier"/>:
		</td>
		<td width="26%" class="optionTitleLeft">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.hub"/>:
		</td>
		<td width="26%">
			<input type="hidden" name="HubFullName" id="HubFullName" value=""/>
			<select class="selectBox" name="HubName" id="HubName" size="1" disabled="disabled">
				<option value="None">
					<fmt:message key="label.pleaseselect"/>
				</option>
			</select>
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.po"/>:
		</td>
		<td width="20%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="po" id="po" value="600150" size="8">
			<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchPoLike" value="..." >
			</button>
			<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" name="newPO" value="newPO"/>
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<input class="inputBox" TYPE="text" name="supplierId" id="supplierid" value="" size="4"/>
			<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchSupplierLike" value="..." />
		</td>
		<td width="26%" class="optionTitleLeft">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.tradeTerms"/>:
		</td>
		<td width="26%">
			<select class="selectBox" name="fob" id="fob" size="1">
				<option value="None">
					<fmt:message key="label.pleaseselect"/>
				</option>
				<!--  query values -->
			</select>
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.bo"/>:
		</td>
		<td width="20%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="bpo" id="bpo" value="" size="8"/>
			<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchBpoLike" value="..." />
			<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" name="newBpo" value="new"/>
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<input class="inputBox" type="text" name="suppRating" id="suppRating" value="" size="8" readonly="readonly"/>
		</td>
		<td width="26%" class="optionTitleLeft" id="supplierline3">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.shipping"/>:
		</td>
		<td width="26%" id="carrierline1" class="optionTitleLeft">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.buyer"/>:
		</td>
		<td width="20%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="buyer" id="buyer" value="" size="12" readonly="readonly">
			<input type="hidden" name="buyerid" id="buyerid" value="">
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
		</td>
		<td width="26%" id="supplierline4" class="optionTitleLeft">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<input type="hidden" name="carrieraccount" id="carrieraccount" value="">
			<input class="inputBox" type="text" name="carrierID" id="carrierID" value="" size="4">
			<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchCarrierLike" value="..." />
		</td>
		<td width="26%" id="carrierline2" class="optionTitleLeft">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.order.taker"/>:
		</td>
		<td width="20%" class="optionTitleLeft">
			<input type="hidden" name="ordertakerID" id="ordertakerID" value="">
			<input class="inputBox" type="text" name="ordertaker" id="ordertaker" value="" size="8">
			<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchOrderTakeLike" value="..." />
		</td>
	</tr>
	<tr>
		<td colspan="2" class="optionTitleBoldRight">
			<fmt:message key="label.phone.number"/>:
		</td>
		<td class="optionTitleLeft">
			<select class="selectBox" name="criticalpo" id="criticalpo" size="1">
				<!-- query values -->
				<OPTION VALUE="N">N</OPTION>
				<OPTION VALUE="Y">Y</OPTION>
				<OPTION VALUE="S">S</OPTION>
			</select>
			<fmt:message key="label.critical"/>
		</td>
		<td colspan="3" class="optionTitleBoldRight">
			<fmt:message key="label.phone.number"/>:
			<input class="inputBox" type="text" name="contactPhoneNo" id="contactPhoneNo" value="" size="15" readonly="readonly">
			<fmt:message key="label.fax"/>:
			<input class="inputBox" type="text" name="contactFaxNo" id="contactFaxNo" value="" size="15" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.email"/>:
		</td>
		<td width="26%" class="optionTitleLeft" id="supplieremail">
		</td>
		<td width="3%" class="optionTitleBoldRight">
		</td>
		<td width="26%" class="optionTitleLeft"/>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.email"/>:
		</td>
		<td width="20%"/>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.shipto"/>:
		</td>
		<td width="26%"/>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.dateconfirmed"/>:
		</td>
		<td width="26%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="confirmDate" id="confirmDate" value="" size="8" readonly="readonlye">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.customerpo"/>:
		</td>
		<td width="20%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="customerPo" id="customerPo" value="" size="20" maxLength="30">
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<input type="hidden" name="shipToCompanyId" id="shipToCompanyId" value="">
			<input class="inputBox" type="text" name="shipToId" id="shipToId" value="" size="4">
			<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchShipToLike" value="..." />
		</td>
		<td width="26%" id="shiptoline2" class="optionTitleLeft">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.dateaccepted"/>:
		</td>
		<td width="26%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="acceptDate" id="acceptDate" value="" size="8" readonly="readonly">
		</td>
		<td width="3%" class="optionTitleBoldRight">
		</td>
		<td width="20%" class="optionTitleLeft">
			<input type="button" style="width:100px;" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.scratchPad"/>" name="poNotes"> 
			<input type="button" style="width:80px;" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.emailpad"/>" name="poNotes">
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.invenGroup"/>:
		</td>
		<td width="26%" class="optionTitleLeft" id="shiptoline3"></td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.boStartDate"/>:
		</td>
		<td width="26%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="boStartDate" id="boStartDate" value="" size="8" readonly="readonly">
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.paymentterms"/>:
		</td>
		<td width="20%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="paymentTerms" id="paymentTerms" value="" size="10" readonly="readonly">
		</td>
	</tr>
	<tr>
		<td width="3%" class="optionTitleBoldRight">
			<input class="inputBox" type="text" name="invenGrp" id="invenGrp" value="" size="10" readonly="readonly">
		</td>
		<td width="26%" id="shiptoline4" class="optionTitleLeft"></td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.boEndDate"/>:
		</td>
		<td width="26%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="boEndDate" id="boEndDate" value="" size="8" readonly="readonly"/>
		</td>
		<td width="3%" class="optionTitleRight">
			<input class="radioBtns" type="checkbox" value="Yes" name="consignedPo" id="consignedPo">
		</td>
		<td width="20%" class="optionTitleBoldLeft">
			<fmt:message key="label.consigned"/> <fmt:message key="label.po"/>
		</td>
	</tr>
	<tr>
		<td width="3%"/>
		<td width="26%" id="shiptoline5" />
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.currency"/>:
		</td>
		<td width="26%" class="optionTitleLeft">
			<select class="selectBox" name="currency" id="currency" size="1">
				<option value="None">
					<fmt:message key="label.pleaseselect"/>
				</option>
				<!-- query value -->
			</select>
		</td>
		<td width="3%" class="optionTitleBoldRight">
			<fmt:message key="label.total"/> <fmt:message key="label.amount"/>:
		</td>
		<td width="20%" class="optionTitleLeft">
			<input class="inputBox" type="text" name="totalAmount" id="totalAmount" value="" size="10" readonly="readonly"/>
		</td>
	</tr>
	<tr>
		<td width="10%" colspan="6">
			<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addMaterial"/>" name="addLineButton">
			<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addCharge"/>" name="addChargeButton">
			<input type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="" name="nothing">
			<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.removeLine"/>" name="remove">
			<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.undoRemove"/>" name="unremove">
			<input type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.save"/>" name="save">
			<input type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.confirm"/>" name="confirm">
			<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.print"/>" name="confirm">
		</td>
	</tr>
</table>
    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>