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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/client/catalog/usageimportinfomain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />    
    
<title>
<fmt:message key="usageImportInfo"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    or:"<fmt:message key="label.or"/>",
    all:"<fmt:message key="label.all"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    searchText:"<fmt:message key="label.searchtext"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    errors:"<fmt:message key="label.errors"/>",
    pleasewait:"<fmt:message key="label.pleasewait"/>",
    waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
    searchInput:"<fmt:message key="error.searchInput.integer"/>"
};

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#createdBy').val(formatted.split(":")[0]);
		$("createdByName").className = "inputBox"; 
	} 
	
	j$("#createdByName").autocomplete("getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
						  companyId: function() { return j$("#companyId").val(); } },
			width: 200,
			max: 10,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 200,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[1].substring(0,40);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#createdByName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateCreatedby();
	}));
	
	
	j$("#createdByName").result(log).next().click(function() {
		j$(this).prev().search();
	});
	
}); 

function invalidateCreatedby()
{
 var createdByName  =  document.getElementById("createdByName");
 var createdBy  =  document.getElementById("createdBy");
 if (createdByName.value.length == 0) {
   createdByName.className = "inputBox";
 }else {
   createdByName.className = "inputBox red";
 }
 //set to empty
 createdBy.value = "";
}

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#lastModifiedBy').val(formatted.split(":")[0]);
		$("lastModifiedByName").className = "inputBox"; 
	} 
	
	j$("#lastModifiedByName").autocomplete("getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
						  companyId: function() { return j$("#companyId").val(); } },
			width: 200,
			max: 10,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 200,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[1].substring(0,40);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#lastModifiedByName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateLastModifiedBy();
	}));
	
	
	j$("#lastModifiedByName").result(log).next().click(function() {
		j$(this).prev().search();
	});
	
}); 

function invalidateLastModifiedBy()
{
 var lastModifiedByName  =  document.getElementById("lastModifiedByName");
 var lastModifiedBy  =  document.getElementById("lastModifiedBy");
 if (lastModifiedByName.value.length == 0) {
   lastModifiedByName.className = "inputBox";
 }else {
   lastModifiedByName.className = "inputBox red";
 }
 //set to empty
 lastModifiedBy.value = "";
}

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('','usageImportInfo');" onresize="resizeFrames();">

<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/usageimportinforesults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="1025" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                     <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
            <td width="30%" class="optionTitleBoldRight">
				<fmt:message key="label.facility"/>:
			</td>
			
			<td width="70%" class="optionTitleBoldLeft">
			<c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
			<select name="facilityId" id="facilityId" class="selectBox">
			
			  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
			    <c:if test="${myWorkareaFacilityViewBean.active == 'y'}">
			        <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
			          <c:if test="${empty selectedFacilityId}" >
			            <c:set var="selectedFacilityId" value='${currentFacilityId}' />
			          </c:if>
			          <c:if test="${currentFacilityId == selectedFacilityId}" >
			            <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
			          </c:if>
			
			        <c:choose>
			          <c:when test="${currentFacilityId == selectedFacilityId}">
			            <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityName}"/></option>
			          </c:when>
			          <c:otherwise>
			            <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityName}"/></option>
			          </c:otherwise>
			        </c:choose>
			    </c:if>
			  </c:forEach>
			</select>
			</td>
		  <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.createdby"/>:</td>
	      <td width="20%" nowrap>
	 		 <input type="text" id="createdByName" value="" class="inputBox" size="20" /> 
	 		 <input name="createdBy" id="createdBy" type="hidden" value="">
	      </td>
	      <td width="20%" class="optionTitleBoldRight" nowrap>
	      	 <fmt:message key="label.lastUpdatedBy"/>:
	      </td>
	      <td width="20%" class="optionTitleBoldLeft" nowrap>
	 		 <input type="text" id="lastModifiedByName" value="" class="inputBox" size="20" /> 
	 		 <input name="lastModifiedBy" id="lastModifiedBy" type="hidden" value="">
	 		 <input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y">
	          <fmt:message key="label.activeOnly"/>
	      </td>
        </tr>
        <tr>
			<td width="20%" class="optionTitleBoldRight">
				<fmt:message key="label.search" />:
			</td>
			<td width="50%" class="optionTitleBoldLeft" nowrap>
				<select name="searchField" class="selectBox" id="searchField">
					<option value="partNumber" selected><fmt:message key="label.partno" /></option>
					<option value="customerMsdsOrMixtureNo"><fmt:message key="label.msds" /></option>
					<option value="tradeName"><fmt:message key="label.tradename" /></option>
					<option value="materialId"><fmt:message key="label.materialid" /></option>
				</select>
				<select name="searchMode" class="selectBox" id="searchMode">
					<option value="is"><fmt:message key="label.is" /></option>
					<option value="contains"><fmt:message key="label.contains" /></option>
					<option value="startsWith"><fmt:message key="label.startswith" /></option>
					<option value="endsWith"><fmt:message key="label.endswith" /></option>
				</select>
				<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15">
			</td>
			<td nowrap class="optionTitleBoldRight"><fmt:message key="label.created"/>:</td>
			<td nowrap class="optionTitleBoldLeft">
				<input name="toDate" id="toDate" type="hidden"
	   				value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
				<input class="inputBox pointer" readonly type="text" name="createdFromDate" id="createdFromDate" value="" onClick="return getCalendar(document.genericForm.createdFromDate,null,document.genericForm.createdToDate,null,document.genericForm.toDate);" maxlength="10" size="9"/>
				<fmt:message key="label.to"/>&nbsp;
				<input class="inputBox pointer" readonly type="text" name="createdToDate" id="createdToDate" value="" onClick="return getCalendar(document.genericForm.createdToDate,document.genericForm.createdFromDate);" maxlength="10" size="9"/>
			</td>
			<td>&nbsp;</td>
			<td nowrap class="optionTitleBoldLeft">
				<input type="checkbox" name="includeTcmisCatalogParts" id="includeTcmisCatalogParts" value="Y" class="radioBtns">
				<fmt:message key="label.includetcmiscatalogparts"/>
			</td>
	   </tr>

       <tr>  
         <td colspan="2">   
            <!-- search button -->
            &nbsp;
            <input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()">
            &nbsp;
            <input name="createExcel" id="createExcel" type="button"
				   value="<fmt:message key="label.createexcelfile"/>" onclick="createXSL();" class="inputBtns"
				   onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
          </td>
        </tr>
        
    </table>
    
    
   <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none"/></div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
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
    <input name="uAction" id="uAction" type="hidden" value="">
    <input name="startSearchTime" id="startSearchTime" type="hidden" value="">
    <input name="defaultFacilityId" id="defaultFacilityId" type="hidden" value="${personnelBean.myDefaultFacilityId}">
    <input name="searchHeight" id="searchHeight" type="hidden" value="">
    <input name="maxData" id="maxData" type="hidden" value="100"/> 
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
<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>


<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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
     <div id="mainUpdateLinks" style="display: "> <%-- mainUpdateLinks Begins --%>
     	<span id="updateResultLink" style="display: none">
     		<a href="#" onclick="resultFrame.getCustomerMsdsDbB4AddPart();"><fmt:message key="label.addpart" /></a>
     		<span id="hasDataLink" style="display: none">
     		| <a href="#" onclick="resultFrame.updateParts();"><fmt:message key="label.update" /></a>
	<%-- 		| <a href="#" onclick="resultFrame.deletePart();"><fmt:message key="label.deletepart" /></a>   --%>
			</span>
		</span>
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
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

</body>
</html:html>