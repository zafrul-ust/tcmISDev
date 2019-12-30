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
<script type="text/javascript" src="/js/client/het/substratemanagementmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
   <fmt:message key="mixturePermissions"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
		var altFacilityId = new Array(
		<c:forEach var="facility" items="${facilities}" varStatus="status">
			'<tcmis:jsReplace value="${facility.facilityId}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altFacilityName = new Array(
		<c:forEach var="facility" items="${facilities}" varStatus="status">
		   '<tcmis:jsReplace value="${facility.facilityName}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altAreaId = new Array();
		<c:forEach var="area" items="${areas}" varStatus="status">
			<c:if test="${status.first}">
				<c:set var="current" value="${area.facilityId}"/>
				<c:set var="first" value="1"/>
				altAreaId['${area.facilityId}'] = new Array(
			</c:if>
			<c:if test="${current != area.facilityId}">
				);
				<c:set var="current" value="${area.facilityId}"/>
				<c:set var="first" value="1"/>
				altAreaId['${area.facilityId}'] = new Array(
			</c:if>
			<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
			'${area.areaId}'
			<c:if test="${status.last}">
				);
			</c:if>
		</c:forEach>

		var altAreaDesc = new Array();
		<c:forEach var="area" items="${areas}" varStatus="status">
			<c:if test="${status.first}">
				<c:set var="current" value="${area.facilityId}"/>
				<c:set var="first" value="1"/>
				altAreaDesc['${area.facilityId}'] = new Array(
			</c:if>
			<c:if test="${current != area.facilityId}">
				);
				<c:set var="current" value="${area.facilityId}"/>
				<c:set var="first" value="1"/>
				altAreaDesc['${area.facilityId}'] = new Array(
			</c:if>
			<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
			'<tcmis:jsReplace value="${area.areaDescription}"/>'
			<c:if test="${status.last}">
				);
			</c:if>
		</c:forEach>
		
		var altBuildingId = new Array();
		<c:forEach var="building" items="${buildings}" varStatus="status">
			<c:if test="${status.first}">
				<c:set var="current" value="${building.areaId}"/>
				<c:set var="first" value="1"/>
				altBuildingId['${building.areaId}'] = new Array(
			</c:if>
			<c:if test="${current != building.areaId}">
				);
				<c:set var="current" value="${building.areaId}"/>
				<c:set var="first" value="1"/>
				altBuildingId['${building.areaId}'] = new Array(
			</c:if>
			<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
			'${building.buildingId}'
			<c:if test="${status.last}">
				);
			</c:if>
		</c:forEach>

		var altBuildingDesc = new Array();
		<c:forEach var="building" items="${buildings}" varStatus="status">
			<c:if test="${status.first}">
				<c:set var="current" value="${building.areaId}"/>
				<c:set var="first" value="1"/>
				altBuildingDesc['${building.areaId}'] = new Array(
			</c:if>
			<c:if test="${current != building.areaId}">
				);
				<c:set var="first" value="1"/>
				<c:set var="current" value="${building.areaId}"/>
				altBuildingDesc['${building.areaId}'] = new Array(
			</c:if>
			<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
			'<tcmis:jsReplace value="${building.buildingName}"/>'
			<c:if test="${status.last}">
				);
			</c:if>
		</c:forEach>

		var altWorkAreaId = new Array();
		<c:forEach var="workarea" items="${workAreas}" varStatus="status">
			<c:if test="${status.first}">
				<c:set var="current" value="${workarea.buildingId}"/>
				<c:set var="first" value="1"/>
				altWorkAreaId['${workarea.buildingId}'] = new Array(
			</c:if>
			<c:if test="${current != workarea.buildingId}">
				);
				<c:set var="current" value="${workarea.buildingId}"/>
				<c:set var="first" value="1"/>
				altWorkAreaId['${workarea.buildingId}'] = new Array(
			</c:if>
			<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
			'${workarea.applicationId}'
			<c:if test="${status.last}">
				);
			</c:if>
		</c:forEach>

		var altWorkAreaDesc = new Array();
		<c:forEach var="workarea" items="${workAreas}" varStatus="status">
			<c:if test="${status.first}">
				<c:set var="current" value="${workarea.buildingId}"/>
				<c:set var="first" value="1"/>
				altWorkAreaDesc['${workarea.buildingId}'] = new Array(
			</c:if>
			<c:if test="${current != workarea.buildingId}">
				);
				<c:set var="current" value="${workarea.buildingId}"/>
				<c:set var="first" value="1"/>
				altWorkAreaDesc['${workarea.buildingId}'] = new Array(
			</c:if>
			<c:choose><c:when test="${first == 1}"><c:set var="first" value="0"/></c:when><c:otherwise>,</c:otherwise></c:choose>
			'<tcmis:jsReplace value="${workarea.applicationDesc}"/>'
			<c:if test="${status.last}">
				);
			</c:if>
		</c:forEach>

                //add all the javascript messages here, this for internationalization of client side javascript messages
                var messagesData = new Array();
                messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
	                showlegend:"<fmt:message key="label.showlegend"/>",
	                errors:"<fmt:message key="label.errors"/>",  
	                validvalues:"<fmt:message key="label.validvalues"/>",
	                itemid:"<fmt:message key="label.itemid"/>",
	                submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	                pleaseselect:"<fmt:message key="label.pleaseselect"/>",
	                all:"<fmt:message key="label.all"/>",
	                waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>"
                };
