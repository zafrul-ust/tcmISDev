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

<% pageContext.setAttribute("newLineChar","\n"); %>
<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class. -->

<tcmis:gridFontSizeCss />
<tcmis:fontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<%-- This handels the menu style and what happens to the right click on the whole page --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<%--  This is for the YUI, uncomment if you will use this 
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<%--  Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/ordertracking/ordertrackingmain.js"></script>
<script type="text/javascript" src="/js/client/catalog/shownewchemappdetail.js"></script>
<script type="text/javascript" src="/js/common/ordertracking/showmrapprovaldetail.js"></script>
<script type="text/javascript" src="/js/rightclick.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- Include this so I can submit grid thru form -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  
    
<title>
<fmt:message key="ordertracking.label.title"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
<c:set var="homeCompanyLogin" value="${personnelBean.companyId == 'Radian'}"/>
homeCompanyLogin = ${homeCompanyLogin};

<c:set var="workareaFacilityColl" value='${personnelBean.myWorkareaFacilityOvBeanCollection}'/>
<bean:size id="companySize" name="workareaFacilityColl"/>
var altCompanyId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "My Companies","<tcmis:jsReplace value="${status.current.companyId}" />"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<tcmis:jsReplace value="${status.current.companyId}" />"
       </c:when>
       <c:otherwise>
         ,"<tcmis:jsReplace value="${status.current.companyId}"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altCompanyName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "<fmt:message key="label.mycompanies"/>","<tcmis:jsReplace value="${status.current.companyName}"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<tcmis:jsReplace value="${status.current.companyName}"/>"
       </c:when>
       <c:otherwise>
         ,"<tcmis:jsReplace value="${status.current.companyName}"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);


var altFacilityId = new Array();
var altFacilityName = new Array();
  <c:if test="${companySize > 1}">
    altFacilityId["My Companies"] = new Array("My Facilities");
    altFacilityName["My Companies"] = new Array("<fmt:message key="label.myfacilities"/>");
  </c:if>

  <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    <bean:size id="facilitySize" name="facilityListCollection"/>

    altFacilityId["<tcmis:jsReplace value="${currentCompanyId}" />"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && facilitySize > 1}">
            "My Facilities","<tcmis:jsReplace value="${status1.current.refColumn}"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<tcmis:jsReplace value="${status1.current.refColumn}" />"
              </c:when>
              <c:otherwise>
                ,"<tcmis:jsReplace value="${status1.current.refColumn}" />"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );

    altFacilityName["<tcmis:jsReplace value="${currentCompanyId}" />"] = new Array(
    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && facilitySize > 1}">
            "<fmt:message key="label.myfacilities"/>","<tcmis:jsReplace value="${status1.current.facilityGroupWithFacility}" />"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<tcmis:jsReplace value="${status1.current.facilityGroupWithFacility}" />"
              </c:when>
              <c:otherwise>
                ,"<tcmis:jsReplace value="${status1.current.facilityGroupWithFacility}" />"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );
  </c:forEach>

