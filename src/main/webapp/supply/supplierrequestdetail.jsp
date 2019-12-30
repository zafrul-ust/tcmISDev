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
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%@ include file="/common/locale.jsp" %>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/supply/supplierrequestdetail.js"></script>
<script type="text/javascript" src="/js/supply/supplierrequest.js"></script>
<script type="text/javascript" src="/js/common/taxcodes.js"></script>

<script language="JavaScript" type="text/javascript">
<!--

var altCountryId = new Array(
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.countryAbbrev}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.countryAbbrev}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altCountry = new Array(
		<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
		 <c:choose>
		   <c:when test="${status.index > 0}">
		    ,"<c:out value="${status.current.country}"/>"
		   </c:when>
		   <c:otherwise>
		    "<c:out value="${status.current.country}"/>"
		   </c:otherwise>
		  </c:choose>
		</c:forEach>
);

var altState = new Array();
var altStateName = new Array();
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
  <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
  <c:set var="stateCollection" value='${status.current.stateCollection}'/>

  altState["<c:out value="${currentCountry}"/>"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.stateAbbrev}"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.stateAbbrev}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altStateName["<c:out value="${currentCountry}"/>"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"<c:out value="${status1.current.state}"/>"
   </c:when>
   <c:otherwise>
        "<c:out value="${status1.current.state}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

<c:set var="myinventorygroups">
 <fmt:message key="label.myinventorygroups"/>
</c:set>
var defaults = {
	   ops: {id:'',name:'<fmt:message key="label.myentities"/>'},
   	   hub: {id:'',name:'<fmt:message key="label.myhubs"/>'},
   	   inv: {id:'',name:'<tcmis:jsReplace value="${myinventorygroups}"/>'}
   }

var opshubig = [
    <c:forEach var="nouse0" items="${opsHubIgOvBeanCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>',
          coll: [ <c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
    	     	  <c:if test="${ status1.index !=0 }">,</c:if>
        	 	  {  id: '${status1.current.hub}',
        	 	   name: '<tcmis:jsReplace value="${status1.current.hubName}"/>',
        	 	   coll: [ <c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
         				   <c:if test="${ status2.index !=0 }">,</c:if>
	    	    	 	   {
	    	    			id:'${status2.current.inventoryGroup}',
	    	    			name:'<tcmis:jsReplace value="${status2.current.inventoryGroupName}"/>'
	    	    		   }
         		   		   </c:forEach>
        	 	 		  ]
        		   }
        		   </c:forEach>
          	     ]
        }
    </c:forEach>
   ];


// -->
</script>

<title>
<fmt:message key="supplierrequestdetail.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",

legalCompanyNameRequired:"<fmt:message key="label.legalcompanyname"/>",
countryRequired:"<fmt:message key="label.country"/>",
addressRequired:"<fmt:message key="label.address"/>",
cityRequired:"<fmt:message key="label.city"/>",
stateRequired:"<fmt:message key="label.state"/>",
zipRequired:"<fmt:message key="label.zip"/>",
supplierMainPhoneRequired:"<fmt:message key="label.suppliermainphone.required"/>",
paymentTermsRequired:"<fmt:message key="label.atleastonepaymentterms"/>",
typeOfPurchaseRequired:"<fmt:message key="label.typeofpurchase"/>",
qualificationLevelRequired:"<fmt:message key="label.qualificationlevel"/>",
eSupplierRequired:"<fmt:message key="error.esupplier.required"/>",
federalTaxIdRequired:"<fmt:message key="error.federaltaxidorvatnum.required"/>",
mainContactPhoneInteger:"<fmt:message key="error.maincompanyphone.integer"/>",
rejectioncomment:"<fmt:message key="supplierrequestdetail.label.rejectioncomment"/>",
entervalidentity:"<fmt:message key="label.entervalidentity"/>",
nosameinvgrpforpaymenttermsexceptions:"<fmt:message key="label.nosameinvgrpforpaymenttermsexceptions"/>",
dupentity:"<fmt:message key="label.dupopsentity"/>",
all:"<fmt:message key="label.all"/>",
commentesrejection:"<fmt:message key="label.commentesrejection"/>",
sapcoderequired:"<fmt:message key="label.sapvendorcode"/>",
remitto:"<fmt:message key="label.remitto"/> ",
operatingentity:"<fmt:message key="label.operatingentity"/> ",
maximumallowedtext:"<fmt:message key="label.maximumallowedtext"/> ",
federaltaxid:"<fmt:message key="label.federaltaxid"/>",
vatregistration:"<fmt:message key="label.vatregistration"/>",
confirmdelete:"<fmt:message key="label.deletethisdraft"/>",
format:"<fmt:message key="label.format"><fmt:param>###-##-####</fmt:param></fmt:message>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
stateProvinceRequired:"<fmt:message key="label.state"/>/<fmt:message key="label.region"/>"
};

var checkRemitTo = false;

function checkclose() {
if( '${param.uAction}' == 'deletedraft' ) {
	tabId = window.name.substr(0,window.name.length-5); 
	try { parent.parent.closeTabx(tabId);
	} catch(ex){}
	try { parent.closeTabx(tabId);
	} catch(ex){}
	window.close();
}

}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myInit()"><%-- has IE JS bug onresize="myResize()"> --%>

<tcmis:form action="/supplierrequestupdate.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">
<div class="contentArea">
<%--
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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

      <tr>
      <td>
<c:choose>
<c:when test="searchSupplierRequestInputBean != null">
<c:set var="status" value="${searchSupplierRequestInputBean.status}"/>
<c:set var="searchText" value="${searchSupplierRequestInputBean.searchText}"/>
</c:when>
<c:otherwise>
<c:set var="status" value="${param.status}"/>
<c:set var="searchText" value="${param.searchText}"/>
</c:otherwise>
</c:choose>
<input name="status" id="status" type="hidden" value="<c:out value="${status}"/>">
<input name="searchText" id="searchText" type="hidden" value="<c:out value="${searchText}"/>">
<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.returntolist"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">

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
--%>
<!-- Search Option Ends -->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
<c:if test="${! empty tcmISErrors or !empty tcmISError}" >
<c:set var="hasError" value='hasError'/>
  <c:forEach var="err" items="${tcmISErrors}" varStatus="status">
    ${err} <br>
  </c:forEach>
  ${tcmISError}
</c:if>    
</div>
<!-- Error Messages Ends -->
<%--
<c:if test="${supplierAddRequestViewBean != null}" >
--%>
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
<c:choose>
  <c:when test="${supplierAddRequestViewBean == null}">
    <c:set var="supplierRequestType" value="New"/>

    <c:set var="requesterName">
      ${personnelBean.lastName}, ${personnelBean.firstName}
    </c:set>
    <c:set var="requesterPhone">
      ${personnelBean.phone}
    </c:set>
    <c:set var="requesterEmail">
      ${personnelBean.email}
    </c:set>
    <c:set var="opsEntityId" value="${param.opsEntityId}"/>
    <c:set var="requester" value="${param.requester}"/>
    <c:set var="paymentTermApprover" value="${param.paymentTermApprover}"/>
    <c:set var="approver" value="${param.approver}"/>
    <c:set var="supplier" value="${param.supplier}"/>

    <c:set var="supplierRequestId" value="${param.supplierRequestId}"/>
    <c:set var="supplierRequestStatus" value="${param.supplierRequestStatus}"/>
    <c:set var="supplierName" value="${param.supplierName}"/>
    <c:set var="selectedCountry" value='${param.countryAbbrev}'/>
    <c:set var="selectedRemitToCountry" value='${param.remitToCountryAbbrev}'/>
    <c:set var="selectedState" value='${param.stateAbbrev}'/>

    <c:set var="selectedRemitToState" value='${param.remitToStateAbbrev}'/>

    <c:set var="addressLine1" value='${param.addressLine1}'/>
    <c:set var="addressLine2" value='${param.addressLine2}'/>
    <c:set var="addressLine3" value='${param.addressLine3}'/>
    <c:set var="remitToAddressLine1" value='${param.remitToAddressLine1}'/>

    <c:set var="remitToAddressLine2" value='${param.remitToAddressLine2}'/>

    <c:set var="remitToAddressLine3" value='${param.remitToAddressLine3}'/>

    <c:set var="city" value='${param.city}'/>
    <c:set var="zip" value='${param.zip}'/>
    <c:set var="zipPlus" value='${param.zipPlus}'/>
    <c:set var="remitToCity" value='${param.remitToCity}'/>

    <c:set var="remitToZip" value='${param.remitToZip}'/>

    <c:set var="remitToZipPlus" value='${param.remitToZipPlus}'/>

    <c:set var="reasonForRejection" value='${param.reasonForRejection}'/>

    <c:set var="supplierMainPhone" value='${param.supplierMainPhone}'/>
    <c:set var="supplierMainFax" value='${param.supplierMainFax}'/>
    <c:set var="supplierContactFirstName" value='${param.supplierContactFirstName}'/>
    <c:set var="supplierContactLastName" value='${param.supplierContactLastName}'/>
    <c:set var="supplierContactEmail" value='${param.supplierContactEmail}'/>
    <c:set var="supplierContactNickname" value='${param.supplierContactNickname}'/>
    <c:set var="supplierContactPhone" value='${param.supplierContactPhone}'/>
    <c:set var="supplierContactPhoneExt" value='${param.supplierContactPhoneExt}'/>
    <c:set var="supplierContactFax" value='${param.supplierContactFax}'/>
    <c:set var="federalTaxId" value='${param.federalTaxId}'/>
    <c:set var="ssnFlag" value='${param.ssnFlag}'/>
    <c:set var="stateTaxId" value='${param.stateTaxId}'/>

    <c:set var="vatRegistrationNumber" value='${param.vatRegistrationNumber}'/>

    <c:set var="defaultPaymentTerms" value='${param.defaultPaymentTerms}'/>
    <c:if test="${empty defaultPaymentTerms}">
      <c:set var="defaultPaymentTerms" value='Net 45'/>
    </c:if>
    <c:set var="typeOfPurchase" value='${param.typeOfPurchase}'/>
    <c:set var="qualificationLevel" value='${param.qualificationLevel}'/>
    <c:if test="${empty qualificationLevel}">
      <c:set var="qualificationLevel" value='New'/>
    </c:if>
    <c:set var="sapVendorCode" value='${param.sapVendorCode}'/>
    <c:set var="haasAccountNumber" value='${param.haasAccountNumber}'/>
    <c:set var="formerSupplierName" value='${param.formerSupplierName}'/>
    <c:set var="supplierNotes" value='${param.supplierNotes}'/>

