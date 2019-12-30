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
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- For Calendar support 
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/newcarrier.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>



<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="newcarrier.title"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--



//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
carriername:"<fmt:message key="label.carriername"/>",
errors:"<fmt:message key="label.errors"/>"};


var showErrorMessage = false;
useLayout=false;
var windowCloseOnEsc = true;
var fromCustomerDetail = false;

var closeNewCarrierWin = false;
<c:if test="${closeNewCarrierWin eq 'Y'}" >
closeNewCarrierWin = true;
</c:if>


<c:choose>
<c:when test="${fromCustomerDetail eq 'Yes'}">
fromCustomerDetail = true;
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="newCarrierWinClose();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/newcarrier.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <!-- Search Option Table Start -->
<table width="300" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
 
	<tr>
		<td nowrap class="optionTitleBoldRight">
			<fmt:message key="label.carriername"/>:
		</td> 
      <td   width="6%" class="optionTitleBoldLeft" >
         <input name="carrierName" id="carrierName" type="text" class="inputBox" size="20" maxlength="30"/>
       </td>      
    </tr>
    
   <tr>
		<td nowrap class="optionTitleBoldRight">
			<fmt:message key="label.transportationmode"/>:
		</td>                                      
       <td   width="6%" class="optionTitleBoldLeft" >
            <select name="transportationMode" class="selectBox" id="transportationMode" >
  				  <option value="Parcel"> <fmt:message key="label.parcel"/>  </option>
                  <option value="LTL"> <fmt:message key="label.ltl"/>  </option>
                  <option value="TL"><fmt:message key="label.tl"/> </option>
                  <option value="MC"><fmt:message key="label.mc" /></option>
           </select>
       </td>  
    </tr>    
   <tr>
		<td nowrap class="optionTitleBoldRight">
			<fmt:message key="label.carriermethod"/>:
		</td>                                      
       <td   width="6%" class="optionTitleBoldLeft" >
            <select name="carrierMethod" class="selectBox" id="carrierMethod" >
  				  <option value="GROUND"> <fmt:message key="label.ground"/>  </option>
                  <option value="AIR"> <fmt:message key="label.air"/>  </option>
                  <option value="SEA"><fmt:message key="label.sea"/> </option>
           </select>
       </td>  
    </tr>
  
	<tr>
		<td colspan="4" class="optionTitleBoldLeft">
		 <input name="buttonOk" id="buttonOk" type="button" 
		 							class="inputBtns" 
		 							onmouseover="this.className='inputBtns inputBtnsOver'" 
		 							onmouseout="this.className='inputBtns'" value="<fmt:message key="label.ok"/>" 
				   					onclick="return addNewCarrier();"/>
				   
		 <input name="buttonCancel" id="buttonCancel" type="button"
                                    class="inputBtns" value="<fmt:message key="label.cancel"/>"
                                    onmouseover="this.className='inputBtns inputBtnsOver'"
                                    onmouseout="this.className='inputBtns'"
                                    onclick="javascript:window.close();"/>		
		</td>		
	</tr>
</table>
    <!-- Search Option Table end -->
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
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden"value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="83"/>
<input name="hCarrier" id="hCarrier" type="hidden" value="${param.carrierName}"/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
<input name="fromCustomerDetail" id="fromCustomerDetail" type="hidden" value="${param.fromCustomerDetail}"/>

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
 	<br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
    <div id="mainUpdateLinks"  > <%-- mainUpdateLinks Begins --%>    
     <span id="updateResultLink" style="display: none">      
     </span>      
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
    <div class="dataContent">
     
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
</div>

</body>
</html:html>