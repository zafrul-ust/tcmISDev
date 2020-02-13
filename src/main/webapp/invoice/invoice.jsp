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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preferred font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<title>
<fmt:message key="invoice.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

function submitSearchForm() {
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Invoice','650','600','yes');
    document.invoiceForm.target='_Invoice';
    var a = window.setTimeout("document.invoiceForm.submit();",500);
	return false;
}

function validateForm()
{
if(document.getElementById("invoicePeriod").value.length == 0  && document.getElementById("invoiceNumber").value.length == 0)
   {
     alert("Please enter InvoicePeriod or InvoiceNumber");
     return false;
   }
   else
   {
     openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Invoice','650','600','yes');
     document.invoiceForm.target='_Invoice';
     var a = window.setTimeout("document.invoiceForm.submit();",500);
	 return false;
   }
}

// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="/invoice.do">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
 </div>
 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left"/>
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right"/>
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="invoice.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.logout"/>
</html:link>
</td>
</tr>
</table>
</div>

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
		<fmt:message key="invoice.label.client"/>:
    </td>
    <td width="70%" class="optionTitleLeft">
         <html:select property="client" styleId="client" styleClass="selectBox">
          <%--  <html:options collection="configuredInventoryGroupOptions" property="value" labelProperty="label" />
          </html:select>--%>
          	<html:option value="AAM AUBURN HILLS NORTH|AMERICAN_AXLE_File1|Group">AAM Auburn Hills North</html:option>
          	<html:option value="AAM AUBURN HILLS NORTH FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Auburn Hills North Fee</html:option>
          	<html:option value="AAM AUBURN HILLS SOUTH|AMERICAN_AXLE_File1|Group">AAM Auburn Hills South</html:option>
          	<html:option value="AAM AUBURN HILLS SOUTH FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Auburn Hills South Fee</html:option>
			<html:option value="AAM BISCOE|AMERICAN_AXLE_File1|Group">AAM Biscoe</html:option>
            <html:option value="AAM BLUFFTON|AMERICAN_AXLE_File1|Group">AAM Bluffton</html:option>
          	<html:option value="AAM BREWTON|AAM_BREWTON_File1|Group">AAM Brewton</html:option>
           	<html:option value="AAM COLDWATER|AMERICAN_AXLE_File1|Group">AAM Coldwater</html:option>
          	<html:option value="AAM COLUMBIANA|AMERICAN_AXLE_File1|Group">AAM Columbiana</html:option>
          	<html:option value="AAM COLUMBUS|AMERICAN_AXLE_File1|Group">AAM Columbus</html:option>
          	<html:option value="AAM COLUMBUS FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Columbus Fee</html:option>
          	<html:option value="AAM DETROIT|AMERICAN_AXLE_File1|Group">AAM Detroit</html:option>
          	<html:option value="AAM DETROIT FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Detroit Fee</html:option>
           	<html:option value="AAM FORT WAYNE|AMERICAN_AXLE_File1|Group">AAM Fort Wayne</html:option>
           	<html:option value="AAM FRASER|AMERICAN_AXLE_File1|Group">AAM Fraser</html:option>
          	<html:option value="AAM FRASER FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Fraser Fee</html:option>
           	<html:option value="AAM FREMONT|AMERICAN_AXLE_File1|Group">AAM Fremont</html:option>
           	<html:option value="AAM LITCHFIELD|AMERICAN_AXLE_File1|Group">AAM Litchfield</html:option>
           	<html:option value="AAM NEW CASTLE|AMERICAN_AXLE_File1|Group">AAM Newcastle</html:option>
           	<html:option value="AAM OXFORD|AMERICAN_AXLE_File1|Group">AAM Oxford</html:option>
          	<html:option value="AAM OXFORD FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Oxford Fee</html:option>
          	<html:option value="AAM OXFORD FORGE|AMERICAN_AXLE_File1|Group">AAM Oxford Forge</html:option>
          	<html:option value="AAM OXFORD FORGE FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Oxford Forge Fee</html:option>
          	<html:option value="AAM PARIS|AMERICAN_AXLE_File1|Group">AAM Paris</html:option>
           	<html:option value="AAM RAMOS 1|AMERICAN_AXLE_File1|Group">AAM Ramos 1</html:option>
           	<html:option value="AAM ROCHESTER|AMERICAN_AXLE_File1|Group">AAM Rochester</html:option>
           	<html:option value="AAM ROCHESTER HILLS TECH|AMERICAN_AXLE_File1|Group">AAM Rochester Hills Technical</html:option>
           	<html:option value="AAM ROYAL OAK|AMERICAN_AXLE_SF_EXCLUDED_File1|Group">AAM Royal Oaks Material + Add Charge</html:option>
           	<html:option value="AAM ROYAL OAK|AMERICAN_AXLE_SF_File1|Group">AAM Royal Oaks Service Fee</html:option>
          	<html:option value="AAM ROYAL OAK FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Royal Oaks Fee</html:option>
          	<html:option value="AAM SUBIACO|AMERICAN_AXLE_File1|Group">AAM Subiaco</html:option>
          	<html:option value="AAM THREE RIVERS|AAM_THREE_RIVERS_File1|Group">AAM Three Rivers</html:option>
            <html:option value="AAM FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Three Rivers Fee</html:option>
            <html:option value="AAM TROY|AMERICAN_AXLE_SF_EXCLUDED_File1|Group">AAM Troy Material + Add Charge</html:option>
            <html:option value="AAM TROY|AMERICAN_AXLE_SF_File1|Group">AAM Troy Service Fee</html:option>
          	<html:option value="AAM TROY FEE|AMERICAN_AXLE_FEEInvoiceReportDefinition|">AAM Troy Fee</html:option>
            <html:option value="AERNNOVA_MEX|AERNNOVA_MEX_File1|Group">AERNNOVA Mexico</html:option>
          	<html:option value="AMT|AMT_File1|Group">AMT</html:option>
          	<html:option value="AMT FEE|AMTFeeReportDefinition|">AMT Fee</html:option>
          	<html:option value="AJW|AJW_File1|Group">AJW</html:option>
          	<html:option value="AJW FEE|AJW_FEEInvoiceReportDefinition|">AJW Fee</html:option>
            <html:option value="BALDWIN ROAD|ACUMENT_File1|Group">ACUMENT Baldwin Road</html:option>
            <html:option value="BELVIDERE_ACU|ACUMENT_File1|Group">ACUMENT Belvidere</html:option>
            <html:option value="BURBANK|ACUMENT_File1|Group">ACUMENT Burbank</html:option>
            <html:option value="FENTON_ACU|ACUMENT_File1|Group">ACUMENT Fenton</html:option>
            <html:option value="FRANKFORT|ACUMENT_File1|Group">ACUMENT Frankfort</html:option>
          	<html:option value="FRANKFORT FEE|ACUMENT_FEEInvoiceReportDefinition|">ACUMENT Frankfort Fee</html:option>
          	<html:option value="FRANKFORT DC|ACUMENT_File1|Group">ACUMENT Frankfort Dist</html:option>
          	<html:option value="FRANKFORT DC FEE|ACUMENT_FEEInvoiceReportDefinition|">ACUMENT Frankfort Dist Fee</html:option>
            <html:option value="HOLLY DIST|ACUMENT_File1|Group">ACUMENT Holly Dist</html:option>
            <html:option value="NORTH HOLLY|ACUMENT_File1|Group">ACUMENT North Holly</html:option>
            <html:option value="ROCHESTER|ACUMENT_File1|Group">ACUMENT Rochester</html:option>
            <html:option value="SPENCER|ACUMENT_File1|Group">ACUMENT Spencer</html:option>
          	<html:option value="STERLING HEIGHTS|ACUMENT_File1|Group">ACUMENT Sterling Heights</html:option>
            <html:option value="AEROCZ|AEROCZInvoiceReportDefinition|">AEROCZ</html:option>
            <html:option value="AEROJET|AEROJET_File1|Group">AEROJET</html:option>
            <html:option value="AEROJET-SACRAM FEE|AEROJET_FEEInvoiceReportDefinition|">AEROJET Fee</html:option>
            <html:option value="AERO GS FEE|AERO_GS_FEEInvoiceReportDefinition|">AERO GS Fee</html:option>
            <html:option value="AEROJET HVILLE FEE|AEROJET_HVILLE_FEEInvoiceReportDefinition|">AEROJET Huntsville Fee</html:option>
  			<html:option value="ROCKETDYNE|ROCKETDYNE_File1|Group">AEROJET Rocketdyne</html:option>
            <html:option value="ROCKETDYNE FEE|ROCKETDYNE_FEEInvoiceReportDefinition|">AEROJET Rocketdyne Fee</html:option>
            <html:option value="ROCKETDYNE WPB|ROCKETDYNE_WPBInvoiceReportDefinition|">AEROJET Rocketdyne WPB</html:option>
            <html:option value="ROCKETDYNE WPB FEE|ROCKETDYNE_WPB_FEEInvoiceReportDefinition|">AEROJET Rocketdyne WPB Fee</html:option>
            <html:option value="AW-YEOVIL|AGUSTA_YEOVILInvoiceReportDefinition|">AGUSTA YEOVIL</html:option>
            <html:option value="AW-YEOVIL 2|AGUSTA_YEOVILInvoiceReportDefinition|">AGUSTA YEOVIL 2</html:option>
            <html:option value="AW-YEOVIL FEE|AGUSTA_YEOVIL_FEEInvoiceReportDefinition|">AGUSTA YEOVIL FEE</html:option>
            <html:option value="AW-YEOVIL FEE 2|AGUSTA_YEOVIL_FEEInvoiceReportDefinition|">AGUSTA YEOVIL FEE 2</html:option>
            <html:option value="ALGAT|ALGATReportDefinition|">ALGAT</html:option>
            <html:option value="ARVIN_MERITOR">ARVIN MERITOR</html:option>
            <html:option value="ASSIREVA_SRL|ASSIREVA_SRLInvoiceReportDefinition|">ASSIREVA SRL</html:option>
            <html:option value="AVCORP|AVCORP_File1|Group">AVCORP</html:option>
            <html:option value="AVCORP FEE|AvcorpFeeInvoiceReportDefinition|">AVCORP Fee</html:option>
            <html:option value="BAE_AUSTIN|BAE_AUSTINInvoiceReportDefinition|">BAE Austin</html:option>
            <html:option value="BAE RDT|BaeRDTConsolidatedInvoiceReportDefinition|">BAE Hillend</html:option>
            <html:option value="BAE">BAE Johnson City</html:option>
            <html:option value="BAE_NASHUA|BAENashua_File1|Group">BAE Nashua</html:option>
            <html:option value="BAE_NASHUA_FEE|BAE_NASHUA_FEEInvoiceReportDefinition|">BAE Nashua Fee</html:option>
            <html:option value="BAZ Original|BAZInvoiceReportDefinition|">BAZ Original</html:option>
            <html:option value="BAZ Duplicate|BAZInvoiceReportDefinition|">BAZ Duplicate</html:option>
            <html:option value="BEECHCRAFT_KS|BEECHCRAFT_File1|Group">BEECHCRAFT</html:option>
            <html:option value="BEECHCRAFT_KS_FEE|BEECHCRAFT_FEEInvoiceReportDefinition|">BEECHCRAFT Fee</html:option>
            <html:option value="BEECHCRAFT_KS_GOVT|BEECHCRAFT_KS_GOVT_File1|Group">BEECHCRAFT Govt</html:option>
            <html:option value="BEECHCRAFT_KS_SPARES|BEECHCRAFT_SPARES_File1|Group">BEECHCRAFT Spares</html:option>
            <html:option value="BEECHCRAFT_MEX|BEECHCRAFT_MEX_File1|Group">BEECHCRAFT Mexico</html:option>
            <html:option value="SGP|BELL_CSS_File1|Group">BELL CSS</html:option>
            <html:option value="SGP FEE|BELL_CSSFeeReportDefinition|">BELL CSS Fee</html:option>
            <html:option value="SGP MRO|BELL_MRO_File1|Group">BELL MRO</html:option>
            <html:option value="DFW AMARILLO|BELL_File1|Group">BELL Ft. Worth</html:option>
            <html:option value="BELL_SPARES|BELL_SPARESInvoiceReportDefinition|">BELL Gateway Spares</html:option>
            <html:option value="LAFAYETTE|BELL_LAFAYETTE_File1|Group">BELL Lafayette</html:option>
            <html:option value="MIRABEL|MIRABEL_File1|Group">BELL Mirabel</html:option>
            <html:option value="MIRABEL FEE|MIRABEL_FEEInvoiceReportDefinition|">BELL Mirabel Fee</html:option>
            <html:option value="MEXICO|BELL_MEX_File1|Group">BELL Mexico</html:option>
            <html:option value="MEXICO FEE|MEXICO_FEEInvoiceReportDefinition|">BELL Mexico Fee</html:option>
            <html:option value="BELL PASS THROUGH|BELL_DFW_File1|Group">BELL Pass Through</html:option>
            <html:option value="BELL_PINEY_FLATS|BELL_PINEY_FLATS_File1|Group">BELL Piney Flats</html:option>
            <html:option value="BOE KSP|BOE_KSP_File1|Group">BOEING Kennedy Space Center</html:option>
            <html:option value="BOE MESA|BOE_MESA_File1|Group">BOEING Mesa</html:option>
            <html:option value="BOE MESA-SF|BOEING_MESA_FEEInvoiceReportDefinition|">BOEING Mesa Fee</html:option>
            <html:option value="BOEING_OFFSITE">BOEING Offsite</html:option>
            <html:option value="BOE PHILADELPHIA|BOE_PHILADELPHIA_File1|Group">BOEING Philadelphia</html:option>
            <html:option value="BOE AERO AUS|BOE_AERO_AUS_File1|Group">BOEING Australia</html:option>
            <html:option value="BOE SAT GENERAL|BOE_SAT_GENERAL_File1|Group">BOEING San Antonio General</html:option>
            <html:option value="BOE SAT FY2030AM|BOE_SAT_FY2030AM_File1|Group">BOEING San Antonio FY20AM</html:option>
            <html:option value="BOE SAT TAIL C17|BOE_SAT_C17_File1|Group">BOEING San Antonio Tail C17</html:option>
            <html:option value="BOE SAT TAIL KC135|BOE_SAT_KC135_File1|Group">BOEING San Antonio Tail KC135</html:option>
            <html:option value="BOE SAT-SF|BOEING_SAT_FEEInvoiceReportDefinition|">BOEING San Antonio Fee</html:option>
            <html:option value="BOE SEATTLE-SF|BOEING_SEATTLE_FEEInvoiceReportDefinition|">BOEING Seattle Fee</html:option>
            <html:option value="BOE ST LOUIS|BOE_ST_LOUIS_File1|Group">BOEING St Louis</html:option>
            <html:option value="BOE ST LOUIS-SF|BOEING_STLOUIS_FEEInvoiceReportDefinition|">BOEING St Louis Fee</html:option>
            <html:option value="BOMBARDIER MTL|BOMBARDIER_CREDITReportDefinition|">BOMBARDIER Montreal Credit</html:option>
            <html:option value="BOMBARDIER MTL|BOMBARDIER_DEBITReportDefinition|">BOMBARDIER Montreal Debit</html:option>
            <html:option value="CAT EAST PEORIA FEE|CATEastPeoriaFeeReportDefinition|">CAT East Peoria Fee</html:option>
            <html:option value="CAT MAPLETON FEE|CAT_MAPLETON_FEEInvoiceReportDefinition|">CAT Mapleton Fee</html:option>
            <html:option value="ABLE MESA|ABLE_MESA_File1|Group">CESSNA Able Mesa</html:option>
            <html:option value="ABLE MESA FEE|ABLEFeeReportDefinition|">CESSNA Able Mesa Fee</html:option>
            <html:option value="CESSNA_AVIC|CessnaAVICConsolidatedInvoiceReportDefinition|">Cessna AVIC</html:option>
            <html:option value="CESSNA_CSPP|CESSNA_CSPPInvoiceReportDefinition|">Cessna CSPP</html:option>
            <html:option value="CESSNA_KS|CESSNA_KS_File1|Group">Cessna KS</html:option>
            <html:option value="CESSNA_KS_FEE|CESSNA_KS_SFReportDefinition|">Cessna KS Fee</html:option>
            <html:option value="CESSNA_MEX|CESSNA_MEX_File1|Group">Cessna Mexico</html:option>
            <%-- <html:option value="CPP RZESZOW|PolandInvoiceReportDefinition|">CPP Rzeszow</html:option>  --%>
            <html:option value="CPP RZESZOW|CPP_RZESZOWInvoiceReportDefinition|">CPP Rzeszow</html:option>
          	<html:option value="CPP RZESZOW|CPP_RZESZOWAddChargeInvoiceReportDefinition|">CPP Rzeszow Add Charge</html:option>
          	<html:option value="CPP RZESZOW|CPP_RZESZOWFeeInvoiceReportDefinition|">CPP Rzeszow Fee</html:option>
            <html:option value="CPP RZESZOW|CPP_RZESZOWCorrectionInvoiceReportDefinition|">CPP Rzeszow Correction</html:option>
            <html:option value="CPP Rzeszow USD|CPP_RZESZOW_USDInvoiceReportDefinition|">CPP Rzeszow USD</html:option>
          	<html:option value="CPP Rzeszow USD|CPP_RZESZOW_USDAddChargeInvoiceReportDefinition|">CPP Rzeszow USD Add Charge</html:option>
          	<html:option value="CPP Rzeszow USD|CPP_RZESZOW_USDFeeInvoiceReportDefinition|">CPP Rzeszow USD Fee</html:option>
            <html:option value="CPP Rzeszow USD|CPP_RZESZOW_USDCorrectionInvoiceReportDefinition|">CPP Rzeszow USD Correction</html:option>
			<html:option value="CTG_BANBURY|CTG_BANBURY_File1|Group">CTG Banbury (PDF)</html:option>
            <html:option value="CTG_BANBURY|CTG_BANBURY_File2|Group">CTG Banbury (XLS)</html:option>
            <html:option value="CTG_BANBURY FEE|CTG_BANBURY_FEEInvoiceReportDefinition|">CTG Banbury Fee</html:option>
            <html:option value="CYCLONE Original|CYCLONEInvoiceReportDefinition|">CYCLONE Original</html:option>
            <html:option value="CYCLONE Duplicate|CYCLONEInvoiceReportDefinition|">CYCLONE Duplicate</html:option>
            <html:option value="DANA-ALBION-FIXED|DANA_FIXED_File1|Group">DANA Albion (XLS)</html:option>
            <html:option value="DANA-ALBION-FIXED|DANA_FIXED_File2|Group">DANA Albion (PDF)</html:option>
            <html:option value="DANA-BLACKLICK-FIXED|DANA_FIXED_File1|Group">DANA Blacklick (XLS)</html:option>
            <html:option value="DANA-BLACKLICK-FIXED|DANA_FIXED_File2|Group">DANA Blacklick (PDF)</html:option>
            <html:option value="DANA-COLUMBIA-FIXED|DANA_FIXED_File1|Group">DANA Columbia (XLS)</html:option>
            <html:option value="DANA-COLUMBIA-FIXED|DANA_FIXED_File2|Group">DANA Columbia (PDF)</html:option>
            <html:option value="DANA-CARDANES-FIXED|DANA_CARDANES_FIXED_File2|Group">DANA Cardanes</html:option>
            <html:option value="DANA-DANVILLE-FIXED|DANA_FIXED_File1|Group">DANA Danville (XLS)</html:option>
            <html:option value="DANA-DANVILLE-FIXED|DANA_FIXED_File2|Group">DANA Danville (PDF)</html:option>
            <html:option value="DANA-FW-FIXED|DANA_FIXED_File1|Group">DANA FW (XLS)</html:option>
            <html:option value="DANA-FW-FIXED|DANA_FIXED_File2|Group">DANA FW (PDF)</html:option>
            <html:option value="DANA-GLASGOW-FIXED|DANA_FIXED_File1|Group">DANA Glasgow (XLS)</html:option>
            <html:option value="DANA-GLASGOW-FIXED|DANA_FIXED_File2|Group">DANA Glasgow (PDF)</html:option>
            <html:option value="DANA-GORDONSVILLE-FIXED|DANA_FIXED_File1|Group">DANA Gordonsville (XLS)</html:option>
            <html:option value="DANA-GORDONSVILLE-FIXED|DANA_FIXED_File2|Group">DANA Gordonsville (PDF)</html:option>
            <html:option value="DANA-HENDERSON-FIXED|DANA_FIXED_File1|Group">DANA Henderson (XLS)</html:option>
            <html:option value="DANA-HENDERSON-FIXED|DANA_FIXED_File2|Group">DANA Henderson (PDF)</html:option>
            <html:option value="DANA-HUMBOLDT-FIXED|DANA_FIXED_File1|Group">DANA Humboldt (XLS)</html:option>
            <html:option value="DANA-HUMBOLDT-FIXED|DANA_FIXED_File2|Group">DANA Humboldt (PDF)</html:option>
            <html:option value="DANA-LIMA-FIXED|DANA_FIXED_File1|Group">DANA Lima (XLS)</html:option>
            <html:option value="DANA-LIMA-FIXED|DANA_FIXED_File2|Group">DANA Lima (PDF)</html:option>
            <html:option value="DANA-LOUISVILLE-FIXED|DANA_FIXED_File1|Group">DANA Louisville (XLS)</html:option>
            <html:option value="DANA-LOUISVILLE-FIXED|DANA_FIXED_File2|Group">DANA Louisville (PDF)</html:option>
            <html:option value="DANA-LUGOFF-FIXED|DANA_FIXED_File1|Group">DANA Lugoff (XLS)</html:option>
            <html:option value="DANA-LUGOFF-FIXED|DANA_FIXED_File2|Group">DANA Lugoff (PDF)</html:option>
            <html:option value="DANA-MARION-FIXED|DANA_FIXED_File1|Group">DANA Marion (XLS)</html:option>
            <html:option value="DANA-MARION-FIXED|DANA_FIXED_File2|Group">DANA Marion (PDF)</html:option>
            <html:option value="DANA-MAUMEE-FIXED|DANA_FIXED_File1|Group">DANA Maumee (XLS)</html:option>
            <html:option value="DANA-MAUMEE-FIXED|DANA_FIXED_File2|Group">DANA Maumee (PDF)</html:option>
            <html:option value="DANA-PARIS-FIXED|DANA_FIXED_File1|Group">DANA Paris (XLS)</html:option>
            <html:option value="DANA-PARIS-FIXED|DANA_FIXED_File2|Group">DANA Paris (PDF)</html:option>
            <html:option value="DANA-POTTS-FIXED|DANA_FIXED_File1|Group">DANA Pottstown (XLS)</html:option>
            <html:option value="DANA-POTTS-FIXED|DANA_FIXED_File2|Group">DANA Pottstown (PDF)</html:option>
            <html:option value="DANA-STCLAIR-FIXED|DANA_FIXED_File1|Group">DANA Saint Clair (XLS)</html:option>
            <html:option value="DANA-STCLAIR-FIXED|DANA_FIXED_File2|Group">DANA Saint Clair (PDF)</html:option>
            <html:option value="DANA-STCLAIR-FIXED FEE|DANA_FEEInvoiceReportDefinition|">DANA Saint Clair Fee</html:option>
            <html:option value="DANA-STERLING-FIXED|DANA_FIXED_File1|Group">DANA Sterling (XLS)</html:option>
            <html:option value="DANA-STERLING-FIXED|DANA_FIXED_File2|Group">DANA Sterling (PDF)</html:option>
            <html:option value="DANA-SUFFOLK-FIXED|DANA_FIXED_File1|Group">DANA Suffolk (XLS)</html:option>
            <html:option value="DANA-SUFFOLK-FIXED|DANA_FIXED_File2|Group">DANA Suffolk (PDF)</html:option>
            <html:option value="DANA-TOLEDO-FIXED|DANA_FIXED_File1|Group">DANA Toledo (XLS)</html:option>
            <html:option value="DANA-TOLEDO-FIXED|DANA_FIXED_File2|Group">DANA Toledo (PDF)</html:option>
            <html:option value="DANA-TOOL-FIXED|DANA_FIXED_File1|Group">DANA Tool (XLS)</html:option>
            <html:option value="DANA-TOOL-FIXED|DANA_FIXED_File2|Group">DANA Tool (PDF)</html:option>
            <html:option value="DANA-WARREN-FIXED|DANA_FIXED_File1|Group">DANA Warren (XLS)</html:option>
            <html:option value="DANA-WARREN-FIXED|DANA_FIXED_File2|Group">DANA Warren (PDF)</html:option>
            <html:option value="DANA-YORKTOWN-FIXED|DANA_FIXED_File1|Group">DANA Yorktown (XLS)</html:option>
            <html:option value="DANA-YORKTOWN-FIXED|DANA_FIXED_File2|Group">DANA Yorktown (PDF)</html:option>
            <html:option value="FENTON">DCX Fenton</html:option>
            <html:option value="DET-DIESEL">DETROIT DIESEL</html:option>
            <html:option value="DET-DIESEL-SF">DETROIT DIESEL Fee</html:option>
            <html:option value="SLAC">DOE SLAC</html:option>
            <html:option value="SLAC-SF">DOE SLAC Fee</html:option>
            <html:option value="DRS">DRS</html:option>
            <html:option value="DRS_PALM_BAY">DRS Palm Bay</html:option>
            <html:option value="DRS_PALM_BAY_FEE">DRS Palm Bay Fee</html:option>
            <html:option value="EATON LA|EATON_LA_File1|Group">Eaton LA</html:option>
            <html:option value="EATON LA FEE|EatonLAFeeReportDefinition|">Eaton LA Fee</html:option>
            <html:option value="ELET_BRIANTEA|ELET_BRIANTEAInvoiceReportDefinition|">ELET BRIANTEA</html:option>
            <html:option value="ELETTRO_BB|ELETTRO_BBInvoiceReportDefinition|">ELETTRO BB</html:option>
            <html:option value="MELBOURNE|EMBRAER_File1|Group">EMBRAER</html:option>
            <html:option value="ERAES|ERAESInvoiceReportDefinition|">ERAES</html:option>
            <html:option value="FEC">FEC</html:option>
            <html:option value="FEC_ADD_CHARGE">FEC ADD CHARGE</html:option>
            <html:option value="FEDCO Original|FEDCOInvoiceReportDefinition|">FEDCO Original</html:option>
            <html:option value="FEDCO Duplicate|FEDCOInvoiceReportDefinition|">FEDCO Duplicate</html:option>
            <html:option value="ALENIA AERMACCHI|ALENIA_AERMACCHIInvoiceReportDefinition|">FINMECCANICA ALENIA AERMACCHI</html:option>
            <html:option value="ALENIA FEE|ALENIA_FEEInvoiceReportDefinition|">FINMECCANICA ALENIA Fee</html:option>
            <html:option value="ALENIA SRL|ALENIA_SRLInvoiceReportDefinition|">FINMECCANICA ALENIA SRL</html:option>
            <html:option value="ALENIA SRL FEE|ALENIA_SRL_FEEInvoiceReportDefinition|">FINMECCANICA ALENIA SRL Fee</html:option>
            <html:option value="GD-SF|GD_SFInvoiceReportDefinition|">GD Fee</html:option>
            <html:option value="GE ENGINE MALAYSIA|GE_ENGINE_MALAYSIA_File1|Group">GE Engine Malaysia</html:option>
          	<html:option value="GE ENGINE MALAYSIA FEE|GE_ENGINE_MALAYSIA_FEEInvoiceReportDefinition|">GE Engine Malaysia Fee</html:option>
            <html:option value="GE GRAND RAPIDS|GE_GRAND_RAPIDS_File1|Group">GE Grand Rapids</html:option>
            <html:option value="GE GRAND RAPIDS FEE|GE_GRAND_RAPIDS_FEEInvoiceReportDefinition|">GE Grand Rapids Fee</html:option>
            <html:option value="GEMA">GEMA</html:option>
            <html:option value="GENON|GENONInvoiceReportDefinition|">GENON</html:option>
            <html:option value="GENON FEE|GENON_FEEInvoiceReportDefinition|">GENON Fee</html:option>
            <html:option value="GARDNER-DENVER|GARDNER_DENVERReportDefinition|">GERMANY GARDNER DENVER</html:option>
            <html:option value="GETRAG|GETRAGInvoiceReportDefinition|">GERMANY GETRAG</html:option>
            <html:option value="GETRAG SLOVAKIA|GETRAG_SLOVAKIAInvoiceReportDefinition|">GERMANY GETRAG SLOVAKIA</html:option>
            <html:option value="GETRAG PT|GETRAG_PTInvoiceReportDefinition|">GERMANY GETRAG PT s.r.o</html:option>
            <html:option value="GETRAG PT FEE|GETRAG_PT_FEEInvoiceReportDefinition|">GERMANY GETRAG PT s.r.o Fee</html:option>
            <html:option value="GETRAG SLOV VARIOCUT|GETRAG_SLOVVARIOCUTInvoiceReportDefinition|">GERMANY GETRAG SLOV VARIOCUT</html:option>
            <html:option value="HOERBIGER|HOERBIGERInvoiceReportDefinition|">GERMANY HOERBIGER</html:option>
            <html:option value="ZWL|ZWLInvoiceReportDefinition|">GERMANY NZWL</html:option>
            <html:option value="SIEMENS|SIEMENSReportDefinition|">GERMANY SIEMENS</html:option>
            <html:option value="GKN FOKKER|GKNFokkerInvoiceReportDefinition|">GKN Fokker</html:option>
            <html:option value="GKN FOKKER|GKNFokkerMaterialDetailReportDefinition|">GKN Fokker Material Detail</html:option>
          	<html:option value="GKN FOKKER FEE|GKN_FOKKER_FEEInvoiceReportDefinition|">GKN Fokker Fee</html:option>
          	<html:option value="GKN NEWTON|GKN_NEWTON_File2|Group">GKN Newton (PDF)</html:option>
          	<html:option value="GKN NEWTON|GKN_NEWTON_File1|Group">GKN Newton (XLS)</html:option>
          	<html:option value="GKN NEWTON FEE|GKN_NEWTON_FEEInvoiceReportDefinition|">GKN Newton Fee</html:option>
            <html:option value="GKN ST LOUIS|GKN_File1|Group">GKN St Louis</html:option>
            <html:option value="GKN ST LOUIS-SF|GKN_STLOUIS_FEEInvoiceReportDefinition|">GKN St Louis Fee</html:option>
            <html:option value="TALLASSEE|GKN_TALLASSEE_File1|Group">GKN Tallassee</html:option>
            <html:option value="TALLASSEE-SF|TALLASSEEInvoiceReportDefinition|">GKN Tallassee Fee</html:option>
            <html:option value="GKN TROLLHATTAN|GKN_TROLLHATTAN_File1|Group">GKN Trollhattan</html:option>
          	<html:option value="GKN TROLLHATTAN FEE|GKN_TROLLHATTAN_FEEInvoiceReportDefinition|">GKN Trollhattan Fee</html:option>
            <html:option value="BEDFORD">GM Bedford</html:option>
            <html:option value="FLINT">GM Flint SF</html:option>
            <html:option value="FLINT_MATERIAL">GM Flint Material</html:option>
            <html:option value="LAKE ORION|LAKE_ORIONInvoiceReportDefinition|">GM Lake Orion</html:option>
            <html:option value="WILMINGTON">GM Wilmington</html:option>
            <html:option value="GMF INDONESIA|GMFIndonesiaInvoiceReportDefinition|">GMF Indonesia</html:option>
            <html:option value="HEXCEL-KENT|HEXCEL_KENTInvoiceReportDefinition|">HEXCEL Kent</html:option>
            <html:option value="HEXCEL-KENT FEE|HEXCEL_KENT_FEEInvoiceReportDefinition|">HEXCEL Kent Fee</html:option>
            <html:option value="HEXCEL-KENT-PS|HEXCEL_KENT_PSInvoiceReportDefinition|">HEXCEL Kent PS</html:option>
            <html:option value="HONEYWELL_YEOVIL|HONEYWELL_YEOVIL_File1|Group">HONEYWELL Yeovil</html:option>
            <html:option value="HONEYWELL_YEOVIL FEE|HONEYWELL_YEOVIL_FEEInvoiceReportDefinition|">HONEYWELL Yeovil Fee</html:option>
            <html:option value="HRGIVON Original|HRGIVONInvoiceReportDefinition|">HRGIVON Original</html:option>
            <html:option value="HRGIVON Duplicate|HRGIVONInvoiceReportDefinition|">HRGIVON Duplicate</html:option>
            <html:option value="AECI|HS_ChinaInvoiceReportDefinition|">HS CHINA</html:option>
            <%-- <html:option value="HS RZESZOW|PolandInvoiceReportDefinition|">HS RZESZOW</html:option>
            <html:option value="HS WROCLAW|PolandInvoiceReportDefinition|">HS WROCLAW</html:option> --%>
            <html:option value="HS RZESZOW|HS_RZESZOWInvoiceReportDefinition|">HS Rzeszow</html:option>
          	<html:option value="HS RZESZOW|HS_RZESZOWAddChargeInvoiceReportDefinition|">HS Rzeszow Add Charge</html:option>
          	<html:option value="HS RZESZOW|HS_RZESZOWFeeInvoiceReportDefinition|">HS Rzeszow Fee</html:option>
            <html:option value="HS RZESZOW|HS_RZESZOWCorrectionInvoiceReportDefinition|">HS Rzeszow Correction</html:option>
            <html:option value="HS WROCLAW|HS_WROCLAWInvoiceReportDefinition|">HS Wroclaw</html:option>
          	<html:option value="HS WROCLAW|HS_WROCLAWAddChargeInvoiceReportDefinition|">HS Wroclaw Add Charge</html:option>
          	<html:option value="HS WROCLAW|HS_WROCLAWFeeInvoiceReportDefinition|">HS Wroclaw Fee</html:option>
            <html:option value="HS WROCLAW|HS_WROCLAWCorrectionInvoiceReportDefinition|">HS Wroclaw Correction</html:option>
            <html:option value="HUDSON FEE|HUDSON_FEEInvoiceReportDefinition|">HUDSON Fee</html:option>
            <html:option value="HUDSON GAS FEE|HUDSON_FEEInvoiceReportDefinition|">HUDSON Gas Fee</html:option>
            <html:option value="HUMBOLDT|HUMBOLDT_File1|Group">Humboldt</html:option>
            <html:option value="IAI2|IAI2InvoiceReportDefinition|">IAI2</html:option>
            <html:option value="IAI2 Original|IAI2InvoiceReportDefinition|">IAI2 Original</html:option>
            <html:option value="IAI2 Duplicate|IAI2InvoiceReportDefinition|">IAI2 Duplicate</html:option>
            <html:option value="IAI Original|IAIInvoiceReportDefinition|">IAI Original</html:option>
            <html:option value="IAI Duplicate|IAIInvoiceReportDefinition|">IAI Duplicate</html:option>
            <html:option value="IAI_FEE Original|IAIFeeInvoiceReportDefinition|">IAI FEE Original</html:option>
            <html:option value="IAI_FEE Duplicate|IAIFeeInvoiceReportDefinition|">IAI FEE Duplicate</html:option>
            <html:option value="IAI Freight Original|IAIFreightInvoiceReportDefinition|">IAI FREIGHT Original</html:option>
            <html:option value="IAI Freight Duplicate|IAIFreightInvoiceReportDefinition|">IAI FREIGHT Duplicate</html:option>
            <html:option value="BROMONT FEE|IBM_BROMONT_FEEInvoiceReportDefinition|">IBM Bromont Fee</html:option>
            <html:option value="IRELAND_AIR_ATLANTA|AirAtlantaConsolidatedInvoiceReportDefinition|">IRELAND AIR ATLANTA TRANSAERO</html:option>
            <html:option value="IRELAND_DUBLIN_AERO|DublinAeroConsolidatedInvoiceReportDefinition|">IRELAND Dublin Aero</html:option>
            <html:option value="IRELAND_SHANNON_AERO|ShannonAeroConsolidatedInvoiceReportDefinition|">IRELAND Shannon Aero</html:option>
            <html:option value="JDEERE BAIGORRIA|JDEERE_ARGENTINA_File1|Group">JDEERE Baigorria</html:option>
            <html:option value="JDEERE BAIGORRIA FEE|JDEERE_ARGENTINA_FEEInvoiceReportDefinition|">JDEERE Baigorria Fee</html:option>
            <html:option value="JDEERE DRIVE TRAIN|JDEERE_File1|Group">JDEERE Drive Train</html:option>
            <html:option value="JDEERE ENGINE WORKS|JDEERE_File1|Group">JDEERE Engine Works</html:option>
            <html:option value="JDEERE LAS ROSAS|JDEERE_ARGENTINA_File1|Group">JDEERE Las Rosas</html:option>
            <html:option value="JDEERE LAS ROSAS FEE|JDEERE_ARGENTINA_FEEInvoiceReportDefinition|">JDEERE Las Rosas Fee</html:option>
            <html:option value="JDEERE MANNHEIM|JDEERE_MANNHEIM_File1|Group">JDEERE Mannheim</html:option>
            <html:option value="JDEERE MANNHEIM FEE|JDEERE_MANNHEIM_FEEInvoiceReportDefinition|">JDEERE Mannheim Fee</html:option>
            <html:option value="JDEERE MOLINE|JDEERE_File1|Group">JDEERE Moline</html:option>
            <html:option value="JDEERE MOLINE FEE|JDEERE_MOLINE_FEEInvoiceReportDefinition|">JDEERE Moline Fee</html:option>
            <html:option value="KAMAN JACKSONVILLE|KAMAN_JACKSONVILLE_File1|Group">KAMAN Jacksonville</html:option>
            <html:option value="KAMAN JACKSONVILLE FEE|KAMAN_JACKSONVILLE_FEEInvoiceReportDefinition|">KAMAN Jacksonville Fee</html:option>
            <html:option value="KANFIT Original|KANFITInvoiceReportDefinition|">KANFIT Original</html:option>
            <html:option value="KANFIT Duplicate|KANFITInvoiceReportDefinition|">KANFIT Duplicate</html:option>
            <html:option value="L-3">L3</html:option>
            <html:option value="HERMOSILLO|HERMOSILLOInvoiceReportDefinition|">LATECOERE Hermosillo</html:option>
          	<html:option value="LEONARDO-ITALY-BRINDISI|LEONARDO_BRINDISI_File1|Group">LEONARDO Brindisi</html:option>
          	<html:option value="LEONARDO CAMERI|LEONARDOCameriInvoiceReportDefinition|">LEONARDO Cameri</html:option>
            <html:option value="LEONARDO CAMERI FEE|LEONAROD_CAMERI_FEEInvoiceReportDefinition|">LEONARDO Cameri FEE</html:option>
          	<html:option value="LEONARDO-ITALY-TESSERA|LEONARDO_TESSERA_File1|Group">LEONARDO Tessera</html:option>
          	<html:option value="LEONARDO-ITALY-VERGIATE|LEONARDO_VERGIATE_File1|Group">LEONARDO Vergiate</html:option>
            <html:option value="LEONARDO-ITALY|LEONARDO_ITALY_FEEInvoiceReportDefinition|">LEONARDO Italy FEE </html:option>
            <html:option value="LEONARDO-NORWAY|LEONARDO_NORWAYInvoiceReportDefinition|">LEONARDO Norway</html:option>
            <html:option value="LEONARDO-NORWAY FEE|LEONARDO_NORWAY_FEEInvoiceReportDefinition|">LEONARDO Norway FEE </html:option>
            <html:option value="LEONARDO-SELEX|LEONARDO_SELEX_File1|Group">LEONARDO Selex</html:option>
            <html:option value="LEONARDO-SELEX FEE|LEONARDO_SELEX_FEEInvoiceReportDefinition|">LEONARDO Selex FEE</html:option>
            <html:option value="ARCHBALD|ARCHBALD_File1|Group">LM Archbald</html:option>