<%-- minority info correspondent to bean.
	private String smallBusiness;
	private String minorityBusiness;
	private String disadvantagedBusiness;
	private String womanOwned;
	private String hubWithLocalCertified;
	private String mbeWithLocalCertified;
	private String wbeWithLocalCertified;
	private String sdbWithSbaCertified;
	private String category8aWithSbaCertified;
	private String hubWithSbaCertified;
	private String educationalInstitution;
	private String mbeBlackAmerican;
	private String mbeHispanicAmerican;
	private String mbeAsianPacificAmerican;
	private String mbeNativeAmerican;
	private String mbeAsianIndianAmerican;
	private String mbeOther;
	private String vietNamVeteranOwned;
	private String disabledVeteranOwned;
	private String nonProfitOrganization;
	private String debarred;
	private String iso9001Certified;
	private String as9100Certified;
	private String as9120Certified;

	--%>
    <c:set var="smallBusiness" value='${param.smallBusiness}'/>
    <c:set var="minorityBusiness" value='${param.minorityBusiness}'/>
    <c:set var="disadvantagedBusiness" value='${param.disadvantagedBusiness}'/>
    <c:set var="womanOwned" value='${param.womanOwned}'/>
    <c:set var="hubWithLocalCertified" value='${param.hubWithLocalCertified}'/>
    <c:set var="mbeWithLocalCertified" value='${param.mbeWithLocalCertified}'/>
    <c:set var="wbeWithLocalCertified" value='${param.wbeWithLocalCertified}'/>
    <c:set var="sdbWithSbaCertified" value='${param.sdbWithSbaCertified}'/>
    <c:set var="category8aWithSbaCertified" value='${param.category8aWithSbaCertified}'/>
    <c:set var="hubWithSbaCertified" value='${param.hubWithSbaCertified}'/>
    <c:set var="educationalInstitution" value='${param.educationalInstitution}'/>
    <c:set var="mbeBlackAmerican" value='${param.mbeBlackAmerican}'/>
    <c:set var="mbeHispanicAmerican" value='${param.mbeHispanicAmerican}'/>
    <c:set var="mbeAsianPacificAmerican" value='${param.mbeAsianPacificAmerican}'/>
    <c:set var="mbeNativeAmerican" value='${param.mbeNativeAmerican}'/>
    <c:set var="mbeAsianIndianAmerican" value='${param.mbeAsianIndianAmerican}'/>
    <c:set var="mbeOther" value='${param.mbeOther}'/>
    <c:set var="vietNamVeteranOwned" value='${param.vietNamVeteranOwned}'/>
    <c:set var="disabledVeteranOwned" value='${param.disabledVeteranOwned}'/>
    <c:set var="nonProfitOrganization" value='${param.nonProfitOrganization}'/>
    <c:set var="debarred" value='${param.debarred}'/>
    <c:set var="iso9001Certified" value='${param.iso9001Certified}'/>
    <c:set var="as9100Certified" value='${param.as9100Certified}'/>
    <c:set var="as9120Certified" value='${param.as9120Certified}'/>

  </c:when>
  <c:otherwise>
    <c:set var="supplierRequestType" value='${supplierAddRequestViewBean.supplierRequestType}'/>
    <c:set var="supplierRequestId" value="${supplierAddRequestViewBean.supplierRequestId}"/>
    <c:set var="opsEntityId" value="${supplierAddRequestViewBean.opsEntityId}"/>
    <c:set var="requesterName" value="${supplierAddRequestViewBean.requesterName}"/>
    <c:set var="requesterPhone" value="${supplierAddRequestViewBean.requesterPhone}"/>
    <c:set var="requesterEmail" value="${supplierAddRequestViewBean.requesterEmail}"/>
    <c:set var="requester" value="${supplierAddRequestViewBean.requester}"/>
    <c:set var="paymentTermApprover" value="${supplierAddRequestViewBean.paymentTermApprover}"/>
    <c:set var="approver" value="${supplierAddRequestViewBean.approver}"/>
    <c:set var="supplier" value="${supplierAddRequestViewBean.supplier}"/>


    <c:set var="selectedCountry" value='${supplierAddRequestViewBean.countryAbbrev}'/>
    <c:set var="selectedState" value='${supplierAddRequestViewBean.stateAbbrev}'/>
    <c:set var="selectedRemitToCountry" value='${supplierAddRequestViewBean.remitToCountryAbbrev}'/>

    <c:set var="selectedRemitToState" value='${supplierAddRequestViewBean.remitToStateAbbrev}'/>

    <c:set var="supplierRequestStatus" value="${supplierAddRequestViewBean.supplierRequestStatus}"/>
    <c:set var="supplierName" value="${supplierAddRequestViewBean.supplierName}"/>
    <c:set var="addressLine1" value='${supplierAddRequestViewBean.addressLine1}'/>
    <c:set var="addressLine2" value='${supplierAddRequestViewBean.addressLine2}'/>
    <c:set var="addressLine3" value='${supplierAddRequestViewBean.addressLine3}'/>
    <c:set var="remitToAddressLine1" value='${supplierAddRequestViewBean.remitToAddressLine1}'/>

    <c:set var="remitToAddressLine2" value='${supplierAddRequestViewBean.remitToAddressLine2}'/>

    <c:set var="remitToAddressLine3" value='${supplierAddRequestViewBean.remitToAddressLine3}'/>

    <c:set var="city" value='${supplierAddRequestViewBean.city}'/>
    <c:set var="zip" value='${supplierAddRequestViewBean.zip}'/>
    <c:set var="zipPlus" value='${supplierAddRequestViewBean.zipPlus}'/>
    <c:set var="remitToCity" value='${supplierAddRequestViewBean.remitToCity}'/>

    <c:set var="remitToZip" value='${supplierAddRequestViewBean.remitToZip}'/>

    <c:set var="remitToZipPlus" value='${supplierAddRequestViewBean.remitToZipPlus}'/>

    <c:set var="reasonForRejection" value='${supplierAddRequestViewBean.reasonForRejection}'/>

    <c:set var="supplierMainPhone" value='${supplierAddRequestViewBean.supplierMainPhone}'/>
    <c:set var="supplierMainFax" value='${supplierAddRequestViewBean.supplierMainFax}'/>
    <c:set var="supplierContactFirstName" value='${supplierAddRequestViewBean.supplierContactFirstName}'/>
    <c:set var="supplierContactLastName" value='${supplierAddRequestViewBean.supplierContactLastName}'/>
    <c:set var="supplierContactEmail" value='${supplierAddRequestViewBean.supplierContactEmail}'/>
    <c:set var="supplierContactNickname" value='${supplierAddRequestViewBean.supplierContactNickname}'/>
    <c:set var="supplierContactPhone" value='${supplierAddRequestViewBean.supplierContactPhone}'/>
    <c:set var="supplierContactPhoneExt" value='${supplierAddRequestViewBean.supplierContactPhoneExt}'/>
    <c:set var="supplierContactFax" value='${supplierAddRequestViewBean.supplierContactFax}'/>
    <c:set var="federalTaxId" value='${supplierAddRequestViewBean.federalTaxId}'/>
    <c:set var="ssnFlag" value='${supplierAddRequestViewBean.ssnFlag}'/>
    <c:set var="stateTaxId" value='${supplierAddRequestViewBean.stateTaxId}'/>

    <c:set var="vatRegistrationNumber" value='${supplierAddRequestViewBean.vatRegistrationNumber}'/>

    <c:set var="defaultPaymentTerms" value='${supplierAddRequestViewBean.defaultPaymentTerms}'/>
    <c:if test="${empty defaultPaymentTerms}">
      <c:set var="defaultPaymentTerms" value='Net 45'/>
    </c:if>
    
    <c:set var="typeOfPurchase" value='${supplierAddRequestViewBean.typeOfPurchase}'/>
    <c:set var="qualificationLevel" value='${supplierAddRequestViewBean.qualificationLevel}'/>
    <c:set var="sapVendorCode" value='${supplierAddRequestViewBean.sapVendorCode}'/>
    <c:set var="haasAccountNumber" value='${supplierAddRequestViewBean.haasAccountNumber}'/>
    <c:set var="formerSupplierName" value='${supplierAddRequestViewBean.formerSupplierName}'/>
    <c:set var="supplierNotes" value='${supplierAddRequestViewBean.supplierNotes}'/>
    <c:set var="smallBusiness" value='${supplierAddRequestViewBean.smallBusiness}'/>
    <c:set var="minorityBusiness" value='${supplierAddRequestViewBean.minorityBusiness}'/>
    <c:set var="disadvantagedBusiness" value='${supplierAddRequestViewBean.disadvantagedBusiness}'/>
    <c:set var="womanOwned" value='${supplierAddRequestViewBean.womanOwned}'/>
    <c:set var="hubWithLocalCertified" value='${supplierAddRequestViewBean.hubWithLocalCertified}'/>
    <c:set var="mbeWithLocalCertified" value='${supplierAddRequestViewBean.mbeWithLocalCertified}'/>
    <c:set var="wbeWithLocalCertified" value='${supplierAddRequestViewBean.wbeWithLocalCertified}'/>
    <c:set var="sdbWithSbaCertified" value='${supplierAddRequestViewBean.sdbWithSbaCertified}'/>
    <c:set var="category8aWithSbaCertified" value='${supplierAddRequestViewBean.category8aWithSbaCertified}'/>
    <c:set var="hubWithSbaCertified" value='${supplierAddRequestViewBean.hubWithSbaCertified}'/>
    <c:set var="educationalInstitution" value='${supplierAddRequestViewBean.educationalInstitution}'/>
    <c:set var="mbeBlackAmerican" value='${supplierAddRequestViewBean.mbeBlackAmerican}'/>
    <c:set var="mbeHispanicAmerican" value='${supplierAddRequestViewBean.mbeHispanicAmerican}'/>
    <c:set var="mbeAsianPacificAmerican" value='${supplierAddRequestViewBean.mbeAsianPacificAmerican}'/>
    <c:set var="mbeNativeAmerican" value='${supplierAddRequestViewBean.mbeNativeAmerican}'/>
    <c:set var="mbeAsianIndianAmerican" value='${supplierAddRequestViewBean.mbeAsianIndianAmerican}'/>
    <c:set var="mbeOther" value='${supplierAddRequestViewBean.mbeOther}'/>
    <c:set var="vietNamVeteranOwned" value='${supplierAddRequestViewBean.vietNamVeteranOwned}'/>
    <c:set var="disabledVeteranOwned" value='${supplierAddRequestViewBean.disabledVeteranOwned}'/>
    <c:set var="nonProfitOrganization" value='${supplierAddRequestViewBean.nonProfitOrganization}'/>
    <c:set var="debarred" value='${supplierAddRequestViewBean.debarred}'/>
    <c:set var="iso9001Certified" value='${supplierAddRequestViewBean.iso9001Certified}'/>
    <c:set var="as9100Certified" value='${supplierAddRequestViewBean.as9100Certified}'/>
    <c:set var="as9120Certified" value='${supplierAddRequestViewBean.as9120Certified}'/>
  </c:otherwise>
