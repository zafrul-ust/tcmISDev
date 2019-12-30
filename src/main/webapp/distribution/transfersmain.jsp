<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="-1">
	<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

	<%@ include file="/common/locale.jsp" %> 						<%-- Sets locale --%>
	<%@ include file="/common/opshubig.jsp" %>						<%-- retrieves data for OpsEntity/hub/IG pulldowns --%>
	
	<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
	<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
	<script type="text/javascript" src="/js/common/commonutil.js"></script>
	<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>	<%-- This handles all the resizing of the page and frames --%>
	<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>
	
	<script type="text/javascript" src="/js/common/opshubig.js"></script>			<%-- sets up data for OpsEntity/hub/IG pulldowns --%>
	
	<script type="text/javascript"src="/js/common/standardGridmain.js"></script> 
	<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script> 
	<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script> 
	<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
	<!-- For Calendar support -->
	<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
	<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
	<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
<script type="text/javascript" src="/js/distribution/transfers.js"></script>


<title>
<fmt:message key="label.transfers"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

var alldefaults = {
	   ops: {id:'all',name:'<fmt:message key="label.allentities"/>'},
   	   hub: {id:'all',name:'<fmt:message key="label.allhubs"/>'},
   	   inv: {id:'all',name:'<fmt:message key="label.allinventorygroups"/>'}
   }
 
var allopshubig = [
    <c:forEach var="nouse3" items="${allOpsHubIgOvBeanCollection}" varStatus="status3">
    <c:if test="${ status3.index !=0 }">,</c:if>
        {
          id:   '${status3.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status3.current.operatingEntityName}"/>',
          coll: [ <c:forEach var="nouse4" items="${status3.current.hubIgCollection}" varStatus="status4">
    	     	  <c:if test="${ status4.index !=0 }">,</c:if>
        	 	  {  id: '${status4.current.hub}',
        	 	   name: '<tcmis:jsReplace value="${status4.current.hubName}"/>',
        	 	   coll: [ <c:forEach var="nouse5" items="${status4.current.inventoryGroupCollection}" varStatus="status5">
         				   <c:if test="${ status5.index !=0 }">,</c:if>
	    	    	 	   {
	    	    			id:'${status5.current.inventoryGroup}',
	    	    			name:'<tcmis:jsReplace value="${status5.current.inventoryGroupName}"/>'
	    	    		   }
         		   		   </c:forEach>
        	 	 		  ]
        		   }
        		   </c:forEach>
          	     ]
        }
    </c:forEach>
   ]; 
  
   
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>", pleasewait:"<fmt:message key="label.pleasewait"/>",	 
validvalues:"<fmt:message key="label.validvalues"/>",
integer:"<fmt:message key="label.errorinteger"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