<%--             <html:option value="ARCHBALD-FEE|ARCHBALD_FEEInvoiceReportDefinition|">LM Archbald Fee</html:option> --%>
            <html:option value="ARCHBALD-FEE|LOCKHEED_ARCHBALD_SF_File1|Group">LM Archbald Fee</html:option>
            <html:option value="AERO GS IOR|LOCKHEED_AERO_GS_IOR_File1|Group">LM Aero GS IOR Material Invoice</html:option>
            <html:option value="AERO GS IOR|LOCKHEED_AERO_GS_IOR_File2|Group">LM Aero GS IOR ISF Invoice</html:option>
            <html:option value="AERO GS IOR|LOCKHEED_AERO_GS_IOR_File3|Group">LM Aero GS IOR Material Credit Invoice</html:option>
            <html:option value="AERO GS IOR|LOCKHEED_AERO_GS_IOR_File4|Group">LM Aero GS IOR ISF Credit Invoice</html:option>
            <html:option value="AERO GS FIXED IOR|LOCKHEED_AERO_GS_FIXED_IOR_File1|Group">LM Aero GS Fixed IOR Material Invoice</html:option>
            <html:option value="AERO GS FIXED IOR|LOCKHEED_AERO_GS_FIXED_IOR_File2|Group">LM Aero GS Fixed IOR ISF Invoice</html:option>
            <html:option value="AERO GS FIXED IOR|LOCKHEED_AERO_GS_FIXED_IOR_File3|Group">LM Aero GS Fixed IOR Material Credit Invoice</html:option>
            <html:option value="AERO GS FIXED IOR|LOCKHEED_AERO_GS_FIXED_IOR_File4|Group">LM Aero GS Fixed IOR ISF Credit Invoice</html:option>
            <html:option value="AERO GS FIXED IOR|LOCKHEED_AERO_GS_FIXED_IOR_File5|Group">LM Aero GS Fixed IOR Add Charge Invoice</html:option>
            <html:option value="AERO GS FIXED IOR|LOCKHEED_AERO_GS_FIXED_IOR_File6|Group">LM Aero GS Fixed IOR Add Charge Credit Invoice</html:option>
            <html:option value="AERO GS MAR|LOCKHEED_MAR_COPRODUCER_File1|Group">LM Aero GS Mar Material Invoice</html:option>
            <html:option value="AERO GS MAR|LOCKHEED_MAR_COPRODUCER_File2|Group">LM Aero GS Mar ISF Invoice</html:option>
            <html:option value="AERO GS MAR|LOCKHEED_MAR_COPRODUCER_File3|Group">LM Aero GS Mar Material Credit Invoice</html:option>
            <html:option value="AERO GS MAR|LOCKHEED_MAR_COPRODUCER_File4|Group">LM Aero GS Mar ISF Credit Invoice</html:option>
            <html:option value="AERO GS MAR FIXED|LOCKHEED_MAR_COPRODUCER_File1|Group">LM Aero GS Mar Fixed Material Invoice</html:option>
            <html:option value="AERO GS MAR FIXED|LOCKHEED_MAR_COPRODUCER_File2|Group">LM Aero GS Mar Fixed ISF Invoice</html:option>
            <html:option value="AERO GS MAR FIXED|LOCKHEED_MAR_COPRODUCER_File3|Group">LM Aero GS Mar Fixed Material Credit Invoice</html:option>
            <html:option value="AERO GS MAR FIXED|LOCKHEED_MAR_COPRODUCER_File4|Group">LM Aero GS Mar Fixed ISF Credit Invoice</html:option>
            <html:option value="AERO GS MAR FIXED|LOCKHEED_MAR_COPRODUCER_File6|Group">LM Aero GS Mar Fixed Add Charge Invoice</html:option>
            <html:option value="AERO GS MAR FIXED|LOCKHEED_MAR_COPRODUCER_File7|Group">LM Aero GS Mar Fixed Add Charge Credit Invoice</html:option>
            <html:option value="BALTIMORE|LM_BALTIMORE_File1|Group">LM Baltimore</html:option>
            <html:option value="LM COPRODUCER|LOCKHEED_COPRODUCER_File1|Group">LM CoProducer Material Invoice</html:option>
            <html:option value="LM COPRODUCER|LOCKHEED_COPRODUCER_File2|Group">LM CoProducer ISF Invoice</html:option>
            <html:option value="LM COPRODUCER|LOCKHEED_COPRODUCER_File3|Group">LM CoProducer Material Credit Invoice</html:option>
            <html:option value="LM COPRODUCER|LOCKHEED_COPRODUCER_File4|Group">LM CoProducer ISF Credit Invoice</html:option>
            <html:option value="LM COPROD FIXED|LOCKHEED_COPRODUCER_File1|Group">LM CoProducer Fixed Material Invoice</html:option>
            <html:option value="LM COPROD FIXED|LOCKHEED_COPRODUCER_File2|Group">LM CoProducer Fixed ISF Invoice</html:option>
            <html:option value="LM COPROD FIXED|LOCKHEED_COPRODUCER_File3|Group">LM CoProducer Fixed Material Credit Invoice</html:option>
            <html:option value="LM COPROD FIXED|LOCKHEED_COPRODUCER_File4|Group">LM CoProducer Fixed ISF Credit Invoice</html:option>
            <html:option value="LM COPROD FIXED|LOCKHEED_COPRODUCER_File6|Group">LM CoProducer Fixed Add Charge Invoice</html:option>
            <html:option value="LM COPROD FIXED|LOCKHEED_COPRODUCER_File7|Group">LM CoProducer Fixed Add Charge Credit Invoice</html:option>
            <html:option value="DENVER|DENVER_File1|Group">LM Denver</html:option>
            <html:option value="DENVER-FEE|DENVER_FEEInvoiceReportDefinition|">LM Denver Fee</html:option>
            <html:option value="GRAND PRAIRIE|GRANDPRAIRIE_File1|Group">LM Grand Prairie</html:option>
            <html:option value="GRAND PRAIRIE-FEE|LOCKHEED_GP_SF_File1|Group">LM Grand Prairie Fee</html:option>
            <html:option value="GREENVILLE-FEE|GREENVILLE_FEEInvoiceReportDefinition|">LM Greenville Fee</html:option>
            <html:option value="GREENVILLE-FEE|LOCKHEED_GREENVILLE_SF_File1|Group">LM Greenville Service Fee</html:option>
            <html:option value="GREENVILLE PO|GREENVILLE_PO_File1|Group">LM Greenville PO</html:option>
            <html:option value="GREENVILLE WBS|GREENVILLE_WBS_File1|Group">LM Greenville WBS</html:option>
            <html:option value="JOHNSTOWN|JOHNSTOWN_File1|Group">LM Johnstown</html:option>
            <html:option value="JOHNSTOWN-FEE|JOHNSTOWN_FEEInvoiceReportDefinition|">LM Johnstown Fee</html:option>
            <html:option value="JOHNSTOWN-FEE|LOCKHEED_JOHNSTOWN_SF_File1|Group">LM Johnstown Service Fee</html:option>
            <html:option value="KELLY|KELLY_File1|Group">LM Kelly</html:option>
            <html:option value="KELLY-FEE|KELLY_SFReportDefinition|">LM Kelly Fee</html:option>
            <html:option value="KELLY-FEE|LOCKHEED_KELLY_SF_File1|Group">LM Kelly Service Fee</html:option>
            <html:option value="LA MESA|LA_MESA_File1|Group">LM La Mesa</html:option>
            <html:option value="LA MESA-FEE|LA_MESA_SFReportDefinition|">LM La Mesa Fee</html:option>
            <html:option value="LUFKIN|LUFKIN_File1|Group">LM Lufkin</html:option>
            <html:option value="LUFKIN FEE|LOCKHEED_LUFKIN_SF_File1|Group">LM Lufkin Fee</html:option>
            <html:option value="FORT WORTH|LOCKHEED_FTW_File1|Group">LM Fort Worth (Material)</html:option>
            <html:option value="FORT WORTH|LOCKHEED_FTW_File2|Group">LM Fort Worth (Material Credit)</html:option>
            <html:option value="FORT WORTH|LOCKHEED_FTW_File3|Group">LM Fort Worth (Service Fee)</html:option>
            <html:option value="FORT WORTH|LOCKHEED_FTW_File4|Group">LM Fort Worth (Service Fee Credit)</html:option>
            <html:option value="FORT WORTH|LOCKHEED_FTW_File5|Group">LM Fort Worth (Consigned Credit)</html:option>
            <html:option value="FORT WORTH FIXED|LOCKHEED_FTW_File1|Group">LM Fort Worth Fixed (Material)</html:option>
            <html:option value="FORT WORTH FIXED|LOCKHEED_FTW_File2|Group">LM Fort Worth Fixed (Material Credit)</html:option>
            <html:option value="FORT WORTH FIXED|LOCKHEED_FTW_File5|Group">LM Fort Worth Fixed (Consigned Credit)</html:option>
            <html:option value="FORT WORTH FIXED|LOCKHEED_FTW_File6|Group">LM Fort Worth Fixed (Add Charge)</html:option>
            <html:option value="FORT WORTH FIXED|LOCKHEED_FTW_File7|Group">LM Fort Worth Fixed (Add Charge Credit)</html:option>
            <html:option value="FTW STD FEE|FTW_STD_FEEInvoiceReportDefinition|">LM Fort Worth Standard Fee</html:option>
            <html:option value="MARIETTA|LOCKHEED_MARIETTA_File1|Group">LM Marietta (Material)</html:option>
            <html:option value="MARIETTA|LOCKHEED_MARIETTA_File2|Group">LM Marietta (Material Credit)</html:option>
            <html:option value="MARIETTA|LOCKHEED_MARIETTA_File3|Group">LM Marietta (Service Fee)</html:option>
            <html:option value="MARIETTA|LOCKHEED_MARIETTA_File4|Group">LM Marietta (Service Fee Credit)</html:option>
            <html:option value="MARIETTA|LOCKHEED_MARIETTA_File5|Group">LM Marietta (Consigned Credit)</html:option>
            <html:option value="MARIETTA FIXED|LOCKHEED_MARIETTA_File1|Group">LM Marietta Fixed (Material)</html:option>
            <html:option value="MARIETTA FIXED|LOCKHEED_MARIETTA_File2|Group">LM Marietta Fixed(Material Credit)</html:option>
            <html:option value="MARIETTA FIXED|LOCKHEED_MARIETTA_File5|Group">LM Marietta Fixed(Consigned Credit)</html:option>
            <html:option value="MARIETTA FIXED|LOCKHEED_MARIETTA_File6|Group">LM Marietta Fixed (Add Charge)</html:option>
            <html:option value="MARIETTA FIXED|LOCKHEED_MARIETTA_File7|Group">LM Marietta Fixed (Add Charge Credit)</html:option>
            <html:option value="MAR STD FEE|MAR_STD_FEEInvoiceReportDefinition|">LM Marietta Standard Fee</html:option>
            <html:option value="LM MOORESTOWN|LM_MOORESTOWN_File1|Group">LM Moorestown</html:option>
            <html:option value="MOORESTOWN-FEE|LM_MOORESTOWN_FEEInvoiceReportDefinition|">LM Moorestown Fee</html:option>
            <html:option value="NEWTOWN-SUNNYVALE|NEWTOWNSUNNYVALE_File1|Group">LM Newtown-Sunnyvale</html:option>
            <html:option value="NEWTOWN-SUNNYVALE-FEE|NEWTOWN_SUNNYVALE_FEEInvoiceReportDefinition|">LM Newtown-Sunnyvale Fee</html:option>
            <html:option value="OCALA|OCALA_File1|Group">LM Ocala</html:option>
            <html:option value="OCALA-FEE|LOCKHEED_OCALA_SF_File1|Group">LM Ocala Fee</html:option>
            <html:option value="ORLANDO|ORLANDO_File1|Group">LM Orlando</html:option>
            <html:option value="ORLANDO-FEE|LOCKHEED_ORLANDO_SF_File1|Group">LM Orlando Fee</html:option>
            <html:option value="OWEGO TAXABLE|LM_OWEGO_TAXABLE_File1|Group">LM Owego Taxable</html:option>
            <html:option value="OWEGO NT|LM_OWEGO_NON_TAXABLE_File1|Group">LM Owego Non Taxable</html:option>
			<html:option value="PALMDALE|LOCKHEED_PALMDALE_File1|Group">LM Palmdale (Material)</html:option>
            <html:option value="PALMDALE|LOCKHEED_PALMDALE_File2|Group">LM Palmdale (Material Credit)</html:option>
            <html:option value="PALMDALE|LOCKHEED_PALMDALE_File3|Group">LM Palmdale (Service Fee)</html:option>
            <html:option value="PALMDALE|LOCKHEED_PALMDALE_File4|Group">LM Palmdale (Service Fee Credit)</html:option>
            <html:option value="PALMDALE|LOCKHEED_PALMDALE_File5|Group">LM Palmdale (Consigned Credit)</html:option>
			<html:option value="PALMDALE FIXED|LOCKHEED_PALMDALE_File1|Group">LM Palmdale Fixed (Material)</html:option>
            <html:option value="PALMDALE FIXED|LOCKHEED_PALMDALE_File2|Group">LM Palmdale Fixed (Material Credit)</html:option>
            <html:option value="PALMDALE FIXED|LOCKHEED_PALMDALE_File5|Group">LM Palmdale Fixed (Consigned Credit)</html:option>
            <html:option value="PALMDALE FIXED|LOCKHEED_PALMDALE_File6|Group">LM Palmdale Fixed (Add Charge)</html:option>
            <html:option value="PALMDALE FIXED|LOCKHEED_PALMDALE_File7|Group">LM Palmdale Fixed (Add Charge Credit)</html:option>
            <html:option value="PALMDALE-FEE|PALMDALE_FEEInvoiceReportDefinition|">LM Palmdale Fee</html:option>
            <html:option value="PLM STD FEE|PLM_STD_FEEInvoiceReportDefinition|">LM Palmdale Standard Fee</html:option>
            <html:option value="SANTA BARBARA|SANTA_BARBARA_File1|Group">LM Santa Barbara</html:option>
