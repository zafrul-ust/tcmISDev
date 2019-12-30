<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

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
<link rel="shortcut icon"
	href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/opshubig.jsp"%> 
<%@ include file="/common/locale.jsp"%> <!--Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss /> <%-- Add any other stylesheets you need for the page here --%>
<script type="text/javascript" src="/js/common/formchek.js"></script> 
<script type="text/javascript" src="/js/common/commonutil.js"></script> <!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> <!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script> <script
	type="text/javascript" src="/js/menu/mmenudom.js"></script> 
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> <script
	type="text/javascript" src="/js/menu/contextmenu.js"></script> <%@ include
	file="/common/rightclickmenudata.jsp"%> <!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/pickingqcmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script> 

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title><fmt:message key="pickingqc.title" /></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
pleaseSelectARow:"<fmt:message key="label.pleaseselectarowforupdate"/>",
mrnumber:"<fmt:message key="label.mrnumber"/>",
errorfloat:"<fmt:message key="errors.float"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
pleaseSelectAHub:"<fmt:message key="label.pleaseselectahub"/>",
all:"<fmt:message key="label.all"/>"
};

var pickLists = {};
var hubList;
<c:set var="curHub" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:forEach var="bean" items="${hubPicklistCollection}" varStatus="status">
	<c:choose>
		<c:when test="${curHub != bean.hub}">
			<c:set var="curHub" value='${bean.hub}'/>
			<c:set var="dataCount" value='${0}'/>
			hubList = new Array();
			pickLists["${curHub}"] = hubList;
			hubList[${dataCount}]={picklistId:"${bean.picklistId}",picklistName:"<tcmis:jsReplace value="PL ${bean.picklistId} : ${bean.picklistPrintDate}"/>"};	
		</c:when>
		<c:otherwise>
			<c:set var="dataCount" value='${dataCount+1}'/> 
			hubList[${dataCount}]={picklistId:"${bean.picklistId}",picklistName:"<tcmis:jsReplace value="PL ${bean.picklistId} : ${bean.picklistPrintDate}"/>"};		
		</c:otherwise>
	</c:choose>
</c:forEach>

var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
	<c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
	<c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

	  altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(""
	  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
		,"<c:out value="${status2.current.facilityId}"/>"
	  </c:forEach>
	  );

	  altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array("<fmt:message key="label.all"/>"
	  <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
		,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
	  </c:forEach>
	  );

  </c:forEach>
</c:forEach>

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','${param.pageid}');setDropDowns();showPicklistOptions(document.getElementById('hub').value)" onresize="resizeFrames()" onunload="closeAllchildren()">

<div class="interface" id="mainPage" style=""><!-- Search Frame Begins -->
<div id="searchFrameDiv">
<div class="contentArea"><tcmis:form action="/pickingqcresults.do"
	onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
	<!-- Search Option Begins -->
	<table id="searchMaskTable" width="600" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>
			<div class="roundcont filterContainer">
			<div class="roundright">
			<div class="roundtop">
			<div class="roundtopright"><img
				src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15"
				height="10" class="corner_filter" style="display: none" /></div>
			</div>
			<div class="roundContent"><!-- Insert all the search option within this div -->
			<table border="0" cellpadding="0" cellspacing="0" class="tableSearch">
				<tr>
				      <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
				      <td width="6%" class="optionTitleBoldLeft">
				         <select name="opsEntityId" id="opsEntityId" class="selectBox">
				         </select>
				      </td>
				    <td class="optionTitleBoldRight"><fmt:message key="label.mr" />:</td>
					<td><input name="prNumber" id="prNumber" type="text" class="inputBox" value='<c:out value="${param.prNumber}"/>' size="8"></td>		
				</tr>
				<tr>		
					        <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
					        <td width="10%" class="optionTitleBoldLeft">
					          <select name="hub" id="hub" class="selectBox">
					          </select>
					       </td>
					<td class="optionTitleBoldRight"><fmt:message key="label.picklist" />:</td>
					<td>
					<c:choose>
						<c:when test="${!empty hubPicklistCollection}" >  
							  <select name="picklistId" class="selectBox" id="picklistId" >
							      <option value=""> <fmt:message key="label.all"/> </option>
								  <c:forEach var="bean" items="${hubPicklistCollection}" varStatus="status">
								  		<c:if test="${defaultHub == bean.hubName}" >
						               		<option value="${bean.picklistId}"> <tcmis:jsReplace value="PL ${bean.picklistId} : ${bean.picklistPrintDate}"/> </option>
						         		</c:if>
						          </c:forEach>
					          </select>
				        </c:when>
				        <c:otherwise>
					         	<select name="picklistId" class="selectBox" id="picklistId" >
					         		<option value="<fmt:message key="label.pleaseselect"/>"><fmt:message key="label.pleaseselect"/></option>
					         	</select>
				        </c:otherwise>
				    </c:choose>
					</td>
				</tr>
				<tr>
				       <td width="5%" class="optionTitleBoldRight">
				       <fmt:message key="label.inventorygroup"/>:
				       </td>
				      <td width="10%" class="optionTitleBoldLeft">
				        <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
				          <select name="inventoryGroup" id="inventoryGroup" class="selectBox" >            
				          </select>
				          </td>	
				</tr>
				<tr>
					<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
			       <td width="5%" class="optionTitleBoldLeft">
			          <select name="facilityId" id="facilityId" class="selectBox">
			          </select>
			          </td>
			      </tr>
			      <tr>
			       </tr>
				<tr>
					<td class="optionTitleBoldLeft" colspan="2">
					<input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
					<input name="buttonCreateExcel" id="buttonCreateExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel();">
					</td>
				</tr>
			</table>
			<!-- End search options --></div>
			<div class="roundbottom">
			<div class="roundbottomright"><img
				src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15"
				class="corner" style="display: none" /></div>
			</div>
			</div>
			</div>

			</td>
		</tr>
	</table>

	<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
		<div class="spacerY">&nbsp;
		<div id="errorMessagesArea" class="errorMessages"><html:errors />
		</div>
		</div>
	</c:if>
	<!-- Error Messages Ends -->


	<!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;">
		<input name="userAction" id="userAction" type="hidden">
		<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
		<%--<input name="searchHeight" id="searchHeight" type="hidden" value="150">--%>
		<input name="selectedHubName" id="selectedHubName" type="hidden" value="">
		<input type="hidden" name="isWmsHub"  id="isWmsHub" value="N"/>
	</div>
	<!-- Hidden elements end -->
