<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	
		<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>
		<%@ include file="/common/opshubig.jsp" %>						<%-- retrieves data for OpsEntity/hub/IG pulldowns --%>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
		
		<script type="text/javascript" src="/js/common/opshubig.js"></script>			<%-- sets up data for OpsEntity/hub/IG pulldowns --%>
		
		<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		

		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/common/admin/chargenumbermanagementmain.js"></script> 
		
		
		<title><fmt:message key="chargenumbermanagement" /></title>
		
		<script language="JavaScript" type="text/javascript">
			<!-- <%-- NOTE: The only javascript here rather than in a specific js file should be javascript that contains values from JSP --%>

			<%-- Standard var for Internationalized messages--%>
			var messagesData = new Array();
			messagesData={
				alert:"<fmt:message key="label.alert"/>",
				and:"<fmt:message key="label.and"/>",
				all:"<fmt:message key="label.all"/>",
				validvalues:"<fmt:message key="label.validvalues"/>",
				submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
				itemInteger:"<fmt:message key="error.item.integer"/>",
				errors:"<fmt:message key="label.errors"/>",     
				searchInput:"<fmt:message key="error.searchInput.integer"/>"
				};
		
				var acctList = new Array(); 
				var acctCount = 0;
				facToAcctMap = {}; 
				chargeMap = {};
				<c:set var="prevFac" value=""/>	
				<c:forEach var="charge" items="${chargeNumberManagementInput}" varStatus="chargeStatus">
					<c:choose>
						<c:when test="${prevFac == charge.facilityId}">	
						<c:if test="${charge.chargeType == 'd'}">		
							acctCount++;
							acctList[acctCount] = '${charge.accountSysId}';
						</c:if>
						</c:when>
						<c:otherwise>
							acctList = new Array();
							acctCount=0;
							acctList[acctCount] = '${charge.accountSysId}';
							facToAcctMap['${charge.facilityId}'] = acctList;
						</c:otherwise>
					</c:choose>
					<c:set var="prevFac" value="${charge.facilityId}"/>
						var tmp = new Array();
						var tmp = {
					rows:[
						'${charge.chargeField1Control} ${charge.chargeLabel1}',
						'${charge.chargeField2Control} ${charge.chargeLabel2}',
						'${charge.chargeField3Control} ${charge.chargeLabel3}',
						'${charge.chargeField4Control} ${charge.chargeLabel4}',
						'${charge.chargeId1}',
						'${charge.chargeId2}',
						'${charge.chargeId3}',
						'${charge.chargeId4}',
						'${charge.noLevel1ChgAcctApprover}', 
						'${charge.noLevel2ChgAcctApprover}',
						'${charge.noLevel3ChgAcctApprover}',
						'${charge.noLevel4ChgAcctApprover}',
						'${charge.approveByPrice}'
						]};
					chargeMap['${charge.accountSysId}-${charge.chargeType}'] = tmp;
				</c:forEach>

		
			// -->
		 </script>
	</head>

	<%--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
	If you dont want to use the layout set javascript variable useLayout=false;--%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','chargeNumberManagement');displaySearchArgs();" onresize="resizeFrames()">
		<div class="interface" id="mainPage" style="">
			<%-- Search Section --%>
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/chargenumbermanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
						<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
											</div>
											<div class="roundContent"><%-- Insert all the search option within this div --%>
												<table width="400" height = "130" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
														<td nowrap width="20%" class="optionTitleBoldRight" nowrap>
															<fmt:message key="label.facility" />:
														</td>
														<td>
															<select name="facilityId" id="facilityId" class="selectBox" onchange="displaySearchArgs()">		
																<c:set var="prevFac" value=""/>	
																<c:forEach var="fac" items="${chargeNumberManagementInput}" varStatus="facStatus">
																	<c:if test="${prevFac != fac.facilityId}">
																		<option value="${fac.facilityId}"><c:out value="${fac.facilityName}"></c:out></option>
																	</c:if>
																	<c:set var="prevFac" value="${fac.facilityId}"/>	
																</c:forEach>
															</select>
														</td>
													</tr>
													<tr>
														<td nowrap width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.accountsys" />:
														</td>
														<td>
															<select name="accountSysId" id="accountSysId" class="selectBox" onchange="showCharge(value,null)">
															</select>
														</td>
													</tr>
													<tr id="chargeTypeRow" style="display: none">
														<td class="optionTitleBoldLeft"></td>
														<td nowrap>
															<input name="directCharge" id="directCharge" type="radio" class="radioBtns" value="" checked ="checked" onclick="document.getElementById('inDirectCharge').checked = false;showCharge($v('accountSysId'),'d')"><fmt:message key="label.direct"/>
															<input name="inDirectCharge" id="inDirectCharge" type="radio" class="radioBtns" value="" onclick="document.getElementById('directCharge').checked = false;showCharge($v('accountSysId'),'i')"><fmt:message key="label.indirect"/>									
														</td>
													</tr>
													<tr id="chargeRow1" style="display: none">
														<td class="optionTitleBoldRight" id="charge1Display" nowrap></td>
														<td  class="optionTitleBoldLeft" id="charge1Content" nowrap></td>
														<td class="optionTitleBoldRight" id="charge2Display" nowrap></td>
														<td  class="optionTitleBoldLeft" id="charge2Content" nowrap></td>
													</tr>
													<tr id="chargeRow2" style="display:">
														<td class="optionTitleBoldRight" id="charge3Display" nowrap></td>
														<td  class="optionTitleBoldLeft" id="charge3Content" nowrap></td>
														<td class="optionTitleBoldRight" id="charge4Display" nowrap></td>
														<td  class="optionTitleBoldLeft" id="charge4Content" nowrap></td>
													</tr>
													<tr>
														<td class="optionTitleBoldLeft" colspan="2">
															<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" 
																class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
																onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
															<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "return createXSL()"/>
														</td>
													</tr>
													<tr height = "17"></tr>
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
						<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
							<div class="spacerY">&nbsp;
								<div id="searchErrorMessagesArea" class="errorMessages">
									<html:errors />
								</div>
							</div>
						</c:if>
						<div id="hiddenElements" style="display: none;">
							<input name="uAction" id="uAction" type="hidden" value="">
							<input name="chargeType" id="chargeType" type="hidden" value="d">	
							<input name="chargeId1" id="chargeId1" Id="hidden" value="">
							<input name="chargeId2" id="chargeId2" Id="hidden" value="">
							<input name="chargeId3" id="chargeId3" Id="hidden" value="">
							<input name="chargeId4" id="chargeId4" Id="hidden" value="">
							<input name="companyId" id="companyId" type="hidden" value="${compId}">      
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript section in main.jsp.  --%> 
							<input name="searchHeight" id="searchHeight" type="hidden" value="1000">
							<input name="chargeNumber1Exists" id="chargeNumber1Exists" type="hidden" value="N">
						    <input name="chargeNumber2Exists" id="chargeNumber2Exists" type="hidden" value="N">
						    <input name="chargeNumber3Exists" id="chargeNumber3Exists" type="hidden" value="N">
						    <input name="chargeNumber4Exists" id="chargeNumber4Exists" type="hidden" value="N">
						    <input name="chargeNumber1" id="chargeNumber1" type="hidden" value="">
						    <input name="chargeNumber2" id="chargeNumber2" type="hidden" value="">
						    <input name="chargeNumber3" id="chargeNumber3" type="hidden" value="">
						    <input name="chargeNumber4" id="chargeNumber4" type="hidden" value="">
						    <input name="chargeLabel1" id="chargeLabel1" type="hidden" value="">
						    <input name="chargeLabel2" id="chargeLabel2" type="hidden" value="">
						    <input name="chargeLabel3" id="chargeLabel3" type="hidden" value="">
						    <input name="chargeLabel4" id="chargeLabel4" type="hidden" value="">
						    <input name="noLevel1ChgAcctApprover" id="noLevel1ChgAcctApprover" type="hidden" value="">
						    <input name="noLevel2ChgAcctApprover" id="noLevel2ChgAcctApprover" type="hidden" value="">
						    <input name="noLevel3ChgAcctApprover" id="noLevel3ChgAcctApprover" type="hidden" value="">
						    <input name="noLevel4ChgAcctApprover" id="noLevel4ChgAcctApprover" type="hidden" value="">
						    <input name="approveByPrice" id="approveByPrice" type="hidden" value="">
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
													<div id="updateResultLink" style="display: none">
														<a href="#" onclick="resultFrame.addRow();"><fmt:message key="label.add" /></a> | 
														<a href="#" onclick="resultFrame.update();"><fmt:message key="label.update" /></a>
													</div>
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
		<%-- Error Messages Div --%>
		<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
	
	</body>
</html:html>
