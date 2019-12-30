<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%@ include file="/common/opshubig.jsp" %>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

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

<script type="text/javascript" src="/js/distribution/customerrequestdetail.js"></script>
<script type="text/javascript" src="/js/distribution/customerrequest.js"></script>
<script type="text/javascript" src="/js/common/taxcodes.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="hasAutoEmailPerm" value="N"/>
<c:set var="preops" value=""/>
<c:set var="opssep" value=""/>

var opspg = [
	    <c:forEach var="nouse0" items="${opsPgColl}" varStatus="status">
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="AccountsReceivable">
<c:set var="hasAutoEmailPerm" value="Y"/>
</tcmis:opsEntityPermission>
<c:set var="hasPerm" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}" userGroupId="priceGroupAssignment">
<c:set var="hasPerm" value="Y"/>
</tcmis:opsEntityPermission>
	    <c:set var="curops" value="${status.current.opsEntityId}"/>
	    <c:if test="${ preops ne curops}">
	    ${opssep}
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>',
          hasPerm:'${hasPerm}',
//          hasPerm:'Y',
          coll: [ 
        	 	  {  id: '${status.current.priceGroupId}',
//           	 	   name: '<tcmis:jsReplace value="${status.current.priceGroupName}"/>'
           	 	     name: '${status.current.priceGroupId}',
	    	    companyId:'${status.current.opsCompanyId}'
        		  }
        <c:set var="opssep" value="]},"/>
    	</c:if>	        
	    <c:if test="${ preops eq curops}">
	    	    	 	   ,
	    	    	 	   {
	    	    			id:'${status.current.priceGroupId}',
	    	    			name:'<tcmis:jsReplace value="${status.current.priceGroupName}"/>',
		    	    		companyId:'${status.current.opsCompanyId}'
//	              	 	     name: '${status.current.priceGroupId}'
	    	    		   }
    	</c:if>	        
    	<c:set var="preops" value="${curops}"/>
	    </c:forEach>
	    <c:if test="${!empty preops }">
	    	]}
	    </c:if>
	   ]; 

var shipToCountryAbbrev = new Array(
		<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
		 <c:if test="${status.index > 0}">,</c:if>
			{text:'<tcmis:jsReplace value="${status.current.country}"/>',value:'${status.current.countryAbbrev}'}
		</c:forEach>
		);

var shipToStateAbbrev = new Array();