// -->
</script> 	

</head>
<body bgcolor="#ffffff" onload="loadLayoutWin('','mixturePermissions');myOnLoad();" onunload="closeAllchildren();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/mixturepermissionresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
		<table width="450" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<tr>
			<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
				<fmt:message key="label.facility"/>:
			</td>
			<td width="40%" class="optionTitleBoldLeft">
				<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">
				</select>
			</td>
			</tr>
			<tr>
				<td width="10%" class="optionTitleBoldRight" nowrap>
					<fmt:message key="label.area"/>:
				</td>
				<td width="40%" class="optionTitleBoldLeft">
					<select name="areaId" id="areaId" class="selectBox" onchange="areaChanged()">			
					</select>
				</td>
			</tr>
			<tr>
				<td width="10%" class="optionTitleBoldRight" nowrap>
					<fmt:message key="label.building"/>:
				</td>
				<td width="40%" class="optionTitleBoldLeft">
					<select name="buildingId" id="buildingId" class="selectBox" onchange="buildingChanged()">			
					</select>
				</td>
			</tr>
			<tr>
				<td width="10%" class="optionTitleBoldRight" nowrap>
					<fmt:message key="label.workarea"/>:
				</td>
				<td width="40%" class="optionTitleBoldLeft">
					<select name="workArea" id="workArea" class="selectBox">
					</select>
				</td>		
			</tr>
			<tr></tr>
			<tr>
     			<td colspan="4" width="100%" class="optionTitleBoldLeft">
          			<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"/>
          			<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return createXSL()"/>
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
<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

	 <%-- freeze --%>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="150">
</div>
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->
<!-- Search Frame Ends -->

	<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br>
				<br>
				<br>
				<fmt:message key="label.pleasewait" />
				<br>
				<br>
				<br>
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
	
			<div id="resultGridDiv" style="display: none;">
				<%-- Search results start --%> 
				<%-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. --%>
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
										<div class="boxhead">
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											     Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											--%>
											<div id="mainUpdateLinks" style="display: none">
									<%--		<a href="#" onclick="resultFrame.newSubstrate();"><fmt:message key="label.addnewsubstrate" /></a> | --%>
												<span id="hasDataLink" style="display: none"><a href="#" onclick="resultFrame.update();"><fmt:message key="label.update" /></a></span>
											</div>
										</div>
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>
								
										<%-- result count and time --%>
										<div id="footer" class="messageBar"></div>
								
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
	<!-- Result Frame Ends --


		

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html>
      		
    		
