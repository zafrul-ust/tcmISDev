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
	document.getElementById('additemtoacllink').innerHTML='';//' | <a href="javascript:call(\'newitem2\')">Add Item</a>';
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
   		$("additemtoacllink").innerHTML = ' | <a href="javascript:addnewitem()">Add Item</a>';
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

function getSuppliers()
{
   loc = "/tcmIS/supply/posuppliermain.do?popUp=Y&displayElementId=supplierName&valueElementId=supplier&statusFlag=true&fromSupplierPriceList=Y&opsEntityId="+$v("opsEntityId");
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
	if ($v("searchArgument").length == 0 && $v("supplier").length == 0) {
		alert(messagesData.missingSearchAndSupplier);
		return false;
	}
	if($v("searchArgument").length > 0 && $v("searchField") == "itemId|number" && !isInteger($v("searchArgument"),true)) {
		alert(messagesData.iteminteger);
		return false;
	}
	return true;
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

function doBatchUpdateBuyer() {

	var loc = "batchupdatebuyer.do?uAction=new&shipToId=" + 
	"&inventoryGroup=" + $v("inventoryGroup")+
	"&hub=" + $v("hub")+
	"&opsEntityId=" + $v("opsEntityId")+
	"&supplier=" + $v("supplier")+
	"&supplierName=" + encodeURIComponent( $v("supplierName") )+
	"&companyId=Radian"; 
	
	loc += "&supplyPathPermission="+supplyPathPermissionArray[$v("opsEntityId")];
	
	var winId= 'batchUpdateBuyer';
	children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"550","575","yes","50","50","20","20","no");
	return  false;		
}

function doCreateTemplate(which) {
	document.getElementById('uAction').value=which;
	document.genericForm.target='_Price_List_Template';
//	the regular result frame use following commented line
//	document.genericForm.target='resultFrame';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_Price_List_Template','650','600','yes');
	document.genericForm.submit();
	//document.genericForm.target='resultFrame';
}

function doUploadTemplate() {
	var loc = "plupload.do"
	var winId= 'plUpload';
	
	openWinGeneric(loc,winId,"600","200","yes","50","50")
	return  false;		
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

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','supplierPriceList');setOps();checkPermission();" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/supplierpricelistresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
		<table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
			<tr>
				<td width="20%" class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.operatingentity" />:
				</td>
				<td width="30%">
					<select name="opsEntityId" id="opsEntityId"	class="selectBox">
					</select>
				</td>
				<td width="8%" class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.search" />:
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
					<select name="searchField" class="selectBox" id="searchField" onchange="changeSearchTypeOptions(this.value)" >
						<option value="itemId|number"><fmt:message key="label.itemid" /></option>
						<option value="itemDesc"><fmt:message key="label.itemdesc" /></option>
					</select>
					<select name="searchMode" class="selectBox" id="searchMode">
						<option value="is"><fmt:message key="label.is" /></option>					
						<option value="startsWith"><fmt:message	key="label.startswith" /></option>
					</select>
					<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="20" />
				</td>
			</tr>
			<tr>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.hub"/>:
				</td>
				<td class="optionTitleBoldLeft">
				         <select name="hub" id="hub" class="selectBox">
				         </select>
				</td>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.supplypath" />:
                </td>
				<td class="optionTitleBoldLeft">
				         <select name="supplyPath" id="supplyPath" class="selectBox">
				         	<option value="ALL"><fmt:message key="label.all"/></option>
							<c:forEach var="path" items="${vvSupplyPathBeanCollection}" varStatus="status">
				    	     	<option value="${path.supplyPath}"><fmt:message key="${path.jspLabel}"/></option>
							</c:forEach>
				         </select>
				</td>
			</tr>
			<tr>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.inventorygroup" />:
				</td>
				<td class="optionTitle">
				       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
				       </select>
				</td>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.supplier" />:
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input name="supplierName" id="supplierName" type="text" maxlength="18" size="15" class="invGreyInputText" readonly="readonly"/>                                                           
					<input name="supplier" id="supplier" type="hidden" value=""/>                                    
					<input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSuppliers()" type="button"/>
					<input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.clear"/>" onclick="clearSupplier()" type="button"/>				
                </td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
				</td>
                <td>
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input type="checkbox" name="showExpired" id="showExpired" value="Y" class="radioBtns" /> <fmt:message key="label.showinactive"/>
                    <input type="checkbox" name="showPriceHistory" id="showPriceHistory" value="Y" class="radioBtns" /> <fmt:message key="label.showpricehistory"/>
				</td>
			</tr>
			
    		<tr>
     			<td colspan="5" width="100%" class="optionTitleBoldLeft">
          			<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm();"/>
          			<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return createXSL()"/>
          			<input name="createUpdateTemplateBtn" type="button" class="inputBtns" value="<fmt:message key="label.updatepricelisttemplate"/>" id="createUpdateTemplateBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return doCreateTemplate('createUpdateTemplate')"/>
					<input name="createNewTemplateBtn" type="button" class="inputBtns" value="<fmt:message key="label.newpricelisttemplate"/>" id="createNewTemplateBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return doCreateTemplate('createInsertTemplate')"/>
           			<input name="uploadTemplateBtn" type="button" class="inputBtns" value="Upload Template" id="uploadTemplateBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return doUploadTemplate()"/>  		
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
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
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
        <a href="javascript:call('_simpleUpdate')"><fmt:message key="label.update"/></a>
      </span>
      <span id="additemtoacllink" style="display: none"></span>

      
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
