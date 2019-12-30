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
		
		<tcmis:gridFontSizeCss /> <%-- Sets CSS based on the user's preffered font size and which application he is viewing --%>
	
		<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
		<script type="text/javascript" src="/js/common/commonutil.js"></script>
	
		<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
		<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

		<%-- Right Mouse Click (RMC) Menu support  --%>
		<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
		<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
		<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
		<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
        <script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
        <%@ include file="/common/rightclickmenudata.jsp" %>

        <%-- Form popup Calendar support --%>
		<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
		<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
		<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
	
		<%-- Grid support --%>
        <script type="text/javascript" src="/js/common/standardGridmain.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
        <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

        <script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
		<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

        <script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
        <script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>

        <%-- Page specific javascript --%>
		<script type="text/javascript" src="/js/catalog/catalogspecqc.js"></script>
		<script type="text/javascript" src="/js/client/catalog/shownewchemappdetail.js"></script>
		<script type="text/javascript" src="/js/client/catalog/chemicalapprovaldata.js"></script>


<title>
<fmt:message key="label.catalogspecrequest"/>
</title>

</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();" >

<tcmis:form action="/catalogspecqc.do" onsubmit="return submitFrameOnlyOnce();">

<script language="JavaScript" type="text/javascript">
<!--
var specLibrary = [
    {text:'<fmt:message key="label.selectOne"/>', value:''},
	<c:forEach var="bean" items="${specLibraryColl}" varStatus="status">
		<c:if test="${ status.index !=0 }">,</c:if>
		{
		    text:'${bean.specLibrary}',
            value:'${bean.specLibrary}'
		}
	</c:forEach>
];

<%-- Define the right click menus --%>
with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=YYY;url=javascript:doNothing();");
}

with(milonic=new menuname("emptyMenu")){
   top="offset=2";
   style=submenuStyle;
   itemheight=17;
   aI("text=;");
}


drawMenus();
	         

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",	 
submit:"<fmt:message key="button.submit"/>",
save:"<fmt:message key="label.save"/>",
addmodifyspecdetail:"<fmt:message key="label.addmodifyspecdetail"/>",
deleteuploadedimage:"<fmt:message key="label.deleteuploadedimage"/>",
uploadSpec:"<fmt:message key="label.uploadspecification"/>",
viewSpec:"<fmt:message key="label.viewspecification"/>",
addSpec:"<fmt:message key="label.addspec"/>",
deleteSpec:"<fmt:message key="label.deletespecification"/>",        
modifySpec:"<fmt:message key="label.modifyspec"/>",
id:"<fmt:message key="label.id"/>",
library:"<fmt:message key="label.library"/>",
name:"<fmt:message key="label.name"/>",
savebeforeaddmodifydetail:"<fmt:message key="label.savebeforeaddmodifydetail"/>"
};

//rowSpanMap contains an entry for each row with its associated rowspan 1, 2, ... or 0 for child row
//rowSpanClassMap contains the color (odd, even) for the row
//rowSpanCols contains the indexes of the columns that span
var rowSpanMap = new Array();
var rowSpanClassMap = new Array();
var rowSpanCols = [6];
var rowSpanLvl2Map = new Array();
var rowSpanLvl2Cols = [7,8,9,10,11,12,13,14,15];


var gridConfig = {
		divName:'specRequestDiv', // the div id to contain the grid.
		beanData:'jsonSpecRequestData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'specBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'specRequestConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:true,			 // this page has rowSpan > 1 or not.
		noSmartRender: false, // Explicitly enable smartrender by setting this to false for rowspans
		singleClickEdit: true,
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
		onRightClick:selectRightclick,  // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
	    selectChild: 2
	};
	
