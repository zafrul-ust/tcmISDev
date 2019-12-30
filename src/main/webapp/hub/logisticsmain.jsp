<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp"%>

<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%@ include file="/common/opshubig.jsp"%>

<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp"%>

<%-- For Calendar support --%>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<%-- Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

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
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/hub/logistics.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />


<title><fmt:message key="label.logistics" /></title>

<script language="JavaScript" type="text/javascript">//add all the javascript messages here, this for internationalization of client side javascript messages
<%--
var althubid = new Array(<c:forEach var="hub" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
	"${hub.branchPlant}"<c:if test="${!status.last}">,</c:if></c:forEach>
	);

var althubName = new Array(<c:forEach var="hub" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
   "${hub.hubName}"<c:if test="${!status.last}">,</c:if></c:forEach>
   );


var altinvid = new Array();
var altinvName = new Array();
<c:forEach var="hub" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
	altinvid["${hub.branchPlant}"] = new Array(""<c:forEach var="ig" items="${hub.inventoryGroupCollection}" varStatus="status1">
		,"${ig.inventoryGroup}"</c:forEach>
	);
	altinvName["${hub.branchPlant}"] = new Array("All"<c:forEach var="ig" items="${hub.inventoryGroupCollection}" varStatus="status1">
,"<tcmis:jsReplace value="${ig.inventoryGroupName}" processMultiLines="true" />"</c:forEach>
	);
</c:forEach>
--%>
 var searchMyArr = [                              
                    { value:'is', text:'<fmt:message key="label.is"/>'},
                    { value:'contains', text:'<fmt:message key="label.contains"/>'},
                    { value:'startsWith', text:'<fmt:message key="label.startswith"/>'},
                    { value:'endsWith', text:'<fmt:message key="label.endswith"/>'}
                   ];  
 
var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
	showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
	waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	errorinteger:"<fmt:message key="label.errorinteger"/>",
	inputSearchText:"<fmt:message key="label.inputSearchText"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
	itemInteger:"<fmt:message key="error.item.integer"/>"
	};

var resulttarget;
function sethubname() {
	  	var hubO = $('hub');
		$('hubName').value = hubO.options[hubO.selectedIndex].text;  
}
defaults.hub.callback = sethubname;

function createXSL(){  
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','LogisticsReport','650','600','yes');
	    resulttarget = document.genericForm.target;
	    document.genericForm.target='LogisticsReport';
	    var a = window.setTimeout("document.genericForm.submit();document.genericForm.target=resulttarget",50);
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


function searchFieldOnchange(selectedValue) {
	if( $v('searchField') == 'catPartNo' ) {
		$("obsoleteCell").innerHTML = '<input name="includeObsoleteParts" id="includeObsoleteParts" type="checkbox" value="Y" /> Include Obsolete Parts'
	}
	else {
		$("obsoleteCell").innerHTML = '&nbsp;';
	}
	

	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;
	  
	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith') &&
		    	(selectedValue == 'radianPo' || selectedValue == 'receiptId' || selectedValue == 'originalReceiptId' || selectedValue == 'itemId'))
		    	continue;
		    setOption(actuallyAddedCount++,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
	  }	

	  //     opts[i] = null; setOption(pos,text,value, 'searchMode');you can remove and rebuild too...
}

function validateForm() {
	var searchArgument = $('searchArgument');
	searchArgument.value = searchArgument.value.trim();

	var searchField = $('searchField');
	if ((searchField.value == 'radianPo' || searchField.value == 'itemId' || searchField.value == 'receiptId' || searchField.value == 'originalReceiptId') && !isInteger(searchArgument.value, true)) {
		alert(messagesData.errorinteger);
		searchArgument.focus();
		return false;
	}

	var hub0 = document.getElementById("hub");
	if ( ( hub0.value == '' || hub0.value == 'All') && searchArgument.value.length == 0) {
		alert(messagesData.inputSearchText);
		return false;
	}

	return true;
}

function submitSearchForm() {
	var ee = window.event;
	if( ee == null ) ee = arguments[0];
	var now = new Date();
	document.getElementById('startSearchTime').value = now.getTime(); 
	if( validateForm() && submitSearchOnlyOnce() ) { 
		  var now = new Date();
		  document.getElementById("startSearchTime").value = now.getTime();
		  $('uAction').value = 'search';
		  if( $v('searchField') == 'lotStatus' ) 
		  $('searchArgument').value = $('lotStatusValue').value;
		  showPleaseWait();
		  document.genericForm.submit();
	}
	return false;//1174146
}

