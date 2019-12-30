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
<%@ include file="/common/opshubig.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/distribution/mrreleasemain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<title>
    <fmt:message key="mrrelease.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
mr:"<fmt:message key="label.mr"/>",
operatingentity:"<fmt:message key="label.operatingentity"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",	
itemInteger:"<fmt:message key="error.item.integer"/>"};

defaults.ops.nodefault = true;
//defaults.hub.nodefault = true;
//defaults.inv.nodefault = true;

 var searchMyArr = [                              
                    { value:'is', text:'<fmt:message key="label.is"/>'},
                    { value:'like', text:'<fmt:message key="label.contains"/>'},
                    { value:'startsWith', text:'<fmt:message key="label.startswith"/>'},
                    { value:'endsWith', text:'<fmt:message key="label.endswith"/>'}
                   ];  

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#customerId').val(formatted.split(":")[0]);
		$("customerName").className = "inputBox"; 
	} 
	
	j$("#customerName").autocomplete("findcustomer.do",{
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

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#personnelId').val(formatted.split(":")[0]);
		$("personnelName").className = "inputBox"; 
	} 

	j$("#personnelName").autocomplete("getpersonneldata.do",{
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
 var personnelId  =  document.getElementById("personnelId");
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

<body bgcolor="#ffffff" onload="loadLayoutWin('','mrRelease');setOps();mainOnLoad();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/mrreleaseresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <%--  <table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch"> --%>
    <table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="6%" class="optionTitleBoldLeft">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
      <td class="optionTitleBoldRight" nowrap> <fmt:message key="label.customer"/>:</td>
      <td class="optionTitleLeft" nowrap>
      	<input type="text" name="customerName" id="customerName" value="" size="30"/ class="inputBox">
      	 <input name="customerId" id="customerId" type="hidden" value=""/>  
     	 <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/> 
     		<!-- <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                                             name="None" value=""  OnClick="clearCustomer()"><fmt:message key="label.clear"/> </button>-->
      </td>
      
    </tr>
    <tr>
      <td nowrap width="6%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td  width="10%" class="optionTitleBoldLeft">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>	
       <td rowspan="3" width="2%" class="optionTitleBoldLeft" ><fmt:message key="label.status"/>:</td>
       <td rowspan="3" width="2%" class="optionTitleBoldLeft" >
        <select name="orderStatus"  class="selectBox" id="orderStatus" multiple size="5" >
        	<option value="All" selected><fmt:message key="label.all"/></option>
        	<c:forEach var="releaseStatusBean" items="${vvReleaseStatusCollection}" varStatus="status">
        	  <c:if test="${ releaseStatusBean.releaseStatus != 'Released' }">
				<c:choose>
					<c:when test="${ releaseStatusBean.releaseStatus == 'Hold' }">
						<option value="${releaseStatusBean.releaseStatus}"><fmt:message key="label.credithold"/></option>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${ releaseStatusBean.releaseStatus == 'Stop' }">
							 	<option value="${releaseStatusBean.releaseStatus}"><fmt:message key="label.creditstop"/></option>
							</c:when>
							<c:otherwise>
								<c:set var="releaseStatusDesc">label.${fn:replace(fn:toLowerCase(releaseStatusBean.releaseStatus)," ","")}</c:set>
								<option value="${releaseStatusBean.releaseStatus}"><fmt:message key="${releaseStatusDesc}"/></option>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>

			  </c:if>
			</c:forEach>
        </select>
       </td>         
    </tr>
    <tr>
      <td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
      <td width="14%" class="optionTitleBoldLeft">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>	             
    </tr>
    <tr>
    <td nowrap class="optionTitleBoldRight" nowrap><fmt:message key="label.confirmdate"/>:</td>
	 <td nowrap class="optionTitleBoldLeft">
		 <fmt:message key="label.from"/>
		 <input class="inputBox pointer" readonly type="text" name="confirmFromDate" id="confirmFromDate" value="" onClick="return getCalendar(document.mrrelease.confirmFromDate,null,document.mrrelease.confirmToDate);"
		        maxlength="10" size="9"/>
		 <fmt:message key="label.to"/>&nbsp;
		 <input class="inputBox pointer" readonly type="text" name="confirmToDate" id="confirmToDate" value="" onClick="return getCalendar(document.mrrelease.confirmToDate,document.mrrelease.confirmFromDate);"
		        maxlength="10" size="9"/>
	 </td>	  
    </tr>
    <tr>    
      <td class="optionTitleBoldRight"> <fmt:message key="label.enteredby"/>:</td>   
    
	 <td class="optionTitleLeft" nowrap>
        <input  type="text" name="personnelName" id="personnelName" value="" size="20" class="inputBox"/>
      	<input name="personnelId" id="personnelId" type="hidden" value=""/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupPerson()"/>
        <input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y">
	          <fmt:message key="label.activeOnly"/>
        	<!-- <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearRequestor()"><fmt:message key="label.clear"/> </button> -->
      </td>
	  <td  class="optionTitleBoldRight" nowrap><fmt:message key="label.search"/>:</td>
                                <td nowrap class="optionTitleBoldLeft">
                                    <select name="searchField"  class="selectBox" id="searchField" onchange="changeSearchMode(this.value)">
                                        <option value="prNumber"><fmt:message key="label.mr"/> </option>
                                        <option value="poNumber"><fmt:message key="label.customerreference" /></option>                                                                                                                                                                  
                                    </select>
                                    &nbsp;
                                     <select name="searchMode" class="selectBox" id="searchMode" >
                                           <option value="is"> <fmt:message key="label.is"/>  </option>
                                           <option value="startsWith"><fmt:message key="label.startswith"/> </option>
                                    </select>
                                 &nbsp;                                                            
                                
                                    <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
                            </td>          
    </tr>
    <tr>
       <td colspan="2"  class="optionTitleBoldLeft" nowrap>
<!-- Larry Test 
          <input name="testData" type="checkbox" value="Y"/>
-->          
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()"/>
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
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
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.opsHubIgOvBeanCollection[0].companyId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

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

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
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
	  <div id="mainUpdateLinks" style="display"> <%-- mainUpdateLinks Begins --%>
	  <Span id="updateResultLink" style="display: none;"><a href="#" onclick="resultFrame.releaseMR();"><fmt:message key="label.releaseorders"/></a></span>
	   <span id="selectedRow">&nbsp;</span>      
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