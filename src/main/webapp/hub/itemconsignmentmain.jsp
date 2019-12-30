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
	
	<%-- initialize the locale for the page --%>
	<%@ include file="/common/locale.jsp" %>
	
	<%-- initialize the OpsEntity/Hub/InventoryGroup JSON data for drawing the pulldowns
	<%@ include file="/common/opshubig.jsp" %>
	NOT using this, customizing pulldowns --%>
	
	<%-- Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS.  --%>
	<tcmis:gridFontSizeCss />

	<%-- Add any other stylesheets you need for the page here --%>
	<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />	
	
	<%-- JS for checking/validating forms --%>
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<!-- This handles all the resizing of the page and frames -->
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the right-click menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	
	<%-- For Pop-Up Calendar Input support --%>
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	<script type="text/javascript" src="/js/common/opshubig.js"></script>
	<script type="text/javascript" src="/js/hub/itemconsignmentmain.js"></script>
	
	<!-- These are for the Grid -->
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

	<%-- This is for the YUI, error messages and/or legend internal page popups --%>
	<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
	<script type="text/javascript" src="/yui/build/event/event.js" ></script>
	<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
	<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
	<script type="text/javascript" src="/yui/build/container/container.js"></script>
	<script type="text/javascript" src="/js/common/waitDialog.js"></script>
	<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>
	
	<title>
		<fmt:message key="label.itemconsignment"/> 	
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
			itemInteger:"<fmt:message key="error.item.integer"/>",
			receiptInteger:"<fmt:message key="error.receiptid.integer"/>",
			receiptid:"<fmt:message key="label.receiptid"/>"
			};

var defaultOpsEntityId = null;
var defaultHub = null;
var preferredInventoryGroup = null;
<c:if test="${!empty personnelBean.opsHubIgOvBeanCollection}">
	<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status"> 
		<c:if test="${opsEntity.hasHubWithConsignmentInventoryGroup && personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId eq opsEntity.opsEntityId}">
		 	defaultOpsEntityId = '${personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId}';
		 	<c:forEach var="hub" items="${status.current.hubIgCollection}" varStatus="status1">
		 		<c:if test="${hub.hasConsignmentInventoryGroup && !empty hub.preferredInventoryGroup}">
					defaultHub = '${personnelBean.opsHubIgOvBeanCollection[0].defaultHub}';
			 		<c:forEach var="inventoryGroupObjBean" items="${hub.inventoryGroupCollection}" varStatus="status2">
			        	 <c:if test="${inventoryGroupObjBean.consignmentInventoryGroup}">
				        	 	   	preferredInventoryGroup = '${hub.preferredInventoryGroup}';
					     </c:if>
			        </c:forEach>
			 	</c:if>
		     </c:forEach>
	     </c:if>
	</c:forEach>