<%--             <html:option value="SANTA BARBARA-FEE|SANTA_BARBARA_FEEInvoiceReportDefinition|">LM Santa Barbara Fee</html:option>  --%>
            <html:option value="SANTA BARBARA-FEE|LOCKHEED_SANTA_BARBARA_SF_File1|Group">LM Santa Barbara Fee</html:option>
            <html:option value="LM- SAC 1|LM_SIKORSKY_CREDIT_File1|Group">LM Sikorsky Credit Old (XLS)</html:option>
            <html:option value="LM- SAC 1|LM_SIKORSKY_DEBIT_File1|Group">LM Sikorsky Debit Old (XLS)</html:option>
            <html:option value="LM- SAC 2|LM_SAC_CREDIT_File1|Group">LM Sikorsky Credit (XLS)</html:option>
            <html:option value="LM- SAC 2|LM_SAC_DEBIT_File1|Group">LM Sikorsky Debit (XLS)</html:option>
            <html:option value="LM- SAC-CTI|LM_SAC_CTI_File1|Group">LM SAC CTI</html:option>
            <%-- <html:option value="LM- SAC-CTI FEE|LM_SAC_CTI_FEEInvoiceReportDefinition|">LM SAC CTI Fee</html:option> --%>
            <html:option value="LM- SAC-CTI FEE|LOCKHEED_SAC_CTI_SF_File1|Group">LM SAC CTI Fee</html:option>
            <html:option value="LM- SAC-HSI|SAC_HSIInvoiceReportDefinition|">LM SAC HSI</html:option>
            <html:option value="LM- SAC-KEYSTONE|SAC_KEYSTONEInvoiceReportDefinition|">LM SAC Keystone</html:option>
            <html:option value="LM- SAC-KEYSTONE FEE|LM_SAC_KEYSTONE_FEEInvoiceReportDefinition|">LM SAC Keystone Fee</html:option>
            <html:option value="LM- SAC-LIFEPORT|LM_SAC_LIFEPORT_File1|Group">LM SAC Lifeport</html:option>
            <html:option value="LM- SAC-LIFEPORT FEE|LOCKHEED_SAC_LIFEPORT_SF_File1|Group">LM SAC Lifeport Fee</html:option>
            <html:option value="LM- SAC-NORTH ISLAND|SAC_NORTHISLANDInvoiceReportDefinition|">LM SAC North Island</html:option>
            <html:option value="LM- SAC-OWEGO|SAC_OWEGOInvoiceReportDefinition|">LM SAC Owego</html:option>
            <%-- <html:option value="LM- SAC PZL|LM_SAC_PZLInvoiceReportDefinition|">LM SAC PZL</html:option>  --%>
            <html:option value="LM- SAC PZL|LM_SAC_PZLInvoiceReportDefinition|">LM SAC PZL</html:option>
          	<html:option value="LM- SAC PZL|LM_SAC_PZLAddChargeInvoiceReportDefinition|">LM SAC PZL Add Charge</html:option>
          	<html:option value="LM- SAC PZL|LM_SAC_PZLFeeInvoiceReportDefinition|">LM SAC PZL Fee</html:option>
            <html:option value="LM- SAC PZL|LM_SAC_PZLCorrectionInvoiceReportDefinition|">LM SAC PZL Correction</html:option>
            <html:option value="LM- SAC-SESI|SAC_KEYSTONEInvoiceReportDefinition|">LM SAC SESI</html:option>
            <html:option value="LM- SAC-SIKORSKY FEE|LOCKHEED_SAC_SIKORSKY_SF_File1|Group">LM SAC Sikorsky Fee</html:option>
            <html:option value="SYRACUSE">LM Syracuse</html:option>
            <html:option value="LM SYRACUSE TAXABLE|LM_SYRACUSE_TAXABLE_File1|Group">LM Syracuse Taxable</html:option>
            <html:option value="LM SYRACUSE NT|LM_SYRACUSE_NON_TAXABLE_File1|Group">LM Syracuse Non Taxable</html:option>
            <html:option value="TRENTON MAT|TRENTONInvoiceReportDefinition|">LM Trenton</html:option>
            <html:option value="TRENTON|TRENTON_FEEInvoiceReportDefinition|">LM Trenton Fee</html:option>
            <html:option value="TROY|TROY_File1|Group">LM Troy</html:option>
            <html:option value="TROY-FEE|TROY_FEEInvoiceReportDefinition|">LM Troy Fee</html:option>
            <html:option value="TROY-FEE|LOCKHEED_TROY_SF_File1|Group">LM Troy Service Fee</html:option>
            <html:option value="TROY OFFSITE|TROY_OFFSITE_File1|Group">LM Troy Offsite</html:option>
            <html:option value="VALLEY FORGE|VALLEYFORGE_File1|Group">LM Valley Forge</html:option>
            <html:option value="VALLEY FORGE-FEE|VALLEY_FORGE_FEEInvoiceReportDefinition|">LM Valley Forge Fee</html:option>
            <html:option value="VF SPACE|VF_SPACE_File1|Group">LM VF Space</html:option>
            <html:option value="VF SPACE-FEE|VF_SPACE_FEEInvoiceReportDefinition|">LM VF Space Fee</html:option>
            <html:option value="LMCES MONTREAL|LMCES_File1|Group">LMCES MONTREAL</html:option>
            <html:option value="LMCES MONTREAL FEE|LMCES_MONTREAL_FEEInvoiceReportDefinition|">LMCES MONTREAL Fee</html:option>
            <html:option value="LMCES MONTREAL USD|LMCES_File1|Group">LMCES MONTREAL USD</html:option>
            <html:option value="LOCKHEED SDS|LOCKHEED_SDSInvoiceReportDefinition|">Lockheed SDS</html:option>
            <html:option value="MAEET">MAEET</html:option>
            <html:option value="MAGELLAN WREXHAM|MAGELLAN_WREXHAM_File1|Group">MAGELLAN Wrexham</html:option>
            <html:option value="MAGELLAN BOURNEMOUTH|MAGELLAN_BOURNEMOUTH_File1|Group">MAGELLAN Bournemouth</html:option>
            <html:option value="MAGELLAN BOURNEMOUTH FEE|MAGELLAN_BOURNEMOUTH_FEEInvoiceReportDefinition|">MAGELLAN Bournemouth Fee</html:option>
            <html:option value="ROLLSTAMP|ROLLSTAMP_File1|Group">MAGNA Rollstamp</html:option>
            <html:option value="ROLLSTAMP FEE|ROLLSTAMP_FEEInvoiceReportDefinition|">MAGNA Rollstamp Fee</html:option>
            <html:option value="ROLLSTAMP FILTERS|ROLLSTAMP_FILTERS_File1|Group">MAGNA Rollstamp Filters</html:option>
            <html:option value="ROLLSTAMP WASTES|ROLLSTAMP_WASTES_File1|Group">MAGNA Rollstamp Wastes</html:option>
            <html:option value="MERCK|MERCKInvoiceReportDefinition|">MERCK</html:option>
            <html:option value="MERCK FEE|MERCK_FEEInvoiceReportDefinition|">MERCK Fee</html:option>
            <html:option value="MERCK ELKTON FEE|MERCK_ELKTON_FEEInvoiceReportDefinition|">MERCK Elkton Fee</html:option>
            <html:option value="MERCK PALO ALTO FEE|MERCK_PA_FEEInvoiceReportDefinition|">MERCK Palo Alto Fee</html:option>
            <html:option value="MERCURY PLANT 4|MERCURY_MARINE_File1|Group">MERCURY MARINE Plant 4</html:option>
          	<html:option value="MERCURY PLANT 11|MERCURY_MARINE_File1|Group">MERCURY MARINE Plant 11</html:option>
          	<html:option value="MERCURY PLANT 12|MERCURY_MARINE_File1|Group">MERCURY MARINE Plant 12</html:option>
          	<html:option value="MERCURY PLANT 15|MERCURY_MARINE_File1|Group">MERCURY MARINE Plant 15</html:option>
          	<html:option value="MERCURY PLANT 15 FEE|MERCURY_MARINE_FEEInvoiceReportDefinition|">MERCURY MARINE Plant 15 Fee</html:option>
          	<html:option value="MERCURY PLANT 17|MERCURY_MARINE_File1|Group">MERCURY MARINE Plant 17</html:option>
          	<html:option value="MERCURY PLANT 36|MERCURY_MARINE_File1|Group">MERCURY MARINE Plant 36</html:option>
          	<html:option value="MERCURY PLANT 98|MERCURY_MARINE_File1|Group">MERCURY MARINE Plant 98</html:option>
            <html:option value="MA_BASINGSTOKE|MIDDLESEX_BASINGSTOKE_File1|Group">MIDDLESEX AEROSPACE Basingstoke</html:option>
            <html:option value="MILLER">MILLER Brewing</html:option>
            <html:option value="MOOG-BAGUIO CITY|MOOG_BAGUIO_CITY_File1|Group">MOOG Baguio City (Non-Production)</html:option>
            <html:option value="MOOG-BC FIXED|MOOG_BAGUIO_CITY_File1|Group">MOOG Baguio City Fixed (Production)</html:option>
            <html:option value="MOOG-EAST AURORA|MOOG_File1|Group">MOOG East Aurora</html:option>
            <html:option value="MOOG-EA-WO|MOOG_EA_WO_File1|Group">MOOG EA WO</html:option>
            <html:option value="MOOG WOLVERHAMPTON|MOOG_WOLVERHAMPTON_File1|Group">MOOG Wolverhampton (PDF)</html:option>
            <html:option value="MOOG WOLVERHAMPTON|MOOG_WOLVERHAMPTON_File2|Group">MOOG Wolverhampton (XLS)</html:option>
            <html:option value="MORAINE">MORAINE (GM)</html:option>
            <html:option value="MT|MTInvoiceReportDefinition|">MT</html:option>
            <html:option value="MT-SF|MT_SFInvoiceReportDefinition|">MT Fee</html:option>
            <html:option value="NALCO">NALCO</html:option>
            <html:option value="NORD MICRO|NORD_MICROInvoiceReportDefinition|">NORD MICRO</html:option>
            <html:option value="NGC SLC|NG_File1|Group">Northrup Grumman</html:option>
          	<html:option value="NGC SLC FEE|NGFeeReportDefinition|">Northrup Grumman Fee</html:option>
            <html:option value="NAS|NASConsolidatedInvoiceReportDefinition|">Norwegian Air Group</html:option>
            <html:option value="NAS FEE|NORWEGIAN_FEEInvoiceReportDefinition|">Norwegian Air Group Fee</html:option>
            <html:option value="NRG|NRGInvoiceReportDefinition|">NRG</html:option>
            <html:option value="OMMC">OMMC</html:option>
            <html:option value="FORT RUCKER|FORT_RUCKERInvoiceReportDefinition|">ONE STOP ENVIRONMENTAL Ft Rucker</html:option>
            <html:option value="FORT RUCKER FEE|FORT_RUCKER_FEEInvoiceReportDefinition|">ONE STOP ENVIRONMENTAL Ft Rucker Fee</html:option>
            <html:option value="OSH-CAR PT|GM_OSHReportDefinition|">OSH-CAR PT</html:option>
            <html:option value="PCC ADI|PCC_ADI_File1|Group">PCC ADI</html:option>
            <html:option value="PCC ADI FEE|PCC_ADIFeeReportDefinition|">PCC ADI Fee</html:option>
            <html:option value="PCC HELICOMB|PCC_HELICOMB_File1|Group">PCC Helicomb </html:option>
            <html:option value="PCC ROLLMET|PCC_ROLLMET_File2|Group">PCC Rollmet (PDF)</html:option>
            <html:option value="PCC ROLLMET|PCC_ROLLMET_File1|Group">PCC Rollmet (XLS)</html:option>
            <html:option value="PCC ROLLMET FEE|PCC_ROLLMETFeeReportDefinition|">PCC Rollmet Fee</html:option>
            <html:option value="PCC WEAVER|PCC_WEAVER_File1|Group">PCC Weaver </html:option>
            <html:option value="PCC UNI SWAG|PCC_UNI_SWAG_File1|Group">PCC Uni Swag </html:option>
            <html:option value="PCC UNI SWAG FEE|PCC_UNI_SWAGFeeReportDefinition|">PCC Uni Swag Fee</html:option>
            <html:option value="NETMRO_MONTHLY_PPG|NETMRO_MONTHLY_PPGReportDefinition|">PPG</html:option>
            <html:option value="PRISM">PRISM</html:option>
