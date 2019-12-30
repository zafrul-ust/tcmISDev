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

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/iframeresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<script src="/js/catalog/qualitysummary.js" language="JavaScript"></script>

<title>
<fmt:message key="label.qualitysummary"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};



with(milonic=new menuname("qsMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.viewdocument"/>;url=javascript:openPdf();");
 }
 
 with(milonic=new menuname("specMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="catalogspec.label.viewspec"/>;url=javascript:viewSpec();");
 }
 
 drawMenus();
// -->
</script>

</head>

<body bgcolor="#ffffff" >
<div class="interface" id="mainPage" style="" onmousedown="toggleContextMenu('contextMenu');">
<div id="searchFrameDiv" class="contentArea">

<div class="roundcont filterContainer" style="width:100%;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <table id="searchTable" width="95%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
		<td width="20%" class="optionTitleBoldRight">
		  <fmt:message key="label.catalog"/>:
		</td>
		<td width="20%" class="optionTitleLeft" nowrap="nowrap">
		  ${param.catalogDesc}
		</td>
		<td width="10%" class="optionTitleBoldRight">
		  <fmt:message key="label.partno"/>:
		</td>
		<td width="20%" class="optionTitleLeft" nowrap="nowrap">
		  ${qualitySummaryViewBean.facPartNo}
		</td>
		<c:if test="${!empty qualitySummaryViewBean.qualityIdLabel}">
			<td width="10%" class="optionTitleBoldRight">
			  ${qualitySummaryViewBean.qualityIdLabel}:
			</td>
			<td width="20%" class="optionTitleLeft" nowrap="nowrap">
			  ${qualitySummaryViewBean.facItemColl[0].qualityId}
			</td>
		</c:if>
	</tr>
	<tr>
	<td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.partdescription"/>:
	</td>
	  <td class="optionTitleLeft" colspan="5">
		${qualitySummaryViewBean.facItemColl[0].partDescription}
	  </td>
	</tr>
	<tr>
	<td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.recertinstructions"/>:
	</td>
	<td class="optionTitleLeft">
	  ${qualitySummaryViewBean.facItemColl[0].recertInstructions}
	</td>

    <td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.partrevision"/>:
	</td>
	<td class="optionTitleLeft" colspan="4">
	  ${qualitySummaryViewBean.facItemColl[0].customerPartRevision}
	</td>
    </tr>
	<tr>
	<td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.incomingtesting"/>:
	</td>
	<td class="optionTitleLeft" colspan="5">
	  ${qualitySummaryViewBean.facItemColl[0].incomingTesting}
	</td>
	</tr>
	<tr>
	<td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.materialcategory"/>:
	</td>
	<td class="optionTitleLeft">
	  ${qualitySummaryViewBean.facItemColl[0].materialCategoryName}
	</td>
	<td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.materialsubcategory"/>:
	</td>
	<td class="optionTitleLeft" colspan="4">
	  ${qualitySummaryViewBean.facItemColl[0].materialSubcategoryName}
	</td>
	</tr>
    <%-- moved this data to Approved Work Areas window
    <tr>
	<td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.approvalcode"/>:
	</td>
	<td class="optionTitleLeft" colspan="5">
	  ${qualitySummaryViewBean.facItemColl[0].approvalCode}
	</td>
	</tr>
    --%>
    <c:if test="${qualitySummaryViewBean.facilityId == 'Palmdale'}">
	<tr>
	<td width="20%" class="optionTitleBoldRight">
	  <fmt:message key="label.otr"/>:
	</td>
	<td class="optionTitleLeft" colspan="5">
	  <c:choose>
        <c:when test="${qualitySummaryViewBean.facItemColl[0].hetUsageRecording == 'OTR'}">
            <c:out value="Y"/>
           </c:when>
           <c:otherwise>
             <c:out value=""/>
           </c:otherwise>
      </c:choose>
	</td>
	</tr>
    </c:if>
    </table>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>

<%-- qualityproducts div start --%>
<div class="roundcont filterContainer" style="width:100%;">
	 <div class="roundright">
	   <div class="roundtop">
	     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
	   </div>
		<div class="roundContent">
			<b><fmt:message key="label.qualifiedproducts"/>:</b>
			 <c:if test="${empty qualityProductsRelationColl}" >
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%">
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
			   </c:if>
		  <c:if test="${!empty qualityProductsRelationColl}" >	  
          <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

          <c:set var="colorClass" value=''/>
          <c:set var="dataCount" value='${0}'/>
          
		   <tr>
		   <th width="5%"><fmt:message key="label.itemid"/></th>
		   <th width="5%"><fmt:message key="label.kitmsds"/></th>
		   <th width="5%"><fmt:message key="label.status"/></th>
		   <th width="10%"><fmt:message key="label.description"/></th>
		   <th width="5%"><fmt:message key="label.packaging"/></th>
		   <th width="10%"><fmt:message key="label.manufacturer"/></th>
		   <th width="10%"><fmt:message key="label.mfgpartnr"/></th>
		   <th width="5%"><fmt:message key="label.compmsds"/></th>
		   <th width="5%"><fmt:message key="label.shelflife"/></th>
		   <th width="5%"><fmt:message key="label.storagetemp"/></th>
		   <th width="5%"><fmt:message key="label.recertNo"/></th>
		   <th width="5%"><fmt:message key="label.rtouttime"/></th>
		   </tr>
		   
		   <c:forEach var="qualityProductsBean" items="${qualityProductsRelationColl}" varStatus="status">
		   <c:choose>
		   <c:when test="${status.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
		  <tr align="center" class="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
		  <c:set var="itemId" value='${status.current.itemId}'/>
          <c:set var="kitsMsdsNumber" value='${status.current.customerMixtureNumber}'/>
          <c:if test="${qualityProductsBean.status eq 'A'}" >
		    <c:set var="currentStatus"><fmt:message key="label.approved"/></c:set>
		  </c:if>
		  <c:if test="${qualityProductsBean.status eq 'I'}" >
		    <c:set var="currentStatus"><fmt:message key="label.pending"/></c:set>
		  </c:if>
		  <c:if test="${qualityProductsBean.status eq 'D'}" >
		    <c:set var="currentStatus"><fmt:message key="label.drawdown"/></c:set>
		  </c:if>
          <c:set var="catPartColl"  value='${status.current.catPartColl}'/>
          <bean:size id="listSize" name="catPartColl"/>
          <c:set var="mainRowSpan" value='${status.current.rowSpan}' />

         <input type="hidden" name="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
         <input type="hidden" name="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >
         <input type="hidden" name="itemId<c:out value="${status.index}"/>" value="<c:out value="${itemId}"/>" >
         <input type="hidden" name="kitMsdsNumber<c:out value="${status.index}"/>" value="<c:out value="${kitsMsdsNumber}"/>" >

         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${itemId}"/></td>
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitsMsdsNumber}"/></td>
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${currentStatus}"/></td>
         
		 <c:forEach var="catPartComponentBean" items="${catPartColl}" varStatus="status1">
		  <c:if test="${status1.index > 0 && listSize > 1 }">
		  <tr align="center" class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
		   </c:if>
		   <c:if test="${status1.current.shelfLifeDays != null}">
		    <c:choose>
		    <c:when test="${status1.current.shelfLifeBasis != null}" >
		     <c:choose>
		     <c:when test="${status1.current.shelfLifeDays != '-1'}">
		      <c:set var="shelfLifeDays">
			      <fmt:message key="label.shelflifedaysfrombasis">
			         <fmt:param>${status1.current.shelfLifeDays}</fmt:param>
			         <fmt:param>${status1.current.shelfLifeBasis}</fmt:param>
			      </fmt:message>
		      </c:set>
		       </c:when>
		      <c:otherwise>
			   <c:set var="shelfLifeDays">
			   <fmt:message key="label.indefinite" />
			   </c:set>
			   </c:otherwise>
		      </c:choose>
		   </c:when>
		   <c:otherwise>
		      <c:set var="shelfLifeDays">
		      ${status1.current.shelfLifeDays}
		       <fmt:message key="label.daysshelflife"></fmt:message>
			   </c:set>        
		   </c:otherwise>
		  </c:choose>
		   </c:if>	   
		    <td width="10%"><c:out value="${status1.current.materialDesc}"/></td>
		    <td width="5%"><c:out value="${status1.current.packaging}"/></td>
		    <td width="10%"><c:out value="${status1.current.mfgDesc}"/></td>
		    <td width="10%"><c:out value="${status1.current.mfgPartNo}"/></td>
		    <td width="5%"><c:out value="${status1.current.compMsds}"/></td>
		    <td width="5%"><c:out value="${shelfLifeDays}"/></td>
		    <td width="5%"><c:out value="${status1.current.displayTemp}"/></td>
		    <td width="5%"><c:out value="${status1.current.maxRecertNumber}"/></td>
		    <td width="5%"><c:out value="${status1.current.roomTempOutTime}"/></td>
		
		  <c:if test="${status1.index > 0 || listSize ==1 }">
		   </tr>
		  </c:if>
		 </c:forEach>
	   </c:forEach>
		   
		   </table>
		   </c:if>
		   </div>
		  <div class="roundbottom">
			 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
		  </div>
		</div>
