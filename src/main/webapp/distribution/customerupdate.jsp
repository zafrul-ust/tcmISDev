<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>

<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>
<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>

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
<script type="text/javascript" src="/js/distribution/customerupdate.js"></script>
<script type="text/javascript" src="/js/common/taxcodes.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
addressRequired:"<fmt:message key="label.address"/>",
cityRequired:"<fmt:message key="label.city"/>",
stateRequired:"<fmt:message key="label.state"/>",
zipRequired:"<fmt:message key="label.postalcode"/>",
countryRequired:"<fmt:message key="label.country"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
paymentTermsRequired:"<fmt:message key="label.paymentterms"/>",
creditlimit:"<fmt:message key="label.creditlimit"/>",
overduelimit:"<fmt:message key="label.graceperiod"/>",
taxRegistrationNumberRequired:"<fmt:message key="error.federaltaxidorvatnum.required"/>",
mustbeanumber:"<fmt:message key="label.mustbeanumberinthisfield"/>",
taxregistrationnum:"<fmt:message key="label.taxregistrationnum"/>",
defaultshelflife:"<fmt:message key="label.defaultshelflife"/>",
creditStatusChange:"<fmt:message key="label.creditstatuschange"/>",
stateProvinceRequired:"<fmt:message key="label.state"/>/<fmt:message key="label.region"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
nochange:"<fmt:message key="error.nochange"/>",
registration:"<fmt:message key="label.registration"/>",
vatinvalidforcountryregion:"<fmt:message key="label.invvalidvarforcountryregion"/>",
vatinvalidforcountry:"<fmt:message key="label.invvalidvarforcountry"/>",
einvoiceemailaddressinvalid:"<fmt:message key="error.einvoiceemailaddressinvalid"/>",
jdecustomerbillto:"<fmt:message key="label.jdebilltoabno"/>"
};

function clearRep(rowid) {
	gridCell(sgrid,rowid,"fieldSalesRepName").setValue('');
	gridCell(sgrid,rowid,"fieldSalesRepId").setValue('');
}

function clearAgent(rowid) {
	gridCell(sgrid,rowid,"salesAgentName").setValue('');
	gridCell(sgrid,rowid,"salesAgentId").setValue('');
}

function getRep(rowid) {
	  loc = "searchpersonnelmain.do?fixedCompanyId=Y&callbackparam=rep_"+rowid;
	  openWinGeneric(loc,"changepersonnel1","600","450","yes","50","50","20","20","no");
}

function getAgent(rowid) {
	  loc = "searchpersonnelmain.do?fixedCompanyId=Y&callbackparam=agent_"+rowid;
	  openWinGeneric(loc,"changepersonnel2","600","450","yes","50","50","20","20","no");
}

function personnelChanged(returnSelectedUserId,returnSelectedUserName,callbackparam){
	var f = callbackparam.split("_");
	if( f[0] == "rep" ) {
		gridCell(sgrid,f[1],"fieldSalesRepName").setValue(returnSelectedUserName);
		gridCell(sgrid,f[1],"fieldSalesRepId").setValue(returnSelectedUserId);
	}
	if( f[0] == "agent" ) {
		gridCell(sgrid,f[1],"salesAgentName").setValue(returnSelectedUserName);
		gridCell(sgrid,f[1],"salesAgentId").setValue(returnSelectedUserId);
	}
	
//	alert( returnSelectedUserId+":"+returnSelectedUserName+":"+f[0]+":"+f[1] );
}

var showUpdateLinks = false;
var isPerm1True = false;

var altCountryId = new Array(
 ''
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
    ,'${status.current.countryAbbrev}'
</c:forEach>
);

var altCountryName = new Array(
 '<fmt:message key="label.pleaseselect"/>'
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
     <c:set var="countryName">${fn:substring(status.current.country,0,30)}</c:set>
    ,'<tcmis:jsReplace value="${countryName}"/>'
</c:forEach>
);

var oriCountry = '${customerBean.countryAbbrev}';
var oriState   = '${customerBean.stateAbbrev}';

