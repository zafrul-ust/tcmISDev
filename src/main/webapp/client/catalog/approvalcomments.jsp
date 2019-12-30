<div id="ApprovalCommentWinArea" class="errorMessages" style="display: none;width: 850px; height: 750px; overflow: auto;">
	<table  border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                &nbsp;
            </td>
        </tr>
        <tr>
			<td>
                <span id="approvalCommentActionSpan">
                    &nbsp;&nbsp;<a href="javascript:closeApprovalCommentWin()"><fmt:message key="label.cancel"/></a>
                    | <a href="#" onclick="returnSelectedComment(); return false;"><fmt:message key="label.returnselecteddata"/></a>
                </span>
            </td>
		</tr>
        <tr>
            <td>
                &nbsp;
            </td>
        </tr>
        <tr>
			<td>
				<div id=approvalCommentInnerDiv class="errorMessages" style="width: 800px; height: 360px; overflow: auto;">
				</div>
			</td>
		</tr>
	</table>

	<script language="JavaScript" type="text/javascript">
	<!--

		var approvalCommentConfig = [
		  {
			  columnId:"permission"
		  },                             
          {
            columnId:"ok",
            columnName:'<fmt:message key="label.ok"/>',
            type:'hchstatus',
            width:2
          },
		  {
			columnId:"comment",
			columnName:'<fmt:message key="label.comments"/>',
			width:45
		  }
		  ];

		var approvalCommentGridConfig = {
			divName:'approvalCommentInnerDiv', // the div id to contain the grid.
			beanData:'jsonApprovalCommentMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'beanApprovalCommentGrid',     // the grid's name, as in beanGrid.attachEvent...
			config:'approvalCommentConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:false,			 // this page has rowSpan > 1 or not.
			submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
			//onRowSelect:selectApprovalCommentRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
			onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		//	onBeforeSorting:_onBeforeSorting
		};

			/*Storing the data to be displayed in a JSON object array.*/
			var jsonApprovalCommentMainData = new Array();
            var jsonApprovalCommentMainData = {
			rows:[{ id:1,
			 	    data:['Y',false,'']
			}]};

            var altApprovalComment = new Array(
                <c:forEach var="chemicalApproverBean" items="${catAddHeaderViewBean.approvalCommentColl}" varStatus="status">
                   <c:if test="${status.index > 0}">,</c:if>
                   {
                        approvalRole:'<tcmis:jsReplace value="${chemicalApproverBean.approvalRole}"/>',
                        requestComment:'<tcmis:jsReplace value="${chemicalApproverBean.requestComment}"/>'
                    }
                </c:forEach>
            );

    //-->
	</script>
</div>