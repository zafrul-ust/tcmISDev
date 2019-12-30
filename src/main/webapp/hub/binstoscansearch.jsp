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
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/binstoscan.js"></script>

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
<fmt:message key="binstoscan.title"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"${status.current.hub}"
   </c:when>
   <c:otherwise>
    "${status.current.hub}"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altRoomId = new Array();
var altRoomName = new Array();
<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.hub}'/>
  <c:set var="roomCollection" value='${status.current.roomVar}'/>

  altRoomId["${currentHub}"] = new Array(""
  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status1">
    ,"${status1.current.room}"
  </c:forEach>
  );

  altRoomName["${currentHub}"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status2">
    ,"${status2.current.roomDescription}"
  </c:forEach>
  );
</c:forEach>
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", daysInteger:"<fmt:message key="error.days.integer"/>",
moneyInteger:"<fmt:message key="error.money.integer"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
all:"<fmt:message key="label.all"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<tcmis:form action="/binstoscanresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

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
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%--  Row 1   --%>
      <tr>
        <td class="optionTitleBoldLeft" width="45%">
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
                <option value="${currentHub}" selected="selected">${status.current.hubName}</option>
              </c:when>
              <c:otherwise>
                <option value="${currentHub}">${status.current.hubName}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
        </td>
        <td class="optionTitleBoldLeft" width="55%">
          <fmt:message key="label.item"/>:
          <input class="inputBox" type="text" name="itemId" id="itemId" value="${param.itemId}" size="20">
        </td>
      </tr>
      <%--  Row 2   --%>
      <tr>
        <td class="optionTitleBoldLeft" width="45%">
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
                  <option value="${currentRoom}" selected>${status.current.roomDescription}</option>
                </c:when>
                <c:otherwise>
                  <option value="${currentRoom}">${status.current.roomDescription}</option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td class="optionTitleBoldLeft" width="55%">
<fmt:message key="binstoscan.label.unitcost"/>
          <input class="inputBox" type="text" name="unitCostMin" id="unitCostMin" value="${param.unitCostMin}" size="6" maxlength="5">
<fmt:message key="binstoscan.label.and"/>
          <input class="inputBox" type="text" name="unitCostMax" id="unitCostMax" value="${param.unitCostMax}" size="6" maxlength="5">
        </td>
      </tr>
      <%--  Row 3  --%>
      <tr>
        <td class="optionTitleBoldLeft" width="45%">
<fmt:message key="binstoscan.label.receipt"/>
         <input class="inputBox" type="text" name="receiptDaySpan" id="receiptDaySpan" value="${param.receiptDaySpan}" size="6" maxlength="5">
&nbsp;<fmt:message key="binstoscan.label.days"/>
        </td>
        <td class="optionTitleBoldLeft" width="55%" nowrap>
<fmt:message key="binstoscan.label.inventoryvalue"/>
          <input class="inputBox" type="text" name="inventoryValueMin" id="inventoryValueMin" value="${param.inventoryValueMin}" size="6" maxlength="5">
<fmt:message key="binstoscan.label.and"/>
          <input class="inputBox" type="text" name="inventoryValueMax" id="inventoryValueMax" value="${param.inventoryValueMax}" size="6" maxlength="5">
        </td>
      </tr>
      <%--  Row 4   --%>
      <tr>
        <td nowrap class="optionTitleBoldLeft" >
<fmt:message key="binstoscan.label.bin"/>
          <input class="inputBox" type="text" name="binCountDays" id="binCountDays" value="${param.binCountDays}" size="3" maxlength="5">
&nbsp;<fmt:message key="binstoscan.label.days"/>
        </td>
      </tr>
      <tr>
        <td colspan="4" class="optionTitleBoldLeft" >
          <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search()">
       <input name="createExcel" id="createExcel" type="button" value="<fmt:message key='label.createexcelfile'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="generateExcel();"/>
       
       </td>
      </tr>
    </table>
    <!-- Search Option Table end -->
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
<input name="userAction" id="userAction" type="hidden"/>
<input name="pageid" id="pageid" type="hidden" value="${param.pageid}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
</tcmis:form>
</body>
</html:html>