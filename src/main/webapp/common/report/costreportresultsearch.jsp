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

<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/searchiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script src="/js/common/report/costreportresultresults.js" language="JavaScript"></script>

<title>
<fmt:message key="label.costreport"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="mySearchOnLoad();">

<tcmis:form action="/costreportresultresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="920" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<tr>
		  <td class="optionTitleBoldRight">&nbsp;</td>
		  <td class="optionTitleLeft">&nbsp;</td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.unitofmeasure"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.uomName}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.requestor"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.requestorName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.searchby"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.searchByName}"/>&nbsp;<c:out value="${requestScope.costReportInputBean.searchType}"/>&nbsp;<c:out value="${requestScope.costReportInputBean.searchText}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.companyName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoicenr"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.searchByInvoiceNumber}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.reportinggroup"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.reportingGroup}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoiceperiod"/>:</td>
		  <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.searchByInvoicePeriod}"/></td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.facilityName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoicedbetween"/>:</td>
		  <fmt:formatDate var="tmpBegin" value="${requestScope.costReportInputBean.invoiceDateBegin}" pattern="${dateFormatPattern}"/>
		  <fmt:formatDate var="tmpEnd" value="${requestScope.costReportInputBean.invoiceDateEnd}" pattern="${dateFormatPattern}"/>
		  <td class="optionTitleLeft">
			<c:choose>
			  <c:when test="${tmpBegin != null && tmpEnd != null}">
			  	 ${tmpBegin}&nbsp;<fmt:message key="label.and"/>&nbsp;${tmpEnd}
			  </c:when>
			  <c:otherwise>
				 <c:if test="${tmpBegin != null}">
				   &gt;&nbsp;${tmpBegin}
				 </c:if>
				 <c:if test="${tmpEnd != null}">
					&lt;&nbsp;${tmpEnd}
				 </c:if>
			  </c:otherwise>
		   </c:choose>
		  </td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.workarea"/>:</td>
        <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.applicationName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.deliveredbetween"/>:</td>
		  <fmt:formatDate var="tmpBegin" value="${requestScope.costReportInputBean.dateDeliveredBegin}" pattern="${dateFormatPattern}"/>
		  <fmt:formatDate var="tmpEnd" value="${requestScope.costReportInputBean.dateDeliveredEnd}" pattern="${dateFormatPattern}"/>
		  <td class="optionTitleLeft">
			<c:choose>
			  <c:when test="${tmpBegin != null && tmpEnd != null}">
			  	 ${tmpBegin} <fmt:message key="label.and"/> ${tmpEnd}
			  </c:when>
			  <c:otherwise>
				 <c:if test="${tmpBegin != null}">
				   > ${tmpBegin}
				 </c:if>
				 <c:if test="${tmpEnd != null}">
					< ${tmpEnd}
				 </c:if>
			  </c:otherwise>
		   </c:choose>
		  </td>
		</tr>
		<tr>
		  <td class="optionTitleBoldRight"><fmt:message key="label.accountingsystem"/>:</td>
        <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.accountSysName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.itemtype"/>:</td>
        <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.itemTypeName}"/></td>
		  <td class="optionTitleBoldRight"><fmt:message key="label.invoicetype"/>:</td>
        <td class="optionTitleLeft"><c:out value="${requestScope.costReportInputBean.invoiceTypeName}"/></td>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
 </div>
<!-- Hidden elements end -->

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display -->
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

</tcmis:form>
</body>
</html:html>