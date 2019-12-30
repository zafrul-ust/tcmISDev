<div id="ApprovalRoleDataWinArea" class="errorMessages" style="display: none;width:790px; height: 350px; overflow: auto;">
	<table  border="0" cellpadding="0" cellspacing="0">
		<tr>
            <td>
                &nbsp;
            </td>
        </tr>
        <tr>
			<td>
                <span id="approvalRoleActionSpan">
                    &nbsp;&nbsp;<a href="javascript:approveRequest()"><fmt:message key="label.approve"/></a>
                    <c:if test="${hasHmrbTab == 'Y'}">    
                        | <a href="javascript:approveWithRestriction()"><fmt:message key="label.approvewithrestriction"/></a>    
                    </c:if>
                    | <a href="javascript:rejectRequest()"><fmt:message key="label.reject"/></a>
                    | <a href="javascript:closeChemicalApprovalWin()"><fmt:message key="label.cancel"/></a>
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
				<div id=approvalRoleDataInnerDiv class="errorMessages" style="width:790px; height: 200px; overflow: auto;">
				</div>
			</td>
		</tr>
	</table>

	<script language="JavaScript" type="text/javascript">
	<!--

		var approvalRoleConfig = [
		  {
			columnId:"permission",
			columnName:''
		  },
		  {
			columnId:"status",
			columnName:' ',
			type:'hch',
			align:'center',
			width:1
		  },
		  {
			columnId:"approvalRole",
			columnName:'<fmt:message key="label.approvalRole"/>',
			width:3,
			tooltip:"Y"
		  },
		  {
			columnId:"reason",
			columnName:'<fmt:message key="label.comments"/>',
			type:"txt",
			tooltip:"Y",
			width:6
		  },
          {
			columnId:"lookupComment",
			columnName:' ',
            width:1
          },
          {
			columnId:"showLookupComment"
          },
          {
			columnId:"beforeTcmQc"
          },
          {
			columnId:"beforeTcmSpec"
          }
          ];

		var approvalRoleGridConfig = {
			divName:'approvalRoleDataInnerDiv', // the div id to contain the grid.
			beanData:'jsonApprovalRoleMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'beanApprovalRoleGrid',     // the grid's name, as in beanGrid.attachEvent...
			config:'approvalRoleConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:false,			 // this page has rowSpan > 1 or not.
			submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
	        onRowSelect:selectApprovalRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
			onRightClick:null   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		//	onBeforeSorting:_onBeforeSorting
		};

			/*Storing the data to be displayed in a JSON object array.*/
			var jsonApprovalRoleMainData = new Array();
			var jsonApprovalRoleMainData = {
			rows:[
				<c:forEach var="chemicalApproverBean" items="${catAddHeaderViewBean.approverColl}" varStatus="status">
                    <c:set var="tmpComment" value=''/>
                    <c:set var="tmpShowComment" value='N'/>
                    <c:forEach var="commentBean" items="${catAddHeaderViewBean.approvalCommentColl}" varStatus="status2">
                        <c:if test="${chemicalApproverBean.approvalRole == commentBean.approvalRole}">
                            <c:set var="tmpComment"><img src="/images/newsearch.gif"/></c:set>
                            <c:set var="tmpShowComment" value='Y'/>
                        </c:if>
                    </c:forEach>                                     
                    <c:set var="tmpApprovalRole"><c:out value="${chemicalApproverBean.approvalRole}"></c:out></c:set>
                    <c:if test="${status.index > 0}">,</c:if>
					{ id:${status.index +1},
			 			data:[
							'Y',
							false,
							'<tcmis:jsReplace value="${tmpApprovalRole}"/>',
                            '',
                            '${tmpComment}',
                            '${tmpShowComment}',
                            '${chemicalApproverBean.beforeTcmQc}',
                            '${chemicalApproverBean.beforeTcmSpec}'     
                          ]
					}
			 	</c:forEach>
			]};

	//-->
	</script>
</div>