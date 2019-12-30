<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
		
		<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>
		<%@ include file="/common/opshubig.jsp" %>						<%-- retrieves data for OpsEntity/hub/IG pulldowns --%>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
		
		<script type="text/javascript" src="/js/common/opshubig.js"></script>			<%-- sets up data for OpsEntity/hub/IG pulldowns --%>
				
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
		
		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/client/catalog/chargenumberreport.js"></script>
		
		<title>
		    <fmt:message key="chargeNumberReport"/>		<%-- Internationalized page title --%>
		</title>
		
		<script language="JavaScript" type="text/javascript">
		<%-- NOTE: The only javascript here rather than in a specific js file is javascript that contains values from JSP --%>
		<%-- Standard var for Internationalized messages--%>
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			myFacilities:"<fmt:message key="label.myfacilities"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			errors:"<fmt:message key="label.errors"/>",  
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			pleaseselect:"<fmt:message key="label.pleaseselect"/>",
			operatingentity:"<fmt:message key="label.operatingentity"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"};
		
		<%-- Settings to allow My Entities, My hubs and My Inventory Group  in the standard pulldowns, used by /js/common/opshubig.js, called by setOps() in onload --%>
		//defaults.ops.nodefault = true;
		//defaults.hub.nodefault = true;
		// defaults.inv.nodefault = true;
		
		//if hub is changed, call igchanged function
		//defaults.hub.callback = igchanged;
		
		var altFacilityId = new Array(
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${facBean.facilityId}"/>'
		</c:forEach>
		);

		var altFacilityName = new Array(
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${facBean.facilityName}"/>'
		</c:forEach>
		);

		var altAreaId = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			altAreaId['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
				<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
					<c:if test="${status1.index > 0}">,</c:if>
					'${areaBean.areaId}'
				</c:forEach>
		);
		</c:forEach>

		var altAreaName = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			altAreaName['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
				<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
					<c:if test="${status1.index > 0}">,</c:if>
					'<tcmis:jsReplace value="${areaBean.areaName}"/>'
				</c:forEach>
		);
		</c:forEach>

		var altBuildingId = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
		        <c:set var="currentArea" value='${areaBean.areaId}'/>
		        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
		        altBuildingId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/>'] = new Array(
		            <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
		                <c:if test="${status2.index > 0}">,</c:if>
		                '${bldgBean.buildingId}'
		            </c:forEach>
		        );
		    </c:forEach>
		</c:forEach>

		var altBuildingName = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
		        <c:set var="currentArea" value='${areaBean.areaId}'/>
		        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
		        altBuildingName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/>'] = new Array(
		            <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
		                <c:if test="${status2.index > 0}">,</c:if>
		                '<tcmis:jsReplace value="${bldgBean.buildingName}"/>'
		            </c:forEach>
		        );
		    </c:forEach>
		</c:forEach>

		var altFloorId = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
		        <c:set var="currentArea" value='${areaBean.areaId}'/>
		        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
		        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
		            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
		            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
		            altFloorId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/>'] = new Array(
		            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
		                <c:if test="${status3.index > 0}">,</c:if>
		                '${floorBean.floorId}'
		            </c:forEach>
		            );
		        </c:forEach>
		    </c:forEach>
		</c:forEach>

		var altFloorName = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
		        <c:set var="currentArea" value='${areaBean.areaId}'/>
		        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
		        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
		            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
		            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
		            altFloorName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/>'] = new Array(
		            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
		                <c:if test="${status3.index > 0}">,</c:if>
		                '<tcmis:jsReplace value="${floorBean.description}"/>'
		            </c:forEach>
		            );
		        </c:forEach>
		    </c:forEach>
		</c:forEach>

		var altRoomId = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
		        <c:set var="currentArea" value='${areaBean.areaId}'/>
		        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
		        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
		            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
		            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
		            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
			            <c:set var="currentFloor" value='${floorBean.floorId}'/>
			            <c:set var="roomCollection" value='${floorBean.roomList}'/>
				        altRoomId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/><c:out value="${currentFloor}"/>'] = new Array(
				            <c:forEach var="roomBean" items="${roomCollection}" varStatus="status4">
				                <c:if test="${status4.index > 0}">,</c:if>
				                '${roomBean.roomId}'
				            </c:forEach>
			            );
		            </c:forEach>
		        </c:forEach>
		    </c:forEach>
		</c:forEach>

		var altRoomName = new Array();
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
			<c:set var="currentFacility" value='${facBean.facilityId}'/>
			<c:set var="areaCollection" value='${facBean.areaList}'/>
			<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
		        <c:set var="currentArea" value='${areaBean.areaId}'/>
		        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
		        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
		            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
		            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
		            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
			            <c:set var="currentFloor" value='${floorBean.floorId}'/>
			            <c:set var="roomCollection" value='${floorBean.roomList}'/>
			            altRoomName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/><c:out value="${currentFloor}"/>'] = new Array(
				            <c:forEach var="roomBean" items="${roomCollection}" varStatus="status4">
				                <c:if test="${status4.index > 0}">,</c:if>
				                '<tcmis:jsReplace value="${roomBean.roomName}"/>'
				            </c:forEach>
			            );
			        </c:forEach>
		        </c:forEach>
		    </c:forEach>
		</c:forEach>

		var deptColl = {};
		var wagColl = {};
		var acctSysColl = {};
		<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="facStatus">
			var currDepArr = new Array();
			<c:forEach var="deptBean" items="${facBean.deptList}" varStatus="deptStatus">
					currDepArr[<c:out value="${deptStatus.index}"/>] = {deptName:'${deptBean.deptName}',
																	  deptId:'${deptBean.deptId}'};
			</c:forEach>
			deptColl['${facBean.facilityId}'] = currDepArr;

			var currWagArr = new Array();
			<c:forEach var="wagBean" items="${facBean.reportingEntityList}" varStatus="wagStatus">
					currWagArr[<c:out value="${wagStatus.index}"/>] = {wagName:'${wagBean.description}',
																	  wagId:'${wagBean.reportingEntityId}'};
			</c:forEach>
			wagColl['${facBean.facilityId}'] = currWagArr;

			var currAcctSysArr = new Array();
			<c:forEach var="acctSysBean" items="${facBean.accountSysList}" varStatus="acctSysStatus">
				currAcctSysArr[<c:out value="${acctSysStatus.index}"/>] = {acctSysName:'${acctSysBean.accountSysDesc}',
																			acctSysId:'${acctSysBean.accountSysId}'};
			</c:forEach>
			acctSysColl['${facBean.facilityId}'] = currAcctSysArr;
		</c:forEach>
		

		// -->
		</script>
	</head>

	<%-- Name passed in loadLayoutWin needs to match the pageData in application.jsp if the page will be on the pulldwon menu --%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','chargeNumberReport');showFacilityOptions()" onresize="resizeFrames()">
	
		<div class="interface" id="mainPage" style="">
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/chargenumberreportresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
												<table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
														<td width="10%" class="optionTitleBoldLeft" colspan="9" >
															<fmt:message key="label.facility"/>:&nbsp;
															<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
															</select>
															&nbsp;&nbsp;&nbsp;
															<fmt:message key="label.area"/>:&nbsp;
															<select class="selectBox" id="areaId" name="areaId" onchange="areaChanged()">
															</select>
															&nbsp;&nbsp;&nbsp;
															<fmt:message key="label.building"/>:&nbsp;
															<select class="selectBox" id="buildingId" name="buildingId" onchange="buildingChanged()">
															</select>
															&nbsp;&nbsp;&nbsp;
															<fmt:message key="label.floor"/>:&nbsp;
															<select class="selectBox" id="floorId" name="floorId" onchange="floorChanged()">
															</select>
															&nbsp;&nbsp;&nbsp;
															<fmt:message key="label.room"/>:&nbsp;
															<select class="selectBox" id="roomId" name="roomId">
															</select>
													    </td>
													</tr>
													<tr>
													    <td class="optionTitleBoldLeft" colspan="2" width="10%" nowrap>
															<fmt:message key="label.workareagroup"/>:
															<select class="selectBox" id="reportingEntityId" name="reportingEntityId">
															</select>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<fmt:message key="label.department"/>:&nbsp;
															<select class="selectBox" id="deptId" name="deptId">
															</select>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<fmt:message key="label.accountsystem"/>:&nbsp;
															<select class="selectBox" id="accountSysId" name="accountSysId">
															</select>
													    </td>
													</tr>     
													<tr>
														<td colspan="2"  class="optionTitleBoldLeft" nowrap>
															<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"/>
															<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "return createXSL()"/>
														</td>
													</tr>   
		    									</table>
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
						<%-- Struts Error Messages --%>
						<%-- Build this section only if there is an error message to display.
						     For the search section, we show the error messages within its frame--%>
						<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
							<div class="spacerY">&nbsp;
								<div id="searchErrorMessagesArea" class="errorMessages">
									<html:errors/>
								</div>
							</div>
						</c:if>

						<%-- Hidden elements to use in javascript or send back to server --%>
						<div id="hiddenElements" style="display: none;">
							<input type="hidden" name="uAction" id="uAction" value="search"/>
							<input type="hidden" name="hubName" id="hubName" value=""/>
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
							<%-- needed if this page will show on application.do --$>
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --%>
							<input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
						</div>
					</tcmis:form>
				</div>
			</div>
			<%-- Results Begin --%>
			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<%-- div for inline popup "Please Wait" window --%>
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br/>
					<br/>
					<br/>
					<fmt:message key="label.pleasewait"/>
				  	<br/>
				  	<br/>
				  	<br/>
				  	<img src="/images/rel_interstitial_loading.gif" align="middle"/>
				</div>
			
				<div id="resultGridDiv" style="display: none;">
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
												     Please keep the <span></span> on the same line this will avoid extra virtical space.
												 --%>     
												<div id="mainUpdateLinks" style="display: none">
					 							</div>
											</div>
											<div class="dataContent">
					  							<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
											</div>					
											<div id="footer" class="messageBar"></div> <%-- result count and time --%>
					
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
		</div>
	
		<%-- Error message inline pop-up.--%>
		<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
		</div>
		
		<%-- Div for show legend inline popup --%>
		<div id="showLegendArea" style="display: none;overflow: auto;">
			<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
		   		<tr>
		   			<td width="100px" class="yellow legendText">
		   				&nbsp;
		   			</td>
		   			<td class="legendText">
		   				<fmt:message key="label.pendingccauth"/>
		   			</td>
		   		</tr>
		    		<tr>
		    			<td width="100px" class="red legendText">
		    				&nbsp;
		    			</td>
		    			<td class="legendText">
		    				<fmt:message key="label.qualityhold"/>
		    			</td>
		    		</tr>
		  </table>
		</div>
	</body>
</html:html>