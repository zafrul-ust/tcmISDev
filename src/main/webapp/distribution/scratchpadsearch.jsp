<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var priceListArray = [
    <c:forEach var="priceListBean" items="${vvpriceListCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${status.current.priceGroupId}',
          name: '${status.current.priceGroupName}'
        }
    </c:forEach>
   ];  

var csrArray = [
    <c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          opsEntityId:   '${status.current.opsEntityId}',
          id:   '${status.current.csrPersonnelId}',
          name: "<tcmis:jsReplace value="${status.current.csrName}" />"
        }
    </c:forEach>
   ];  
   
priceGroupArray = [
    <c:forEach var="priceListBean" items="${vvpriceListCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          opsEntityId:   '${status.current.opsEntityId}',
          id:   '${status.current.priceGroupId}',
          name: "<tcmis:jsReplace value="${status.current.priceGroupName}" />"
        }
    </c:forEach>
   ];  
   
var currencyArray = [
    <c:forEach var="entityCurrencyBean" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          opsEntityId:   '${status.current.opsEntityId}',
          homeCurrencyId:   '${status.current.homeCurrencyId}'
        }
    </c:forEach>
   ];     
   
/*   
var opsArray = [
    <c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${status.current.opsEntityId}',
          name: '<tcmis:jsReplace value="${status.current.operatingEntityName}"/>'
        }
    </c:forEach>
   ]; 
*/

//No use??
var altCurrencyId = new Array(
<c:forEach var="currencyBean" items="${customerCurrencyColl}" varStatus="status">
    <c:choose>
			<c:when test="${status.index == 0}">
	        "<c:out value="${status.current.currencyId}"/>"
			</c:when>
			<c:otherwise>
	       	,"<c:out value="${status.current.currencyId}"/>"
			</c:otherwise>
	</c:choose>
</c:forEach>
);

//No use??
var altCurrencyXref = new Array(
<c:forEach var="currencyBean" items="${customerCurrencyColl}" varStatus="status">
   <c:choose>
			<c:when test="${status.index == 0}">
	        "<c:out value="${status.current.currencyDisplay}"/>"
			</c:when>
			<c:otherwise>
	       	,"<c:out value="${status.current.currencyDisplay}"/>"
			</c:otherwise>
	</c:choose>
</c:forEach>
);

//No use??
<c:set var="vvPaymentTermsColl" value='${vvPaymentTermsBeanCollection}'/>
<bean:size id="paymentTermsCollSize" name="vvPaymentTermsColl"/>
var altPaymentTerms = new Array(
<c:forEach var="paymentTermsBean" items="${vvPaymentTermsBeanCollection}" varStatus="status">
    <c:choose>
			<c:when test="${status.index == 0}">
	        "<c:out value="${status.current.paymentTerms}"/>"
			</c:when>
			<c:otherwise>
	       	,"<c:out value="${status.current.paymentTerms}"/>"
			</c:otherwise>
	</c:choose>
</c:forEach>
);

// -->
</script> 
<script>
 
function showMRDocuments()
{ 
	var prNumber = document.getElementById("prNumber").value;
	var companyId = document.getElementById("companyId").value;
	var inventoryGroup = document.getElementById("inventoryGroup").value;
    var opsEntityId = document.getElementById("opsEntityId").value;

    var orderType = document.getElementById("orderType").value;
 var loc = "/tcmIS/distribution/showmrdocuments.do?showDocuments=Yes"+
           "&opsEntityId="+opsEntityId+"&prNumber="+prNumber+"&inventoryGroup="+inventoryGroup+"&companyId="+companyId+"&orderType="+orderType+"";
 try {
 	parent.children[parent.children.length] = openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showAllMrDocuments","600","300","yes","80","80");
 }
}

function printMRDocumentLabel()
{
   var prNumber = document.getElementById("prNumber").value;
   var companyId = document.getElementById("companyId").value;
   var inventoryGroup = document.getElementById("inventoryGroup").value;
   var customerName = URLEncode(document.getElementById("customerName").value);    
   var loc = "/tcmIS/hub/printmrpodocumentlabel.do?labelType=MR&paperSize=31"+
           "&customerName="+customerName+"&labelMrPoNumber="+prNumber+"&inventoryGroup="+inventoryGroup+"&companyId="+companyId+"";
 try {
 	openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
 }
}
</script>
 
 
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them.
     If you need to set the widht of the search section you do it on the searchMaskTable in the search.jsp
 -->
<c:set var="acceptInterCompanyMr" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${salesQuoteViewCollection.opsEntityId}" userGroupId="acceptInterCompanyMr">
	<c:set var="acceptInterCompanyMr" value="Y"/>
</tcmis:opsEntityPermission> 
 

<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
     
    <%--  Update Link Div --%>
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%--    
    <tcmis:facilityPermission indicator="true" userGroupId="NewSuppApprover"> 		
  		<c:set var="approvalPermission" value="true"/>
	</tcmis:facilityPermission> --%>
      <div id="gridUpdateLinks" style="display:"> <%-- mainUpdateLinks Begins --%>
        <c:choose>
			<c:when test="${salesQuoteViewCollection.orderType == 'Quote'}">
				<c:choose>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP' && fn:toUpperCase(salesQuoteViewCollection.orderStatus) == 'DRAFT'}">
			        	<span id="deleteSpan"><a href="#" onclick="deleteQuote();"><fmt:message key="label.delete"/></a>
			        						  | <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a></span>
			        	<c:set var="scratchPadPermission" value='N' />
<script language="JavaScript" type="text/javascript">
<!--
	hideResultTable = true;
// -->    	
</script>	
			        </c:when>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP' && !(fn:toUpperCase(salesQuoteViewCollection.orderStatus) == 'DRAFT')}">
			        	&nbsp;
			        	<c:set var="scratchPadPermission" value='N' />
<script language="JavaScript" type="text/javascript">
<!--
	hideResultTable = true;
// -->    	
</script>	
			        </c:when>
			        <c:otherwise>
			        	<span id="saveSpan"><a href="#" onclick="submitSave();"><fmt:message key="label.save"/></a> | 
			        	<span id="noRowSpan11"><a href="#" onclick="confirmQuote();"><fmt:message key="label.confirmquote"/></a> | </span></span>
			        	<span id="noRowSpan12"><a href="#" onclick="newScratchPadTab('Q','newQuoteFromScratchPad');"><fmt:message key="label.createnewquote"/></a> | 
			        						   <a href="#" onclick="newScratchPadTab('B','newQuoteFromScratchPad');"><fmt:message key="label.createblanket"/></a> | </span>
			        	<span id="createMRSpan"><a href="#" onclick="newScratchPadTab('MR','createMRfromQuote');"><fmt:message key="label.createMR"/></a> | </span>
			        		<a href="#" onclick="newScratchPadTab('SP','newScratchPadFromQuote');"><fmt:message key="label.createscratchpad"/></a>
			        	<span id="cancelquoteSpan" style="display:none;"> | <a href="#" onclick="cancelQuote();"><fmt:message key="label.cancelquote"/></a></span>	
			        	<span id="deleteSpan"> | <a href="#" onclick="deleteQuote();"><fmt:message key="label.delete"/></a></span>
			        	<c:if test="${ fn:toUpperCase(salesQuoteViewCollection.status) == 'POSUBMIT' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED'}">
			        	 | <a href="#" onclick="printQuote();"><fmt:message key="label.printconfirmation"/></a>
			        	 | <a href="#" onclick="printQuoteProForma();"><fmt:message key="label.printproforma"/></a>
                         | <a href="#" onclick="printMRDocumentLabel();"><fmt:message key="label.documentlabels" /></a>
                         | <a href="#" onclick="showMRDocuments();"><fmt:message key="label.viewadddocuments" /></a>
                        </c:if>
			        	 | <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a>
			        </c:otherwise>
		  	    </c:choose>
			</c:when>
			<c:when test="${salesQuoteViewCollection.orderType == 'Blanket Order'}">
				<c:choose>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP' && fn:toUpperCase(salesQuoteViewCollection.orderStatus) == 'DRAFT'}">
			        	<span id="deleteSpan"><a href="#" onclick="deleteBlanket();"><fmt:message key="label.delete"/></a>
			        						  | <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a></span>
			        	<c:set var="scratchPadPermission" value='N' />
<script language="JavaScript" type="text/javascript">
<!--
	hideResultTable = true;
// -->    	
</script>	
			        </c:when>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP' && !(fn:toUpperCase(salesQuoteViewCollection.orderStatus) == 'DRAFT')}">
			        	&nbsp;
			        	<c:set var="scratchPadPermission" value='N' />
<script language="JavaScript" type="text/javascript">
<!--
	hideResultTable = true;
// -->    	
</script>	
			        </c:when>
			        <c:otherwise>
			        	<span id="saveSpan"><a href="#" onclick="submitSave();"><fmt:message key="label.save"/></a> | 
			        	<span id="noRowSpan11"><a href="#" onclick="confirmBlanket();"><fmt:message key="label.confirmblanket"/></a> | </span></span>
			        	<span id="noRowSpan12"><a href="#" onclick="newScratchPadTab('B','newQuoteFromScratchPad');"><fmt:message key="label.createnewblanket"/></a> | 
			        	 					   <a href="#" onclick="newScratchPadTab('Q','newQuoteFromScratchPad');"><fmt:message key="label.createquote"/></a> | </span>
			        	<c:if test="${ fn:toUpperCase(salesQuoteViewCollection.status) == 'POSUBMIT' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED'}">		   
			        	  <span id="createMRSpan"><a href="#" onclick="newScratchPadTab('MR','createMRfromQuote');"><fmt:message key="label.createMR"/></a> | </span>
			        	</c:if>
			        	<a href="#" onclick="newScratchPadTab('SP','newScratchPadFromQuote');"><fmt:message key="label.createscratchpad"/></a>
			        	<span id="cancelquoteSpan" style="display:none;"> | <a href="#" onclick="cancelQuote();"><fmt:message key="label.cancelblanket"/></a></span>	
			        	<span id="deleteSpan"> | <a href="#" onclick="deleteQuote();"><fmt:message key="label.delete"/></a></span>
