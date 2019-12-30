<c:set var="useApprovalDataCount" value='${0}'/>
<c:set var="showWasteWaterDischarge" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowWasteWaterDischarge', catAddHeaderViewBean.engEvalFacilityId, catAddHeaderViewBean.companyId)}"/>
var showWasteWaterDischarge = ${showWasteWaterDischarge};
<c:set var="showEmissionPoints" value="${tcmis:isCompanyFeatureReleased(personnelBean,'ShowEmissionPoints', catAddHeaderViewBean.engEvalFacilityId, catAddHeaderViewBean.companyId)}"/>
var showEmissionPoints = ${showEmissionPoints};

var useApprovalJsonMainData = new Array();
var useApprovalJsonMainData = {
	rows:[
		<c:forEach var="catalogAddUserGroupBean" items="${catAddHeaderViewBean.useApprovalDataColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='N'/>

			<c:set var="approved"><fmt:message key="label.approved"/></c:set>
			<c:set var="unapproved"><fmt:message key="label.unapproved"/></c:set>
			<c:set var="tmpApprovalStatus" value="${status.current.approvalStatus}"/>
			<c:set var="tmpApprovalRequestStatus" value="${status.current.approvalRequestStatus}"/>
			<c:if test="${status.current.dataSource == 'new' && catAddHeaderViewBean.viewLevel != 'view' && catAddHeaderViewBean.allowEditUseApproval == 'Y'}">
				<c:set var="currentPermission" value='Y'/>
			</c:if>
			<c:if test="${status.current.dataSource == 'catalog'}">
				<c:if test="${tmpApprovalStatus == 'approved'}">
					<c:set var="tmpApprovalStatus" value="${approved}"/>
				</c:if>
				<c:if test="${tmpApprovalStatus == 'unapproved'}">
					<c:set var="tmpApprovalStatus" value="${unapproved}"/>	
				</c:if>

				<c:set var="tmpApprovalRequestStatus" value=" "/>
			</c:if>

			<c:set var="restriction1" value=''/>
			<c:set var="restriction2" value=''/>
			<c:if test="${!empty status.current.quantity1}">
				<c:set var="restriction1">${status.current.quantity1} <fmt:message key="label.per"/> ${status.current.per1} ${status.current.period1}</c:set>
			</c:if>
			<c:if test="${!empty status.current.quantity2}">
				<c:set var="restriction2">${status.current.quantity2} <fmt:message key="label.per"/> ${status.current.per2} ${status.current.period2}</c:set>
			</c:if>

			<c:set var="workAreaDesc"><tcmis:jsReplace value="${status.current.applicationDesc}" processMultiLines="true"/></c:set>
			<c:if test="${status.current.workArea == 'All'}">
				<c:set var="workAreaDesc"><fmt:message key="label.allWorkAreas"/></c:set>
			</c:if>
            <c:if test="${status.current.workArea == 'All' && catAddHeaderViewBean.specificUseApprovalRequired == 'Y'}">
				<c:set var="workAreaDesc"><fmt:message key="label.allexceptcontrolled"/></c:set>
			</c:if>

            { id:${status.index +1},
			 data:['${currentPermission}',
				'${tmpApprovalStatus}',
			    '${tmpApprovalRequestStatus}',
                '<tcmis:jsReplace value="${status.current.applicationUseGroupName}"/>: <tcmis:jsReplace value="${status.current.applicationUseGroupDesc}"/>',
                '${workAreaDesc}',
			    '<tcmis:jsReplace value="${status.current.inventoryGroup}"/>',
				'<tcmis:jsReplace value="${status.current.userGroupDesc}" processMultiLines="true"/>',
				'<tcmis:jsReplace value="${status.current.processDesc}" processMultiLines="true"/>',
				'${status.current.estimatedAnnualUsage}',
				'${restriction1}',
				'${restriction2}',
				<c:if test= "${showWasteWaterDischarge}" >
					<c:choose>
						<c:when test="${status.current.wasteWaterDischarge == 'Y'}">
							'<fmt:message key="label.yes"/>',
						</c:when>
						<c:when test="${status.current.wasteWaterDischarge == 'N'}">
							'<fmt:message key="label.no"/>',
						</c:when>
						<c:otherwise>
							'',
						</c:otherwise>
					</c:choose>
					'<tcmis:jsReplace value="${status.current.wasteWaterDischarge}"/>',
				</c:if>
				<c:if test= "${showEmissionPoints}" >
					'<tcmis:jsReplace value="${status.current.emissionPointDescs}"/>',
					'<tcmis:jsReplace value="${status.current.emissionPoints}"/>',
					'<tcmis:jsReplace value="${status.current.emissionPointIdSeparator}"/>',
				</c:if>
				'<tcmis:jsReplace value="${status.current.workArea}"/>',
			    '${status.current.dataSource}',
                '${status.current.applicationUseGroupId}'
             ]}
			 <c:set var="useApprovalDataCount" value='${useApprovalDataCount+1}'/>
		 </c:forEach>
		]};


