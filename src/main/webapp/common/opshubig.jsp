<script language="JavaScript" type="text/javascript">
<!--
<c:set var="myinventorygroups">
 <fmt:message key="label.myinventorygroups"/>
</c:set>
var defaults = {
	   ops: {id:'',name:'<fmt:message key="label.myentities"/>'},
   	   hub: {id:'',name:'<fmt:message key="label.myhubs"/>'},
   	   inv: {id:'',name:'<tcmis:jsReplace value="${myinventorygroups}"/>'}
   }

var defaultOpsEntityId = null;
var defaultHub = null;
var preferredInventoryGroup = null;
<c:if test="${!empty personnelBean.opsHubIgOvBeanCollection}">
	defaultOpsEntityId = '${personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId}';
	<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status"> 
		<c:if test="${personnelBean.opsHubIgOvBeanCollection[0].defaultOpsEntityId eq opsEntity.opsEntityId}">
			defaultHub = '${opsEntity.defaultHub}';
		 	<c:forEach var="hub" items="${status.current.hubIgCollection}" varStatus="status1">
		 		<c:if test="${hub.hub eq personnelBean.opsHubIgOvBeanCollection[0].defaultHub && !empty hub.preferredInventoryGroup}">
			 		preferredInventoryGroup = '${hub.preferredInventoryGroup}';
			 	</c:if>
		     </c:forEach>
	     </c:if>
	</c:forEach>
</c:if>
 
var opshubig = [
	<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status"> { 
	  id: '${opsEntity.opsEntityId}', 
	  name: '<tcmis:jsReplace value="${opsEntity.operatingEntityName}"/>',
	  coll: [<c:forEach var="hub" items="${status.current.hubIgCollection}" varStatus="status1"> { 
	  	  id: '${hub.hub}', 
	  	  name: '<tcmis:jsReplace value="${hub.hubName}"/>',
	  	  autoPutHub:'${hub.automatedPutaway}',
		  coll:[<c:forEach var="inventoryGroup" items="${hub.inventoryGroupCollection}" varStatus="status2">
		 	{ 
		 		id:'${inventoryGroup.inventoryGroup}', 
		 		name:'<tcmis:jsReplace value="${inventoryGroup.inventoryGroupName}"/>'}<c:if test="${!status2.last}">,</c:if></c:forEach>
		      ]
		}<c:if test="${!status1.last}">,</c:if>
	      </c:forEach>]
	}<c:if test="${!status.last}">,</c:if>
	</c:forEach>]; 
// -->
</script>
   