<%--             <html:option value="PWK KALISZ|PWK_Material_File1|Group">PWK Kalisz Material (PDF)</html:option>
			<html:option value="PWK KALISZ|PWK_Material_File2|Group">PWK Kalisz Material (XLS)</html:option>
			<html:option value="PWK KALISZ|PWK_AddCharge_File1|Group">PWK Kalisz Add Charge (PDF)</html:option>
			<html:option value="PWK KALISZ|PWK_AddCharge_File2|Group">PWK Kalisz Add Charge (XLS)</html:option>
			<html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICE_File1|Group">PWT Niepolomice Material (XLS)</html:option>
			<html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICE_File2|">PWT Niepolomice Material (PDF)</html:option>
			<html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICE_AddCharge_File1|Group">PWT Niepolomice Add Charge (XLS)</html:option>
			<html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICE_AddCharge_File2|">PWT Niepolomice Add Charge (PDF)</html:option>
            <html:option value="PWT NIEPOLOMICE FEE|PWTNiepolomiceFeeReportDefinition|">PWT Niepolomice Fee</html:option>  --%>
            <html:option value="PWK KALISZ|PWK_KALISZInvoiceReportDefinition|">PWK Kalisz</html:option>
          	<html:option value="PWK KALISZ|PWK_KALISZAddChargeInvoiceReportDefinition|">PWK Kalisz Add Charge</html:option>
          	<html:option value="PWK KALISZ|PWK_KALISZFeeInvoiceReportDefinition|">PWK Kalisz Fee</html:option>
          	<html:option value="PWK KALISZ|PWK_KALISZCorrectionInvoiceReportDefinition|">PWK Kalisz Correction</html:option>
            <html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICEInvoiceReportDefinition|">PWT Niepolomice</html:option>
          	<html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICEAddChargeInvoiceReportDefinition|">PWT Niepolomice Add Charge</html:option>
          	<html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICEFeeInvoiceReportDefinition|">PWT Niepolomice Fee</html:option>
          	<html:option value="PWT NIEPOLOMICE|PWT_NIEPOLOMICECorrectionInvoiceReportDefinition|">PWT Niepolomice Correction</html:option>
            <html:option value="RAC">RAC Raytheon</html:option>
			<html:option value="EXOSTAR">RAYTHEON Exostar</html:option>
			<html:option value="GOLETAEW">RAYTHEON Goleta EW (Word)</html:option>
            <html:option value="EW|GoletaInvoiceReportDefinition|">RAYTHEON Goleta EW</html:option>
            <html:option value="EW-FEE|EW_FEEInvoiceReportDefinition|">RAYTHON Goleta EW Fee</html:option>
            <html:option value="Raytheon UK|Raytheon_UK_File1|Group">RAYTHEON UK</html:option>
            <html:option value="Raytheon UK FEE|Raytheon_UK_FEEInvoiceReportDefinition|">RAYTHON UK Fee</html:option>
            <html:option value="MEGGITT RDT|MeggittRDTConsolidatedInvoiceReportDefinition|">RDT Meggitt</html:option>
            <html:option value="CARLSBAD|RC_CARLSBAD_File1|Group">ROCKWELL COLLINS Carlsbad (PDF)</html:option>
            <html:option value="CARLSBAD|RC_CARLSBAD_File2|Group">ROCKWELL COLLINS Carlsbad (XLS)</html:option>
            <html:option value="RCS|RC_DOMESTIC_File1|Group">ROCKWELL COLLINS Domestic (PDF)</html:option>
            <html:option value="RCS|RC_DOMESTIC_File2|Group">ROCKWELL COLLINS Domestic (XLS)</html:option>
            <html:option value="CEDAR RAPIDS|RC_CEDAR_RAPIDS_File1|Group">ROCKWELL COLLINS Iowa (PDF)</html:option>
           	<html:option value="CEDAR RAPIDS|RC_CEDAR_RAPIDS_File2|Group">ROCKWELL COLLINS Iowa (XLS)</html:option>
           	<html:option value="TUSTIN|RC_TUSTIN_File1|Group">ROCKWELL COLLINS Irvine (PDF)</html:option>
            <html:option value="TUSTIN|RC_TUSTIN_File2|Group">ROCKWELL COLLINS Irvine (XLS)</html:option>
            <html:option value="RCI MELBOURNE|RC_MELBOURNE_File1|Group">ROCKWELL COLLINS Melbourne (PDF)</html:option>
			<html:option value="RCI MELBOURNE|RC_MELBOURNE_File2|Group">ROCKWELL COLLINS Melbourne (XLS)</html:option>
            <html:option value="MEXICALI|RC_MEXICALI_File1|Group">ROCKWELL COLLINS Mexicali (PDF)</html:option>
            <html:option value="MEXICALI|RC_MEXICALI_File2|Group">ROCKWELL COLLINS Mexicali (XLS)</html:option>
            <html:option value="BINGHAMTON|RC_BINGHAMTON_File1|Group">ROCKWELL COLLINS Sterling (Binghamton) (PDF)</html:option>
            <html:option value="BINGHAMTON|RC_BINGHAMTON_File2|Group">ROCKWELL COLLINS Sterling (Binghamton) (XLS)</html:option>
            <html:option value="WILSONVILLE|RC_WILSONVILLE_File1|Group">ROCKWELL COLLINS Wilsonville (PDF)</html:option>
            <html:option value="WILSONVILLE|RC_WILSONVILLE_File2|Group">ROCKWELL COLLINS Wilsonville (XLS)</html:option>
            <html:option value="RR-SGP|ROLLSROYCEInvoiceReportDefinition|">ROLLS ROYCE</html:option>
            <html:option value="RR-SGP FEE|RR_SGP_FEEInvoiceReportDefinition|">ROLLS ROYCE Fee</html:option>
            <html:option value="RR-SGP-ACHORD|ROLLSROYCEACHORD_File1|Group">ROLLS ROYCE Achord</html:option>
            <html:option value="RR-LACHINE|RRC_File1|Group">ROLLS ROYCE Lachine</html:option>
            <html:option value="RR-LACHINE FEE|RR_LACHINE_FEEInvoiceReportDefinition|">ROLLS ROYCE Lachine Fee</html:option>
            <html:option value="RR-LACHINE-DIRECT|RRC_DIRECT_File1|Group">ROLLS ROYCE Lachine Direct</html:option>
            <html:option value="RR-LACHINE-NEW|RRC_INDIRECT_File1|Group">ROLLS ROYCE Lachine Indirect</html:option>
            <html:option value="RR-SGP-CAB|ROLLSROYCECAB_File1|Group">ROLLS ROYCE Singapore CAB</html:option>
            <html:option value="RR-UK|RR_UK_File1|Group">ROLLS ROYCE UK Sites</html:option>
            <%--<html:option value="SAC PZL|PolandInvoiceReportDefinition|">SAC PZL</html:option>  --%>
            <%-- <html:option value="POLAND|HISPANO_File1|Group">SAFRAN Hispano (PDF)</html:option>
			<html:option value="POLAND|HISPANO_File2|Group">SAFRAN Hispano (XLS)</html:option> --%>
 			<html:option value="POLAND|SAFRAN_HISPANOInvoiceReportDefinition|">SAFRAN Hispano</html:option>
         	<html:option value="POLAND|SAFRAN_HISPANOAddChargeInvoiceReportDefinition|">SAFRAN Hispano Add Charge</html:option>
          	<html:option value="POLAND|SAFRAN_HISPANOFeeInvoiceReportDefinition|">SAFRAN Hispano Fee</html:option>
			<html:option value="POLAND|SAFRAN_HISPANOCorrectionInvoiceReportDefinition|">SAFRAN Hispano Correction</html:option>
			<html:option value="SAC PZL|SAC_PZLInvoiceReportDefinition|">SAC PZL</html:option>
          	<html:option value="SAC PZL|SAC_PZLAddChargeInvoiceReportDefinition|">SAC PZL Add Charge</html:option>
          	<html:option value="SAC PZL|SAC_PZLFeeInvoiceReportDefinition|">SAC PZL Fee</html:option>
          	<html:option value="SAC PZL|SAC_PZLCorrectionInvoiceReportDefinition|">SAC PZL Correction</html:option>
