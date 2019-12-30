<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

	<%@ include file="/common/locale.jsp"%>
	<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss /> 
	<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
	
	<%-- Add any other stylesheets you need for the page here --%> 
	<script type="text/javascript" src="/js/common/formchek.js"></script> 
	<script type="text/javascript" src="/js/common/commonutil.js"></script> 

	<!-- This handles all the resizing of the page and frames --> 
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> 
	
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script> 
	
	<!-- This handels the menu style and what happens to the right click on the whole page --> 
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
		
	<%@ include file="/common/rightclickmenudata.jsp"%> 
	
	<!-- For Calendar support --> 
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script> 
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script> 
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script> 
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
	<%--<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
	<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
	<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
	--%>
	
	<!-- Add any other javascript you need for the page here --> 
	<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/admin/usergrouppermissionmain.js"></script>
	<script type="text/javascript" src="/js/common/admin/usergrouppermissionsearch.js"></script>
	
	<!-- This is for the YUI, uncomment if you will use this -->
	<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
	<script type="text/javascript" src="/yui/build/event/event.js" ></script>
	<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
	<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
	<script type="text/javascript" src="/yui/build/container/container.js"></script>
	<script type="text/javascript" src="/js/common/waitDialog.js"></script>
	<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

	<title>
		<fmt:message key="label.usergroupperms"/> (${fullName})
	</title>

	<script language="JavaScript" type="text/javascript">
		var facilityPermissionArray = new Array();
		<c:set var="counter" value='${0}'/>
		<c:forEach var="hubBean" items="${personnelBean.permissionBean.userGroupMemberBeanCollection}" varStatus="status">
			<c:if test="${hubBean.userGroupId == 'Administrator'}">
				facilityPermissionArray["${counter}"] = "<tcmis:jsReplace value="${hubBean.facilityId}"/>";
				<c:set var="counter" value='${counter + 1}'/>
			</c:if>
		</c:forEach>

		<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
		<bean:size id="companySize" name="companyFacilityColl"/>
		var	altCompanyId = new Array(
			<c:forEach var="curCompany" items="${companyFacilityColl}" varStatus="status">
				<c:if test="${!status.first}">,</c:if>
				"<tcmis:jsReplace value="${curCompany.companyId}"/>"
			</c:forEach>
		);

		var altCompanyName = new Array(
			<c:forEach var="curCompany" items="${companyFacilityColl}" varStatus="status">
				<c:if test="${!status.first}">,</c:if>
				"<tcmis:jsReplace value="${curCompany.companyName}"/>"
			</c:forEach>
		);

		var altFacilityId = new Array();
		var altFacilityName = new Array();
		
		<c:if test="${companySize > 1}">
			altCompanyId.unshift("<fmt:message key="label.mycompanies"/>");
			altCompanyName.unshift("<fmt:message key="label.mycompanies"/>");
			altFacilityId["<fmt:message key="label.mycompanies"/>"] = new Array("<fmt:message key="label.selectOne"/>");
			altFacilityName["<fmt:message key="label.mycompanies"/>"] = new Array("<fmt:message key="label.selectOne"/>");
		</c:if>
		
		<c:forEach var="curCompany" items="${companyFacilityColl}">
			<c:set var="facilityColl" value='${curCompany.facilityList}'/>
			<bean:size id="facilitySize" name="facilityColl"/>
			altFacilityId["<tcmis:jsReplace value="${curCompany.companyId}"/>"] = new Array(
				<c:forEach var="curFacility" items="${facilityColl}" varStatus="status">
					<c:if test="${!status.first}">,</c:if>
					"<tcmis:jsReplace value="${curFacility.facilityId}"/>"
				</c:forEach>
			);

			altFacilityName["<tcmis:jsReplace value="${curCompany.companyId}"/>"] = new Array(
				<c:forEach var="curFacility" items="${facilityColl}" varStatus="status">
					<c:if test="${!status.first}">,</c:if>
					"<tcmis:jsReplace value="${curFacility.facilityName}"/>"
				</c:forEach>
			);
			
			<c:if test="${facilitySize > 1}">
				altFacilityId.unshift("<fmt:message key="label.selectOne"/>");
				altFacilityName.unshift("<fmt:message key="label.selectOne"/>");
			</c:if>
		</c:forEach>

		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData= {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			selectGroup:"<fmt:message key="label.selectaddusergroup"/>",
			selectUser:"<fmt:message key="label.selectdeleteuser"/>",
			selectDeleteGroup:"<fmt:message key="label.selectdeletegroup"/>",
			selectFacility:"<fmt:message key="msg.selectFacility"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"
		};
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','openPos');myOnload();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style=""> <!-- start of interface-->


<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them
 -->
 <tcmis:form action="/usergrouppermissionresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
 <div class="contentArea"> <!-- Start of contentArea-->
		
