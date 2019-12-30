<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<script type="text/javascript" src="/js/catalog/catalogaddqcmain.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
<fmt:message key="label.catalogaddqc"/>
</title>
<script language="JavaScript" type="text/javascript"> 
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData= { 
			alert:"<fmt:message key="label.alert"/>",
			and:"<fmt:message key="label.and"/>",
			validvalues:"<fmt:message key="label.validvalues"/>",
			submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
			companyId:"<fmt:message key="label.companyid"/>",
			showlegend:"<fmt:message key="label.showlegend"/>"
			};


	var defaults = {
        companyId: {id:'ALL',name:'<fmt:message key="label.all"/>'},
        catalog: {id:'',name:'<fmt:message key="label.all"/>'},
        assignedTo: {id:'',name:'<fmt:message key="label.all"/>'},
        supplier: {id:'',name:'<fmt:message key="label.all"/>'},
        taskStatus: {id:'Open,Assigned',name:'<fmt:message key="label.openassigned"/>'}
	}

	<c:set var="precomp" value=""/>
	<c:set var="compsep" value=""/>

	var compcat = [
	    	<c:forEach var="companyCatalog" items="${catalogCol}" varStatus="status">
	    		<c:set var="curcomp" value="${companyCatalog.companyId}"/>
	    		<c:choose>
		    		<c:when test="${ precomp ne curcomp}">
			    		${compsep}
		        		{id:   '${companyCatalog.companyId}',
		          		 name: '<tcmis:jsReplace value="${companyCatalog.companyName}"/>',
		          		 coll: [{  id: '${companyCatalog.catalogId}', name: '<tcmis:jsReplace value="${companyCatalog.catalogDesc}"/>'}
		        	  	 <c:set var="compsep" value="]},"/>
	    			</c:when>	        
				<c:otherwise>
		    	    		,
		    	    	 	{id:'${companyCatalog.catalogId}', name:'<tcmis:jsReplace value="${companyCatalog.catalogDesc}"/>'}
	    			</c:otherwise>
    			</c:choose>	        
    			<c:set var="precomp" value="${curcomp}"/>
	    	</c:forEach>
	    	<c:if test="${!empty precomp }">]}</c:if>
	   ]; 

	var assignedUsers = [
			{id: '-1', name: 'Unassigned'}<c:if test="${not empty catalogUsers}">,</c:if>
	     	    	<c:forEach var="catalogUser" items="${catalogUsers}" varStatus="status">
	     	    		{id: '${catalogUser.personnelId}', name: '${catalogUser.personnelName}'}<c:if test="${!status.last}">,</c:if>
	     	    	</c:forEach>
	             	];

    var supplierData = [
        <c:forEach var="bean" items="${catalogVendors}" varStatus="status">
            {
                id: '${bean.supplier}',
                name: '${bean.supplierName}'
            }
            <c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];
    
    var taskStatusData = [
    	<c:forEach var="bean" items="${taskStatuses}" varStatus="status">
    		{
    			"id": '${bean.status}',
    			"name": '${bean.status}'
    		}
    		<c:if test="${!status.last}">,</c:if>
    	</c:forEach>
		,{"id": "", "name":'<fmt:message key="label.all"/>'},
    ];

