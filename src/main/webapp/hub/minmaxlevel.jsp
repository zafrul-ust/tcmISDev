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

<tcmis:fontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/minmaxlevel.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
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

var altInventoryGroupId = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupBeanCollection" value='${status.current.inventoryGroupCollection}'/>

  altInventoryGroupId["<c:out value="${currentHub}"/>"] = new Array("",
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.inventoryGroup}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>",
  <c:forEach var="InventoryGroupBean" items="${inventoryGroupBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.inventoryGroupName}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>
// -->
</script>

<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="minmaxlevel.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", criteriaRequired:"<fmt:message key="error.itempartnumber.required"/>",
itemIdInteger:"<fmt:message key="error.item.integer"/>",
reorderQuantityInteger:"<fmt:message key="error.reorderquantity.integer"/>",
reorderQuantityNotZero:"<fmt:message key="error.reorderquantity.notzero"/>",
reorderPointInteger:"<fmt:message key="error.reorderpoint.integer"/>",
stockingLevelInteger:"<fmt:message key="error.stockinglevel.integer"/>",
reorderPointVersusLevel:"<fmt:message key="error.reorderpointlevel"/>",
lookAheadDaysInteger:"<fmt:message key="error.lookaheaddays.integer"/>"};
// -->
</script>
</head>
<c:if test="${currentMinMaxLevelViewBeanCollection != null}">
<bean:size collection="${currentMinMaxLevelViewBeanCollection}" id="resultSize"/>
</c:if>
<body bgcolor="#ffffff" onload="myOnLoad(<c:out value="${resultSize}"/>);">

<tcmis:form action="/minmaxlevel.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="minmaxlevel.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

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
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="20%">
          <!-- Use this for dropdowns you build with collections from the database -->
<c:set var="selectedHub" value='${param.branchPlant}'/>
          <select name="branchPlant" id="branchPlant" class="selectBox" onchange="hubChanged();">
          <c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>

            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.branchPlant}'/>
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
              </c:when>
            </c:choose>

            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
       </td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="20%">
          <!-- Use this for dropdowns you build with collections from the database -->
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status">
              <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value="<c:out value="${currentInventoryGroup}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentInventoryGroup}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
       </td>
        <td width="10%" class="optionTitleBoldRight">
          <!-- Use this for dropdowns that are hardcoded -->
          <html:select property="criterion" styleClass="selectBox" styleId="criterion">
          <html:option value="itemId" key="label.itemid"/>
          <html:option value="partNumber" key="label.partnumber"/>
        </html:select>
       </td>
        <td width="30%"><input name="criteria" id="criteria" type="text" class="inputBox" value="<c:out value="${param.criteria}"/>"></td>
      </tr>

      <tr>
      <td colspan="6"><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateForm();"></td>
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
<c:forEach var="minMaxLevelLogViewBean" items="${errorCollection}" varStatus="status">
  <fmt:message key="error.minmaxlevel.procerror"/>: <c:out value="${status.current.inventoryGroup}"/>/<c:out value="${status.current.catalogId}"/>/<c:out value="${status.current.catPartNo}"/>
</c:forEach>
</div>
<!-- Error Messages Ends -->

