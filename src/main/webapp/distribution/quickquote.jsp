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

<html:html lang="true" >
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<c:if test="${ 'Y' != param.readonly }"> 
<%@ include file="/common/opshubig.jsp" %>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
</c:if>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="Hidden"/>

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
<script type="text/javascript" src="/js/distribution/quickquote.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  
  
<title>
<fmt:message key="quickQuote"/>
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
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>",
    errors:"<fmt:message key="label.errors"/>",
    pleasewait:"<fmt:message key="label.pleasewait"/>",
    pleaseinput:"<fmt:message key="label.pleaseinput"/>",
    operatingentity:"<fmt:message key="label.operatingentity"/>",
    inputitem:"<fmt:message key="label.inputitem"/>",
    waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
    searchInput:"<fmt:message key="error.searchInput.integer"/>"
};

defaults = {
	   ops: {id:'',name:'<fmt:message key="label.pleaseselect"/>'},
   	   hub: {id:'',name:'<fmt:message key="label.myhubs"/>'},
   	   inv: {id:'',name:'<fmt:message key="label.myinventorygroups"/>'}
}

defaults.ops.callback = setCurrencyId;

var opsCurrencyArray = [
    <c:forEach var="entityCurrencyBean" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          opsEntityId:   '${status.current.opsEntityId}',
          homeCurrencyId:   '${status.current.homeCurrencyId}'
        }
    </c:forEach>
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
// -->
 </script>
</head>

