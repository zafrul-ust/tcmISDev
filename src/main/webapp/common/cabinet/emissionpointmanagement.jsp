<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

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
	<tcmis:gridFontSizeCss />
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<!-- This handles all the resizing of the page and frames -->
	<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- This handels the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp"%>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/cabinet/emissionpointmanagement.js"></script>
	<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
	
	<!-- These are for the Grid-->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<title><fmt:message key="label.emissionpointfor" /> <tcmis:jsReplace value="${param.application}" /></title>
	
	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		messagesData = {
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			recordFound:"<fmt:message key="label.recordFound"/>",
			norowselected:"<fmt:message key="error.norowselected"/>",
			searchDuration:"<fmt:message key="label.searchDuration"/>",
			minutes:"<fmt:message key="label.minutes"/>",
			seconds:"<fmt:message key="label.seconds"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			genericError:"<fmt:message key="generic.error"/>",
			enterValidValues:"<fmt:message key="msg.errorHeader"/>",
			facilityEmissionPoint:"<fmt:message key="label.facilityemissionpoint"/>",
			workAreaEmissionPoint:"<fmt:message key="label.workareaemissionpoint"/>",
			rowX:"<fmt:message key="label.rowx"/>",
			deleteStr:"<fmt:message key="label.delete"/>"
		};
		
		var config = [
			{//0
				columnId:"permission"
			},
			{
				columnId:"facEmissionPointPermission"
			},
			{
				columnId:"appEmissionPointPermission"
			},
			{
				columnId:"active",
				columnName:'<fmt:message key="label.status"/>',
				type:'hcoro',
				align:'center',
				onChange: compareToOriginalVal,
				width:6
			},
			{
				columnId:"facEmissionPoint",
				columnName:'<fmt:message key="label.facilityemissionpoint"/>',
				type:'hed',
				align:'center',
				maxlength: 30,
				width:8,
				permission:true
			},
			{//5
				columnId:"appEmissionPoint",
				columnName:'<fmt:message key="label.workareaemissionpoint"/>',
				type:'hed',
				align:'center',
				maxlength: 30,
				width:10,
				permission:true
			},
			{
				columnId:"appEmissionPointDesc",
				columnName:'<fmt:message key="label.description"/>',
				type:'hed',
				width:40,
				maxlength: 100,
				onChange: compareToOriginalVal,
				tooltip:"Y"
			},
			{
				columnId:"isNew"
			},
			{
				columnId:"changed"
			},
			{
				columnId:"originalActive"
			},
			{//10
				columnId:"originalAppEmissionPointDesc"
			}
		];
		
		<%-- Define status's types --%>
		var active = [
			{
				text:'<fmt:message key="label.active"/>',
				value:'Y'
			},
			{
				text:'<fmt:message key="label.inactive"/>',
				value:'N'
			}
		];
		
		<%-- Define the grid options--%>
		var gridConfig = {
			divName: 'facAppEmissionPointBean',			<%--  the div id for the grid. This is the also the variable name used to pass data back in updates --%>
			beanData: 'jsonMainData',					<%--  the data variable name for jsonparse, as in grid.parse( beanData ,"json" ) --%>
			beanGrid: 'beanGrid',						<%--  variable to put the grid object in for later use --%>
			config: 'config',							<%--  the column config var name, as in var columnConfig = { [ columnId:..,columnName... --%>
			submitDefault: true,
			onRightClick: buildRightClickOption			<%--  a javascript function to be called on right click with rowId & cellId as args --%>
		};
		
		<%-- Define the right click menus --%>
    	with(milonic=new menuname("rightClickMenu")){
			top="offset=2"
			style = contextStyle;
			margin=3
			aI("text=;url=;image=");
    	}

    	<%-- Initialize the Right Mouse Click Menus --%>
    	drawMenus();
		
		showUpdateLinks = true;
	</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid();" onresize="myResize();" onunload="opener.parent.newCloseTransitWin(window.name);">
	<tcmis:form action="/emissionpointmanagement.do" onsubmit="return submitFrameOnlyOnce();">
		<div class="interface" id="resultsPage">
			<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
			So this is just used to feed the pop-up in the main page. Similar divs would have to be built to show any other messages in a pop-up.-->
			<!-- Error Messages Begins -->
			<div id="errorMessagesAreaBody" style="display: none;">
				${tcmISError}<br />
				<c:forEach items="${tcmISErrors}" varStatus="status">
					${status.current}<br />
				</c:forEach>
			</div>

			<script type="text/javascript">
				/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
				<c:choose>
					<c:when test="${empty tcmISErrors and empty tcmISError}">
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
					<br /><br /><br />
					<fmt:message key="label.pleasewait" />
					<br /><br /><br />
					<img src="/images/rel_interstitial_loading.gif" align="middle" />
				</div>
				<!-- Transit Page Ends -->
				<div id="resultGridDiv" style="display: none;">
					<!-- results start -->
					<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
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
												<div id="mainUpdateLinks">
													<%-- mainUpdateLinks Begins --%>
													<span id="updateResultLink" style="display: none">
														<a href="#" onclick="addNewEmissionPoint();"><fmt:message key="label.newemissionpoint" /></a> | 
														<a href="#" onclick="updateEmissionPoint();"><fmt:message key="label.update" /></a> |
													</span>
													<a href="#" onclick="window.close()"><fmt:message key="label.close" /></a> &nbsp;
												</div>
												<%-- mainUpdateLinks Ends --%>
											</div>
											<%-- boxhead Ends --%>
											<div class="dataContent">
												<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
												<div id="facAppEmissionPointBean" style="width: 100px; height: 300px;" style="display: none;"></div>
												<!-- Search results start -->
												<c:if test="${facAppEmissionPointBeanCollection != null}">
													
															<script type="text/javascript">
																/*Storing the data to be displayed in a JSON object array.*/
																var jsonMainData = {
																	rows:[
																		<c:forEach var="bean" items="${facAppEmissionPointBeanCollection}" varStatus="status">
																			<c:if test="${status.index > 0}">,</c:if>
																			/*The row ID needs to start with 1 per their design.*/
																			{
																				id:${status.count},
																				data: [ 
																					'Y',
																					'N',
																					'N',
																					'${bean.active}',
																					'<tcmis:jsReplace value="${bean.facEmissionPoint}" />',
																					'<tcmis:jsReplace value="${bean.appEmissionPoint}" />',
																					'<tcmis:jsReplace value="${bean.appEmissionPointDesc}" processMultiLines="true" />',
																					false,
																					false,
																					'${bean.active}',
																					'<tcmis:jsReplace value="${bean.appEmissionPointDesc}" processMultiLines="true" />'
																				]
																			}
																		</c:forEach>
																	]
																};
															</script>
															<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable" style="display:none">
																<tr>
																	<td width="100%"><fmt:message key="main.nodatafound" /></td>
																</tr>
															</table>
												</c:if>
												<!-- Search results end -->
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
					<!-- results end -->
				</div>
			</div>
			<!-- Result Frame Ends -->

			<!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
				<%-- Retrieve all the Search Criteria here for re-search after update--%>
				<tcmis:setHiddenFields beanName="searchInput" />
				<input type="hidden" id="startSearchTime" name="startSearchTime" value="${startSearchTime}"/>
				<input type="hidden" id="endSearchTime" name="endSearchTime" value="${endSearchTime}"/>
			</div>
			<!-- Hidden elements end -->
		</div>
		<!-- close of interface -->
	</tcmis:form>

	<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
</body>
</html:html>