<%--			        <c:if test="${ fn:toUpperCase(salesQuoteViewCollection.status) == 'POSUBMIT' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED'}">
			        	 | <a href="#" onclick="printQuote();"><fmt:message key="label.printconfirmation"/></a>
			        	</c:if> --%>
			        	 | <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a>
			        </c:otherwise>
		  	    </c:choose>
			</c:when>
			<c:when test="${salesQuoteViewCollection.orderType == 'MR'}">
			    <c:choose>
					<c:when test="${salesQuoteViewCollection.releaseStatus == 'Pending Customer'}">
			        	<span id="confirmMRSpan"><a href="#" onclick="confirmMR();"><fmt:message key="label.confirmmr"/></a> | </span>
			        	 | <a href="#" onclick=""><fmt:message key="label.customerreview"/></a>
			        	 | <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a>
			        </c:when>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP' && fn:toUpperCase(salesQuoteViewCollection.orderStatus) == 'DRAFT'}">
			        	<span id="deleteSpan"><a href="#" onclick="deleteMR();"><fmt:message key="label.delete"/></a></span>
			        	| <a href="#" onclick="showMRDocuments();"><fmt:message key="label.viewadddocuments" /></a>
			        	| <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a>
			        	<c:set var="scratchPadPermission" value='N' />
<script language="JavaScript" type="text/javascript">
<!--
	hideResultTable = true;
// -->    	
</script>	
			        </c:when>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP' && !(fn:toUpperCase(salesQuoteViewCollection.orderStatus) == 'DRAFT')}">
			        	&nbsp;
			        	<c:set var="scratchPadPermission" value='N' />
<script language="JavaScript" type="text/javascript">
<!--
	hideResultTable = true;
// -->    	
</script>	
			        </c:when>
			        <c:otherwise>
			        	<span id="saveSpan"><a href="#" onclick="submitSave()"><fmt:message key="label.save"/></a> | </span>
			          <c:if test="${salesQuoteViewCollection.releaseStatus == 'Pending Acceptance' && acceptInterCompanyMr == 'Y'}"><a href="#" onclick="acceptMR();"><fmt:message key="label.acceptmr"/></a> | </c:if>
			        	<span id="confirmMRSpan"><a href="#" onclick="confirmMR();"><fmt:message key="label.confirmmr"/></a><c:if test="${param.fromEmail != 'Y'}"> | </c:if></span>
			          <c:if test="${param.fromEmail != 'Y'}">		          
			        	<span id="noRowSpan2"><a href="#" onclick="newScratchPadTab('MR','createMRfromMR');"><fmt:message key="label.createnewmr"/></a> | 
			        	<a href="#" onclick="newScratchPadTab('Q','newQuoteFromMR');"><fmt:message key="label.createquote"/></a> | 
			        	<a href="#" onclick="newScratchPadTab('B','newQuoteFromMR');"><fmt:message key="label.createblanket"/></a> | 
			        	<a href="#" onclick="newScratchPadTab('SP','newScratchPadFromMR');"><fmt:message key="label.createscratchpad"/></a>
			          </c:if>
			        	<span id="autoAllocateSpan"> | <a href="#" onclick="autoAllocate('MR');"><fmt:message key="label.autoallocate"/></a></span>
			        	<span id="releaseMRSpan" style="display:none;"> | <a href="#" onclick="releaseMR();"><fmt:message key="label.releasemr"/></a></span>
			        	</span>
			        	<span id="deleteSpan"> | <a href="#" onclick="deleteMR();"><fmt:message key="label.delete"/></a></span>
			        	<c:if test="${ fn:toUpperCase(salesQuoteViewCollection.status) == 'POSUBMIT' || fn:toUpperCase(salesQuoteViewCollection.status) == 'CONFIRMED'}">
			        		<span id="printSpan" style="display:;"> | <a href="#" onclick="printMR();"><fmt:message key="label.printconfirmation"/></a>
			        	 	| <a href="#" onclick="printProForma();"><fmt:message key="label.printproforma"/></a></span>
			        	 	<span id="printInvoice" style="display:none;"> | <a href="#" onclick="printInvoice();"><fmt:message key="label.printinvoice"/></a></span>
			        	 	| <a href="#" onclick="printMRDocumentLabel();"><fmt:message key="label.documentlabels" /></a>
			        	</c:if>
			        	| <a href="#" onclick="showMRDocuments();"><fmt:message key="label.viewadddocuments" /></a>
			        	| <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a>
			        </c:otherwise>
		  	    </c:choose>
			</c:when>
		<%--  This is for Scratch Pad  --%>			
			<c:otherwise>
				<c:choose>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP' && fn:toUpperCase(salesQuoteViewCollection.orderStatus) == 'DRAFT'}">
			        	<span id="deleteSpan"><a href="#" onclick="deleteQuote();"><fmt:message key="label.delete"/></a>
			        						  | <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a></span>
			        	<c:set var="scratchPadPermission" value='N' />
<script language="JavaScript" type="text/javascript">
<!--
	hideResultTable = true;
// -->    	
</script>	
			        </c:when>
			        <c:otherwise>
			        	<span id="showlegendLink"><a href="#" onclick="getScratchPadId();"><fmt:message key="label.new"/></a> | 
			        	<a href="#" onclick="submitSave();"><fmt:message key="label.save"/></a> | 
			        	<a href="#" onclick="newScratchPadTab('Q','newQuoteFromScratchPad');"><fmt:message key="label.createquote"/></a> |
			        	<a href="#" onclick="createQuoteOrMRDeleteScratchPad('Q','newQuoteFromScratchPad');"><fmt:message key="label.createquote"/>-<fmt:message key="label.deletescratchpad"/></a> |
			        	<a href="#" onclick="newScratchPadTab('B','newQuoteFromScratchPad');"><fmt:message key="label.createblanket"/></a> |
			        	<a href="#" onclick="createQuoteOrMRDeleteScratchPad('B','newQuoteFromScratchPad');"><fmt:message key="label.createblanket"/>-<fmt:message key="label.deletescratchpad"/></a> |
			        	<a href="#" onclick="newScratchPadTab('MR','createMRfromQuote');"><fmt:message key="label.createMR"/></a> | 
			        	<a href="#" onclick="createQuoteOrMRDeleteScratchPad('MR','createMRfromQuote');"><fmt:message key="label.createMR"/>-<fmt:message key="label.deletescratchpad"/></a> |
