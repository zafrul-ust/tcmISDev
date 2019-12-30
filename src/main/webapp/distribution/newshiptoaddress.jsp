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
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/newshiptoaddress.js"></script>

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

var altState = new Array();
var altStateName = new Array();
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
  <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
  <c:set var="stateCollection" value='${status.current.stateCollection}'/>

  altState["<c:out value="${currentCountry}"/>"] = new Array(
		  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
			<c:set var="curState" value="${status1.current.stateAbbrev}"/>
		  	<c:if test="${ curState == 'None' }">
		  		<c:set var="curState" value=""/>
		  	</c:if>
		   <c:choose>
		   <c:when test="${status1.index > 0}">
		        ,'<tcmis:jsReplace value="${curState}"/>'
		   </c:when>
		   <c:otherwise>
			   '<tcmis:jsReplace value="${curState}"/>'
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

// -->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="newshiptoaddress.title"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
legalCompanyNameRequired:"<fmt:message key="error.legalcompanyname.required"/>",
countryRequired:"<fmt:message key="error.country.required"/>",
addressRequired:"<fmt:message key="error.address.required"/>",
cityRequired:"<fmt:message key="error.city.required"/>",
stateRequired:"<fmt:message key="error.state.required"/>",
zipRequired:"<fmt:message key="label.postalcode"/>",
shipToNameRequired:"<fmt:message key="label.shipto"/> <fmt:message key="label.name"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
all:"<fmt:message key="label.all"/>",
invRequired:"<fmt:message key="label.inventorygroup"/>",
atleastoneinvgroup:"<fmt:message key="label.atleastoneinvgroup"/>",
stateProvinceRequired:"<fmt:message key="label.state"/>/<fmt:message key="label.region"/>"
};


var fromCustomerDetail = false;
var fromShipToAddress = false;

<c:choose>
<c:when test="${fromCustomerDetail eq 'Yes'}">
fromCustomerDetail = true;
</c:when>
<c:otherwise>
<c:choose>
<c:when test="${fromShipToAddress eq 'Yes'}">
fromShipToAddress = true;
</c:when>
</c:choose>
</c:otherwise>
</c:choose>
//var beginning = true; 

defaults.ops =  {id:'',name:messagesData.pleaseselect};
defaults.inv =  {id:'',name:messagesData.pleaseselect};
defaults.pg  =  {id:'',name:messagesData.pleaseselect};

/* use the new one
<c:set var="preops" value=""/>
<c:set var="opssep" value=""/>
var opspg = [
	    <c:forEach var="nouse0" items="${opsPgColl}" varStatus="status">
	    <c:set var="curops" value="${status.current.opsEntityId}"/>
	    <c:if test="${ preops ne curops}">
	    ${opssep}
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>',
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
*/

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="setDropDowns();myOnload();closeAllchildren();document.genericForm.customerName.focus();initGridWithGlobal(_gridConfig);showRemoveLine();" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/newshiptoaddress.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${done == 'Y'}">
    done = true;
   </c:when>
   <c:otherwise>
    done = false;
   </c:otherwise>
</c:choose>

<c:set var="priceGroupId" value='${param.priceGroupId}'/>
<c:if test="${empty priceGroupId}">
  <c:set var="priceGroupId" value="${newShipToBean.priceGroupId}"/>
</c:if>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
    <c:set var="selectedShipToCountry" value='USA'/>
    <tcmis:isCNServer>
		<c:set var="selectedShipToCountry" value='CHN'/>
	</tcmis:isCNServer> 