var facilityId = new Array();
var altApplication = new Array();
var altApplicationDesc = new Array();
  <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="facilityListCollection" value='${status.current.facilityList}'/>

    <bean:size id="facilitySize" name="facilityListCollection"/>
    <c:if test="${facilitySize > 1}">
      altApplication["My Facilities"] = new Array("My Work Areas");
      altApplicationDesc["My Facilities"] = new Array("<fmt:message key="label.myworkareas"/>");
    </c:if>

    <c:forEach var="facilityObjBean" items="${facilityListCollection}" varStatus="status1">
      <c:set var="currentFacilityId" value='${status1.current.refColumn}'/>
      <c:set var="applicationListCollection" value='${status1.current.applicationList}'/>

      <bean:size id="applicationSize" name="applicationListCollection"/>
      
      facilityId["<tcmis:jsReplace value="${currentFacilityId}" />"] = '${status1.current.facilityId}';

      altApplication["<tcmis:jsReplace value="${currentFacilityId}" />"] = new Array(
      <c:forEach var="applicationObjBean" items="${applicationListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && applicationSize > 1}">
            "My Work Areas","<tcmis:jsReplace value="${status2.current.application}" />"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
                "<tcmis:jsReplace value="${status2.current.application}" />"
              </c:when>
              <c:otherwise>
                ,"<tcmis:jsReplace value="${status2.current.application}" />"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );

      altApplicationDesc["<tcmis:jsReplace value="${currentFacilityId}" />"] = new Array(
      <c:forEach var="applicationObjBean" items="${applicationListCollection}" varStatus="status2">
        <c:choose>
          <c:when test="${status2.index == 0 && applicationSize > 1}">
          		"<fmt:message key="label.myworkareas"/>","<tcmis:jsReplace value="${fn:replace(status2.current.applicationDesc,newLineChar, '')}" />"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status2.index == 0}">
              	"<tcmis:jsReplace value="${fn:replace(status2.current.applicationDesc,newLineChar, '')}" />"
              </c:when>
              <c:otherwise>
              	,"<tcmis:jsReplace value="${fn:replace(status2.current.applicationDesc,newLineChar, '')}" />"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      );
    </c:forEach>
  </c:forEach>

var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
		
// Ordertrackingmain.js showerrormessages is no good.
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 450, 300);
  errorWin.setText(messagesData.errors);
  errorWin.clearIcon();  // hide icon
  errorWin.denyResize(); // deny resizing
  errorWin.denyPark();   // deny parking
  errorWin.attachObject("errorMessagesArea");
  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
  errorWin.center();
  }
  else
  {
    // just show
    dhxWins.window("errorWin").show();
  }
}


function getApplication() {
	if (typeof(facilityId[$v("facilityId")]) == 'undefined' || facilityId[$v("facilityId")].length == 0) {
		var applicationIdO = document.getElementById("applicationId");

		for (var i = applicationIdO.length; i >= 0;i--) {
		    applicationIdO[i] = null;
		}
		
		setOption(0, messagesData.myworkareas,"My Work Areas", "applicationId");
		return false;
	}
	
	var callArgs = new Object();
	callArgs.companyId = $v("companyId");
	callArgs.facilityId = facilityId[$v("facilityId")];
	callArgs.action = 'getApplicationColl';
	
	j$.post("/tcmIS/haas/ordertrackingmain.do", j$.param(callArgs), buildApplicationDropdown);  
}


function buildApplicationDropdown(data,status){
	var applicationIdO = document.getElementById("applicationId");

	for (var i = applicationIdO.length; i >= 0;i--) {
	    applicationIdO[i] = null;
	}

	var results = jQuery.parseJSON(data);
	var applicationIdArray = new Array();
	var applicationDescArray = new Array();

	for (var row in results.applicationColl){
		applicationIdArray[applicationIdArray.length] = results.applicationColl[row].application;
		applicationDescArray[applicationDescArray.length] = results.applicationColl[row].applicationDesc;
	}
	 
	if (typeof(applicationIdArray) == 'undefined')
			 setOption(0, messagesData.myworkareas,"My Work Areas", "applicationId");
	else
	{
			if(applicationIdArray.length == 0 && selectedFacility == 'All') {
	            applicationIdArray.unshift("All");
				applicationDescArray.unshift(messagesData.allWorkAreas);
	        } else if(applicationIdArray.length == 0 || applicationIdArray.length > 1){
	        	applicationIdArray.unshift("My Work Areas");
			    applicationDescArray.unshift(messagesData.myworkareas);
	        }

			for (var i=0; i < applicationIdArray.length; i++) {
			    setApplication(i,htmlDencode(applicationDescArray[i]),applicationIdArray[i])
			}
	}
	 
	applicationIdO.selectedIndex = 0;
}
		
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
errors:"<fmt:message key="label.errors"/>",
anySearchError:"<fmt:message key="label.anySearchError"/>",
withinDayError:"<fmt:message key="label.withinDayError"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
myworkareas:"<fmt:message key="label.myworkareas"/>",
allWorkAreas:"<fmt:message key="label.allWorkAreas"/>",        
searchInput:"<fmt:message key="error.searchInput.integer"/>",
allFacilities:"<fmt:message key="label.allfacilities"/>",
myFacilities:"<fmt:message key="label.myfacilities"/>",  
selectcompany:"<fmt:message key="label.firstchooseacompany"/>",  
all:"<fmt:message key="label.all"/>"
};

