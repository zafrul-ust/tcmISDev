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

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>

<title>
</title>

<script language="JavaScript" type="text/javascript">
<!--
windowCloseOnEsc = true;
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	itemInteger:"<fmt:message key="error.item.integer"/>",
	pleaseselect:"<fmt:message key="label.norowselected"/>",
	requestcreated:"Successfully created request # "};
useLayout = false;

<c:set var="lastCompanyId" value=""/>
<c:set var="lastFacilityId" value=""/>
<c:set var="lastCatalogId" value=""/>
var companyFacCatalogArray = [
<c:forEach var="hubvar" items="${companyFacCatalogCollection}" varStatus="status">
<c:choose>
<c:when test="${lastCompanyId eq status.current.companyId}">

	<c:choose>
	<c:when test="${lastFacilityId eq status.current.facilityId}">
		,{id:"<tcmis:jsReplace value='${status.current.catalogId}'/>"
			,name:"<tcmis:jsReplace value='${status.current.catalogDesc}'/>"}
		<c:set var="lastCatalogId" value="${status.current.catalogId}"/>
	</c:when>
	<c:otherwise>
	<c:if test="${not empty lastFacilityId}">
	]}
	</c:if>
	<c:set var="lastFacilityId" value="${status.current.facilityId}"/>
	<c:set var="lastCatalogId" value="${status.current.catalogId}"/>
	,{id:"<tcmis:jsReplace value='${status.current.facilityId}'/>",
		name:"<tcmis:jsReplace value='${status.current.facilityName}'/>",
		catalogCompanyId:"<tcmis:jsReplace value='${status.current.catalogCompanyId}'/>",
		catalogs:[
			{id:"<tcmis:jsReplace value='${status.current.catalogId}'/>"
				,name:"<tcmis:jsReplace value='${status.current.catalogDesc}'/>"}
	</c:otherwise>
	</c:choose>

</c:when>
<c:otherwise>
<c:if test="${not empty lastCompanyId}">
]}]},
</c:if>
<c:set var="lastCompanyId" value="${status.current.companyId}"/>
<c:set var="lastFacilityId" value="${status.current.facilityId}"/>
<c:set var="lastCatalogId" value="${status.current.catalogId}"/>
    {id:"<tcmis:jsReplace value='${status.current.companyId}'/>",
    	name:"<tcmis:jsReplace value='${status.current.companyName}'/>",
    	facilities:[
    	            {id:"<tcmis:jsReplace value='${status.current.facilityId}'/>",
    	            	name:"<tcmis:jsReplace value='${status.current.facilityName}'/>",
    	            	catalogCompanyId:"<tcmis:jsReplace value='${status.current.catalogCompanyId}'/>",
    	            	catalogs:[
    	            	          {id:"<tcmis:jsReplace value='${status.current.catalogId}'/>"
    	            	        	  , name:"<tcmis:jsReplace value='${status.current.catalogDesc}'/>"}
</c:otherwise>
</c:choose>
</c:forEach>
<c:choose>
<c:when test="${empty companyFacCatalogCollection}">];</c:when>
<c:otherwise>]}]}];</c:otherwise>
</c:choose>


var facilityLocales = {
	<c:set var="lastCompanyId" value=""/>
	<c:set var="lastFacilityId" value=""/>
	<c:forEach var="locale" items="${localeCollection}" varStatus="status">
	
	<c:choose>
	<c:when test="${lastCompanyId eq status.current.companyId}">
	
		<c:choose>
		<c:when test="${lastFacilityId eq status.current.facilityId}">
			,{ id:${status.index +1},
				data:[
					 '${locale.available?"Y":"N"}',
					 '',
					 '${locale.localeId}',
					 '<tcmis:jsReplace value="${locale.localeCode}" processMultiLines="false"/>',
					 '<tcmis:jsReplace value="${locale.localeDisplay}" processMultiLines="false"/>'
				]
			}
		</c:when>
		<c:otherwise>
		<c:if test="${not empty lastFacilityId}">
		]
		</c:if>
		<c:set var="lastFacilityId" value="${status.current.facilityId}"/>
		,"<tcmis:jsReplace value='${status.current.facilityId}'/>":[
		                     			{ id:${status.index +1},
		                     				data:[
		                     					 '${locale.available?"Y":"N"}',
		                     					 '',
		                     					 '${locale.localeId}',
		                     					 '<tcmis:jsReplace value="${locale.localeCode}" processMultiLines="false"/>',
		                     					 '<tcmis:jsReplace value="${locale.localeDisplay}" processMultiLines="false"/>'
		                     				]
		                     			}
		</c:otherwise>
		</c:choose>
	
	</c:when>
	<c:otherwise>
	<c:if test="${not empty lastCompanyId}">
	]},
	</c:if>
	<c:set var="lastCompanyId" value="${status.current.companyId}"/>
	<c:set var="lastFacilityId" value="${status.current.facilityId}"/>
	"${status.current.companyId}":{
		"${status.current.facilityId}":[
				{ id:${status.index +1},
					data:[
						 '${locale.available?"Y":"N"}',
						 '',
						 '${locale.localeId}',
						 '<tcmis:jsReplace value="${locale.localeCode}" processMultiLines="false"/>',
						 '<tcmis:jsReplace value="${locale.localeDisplay}" processMultiLines="false"/>'
					]
				}
	</c:otherwise>
	</c:choose>
	</c:forEach>
<c:choose>
<c:when test="${empty localeCollection}">};</c:when>
<c:otherwise>]}};</c:otherwise>
</c:choose>


