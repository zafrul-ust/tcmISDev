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
<%@ include file="/common/opshubig.jsp" %>
<!-- For Calendar support 
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/newcarrieraccount.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>



<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="newcarrieraccount.title"/>
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
account:"<fmt:message key="label.carrieraccount"/>",
customerdetails:"<fmt:message key="customerdetails.title"/>",
errors:"<fmt:message key="label.errors"/>"};

var showErrorMessage = false;
useLayout=false;
var windowCloseOnEsc = true;
var closeCustomerDetailWin = false;


var closeNewAcctWin = false;
var fromCustomerDetail = false;


<c:choose>
<c:when test="${fromCustomerDetail eq 'Yes'}">
<%-- Un comment when needed and remove only <c:set var="onLoadsFunctions" value='newAccountWinClose();'/> below this commented code
<c:set var="onLoadsFunctions" value='setDropDowns();newAccountWinClose();'/>
--%>
<c:set var="onLoadsFunctions" value='newAccountWinClose();'/>
<c:set var="showOpsAndInvGrp" value='Y'/>
fromCustomerDetail = true;
</c:when>
<c:otherwise>
<c:set var="onLoadsFunctions" value='newAccountWinClose();'/>
</c:otherwise>
</c:choose>






<c:if test="${closeNewAcctWin eq 'Y'}" >
closeNewAcctWin = true;
</c:if>

<c:if test="${closeCustomerDetailWin eq 'Y'}" >
closeCustomerDetailWin = true;
</c:if>
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="${onLoadsFunctions};closeAllchildren();" onunload="closeAllchildren();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/newcarrieraccount.do" onsubmit="return submitSearchOnlyOnce(); " target="resultFrame">
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
<table width="500" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<%-- Un comment when needed 


  <c:if test="${showOpsAndInvGrp eq 'Y'}" >	
	
	
     <tr>
		<td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
     <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">        
         </select>
      </td>
    </tr>  
      <tr>		
	   <td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>
    </tr> 
  </c:if>
  
  --%>
	<tr>
		<td nowrap class="optionTitleBoldRight">
			<fmt:message key="label.carriername"/>:
		</td> 
       <td width="6%" class="optionTitleBoldLeft">
           <select name="carrierName" class="selectBox" id="carrierName">           
           <c:set var="selectedCarrier" value='${param.selectedCarrier}'/>
           <c:forEach var="carrierNamesCol" items="${carrierNameCollection}" varStatus="status">
           <c:set var="currentCarrier" value='${status.current.carrierName}'/>
            <c:choose>
              <c:when test="${currentCarrier == selectedCarrier}">                           
                 <option value="<c:out value="${currentCarrier}"/>" selected>${currentCarrier}</option>
              </c:when>
              <c:otherwise>
                <option value="<c:out value="${currentCarrier}"/>">${currentCarrier}</option>
              </c:otherwise>
             </c:choose>                           
            </c:forEach>                                
           </select>
       </td>
       	<td colspan="2" class="optionTitleBoldLeft">
 			<input name="buttonNew" id="buttonNew" type="button"
                                    class="inputBtns" value="<fmt:message key="label.new"/>"
                                    onmouseover="this.className='inputBtns inputBtnsOver'"
                                    onmouseout="this.className='inputBtns'"
                                    onclick="openNewCarrierWin();"/>			
		</td>
    </tr>
    
    	<tr>
		<td nowrap class="optionTitleBoldRight">
			<fmt:message key="label.carrieraccount"/>:
		</td>                                      
       <td   width="6%" class="optionTitleBoldLeft" >
           <input name="account" id="account" type="text" class="inputBox" size="20" maxlength="30"/>
       </td>  
    </tr>
    <tr>
     <td class="optionTitleBoldRight"><fmt:message key="label.notes"/>:</td>
        <td class="optionTitleBoldLeft">
          <textarea rows="5" cols="40" name="notes" id="notes" class="inputBox"></textarea>
       </td>
    </tr>
  
	<tr>
		<td colspan="4" class="optionTitleBoldLeft">
		 <input name="buttonOk" id="buttonOk" type="button" 
		 							class="inputBtns" 
		 							onmouseover="this.className='inputBtns inputBtnsOver'" 
		 							onmouseout="this.className='inputBtns'" value="<fmt:message key="label.ok"/>" 
				   					onclick="return addNewCarrierAccount();"/>
				   
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
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="All"/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
<input name="haccount" id="haccount" type="hidden" value="${param.account}"/>
<input name="hCarrierName" id="hCarrierName" type="hidden" value="${param.carrierName}"/>
<input name="selectedCarrier" id="selectedCarrier" type="hidden" value="${param.selectedCarrier}"/>
<input name="fromCustomerDetail" id="fromCustomerDetail" type="hidden" value="${param.fromCustomerDetail}"/>
<input name="callbackparam" id="callbackparam" type="hidden" value="${param.callbackparam}"/>
<input name="carrierAccountId" id="carrierAccountId" type="hidden" value="${carrierAccountId}"/>
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
      &nbsp;
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

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>


</body>
</html:html>