useLayout = false;

var allowAllFacilities = false;
<tcmis:featureReleased feature="OTAllowAllFacilities" scope="ALL">
    allowAllFacilities = true;    
</tcmis:featureReleased>


j$().ready(function() {
	function log(event, data, formatted) {
		j$('#requestorId').val(formatted.split(":")[0]);
		$("requestorName").className = "inputBox"; 
	} 
	
	j$("#requestorName").autocomplete("getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
						  companyId: function() { return j$("#companyId").val(); } },
			width: 200,
			max: 100,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 200,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[1].substring(0,40);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#requestorName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateRequestor();
	}));
	
	
	j$("#requestorName").result(log).next().click(function() {
		j$(this).prev().search();
	});
	
}); 

var searchMyArr = new Array(
		{value:'LIKE', text: '<fmt:message key="label.contains"/>'}
		,{value:'IS', text: '<fmt:message key="label.is"/>'}
	);

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onload="loadLayoutWin('','orderTracking'); showFacility();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/ordertracking.gif" align="right">
</td>
</tr>
</table>
<%@ include file="/common/clientnavigation.jsp" %>
</div>

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/ordertrackingresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="950" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

    <%-- row 1 --%>
    <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.requestor"/>:</td>
      <td width="20%" nowrap>
      	<c:choose>
	      	<c:when test="${homeCompanyLogin}">
	      		<input type="text" id="requestorName" value="" class="inputBox" size="20" /> 
		 		<input name="requestorId" id="requestorId" type="hidden" value="" />
	      	</c:when>
	      	<c:otherwise>
		 		 <input type="text" id="requestorName" value="<c:out value='${requestorName}'/>" class="inputBox" size="20" /> 
		 		 <input name="requestorId" id="requestorId" type="hidden" value="${requestorId}" />

		 	</c:otherwise>
		</c:choose>
				<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y" />
          		<fmt:message key="label.activeOnly"/>
