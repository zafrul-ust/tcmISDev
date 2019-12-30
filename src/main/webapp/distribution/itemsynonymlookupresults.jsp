<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
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

<!-- Add any other javascript you need for the page here 
<script type="text/javascript" src="/js/client/catalog/itemcatalog.js"></script>
-->

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<%--This is to allow copy and paste. works only in IE--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
contextDisabled = true;

var messagesData = new Array();

messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
pleasewait:"<fmt:message key="label.pleasewaitmenu"/>",
itemid:"<fmt:message key="label.itemid"/>",
mfglit:"<fmt:message key="label.mfglit"/>",
image:"<fmt:message key="label.image"/>",
viewmsds:"<fmt:message key="label.viewmsds"/>",
shelflife:"<fmt:message key="catalog.label.shelflife"/>",
item:"<fmt:message key="label.item"/>",
componentdescription:"<fmt:message key="inventory.label.componentdescription"/>",
packaging:"<fmt:message key="inventory.label.packaging"/>",
grade:"<fmt:message key="label.grade"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
mfgpartno:"<fmt:message key="label.mfgpartno"/>",
showevaluation:"<fmt:message key="label.showevaluation"/>",
selectedRowMsg:"<fmt:message key="label.selecteditem"/>",
country:"<fmt:message key="label.country"/>"	
};

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var sourcePage = false;
<c:set var="sourcePageValue" value="${param.sourcePage}"/>
<c:choose>
<c:when test="${sourcePageValue eq 'addItem'}">
sourcePage = true;
</c:when>
</c:choose>

var map = null;
var map2 = null;
//var prerow = null;
var preRowId = null;
var preClass = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
var lineIdMap = new Array();
var lineIdMap2 = new Array();

var selectedRowId = null;
var selectedColId = null;

with ( milonic=new menuname("viewMsds222") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<fmt:message key="label.splitline"/> ;url=javascript:viewMsds();" );
 aI( "text=dkfjdsjhre ;url=javascript:viewMsds();" );	
}

drawMenus();


function fixRowColor(thisrow,rowind){
	var cname = "ev_haas rowselected";
	thisrow.className = cname;
}

function selectRow()
{
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
   if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }

	if( preRowId != null) {
		if(	lineMap3[preRowId-1] == 0 )
			preClass="odd_haas";
		else
			preClass="ev_haas";
		mymap = lineIdMap[preRowId-1];
		for(var j = 0;j < mymap.length; j++) {
		    var rrid = mymap[j];
			mygrid.rowsAr[ rrid*1+1 ].className = preClass;
		}
	}

	mymap = lineIdMap[rowId0-1];
	for(var j = 0;j < mymap.length; j++) {
	    var rrid = mymap[j];
		fixRowColor( mygrid.rowsAr[ rrid*1+1 ] );
	}

   preRowId      = rowId0;
   selectedRowId = rowId0;
   selectedColId = colId0;

	itemid = cellValue(rowId0,"itemId");
	
	if(sourcePage)
	{
	 itemdesc = cellValue(rowId0,"materialDesc");
	 parent.document.getElementById('updateResultLink').style["display"] = "none";
	 var selectedUser = parent.document.getElementById("selectedRow");
     selectedUser.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">'+messagesData.selectedRowMsg+' : '+ itemid +'</a>';
   }
   else
   {
     parent.document.getElementById('selectedRow').style["display"] = "none";
	 parent.document.getElementById('showOptions').style["display"] = "";
   }

	if( !rightClick ) return;
}

function selectData()
{ 
  try {
 	parent.opener.document.getElementById(parent.$v('itemId')).value = itemid;
  	var openerdisplayElementId = parent.opener.document.getElementById(parent.$v('itemdesc'));
  	openerdisplayElementId.className = "inputBox";
  	openerdisplayElementId.value = itemdesc;
  } catch( ex ) {}
  
  try {
  if( parent.opener.itemChanged ) {
	  parent.opener.itemChanged(itemid,itemdesc);
  }
  } catch(exx) {}
	parent.close();
}


var config = [
{
    columnId:"itemId",  	
	columnName:messagesData.item
},
{
    columnId:"materialDesc"  ,	
	 columnName:messagesData.componentdescription,
	 width:30
},
{
    columnId:"packaging",  	
	columnName:messagesData.packaging,
	width:20
},
{
    columnId:"grade",  	
	columnName:messagesData.grade
},
{
    columnId:"mfgDesc", 	
	columnName:messagesData.manufacturer,
	width:30
},
{
    columnId:"country",  	
	columnName:messagesData.country
},
{
    columnId:"mfgPartNo",  	
	columnName:messagesData.mfgpartno
},
{
    columnId:"shelfLifeDisplay",  	
	columnName:messagesData.shelflife,
	width:20
},
{
    columnId:"msdsOnLine"  	
},
{
    columnId:"materialId"  	
},
{
    columnId:"engEval"  	
}
];

