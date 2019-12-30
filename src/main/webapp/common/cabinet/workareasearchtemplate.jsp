<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<script language="JavaScript" type="text/javascript">
<!--
		var altCompanyId = new Array();		
		var altCompanyName = new Array();
		var altFacilityId = new Array();
		var altFacilityName = new Array();
		var altAreaId = new Array();
		var altAreaName = new Array();
		var altBuildingId = new Array();
		var altBuildingName = new Array();
		var wagColl = new Array();
		var deptColl = new Array();
				
		<c:set var="companyCount" value='0'/>
		<c:set var="facilityCount" value='0'/>

		<c:forEach var="company" items="${facAppReportViewBeanCollection}" varStatus="companyStatus">
			// add company 	
			altCompanyId[${companyStatus.index}] = '<tcmis:jsReplace value="${company.companyId}"/>';		
			altCompanyName[${companyStatus.index}] = '<tcmis:jsReplace value="${company.companyName}"/>';

			<c:set var="facilityCount" value='0'/>
			
			// add facilities for this company			
			altFacilityId['<tcmis:jsReplace value="${company.companyId}"/>'] = new Array();
			altFacilityName['<tcmis:jsReplace value="${company.companyId}"/>'] = new Array();
		
			<c:forEach var="facility" items="${company.facilityList}" varStatus="facStatus">
				altFacilityId['<tcmis:jsReplace value="${company.companyId}"/>'][${facStatus.index}]='<tcmis:jsReplace value="${facility.facilityId}"/>';
				altFacilityName['<tcmis:jsReplace value="${company.companyId}"/>'][${facStatus.index}]='<tcmis:jsReplace value="${facility.facilityName}"/>';

				// add areas for this facility
				altAreaId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array();
				altAreaName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = new Array();
				
				<c:forEach var="area" items="${facility.areaList}" varStatus="areaStatus">
					altAreaId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'][${areaStatus.index}]='<tcmis:jsReplace value="${area.areaId}"/>';
					altAreaName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'][${areaStatus.index}]='<tcmis:jsReplace value="${area.areaName}"/>';
					
					// add buildings for this area
					altBuildingId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'] = new Array();
					altBuildingName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'] = new Array();
				
		            <c:forEach var="building" items="${area.buildingList}" varStatus="buildingStatus">
		            	altBuildingId['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'][${buildingStatus.index}]='<tcmis:jsReplace value="${building.buildingId}"/>';
		            	altBuildingName['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/><tcmis:jsReplace value="${area.areaId}"/>'][${buildingStatus.index}]='<tcmis:jsReplace value="${building.buildingName}"/>';
		            </c:forEach>
				</c:forEach>
				
				// add work area groups for this facility
				var currWagArr = new Array();
				<c:forEach var="wag" items="${facility.reportingEntityList}" varStatus="wagStatus">
					currWagArr[<c:out value="${wagStatus.index}"/>] = {wagName:'<tcmis:jsReplace value="${wag.description}"/>',
																	  wagId:'${wag.reportingEntityId}'};
				</c:forEach>
				wagColl['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = currWagArr;
				
				// add departments for this facility
				var currDepArr = new Array();
				<c:forEach var="dept" items="${facility.deptList}" varStatus="deptStatus">
					currDepArr[<c:out value="${deptStatus.index}"/>] = {deptName:'<tcmis:jsReplace value="${dept.deptName}"/>',
																	  deptId:'${dept.deptId}'};
				</c:forEach>
				deptColl['<tcmis:jsReplace value="${company.companyId}"/><tcmis:jsReplace value="${facility.facilityId}"/>'] = currDepArr;
			</c:forEach>
		</c:forEach>
		
		// user default facility for each company
		var myDefaultFacilities = {
			<c:forEach var="bean" items="${personnelBean.myCompanyDefaultFacilityIdCollection}" varStatus="status">
				'<tcmis:jsReplace value="${bean.companyId}"/>':'<tcmis:jsReplace value="${bean.facilityId}"/>'
				<c:if test="${!status.last}">,</c:if>
			</c:forEach>	
		};
		
		
		
		// -->
		 </script>
	  <tr >
	  	  <td nowrap width="100%" class="optionTitleBold">
		  	 <span id="company">
	        	<fmt:message key="label.company"/>:&nbsp;
				<select class="selectBox" id="companyId" name="companyId" onchange="companyChanged()">
				</select>
				&nbsp;&nbsp;&nbsp;
			</span>
        	<fmt:message key="label.facility"/>:&nbsp;
			<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
			</select>
			&nbsp;&nbsp;&nbsp;
			<fmt:message key="label.area"/>:&nbsp;
			<select class="selectBox" id="areaIdSel" name="areaIdSel" onchange="areaChanged();">
			</select>
			<input name="areaIdMultiSelTxt" id="areaIdMultiSelTxt" value="" class="inputBox"  style="display: none" readonly="readonly" size="33"/>
	  		<input type="hidden" name="areaId" id="areaId" value="" />	
			<button id="areaMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value="" onClick="popMultiSel('areaId');return false;" style="display:none;"></button>
			&nbsp;&nbsp;&nbsp;
			<span id="areaIdSpan">
			 </span>	  
			<%--<span id="buildingFloorRoom"> --%>
			<fmt:message key="label.building"/>:&nbsp;
			<select class="selectBox" id="buildingIdSel" name="buildingIdSel" onchange="buildingChanged();">
			</select>
			<input name="buildingIdMultiSelTxt" id="buildingIdMultiSelTxt" value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
  			<input type="hidden" name="buildingId" id="buildingId" value="" />	
			<button id="buildingMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value=""  onClick="popMultiSel('buildingId');return false;" <c:if test="${hideMultiSel == 'Y'}">style="display:none;"</c:if>></button>
			&nbsp;&nbsp;&nbsp;
			<span id="buildingIdSpan">
		     </span>	  
			<%--</span> --%>
		</td>
      </tr>
      <tr>
      		<td width="100%" nowrap="true" class="optionTitleBoldLeft">
	          <fmt:message key="label.workareagroup"/>:
	          <select class="selectBox"  name="reportingEntityId" id="reportingEntityId" onchange="workAreaGroupChanged()">
	          </select>
	          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:message key="label.department"/>:&nbsp;
				<select class="selectBox" id="deptIdSel" name="deptIdSel" onchange="deptChanged();">
				</select>
				<input name="deptIdMultiSelTxt" id="deptIdMultiSelTxt"  value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
		 			<input type="hidden" name="deptId" id="deptId" value="" />	
				<button id="deptMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'"
		                	name="None" value=""  onClick="popMultiSel('deptId');return false;" <c:if test="${hideMultiSel == 'Y'}">style="display:none;"</c:if>> </button>
		        <span id="deptIdSpan">
		        </span>	  
	        </td>
      </tr>
      <tr >
	      <td width="100%" nowrap="true" class="optionTitleBoldLeft">
	      		<fmt:message key="label.workarea"/>:
	      		<select name="applicationIdSel" id="applicationIdSel" class="selectBox" onchange="workAreaChanged()"></select>
	      		<input name="applicationIdMultiSelTxt" id="applicationIdMultiSelTxt" value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
		 		<input type="hidden" name="applicationId" id="applicationId" value=""/>	
				<button id="applicationIdMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'"
		                	name="None" value=""  onClick="popMultiSel('applicationId');return false;" <c:if test="${hideMultiSel == 'Y'}">style="display:none;"</c:if>></button>
		        <span id="applicationIdSpan">
		        </span>	                		       	
	      </td>
      </tr>