<c:if test="${currentMinMaxLevelViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>   
<c:if test="${!empty currentMinMaxLevelViewBeanCollection}" >
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
      <input name="action" type="hidden">
      <tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg">
        <a href="#" onclick="submitUpdate(<c:out value="${resultSize}"/>);"><fmt:message key="label.update"/></a> 
      </tcmis:inventoryGroupPermission>
    </div>
    <div class="dataContent">
    <table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="currentMinMaxLevelViewBean" items="${currentMinMaxLevelViewBeanCollection}" varStatus="status">
    <c:set var="dataCount" value='${dataCount+1}'/>
    <c:if test="${status.index == 0}">
    <tr>
      <td width="100%" colspan="15">
    <c:choose>
     <c:when test="${param.criterion == 'itemId'}">
        <fmt:message key="label.itemdescription"/>: <c:out value="${status.current.itemDesc}"/>
     </c:when>
     <c:otherwise>
        <fmt:message key="label.partdescription"/>: <c:out value="${status.current.partDescription}"/>
     </c:otherwise>
    </c:choose>
      </td>
    </tr>
    </c:if>
    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.inventorygroup"/></th>
    <th width="5%"><fmt:message key="label.company"/></th>
    <th width="5%"><fmt:message key="label.catalog"/></th>
    <th width="5%"><fmt:message key="label.partnumber"/></th>
    <th width="5%"><fmt:message key="label.partgroupnumber"/></th>
    <th width="5%"><fmt:message key="label.itemid"/></th>
    <th width="5%"><fmt:message key="label.currentstockingmethod"/></th>
    <th width="5%"><fmt:message key="label.stockingmethod"/></th>
    <th width="5%"><fmt:message key="label.reorderpoint"/></th>
    <th width="5%"><fmt:message key="label.stockinglevel"/></th>
    <th width="5%"><fmt:message key="label.reorderquantity"/></th>
    <th width="5%"><fmt:message key="label.lookaheaddays"/></th>
    <th width="5%"><fmt:message key="label.receiptprocessingmethod"/></th>
    <th width="5%"><fmt:message key="label.billingmethod"/></th>
    <th width="15%"><fmt:message key="label.remarks"/></th>
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

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td width="5%"><c:out value="${status.current.inventoryGroup}"/></td>
     <td width="5%"><c:out value="${status.current.companyId}"/></td>
     <td width="5%"><c:out value="${status.current.catalogId}"/></td>
     <td width="5%"><c:out value="${status.current.catPartNo}"/></td>
     <td width="5%"><c:out value="${status.current.partGroupNo}"/></td>
     <td width="5%"><c:out value="${status.current.itemId}"/></td>
     <td width="5%"><c:out value="${status.current.currentStockingMethod}"/></td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].inventoryGroup" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].inventoryGroup" type="hidden" value="<c:out value="${status.current.inventoryGroup}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].companyId" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].companyId" type="hidden" value="<c:out value="${status.current.companyId}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].catalogId" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].catalogId" type="hidden" value="<c:out value="${status.current.catalogId}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].catPartNo" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].catPartNo" type="hidden" value="<c:out value="${status.current.catPartNo}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].partGroupNo" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].partGroupNo" type="hidden" value="<c:out value="${status.current.partGroupNo}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].personnelId" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].personnelId" type="hidden" value="<c:out value="${personnelBean.personnelId}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].oldStockingMethod" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].oldStockingMethod" type="hidden" value="<c:out value="${status.current.stockingMethod}"/>">
<select name="minMaxLevelInputBean[<c:out value="${status.index}"/>].stockingMethod" id="stockingMethod<c:out value="${status.index}"/>" class="selectBox" onchange="stockingMethodChanged('<c:out value="${status.index}"/>');">
  <option value="exitMM" <c:if test="${status.current.stockingMethod == 'exitMM'}"> selected </c:if>><fmt:message key="label.exitmm"/></option>
  <option value="MM" <c:if test="${status.current.stockingMethod == 'MM'}"> selected </c:if>><fmt:message key="label.mm"/></option>
  <option value="OOR" <c:if test="${status.current.stockingMethod == 'OOR'}"> selected </c:if>><fmt:message key="label.oor"/></option>
  <option value="virtualMM" <c:if test="${status.current.stockingMethod == 'virtualMM'}"> selected </c:if>><fmt:message key="label.virtualmm"/></option>
