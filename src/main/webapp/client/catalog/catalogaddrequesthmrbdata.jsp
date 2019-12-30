<c:set var="hmrbDataCount" value='${0}'/>
var hmrbJsonMainData = new Array();
var hmrbJsonMainData = {
	rows:[
		<c:forEach var="catalogAddHmrbBean" items="${catAddHeaderViewBean.hmrbDataColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='Y'/>
            <fmt:formatDate var="fmtStartDate" value="${catalogAddHmrbBean.beginDateJsp}" pattern="${dateFormatPattern}"/>
            <fmt:formatDate var="fmtEndDate" value="${catalogAddHmrbBean.endDateJsp}" pattern="${dateFormatPattern}"/>
            <c:set var="editExpirationPermission" value='N'/>
            <c:if test="${setUpdateExpirationPermission == 'Y' && catalogAddHmrbBean.dataSource == 'catalog'}">
                <c:set var="editExpirationPermission" value='Y'/>
            </c:if>
            <c:if test="${param.setUpdateExpirationPermission == 'Y' && catalogAddHmrbBean.dataSource == 'catalog'}">
                <c:set var="editExpirationPermission" value='Y'/>
            </c:if>

            { id:${status.index +1},
			 data:['${currentPermission}',
				'${catalogAddHmrbBean.approvalCodeStatus}',
                '${catalogAddHmrbBean.approvalCodeName}',
                '${catalogAddHmrbBean.usageCategoryName}',
                '${catalogAddHmrbBean.usageSubcategoryName}',
                '${catalogAddHmrbBean.materialCategoryName}',
                '${catalogAddHmrbBean.materialSubcategoryName}',
                '${editExpirationPermission}',
                '${fmtStartDate}',
                '${editExpirationPermission}',
                '${fmtEndDate}',
                '<tcmis:jsReplace value="${catalogAddHmrbBean.pointOfContact}" processMultiLines="true" />',
                '${catalogAddHmrbBean.hmrbLineItem}',
                '${catalogAddHmrbBean.dataSource}',
                '${catalogAddHmrbBean.applicationUseGroupId}',
                '${catalogAddHmrbBean.emapRequired}'
                
             ]}
			 <c:set var="hmrbDataCount" value='${hmrbDataCount+1}'/>
		 </c:forEach>
		]};

var hmrbConfig = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  submit:true
},
{ columnId:"approvalCodeName",
  columnName:'<fmt:message key="label.approvalcode"/>',
  submit:true
},
{ columnId:"usageCategoryName",
  columnName:'<fmt:message key="label.usagecategory"/>',
  submit:true
},
{ columnId:"usageSubcategoryName",
  columnName:'<fmt:message key="label.usagesubcategory"/>',
  width: 15,
  submit:true
},
{ columnId:"materialCategoryName",
  columnName:'<fmt:message key="label.materialcategory"/>',
  width: 12,
  submit:true
},
{ columnId:"materialSubcategoryName",
  columnName:'<fmt:message key="label.materialsubcategory"/>',
  width: 12,
  submit:true
},
{
columnId:"beginDateJspPermission"
},
{ columnId:"beginDateJsp",
  columnName:'<fmt:message key="label.begindate"/>',
  type:'hcal',
  permission:true,
  submit:true
},
{
columnId:"endDateJspPermission"
},
{ columnId:"endDateJsp",
  columnName:'<fmt:message key="label.enddate"/>',
  type:'hcal',
  permission:true,
  submit:true
},
{ columnId:"pointOfContact",
  columnName:'<fmt:message key="label.pointofcontact"/>',
  width: 12,
  tooltip:"Y",
  submit:true
},
{ columnId:"hmrbLineItem",
  submit:true
},
{ columnId:"dataSource",
  submit:true
},
{ columnId:"applicationUseGroupId",
  submit:true
},
{ columnId:"emapRequired",
  submit:false
}
];