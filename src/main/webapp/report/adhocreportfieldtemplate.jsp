<script language="JavaScript" type="text/javascript">
<!--

	var reportSelectedRowId;
	
	with(milonic=new menuname("rightClickReportRemove")){
	    top="offset=2"
	    style = contextStyle;
	    margin=3
	    aI("text=<fmt:message key="label.removeLine"/>;url=javascript:delReport();");
	}
	<%-- Initialize the RCMenus --%>
    drawMenus();

    var reportFieldGridConfig = {
            divName:'reportFieldGridDiv', // the div id to contain the grid.this is also the dynabean form that will be sent back to the server
            beanData:'reportFieldJsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
            beanGrid:'reportFieldBeanGrid',     // the grid's name, as in beanGrid.attachEvent...
            config:'reportFieldConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
            rowSpan:'false',			 // this page has rowSpan > 1 or not.
            submitDefault:'true',    // the fields in grid are defaulted to be submitted or not.
            singleClickEdit:false,     // this will open the txt cell type to enter notes by single click  // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
            noSmartRender:false, // If set to true this will disable smart rendering and cause the entire grid to be drawn immediately, default false
            onRowSelect: reportFieldRowSelect,            
            onRightClick: onReportRightClick
    };

    var reportFieldConfig = [
        {
              columnId:"permission"
        },
        {
              columnId:"baseFieldName",
              columnName:'<fmt:message key="adhocusagereport.label.basefields"/>',
              tooltip:true,
              width:40
        },
        {
              columnId:"baseDescription",
              columnName:'<fmt:message key="label.description"/>',
              tooltip:true,
              width:64
        },
        {
            columnId:"baseFieldId"
        }
    ];

    <c:set var="dataCount" value='${0}'/>
    var reportFieldJsonMainData = {
		rows:[
            <c:forEach var="fieldBean" items="${reportFieldBeanCollection}" varStatus="reportFieldStatus">
                <c:if test="${reportFieldStatus.index > 0}">,</c:if>
                {id:${reportFieldStatus.count},
                     data:[
                      'N',
                      '<tcmis:jsReplace value="${fieldBean.name}" />',
                      '<tcmis:jsReplace value="${fieldBean.description}" processMultiLines="true"/>',
                      '<tcmis:jsReplace value="${fieldBean.baseFieldId}" />'
                  ]}
                 <c:set var="dataCount" value='${dataCount+1}'/>
		    </c:forEach>
		]};
	
// -->
</script>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
        <td class="optionTitleBoldLeft" width="100%" style="border-left: 1px solid #D0D0D0; padding: 5px;">
            <button id="reportFieldsEditBtn" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                name="None" value=""  OnClick="popReportBaseFields();return false;"><fmt:message key="label.editlist"/>
            </button>
            &nbsp;&nbsp;&nbsp;
            <img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="moveReportFields(true);"/>
            <img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="moveReportFields(false);"/>
        </td>
    </tr>

    <%--
    <tr>
        <td class="optionTitleBoldLeft" width="100%" style="border-left: 1px solid #D0D0D0; padding: 5px;">
            <select name="reportFieldList" id="reportFieldList" class="selectBox" size="10" multiple>
                <option value="xxblankxx">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </option>
                <c:forEach var="fieldBean" items="${reportFieldBeanCollection}" varStatus="status">
                    <option value="${fieldBean.baseFieldId}">${fieldBean.name}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    --%>

    <tr>
       <td>
            <div class="dataContent">
                <div id="reportFieldGridDiv"  style="width:100%;height:170px;"></div>
            </div>
		</td>
    </tr>
</table>

<input type="hidden" name="baseFieldId" id="baseFieldId" value=""/>
<input type="hidden" name="baseFieldName" id="baseFieldName" value=""/>
<input type="hidden" name="baseDescription" id="baseDescription" value=""/>