<%--		        	<span id="noRowSpan3"><a href="#" onclick="autoAllocate('SP');"><fmt:message key="label.autoallocate"/></a> | 
			        	</span>  --%>
			        	<a href="#" onclick="deleteQuote();"><fmt:message key="label.delete"/></a></span>
			        	| <a href="#" onclick="refresh();"><fmt:message key="label.refresh"/></a>
			        </c:otherwise>
		  	    </c:choose>	       
			</c:otherwise>
		  </c:choose>
     </div> <%-- mainUpdateLinks Ends --%>
	 </div> <%-- boxhead Ends --%>
    
    <!-- Insert all the search option within this div --> 
    <table id="searchTable" width="100%" height="120" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%--     Row 1    --%>
      <tr>
		<input type="hidden" name="user_id" id="user_id" value='${personnelBean.personnelId}'/>
        <td class="optionTitleBoldRight" width="12%" nowrap>
          <fmt:message key="label.customer"/>: </td>
        <td class="optionTitleLeft" width="24%" colspan="2" nowrap>
          <c:choose>
            <c:when test='${newPermission == "Y"}' >
  				<input name="customerName" id="customerName" type="text" maxlength="50" size="30" 
  					value="${salesQuoteViewCollection.customerName}" class="invGreyInputText" readonly/>
		  	 	</td>
		  		<td>
    			<span id="lookupBtnDisplay" ><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/></span>
          		<span id="customerIdDisplay" >
          			<c:if test="${empty salesQuoteViewCollection.abcClassification }">(${salesQuoteViewCollection.customerId} - <fmt:message key="label.na"/>)</c:if>
          			<c:if test="${! empty salesQuoteViewCollection.abcClassification }">(${salesQuoteViewCollection.customerId} - ${salesQuoteViewCollection.abcClassification})</c:if>
          		</span>
  			</c:when>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  				<input name="customerName" id="customerName" type="text" maxlength="50" size="30" 
  					value="${salesQuoteViewCollection.customerName}" class="invGreyInputText" readonly/>
		  		</td>
		  		<td>
    			<span id="customerIdDisplay" >
          			<c:if test="${empty salesQuoteViewCollection.abcClassification }">(${salesQuoteViewCollection.customerId} - <fmt:message key="label.na"/>)</c:if>
          			<c:if test="${! empty salesQuoteViewCollection.abcClassification }">(${salesQuoteViewCollection.customerId} - ${salesQuoteViewCollection.abcClassification})</c:if>
          		</span>
  			</c:when>
  			<c:otherwise>
    			<input name="customerName" id="customerName" type="text" maxlength="50" size="30" 
  					value="${salesQuoteViewCollection.customerName}" class="invGreyInputText" readonly/>
		  		</td>
		  		<td>
          		<span id="lookupBtnDisplay" ><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onClick="lookupCustomer()"/></span>
          		<span id="customerIdDisplay" >
          			<c:if test="${empty salesQuoteViewCollection.abcClassification }">(${salesQuoteViewCollection.customerId} - <fmt:message key="label.na"/>)</c:if>
          			<c:if test="${! empty salesQuoteViewCollection.abcClassification }">(${salesQuoteViewCollection.customerId} - ${salesQuoteViewCollection.abcClassification})</c:if>
          		</span>
  			</c:otherwise>
		  </c:choose>   
          
        </td>
        <td class="optionTitleBoldRight" width="12%" nowrap>
          <c:choose>
	  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.originalSalesQuoteType == "Quote"}' >
	  				<fmt:message key="label.originalquote"/>:
	  			</c:when>
	  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.originalSalesQuoteType == "Blanket Order"}' >
	  				<fmt:message key="label.originalblanket"/>:
	  			</c:when>
	  			<c:when test='${salesQuoteViewCollection.orderType == "Blanket Order"}' >
	  				<fmt:message key="label.blanket"/>:
	  			</c:when>
	  			<c:when test='${salesQuoteViewCollection.orderType == "Quote"}' >
	  				<fmt:message key="label.quote"/>:
	  			</c:when>
	  			<c:otherwise>
	    			&nbsp;
	  			</c:otherwise>
		  </c:choose>
        </td>  
        <td class="optionTitleLeft" width="12%" nowrap>
          <c:choose>
	  			<c:when test='${salesQuoteViewCollection.orderType == "MR"}' >
	  				<a href="#" onclick="openOriginalQuote(); return false;">${salesQuoteViewCollection.originalSalesQuoteId}</a>
	  			</c:when>
	  			<c:when test='${salesQuoteViewCollection.orderType == "Quote" || salesQuoteViewCollection.orderType == "Blanket Order"}' >
	  				${salesQuoteViewCollection.prNumber}
	  			</c:when>
	  			<c:otherwise>
	    			&nbsp;
	  			</c:otherwise>
		  </c:choose>
        </td>
        <td>&nbsp;</td>
        <td width="24%" class="optionTitleBoldRight" nowrap><fmt:message key="label.defaultinvgrp"/>:&nbsp;</td>
      	<td width="16%" nowrap colspan="2">
      	  <input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${salesQuoteViewCollection.inventoryGroup}"/>
    		 <span id="inventoryGroupNameSpan">${salesQuoteViewCollection.inventoryGroupName}</span>
  <%-- 
       	  <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.status == "Confirmed" || salesQuoteViewCollection.status == "Picked" || salesQuoteViewCollection.status == "Shipped" || salesQuoteViewCollection.status == "Invoiced")}' >
  				<input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${salesQuoteViewCollection.inventoryGroup}"/>
    			${salesQuoteViewCollection.inventoryGroup}
  			</c:when>
  			<c:otherwise>
    			<input class="inputBox" type="text" name="inventoryGroup" id="inventoryGroup" value="${salesQuoteViewCollection.inventoryGroup}" maxlength="50" size="20">
  			</c:otherwise>
		  </c:choose>--%>
      	</td>

      </tr>
      
      <%--     Row 2    --%>
      <tr>
        <td class="optionTitleBoldRight" width="12%" nowrap>&nbsp;&nbsp;&nbsp;
          <fmt:message key="label.shiptoname"/>: </td>
        <td class="optionTitleLeft" width="24%" colspan="3" nowrap>
            <span id="locationDescDisplay" >${salesQuoteViewCollection.locationDesc}</span>
        </td>
        <td class="optionTitleBoldRight" width="12%" nowrap>
          <c:if test='${salesQuoteViewCollection.orderType == "MR"}'><fmt:message key="label.order"/>:</c:if> 
        </td>
        <td class="optionTitleBoldLeft" width="12%" nowrap>
		  <c:if test='${salesQuoteViewCollection.orderType == "MR"}'><input name="order" id="order" type="text" maxlength="20" size="20" 
  			value="${salesQuoteViewCollection.prNumber}" class="invGreyInputText" readonly/></c:if>      
        </td>
        <td>&nbsp;</td>
        <td class="optionTitleBoldRight" id="materialPriceLabel" width="20%" nowrap>
          <fmt:message key="label.materialprice"/>: </td>
        <td class="optionTitleLeft" id="materialPriceDisplay" width="20%" colspan="2">
        	<span id="materialPriceSpan"></span>
        </td>
      </tr>
      
      <%--     Row 3    --%>
      <tr>
        <td class="optionTitleBoldRight" width="12%" nowrap>
          <fmt:message key="label.contact"/>: </td>
        <td class="optionTitleLeft" width="24%" colspan="2" nowrap>
          <c:choose>
		  		<c:when test="${salesQuoteViewCollection.requestor == '' || salesQuoteViewCollection.requestor eq null}">
		      		<input type="hidden" name="requestor" id="requestor" value="0">
		  		</c:when>
		  		<c:otherwise>
		    		<input type="hidden" name="requestor" id="requestor" value="${salesQuoteViewCollection.requestor}">
		  		</c:otherwise>
		  </c:choose>
          <c:choose>
            <c:when test='${newPermission == "Y"}' >
  				<input type="text" name="requestorName" id="requestorName" class="invGreyInputText" maxlength="61" size="30" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorName}"/>"/>
		  		</td>
		  		<td nowrap>
    			<span id="contactLookupBtnDisplay" style="display:;"><input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchContact" id="searchContact" value="" OnClick="getContact()" type="button">
		  		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="clearContact();return false;"><fmt:message key="label.clear"/> </button></span>
  			</c:when>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && salesQuoteViewCollection.status == "Confirmed") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  				<input type="hidden" name="requestorName" id="requestorName" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorName}"/>"/>
    			<span id="requestorNameDisplay"><tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorName}"/></span>
		    	</td>
		  		<td>&nbsp;
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId == "CASH_SALES"}' >
  				<input name="requestorName" id="requestorName" type="text" maxlength="61" size="30" onchange="requestorChanged();"
  					value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorName}"/>" class="inputBox"/>
		  		</td>
		  		<td nowrap>
  				<span id="contactLookupBtnDisplay" style="display:none;"><input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchContact" id="searchContact" value="" OnClick="getContact()" type="button">
		  		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="clearContact();return false;"><fmt:message key="label.clear"/> </button></span>
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId != "CASH_SALES" && salesQuoteViewCollection.requestor == 0}' >
  				<input name="requestorName" id="requestorName" type="text" maxlength="61" size="30" onchange="requestorChanged();"
  					value="" class="inputBox"/>
		  		</td>
		  		<td nowrap>
  				<span id="contactLookupBtnDisplay" style="display:none;"><input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchContact" id="searchContact" value="" OnClick="getContact()" type="button">
		  		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="clearContact();return false;"><fmt:message key="label.clear"/> </button></span>
  			</c:when>
  			<c:otherwise>
    			<input name="requestorName" id="requestorName" type="text" maxlength="61" size="30" onchange="requestorChanged();"
  					value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorName}"/>" class="invGreyInputText" readonly />  
		  		</td>
		  		<td nowrap>    
		  		<span id="contactLookupBtnDisplay" style="display:;"><input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchContact" id="searchContact" value="" OnClick="getContact()" type="button">
		  		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="clearContact();return false;"><fmt:message key="label.clear"/> </button></span>
  			</c:otherwise>
		  </c:choose>   
        </td>
        <td class="optionTitleBoldRight" width="12%" nowrap>
		  <c:choose>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR"}' >
  	 			<fmt:message key="label.orderdate"/>: 
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "Quote"}' >
  	 			<fmt:message key="label.quotedate"/>: 
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "Blanket Order"}' >
    			<fmt:message key="label.blanketdate"/>: 
  			</c:when>
  			<c:otherwise>
    			&nbsp;
  			</c:otherwise>
		  </c:choose>    
        </td>
        <td class="optionTitleLeft" id="orderDateDisplay" width="12%" nowrap>
          <fmt:formatDate var="fmtSubmittedDate" value="${salesQuoteViewCollection.submittedDate}" pattern="${dateFormatPattern}"/>
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || salesQuoteViewCollection.orderType == "Quote" || salesQuoteViewCollection.orderType == "Blanket Order" || (salesQuoteViewCollection.status == "Confirmed" || salesQuoteViewCollection.status == "Picked" || salesQuoteViewCollection.status == "Shipped" || salesQuoteViewCollection.status == "Invoiced")}' >
  	 			<input type="hidden" name="submittedDate" id="submittedDate" value="${fmtSubmittedDate}"/>  
    			${fmtSubmittedDate}
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && (fn:toUpperCase(salesQuoteViewCollection.status) == "POSUBMIT" || fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  	 			<input type="hidden" name="submittedDate" id="submittedDate" value="${fmtSubmittedDate}"/>  
    			${fmtSubmittedDate}
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR"}' >
    			<input type="hidden" name="submittedDate" id="submittedDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" >
    			<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "Scratch Pad"}' >
    			&nbsp;
  			</c:when>
  			<c:otherwise>
    			<input class="inputBox pointer" type="text" name="submittedDate" id="submittedDate" readonly onClick="return getCalendar(document.genericForm.submittedDate);" value="${fmtSubmittedDate}" size="10" maxlength="10">
  			</c:otherwise>
		  </c:choose>    
        </td>
        <td>&nbsp;</td>
        <td class="optionTitleBoldRight" id="expectedMarginLabel" width="20%" nowrap>
          <fmt:message key="label.expectedmargin"/>: </td>
        <td class="optionTitleLeft" id="expectedMarginDisplay" width="20%" colspan="2">
        	<span id="expectedMarginSpan"></span>
        </td>
      </tr>
      
      <%--     Row 4    --%>
     <tr>
        <td class="optionTitleBoldRight" width="12%"><fmt:message key="label.telephone"/>:</td>
        <td class="optionTitleLeft" width="8%" nowrap>
          <c:choose>
            <c:when test='${scratchPadPermission == "N"}' >
  	 			<input type="hidden" name="requestorPhone" id="requestorPhone" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorPhone}"/>"/>  
    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorPhone}"/>
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId == "CASH_SALES"}' >
  				<input name="requestorPhone" id="requestorPhone" type="text" maxlength="30" size="15" onchange="requestorChanged();"
  					value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorPhone}"/>" class="inputBox" />
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId != "CASH_SALES" && salesQuoteViewCollection.requestor == 0}' >
  				<input type="text" name="requestorPhone" id="requestorPhone" maxlength="30" size="15" class="invGreyInputText" value="" readonly="readonly"/>
  			</c:when>
  			<c:otherwise>
    			<input type="text" name="requestorPhone" id="requestorPhone" maxlength="30" size="15" class="invGreyInputText" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorPhone}"/>" readonly="readonly"/>
  			</c:otherwise>
		  </c:choose>    
  		</td>
  		<td class="optionTitleBoldRight" width="2%" nowrap>	
          <fmt:message key="label.fax"/>: 
        </td>
  		<td class="optionTitleLeft" width="8%" nowrap>
  		  <c:choose>
  		    <c:when test='${scratchPadPermission == "N"}' >
  	 			<input type="hidden" name="requestorFax" id="requestorFax" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorFax}"/>"/>  
    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorFax}"/>
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId == "CASH_SALES"}' >
  				<input name="requestorFax" id="requestorFax" type="text" maxlength="30" size="15" onchange="requestorChanged();"
  					value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorFax}"/>" class="inputBox" />
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId != "CASH_SALES" && salesQuoteViewCollection.requestor == 0}' >
  				<input type="text" name="requestorFax" id="requestorFax" maxlength="30" size="15" class="invGreyInputText" value="" readonly="readonly"/>
  			</c:when>
  			<c:otherwise>
    			<input type="text" name="requestorFax" id="requestorFax" maxlength="30" size="15" class="invGreyInputText" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorFax}"/>" readonly="readonly"/>
  			</c:otherwise>
		  </c:choose>    	
        </td>
        <td class="optionTitleBoldRight" width="12%" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	          <c:choose>
	  			<c:when test='${salesQuoteViewCollection.orderType == "MR" || salesQuoteViewCollection.orderType == "Blanket Order"}' >
	  				<fmt:message key="label.customerpo"/>
	  			</c:when>
	  			<c:otherwise>
	    			<fmt:message key="label.customerref"/>
	  			</c:otherwise>
			  </c:choose>:
			  <c:if test='${salesQuoteViewCollection.orderType != "Scratch Pad"}' >
			  	<span style='font-size:12.0pt;color:red'>*</span> 
     		  </c:if>
		</td>
        <td class="optionTitleLeft" id="customerPoDisplay" width="12%" nowrap>