var altCountryId = new Array(
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,'${status.current.countryAbbrev}'
   </c:when>
   <c:otherwise>
    '${status.current.countryAbbrev}'
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

  altState["${currentCountry}"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.stateAbbrev}"/>'
   </c:when>
   <c:otherwise>
	   '<tcmis:jsReplace value="${status1.current.stateAbbrev}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );

  altStateName["${currentCountry}"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,'<tcmis:jsReplace value="${status1.current.state}"/>'
   </c:when>
   <c:otherwise>
	   '<tcmis:jsReplace value="${status1.current.state}"/>'
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

if( '${param.source}' == 'scratchPad' ) {
    windowCloseOnEsc = true;
}

// -->
</script>

<%--TODO - make the input fields in grid for fields sales rep and sales agent look read only, also carrier name--%>
<%--TODO - Check zip code in ship to grid.--%>
<%--TODO - make sales agent and feild sales re lookup look read-only in grid--%>
<%--TODO - make error message in dhtmlxWin.--%>
<%--TODO - use vv_ship_complete for ship complete drop down--%>


<title>
<fmt:message key="customerrequestdetail.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
federalTaxIdRequired:"<fmt:message key="error.federaltaxidorvatnum.required"/>",
companyid:"<fmt:message key="label.companyid"/>",
customername:"<fmt:message key="label.customername"/>",
countryRequired:"<fmt:message key="label.country"/>",
addressRequired:"<fmt:message key="label.address"/>",
cityRequired:"<fmt:message key="label.city"/>",
stateRequired:"<fmt:message key="label.state"/>",
zipRequired:"<fmt:message key="label.postalcode"/>",
phoneRequired:"<fmt:message key="label.phone"/>",
paymentTermsRequired:"<fmt:message key="label.paymentterms"/>",
taxRegistrationTypeRequired:"<fmt:message key="label.typeofpurchase"/>",
priceGroupIdRequired:"<fmt:message key="label.qualificationlevel"/>",
taxRegistrationNumberRequired:"<fmt:message key="error.federaltaxidorvatnum.required"/>",
maximumallowedtext:"<fmt:message key="label.maximumallowedtext"/> ",
all:"<fmt:message key="label.all"/>",
commentesrejection:"<fmt:message key="label.commentesrejection"/>",
sapcoderequired:"<fmt:message key="label.sapvendorcode"/>",
shipto:"<fmt:message key="label.shipto"/> ",
operatingentity:"<fmt:message key="label.operatingentity"/> ",
mustbeanumber:"<fmt:message key="label.mustbeanumberinthisfield"/>",
creditlimit:"<fmt:message key="label.creditlimit"/>",
overduelimit:"<fmt:message key="label.graceperiod"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
atleastonecontactRequired:"<fmt:message key="label.atleastonecontact"/>",
locationdesc:"<fmt:message key="label.shiptoname"/>",
salesrep:"<fmt:message key="label.fieldsalesrep"/>",
pricelist:"<fmt:message key="label.pricelist"/>",
taxregistrationnum:"<fmt:message key="label.taxregistrationnum"/>",
addressline1:"<fmt:message key="label.addressline1"/>",
lastname:"<fmt:message key="label.lastname"/>",
firstname:"<fmt:message key="label.firstname"/>",
contact:"<fmt:message key="label.contact"/>",
carrier:"<fmt:message key="label.carrier"/>",
carriername:"<fmt:message key="label.carriername"/>",
carrieraccount:"<fmt:message key="label.carrieraccount"/>",
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
stateProvinceRequired:"<fmt:message key="label.state"/>/<fmt:message key="label.region"/>",
nodatafound:"<fmt:message key="main.nodatafound"/>",
confirmdelete:"<fmt:message key="label.deletethisdraft"/>",
clear:"<fmt:message key="label.clear"/>",
taxexemptioncode:"<fmt:message key="label.taxexemptioncertificate"/>",
defaultshelflife:"<fmt:message key="label.defaultshelflife"/>",
creditlimit:"<fmt:message key="label.creditlimit"/>",
graceperiod:"<fmt:message key="label.graceperiod"/>",
atleastone:"<fmt:message key="label.atleastoneentryreq"/>",
invRequired:"<fmt:message key="label.inventorygroup"/>",
registration:"<fmt:message key="label.registration"/>",
vatinvalidforcountryregion:"<fmt:message key="label.invvalidvarforcountryregion"/>",
vatinvalidforcountry:"<fmt:message key="label.invvalidvarforcountry"/>",
einvoiceemailaddressinvalid:"<fmt:message key="error.einvoiceemailaddressinvalid"/>",
jdecustomerbillto:"<fmt:message key="label.jdebilltoabno"/>"
};

function checkclose() {
	if( '${param.uAction}' == 'deletedraft' ) {
//		tabId = 'newCustomerRequestDetail'+$v('customerRequestId');
		tabId = window.name.substr(0,window.name.length-5); 
		try { parent.parent.closeTabx(tabId);
		} catch(ex){}
		try { parent.closeTabx(tabId);
		} catch(ex){}
		window.close();
	}
	  <c:if test="${customerAddRequestViewBean == null}"> 
	  if( '${param.uAction}' == 'viewrequestdetail' ){
			alert(messagesData.nodatafound);
			tabId = window.name.substr(0,window.name.length-5); 
			try { parent.parent.closeTabx(tabId);
			} catch(ex){}
			try { parent.closeTabx(tabId);
			} catch(ex){}
			window.close();
		}
	  </c:if>
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="checkclose();myInit();" onresize="myResize()">

<tcmis:form action="/customerrequestupdate.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">
<div class="contentArea">
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
<c:if test="${customerAddRequestViewBean != null}" >
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
  <c:when test="${customerAddRequestViewBean == null}">
    <c:set var="customerRequestType" value="New"/>

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
    <c:set var="paymentTermsApprover" value="${param.paymentTermsApprover}"/>
    <c:set var="approver" value="${param.approver}"/>
    <c:set var="customerId" value="${param.customerId}"/>

    <c:set var="customerRequestId" value="${param.customerRequestId}"/>
    <c:set var="customerRequestStatus" value="${param.customerRequestStatus}"/>
    <c:set var="customerName" value="${param.customerName}"/>
    <c:set var="selectedCountry" value='${param.countryAbbrev}'/>
    <c:set var="selectedState" value='${param.stateAbbrev}'/>
    <c:set var="addressLine1" value='${param.addressLine1}'/>
    <c:set var="addressLine2" value='${param.addressLine2}'/>
    <c:set var="addressLine3" value='${param.addressLine3}'/>
    <c:set var="addressLine4" value='${param.addressLine4}'/>
    <c:set var="addressLine5" value='${param.addressLine5}'/>

    <c:set var="city" value='${param.city}'/>
    <c:set var="zip" value='${param.zip}'/>
    <c:set var="zipPlus" value='${param.zipPlus}'/>
    <c:set var="reasonForRejection" value='${param.reasonForRejection}'/>

    <c:set var="fax" value='${param.fax}'/>
    <c:set var="firstName" value='${param.firstName}'/>
    <c:set var="lastName" value='${param.lastName}'/>
    <c:set var="mobile" value='${param.mobile}'/>
    <c:set var="newCompanyId" value='${param.newCompanyId}'/>
    <c:set var="companyId" value='${param.companyId}'/>
    <c:set var="companyName" value='${param.companyName}'/>
    <c:set var="companyShortName" value='${param.companyShortName}'/>
    <c:set var="emailOrderAck" value='${param.emailOrderAck}'/>
    <c:set var="creditLimit" value='${param.creditLimit}'/>
    <c:set var="overdueLimit" value='${param.overdueLimit}'/>
    <c:set var="shelfLifeRequired" value='${param.shelfLifeRequired}'/>
    <c:set var="industry" value='${param.industry}'/>
    <c:set var="invoiceConsolidation" value='${param.invoiceConsolidation}'/>
    <c:set var="taxCurrencyId" value='${param.taxCurrencyId}'/>
    <c:set var="exitLabelFormat" value='${param.exitLabelFormat}'/>
    <c:set var="currencyId" value='${param.currencyId}'/>
    <c:set var="creditStatus" value='${param.creditStatus}'/>
    <c:set var="shipComplete" value='${param.shipComplete}'/>
    <c:set var="overdueLimitBasis" value='${param.overdueLimitBasis}'/>
    <c:set var="email" value='${param.email}'/>
    <c:set var="nickname" value='${param.nickname}'/>
    <c:set var="fax" value='${param.fax}'/>
    <c:set var="taxRegistrationNumber" value='${param.taxRegistrationNumber}'/>
    <c:set var="taxRegistrationType" value='${param.taxRegistrationType}'/>

    <c:set var="vatRegistrationNumber" value='${param.vatRegistrationNumber}'/>

    <c:set var="paymentTerms" value='${param.paymentTerms}'/>
    <c:set var="taxRegistrationType" value='${param.taxRegistrationType}'/>
    <c:set var="priceGroupId" value='${param.priceGroupId}'/>
    <c:if test="${empty priceGroupId}">
      <c:set var="priceGroupId" value='New'/>
    </c:if>
    <c:set var="sapCustomerCode" value='${param.sapCustomerCode}'/>
    <c:set var="accountSysId" value='${param.accountSysId}'/>
    <c:set var="customerNotes" value='${param.customerNotes}'/>

  </c:when>
  <c:otherwise>
    <c:set var="customerRequestType" value='${customerAddRequestViewBean.customerRequestType}'/>
    <c:set var="customerRequestId" value="${customerAddRequestViewBean.customerRequestId}"/>
    <c:set var="opsEntityId" value="${customerAddRequestViewBean.opsEntityId}"/>
    <c:set var="requesterName" value="${customerAddRequestViewBean.requesterName}"/>
    <c:set var="requesterPhone" value="${customerAddRequestViewBean.requesterPhone}"/>
    <c:set var="requesterEmail" value="${customerAddRequestViewBean.requesterEmail}"/>
    <c:set var="requester" value="${customerAddRequestViewBean.requester}"/>
    <c:set var="paymentTermsApprover" value="${customerAddRequestViewBean.paymentTermsApprover}"/>
    <c:set var="approver" value="${customerAddRequestViewBean.approver}"/>
    <c:set var="customerId" value="${customerAddRequestViewBean.customerId}"/>


    <c:set var="selectedCountry" value='${customerAddRequestViewBean.countryAbbrev}'/>
    <c:set var="selectedState" value='${customerAddRequestViewBean.stateAbbrev}'/>
    <c:set var="customerRequestStatus" value="${customerAddRequestViewBean.customerRequestStatus}"/>
    <c:set var="customerName" value="${customerAddRequestViewBean.customerName}"/>
    <c:set var="addressLine1" value='${customerAddRequestViewBean.addressLine1}'/>
    <c:set var="addressLine2" value='${customerAddRequestViewBean.addressLine2}'/>
    <c:set var="addressLine3" value='${customerAddRequestViewBean.addressLine3}'/>
    <c:set var="addressLine4" value='${customerAddRequestViewBean.addressLine4}'/>
    <c:set var="addressLine5" value='${customerAddRequestViewBean.addressLine5}'/>

    <c:set var="city" value='${customerAddRequestViewBean.city}'/>
    <c:set var="zip" value='${customerAddRequestViewBean.zip}'/>
    <c:set var="zipPlus" value='${customerAddRequestViewBean.zipPlus}'/>
    <c:set var="reasonForRejection" value='${customerAddRequestViewBean.reasonForRejection}'/>

    <c:set var="newCompanyId" value='${fn:trim(customerAddRequestViewBean.newCompanyId)}'/>
    <c:set var="companyId" value='${customerAddRequestViewBean.companyId}'/>
    <c:set var="companyName" value='${customerAddRequestViewBean.companyName}'/>
    <c:set var="companyShortName" value='${customerAddRequestViewBean.companyShortName}'/>
    <c:set var="emailOrderAck" value='${customerAddRequestViewBean.emailOrderAck}'/>
    <c:set var="creditLimit" value='${customerAddRequestViewBean.creditLimit}'/>
    <c:set var="overdueLimit" value='${customerAddRequestViewBean.overdueLimit}'/>
    <c:set var="shelfLifeRequired" value='${customerAddRequestViewBean.shelfLifeRequired}'/>
    <c:set var="industry" value='${customerAddRequestViewBean.industry}'/>
    <c:set var="invoiceConsolidation" value='${customerAddRequestViewBean.invoiceConsolidation}'/>
    <c:set var="taxCurrencyId" value='${customerAddRequestViewBean.taxCurrencyId}'/>
    <c:set var="exitLabelFormat" value='${customerAddRequestViewBean.exitLabelFormat}'/>
    <c:set var="currencyId" value='${customerAddRequestViewBean.currencyId}'/>
    <c:set var="creditStatus" value='${customerAddRequestViewBean.creditStatus}'/>
    <c:set var="shipComplete" value='${customerAddRequestViewBean.shipComplete}'/>
    <c:set var="overdueLimitBasis" value='${customerAddRequestViewBean.overdueLimitBasis}'/>

    <c:set var="taxRegistrationNumber" value='${customerAddRequestViewBean.taxRegistrationNumber}'/>
    <c:set var="taxRegistrationType" value='${customerAddRequestViewBean.taxRegistrationType}'/>

    <c:set var="paymentTerms" value='${customerAddRequestViewBean.paymentTerms}'/>
    <c:set var="taxRegistrationType" value='${customerAddRequestViewBean.taxRegistrationType}'/>
    <c:set var="priceGroupId" value='${customerAddRequestViewBean.priceGroupId}'/>
    <c:set var="sapCustomerCode" value='${customerAddRequestViewBean.sapCustomerCode}'/>
    <c:set var="accountSysId" value='${customerAddRequestViewBean.accountSysId}'/>
    <c:set var="customerNotes" value='${customerAddRequestViewBean.customerNotes}'/>
  </c:otherwise>
</c:choose>

<c:if test="${empty selectedCountry}">
	    <c:set var="selectedCountry" value='USA'/>
		<tcmis:isCNServer>
			<c:set var="selectedCountry" value='CHN'/>
		</tcmis:isCNServer>	 
</c:if>
<c:set var="newPermission" value="false"/>
<c:set var="newCompanyPermission" value="false"/>
<c:set var="approvalPermission" value="false"/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
  <c:set var="newPermission" value="true"/>
</tcmis:inventoryGroupPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="NewCompany">
  <c:set var="newCompanyPermission" value="true"/>
</tcmis:opsEntityPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="NewCustomerApprover">
  <c:set var="newPermission" value="true"/>
  <c:set var="approvalPermission" value="true"/>
</tcmis:opsEntityPermission>
<%-- larry note: remove later. 
<c:set var="approvalPermission" value="true"/>
<c:set var="newCompanyPermission" value="true"/>
<c:set var="newPermission" value="true"/>
<c:set var="newPermission" value="false"/>
--%>

<c:if test="${ !(customerRequestStatus eq 'Draft') || requester != personnelBean.personnelId }">
    <c:set var="newPermission" value="false"/>
</c:if>

<%-- beginning of setting actions --%>
<c:choose>
<c:when test="${customerRequestType eq 'Change New Company'}">
	<c:if test="${customerRequestStatus eq 'Draft' && newPermission == 'true'}">
    	<a href="#" onclick="changeNewCompany();"><fmt:message key="button.submit"/></a>
	</c:if>
	<c:if test="${customerRequestStatus eq 'Pending New Company Approval' && newCompanyPermission == 'true'}">
    	<a href="#" onclick="approvePaymentTermsDirect();"><fmt:message key="label.approvenewcompany"/></a>
    	| <a href="#" onclick="rejectCustomerRequest();"><fmt:message key="label.reject"/></a>
	</c:if>
</c:when>

<c:otherwise>
<c:set var="preBar" value=""/>
<c:choose>
  <c:when test="${customerRequestStatus eq 'Draft' and requester eq personnelBean.personnelId and ( approvalPermission eq 'true' or newCompanyPermission eq 'true' or newPermission eq 'true')}" >
	<a href="#" onclick="save();"><fmt:message key="label.save"/></a> |
    <a href="#" onclick="submitdraft();"><fmt:message key="button.submit"/></a> |
    <a href="#" onclick="deletedraft();"><fmt:message key="label.delete"/></a>
	<c:set var="preBar" value=" | "/>
  </c:when>
</c:choose>

<c:if test="${customerRequestStatus == 'Pending New Company Approval' }">
  <c:if test="${newCompanyPermission eq 'true'}" >
  	<a href="#" onclick="saveCustomerRequest();"><fmt:message key="label.save"/></a> |
  	<a href="#" onclick="approveNewCompanyCustomerRequest();"><fmt:message key="label.approvenewcompany"/></a>
    | <a href="#" onclick="rejectCustomerRequest();"><fmt:message key="label.reject"/></a>
  </c:if>
  <c:if test="${newCompanyPermission ne 'true' && approvalPermission eq 'true'}" >
  	<a href="#" onclick="saveCustomerRequest();"><fmt:message key="label.save"/></a>
  </c:if>
</c:if>

<c:if test="${approvalPermission == 'true'}">
  <c:if test="${customerRequestStatus == 'Pending Financial Approval'}">
  	<a href="#" onclick="saveCustomerRequest();"><fmt:message key="label.save"/></a> |
    <a href="#" onclick="approveCustomerRequest();"><fmt:message key="label.approve"/></a> |
    <a href="#" onclick="rejectCustomerRequest();"><fmt:message key="label.reject"/></a>
  </c:if>
</c:if>

</c:otherwise>
</c:choose>
<%-- end of setting actions --%>

<%-- setting up readonly --%>

<c:set var="readonly" value="true"/>
<c:if test="${ approvalPermission eq 'true' and ( (customerRequestStatus eq 'Draft' and requester eq personnelBean.personnelId ) or customerRequestStatus eq 'Pending New Company Approval' or customerRequestStatus eq 'Pending Financial Approval')}" >
	<c:set var="readonly" value="false"/>
</c:if>
<c:if test="${ newCompanyPermission eq 'true' and ( (customerRequestStatus eq 'Draft' and requester eq personnelBean.personnelId ) or customerRequestStatus eq 'Pending New Company Approval' )}" >
	<c:set var="readonly" value="false"/>
</c:if>
<c:if test="${ newPermission eq 'true' and customerRequestStatus eq 'Draft' }" >
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
	${customerRequestStatus}
</td>
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.requesttype"/>:</td>
<td width="15%">${customerRequestType}</td>
<td/>
<td/>
</tr>
</table>
</td>
</tr>
<tr>
<td colspan="4">
  <input type="hidden" name="requesterName" id="requesterName" value="${requesterName}"/>
  <input type="hidden" name="requesterPhone" id="requesterPhone" value="${requesterPhone}"/>
  <input type="hidden" name="requesterEmail" id="requesterEmail" value="${requesterEmail}"/>
  <input type="hidden" name="requester" id="requester" value="${requester}"/>
  <input type="hidden" name="paymentTermsApprover" id="paymentTermsApprover" value="${paymentTermsApprover}"/>
  <input type="hidden" name="approver" id="approver" value="${approver}"/>
  <input type="hidden" name="customerId" id="customerId" value="${customerId}"/>
  <input type="hidden" name="customerRequestId" id="customerRequestId" value="${customerRequestId}"/>
  <input type="hidden" name="customerRequestStatus" id="customerRequestStatus" value="${customerRequestStatus}"/>

<c:if test="${customerRequestStatus == 'Pending Financial Approval' }">
<c:if test="${!empty customerAddRequestViewBean.paymentTermApproverName}">
			<c:if test="${empty customerAddRequestViewBean.datePaymentTermsApproved}">
<fmt:message key="label.newcompanyapprovedby">
	<fmt:param>${customerAddRequestViewBean.paymentTermApproverName}</fmt:param>
</fmt:message>
			</c:if>
	<c:if test="${!empty customerAddRequestViewBean.datePaymentTermsApproved}">
<fmt:formatDate var="appdate" value="${customerAddRequestViewBean.datePaymentTermsApproved}" pattern="${dateFormatPattern}"/>
<fmt:message key="label.newcompanyapprovedbyondate">
	<fmt:param>${customerAddRequestViewBean.paymentTermApproverName}</fmt:param>
	<fmt:param>${appdate}</fmt:param>
</fmt:message>
	</c:if>
</c:if>
</c:if>

<c:if test="${customerRequestStatus == 'Pending New Company Approval' }">
</c:if>

<c:if test="${customerRequestStatus == 'Created' }">
</c:if>

<c:if test="${customerRequestStatus == 'Rejected' }">
	<c:if test="${empty customerAddRequestViewBean.dateApproved}">
		<fmt:message key="label.rejectedby"/> ${customerAddRequestViewBean.approverName}
	</c:if>
	<c:if test="${!empty customerAddRequestViewBean.dateApproved}">
		<fmt:formatDate var="appdate" value="${customerAddRequestViewBean.dateApproved}" pattern="${dateFormatPattern}"/>
		<fmt:message key="label.rejectedbyondate">
			<fmt:param>${customerAddRequestViewBean.approverName}</fmt:param>
			<fmt:param>${appdate}</fmt:param>
		</fmt:message>
		<br/>
		<fmt:message key="label.comment"/>: <span style='font-size:12.0pt;color:red'>${reasonForRejection}</span>
	</c:if>
</c:if>

<c:if test="${customerRequestStatus == 'Created' || customerRequestStatus == 'Approved'}">

	<c:if test="${empty customerAddRequestViewBean.dateApproved}">
		<fmt:message key="label.approvedby"/> ${customerAddRequestViewBean.approverName}
		<c:if test="${!empty customerAddRequestViewBean.paymentTermApproverName}">
			<br/>
			<c:if test="${empty customerAddRequestViewBean.datePaymentTermsApproved}">
			<fmt:message key="label.newcompanyapprovedby">
				<fmt:param>${customerAddRequestViewBean.paymentTermApproverName}</fmt:param>
			</fmt:message>
			</c:if>
			<c:if test="${!empty customerAddRequestViewBean.datePaymentTermsApproved}">
			<fmt:formatDate var="appdate" value="${customerAddRequestViewBean.datePaymentTermsApproved}" pattern="${dateFormatPattern}"/>
			<fmt:message key="label.newcompanyapprovedbyondate">
				<fmt:param>${customerAddRequestViewBean.paymentTermApproverName}</fmt:param>
				<fmt:param>${appdate}</fmt:param>
			</fmt:message>
			</c:if>
		</c:if>
	</c:if>

	<c:if test="${!empty customerAddRequestViewBean.dateApproved}">
		<fmt:formatDate var="appdate" value="${customerAddRequestViewBean.dateApproved}" pattern="${dateFormatPattern}"/>
		<fmt:message key="label.approvedbyondate">
			<fmt:param>${customerAddRequestViewBean.approverName}</fmt:param>
			<fmt:param>${appdate}</fmt:param>
		</fmt:message>
	<c:if test="${!empty customerAddRequestViewBean.paymentTermApproverName}">
		
			<c:if test="${empty customerAddRequestViewBean.datePaymentTermsApproved}">
			<br/>
			<fmt:message key="label.newcompanyapprovedby">
				<fmt:param>${customerAddRequestViewBean.paymentTermApproverName}</fmt:param>
			</fmt:message>
			</c:if>
			<c:if test="${!empty customerAddRequestViewBean.datePaymentTermsApproved}">
<fmt:formatDate var="appdate" value="${customerAddRequestViewBean.datePaymentTermsApproved}" pattern="${dateFormatPattern}"/>
<fmt:message key="label.newcompanyapprovedbyondate">
	<fmt:param>${customerAddRequestViewBean.paymentTermApproverName}</fmt:param>
	<fmt:param>${appdate}</fmt:param>
</fmt:message>
			</c:if>
		</c:if>
	</c:if>
</c:if>

</td>
</tr>
<tr>
<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.address"/></legend>

<table>
<tr>
<td width="15%" colspan="4" class="optionTitleBoldLeft">
 <fmt:message key="label.company"/>:&nbsp;
<%-- 
<c:if test="${ customerRequestStatus eq 'Draft' and newPermission eq 'true' || (customerRequestStatus eq 'Pending New Company Approval' and newCompanyPermission eq 'true') }">
--%>
<c:if test="${ readonly != 'true' && !(customerRequestStatus == 'Pending Financial Approval' and approvalPermission == 'true') }">
<c:if test="${newCompanyId eq 'Y'}">
<input type="text" class="inputBox" name="companyId" id="companyId" value="<tcmis:inputTextReplace value="${companyId}" />" size="20" maxlength="30"/>
</c:if>
<c:if test="${newCompanyId ne 'Y'}">
<input type="text" readonly="readonly" class="invGreyInputText" name="companyId" id="companyId" value="<tcmis:inputTextReplace value="${companyId}" />" size="20" maxlength="30"/>
</c:if>
<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="cLookupButton" id="cLookupButton" value="" onclick="getCompanyId()"/>
<input type="checkbox" class="radioBtns" name="newCompanyId" id="newCompanyId" value="Y" onclick="clearCompanyId()" <c:if test="${newCompanyId eq 'Y'}">checked</c:if> /><fmt:message key="label.new"/>
&nbsp;&nbsp;&nbsp;&nbsp;
<span id="companyIdMsgSpan" style="display:<c:if test="${newCompanyId ne 'Y'}">none</c:if>">
<fmt:message key="label.shortname"/>:
<input type="text" class="inputBox" name="companyShortName" id="companyShortName" value="<tcmis:inputTextReplace value="${companyShortName}" />" size="8" maxlength="30" />
<fmt:message key="label.legalcompanyname"/>:
<input type="text" class="inputBox" name="companyName" id="companyName" value="<tcmis:inputTextReplace value="${companyName}" />" size="20" maxlength="30"/>
</span>
</c:if>

<c:if test="${ !(readonly != 'true' && !(customerRequestStatus == 'Pending Financial Approval' and approvalPermission == 'true') ) }">
<input type="text" class="invGreyInputText" name="companyId" id="companyId" value="<tcmis:inputTextReplace value="${companyId}" />" size="20" readonly="readonly" maxlength="30"/>
<input disabled="disabled" type="checkbox" class="radioBtns" name="newCompanyId" id="newCompanyId" value="Y" onclick="clearCompanyId()" <c:if test="${newCompanyId eq 'Y'}">checked</c:if> /><fmt:message key="label.new"/>
&nbsp;&nbsp;&nbsp;&nbsp;
<fmt:message key="label.shortname"/>:
<input type="hidden" class="inputBox" name="companyShortName" id="companyShortName" value="<tcmis:inputTextReplace value="${companyShortName}" />" size="8" maxlength="30" />
<span class="optionTitle"><tcmis:inputTextReplace value="${companyShortName}" /></span>
&nbsp;&nbsp;&nbsp;&nbsp;
<fmt:message key="label.legalcompanyname"/>:
<input type="hidden" class="inputBox" name="companyName" id="companyName" value="<tcmis:inputTextReplace value="${companyName}" />" size="20"  maxlength="30"/>
<span class="optionTitle"><tcmis:inputTextReplace value="${companyName}" /></span>
</c:if>

</td>
</tr>


<tr>


<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.customername"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if></td>

<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' or customerRequestType eq 'Activate' or customerRequestType eq 'Modify'}">
	<input type="hidden" name="customerName" id="customerName" value="<tcmis:inputTextReplace value="${customerName}" />"/>
	<tcmis:inputTextReplace value="${customerName}" />
</c:if>
<c:if test="${ !(readonly == 'true' or customerRequestType eq 'Activate' or customerRequestType eq 'Modify') }">
	<input type="text" class="inputBox" name="customerName" id="customerName" value="<tcmis:inputTextReplace value="${customerName}" />" size="30" maxlength="50"/>
</c:if>
</td>
<c:if test="${ readonly == 'true' }">
	<c:set var="readonlydisabled" value="disabled"/>
</c:if>
<%--

<td width="8%" class="optionTitleBoldRight">

<fmt:message key="label.operatingentity"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>


<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' }">
 <input type="hidden" name="opsEntityId" id="opsEntityId" value="${opsEntityId}"/>
 <c:forEach var="opsEntityIdBean" items="${vvOpsHubIg}">
   <c:if test="${opsEntityId eq opsEntityIdBean.opsEntityId}">
	${opsEntityIdBean.operatingEntityName}
   </c:if>
 </c:forEach>
</c:if>
<c:if test="${ readonly != 'true' }">
	<select name="opsEntityId" id="opsEntityId" class="selectBox" onchange="buildNewIgValudset()">
	<option value=""><fmt:message key="label.pleaseselect"/></option>
	<c:forEach var="opsEntityIdBean" items="${vvOpsHubIg}">
		<option value="${opsEntityIdBean.opsEntityId}" <c:if test="${opsEntityId eq opsEntityIdBean.opsEntityId}">selected</c:if>>${opsEntityIdBean.operatingEntityName}</option>
	</c:forEach>
	</select>
</c:if>
</td>
--%>
<td width="8%" colspan="2" class="optionTitleBoldCenter">
	<fmt:message key="label.addressindexing"/>
</td>

</tr>


<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.fullbilltoaddress"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine1" id="addressLine1" value="<tcmis:inputTextReplace value="${addressLine1}" />"/>
	${addressLine1}
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine1" id="addressLine1" value="<tcmis:inputTextReplace value="${addressLine1}" />" size="30" maxlength="100"/>
</c:if>
</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.country"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" class="optionTitle">


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



<tr>

<td width="8%" class="optionTitleBoldRight"></td>
<td width="15%" class="optionTitle">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine2" id="addressLine2" value="<tcmis:inputTextReplace value="${addressLine2}" />"/>
	${addressLine2}
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine2" id="addressLine2" value="<tcmis:inputTextReplace value="${addressLine2}" />" size="30" maxlength="50"/>
</c:if>

</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.state"/>/<fmt:message key="label.region"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" class="optionTitle">

          <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">
            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
            <c:choose>
              <c:when test="${empty selectedCountry}" >
                <c:set var="selectedCountry" value='${status.current.countryAbbrev}'/>
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
              <c:when test="${currentCountry == selectedCountry}" >
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
            </c:choose>
          </c:forEach>

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

<td width="8%" class="optionTitleBoldRight"></td>

<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine3" id="addressLine3" value="<tcmis:inputTextReplace value="${addressLine3}" />"/>
	<tcmis:inputTextReplace value="${addressLine3}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine3" id="addressLine3" value="<tcmis:inputTextReplace value="${addressLine3}" />" size="30" maxlength="50"/>
</c:if>
</td>


<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.city"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if></td>

<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="city" id="city" value="<tcmis:inputTextReplace value="${city}" />"/>
	<tcmis:inputTextReplace value="${city}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="city" id="city" value="<tcmis:inputTextReplace value="${city}" />" size="20" maxlength="30"/>
</c:if>

</td>

</tr>



<tr>
<td width="8%" class="optionTitleBoldRight"></td>

<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine4" id="addressLine4" value="<tcmis:inputTextReplace value="${addressLine4}" />"/>
	<tcmis:inputTextReplace value="${addressLine4}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine4" id="addressLine4" value="<tcmis:inputTextReplace value="${addressLine4}" />" size="30" maxlength="50"/>
</c:if>
</td>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.postalcode"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="zip" id="zip" value="<tcmis:inputTextReplace value="${zip}" />"/>
	<tcmis:inputTextReplace value="${zip}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="zip" id="zip" value="<tcmis:inputTextReplace value="${zip}" />" size="10" maxlength="30"/>
</c:if>
<span id="gone" style="display:none">

<span id="zipPlusSpan" style="">
<c:if test="${ readonly == 'true' }">
    <input type="hidden" class="inputBox" name="zipPlus" id="zipPlus" value="<tcmis:inputTextReplace value="${zipPlus}" />"/>
    <c:if test="${!empty zipPlus}">-<tcmis:inputTextReplace value="${zipPlus}" /></c:if>
</c:if>
<c:if test="${ readonly != 'true' }">
	-<input type="text" class="inputBox" name="zipPlus" id="zipPlus" value="<tcmis:inputTextReplace value="${zipPlus}" />" size="5" maxlength="4"/>
</c:if>
</span>

</span>

</td>

</tr>
<tr>
<td width="8%" class="optionTitleBoldRight"></td>
<td width="15%" class="optionTitle">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="addressLine5" id="addressLine5" value="<tcmis:inputTextReplace value="${addressLine5}" />"/>
	<tcmis:inputTextReplace value="${addressLine5}" />
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="addressLine5" id="addressLine5" value="<tcmis:inputTextReplace value="${addressLine5}" />" size="30" maxlength="50"/>
</c:if>
</td>
<td/>
<td/>
</tr>
<tr>
	<td class="optionTitleBoldRight"><fmt:message key="label.acceptseinvoices"/>:</td>
    <td class="optionTitleBoldLeft" nowrap >
	   <input name="autoEmailInvoice" id="autoEmailInvoice" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerAddRequestViewBean.autoEmailInvoice eq 'Y'}">checked</c:if>   <c:if test="${hasAutoEmailPerm != 'Y'}"> disabled</c:if>>	   
	   <fmt:message key="label.einvoicesbatchsize"/>:        
	   <select name="autoEmailBatchSize" id="autoEmailBatchSize" class="selectBox" <c:if test="${hasAutoEmailPerm != 'Y'}"> disabled</c:if>>
 			<option value="" <c:if test="${'' == customerAddRequestViewBean.autoEmailBatchSize}">selected</c:if>></option>
	   		<option value="1" <c:if test="${1 == customerAddRequestViewBean.autoEmailBatchSize}">selected</c:if>>1</option>  
		    <c:forEach begin="5" end="25" varStatus="loop" step="5">
		  			<option value="${loop.index}" <c:if test="${loop.index == customerAddRequestViewBean.autoEmailBatchSize}">selected</c:if>>${loop.index}</option> 
			</c:forEach>       
	    </select>
    </td>
</tr>
<tr>  
<td class="optionTitleBoldRight"><fmt:message key="label.einvoiceemailaddress"/>:</td>
 <td class="optionTitleBoldLeft" >
<input name="autoEmailAddress" id="autoEmailAddress" class="inputBox" maxlength="255" size="35" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.autoEmailAddress}" />"/ <c:if test="${hasAutoEmailPerm != 'Y'}"> disabled</c:if>>
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
<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.registration"/> # 1:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>

