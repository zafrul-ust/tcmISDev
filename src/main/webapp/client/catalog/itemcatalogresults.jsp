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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/itemcatalog.js"></script>


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
waitingforinput:"<fmt:message key="label.waitingforinputfrom"/>",
addeditsynonym:"<fmt:message key="label.addeditsynonym"/>",
newPartFrom:"<fmt:message key="label.newpartfrom"/>",
itemModifyPkg:"<fmt:message key="label.itemmodifypkg"/>",    
country:"<fmt:message key="label.country"/>"	
};

var map = null;
var map2 = null;
var prerow = null;
var preClass = null;
var lineMap = new Array();
var lineMap2 = new Array();
var lineMap3 = new Array();
var lineIdMap = new Array();
var lineIdMap2 = new Array();

var selectedRowId = null;
var selectedColId = null;

with ( milonic=new menuname("rightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("ViewSDS/MSDS") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
// style = contextStyle;
// margin=3;
 aI( "text="+messagesData.viewmsds+";url=javascript:viewMSDS();" );
}

drawMenus();

var curcursor = null;
var preContextMenuName = null
var disableMenu = parent.disableMenu;


// ajax request function.
var req;

var mygrid;


var newMsdsViewer = false;

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="newinit();">
<tcmis:form action="/catalogresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<c:set var="catalogAddPermission" value="N"/>
<c:if test="${isNewCatalogAddProcessReadyForFacility == 'Y'}">
	<tcmis:facilityPermission indicator="true" userGroupId="CreateNewChemical" companyId="${personnelBean.companyId}" facilityId="${param.facilityId}">
		<c:set var="catalogAddPermission" value="Y"/>
	</tcmis:facilityPermission>
</c:if>

<c:set var="engEvalPermission" value="N"/>
<tcmis:facilityPermission indicator="true" userGroupId="CreateNewChemical" companyId="${personnelBean.companyId}" facilityId="${param.facilityId}">
    <c:set var="engEvalPermission" value="Y"/>
</tcmis:facilityPermission>

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="disableEngEval" value="${tcmis:isFeatureReleased(personnelBean,'DisableEngineeringEvaluation', param.facilityId)}"/>
var disableEngEval = ${disableEngEval};

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
				  '<tcmis:jsReplace value="${p.grade}"/>',
			     '<tcmis:jsReplace value="${p.mfgDesc}" processMultiLines="true"/>',
				  '${p.countryName}',
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
		{
            id:'<tcmis:jsReplace value="${status2.current.accountSysId}"/>',
            desc:'<tcmis:jsReplace value="${status2.current.accountSysDesc}"/>',
            label:'<tcmis:jsReplace value="${status2.current.accountSysLabel}"/>',
            facItemChargeTypeOverride:'${status2.current.facItemChargeTypeOverride}'
        }
	</c:forEach>
	);

    var altCatalogFacility = new Array(
    <c:forEach var="catalogFacilityBean" items="${catalogFacilityCollection}" varStatus="status2">
       <c:if test="${status2.index > 0}">,</c:if>
       {
            catalogCompanyId:'<tcmis:jsReplace value="${status2.current.catalogCompanyId}"/>',
            catalogId:'<tcmis:jsReplace value="${status2.current.catalogId}"/>',
            catalogDesc:'<tcmis:jsReplace value="${status2.current.catalogDesc}"/>',
            catalogAddAllowed:'<tcmis:jsReplace value="${status2.current.catalogAddAllowed}"/>'
        }
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
  showUpdateLinks = false;  
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
<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
<input name="applicationId" id="applicationId" value="${param.applicationId}" type="hidden"/>
<input name="ecommerce" id="ecommerce" value="${param.ecommerce}" type="hidden"/>
<input name="catalogAddPermission" id="catalogAddPermission" value="${catalogAddPermission}" type="hidden"/>
<input name="engEvalPermission" id="engEvalPermission" value="${engEvalPermission}" type="hidden"/>
<%--
//USE fac_loc_app.charge_type default first unless it's overrides by vv_account_sys.fac_item_charge_type_override
//here's the logic for overriding fac_loc_app.charge_type_default
//vv_account_sys.fac_item_charge_type_override == fac_item.part_charge_type OR vv_accoount_sys.fac_item_charge_type_override = a
//then USE fac_item.part_charge_type
//vv_account_sys.fac_item_charge_type_override:
// d - and fac_item.part_charge_type = d then USE fac_item.part_charge_type (in this case it's d)
// i - and fac_item.part_charge_type = i then USE fac_item.part_charge_type (in this case it's i)
// a - always USE fac_item.part_charge_type
// n - never override => USE fla.charge_type_default
--%>      
<input type="hidden" name="engEvalPartTypeRequired" id="engEvalPartTypeRequired" value="${engEvalPartTypeRequired}"/>
<input type="hidden" name="facLocAppChargeTypeDefault" id="facLocAppChargeTypeDefault" value="${facLocAppChargeTypeDefault}"/>

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<!--
<tcmis:saveRequestParameter/>
-->
 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html>