<%--          <c:if test='${salesQuoteViewCollection.billToCompanyId == "CASH_SALES"}' > --%>
	          <c:choose>
<%--	  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "MR" && fn:toUpperCase(salesQuoteViewCollection.status) == "POSUBMIT")}' > --%>
 	  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "MR" && (fn:toUpperCase(salesQuoteViewCollection.orderStatus) == "FULLY SHIPPIED" || fn:toUpperCase(salesQuoteViewCollection.orderStatus) == "Fully Cancelled" || fn:toUpperCase(salesQuoteViewCollection.billedFlag) == "Y"))}' >
	  				<input type="hidden" name="poNumber" id="poNumber" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.poNumber}"/>"/>
	    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.poNumber}"/>
	  			</c:when>
	  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.originalSalesQuoteType == "Blanket Order"}' >
	  				<input type="hidden" name="poNumber" id="poNumber" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.poNumber}"/>"/>
	    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.poNumber}"/>
	  			</c:when>
	  			<c:when test='${salesQuoteViewCollection.orderType == "Blanket Order" && (fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED" || fn:toUpperCase(salesQuoteViewCollection.status) == "POSUBMIT")}' >
	  				<input type="hidden" name="poNumber" id="poNumber" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.poNumber}"/>"/>
	    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.poNumber}"/>
	  			</c:when>
	  			<c:otherwise>
	    			<input class="inputBox" type="text" name="poNumber" id="poNumber" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.poNumber}"/>" size="20" maxlength="30">
	  			</c:otherwise>
			  </c:choose>  
