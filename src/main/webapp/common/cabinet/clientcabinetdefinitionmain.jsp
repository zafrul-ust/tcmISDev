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
<script type="text/javascript" src="/js/common/cabinet/clientcabinetdefinitionmain.js"></script>

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
    <fmt:message key="cabinetdefinition.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
		var altFacilityId = new Array(
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			'<tcmis:jsReplace value="${facility.facilityId}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);

		var altFacilityName = new Array(
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
		   '<tcmis:jsReplace value="${facility.facilityName}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);

		var altWorkAreaGroupId = new Array();
		var altWorkAreaGroupDesc = new Array();
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
		   <c:set var="wagCount" value='0'/>
			altWorkAreaGroupId['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
			<c:forEach var="wagBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:if test="${wagBean.reportingEntityStatus == 'A'}">
				 	<c:if test="${wagCount > 0}">,</c:if>
					'<tcmis:jsReplace value="${wagBean.reportingEntityId}"/>'
					<c:set var="wagCount" value='${wagCount+1}'/>
				</c:if>
			</c:forEach>
			);
		   <c:set var="wagCount" value='0'/>
			altWorkAreaGroupDesc['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
		 	<c:forEach var="wagBean"  items="${facility.workAreaGroupColl}" varStatus="status1">
		   	<c:if test="${wagBean.reportingEntityStatus == 'A'}">
				 	<c:if test="${wagCount > 0}">,</c:if>
					'<tcmis:jsReplace value="${wagBean.reportingEntityDesc}"/>'
					<c:set var="wagCount" value='${wagCount+1}'/>
				</c:if>
			</c:forEach>
			);
		</c:forEach>

		var altWorkAreaArray = new Array();
		var altWorkAreaDescArray = new Array();
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-All'] = new Array(
			<c:forEach var="workAreaBean" items="${facility.facilityWorkAreaColl}" varStatus="status1">
				<c:if test="${workAreaBean.status == 'A'}">
					<c:if test="${workAreaCount > 0}">,</c:if>
					'<tcmis:jsReplace value="${workAreaBean.applicationId}"/>'
					<c:set var="workAreaCount" value='${workAreaCount+1}'/>
				</c:if>
			</c:forEach>
			);
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-All'] = new Array(
			<c:forEach var="workAreaBean" items="${facility.facilityWorkAreaColl}" varStatus="status1">
				<c:if test="${workAreaBean.status == 'A'}">
					<c:if test="${workAreaCount > 0}">,</c:if>
					'<tcmis:jsReplace value="${workAreaBean.applicationDesc}"/>'
					<c:set var="workAreaCount" value='${workAreaCount+1}'/>
				</c:if>
			</c:forEach>
		    	);
		</c:forEach>

		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:forEach var="workAreaGroupBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:set var="workAreaCount" value='${0}'/>
				altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						<c:if test="${workAreaCount > 0}">,</c:if>
						'<tcmis:jsReplace value="${workAreaBean.applicationId}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
				);
				<c:set var="workAreaCount" value='0'/>
				altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						<c:if test="${workAreaCount > 0}">,</c:if>
						'<tcmis:jsReplace value="${workAreaBean.applicationDesc}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
		      		);
			</c:forEach>
		</c:forEach>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
searchInput:"<fmt:message key="error.searchInput.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','clientCabinetDetail');myOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/clientcabinetdefinitionresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="800" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
       <tr>
			<td width="15%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
			<td width="35%" class="optionTitleBoldLeft">
				<select class="selectBox"  name="facilityId" id="facilityId" size="1" onchange="facilityChanged()"></select>
			</td>
			<td width="15%" rowspan="3" class="optionTitleBoldRight"><fmt:message key="label.workarea"/>:</td>
			<td width="35%" rowspan="3" class="optionTitleBoldLeft">
				<select name="cabinetIdArray" id="cabinetIdArray" class="selectBox" multiple size="5"></select>
			</td>
		</tr>
		<tr>
			<td width="15%" class="optionTitleBoldRight"><fmt:message key="label.workareagroup"/>:</td>
			<td width="35%" class="optionTitleBoldLeft">
				<select class="selectBox"  name="useApplication" id="useApplication" size="1"  onchange="workAreaGroupChanged()"></select>
			</td>
		</tr>
      <tr>
        <td colspan="3" class="optionTitleBoldLeft">
			<input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key='label.search'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return search()"/>
			<input name="buttonCreateExcel" id="buttonCreateExcel" type="button" value="<fmt:message key='label.createexcelfile'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return generateExcel()"/>
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
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>	
<input name="sourcePage" id="sourcePage" type="hidden" value="clientCabinetDefinition">	
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
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
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <div id="updateResultLink" style="display: none">
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>

  	  <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
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
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;"></div>

</body>
</html:html>