var columnConfig = [
  			{columnId:"permission"},
  			{columnId:"grab", columnName:'<fmt:message key="label.grab"/>', type:'hchstatus', align:'center', width:2},
  			{columnId:"localeId"},
  			{columnId:"localeCode"},
  			{columnId:"localeDisplay", columnName:'<fmt:message key="label.language"/>'}
];

var localeGridConfig = {
		divName:'localeDiv',
		beanData:'jsonMainData',
		beanGrid:'beanGrid',
		config:'columnConfig',
		rowSpan:false,
		noSmartRender: true,
		submitDefault:true
};

function displayLocaleColumns() {
	if ( ! jQuery.isEmptyObject(facilityLocales)) {
		clearLocaleColumns();
		var companyId = $v("companyId");
		var facilityId = $v("facilityId");
		var localeArray = facilityLocales[companyId][facilityId];
		//looping thru master data
		try {
			for (var i = 0; i < localeArray.length; i++) {
				beanGrid.addRow(i+1,localeArray[i].data,i);
			}
			$("totalLines").value = localeArray.length;
		} catch(e) {
			$("totalLines").value = 0;
		}
	}
}

function clearLocaleColumns() {
	for(var i = beanGrid.getRowsNum(); i > 0; i--) {
		beanGrid.deleteRow(i);
	}
}

function initializeGrid() {
	initGridWithGlobal(localeGridConfig);
}

function submitRequest() {
	var atLeastOneChecked = false;
	for(var i = beanGrid.getRowsNum(); i > 0; i--) {
		try {
			if ($("grab"+i).checked) {
				atLeastOneChecked = true;
			}
		} catch(e){}
	}
	
	if (atLeastOneChecked) {
		beanGrid.parentFormOnSubmit();
		$("uAction").value = "request";
		genericForm.submit();
	}
	else {
		alert(messagesData.pleaseselect);
	}
}

function showCatalog(selectedCompany,selectedInv,selectedCatalog) {
	  var catalogArray = companyFacCatalogArray[selectedCompany].facilities[selectedInv].catalogs;
	  
	  var selectI = 0;
	  var inserted = 0 ;
	  
	  var cat = $("catalogId");
	  for (var i = cat.length; i > 0; i--) {
	    cat[i] = null;
	  }
	  if( catalogArray != null ) {
		  for (var i=0; i < catalogArray.length; i++) {
	    	if( catalogArray.length != 2 || i != 0 ) {
		    	setOption(inserted,catalogArray[i].name,catalogArray[i].id, "catalogId");
	    		if( selectedCatalog == catalogArray[i].id ) 
	    			selectI = inserted;
	    		inserted++;
	    	}
	  	  }
	  }
	  if( inserted == 0 )
	   	setOption(inserted,messagesData.mycatalogs,"", "catalogId");
	  $("catalogId").selectedIndex = selectI;
	  
	  var catalogCompanyId = companyFacCatalogArray[selectedCompany].facilities[selectedInv].catalogCompanyId;
	  $("catalogCompanyId").value = catalogCompanyId;
}

function showFacility(selectedCompany,selectedInv) {
	  var facilityArray = companyFacCatalogArray[selectedCompany].facilities;
	  var selectI = 0;
	  var inserted = 0 ;
	  
	  var inv = $("facilityId");
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  if( facilityArray != null ) {
		  for (var i=0; i < facilityArray.length; i++) {
	    	if( facilityArray.length != 2 || i != 0 ) {
		    	setOption(inserted,facilityArray[i].name,facilityArray[i].id, "facilityId");
	    		if( selectedInv == facilityArray[i].id ) 
	    			selectI = inserted;
	    		inserted++;
	    	}
	  	  }
	  }
	  if( inserted == 0 )
	   	setOption(inserted,messagesData.myfacilities,"", "facilityId");
	  $("facilityId").selectedIndex = selectI;
}

function showCompany(oldCompany){
  var companyArray = companyFacCatalogArray;
  var selectI = 0 ;
  var inserted = 0;

  if( companyArray.length == 1 ) 
  	setOption(0,companyArray[0].name,companyArray[0].id,"companyId");
  else {
	  for (var i=0; i < companyArray.length; i++) {
    		setOption(inserted,companyArray[i].name,companyArray[i].id, "companyId");
    		if( oldCompany == companyArray[i].id ) 
    			selectI = inserted;
    		inserted++;
	  }
  }
  $("companyId").selectedIndex = selectI;
}

