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
<script type="text/javascript" src="/js/common/cabinet/clientcabinetputawaymain.js"></script>
<script type="text/javascript" src="/js/common/cabinet/workareasearchtemplate.js"></script>

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
	<fmt:message key="clientCabinetPutAway"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

var altCompanyId2 = new Array();		
var altCompanyName2 = new Array();
var altFacilityId2 = new Array();
var altFacilityName2 = new Array();
var test = new Array();

<c:set var="companyCount" value='0'/>

<c:forEach var="company" items="${facAppReportViewBeanCollection}" varStatus="companyStatus">
	<c:set var="addCompany" value="false"/>
	<c:set var="facilityCount" value='0'/>
	
	<c:forEach var="facility" items="${company.facilityList}" varStatus="facStatus">
	
    test['<tcmis:jsReplace value="${facility.facilityId}"/>'] = '${facility.putAwayRequired}';
    
	<c:if test="${facility.putAwayRequired}">
	// only add facility if put away is required
		<c:if test="${!addCompany}">
		// add to company list if it hasn't been added
			altCompanyId2[${companyCount}] = '<tcmis:jsReplace value="${company.companyId}"/>';		
			altCompanyName2[${companyCount}] = '<tcmis:jsReplace value="${company.companyName}"/>';
			
			altFacilityId2['<tcmis:jsReplace value="${company.companyId}"/>'] = new Array();
			altFacilityName2['<tcmis:jsReplace value="${company.companyId}"/>'] = new Array();
			
			<c:set var="addCompany" value="true"/>
			<c:set var="companyCount" value='${companyCount+1}'/>
		</c:if>
		
		altFacilityId2['<tcmis:jsReplace value="${company.companyId}"/>'][${facilityCount}]='<tcmis:jsReplace value="${facility.facilityId}"/>';
		altFacilityName2['<tcmis:jsReplace value="${company.companyId}"/>'][${facilityCount}]='<tcmis:jsReplace value="${facility.facilityName}"/>';
		
		<c:set var="facilityCount" value='${facilityCount+1}'/>
	</c:if>
		
	</c:forEach>
</c:forEach>

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
			searchInput:"<fmt:message key="error.searchInput.integer"/>"
			};
	// -->
	 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','cabinetPutAway');myOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">
	<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<div class="contentArea">
<tcmis:form action="/clientcabinetputawayresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="950" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer"  style=width:100%>
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	  <%-- Row 1 --%>
	 <%@ include file="/common/cabinet/workareasearchtemplate.jsp" %>
	 <tr>
        <td class="optionTitleBoldLeft">
          <fmt:message key="label.search"/>:
			<html:select property="searchArgument" styleId="searchArgument" styleClass="selectBox">
				<html:option value="partNumber" key="label.partnumber"/>
				<html:option value="item" key="label.itemid"/>
				<html:option value="packingList" key="label.packinglist"/>
			</html:select>
			<html:select property="criterion" styleId="criterion" styleClass="selectBox">
				<html:option value="like" key="label.contain"/>
				<html:option value="is" key="label.is"/>
			</html:select>
			<input class="inputBox" type="text" name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" size="15">	
        </td>
     </tr>
     <tr>
        <td class="optionTitleBoldLeft">
        	<fmt:message key="label.datedelivered"/>:&nbsp;
	         <%--Date search fields.getDateTag is used to set default values for the search field --%>
	         <input class="inputBox pointer" readonly type="text" name="dateStart" id="dateStart" value="" onClick="return getCalendar(document.clientCabinetPutAwayForm.dateStart,null,document.clientCabinetPutAwayForm.dateEnd);"
			        maxlength="10" size="12"/>
			 <fmt:message key="label.to"/>&nbsp;
			 <input class="inputBox pointer" readonly type="text" name="dateEnd" id="dateEnd" value="" onClick="return getCalendar(document.clientCabinetPutAwayForm.dateEnd,document.clientCabinetPutAwayForm.dateStart);"
			        maxlength="10" size="12"/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldLeft">
          <fmt:message key="label.putawaymethod"/>:
			<html:select property="putAwayMethod" styleId="putAwayMethod" styleClass="selectBox">
				<html:option value="ALL" key="label.all"/>
				<html:option value="CONTAINER" key="label.container"/>
				<html:option value="CABINET" key="label.cabinet"/>
			</html:select>
        </td>
     </tr>
      <tr>
        <td width="100%"  class="optionTitleBoldLeft">
          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search();">
          <input name="submitExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
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
	<input name="sourcePage" id="sourcePage" type="hidden" value="clientCabinetPutAway">
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
										<div id="updateResultLink" style="display: none">
											<a href="#" onclick="resultFrame.submitUpdate(); return false;"><fmt:message key="button.submit"/></a> | 
											<a href="#" onclick="resultFrame.printPackingList(); return false;"><fmt:message key="label.printpackinglist"/></a>
										</div>
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