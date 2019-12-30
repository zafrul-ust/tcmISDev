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
<fmt:message key="label.labelinfo"/>
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
<table id="searchMaskTable" witdh="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" witdh="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table witdh="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <!-- Insert all the search options here -->

<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.requestor"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		Henning, Jesse
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.phone"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		952-402-2484
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.facility"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		TCO-Shakopee
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.workarea"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		SHK/HEAD DISC ANALYSI
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.haas"/> <fmt:message key="label.requestid"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		60392
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.seagate"/> <fmt:message key="label.requestid"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td BGCOLOR="#000066" COLSPAN="2">&nbsp;</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.part"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	1
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.manufacturer"/>:
	</td>
		<td witdh="85%" class="optionTitleLeft">
		Sigma Aldrich Chemical Corp (including Fluka and Riedel)
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.description"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		Diatomaceous Earth Acid-Washed ≥95%, Not Further Calcined (D3877)
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.tradename"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		Diatomaceous Earth Acid-Washed, Not Further Calcined (D3877)
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.msdsnumber"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.mfg.part.num"/>:
	</td>
	<td witdh="85%" CLASS="Inbluel">
		D3877-5KG
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.grade"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.packaging"/>:
	</td>
	<td witdh="85%" CLASS="Inbluel">
		5 kg in a 5 gal plastic pail
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.shelflibe"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
  		from date of 
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.storagetemp"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td BGCOLOR="#000066" colspan="2">&nbsp;
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.suppliername"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		Sigma-aldrich
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.contactname"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.phone"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		800-325-3010
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.fax"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.email"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.supplier"/> <fmt:message key="label.partNno"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.csm"/> <fmt:message key="label.partNno"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		<input type="text" name="csmPartNo" id="csmPartNo" value="" size="25" readonly="readonly">
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.haas"/> <fmt:message key="label.itemno"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		<input type="hidden" name="haasItemNo" id="haasItemNo" value="528277"/>
			528277
	</td>
</tr>
<tr>
	<th colspan="2">
		<fmt:message key="label.labelinfo"/>
	</th>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.health"/>:
	</td>
	<td witdh="85%">
		<select class="selectBox" name="health" id="health" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.flamability"/>:
	</td>
	<td witdh="85%" class="optionTitleLeft">
		<select class="selectBox" name="flamability" id="flamability" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
</select>
</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.reactivity"/>:
	</td>
	<td witdh="85%">
		<select class="selectBox" name="reactivity" id="reactivity" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.chemical"/> <fmt:message key="label.storage"/>:
	</td>
	<td witdh="85%" CLASS="Inbluel">
		<select class="selectBox" name="chemicalStorage" id="chemicalStorage" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.signalWord"/>:
	</td>
	<td witdh="85%">
		<select class="selectBox" name="signalWord" id="signalWord" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.personalProtectiveEquip"/>:
	</td>
	<td witdh="85%">
		<select class="selectBox" name="perprotectiveEquip" id="perprotectiveEquip" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.hazard"/>:
		<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" name='hazardValues' value="getHazardValues">
			<fmt:message key="label.getHazardValues"/>
		</input>
	</td>
	<td witdh="85%">
		<select class="selectBox" name="hazard" id="hazard" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.disposalCode"/>:
	</td>
	<td witdh="85%">
		<select class="selectBox" name="disposableCode" id="disposableCode" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td witdh="15%" class="optionTitleBoldLeft">
		<fmt:message key="label.disposalContainerCode"/>:
	</td>
	<td witdh="85%">
		<select class="selectBox" name="disposableContainerCode" id="disposableContainerCode" size="1">
			<option value="">
				<fmt:message key="label.pleaseselect"/>
			</option>
			<!-- query value -->
		</select>
	</td>
</tr>
<tr>
	<td>
	<input type="submit" value="Submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" name="UpdateCat">
		<fmt:message key="label.updateCat"/>
	</input>
	</td>
</tr>
</table>
    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" witdh="15" height="15" class="corner" style="display: none" /> </div>
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