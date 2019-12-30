<c:set var="flowdownDataCount" value='${0}'/>
var flowdownJsonMainData = new Array();
var flowdownJsonMainData = {
	rows:[
		<c:forEach var="catalogAddFlowDownBean" items="${catAddHeaderViewBean.flowdownDataColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='Y'/>
			<fmt:formatDate var="fmtDate" value="${status.current.revisionDate}" pattern="${dateFormatPattern}"/>
			{ id:${status.index +1},
			 data:['${currentPermission}',
				'<tcmis:jsReplace value="${status.current.flowDown}"/>',
			   '${status.current.displayStatus}',
				'<tcmis:jsReplace value="${status.current.flowDownDesc}" processMultiLines="true" />',
				'${status.current.flowDownType}',
				'${fmtDate}',
				'${status.current.currentVersion}',
				'${status.current.content}',
				'${status.current.dataSource}'
			 ]}
			 <c:set var="flowdownDataCount" value='${flowdownDataCount+1}'/>
		 </c:forEach>
		]};