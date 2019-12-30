<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<script type="text/javascript" src="/js/common/report/costreportresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="label.costreport"/>
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

<body bgcolor="#ffffff" onload="myResultOnload();">

<tcmis:form action="/costreportresult.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">

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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

      <tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.requestor"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.requestorName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.searchby"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.searchByName}"/> <c:out value="${param.searchType}"/> <c:out value="${param.searchText}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.companyName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoicenr"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.searchByInvoiceNumber}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.reportinggroup"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.reportingGroup}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoiceperiod"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.searchByInvoicePeriod}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td class="optionTitleLeft"><c:out value="${param.facilityName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoicedbetween"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.invoiceDateBegin}"/> <fmt:message key="label.and"/> <c:out value="${param.invoiceDateEnd}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.workarea"/>:</td>
        <td class="optionTitleLeft"><c:out value="${param.applicationName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.deliveredbetween"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${param.dateDeliveredBegin}"/> <fmt:message key="label.and"/> <c:out value="${param.dateDeliveredEnd}"/></td>	
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.accountingsystem"/>:</td>
        <td class="optionTitleLeft"><c:out value="${param.accountSysName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.itemtype"/>:</td>
        <td class="optionTitleLeft"><c:out value="${param.itemTypeName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoicetype"/>:</td>
        <td class="optionTitleLeft"><c:out value="${param.invoiceTypeName}"/></td>	
		</tr>

    </table>
   <!-- End search options -->
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${costReportViewBeanCollection != null}" >
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
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
		 <c:set var="colorClass" value=''/>
		 <c:set var="dataCount" value='${0}'/>
		 <c:set var="voucherUrlIndex" value='${-1}'/>
		 <c:set var="voucherUrl" value=''/>
		 <c:set var="whereClauseIndex" value='${-1}'/>
		 <c:set var="whereClauseData" value=''/>
		 <c:set var="totalAddChargeIndex" value='${-1}'/>
		 <c:set var="totalSalesTaxIndex" value='${-1}'/>

		 <%-- first try to find out all of the indexes need link
		      i.e. total additional charge, total_sales_tax
		 --%>
		 <c:forEach var="bean" items="${sqlFields}" varStatus="status2">
			 <%-- add 1 to index because whereClauseForLink was added --%>
			 <c:if test="${status2.current == 'total_add_charge'}">
				<c:set var="totalAddChargeIndex" value='${status2.index+1}'/>
			</c:if>
			<c:if test="${status2.current == 'total_sales_tax'}">
				<c:set var="totalSalesTaxIndex" value='${status2.index+1}'/>
				
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
									<td width="5%"><a href="javascript:openWinGeneric('<c:out value="${voucherUrl}"/>','<c:out value="${status2.current}"/>','800','600','yes')"><c:out value="${status2.current}"/></a></td>
									<c:set var="voucherUrl" value=''/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${status2.index == totalAddChargeIndex}">
											<td width="5%"><a href="javascript:showAdditionalCharge('<c:out value="${status.index}"/>')" style=\"color:#0000ff\"><c:out value="${status2.current}"/></a></td>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${status2.index == totalSalesTaxIndex}">
													<td width="5%"><a href="javascript:showSalesTax('<c:out value="${status.index}"/>')" style=\"color:#0000ff\"><c:out value="${status2.current}"/></a></td>
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
					<c:forEach var="columnData" items="${status2.current}" varStatus="status3">
						<td width="5%" class="optionTitleBoldLeft">${status3.current}</td>
						<c:set var="dataCount" value='${dataCount+1}'/>
					</c:forEach>
				</tr>
			</c:forEach>
		 </c:if>
	</table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty costReportViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
	<input type="hidden" name="action" id="action" value=""/>
	<input type="hidden" name="whereClauseForLink" id="whereClauseForLink" value=""/>  

	<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
	<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>