<%--		  </c:if>   --%>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td class="optionTitleBoldRight" id="addlChargeLabel" nowrap><fmt:message key="label.addlcharges"/>:</td>
        <td class="optionTitleLeft"  id="addlChargeDisplay" colspan="2" nowrap>
          <input type="hidden" name="totalHeaderCharge" id="totalHeaderCharge" value="${salesQuoteViewCollection.totalHeaderCharge}"/>
		  
		  <fmt:formatNumber var="totalHeaderCharge" value="${salesQuoteViewCollection.totalHeaderCharge}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>
          <span id="headerCharges">${totalHeaderCharge}</span>&nbsp;&nbsp;
          <c:if test='${salesQuoteViewCollection.orderType != "Blanket Order"}'>
          	<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="addHeaderCharges();return false;"><fmt:message key="label.hdcharges"/> </button>
          </c:if>
        </td>
      </tr> 
      
      <%--     Row 5    --%>
      <tr id="row5" >
        <td class="optionTitleBoldRight" width="12%"><fmt:message key="label.email"/>:</td>
        <td class="optionTitleLeft" width="15%" colspan="3" nowrap >
        <input type="hidden" name="emailOrderAck" id="emailOrderAck" value="${salesQuoteViewCollection.emailOrderAck}"/>
          <c:choose>
            <c:when test='${scratchPadPermission == "N"}' >
  	 			<input type="hidden" name="requestorEmail" id="requestorEmail" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/>"/>  
    			<span id="emailLink"><a href='mailto:<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/>'><tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/></a></span>
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId == "CASH_SALES"}' >
  				<input name="requestorEmail" id="requestorEmail" type="text" maxlength="80" size="54" onchange="requestorChanged();"
  					value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/>" class="inputBox" /> 
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.billToCompanyId != "CASH_SALES" && salesQuoteViewCollection.requestor == 0}' >
  				<input type="text" name="requestorEmail" id="requestorEmail" maxlength="80" size="54" class="invGreyInputText" value="" readonly="readonly"/> 
  			</c:when>
  			<c:otherwise>
  				<input type="hidden" name="requestorEmail" id="requestorEmail" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/>"/>
  				<span id="emailLink"><a href='mailto:<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/>'><tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/></a></span>
    			<%--<input type="text" name="requestorEmail" id="requestorEmail" maxlength="80" size="30" class="invGreyInputText" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.requestorEmail}"/>" readonly="readonly"/>--%>
    	  		<%--<span id="requestorEmailDisplay">${salesQuoteViewCollection.requestorEmail}</span>--%>
  			</c:otherwise>
		  </c:choose>    
        </td>
        <td class="optionTitleBoldRight" width="12%" id="quoteExpireLabel"  nowrap>
          <c:choose>
  			<c:when test='${salesQuoteViewCollection.orderType == "Quote"}' >
  				<fmt:message key="label.quoteexpires"/>:
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "Blanket Order"}' >
  				<fmt:message key="label.blanketexpires"/>:
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR"}' >
  				<fmt:message key="label.podate"/>:
  			</c:when>
  			<c:otherwise>
    			&nbsp;
  			</c:otherwise>
		  </c:choose>
        </td>
        <td class="optionTitleLeft" width="12%" id="quoteExpireDisplay" nowrap>
          <input name="toDate" id="toDate" type="hidden"
	   		value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
	   		
    	  <fmt:formatDate var="fmtExpireDate" value="${salesQuoteViewCollection.quoteExpireDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="fmtCustomerPODate" value="${salesQuoteViewCollection.customerPoDate}" pattern="${dateFormatPattern}"/>
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  				<input type="hidden" name="quoteExpireDate" id="quoteExpireDate"  value="${fmtExpireDate}"/>
    			${fmtExpireDate}
  			</c:when>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "MR" && fn:toUpperCase(salesQuoteViewCollection.status) == "POSUBMIT")}' >
  				<input type="hidden" name="fmtCustomerPODate" id="fmtCustomerPODate"  value="${fmtCustomerPODate}"/>
    			${fmtCustomerPODate}
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "Quote" || salesQuoteViewCollection.orderType == "Blanket Order"}' >
				 <input type="hidden" name="today" id="today" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>' />
				 <c:choose>
  				  <c:when test="${salesQuoteViewCollection.quoteExpireDate != null}" >
				    <input class="inputBox pointer" type="text" name="quoteExpireDate" id="quoteExpireDate" onchange="quoteExpireDateChanged();" readonly onClick="return getCalendar(document.genericForm.quoteExpireDate,$('today'));" value="${fmtExpireDate}" size="10" maxlength="10">
				  </c:when>
				  <c:otherwise>
				    <input class="inputBox pointer" type="text" name="quoteExpireDate" id="quoteExpireDate" onchange="quoteExpireDateChanged();" readonly onClick="return getCalendar(document.genericForm.quoteExpireDate,$('today'));" value="<tcmis:getDateTag numberOfDaysFromToday="30" datePattern="${dateFormatPattern}"/>" size="10" maxlength="10">
				  </c:otherwise>
				</c:choose>
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.customerPoDate != null &&  salesQuoteViewCollection.customerPoDate != ""}' >
  				<input class="inputBox pointer" type="text" name="customerPoDate" id="customerPoDate" readonly onClick="return getCalendar(document.genericForm.customerPoDate);" value="${fmtCustomerPODate}" size="10" maxlength="10">
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR"}' >
  				<input class="inputBox pointer" type="text" name="customerPoDate" id="customerPoDate" readonly onClick="return getCalendar(document.genericForm.customerPoDate);" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" size="10" maxlength="10">
  			</c:when>
  			<c:otherwise>
    			&nbsp;
  			</c:otherwise>
		  </c:choose>
        </td>
		<td>&nbsp;</td>
        <td class="optionTitleBoldRight" id="totalOrderLabel" nowrap><fmt:message key="label.total"/>:</td>
        <td class="optionTitleLeft"  id="totalOrderDisplay" colspan="2">
			<span id="totalSpan"></span>
        </td>
      </tr> 
      
      <tr id="show" style="display:;">
      	<td colspan="11" >
           &nbsp;
        </td>
        <td class="optionTitleBoldRight"  onclick="showDiv();"  >
            <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
        </td>
      </tr>  

      <%--     Row 6    --%>
      <tr id="row6" style="display:none;">
        <td class="optionTitleBoldRight"><fmt:message key="label.billto"/>:</td>
        <td colspan="3">
          <input type="hidden" id="billToCompanyId" name="billToCompanyId" value="${salesQuoteViewCollection.billToCompanyId}"/>
          <input type="hidden" id="billToLocationId" name="billToLocationId" value="${salesQuoteViewCollection.billToLocationId}"/>
          <input type="hidden" id="billToCountry" name="billToCountry" value="${salesQuoteViewCollection.billToCountry}"/>
          <input type="hidden" id="billToState" name="billToState" value="${salesQuoteViewCollection.billToState}"/>
          <input type="hidden" id="billToCity" name="billToCity" value="${salesQuoteViewCollection.billToCity}"/>
          <input type="hidden" id="billToZip" name="billToZip" value="${salesQuoteViewCollection.billToZip}"/>
          <input type="hidden" id="billToUpdatable" name="billToUpdatable" value="${salesQuoteViewCollection.billToUpdatable}"/>
          <input type="hidden" id="billToAddressLine1" name="billToAddressLine1" value="${salesQuoteViewCollection.billToAddressLine1}"/>
          <span id="billToAddressLine1Span"><tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine1}" /></span>   
        </td>
        <td class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.shipto"/>:</td>
        <td  width="12%" nowrap>
        	<input type="hidden" name="shipToCompanyId" id="shipToCompanyId" value="${salesQuoteViewCollection.shipToCompanyId}"/> 
            <input type="hidden" id="shipToLocationId" name="shipToLocationId" value="${salesQuoteViewCollection.shipToLocationId}"/>
            <input type="hidden" id="shipToCountry" name="shipToCountry" value="${salesQuoteViewCollection.shipToCountry}"/>
            <input type="hidden" id="shipToState" name="shipToState" value="${salesQuoteViewCollection.shipToState}"/>
            <input type="hidden" id="shipToCity" name="shipToCity" value="${salesQuoteViewCollection.shipToCity}"/>
            <input type="hidden" id="shipToZip" name="shipToZip" value="${salesQuoteViewCollection.shipToZip}"/>
            <input type="hidden" id="shipToUpdatable" name="shipToUpdatable" value="${salesQuoteViewCollection.shipToUpdatable}"/>
        	<input type="hidden" name="shipToAddressLine1" id="shipToAddressLine1" value="${salesQuoteViewCollection.shipToAddressLine1}"/>
    		<span id="shipToAddressLine1Display"><tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine1}" /></span>
    	</td>
  		<td>
		  <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.status == "Confirmed" || salesQuoteViewCollection.status == "Picked" || salesQuoteViewCollection.status == "Shipped" || salesQuoteViewCollection.status == "Invoiced")}' >
  				&nbsp;
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.originalSalesQuoteType == "Blanket Order"}' >
	  			&nbsp;
	  		</c:when>
	  		<c:when test='${salesQuoteViewCollection.orderType == "Blanket Order" && (fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED" || fn:toUpperCase(salesQuoteViewCollection.status) == "POSUBMIT")}' >
	  			&nbsp;
	  		</c:when>
  			<c:otherwise>
  				<span id="shipToBtnDisplay" style="display:;"><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" 
             		onmouseout="this.className='lookupBtn'" name="getShipTo" value="..." align="right" onClick="changeShipTo();"/>&nbsp;&nbsp;&nbsp;</span>
  			</c:otherwise>
		  </c:choose>
        </td>
        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.revenueentity"/>:</td>
      	<td width="10%" colspan="2">
      		<input id="opsEntityId" name="opsEntityId" type="hidden" value="${salesQuoteViewCollection.opsEntityId}"/>
      		<span id="opsEntityNameSpan" style="display:none;">
    			${salesQuoteViewCollection.operatingEntityName}
    		</span>
  			  <span id="opsEntityIdDropDownSpan" style="display:;">
    			<select name="headerOpsEntityId" id="headerOpsEntityId" class="selectBox" onchange="setOpsEntityId();buildCsrDropDown('',csrArray);buildPriceListDropDown('',priceGroupArray);setDefaultPGINV('',currencyArray);">
                    <c:forEach var="cbean" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
           		    	<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="GenerateOrders"> 
  							<option value="${status.current.opsEntityId}" <c:if test="${salesQuoteViewCollection.opsEntityId eq status.current.opsEntityId}">selected</c:if>>${status.current.operatingEntityName}</option>
						</tcmis:opsEntityPermission>
    				</c:forEach>
    			</select>
			  </span>		  
        </td>
      </tr>  
 
      <tr id="row7" style="display:none;">
        <td class="optionTitleBoldRight">
          	<c:choose>
  				<c:when test="${salesQuoteViewCollection.billToCompanyId == 'CASH_SALES'}" >
	  				<span id="inputBillToBtnSpan" style="display:;">
  				</c:when>
  				<c:otherwise>
  					<span id="inputBillToBtnSpan" style="display:none;">
  				</c:otherwise>
		  	</c:choose>
		  	<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="inputAddress('B');return false;"><fmt:message key="label.onetime"/> </button>
		  </span>
	    </td>
        <td colspan="3">        
          <input type="hidden" id="billToAddressLine2" name="billToAddressLine2" value="${salesQuoteViewCollection.billToAddressLine2}"/>
          <span id="billToAddressLine2Span"><tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine2}" /></span>   
        </td>
        <td class="optionTitleRight">
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.status == "Confirmed" || salesQuoteViewCollection.status == "Picked" || salesQuoteViewCollection.status == "Shipped" || salesQuoteViewCollection.status == "Invoiced")}' >
  				&nbsp;
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.originalSalesQuoteType == "Blanket Order" }' >
  				&nbsp;
  			</c:when>
  			<c:otherwise>
  			    <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="inputAddress('S');return false;"><fmt:message key="label.onetime"/> </button>
  			</c:otherwise>
		  </c:choose>
        </td>      
        <td nowrap>
          <input type="hidden" name="shipToAddressLine2" id="shipToAddressLine2" value="${salesQuoteViewCollection.shipToAddressLine2}"/>
          <span id="shipToAddressLine2Display"><tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine2}" /></span>
		</td>
		<td>&nbsp;</td>
        <td class="optionTitleBoldRight" width="10%" nowrap>
          <fmt:message key="label.enteredby"/>: </td>
        <td class="optionTitleLeft" width="10%" colspan="2" nowrap>
        	<input type="hidden" name="submittedBy" id="submittedBy" value="${salesQuoteViewCollection.submittedBy}"/>
        		<span id="enteredBySpan"><tcmis:inputTextReplace value="${salesQuoteViewCollection.submittedByName}" /></span>
        </td>

      </tr>
      
      <tr id="row8" style="display:none;">
        <td class="optionTitleBoldRight">&nbsp;</td>
        <td colspan="3">         
          <input type="hidden" id="billToAddressLine3" name="billToAddressLine3" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine3}" />"/>
          <span id="billToAddressLine3Span"><tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine3}" /></span>   
        </td> 
        <td>&nbsp;</td>       
        <td nowrap>
          <input type="hidden" name="shipToAddressLine3" id="shipToAddressLine3" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine3}" />"/>
    		<span id="shipToAddressLine3Display"><tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine3}" /></span> 
        </td>
        <td>&nbsp;</td>
        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.csr"/>:&nbsp;</td>
      	<td width="10%" colspan="2">
       	  <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && salesQuoteViewCollection.status == "Confirmed")  || (salesQuoteViewCollection.orderType == "Blanket Order" && salesQuoteViewCollection.status == "Confirmed")}' >
  				<input type="hidden" name="customerServiceRepId" id="customerServiceRepId" value="${salesQuoteViewCollection.customerServiceRepId}"/>
    			${salesQuoteViewCollection.customerServiceRepName}
  			</c:when>
  			<c:otherwise>
  				<select name="customerServiceRepId" id="customerServiceRepId" class="selectBox">
  				    <c:if test="${ salesQuoteViewCollection.customerServiceRepId == null || salesQuoteViewCollection.customerServiceRepId == '' }">
                          <option value=""><fmt:message key="label.pleaseselect"/></option>
                    </c:if>
                    <c:set var="submittedByFound" value="false"/>
                    <c:set var="csrFound" value="false"/>
                    <c:set var="loggedInPersonFound" value="false"/>
					<c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
                       <c:if test="${ (csrBean.opsEntityId == salesQuoteViewCollection.opsEntityId) && (salesQuoteViewCollection.customerServiceRepId == csrBean.csrPersonnelId)}" >
                                <c:set var="csrFound" value="true"/>
                          </c:if>
                    </c:forEach>     
                    <c:if test="${!csrFound}">
                    <c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
                        <c:if test="${ (csrBean.opsEntityId == salesQuoteViewCollection.opsEntityId) && (salesQuoteViewCollection.submittedBy == csrBean.csrPersonnelId)}" >
                                <c:set var="submittedByFound" value="true"/>
                          </c:if>
                    </c:forEach>
                    </c:if>
                    <c:if test="${!submittedByFound && !csrFound}">
                     <c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
                        <c:if test="${ (csrBean.opsEntityId == salesQuoteViewCollection.opsEntityId) && (personnelBean.personnelId == csrBean.csrPersonnelId) && (salesQuoteViewCollection.customerServiceRepId == null)}" >
                                <c:set var="loggedInPersonFound" value="true"/>
                          </c:if>                          
                    </c:forEach>
                    </c:if>
                    <c:forEach var="csrBean" items="${csrCollection}" varStatus="status">
                        <c:if test="${ csrBean.opsEntityId == salesQuoteViewCollection.opsEntityId }">
                        <c:choose>
                            <c:when test="${csrFound && (salesQuoteViewCollection.customerServiceRepId == csrBean.csrPersonnelId)}">
	                           <option value="${csrBean.csrPersonnelId}" selected="selected">${csrBean.csrName}</option>
	                        </c:when>
	                        <c:when test="${submittedByFound && (salesQuoteViewCollection.submittedBy == csrBean.csrPersonnelId)}">
	                         <option value="${csrBean.csrPersonnelId}" selected="selected">${csrBean.csrName}</option>
	                        </c:when>
	                        <c:when test="${loggedInPersonFound && (personnelBean.personnelId == csrBean.csrPersonnelId) && (salesQuoteViewCollection.customerServiceRepId == null)}"> 
	                         <option value="${csrBean.csrPersonnelId}" selected="selected">${csrBean.csrName}</option>
	                        </c:when>
	                        <c:otherwise>
	                         <option value="${csrBean.csrPersonnelId}">${csrBean.csrName}</option>
	                        </c:otherwise>
                         </c:choose>
                         </c:if>
                    </c:forEach>
		  		</select>
  			</c:otherwise>
		  </c:choose>   
      	</td>
      </tr>
      
      <tr id="row9" style="display:none;">
        <td class="optionTitleBoldRight">&nbsp;</td>
        <td colspan="3">         
          <input type="hidden" id="billToAddressLine4" name="billToAddressLine4" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine4}" />"/>
          <span id="billToAddressLine4Span"><tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine4}" /></span>    
        </td>
        <td>&nbsp;</td>      
        <td nowrap>
          <input type="hidden" name="shipToAddressLine4" id="shipToAddressLine4" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine4}" />"/>
    		<span id="shipToAddressLine4Display"><tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine4}" /></span>
        </td>
        <td>&nbsp;</td>
        <td width="10%" class="optionTitleBoldRight" nowrap><fmt:message key="label.salesagent"/>:&nbsp;</td>
      	<td width="10%">
    		<span id="salesAgentNameSpan">${salesQuoteViewCollection.salesAgentName}</span>
      	</td>
      </tr>
      
      <tr id="row10" style="display:none;">
        <td class="optionTitleBoldRight">&nbsp;</td>
        <td colspan="3">         
          <input type="hidden" id="billToAddressLine5" name="billToAddressLine5" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine5}" />"/>
          <span id="billToAddressLine5Span"><tcmis:inputTextReplace value="${salesQuoteViewCollection.billToAddressLine5}" /></span>     
        </td>
        <td>&nbsp;</td>       
        <td nowrap>
          <input type="hidden" name="shipToAddressLine5" id="shipToAddressLine5"  value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine5}" />"/>
    		<span id="shipToAddressLine5Display"><tcmis:inputTextReplace value="${salesQuoteViewCollection.shipToAddressLine5}" /></span>
        </td>
        <td>&nbsp;</td>
        <td width="10%" class="optionTitleBoldRight" ><fmt:message key="label.fieldsalesrep"/>:&nbsp;</td>
      	<td width="10%" colspan="2">
    	  <span id="fieldSalesRepNameSpan">${salesQuoteViewCollection.fieldSalesRepName}</span>
      	</td>
      </tr>
      
      <%--     Row 7    --%>
      <tr id="row11" style="display:none;">
        <td class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.creditterms"/>:</td>
        <td class="optionTitleLeft" width="12%" colspan="3">
          <input type="hidden" name="paymentTerms" id="paymentTerms" value="${salesQuoteViewCollection.paymentTerms}"/>
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "MR" && fn:toUpperCase(salesQuoteViewCollection.status) == "POSUBMIT")}' >
    			${salesQuoteViewCollection.paymentTerms}
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.originalSalesQuoteType == "Blanket Order" }' >
  				${salesQuoteViewCollection.paymentTerms}
  			</c:when>
  			<c:otherwise>
    			<select name="paymentTermsDisplay" id="paymentTermsDisplay" class="selectBox" onchange="getPaymentTerms();">
					<c:forEach var="paymentTermsBean" items="${vvPaymentTermsBeanCollection}" varStatus="status">
						<c:choose>
                        <c:when test="${(salesQuoteViewCollection.paymentTerms == status.current.paymentTerms) && salesQuoteViewCollection.billToCompanyId != 'CASH_SALES'}">
          					<option value="${status.current.paymentTerms}" selected>${status.current.paymentTerms}</option>
          				</c:when>
                        <c:when test="${(salesQuoteViewCollection.defaultPaymentTerms == status.current.paymentTerms) && salesQuoteViewCollection.billToCompanyId != 'CASH_SALES'}">
          					<option value="${status.current.paymentTerms}" >${status.current.paymentTerms}</option>
          				</c:when>
                        <c:when test="${status.current.prepayTerms == 'Y'}">
                          <c:choose>
                            <c:when test="${(salesQuoteViewCollection.paymentTerms == status.current.paymentTerms)}">
          					<option value="${status.current.paymentTerms}" selected>${status.current.paymentTerms}</option>
          				    </c:when>
                            <c:otherwise>
                                <option value="${status.current.paymentTerms}">${status.current.paymentTerms}</option>
                            </c:otherwise>
                          </c:choose>
                          </c:when>
                        </c:choose>    
                    </c:forEach>
		  		</select> 
