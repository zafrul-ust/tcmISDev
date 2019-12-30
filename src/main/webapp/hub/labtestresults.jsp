var testResultGridConfig = {
        divName: 'testResultsDisplay',
        beanData: 'testResultData',
        beanGrid: 'testResultGrid',
        config: 'testResultGridColumnConfig',
        rowSpan: false,
        onRowSelect:doOnRowSelected,
        backgroundRender: true,
        submitDefault: false
};

var testResultGridColumnConfig = [
    {columnId:'permission'},
    {columnId:'testId', columnName:'<fmt:message key="label.testid"/>', width:4, submit:true},
    {columnId:'testDesc', columnName:'<fmt:message key="label.testdescription"/>', width:17, submit:false, tooltip:true},
    {columnId:'itemCode', columnName:'<fmt:message key="label.itemcode"/>', width:6, submit:false},
    {columnId:'criteria', columnName:'<fmt:message key="label.testcriteria"/>', width:20, submit:false, tooltip:true},
    {columnId:'passFail', columnName:'<fmt:message key="title.testresults"/>', type:"hcoro", width:10, submit:true, align:'center'},
    {columnId:'labLogNumber', columnName:'<fmt:message key="label.lablog"/>', type:"txt", width:12, submit:true, tooltip:true},
    {columnId:'note', columnName:'<fmt:message key="label.notes"/>', type:"txt", width:12, submit:true, tooltip:true}
];

var passFail = [
      {value: '', text:'<fmt:message key="label.pleaseselect"/>'},
      {value: 'pass', text:'<fmt:message key="label.pass"/>'},
      {value: 'fail', text:'<fmt:message key="label.fail"/>'},
      {value: 'ntes', text:'<fmt:message key="label.nottested"/>'}
];


<c:set var="readonly" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="labPersonnel" inventoryGroup="${testrequestbean.inventoryGroup}">
<c:choose>
   <c:when test="${(testrequestbean.requestStatus == 'Complete')}" >
       <c:set var="readonly" value='N'/>
       </c:when>
      <c:otherwise>
        <c:set var="readonly" value='Y'/>
      </c:otherwise>
</c:choose>
</tcmis:inventoryGroupPermission>
<tcmis:catalogPermission indicator="true" userGroupId="labPersonnel" catalogId="${testrequestbean.catalogId}">
<c:choose>
   <c:when test="${(testrequestbean.requestStatus == 'Complete')}" >
       <c:set var="readonly" value='N'/>
       </c:when>
      <c:otherwise>
        <c:set var="readonly" value='Y'/>
      </c:otherwise>
</c:choose>
</tcmis:catalogPermission>

var testResultData = {
rows:[
    <c:forEach var="testResult" items="${testrequestbean.testResults}" varStatus="status">
        {id:${status.count},
         data:[	'${readonly}',
                '${testResult.testId}',
                '<tcmis:jsReplace value="${testResult.testDesc}" processMultiLines="true"/>',
                '${testResult.itemCode}',
                '<tcmis:jsReplace value="${testResult.criteria}" processMultiLines="true"/>',
                '${testResult.passFail}',
                '<tcmis:jsReplace value="${testResult.labLogNumber}" processMultiLines="true"/>',
                '<tcmis:jsReplace value="${testResult.note}" processMultiLines="true"/>'
              ]
        }
        <c:if test="${!status.last}">,</c:if>
    </c:forEach>
]};