</select>
<%--
<html:select property="foo" value="${status.current.currentStockingMethod}">
<html:option value="exitMM"><fmt:message key="label.exitmm"/></html:option>
<html:option value="MM"><fmt:message key="label.mm"/></html:option>
<html:option value="OOR"><fmt:message key="label.oor"/></html:option>
<html:option value="virtualMM"><fmt:message key="label.virtualmm"/></html:option>
</html:select>
--%>
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="false" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<c:out value="${status.current.stockingMethod}"/>
</tcmis:inventoryGroupPermission>
     </td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].oldReorderPoint" id="oldReorderPoint<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.reorderPoint}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].reorderPoint" id="reorderPoint<c:out value="${status.index}"/>" type="text" class="inputBox" value="<c:out value="${status.current.reorderPoint}"/>" size="5" maxlength="10" onchange="checkReorderPoint('<c:out value="${status.index}"/>');">
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="false" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<c:out value="${status.current.reorderPoint}"/>
</tcmis:inventoryGroupPermission>
     </td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].oldStockingLevel" id="oldStockingLevel<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.stockingLevel}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].stockingLevel" id="stockingLevel<c:out value="${status.index}"/>" type="text" class="inputBox" value="<c:out value="${status.current.stockingLevel}"/>" size="5" maxlength="10" onchange="checkStockingLevel('<c:out value="${status.index}"/>');">
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="false" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<c:out value="${status.current.stockingLevel}"/>
</tcmis:inventoryGroupPermission>
     </td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].oldReorderQuantity" id="oldReorderQuantity<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.reorderQuantity}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].reorderQuantity" id="reorderQuantity<c:out value="${status.index}"/>" type="text" class="inputBox" value="<c:out value="${status.current.reorderQuantity}"/>" size="5" maxlength="10" onchange="checkReorderQuantity('<c:out value="${status.index}"/>');">
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="false" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<c:out value="${status.current.reorderQuantity}"/>
</tcmis:inventoryGroupPermission>
     </td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].oldLookAheadDays" id="oldLookAheadDays<c:out value="${status.index}"/>" type="hidden" value="<c:out value="${status.current.lookAheadDays}"/>">
<input name="minMaxLevelInputBean[<c:out value="${status.index}"/>].lookAheadDays" id="lookAheadDays<c:out value="${status.index}"/>" type="text" class="inputBox" value="<c:out value="${status.current.lookAheadDays}"/>" size="5" maxlength="10" onchange="checkLookAheadDays('<c:out value="${status.index}"/>');">
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="false" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<c:out value="${status.current.lookAheadDays}"/>
</tcmis:inventoryGroupPermission>
     </td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="false" userGroupId="ItemMgmt" inventoryGroup="${status.current.inventoryGroup}">
  <c:out value="${status.current.receiptProcessingMethod}"/>
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ItemMgmt" inventoryGroup="${status.current.inventoryGroup}">
  <c:choose>
    <c:when test="${status.current.issueGeneration == 'Item Counting'}">
      <select name="minMaxLevelInputBean[<c:out value="${status.index}"/>].receiptProcessingMethod" id="receiptProcessingMethod<c:out value="${status.index}"/>" class="selectBox" value="<c:out value="${status.current.receiptProcessingMethod}"/>" onchange="receiptProcessingMethodChanged('<c:out value="${status.index}"/>');">
      <c:forEach var="igReceiptProcessingViewBean" items="${currentMinMaxLevelViewBean.igReceiptProcessingViewBeanCollection}" varStatus="status1">
        <option value="<c:out value="${status1.current.receiptProcessingMethod}"/>" 
        <c:if test="${status.current.receiptProcessingMethod == status1.current.receiptProcessingMethod}">
          selected
        </c:if>
        ><c:out value="${status1.current.receiptProcessingMethodDesc}"/></option>
      </c:forEach>
      </select>
    </c:when>
    <c:otherwise>
      <c:out value="${status.current.receiptProcessingMethod}"/>
    </c:otherwise>
  </c:choose>
</tcmis:inventoryGroupPermission>
     </td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="false" userGroupId="ItemMgmt" inventoryGroup="${status.current.inventoryGroup}">
<c:out value="${status.current.billingMethod}"/>
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="ItemMgmt" inventoryGroup="${status.current.inventoryGroup}">
  <c:choose>
    <c:when test="${status.current.issueGeneration == 'Item Counting'}">
      <select name="minMaxLevelInputBean[<c:out value="${status.index}"/>].billingMethod" id="billingMethod<c:out value="${status.index}"/>" class="selectBox" value="<c:out value="${status.current.billingMethod}"/>">
      <c:forEach var="igBillingMethodViewBean" items="${currentMinMaxLevelViewBean.igBillingMethodViewBeanCollection}" varStatus="status1">
        <option value="<c:out value="${status1.current.billingMethod}"/>" 
        <c:if test="${status.current.billingMethod == status1.current.billingMethod}">
          selected
        </c:if>
        ><c:out value="${status1.current.billingMethod}"/></option>
      </c:forEach>
      </select>
    </c:when>
    <c:otherwise>
      <c:out value="${status.current.billingMethod}"/>
    </c:otherwise>
  </c:choose>