<%-- 		  		
<script language="JavaScript" type="text/javascript">
<!--
	for(i=0;i< document.getElementById("paymentTerms").length;i++) {
     	  if( "${salesQuoteViewCollection.paymentTerms}" == document.getElementById("paymentTerms").options[i].value ) {
     			 document.getElementById("paymentTerms").selectedIndex = i;
     			 break;
     	  }
	}     
// -->    	
</script>	
--%>		  
  			</c:otherwise>
		  </c:choose>    
        </td>
        <td width="12%" class="optionTitleBoldRight" nowrap><fmt:message key="label.attn"/>:</td>
        <td class="optionTitleLeft" width="15%" nowrap>
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && salesQuoteViewCollection.status == "Confirmed") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  				<input type="hidden" name="endUser" id="endUser" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.endUser}"/>"/>
    			<c:out value="${salesQuoteViewCollection.endUser}"  escapeXml="true"/>
  			</c:when>
  			<c:otherwise>
    			<input class="inputBox" type="text" name="endUser" id="endUser" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.endUser}"/>" size="20" maxlength="100">
  			</c:otherwise>
		  </c:choose>   
        </td>
        <td>&nbsp;</td>
        <td id="shippingMethodLabel" class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.shippingmethod"/>:</td>
        <td id="shippingMethodDisplay" class="optionTitleLeft" width="15%" colspan="2" nowrap>
        
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && salesQuoteViewCollection.status == "Confirmed") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  				<input type="hidden" name="carrierServiceType" id="carrierServiceType" value="${salesQuoteViewCollection.carrierServiceType}"/>
    			${salesQuoteViewCollection.carrierServiceType}
  			</c:when>
  			<c:otherwise>
    			<select name="carrierServiceType" id="carrierServiceType" class="selectBox">
					<c:forEach var="carrierServiceTypeBean" items="${vvCarrierServiceTypeCollection}" varStatus="status">
						<option value="${status.current.carrierServiceType}">${status.current.serviceTypeDesc}</option>
					</c:forEach>
		  		</select> 
<script language="JavaScript" type="text/javascript">
<!--

	for(var i=0;i< document.getElementById("carrierServiceType").length;i++) {
		  if( "${salesQuoteViewCollection.carrierServiceType}".length == 0) {
		  		if(document.getElementById("carrierServiceType").options[i].value == 'Standard') {
     	   		  document.getElementById("carrierServiceType").selectedIndex = i;
     			  break;
     			} 
     	  }   	  
     	  else if( "${salesQuoteViewCollection.carrierServiceType}" == document.getElementById("carrierServiceType").options[i].value ) {
     			 document.getElementById("carrierServiceType").selectedIndex = i;
     			 break;
     	  }
	}     
