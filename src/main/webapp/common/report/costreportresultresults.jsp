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
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/resultiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>	
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script src="/js/common/report/costreportresultresults.js" language="JavaScript"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<title>
<fmt:message key="label.costreport"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/costreportresultresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links. Set the variable you use in javascript to true.-->
 <script type="text/javascript">
 <!--
  showUpdateLinks = false;
 //-->
 </script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
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

<c:if test="${costReportViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
	 <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
		 <c:set var="colorClass" value=''/>
		 <c:set var="dataCount" value='${0}'/>
		 <c:set var="voucherUrlIndex" value='${-1}'/>
		 <c:set var="voucherUrl" value=''/>
		 <c:set var="whereClauseIndex" value='${-1}'/>
		 <c:set var="whereClauseData" value=''/>
		 <c:set var="totalAddChargeIndex" value='${-1}'/>
		 <c:set var="totalSalesTaxIndex" value='${-1}'/>
		 <%-- for right alligment --%>
		 <c:set var="qtyIndex" value='${-1}'/>
		 <c:set var="invoiceUnitPriceIndex" value='${-1}'/>
		 <c:set var="unitRebateIndex" value='${-1}'/>
		 <c:set var="totalFreightChargeIndex" value='${-1}'/>
		 <c:set var="serviceFeeIndex" value='${-1}'/>
		 <c:set var="peiAmountIndex" value='${-1}'/>
		 <c:set var="netAmountIndex" value='${-1}'/>
		 <c:set var="materialSavingIndex" value='${-1}'/>
		 <%-- date format --%>
		 <c:set var="invoiceDateIndex" value='${-1}'/>
		 <c:set var="invoicePeriodStartDateIndex" value='${-1}'/>
		 <c:set var="invoicePeriodEndDateIndex" value='${-1}'/>

		 <%-- first try to find out all of the indexes need link
		      i.e. total additional charge, total_sales_tax
		 --%>
		 <c:forEach var="bean" items="${sqlFields}" varStatus="status2">
			<c:if test="${status2.current == 'percent_split_charge'}">
				<c:set var="percentSplitChargeIndex" value='${status2.index}'/>
			</c:if>
			<c:if test="${status2.current == 'quantity'}">
				<c:set var="quantityIndex" value='${status2.index}'/>
			</c:if>
			<c:if test="${status2.current == 'invoice_unit_price'}">
				<c:set var="invoiceUnitPriceIndex" value='${status2.index}'/>
			</c:if>
			<c:if test="${status2.current == 'unit_rebate'}">
				<c:set var="unitRebateIndex" value='${status2.index}'/>
			</c:if>
			<c:if test="${status2.current == 'total_freight_charge'}">
				<c:set var="totalFreightChargeIndex" value='${status2.index}'/>
			</c:if>
			 <%-- add 1 to index because whereClauseForLink was added --%>
			 <c:if test="${status2.current == 'total_add_charge'}">
				<c:set var="totalAddChargeIndex" value='${status2.index+1}'/>
			</c:if>
			<c:if test="${status2.current == 'total_sales_tax'}">
				<c:set var="totalSalesTaxIndex" value='${status2.index+1}'/>
			</c:if>
			<c:choose>
				<c:when test="${totalAddChargeIndex > -1 || totalSalesTaxIndex > -1}">
					<c:if test="${status2.current == 'service_fee'}">
						<c:set var="serviceFeeIndex" value='${status2.index+1}'/>
					</c:if>
					<c:if test="${status2.current == 'pei_amount'}">
						<c:set var="peiAmountIndex" value='${status2.index+1}'/>
					</c:if>
					<c:if test="${status2.current == 'net_amount'}">
						<c:set var="netAmountIndex" value='${status2.index+1}'/>
					</c:if>
					<c:if test="${status2.current == 'total_rebate'}">
						<c:set var="materialSavingIndex" value='${status2.index+1}'/>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${status2.current == 'service_fee'}">
						<c:set var="serviceFeeIndex" value='${status2.index}'/>
					</c:if>
					<c:if test="${status2.current == 'pei_amount'}">
						<c:set var="peiAmountIndex" value='${status2.index}'/>
					</c:if>
					<c:if test="${status2.current == 'net_amount'}">
						<c:set var="netAmountIndex" value='${status2.index}'/>
					</c:if>
					<c:if test="${status2.current == 'total_rebate'}">
						<c:set var="materialSavingIndex" value='${status2.index}'/>
					</c:if>
				</c:otherwise>
			</c:choose>
			<%-- date format --%>
			<c:if test="${status2.current == 'invoice_date'}">
				<c:set var="invoiceDateIndex" value='${status2.index}'/>
			</c:if>
			<c:if test="${status2.current == 'start_date'}">
				<c:set var="invoicePeriodStartDateIndex" value='${status2.index}'/>
			</c:if> 
			<c:if test="${status2.current == 'end_date'}">
				<c:set var="invoicePeriodEndDateIndex" value='${status2.index}'/>
			</c:if>
		 </c:forEach>

		 <%-- column headers  --%>
		 <c:forEach var="costReportViewBean" items="${costReportViewBeanCollection}" varStatus="status">
			 <c:if test="${status.index % 10 == 0}">
				<!-- Need to print the header every 10 rows-->
				<tr>
					<c:forEach var="bean" items="${reportFields}" varStatus="status2">
						<c:choose>
							<c:when test="${status2.current == 'voucherUrl' || status2.current == 'whereClauseForLink'}">
								<c:if test="${status2.current == 'voucherUrl'}">
									<c:set var="voucherUrlIndex" value='${status2.index}'/>
								</c:if>
								<c:if test="${status2.current == 'whereClauseForLink'}">
									<c:set var="whereClauseIndex" value='${status2.index}'/>
								</c:if>
							</c:when>
							<c:otherwise>
								<th width="5%">${status2.current}</th>
							</c:otherwise>
						</c:choose>
					</c:forEach>
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

			 <%-- data rows --%>
			 <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
			 	<c:forEach var="dataBean" items="${status.current}" varStatus="status2">
					<c:choose>
						<c:when test="${status2.index == voucherUrlIndex || status2.index == whereClauseIndex}">
							<c:if test="${status2.index == voucherUrlIndex}">
								<c:set var="voucherUrl" value='${status2.current}'/>
							</c:if>
							<c:if test="${status2.index == whereClauseIndex}">
								<c:set var="whereClauseData" value='${status2.current}'/>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${!empty voucherUrl}" >
									<td class="optionTitleRight" width="5%"><a href="javascript:openWinGeneric('<c:out value="${voucherUrl}"/>','<c:out value="${status2.current}"/>','800','600','yes')"><c:out value="${status2.current}"/></a></td>
									<c:set var="voucherUrl" value=''/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${status2.index == totalAddChargeIndex}">
											<fmt:formatNumber var="formatTotalAddCharge" maxFractionDigits="6" minFractionDigits="6"><c:out value="${status2.current}"/></fmt:formatNumber>
											<td class="optionTitleRight" width="5%"><a href="javascript:showAdditionalCharge('<c:out value="${status.index}"/>')" style=\"color:#0000ff\"><c:out value="${formatTotalAddCharge}"/></a></td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${status2.index == totalSalesTaxIndex}">
													<fmt:formatNumber var="formatValue" maxFractionDigits="6" minFractionDigits="6"><c:out value="${status2.current}"/></fmt:formatNumber>
													<td class="optionTitleRight" width="5%"><a href="javascript:showSalesTax('<c:out value="${status.index}"/>')" style=\"color:#0000ff\"><c:out value="${formatValue}"/></a></td>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${status2.index == quantityIndex || status2.index == invoiceUnitPriceIndex || status2.index == unitRebateIndex ||
														                status2.index == totalFreightChargeIndex || status2.index == serviceFeeIndex || status2.index == peiAmountIndex ||
														                status2.index == netAmountIndex || status2.index == materialSavingIndex || status2.index == percentSplitChargeIndex}">
															<c:choose>
																<c:when test="${status2.index == quantityIndex}">
																	<td class="optionTitleRight" width="5%">${status2.current}</td>
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber var="formatValue" maxFractionDigits="6" minFractionDigits="6"><c:out value="${status2.current}"/></fmt:formatNumber>
																	<td class="optionTitleRight" width="5%">${formatValue}</td>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${status2.index == invoiceDateIndex || status2.index == invoicePeriodStartDateIndex || status2.index == invoicePeriodEndDateIndex}">
																	<fmt:formatDate var="formatInvoiceDate" value="${status2.current}" pattern="${dateFormatPattern}"/>
																	<td width="5%">${formatInvoiceDate}</td>
																</c:when>
																<c:otherwise>
																	<td width="5%">${status2.current}</td>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				 </c:forEach>
				 <input type="hidden" name="whereClauseForLink<c:out value="${status.index}"/>" id="whereClauseForLink<c:out value="${status.index}"/>" value="<c:out value="${whereClauseData}"/>" >
			 </tr>
			<c:set var="dataCount" value='${dataCount+1}'/>
		</c:forEach>
		<%-- for total by currency --%>
		 <c:if test="${!empty totalPerCurrency}">
			 <c:choose>
			  <c:when test="${dataCount % 2 == 0}" >
				<c:set var="colorClass" value=''/>
			  </c:when>
			  <c:otherwise>
				<c:set var="colorClass" value='alt'/>
			  </c:otherwise>
			 </c:choose>
			 <c:forEach var="bean" items="${totalPerCurrency}" varStatus="status2">
				<tr class="<c:out value="${colorClass}"/>">
					<c:set var="totalSumData" value='${status2.current}'/>
					<bean:size id="totalSumSize" name="totalSumData"/>
					<c:forEach var="columnData" items="${status2.current}" varStatus="status3">
						<c:choose>
							<c:when test="${status3.index < totalSumSize-1}">
								<c:choose>
									<c:when test="${status3.current == '&nbsp;'}">
										<%-- displaying non-total columns --%>
										<td width="5%" class="optionTitleBoldRight">${status3.current}</td>
									</c:when>
									<c:otherwise>
										<fmt:formatNumber var="formatValue" maxFractionDigits="6" minFractionDigits="6"><c:out value="${status3.current}"/></fmt:formatNumber>
										<td width="5%" class="optionTitleBoldRight">${formatValue}</td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<%-- displaying currency column --%>
								<td width="5%" class="optionTitleBoldLeft">${status3.current}</td>
							</c:otherwise>
						</c:choose>
						<c:set var="dataCount" value='${dataCount+1}'/>
					</c:forEach>
				</tr>
			</c:forEach>
		 </c:if>
	</table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty costReportViewBeanCollection}" >



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
	<input type="hidden" name="action" id="action" value=""/>
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html:html>