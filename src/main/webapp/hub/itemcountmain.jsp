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
	
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />

	<%-- Add any other stylesheets you need for the page here --%>
	<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />	
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

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
	<script type="text/javascript" src="/js/hub/itemcountmain.js"></script>
	
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
		<fmt:message key="label.itemCount"/>	
	</title>

	
	<script language="JavaScript" type="text/javascript">
		windowCloseOnEsc = true;

		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = 
			{alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			additem:"<fmt:message key="label.additem"><fmt:param><fmt:message key="label.waitingfor"/></fmt:param></fmt:message>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			errors:"<fmt:message key="label.errors"/>",  
			validvalues:"<fmt:message key="label.validvalues"/>",
			missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",	
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			selectinventorygroup:"<fmt:message key="label.selectinventorygroup"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"};
			
			<c:set var="hasPermission" value='N'/>
           <tcmis:inventoryGroupPermission indicator="true" userGroupId="InvTransfer" inventoryGroup="${param.inventoryGroup}">
	           <c:set var="hasPermission" value='Y'/>
           </tcmis:inventoryGroupPermission>
			

var defaultOpsEntityId = null;
var defaultHub = null;
var preferredInventoryGroup = null;
<c:if test="${!empty personnelBean.opsHubIgOvBeanCollection}">
	<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status"> 
		<c:if test="${opsEntity.hasHubWithCountingInventoryGroup && personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId eq opsEntity.opsEntityId}">
		 	<c:forEach var="hub" items="${opsEntity.hubIgCollection}">
		 		<c:if test="${!empty hub.preferredInventoryGroup}">
		 			<c:forEach var="ig" items="${hub.inventoryGroupCollection}">
		 				<c:if test="hub.preferredInventoryGroup == ig.inventoryGroup && ig.countingInventoryGroup">
				 			defaultOpsEntityId = '${personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId}';
							defaultHub = '${personnelBean.opsHubIgOvBeanCollection[0].defaultHub}';
					 		preferredInventoryGroup = '${hub.preferredInventoryGroup}';
		 				</c:if>
		 			</c:forEach>
			 	</c:if>
		     </c:forEach>
	     </c:if>
	</c:forEach>
</c:if>

			// Modified copy of what's in /js/common/opshubig.jsp
			
			<c:set var="myinventorygroups">
				<fmt:message key="label.myinventorygroups"/>
			</c:set>
			var defaults = {
				   ops: {id:'',name:'<fmt:message key="label.myentities"/>'},
			   	   hub: {id:'',name:'<fmt:message key="label.myhubs"/>'},
			   	   inv: {id:'',name:'<tcmis:jsReplace value="${myinventorygroups}"/>'}
			}

        	 	<c:set var="opsCounter" value="0"/>
			var opshubig = [
			    <c:forEach var="userOpsEntityHubIgOvBean" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
			    	<c:if test="${userOpsEntityHubIgOvBean.hasHubWithCountingInventoryGroup}">
	 			        <c:set var="opsCounter" value="${opsCounter + 1}"/><c:set var="hubCounter" value="0"/><c:if test="${ opsCounter > 1 }">,</c:if>
				        { id:   '${userOpsEntityHubIgOvBean.opsEntityId}',
				          name: '<tcmis:jsReplace value="${userOpsEntityHubIgOvBean.operatingEntityName}"/>',
				          coll: [ <c:forEach var="hubIgObjBean" items="${userOpsEntityHubIgOvBean.hubIgCollection}" varStatus="status1">
				          		<c:if test="${hubIgObjBean.hasCountingInventoryGroup}">
				          		  	<c:set var="hubCounter" value="${hubCounter + 1}"/><c:if test="${ hubCounter > 1 }">,</c:if><c:set var="igCounter" value="0"/>
				        	 	  	{id: '${hubIgObjBean.hub}',
								 name: '<tcmis:jsReplace value="${hubIgObjBean.hubName}"/>',
				        	 	   	coll: [ <c:forEach var="inventoryGroupObjBean" items="${hubIgObjBean.inventoryGroupCollection}" varStatus="status2">
				        	 	   			<c:if test="${inventoryGroupObjBean.countingInventoryGroup}">
						        	 	   		<c:set var="igCounter" value="${igCounter + 1}"/><c:if test="${ igCounter > 1 }">,</c:if>
							    	    	 	   	{id:'${inventoryGroupObjBean.inventoryGroup}',
							    	    			 name:'<tcmis:jsReplace value="${inventoryGroupObjBean.inventoryGroupName}"/>'}
							    	    		</c:if>
				         		   	   	</c:forEach>]
				        		   	}
				        		</c:if>
				        	  </c:forEach>]
				        }
			    	</c:if>
			    </c:forEach>]; 
			var hubIsConsignment = {};
			<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="stat">
				<c:if test="${opsEntity.hasHubWithCountingInventoryGroup}">
					<c:forEach var="hub" items="${opsEntity.hubIgCollection}" varStatus="stat1">// ${hub.hubName}
						<c:if test="${hub.hasCountingInventoryGroup}">
							hubIsConsignment["${hub.hub}"] = <c:choose><c:when test="${hub.hasConsignmentInventoryGroup}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
	   		var invGrpType = {};
			<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="stat">
				<c:forEach var="hub" items="${opsEntity.hubIgCollection}" varStatus="stat1">
					<c:forEach var="inventoryGroup" items="${hub.inventoryGroupCollection}" varStatus="stat2">
			 		<c:if test="${inventoryGroup.consignmentInventoryGroup}">
			 			invGrpType["${inventoryGroup.inventoryGroup}"] = "true";
			 		</c:if>
					</c:forEach>
				</c:forEach>
			</c:forEach>
		    		

		// Can be removed to show My Entities, My hubs and My Inventory Group
		defaults.ops.nodefault = true;
		defaults.hub.nodefault = true;
		defaults.inv.nodefault = true;

	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','itemCount');setOps()" onresize="resizeFrames()">

	<div class="interface" id="mainPage" style="">
		
		<!-- Search Div Begins -->
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/itemcountresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
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
													<td width="5%" class="optionTitleBoldRight" nowrap>
														<fmt:message key="label.sortby"/>:
													</td>
													<td width="10%" class="optionTitleLeft">
														<select name="sortBy" id="sortBy" class="selectBox">
															<option value="Bin" ><fmt:message key="label.bin"/></option>
															<option value="ItemID" selected><fmt:message key="label.itemid"/></option>
															<option value="Description" ><fmt:message key="label.description"/></option>
															<option value="PartNumber"><fmt:message key="label.partnumber"/></option>
														</select>
													</td>
												</tr>
												<TR>
													<td nowrap width="6%"  class="optionTitleBoldRight">
														<fmt:message key="label.hub"/>:
													</td>
													<td  width="10%" class="optionTitleBoldLeft">
														<select name="hub" id="hub" class="selectBox"></select>
													</td>	
												</TR>
												<TR>
													<td nowrap  width="8%" class="optionTitleBoldRight">
														<fmt:message key="label.inventorygroup"/>:
													</td>
													<td width="14%" class="optionTitleBoldLeft">
