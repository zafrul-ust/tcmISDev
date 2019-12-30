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

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/common/admin/approverinformation.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  



<title>
<fmt:message key="label.financialApproverTitle"/>
</title>

<script language="JavaScript" type="text/javascript">
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		canootexceedapproverlimit:"<fmt:message key="label.canootexceedapproverlimit"/>",
		pleaseselectuser:"<fmt:message key="label.pleaseselectuser"/>",
		replaceapprover:"<fmt:message key="label.replaceapprover"/>"
	};
	
	j$().ready(function() {
		function log(event, data, formatted) {
			j$('#personnelId').val(formatted.split(":")[0]);
			$("personnelName").className = "inputBox"; 
		} 
		
		j$("#personnelName").autocomplete("getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
				companyId: function() { return j$("#companyId").val(); } },
				width: 200,
				max: 10,  // default value is 10
				cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
				scrollHeight: 200,
				formatItem: function(data, i, n, value) {
					return  value.split(":")[1].substring(0,40);
				},
				formatResult: function(data, value) {
					return value.split(":")[1];
				}
		});
		
		j$('#personnelName').bind('keyup',(function(e) {
			var keyCode = (e.keyCode ? e.keyCode : e.which);
			
			if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
				invalidatePersonnel();
		}));
		
		j$("#personnelName").result(log).next().click(function() {
			j$(this).prev().search();
		});
	}); 
	
	function invalidatePersonnel() {
		var personnelName  =  document.getElementById("personnelName");
		var personnelId  =  document.getElementById("personnelId");
		if (personnelName.value.length == 0) {
			personnelName.className = "inputBox";
		} else {
			personnelName.className = "inputBox red";
		}
		
		//set to empty
		personnelId.value = "";
	}
</script>
</head>

<body bgcolor="#ffffff" onload="setValues()">

<tcmis:form action="financialapproverresults.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="label.financialApproverTitle"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">

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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.facility"/>:<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/></td>
        <td><span id="facilityName"></span><input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight">
         <c:if test="${param.uAction == 'Delete'}">
          <fmt:message key="label.deletedapprover"/>:
         </c:if>
        <c:if test="${param.uAction != 'Delete'}">
          <fmt:message key="label.username"/>:
        </c:if>
       </td>
        <td>
        <c:choose>
        	<c:when test="${param.uAction eq 'Add'}">
        		<input  type="text" name="personnelName" id="personnelName" value="" size="20" class="inputBox"/>
      			<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y"/>
      			<fmt:message key="label.activeOnly"/>
        	</c:when>
        	<c:otherwise>
        		<div id="personnelName"></div>
        	</c:otherwise>
        </c:choose>
      	<input name="personnelId" id="personnelId" type="hidden" value=""/>
        </td>
      </tr>
      <c:if test="${param.uAction != 'Delete'}">
      <tr>
        <td class="optionTitleBoldRight">
          <fmt:message key="label.userorderlimit"/>(${param.currencyId}):</td>
        <td class="optionTitle"><input type="text" id="costLimitDisplay" name="costLimitDisplay" value=""/><input type="hidden" id="costLimit" name="costLimit" value=""/></td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight" nowrap="nowrap">
          <fmt:message key="label.userapprovallimit"/>(${param.currencyId}):</td>
        <td class="optionTitle"><input type="text" id="approvalLimitDisplay" name="approvalLimitDisplay" value=""/><input type="hidden" id="approvalLimit" name="approvalLimit" value=""/></td>
      </tr>
   </c:if>
      <tr>
        <td class="optionTitleBoldRight" nowrap="nowrap">
         <c:if test="${param.uAction == 'Delete'}">
          <fmt:message key="label.newapprover"/>(${param.currencyId}):
         </c:if>
         <c:if test="${param.uAction != 'Delete'}">
          <fmt:message key="label.nextlevel"/>(${param.currencyId}):
         </c:if>
        </td>
        <td class="optionTitle"><select id="approverId" name="approverId"></select></td>
      </tr>
      <tr>
      	<td class="optionTitleRight">
			<input name="Ok" id="Ok" type="button" value="<fmt:message key="label.ok"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return search()"/>
		</td>
	   	<td class="optionTitleLeft">
			<input type="button" name="cancel" id="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="window.close()"/>
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
</table>
<!-- Search Option Ends -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input type="hidden" name="valueElementId" value="personnelId"/>
 <input type="hidden" name="editAction" name="editAction" value="edit"/>
 <input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
 <input type="hidden" name="userId" id="userId" value="${param.userId}"/>
 <input type="hidden" name="refresh" id="refresh" value="${refresh}"/>
 <input type="hidden" name="tcmISError" id="tcmISError" value="${tcmISError}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>