<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
	<%@ include file="/common/opshubig.jsp"%> 
	<!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss /> 
	
	<%-- Add any other stylesheets you need for the page here --%> 
	<script type="text/javascript" src="/js/common/formchek.js"></script> 
	<script type="text/javascript" src="/js/common/commonutil.js"></script> 

	<!-- This handles all the resizing of the page and frames --> 
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> 
	
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script> 
	
	<!-- This handels the menu style and what happens to the right click on the whole page --> 
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
	

	
	<!-- For Calendar support --> 
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script> 
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script> 
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script> 
	
	<!-- Add any other javascript you need for the page here --> 
	<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
	<script type="text/javascript" src="/js/common/opshubig.js"></script> 
	<script type="text/javascript" src="/js/hub/transferrequestmain.js"></script> 
	
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	
	
	
	<title><fmt:message key="transferrequest.title"/></title>
	
	<script language="JavaScript" type="text/javascript">
	<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
			itemInteger:"<fmt:message key="error.item.integer"/>",
			errors:"<fmt:message key="label.errors"/>",     
			results:"<fmt:message key="label.results"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",	
			sameInventoryGroup:"<fmt:message key="label.sameInventoryGroup"/>",
			sameHub:"<fmt:message key="label.sameHub"/>",
			destInventoryGroup:"<fmt:message key="label.destInventoryGroup"/>",
			searchInput:"<fmt:message key="error.searchInput.integer"/>"
			};
		
		// Can be commented out to show My Entities, My hubs and My Inventory Groups in the pulldowns
		defaults.ops.nodefault = true;
		defaults.hub.nodefault = true;
		//defaults.inv.nodefault = true;
        defaults.ops.callback = checkDestOpsChanged;

var allopshubig = [
	<c:forEach var="opsEntity" items="${destinationHubInvGroupOvBeanColl}" varStatus="status"> 
	  <c:if test="${status.index > 0}">,</c:if>
	  {
		  id: '${opsEntity.opsEntityId}', 
		  name: '<tcmis:jsReplace value="${opsEntity.operatingEntityName}"/>',
		  coll: [<c:forEach var="hub" items="${status.current.hubIgCollection}" varStatus="status1"> 
		  		<c:if test="${status1.index > 0}">,</c:if>
		  		{ 
			  	  id: '${hub.hub}', 
			  	  name: '<tcmis:jsReplace value="${hub.hubName}"/>',
				  coll:[<c:forEach var="inventoryGroup" items="${hub.inventoryGroupCollection}" varStatus="status2">
					  	<c:if test="${status2.index > 0}">,</c:if>
					 	{ 
					 		id:'${inventoryGroup.inventoryGroup}', 
					 		name:'<tcmis:jsReplace value="${inventoryGroup.inventoryGroupName}"/>'
					 	}
			 		 </c:forEach>]
				}
	        </c:forEach>]
	}
	</c:forEach>]; 

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
    aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
    aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
}

drawMenus();

