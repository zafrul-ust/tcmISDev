<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
	<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

	
	<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>
	<%@ include file="/common/opshubig.jsp" %>						<%-- retrieves data for OpsEntity/hub/IG pulldowns --%>
	
	<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
	
	<script type="text/javascript" src="/js/common/opshubig.js"></script>			<%-- sets up data for OpsEntity/hub/IG pulldowns --%>
			
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>

	<!-- This handels the menu style and what happens to the right click on the whole page -->
	<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
	<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
	<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
	<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
	<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/clientinventory/inventorymain.js"></script>



<title>
<fmt:message key="label.inventory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<%@ include file="/common/clientnavigation.jsp" %>
</div>

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them
 -->
 <iframe id="searchFrame" name="searchFrame" width="100%" height="140" frameborder="0" marginwidth="0" style="" src="inventorysearch.do"></iframe>
</div>
<!-- Search Frame Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<%-- div for inline popup "Please Wait" window --%>
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br/>
					<br/>
					<br/>
					<fmt:message key="label.pleasewait"/>
				  	<br/>
				  	<br/>
				  	<br/>
				  	<img src="/images/rel_interstitial_loading.gif" align="middle"/>
				</div>
			
				<div id="resultGridDiv" style="display: none;">
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
												     Please keep the <span></span> on the same line this will avoid extra virtical space.
												 --%>     
												<div id="mainUpdateLinks" style="display: none"> 
					 							</div>
											</div>
											<div class="dataContent">
					  							<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="100%" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
											</div>					
											<div id="footer" class="messageBar"></div> <%-- result count and time --%>
					
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
			</div><!-- Result Frame Ends -->

<!-- Can not have footer message because of resizing issues with iframe -->
<%--
<!-- Footer message start -->
<div id="footer" class="messageBar">545</div>
<!-- Footer message end -->
--%>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<%-- needed if this page will show on application.do --$>
<%-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  --%>
<input name="searchHeight" id="searchHeight" type="hidden" value="133"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

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