</c:choose>

<c:if test="${empty selectedCountry}">
      <c:set var="selectedCountry" value='USA'/>
      <tcmis:isCNServer>
			<c:set var="selectedCountry" value='CHN'/>
	  </tcmis:isCNServer> 
</c:if>
<c:if test="${empty selectedRemitToCountry}">
      <c:set var="selectedRemitToCountry" value='USA'/>
      <tcmis:isCNServer>
			<c:set var="selectedRemitToCountry" value='CHN'/>
	  </tcmis:isCNServer>
</c:if>

<!-- newPermission is true only if current user is not the requester and has permission for 'BuyOrder' or 'Sourcing' or 'newSupplier' or 'NewSuppApprover' -->
<c:set var="newPermission" value="false"/>
<!-- financialPermission is true only if current user has permission for 'NewPaymentTerms' and the request is not in 'Draft' status. This controls whether user can approve the request's Payment Term -->
<c:set var="financialPermission" value="false"/>
<c:set var="newfinancialPermission" value="N"/>
<c:set var="allApproved" value="Y"/>
<!-- approvalPermission is true only if current user has permission for 'NewSuppApprover' and the request is not in 'Draft' status. This controls whether user can approve the request to make it official -->
<c:set var="approvalPermission" value="false"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder">
  <c:set var="newPermission" value="true"/>
</tcmis:inventoryGroupPermission>
<tcmis:facilityPermission indicator="true" userGroupId="Sourcing">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<tcmis:facilityPermission indicator="true" userGroupId="newSupplier">
  <c:set var="newPermission" value="true"/>
</tcmis:facilityPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="NewPaymentTerms">
  <c:set var="financialPermission" value="true"/>
</tcmis:opsEntityPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="NewSuppApprover">
  <c:set var="newPermission" value="true"/>
  <c:set var="approvalPermission" value="true"/>
</tcmis:opsEntityPermission>

<c:set var="userIsRequester" value="false" />
<c:choose>
<c:when test="${requester eq personnelBean.personnelId}">
<c:set var="userIsRequester" value="true" />
</c:when>
<c:otherwise>
<c:set var="newPermission" value="false"/>
<c:if test="${ supplierRequestStatus eq 'Draft' }">
    <c:set var="financialPermission" value="false"/>
    <c:set var="approvalPermission" value="false"/>
</c:if>
</c:otherwise>
</c:choose>

<%-- beginning of setting actions --%>
<c:choose>
<c:when test="${supplierRequestType eq 'Change Payment Terms'}">
	<c:if test="${supplierRequestStatus eq 'Draft' && newPermission == 'true'}">
    	<a href="#" onclick="changePaymentTerms();"><fmt:message key="button.submit"/></a>
	</c:if>
	<c:if test="${supplierRequestStatus eq 'Pending Payment Terms' && financialPermission == 'true'}">
		<c:if test="${userIsRequester == 'false'}">
		<span id="pendingspan" style="display:">
    	<a href="#" onclick="approvePaymentTermsDirect();"><fmt:message key="label.approvepaymentterms"/></a> |
    	</span>
    	</c:if>
    	<a href="#" onclick="rejectSupplierRequest();"><fmt:message key="label.reject"/></a>
	</c:if>
</c:when>

<c:otherwise>
<c:set var="preBar" value=""/>

  <c:if test="${(newPermission eq 'true' and supplierRequestStatus == 'Draft') or (financialPermission eq 'true' and supplierRequestStatus == 'Pending Payment Terms') or (approvalPermission eq 'true' and supplierRequestStatus == 'Pending Approval') }" >
	<a href="#" onclick="save();"><fmt:message key="label.save"/></a> 
	<c:set var="preBar" value=" | "/>
  </c:if>
  
  <c:if test="${ newPermission eq 'true' and supplierRequestStatus eq 'Draft'}" >
    ${preBar} <a href="#" onclick="saveSupplierRequest();"><fmt:message key="button.submit"/></a> |
	<a href="#" onclick="deletedraft();"><fmt:message key="label.delete"/></a> 
  </c:if>

<c:if test="${financialPermission == 'true' && supplierRequestStatus == 'Pending Payment Terms'}">
  	<span id="spanHAASTCMMEX" style="display:">${preBar}
  	<c:if test="${userIsRequester == 'false'}">
  	<span id="pendingspan" style="display:"><a href="#" onclick="approvePaymentTermsSupplierRequest();"><fmt:message key="label.approvepaymentterms"/></a> | </span>
  	</c:if>
    <a href="#" onclick="rejectSupplierRequest();"><fmt:message key="label.reject"/></a></span>
</c:if>
<c:if test="${approvalPermission == 'true'}">
  <c:if test="${supplierRequestStatus == 'Pending Approval'}">
    <span id="spanHAASTCMMEX" style="display:">${preBar}
    <c:if test="${userIsRequester == 'false'}">
      <a href="#" onclick="approveSupplierRequest();"><fmt:message key="label.approve"/></a> | 
    </c:if>
    <a href="#" onclick="rejectSupplierRequest();"><fmt:message key="label.reject"/></a></span>
  </c:if>
</c:if>

<c:if test="${userIsRequester == 'true'}">
<c:if test="${supplierRequestStatus == 'Pending Approval'}">
	<span class="optionTitleLeft" id ="customerOverrideRule" style="padding-left:300px; overflow: auto;color:red;font-weight:bold"><fmt:message key="label.supplierreqapprovalperm"/></span>
</c:if>
<c:if test="${supplierRequestStatus == 'Pending Payment Terms'}">
	<span class="optionTitleLeft" id ="customerOverrideRule" style="padding-left:300px; overflow: auto;color:red;font-weight:bold"><fmt:message key="label.supplierreqpmttermsapprovalperm"/></span>
</c:if>
</c:if>

</c:otherwise>
</c:choose>
<%-- end of setting actions --%>

<%-- setting up readonly --%>
<c:set var="readonly" value="true"/>
<c:if test="${ (financialPermission == 'true' or approvalPermission == 'true' or newPermission eq 'true') && supplierRequestStatus eq 'Draft' }" >
	<c:set var="readonly" value="false"/>
</c:if>
    </div>
    <div class="dataContent">
    <table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
<td colspan="4">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.requestor"/>:</td>
<td width="15%">${requesterName}</td>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.phone"/>:</td>
<td width="15%">${requesterPhone}</td>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.email"/>:</td>
<td width="15%">${requesterEmail}</td>
</tr>
<tr>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.requeststatus"/>:</td>
<td width="15%">
	${supplierRequestStatus}
</td>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.requesttype"/>:</td>
<td width="15%">${supplierRequestType}</td>
<td/>
<td/>
</tr>
</table>
</td>
</tr>
<tr>
<td colspan="4">
  <input type="hidden" name="currentUser" id="currentUser" value="${personnelBean.personnelId}"/>
  <input type="hidden" name="requesterName" id="requesterName" value="${requesterName}"/>
  <input type="hidden" name="requesterPhone" id="requesterPhone" value="${requesterPhone}"/>
  <input type="hidden" name="requesterEmail" id="requesterEmail" value="${requesterEmail}"/>
  <input type="hidden" name="requester" id="requester" value="${requester}"/>
  <input type="hidden" name="paymentTermApprover" id="paymentTermApprover" value="${paymentTermApprover}"/>
  <input type="hidden" name="approver" id="approver" value="${approver}"/>
  <input type="hidden" name="supplier" id="supplier" value="${supplier}"/>
  <input type="hidden" name="supplierRequestId" id="supplierRequestId" value="${supplierRequestId}"/>
  <input type="hidden" name="supplierRequestStatus" id="supplierRequestStatus" value="${supplierRequestStatus}"/>

	<%--
<c:if test="${supplierRequestStatus == 'Pending Approval' }">
	<c:if test="${empty supplierAddRequestViewBean.datePaymentTermsApproved}">
<fmt:message key="label.paymenttermsapprovedby"/> ${supplierAddRequestViewBean.paymentTermApproverName}
	</c:if>

	<c:if test="${!empty supplierAddRequestViewBean.datePaymentTermsApproved}">
<fmt:formatDate var="appdate" value="${supplierAddRequestViewBean.datePaymentTermsApproved}" pattern="${dateFormatPattern}"/>
<fmt:message key="label.paymenttermsapprovedbyondate">
	<fmt:param>${supplierAddRequestViewBean.paymentTermApproverName}</fmt:param>
	<fmt:param>${appdate}</fmt:param>
</fmt:message>
	</c:if>
</c:if>
	--%>

<c:if test="${supplierRequestStatus == 'Pending Payment Terms' }">
</c:if>

<c:if test="${supplierRequestStatus == 'Created' }">
</c:if>

<c:if test="${supplierRequestStatus == 'Rejected' }">

	<c:if test="${empty supplierAddRequestViewBean.dateApproved}">
<fmt:message key="label.rejectedby"/> ${supplierAddRequestViewBean.approverName}
	</c:if>
	<c:if test="${!empty supplierAddRequestViewBean.dateApproved}">

<fmt:formatDate var="appdate" value="${supplierAddRequestViewBean.dateApproved}" pattern="${dateFormatPattern}"/>
<fmt:message key="label.rejectedbyondate">
	<fmt:param>${supplierAddRequestViewBean.approverName}</fmt:param>
	<fmt:param>${appdate}</fmt:param>
</fmt:message>
<br/>
<fmt:message key="label.comment"/>: <span style='font-size:12.0pt;color:red'>${reasonForRejection}</span>
	</c:if>
</c:if>

<c:if test="${supplierRequestStatus == 'Created' || supplierRequestStatus == 'Approved'}">

	<c:if test="${empty supplierAddRequestViewBean.dateApproved}">
<fmt:message key="label.approvedby"/> ${supplierAddRequestViewBean.approverName}

		<%--
		<c:if test="${!empty supplierAddRequestViewBean.paymentTermApproverName}">
