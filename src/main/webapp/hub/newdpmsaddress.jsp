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

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
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
<script language="JavaScript" type="text/javascript">
<!--
var altCountryId = new Array(
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.countryAbbrev}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.countryAbbrev}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altState = new Array();
var altStateName = new Array();
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
  <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
  <c:set var="stateCollection" value='${status.current.stateCollection}'/>

  altState["<c:out value="${currentCountry}"/>"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.stateAbbrev}"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.stateAbbrev}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altStateName["<c:out value="${currentCountry}"/>"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.state}"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.state}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

// -->
</script>
<script type="text/javascript" src="/js/hub/dpmsaddress.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="dpmsaddress.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="newAddressLoad();">

<tcmis:form action="/dpmsaddress.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

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
        <td class="optionTitleBoldLeft"><fmt:message key="label.dodaac"/>:</td>
        <td>

          <input name="dodaac" id="dodaac" type="text" class="inputBox" maxlength="6"
                 <c:if test="${type == 'nol'}">
                  value="NOL"
                 onfocus="blur();"
                 </c:if>
                  >

       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.dodaactype"/>:</td>
        <td>
          <select name="dodaacType" id="dodaacType" class="selectBox">
 <option value="POE" selected>POE</option>
 <option value="DODAAC">DODAAC</option>
 <option value="NOL">NOL</option>
<%--
           <c:if test="${type == 'nol'}">
            <option value="NOL" selected>NOL</option>
           </c:if>
           <c:if test="${type == 'dpms'}">
            <option value="DPMS" selected>DPMS</option>
           </c:if>
           <c:if test="${type == 'nododaac'}">
            <option value="POE" selected>POE</option>
           </c:if>
--%>
          </select>
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.address1"/>:</td>
        <td>
          <input name="address1" id="address1" type="text" size="40" class="inputBox" maxlength="35">
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.address2"/>:</td>
        <td>
          <input name="address2" id="address2" type="text" size="40" class="inputBox" maxlength="35">
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.address3"/>:</td>
        <td>
          <input name="address3" id="address3" type="text" size="40" class="inputBox" maxlength="35">
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.address4"/>:</td>
        <td>
          <input name="address4" id="address4" type="text" size="40" class="inputBox" maxlength="35">
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.city"/>:</td>
        <td>
          <input name="city" id="city" type="text" class="inputBox" >
       </td>
      </tr>
       <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.zip"/>:</td>
        <td>
          <input name="zip" id="zip" type="text" class="inputBox" >
       </td>
      </tr>
       <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.state"/>:</td>
        <td>
          <select name="stateAbbrev" id="stateAbbrev" class="selectBox">
            <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status">
              <c:set var="currentState" value='${status.current.stateAbbrev}'/>
              <c:choose>
                <c:when test="${currentState == selectedState}">
                  <option value="<c:out value="${currentState}"/>" selected><c:out value="${status.current.state}"/></option>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentState}"/>"><c:out value="${status.current.state}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>   
       </td>
      </tr>
       <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.country"/>:</td>
        <td>
         <select name="countryAbbrev" id="countryAbbrev" class="selectBox" onchange="countryChanged();">
          <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">
            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>

            <c:choose>
              <c:when test="${empty selectedCountry}" >
                <c:set var="selectedCountry" value='${status.current.countryAbbrev}'/>
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
              <c:when test="${currentCountry == selectedCountry}" >
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
            </c:choose>
            <c:choose>
              <c:when test="${currentCountry == selectedCountry}">
                <option value="<c:out value="${currentCountry}"/>" selected><c:out value="${status.current.country}"/></option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentCountry}"/>"><c:out value="${status.current.country}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          </select>
       </td>
      </tr>

      <tr>
      <td>
<input name="addNewAddress" id="addNewAddress" type="submit" value="<fmt:message key="label.add"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitAddForm(); return true;">

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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->
<%--
<c:if test="${custPoAddressChangeViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="custPoAddressChangeViewBean" items="${custPoAddressChangeViewBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.example1"/></th>
    <th width="5%"><fmt:message key="label.example2"/></th>
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
     <td width="5%"><c:out value="${status.current.addressChangeReuqestId}"/></td>
     <td width="5%"><c:out value="${status.current.origShiptToDodaac}"/></td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty custPoAddressChangeViewBeanCollection}" >

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
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->
--%>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