<td width="15%" class="optionTitleBoldLeft" nowrap>

<c:if test="${ readonly == 'true' }">
	<input size="8" class="invGreyInputText" type="text" name="taxRegistrationType" id="taxRegistrationType" value="${customerAddRequestViewBean.taxRegistrationType}"/>
</c:if>

<c:if test="${ readonly != 'true' }">
        <select name="taxRegistrationType" id="taxRegistrationType" class="selectBox" onchange="">
        <option value=""><fmt:message key="label.pleaseselect"/></option>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerAddRequestViewBean.taxRegistrationType eq cbean.taxType}">selected</c:if>>${cbean.taxType}</option>
    	</c:forEach>
    	</select>
</c:if>

<fmt:message key="label.number"/>:
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="taxRegistrationNumber" id="taxRegistrationNumber" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber}" />"/>
	<span class="optionTitle"><tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber}" /></span>
</c:if>
<c:if test="${ readonly != 'true' }">
	<input name="taxRegistrationNumber" id="taxRegistrationNumber" class="inputBox" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber}" />" maxLength="30"/>
</c:if>
    
    </td>

</tr>

<tr>
<td width="8%" class="optionTitleBoldRight" nowrap>2:<c:if test="${ readonly != 'true' }"></c:if>
</td>

