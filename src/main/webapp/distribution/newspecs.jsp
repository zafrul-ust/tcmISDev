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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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
<script type="text/javascript" src="/js/distribution/newspecs.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/fileUpload/validatefileextension.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.new"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--



//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
spec:"<fmt:message key="label.spec"/>",
detail:"<fmt:message key="label.detail"/>",
notusecommasinspec:"<fmt:message key="label.notusecommasinspec"/>",
filetypenotallowed:"<fmt:message key="label.filetypenotallowed"/>",
errors:"<fmt:message key="label.errors"/>"};


var showErrorMessage = false;
useLayout=false;
var windowCloseOnEsc = true;
var fromReceiptSpec = false;

var closeNewSpecWin = false;
<c:if test="${closeNewSpecWin eq 'Y'}" >
closeNewSpecWin = true;
</c:if>


<c:choose>
<c:when test="${fromReceiptSpec eq 'Yes'}"> 
fromReceiptSpec = true;
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>




// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="newSpecWinClose();" onresize="resizeFrames()">
<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/newspecs.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame" enctype="multipart/form-data">
<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
       <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>        
    <div id="mainUpdateLinks"  > <%-- mainUpdateLinks Begins --%>    
     <span id="updateResultLink" >&nbsp;&nbsp;&nbsp;      
     <a href="#" onclick="addNewSpec();"><fmt:message key="label.return"/>&nbsp;<fmt:message key="label.spec"/></a>&nbsp;
		     |&nbsp;<a href="#" onclick="javascript:window.close()"><fmt:message key="label.close"/></a>
     </span>      
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
<table width="500" border="0" cellpadding="0" cellspacing="2" class="tableSearch">
    <tr id="row3">
		<td width="6%"  class="optionTitleBoldRight" nowrap>
			<fmt:message key="label.specdate"/>:
		</td> 
      <td   colspan="2" class="optionTitleBoldLeft" >        
         
          <input class="inputBox pointer" readonly type="text" name="specDate" id="specDate" value="" onClick="return getCalendar(document.genericForm.specDate,null,null);"
                                                                    maxlength="10" size="8"/>
       </td>   
       
       <td width="6%"  class="optionTitleBoldRight">&nbsp;</td>   
    </tr>
    
   <tr>
	   <td  width="6%" class="optionTitleBoldRight" nowrap>
		<span style='font-size:12.0pt;color:red'>*</span>
			<fmt:message key="label.spec"/>:
	   </td> 
       <td  colspan="2" class="optionTitleBoldLeft" >
      
         <input name="specName" id="specName" type="text" class="inputBox" size="20" maxlength="30" value="${param.specName}"/>
       </td>
       <td width="6%"  class="optionTitleBoldRight">&nbsp;</td>      
    </tr>
    
    <tr id="row4">
		<td width="6%"  class="optionTitleBoldRight" nowrap>
			<fmt:message key="label.library"/>:
		</td> 
       <td   colspan="2" class="optionTitleLeft" >        
         ${param.specLibraryDesc}
       </td>   
       
       <td width="6%"  class="optionTitleBoldRight">&nbsp;</td>   
    </tr>
    
    <tr>
		<td  width="6%" class="optionTitleBoldRight" nowrap>
			<fmt:message key="label.revision"/>:
		</td> 
      <td colspan="2" class="optionTitleBoldLeft" >
         <input name="specVersion" id="specVersion" type="text" class="inputBox" size="10" maxlength="15" value="${param.specVersion}"/>
       </td>  
       <td width="6%"  class="optionTitleBoldRight">&nbsp;</td>    
    </tr>
    
    <tr>
		<td  width="6%" class="optionTitleBoldRight" nowrap>
			<fmt:message key="label.amendment"/>:
		</td> 
      <td   colspan="2" class="optionTitleBoldLeft" >
         <input name="specAmendment" id="specAmendment" type="text" class="inputBox" size="10" maxlength="10" value="${param.specAmendment}"/>
       </td> 
       <td width="6%"  class="optionTitleBoldRight">&nbsp;</td>     
    </tr>
    
    <tr>
		<td  width="6%" class="optionTitleBoldRight" nowrap>
			<fmt:message key="label.detail"/>:
		</td> 
       <td> 
         <select name="specDetailType" class="selectBox" id="specDetailType" >
                <option value=""><fmt:message key="label.type"/></option>
          <c:forEach var="addSpecsBean" items="${specDetailTypeCol}" varStatus="status">
          	<c:if test="${ param.specDetailType eq addSpecsBean.specDetailType}">
          		<option value="${addSpecsBean.specDetailType}" selected>${addSpecsBean.specDetailType}</option>
          	</c:if>
          	<c:if test="${ param.specDetailType ne addSpecsBean.specDetailType}">
          		<option value="${addSpecsBean.specDetailType}" >${addSpecsBean.specDetailType}</option>
          	</c:if>
		  </c:forEach>
	    </select>	         
        <select name="specDetailClass" class="selectBox" id="specDetailClass" >
               <option value=""><fmt:message key="label.class"/></option>
         <c:forEach var="addSpecsBean" items="${specDetailClassCol}" varStatus="status">
         	<c:if test="${ param.specDetailClass eq addSpecsBean.specDetailClass}">
         		<option value="${addSpecsBean.specDetailClass}" selected>${addSpecsBean.specDetailClass}</option>
         	</c:if>
         	<c:if test="${ param.specDetailClass ne addSpecsBean.specDetailClass}">
         		<option value="${addSpecsBean.specDetailClass}" >${addSpecsBean.specDetailClass}</option>
         	</c:if>
	     </c:forEach>
        </select>
        <select name="specDetailForm" class="selectBox" id="specDetailForm" >
               <option value=""><fmt:message key="label.form"/></option>
          <c:forEach var="addSpecsBean" items="${specDetailFormCol}" varStatus="status">
         	<c:if test="${ param.specDetailForm eq addSpecsBean.specDetailForm}">
         		<option value="${addSpecsBean.specDetailForm}" selected>${addSpecsBean.specDetailForm}</option>
         	</c:if>
         	<c:if test="${ param.specDetailForm ne addSpecsBean.specDetailForm}">
         		<option value="${addSpecsBean.specDetailForm}" >${addSpecsBean.specDetailForm}</option>
         	</c:if>
	     </c:forEach>
       </select>
        </td>
     </tr>
     <tr></tr>
     <tr></tr>
       <tr>
        <td  width="6%" class="optionTitleBoldRight">&nbsp;		
		</td> 
        <td>
        <select name="specDetailGroup" class="selectBox" id="specDetailGroup" >
               <option value=""><fmt:message key="label.group"/></option>
          <c:forEach var="addSpecsBean" items="${specDetailGroupCol}" varStatus="status">
         	<c:if test="${ param.specDetailGroup eq addSpecsBean.specDetailGroup}">
         		<option value="${addSpecsBean.specDetailGroup}" selected>${addSpecsBean.specDetailGroup}</option>
         	</c:if>
         	<c:if test="${ param.specDetailGroup ne addSpecsBean.specDetailGroup}">
         		<option value="${addSpecsBean.specDetailGroup}" >${addSpecsBean.specDetailGroup}</option>
         	</c:if>
	     </c:forEach>
         </select>	  
         <select name="specDetailGrade" class="selectBox" id="specDetailGrade" >
               <option value=""><fmt:message key="label.grade"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailGradeCol}" varStatus="status">
         	<c:if test="${ param.specDetailGrade eq addSpecsBean.specDetailGrade}">
         		<option value="${addSpecsBean.specDetailGrade}" selected>${addSpecsBean.specDetailGrade}</option>
         	</c:if>
         	<c:if test="${ param.specDetailGrade ne addSpecsBean.specDetailGrade}">
         		<option value="${addSpecsBean.specDetailGrade}" >${addSpecsBean.specDetailGrade}</option>
         	</c:if>
	      </c:forEach>
        </select>
         <select name="specDetailStyle" class="selectBox" id="specDetailStyle" >
               <option value=""><fmt:message key="label.style"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailStyleCol}" varStatus="status">
         	<c:if test="${ param.specDetailStyle eq addSpecsBean.specDetailStyle}">
         		<option value="${addSpecsBean.specDetailStyle}" selected>${addSpecsBean.specDetailStyle}</option>
         	</c:if>
         	<c:if test="${ param.specDetailStyle ne addSpecsBean.specDetailStyle}">
         		<option value="${addSpecsBean.specDetailStyle}" >${addSpecsBean.specDetailStyle}</option>
         	</c:if>
	      </c:forEach>
        </select>
         </td>
     </tr>
     <tr></tr>
     <tr></tr>
       <tr>
        <td  width="6%" class="optionTitleBoldRight">&nbsp;		
		</td> 
        <td> 
        <select name="specDetailFinish" class="selectBox" id="specDetailFinish" >
               <option value=""><fmt:message key="label.finish"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailFinishCol}" varStatus="status">
         	<c:if test="${ param.specDetailFinish eq addSpecsBean.specDetailFinish}">
         		<option value="${addSpecsBean.specDetailFinish}" selected>${addSpecsBean.specDetailFinish}</option>
         	</c:if>
         	<c:if test="${ param.specDetailFinish ne addSpecsBean.specDetailFinish}">
         		<option value="${addSpecsBean.specDetailFinish}" >${addSpecsBean.specDetailFinish}</option>
         	</c:if>
	      </c:forEach>
        </select>
        <select name="specDetailSize" class="selectBox" id="specDetailSize" >
               <option value=""><fmt:message key="label.size"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailSizeCol}" varStatus="status">
         	<c:if test="${ param.specDetailSize eq addSpecsBean.specDetailSize}">
         		<option value="${addSpecsBean.specDetailSize}" selected>${addSpecsBean.specDetailSize}</option>
         	</c:if>
         	<c:if test="${ param.specDetailSize ne addSpecsBean.specDetailSize}">
         		<option value="${addSpecsBean.specDetailSize}" >${addSpecsBean.specDetailSize}</option>
         	</c:if>
	      </c:forEach>
        </select> 
          <select name="specDetailColor" class="selectBox" id="specDetailColor" >
               <option value=""><fmt:message key="label.color"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailColorCol}" varStatus="status">
         	<c:if test="${ param.specDetailColor eq addSpecsBean.specDetailColor}">
         		<option value="${addSpecsBean.specDetailColor}" selected>${addSpecsBean.specDetailColor}</option>
         	</c:if>
         	<c:if test="${ param.specDetailColor ne addSpecsBean.specDetailColor}">
         		<option value="${addSpecsBean.specDetailColor}" >${addSpecsBean.specDetailColor}</option>
         	</c:if>
	      </c:forEach>
        </select>
         </tr>
     <tr></tr>
     <tr></tr>
        <!--<select name="specDetailMethod" class="selectBox" id="specDetailMethod" >
               <option value=""><fmt:message key="label.method"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailMethodCol}" varStatus="status">
         	<c:if test="${ param.specDetailMethod eq addSpecsBean.specDetailMethod}">
         		<option value="${addSpecsBean.specDetailMethod}" selected>${addSpecsBean.specDetailMethod}</option>
         	</c:if>
         	<c:if test="${ param.specDetailMethod ne addSpecsBean.specDetailMethod}">
         		<option value="${addSpecsBean.specDetailMethod}" >${addSpecsBean.specDetailMethod}</option>
         	</c:if>
	      </c:forEach>
        </select>
         <select name="specDetailCondition" class="selectBox" id="specDetailCondition" >
               <option value=""><fmt:message key="label.condition"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailConditionCol}" varStatus="status">
         	<c:if test="${ param.specDetailCondition eq addSpecsBean.specDetailCondition}">
         		<option value="${addSpecsBean.specDetailCondition}" selected>${addSpecsBean.specDetailCondition}</option>
         	</c:if>
         	<c:if test="${ param.specDetailCondition ne addSpecsBean.specDetailCondition}">
         		<option value="${addSpecsBean.specDetailCondition}" >${addSpecsBean.specDetailCondition}</option>
         	</c:if>
	      </c:forEach>
        </select>
         <select name="specDetailDash" class="selectBox" id="specDetailDash" >
               <option value=""><fmt:message key="label.dash"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailDashCol}" varStatus="status">
         	<c:if test="${ param.specDetailDash eq addSpecsBean.specDetailDash}">
         		<option value="${addSpecsBean.specDetailDash}" selected>${addSpecsBean.specDetailDash}</option>
         	</c:if>
         	<c:if test="${ param.specDetailDash ne addSpecsBean.specDetailDash}">
         		<option value="${addSpecsBean.specDetailDash}" >${addSpecsBean.specDetailDash}</option>
         	</c:if>
	      </c:forEach>
        </select>
        </td>
        </tr>
	     <tr></tr>
	     <tr></tr>
       <tr>
       <td  width="6%" class="optionTitleBoldRight">&nbsp;		
		</td>
        <td>
        <select name="specDetailNote" class="selectBox" id="specDetailNote" >
               <option value=""><fmt:message key="label.note"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailNoteCol}" varStatus="status">
         	<c:if test="${ param.specDetailNote eq addSpecsBean.specDetailNote}">
         		<option value="${addSpecsBean.specDetailNote}" selected>${addSpecsBean.specDetailNote}</option>
         	</c:if>
         	<c:if test="${ param.specDetailNote ne addSpecsBean.specDetailNote}">
         		<option value="${addSpecsBean.specDetailNote}" >${addSpecsBean.specDetailNote}</option>
         	</c:if>
	      </c:forEach>
        </select>-->
         <tr>
         <td width="6%"  class="optionTitleBoldRight" nowrap>
			<fmt:message key="label.other"/>:
		</td> 
        <td colspan="2" class="optionTitleBoldLeft">
           <input name="specDetailOther" id="specDetailOther" type="text" class="inputBox" size="10" maxlength="10" value="${param.specDetailOther}"/>
       </td>
       <td>
       <input name="content" id="content" type="hidden" value="${addSpecsBean.content}"/>
       <input name="specDetailCondition" id="specDetailCondition" type="hidden" value="${addSpecsBean.specDetailCondition}"/>
       <input name="specDetailDash" id="specDetailDash" type="hidden" value="${addSpecsBean.specDetailDash}"/>
       <input name="specDetailNote" id="specDetailNote" type="hidden" value="${addSpecsBean.specDetailNote}"/>
       <input name="specDetailMethod" id="specDetailMethod" type="hidden" value="${addSpecsBean.specDetailMethod}"/>
       </td>
      </tr>
      <tr id="row1">
		<td  width="6%" class="optionTitleBoldRight">
			<fmt:message key="label.specorg"/>:
		</td> 
      <td   colspan="2" class="optionTitleBoldLeft" >
         <input name="specOrg" id="specOrg" type="text" class="inputBox" size="30" maxlength="60" value=""/>
       </td> 
       <td width="6%"  class="optionTitleBoldRight">&nbsp;</td>     
    </tr>
    
     <tr id="row2">
		<td colspan="3" class="optionTitleBoldLeft">
			 <html:file property="theFile" styleId="theFile" value="" size="50" maxlength="50" />
		</td> 
           
    </tr>

</table>
    <!-- Search Option Table end -->
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
<!-- Error Messages Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" value="${param.uAction}"/>
<input name="specId" id="specId" type="hidden" value="${specId}"/>
<input name="startSearchTime" id="startSearchTime" type="hidden"value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="83"/>
<%--TODO need to get the correct location for newly added specs--%>    
<input name="location" id="location" type="hidden" value="MSDS"/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
<input name="specLibrary" id="specLibrary" type="specLibrary" value="${param.specLibrary}"/>
<input name="specLibraryDesc" id="specLibraryDesc" type="hidden" value="${param.specLibraryDesc}"/>
<input name="fromReceiptSpec" id="fromReceiptSpec" type="hidden" value="${fromReceiptSpec}"/>

</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->



<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
 	<br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
    <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">

    <div class="dataContent">
     
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


</div> <!-- close of interface -->


<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</div>

</body>
</html:html>