<fieldset>
<legend>&nbsp;<fmt:message key="label.shipto"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
	<td nowrap class="optionTitleBoldRight"><fmt:message key="label.shipto"/>&nbsp;<fmt:message key="label.name"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%">
		<input type="text" class="inputBox" name="customerName" id="customerName" value="" size="30" maxlength="30"/>
		<input type="hidden" name="customerId" id="customerId" value="${param.customerId}"/>
		<input type="hidden" name="billToCompanyId" id="billToCompanyId" value="${param.billToCompanyId}"/>		
		<input type="hidden" name="shipToCountryAbbrev" id="shipToCountryAbbrev" value="${param.remitToCountryAbbrev}"/>
		<input type="hidden" name="shipToStateAbbrev" id="shipToStateAbbrev" value="${param.remitToStateAbbrev}"/>
		<input type="hidden" name="locationDesc" id="locationDesc" value="${param.locationDesc}"/>
		<input type="hidden" name="defaultInvGroup" id="defaultInvGroup" value="${param.defaultInvGroup}"/>
		<input type="hidden" name="defaultPriceGroupId" id="defaultPriceGroupId" value="${defaultPriceGroupColl[0].priceGroupId}"/>
	</td>

<td width="8%" class="optionTitleBoldRight" colspan="2"><fmt:message key="label.state"/>/<fmt:message key="label.region"/>:</td>
	<td width="15%">
		<c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">

            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>         

              <c:if test="${currentCountry == selectedShipToCountry}" >

                <c:set var="stateCollection" value='${status.current.stateCollection}'/>

              </c:if>

          </c:forEach>
          
          <select name="remitToStateAbbrev" id="remitToStateAbbrev" class="selectBox">

            <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status">

              <c:set var="currentState" value='${status.current.stateAbbrev}'/>
			  <c:choose>
				  <c:when test="${currentState == 'None'}" >
				    <option value=""><c:out value="${status.current.state}"/></option>
				  </c:when>
				  <c:otherwise>
				    <option value="<c:out value="${currentState}"/>"><c:out value="${status.current.state}"/></option>
				  </c:otherwise>
			   </c:choose>	

            </c:forEach>

          </select>
	</td>
	
</tr>

<tr>
	<td width="8%" class="optionTitleBoldRight" title='<fmt:message key="label.addressToolTip"/>'><fmt:message key="label.fulladdress"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%"><input type="text" class="inputBox" name="remitToAddressLine1" id="remitToAddressLine1" value="${newShipToBean.shipToAddressLine1Display}" size="30" maxlength="50"/></td>

<td width="8%" class="optionTitleBoldRight" colspan="2"><fmt:message key="label.city"/>:<span style='font-size:12.0pt;color:red'>*</span></td>

    <td width="15%"><input type="text" class="inputBox" name="remitToCity" id="shipToCity" value="${param.remitToCity}" size="20" maxlength="30"/></td>

</tr>

		
	
</tr>

<tr>
<td width="8%">&nbsp;</td>

<td width="15%"><input type="text" class="inputBox" name="remitToAddressLine2" id="remitToAddressLine2" value="${newShipToBean.shipToAddressLine2Display}" size="30" maxlength="50" /></td>
	<td width="8%" class="optionTitleBoldRight" colspan="2"><fmt:message key="label.postalcode"/>:<span style='font-size:12.0pt;color:red'>*</span></td>

	<td width="15%"><input type="text" class="inputBox" name="shipToZip" id="shipToZip" value="${param.remitToZip}" size="10" maxlength="10" />
 		<span id="shipToZipPlusSpan" style="display:none">
		-<input type="text" class="inputBox" name="shipToZipPlus" id="shipToZipPlus" value="${param.remitToZipPlus}" size="5" maxlength="4" /></span>
	</td>
</tr>

<tr>
<td width="8%">&nbsp;</td>
<td width="15%"><input type="text" class="inputBox" name="remitToAddressLine3" id="remitToAddressLine3" value="${newShipToBean.shipToAddressLine3Display}" size="30" maxlength="50" /></td>
	
	<td width="8%" class="optionTitleBoldRight" colspan="2"><fmt:message key="label.country"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
  	<td width="15%">
          <select name="remitToCountryAbbrev" id="remitToCountryAbbrev" class="selectBox" onchange="shipCountryChanged();">

          <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">

            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>         

              <c:if test="${currentCountry == selectedShipToCountry}" >

                <c:set var="stateCollection" value='${status.current.stateCollection}'/>

              </c:if>

            <c:choose>

              <c:when test="${currentCountry == selectedShipToCountry}">

                <option value="<c:out value="${currentCountry}"/>" selected><c:out value="${status.current.country}"/></option>

              </c:when>

              <c:otherwise>

                <option value="<c:out value="${currentCountry}"/>"><c:out value="${status.current.country}"/></option>

              </c:otherwise>

            </c:choose>

          </c:forEach>

          </select>

	</td>