<%-- 
														<select name="inventoryGroup" id="inventoryGroup" class="selectBox" multiple="multiple" onChange="showConsignedB()"></select>
--%>
														<select name="inventoryGroup" id="inventoryGroup" class="selectBox" onChange="showConsignedB()"></select>
													</td>	             
												</TR>
												<tr>
													<td colspan="4">
												          	<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm()">
														<input name="createExcel"  type="button" class="inputBtns" value="<fmt:message key="label.createexcel"/>" id="createexcel" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/> 
														<span id="consignedBSpan" style="display: none" >
															<tcmis:inventoryGroupPermission indicator="true" userGroupId="Hub ItemCount">
                	                                                							<input name="consigneditem"  type="button" class="inputBtns" value="<fmt:message key="label.newconsigneditem"/>" id="consigneditem" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="newConsignedItem();"/>
								                                			</tcmis:inventoryGroupPermission>
														</span>
														<input name="printBinLabels"  type="button" class="inputBtns" value="Print Bin Labels" id="printBinLabels" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="selectCatPartNo()"/> 
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
												<a href="#" onclick="showLegend(); return false;"><fmt:message key="label.showlegend"/></a>
												<span id="singleUpdateLinks" style="display: none"> 
													&nbsp;|&nbsp;<a href="#" onclick="resultFrame.processItemCount();"><fmt:message key="label.process"/> <fmt:message key="label.count"/></a>&nbsp;|
													<span id="poNumberSpan">&nbsp;<fmt:message key="label.ponumber"/>:&nbsp;<input name="poNumber" id="poNumber" type="text" class="inputBox" size="15" maxlength="30" value="" onchange="resultFrame.genericForm.poNumber.value=this.value;"/>&nbsp;|</span>
													<span id="addItemSpan1" style="display: none" > 
														<a href="#" onclick="resultFrame.showAddItem();"><fmt:message key="label.additem"/></a>&nbsp;|
													</span>
													<a href="#" onclick="showPleaseWait();resultFrame.closeItemCount();"><fmt:message key="label.closecount"/></a>
												</span>
												<span id="multiUpdateLinks" style="display: none"> 
													&nbsp;|&nbsp;<a href="#" onclick="resultFrame.processItemCount();"><fmt:message key="label.process"/> <fmt:message key="label.count"/> <fmt:message key="label.forallgroups"/></a>&nbsp;| 
													<span id="poNumberSpan">&nbsp;<fmt:message key="label.ponumber"/>:&nbsp;<input name="poNumber" id="poNumber" type="text" class="inputBox" size="15" maxlength="30" value="" onchange="resultFrame.genericForm.poNumber.value=this.value;"/>&nbsp;|</span>
													<span id="addItemSpan2" style="display: none" > 
														<a href="#" onclick="resultFrame.showAddItem();"><fmt:message key="label.additem"/></a>&nbsp;|
													</span>
													<a href="#" onclick="showPleaseWait();resultFrame.closeItemCount();"><fmt:message key="label.closecount"/> <fmt:message key="label.forallgroups"/></a>
												</span>
											</div> <%-- mainUpdateLinks Ends --%>
											<div id="newCount" style="display: none">
												<a href="#" onclick="showPleaseWait(); resultFrame.startItemCount();"><fmt:message key="label.startnewcount"/></a> 
											</div> 
											<div id="multiNewCount" style="display: none">
												<a href="#" onclick="showPleaseWait(); resultFrame.startItemCount();"><fmt:message key="label.startnewcount"/> <fmt:message key="label.forallgroups"/></a> 
											</div> 
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

	<div id="showLegendArea" style="display:none;z-index:5;">
		<div id="showLegendAreaTitle" class="hd"><fmt:message key="label.showlegend"/></div>
		<div id="showLegendAreaBody" class="bd">
			<table width=100% class="avoidCssSelectors tableResults" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="red" width="5%">&nbsp;</td>
					<td width="20%"><fmt:message key="label.legendissueonreceipt"/></td>
				</tr>
				<tr>
					<td class="black" width="5%">&nbsp;</td>
					<td width="20%"><fmt:message key="label.legendexcludedfromcount"/></td>
				</tr>
			</table>
		</div>
	</div>	
	
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">		
	</div>

	<script type="text/javascript">
		YAHOO.namespace("example.aqua");
		YAHOO.util.Event.addListener(window, "load", init);
	</script>

</body>
</html:html>