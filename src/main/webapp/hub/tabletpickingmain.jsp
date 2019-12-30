<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

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
<script type="text/javascript" src="/js/hub/tabletpickingmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<script language="JavaScript" type="text/javascript">

<!--
		var altFacilityId = new Array();
		var altFacilityName = new Array();
		<c:forEach var="hubInventoryGroupFacOvBean" items="${hubInventoryGroupFacOvBeanCollection}" varStatus="status">
		  <c:set var="currentHub" value='${status.current.branchPlant}'/>
		  <c:set var="inventoryGroupCollection" value='${status.current.inventoryGroupCollection}'/>
		  <c:forEach var="inventoryGroupBean" items="${inventoryGroupCollection}" varStatus="status1">
		    <c:set var="currentInventoryGroup" value='${status1.current.inventoryGroup}'/>
		    <c:set var="facilityBeanCollection" value='${status1.current.facilities}'/>

		      altFacilityId["<c:out value="${currentInventoryGroup}"/>"] = new Array(""
		      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
		        ,"<c:out value="${status2.current.facilityId}"/>"
		      </c:forEach>
		      );

		      altFacilityName["<c:out value="${currentInventoryGroup}"/>"] = new Array("<fmt:message key="label.all"/>"
		      <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status2">
		        ,"<c:out value="${status2.current.facilityName}" escapeXml="false"/>"
		      </c:forEach>
		      );

		  </c:forEach>
		</c:forEach>		


		var csrArray = [
		                <c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
		                <c:if test="${ status.index !=0 }">,</c:if>
		                    {
		                      opsEntityId:   '${status.current.opsEntityId}',
		                      id:   '${status.current.csrPersonnelId}',
		                      name: '${status.current.csrName}'
		                    }
		                </c:forEach>
		               ];  

	
// -->
</script>

