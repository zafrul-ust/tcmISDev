<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

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

<title>
Multi Select
</title>

<script language="JavaScript" type="text/javascript">
<!--
var resizeGridWithWindow = true;
/*This will close the pop-up if the esc key is pressed*/
windowCloseOnEsc = true;
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
open:"<fmt:message key="label.open"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
noRowSelected:"<fmt:message key="error.norowselected"/>"
};


var gridConfig = {
		divName:'areaMultiSelectBean', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:'false',			 // this page has rowSpan > 1 or not.
		submitDefault:'false',    // the fields in grid are defaulted to be submitted or not.
	    singleClickEdit:true,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		noSmartRender:true// If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
		};

var config = [
{
  columnId:"permission"
}, 
{columnId:"okPermission"},
{
  columnId:"ok",
  columnName:'<fmt:message key="label.select"/>',
  width:3,
  type:'hchstatus',
  align:'center'
},
{ 
  columnId:"desc",
  <c:choose>
	<c:when test = "${param.dropDownId == 'deptId'}">
	  columnName:'<fmt:message key="label.department"/>',	
	</c:when>
	<c:when test = "${param.dropDownId == 'buildingId'}">
	  columnName:'<fmt:message key="label.building"/>',	
	</c:when>
	<c:when test = "${param.dropDownId == 'countTypeArray'}">
		  columnName:'<fmt:message key="label.counttype"/>',	
	</c:when>
	<c:when test = "${param.dropDownId == 'applicationId' || param.dropDownId == 'application'}">
		  columnName:'<fmt:message key="label.workarea"/>',	
	</c:when>
	<c:when test = "${param.dropDownId == 'materialSubcategoryId'}">
		  columnName:'<fmt:message key="label.materialsubcategory"/>',	
	</c:when>
	<c:when test = "${param.dropDownId == 'materialCategoryId'}">
			  columnName:'<fmt:message key="label.materialcategory"/>',	
	</c:when>
    <c:when test = "${param.dropDownId == 'flammabilityControlZone'}">
             columnName:'<fmt:message key="label.flammabilitycontrolzone"/>', 
    </c:when>
    <c:when test = "${param.dropDownId == 'vocZone'}">
             columnName:'<fmt:message key="label.voczone"/>', 
    </c:when>
    <c:when test = "${param.dropDownId == 'emissionPoints'}">
             columnName:'<fmt:message key="label.emissionpoint"/>', 
    </c:when>
    <c:when test = "${param.dropDownId == 'facilityGroupId'}">
    columnName:'<fmt:message key="label.facilitygroup"/>',
    </c:when>
    <c:when test = "${param.dropDownId == 'facilityId'}">
    columnName:'<fmt:message key="label.facility"/>',
    </c:when>
	<c:otherwise>
	  columnName:'<fmt:message key="label.area"/>',
	</c:otherwise>
  </c:choose>
  tooltip:true,
  width:15,
  sorting:'na'
}, 
{
  columnId:"id"
}
<c:if test="${param.dropDownId == 'materialSubcategoryId'}">
,
{
	columnName:'<fmt:message key="label.materialcategory"/>',	
	columnId:"materialCategory",
	tooltip:true,
	width:15,
	sorting:'na'
}
</c:if>
];

function returnSelect(close)
{
	renderAllRows();
	var rowsNum = beangrid.getRowsNum();
	var sendSelect = new Array();
	window.opener.clearElements(which);
	for ( var row = 0; row < rowsNum; row++) {
		if($('ok'+row).checked)
		{
			sendSelect.push(cellValue(row,'desc')+'#^%&*!$@'+cellValue(row,'id'));
		    window.opener.createElement(which,cellValue(row,'id'));
		}
	}
	window.opener.multiSelRet(sendSelect, which);
	if(close)
		window.close();
}

