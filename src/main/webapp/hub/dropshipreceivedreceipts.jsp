<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script type="text/javascript" src="/js/hub/receiving.js"></script>

<title><c:choose>
	<c:when test="${empty receivedReceipts}">
		<fmt:message key="unconfirmedreceipts.title" />
	</c:when>
	<c:otherwise>
		<fmt:message key="receivedreceipts.title" />
	</c:otherwise>
</c:choose></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var showPrintInvoices = false;

function myOnload() {
	if(showPrintInvoices == true)
		$("mainUpdateLinks").style.display="";
}

function printInvoices() {

}
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload();">

<tcmis:form action="/showdropshipreceivedreceipts.do" onsubmit="return submitOnlyOnce();">

	<div id="transitPage" class="optionTitleBoldCenter" style="display: none;"><br>
	<br>
	<br>
	<fmt:message key="label.pleasewait" /> <br>
	<br>
	<br>
	<img src="/images/rel_interstitial_loading.gif" align="middle"></div>
	<div class="interface" id="mainPage"><!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
	<div class="topNavigation" id="topNavigation">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="200"><img src="/images/tcmtitlegif.gif" align="left"></td>
			<td><img src="/images/tcmistcmis32.gif" align="right"></td>
		</tr>
	</table>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="70%" class="headingl"><c:choose>
				<c:when test="${empty receivedReceipts}">
					<fmt:message key="unconfirmedreceipts.title" />
				</c:when>
				<c:otherwise>
					<fmt:message key="receivedreceipts.title" />
				</c:otherwise>
			</c:choose></td>
			<td width="30%" class="headingr"></td>
		</tr>
	</table>
	</div>

	<div class="contentArea"><!-- Search Option Begins -->
	<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
			<div class="roundcont filterContainer">
			<div class="roundright">
			<div class="roundtop">
			<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
			</div>
			<div class="roundContent"><!-- Insert all the search option within this div --> <input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>"> <input type="hidden" name="paperSize" id="paperSize"
				value="31"
			> <c:set var="printerLocation" value='${sessionScope.personnelBean.printerLocation}' />


			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
				<tr class=''>
					<c:if test="${!empty printerLocation}">
						<td width="2%" class="optionTitleRight"><input class="radioBtns" id="option1" type="radio" name="paperSizeRadio" value="31" checked></td>
						<td width="2%" class="optionTitleLeft"><fmt:message key="labels.label.size35" /></td>

						<td width="10%" class="optionTitleLeft"><html:button property="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="printContainerLabels()">
							<fmt:message key="receivedreceipts.button.containerlabels" />
						</html:button></td>

						<td width="20%" class="optionTitleLeft"><input class="radioBtns" type="checkbox" name="skipKitLabels" id="skipKitLabels" value="Yes">&nbsp;<fmt:message key="receivedreceipts.label.skipkitlabels" /></td>

                       <td width="5%" class="alignLeft">
                         <html:button property="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "printRecDocumentLabels()">
                               <fmt:message key="label.documentlabels" />                             
                         </html:button>
                       </td>
                    </c:if>

					<td width="5%"><html:button property="printthispage" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="javascript: if (window.print) window.print();">
						<fmt:message key="label.printthispage" />
					</html:button></td>

					<td width="5%" class="optionTitleLeft"><html:button property="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow()">
						<fmt:message key="label.close" />
					</html:button></td>
				</tr>
			</table>
			<!-- End search options --></div>
			<div class="roundbottom">
			<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
			</div>
			</div>
			</div>
			</td>
		</tr>
	</table>
	<!-- Search Option Ends -->

	<div class="spacerY">&nbsp;</div>

	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages"><html:errors /></div>
	<!-- Error Messages Ends --> <c:if test="${receivedReceiptsCollection != null}">
		<!-- Search results start -->
		<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
		<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
				<div class="roundcont contentContainer">
				<div class="roundright">
				<div class="roundtop">
				<div class="roundtopright"><img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
				<div class="roundContent">
				<div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%> <%--<a href="javascript:printInvoices();"><fmt:message key="label.invoiceprint"/></a>--%></div>
				<div class="dataContent">

				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
					<c:set var="colorClass" value='' />
					<c:set var="dataCount" value='${0}' />
					<c:set var="showPrintInvoices" value='N' />

					<c:forEach var="receiptDescDropshipViewBean" items="${receivedReceiptsCollection}" varStatus="status">
						<c:if test="${status.index % 10 == 0}">
							<tr>
								<th width="1%"></th>
								<th width="4%"><fmt:message key="label.po" /><br />
								<fmt:message key="label.poline" /></th>
								<th width="5%"><fmt:message key="label.supplier" /></th>
								<th width="5%"><fmt:message key="label.item" /><br />
								(<fmt:message key="label.packaging" />)</th>
								<th width="12%"><fmt:message key="label.description" /></th>
								<th width="4%"><fmt:message key="receiving.label.qtyreceived" /></th>
								<th width="4%"><fmt:message key="label.receiptid" /></th>
								<th width="4%"><fmt:message key="label.lot" /><br />
								<fmt:message key="label.bin" /></th>
								<th width="4%"><fmt:message key="label.dates" /></th>
								<th width="12%"><fmt:message key="label.notes" /></th>
								<th width="4%"><fmt:message key="receiving.label.deliveryticket" /></th>
								<th width="4%"><fmt:message key="label.qastatement" /></th>
							</tr>
						</c:if>

						<c:if test="${status.index == 0}">
							<input type="hidden" name="hubNumber" id="hubNumber" value="<c:out value="${status.current.branchPlant}"/>">
						</c:if>

						<c:choose>
							<c:when test="${status.index % 2 == 0}">
								<c:set var="colorClass" value='' />
							</c:when>
							<c:otherwise>
								<c:set var="colorClass" value='alt' />
							</c:otherwise>
						</c:choose>

						<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
							<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].prNumber" id="prNumber<c:out value="${status.index}"/>" value="<c:out value="${status.current.prNumber}"/>" />
							<input type="hidden" name="shipConfirmInputBean[<c:out value="${status.index}"/>].shipmentId" id="shipmentId<c:out value="${status.index}"/>" value="<c:out value="${status.current.shipmentId}"/>" />
							<c:set var="manageKitsAsSingleUnit" value='${status.current.manageKitsAsSingleUnit}' />
							<td width="5%"><c:choose>
								<c:when test="${fn:toUpperCase(status.current.distribution) == 'Y'}">
									<input name="shipConfirmInputBean[${status.index}].selected" id="checkbox${status.index}" type="checkbox" value="Y" />
									<c:set var="showPrintInvoices" value='Y' />
								</c:when>
								<c:otherwise>
									<input name="shipConfirmInputBean[${status.index}].selected" id="checkbox${status.index}" type="hidden" value="N" />
								</c:otherwise>
							</c:choose></td>
							<td width="5%"><c:out value="${status.current.radianPo}" /><br />
							<c:out value="${status.current.poLine}" /></td>
							<td width="7%"><c:out value="${status.current.supplierName}" /></td>
							<td width="5%"><c:out value="${status.current.itemId}" /><br />
							(<c:out value="${status.current.packaging}" />)</td>
							<td width="12%" class="optionTitleLeft"><c:out value="${status.current.lineDesc}" /></td>

							<fmt:formatDate var="formattedDom" value="${status.current.dateOfManufacture}" pattern="${dateFormatPattern}" />
							<fmt:formatDate var="formattedDos" value="${status.current.dateOfShipment}" pattern="${dateFormatPattern}" />
							<fmt:formatDate var="formattedDor" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}" />

							<fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy" />
							<c:choose>
								<c:when test='${fmtExpireDate=="01/01/3000"}'>
									<c:set var="formattedExpirationDate">
										<fmt:message key="label.indefinite" />
									</c:set>

								</c:when>
								<c:otherwise>
									<fmt:formatDate var="localeExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}" />
									<c:set var="formattedExpirationDate" value="${localeExpireDate}" />
								</c:otherwise>
							</c:choose>
							<%-- 
  <fmt:formatDate var="formattedExpirationDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy"/>
  <c:if test="${formattedExpirationDate == '01/01/3000'}" >
    <c:set var="formattedExpirationDate" value='Indefinite'/>
  </c:if> --%>

							<td width="5%"><c:out value="${status.current.quantityReceived}" /></td>
							<td width="5%"><c:out value="${status.current.receiptId}" /> <br />
							<img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>" onMouseOver="style.cursor='hand'"
								onclick="javascript:showReceiptDocuments('${status.current.receiptId}','${status.current.inventoryGroup}','${status.current.shipToCompanyId}','${status.current.shipToLocationId}')"
							></td>
							<td width="8%"><c:out value="${status.current.mfgLot}" /><br />
							<c:out value="${status.current.bin}" /></td>
							<td width="5%" nowrap class="optionTitleLeft"><fmt:message key="receivedreceipts.label.dom" />:<c:out value="${formattedDom}" /><br />
							<fmt:message key="receivedreceipts.label.dor" />:<c:out value="${formattedDor}" /><br />
							<fmt:message key="receivedreceipts.label.manufacturerdos" />:<c:out value="${formattedDos}" /><br />
							<fmt:message key="receivedreceipts.label.expdate" />:<c:out value="${formattedExpirationDate}" /><br />
							</td>
							<td width="12%" class="alignLeft">${status.current.notes}</td>
							<td class="alignLeft">${status.current.deliveryTicket}</td>
							<td class="alignLeft">${status.current.qaStatement}</td>

						</tr>
						<c:set var="dataCount" value='${dataCount+1}' />
					</c:forEach>
				</table>

				<c:if test="${showPrintInvoices == 'Y'}">
					<script language="JavaScript" type="text/javascript">
<!--
	showPrintInvoices = true;
// -->    	
</script>
				</c:if> <input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>"> <!-- If the collection is empty say no data found --> <c:if test="${empty receivedReceiptsCollection}">

					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
						<tr>
							<td width="100%"><fmt:message key="main.nodatafound" /></td>
						</tr>
					</table>
				</c:if></div>
				</div>
				<div class="roundbottom">
				<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
				</div>
				</div>
				</div>
				</td>
			</tr>
		</table>
		<!-- Search results end -->
	</c:if> <!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;"></div>
	<!-- Hidden elements end --></div>
	<!-- close of contentArea --> <!-- Footer message start -->
	<div class="messageBar">&nbsp;</div>
	<!-- Footer message end --></div>
	<!-- close of interface -->

</tcmis:form>
</body>
</html:html>