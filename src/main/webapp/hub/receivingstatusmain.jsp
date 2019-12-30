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
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preferred font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
		
		<script type="text/javascript" src="/js/common/opshubig.js"></script>			<%-- sets up data for OpsEntity/hub/IG pulldowns --%>
				
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		
		<%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/hub/receivingstatusmain.js"></script>
		
		<title>
		    <fmt:message key="label.receivingstatus"/>		<%-- Internationalized page title --%>
		</title>
		
		<script language="JavaScript" type="text/javascript">
		<%-- NOTE: The only javascript here rather than in a specific js file is javascript that contains values from JSP --%>
		<%-- Standard var for Internationalized messages--%>
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			showlegend:"<fmt:message key="label.legend"/>",
			errors:"<fmt:message key="label.errors"/>",  
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			pleaseselect:"<fmt:message key="label.pleaseselect"/>",
			operatingentity:"<fmt:message key="label.operatingentity"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"};
		
		<%-- Settings to allow My Entities, My hubs and My Inventory Group  in the standard pulldowns, used by /js/common/opshubig.js, called by setOps() in onload --%>
		defaults.ops.nodefault = true;
		defaults.hub.nodefault = true;
		defaults.inv.nodefault = false;
		
		//if hub is changed, call igchanged function
		defaults.hub.callback = checkReceivingStatus;
		
		// -->
		</script>
	</head>

	<%-- Name passed in loadLayoutWin needs to match the pageData in application.jsp if the page will be on the pulldwon menu --%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','receivingStatus');setOps();" onresize="resizeFrames()">
	
		<div class="interface" id="mainPage" style="">
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/receivingstatusresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
												<table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
		      												<td nowrap  width="8%" class="optionTitleBoldRight" nowrap>
		      													<fmt:message key="label.operatingentity"/>:
		      												</td>
		      												<td width="6%" class="optionTitleBoldLeft" nowrap>
			         											<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
		      												</td>
													 	<td width="2%" class="optionTitleBoldLeft">
													 		<fmt:message key="label.status"/>:
													 	</td>
														<td width="2%" class="optionTitleBoldLeft">
															<select name="receivingStatus"  class="selectBox" id="receivingStatus" onchange="checkReceivingStatus();">
																<c:forEach var="row" items="${vvReceivingStatusCollection}" varStatus="status">
																	<c:if test='${row.receivingStatus != "Binned"}'>
																		<option value="${row.receivingStatus}" <c:if test='${row.receivingStatus == "QC Ready"}'>selected</c:if>>${row.receivingStatusDesc}</option>
																	</c:if>
																</c:forEach>
													    	</select>
													   	</td>         
													</tr>
													<tr>
														<td nowrap width="6%"  class="optionTitleBoldRight">
															<fmt:message key="label.hub"/>:
														</td>
														<td  width="10%" class="optionTitleBoldLeft">
													   		<select name="hub" id="hub" class="selectBox" ></select>
														</td>	
													 	<td width="2%" class="optionTitleBoldLeft">
													 		<span id="assignedToLabel" style="display: none"><fmt:message key="label.assignedto"/>:</span>
													 	</td>
														<td width="2%" class="optionTitleBoldLeft">
															<select name="assignedTo"  class="selectBox" id="assignedTo" style="display: none">
													    	</select>
													   	</td>         
													</tr>
													<tr>
														<td nowrap  width="8%" class="optionTitleBoldRight">
															<fmt:message key="label.inventorygroup"/>:
														</td>
														<td width="14%" class="optionTitleBoldLeft">
															<select name="inventoryGroup" id="inventoryGroup" class="selectBox"  multiple size="4" >
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
							<input type="hidden" name="uAction" id="uAction" value=""/>
							<input type="hidden" name="hubName" id="hubName" value=""/>
							<input type="hidden" name="inventoryGroupList" id="inventoryGroupList" value=""/>
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
							<%-- needed if this page will show on application.do --$>
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --%>
							<input name="searchHeight" id="searchHeight" type="hidden" value="240"/>
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
													<span id="updateAssigneeLink" style="display: none"><a href="javascript:resultFrame.doUpdate();"><fmt:message key="label.update"/></a> | </span>         
													<span><a href="javascript:showLegend()"><fmt:message key="label.Legend"/></a></span>
					 							</div>
											</div>
											<div class="dataContent">
					  							<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="600" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
		
		<%-- show legend Begins --%>
		<div id="showLegendArea" style="display: none;overflow: auto;">
		  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
		    <tr><td width="100px" class="pink">&nbsp;</td><td class="legendText"><fmt:message key="label.aogsupercriticalpo"/></td></tr>
		    <tr><td width="100px" class="red">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalpo"/></td></tr>
		    <tr><td width="100px" class="orange">&nbsp;</td><td class="legendText"><fmt:message key="label.receiptforexcessmaterialreceivedonpo"/></td></tr>
		    <tr><td width="100px" class="yellow">&nbsp;</td><td class="legendText"><fmt:message key="label.allocatedToOpenPO"/></td></tr>
		    <tr><td width="100px" class="green">&nbsp;</td><td class="legendText"><fmt:message key="label.receiptformlitem"/></td></tr>
		  </table>
		</div>
		<%-- show legend Begins --%>
		<div id="showLegendAreaBinning" style="display: none;overflow: auto;">
		  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
		    <tr><td width="100px" class="pink">&nbsp;</td><td class="legendText"><fmt:message key="label.orderonrequest"/></td></tr>
		    <tr><td width="100px" class="blue">&nbsp;</td><td class="legendText"><fmt:message key="label.notreadyforpick"/></td></tr>
		  </table>
		</div>		
		
	</body>
</html:html>