// -->    	
</script>
  			</c:otherwise>
		  </c:choose>       
		  
        </td>
      </tr> 
      
      <%--     Row 8    --%>
      <tr id="row12" style="display:none;">
        <td class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.creditlimit"/>:</td>
        <td class="optionTitleLeft" width="12%" colspan="3">
          <span id="creditLimitDisplay">${salesQuoteViewCollection.homeCurrencyId}&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${salesQuoteViewCollection.creditLimitInHomeCurrency}"/></span>
          <c:if test="${salesQuoteViewCollection.homeCurrencyId != salesQuoteViewCollection.currencyId && salesQuoteViewCollection.creditLimitInOrderCurrency != '' && salesQuoteViewCollection.creditLimitInOrderCurrency != null}">
          	&nbsp;(${salesQuoteViewCollection.currencyId}&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${salesQuoteViewCollection.creditLimitInOrderCurrency}"/>)
          </c:if>
        </td>
        <td width="12%" class="optionTitleBoldRight" nowrap><fmt:message key="label.shipcomplete"/>:</td>
        <td class="optionTitleLeft" width="15%" nowrap>
          <span id="shipCompleteDisplay">${salesQuoteViewCollection.shipComplete}</span>
        </td>
        <td>&nbsp;</td>
        <td id="carrierLabel" class="optionTitleBoldRight" width="12%"><fmt:message key="label.carrier"/>:</td>
        <td id="carrierDisplay" class="optionTitleLeft" width="15%" nowrap >
          <input type="hidden" name="carrierAccountId" id="carrierAccountId" value="<c:out value="${salesQuoteViewCollection.carrierAccountId}"/>">
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && salesQuoteViewCollection.status == "Confirmed") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.carrierName}"/>
  			</c:when>
  			<c:otherwise>
    	  		<input name="carrierName" id="carrierName" type="text" maxlength="18" size="15" 
  					value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.carrierName}"/>" class="invGreyInputText" readonly/> 
  		</td>
  		<td nowrap>     
		  		<input class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="searchCarrier" id="searchCarrier" value="" OnClick="getCarrier()" type="button">
		  		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="clearCarrier();return false;"><fmt:message key="label.clear"/> </button>
  			</c:otherwise>
		   </c:choose>   
        </td>
      </tr> 
      
      <%--     Row 9    --%>
      <tr id="row13" style="display:none;">
        <td class="optionTitleBoldRight" width="12%"><fmt:message key="label.availablecredit"/>:</td>
        <td class="optionTitleLeft" width="15%" nowrap colspan="3">
          <input type="hidden" name="availableCredit" id="availableCredit" value="${salesQuoteViewCollection.availableCredit}"/>
          <c:choose>
			<c:when test="${salesQuoteViewCollection.availableCredit < 0 || fn:toUpperCase( salesQuoteViewCollection.withinTerms) == 'N'}">
				<span id="availableCreditSpan"><label class="red"><tcmis:isCNServer indicator="false">USD</tcmis:isCNServer><tcmis:isCNServer>CNY</tcmis:isCNServer> <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${salesQuoteViewCollection.availableCredit}"/></label></span>
	        	<span id="custCreditOverLimit" onclick="showOverCreditLimitWin();"> <label class="red">(!)</label></span>
			</c:when>
			<c:otherwise>
				<span id="availableCreditSpan"><tcmis:isCNServer indicator="false">USD</tcmis:isCNServer><tcmis:isCNServer>CNY</tcmis:isCNServer> <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${salesQuoteViewCollection.availableCredit}"/></span>
	       		<span id="custCreditOverLimit"></span>
			</c:otherwise>
		  </c:choose>
		  <c:if test="${salesQuoteViewCollection.availableCreditInOrdCrncy != '' && salesQuoteViewCollection.availableCreditInOrdCrncy != null}">
          	&nbsp;(${salesQuoteViewCollection.currencyId}&nbsp;<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${salesQuoteViewCollection.availableCreditInOrdCrncy}"/>)
          </c:if>
        </td>
        <td class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.chargefreight"/>:</td>
        <td class="optionTitleBoldLeft" width="12%" nowrap>
        	<c:choose>
			  <c:when test="${salesQuoteViewCollection.chargeFreight == 'Y'}">
	        	<input name="chargeFreight" type="checkbox" class="radioBtns" value="" checked id="chargeFreight" disabled="disabled" >
			  </c:when>
			  <c:otherwise>
	       		<input name="chargeFreight" type="checkbox" class="radioBtns" value="" id="chargeFreight" disabled="disabled" >
			  </c:otherwise>
			</c:choose>
		</td>
		<td>&nbsp;</td>
		<td id="accountLabel" class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.accountnumber"/>:</td>
        <td id="accountDisplay" class="optionTitleBoldLeft" width="15%" colspan="2" nowrap >
           <input name="carrierAccountNumber" id="carrierAccountNumber" type="text" maxlength="18" size="15" 
  			  value="${salesQuoteViewCollection.carrierAccountNumber}" class="invGreyInputText" readonly/>      
        </td>
      </tr> 
      
      <%--     Row 10   --%>
      <tr id="row14" style="display:none;">
        <td class="optionTitleBoldRight" width="12%"><fmt:message key="label.pricelist"/>:</td>
        <td class="optionTitleLeft" colspan="3">
          <input type="hidden" name="priceGroupId" id="priceGroupId" value="${salesQuoteViewCollection.priceGroupId}"/>
          <span id="headerPriceGroupNameSpan">
          	<c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	 	   		<c:if test="${salesQuoteViewCollection.priceGroupId eq cbean.priceGroupId}">${cbean.priceGroupName}</c:if>
    		</c:forEach>
    	  </span>
          <span id="hideOriPG" style="display:none;">
          <span id="priceGroupSpan" style="display:none;">
          	<c:forEach var="cbean" items="${vvpriceListCollection}" varStatus="status">
	 	   		<c:if test="${salesQuoteViewCollection.priceGroupId eq cbean.priceGroupId}">${cbean.priceGroupName}</c:if>
    		</c:forEach>
    	  </span>
  		  <span id="priceGroupIdDropDownSpan" style="display:;">
  		      <select name="headerPriceGroupId" id="headerPriceGroupId" onchange="setPriceGroupId();" class="selectBox">
  				  <c:if test="${ salesQuoteViewCollection.customerServiceRepId == null || salesQuoteViewCollection.customerServiceRepId == '' }">
                        <option value=""><fmt:message key="label.pleaseselect"/></option>
                  </c:if>
			      <c:forEach var="priceListbean" items="${vvpriceListCollection}" varStatus="status">
				    <c:if test="${ status.current.opsEntityId == salesQuoteViewCollection.opsEntityId }">
                        <c:choose>
                          <c:when test='${(salesQuoteViewCollection.priceGroupId == priceListbean.priceGroupId)}' >
    			                <option value="${status.current.priceGroupId}" selected="selected">${status.current.priceGroupName}</option>
  			              </c:when>
                          <c:otherwise>
                            <option value="${status.current.priceGroupId}">${status.current.priceGroupName}</option>
                  	      </c:otherwise>
                        </c:choose>
                      </c:if>
				  </c:forEach>
		  		</select>
		  </span>
		  </span>
		</td>
        <td class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.defaultshelflife"/>:</td>
        <td class="optionTitleLeft" width="15%" nowrap >
          <span id="shelfLifeRequiredDisplay">${salesQuoteViewCollection.shelfLifeRequired}
              <c:if test="${!empty salesQuoteViewCollection.shelfLifeRequired}"> %</c:if>
          </span>
        </td>
        <td>&nbsp;</td>
        <td id="accountContactLabel" class="optionTitleBoldRight" width="12%" nowrap><fmt:message key="label.carriercontact"/>:</td>
        <td id="accountContactDisplay" class="optionTitleLeft" width="15%" colspan="2" nowrap >
          <c:choose>
  			<c:when test='${scratchPadPermission == "N"|| (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  				<input type="hidden" name="carrierContact" id="carrierContact" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.carrierContact}"/>"/>
    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.carrierContact}"/>
  			</c:when>
  			<c:otherwise>
    			<input class="inputBox" type="text" name="carrierContact" id="carrierContact" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.carrierContact}"/>" size="20" maxlength="100">
  			</c:otherwise>
		  </c:choose>   	  
        </td>
      </tr>    
      
      <%--     Row 11    --%>
       <tr id="row15" style="display:none;">
        <td class="optionTitleBoldRight" width="12%"><fmt:message key="label.status"/>:</td>
        <td class="optionTitleLeft" colspan="3">
        	 	<c:choose>
			        <c:when test="${fn:toUpperCase(salesQuoteViewCollection.creditStatus) == 'STOP'}">
			        	${salesQuoteViewCollection.orderStatus} <label class="red">(STOP)</label>
			        </c:when>
			        <c:when test="${salesQuoteViewCollection.orderType == 'MR' && !(salesQuoteViewCollection.releaseStatus == null)}">
			        	${salesQuoteViewCollection.orderStatus} (${salesQuoteViewCollection.releaseStatus})
			        </c:when>
			        <c:otherwise>
			        	${salesQuoteViewCollection.orderStatus}
			        </c:otherwise>
		  	    </c:choose>
        </td>
        <td class="optionTitleBoldRight" id="originalQuoteLabel"  nowrap><fmt:message key="label.currency"/>:</td>
        <td class="optionTitleLeft" id="originalQuoteDisplay" >
        	<input type="hidden" name="currencyId" id="currencyId" value="${salesQuoteViewCollection.currencyId}"/>
        	<fmt:formatNumber var="fmtOrderToHomeConversionRate" value="${salesQuoteViewCollection.orderToHomeConversionRate}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
  			  <span id="currencyIdSpan" style="display:none;">
    			${salesQuoteViewCollection.currencyId}
    		  </span>
  			  <span id="currencyIdDropDownSpan" style="display:;">
    			<select name="headerCurrencyId" id="headerCurrencyId" onchange="getCurrencyId();" class="selectBox">
					<c:forEach var="currencyBean" items="${customerCurrencyColl}" varStatus="status">
						<option value="${status.current.currencyId}"  <c:if test="${currencyBean.defaultCurrency == 'Y'}">selected="selected"</c:if> >${status.current.currencyDisplay}</option>
					</c:forEach>
		  		</select> 
		  		<c:if test="${salesQuoteViewCollection.homeCurrencyId != salesQuoteViewCollection.currencyId}">
          			&nbsp;(${fmtOrderToHomeConversionRate})
          		</c:if>