function setCompany() {
	var oldCompany =  $("oldcompanyId").value;
	var oldinv =  $("oldfacilityId").value;
	var oldCat = $("oldCatalogId").value;
	try {
 		showCompany(oldCompany);
 		showFacility($("companyId").selectedIndex, oldinv);
 		showCatalog($("companyId").selectedIndex, $("facilityId").selectedIndex, oldCat);
	} catch (ex) {}
}

function companyChanged() {
	var company = $("companyId");
 	var selectedCompany = company.selectedIndex;
	showFacility(selectedCompany,null);
	
	var facility = $("facilityId");
 	var selectedFacility = facility.selectedIndex;
	showCatalog(selectedCompany, selectedFacility, null);
	displayLocaleColumns();
}

function facilityChanged() {
	var company = $("companyId");
 	var selectedCompany = company.selectedIndex;
 	
 	var facility = $("facilityId");
 	var selectedFacility = facility.selectedIndex;
	showCatalog(selectedCompany,selectedFacility,null);
	displayLocaleColumns();
}

function myUnload() {
	try{
		opener.closeTransitWin();
	}catch(ex){}
}

function closePopup(requestNumber) {
	alert(messagesData.requestcreated+requestNumber);
	window.close();
}
// -->
</script>
</head>

<c:choose>
<c:when test="${not empty generatedRequest}"><body onload="closePopup(${generatedRequest});" onunload="myUnload();"></body></c:when>
<c:otherwise>
<body bgcolor="#ffffff" onload="setCompany();initializeGrid();displayLocaleColumns();" onunload="myUnload();">
<tcmis:form action="/sdsauthoringselectionmain.do" onsubmit="return submitSearchOnlyOnce();">
<div class="interface" id="mainPage" style="">

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<div id="resultGridDiv">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
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
      <a href="#" onclick="submitRequest();"><fmt:message key="label.submit"/></a>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     	<div class="backGroundContent">
     	
      
      <table width="100%">
      <tr>
      <td class="optionTitleBoldRight" style="width:10%">
      <label for="companyId"><fmt:message key="label.company"/>:</label>
      </td>
      <td>
      <select name="companyId" id="companyId" class="selectBox" onchange="companyChanged()"></select>
      <input type="hidden" name="oldcompanyId" id="oldcompanyId" value="" />
      </td>
      </tr>
      <tr>
      <td  class="optionTitleBoldRight" style="width:10%">
      <label for="companyId"><fmt:message key="label.facility"/>:</label>
      </td>
      <td>
      <select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()"></select>
      <input type="hidden" name="oldfacilityId" id="oldfacilityId" value=""/>
      </td>
      </tr>
      <tr>
      <td  class="optionTitleBoldRight" style="width:10%">
      <label for="catalogId"><fmt:message key="label.catalog"/>:</label>
      </td>
      <td>
      <select name="catalogId" id="catalogId" class="selectBox"></select>
      <input type="hidden" name="oldCatalogId" id="oldCatalogId" value=""/>
      <input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value=""/>
      </td>
      </tr>
      </table>

		<%--NEW - there is no results table anymore--%>
		<div id="localeDiv" style="width:100%;%;height:400px;" style="display: none;"></div>
		
		<c:if test="${localeCollection != null}" >
		<script type="text/javascript">
		<!--
		
			<c:if test="${!empty localeCollection}" >
				var jsonMainData = new Array();
				var jsonMainData = {
					rows:[
							{ id:${status.index +1},
								data:[
									 '',
									 '',
									 '',
									 '',
									 ''
								]
							}
				]};
			</c:if>
		// -->
		</script>
		
		<!-- If the collection is empty say no data found -->
		<c:if test="${empty localeCollection}" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
		 <tr>
		  <td width="100%">
		   <fmt:message key="main.nodatafound"/>
		  </td>
		 </tr>
		</table>
		</c:if>
		</c:if>
		
		<!-- Hidden element start -->
		<div id="hiddenElements" style="display: none;">
		<input type="hidden" name="uAction" id="uAction" value=""/>	
		<input name="totalLines" id="totalLines" value="" type="hidden">
		<input name="materialId" id="materialId" value="<tcmis:jsReplace value="${param.materialId}"/>"/>
		<input name="revisionDate" id="revisionDate" value="<tcmis:jsReplace value="${param.revisionDate}"/>"/>
		<input name="minHeight" id="minHeight" type="hidden" value="100">
		 </div>
		<!-- Hidden elements end -->
		
		</div> <!-- close of backGroundContent -->
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
</tcmis:form>
</body>
</c:otherwise>
</c:choose>
</html:html>