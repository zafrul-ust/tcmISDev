<script language="JavaScript" type="text/javascript">
<!--
var altFacilityGroupId = new Array(
		<c:set var="currentFacilityGroup" value=''/>
		<c:forEach var="facGroupBean" items="${facAppReportViewBeanCollection[0].facilityGroupList}" varStatus="status">
			<c:if test="${currentFacilityGroup != facGroupBean.facilityGroupId}">
				<c:if test="${status.index > 0}">,</c:if>
				'<tcmis:jsReplace value="${facGroupBean.facilityGroupId}"/>'
				<c:set var="currentFacilityGroup" value='${facGroupBean.facilityGroupId}'/>
			</c:if>
		</c:forEach>
		);

		var altFacilityGroupDescription = new Array(
		<c:set var="currentFacilityGroup" value=''/>
		<c:forEach var="facGroupBean" items="${facAppReportViewBeanCollection[0].facilityGroupList}" varStatus="status">
			<c:if test="${currentFacilityGroup != facGroupBean.facilityGroupId}">
				<c:if test="${status.index > 0}">,</c:if>
				'<tcmis:jsReplace value="${facGroupBean.facilityGroupDescription}"/>'
				<c:set var="currentFacilityGroup" value='${facGroupBean.facilityGroupId}'/>
			</c:if>
		</c:forEach>
		);

		var altFacilityId = new Array();

		<c:set var="currentFacilityGroupId" value=''/>
		<c:forEach var="facGroupBean" items="${facAppReportViewBeanCollection[0].facilityGroupList}" varStatus="status">
		<c:if test="${currentFacilityGroupId != facGroupBean.facilityGroupId}">
			<c:set var="count" value='0'/>
			altFacilityId['<tcmis:jsReplace value="${facGroupBean.facilityGroupId}"/>'] = new Array(
				<c:forEach var="facilityBean" items="${facAppReportViewBeanCollection[0].facilityGroupList}" varStatus="status1">
					<c:if test="${facilityBean.facilityGroupId == facGroupBean.facilityGroupId}">
						<c:if test="${count != 0}">,</c:if>
						<c:if test="${count == 0}"><c:set var="count" value='1'/></c:if>
						'${facilityBean.facilityId}'
					</c:if>
				</c:forEach>
		);
		<c:set var="currentFacilityGroupId" value='${facGroupBean.facilityGroupId}'/>
		</c:if>
		</c:forEach>

		var altFacilityName = new Array();

		<c:set var="currentFacilityGroupId" value=''/>
		<c:forEach var="facGroupBean" items="${facAppReportViewBeanCollection[0].facilityGroupList}" varStatus="status">
		<c:if test="${currentFacilityGroupId != facGroupBean.facilityGroupId}">
			<c:set var="count" value='0'/>
			altFacilityName['<tcmis:jsReplace value="${facGroupBean.facilityGroupId}"/>'] = new Array(
				<c:forEach var="facilityBean" items="${facAppReportViewBeanCollection[0].facilityGroupList}" varStatus="status1">
					<c:if test="${facilityBean.facilityGroupId == facGroupBean.facilityGroupId}">
						<c:if test="${count != 0}">,</c:if>
						<c:if test="${count == 0}"><c:set var="count" value='1'/></c:if>
						<c:forEach var="facilityBean2" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status1">
							<c:if test="${facilityBean2.facilityId == facilityBean.facilityId}">
								'${facilityBean2.facilityName}'
							</c:if>
						</c:forEach>
					</c:if>
				</c:forEach>
		);
		<c:set var="currentFacilityGroupId" value='${facGroupBean.facilityGroupId}'/>
		</c:if>
		</c:forEach>

				var altAreaId = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					altAreaId['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
						<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
							<c:if test="${status1.index > 0}">,</c:if>
							'${areaBean.areaId}'
						</c:forEach>
				);
				</c:forEach>

				var altAreaName = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					altAreaName['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
						<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
							<c:if test="${status1.index > 0}">,</c:if>
							'<tcmis:jsReplace value="${areaBean.areaName}"/>'
						</c:forEach>
				);
				</c:forEach>

				var altBuildingId = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
				        <c:set var="currentArea" value='${areaBean.areaId}'/>
				        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
				        altBuildingId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/>'] = new Array(
				            <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
				                <c:if test="${status2.index > 0}">,</c:if>
				                '${bldgBean.buildingId}'
				            </c:forEach>
				        );
				    </c:forEach>
				</c:forEach>

				var altBuildingName = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
				        <c:set var="currentArea" value='${areaBean.areaId}'/>
				        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
				        altBuildingName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/>'] = new Array(
				            <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
				                <c:if test="${status2.index > 0}">,</c:if>
				                '<tcmis:jsReplace value="${bldgBean.buildingName}"/>'
				            </c:forEach>
				        );
				    </c:forEach>
				</c:forEach>

				var altFloorId = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
				        <c:set var="currentArea" value='${areaBean.areaId}'/>
				        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
				        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
				            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
				            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
				            altFloorId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/>'] = new Array(
				            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
				                <c:if test="${status3.index > 0}">,</c:if>
				                '${floorBean.floorId}'
				            </c:forEach>
				            );
				        </c:forEach>
				    </c:forEach>
				</c:forEach>

				var altFloorName = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
				        <c:set var="currentArea" value='${areaBean.areaId}'/>
				        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
				        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
				            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
				            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
				            altFloorName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/>'] = new Array(
				            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
				                <c:if test="${status3.index > 0}">,</c:if>
				                '<tcmis:jsReplace value="${floorBean.description}"/>'
				            </c:forEach>
				            );
				        </c:forEach>
				    </c:forEach>
				</c:forEach>

				var altRoomId = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
				        <c:set var="currentArea" value='${areaBean.areaId}'/>
				        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
				        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
				            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
				            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
				            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
					            <c:set var="currentFloor" value='${floorBean.floorId}'/>
					            <c:set var="roomCollection" value='${floorBean.roomList}'/>
						        altRoomId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/><c:out value="${currentFloor}"/>'] = new Array(
						            <c:forEach var="roomBean" items="${roomCollection}" varStatus="status4">
						                <c:if test="${status4.index > 0}">,</c:if>
						                '${roomBean.roomId}'
						            </c:forEach>
					            );
				            </c:forEach>
				        </c:forEach>
				    </c:forEach>
				</c:forEach>

				var altRoomName = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					<c:set var="currentFacility" value='${facBean.facilityId}'/>
					<c:set var="areaCollection" value='${facBean.areaList}'/>
					<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
				        <c:set var="currentArea" value='${areaBean.areaId}'/>
				        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
				        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
				            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
				            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
				            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
					            <c:set var="currentFloor" value='${floorBean.floorId}'/>
					            <c:set var="roomCollection" value='${floorBean.roomList}'/>
					            altRoomName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/><c:out value="${currentFloor}"/>'] = new Array(
						            <c:forEach var="roomBean" items="${roomCollection}" varStatus="status4">
						                <c:if test="${status4.index > 0}">,</c:if>
						                '<tcmis:jsReplace value="${roomBean.roomName}"/>'
						            </c:forEach>
					            );
					        </c:forEach>
				        </c:forEach>
				    </c:forEach>
				</c:forEach>

				var deptColl = {};
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="facStatus">
					var currDepArr = new Array();
					<c:forEach var="deptBean" items="${facBean.deptList}" varStatus="deptStatus">
							currDepArr[<c:out value="${deptStatus.index}"/>] = {deptName:'${deptBean.deptName}',
																			  deptId:'${deptBean.deptId}'};
					</c:forEach>
					deptColl['${facBean.facilityId}'] = currDepArr;
				</c:forEach>

				var wagColl = {};
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="facStatus">
					var currWagArr = new Array();
					<c:forEach var="wagBean" items="${facBean.reportingEntityList}" varStatus="wagStatus">
							currWagArr[<c:out value="${wagStatus.index}"/>] = {wagName:'${wagBean.description}',
																			  wagId:'${wagBean.reportingEntityId}'};
					</c:forEach>
					wagColl['${facBean.facilityId}'] = currWagArr;
				</c:forEach>

				
				var altCatalogId = new Array();
				<c:set var="currentFacility" value=''/>
				<c:forEach var="facCatalogBean" items="${facilityCatalogColl}" varStatus="status">
					<c:choose>
						<c:when test="${currentFacility eq facCatalogBean.facilityId}">
							,'${facCatalogBean.catalogId}'
						</c:when>
						<c:otherwise>
							<c:set var="currentFacility" value='${facCatalogBean.facilityId}'/>
							<c:if test="${status.index > 0}">);</c:if>
							altCatalogId['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
								'${facCatalogBean.catalogId}'
						</c:otherwise>
					</c:choose>
				</c:forEach>
				);
							
							
				var altCatalogCompanyId = new Array();
				<c:set var="currentFacility" value=''/>
				<c:forEach var="facCatalogBean" items="${facilityCatalogColl}" varStatus="status">
					<c:choose>
						<c:when test="${currentFacility eq facCatalogBean.facilityId}">
							,'${facCatalogBean.catalogCompanyId}'
						</c:when>
						<c:otherwise>
							<c:set var="currentFacility" value='${facCatalogBean.facilityId}'/>
							<c:if test="${status.index > 0}">);</c:if>
							altCatalogCompanyId['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
								'${facCatalogBean.catalogCompanyId}'
						</c:otherwise>
					</c:choose>
				</c:forEach>
				);			
														
				<%--var altCatalogDesc = new Array();
				<c:set var="currentFacility" value=''/>
				<c:forEach var="facCatalogBean" items="${facilityCatalogColl}" varStatus="status">
					<c:if test="${currentFacility eq facBean.facilityId}">
						,'${facCatalogBean.catalogDesc}'
					</c:if>
					<c:if test="${currentFacility != facCatalogBean.facilityId}">
						<c:set var="currentFacility" value='${facCatalogBean.facilityId}'/>
						<c:if test="${status.index > 0}">);</c:if>
						altCatalogDesc['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
							'${facCatalogBean.catalogDesc}'
					</c:if>
				</c:forEach>
				);--%>
				
				var altCatalogDesc = new Array();
				<c:set var="currentFacility" value=''/>
				<c:forEach var="facCatalogBean" items="${facilityCatalogColl}" varStatus="status">
					<c:choose>
					<c:when test="${currentFacility eq facCatalogBean.facilityId}">
						,'<tcmis:jsReplace value="${facCatalogBean.catalogDesc}"/>'
					</c:when>
					<c:otherwise>
						<c:set var="currentFacility" value='${facCatalogBean.facilityId}'/>
						<c:if test="${status.index > 0}">);</c:if>
						altCatalogDesc['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
								'<tcmis:jsReplace value="${facCatalogBean.catalogDesc}"/>'
					</c:otherwise>
				</c:choose>
				</c:forEach>
				);
	
				var featureReleaseCheck = new Array();
				<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
					featureReleaseCheck['<tcmis:jsReplace value="${facBean.facilityId}"/>'] = 'N';
					<tcmis:featureReleased feature="ShowMaterialSubcategory" scope="${facBean.facilityId}">
						featureReleaseCheck['<tcmis:jsReplace value="${facBean.facilityId}"/>'] = 'Y';
					</tcmis:featureReleased>
				</c:forEach>

                var flammabilityControlZoneIdColl = new Array();                
                <c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="facStatus">
                    var currFlammabilityControlZoneArr = new Array();
                    <c:forEach var="flamBean" items="${facBean.flammabilityControlZoneList}" varStatus="flamStatus">
                           currFlammabilityControlZoneArr[<c:out value="${flamStatus.index}"/>] = {flammabilityControlZoneId:'${flamBean.flammabilityControlZoneId}',
                            		flammabilityControlZoneDesc:'${flamBean.flammabilityControlZoneDesc}'};
                    </c:forEach>
                    flammabilityControlZoneIdColl['${facBean.facilityId}'] = currFlammabilityControlZoneArr;
                </c:forEach>

                var vocZoneIdColl = new Array();                
                <c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="facStatus">
                    var currVocZoneArr = new Array();
                    <c:forEach var="vocBean" items="${facBean.vocZoneList}" varStatus="vocStatus">
                           currVocZoneArr[<c:out value="${vocStatus.index}"/>] = {vocZoneId:'${vocBean.vocZoneId}',
                                    vocZoneDescription:'${vocBean.vocZoneDescription}'};
                    </c:forEach>
                    vocZoneIdColl['${facBean.facilityId}'] = currVocZoneArr;
                </c:forEach>

                
                <c:set var="hideAreaBldgFlrRmOptions" value='N'/>
                var hideAreaBldgFlrRmOptionsJsp = 'N';
                <tcmis:featureReleased feature="HideAreaBldgFlrRmOptions" scope="ALL">                    
                    <c:set var="hideAreaBldgFlrRmOptions" value='Y'/>
                    var hideAreaBldgFlrRmOptionsJsp = 'Y';
                    
                </tcmis:featureReleased>
                
                
				
		// -->
		 </script>
	  
	  <tr >
        <td nowrap width="100%" class="optionTitleBold">
        <fmt:message key="label.facilitygroup"/>:&nbsp;
		<select class="selectBox" id="facilityGroupId" name="facilityGroupId" onchange="facilityGroupChanged();">
		</select>
		<input type="hidden" name="facilityGroupName" id="facilityGroupName" value=""/>	
        		<fmt:message key="label.facility"/>:&nbsp;
		<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
		</select>
		<input type="hidden" name="facilityName" id="facilityName" value=""/>	
		&nbsp;&nbsp;&nbsp;
		<span id="hideAreaBldgFlrRmOptionsSpan">
		<fmt:message key="label.area"/>:&nbsp;
		<select class="selectBox" id="areaIdSel" name="areaIdSel" onchange="areaChanged();">
		</select>
		<input name="areaName" id="areaName" value="" class="inputBox"  style="display: none" readonly="readonly" size="33"/>
  		<input type="hidden" name="areaId" id="areaId" value="" />	
		<button id="areaMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value="" onClick="popMultiSel('areaId');return false;" ></button>
		&nbsp;&nbsp;&nbsp;
		<span id="areaIdSpan">
		 </span>	  
		<%--<span id="buildingFloorRoom"> --%>
			<fmt:message key="label.building"/>:&nbsp;
			<select class="selectBox" id="buildingIdSel" name="buildingIdSel" onchange="buildingChanged();">
			</select>
			<input name="buildingName" id="buildingName" value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
  			<input type="hidden" name="buildingId" id="buildingId" value="" />	
			<button id="buildingMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value=""  onClick="popMultiSel('buildingId');return false;"></button>
			&nbsp;&nbsp;&nbsp;
			<span id="buildingIdSpan">
		     </span>	  
		<%--</span> --%>
			&nbsp;&nbsp;&nbsp;
			<fmt:message key="label.floor"/>:&nbsp;
			<select class="selectBox" id="floorId" name="floorId" onchange="floorChanged()">
			</select>
			<input type="hidden" name="floorName" id="floorName" value=""/>	
			&nbsp;&nbsp;&nbsp;
			<fmt:message key="label.room"/>:&nbsp;
			<select class="selectBox" id="roomId" name="roomId" onchange="roomChanged()">
			</select>
			<input type="hidden" name="roomName" id="roomName" value=""/>	
		</span>
		</td>
      </tr>
        <c:if test="${showFlammabilityVocZone == 'Y'}">
	        <tr>
	        	<td width="100%" nowrap="true" class="optionTitleBoldLeft">
		    	<fmt:message key="label.flammable"/>:<input type="checkbox" name="flammable" value="" id="flammable" onclick="if(this.value=='Y'){this.value='';}else{this.value='Y'}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		      	<c:if test="${pageId != 'adHocMatMx'}"> 
	                  <span id="flammabilityControlZoneIdShowSpan" style="display: none">
	                        <fmt:message key="label.flammabilitycontrolzone"/>:&nbsp;
	                        <select class="selectBox" id="flammabilityControlZoneIdSel" name="flammabilityControlZoneIdSel" onchange="flammabilityControlZoneIdChanged();"></select>
	                        <input name="flammabilityControlZoneDesc" id="flammabilityControlZoneDesc" value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
	                        <input type="hidden" name="flammabilityControlZoneId" id="flammabilityControlZoneId" value="" />
	                        <button id="flammabilityControlZoneIdMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value=""  onClick="popMultiSel('flammabilityControlZoneId');return false;"></button>&nbsp;
	                        <c:if test="${pageId == 'adHocInv'}">
		                        <input type="checkbox" id="overFlamCtrlZnLimit" name="overFlamCtrlZnLimit" onclick="overFlamCtrlZnLimitChanged();" class="radioBtns" value="Y" ><fmt:message key="label.overflammabilitylimit"/>:&nbsp;
		                        <input class="inputBox" type="text" id="overFlamCtrlZnLmtPercent" name="overFlamCtrlZnLmtPercent" size="5" maxlength="10" value="" onchange="validateOverFlamCtrlZnLmtPercent();"/>%
		                    </c:if>
	                        <span id="flammabilityControlZoneIdSpan"></span>
	                  </span>
	                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                  <span id="vocZoneIdShowSpan" style="display: none">
	                    <fmt:message key="label.voczone"/>:&nbsp;
	                    <select class="selectBox" id="vocZoneIdSel" name="vocZoneIdSel" onchange="vocZoneIdChanged();"></select>
	                    <input name="vocZoneDescription" id="vocZoneDescription" value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
	                    <input type="hidden" name="vocZoneId" id="vocZoneId" value="" />
	                    <button id="vocZoneIdMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'" name="None" value=""  onClick="popMultiSel('vocZoneId');return false;"></button>
	                    <span id="vocZoneIdSpan"></span>  
	                  </span>    
		      	</c:if>
		      	  </td>
	      	</tr>
      	</c:if>
      <c:if test="${showFlammabilityVocZone == 'N' || pageId == 'adHocMatMx'}">
        <input type="hidden" name="flammable" id="flammable" value="" />
        <input type="hidden" name="flammabilityControlZoneIdSel" id="flammabilityControlZoneIdSel" value="" />
        <input type="hidden" name="flammabilityControlZoneDesc" id="flammabilityControlZoneDesc" value="" />
        <input type="hidden" name="flammabilityControlZoneId" id="flammabilityControlZoneId" value="" />
        <input type="hidden" name="overFlamCtrlZnLimit" id="overFlamCtrlZnLimit" value="" />
        <input type="hidden" name="overFlamCtrlZnLmtPercent" id="overFlamCtrlZnLmtPercent" value="" />

        <input type="hidden" name="vocZoneIdSel" id="vocZoneIdSel" value="" />
        <input type="hidden" name="vocZoneDescription" id="vocZoneDescription" value="" />
        <input type="hidden" name="vocZoneId" id="vocZoneId" value="" />
      </c:if>

      <tr>
      		<td width="100%" nowrap="true" class="optionTitleBoldLeft">
      		<span id="adhocMatShowSpan">
	          <fmt:message key="label.workareagroup"/>:
	          <select class="selectBox"  name="reportingEntityId" id="reportingEntityId" onchange="workAreaGroupChanged()">
	          </select>
	          <input type="hidden" name="reportingEntityName" id="reportingEntityName" value=""/>	
	          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	          	<span id="showDeptSpan" style="display: none">
				<fmt:message key="label.department"/>:&nbsp;
				<select class="selectBox" id="deptIdSel" name="deptIdSel" onchange="deptChanged();">
				</select>
				<input name="deptName" id="deptName"  value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
		 			<input type="hidden" name="deptId" id="deptId" value="" />	
				<button id="deptMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'"
		                	name="None" value=""  onClick="popMultiSel('deptId');return false;"> </button>
		        <span id="deptIdSpan">
		        </span>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </span>
           </span>
	           <fmt:message key="label.workarea"/>:
                <select name="applicationSel" id="applicationSel" class="selectBox" onchange="workAreaChanged()"></select>
                <input name="applicationDesc" id="applicationDesc" value="" class="inputBox" style="display: none" readonly="readonly" size="33"/>
                <input type="hidden" name="application" id="application" value=""/>
                <button id="applicationMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'"
                            name="None" value=""  onClick="popMultiSel('application');return false;"></button>
                <span id="applicationSpan">
                </span>
	      </td>  
	    </tr>

	    <tr id="subcategorySpan" style="display:none;">
	      <td width="100%" nowrap="true" class="optionTitleBoldLeft"  >
			       <input type="hidden" name="hideCatalogId" id="hideCatalogId" value="${tcmis:isFeatureReleased(personnelBean,'MatlCatIdNotRequired', 'ALL')}"/>
			       <span id="showCatalogSpan">
					<fmt:message key="label.catalog"/>:&nbsp;
					<select class="selectBox" id="catalogId" name="catalogId" onchange="catalogChanged()">
					</select>
					&nbsp;&nbsp;&nbsp;
					</span>
					<fmt:message key="label.materialcategory"/>:&nbsp;
					<select name="materialCategoryIdSel" id="materialCategoryIdSel" class="selectBox" onchange="materialCategorySearchCall('materialCategoryId')"></select>
			      	<input name="materialCategoryName" id="materialCategoryName" value="" class="inputBox" style="display:none" readonly="readonly" size="33"/>
				 	<input type="hidden" name="materialCategoryId" id="materialCategoryId" value=""/>	
					<button id="materialCategoryMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'"
				                	name="None" value=""  onClick="popMultiSel('materialCategoryId');return false;"></button>
					<span id="materialCategoryIdSpan">
				    </span>		
				    &nbsp;&nbsp;&nbsp;
					<fmt:message key="label.materialsubcategory"/>:&nbsp;
			      	<select name="materialSubcategoryIdSel" id="materialSubcategoryIdSel" class="selectBox" onchange="window.clearElements('materialSubcategoryId');window.createElement('materialSubcategoryId',this.value);$('materialSubcategoryId').value = this.value;$('materialSubcategoryName').value = this.options[this.selectedIndex].text;"></select>
			      	<input name="materialSubcategoryName" id="materialSubcategoryName" value="" class="inputBox" style="display:none" readonly="readonly" size="33"/>
				 	<input type="hidden" name="materialSubcategoryId" id="materialSubcategoryId" value=""/>	
					<button id="materialSubcategoryMultiSel" class="listButton" onmouseover="this.className='listButton listButtonOvr'" onMouseout="this.className='listButton'"
				                	name="None" value=""  onClick="popMultiSel('materialSubcategoryId');return false;"></button>
					<span id="materialSubcategoryIdSpan">
				    </span>	
					<!--		
					<select class="selectBox" id="materialSubcategoryId" name="materialSubcategoryId">
					</select>-->
		  </td>
      </tr>