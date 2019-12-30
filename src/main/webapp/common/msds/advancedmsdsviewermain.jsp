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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>


<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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
<script type="text/javascript" src="/js/common/msds/advancedmsdsviewermain.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	

<!-- These are for the Grid-->
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<%-- Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script src="/js/report/adhocsearchmsdstemplate.js" language="JavaScript"></script>

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />

<script type='text/javascript' src='/js/jquery/ajaxQueue.js'></script>

<title>
<fmt:message key="title.msdsviewer"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    or:"<fmt:message key="label.or"/>",
    all:"<fmt:message key="label.all"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>",
    errors:"<fmt:message key="label.errors"/>",
    pleasewait:"<fmt:message key="label.pleasewait"/>",
    searchText:"<fmt:message key="label.searchtext"/>",
    ph:"<fmt:message key="label.ph"/>",
    flashpoint:"<fmt:message key="label.flashpoint"/>",
    vaporpressure:"<fmt:message key="label.vaporpressure"/>",
    voc:"<fmt:message key="label.voc"/>",
    solids:"<fmt:message key="label.solids"/>",
    nfpa:"<fmt:message key="label.nfpa"/>",
    hmis:"<fmt:message key="label.hmis"/>",
    health:"<fmt:message key="label.health"/>",
    flammability:"<fmt:message key="label.flammability"/>",
    reactivity:"<fmt:message key="label.reactivity"/>",
    list:"<fmt:message key="label.list"/>",
    casnumber:"<fmt:message key="label.casnumber"/>",
    max:"<fmt:message key="label.max"/>",
    min:"<fmt:message key="label.min"/>",
    average:"<fmt:message key="label.average"/>",
    searchInput:"<fmt:message key="error.searchInput.integer"/>",
    searchTextRequired:"<fmt:message key="label.searchtextorfacility"/>",
    searchTextOnly:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
    listLimit:"<fmt:message key="error.listlimit"/>"
};

var defaults = {
	   fac: {id:'',name:'<fmt:message key="label.all"/>'},
   	   app: {id:'',name:'<fmt:message key="label.all"/>'}
}

<c:set var="currentFacilityId" value=''/>
var facApplicationArr = [
	<c:forEach var="facBean" items="${facilityWorkareaColl}" varStatus="status">
	  <c:if test="${currentFacilityId != facBean.facilityId && currentFacilityId != ''}">]},</c:if>
	  <c:if test="${currentFacilityId != facBean.facilityId}">
	  { 	  id: '${facBean.facilityId}',
		  name: '<tcmis:jsReplace value="${facBean.facilityName}"/>',
		  coll: [{
		  	  id: '${facBean.application}',
		  	  name: '<tcmis:jsReplace value="${facBean.applicationDesc}"/>'}
	  </c:if>
	  <c:if test="${currentFacilityId == facBean.facilityId}">
	  ,{	  	  id: '${facBean.application}',
		  	  name: '<tcmis:jsReplace value="${facBean.applicationDesc}"/>'}
	  </c:if>

	<c:set var="currentFacilityId" value='${facBean.facilityId}'/>
	</c:forEach>]}];

j$().ready(function() {

	j$("#manufacturer").autocomplete("manufacturersearchmain.do?loginNeeded=N&uAction=autoCompleteSearch",{
			width: 528,
			max: 60,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 150,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[0]+" : "+value.split(":")[1].substring(0,240);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});

	j$('#manufacturer').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateManufacturerInput();
	}));

	j$("#manufacturer").result(mfgLog).next().click(function() {
		j$(this).prev().search();
	});

	function mfgLog(event, data, formatted) {
		$("manufacturer").className = "inputBox";
		$('mfgId').value = formatted.split(":")[0];
	}

});

function invalidateManufacturerInput()
{
 if ($v("manufacturer").length == 0) {
   $("manufacturer").className = "inputBox";
 }else {
   $("manufacturer").className = "inputBox red";
//   $("manufacturer").className = "inputBox red";
 }
 //set to empty
 $('mfgId').value = "";
}

	var listOptionArray = [
    <c:forEach var="listBean" items="${listColl}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${status.current.listId}',
          name: '<tcmis:jsReplace value="${status.current.listName}"/>'
        }
    </c:forEach>
    ];

j$().ready(function() {
	function log(event, data, formatted) {
		if( typeof(data) == 'undefined') return false;
		j$('#searchListId').val(data.id);
		$("listDesc").className = "inputBox";

		var foundIndex = -1;
		var list = $("list");
		var listCount = 0;
		for (j=0;j<list.length;j++) {
		   if(list.options[j].selected)
			   ++listCount;
           if(data.id == list.options[j].value){
        	   foundIndex = j;
     //         move(chemicalFieldList,true);
           }
        }

        if(listCount > 18)
            alert(messagesData.listLimit);
        else if(foundIndex != -1)
    		list.options[foundIndex].selected = true;

	}

	j$("#listDesc").autocomplete(listOptionArray, {
		minChars: 1,
		width: 700,
		max: 300,
		matchContains:true,
		cacheLength:0,
		//matchContains: "word",
		autoFill: false,
		formatItem: function(row, i, max) {
			return row.name;
		},
		formatMatch: function(row, i, max) {
			return row.id + "||"+row.name;
		},
		formatResult: function(row) {
			return row.name;
		}

	});

	j$('#listDesc').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateList();
	}));

	j$("#listDesc").result(log).next().click(function() {
		j$(this).prev().search();
	});
});

