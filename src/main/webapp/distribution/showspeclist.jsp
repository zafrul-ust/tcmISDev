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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
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
<script type="text/javascript" src="/js/distribution/speclist.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->


<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<c:choose>
    <c:when test="${param.save == 'Y'}">
     	<fmt:message key="label.specificationlookup"/>
    </c:when>
    <c:otherwise>
     	<fmt:message key="label.addline"/> > <fmt:message key="label.specificationlookup"/>
    </c:otherwise>
   </c:choose>   
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",searchInputInteger:"<fmt:message key="error.searchInput.integer"/>", 
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

function initializePoLineSpecGrid(){
	beangrid = new dhtmlXGridObject('poLineSpecBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(beangrid,poLineSpecConfig,false,false);
	
	if( typeof( jsonPoLineSpecData ) != 'undefined' ) {		
		beangrid.parse(jsonPoLineSpecData,"json");
	}

//	beangrid.attachEvent("onRowSelect",doOnRowSelected);
}


var poLineSpecConfig = [
{ columnId:"specLibraryDesc",
  columnName:'<fmt:message key="label.library"/>',
  width:12
},
{ columnId:"specName",
  columnName:'<fmt:message key="label.specification"/>',
  width:12
},
{ columnId:"detail",
  columnName:'<fmt:message key="label.detail"/>',
  width:10
},
{
  	columnId:"coc",
  	columnName:'<fmt:message key="catalogspec.label.coc"/>',
  	type:'hchstatus',
    align:'center',
    width:3
},
{
  	columnId:"coa",
  	columnName:'<fmt:message key="catalogspec.label.coa"/>',
  	type:'hchstatus',
    align:'center',
    width:3
},
{
  	columnId:"radianPo"
},
{	
  	columnId:"poLine"
},
{	
  	columnId:"specId"
}
];


var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"ok",
  columnName:'   ',
  width:3,
  type:"hchstatus",
  sorting:"haasHch",
  align:"center",
  onChange:callEnableDisable,
  submit:true
},
{ columnId:"specLibraryDesc",
  columnName:'<fmt:message key="label.library"/>',
  width:12
},
{ columnId:"specName",
  columnName:'<fmt:message key="label.specification"/>',
  width:12
},
{ columnId:"specVersion",
  columnName:'<fmt:message key="label.revision"/>',
  align:"left",
  width:5
},
{ columnId:"specAmendment",
  columnName:'<fmt:message key="label.amendment"/>',
  align:"left",
  width:6
},
{ columnId:"detail",
  columnName:'<fmt:message key="label.detail"/>',
  width:10
},
{
  	columnId:"coc",
  	columnName:'<fmt:message key="catalogspec.label.coc"/>',
  	type:'hchstatus',
    align:'center',
    width:3
},
{
  	columnId:"coa",
  	columnName:'<fmt:message key="catalogspec.label.coa"/>',
  	type:'hchstatus',
    align:'center',
    width:3
},
{
  	columnId:"itemId"
},
{	
  	columnId:"specLibrary"
},
{	
  	columnId:"specId"
}
];


// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','speclist');initializePoLineSpecGrid();resultOnLoad();setTimeout('resizeFrames(178)',88);" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames(178);">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/speclist.do" onsubmit="" target="resultFrame">
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
    <div class="optionTitleBoldLeft"><fmt:message key="label.specrequirements"/>:</div>
    <table width="200" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleLeft" colspan="4" nowrap>					
			<div id="poLineSpecBean" style="height:100px;width:600px;" style="display: ;"></div>
<c:if test="${poLineSpecColl != null}">
<script type="text/javascript">
<!--
<c:set var="poLineSpecDataCount" value='${0}'/>
var jsonPoLineSpecData = new Array();
var jsonPoLineSpecData = { 
rows:[    
<c:forEach var="poLineSpecBean" items="${poLineSpecColl}" varStatus="status">
<%-- <c:if test="${specBean.specId != 'No Specification'}" > --%>
<c:if test="${poLineSpecDataCount > 0}">,</c:if>
<c:set var="coc" value="false"/>
<c:set var="coa" value="false"/>

<tcmis:jsReplace var="specName" value="${poLineSpecBean.specName}"  processMultiLines="false"/>
<tcmis:jsReplace var="specId" value="${poLineSpecBean.specId}"  processMultiLines="true"/>
<tcmis:jsReplace var="detail" value="${poLineSpecBean.detail}"  processMultiLines="true"/>
<tcmis:jsReplace var="specLibraryDesc" value="${poLineSpecBean.specLibraryDesc}"  processMultiLines="true"/>
<c:if test="${poLineSpecBean.coc == 'Y'}"><c:set var="coc" value="true"/></c:if>
<c:if test="${poLineSpecBean.coa == 'Y'}"><c:set var="coa" value="true"/></c:if>

 /*The row ID needs to start with 1 per their design.*/
{ id:${poLineSpecDataCount +1},
 data:[ 
'${specLibraryDesc}','${specId}','${detail}',${coc},${coa},
'${poLineSpecBean.radianPo}','${poLineSpecBean.poLine}','${specId}' ]}
 <c:set var="poLineSpecDataCount" value='${poLineSpecDataCount+1}'/>
<%-- </c:if>  --%>
 </c:forEach>
]};
//-->
</script>
</c:if>
			
		</td>
		<td width="45%" class="optionTitleLeft" nowrap>&nbsp;</td>
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
<div class="spacerY" style="display:none;">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->
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


