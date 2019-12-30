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

<html lang="true">
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


<!-- For Calendar support 
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>



<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.invoice"/>
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
errors:"<fmt:message key="label.errors"/>"};



function submitInvoiceForm()
{
    if(document.getElementById("client").value.length == 0)
    {
       document.invoiceForm.target='_self';
       alert("Please enter client");
       return false;
    }
    if(document.getElementById("invoicePeriod").value.length == 0  && document.getElementById("invoiceNumber").value.length == 0)
    {
       document.invoiceForm.target='_self';
       alert("Please enter InvoicePeriod or InvoiceNumber");
       return false;
     }
     else
    {
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Invoice','650','600','yes');
	    document.invoiceForm.target='_Invoice';
	    var a = window.setTimeout("document.invoiceForm.submit();",500);
		return true;
	}
}

function submitClientForm()
{

     document.getElementById("action").value = 'GetClient';
     var invoiceNumber= document.getElementById("invoiceNumber").value;
	if(document.getElementById("invoiceNumber").value.length == 0)
	{
	    
		 alert("Please enter InvoiceNumber");
	     return false;
	}
	else
	{
	   	//var a = window.setTimeout("document.invoiceForm.submit();",500);
		//return true;
		document.invoiceForm.target='_self';
		callToServer('invoice.do?uAction=GetClient&invoiceNumber='+invoiceNumber);
	}
}

function setClient()
{
 
 var client = document.getElementById("client");
  
 for(var i=0; i< client.options.length; i++) 
 {
     var str = client.options[i].value;
     
     if(str.indexOf('|') > 0)
     {
		 if (str.substr(0,str.indexOf('|')) == '${client}')
		   { 
		      client.selectedIndex = i;
		            break;   
		   } 
	 }
	 else
	   {
	      if(str == '${client}')
	        {
	           client.selectedIndex = i;
		            break; 
		     }
		     
	   }
	   
	   
  }
 
 document.getElementById("invoiceNumber").value = '${invoiceNumber}';
 document.getElementById("invoicePeriod").value = '${invoicePeriod}';
 
}

function clear()
{
 document.getElementById("client").options[0].value = '';
 document.getElementById("invoiceNumber").value = '';
 document.getElementById("invoicePeriod").value = '';

}


// -->
</script>
</head>

