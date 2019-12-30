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
<%@ include file="/common/opshubig.jsp" %>

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
<script type="text/javascript" src="/js/common/opshubig.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<c:set var="hasCatalogManagementPermission" value='Y'/>

<title>
   ${param.curpath} &gt; Supplier <fmt:message key="label.pricelist"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
iteminteger:"<fmt:message key="error.item.integer"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
missingSearchAndSupplier:"<fmt:message key="label.missingsearchandsupplier"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

defaults.ops.nodefault = true;
//defaults.hub.nodefault = true;
//defaults.hub.callback = changeRoom;
function opschan() {
  try {
	document.getElementById('additemtoacllink').innerHTML='';//' | <a href="javascript:call(\'newitem2\')">Add Task</a>';
	checkPermission();
  } 
  catch(ex) {}
}

defaults.ops.callback = opschan;

function pgChanged(){
	var c = $('priceGroupId'); 
	$('priceGroupName').value = c.options[c.selectedIndex].text;
	for( i =0; i< currpgs.length;i++) {
		if( currpgs[i].id == c.value )  {
//			alert( currpgs[i].id+":"+currpgs[i].companyId );
			if( currpgs[i].companyId )
				$('companyId').value = currpgs[i].companyId;
			else
				$('companyId').value = '';
			break;
		}
	}
	try {
   		$("additemtoacllink").innerHTML = ' | <a href="javascript:addnewitem()">Add Task</a>';
    } catch(ex) {}
}
	
var dhxFreezeWins = null;
function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxFreezeWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		if (dhxFreezeWins.window("transitDailogWin")) {
			dhxFreezeWins.window("transitDailogWin").setModal(false);
			dhxFreezeWins.window("transitDailogWin").hide();
		}
	}
}
var children = new Array();

function newAssignmentReturn(r0,r1,r2,r3){
//	alert("catalogsullierresults:"+r0+":"+r1+":"+r2+":"+r3);
	$('uAction').value = 'newAssignment';
	suppOptions = $('supplier').options;
	size= $('supplier').length;
	for(i=0;i<size;i++) {
		if( suppOptions[i].value == r0 ) {
			$('supplier').selectedIndex = i;
			break;
		}
	}
	$('catalogQueueItemId').value=r1;
//	$('task').value = r2;
	$('localeCode').value = r3;
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();
	document.genericForm.target='resultFrame';
	showPleaseWait();
	document.genericForm.submit();
}

function newAssignment( r1, r2 )
{
	$('supplierName').value = $('supplier').options[$('supplier').selectedIndex].text;
	loc = "/tcmIS/catalog/newtask.do?supplier=" + $v("supplier")+"&supplierName=" + encodeURIComponent( $v("supplierName") );
	openWinGeneric(loc,"newassignment","800","500","yes","50","50")
}

function taskitemsearch(){
	var loc = "taskitemsearch.do?uAction=new&shipToId=";  
	   openWinGeneric(loc,"newitem","800","500","yes","50","50");
		return;
}

function setItem( r0,r1,r2) {
	var elename = 'catalogQueueItemId';
	if( r0 == 'new') {
	var obj = $(elename);
	var size = obj.length;
	var arrValue = new Array(size+1);
	var arrText = new Array(size+1);
	
      for (var i = 0; i< size; i++) {
		     arrValue[i] = obj.options[i].value;
		     arrText[i] = obj.options[i].text;
	   }
	   for (var i = obj.length; i > 0;i--)
		     obj[i] = null;
	obj.options[0].value = r1;
	obj.options[0].text = r2;
	   for (var i = 0; i < size; i++) {
			  var optionName = new Option(arrText[i], arrValue[i], false, false);
			  obj.options[i+1] = optionName;
	   }
	  $(elename).selectedIndex = 0;
	}		
}

function supplierChanged() {
	var elename = 'supplier';
	var obj = $(elename);
	var size = obj.length;
	var arrValue = new Array(size+1);
	var arrText = new Array(size+1);
	
      for (var i = 0; i< size; i++) {
		     arrValue[i] = obj.options[i].value;
		     arrText[i] = obj.options[i].text;
	   }
	   for (var i = obj.length; i > 0;i--)
		     obj[i] = null;
	obj.options[0].value = $v('valueElementId');
	obj.options[0].text = $v('displayElementId');
	   for (var i = 0; i < size; i++) {
			  var optionName = new Option(arrText[i], arrValue[i], false, false);
			  obj.options[i+1] = optionName;
	   }
	  $(elename).selectedIndex = 0;

} 

function getSuppliers()
{
   loc = "/tcmIS/supply/posuppliermain.do?popUp=Y&displayElementId=displayElementId&valueElementId=valueElementId&statusFlag=true&fromSupplierPriceList=Y";
   openWinGeneric(loc,"supplierssearch","800","500","yes","50","50")
}

function clearSupplier()
{
    document.getElementById("supplierName").value = "";
    document.getElementById("supplier").value = "";
}

function getVendorAssignment()
{
   loc = "/tcmIS/catalog/newvendorassignment.do?uAction=open&supplier="+$v("supplier");
   
//   alert( loc );
   openWinGeneric(loc,"supplierssearch","800","500","yes","50","50")
}

function clearSupplier()
{ 
    document.getElementById("supplierName").value = "";
    document.getElementById("supplier").value = "";
}

window.onresize= resizeFrames;

