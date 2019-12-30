var newMsdsViewer = false;
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>


    <c:set var="dataCount" value='${0}'/>
	<c:set var="prePartNumber" value=''/>
	<c:set var="preLineItem" value=''/>
	var map = null;
	var lineMap = new Array();
	var lineIdMap = new Array();
	var lineMap3 = new Array();
    <c:set var="itemCount" value='-1'/>

	<c:forEach var="tmpBean" items="${catAddHeaderViewBean.qplDataColl}" varStatus="status">
		<c:set var="currentLineItem" value='${status.current.displayLineItem}'/>
		<c:if test="${ currentLineItem != preLineItem }">
			lineMap[${status.index}] = ${catAddHeaderViewBean.qplRowSpan[currentLineItem]};
			map = new Array();
			<c:set var="itemCount" value='${itemCount+1}'/>
		</c:if>
		<c:set var="preLineItem" value='${status.current.displayLineItem}'/>
		lineMap3[${status.index}] = ${itemCount % 2} ;
		map[map.length] =  ${status.index};
		lineIdMap[${status.index}] = map;
	</c:forEach>
	
	<c:set var="atLeastOneMixture" value='false'/>
	<c:set var="prevLinePart" value="${catAddHeaderViewBean.qplDataColl[0].lineItem-catAddHeaderViewBean.qplDataColl[0].partId}"/>
	
	<c:set var="editKitMsdsFeatureRelease" value="${tcmis:isFeatureReleased(personnelBean,'EditKitMsds', catAddHeaderViewBean.engEvalFacilityId)}"/>

	var qplJsonMainData = new Array();
	var qplJsonMainData = {
		rows:[
			<c:forEach var="catPartQplViewBean" items="${catAddHeaderViewBean.qplDataColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='N'/>
			<c:if test="${status.current.dataSource == 'new' && status.current.startingView != 3 && catAddHeaderViewBean.viewLevel != 'view'}">
				<c:set var="currentPermission" value='Y'/>
			</c:if>
			
			<c:set var="valViewLevel" value='${catAddHeaderViewBean.viewLevel}'/>
			<c:if test="${empty valViewLevel}">
				<c:set var="valViewLevel" value='${param.viewLevel}'/>
			</c:if>
			
			<c:set var="editMixRatio" value="N"/>
			<c:if test="${valViewLevel == 'requestor' && status.current.itemIsKit != 'N' && currentPermission == 'Y' && empty status.current.approvedCustMixtureNumber}">
			    <c:set var="editMixRatio" value='Y'/>
			</c:if>
			
			<c:set var="valAllowEditMixtureData" value='${catAddHeaderViewBean.allowEditMixtureData}'/>
			<c:if test="${empty valAllowEditMixtureData}">
				<c:set var="valAllowEditMixtureData" value='${param.allowEditMixtureData}'/>
			</c:if>

            <c:if test="${(param.viewLevel == 'approver' || catAddHeaderViewBean.viewLevel == 'approver') && status.current.itemIsKit != 'N' && currentPermission == 'Y' && empty status.current.approvedCustMixtureNumber && valAllowEditMixtureData == 'Y'}">
			    <c:set var="editMixRatio" value='Y'/>
			</c:if>

            <c:set var="allowToEditKitMsds" value="N"/>
            <c:if test="${status.current.itemIsKit != 'N' && currentPermission == 'Y' && editKitMsdsFeatureRelease && empty status.current.approvedCustMixtureNumber}">
            	<c:set var="allowToEditKitMsds" value="Y"/>
            </c:if>

            <c:set var="tmpShelfLifePermission" value='N'/>
			<c:set var="tmpShelfLifeDays" value='${status.current.shelfLifeDays}'/>
			<c:set var="tmpShelfLifeBasis" value='${status.current.shelfLifeBasis}'/>
			<c:if test="${tmpShelfLifeDays == '-1'}">
				<c:set var="tmpShelfLifeDays" value=''/>
				<c:set var="tmpShelfLifeBasis" value='Indefinite'/>
			</c:if>
			<c:if test="${tmpShelfLifeBasis == 'E'}">
				<c:set var="tmpShelfLifeDays" value=''/>
			</c:if>
			<c:if test="${tmpShelfLifeBasis == 'X'}">
				<c:set var="tmpShelfLifeDays" value=''/>
			</c:if>
			<c:if test="${tmpShelfLifeBasis == 'M' || tmpShelfLifeBasis == 'R' || tmpShelfLifeBasis == 'S' || tmpShelfLifeBasis == 'P' || tmpShelfLifeBasis == 'T'}">
                <c:if test="${currentPermission == 'Y'}">
                    <c:set var="tmpShelfLifePermission" value='Y'/>
                </c:if>
			</c:if>

			<c:set var="tmpCustomerTempId" value='${status.current.customerTemperatureId}'/>
			<c:if test="${currentPermission == 'N'}">
				<c:if test="${empty tmpShelfLifeBasis}">
					<c:set var="tmpShelfLifeBasis" value=' '/>
				</c:if>
				<c:if test="${empty tmpCustomerTempId}">
					<c:set var="tmpCustomerTempId" value=' '/>
				</c:if>
			</c:if>

            <c:set var="tmpHetUsagePermission" value='${catAddHeaderViewBean.allowEditHetUsageRecording}'/>

            <c:set var="tmpVocetPropertiesPermission" value='N'/>
			<c:if test="${hasHmrbTab == 'Y' && status.current.dataSource == 'new' && catAddHeaderViewBean.allowEditVocetProperties == 'Y'}">
				<c:set var="tmpVocetPropertiesPermission" value='Y'/>
			</c:if>

            <c:set var="tmpNanomaterialPermission" value='N'/>
			<c:if test="${hasHmrbTab == 'Y' && status.current.dataSource == 'new' && catAddHeaderViewBean.allowEditNanomaterial == 'Y'}">
				<c:set var="tmpNanomaterialPermission" value='Y'/>
			</c:if>
            <c:set var="tmpContainsNanomaterial" value="false"/>
            <c:if test="${status.current.nanomaterial == 'Y'}">
                <c:set var="tmpContainsNanomaterial" value="true"/>
            </c:if>
            <c:set var="tmpRadioactivePermission" value='N'/>
			<c:if test="${hasHmrbTab == 'Y' && status.current.dataSource == 'new' && catAddHeaderViewBean.allowEditRadioactive == 'Y'}">
				<c:set var="tmpRadioactivePermission" value='Y'/>
			</c:if>
            <c:set var="tmpRadioactive" value="false"/>
            <c:if test="${status.current.radioactive == 'Y'}">
                <c:set var="tmpRadioactive" value="true"/>
            </c:if>
            
            <c:set var="debugLineItem" value="${status.current.lineItem}"/>
            <c:set var="debugPrevLineItem" value="${prevLineItem}"/>
            <c:set var="debugPartId" value="${status.current.partId}"/>
            <c:set var="debugPrevLinePart" value="${prevLinePart}"/>
            
            <c:if test="${(status.current.lineItem == prevLineItem && status.current.partId != prevLinePart) || !empty status.current.approvedCustMixtureNumber}">
            		<c:set var="atLeastOneMixture" value='true'/>
            </c:if>
			<c:set var="prevLineItem" value="${status.current.lineItem}"/>
			<c:set var="prevLinePart" value="${status.current.partId}"/>
			
			
			<c:set var="customerMsdsNumberPermission" value="N"/>
            <c:if test="${empty status.current.approvedCustMsdsNumber && currentPermission == 'Y'}">
            		<c:set var="customerMsdsNumberPermission" value='Y'/>
            </c:if>

            <c:set var="customerMfgIdPermission" value="N"/>
            <c:if test="${catAddHeaderViewBean.customerMfgIdRequired == 'Y' && currentPermission == 'Y'}">
                <c:set var="customerMfgIdPermission" value='Y'/>
            </c:if>

            <c:set var="tempSizeUnit" value="${status.current.calcMixRatioSizeUnit}"/>
            <c:if test="${tempSizeUnit == '%(v/v)'}">
                 <c:set var="tempSizeUnit"><fmt:message key="report.label.percentByVolume"/></c:set>
            </c:if>
            <c:if test="${tempSizeUnit == '%(w/w)'}">
                 <c:set var="tempSizeUnit"><fmt:message key="report.label.percentByWeight"/></c:set>
            </c:if>

            <c:set var="materialNetAmount" value=""/>
            <c:if test="${!empty status.current.netwt && !empty status.current.netwtUnit }">
                 <c:set var="materialNetAmount">${status.current.netwt} ${status.current.netwtUnit}</c:set>
            </c:if>

            { id:${status.index +1},
				data:['${currentPermission}','${editMixRatio}','${editMixRatio}','${tmpMixturePermission}','',
					    '${status.current.displayStatus}',
				        '${status.current.itemId}',
						'${allowToEditKitMsds}',
						'${status.current.customerMixtureNumber}',
                        '${editMixRatio}',
						'<tcmis:jsReplace value="${status.current.mixtureDesc}"/>',
						'${status.current.mixRatioAmount}',
						'${status.current.mixRatioSizeUnit}',
						'${status.current.calcMixRatioAmount} ${tempSizeUnit}',				
                        '${status.current.kitApprovalCode}',
                        '${tmpHetUsagePermission}',
                        '${status.current.hetUsageRecording}',
                        '<tcmis:jsReplace value="${status.current.materialDesc}"/>',
						'<tcmis:jsReplace value="${status.current.packaging}"/>',
                        '${materialNetAmount}',
                        '<tcmis:jsReplace value="${status.current.grade}"/>',
				        '<tcmis:jsReplace value="${status.current.mfgDesc}"/>',
                        '${customerMfgIdPermission}',
                        '<tcmis:jsReplace value="${status.current.customerMfgId}"/>',
                        '<tcmis:jsReplace value="${status.current.customerMfgIdDisplay}"/>',
				        '${customerMsdsNumberPermission}',
						'${status.current.customerMsdsNumber}',
                        '${status.current.replacesMsds}',
                        '${status.current.replacesMsds}',
                        '${status.current.msdsApprovalCode}',
                        '${tmpNanomaterialPermission}',
                        ${tmpContainsNanomaterial},
                        '${tmpRadioactivePermission}',
                        ${tmpRadioactive},
                        '${tmpVocetPropertiesPermission}',
                        '${status.current.vocetStatusId}',
                        '${tmpVocetPropertiesPermission}',
                        '${status.current.vocetCoatCategoryId}', 
                        '${status.current.materialId}','${status.current.lineItem}',
						'${status.current.msdsOnLine}','${status.current.dataSource}','${status.current.partId}',
						'${status.current.startingView}',
				        '${tmpShelfLifePermission}',
						'${tmpShelfLifeDays}',
						'${tmpShelfLifeBasis}',
						'${tmpCustomerTempId}',
				        '${status.current.roomTempOutTime}',
                        '${status.current.roomTempOutTimeUnit}',
                        '${status.current.labelColor}',
                        '${status.current.approvalLetterContent}',
                         '${status.current.itemIsKit}',
                         '${status.current.startingView}',
                        '${status.current.approvedCustMixtureNumber}'
                ]}
			 <c:set var="dataCount" value='${dataCount+1}'/>
			 </c:forEach>
			]};
			
