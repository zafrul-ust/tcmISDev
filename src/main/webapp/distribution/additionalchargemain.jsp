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
	<%@ include file="/common/opshubig.jsp" %>
	
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />

	<%-- Add any other stylesheets you need for the page here --%>
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/distribution/additionalchargemain.js"></script>

	<!-- This handles all the resizing of the page and frames -->
	<%--NEW--%>
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
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
	<script type="text/javascript" src="/js/common/opshubig.js"></script>
	
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<!-- This is for the YUI, uncomment if you will use this -->
	
	<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
	<script type="text/javascript" src="/yui/build/event/event.js" ></script>
	<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
	<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
	<script type="text/javascript" src="/yui/build/container/container.js"></script>
	<script type="text/javascript" src="/js/common/waitDialog.js"></script>
	<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
	
	<title>
		<fmt:message key="label.additionalchargesetup"/>	
	</title>
	
	<script language="JavaScript" type="text/javascript">
		windowCloseOnEsc = true;

		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = 
			{alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			errors:"<fmt:message key="label.errors"/>",  
			validvalues:"<fmt:message key="label.validvalues"/>",
			missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",	
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"};
		
		var defaultOpsEntityId = null;
		
       <c:if test="${!empty personnelBean.opsHubIgOvBeanCollection && !empty personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId }">
	      defaultOpsEntityId = '${personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId}';
         </c:if>
         
		var defaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true}
	   	    }
	   	    
	   	function buildDropDown( arr, defaultObj, eleName ) {
		   var obj = $(eleName);
		   for (var i = obj.length; i > 0;i--)
		     obj[i] = null;
		  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
		  if( arr == null || arr.length == 0 ) 
			  setOption(0,defaultObj.name,defaultObj.id, eleName); 
		  else if( arr.length == 1 )
			  setOption(0,arr[0].name,arr[0].id, eleName);
		  else {
		      var start = 0;
		  	  if( defaultObj.nodefault )
		  	  	start = 0 ; // will do something??
		  	  else {
			  setOption(0,defaultObj.name,defaultObj.id, eleName); 
				  start = 1;
			  }
			  for ( var i=0; i < arr.length; i++) {
			    	setOption(start++,arr[i].name,arr[i].id,eleName);
			  }
		  }
		  obj.selectedIndex = 0;
		}

		function setOpsEntity() {
		 	buildDropDown(opshubig,defaults.ops,"opsEntityId");
		 			   
		    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
		    	$('opsEntityId').value = defaultOpsEntityId;
		    }
		    
		}    
					
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','additionalChargeMain'); setOpsEntity();" onresize="resizeFrames()" >

	<div class="interface" id="mainPage" style="">
		
		<!-- Search Div Begins -->
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/additionalchargeresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="900" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
								     			<div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
								   		</div>
									   	<div class="roundContent">
										    	<!-- Insert all the search option within this div -->
											<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td nowrap  width="8%" class="optionTitleBoldRight" nowrap>
														<fmt:message key="label.operatingentity"/>:
													</td>
													<td width="6%" class="optionTitleBoldLeft">
													  	<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
													</td>
													<td class="optionTitleBoldLeft" nowrap>
													        <input type="checkbox" name="headerChargesOnly" id="headerChargesOnly" onclick="selectOnlyOne(this)">&nbsp;<fmt:message key="label.showheaderchargesonly"/>&nbsp;
													</td>
												</tr>
												<tr>
													<td width="20%" class="optionTitleBoldRight"><fmt:message key="label.search" />:</td>
												    <td width="20%" class="optionTitleBoldLeft" nowrap>
													<select name="searchField" class="selectBox" id="searchField">
														<option value="ITEM_ID"><fmt:message key="label.itemid" /></option>
														<option value="ITEM_DESCRIPTION"><fmt:message key="label.description" /></option>
													</select>
													<select name="searchMode" class="selectBox" id="searchMode">
														<option value="IS"><fmt:message key="label.is" /></option>
														<option value="LIKE"><fmt:message key="label.contains" /></option>
													</select>
													<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15">
												</td>
													<td class="optionTitleBoldLeft" nowrap >
													        <input type="checkbox" name="lineChargesOnly" id="lineChargesOnly" onclick="selectOnlyOne(this)">&nbsp;<fmt:message key="label.showlinechargesonly"/>&nbsp;
													</td>
												</tr>
												<tr>
													<td colspan="3">
											          		<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm()">
														<input name="createexcel"  id="createexcel"  type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>          		 
													</td>
												</tr>
											</table>
										    	<!-- Search Option Table end -->
										</div>
										<div class="roundbottom">
									     		<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<!-- Search Option Ends -->
				
					<div id="transitDialogWin" class="errorMessages" style="display: none;overflow: auto;">
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
			
					<!-- Hidden element start -->
					<div id="hiddenElements" style="display: none;">
						<input type="hidden" name="uAction" id="uAction" value="search"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
					</div>
					<!-- Hidden elements end -->
			
				</tcmis:form>
			</div>
			<!-- close of contentArea -->
		</div>
		<!-- Search Div Ends -->
		
		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
		
			<%--NEw -Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br><br><br><fmt:message key="label.pleasewait"/>
				<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
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
					    					<div class="boxhead"> <%-- boxhead Begins --%>
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											     Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											--%>
											<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
												<Span id="updateResultLink" style="display: none;"><a href="#" onclick="resultFrame.updateCharges();"><fmt:message key="label.update"/></a></span> 
											</div> <%-- mainUpdateLinks Ends --%>
				    						</div> <%-- boxhead Ends --%>
					
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>
					
										<%-- result count and time --%>
										<div id="footer" class="messageBar">
										</div>
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
		</div><!-- Result Frame Ends -->
		
	</div> <!-- close of interface -->
	
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">		
	</div>

</body>
</html:html>