var searchMyArr = new Array( 
		{value:'is', text: '<fmt:message key="label.is"/>'} 
		,{value:'contains', text: '<fmt:message key="label.contains"/>'} 
		,{value:'startsWith', text: '<fmt:message key="label.startswith"/>'} 
		,{value:'endsWith', text: '<fmt:message key="label.endswith"/>'} 
		); 

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','transfers');setOps();setDestOps(allopshubig,alldefaults);" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/transfersresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="5%">&nbsp;</td>
      <td width="10%" class="optionTitleBoldLeft"><fmt:message key="label.source"/></td>
      <td width="5%">&nbsp;</td>
      <td width="10%" class="optionTitleBoldLeft"><fmt:message key="label.destination"/></td>
     </tr>
   <!-- <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap>&nbsp;</td>
      <td width="10%">
		<select name="sourceEntities" id="sourceEntities" class="selectBox" onchange="changeSourceDropDown();">
			<option  value="all"><fmt:message key="label.allentities"/></option>
			<option  value="my" selected><fmt:message key="label.myentities"/></option>
       	</select>
      </td>
      <td width="5%" class="optionTitleBoldRight" nowrap>&nbsp;</td>
      <td width="10%" >
		<select name="destinationEntities" id="destinationEntities" class="selectBox" onchange="changeDestinationDropDown();">
			<option  value="all" selected><fmt:message key="label.allentities"/></option>  
			<option  value="my"><fmt:message key="label.myentities"/></option>
       	</select>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td> 
    </tr>-->
    <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="sourceOpsEntityId" id="sourceOpsEntityId" class="selectBox"></select>
      </td>
      <!--<td width="5%" class="optionTitleBoldRight" nowrap>&nbsp;&nbsp;&nbsp;<fmt:message key="label.operatingentity"/>:</td>
        <td width="10%">
         <select name="destinationOpsEntityId" id="destinationOpsEntityId" class="selectBox"></select>
      </td>-->
      <td>&nbsp;</td>
      <td>&nbsp;</td>
     </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="sourceHub" id="sourceHub" class="selectBox"></select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="destinationHub" id="destinationHub" class="selectBox"></select>
      </td>
      </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.invgrp"/>:&nbsp;</td>
      <td width="10%">
        <select name="sourceInventoryGroup" id="sourceInventoryGroup" class="selectBox"></select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.invgrp"/>:&nbsp;</td>
      <td width="10%">
       <select name="destinationInventoryGroup" id="destinationInventoryGroup" class="selectBox"></select>
      </td>
     </tr>
    <tr>
    <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.search"/>:&nbsp;</td>
    <td width="10%" colspan="3">
 		<select name="searchField" id="searchField" class="selectBox" onchange="changeSearchTypeOptions(this.value)" >
 			<option  value="itemId"><fmt:message key="label.itemid"/></option>
			<option  value="itemDesc"><fmt:message key="label.itemdesc"/></option>
            <option  value="transferRequestId"><fmt:message key="label.transferrequestid"/></option>
            <option  value="distCustomerPartList"><fmt:message key="label.customerpartnumber"/></option>
           </select>
 		<html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
			<html:option value="is" ><fmt:message key="label.is"/></html:option>
			<html:option value="startsWith" key="label.startswith"/>
 		</html:select>
   		<input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="20" onkeypress="return onKeyPress()">
 	  </td>
 	 </tr>
 	 <tr> 
 	  <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.requestdate"/>&nbsp;<fmt:message key="label.between"/>&nbsp;</td>
 	  <td width="10%">
        <input class="inputBox pointer" readonly type="text" name="fromDate" id="fromDate" value="" onClick="return getCalendar(document.genericForm.fromDate,null,document.genericForm.toDate);" size="10"/>
			&nbsp;<fmt:message key="label.and"/>&nbsp;
		<input class="inputBox pointer" readonly type="text" name="toDate" id="toDate" value="" onClick="return getCalendar(document.genericForm.toDate,document.genericForm.fromDate);" size="10"/>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.status"/>:&nbsp;</td>
      <td width="10%">
        <select name="status" id="status" class="selectBox">
			<option  value=""><fmt:message key="label.all"/></option>
			<option  value="Open"><fmt:message key="label.open"/></option>
			<option  value="Closed"><fmt:message key="label.closed"/></option>
			<option  value="Hold"><fmt:message key="label.qualityhold"/></option>
       	</select>
      </td>
    </tr>
    <tr>
     <td colspan="5" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()">
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
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
<input type="hidden" name="uAction" id="uAction" value="createXSL"/>
<input type="hidden" name="hubName" id="hubName" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="183">
<input name="oldOpsEntityId" id="oldOpsEntityId" type="hidden" value="">

</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
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

<!-- Transit Page Ends -->

			<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
				<!-- Transit Page Begins -->
				<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
					<br><br><br><fmt:message key="label.pleasewait" /> <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
				</div>
				<!-- Transit Page Ends -->
				<div id="resultGridDiv" style="display: none;"><!-- Search results start --> <!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
					<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="roundcont contentContainer">
									<div class="roundright">
										<div class="roundtop">
											<div class="roundtopright">
												<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
											</div>
										</div>
										<div class="roundContent">
											<div class="boxhead">
												<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
										          	Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
										      		--%>
												<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
													 <span id="updateResultLink" style="display: none"> 
	      												<a href="#" onclick="resultFrame.releaseTransfers();"><fmt:message key="label.releasetransfers"/></a>
	      											</span>
	      											<span id="updatePipeLine" style="display: none">&nbsp;|&nbsp;</span>
	      											<span id="updateResultLink2" style="display: none"> 
	      												<a href="#" onclick="resultFrame.closeTransfers();"><fmt:message key="label.closetransfers"/></a>
	      											</span>
												</div> <%-- mainUpdateLinks Ends --%>
											</div> <%-- boxhead Ends --%>
									
											<div class="dataContent">
												<iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
											</div>
											<%-- result count and time --%>
											<div id="footer" class="messageBar">
											</div>
										</div>
									
										<div class="roundbottom">
											<div class="roundbottomright">
												<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
											</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
					
				</div>
			</div>


</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>