<td width="15%" class="optionTitleBoldLeft" nowrap>

<c:if test="${ readonly == 'true' }">
	<input size="8" class="invGreyInputText" type="text" name="taxRegistrationType2" id="taxRegistrationType2" value="${customerAddRequestViewBean.taxRegistrationType2}"/>
</c:if>

<c:if test="${ readonly != 'true' }">
        <select name="taxRegistrationType2" id="taxRegistrationType2" class="selectBox" onchange="">
        <option value=""></option>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerAddRequestViewBean.taxRegistrationType2 eq cbean.taxType}">selected</c:if>>${cbean.taxType}</option>
    	</c:forEach>
    	</select>
</c:if>

<fmt:message key="label.number"/>: 
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="taxRegistrationNumber2" id="taxRegistrationNumber2" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber2}" />"/>
	<span class="optionTitle"><tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber2}" /></span>
</c:if>
<c:if test="${ readonly != 'true' }">
	<input name="taxRegistrationNumber2" id="taxRegistrationNumber2" class="inputBox" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber2}" />" maxLength="30"/>
</c:if>
    
    </td>

</tr>

<tr>
<td width="8%" class="optionTitleBoldRight" nowrap>3:<c:if test="${ readonly != 'true' }"></c:if>
</td>

<td width="15%" class="optionTitleBoldLeft" nowrap>

<c:if test="${ readonly == 'true' }">
	<input size="8" class="invGreyInputText" type="text" name="taxRegistrationType3" id="taxRegistrationType3" value="${customerAddRequestViewBean.taxRegistrationType3}"/>
</c:if>

<c:if test="${ readonly != 'true' }">
        <select name="taxRegistrationType3" id="taxRegistrationType3" class="selectBox" onChange="">
    	<option value=""></option>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerAddRequestViewBean.taxRegistrationType3 eq cbean.taxType}">selected</c:if>>${cbean.taxType}</option>
    	</c:forEach>
    	</select>
</c:if>

<fmt:message key="label.number"/>: 
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="taxRegistrationNumber3" id="taxRegistrationNumber3" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber3}" />"/>
	<span class="optionTitle"><tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber3}" /></span>
</c:if>
<c:if test="${ readonly != 'true' }">
	<input name="taxRegistrationNumber3" id="taxRegistrationNumber3" class="inputBox" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber3}" />" maxLength="30"/>
</c:if>
    
    </td>

</tr>

<tr>
<td width="8%" class="optionTitleBoldRight" nowrap>4:<c:if test="${ readonly != 'true' }"></c:if>
</td>

<td width="15%" class="optionTitleBoldLeft" nowrap>

<c:if test="${ readonly == 'true' }">
	<input size="8" class="invGreyInputText" type="text" name="taxRegistrationType4" id="taxRegistrationType4" value="${customerAddRequestViewBean.taxRegistrationType4}"/>
</c:if>

<c:if test="${ readonly != 'true' }">
        <select name="taxRegistrationType4" id="taxRegistrationType4" class="selectBox" onChange="">
    	<option value=""></option>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerAddRequestViewBean.taxRegistrationType4 eq cbean.taxType}">selected</c:if>>${cbean.taxType}</option>
    	</c:forEach>
    	</select>
</c:if>

<fmt:message key="label.number"/>: 
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="taxRegistrationNumber4" id="taxRegistrationNumber4" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber4}" />"/>
	<span class="optionTitle"><tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber4}" /></span>
</c:if>
<c:if test="${ readonly != 'true' }">
	<input name="taxRegistrationNumber4" id="taxRegistrationNumber4" class="inputBox" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.taxRegistrationNumber4}" />" maxLength="30"/>
</c:if>
    
    </td>

</tr>


<tr>
    <td class="optionTitleBoldRight"><fmt:message key="label.industry"/>: </td>
    <td class="optionTitle">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="industry" id="industry" value="${industry}"/>
	<c:forEach var="cbean" items="${vvIndustryCollection}" varStatus="status">
	  <c:if test="${industry eq cbean.industry}">
	     ${cbean.industryDescription}
      </c:if>
    </c:forEach>
</c:if>
<c:if test="${ readonly != 'true' }">
        <select name="industry" id="industry" class="selectBox" onChange="">
    	<c:forEach var="cbean" items="${vvIndustryCollection}" varStatus="status">
	 	   	<option value="${cbean.industry}" <c:if test="${industry eq cbean.industry}">selected</c:if>>${cbean.industryDescription}</option>
    	</c:forEach>
    	</select>
</c:if>
    
    </td>
    <td class="optionTitleBoldRight"><fmt:message key="label.sapindustrykey"/>: </td>
    <td class="optionTitle">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="sapIndustryKey" id="sapIndustryKey" value="${customerAddRequestViewBean.sapIndustryKey}"/>
	<c:forEach var="bean" items="${vvSapIndustryCollection}" varStatus="status">
	  <c:if test="${customerAddRequestViewBean.sapIndustryKey eq bean.sapIndustryKey}">
	     ${bean.description}
      </c:if>
    </c:forEach>
</c:if>
<c:if test="${ readonly != 'true' }">
	<select name="sapIndustryKey" id="sapIndustryKey" class="selectBox" onChange="">
    	<c:forEach var="bean" items="${vvSapIndustryCollection}" varStatus="status">
	 	   	<option value="${bean.sapIndustryKey}" <c:if test="${customerAddRequestViewBean.sapIndustryKey eq bean.sapIndustryKey}">selected</c:if>>${bean.description}</option>
    	</c:forEach>
    </select>
</c:if>

    </td>
    
</tr>


<tr>

    	<td class="optionTitleBoldRight" nowrap><fmt:message key="label.consolidationmethod"/>:</td>
    	<td class="optionTitle">

<c:if test="${empty invoiceConsolidation }">
	<c:set var="invoiceConsolidation" value="Monthly"/>
</c:if>    	

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="invoiceConsolidation" id="invoiceConsolidation" value="${invoiceConsolidation}"/>
    	<c:forEach var="cbean" items="${vvInvoiceConsolidationCollection}" varStatus="status">
	 	   	<c:if test="${invoiceConsolidation eq cbean.invoiceConsolidation}">selected
                ${cbean.invoiceConsolidationDesc}
            </c:if>
    	</c:forEach>
</c:if>
<c:if test="${ readonly != 'true' }">
        <select name="invoiceConsolidation" id="invoiceConsolidation" class="selectBox" onChange="">
        	<option value="At Shipment" selected>At Shipment</option>
<%--    	<c:forEach var="cbean" items="${vvInvoiceConsolidationCollection}" varStatus="status">
	 	   	<option value="${cbean.invoiceConsolidation}" <c:if test="${invoiceConsolidation eq cbean.invoiceConsolidation}">selected</c:if>>${cbean.invoiceConsolidationDesc}</option>
    	</c:forEach>   --%>
    	</select>
</c:if>
    	
    </td>
    <td class="optionTitleBoldRight"><fmt:message key="label.defaultshelflife"/>: </td><td class="optionTitle">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="shelfLifeRequired" id="shelfLifeRequired" value="${shelfLifeRequired}"/>
	${shelfLifeRequired}
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="shelfLifeRequired" id="shelfLifeRequired" value="${shelfLifeRequired}" size="4" maxlength="20" />
</c:if>

    </td>
    
    </tr>
<tr>

<td width="8%" class="optionTitleBoldRight"><fmt:message key="label.paymentterms"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>

</td>

<td width="15%" class="optionTitle">
<%-- Approved,Created,Pending Financial Approval,Pending New Company Approval,Rejected--%>
    <c:if test="${empty paymentTerms}">
      <c:set var="paymentTerms" value='Net 45'/>
    </c:if>

<c:if test="${ readonly eq 'false' }">
<html:select property="paymentTerms" styleClass="selectBox" styleId="paymentTerms" value="${paymentTerms}">

  <html:options collection="vvPaymentTermsBeanCollection" property="paymentTerms" labelProperty="paymentTerms"/>

</html:select>
<%--<br><fmt:message key="supplierrequestdetail.label.standardtermsmessage"/>--%>
</c:if>

<c:if test="${ readonly eq 'true' }">
	${paymentTerms}
    <input type="hidden" name="paymentTerms" id="paymentTerms" value="${paymentTerms}"/>
</c:if>
<c:if test="${empty param.oriPaymentTerms}">
    <c:set var="oriPaymentTerms" value='${param.paymentTerms}'/>