function submitSearchForm()
{
  if(validateForm()) {
   var now = new Date();
   document.getElementById("startSearchTime").value = now.getTime();
   
   $('supplierName').value = $('supplier').options[$('supplier').selectedIndex].text;
   
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
	return true;
}

function checkPermission() {
	
}

function createXSL() {
	document.getElementById('uAction').value="createXSL";
	document.genericForm.target='_Price_List_Excel_Report';
//	the regular result frame use following commented line
//	document.genericForm.target='resultFrame';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_Price_List_Excel_Report','650','600','yes');
	document.genericForm.submit();
	document.genericForm.target='resultFrame';
}

function checkPermission() {
	 
	  var opsEntityId = $v("opsEntityId");
	  var found = false; 

	  for(var i=0; i < OpsEntityPermissionArray.length; i++) {
	    if(OpsEntityPermissionArray[i] == opsEntityId) {
	      found = true;
	    }
	  }
	  if(found) {	    
	    //$('batchUpdateBuyerBtnSpan').style.visibility = 'visible';
	    $('additemtoacllink').style.visibility = 'visible';
	  }
	  else {	    
	    //$('batchUpdateBuyerBtnSpan').style.visibility = 'hidden';
	    $('additemtoacllink').style.visibility = 'hidden';
	  }
}


function changeSearchTypeOptions(selectedValue) 
{ 
	var searchType = $('searchMode'); 
	for (var i = searchType.length; i > 0;i--) 
		searchType[i] = null; 
	
	var actuallyAddedCount = 0; 
	for (var i=0; i < searchMyArr.length; i++) 
	{ 
		if(selectedValue == 'itemId|number' && (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith')) 
			continue; 
		setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode");
		actuallyAddedCount++; 
	} 
}

var OpsEntityPermissionArray = new Array();
<c:set var="counter" value='${0}'/>
<c:forEach var="opsEntityBean" items="${personnelBean.permissionBean.userGroupMemberOpsEntityBeanCollection}" varStatus="status">
  <c:if test="${(status.current.userGroupId  == 'supplierPriceList')}">
    OpsEntityPermissionArray["<c:out value="${counter}"/>"] = "<c:out value="${status.current.opsEntityId}" escapeXml="false"/>";
    <c:set var="counter" value='${counter + 1}'/>    
  </c:if>
</c:forEach>

var supplyPathPermissionArray = new Array();
<c:forEach var="bean" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
	supplyPathPermissionArray['${bean.opsEntityId}'] = 'N';
  <tcmis:opsEntityPermission indicator="true" userGroupId="SPLsupplyPath" opsEntityId="${bean.opsEntityId}">
	supplyPathPermissionArray['${bean.opsEntityId}'] = 'Y';
  </tcmis:opsEntityPermission> 
</c:forEach>

var searchMyArr = new Array( 
		{value:'contains', text: '<fmt:message key="label.contain"/>'} 
		,{value:'is', text: '<fmt:message key="label.is"/>'} 
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'} 
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'} 
		); 
/*
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
*/
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','catalogSupplierManagement');setOps();checkPermission();" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/catalogsupplierresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.vendor"/>:&nbsp;
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
				    <select name="supplier" id="supplier" class="selectBox">
                        <c:forEach var="vendorBean" items="${catalogVendor}" varStatus="status">
                                <option value="${vendorBean.supplier}">${vendorBean.supplierName}</option>
                        </c:forEach>
    			        <option value="">All Vendor</option>
					</select>
					<c:if test="${hasCatalogManagementPermission  == 'Y'}">
						<input name="vendorAssignmentButton" type="button" class="inputBtns" value="New Vendor" id="vendorAssignmentButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return getSuppliers()"/>
					</c:if>          			  		
				</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
		 		<fmt:message key="label.task"/>:&nbsp;
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
                <select id="task" name="task" class="selectBox">
                  <option value="SDS Sourcing" selected>SDS Sourcing</option>
                  <option value="SDS Indexing">SDS Indexing</option>
                  <option value="SDS Authoring">SDS Authoring</option>
                  <option value="Item Creation">Item Creation</option>
                  <option value="">All Tasks</option>
                </select>
            </td>
	    </tr>
        <tr>
            <td>&nbsp;</td>
            <td class="optionTitleBoldLeft" nowrap="nowrap">
                <input type="checkbox" name="showInactive" id="showInactive" value="Y" class="radioBtns" /> <fmt:message key="label.showinactive"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" width="100%" class="optionTitleBoldLeft">
                <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm();"/>
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
<input type="hidden" name="uAction" id="uAction" value="search"/>
<input type="hidden" name="supplierName" id="supplierName" value="${param.supplierName}"/>
<input type="hidden" name="catalogQueueItemId" id="catalogQueueItemId" value=""/>
<input type="hidden" name="localeCode" id="localeCode" value=""/>
<input type="hidden" name="displayElementId" id="displayElementId" value="${param.displayElementId}"/>
<input type="hidden" name="valueElementId" id="valueElementId" value="${param.valueElementId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="200"/>
</div>
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
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
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
        <c:if test="${hasCatalogManagementPermission  == 'Y'}">
        	<a href="javascript:call('_simpleUpdate')"><fmt:message key="label.update"/></a>
        </c:if> 
      </span>
      <span id="additemtoacllink"></span>

      
      <%-- 
      <a href="javascript:simpleCallToServerWithGrid('pricelistresults.do?uAction=loaddata')"><fmt:message key="label.lotstatuslegend"/></a>
            <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="transactions">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
        </tcmis:permission>
      </span> --%>
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
				<img src="/images/rel_interstitial_loading.gif" align="middle"/>
			</td>
		</tr>
	</table>
</div>

</body>
</html>