<div id="resultGridDiv" style="display: ;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="0" frameborder="0" marginwidth="0" src="/blank.html"><input type="hidden" name="minHeight" id="minHeight" value="100"/></iframe> 
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop"> 
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: " /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
      --%>      
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
       <span id="updateResultLink">
       	<a href="#" onclick="getSpecList();"><fmt:message key="label.returnselected"/></a> 
       	<span id="addSpecSpan">| <a href="#" onclick="addNewSpec();"><fmt:message key="addspecs.title"/></a></span></span>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="catalogItemSpecListBean" style="height:400px;" style="display: none;"></div>

<c:if test="${specListColl != null}">
<script type="text/javascript">
<!--
<c:set var="noSpecifictionChecked" value='N'/>

<c:set var="dataCount" value='${0}'/>
var jsonMainData = new Array();
var jsonMainData = { 
rows:[    
<c:forEach var="specBean" items="${specListColl}" varStatus="status">
<%-- <c:if test="${specBean.specId != 'No Specification'}" > --%>
<c:if test="${dataCount > 0}">,</c:if>
<c:set var="coc" value="false"/>
<c:set var="coa" value="false"/>
<c:set var="okVal" value="false"/>

<tcmis:jsReplace var="specName" value="${specBean.specName}"  processMultiLines="false"/>
<tcmis:jsReplace var="specId" value="${specBean.specId}"  processMultiLines="true"/>
<tcmis:jsReplace var="detail" value="${specBean.detail}"  processMultiLines="true"/>
<tcmis:jsReplace var="specLibraryDesc" value="${specBean.specLibraryDesc}"  processMultiLines="true"/>
<tcmis:jsReplace var="specLibrary" value="${specBean.specLibrary}"  processMultiLines="false"/>
<c:if test="${specBean.coc == 'Y' || (param.save != 'Y' && specId == 'Std Mfg Cert')}"><c:set var="coc" value="true"/></c:if>
<c:if test="${specBean.coa == 'Y'}"><c:set var="coa" value="true"/></c:if>

<c:if test="${(specBean.coa == 'Y') or (specBean.coc == 'Y') }"><c:set var="okVal" value="true"/></c:if>
<c:if test="${param.save != 'Y' && specId == 'Std Mfg Cert'}"><c:set var="okVal" value="true"/></c:if>

<c:if test="${specBean.specForPart == 'Y' && specId == 'No Specification'}">
 <c:set var="okVal" value="true"/>
 <c:set var="noSpecifictionChecked" value='Y'/>
</c:if>


 /*The row ID needs to start with 1 per their design.*/
{ id:${dataCount +1},
 data:[ 
<%--  
<c:choose>
  <c:when test="${specBean.specId == 'No Specification'}" >
    'N',
  </c:when>
  <c:otherwise>
    'Y',
  </c:otherwise>
</c:choose>    --%>
'Y',${okVal},'${specLibraryDesc}','${specName}','${specBean.specVersion}','${specBean.specAmendment}',  
'${detail}',${coc},${coa},'${status.current.itemId}','${specLibrary}','${specId}' ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
<%-- </c:if>  --%>
 </c:forEach>
]};
//-->
</script>
</c:if>

<c:if test="${noSpecifictionChecked == 'Y'}">
    <script type="text/javascript">
        <!--
        noSpecifictionChecked = true;
        //-->
    </script>
</c:if>

<!-- If the collection is empty say no data found -->

<c:if test="${empty specListColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
</c:if> 
 <!-- Search results end --> 


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

  	  <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: " /> </div>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="itemId" id="itemId" value="${param.itemId}"/>
<input type="hidden" name="catPartNo" id="catPartNo" value="${param.catPartNo}"/>
<input type="hidden" name="radianPo" id="radianPo" value="${param.radianPo}"/>
<input type="hidden" name="poLine" id="poLine" value="${param.poLine}"/>

<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="${param.uAction}" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="save" id="save" type="hidden" value="${param.save}"/>
<input name="specListConcat" id="specListConcat" type="text" value="${param.specListConcat}"/>
<input name="specVersionConcat" id="specVersionConcat" type="text" value="${param.specVersionConcat}"/>
<input name="specAmendmentConcat" id="specAmendmentConcat" type="text" value="${param.specAmendmentConcat}"/>
<input name="specDetailConcat" id="specDetailConcat" type="text" value="${param.specDetailConcat}"/>
<input name="specLibraryDescConcat" id="specLibraryDescConcat" type="text" value="${param.specLibraryDescConcat}"/>
<input name="specLibraryConcat" id="specLibraryConcat" type="text" value="${param.specLibraryConcat}"/>
<input name="specCocConcat" id="specCocConcat" type="text" value="${param.specCocConcat}"/>
<input name="specCoaConcat" id="specCoaConcat" type="text" value="${param.specCoaConcat}"/>

<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
</div>
<!-- Hidden elements end -->

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.statusisinactiveorcreditstatusstop"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</tcmis:form>
</body>
</html:html>