<body bgcolor="#ffffff"  onload="if('Y' == '${param.fromJDE}'){setOps();setOpsHubIGValue('${param.opsEntityId}', '${param.hub}', '${param.inventoryGroup}');getSuggestedPrice('Y');submitSearch();myResize();} else if('Y' != '${param.readonly}')setOps();else {submitSearch();}myResize();" onresize="myResize();">
<tcmis:form action="/quickquoteresults.do" onsubmit="return submitSearchOnlyOnce();" >
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
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
    <table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <c:if test="${ 'Y' != param.readonly }"> 
	      <td nowrap  width="8%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.operatingentity"/>:</td>
	      <td  width="10%" class="optionTitleLeft" nowrap>
	         	<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
	         	<input name="currencyId" id="currencyId" type="hidden" value=""/>  
	      </td>
      </c:if>
      <c:if test="${ 'Y' == param.readonly }"> 
         	<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}"/>
         	<input name="homeCurrencyId" id="homeCurrencyId" type="hidden" value="${param.homeCurrencyId}"/>    
      </c:if>
      <c:if test="${ 'Y' != param.readonly }"> 
      <td nowrap width="6%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td  width="10%" class="optionTitleLeft" nowrap>
         	<select name="hub" id="hub" class="selectBox"></select>
      </td>
      </c:if>	
      <td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
      <td width="14%" class="optionTitleLeft" nowrap>
      	 <c:if test="${ 'Y' != param.readonly }"> 
         	<select name="inventoryGroup" id="inventoryGroup" class="selectBox" onChange="clearItemSearch()" ></select>
         </c:if>
         <c:if test="${ 'Y' == param.readonly }">
         	${param.inventoryGroupName}
         	<input name="inventoryGroup" id="inventoryGroup" type="hidden" value="${param.inventoryGroup}"/> 
         	<input name="hub" id="hub" type="hidden" value="${param.hub}"/>   
         </c:if> 
      </td>	
      <td  class="optionTitleBoldLeft" nowrap>
      </td>
    </tr> 
    <c:if test="${ 'Y' != param.readonly }"> 
	    <tr>
	      <td width="1%" class="optionTitleBoldLeft" colspan="2" nowrap><fmt:message key="label.item"/>:
	      	 <c:choose>
		      	 <c:when test="${ 'Y' == param.fromJDE }"> 
		      	 	 <input name="itemId" id="itemId" readonly class="inputBox" value="${param.itemId}">&nbsp;
		      	 </c:when>
		      	 <c:otherwise>		      	 
	         		<input name="itemId" id="itemId" type="text" class="inputBox" onkeypress="return onKeySearch(event);" size="6" value="">&nbsp;
	         		<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectItem" value="..." align="right" onClick="lookupItem();"/>
	         	</c:otherwise>
		     </c:choose>
	      </td>
	      <td class="optionTitleBoldLeft" colspan="5" nowrap> <fmt:message key="label.customer"/>:
	      	<input type="text" name="customerName" id="customerName" value="" size="30" class="inputBox"/>
	      	<input name="customerId" id="customerId" type="hidden" value=""/>  
	      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/>
	     	<!-- <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
	                                             name="None" value=""  OnClick="return clearCustomer();"><fmt:message key="label.clear"/> </button> -->
	        <c:choose>
	         <c:when test="${ 'Y' == param.fromJDE }"> 
		      <input name="hideInterCompany" id="hideInterCompany" type="checkbox" class="radioBtns" value="Y" checked> <fmt:message key="label.hideintercompany"/>
       	     </c:when>
       	     <c:otherwise>	
       	      <input name="hideInterCompany" id="hideInterCompany" type="checkbox" class="radioBtns" value="Y" > <fmt:message key="label.hideintercompany"/>
       	     </c:otherwise>	
		   </c:choose>
	      </td>
	    </tr> 
    </c:if> 
    <c:if test="${ 'Y' == param.readonly }"> 
	   <input name="itemId" id="itemId" type="hidden" class="inputBox" onkeypress="onKeySearch(event);" value="${param.itemId}">
	   <input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>  
    </c:if> 
    <tr>
      <td class="optionTitleLeft" colspan="6"><B><fmt:message key="label.itemdesc"/>:</B>
      	<span id="itemDescSpan">${param.itemDesc}</span>
      </td>
     </tr> 
    <c:if test="${ 'Y' != param.readonly }">  
     <tr>
      <td class="optionTitleLeft" colspan="2" nowrap><B><fmt:message key="label.suggestedsellprice"/>:</B>
      	<span id="priceSpan"></span>
      </td>
      <td class="optionTitleLeft" colspan="5">
      	<input name="submitSearchBtn" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearchBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "getSuggestedPrice('Y');submitSearch();"/>
      </td>
    </tr> 
    </c:if>             
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
    <input name="uAction" id="uAction" type="text" value="" />
    <input name="searchHeight" id="searchHeight" type="text" value="" />
    <input name="itemDesc" id="itemDesc" type="text" value="" />
    <input name="searchKey" id="searchKey" type="text" value="INVENTORY GROUP" />
    <input name="catalogCompanyId" id="catalogCompanyId" type="text" value="HAAS" />
    <input name="catalogId" id="catalogId" type="text" value="Global" />
    <input name="searchPartNumberMode" id="searchPartNumberMode" type="text" value="" />
    <input name="partNumber" id="partNumber" type="text" value="" />
    <input name="searchCustomerPartNumberMode" id="searchCustomerPartNumberMode" type="text" value="" />
    <input name="customerPartNumber" id="customerPartNumber" type="text" value="" />
    <input name="searchPartDescMode" id="searchPartDescMode" type="text" value="" />
    <input name="partDesc" id="partDesc" type="text" value="" />
    <input name="searchTextMode" id="searchTextMode" type="text" value="" />
    <input name="text" id="text" type="text" value="" />
    <input name="searchSpecificationMode" id="searchSpecificationMode" type="text" value="" />
    <input name="specification" id="specification" type="text" value="" />
    <input name="searchAlternateMode" id="searchAlternateMode" type="text" value="" />
    <input name="alternate" id="alternate" type="text" value="" />
    
    <input name="openedPr" id="openedPr" value="" type="hidden"/>
 	<input name="openedQuoteType" id="openedQuoteType" value="" type="hidden"/>
 	<input name="fromJDE" id="fromJDE" value="${param.fromJDE}" type="hidden" />
 	<c:if test="${ 'Y' == param.fromJDE }"> 
 		<input name="userId" id="userId" value="${param.userId}" type="hidden" />
 	</c:if>
</div>
<!-- Hidden elements end -->
</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 0px 0px 0px 0px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle"><br>
  <div style="height:1000px;"></div>
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display:;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="1" cellspacing="0">
<tr> 
    <td height="100%" valign="top">
    	<table border="0" width="100%"><tr><td>
	    	<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="100%" style="border-top:#E8F0FF 1px solid;border-right:#E8F0FF 1px solid;border-left:#E8F0FF 1px solid;border-bottom:#E8F0FF 1px solid;" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
	    	</td></tr>
    	</table>
	</td>
  </tr>
</table>
</div>  
</div><!-- Result Frame Ends -->

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

	 <%-- freeze --%>
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


    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>
</tcmis:form>
</body>
</html:html>