<br/>
			<c:if test="${empty supplierAddRequestViewBean.datePaymentTermsApproved}">
				<fmt:message key="label.paymenttermsapprovedby"/> ${supplierAddRequestViewBean.paymentTermApproverName}
			</c:if>

			<c:if test="${!empty supplierAddRequestViewBean.datePaymentTermsApproved}">
<fmt:formatDate var="appdate" value="${supplierAddRequestViewBean.datePaymentTermsApproved}" pattern="${dateFormatPattern}"/>
<fmt:message key="label.paymenttermsapprovedbyondate">
	<fmt:param>${supplierAddRequestViewBean.paymentTermApproverName}</fmt:param>
	<fmt:param>${appdate}</fmt:param>
</fmt:message>
			</c:if>

		</c:if>
		--%>

	</c:if>

	<c:if test="${!empty supplierAddRequestViewBean.dateApproved}">
<fmt:formatDate var="appdate" value="${supplierAddRequestViewBean.dateApproved}" pattern="${dateFormatPattern}"/>
<fmt:message key="label.approvedbyondate">
	<fmt:param>${supplierAddRequestViewBean.approverName}</fmt:param>
	<fmt:param>${appdate}</fmt:param>
</fmt:message>


		<%--
		<c:if test="${!empty supplierAddRequestViewBean.paymentTermApproverName}">
<br/>
			<c:if test="${empty supplierAddRequestViewBean.datePaymentTermsApproved}">
				<fmt:message key="label.paymenttermsapprovedby"/> ${supplierAddRequestViewBean.paymentTermApproverName}
			</c:if>

			<c:if test="${!empty supplierAddRequestViewBean.datePaymentTermsApproved}">
<fmt:formatDate var="appdate" value="${supplierAddRequestViewBean.datePaymentTermsApproved}" pattern="${dateFormatPattern}"/>
<fmt:message key="label.paymenttermsapprovedbyondate">
	<fmt:param>${supplierAddRequestViewBean.paymentTermApproverName}</fmt:param>
	<fmt:param>${appdate}</fmt:param>
</fmt:message>
			</c:if>

		</c:if>--%>


	</c:if>
</c:if>

</td>
</tr>

<c:if test="${supplierRequestType eq 'Change Payment Terms'}">
	<c:set var="readonly" value="true"/>
</c:if>

<tr>
<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.address"/></legend>

<table>
<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.legalcompanyname"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if></td>

<td width="15%">
<c:if test="${ readonly == 'true' or supplierRequestType eq 'Activate' or supplierRequestType eq 'Modify'}">
	<input type="hidden" name="supplierName" id="supplierName" value="<tcmis:inputTextReplace value="${supplierName}" />"/>
	<tcmis:inputTextReplace value="${supplierName}" />
</c:if>
<c:if test="${ !(readonly == 'true' or supplierRequestType eq 'Activate' or supplierRequestType eq 'Modify') }">
	<input type="text" class="inputBox" name="supplierName" id="supplierName" value="<tcmis:inputTextReplace value="${supplierName}" />" size="40" maxlength="50"/>
</c:if>
</td>

<td width="8%"></td>

<td width="15%">&nbsp;</td>

</tr>


<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.address"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine1" id="addressLine1" value="<tcmis:inputTextReplace value="${addressLine1}" />"/>
	<tcmis:inputTextReplace value="${addressLine1}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine1" id="addressLine1" value="<tcmis:inputTextReplace value="${addressLine1}" />" size="30" maxlength="40"/>
</c:if>
</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.city"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if></td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="city" id="city" value="<tcmis:inputTextReplace value="${city}" />"/>
	${city}
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="city" id="city" value="<tcmis:inputTextReplace value="${city}" />" size="20" maxlength="30"/>
</c:if>

</td>

</tr>



<tr>

<td width="8%"></td>

<td width="15%">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine2" id="addressLine2" value="<tcmis:inputTextReplace value="${addressLine2}" />"/>
	<tcmis:inputTextReplace value="${addressLine2}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine2" id="addressLine2" value="<tcmis:inputTextReplace value="${addressLine2}" />" size="30" maxlength="40"/>
</c:if>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.state"/>/<fmt:message key="label.province"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="stateAbbrev" id="stateAbbrev" value="${selectedState}"/>
	${selectedState}
</c:if>

<c:if test="${ readonly != 'true' }">
          <select name="stateAbbrev" id="stateAbbrev" class="selectBox">
          </select>
</c:if>

</td>

</tr>



<tr>

<td width="8%"></td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine3" id="addressLine3" value="<tcmis:inputTextReplace value="${addressLine3}" />"/>
	${addressLine3}
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine3" id="addressLine3" value="<tcmis:inputTextReplace value="${addressLine3}" />" size="30" maxlength="40"/>
</c:if>
</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.zip"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="zip" id="zip" value="<tcmis:inputTextReplace value="${zip}" />"/>
	<tcmis:inputTextReplace value="${zip}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="zip" id="zip" value="<tcmis:inputTextReplace value="${zip}" />" size="5" maxlength="10"/>
</c:if>

<span id="zipPlusSpan" style="">
<c:if test="${ readonly == 'true' }">
    <input type="hidden" class="inputBox" name="zipPlus" id="zipPlus" value="<tcmis:inputTextReplace value="${zipPlus}" />"/>
    -<tcmis:inputTextReplace value="${zipPlus}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	-<input type="text" class="inputBox" name="zipPlus" id="zipPlus" value="<tcmis:inputTextReplace value="${zipPlus}" />" size="5" maxlength="4"/>
</c:if>
</span>
</td>
</tr>



<tr>

<td/><td/>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.country"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">


<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="countryAbbrev" id="countryAbbrev" value="${selectedCountry}"/>
	${selectedCountry}
</c:if>
<c:if test="${ readonly != 'true' }">
          <select name="countryAbbrev" id="countryAbbrev" class="selectBox" onchange="countryChanged();">
          </select>
</c:if>


</td>

</tr>




</table>

</fieldset>
</td>
</tr>
<c:if test="${supplierRequestType ne 'Change Payment Terms' and supplierRequestType ne 'Activate' and supplierRequestType ne 'Modify'}">

<script language="JavaScript" type="text/javascript">
<!--
checkRemitTo = true;
// -->
</script>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.remitto"/><c:if test="${ readonly != 'true' }">&#150;&#150;&#150;&#150;&#150;&#150;<input name="sameAsAbove" id="sameAsAbove" type="checkbox" onclick="copyFromAbove()"/><fmt:message key="label.sameasabove"/></c:if></legend>
<table>

<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.address"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="remitToAddressLine1" id="remitToAddressLine1" value="<tcmis:inputTextReplace value="${remitToAddressLine1}" />"/>
	${remitToAddressLine1}
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="remitToAddressLine1" id="remitToAddressLine1" value="<tcmis:inputTextReplace value="${remitToAddressLine1}" />" size="30" maxlength="40"/>
</c:if>
</td>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.city"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="remitToCity" id="remitToCity" value="<tcmis:inputTextReplace value="${remitToCity}" />"/>
	<tcmis:inputTextReplace value="${remitToCity}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="remitToCity" id="remitToCity" value="<tcmis:inputTextReplace value="${remitToCity}" />" size="20" maxlength="30"/>
</c:if>

</td>

</tr>



<tr>

<td width="8%"></td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="remitToAddressLine2" id="remitToAddressLine2" value="<tcmis:inputTextReplace value="${remitToAddressLine2}" />"/>
	<tcmis:inputTextReplace value="${remitToAddressLine2}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="remitToAddressLine2" id="remitToAddressLine2" value="<tcmis:inputTextReplace value="${remitToAddressLine2}" />" size="30" maxlength="40"/>
</c:if>
</td>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.state"/>/<fmt:message key="label.province"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="remitToStateAbbrev" id="remitToStateAbbrev" value="${selectedRemitToState}"/>
	${selectedRemitToState}
</c:if>

<c:if test="${ readonly != 'true' }">
          <select name="remitToStateAbbrev" id="remitToStateAbbrev" class="selectBox">
          </select>

</c:if>
</td>

</tr>



<tr>

<td width="8%"></td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="remitToAddressLine3" id="remitToAddressLine3" value="<tcmis:inputTextReplace value="${remitToAddressLine3}" />"/>
	<tcmis:inputTextReplace value="${remitToAddressLine3}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="remitToAddressLine3" id="remitToAddressLine3" value="<tcmis:inputTextReplace value="${remitToAddressLine3}" />" size="30" maxlength="40"/>
</c:if>
</td>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.zip"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="remitToZip" id="remitToZip" value="<tcmis:inputTextReplace value="${remitToZip}" />"/>
	<tcmis:inputTextReplace value="${remitToZip}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="remitToZip" id="remitToZip" value="<tcmis:inputTextReplace value="${remitToZip}" />" size="5" maxlength="10"/>
</c:if>

<span id="remitToZipPlusSpan" style="">
<c:if test="${ readonly == 'true' }">
    <input type="hidden" class="inputBox" name="remitToZipPlus" id="remitToZipPlus" value="<tcmis:inputTextReplace value="${remitToZipPlus}" />"/>
    -<tcmis:inputTextReplace value="${remitToZipPlus}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	-<input type="text" class="inputBox" name="remitToZipPlus" id="remitToZipPlus" value="<tcmis:inputTextReplace value="${remitToZipPlus}" />" size="5" maxlength="4"/>
</c:if>
</span>
</tr>


<tr>
<td/><td/>
<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.country"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="remitToCountryAbbrev" id="remitToCountryAbbrev" value="${selectedRemitToCountry}"/>
	${selectedRemitToCountry}
</c:if>
<c:if test="${ readonly != 'true' }">
          <select name="remitToCountryAbbrev" id="remitToCountryAbbrev" class="selectBox" onchange="remitCountryChanged();">
          </select>
</c:if>

</td>

</tr>


</table>

</fieldset>
</td>
</tr>

</c:if>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.contactinfo"/></legend>
<table>
<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.mainphone"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierMainPhone" id="supplierMainPhone" value="<tcmis:inputTextReplace value="${supplierMainPhone}" />"/>
	<tcmis:inputTextReplace value="${supplierMainPhone}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierMainPhone" id="supplierMainPhone" value="<tcmis:inputTextReplace value="${supplierMainPhone}" />" size="15"/>
</c:if>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.mainfax"/>:</td>