</c:if>
<c:if test="${!empty param.oriPaymentTerms}">
    <c:set var="oriPaymentTerms" value='${param.oriPaymentTerms}'/>
</c:if>
    <input type="hidden" name="oriPaymentTerms" id="oriPaymentTerms" value="${oriPaymentTerms}"/>
</td>
 <td width="8%" class="optionTitleBoldRight" nowrap>
<fmt:message key="label.chargefreight"/>:
</td>
<td class="optionTitle">
    <input ${readonlydisabled} name="chargeFreight" id="chargeFreight" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerAddRequestViewBean.chargeFreight eq 'Y'}">checked</c:if>> 
</td>
</tr>
<tr>
<c:set var="billPermission" value="N"/>
<c:if test="${readonly != 'true'}">
<tcmis:opsEntityPermission indicator="true" userGroupId="priceGroupAssignment">
  <c:set var="billPermission" value="Y"/>
</tcmis:opsEntityPermission>
</c:if>

<td width="8%" class="optionTitleBoldRight" nowrap>
<fmt:message key="label.customerglobalpricelist"/>:<%--<c:if test="${ billPermission == 'Y' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>--%>
</td>

<td width="15%" class="optionTitle">

<c:if test="${ billPermission != 'Y' }">
	<input type="hidden" name="priceGroupId" id="priceGroupId" value="${priceGroupId}"/>
    				<c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	 	   				<c:if test="${priceGroupId eq cbean.priceGroupId}">${cbean.priceGroupName}</c:if>
    				</c:forEach>
</c:if>
<c:if test="${ billPermission == 'Y' }">
				  <select name="priceGroupId" id="priceGroupId" class="selectBox">
                    <option value=""><fmt:message key="label.pleaseselect"/></option>
                    <c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	 	   				<option value="${cbean.priceGroupId}" <c:if test="${priceGroupId eq cbean.priceGroupId}">selected</c:if>>${cbean.priceGroupName}</option>
    				</c:forEach>
    			  </select>
</c:if>

</td>

     <td class="optionTitleBoldRight"><fmt:message key="label.shipcomplete"/>:</td>
    <td class="optionTitle">
    
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="shipComplete" id="shipComplete" value="${shipComplete}"/>
	${shipComplete}
</c:if>
<c:if test="${ readonly != 'true' }">
    			<select name="shipComplete" id="shipComplete" class="selectBox">
    				<option value="NO" <c:if test="${shipComplete eq 'NO'}">selected</c:if>><fmt:message key="label.no"/></option>
    				<option value="LINE" <c:if test="${shipComplete eq 'LINE'}">selected</c:if>><fmt:message key="label.line"/></option>
    				<option value="ORDER" <c:if test="${shipComplete eq 'ORDER'}">selected</c:if>><fmt:message key="label.order"/></option>
    			</select>
</c:if>
    
    </td>
</tr>

<tr>

<td class="optionTitleBoldRight" nowrap><fmt:message key="label.creditlimit"/>
<c:if test="${ customerRequestStatus ne 'Draft' }">
<span style='font-size:12.0pt;color:red'>*</span>
</c:if>
: </td><td class="optionTitle" >
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="creditLimit" id="creditLimit" value="${creditLimit}"/>
	${creditLimit} <tcmis:isCNServer indicator="false">USD</tcmis:isCNServer><tcmis:isCNServer>CNY</tcmis:isCNServer>
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="creditLimit" id="creditLimit" value="${creditLimit}" size="10"/> <tcmis:isCNServer indicator="false">USD</tcmis:isCNServer><tcmis:isCNServer>CNY</tcmis:isCNServer>
</c:if>

</td>
    <td class="optionTitleBoldRight" nowrap>
    <fmt:message key="label.emailorderack"/>:</td><td class="optionTitle">

<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="emailOrderAck" id="emailOrderAck" value="${emailOrderAck}"/>
	${emailOrderAck}
</c:if>
<c:if test="${ readonly != 'true' }">
    <input name="emailOrderAck" id="emailOrderAck1" type="radio" class="radioBtns" value="Y" <c:if test="${emailOrderAck ne 'N'}">checked</c:if>>Y
    <input name="emailOrderAck" id="emailOrderAck2" type="radio" class="radioBtns" value="N" <c:if test="${emailOrderAck eq 'N'}">checked</c:if>>N
</c:if>


	</td>
</tr>

<tr>


    <td class="optionTitleBoldRight"><fmt:message key="label.graceperiod"/>
<c:if test="${ customerRequestStatus ne 'Draft' }">
<span style='font-size:12.0pt;color:red'>*</span>
</c:if>: </td><td class="optionTitle">
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="overdueLimit" id="overdueLimit" value="${overdueLimit}"/>
	${overdueLimit}
</c:if>
<c:if test="${ readonly != 'true' }">
	<input type="text" class="inputBox" name="overdueLimit" id="overdueLimit" value="${overdueLimit}" size="10" maxlength="20"/>
</c:if>

     &nbsp;<fmt:message key="label.days"/>

<%--<c:if test="${ readonly == 'true' }">--%>
    <input type="hidden" name="overdueLimitBasis" id="overdueLimitBasis" value="days"/>
    <%--<input type="hidden" name="overdueLimitBasis" id="overdueLimitBasis" value="${overdueLimitBasis}"/>--%>
	<%--${overdueLimitBasis}--%>
<%--</c:if>--%>
<%--<c:if test="${ readonly != 'true'}">
        <select name="overdueLimitBasis" id="overdueLimitBasis" class="selectBox" onChange="">
    				<option <c:if test="${overdueLimitBasis eq 'days'}">selected</c:if>>days</option>
    				<option <c:if test="${overdueLimitBasis eq '%'}">selected</c:if>>%</option>
    	</select>
</c:if>--%>
     
    </td>
    <td colspan="2" class="optionTitleBoldLeft">
	    <input ${readonlydisabled} name="shipMfgCoc" id="shipMfgCoc" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerAddRequestViewBean.shipMfgCoc eq 'Y'}">checked</c:if>> <fmt:message key="label.mfgcoc"/> 
		<input ${readonlydisabled} name="shipMfgCoa" id="shipMfgCoa" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerAddRequestViewBean.shipMfgCoa eq 'Y'}">checked</c:if>> <fmt:message key="label.mfgcoa"/> 
		<input ${readonlydisabled} name="shipMsds" id="shipMsds" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerAddRequestViewBean.shipMsds eq 'Y'}">checked</c:if>> <fmt:message key="label.msds"/> 
	</td>
    
    <td class="optionTitleBoldRight" nowrap style="display:none;"><fmt:message key="label.material"/> <fmt:message key="label.currency"/>: </td>
    <td class="optionTitle" style="display:none;">
<%-- larry note: somehow   ${cbean.currencyDisplay} doesn't have anything --%>
<%-- <c:if test="${ readonly == 'true' }">
	<input type="hidden" name="currencyId" id="currencyId" value="${currencyId}"/>
	${currencyId}
</c:if>
<c:if test="${ readonly != 'true' }">
        <select name="currencyId" id="currencyId" class="selectBox" onChange="">
    	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	 	   	<option value="${cbean.currencyId}" <c:if test="${currencyId eq cbean.currencyId}">selected</c:if>>${cbean.currencyId}</option>
    	</c:forEach>
    	</select>
</c:if> --%>
    </td>


</tr>



<tr>
<td class="optionTitleBoldRight">
<fmt:message key="label.fieldsalesrep"/>:<c:if test="${ readonly != 'true' }"><span style='font-size:12.0pt;color:red'>*</span></c:if>
</td>
<td class="optionTitle">
<c:if test="${ readonly != 'true' }">
<input type="text" class="invGreyInputText" name="fieldSalesRepName" id="fieldSalesRepName" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.fieldSalesRepName}" />" size="16"/>
<input type="hidden" name="fieldSalesRepId" id="fieldSalesRepId" value="${customerAddRequestViewBean.fieldSalesRepId}"/>
<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="pLookupButton" id="pLookupButton" value="" onclick="getSalesRep()"/>
<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.clear"/>" OnClick="cleargetSalesRep()"/>
</c:if>

<c:if test="${ readonly == 'true' }">
	<input type="hidden" class="invGreyInputText" name="fieldSalesRepName" id="fieldSalesRepName" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.fieldSalesRepName}" />" size="16"/>
    <tcmis:inputTextReplace value="${customerAddRequestViewBean.fieldSalesRepName}" />
</c:if>

    </td>
    <td class="optionTitleBoldRight"><fmt:message key="label.abcclassification"/>:</td>
    <td class="optionTitleLeft">
		<select id="abcClassification" name="abcClassification" class="selectBox">
			<option value=""></option>
			<option value="A" <c:if test="${'A' eq customerAddRequestViewBean.abcClassification}">selected </c:if>>A</option>
			<option value="B" <c:if test="${'B' eq customerAddRequestViewBean.abcClassification}">selected </c:if>>B</option>
			<option value="C" <c:if test="${'C' eq customerAddRequestViewBean.abcClassification}">selected </c:if>>C</option>
		</select>
	</td>   
<%--    <td class="optionTitleBoldRight"><fmt:message key="label.tax"/> <fmt:message key="label.currency"/>:</td>
    <td class="optionTitle" >
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="taxCurrencyId" id="taxCurrencyId" value="${taxCurrencyId}"/>
	${taxCurrencyId}
</c:if>
<c:if test="${ readonly != 'true' }">
        <select name="taxCurrencyId" id="taxCurrencyId" class="selectBox" onChange="">
    	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	 	   	<option value="${cbean.currencyId}" <c:if test="${taxCurrencyId eq cbean.currencyId}">selected</c:if>>${cbean.currencyId}</option>
    	</c:forEach>
    	</select>
</c:if>
    </td>  --%>
</tr>

<tr class="alt">

<td width="8%" class="optionTitleBoldLeft" colspan="2"><fmt:message key="label.sapcustomercode"/>:&nbsp;
<input type="text" class="invGreyInputText" name="sapCustomerCode" id="sapCustomerCode" value="<tcmis:inputTextReplace value="${sapCustomerCode}" />" maxLength="6" size="6" readonly/>
<c:if test="${ !readonly }">

<c:if test="${customerRequestStatus == 'Pending Financial Approval' and approvalPermission == 'true'}">
  <input type="checkbox" class="radioBtns" name="checknew" id="checknew" value="nouse" onclick="clearSapCustomerCode()" checked/><fmt:message key="label.new"/>
  <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="lookupButton" id="lookupButton" value="" onclick="getVendorCode()"/>
</c:if>

<c:if test="${!(customerRequestStatus == 'Pending Financial Approval' and approvalPermission == 'true')}">
  <input disabled type="checkbox" class="radioBtns" name="checknew" id="checknew" value="nouse" onclick="clearSapCustomerCode()" checked/><fmt:message key="label.new"/>
</c:if>


</c:if>


</td>

    <td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.exitlabelformat"/>: </td>
    <td class="optionTitle" >
<c:if test="${ readonly == 'true' }">
	<input type="hidden" name="exitLabelFormat" id="exitLabelFormat" value="${exitLabelFormat}"/>
	${exitLabelFormat}
</c:if>
<c:if test="${ readonly != 'true' }">

        <select name="exitLabelFormat" id="exitLabelFormat" class="selectBox" onChange="">
 	   	<option value=""><fmt:message key="label.pleaseselect"/></option>
    	<c:forEach var="vvBean" items="${labelColl[2]}" varStatus="status">
	 	   	<option value="${vvBean[0]}" <c:if test="${exitLabelFormat eq vvBean[0]}">selected</c:if>>${vvBean[0]}</option>
    	</c:forEach>
    	</select>
</c:if>
    
    </td>

