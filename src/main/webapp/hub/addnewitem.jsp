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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/addnewitem.js"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title>
<fmt:message key="label.addcitr"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var alldefaults = {
	  // ops: {id:'all',name:'<fmt:message key="label.allentities"/>'}
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
  

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
all:"<fmt:message key="label.all"/>",
integer:"<fmt:message key="label.errorinteger"/>",
inventorygroup:"<fmt:message key="label.inventorygroup"/>",
itemid:"<fmt:message key="label.itemid"/>",
missingInventoryGroup:"<fmt:message key="label.missinginventorygroup"/>",
sameInventoryGroup:"<fmt:message key="label.sameInventoryGroup"/>"
};


var closeNewItemWin = false;
<c:if test="${closeNewItemWin == 'Y'}" >
closeNewItemWin = true;
</c:if>

var showErrorMessage = false;

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="newItemWinClose();setOps(allopshubig,alldefaults);$('sourceOpsEntityId').value='${param.opsEntityId}';$('inventoryGroup').value='${param.inventoryGroup}';" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" >

<tcmis:form action="/addnewitem.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>      
    ${requestScope['org.apache.struts.action.ERROR']}    
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
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
		<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.destinationig" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="inventoryGroup" id="inventoryGroup" type="text" size="30" class="invGreyInputText" readonly="readonly"/>                                                         
		</td>
	 </tr>
   <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.sourceopsentity"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
         <select name="sourceOpsEntityId" id="sourceOpsEntityId" class="selectBox">
         </select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.sourcehub"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
         <select name="sourceHub" id="sourceHub" class="selectBox"></select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.sourceinventorygroup"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
       <select name="sourceInventoryGroup" id="sourceInventoryGroup" class="selectBox">
       </select>
      </td>
    </tr>
	 <tr>
	<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.item" />:
	</td>
	<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
               <input name="itemId" id="itemId" type="text"  size="10" class="invGreyInputText" readonly="readonly" value="${param.itemId}"  />                                                           
               <input name="itemId" id="itemId" type="hidden" value="${param.itemId}"/>
                 
               <input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchsupplierlike" value="..." onclick="getItem()" type="button"/>
               <input class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="clearsupplierlike" value="<fmt:message key="label.clear"/>" onclick="clearItem()" type="button"/>
               
	</td>
   </tr>
   <tr>
	<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.description" />:
	</td>
	<td class="optionTitleBoldLeft" nowrap="nowrap">
         <input name="itemDesc" id="itemDesc" type="text"  size="40" class="invGreyInputText" readonly="readonly" value="${param.itemDesc}"  />                                                           
    </td>
    </tr>
	<tr><td></td></tr> 
<tr>
	<td width="10%" colspan="2">
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
<input type="hidden" name="branchPlant" id="branchPlant" value="${param.hub}"/>
<input type="hidden" name="srcOpsEntityId" id="srcOpsEntityId" value="${param.opsEntityId}"/>

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