</tcmis:form></div>
</div>
<!-- Search Frame Ends -->

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="pink">&nbsp;</td><td class="legendText"><fmt:message key="buyorders.legend.supercritical"/><br/><fmt:message key="label.lddown"/></td></tr>
  	<tr><td width="100px" class="red">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalpo"/></td></tr>
    <tr><td width="100px" class="yellow">&nbsp;</td><td class="legendText"><fmt:message key="label.pickingqclegend1"/></td></tr>
    <tr><td width="100px" class="error">&nbsp;</td><td class="legendText"><fmt:message key="label.pickingqclegend2"/></td></tr>
    <tr><td width="100px" class="orange">&nbsp;</td><td class="legendText"><fmt:message key="label.pickingqclegend3"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>

	<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br>
				<br>
				<br>
				<fmt:message key="label.pleasewait" />
				<br>
				<br>
				<br>
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
	
			<div id="resultGridDiv" style="display: none;">
				<%-- Search results start --%> 
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
											--%>
											<div id="mainUpdateLinks">
												<span id="showlegendLink">
													<a href="#" onclick="showpickingpagelegend(); return false;"><fmt:message key="label.showlegend" /></a>
												</span>
												<span id="updateResultLink" style="display: none"> |
													<a href="#" onclick="window.frames['resultFrame'].doConfirm();"><fmt:message key="button.confirm" /></a>
												</span>
												<span id="doPrintBolShortLink"> |
													<a href="#" onclick="window.frames['resultFrame'].doPrintBolShort(); return false;"><fmt:message key="picklistreprint.printbolshort" /></a>
												</span>
												<span id="doPrintBolLongLink"> |
													<a href="#" onclick="window.frames['resultFrame'].doPrintBolLong(); return false;"><fmt:message key="picklistreprint.printbollong" /></a>
												</span>
												<span id="doPrintDelieveryLabelLink"> |
													<a href="#" onclick="window.frames['resultFrame'].doPrintBoxLabels(); return false;"><fmt:message key="label.deliverylabels" /></a>
												</span>
												<span id="doReprintLablesLink"> |
													<a href="#" onclick="window.frames['resultFrame'].doReprintLables(); return false;"><fmt:message key="pickingqc.reprintlabels" /></a>
												</span>
												<span id="doRDeliveryTicketLink"> |
													<a href="#" onclick="window.frames['resultFrame'].doDeliveryTicket(); return false;"><fmt:message key="pickingqc.deliveryticket" /></a>
												</span>
												<span id="doExitLablesLink"> |
													<a href="#" onclick="window.frames['resultFrame'].doPrintExitLabels(); return false;"><fmt:message key="label.printexitlabels" /></a>
												</span>
									
											</div>
										</div>
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>
								
										<%-- result count and time --%>
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
	
	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;">
	</div>


</body>
</html:html>