function addnewBin(){
  var newbinURL = "/tcmIS/Hub/AddNewBin?";
  if( !$v('hub') ) {
	  alert('<fmt:message key="label.pleaseselectahub"/>');
	  return false;
  }
  newbinURL = newbinURL + "HubName=" + $v('hub');
  openWinGeneric(newbinURL,"add_newbin","400","200","Yes")
}

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#receiverId').val(formatted.split(":")[0]);
		$("personnelName").className = "inputBox"; 
	} 

	j$("#personnelName").autocomplete("/tcmIS/haas/getpersonneldata.do",{
		extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
					  companyId: function() { return j$("#companyId").val(); } },
		width: 200,
		max: 10,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 200,
		formatItem: function(data, i, n, value) {
			return  value.split(":")[1].substring(0,40);
		},
		formatResult: function(data, value) {
			return value.split(":")[1];
		}
	});
	
	j$('#personnelName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);
	
		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidatePersonnel();
	}));
	
	
	j$("#personnelName").result(log).next().click(function() {
		j$(this).prev().search();
	});
}); 

function invalidatePersonnel()
{
 var personnelName  =  document.getElementById("personnelName");
 var receivedbyid  =  document.getElementById("receiverId");
 if (personnelName.value.length == 0) {
   personnelName.className = "inputBox";
 }else {
   personnelName.className = "inputBox red";
 }
 //set to empty
 receivedbyid.value = "";
}

function lookupPerson()
{
	 loc = "searchpersonnelmain.do?displayArea=personnelName&valueElementId=receiverId";
	 
	 if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/haas/" + loc;
	 
	 openWinGeneric(loc,"PersonnelId","650","455","yes","50","50");
}

function personnelChanged(id, name) {
	document.getElementById("personnelName").className = "inputBox";
}

