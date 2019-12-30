<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
    
<title>
<fmt:message key="label.addnonmanagedmatl"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
windowCloseOnEsc = true;
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
msdsnumber:"<fmt:message key="label.msdsnumber"/>",
averageAmount:"<fmt:message key="label.averageamount"/>",
maxAmount:"<fmt:message key="label.maxamount"/>",
avgGreaterMax:"<fmt:message key="label.avggreatermax"/>",
unit:"<fmt:message key="label.unit"/>",
tierIIStorageMethod:"<fmt:message key="label.tieriistoragemethod"/>",
workarea:"<fmt:message key="label.workarea"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
pleasechooseworkarea:"<fmt:message key="label.pleasechooseworkarea"/>",
nodatafound:"<fmt:message key="main.nodatafound"/>",
tierIITemperature:"<fmt:message key="label.tieriistoragetemperature"/>",
replacenonmanagedmat:"<fmt:message key="label.replacenonmanagedmat"/>",
largestContainerSize:"<fmt:message key="label.largestcontainersize"/>"
};

function myOnload() {
	var start = 0;
	var sizeUnitArray = buildsizeUnitDropdownArray('${sizeUnitString}');
	start = 0;
	setOption(start++,messagesData.pleaseselect,"","sizeUnit");
	for ( var k=0; k < sizeUnitArray.length; k++) {
		setOption(start++,sizeUnitArray[k].text,sizeUnitArray[k].value,"sizeUnit");
	}
	  
	$("sizeUnit").selectedIndex = 0;
	
	start = 0;
	for ( var j=0; j < tierIIStorage.length; j++) {
		setOption(start++,tierIIStorage[j].text,tierIIStorage[j].value,"tierIIStorage");
	}	  
	$("tierIIStorage").selectedIndex = 0;

	start = 0;
	for ( var j=0; j < tierIITemperature.length; j++) {
		setOption(start++,tierIITemperature[j].text,tierIITemperature[j].value,"tierIITemperature");
	}  
	$("tierIITemperature").selectedIndex = 0;
	
}

function buildsizeUnitDropdownArray(sizeUnitOption) {
	  var splitResult = sizeUnitOption.split(";");
	  var sizeUnitArray = new Array();
	  
	  if(sizeUnitOption == null || sizeUnitOption.length == 0) {
	    sizeUnitArray[0] = {text:'',value:''};
	  }
	  else {
		  for(i = 0; i < splitResult.length; i++){
			sizeUnitArray[i] = {text:splitResult[i],value:splitResult[i]};
	  	  }
	  }
	 
	  return sizeUnitArray;
}

function returnMaterial() {
	var errorMsg = messagesData.validvalues+"\n";
	
	var count = 0;
	if($v("msdsno").length  == 0) {
		errorMsg += "\n"+messagesData.msdsnumber;
		count = 1;
	}
	var avgAmount = $v("avgAmount");
	if(!isFloat($v("avgAmount"), false) || $v("avgAmount") <= 0*1) {
		errorMsg += "\n"+messagesData.averageAmount;
		count = 1;
	}
	var maxAmount = $v("maxAmount");
	if(!isFloat(maxAmount, false) || maxAmount <= 0*1) {
		errorMsg += "\n"+messagesData.maxAmount;
		count = 1;
	}
	
	var largestContainerSize = $v("largestContainerSize");
	if(!isFloat(largestContainerSize, false) || largestContainerSize <= 0*1) {
		errorMsg += "\n"+messagesData.largestContainerSize;
		count = 1;
	}
	
	if(maxAmount != '' && maxAmount != '&nbsp;' && avgAmount != '' &&  avgAmount != '&nbsp;')
		if(parseFloat(avgAmount) > parseFloat(maxAmount))
		{
			errorMsg += "\n"+ messagesData.avgGreaterMax;
			count = 1;
		}	
	if($v("sizeUnit").length  == 0) {
		errorMsg += "\n"+messagesData.unit;
		count = 1;
	}
	if(tierIIRequired)
	{
		if($v("tierIIStorage").length  == 0) {
			errorMsg += "\n"+messagesData.tierIIStorageMethod;
			count = 1;
		}
	
		if($v("tierIITemperature").length  == 0) {
			errorMsg += "\n"+messagesData.tierIITemperature;
			count = 1;
		}
	}
	
	if( count > 0) {
		alert(errorMsg);
		return false;
	} 
	/*else {
		if(opener.parent.opener.document.title='=Work Area Stock Mgmt')
			opener.parent.opener.addMaterial($v("cabinetId"), $v("materialId"), $v("avgAmount"), $v("maxAmount"), $v("sizeUnit"), $v("catalogId"), $v("catalogCompanyId"), $v("msdsno"), $v("tierIIStorage"),$v("tierIITemperature")); 
		else
			opener.parent.opener.addMaterial($v("cabinetId"), $v("materialId"), $v("amount"), $v("sizeUnit"), $v("catalogId"), $v("catalogCompanyId"), $v("msdsno") ); 
		window.close();
		opener.parent.close();
	}*/
	nonManagedMatCountCheck();
}

function nonManagedMatCountCheck()
{
	var app = $v('cabinetId');
	callToServer("addnonmanagedmaterial.do?uAction=nonManagedMatCountCheck&companyId=" + $v('companyId') + "&facilityId=" + $v('facilityId')  + "&application=" + app + "&materialId=" + $v('materialId'));
}

function retCount(count)
{
	if(parseInt(count) > 1)
	{
	    if (confirm(messagesData.replacenonmanagedmat))
	    {
	    	addToOriginalParentGrid();
	    	window.close();
			opener.parent.close();
	    }
		
	}
	else
		{
			addToOriginalParentGrid();
			window.close();
			opener.parent.close();
		}
		
}

