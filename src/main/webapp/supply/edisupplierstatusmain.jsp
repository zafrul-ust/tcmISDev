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
					<%-- retrieves data for OpsEntity/hub/IG pulldowns --%>
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
				
		<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		
				<!-- For Calendar support for column type hcal-->

		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
		<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

		<title><fmt:message key="ediSupplierPOStatus"/></title>
		
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
				showlegend:"<fmt:message key="label.showlegend"/>",
				searchInput:"<fmt:message key="error.searchInput.integer"/>"
				};
			
			<%-- Settings to allow My Entities, My hubs and My Inventory Group  in the standard pulldowns, used by /js/common/opshubig.js, called by setOps() in onload --%>
			//defaults.ops.nodefault = true;
			//defaults.hub.nodefault = true;
			// defaults.inv.nodefault = true;
			
			//if hub is changed, call hubchanged function
			//defaults.hub.callback = hubchanged;
			
	function showLegend() {
		var showLegendArea = document.getElementById("showLegendArea");
		showLegendArea.style.display = "";
	
		var dhxWins = new dhtmlXWindows()
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
		if (!dhxWins.window(messagesData.showlegend)) {
			// create window first time
			var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 150);
			legendWin.setText(messagesData.showlegend);
			legendWin.clearIcon(); // hide icon
			legendWin.denyResize(); // deny resizing
			legendWin.denyPark(); // deny parking
			legendWin.attachObject("showLegendArea");
			legendWin.attachEvent("onClose", function(legendWin) {
				legendWin.hide();
			});
			legendWin.center();
		}
		else {
			// just show
			dhxWins.window("legendwin").show();
		}
	}

		

		 </script>
	</head>

	<%--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
	If you dont want to use the layout set javascript variable useLayout=false;--%>
	<body bgcolor="#ffffff" onload="loadLayoutWin('','openPos');" onresize="resizeFrames()">
		<div class="interface" id="mainPage" style="">
			<%-- Search Section --%>
			<div id="searchFrameDiv">
				<div class="contentArea">
					<tcmis:form action="/edisupplierstatusresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
						<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
											</div>
											<div class="roundContent"><%-- Insert all the search option within this div --%>
												<table width="500" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
														<td nowrap width="20%" class="optionTitleBoldRight" nowrap>
															<fmt:message key="label.company" />:
														</td>
														<td width="50%" class="optionTitleBoldLeft">
															<select name="companyId" id="companyId" class="selectBox">
															<option value="3M Aerospace">3M Aerospace</option>
														    <option value="Old World Automotive Products">Old World Automotive Products</option>
															</select>
														</td>
													</tr>
													<tr>
														<td width="20%" class="optionTitleBoldRight">
															<fmt:message key="label.search" />:
														</td>
														<td width="50%" class="optionTitleBoldLeft" nowrap>
															<select name="searchField" class="selectBox" id="searchField">
																<option value="radianPo" selected><fmt:message key="label.haaspo" /></option>
																<option value="ediAcknowledgementCode"><fmt:message key="label.acknowledgementcode" /></option>
																
															</select>
															<select name="searchMode" class="selectBox" id="searchMode">
																<option value="is"><fmt:message key="label.is" /></option>
																<option value="contains"><fmt:message key="label.contains" /></option>
																<option value="startsWith"><fmt:message key="label.startswith" /></option>
																<option value="endsWith"><fmt:message key="label.endswith" /></option>
															</select>
															<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15">
														</td>
													</tr>
													<tr>
													<td nowrap class="optionTitleBoldRight"><fmt:message key="label.datereceived"/>:</td>
														<td nowrap class="optionTitleBoldLeft">
															<fmt:message key="label.from"/>
															<input class="inputBox pointer" readonly type="text" name="dateInsertedFromDate" id="dateInsertedFromDate" value="" onClick="return getCalendar(document.genericForm.dateInsertedFromDate,null,document.genericForm.dateInsertedToDate);" maxlength="10" size="9"/>
															<fmt:message key="label.to"/>&nbsp;
															<input class="inputBox pointer" readonly type="text" name="dateInsertedToDate" id="dateInsertedToDate" value="" onClick="return getCalendar(document.genericForm.dateInsertedToDate,document.genericForm.dateInsertedFromDate);" maxlength="10" size="9"/>
														</td>	  
													</tr>
													<tr>
														<td colspan="2" nowrap="">
													   		<input type="checkbox" name="showConfirmedPOs" value="N" id="showConfirmedPOs" onclick="if(this.checked)this.value='Y';else this.value ='N'"/>
															<span class="optionTitleBoldLeft">Show Confirmed POs</span>&nbsp;
														</td>
												 	</tr> 
													<tr>
														<td class="optionTitleBoldLeft" colspan="2">
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
						<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
							<div class="spacerY">&nbsp;
								<div id="searchErrorMessagesArea" class="errorMessages">
									<html:errors />
								</div>
							</div>
						</c:if>
						<div id="hiddenElements" style="display: none;">
							<input name="uAction" id="uAction" type="hidden" value=""> 
							<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
							<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript section in main.jsp.  --%> 
							<input name="searchHeight" id="searchHeight" type="hidden" value="214">
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
												<div id="mainUpdateLinks">
													<span id="showlegendLink">
														<a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a>
													</span>
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
		<%-- Error Messages Div --%>
		<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
		
				<%-- Div for show legend inline popup --%>
		<div id="showLegendArea" style="display: none;overflow: auto;">
		<table width=100% class="tableResults" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="100px">AC</td>
				<td class="legendText">Item Accepted and Shipped</td>
			</tr>
			<tr>
				<td width="100px">AR</td>
				<td class="legendText">Item Accepted and Released for Shipment
					Inventory has been allocated to fill this item, and it is in some
					phase of the shipping process. Inventory may have been: allocated,
					released to the warehouse for shipment, in the process of being
					picked, picked and on the dock, or picked and loaded on the
					trailer.</td>
			</tr>
		</table>
	</div>
	
	</body>
</html:html>