<%--    
         <input class="inputBox" type="text" name="requestorName" id="requestorName" value="<c:out value='${requestorName}'/>" onChange="invalidateRequestor()" size="20">
         <input name="requestorId" id="requestorId" type="hidden" value="<c:out value="${requestorId}"/>">
         <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="lookupButton" id="lookupButton" value="" onclick="getPersonnel()">
 --%>
      </td>

      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
      <td width="30%">
				 <c:set var="workareaFacilityColl" value='${personnelBean.myWorkareaFacilityOvBeanCollection}'/>
				 <bean:size id="companySize" name="workareaFacilityColl"/>
				 <c:set var="selectedCompanyId" value='${param.companyId}'/>
         <select name="companyId" id="companyId" onchange="companyChanged()" class="selectBox">
	          <c:if test="${companySize > 1}">
	            <option value="My Companies" selected><fmt:message key="label.mycompanies"/></option>
	            <c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
	          </c:if>
	
	          <c:forEach var="myWorkareaFacilityViewBean" items="${personnelBean.myWorkareaFacilityOvBeanCollection}" varStatus="status">
	            <c:set var="currentCompanyId" value='${status.current.companyId}'/>
	              <c:if test="${empty selectedCompanyId}" >
	                <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
	                <c:set var="facilityCollection" value='${status.current.facilityList}'/>
	              </c:if>
	              <option value="<tcmis:jsReplace value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}" /></option>
	          </c:forEach>
         </select>
      </td>

      <td width="2%" class="optionTitleBoldLeft">
        <input name="status" id="statusAny" type="radio" class="radioBtns" value="ANY"/>
      </td>
      <td width="5%" class="optionTitleBoldLeft" nowrap>
        <fmt:message key="label.any"/>
      </td>
      <td width="1%" class="optionTitleBoldRight">
        <input name="status" id="statusDraft" type="radio" class="radioBtns" value="DRAFT"/>
      </td>
      <td width="5%" class="optionTitleBoldLeft" nowrap>
        <fmt:message key="label.draft"/>
      </td>
      <td width="1%" class="optionTitleBoldRight">
        <input name="status" id="statusPending" type="radio" class="radioBtns" value="PENDING"/>
      </td>
      <td width="5%" class="optionTitleBoldLeft" nowrap>
        <fmt:message key="label.pending"/>
      </td>
      <td width="1%" class="optionTitleBoldRight">             
        <input name="status" id="statusOpen" type="radio" class="radioBtns" value="OPEN" checked/>
      </td>
      <td width="5%" class="optionTitleBoldLeft">          
        <fmt:message key="label.open"/>
      </td>
    </tr>

    <%-- row 2 --%>
    <tr>
       <td width="5%" class="optionTitleBoldRight">
       		<c:choose>
       			<c:when test="${homeCompanyLogin}">
       				<input name="needMyApproval" id="needMyApproval" onclick="checkDisabled()" type="hidden" class="radioBtns" value="N" />	
       			</c:when>
       			<c:otherwise>
         			<input name="needMyApproval" id="needMyApproval" onclick="checkDisabled()" type="checkbox" class="radioBtns" value="Y" />
				</c:otherwise>
			</c:choose>
       </td>
       <td width="20%" class="optionTitleBoldLeft">
       		<c:if test="${!homeCompanyLogin}">
          		<fmt:message key="label.needmyapproval"/>
          	</c:if>
       </td>

      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
      <td width="30%">
      		<c:choose>
       			<c:when test="${homeCompanyLogin}">
       				<select name="facilityId" id="facilityId" onchange="getApplication()" class="selectBox"/>
       			</c:when>
       			<c:otherwise>
         			<select name="facilityId" id="facilityId" onchange="facilityChanged()" class="selectBox"/>
				</c:otherwise>
			</c:choose>
      </td>


       <td width="25%" colspan="8" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;
         <input name="status" id="statusReleased" type="radio" class="radioBtns" value="RELEASED"/>
         <fmt:message key="label.released"/> <fmt:message key="label.within"/>
         <input class="inputBox" type="text" name="releasedSinceDays" id="releasedSinceDays" value="<c:out value='${releasedSinceDays}'/>" size="5"/>
         <fmt:message key="label.days"/>
       </td>
    </tr>

    <%-- row 3 --%>
    <tr>
             <td width="5%" class="optionTitleBoldRight">
         <input name="critical" id="critical" type="checkbox" class="radioBtns" value="Y"/>
       </td>
       <td width="20%" class="optionTitleBoldLeft">
          <fmt:message key="label.criticalonly"/>
       </td>
       
       <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.workarea"/>:</td>
       <td width="30%">
          <select name="applicationId" id="applicationId" class="selectBox">
          </select>
       </td>

       <td width="25%" colspan="8" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;
         <input name="status" id="statusDelivered" type="radio" class="radioBtns" value="DELIVERED"/>
         <fmt:message key="label.fullydelivered"/> <fmt:message key="label.within"/>
         <input class="inputBox" type="text" name="deliveredSinceDays" id="deliveredSinceDays" value="<c:out value='${deliveredSinceDays}'/>" size="5"/>
         <fmt:message key="label.days"/>
       </td>
    </tr>

    <%-- row 4 --%>
    <tr>
      <td width="5%" colspan="2" class="optionTitleBoldLeft">
        <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" 
        	onclick='return submitSearchForm();'/>
        <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;"/>
      </td>
      <td width="10%" class="optionTitleBoldRight" nowrap>
         <fmt:message key="label.searchby"/>:&nbsp;
       </td>
       <td width="30%" class="optionTitleBoldLeft" nowrap>
 			 <html:select property="searchWhat" styleId="searchWhat"  onchange="changeSearchTypeOptions(this.value)" styleClass="selectBox">
		 		<html:option value="CAT_PART_NO" key="label.partNno"/>
   				<html:option value="PO_NUMBER" key="label.userpo"/>
   				<html:option value="PO_NUMBER_LINE" key="label.podaskline"/>
				<html:option value="PART_DESC" key="label.partdesc"/>
				<html:option value="PR_NUMBER" key="label.materialrequestno"/>
				<tcmis:featureReleasedForMyFacilities feature="OTAllowSearchByItem" companyId="${personnelBean.companyId}">
				    <html:option value="ITEM_ID" key="label.itemid"/>
				</tcmis:featureReleasedForMyFacilities>
				<tcmis:featureReleasedForMyFacilities feature="ReorderMR" companyId="${personnelBean.companyId}">
					<html:option value="ORDER_NAME" key="label.ordername"/>
				</tcmis:featureReleasedForMyFacilities>
		   </html:select>

 			 <html:select property="searchType" styleId="searchType" styleClass="selectBox">
   				<html:option value="LIKE" key="label.contains"/>
   				<html:option value="IS" key="label.is"/>
  		 	</html:select>

        <input class="inputBox" type="text" name="searchText" id="searchText" value="<c:out value='${param.searchText}'/>" size="10"/>
       </td>


       <td width="25%" colspan="8" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;
         <input name="status" id="statusCanceled" type="radio" class="radioBtns" value="CANCELED"/>
         <fmt:message key="label.cancelled"/> <fmt:message key="label.within"/>
         <input class="inputBox" type="text" name="cancelledSinceDays" id="cancelledSinceDays" value="<c:out value='${cancelledSinceDays}'/>" size="5"/>
         <fmt:message key="label.days"/>
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

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
<input name="companyName" id="companyName" type="hidden" value="">
<input name="facilityName" id="facilityName" type="hidden" value="">
<input name="applicationDesc" id="applicationDesc" type="hidden" value="">
<input name="searchTypeDesc" id="searchTypeDesc" type="hidden" value="">
<input name="searchWhatDesc" id="searchWhatDesc" type="hidden" value="">
<input name="searchNeedMyApproval" id="searchNeedMyApproval" type="hidden" value="">
<input name="searchHeight" id="searchHeight" type="hidden" value="133">
<input name="myDefaultFacilityId" id="myDefaultFacilityId" type="hidden" value="${personnelBean.myDefaultFacilityId}">
<input name="maxData" id="maxData" type="hidden" value="4000"/>

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
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
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
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="showlegendLink">
        <a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a>        
      </span>
      <span id="updateResultLink" style="display: none">
        <%--<a href="#" onclick="call('submitData'); return false;"><fmt:message key="label.update"/></a>--%>        
        <%--| <a href="#" onclick="call('printGrid'); return false;"><fmt:message key="label.print"/></a>--%>
      </span>
      <span id="reportTimeMessageLink" style="display: none">

      </span>
      &nbsp;
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
<div id="errorMessagesAreaBody" class="bd">
 <html:errors/>
</div>
</div>

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
   <tr><td width="100px" class="pink legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.aog"/></td></tr>
    <tr><td width="100px" class="red legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalorders"/></td></tr>
    <tr><td width="100px" class="yellow legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.pendingcancellation"/></td></tr>
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="ordertracking.label.cancelled"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>