function invalidateList()
{
 var listDesc  =  document.getElementById("listDesc");
 var listId  =  document.getElementById("searchListId");
 if (listDesc.value.length == 0) {
   listDesc.className = "inputBox";
 }else {
   listDesc.className = "inputBox red";
 }
 //set to empty
 listId.value = "";
}

function clearFilterList() {
	$("searchListId").value = "";
	$("listDesc").value = "";
	$("listDesc").className = "inputBox";
}

function showLegend() {
	children[children.length] = openWinGeneric("approvedworkareaslegend.do?facilityId="+$v('facilityId'),"shownewlist","570","360","yes","80","80");
}
// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout.
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff" onkeydown="detectShiftDown()" onKeyUp="detectShiftUp()"  onload="setFacAppDropdowns();loadTemplate();" onunload ="closeAllchildren();" >

<div class="interface" id="mainPage" style="">

<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/advancedmsdsviewerresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="1400" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                     <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
            <td width="1%" class="optionTitleBoldRight"nowrap>
                <fmt:message key="label.facility"/>:
            </td>
            <td width="1%">
                <c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
                <select name="facilityId" id="facilityId" class="selectBox" >
                </select>
            </td>
        </tr>
        <tr>
            <td width="1%" class="optionTitleBoldRight" nowrap>&nbsp;
                <fmt:message key="label.workarea"/>:
            </td>

            <td class="optionTitleBoldLeft">
			                <select name="application" id="application" multiple="multiple" size="3" onchange="workAreaChanged();" class="selectBox" >
			                </select>
            </td>
            <td class="optionTitleBoldLeft" nowrap >
			    <input name="approvedOnlyDisplay" id="approvedOnlyDisplay" type="checkbox" class="radioBtns" value="Y" >
			    <fmt:message key="label.approved"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <input name="approvedOnly" id="approvedOnly" type="hidden" value="" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <input name="stocked" id="stocked" type="checkbox" class="radioBtns" value="Y" >
			    <fmt:message key="label.stocked"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<input name="fullDatabase" id="fullDatabase" type="checkbox" class="radioBtns" value="Y" >&nbsp;
                <fmt:message key="label.fulldatabase"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<input name="kitOnly" id="kitOnly" type="checkbox" class="radioBtns" value="Y" >&nbsp;
            	<fmt:message key="label.kitmsds"/>
            </td>

        </tr>
    </table>

			<%@ include file="/report/adhocsearchmsdstemplate.jsp" %>

<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
 	<tr>
         <td colspan="7"  class="optionTitleBoldLeft">   
            <!-- search button -->
            &nbsp;
            <input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()">
            <input name="createExcel" id="createExcel" type="button"
				   value="<fmt:message key="label.createexcelfile"/>" onclick="generateExcel();" class="inputBtns"
				   onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
			<input name="clearallfields" id="clearallfields" type="button"
				   value="<fmt:message key="label.clearallfields"/>" onclick="clearAll();" class="inputBtns"
				   onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
				   
		  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	*<fmt:message key="label.listupdatepending"/>
          </td>
        </tr>
    </table>

   <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none"/></div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
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
<div id="hiddenElements" style="display: none;">
    <input name="uAction" id="uAction" type="hidden" value="">
    <input name="pageId" id="pageId" type="hidden" value="advMsds">
    <input name="hideOrShowDiv" id="hideOrShowDiv" type="hidden" value="show">
    <input name="selectedCasId" id="selectedCasId" type="hidden" value=""> 
    <input name="casNumberAndOrString" id="casNumberAndOrString" type="hidden" value="">
    <input name="casNumberString" id="casNumberString" type="hidden" value="">
    <input name="applicationList" id="applicationList" type="hidden" value="">
    <input name="oldApplicationList" id="oldApplicationList" type="hidden" value="">
    <input name="defaultFacilityId" id="defaultFacilityId" type="hidden" value="${personnelBean.myDefaultFacilityId}">
    <input type="hidden" name="facilityName" id="facilityName" value=""/>
    <input type="hidden" name="applicationDesc" id="applicationDesc" value=""/>
    

</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

</div> <!-- close of interface -->
    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>
<%--
<div id="hiddenDesc" style="visibility: hidden;position: absolute;z-index: 2;font:8pt sans-serif;border: solid 1px grey;background-color:white;">
<TEXTAREA id="msdsNos" name="msdsNos" rows="6" cols="30" style="background-color: #ffffff;border: 1px solid #cccccc;"></TEXTAREA><br/><br/>
&nbsp;&nbsp;
<select name="matchType" id="matchType" class="selectBox">
    <option value="all"><fmt:message key="label.matchall"/></option>
    <option value="any" selected ><fmt:message key="label.matchany"/></option>
</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="okInput" id="okInput" type="button" value="<fmt:message key="label.ok"/>" onclick="okInput();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="#" onclick="hideTooltip('hiddenDesc');"><fmt:message key="label.cancel" /></a>
</div> 
 --%>
</body>
</html:html>