var specRequestConfig = [
  {columnId:"permission"},			<%-- Update Permission for entire line --%>
  {columnId:"specLibraryPermission"},
  {columnId:"specNamePermission"},
  {columnId:"specTitlePermission"},
  {columnId:"specVersionPermission"},
  {columnId:"specAmendmentPermission"},
  {
	columnId:"lineItem",
	columnName:'<fmt:message key="label.line"/>',
	align:'right',
	width:2
  },
  {
	columnId:"specId",
	columnName:'<fmt:message key="label.id"/>',
	width:10
  },
  {
	columnId:"specLibrary",
	columnName:'<fmt:message key="label.library"/>',
	type:'hcoro',
    tooltip:'Y',  
    width:20,
    onChange:lineUpdated,
    permission:true
  },
  {
	columnId:"specName",
	columnName:'<fmt:message key="label.name"/>',
    type:'hed',
    tooltip:'Y',
    width:15,
    size:50,
    onChange:updateSpecId,
    permission:true
  },  
  {
	columnId:"specTitle",
	columnName:'<fmt:message key="label.title"/>',
    type:'hed',
    tooltip:'Y',
	width:20,
    size:50,
    permission:true
  },
  {
	columnId:"specVersion",
	columnName:'<fmt:message key="label.revision"/>',
	type:'hed',
	width:8,
    size:20,
    onChange:updateSpecId,
    permission:true
  },
  {
	columnId:"specAmendment",
	columnName:'<fmt:message key="label.amendment"/>',
    type:'hed', 
    width:8,
    size:20,
    onChange:updateSpecId,
    permission:true
  },
  {columnId:"specDatePermission"},
  {
      columnId:"specDate",
      columnName:'<fmt:message key="label.specdate"/>',
      type:'hcal',
      permission:true,
      submit:true
  },
  {
	columnId:"coc",
	columnName:'<fmt:message key="label.coc"/>',
	type:'hchstatus',
	align:'center',
	width:3
  },
  {
	columnId:"coa",
	columnName:'<fmt:message key="label.coa"/>',
	type:'hchstatus',
	align:'center',
	width:3
  },
  {
	columnId:"itar",
	columnName:'<fmt:message key="label.itar"/>',
	align:'center',
	width:3
  },
  {
	columnId:"specDetail",
	columnName:'<fmt:message key="label.detail"/>',
    tooltip:'Y',
    width:30
  },
  {
	columnId:"onLine"
  },
  {
	columnId:"content"
  },
  {
	columnId:"specSource"
  },
  {
    columnId:"dataSource"
  },
  {
	columnId:"oldSpecId"
  },
  {
	columnId:"updated"
  }
  ];
	

// -->
</script>

