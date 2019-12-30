(function($G, $) {
	'use strict';
	
	var searchFrame = parent.document;
	
	function app() {
		this.selectedRow = 0;
		this.children = [];
		this.beanGrid;
		this.rejectionCommentBox;
		
		this.initializeCatAdds = function(grid) {
			this.beanGrid = grid;
			var that = this;
			$("#okAll").on("change", function() {
				if (this.checked) {
					that.checkAll();
				}
				else {
					that.uncheckAll();
				}
			});
			
			for (var idx = 1; idx <= this.beanGrid.getRowsNum(); idx++) {
				var sdsPresentClickFn = function(i) {
					return function() {
						that.showSdsTable(i);
					};
				};
				$("#sdsPresent"+idx).on("click", sdsPresentClickFn(idx));
			}
			this.checkWhenFieldChanges("materialDesc");
			this.checkWhenFieldChanges("tradeName");
			this.checkWhenFieldChanges("mfgCatalogId");
			this.checkWhenFieldChanges("grade");
			this.checkWhenFieldChanges("dimension");
		};
		
		this.checkAllWithSame = function(id, sourceIdx, checked) {
			var sourceValue = $G.gridCellValue(this.beanGrid, sourceIdx, id);
			var that = this;
			
			for (var idx = 1; idx <= this.beanGrid.getRowsNum(); idx++) {
				var value = $G.gridCellValue(this.beanGrid, idx, id);
				if (value == sourceValue && idx != sourceIdx) {
					this.checkBox(idx, checked);
				}
			}
		};
		
		this.checkWhenFieldChanges = function(id) {
			var that = this;
			for (var i = 1; i <= this.beanGrid.getRowsNum(); i++) {
				var fn = function(idx) {
					return function() {
						that.checkBox(idx, true);
						that.checkAllWithSame("requestId", idx, true);
					};
				};
				$("#"+id+i).on("change", fn(i));
			}
		};
		
		this.selectCatAdd = function(row, colIdx) {
			this.selectedRow = row;
		};
		
		this.approveSingle = function() {
			this.uncheckAll();
			this.checkBox(this.selectedRow, true);
			this.checkAllWithSame("requestId", this.selectedRow, true);
			this.approveSelected();
		};
		
		this.rejectSingle = function() {
			this.uncheckAll();
			this.checkBox(this.selectedRow, true);
			this.checkAllWithSame("requestId", this.selectedRow, true);
			this.rejectSelected();
		};
		
		this.checkBox = function(row, checked) {
			$("#ok"+row).prop("checked", checked);
			$G.updateHchStatusA("ok"+row);
		};
		
		this.checkAll = function() {
			var that = this;
			this.forEachGridRow(function(idx) {
				that.checkBox(idx, true);
			});
		}
		
		this.uncheckAll = function() {
			var that = this;
			this.forEachGridRow(function(idx) {
				that.checkBox(idx, false);
			});
		};
		
		this.checkAllExcept = function(row) {
			var that = this;
			this.forEachGridRow(function(idx) {
				if (idx != row) {
					that.checkBox(idx, true);
				}
			});
		};
		
		this.uncheckAllExcept = function(row) {
			var that = this;
			this.forEachGridRow(function(idx) {
				if (idx != row) {
					that.checkBox(idx, false);
				}
			});
		};
		
		this.closeRejectionCommentBox = function() {
			this.rejectionCommentBox.hide();
		};
		
		this.updateSelected = function() {
			if (this.isFormValid()) {
				this.submitResults("update");
			}
			else {
				alert($G.messagesData.norowselected);
			}
		};
		
		this.approveSelected = function(withholdFromVendor) {
			if (this.isFormValid()) {
				this.submitResults(withholdFromVendor?"approveWithhold":"approve");
			}
			else {
				alert($G.messagesData.norowselected);
			}
		};
		
		this.rejectSelected = function() {
			var validity = this.isRejectFormValid();
			switch(validity) {
			case 0:
				this.submitResults("reject");break;
			case 1:
				this.promptUserForComment();break;
			case 2:
				$G.alert($G.messagesData.norowselected);break;
			}
		};
		
		this.promptUserForComment = function() {
			if ( ! this.rejectionCommentBox) {
				var that = this;
				this.rejectionCommentBox = new $G.popupService.Popup("catalogaddqcresults.do", 
						{"uAction":"promptForComments", "limit":500}, 
						"rejectionCommentBox", 300, 200, true, 100, 100);
				this.rejectionCommentBox.finished(function(data) {
					if (data) {
						$("#rejectionComment").val(data.comment);
						that.rejectSelected();
					}
				});
			}
			this.rejectionCommentBox.show();
		};
		
		this.showSdsTable = function(row) {
			this.selectedRow = row;
			var requestId = $G.gridCellValue(this.beanGrid, this.selectedRow, "requestId");
			var loc = "managecataddsds.do?requestId="+requestId+"&editable=Y";
			var win = $G.openWinGeneric(loc,"sdsTable","500","400","yes","50","50","20","20","no");
			this.children[this.children.length] = win;
		};
		
		this.closeChildren = function() {
			for (var i = 0; i < this.children.length; i++) {
				this.children[i].close();
			}
			if (this.rejectionCommentBox) {
				this.rejectionCommentBox.hide();
			}
		};
		
		this.showError = function() {
			var errorMessagesArea = $("#errorMessagesAreaBody");
			if (errorMessagesArea) {
				errorMessagesArea.style.display="";
			}

			if (dhxWins == null) {
				dhxWins = new dhtmlXWindows();
				dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
			}

			if (!dhxWins.window("errorMessagesArea")) {
				// create window first time
				errorWin = dhxWins.createWindow("errorMessagesArea",0,0, 400, 200);
				errorWin.setText("");
				errorWin.clearIcon();  // hide icon
				errorWin.denyResize(); // deny resizing
				errorWin.denyPark();   // deny parking

				errorWin.attachObject("errorMessagesAreaBody");
				// errorWin.attachEvent("onClose",
				// function(inputWin){inputWin.hide();});
				errorWin.center();
				// setting window position as default x,y position doesn't seem
				// to work, also hidding buttons.
				errorWin.setPosition(328, 131);
				errorWin.button("minmax1").hide();
				errorWin.button("park").hide();
				//errorWin.button("close").hide();
				errorWin.setModal(false);
			}else{
				// just show
				errorWin.setModal(true);
				dhxWins.window("errorMessagesArea").show();
			}
		};
		
		this.isFormValid = function() {
			return this.countChecked() > 0;
		};
		
		this.isRejectFormValid = function() {
			var tableValid = this.isFormValid();
			var commentValid = $("#rejectionComment").val().length > 0;
			
			var validity = tableValid?(commentValid?0:1):2;
			return validity;
		};

		this.submitResults = function(action) {
			$("#uAction").val(action);
			this.redoSearch();
			this.beanGrid.parentFormOnSubmit();
			document.genericForm.submit();
		};
		
		this.redoSearch = function() {
			var now = new Date();
		    $("#startSearchTime", $G.parent.document).val(now.getTime());
			$G.parent.showPleaseWait();
		};
		
		this.countChecked = function() {
			var count = 0;
			this.forEachGridRow(function(row) {
				count += $("#ok"+row+":checked").length;
			});
			return count;
		};
		
		this.forEachGridRow = function(consumer) {
			for (var i = 1; i <= this.beanGrid.getRowsNum(); i++) {
				consumer(i);
			}
		};
		
	};
	

	$G.catAddQc = $G.catAddQc || {
		"app": app
	};

	var catAddQcApp = new $G.catAddQc.app();
	
	$G.update = function() {
	};
	
	$G.closeTransitWin = function() {
	};
	
	$G.checkOk = function(row, colIdx) {
		var selectedOkValue = cellValue(row, "ok");
		catAddQcApp.checkAllWithSame("requestId", row, selectedOkValue == "true");
	};
	
	$G.rightClickRow = function(row, colIdx) {
		catAddQcApp.selectCatAdd(row, colIdx);
		$G.toggleContextMenu('rightClickMenu');
	};
	
	$G.selectTableRow = function(row, colIdx) {
		catAddQcApp.selectCatAdd(row, colIdx);
	};
	
	$G.approveCatAdd = function() {
		catAddQcApp.approveSingle();
	};
	
	$G.rejectCatAdd = function() {
		catAddQcApp.rejectSingle();
	};
	
	$G.initializeCatAdd = function() {
		catAddQcApp.initializeCatAdds($G.beangrid);
	};
	
	$(function() {
		$G.resultOnLoadWithGrid($G.gridConfig);
		$($G.window).on("unload", function() {
			catAddQcApp.closeChildren();
		});
		$("#savePendingAssign > a", $G.parent.document).on("click", function() {
			catAddQcApp.updateSelected();
		});
		$("#approvePendingAssign > a", $G.parent.document).on("click", function() {
			catAddQcApp.approveSelected(false);
		});
		$("#approveAndRetain > a", $G.parent.document).on("click", function() {
			catAddQcApp.approveSelected(true);
		});
		$("#rejectRequest > a", $G.parent.document).on("click", function() {
			catAddQcApp.rejectSelected();
		});
		if ($G.showErrorMessage) {
			catAddQcApp.showError();
		}
	});
	
}(this, jQuery));