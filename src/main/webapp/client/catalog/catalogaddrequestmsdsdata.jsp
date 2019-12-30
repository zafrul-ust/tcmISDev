var newMsdsViewer = false;
var showPerVolWeight = ${tcmis:isFeatureReleased(personnelBean,'ShowPerVolWeight', catAddHeaderViewBean.engEvalFacilityId)};

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>

<c:set var="allowToEditKitMsds" value="N"/>
<tcmis:featureReleased feature="EditKitMsds" scope="${catAddHeaderViewBean.engEvalFacilityId}">
    <c:set var="allowToEditKitMsds" value='Y'/>
</tcmis:featureReleased>

    <c:set var="dataCount" value='${0}'/>
	<c:set var="preLineItem" value=''/>
	var map = null;
	var lineMap = new Array();
	var lineIdMap = new Array();
	var lineMap3 = new Array();
	<c:set var="msdsCount" value='-1'/>
	<c:forEach var="tmpBean" items="${catAddHeaderViewBean.msdsDataColl}" varStatus="status">
		<c:set var="currentLineItem" value='1'/>
        <c:if test="${ currentLineItem != preLineItem }">
			lineMap[${status.index}] = ${catAddHeaderViewBean.msdsRowSpan[currentLineItem]};
			map = new Array();
			<c:set var="msdsCount" value='${msdsCount+1}'/>
		</c:if>
		<c:set var="preLineItem" value='${currentLineItem}'/>
		lineMap3[${status.index}] = ${msdsCount % 2} ;
		map[map.length] =  ${status.index} ;
		lineIdMap[${status.index}] = map;
	</c:forEach>

	var msdsJsonMainData = new Array();
	var msdsJsonMainData = {
		rows:[
			<c:forEach var="catAddMsdsViewBean" items="${catAddHeaderViewBean.msdsDataColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='N'/>
			<c:if test="${catAddHeaderViewBean.viewLevel != 'view' && catAddHeaderViewBean.startingView == 6}">
				<c:set var="currentPermission" value='Y'/>
			</c:if>

            <c:set var="tmpMixturePermission" value='N'/>
			<c:if test="${catAddHeaderViewBean.viewLevel == 'requestor' && catAddHeaderViewBean.startingView == 6 && catAddHeaderViewBean.allowMixture == 'Y'}">
				<c:set var="tmpMixturePermission" value='Y'/>
			</c:if>
            <c:if test="${catAddHeaderViewBean.viewLevel == 'approver' && catAddHeaderViewBean.startingView == 6 && catAddHeaderViewBean.allowMixture == 'Y' && catAddHeaderViewBean.allowEditMixtureData == 'Y'}">
				<c:set var="tmpMixturePermission" value='Y'/>
			</c:if>

            <c:set var="tmpCompMsdsPermission" value='N'/>
            <c:set var="tmpReplaceMsdsPermission" value='N'/>
            <c:if test="${currentPermission == 'Y' && status.current.startingView == 6}">
				<c:set var="tmpCompMsdsPermission" value='Y'/>
                <c:set var="tmpReplaceMsdsPermission" value='Y'/>
            </c:if>

            <c:set var="tmpVocetPropertiesPermission" value='N'/>
			<c:if test="${hasHmrbTab == 'Y' && catAddHeaderViewBean.allowEditVocetProperties == 'Y'}">
				<c:set var="tmpVocetPropertiesPermission" value='Y'/>
			</c:if>

            <c:set var="tmpNanomaterialPermission" value='N'/>
			<c:if test="${hasHmrbTab == 'Y' && catAddHeaderViewBean.allowEditNanomaterial == 'Y'}">
				<c:set var="tmpNanomaterialPermission" value='Y'/>
			</c:if>
            <c:set var="tmpContainsNanomaterial" value="false"/>
            <c:if test="${status.current.nanomaterial == 'Y'}">
                <c:set var="tmpContainsNanomaterial" value="true"/>
            </c:if>
            <c:set var="tmpRadioactivePermission" value='N'/>
			<c:if test="${hasHmrbTab == 'Y' && catAddHeaderViewBean.allowEditRadioactive == 'Y'}">
				<c:set var="tmpRadioactivePermission" value='Y'/>
			</c:if>
            <c:set var="tmpRadioactive" value="false"/>
            <c:if test="${status.current.radioactive == 'Y'}">
                <c:set var="tmpRadioactive" value="true"/>
            </c:if>

            <c:if test="${tmpMixturePermission == 'N'}">
				<c:set var="allowToEditKitMsds" value='N'/>
			</c:if>

            <c:set var="customerMfgIdPermission" value="N"/>
            <c:if test="${catAddHeaderViewBean.customerMfgIdRequired == 'Y' && currentPermission == 'Y'}">
                <c:set var="customerMfgIdPermission" value='Y'/>
            </c:if>


            { id:${status.index +1},
				data:['${currentPermission}',
                        '${status.current.displayStatus}',
						'${allowToEditKitMsds}',
						'${status.current.customerMixtureNumber}',
                        '${tmpMixturePermission}',
                        '<tcmis:jsReplace value="${status.current.mixtureDesc}"/>',
                        '${tmpMixturePermission}',
                        '${tmpMixturePermission}',
                        '${status.current.partSize}',
                        '${status.current.sizeUnit}',
                        '<fmt:formatNumber value="${status.current.percentByWeight}" pattern="${totalcurrencyformat}"/>',
                        '${status.current.kitApprovalCode}',
                        '${tmpCompMsdsPermission}',
                        '${status.current.customerMsdsNumber}',
                        '${tmpReplaceMsdsPermission}',
                        '${status.current.replacesMsds}',
                        '${status.current.replacesMsds}',
                         '${status.current.msdsApprovalCode}',
                        '<tcmis:jsReplace value="${status.current.materialDesc}"/>',
				        '<tcmis:jsReplace value="${status.current.manufacturer}"/>',
                        '${customerMfgIdPermission}',
                        '<tcmis:jsReplace value="${status.current.customerMfgId}"/>',
                        '<tcmis:jsReplace value="${status.current.customerMfgIdDisplay}"/>',
                        '<tcmis:jsReplace value="${status.current.mfgTradeName}"/>',
                        <%--'${status.current.aerosolContainer}',--%>
                        '${tmpNanomaterialPermission}',
                        ${tmpContainsNanomaterial},
                        '${tmpRadioactivePermission}',
                        ${tmpRadioactive},
                        '${tmpVocetPropertiesPermission}',
                        '${status.current.vocetStatusId}',
                        '${tmpVocetPropertiesPermission}',
                        '${status.current.vocetCoatCategoryId}', 
                        '${status.current.materialId}',
                        '${status.current.lineItem}',
						'${status.current.msdsOnLine}',
                        'new',
                        '${status.current.partId}',
						'${status.current.startingView}',
						'${status.current.approvedCustMixtureNumber}'
                ]}
			 <c:set var="dataCount" value='${dataCount+1}'/>
			 </c:forEach>
			]};