var altState = new Array();
var altStateName = new Array();
<c:forEach var="vvCountryBean" items="${vvCountryBeanCollection}" varStatus="status">
  <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
  <c:set var="stateCollection" value='${status.current.stateCollection}'/>
  altState["${currentCountry}"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
  <c:set var="curState" value="${status1.current.stateAbbrev}"/>
  <c:if test="${curState eq 'None'}">
	<c:set var="curState" value=""/>
  </c:if>

<c:choose>
   <c:when test="${status1.index > 0}">
        ,"${curState}"
   </c:when>
   <c:otherwise>
        "${curState}"
   </c:otherwise>
</c:choose>
  </c:forEach>
  );

  altStateName["${currentCountry}"] = new Array(
  <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="status1">
 <c:choose>
   <c:when test="${status1.index > 0}">
        ,"${status1.current.state}"
   </c:when>
   <c:otherwise>
        "${status1.current.state}"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>



// permissions
<c:set var="hasAutoEmailPerm" value="N"/>
<c:set var="adminPermissions" value="false"/>
<c:set var="perm2" value="false"/>
<c:set var="editAddressPermissions" value='N'/>

<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailAdmin">   
   <c:set var="adminPermissions" value="true"/>
   <c:set var="perm2" value="true"/>   
</tcmis:opsEntityPermission>

<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailUpdate">   
<c:if test="${customerBean.creditStatus != 'Stop'}">
   <c:set var="perm2" value="true"/>
</c:if>
</tcmis:opsEntityPermission>

<tcmis:opsEntityPermission indicator="true" userGroupId="editShipto">   
<c:set var="editAddressPermissions" value='Y'/>   
</tcmis:opsEntityPermission>


<tcmis:opsEntityPermission indicator="true" userGroupId="GenerateOrders">
<c:if test="${customerBean.creditStatus != 'Stop'}">
    <c:set var="perm2" value="true"/>
</c:if>
</tcmis:opsEntityPermission>

// config here. CONTACT CONFIG
var cconfig = [
	{columnId:"permission",
	columnName:'',
	submit:false
 },             
  {
    columnId:"fullName",
    columnName:'<fmt:message key="label.name"/>',
	submit:false
  },
  {
  	columnId:"nickName",
   columnName:'<fmt:message key="label.nickname"/>',
   submit:true
  },
  {
  	columnId:"contactType",
   columnName:'<fmt:message key="label.jobfunction"/>',
   submit:true
  },
  {
  	columnId:"phone",
   columnName:'<fmt:message key="label.phone"/>',
   submit:true
  },
  {  
    columnId:"mobile",
   columnName:'<fmt:message key="label.mobile"/>',
   submit:true
  },
  {
    columnId:"fax",
    columnName:'<fmt:message key="label.fax"/>',
    type:'ed',
    submit:true
  },
  {
  	columnId:"email",
   columnName:'<fmt:message key="label.email"/>',
  	width:10,
  	submit:true
  },
  {
  	columnId:"otherJobFunctions",
  	columnName:'<fmt:message key="label.otherjobfunctions"/>',
  	width:22,
  	submit:true
  },
  {
	columnId:"status",
	columnName:'<fmt:message key="label.active"/>',
	type:"hchstatus",
	align:'center',
	submit:true		
  },
  {
  	columnId:"defaultContact",
	columnName:"<fmt:message key="label.defaultcontact"/>",
  	type:'hchstatus', 
  	onChange:checkOne, 
    align:'center',
    submit:true		
  },
  {
	columnId:"contactPersonnelId",
	submit:true	    
  },  
  { columnId:"customerId",
	submit:true	
  }, 
  { columnId:"contactPersonnelId",
	submit:true	
  },
  { columnId:"firstName",
	submit:true	
  },
  { columnId:"lastName",
	submit:true	
  },
  { columnId:"purchasing",
	submit:true	
  },
  { columnId:"accountsPayable",
	submit:true	
  },
  { columnId:"receiving",
	submit:true	
  },
  { columnId:"management",
	submit:true	
  },
  { columnId:"qualityAssurance",
	submit:true	
  },
  {  
	columnId:"contactInfoChange",
  	submit:true	
  }
  
  
]; 

var altCompanyId = new Array(""
	<c:forEach var="companyB" items="${companyBean}" varStatus="status">
	<tcmis:jsReplace var="companyBCompanyId" value="${status.current.companyId}"  processMultiLines="true"/>
	,'${companyBCompanyId}'
	</c:forEach>
);
var altCompanyName = new Array(""
	<c:forEach var="companyB" items="${companyBean}" varStatus="status">
	<tcmis:jsReplace var="companyBCompanyName" value="${status.current.companyName}"  processMultiLines="true"/>
	,'${companyBCompanyName}'
	</c:forEach>
);

var div23height = null;
var div1height = null;
var dhxLayout = null;
var contactInfoMap = {};


<c:set var="contactDataCount" value='${0}'/>



var cdata = {
rows:[
<c:forEach var="bean" items="${customerContactColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="other" value=""/>
<c:if test="${bean.purchasing == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.purchasing"/></c:set>
</c:if>
<c:if test="${bean.accountsPayable == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.accountspayable"/></c:set>
</c:if>
<c:if test="${bean.receiving == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.receiving"/></c:set>
</c:if>
<c:if test="${bean.qualityAssurance == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.qualityassurance"/></c:set>
</c:if>
<c:if test="${bean.management == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.management"/></c:set>
</c:if>
<c:if test="${other != ''}">
	<c:set var="other">${fn:substringAfter(other,",")}</c:set>
</c:if>

<c:if test="${bean.contactPersonnelId > 0}">
<c:set var="showEdit" value='Y'/>
</c:if>

<c:set var="hasPerm" value='N'/>
<c:if test="${(adminPermissions== true) or (perm2== true)}">
<c:set var="hasPerm" value='Y'/>
</c:if>

<c:set var="conStatus" value="false"/>
<c:if test="${bean.status eq 'ACTIVE'}"><c:set var="conStatus" value="true"/></c:if>

<c:set var="defaultContact" value="false"/>
<c:if test="${bean.defaultContact eq 'Y'}"><c:set var="defaultContact" value="true"/></c:if>   

{ id:${status.index +1},

	<c:if test="${bean.status eq 'INACTIVE'}">'class':"grid_black",</c:if>
	
 data:[
	'${hasPerm}',     
  '<tcmis:jsReplace value="${bean.lastName}"/>, <tcmis:jsReplace value="${bean.firstName}"/>',
  '<tcmis:jsReplace value="${bean.nickname}"/>',
  '<tcmis:jsReplace value="${bean.contactType}"/>',
  '${bean.phone}',
  '${bean.altPhone}',
  '${bean.fax}',
  '<tcmis:jsReplace value="${bean.email}"/>',
  '${other}',
  ${conStatus},${defaultContact},
  '${bean.contactPersonnelId}',
  '${bean.customerId}','${bean.contactPersonnelId}','<tcmis:jsReplace value="${bean.firstName}"/>','<tcmis:jsReplace value="${bean.lastName}"/>',
  '${bean.purchasing}', '${bean.accountsPayable}','${bean.receiving}','${bean.management}','${bean.qualityAssurance}',false
  ]
}
<c:set var="contactDataCount" value='${contactDataCount+1}'/>
 </c:forEach>
]};

<c:set var="mapDataCount" value='${0}'/>
<c:forEach var="bean" items="${customerContactColl}" varStatus="statusMap">
<c:set var="conStatus" value="false"/>
<c:if test="${bean.status eq 'ACTIVE'}"><c:set var="conStatus" value="true"/></c:if>
<c:set var="defaultContact" value="false"/>
<c:if test="${bean.defaultContact eq 'Y'}"><c:set var="defaultContact" value="true"/></c:if>  
contactInfoMap['active' + '${mapDataCount}'] =   '${conStatus}';
contactInfoMap['dContact' + '${mapDataCount}'] =   '${defaultContact}';
<c:set var="mapDataCount" value='${mapDataCount+1}'/>
</c:forEach>
contactInfoMap['count'] =   '${mapDataCount}';

var cgrid = null;
/*
var gridConfig = {
	divName:'contactBean', // the div id to contain the grid.
	beanData:'cdata',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'cgrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'cconfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:false,    // the fields in grid are defaulted to be submitted or not.
	onRowSelect:cSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:cSelectRow,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	onBeforeSelect:doOnBeforeSelect
//	onBeforeSorting:_onBeforeSorting
};

*/


function modShipTo() {
	var rowid = shipSelectedRowId;
//var loc = "/tcmIS/distribution/newshiptoaddress.do?fromCustomerDetail=Yes&customerIdRequestType="+ "&customerId=" + $v('customerId')+ "&billToCompanyId=" + $v('billToCompanyId')+ "&priceList=" + $v('priceGroupId')+ "&fieldSalesRepId=" + $v('fieldSalesRepId')+ "&fieldSalesRepName=" + encodeURIComponent($v('fieldSalesRepName'));
var loc = "/tcmIS/distribution/shiptoentitypg.do"+
	"?companyId="  + encodeURIComponent( gridCellValue(sgrid,rowid,"companyId") ) +
	"&facilityId=" + encodeURIComponent( gridCellValue(sgrid,rowid,"facilityId"));
	"&opsEntityId=" + encodeURIComponent( gridCellValue(sgrid,rowid,"opsEntityId"));
//
// hard code value  var loc = "http://localhost/tcmIS/distribution/shiptoentitypg.do?companyId=RAYTHEON&facilityId=Richardson";
  
	children[children.length] = openWinGeneric(loc, "newShipToAddress" + "", "800", "330", "yes", "50", "50");
}

function modBillTo() {
	//var loc = "/tcmIS/distribution/newshiptoaddress.do?fromCustomerDetail=Yes&customerIdRequestType="+ "&customerId=" + $v('customerId')+ "&billToCompanyId=" + $v('billToCompanyId')+ "&priceList=" + $v('priceGroupId')+ "&fieldSalesRepId=" + $v('fieldSalesRepId')+ "&fieldSalesRepName=" + encodeURIComponent($v('fieldSalesRepName'));
	  var loc = "/tcmIS/distribution/billtoentitypg.do"+
	      "?billToCompanyId="+encodeURIComponent($v('billToCompanyId'))+
	      "&customerId="+encodeURIComponent($v('customerId'))+
	  	  "&customerName="+encodeURIComponent($v('customerName'))+
	  	  "&opsEntityId="+encodeURIComponent($v('opsEntityId'));
	  	
		children[children.length] = openWinGeneric(loc, "newBillToPG" + "", "800", "330", "yes", "50", "50");
}

function chooseCustomerValidCurrency() {
	  var loc = "/tcmIS/distribution/customervalidcurrencymain.do"+
	      "?customerId="+encodeURIComponent($v('customerId'))+
	  	  "&customerName="+encodeURIComponent($v('customerName'));
	  	  //"&opsEntityId="+encodeURIComponent($v('opsEntityId'));
	  	
		children[children.length] = openWinGeneric(loc, "customerValidCurrency" + "", "700", "500", "yes", "50", "50");
}

with(milonic=new menuname("newShipToMenu")){
     top="offset=2"
     style = contextStyle;
     margin=3
     aI("text=<fmt:message key="label.newshipto"/>;url=javascript:newShipTo();"); 
     aI("text=<fmt:message key="label.modifyshippg"/>;url=javascript:modShipTo();"); 
     <c:if test="${editAddressPermissions == 'Y'}">
  	 aI("text=<fmt:message key="label.editaddress"/>;url=javascript:editAddress();"); 
  	 </c:if>
}

with(milonic=new menuname("newContact")){
     top="offset=2"
     style = contextStyle;
     margin=3
     aI("text=<fmt:message key="label.newcontact"/>;url=javascript:newContact();"); 
     <c:if test="${showEdit == 'Y'}">
     aI("text=<fmt:message key="label.editcontact"/>;url=javascript:editContact();"); 
 </c:if>
}

with(milonic=new menuname("newCarrier")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="newcarrier.title"/>;url=javascript:openNewCarrierWin();");
}

drawMenus();
	
var shipToConfig = [
{columnId:"permission",
	columnName:'',
	submit:true
},        
{columnId:"facilityPriceGroupIdPermission",
	columnName:'',
	submit:true
},  
{ columnId:"locationShortName",
  columnName:'',  
  tooltip:"Y"
},
{ columnId:"locationDesc",
  columnName:'<fmt:message key="label.shiptoname"/>'
},
{ columnId:"address",
  columnName:'<fmt:message key="label.address"/>',  
  tooltip:"Y"
},
{ columnId:"facilityPriceGroupId",
  columnName:'<fmt:message key="label.globalshiptopricelist"/>',  
		width:14,
 	<c:if test="${(adminPermissions== true) or (perm2== true)}">
	  	type:"hcoro",
 	</c:if>  	  	
  tooltip:"Y"
}
,
	{
	  	columnId:"salesAgentId"
	},
	{
	  	columnId:"fieldSalesRepId"
	},
{ columnId:"salesAgentName",
  columnName:'<fmt:message key="label.salesagent"/>',  
  tooltip:"Y"
},
{
  	columnId:"salesAgentLookup",
  	<c:if test="${(adminPermissions== true) or (perm2== true)}">
  	  	columnName:'#cspan',
 	</c:if>  	  	
//  	attachHeader:'<fmt:message key="label.lookup"/>',
  	width:6
},
{ columnId:"fieldSalesRepName",
  columnName:'<fmt:message key="label.fieldsalesrep"/>',  
  tooltip:"Y"
},
{
  	columnId:"fieldSalesRepLookup",
  	<c:if test="${(adminPermissions== true) or (perm2== true)}">
  	  	columnName:'#cspan',
  	</c:if>  	  	
//  	attachHeader:'<fmt:message key="label.lookup"/>',
  	width:6
},
	{
	  	columnId:"opsEntityId"
	},
	{
	  	columnId:"opsEntityName"
//		  	,
//		columnName:"<fmt:message key="label.operatingentity"/>",
//	    onChange:shipOpsChanged,
//	  	type:"hcoro",
//		width:20
	},
{ columnId:"inventoryGroup",
  columnName:'<fmt:message key="label.globaldefaultinventorygroup"/>',
  width:12,
  tooltip:"Y"
},
{
	columnId:"shiptoStatus",
	columnName:'<fmt:message key="label.active"/>',
	type:"hchstatus",
	align:"center",
	submit:true		
},
{
	columnId:"shiptoNotes",
	columnName:'<fmt:message key="label.shiptonotes"/>',
	<c:if test="${(adminPermissions== true) or (perm2== true)}">
	type:"txt",
	</c:if>
	width:10
},
{
	columnId:"msdsLocaleOverride",
	columnName:'<fmt:message key="label.msdslocaleoverride"/>',
	type:"hcoro",
    width:15
},
{
	columnId:"updatedByName",
	columnName:'<fmt:message key="label.updatedby"/>',
	tooltip:"Y",
    width:15
},
{
	columnId:"updatedOn",
	columnName:'<fmt:message key="label.updatedon"/>',
	hiddenSortingColumn:'hiddenUpdatedOnDateTime',
	sorting:'int'
},
{ 
	  columnId:"hiddenUpdatedOnDateTime",
	  sorting:'int'
},
{ 
	  columnId:"status",
	  submit:true	
},
{
	columnId:"facilityId",
	submit:true		
},
{
	columnId:"shipToLocationId",
	submit:true		
},
{
	columnId:"companyId",
	submit:true		
},
{  
	columnId:"shipInfoChange",
	submit:true	
}
];

var ddddInventoryGroup = new Array(
		{text:'Please select',value:''}
		);
var opsEntityId = new Array();
var defaultInventoryGroup = new Array()
var facilityPriceGroupId = new Array();

function shipOpsChanged(rowId,columnId,invval) {
	
	  var selectedOps = gridCellValue(shiptoGrid,rowId,columnId);
	  var inv = $("defaultInventoryGroup"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewIgValudset(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.all,"", "defaultInventoryGroup"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "defaultInventoryGroup"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
// facilityPriceGroupId	  
	  var inv = $("facilityPriceGroupId"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildNewpriceGroupId(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.pleaseselect,"", "facilityPriceGroupId"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "facilityPriceGroupId"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}

// inv hcoro build
function buildNewIgValudset(opsid) {
//	var opsid = $v('opsEntityId');
	var newInvArray = new Array();
	if( !opsid ) newInvArray = ddddInventoryGroup;
	for( i=0;i < opshubig.length; i++) {
		if( opshubig[i].id == opsid ) {
			var hubcoll = opshubig[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				var igcoll = hubcoll[j].coll;
				for( k=0;k< igcoll.length;k++ ){
					newInvArray[newInvArray.length] = {text:igcoll[k].name,value:igcoll[k].id};
				}
			}	
		}
	}
	return newInvArray;
	//defaultInventoryGroup = newInvArray;
	//inventoryGroup = newInvArray;
	if( !beginning ) { 
		if(	shiptoGrid.getRowsNum() != 0 || carrierGrid.getRowsNum() != 0 ) { 
			for( rowId in shiptorowids ) {
					  var elename =	"defaultInventoryGroup"+rowId;
					  var inv = $(elename);
					  for (var i = inv.length; i > 0; i--) {
						  inv[i] = null;
					  }
					  if(defaultInventoryGroup.length == 0) {
					    setOption(0,messagesData.pleaseselect,"", elename);
					  }
					  for (var i=0; i < defaultInventoryGroup.length; i++) {
					    setOption(i,defaultInventoryGroup[i].text,defaultInventoryGroup[i].value, elename);
					  }
			}
//			for( rowId in carrierrowids ) {
//				  var elename =	"inventoryGroup"+rowId;
//				  var inv = $(elename);
//				  for (var i = inv.length; i > 0; i--) {
//					  inv[i] = null;
//				  }
//				  if(inventoryGroup.length == 0) {
//				    setOption(0,messagesData.pleaseselect,"", elename);
//				  }
//				  for (var i=0; i < inventoryGroup.length; i++) {
//				    setOption(i,inventoryGroup[i].text,inventoryGroup[i].value, elename);
//				  }
//			}			
		}
	}
}

// price group stuff
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
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="AccountsReceivable">
<c:set var="hasAutoEmailPerm" value="Y"/>
</tcmis:opsEntityPermission>
	    <c:set var="curops" value="${status.current.opsEntityId}"/>
	    <c:if test="${ preops ne curops}">
	    ${opssep}
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>',
		  companyId:'${status.current.opsCompanyId}',
          hasPerm:'${hasPerm}',
          coll: [ 
        	 	  {  id: '${status.current.priceGroupId}',
           	 	   name: '<tcmis:jsReplace value="${status.current.priceGroupName}"/>',
//           	 	     name:'${status.current.priceGroupId}',
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

var msdsLocaleOverride = 
	new Array( 
		{text:messagesData.pleaseselect,value:''}
		<c:forEach var="cbean" items="${vvLocale}" varStatus="status"> 
		,{text:'<tcmis:jsReplace value="${cbean.localeDisplay}"/>',value:'<tcmis:jsReplace value="${cbean.localeCode}"/>'}
		</c:forEach>  	
		);  

function buildNewpriceGroupId(opsid,priceid,permission) {
//	var opsid = $v('opsEntityId');
	var newInvArray = new Array();
	if(permission == 'Y')
		newInvArray[newInvArray.length] = {text:messagesData.pleaseselect,value:pgdefaults.pg.id};
	else
		newInvArray[newInvArray.length] = {text:"",value:pgdefaults.pg.id};
	for( i=0;i < opspg.length; i++) {
//		if( opspg[i].id == opsid && opspg[i].hasPerm == 'Y' ) {
		if( opspg[i].id == opsid  ) {
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

// hcoro array ini

<c:forEach var="shipToBean" items="${shipToBeanColl}" varStatus="status">
//defaultInventoryGroup[${status.index +1}] = buildNewIgValudset('${shipToBean.opsEntityId}');
<c:set var="priceListPerm" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="priceGroupAssignment">
<c:set var="priceListPerm" value="Y"/>
facilityPriceGroupId[${status.index +1}] = buildNewpriceGroupId('${shipToBean.opsEntityId}','${shipToBean.facilityPriceGroupId}','Y');
</tcmis:opsEntityPermission>

<c:if test="${priceListPerm == 'N'}">
facilityPriceGroupId[${status.index +1}] = buildNewpriceGroupId('${shipToBean.opsEntityId}','${shipToBean.facilityPriceGroupId}','N');
</c:if>
</c:forEach>

<c:if test="${!empty shipToBeanColl}">
<c:set var="shipToDataCount" value='${0}'/>
var shipToData = new Array();
var shipToData = {
rows:[
<c:forEach var="shipToBean" items="${shipToBeanColl}" varStatus="status">

<c:set var="shipToRowId" value="${status.index +1}"/>

<c:if test="${status.index > 0}">,</c:if>

<tcmis:jsReplace var="locationDesc" value="${status.current.locationDesc}"  processMultiLines="true"/>

<tcmis:jsReplace value="${shipToBean.salesAgentName}" var="salesAgentName"/>
<tcmis:jsReplace value="${shipToBean.fieldSalesRepName}" var="fieldSalesRepName"/>

<c:if test="${readonly == 'true'}">
<c:set var="salesAgentDisplay" value=""/>
<c:set var="fieldSalesRepDisplay" value=""/>
</c:if>

<c:if test="${readonly != 'true'}">
<c:set var="salesAgentDisplay">
<input class="lookupBtn" type="button" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" onclick="getAgent(${shipToRowId})"/>
<input class="smallBtns" type="button" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearAgent(${shipToRowId})"/> 
</c:set>
<tcmis:jsReplace var="salesAgentDisplay" value="${salesAgentDisplay}"  processMultiLines="true" />

<c:set var="fieldSalesRepDisplay">
<input class="lookupBtn" type="button" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" value="" onclick="getRep(${shipToRowId})"/>
<input class="smallBtns" type="button" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" value="<fmt:message key="label.clear"/>" onclick="clearRep(${shipToRowId})"/> 
</c:set>
<tcmis:jsReplace var="fieldSalesRepDisplay" value="${fieldSalesRepDisplay}"  processMultiLines="true" />

</c:if>


<tcmis:jsReplace var="shipToAddressLine1Display" value="${status.current.shipToAddressLine1Display}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine2Display" value="${status.current.shipToAddressLine2Display}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine3Display" value="${status.current.shipToAddressLine3Display}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine4Display" value="${status.current.shipToAddressLine4Display}"  processMultiLines="true"/>
<tcmis:jsReplace var="shipToAddressLine5Display" value="${status.current.shipToAddressLine5Display}"  processMultiLines="true"/>

<c:set var="shiptoStatus" value="false"/>
<c:if test="${status.current.status eq 'A'}"><c:set var="shiptoStatus" value="true"/></c:if>

<c:forEach var="cbean" items="${vvpriceListCollection}" >
<c:if test="${status.current.facilityPriceGroupId eq cbean.priceGroupId}">
	<tcmis:jsReplace value="${cbean.priceGroupName}" var="shiptoPriceGroupName"/>
</c:if>
</c:forEach>

<c:set var="hasPerm" value='N'/>
<c:if test="${(adminPermissions == true) or (perm2 == true)}">
<tcmis:jsReplace value="${status.current.facilityPriceGroupId}" var="shiptoPriceGroupName"/>
<c:set var="hasPerm" value='Y'/>
</c:if>

<c:set var="priceListPerm" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="priceGroupAssignment">
<c:set var="priceListPerm" value="Y"/>
</tcmis:opsEntityPermission>

<c:if test="${hasPerm == 'N'}"><c:set var="priceListPerm" value="N"/></c:if>

/* fmt:formatDate, to formate your date to locale pattern */
/* pattern="${dateFormatPattern}" or pattern="${dateTimeFormatPattern}" */
<fmt:formatDate var="fmtUpdatedOnDate" value="${status.current.updatedOn}" pattern="${dateFormatPattern}"/>

/* Get time for hidden date column. This is for sorting purpose. */
<c:set var="sortUpdatedOnDateTime" value="${status.current.updatedOn.time}"/>



<tcmis:jsReplace var="shiptoNotes" value="${status.current.shiptoNotes}"  processMultiLines="true"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},

 <c:if test="${status.current.status eq 'I'}">'class':"grid_black",</c:if>
 
 data:['${hasPerm}','${priceListPerm}','<tcmis:jsReplace value="${status.current.locationShortName}" />','${locationDesc}',
 '${shipToAddressLine1Display} ${shipToAddressLine2Display} ${shipToAddressLine3Display} ${shipToAddressLine4Display}  ${shipToAddressLine5Display}',
 '${shiptoPriceGroupName}','${status.current.salesAgentId}','${status.current.fieldSalesRepId}','${salesAgentName}','${salesAgentDisplay}','${fieldSalesRepName}','${fieldSalesRepDisplay}',
 '${status.current.opsEntityId}','<tcmis:jsReplace value="${status.current.opsEntityName}" />',
 '<tcmis:jsReplace value="${status.current.inventoryGroupName}" />',
 ${shiptoStatus},'${shiptoNotes}',
 '${status.current.msdsLocaleOverride}','${status.current.updatedByName}','${fmtUpdatedOnDate}','${sortUpdatedOnDateTime}','${status.current.status}','${status.current.facilityId}','${status.current.shipToLocationId}','${status.current.companyId}',false]}
<c:set var="shipToDataCount" value='${shipToDataCount+1}'/>
 </c:forEach>
]};

var shipMap = {};
<c:set var="mapDataCount" value='${0}'/>
<c:forEach var="bean2" items="${shipToBeanColl}" varStatus="statusMap2">
<tcmis:jsReplace value="${bean2.salesAgentName}" var="salesAgentName"/>
<tcmis:jsReplace value="${bean2.fieldSalesRepName}" var="fieldSalesRepName"/>
<tcmis:jsReplace value="${statusMap2.current.facilityPriceGroupId}" var="shiptoPriceGroupName"/>
<c:set var="shiptoStatus" value="false"/>
<c:if test="${statusMap2.current.status eq 'A'}"><c:set var="shiptoStatus" value="true"/></c:if>
<tcmis:jsReplace var="shiptoNotes" value="${statusMap2.current.shiptoNotes}"  processMultiLines="true"/>
shipMap['shiptoPriceGroupName' + '${mapDataCount}'] =   '${shiptoPriceGroupName}';
shipMap['salesAgentName' + '${mapDataCount}'] =   '${salesAgentName}';
shipMap['fieldSalesRepName' + '${mapDataCount}'] =   '${fieldSalesRepName}';
shipMap['shiptoStatus' + '${mapDataCount}'] =   '${shiptoStatus}';
shipMap['msdsLocaleOverride' + '${mapDataCount}'] = '${statusMap2.current.msdsLocaleOverride}';
shipMap['shiptoNotes' + '${mapDataCount}'] = '${shiptoNotes}';
<c:set var="mapDataCount" value='${mapDataCount+1}'/>
</c:forEach>
shipMap['count'] =   '${mapDataCount}';
</c:if>


var carrierConfig = [
{ columnId:"carrierName",
  columnName:'<fmt:message key="label.carriername"/>', 
  width:15, 
  tooltip:"Y"
},
{ columnId:"account",
  columnName:'<fmt:message key="label.account"/>'
},
{ columnId:"notes",
  columnName:'<fmt:message key="label.notes"/>', 
  width:25, 
  tooltip:"Y"
},
{ columnId:"inventoryGroup",
  columnName:'',  
  tooltip:"Y"
},
{ columnId:"carrierCode"	 
}
];


<c:if test="${!empty carrierInfoViewBeanColl}">
<c:set var="carrierDataCount" value='${0}'/>
var carrierData = new Array();
var carrierData = {
rows:[
<c:forEach var="carrierInfoViewBeanColl" items="${carrierInfoViewBeanColl}" varStatus="status">

<c:if test="${status.index > 0}">,</c:if>

<tcmis:jsReplace var="notes" value="${status.current.notes}"  processMultiLines="true"/>

 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['<tcmis:jsReplace value="${status.current.carrierName}"/>','${status.current.account}','${notes}',
 '<tcmis:jsReplace value="${status.current.inventoryGroupName}"/>','${status.current.carrierCode}']}
<c:set var="carrierDataCount" value='${carrierDataCount+1}'/>
 </c:forEach>
]};
</c:if>

function stopwait() {
stopPleaseWait();  
}

var billingMap = {};
var otherMap = {};

<c:set var="selectedCountry" value='${param.countryAbbrev}'/>
<c:if test="${empty selectedCountry}">
	<c:set var="selectedCountry" value='${customerBean.countryAbbrev}'/>
</c:if>
<c:if test="${empty selectedCountry}">
	<c:set var="selectedCountry" value='USA'/>
	<tcmis:isCNServer>
		<c:set var="selectedCountry" value='CHN'/>
	</tcmis:isCNServer> 
</c:if>

<c:set var="selectedState" value='${param.stateAbbrev}'/>
<c:if test="${empty selectedState}">
  <c:set var="selectedState" value='${customerBean.stateAbbrev}'/>
</c:if>

<c:set var="industryKey" value='${param.industry}'/>
<c:if test="${empty industry}">
	<c:set var="industryKey" value='${customerBean.industry}'/>
</c:if>

<c:if test="${customerBean.exitLabelFormat eq vvBean[0]}">
	<c:set var="exitLabelFormat" value='${vvBean[0]}'/>
</c:if>
<tcmis:jsReplace var="customerName" value="${customerBean.customerName}"  processMultiLines="false"/>
<tcmis:jsReplace var="addressLine1" value="${customerBean.addressLine1}"  processMultiLines="false"/>

function mapPrevFrom(){

	billingMap['customerName'] = '<tcmis:jsReplace value="${customerName}" processMultiLines="true"/>';
	billingMap['addressLine1'] = '<tcmis:jsReplace value="${addressLine1}" processMultiLines="true"/>';
	billingMap['addressLine2'] = '<tcmis:jsReplace value="${customerBean.addressLine2}" processMultiLines="true"/>';
	billingMap['addressLine3'] = '<tcmis:jsReplace value="${customerBean.addressLine3}" processMultiLines="true"/>';
	billingMap['addressLine4'] = '<tcmis:jsReplace value="${customerBean.addressLine4}" processMultiLines="true"/>';
	billingMap['addressLine5'] = '<tcmis:jsReplace value="${customerBean.addressLine5}" processMultiLines="true"/>';
	//billingMap[invoiceConsolidation] =
	billingMap['countryAbbrev'] = '${selectedCountry}';
	billingMap['stateAbbrev'] = '${selectedState}';
	billingMap['city'] = '<tcmis:jsReplace value="${customerBean.city}" processMultiLines="true"/>';
	billingMap['zip'] = '<tcmis:jsReplace value="${customerBean.zip}" processMultiLines="true"/>';
	billingMap['taxRegistrationNumber'] =  '<tcmis:jsReplace value="${customerBean.taxRegistrationNumber}" processMultiLines="true"/>';
	billingMap['taxRegistrationNumber2'] =  '<tcmis:jsReplace value="${customerBean.taxRegistrationNumber2}" processMultiLines="true"/>';
	billingMap['taxRegistrationNumber3'] = '<tcmis:jsReplace value="${customerBean.taxRegistrationNumber3}" processMultiLines="true"/>';
	billingMap['taxRegistrationNumber4'] = '<tcmis:jsReplace value="${customerBean.taxRegistrationNumber4}" processMultiLines="true"/>';
	billingMap['creditLimit'] = '${customerBean.creditLimit}';
	billingMap['overdueLimit'] = '${customerBean.overdueLimit}';
	billingMap['creditStatus'] = '${customerBean.creditStatus}';
	billingMap['paymentTerms'] = '${customerBean.paymentTerms}';
	billingMap['abcClassification'] = '${customerBean.abcClassification}';	
	billingMap['autoEmailInvoice'] = '${customerBean.autoEmailInvoice}';
	billingMap['autoEmailAddress'] = '${customerBean.autoEmailAddress}';
	billingMap['autoEmailBatchSize'] = '${customerBean.autoEmailBatchSize}';
	var priceGroupId =  document.getElementById('priceGroupId');
	otherMap['priceGroupId'] = $v('priceGroupId');//priceGroupId.options[priceGroupId.selectedIndex].value;
	otherMap['sapIndustryKey'] = '${customerBean.sapIndustryKey}';
	otherMap['industryKey'] = '${industryKey}';
	otherMap['fieldSalesRepName'] = '<tcmis:jsReplace value="${customerBean.fieldSalesRepName}" processMultiLines="true"/>';
	otherMap['notes'] = '<tcmis:jsReplace value="${customerBean.notes}" processMultiLines="true"/>';
	otherMap['taxNotes'] = '<tcmis:jsReplace value="${customerBean.taxNotes}" processMultiLines="true"/>';
	otherMap['chargeFreight'] = '${customerBean.chargeFreight}';
	otherMap['shelfLifeRequired'] = '${customerBean.shelfLifeRequired}';
	otherMap['emailOrderAck'] = '${customerBean.emailOrderAck}';
	otherMap['exitLabelFormat'] = '${exitLabelFormat}';
	otherMap['shipMfgCoc'] = '${customerBean.shipMfgCoc}';
	otherMap['shipMsds'] = '${customerBean.shipMsds}';
	otherMap['shipComplete'] = '${customerBean.shipComplete}';
}
// -->
</script>

<title>
<fmt:message key="customerdetails.title"/>
</title>
</head>

<body bgcolor="#ffffff" onload="stopwait();setCountry();loadGrids();mapPrevFrom();" onunload="try{closeAllchildren();}catch(ex){}"  onresize="resizeWin();"> 
<%--NEw -Transit Page Starts --%>
<tcmis:form action="/customerupdate.do" onsubmit="return submitSearchOnlyOnce();">
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
  <br/><br/><br/><br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
<div class="interface" id="mainPage" style="">

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>   
//-->
</script>

<!-- Search Frame Begins -->
<div id="searchSectionDiv" style="margin: 4px 4px 0px 4px;">
<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showDivRow" style="display: none;">

<td class="optionTitleBoldLeft"> <fmt:message key="customerdetails.title"/></td>
<td colspan="6"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="showDiv();">
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>

<tr id="hideDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideDiv();">
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>

</table>

<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" >
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
      <fmt:formatDate var="fmtBillingDate" value="${customerBean.updatedBillingDataOn}" pattern="${dateFormatPattern}"/>
     <fmt:formatDate var="fmtOtherDate" value="${customerBean.updatedOn}" pattern="${dateFormatPattern}"/>
   <div class="roundContent">    
    <div class="boxhead"> <%-- boxhead Begins --%>
      <div id="mainUpdateLinks" > <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink">
      <a href="javascript:updateCustomer()"><fmt:message key="label.update"/></a></span>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.billingupdatedby"/>:&nbsp;${customerBean.updatedBillingDataByName}
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.billingupdatedon"/>:&nbsp;${fmtBillingDate}
  	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.updatedby"/>:&nbsp;${customerBean.updatedByName}
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.updatedon"/>:&nbsp;${fmtOtherDate}
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

  <c:set var="priceGroupId" value='${param.priceGroupId}'/>
  <c:if test="${empty priceGroupId}">
    <c:set var="priceGroupId" value="${customerBean.priceGroupId}"/>
  </c:if>
       
   <c:set var="industry" value='${param.industry}'/>
   <c:if test="${empty industry}">
     <c:set var="industry" value='${customerBean.industry}'/>
   </c:if>

  
    <!-- Insert all the search option within this div -->
    <table width="980" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
    <td class="optionTitleBoldRight" width="5%" nowrap><fmt:message key="label.name"/>: </td>
   
    <c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft" width="20%">
    	<input name="customerName" id="customerName" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.customerName}" />"   size="35" maxlength="50"/>&nbsp;(${customerBean.customerId})
      </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft" width="20%">
		${customerBean.customerName}&nbsp;(${customerBean.customerId})  
		&nbsp;<input name="customerName" id="customerName" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.customerName}" />"/>
   </td>
    </c:if>     
    
    <td class="optionTitleBoldRight" width="15%"><fmt:message key="label.company"/>:</td>
     
    <c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft" width="20%">  
   	<input name="billToCompanyName" id="billToCompanyName" type="text" class="invGreyInputText" value="<tcmis:inputTextReplace value="${customerBean.billToCompanyName}" />" readonly/>	
     </td>
    </c:if>   
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft" width="20%" >  
     ${customerBean.billToCompanyName}
    	&nbsp;<input name="billToCompanyName" id="billToCompanyName" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.billToCompanyName}" />"/>	
   </td>
    </c:if>     
  
   
    <c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft" width="5%">
     <select name="taxRegistrationType" id="taxRegistrationType" class="selectBox" onChange="">
        <c:if test="${empty customerBean.taxRegistrationNumber}">
         <c:set var="noselection" value='true'/>
        <option value=""></option>
        </c:if>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerBean.taxRegistrationType eq cbean.taxType}">
	 	   	 <c:if test="${!noselection}">
	 	   	 selected
	 	   	 </c:if>
	 	   	</c:if>>${cbean.taxType}</option>
	 	   	
    	</c:forEach>
    	</select>
    	  </td>
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleBoldRight" width="10%">
		<input type="hidden" name="taxRegistrationType" id="taxRegistrationType" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationType}" />"/>
		<tcmis:inputTextReplace value="${customerBean.taxRegistrationType}" />
      </td>
    </c:if>
  
    
    <td class="optionTitleBoldRight"><fmt:message key="label.number"/>: </td>
   
    <c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft" >
    <input name="taxRegistrationNumber" id="taxRegistrationNumber" class="inputBox" maxlength="30" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber}" />"/>
     </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleBoldLeft" >
	<input type="hidden" name="taxRegistrationNumber" id="taxRegistrationNumber" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber}" />"/>
	<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber}" />
	</td>
    </c:if> 
   
    
    </tr>
    <tr>
    
    
  	<td class="optionTitleBoldRight"><fmt:message key="label.fullbilltoaddress"/>: </td>
   
    <c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft" width="2%" >
  	    <%-- <span style='font-size:12.0pt;color:red'>*</span>--%>
  	    <input name="addressLine1" id="addressLine1" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.addressLine1}" />" size="35" maxlength="50"/>
    </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft"  width="2%" >
    	<tcmis:inputTextReplace value="${customerBean.addressLine1}" />
    	&nbsp;<input name="addressLine1" id="addressLine1" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.addressLine1}" />"/>
    </td>
    </c:if>     
      <td class="optionTitleBoldRight" width="2%"><fmt:message key="label.country"/>: </td>
   
    <c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft">
     	<select name="countryAbbrev" id="countryAbbrev" class="selectBox" onChange="countryChanged()">
     	</select>
     	 </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft" >
     	 <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">
            <c:if test="${status.current.countryAbbrev == selectedCountry}" >
              	${status.current.country}
            </c:if>
    </c:forEach>
    	
    	&nbsp;<input name="countryAbbrev" id="countryAbbrev" type="hidden"  value="${customerBean.countryAbbrev}"/>
    	</td>
    </c:if>   
   
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
    <select name="taxRegistrationType2" id="taxRegistrationType2" class="selectBox" onChange="">
     <c:if test="${empty customerBean.taxRegistrationNumber2}">
       <option value=""></option>
       <c:set var="noselection2" value='true'/>
       
        </c:if>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerBean.taxRegistrationType2 eq cbean.taxType}">
	 	   	 <c:if test="${!noselection2}">
	 	   	 selected
	 	   	 </c:if>
	 	   	</c:if>>${cbean.taxType}</option>
    	</c:forEach>
    	</select>
    	</td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleBoldRight">
		<input type="hidden" name="taxRegistrationType2" id="taxRegistrationType2" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationType2}" />"/>
		<tcmis:inputTextReplace value="${customerBean.taxRegistrationType2}" />
   </td>
    </c:if>
    
    
    <td class="optionTitleBoldRight"><fmt:message key="label.number"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft" >
    <input name="taxRegistrationNumber2" id="taxRegistrationNumber2"  maxlength="30" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber2}" />"/>
    </td>
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft" >
	<input type="hidden" name="taxRegistrationNumber2" id="taxRegistrationNumber2" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber2}" />"/>
	<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber2}" />
	</td>
    </c:if>  
     
     
    </tr>
    <tr>
 	<%-- in rowspan
    <td class="optionTitleBoldRight"></td>
    --%>
     <td class="optionTitleBoldRight">&nbsp;</td>    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
    	<input name="addressLine2" id="addressLine2" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.addressLine2}" />" size="35" maxlength="50"/>
    </td>	
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft">
    	${customerBean.addressLine2}
    	&nbsp;<input name="addressLine2" id="addressLine2" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.addressLine2}" />"/>
    </td>	
    </c:if>   
    
    <td class="optionTitleBoldRight"><fmt:message key="label.state"/>/<fmt:message key="label.region"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
     	<select name="stateAbbrev" id="stateAbbrev" class="selectBox">
     	</select>
     	 </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft">
     <c:forEach var="vvCountyBean" items="${vvCountryBeanCollection}" varStatus="status">
            <c:set var="currentCountry" value='${status.current.countryAbbrev}'/>
            <c:choose>
              <c:when test="${empty selectedCountry}" >
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
              <c:when test="${currentCountry == selectedCountry}" >
                <c:set var="stateCollection" value='${status.current.stateCollection}'/>
              </c:when>
            </c:choose>
          </c:forEach>
     
    	 <c:forEach var="vvStateBean" items="${stateCollection}" varStatus="stateStatus">
              <c:set var="currentState" value='${stateStatus.current.stateAbbrev}'/>
              <c:if test="${currentState == selectedState}">
                  ${stateStatus.current.state}
              </c:if>             
         </c:forEach>
    	&nbsp;<input name="stateAbbrev" id="stateAbbrev" type="hidden"  value="${customerBean.stateAbbrev}"/>
    	</td>
    </c:if>
   

    <td class="optionTitleBoldLeft" width="2%">
    <c:if test="${adminPermissions}">
        <select name="taxRegistrationType3" id="taxRegistrationType3" class="selectBox" onChange="">
         <c:if test="${empty customerBean.taxRegistrationNumber3}">
        <option value=""></option>
        <c:set var="noselection3" value='true'/>
        </c:if>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerBean.taxRegistrationType3 eq cbean.taxType}">
	 	   <c:if test="${!noselection3}">
	 	   	 selected
	 	   	 </c:if>
	 	   	</c:if>>${cbean.taxType}</option>
    	</c:forEach>
    	</select>
    </c:if>
    <c:if test="${!adminPermissions}">
		<input type="hidden" name="taxRegistrationType3" id="taxRegistrationType3" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationType3}" />"/>
	${customerBean.taxRegistrationType3}
    </c:if>
    </td>
    
    <td class="optionTitleBoldRight"><fmt:message key="label.number"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft" >
    <input name="taxRegistrationNumber3" id="taxRegistrationNumber3" class="inputBox"  maxlength="30" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber3}" />"/>
    </td>
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft" >
	<input type="hidden" name="taxRegistrationNumber3" id="taxRegistrationNumber3" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber3}" />"/>
	${customerBean.taxRegistrationNumber3}
	</td>
    </c:if>
  
    </tr>
    <tr>
	<td class="optionTitleBoldRight">&nbsp;</td>
	<%-- in rowspan    
	--%>
	
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
    	<input name="addressLine3" id="addressLine3" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.addressLine3}" />" size="35" maxlength="50"/>
    </td>	
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft">
    	${customerBean.addressLine3}
    	&nbsp;<input name="addressLine3" id="addressLine3" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.addressLine3}" />"/>
    </td>	
    </c:if>
   
	
	    
      <td class="optionTitleBoldRight"><fmt:message key="label.city"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
    	<input name="city" id="city" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.city}" />"/>
    	  </td> 	
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft">
    	${customerBean.city}
    	&nbsp;<input name="city" id="city" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.city}" />"/>
    	</td>
    </c:if>
  
	

    <td class="optionTitleBoldLeft" width="2%">
    <c:if test="${adminPermissions}">
    <select name="taxRegistrationType4" id="taxRegistrationType4" class="selectBox" onChange="">
    	 <c:if test="${empty customerBean.taxRegistrationNumber4}">
        <option value=""></option>
        <c:set var="noselection4" value='true'/>
        </c:if>
    	<c:forEach var="cbean" items="${vvTaxTypeColl}" varStatus="status">
	 	   	<option value="${cbean.taxType}" <c:if test="${customerBean.taxRegistrationType4 eq cbean.taxType}">
	 	   	 <c:if test="${!noselection4}">
	 	   	 selected
	 	   	 </c:if>	 	   	
	 	   	</c:if>>${cbean.taxType}</option>
    	</c:forEach>
    	</select>
    </c:if>
    <c:if test="${!adminPermissions}">
    	<%-- This is the original one with a different view bean. Guess it's just a typo.
    	<input type="hidden" name="taxRegistrationType4" id="taxRegistrationType4" value="${customerAddRequestViewBean.taxRegistrationType4}"/> --%>
		<input type="hidden" name="taxRegistrationType4" id="taxRegistrationType4" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationType4}" />"/>
		<tcmis:inputTextReplace value="${customerBean.taxRegistrationType4}" />
    </c:if>
    </td>
    
    <td class="optionTitleBoldRight"><fmt:message key="label.number"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft" width="2%">
    <input name="taxRegistrationNumber4" id="taxRegistrationNumber4"  maxlength="30" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber4}" />"/>
     </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft" width="2%">
	<input type="hidden" name="taxRegistrationNumber4" id="taxRegistrationNumber4" value="<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber4}" />"/>
	<tcmis:inputTextReplace value="${customerBean.taxRegistrationNumber4}" />
	</td>
    </c:if>
   
    </tr>
    <tr>
	<%-- in rowspan    
	--%>
    <td class="optionTitleBoldRight">&nbsp;</td>	
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
    	<input name="addressLine4" id="addressLine4" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.addressLine4}" />" size="35" maxlength="50"/>
    </td>	
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft">
    	${customerBean.addressLine4}
    	&nbsp;<input name="addressLine4" id="addressLine4" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.addressLine4}" />"/>
    </td>	
    </c:if>
    
    
    <td class="optionTitleBoldRight"><fmt:message key="label.postalcode"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
    	<input name="zip" id="zip" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.zip}" />" size="10"/>
    	<%--<span id="zipPlusSpan" style="display:none">
    -<input type="text" class="inputBox" name="zipPlus" id="zipPlus" value="${customerBean.zipPlus}" size="4"/>
		</span>--%>
		  </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft">
    	${customerBean.zip} 
    	&nbsp;<input name="zip" id="zip" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.zip}" />"/>
    	</td>
    </c:if>    	
      
     <td class="optionTitleLeft" colspan="3">
     <c:choose>
     <%--  used to be (selectedCountry =='USA' or (selectedCountry =='Canada')) , now we allow this for everybody--%>
     <c:when test="${ true }">    
   <a href="javascript:openCustTaxExempCert()"><fmt:message key="label.taxexemptioncertificate"/></a>
   </c:when>   
   <c:otherwise>
   &nbsp;
   </c:otherwise>
   </c:choose>
     
     </td>
    </tr>
    <tr>
    <td class="optionTitleBoldRight">&nbsp;</td>
	
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
    	<input name="addressLine5" id="addressLine5" type="text" class="inputBox" value="<tcmis:inputTextReplace value="${customerBean.addressLine5}" />" size="35" maxlength="50"/>
    	</td>
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft">
    	<tcmis:inputTextReplace value="${customerBean.addressLine5}" />
    	&nbsp;<input name="addressLine5" id="addressLine5" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.addressLine5}" />"/>
     </td>	
    </c:if>
    
       <td class="optionTitleBoldRight"><fmt:message key="label.defaultshelflife"/>: </td>
    
    <c:if test="${perm2}">
    <td class="optionTitleBoldLeft">
    	<input name="shelfLifeRequired" id="shelfLifeRequired" type="text" class="inputBox" value="${customerBean.shelfLifeRequired}" size="6"/>&nbsp;%
     </td>
    </c:if>
    <c:if test="${!perm2}">
     <td class="optionTitleLeft">
    	${customerBean.shelfLifeRequired}
    	&nbsp;<input name="shelfLifeRequired" id="shelfLifeRequired" type="hidden"  value="${customerBean.shelfLifeRequired}"/>
  		</td>
	 </c:if>
   <%--
    <td class="optionTitleBoldRight"><fmt:message key="label.material"/> <fmt:message key="label.currency"/>: </td>   

    <c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft" colspan="2">
    <select name="currencyId" id="currencyId" class="selectBox">
    	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	 	   	<option value="${cbean.currencyId}" <c:if test="${customerBean.currencyId eq cbean.currencyId}">selected</c:if>>${cbean.currencyId}</option>
    	</c:forEach>
    </select>
     </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft" colspan="2">
		${customerBean.currencyId}
		&nbsp;<input name="currencyId" id="currencyId" type="hidden"  value="${customerBean.currencyId}"/>
		</td>
    </c:if>
  --%>
       <td class="optionTitleBoldRight"><fmt:message key="label.exitlabelformat"/>: </td>

    <c:if test="${perm2}">
    <td class="optionTitleBoldLeft" colspan="2">
       <select name="exitLabelFormat" id="exitLabelFormat" class="selectBox" onChange="">
 	   	<option value=""><fmt:message key="label.pleaseselect"/></option>
    	<c:forEach var="vvBean" items="${labelColl[2]}" varStatus="status">
	 	   	<option value="${vvBean[0]}" <c:if test="${customerBean.exitLabelFormat eq vvBean[0]}">selected</c:if>>${vvBean[0]}</option>
    	</c:forEach>
    	</select>
    </td> 
    </c:if>
    <c:if test="${!perm2}">
    <td class="optionTitleLeft" colspan="2">
		${customerBean.exitLabelFormat}
		&nbsp;<input name="exitLabelFormat" id="exitLabelFormat" type="hidden"  value="${customerBean.exitLabelFormat}"/>
	</td>
    </c:if>

    </tr>    
     
    <tr>
    
    <td class="optionTitleBoldRight" colspan="2" nowrap ><fmt:message key="label.acceptseinvoices"/>:
	   <input name="autoEmailInvoice" id="autoEmailInvoice" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.autoEmailInvoice eq 'Y'}">checked</c:if>   <c:if test="${hasAutoEmailPerm != 'Y'}"> disabled</c:if>>	   
	   <fmt:message key="label.einvoicesbatchsize"/>:        
	   <select name="autoEmailBatchSize" id="autoEmailBatchSize" class="selectBox" <c:if test="${hasAutoEmailPerm != 'Y'}"> disabled</c:if>>
 			<option value="" <c:if test="${'' == customerBean.autoEmailBatchSize}">selected</c:if>></option>
	   		<option value="1" <c:if test="${1 == customerBean.autoEmailBatchSize}">selected</c:if>>1</option>  
		    <c:forEach begin="5" end="25" varStatus="loop" step="5">
		  			<option value="${loop.index}" <c:if test="${loop.index == customerBean.autoEmailBatchSize}">selected</c:if>>${loop.index}</option> 
			</c:forEach> 
	    </select>
    </td>

      <td class="optionTitleBoldRight"><fmt:message key="label.consolidationmethod"/>:</td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft">
        <select name="invoiceConsolidation" id="invoiceConsolidation" class="selectBox"> 
        	<option value="At Shipment" selected>At Shipment</option>         
