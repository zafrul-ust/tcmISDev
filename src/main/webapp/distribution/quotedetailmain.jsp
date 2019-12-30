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
<%@ include file="/common/locale.jsp" %>
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
<fmt:message key="detailforquotenumber.title"/>&nbsp;${param.quoteNumber}
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

<tcmis:form action="/quotedetailmain.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea" style="display:">
<!-- Search Option Begins -->
<div style="display:none">
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
        <td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td>
          <!-- Use this for dropdowns you build with collections from the database -->
          <select name="select" id="select" class="selectBox">
            <option value="0" selected>Portamouth</option>
          </select>
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.text"/>:</td>
        <td><input name="textfield" id="textfield" type="text" class="inputBox"></td>
      </tr>
      <tr>
        <td class="optionTitle"><input name="radiobutton" id="radiobutton" type="radio" class="radioBtns" value="radiobutton">
          <fmt:message key="label.activeforfacility"/></td>
        <td class="optionTitle"><input name="checkbox" id="checkbox" type="checkbox" class="radioBtns" value="checkbox">
          <fmt:message key="label.activeonly"/></td>
      </tr>
      <tr>
      <td>
         <input name="Submit" id="Submit" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm();">
<input name="CreateExcelFile" id="CreateExcelFile" type="button" value="<fmt:message key="label.createexcelfile"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
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
</div>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

   <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
<tr>
    <th width="5%"><fmt:message key="label.partnumber"/></th>
    <th width="5%"><fmt:message key="label.description"/></th>
    <th width="5%"><fmt:message key="label.specification"/></th>
    <th width="3%"><fmt:message key="label.qty"/></th>
    <th width="3%"><fmt:message key="label.priceeach"/></th>
    <th width="3%"><fmt:message key="label.numberOfOrders"/></th>
    <th width="2%"><fmt:message key="label.sellingprice"/></th>
    <th width="2%"><fmt:message key="label.orderqty"/></th>
    <th width="5%"><fmt:message key="label.allocated"/></th>
    <th width="5%"><fmt:message key="label.expectedcost"/></th>
    <th width="4%"><fmt:message key="label.margin"/></th>
    <th width="5%"><fmt:message key="label.warehouse"/></th>
    <th width="5%"><fmt:message key="label.region"/></th>
    <th width="4%"><fmt:message key="label.global"/></th>
    
   </tr>
</table>



<c:if test="${pageNameViewBeanCollection != null}" >
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
    <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div>
    <div class="dataContent">
     
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="pageNameViewBean" items="${pageNameViewBeanCollection}" varStatus="status">

   <tr>
    <th width="5%"><fmt:message key="label.partnumber"/></th>
    <th width="5%"><fmt:message key="label.description"/></th>
    <th width="5%"><fmt:message key="label.specification"/></th>
    <th width="3%"><fmt:message key="label.qty"/></th>
    <th width="3%"><fmt:message key="label.priceeach"/></th>
    <th width="3%"><fmt:message key="label.numberOfOrders"/></th>
    <th width="2%"><fmt:message key="label.sellingprice"/></th>
    <th width="2%"><fmt:message key="label.orderqty"/></th>
    <th width="5%"><fmt:message key="label.allocated"/></th>
    <th width="5%"><fmt:message key="label.expectedcost"/></th>
    <th width="4%"><fmt:message key="label.margin"/></th>
    <th width="5%"><fmt:message key="label.warehouse"/></th>
    <th width="5%"><fmt:message key="label.region"/></th>
    <th width="4%"><fmt:message key="label.global"/></th>    
   </tr>


    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td width="5%">"${status.current.partnumber}"/></td>
     <td width="5%">"${status.current.description}"/></td>
     <td width="5%">"${status.current.specification}"/></td>
     <td width="3%">"${status.current.qty}"/></td>
     <td width="3%">"${status.current.priceeach}"/></td>
     <td width="3%">"${status.current.numberOfOrders}"/></td>  
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty pageNameViewBeanCollection}" >

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

</tcmis:form>
</body>
</html:html>