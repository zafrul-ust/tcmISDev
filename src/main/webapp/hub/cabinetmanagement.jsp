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

<link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>


<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/cabinetmanagement.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
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
<fmt:message key="cabinetmanagement.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();">


<tcmis:form action="/cabinetmanagement.do" onsubmit="return submitOnlyOnce();">

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
<fmt:message key="cabinetmanagement.title"/>
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
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table border="0" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr >
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.hub"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft">
          <input name="hubName" id="hubName" type="hidden">
          <select name="branchPlant" id="branchPlant" class="selectBox" onchange="hubChanged()">
          <c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}" escapeXml="false"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}" escapeXml="false"/></option>
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
                  <option value="<c:out value="${currentCabinet}"/>"><c:out value="${status.current.cabinetName}" escapeXml="false"/></option>
                </c:when>
                <c:otherwise>
                  <c:set var="selectedFlag" value=''/>
                  <c:forEach items="${selectedCabinetIdArray}" varStatus="status1">
                    <c:set var="selectedCabinet" value='${selectedCabinetIdArray[status1.index]}'/>
                    <c:if test="${currentCabinet == selectedCabinet}">
                      <c:set var="selectedFlag" value='selected'/>
                    </c:if>
                  </c:forEach>
                  <option value="<c:out value="${currentCabinet}"/>" <c:out value="${selectedFlag}"/>><c:out value="${status.current.cabinetName}" escapeXml="false"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>
        <td width="20%" class="optionTitleBoldRight">
<html:select property="itemOrPart" styleId="itemOrPart" styleClass="selectBox">
<html:option value="part" key="label.partnumber"/>
<html:option value="item" key="label.itemid"/>
</html:select>
<html:select property="criterion" styleId="criterion" styleClass="selectBox">
<html:option value="contains" key="label.contain"/>
<html:option value="is" key="label.is"/>
</html:select>
        </td>
        <td width="10%" class="optionTitleBoldLeft">
<input class="inputBox" type="text" name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" size="15">
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
                  <option value="<c:out value="${currentFacility}"/>" selected><c:out value="${status.current.facilityName}" escapeXml="false"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentFacility}"/>"><c:out value="${status.current.facilityName}" escapeXml="false"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>

         <td width="20%" class="optionTitleBoldRight">
<fmt:message key="label.sortby"/>:
        </td>
        <td width="10%" class="optionTitleBoldLeft">
<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
  <html:option value="cabinetId" key="label.cabinetid"/>
  <html:option value="partNumber" key="label.partnumber"/>
  <html:option value="itemId" key="label.itemid"/>
</html:select>
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
                  <option value="<c:out value="${currentApplication}"/>" selected><c:out value="${status.current.applicationDesc}" escapeXml="false"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentApplication}"/>"><c:out value="${status.current.applicationDesc}" escapeXml="false"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>

        <td width="20%" class="optionTitleBoldRight">
<input name="inactive" id="inactive" type="checkbox" class="radioBtns" value="inactive"
<c:if test="${!empty param.inactive}">
checked
</c:if>
>
          <fmt:message key="cabinetmanagement.label.showinactivebins"/>
        </td>
        <td width="10%" class="optionTitleBoldRight">
&nbsp;
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateForm();">
        </td>
        <td class="optionTitleBoldLeft">
          <input name="submitExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        </td>
        <td colspan="4" class="optionTitleBoldLeft">
          <input name="submitNewBin" id="submitNewBin" type="submit" class="inputBtns" value="<fmt:message key="label.createnewbin"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="addBin(); return false;">
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

<c:if test="${cabinetPartLevelViewBeanCollection != null}" >
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
      <c:if test="${!empty cabinetPartLevelViewBeanCollection}" >
 <tcmis:facilityPermission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
