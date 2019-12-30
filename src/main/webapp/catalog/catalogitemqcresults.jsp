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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/catalogitemqc.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.catalogitemqc"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<!--Uncomment for production-->
<%--<tcmis:form action="/catalogitemqc.do" onsubmit="return submitFrameOnlyOnce();">--%>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<!--Uncomment for production-->
<%--
<tcmis:permission indicator="true" userGroupId="catalogitemqcUserGroup" facilityId="${param.testingField}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:permission>
--%>
<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<!--Uncomment for production-->
<%--<c:if test="${catalogitemqcViewBeanCollection != null}" >--%>
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <!--Uncomment for production-->
 <%--<c:if test="${!empty catalogitemqcViewBeanCollection}" >--%>

    <table width="100%" class="tableResults" id="resultsPageTable" border="1" cellpadding="0" cellspacing="0">

      <tr class="alt">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgid"/>:
 		</td>
 		<td width="10%" class="alignLeft" ><c:out value="${status.current.mfgId}"/></td>
 
  		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.manufacturer"/>:
 		</td>
 		<td width="43%" class="alignLeft" colspan="4"><c:out value="${status.current.manufacturer}"/></td>
      </tr>
      
      <tr class="">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgphone"/>:
 		</td>
 		<td width="10%" class="alignLeft" ><c:out value="${status.current.mfgPhone}"/></td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgcontact"/>:
 		</td>
 		<td width="43%" class="alignLeft" colspan="4"><c:out value="${status.current.mfgContact}"/></td> 		
      </tr>

      <tr class="alt">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgemail"/>:
 		</td>
 		<td width="10%" class="alignLeft" ><c:out value="${status.current.mfgEmail}"/></td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgnotes"/>:
 		</td>
 		<td width="70%" name="mfgnotes" id="mfgnotes" class="alignLeft" colspan="4"><c:out value="${status.current.mfgNotes}"/></td> 		
      </tr>

      <tr class="">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.materialid"/>:
 		</td>
 		<td width="10%" class="alignLeft"><c:out value="${status.current.materialId}"/></td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.materialdescription"/>:
 		</td>
		<td width="70%" class="optionTitleLeft" colspan="4">       		
   			<input class="inputBox" type="text" name="materialDescription" id="materialDescription" size="50" maxlength="80"
   				value="<c:out value="${status.current.materialDescription}"/>" >
 		</td>
      </tr>
 		
      <tr class="alt">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgshelflife"/>:
 		</td>
		<td width="10%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="mfgShelfLife" id="mfgShelfLife" size="5" 
   			value="<c:out value="${status.current.mfgShelfLife}"/>" ><fmt:message key="label.days"/> 
 		</td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.tradename"/>:
 		</td>
		<td width="70%" class="optionTitleLeft" colspan="4"><c:out value="${status.current.tradeName}"/></td>       		
      </tr>

      <tr class="">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.basis"/>:
 		</td> 		
		<td width="10%" class="optionTitleLeft">      
 			<select name="shelfLifeBasis" id="shelfLifeBasis" class="selectBox">
 			
    			<option value="">Please Select</option>
    			<option value="R">DOR</option> 		
    			<option value="M">DOM</option>
    			<option value="S">DOS</option> 		
    			<option value="T">Temp</option> 		

 			</select>
 		</td>    
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgstoragetemp"/>:&nbsp;
 		</td> 		
		<td width="70%" class="optionTitleLeft" colspan="4"> 
			<fmt:message key="label.min"/>:&nbsp;      		
   			<input class="inputBox" type="text" name="minTemp" id="minTemp" size="3" maxlength="30"
   			value="<c:out value="${status.current.minTemp}"/>" >
   			<fmt:message key="label.max"/>:&nbsp;&nbsp;
   			<input class="inputBox" type="text" name="maxTemp" id="maxTemp" size="3" maxlength="30"
   			value="<c:out value="${status.current.maxTemp}"/>" >
   			<fmt:message key="label.units"/>:&nbsp;&nbsp;
 			<select name="shelfLifeBasis" id="shelfLifeBasis" class="selectBox">			
    			<option value="">None</option>
    			<option value="F"><fmt:message key="label.degreesfahrenheit"/></option> 		
    			<option value="C"><fmt:message key="label.degreescelsius"/></option>
 			</select>  			
 		</td>
      </tr>

      <tr class="alt">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.grade"/>:
 		</td>
		<td width="10%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="grade" id="grade" size="5" value="<c:out value="${status.current.grade}"/>" >
 		</td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.mfgpartnr"/>:
 		</td>
		<td width="70%" class="optionTitleLeft" colspan="4">       		
   			<input class="inputBox" type="text" name="mfgPartNr" id="mfgPartNr" size="5" maxlength="30"
   			value="<c:out value="${status.current.mfgPartNr}"/>" >
 		</td>
      </tr>

      <tr class="">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.labelcolor"/>:
 		</td>
		<td width="10%" class="optionTitleLeft" >       		
  			
 			<select name="labelColor" id="labelColor" class="selectBox">
 			
    			<option value=""><fmt:message key="label.pleaseselect"/></option>
    			<option value="Black"><fmt:message key="label.black"/></option>
    			<option value="Goldenrod"><fmt:message key="label.goldenrod"/></option>
    			<option value="Green"><fmt:message key="label.green"/></option>
    			<option value="N/A"><fmt:message key="label.na"/></option>
    			<option value="Orange"><fmt:message key="label.orange"/></option>
    			<option value="Red"><fmt:message key="label.red"/></option>
    			<option value="White SCR"><fmt:message key="label.whitescr"/></option>
    			<option value="White-RET"><fmt:message key="label.whiteret"/></option>

 			</select>
 		</td>    
 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.nrcomp"/>:
 		</td>
		<td width="30%" class="optionTitleLeft" colspan="2">       		
   			<input class="inputBox" type="text" name="nrOfComponents" id="nrOfComponents"  size="5"	value="<c:out value="${status.current.nrOfComponents}"/>" >&nbsp;
 		<%-- </td>

 		<td width="15%" class="optionTitleBoldRight"> --%>
  			<fmt:message key="label.partsize"/>:
   			<input class="inputBox" type="text" name="partSize" id="partSize" size="10"	value="<c:out value="${status.current.partSize}"/>" >
  			<fmt:message key="label.sizeunit"/>:
 			<select name="labelColor" id="labelColor" class="selectBox">
 			
    			<option value=""><fmt:message key="label.pleaseselect"/></option>
    			<option value="ampule"><fmt:message key="label.ampule"/></option>
    			<option value="bag"><fmt:message key="label.bag"/></option>
    			<option value="belt"><fmt:message key="label.belt"/></option>
    			<option value="troy oz"><fmt:message key="label.troy oz"/></option>
    			<option value="tube"><fmt:message key="label.tube"/></option>
    			<option value="unit"><fmt:message key="label.unit"/></option>
    			<option value="wipe"><fmt:message key="label.wipe"/></option>
    			<option value="yard"><fmt:message key="label.yard"/></option>

 			</select>
 		</td>    

 		<td width="9%" class="optionTitleBoldLeft" colspan="2">
  			<fmt:message key="label.packagingstyle"/>:
 			<select name="packagingStyle" id="packagingStyle" class="selectBox">
 			
    			<option value=""><fmt:message key="label.pleaseselect"/></option>
    			<option value="1/4&#34;VCR carbon steel cylinder">1/4 inch VCR carbon steel cylinder</option>
    			<option value="2.7oz &#40;90gram&#41; tube">2.7oz (90gram) tube</option>
    			<option value="2AL Aluminum cylinder">2AL Aluminum cylinder</option>
 			</select>
 		</td>    
      </tr>

      <tr class="alt">
 		<td width="8%" class="optionTitleBoldRight">
  			<fmt:message key="label.dimension"/>:
 		</td>
		<td width="10%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="dimension" id="dimension" size="5" 
   			value="<c:out value="${status.current.dimension}"/>" >
 		</td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.netwt"/>:
 		</td>
		<td width="15%" class="optionTitleLeft" >       		  			
   			<input class="inputBox" type="text" name="netWt" id="netWt" size="5" maxlength="30"	value="<c:out value="${status.current.netWt}"/>" >
  			<fmt:message key="label.netwtunit"/>:
 			<select name="netWeightUnit" id="netWeightUnit" class="selectBox"> 			
    			<option value=""><fmt:message key="label.pleaseselect"/></option>
    			<option value="kg"><fmt:message key="label.kg"/></option>
    			<option value="gal"><fmt:message key="label.gal"/></option>
    			<option value="cc"><fmt:message key="label.cc"/></option>
    			<option value="gram"><fmt:message key="label.gram"/></option>
    			<option value="lb"><fmt:message key="label.lb"/></option>
    			<option value="liter"><fmt:message key="label.liter"/></option>
 			</select>
 		</td>    
 		
 		<td width="9%" class="optionTitleBoldRight">
  			<fmt:message key="label.itemverified"/>:
 		</td>
 		
		<td width="5%" class="optionTitleLeft">      
 			<select name="itemVerified" id="itemVerified" class="selectBox">			
				<option selected value="N"><fmt:message key="label.no"/></option>
				<option value="Y"><fmt:message key="label.yes"/></option>
 			</select>
 		</td>    		 
 		<td width="15%" class="optionTitleBoldLeft">
  			<fmt:message key="label.samplesize"/>:
 		</td>
 		
      </tr>
      
      <tr class="">      
 		<td width="8%" class="optionTitleBoldRight" >
  			<fmt:message key="label.comments"/>:  			
 		</td>
 		<td width="92%" class="optionTitleBoldLeft" colspan="7">
 			<textarea name="comments" rows="3" cols="110" class="inputBox"><c:out value="${status.current.comments}"/>
 			</textarea>
 		</td>
      </tr>
 		
    </table>


    <!--Uncomment for production-->
    <%--<c:forEach var="catalogitemqcViewBean" items="${catalogitemqcViewBeanCollection}" varStatus="status">--%>

    <!--Uncomment for production-->
    <%--<c:if test="${status.index % 10 == 0}">--%>
    <!-- Need to print the header every 10 rows-->

    <%--Place the table header here 
    
		<tr>
    		<th width="5%"><fmt:message key="label.example1"/></th>
		
		
		</tr> --%>

    <!--Uncomment for production-->
    <%--</c:if>--%>

    <!--Uncomment for production-->
    <%--
    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>
    --%>

    <%--<tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td width="5%"><c:out value="${status.current.exampleColumn1}"/></td>
     <td width="5%"><c:out value="${status.current.exampleColumn2}"/></td>
   </tr>--%>
<%--
   <c:set var="dataCount" value='${dataCount+1}'/> --%>

   <!--Uncomment for production-->
   <%--</c:forEach>--%>
   </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>
   <!-- If the collection is empty say no data found -->

   <!--Uncomment for production-->
   <%--<c:if test="${empty catalogitemqcViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>

<!-- Search results end -->
<!--Uncomment for production-->
<%--</c:if>--%>

<!-- Hidden element start -->

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>