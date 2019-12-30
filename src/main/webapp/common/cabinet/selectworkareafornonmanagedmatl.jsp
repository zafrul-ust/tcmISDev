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
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- Add any other javascript you need for the page here -->
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
    
<title>
<fmt:message key="label.pleasechooseworkarea"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var windowCloseOnEsc = true;
var dhxWins = null;
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
pleasechooseworkarea:"<fmt:message key="label.pleasechooseworkarea"/>"
};

var cabinetId = new Array(
		{value:'', text: '<fmt:message key="label.pleaseselect"/>'}
		<c:forEach var="bean" items="${authorizedUserWorkAreasColl}" varStatus="authorizedUserWorkAreasStatus">
			, {value:'${bean.applicationId}', text: '<tcmis:jsReplace value="${bean.applicationDesc}" processMultiLines="false" />'}
		</c:forEach>
		);


function myLoad() {
	var start = 0;
	var parentApplicationIdSelSelected = new Array();
	var parentApplicationIdSel = opener.parent.document.getElementById("applicationIdSel");
	if(parentApplicationIdSel.style['display'] == '')
	{
		for (var l=0; l < cabinetId.length; l++)
			setOption(start++,cabinetId[l].text,cabinetId[l].value,"cabinetId");
	}
	else
	{

		parentApplicationIdSelSelected = opener.parent.document.getElementById("applicationId").value.split('|');
		if(parentApplicationIdSelSelected.length < 6)
		{
			setOption(start++,cabinetId[0].text,cabinetId[0].value,"cabinetId");
			for (var m=0; m < cabinetId.length; m++)
				for(var n = 0; n < parentApplicationIdSelSelected.length; n++)	
					if(parentApplicationIdSelSelected[n] == cabinetId[m].value)
						setOption(start++,cabinetId[m].text,cabinetId[m].value,"cabinetId");
		
			start = 0;
			if($('cabinetId').options.length == 1 && $('cabinetId').options[0].value == '')
			{
				for (var l=0; l < cabinetId.length; l++)
					setOption(start++,cabinetId[l].text,cabinetId[l].value,"cabinetId");
			}
		}
		else
			for (var l=0; l < cabinetId.length; l++)
				setOption(start++,cabinetId[l].text,cabinetId[l].value,"cabinetId");	
	}

	if($('cabinetId').options.length < 3)
	{
		$('cabinetId').selectedIndex = 1;
		returnSelected();
	}
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
var closeParentTransit = true;
function returnSelected()
{
	if($v('cabinetId') == '')
		alert(messagesData.pleasechooseworkarea);
	else
	{	
		closeParentTransit = false;
		var nonManagedMaterialUrl = "materialsearchmain.do?userAction=search&facilityId="+$v("facilityId")+"&companyId="+$v("companyId")+"&application="+$v('cabinetId')+"&applicationDesc="+$('cabinetId').options[$('cabinetId').selectedIndex].text;
		opener.openMaterialSearchWindow(nonManagedMaterialUrl);
	}
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="myLoad();" onunload="if(closeParentTransit){try{opener.parent.closeTransitWin();}catch(ex){}}">

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

<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

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
    	<select name="cabinetId" id="cabinetId" class="selectBox">
        </select>
  	</td>
</tr>
<tr>
	<td colspan="4" width="100%" align="center">
          <input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.select"/>" id="select" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick="returnSelected();">
          <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
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
<input type="hidden" name="catalogId" id="catalogId" value="${param.catalogId}">
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}">
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}">
<input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}">
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