<a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
 </tcmis:facilityPermission>

    </c:if>
    </div>
    <div class="dataContent">
    <table width="100%" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="cabinetPartLevelViewBean" items="${cabinetPartLevelViewBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
        <tr>
          <th width="5%"><fmt:message key="label.item"/></th>
          <th width="20%"><fmt:message key="label.description"/></th>
          <th width="20%"><fmt:message key="label.containersize"/></th>
          <th width="5%"><fmt:message key="label.catalogid"/></th>
          <th width="5%"><fmt:message key="label.cabinet"/></th>
          <th width="10%"><fmt:message key="label.bin"/></th>
          <th width="5%"><fmt:message key="label.partnumber"/></th>
          <th width="10%"><fmt:message key="label.msdsnumber"/></th>
          <th width="5%"><fmt:message key="label.stockinglevel"/></th>
          <th width="5%"><fmt:message key="label.reorderpoint"/></th>
          <th width="5%"><fmt:message key="label.status"/></th>
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
<c:if test="${status.current.status != 'A'}">
<c:set var="rowClass" value='class=black'/>
</c:if>
        <tr <c:out value="${rowClass}"/> id="row<c:out value="${status.index}"/>">
          <td width="5%">
<input type="hidden" name="cabinetId<c:out value="${status.index}"/>" value="<c:out value="${status.current.cabinetId}"/>">
<%--
<input name="myCheckboxArray<c:out value="${status.index}"/>" id="checkbox<c:out value="${status.index}"/>" type="checkbox" class="radioBtns" value="<c:out value="${status.index}"/>">

--%>
<a href="#" onclick="changeMinMaxCabinet('<c:out value="${status.current.sourceHub}"/>', '<c:out value="${status.current.facilityId}" escapeXml="false"/>','<c:out value="${status.current.itemId}"/>','<c:out value="${status.current.cabinetId}"/>','<c:out value="${status.current.binId}"/>','<c:out value="${status.current.cabinetName}"/>','<c:out value="${status.current.useApplication}"/>', '<c:out value="${status.current.catPartNo}"/>', '<c:out value="${status.current.companyId}"/>', '<c:out value="${status.current.catalogId}"/>', '<c:out value="${status.current.partGroupNo}"/>', '<c:out value="${status.current.status}"/>'); return false;">
<c:out value="${status.current.itemId}"/></a>&nbsp;
</td>
          <td width="20%"><c:out value="${status.current.materialDesc}"/>&nbsp;</td>
          <td width="20%"><c:out value="${status.current.packaging}"/>&nbsp;</td>
          <td width="5%"><c:out value="${status.current.catalogId}"/>&nbsp;</td>
          <td width="5%"><c:out value="${status.current.cabinetName}"/>&nbsp;</td>
          <td width="10%"><c:out value="${status.current.binName}"/>&nbsp;</td>
          <td width="5%"><c:out value="${status.current.catPartNo}"/>&nbsp;</td>
          <td width="10%"><c:out value="${status.current.materialIdString}"/>&nbsp;</td>
          <td width="5%"><c:out value="${status.current.stockingLevel}"/>&nbsp;</td>
          <td width="5%"><c:out value="${status.current.reorderPoint}"/>&nbsp;</td>
          <td width="5%">
<input type="hidden" name="cabinetManagementInputBean[<c:out value="${status.index}"/>].binId" value="<c:out value="${status.current.binId}"/>">
<input type="hidden" name="cabinetManagementInputBean[<c:out value="${status.index}"/>].oldStatus" value="<c:out value="${status.current.status}"/>">
<tcmis:permission indicator="true" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
<input type="checkbox" name="cabinetManagementInputBean[<c:out value="${status.index}"/>].status" id="cabinetManagementInputBean[<c:out value="${status.index}"/>].status"class="radioBtns" value="A"

<c:if test="${status.current.status == 'A'}">
checked
</c:if>
 onclick="checkActiveStatus(<c:out value="${status.index}"/>);">
 </tcmis:permission>
<tcmis:permission indicator="false" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
   <input type="hidden" name="cabinetManagementInputBean[<c:out value="${status.index}"/>].status" value="<c:out
value="${status.current.status}"/>">
  <c:if test="${status.current.status == 'A'}">
    <img src="/images/item_chk1.gif" border=0 alt="Active" align="">
  </c:if>
  <c:if test="${status.current.status != 'A'}">
    <img src="/images/item_chk0.gif" border=0 alt="Active" align="">
  </c:if>
 </tcmis:permission>
<%--
<c:if test="${status.current.status == 'A'}">
checked
</c:if>

 <tcmis:facilityPermission indicator="false" userGroupId="CabinetMgmt" facilityId="${param.hubName}">
 disabled
 </tcmis:facilityPermission>
 onclick="checkActiveStatus(<c:out value="${status.index}"/>);">
--%>

          &nbsp;</td>
        </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty cabinetPartLevelViewBeanCollection}" >

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
 <div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

