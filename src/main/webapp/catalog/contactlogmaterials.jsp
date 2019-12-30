<c:set var="materialDataCount" value='${0}'/>
var materialJsonData= new Array();
var materialJsonData = {
	rows:[
		<c:forEach var="contactLogMaterialBean" items="${contactLogBean.materialDataColl}" varStatus="status2">
			<c:if test="${status2.index > 0}">,</c:if>
			<c:set var="currentPermission" value='N'/>
			<fmt:formatDate var="fmtRevisionDate" value="${status2.current.revisionDate}" pattern="${dateFormatPattern}"/>
			{ id:${status2.index +1},
			 data:['${currentPermission}',
				'${status2.current.materialId}',
				'${fmtRevisionDate}'
			 ]}
			 <c:set var="materialDataCount" value='${materialDataCount+1}'/>
		 </c:forEach>
		]};

