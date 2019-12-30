<c:set var="specDataCount" value='${0}'/>
var specJsonMainData = new Array();
var specJsonMainData = {
	rows:[
		<c:forEach var="catalogAddSpecBean" items="${catAddHeaderViewBean.specDataColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			<c:set var="currentPermission" value='Y'/>
            <c:set var="itarSpecPermission" value='N'/>
            <c:if test="${catAddHeaderViewBean.allowEditSpec == 'Y' && showItarControl == 'true'}">
                <c:set var="itarSpecPermission" value='Y'/>
            </c:if>
            
            <fmt:formatDate var="fmtSpecDate" value="${status.current.specDate}" pattern="${dateFormatPattern}"/>
			{ id:${status.index +1},
			 data:['${currentPermission}',
				'<tcmis:jsReplace value="${status.current.specId}"/>',
			   '${status.current.displayStatus}',
				'<tcmis:jsReplace value="${status.current.specName}"/>',
				'<tcmis:jsReplace value="${status.current.specTitle}"/>',
				'<tcmis:jsReplace value="${status.current.specVersion}"/>',
				'${status.current.specAmendment}',
				'${fmtSpecDate}',
				'${status.current.coc}',
				'${status.current.coa}',
                '${itarSpecPermission}',
                '${status.current.itar}',
                '${status.current.content}',
				'${status.current.onLine}',
				'${status.current.dataSource}'
			 ]}
			 <c:set var="specDataCount" value='${specDataCount+1}'/>
		 </c:forEach>
		]};

