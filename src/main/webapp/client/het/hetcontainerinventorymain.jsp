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
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>

	<!-- This handles all the resizing of the page and frames -->
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	<!-- This handles which key press events are disabled on this page -->
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
	<!-- This handles the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>
	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
	<script type="text/javascript" src="/js/client/het/hetcontainerinventorymain.js"></script>
	
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
	
	<title>
		<fmt:message key="containerInventory"/>	
	</title>

	
	<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = 
		{alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		errors:"<fmt:message key="label.errors"/>",  
		validvalues:"<fmt:message key="label.validvalues"/>",
		pleasewait:"<fmt:message key="label.pleasewait"/>",	
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
	};

	
		var altFacilityId = new Array(
		<c:forEach var="facility" items="${personnelBean.fullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			'<tcmis:jsReplace value="${facility.facilityId}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altFacilityName = new Array(
		<c:forEach var="facility" items="${personnelBean.fullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
		   '<tcmis:jsReplace value="${facility.facilityName}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altWorkAreaGroupId = new Array();
		var altWorkAreaGroupDesc = new Array();
		<c:forEach var="facility" items="${personnelBean.fullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			altWorkAreaGroupId['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
					'-1'
			<c:forEach var="wagBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:if test="${wagBean.reportingEntityStatus == 'A'}">
				 	,'<tcmis:jsReplace value="${wagBean.reportingEntityId}"/>'
				</c:if>
			</c:forEach>
			);
			altWorkAreaGroupDesc['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
			 		messagesData.all
		 	<c:forEach var="wagBean"  items="${facility.workAreaGroupColl}" varStatus="status1">
		   		<c:if test="${wagBean.reportingEntityStatus == 'A'}">
				 	,'<tcmis:jsReplace value="${wagBean.reportingEntityDesc}"/>'
				</c:if>
			</c:forEach>
			);
		</c:forEach>
	
		var altWorkAreaArray = new Array();
		var altWorkAreaDescArray = new Array();
		<c:forEach var="facility" items="${personnelBean.fullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>--1'] = new Array(
					'-1'
			);
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>--1'] = new Array(
			 		messagesData.all
		    	);
		</c:forEach>

		<c:forEach var="facility" items="${personnelBean.fullUserFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:forEach var="workAreaGroupBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:set var="workAreaCount" value='${0}'/>
				altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						<c:if test="${workAreaCount > 0}">,</c:if>
						'<tcmis:jsReplace value="${workAreaBean.applicationId}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
				);
				<c:set var="workAreaCount" value='0'/>
				altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						<c:if test="${workAreaCount > 0}">,</c:if>
						'<tcmis:jsReplace value="${workAreaBean.applicationDesc}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
		      		);
			</c:forEach>
		</c:forEach>
		
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','containerInventory');myOnLoad();" onresize="resizeFrames()">

	<div class="interface" id="mainPage" style="">
		
		<!-- Search Div Begins -->
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/hetcontainerinventoryresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">

					<!-- Search Option Begins -->
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
											<table  class="tableSearch" width="800" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td width="15%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
													<td width="25%" class="optionTitleBoldLeft">
														<select class="selectBox"  name="facilityId" id="facilityId" size="1" onchange="facilityChanged()"></select>
													</td>
													<td width="10%" class="optionTitleBoldRight">
														<fmt:message key="label.search" />:&nbsp;
													</td>
													<td width="50%" class="optionTitleLeft">
														<select name="searchField" class="selectBox" id="searchField">
															<option value="containerId"><fmt:message key="label.containerid" /></option>
															<option value="catPartNo"><fmt:message key="label.partno" /></option>
															<option value="materialDesc"><fmt:message key="label.materialdesc" /></option>
															<option value="msdsNumber"><fmt:message key="label.msds" /></option>
															<option value="itemId"><fmt:message key="label.itemid" /></option>
														</select>
														<select name="searchMode" class="selectBox" id="searchMode">
															<option value="is"><fmt:message key="label.is" /></option>
															<option value="like"><fmt:message key="label.contains" /></option>
															<option value="startsWith"><fmt:message key="label.startswith" /></option>
														</select>
														<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='<c:out value="${param.searchArgument}"/>' size="25" />
													</td>
													
												</tr>
												<tr>
													<td  class="optionTitleBoldRight"><fmt:message key="label.workareagroup"/>:</td>
													<td class="optionTitleBoldLeft">
														<select class="selectBox"  name="workAreaGroup" id="workAreaGroup" size="1"  onchange="workAreaGroupChanged()"></select>
													</td>
												</tr>
												<tr>
													<td  class="optionTitleBoldRight"><fmt:message key="label.workarea"/>:</td>
													<td  class="optionTitleBoldLeft">
														<select name="workArea" id="workArea" class="selectBox" size="1"></select>
													</td>
												</tr>
												<tr>
													<td colspan=4 class="optionTitleBoldLeft">
														<input name="submitSearch" id="submitSearch" type="button" value="<fmt:message key='label.search'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return search()"/>
														<input name="buttonCreateExcel" id="buttonCreateExcel" type="button" value="<fmt:message key='label.createexcelfile'/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return generateExcel()"/>
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
						<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="searchHeight" id="searchHeight" type="hidden" value="150">
						<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
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
												<span id="updateResultLink" style="display: none">
													<a href="#" onclick="resultFrame.update();"><fmt:message key="label.update" /></a>
													| <a href="#" onclick="resultFrame.transfer();"><fmt:message key="label.transfer" /></a>
													| <a href="#" onclick="resultFrame.deleteRecord();"><fmt:message key="label.delete" /></a>
												</span>
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
	
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">		
	</div>

</body>
</html:html>