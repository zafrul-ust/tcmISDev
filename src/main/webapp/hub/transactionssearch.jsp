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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/transactions.js"></script>
<script type="text/javascript" src="/js/supply/searchPOs.js"></script>

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
<fmt:message key="transactions.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
   messagesData={alert:"<fmt:message key="label.alert"/>",
                   and:"<fmt:message key="label.and"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
           validValues:"<fmt:message key="transactions.validvalues"/>",
          onDateFormat:"<fmt:message key="transactions.ondateformat">
          					<fmt:param>${dateFormatLabel}</fmt:param>
          				</fmt:message>",
             receiptId:"<fmt:message key="label.receiptid"/>",
              mrNumber:"<fmt:message key="label.mrnumber"/>",
                    po:"<fmt:message key="label.po"/>",
           withinXDays:"<fmt:message key="transactions.withinxedays"/>",
                itemId:"<fmt:message key="label.itemid"/>"};

   var althubid = new Array(
   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
     <c:choose>
       <c:when test="${status.index > 0}">
        ,"<c:out value="${status.current.branchPlant}"/>"
       </c:when>
       <c:otherwise>
        "<c:out value="${status.current.branchPlant}"/>"
       </c:otherwise>
      </c:choose>
   </c:forEach>
   );

   var altinvid = new Array();
   var altinvName = new Array();
   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
     <c:set var="currentHub" value='${status.current.branchPlant}'/>
     <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

   altinvid["<c:out value="${currentHub}"/>"] = new Array("All"
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
      </c:forEach>
   );

   altinvName["<c:out value="${currentHub}"/>"] = new Array("All"
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
      </c:forEach>
   );
   </c:forEach>
 //-->
 </script>


</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/transactionsresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

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
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <c:set var="selectedHub" value='${param.branchPlant}'/>
         <c:set var="selectedHubName" value=''/>
         <c:set var="inventoryGroupDefinitions" value='${null}'/>
         <select name="branchPlant" onchange="hubChanged()" class="selectBox" id="branchPlant">
           <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:if test="${selectedHub == null}">
                <c:set var="selectedHub" value="${currentHub}"/>
            </c:if>
            <c:if test="${currentHub == selectedHub}" >
              <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
            </c:if>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}"/></option>
                <c:set var="selectedHubName" value="${status.current.hubName}"/>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.receiptid"/>:</td>
      <td width="10%" class="optionTitleLeftt">
         <input class="inputBox" type="text" name="receiptId" id="receiptId" value='<c:out value="${param.receiptId}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.mr"/>:</td>
      <td width="10%" class="optionTitleLeftt">
         <input class="inputBox" type="text" name="mrNumber" id="mrNumber" value='<c:out value="${param.mrNumber}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.trackingnumber"/>:</td>
      <td width="10%" class="optionTitleBoldLeft">
         <input class="inputBox" type="text" name="trackingNumber" id="trackingNumber"/>
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.invgrp"/>:&nbsp;</td>
      <td width="10%">
       <c:set var="selectedIg" value='${param.inventoryGroup}'/>
       <c:set var="invenGroupCount" value='${0}'/>
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox" size="1">
        <option value="All"><fmt:message key="label.all"/></option>
        <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
          <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
          <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
          <c:if test="${empty selectedIg}" >
              <c:set var="selectedIg" value=""/>
          </c:if>
          <c:choose>
            <c:when test="${currentIg == selectedIg}">
              <option value='<c:out value="${currentIg}"/>' selected><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:when>
            <c:otherwise>
              <option value='<c:out value="${currentIg}"/>'><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
       </select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.itemid"/>:</td>
      <td width="10%" class="optionTitleLeft">
         <input class="inputBox" type="text" name="itemId" id="itemId" value='<c:out value="${param.itemId}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.mfglot"/>:</td>
      <td width="10%" class="optionTitleLeft">
         <input class="inputBox" type="text" name="mfgLot" id="mfgLot" value='<c:out value="${param.mfgLot}"/>' size="25">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.po"/>:</td>
      <td width="10%" class="optionTitleLeft">
         <input class="inputBox" type="text" name="radianPo" id="radianPo" value='<c:out value="${param.radianPo}"/>' size="15">
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="transactions.transtype"/>:&nbsp;</td>
      <td width="10%">
        <html:select property="transType" styleClass="selectBox" styleId="transType">
          <html:option value="ALL" key="label.all"/>
          <html:option value="OV" key="graphs.label.receipts"/>
          <html:option value="IT" key="label.transfers"/>
          <html:option value="RI" key="graphs.label.issues"/>
        </html:select>
      </td>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="transactions.ondate"/>:</td>
      <td width="10%">
		<input class="inputBox pointer" readonly type="text" name="txnOnDate" id="txnOnDate" value="${param.txnOnDate}" onClick="return getCalendar(document.genericForm.txnOnDate);" size="10"/>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.within"/></td>
      <td width="10%" class="optionTitleLeft">
         <c:choose>
          <c:when test="${param.daysOld == null}" >
              <c:set var="numDaysOld" value="60"/>
          </c:when>
          <c:otherwise>
              <c:set var="numDaysOld" value="${param.daysOld}"/>
          </c:otherwise>
         </c:choose>
         <input class="inputBox" type="text" name="daysOld" id="daysOld" value='<c:out value="${numDaysOld}"/>' size="5">&nbsp;<fmt:message key="label.days"/>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:&nbsp;</td>
      <td width="10%">
        <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
          <html:option value="dateOfReceipt" key="inventorydetail.label.dor"/>
          <html:option value="expireDate" key="transactions.expiredate"/>
          <html:option value="itemId" key="label.itemid"/>
          <html:option value="radianPo" key="label.po"/>
          <html:option value="receiptId" key="label.receiptid"/>
          <html:option value="transactionDate" key="transactions.transactiondate"/>
        </html:select>
      </td>
    </tr>
    <tr>
     <td colspan="8" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()">
          <input name="createXSL" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="createXSL" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return validateSearchFormXSL()"/>
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
<input type="hidden" name="uAction" id="uAction" value="createXSL"/>
<input type="hidden" name="hubName" id="hubName" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>