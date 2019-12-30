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
<script type="text/javascript" src="/js/distribution/contactlookupmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>  
  <c:choose>
	<c:when test="${param.customerId == null}">
	        <fmt:message key="contactsearch.title"/> - ${param.tabName}
	</c:when>
	<c:otherwise>
	       	<fmt:message key="contactsearch.title"/> <fmt:message key="label.for"/> ${customerName}(${param.customerId})-${param.tabName}
	</c:otherwise>
  </c:choose>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",searchInputInteger:"<fmt:message key="error.searchInput.integer"/>", 
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
newcontact:"<fmt:message key="label.newcontact"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','contactLookup');fixCustomerIdIfExisted();document.genericForm.searchContactArgument.focus();" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/contactlookupresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="200" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
        <td width="5%" class="optionTitleBoldRight" id="customerIdLabel" nowrap><fmt:message key="label.customerid"/></td> 
        <td width="45%" class="optionTitleLeft" id="customerIdSearch" nowrap>
           <select name="searchCustomerIdMode" id="searchCustomerIdMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
          </select>
          <input class="inputBox" type="text" name="searchCustomerIdArgument" id="searchCustomerIdArgument" value="${param.customerId}" size="20" onkeypress="return onKeyPress()"></input>
        </td>      
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.contact"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchContactMode" id="searchContactMode" class="selectBox">
            <option value="is" selected><fmt:message key="label.is"/></option>
            <option value="contains" selected><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchContactArgument" id="searchContactArgument" value="${param.searchContactArgument}" size="20" onkeypress="return onKeyPress()"></input>
        </td>
      </tr>
      
      <%--     Row 2    --%>
      <tr>
        <td width="5%" class="optionTitleBoldRight" id="customerNameLabel" nowrap><fmt:message key="label.customername" />:</td> 
        <td width="45%" class="optionTitleLeft" id="customerNameSearch" nowrap>
           <select name="searchNameMode" id="searchNameMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="contains" selected><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchNameArgument" id="searchNameArgument" value="${customerName}" size="20" onkeypress="return onKeyPress()"></input>
        </td>
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.telephone" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchPhoneMode" id="searchPhoneMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="contains" selected><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchPhoneArgument" id="searchPhoneArgument" size="20" onkeypress="return onKeyPress()"></input></td>
        </td>
      </tr>
      
       <%--     Row 3    --%>
 	  <tr>
        <td width="5%" class="optionTitleBoldRight" id="id1" nowrap>&nbsp;</td>
        <td width="45%" class="optionTitleBoldLeft" id="id2" nowrap>&nbsp;</td>

        <%--<td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.synonym" />:</td>
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchSynonymMode" id="searchSynonymMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="contains" selected><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchSynonymArgument" id="searchSynonymArgument" value="${param.searchSynonymArgument}" size="20" onkeypress="return onKeyPress()"></input>
          </td>
        --%>  
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.email"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchEmailMode" id="searchEmailMode" class="selectBox">
            <option value="is" selected><fmt:message key="label.is"/></option>
            <option value="contains" selected><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchEmailArgument" id="searchEmailArgument" value="${param.searchEmailArgument}" size="20" onkeypress="return onKeyPress()"></input>
        </td>
        <td width="5%" class="optionTitleBoldRight" nowrap>&nbsp;</td>
        <td width="45%" class="optionTitleBoldLeft" nowrap><input type="checkbox" class="radioBtns" name="showinactive" id="showinactive" value="Y" /><fmt:message key="label.showinactive"/></td>
        
        
      </tr>
      <%--     Row 4    --%>    
    
      <tr>
        <td class="optionTitleLeft" colspan="4" nowrap>					
			<input type="button" name="submitSearch" id="submitSearch" value="<fmt:message key="label.search"/>" class="inputBtns"
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
				onclick="submitSearchForm();" />
			<input type="button" name="btnCreateExcel" id="btnCreateExcel" value="<fmt:message key="label.createexcel"/>" class="inputBtns"
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
				onclick="validateSearchFormXSL();">
		    <span id="btnNewContactSpan"><input type="button" name="btnNewContact" id="btnNewContact" value="<fmt:message key="label.newcontact"/>" class="inputBtns"
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
				onclick="newContact();"></input></span>
		</td>
		<td width="45%" class="optionTitleLeft" nowrap>&nbsp;</td>
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
<div class="spacerY" style="display:none;">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="billToCompanyId" id="billToCompanyId" value="${param.billToCompanyId}"/>
<input type="hidden" name="customerId" id="customerId" value="${param.customerId}"/>
<input type="hidden" name="customerName" id="customerName" value="${customerName}"/>
<input name="displayElementId" id="displayElementId" type="hidden" value="${param.displayElementId}">
<input name="displayArea" id="displayArea" type="hidden" value="${param.displayArea}">
<input name="valueElementId" id="valueElementId" type="hidden" value="${param.valueElementId}">   
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
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
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
     <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
     	<span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
        <span id="selectedContact">&nbsp;</span>	    
     </div>
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
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.statusisinactiveorcreditstatusstop"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>

</body>
</html:html>