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
<script type="text/javascript" src="/js/common/misc/searchpersonnel.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
    <fmt:message key="searchpersonnel.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
	<c:set var="workareaFacilityColl" value='${dropDownDataCollection}'/>
	<bean:size id="companySize" name="workareaFacilityColl"/>
	var altCompanyId = new Array(
		<c:forEach var="myWorkareaFacilityViewBean" items="${dropDownDataCollection}" varStatus="status">
			<c:choose>
				<c:when test="${status.index == 0 && companySize > 1}">
					"My Companies","<c:out value="${status.current.companyId}" escapeXml="false"/>"
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${status.index == 0}">
							"<c:out value="${status.current.companyId}" escapeXml="false"/>"
						</c:when>
						<c:otherwise>
							,"<c:out value="${status.current.companyId}" escapeXml="false"/>"
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	);
	
	var altCompanyName = new Array(
		<c:forEach var="myWorkareaFacilityViewBean" items="${dropDownDataCollection}" varStatus="status">
			<c:choose>
				<c:when test="${status.index == 0 && companySize > 1}">
					"<fmt:message key="label.mycompanies"/>",'<tcmis:jsReplace value="${status.current.companyName}"/>'
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${status.index == 0}">
							'<tcmis:jsReplace value="${status.current.companyName}"/>'
						</c:when>
						<c:otherwise>
							,'<tcmis:jsReplace value="${status.current.companyName}"/>'
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	);
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		showlegend:"<fmt:message key="label.showlegend"/>",
		errors:"<fmt:message key="label.errors"/>",  
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		itemInteger:"<fmt:message key="error.item.integer"/>",
		selectA:"<fmt:message key="errors.selecta" />",
		company:"<fmt:message key="label.company" />"
	};
	
	<c:set var="displayView" value='${displayView}'/>
	var myHeight = 83;
	<c:if test="${companySize > 1}">
		myHeight = 110;
	</c:if>
	
	<c:if test="${displayView == 'displayCompanyFacility'}">
		myHeight = 133;
	</c:if>
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','searchpersonnelmain');mySearchOnLoad();document.genericForm.searchText.focus();" onresize="resizeFrames()" onunload="try{closeAllchildren(); opener.parent.closeTransitWin();}catch(ex){}">
	<div class="interface" id="mainPage" style="">
		<!-- Search Frame Begins -->
		<div id="searchFrameDiv">
			<%--NEW - removed the search frame and copied the search section here--%>
			<div class="contentArea">
				<tcmis:form action="/searchpersonnelresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="450" border="0" cellpadding="0" cellspacing="0">
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
											<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<%-- row 1 --%>
												<c:set var="displayView" value='${displayView}'/>
												<c:if test="${displayView == 'displayCompany'}">
													<tr>
														<td width="30%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
														<td width="50%" colspan="2">
															<c:set var="selectedCompanyId" value='${companyId}'/>
															<select name="companyId" id="companyId" class="selectBox">
																<c:choose>
																	<c:when test="${companySize == 0}">
																		<option value="My Companies"><fmt:message key="label.mycompanies"/></option>
																	</c:when>
																	<c:otherwise>
																		<c:if test="${companySize > 1}">
																			<option value="My Companies" selected><fmt:message key="label.mycompanies"/></option>
																			<c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
																		</c:if>
																		<c:forEach var="myWorkareaFacilityViewBean" items="${dropDownDataCollection}" varStatus="status">
																			<c:set var="currentCompanyId" value='${status.current.companyId}'/>
																			<c:if test="${empty selectedCompanyId}" >
																				<c:set var="selectedCompanyId" value='${status.current.companyId}'/>
																			</c:if>
																			<option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}"/></option>
																		</c:forEach>
																	</c:otherwise>
																</c:choose>
															</select>
														</td>
													</tr>
												</c:if>
												
												<c:if test="${displayView == 'displayCompanyFacility'}">
													<tr>
														<td width="30%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
														<td width="50%" colspan="2"><c:out value="${companyName}"/></td>
													</tr>
													<tr>
														<td width="30%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
														<td width="50%" colspan="2"><c:out value="${facilityName}"/></td>
													</tr>
													
													<%-- hidden values --%>
													<input name="companyId" id="companyId" type="hidden" value="<c:out value="${companyId}"/>"/>
													<input name="facilityId" id="facilityId" type="hidden" value="<c:out value="${facilityId}"/>"/>
												</c:if>
												
												<c:if test="${displayView == 'hideCompanyFacility'}">
													<%-- hidden values --%>
													<input name="companyId" id="companyId" type="hidden" value="<c:out value="${companyId}"/>"/>
												</c:if>
												
												<%-- row 2 --%>
												<tr>
													<td width="30%"  class="optionTitleBoldRight">
														<fmt:message key="label.search"/>:
													</td>
													<td width="50%"  class="optionTitleLeft">
														<input type="text" class="inputBox" name="searchText" id="searchText" value="<c:out value="${param.searchText}"/>" size="30" onkeypress="return onKeyPress()"/>
													</td>
													<td width="30%"  class="optionTitleBoldLeft" nowrap>
													    <c:set var="checkStatus" value='checked'/>
													    <c:if test="${param.allowUserCreate == 'Y'}">
													        <c:set var="checkStatus" value=''/>
													    </c:if>
														<input type="checkbox" class="radioBtns" name="status" id="status" value="ACTIVE" ${checkStatus}/>
														<fmt:message key="label.activeOnly"/>
													</td>
												</tr>
												
												<%-- row 3 --%>
												<tr>
													<td width="30%"  colspan="3" class="optionTitleLeft">
														<html:submit property="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"><fmt:message key="label.search"/></html:submit>
														<c:set var="allowUserCreate" value='${param.allowUserCreate}'/>
														<c:if test="${allowUserCreate == 'Y'}">
															&nbsp;<input onclick="javascript:createNewUser();" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createUser"/>" name="createUser" id="createUser"/>
														</c:if>
													</td>
												</tr>
											</table>
											<!-- End search options -->
										</div>
										
										<div class="roundbottom">
											<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
					
					<!-- Error Messages Begins -->
					<!-- Build this section only if there is an error message to display.
					For the search section, we show the error messages within its frame-->
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
						<input name="action" id="action" type="hidden" value=""/>
						<input name="displayElementId" id="displayElementId" type="hidden" value="<c:out value="${param.displayElementId}"/>"/>
						<input name="displayArea" id="displayArea" type="hidden" value="<c:out value="${param.displayArea}"/>"/>
						<input name="valueElementId" id="valueElementId" type="hidden" value="<c:out value="${param.valueElementId}"/>"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="fixedCompanyId" id="fixedCompanyId" type="hidden" value="${param.fixedCompanyId}"/>
						<input name="callbackparam" id="callbackparam" type="hidden" value="${param.callbackparam}"/>
					</div>
					<!-- Hidden elements end -->
				</tcmis:form>
			</div>
			<!-- close of contentArea -->
		</div>
		<!-- Search Frame Ends -->
		
		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%--NEw -Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br/><br/><br/><fmt:message key="label.pleasewait"/>
				<br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</div>
			<!-- Transit Page Ends -->
			
			<div id="resultGridDiv" style="display: none;">
				<!-- Search results start -->
				<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
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
											Please keep the <span></span> on the same line this will avoid extra virtical space.--%>
											<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
											<%-- mainUpdateLinks Begins --%>
											<div id="mainUpdateLinks" style="display: none">
												<span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
												<span id="selectedUser">&nbsp;</span>
											</div>
											<%-- mainUpdateLinks Ends --%>
										</div>
										<%-- boxhead Ends --%>
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
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- Result Frame Ends -->
	</div>
	<!-- close of interface -->
	
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;"></div>
	
	<%-- show legend Begins --%>
	<div id="showLegendArea" style="display: none;overflow: auto;">
		<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px" class="black legendText">&nbsp;</td>
				<td class="legendText"><fmt:message key="label.inactive"/></td>
			</tr>
		</table>
	</div>
	<%-- show legend Ends --%>
</body>
</html:html>