<c:set var="showTimeTempColumn" value='N'/>
<c:if test="${catAddHeaderViewBean.timeTempSensitive == 'Y'}">
	<c:set var="showTimeTempColumn" value='Y'/>
</c:if>

<c:set var="showRoomTempOutTimeColumn" value='N'/>
<c:if test="${catAddHeaderViewBean.allowRoomTempOutTime == 'Y' && catAddHeaderViewBean.roomTempOutTime == 'Y'}">
	<c:set var="showRoomTempOutTimeColumn" value='Y'/>
</c:if>

<c:set var="showMixtureColumn" value='N'/>
<c:if test="${catAddHeaderViewBean.allowMixture == 'Y' && atLeastOneMixture == true}">
	<c:set var="showMixtureColumn" value='Y'/>
</c:if>
showMixtureColumnAudit = '${showMixtureColumn}';

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

<c:set var="ShowHETColumns" value="${tcmis:isFeatureReleased(personnelBean,'ShowHETColumns', catAddHeaderViewBean.engEvalFacilityId)}"/>
var ShowHETColumns = ${ShowHETColumns};

<c:set var="showHetRecording" value='N'/>
<c:if test="${ShowHETColumns &&
             (catAddHeaderViewBean.requestStatus == '5' ||
              catAddHeaderViewBean.requestStatus == '7' ||
              catAddHeaderViewBean.requestStatus == '8' ||
              catAddHeaderViewBean.requestStatus == '9' ||
              catAddHeaderViewBean.requestStatus == '11' ||
              catAddHeaderViewBean.requestStatus == '12')}">
	<c:set var="showHetRecording" value='Y'/>
