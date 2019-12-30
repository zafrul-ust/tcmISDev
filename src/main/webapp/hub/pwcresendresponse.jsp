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
<script type="text/javascript" src="/js/hub/pwcresendresponse.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>  
  
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->

<title>
<fmt:message key="pwcresendresponse.title"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
ok:"<fmt:message key="label.ok"/>",
needdate:"<fmt:message key="label.needdate"/>",
mrnumber:"<fmt:message key="label.mrnumber"/>",
customerpo:"<fmt:message key="label.customerpo"/>",
customerpoline:"<fmt:message key="label.mrline"/>",
facpartnumber:"<fmt:message key="label.partnumber"/>",
status:"<fmt:message key="label.errorss"/>",
detail:"<fmt:message key="label.detail"/>",
itemid:"<fmt:message key="label.itemid"/>",
itemdescription:"<fmt:message key="label.itemdescription"/>",
errors:"<fmt:message key="label.errors"/>",  
orderqty:"<fmt:message key="label.orderqty"/>",
orderprice:"<fmt:message key="label.orderprice"/>",
catalogprice:"<fmt:message key="label.catalogprice"/>",
allocatedquantity:"<fmt:message key="label.allocatedqty"/>",
allocationype:"<fmt:message key="label.allocationtype"/>",
allocation:"<fmt:message key="label.allocation"/>",
mr:"<fmt:message key="label.mr"/>",
ponumber:"<fmt:message key="label.ponumber"/>",
required:"<fmt:message key="errors.required"/>",
showlegend:"<fmt:message key="label.showlegend"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="padding:5px;">

<!--  Search Frame Begins -->
<div id="searchFrameDiv">
	<div class="contentArea">
	<tcmis:form action="/pwcresendresponseresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
		<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
	
			<div class="roundcont filterContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display:none"/>
						</div>
					</div>
					<div class="roundContent">
						<div class="dataContent">
							<table>
							<tr>
							<td style="text-align:right">
							<label for="lookupAction" class="optionTitleBold"><fmt:message key="label.action"/>: </label>
							</td>
							<td>
							<select id="lookupAction" name="lookupAction" class="selectBox" onchange="changeSearchAction()">
								<option value="poLookup"><fmt:message key="label.polookup"/></option>
								<option value="orderLookup"><fmt:message key="label.orderlookup"/></option>
								<%--<option value="allocated"><fmt:message key="label.allocated"/></option>
								<option value="pickedNotShipped"><fmt:message key="label.pickedforshipment"/></option>
								<option value="shipped"><fmt:message key="label.shipped"/></option>
								<option value="billed"><fmt:message key="label.billed"/></option>--%>
								<option value="notifyError"><fmt:message key="pwcresendresponse.title"/></option>
							</select>
							</td>
							</tr>
							<tr id="orderSearchDiv" style="display:none">
							<td style="text-align:right">
								<label id="orderNumberSearchLabel" for="orderNumberSearch" class="optionTitleBold"><fmt:message key="label.customerpo"/>: </label>
							</td>
							<td>
								<input class="inputBox" type="text" id="orderNumberSearch" name="orderNumberSearch"/>
							</td>
							</tr>
							<tr id="poSearchDiv">
							<td style="text-align:right">
								<label for="poNumberSearch" class="optionTitleBold"><fmt:message key="label.ponumber"/>: </label>
							</td>
							<td>
								<input class="inputBox" type="text" id="poNumberSearch" name="poNumberSearch"/>
							</td>
								<%--<td><button name="searchpolike" class="submit" id="searchpolike" onmouseover="className='submithover'" onmouseout="className='submit'" onclick="reSearchPoNumber()" type="button" value="..."><img alt="PO" src="/images/search_small.gif"></button></td>--%>
							</tr>
							<tr>
							<td colspan=2>
							<input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" 
	        						onclick='return submitSearchForm();'/>
	        				</td>
	        				</tr>
	        				</table>
	        			</div>
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display:none"/>
						</div>
					</div>
				</div>
			</div>
			</td>
			</tr>
			</table>
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
			
			<!-- Hidden element start -->
			<div id="hiddenElements" style="display: none;">
				<input name="startSearchTime" id="startSearchTime" type="hidden" value=""/>
				<input name="action" id="action" value="" type="hidden"/>
				<input name="lookupClass" id="lookupClass" value="" type="hidden"/>
				<input name="customerPo" id="customerPo" value="" type="hidden"/>
				<input name="customerPoLineNumber" id="customerPoLineNumber" value="" type="hidden"/>
				<input name="prNumber" id="prNumber" value="" type="hidden"/>
				<input name="prLineNumber" id="prLineNumber" value="" type="hidden"/>
			</div>
	</tcmis:form>
	</div>
</div>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table width="100%" id="resultsMaskTable"  border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
   
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
   
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>      
       <span id="resendFullOrderAckLink" style="display:none"><a href="#" onclick="call('update');"><fmt:message key="label.resendfullorderack"/></a> | </span>
       <span id="resetAndSendResponseLink" style="display:none"><a href="#" onclick="call('resetSendResponse');"><fmt:message key="label.resetandsendresponse"/></a> | </span>
       <span id="showlegendLink" style="display:none"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

	<div class="dataContent">
		<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
	</div>

	<%-- result count and time --%>
	<div id="footer" class="messageBar"></div>

</div>
<div class="roundbottom">
	<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
</div>
</div>
</div>
</td></tr>
</table>
</div>
</div><!-- Result Frame Ends -->

</div> <!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
	<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100px" class="red legendText">&nbsp;</td>
			<td class="legendText"><fmt:message key="label.resendfullorderack"/></td>
		</tr>
	</table>
</div>
<%-- show legend Ends --%>

</body>
</html:html>