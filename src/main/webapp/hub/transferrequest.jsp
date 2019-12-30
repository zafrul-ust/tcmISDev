<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>

<meta http-equiv="content-type" content="text/html;charset=UTF-8">
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

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>


<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/transferrequest.js" language="JavaScript"></script>
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

var toAltHubId = new Array(
<c:forEach var="hubBean" items="${hubInventoryGroupOvBeanCollection}" varStatus="status">
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

var toAltInventoryGroupId = new Array();
var toAltInventoryGroupName = new Array();
<c:forEach var="hubBean" items="${hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupBeanCollection" value='${status.current.inventoryGroupCollection}'/>

  toAltInventoryGroupId["<c:out value="${currentHub}"/>"] = new Array(
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

  toAltInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array(
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
<fmt:message key="transferrequest.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", transferQuantityInteger:"<fmt:message key="error.quantity.integer"/>",
noTransfer:"<fmt:message key="error.transfer.none"/>", itemIdInteger:"<fmt:message key="error.item.integer"/>",
dateFormat:"<fmt:message key="error.date.invalid"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="/transferrequest.do" onsubmit="return submitOnlyOnce();" acceptCharset="UTF-8" enctype="UTF-8">

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
<fmt:message key="transferrequest.title"/>
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
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.from"/> <fmt:message key="label.hub"/>:</td>
        <td width="15%">
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
        <td width="10%" class="optionTitleBoldRight">
          <!-- Use this for dropdowns that are hardcoded -->
          <html:select property="itemOrDescription" styleClass="selectBox" styleId="itemOrDescription">
          <html:option value="itemId" key="label.itemid"/>
          <html:option value="description" key="label.description"/>
        </html:select>
       </td>
        <td width="15%" class="optionTitleBoldLeft">
          <!-- Use this for dropdowns that are hardcoded -->
          <html:select property="criterion" styleClass="selectBox" styleId="criterion">
          <html:option value="is" key="label.is"/>
          <html:option value="contain" key="label.contain"/>
          <html:option value="startsWith" key="label.startswith"/>
          <html:option value="endsWith" key="label.endswith"/>
        </html:select>
&nbsp;
<input name="criteria" id="criteria" type="text" class="inputBox" value="<c:out value="${param.criteria}"/>">
       </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.from"/> <fmt:message key="label.inventorygroup"/>:</td>
        <td width="15%">
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

        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td width="15%">
          <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
            <html:option value="itemId" key="label.itemid"/>
            <html:option value="itemDescription" key="label.description"/>
          </html:select>
        </td>
      </tr>

      <tr>
      <td colspan="4"><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateForm();"></td>
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
<c:if test="${errorCollection != null}" >
  <c:forEach var="transferRequestInputBean" items="${errorCollection}" varStatus="status">
    <fmt:message key="error.transferrequest.procerror"/>: <c:out value="${status.current.itemId}"/>/<c:out value="${status.current.fromInventoryGroup}"/>/<c:out value="${status.current.toInventoryGroup}"/>
  </c:forEach>
  <c:if test="${empty errorCollection}" >
   <div id="successMessageArea" class="successMessages">
    <fmt:message key="transferrequest.successmessage"/>
   </div>
  </c:if>
</c:if>
</div>
<!-- Error Messages Ends -->

<c:if test="${transferableInventoryViewBeanCollection != null}" >
<bean:size collection="${transferableInventoryViewBeanCollection}" id="resultSize"/>
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
      <input name="action" type="hidden">
      <tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer">
        <a href="#" onclick="submitTransfer(<c:out value="${resultSize}"/>);"><fmt:message key="label.process"/></a>
      </tcmis:inventoryGroupPermission>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>

    <c:forEach var="transferableInventoryViewBean" items="${transferableInventoryViewBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.item"/></th>
    <th width="15%"><fmt:message key="label.description"/></th>
    <th width="5%"><fmt:message key="label.packaging"/></th>
    <th width="5%"><fmt:message key="label.from"/> <fmt:message key="label.inventorygroup"/></th>
    <th width="5%"><fmt:message key="label.to"/> <fmt:message key="label.hub"/></th>
    <th width="5%"><fmt:message key="label.to"/> <fmt:message key="label.inventorygroup"/></th>
    <th width="5%"><fmt:message key="label.quantityavailable"/></th>
    <th width="5%"><fmt:message key="label.quantitytosend"/></th>
    <th width="5%"><fmt:message key="label.datesent"/></th>
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
     <td width="5%"><c:out value="${status.current.itemId}"/></td>
     <td width="15%"><c:out value="${status.current.itemDesc}"/></td>
     <td width="5%"><c:out value="${status.current.packaging}"/></td>
     <td width="5%"><c:out value="${status.current.inventoryGroupName}"/></td>
     <td width="5%">&nbsp;
<tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer" inventoryGroup="${status.current.inventoryGroup}">
<input name="transferRequestInputBean[<c:out value="${status.index}"/>].fromInventoryGroup" id="transferRequestInputBean[<c:out value="${status.index}"/>].fromInventoryGroup" type="hidden" value="<c:out value="${status.current.inventoryGroup}"/>">
<input name="transferRequestInputBean[<c:out value="${status.index}"/>].itemId" id="transferRequestInputBean[<c:out value="${status.index}"/>].itemId" type="hidden" value="<c:out value="${status.current.itemId}"/>">

          <select name="transferRequestInputBean[<c:out value="${status.index}"/>].toBranchPlant" id="toBranchPlant<c:out value="${status.index}"/>" class="selectBox" onchange="toHubChanged('<c:out value="${status.index}"/>');">
<%--
          <c:forEach var="hubBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status1">
--%>
          <c:forEach var="hubBean" items="${hubInventoryGroupOvBeanCollection}" varStatus="status1">
            <c:set var="currentHub" value='${status1.current.branchPlant}'/>

            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status1.current.branchPlant}'/>
                <c:set var="inventoryGroupCollection" value='${status1.current.inventoryGroupCollection}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="inventoryGroupCollection" value='${status1.current.inventoryGroupCollection}'/>
              </c:when>
            </c:choose>

            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status1.current.hubName}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentHub}"/>"><c:out value="${status1.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