</tr>



<tr class="alt">
	<td width="8%" class="optionTitleBoldLeft" colspan="4">
		<fmt:message key="label.jdebilltoabno"/>:&nbsp;
		<c:choose>
			<c:when test="${ readonly == 'true' }">
				<input type="hidden" name="jdeCustomerBillTo" id="jdeCustomerBillTo" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.jdeCustomerBillTo}" />"/>
				<span class="optionTitle"><tcmis:inputTextReplace value="${customerAddRequestViewBean.jdeCustomerBillTo}" /></span>
			</c:when>
			<c:otherwise>
				<input name="jdeCustomerBillTo" id="jdeCustomerBillTo" class="inputBox" value="<tcmis:inputTextReplace value="${customerAddRequestViewBean.jdeCustomerBillTo}" />" maxLength="30"/>
			</c:otherwise>
		</c:choose>
	</td>
</tr>


</table>
</fieldset>
</td>
</tr>

<tr>
<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend>&nbsp;<fmt:message key="label.customerentitypricelist"/>&nbsp;
<%--  
<span id="updateLine" style='color:blue;cursor:pointer'>
<a onclick=""><fmt:message key="label.update"/></a>	
&nbsp;
</span>
|&nbsp;
<span id="closeSpan" style="color:blue;cursor:pointer">
<a onclick="window.close()"><fmt:message key="label.close"/></a>
&nbsp;

</span>
|&nbsp;
--%>

<c:if test="${billPermission == 'Y'}">
&#150;&#150;&#150;&#150;&#150;&#150;
<span id="newbilltoAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewbilltosLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newbilltoRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removebilltoLine()"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>
</legend>
<div id="billColl" style="height:150px;width:99%;display: none;" ></div>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
//to build price list drop down.

var pgdefaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true},
	   	   pg: {id:'',name:'<fmt:message key="label.pleaseselect"/>'}
	   }

var billopsEntityIdArr = new Array();
for( i=0;i < opspg.length; i++) { // opshubig
	billopsEntityIdArr[i] = {text:opspg[i].name,value:opspg[i].id}
}

var billtorowids = new Array();
var billopsEntityId = new Array();
var billpriceGroupId= new Array();
<c:forEach var="bean" items="${billColl}" varStatus="status">
billopsEntityId[${status.index +1}] = billopsEntityIdArr;
billtorowids[${status.index +1}] = ${status.index +1}; 
</c:forEach>

function buildNewOpsValudset() {
	var newItemIdArray = new Array();
	for( i=0;i < billopsEntityIdArr.length; i++) 
		if( opspg[i].hasPerm == 'Y' )
			newItemIdArray[billopsEntityIdArr[i].value] = {text:billopsEntityIdArr[i].text,value:billopsEntityIdArr[i].value};

//	
//  delete the incoming opsentity id	
	delete( newItemIdArray[$v('opsEntityId')] ) ;
	for( rowid in billtorowids ) {
		val = gridCellValue(billGrid,rowid,"billopsEntityId");
		if( val )
			delete( newItemIdArray[val] ) ;
	}
	var valArray = new Array();
	for( v in newItemIdArray )
		valArray[valArray.length] = newItemIdArray[v];
	return valArray;
}

function billbuildNewpriceGroupId(opsid,priceid) {
//	var opsid = $v('opsEntityId');
//	alert( opsid+":"+priceid);
	var newInvArray = new Array();
	newInvArray[newInvArray.length] = {text:pgdefaults.pg.name,value:pgdefaults.pg.id};
	for( i=0;i < opspg.length; i++) {
		if( opspg[i].id == opsid && opspg[i].hasPerm == 'Y' ) {
			var hubcoll = opspg[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				newInvArray[newInvArray.length] = {text:hubcoll[j].name,value:hubcoll[j].id};
			}	
		}
	}
// This code is for page that already have value.
    if( newInvArray.length == 1 && priceid ) {
    	for( i=0;i < opspg.length; i++) {
    		if( opspg[i].id == opsid ) {
    			var hubcoll = opspg[i].coll;
    			for( j = 0;j< hubcoll.length;j++ ){
        			if( hubcoll[j].id == priceid ) { 
    					newInvArray[0].text = hubcoll[j].name;
    					newInvArray[0].id   = hubcoll[j].id;
    					return newInvArray;
        			}
    			}	
    		}
    	}
    }
	return newInvArray;
}

<c:forEach var="bean" items="${billColl}" varStatus="status">
	//defaultInventoryGroup[${status.index +1}] = buildNewIgValudset('${bean.billopsEntityId}');
	billopsEntityId[ ${status.index +1}] = billopsEntityIdArr;// initial value set buildNewOpsValudset();
	billpriceGroupId[${status.index +1}] = billbuildNewpriceGroupId('${bean.opsEntityId}','${bean.priceGroupId}');
</c:forEach>

function shipOpsChanged(rowId,columnId,invval,pgid) {
	
	  var selectedOps = gridCellValue(shiptoGrid,rowId,columnId);
	  var inv = $("defaultInventoryGroup"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewIgValudset(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.all,"", "defaultInventoryGroup"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "defaultInventoryGroup"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
//pricegroupid	  
	  var inv = $("priceGroupId"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = billbuildNewpriceGroupId(selectedOps,pgid);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.pleaseselect,"", "priceGroupId"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "priceGroupId"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}

function billOpsChanged(rowId,columnId,invval) {
	  var selectedOps = gridCellValue(billGrid,rowId,columnId);

//	  alert( invval );
//billpriceGroupId	  
	  var inv = $("billpriceGroupId"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;
      
	  var invarr = billbuildNewpriceGroupId(selectedOps,invval);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.pleaseselect,"", "billpriceGroupId"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "billpriceGroupId"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}


<c:set var="billCount" value='0'/>
/*
private String facilityId;
private String facilityName;
private String companyId;
private String opsEntityId;
private String operatingEntityName;
private String priceGroupId;
private String priceGroupName;
private String inventoryGroupDefault;
*/

function showbillpg() {
// this is not necessary.	
return;	
	if( pageReadOnly ) return ; // value already shown.
	for( rowId in billtorowids ) { 
//		alert(rowId+":"+gridCellValue(billGrid,rowId,"billpriceGroupId"));
//		billOpsChanged(rowId,"billopsEntityId",gridCellValue(billGrid,rowId,"billpriceGroupId"));
	}
}

var billtoData = {
        rows:[
<c:forEach var="bean" items="${billColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>

        <c:set var="billPermission" value="N"/>
		<c:if test="${readonly == 'true'}">
        <tcmis:opsEntityPermission indicator="true" opsEntityId="${bean.opsEntityId}" userGroupId="priceGroupAssignment">
          <c:set var="billPermission" value="Y"/>
        </tcmis:opsEntityPermission>
        </c:if>
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${billPermission}',
 'N',
 '${bean.opsEntityId}',
 '${bean.priceGroupId}',
 '{bean.inventoryGroupName}',
 ''
 ]}
    <c:set var="billCount" value='${billCount+1}'/>
     </c:forEach>
    ]};

var billColumn = [
  {
  	columnId:"permission"
  },
{
  	columnId:"billopsEntityIdPermission"
},
{
  	columnId:"billopsEntityId",
	columnName:"<fmt:message key="label.operatingentity"/>",
    onChange:billOpsChanged,
    permission:true,
	width:20
	,type:"hcoro"
},
{
  	columnId:"billpriceGroupId",
	columnName:"<fmt:message key="label.pricelist"/>",
    //onChange:billOpsChanged,
	width:20
	,type:"hcoro"
},
{
  	columnId:"inventoryGroupName",
//	columnName:"<fmt:message key="label.inventorygroup"/>",
	width:20
	//,type:"hcoro"
} ,
{
  	columnId:"changed"
}
];

function removebilltoLine() {
	if( billtoselectedRowId == null ) return;
	billGrid.deleteRow(billtoselectedRowId);
	delete( billtorowids[billtoselectedRowId] ) ;
	if( billGrid.getRowsNum() == 0 ) 
		$('newbilltoRemoveLine').style["display"] = "none";
	billtoselectedRowId = null;
	return ;
}

var billtoselectedRowId = null;

function addNewbilltosLine() {

 var rowid = (new Date()).valueOf();
 ind = billGrid.getRowsNum();
	  
	  count = 0 ;
	  var arr = buildNewOpsValudset();
	  if( !arr || !arr.length ) return; 
	  billopsEntityId[ rowid ] = arr;
      billpriceGroupId[ rowid ] = billbuildNewpriceGroupId(billopsEntityId[ rowid ][0].value );
	  if(billpriceGroupId[ rowid ].length == 0) {
		  billpriceGroupId[ rowid ] = new Array( {text:messagesData.all,value:""} );
	  }
		  
	  
	var thisrow = billGrid.addRow(rowid,"",ind);

// billopsEntityId
	  billGrid.cells(rowid, count++).setValue('Y');
	  billGrid.cells(rowid, count++).setValue('Y');
	  billGrid.cells(rowid, count++).setValue(billopsEntityId[ rowid ][0].value);
	  billGrid.cells(rowid, count++).setValue(billpriceGroupId[ rowid ][0].value);
	  billGrid.cells(rowid, count++).setValue('Default');
	  billGrid.cells(rowid, count++).setValue('New');
	  
	  billtorowids[""+rowid] = rowid;
	  billtoselectedRowId = rowid;
	  billGrid.selectRow(billGrid.getRowIndex(rowid));
	  $('newbilltoRemoveLine').style.display="";

}

function billtoselectRow()
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

 billtoselectedRowId = rowId0;
 
} //end of method

function showRemoveLine() {
	if( billGrid != null ) {
		var line = billGrid.getRowsNum();
		if( line && $('newbilltoRemoveLine') ) 
			$('newbilltoRemoveLine').style.display="";
	}
}

var billConfig = {
		divName:'billColl', // the div id to contain the grid of the data.
		beanData:'billtoData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'billGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'billColumn',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
								// remember to call haasGrid.parentFormOnSubmit() before actual submit.
		onRowSelect:billtoselectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:billtoselectRow,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//		onBeforeSorting:_onBeforeSorting
	};

//-->
</script>

</fieldset>
</td>
</tr>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.contactinfo"/>
<c:if test="${readonly != 'true'}">
&#150;&#150;&#150;&#150;&#150;&#150;
<span id="newContactAddLine" style='color:blue;cursor:pointer'><a onclick="addNewContactsLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newContactRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;

<%-- 
<a onclick="if( cellValue(selectedRowId,'changed') != 'New' ) {gridCell(newContactsGrid,selectedRowId,'changed').setValue('Remove');_simpleUpdate();} else removeNewContactsLine();"><fmt:message key="label.removeLine"/></a>
--%>
<a onclick="removeNewContactsLine()"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>
</legend>
<div id="CustomerContactAddRequestBean" style="height:150px;display: none;" ></div>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var pageReadOnly = true; 

<c:set var="gp" value="N"/>
<c:if test="${readonly != 'true'}">
	<c:set var="gp" value="Y"/>
	pageReadOnly = false;
</c:if>

var jsonData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>
         <c:set var="p" value="Y"/>
         <c:set var="management" value="false"/>
         <c:if test="${bean.management eq 'Y'}"><c:set var="management" value="true"/></c:if>
         <c:set var="purchasing" value="false"/>
         <c:if test="${bean.purchasing eq 'Y'}"><c:set var="purchasing" value="true"/></c:if>
         <c:set var="accountsPayable" value="false"/>
         <c:if test="${bean.accountsPayable eq 'Y'}"><c:set var="accountsPayable" value="true"/></c:if>
         <c:set var="receiving" value="false"/>
         <c:if test="${bean.receiving eq 'Y'}"><c:set var="receiving" value="true"/></c:if>
         <c:set var="qualityAssurance" value="false"/>
         <c:if test="${bean.qualityAssurance eq 'Y'}"><c:set var="qualityAssurance" value="true"/></c:if>
         <c:set var="defaultContact" value="false"/>
         <c:if test="${bean.defaultContact eq 'Y'}"><c:set var="defaultContact" value="true"/></c:if>         
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${gp}',
 '<tcmis:jsReplace value="${bean.firstName}"/>',
 '<tcmis:jsReplace value="${bean.lastName}"/>',
 '${bean.contactType}',
 '<tcmis:jsReplace value="${bean.nickname}"/>',
 '${bean.phone}',
 '${bean.mobile}',
 '<tcmis:jsReplace value="${bean.email}"/>',
 '${bean.fax}',
 ${purchasing},
 ${accountsPayable},
 ${receiving},
 ${qualityAssurance},
 ${management},
 ${defaultContact},
 '${bean.customerRequestId}',
 '${bean.customerId}',
 '${bean.contactPersonnelId}',
 '<tcmis:jsReplace value="${bean.billToCompanyId}"/>',
 'Y'
 ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
     </c:forEach>
    ]};


