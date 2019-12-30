<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	<%--Use this tag to get the correct CSS class.
	This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. --%>
	<tcmis:gridFontSizeCss />

	<%-- Add any other stylesheets you need for the page here --%>
	<script type="text/javascript" src="/js/common/formchek.js"></script>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
	<%-- This handels which key press events are disabeled on this page --%>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>

	<%-- This handels the menu style and what happens to the right click on the whole page --%>
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

	<%-- For Calendar support --%>
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

	<!-- Add any other javascript you need for the page here -->
	<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

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

	<title></title>

	<script language="JavaScript" type="text/javascript">
		<!--
		windowCloseOnEsc = true;
		var children = new Array(); 

		//add all the javascript messages here, this for internationalization of client side javascript messages
		var messagesData = new Array();
		messagesData={
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			all:"<fmt:message key="label.all"/>",
			showlegend:"<fmt:message key="label.showlegend"/>",
			errors:"<fmt:message key="label.errors"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>"
		};
		useLayout = false;
		
		var fromListManagement = false;
		
		<c:choose>
		<c:when test="${param.fromListManagement eq 'Yes'}"> 
		  fromListManagement = true;
		</c:when>
		<c:otherwise>
		</c:otherwise>
		</c:choose>

		function closeAllchildren() 
		{ 
			{
				try {
					for(var n=0;n<children.length;n++) {
						children[n].closeAllchildren(); 
					}
				} catch(ex)
				{
				}
				if(!window.closed)
					window.close();
			} 
		} 

		function getSearchString() {
			try {
				var searchString = '';
				
				if (!fromListManagement) {
					searchString = parent.opener.getCasNumber();
					var defaultSearch = 'cas_number';
		
					if (searchString == null || searchString.length == 0) {
						searchString = parent.opener.getMsdsChemicalName();
						defaultSearch = 'preferred_name';
					}
					
					document.getElementById('searchField').value = defaultSearch;
				}
				
				if (searchString.length > 0) {
					document.getElementById('searchArgument').value = searchString;
					submitSearchForm();
					document.genericForm.submit();
				}else {
					document.getElementById('searchArgument').focus();
				}
				
			} catch(ex){}

		}

		function createNew() {
			document.getElementById("userAction").value = 'new';
			document.getElementById("uAction").value = 'new';
			document.genericForm.target = '';
			window.onunload=null;
			document.genericForm.submit();
		}
		function createNewTrade () {
			document.getElementById("createTradeSecret").value = 'true';
			createNew();
		}

		function submitSearchForm() {
			 document.getElementById("returnResultLink").innerHTML ='';
			 var now = new Date();
	         document.getElementById("startSearchTime").value = now.getTime();
	         document.getElementById("userAction").value = 'search';
	   	     document.genericForm.target='resultFrame';
	   	     showPleaseWait();
	         return true;
		}
		
		function generateExcel() {

			    document.getElementById("userAction").value = 'createExcel';
			    document.getElementById("uAction").value = 'createExcel';
				
				openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',
						'_Cas_Number_Excel', '650', '600', 'yes');
				document.genericForm.target = '_Cas_Number_Excel';
				var a = window.setTimeout("document.genericForm.submit();", 50);
			
        }	
        
        function setSearchMode() {
        	if($v("searchField") == 'preferred_name')
        		$("searchMode").value = 'like';
        	else
        		$("searchMode").value = 'is';
        }
        	
	// -->
	</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','casnumbersearch');getSearchString();" onunload="try{opener.stopShowingWait();}catch(ex){}" onresize="resizeFrames()">
	<div class="interface" id="mainPage" style="">
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/casnumbersearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
					<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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
											<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td width="10%" class="optionTitleBoldRight" valign="top">
														<fmt:message key="label.search"/>:
													</td>
													<td class="optionTitleBoldLeft">
														  <select name="searchField"  class="selectBox" id="searchField" onchange="setSearchMode();">
												               <option value="cas_number"><fmt:message key="label.casnumber"/></option>
												               <option value="ec_number"><fmt:message key="label.ecnumbersearch"/></option>
												               <option value="preferred_name"><fmt:message key="label.preferredname"/></option>
												          </select>
												          &nbsp;
												          <select name="searchMode" class="selectBox" id="searchMode" >
												               <option value="is" selected> <fmt:message key="label.is"/>  </option>
												               <option value="like"> <fmt:message key="label.contains"/>  </option>
												               <option value="startsWith"><fmt:message key="label.startswith"/> </option>
												               <option value="endsWith"><fmt:message key="label.endswith" /></option>
												          </select>
												          &nbsp;
												          <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="30"/>
														<!--  <input type="text" name="searchArgument" id="searchArgument" value="${param.SearchString}" size="40" class="inputBox" onkeypress="return onKeyPress()"/><BR> -->
														
														<c:if test="${param.fromListManagement != 'Yes'}">
														   <input type="checkbox" name="tradeSecret" id="tradeSecret"><fmt:message key="label.tradesecret"/>
														</c:if>
													</td>
												</tr>
												<tr>
													<td colspan="2">
													  <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
															 onclick="return submitSearchForm()">
													  <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" 
					                                    class="inputBtns" value="<fmt:message key="label.createexcel"/>"
					                                    onmouseover="this.className='inputBtns inputBtnsOver'"
					                                    onmouseout="this.className='inputBtns'"
					                                    onclick="generateExcel(); return false;">		 
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
					<div id="hiddenElements" style="display: none;">
						<input type="hidden" name="createTradeSecret" id="createTradeSecret" value=""/>
						<input type="hidden" name="userAction" id="userAction" value="search"/>
						<input type="hidden" name="uAction" id="uAction" value="search"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="fromListManagement" id="fromListManagement" type="hidden" value="${param.fromListManagement}"/>
						<input name="noNew" id="noNew" type="hidden" value="${param.noNew}"/>
						<input name="loginNeeded" id="loginNeeded" value="${param.loginNeeded}" type="hidden"/>
					</div>
				</tcmis:form>
			</div> 
		</div>
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br><br><br><fmt:message key="label.pleasewait"/>
				<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>

			<div id="resultGridDiv" style="display: none;">
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
											Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.--%>
											<span id="updateResultLink"><a href="#" onclick="createNew(); return false;"><fmt:message key="label.newcasnumber"/></a>&nbsp;|&nbsp;
												<a href="#" onclick="createNewTrade(); return false;"><fmt:message key="label.new"/> <fmt:message key="label.tradesecret"/></a>&nbsp;|&nbsp;
											</span>
											<span id="mainUpdateLinks" style=""> 
												<span id="returnResultLink"></span>
											</span> <%-- mainUpdateLinks Ends --%>
										</div>
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>
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
</div>
<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>