<td width="15%">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierMainFax" id="supplierMainFax" value="<tcmis:inputTextReplace value="${supplierMainFax}" />"/>
	<tcmis:inputTextReplace value="${supplierMainFax}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierMainFax" id="supplierMainFax" value="<tcmis:inputTextReplace value="${supplierMainFax}" />" size="15"/>
</c:if>

</td>

</tr>



<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.contactfirstname"/>:</td>

<td width="15%" >
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierContactFirstName" id="supplierContactFirstName" value="<tcmis:inputTextReplace value="${supplierContactFirstName}" />"/>
	<tcmis:inputTextReplace value="${supplierContactFirstName}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierContactFirstName" id="supplierContactFirstName" value="<tcmis:inputTextReplace value="${supplierContactFirstName}" />" size="20"/>
</c:if>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.contactemail"/>:</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierContactEmail" id="supplierContactEmail" value="<tcmis:inputTextReplace value="${supplierContactEmail}" />"/>
	<tcmis:inputTextReplace value="${supplierContactEmail}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierContactEmail" id="supplierContactEmail" value="<tcmis:inputTextReplace value="${supplierContactEmail}" />" size="20"/>
</c:if>

</td>

</tr>



<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.contactlastname"/>:</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierContactLastName" id="supplierContactLastName" value="<tcmis:inputTextReplace value="${supplierContactLastName}" />"/>
	<tcmis:inputTextReplace value="${supplierContactLastName}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierContactLastName" id="supplierContactLastName" value="<tcmis:inputTextReplace value="${supplierContactLastName}" />" size="20"/>
</c:if>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.contactphone"/>:</td>

<td width="15%" class="optionTitleBoldLeft">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierContactPhone" id="supplierContactPhone" value="<tcmis:inputTextReplace value="${supplierContactPhone}" />"/>
	<tcmis:inputTextReplace value="${supplierContactPhone}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierContactPhone" id="supplierContactPhone" value="<tcmis:inputTextReplace value="${supplierContactPhone}" />" size="15"/>
</c:if>
&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.ext"/>:
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierContactPhoneExt" id="supplierContactPhoneExt" value="<tcmis:inputTextReplace value="${supplierContactPhoneExt}" />"/>
	<tcmis:inputTextReplace value="${supplierContactPhoneExt}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierContactPhoneExt" id="supplierContactPhoneExt" value="<tcmis:inputTextReplace value="${supplierContactPhoneExt}" />" size="3"/>
</c:if>


</td>

</tr>



<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.contactnickname"/>:</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierContactNickname" id="supplierContactNickname" value="<tcmis:inputTextReplace value="${supplierContactNickname}" />"/>
	<tcmis:inputTextReplace value="${supplierContactNickname}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierContactNickname" id="supplierContactNickname" value="<tcmis:inputTextReplace value="${supplierContactNickname}" />" size="20"/>
</c:if>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.contactfax"/>:</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="supplierContactFax" id="supplierContactFax" value="<tcmis:inputTextReplace value="${supplierContactFax}" />"/>
	<tcmis:inputTextReplace value="${supplierContactFax}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="supplierContactFax" id="supplierContactFax" value="<tcmis:inputTextReplace value="${supplierContactFax}" />" size="15"/>
</c:if>

</td>

</tr>

</table>

</fieldset>
</td>
</tr>
<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.businessinfo"/></legend>
<table>
<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.federaltaxid"/>:<br>(<fmt:message key="label.or"/> <fmt:message key="label.ownerssn"/>)</td>

<td width="15%">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="federalTaxId" id="federalTaxId" value="<tcmis:inputTextReplace value="${federalTaxId}" />"/>
	<tcmis:inputTextReplace value="${federalTaxId}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="federalTaxId" id="federalTaxId" value="<tcmis:inputTextReplace value="${federalTaxId}" />" maxlength="16" size="20"/>
</c:if>
<input type="checkbox" class="radioBtns" name="ssnFlag" id="ssnFlag" value="${ssnFlag}" <c:if test="${ssnFlag eq 'Y' }">checked</c:if> <c:if test="${readonly eq 'true'}">disabled</c:if> />SSN
</td>


<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.vatregistration"/>:</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="vatRegistrationNumber" id="vatRegistrationNumber" value="<tcmis:inputTextReplace value="${vatRegistrationNumber}" />"/>
	<tcmis:inputTextReplace value="${vatRegistrationNumber}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="vatRegistrationNumber" id="vatRegistrationNumber" value="<tcmis:inputTextReplace value="${vatRegistrationNumber}" />" maxlength="16" size="20"/>
</c:if>


</td>

</tr>



<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.statetaxid"/>:</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="stateTaxId" id="stateTaxId" value="<tcmis:inputTextReplace value="${stateTaxId}" />"/>
	<tcmis:inputTextReplace value="${stateTaxId}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="stateTaxId" id="stateTaxId" value="<tcmis:inputTextReplace value="${stateTaxId}" />" size="25"/>
</c:if>


</td>
<%--
<td width="8%" class="optionTitleBoldRight">
<fmt:message key="label.operatingentity"/>:<c:if test="${ supplierRequestType == 'New' || supplierRequestType == 'Activate' || supplierRequestType == 'Modify' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" class="optionTitleBoldLeft">
<c:if test="${ readonly == 'true' }">
 <input type="hidden" name="opsEntityId" id="opsEntityId" value="${opsEntityId}"/>
 <c:forEach var="opsEntityIdBean" items="${vvOperatingEntity}">
   <c:if test="${opsEntityId eq opsEntityIdBean.opsEntityId}">
	${opsEntityIdBean.operatingEntityName}
   </c:if>
 </c:forEach>
</c:if>
<c:if test="${ readonly != 'true' }">
	<select name="opsEntityId" id="opsEntityId" class="selectBox">
	<option value=""><fmt:message key="label.pleaseselect"/></option>
	<c:forEach var="opsEntityIdBean" items="${vvOperatingEntity}">
		<option value="${opsEntityIdBean.opsEntityId}" <c:if test="${opsEntityId eq opsEntityIdBean.opsEntityId}">selected</c:if>>${opsEntityIdBean.operatingEntityName}</option>
	</c:forEach>
	</select>
</c:if>
</td>
--%>

</tr>

<%-- 
<tr>

<td width="8%" colspan="4" class="optionTitleBoldRight"><fmt:message key="label.defaultpaymentterms"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>
<tr>
<td width="15%"  colspan="4">
<c:set var="showPaymentTerms" value="false"/>
<c:if test="${ ( supplierRequestStatus eq 'Draft' or supplierRequestStatus == 'Pending Payment Terms') and (newPermission == 'true' or financialPermission == 'true' ) }">
	<c:set var="showPaymentTerms" value="true"/>
</c:if>
<c:if test="${supplierRequestType eq 'Change Payment Terms' && supplierRequestStatus eq 'Pending Payment Terms' && financialPermission == 'false'}">
	<c:set var="showPaymentTerms" value="false"/>
</c:if>
<c:if test="${supplierRequestType eq 'Modify' && supplierRequestStatus eq 'Draft' && newPermission == 'true'}">
	<c:set var="showPaymentTerms" value="true"/>
</c:if>

<c:if test="${ showPaymentTerms eq 'true' }">
<html:select property="defaultPaymentTerms" styleClass="selectBox" styleId="defaultPaymentTerms" value="${defaultPaymentTerms}">
  <html:options collection="vvPaymentTermsBeanCollection" property="paymentTerms" labelProperty="paymentTerms"/>
</html:select>
<br/><fmt:message key="supplierrequestdetail.label.standardtermsmessage"/>
</c:if>

<c:if test="${ showPaymentTerms eq 'false' }">
	${defaultPaymentTerms}
    <input type="hidden" name="defaultPaymentTerms" id="defaultPaymentTerms" value="${defaultPaymentTerms}"/>
</c:if>
<c:if test="${empty param.oriDefaultPaymentTerms}">
    <c:set var="oriDefaultPaymentTerms" value='${param.defaultPaymentTerms}'/>
</c:if>
<c:if test="${!empty param.oriDefaultPaymentTerms}">
    <c:set var="oriDefaultPaymentTerms" value='${param.oriDefaultPaymentTerms}'/>
</c:if>
    <input type="hidden" name="oriDefaultPaymentTerms" id="oriDefaultPaymentTerms" value="${oriDefaultPaymentTerms}"/>
    <input type="hidden" name="originalPaymentTerms" id="originalPaymentTerms" value="${oriDefaultPaymentTerms}"/>

</td>

</tr>


--%>

<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.typeofpurchase"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="typeOfPurchase" id="typeOfPurchase" value="${typeOfPurchase}"/>
	${typeOfPurchase}
</c:if>
<c:if test="${ readonly != 'true' }">
<select name="typeOfPurchase" id="typeOfPurchase" class="selectBox">
<c:forEach var="bean" items="${vvTypeOfPurchase}" varStatus="status">
	<option value="${bean.typeOfPurchase}" <c:if test="${ bean.typeOfPurchase eq typeOfPurchase }">selected</c:if> ><fmt:message key="${bean.typeOfPurchaseId}"/></option>
</c:forEach>
</select>
</c:if>



</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.qualificationlevel"/>:<br/><fmt:message key="label.supplierrating"/><c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="qualificationLevel" id="qualificationLevel" value="${qualificationLevel}"/>
	${qualificationLevel}
</c:if>
<c:if test="${ readonly != 'true' }">
<html:select property="qualificationLevel" styleClass="selectBox" styleId="qualificationLevel" value="${qualificationLevel}">

  <html:options collection="vvQualificationLevelBeanCollection" property="qualificationLevel" labelProperty="qualificationLevelDesc"/>

</html:select>
</c:if>



</td>


</tr>
<tr>
<td colspan="4">
<br>
<fmt:message key="label.paymentterms"/>
<c:set var="editpayment" value="false"/> 
<c:if test="${ (financialPermission == 'true' or approvalPermission == 'true' or newPermission == 'true') and supplierRequestStatus == 'Draft' }" >
	<c:set var="editpayment" value="true"/>	
</c:if>
<c:if test="${ (financialPermission == 'true' or approvalPermission == 'true') and (supplierRequestStatus == 'Pending Payment Terms' or supplierRequestStatus == 'Pending Approval') }" >
	<c:set var="editpayment" value="true"/>	
</c:if>

<c:if test="${editpayment == 'true' }">
&#150;&#150;&#150;&#150;&#150;&#150; <span id="newPaymentAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewPaymentLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newPaymentRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removePaymentLine()"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>

