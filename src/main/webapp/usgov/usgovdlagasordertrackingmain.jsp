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
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
		
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>		
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<!-- For Calendar support for column type hcal-->

		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
		<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
		<script type="text/javascript" src="/js/usgov/usgovdlagasordertrackingmain.js"></script>
				<script type="text/javascript" src="/js/common/standardGridmain.js"></script>


<title>
<fmt:message key="ordertracking.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.searchtextororderdate"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
entersearch:"<fmt:message key="label.pleaseenterasearchargument"/>"};
// 
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','usgovDlaGasOrderTracking');" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">
	<div id="searchFrameDiv">
		<div class="contentArea">
		    <tcmis:form action="/usgovdlagasordertrackingresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
			<!-- Search Option Begins -->
			<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
			<tr><td>
			<div class="roundcont filterContainer">
			 <div class="roundright">
			   <div class="roundtop">
			     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
			   </div>
			   <div class="roundContent">
			    <!-- Insert all the search option within this div -->
			    <table width="100%" border="0" cellpadding="0" cellspacing="3" class="tableSearch">
					<tr>
						<td class="optionTitleBoldLeft" nowrap><fmt:message key="label.search" />:&nbsp;
						<select name="searchField" id="searchField" class="selectBox" rowspan="1">
								<option value="deliveryNo"><fmt:message key="label.deliverynumber" /></option>
								<option value="callNo"><fmt:message key="label.callnumber" /></option>
								<option value="docNumber"><fmt:message key="label.docnumber" /></option>
								<option value="milstripCode"><fmt:message key="label.milstrip" /></option>
								<%-- <option value="poNumber"><fmt:message key="label.customerpo" /></option>
								<option value="trackingNumber"><fmt:message key="label.trackingnumber" /></option>--%>
								<option value="catPartNo"><fmt:message key="label.nsn" /></option>
								<option value="shipToDodaac"><fmt:message key="label.shiptododaac" /></option>
								<option value="markForDodaac"><fmt:message key="label.ultimatedodaac" /></option>					
						</select> 
						&nbsp;<fmt:message key="label.is" />&nbsp;
						<input type="hidden"  name="searchMode" id="searchMode" value="is">
						<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20">
						</td>
					</tr>
					<tr>
			        <td class="optionTitleBoldLeft" nowrap><fmt:message key="label.status" />: &nbsp;
			         <select name="status" id="status" class="selectBox">
			           <option value="All"><fmt:message key="label.all"/></option>
			           <option value="In Progress"><fmt:message key="label.inprogress"/></option>
			           <option value="Shipped"><fmt:message key="label.shipped"/></option>
			           <option value="Partially Shipped"><fmt:message key="label.partiallyshipped"/></option>
			           <option value="Picked not Shipped"><fmt:message key="label.pickedforshipment"/></option>  
			           <option value="Pending VSM"><fmt:message key="label.pendingvsm"/></option>
			           <option value="Canceled"><fmt:message key="label.canceled"/></option>                   
			        </select>
			        &nbsp; <fmt:message key="label.contractnumber" />: &nbsp;
			        <select name="contractNumber" id="contractNumber" class="selectBox">
			        		<option value="SPE4A616D0226">SPE4A616D0226</option>
			        		<option value="SPM4AR07D0100">SPM4AR07D0100</option>
			        </select>
			        </td>
			      </tr>			
			      <tr class="alignLeft">
			        <td width="10%" class="optionTitleBoldLeft" colspan="4">
			            <select name="dateOpt" id="dateOpt" class="selectBox">
			                  <option value="orderDate"><fmt:message key="label.usgovorderdate"/></option>
			                  <option value="dateShipped"><fmt:message key="label.actualshipdate"/></option>
			                  <option value="desiredShipDate"><fmt:message key="label.dlametricdate"/></option>
			            </select>
			            <fmt:message key="label.from"/>
			            <input type="text" name="dateFrom" id="dateFrom" readonly class="inputBox pointer"  style="cursor:pointer;" value="" onclick="return getCalendar(document.getElementById('dateFrom'),null,document.getElementById('dateTo'));" size="10">
			            &nbsp;<fmt:message key="label.to"/>&nbsp;&nbsp;
			            <input type="text" name="dateTo" id="dateTo" readonly class="inputBox pointer"  style="cursor:pointer;" value="" onclick="return getCalendar(document.getElementById('dateTo'),document.getElementById('dateFrom'));" size="10">
			        </td>
			      </tr>
			      <tr class="alignLeft">
			      <td colspan="6" class="optionTitleLeft">
					  <input type="submit" name="search" id="search" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return submitSearchForm()"/>
			 		  <input name="CreateExcelFile" id="CreateExcelFile" type="button" value="<fmt:message key="label.createexcelfile"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel()">
			 		 </td>
			 	   </tr>
			    </table>
			   <!-- End search options -->
			   </div>
			   <div class="roundbottom">
			     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
			   </div>
			 </div>
			</div>
			</td></tr>
			</table>
			<!-- Search Option Ends -->
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
					<input type="hidden" name="action" id="action" value="search"/>
					<input type="hidden" name="startSearchTime" id="startSearchTime" value=""/>
					<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
					<%-- needed if this page will show on application.do --$>
					<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --%>
					<input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
				</div>
			</tcmis:form>
		</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

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
		  </table>
		</div>

</body>
</html:html>
