<c:set var="receiptDataCount" value='${0}'/>
var receiptJsonMainData = new Array();
var receiptJsonMainData = {
	rows:[
		<c:forEach var="labTestReceiptBean" items="${testrequestbean.labTestReceiptColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
            <fmt:formatDate var="fmtDateMfg" value="${labTestReceiptBean.dateOfManufacture}" pattern="${dateFormatPattern}"/>
            <fmt:formatDate var="fmtDateship" value="${labTestReceiptBean.dateOfShipment}" pattern="${dateFormatPattern}"/>
            <fmt:formatDate var="fmtDateReceipt" value="${labTestReceiptBean.dateOfReceipt}" pattern="${dateFormatPattern}"/>
            { id:${status.index +1},
			 data:['N',
                '${labTestReceiptBean.receiptId}',
                '${labTestReceiptBean.lotStatus}',
                '${labTestReceiptBean.radianPo}-${labTestReceiptBean.poLine}',
                '<tcmis:jsReplace value="${labTestReceiptBean.mfgLot}"/>',
                '${fmtDateMfg}',
                '${fmtDateShip}',
                '${fmtDateReceipt}',
                '<tcmis:jsReplace value="${labTestReceiptBean.inventoryGroup}"/>'
             ]}
			 <c:set var="receiptDataCount" value='${receiptDataCount+1}'/>
		 </c:forEach>
		]};

var receiptConfig = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"receiptId",
  columnName:'<fmt:message key="label.receiptid"/>'
},
{ columnId:"status",
  columnName:'<fmt:message key="label.lotstatus"/>',
  width: 10
},
{ columnId:"radianPo",
  columnName:'<fmt:message key="label.haaspo"/>'
},
{ columnId:"mfgLot",
  columnName:'<fmt:message key="label.lotnumber"/>',
  width: 12
},
{ columnId:"dateOfManufacture",
  columnName:'<fmt:message key="receivedreceipts.label.dom"/>',
  width: 8
},
{ columnId:"dateOfShipment",
  columnName:'<fmt:message key="receivedreceipts.label.manufacturerdos"/>',
  width: 8
},
{ columnId:"dateOfReceipt",
  columnName:'<fmt:message key="receivedreceipts.label.dor"/>',
  width: 8
},
{ columnId:"inventoryGroup"
}
];

var labTestReceiptGridConfig = {
        divName: 'receiptDataDiv',
        beanData: 'receiptJsonMainData',
        beanGrid: 'labTestReceiptGrid',
        config: 'receiptConfig',
        rowSpan: false,
        onRowSelect:receiptOnRowSelect,
        onRightClick:receiptOnRowSelect,
        backgroundRender: true,
        submitDefault: false
};