<c:set var="showMixtureColumn" value='N'/>
<c:if test="${catAddHeaderViewBean.allowMixture == 'Y'}">
	<c:set var="showMixtureColumn" value='Y'/>
</c:if>

<c:set var="showHmrbColumn" value='N'/>
<c:if test="${hasHmrbTab == 'Y'}">
	<c:set var="showHmrbColumn" value='Y'/>
</c:if>

<c:set var="showReplacesMsdsColumn" value='N'/>
<c:if test="${showReplacesMsds == 'Y' && showMixtureColumn == 'N'}">
	<c:set var="showReplacesMsdsColumn" value='Y'/>
</c:if>

<c:set var="showVocetColumn" value='N'/>
<c:if test="${hasHmrbTab == 'Y' && catAddHeaderViewBean.allowEditVocetProperties == 'Y'}">
	<c:set var="showVocetColumn" value='Y'/>
</c:if>

var msdsConfig = [
{
	columnId:"permission"
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width: 10,
  submit:true
},
{ columnId:"customerMixtureNumberPermission"
},
{ columnId:"customerMixtureNumber",
  <c:if test="${showMixtureColumn == 'Y'}">
  	columnName:'<fmt:message key="label.kitmsds"/>',
  </c:if>
  type: 'hed',
  width: 6,
  permission:true,
  submit:true
},
{ columnId:"mixtureDescPermission"
},
{ columnId:"mixtureDesc",
  <c:if test="${showMixtureColumn == 'Y'}">
  	columnName:'<fmt:message key="report.label.kitDescription"/>',
  </c:if>
  type: 'hed',
  width: 15,
  size:28,
  tooltip:"Y",  
  permission:true,
  submit:true
},
{ columnId:"partSizePermission"
},
{ columnId:"sizeUnitPermission"
},
{ columnId:"partSize",
  <c:if test="${showMixtureColumn == 'Y'}">
    columnName:'<fmt:message key="label.mixratio"/>',
    attachHeader:'<fmt:message key="label.amount"/>*',
  </c:if>
  type: 'hed',
  width: 4,
  permission:true,
  submit:true
},
{ columnId:"sizeUnit",
  <c:if test="${showMixtureColumn == 'Y'}">
    columnName:'#cspan',
    attachHeader:'<fmt:message key="label.unit"/>*',
  </c:if>
  type: 'hcoro',
  width: 7,
  permission:true, 
  submit:true
},
{ columnId:"percentByWeight",
  width: 6,
  submit:true
},
{ columnId:"kitApprovalCode"
  <c:if test="${showMixtureColumn == 'Y'}">
    ,columnName:'<fmt:message key="label.kitapprovalcodes"/>'
  </c:if>
},
{ columnId:"customerMsdsNumberPermission"
},
{ columnId:"customerMsdsNumber",
  columnName:'<fmt:message key="label.msds"/>',
  type: 'hed',
  permission:true,
  submit:true  
},
{ columnId:"replacesMsdsPermission"
},
{ columnId:"replacesMsds",
  <c:if test="${showReplacesMsdsColumn == 'Y'}">
    columnName:'<fmt:message key="label.replacesmsds"/>',
  </c:if>
  type: 'hed',
  permission:true,  
  submit:true
},
{ columnId:"replacesMsdsValidator",
  type: 'hed'
},
{ columnId:"msdsApprovalCode",
  columnName:'<fmt:message key="label.msdsapprovalcode"/>'
},
{ columnId:"materialDesc",
  columnName:'<fmt:message key="label.materialdesc"/>',
  width: 20,
  tooltip:"Y",
  submit:true
},
{ columnId:"manufacturer",
  columnName:'<fmt:message key="label.manufacturer"/>',
  width: 15,
  tooltip:"Y",
  submit:true
},
{ columnId:"customerMfgIdPermission"
},
{ columnId:"customerMfgId",
<c:if test="${catAddHeaderViewBean.customerMfgIdRequired == 'Y'}">
    columnName:'<fmt:message key="label.customermfgid"/>',
    attachHeader:'<fmt:message key="label.new"/>',
    type: 'hed',
</c:if>
permission:true,
submit:true
},
{ columnId:"customerMfgIdDisplay",
<c:if test="${catAddHeaderViewBean.customerMfgIdRequired == 'Y'}">
    columnName:'#cspan',
    attachHeader:'<fmt:message key="label.mapped"/>',
</c:if>
tooltip:"Y"
},
{ columnId:"mfgTradeName",
  columnName:'<fmt:message key="label.tradename"/>',
  width: 15,
  tooltip:"Y",  
  submit:true
},
<%--
{ columnId:"aerosolContainer",
  <c:if test="${showHmrbColumn == 'Y'}">
    columnName:'<fmt:message key="label.aerosol"/>',
  </c:if>
  width: 5
},
--%>
{ columnId:"nanomaterialPermission"
},
{ columnId:"nanomaterial",
  <c:if test="${showHmrbColumn == 'Y'}">
    columnName:'<fmt:message key="label.containsnanomaterial"/>',
  </c:if>
  type:'hchstatus',
  align:'center',
  permission:true,
  width: 5
},
{ columnId:"radioactivePermission"
},
{ columnId:"radioactive",
  <c:if test="${showHmrbColumn == 'Y'}">
    columnName:'<fmt:message key="label.radioactive"/>',
  </c:if>
  type:'hchstatus',
  align:'center',
  permission:true,
  width: 5
},
{ columnId:"vocetStatusIdPermission"
},
{ columnId:"vocetStatusId",
  <c:if test="${showVocetColumn == 'Y'}">
  	columnName:'<fmt:message key="label.vocet"/>',
    attachHeader:'<fmt:message key="label.status"/>',
    type: 'hcoro',
  </c:if>
  width: 6,
  align:'center',
  permission:true,
  submit:true
},
{ columnId:"vocetCoatCategoryIdPermission"
},
{ columnId:"vocetCoatCategoryId",
  <c:if test="${showVocetColumn == 'Y'}">
  	columnName:'#cspan',
    attachHeader:'<fmt:message key="label.category"/>',
    type: 'hcoro',
  </c:if>
  align:'center',
  permission:true,
  submit:true
},
{ columnId:"materialId",
  columnName:'',
  submit:true
},
{ columnId:"lineItem",
  columnName:'',
  submit:true
},
{ columnId:"msdsOnLine",
  columnName:''
},
{ columnId:"dataSource",
  columnName:'',
  submit:true
},
{ columnId:"partId",
  columnName:'',
  submit:true
},
{ columnId:"startingView",
  submit:true
},
{ columnId:"itemId",
  submit:true
},
{ columnId:"approvedCustMixtureNumber"
}
];

