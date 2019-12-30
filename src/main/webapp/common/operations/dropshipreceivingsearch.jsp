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
<script type="text/javascript" src="/js/common/operations/dropshipreceiving.js"></script>

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
<fmt:message key="dropshipReceiving"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var altCompanyId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myFacilityDockCollection}" varStatus="status">
  <c:choose>
    <c:when test="${status.index == 0}">
      "<c:out value="${status.current.companyId}" escapeXml="false"/>"
    </c:when>
    <c:otherwise>
      ,"<c:out value="${status.current.companyId}" escapeXml="false"/>"
    </c:otherwise>
  </c:choose>
</c:forEach>
);

var altCompanyName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myFacilityDockCollection}" varStatus="status">
  <c:choose>
    <c:when test="${status.index == 0}">
      "<c:out value="${status.current.companyName}" escapeXml="false"/>"
    </c:when>
    <c:otherwise>
      ,"<c:out value="${status.current.companyName}" escapeXml="false"/>"
    </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityId = new Array();
var altFacilityName = new Array();
  <c:forEach var="myWorkareaFacilityViewBean" items="${myFacilityDockCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    altFacilityId["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.facilityId}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
    </c:forEach>
    );

    altFacilityName["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.facilityName}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
    </c:forEach>
    );
  </c:forEach>

