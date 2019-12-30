<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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

<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>


<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/allocationanalysis.js"></script>

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<script language="JavaScript" type="text/javascript">
<!--

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;


var altFacilityId = new Array();
var altFacilityName = new Array();
<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>
  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

      altFacilityId["${currentInventoryGroup}"] = new Array(""
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"${status2.current.facilityId}"
      </c:forEach>
      );

      altFacilityName["${currentInventoryGroup}"] = new Array("<fmt:message key="label.all"/>"
      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
      </c:forEach>
      );

  </c:forEach>
</c:forEach>

// -->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="allocationanalysis.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",daySpanInteger:"<fmt:message key="error.dayspan.integer"/>",
itemMrInteger:"<fmt:message key="error.itemmr.integer"/>",
all:"<fmt:message key="label.all"/>"
};

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#customerId').val(formatted.split(":")[0]);
		$("customerName").className = "inputBox"; 
	} 
	
	j$("#customerName").autocomplete("/tcmIS/distribution/findcustomer.do",{
			width: 350,
			max: 100,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 150,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[0]+" "+value.split(":")[1].substring(0,32);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#customerName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateCustomer();
	}));
	
	j$("#customerName").result(log).next().click(function() {
		j$(this).prev().search();
	});

}); 

function invalidateCustomer()
{
 var customerName  =  document.getElementById("customerName");
 var customerId  =  document.getElementById("customerId");
 if (customerName.value.length == 0) {
   customerName.className = "inputBox";
 }else {
   customerName.className = "inputBox red";
 }
 //set to empty
 customerId.value = "";
}

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#csrPersonnelId').val(formatted.split(":")[0]);
		$("personnelName").className = "inputBox"; 
	} 

	j$("#personnelName").autocomplete("/tcmIS/haas/getpersonneldata.do",{
		extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
					  companyId: function() { return j$("#companyId").val(); } },
		width: 200,
		max: 10,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 150,
		formatItem: function(data, i, n, value) {
			return  value.split(":")[1].substring(0,40);
		},
		formatResult: function(data, value) {
			return value.split(":")[1];
		}
	});
	
	j$('#personnelName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);
	
		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidatePersonnel();
	}));
	
	
	j$("#personnelName").result(log).next().click(function() {
		j$(this).prev().search();
	});
}); 

function invalidatePersonnel()
{
 var personnelName  =  document.getElementById("personnelName");
 var personnelId  =  document.getElementById("csrPersonnelId");
 if (personnelName.value.length == 0) {
   personnelName.className = "inputBox";
 }else {
   personnelName.className = "inputBox red";
 }
 //set to empty
 personnelId.value = "";
}
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();setOps();inventoryGroupChanged();">

<tcmis:form action="/allocationanalysisresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

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
    <table width="800" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
	    <td width="6%" class="optionTitleBoldLeft">
	         <select name="opsEntityId" id="opsEntityId" class="selectBox">
	         </select>
	    </td>
	    <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.customer"/>:</td>
        <td width="30%" nowrap>
        	<input type="text" name="customerName" id="customerName" value="" size="30" class="inputBox" />
      	 	<input name="customerId" id="customerId" type="hidden" value=""/>  
      		<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/>
     		<!-- <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                                             name="None" value=""  OnClick="clearCustomer();return false;"><fmt:message key="label.clear"/> </button> -->
        </td>
        <td class="optionTitleBoldRight" nowrap> <fmt:message key="label.csr"/>:
        <input  type="text" name="personnelName" id="personnelName" value="" size="30" class="inputBox" />
      	<input name="csrPersonnelId" id="csrPersonnelId" type="hidden" value=""/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedPerson" value="..." align="right" onClick="lookupPerson()"/>
        <!-- <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearPerson();return false;"><fmt:message key="label.clear"/> </button> -->
      </tr>
      <tr>
        <td nowrap width="10%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td  width="15%" class="optionTitleBoldLeft">
           <select name="hub" id="hub" class="selectBox">
           </select>
        </td>	
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.lotstatus"/>:</td>
        <td width="15%">
<c:set var="selectedLotStatus" value="${param.lotStatus}"/>
<c:if test="${selectedLotStatus == null || selectedLotStatus == ''}">
  <c:set var="selectedLotStatus" value=''/>
</c:if>
			<select id="lotStatus" name="lotStatus" class="selectBox">
				<option value=""><fmt:message key="label.all"/></option>
				<c:forEach var="bean" items="${vvLotStatusBeanCollection}" varStatus="status">
				 <c:set var="jspLabel" value=""/>
   				 <c:if test="${fn:length(status.current.jspLabel) > 0}"><c:set var="jspLabel">${status.current.jspLabel}</c:set></c:if>
						   	<c:choose>
								<c:when test="${selectedLotStatus == bean.lotStatus}">
									<option value="${bean.lotStatus}" selected><fmt:message key="${jspLabel}"/></option>
								</c:when>
								<c:otherwise>
									<option value="${bean.lotStatus}"><fmt:message key="${jspLabel}"/></option>
								</c:otherwise>
							</c:choose>
				</c:forEach>
			</select>
        </td>
        <td class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y">
	          <fmt:message key="label.activeOnly"/>
        </td>
      </tr>
      <tr>
        <td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
      	<td width="14%" class="optionTitleBoldLeft">
       		<select name="inventoryGroup" id="inventoryGroup" onchange="inventoryGroupChanged();" class="selectBox">
       		</select>
      	</td>
      	<td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.progressstatus"/>:</td>
        <td width="15%">
