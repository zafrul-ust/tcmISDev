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

<%@ include file="/common/locale.jsp" %> 
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
<fmt:message key="label.packinglist"/>
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

<body bgcolor="#ffffff">

<%--
<tcmis:form action="/pagename.do" onsubmit="return submitOnlyOnce();">
--%>
<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<!-- start of topNavigation-->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="label.packinglist"/>
</td>
<td width="30%" class="headingr">
</td>
</tr>
</table>
</div>
<!-- close of topNavigation-->

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${picklistPrintColl != null}" >
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
    <div class="boxhead">
    <a href="#" onclick="window.print();"><fmt:message key="label.print"/></a> |
		<a href="#" onclick="window.close(); return false;"><fmt:message key="label.close"/></a>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="pickBean" items="${picklistPrintColl}" varStatus="status">

<!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr>
      <%--<th width="5%"><fmt:message key="label.facility"/></th>--%>
      <th width="5%"><fmt:message key="label.workarea"/></th>
      <th width="5%"><fmt:message key="label.deliverypoint"/></th>
      <th width="5%"><fmt:message key="label.requestor"/></th>
      <th width="5%"><fmt:message key="label.mrline"/></th>
      <th width="2%"><fmt:message key="label.item"/></th>
      <c:choose>
        <c:when test="${pickBean.companyId ne 'SEAGATE'}">
          <th width="5%"><fmt:message key="label.partnumber"/></th>
        </c:when>
        <c:otherwise>
          <th width="5%"><fmt:message key="label.csmnumber"/></th>
        </c:otherwise>
      </c:choose>
      <th width="9%"><fmt:message key="label.partdescription"/></th>
      <th width="4%"><fmt:message key="label.receiptid"/></th>
<%--      <th width="4%"><fmt:message key="label.mfglot"/></th>
      <th width="4%"><fmt:message key="label.expdate"/></th>--%>
      <th width="2%"><fmt:message key="label.qty"/></th>
      <th width="5%"><fmt:message key="label.packaging"/></th>
      <th width="5%"><fmt:message key="label.issueid"/></th>
      <c:choose>
        <c:when test="${pickBean.companyId ne 'SEAGATE'}">
          <th width="5%"><fmt:message key="label.customerpo"/></th>
        </c:when>
        <c:otherwise>
          <th width="5%"><fmt:message key="label.donumber"/></th>
        </c:otherwise>
      </c:choose>  
      <th width="5%"><fmt:message key="label.datedelivered"/></th>
    </tr>
    </c:if>

    <c:choose>
      <c:when test="${status.index % 2 == 0}">
       <c:set var="colorClass" value=''/>
      </c:when>
      <c:otherwise>
       <c:set var="colorClass" value="alt"/>
      </c:otherwise>
    </c:choose>

   <tr class='<c:out value="${colorClass}"/>' align="center">
      <%--<td width="5%"><c:out value="${pickBean.facilityId}"/>&nbsp;</td>--%>
      <td width="5%"><c:out value="${pickBean.application}"/>&nbsp;</td>
      <td width="5%"><c:out value="${pickBean.deliveryPoint}"/>&nbsp;</td>
      <td width="5%"><c:out value="${pickBean.requestor}"/>&nbsp;</td>
      <td width="5%"><c:out value="${pickBean.mrLine}"/>&nbsp;</td>
      <td width="2%"><c:out value="${pickBean.itemId}"/>&nbsp;</td>
      <td width="5%"><c:out value="${pickBean.catPartNo}"/>&nbsp;</td>
      <td width="9%"><c:out value="${pickBean.partDescription}"/>&nbsp;</td>
      <td width="4%"><c:out value="${pickBean.receiptId}"/>&nbsp;<br></td>
<%--      <td width="4%"><c:out value="${pickBean.mfgLot}"/>&nbsp;</td>
      <td width="4%">
        <fmt:formatDate var="fmtExpireDate" value="${pickBean.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test='${fmtExpireDate=="01/01/3000"}'>
             <fmt:message key="label.indefinite"/>&nbsp;
          </c:when>
          <c:otherwise>
             <c:out value="${fmtExpireDate}"/>&nbsp;
          </c:otherwise>
        </c:choose>
      </td>--%>
     <td width="2%"><c:out value="${pickBean.quantity}"/>&nbsp;</td>
     <td width="5%"><c:out value="${pickBean.packaging}"/>&nbsp;</td>
     <td width="2%"><c:out value="${pickBean.issueId}"/>&nbsp;</td>
     <c:choose>
        <c:when test="${pickBean.companyId ne 'SEAGATE'}">
          <td width="2%"><c:out value="${pickBean.poNumber}"/>&nbsp;</td>
        </c:when>
        <c:otherwise>
          <td width="2%"><c:out value="${pickBean.doNumber}"/>&nbsp;</td>
        </c:otherwise>
      </c:choose>  
     <td width="2%">
       <fmt:formatDate var="fmtDateDelivered" value="${pickBean.dateDelivered}" pattern="${dateFormatPattern}"/>
       <c:out value="${fmtDateDelivered}"/>&nbsp;
     </td>     
     <c:set var="dataCount" value='${dataCount+1}'/>
    </tr>
   </c:forEach>
   </table>
      
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty picklistPrintColl}" >
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
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

<%--</tcmis:form>--%>
</body>
</html:html>