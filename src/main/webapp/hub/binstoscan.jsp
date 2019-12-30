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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
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
<script src="/js/hub/binstoscan.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.hub}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.hub}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altRoomId = new Array();
var altRoomName = new Array();
<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.hub}'/>
  <c:set var="roomCollection" value='${status.current.roomVar}'/>

  altRoomId["<c:out value="${currentHub}"/>"] = new Array(""
  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status1">
    ,"<c:out value="${status1.current.room}"/>"
  </c:forEach>
  );

  altRoomName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status2">
    ,"<c:out value="${status2.current.roomDescription}"/>"
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
<fmt:message key="binstoscan.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", daysInteger:"<fmt:message key="error.days.integer"/>",
moneyInteger:"<fmt:message key="error.money.integer"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/binstoscan.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%">
<tr><td width="70%" class="headingl">
<fmt:message key="binstoscan.title"/>
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
    <table width="100%" class="tableSearch">
      <tr>
        <td class="optionTitleBoldLeft">
          <fmt:message key="label.hub"/>:
          <c:set var="selectedHub" value='${param.branchPlant}'/>
          <select name="branchPlant" id="branchPlant" class="selectBox" onchange="hubChanged()">
          <c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.hub}'/>

            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.hub}'/>
                <c:set var="roomCollection" value='${status.current.roomVar}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="roomCollection" value='${status.current.roomVar}'/>
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
        <td class="optionTitleBoldLeft">
          <fmt:message key="label.item"/>:
          <input class="inputBox" type="text" name="itemId" id="itemId" value="<c:out value="${param.itemId}"/>" size="20">
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft">
          <fmt:message key="label.room"/>:
          <c:set var="selectedRoom" value='${param.room}'/>
          <select class="selectBox"  name="room" id="room" size="1">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status">
              <c:set var="currentRoom" value='${status.current.room}'/>
              <c:choose>
                <c:when test="${empty selectedRoom}" >
                  <c:set var="selectedRoom" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentRoom == selectedRoom}">
                  <option value="<c:out value="${currentRoom}"/>" selected><c:out value="${status.current.roomDescription}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentRoom}"/>"><c:out value="${status.current.roomDescription}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td class="optionTitleBoldLeft">
<fmt:message key="binstoscan.label.unitcost"/>
          <input class="inputBox" type="text" name="unitCostMin" id="unitCostMin" value="<c:out value="${param.unitCostMin}"/>" size="6" maxlength="5">
<fmt:message key="binstoscan.label.and"/>
          <input class="inputBox" type="text" name="unitCostMax" id="unitCostMax" value="<c:out value="${param.unitCostMax}"/>" size="6" maxlength="5">
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft">
<fmt:message key="binstoscan.label.receipt"/>
         <input class="inputBox" type="text" name="receiptDaySpan" id="receiptDaySpan" value="<c:out value="${param.receiptDaySpan}"/>" size="6" maxlength="5">
&nbsp;<fmt:message key="binstoscan.label.days"/>
        </td>
        <td class="optionTitleBoldLeft">
<fmt:message key="binstoscan.label.inventoryvalue"/>
          <input class="inputBox" type="text" name="inventoryValueMin" id="inventoryValueMin" value="<c:out value="${param.inventoryValueMin}"/>" size="6" maxlength="5">
<fmt:message key="binstoscan.label.and"/>
          <input class="inputBox" type="text" name="inventoryValueMax" id="inventoryValueMax" value="<c:out value="${param.inventoryValueMax}"/>" size="6" maxlength="5">
        </td>
      </tr>
      <tr>
        <td colspan="2" class="optionTitleBoldLeft">
<fmt:message key="binstoscan.label.bin"/>
          <input class="inputBox" type="text" name="binCountDays" id="binCountDays" value="<c:out value="${param.binCountDays}"/>" size="3" maxlength="5">
&nbsp;<fmt:message key="binstoscan.label.days"/>
        </td>
      </tr>
      <tr>
        <td colspan="4" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateForm();">
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
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${binsToScanViewBeanCollection != null}" >
<!-- Search results start -->
<table id="resultsMaskTable" width="50%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> </div>
    <div class="dataContent">
    <table width="100%" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

   <c:forEach var="binsToScanViewBean" items="${binsToScanViewBeanCollection}" varStatus="status">

    <c:if test="${status.index == 0}">
        <tr>
          <th><fmt:message key="label.room"/></th>
          <th><fmt:message key="label.bin"/></th>
        </tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="rowClass" value=''/>
  </c:when>
  <c:otherwise>
   <c:set var="rowClass" value='class=alt'/>
  </c:otherwise>
</c:choose>
        <tr <c:out value="${rowClass}"/>>
          <td><c:out value="${status.current.room}"/>&nbsp;</td>
          <td><c:out value="${status.current.bin}"/>&nbsp;</td>
        </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty binsToScanViewBeanCollection}" >

    <table width="100%">
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
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