<%--    		<c:forEach var="ibean" items="${vvInvoiceConsolidationCollection}" varStatus="status">
	    		<option value="${ibean.invoiceConsolidation}" <c:if test="${customerBean.invoiceConsolidation eq ibean.invoiceConsolidation}">selected</c:if>>${ibean.invoiceConsolidationDesc}</option>
    		</c:forEach>  --%>
	    </select>
	   </td> 
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft">
        ${customerBean.invoiceConsolidation}
        &nbsp;<input name="invoiceConsolidation" id="invoiceConsolidation" type="hidden"  value="${customerBean.invoiceConsolidation}"/>
       </td> 
    </c:if>
   
   <td class="optionTitleBoldRight"><fmt:message key="label.shipcomplete"/>:</td>
   
    <c:if test="${perm2}">
     <td class="optionTitleBoldLeft">
    			<select name="shipComplete" id="shipComplete" class="selectBox">
    				<option value = "NO" <c:if test="${customerBean.shipComplete eq 'NO'}">selected</c:if>>No</option>
    				<option value = "LINE" <c:if test="${customerBean.shipComplete eq 'LINE'}">selected</c:if>>Line</option>
    				<option value = "ORDER" <c:if test="${customerBean.shipComplete eq 'ORDER'}">selected</c:if>>Order</option>
    			</select>
    			</td>
    </c:if>
    <c:if test="${!perm2}">
    <td class="optionTitleLeft">
    	${customerBean.shipComplete}
    	&nbsp;<input name="shipComplete" id="shipComplete" type="hidden"  value="${customerBean.shipComplete}"/>
    	</td>
    </c:if>
      
        <td class="optionTitleBoldRight"><%--<fmt:message key="label.tax"/> <fmt:message key="label.currency"/>: --%></td>
    
