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


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/opencustomer/ordermsds.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
<fmt:message key="label.msds"/>
</title>
<script language="JavaScript" type="text/javascript"> 
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData= { 
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			required:"<fmt:message key="label.searchargumentrequired"/>"
			};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','msdsSearch');" onresize="resizeFrames()">
	<div class="interface" id="mainPage" style=""><!-- Search Frame Begins -->
		<div id="searchFrameDiv"><%--NEW - removed the search frame and copied the search section here--%>
			<div class="contentArea"><tcmis:form action="/ordermsdsresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
				<!-- Search Option Begins -->
				<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
						<div class="roundcont filterContainer">
						<div class="roundright">
						<div class="roundtop">
						<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
						</div>
						<div class="roundContent">
						<table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
							<tr>
								<td width="30%" class="optionTitleBoldRight">
									<fmt:message key="label.customer" />:
								</td>
								<td width="70%">
									<select name="customerId" id="customerId" class="selectBox">
								     	    	<c:forEach var="customer" items="${userCustomerCollection}" varStatus="status">
								     	    		<option value="${customer.customerId}"><tcmis:jsReplace value="${customer.customerName}" processMultiLines="true"/></option>
								     	    	</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.searchtext" />:&nbsp;
								</td>
								<td class="optionTitleLeft">
									<input class="inputBox" name="searchArgument" id="searchArgument"  size="40" />									
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.lastorderedwithin" />:&nbsp;
								</td>
								<td class="optionTitleLeft">
			 						<select name="monthsBack" class="selectBox" id="monthsBack">
										<option value="1"><fmt:message key="label.onemonth" /></option>
										<option value="3"><fmt:message key="label.threemonths" /></option>
										<option value="6"><fmt:message key="label.sixmonths" /></option>
										<option value="12"><fmt:message key="label.oneyear" /></option>
										<option value=""><fmt:message key="label.indefinite" /></option>
									</select>

								</td>
							</tr>
							<tr class="alignLeft">
								<td  class="optionTitleLeft" colspan="2">
									<input name="SearchButton" id="SearchButton" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/> 
								</td>
							</tr>
						</table>
						</div>
						<div class="roundbottom">
						<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
						</div>
						</div>
						</div>
						</td>
					</tr>
				</table>
				<!-- Search Option Ends -->
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="uAction" id="uAction" value="" /> 
					<input name="startSearchTime" id="startSearchTime" type="hidden" value="" /> 
					<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/> 
					<input name="searchHeight" id="searchHeight" type="hidden" value="150"></div>
				<!-- Hidden elements end -->
			
			</tcmis:form>
			</div>
			<!-- close of contentArea -->
		</div>
		<!-- Search Frame Ends -->
		
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
											<div id="mainUpdateLinks" style="display: none"> 
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
</html>
      		