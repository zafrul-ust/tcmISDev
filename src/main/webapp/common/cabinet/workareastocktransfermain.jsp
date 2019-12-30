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
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
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
<script type="text/javascript" src="/js/common/cabinet/workareastocktransfermain.js"></script>

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

<title>
	<fmt:message key="workAreaStockTransfer"/>
</title>

	<script language="JavaScript" type="text/javascript">
	<!--
		var altCompanyId = new Array();		
		var altCompanyName = new Array();
		var altFacilityId = new Array();
		var altFacilityName = new Array();
		var altAreaId = new Array();
		var altAreaName = new Array();
		var altBuildingId = new Array();
		var altBuildingName = new Array();
		var wagColl = new Array();
		var deptColl = new Array();
	
		<c:forEach var="company" items="${facAppReportViewBeanCollection}" varStatus="companyStatus">
			// add company 	
			altCompanyId[${companyStatus.index}] = '<tcmis:jsReplace value="${company.companyId}"/>';		
			altCompanyName[${companyStatus.index}] = '<tcmis:jsReplace value="${company.companyName}"/>';
			
			// add facilities for this company			
			altFacilityId['<tcmis:jsReplace value="${company.companyId}"/>'] = new Array();
			altFacilityName['<tcmis:jsReplace value="${company.companyId}"/>'] = new Array();
		
			<c:forEach var="facility" items="${company.facilityList}" varStatus="facStatus">
				altFacilityId['<tcmis:jsReplace value="${company.companyId}"/>'][${facStatus.index}]='<tcmis:jsReplace value="${facility.facilityId}"/>';
				altFacilityName['<tcmis:jsReplace value="${company.companyId}"/>'][${facStatus.index}]='<tcmis:jsReplace value="${facility.facilityName}"/>';
	
				// add areas for this facility
				altAreaId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array();
				altAreaName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array();
				
				<c:forEach var="area" items="${facility.areaList}" varStatus="areaStatus">
					altAreaId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'][${areaStatus.index}]='<tcmis:jsReplace value="${area.areaId}"/>';
					altAreaName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'][${areaStatus.index}]='<tcmis:jsReplace value="${area.areaName}"/>';
					
					// add buildings for this area
					altBuildingId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'] = new Array();
					altBuildingName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'] = new Array();
				
		            <c:forEach var="building" items="${area.buildingList}" varStatus="buildingStatus">
		            	altBuildingId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'][${buildingStatus.index}]='<tcmis:jsReplace value="${building.buildingId}"/>';
		            	altBuildingName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'][${buildingStatus.index}]='<tcmis:jsReplace value="${building.buildingName}"/>';
		            </c:forEach>
				</c:forEach>
				
				// add work area groups for this facility
				var currWagArr = new Array();
				<c:forEach var="wag" items="${facility.reportingEntityList}" varStatus="wagStatus">
					currWagArr[<c:out value="${wagStatus.index}"/>] = {wagName:'<tcmis:jsReplace value="${wag.description}"/>',
																	  wagId:'${wag.reportingEntityId}'};
				</c:forEach>
				wagColl['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = currWagArr;
				
				// add departments for this facility
				var currDepArr = new Array();
				<c:forEach var="dept" items="${facility.deptList}" varStatus="deptStatus">
					currDepArr[<c:out value="${deptStatus.index}"/>] = {deptName:'<tcmis:jsReplace value="${dept.deptName}"/>',
																	  deptId:'${dept.deptId}'};
				</c:forEach>
				deptColl['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = currDepArr;
			</c:forEach>
		</c:forEach>
		
		// user default facility for each company
		var myDefaultFacilities = {
			<c:forEach var="bean" items="${personnelBean.myCompanyDefaultFacilityIdCollection}" varStatus="status">
				'<tcmis:jsReplace value="${bean.companyId}"/>':'<tcmis:jsReplace value="${bean.facilityId}"/>'
				<c:if test="${!status.last}">,</c:if>
			</c:forEach>	
		};
	
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
			pleasewait:"<fmt:message key="label.pleasewait"/>",
			pleaseselect:"<fmt:message key="label.pleaseselect"/>",
	      	selectWorkAreaToAddPartTo:"<fmt:message key="label.selectworkareatoaddpartto"/>",
			searchInput:"<fmt:message key="error.searchInput.integer"/>",
            differentFromWorkArea:"<fmt:message key="label.differentfromworkarea"/>",
            selectInventoryGroup:"<fmt:message key="label.missinginventorygroup"/>",
            selectWorkAreaOrInventoryGroup:"<fmt:message key="label.missinginventorygrouporworkarea"/>",    
            select:"<fmt:message key = "label.pleaseselectaworkarea"/>"
			};
	// -->
	 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','workAreaStockTransfer');myOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">
	<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<div class="contentArea">
<tcmis:form action="/workareastocktransferresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="1000" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer"  style=width:100%>
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<tr>
	  	  <td colspan="2" nowrap width="100%" class="optionTitleBold">
		  	 <span id="company">
	        	<fmt:message key="label.company"/>:&nbsp;
				<select class="selectBox" id="companyId" name="companyId" onchange="companyChanged()">
				</select>
                &nbsp;&nbsp;&nbsp;&nbsp;   
            </span>
            <fmt:message key="label.facility"/>:&nbsp;
			<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
			</select>
         </td>
      </tr>
      <tr>
      	<td nowrap width="100%" class="optionTitleBold">
      		<fmt:message key="label.from"/>:
      	</td>
      	<td nowrap width="100%" class="optionTitleBold">
      		<fmt:message key="label.to"/>:
      	</td>
      </tr>
      <tr>
      	<c:forEach begin="0" end="1" var="index">
			<td>
	      		<table>
	      			<tr>
				      	<td width="100%" nowrap="true" class="optionTitleBoldLeft">
							<fmt:message key="label.area"/>:&nbsp;
							<select class="selectBox" id="input[${index}].areaIdSel" name="input[${index}].areaIdSel" onchange="areaChanged(${index});">
							</select>
							<input name="input[${index}].areaIdMultiSelTxt" id="input[${index}].areaIdMultiSelTxt" value="" class="inputBox"  style="display: none" readonly="readonly" size="33"/>
					  		<input type="hidden" name="input[${index}].areaId" id="input[${index}].areaId" value="" />	
							<button id="input[${index}].areaMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value="" onClick="popMultiSel('input[${index}].areaId');return false;" style="display:none;"></button>
							&nbsp;&nbsp;&nbsp;
							<span id="input[${index}].areaIdSpan">
							 </span>	  
							<%--<span id="buildingFloorRoom"> --%>
							<fmt:message key="label.building"/>:&nbsp;
							<select class="selectBox" id="input[${index}].buildingIdSel" name="input[${index}].buildingIdSel" onchange="buildingChanged(${index});">
							</select>
							<input name="input[${index}].buildingIdMultiSelTxt" id="input[${index}].buildingIdMultiSelTxt" value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
				  			<input type="hidden" name="input[${index}].buildingId" id="buildingId" value="" />	
							<button id="input[${index}].buildingMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value=""  onClick="popMultiSel('input[${index}].buildingId');return false;" <c:if test="${hideMultiSel == 'Y'}">style="display:none;"</c:if>></button>
							&nbsp;&nbsp;&nbsp;
							<span id="input[${index}].buildingIdSpan">
						     </span>	
				      	
				      	</td>
				      </tr>
				      <tr>
				      		<td width="100%" nowrap="true" class="optionTitleBoldLeft">
					          <fmt:message key="label.workareagroup"/>:
					          <select class="selectBox"  name="input[${index}].reportingEntityId" id="input[${index}].reportingEntityId" onchange="workAreaGroupChanged(${index})">
					          </select>
					          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<fmt:message key="label.department"/>:&nbsp;
								<select class="selectBox" id="input[${index}].deptIdSel" name="input[${index}].deptIdSel" onchange="deptChanged(${index});">
								</select>
								<input name="input[${index}].deptIdMultiSelTxt" id="input[${index}].deptIdMultiSelTxt"  value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
						 			<input type="hidden" name="input[${index}].deptId" id="input[${index}].deptId" value="" />	
								<button id="input[${index}].deptMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'"
						                	name="None" value=""  onClick="popMultiSel('input[${index}].deptId');return false;" <c:if test="${hideMultiSel == 'Y'}">style="display:none;"</c:if>> </button>
						        <span id="input[${index}].deptIdSpan">
						        </span>	  
					        </td>
				      </tr>
				      <tr >
					      <td width="100%" nowrap="true" class="optionTitleBoldLeft">
					      		<fmt:message key="label.workarea"/>:
					      		<select name="input[${index}].applicationId" id="input[${index}].applicationId" class="selectBox" onchange="workAreaChanged(${index});"></select>
						        </span>	                		       	
					      </td>
				      </tr>
					 <tr>
	      		</table>
	      	</td>
      	</c:forEach>	
      </tr>
      <tr>
        <td class="optionTitleBoldLeft">
          <fmt:message key="label.search"/>:
			<html:select property="searchArgument" styleId="searchArgument" styleClass="selectBox">
				<html:option value="CAT_PART_NO" key="label.partnumber"/>
				<html:option value="ITEM_ID" key="label.itemid"/>
			</html:select>
			<html:select property="criterion" styleId="criterion" styleClass="selectBox">
				<html:option value="CONTAINS" key="label.contain"/>
				<html:option value="IS" key="label.is"/>
			</html:select>
			<input class="inputBox" type="text" name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" size="15">	
        </td>
     </tr>
      <tr>
        <td width="100%"  class="optionTitleBoldLeft">
          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search();">
          <input name="submitExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        </td>
        <td  width="100%" nowrap="true" class="optionTitleBoldLeft">
            <fmt:message key="label.warehouse"/>:&nbsp;
			<select class="selectBox" id="inventoryGroup" name="inventoryGroup" onchange="inventoryGroupChanged()">
			</select>
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
	<input name="uAction" id="uAction" type="hidden">
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
	<input name="facilityName" id="facilityName" type="hidden" value="">
	<input name="application" id="application" type="hidden" value="">
	<input name="applicationDesc" id="applicationDesc" type="hidden" value="">
	<input name="applicationId" id="applicationId" type="hidden" value="">
    <input name="workAreaSelectCount" id="workAreaSelectCount" type="hidden" value="">
	<input name="binId" id="binId" type="hidden" value="">
	<input name="sourcePage" id="sourcePage" type="hidden" value="workAreaStockTransfer">
	<input name="accountSysId" id="accountSysId" type="hidden" value="">
    <input name="maxData" id="maxData" type="hidden" value="100"/> 
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
	<!-- Transit Page Begins -->
	<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
		<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
	</div>
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
											<a href="#" onclick="resultFrame.submitUpdate(); return false;"><fmt:message key="button.submit"/></a> 
										</span>
									</div> <%-- mainUpdateLinks Ends --%>
								</div> <%-- boxhead Ends --%>

								<div class="dataContent">
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

<!-- Input Dialog Window Begins -->
<div id="accountSysDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center"  width="100%">
				<select name="accountSystemSelectBox" id="accountSystemSelectBox" class="selectBox">
				</select>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" width="100%">
				<input name="accountSysOk"  id="accountSysOk"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="accountSysOkClose();"/>
			</td>
		</tr>
	</table>
</div>

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

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.inactiveparts"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>

</body>
</html:html>