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
<%--
<script type="text/javascript" src="/js/distribution/partsearchmain.js"></script>
 --%>
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
Add Part to Price Group
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
itemInteger:"<fmt:message key="error.item.integer"/>"};



function setCatalogCompanyName(){
	var c = $('catalogCompanyId'); 
	$('catalogCompanyName').value = c.options[c.selectedIndex].text;
 }

function setCatalogName(){
	var c = $('catalogId'); 
	$('catalogName').value = c.options[c.selectedIndex].text;
 }

var pcdefaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>'},
	   	   hub: {id:'',name:'<fmt:message key="label.myhubs"/>'}
}

pcdefaults.ops.nodefault = true;
pcdefaults.hub.nodefault = true;
pcdefaults.ops.callback = setCatalogCompanyName;
pcdefaults.hub.callback = setCatalogName;

var pc = [
	    <c:forEach var="nouse0" items="${priceGroupCatalogOv}" varStatus="status">
	    <c:if test="${ status.index !=0 }">,</c:if>
	        {
	          id:   '${status.current.catalogCompanyId}',
	          name: '<tcmis:jsReplace value="${status.current.catalogCompanyName}"/>',
	          coll: [ <c:forEach var="nouse1" items="${status.current.catalogCollection}" varStatus="status1">
	    	     	  <c:if test="${ status1.index !=0 }">,</c:if>
	        	 	  {  id: '${status1.current.catalogId}',
	        	 	   name: '<tcmis:jsReplace value="${status1.current.catalogDesc}"/>'
	        		   }
	        		   </c:forEach>
	          	     ]
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
	  obj.selectedIndex = 0;
	}

	function setOps() {
	 	buildDropDown(pc,pcdefaults.ops,"catalogCompanyId");
	 	$('catalogCompanyId').onchange = opsChanged;
	    $('catalogId').onchange = hubChanged;	
	    opsChanged();
	}

	function opsChanged()
	{  
	   var opsO = $("catalogCompanyId");
	   var arr = null;
	   if( opsO.value != '' ) {
	   	   for(i=0;i< pc.length;i++)
	   	   		if( pc[i].id == opsO.value ) {
	   	   			arr = pc[i].coll;
	   	   			break;
	   	   		}
	   }

	   buildDropDown(arr,pcdefaults.hub,"catalogId");
	   hubChanged();
	   if( pcdefaults.ops.callback ) pcdefaults.ops.callback();
	}

	function hubChanged()
	{
	   var opsO = $("catalogCompanyId");
	   var hubO = $("catalogId");
	   var arr = null;
	   if( opsO.value != '' && hubO.value != '' ) {
	   	   for(i=0;i< pc.length;i++)
	   	   		if( pc[i].id == opsO.value ) {
			   	   for(j=0;j< pc[i].coll.length;j++)
		   	   		if( pc[i].coll[j].id == hubO.value ) {
		   	   			arr = pc[i].coll[j].coll;
		   	   			break;
	   	   		    }
	   	   		 break;
	   	   		}
	   }
//	   buildDropDown(arr,defaults.inv,"inventoryGroup");
	   if( pcdefaults.hub.callback ) pcdefaults.hub.callback();

	}


function setup() {
	setOps();
//	$('catalogCompanyId').value = '${param.catalogCompanyId}';
//	$('catalogId').value = '${param.catalogId}' ;
//	setCatalogCompanyName();
/*	var eleName = 'inventoryGroup';
	   var obj = $(eleName);
	   for (var i = obj.length; i-- > 0;)
	     obj[i] = null;
	   
	   var selectedIndex =0;
	   for(i=0;i< igarr.length;i++) {
				setOption(i,igarr[i].text,igarr[i].value, eleName);
				if( igarr[i].value == preinv )
					selectedIndex = i;
	   } 
   	   obj.selectedIndex = selectedIndex;
	   */
}


function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}

