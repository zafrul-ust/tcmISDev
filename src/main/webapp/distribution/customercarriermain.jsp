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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/customercarriermain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="customercarrier.title"/> <fmt:message key="label.for"/> ${param.customerName}(${param.customerId})-${param.tabName}
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var returnSelectedValue=null;
var returnSelectedId=null;
var valueElementId="${valueElementId}";
var displayElementId="${displayElementId}";
var displayAccount="${displayAccount}";

var windowCloseOnEsc = true;

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
searchInput:"<fmt:message key="label.inputSearchText"/>",
errors:"<fmt:message key="label.errors"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>"};



// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="loadLayoutWin('','customerCarrier');mainOnload();document.genericForm.searchArgument.focus();" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/customercarrierresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
		<td  class="optionTitleBoldRight" nowrap>
			<fmt:message key="label.search"/>:
		</td>
		    <td    class="optionTitleBoldLeft">
           <select name="searchField"  class="selectBox" id="searchField" >
              <option value="carrierName"><fmt:message key="label.carriername"/></option>
               <option value="carrierCode"> <fmt:message key="label.carriercode"/>  </option>                                     
           </select>
       </td> 
       <td  class="optionTitleBoldLeft" >
           <select name="searchMode" class="selectBox" id="searchMode" >
                  <option value="like"> <fmt:message key="label.contains"/>  </option>
                  <option value="is"> <fmt:message key="label.is"/>  </option>
                  <option value="startsWith"><fmt:message key="label.startswith"/> </option>
                  <option value="endsWith"><fmt:message key="label.endswith" /></option>
           </select>
       </td>                                      
       <td  width="4%" class="optionTitleBoldLeft">
           <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="20"/>
       </td>     
      
    </tr>
    <tr>
    <td colspan="4" class="optionTitleBoldLeft">
		  <input type="checkbox" name="showCustomerCarriersOnly" value="Yes" id="showCustomerCarriersOnly" onclick="changeCustomerCarrierFlagValue(this);"/>
	    <span class="optionTitleBoldLeft"><fmt:message key="label.showcustomercarrier"/></span>
		</td>	
    </tr>
  
	<tr>
		<td colspan="3" class="optionTitleBoldLeft" nowrap>
		 <input name="submitSearch" id="submitSearch" type="submit" 
		 							class="inputBtns" 
		 							onmouseover="this.className='inputBtns inputBtnsOver'" 
		 							onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" 
				   					onclick="return submitSearchForm();"/>
				   
		 <input name="buttonNew" id="buttonNew" type="button"
                                    class="inputBtns" value="<fmt:message key="label.new"/>"
                                    onmouseover="this.className='inputBtns inputBtnsOver'"
                                    onmouseout="this.className='inputBtns'"
                                    onclick="openNewCarrierAccountWin();"/>		
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
<input name="action" id="action" type="hidden" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden"value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="83"/>
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>


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
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
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
    <div id="mainUpdateLinks" > <%-- mainUpdateLinks Begins --%>    
	<span id="selectedRow">&nbsp;</span>     
    </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
    <div class="dataContent"> 
        <c:choose>
		<c:when test="${!empty param.inventoryGroup and !empty param.customerId}">
			<script language="JavaScript" type="text/javascript">
			<!--	 
			showPleaseWait();
			var now = new Date();
	    	var startSearchTime = document.getElementById("startSearchTime");
			startSearchTime.value = now.getTime();			
			// -->
			</script>
	    <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/tcmIS/distribution/customercarrierresults.do?action=search&inventoryGroup=${param.inventoryGroup}&customerId=${param.customerId}&showCustomerCarriersOnly=${param.showCustomerCarriersOnly}"></iframe>	       
	  </c:when>
	  <c:otherwise>
	  <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>		
      </c:otherwise>
	</c:choose> 
    
     
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