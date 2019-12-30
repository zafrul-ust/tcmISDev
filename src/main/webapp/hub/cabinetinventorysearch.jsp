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
<%@ include file="/common/rightclickmenudata.jsp" %>

<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/searchiframeresize.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>


<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/cabinetinventory.js" language="JavaScript"></script>
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
var altCompanyId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubBean" items="${hubCabinetViewBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="facilityBeanCollection" value='${status.current.facilityBeanCollection}'/>

  altCompanyId["<c:out value="${currentHub}"/>"] = new Array(
  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.companyId}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.companyId}" escapeXml="false"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

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
<fmt:message key="label.cabinetinventory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",all:"<fmt:message key="label.all"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
pleaseDoNotSelectAllCabinets:"<fmt:message key="label.pleasedonotselectallcabinets"/>",
pleaseSelectACabinet:"<fmt:message key="label.pleaseselectacabinet"/>",
expireInFromDaysInteger:"<fmt:message key="label.expireinfromdays"/> <fmt:message key="label.mustbeaninteger"/>",
expireInToDaysInteger:"<fmt:message key="label.expireintodays"/> <fmt:message key="label.mustbeaninteger"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnLoad();">

<tcmis:form action="/cabinetinventoryresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

 <div class="interface" id="searchMainPage">

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
    <table width="850" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
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
        <td width="10%" class="optionTitleBoldRight">
			<html:select property="searchField" styleId="searchField" styleClass="selectBox">
				<html:option value="CAT_PART_NO" key="label.partnumber"/>
        <html:option value="ITEM_DESC" key="label.itemdesc"/>        
        <html:option value="ITEM_ID" key="label.itemid"/>
			</html:select>
		</td>
        <td width="20%" class="optionTitleBoldLeft" colspan="2">
		
			<html:select property="matchingMode" styleId="matchingMode" styleClass="selectBox">
				<html:option value="contains" key="label.contain"/>
				<html:option value="is" key="label.is"/>
			</html:select>
			<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value="<c:out value="${param.searchArgument}"/>" size="15">
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
				<td width="5%" class="optionTitleBoldRight">
					<fmt:message key="label.expiresin" />
				</td>
				<td width="5%" class="optionTitleBoldLeft" colspan="3">
					<input type="text" class="inputBox" name="expireInFrom" id="expireInFrom" value="" size="2" maxLength="4"/>
					<fmt:message key="label.to"/>
					<input type="text" class="inputBox" name="expireInTo" id="expireInTo" value="" size="2" maxLength="4"/> 
					<fmt:message key="label.days" />
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
        
         <td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.sortby"/>:
        </td>
        <td width="20%" class="optionTitleBoldLeft" colspan="2">
			<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
  				<html:option value="CABINET_ID" key="label.cabinetid"/>
  				<html:option value="CAT_PART_NO" key="label.partnumber"/>
  				<html:option value="ITEM_ID" key="label.itemid"/>
			</html:select>
        </td>
        
      </tr>
      <tr>
        <td class="optionTitleBoldRight">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
        </td>
        <td class="optionTitleBoldLeft" colspan="4">
          <input name="submitExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        </td>
        <td class="optionTitleBoldRight">
        			<input type="checkBox" class="radioBtns" name="positiveQ" id="positiveQ" value="Yes"/>
        </td>
        <td class="optionTitleLeft">
					<fmt:message key="label.showonlypositiveq"/>
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
<input name="action" id="action" type="hidden">
<input name="facilityName" id="facilityName" value="<c:out value="${selectedFacilityName}"/>" type="hidden">
<input name="companyId" id="companyId" value="" type="hidden">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

