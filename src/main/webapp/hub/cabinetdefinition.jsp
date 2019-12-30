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

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
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
<script src="/js/hub/cabinetdefinition.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
var facilityPermissionArray = new Array();
<c:set var="counter" value='${0}'/>
<c:forEach var="hubBean" items="${personnelBean.permissionBean.userGroupMemberBeanCollection}" varStatus="status">
  <c:if test="${status.current.userGroupId == 'CabinetMgmt'}">
    facilityPermissionArray["<c:out value="${counter}"/>"] = "<c:out value="${status.current.facilityId}" escapeXml="false"/>";
    <c:set var="counter" value='${counter + 1}'/>
  </c:if>
</c:forEach>


var altHubName = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  altHubName["<c:out value="${status.current.branchPlant}"/>"] = "<c:out value="${status.current.hubName}"/>";
</c:forEach>

var altHubId = new Array(
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
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

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>

  altFacilityId["<c:out value="${currentHub}"/>"] = new Array(
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altFacilityName["<c:out value="${currentHub}"/>"] = new Array(
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

var altApplicationId = new Array();
var altApplicationDesc = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
    <c:set var="currentFacility" value='${status1.current.facilityId}'/>
    <c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>

      altApplicationId["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array(""
      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.application}" escapeXml="false"/>"
      </c:forEach>
      );

      altApplicationDesc["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.applicationDesc}" escapeXml="false"/>"
      </c:forEach>
      );

  </c:forEach>
</c:forEach>


var altCabinetIdArray = new Array();
var altCabinetNameArray = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
    <c:set var="currentFacility" value='${status1.current.facilityId}'/>
    <c:set var="sortedCabinetBeanForAllWorkAreasCollection" value='${status1.current.sortedCabinetBeanForAllWorkAreasCollection}'/>
    altCabinetIdArray["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>-All"] = new Array(""
    <c:forEach var="myBean" items="${sortedCabinetBeanForAllWorkAreasCollection}" varStatus="status2">
      ,"<c:out value="${status2.current.cabinetId}"/>"
    </c:forEach>
<%--
    <c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>
    altCabinetIdArray["<c:out value="${currentFacility}"/>-All"] = new Array(""
    <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
      <c:set var="currentApplication" value='${status2.current.application}'/>
      <c:set var="cabinetBeanCollection" value='${status2.current.cabinetBeanCollection}'/>
      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
        ,"<c:out value="${status3.current.cabinetId}"/>"
      </c:forEach>
    </c:forEach>
--%>
    );

    altCabinetNameArray["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>-All"] = new Array("<fmt:message key="label.all"/>"
    <c:forEach var="myBean" items="${sortedCabinetBeanForAllWorkAreasCollection}" varStatus="status2">
      ,"<c:out value="${status2.current.cabinetName}" escapeXml="false"/>"
    </c:forEach>
<%--
    altCabinetNameArray["<c:out value="${currentFacility}"/>-All"] = new Array("<fmt:message key="label.all"/>"
    <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
      <c:set var="currentApplication" value='${status2.current.application}'/>
      <c:set var="cabinetBeanCollection" value='${status2.current.cabinetBeanCollection}'/>
      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
        ,"<c:out value="${status3.current.cabinetName}" escapeXml="false"/>"
      </c:forEach>
    </c:forEach>
--%>
    );
  </c:forEach>
</c:forEach>


<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
    <c:set var="currentFacility" value='${status1.current.facilityId}'/>
    <c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>
    <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
      <c:set var="currentApplication" value='${status2.current.application}'/>
      <c:set var="cabinetBeanCollection" value='${status2.current.cabinetBeanCollection}'/>


      altCabinetIdArray["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>-<c:out value="${currentApplication}" escapeXml="false"/>"] = new Array(""
      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
        ,"<c:out value="${status3.current.cabinetId}"/>"
      </c:forEach>
      );

      altCabinetNameArray["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>-<c:out value="${currentApplication}" escapeXml="false"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
        ,"<c:out value="${status3.current.cabinetName}" escapeXml="false"/>"
      </c:forEach>
      );
    </c:forEach>
  </c:forEach>
</c:forEach>
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
<fmt:message key="cabinetdefinition.title"/>
</title>

<script language="JavaScript" type="text/javascript">

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
     submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        itemInteger:"<fmt:message key="error.item.integer"/>"};

</script>
</head>

<body bgcolor="#ffffff" onload="cabdefOnLoad();">


<tcmis:form action="/cabinetdefinition.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
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
<fmt:message key="cabinetdefinition.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<%-- Because of the way the jsp is compiled I need to do this here --%>
<c:set var="selectedHub" value='${param.branchPlant}'/>
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:choose>
    <c:when test="${empty selectedHub}" >
      <c:set var="selectedHub" value='${status.current.branchPlant}'/>
      <c:set var="initFacilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
    </c:when>
    <c:when test="${currentHub == selectedHub}" >
      <c:set var="initFacilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
    </c:when>
  </c:choose>
</c:forEach>

<c:set var="selectedFacility" value='${param.facilityId}'/>
<c:forEach var="facilityBean" items="${initFacilityBeanCollection}" varStatus="status">
  <c:set var="currentFacility" value='${status.current.facilityId}'/>
  <c:choose>
    <c:when test="${empty selectedFacility}" >
      <c:set var="selectedFacility" value=''/>
      <c:set var="initApplicationBeanCollection" value='${status.current.applicationBeanCollection}'/>
    </c:when>
    <c:when test="${currentFacility == selectedFacility}" >
      <c:set var="initApplicationBeanCollection" value='${status.current.applicationBeanCollection}'/>
    </c:when>
  </c:choose>
</c:forEach>

<c:set var="selectedApplication" value='${param.useApplication}'/>
  <c:choose>
    <c:when test="${empty selectedApplication}" >
<c:set var="selectedFacility" value='${param.facilityId}'/>
<c:forEach var="facilityBean" items="${initFacilityBeanCollection}" varStatus="status">
  <c:set var="currentFacility" value='${status.current.facilityId}'/>
  <c:choose>
    <c:when test="${empty selectedFacility}" >
      <c:set var="initCabinetBeanCollection" value='${status.current.sortedCabinetBeanForAllWorkAreasCollection}'/>
    </c:when>
    <c:when test="${currentFacility == selectedFacility}" >
      <c:set var="initCabinetBeanCollection" value='${status.current.sortedCabinetBeanForAllWorkAreasCollection}'/>
    </c:when>
  </c:choose>
</c:forEach>
    </c:when>
    <c:otherwise>
<c:forEach var="applicationBean" items="${initApplicationBeanCollection}" varStatus="status">
  <c:set var="currentApplication" value='${status.current.application}'/>
  <c:choose>
    <c:when test="${empty selectedApplication}" >
      <c:set var="selectedApplication" value=''/>
      <c:set var="initCabinetBeanCollection" value='${status.current.cabinetBeanCollection}'/>
    </c:when>
    <c:when test="${currentApplication == selectedApplication}" >
      <c:set var="initCabinetBeanCollection" value='${status.current.cabinetBeanCollection}'/>
    </c:when>
  </c:choose>
</c:forEach>
    </c:otherwise>
  </c:choose>

<%-- end setting up init collections --%>

<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
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
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.hub"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft">
         <c:set var="selectedHub" value='${param.branchPlant}'/>
         <c:set var="selectedHubName" value=''/>
         <input name="hubName" id="hubName" type="hidden">
         <select name="branchPlant" onchange="hubChanged()" class="selectBox" id="branchPlant">
           <c:forEach var="hubInventoryGroupOvBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}" escapeXml="false"/></option>
                <c:set var="selectedHubName" value="${status.current.hubName}"/>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}" escapeXml="false"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
        </td>
        <td width="5%" rowspan="3" class="optionTitleBoldRight">
          <fmt:message key="label.cabinet"/>:
        </td>
        <td width="20%" rowspan="3" class="optionTitleBoldLeft">
          <c:set var="selectedCabinetIdArray" value='${paramValues.cabinetIdArray}'/>
          <select name="cabinetIdArray" id="cabinetIdArray" class="selectBox" multiple size="5">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="cabinetBean" items="${initCabinetBeanCollection}" varStatus="status">
              <c:set var="currentCabinet" value='${status.current.cabinetId}'/>
              <c:choose>
                <c:when test="${empty selectedCabinetIdArray}" >
                  <option value='<c:out value="${currentCabinet}"/>'><c:out value="${status.current.cabinetName}" escapeXml="false"/></option>
                </c:when>
                <c:otherwise>
                  <c:set var="selectedFlag" value=''/>
                  <c:forEach items="${selectedCabinetIdArray}" varStatus="status1">
                    <c:set var="selectedCabinet" value='${selectedCabinetIdArray[status1.index]}'/>
                    <c:if test="${currentCabinet == selectedCabinet}">
                      <c:set var="selectedFlag" value='selected'/>
                    </c:if>
                  </c:forEach>
                  <option value='<c:out value="${currentCabinet}"/>' <c:out value="${selectedFlag}"/>><c:out value="${status.current.cabinetName}" escapeXml="false"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td width="20%">&nbsp;</td>
        <td width="10%" class="optionTitleBoldLeft">
            <input name="submitSearch" type="submit" class="inputBtns" value="Search" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onsubmit="return checkTransactionsInput();">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.facility"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft">
          <select class="selectBox"  name="facilityId" id="facilityId" size="1" onchange="facilityChanged()">
            <c:forEach var="facilityBean" items="${initFacilityBeanCollection}" varStatus="status">
              <c:set var="currentFacility" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${currentFacility == selectedFacility}">
                  <option value="<c:out value="${currentFacility}"/>" selected><c:out value="${status.current.facilityName}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentFacility}"/>"><c:out value="${status.current.facilityName}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td width="20%">&nbsp;</td>
        <td width="10%" class="optionTitleBoldLeft">
              <html:button property="buttonCreateExcel" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel()">
                 <fmt:message key="label.createexcelfile"/>
              </html:button>
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.workarea"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft">
          <select class="selectBox"  name="useApplication" id="application" size="1"  onchange="applicationChanged()">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="applicationBean" items="${initApplicationBeanCollection}" varStatus="status">
              <c:set var="currentApplication" value='${status.current.application}'/>
              <c:choose>
                <c:when test="${currentApplication == selectedApplication}">
                  <option value="<c:out value="${currentApplication}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentApplication}"/>"><c:out value="${status.current.applicationDesc}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td width="20%">&nbsp;</td>
        <td width="10%" class="optionTitleBoldLeft">
          <input name="submitNewBin" type="submit" class="inputBtns" value='<fmt:message key="label.createnewbin"/>' id="submitNewBin" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="addBin(); return false;">
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
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<c:if test="${cabinetDefinitionColl != null}" >
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
        <input name="action" id="action" type="hidden">
    </div>
    <div class="dataContent">
    <table width="100%" class="tableResults">
    <c:set var="rowClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="workareaBean" items="${cabinetDefinitionColl}" varStatus="status">
    <c:if test="${status.index % 10 == 0}">
      <tr>
        <th><fmt:message key="label.workarea"/></th>
        <th><fmt:message key="label.cabinet"/></th>
        <th><fmt:message key="label.bin"/></th>
        <th><fmt:message key="label.catalog"/></th>
        <th><fmt:message key="label.partnumber"/></th>
        <th><fmt:message key="label.item"/></th>
        <th><fmt:message key="label.description"/></th>
      </tr>
    </c:if>

      <tr class='<c:out value="${rowClass}"/>'>
        <td rowspan='<c:out value="${workareaBean.workareaRowspan}"/>'><c:out value="${workareaBean.useApplication}"/></td>
        <c:set var="firstWorkAreaRow" value="${true}"/>
       <c:forEach var="cabinetBean" items="${workareaBean.cabinets}">

          <c:if test='${! firstWorkAreaRow}'>
      <tr class='<c:out value="${rowClass}"/>'>
          </c:if>

          <td rowspan='<c:out value="${cabinetBean.cabinetRowspan}"/>'><c:out value="${cabinetBean.cabinetName}"/></td>
          <c:set var="firstCabinetRow" value="${true}"/>
          <c:forEach var="binBean" items="${cabinetBean.bins}">

            <c:if test='${! firstCabinetRow}'>
      <tr class='<c:out value="${rowClass}"/>'>
            </c:if>

            <td><c:out value="${binBean.binName}"/>&nbsp;</td>
            <td><c:out value="${binBean.catalogId}"/>&nbsp;</td>
            <td><c:out value="${binBean.catPartNo}"/>&nbsp;</td>
            <td><c:out value="${binBean.itemId}"/>&nbsp;</td>
            <td><c:out value="${binBean.materialDesc}"/> <c:out value="${binBean.mfgDesc}"/></td>
            <c:set var="dataCount" value='${dataCount+1}'/>

            <c:choose>
              <c:when test="${dataCount % 2 == 0}" >
                <c:set var="rowClass" value=''/>
              </c:when>
              <c:otherwise>
                <c:set var="rowClass" value='alt'/>
              </c:otherwise>
            </c:choose>
           </tr>
           <c:set var="firstCabinetRow" value="${false}"/>
          </c:forEach>
          <c:set var="firstWorkAreaRow" value="${false}"/>
       </c:forEach>
    </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty cabinetDefinitionColl}">
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

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

