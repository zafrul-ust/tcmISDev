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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here 
<script type="text/javascript" src="/js/distribution/newshiptoaddress.js"></script>
-->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
var windowCloseOnEsc = true;
var uomErrMsg = '';

function submitSearchForm() {
	if( uomErrMsg != '' ) {
		showError( uomErrMsg );
		return;
	}
	
	var errmsg = '';
//	if( !$v("partNo") ) {
//		errmsg += '<fmt:message key="label.partnum"/><BR/>\n';
//	}
	if( !$v("customerMsdsOrMixtureNo") ) {
		errmsg += '<fmt:message key="label.msdsnumber"/><BR/>\n';
	}
	if( !$v("partAmount") ) {
		errmsg += '<fmt:message key="label.quantity"/><BR/>\n';
	}
	if( !$v("purchaseSource") ) {
		errmsg += '<fmt:message key="label.source"/><BR/>\n';
	}
	if( !$v("application") ) {
		errmsg += '<fmt:message key="label.workarea"/><BR/>\n';
	}
	if( !$v("workAreaGroup") ) {
		errmsg += '<fmt:message key="label.workareagroup"/><BR/>\n';
	}
	if( !$v("storageDate") ) {
		errmsg += '<fmt:message key="label.storagedate"/><BR/>\n';
	}
	if( !$v("msdsUnitOfMeasure") ) {
		errmsg += '<fmt:message key="label.unitofmeasure"/><BR/>\n';
	}
	if( errmsg != '' ) {
		showError( messagesData.validvalues +"<BR/>\n" +errmsg );
		return;
	}
	
	$('purchaseSourceName').value =  $("purchaseSource").options[$("purchaseSource").selectedIndex].text;
    $("workAreaName").value = $("application").options[$("application").selectedIndex].text;
    $("uAction").value = "process";
	document.genericForm.submit();
}

function showError(errorMessage) {
	$('errorMessagesAreaBody').innerHTML = errorMessage;
	showErrorMessages();
}

function returnChem() {
    try {
        if( '${uploadId}' !='' && window.opener.addNewListchemical ) {
            window.opener.submitRefreshAdd();
            window.close();
        }
    } catch(exx) {}
}

function chemicalInfo() {
  this.partNo='${param.partNo}';
  this.tradeName='${tradeName}';
  this.customerMsdsOrMixtureNo='${param.customerMsdsOrMixtureNo}';
  this.msdsUnitOfMeasure='${param.msdsUnitOfMeasure}';
  this.partAmount='${param.partAmount}';
  this.storageDate='${param.storageDate}';
  this.workAreaName='${param.workAreaName}';
  this.purchaseSource='${param.purchaseSourceName}';
  this.insertDate='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>';
  this.insertedByName='You';//'${personnelBean.fullName}';//'${param.insertedByName}';
  this.entryType='M';
  this.uploadSeqId='${uploadId}';
  this.dataLoadRowId=1;
}