var altDock = new Array();
var altDockDesc = new Array();
  <c:forEach var="myWorkareaFacilityViewBean" items="${myFacilityDockCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
      <c:set var="currentFacilityId" value='${status1.current.facilityId}'/>
      <c:set var="dockListCollection" value='${status1.current.dockList}'/>

      <bean:size id="dockSize" name="dockListCollection"/>
      altDock["<c:out value="${currentFacilityId}" escapeXml="false"/>"] = new Array(
      <c:forEach var="facilityDockObjBean" items="${dockListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && dockSize > 1}">
            "All Docks","<c:out value="${status2.current.dockLocationId}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.dockLocationId}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.dockLocationId}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );

      altDockDesc["<c:out value="${currentFacilityId}" escapeXml="false"/>"] = new Array(
      <c:forEach var="facilityDockObjBean" items="${dockListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && dockSize > 1}">
            "<fmt:message key="label.alldocks"/>","<c:out value="${status2.current.dockDesc}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<c:out value="${status2.current.dockDesc}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status2.current.dockDesc}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );
    </c:forEach>
  </c:forEach>

  var searchMyArr = new Array(
			{value:'LIKE', text: '<fmt:message key="label.contain"/>'}
			,{value:'IS', text: '<fmt:message key="label.is"/>'}
			,{value:'STARTSWITH', text: '<fmt:message key="label.startswith"/>'}
			,{value:'ENDSWITH', text: '<fmt:message key="label.endswith"/>'}
		);

// -->
</script>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/dropshipreceivingresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="760" border="0" cellpadding="0" cellspacing="0">
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
      <%-- row 1 --%>
      <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
      <td width="30%">
         <c:set var="selectedCompanyId" value='${param.companyId}'/>
         <select name="companyId" id="companyId" onchange="companyChanged()" class="selectBox">
             <c:forEach var="myWorkareaFacilityViewBean" items="${myFacilityDockCollection}" varStatus="status">
               <c:set var="currentCompanyId" value='${status.current.companyId}'/>
                 <c:if test="${empty selectedCompanyId}" >
                   <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
                   <c:set var="facilityCollection" value='${status.current.facilityList}'/>
                 </c:if>
                 <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}"/></option>
             </c:forEach>
         </select>
      </td>

       <td width="10%" class="optionTitleBoldRight">
         <fmt:message key="label.searchby"/>:
       </td>
       <td width="45%" class="optionTitleBoldLeft">
        <c:set var="selectedSearchBy" value='${param.searchWhat}'/>
        <select name="searchWhat" id="searchWhat" class="selectBox" onchange="changeSearchTypeOptions(this.value)">
          <option value="itemDescription" selected><fmt:message key="label.itemdesc"/></option>
          <option value="itemId"><fmt:message key="label.itemid"/></option>
          <option value="mrNumber"><fmt:message key="label.mrnumber"/></option>
		  <option value="radianPo" selected><fmt:message key="label.haaspo"/></option>
        </select>

        <c:set var="selectedSearchType" value='${param.searchType}'/>
        <select name="searchType" id="searchType" class="selectBox">
          <option value="IS" selected><fmt:message key="label.is"/></option>
          <option value="STARTSWITH"><fmt:message key="label.startswith"/></option>
        </select>

        <input class="inputBox" type="text" name="searchText" id="searchText" value="<c:out value='${param.searchText}'/>" size="10">
       </td>

      </tr>

      <%-- row 2 --%>
      <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
      <td width="30%">
         <c:set var="selectedFacilityId" value='${param.facilityId}'/>
         <select name="facilityId" id="facilityId" onchange="facilityChanged()" class="selectBox">
             <c:forEach var="facilityOvBean" items="${facilityCollection}" varStatus="status">
               <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
                 <c:if test="${empty selectedFacilityId}" >
                  <c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
                  <c:set var="dockSelectBeanCollection" value='${status.current.dockList}'/>
                 </c:if>
                 <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}" escapeXml="false"/></option>
             </c:forEach>
         </select>
      </td>

	<td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.sort"/>:
	</td>
	<td width="45%">
        <c:set var="selectedSortBy" value='${param.sortBy}'/>
        <select name="sortBy" id="sortBy" class="selectBox">
          <option value="EXPECTED,LAST_SUPPLIER" selected><fmt:message key="label.dateexptdsupplier"/></option>
          <option value="MR_NUMBER,MR_LINE_ITEM"><fmt:message key="label.mrline"/></option>
          <option value="RADIAN_PO,LINE_ITEM"><fmt:message key="label.popoline"/></option>
          <option value="LAST_SUPPLIER,EXPECTED"><fmt:message key="label.supplierdateexptd"/></option>
        </select>
	</td>

      </tr>
      <%-- row 3 --%>
      <tr>
       <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.shipto"/>:</td>
       <td width="30%">
          <c:choose>
           <c:when test="${dockSelectBeanCollection == null}">
             <c:set var="dockSize" value="0"/>
           </c:when>
           <c:otherwise>
             <bean:size id="dockSize" name="dockSelectBeanCollection"/>
           </c:otherwise>
          </c:choose>
          <c:set var="selectedDockLocationIdId" value='${param.dockId}'/>
          <select name="dockId" id="dockId" class="selectBox">
            <c:choose>
              <c:when test="${dockSize == 0}">
                <option value="All Docks"><fmt:message key="label.alldocks"/></option>
              </c:when>
              <c:otherwise>
                <c:if test="${dockSize > 1}">
                  <option value="All Docks" selected><fmt:message key="label.alldocks"/></option>
                </c:if>
                <c:forEach var="facilityDockObjBean" items="${dockSelectBeanCollection}" varStatus="status">
                  <c:set var="currentDockLocationIdId" value='${status.current.dockLocationId}'/>
                  <option value="<c:out value="${currentDockLocationIdId}"/>"><c:out value="${status.current.dockDesc}"/></option>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </select>
       </td>
       <td>&nbsp;</td>
       <td width="30%">
          <b><fmt:message key="label.exptdwithin"/></b>&nbsp;
	  <input class="inputBox" type="text" name="expectedWithin" id="expectedWithin" value="30" size="1"/>
	  &nbsp;<b><fmt:message key="label.days"/></b>
       </td>

      </tr>

    <%-- buttons --%>
    <tr>
      <td width="5%" colspan="2" class="optionTitleBoldLeft">
        <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
        
        <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">

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
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>