<div id="SupplierAddPaymentTermsBean" style="height:150px;width:99%;display: none;" ></div>
<br>
<fmt:message key="label.paymenttermsexceptions"/>
<c:if test="${editpayment == 'true'}">
 &#150;&#150;&#150;&#150;&#150;&#150;  
<span id="newIgExcAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewIgExcLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newIgExcRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removeIgExcLine()"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>
<div id="SupplierAddExcPaymentTermsBean" style="height:150px;width:99%;display: none;" ></div>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
// common to all grid
var pageReadOnly = true;
<c:set var="gp" value="N"/>
<c:if test="${editpayment == 'true'}">
	<c:set var="gp" value="Y"/>
	pageReadOnly = false;
</c:if>

function showremovelines() {
	   try
	   {
		if(	paymentGrid.getRowsNum() != 0 )
			$('newPaymentRemoveLine').style["display"] = "";
		if( igExcGrid.getRowsNum() != 0 ) 
			$('newIgExcRemoveLine').style["display"] = "";
	}
	    catch (ex2)
	    {

	    }
	}
// payment term grid

var opsEntityId = new Array();
var opsEntityIdArr = new Array();

for( i=0;i < opshubig.length; i++) { 
	opsEntityIdArr[i] = {text:opshubig[i].name,value:opshubig[i].id}
	//excOpsEntityIdArr[i] = {text:opshubig[i].name,value:opshubig[i].id}
}


<c:set var="sep" value=""/>
var paymentTerms = new Array(
		<c:forEach var="paybean" items="${vvPaymentTermsBeanCollection}" varStatus="status">
			<c:if test="${status.current.prepayTerms != 'Y'}">
                ${sep} {text:'${status.current.paymentTerms}',value:'${status.current.paymentTerms}'}
                <c:set var="sep" value=","/>
         	</c:if>
		</c:forEach>
);

var paymentConfig = {
		divName:'SupplierAddPaymentTermsBean', // the div id to contain the grid of the data.
		beanData:'paymentData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'paymentGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'paymentconfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:paymentSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};
//  igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
//  paymentGrid.parentFormOnSubmit(); //prepare grid for data sending
var paymentRowId = null;
var paymentrowids = new Array();

function buildNewOpsValudset() {
	var newItemIdArray = new Array();
	for( i=0;i < opsEntityIdArr.length; i++) 
		newItemIdArray[opsEntityIdArr[i].value] = {text:opsEntityIdArr[i].text,value:opsEntityIdArr[i].value};

//	for( rowid = 1; rowid <= ind; rowid++ ) {
	for( rowid in paymentrowids ) {
		val = gridCellValue(paymentGrid,rowid,"opsEntityId");
		if( val )
			delete( newItemIdArray[val] ) ;
	}
	var valArray = new Array();
	for( v in newItemIdArray )
		valArray[valArray.length] = newItemIdArray[v];
	return valArray;
}

function loadpaymentrowids() {
	var up = paymentData.rows.length
	for(i=1;i<= up; i++)
		paymentrowids[""+i] = i;
}

function removePaymentLine() {
	paymentGrid.deleteRow(paymentRowId);
	delete( paymentrowids[paymentRowId] ) ;
	if( paymentGrid.getRowsNum() == 0 ) 
		$('newPaymentRemoveLine').style["display"] = "none";
	return ;
}

function addNewPaymentLine(rowKey,orderQuantityRule) {

// if( !$v('opsEntityId') ) {
//	  alert(messagesData.pleaseselect + " : " + messagesData.operatingentity );
//	  return ;
// }
 var rowid = (new Date()).valueOf();
 ind = paymentGrid.getRowsNum();
//	charges[rowKey].rowidd = thisrow.idd;
	  
	  count = 0 ;
	var thisrow = paymentGrid.addRow(rowid,"",ind);
	  opsEntityId[ rowid ] = buildNewOpsValudset();
    alertthis = true;
    
      newStatus[rowid] = new Array( { value:'New',text:'New'} ) ;
	  paymentGrid.cells(rowid, count++).setValue('Y');
	  paymentGrid.cells(rowid, count++).setValue('Y');
	  paymentGrid.cells(rowid, count++).setValue('N');
// opsentityid
	  paymentGrid.cells(rowid, count++).setValue(opsEntityId[ rowid ][0].value);
	  paymentGrid.cells(rowid, count++).setValue('Net 45');
	  paymentGrid.cells(rowid, count++).setValue('');
	  paymentGrid.cells(rowid, count++).setValue('');
	  paymentGrid.cells(rowid, count++).setValue('New');
	  paymentGrid.cells(rowid, count++).setValue('');
	  paymentGrid.cells(rowid, count++).setValue('');
	  paymentGrid.cells(rowid, count++).setValue('');
	  paymentGrid.cells(rowid, count++).setValue($v('supplierRequestId'));
	  paymentGrid.cells(rowid, count++).setValue($v('supplier'));

	  paymentrowids[""+rowid] = rowid;
	  paymentRowId = rowid;
	  paymentGrid.selectRow(paymentGrid.getRowIndex(rowid));
	  $('newPaymentRemoveLine').style.display="";
//	  paymentCountryChanged(rowid,"columnName");
}

function paymentSelectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 paymentRowId = rowId0;
 
} //end of method
/*
function paymentOpsChanged(rowId,columnId,invval) {
	
	  var selectedOps = gridCellValue(paymentGrid,rowId,columnId);
	  var inv = $("inventoryGroup"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewIgValudset(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.all,"", "inventoryGroup"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "inventoryGroup"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}
*/
var defaultops = '';

function setdefaultops() {

	for( i=0;i < opshubig.length; i++) { 
		defaultops = opshubig[i].id;
		break;
	}
//	for( i=0;i < opshubig.length; i++) { 
//		opsEntityIdArr[i] = {text:opshubig[i].name,value:opshubig[i].id}
//		excOpsEntityIdArr[i] = {text:opshubig[i].name,value:opshubig[i].id}
//	}
//	$('opsEntityId').value = defaultops;
//	$('excOpsEntityId').value = defaultops;
	
}

var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"opsEntityId":true,"newStatus":true,"excOpsEntityId":true};

var newStatus = new  Array();

var newStatusOnly = new  Array( { value:'New',text:'New'} ) ;

var newStatusVal = new  Array({text:'<fmt:message key="label.active"/>',value:'Active'},
        {text:'<fmt:message key="label.inactive"/>',value:'Inactive'});

<c:forEach var="bean" items="${PaymentColl}" varStatus="status">
	<c:choose>
  		<c:when test="${bean.newStatus == 'New'}">
      		newStatus[${status.index +1}] =   newStatusOnly;
  		</c:when>
  		<c:otherwise>
    		newStatus[${status.index +1}] =   newStatusVal;
  		</c:otherwise>
 	</c:choose>
	opsEntityId[${status.index +1}] = opsEntityIdArr;
</c:forEach>

<c:set var="paymentCount" value='0'/>

var paymentData = {
        rows:[
<c:forEach var="bean" items="${PaymentColl}" varStatus="status">
<c:choose>
 <c:when test="${bean.newStatus == 'New' && financialPermission == 'true' && editpayment == 'true'}">
  <c:set var="opsEntityIdPermission" value="Y"/>
 </c:when>
 <c:otherwise>
  <c:set var="opsEntityIdPermission" value="N"/>
 </c:otherwise>
</c:choose>

<c:choose>
<c:when test="${bean.newStatus == 'New' || editpayment != 'true'}">
  <c:set var="newStatusPermission" value="N"/>
 </c:when>
 <c:otherwise>
  <c:set var="newStatusPermission" value="Y"/>
 </c:otherwise>
</c:choose>

<c:if test="${ supplierRequestStatus != 'Draft'}" >
 <c:set var="gp" value="N"/>
 <c:set var="opsEntityIdPermission" value="N"/>
 <c:set var="newStatusPermission" value="N"/>  

 <c:if test="${supplierRequestStatus != 'Created' && supplierRequestStatus != 'Rejected' && supplierRequestStatus != 'Approved'}">

 <tcmis:opsEntityPermission indicator="true" opsEntityId="${bean.opsEntityId}"  userGroupId="NewPaymentTerms">
 <c:if test="${supplierRequestStatus != 'Pending Approval'}">
  <c:set var="gp" value="Y"/>
 </c:if>
  <c:set var="opsEntityIdPermission" value="Y"/>  
  <c:if test="${bean.newStatus != 'New'}"><c:set var="newStatusPermission" value="Y"/></c:if>
 </tcmis:opsEntityPermission>
 <c:if test="${! empty bean.approvedOn}">
	<c:set var="opsEntityIdPermission" value="N"/>
 </c:if>
</c:if>

</c:if>

        <c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="approvedOn" value="${bean.approvedOn}" pattern="${dateFormatPattern}"/>
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${gp}',
 '${opsEntityIdPermission}',
 '${newStatusPermission}',
/* 
  <c:choose>
  		<c:when test="${bean.newStatus == 'New' && financialPermission == 'true' && editpayment == 'true'}">
      		'Y'
  		</c:when>
  		<c:otherwise>
    		'N'
  		</c:otherwise>
 </c:choose>,
 <c:choose>
  		<c:when test="${bean.newStatus == 'New' || financialPermission == 'false' || editpayment != 'true'}">
      		'N'
  		</c:when>
  		<c:otherwise>
    		'Y'
  		</c:otherwise>
 </c:choose>, */
 '${bean.opsEntityId}',
 '${bean.paymentTerms}',
 '${bean.currentPaymentTerms}',
 '${bean.currentStatus}',
 '${bean.newStatus}',
 '${approvedOn}',
 '${bean.approvedBy}',
 '<tcmis:jsReplace value="${bean.approvedByName}"/>',
 '${supplierRequestId}',
 '${bean.supplier}'
 ]}
    <c:set var="paymentCount" value='${paymentCount+1}'/>
     </c:forEach>
    ]};

