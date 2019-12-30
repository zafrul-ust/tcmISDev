(function($G, $) {
	'use strict';
	
	$G.gridRowService = $G.gridRowService || {
	
		// config is an object with properties: checkAll, columnId, selectionCountDisplay
		RowChecker: function(grid, config) {
			if ( ! (grid && config)) {
				throw "Error: Failed to initialize RowChecker. grid or config was undefined";
			}
			var theGrid = grid || null;
			var selected = [];
			var configuration = config || {checkboxType:"hch"};
			var checkAllIndicator = $("#"+configuration.checkAll);
			var checkColumnId = configuration.columnId;
			var selectionCount = $("#"+configuration.selectionCountDisplay);
			var checkUpdateFn;
			
			function init(checker) {
				checkAllIndicator.on("change.gridRowService", function() {
					checker.checkAll(this.id);
				});
				for (var i = 1; i <= theGrid.getRowsNum(); i++) {
					var field = $("#"+checkColumnId+i);
					if (field.length > 0) {
						var checkerFn = function(idx, checkFn) {
							return function() {
								checkFn(idx, this.checked);
							};
						};
						field.on("change.gridRowService", checkerFn(i, checker.checkBox));
					}
				}
				
				checkUpdateFn = configuration.checkboxType == "hchstatus"?$G.updateHchStatusA:$G.updateHchA;
			}
			
			function selectedIndexOf(chkboxId) {
				var idx = -1;
				for (var i = 0; i < selected.length; i++) {
					if (chkboxId == selected[i]) {
						idx = i;
						break;
					}
				}
				return idx;
			}
			
			this.countChecked = function() {
				return selected.length;
			};
			
			this.isChecked = function(rowNum) {
				return 0 <= selectedIndexOf(checkColumnId+rowNum);
			};
			
			this.listCheckedIds = function() {
				return selected;
			};
			
			function alreadyChecked(chkboxId) {
				var checked = false;
				for (var i = 0; i < selected.length; i++) {
					if (chkboxId == selected[i]) {
						checked = true;
					}
				}
				return checked;
			}
			
			function checkIt(rowNumber, checked) {
				var chkboxId = checkColumnId+rowNumber;
				var chkbox = $("#"+chkboxId);
				if (chkbox && ! chkbox.disabled) {
					if (chkbox.prop("checked") != checked) {
						chkbox.prop("checked", checked);
						checkUpdateFn(chkboxId);
					}
					if (checked) {
						if( ! alreadyChecked(chkboxId)) {
							selected.push(chkboxId);
						}
					}
					else {
						var selectIdx = selectedIndexOf(chkboxId);
						if (selectIdx >= 0) {
							selected.splice(selectIdx, 1);
						}
					}
				}
			}
			
			this.checkBox = function(rowNumber, checked) {
				checkIt(rowNumber, checked);
				changeSelectionCount();
			};
			
			function bulkCheck(checked, exceptions) {
				var rowCount = theGrid.getRowsNum();
				checkAllIndicator.prop("checked", checked);
				for (var i = 1; i <= rowCount; i++) {
					if ( ! (exceptions && exceptions[i])) {
						checkIt(i, checked);
					}
				}
				changeSelectionCount();
			}
			
			this.checkAll = function(checkAllBox) {
				var checked = false;
				if (checkAllBox) {
					checked = $("#"+checkAllBox).is(":checked");
				}
				bulkCheck(checked);
			};
			
			this.uncheckAll = function() {
				bulkCheck(false);
			};
			
			function deriveExceptionList(exceptionList) {
				var exceptions = [];
				for (var i = 0; i < exceptionList.length; i++) {
					exceptions[exceptionList[i]] = true;
				}
				return exceptions;
			}
			
			this.checkAllExcept = function() {
				bulkCheck(true, deriveExceptionList(arguments));
			};
			
			this.uncheckAllExcept = function() {
				bulkCheck(false, deriveExceptionList(arguments));
			};
			
			function doCheck(idx, checkFn, check) {
				return function(event) {
					checkFn(idx, true);
				};
			}
			
			this.checkWhenFieldChanges = function(fieldId) {
				if (fieldId) {
					for (var i = 1; i <= theGrid.getRowsNum(); i++) {
						var field = $("#"+fieldId+i);
						if (field.length > 0) {
							field.on("change.gridRowService", doCheck(i, this.checkBox));
						}
					}
				}
			}
			
			function changeSelectionCount() {
				var grabCount = selected.length;
				if (grabCount == 0) {
					selectionCount.hide();
				}
				else {
					selectionCount.show();
					selectionCount.html(grabCount+" item(s) selected");
				}
			}

			init(this);
		}
	};
}(this, jQuery));