<%-- larry note: somehow   ${cbean.currencyDisplay} doesn't have anything --%>
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft" colspan="2">
<%--     <select name="taxCurrencyId" id="taxCurrencyId" class="selectBox">
    	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	 	   	<option value="${cbean.currencyId}" <c:if test="${customerBean.taxCurrencyId eq cbean.currencyId}">selected</c:if>>${cbean.currencyId}</option>
    	</c:forEach>
    </select>
--%>    
    </td> 
    </c:if>
    <c:if test="${!adminPermissions}">
    <td class="optionTitleLeft" colspan="2">
		${customerBean.taxCurrencyId}
		&nbsp;<input name="taxCurrencyId" id="taxCurrencyId" type="hidden"  value="${customerBean.taxCurrencyId}"/>
		</td>
    </c:if>
    
     
    </tr>
    <tr>  
    <td class="optionTitleBoldRight"><fmt:message key="label.einvoiceemailaddress"/> </td>
     <td class="optionTitleBoldLeft" >
    <input name="autoEmailAddress" id="autoEmailAddress" class="inputBox" maxlength="255" size="35" value="<tcmis:inputTextReplace value="${customerBean.autoEmailAddress}" />"/ <c:if test="${hasAutoEmailPerm != 'Y'}"> disabled</c:if>>
     </td>

	<td class="optionTitleBoldRight">
		<fmt:message key="label.customerglobalpricelist"/>:&nbsp;
	</td>
   
