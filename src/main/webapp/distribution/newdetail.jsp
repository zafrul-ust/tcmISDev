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
<script type="text/javascript" src="/js/distribution/newdetail.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>



<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.newspecdetail"/>
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
errors:"<fmt:message key="label.errors"/>"};


var showErrorMessage = false;
useLayout=false;
var windowCloseOnEsc = true;

var fromReceiptSpec = false;
var fromSpecQC = false;

var closeNewSpecWin = false;
<c:if test="${closeNewSpecWin eq 'Y'}" >
closeNewSpecWin = true;
</c:if>


<c:choose>
<c:when test="${fromReceiptSpec eq 'Yes'}"> 
fromReceiptSpec = true;
</c:when>
<c:when test="${fromSpecQC eq 'Yes'}"> 
fromSpecQC = true;
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>




// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="newSpecWinClose();" onresize="resizeFrames()" onunload="window.opener.closeTransitWin()">
<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/newdetail.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame" enctype="multipart/form-data">
<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
    <span class="optionTitleBoldRight">&nbsp;&nbsp;&nbsp;<fmt:message key="label.spec"/>:</span>
   	<span class="optionTitleLeft">${param.specName}</span>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span class="optionTitleBoldRight">&nbsp;&nbsp;&nbsp;<fmt:message key="label.revision"/>:</span>
   	<span class="optionTitleLeft">${param.specVersion}</span>
	<span class="optionTitleBoldRight">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.amendment"/>:</span>
   	<span class="optionTitleLeft">${param.specAmendment}</span>
   	
       <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>        
    <div id="mainUpdateLinks"  > <%-- mainUpdateLinks Begins --%>    
     <span id="updateResultLink" >&nbsp;&nbsp;&nbsp;     
     <a href="#" onclick="addNewDetail();"><fmt:message key="label.returnspec"/></a>&nbsp;
		     |&nbsp;<a href="#" onclick="javascript:window.close()"><fmt:message key="label.close"/></a>
     </span>      
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
<table width="100%" border="0" cellpadding="0" cellspacing="2" class="tableSearch">
    
    <tr>
		<td  width="6%" class="optionTitleBoldRight">
			<fmt:message key="label.detail"/>:
		</td> 
       <td colspan="3"> 
         <select name="specDetailType" class="selectBox" id="specDetailType" >
                <option value=""><fmt:message key="label.type"/></option>
            <c:forEach var="addSpecsBean" items="${specDetailTypeCol}" varStatus="status">
            	<c:choose>
            		<c:when test="${ param.specDetailType eq addSpecsBean.specDetailType || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailType eq addSpecsBean.specDetailType)}">
            			<option value="${addSpecsBean.specDetailType}" selected>${addSpecsBean.specDetailType}</option>
            		</c:when>
            		<c:otherwise>
            			<option value="${addSpecsBean.specDetailType}" >${addSpecsBean.specDetailType}</option>
            		</c:otherwise>
            	</c:choose>
               <%-- <c:if test="${ param.specDetailType eq addSpecsBean.specDetailType}">
	         		<option value="${addSpecsBean.specDetailType}" selected>${addSpecsBean.specDetailType}</option>
	         	</c:if>
	         	<c:if test="${ param.specDetailType ne addSpecsBean.specDetailType}">
          			<option value="${addSpecsBean.specDetailType}" >${addSpecsBean.specDetailType}</option>
          		</c:if> --%>
		    </c:forEach>
	     </select>	         
         <select name="specDetailClass" class="selectBox" id="specDetailClass" >
               <option value=""><fmt:message key="label.class"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailClassCol}" varStatus="status">
           		<c:choose>
            		<c:when test="${ param.specDetailClass eq addSpecsBean.specDetailClass || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailClass eq addSpecsBean.specDetailClass)}">
            			<option value="${addSpecsBean.specDetailClass}" selected>${addSpecsBean.specDetailClass}</option>
            		</c:when>
            		<c:otherwise>
            			<option value="${addSpecsBean.specDetailClass}" >${addSpecsBean.specDetailClass}</option>
            		</c:otherwise>
            	</c:choose>
           		<%-- <c:if test="${ param.specDetailClass eq addSpecsBean.specDetailClass}">
	         		<option value="${addSpecsBean.specDetailClass}" selected>${addSpecsBean.specDetailClass}</option>
	         	</c:if>
	         	<c:if test="${ param.specDetailClass ne addSpecsBean.specDetailClass}">
          			<option value="${addSpecsBean.specDetailClass}" >${addSpecsBean.specDetailClass}</option>
          		</c:if> --%>
	       </c:forEach>
         </select>
        </td>
     </tr>
     <tr></tr>
     <tr></tr>
       <tr>
        <td  width="6%" class="optionTitleBoldRight">
        	<input name="clesrBtn" id="clesrBtn" type="button" class="smallBtns" value="<fmt:message key="label.clear"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick= "clearField();"/>
		</td> 
        <td colspan="3">
	        <select name="specDetailForm" class="selectBox" id="specDetailForm" >
	               <option value=""><fmt:message key="label.form"/></option>
	          	<c:forEach var="addSpecsBean" items="${specDetailFormCol}" varStatus="status">
	          		<c:choose>
            		<c:when test="${ param.specDetailForm eq addSpecsBean.specDetailForm || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailForm eq addSpecsBean.specDetailForm)}">
            			<option value="${addSpecsBean.specDetailForm}" selected>${addSpecsBean.specDetailForm}</option>
            		</c:when>
            		<c:otherwise>
            			<option value="${addSpecsBean.specDetailForm}" >${addSpecsBean.specDetailForm}</option>
            		</c:otherwise>
            	</c:choose>
	          		<%-- <c:if test="${ param.specDetailForm eq addSpecsBean.specDetailForm}">
		         		<option value="${addSpecsBean.specDetailForm}" selected>${addSpecsBean.specDetailForm}</option>
		         	</c:if>
		         	<c:if test="${ param.specDetailForm ne addSpecsBean.specDetailForm}">
	          			<option value="${addSpecsBean.specDetailForm}" >${addSpecsBean.specDetailForm}</option>
	          		</c:if> --%>
		     	</c:forEach>
	        </select>
	        <select name="specDetailGroup" class="selectBox" id="specDetailGroup" >
	               <option value=""><fmt:message key="label.group"/></option>
	          	<c:forEach var="addSpecsBean" items="${specDetailGroupCol}" varStatus="status">
	          		<c:choose>
	            		<c:when test="${ param.specDetailGroup eq addSpecsBean.specDetailGroup || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailGroup eq addSpecsBean.specDetailGroup)}">
	            			<option value="${addSpecsBean.specDetailGroup}" selected>${addSpecsBean.specDetailGroup}</option>
	            		</c:when>
	            		<c:otherwise>
	            			<option value="${addSpecsBean.specDetailGroup}" >${addSpecsBean.specDetailGroup}</option>
	            		</c:otherwise>
	            	</c:choose>
	         		<%-- <c:if test="${ param.specDetailGroup eq addSpecsBean.specDetailGroup}">
		         		<option value="${addSpecsBean.specDetailGroup}" selected>${addSpecsBean.specDetailGroup}</option>
		         	</c:if>
		         	<c:if test="${ param.specDetailGroup ne addSpecsBean.specDetailGroup}">
	          			<option value="${addSpecsBean.specDetailGroup}" >${addSpecsBean.specDetailGroup}</option>
	          		</c:if> --%>
		     	</c:forEach>
	         </select>
	      </td>
     </tr>
	 <tr>
        <td  width="6%" class="optionTitleBoldRight">&nbsp;		
		</td> 
        <td colspan="3">	  
	        <select name="specDetailGrade" class="selectBox" id="specDetailGrade" >
	               <option value=""><fmt:message key="label.grade"/></option>
	           <c:forEach var="addSpecsBean" items="${specDetailGradeCol}" varStatus="status">
	           		<c:choose>
	            		<c:when test="${ param.specDetailGrade eq addSpecsBean.specDetailGrade || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailGrade eq addSpecsBean.specDetailGrade)}">
	            			<option value="${addSpecsBean.specDetailGrade}" selected>${addSpecsBean.specDetailGrade}</option>
	            		</c:when>
	            		<c:otherwise>
	            			<option value="${addSpecsBean.specDetailGrade}" >${addSpecsBean.specDetailGrade}</option>
	            		</c:otherwise>
	            	</c:choose>
	         	    <%-- <c:if test="${ param.specDetailGrade eq addSpecsBean.specDetailGrade}">
		         		<option value="${addSpecsBean.specDetailGrade}" selected>${addSpecsBean.specDetailGrade}</option>
		         	</c:if>
		         	<c:if test="${ param.specDetailGrade ne addSpecsBean.specDetailGrade}">
	          			<option value="${addSpecsBean.specDetailGrade}" >${addSpecsBean.specDetailGrade}</option>
	          		</c:if>	 --%>
		    </c:forEach>
	        </select>
	        <select name="specDetailStyle" class="selectBox" id="specDetailStyle" >
	               <option value=""><fmt:message key="label.style"/></option>
	           <c:forEach var="addSpecsBean" items="${specDetailStyleCol}" varStatus="status">
	           		<c:choose>
	            		<c:when test="${ param.specDetailStyle eq addSpecsBean.specDetailStyle || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailStyle eq addSpecsBean.specDetailStyle)}">
	            			<option value="${addSpecsBean.specDetailStyle}" selected>${addSpecsBean.specDetailStyle}</option>
	            		</c:when>
	            		<c:otherwise>
	            			<option value="${addSpecsBean.specDetailStyle}" >${addSpecsBean.specDetailStyle}</option>
	            		</c:otherwise>
	            	</c:choose>
	           		<%-- <c:if test="${ param.specDetailStyle eq addSpecsBean.specDetailStyle}">
		         		<option value="${addSpecsBean.specDetailStyle}" selected>${addSpecsBean.specDetailStyle}</option>
		         	</c:if>
		         	<c:if test="${ param.specDetailStyle ne addSpecsBean.specDetailStyle}">
	          			<option value="${addSpecsBean.specDetailStyle}" >${addSpecsBean.specDetailStyle}</option>
	          		</c:if> --%>	
		      </c:forEach>
	        </select>
         </td>
     </tr>
     <tr>
        <td  width="6%" class="optionTitleBoldRight">&nbsp;		
		</td> 
        <td colspan="3"> 
        <select name="specDetailFinish" class="selectBox" id="specDetailFinish" >
               <option value=""><fmt:message key="label.finish"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailFinishCol}" varStatus="status">
           		<c:choose>
            		<c:when test="${ param.specDetailFinish eq addSpecsBean.specDetailFinish || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailFinish eq addSpecsBean.specDetailFinish)}">
            			<option value="${addSpecsBean.specDetailFinish}" selected>${addSpecsBean.specDetailFinish}</option>
            		</c:when>
            		<c:otherwise>
            			<option value="${addSpecsBean.specDetailFinish}" >${addSpecsBean.specDetailFinish}</option>
            		</c:otherwise>
            	</c:choose>
         		<%-- <c:if test="${ param.specDetailFinish eq addSpecsBean.specDetailFinish}">
		         	<option value="${addSpecsBean.specDetailFinish}" selected>${addSpecsBean.specDetailFinish}</option>
		        </c:if>
		        <c:if test="${ param.specDetailFinish ne addSpecsBean.specDetailFinish}">
	          		<option value="${addSpecsBean.specDetailFinish}" >${addSpecsBean.specDetailFinish}</option>
	          	</c:if> --%>	
	      </c:forEach>
        </select>
        <select name="specDetailSize" class="selectBox" id="specDetailSize" >
               <option value=""><fmt:message key="label.size"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailSizeCol}" varStatus="status">
           		<c:choose>
            		<c:when test="${ param.specDetailSize eq addSpecsBean.specDetailSize || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailSize eq addSpecsBean.specDetailSize)}">
            			<option value="${addSpecsBean.specDetailSize}" selected>${addSpecsBean.specDetailSize}</option>
            		</c:when>
            		<c:otherwise>
            			<option value="${addSpecsBean.specDetailSize}" >${addSpecsBean.specDetailSize}</option>
            		</c:otherwise>
            	</c:choose>
         		<%-- <c:if test="${ param.specDetailSize eq addSpecsBean.specDetailSize}">
		         	<option value="${addSpecsBean.specDetailSize}" selected>${addSpecsBean.specDetailSize}</option>
		        </c:if>
		        <c:if test="${ param.specDetailSize ne addSpecsBean.specDetailSize}">
	          		<option value="${addSpecsBean.specDetailSize}" >${addSpecsBean.specDetailSize}</option>
	          	</c:if>	 --%>
	      </c:forEach>
        </select> 
     </tr>
     <tr>
        <td  width="6%" class="optionTitleBoldRight">&nbsp;		
		</td> 
        <td colspan="3"> 
          <select name="specDetailColor" class="selectBox" id="specDetailColor" >
               <option value=""><fmt:message key="label.color"/></option>
           <c:forEach var="addSpecsBean" items="${specDetailColorCol}" varStatus="status">
           		<c:choose>
            		<c:when test="${ param.specDetailColor eq addSpecsBean.specDetailColor || (fromSpecQC eq 'Yes' && specQCDetailBean.specDetailColor eq addSpecsBean.specDetailColor)}">
            			<option value="${addSpecsBean.specDetailColor}" selected>${addSpecsBean.specDetailColor}</option>
            		</c:when>
            		<c:otherwise>
            			<option value="${addSpecsBean.specDetailColor}" >${addSpecsBean.specDetailColor}</option>
            		</c:otherwise>
            	</c:choose>
         		<%-- <c:if test="${ param.specDetailColor eq addSpecsBean.specDetailColor}">
		         	<option value="${addSpecsBean.specDetailColor}" selected>${addSpecsBean.specDetailColor}</option>
		        </c:if>
		        <c:if test="${ param.specDetailColor ne addSpecsBean.specDetailColor}">
	          		<option value="${addSpecsBean.specDetailColor}" >${addSpecsBean.specDetailColor}</option>
	          	</c:if>	 --%>
	       </c:forEach>
         </select>
     </tr>
     <tr></tr>
        <tr>
        <td width="6%"  class="optionTitleBoldRight">
			<fmt:message key="label.other"/>:
		</td> 
        <td colspan="2" class="optionTitleBoldLeft">
        	<c:choose>
           		<c:when test="${fromSpecQC eq 'Yes' && not empty specQCDetailBean.specDetailOther}">
           			<input name="specDetailOther" id="specDetailOther" type="text" class="inputBox" size="10" maxlength="10" value="${specQCDetailBean.specDetailOther}"/>
           		</c:when>
           		<c:otherwise>
           			<input name="specDetailOther" id="specDetailOther" type="text" class="inputBox" size="10" maxlength="10" value="${param.specDetailOther}"/>
           		</c:otherwise>
           	</c:choose>
           
       </td>
       <td>
       <input name="content" id="content" type="hidden" value="${addSpecsBean.content}"/>
       <input name="specDetailCondition" id="specDetailCondition" type="hidden" value="${addSpecsBean.specDetailCondition}"/>
       <input name="specDetailDash" id="specDetailDash" type="hidden" value="${addSpecsBean.specDetailDash}"/>
       <input name="specDetailNote" id="specDetailNote" type="hidden" value="${addSpecsBean.specDetailNote}"/>
       <input name="specDetailMethod" id="specDetailMethod" type="hidden" value="${addSpecsBean.specDetailMethod}"/>
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
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors && empty tcmISError}"> 
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
<input name="uAction" id="uAction" type="hidden" value=""/>
<input name="specId" id="specId" value="${param.specId}" type="hidden"/>
<input name="specName" id="specName" value="${param.specName}" type="hidden"/>
<input name="specLibrary" id="specLibrary" value="${param.specLibrary}" type="hidden"/>
<input name="specLibraryDesc" id="specLibraryDesc" value="${param.specLibraryDesc}" type="hidden"/>
<input name="specAmendment" id="specAmendment" value="${param.specAmendment}" type="hidden"/>
<input name="specVersion" id="specVersion" value="${param.specVersion}" type="hidden"/> 
<input name="fromReceiptSpec" id="fromReceiptSpec" value="${param.fromReceiptSpec}" type="hidden"/> 
<input name="fromSpecQC" id="fromSpecQC" value="${param.fromSpecQC}" type="hidden"/> 
<input name="startSearchTime" id="startSearchTime" type="hidden"value=""/>
<input name="searchHeight" id="searchHeight" type="hidden" value="83"/>
<input name="requestId" id="requestId" value="${param.requestId}" type="hidden"/> 
<input name="companyId" id="companyId" value="${param.companyId}" type="hidden"/>

<%--TODO need to get the correct location for newly added specs--%>    
<input name="location" id="location" type="hidden" value="MSDS"/>
<%--<input name="fromReceiptSpec" id="fromReceiptSpec" type="hidden" value="${fromReceiptSpec}"/>--%>





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