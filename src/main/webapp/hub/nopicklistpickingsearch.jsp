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
<script src="/js/hub/nopicklistpicking.js" language="JavaScript"></script>

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

<script language="JavaScript" type="text/javascript">

var altHubId = new Array(
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
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

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>

  altInventoryGroup["<c:out value="${currentHub}"/>"] = new Array(""
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
        ,"<c:out value="${status1.current.inventoryGroupName}"/>"
  </c:forEach>
  );
</c:forEach>

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

      altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(""
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityId}"/>"
      </c:forEach>
      );

      altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
      </c:forEach>
      );

  </c:forEach>
</c:forEach>

</script>

<title>
<fmt:message key="nopicklistpicking.title"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
     submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
         qtyInteger:"<fmt:message key="error.quantity.integer"/>",
                all:"<fmt:message key="label.all"/>",
           diffQty1:"<fmt:message key="pickingqc.confirm.diffpickqty1"/>",
           diffQty2:"<fmt:message key="pickingqc.confirm.diffpickqty2"/>",
           diffQty3:"<fmt:message key="pickingqc.confirm.diffpickqty3"/>"};
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/nopicklistpickingresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">
<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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
        <td width="15%">
         <c:set var="selectedHub" value='${param.hub}'/>
          <select name="hub" id="hub" class="selectBox" onchange="hubChanged();">
          <c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:set var="currentHubName" value='${status.current.hubName}'/>
            <tcmis:facilityPermission indicator="true" userGroupId="Issuing" facilityId="${currentHubName}">
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
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}"/></option>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
            </tcmis:facilityPermission>
          </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.mr"/>:</td>
        <td width="15%">
          <input name="prNumber" id="prNumber" type="text" class="inputBox" value='<c:out value="${param.prNumber}"/>' size="8">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
        <td width="15%" colspan="3">
          <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox" onchange="inventoryGroupChanged();">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="inventoryGroupFacilityObjBean" items="${inventoryGroupCollection}" varStatus="status">
              <c:set var="currentInventoryGroup" value='${status.current.inventoryGroup}'/>
              <c:choose>
                <c:when test="${empty selectedInventoryGroup}" >
                  <c:set var="selectedInventoryGroup" value=''/>
                  <c:set var="facilityBeanCollection" value=''/>
                </c:when>
                <c:when test="${selectedInventoryGroup == currentInventoryGroup}" >
                  <c:set var="facilityBeanCollection" value='${status.current.facilities}'/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentInventoryGroup == selectedInventoryGroup}">
                  <option value='<c:out value="${currentInventoryGroup}"/>' selected><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:when>
                <c:otherwise>
                  <option value='<c:out value="${currentInventoryGroup}"/>'><c:out value="${status.current.inventoryGroupName}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select></td>

        <%--<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td width="15%">
          <c:set var="selectedSortBy" value="${param.sortBy}"/>
          <c:if test="${selectedSortBy == null || selectedSortBy == ''}">
            <c:set var="selectedSortBy" value='FACILITY_ID,APPLICATION'/>
          </c:if>
          <html:select property="sortBy" styleId="sortBy" styleClass="selectBox" value="${selectedSortBy}">
            <html:option value="facilityId,application" key="picklistpicking.facilityidworkarea"/>
            <html:option value="prNumber" key="label.mr"/>
            <html:option value="catPartNo" key="label.partnumber"/>
          </html:select>
        </td>--%>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="15%">
          <c:set var="selectedFacilityId" value='${param.facilityId}'/>
          <select name="facilityId" id="facilityId" class="selectBox">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status">
              <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${empty selectedFacilityId}" >
                  <c:set var="selectedFacilityId" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentFacilityId == selectedFacilityId}">
                  <option value='<c:out value="${currentFacilityId}"/>' selected><c:out value="${status.current.facilityId}"/></option>
                </c:when>
                <c:otherwise>
                  <option value='<c:out value="${currentFacilityId}"/>'><c:out value="${status.current.facilityId}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select></td>
        <td width="10%" class="optionTitleBoldRight">&nbsp;</td>
        <td width="15%">
          &nbsp;
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
           <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value='<fmt:message key="label.search"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
        </td>
        <td width="15%" class="optionTitleBoldRight">
          &nbsp;
        </td>
        <td width="10%">
          &nbsp;
        </td>
        <td width="15%">
          &nbsp;
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
<input name="sortBy" id="sortBy" value='mrLine' type="hidden">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