<c:set var="permPG" value="false"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${param.opsEntityId}"  userGroupId="priceGroupAssignment">
<c:set var="permPG" value="true"/>
</tcmis:opsEntityPermission>

    <c:if test="${permPG && perm2}">
     <td class="optionTitleBoldLeft">
				  <select name="priceGroupId" id="priceGroupId" class="selectBox">
				  <option value=""></option>
    				<c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	 	   				<option value="${cbean.priceGroupId}" <c:if test="${priceGroupId eq cbean.priceGroupId}">selected </c:if>>${cbean.priceGroupName}</option>
    				</c:forEach>
    			  </select>
    			   </td>
    </c:if>
    <c:if test="${! (permPG && perm2) }">
     <td class="optionTitleLeft">
     	<c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	 	   	<c:if test="${customerBean.priceGroupId eq cbean.priceGroupId}">${cbean.priceGroupName}</c:if>
    	</c:forEach>
    	&nbsp;<input name="priceGroupId" id="priceGroupId" type="hidden"  value="${customerBean.priceGroupId}"/>
    	</td>
    </c:if>
   
	<td class="optionTitleBoldRight">
    <fmt:message key="label.chargefreight"/>:    
    </td>
    <c:if test="${perm2}">
       <td class="optionTitleBoldLeft">
	   <input name="chargeFreight" id="chargeFreight" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.chargeFreight eq 'Y'}">checked</c:if>>
	    </td>
    </c:if>
	  <c:if test="${!perm2}">
	     <td class="optionTitleBoldLeft">
	   		<input  disabled name="sdfs" id="sdfs" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.chargeFreight eq 'Y'}">checked</c:if>>
	   	</td>
    </c:if>

	 
 	<td class="optionTitleBoldLeft" nowrap>
	<fmt:message key="label.creditlimit"/>:    
    <c:if test="${adminPermissions}">
    	<input name="creditLimit" id="creditLimit" type="text" class="inputBox" value="${customerBean.creditLimit}" size="6"/> <tcmis:isCNServer indicator="false">USD</tcmis:isCNServer><tcmis:isCNServer>CNY</tcmis:isCNServer>
    	&nbsp;<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
            name="None" value=""  OnClick="showCreditReview();return false;"><fmt:message key="creditreview.title"/> </button>
    </c:if>
    <c:if test="${!adminPermissions}">
    	${customerBean.creditLimit} <tcmis:isCNServer indicator="false">USD</tcmis:isCNServer><tcmis:isCNServer>CNY</tcmis:isCNServer>
    	&nbsp;<input name="creditLimit" id="creditLimit" type="hidden"  value="${customerBean.creditLimit}"/>
    </c:if>
     </td>   
    
    </tr>
    <tr>    	
    <td class="optionTitleBoldRight"><fmt:message key="label.industry"/>: </td>
  
    <c:if test="${perm2}">
      <td class="optionTitleBoldLeft">
    <select name="industry" id="industry" class="selectBox">
    	<c:forEach var="ibean" items="${vvIndustryCollection}" varStatus="status">
    	<option value="${ibean.industry}" <c:if test="${customerBean.industry eq ibean.industry}">selected</c:if>>${ibean.industryDescription}<%-- nothing yet.<fmt:message key="${ibean.industryLabel}"/>--%></option>
    	</c:forEach>
    </select>
     </td>  
    </c:if>
    <c:if test="${!perm2}">
     <td class="optionTitleLeft">
    	<c:forEach var="cbean" items="${vvIndustryCollection}" varStatus="status">
	  <c:if test="${industry eq cbean.industry}">
	     ${cbean.industryDescription}
      </c:if>
    </c:forEach>
    	&nbsp;<input name="industry" id="industry" type="hidden"  value="${customerBean.industry}"/>
    	</td>
    </c:if>
        
     <td  class="optionTitleBoldRight">
    <fmt:message key="label.emailorderack"/>:</td>
    <td class="optionTitleBoldLeft" width="8%" >
    <c:if test="${perm2}">
	    <input name="emailOrderAck" id="emailOrderAck1" type="radio" class="radioBtns" value="Y" <c:if test="${customerBean.emailOrderAck ne 'N'}">checked</c:if>>Y
	    <input name="emailOrderAck" id="emailOrderAck2" type="radio" class="radioBtns" value="N" <c:if test="${customerBean.emailOrderAck eq 'N'}">checked</c:if>>N
    </c:if>
    <c:if test="${!perm2}">
	    <input disabled name="sdfs" id="sdfsd" type="radio" class="radioBtns" value="Y" <c:if test="${customerBean.emailOrderAck ne 'N'}">checked</c:if>><span id="spanEmailOrderAck1" style="color:gray">Y</span>
	    <input disabled name="sdfsdf" id="sdfsd" type="radio" class="radioBtns" value="N" <c:if test="${customerBean.emailOrderAck eq 'N'}">checked</c:if>><span id="spanEmailOrderAck2" style="color:gray">N</span>
   		&nbsp;<input name="emailOrderAck" id="emailOrderAck" type="hidden"  value="${customerBean.emailOrderAck}"/>
    </c:if>
	</td>
    <td class="optionTitleBoldRight"><fmt:message key="label.graceperiod"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft" colspan="2">
    	<input name="overdueLimit" id="overdueLimit" type="text" class="inputBox" value="${customerBean.overdueLimit}" size="6"/>
        &nbsp;<fmt:message key="label.days"/>
        <input type="hidden" name="overdueLimitBasis" id="overdueLimitBasis" value="days"/>
    </td>
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft">
    	${customerBean.overdueLimit}
        &nbsp;<fmt:message key="label.days"/>
        <input type="hidden" name="overdueLimitBasis" id="overdueLimitBasis" value="days"/>
        <input name="overdueLimit" id="overdueLimit" type="hidden"  value="${customerBean.overdueLimit}"/> 
   </td>
    </c:if>
    
   
    <%--<c:if test="${adminPermissions}">
     <td class="optionTitleBoldLeft">
		<select name="overdueLimitBasis" id="overdueLimitBasis" class="selectBox">
			<option <c:if test="${customerBean.overdueLimitBasis eq 'days'}">selected</c:if>>days</option>
			<option <c:if test="${customerBean.overdueLimitBasis ne 'days'}">selected</c:if>>%</option>
		</select>
   	 </td>
    </c:if>--%>
    <%--<c:if test="${!adminPermissions}">--%>
     <td class="optionTitleLeft">
			<%--${customerBean.overdueLimitBasis}--%>
            &nbsp;
            <%--&nbsp;<input name="overdueLimitBasis" id="overdueLimitBasis" type="hidden"  value="${customerBean.overdueLimitBasis}"/>--%>
			</td>
    <%--</c:if>--%>
   
	
    </tr>
    <tr> 
	<td class="optionTitleBoldRight" nowrap><fmt:message key="label.sapindustrykey"/>:</td>

    <c:if test="${perm2}">
      <td class="optionTitleBoldLeft">
    <select name="sapIndustryKey" id="sapIndustryKey" class="selectBox">
    	<c:forEach var="sbean" items="${vvSapIndustryKeyCollection}" varStatus="status">
    	<option value="${sbean.sapIndustryKey}" <c:if test="${customerBean.sapIndustryKey eq sbean.sapIndustryKey}">
    	selected
    	</c:if>>${sbean.description}<%-- nothing yet.<fmt:message key="${ibean.industryLabel}"/>--%></option>
    	</c:forEach>
    </select>
     </td>  
    </c:if>
    <c:if test="${!perm2}">
     <td class="optionTitleLeft">
    	<c:forEach var="sbean" items="${vvSapIndustryKeyCollection}" varStatus="status">
	  <c:if test="${customerBean.sapIndustryKey eq sbean.sapIndustryKey}">
	     ${sbean.description}
      </c:if>
    </c:forEach>
    	&nbsp;<input name="sapIndustryKey" id="sapIndustryKey" type="hidden"  value="${customerBean.sapIndustryKey}"/>
    	</td>
    </c:if>
     
	
    <c:if test="${perm2}">
    <td class="optionTitleBoldLeft" colspan="2">
    <input name="shipMfgCoc" id="shipMfgCoc" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.shipMfgCoc eq 'Y'}">checked</c:if>> <fmt:message key="label.mfgcoc"/>
    <%--<input name="shipMfgCoa" id="shipMfgCoa" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.shipMfgCoa eq 'Y'}">checked</c:if>> <fmt:message key="label.mfgcoa"/>--%>
    <%--Hidding this till we decide to use it--%>
    <input name="shipMfgCoa" id="shipMfgCoa" type="hidden"  value="${customerBean.shipMfgCoa}"/>
    <input name="shipMsds" id="shipMsds" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.shipMsds eq 'Y'}">checked</c:if>> <fmt:message key="label.msds"/>
     </td>
    </c:if>
    <c:if test="${!perm2}">
     <td class="optionTitleBoldLeft" colspan="2">
    <input disabled name="sdsd" id="sdsd" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.shipMfgCoc eq 'Y'}">checked</c:if>> <fmt:message key="label.mfgcoc"/>
	<input disabled name="sdsd" id="sdsd" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.shipMfgCoa eq 'Y'}">checked</c:if>> <fmt:message key="label.mfgcoa"/>
	<input disabled name="sdsd" id="sdsd" type="checkbox" class="radioBtns" value="Y" <c:if test="${customerBean.shipMsds eq 'Y'}">checked</c:if>> <fmt:message key="label.msds"/>
    &nbsp;<input name="shipMfgCoc" id="shipMfgCoc" type="hidden"  value="${customerBean.shipMfgCoc}"/>
    &nbsp;<input name="shipMfgCoa" id="shipMfgCoa" type="hidden"  value="${customerBean.shipMfgCoa}"/>
    &nbsp;<input name="shipMsds" id="shipMsds" type="hidden"  value="${customerBean.shipMsds}"/>
     </td>
    </c:if>
    
   	<td class="optionTitleBoldRight"><fmt:message key="label.creditstatus"/>: </td>
    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft" colspan="2">
				  <select name="creditStatus" id="creditStatus" class="selectBox" onchange="showCreditStatusMsg();">
  	                <c:forEach var="ibean" items="${vvCreditStatusCollection}" varStatus="status">
		    			<option value="${ibean.creditStatus}" <c:if test="${customerBean.creditStatus eq ibean.creditStatus}">selected</c:if>>${ibean.creditStatusDesc}</option>
    				</c:forEach>
    			  </select>
     </td> 
    </c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft" colspan="2">
    	${customerBean.creditStatus}
    	&nbsp;<input name="creditStatus" id="creditStatus" type="hidden"  value="${customerBean.creditStatus}"/>
    	</td>
    </c:if>
   
    </tr>     
    <tr>
    
    <td class="optionTitleBoldRight" nowrap><fmt:message key="label.fieldsalesrep"/>:</td>
      
    <c:if test="${perm2}">
    <td class="optionTitleBoldLeft">  
    <input  readonly type="text" class="invGreyInputText" name="fieldSalesRepName" id="fieldSalesRepName" value="<tcmis:inputTextReplace value="${customerBean.fieldSalesRepName}" />" size="20" maxlength="35"/>
    <input type="hidden" name="fieldSalesRepId" id="fieldSalesRepId" value="${customerBean.fieldSalesRepId}"/>
	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="cLookupButton" id="cLookupButton" value="" onclick="getSalesRep()"/>	
    </td>  
    </c:if>   
    <c:if test="${!perm2}">
    <td class="optionTitleLeft">  
    	${customerBean.fieldSalesRepName}