</div>

<!-- quality produts div ends -->

<%-- Spec div start --%>
<div class="roundcont filterContainer" style="width:100%;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
	<div class="roundContent">
	 <b><fmt:message key="label.specification"/>:</b>
			  <c:if test="${empty qualitySummaryViewBean.specificationsColl}" >
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%">
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
			   </c:if>
		    <c:if test="${!empty qualitySummaryViewBean.specificationsColl}" >	  
          <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

          <c:set var="colorClass" value=''/>
          <c:set var="dataCount" value='${0}'/>
          
		   <tr>
		   <th width="15%"><fmt:message key="label.spec"/></th>
		   <th width="15%"><fmt:message key="label.revision"/></th>
		   <th width="15%"><fmt:message key="label.amendment"/></th>
		   <th width="15%"><fmt:message key="label.detail"/></th>
		   <th width="15%"><fmt:message key="label.coc"/></th>
		   <th width="15%"><fmt:message key="label.coa"/></th>
		   </tr>

		   <c:forEach var="facSpecViewBean" items="${qualitySummaryViewBean.specificationsColl}" varStatus="statusS">
		   <c:choose>
		   <c:when test="${statusS.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
		  <tr class="<c:out value="${colorClass}"/>" id="rowIdS<c:out value="${statusS.index}"/>" onmouseup="selectSRow('${statusS.index}')" >
		   <input type="hidden" name="colorClass${statusS.index}" id="colorClass<c:out value="${statusS.index}"/>" value="${colorClass}" />
		   <input type="hidden" id="sDataCount<c:out value="${statusS.index}"/>" value="${statusS.index}">
           <td width="15%"><c:out value="${statusS.current.specName}"/></td>
		   <td width="15%"><c:out value="${statusS.current.specVersion}"/></td>
		   <td width="15%"><c:out value="${statusS.current.specAmendment}"/></td>
		   <td width="15%"><c:out value="${statusS.current.detail}"/></td>
		   <td width="15%"><c:out value="${statusS.current.coc}"/></td>
		   <td width="15%"><c:out value="${statusS.current.coa}"/></td>
		   <input type="hidden" name="facSpecViewBean[<c:out value="${statusS.index}"/>].specId" id="specId<c:out value="${statusS.index}"/>" value="<c:out value="${statusS.current.specId}"/>">
		   <input type="hidden" name="facSpecViewBean[<c:out value="${statusS.index}"/>].content" id="content<c:out value="${statusS.index}"/>" value="<c:out value="${statusS.current.content}"/>">
		   <input type="hidden" name="facSpecViewBean[<c:out value="${statusS.index}"/>].online" id="online<c:out value="${statusS.index}"/>" value="<c:out value="${statusS.current.online}"/>">
		  </tr>
		  </c:forEach>

		</table>
		 </c:if>
	 </div>
  	<div class="roundbottom">
	 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
	</div>
