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

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
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
<script type="text/javascript" src="/js/supply/supitemnotesmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
   <fmt:message key="supplieritemnotes.title"/>
</title>

<script language="JavaScript" type="text/javascript">

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",searchInputInteger:"<fmt:message key="error.searchInput.integer"/>",
itemid:"<fmt:message key="label.itemid"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
missingSearchAndSupplier:"<fmt:message key="label.missingsearchandsupplier"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var defaultOpsEntityId = null;
<c:if test="${!empty personnelBean.opsHubIgOvBeanCollection && !empty personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId }">
	defaultOpsEntityId = '${personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId}';
</c:if>

var defaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true}
		   }
		   
var opspg = [
    <c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>'
        }
    </c:forEach>
   ];      	   
	   
function buildDropDown( arr, defaultObj, eleName ) {
		   var obj = $(eleName);
		   for (var i = obj.length; i > 0;i--)
		     obj[i] = null;
		  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
		  if( arr == null || arr.length == 0 ) 
			  setOption(0,defaultObj.name,defaultObj.id, eleName); 
		  else if( arr.length == 1 )
			  setOption(0,arr[0].name,arr[0].id, eleName);
		  else {
		      var start = 0;
		  	  if( defaultObj.nodefault )
		  	  	start = 0 ; // will do something??
		  	  else {
			  setOption(0,defaultObj.name,defaultObj.id, eleName); 
				  start = 1;
			  }
			  for ( var i=0; i < arr.length; i++) {
			    	setOption(start++,arr[i].name,arr[i].id,eleName);
			  }
		  }
		  
		  if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
		    	$('opsEntityId').value = defaultOpsEntityId;
		  }
		  //obj.selectedIndex = 0;
		}
// -->
</script> 	

</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','supplierItemNotes');buildDropDown(opspg,defaults.ops,'opsEntityId');document.getElementById('searchArgument').focus()" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/supplieritemnotesresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
		<table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<tr>
				<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.operatingentity" />:
				</td>
				<td width="40%">
					<select name="opsEntityId" id="opsEntityId"	class="selectBox">
					</select>
				</td>
				<td class="optionTitleBoldRight"><fmt:message key="label.search" />:
				</td>
				<td class="optionTitleBoldLeft">
					<select name="searchField" class="selectBox" id="searchField">
						<option value="itemId"><fmt:message key="label.itemid"/></option>
					</select>
					<select name="searchMode" class="selectBox" id="searchMode">
						<option value="is"><fmt:message key="label.is" /></option>
                 	</select>
					<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='<c:out value="${param.searchArgument}"/>' size="15" />
				</td>
			</tr>
			<tr>
            <td class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.supplier" />:</td>
                <td class="optionTitleBoldLeft" nowrap="nowrap" colspan="3">				
                                 <input name="supplierName" id="supplierName" type="text" size="30" class="invGreyInputText" readonly="readonly"/>                                                           
                                 <input name="supplier" id="supplier" type="hidden" value="${param.supplier}"/>                                    
                                 <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSuppliers()" type="button"/>
                                 <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.clear"/>" onclick="clearSupplier()" type="button"/>				
				</td>
			</tr>
			<tr>
     			<td colspan="4" width="100%" class="optionTitleBoldLeft">
          			<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()"/>
          			<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return createXSL()"/>
				</td>
    		</tr>
      </table>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="createXSL"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="opsEntityId" id="totaopsEntityIdlLines" value="${param.opsEntityId}" type="hidden"/>
<input name="searchHeight" id="searchHeight" type="hidden" value="150" />
</div>
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="display: none;margin: 0px 4px 0px 4px;">

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
    <div class="boxhead"> 
    <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: "> <%-- mainUpdateLinks Begins --%>
              <span id="updateResultLink" style="display: none"> 
			  <a href="#" onclick="resultFrame.updateExpedite();"><fmt:message key="label.update" /></a></span>
              <span id="deleteResultLink" style="display: none">|  <a href="#" onclick="resultFrame.deleteRow();"><fmt:message key="label.delete" /></a> | </span>
              <span id="addItemSpan" style="display: none"><a href="#" onclick="resultFrame.addNewComment('NewItemOK');"><fmt:message key="label.addNewComment"/></a></span>
       </div> <%-- mainUpdateLinks Ends --%>
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
</html>
      		
    		