</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','logisticsNew');setOps();" onresize="resizeFrames()">

	<div class="interface" id="mainPage" style="">

		<!-- Search Frame Begins -->
		<div id="searchFrameDiv">
			<%--NEW - removed the search frame and copied the search section here--%>
			<div class="contentArea">
				<tcmis:form action="/logisticsresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
					<!-- Search Option Begins -->
					<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont filterContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright">
												<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
											</div>
										</div>
										<div class="roundContent">
											<table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
												<tr>
													<td nowrap width="8%" class="optionTitleBoldRight"><fmt:message key="label.operatingentity" />:</td>
													<td width="10%" class="optionTitleBoldLeft"><select name="opsEntityId" id="opsEntityId" class="selectBox">
													</select></td>
													<td width="5%" class="optionTitleBoldLeft" colspan="3" nowrap="nowrap"><fmt:message key="label.search" />: <select class="selectBox" name="searchField" id="searchField" onchange="searchFieldOnchange(this.value)">
															<option value="bin">
																<fmt:message key="label.bin" />
															</option>
															<option value="catPartNo">
																<fmt:message key="label.partnumber" />
															</option>
															<option value="deliveryTicket">
																<fmt:message key="label.deliveryticket" />
															</option>
															<option value="itemDesc">
																<fmt:message key="label.itemdesc" />
															</option>
															<option value="itemId">
																<fmt:message key="label.itemid" />
															</option>
															<%-- <option value="lotStatus"><fmt:message key="label.lotstatus"/></option> --%>
															<option value="mfgLot">
																<fmt:message key="label.mfglot" />
															</option>
															<option value="originalReceiptId">
																<fmt:message key="label.originalreceiptid" />
															</option>
															<option value="ownerCompanyId">
																<fmt:message key="label.ownercompanyid" />
															</option>
															<option value="poNumber">
																<fmt:message key="label.customerpo" />
															</option>
															<option value="radianPo">
																<fmt:message key="label.po" />
															</option>
															<option selected value="receiptId">
																<fmt:message key="label.receiptid" />
															</option>
													</select> <select name="searchMode" id="searchMode" class="selectBox">
															<option value="is">
																<fmt:message key="label.is" />
															</option>
															<option value="startsWith">
																<fmt:message key="label.startswith" />
															</option>
													</select> <input class="inputBox" type="text" name="searchArgument" id="searchArgument" value="" size="20" onkeypress="onKeyPress(event)" /></td>
													<td width="5%" class="optionTitleBoldLeft" rowspan="6" valign="top" colspan="2">
														<%-- 			<span id="lotStatusSpan" style="display:none"> --%> <select id="lotStatus" name="lotStatus" class="selectBox" multiple="multiple" size="10">
															<c:forEach var="bean" items="${lotStatusColl}" varStatus="status">
																<c:set var="jspLabel" value="" />
																<c:if test="${fn:length(status.current.jspLabel) > 0}">
																	<c:set var="jspLabel">${status.current.jspLabel}</c:set>
																</c:if>
																<option value="${bean.lotStatus}">
																	<fmt:message key="${jspLabel}" />
																</option>
																<c:if test="${bean.lotStatus eq 'Found'}">
																	<option value="Incoming">
																		<fmt:message key="label.incoming" />
																	</option>
																</c:if>
															</c:forEach>
													</select>
													</td>
												</tr>
												<tr>
													<td nowrap width="6%" class="optionTitleBoldRight"><fmt:message key="label.hub" />:</td>
													<td width="5%" class="optionTitleBoldLeft"><select name="hub" id="hub" class="selectBox" onchange=""></select></td>
													<td width="5%" class="optionTitleBoldLeft" colspan=2 nowrap="nowrap"><input type="checkbox" name="showNeedingPrint" id="showNeedingPrint" value="Yes" /> <fmt:message key="label.showonlyreceipts" /></td>
												</tr>
												<tr>
													<td nowrap class="optionTitleBoldRight"><fmt:message key="label.inventorygroup" />:</td>
													<td width="5%" class="optionTitleBoldLeft"><select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select></td>
													<td width="5%" class="optionTitleBoldLeft" nowrap="nowrap"><input type="checkbox" name="includeHistory" id="includeHistory" value="Yes" /> <fmt:message key="label.includehistory" /> </td> 
													<td width="5%" class="optionTitleBoldLeft" nowrap="nowrap" id="obsoleteCell">&nbsp;</td>
													<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.lotstatus" />:</td>
												</tr>
												
												<tr>
													<td width="5%" class="optionTitleBoldRight"><fmt:message key="label.sort" />:&nbsp;</td>
													<td width="10%" class="optionTitleBoldLeft"><select name="sortBy" id="sortBy" class="selectBox">
															<option value="receiptId|desc">
																<fmt:message key="label.receiptid" />
															</option>
															<option value="dateOfReceipt,receiptId|desc">
																<fmt:message key="label.dateofreceipt" />
															</option>
															<option value="lotStatusAge,lotStatus,itemId,receiptId|desc">
																<fmt:message key="label.ageStatusItem" />
															</option>
															<option value="receiptExpireDate|desc,receiptId|desc">
																<fmt:message key="label.expiredate" />
															</option>
															<option value="bin,receiptId|desc">
																<fmt:message key="label.binRceId" />
															</option>
															<option value="inventoryGroup,receiptExpireDate,receiptId|desc">
																<fmt:message key="label.inventorygroup" />
																/
																<fmt:message key="label.expiredate" />
															</option>
															<option value="inventoryGroup,itemId,receiptId|desc">
																<fmt:message key="label.inventorygroup" />
																/
																<fmt:message key="label.item" />
															</option>
															<option value="itemId,lotStatus,receiptId|desc">
																<fmt:message key="label.item" />
																/
																<fmt:message key="label.status" />
																/
																<fmt:message key="label.receiptid" />
															</option>
															<option value="ownerCompanyId,bin,receiptId|desc">
																<fmt:message key="label.ownerBinRecp" />
															</option>
													</select></td>
													<td width="5%" class="optionTitleBoldLeft" nowrap="nowrap" colspan="4"><fmt:message key="label.transactiondate" />: <input name="transactionDate" id="transactionDate" type="text" class="inputBox pointer" size="10" readonly="readonly" onclick="getCalendar(document.genericForm.transactionDate);" /> &nbsp;</td>
												</tr>
												<tr>
													<td colspan=2>&nbsp;</td>												
													<td width="5%" colspan=3 class="optionTitleBoldLeft" nowrap="nowrap"><fmt:message key="label.receiver" />:&nbsp; <input type="text" name="personnelName" id="personnelName" value="" size="20" class="inputBox" /> <input name="receiverId" id="receiverId" type="hidden" value="" /> <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onclick="lookupPerson()" /> (<input name="activeOnly" id="activeOnly" type="checkbox" class="radioBtns" checked value="Y" /> <fmt:message key="label.activeOnly" />)</td>
												</tr>
												<tr>
													<td colspan=2>&nbsp;</td>												
													<td width="5%" colspan=3 class="optionTitleBoldLeft" nowrap="nowrap"><fmt:message key="label.expwithin" />:&nbsp; <input class="inputBox" type="text" name="numDaysOld" id="numDaysOld" value="" size="3" maxlength="5" /> <fmt:message key="label.days" /></td>
												</tr>
												<tr>
													<td colspan="6" class="optionTitleLeft"><input onclick="return submitSearchForm()" type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="search" id="search" />&nbsp; <input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExl" id="createExl" onclick="createXSL()" /> <input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createnewbin"/>" name="addNewBin" id="addNewBin" onclick="return addnewBin()" /></td>
												</tr>
											</table>
										</div>
										<div class="roundbottom">
											<div class="roundbottomright">
												<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
					<!-- Search Option Ends -->


					<!-- Hidden element start -->
					<div id="hiddenElements" style="display: none;">
						<input type="hidden" name="uAction" id="uAction" value="search" /> <input name="startSearchTime" id="startSearchTime" type="hidden" value="" /> <input type="hidden" name="companyId" id="companyId" value="${personnelBean.opsHubIgOvBeanCollection[0].companyId}" /> <input name="searchHeight" id="searchHeight" type="hidden" value="189" /> <input name="hubName" id="hubName" type="hidden" value="" />
						<input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>
					</div>
					<!-- Hidden elements end -->

				</tcmis:form>
			</div>
			<!-- close of contentArea -->

		</div>
		<!-- Search Frame Ends -->

		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

			<%--NEw -Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
			<!-- Transit Page Ends -->

			<div id="resultGridDiv" style="display: none;">
				<!-- Search results start -->
				<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="roundcont contentContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright">
											<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
										</div>
									</div>
									<div class="roundContent">
										<div class="boxhead">
											<%-- boxhead Begins --%>
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          									Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
											<div id="mainUpdateLinks" style="display: none">
												<%-- mainUpdateLinks Begins --%>
												<span id="showlegendLink"> <a href="javascript:showlogisticspagelegend()"><fmt:message key="label.showlegend" /></a>
												</span> <span id="updateResultLink" style="display: none"> | <a href="javascript:call('update')"><fmt:message key="label.update" /></a> | <a href="javascript:call('generatelabels')"><fmt:message key="label.containerlabels" /></a> | <a href="javascript:call('printReceivingBoxLabels')"><fmt:message key="pickingqc.receivinglabels" /></a> | <a href="javascript:call('printDocumentLabels')"><fmt:message key="label.documentlabels" /></a> | <a href="javascript:call('generatealllabels')"><fmt:message key="cabinetlabel.label.generatealllabel" /></a> <input type="checkbox" name="printKitLabels" id="printKitLabels" value="Yes"><fmt:message key="label.skipkitcaseqtylabels" /> <input type="radio" name="paperSize" onclick="gerResultFrame().$('Paper').value='31';" value="31" checked />3"/1" &nbsp; <input type="radio" name="paperSize" onclick="gerResultFrame().$('Paper').value='038';" value="038" />4-3/16" / 3/8" &nbsp; </span>
											</div>
											<%-- mainUpdateLinks Ends --%>
										</div>
										<%-- boxhead Ends --%>

										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
										</div>

										<%-- result count and time --%>
										<div id="footer" class="messageBar"></div>

									</div>
									<div class="roundbottom">
										<div class="roundbottomright">
											<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- Result Frame Ends -->

	</div>
	<!-- close of interface -->

	<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;"></div>

	<div id="transitDailogWin" class="errorMessages" style="display: none; left: 20%; top: 20%; z-index: 5;"></div>
	<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
		<table width="100%" border="0" cellpadding="2" cellspacing="1">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="center" id="transitLabel"></td>
			</tr>
			<tr>
				<td align="center"><img src="/images/rel_interstitial_loading.gif" align="middle"></td>
			</tr>
		</table>
	</div>
	<%-- show legend Begins --%>
	<div id="showLegendArea" style="display: none;overflow: auto;">
	  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
	    <tr><td width="100px" class="yellow">&nbsp;</td><td class="legendText"><fmt:message key="label.aogsupercriticalpo"/></td></tr>
	    <tr><td width="100px" class="green">&nbsp;</td><td class="legendText"><fmt:message key="label.receiptformlitem"/></td></tr>
	    <tr><td width="100px" class="red">&nbsp;</td><td class="legendText"><fmt:message key="label.errorprocessingreceipt"/></td></tr>
	  </table>
	</div>
</body>
</html>