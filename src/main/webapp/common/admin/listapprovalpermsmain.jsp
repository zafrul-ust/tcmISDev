<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/common/admin/listapprovalpermissionmain.js"></script>
<script type="text/javascript" src="/js/common/admin/listapprovalpermissionsearch.js"></script>

<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<title>
<fmt:message key="label.listapprovalperms"/> (${fullName})
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData= {
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
		selectFacility:"<fmt:message key="msg.selectFacility"/>",
		selectGroup:"<fmt:message key="label.selectaddusergroup"/>",
		selectUser:"<fmt:message key="label.selectdeleteuser"/>",
		selectOne:"<fmt:message key="label.selectOne"/>"
	};

	<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
	var altCompanyId = new Array(
		""
		<c:forEach var="curCompany" items="${companyFacilityColl}">
			,"<tcmis:jsReplace value="${curCompany.companyId}"/>"
		</c:forEach>
	);

	var altCompanyName = new Array(
		"<fmt:message key="label.mycompanies"/>"
		<c:forEach var="curCompany" items="${companyFacilityColl}">
			,"<tcmis:jsReplace value="${curCompany.companyName}"/>"
		</c:forEach>
	);

	var altFacilityId = new Array();
	var altFacilityName = new Array();
	<c:forEach var="curCompany" items="${companyFacilityColl}">
		altFacilityId["<tcmis:jsReplace value="${curCompany.companyId}"/>"] = new Array(
			""
			<c:forEach var="curFacility" items="${curCompany.facilityList}">
				,"<tcmis:jsReplace value="${curFacility.facilityId}"/>"
			</c:forEach>
		);

		altFacilityName["<tcmis:jsReplace value="${curCompany.companyId}"/>"] = new Array(
			"<fmt:message key="label.selectOne"/>"
			<c:forEach var="curFacility" items="${curCompany.facilityList}">
				,"<tcmis:jsReplace value="${curFacility.facilityName}"/>"
			</c:forEach>
		);
	</c:forEach>
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','listApprovalPermission');setCompany();" onresize="resizeFrames()">
	<div class="interface" id="mainPage" style=""> <!-- start of interface-->
		<div id="searchFrameDiv" >
			<tcmis:form action="/listapprovalpermissionresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
				<div class="contentArea">
					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright">
												<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
											</div>
										</div>
										<div class="roundContent">
											<!-- Insert all the search option within this div -->
											<!-- Search Option Table Start -->
											<table width="80%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<input type="hidden" name="oldcompanyId" id="oldcompanyId" value="" />
												<c:set var="companyFacilityColl" value='${UserFacilitySelectOvCollection}'/>
												<c:choose>
													<c:when test="${fn:length(companyFacilityColl) == 1}">
														<c:forEach var="companyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
															<input type="hidden" name="companyId" id="companyId" value="${status.current.companyId}"/>
														</c:forEach>
													</c:when>
													<c:otherwise>
														<tr>
															<td class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
															<td class="optionTitleLeft">
																<!-- Use this for dropdowns you build with collections from the database -->
																<select name="companyId" id="companyId" class="selectBox" onchange="CompanyChanged()">
																</select>
															</td>
														</tr>
													</c:otherwise>
												</c:choose>
												<tr>
													<td  class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
													<td  class="optionTitleLeft">
														<!-- Use this for dropdowns you build with collections from the database -->
														<input type="hidden" name="oldfacilityId" id="oldfacilityId" value=""/>
														<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">
														</select>
													</td>
													<td width="5%" class="optionTitleRight">
														<input style="display: none" type="button" class="smallBtns" name="createNewGroup" id="createNewGroup" value="<fmt:message key="label.newlistapproval"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick="editFacilityListApproval()"/>
													</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td colspan="2" nowrap>
														<input name="viewType" id="viewTypeMy" type="radio" class="radioBtns" value="MY" checked><fmt:message key="label.userapproval"/></input>
														<input name="viewType" id="viewTypeAll" type="radio" class="radioBtns" value="ALL"><fmt:message key="label.alluserapproval"/></input>
													</td>
												</tr>
												<tr>
													<td colspan="2" nowrap class="optionTitleRight">
														<input name="submitSearch" id="submitSearch" type="submit"  value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchData()"/>
														<input name="createexcel" id="createexcel" type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="createXSL()"/>
														<input type="button" id="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="parent.window.close()"/>
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
							</td>
						</tr>
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
						<input name="uAction" id="uAction" type="hidden" value=""/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
						<input name="searchHeight" id="searchHeight" type="hidden" value="108"/>
						<input type="hidden" name="facilityName" id="facilityName" value=""/>
						<input type="hidden" name="companyName" id="companyName" value=""/>	
						<input type="hidden" name="personnelId" id="personnelId" value="${param.personnelId}" />
						<input type="hidden" name="displayView" id="displayView" value=""/>
						<input type="hidden" name="resultView" id="resultView" value=""/>
						<input type="hidden" name="companySize" id="companySize" value="${fn:length(UserFacilitySelectOvCollection)}"/>
					</div>
					<!-- Hidden elements end -->
				</div>
			</tcmis:form>
		</div>
		<!-- Search Frame Ends -->

		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<!-- Transit Page Begins -->
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br/><br/><br/><fmt:message key="label.pleasewait"/>
				<br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</div>
			<!-- Transit Page Ends -->
			<div id="resultGridDiv" style="display: none;"> 
			<!-- Search results start -->
			<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
										<div class="boxhead"> <%-- boxhead Begins --%>
										<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
										Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
										--%>
											<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
												<div id="updateResultLink" style="display: none">
													<a href="javascript:submitUpdate();"><fmt:message key="label.update"/></a>
												</div>
												<div id="updateResultTreeLink" style="display: none">
													<a href="javascript:submitAdd()"><fmt:message key="label.addusertolist"/></a>
													| <a href="javascript:submitDelete()"><fmt:message key="label.deleteuserfromlist"/></a>
												</div>
											</div> <%-- mainUpdateLinks Ends --%>
										</div> <%-- boxhead Ends --%>
										<div class="dataContent"> 
											<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
		<!-- Result Frame Ends --> 
	</div>
	<!-- close of interface -->

	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
	</div>
</body>
</html:html>