<!-- Search Option Begins -->
<table id="searchMaskTable" width="450" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<%-- determine whether to display company dropdown --%>
			<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
			<bean:size id="companySize" name="companyFacilityColl"/>
			<input type="hidden" name="companySize" id="companySize" value="${companySize}"/>
			<c:choose>
				<c:when test="${companySize == 1}">
					 <c:forEach var="companyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
						 <input type="hidden" name="companyId" id="companyId" value="${status.current.companyId}"/>
						 <c:set var="facilityCollection" value='${status.current.facilityList}'/>
					 </c:forEach>
				</c:when>
				<c:otherwise>
					 <tr>
							<td width="13%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
							<td width="33%" class="optionTitleLeft">
								<c:set var="selectedCompanyId" value='${param.companyId}'/>
								<select name="companyId" id="companyId" class="selectBox" onChange="CompanyChanged()">
								 <c:choose>
									 <c:when test="${companySize == 0}">
										 <option value="<fmt:message key="label.mycompanies"/>"><fmt:message key="label.mycompanies"/></option>
									 </c:when>
									 <c:otherwise>
										 <c:if test="${companySize > 1}">
											 <option value="<fmt:message key="label.mycompanies"/>" selected><fmt:message key="label.mycompanies"/></option>
											 <c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
										 </c:if>

										 <c:forEach var="companyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
											 <c:set var="currentCompanyId" value='${status.current.companyId}'/>
												 <c:if test="${empty selectedCompanyId}" >
													 <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
													 <c:set var="facilityCollection" value='${status.current.facilityList}'/>
												 </c:if>
												 <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}" escapeXml="false"/></option>
										 </c:forEach>
									 </c:otherwise>
								 </c:choose>
								</select>
							</td>
						 	<td width="0%">
								&nbsp;
							</td>
						</tr>
				</c:otherwise>
			</c:choose>

      <tr>
        <td width="13%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="33%" class="optionTitleLeft">
					<c:choose>
						 <c:when test="${facilityCollection == null}">
							 <c:set var="facilitySize" value="0"/>
						 </c:when>
						 <c:otherwise>
							 <bean:size id="facilitySize" name="facilityCollection"/>
						 </c:otherwise>
					 </c:choose>
					 <c:set var="selectedFacilityId" value='${param.facilityId}'/>
					 <select name="facilityId" id="facilityId" class="selectBox" onChange="facilityChanged()">
					 <c:choose>
						 <c:when test="${facilitySize == 0}">
							 <option value="<fmt:message key="label.selectOne"/>"><fmt:message key="label.selectOne"/></option>
						 </c:when>
						 <c:otherwise>
							 <c:if test="${facilitySize > 1}">
								 <option value="<fmt:message key="label.selectOne"/>" selected><fmt:message key="label.selectOne"/></option>
								 <c:set var="selectedFacilityId" value="selectOne"/>  <%-- set it to something so I can test it selectedFacilityId is empty below --%>
							 </c:if>
							 <c:forEach var="facilityOvBean" items="${facilityCollection}" varStatus="status">
								 <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
									 <c:if test="${empty selectedFacilityId}" >
										<c:set var="selectedFacilityId" value='${status.current.facilityId}'/>
									 </c:if>
									 <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}" escapeXml="false"/></option>
							 </c:forEach>
						 </c:otherwise>
					 </c:choose>
			</td>
			<td width="5%" class="optionTitleRight">
				<input style="display: none" type="button" class="smallBtns" name="createNewGroup" id="createNewGroup" value="<fmt:message key="label.newusergroup"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="createNewUserGroup()"/>
		 	</td>
      </tr>

		<tr>
			<td>&nbsp;</td>
			<td colspan="2" nowrap>
				<input name="viewType" id="viewTypeMy" type="radio" class="radioBtns" value="MY" checked>
        		<fmt:message key="label.useraccess"/>
        		<input name="viewType" id="viewTypeAll" type="radio" class="radioBtns" value="ALL">
        		<fmt:message key="label.alluseraccess"/>
			</td>
		</tr>

		<tr>
      	<td colspan="3">
				  <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchData();">
              <input name="createexcel" id="createexcel" type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>
				  <input type="button" name="cancel" id="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="parent.window.close()"/>
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

<!-- Search Frame Ends -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="action" id="action" value="search"/>
<input type="hidden" name="resultView" id="resultView" value=""/>
<input type="hidden" name="personnelId" id="personnelId" value="${param.personnelId}" />
<input type="hidden" name="fullName" id="fullName" value="${param.fullName}" />
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<input type="hidden" name="companyName" id="companyName" value=""/>	
<input type="hidden" name="displayView" id="displayView" value=""/>
<input type="hidden" name="userGroupType" id="userGroupType" value="${param.userGroupType}" />	
</div>
<!-- Hidden elements end -->

 <!-- close of interface -->
</tcmis:form>
</div>


<div class="spacerY">&nbsp;</div>

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
										<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
										      <div id="updateResultLink" style="display: none">
										        <a href="javascript:resultFrame.update()"><fmt:message key="label.update"/></a>
										      </div>
												<div id="updateResultTreeLink" style="display: none">
											        <a href="javascript:submitAdd()"><fmt:message key="label.addusertogroup"/></a>
											        | <a href="javascript:submitDelete()"><fmt:message key="label.deleteuserfromgroup"/></a>
													  | <a href="javascript:submitDeleteGroup()"><fmt:message key="label.deletegroup"/></a>	
												</div>
						    			 </div> <%-- mainUpdateLinks Ends --%>
									</div> <%-- boxhead Ends --%>
							
									<div class="dataContent">
										<iframe id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
	</div><!-- Result Frame Ends -->

<!-- Can not have footer message because of resizing issues with iframe -->
<%--
<!-- Footer message start -->
<div id="footer" class="messageBar">545</div>
<!-- Footer message end -->
--%>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
		<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/> <!-- needed if this page will show on application.do -->
				<!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --> 
		<input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">
 <html:errors/>
</div>
</div>

<script type="text/javascript">
<!--
YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);
//-->
</script>

</body>
</html:html>