<%--       <input  readonly type="text" class="invGreyInputText" name="fieldSalesRepName" id="fieldSalesRepName" value="<tcmis:inputTextReplace value="${customerBean.fieldSalesRepName}" />" size="6"/>	--%>
     </td>  
    </c:if>     
    <td class="optionTitleBoldRight"><fmt:message key="label.abcclassification"/>:</td>
    <td class="optionTitleLeft">
		<select id="abcClassification" name="abcClassification" class="selectBox">
			<option value=""></option>
			<option value="A" <c:if test="${'A' eq customerBean.abcClassification}">selected </c:if>>A</option>
			<option value="B" <c:if test="${'B' eq customerBean.abcClassification}">selected </c:if>>B</option>
			<option value="C" <c:if test="${'C' eq customerBean.abcClassification}">selected </c:if>>C</option>
		</select>
	</td>   
    <td class="optionTitleBoldRight"><fmt:message key="label.paymentterms"/>: </td>    
    <c:if test="${adminPermissions}">
    <td class="optionTitleBoldLeft" colspan="2">
		<html:select property="paymentTerms" styleClass="selectBox" styleId="paymentTerms" value="${customerBean.paymentTerms}">
		  <html:options collection="vvPaymentTermsBeanCollection" property="paymentTerms" labelProperty="paymentTerms"/>
		</html:select>
		 </td>
	</c:if>
    <c:if test="${!adminPermissions}">
     <td class="optionTitleLeft" colspan="2">
		${customerBean.paymentTerms}
		&nbsp;<input name="paymentTerms" id="paymentTerms" type="hidden"  value="${customerBean.paymentTerms}"/>
		</td>
	</c:if>
   
	</tr>
	<tr>
		<td class="optionTitleBoldRight" nowrap><fmt:message key="label.jdebilltoabno"/>:&nbsp;</td> 
		<td class="optionTitleLeft">  
	    	<input name="jdeCustomerBillTo" id="jdeCustomerBillTo" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.jdeCustomerBillTo}" />"/>
  				<tcmis:inputTextReplace value="${customerBean.jdeCustomerBillTo}" />
		</td>
		<td class="optionTitleBoldRight" nowrap>&nbsp;</td>
		<td class="optionTitleBoldRight" nowrap><fmt:message key="label.customerorigin"/>:&nbsp;</td> 
		<td class="optionTitleLeft">  	    	
  				<tcmis:inputTextReplace value="${customerBean.customerOrigin}" />
		</td>    
	</tr>
	<tr>
	<td class="optionTitleBoldRight" rowspan="2"><fmt:message key="label.notes"/>: </td>
	<c:if test="${perm2}">
	<td  colspan="3" rowspan="2">
          <textarea rows="3" cols="124"  name="customerNotes" id="notes" class="inputBox">    
            ${customerBean.notes}          
          </textarea>
       </td>
       </c:if>
       <c:if test="${!perm2}">
       <td class="optionTitleLeft" colspan="3">
       <tcmis:inputTextReplace value="${customerBean.notes}"/>
       &nbsp;<input name="customerNotes" id="customerNotes" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.notes}"/>"/>
       </td>
       </c:if>
     <td class="optionTitleBoldLeft" nowrap>
	    <span id="updateBillToPriceList" style='color:blue;cursor:pointer'>
			<a href="javascript:modBillTo()"><fmt:message key="label.customerentitypricelist"/></a>
		</span>  
	 </td>
	</tr>
	<tr>
     <td class="optionTitleBoldLeft" nowrap>
	    <span id="updateBillToPriceList" style='color:blue;cursor:pointer'>
			<a href="javascript:chooseCustomerValidCurrency()"/><fmt:message key="label.customercurrencylist"/></a>
		</span>  
	 </td>
	</tr>
	<tr>
		<td class="optionTitleBoldRight" rowspan="2"><fmt:message key="label.taxnotes"/>: </td>
		<td  colspan="3" rowspan="2">
          <textarea rows="3" cols="124"  name="taxNotes" id="taxNotes" class="inputBox">    
            ${customerBean.taxNotes}          
          </textarea>
     	</td>
     	<td class="optionTitleLeft" colspan="3">
	       &nbsp;<input name="taxNotes" id="taxNotes" type="hidden"  value="<tcmis:inputTextReplace value="${customerBean.taxNotes}"/>"/>
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
 <tr id="hide" style="display:none;">
      	<td colspan="7" >
           &nbsp;
        </td>
        <td class="optionTitleBoldRight">
          <div style="display:" onclick="hideDiv();">
            <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" value=""/>
