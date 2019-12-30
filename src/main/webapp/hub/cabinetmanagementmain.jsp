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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/cabinetmanagement.js"></script> 

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- Include this so I can submit grid thru form -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<title>
	<fmt:message key="cabinetmanagement.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
			itemInteger:"<fmt:message key="error.item.integer"/>",
			errors:"<fmt:message key="label.errors"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",
			accountsysteminputdialog:"<fmt:message key="label.catalogaccountsysIdInput"/>",
			pleaseselect:"<fmt:message key="label.pleaseselect"/>",
			searchInput:"<fmt:message key="error.searchInput.integer"/>"
			};
		
		
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
    );

    altCabinetNameArray["<c:out value="${currentHub}" escapeXml="false"/>-<c:out value="${currentFacility}" escapeXml="false"/>-All"] = new Array("<fmt:message key="label.all"/>"
    <c:forEach var="myBean" items="${sortedCabinetBeanForAllWorkAreasCollection}" varStatus="status2">
      ,'<tcmis:jsReplace value="${status2.current.cabinetName}"  />'
    </c:forEach>
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
        ,'<tcmis:jsReplace value="${status3.current.cabinetName}"  />'
      </c:forEach>
      );
    </c:forEach>
  </c:forEach>
</c:forEach>
	
	// -->
	 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','cabinetManagement');$('branchPlant').selectedIndex=0;hubChanged();myOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">
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


	<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<div class="contentArea">
<tcmis:form action="/cabinetmanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
	<table width="800" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	  <%-- Row 1 --%>
	  <tr >
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.hub"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft" colspan="2">
          <input name="hubName" id="hubName" type="hidden">
          <select name="branchPlant" id="branchPlant" class="selectBox" onchange="hubChanged();">
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
        <td width="5%"  class="optionTitleBoldRight">
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
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.facility"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft" colspan="2">
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
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.workarea"/>:
        </td>
        <td width="25%" class="optionTitleBoldLeft" colspan="2">
          <select class="selectBox"  name="useApplication" id="useApplication" size="1"  onchange="applicationChanged()">
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

      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight">
          <fmt:message key="label.search"/>:
        </td>
        <td colspan="2" class="optionTitleBoldLeft" nowrap>
		<html:select property="itemOrPart" styleId="itemOrPart" styleClass="selectBox">
		<html:option value="part" key="label.partnumber"/>
		<html:option value="item" key="label.itemid"/>
		</html:select>
		<html:select property="criterion" styleId="criterion" styleClass="selectBox">
		<html:option value="contains" key="label.contain"/>
		<html:option value="is" key="label.is"/>
		</html:select>
		<input class="inputBox" type="text" name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" size="15">
        </td>
        <td>
			&nbsp;
        </td>
        <td  class="optionTitleBoldLeft" nowrap>
			<input name="inactive" id="inactive" type="checkbox" class="radioBtns" value="inactive" <c:if test="${!empty param.inactive}">checked</c:if>>
          	<fmt:message key="label.showinactiveparts"/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight">
          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search();">
        </td>
        <td class="optionTitleBoldLeft">
          <input name="submitExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        </td>
        <td colspan="2" class="optionTitleBoldLeft">
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
<!-- Build this section only if there is an error message to display.
  For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
	<div class="spacerY">&nbsp;
		<div id="searchErrorMessagesArea" class="errorMessages">
			<html:errors />
		</div>
	</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="uAction" id="uAction" type="hidden">
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
	<input name="cabinetId" id="cabinetId" type="hidden" value="">
	<input name="binId" id="binId" type="hidden" value="">
	<input name="binName" id="binName" type="hidden" value="">
	<input name="application" id="application" type="hidden" value="">
	<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
	<input name="sourcePage" id="sourcePage" type="hidden" value="cabinetManagement">
	<input name="accountSysId" id="accountSysId" type="hidden" value="">
	<input name="facilityName" id="facilityName" type="hidden" value="">
	<input name="companyId" id="companyId" type="hidden" value="">
	<input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="">
	<input name="cabinetName" id="cabinetName" type="hidden" value="">
	<input name="applicationId" id="applicationId" type="hidden" value="">
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
	<!-- Transit Page Begins -->
	<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
		<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
	</div>
	<!-- Transit Page Ends -->
	<div id="resultGridDiv" style="display: none;"><!-- Search results start --> <!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
		<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div class="roundcont contentContainer">
						<div class="roundright">
							<div class="roundtop">
								<div class="roundtopright">
									<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
							<div class="roundContent">
								<div class="boxhead"><%-- boxhead Begins --%>
									<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											--%>
									<div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%>
										<span id="updateResultLink" style="display: none">
											<a href="#" onclick="resultFrame.updateGrid(); return false;"><fmt:message key="label.update"/></a>
											|
										 </span>
										 <span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
										 <span id="createNewBinLink"><a href="javascript:addBin()"><fmt:message key="label.createnewbin"/></a></span>
									</div> <%-- mainUpdateLinks Ends --%>
								</div> <%-- boxhead Ends --%>

								<div class="dataContent">
									<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
								</div>
								<%-- result count and time --%>
								<div id="footer" class="messageBar">
								</div>
							</div>

							<div class="roundbottom">
								<div class="roundbottomright">
									<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<!-- Result Frame Ends -->
</div>
<!-- close of interface -->

<!-- Input Dialog Window Begins -->
<div id="accountSysDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center"  width="100%">
				<select name="accountSystemSelectBox" id="accountSystemSelectBox" class="selectBox">
				</select>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" width="100%">
				<input name="accountSysOk"  id="accountSysOk"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="accountSysOkClose();"/>
			</td>
		</tr>
	</table>
</div>

<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.inactiveparts"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>

</body>
</html:html>