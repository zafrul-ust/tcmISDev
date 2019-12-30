function createXSL() {

	$('uAction').value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_Shipment_History_Excel', '650', '600', 'yes');
	document.genericForm.target = '_Shipment_History_Excel';
	var a = window.setTimeout("document.genericForm.submit();", 50);
	return false;
}

// If there are rows extant, setup the rowspans
function resultOnLoad() {
	if ($v("totalLines") > 0) {
		loadRowSpans();
	}
}

// set the grid row's color to even or odd based on the passed counter
function setRowColor(row, rowCntr) {
	beanGrid.rowsAr[row].className = rowCntr % 2 == 0 ? "odd_haas" : "ev_haas";
}

// Set up all of the nested row spans
function loadRowSpans() {
	var rowCntr = 0;
	for ( var i = 0; i < beanGrid.getRowsNum(); i++) {
		try {
			// Outer most layer is based on MR
			if (mrRowspanMap[i] != null) {
				if (mrRowspanMap[i] > 1) {
					beanGrid.setRowspan(i + 1, 1, mrRowspanMap[i]);
					// Cols 1 & 2 are hidden cols
					beanGrid.setRowspan(i + 1, 2, mrRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 5, mrRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 6, mrRowspanMap[i]);
					setRowColor(i + 1, ++rowCntr);
				}
				else if (mrRowspanMap[i] == 1) {
					setRowColor(i + 1, ++rowCntr);
				}
				else {
					setRowColor(i + 1, rowCntr);
				}
			}
			// Second layer is based on Line Item
			if (itemRowspanMap[i] != null) {
				if (itemRowspanMap[i] > 1) {
					beanGrid.setRowspan(i + 1, 7, itemRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 8, itemRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 9, itemRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 10, itemRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 11, itemRowspanMap[i]);
				}
			}
			// Third layer is based on Receipt ID
			if (receiptRowspanMap[i] != null) {
				if (receiptRowspanMap[i] > 1) {
					beanGrid.setRowspan(i + 1, 12, receiptRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 14, receiptRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 15, receiptRowspanMap[i]);
					beanGrid.setRowspan(i + 1, 16, receiptRowspanMap[i]);
				}
			}
		}
		catch (ex) {
		}
	}	
}

function getParentRowId(rowId) {
	var parentRowId = rowId;
	while (mrRowspanMap[parentRowId - 1] == 0) {
		parentRowId--;
	}
	return parentRowId;
}

function deselectRow (parentRowId) {
	var resetClass = "ev_haas";
	if (parentRowId != 1 && beanGrid.rowsAr[parentRowId - 1].className.indexOf("ev") >= 0) {
		resetClass = "odd_haas";
	}
	
	for (i = 0; i < mrRowspanMap[parentRowId - 1];i++) {
		beanGrid.rowsAr[i + parentRowId].className = resetClass;
	}

}

function selectRowspan(parentRowId) {
	for (i = 0; i < mrRowspanMap[parentRowId - 1];i++) {
		beanGrid.rowsAr[i + parentRowId].className = "ev_haas rowselected";
	}
}