<input name="hubName" id="hubName" type="hidden" value=""/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="billToCompanyId" id="billToCompanyId" type="hidden" value="${customerBean.billToCompanyId}"/>
<input name="contactTotalLines" id="contactTotalLines" value="${contactDataCount}" type="hidden"/>
<input name="shipToTotalLines" id="shipToTotalLines" value="${shipToDataCount}" type="hidden"/>
<input name="carrierToTotalLines" id="carrierToTotalLines" value="${carrierDataCount}" type="hidden"/>
<input name="billToLocationId" id="billToLocationId" value="${customerBean.billToLocationId}" type="hidden"/>
<input name="billingChange" id="billingChange" value="false" type="hidden"/>
<input name="otherChange" id="otherChange" value="false" type="hidden"/>
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

</div> <!-- Search Frame Ends -->

</div><!-- Search Section Div Ends -->


<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showContactDivRow" style="display: none;">


<td class="optionTitleBoldLeft"> <fmt:message key="label.contactinfo"/></td>
<td colspan="6"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="showContactDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideContactDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideContactDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>
<div id="resultGridDiv"  onmousedown="contactmenu(event)"> <%-- style="display: none;"> --%>
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
    <div class="boxhead"><fmt:message key="label.contactinfo"/>
    	<c:if test="${(adminPermissions== true) or (perm2== true)}">

		&#150;&#150;&#150;&#150;&#150;&#150;
		<span id="newContactAddLine" style='color:blue;cursor:pointer'><a onclick="newContact();"><fmt:message key="label.newcontact"/></a>	
		&nbsp;
		</span>
		</c:if>
    </div> 
	<div id="contactBean"></div>
  </div>
   <div id="contactFooter"></div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>  