<body bgcolor="#ffffff"   onload="setClient();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/invoice.do">
<div class="contentArea">

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
<table width="400" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
 <tr>
    <td  class="optionTitleBoldRight">
		<fmt:message key="invoice.label.client"/>:
    </td>
    <td  class="optionTitleLeft">
         <select id="client" name="client" class="selectBox">
          <%--  <options collection="configuredInventoryGroupOptions" property="value" labelProperty="label" />
          </select>--%>
            <option value="">SELECT</option>
            <option value="AEROCZ|AEROCZInvoiceReportDefinition|">AEROCZ</option>
            <option value="AEROJET|AEROJET_File1|Group">AEROJET</option>
            <option value="AEROJET-SACRAM FEE|AEROJET_FEEInvoiceReportDefinition|">AEROJET Fee</option>
            <option value="AERO GS FEE|AERO_GS_FEEInvoiceReportDefinition|">AERO GS Fee</option>
            <option value="IRELAND_AIR_ATLANTA|AirAtlantaConsolidatedInvoiceReportDefinition|">AIR ATLANTA</option>
            <option value="ALENIA AERMACCHI|ALENIA_AERMACCHIInvoiceReportDefinition|">ALENIA AERMACCHI</option>
            <option value="ALGAT|ALGATReportDefinition|">ALGAT</option>
            <option value="ARCHBALD-FEE|ARCHBALD_FEEInvoiceReportDefinition|">ARCHBALD Fee</option>
            <option value="ARVIN_MERITOR">ARVIN MERITOR</option>
            <option value="ASSIREVA_SRL|ASSIREVA_SRLInvoiceReportDefinition|">ASSIREVA SRL</option>
            <option value="AVCORP|AVCORP_File1|Group">AVCORP</option>
            <option value="AVCORP FEE|AvcorpFeeInvoiceReportDefinition|">AVCORP Fee</option>
            <option value="BAE">BAE</option>
            <option value="BAE_AUSTIN|BAE_AUSTINInvoiceReportDefinition|">BAE AUSTIN</option>
            <option value="BAE_NASHUA|BAENashua_File1|Group">BAE NASHUA</option>
            <option value="BAE_NASHUA_FEE|BAE_NASHUA_FEEInvoiceReportDefinition|">BAE NASHUA Fee</option>
            <option value="BAE_SAMLESBURY|BAESamlesburyConsolidatedInvoiceReportDefinition|">BAE SAMLESBURY</option>
            <option value="BAE_SAMLESBURY_CMS|BAECMSSamlesburyConsolidatedInvoiceReportDefinition|">BAE SAMLESBURY CMS</option>
            <option value="BAE_SYSTEMS_MONTHLY|BAESystemsConsolidatedInvoiceReportDefinition|">BAE SYSTEMS MONTHLY</option>
            <option value="BAE UK|BAE_UKInvoiceReportDefinition|">BAE UK</option>
            <option value="BAZ Original|BAZInvoiceReportDefinition|">BAZ Original</option>
            <option value="BAZ Duplicate|BAZInvoiceReportDefinition|">BAZ Duplicate</option>
            <option value="BEDFORD">BEDFORD (GM)</option>
            <option value="BOEING_OFFSITE">BOEING OFFSITE</option>
            <option value="BOE ST LOUIS|BOE_ST_LOUIS_File1|Group">BOEING STLOUIS</option>
            <option value="BOE ST LOUIS-SF|BOEING_STLOUIS_FEEInvoiceReportDefinition|">BOEING STLOUIS Fee</option>
            <option value="CAL">UNITED AIRLINES</option>
            <option value="CYCLONE Original|CYCLONEInvoiceReportDefinition|">CYCLONE Original</option>
            <option value="CYCLONE Duplicate|CYCLONEInvoiceReportDefinition|">CYCLONE Duplicate</option>
            <option value="DANA-FW">DANA FW</option>
            <option value="DANA-LIMA">DANA LIMA</option>
            <option value="DANA-MARION">DANA MARION</option>
            <option value="DANA-POTTS">DANA POTTS</option>
            <option value="FENTON">DCX FENTON</option>
            <option value="DETROIT-DIESEL">DETROIT DIESEL</option>
            <option value="DET-DIESEL-SF">DETROIT DIESEL Fee</option>
            <option value="DRS">DRS</option>
            <option value="DRS_PALM_BAY">DRS PALM BAY</option>
            <option value="DRS_PALM_BAY_FEE">DRS PALM BAY Fee</option>
            <option value="ELET_BRIANTEA|ELET_BRIANTEAInvoiceReportDefinition|">ELET BRIANTEA</option>
            <option value="ELETTRO_BB|ELETTRO_BBInvoiceReportDefinition|">ELETTRO BB</option>
            <option value="ERAES|ERAESInvoiceReportDefinition|">ERAES</option>
            <option value="EW|GoletaInvoiceReportDefinition|">EW (Goleta)</option>
            <option value="EW-FEE|EW_FEEInvoiceReportDefinition|">EW Fee</option>
            <option value="EXOSTAR">EXOSTAR (Raytheon)</option>
            <option value="FEC">FEC</option>
            <option value="FEC_ADD_CHARGE">FEC ADD CHARGE</option>
            <option value="FEDCO Original|FEDCOInvoiceReportDefinition|">FEDCO Original</option>
            <option value="FEDCO Duplicate|FEDCOInvoiceReportDefinition|">FEDCO Duplicate</option>
            <option value="FLINT">FLINT SF (GM)</option>
            <option value="FLINT_MATERIAL">FLINT MATERIAL (GM)</option>
            <option value="FORT RUCKER|FORT_RUCKERInvoiceReportDefinition|">FORT RUCKER</option>
            <option value="FORT RUCKER FEE|FORT_RUCKER_FEEInvoiceReportDefinition|">FORT RUCKER Fee</option>
            <option value="G-SINGAPORE|G_SINGAPOREReportDefinition|">G SINGAPORE</option>
            <option value="GARDNER-DENVER|GARDNER_DENVERReportDefinition|">GARDNER DENVER</option>
            <option value="GEMA">GEMA</option>
            <option value="GD">GENERAL DYNAMICS</option>
            <option value="GD-SF|GD_SFInvoiceReportDefinition|">GD ServiceFee</option>
            <option value="GE-W">GE WHIPPANY</option>
            <option value="GE-WF">GE WHIPPANY Fee</option>
            <option value="GETRAG|GETRAGInvoiceReportDefinition|">GETRAG</option>
            <option value="GETRAG SLOVAKIA|GETRAG_SLOVAKIAInvoiceReportDefinition|">GETRAG SLOVAKIA</option>
            <option value="GETRAG SLOV VARIOCUT|GETRAG_SLOVVARIOCUTInvoiceReportDefinition|">GETRAG SLOV VARIOCUT</option>
            <option value="GKN ST LOUIS|GKN_File1|Group">GKN(St Louis)</option>
            <option value="TALLASSEE|TALLASSEEInvoiceReportDefinition|">GKN Material</option>
            <option value="TALLASSEE-SF|TALLASSEEInvoiceReportDefinition|">GKN Fee</option>
            <option value="GKN ST LOUIS-SF|GKN_STLOUIS_FEEInvoiceReportDefinition|">GKN STLOUIS Fee</option>
            <option value="GOLETAEW">GOLETA EW</option>
            <option value="HEXCEL-KENT|HEXCEL_KENTInvoiceReportDefinition|">HEXCEL KENT</option>
            <option value="HEXCEL-KENT FEE|HEXCEL_KENT_FEEInvoiceReportDefinition|">HEXCEL KENT Fee</option>
            <option value="HRGIVON Original|HRGIVONInvoiceReportDefinition|">HRGIVON Original</option>
            <option value="HRGIVON Duplicate|HRGIVONInvoiceReportDefinition|">HRGIVON Duplicate</option>
            <option value="AECI|HS_ChinaInvoiceReportDefinition|">HS CHINA</option>
            <option value="HSD-CLAVER|HSD_CLAVERInvoiceReportDefinition|">HSD CLAVER</option>
            <option value="HSD-MALAYSIA|HSD_MALAYSIAInvoiceReportDefinition|">HSD MALAYSIA</option>
            <option value="HSD-MARSTON|HSD_MARSTONInvoiceReportDefinition|">HSD MARSTON</option>
            <option value="HSD-REVIMA|REVIMAReportDefinition|">HSD REVIMA</option>
            <option value="HSD RF|HSDReportDefinition|">HSD RF (Ratier-Figeac)</option>
            <option value="IAI2 Original|IAI2InvoiceReportDefinition|">IAI2 Original</option>
            <option value="IAI2 Duplicate|IAI2InvoiceReportDefinition|">IAI2 Duplicate</option>
            <option value="IAI Original|IAIInvoiceReportDefinition|">IAI Original</option>
            <option value="IAI Duplicate|IAIInvoiceReportDefinition|">IAI Duplicate</option>
            <option value="IAI_FEE Original|IAIFeeInvoiceReportDefinition|">IAI FEE Original</option>
            <option value="IAI_FEE Duplicate|IAIFeeInvoiceReportDefinition|">IAI FEE Duplicate</option>
            <option value="IAI Freight Original|IAIFreightInvoiceReportDefinition|">IAI FREIGHT Original</option>
            <option value="IAI Freight Duplicate|IAIFreightInvoiceReportDefinition|">IAI FREIGHT Duplicate</option>
            <option value="IRELAND_DUBLIN_AERO|DublinAeroConsolidatedInvoiceReportDefinition|">IRELAND Dublin Aero</option>
            <option value="IRELAND_SHANNON_AERO|ShannonAeroConsolidatedInvoiceReportDefinition|">IRELAND Shannon Aero</option>
            <option value="JOHNSTOWN|JOHNSTOWN_File1|Group">JOHNSTOWN</option>
            <option value="JOHNSTOWN-FEE|JOHNSTOWN_FEEInvoiceReportDefinition|">JOHNSTOWN Fee</option>
            <option value="KANFIT Original|KANFITInvoiceReportDefinition|">KANFIT Original</option>
            <option value="KANFIT Duplicate|KANFITInvoiceReportDefinition|">KANFIT Duplicate</option>
            <option value="L-3">L3</option>
            <option value="LAKE ORION|LAKE_ORIONInvoiceReportDefinition|">LAKE ORION</option>
            <option value="ARCHBALD|ARCHBALD_File1|Group">LM ARCHBALD</option>
            <option value="KELLY">LOCKHEED/Kelly AC (San Antonio)</option>
            <option value="SYRACUSE">LOCKHEED/Syracuse</option>
            <option value="MAEET">MAEET</option>
            <option value="MARIETTA#1|LOCKHEED_MARIETTA_File1|Group">MARIETTA (Material)</option>
            <option value="MARIETTA#2|LOCKHEED_MARIETTA_File2|Group">MARIETTA (Material Credit)</option>
            <option value="MARIETTA#3|LOCKHEED_MARIETTA_File3|Group">MARIETTA (Service Fee)</option>
            <option value="MARIETTA#4|LOCKHEED_MARIETTA_File4|Group">MARIETTA (Service Fee)</option>
            <option value="MARIETTA#5|LOCKHEED_MARIETTA_File5|Group">MARIETTA (Consigned Credit)</option>
            <option value="MILLER">MILLER Brewing</option>
            <option value="MOOG-EAST AURORA|MOOG_File1|Group">MOOG EAST AURORA</option>
            <option value="MOOG-BAGUIO CITY|MOOG_BAGUIO_CITY_File1|Group">MOOG Baguio City</option>
            <option value="MORAINE">MORAINE (GM)</option>
            <option value="MT|MTInvoiceReportDefinition|">MT</option>
            <option value="MT-SF|MT_SFInvoiceReportDefinition|">MT Fee</option>
            <option value="NALCO">NALCO</option>
            <option value="NORD MICRO|NORD_MICROInvoiceReportDefinition|">NORD MICRO</option>
            <option value="NRG|NRGInvoiceReportDefinition|">NRG</option>
            <option value="OMMC">OMMC</option>
            <option value="OSH-CAR PT|GM_OSHReportDefinition|">OSH-CAR PT</option>
            <option value="PALMDALE#1|LOCKHEED_PALMDALE_File1|Group">PALMDALE (Material)</option>
            <option value="PALMDALE#2|LOCKHEED_PALMDALE_File2|Group">PALMDALE (Material Credit)</option>
            <option value="PALMDALE#3|LOCKHEED_PALMDALE_File3|Group">PALMDALE (Service Fee)</option>
            <option value="PALMDALE#4|LOCKHEED_PALMDALE_File4|Group">PALMDALE (Service Fee Credit)</option>
            <option value="PALMDALE#5|LOCKHEED_PALMDALE_File5|Group">PALMDALE (Consigned Credit)</option>
            <option value="POLCHEM|POLCHEMInvoiceReportDefinition|">POLCHEM</option>
            <option value="NETMRO_MONTHLY_PPG|NETMRO_MONTHLY_PPGReportDefinition|">PPG</option>
            <option value="PRISM">PRISM</option>
            <option value="PWA-JTT|PWA_JTTInvoiceReportDefinition|">PWA JTT</option>
            <option value="PWA-SA FEE|PWA_SA_FEEInvoiceReportDefinition|">PWA SA Fee</option>
            <option value="PWA-SA PAPER|PWA_SA_PAPERInvoiceReportDefinition|">PWA SA PAPER</option>
            <option value="PWA-WV|PWA_WVInvoiceReportDefinition|">PWA WV</option>
            <option value="PWA SINGAPORE|PWAReportDefinition|">PWA SINGAPORE</option>
            <option value="PWC|PWCReportDefinition|">PWC</option>
            <option value="PWR FEE|PWR_FEEInvoiceReportDefinition|">PWR Fee</option>
            <option value="RAC">RAC Raytheon</option>
            <option value="RR-SGP|ROLLSROYCEInvoiceReportDefinition|">ROLLSROYCE</option>
            <option value="RR-SGP FEE|RR_SGP_FEEInvoiceReportDefinition|">ROLLSROYCE Fee</option>
            <option value="RR-SGP-CAB|RRCABInvoiceReportDefinition|">ROLLSROYCE-CAB</option>
            <option value="SAC|SACInvoiceReportDefinition|">SAC</option>
            <option value="SAC-CHASE FIELD|SAC_CHASE_FIELDInvoiceReportDefinition|">SAC-CHASE FIELD</option>
            <option value="SAC-CHASE FIELD FEE|SAC_CHASE_FEEInvoiceReportDefinition|">SAC-CHASE FIELD Fee</option>
            <option value="SAC-CTI FEE|SAC_CTI_FEEInvoiceReportDefinition|">SAC CTI Fee</option>
            <option value="SAC-HSI|SAC_HSIInvoiceReportDefinition|">SAC HSI</option>
            <option value="SAC-KEYSTONE|SAC_KEYSTONEInvoiceReportDefinition|">SAC KEYSTONE</option>
            <option value="SAC-KEYSTONE FEE|SAC_KEYSTONE_FEEInvoiceReportDefinition|">SAC KEYSTONE Fee</option>
            <option value="SAC-LIFEPORT|SAC_LIFEPORTInvoiceReportDefinition|">SAC LIFEPORT</option>
            <option value="SAC-NORTH ISLAND|SAC_NORTHISLANDInvoiceReportDefinition|">SAC NORTHISLAND</option>
            <option value="SAC-OWEGO|SAC_OWEGOInvoiceReportDefinition|">SAC OWEGO</option>
            <option value="SAIC">SAIC</option>
            <option value="SAIC_TF">SAIC TestingFee</option>
            <option value="SALES Original|IAI_SALESInvoiceReportDefinition|">SALES Original</option>
            <option value="SALES Duplicate|IAI_SALESInvoiceReportDefinition|">SALES Duplicate</option>
            <option value="SALES-ACM|ACMReportDefinition|">SALES ACM</option>
            <option value="SALES-ASIA|SALES_ASIAReportDefinition|">SALES ASIA</option>
            <option value="SALES-ITALY|SALES_ITALYInvoiceReportDefinition|">SALES ITALY</option>
            <option value="SALES-STS|SALES_STSInvoiceReportDefinition|">SALES STS</option>
            <option value="SALES-TRIUMPH|TRIUMPHReportDefinition|">SALES TRIUMPH</option>
            <option value="SAUER_DANFOSS">SAUER Danfoss</option>
            <option value="SCHWEIZER|SCHWEIZERInvoiceReportDefinition|">SCHWEIZER</option>
            <option value="SEAGATE MALAYSIA|SEAGATE_MALAYSIAInvoiceReportDefinition|">SEAGATE MALAYSIA</option>
            <option value="SEAGATE MALAYSIA FEE|SEAGATE_MALAYSIA_FEEInvoiceReportDefinition|">SEAGATE MALAYSIA Fee</option>
            <option value="SG CONSUMABLES#1|SG_CONSUMABLES_File1|Group">SG CONSUMABLES(Consumables)</option>
            <option value="SG CONSUMABLES-SF|SG_CONSUMABLES_SFReportDefinition|">SG CONSUMABLES SF</option>
            <option value="SG NONVENDABLES#1|SG_NONVENDABLES_File1|Group">SG NONVENDABLES</option>
            <option value="SIEMENS|SIEMENSReportDefinition|">SIEMENS</option>
            <option value="SINGAPORE|SINGAPOREReportDefinition|">SINGAPORE (UTD-Hamilton)</option>
            <option value="SINGAPORE-SF|SINGAPORE_SFReportDefinition|">SINGAPORE SF</option>
            <option value="SLAC">SLAC</option>
            <option value="SLAC-SF">SLAC SF</option>
            <option value="SING_MONTHLY_STA|StAerospaceConsolidatedInvoiceReportDefinition|">ST Aerospace</option>
            <option value="STD AERO|STD_AEROReportDefinition|">STANDARD Aero</option>
            <option value="STARK">STARK</option>
            <option value="SWA">SOUTHWEST AIRLINES</option>
            <option value="TIMKEN">TIMKEN</option>
            <option value="TOMKINS-AUTO|TOMKINSReportDefinition|">TOMKINS Auto</option>
            <option value="TRENTON MAT|TRENTONInvoiceReportDefinition|">TRENTON Material</option>
            <option value="TRENTON|TRENTON_FEEInvoiceReportDefinition|">TRENTON Fee</option>
            <option value="UK_ACE|AceConsolidatedInvoiceReportDefinition|">UK Ace</option>
            <option value="UK_MONTHLY_AIR|AircelleConsolidatedInvoiceReportDefinition|">UK Aircelle</option>
            <option value="UK_MONTHLY_ATC|ATCConsolidatedInvoiceReportDefinition|">UK Atc</option>
            <option value="UK_WEEKLY_BAE|BAEConsolidatedInvoiceReportDefinition|">UK BAE</option>
            <option value="UK_MONTHLY_BMI|BMIConsolidatedInvoiceReportDefinition|">UK BMI</option>
            <option value="UK_MONTHLY_BROOK|BrookConsolidatedInvoiceReportDefinition|">UK Brookhouse</option>
            <option value="UK_BOND|BondConsolidatedInvoiceReportDefinition|">UK Bond</option>
            <option value="UK_CHC_SCOTIA|ScotiaConsolidatedInvoiceReportDefinition|">UK CHC Scotia</option>
            <option value="UK_MONTHLY_CTRM|CTRMConsolidatedInvoiceReportDefinition|">UK CTRM</option>
            <option value="UK_MONTHLY_FINNAIR_ES|FINNAIR_ESConsolidatedInvoiceReportDefinition|">UK Finnair ES</option>
            <option value="UK_MONTHLY_FINNAIR_TS|FINNAIR_TSConsolidatedInvoiceReportDefinition|">UK Finnair TS</option>
            <option value="UK_MONTHLY_FINNAIR_TS_FEE|FINNAIR_TS_FEEInvoiceReportDefinition|">UK Finnair TS Fee</option>
            <option value="UK_MONTHLY_FLYBE|FlyBeConsolidatedInvoiceReportDefinition|">UK FlyBe</option>
            <option value="UK_GE_ARLE_COURT|ArleCourtConsolidatedInvoiceReportDefinition|">UK GE Arle Court</option>
            <option value="UK_GE_BISHOPS_CLEEVE|BishopsCleeveConsolidatedInvoiceReportDefinition|">UK GE Bishops Cleeve</option>
            <option value="UK_GE_DOWTY_PROPELLERS|DowtyPropellersConsolidatedInvoiceReportDefinition|">UK GE Dowty Propellers</option>
            <option value="UK_GE_DOWTY_REPAIR|DowtyRepairConsolidatedInvoiceReportDefinition|">UK GE Dowty Repair</option>
            <option value="UK_MONTHLY_GKN_LUTON|GKNConsolidatedInvoiceReportDefinition|">UK GKN</option>
            <option value="UK_MONTHLY_HONEYWELL_LUTON|LutonConsolidatedInvoiceReportDefinition|">UK HoneyWell Luton</option>
            <option value="UK_MONTHLY_HONEYWELL_FELTHAM|FelthamConsolidatedInvoiceReportDefinition|">UK HoneyWell Feltham</option>
            <option value="UK_MONTHLY_INFLITE|InfliteConsolidatedInvoiceReportDefinition|">UK Inflite</option>
            <option value="UK_LOGANAIR|LoganairConsolidatedInvoiceReportDefinition|">UK Loganair</option>
            <option value="UK_MONTHLY_MAGELLAN_STRUCTURES|MagellanConsolidatedInvoiceReportDefinition|">UK MagellanStructures</option>
            <option value="UK_MONTHLY_MAGELLAN_BOURNEMOUT|BournemoutConsolidatedInvoiceReportDefinition|">UK MagellanBournemout</option>
            <option value="UK_MONTHLY_MEGGITT|MeggittConsolidatedInvoiceReportDefinition|">UK Meggitt</option>
            <option value="UK_MONTHLY_MA_CHEMICALS|MA_CHEMICALSConsolidatedInvoiceReportDefinition|">UK Ma Chemicals</option>
            <option value="UK_MONTHLY_MA_CHEM_NC|MA_CHEM_NCConsolidatedInvoiceReportDefinition|">UK Ma Chem NC</option>
            <option value="UK_MONTHLY_MA_TOOLING|MA_TOOLINGConsolidatedInvoiceReportDefinition|">UK Ma Tooling</option>
            <option value="UK_MONTHLY_MA_TOOL_NC|MA_TOOL_NCConsolidatedInvoiceReportDefinition|">UK Ma Tool NC</option>
            <option value="UK_MONTHLY_A|UKAllConsolidatedInvoiceReportDefinition|">UK Others</option>
            <option value="UK_MONTHLY_SAS|SASConsolidatedInvoiceReportDefinition|">UK SAS</option>
            <option value="UK_MONTHLY_THOMSON_AIRWAYS|THOMSON_AIRWAYSConsolidatedInvoiceReportDefinition|">UK Thomson Airways</option>
            <option value="UK_THOMAS_COOK|COOKConsolidatedInvoiceReportDefinition|">UK Thomas Cook</option>
            <option value="UK_MONTHLY_TITAN|TITANConsolidatedInvoiceReportDefinition|">UK Titan</option>
            <option value="UTC_HSD">UTC HSD (New England)</option>
            <option value="UTC_PWA">UTC PWA (New England)</option>
            <option value="UTC_SAC">UTC Sikorsky</option>
            <option value="UTC_SAC_CTI">UTC SAC CTI</option>
            <option value="VALLEY FORGE|VALLEYFORGE_File1|Group">VALLEY FORGE</option>
            <option value="VALLEY FORGE-FEE|VALLEY_FORGE_FEEInvoiceReportDefinition|">VALLEY FORGE Fee</option>
            <option value="VOLVO">VOLVO NRV</option>
            <option value="WILMINGTON">WILMINGTON (GM)</option>
            <option value="WOODWARD|WOODWARD_File1|Group">WOODWARD</option>
            <option value="WOODWARD FEE|WOODWARD_FEEInvoiceReportDefinition|">WOODWARD Fee</option>
            <option value="ZF">ZF Marysville</option>
            <option value="ZWL|ZWLInvoiceReportDefinition|">ZWL</option>
         </select>   
    </td>
  </tr>
  <tr>
    <td  class="optionTitleBoldRight">
		<fmt:message key="invoice.label.invoiceperiod"/>:
    </td>
    <td  class="optionTitleLeft">
          <input class="inputBox" type="text" name="invoicePeriod" id="invoicePeriod"  value="" />
    </td>
  </tr>
  <tr>
    <td  class="optionTitleBoldRight">
		<fmt:message key="label.invoice"/>:
    </td>
    <td  class="optionTitleLeft">
          <input class="inputBox" type="text" name="invoiceNumber" id="invoiceNumber"  value="" />
          <button name="clientButton"  class="inputBtns" 
           onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
           onclick= "return(submitClientForm());">
		  Get Client</button>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="optionTitleLeft">
        <button name="invoiceButton"  class="inputBtns" 
        onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
        onclick= "return submitInvoiceForm();">
		<fmt:message key="invoice.button.submit"/>
        </button>
     </td>
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
<div id="errorMessagesAreaBody" style="display:none;">
    <errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value=""/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->



<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
 	<br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
      --%>        
    <div id="mainUpdateLinks"  > <%-- mainUpdateLinks Begins --%>    
     <span id="updateResultLink" style="display: none">      
     </span>      
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
    <div class="dataContent">
     
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


<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</div>

</body>
</html>