<tr>
<td width="8%">&nbsp;</td>
<td width="15%"><input type="text" class="inputBox" name="remitToAddressLine4" id="remitToAddressLine4" value="${newShipToBean.shipToAddressLine4Display}" size="30" maxlength="50" /></td>
	<td width="10%" class="optionTitleBoldRight" nowrap colspan="2"><fmt:message key="label.operatingentity"/>:</td>
     <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">        
         </select>
      </td>	
	
</tr>

<tr>

   <td width="8%">&nbsp;</td>
   <td width="15%"><input type="text" class="inputBox" name="remitToAddressLine5" id="remitToAddressLine5" value="${newShipToBean.shipToAddressLine5Display}" size="30" maxlength="50" /></td>
	   <td width="15%" class="optionTitleBoldRight" nowrap colspan="2"><fmt:message key="label.globaldefaultinventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>
</tr>
<tr>
 <td nowrap class="optionTitleBoldRight"><fmt:message key="label.fieldsalesrep"/>:</td>
    <td class="optionTitleBoldLeft">    
    <input type="text" class="invGreyInputText" name="fieldSalesRepName" id="fieldSalesRepName" value="${param.fieldSalesRepName}" size="15" readonly/>
    <input type="hidden" name="fieldSalesRepId" id="fieldSalesRepId" value="${param.fieldSalesRepId}"/>
	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="cLookupButton" id="cLookupButton" value="" onclick="getSalesRep()"/>	
    <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearFieldSalesRep()"><fmt:message key="label.clear"/> </button>
    </td>
    <td class="optionTitleBoldRight" rowspan="3">
    <fmt:message key="label.notes"/>:
    </td> 
	<td nowrap class="optionTitleBoldLeft" colspan="2" rowspan="3">
	    <textarea name="shiptoNotes" id="shiptoNotes" class="inputBox" rows="3" cols="60"></textarea>
	</td>
</tr>
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.salesagent"/></td>
    <td class="optionTitleBoldLeft">    
    <input   type="text" class="invGreyInputText" name="salesAgent" id="salesAgent" value="${newShipToBean.salesAgentName}" size="15" readonly/>
    <input type="hidden" name="salesAgentId" id="salesAgentId" value="${newShipToBean.salesAgentId}"/>
	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="cLookupButton" id="cLookupButton" value="" onclick="getSalesAgent()"/>	
    <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearSalesAgent()"><fmt:message key="label.clear"/> </button>
    </td>
</tr>
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.globalshiptopricelist"/>:</td>
  <td class="optionTitleBoldLeft">    
    <select name="priceGroupId" id="priceGroupId" class="selectBox">
  	</select>
  </td>
</tr>
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.msdslocaleoverride"/>:</td>
  <td class="optionTitleBoldLeft">    
    <select name="msdsLocaleOverride" id="msdsLocaleOverride" class="selectBox">
    	<option value=""><fmt:message key="label.pleaseselect"/></option>
		<c:forEach var="cbean" items="${vvLocale}" varStatus="status">
			<option value="${cbean.localeCode}">${cbean.localeDisplay}</option>
		</c:forEach>  	
  	</select>
  </td>
  <td/>
  <td/>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
	<td width="15%" colspan="6">
	<b><fmt:message key="label.shippg"/><br/></b>
<%--
<fieldset>
<legend>&nbsp;<fmt:message key="label.shipto"/>&nbsp;${facilityColl[0].facilityName}</legend>