function buildDropDown( arr, defaultObj, eleName ) {
	 var obj = $(eleName);
	 for (var i = obj.length; i > 0;i--) {
	   obj[i] = null;
	 }
	// if size = 1 or 2 show last one, first one is all, second one is the only choice.
	if( arr == null || arr.length == 0 ) 
	 setOption(0,defaultObj.name,defaultObj.id, eleName); 
	else if( arr.length == 1) {
	    var startIndex = 0;
	    if(eleName == "supplier")
	        setOption(startIndex++,defaultObj.name,defaultObj.id, eleName);
	    setOption(startIndex,arr[0].name,arr[0].id, eleName);
	}else {
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
 	buildDropDown(compcat,defaults.companyId,"companyId");
 	$('companyId').onchange = companyChanged;
    	companyChanged();
 	buildDropDown(assignedUsers,defaults.assignedTo,"assignedTo");
 	buildDropDown(supplierData,defaults.supplier,"supplier");
 	buildDropDown(taskStatusData,defaults.taskStatus,"taskStatus");
}

var currcat = null;
function companyChanged() {
	   var companyO = $("companyId");
	   var arr = null;
	   if( companyO.value != '' ) {
	   	   for(i=0;i< compcat.length;i++)
	   	   		if( compcat[i].id == companyO.value ) {
	   	   			arr = compcat[i].coll;
	   	   			break;
	   	   		}
	   }
	   currcat = arr;
	   buildDropDown(arr,defaults.catalog,"catalogId");
}


var searchMyArr = new Array(
		{value:'contains', text: '<fmt:message key="label.contain"/>'}
		,{value:'is', text: '<fmt:message key="label.is"/>'}
	);


// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','catalogAddProcess');setDropdowns();statusChanged();" onresize="resizeFrames()">
	<div class="interface" id="mainPage" style=""><!-- Search Frame Begins -->
		<div id="searchFrameDiv"><%--NEW - removed the search frame and copied the search section here--%>
			<div class="contentArea"><tcmis:form action="/catalogaddqcresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
				<!-- Search Option Begins -->
				<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
						<div class="roundcont filterContainer">
						<div class="roundright">
						<div class="roundtop">
						<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
						</div>
						<div class="roundContent">
						<table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
							<tr>
								<td width="15%" class="optionTitleBoldRight">
									<fmt:message key="label.company" />:
								</td>
								<td width="35%">
									<select name="companyId" id="companyId" class="selectBox">
									</select>
								</td>
								<td width="15%" class="optionTitleBoldRight">
									<fmt:message key="label.status" />:&nbsp;
								</td>
								<td width="35%" class="optionTitleLeft">
									<html:select property="status" styleId="status" styleClass="selectBox" onchange="statusChanged(this.value);">
										<html:option value="Pending Assignment">
											<fmt:message key="label.pendingassignment" />
										</html:option>
                                        <html:option value="Pending SDS Sourcing">
											<fmt:message key="label.pendingsdssourcing" />
										</html:option>
                                        <html:option value="Pending SDS Indexing">
											<fmt:message key="label.pendingsdsindexing" />
										</html:option>
                                        <html:option value="Pending SDS Custom Indexing">
											<fmt:message key="label.pendingsdscustomindexing" />
										</html:option>
                                        <html:option value="Pending SDS QC">
											<fmt:message key="label.pendingsdsqc" />
										</html:option>
                                        <html:option value="Pending Item Creation">
											<fmt:message key="label.pendingitemcreation" />
										</html:option>
                                        <html:option value="Pending Spec">
											<fmt:message key="label.pendingspec" />
										</html:option>
										<html:option value="All Statuses">
                                            <fmt:message key="label.allstatuses" />
                                        </html:option>
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.catalog" />:&nbsp;
								</td>
								<td class="optionTitleLeft">
									<select name="catalogId" id="catalogId" class="selectBox">
									</select>
								</td>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.taskstatus" />:
								</td>
								<td class="optionTitleLeft">
									<html:select property="taskStatus" styleId="taskStatus" styleClass="selectBox">
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.assignedto" />:
								</td>
								<td class="optionTitleLeft">
									<select name="assignedTo" id="assignedTo" class="selectBox">
									</select>
								</td>

                                <td class="optionTitleBoldRight">
                                    <fmt:message key="label.vendor" />:
                                </td>
                                <td class="optionTitleLeft">
                                    <select name="supplier" id="supplier" class="selectBox">
                                    </select>
                                </td>

                                <%--
                                <c:if test="${fn:indexOf(pageContext.request.requestURL,'https://cn') == 0}">
                                    <input type="hidden" name="alternateDb" id="alternateDb" value="AHC" />
                                </c:if>
                                <c:if test="${fn:indexOf(pageContext.request.requestURL,'https://cn') < 0}">
                                    <td class="optionTitleBoldRight">
                                        <fmt:message key="label.system" />:
                                    </td>
                                    <td width="35%" class="optionTitleLeft">
                                        <html:select property="alternateDb" styleId="alternateDb" styleClass="selectBox">
                                            <html:option value="">
                                                <fmt:message key="label.haasgroupintl" />
                                            </html:option>
                                            <html:option value="AHC">
                                                <fmt:message key="label.avichaas" />
                                            </html:option>
                                        </html:select>
                                    </td>
                                </c:if>
                                --%>
                            </tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.search" />:&nbsp;
								</td>
								<td colspan="3" class="optionTitleLeft">
									<select name="searchField" class="selectBox" onchange="changeSearchTypeOptions(this.value)" id="searchField">
										<option value="requestId"><fmt:message key="label.requestid" /></option>
										<option value="manufacturer"><fmt:message key="label.manufacturer" /></option>
										<option value="material_desc"><fmt:message key="label.materialdesc" /></option>
									</select>
									<select name="searchMode" class="selectBox" id="searchMode">
										<option value="is"><fmt:message key="label.is" /></option>
									</select>
									<input class="inputBox" type="text" name="searchArgument" id="searchArgument" value='<c:out value="${param.searchArgument}"/>' size="25" />
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight">
									<fmt:message key="label.sort" />:
								</td>
								<td class="optionTitleLeft" colspan="3">
									<html:select property="sortBy" styleId="sortBy" styleClass="selectBox">
										<html:option value="1">
											<fmt:message key="label.companycatalogidsubmittedqcdate" />
										</html:option>
										<html:option value="2">
											<fmt:message key="label.submittedqcdatecompanycatalogid" />
										</html:option>
									</html:select>
								</td>
							</tr>
							<tr class="alignLeft">
								<td width="10%" class="optionTitleLeft" colspan="4">
								<input name="SearchButton" id="SearchButton" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
									onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();"/> 
								<input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" 
									onmouseout="this.className='inputBtns'" onclick="return createXSL()" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input name="excludeMerckRequest" id="excludeMerckRequest" type="checkbox" class="radioBtns" value="Y"/>
                                <label for="excludeMerckRequest"><fmt:message key="label.excludemerckrequests"/></label>
                                <input name="historicalSearch" id="historicalSearch" type="checkbox" class="radioBtns" value="Y"/>
                                <label for="historicalSearch"><fmt:message key="label.historicalsearch"/></label>
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
					<input name="maxData" id="maxData" type="hidden" value="1000"/>
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
											<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
											     Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
											--%>
											<div id="mainUpdateLinks" style="display: none"> 
												<span id="updateResultLink"><a href="#" onclick="resultFrame.update();"><fmt:message key="label.update"/></a> |</span>
												<%-- creating this id so that catalogaddvendorqueueresults.js can access this variable without throwing js error --%>
												<span id="qcChecked" style="display: none"><a href="#" onclick="resultFrame.update();"><fmt:message key="label.approve"/></a> |</span>
												<span id="savePendingAssign" style="display: none"><a href="#"><fmt:message key="label.update"/></a> |</span>
												<span id="approvePendingAssign" style="display: none"><a href="#"><fmt:message key="label.approve"/></a> |</span>
												<span id="approveAndRetain" style="display: none"><a href="#"><fmt:message key="label.approvewithoutvendor"/></a> |</span>
												<span id="rejectRequest" style="display: none"><a href="#"><fmt:message key="label.reject"/></a> |</span>
												<span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
											</div>
										</div>
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
	<div id="errorMessagesArea" class="errorMessages" style="display: none; overflow: auto;">
	</div>
	
	<%-- show legend Begins --%>
	<div id="showLegendArea" style="display: none;overflow: auto;">
		<table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px" class="red legendText">&nbsp;</td>
				<td class="legendText"><fmt:message key="label.overdue"/></td>
			</tr>
		</table>
	</div>
	<%-- show legend Ends --%>


</body>
</html>
      		