<c:set var="sizeUnitColl" value='${catAddHeaderViewBean.shippingWeightUnitColl}'/>
<bean:size id="sizeUnitSize" name="sizeUnitColl"/>
var sizeUnit = new Array(
<c:if test="${sizeUnitSize > 1}">
{
	text:'<fmt:message key="label.select"/>',
	value:''
},
</c:if>
<c:choose>
  <c:when test="${tcmis:isFeatureReleased(personnelBean,'ShowPerVolWeight', catAddHeaderViewBean.engEvalFacilityId)}">
   {text:'<fmt:message key="report.label.percentByVolume"/>',
	 value:'%(v/v)'
   }
   ,
   {text:'<fmt:message key="report.label.percentByWeight"/>',
	 value:'%(w/w)'
   }

  </c:when>
  <c:otherwise>
	<c:forEach var="sizeUnitBean" items="${catAddHeaderViewBean.shippingWeightUnitColl}" varStatus="status2">
	  	  <c:if test="${status2.index > 0}">,</c:if>
		   {text:'<tcmis:jsReplace value="${sizeUnitBean.sizeUnit}"/>',
			 value:'<tcmis:jsReplace value="${sizeUnitBean.sizeUnit}"/>'
		   }
	</c:forEach>
</c:otherwise>
</c:choose>
);