var altFacilityId = new Array(
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			'<tcmis:jsReplace value="${facility.facilityId}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altFacilityName = new Array(
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
		   '<tcmis:jsReplace value="${facility.facilityName}"/>'<c:if test="${!status.last}">,</c:if>
		</c:forEach>
		);
	
		var altWorkAreaGroupId = new Array();
		var altWorkAreaGroupDesc = new Array();
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
		   <c:set var="wagCount" value='0'/>
			altWorkAreaGroupId['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
					''
			<c:forEach var="wagBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:if test="${wagBean.reportingEntityStatus == 'A'}">
					,'<tcmis:jsReplace value="${wagBean.reportingEntityId}"/>'
					<c:set var="wagCount" value='${wagCount+1}'/>
				</c:if>
			</c:forEach>
			);
		   <c:set var="wagCount" value='0'/>
			altWorkAreaGroupDesc['<tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array(
					'<fmt:message key="label.pleaseselect"/>'
		 	<c:forEach var="wagBean"  items="${facility.workAreaGroupColl}" varStatus="status1">
		 	<c:if test="${wagBean.reportingEntityStatus == 'A'}">
					,'<tcmis:jsReplace value="${wagBean.reportingEntityDesc}"/>'
					<c:set var="wagCount" value='${wagCount+1}'/>
				</c:if>
			</c:forEach>
			);
		</c:forEach>
	
		var altWorkAreaArray = new Array();
		var altWorkAreaDescArray = new Array();
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-']=new Array('');
			altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-']=new Array('<fmt:message key="label.pleaseselect"/>');
		</c:forEach>
		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-All'] = new Array(
					''
			<c:forEach var="workAreaBean" items="${facility.facilityWorkAreaColl}" varStatus="status1">
				<c:if test="${workAreaBean.status == 'A'}">
					,'<tcmis:jsReplace value="${workAreaBean.application}"/>'
					<c:set var="workAreaCount" value='${workAreaCount+1}'/>
				</c:if>
			</c:forEach>
			);
			<c:set var="workAreaCount" value='0'/>
			altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-All'] = new Array(
			'<fmt:message key="label.pleaseselect"/>'
			<c:forEach var="workAreaBean" items="${facility.facilityWorkAreaColl}" varStatus="status1">
				<c:if test="${workAreaBean.status == 'A'}">
					,'<tcmis:jsReplace value="${workAreaBean.applicationDesc}"/>'
					<c:set var="workAreaCount" value='${workAreaCount+1}'/>
				</c:if>
			</c:forEach>
		    	);
		</c:forEach>

		<c:forEach var="facility" items="${personnelBean.userFacilityWorkAreaGroupWorkAreaOvBeanCollection}" varStatus="status">
			<c:forEach var="workAreaGroupBean" items="${facility.workAreaGroupColl}" varStatus="status1">
				<c:set var="workAreaCount" value='${0}'/>
				altWorkAreaArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				''
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						,'<tcmis:jsReplace value="${workAreaBean.application}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
				);
				<c:set var="workAreaCount" value='0'/>
				altWorkAreaDescArray['<tcmis:jsReplace value="${facility.facilityId}"/>-<tcmis:jsReplace value="${workAreaGroupBean.reportingEntityId}"/>'] = new Array(
				'<fmt:message key="label.pleaseselect"/>'						
				<c:forEach var="workAreaBean" items="${workAreaGroupBean.workAreaColl}" varStatus="status2">
					<c:if test="${workAreaBean.status == 'A'}">
						,'<tcmis:jsReplace value="${workAreaBean.applicationDesc}"/>'
						<c:set var="workAreaCount" value='${workAreaCount+1}'/>
					</c:if>
				</c:forEach>
		      		);
			</c:forEach>
		</c:forEach>
				

function getUnitOfMeasure() {
	var partNo = $v('partNo');
	var msds = $v('customerMsdsOrMixtureNo');
	if(!msds) return;
	var url = '';
	url += 'inventoryadd.do?uAction=loaddata&callback=setuom';
	url += '&companyId='+encodeURIComponent($v('companyId'));
	url += '&facilityId='+encodeURIComponent($v('facilityId'));
	url += '&msds='+encodeURIComponent(msds);
	url += '&partNo='+encodeURIComponent(partNo);
	callToServer(url);
}

function disableInputs(toggleVal) {
	$('partAmount').disabled = toggleVal;
	$('msdsUnitOfMeasure').disabled = toggleVal;
}

function setuom(returnuom) {
	uomErrMsg = '';
	//if error
	if (returnuom.endsWith('number') || returnuom.startsWith('This')) { // Invalid MSDS or MSDS/PartNo combo
		disableInputs(true);
		showError(returnuom);
		uomErrMsg = returnuom;
		return;
    //else if limited uom
    }else if(!returnuom) {
		disableInputs(false);
		setUomDropDown();
		return;
	}else {
		disableInputs(false);
		var uom = $('msdsUnitOfMeasure');
		for ( var i = uom.length; i > 0; i--) {
			uom[i] = null;
		}
		setOption(0, returnuom, returnuom, "msdsUnitOfMeasure");
	}
}