</div>
</div>

<%-- Spec div end --%>

<%-- Purchasing notes div start --%>

<div class="roundcont filterContainer" style="width:100%;">
 	<div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   	</div>
		<div class="roundContent">
		   <b><fmt:message key="label.purchasingnotes"/>:</b>
			  <c:if test="${empty qualitySummaryViewBean.purchasingNotesColl}" >
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%">
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
			   </c:if>
			   <c:if test="${!empty qualitySummaryViewBean.purchasingNotesColl}" >
             <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

            <c:set var="colorClass" value=''/>
           <c:set var="dataCount" value='${0}'/>

		   <tr>
		   <th width="20%"><fmt:message key="label.id"/></th>
		   <th width="75%"><fmt:message key="label.description"/></th>
		   </tr>
   
		   <c:forEach var="vvFlowDownBean" items="${qualitySummaryViewBean.purchasingNotesColl}" varStatus="statusP">
		   <c:choose>
		   <c:when test="${statusP.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
		  <tr class="<c:out value="${colorClass}"/>" id="rowIdP<c:out value="${statusP.index}"/>" onmouseup="selectPRow('${statusP.index}')">
		  <input type="hidden" name="colorClass${statusP.index}" id="colorClass<c:out value="${statusP.index}"/>" value="${colorClass}" />
           <input type="hidden" id="pDataCount<c:out value="${statusP.index}"/>" value="${statusP.index}">
		   <td width="20%"><c:out value="${statusP.current.flowDown}"/></td>
		   <td width="75%"><c:out value="${statusP.current.flowDownDesc}"/></td>
		    <input type="hidden" name="vvFlowDownBean[<c:out value="${statusP.index}"/>].content" id="content<c:out value="${statusP.index}"/>" value="<c:out value="${statusP.current.content}"/>">
		  </tr>
		  </c:forEach>
		</table>
		</c:if>
		 </div>
		  <div class="roundbottom">
			 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
		  </div>
		</div>