var paymentconfig = [
  {
  	columnId:"permission"
  },
  {
  	columnId:"opsEntityIdPermission"
  },
  {
  	columnId:"newStatusPermission"
  },
{
  	columnId:"opsEntityId",
	columnName:"<fmt:message key="label.operatingentity"/>",
//    onChange:paymentOpsChanged,
	width:18,
  	type:"hcoro"
},
{
  	columnId:"paymentTerms",
	columnName:"<fmt:message key="label.paymentterms"/>",
	width:18,
  	type:"hcoro"
},
{
  	columnId:"currentPaymentTerms",
	columnName:"<fmt:message key="label.currentpaymentterms"/>",
	width:10
},
{
  	columnId:"currentStatus",
	columnName:"<fmt:message key="label.current.status"/>",
	width:6
},
{
  	columnId:"newStatus",
	columnName:"<fmt:message key="label.newstatus"/>",
	type:"hcoro",
	width:8
},
{
  	columnId:"approvedOn",
	columnName:"<fmt:message key="label.approvedon"/>"
},
{
  	columnId:"approvedBy"
//	,columnName:"<fmt:message key="label.paymentterms"/>",
// 	type:"hcoro",
//  	width:20
},
{
  	columnId:"approvedByName",
	columnName:"<fmt:message key="label.approvedby"/>"
},
{
  	columnId:"supplierRequestId"
},
{
  	columnId:"supplier"
}  
];

//////////////////////// excepion grid code
var inventoryGroup = new Array();
var excOpsEntityId = new Array();
var excOpsEntityIdArr = new Array();

for( i=0;i < opshubig.length; i++) { 
	excOpsEntityIdArr[i] = {text:opshubig[i].name,value:opshubig[i].id}
}

var igExcConfig = {
		divName:'SupplierAddExcPaymentTermsBean', // the div id to contain the grid of the data.
		beanData:'igExcData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'igExcGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'igExcconfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:igExcSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};

//  igExcGrid.parentFormOnSubmit(); //prepare grid for data sending
var igExcRowId = null;
var igExcrowids = new Array();

function loadigExcrowids() {
	var len = paymentData.rows.length;
	var up = igExcData.rows.length + len;
	for(i=1+len;i<= up; i++)
		igExcrowids[""+i] = i;
}

function removeIgExcLine() {
	igExcGrid.deleteRow(igExcRowId);
	delete( igExcrowids[igExcRowId] ) ;
	if( igExcGrid.getRowsNum() == 0 ) 
		$('newIgExcRemoveLine').style["display"] = "none";
	return ;
}

function addNewIgExcLine(rowKey,orderQuantityRule) {

// if( !$v('opsEntityId') ) {
//	  alert(messagesData.pleaseselect + " : " + messagesData.operatingentity );
//	  return ;
// }
 var rowid = (new Date()).valueOf();
 ind = igExcGrid.getRowsNum();
//	charges[rowKey].rowidd = thisrow.idd;
	  
	  count = 0 ;
	  excOpsEntityId[ rowid ] = buildNewExcOpsValudset();
	  inventoryGroup[ rowid ] = buildNewIgValudset(excOpsEntityId[ rowid ][0].value);
	  if(inventoryGroup[ rowid ].length == 0) {
		  inventoryGroup[ rowid ] = new Array( {text:messagesData.all,value:""} );
	  }
		  
	var thisrow = igExcGrid.addRow(rowid,"",ind);
    alertthis = true;
// opsentityid
	  igExcGrid.cells(rowid, count++).setValue('Y');
	  igExcGrid.cells(rowid, count++).setValue('Y');
	  igExcGrid.cells(rowid, count++).setValue(excOpsEntityId[ rowid ][0].value);
	  igExcGrid.cells(rowid, count++).setValue(0);
	  igExcGrid.cells(rowid, count++).setValue('Net 45');
	  igExcGrid.cells(rowid, count++).setValue('');
	  igExcGrid.cells(rowid, count++).setValue('');
	  igExcGrid.cells(rowid, count++).setValue('');
	  igExcGrid.cells(rowid, count++).setValue('');
	  igExcGrid.cells(rowid, count++).setValue($v('supplierRequestId'));
	  igExcGrid.cells(rowid, count++).setValue(excOpsEntityId[ rowid ][0].value); 
	  igExcGrid.cells(rowid, count++).setValue($v('supplier'));
	  
	  igExcrowids[""+rowid] = rowid;
	  igExcRowId = rowid;
	  igExcGrid.selectRow(igExcGrid.getRowIndex(rowid));
	  $('newIgExcRemoveLine').style.display="";
//	  igExcCountryChanged(rowid,"columnName");
}

function igExcSelectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 igExcRowId = rowId0;
 
} //end of method

<c:forEach var="bean" items="${PaymentIgColl}" varStatus="status">
	excOpsEntityId[${status.index + paymentCount + 1}] = excOpsEntityIdArr;
</c:forEach>


function buildNewExcOpsValudset() {
	var newItemIdArray = new Array();
	for( i=0;i < opsEntityIdArr.length; i++) 
		newItemIdArray[opsEntityIdArr[i].value] = {text:opsEntityIdArr[i].text,value:opsEntityIdArr[i].value};

//	for( rowid = 1; rowid <= ind; rowid++ ) {
	for( rowid in igExcrowids ) {
		val = gridCellValue(igExcGrid,rowid,"opsEntityId");
//		if( val )
//			delete( newItemIdArray[val] ) ;
	}
	var valArray = new Array();
	for( v in newItemIdArray )
		valArray[valArray.length] = newItemIdArray[v];
	return valArray;
}

function igExcOpsChanged(rowId,columnId,invval) {
	
	  var selectedOps = gridCellValue(igExcGrid,rowId,columnId);
  
	  var inv = $("inventoryGroup"+rowId);
	if(inv != null) {
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;
	  var invarr = buildNewIgValudset(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.all,"", "inventoryGroup"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "inventoryGroup"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
	  gridCell(igExcGrid,rowId,"opsEntityId").setValue(gridCellValue(igExcGrid,rowId,columnId));
	}
}

var ddddInventoryGroup = new Array(
		{text:'Please select',value:''}
		);

function buildNewIgValudset(opsid) {
//	var opsid = $v('opsEntityId');
	var newInvArray = new Array();
	if( !opsid ) newInvArray = ddddInventoryGroup;
	for( i=0;i < opshubig.length; i++) {
		if( opshubig[i].id == opsid ) {
			var hubcoll = opshubig[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				var igcoll = hubcoll[j].coll;
				for( k=0;k< igcoll.length;k++ ){
					newInvArray[newInvArray.length] = {text:igcoll[k].name,value:igcoll[k].id};
				}
			}	
		}
	}
	return newInvArray;
	if( !beginning ) { 
		if(	igExcGrid.getRowsNum() != 0 || carrierGrid.getRowsNum() != 0 ) { 
			for( rowId in igExcrowids ) {
					  var elename =	"inventoryGroup"+rowId;
					  var inv = $(elename);
					  for (var i = inv.length; i > 0; i--) {
						  inv[i] = null;
					  }
					  if(inventoryGroup.length == 0) {
					    setOption(0,messagesData.pleaseselect,"", elename);
					  }
					  for (var i=0; i < inventoryGroup.length; i++) {
					    setOption(i,inventoryGroup[i].text,inventoryGroup[i].value, elename);
					  }
			}
		}
	}
}

function showgridinv() {
	//return ;
	if( pageReadOnly ) return ; // value already shown.
	for( rowId in igExcrowids) 
		igExcOpsChanged(rowId,"excOpsEntityId",gridCellValue(igExcGrid,rowId,"inventoryGroup"));
}


<c:forEach var="bean" items="${PaymentIgColl}" varStatus="status">
<%--//	shipToStateAbbrev[${status.index +1}] = buildigExcStateAbbrev('${bean.shipToCountryAbbrev}');--%>
	inventoryGroup[${status.index + paymentCount + 1}] = buildNewIgValudset('${bean.opsEntityId}');
</c:forEach>
<c:set var="igExcCount" value='0'/>

<c:set var="gp" value="N"/>
<c:if test="${editpayment == 'true'}">
	<c:set var="gp" value="Y"/>
	pageReadOnly = false;
</c:if>

<c:choose>
<c:when test="${financialPermission == 'true' && editpayment == 'true'}">
  <c:set var="excOpsEntityIdPermission" value="Y"/>
 </c:when>
 <c:otherwise>
  <c:set var="excOpsEntityIdPermission" value="N"/>
 </c:otherwise>
</c:choose>

var igExcData = {
        rows:[
<c:forEach var="bean" items="${PaymentIgColl}" varStatus="status">

 <c:set var="gp" value="N"/>
 <c:set var="excOpsEntityIdPermission" value="N"/>
 <tcmis:opsEntityPermission indicator="true" opsEntityId="${bean.opsEntityId}"  userGroupId="NewPaymentTerms"> 
  <c:if test="${empty bean.approvedBy}">
	  <c:set var="gp" value="Y"/>
	  <c:set var="excOpsEntityIdPermission" value="Y"/> 
	  <c:set var="newfinancialPermission" value="Y"/>
  </c:if>

 </tcmis:opsEntityPermission>

<c:if test="${empty bean.approvedBy}">
	<c:set var="allApproved" value="N"/>
</c:if>

		  <c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="approvedOn" value="${bean.approvedOn}" pattern="${dateFormatPattern}"/>
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index + paymentCount+ 1},
         data:[
 '${gp}',
 '${excOpsEntityIdPermission}',
 '${bean.opsEntityId}',
 '${bean.inventoryGroup}',
 '${bean.paymentTerms}',
 '${bean.currentPaymentTerms}',
 '${approvedOn}',
 '${bean.approvedBy}',
 '${bean.approvedByName}',
 '${supplierRequestId}',
 '${bean.opsEntityId}',
 '${bean.supplier}'
 ]}
    <c:set var="igExcCount" value='${igExcCount+1}'/>
     </c:forEach>
    ]};

var igExcconfig = [
  {
  	columnId:"permission"
  },
  {
  	columnId:"excOpsEntityIdPermission"
  },
  {
	  	columnId:"excOpsEntityId",
		columnName:"<fmt:message key="label.operatingentity"/>",
	    onChange:igExcOpsChanged,
		width:17,
	  	type:"hcoro"
  },
{
  	columnId:"inventoryGroup",
	columnName:"<fmt:message key="label.inventorygroup"/>",
	width:19,
  	type:"hcoro"
},
{
  	columnId:"paymentTerms",
	columnName:"<fmt:message key="label.paymentterms"/>",
  	type:"hcoro",
  	width:17
},
{
  	columnId:"currentPaymentTerms",
	columnName:"<fmt:message key="label.currentpaymentterms"/>",
	width:9
},
{
  	columnId:"approvedOn",
	columnName:"<fmt:message key="label.approvedon"/>"
},
{
  	columnId:"approvedBy"
//	,columnName:"<fmt:message key="label.paymentterms"/>",
// 	type:"hcoro",
//  	width:20
},
{
  	columnId:"approvedByName",
	columnName:"<fmt:message key="label.approvedby"/>"
},
{
  	columnId:"supplierRequestId"
},
{
  	columnId:"opsEntityId"
},
{
  	columnId:"supplier"
}
];