function setUomDropDown() {
	var uom = $('msdsUnitOfMeasure');
	for ( var i = uom.length; i > 0; i--) {
		uom[i] = null;
	}
	setOption(0, '<fmt:message key="label.pleaseselect"/>', '', "msdsUnitOfMeasure");
    <c:forEach var="uom" items="${unitsOfMeasure}" varStatus="status">
        setOption(${status.count}, '${uom.msdsUnitOfMeasure}', '${uom.msdsUnitOfMeasure}', "msdsUnitOfMeasure");
    </c:forEach>
    $('msdsUnitOfMeasure').value = '${param.msdsUnitOfMeasure}';
}
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
<fmt:message key="label.manualinventoryimport"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--

function showWorkAreaGroupOptions(selectedFacility) {
	var workAreaGroupArray = altWorkAreaGroupId[selectedFacility];
	var workAreaGroupDescArray = altWorkAreaGroupDesc[selectedFacility];

	if (workAreaGroupArray != null) {
		if (workAreaGroupArray.length == 1) {
			setOption(0, workAreaGroupDescArray[0], workAreaGroupArray[0], "workAreaGroup")
		}
		else {
			for (var i = 0; i < workAreaGroupArray.length; i++) {
				setOption(i, workAreaGroupDescArray[i], workAreaGroupArray[i], "workAreaGroup");
			}
		}
	}
}

function workAreaGroupChanged() {
	var facilityO = document.getElementById("facilityId");
	var workAreaGroupO = document.getElementById("workAreaGroup");
	var workAreaO = document.getElementById("application");

	var selectedFacility = facilityO.value;
	var selectedWorkAreaGroup = workAreaGroupO.value;

	if (workAreaO != null) {
		for ( var i = workAreaO.length; i > 0; i--) {
			workAreaO[i] = null;
		}
	}
	showWorkAreaOptions(selectedFacility + "-" + selectedWorkAreaGroup);
//	workAreaO.selectedIndex = 0;
	workAreaO.value = '${param.application}';
}

function showWorkAreaOptions(selectedWorkAreaGroup) {
	var fac = document.getElementById("facilityId");
	var currApp = document.getElementById("workAreaGroup");


	var workAreaArray = altWorkAreaArray[selectedWorkAreaGroup];
	var workAreaDescArray = altWorkAreaDescArray[selectedWorkAreaGroup];

	if (workAreaArray != null) {
		if (workAreaArray.length == 1) {
			setOption(0, workAreaDescArray[0], workAreaArray[0], "application");
		}
		else {
			for (var i = 0; i < workAreaArray.length; i++) {
				setOption(i, workAreaDescArray[i], workAreaArray[i], "application");
			}
		}
	}
}

function myOnLoad() {
    if ('${uploadId}' !='') {
        returnChem();
    }else {
        var selectedFacility = $v('facilityId');
		var workAreaGroupO = $("workAreaGroup");
		var workAreaO = $("application");

		for ( var i = workAreaGroupO.length; i > 0; i--) {
			workAreaGroupO[i] = null;
		}
		for ( var i = workAreaO.length; i > 0; i--) {
			workAreaO[i] = null;
		}
		showWorkAreaGroupOptions(selectedFacility);
		workAreaGroupO.value = '${param.workAreaGroup}';
		workAreaGroupChanged();
		workAreaO.value = '${param.application}';
		$('purchaseSource').value = '${param.purchaseSource}';
        if($v("uAction") == 'process') {
            disableInputs(false);
            getUnitOfMeasure();
        }
    }
}

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


// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="myOnLoad();" onunload="parent.opener.stopShowingWait()" onresize="resizeFrames()">

<tcmis:form action="/inventoryadd.do" onsubmit="return submitFrameOnlyOnce();">

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

