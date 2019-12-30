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
<script type="text/javascript" src="/js/hub/pickinggroupmgmtmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title>ThisIsATest</title>
<script language="JavaScript" type="text/javascript"/>
<!-- 
var messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
mr:"<fmt:message key="label.mr"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
expireDaysInteger:"<fmt:message key="error.expiredays.integer"/>",
picklistConfirm:"<fmt:message key="picklistpicking.confirm.generate"/>",
total:"<fmt:message key="label.total"/>",
errors:"<fmt:message key="label.errors"/>"};

useLayout = false;

var pickingGroups = [
	{hub:"",
		  groups:[{name:'<fmt:message key="label.any"/>',id:''}]
	},
	<c:set var="pgHub" value="0"/>
	<c:set var="pgCount" value="0"/>
	<c:forEach var="pg" items="${vvPickingGroupColl}" varStatus="status">
 		<c:if test="${pgHub eq pg.hub}">
			<c:if test="${pgCount > 0}">,</c:if>
		</c:if>
		<c:if test="${pgHub != pg.hub}">
			<c:if test="${pgHub != 0}">]},</c:if>
			<c:set var="pgCount" value="0"/>
			<c:set var="pgHub" value="${pg.hub}"/>
		{hub:"${pg.hub}",
		  groups:[{name:'<fmt:message key="label.any"/>',id:''},
		</c:if>
		{name:'<tcmis:jsReplace value="${pg.pickingGroupName}" processMultiLines="false"/>',
				id:'${pg.pickingGroupId}'}
		<c:set var="pgCount" value="${pgCount+1}"/>
	</c:forEach>
]}];
//-->
</script>
</head>
<body onload="loadLayoutWin('','pickingGroupMgmt');setOps();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="pickinggroupmgmtresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
					<table width="710" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      				<tr>
					 	<td width="5%" class="optionTitleBoldRight">
							<fmt:message key="label.operatingentity"/>:
					 	</td>
					 	<td width="40%" class="optionTitleLeft">
							<select name="opsEntityId" id="opsEntityId" class="selectBox">
							</select>
							<input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value="${param.opsEntityId}"/>
						</td>
						<td width="10%">&nbsp;</td>
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
						</td>

						<td width="10%" class="optionTitleLeft">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="5%" class="optionTitleBoldRight">
							&nbsp;
						</td>
						<td width="40%" class="optionTitleLeft">
							<label for="activeOnly" class="optionTitleBoldLeft"><fmt:message key="label.showactiveonly"/><input type="checkbox" id="activeOnly" name="activeOnly" checked/></label>
						</td>
						<td width="10%" class="optionTitleLeft">
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
<!-- Build this section only if there is an error message to display. For the search section, we show the error messages within its frame -->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
	<div class="spacerY">&nbsp;
		<div id="searchErrorMessagesArea" class="errorMessages">
			<html:errors />
		</div>
	</div>
</c:if>
<!-- Error Messages Ends -->
<!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="uAction" id="uAction" value=""/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
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
								<a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a> |
								<a href="#" onclick="call('addPickingGroupRow')"><fmt:message key="label.addrow"/></a> |
								<a href="#" onclick="call('submitUpdate')"><fmt:message key="label.update"/></a>
								<span id="updateResultLink" style="display: none"></span>
							</div>
						</div>
						<div class="dataContent"> 
							<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="400" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
	<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100px" class="black legendText">&nbsp;</td>
			<td class="legendText"><fmt:message key="label.inactive"/></td>
		</tr>
	</table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>