<%-- 			<html:option value="POLAND|PolandInvoiceReportDefinition|">SAFRAN Hispano</html:option>
          	<html:option value="POLAND|PolandAddChargeInvoiceReportDefinition|">SAFRAN Hispano Add Charge</html:option>
          	<html:option value="POLAND FEE|PolandFeeInvoiceReportDefinition|">SAFRAN Hispano Fee</html:option>  --%>
			<html:option value="CHEMPOL II|CHEMPOLInvoiceReportDefinition|">SAIC ChemPOL II</html:option>
			<html:option value="SAIC ChemPOL II|SAIC_ChemPOLFeeReportDefinition|">SAIC ChemPOL II Fee</html:option>
            <html:option value="SAIC">SAIC Fee</html:option>
            <html:option value="SAIC_TF">SAIC DLA TestingFee</html:option>
            <html:option value="POLCHEM|POLCHEMInvoiceReportDefinition|">SAIC Polchem</html:option>
            <html:option value="SALES Original|IAI_SALESInvoiceReportDefinition|">SALES Original</html:option>
            <html:option value="SALES Duplicate|IAI_SALESInvoiceReportDefinition|">SALES Duplicate</html:option>
            <html:option value="SALES-ACM|ACMReportDefinition|">SALES ACM</html:option>
            <html:option value="SALES-ASIA|SALES_ASIAReportDefinition|">SALES ASIA</html:option>
            <html:option value="SALES-ITALY|SALES_ITALYInvoiceReportDefinition|">SALES ITALY</html:option>
            <html:option value="SALES-STS|SALES_STSInvoiceReportDefinition|">SALES STS</html:option>
            <html:option value="SALES-TRIUMPH|TRIUMPHReportDefinition|">SALES TRIUMPH</html:option>
            <html:option value="AUSTIN FEE|AUSTIN_FEEInvoiceReportDefinition|">SAMSUNG Austin Fee</html:option>
            <html:option value="SASCO_HARDWARE|SASCOConsolidatedInvoiceReportDefinition|">SASCO Hardware</html:option>
            <html:option value="SAUER_DANFOSS">SAUER Danfoss</html:option>
            <html:option value="SCHAFER-FREDERICKTOWN">SCHAFER Fredericktown</html:option>
            <html:option value="SCHWEIZER|SCHWEIZERInvoiceReportDefinition|">SCHWEIZER</html:option>
            <html:option value="SEAGATE MALAYSIA|SEAGATE_MALAYSIAInvoiceReportDefinition|">SEAGATE Malaysia</html:option>
            <html:option value="SEAGATE MALAYSIA FEE|SEAGATE_MALAYSIA_FEEInvoiceReportDefinition|">SEAGATE Malaysia Fee</html:option>
            <html:option value="NANJING|NANJING_File1|Group">SIEMENS Nanjing</html:option>
            <html:option value="NANJING FEE|NANJING_FEEInvoiceReportDefinition|">SIEMENS Nanjing Fee</html:option>
            <html:option value="SIEMENS-MONTREAL|SIEMENSMontreal_File1|Group">SIEMENS Montreal</html:option>
            <html:option value="SIEMENS-MONTREAL FEE|SIEMENSMontrealFeeReportDefinition|">SIEMENS Montreal Fee</html:option>
            <html:option value="SPIRIT US - KS|SPIRIT_File1|Group">SPIRIT</html:option>
            <html:option value="SPIRIT US - KS FEE|SPIRITFeeReportDefinition|">SPIRIT Fee</html:option>
            <html:option value="SPIRIT US - TULSA|SPIRIT_US_TULSA_File1|Group">SPIRIT Tulsa</html:option>
            <html:option value="SING_MONTHLY_STA|StAerospaceConsolidatedInvoiceReportDefinition|">ST Aerospace (10106143)</html:option>
            <html:option value="SING_MONTHLY_LUFTHANSA|LUFTHANSAConsolidatedInvoiceReportDefinition|">ST Aerospace LUFTHANSA</html:option>
            <html:option value="SING_MONTHLY_SASCO|SASCOConsolidatedInvoiceReportDefinition|">ST Aerospace SASCO</html:option>
            <html:option value="STD AERO|STD_AEROReportDefinition|">STANDARD Aero</html:option>
            <html:option value="STARK">STARK</html:option>
            <html:option value="SWA">SOUTHWEST AIRLINES</html:option>
            <html:option value="SWA-FEE|SWA_FEEInvoiceReportDefinition|">SOUTHWEST AIRLINES Fee</html:option>
            <html:option value="TASA-CHONBURI|TASA_CHONBURI_File1|Group">TASA-CHONBURI</html:option>
            <html:option value="TASA-CHONBURI FEE|TASA_CHONBURIFeeReportDefinition|">TASA-CHONBURI Fee</html:option>
            <html:option value="TIMKEN">TIMKEN</html:option>
            <html:option value="TOMKINS-AUTO|TOMKINSReportDefinition|">TOMKINS Auto</html:option>
            <html:option value="TRIUMPH MEXICALI|TRIUMPH_MEXICALI_File1|Group">TRIUMPH Mexicali</html:option>
          	<html:option value="TRIUMPH MEXICALI FEE|TRIUMPH_MEXICALI_FEEInvoiceReportDefinition|">TRIUMPH Mexicali Fee</html:option>
          	<html:option value="TRIUMPH MILLEDGEVILLE|TRIUMPH_MILLEDGEVILLE_File1|Group">Triumph Milledgeville</html:option>
            <html:option value="TRIUMPH MILLEDGEVILLE FEE|TRIUMPHMilledgevilleFeeReportDefinition|">Triumph Milledgeville Fee</html:option>
            <html:option value="TRIUMPH STRUCTURES US|TRIUMPH_WICHITA_File1|Group">Triumph Wichita</html:option>
            <html:option value="TRIUMPH STRUCTURES US FEE|TRIUMPH_WICHITA_FEEInvoiceReportDefinition|">Triumph Wichita Fee</html:option>
            <html:option value="UK_ACE|AceConsolidatedInvoiceReportDefinition|">UK Ace</html:option>
            <html:option value="UK_AIRBUS|UKAirbusConsolidatedInvoiceReportDefinition|">UK Airbus</html:option>
            <html:option value="UK_MONTHLY_AIR|AircelleConsolidatedInvoiceReportDefinition|">UK Aircelle</html:option>
            <html:option value="UK_MONTHLY_ATC|ATCConsolidatedInvoiceReportDefinition|">UK ATC</html:option>
            <html:option value="UK_MONTHLY_ATITECH|AtitechConsolidatedInvoiceReportDefinition|">UK ATITECH</html:option>
            <html:option value="BAE_SAMLESBURY|BAESamlesburyConsolidatedInvoiceReportDefinition|">UK BAE SAMLESBURY</html:option>