function clearAll()
{
	var checkboxname = 'ok';
	var rowsNum = beangrid.getRowsNum(); // Get the total rows of the grid
	var columnId = beangrid.getColIndexById(checkboxname);
	for ( var rowId = 0; rowId < rowsNum; rowId++) {
			var curCheckBox = checkboxname + rowId;
			if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
				$(curCheckBox).checked = false;
				updateHchStatusA(curCheckBox);
			}
			else { // The HTML element hasn't been drawn yet, update the JSON data directly
				 beangrid._haas_json_data.rows[beangrid.getRowIndex(rowId)].data[columnId] = false;
			}
		}
}

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="popupOnLoadWithGrid(gridConfig);" onresize="setTimeout('resizeFrames();',55)" onunload="opener.closeTransitWin()">
<tcmis:form action="/multiselectresults.do" onsubmit="return submitFrameOnlyOnce();">

 <script type="text/javascript">
 <!--
showUpdateLinks = true;
 //-->
 </script>
   
 <!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->  

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

<div class="interface" id="resultsPage">
<div class="backGroundContent"> <!-- start of backGroundContent -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">

<!-- results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
          <a href="#" onclick="returnSelect(true);return false;"><fmt:message key="label.returnselectedclose"/></a>&nbsp;|&nbsp;
          <a href="#" onclick="clearAll();return false;"><fmt:message key="label.clear"/></a>&nbsp;|&nbsp;
          <a href="#" onclick="window.close();return false;"><fmt:message key="label.cancel"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
    
    <div id="hiddenElements" style="display: none;">
	<input name="totalLines" id="totalLines" value="" type="hidden"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>
	<!--This sets the start time for time elapesed from the action.-->
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
	<!--This sets the end time for time elapesed from the action.-->
	<input name="endSearchTime" id="endSearchTime" type="hidden" value="">
	</div>

<div id="areaMultiSelectBean" style="width:100%;height:500px;" style="display: none"></div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable" style="display:none">
							<tr>
								<td width="100%">
									<fmt:message key="main.nodatafound"/>
								</td>
							</tr>
			</table>
<script type="text/javascript">
<!--
var which = '${param.dropDownId}';
var options = window.opener.document.getElementById('${param.dropDownId}' + 'Sel').options;
<c:if test="${param.dropDownId == 'materialSubcategoryId'}">
var materialCategoryArr = window.opener.document.getElementById('materialSubcategoryIdSel');
</c:if>
//var preSelectVals = window.opener.document.getElementById('${param.dropDownId}' + 'MultiSelTxt').value;
var sa = opener.selectedApplication;

if(options.length == 1 && options[0].text == 'All') {
	$('resultsPageTable').style.display = '';
	$('totalLines').value = 0;
}else {
	var jsonMainData = new Array();
	jsonMainData.rows = new Array();
	if((options[0].text == 'All' || options[0].value == 'All' || options[0].value == '') && options.length > 1)
		$('totalLines').value = options.length - 1;
	else
		$('totalLines').value = options.length;
	var addedCount = 0;
    <c:if test="${param.dropDownId == 'materialSubcategoryId'}">
		matCatArrIndex = 1;
		if(materialCategoryArr.options[0].text != 'All')
			matCatArrIndex = 0;
	</c:if>
	for(var i = 0; i < options.length; i++)	{
		if(options[i].text != 'All' && options[i].text != '' && options[i].value != 'All' && options[i].value != '') {
            var isSelected = false;
            //this is assuming the use of clearElements() and createElement() methods while handling dropdown selection
            var isSelectedEle = window.opener.document.getElementById(which + options[i].value);
            if(isSelectedEle != null)
                isSelected = true;
            jsonMainData.rows[addedCount] =
                {id:addedCount++,
                 data:['Y',
                       'Y',
                       isSelected,
                       options[i].text,
                       options[i].value
                       <c:if test="${param.dropDownId == 'materialSubcategoryId'}">
                       ,
                       materialCategoryArr.options[matCatArrIndex++].text
                       </c:if>
                      ]
                };
		}
	}
}

 //-->
</script>

        
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
   </div>

</div>
</div>

<div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
    
  </td>
</tr>
</table>
<!-- results end -->



</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</tcmis:form>
</body>
</html:html>