var useApprovalConfig = [
{ columnId:"permission"
},
{ columnId:"currentStatus",
  columnName:'<fmt:message key="label.currentstatus"/>',
  submit:true
},
{ columnId:"approvalStatus",
  columnName:'<fmt:message key="label.requeststatus"/>',
  type: 'hcoro',
  submit:true
},
{ columnId:"workAreaGroupDisplay",
  <c:if test="${catAddHeaderViewBean.hasApplicationUseGroup == 'Y'}">
    columnName:'<fmt:message key="label.approvalcode"/>',
  </c:if>
  width: 15,
  tooltip:"Y",
  submit:true
},
{ columnId:"workAreaDisplay",
  columnName:'<fmt:message key="label.workarea"/>',
  width: 15,
  submit:true
},
{ columnId:"inventoryGroup",
  columnName:'<fmt:message key="label.inventorygroup"/>',
  type: 'hcoro',
  submit:true
},
{ columnId:"userGroupId",
  columnName:'<fmt:message key="label.usergroup"/>',
  width: 10,
  submit:true
},
{ columnId:"processDesc",
  columnName:'<fmt:message key="label.useprocessdesc"/>',
  width: 18,
  submit:true
},
{ columnId:"estimatedAnnualUsage",
  columnName:'<fmt:message key="label.estimateannualusage"/>',
  submit:true
},
{ columnId:"limit1",
  columnName:'<fmt:message key="label.restriction1"/>',
  submit:true
},
{ columnId:"limit2",
  columnName:'<fmt:message key="label.restriction2"/>',
  submit:true
},
<c:if test= "${showWasteWaterDischarge}" >
	{ columnId:"wasteWaterDischargeDisplay",
	  columnName:'<fmt:message key="label.wastewaterdischarge"/>',
	  align:'center'
	},
	{columnId:"wasteWaterDischarge"},
</c:if>
<c:if test= "${showEmissionPoints}" >
	{ columnId:"emissionPointDescs",
	  columnName:'<fmt:message key="label.emissionpoint"/>',
	  width:32,
	  tooltip:true
	},
	{columnId:"emissionPoints"},
	{columnId:"emissionPointIdSeparator"},
</c:if>
{ columnId:"workArea",
  columnName:'',
  submit:true
},
{ columnId:"dataSource",
  columnName:'',
  submit:true
}
,
{ columnId:"applicationUseGroupId",
  columnName:'',
  submit:true
}
];

<c:set var="approved"><fmt:message key="label.approved"/></c:set>
<c:set var="unapproved"><fmt:message key="label.unapproved"/></c:set>
var approvalStatus = new Array(
				{text:'${approved}',value:'approved'},
				{text:'${unapproved}',value:'unapproved'}
);


<c:set var="igColl" value='${catAddHeaderViewBean.inventoryGroupColl}'/>
<bean:size id="igSize" name="igColl"/>
var inventoryGroup = new Array(
<c:if test="${igSize > 1}">
{
	text:'<fmt:message key="label.allinventorygroups"/>',
	value:'All'
},
</c:if>
<c:forEach var="inventoryGroupDefinitionBean" items="${catAddHeaderViewBean.inventoryGroupColl}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {text:'<tcmis:jsReplace value="${inventoryGroupDefinitionBean.inventoryGroupName}"/>',
	 value:'<tcmis:jsReplace value="${inventoryGroupDefinitionBean.inventoryGroup}"/>'
	}
</c:forEach>
);