var contactType = new Array( 
		<c:forEach var="cbean" items="${vvJobFunctionCollection}" varStatus="status"> 
		<c:if test="${status.index > 0}">,</c:if>
		{text:'<tcmis:jsReplace value="${cbean.contactTypeDesc}"/>',value:'<tcmis:jsReplace value="${cbean.contactType}"/>'}
		</c:forEach>  	
		);  

<c:if test="${!empty beanColl}">
	<tcmis:isCNServer indicator="false">selectCurrency='USD';</tcmis:isCNServer>
	<tcmis:isCNServer>selectCurrency='CNY';</tcmis:isCNServer>
</c:if>

var config = [
  {
  	columnId:"permission"
  },
  {
	columnId:"firstName",
	columnName:"<fmt:message key="label.firstname"/> *",
	size:10,
	maxlength:30,
	type:"hed"
},
{
  	columnId:"lastName",
	columnName:"<fmt:message key="label.lastname"/> *",
	size:10,
	maxlength:30,
  	type:"hed"
},
{
  	columnId:"contactType",
	columnName:"<fmt:message key="label.jobfunction"/>",
	width:13,
  	type:"hcoro"
},
{
  	columnId:"nickname",
	columnName:"<fmt:message key="label.nickname"/>",
	size:10,
	maxlength:30,
  	type:"hed"
},
{
  	columnId:"phone",
	columnName:"<fmt:message key="label.phone"/> *",
    size:12,
  	type:"hed"
},
{
  	columnId:"mobile",
	columnName:"<fmt:message key="label.mobile"/>",
  	type:"hed"
},
{
  	columnId:"email",
	columnName:"<fmt:message key="label.email"/>",
    width:13,
    size:20,
    maxlength:80,
  	type:"hed"
},
{
  	columnId:"fax",
	columnName:"<fmt:message key="label.fax"/>",
    size:12,
  	type:"hed"
},
{
  	columnId:"purchasing",
	columnName:"<fmt:message key="label.purchasing"/>",
  	type:'hchstatus',  
    align:'center'
},
{
  	columnId:"accountsPayable",
	columnName:"<fmt:message key="label.accountspayable"/>",
  	type:'hchstatus',  
    align:'center'
},
{
  	columnId:"receiving",
	columnName:"<fmt:message key="label.receiving"/>",
  	type:'hchstatus',  
    align:'center'
},
{
  	columnId:"qualityAssurance",
	columnName:"<fmt:message key="label.qualityassurance"/>",
  	type:'hchstatus',  
    align:'center'
},
{
  	columnId:"management",
	columnName:"<fmt:message key="label.management"/>",
  	type:'hchstatus',  
    align:'center'
},
{
  	columnId:"defaultContact",
	columnName:"<fmt:message key="label.defaultcontact"/>",
  	type:'hchstatus', 
  	onChange:checkOne, 
    align:'center'
},
{
  	columnId:"customerRequestId"
},
{
  	columnId:"customerId"
},
{
  	columnId:"contactPersonnelId"
},
{
  	columnId:"billToCompanyId"
}, 
{
  	columnId:"changed"
}
];

//-->
</script>

</fieldset>
</td>
</tr>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.shipto"/>
<c:if test="${readonly != 'true'}">
&#150;&#150;&#150;&#150;&#150;&#150;
<span id="newShiptoAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewShiptosLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newShiptoRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removeShiptoLine()"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>
</legend>

<div id="CustomerShiptoAddRequestBean" style="height:150px;display: none;" ></div>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
//to build price list drop down.
var pgdefaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true},
	   	   pg: {id:'',name:'<fmt:message key="label.pleaseselect"/>'}
	   }

<c:forEach var="bean" items="${shipColl}" varStatus="status">
	shipToStateAbbrev[${status.index +1}] = buildShiptoStateAbbrev('${bean.shipToCountryAbbrev}');
	defaultInventoryGroup[${status.index +1}] = buildNewIgValudset('${bean.opsEntityId}');
	priceGroupId[${status.index +1}] = billbuildNewpriceGroupId('${bean.opsEntityId}','${bean.priceGroupId}');
</c:forEach>

var msdsLocaleOverride = 
	new Array( 
		{text:messagesData.pleaseselect,value:''}
		<c:forEach var="cbean" items="${vvLocale}" varStatus="status"> 
		,{text:'<tcmis:jsReplace value="${cbean.localeDisplay}"/>',value:'<tcmis:jsReplace value="${cbean.localeCode}"/>'}
		</c:forEach>  	
		);  

<c:set var="shipCount" value='0'/>

var shiptoData = {
        rows:[
<c:forEach var="bean" items="${shipColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>

<c:set var="hasPerm" value="N"/>

		<c:if test="${readonly == 'true'}">
			<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}" userGroupId="priceGroupAssignment">
			<c:set var="hasPerm" value="Y"/>
			</tcmis:opsEntityPermission>	    
	    	<tcmis:jsReplace value="${bean.salesAgentName}" var="salesAgentName"/>
	    	<tcmis:jsReplace value="${bean.fieldSalesRepName}" var="fieldSalesRepName"/>
	    </c:if>
        <c:if test="${readonly != 'true'}">
	    	<c:set var="salesAgentName">
	    		<input readonly class="invInputText" size="15" id="salesAgentName_${status.index+1}" value="${bean.salesAgentName}"/><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" id="sLookupButton" value="" onclick="getShipToSalesAgent(${status.index+1})"/><input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.clear"/>" OnClick="clearAgent(${status.index+1})"/>
	    	</c:set>
	    	<c:set var="fieldSalesRepName">
    			<input readonly class="invInputText" size="15" id="fieldSalesRepName_${status.index+1}" value="${bean.fieldSalesRepName}"/><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" id="fLookupButton" value="" onclick="getShipToFieldSalesRep(${status.index+1})"/><input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.clear"/>" OnClick="clearField(${status.index+1})"/> 
    		</c:set>
	    	<tcmis:jsReplace value="${salesAgentName}" var="salesAgentName"/>
	    	<tcmis:jsReplace value="${fieldSalesRepName}" var="fieldSalesRepName"/>
   		</c:if>

        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${gp}',
 '<tcmis:jsReplace value="${bean.locationDesc}" processMultiLines="true"/>',
 '<tcmis:jsReplace value="${bean.shipToAddressLine1}"/>',
 '<tcmis:jsReplace value="${bean.shipToAddressLine2}"/>',
 '<tcmis:jsReplace value="${bean.shipToAddressLine3}"/>',
 '<tcmis:jsReplace value="${bean.shipToAddressLine4}"/>',
 '<tcmis:jsReplace value="${bean.shipToAddressLine5}"/>',
 '<tcmis:jsReplace value="${bean.shipToCity}"/>',
 '${bean.shipToStateAbbrev}',
 '${bean.shipToCountryAbbrev}',
 '${bean.shipToZip}',
 '${bean.opsEntityId}',
 '${bean.defaultInventoryGroup}',
 '${bean.salesAgentId}',
 '${bean.fieldSalesRepId}',
 '${salesAgentName}',
 '${fieldSalesRepName}',
 '${hasPerm}',
 '${bean.priceGroupId}',
 '${bean.customerRequestId}',
 '${bean.customerId}',
 '<tcmis:jsReplace value="${bean.billToCompanyId}"/>',
 'Y',
 '<tcmis:jsReplace value="${bean.internalNote}" processMultiLines="true"/>',
 '${bean.msdsLocaleOverride}',
 '<tcmis:jsReplace value="${bean.jdeCustomerShipTo}"/>'
 ]}
    <c:set var="shipCount" value='${shipCount+1}'/>
     </c:forEach>
    ]};

var shiptoconfig = [
  {
  	columnId:"permission"
  },
  {
	columnId:"locationDesc",
	columnName:"<fmt:message key="label.shiptoname"/> *",
	width:10,
	size:15,
	maxlength:30,
	type:"hed"
},
{
	columnId:"shipToAddressLine1",
	columnName:"<fmt:message key="label.addressline1"/> *",
	width:12,
	size:20,
	type:"hed",
	maxlength:50
},
{
  	columnId:"shipToAddressLine2",
	columnName:"Line 2",
	width:12,
	size:20,
  	type:"hed",
    maxlength:50
},
{
  	columnId:"shipToAddressLine3",
	columnName:"Line 3",
	width:12,
	size:20,
  	type:"hed",
    maxlength:50
},
{
  	columnId:"shipToAddressLine4",
	columnName:"Line 4",
	width:12,
	size:20,
  	type:"hed",
    maxlength:50
},
{
  	columnId:"shipToAddressLine5",
	columnName:"Line 5",
	width:12,
	size:20,
  	type:"hed",
    maxlength:50
},
{
  	columnId:"shipToCity",
	columnName:"<fmt:message key="label.city"/> *",
	size:10,
  	type:"hed"
},
{
  	columnId:"shipToStateAbbrev",
	columnName:"<fmt:message key="label.state"/>",
  	//type:"hed"
  	width:12,
  	type:"hcoro"
},
{
  	columnId:"shipToCountryAbbrev",
	columnName:"<fmt:message key="label.country"/>",
	width:20,
//  	type:"hed"
    onChange:shipCountryChanged,
  	type:"hcoro"
},
{
  	columnId:"shipToZip",
	columnName:"<fmt:message key="label.zip"/>",
	size:10,
	type:"hed"
},
{
  	columnId:"opsEntityId",
	columnName:"<fmt:message key="label.operatingentity"/>",
    onChange:shipOpsChanged,
	width:20,
  	type:"hcoro"
},
{
  	columnId:"defaultInventoryGroup",
	columnName:"<fmt:message key="label.inventorygroup"/>",
	width:20,
  	type:"hcoro"
},
{
  	columnId:"salesAgentId"
},
{
  	columnId:"fieldSalesRepId"
},
{
  	columnId:"salesAgentName",
	columnName:"<fmt:message key="label.salesagent"/>",
  	type:"ro",
  	width:16,
  	size:26,
  	submit:false
},
{
  	columnId:"fieldSalesRepName",
	columnName:"<fmt:message key="label.fieldsalesrep"/>",
  	type:"ro",
  	width:16,
  	size:26,
	submit:false
},
{
  	columnId:"priceGroupIdPermission"
},
{
  	columnId:"priceGroupId",
//	columnName:"<fmt:message key="label.pricelist"/>",
	width:10,
	permission:true,
  	type:"hcoro"
},
{
  	columnId:"customerRequestId"
},
{
  	columnId:"customerId"
},
{
  	columnId:"billToCompanyId"
}, 
{
  	columnId:"changed"
},
{
	columnId:"internalNote",
	columnName:'<fmt:message key="label.shiptonotes"/>',
	<c:if test="${readonly != 'true'}">	
		type:"txt",
	</c:if>
	width:10
},
{
	columnId:"msdsLocaleOverride",
	columnName:'<fmt:message key="label.msdslocaleoverride"/>',
	type:"hcoro",
    width:15
},
{
	columnId:"jdeCustomerShipTo",
	columnName:'<fmt:message key="label.jdeshiptoabno"/>',
    width:30,
	type:"hed"
}
];