<script language="JavaScript" type="text/javascript">
<!--
	setCurrencyId('${salesQuoteViewCollection.opsEntityId}',currencyArray);
	for(i=0;i< document.getElementById("headerCurrencyId").length;i++) {
     	  if( "${salesQuoteViewCollection.currencyId}" == document.getElementById("headerCurrencyId").options[i].value && "${salesQuoteViewCollection.currencyId}" != null && "${salesQuoteViewCollection.currencyId}" != "") {
     			 document.getElementById("headerCurrencyId").selectedIndex = i;
     			 break;
     	  }
	}     

	if(document.getElementById("headerCurrencyId").selectedIndex  == 0)
		$("currencyId").value = $v("headerCurrencyId");	
// -->    	
</script>	
			  </span>		  
        </td>
        <td>&nbsp;</td>
        <td class="optionTitleBoldRight" id="originalQuoteLabel" width="12%" nowrap><fmt:message key="label.shippingterms"/>:</td>
        <td class="optionTitleLeft" id="originalQuoteDisplay" colspan="2">
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "Blanket Order" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED") || (salesQuoteViewCollection.orderType == "MR" && fn:toUpperCase(salesQuoteViewCollection.status) == "POSUBMIT")}' >
  				<input type="hidden" name="incoTerms" id="incoTerms" value="${salesQuoteViewCollection.incoTerms}"/>
    			${salesQuoteViewCollection.incoTerms}
  			</c:when>
  			<c:when test='${salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.originalSalesQuoteType == "Blanket Order"}' >
  				<input type="hidden" name="incoTerms" id="incoTerms" value="${salesQuoteViewCollection.incoTerms}"/>
    			${salesQuoteViewCollection.incoTerms}
  			</c:when>
  			<c:otherwise>
    			<select name="incoTerms" id="incoTerms" class="selectBox">
					<c:forEach var="freightBean" items="${vvFreightOnBoardBeanCollection}" varStatus="status">
						<c:if test="${status.current.status == 'ACTIVE'}">
						<c:choose>
  				  			<c:when test='${salesQuoteViewCollection.incoTerms eq status.current.freightOnBoard}' >
                         		<option value="${status.current.freightOnBoard}" selected>${status.current.description}</option>
                        	</c:when>
  				  			<c:when test='${salesQuoteViewCollection.incoTerms == null && status.current.freightOnBoard == "EXW"}' >
  				  				<option value="${status.current.freightOnBoard}" selected>${status.current.description}</option>
  				  			</c:when>
  				  			<c:otherwise>
  				  				<option value="${status.current.freightOnBoard}" >${status.current.description}</option>
  				  			</c:otherwise>
  				  		</c:choose>
                        </c:if>
                    </c:forEach>
		  		</select> 
  			</c:otherwise>
		  </c:choose>   
        </td>
      </tr>
      
      <%--     Row 12    --%>
      <tr id="row16" style="display:none;">
        <td class="optionTitleBoldRight" width="12%"><fmt:message key="label.notes"/>:</td>
        <td class="optionTitleLeft" width="100%" colspan="6" nowrap >
    		<table>
    		  <tr>
    			<c:choose>
  				  <c:when test='${scratchPadPermission == "N" && salesQuoteViewCollection.orderType != "Scratch Pad"}' >
  					<td><a href="#" onclick="showNotes(messagesData.orderinternal,'internalNoteDiv','readOnly');"><fmt:message key="label.orderinternal"/></a> </td>
	    			<td><a href="#" onclick="showNotes(messagesData.orderexternal,'externalNoteDiv','readOnly');"><fmt:message key="label.orderexternal"/></a> </td>
  				  </c:when>
  				  <c:when test='${scratchPadPermission != "N" && salesQuoteViewCollection.orderType != "Scratch Pad"}' >
  					<td><a href="#" onclick="showNotes(messagesData.orderinternal,'internalNoteDiv');"><fmt:message key="label.orderinternal"/></a> </td>
	    			<td><a href="#" onclick="showNotes(messagesData.orderexternal,'externalNoteDiv');"><fmt:message key="label.orderexternal"/></a> </td>
  				  </c:when>
  				  <c:otherwise>
    				<td><fmt:message key="label.orderinternal"/></td>
	    			<td><fmt:message key="label.orderexternal"/></td>
    			  </c:otherwise>
		  	  </c:choose>   		
	    			<td>
	    				  <c:choose>
				  			<c:when test='${salesQuoteViewCollection.customerNote != null && salesQuoteViewCollection.customerNote != ""}' >
				  				<a href="#" onclick="showNotes(messagesData.billto,'billtoNoteDiv','readOnly');"><fmt:message key="label.billto"/></a> 
				  			</c:when>
				  			<c:otherwise>
				  				<fmt:message key="label.billto"/>
				  			</c:otherwise>
						  </c:choose>         
	    			</td>
	    			<td>
	    				  <c:choose>
				  			<c:when test='${salesQuoteViewCollection.orderType != "MR" && salesQuoteViewCollection.shiptoNote != null && salesQuoteViewCollection.shiptoNote != ""}' >
				  				<a href="#" onclick="showNotes(messagesData.shipto,'shiptoNoteDiv','readOnly');"><fmt:message key="label.shipto"/></a> 
				  			</c:when>
				  			<c:when test='${scratchPadPermission != "N" && salesQuoteViewCollection.orderType == "MR"}' >
				  				<a href="#" onclick="showNotes(messagesData.shipto,'orderShiptoNoteDiv');"><fmt:message key="label.shipto"/></a> 
				  			</c:when>
				  			<c:when test='${scratchPadPermission == "N" && salesQuoteViewCollection.orderType == "MR"}' >
				  				<a href="#" onclick="showNotes(messagesData.shipto,'orderShiptoNoteDiv','readOnly');"><fmt:message key="label.shipto"/></a> 
				  			</c:when>
				  			<c:otherwise>
				  				<fmt:message key="label.shipto"/>
				  			</c:otherwise>
						  </c:choose>   
	    			</td>
	    			<td>
	    				  <c:choose>
				  			<c:when test='${scratchPadPermission != "N" && salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.releaseStatus == "Pending Acceptance"}' >
				  				<a href="#" onclick="showNotes(messagesData.holdcomments,'holdCommentsDiv');"><fmt:message key="label.holdcomments"/></a> 
				  			</c:when>
				  			<c:when test='${scratchPadPermission == "N" && salesQuoteViewCollection.orderType == "MR" && salesQuoteViewCollection.releaseStatus == "Pending Acceptance"}' >
				  				<a href="#" onclick="showNotes(messagesData.holdcomments,'holdCommentsDiv','readOnly');"><fmt:message key="label.holdcomments"/></a> 
				  			</c:when>
				  			<c:otherwise>
				  				<fmt:message key="label.holdcomments"/>
				  			</c:otherwise>
						  </c:choose>   
	    			</td>
	    	  </tr>
	    	</table>
        </td> 
        <td class="optionTitleBoldRight" id="materialRequestOriginLabel" width="12%" nowrap>
        	<c:if test='${salesQuoteViewCollection.orderType == "MR"}'>
        		<fmt:message key="label.materialrequestorigin"/>:
        	</c:if>
        </td>
        <td class="optionTitleLeft" id="materialRequestOriginDisplay" colspan="2">
          		<c:if test='${salesQuoteViewCollection.orderType == "MR"}'>
	          		<input type="hidden" name="materialRequestOrigin" id="materialRequestOrigin" value="<tcmis:jsReplace value="${salesQuoteViewCollection.materialRequestOrigin}" />"/>
	    			<tcmis:jsReplace value="${salesQuoteViewCollection.materialRequestOrigin}" />
	    		</c:if>
        </td>   
      </tr>
<%--      
      <tr id="row16" style="display:none;">
        <td class="optionTitleBoldRight" width="12%"><fmt:message key="label.specialinstructions"/>:</td>
        <td class="optionTitleLeft" width="15%" colspan="9" nowrap >
          <c:choose>
  			<c:when test='${scratchPadPermission == "N" || (salesQuoteViewCollection.orderType == "Quote" && fn:toUpperCase(salesQuoteViewCollection.status) == "CONFIRMED")}' >
  				<input type="hidden" name="specialInstructions" id="specialInstructions" value="<tcmis:inputTextReplace value="${salesQuoteViewCollection.specialInstructions}"/>"/>
    			<tcmis:inputTextReplace value="${salesQuoteViewCollection.specialInstructions}"/>
  			</c:when>
  			<c:otherwise>
    			<TEXTAREA name="specialInstructions" id="specialInstructions" onKeyDown='limitText("specialInstructions", "<fmt:message key="label.specialinstructionswithnobreak"/>", 1000);' onKeyUp='limitText("specialInstructions", "<fmt:message key="label.specialinstructionswithnobreak"/>", 1000);' class="inputBox" COLS=150 ROWS=2><tcmis:inputTextReplace value="${salesQuoteViewCollection.specialInstructions}"/></TEXTAREA>
  			</c:otherwise>
		  </c:choose>         
        </td>    
      </tr> --%> 
      
      <tr id="hide" style="display:none;">
      	<td colspan="9" >
           &nbsp;
        </td>
        <td class="optionTitleBoldRight">
          <div style="display:" onclick="hideDiv();">
            <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
          </div> 
        </td>
      </tr> 
<%--  </div> --%>
    </table> 
  
      
 </div>  
    <!-- End search options -->
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>  
   </div>
 </div>
</div>
</td></tr>
</table>
 