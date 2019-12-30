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

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script SRC="/js/hub/itemcountscansheet.js" language="JavaScript"></script>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altHubId = new Array(
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

var altInventoryGroupId = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>

  altInventoryGroupId["<c:out value="${currentHub}"/>"] = new Array(""
  <c:forEach var="hubInventoryGroupOvViewBean" items="${inventoryGroupCollection}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="hubInventoryGroupOvViewBean" items="${inventoryGroupCollection}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
  </c:forEach>
  );
</c:forEach>
// -->
</SCRIPT>



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
<fmt:message key="itemcountscansheet.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/itemcountscansheet.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="itemcountscansheet.title"/>
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
        <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="20%">
          <c:set var="selectedHub" value='${param.hub}'/>
          <select name="hub" id="hub" class="selectBox" onchange="hubChanged()">
          <c:forEach var="hubInventoryGroupOvBea" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
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
        <td width="15%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="20%">
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="hubInventoryGroupOvViewBean" items="${inventoryGroupCollection}" varStatus="status">
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
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.itemtype"/>:</td>
        <td width="20%">
        <html:select property="itemType" styleClass="selectBox" styleId="itemType">
          <html:option value="" key="label.all"/>
          <html:option value="MM" key="itemcountscansheet.label.onlymm"/>
          <html:option value="OOR" key="itemcountscansheet.label.onlyoor"/>
        </html:select></td>
      </tr>
      <tr>
      <td class="optionTitleBoldRight"><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="document.genericForm.target='';document.genericForm.action='/tcmIS/hub/itemcountscansheet.do'"></td>
      <td colspan="5">&nbsp;</td>
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
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${countScanSetupViewBeanCollection != null}" >
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
    <div class="boxhead"> <a href="#" onclick="printScanSheet(); return false;"><fmt:message key="label.print"/></a></div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="countScanSetupViewBean" items="${countScanSetupViewBeanCollection}" varStatus="status">
    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
     <th width="2%"><fmt:message key="label.print"/>
      <c:if test="${status.index == 0}">
       <input type="checkbox" value="" onClick="checkAllCheckBoxes('ok')" name="checkAll" id="checkAll">
      </c:if>
     </th>
     <th><fmt:message key="label.inventorygroup"/></th>
     <th><fmt:message key="label.partnumber"/></th>
     <th><fmt:message key="label.itemid"/></th>
     <th><fmt:message key="label.description"/></th>
     <th><fmt:message key="label.countuom"/></th>
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

     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].inventoryGroup" id="inventoryGroup<c:out value="${status.index}"/>" value="<c:out value="${status.current.inventoryGroup}"/>" >
     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].itemId" id="itemId<c:out value="${status.index}"/>" value="<c:out value="${status.current.itemId}"/>" >
     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].catPartNo" id="catPartNo<c:out value="${status.index}"/>" value="<c:out value="${status.current.catPartNo}"/>" >
     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].partGroupNo" id="partGroupNo<c:out value="${status.index}"/>" value="<c:out value="${status.current.partGroupNo}"/>" >
     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].countUom" id="countUom<c:out value="${status.index}"/>" value="<c:out value="${status.current.countUom}"/>" >
     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].description" id="description<c:out value="${status.index}"/>" value="<c:out value="${status.current.description}"/>" >
     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].stockingMethod" id="stockingMethod<c:out value="${status.index}"/>" value="<c:out value="${status.current.stockingMethod}"/>" >
     <input type="hidden" name="countScanSetupViewBean[<c:out value="${status.index}"/>].countType" id="countType<c:out value="${status.index}"/>" value="<c:out value="${status.current.countType}"/>" >

     <td width="2%">
       <input type="checkbox" name="countScanSetupViewBean[<c:out value="${status.index}"/>].ok" id="ok<c:out value="${status.index}"/>" value="<c:out value="${status.current.itemId}"/>">
     </td>
     <td><c:out value="${status.current.inventoryGroup}"/></td>
     <td><c:out value="${status.current.catPartNo}"/></td>
     <td><c:out value="${status.current.itemId}"/></td>
     <td><c:out value="${status.current.description}"/></td>
     <td><c:out value="${status.current.countUom}"/></td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty countScanSetupViewBeanCollection}" >

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

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
 <input type="hidden" name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>">
 <%--<input type="hidden" name="lastSearchHub" id="lastSearchHub" value="<c:out value='${param.hub}'/>">
 <input type="hidden" name="lastSearchInventoryGroup" id="lastSearchInventoryGroup" value="<c:out value='${param.inventoryGroup}'/>">
 <input type="hidden" name="lastSearchItemType" id="lastSearchItemType" value="<c:out value='${param.itemtype}'/>">--%>
</div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