<c:set var="vocetStatusColl" value='${catAddHeaderViewBean.vocetStatusColl}'/>
<bean:size id="vocetStatusSize" name="vocetStatusColl"/>
var vocetStatusId = new Array(
<c:if test="${vocetStatusSize > 1}">
{
	text:'',
	value:''
},
</c:if>
<c:forEach var="vocetStatusBean" items="${catAddHeaderViewBean.vocetStatusColl}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {text:'<tcmis:jsReplace value="${vocetStatusBean.vocetStatusDesc}"/>',
	 value:'<tcmis:jsReplace value="${vocetStatusBean.vocetStatusId}"/>'
	}
</c:forEach>
);

<c:set var="vocetCoatCategoryColl" value='${catAddHeaderViewBean.vocetCoatCategoryColl}'/>
<bean:size id="vocetCoatCategorySize" name="vocetCoatCategoryColl"/>
var vocetCoatCategoryId = new Array(
<c:if test="${vocetCoatCategorySize > 1}">
{
	text:'',
	value:''
},
</c:if>
<c:forEach var="vocetCoatCategoryBean" items="${catAddHeaderViewBean.vocetCoatCategoryColl}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {text:'<tcmis:jsReplace value="${vocetCoatCategoryBean.vocetCoatCategoryDesc}"/>',
	 value:'<tcmis:jsReplace value="${vocetCoatCategoryBean.vocetCoatCategoryId}"/>'
	}
</c:forEach>
);