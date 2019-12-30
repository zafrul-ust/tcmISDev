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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/pagename.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.genericbol"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<%--<tcmis:form action="/pagename.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">--%>

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->

		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<tr>
				<td width="5%" class="optionTitleBoldRight">
					<fmt:message key="label.hub" />:
				</td>
				<td width="20%" class="optionTitleLeft">
					<select name="hub" size="1" class="selectBox">
					<!--  query value here -->
					</select>
				</td>
				<td width="30%" class="optionTitleLeft">
					<input type="checkbox" name="showInventory" id="showInventory" value="Yes" class="radioBtns" />
					<fmt:message key="label.show" /> <fmt:message key="label.inventory"/>
				<td width="5%"/>
			</tr>
		</table>

		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<tr>
				<td width="5%" class="optionTitleBoldRight">
					<fmt:message key="label.from" />:
				</td>
				<td width="5%" class="optionTitleLeft">
					<input type="text" class="inputBox" name="fromLocationDesc" id="fromLocationDesc" value="" size="25">
					<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchShipTo" value="..."/>
				</td>
				<td width="5%">
					<fmt:message key="label.shipper" /> <fmt:message	key="label.id" />
				<td width="5%">
					<input type="text" class="inputBox" name="shipperId" id="shipperId" value="" size="15">
				</td>
			</tr>
			<tr>
				<td width="5%"/>
				<td width="5%">
					<input type="text" class="inputBox"	name="fromAddr1" id="fromAddr1" value="" size="25"/>
				</td>
				<td width="5%">
					<fmt:message key="label.carrier" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox" name="carrier" id="carrier" value="" size="15">
				</td>
			</tr>
			<tr>
				<td width="5%" />
				<td width="5%">
					<input type="text" class="inputBox"	name="fromAddr2" id="fromAddr2" value="" size="25" />
				</td>
				<td width="5%">
					<fmt:message key="label.carrierno" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox" name="carrierNo" id="carrierNo" value="" size="15" />
				</td>
			</tr>
			<tr>
				<td width="5%" class="optionTitleRight">
					<fmt:message key="label.city" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox"	name="fromCity" id="fromCity" value="" size="15">
				</td>
				<td width="5%">
					<fmt:message key="label.state" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox"	name="fromState" id="fromState" value="" size="3" />
				</td>
			<tr>
				<td width="5%" class="optionTitleRight">
					<fmt:message key="label.zip" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox"	name="fromZip" id="fromZip" value="" size="7" />
				</td>
			</tr>
			<tr>
				<td width="5%" class="optionTitleBoldRight">
					<fmt:message key="label.shipto" />
				</td>
				<td width="5%" class="optioTitleLeft">
					<input type="text" class="inputBox" name="toLocationDesc" id="toLocationDesc" value="" size="25" />
					<button class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchShipTo" value="..."/>
					</button>
				</td>
				<td width="5%">
					<fmt:message key="label.ponumber" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox"	name="poNumber" id="poNumber" value="" size="15" />
						<br/>
					<fmt:message key="label.poref" />&nbsp;
					<input type="text" class="inputBox" name="poNumber" id="poNumber" value="" size="15" />
				</td>
			</tr>
			<tr>
				<td width="5%"/>
				<td width="5%">
					<input type="text" class="inputBox"	name="toAddr11" id="toAddr11" value="" size="25" />
				</td>
				<td width="5%">
					<fmt:message key="label.reqdeldate" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox"	name="requiredDate" id="requiredDate" value="" size="15" />
				</td>
			</tr>
			<tr>
				<td width="5%"/>
				<td width="5%">
					<input type="text" class="inputBox" name="toAddr2" id="toAddr2" value="" size="25" />
				</td>
				<td width="5%">
					<fmt:message key="label.shipdate" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox"	name="shipDate" id="shipDate" value="" size="15" />
				</td>
			</tr>
			<tr>
				<td width="5%" class="optionTitleRight">
					<fmt:message key="label.city" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox" name="toCity" id="toCity" value="" size="15" />
				</td>
				<td width="5%">
					<fmt:message key="label.state" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox"	name="toState" id="toState" value="" size="3" />
				</td>
			</tr>
			<tr>
				<td width="5%" class="optionTitleRight">
					<fmt:message key="label.zip" />
				</td>
				<td width="5%">
					<input type="text" class="inputBox" name="toZip" id="toZip" value="" size="7" />
				</td>
				<td width="5%"/>
				<td width="5%"/>
			</tr>
			<tr>
				<td>
					<input type="submit" value="<fmt:message key="label.generateBOL"/>" name="generate" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
				</td>
				<td colspan="3"/>
			</tr>
		</table>
		<!-- Search Option Table end -->
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>