//-->
</script>


</fieldset>
</td>
</tr>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.carrier"/>
<c:if test="${readonly != 'true'}">
&#150;&#150;&#150;&#150;&#150;&#150;
<span id="newCarrierAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewCarriersLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newCarrierRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removeCarrierLine();"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>
</legend>

<div id="CustomerCarrierAddRequestBean" style="height:150px;display: none;" ></div>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

<c:set var="carrier" value='0'/>

function getCarrier( rowId ) {
	  loc = "newcarrieraccount.do?action=newcarrieraccount&callbackparam=carrier_"+rowId;
	  openWinGeneric(loc,"getCarrier","600","250","yes","50","50","20","20","no");
}

function carrierChanged(name,account,notes,callbackparam) {
	var srr = callbackparam.split('_');
	$('carrier_'+srr[1]).value = name;
	gridCell(carrierGrid,srr[1],"carrierName").setValue(name);
	gridCell(carrierGrid,srr[1],"carrierAccount").setValue(account);
	gridCell(carrierGrid,srr[1],"notes").setValue(notes);
}

var carrierData = {
        rows:[
<c:forEach var="bean" items="${carrierColl}" varStatus="status">
<tcmis:jsReplace value="${bean.notes}" var="notes" processMultiLines="true"/>
<c:if test="${readonly == 'true'}">
	<tcmis:jsReplace value="${bean.carrierName}" var="carrierName"/>
	<tcmis:jsReplace value="${bean.carrierName}" var="carrierDisplay"/>
</c:if>
<c:if test="${readonly != 'true'}">
<c:set var="carrierDisplay">
	<input readonly class="invInputText" size="15" id="carrier_${status.index+1}" value="${bean.carrierName}"/><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" id="carrierLookupButton" value="" onclick="getCarrier(${status.index+1})"/>
</c:set>
<tcmis:jsReplace value="${carrierDisplay}" var="carrierDisplay"/>
</c:if>


	<c:if test="${status.index > 0}">,</c:if>
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${gp}',
 '<tcmis:jsReplace value="${bean.carrierName}"/>',
 '${carrierDisplay}',
 '${bean.carrierAccount}',
 '${bean.transportationMode}',
 '${bean.carrierMethod}',
 'ALL',
 '${notes}',
 '${bean.customerRequestId}',
 '${bean.customerId}',
 '<tcmis:jsReplace value="${bean.billToCompanyId}"/>',
 'Y'
 ]}
    <c:set var="carrier" value='${carrier+1}'/>
     </c:forEach>
    ]};

var carrierconfig = [
  {
  	columnId:"permission"
  },
  {
		columnId:"carrierName"
//		,columnName:"<fmt:message key="label.carriername"/> *",
//		width:12,
//		type:"hed"
	},
	{
	  	columnId:"carrierDisplay",
		columnName:"<fmt:message key="label.carriername"/> *",
	  	type:"ro",
	  	width:12,
		submit:false
	},
{
  	columnId:"carrierAccount",
	columnName:"<fmt:message key="label.carrieraccount"/> *",
	width:12,
	size:20,
	type:"hed"
},
{
  	columnId:"transportationMode"
// 	  	,columnName:"<fmt:message key="label.transportationmode"/>",
//	width:12,
//	type:"hcoro"
},
{
  	columnId:"carrierMethod"
//  	,columnName:"<fmt:message key="label.carriermethod"/>",
//	width:12,
//	type:"hcoro"
},
{
  	columnId:"inventoryGroup"
	//,columnName:"<fmt:message key="label.inventorygroup"/>",
	//width:20,
  	//type:"hcoro"
},
{
  	columnId:"notes",
	columnName:"<fmt:message key="label.notes"/>",
	width:20,
	size:38,
	type:"hed"
},
{
  	columnId:"customerRequestId"
},
{
  	columnId:"customerId"
},
{
  	columnId:"billToCompanyId"
}, 
{
  	columnId:"changed"
} 
];

//-->
</script>

</fieldset>
</td>
</tr>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.taxexemptioncertificate"/>
<c:if test="${readonly != 'true'}">
&#150;&#150;&#150;&#150;&#150;&#150;
<span id="newTaxAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewTaxLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newTaxRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removeTaxLine()"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>
</legend>

<div id="CustomerTaxAddRequestBean" style="height:150px;display: none;" ></div>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
// make sure this line stays here, don't move it to js.
//var validTaxCountryAbbrev = new Array('USA','CAN','IRL','DEU');
var countryAbbrev = new Array(
         <c:forEach var="vvCountryBean" items="${vvValidTaxCountryColl}" varStatus="status1">
		 <c:if test="${status1.index > 0}">,</c:if>
			{text:'<tcmis:jsReplace value="${status1.current.country}"/>',value:'${status1.current.countryAbbrev}'}
		</c:forEach>
		);
var stateAbbrev = new Array();
//var countryAbbrev = getValidCountryAbbrev();

<c:forEach var="bean" items="${taxColl}" varStatus="status">
	stateAbbrev[${status.index +1}] = buildStateAbbrev('${bean.countryAbbrev}');
</c:forEach>

<c:set var="taxCount" value='0'/>

var taxData = {
        rows:[
<c:forEach var="bean" items="${taxColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>
        /*The row ID needs to start with 1 per their design.*/
 <fmt:formatDate var="expirationDate" value="${bean.expirationDate}" pattern="${dateFormatPattern}"/>
        { id:${status.index +1},
         data:[
 '${gp}',
 '${bean.countryAbbrev}',
 '${bean.stateAbbrev}',
 '${bean.taxExemptionCertificate}',
 '${expirationDate}',
 '${bean.customerRequestId}',
 '${bean.customerId}',
 '<tcmis:jsReplace value="${bean.billToCompanyId}"/>',
 'Y'
 ]}
    <c:set var="taxCount" value='${taxCount+1}'/>
     </c:forEach>
    ]};

var taxconfig = [
  {
  	columnId:"permission"
  },
  {
	  	columnId:"countryAbbrev",
		columnName:"<fmt:message key="label.country"/>",
		width:25,
//	  	type:"hed"
	    onChange:taxCountryChanged,
	  	type:"hcoro"
	},
	{
	  	columnId:"stateAbbrev",
		columnName:"<fmt:message key="label.state"/>",
	  	//type:"hed"
	  	width:15,
	  	type:"hcoro"
	},
{
	columnId:"taxExemptionCertificate",
	columnName:"<fmt:message key="label.taxexemptioncertificate"/> *",
	width:15,
	size:20,
	type:"hed"
},
{
	columnId:"expirationDate",
	columnName:"<fmt:message key="label.expiredate"/>",
	type:"hcal"
},
{
  	columnId:"customerRequestId"
},
{
  	columnId:"customerId"
},
{
  	columnId:"billToCompanyId"
}, 
{
  	columnId:"changed"
} 
];

//-->
</script>


</fieldset>
</td>
</tr>

<tr>

<td colspan="4" class="optionTitleBoldLeft">
<fieldset>
<legend><fmt:message key="label.misc"/></legend>
<table>


<tr>

<td width="8%" class="optionTitleBoldLeft" colspan="3">
<table>
<tr><td><fmt:message key="label.notes"/>:</td>
<td width="15%"  class="optionTitleBoldLeft">

<c:if test="${ readonly eq 'false' }">
<textarea name="customerNotes" id="customerNotes" class="inputBox" rows="10" cols="80">${customerNotes}</textarea>
</c:if>

<c:if test="${ readonly eq 'true' }">
<textarea readonly="readonly" class="invGreyInputText" name="customerNotes" id="customerNotes" class="inputBox" rows="10" cols="80">${customerNotes}</textarea>
</c:if>
</td>
</tr></table>
</td>
<td/>
</tr>
</table>

</fieldset>
</td>
</tr>

   </table>
   <!-- If the collection is empty say no data found -->
<%--
<input type="checkbox" name="disadvantagedBusiness" id="disadvantagedBusiness" value="Y" <c:if test="${disadvantagedBusiness == 'Y'}">checked</c:if>${flag}>

<c:if test="${empty customerAddRequestViewBean}" >

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
</td></tr>
</table>
<!-- Search results end -->
<%--
</c:if>
--%>
<!-- Hidden element start, the search text is just saved, might not be used. -->
 <div id="hiddenElements" style="display: none;">
<c:choose>
<c:when test="searchCustomerRequestInputBean != null">
<c:set var="status" value="${searchCustomerRequestInputBean.status}"/>
<c:set var="searchText" value="${searchCustomerRequestInputBean.searchText}"/>
</c:when>
<c:otherwise>
<c:set var="status" value="${param.status}"/>
<c:set var="searchText" value="${param.searchText}"/>
</c:otherwise>
</c:choose>
<input name="status" id="status" type="hidden" value="<c:out value="${status}"/>">
<input name="searchText" id="searchText" type="hidden" value="<c:out value="${searchText}"/>">
<input name="submitSearch" id="submitSearch" type="hidden">
<input id="blockBefore_expirationDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" type="hidden"/>
<input id="inDefinite_expirationDate" value="false" type="hidden"/><%--No Use and I don't have time to fix this. --%>

   <input type="hidden" name="uAction" id="uAction">
   <input type="hidden" name="reasonForRejection" id="reasonForRejection" value="${reasonForRejection}"/>
   <input type="hidden" name="customerRequestType" id="customerRequestType" value="${customerRequestType}"/>

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

function myInit() {
	setdefaultops();
	loadrowids();
	loadshiptorowids();
	loadcarrierrowids();
	loadtaxrowids();
//	loadbillto();
	initGridWithGlobal(shipConfig);
	initGridWithGlobal(carrierConfig);
	initGridWithGlobal(taxConfig);
	initGridWithGlobal(billConfig);
	initGridWithGlobal(_gridConfig);
	showgridstates();
	showgridinv();
	showtaxgridstates();
	showbillpg();
	showremovelines();
	<c:if test="${ readonly != 'true' }">
	buildCountry($('countryAbbrev'));
	countryChanged('${selectedCountry}');
	showStateOptions('${selectedCountry}', '${selectedState}');
	</c:if>
}


var custReqType = '${customerRequestType}';
var curPriceGroupId = '${priceGroupId}';
var pgmap1 = new Array();
var pgmap2 = new Array();
<c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	pgmap1["${cbean.priceGroupId}"] = '<tcmis:jsReplace value="${cbean.priceGroupName}"/>';
</c:forEach>
<c:forEach var="cbean" items="${vvpriceListCollectionAll}" varStatus="status">
	pgmap2["${cbean.priceGroupId}"] = '<tcmis:jsReplace value="${cbean.priceGroupName}"/>';
</c:forEach>
if( curPriceGroupId && curPriceGroupId != 'New' && pgmap1[curPriceGroupId] == null ) {
	try{
		var i = $('priceGroupId').options.length;
	    setOption(i,pgmap2[curPriceGroupId],curPriceGroupId, "priceGroupId");
//	    alert(curPriceGroupId+":"+pgmap2[curPriceGroupId]);
	}catch(ex){}
}
// -->
</script>

</body>
</html:html>