</c:if>

<c:set var="showMixRatioUnitAsVolumeWeight" value="${tcmis:isFeatureReleased(personnelBean,'ShowPerVolWeight', catAddHeaderViewBean.engEvalFacilityId)}"/>
var showMixRatioUnitAsVolumeWeight = ${showMixRatioUnitAsVolumeWeight};

<c:set var="sizeUnitColl" value='${catAddHeaderViewBean.shippingWeightUnitColl}'/>
<bean:size id="sizeUnitSize" name="sizeUnitColl"/>
var mixRatioSizeUnit = new Array(
{
	text:'<fmt:message key="label.pleaseselect"/>',
	value:''
}
<c:choose>
  <c:when test="${showMixRatioUnitAsVolumeWeight}">
   ,
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
	  	  ,
		   {text:'<tcmis:jsReplace value="${sizeUnitBean.sizeUnit}"/>',
			 value:'<tcmis:jsReplace value="${sizeUnitBean.sizeUnit}"/>'
		   }
	</c:forEach>
</c:otherwise>
</c:choose>
);

<c:set var="showMaterialNetWt" value="${tcmis:isFeatureReleased(personnelBean,'ShowMaterialNetWt','ALL')}"/>

var qplConfig = [
{
	columnId:"permission"
},
{ columnId:"mixRatioAmountPermission"
},
{ columnId:"mixRatioSizeUnitPermission"
},
{ columnId:"mixRatioCalculatedPermission"
},
{ columnId:"itemPerPart",
  columnName:''
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width: 15,
  submit:true
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.item"/>',
  width: 6,
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
  	columnName:'<fmt:message key="label.kitdesc"/>',
  </c:if>
  type: 'hed',
  width: 15,
  size:28,
  tooltip:"Y",
  permission:true,
  submit:true
},
{ columnId:"mixRatioAmount",
  <c:if test="${showMixtureColumn == 'Y'}">
  	columnName:'<fmt:message key="label.mixratio"/>',
    attachHeader:'<fmt:message key="label.amount"/>',
  </c:if>
  type: 'hed',
  width: 4,
  size:10,
  permission:true,
  submit:true
},
{ columnId:"mixRatioSizeUnit",
  <c:if test="${showMixtureColumn == 'Y'}">
  	columnName:'#cspan',
    attachHeader:'<fmt:message key="label.unit"/>',
  </c:if>
  type: 'hcoro',
  width: 7,
  tooltip:"Y",
  permission:true,
  submit:true
},
{ columnId:"mixRatioCalculated",
  <c:if test="${showMixtureColumn == 'Y'}">
    columnName:'#cspan',
    attachHeader:'<fmt:message key="label.calculated"/>',
  </c:if>
  width: 5,
  tooltip:"Y"
},
{ columnId:"kitApprovalCode"
  <c:if test="${showMixtureColumn == 'Y'}">
    ,columnName:'<fmt:message key="label.kitapprovalcodes"/>'
  </c:if>
},
{ columnId:"hetUsageRecordingPermission"
},
{ columnId:"hetUsageRecording",
  <c:if test="${showHetRecording == 'Y'}">
    columnName:'<fmt:message key="label.hetusagerecording"/>',
  </c:if>
  type: 'hcoro',
  permission:true
},
{ columnId:"materialDesc",
  columnName:'<fmt:message key="label.desc"/>',
  width: 20,
  submit:true
},
{ columnId:"packaging",
  columnName:'<fmt:message key="label.packaging"/>',
  width: 20,
  submit:true
},
{ columnId:"netAmount",
  <c:if test="${showMaterialNetWt}">
    columnName:'<fmt:message key="label.netamount"/>',
  </c:if>
  width: 7,
  submit:true
},
{ columnId:"grade",
  columnName:'<fmt:message key="label.grade"/>',
  width: 10,
  submit:true
},
{ columnId:"mfgDesc",
  columnName:'<fmt:message key="label.manufacturer"/>',
  width: 15,
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
{ columnId:"customerMsdsNumberPermission"
},
{ columnId:"customerMsdsNumber",
  columnName:'<fmt:message key="label.msds"/>',
  permission:true,
  type: 'hed'	
},
{ columnId:"replacesMsds",
  <c:if test="${showReplacesMsdsColumn == 'Y'}">
    columnName:'<fmt:message key="label.replacesmsds"/>',
  </c:if>
  type: 'hed'
},
{ columnId:"replacesMsdsValidator",
  type: 'hed'
},
<%--
{ columnId:"aerosolContainer",
  <c:if test="${showHmrbColumn == 'Y'}">
    columnName:'<fmt:message key="label.aerosol"/>',
  </c:if>
  width: 5
},
--%>
{ columnId:"msdsApprovalCode"
  <c:if test="${hasHmrbTab == 'Y'}">
    ,columnName:'<fmt:message key="label.msdsapprovalcode"/>'
  </c:if>
},
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
  submit:true
},
{ columnId:"partId",
  columnName:'',
  submit:true
},
{ columnId:"startingView",
  columnName:'',	
  submit:true
},
{
	columnId:"shelfLifeDaysPermission"
},
{ columnId:"shelfLifeDays",
  <c:if test="${showTimeTempColumn == 'Y'}">
  	columnName:'<fmt:message key="label.shelflife"/>*',
  </c:if>
  type: 'hed',
  width: 6,
  permission:true,	
  submit:true
},
{ columnId:"shelfLifeBasis",
  <c:if test="${showTimeTempColumn == 'Y'}">
  	columnName:'<fmt:message key="label.shelflifeunit"/>*',
  </c:if>
  type: 'hcoro',
  width: 12,
  <c:if test="${callParent eq 'Y'}">
	  	onChange:parent.shelfLifeBasisChanged,
  </c:if>
  <c:if test="${callParent ne 'Y'}">
		 onChange:shelfLifeBasisChanged,
  </c:if>
  submit:true
},
{ columnId:"customerTemperatureId",
  <c:if test="${showTimeTempColumn == 'Y'}">
  	columnName:'<fmt:message key="label.storagetemp"/>*',
  </c:if>
  type: 'hcoro',
  submit:true
},
{ columnId:"roomTempOutTime",
  <c:if test="${showRoomTempOutTimeColumn == 'Y'}">
  	columnName:'<fmt:message key="label.roomtempouttime"/>',
    attachHeader:'<fmt:message key="label.value"/>*',
  </c:if>
  type: 'hed',
  width: 6,
  align:'center',
  submit:true
},
{ columnId:"roomTempOutTimeUnit",
  <c:if test="${showRoomTempOutTimeColumn == 'Y'}">
  	columnName:'#cspan',
    attachHeader:'<fmt:message key="label.unit"/>*',
    type: 'hcoro',
  </c:if>
  align:'center', 
  submit:true
},
{ columnId:"labelColor",
  <c:if test="${catAddHeaderViewBean.labelColorRequired == 'Y'}">
  	columnName:'<fmt:message key="label.labelcolor"/>*',
    type: 'hcoro',
  </c:if>
  submit:true
},
{ columnId:"approvalLetterContent",
  columnName:''
},
{ columnId:"isKit"
},
{
   columnId:"startingView"
},
{
	columnId:"approvedCustMixtureNumber"
}
];

