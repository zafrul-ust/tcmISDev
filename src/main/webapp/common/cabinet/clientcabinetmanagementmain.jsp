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
<script type="text/javascript" src="/js/common/cabinet/clientcabinetmanagementmain.js"></script>
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
	<fmt:message key="clientCabinetManagement"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
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
			showlegend:"<fmt:message key="label.showlegend"/>",
			pleasewait:"<fmt:message key="label.pleasewait"/>",
			accountsysteminputdialog:"<fmt:message key="label.catalogaccountsysIdInput"/>",
			pleaseselect:"<fmt:message key="label.pleaseselect"/>",
	      	selectWorkAreaToAddPartTo:"<fmt:message key="label.selectworkareatoaddpartto"/>",
			myFacilities:"<fmt:message key="label.myfacilities"/>",
			searchInput:"<fmt:message key="error.searchInput.integer"/>"
			};
		
		var companyPermArr = new Array();

		<c:forEach var="companyPermission" items="${authorizedUsersForUsergroup}" varStatus="companyStatus">
	         companyPermArr[<c:out value="${companyStatus.index}"/>] = '<tcmis:jsReplace value="${companyPermission.companyId}"/>';
		</c:forEach>
	
     
	// -->
</script>
	 
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','clientCabinetManagement');myOnLoad();" onunload="closeAllchildren();" onresize="resizeFrames()">
    
<div class="interface" id="mainPage" style="">
	<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<div class="contentArea">
<tcmis:form action="/clientcabinetmanagementresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
      	<td width="100%"   class="optionTitleBoldLeft">
      	<fmt:message key="label.counttype" />:
        <html:select property="countTypeArraySel" styleClass="selectBox" styleId="countTypeArraySel" onchange="countTypeArrayChanged()">
           <html:option value="" key="label.all"/>
            <html:options collection="countTypeDropDownList" property="value" labelProperty="label"/>
        </html:select>
        <input name="countTypeArrayMultiSelTxt" id="countTypeArrayMultiSelTxt" class="inputBox" value="" style="display: none" readonly="readonly" size="33"/>
 		<input type="hidden" name="countTypeArray" id="countTypeArray" value="" />	
		<button id="countTypeMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onmouseout="this.className='listButton'" name="None" value=""  onclick="popMultiSel('countTypeArray');return false;"></button>
		<span id="countTypeArraySpan">
		 </span>	  
         <input name="inactive" id="inactive" type="checkbox" class="radioBtns" value="inactive" <c:if test="${!empty param.inactive}">checked</c:if>>
         <fmt:message key="label.showinactiveparts"/>
        </td>

      </tr>
		<tr>
        <td width="100%"   class="optionTitleBoldLeft">
          <fmt:message key="label.search"/>:
			<html:select property="itemOrPart" styleId="itemOrPart" styleClass="selectBox">
				<html:option value="cat_part_no" key="label.partnumber"/>
				<html:option value="item_id" key="label.itemid"/>
			</html:select>
			<html:select property="criterion" styleId="criterion" styleClass="selectBox">
				<html:option value="contains" key="label.contain"/>
				<html:option value="is" key="label.is"/>
			</html:select>
			<input class="inputBox" type="text" name="criteria" id="criteria" value="<c:out value="${param.criteria}"/>" size="15">
        </td>
      </tr>
      <tr>
        <td width="100%"  class="optionTitleBoldLeft">
        
          <input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search();">
          <input name="submitExcel" id="submitExcel" type="button" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">          
          <span id="showHideBtn" name="showHideBtn">
            <input title="<fmt:message key="label.createparttemplatetitle"/>" name="createDataB" id="createDataB" type="button" class="inputBtns" value="<fmt:message key="label.createminmaxtemplate"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="createTemplateData()"/>  
            <input name="uploadB" id="uploadB" type="button" class="inputBtns" value="<fmt:message key="label.uploadpartleveldata"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="uploadList(); return false;">
          </span>
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
	<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
	<input name="sourcePage" id="sourcePage" type="hidden" value="clientCabinetManagement">
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
										<span id="showlegendLink" style="display: "><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
										<span id="updateResultLink" style="display: none">
											| <a href="#" onclick="resultFrame.updateGrid(); return false;"><fmt:message key="label.update"/></a>
										</span>
										<span id="addNonManagedMaterialLink" style="display: none">
											| <a href="#" onclick="resultFrame.getNonManagedMaterial(); return false;"><fmt:message key="label.addnonmanagedmatl"/></a>
										</span>
										 <span id="deleteSpan" style="display: none"> | <a href="#" onclick="resultFrame.deleteRow(); return false;"><fmt:message key="label.delete"/></a></span>
										 <span id="addPartLink"> | <a href="javascript:addPart('addPartFromLink')"><fmt:message key="label.addpart"/></a></span>
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