<title>
<fmt:message key="picklistpicking.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
mr:"<fmt:message key="label.mr"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
expireDaysInteger:"<fmt:message key="error.expiredays.integer"/>",
picklistConfirm:"<fmt:message key="picklistpicking.confirm.generate"/>",
total:"<fmt:message key="label.total"/>",
errors:"<fmt:message key="label.errors"/>"};

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#customerId').val(formatted.split(":")[0]);
		$("customerName").className = "inputBox"; 
	} 
	
	j$("#customerName").autocomplete("/tcmIS/distribution/findcustomer.do",{
			width: 350,
			max: 100,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 200,
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

useLayout = false;

// -->
</script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"    onload="loadLayoutWin('','tabletPicking');setDropDowns();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/tabletpickingresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt=""
                                                        width="15" height="10" class="corner_filter"
                                                        style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                        <!-- Insert all the search option within this div -->
      <table width="710" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
      <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="6%" class="optionTitleBoldLeft">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
     
              <td class="optionTitleBoldRight"> <fmt:message key="label.customer"/>:</td>
      <td class="optionTitleLeft" nowrap="nowrap">
      	<input type="text" name="customerName" id="customerName" value="" size="30"  class="inputBox"/>
      	<input name="customerId" id="customerId" type="hidden" value=""/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/>
      </td>
      <td class="optionTitleLeft" width="5%">&nbsp;</td>
    </tr>
      <tr>
        <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
        <td width="10%" class="optionTitleBoldLeft">
          <c:set var="selectedHub" value='${param.hub}'/>
          <select name="hub" id="hub" class="selectBox">
          </select>
       </td>
       
       		      <td width="5%" class="optionTitleBoldRight">
		      <fmt:message key="label.csr"/>:
		      </td>
		      <td>
      
    			<select name="customerServiceRepId" id="customerServiceRepId" class="selectBox">
				 <option value=""><fmt:message key="label.all"/></option>
				<c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
				 <option value="${status.current.csrPersonnelId}">${status.current.csrName}</option>
                	</c:forEach>
	  		</select>
      </td>
            
      </tr>
      <tr>
       <td width="5%" class="optionTitleBoldRight">
       <fmt:message key="label.inventorygroup"/>:
       </td>
      <td width="10%" class="optionTitleBoldLeft">
        <c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox" >            
          </select>
          </td>

		   <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
            <td colspan="3" class="optionTitleBoldLeft" nowrap>
                <select name="searchField"  class="selectBox" id="searchField" >
                    <option value="pr_number"> <fmt:message key="label.mr"/>  </option>
                                                                                                                                                                                 
                </select>
                &nbsp;
                <select name="searchMode" class="selectBox" id="searchMode" >
                       <option value="is"> <fmt:message key="label.is"/>  </option>
                </select>
             &nbsp;
                <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
            </td>     
        
		</tr>
      <tr>
        <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.facility"/>:</td>
       <td width="5%" class="optionTitleBoldLeft">
          <c:set var="selectedFacilityId" value='${param.facilityId}'/>
          <select name="facilityId" id="facilityId" class="selectBox">           
            <c:forEach var="facilityBean" items="${facilityBeanCollection}" varStatus="status">
              <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
              <c:choose>
                <c:when test="${empty selectedFacilityId}" >
                  <c:set var="selectedFacilityId" value=''/>
                </c:when>
              </c:choose>
              <c:choose>
                <c:when test="${currentFacilityId == selectedFacilityId}">
                  <option value='<c:out value="${currentFacilityId}"/>' selected><c:out value="${status.current.facilityId}"/></option>
                </c:when>
                <c:otherwise>
                  <option value='<c:out value="${currentFacilityId}"/>'><c:out value="${status.current.facilityId}"/></option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
          </td>

   	   <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.expiresafter"/>:</td>
       <td width="10%">
          <c:set var="enteredDays" value='${param.expireDays}'/>
         <c:if test='${param.expireDays == null}'>
             <c:set var="enteredDays" value='30'/>
         </c:if>
         <input name="expireDays" id="expireDays" type="text" class="inputBox" value="${enteredDays}" size="4"/>
      </td>	
     </tr>
     <tr>
     <td width="5%" class="optionTitleBoldRight">&nbsp;</td>
       <td width="10%">
       &nbsp;
       </td>
    
        		 <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
        <td width="5%" class="optionTitleBoldLeft" nowrap>
          <c:set var="selectedSortBy" value="${param.sortBy}"/>
          <c:if test="${selectedSortBy == null || selectedSortBy == ''}">
            <c:set var="selectedSortBy" value='prNumber'/>
          </c:if>
          <html:select property="sortBy" styleId="sortBy" styleClass="selectBox" value="pr_number"> 
            <html:option value="facility_id,application" key="picklistpicking.facilityidworkarea"/>
            <html:option value="customer_service_rep_name" key="label.csr"/>
            <html:option value="item_id" key="label.itemid"/>
            <html:option value="pr_number" key="label.mr"/>
            <html:option value="need_date" key="label.needdate"/>
            <html:option value="cat_part_no" key="label.partnumber"/>
            <html:option value="ship_to_location_id" key="label.shipto"/>
          </html:select>
        </td>        
     </tr>
     
     <tr> 
      <td width="5%" class="optionTitleBoldLeft" colspan="3">
	   <input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick=" return submitSearchForm();"/>
	   <input name="createExcel" id="createExcel" type="button" value="<fmt:message key="label.createexcelfile"/>" onclick="generatePickListExcel();" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
	   <input name="regeneratePicklist" id="regeneratePicklist" type="button" value="<fmt:message key="picklistpicking.regeneratepicklist"/>" onclick="doregenerate();" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
      </td>
      </tr>
    </table>
                        <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt=""
                                                           width="15" height="15" class="corner"
                                                           style="display: none"/></div>
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
    <input name="action" id="action" type="hidden" value="">
    <input name="startSearchTime" id="startSearchTime" type="hidden"value="">
    <input name="searchHeight" id="searchHeight" type="hidden" value="158">
    <input name="transportationMode" id="transportationMode" type="hidden" value="">
    <input name="oconusFlag" id="oconusFlag" type="hidden" value="">
    <input name="hazardous" id="hazardous" type="hidden" value="">
    <input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>
    <input type="hidden" name="isWmsHub"  id="isWmsHub" value="N"/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
  	<tr><td width="100px" class="pink">&nbsp;</td><td class="legendText"><fmt:message key="buyorders.legend.supercritical"/><br/><fmt:message key="label.lddown"/></td></tr>
  	<tr><td width="100px" class="red">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalpo"/></td></tr>
    <tr><td width="100px" class="yellow">&nbsp;</td><td class="legendText"><fmt:message key="label.pickingqclegend1"/></td></tr>
    <tr><td width="100px" class="error">&nbsp;</td><td class="legendText"><fmt:message key="label.pickingqclegend2"/></td></tr>
    <tr><td width="100px" class="orange">&nbsp;</td><td class="legendText"><fmt:message key="label.pickingqclegend3"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>

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
       <div id="mainUpdateLinks" > <%-- mainUpdateLinks Begins --%>
      <span id="showlegendLink"> <a href="#" onclick="showpickingpagelegend(); return false;"><fmt:message key="label.showlegend"/></a></span>
      <span id="updateResultLink" style="display:none">| 
      <a href="#" onclick="call('viewPickableUnits');"><fmt:message key="picklistpicking.generatepicklist"/></a></span>
      </div>
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