<c:set var="storageTempColl" value='${catAddHeaderViewBean.catalogStorageTempColl}'/>
<bean:size id="storageTempSize" name="storageTempColl"/>
var customerTemperatureId = new Array(
<c:if test="${storageTempSize > 1}">
{
	text:'<fmt:message key="label.select"/>',
	value:''
},
</c:if>
<c:forEach var="catalogStorageTempBean" items="${catAddHeaderViewBean.catalogStorageTempColl}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {text:'<tcmis:jsReplace value="${catalogStorageTempBean.displayTemp}"/>',
	 value:'<tcmis:jsReplace value="${catalogStorageTempBean.customerTemperatureId}"/>'
	}
</c:forEach>
);

<c:set var="mfgRecommendedLabel"><fmt:message key="label.mfgrecommended"/></c:set>
<c:set var="indefiniteLabel"><fmt:message key="label.indefinite"/></c:set>
<c:set var="daysFromReceiptLabel"><fmt:message key="label.daysfromreceipt"/></c:set>
<c:set var="daysFromMfgLabel"><fmt:message key="label.daysfrommfg"/></c:set>
<c:set var="daysFromShipmentLabel"><fmt:message key="label.daysfromshipment"/></c:set>
<c:set var="daysFromRepackageLabel"><fmt:message key="label.daysfromrepackage"/></c:set>
<c:set var="daysFromTestLabel"><fmt:message key="label.daysfromtest"/></c:set>
<c:set var="excludedLabel"><fmt:message key="label.excluded"/></c:set>
<c:set var="exemptLabel"><fmt:message key="label.exempt"/></c:set>
var shelfLifeBasis = new Array(
<c:forEach var="vvShelfLifeBean" items="${catAddHeaderViewBean.shelfLifeBasisColl}" varStatus="status2">
	<c:if test="${status2.index == 0}">
		{text:'${mfgRecommendedLabel}',value:'MfgRecommended'},
		{text:'${indefiniteLabel}',value:'Indefinite'}
	</c:if>
	<c:choose>
		<c:when test="${vvShelfLifeBean.shelfLifeBasis == 'R'}">
			,
			{text:'${daysFromReceiptLabel}',value:'${vvShelfLifeBean.shelfLifeBasis}'}
		</c:when>
		<c:when test="${vvShelfLifeBean.shelfLifeBasis == 'M'}">
			,
			{text:'${daysFromMfgLabel}',value:'${vvShelfLifeBean.shelfLifeBasis}'}
		</c:when>
		<c:when test="${vvShelfLifeBean.shelfLifeBasis == 'S'}">
			,
			{text:'${daysFromShipmentLabel}',value:'${vvShelfLifeBean.shelfLifeBasis}'}
		</c:when>
		<c:when test="${vvShelfLifeBean.shelfLifeBasis == 'P'}">
			,
			{text:'${daysFromRepackageLabel}',value:'${vvShelfLifeBean.shelfLifeBasis}'}
		</c:when>
	    <c:when test="${vvShelfLifeBean.shelfLifeBasis == 'T'}">
	    	,
			{text:'${daysFromTestLabel}',value:'${vvShelfLifeBean.shelfLifeBasis}'}
		</c:when>
		<c:when test="${vvShelfLifeBean.shelfLifeBasis == 'E' && 
			tcmis:isFeatureReleased(personnelBean, 'ExcludedShelfLife', catAddHeaderViewBean.engEvalFacilityId)}">				
			,
			{text:'${excludedLabel}',value:'${vvShelfLifeBean.shelfLifeBasis}'}
		</c:when>
		<c:when test="${vvShelfLifeBean.shelfLifeBasis == 'X' && 
			tcmis:isFeatureReleased(personnelBean, 'ExcludedShelfLife', catAddHeaderViewBean.engEvalFacilityId)}">				
			,
			{text:'${exemptLabel}',value:'${vvShelfLifeBean.shelfLifeBasis}'}
		</c:when>
	</c:choose>	
