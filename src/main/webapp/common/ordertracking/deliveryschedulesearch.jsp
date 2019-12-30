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

<%-- For Calendar support --%>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
<script src="/js/calendar/CalendarPopup.js" language="JavaScript"></script>
<script src="/js/calendar/date.js" language="JavaScript"></script>

<script src="/js/common/ordertracking/deliveryschedule.js" language="JavaScript"></script>

<title>
<fmt:message key="deliveryschedule.label.title"/>
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

<tcmis:form action="/deliveryscheduleresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
  <%-- row 1 --%>
  <tr>
    <td width="7%" class="optionTitleBoldRight">
      <fmt:message key="deliveryschedule.label.requestnum"/>:
    </td>
    <td width="15%" class="alignLeft">
      <c:out value='${scheduleDeliveryHeaderViewBean.prNumber}'/>
    </td>

    <td width="5%" class="optionTitleBoldRight">
      <fmt:message key="label.status"/>:
    </td>
    <td width="10%" class="alignLeft">
      <c:out value='${scheduleDeliveryHeaderViewBean.requestLineStatus}'/>
    </td>

    <td width="5%" class="optionTitleBoldRight">
      <fmt:message key="label.type"/>:
    </td>
    <td width="10%" class="alignLeft">
      <c:out value='${scheduleDeliveryHeaderViewBean.itemType}'/>
    </td>
  </tr>

  <%-- row 2 --%>
  <tr>
    <td width="7%" class="optionTitleBoldRight">
      <fmt:message key="label.partnumber"/>:
    </td>
    <td width="15%" class="alignLeft">
      <c:out value='${scheduleDeliveryHeaderViewBean.facPartNo}'/>
    </td>

    <td width="5%" class="optionTitleBoldRight">
      <fmt:message key="label.orderedqty"/>:
    </td>
    <td width="10%" class="alignLeft">
      <div id="orderedQty">
        <c:out value='${scheduleDeliveryHeaderViewBean.quantity}'/>
      </div>
    </td>

    <td width="5%" class="optionTitleBoldRight">
      <fmt:message key="deliveryschedule.label.qtyscheduled"/>:
    </td>
    <td width="10%" class="alignLeft">
      <div id="scheduledQty">
        <c:out value='${scheduleDeliveryHeaderViewBean.quantity}'/>
      </div>
    </td>
  </tr>

  <%-- row 3 --%>
  <tr>
    <td width="7%" class="optionTitleBoldRight">
      <fmt:message key="label.description"/>:
    </td>
    <td colspan="5" align="alignLeft">
      <c:out value='${scheduleDeliveryHeaderViewBean.partDescription}'/> (<c:out value='${scheduleDeliveryHeaderViewBean.packaging}'/>)
    </td>
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
<input name="action" id="action" type="hidden" value="">
<input name="companyId" id="companyId" type="hidden"value="<c:out value="${param.companyId}"/>">
<input name="prNumber" id="prNumber" type="hidden" value="<c:out value="${param.prNumber}"/>">
<input name="lineItem" id="lineItem" type="hidden"value="<c:out value="${param.lineItem}"/>">
<input name="source" id="source" type="hidden"value="<c:out value="${param.source}"/>">
<input name="requestLineStatus" id="requestLineStatus" type="hidden" value="<c:out value="${scheduleDeliveryHeaderViewBean.requestLineStatus}"/>">
<input name="requestor" id="requestor" type="hidden" value="<c:out value="${scheduleDeliveryHeaderViewBean.requestor}"/>">
<input name="orderedQtyV" id="orderedQtyV" type="hidden" value="<c:out value="${scheduleDeliveryHeaderViewBean.quantity}"/>">
<input name="scheduledQtyV" id="scheduledQtyV" type="hidden" value="<c:out value="${scheduleDeliveryHeaderViewBean.quantity}"/>">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>