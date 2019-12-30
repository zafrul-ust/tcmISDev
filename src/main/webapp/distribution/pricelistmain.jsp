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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

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
   ${param.curpath} &gt; <fmt:message key="label.pricelist"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
addparttopricegroup:"<fmt:message key="label.addparttopricegroup"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

var showaddparttoacllink = false;

var defaultOpsEntityId = null;
<c:if test="${!empty personnelBean.opsHubIgOvBeanCollection && !empty personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId }">
	defaultOpsEntityId = '${personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId}';
</c:if>

var defaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true},
	   	   pg: {id:'',name:'<fmt:message key="label.all"/>'}
	   }

<c:set var="preops" value=""/>
<c:set var="opssep" value=""/>
var opspg = [
	    <c:forEach var="nouse0" items="${opsPgColl}" varStatus="status">
	    <c:set var="curops" value="${status.current.opsEntityId}"/>
	    <c:if test="${ preops ne curops}">
	    ${opssep}
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>',
          coll: [ 
        	 	  {  id: '${status.current.priceGroupId}',
           	 	   name: '<tcmis:jsReplace value="${status.current.priceGroupName}"/>',
//           	 	     name: '${status.current.priceGroupId}',
	    	    companyId:'${status.current.opsCompanyId}'
        		  }
        <c:set var="opssep" value="]},"/>
    	</c:if>	        
	    <c:if test="${ preops eq curops}">
	    	    	 	   ,
	    	    	 	   {
	    	    			id:'${status.current.priceGroupId}',
	    	    			name:'<tcmis:jsReplace value="${status.current.priceGroupName}"/>',
		    	    		companyId:'${status.current.opsCompanyId}'
//	              	 	     name: '${status.current.priceGroupId}'
	    	    		   }
    	</c:if>	        
    	<c:set var="preops" value="${curops}"/>
	    </c:forEach>
	    <c:if test="${!empty preops }">
	    	]}
	    </c:if>
	   ]; 

var pgarr = new Array();
var opsPermission = new Array();

<c:forEach var="nouse0" items="${opsPgColl}" varStatus="status">
	pgarr[ encodeURIComponent('${status.current.priceGroupId}') ] = encodeURIComponent('${status.current.opsCompanyId}');
	 <tcmis:opsEntityPermission indicator="true" userGroupId="updateCustomerPriceList" opsEntityId="${status.current.opsEntityId}">
	 	opsPermission[encodeURIComponent('${status.current.opsEntityId}') ] = encodeURIComponent('${status.current.opsEntityId}');
	 </tcmis:opsEntityPermission>
</c:forEach>		   

function getOpsCompanyid( coded_pgid ){
	return pgarr[coded_pgid];
}

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
 	buildDropDown(opspg,defaults.ops,"opsEntityId");
 	$('opsEntityId').onchange = opsChanged;
    $('priceGroupId').onchange = pgChanged;	
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
    opsChanged();
}

var currpgs = null;
function opsChanged() {
	   var opsO = $("opsEntityId");
	   var arr = null;
	   if( opsO.value != '' ) {
		   var temp1 = opsO.value;
	   	   for(i=0;i< opspg.length;i++)
	   	   		if( opspg[i].id == opsO.value ) {
	   	   			arr = opspg[i].coll;
	   	   			break;
	   	   		}
	   	   // check update permission
	   	   showaddparttoacllink = false;
	   	   if (opsPermission[opsO.value] != null )
	   		showaddparttoacllink = true; 
	   }
	   currpgs = arr;
	   buildDropDown(arr,defaults.pg,"priceGroupId");
	   pgChanged();
}

function addnewpart() {
	if(!$v('priceGroupId')){alert(messagesData.selectpricelist);return ;}call('newpart2');
}

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

	if( $('priceGroupId').value && showaddparttoacllink == true)
    	$("addparttoacllink").innerHTML = ' | <a href="javascript:addnewpart()">'+messagesData.addparttopricegroup+" "+$('priceGroupName').value+'</a>';
    else
    	$("addparttoacllink").innerHTML = '';// | <a href="javascript:addnewpart()">Add Part To Price Group </a>';

}
	
function newPriceList() {
	 openWinGeneric("newpricelist.do?opsEntityId="+encodeURIComponent( $v('opsEntityId') ) 
              ,"newpricelistmain","500","300","yes","40","40","no");
}

function priceListAdded() {
	location.reload();
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

function createXSL() {
	document.getElementById('uAction').value="createXSL";
	document.genericForm.target='_Price_List_Excel_Report';
//	the regular result frame use following commented line
//	document.genericForm.target='resultFrame';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_Price_List_Excel_Report','650','600','yes');
	document.genericForm.submit();
	document.genericForm.target='resultFrame';
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','priceList');setOps()" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/pricelistresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
				<td width="10%">
					<select name="opsEntityId" id="opsEntityId"	class="selectBox">
					</select>
				</td>
				<td class="optionTitleBoldRight" nowrap><fmt:message key="label.search" />:
				</td>
				<td class="optionTitleBoldLeft" nowrap>
					<select name="searchField" class="selectBox" id="searchField">
						<option value="partName"><fmt:message key="label.part" /></option>
						<option value="partDesc"><fmt:message key="label.partdesc" /></option>
					</select>
					<select name="searchMode" class="selectBox" id="searchMode">
						<option value="is"><fmt:message key="label.is" /></option>
                        <option value="contains" selected="selected"><fmt:message key="label.contains" /></option>
                        <option value="startsWith"><fmt:message	key="label.startswith" /></option>
						<option value="endsWith"><fmt:message key="label.endswith" /></option>
					</select>
					<input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="20" />
				</td>
			</tr>
			<tr>
				<td width="8%" class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.pricelist" />:
				</td>
				<td width="15%" class="optionTitle" nowrap>
				  <select name="priceGroupId" id="priceGroupId" class="selectBox">
<%--                       <option value=""><fmt:message key="label.all"/></option>
                    <c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	 	   				<option value="${cbean.priceGroupId}" <c:if test="${priceGroupId eq cbean.priceGroupId}">selected</c:if>>${cbean.priceGroupName}</option>
    				</c:forEach>
--%>    				
    			  </select>
<input type="hidden" name="priceGroupName" id="priceGroupName" value=""/> 
 <tcmis:opsEntityPermission indicator="true" userGroupId="updateCustomerPriceList" opsEntityId="${param.opsEntityId}">
    <c:set var="showaddpartalloclink" value='Y'/>
 </tcmis:opsEntityPermission>     		  
<tcmis:opsEntityPermission indicator="true" userGroupId="CreatePriceGroup">
	<input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="editButton" value="<fmt:message key="label.new"/>" onclick="newPriceList()" />
</tcmis:opsEntityPermission>
			</td>
				<td class="optionTitleBoldLeft" colspan="2" nowrap="nowrap">
					<input type="checkbox" name="showExpired" id="showExpired" value="Y" class="radioBtns" /> <fmt:message key="label.showexpired"/>
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
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
         <select name="hub" id="hub" class="selectBox">
         </select>
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
<input name="searchHeight" id="searchHeight" type="hidden" value="189"/>
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
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      &nbsp;
      <span id="updateResultLink" style="display: none">
        <a href="javascript:call('_simpleUpdate')"><fmt:message key="label.update"/></a>
      </span>

    	<span id="addparttoacllink" style="display: none">
      	</span>
      
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
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>
</body>
</html>