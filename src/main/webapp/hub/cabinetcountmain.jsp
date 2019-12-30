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

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/cabinetcount.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
var facilityPermissionArray = new Array();
<c:set var="counter" value='${0}'/>
<c:forEach var="hubBean" items="${personnelBean.permissionBean.userGroupMemberBeanCollection}" varStatus="status">
  <c:if test="${status.current.userGroupId == 'CabinetMgmt'}">
    facilityPermissionArray['<c:out value="${counter}"/>'] = '<tcmis:jsReplace value="${status.current.facilityId}" processMultiLines="false"/>';
    <c:set var="counter" value='${counter + 1}'/>
  </c:if>
</c:forEach>


var altHubName = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  altHubName['<tcmis:jsReplace value="${status.current.branchPlant}"/>'] = '<tcmis:jsReplace value="${status.current.hubName}"/>';
</c:forEach>

var altHubId = new Array(
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,'<tcmis:jsReplace value="${status.current.branchPlant}"/>'
   </c:when>
   <c:otherwise>
    '<tcmis:jsReplace value="${status.current.branchPlant}"/>'
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>

  altFacilityId['<tcmis:jsReplace value="${currentHub}"/>'] = new Array(
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.facilityId}" processMultiLines="false"/>'
   </c:when>
   <c:otherwise>
        '<tcmis:jsReplace value="${status1.current.facilityId}" processMultiLines="false"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altFacilityName['<tcmis:jsReplace value="${currentHub}"/>'] = new Array(
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.facilityName}" processMultiLines="false"/>'
   </c:when>
   <c:otherwise>
        '<tcmis:jsReplace value="${status1.current.facilityName}" processMultiLines="false"/>'
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

      altApplicationId['<tcmis:jsReplace value="${currentHub}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentFacility}" processMultiLines="false"/>'] = new Array(''
      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
        ,'<tcmis:jsReplace value="${status2.current.application}" processMultiLines="false"/>'
      </c:forEach>
      );

      altApplicationDesc['<tcmis:jsReplace value="${currentHub}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentFacility}" processMultiLines="false"/>'] = new Array('<fmt:message key="label.all"/>'
      <c:forEach var="applicationBean" items="${applicationBeanCollection}" varStatus="status2">
        ,'<tcmis:jsReplace value="${status2.current.applicationDesc}" processMultiLines="false"/>'
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
    altCabinetIdArray['<tcmis:jsReplace value="${currentHub}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentFacility}" processMultiLines="false"/>-All'] = new Array(''
    <c:forEach var="myBean" items="${sortedCabinetBeanForAllWorkAreasCollection}" varStatus="status2">
      ,'<tcmis:jsReplace value="${status2.current.cabinetId}"/>'
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

    altCabinetNameArray['<tcmis:jsReplace value="${currentHub}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentFacility}" processMultiLines="false"/>-All'] = new Array('<fmt:message key="label.all"/>'
    <c:forEach var="myBean" items="${sortedCabinetBeanForAllWorkAreasCollection}" varStatus="status2">
      ,'<tcmis:jsReplace value="${status2.current.cabinetName}" processMultiLines="true"/>'
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


      altCabinetIdArray['<tcmis:jsReplace value="${currentHub}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentFacility}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentApplication}" processMultiLines="false"/>'] = new Array(''
      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
        ,'<tcmis:jsReplace value="${status3.current.cabinetId}"/>'
      </c:forEach>
      );

      altCabinetNameArray['<tcmis:jsReplace value="${currentHub}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentFacility}" processMultiLines="false"/>-<tcmis:jsReplace value="${currentApplication}" processMultiLines="false"/>'] = new Array('<fmt:message key="label.all"/>'
      <c:forEach var="cabinetBean" items="${cabinetBeanCollection}" varStatus="status3">
        ,'<tcmis:jsReplace value="${status3.current.cabinetName}" processMultiLines="true"/>'
      </c:forEach>
      );
    </c:forEach>
  </c:forEach>
</c:forEach>
// -->
</script>


<title>
<fmt:message key="label.cabinetcount"/>
</title>
<script language="JavaScript" type="text/javascript"> 
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
notAllCabinets:"<fmt:message key="label.pleasedonotselectallcabinets"/>"};

var searchMyArr = new Array(
		{value:'contains', text: '<fmt:message key="label.contain"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
	);
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','cabinetCount');myOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/cabinetcountresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

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
      <c:set var="selectedFacility" value='${status.current.facilityId}'/>
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
<%--<c:set var="selectedFacility" value='${param.facilityId}'/>--%>
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

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
		<table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
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
        <td width="15%" rowspan="3" class="optionTitleBoldLeft">
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
        <td width="20%" >
        
        </td>
        </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.facility"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft">
          <c:set var="selectedFacilityName" value='${param.facilityName}'/>
          <select class="selectBox"  name="facilityId" id="facilityId" size="1" onchange="facilityChanged()">
            <c:forEach var="facilityBean" items="${initFacilityBeanCollection}" varStatus="status">
              <c:set var="currentFacility" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${currentFacility == selectedFacility}">
                  <option value="<c:out value="${currentFacility}"/>" selected><c:out value="${status.current.facilityName}" escapeXml="false"/></option>
                  <c:set var="selectedFacilityName" value='${status.current.facilityName}'/>
                </c:when>
                <c:otherwise>
                  <option value="<c:out value="${currentFacility}"/>"><c:out value="${status.current.facilityName}" escapeXml="false"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </td>

         <td width="20%" >
        </td>
        <td width="10%" >
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.workarea"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft">
          <select class="selectBox"  name="application" id="application" size="1"  onchange="applicationChanged()">
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

        <td width="20%" >
        </td>
        <td width="10%" >
        </td>
      </tr>
      <tr>
      <td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.search"/>:&nbsp;
 	  </td>
      <td  class="optionTitleBoldLeft" colspan="3">
					<select name="searchField" class="selectBox" id="searchField" onchange="changeSearchTypeOptions(this.value);">
						<option value="itemId"><fmt:message key="label.itemid"/></option>
						<option value="description"><fmt:message key="label.description"/></option>
					</select>
					<select name="searchMode" class="selectBox" id="searchMode">
						<option value="is"><fmt:message key="label.is" /></option>
                 	</select>
                 	
         <input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='<c:out value="${param.searchArgument}"/>' size="25" />
		</td>
		
      </tr>
      <tr>
        <td class="optionTitleBoldRight">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
        </td>
        <td class="optionTitleBoldLeft">
          <input name="submitExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        </td>
      </tr>
      </table>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="facilityName" id="facilityName" value="<c:out value="${selectedFacilityName}"/>" type="hidden">
<input name="hubName" id="hubName" value="" type="hidden">
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="150">
</div>
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> 
    <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
         <span id="updateResultLink" style="display: none"> 
         <a href="#" onclick="call('submitMainUpdate'); return false;"><fmt:message key="label.update"/></a>
         </span>     
       </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>

  	  <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>  
</div><!-- Result Frame Ends -->

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>


</body>
</html>
      		