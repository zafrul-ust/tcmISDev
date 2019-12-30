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
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
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
<script type="text/javascript" src="/js/client/catalog/catalogaddtrackingmain.js"></script>
<script type="text/javascript" src="/js/client/catalog/shownewchemappdetail.js"></script>

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
    <fmt:message key="catalogAddTracking.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
<bean:size id="facilitySize" name="myWorkareaFacilityViewBeanCollection"/>
var altFacilityId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
	<c:choose>
		<c:when test="${status.index == 0 && facilitySize > 1}">
			'My Facilities','<tcmis:jsReplace value="${status.current.facilityId}"/>'
	 	</c:when>
	 <c:otherwise>
		<c:choose>
		  <c:when test="${status.index == 0}">
			 '<tcmis:jsReplace value="${status.current.facilityId}"/>'
		  </c:when>
		  <c:otherwise>
			 ,'<tcmis:jsReplace value="${status.current.facilityId}"/>'
		  </c:otherwise>
		</c:choose>
	 </c:otherwise>
	</c:choose>
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
	<c:choose>
		<c:when test="${status.index == 0 && facilitySize > 1}">
			'<fmt:message key="label.myfacilities"/>','<tcmis:jsReplace value="${status.current.facilityName}"/>'
	 	</c:when>
	 <c:otherwise>
		<c:choose>
		  <c:when test="${status.index == 0}">
			 '<tcmis:jsReplace value="${status.current.facilityName}"/>'
		  </c:when>
		  <c:otherwise>
			 ,'<tcmis:jsReplace value="${status.current.facilityName}"/>'
		  </c:otherwise>
		</c:choose>
	 </c:otherwise>
	</c:choose>
</c:forEach>
);

var altApplication = new Array();
var altApplicationDesc = new Array();
 <c:if test="${facilitySize > 1}">
	altApplication["My Facilities"] = new Array("My Work Areas");
	altApplicationDesc["My Facilities"] = new Array("<fmt:message key="label.myworkareas"/>");
 </c:if>
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
   <c:set var="appListCollection" value='${status.current.applicationBeanCollection}'/>
	<bean:size id="applicationSize" name="appListCollection"/>
	altApplication['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
	 <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
	 	<c:choose>
          <c:when test="${status1.index == 0 && applicationSize > 1}">
            'My Work Areas','<tcmis:jsReplace value="${status1.current.application}" processMultiLines="true"/>'
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                '<tcmis:jsReplace value="${status1.current.application}" processMultiLines="true"/>'
              </c:when>
              <c:otherwise>
                ,'<tcmis:jsReplace value="${status1.current.application}" processMultiLines="true"/>'
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
	 </c:forEach>
	);

	altApplicationDesc['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
	 <c:forEach items="${status.current.applicationBeanCollection}" varStatus="status1">
	 	<c:choose>
          <c:when test="${status1.index == 0 && applicationSize > 1}">
            'My Work Areas','<tcmis:jsReplace value="${status1.current.applicationDesc}" processMultiLines="true"/>'
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                '<tcmis:jsReplace value="${status1.current.applicationDesc}" processMultiLines="true"/>'
              </c:when>
              <c:otherwise>
                ,'<tcmis:jsReplace value="${status1.current.applicationDesc}" processMultiLines="true"/>'
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
	 </c:forEach>
	);
</c:forEach>

var altUserFacilityApprovalRole = new Array();
<c:forEach var="facilityApprovalRoleBean" items="${userFacilityApprovalRoleColl}" varStatus="status">
   <c:set var="approvalRoleCollection" value='${status.current.approvalRoleColl}'/>
	<bean:size id="approvalRoleSize" name="approvalRoleCollection"/>
	altUserFacilityApprovalRole['<tcmis:jsReplace value="${status.current.facilityId}"/>'] = new Array(
	 <c:forEach items="${status.current.approvalRoleColl}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0}">
            '<tcmis:jsReplace value="${status1.current.approvalRole}" processMultiLines="true"/>'
          </c:when>
          <c:otherwise>
            ,'<tcmis:jsReplace value="${status1.current.approvalRole}" processMultiLines="true"/>'
          </c:otherwise>
        </c:choose>
	 </c:forEach>
	);
</c:forEach>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
allRoles:"<fmt:message key="label.allroles"/>",    
searchInput:"<fmt:message key="error.searchInput.integer"/>"};

