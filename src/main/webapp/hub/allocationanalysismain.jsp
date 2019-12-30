<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

	<%@ include file="/common/locale.jsp" %>
	<%@ include file="/common/opshubig.jsp" %>
	
	<!--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
	<tcmis:gridFontSizeCss />

	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<!-- This handles all the resizing of the page and frames -->
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	<!-- This handels which key press events are disabeled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/hub/allocationanalysis.js"></script>
	<script type="text/javascript" src="/js/common/opshubig.js"></script>
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

	<!-- These are for the Grid, uncomment if you are going to use the grid -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

	<!-- Include this so I can submit grid thru form -->
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

	<!--  These are for the autocomplete function -->
	<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
	<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
	<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

	<title>
		<fmt:message key="allocationanalysis.title"/>
	</title>

	<script language="JavaScript" type="text/javascript">
		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			daySpanInteger:"<fmt:message key="error.dayspan.integer"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			itemMrInteger:"<fmt:message key="error.itemmr.integer"/>",
			all:"<fmt:message key="label.all"/>"
		};
	
		defaults.ops.nodefault = true;
		defaults.hub.nodefault = true;
		defaults.hub.callback = inventoryGroupChanged;

		var altFacility = new Array ();

		<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}">
			<c:forEach var="inventoryGroupBean" items="${hubInventoryGroupFacOvBean.inventoryGroupCollection}">
				<tcmis:jsReplace var="currentInventoryGroup" value="${inventoryGroupBean.inventoryGroup}"/>
				altFacility["${currentInventoryGroup}"] = new Array (
					<c:forEach var="facilityBean" items="${inventoryGroupBean.facilities}" varStatus="status">
						<c:if test="${!status.first}">,</c:if>
						{
							id:"<tcmis:jsReplace value="${facilityBean.facilityId}"/>",
							name:"<tcmis:jsReplace value="${facilityBean.facilityName}"/>"
						}
					</c:forEach>
				);
			</c:forEach>
		</c:forEach>
		
		j$().ready(function() {
			function log(event, data, formatted) {
				j$('#customerId').val(formatted.split(":")[0]);
				$("customerName").className = "inputBox"; 
			} 

			j$("#customerName").autocomplete("/tcmIS/distribution/findcustomer.do",{
				width: 350,
				max: 100,  // default value is 10
				cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
				scrollHeight: 150,
				formatItem: function(data, i, n, value) {
					return  value.split(":")[0]+" "+value.split(":")[1].substring(0,32);
				},
				formatResult: function(data, value) {
					return value.split(":")[1];
				}
			});

			j$('#customerName').bind('keyup',(function(e) {
				var keyCode = (e.keyCode ? e.keyCode : e.which);
				if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
					invalidateCustomer();
			}));

			j$("#customerName").result(log).next().click(function() {
				j$(this).prev().search();
			});
		});

		j$().ready(function() {
			function log(event, data, formatted) {
				j$('#csrPersonnelId').val(formatted.split(":")[0]);
				$("personnelName").className = "inputBox"; 
			} 

			j$("#personnelName").autocomplete("/tcmIS/haas/getpersonneldata.do",{
				extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
							  companyId: function() { return j$("#companyId").val(); } },
				width: 200,
				max: 10,  // default value is 10
				cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
				scrollHeight: 150,
				formatItem: function(data, i, n, value) {
					return  value.split(":")[1].substring(0,40);
				},
				formatResult: function(data, value) {
					return value.split(":")[1];
				}
			});

			j$('#personnelName').bind('keyup',(function(e) {
				var keyCode = (e.keyCode ? e.keyCode : e.which);

				if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
					invalidatePersonnel();
			}));

			j$("#personnelName").result(log).next().click(function() {
				j$(this).prev().search();
			});
		});
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','allocationAnalysis');setOps();" onresize="resizeFrames()">
	<div class="interface" id="mainPage" style="">
		<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;"></div>

		<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
			<table width="100%" border="0" cellpadding="2" cellspacing="1">
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td align="center" id="transitLabel"></td>
				</tr>
				<tr>
					<td align="center">
						<img src="/images/rel_interstitial_loading.gif" align="middle">
					</td>
				</tr>
			</table>
		</div>
		<!-- Search Frame Begins -->
		<div id="searchFrameDiv">
			<div id="contentArea">
				<tcmis:form action="/allocationanalysisresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
										</div>
										<div class="roundContent">
											<!-- Insert all the search option within this div -->
											<table border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
													<td width="6%" class="optionTitleBoldLeft" colspan="2">
														<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
													</td>
													<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.customer"/>:</td>
													<td width="30%" nowrap>
														<input type="text" name="customerName" id="customerName" value="" size="30" class="inputBox" />
														<input name="customerId" id="customerId" type="hidden" value=""/>  
														<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="customerLookup" value="..." align="right" onClick="lookupCustomer()"/>
													</td>
													<td class="optionTitleBoldRight" nowrap><fmt:message key="label.csr"/>:</td>
													<td width="30%" nowrap>
														<input  type="text" name="personnelName" id="personnelName" value="" size="30" class="inputBox" />
														<input name="csrPersonnelId" id="csrPersonnelId" type="hidden" value=""/>
														<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="personnelLookup" value="..." align="right" onClick="lookupPersonnel()"/>
	    	  										</td>
	    	  									</tr>
												<tr>
													<td nowrap width="10%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
													<td  width="15%" class="optionTitleBoldLeft" colspan="2">
														<select name="hub" id="hub" class="selectBox"></select>
													</td>
													<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.lotstatus"/>:</td>
													<td width="15%">
														<select id="lotStatus" name="lotStatus" class="selectBox">
															<option value=""><fmt:message key="label.all"/></option>
															<c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
																<option value="${bean.lotStatus}"><fmt:message key="${bean.jspLabel}"/></option>
															</c:forEach>
														</select>
													</td>
													<td class="optionTitleBoldLeft" nowrap colspan="2">
														<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y"/>
														<fmt:message key="label.activeOnly"/>
													</td>
												</tr>
												<tr>
													<td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
													<td width="14%" class="optionTitleBoldLeft" colspan="2">
														<select name="inventoryGroup" id="inventoryGroup" onchange="inventoryGroupChanged();" class="selectBox"></select>
													</td>
													<td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.progressstatus"/>:</td>
													<td width="15%">
														<select name="progressStatus" id="progressStatus" class="selectBox">
															<option value=""><fmt:message key="label.all"/></option>
															<option value="Available"><fmt:message key="label.available"/></option>
															<option value="In Supply"><fmt:message key="label.insupply"/></option>
															<option value="Not Allocated"><fmt:message key="label.notallocated"/></option>
															<option value="On Hand, Not Available"><fmt:message key="allocationanalysis.label.onhand"/></option>
														</select>
													</td>
													<td width="10%" class="optionTitleBoldLeft" nowrap colspan="2">
														<select name="itemOrMrCriteria" id="itemOrMrCriteria" class="selectBox">
															<option value="itemid"><fmt:message key="label.itemid"/></option>
															<option value="mr"><fmt:message key="label.mr"/></option>
														</select>
														<fmt:message key="label.is"/>
														<input name="itemOrMr" id="itemOrMr" type="text" class="inputBox" value="" size="8"/>
													</td>
												</tr>
												<tr>
													<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
													<td width="15%" colspan="2">
														<select name="facilityId" id="facilityId" class="selectBox"></select>
													</td>
													<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
													<td width="15%">
														<select name="sortBy" id="sortBy" class="selectBox">
															<option value="FACILITY_ID"><fmt:message key="label.facilityid"/></option>
															<option value="ITEM_ID,REQUIRED_DATETIME"><fmt:message key="allocationanalysis.label.itemneeded"/></option>
															<option value="PR_NUMBER,LINE_ITEM"><fmt:message key="label.mrline"/></option>
															<option value="PR_NUMBER,LINE_ITEM,REQUIRED_DATETIME"><fmt:message key="allocationanalysis.label.mrlineneeded"/></option>
															<option value="REQUIRED_DATETIME" selected><fmt:message key="label.needed"/></option>
															<option value="SYSTEM_REQUIRED_DATETIME"><fmt:message key="allocationanalysis.label.ontimedate"/></option>
															<option value="REF_NO"><fmt:message key="label.referenceno"/></option>
															<option value="ITEM_TYPE,ITEM_ID,REQUIRED_DATETIME"><fmt:message key="allocationanalysis.label.typeitemneeded"/></option>
															<option value="CUSTOMER_NAME"><fmt:message key="label.customer"/></option>
														</select>
													</td>
													<td width="10%" class="optionTitleBoldLeft" nowrap colspan="2">
														<select name="daySpanCriteria" id="daySpanCriteria" class="selectBox">
															<option value="needed"><fmt:message key="label.needed"/></option>
															<option value="ontime"><fmt:message key="label.ontime"/></option>
														</select>
														<fmt:message key="label.within"/>
														<input name="daySpan" id="daySpan" type="text" class="inputBox" value="7" size="3"/>
														<fmt:message key="label.days"/>
													</td>
												</tr>

												<tr>
													<td width="10%" class="optionTitleBoldRight">&nbsp;</td>
													<td width="15%" class="optionTitleBoldLeft" colspan="2">
														<input name="searchTypeNonScheduled" type="checkbox" class="radioBtns" value="Y" id="searchTypeNonScheduled" checked/><fmt:message key="label.nonscheduled"/>
														<input name="searchTypeScheduled" type="checkbox" class="radioBtns" value="Y" id="searchTypeScheduled" checked/><fmt:message key="deliveryschedule.label.scheduled"/>
														<input name="searchTypeTransfer" type="checkbox" class="radioBtns" value="Y" id="searchTypeTransfer" checked/><fmt:message key="label.transfer"/>
													</td>
												</tr>
												<tr>
													<td width="10%" class="optionTitleBoldRight">
														<input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="search();" />
													</td>
													<td width="15%">
														<input name="submitExcel" id="submitExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel();" />          
													</td>
													<td colspan="4">&nbsp; </td>
												</tr>
											</table>
											<!-- End search options -->
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
						<input name="uAction" id="uAction" type="hidden" value="" />
						<input type="hidden" name="companyId" id="companyId" value="${personnelBean.opsHubIgOvBeanCollection[0].companyId}"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
					</div>
					<!-- Hidden elements end -->
				</tcmis:form>
			</div>
		</div>
		<!-- Search Frame Ends -->

		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<!-- Transit Page Begins -->
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br/><br/><br/><fmt:message key="label.pleasewait"/><br/><br/><br/>
				<img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</div>

			<!-- Transit Page Ends -->
			<div id="resultGridDiv" style="display: none;">
				<!-- Search results start -->
				<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
										<div class="boxhead"> <%-- boxhead Begins --%>
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											--%>
											<div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
												<span id="showlegendLink">
													<a href="#" onclick="showAllocationLegend(); return false;"><fmt:message key="label.showlegend"/></a>
												</span>
												<span id="updateResultLink" style="display: none"></span>
											</div> <%-- mainUpdateLinks Ends --%>
										</div> <%-- boxhead Ends --%>
	
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>
	
										<div id="footer" class="messageBar"></div> 
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
	
	<%-- show legend Begins --%>
	<div id="showLegendArea" style="display: none;overflow: auto;">
		<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pink" width="5%">&nbsp;</td>
				<td width="20%"><div><fmt:message key="buyorders.legend.supercritical"/></div></td>
			</tr>
			<tr>
				<td class="red" width="5%">&nbsp;</td>
				<td width="20%"><fmt:message key="label.criticalpo"/></td>
			</tr>
			<tr>
				<td class="yellow" width="5%">&nbsp;</td>
				<td width="20%"><fmt:message key="label.allocationlegend1"/></td>
			</tr>
			<tr>
				<td class="green" width="5%">&nbsp;</td>
				<td width="20%"><fmt:message key="label.allocationlegend2"/></td>
			</tr>
			<tr>
				<td class="orange" width="5%">&nbsp;</td>
				<td width="20%"><fmt:message key="label.allocationlegend3"/></td>
			</tr>
			<tr>
				<td class="black" width="5%">&nbsp;</td>
				<td width="20%"><fmt:message key="label.allocationlegend4"/></td>
			</tr>
  		</table>
	</div>
	<%-- show legend Ends --%>

	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>
</body>
</html:html>