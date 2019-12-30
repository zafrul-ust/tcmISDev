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
<script type="text/javascript" src="/js/distribution/quickitemsearchmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.itemsearch"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",
searchInputInteger:"<fmt:message key="error.searchInput.integer"/>", 
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",	
itemInteger:"<fmt:message key="error.item.integer"/>"};



var returnSelectedValue = null;
var returnSelectedDesc  = null;
var returnSelectedPrice  = null;



// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','quickItemSearch');document.genericForm.partNumber.focus();autoSubmit();" onresize="resizeFrames()" onunload="" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/quickitemsearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="800" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
     <%--     Row 1    --%>
      <tr>
        <td width="2%" class="optionTitleBoldRight" ><fmt:message key="label.partnumber" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap> 
          <select name="searchPartNumberMode" id="searchPartNumberMode" class="selectBox">
            <option value="is" <c:if test="${param.searchPartNumberMode eq 'is'}"> selected="selected"</c:if>><fmt:message key="label.is"/></option>
            <option value="like" <c:if test="${param.searchPartNumberMode eq 'like' || param.searchPartNumberMode == null}">selected="selected"</c:if>><fmt:message key="label.contains"/></option>
            <option value="startsWith" <c:if test="${param.searchPartNumberMode eq 'startsWith'}">selected="selected"</c:if>><fmt:message key="label.startswith"/></option>
            <option value="endsWith" <c:if test="${param.searchPartNumberMode eq 'endsWith'}">selected="selected"</c:if>><fmt:message key="label.endswith"/></option>
          </select>     
              
          <input class="inputBox" type="text" name="partNumber" id="partNumber" size="15" onkeypress="return onKeyPress()"<c:if test="${param.partNumber != null}">value=${param.partNumber}</c:if>>
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.customerpartnumber" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchCustomerPartNumberMode" id="searchCustomerPartNumberMode" class="selectBox">
            <option value="is" <c:if test="${param.searchCustomerPartNumberMode eq 'is'}">selected="selected"</c:if>><fmt:message key="label.is"/></option>
            <option value="like" <c:if test="${param.searchCustomerPartNumberMode eq 'like' || param.searchCustomerPartNumberMode == null}">selected="selected"</c:if>><fmt:message key="label.contains"/></option>
            <option value="startsWith" <c:if test="${param.searchCustomerPartNumberMode eq 'startsWith'}">selected="selected"</c:if>><fmt:message key="label.startswith"/></option>
            <option value="endsWith" <c:if test="${param.searchCustomerPartNumberMode eq 'endsWith'}">selected="selected"</c:if>><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="customerPartNumber" id="customerPartNumber" size="15" onkeypress="return onKeyPress()"<c:if test="${param.customerPartNumber != null}">value=${param.customerPartNumber}</c:if>>
        </td>
      </tr>
      
      <%--     Row 2    --%>
      <tr>
        <td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.partdescription"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchPartDescMode" id="searchPartDescMode" class="selectBox">
            <option value="is" <c:if test="${param.searchPartDescMode eq 'is'}">selected="selected"</c:if>><fmt:message key="label.is"/></option>
            <option value="like" <c:if test="${param.searchPartDescMode eq 'like' || param.searchPartDescMode == null}">selected="selected"</c:if>><fmt:message key="label.contains"/></option>
            <option value="startsWith" <c:if test="${param.searchPartDescMode eq 'startsWith'}">selected="selected"</c:if>><fmt:message key="label.startswith"/></option>
            <option value="endsWith" <c:if test="${param.searchPartDescMode eq 'endsWith'}">selected="selected"</c:if>><fmt:message key="label.endswith"/></option>
          </select>           
          <input class="inputBox" type="text" name="partDesc" id="partDesc" size="15" onkeypress="return onKeyPress()" <c:if test="${param.partDesc != null}">value=${param.partDesc}</c:if>>
        </td>
        <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.text" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchTextMode" id="searchTextMode" class="selectBox">
            <option value="is" <c:if test="${param.searchTextMode eq 'is'}">selected="selected"</c:if>><fmt:message key="label.is"/></option>
            <option value="like" <c:if test="${param.searchTextMode eq 'like' || param.searchTextMode == null}">selected="selected"</c:if>><fmt:message key="label.contains"/></option>
            <option value="startsWith" <c:if test="${param.searchTextMode eq 'startsWith'}">selected="selected"</c:if>><fmt:message key="label.startswith"/></option>
            <option value="endsWith" <c:if test="${param.searchTextMode eq 'endsWith'}">selected="selected"</c:if>><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="text" id="text" size="15" onkeypress="return onKeyPress()" <c:if test="${param.text != null}">value=${param.text}</c:if>>
        </td>
      </tr>
      
      <%--     Row 3    --%>
      <tr>
        <td width="2%" class="optionTitleBoldRight" ><fmt:message key="label.specification"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchSpecificationMode" id="searchSpecificationMode" class="selectBox">
            <option value="is" <c:if test="${param.searchSpecificationMode eq 'is'}">selected="selected"</c:if>><fmt:message key="label.is"/></option>
            <option value="like" <c:if test="${param.searchSpecificationMode eq 'like' || param.searchSpecificationMode == null}">selected="selected"</c:if>><fmt:message key="label.contains"/></option>
            <option value="startsWith" <c:if test="${param.searchSpecificationMode eq 'startsWith'}">selected="selected"</c:if>><fmt:message key="label.startswith"/></option>
            <option value="endsWith" <c:if test="${param.searchSpecificationMode eq 'endsWith'}">selected="selected"</c:if>><fmt:message key="label.endswith"/></option>
          </select>           
          <input class="inputBox" type="text" name="specification" id="specification" size="15" onkeypress="return onKeyPress()" <c:if test="${param.specification != null}">value=${param.specification}</c:if>>
        </td>
        <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.synonym" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchAlternateMode" id="searchAlternateMode" class="selectBox">
            <option value="is" <c:if test="${param.searchAlternateMode eq 'is'}">selected="selected"</c:if>><fmt:message key="label.is"/></option>
            <option value="like" <c:if test="${param.searchAlternateMode eq 'like' || param.searchAlternateMode == null}">selected="selected"</c:if>><fmt:message key="label.contains"/></option>
            <option value="startsWith" <c:if test="${param.searchAlternateMode eq 'startsWith'}">selected="selected"</c:if>><fmt:message key="label.startswith"/></option>
            <option value="endsWith" <c:if test="${param.searchAlternateMode eq 'endsWith'}">selected="selected"</c:if>><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="alternate" id="alternate" size="15" onkeypress="return onKeyPress()" <c:if test="${param.alternate != null}">value=${param.alternate}</c:if>>
        </td>
      </tr>
       
      <%--     Row 4    --%>
      <tr>       
        <td class="optionTitleLeft" colspan="6" nowrap>				
			<input type="submit" name="submitSearch" id="submitSearch" value="<fmt:message key="label.search"/>" class="inputBtns"
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
				onclick="return submitSearchForm();" />
			<input name="close" type="button" class="inputBtns" value="<fmt:message key="label.close"/>" id="close" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
          	<input name="clearallfields" id="clearallfields" type="button"
				   value="<fmt:message key="label.clearallfields"/>" onclick="clearAll();" class="inputBtns"
				   onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
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
<div id="hiddenElements" style="display:;">
<input name="uAction" id="uAction" type="hidden" value="${param.uAction}"/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
<input name="currencyId" id="currencyId" type="hidden" value="${param.currencyId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/>
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
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
     <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
        <span id="selectedPart">&nbsp;</span>
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

</body>
</html:html>