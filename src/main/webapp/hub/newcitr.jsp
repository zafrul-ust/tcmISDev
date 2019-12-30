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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/newcitr.js"></script>

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
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
legalCompanyNameRequired:"<fmt:message key="error.legalcompanyname.required"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>", 
all:"<fmt:message key="label.all"/>",
integer:"<fmt:message key="label.errorinteger"/>",
missinginventorygroup:"<fmt:message key="label.missinginventorygroup"/>",
inventorygroup:"<fmt:message key="label.inventorygroup"/>",
itemid:"<fmt:message key="label.itemid"/>",
ownerCompanyId:"<fmt:message key="label.company"/>",
quantityToReceive:"<fmt:message key="label.quantity"/>",
expectedDeliveryDate:"<fmt:message key="label.expecteddeliverydate"/>",
poNumber:"<fmt:message key="label.ponumber"/>",
supplier:"<fmt:message key="label.supplier"/>"
};

var closeNewCitrWin = false;
<c:if test="${closeNewCitrWin == 'Y'}" >
closeNewCitrWin = true;
</c:if>

var altInventoryGroup = new Array(
<c:forEach var="userIgBean" items="${userIgCompanyOwnerBeanCollection}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
    {
        inventoryGroup:'<tcmis:jsReplace value="${userIgBean.inventoryGroup}"/>',
        inventoryGroupName:'<tcmis:jsReplace value="${userIgBean.inventoryGroupName}"/>'
    }
</c:forEach>
);

var altCompany = new Array();
<c:forEach var="userIgBean" items="${userIgCompanyOwnerBeanCollection}" varStatus="status">
    altCompany['<tcmis:jsReplace value="${userIgBean.inventoryGroup}"/>'] = new Array(
     <c:forEach var="userIgCompanyBean" items="${userIgBean.companyColl}" varStatus="status1">
        <c:if test="${status1.index > 0}">,</c:if>
        {
            companyId:'<tcmis:jsReplace value="${userIgCompanyBean.companyId}"/>',
            companyName:'<tcmis:jsReplace value="${userIgCompanyBean.companyName}"/>'
        }
     </c:forEach>
    );
</c:forEach>


var altOwnerSegment = new Array();
<c:forEach var="userIgBean" items="${userIgCompanyOwnerBeanCollection}" varStatus="status">
    <c:forEach var="userIgCompanyBean" items="${userIgBean.companyColl}" varStatus="status1">
        altOwnerSegment['<tcmis:jsReplace value="${userIgBean.inventoryGroup}"/><tcmis:jsReplace value="${userIgCompanyBean.companyId}"/>'] = new Array(
         <c:forEach var="ownerSegmentBean" items="${userIgCompanyBean.ownerSegmentColl}" varStatus="status2">
            <c:if test="${status2.index > 0}">,</c:if>
            {
                ownerSegmentId:'<tcmis:jsReplace value="${ownerSegmentBean.ownerSegmentId}"/>',
                ownerSegmentDesc:'<tcmis:jsReplace value="${ownerSegmentBean.ownerSegmentDesc}"/>'
            }
         </c:forEach>
        );
    </c:forEach>
</c:forEach>


// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="newCitrWinClose();setup();" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" >

<tcmis:form action="/addnewcitr.do" onsubmit="return submitFrameOnlyOnce();">

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

    <!--   <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
       <select name="opsEntityId" id="opsEntityId" class="selectBox" disabled></select>
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
       <select name="hub" id="hub" class="selectBox" disabled></select>
      </td>
    </tr>-->
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox" onchange="inventoryGroupChanged()"></select>
      </td>
    </tr>
	<tr>
		<td width="10%" class="optionTitleBoldRight" ><fmt:message key="label.company"/><span style='font-size:12.0pt;color:red'>*</span>:</td>
        <td>         
            <select name="ownerCompanyId" class="selectBox" id="ownerCompanyId" onchange="companyChanged()">
            </select>
        </td>
   	</tr>
   <tr>
	<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.item" /><span style='font-size:12.0pt;color:red'>*</span>:
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
    <tr>
		<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.ponumber" /><span style='font-size:12.0pt;color:red'>*</span>:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="poNumber" id="poNumber" type="text" maxlength="18" size="15" class="inputBox"  value=""/>                                                           
		</td>
	 </tr>
     <tr>
		<td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.customerpo" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="customerPoNo" id="customerPoNo" type="text" maxlength="18" size="15" class="inputBox"  value=""/>                                                           
		</td>
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.poline" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="customerPoLine" id="customerPoLine" type="text" maxlength="18" size="15" class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.expecteddeliverydate" /><span style='font-size:12.0pt;color:red'>*</span>:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="expectedDeliveryDate" id="expectedDeliveryDate" type="text" class="inputBox pointer" size="10" readonly="readonly" onclick="getCalendar(document.genericForm.expectedDeliveryDate,null,null);" />
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.quantity" /><span style='font-size:12.0pt;color:red'>*</span>:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="quantityToReceive" id="quantityToReceive" type="text"  size="8" class="inputBox"  value=""/>&nbsp
		<span id="uos" style="display: none">
		(<input name="unitOfSale" id="unitOfSale" type="text" size="2" class="invGreyInputText" readonly="readonly"/>)
		</span>                                                                                                                     
		</td>
	</tr>
	<tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		    <fmt:message key="label.ownersegmentid" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
            <select name="ownerSegmentId" id="ownerSegmentId" class="selectBox"></select>
		</td>
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.chargenumber" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="accountNumber" id="accountNumber" type="text"  size="10"  class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.chargenumber2" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="accountNumber2" id="accountNumber2" type="text"  size="10"  class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.chargenumber3" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="accountNumber3" id="accountNumber3" type="text"  size="10"  class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.chargenumber4" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="accountNumber4" id="accountNumber4" type="text"  size="10"  class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.customerreceiptid" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="customerReceiptId" id="customerReceiptId" type="text"  size="10"  class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.qualityTrackingNumber" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="qualityTrackingNumber" id="qualityTrackingNumber" type="text"  size="10"  class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.supplier" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<input name="supplierName" id="supplierName" type="text"  size="20" class="inputBox"  value=""/>                                                           
		</td>	
	 </tr>
	 <tr>
	    <td width="10%" class="optionTitleBoldRight" nowrap="nowrap">
		<fmt:message key="label.notes" />:
		</td>
		<td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
		<textarea cols="40" rows="4" class="inputBox" name="notes" id="notes"></textarea>                                                           
		</td>	
	 </tr>
	 <tr><td></td></tr>
   <tr>
	<td colspan="2">
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
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value=""/>
<input type="hidden" name="catalogId" id="catalogId" value=""/>
<input type="hidden" name="catPartNo" id="catPartNo" value=""/>
<input type="hidden" name="partGroupNo" id="partGroupNo" value=""/>
<input type="hidden" name="defaultInventoryGroup" id="defaultInventoryGroup" value="${param.inventoryGroup}"/>
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