</div>
		  
	
<%-- Purchasing notes div end --%>

<%-- Receiving notes div start --%>
<div class="roundcont filterContainer" style="width:100%;">
 	<div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   	</div>
		<div class="roundContent">
		   <b><fmt:message key="label.receivingnotes"/>:</b>
			  <c:if test="${empty qualitySummaryViewBean.receivingNotesColl}" >
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
			     <tr>
			      <td width="100%" >
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
			   </c:if>
			   <c:if test="${!empty qualitySummaryViewBean.receivingNotesColl}" >
               <table width="95%" border="1" cellpadding="0" cellspacing="0" class="tableResults">

		      <c:set var="colorClass" value=''/>
		    <c:set var="dataCount" value='${0}'/>

		   <tr>
		   <th width="30%"><fmt:message key="label.id"/></th>
		   <th width="65%"><fmt:message key="label.description"/></th>
		   </tr>
   
		   <c:forEach var="vvFlowDownBean" items="${qualitySummaryViewBean.receivingNotesColl}" varStatus="statusR">
		   <c:choose>
		   <c:when test="${statusR.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
		  <tr class="<c:out value="${colorClass}"/>" id="rowIdR<c:out value="${statusR.index}"/>" onmouseup="selectRRow('${statusR.index}')">
		   <input type="hidden" name="colorClass${statusR.index}" id="colorClass<c:out value="${statusR.index}"/>" value="${colorClass}" />
           <input type="hidden" id="rDataCount<c:out value="${statusR.index}"/>" value="${statusR.index}">
           <td width="30%"><c:out value="${statusR.current.flowDown}"/></td>
		   <td width="65%"><c:out value="${statusR.current.flowDownDesc}"/></td>
		   <input type="hidden" name="vvFlowDownBean[<c:out value="${statusR.index}"/>].content" id="content<c:out value="${statusR.index}"/>" value="<c:out value="${statusR.current.content}"/>">
		  </tr>
		  <c:set var="dataCount" value='${dataCount+1}'/>
		  </c:forEach>
		</table>
		</c:if>
			  </div>
			  <div id="hiddenElements" style="display: none;">
				  <input name="inventoryGroup" id="inventoryGroup" type="hidden" value='<tcmis:jsReplace value="${param.inventoryGroup}"/>'/>
				  <input type="hidden" name="opsEntityId" id="opsEntityId" value='<tcmis:jsReplace value="${param.opsEntityId}"/>'/>
                  <input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
                  <input type="hidden" name="catPartNo" id="catPartNo" value='<tcmis:jsReplace value="${param.catPartNo}"/>'/>
                  <input type="hidden" name="catalogId" id="catalogId" value='<tcmis:jsReplace value="${param.catalogId}"/>'/>
                  <input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value='<tcmis:jsReplace value="${param.catalogCompanyId}"/>'/>
                  <input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','', personnelBean.companyId)}'/>
              </div>
		  <div class="roundbottom">
			 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
		  </div>
		</div>
</div>		
	
<%-- Receiving notes div end --%>
</table>
</div>
</div>
</body>
</html:html>