var gridConfig = {
		divName:'itemCatalogScreenSearchBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'mygrid',     // the grid's name, as in beanGrid.attachEvent...
		config:config,	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRow   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//		onBeforeSorting:_onBeforeSorting
	};

   function resultOnLoad() {
		parent.document.getElementById('updateResultLink').style["display"] = "none";
		if ($v("totalLines") > 0) {
			loadRowSpans();
		}else {
			parent.document.getElementById('mainUpdateLinks').style["display"] = "";
		}
       if (sourcePage)
       {
           parent.document.getElementById('updateResultLink').style["display"] = "none";
           var selectedUser = parent.document.getElementById("selectedRow");
           selectedUser.innerHTML = '';
       }
    }

	function loadRowSpans()
	{
	  for(var i=0;i<mygrid.getRowsNum();i++){
		  try
		  {
			var rowSpan = lineMap[i];
			if( rowSpan == null || rowSpan == 1 || rowSpan == 0 || rowSpan == -1 ) continue;
			mygrid.setRowspan(i+1,0,rowSpan*1);
		  }
		  catch (ex)
		  {
		  //alert("here 269");
		  }
		}

	  for(var i=0;i<mygrid.getRowsNum();i++){
	  		if(lineMap3[i] == 0)
				preClass="odd_haas";
			else
				preClass="ev_haas";
			mygrid.rowsAr[ i+1 ].className = preClass;
	  }

	}

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(gridConfig);setTimeout('resultOnLoad()',100);">
<tcmis:form action="/searchglobalitemresults.do" onsubmit="return submitFrameOnlyOnce();">

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="parCount" value="0"/>

<c:set var="dataCount" value='${0}'/>
<c:set var="prePar" value=""/>

<c:if test="${itemCatalogBeanCollection != null}" >
 <c:if test="${!empty itemCatalogBeanCollection}" >
//  var jsonMainData = new Array();
//  The color stuff is not working at this moment, I will use
//  javascript to fix it.
  var jsonMainData = {
  rows:[
  <c:forEach var="p" items="${itemCatalogBeanCollection}" varStatus="status">
	  <c:if test="${status.index != 0 }">,</c:if>
	<c:set var="curPar" value="${status.current.itemId}"/>
	<c:if test="${!(curPar eq prePar)}">
		<c:set var="parCount" value="${parCount+1}"/>
		<c:if test="${ parCount % 2 == 0 }">
			<c:set var="partClass" value="odd_haas"/>
		</c:if>
		<c:if test="${ parCount % 2 != 0 }">
			<c:set var="partClass" value="ev_haas"/>
		</c:if>
	</c:if>

    { id:${status.index +1},"class":"${partClass}",
        data:[
              '${p.itemId}',
				  '<tcmis:jsReplace value="${p.materialDesc}" processMultiLines="true"/>',
				  '<tcmis:jsReplace value="${p.packaging}" processMultiLines="true"/>',
				  '<tcmis:jsReplace value="${p.grade}" processMultiLines="true"/>',
			     '<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>',
				  '<tcmis:jsReplace value="${p.countryName}" processMultiLines="true"/>',
				  '<tcmis:jsReplace value="${p.mfgPartNo}" processMultiLines="true"/>',
				  '<tcmis:jsReplace value="${p.shelfLife}" processMultiLines="true"/>',
					// hidden here.
	 		     '${p.msdsOnLine}',
				  '${p.materialId}',
				  '${p.engEval}' 
				  ]}
		<c:set var="numberOfKit" value="${status.current.numberOfKitsPerItem}"/>
		<c:if test="${!(numberOfKit eq -1)}">
			<c:set var="dataCount" value='${dataCount+1}'/>
		</c:if>
		<c:set var="prePar" value="${status.current.itemId}"/>

     </c:forEach>
  ]};

  </c:if>
 </c:if>

// -->
</script>

<%-- determining rowspan --%>
<c:set var="itemCount" value='0'/>
<c:forEach var="p" items="${itemCatalogBeanCollection}" varStatus="status">
	<c:set var="numberOfKit" value="${status.current.numberOfKitsPerItem}"/>
	<script language="JavaScript" type="text/javascript">
	<!--
	   lineMap[${status.index}] = ${numberOfKit} ;
		<c:if test="${!(numberOfKit eq -1)}">
	      <c:set var="itemCount" value='${itemCount+1}'/>
			map = new Array();
		</c:if>
	   lineMap3[${status.index}] = ${itemCount} % 2;
		map[map.length] =  ${status.index} ; lineIdMap[${status.index}] = map;
	// -->
	</script>
</c:forEach>

<script language="JavaScript" type="text/javascript">
<!--
	var altAccountSysId = new Array(
	<c:forEach var="prRulesViewBean" items="${prRulesViewCollection}" varStatus="status2">
		<c:if test="${status2.index > 0}">
		 ,
		</c:if>
		{id:'<tcmis:jsReplace value="${status2.current.accountSysId}"/>',label:'<tcmis:jsReplace value="${status2.current.accountSysLabel}"/>'}
	</c:forEach>
	);
// -->
</script>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<%--
<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >
--%>
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
<%--</tcmis:facilityPermission>--%>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
${tcmisError}
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmisError}">
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

<%--NEW - there is no results table anymore--%>  
<div id="itemCatalogScreenSearchBean" style="width:100%;%;height:400px;" style="display: none;"></div>


<c:if test="${itemCatalogBeanCollection != null}" >
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty itemCatalogBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="sourcePage" id="sourcePage" type="hidden" value="${param.sourcePage}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html>
