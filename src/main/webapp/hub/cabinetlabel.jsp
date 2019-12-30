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
<script src="/js/hub/cabinetlabel.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
var altHubId = new Array(
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.preferredWarehouse}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.preferredWarehouse}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.preferredWarehouse}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>

  altFacilityId["<c:out value="${currentHub}"/>"] = new Array(
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
    <c:choose>
      <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.facilityId}"/>"
      </c:when>
      <c:otherwise>
        "<c:out value="${status1.current.facilityId}"/>"
      </c:otherwise>
    </c:choose>
  </c:forEach>
  );

  altFacilityName["<c:out value="${currentHub}"/>"] = new Array(
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
    <c:choose>
      <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.facilityName}"/>"
      </c:when>
      <c:otherwise>
        "<c:out value="${status1.current.facilityName}"/>"
      </c:otherwise>
    </c:choose>
  </c:forEach>
  );
</c:forEach>

var altApplicationId = new Array();
var altApplicationDesc = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.preferredWarehouse}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
    <c:set var="currentFacility" value='${status1.current.facilityId}'/>
    <c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>

      altApplicationId["<c:out value="${currentFacility}"/>"] = new Array(""
      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.application}"/>"
      </c:forEach>
      );

      altApplicationDesc["<c:out value="${currentFacility}"/>"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.applicationDesc}" escapeXml="false"/>"
      </c:forEach>
      );

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
<fmt:message key="cabinetlabel.title"/>
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


<tcmis:form action="/cabinetlabel.do" onsubmit="return submitOnlyOnce();">

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
<fmt:message key="cabinetlabel.title"/>
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
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table border="0" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr valign="MIDDLE">
        <td width="15%" class="optionTitleBoldRight">
          <fmt:message key="label.hub"/>:
        </td>
        <td width="40%" class="optionTitleBoldLeft">
          <c:set var="selectedHub" value='${param.preferredWarehouse}'/>
          <select name="preferredWarehouse" id="preferredWarehouse" class="selectBox" onchange="hubChanged()">
          <c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.preferredWarehouse}'/>

            <c:choose>
              <c:when test="${empty selectedHub}" >
                <c:set var="selectedHub" value='${status.current.preferredWarehouse}'/>
                <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
              </c:when>
              <c:when test="${currentHub == selectedHub}" >
                <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.preferredWarehouse}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.preferredWarehouse}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
        </td>
        <td width="10%" class="optionTitleBoldRight">
&nbsp;
        </td>
        <td width="25%" class="optionTitleBoldLeft">
&nbsp;
        </td>
      </tr>
      <tr>
        <td width="15%" class="optionTitleBoldRight">
          <fmt:message key="label.facility"/>:
        </td>
        <td width="40%" class="optionTitleBoldLeft">
          <c:set var="selectedFacility" value='${param.facilityId}'/>
          <select class="selectBox"  name="facilityId" id="facilityId" size="1" onchange="facilityChanged()">
            <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status">
              <c:set var="currentFacility" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${empty selectedFacility}" >
                  <c:set var="selectedFacility" value='${status.current.facilityId}'/>
                  <c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>
                </c:when>
                <c:when test="${currentFacility == selectedFacility}" >
                  <c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>
                </c:when>
              </c:choose>
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

         <td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.sortby"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft">
<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
  <html:option value="cabinetId" key="label.cabinetid"/>
  <html:option value="cabinetDescription" key="label.cabinetdescription"/>
  <html:option value="application" key="label.workarea"/>
</html:select>

        </td>
      </tr>
      <tr>
        <td width="15%" class="optionTitleBoldRight">
          <fmt:message key="label.workarea"/>:
        </td>
        <td width="40%" class="optionTitleBoldLeft">
          <c:set var="selectedApplication" value='${param.application}'/>
          <select class="selectBox"  name="application" id="application" size="1">
            <option value=""><fmt:message key="label.all"/></option>
            <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status">
              <c:set var="currentApplication" value='${status.current.application}'/>
              <c:choose>
                <c:when test="${empty selectedApplication}" >
                  <c:set var="selectedApplication" value=''/>
                </c:when>
              </c:choose>
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

        <td width="10%" class="optionTitleBoldLeft">
&nbsp;
        </td>
        <td width="25%" class="optionTitleBoldLeft">
&nbsp;
        </td>
      </tr>
      <tr>
        <td colspan="4" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="document.pressed='search'">
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
<%-- </tcmis:form> --%>
<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<%-- <tcmis:form action="/label.do?baseLineButton=baseLineButton" target="cabinetbinwindow" onsubmit="return submitOnlyOnce();">--%>
<c:if test="${searchResultBeanCollection != null}" >
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

          <input name="size" id="size" type="radio" class="radioBtns" value="small" checked>
<fmt:message key="cabinetlabel.label.smallradio"/>
          <input name="size" id="size" type="radio" class="radioBtns" value="large">
<fmt:message key="cabinetlabel.label.largeradio"/>
|
<a href="javascript:generate('baseLineButton')"><fmt:message key="cabinetlabel.label.generatebaseline"/></a>
|
<a href="javascript:generate('generateCabinetLabelButton')"><fmt:message key="cabinetlabel.label.generatecabinetlabel"/></a>
|
<a href="javascript:generate('generateCabinetBinLabelButton')"><fmt:message key="cabinetlabel.label.generatecabinetbinlabel"/></a>
|
<a href="javascript:generate('generateAllLabelButton')"><fmt:message key="cabinetlabel.label.generatealllabel"/></a>

    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
<bean:size collection="${searchResultBeanCollection}" id="resultSize"/>
    <c:forEach var="resultBean" items="${searchResultBeanCollection}" varStatus="status">

    <c:if test="${status.index == 0}">
        <tr>
          <th width="10%"><fmt:message key="label.print"/> <br><input name="allCheck" id="allCheck" type="checkbox" class="" value="all" onclick="checkAll(<c:out value="${resultSize}"/>);"></th>
          <th width="10%"><fmt:message key="label.cabinetid"/></th>
          <th width="40%"><fmt:message key="label.cabinetdescription"/></th>
          <th width="40%"><fmt:message key="label.workarea"/></th>
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
          <td width="10%">
<input type="hidden" name="cabinetId<c:out value="${status.index}"/>" value="<c:out value="${status.current.cabinetId}"/>">
<input name="myCheckboxArray<c:out value="${status.index}"/>" id="checkbox<c:out value="${status.index}"/>" type="checkbox" class="" value="<c:out value="${status.index}"/>">
</td>
          <td width="10%"><a href="javascript:showBinDetails('<tcmis:padding value="${status.current.cabinetId}" length="8" paddingCharacter="0" paddingSide="left"/>','<c:out value="${status.current.preferredWarehouse}"/>')"><tcmis:padding value="${status.current.cabinetId}" length="8" paddingCharacter="0" paddingSide="left"/></a>&nbsp;</td>
          <td width="40%"><c:out value="${status.current.cabinetName}"/>&nbsp;</td>
          <td width="40%"><c:out value="${status.current.application}"/>&nbsp;</td>
        </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty searchResultBeanCollection}" >

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

