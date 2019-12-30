<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" 		language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 	prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 	prefix="fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" 		prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" 			prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" 			prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" 			prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" 				prefix="tcmis"%>

<html:html lang="true">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="expires" content="-1"/>
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	
		<%@ include file="/common/locale.jsp"%> 
		<!-- Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
		<tcmis:gridFontSizeCss /> 
		<%-- Add any other stylesheets you need for the page here --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script> 
		<script type="text/javascript" src="/js/common/commonutil.js"></script> 
		<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
		<!-- This handles which key press events are disabeled on this page -->
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
		<!-- This handles the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
		<%@ include file="/common/rightclickmenudata.jsp"%> 
		
		<!-- Add any other javascript you need for the page here --> 
		<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

		<!-- These are for the Grid-->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
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
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

		<title>
			<fmt:message key="label.allocations"/>
		</title>

		<script language="JavaScript" type="text/javascript">
		<!--
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData = {alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		recordFound:"<fmt:message key="label.recordFound"/>",
		searchDuration:"<fmt:message key="label.searchDuration"/>",
		minutes:"<fmt:message key="label.minutes"/>",
		seconds:"<fmt:message key="label.seconds"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
		itemInteger:"<fmt:message key="error.item.integer"/>"};
		
		var gridConfig = {
			divName:'allocationsGridDiv',		// the div id to contain the grid.
			beanData:'jsonMainData',     		// the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'beanGrid',     			// the grid's name, as in beanGrid.attachEvent...
			config:'config',	     			// the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:'false',		 			// this page has rowSpan > 1 or not.
			submitDefault:'true',    			// the fields in grid are defaulted to be submitted or not.
		    singleClickEdit:true,     			// this will open the txt cell type to enter notes by single click
    		noSmartRender:false 				// If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};
		
		/*Declare any right click menus if any.*/
		with(milonic=new menuname("rightClickMenu")){
		    top="offset=2"
		    style = contextStyle;
		    margin=3
		 	aI("text=<fmt:message key="label.open"/> <fmt:message key="label.mr"/>;url=javascript:openMr();");
		}
		drawMenus();
		
		/*This array defines the grid and properties about the columns in the grid.
		* more explanation of the grid config can be found in /dhtmlxGrid/codebase/dhtmlxcommon_haas.js initGridWithConfig()
		* */
		var config = [
		{ columnId:"mr",
		  columnName:'<fmt:message key="label.mr"/>',
		  tooltip:"Y",
		  width:7
		},
		{ columnId:"itemId",
		  columnName:'<fmt:message key="label.itemid"/>',
		  tooltip:"Y",
		  width:5
		},
		{ columnId:"itemDesc",
		  columnName:'<fmt:message key="label.description"/>',
		  tooltip:"Y",
		  width:26
		},
		{ columnId:"catPartNo",
		  columnName:'<fmt:message key="label.catpartno"/>',
		  tooltip:"Y",
		  width:7
		},
		{ columnId:"customerName",
		  columnName:'<fmt:message key="label.customer"/>',
		  tooltip:"Y",
		  width:15
		},
		{ columnId:"csrName",
		  columnName:'<fmt:message key="label.csr"/>',
		  tooltip:"Y",
		  width:10
		},
		{ columnId:"dateToDeliver",
		  columnName:'<fmt:message key="label.deliverydate"/>',
		  tooltip:"Y",
		  width:8
		},
		{ columnId:"dedicated",
		  columnName:'<fmt:message key="label.dedicated"/>',
		  tooltip:"Y",
		  width:5
		},
		{ columnId:"mfgLot",
		  columnName:'<fmt:message key="allocationanalysis.label.suppliermfglot"/>',
		  tooltip:"Y",
		  width:10
		},
		{ columnId:"docType",
		  columnName:'<fmt:message key="label.allocationtype"/>',
		  tooltip:"Y",
		  width:7
		},
		{ columnId:"docNum",
		  columnName:'<fmt:message key="label.reference"/>',
		  tooltip:"Y",
		  width:7
		},
		{
		  columnId:"dateCreated",
		  columnName:'<fmt:message key="label.date.created"/>',
		  tooltip:"Y",
		  width:12
		}
		];
		// -->
		</script>
	</head>
	
	<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid(gridConfig);" onresize="resizeFrames()">
		<tcmis:form action="/showallocations.do" onsubmit="return submitFrameOnlyOnce();">

			<div class="interface" id="resultsPage">
			
				<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
				So this is just used to feed the pop-up in the main page.
				Similar divs would have to be built to show any other messages in a pop-up.-->
				<!-- Error Messages Begins -->
				<div id="errorMessagesAreaBody" style="display:none;">
				  ${tcmISError}<br/>
				  <c:forEach items="${tcmISErrors}" varStatus="status">
				  	${status.current}<br/>
				  </c:forEach>
				</div>
			
				<script type="text/javascript">
				<!--
				/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
			   <c:choose>
			    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
			     showErrorMessage = false;
			    </c:when>
			    <c:otherwise>
			     showErrorMessage = true;
			    </c:otherwise>
			   </c:choose>   
				//-->
				</script>
				<!-- Error Messages Ends -->
			
				<!-- Result Frame Begins -->
				<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
					<!-- Transit Page Begins -->
					<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					  	<br/><br/><br/><fmt:message key="label.pleasewait"/>
					  	<br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
					</div>
					<!-- Transit Page Ends -->
					
					<div id="resultGridDiv" style="height: 300px; display: none;"> 
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
												<%-- boxhead Begins --%> 
												<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
									          	Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
									          	Please keep the <span></span> on the same line this will avoid extra virtical space.
									      		--%>
													<div id="mainUpdateLinks" style="display: none">
														<div id="updateResultLink" style="display: none"></div>
													</div>
												</div>
											
												<div class="dataContent">
													<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
													<div id="allocationsGridDiv" style="height:400px; display: none;"></div>
													<!-- Search results start -->
													<c:if test="${mrAllocationViewBeanCollection != null}" >
														<script type="text/javascript">
															<!--	
															/*This is to keep track of whether to show any update links.
															If the user has any update permisions for any row then we show update links.*/
															<c:set var="showUpdateLink" value='N'/>
															<c:set var="dataCount" value='${0}'/>
															<c:if test="${!empty mrAllocationViewBeanCollection}" >
																/*Storing the data to be displayed in a JSON object array.*/
																var jsonMainData = new Array();
																var jsonMainData = {
																rows:[
																<c:forEach var="mrAllocationViewBean" items="${mrAllocationViewBeanCollection}" varStatus="status">
																	<c:if test="${status.index > 0}">,</c:if>

																	<fmt:formatDate  var="fmtDeliveryDate" 	value="${mrAllocationViewBean.dateToDeliver}" 	pattern="${dateFormatPattern}"/>
																	
																	<tcmis:jsReplace var="mr" 				value="${mrAllocationViewBean.mr}" 				processMultiLines="true" />
																	<tcmis:jsReplace var="itemId" 			value="${mrAllocationViewBean.itemId}" 			processMultiLines="true" />
																	<tcmis:jsReplace var="description" 		value="${mrAllocationViewBean.itemDesc}" 		processMultiLines="true" />
																	<tcmis:jsReplace var="catPartNo" 		value="${mrAllocationViewBean.catPartNo}" 		processMultiLines="true" />
																	<tcmis:jsReplace var="customer" 		value="${mrAllocationViewBean.customerName}" 	processMultiLines="true" />
																	<tcmis:jsReplace var="csr" 				value="${mrAllocationViewBean.csrName}" 		processMultiLines="true" />
																	<tcmis:jsReplace var="deliveryDate" 	value="${fmtDeliveryDate}" 						processMultiLines="true" />
																	<tcmis:jsReplace var="dedicated" 		value="${mrAllocationViewBean.dedicated}" 		processMultiLines="true" />
																	<tcmis:jsReplace var="supplierMfgLot"	value="${mrAllocationViewBean.supplierName}" 	processMultiLines="true" />
																	<tcmis:jsReplace var="allocationType" 	value="${mrAllocationViewBean.docType}" 		processMultiLines="true" />
																	<tcmis:jsReplace var="reference" 		value="${mrAllocationViewBean.docNum}" 			processMultiLines="true" />
																	<tcmis:jsReplace var="dateCreated" 		value="${mrAllocationViewBean.dateCreated}" 	processMultiLines="true" />
																	
																	/*The row ID needs to start with 1 per their design.*/
																	{ id:${status.index +1},
																	 data:[
																	  '${mr}','${itemId}','${description}','${catPartNo}',  
																	  '${customer}','${csr}','${deliveryDate}','${dedicated}',
																	  '${supplierMfgLot}','${allocationType}','${reference}',
																	  '${dateCreated}']}
																</c:forEach>
																]};
															</c:if>
															//-->
														</script>
														
														<!-- If the collection is empty say no data found -->
														<c:if test="${empty mrAllocationViewBeanCollection}" >
															<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
															 	<tr>
																  	<td width="100%">
																	   	<fmt:message key="main.nodatafound"/>
																  	</td>
															 	</tr>
															</table>
														</c:if>
													</c:if>
													<!-- Search results end -->
												</div>

												<%-- result count and time --%>
												<div id="footer" class="messageBar"></div>
												
											</div>
											<div class="roundbottom">
										    	<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
										  	</div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</div> <!-- allocationsGridDiv end -->
				</div>
				<!-- allocationsFrameDivId Ends -->
				
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					
					<tcmis:setHiddenFields beanName="allocationsInputBean"/>

					<!--This sets the start time for time elapesed from the action. -->
					<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
					<!--This sets the end time for time elapesed from the action. -->
					<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
					
					<input name="minHeight" id="minHeight" type="hidden" value="100"/>
				</div>
				<!-- Hidden elements end -->
			
			</div> <!-- close of interface -->
			
			<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
			     The default value of showUpdateLinks is false.
			-->
			<c:if test="${showUpdateLink == 'Y'}">
			    <script type="text/javascript">
			        <!--
			        showUpdateLinks = true;
			        //-->
			    </script>
			</c:if>
		
		</tcmis:form>
		
		<!-- You can build your error messages below.
		     Similar divs would have to be built to show any other messages in a inline pop-up.-->
		<!-- Error Messages Begins -->
		<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
		</div>
		
	</body>	
</html:html>
