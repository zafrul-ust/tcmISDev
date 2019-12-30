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

<html:html lang="true">
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
<!-- This handles which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/client/catalog/deliveryscheduleviewmain.js"></script>
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
<fmt:message key="deliveryscheduleview"/>
</title>

<script language="JavaScript" type="text/javascript">
	var messagesData = new Array();
	messagesData= { 
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
			approvedmrsearchtext:"<fmt:message key="deliveryschedule.error.approvedmrsearchtext"/>"
	};
	
	var defaults = {
		reviewer: {nodefault: true},
		company: {id:'',name:'<fmt:message key="label.all"/>'},
	  	facility: {id:'',name:'<fmt:message key="label.all"/>'}
	}
	
	<c:set var="pregroup" value=""/>
	<c:set var="precomp" value=""/>
	<c:set var="revsep" value=""/>
	<c:set var="compsep" value=""/>
	
	var reviewerDropdownList = [
		<c:forEach var="revCompFacBean" items="${reviewerCompFacBeanCollection}" varStatus="status">
			<c:set var="curgroup" value="${status.current.userGroupId}" />
			<c:set var="curcomp" value="${status.current.companyId}" />
			<c:choose>
    		<c:when test="${pregroup ne curgroup}">
    			<c:if test="${!empty precomp }">]}</c:if>
    			${revsep}
    			{id: '<tcmis:jsReplace value="${curgroup}" />',
    				name: '<tcmis:jsReplace value="${curgroup}" />',
    				comp:[{
    					id: '${status.current.companyId}',
    					name: '<tcmis:jsReplace value="${status.current.companyName}"/>',
    					fac: [{
    						id: '${status.current.facilityId}',
    						name: '<tcmis:jsReplace value="${status.current.facilityName}"/>'
    					}
    					<c:set var="compsep" value="]},"/>
    					<c:set var="revsep" value="]},"/>
    		</c:when>
    		<c:otherwise>
    			<c:choose>
    			<c:when test="${precomp ne curcomp}">
    				${compsep}
	        		{id:   '${status.current.companyId}',
	          		 name: '<tcmis:jsReplace value="${status.current.companyName}"/>',
	          		 fac: [{  id: '${status.current.facilityId}', name: '<tcmis:jsReplace value="${status.current.facilityName}"/>'}
	        	  	 <c:set var="compsep" value="]},"/>
	        	</c:when>
				<c:otherwise>
					,
		    	 	{id:'${status.current.facilityId}', name:'<tcmis:jsReplace value="${status.current.facilityName}"/>'}
				</c:otherwise>
				</c:choose>
    		</c:otherwise>
    		</c:choose>
			<c:set var="precomp" value="${curcomp}"/>
    		<c:set var="pregroup" value="${curgroup}"/>
		</c:forEach>
		<c:if test="${!empty precomp }">]}</c:if>
		<c:if test="${!empty pregroup }">]}</c:if>
	];
   	   
   	function buildDropDown( arr, defaultObj, eleName ) {
	   	var obj = $(eleName);
	   	for (var i = obj.length; i > 0;i--) {
	   		obj[i] = null;
	   	}
	   	// if size = 1 or 2 show last one, first one is all, second one is the only choice.
	   	if( arr == null || arr.length == 0 ) 
	   		setOption(0,defaultObj.name,defaultObj.id, eleName); 
	   	else if( arr.length == 1 )
	   		setOption(0,arr[0].name,arr[0].id, eleName);
	   	else {
	   	    var start = 0;
		   	if( defaultObj.nodefault ) {
		   		start = 0 ; // will do something??
		   	}
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

	function setDropdowns() {
		buildDropDown(reviewerDropdownList, defaults.reviewer, "reviewer");
	 	$('company').onchange = companyChanged;
	    reviewerChanged();
	    companyChanged();
	 	//buildDropDown(assignedUsers,defaults.assignedTo,"assignedTo");
	}

	var currgroup = null;
	var currcomp = null;
	function reviewerChanged() {
		var reviewerO = $("reviewer");
		var compArray = null;
		for(i=0;i< reviewerDropdownList.length;i++) {
			if (reviewerDropdownList[i].id == reviewerO.value) {
				compArray = reviewerDropdownList[i].comp;
				break;
			}
		}
	
		currgroup = compArray;
		buildDropDown(compArray,defaults.company,"company");
	}
	
	function companyChanged() {
		var reviewerO = $("reviewer");
		var companyO = $("company");
		var facArray = null;
		for(i=0;i< reviewerDropdownList.length;i++) {
			if (reviewerDropdownList[i].id == reviewerO.value) {
				for(j=0;j< reviewerDropdownList[i].comp.length;j++) {
	 				if( reviewerDropdownList[i].comp[j].id == companyO.value ) {	
	 	   				facArray = reviewerDropdownList[i].comp[j].fac;
	 	   				break;
	 				}
	 	   		}
				break;
			}
		}
	
		currcomp = facArray;
		buildDropDown(facArray,defaults.facility,"facility");
	}

</script>
</head>
<body bgcolor="#ffffff" onload="loadLayoutWin('','deliveryscheduleview');setDropdowns();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style=""><!-- Search Frame Begins -->
		<div id="searchFrameDiv">
			<div class="contentArea"><tcmis:form action="/deliveryscheduleviewresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
				<!-- Search Option Begins -->
				<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
						<div class="roundcont filterContainer" style="width:100%">
						<div class="roundright">
						<div class="roundtop">
						<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
						</div>
						<div class="roundContent">
						<table width="400" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
							<tr>
								<td width="15%" class="optionTitleBoldRight">
									<fmt:message key="deliveryschedule.label.reviewer" />:
								</td>
								<td width="35%">
									<select name="reviewer" id="reviewer" class="selectBox" onchange="reviewerChanged();">
									</select>
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.company" />:&nbsp;
								</td>
								<td class="optionTitleLeft">
									<select name="company" id="company" class="selectBox">
									</select>
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.facility" />:&nbsp;
								</td>
								<td class="optionTitleLeft">
									<select name="facility" id="facility" class="selectBox">
									</select>
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.search" />:&nbsp;
								</td>
								<td class="optionTitleLeft">
									<input type="text" id="searchTerms" name="searchTerms" />
								</td>
								<td class="optionTitleLeft">
									<input type="checkbox" id="approvedMrs" name="approvedMrs" onclick="return approvedMrSelected();"/>
								</td>
								<td class="optionTitleBoldLeft">
									<fmt:message key="label.approvedmrs" />
								</td>
							</tr>
							<tr class="alignLeft">
								<td width="10%" class="optionTitleLeft" colspan="2">
								<input name="SearchButton" id="SearchButton" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/> 
								<%--<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" 
									onmouseout="this.className='inputBtns'" onclick="return createXSL()" />--%>
                            </td>
							</tr>
						</table>
						</div>
						<div class="roundbottom">
						<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
						</div>
						</div>
						</div>
						</td>
					</tr>
				</table>
				<!-- Search Option Ends -->
				<!-- Hidden element start -->
				<div id="hiddenElements" style="display: none;">
					<input type="hidden" name="uAction" id="uAction" value="" /> 
					<input name="startSearchTime" id="startSearchTime" type="hidden" value="" /> 
					<input name="endSearchTime" id="endSearchTime" type="hidden" value=""/> 
					<input name="searchHeight" id="searchHeight" type="hidden" value="150">
					<input name="approvedMrsOnly" id="approvedMrsOnly" type="hidden" value="N"/>
				</div>
				<!-- Hidden elements end -->
			
			</tcmis:form>
			</div>
			<!-- close of contentArea -->
		</div>
		<!-- Search Frame Ends -->
		
		<!-- Result Frame Begins -->
		<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
			<%-- Transit Page Starts --%>
			<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
				<br>
				<br>
				<br>
				<fmt:message key="label.pleasewait" />
				<br>
				<br>
				<br>
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</div>
	
			<div id="resultGridDiv" style="display: none;">
				<%-- Search results start --%> 
				<%-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. --%>
				<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td valign="top">
							<div class="roundcont contentContainer">
								<div class="roundright">
									<div class="roundtop">
										<div class="roundtopright">
											<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
										</div>
									</div>
									<div class="roundContent">
										<div class="boxhead">
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											     Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											--%>
											<div id="mainUpdateLinks" style="display: none"> 
												
											</div>
										</div>
										<div class="dataContent">
											<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
</body>
</html:html>