<fieldset>
<legend>&nbsp;<fmt:message key="label.manualinventoryimport"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
		<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.partnum"/>:</td>
		<td width="25%" class="optionTitleBoldLeft">
			<input class="inputBox" type="text" name="partNo" id="partNo" value='${param.partNo}' size="25" onchange="getUnitOfMeasure()"/>
		</td>
		<td width="10%" class="optionTitleBoldRight">
			<fmt:message key="label.source" />:&nbsp;
		</td>
		<td width="50%" class="optionTitleLeft" nowrap="true">
			<select name="purchaseSource" id="purchaseSource" class="selectBox" id="source">
				<option value=""><fmt:message key="label.pleaseselect"/></option>
				<c:forEach var="pur" items="${purchasingMethod}" varStatus="status">
						<option value="${pur.purchasingMethodId}">${pur.purchasingMethodName}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.msdsnumber"/>:</td>
		<td width="25%" class="optionTitleBoldLeft">
			<input class="inputBox" type="text" name="customerMsdsOrMixtureNo" id="customerMsdsOrMixtureNo" value='${param.customerMsdsOrMixtureNo}' size="25"  onchange="getUnitOfMeasure()" />
		</td>
		<td  class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.workareagroup"/>:</td>
		<td class="optionTitleBoldLeft">
			<select class="selectBox"  name="workAreaGroup" id="workAreaGroup" size="1"  onchange="workAreaGroupChanged()"></select>
		</td>
	</tr>
	<tr>
		<td  class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.quantity"/>:</td>
		<td width="25%" class="optionTitleBoldLeft">
			<input class="inputBox" type="text" name="partAmount" id="partAmount" value='${param.partAmount}' size="25" disabled/>
		</td>
		<td  class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.workarea"/>:</td>
		<td  class="optionTitleBoldLeft">
			<select name="application" id="application" class="selectBox" size="1"></select>
		</td>
	</tr>
	<tr>
		<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.unitofmeasure"/>:</td>
		<td width="25%" class="optionTitleBoldLeft">
			<select name="msdsUnitOfMeasure" id="msdsUnitOfMeasure" class="selectBox" size="1" disabled>
				<option value=""><fmt:message key="label.pleaseselect"/></option>
			</select>
		</td>
		<td width="15%" class="optionTitleBoldRight" nowrap="true"><fmt:message key="label.storagedate"/>:</td>
		<td width="25%" class="optionTitleBoldLeft" nowrap="nowrap">
            <c:set var="todayDate"><tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/></c:set>
            <input type="text" readonly name="storageDate" id="storageDate" onclick="return getCalendar(document.genericForm.storageDate,null,null,null,document.genericForm.today);" class="inputBox pointer" value='${todayDate}' maxlength="10" size="10"/>
	    </td>
	</tr>												
	<tr>
		<td colspan=4 class="optionTitleBoldLeft">
			 <input name="buttonCreateExcel" id="buttonCreateExcel" type="button"
	                                    class="inputBtns" value="<fmt:message key="label.save"/>"
	                                    onmouseover="this.className='inputBtns inputBtnsOver'"
	                                    onmouseout="this.className='inputBtns'"
	                                    onclick="return submitSearchForm();">
			<input name="uploadListB" id="uploadListB" type="button"
	                                    class="inputBtns" value="<fmt:message key="label.cancel"/>"
	                                    onmouseover="this.className='inputBtns inputBtnsOver'"
	                                    onmouseout="this.className='inputBtns'"
	                                    onclick="window.close();">
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
	<input type="hidden" name="purchaseSourceName" id="purchaseSourceName" value=""/>
	<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
	<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
	<input type="hidden" name="today" id="today" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
	<input name="workAreaName" id="workAreaName" type="hidden" value=""/>
	<input type="hidden" name="type" id="type" value="M"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
 <script type="text/javascript">
 <!--
	 if ( showErrorMessage ) {
//		 alert('${tcmISError}');
		showErrorMessages();
	 }
  showUpdateLinks = true;
 //-->
 </script>
</body>
</html:html>