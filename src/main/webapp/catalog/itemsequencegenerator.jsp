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
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/catalog/itemsequencegenerator.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
    <fmt:message key="receiptdocumentviewer.title"/> 
</title>
<script language="JavaScript" type="text/javascript">

var messagesData = [];

</script>
</head>
<body onresize="resizeResults();">
	<div class="interface" id="mainPage" style="">
			<!-- Search Frame Begins -->
			<div id="searchFrameDiv">
				<!-- open contentArea -->
				<div class="contentArea">
					<tcmis:form action="/itemsequencegenerator.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
												<table>
													<tr>
														<td><label for="howMany" class="optionTitleBold">Generate:</label></td>
														<td><input id="howMany" name="howMany" type="text"></input></td>
														<td><label for="searchLike" class="optionTitleBold"></label></td>
														<td>
															<select id="searchLike" name="searchLike">
																<option value="material_id">Material IDs</option>
																<option value="item_id">Item IDs</option>
																<option value="trade_secret">CAS Number/Trade Secret</option>
																<option value="manufacturer">Manufacturer</option>
															</select>
														</td>
														<td></td>
														<td><input id="generate" name="generate" type="button" value="Generate" onclick="generateSequence()"></input></td>
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

						<%-- Error Messages --%>
						<!-- Hidden element start -->
						<div id="hiddenElements" style="display: none;">
							<input name="uAction" id="uAction" type="hidden" value="generate"/>
						</div>
						<!-- Hidden elements end -->

					</tcmis:form>
				</div>
				<!-- close of contentArea -->
			</div>
			<!-- Search Frame Ends -->

			<div id="resultGridDiv" style="display:none;"> 
				<div class="roundcont contentContainer">
					<div class="roundright">
						<div class="roundtop">
							<div class="roundtopright">
								<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
						<div class="roundContent">
							<div id="boxhead" style="border-bottom: 1px solid gray; padding-bottom:5px;color: #666666" class="optionTitleBold"></div>
							<div id="dataContent" style="overflow-y:scroll;"></div>
						</div>
						<div class="roundbottom">
							<div class="roundbottomright">
								<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
							</div>
						</div>
					</div>
				</div>
			<!-- Search results end -->
			</div>

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
</html>