</div><!-- Result Frame Ends -->

<!-- Shipto Frame Begins -->
<div id="shiptoFrameDiv" style="margin: 4px 4px 0px 4px;" onmousedown="shiptomenu(event)">
<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showShipToDivRow" style="display: none;">

<td class="optionTitleBoldLeft"> <fmt:message key="label.shipto"/></td>

<td colspan="6"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="showShipToDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideShipToDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideShipToDiv();">
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>
<div id="shiptoGridDiv"> <%-- style="display: none;"> --%>
<!-- Search shipto start -->
<!-- If you want your shipto section to span only 50% set this to 50% and your main table will be 100% -->
<table id="shiptoMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"><fmt:message key="label.shipto"/>
    <c:if test="${(adminPermissions== true) or (perm2== true)}">
	&#150;&#150;&#150;&#150;&#150;&#150;
	<span id="newShipToAddLine" style='color:blue;cursor:pointer'><a onclick="newShipTo();"><fmt:message key="label.newshipto"/></a>	
	&nbsp;
	</span>
	</c:if>
    </div> 
	<div id="shiptoBean"></div>
  </div>
  <div>
   <div id="sfooter"> &nbsp;</div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div> 

</div><!-- Shipto Frame Ends -->

<!-- customerCarrier Frame Begins -->
<div id="customerCarrierFrameDiv" style="margin: 4px 4px 0px 4px;" onmousedown="carriermenu(event)">
<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showCustomerCarrierDivRow" style="display: none;">

<td class="optionTitleBoldLeft"> <fmt:message key="label.customercarrier"/></td>
<td colspan="6"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="showCustomerCarrierDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideCustomerCarrierDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideCustomerCarrierDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>
<div id="customerCarrierGridDiv"> <%-- style="display: none;"> --%>
<!-- Search shipto start -->
<!-- If you want your shipto section to span only 50% set this to 50% and your main table will be 100% -->
<table id="customerCarrierMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"><fmt:message key="label.customercarrier"/>
    <c:if test="${(adminPermissions== true) or (perm2== true)}">

	&#150;&#150;&#150;&#150;&#150;&#150;
	<span id="newCarrierAddLine" style='color:blue;cursor:pointer'><a onclick="openNewCarrierWin();"><fmt:message key="newcarrier.title"/></a>	
	&nbsp;
	</span>

	</c:if>
	
	</div> 
	<div id="customerCarrierBean"></div>
  </div>
   <div id="carrierFooter">&nbsp;</div> 
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>  

</div><!-- customerCarrier Frame Ends -->

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
<c:if test="${(adminPermissions== true) or (perm2== true)}">
<script type="text/javascript">
        <!--
        showUpdateLinks = true;
        var curPriceGroupId = '${priceGroupId}';
        var pgmap1 = new Array();
        var pgmap2 = new Array();
        <c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
        	pgmap1["${cbean.priceGroupId}"] = '${cbean.priceGroupName}';
        </c:forEach>
        <c:forEach var="cbean" items="${vvpriceListCollectionAll}" varStatus="status">
        	pgmap2["${cbean.priceGroupId}"] = '${cbean.priceGroupName}';
        </c:forEach>
        if( curPriceGroupId && curPriceGroupId != 'New' && pgmap1[curPriceGroupId] == null ) {
        	try{
        		var i = $('priceGroupId').options.length;
        	    setOption(i,pgmap2[curPriceGroupId],curPriceGroupId, "priceGroupId");
        		$('priceGroupId').selectedIndex = i;
//        	    alert(curPriceGroupId+":"+pgmap2[curPriceGroupId]);
        	}catch(ex){}
        }
//-->
</script>
</c:if>

<c:if test="${adminPermissions}">
    <script type="text/javascript">
        <!--
        isPerm1True = true;
        //-->
    </script>
</c:if>
</tcmis:form>
</body>
</html>