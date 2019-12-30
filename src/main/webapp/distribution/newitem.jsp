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

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script>

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
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/distribution/newitem.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
    
<title>
<fmt:message key="label.newitemform"/>
</title>


<script type="text/javascript">
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
shipToNameRequired:"<fmt:message key="label.shipto"/> <fmt:message key="label.name"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
fulladdressnozip:"<fmt:message key="label.fulladdressnozip"/>",
fulladdressnocity:"<fmt:message key="label.fulladdressnocity"/>",
all:"<fmt:message key="label.all"/>",
missinginventorygroup:"<fmt:message key="label.missinginventorygroup"/>",
inventorygroup:"<fmt:message key="label.inventorygroup"/>",
itemid:"<fmt:message key="label.itemid"/>",
buyer:"<fmt:message key="label.buyer"/>",
supplier:"<fmt:message key="label.supplier"/>"
};

defaults.ops.nodefault = true;
//defaults.hub.nodefault = true;
//defaults.inv.nodefault = true;

defaults.ops.callback = checkPermission;

window.onresize= resizeFrames;

var OpsEntityPermissionArray = new Array();
<c:set var="oCounter" value='${0}'/>
<c:forEach var="opsEntityBean" items="${personnelBean.permissionBean.userGroupMemberOpsEntityBeanCollection}" varStatus="status">
  <c:if test="${(status.current.userGroupId  == 'supplierPriceList')}">
    OpsEntityPermissionArray["<c:out value="${oCounter}"/>"] = "<c:out value="${status.current.opsEntityId}" escapeXml="false"/>";
    <c:set var="oCounter" value='${oCounter + 1}'/>    
  </c:if>
</c:forEach>

var OpsCurrencyIdArray = [
	<c:forEach var="opsCurrencyIdBean" items="${opsEntityRemittanceBeanColl}" varStatus="status1">
	    	{opsEntityId:   '${opsCurrencyIdBean.opsEntityId}',
		 id:   '${opsCurrencyIdBean.currencyId}',
		 name: '<tcmis:jsReplace value="${opsCurrencyIdBean.currencyDisplay}"/>'
	    	}<c:if test="${!status1.last}">,</c:if>
	</c:forEach>
];     


var OpsCurrencyDefaultArray = new Array();
<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
	OpsCurrencyDefaultArray['${opsEntity.opsEntityId}'] ='${opsEntity.homeCurrencyId}' 
</c:forEach>
;   

function doOnload() {
	setOps();
	$('opsEntityId').value='${param.opsEntityId}';
	opsChanged();
	$('hub').value='${param.hub}';
	hubChanged();
	if('All' == '${param.inventoryGroup}')
		$('inventoryGroup').value='';
	else
		$('inventoryGroup').value='${param.inventoryGroup}';
	setCurrencyDropdown(OpsCurrencyIdArray, $v('opsEntityId'), 'currencyId');
	
	if('${param.itemId}'.length != 0 && '${param.itemDesc}'.length == 0)
		callToServer("newitem.do?uAction=getItemDesc&itemId="+'${param.itemId}'); 
}  

function setItemDesc(itemDesc) {
	$('itemDesc').value = itemDesc;
}

function getBuyTypeFlag(){
		if($v('inventoryGroup') != 'All'){
			j$.ajax({
				url:"newitem.do",
				type: "POST",
				data:{uAction: "getBuyTypeFlag", inventoryGroup:$v("inventoryGroup")},
				success: function(data) {
					if (data != null && data != "") {
						$("buyTypeFlag").value = data;
					}
				}
			});
		}
	}

// -->
</script>

</head>

<body bgcolor="#ffffff"  onload="doOnload()" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" >

<tcmis:form action="/newitem.do" onsubmit="return submitFrameOnlyOnce();">

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
<fieldset>
<legend>&nbsp;<fmt:message key="label.newitemform"/>&nbsp;</legend>
<table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%" nowrap>
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox" onchange="getBuyTypeFlag()">
       </select>
       <input type="checkbox" name="allInventoryGroups" id="allInventoryGroups"><fmt:message key="label.forallgroups"/></input>
      </td>
    </tr>

<tr>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.shipto" />:
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
<input name="shipToLocationName" id="shipToLocationName" type="text" maxlength="18" size="15" class="invGreyInputText" readonly="readonly" value="All"/>                                                           
                                 <input name="shipToLocationId" id="shipToLocationId" type="hidden" value="All"/>
                                 <input name="shipToCompanyId" id="shipToCompanyId" type="hidden" value="All"/>
                                 <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getShipToId()" type="button"/>
                                 <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearShipToId()" type="button"/>
                               				
				</td>
</tr>
<tr>
				<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.item" /><span style='font-size:12.0pt;color:red'>*</span>:
				</td>
				<td class="optionTitleBoldLeft" nowrap="nowrap">
<input name="itemDesc" id="itemDesc" type="text" maxlength="18" size="15" value="${param.itemDesc}" class="invGreyInputText" readonly="readonly"/>                                                           
                                 <input name="itemId" id="itemId" type="hidden" value="${param.itemId}"/>                                    
                                 <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getItem()" type="button"/>
                                 <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearItem()" type="button"/>
				</td>
</tr>

	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.supplier" /><span style='font-size:12.0pt;color:red'>*</span>:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input name="supplierName" id="supplierName" type="text" maxlength="18" size="15" class="invGreyInputText" readonly="readonly" value="${param.supplierName}"/>                                                           
                    <input name="supplier" id="supplier" type="hidden" value="${param.supplier}"/>                                    
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getSuppliers()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearSuppliers()" type="button"/>                                				
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.buyer" />:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
					<input name="buyerName" id="buyerName" type="text" maxlength="18" size="15" class="invGreyInputText" readonly="readonly" value="${param.buyerName}"/>                                                           
                    <input name="buyer" id="buyer" type="hidden" value="${param.buyer}"/>                                    
                    <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getBuyer()" type="button"/>
                    <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearBuyer()" type="button"/>                                				
			</td>
	</tr>
	<tr>
			<td class="optionTitleBoldRight" nowrap="nowrap">
					<fmt:message key="label.currency" />:
			</td>
			<td class="optionTitleBoldLeft" nowrap="nowrap">
				<select name="currencyId" id="currencyId" class="selectBox" >
    			</select>     				
			</td>
	</tr>
<tr>
	<td width="15%" colspan="4">
		<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "newItem();"/>
        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "window.close();"/>
	</td>

</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
</fieldset>


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
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input type="hidden" name="buyTypeFlag" id="buyTypeFlag" value="${buyTypeFlag}"/>
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