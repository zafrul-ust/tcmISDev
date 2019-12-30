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

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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
<script type="text/javascript" src="/js/client/het/faclocappsearchmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
   <fmt:message key="label.cartitemsearch"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		all:"<fmt:message key="label.all"/>",
		showlegend:"<fmt:message key="label.showlegend"/>",
		errors:"<fmt:message key="label.errors"/>",  
		validvalues:"<fmt:message key="label.validvalues"/>",
		itemid:"<fmt:message key="label.itemid"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		pleaseselect:"<fmt:message key="label.pleaseselect"/>",
		all:"<fmt:message key="label.all"/>"
	};
// -->
</script> 	

</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','cartItemSearch');" onresize="resizeFrames()" onunload="opener.parent.closeTransitWin();">
	<div class="interface" id="mainPage" style="">
		<div id="searchFrameDiv">
			<div class="contentArea">
				<tcmis:form action="/cartitemsearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
											<table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td  class="optionTitleBoldRight">
														<fmt:message key="label.workarea"/>:
													</td>
													<td  class="optionTitleBoldLeft">
														${param.workAreaName}
													</td>
												</tr>
												<tr>
													<td  class="optionTitleBoldRight">
														<fmt:message key="label.search"/>:
													</td>
													<td class="optionTitleLeft">
														<select name="searchField" class="selectBox" id="searchField">
															<option value="MSDS_NUMBER"><fmt:message key="label.msdsnumber" /></option>
															<option value="CAT_PART_NO"><fmt:message key="label.partno" /></option>
															<option value="MATERIAL_DESC"><fmt:message key="label.materialdesc" /></option>
															<option value="ITEM_ID"><fmt:message key="label.itemid" /></option>
														</select>
														<select name="searchMode" class="selectBox" id="searchMode">
															<option value="is"><fmt:message key="label.is" /></option>
															<option value="contains"><fmt:message key="label.contains" /></option>
															<option value="startswith"><fmt:message key="label.startswith" /></option>
														</select>
														<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='<c:out value="${param.searchArgument}"/>' size="25" />
													</td>
												</tr>
												<tr>
													<td colspan="2" width="100%" class="optionTitleBoldLeft">
          													<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"/>
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
						<input type="hidden" name="uAction" id="uAction" value="search"/>
						<input type="hidden" name="facilityId" id="uAction" value="${param.facilityId}"/>
						<input type="hidden" name="workArea" id="uAction" value="${param.workArea}"/>
						<input type="hidden" name="workAreaName" id="uAction" value="${param.workAreaName}"/>
						<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
						<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/>
						<input name="searchHeight" id="searchHeight" type="hidden" value="150">
					</div>
				</tcmis:form>
			</div>
		</div>
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
											<div id="mainUpdateLinks" style="display: none"> 
												<a href="#" id ="selectedRow" onclick="resultFrame.returnItem();"></a>
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
	</div> <!-- close of interface -->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
	
	</div>

</body>
</html>
      		
    		
