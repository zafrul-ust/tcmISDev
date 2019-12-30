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
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
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
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<!--Uncomment for production-->
<%--<tcmis:form action="/catalogitemqc.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">--%>

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="1100" border="1" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

    <table width="100%" border="1" cellpadding="0" cellspacing="0" class="tableResults">

      <tr class="alt">
 		<td width="12%" class="optionTitleBoldRight" colspan="2">
  			<fmt:message key="label.requestid"/>:
 		</td>
 		<td width="15%" class="alignLeft">70563</td>
 
  		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.requestorphone"/>:
 		</td>
 		<td width="15%" class="alignLeft">512-519-3918</td>

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.company"/>:
 		</td>
 		<td width="10%" class="alignLeft">RAYTHEON</td>

 		<td width="15%" class="optionTitleBoldRight">
  			<fmt:message key="label.datesubmitted"/>:
 		</td>
 		<td width="15%" class="alignLeft" colspan="2">09 November  2006 12:11 PM</td> 		
      </tr>
    
    
      <tr class="">
 		<td width="12%" class="optionTitleBoldRight" colspan="2">
  			<fmt:message key="label.requestor"/>:
 		</td>
 		<td width="15%" class="alignLeft">Ngo, Trong</td> 		
 
  		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.requestoremail"/>:
 		</td>
 		<td width="15%" class="alignLeft">tngo@haastcm.com</td> 		

 		<td width="10%" class="optionTitleBoldRight">
  			<fmt:message key="label.catalogid"/>:
 		</td>
 		<td width="10%" class="alignLeft">Andover</td>

 		<td width="15%" class="optionTitleBoldRight">
  			<fmt:message key="label.evaluation"/>:
 		</td>
 		<td width="15%" class="alignLeft" colspan="2">n</td> 		
 		
      </tr>
        
      <tr class="alt">
 		<td width="12%" class="optionTitleBoldRight" colspan="2">
  			<fmt:message key="label.userpartnumber"/>:
 		</td>
		<td width="68%" class="optionTitleLeft" colspan="3">       		
   			<input class="inputBox" type="text" name="userPartNumber" id="userPartNumber" size="20">
 		</td>
 		
 		<td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.startingview"/>:
 		</td>
 		<td width="15%" class="alignLeft" colspan="4">New Part</td>		
      </tr>
      
 <%--  %><INPUT type="text" CLASS="INVISIBLEHEADBLUE"  name="item_id" value="
206041
" SIZE="10" readonly><INPUT TYPE="button" CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'" name="searchitemidlike" value="..." OnClick="getitemID(this.form)"></TD>

 		--%>
      <tr class="">
 		<td width="12%" class="optionTitleBoldRight">
  			<fmt:message key="label.itemid"/>:
 		</td>
		<td width="5%" class="optionTitleLeft" >       		
			<c:out value="${status.current.itemId}"/>206041
 		</td>

 		<td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.category"/>:
 		</td> 		
		<td width="10%" class="optionTitleLeft">      
 			<select name="category" id="category" class="selectBox">
 			
    			<option value="">Please Select</option>
    			<option value="1">Acids, Alkali, Salts, & Other</option> 		
    			<option value="22">Activators, Agents, Catalysts, & Other Misc Parts</option>
    			<option value="2">Adhesives & Sealants</option> 		

 			</select>
 		</td>    

 		<td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.type"/>:
 		</td> 		
		<td width="10%" class="optionTitleLeft">      
 			<select name="type" id="type" class="selectBox">
    			<option value="AN">AN</option>
    			<option value="AR">AR</option> 		
    			<option value="BG">BG</option>
    			<option value="CF">CF</option> 		
 			</select>
 		</td>    
 		
 		<td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.caseqty"/>:
 		</td>
		<td width="10%" class="optionTitleLeft" >       		
   			<input class="inputBox" type="text" name="caseQty" id="caseQty" size="20">
 		</td>
 		
 		<td width="5%" class="optionTitleBoldRight">
  			<fmt:message key="label.samplesize"/>:
 		</td> 		
		<td width="10%" class="optionTitleLeft">      
 			<%-- <html:select property="isSampleSize" styleId="isSampleSize" styleClass="selectBox">
				<html:option value="N" selected="selected" ><fmt:message key="label.no"/></html:option>
				<html:option value="Y"><fmt:message key="label.yes"/></html:option>
  			</html:select>	--%>	 			
 			<select name="isSampleSize" id="isSampleSize" class="selectBox">
				<option value="N" selected="selected" ><fmt:message key="label.no"/></option>
				<option value="Y"><fmt:message key="label.yes"/></option>
  			</select>		 			
 		</td>    
      </tr>
 		
      <tr class="alt">
 		<td width="12%" class="optionTitleBoldRight" colspan="2">
  			<fmt:message key="label.inseparablekit"/>:
 		</td>
		<td width="88%" class="optionTitleLeft" colspan="8">       		
   			<input class="radioBtns" type="checkbox" name="inseparableKit" id="inseparableKit" value="Y" >
 		</td>
      </tr>

      <tr class="">
 		<td width="12%" class="optionTitleBoldRight" colspan="2">
  			<fmt:message key="label.itemnotes"/>:<a href="javascript:getItemNotes('1')" style="color:#0000ff;cursor:pointer"><img id="itemnotesimage1" src="/images/plus.jpg" alt="Item Notes"></a><br>
 			<input name="addNotes" id="addNotes" type="button" value="<fmt:message key="label.add"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">  			
 		</td>
		<td width="88%" class="optionTitleLeft" colspan="8">       		
 		</td>
      </tr>
					<%-- %><TR>
					<TD HEIGHT="20" WIDTH="10%" CLASS="Inwhitel">
					<B>Item Notes: </B> <A HREF="javascript:getItemNotes('1')" STYLE="color:#0000ff;cursor:pointer"><IMG ID="itemnotesimage1" src="/images/plus.jpg" alt="Item Notes"></A><BR><INPUT TYPE="button" CLASS="SUBMIT" onmouseover="className='SUBMITHOVER'" onMouseout="className='SUBMIT'" name='additemnotes' OnClick=editItemNotes('1') value="Add">
					</TD>
					<TD HEIGHT="20" COLSPAN="9" CLASS="Inwhiter" ID="row4detaildivrow11"></TD>
					<INPUT TYPE="hidden" NAME="itemnotestatus1" VALUE="No"></TR> --%>
 	  	  
 	  <%-- control-buttons row... --%>
 	  
 	  <tr class="alt">      
        <td width="24%" class="optionTitleLeft" colspan="10"> 		
			<input name="save" id="save" type="submit" value="<fmt:message key="label.save"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
			<input name="approve" id="approve" type="button" value="<fmt:message key="label.approve"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
			<input name="reverse" id="reverse" type="button" value="<fmt:message key="label.reverse"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
			<input name="viewRequest" id="viewRequest" type="button" value="<fmt:message key="label.viewrequest"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
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
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>