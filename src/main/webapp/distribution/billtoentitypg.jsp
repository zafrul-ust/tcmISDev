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
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>
<%@ include file="/common/opshubig.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
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
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="label.billpg"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
legalCompanyNameRequired:"<fmt:message key="error.legalcompanyname.required"/>",
countryRequired:"<fmt:message key="error.country.required"/>",
addressRequired:"<fmt:message key="error.address.required"/>",
cityRequired:"<fmt:message key="error.city.required"/>",
stateRequired:"<fmt:message key="error.state.required"/>",
zipRequired:"<fmt:message key="label.postalcode"/>",
billToNameRequired:"<fmt:message key="label.billto"/> <fmt:message key="label.name"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
all:"<fmt:message key="label.all"/>"
};

//var beginning = true; 
/*
defaults.ops =  {id:'',name:messagesData.pleaseselect};
defaults.inv =  {id:'',name:messagesData.pleaseselect};
defaults.pg  =  {id:'',name:messagesData.pleaseselect};
defaults.pg.nodefault = true;

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
//           	 	   name: '<tcmis:jsReplace value="${status.current.priceGroupName}"/>'
           	 	     name: '${status.current.priceGroupId}',
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

	   function setDropDowns()
	   {
	   	buildDropDown(opshubig,defaults.ops,"opsEntityId");
	    	$('opsEntityId').onchange = setInv;    
	   	setInv();
	   }

	   function buildNewIgValudset(opsid) {
//	   	var opsid = $v('opsEntityId');
	   	var newInvArray = new Array();
	   	if( !opsid ) return newInvArray; 
	   	for( i=0;i < opshubig.length; i++) {
	   		if( opshubig[i].id == opsid ) {
	   			var hubcoll = opshubig[i].coll;
	   			for( j = 0;j< hubcoll.length;j++ ){
	   				var igcoll = hubcoll[j].coll;
	   				for( k=0;k< igcoll.length;k++ ){
	   					newInvArray[newInvArray.length] = {name:igcoll[k].name,id:igcoll[k].id};
	   				}
	   			}	
	   		}
	   	}
	   	return newInvArray;
	   }

	   function setInv()
	   {
	   	 var opsO = $("opsEntityId");
	   	 var arr = buildNewIgValudset(opsO.value);
	   	 
	   	 buildDropDown(arr,defaults.inv,"inventoryGroup");

	   	   var arr = null;
	   	   if( opsO.value != '' ) {
	   	   	   for(i=0;i< opspg.length;i++)
	   	   	   		if( opspg[i].id == opsO.value ) {
	   	   	   			arr = opspg[i].coll;
	   	   	   			break;
	   	   	   		}
	   	   }
	   	   buildDropDown(arr,defaults.pg,"priceGroupId");
	   }
	   
// This builddropdown I wantto put in common
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
	   		  for ( var i=0; i < arr.length; i++) {
	   		    	setOption(start++,arr[i].name,arr[i].id,eleName);
	   		  }
	   	  }
	   	  obj.selectedIndex = 0;
	   }
*/
windowCloseOnEsc = true;

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="initGridWithGlobal(_gridConfig);showRemoveLine();"  onresize="resizeFrames()">

<tcmis:form action="/billtoentitypg.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${done == 'Y'}">
    done = true;
   </c:when>
   <c:otherwise>
    done = false;
   </c:otherwise>
</c:choose>

//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
    <c:set var="selectedbillToCountry" value='USA'/>
    <tcmis:isCNServer>
		<c:set var="selectedbillToCountry" value='CHN'/>
	</tcmis:isCNServer> 
<%-- 
<fieldset>
<legend>&nbsp;<fmt:message key="label.operatingentitydefaultpricelist"/>&nbsp;</legend>
--%>
<c:set var="billPermission" value="N"/>
<tcmis:opsEntityPermission indicator="true" userGroupId="priceGroupAssignment">
  <c:set var="billPermission" value="Y"/>
</tcmis:opsEntityPermission>

<c:if test="${billPermission == 'Y'}">

<span id="updateLine" style='color:blue;cursor:pointer'>
<a onclick="submitUpdate();"><fmt:message key="label.update"/></a>	
&nbsp;
</span>
|&nbsp;
<span id="newbilltoAddLine" style='color:blue;cursor:pointer'>
<a onclick="addNewbilltosLine()"><fmt:message key="label.addline"/></a>	
&nbsp;
</span>
<span id="newbilltoRemoveLine" style="color:blue;cursor:pointer;display:none">
|&nbsp;
<a onclick="removebilltoLine()"><fmt:message key="label.removeLine"/></a>
</span>
|&nbsp;
<span id="closeSpan" style="color:blue;cursor:pointer">
<a onclick="window.close()"><fmt:message key="label.close"/></a>
&nbsp;
</span>
</c:if>
<div id="billColl" style="height:150px;width:99%;display: none;" ></div>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
//to build price list drop down.
function submitUpdate() {
//	  numberOfRows = document.getElementById('totalLines').value;
	  var flag = true;//validateUpdateForm(numberOfRows);
	  if(flag) {
	    var action = document.getElementById("uAction");
	    action.value = 'update';
//	    submitOnlyOnce();
	    billGrid.parentFormOnSubmit();
		showPleaseWait();
	 	window.setTimeout("document.genericForm.submit();",500); 	  
//	    document.genericForm.submit();
//		parent.showPleaseWait();
	  }
	  return flag;
}

var pgdefaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true},
	   	   pg: {id:'',name:'<fmt:message key="label.pleaseselect"/>'}
	   }

<c:set var="preops" value=""/>
<c:set var="opssep" value=""/>
var opspg = [
	    <c:forEach var="nouse0" items="${opsPgColl}" varStatus="status">
<c:set var="hasPerm" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="priceGroupAssignment">
<c:set var="hasPerm" value="Y"/>
</tcmis:opsEntityPermission>
	    <c:set var="curops" value="${status.current.opsEntityId}"/>
	    <c:if test="${ preops ne curops}">
	    ${opssep}
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>',
          hasPerm:'${hasPerm}',
          coll: [ 
        	 	  {  id: '${status.current.priceGroupId}',
//           	 	   name: '<tcmis:jsReplace value="${status.current.priceGroupName}"/>'
           	 	     name: '${status.current.priceGroupId}',
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

var opsEntityIdArr = new Array();
for( i=0;i < opspg.length; i++) { // opshubig
	opsEntityIdArr[i] = {text:opspg[i].name,value:opspg[i].id}
}

var billtorowids = new Array();
var opsEntityId = new Array();
var priceGroupId= new Array();
<c:forEach var="bean" items="${beanColl}" varStatus="status">
opsEntityId[${status.index +1}] = opsEntityIdArr;
billtorowids[${status.index +1}] = ${status.index +1}; 
</c:forEach>

function buildNewOpsValudset() {
	var newItemIdArray = new Array();
	for( i=0;i < opsEntityIdArr.length; i++) 
		if( opspg[i].hasPerm == 'Y' )
			newItemIdArray[opsEntityIdArr[i].value] = {text:opsEntityIdArr[i].text,value:opsEntityIdArr[i].value};

//	for( rowid = 1; rowid <= ind; rowid++ ) {
//  delete the incoming opsentity id	
	delete( newItemIdArray[$v('opsEntityId')] ) ;
	for( rowid in billtorowids ) {
		val = gridCellValue(billGrid,rowid,"opsEntityId");
		if( val )
			delete( newItemIdArray[val] ) ;
	}
	var valArray = new Array();
	for( v in newItemIdArray )
		valArray[valArray.length] = newItemIdArray[v];
	return valArray;
}

function buildNewpriceGroupId(opsid,priceid) {
//	var opsid = $v('opsEntityId');
	var newInvArray = new Array();
	newInvArray[newInvArray.length] = {text:pgdefaults.pg.name,value:pgdefaults.pg.id};
	for( i=0;i < opspg.length; i++) {
		if( opspg[i].id == opsid && opspg[i].hasPerm == 'Y' ) {
			var hubcoll = opspg[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				newInvArray[newInvArray.length] = {text:hubcoll[j].name,value:hubcoll[j].id};
			}	
		}
	}
// This code is for page that already have value.
    if( newInvArray.length == 1 && priceid ) {
    	for( i=0;i < opspg.length; i++) {
    		if( opspg[i].id == opsid ) {
    			var hubcoll = opspg[i].coll;
    			for( j = 0;j< hubcoll.length;j++ ){
        			if( hubcoll[j].id == priceid ) { 
    					newInvArray[0].text = hubcoll[j].name;
    					newInvArray[0].id   = hubcoll[j].id;
    					return newInvArray;
        			}
    			}	
    		}
    	}
    }
	return newInvArray;
}

<c:forEach var="bean" items="${beanColl}" varStatus="status">
	//defaultInventoryGroup[${status.index +1}] = buildNewIgValudset('${bean.opsEntityId}');
	opsEntityId[ ${status.index +1}] = opsEntityIdArr;// initial value set buildNewOpsValudset();
	priceGroupId[${status.index +1}] = buildNewpriceGroupId('${bean.opsEntityId}','${bean.priceGroupId}');
</c:forEach>

function billOpsChanged(rowId,columnId,invval) {
	
	  var selectedOps = gridCellValue(billGrid,rowId,columnId);

//pricegroupid	  
	  var inv = $("priceGroupId"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewpriceGroupId(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.pleaseselect,"", "priceGroupId"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "priceGroupId"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}


<c:set var="billCount" value='0'/>
/*
private String facilityId;
private String facilityName;
private String companyId;
private String opsEntityId;
private String operatingEntityName;
private String priceGroupId;
private String priceGroupName;
private String inventoryGroupDefault;
*/

var billtoData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>

        <c:if test="${readonly == 'true'}">
	    </c:if>
        <c:if test="${readonly != 'true'}">
   		</c:if>

        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${billPermission}',//'${gp}',
 'N',//'${gp}',
 '${bean.opsEntityId}',
 '${bean.priceGroupId}',
 '${bean.operatingCompanyId}',
 ''
 ]}
    <c:set var="billCount" value='${billCount+1}'/>
     </c:forEach>
    ]};

var billColumn = [
  {
  	columnId:"permission"
  },
{
  	columnId:"opsEntityIdPermission"
},
{
  	columnId:"opsEntityId",
	columnName:"<fmt:message key="label.operatingentity"/>",
    onChange:billOpsChanged,
    permission:true,
	width:20
	,type:"hcoro"
},
{
  	columnId:"priceGroupId",
	columnName:"<fmt:message key="label.pricelist"/>",
    //onChange:billOpsChanged,
	width:20
	,type:"hcoro"
},
{
  	columnId:"operatingCompanyId",
//	columnName:"<fmt:message key="label.inventorygroup"/>",
	width:20
	//,type:"hcoro"
} ,
{
  	columnId:"changed"
}
];

function removebilltoLine() {
	if( billtoselectedRowId == null ) return;
	if( gridCellValue(billGrid,billtoselectedRowId,"changed") == 'New' ) {
	billGrid.deleteRow(billtoselectedRowId);
	delete( billtorowids[billtoselectedRowId] ) ;
	if( billGrid.getRowsNum() == 0 ) 
		$('newbilltoRemoveLine').style["display"] = "none";
	billtoselectedRowId = null;
	return ;
	}
	gridCell(billGrid,billtoselectedRowId,"changed").setValue("Remove");
	submitUpdate();
}

var billtoselectedRowId = null;

function addNewbilltosLine() {

 var rowid = (new Date()).valueOf();
 ind = billGrid.getRowsNum();
	  
	  count = 0 ;
	  var arr = buildNewOpsValudset();
	  if( !arr || !arr.length ) return; 
	  opsEntityId[ rowid ] = arr;
      priceGroupId[ rowid ] = buildNewpriceGroupId(opsEntityId[ rowid ][0].value );
	  if(priceGroupId[ rowid ].length == 0) {
		  priceGroupId[ rowid ] = new Array( {text:messagesData.all,value:""} );
	  }
		  
	  
	var thisrow = billGrid.addRow(rowid,"",ind);

// opsentityid
	  billGrid.cells(rowid, count++).setValue('Y');
	  billGrid.cells(rowid, count++).setValue('Y');
	  billGrid.cells(rowid, count++).setValue(opsEntityId[ rowid ][0].value);
	  billGrid.cells(rowid, count++).setValue(priceGroupId[ rowid ][0].value);
	  billGrid.cells(rowid, count++).setValue('Default');
	  billGrid.cells(rowid, count++).setValue('New');
	  
	  billtorowids[""+rowid] = rowid;
	  billtoselectedRowId = rowid;
	  billGrid.selectRow(billGrid.getRowIndex(rowid));
	  $('newbilltoRemoveLine').style.display="";

}

function billtoselectRow()
{
 rightClick = false;
 rowId0 = arguments[0];
 colId0 = arguments[1];
 ee     = arguments[2];

 oldRowId = rowId0;

 if( ee != null ) {
 		if( ee.button != null && ee.button == 2 ) rightClick = true;
 		else if( ee.which == 3  ) rightClick = true;
 }

 billtoselectedRowId = rowId0;
 
} //end of method

function showRemoveLine() {
	if( billGrid != null ) {
		var line = billGrid.getRowsNum();
		if( line && $('newbilltoRemoveLine') ) 
			$('newbilltoRemoveLine').style.display="";
	}
}

_gridConfig.submitDefault = true;
_gridConfig.divName = 'billColl';
_gridConfig.config = 'billColumn';	     // the column config var name, as in var config = { [ columnId:..,columnName...
_gridConfig.beanData = 'billtoData';
//_gridConfig.rowSpan = -1;
_gridConfig.beanGrid = 'billGrid';
_gridConfig.onRowSelect = billtoselectRow;
_gridConfig.onRightClick = billtoselectRow;
//_gridConfig.singleClickEdit = true;

//-->
</script>
<%-- 
</fieldset>
--%>

<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="update"/>
<input type="hidden" name="totalLines" id="totalLines" value="${billCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>

<input type="hidden" name="customerId" id="customerId" value="${param.customerId}"/>
<input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
<input type="hidden" name="billToCompanyId" id="billToCompanyId" value="${param.billToCompanyId}"/>
<input type="hidden" name="customerName" id="customerName" value="${param.customerName}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>
</body>
</html:html>