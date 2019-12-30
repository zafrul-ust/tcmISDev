<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" 		language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" 		prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" 		prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" 			prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" 				prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" 				prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" 				prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" 					prefix="tcmis"%>

<html:html lang="true">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="expires" content="-1">
		<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	
		<%@ include file="/common/locale.jsp"%> 
		<%@ include file="/common/opshubig.jsp" %>
		
		<!-- Use this tag to get the correct CSS class.
		This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
		<tcmis:gridFontSizeCss /> 
		<%-- Add any other stylesheets you need for the page here --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script> 
		<script type="text/javascript" src="/js/common/commonutil.js"></script> 
		<!-- This handles all the resizing of the page and frames -->
		<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script> 
		<!-- This handles which key press events are disabeled on this page -->
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>
	
		<!-- This handles the menu style and what happens to the right click on the whole page -->
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script> 
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script> 
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script> 
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script> 
		<%@ include file="/common/rightclickmenudata.jsp"%> 
		
		<!-- For Calendar support
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	 	-->
		
		<!-- Add any other javascript you need for the page here --> 
		<script type="text/javascript" src="/js/supply/allocationsmain.js"></script> 

		<script type="text/javascript" src="/js/common/standardGridmain.js"></script> 
		<script type="text/javascript" src="/js/common/opshubig.js"></script>

		<!-- These are for the Grid-->
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
		<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	
		<title><fmt:message key="projectdocument.label.customerDisplay" /></title>
	
		<script language="JavaScript" type="text/javascript">
			<!--
			//add all the javascript messages here, this for internationalization of client side javascript messages
			var messagesData = new Array();
			messagesData = {alert:"<fmt:message key="label.alert"/>",
			all:"<fmt:message key="label.all"/>",
			errors:"<fmt:message key="label.errors"/>",      
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			itemInteger:"<fmt:message key="error.item.integer"/>",
			searchInput:"<fmt:message key="error.searchInput.integer"/>"};
			// -->
		</script>
	</head>
	
	<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout.
	It is important to send the pageId if this page is going to open in a tab in the application.
	You can also call any functions you need to do your search initialization for drop downs.
	No need to pass page name as it is not used.
	-->
	<body bgcolor="#ffffff" onload="loadLayoutWin('','allocations');setOps();" onresize="resizeFrames()">
		<div class="interface" id="mainPage" style="">
			<!-- Search div Begins -->
			<div id="searchFrameDiv">
				<!-- start of contentArea -->
				<div class="contentArea">
					<tcmis:form action="/allocationsresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
						<!-- Search Option Begins -->
						<%--Change the width to what you want your page to span.--%>
						<table id="searchMaskTable" width="700" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<div class="roundcont filterContainer">
										<div class="roundright">
											<div class="roundtop">
												<div class="roundtopright">
													<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
												</div> <!-- roundtopright end -->
											</div> <!-- roundtop end -->
											<div class="roundContent">
												<!-- Insert all the search option within this div -->
												<table width="100%" id="tableSearch" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
													<tr>
												      	<td nowrap width="6%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
												      	<td width="6%" class="optionTitleBoldLeft">
												         	<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
												      	</td>
														<td width="6%" class="optionTitleBoldRight"><fmt:message key="label.search" />:</td>
														<td width="10%" class="optionTitleLeft">
															<html:select property="searchField" styleId="searchField" styleClass="selectBox">
																<html:option value="prNumber"><fmt:message key="label.mr" /></html:option>
																<html:option value="PO"><fmt:message key="label.po" /></html:option>
																<html:option value="Receipt"><fmt:message key="label.receipt" /></html:option>
																<html:option value="BuyOrder"><fmt:message key="label.buyorder" /></html:option>
																<html:option value="itemId"><fmt:message key="label.itemid" /></html:option>
															</html:select>
														</td>
														<td width="2%" class="optionTitleLeft">
															<html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
																<html:option value="is"><fmt:message key="label.is" /></html:option>
																<!--
																<html:option value="contains"><fmt:message key="label.contains"/></html:option>
																<html:option value="startsWith" key="label.startswith"/>
																<html:option value="endsWith" key="label.endswith"/>
																-->
															</html:select>
														</td>
														<td width="15%" class="optionTitleBoldRight">
															<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="30" onkeypress="return onKeyPress()"/>
														</td>
												    </tr>
												    <tr>  	
														<td width="6%" class="optionTitleBoldRight"><fmt:message key="label.hub" />:</td>
														<td width="6%">
															<select name="hub" id="hub" onchange="hubChanged()" class="selectBox">
															</select>
														</td>
														<td colspan="4">&nbsp;</td>
													</tr>
													<tr>
														<td width="6%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup" />:&nbsp;</td>
														<td width="6%">
															       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
                                                                   </select>
														</td>
														<td colspan="4">&nbsp;</td>														
													</tr>
													<tr>
														<%--The colspan will depend on the page--%>
														<td colspan="6">
															<html:submit property="search" styleId="submitSearch" styleClass="inputBtns" 
															onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
															onclick="return submitSearchForm();"> <fmt:message key="label.search" /></html:submit>
															<html:button property="buttonCreateExcel" styleId="submitSearch" styleClass="inputBtns" 
															onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
															onclick="generateExcel(); return false;"> <fmt:message key="label.createexcelfile" /></html:button>
														</td>
													</tr>
												</table>
												<!-- End search options -->
											</div> <!-- roundContent -->
											<div class="roundbottom">
												<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
											</div> <!-- roundbottom end -->
										</div> <!-- roundright end -->
									</div> <!-- roundcont filterContainer -->
								</td>
							</tr>
						</table> <!-- searchMaskTable -->
						<!-- Search Option Ends -->
					
						<!-- Error Messages Begins -->
						<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
							<div class="spacerY">&nbsp;
								<div id="searchErrorMessagesArea" class="errorMessages"><html:errors />
								</div>
							</div>
						</c:if> <!-- Error Messages Ends -->
					
						<div id="hiddenElements" style="display: none;">
							<input name="uAction" id="uAction" type="hidden" value=""/> 
							<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
							<input name="searchHeight" id="searchHeight" type="hidden" value="200"/>
						</div> <!-- Hidden elements end -->
					</tcmis:form>
				</div>
				<!-- close of contentArea -->
			</div> <!-- searchFrameDiv end --> 
			<!-- Result Frame Begins -->
			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<!-- Transit Page Begins -->
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br><br><br><fmt:message key="label.pleasewait"/> <br><br></br><img src="/images/rel_interstitial_loading.gif" align="middle"/>
				</div>
				<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- results start -->
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
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <%--<span id="showlegendLink"><a href="javascript:showreceivingpagelegend()"><fmt:message key="label.showlegend"/></a></span>
      <span id="updateResultLink" style="display: none">| <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a></span>
      --%></div> <%-- mainUpdateLinks Ends --%>      
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td>
</tr>
</table>
<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

</div> <!-- close of interface -->

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>