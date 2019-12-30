<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:html lang="true">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

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

<!-- Add any other javascript you need for the page here 
<script type="text/javascript" src="/js/distribution/partsearch.js"></script>
-->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
</title>

<script type="text/javascript">
    <!--
   	 showUpdateLinks = false;
    //-->
</script>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
selectedpartnumber:"<fmt:message key="label.addcatalogitem"/>", 
newcontact:"<fmt:message key="label.newcontact"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
specgroup:"<fmt:message key="label.specgroup"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{ columnId:"permission"
},
{ columnId:"alternateName",
  columnName:'<fmt:message key="label.catalogitem"/>',
  sorting:"haasStr",
  tooltip:true,
  width:7
},
{ columnId:"description",
  columnName:'<fmt:message key="label.description"/>',
  sorting:"haasStr",
  tooltip:true,
  width:40
},
{
    columnId:"specListId"
//    ,columnName:'<fmt:message key="label.specgroup"/>',
//    tooltip:true,
//    width:6
},
{
  	columnId:"specListD",
  	columnName:'<fmt:message key="label.specification"/>',
  	tooltip:true,
  	width:25
},
	{
  		columnId:"itemId"
	},
	{
  		columnId:"specList"
	}
];


function addPopUrl() {}

with(milonic=new menuname("partSearchMenu")){
         top="offset=2"
         style = contextWideStyle;
         margin=3
         aI("text=<fmt:message key="label.showallocatableig"/>;url=javascript:allocationPopup('IG');");
         aI("text=<fmt:message key="label.showallocatableregion"/>;url=javascript:allocationPopup('REGION');");
         aI("text=<fmt:message key="label.showallocatableglobal"/>;url=javascript:allocationPopup('GLOBAL');");
         aI("text=<fmt:message key="label.showpreviousordersforcustomer"/>;url=javascript:showPreHistory();"); 
         aI("text=<fmt:message key="label.showallpreviousorders"/>;url=javascript:showAllPreHistory();"); 
         aI("text=<fmt:message key="showpohistory.title"/>;url=javascript:showPoHistory();"); 
         aI("text=<fmt:message key="showquotationallhistoryforcustomer.title"/>;url=javascript:showQuotationHistory();"); 
         aI("text=<fmt:message key="showquotationallhistory.title"/>;url=javascript:showAllQuotationHistory();"); 
         aI("type=header;text=<fmt:message key="label.showpricelistinfo"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
         aI("type=header;text=<fmt:message key="label.showcurrentsourcinginfo"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
         //aI("text=<fmt:message key="label.showpricelistinfo"/>;url=javascript:showPriceListInfo();"); 
         //aI("text=<fmt:message key="label.showcurrentsourcinginfo"/>;url=javascript:addPopUrl();");
         //aI("text=<fmt:message key="label.showiteminfo"/>;url=javascript:addPopUrl();"); 
    }

drawMenus();

function pp(name) {
	var value = parent.$v(name);
	return value;
//	return encodeURIComponent(value);
}

function gg(name) {
	var value = cellValue(selectedRowId,name);
	return value;
//	alert( value );
//	return encodeURIComponent(value);
}

function getcurpath (){
	return encodeURIComponent($v('curpath'));
}

function showPriceListInfo() {
	
	var loc = "/tcmIS/distribution/showpricelist.do"+
	"?companyId=" +pp('companyId')+
	"&searchArgument="+gg('alternateName')+
	"&priceGroupId="+pp('priceGroupId')+
	"&searchMode=is"+
	"&opsEntityId="+pp('opsEntityId')+
	"&curpath="+getcurpath()+
	"&searchField=catPartNo"+
	"&uAction=search";
	parent.children[parent.children.length] = 	
	openWinGeneric(loc, "showPriceListInfo_"+pp('prNumber').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");
	
}

function partinfo() {
	
	  this.opsEntityId = pp('opsEntityId');
	  this.priceGroupId = pp('priceGroupId');
	  this.priceGroupName = pp('priceGroupName');
	  this.catalogCompanyId = pp('catalogCompanyId');
	  this.catalogCompanyName = pp('catalogCompanyName');
	  this.catalogId = pp('catalogId');
	  this.catalogName = pp('catalogName');
	  this.companyId = pp('companyId');
	  this.partGroupNo = '1'; // assumption.
	  this.currencyId = pp('currencyId');

	  this.partName = gg('alternateName');
	  this.description = gg('description');

	  this.catPartNo = gg('itemId')+"-"+gg('specListId');
	  this.specList = gg('specListId')+":"+gg('specList');
	  this.specListId = gg('specListId');

}

var addedpart = new Array();
function setPart() { 
//	try {
	
	if( addedpart[selectedRowId] ) return;
//	alert( "1:"+selectedRowId+addedpart[selectedRowId] );
	var a = new partinfo();		
	parent.opener.addpart(a);
//	alert( "2:"+selectedRowId+addedpart[selectedRowId] );
	addedpart[selectedRowId] = 'added';
//	parent.close();
//	} catch(ex){}
}

function loadRowSpans()
{
 if (!haasGrid)
    return;
    
 for(var i=0;i<haasGrid.getRowsNum();i++){
   try
   {
     var rowSpan = lineMap[i];
     if( rowSpan == null || rowSpan == 1 ) continue;

     haasGrid.setRowspan(i+1,1,rowSpan*1);
     haasGrid.setRowspan(i+1,2,rowSpan*1);
   }
   catch (ex)
   {
     //alert("here 269");
   }
 }
}

function selectRightclick(rowId,cellInd){
//	 haasGrid.selectRowById(rowId,null,false,false);	
	 selectRow(rowId,cellInd);
	}

	var selectedRowId=null;
	var preClass = null;
	var preRowId = null;

	function fixRowColor(thisrow,rowind){
		var cname = "ev_haas rowselected";
		thisrow.className = cname;
	}

	function selectRow(rowId,cellInd){
		
	    rowId0 = arguments[0];
	    colId0 = arguments[1];

//	    haasGrid.selectRowById(rowId);
	// color style stuff

		if( preRowId != null) {
			if(	lineMap3[preRowId-1] == 1 )
				preClass="odd_haas";
			else
				preClass="ev_haas";
			mymap = lineIdMap[preRowId-1];
			for(var j = 0;j < mymap.length; j++) {
				 var rrid = mymap[j];
				 haasGrid.rowsAr[ rrid*1+1 ].className = preClass;
			}
		}

		mymap = lineIdMap[rowId0-1];
		for(var j = 0;j < mymap.length; j++) {
		    var rrid = mymap[j];
			fixRowColor( haasGrid.rowsAr[ rrid*1+1 ] );//,true,false,false);
		}

	    preRowId      = rowId0;
	//    
		selectedRowId = rowId0;
		var selectedPart = parent.document.getElementById("selectedPart");

		selectedPart.style["display"] = "";
		parent.document.getElementById("selectedPart").style["display"] = "";
		parent.selectedpartNumber = cellValue(rowId0,'alternateName');
	 
		selectedPart.innerHTML = '<a href="#" onclick="call('+"'setPart'"+'); return false;">'+messagesData.selectedpartnumber+' : '+cellValue(rowId0,'alternateName')+', '+messagesData.specgroup+' '+cellValue(rowId0,'specListId')+'</a>';
		parent.$('currencySpan').style.display="";
	}

var lineMap3 = new Array();
var map = null;
var lineMap = new Array();
var lineIdMap = new Array();

var gridConfig = {
		divName:'partViewBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beangrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		evenoddmap:lineMap3,
//		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//		onBeforeSorting:_onBeforeSorting
		singleClickEdit : true
	};

//	window.onresize = resizeFrames;
	windowCloseOnEsc = true;
//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);loadRowSpans();">
<tcmis:form action="/pricegrouppartsearchresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">
<!-- If the collection is empty say no data found -->
<c:if test="${empty partSearchCollection}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
<!-- Search results end -->
<div id="partViewBean" style="width:100%;%;height:400px;" style="display: none;"></div>

<c:set var="dataCount" value='${0}'/>	

<c:if test="${!empty partSearchCollection }" >
<script type="text/javascript">
<!--
<c:set var="prePart" value=""/>
<c:set var="prePos" value="-1"/>
<c:set var="idcount" value="-1"/>
<c:forEach var="partSearchBean" items="${partSearchCollection}" varStatus="status">
<c:set var="curPart" value="${partSearchBean.alternateName}"/>
<c:if test="${ curPart != prePart }">
	<c:if test="${! empty prePart}">
		lineMap[${prePos}] = map.length;
	</c:if>
	<c:set var="idcount" value="${idcount+1}"/>
	<c:set var="prePos" value="${status.index}"/>
	map = new Array();
</c:if>
 lineMap3[${status.index}] = ${idcount%2} ;
 map[map.length] = ${status.index} ; 
 lineIdMap[${status.index}] = map;
<c:set var="prePart" value='${partSearchBean.alternateName}'/>
</c:forEach>
<c:if test="${! empty prePart}">
lineMap[${prePos}] = map.length;
</c:if>


var jsonMainData = {
rows:[
<c:forEach var="partSearchBean" items="${partSearchCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
  <tcmis:jsReplace var="partDescription" value="${partSearchBean.partDescription}" processMultiLines="true" />
  <tcmis:jsReplace var="specList" value="${partSearchBean.specList}"/>
/*The row ID needs to start with 1 per their design. Use sinlge quotes for column data seperators.*/
{ id:${status.index +1},
 data:['Y',
  '<tcmis:jsReplace value="${partSearchBean.alternateName}"/>','<tcmis:jsReplace value="${partDescription}"/>','${partSearchBean.specListId}',
  '${partSearchBean.specListId}: ${specList}','${partSearchBean.itemId}','${specList}'
  ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};

//-->
</script>   
</c:if>   

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/> 
	<input name="curpath" id="curpath" type="hidden" value="${param.curpath}"/>
	<input name="priceGroupId" id="priceGroupId" type="hidden" value="${param.priceGroupId}"/>
	<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>

</body>
</html:html>