function addToOriginalParentGrid()
{
	var originalOpener = opener.parent.originalOpener;
	if(originalOpener != null)
	{
		if(originalOpener.document.title='=Work Area Stock Mgmt')
			originalOpener.addMaterial($v("cabinetId"), $v("materialId"), $v("avgAmount"), $v("maxAmount"), $v("sizeUnit"), $v("catalogId"), $v("catalogCompanyId"), $v("msdsno"), $v("tierIIStorage"),$v("tierIITemperature"),$v("largestContainerSize")); 
		else
			originalOpener.addMaterial($v("cabinetId"), $v("materialId"), $v("amount"), $v("sizeUnit"), $v("catalogId"), $v("catalogCompanyId"), $v("msdsno") ); 
	}
	else{
		if(opener.parent.opener.document.title='=Work Area Stock Mgmt')
			opener.parent.opener.addMaterial($v("cabinetId"), $v("materialId"), $v("avgAmount"), $v("maxAmount"), $v("sizeUnit"), $v("catalogId"), $v("catalogCompanyId"), $v("msdsno"), $v("tierIIStorage"),$v("tierIITemperature"),$v("largestContainerSize")); 
		else
			opener.parent.opener.addMaterial($v("cabinetId"), $v("materialId"), $v("amount"), $v("sizeUnit"), $v("catalogId"), $v("catalogCompanyId"), $v("msdsno") ); 
	}
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="myOnload();" onunload="opener.parent.closeTransitWin();" onresize="resizeFrames()">

<tcmis:form action="/addnonmanagedmaterial.do" onsubmit="return submitFrameOnlyOnce();">

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
var tierIIStorage = new Array(
		{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
		<c:forEach var="bean" items="${vvUnidocsStorageContainerBeanColl}" varStatus="unidocsStorageStatus">
			, {value:'${bean.unidocsStorageContainer}', text: '<tcmis:jsReplace value="${bean.description}" processMultiLines="false" />'}
		</c:forEach>
		);

var tierIITemperature = new Array(
		{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
		<c:forEach var="bean" items="${vvTierIITemperatureCodeBeanColl}" varStatus="vvTierIITemperatureCodeStatus">
			, {value:'${bean.tierIITemperatureCode}', text: '<tcmis:jsReplace value="${bean.tierIITemperature}" processMultiLines="false" />'}
		</c:forEach>
		);

var tierIIRequired = false;
<c:set var="tierIIRequired" value="N"/>
<tcmis:featureReleased feature="TierIIRequired" scope="ALL" companyId="${param.companyId}">
    tierIIRequired = true;
    <c:set var="tierIIRequired" value="Y"/>
</tcmis:featureReleased>
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


<fieldset>
<legend><fmt:message key="label.addnonmanagedmatl"/></legend>

<table>
<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.workarea"/>:<span style='font-size:12.0pt;color:red'>*</span>
	</td>
    <td width="15%" class="optionTitle">
    	 ${param.applicationDesc}
  	</td>
</tr>

<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.msdsnumber"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%" class="optionTitle">
		<c:choose>
		   <c:when test="${null == param.msdsNumber || empty param.msdsNumber}">
		    <input type="text" class="inputBox" name="msdsno" id="msdsno" value="" size="10" maxlength="10"/>
		   </c:when>
		   <c:otherwise>
		    ${param.msdsNumber}
		    <input type="hidden" name="msdsno" id="msdsno" value="${param.msdsNumber}" />
		   </c:otherwise>
		</c:choose>
	</td>
</tr>

<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.averageamount"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="avgAmount" id="avgAmount" value="" size="10" maxlength="10"/>
	</td>
</tr>
<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.maxamount"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="maxAmount" id="maxAmount" value="" size="10" maxlength="10"/>
	</td>
</tr>
<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.largestcontainersize"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%" class="optionTitle">
		<input type="text" class="inputBox" name="largestContainerSize" id="largestContainerSize" value="" size="10" maxlength="10"/>
	</td>
</tr>

<tr>
	<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.unit"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%" class="optionTitle">
		<select name="sizeUnit" id="sizeUnit" class="selectBox">
        </select>
<%-- 	<input type="text" class="inputBox" name="sizeUnit" id="sizeUnit" value="" size="10" maxlength="10"/>  --%>	
	</td>
</tr>

	<tr>
		<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.tieriistoragemethod"/>:<c:if test="${tierIIRequired == 'Y'}"><span style='font-size:12.0pt;color:red'>*</span></c:if></td>
		<td width="15%" class="optionTitle">
			<select name="tierIIStorage" id="tierIIStorage" class="selectBox">
	        </select>
		</td>
	</tr>
	<tr>
		<td width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.tieriistoragetemperature"/>:<c:if test="${tierIIRequired == 'Y'}"><span style='font-size:12.0pt;color:red'>*</span></c:if></td>
		<td width="15%" class="optionTitle">
			<select name="tierIITemperature" id="tierIITemperature" class="selectBox">
	        </select>
		</td>
	</tr>
	<tr>
		<td colspan="4" width="100%" align="center">
	          <input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.add"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
	          		 onclick="returnMaterial();">
	          <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
	          		 onclick="window.close();"/>
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

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="materialId" id="materialId" value="${param.materialId}">
<input type="hidden" name="catalogId" id="catalogId" value="${param.catalogId}">
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}">
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}">
<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}">
<input type="hidden" name="cabinetId" id="cabinetId" value="${param.application}">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html>