<div class="interface" id="resultsPage">

	<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
	So this is just used to feed the pop-up in the main page.
	Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display:none;">
	  ${tcmISError}<br/>
	  <c:forEach items="${tcmISErrors}" varStatus="status">
	  	${status.current}<br/>
	  </c:forEach>
	</div>

	<script type="text/javascript">
	<!--
	/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
	<c:choose>
	   <c:when test="${empty tcmISErrors and empty tcmISError}">
	    showErrorMessage = false;
	   </c:when>
	   <c:otherwise>
	    showErrorMessage = true;
	   </c:otherwise>
	</c:choose>
	//-->
	</script>
	<!-- Error Messages Ends -->

	<!-- Result Frame Begins -->
	<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
		
		<!-- Transit Page Begins -->
		<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
		  <br><br><br><fmt:message key="label.pleasewait"/>
		  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
		</div>
		<!-- Transit Page Ends -->
		
		<div id="resultGridDiv">
		<!-- results start -->
		<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
		<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td>
			<div class="roundcont contentContainer">
			<div class="roundright">
  			<div class="roundtop">
    			<div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  			</div>
  			<div class="roundContent">
    		<div class="boxhead"> <%-- boxhead Begins --%>
		     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
		          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
		          Please keep the <span></span> on the same line this will avoid extra virtical space.
		      --%>
      			<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      				<c:if  test="${catAddHeaderViewBean.viewLevel == 'approver'}">
						<a href="#" onclick="submitApproveForm();"><fmt:message key="label.approve"/></a> | 
						<a href="#" onclick="submitRejectForm();"><fmt:message key="label.reject"/></a> | 
						<a href="#" onclick="saveData();"><fmt:message key="label.save"/></a> | 
						<a href="#" onclick="submitRevert();"><fmt:message key="label.revert"/></a>
					</c:if>
						<%-- <a href="#" onclick="showApprovalDetail();"><fmt:message key="label.approvaldetail"/></a> --%>
					<br>
				</div> <%-- mainUpdateLinks Ends --%>	
			</div> <%-- boxhead Ends --%>		
					<fieldset><legend class="optionTitle"><fmt:message key="label.catalogspecrequest"/></legend>
						<table  width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
			                    <td class="optionTitleBoldRight">
			                        <fmt:message key="label.requestor"/>:&nbsp;
			                    </td>
			                    <td class="optionTitleLeft" title="${catAddHeaderViewBean.requestorPhone} - ${catAddHeaderViewBean.requestorEmail}" nowrap>
			                        ${catAddHeaderViewBean.requestorName}
			                    </td>
			                    <td class="optionTitleBoldRight">
			                        <fmt:message key="label.request"/>:&nbsp;
			                    </td>
			                    <td class="optionTitleLeft nowrap">
			                        ${catAddHeaderViewBean.requestId} (${catAddHeaderViewBean.startingViewDesc})
			                    </td>
			                    <td class="optionTitleBoldRight">
			                        <fmt:message key="label.submitdate"/>:&nbsp;
			                    </td>
			                    <td class="optionTitleLeft" nowrap>
			                        <fmt:formatDate var="fmtSubmitDate" value="${catAddHeaderViewBean.submitDate}" pattern="${dateFormatPattern}"/>
			                        ${fmtSubmitDate}
			                    </td>
			                </tr>
			                <tr>
			                    <td class="optionTitleBoldRight">
			                        <fmt:message key="label.catalog"/>:&nbsp;
			                    </td>
			                    <td class="optionTitleLeft" nowrap>
			                        ${catAddHeaderViewBean.catalogId}
			                    </td>
			                    <td class="optionTitleBoldRight">
			                        <fmt:message key="label.partno"/>:&nbsp;
			                    </td>
			                    <td class="optionTitleLeft" nowrap>
			                        ${catAddHeaderViewBean.catPartNo}
			                    </td>
			                    <td class="optionTitleBoldRight">
			                        <fmt:message key="label.replacespartno"/>:&nbsp;
			                    </td>
			                    <td  class="optionTitleLeft" nowrap>
			                        ${catAddHeaderViewBean.oldCatPartNo}
			                    </td>
			                </tr>
		                    <tr >
		                        <td class="optionTitleBoldRight">
		                            <fmt:message key="label.partdesc"/>:&nbsp;
		                        </td>
		                        <td class="optionTitleLeft" colspan=3}" >
		                            ${catAddHeaderViewBean.partDescription}
		                        </td>
		                        <td colspn=2></td>
		                    </tr>
						</table>
					</fieldset>
				
			

			<%-- transition --%>
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

	 		<%-- message --%>
			<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
				<table width="100%" border="0" cellpadding="2" cellspacing="1">
					<tr>
						<td align="center" width="100%">
							<textarea cols="70" rows="5" class="inputBox" readonly="true" name="messageText" id="messageText"></textarea>
						</td>
					</tr>
					<tr>
						<td align="center" width="100%">
						<input name="closeMessageB"  id="closeMessageB"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
						</td>
					</tr>
				</table>
			</div>

	 		<div class="dataContent">
		 		<fieldset><legend><fmt:message key="label.lineitems"/></legend>
			 		<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
		 			<div id="specRequestDiv" style="width:100%;height:400px;">></div>
					<!-- Search results start -->
					<c:if test="${catAddSpecRequestLines != null}" >
						<script type="text/javascript">
						<!--
			                <c:set var="dataCount" value='${0}'/>
							/*Storing the data to be displayed in a JSON object array.*/
							var jsonSpecRequestData = {
						      rows:[
								  <c:forEach var="row" items="${catAddSpecRequestLines}" varStatus="status">
								  	<%-- Check row permission here --%>
									<c:set var="lineUpdatePermission" value='N'/>
									<c:set var="specUpdatePermission" value='N'/>
									
									<c:if  test="${catAddHeaderViewBean.viewLevel == 'approver'}">
										<c:if test="${row.specSource != 'catalog_add_spec' && row.dataSource != 'BASE'}">
											<c:set var="lineUpdatePermission" value='Y'/>
											<c:if test="${row.specSource == 'catalog_add_spec_qc' && row.specId != 'Std Mfg Cert'}">
												<c:set var="specUpdatePermission" value='Y'/>
											</c:if>
										</c:if>
									</c:if>

                                    <fmt:formatDate var="fmtSpecDate" value="${row.specDate}" pattern="${dateFormatPattern}"/>

                                    <c:if test="${status.index != 0 }">,</c:if>
									  { id:${status.index +1},
										  data:[
												'${lineUpdatePermission}',
												'${specUpdatePermission}',
												'${specUpdatePermission}',
												'${specUpdatePermission}',
												'${specUpdatePermission}',
												'${specUpdatePermission}',
												${row.lineItem},
												'<tcmis:jsReplace processSpaces="true" value="${row.specId}"/>',
												'${row.specLibrary}',
												'<tcmis:jsReplace value="${row.specName}"/>',
												'<tcmis:jsReplace value="${row.specTitle}"/>',
												'<tcmis:jsReplace value="${row.specVersion}"/>',
												'<tcmis:jsReplace value="${row.specAmendment}"/>',
                                                '${specUpdatePermission}',
                                                '${fmtSpecDate}',
                                                <c:choose><c:when test="${row.coc == 'Y'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
                                                <c:choose><c:when test="${row.coa == 'Y'}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,
                                                '${row.itar}',
                                                '<tcmis:jsReplace value="${row.specDetail}"/>',
                                                '${row.onLine}',
												'${row.content}',
                                                '${row.specSource}',
                                                '${row.dataSource}',
                                                '<tcmis:jsReplace processSpaces="true" value="${row.specId}"/>',
                                                false
                                     ]}
								   <c:set var="dataCount" value='${dataCount+1}'/>
			                      </c:forEach>
								]};

                              <%-- determining rowspan --%>
                              <c:set var="rowSpanCount" value='0' />
                                <%-- determining rowspan --%>
                                <c:forEach var="row" items="${catAddSpecRequestLines}" varStatus="status">
                                    <c:set var="currentKey" value='${row.lineItem}' />
                                    <c:set var="currentLvl2Key" value='${row.lineItem}${row.specId}${row.dataSource}' />
                                    <c:choose>
                                        <c:when test="${status.first}">
                                            <c:set var="rowSpanStart" value='0' />
                                            <c:set var="rowSpanCount" value='1' />
                                            <c:set var="prevSpanCount" value="${rowSpanCount}"/>
                                            rowSpanMap[0] = 1;
                                            rowSpanClassMap[0] = 1;

                                            rowSpanLvl2Map[0] = 1;
                                            <c:set var="rowSpan2Start" value='0' />
                                            <c:set var="rowSpan2Count" value='1' />
                                            <c:set var="prevSpan2Count" value="${rowSpan2Count}"/>
                                        </c:when>
                                        <c:when test="${currentKey == previousKey}">
                                            rowSpanMap[${rowSpanStart}]++;
                                            rowSpanMap[${status.index}] = 0;
                                            rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};

                                                <c:choose>
                                                    <c:when test="${currentLvl2Key eq previousLvl2Key}">
                                                        rowSpanLvl2Map[${status.index}] = 0;
                                                        rowSpanLvl2Map[${rowSpan2Start}]++;
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="rowSpan2Count" value='${rowSpan2Count + 1}' />
                                                        <c:set var="rowSpan2Start" value='${status.index}' />
                                                        rowSpanLvl2Map[${status.index}]=1;
                                                    </c:otherwise>
                                                </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="rowSpanCount" value='${rowSpanCount + 1}' />
                                            <c:set var="rowSpanStart" value='${status.index}' />
                                            rowSpanMap[${status.index}] = 1;
                                            rowSpanClassMap[${status.index}] = ${rowSpanCount % 2};
                            
                                            <c:set var="rowSpan2Count" value='${rowSpan2Count + 1}' />
                                            <c:set var="rowSpan2Start" value='${status.index}' />
                                            rowSpanLvl2Map[${status.index}]=1;
                                        </c:otherwise>
                                    </c:choose>
                                    <c:set var="previousLvl2Key" value='${currentLvl2Key}' />
                                    <c:set var="previousKey" value='${currentKey}' />
                                </c:forEach>


                        //-->
						</script>

						<!-- If the collection is empty say no data found -->
						<c:if test="${empty catAddSpecRequestLines}" >
							<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
							 <tr>
							  <td width="100%">
								<fmt:message key="main.nodatafound"/>
							  </td>
							 </tr>
							</table>
						</c:if>
	
					</c:if>
				</fieldset>
			<!-- Search results end -->
			</div>
   
 		 	</div>

			<div class="roundbottom">
    			<div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  			</div>
			</div>
			</div>
		</td>
		</tr>
		</table>
		<!-- results end -->
		</div>
	</div>
	<!-- Result Frame Ends -->

	<!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;">
		<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
		<input name="uAction" id="uAction" value="" type="hidden"/>
		<input name="startSearchTime" id="startSearchTime" type="hidden" value="">
		<input name="endSearchTime" id="endSearchTime" type="hidden" value="">
        <input name="requestId" id="requestId" type="hidden" value="${catAddHeaderViewBean.requestId}">
        <input name="companyId" id="companyId" type="hidden" value="${catAddHeaderViewBean.companyId}">
        <input name="specId" id="specId" type="hidden" value="">
        <input name="specName" id="specName" type="hidden" value="">
        <input name="specTitle" id="specTitle" type="hidden" value="">
        <input name="specVersion" id="specVersion" type="hidden" value="">
        <input name="specAmendment" id="specAmendment" type="hidden" value="">
        <input name="specLibrary" id="specLibrary" type="hidden" value="">
        <input name="coc" id="coc" type="hidden" value="">
        <input name="coa" id="coa" type="hidden" value="">
        <input name="oldSpecId" id="oldSpecId" type="hidden" value=""/>
        <input name="lineItem" id="lineItem" type="hidden" value=""/>
        <input name="minHeight" id="minHeight" type="hidden" value="100">
        <input name="itarControl" id="itarControl" type="hidden" value="${tcmis:isFeatureReleased(personnelBean,'ItarControl',catAddHeaderViewBean.engEvalFacilityId)}"/>
        <input name="viewLevel" id="viewLevel" type="hidden" value="${catAddHeaderViewBean.viewLevel}">
        <input type="hidden" name="blockBefore_specDate" id="blockBefore_specDate" value=""/>
        <input type="hidden" name="blockAfter_specDate" id="blockAfter_specDate" value=""/>
        <input type="hidden" name="blockBeforeExclude_specDate" id="blockBeforeExclude_specDate" value=""/>
        <input type="hidden" name="blockAfterExclude_specDate" id="blockAfterExclude_specDate" value=""/>
        <input type="hidden" name="inDefinite_specDate" id="inDefinite_specDate" value=""/>
        <input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer', '',catAddHeaderViewBean.companyId)}'/>
    </div>
	<!-- Hidden elements end -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>