</tcmis:inventoryGroupPermission>
     </td>
     <td width="15%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
<textarea class="inputBox" name="minMaxLevelInputBean[<c:out value="${status.index}"/>].remarks" id="minMaxLevelInputBean[<c:out value="${status.index}"/>].remarks" cols=30 rows=2></textarea>
</tcmis:inventoryGroupPermission>
<tcmis:inventoryGroupPermission indicator="false" userGroupId="MinMaxChg" inventoryGroup="${status.current.inventoryGroup}">
&nbsp;
</tcmis:inventoryGroupPermission>
     </td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</c:if>
<!-- now history -->
    <c:if test="${!empty minMaxLevelLogViewBeanCollection}">
<tr height="10"><td>&nbsp;</td></tr>
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

    <tr>
    <th colspan="22" ><fmt:message key="label.changehistory"/></th>
    </tr>

    <c:forEach var="minMaxLevelLogViewBean" items="${minMaxLevelLogViewBeanCollection}" varStatus="status">
    <c:set var="dataCount" value='${dataCount+1}'/>

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="4%"><fmt:message key="label.inventorygroup"/></th>
    <th width="2%"><fmt:message key="label.company"/></th>
    <th width="2%"><fmt:message key="label.catalogid"/></th>
    <th width="2%"><fmt:message key="label.partnumber"/></th>
    <th width="5%"><fmt:message key="label.partgroupnumber"/></th>
    <th width="5%"><fmt:message key="label.changedon"/></th>
    <th width="5%"><fmt:message key="label.changedby"/></th>
    <th width="5%"><fmt:message key="label.oldstockingmethod"/></th>
    <th width="5%"><fmt:message key="label.newstockingmethod"/></th>
    <th width="5%"><fmt:message key="label.oldreorderpoint"/></th>
    <th width="5%"><fmt:message key="label.newreorderpoint"/></th>
    <th width="5%"><fmt:message key="label.oldstockinglevel"/></th>
    <th width="5%"><fmt:message key="label.newstockinglevel"/></th>
    <th width="5%"><fmt:message key="label.oldreorderquantity"/></th>
    <th width="5%"><fmt:message key="label.newreorderquantity"/></th>
    <th width="5%"><fmt:message key="label.oldlookahead"/></th>
    <th width="5%"><fmt:message key="label.newlookahead"/></th>
    <th width="5%"><fmt:message key="label.oldreceiptprocessingmethod"/></th>
    <th width="5%"><fmt:message key="label.newreceiptprocessingmethod"/></th>
    <th width="5%"><fmt:message key="label.oldbillingmethod"/></th>
    <th width="5%"><fmt:message key="label.newbillingmethod"/></th>
    <th width="5%"><fmt:message key="label.remarks"/></th>
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

    <tr class="<c:out value="${colorClass}"/>" id="historyRowId<c:out value="${status.index}"/>">
     <td width="4%"><c:out value="${status.current.inventoryGroup}"/>&nbsp;</td>
     <td width="2%"><c:out value="${status.current.companyId}"/>&nbsp;</td>
     <td width="2%"><c:out value="${status.current.catalogId}"/>&nbsp;</td>
     <td width="2%"><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.partGroupNo}"/>&nbsp;</td>
     <td width="5%"><fmt:formatDate pattern="MM/dd/yyyy HH:mm:ss" value="${status.current.dateModified}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.modifiedByName}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.oldStocked}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.newStocked}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.oldOrderPoint}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.newOrderPoint}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.oldStockingLevel}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.newStockingLevel}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.oldReorderQuantity}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.newReorderQuantity}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.oldLookAhead}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.newLookAhead}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.oldReceiptProcessingMethod}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.newReceiptProcessingMethod}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.oldBillingMethod}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.newBillingMethod}"/>&nbsp;</td>
     <td width="5%"><c:out value="${status.current.remarks}"/>&nbsp;</td>
   </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty currentMinMaxLevelViewBeanCollection}" >

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
</div>
</td></tr>
</c:if> 
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