&#150;&#150;&#150;&#150;&#150;&#150;
 <span id="updateLine" style='color:blue;cursor:pointer'>
<a onclick="submitUpdate();"><fmt:message key="label.update"/></a>	
&nbsp;
</span>
|&nbsp;
<span id="closeSpan" style="color:blue;cursor:pointer">
<a onclick="window.close()"><fmt:message key="label.close"/></a>
&nbsp;
</span>
|&nbsp;
--%>
<c:set var="billPermission" value="N"/>
<tcmis:opsEntityPermission indicator="true" userGroupId="priceGroupAssignment">
  <c:set var="billPermission" value="Y"/>
</tcmis:opsEntityPermission>

<c:if test="${billPermission == 'Y'}">

<span id="newShiptoAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewShiptosLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newShiptoRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removeShiptoLine()"><fmt:message key="label.removeLine"/></a>
</span>
</c:if>
<div id="shipColl" style="height:130px;width:95%;display: none;" ></div>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
//to build price list drop down.
function submitUpdate() {
//	  numberOfRows = document.getElementById('totalLines').value;
	  var flag = true;//validateUpdateForm(numberOfRows);
	  if(flag) {
	    var action = document.getElementById("uAction");
	    action.value = 'update';
//	    submitOnlyOnce();
	    haasGrid.parentFormOnSubmit();
		showPleaseWait();
	 	window.setTimeout("document.genericForm.submit();",500); 	  
//	    document.genericForm.submit();
//		parent.showPleaseWait();
	  }
	  return flag;
}

var pgdefaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true},
	   	   pg: {id:'',name:'<fmt:message key="label.pleaseselect"/>'}
	   }