//-->
</script>

</td>
</tr>



</table>

</fieldset>
</td>
</tr>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.accountinginfo"/></legend>
<table>

<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.sapvendorcode"/>:</td>


<td width="15%" colspan="3" >
<input type="text" class="invGreyInputText" name="sapVendorCode" id="sapVendorCode" value="<tcmis:inputTextReplace value="${sapVendorCode}" />" maxLength="6" size="6" readonly/>
<span id="sapSpan" style="display:none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
<%--
<c:if test="${approvalPermission == 'false' || supplierRequestStatus != 'Pending Approval'}">
</c:if>
<c:if test="${ 1 == 1 }">--%>
<c:if test="${supplierRequestStatus == 'Pending Approval' and approvalPermission == 'true'}">
  <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="lookupButton" id="lookupButton" value="" onclick="getVendorCode()">
  <input type="checkbox" class="radioBtns" name="checknew" id="checknew" value="nouse" onclick="clearSapVendorCode()" <c:if test="${ empty sapVendorCode}">checked</c:if> /><fmt:message key="label.new"/>
</c:if>
</td>

</tr>

<tr class="alt">

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.haasaccountnumber"/>:</td>

<td width="15%" colspan="3" >

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="haasAccountNumber" id="haasAccountNumber" value="<tcmis:inputTextReplace value="${haasAccountNumber}" />"/>
	<tcmis:inputTextReplace value="${haasAccountNumber}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="haasAccountNumber" id="haasAccountNumber" value="<tcmis:inputTextReplace value="${haasAccountNumber}" />" size="25"/>
</c:if>
</td>

</tr>

</table>
</fieldset>
</td>
</tr>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.misc"/></legend>
<table>
<!-- by default, misc section will follow readonly flag. If the status is Pending Approval and user is approver, allow section edit-->
<c:set var="miscReadOnly" value="${readonly}"/>
<c:if test="${approvalPermission eq 'true' and supplierRequestStatus eq 'Pending Approval'}">
	<c:set var="miscReadOnly" value="false"/>
</c:if>
<tr>

<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.formersuppliername"/>:</td>

<td width="15%" colspan="3" >

<c:if test="${ miscReadOnly == 'true' }">
	<input type="hidden" name="formerSupplierName" id="formerSupplierName" value="<tcmis:inputTextReplace value="${formerSupplierName}" />"/>
	<tcmis:inputTextReplace value="${formerSupplierName}" />
</c:if>
<c:if test="${ miscReadOnly != 'true' }">
	<input type="text" class="inputBox" name="formerSupplierName" id="formerSupplierName" value="<tcmis:inputTextReplace value="${formerSupplierName}" />" size="25"/>
</c:if>

</td>

</tr>



<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.notes"/>:</td>

<td width="15%" colspan="3" >
<c:set var="showNotes" value="false"/>
<c:if test="${ supplierRequestType eq 'Change Payment Terms' or readonly != 'true' or miscReadOnly ne 'true'}">
	<c:set var="showNotes" value="true"/>
</c:if>
<c:if test="${supplierRequestType eq 'Change Payment Terms' && supplierRequestStatus eq 'Pending Payment Terms' && financialPermission eq 'false'}">
	<c:set var="showNotes" value="false"/>
</c:if>

<c:if test="${ showNotes eq 'true' }">
<textarea name="supplierNotes" id="supplierNotes" class="inputBox" rows="10" cols="80">
<c:out value="${supplierNotes}"/>
</textarea>
</c:if>

<c:if test="${ showNotes eq 'false' }">
	<input type="hidden" name="supplierNotes" id="supplierNotes" value="<tcmis:inputTextReplace value="${supplierNotes}" />"/>
	<tcmis:inputTextReplace value="${supplierNotes}" />
</c:if>

</td>

</tr>

<c:set var="flag" value=" disabled "/>
<c:if test="${ miscReadOnly != 'true' }">
<c:set var="flag" value=""/>
</c:if>

<tr>

<td colspan="2">
<input type="checkbox" name="smallBusiness" id="smallBusiness" value="Y" <c:if test="${smallBusiness == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.smallbusiness"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="mbeBlackAmerican" id="mbeBlackAmerican" value="Y" <c:if test="${mbeBlackAmerican == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.mbeblackamerican"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="minorityBusiness" id="minorityBusiness" value="Y" <c:if test="${minorityBusiness == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.minoritybusiness"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="mbeHispanicAmerican" id="mbeHispanicAmerican" value="Y" <c:if test="${mbeHispanicAmerican == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.mbehispanicamerican"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="disadvantagedBusiness" id="disadvantagedBusiness" value="Y" <c:if test="${disadvantagedBusiness == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.disadvantagedbusiness"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="mbeAsianPacificAmerican" id="mbeAsianPacificAmerican" value="Y" <c:if test="${mbeAsianPacificAmerican == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.mbeasianpacificamerican"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="iso9001Certified" id="iso9001Certified" value="Y" <c:if test="${iso9001Certified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.iso9001certified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="as9100Certified" id="as9100Certified" value="Y" <c:if test="${as9100Certified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.as9100certified"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="as9120Certified" id="as9120Certified" value="Y" <c:if test="${as9120Certified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.as9120certified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="mbeNativeAmerican" id="mbeNativeAmerican" value="Y" <c:if test="${mbeNativeAmerican == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.mbenativeamerican"/>
</td>
</tr>



<tr>
<td colspan="2">
<input type="checkbox" name="hubWithLocalCertified" id="hubWithLocalCertified" value="Y" <c:if test="${hubWithLocalCertified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.hubwithlocalcertified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="mbeAsianIndianAmerican" id="mbeAsianIndianAmerican" value="Y" <c:if test="${mbeAsianIndianAmerican == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.mbeasianindianamerican"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="mbeWithLocalCertified" id="mbeWithLocalCertified" value="Y" <c:if test="${mbeWithLocalCertified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.mbewithlocalcertified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="mbeOther" id="mbeOther" value="Y" <c:if test="${mbeOther == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.mbeother"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="wbeWithLocalCertified" id="wbeWithLocalCertified" value="Y" <c:if test="${wbeWithLocalCertified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.wbewithlocalcertified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="womanOwned" id="womanOwned" value="Y" <c:if test="${womanOwned == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.womanowned"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="sdbWithSbaCertified" id="sdbWithSbaCertified" value="Y" <c:if test="${sdbWithSbaCertified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.sdbwithsbacertified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="vietNamVeteranOwned" id="vietNamVeteranOwned" value="Y" <c:if test="${vietNamVeteranOwned == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.vietnamverteranowned"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="category8aWithSbaCertified" id="category8aWithSbaCertified" value="Y" <c:if test="${category8aWithSbaCertified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.category8awithsbacertified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="disabledVeteranOwned" id="disabledVeteranOwned" value="Y" <c:if test="${disabledVeteranOwned == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.disabledveteranowned"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="hubWithSbaCertified" id="hubWithSbaCertified" value="Y" <c:if test="${hubWithSbaCertified == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.hubwithsbacertified"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="nonProfitOrganization" id="nonProfitOrganization" value="Y" <c:if test="${nonProfitOrganization == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.nonprofitorg"/>
</td>
</tr>

<tr>
<td colspan="2">
<input type="checkbox" name="educationalInstitution" id="educationalInstitution" value="Y" <c:if test="${educationalInstitution == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.educationalinstitution"/>
</td>
<td colspan="2" nowrap>
<input type="checkbox" name="debarred" id="debarred" value="Y" <c:if test="${debarred == 'Y'}">checked</c:if>${flag}>
<fmt:message key="label.debarred"/>
</tr>
</table>
</fieldset>
</td>
</tr>
   </table>
   <!-- If the collection is empty say no data found -->
<%--
<input type="checkbox" name="disadvantagedBusiness" id="disadvantagedBusiness" value="Y" <c:if test="${disadvantagedBusiness == 'Y'}">checked</c:if>${flag}>

<c:if test="${empty supplierAddRequestViewBean}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>

   </c:if>
--%>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
<%--
</c:if>
--%>
<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
<c:choose>
<c:when test="searchSupplierRequestInputBean != null">
<c:set var="status" value="${searchSupplierRequestInputBean.status}"/>
<c:set var="searchText" value="${searchSupplierRequestInputBean.searchText}"/>
</c:when>
<c:otherwise>
<c:set var="status" value="${param.status}"/>
<c:set var="searchText" value="${param.searchText}"/>
</c:otherwise>
</c:choose>
<input name="status" id="status" type="hidden" value="<c:out value="${status}"/>">
<input name="searchText" id="searchText" type="hidden" value="<c:out value="${searchText}"/>">
<input name="submitSearch" id="submitSearch" type="hidden">

   <input type="hidden" name="uAction" id="uAction">
   <input type="hidden" name="defaultPaymentTerms" id="defaultPaymentTerms" value="${defaultPaymentTerms}"/>
   <input type="hidden" name="opsEntityId" id="opsEntityId" value=""/>
   <input type="hidden" name="reasonForRejection" id="reasonForRejection" value="${reasonForRejection}"/>

   <input type="hidden" name="supplierRequestType" id="supplierRequestType" value="${supplierRequestType}"/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>

<script language="JavaScript" type="text/javascript">
<!--
var suppReqType = '${supplierRequestType}';
if( '${newfinancialPermission}' == 'N' && '${financialPermission}' == 'false' && '${allApproved}' == 'N' ) {
	try {
		$('pendingspan').style['display'] = 'none';
	} catch(ex){}
}

function myInit() {

	checkclose();
	setdefaultops();
	loadpaymentrowids();
	loadigExcrowids();
	initGridWithGlobal(paymentConfig);
	initGridWithGlobal(igExcConfig);
	showgridinv();
	showremovelines();
	<c:if test="${ readonly != 'true' }">
	buildCountry($('countryAbbrev'));
	countryChanged('${selectedCountry}');
	showStateOptions('${selectedCountry}', '${selectedState}');
	if( checkRemitTo ) {
		buildCountry($('remitToCountryAbbrev'));
		remitCountryChanged('${selectedRemitToCountry}');
		remitShowStateOptions('${selectedRemitToCountry}', '${selectedRemitToState}');
	}
	</c:if>
}

// -->
</script>

</body>
</html:html>