</tcmis:inventoryGroupPermission>
</td>
     <td width="10%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer" inventoryGroup="${status.current.inventoryGroup}">
          <select name="transferRequestInputBean[<c:out value="${status.index}"/>].toInventoryGroup" id="toInventoryGroup<c:out value="${status.index}"/>" class="selectBox">
            <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status2">
              <c:set var="currentInventoryGroup" value='${status2.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value="<c:out value="${currentInventoryGroup}"/>" selected><c:out value="${status2.current.inventoryGroupName}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentInventoryGroup}"/>"><c:out value="${status2.current.inventoryGroupName}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
</tcmis:inventoryGroupPermission>
&nbsp;</td>
     <td width="5%"><c:out value="${status.current.quantity}"/></td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer" inventoryGroup="${status.current.inventoryGroup}">
<input name="transferRequestInputBean[<c:out value="${status.index}"/>].transferQuantity" id="transferQuantity<c:out value="${status.index}"/>" type="text" class="inputBox" size="4" onchange="validateQuantity('<c:out value="${status.index}"/>');">
</tcmis:inventoryGroupPermission>
&nbsp;</td>
     <td width="5%">
<tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer" inventoryGroup="${status.current.inventoryGroup}">
<c:if test="${status.current.xferSourceOriginate == 'Y'}">
<input name="transferRequestInputBean[<c:out value="${status.index}"/>].transferDate" id="transferDate<c:out value="${status.index}"/>" type="text" class="inputBox" size="8" maxlength="10">
<a href="javascript: void(0);" id="linktransferDate<c:out value="${status.index}"/>" onclick="return getCalendar(document.genericForm.transferDate<c:out value="${status.index}"/>);">&diams;</a>
</c:if>
</tcmis:inventoryGroupPermission>
&nbsp;</td>
   </tr>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty transferableInventoryViewBeanCollection}" >

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

