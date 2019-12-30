<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<meta http-equiv="Pragma" CONTENT="no-cache">
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


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="invoice.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function printInvoice()
{
   if ($("invoicePeriod").value.trim().length > 0)
 {
   var loc = "/tcmIS/invoice/haasinvoice.do?action=printInvoice&invoicePeriod="+$("invoicePeriod").value+"&client="+$("client").value;
     alert(loc);
   openWinGeneric(loc,"invoicePeriod444","800","600","yes","80","80");
 }
}
// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="/haasinvoice.do">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
  <tr>
    <td width="30%" class="optionTitleBoldRight">
		<fmt:message key="label.invoicegroup"/>:
    </td>
    <td width="70%" class="optionTitleLeft">
          <html:select property="client" styleId="client" styleClass="selectBox">
            <html:option value="ARVIN_MERITOR">Arvin Meritor</html:option>
            <html:option value="BAE">BAE</html:option>
            <html:option value="BOEING_OFFSITE">Boeing Offsite</html:option>
            <html:option value="CAL">United Airlines</html:option>
            <html:option value="CATERPILLAR">Caterpillar</html:option>
            <html:option value="DANA-CG">Dana-CG</html:option>
            <html:option value="DANA-CGPS">Dana-CGPS</html:option>
            <html:option value="DANA-FW">DANA-FW</html:option>
            <html:option value="DANA-LIMA">Dana-LIMA</html:option>
            <html:option value="DANA-MAN">Dana-MAN</html:option>
            <html:option value="DANA-MARION">Dana-MARION</html:option>
            <html:option value="DANA-POTTS">Dana-POTTS</html:option>
            <html:option value="DANA-SJ">Dana-SJ</html:option>
            <html:option value="DANA-SYR">Dana-SYR</html:option>
            <html:option value="DCX">DCX - FENTON</html:option>
            <html:option value="DETROITDIESEL">Detroit Diesel</html:option>
            <html:option value="DRS">DRS</html:option>
            <html:option value="FEC">FEC</html:option>
            <html:option value="FEC_ADD_CHARGE">FEC (Add Charge)</html:option>
            <html:option value="GEMA">GEMA</html:option>
            <html:option value="GD">General Dynamics</html:option>
            <html:option value="GKN_MAT">GKN MAT</html:option>
            <html:option value="GKN_SF">GKN SF</html:option>
            <html:option value="BEDFORD">GM-Bedford</html:option>
		    <html:option value="FLINT">GM-Flint SF</html:option>
            <html:option value="FLINT_MATERIAL">GM-Flint Material</html:option>
            <html:option value="MORAINE">GM-Moraine</html:option>
            <html:option value="WILMINGTON">GM-Wilmington</html:option>
            <html:option value="GOLETAEW">Goleta EW</html:option>
            <html:option value="L3">L3</html:option>
            <html:option value="KELLY">Lockheed/Kelly AC (San Antonio)</html:option>
            <html:option value="SYRACUSE">Lockheed/Syracuse</html:option>
            <html:option value="MILLER">Miller Brewing</html:option>
            <html:option value="NALCO">Nalco</html:option>
            <html:option value="NRG">NRG</html:option>
		    <html:option value="POLCHEM">POLCHEM</html:option>
            <html:option value="PRISM">Prism</html:option>
            <html:option value="PGE">PGE</html:option>
            <html:option value="QOS">Quality On Site</html:option>
            <html:option value="RAC">Raytheon RAC</html:option>
            <html:option value="EXOSTAR">Raytheon-Exostar</html:option>
            <html:option value="SAIC">SAIC</html:option>
            <html:option value="SAUER_DANFOSS">Sauer-Danfoss</html:option>
            <html:option value="SLAC-SF">SLAC-SF</html:option>
            <html:option value="STARK">STARK</html:option>
            <html:option value="SWA">Southwest Airlines</html:option>
            <html:option value="TIMKEN">Timken</html:option>
            <html:option value="UTC_HSD">UTC - HSD (New England)</html:option>
            <html:option value="UTC_PWA">UTC - PWA (New England)</html:option>
            <html:option value="UTC_PWA_PAPER">UTC - PWA PAPER</html:option>
            <html:option value="UTC_SAC">UTC - Sikorsky</html:option>
            <html:option value="SCHWEIZER">UTC - Schweizer</html:option>
            <html:option value="ALBION">Verasun - Albion</html:option>
            <html:option value="AURORA">Verasun - Aurora</html:option>
            <html:option value="BLOOMINGBURG">Verasun - Bloomingburg</html:option>
            <html:option value="CHARLES-CITY">Verasun - Charles City</html:option>
            <html:option value="FORT-DODGE">Verasun - Fort Dodge</html:option>
            <html:option value="HARTLEY">Verasun - Hartley</html:option>
            <html:option value="LINDEN">Verasun - Linden</html:option>
            <html:option value="WELCOME">Verasun - Welcome</html:option>
            <html:option value="VOLVO">Volvo NRV</html:option>
          </html:select>
    </td>
  </tr>
  <tr>
    <td  width="30%" class="optionTitleBoldRight">
		<fmt:message key="invoice.label.invoiceperiod"/>:
    </td>
    <td  width="70%" class="optionTitleLeft">
          <html:text property="invoicePeriod" styleId="invoicePeriod" styleClass="inputBox"/>
    </td>
  </tr>
  <%--<tr>
    <td  width="30%" class="optionTitleBoldRight">
		<fmt:message key="label.invoice"/>:
    </td>
    <td  width="70%" class="optionTitleLeft">
          <html:text property="invoiceNumber" styleId="invoiceNumber" styleClass="inputBox"/>
    </td>
  </tr>--%>
  <tr>
    <td colspan="2" class="optionTitleLeft">
       <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="invoice.button.submit"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "return printInvoice()">                     
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
