<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/updateitemshelflifemain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title><fmt:message key="shelfLifeManagement"/></title>
<script language="JavaScript" type="text/javascript"/>
<!--
var windowCloseOnEsc = true;

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
searchInput:"<fmt:message key="label.inputSearchText"/>",
errors:"<fmt:message key="label.errors"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
inputsearchtext:"<fmt:message key="label.inputSearchText"/>"};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;

<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
var searchArray = [
    { id:'Item ID', text:'<tcmis:jsReplace value="${itemid}"/>'}
];
//-->
</script>
</head>
<body onload="loadLayoutWin('','updateItemShelfLife');setOps();setDefault();setSearchWhat(searchArray);" onunload="closeAllchildren();" onresize="resizeFrames()" >
	<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="/updateitemshelfliferesults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
					<%-- Insert all the search option within this div --%>
					<!-- Search Option Table Start -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

					<tr>
					 <td width="5%" class="optionTitleBoldRight">
						<fmt:message key="label.operatingentity"/>:
					 </td>
					 <td width="40%" class="optionTitleLeft">
						<select name="opsEntityId" id="opsEntityId" class="selectBox">
						</select>
						<input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value="${param.opsEntityId}"/>
					 </td>
					<td width="5%">&nbsp;</td>
					</tr>

					<tr>
					 <td width="5%" class="optionTitleBoldRight">
						<fmt:message key="label.hub"/>:
					 </td>
					 <td width="40%" class="optionTitleLeft">
						<select name="sourceHub" id="sourceHub" class="selectBox">
						</select>
						<input type="hidden" name="selectedHub" id="selectedHub" value="${param.sourceHub}"/>
						
					 <c:set var="selectedHubName" value='${param.sourceHubName}'/>
					 <c:set var="receivingQcPermission" value=''/>
					 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" facilityId="${selectedHubName}">
					  <c:set var="receivingQcPermission" value='Yes'/>
					 </tcmis:inventoryGroupPermission>

					 </td>

						<td width="5%" class="optionTitleLeft">
							&nbsp;
						</td>
					</tr>

					<tr>
					 <td width="5%" class="optionTitleBoldRight">
						<fmt:message key="label.inventorygroup"/>:
					 </td>
					 <td width="40%" class="optionTitleBoldLeft">
						<select name="inventoryGroup" id="inventoryGroup" class="selectBox">
						</select>
						<input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value="${param.inventoryGroup}"/>

					 </td>

						<td width="5%" class="optionTitleLeft">
							&nbsp;
						</td>
					</tr>

					<tr>
					  <td width="5%" class="optionTitleBoldRight">
						 <fmt:message key="label.search"/>:
					  </td>
					  <td width="40%" class="optionTitleLeft" nowrap>
						 <select name="searchWhat" id="searchWhat"  onchange="getSelectedSearchWhat();" class="selectBox">
						 </select>
						 <input type="hidden" name="selectedSearchWhat" id="selectedSearchWhat" value="${param.searchWhat}"/>
						 <html:select property="searchType" styleId="searchType" styleClass="selectBox">
								<html:option value="is" ><fmt:message key="label.is"/></html:option>
								<html:option value="contains"><fmt:message key="label.contains"/></html:option>
								<html:option value="startsWith" key="label.startswith"/>
								<html:option value="endsWith" key="label.endswith"/>
						 </html:select>
						 <html:text property="search" styleId="search" size="20" styleClass="inputBox"/>
					  </td>

						<td width="5%" class="optionTitleLeft">
							&nbsp;
						</td>
					</tr>

					<tr>
					<td width="80%" class="optionTitleLeft" colspan="3">
					<input onclick="return submitSearchForm()" type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="search" id="search"/>&nbsp;
					<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExl" id="createExl" onclick="createXSL()"/>
					</td>
					</tr>
					</table>
					<!-- Search Option Table end -->
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
<!-- Error Messages Begins --><!-- Build this section only if there is an error message to display. For the search section, we show the error messages within its frame --><c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">	<div class="spacerY">&nbsp;		<div id="searchErrorMessagesArea" class="errorMessages">			<html:errors />		</div>	</div></c:if><!-- Error Messages Ends --><!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="userAction" id="userAction" value=""/>
	<input type="hidden" name="sourceHubName" id="sourceHubName" value="<c:out value="${selectedHubName}"/>"/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<input name="searchHeight" id="searchHeight" type="hidden" value="125"/>
</div>
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	<br><br><br><fmt:message key="label.pleasewait"/>
	<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
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
						<%-- boxhead Begins --%>
						<div class="boxhead">
							<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
								Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
							<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
								<span id="updateResultLink" style="display: none"></span>
							</div>
						</div>
						<div class="dataContent"> 
							<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
<!-- Search results end -->
</div>
</div>
<!-- Result Frame Ends -->
</div>
<!-- close of interface -->
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
	<div id="errorMessagesAreaBody" class="bd">
		<html:errors/>
	</div>
</div>
</body>
</html:html>