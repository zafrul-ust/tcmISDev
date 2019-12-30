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

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<title>
<fmt:message key="shipconfirm.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

function lookupCustomer() {    
	  loc = "customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
	  
	  if ($v("personnelCompanyId") == 'Radian') 
		  loc = "../distribution/" + loc;
	  
	  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}
function clearCustomer() {    

	$('customerName').value = '';
	$("customerName").className = 'inputBox';
	$('customerId').value = '';

}

function customerChanged(id, name) {
	$("customerName").className = 'inputBox';
}

var csrArr = new Array(
	{value:'',text:'<fmt:message key="label.all"/>',opsEntityId:''}
<c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
 	,{value:'${status.current.csrPersonnelId}',text:'<tcmis:jsReplace value="${status.current.csrName}"/>',opsEntityId:'${status.current.opsEntityId}'}
</c:forEach>
);

function setCsr() {
	   var eleName = 'customerServiceRepId';
	   var opsEntityId = $v('opsEntityId'); 
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	   var start = 0 ;
	   setOption(start++,csrArr[0].text,csrArr[0].value,eleName);
	   for ( var i=0; i < csrArr.length; i++) {
		   		if( csrArr[i].opsEntityId == opsEntityId )
		    		setOption(start++,csrArr[i].text,csrArr[i].value,eleName);
	   }
}

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;
defaults.ops.callback = setCsr;

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

var children= new Array();
function closeAllchildren()
{
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

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#customerId').val(formatted.split(":")[0]);
		$("customerName").className = "inputBox"; 
	} 
	
	j$("#customerName").autocomplete("/tcmIS/distribution/findcustomer.do",{
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

<body bgcolor="#ffffff" onload="loadLayoutWin('','${param.pageid}');setOps();setCsr();" onresize="resizeFrames()" onunload="closeAllchildren();">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/shipconfirmresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="500" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
      <td class="optionTitleBoldRight" nowrap> <fmt:message key="label.customer"/>:</td>
      <td class="optionTitleLeft" nowrap="nowrap">
      	<input type="text" name="customerName" id="customerName" value="" size="30"  class="inputBox"/>
      	<input name="customerId" id="customerId" type="hidden" value=""/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onclick="lookupCustomer()"/>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>
      <td width="5%" class="optionTitleBoldRight">
		      <fmt:message key="label.csr"/>:
		      </td>
		      <td>
   			<select name="customerServiceRepId" id="customerServiceRepId" class="selectBox">
	  		</select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>
		   <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
            <td colspan="2" class="optionTitleBoldLeft" nowrap="nowrap">
                <select name="searchField"  class="selectBox" id="searchField" >
                    <option value="prNumber"> <fmt:message key="label.mr"/>  </option>
                </select>
                &nbsp;
                <select name="searchMode" class="selectBox" id="searchMode" >
                    <option value="is"> <fmt:message key="label.is"/>  </option>
                </select>
             &nbsp;
                <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
            </td>     
    </tr>
    <tr>
	<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.sortby"/>:</td>
      <td width="10%" colspan="5">
          <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
          <html:option value="sd" key="shipconfirm.label.sortbydockdeliverto"/>
          <html:option value="sfa" key="shipconfirm.label.sortbydockfacilityworkarea"/>
          <html:option value="plb" key="shipconfirm.label.sortbymrlinepicklist"/>
          <html:option value="fpl" key="shipconfirm.label.sortbyfacilitymrline"/>
        </html:select></td>
      </tr>
    <tr>
     <td colspan="4" width="100%" class="optionTitleBoldLeft" nowrap>
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()">
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
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
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
    <input type="hidden" name="uAction" id="uAction" value="createXSL"/>
    <input type="hidden" name="hubName" id="hubName" value=""/>
    <input type="hidden" name="pageid" id="pageid" value='<tcmis:jsReplace value="${param.pageid}"/>'/>
    <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
    <input name="searchHeight" id="searchHeight" type="hidden" value="150">
    <input name='date60' id='date60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-60" datePattern="${dateFormatPattern}"/>'  />
    <input name='date7' id='date7' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="7" datePattern="${dateFormatPattern}"/>'  />
    <input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>
    <input type="hidden" name="isWmsHub"  id="isWmsHub" value="N"/>
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
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
        <span id="showlegendLink">
            <a href="javascript:simpleCallToServerWithGrid('inventoryreportresults.do?uAction=loaddata')"><fmt:message key="label.lotstatuslegend"/></a>
        </span>
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


	 <%-- freeze --%>
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
</html:html>