</c:if>

		<%-- Modified copy of what's in /js/common/opshubig.jsp to allow for customized pulldowns --%>
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
		    	<c:if test="${userOpsEntityHubIgOvBean.hasHubWithConsignmentInventoryGroup}">
 			        <c:set var="opsCounter" value="${opsCounter + 1}"/>
 			        <c:if test="${ opsCounter > 1 }">,</c:if>
			        {
			          id:   '${userOpsEntityHubIgOvBean.opsEntityId}',
			          name: '<tcmis:jsReplace value="${userOpsEntityHubIgOvBean.operatingEntityName}"/>',
	        	 	  <c:set var="hubCounter" value="0"/>
			          coll: [ <c:forEach var="hubIgObjBean" items="${userOpsEntityHubIgOvBean.hubIgCollection}" varStatus="status1">
			          		<c:if test="${hubIgObjBean.hasConsignmentInventoryGroup}">
			          		  <c:set var="hubCounter" value="${hubCounter + 1}"/>
			    	     	  	  <c:if test="${ hubCounter > 1 }">,</c:if>
			        	 	  {  id: '${hubIgObjBean.hub}',
			        	 	   name: '<tcmis:jsReplace value="${hubIgObjBean.hubName}"/>',
			        	 	   <c:set var="igCounter" value="0"/>
			        	 	   coll: [ <c:forEach var="inventoryGroupObjBean" items="${hubIgObjBean.inventoryGroupCollection}" varStatus="status2">
			        	 	   		<c:if test="${inventoryGroupObjBean.consignmentInventoryGroup}">
				        	 	   		<c:set var="igCounter" value="${igCounter + 1}"/>
				         				<c:if test="${ igCounter > 1 }">,</c:if>
					    	    	 	   	{
					    	    	 	   	id:'${inventoryGroupObjBean.inventoryGroup}',
					    	    			name:'<tcmis:jsReplace value="${inventoryGroupObjBean.inventoryGroupName}"/>'
					    	    			}
					    	    		</c:if>
			         		   	   </c:forEach>
			        	 	 	]
			        		   }
			        		</c:if>
			        	  </c:forEach>
			          	 ]
			        }
		    	</c:if>
		    </c:forEach>
		]; 		
		<%-- END customized pulldowns --%>

		// Can be removed to show My Entities, My hubs and My Inventory Group
		defaults.ops.nodefault = true;
		defaults.hub.nodefault = true;
		defaults.inv.nodefault = true;


	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','itemConsignment');setOps();" onresize="resizeFrames()">

	<div class="interface" id="mainPage" style="">
		
		<!-- Search Div Begins -->
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/itemconsignmentresults.do" onsubmit="validateSearch(); return submitSearchOnlyOnce();" target="resultFrame">

					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="1000" border="0" cellpadding="0" cellspacing="0">
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
													<td   width="15%" class="optionTitleBoldRight" >
														<fmt:message key="label.operatingentity"/>:
													</td>
													<td width="30%" class="optionTitleBoldLeft">
													  	<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
													</td>
													<td width="15%" class="optionTitleBoldRight">
														<fmt:message key="label.search"/>:
													</td>
													<td  nowrap width="40%" class="optionTitleLeft">
												        	<select name="searchField"  class="selectBox" id="searchField">
															<option value="itemId"> <fmt:message key="label.itemid"/>  </option>
															<option value="itemDesc"><fmt:message key="label.itemdesc"/> </option>                                                                                                         
															<option value="receiptId"><fmt:message key="label.receiptid"/> </option>
															<option value="customerReceiptId"><fmt:message key="label.legacyreceiptid"/> </option>
                                                            <option value="distCustomerPartList"><fmt:message key="label.partnumber"/> </option>
                                                        </select>&nbsp;<select name="searchMode" class="selectBox" id="searchMode" >
												                        <option value="is"> <fmt:message key="label.is"/>  </option>
												                        <option value="contains"> <fmt:message key="label.contains"/>  </option>
												                        <option value="startsWith"><fmt:message key="label.startswith"/> </option>
												                        <option value="endsWith"><fmt:message key="label.endswith" /></option>
												                </select>&nbsp;<input name="searchText" id="searchText" type="text" class="inputBox" size="15" onChange="validateSearch();"/>
													</td>
												</tr>
												<TR>
													<td nowrap  class="optionTitleBoldRight">
														<fmt:message key="label.hub"/>:
													</td>
													<td   class="optionTitleBoldLeft">
														<select name="hub" id="hub" class="selectBox"></select>
													</td>	
												</TR>
												<TR>
													<td nowrap  class="optionTitleBoldRight">
														<fmt:message key="label.inventorygroup"/>:
													</td>
													<td  class="optionTitleBoldLeft">
														<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
													</td>	             
												</TR>
												<tr>
													<td colspan="4">
											          		<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return validateSearch() && submitSearchForm()">
														<input name="createExcel"  type="button" class="inputBtns" value="<fmt:message key="label.createexcel"/>" id="createexcel" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return validateSearch() && createXSL()"/>          		 
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
						<input type="hidden" name="dateDelivered" id="dateDelivered" value=""/>
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
												<span id="updateResultLink"><a href="#" onclick="resultFrame.process();"><fmt:message key="label.process"/></a>
												| <fmt:message key="label.ponumber"/>:
												<input name="poNumber" id="poNumber" type="text" class="inputBox" size="15" maxlength="30" value=""/></span>
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

	<script type="text/javascript">
		YAHOO.namespace("example.aqua");
		YAHOO.util.Event.addListener(window, "load", init);
	</script>

</body>
</html:html>