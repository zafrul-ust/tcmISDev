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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<c:set var="module">
	 <tcmis:module/>
</c:set>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/distribution/newshiptoaddress.js"></script>

<script language="JavaScript" type="text/javascript">
<!--

// -->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
--%>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
--%>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
--%>
    
<title>
<fmt:message key="title.newpricegroup"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
missingCurrency:"<fmt:message key="label.missingcurrency"/>"
};

function done() {
	<c:if test="${empty tcmISError}">
	if( "${done}" == 'Y' ) {
		try {
			opener.priceListAdded($v('priceGroupId'),$v('priceGroupName'))
		}catch(ex){}
		window.close();
	}
	</c:if>
}

window.onresize = resizeFrames;
windowCloseOnEsc = true;

function setup() {
	defaults.ops.callback = changePrice;

	   var eleName = 'opsEntityId';
	   var obj = $(eleName);
	   for (var i = obj.length; i-- > 0;)
	     obj[i] = null;
//	   var pr = new Array();
	   
//	   for (var i=0; i<priceData.length; i++)
//		   pr[ priceData[i].opsId ] = priceData[i].opsId; 

	   var j =0;
	   for(i=0;i< opshubig.length;i++)
//		   if( pr[ opshubig[i].id ] )
			    {
				setOption(i,opshubig[i].name,opshubig[i].id, eleName);
		   } 
	   $('opsEntityId').value = '${param.opsEntityId}'
//	changePrice();
}

function changePrice() {
	   var eleName = 'priceGroupId';
	   var obj = $(eleName);
	   for (var i = obj.length; i-- > 0;)
	     obj[i] = null;
	   var opsval = $v('opsEntityId');
	   var j = 0 ;
	   for (var i = priceData.length; i-- > 0;)
			if( priceData[i].opsId == opsval ) { 			
	   			setOption(j,priceData[i].priceName,priceData[i].priceId, eleName);
	   			j++;
			}
}

function submitSearchForm(){
	if ($v("currencyId").trim().length == 0) {
		alert(messagesData.missingCurrency);
		return;
	}
	
	showPleaseWait();
	document.genericForm.submit();	
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="done();setup();">

<tcmis:form action="/newpricelist.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     
    </div> <%-- boxhead Ends --%>

<div class="dataContent">

<fieldset>
<legend>&nbsp;<fmt:message key="label.pricelist"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<%-- 
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.pricegroup"/>:</td>
    <td class="optionTitleBoldLeft">    
    <input type="text" name="priceGroupId" id="priceGroupId" class="inputBox" value=""/>
    </td>
</tr>
--%>
<c:if test="${module == 'distribution'}">
<input type="hidden" name="companyId" id="companyId" value="HAAS"/>  
</c:if>
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.pricegroupname"/>:</td>
    <td class="optionTitleBoldLeft">    
    <input type="text" name="priceGroupName" id="priceGroupName" class="inputBox" value=""/>
    </td>
</tr>
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.pricegroupdesc"/>:</td>
    <td class="optionTitleBoldLeft">    
    <input type="text" name="priceGroupDesc" id="priceGroupDesc" class="inputBox" value=""/>
    </td>
</tr>
<tr>
	<td width="10%" class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.controllingopsentityid"/>:</td>
    <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">        
         </select>
    </td>	
</tr>
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.defaultcurrency"/>:</td>
    <td class="optionTitleBoldLeft">
		<select name="currencyId" id="currencyId" class="selectBox">
	 	   	<option value=""><fmt:message key="label.selectOne"/></option>
    	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	 	   	<option value="${cbean.currencyId}">${cbean.currencyId}</option>
    	</c:forEach>
    	</select>
    </td>
</tr>
<tr>
 <td class="optionTitleBoldRight"><fmt:message key="label.baselineresetdate"/>:</td>
    <td class="optionTitleBoldLeft">    
 <input name="expireDate" id="expireDate" type="text" class="inputBox pointer" size="10" readonly="readonly" onclick="getCalendar(document.genericForm.expireDate);" />    </td>
</tr>

<tr style="display:none">
	<td width="8%" class="optionTitleBoldRight" title='<fmt:message key="label.addressToolTip"/>'><fmt:message key="label.fulladdress"/>:<span style='font-size:12.0pt;color:red'>*</span></td>
	<td width="15%"><input type="text" class="inputBox" name="remitToAddressLine1" id="shipToAddressLine1" value="${newShipToBean.shipToAddressLine1Display}" size="30" maxlength="40"/></td>
		
	<td width="15%" class="optionTitleBoldRight"><fmt:message key="label.default"/>&nbsp;<fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>
</tr>


<tr>
  <td class="optionTitleBoldRight"></td>
    <td class="optionTitleBoldLeft">    
          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="button.submit"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
          		 onclick="submitSearchForm();"/>
    </td>
</tr>



</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
</fieldset>


<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="new"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="minHeight" id="minHeight" type="hidden" value="300"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>
</body>
</html:html>