var searchMyArr = new Array(
		{value:'contains', text: '<fmt:message key="label.contains"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'}
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'}
	);
		
	// -->
	 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','transferRequest');setOps();setDestOps();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">
	<!-- Search Frame Begins -->
	<div id="searchFrameDiv">
		<div class="contentArea">
				<tcmis:form action="/transferrequestresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
				<!-- Search Option Begins -->
				<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
						<div class="roundcont filterContainer">
							<div class="roundright">
								<div class="roundtop">
									<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
									</div>
									<div class="roundContent"><!-- Insert all the search option within this div -->
										<table width="500" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
											<%-- Row 1 --%>
											<tr>
												<td nowrap width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity" />:</td>
												<td width="50%" class="optionTitleBoldLeft">
													<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
												</td>
											</tr>
											<%-- Row 2 --%>
											<tr>
												<td nowrap width="20%" class="optionTitleBoldRight"><fmt:message key="label.fromhub" />:</td>
												<td width="50%" class="optionTitleBoldLeft">
													<select name="hub" id="hub" class="selectBox"></select>
												</td>
												<td nowrap width="20%" class="optionTitleBoldRight"><fmt:message key="label.tohub" />:</td>
												<td width="50%" class="optionTitleBoldLeft">
													<select name="destHub" id="destHub" class="selectBox"></select>
												</td>
											</tr>
											<%-- Row 3 --%>
											<tr>
												<td nowrap width="20%" class="optionTitleBoldRight"><fmt:message key="label.frominventorygroup" />:</td>
												<td width="50%" class="optionTitleBoldLeft" >
													<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
												</td>
										        <td nowrap width="20%" class="optionTitleBoldRight"><fmt:message key="label.toinventorygroup" />:</td>
												<td width="50%" class="optionTitleBoldLeft" >
													<select name="destInventoryGroup" id="destInventoryGroup" class="selectBox"></select>
												</td>
											</tr>
											<tr>
											  <td width="20%" class="optionTitleBoldRight"><fmt:message key="label.search" />:</td>
												<td width="50%" class="optionTitleBoldLeft" nowrap>
													<select name="searchField" class="selectBox" id="searchField" onchange="changeSearchTypeOptions(this.value)">
														<option value="itemId"><fmt:message key="label.itemid" /></option>
														<option value="itemDesc"><fmt:message key="label.description" /></option>
														<option value="distCustomerPartList"><fmt:message key="label.customerpartnumber" /></option>
													</select>
													<select name="searchMode" class="selectBox" id="searchMode">
														<option value="is"><fmt:message key="label.is" /></option>
														<option value="startsWith"><fmt:message key="label.startswith" /></option>
													</select>
													<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15">
												</td>
											</tr>
											<tr>
												<td nowrap width="20%" class="optionTitleBoldRight" nowrap></td>
												<td width="20%" class="optionTitleBoldLeft">
												<input type="checkbox" name="includeNoInventory" id="includeNoInventory" value="" /><fmt:message key="label.includeNoInventory"/></td>
										    </tr>		
											<%-- Row 5 --%>
											<tr>
												<td class="optionTitleBoldLeft" colspan="2">
													<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" 
														class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
														onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
												    <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		                                       onclick= "return createXSL()"/>		
												</td>
											</tr>
										</table>
									<!-- End search options -->
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
				<!-- Search Option Ends -->
			
				<!-- Error Messages Begins -->
				<!-- Build this section only if there is an error message to display.
			     For the search section, we show the error messages within its frame
			-->
				<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
					<div class="spacerY">&nbsp;
						<div id="searchErrorMessagesArea" class="errorMessages">
							<html:errors />
						</div>
					</div>
				</c:if>
				<!-- Error Messages Ends -->
			
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input name="uAction" id="uAction" type="hidden" value=""> 
					<input name="startSearchTime" id="startSearchTime" type="hidden" value=""> 
					<input name="opsEntityName" id="opsEntityName" type="hidden" value="">
                    <input name="oldOpsEntityId" id="oldOpsEntityId" type="hidden" value="">
                    <input name="toDate" id="toDate" type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'>
                    <input name="sourceInventoryGroup" id="sourceInventoryGroup" type="hidden" value="">
					<!-- needed if this page will show on application.do -->
					<!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --> 
					<input name="searchHeight" id="searchHeight" type="hidden" value="180">
					
				</div>
				<!-- Hidden elements end -->
			</tcmis:form>
		</div>
		<!-- close of contentArea -->
	</div>
	<!-- Search Frame Ends --> 
	<!-- Result Frame Begins -->
	<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
	
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>	

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

		<!-- Transit Page Ends -->
		<div id="resultGridDiv" style="display: none;"><!-- Search results start --> <!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
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
									<div class="boxhead"><%-- boxhead Begins --%> 
										<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
								          	Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
								      		--%>
										<div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%> 
											<span id="updateResultLink" style="display: none">
												<a href="#" onclick="resultFrame.submitTransfer();"><fmt:message key="label.process"/></a>
											</span>
											<span id="addItemSpan1" style="display: none">
												| <a href="#" onclick="newConsignedItem();"><fmt:message key="label.newconsigneditem"/></a>
											</span>
										</div> <%-- mainUpdateLinks Ends --%>
									</div> <%-- boxhead Ends --%>
									<div class="boxhead">
										<span id="addItemSpan2" style="display: none">
												<a href="#" onclick="newConsignedItem();"><fmt:message key="label.newconsigneditem"/></a>
										</span>
									</div>
									<div class="dataContent" class="boxhead">
										<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
									</div>
									<%-- result count and time --%>
									<div id="footer" class="messageBar">
									</div>
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
<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>



</body>
</html:html>