<c:set var="selectedProgressStatus" value="${param.progressStatus}"/>
<c:if test="${selectedProgressStatus == null || selectedProgressStatus == ''}">
  <c:set var="selectedProgressStatus" value=''/>
</c:if>
<html:select property="progressStatus" styleId="progressStatus" styleClass="selectBox" value="${selectedProgressStatus}">
<html:option value="" key="label.all"/>
<html:option value="Available" key="label.available"/>
<html:option value="In Supply" key="label.insupply"/>
<html:option value="Not Allocated" key="label.notallocated"/>
<html:option value="On Hand, Not Available" key="allocationanalysis.label.onhand"/>
</html:select>
        </td>	         
        <td width="10%" class="optionTitleBoldLeft" nowrap>
<c:set var="selectedItemOrMrCriteria" value="${param.itemOrMrCriteria}"/>
<c:if test="${selectedItemOrMrCriteria == null || selectedItemOrMrCriteria == ''}">
  <c:set var="selectedItemOrMrCriteria" value='itemid'/>
</c:if>
<html:select property="itemOrMrCriteria" styleId="itemOrMrCriteria" styleClass="selectBox" value="${selectedItemOrMrCriteria}">
<html:option value="itemid" key="label.itemid"/>
<html:option value="mr" key="label.mr"/>
</html:select>
          <fmt:message key="label.is"/>
          <input name="itemOrMr" id="itemOrMr" type="text" class="inputBox" value="${param.itemOrMr}" size="8">
        </td>
      </tr>
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
        <td width="15%">
          <select name="facilityId" id="facilityId" class="selectBox">
          </select></td>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td width="15%">
<c:set var="selectedSortBy" value="${param.sortBy}"/>
<c:if test="${selectedSortBy == null || selectedSortBy == ''}">
  <c:set var="selectedSortBy" value='REQUIRED_DATETIME'/>
</c:if>
<html:select property="sortBy" styleId="sortBy" styleClass="selectBox" value="${selectedSortBy}">
<html:option value="FACILITY_ID" key="label.facilityid"/>
<html:option value="ITEM_ID,REQUIRED_DATETIME" key="allocationanalysis.label.itemneeded"/>
<html:option value="PR_NUMBER,LINE_ITEM" key="label.mrline"/>
<html:option value="PR_NUMBER,LINE_ITEM,REQUIRED_DATETIME" key="allocationanalysis.label.mrlineneeded"/>
<html:option value="REQUIRED_DATETIME" key="label.needed"/>
<html:option value="SYSTEM_REQUIRED_DATETIME" key="allocationanalysis.label.ontimedate"/>
<html:option value="REF_NO" key="label.referenceno"/>
<html:option value="ITEM_TYPE,ITEM_ID,REQUIRED_DATETIME" key="allocationanalysis.label.typeitemneeded"/>
<html:option value="CUSTOMER_NAME" key="label.customer"/>
</html:select>
        </td>
        <td width="10%" class="optionTitleBoldLeft" nowrap>
<c:set var="selectedDaySpanCriteria" value="${param.daySpanCriteria}"/>
<c:if test="${selectedDaySpanCriteria == null || selectedDaySpanCriteria == ''}">
  <c:set var="selectedDaySpanCriteria" value='needed'/>
</c:if>
<html:select property="daySpanCriteria" styleId="daySpanCriteria" styleClass="selectBox" value="${selectedDaySpanCriteria}">
<html:option value="needed" key="label.needed"/>
<html:option value="ontime" key="label.ontime"/>
</html:select>
<c:set var="selectedDaySpan" value="${param.daySpan}"/>
<c:if test="${selectedDaySpan == null || selectedDaySpan == ''}">
  <c:set var="selectedDaySpan" value='7'/>
</c:if>
          <fmt:message key="label.within"/>
          <input name="daySpan" id="daySpan" type="text" class="inputBox" value="${selectedDaySpan}" size="3">
          <fmt:message key="label.days"/>
        </td>
      </tr>

      <tr>
        <td width="10%" class="optionTitleBoldRight">&nbsp;</td>
        <td width="15%" class="optionTitleBoldLeft" colspan="2"> 
        	<input name="searchType" type="radio" class="radioBtns" value="all" id="all" checked> <fmt:message key="label.all"/>
        	<input name="searchType" type="radio" class="radioBtns" value="scheduled" id="scheduled" > <fmt:message key="label.scheduledonly"/>
        	<input name="searchType" type="radio" class="radioBtns" value="transferRequest" id="transferRequestOnly" > <fmt:message key="label.transferrequestonly"/>
        </td>
            </tr>
      <tr>
      <td width="10%" class="optionTitleBoldRight"><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return search();"></td>
     <td width="15%">
       <html:button property="buttonCreateExcel" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
        <fmt:message key="label.createexcelfile"/>
       </html:button>
     </td>
     <td colspan="4">&nbsp; </td>
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="createExcel">
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.opsHubIgOvBeanCollection[0].companyId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>