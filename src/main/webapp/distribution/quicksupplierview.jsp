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

<html:html lang="true" >
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="Hidden"/>

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
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/quicksupplierview.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  
    
<title>
<fmt:message key="quickSupplierView"/>
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
    days:"<fmt:message key="label.days"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>",
    errors:"<fmt:message key="label.errors"/>",
    pleasewait:"<fmt:message key="label.pleasewait"/>",
    selectinventorygroup:"<fmt:message key="label.selectinventorygroup"/>",
    suppliercontact:"<fmt:message key="label.suppliercontact"/>",
    waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
    pleaseinput:"<fmt:message key="label.pleaseinput"/>",
    operatingentity:"<fmt:message key="label.operatingentity"/>",
    supplier:"<fmt:message key="label.supplier"/>",
    searchInput:"<fmt:message key="error.searchInput.integer"/>"
};

defaults = {
	   ops: {id:'',name:'<fmt:message key="label.pleaseselect"/>'},
   	   hub: {id:'',name:'<fmt:message key="label.all"/>'},
   	   inv: {id:'',name:'<fmt:message key="label.all"/>'}
}

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#supplier').val(formatted.split(":")[0]);
		$("supplierName").className = "inputBox"; 
	} 

	j$("#supplierName").autocomplete("findsupplier.do",{
		extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); } },
		width: 550,
		max: 100,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 200,
		formatItem: function(data, i, n, value) {
			return  value.split(":")[0]+"  "+value.split(":")[1].substring(0,45)+" - "+value.split(":")[3];
		},
		formatResult: function(data, value) {
			return value.split(":")[1];
		}
	});
	
	j$('#supplierName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);
	
		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateSupplier();
	}));
	
	
	j$("#supplierName").result(log).next().click(function() {
		j$(this).prev().search();
	});
}); 

function invalidateSupplier()
{
 var supplierName  =  document.getElementById("supplierName");
 var supplier  =  document.getElementById("supplier");
 if (supplierName.value.length == 0) {
   supplierName.className = "inputBox";
 }else {
   supplierName.className = "inputBox red";
 }
 //set to empty
 supplier.value = "";
}
// -->
 </script>
</head>

<body bgcolor="#ffffff"  onload="try{setOps();}catch(ex){submitSearch();}myResize();" onresize="myResize();">
<tcmis:form action="/quicksupplierviewresults.do" onsubmit="return submitSearchOnlyOnce();" >
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
	  <td nowrap  width="8%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.operatingentity"/>:</td>
	  <td  width="10%" class="optionTitleLeft" nowrap>
	       <select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
	  </td>
      <td nowrap width="6%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td  width="10%" class="optionTitleLeft" nowrap>
         	<select name="hub" id="hub" class="selectBox"></select>
      </td>
      <td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
      <td width="14%" class="optionTitleLeft" nowrap>
         	<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
      </td>	
      <td  class="optionTitleBoldLeft" nowrap>
      </td>
    </tr> 
	<tr>
	<%-- 
	      <td width="1%" class="optionTitleBoldLeft" colspan="2" nowrap><fmt:message key="label.item"/>:
	         <input name="itemId" id="itemId" type="text" class="inputBox" onclick="return false;" size="6" value="">&nbsp;
	         <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectItem" value="..." align="right" onClick="lookupItem();"/>
	      </td>
	 --%>
	      <td class="optionTitleBoldLeft" colspan="4" nowrap> <fmt:message key="label.supplier"/>:&nbsp;
	      	<input type="text" id="supplierName" name="supplierName" value="" class="inputBox" size="50" /> 
 		 	<input name="supplier" id="supplier" type="hidden" value="">
 		 	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedsupplier" value="..." align="right" onClick="lookupSupplier()"/>
 		 	<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y">
	          <fmt:message key="label.activeOnly"/>
	 	  </td>
	 	  <td class="optionTitleBoldLeft" colspan="2" nowrap>
	 		<fmt:message key="label.within"/>
	 		<input name="days" id="days" type="text" class="inputBox" size="5" value="100">
	 		<fmt:message key="label.days"/>
	 		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      	<input name="submitSearchBtn" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearchBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
	          		 onclick= "submitSearch();"/>
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
    <input name="uAction" id="uAction" type="text" value="">
    <input name="searchHeight" id="searchHeight" type="text" value="">
	<input name="today" id="today" type="text" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>">
    <input name="searchKey" id="searchKey" type="text" value="INVENTORY GROUP">
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

    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>
</tcmis:form>
</body>
</html:html>