<c:set var="preops" value=""/>
<c:set var="opssep" value=""/>
var opspg = [
	    <c:forEach var="nouse0" items="${opsPgColl}" varStatus="status">
<c:set var="hasPerm" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="priceGroupAssignment">
<c:set var="hasPerm" value="Y"/>
</tcmis:opsEntityPermission>
	    <c:set var="curops" value="${status.current.opsEntityId}"/>
	    <c:if test="${ preops ne curops}">
	    ${opssep}
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>',
		  companyId:'${status.current.opsCompanyId}',
          hasPerm:'${hasPerm}',
          coll: [ 
        	 	  {  id: '${status.current.priceGroupId}',
//           	 	   name: '<tcmis:jsReplace value="${status.current.priceGroupName}"/>'
           	 	     name:'${status.current.priceGroupId}',
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

var opsEntityIdArr = new Array();
for( i=0;i < opspg.length; i++) { // opshubig
	opsEntityIdArr[i] = {text:opspg[i].name,value:opspg[i].id}
}

var rowids = new Array();
var opsEntityId = new Array();
var priceGroupId= new Array();
var inventoryGroupDefault = new Array();

<c:forEach var="bean" items="${beanColl}" varStatus="status">
opsEntityId[${status.index +1}] = opsEntityIdArr;
rowids[${status.index +1}] = ${status.index +1}; 
</c:forEach>

function buildNewOpsValudset() {
	var newItemIdArray = new Array();
	for( i=0;i < opsEntityIdArr.length; i++) 
		newItemIdArray[opsEntityIdArr[i].value] = {text:opsEntityIdArr[i].text,value:opsEntityIdArr[i].value};

//	for( rowid = 1; rowid <= ind; rowid++ ) {
//  delete the incoming opsentity id	
	delete( newItemIdArray[$v('opsEntityId')] ) ;
	for( rowid in rowids ) {
		val = gridCellValue(haasGrid,rowid,"opsEntityId");
		if( val )
			delete( newItemIdArray[val] ) ;
	}
	var valArray = new Array();
	for( v in newItemIdArray )
		valArray[valArray.length] = newItemIdArray[v];
	return valArray;
}

function buildNewGridIgValudset(opsid) {
//   	var opsid = $v('opsEntityId');
   	var newInvArray = new Array();
		newInvArray[newInvArray.length] = {text:messagesData.pleaseselect,value:''};
   	if( !opsid ) return newInvArray;

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
}

function buildNewpriceGroupId(opsid,priceid) {
//	var opsid = $v('opsEntityId');
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

<c:forEach var="bean" items="${beanColl}" varStatus="status">
	opsEntityId[ ${status.index +1}] = opsEntityIdArr;// initial value set buildNewOpsValudset();
	priceGroupId[${status.index +1}] = buildNewpriceGroupId('${bean.opsEntityId}','${bean.priceGroupId}');
	inventoryGroupDefault[${status.index +1}] = buildNewGridIgValudset('${bean.opsEntityId}');
</c:forEach>


function shipOpsChangedPG(rowId,columnId,invval) {
	
	  var selectedOps = gridCellValue(haasGrid,rowId,columnId);

//pricegroupid	  
	  var inv = $("priceGroupId"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewpriceGroupId(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.pleaseselect,"", "priceGroupId"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "priceGroupId"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}

function shipOpsChangedINV(rowId,columnId,invval) {
	
	  var selectedOps = gridCellValue(haasGrid,rowId,columnId);

//pricegroupid	  
	  var inv = $("inventoryGroupDefault"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewGridIgValudset(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,"","", "inventoryGroupDefault"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "inventoryGroupDefault"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}

function shipOpsChanged(rowId,columnId) {
	shipOpsChangedPG(rowId,columnId);
	shipOpsChangedINV(rowId,columnId)
	
}



<c:set var="shipCount" value='0'/>
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

var shiptoData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>

        <c:if test="${readonly == 'true'}">
	    </c:if>
        <c:if test="${readonly != 'true'}">
   		</c:if>

        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${billPermission}',//'${gp}',
 'N',//'${gp}',
 '${bean.opsEntityId}',
 '${bean.priceGroupId}',
 '${bean.inventoryGroupDefault}',
 ''
 ]}
    <c:set var="shipCount" value='${shipCount+1}'/>
     </c:forEach>
    ]};

var shipConfig = [
  {
  	columnId:"permission"
  },
{
  	columnId:"opsEntityIdPermission"
},
{
  	columnId:"opsEntityId",
	columnName:"<fmt:message key="label.operatingentity"/>",
    onChange:shipOpsChanged,
    permission:true,
	width:20
	,type:"hcoro"
},
{
  	columnId:"priceGroupId",
	columnName:"<fmt:message key="label.pricelist"/>",
    //onChange:shipOpsChanged,
	width:20
	,type:"hcoro"
},
{
  	columnId:"inventoryGroupDefault",
	columnName:"<fmt:message key="label.inventorygroup"/>",
	width:20
	,type:"hcoro"
} ,
{
  	columnId:"changed"
}
];

function removeShiptoLine() {
	if( selectedRowId == null ) return;
	if( cellValue(selectedRowId,"changed") == 'New' ) {
	haasGrid.deleteRow(selectedRowId);
	delete( rowids[selectedRowId] ) ;
	if( haasGrid.getRowsNum() == 0 ) 
		$('newShiptoRemoveLine').style["display"] = "none";
	selectedRowId = null;
	return ;
	}
	cell(selectedRowId,"changed").setValue("Remove");
	submitUpdate();
}

/* This one is not going back to server.
function removeShiptoLine() {
	if( selectedRowId == null ) return;
	haasGrid.deleteRow(selectedRowId);
	delete( rowids[selectedRowId] ) ;
	if( haasGrid.getRowsNum() == 0 ) 
		$('newShiptoRemoveLine').style["display"] = "none";
	selectedRowId = null;
	return ;
}
*/
var selectedRowId = null;

function addNewShiptosLine() {

 var rowid = (new Date()).valueOf();
 ind = haasGrid.getRowsNum();
	  
	  count = 0 ;
	  var arr = buildNewOpsValudset();
	  if( !arr || !arr.length ) return; 
	  opsEntityId[ rowid ] = arr;
      priceGroupId[ rowid ] = buildNewpriceGroupId(opsEntityId[ rowid ][0].value );
  	  inventoryGroupDefault[ rowid ] = buildNewGridIgValudset( opsEntityId[ rowid ][0].value );
    
	  if(priceGroupId[ rowid ].length == 0) {
		  priceGroupId[ rowid ] = new Array( {text:messagesData.all,value:""} );
	  }
	  if(inventoryGroupDefault[ rowid ].length == 0) {
		  inventoryGroupDefault[ rowid ] = new Array( {text:"",value:""} );
	  }
		  
	  
	var thisrow = haasGrid.addRow(rowid,"",ind);

// opsentityid
	  haasGrid.cells(rowid, count++).setValue('Y');
	  haasGrid.cells(rowid, count++).setValue('Y');
	  haasGrid.cells(rowid, count++).setValue(opsEntityId[ rowid ][0].value);
	  haasGrid.cells(rowid, count++).setValue(priceGroupId[ rowid ][0].value);
	  haasGrid.cells(rowid, count++).setValue(inventoryGroupDefault[ rowid ][0].value);
	  haasGrid.cells(rowid, count++).setValue('New');
	  
	  rowids[""+rowid] = rowid;
	  selectedRowId = rowid;
	  haasGrid.selectRow(haasGrid.getRowIndex(rowid));
	  $('newShiptoRemoveLine').style.display="";

}

function selectRow()
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

 selectedRowId = rowId0;
 
} //end of method

function showRemoveLine() {
	if( haasGrid != null ) {
		var line = haasGrid.getRowsNum();
		if( line && $('newShiptoRemoveLine') ) 
			$('newShiptoRemoveLine').style.display="";
	}
}

_gridConfig.submitDefault = true;
_gridConfig.divName = 'shipColl';
_gridConfig.config = 'shipConfig';	     // the column config var name, as in var config = { [ columnId:..,columnName...
_gridConfig.beanData = 'shiptoData';
//_gridConfig.rowSpan = -1;
//_gridConfig.beanGrid = 'newFeesGrid';
_gridConfig.onRowSelect = selectRow;
_gridConfig.onRightClick = selectRow;
//_gridConfig.singleClickEdit = true;

//-->
</script>
<%-- 
</fieldset>
--%>
	</td>
</tr>
<tr>
	<td width="15%" colspan="4">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return newAddress();">
        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
	</td>

</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
</fieldset>


<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="hshipToLocationId" id="hshipToLocationId" type="hidden" value="${newShipToBean.shipToLocationId}"/>
<input name="hshipToCompanyId" id="hshipToCompanyId" type="hidden" value="${newShipToBean.shipToCompanyId}"/>
<input name="fromShipToAddress" id="fromShipToAddress" type="hidden" value="${param.fromShipToAddress}"/>
<input name="fromCustomerDetail" id="fromCustomerDetail" type="hidden" value="${param.fromCustomerDetail}"/>
<input name="selectedInventoryGroup" id="selectedInventoryGroup" type="hidden" value="${newShipToBean.inventoryGroupDefault}"/>
<input name="selectedInventoryGroupName" id="selectedInventoryGroupName" type="hidden" value="${newShipToBean.inventoryGroupName}"/>

<%-- bug?
<input name="hlocationType" id="hlocationType" type="hidden" value="${newShipToBean.locationDesc}"/>
 --%>
<input name="hlocationType" id="hlocationType" type="hidden" value="${newShipToBean.locationType}"/>
<input name="hlocationShortName" id="hlocationShortName" type="hidden" value="${newShipToBean.locationShortName}"/>
<%-- bug?
<input name="hlocationDesc" id="hlocationDesc" type="hidden" value="${newShipToBean.locationType}"/>
 --%>
<input name="hlocationDesc" id="hlocationDesc" type="hidden" value="${newShipToBean.locationDesc}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>
</body>
</html:html>