window.onresize = resizeFrames;
windowCloseOnEsc = true;


// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setup();loadLayoutWin('','partSearch');closeAllchildren();" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/pricegrouppartsearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
        <td width="2%" class="optionTitleBoldRight" ><fmt:message key="label.catalogcompany" />:</td> 
        <td width="45%" class="optionTitleLeft"> 
          <select name="catalogCompanyId" id="catalogCompanyId" class="selectBox">
          </select>         
          <input class="inputBox" type="hidden" name="catalogCompanyName" id="catalogCompanyName" value=""/>
        </td>
        <td width="2%" class="optionTitleBoldRight" ><fmt:message key="label.partnumber" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap> 
          <select name="searchPartNumberMode" id="searchPartNumberMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>         
          <input class="inputBox" type="text" name="partNumber" id="partNumber" size="15" onkeypress="return onKeyPress()"/>
        </td>
      </tr>
      <tr>
        <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.catalog" />:</td> 
        <td width="45%" class="optionTitleLeft">
          <select name="catalogId" id="catalogId" class="selectBox">
          </select>
          <input class="inputBox" type="hidden" name="catalogName" id="catalogName" value=""/>
        </td>
        <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.text" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchTextMode" id="searchTextMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="text" id="text" size="15" onkeypress="return onKeyPress()"/>
        </td>


      </tr>
      
      <%--     Row 2    --%>
      <tr>
        <td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.partdescription"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchPartDescMode" id="searchPartDescMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>           
          <input class="inputBox" type="text" name="partDesc" id="partDesc" size="15" onkeypress="return onKeyPress()"></input>
        </td>
        <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.synonym" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap="nowrap">
          <select name="searchAlternateMode" id="searchAlternateMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="alternate" id="alternate" size="15" onkeypress="return onKeyPress()"/>
        </td>

      </tr>
      
      <%--     Row 3   
      <tr>
        <td width="2%" class="optionTitleBoldRight" ><fmt:message key="label.specification"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchSpecificationMode" id="searchSpecificationMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>           
          <input class="inputBox" type="text" name="specification" id="specification" size="15" onkeypress="return onKeyPress()"></input>
        </td>
      </tr>
       <tr>
         <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.inventorygroup" />:</td>
        <td width="45%" class="optionTitleLeft" nowrap>
           Row 4
          <select name="autoAllocationIgMode" id="autoAllocationIgMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
      
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
          </select>
      
        </td>
          </tr>
          --%>
      <%--     Row 4    --%>
      <tr>       
        <td class="optionTitleLeft" colspan="6" nowrap>				
			<input type="submit" name="submitSearch" id="submitSearch" value="<fmt:message key="label.search"/>" class="inputBtns"
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
				onclick="return submitSearchForm();" />
			<input name="close" type="button" class="inputBtns" value="<fmt:message key="label.close"/>" id="close" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "javascript:closeAllchildren();window.close();"/>
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
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
<input name="priceGroupId" id="priceGroupId" type="hidden" value="${param.priceGroupId}"/>
<input name="priceGroupName" id="priceGroupName" type="hidden" value="${param.priceGroupName}"/>
<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="curpath" id="curpath" type="hidden" value="${param.curpath} > <fmt:message key="label.addline"/>"/>
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
		<span id="currencySpan" style="display: none">
		<select name="currencyId" id="currencyId" class="selectBox">
    	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	 	   	<option value="${cbean.currencyId}" <c:if test="${currencyId eq cbean.currencyId}">selected</c:if>>${cbean.currencyId}</option>
    	</c:forEach>
    	</select>
    	</span>
		<%-- 
		<span id="newPackagingSpan" style="display"><a href="javascript:newPackaging()"><fmt:message key="label.newpackaging"/></a> | </span>
		<span id="newMaterialSpan" style="display"><a href="javascript:newMaterial()"><fmt:message key="label.newmaterial"/></a></span>
		--%>
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