<%--             <html:option value="BAE_SAMLESBURY_CMS|BAECMSSamlesburyConsolidatedInvoiceReportDefinition|">UK BAE SAMLESBURY CMS</html:option> --%>
            <html:option value="BAE_SAMLESBURY_CMS|UK_BAE_SAMLESBURYInvoiceReportDefinition|">UK BAE SAMLESBURY CMS</html:option>
            <html:option value="BAE_SYSTEMS_MONTHLY|BAESystemsConsolidatedInvoiceReportDefinition|">UK BAE SYSTEMS MONTHLY SALAM</html:option>
            <html:option value="BAE UK|BAE_UKInvoiceReportDefinition|">UK BAE Fee</html:option>
            <html:option value="UK_WEEKLY_BAE|BAEConsolidatedInvoiceReportDefinition|">UK BAE ROCHESTER</html:option>
            <html:option value="UK_MONTHLY_BMI|BMIConsolidatedInvoiceReportDefinition|">UK BMI</html:option>
            <html:option value="UK_BOEING_CO|UKBoeingConsolidatedInvoiceReportDefinition|">UK Boeing</html:option>
            <html:option value="UK_BOND|BondConsolidatedInvoiceReportDefinition|">UK Bond</html:option>
            <html:option value="UK_BRISTOW|UKBristowConsolidatedInvoiceReportDefinition|">UK Bristow</html:option>
            <html:option value="UK_CHC_SCOTIA|ScotiaConsolidatedInvoiceReportDefinition|">UK CHC Scotia</html:option>
            <html:option value="UK_MONTHLY_CTRM|CTRMConsolidatedInvoiceReportDefinition|">UK CTRM</html:option>
            <html:option value="UK_MONTHLY_FINNAIR_ES|FINNAIR_ESConsolidatedInvoiceReportDefinition|">UK Finnair ES</html:option>
            <html:option value="UK_MONTHLY_FINNAIR_TS|FINNAIR_TSConsolidatedInvoiceReportDefinition|">UK Finnair TS</html:option>
            <html:option value="UK_MONTHLY_FINNAIR_TS_FEE|FINNAIR_TS_FEEInvoiceReportDefinition|">UK Finnair TS Fee</html:option>
            <html:option value="UK_MONTHLY_FLYBE|FlyBeConsolidatedInvoiceReportDefinition|">UK FlyBe</html:option>
            <html:option value="UK_MONTHLY_FPT|FPTConsolidatedInvoiceReportDefinition|">UK FPT</html:option>
            <html:option value="UK_GE_ARLE_COURT|ArleCourtConsolidatedInvoiceReportDefinition|">UK GE Arle Court</html:option>
            <html:option value="GE UK BISHOPS|BishopsCleeveConsolidatedInvoiceReportDefinition|">UK GE Bishops Cleeve (CMS)</html:option>
            <html:option value="UK_GE_BISHOPS_CLEEVE|BishopsCleeveConsolidatedInvoiceReportDefinition|">UK GE Bishops Cleeve (Consignment)</html:option>
            <html:option value="GE UK CARDIFF|GE_CARDIFF_File1|Group">UK GE Cardiff</html:option>
            <html:option value="UK_GE_DOWTY_PROPELLERS|DowtyPropellersConsolidatedInvoiceReportDefinition|">UK GE Dowty Propellers</html:option>
            <html:option value="UK_GE_DOWTY_REPAIR|DowtyRepairConsolidatedInvoiceReportDefinition|">UK GE Dowty Repair</html:option>
            <html:option value="GE HAMBLE|GEHambleConsolidatedInvoiceReportDefinition|">UK GE Hamble</html:option>
            <html:option value="GE HUNGARY|GEHungaryConsolidatedInvoiceReportDefinition|">UK GE Hungary</html:option>
            <html:option value="UK_MONTHLY_GKN_LUTON|GKNConsolidatedInvoiceReportDefinition|">UK GKN</html:option>
            <html:option value="GKN-WA|GKN_WAInvoiceReportDefinition|">UK GKN WA</html:option>
            <html:option value="GKN-IOW|GKN_IOWInvoiceReportDefinition|">UK GKN IOW</html:option>
            <html:option value="GKN-YEOVIL|GKN_YEOVILInvoiceReportDefinition|">UK GKN YEOVIL</html:option>
            <html:option value="UK_MONTHLY_GOODRICH|UK_GOODRICHInvoiceReportDefinition|">UK Goodrich</html:option>
            <html:option value="UK_MONTHLY_HONEYWELL_LUTON|LutonConsolidatedInvoiceReportDefinition|">UK HoneyWell Luton</html:option>
            <html:option value="UK_MONTHLY_HONEYWELL_FELTHAM|FelthamConsolidatedInvoiceReportDefinition|">UK HoneyWell Feltham</html:option>
            <html:option value="UK_MONTHLY_A|UKAllConsolidatedInvoiceReportDefinition|">UK JET2COM</html:option>
            <html:option value="UK_MONTHLY_INFLITE|InfliteConsolidatedInvoiceReportDefinition|">UK Inflite</html:option>
            <html:option value="UK_MONTHLY_BROOK|BrookConsolidatedInvoiceReportDefinition|">UK Karman (Brookhouse)</html:option>
            <html:option value="UK_MONTHLY_KILGOUR|KilgourConsolidatedInvoiceReportDefinition|">UK Kilgour</html:option>
            <html:option value="UK_LOGANAIR|LoganairConsolidatedInvoiceReportDefinition|">UK Loganair</html:option>
            <html:option value="UK_MONTHLY_MAGELLAN_STRUCTURES|MagellanConsolidatedInvoiceReportDefinition|">UK MagellanStructures</html:option>
            <html:option value="UK_MONTHLY_MAGELLAN_BOURNEMOUT|BournemoutConsolidatedInvoiceReportDefinition|">UK MagellanBournemout</html:option>
            <html:option value="UK_MONTHLY_MEGGITT|MeggittConsolidatedInvoiceReportDefinition|">UK Meggitt</html:option>
            <html:option value="UK_MONTHLY_MA_CHEMICALS|MA_CHEMICALSConsolidatedInvoiceReportDefinition|">UK Marshall Chemicals</html:option>
            <html:option value="UK_MONTHLY_MA_CHEM_NC|MA_CHEM_NCConsolidatedInvoiceReportDefinition|">UK Marshall Chem NC</html:option>
            <html:option value="UK_MONTHLY_MA_TOOLING|MA_TOOLINGConsolidatedInvoiceReportDefinition|">UK Marshall Tooling</html:option>
            <html:option value="UK_MONTHLY_MA_TOOL_NC|MA_TOOL_NCConsolidatedInvoiceReportDefinition|">UK Marshall Tool NC</html:option>
            <html:option value="UK_MONTHLY_ONTIC|OnticConsolidatedInvoiceReportDefinition|">UK Ontic</html:option>
            <html:option value="REVIMA|REVIMAReportDefinition|">UK REVIMA</html:option>
            <html:option value="UK_MONTHLY_SAS|SASConsolidatedInvoiceReportDefinition|">UK SAS</html:option>
            <html:option value="UK_MONTHLY_THOMSON_AIRWAYS|THOMSON_AIRWAYSConsolidatedInvoiceReportDefinition|">UK Thomson Airways</html:option>
            <html:option value="UK_THOMAS_COOK|COOKConsolidatedInvoiceReportDefinition|">UK Thomas Cook</html:option>
            <html:option value="TRIUMPH_UK|UK_TRIUMPHInvoiceReportDefinition|">UK Triumph</html:option>
            <html:option value="TRIUMPH_UK FEE|UK_TRIUMPH_FEEInvoiceReportDefinition|">UK Triumph Fee</html:option>
            <html:option value="UK_MONTHLY_TITAN|TITANConsolidatedInvoiceReportDefinition|">UK Titan</html:option>
            <html:option value="CAL">UNITED AIRLINES</html:option>
            <html:option value="UMW MALAYSIA|UMW_MALAYSIA_File1|Group">UMW Malaysia</html:option>
          	<html:option value="UMW MALAYSIA FEE|UMW_MALAYSIA_FEEInvoiceReportDefinition|">UMW Malaysia Fee</html:option>
            <html:option value="UNITED_AVIATION_SING|UnitedAviationConsolidatedInvoiceReportDefinition|">United Aviation Consignment Singapore</html:option>
            <html:option value="UTAS-BAMBERG|UTAS_BAMBERG_File1|Group">UTAS Bamberg</html:option>
            <html:option value="UTAS-CHESHIRE|UTAS_CHESHIREInvoiceReportDefinition|">UTAS Cheshire</html:option>
            <html:option value="UTAS-COSPRINGS|UTAS_COSPRINGS_File1|Group">UTAS CoSprings</html:option>
            <html:option value="UTAS-COSPRINGS FEE|UTAS_COSPRINGS_FEEInvoiceReportDefinition|">UTAS CoSprings Fee</html:option>
            <html:option value="UTAS-JAMESTOWN|UTAS_JAMESTOWN_File1|Group">UTAS Jamestown</html:option>
            <html:option value="UTAS-JAMESTOWN FEE|UTAS_JAMESTOWN_FEEInvoiceReportDefinition|">UTAS Jamestown Fee</html:option>
            <html:option value="UTAS MAASTRICHT|MAASTRICHT_File1|Group">UTAS Maastricht</html:option>
            <html:option value="UTAS MAASTRICHT FEE|UTAS_MAASTRICHT_FEEInvoiceReportDefinition|">UTAS Maastricht Fee</html:option>
            <html:option value="UTAS MARSTON GREEN|UTAS_MARSTON_GREEN_File1|Group">UTAS Marston Green</html:option>
            <html:option value="UTAS MARSTON GREEN FEE|UTAS_JAMESTOWN_FEEInvoiceReportDefinition|">UTAS Marston Green Fee</html:option>
            <html:option value="UTAS-MONROE|UTAS_MONROE_File1|Group">UTAS Monroe</html:option>
            <html:option value="UTAS-MONROE FEE|UTAS_MONROE_FEEInvoiceReportDefinition|">UTAS Monroe Fee</html:option>
            <html:option value="UTAS-NEUSS|UTAS_NEUSS_File1|Group">UTAS Neuss</html:option>
            <html:option value="UTAS-NEUSS FEE|UTAS_NEUSS_FEEInvoiceReportDefinition|">UTAS Neuss Fee</html:option>
            <html:option value="UTAS-WOLVERHAMPTON|UTAS_WOLVERHAMPTON_File1|Group">UTAS Wolverhampton (XLS)</html:option>
            <html:option value="UTAS-WOLVERHAMPTON|UTAS_WOLVERHAMPTON_File2|Group">UTAS Wolverhampton (PDF)</html:option>
            <html:option value="UTAS-WOLVERHAMPTON FEE|UTAS_WOLVERHAMPTON_FEEInvoiceReportDefinition|">UTAS Wolverhampton Fee</html:option>
            <html:option value="SINGAPORE|SINGAPOREReportDefinition|">UTC HS Singapore</html:option>
            <html:option value="SINGAPORE-SF|SINGAPORE_SFReportDefinition|">UTC HS Singapore Fee</html:option>
            <html:option value="UTC_HSD">UTC HSD (New England)</html:option>
            <html:option value="HSD-CLAVER|HSD_CLAVERInvoiceReportDefinition|">UTC HSD CLAVER</html:option>
            <html:option value="HSD-ELEKTRONIK|HSD_ELEKTRONIKInvoiceReportDefinition|">UTC HSD ELEKTRONIK</html:option>
            <html:option value="HSD-MARSTON|HSD_MARSTONInvoiceReportDefinition|">UTC HSD MARSTON</html:option>
            <html:option value="HSD-MALAYSIA|HSD_MALAYSIAInvoiceReportDefinition|">UTC HSD MALAYSIA</html:option>
			<html:option value="HSD-REVIMA|HSDREVIMAReportDefinition|">UTC HSD REVIMA</html:option>
            <html:option value="HSD RF|HSDRFReportDefinition|">UTC HSD RF (Ratier-Figeac)</html:option>
            <html:option value="KIDDE_WILSON|KIDDE_WILSONInvoiceReportDefinition|">UTC Kidde Wilson</html:option>
            <html:option value="MARSTON-GREEN|HSD_MARSTONInvoiceReportDefinition|">UTC MARSTON GREEN</html:option>
			<html:option value="PWC|PWCReportDefinition|">UTC PWC</html:option>
			<html:option value="PWC_1012|PWC1012ReportDefinition|">UTC PWC 1012</html:option>
            <html:option value="UTC_PWA">UTC PWA (New England)</html:option>
            <html:option value="PWA-JTT|PWA_JTTInvoiceReportDefinition|">UTC PWA JTT</html:option>
            <html:option value="PWA-WV|UTC_PWA_WV_File1|Group">UTC PWA WV</html:option>
            <html:option value="PWA-SA FEE|PWA_SA_FEEInvoiceReportDefinition|">UTC SA Fee</html:option>
            <html:option value="PWA-SA PAPER|PWA_SA_PAPERInvoiceReportDefinition|">UTC SA Paper</html:option>
 			<html:option value="SAC|SACInvoiceReportDefinition|">UTC SAC</html:option>
            <html:option value="SAC-CHASE FIELD|SAC_CHASE_FIELDInvoiceReportDefinition|">UTC SAC-Chase Field</html:option>
            <html:option value="SAC-CHASE FIELD FEE|SAC_CHASE_FEEInvoiceReportDefinition|">UTC SAC-Chase Field Fee</html:option>
           <%--  <html:option value="UTC_SAC_CTI">UTC SAC CTI</html:option> --%>
            <html:option value="SAC-CTI|SAC_HSIInvoiceReportDefinition|">UTC SAC CTI</html:option>
            <html:option value="SAC-CTI FEE|SAC_CTI_FEEInvoiceReportDefinition|">UTC SAC CTI Fee</html:option>
            <html:option value="SAC-HSI|SAC_HSIInvoiceReportDefinition|">UTC SAC HSI</html:option>
            <html:option value="SAC-KEYSTONE|SAC_KEYSTONEInvoiceReportDefinition|">UTC SAC Keystone</html:option>
            <html:option value="SAC-KEYSTONE FEE|SAC_KEYSTONE_FEEInvoiceReportDefinition|">UTC SAC Keystone Fee</html:option>
            <html:option value="SAC-LIFEPORT|SAC_LIFEPORTInvoiceReportDefinition|">UTC SAC Lifeport</html:option>
            <html:option value="SAC-NORTH ISLAND|SAC_NORTHISLANDInvoiceReportDefinition|">UTC SAC North Island</html:option>
            <html:option value="SAC-OWEGO|SAC_OWEGOInvoiceReportDefinition|">UTC SAC Owego</html:option>
            <html:option value="SAC-SESI|SAC_KEYSTONEInvoiceReportDefinition|">UTC SAC SESI</html:option>
            <html:option value="UTC_SAC">UTC Sikorsky</html:option>
      		<html:option value="PWA SINGAPORE|PWAReportDefinition|">UTC SINGAPORE</html:option>
      		<html:option value="SG CONSUMABLES#1|SG_CONSUMABLES_File1|Group">UTC SG Consumables</html:option>
            <html:option value="SG CONSUMABLES-SF|SG_CONSUMABLES_SFReportDefinition|">UTC SG Consumables Fee</html:option>
            <html:option value="SG NONVENDABLES#1|SG_NONVENDABLES_File1|Group">UTC SG Nonvendables</html:option>
            <html:option value="VIRGIN_ATLANTIC|VIRGIN_ATLANTIC_File1|Group">VIRGIN Atlantic</html:option>
          	<html:option value="VIRGIN_ATLANTIC FEE|VIRGIN_ATLANTIC_FEEInvoiceReportDefinition|">VIRGIN Atlantic Fee</html:option>
            <html:option value="VOLVO">VOLVO NRV</html:option>
            <html:option value="WHIPPANY">WHIPPANY Actuation Systems</html:option>
            <html:option value="WHIPPANY FEE">WHIPPANY Actuation Systems Fee</html:option>
            <html:option value="DUARTE|DUARTE_File1|Group">WOODWARD Duarte</html:option>
            <html:option value="DUARTE FEE|DUARTE_FEEInvoiceReportDefinition|">WOODWARD Duarte Fee</html:option>
            <html:option value="FORT COLLINS|FORT_COLLINS_File1|Group">WOODWARD Fort Collins</html:option>
            <html:option value="SANTA CLARITA|SANTA_CLARITA_File1|Group">WOODWARD Santa Clarita</html:option>
            <html:option value="SANTA CLARITA FEE|SANTA_CLARITA_FEEInvoiceReportDefinition|">WOODWARD Santa Clarita Fee</html:option>
            <html:option value="WOODWARD|WOODWARD_File1|Group">WOODWARD Skokie</html:option>
            <html:option value="WOODWARD FEE|WOODWARD_FEEInvoiceReportDefinition|">WOODWARD Skokie Fee</html:option>
            <html:option value="WSK RZESZOW|WSK_RZESZOWInvoiceReportDefinition|">WSK Rzeszow</html:option>
         	<html:option value="WSK RZESZOW|WSK_RZESZOWAddChargeInvoiceReportDefinition|">WSK Rzeszow Add Charge</html:option>
          	<html:option value="WSK RZESZOW|WSK_RZESZOWFeeInvoiceReportDefinition|">WSK Rzeszow Fee</html:option>
            <html:option value="WSK RZESZOW|WSK_RZESZOWCorrectionInvoiceReportDefinition|">WSK Rzeszow Correction</html:option>
            <html:option value="GRAYCOURT">ZF Graycourt</html:option>
            <html:option value="MARYSVILLE">ZF Marysville</html:option>
            <html:option value="ZFGC MACHINE SERVICE">ZFGC Machine Service</html:option>
            <html:option value="ZODIAC-Thailand|ZODIAC_THAILAND_File1|Group">ZODIAC Thailand</html:option>
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
  <tr>
    <td  width="30%" class="optionTitleBoldRight">
		<fmt:message key="label.invoice"/>:
    </td>
    <td  width="70%" class="optionTitleLeft">
          <html:text property="invoiceNumber" styleId="invoiceNumber" styleClass="inputBox"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="optionTitleLeft">
        <button name="submitButton" id="submitButton" class="inputBtns"
        onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
        onclick= "submitSearchForm();">
		<fmt:message key="invoice.button.submit"/>
        </button>
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
 <div id="hiddenElements" style="display: none;">
 <input type="hidden" name="action" id="action" value="">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
