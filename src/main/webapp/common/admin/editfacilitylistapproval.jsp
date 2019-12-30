<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
		<!-- Add any other stylesheets you need for the page here -->
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
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
		<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
		<script type="text/javascript" src="/js/common/admin/editfacilitylistapproval.js"></script>
		
		<!-- These are for the Grid, uncomment if you are going to use the grid -->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<%--This is for HTML form integration.--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<%--This is to suppoert loading by JSON.--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
		<%--This has the custom cells we built, hcal - the internationalized calendar which we use
		hlink- this is for any links you want tp provide in the grid, the URL/function to call
		can be attached based on a even (rowselect etc)--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<%--This file has our custom sorting and other utility methos for the grid.--%>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
		
		<title>
			<fmt:message key="label.editfacilitylistapproval"/> ---(${param.facilityName})
		</title>
		
		<script language="JavaScript" type="text/javascript">
			//add all the javascript messages here, this for internationalization of client side javascript messages
			var messagesData = new Array();
			messagesData={
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				recordFound:"<fmt:message key="label.recordFound"/>",
				searchDuration:"<fmt:message key="label.searchDuration"/>",
				minutes:"<fmt:message key="label.minutes"/>",
				seconds:"<fmt:message key="label.seconds"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
				setAtLeastOneLimit:"<fmt:message key="label.setatleastonelimit"/>",
				mrLimitWrongFormat:"<fmt:message key="label.mrlimitwrongformat"/>",
				ytdLimitWrongFormat:"<fmt:message key="label.ytdlimitwrongformat"/>",
				periodLimitWrongFormat:"<fmt:message key="label.periodlimitwrongformat"/>",
				missingPeriodDays:"<fmt:message key="label.missingperioddays"/>",
				line:"<fmt:message key="label.line"/>",
				noDefault:"<fmt:message key="label.cannotRemoveDefault"/>"
			};
			
			var config = [
				{
					columnId: "permission"
				},    
				{
					columnId:"listName",
					columnName:"<fmt:message key="label.list"/>",
					width:20
				},
				{
					columnId:"status",
					columnName:"<fmt:message key="label.active"/>",
					type:'hchstatus',
					align:'center',
					width:4
				},
				{
					columnId:"mrLimit",
					columnName:"<fmt:message key="label.mrlimit"/>",
					attachHeader:"<fmt:message key="label.value"/>",
					type:'hed',
					align:'center',
					width:6
				},
				{
					columnId:"mrLimitUnit",
					columnName:"#cspan",
					attachHeader:"<fmt:message key="label.weightunit"/>",
					type:'hcoro',
					align:'center',
					width:8
				},
				{
					columnId:"ytdLimit",
					columnName:"<fmt:message key="label.ytdlimit"/>",
					attachHeader:"<fmt:message key="label.value"/>",
					type:'hed',
					align:'center',
					width:6
				},
				{
					columnId:"ytdLimitUnit",
					columnName:"#cspan",
					attachHeader:"<fmt:message key="label.weightunit"/>",
					type:'hcoro',
					align:'center',
					width:8
				},
				{
					columnId:"ytdStartMm",
					columnName:"#cspan",
					attachHeader:"<fmt:message key="label.month"/> (<fmt:message key="label.ytdstarting"/>)",
					type:'hcoro',
					align:'center',
					width:8
				},
				{
					columnId:"periodLimit",
					columnName:"<fmt:message key="label.periodlimit"/>",
					attachHeader:"<fmt:message key="label.value"/>",
					type:'hed',
					align:'center',
					width:6
				},
				{
					columnId:"periodLimitUnit",
					columnName:"#cspan",
					attachHeader:"<fmt:message key="label.weightunit"/>",
					type:'hcoro',
					align:'center',
					width:8
				},
				{
					columnId:"periodDays",
					columnName:"#cspan",
					attachHeader:"<fmt:message key="label.period"/> (<fmt:message key="label.days"/>)",
					type:'hed',
					align:'center',
					width:6
				},
				{
					columnId:"companyId"
				},
				{
					columnId:"facilityId"
				},
				{
					columnId:"listId"
				}
			];
			
			var mrLimitUnit = new Array(
				<c:forEach var="sizeUnitConversionBean" items="${sizeUnitConversionCollection}" varStatus="status">
					<c:if test="${status.index > 0}">,</c:if>
					{
						text:"<tcmis:jsReplace value="${sizeUnitConversionBean.fromUnit}"/>",
						value:"${sizeUnitConversionBean.fromUnit}"
					}
				</c:forEach>
			);
			
			var ytdLimitUnit = mrLimitUnit;
			
			var ytdStartMm = new Array(
				{
					text:"<fmt:message key="label.jan"/>",
					value:"01"
				},
				{
					text:"<fmt:message key="label.feb"/>",
					value:"02"
				},
				{
					text:"<fmt:message key="label.mar"/>",
					value:"03"
				},
				{
					text:"<fmt:message key="label.apr"/>",
					value:"04"
				},
				{
					text:"<fmt:message key="label.may"/>",
					value:"05"
				},
				{
					text:"<fmt:message key="label.jun"/>",
					value:"06"
				},
				{
					text:"<fmt:message key="label.jul"/>",
					value:"07"
				},
				{
					text:"<fmt:message key="label.aug"/>",
					value:"08"
				},
				{
					text:"<fmt:message key="label.sep"/>",
					value:"09"
				},
				{
					text:"<fmt:message key="label.oct"/>",
					value:"10"
				},
				{
					text:"<fmt:message key="label.nov"/>",
					value:"11"
				},
				{
					text:"<fmt:message key="label.dec"/>",
					value:"12"
				}
			);
			
			var periodLimitUnit = mrLimitUnit;
			
			var gridConfig = {
					divName: 'facilityListSelectionViewBean',	<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
					beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
					beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
					config: 'config',					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
					submitDefault: true
				};
			showUpdateLinks = true;
		</script>
	</head>
	
	<body bgcolor="#ffffff" onload="resultOnLoadWithGrid()" onresize="resizeFrames()">
		<tcmis:form action="/editfacilitylistapproval.do" onsubmit="return submitOnlyOnce();">
			<div class="interface" id="resultsPage">
				<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
				The default value of showUpdateLinks is false.-->
				
				<c:set var="isAdmin" value="Yes"/>
				<tcmis:facilityPermission indicator="true" userGroupId="ListApprovalAdmin" facilityId="${facilityId}" companyId="${companyId}">
					<script type="text/javascript">
						showUpdateLinks = true;
						<c:set var="isAdmin" value="Yes"/>
					</script>
				</tcmis:facilityPermission>
				
				<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
				So this is just used to feed the YUI pop-up in the main page.
				Similar divs would have to be built to show any other messages in a pop-up.-->
				<!-- Error Messages Begins -->
				<div id="errorMessagesAreaBody" style="display:none;">
					<html:errors/>
					${tcmISError}
				</div>
				
				<script type="text/javascript">
					/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
					<c:choose>
						<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['tcmISError']}">
							showErrorMessage = false;
						</c:when>
						<c:otherwise>
							showErrorMessage = true;
						</c:otherwise>
					</c:choose>
				</script>
				<!-- Error Messages Ends -->
				
				<!-- Result Frame Begins -->
				<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
					<!-- Transit Page Begins -->
					<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
						<br/><br/><br/><fmt:message key="label.pleasewait"/>
						<br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
					</div>
					<!-- Transit Page Ends -->
					
					<!-- results start -->
					<div id="resultGridDiv" style="display: none;">
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
													<%-- mainUpdateLinks Begins --%>
													<div id="mainUpdateLinks" style="display:none">
														<span id="updateResultLink" style="display:none">
															<a href="javascript:submitUpdate()"><fmt:message key="label.update"/></a> |
														</span>
														<a href="javascript:createXSL()"><fmt:message key="label.createExcel"/></a> |
														<a href="#" onclick="parent.window.close()"><fmt:message key="label.cancel"/></a>&nbsp;
													</div>
													<%-- mainUpdateLinks Ends --%>
												</div>
												<%-- boxhead Ends --%>
												<div class="dataContent">
													<div id="facilityListSelectionViewBean" style="width:100%;height:400px;" style="display: none;"></div>
													<!-- Search results starts -->
													<c:choose>
														<c:when test="${!empty facilityListSelectionViewBeanCollection}">
															<script type="text/javascript">
																<%-- Loop through the results and output each row, formatting the results as necessary
																use tcmis:jsReplace to escape special characters for ANY user entered text
																use fmt:formatNumber to format numbers (existing patterns: unitpricecurrencyformat, totalcurrencyformat, oneDigitformat)
																use mt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
																var jsonMainData = new Array();
											          		 	    var jsonMainData = {
																	rows:[
																		<c:forEach var="UserFacilityAdminViewBean" items="${facilityListSelectionViewBeanCollection}" varStatus="status">
																			<c:set var="changePermission" value="N"/>
																			<c:set var="statusChecked" value="false"/>
																			
																			<c:if test="${isAdmin == 'Yes'}">
																				<c:set var="changePermission" value="Y"/>
																			</c:if>
																			<c:if test="${UserFacilityAdminViewBean.status == 'A'}">
																				<c:set var="statusChecked" value="true"/>
																			</c:if>
																			
																			<c:if test="${!status.first}">,</c:if>
											               	            	{
											               	            		id:${status.count},
											               	            		data:[
																					"${changePermission}",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.listName}"/>",
																					${statusChecked},
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.mrLimit}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.mrLimitUnit}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.ytdLimit}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.ytdLimitUnit}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.ytdStartMm}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.periodLimit}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.periodLimitUnit}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.periodDays}"/>",
																					<%-- hidden columns --%>
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.companyId}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.facilityId}"/>",
																					"<tcmis:jsReplace value="${UserFacilityAdminViewBean.listId}"/>"
																				]
																			}
																		</c:forEach>
																	]
												           	    };
															</script>
														</c:when>
														<c:otherwise>
															<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
																<tr>
																	<td width="100%">
																		<fmt:message key="main.nodatafound"/>
																	</td>
																</tr>
															</table>
														</c:otherwise>
													</c:choose>
													<!-- Search results end -->
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
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="action" id="action" value=""/>
					<input type="hidden" name="companyId" id="companyId" value="${param.companyId}" />
					<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}" />
					<input type="hidden" name="facilityName" id="facilityName" value="${param.facilityName}" />
					<input type="hidden" name="totalLines" id="totalLines" value="${fn:length(facilityListSelectionViewBeanCollection)}" />
					<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
					<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
				</div>
				<!-- Hidden elements end -->
			</div>
			<!-- close of interface -->
		</tcmis:form>
		
		<!-- You can build your error messages below.
		Similar divs would have to be built to show any other messages in a inline pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;"></div>
		<!-- Error Messages Ends -->
	</body>
</html:html>