</c:forEach>
);

var hetUsageRecording = new Array(
    {
	    text:'<fmt:message key="label.dailylogging"/>',
	    value:'Daily Usage'
    },
    {
	    text:'<fmt:message key="label.otr"/>',
	    value:'OTR'
    }
);

<c:if test="${catAddHeaderViewBean.labelColorRequired == 'Y'}">    
    <c:set var="labelColorColl" value='${catAddHeaderViewBean.labelColorColl}'/>
    <bean:size id="labelColorSize" name="labelColorColl"/>
    var labelColor = new Array(
    <c:if test="${labelColorSize > 1}">
    {
        text:'<fmt:message key="label.select"/>',
        value:''
    },
    </c:if>
    <c:forEach var="catalogAddItemBean" items="${catAddHeaderViewBean.labelColorColl}" varStatus="status2">
       <c:if test="${status2.index > 0}">,</c:if>
       {
         text:'<tcmis:jsReplace value="${catalogAddItemBean.labelColor}"/>',
         value:'<tcmis:jsReplace value="${catalogAddItemBean.labelColor}"/>'
        }
    </c:forEach>
    );
</c:if>

var roomTempOutTimeUnit = new Array(
    {
	    text:'<fmt:message key="label.hours"/>',
	    value:'HOURS'
    },
    {
	    text:'<fmt:message key="label.days"/>',
	    value:'DAYS'
    }
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