j$().ready(function() {
	j$("#requestorName").autocomplete("getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); }},
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
	
	j$('#requestorName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateRequestor();
	}));
	
	function log(event, data, formatted) {
		j$('#requestor').val(formatted.split(":")[0]);
		$("requestorName").className = "inputBox"; // Do not use $("requestorName").className here. $ has been overrided by jquery
	} 
	
	j$("#requestorName").result(log).next().click(function() {
		j$(this).prev().search();
	});
	
	
	j$("#approverName").autocomplete("getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#activeOnly2').is(':checked'); }},
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
	
	j$('#approverName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateApprover();
	}));
	
	function approverLog(event, data, formatted) {
		j$('#approver').val(formatted.split(":")[0]);
		$("approverName").className = "inputBox"; // Do not use $("requestorName").className here. $ has been overrided by jquery
	} 
	
	j$("#approverName").result(approverLog).next().click(function() {
		j$(this).prev().search();
	});
	
	
	
}); 
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','catalogAddTracking');loadFacility();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/catalogaddtrackingresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="900" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	 	<tr>
			 <td width="80%">
			 	<table  border="0">
					<tr>
       				<td class="optionTitleBoldRight" nowrap> <fmt:message key="label.requestor"/>:</td>
      				<td class="optionTitleLeft" nowrap>
      	 				<input type="text" id="requestorName" value="<c:out value='${requestorName}'/>" class="inputBox" size="20" /> 
				 		<input name="requestor" id="requestor" type="hidden" value="">
				 		<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y">
				        <fmt:message key="label.activeOnly"/>
          <%--
          				<input class="inputBox" type="text" name="requestorName" id="requestorName" value="" size="20" onChange="invalidateRequestor()">
      	 				<input name="requestor" id="requestor" type="hidden" value=""/>
      					<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" id="selectedRequestor" name="selectedRequestor" value="..." align="right" onClick="searchRequestor()"/>
							<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearRequestor()"><fmt:message key="label.clear"/> </button>
						   --%>
      				</td>
	  					<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.facility"/>:
						</td>
						<td  class="optionTitleBoldLeft" colspan="3">
							<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">
							</select>
						</td>
				 	</tr>
				 	<tr>
						<td class="optionTitleBoldRight" nowrap> <fmt:message key="label.approver"/>:</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" id="approverName" value="<c:out value='${approverName}'/>" class="inputBox" size="20" /> 
				 			<input name="approver" id="approver" type="hidden" value="">
				 			<input name="activeOnly2" id="activeOnly2" onclick="" type="checkbox" checked class="radioBtns" value="Y">
				        	<fmt:message key="label.activeOnly"/>
					<%--
							<input class="inputBox" type="text" name="approverName" id="approverName" value="" size="20"  onChange="invalidateApprover()">
							<input name="approver" id="approver" type="hidden" value=""/>
							<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedApprover" id="selectedApprover" value="..." align="right" onClick="searchApprover()"/>
							<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearApprover()"><fmt:message key="label.clear"/> </button>
						   --%>
						</td>
						<td class="optionTitleBoldRight" nowrap >
							<fmt:message key="label.workarea"/>:
						</td>
						<td class="optionTitleBoldLeft" colspan="3">
							<select name="applicationId" id="applicationId" class="selectBox">
							</select>
						</td>
		      	</tr>
    				<tr>
                        <td colspan="2" class="optionTitleBoldCenter">
							<input type="checkbox" class="radioBtns" name="requestNeedingMyApproval" id="requestNeedingMyApproval" value="needingMyApproval" onclick="needingMyApprovalClicked()" ><fmt:message key="label.showallrequestsneedingmyapproval"/>
						</td>
                        <%--
                        <td class="optionTitleBoldRight" nowrap> <fmt:message key="label.requestid"/>:</td>
						<td class="optionTitleLeft" nowrap>
							<input class="inputBox" type="text" name="requestId" id="requestId" value="" size="20">
						</td>
                        --%>
                        <td class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
					 	<td  width="2%"  class="optionTitleBoldLeft">
						  <select name="searchField"  class="selectBox" id="searchField" >
								<option value="cat_part_no"> <fmt:message key="label.part"/>  </option>
								<option value="material_desc" selected> <fmt:message key="label.partdesc"/>  </option>
								<option value="item_id"> <fmt:message key="label.itemid"/>  </option>
							    <option value="request_id"> <fmt:message key="label.requestid"/>  </option>
                                <option value="customer_msds_mixture_number"> <fmt:message key="label.msds"/>  </option>
                          </select>
					 	</td>
					 	<td width="2%" align="left" >
						  <select name="searchMode" class="selectBox" id="searchMode" >
							  <option value="like"> <fmt:message key="label.contains"/>  </option>
							  <option value="is"> <fmt:message key="label.is"/>  </option>
							  <option value="startsWith"><fmt:message key="label.startswith"/> </option>
							  <option value="endsWith"><fmt:message key="label.endswith" /></option>
						  </select>
					 	</td>
       				<td   width="2%" align="left" >
           				<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15">
       				</td>
    				</tr>
					<tr>
                        <td colspan="2">&nbsp;</td>
                        <td class="optionTitleBoldLeft" colspan="4">
                            <select name="dateType" class="selectBox" id="dateType" >
                                <option value="last_updated"><fmt:message key="label.lastupdated"/> </option>
                                <option value="request_date"> <fmt:message key="label.requested"/>  </option>
							    <option value="submit_date"> <fmt:message key="label.submitted"/>  </option>
                            </select>
                            <input type="hidden" name="today" id="today" value='<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>' />
                            <html:text size="10" property="beginDateJsp" styleClass="inputBox" styleId="beginDateJsp"
                                  onclick="return getCalendar(document.catalogAddTracking.beginDateJsp,null,$('today'),null,document.catalogAddTracking.endDateJsp);"
                                  onfocus="blur();"/> <a href="javascript: void(0);" ID="linkbeginDateJsp"
                                  STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
                            &nbsp;<fmt:message key="label.to"/>&nbsp;
                            <input type="hidden" name="todayEnd" id="todayEnd" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>' />
                            <html:text size="10" property="endDateJsp" styleClass="inputBox" styleId="endDateJsp"
					            onclick="return getCalendar(document.catalogAddTracking.endDateJsp,null,null,document.catalogAddTracking.beginDateJsp,$('todayEnd'));"
					            onfocus="blur();"/><a href="javascript: void(0);" ID="linkendDateJsp"
					            STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
                        </td>
					</tr>
					<tr>
                        <td colspan="2">&nbsp;</td>
                        <td class="optionTitleBoldLeft" colspan="4"><fmt:message key="label.pendingapproval"/>:
                            <select name="pendingApprovalRole" class="selectBox" id="pendingApprovalRole" onchange="pendingApprovalRoleChanged()" >
                            </select>
                        </td>
                    </tr>
					<tr>
    					<td colspan="6"  class="optionTitleBoldLeft">
          				<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 			onclick= "return submitSearchForm()"/>
          				<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 			onclick= "return generateExcel()"/>
      				</td>
    				</tr>
				</table>
			 </td>
			 <td width="20%">
                 <%--
                <fieldset>
            <legend><fmt:message key="label.status"/></legend>
                --%>
					<table  border="0">
						<tr>
							<td width="2%"  class="optionTitleBoldLeft" nowrap>
								<input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="0,14" ><fmt:message key="label.draftnewmaterial"/>
							</td>
							<td  width="2%"  class="optionTitleBoldLeft" nowrap>
								<input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="1" ><fmt:message key="label.draftnewsizepkg"/>
							</td>
						</tr>
						<tr>
							<td  class="optionTitleBoldLeft" nowrap>
								<input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="2" ><fmt:message key="label.draftnewpartno"/>
							</td>
							<td  class="optionTitleBoldLeft" nowrap>
								<input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="3,4,15" ><fmt:message key="label.draftnewapproval"/>
							</td>
						</tr>
						<tr>
							<td  class="optionTitleBoldLeft">
								<input type="checkbox" class="radioBtns" checked name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="6"><fmt:message key="label.pendingqc"/>
							</td>
							<td  class="optionTitleBoldLeft">
								<input type="checkbox" class="radioBtns" checked name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="5"><fmt:message key="label.pendingapproval"/>
							</td>
						</tr>
						<tr>
							<td  class="optionTitleBoldLeft" nowrap>
								<input type="checkbox" class="radioBtns" checked name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="8"><fmt:message key="label.pendingpricing"/>
							</td>
							<td  class="optionTitleBoldLeft">
								<input type="checkbox" class="radioBtns" checked name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="11"><fmt:message key="label.pendingcatalog"/>
							</td>
						</tr>
                        <tr>
							<td  class="optionTitleBoldLeft" nowrap>
								<input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="17"><fmt:message key="label.pendingresubmit"/>
							</td>
							<td  class="optionTitleBoldLeft">
								<input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="16"><fmt:message key="label.resubmitted"/>
							</td>
						</tr>
                        <tr>
       					<td  class="optionTitleBoldLeft" nowrap>
								 <input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="9,12"><fmt:message key="label.readytoorder"/>
							</td>
	   					<td  class="optionTitleBoldLeft">
								<input type="checkbox" class="radioBtns" name="requestStatusChkbxArray" id="requestStatusChkbxArray" value="7,18"><fmt:message key="label.rejected"/>
							</td>
    					</tr>
					</table>
          <%-- </fieldset> --%>
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
<input type="hidden" name="action" id="action" value=""/>
<input type="hidden" name="hubName" id="hubName" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input type="hidden" name="isCompanyUsesCustomerMSDS" id="isCompanyUsesCustomerMSDS" value="${isCompanyUsesCustomerMSDS}"/>    
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
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
    <div class="boxhead"> 
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>     
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>	 
      <div id="updateResultLink" style="display: none">
         <a href="#" onclick="call('updateSalesOrder'); return false;"><fmt:message key="label.update"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>