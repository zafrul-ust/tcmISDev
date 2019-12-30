<c:set var="storageDataCount" value='${0}'/>

function callOnChangeFunction(rowId)
{
	if(gridCellValue(storageBeanGrid,rowId,"newRow") != 'Y')
		 setGridCellValue(storageBeanGrid,rowId,"updated","Y");
}

var storageJsonMainData = new Array();
var storageJsonMainData = {
	rows:[
		<c:forEach var="catalogAddStorageBean" items="${catAddHeaderViewBean.storageDataColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='N'/>

            { id:${status.index +1},
			 data:['${currentPermission}',
			 	'N',
			 	'Y',
			 	'Y',
				'${catalogAddStorageBean.applicationDesc}',
                '${catalogAddStorageBean.maximumQuantityStored}',
                '${catalogAddStorageBean.averageQuantityStored}',
                '${catalogAddStorageBean.application}',
                '${catalogAddStorageBean.companyId}',
                '${catalogAddStorageBean.requestId}',
                'N',
                'N'
             ]}
			 <c:set var="storageDataCount" value='${storageDataCount+1}'/>
		 </c:forEach>
		]};
		
var storageConfig = [
{ columnId:"permission"
},
{ columnId:"applicationDescPermission"
},
{ columnId:"maximumQuantityStoredPermission"
},
{ columnId:"averageQuantityStoredPermission"
},
{ columnId:"applicationDesc",
  columnName:'<fmt:message key="label.workarea"/>',
  width: 15,
  size: 30,
  type:"hed",
  permission:true
},
{ columnId:"maximumQuantityStored",
  columnName:'<fmt:message key="label.maximumquantitystored"/>',
  width: 15,
  type:"hed",
  onChange:callOnChangeFunction,
   size: 30,
  submit:true,
  permission:true
},

{ columnId:"averageQuantityStored",
  columnName:'<fmt:message key="label.averagequantitystored"/>',
  width: 15,
  type:"hed",
  onChange:callOnChangeFunction,
  size: 30,
  submit:true,
  permission:true
},
{ columnId:"application",
  submit:true
},
{ columnId:"companyId",
  submit:true
},
{ columnId:"requestId",
  submit:true
},
{ columnId:"newRow",
  submit:true
},
{ columnId:"updated",
  submit:true
}
];