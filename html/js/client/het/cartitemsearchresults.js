var selectedRowId = null;
var resizeGridWithWindow = true;
var windowCloseOnEsc = true;

function selectRow(rowId, cellInd) {
	selectedRowId = rowId;
	partNo = gridCellValue(mygrid, rowId, "catPartNo");
	containerId = gridCellValue(mygrid, rowId, "containerId");
	parent.$("selectedRow").innerHTML = messagesData.returnItem + " : " + messagesData.partNo + " " + partNo + " - " + containerId;
}

function returnItem() {
	var rowId = mygrid.getRowId(mygrid.haasGetRowSpanStart(selectedRowId));
	var partno = gridCellValue(mygrid, rowId, "catPartNo");
	var itemId = gridCellValue(mygrid, rowId, "itemId");
	var containerId = gridCellValue(mygrid, rowId, "containerId");
	var msds = gridCellValue(mygrid, rowId, "custMsdsNo");
	var desc = gridCellValue(mygrid, rowId, "materialDesc");
	var size = gridCellValue(mygrid, rowId, "containerSize");

	if (parent.window.opener.insertItem(partno, itemId, msds, containerId, desc, size)) {
		var parentIndex = mygrid.haasGetRowSpanStart(selectedRowId);
		if (mygrid._haas_row_span_map[parentIndex] > 1) {
			var max = parentIndex + mygrid._haas_row_span_map[parentIndex] - 1;
			for ( var index = parentIndex + 1; index <= max; index++) {
				rowId = mygrid.getRowId(index);
				partno = gridCellValue(mygrid, rowId, "catPartNo");
				itemId = gridCellValue(mygrid, rowId, "itemId");
				containerId = gridCellValue(mygrid, rowId, "containerId");
				msds = gridCellValue(mygrid, rowId, "custMsdsNo");
				desc = gridCellValue(mygrid, rowId, "materialDesc");
				size = gridCellValue(mygrid, rowId, "containerSize");
				parent.window.opener.insertItemChild(partno, itemId, msds, containerId, desc, size);
			}
		}
	}
	parent.window.close();
}
