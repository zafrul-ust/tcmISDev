<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="nested" uri="/WEB-INF/struts-nested.tld"%>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@ taglib prefix="tcmis" uri="/WEB-INF/tcmis.tld"%>
<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta http-equiv="expires" content="-1" />
	<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<%-- For Internationalization, copies data from calendarval.js --%>
	<%@ include file="/common/locale.jsp"%>
	<tcmis:gridFontSizeCss />
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script	type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
	<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
	
	<title>
		<fmt:message key="label.minmaxchangehistory"/>: <tcmis:jsReplace value="${param.titleDesc}"/>
	</title>
	<script language="JavaScript" type="text/javascript">
		var messagesData={
			errors:"<fmt:message key="label.errors"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>"
		};
		
		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'inventoryLevelLogBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',			<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',				<%--  variable to put the grid object in for later use --%>
			config: 'config'					<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
		};
		
		<%-- Define the columns for the result grid --%>
		var config = [
			{
				columnId: "permission"
			},
			{
				columnId:"oldReorderPoint",
				columnName:'<fmt:message key="label.old"/>',
				attachHeader:'<fmt:message key="label.reorderpoint"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"oldStockingLevel",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.stockinglevel"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"newReorderPoint",
				columnName:'<fmt:message key="label.new"/>',
				attachHeader:'<fmt:message key="label.reorderpoint"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"newStockingLevel",
				columnName:'#cspan',
				attachHeader:'<fmt:message key="label.stockinglevel"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"updatedDate",
				columnName:'<fmt:message key="label.updateddate"/>',
				width:7,
				align: 'center'
			},
			{
				columnId:"updatedByName",
				columnName:'<fmt:message key="label.updatedby"/>',
				width:7,
				align: 'center',
				tooltip: true
			},
			{
				columnId:"updatedBy"
			}
		];
	</script>
</head>
<body bgcolor="#ffffff"	onload="popupOnLoadWithGrid(gridConfig);" onresize="resizeFrames();">
	<div class="interface" id="resultsPage" style="">
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<!-- Transit Page Begins -->
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br/><br/><br/><fmt:message key="label.pleasewait"/><br/><br/><br/>
				<img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</div>
			<!-- Transit Page Ends -->
			<div id="resultGridDiv" style="display:none">
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
											Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
											<div id="mainUpdateLinks" style="display:none">
												<%-- mainUpdateLinks Begins --%>
												<div id="updateResultLink" style="display:none">
												</div>
											</div>
										</div>
										<div class="dataContent">
											<div id="inventoryLevelLogBean" style="width: 100%; height: 50px" style="display: none;"/>
											
											<c:choose>
												<%-- If the collection is empty say no data found --%>
												<c:when test="${empty inventoryLevelLogBeanCollection}" >
													<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
														<tr>
															<td width="100%">
																<fmt:message key="main.nodatafound"/>
															</td>
														</tr>
													</table>
												</c:when>
												<c:otherwise>
													<script type="text/javascript">
														<%-- Loop through the results and output each row, formatting the results as necessary
															use tcmis:jsReplace to escape special characters for ANY user entered text
															use fmt:formatNumber to format numbers (existing patterns: unitpricecurrencyformat, totalcurrencyformat, oneDigitformat)
															use mt:formatDate to format dates (existing patterns: dateFormatPattern, dateTimeFormatPattern)--%>
														var jsonMainData = {
															rows:[
																<c:forEach var="myBean" items="${inventoryLevelLogBeanCollection}" varStatus="status">
																	<c:if test="${!status.first}">,</c:if>
																	{
																		id:${status.count},
																		data:[
																			'Y',
																			'${myBean.oldReorderPoint}',
																			'${myBean.oldStockingLevel}',
																			'${myBean.newReorderPoint}',
																			'${myBean.newStockingLevel}',
																			'<fmt:formatDate value="${myBean.updatedDate}" pattern="${dateFormatPattern}" />',
																			'<tcmis:jsReplace value="${myBean.updatedByName}" />',
																			'${myBean.updatedBy}'
																		]
																	}
																</c:forEach>
															]
														};
														
														showUpdateLinks = true;
													</script>
												</c:otherwise>
											</c:choose>
										</div>
										<!-- Footer message start -->
										<div id="footer" class="messageBar"></div>
										<!-- Footer message end -->
									</div>
								</div>
								<div class="roundbottom">
									<div class="roundbottomright">
										<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!-- Hidden Element start -->
			<div id="hiddenElements" style="display: none">
				<input type="hidden" name="totalLines" id="totalLines" value="${fn:length(inventoryLevelLogBeanCollection)}" />
				<input type="hidden" name="startSearchTime" id="startSearchTime" value="${startSearchTime}" />
				<input type="hidden" name="endSearchTime" id="endSearchTime" value="${endSearchTime}" />
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var showMessage = false;
		<c:choose>
			<c:when test="${requestScope['org.apache.struts.action.ERROR'] == null or not empty tcmISErrors or not empty tcmISError}">
				showMessage = true;
			</c:when>
		</c:choose>
	</script>
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
	<!-- Error Messages Begins -->
	<div id="messagesArea" class="errorMessages" style="display: none; overflow: auto;">
		<c:if test="${requestScope['org.apache.struts.action.ERROR'] == null or not empty tcmISErrors or not empty tcmISError}">
			${tcmISError}<br />
			<c:forEach var="tcmisError" items="${tcmISErrors}">
				${tcmisError}<br />
			</c:forEach>
		</c:if>
	</div>
</body>
</html:html>