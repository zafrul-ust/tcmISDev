var debugOn = false;
var haasGrid = null;
var fontSizeFactor = 12;
var originalColTotalWidth = 0;
var reSizeGridCoLWidthsCount = 0;
// This is to call updatecolumn widths based on if the colums were ever resized. 
var replaceAmp = new RegExp("&(?![A-Z|a-z|#0-9]+;)","g");


if (typeof dhtmlXGridObject != "undefined") {

	dhtmlXGridObject.prototype._haas_bg_render_timer;
	dhtmlXGridObject.prototype._haas_bg_render_enabled = false;
	dhtmlXGridObject.prototype._haas_row_span = false;
	dhtmlXGridObject.prototype._haas_row_span_child_select = false;
	dhtmlXGridObject.prototype._haas_row_span_child_cols;
	dhtmlXGridObject.prototype._haas_row_span_map;
	dhtmlXGridObject.prototype._haas_row_span_set = [];
	dhtmlXGridObject.prototype._haas_row_span_cols;
	dhtmlXGridObject.prototype._haas_row_span_class_map;
	dhtmlXGridObject.prototype._haas_row_span_lvl2 = false;
	dhtmlXGridObject.prototype._haas_row_span_lvl2_child_select = false;
	dhtmlXGridObject.prototype._haas_row_span_lvl2_map = [];
	dhtmlXGridObject.prototype._haas_row_span_lvl2_cols;
	dhtmlXGridObject.prototype._haas_row_span_lvl2_child_cols;
	dhtmlXGridObject.prototype._haas_row_span_lvl3 = false;
	dhtmlXGridObject.prototype._haas_row_span_lvl3_child_select = false;
	dhtmlXGridObject.prototype._haas_row_span_lvl3_map = [];
	dhtmlXGridObject.prototype._haas_row_span_lvl3_cols;
	dhtmlXGridObject.prototype._haas_row_span_lvl3_child_cols;
	dhtmlXGridObject.prototype._haas_debug = false;
	dhtmlXGridObject.prototype._haas_child_select_style = "BACKGROUND-COLOR: #6262ff";
	dhtmlXGridObject.prototype._haas_child_select_style_pattern = /BACKGROUND-COLOR:\s+(\#6262ff|rgb\(98,\s*98,\s*255\))/ig;
	dhtmlXGridObject.prototype._haas_display_width = 0;
	dhtmlXGridObject.prototype._haas_original_col_total_width = 0;
	dhtmlXGridObject.prototype._haas_ok_column = null;

	dhtmlXGridObject.prototype.haasRowIsRendered = function(rowId) {
		var rowIndex = this.getRowIndex(rowId);

		if (this._srnd && rowIndex > 1 && !this.rowsCol[rowIndex + 1]) {
			if (rowId < this.getRowsNum() && !this.rowsAr[rowId]) {
				if (this._haas_debug)
					debug(rowId + " not rendered, rowsCol[" + (rowIndex - 1) + "] == " + this.rowsCol[rowIndex - 1] + ", rowsCol[" + (rowIndex) + "] == " + this.rowsCol[rowIndex] + ", rowsCol[" + (rowIndex + 1) + "] == " + this.rowsCol[rowIndex + 1]
							+ ", this.rowsAr[" + rowId + "] == " + this.rowsAr[rowId]);
				return false;
			}
		}
		return this.doesRowExist(rowId);
	};
	dhtmlXGridObject.prototype.haasRenderRow = function(rowIndex) {
		if (this._haas_debug)
			debug("rendering rowIndex " + rowIndex);
		rowIndex = Math.abs(rowIndex);
		try {
			this._add_from_buffer(rowIndex);
		}
		catch (err) {
			log("Error rendering rowIndex - " + rowIndex);
			log(err);
			// this.rowsCol[rowIndex] = null;
		}
		if (this._tgle) {
			this._updateLine(this._h2.get[this.rowsBuffer[rowIndex].idd], this.rowsBuffer[rowIndex]);
			this._updateParentLine(this._h2.get[this.rowsBuffer[rowIndex].idd], this.rowsBuffer[rowIndex])
		}

		// handle row spanning columns
		if (this._haas_row_span) {
			if (this._haas_debug)
				debug("Handling rowspan for rowIndex == " + rowIndex);
			this.haasSetRowSpan(rowIndex);
			if (this.checkEvent("onAfterHaasRenderRow")) {
				var maxIndex = this.haasGetRowSpanEndIndex(rowIndex);
				for ( var rowSpanIndex = this.haasGetRowSpanStart(rowIndex); rowSpanIndex < maxIndex; rowSpanIndex++) {
					this.callEvent("onAfterHaasRenderRow", [
						this.getRowId(rowSpanIndex)
					]);
				}
			}
			if (this.checkEvent("onAfterHaasRenderRowSpan")) {
				this.callEvent("onAfterHaasRenderRowSpan", [
					this.haasGetRowSpanStart(rowIndex)
				]);
			}
		}
		else {
			this.callEvent("onAfterHaasRenderRow", [
				this.getRowId(rowIndex)
			]);
		}
	};
	dhtmlXGridObject.prototype.haasGetRowClass = function(row, rowId) {
		var rowClass;
		rowClass = row._css;
		if (rowClass == undefined) {
			if (row.className) {
				rowClass = row.className.trim();
			}
			else {
				if (this._haas_row_span) {
					rowClass = this._haas_row_span_class_map[rowId - 1] == this._haas_row_span_class_map[0] ? "ev_haas" : "odd_haas";
				}
				else {
					if (rowId % 2 == 1) {
						rowClass = 'odd_haas';
					}
					else {
						rowClass = 'ev_haas';
					}
				}
			}
		}
		return rowClass;
	};
	dhtmlXGridObject.prototype.haasIsRowSpanSelected = function(rowId, selectedRowId) {
		var startingRowIndex = this.haasGetRowSpanStart(rowId);
		var rowSpanLength = this._haas_row_span_map[startingRowIndex];
		var position = selectedRowId - startingRowIndex;
		// if (this._haas_debug) debug("Rowspan selected for " + rowId + " -> " + position +" >= 1 && " + position + " <= " + rowSpanLength);
		return position >= 1 && position <= rowSpanLength;
	}
	dhtmlXGridObject.prototype.haasSetRowSpan = function(rowId, callback) {
		if (rowId == undefined) {
			if (this._haas_debug)
				debug("RowId == " + rowId);
			return;
		}
		if (this._haas_row_span_set[rowId]) {
			if (this._haas_debug)
				debug("Rowspan already set for rowId " + rowId);
			return;
		}

		var row = this.getRowById(rowId);
		var rowIndex = rowId - 1;
		var rowSpan = this._haas_row_span_map[rowIndex];
		var selectedRowId = this.getSelectedRowId();

		// Can't check for 0/1 as some existing pages use either or,
		// just match to first row which should always be white/ev_haas
		var spanClass = this._haas_row_span_class_map[rowId - 1] == this._haas_row_span_class_map[0] ? "ev_haas" : "odd_haas";
		if (rowId == selectedRowId || (rowSpan != 1 && this.haasIsRowSpanSelected(rowId, selectedRowId))) {
			spanClass += " rowselected";
		}

		if (row) {
			var oldClass = this.haasGetRowClass(row, rowId);
			if (oldClass.search(/grid_/) >= 0) { // Handle nonstandard marking colors
				if (this._haas_debug)
					debug("Non grid color for row, not changing row class");
				spanClass = oldClass;
			}
			else {
				if (this._haas_debug)
					debug("Setting row class for " + rowId + " to " + spanClass);
				row.className = row._css = spanClass;
				if (this.rowsAr[rowId]) {
					this.rowsAr[rowId].className = spanClass;
				}

			}
		}
		if (rowSpan != null && rowSpan > 1) {
			rowSpan = rowSpan * 1;
			var maxSpan = rowId + rowSpan;

			// Render & Set the color class for the entire rowspan
			for ( var index = rowId + 1; index < maxSpan; index++) {
				this._add_from_buffer(index);
				row = this.getRowById(index);
				if (row) {
					if (this._haas_debug)
						debug("Setting row class for child row " + index + " to " + spanClass);
					row.className = row._css = spanClass;
				}
				if (this.rowsAr[index]) {
					this.rowsAr[index].className = spanClass;
				}
				// this._haas_row_span_set[index] = 1;
			}
			// Set the rowspan
			for ( var index = 0; index < this._haas_row_span_cols.length; index++) {
				// if (this._haas_debug) debug("setRowSpan(" + rowId + ", " + this._haas_row_span_cols[index] + ", " + rowSpan + ")");
				try {
					this.setRowspan(rowId, this._haas_row_span_cols[index], rowSpan);
					this._haas_row_span_set[rowId] = 1;
				}
				catch (err) {
					// log("Error setting rowspan for row " + rowId + " err == " + err);
					if (null != callback) {
						window.setTimeout((function(grid, param1, param2, param3) {
							return function() {
								grid.haasSetRowSpan(param1, param2, param3);
							};
						})(this, start, true), 100);
					}
				}
			}
			if (this._haas_row_span_lvl2) {
				// Set the row span on any children
				for ( var index = rowId; index < maxSpan; index++) {
					var rowSpanLvl2 = this._haas_row_span_lvl2_map[index - 1];
					if (rowSpanLvl2 != null && rowSpanLvl2 > 1) {
						for ( var colIndex = 0; colIndex < this._haas_row_span_lvl2_cols.length; colIndex++) {
							try {
								// if (this._haas_debug) debug("setRowSpan(" + index + ", " + this._haas_row_span_lvl2_cols[colIndex] + ", " + rowSpanLvl2 + ")");
								this.setRowspan(index, this._haas_row_span_lvl2_cols[colIndex], rowSpanLvl2);
							}
							catch (err) {
								// log("Error setting lvl2 rowspan for row " + index + " err == " + err);
								if (null != callback) {
									window.setTimeout((function(grid, param1, param2, param3) {
										return function() {
											grid.haasSetRowSpan(param1, param2, param3);
										};
									})(this, start, true), 100);
								}
							}
						}
					}
				}
				if (this._haas_row_span_lvl3) {
					// Set the row span on any children
					for ( var index = rowId; index < maxSpan; index++) {
						var rowSpanlvl3 = this._haas_row_span_lvl3_map[index - 1];
						if (rowSpanlvl3 != null && rowSpanlvl3 > 1) {
							for ( var colIndex = 0; colIndex < this._haas_row_span_lvl3_cols.length; colIndex++) {
								try {
									// if (this._haas_debug) debug("setRowSpan(" + index + ", " + this._haas_row_span_lvl3_cols[colIndex] + ", " + rowSpanlvl3 + ")");
									this.setRowspan(index, this._haas_row_span_lvl3_cols[colIndex], rowSpanlvl3);
								}
								catch (err) {
									// log("Error setting lvl3 rowspan for row " + index + " err == " + err);
									if (null != callback) {
										window.setTimeout((function(grid, param1, param2, param3) {
											return function() {
												grid.haasSetRowSpan(param1, param2, param3);
											};
										})(this, start, true), 100);
									}
								}
							}
						}
					}
				}
			}
		}
		else {
			// This row has been handled, don't need to handle it again until data reloaded
			this._haas_row_span_set[rowId] = 1;
		}
	}
	dhtmlXGridObject.prototype._haas_do_background_render = function() {
		var min = Math.floor(this.objBox.scrollTop / this._srdh);
		var start = min + this._get_view_size();
		start += (this._srnd_pr || 0);

		this._haas_bg_render_timer = window.setTimeout((function(grid, param1) {
			return function() {
				grid._haas_background_render_next_data_chunk(param1);
			};
		})(this, start), 200);
	};
	dhtmlXGridObject.prototype._haas_background_render_next_data_chunk = function(startingPos) {
		var max = startingPos + 50;

		if (max > this.rowsBuffer.length) {
			max = this.rowsBuffer.length;
		}
		for ( var rowCntr = startingPos; rowCntr <= max; rowCntr++) {
			if (!this.rowsCol[rowCntr]) {
				this.haasRenderRow(rowCntr);
			}
			if (!this._haas_bg_render_enabled) {
				// if (this._haas_debug) debug("Background rendering stopped at " + rowCntr);
				break;
			}
		}
		if (max < this.rowsBuffer.length && this._haas_bg_render_enabled) {
			if (this._haas_debug)
				debug("Background render completed through " + max);
			this._haas_bg_render_timer = window.setTimeout((function(grid, param1) {
				return function() {
					grid._haas_background_render_next_data_chunk(param1);
				};
			})(this, max), 500);
		}
		else {
			if (this._haas_debug)
				debug("Background render completed");
			this.callEvent("onAfterHaasGridRendered", []);
		}
	};
	dhtmlXGridObject.prototype.haasDisableBackgroundRender = function() {
		this._haas_bg_render_enabled = false;
		if (this._haas_bg_render_timer) {
			window.clearTimeout(this._haas_bg_render_timer);
		}
	};
	if (typeof dhtmlXGridObject.prototype.parentFormOnSubmit_original != "undefined") {
		dhtmlXGridObject.prototype.parentFormOnSubmit_original = dhtmlXGridObject.prototype.parentFormOnSubmit;
		dhtmlXGridObject.prototype.parentFormOnSubmit = function() {
			this.haasDisableBackgroundRender();
			this.parentFormOnSubmit_original();
		};
	}
	dhtmlXGridObject.prototype.formCreateInputCollection = function () {
		if (this.parentForm == !1)
			return !1;
		
		var useRemoveChild = false;
		if(typeof this.parentForm.removeChild == 'function')
			useRemoveChild = true;

		for (var a = 0; a < this.formInputs.length; a++) {
				if(useRemoveChild)
					this.parentForm.removeChild(this.formInputs[a]);
				else
					this.formInputs[a].parentNode.removeChild(this.formInputs[a]);
		}
		
		this.formInputs = [];
		if (this.FormSubmitSerialization) {
			this.createFormInput("serialized", this.serialize());
		}
		else if (this.FormSubmitOnlySelected) {
			if (this.FormSubmitOnlyRowID) {
				this.createFormInput("selected", this.getSelectedId());
			}
			else {
				for (a = 0; a < this.selectedRows.length; a++) {
					this.createFormInputRow(this.selectedRows[a]);
				}
			}
		}
		else {
			var rowsArray = this.rowsAr;
			var max = this.getRowsNum();
			var funcCreateElement = document.createElement;
			var parentForm = this.parentForm;
			var boxId = (this.globalBox || this.entBox).id;
			var colCount = this._cCount;
			var submitCols = this._submit_cols;
			var inputMask = this._input_mask;
			var formInputs = this.formInputs;

			for (var i = 0; i < max; i++) {
				var rowId = this.getRowId(i);
				if (rowsArray[rowId]) {
					if (!this._haas_ok_column || gridCellValue(this, rowId, this._haas_ok_column) == "true") {
						var row = rowsArray[rowId];
						if (row.childNodes) {
							for (var c = 0; c < colCount; c++) {
								if (!submitCols || submitCols[c]) {
									var a1 = inputMask ? [boxId, row.idd, c] : row.idd + "_" + c
									var input = document.createElement('input');
									input.type = "hidden";
									input.name = inputMask && typeof a1 != "string" ? inputMask.apply(this, a1) : (boxId || "dhtmlXGrid") + "_" + a1;
									var value = this.cells3(row, c).getValue();
									if (value && typeof(value) == 'string') {
										 value = value.replace(/&amp;/g, "&");
									 }
									input.value = value == '&nbsp;' ? '' : value;
									parentForm.appendChild(input);
									formInputs.push(input);
								}
							}
						}
					}
				}
			}

		}
	};


	dhtmlXGridObject.prototype._haas_update_srnd_view = function() {
		if (this._haas_debug)
			debug("_haas_update_srnd_view()");
		var min = Math.floor(this.objBox.scrollTop / this._srdh);
		var max = min + this._get_view_size();
		if (min >= this.rowsBuffer.length) {
			min = this.rowsBuffer.length - this._get_view_size();
		}
		if (min > this._get_view_size()) {
			min -= this._get_view_size();
		}
		if (min <= 0) {
			min = 1;
		}

		max += (this._srnd_pr || 0);
		if (max > this.rowsBuffer.length)
			max = this.rowsBuffer.length;
		for ( var j = min; j <= max; j++) {
			if (!this.rowsCol[j]) {
				this.haasRenderRow(j);
			}
			else {
				this.callEvent("onAfterHaasRenderRow", [
					j
				]);
				if (this._haas_row_span) {
					this.haasSetRowSpan(j);
					this.callEvent("onAfterHaasRenderRowSpan", [
						this.haasGetRowSpanStart(j)
					]);
				}
			}

		}
	};
	dhtmlXGridObject.prototype.render_dataset = function(min, max){ 
		//normal mode - render all
		//var p=this.obj.parentNode;
		//p.removeChild(this.obj,true)
		if (this._srnd){
			if (this._fillers) {
				//return this._update_srnd_view();
				return this._haas_update_srnd_view();
			}
			max=Math.min((this._get_view_size()+(this._srnd_pr||0)), this.rowsBuffer.length);
			
		}
	
		if (this.pagingOn){
			min=Math.max((min||0),(this.currentPage-1)*this.rowsBufferOutSize);
			max=Math.min(this.currentPage*this.rowsBufferOutSize, this.rowsBuffer.length)
		} else {
			min=min||0;
			max=max||this.rowsBuffer.length;
		}
	
		for (var i = min; i < max; i++){
			var r = this.render_row(i)
	
			if (r == -1){
				if (this.xmlFileUrl){
				    if (this.callEvent("onDynXLS",[i,(this._dpref?this._dpref:(max-i))]))
				        this.load(this.xmlFileUrl+getUrlSymbol(this.xmlFileUrl)+"posStart="+i+"&count="+(this._dpref?this._dpref:(max-i)), this._data_type);
				}
				max=i;
				break;
			}
						
			if (!r.parentNode||!r.parentNode.tagName){ 
				this._insertRowAt(r, i);
				if (r._attrs["selected"] || r._attrs["select"]){
					this.selectRow(r,r._attrs["call"]?true:false,true);
					r._attrs["selected"]=r._attrs["select"]=null;
				}
			}
			
							
			if (this._ads_count && i-min==this._ads_count){
				var that=this;
				this._context_parsing=this._context_parsing||this._parsing;
				return this._contextCallTimer=window.setTimeout(function(){
					that._contextCallTimer=null;
					that.render_dataset(i,max);
					if (!that._contextCallTimer){
						if(that._context_parsing)
					    	that.callEvent("onXLE",[])
					    else 
					    	that._fixAlterCss();
					    that.callEvent("onDistributedEnd",[]);
					    that._context_parsing=false;
				    }
				},this._ads_time)
			}
			this.callEvent("onAfterHaasRenderRow", [
				i + 1
			]);
		}
	
		if (this._srnd&&!this._fillers){
			var add_count = this.rowsBuffer.length-max;
			this._fillers = [];

			while (add_count > 0){
				var add_step = (_isIE || window._FFrv)?Math.min(add_count, 50000):add_count;
				var new_filler = this._add_filler(max, add_step);
				if (new_filler)
					this._fillers.push(new_filler);
				add_count -= add_step;
				max += add_step;
			}				
		}
	
		//p.appendChild(this.obj)
		this.setSizes();
	};
	dhtmlXGridObject.prototype.parse = function(data, call, type) {
		if (arguments.length == 2 && typeof call != "function") {
			type = call;
			call = null;
		}
		type = type || "xml";
		this._data_type = type;
		this._haas_json_data = data;
		this._haas_row_span_set = [];
		data = this["_process_" + type](data);
		if (!this._contextCallTimer)
			this.callEvent("onXLE", [
							this, 0, 0, data
			]);
		if (call) {
			call();
		}
		if (this._haas_row_span && this._haas_row_span_map[0] > 1) {
			this.haasSetRowSpan(1);
		}
		this.callEvent("onAfterHaasRenderRowSpan", [
			1
		]);
		this.callEvent("onAfterHaasRenderRow", [
			1
		]);

		// If smartRender && background render is Enabled begin
		// rendering
		if (this._srnd && this._haas_bg_render_enabled) {
			this._haas_do_background_render();
		}
	};
	dhtmlXGridObject.prototype.haasSetRowSpanSelected = function(rowId) {
		var startingRow = this.haasGetRowSpanStart(rowId);
		var rowIndex;
		if (this._haas_debug)
			log("Setting rowspan selected for rowId == " + rowId + ", with starting index = " + startingRow);
		for ( var j = 1; j <= this._haas_row_span_map[startingRow]; j++) {
			rowIndex = startingRow + j;
			if (this.rowsAr[rowIndex]) {
//				if (this.rowsAr[rowIndex].className.search(/grid_/) >= 0) {
//					this.rowsAr[rowIndex].className = this.rowsAr[rowIndex].className.replace(/(grid_\w+)/, "$1Selected");
//				}
//				else {
//					this.rowsAr[rowIndex].className += " rowselected";
					this.rowsAr[rowIndex].className = this.rowsAr[rowIndex].className.replace(/\s*$/, " rowselected");

//				}
				if (this._haas_debug)
					log("Set class for rowIndex " + rowIndex + " to " + this.rowsAr[rowIndex].className);
			}
		}
		if (this._haas_row_span_child_select) {
			if (this.haasIsCellRowSpanChild(this.getSelectedCellIndex())) {
				this.haasSetRowSpanChildStyle(rowId, this._haas_child_select_style);
			}
		}
		else if (this._haas_row_span_lvl2_child_select) {
			var selectedCell = this.getSelectedCellIndex();
			if (this.haasIsCellRowSpanLvl2Child(selectedCell)) {
				this.haasSetRowSpanLvl2ChildStyle(rowId, this._haas_child_select_style);
			}
			else if (this.haasIsCellRowSpanChild(selectedCell)) {
				this.haasSetRowSpanChildStyle(rowId, this._haas_child_select_style);
			}
		}
		else if (this._haas_row_span_lvl3_child_select) {
			var selectedCell = this.getSelectedCellIndex();
			if (this.haasIsCellRowSpanLvl3Child(selectedCell)) {
				this.haasSetRowSpanLvl3ChildStyle(rowId, this._haas_child_select_style);
			}
			else if (this.haasIsCellRowSpanLvl2Child(selectedCell)) {
				this.haasSetRowSpanLvl2ChildStyle(rowId, this._haas_child_select_style);
			}
			else if (this.haasIsCellRowSpanChild(selectedCell)) {
				this.haasSetRowSpanChildStyle(rowId, this._haas_child_select_style);
			}
		}
	};
	dhtmlXGridObject.prototype.haasIsCellRowSpanChild = function(cellIndex) {
		var columns = this.haasGetRowSpanChildCols();
		var numCols = columns.length;
		for ( var colIndex = 0; colIndex < numCols; colIndex++) {
			if (columns[colIndex] == cellIndex) {
				return true;
			}
		}
		return false;
	};
	dhtmlXGridObject.prototype.haasIsCellRowSpanLvl2Child = function(cellIndex) {
		var columns = this.haasGetRowSpanLvl2ChildCols();
		var numCols = columns.length;
		for ( var colIndex = 0; colIndex < numCols; colIndex++) {
			if (columns[colIndex] == cellIndex) {
				return true;
			}
		}
		return false;
	};
	dhtmlXGridObject.prototype.haasIsCellRowSpanLvl3Child = function(cellIndex) {
		var columns = this.haasGetRowSpanLvl3ChildCols();
		var numCols = columns.length;
		for ( var colIndex = 0; colIndex < numCols; colIndex++) {
			if (columns[colIndex] == cellIndex) {
				return true;
			}
		}
		return false;
	};
	dhtmlXGridObject.prototype.haasSetRowClass = function(rowId, rowClass) {
		// var rowIndex = this.getRowIndex(rowId);
		if (this.getSelectedRowId() == rowId) {
//			if (rowClass.search(/grid_/) >= 0) {
//				rowClass = rowClass.replace(/(grid_\w+)/, "$1Selected");
//			}
//			else {
				rowClass = rowClass.replace(/\s*$/, " rowselected");
				//rowClass += " rowselected";
			}
//		}
		this.rowsAr[rowId].className = rowClass;
	};
	dhtmlXGridObject.prototype.haasGetRowSpanChildCols = function() {
		if (!this._haas_row_span_child_cols) {
			this._haas_row_span_child_cols = [];
			var usedCols = [];
			var numCols = this._haas_json_data.rows[0].data.length;
			var index = 0;
			for ( var index = 0; index < this._haas_row_span_cols.length; index++) {
				usedCols[this._haas_row_span_cols[index]] = 1;
			}
			for ( var index = 0; index < numCols; index++) {
				if (usedCols[index] != 1) {
					if ((this._hrrar && this._hrrar[index])) { // ignore hidden cols
						continue;
					}
					this._haas_row_span_child_cols.push(index);
				}
			}
		}
		return this._haas_row_span_child_cols;
	};
	dhtmlXGridObject.prototype.haasGetRowSpanLvl2ChildCols = function() {
		if (!this._haas_row_span_lvl2_child_cols) {
			this._haas_row_span_lvl2_child_cols = [];
			var usedCols = [];
			var numCols = this._haas_json_data.rows[0].data.length;
			var index = 0;
			for ( var index = 0; index < this._haas_row_span_cols.length; index++) {
				usedCols[this._haas_row_span_cols[index]] = 1;
			}
			if (this._haas_row_span_lvl2_cols) {
				for ( var index = 0; index < this._haas_row_span_lvl2_cols.length; index++) {
					usedCols[this._haas_row_span_lvl2_cols[index]] = 1;
				}
			}
			for ( var index = 0; index < numCols; index++) {
				if (usedCols[index] != 1) {
					if ((this._hrrar && this._hrrar[index])) { // ignore hidden cols
						continue;
					}
					this._haas_row_span_lvl2_child_cols.push(index);
				}
			}
		}
		return this._haas_row_span_lvl2_child_cols;
	};
	dhtmlXGridObject.prototype.haasGetRowSpanLvl3ChildCols = function() {
		if (!this._haas_row_span_lvl3_child_cols) {
			this._haas_row_span_lvl3_child_cols = [];
			var usedCols = [];
			var numCols = this._haas_json_data.rows[0].data.length;
			var index = 0;
			for ( var index = 0; index < this._haas_row_span_cols.length; index++) {
				usedCols[this._haas_row_span_cols[index]] = 1;
			}
			if (this._haas_row_span_lvl2_cols) {
				for ( var index = 0; index < this._haas_row_span_lvl2_cols.length; index++) {
					usedCols[this._haas_row_span_lvl2_cols[index]] = 1;
				}
				if (this._haas_row_span_lvl3_cols) {
					for ( var index = 0; index < this._haas_row_span_lvl3_cols.length; index++) {
						usedCols[this._haas_row_span_lvl3_cols[index]] = 1;
					}
				}
			}
			for ( var index = 0; index < numCols; index++) {
				if (usedCols[index] != 1) {
					if ((this._hrrar && this._hrrar[index])) { // ignore hidden cols
						continue;
					}
					this._haas_row_span_lvl3_child_cols.push(index);
				}
			}
		}
		return this._haas_row_span_lvl3_child_cols;
	};
	dhtmlXGridObject.prototype.haasRemoveRowSpanChildStyle = function(rowId) {
		var columns = this.haasGetRowSpanChildCols();
		var numCols = columns.length;

		var startingChildIndex = this._haas_row_span_lvl2 ? this.haasGetRowSpanStartLvl2(rowId) : rowId - 1;
		var endingChildIndex = this._haas_row_span_lvl2 ? this.haasGetRowSpanEndIndexLvl2(rowId) : rowId;
		for ( var rowIndex = startingChildIndex; rowIndex < endingChildIndex; rowIndex++) {
			var curRowId = rowIndex + 1;
			var r = this.getRowById(curRowId);
			if (!r) {
				continue;
			}
			for ( var colIndex = 0; colIndex < numCols; colIndex++) {
				var cell = r.childNodes[r._childIndexes ? r._childIndexes[colIndex] : colIndex];
				if (cell.style.cssText.length == 0 || cell.style.cssText.search(/display:\s+none/i) == 0 || cell.style.cssText.search(this._haas_child_select_style_pattern) == 0) {
					this.setCellTextStyle(curRowId, columns[colIndex], "");
				}
				else {
					this.setCellTextStyle(curRowId, columns[colIndex], cell.style.cssText.replace(this._haas_child_select_style_pattern, ""));
				}
			}
		}
	};
	dhtmlXGridObject.prototype.haasRemoveRowSpanLvl2ChildStyle = function(rowId) {
		var columns = this.haasGetRowSpanLvl2ChildCols();
		var numCols = columns.length;
		var startingChildIndex = this._haas_row_span_lvl3 ? this.haasGetRowSpanStartLvl3(rowId) : rowId - 1;
		var endingChildIndex = this._haas_row_span_lvl3 ? this.haasGetRowSpanEndIndexLvl3(rowId) : rowId;
		for ( var rowIndex = startingChildIndex; rowIndex < endingChildIndex; rowIndex++) {
			var curRowId = rowIndex + 1;
			var r = this.getRowById(curRowId);
			if (!r) {
				continue;
			}
			for ( var colIndex = 0; colIndex < numCols; colIndex++) {
				var cell = r.childNodes[r._childIndexes ? r._childIndexes[colIndex] : colIndex];
				if (cell.style.cssText.length == 0 || cell.style.cssText.search(/display:\s+none/i) == 0 || cell.style.cssText.search(this._haas_child_select_style_pattern) == 0) {
					this.setCellTextStyle(curRowId, columns[colIndex], "");
				}
				else {
					this.setCellTextStyle(curRowId, columns[colIndex], cell.style.cssText.replace(this._haas_child_select_style_pattern, ""));
				}
			}
		}
	};
	dhtmlXGridObject.prototype.haasRemoveRowSpanLvl3ChildStyle = function(rowId) {
		var columns = this.haasGetRowSpanLvl3ChildCols();
		var numCols = columns.length;
		var r = this.getRowById(rowId)
		if (!r) {
			return;
		}
		for ( var colIndex = 0; colIndex < numCols; colIndex++) {
			var cell = r.childNodes[r._childIndexes ? r._childIndexes[colIndex] : colIndex];
			if (cell.style.cssText.length == 0 || cell.style.cssText.search(/display:\s+none/i) >= 0) {
				this.setCellTextStyle(rowId, columns[colIndex], "");
			}
			else {
				this.setCellTextStyle(rowId, columns[colIndex], cell.style.cssText.replace(this._haas_child_select_style_pattern, ""));
			}

		}
	};
	dhtmlXGridObject.prototype.haasSetRowSpanChildStyle = function(rowId) {
		var columns = this.haasGetRowSpanChildCols();
		var numCols = columns.length;

		var startingChildIndex = this._haas_row_span_lvl2 ? this.haasGetRowSpanStartLvl2(rowId) : rowId - 1;
		var endingChildIndex = this._haas_row_span_lvl2 ? this.haasGetRowSpanEndIndexLvl2(rowId) : rowId;
		for ( var rowIndex = startingChildIndex; rowIndex < endingChildIndex; rowIndex++) {
			var r = this.getRowById(rowIndex + 1);
			if (!r) {
				continue;
			}
			for ( var colIndex = 0; colIndex < numCols; colIndex++) {
				var cell = r.childNodes[r._childIndexes ? r._childIndexes[colIndex] : colIndex];
				if (cell.style.cssText.length == 0 || cell.style.cssText.search(/display:\s+none/i) >= 0) {
					this.setCellTextStyle(rowIndex + 1, columns[colIndex], this._haas_child_select_style);
				}
				else {
					this.setCellTextStyle(rowIndex + 1, columns[colIndex], this._haas_child_select_style + ";" + cell.style.cssText);
				}
			}
		}
	};
	dhtmlXGridObject.prototype.haasSetRowSpanLvl2ChildStyle = function(rowId) {
		var columns = this.haasGetRowSpanLvl2ChildCols();
		var numCols = columns.length;
		var startingChildIndex = this._haas_row_span_lvl3 ? this.haasGetRowSpanStartLvl3(rowId) : rowId - 1;
		var endingChildIndex = this._haas_row_span_lvl3 ? this.haasGetRowSpanEndIndexLvl3(rowId) : rowId;
		for ( var rowIndex = startingChildIndex; rowIndex < endingChildIndex; rowIndex++) {
			var r = this.getRowById(rowIndex + 1);
			if (!r) {
				continue;
			}
			for ( var colIndex = 0; colIndex < numCols; colIndex++) {
				var cell = r.childNodes[r._childIndexes ? r._childIndexes[colIndex] : colIndex];
				if (cell.style.cssText.length == 0 || cell.style.cssText.search(/display:\s+none/i) >= 0) {
					this.setCellTextStyle(rowIndex + 1, columns[colIndex], this._haas_child_select_style);
				}
				else {
					this.setCellTextStyle(rowIndex + 1, columns[colIndex], this._haas_child_select_style + ";" + cell.style.cssText);
				}
			}
		}
	};
	dhtmlXGridObject.prototype.haasSetRowSpanLvl3ChildStyle = function(rowId) {
		var columns = this.haasGetRowSpanLvl3ChildCols();
		var numCols = columns.length;
		var r = this.getRowById(rowId)
		if (!r) {
			return;
		}
		for ( var colIndex = 0; colIndex < numCols; colIndex++) {
			var cell = r.childNodes[r._childIndexes ? r._childIndexes[colIndex] : colIndex];
			if (cell.style.cssText.length == 0 || cell.style.cssText.search(/display:\s+none/i) >= 0) {
				this.setCellTextStyle(rowId, columns[colIndex], this._haas_child_select_style);
			}
			else {
				this.setCellTextStyle(rowId, columns[colIndex], this._haas_child_select_style + ";" + cell.style.cssText);
			}
		}
	};
	dhtmlXGridObject.prototype.haasSetColSpanStyle = function(rowId, startColIndex, endColIndex, style) {
		var rowIndex = this.getRowIndex(rowId);
		for ( var column = startColIndex; column <= endColIndex; column++) {
			this.setCellTextStyle(rowIndex + 1, column, style);
		}
	};
	dhtmlXGridObject.prototype.haasClearRowSpanSelected = function(rowId) {
		var startingRow = this.haasGetRowSpanStart(rowId);
//		var selectPattern1 = /Selected/g;
		var selectPattern2 = /rowselected/g;
		var numRows = this._haas_row_span_map[startingRow];

		for ( var j = 1; j <= numRows; j++) {
			var rowIndex = startingRow + j;
			var theRow = this.rowsAr[rowIndex];
//			if (theRow.className.search(/grid_/) >= 0) {
//				theRow.className = theRow.className.replace(selectPattern1, "");
//			}
//			else {
				theRow.className = theRow.className.replace(selectPattern2, "");
//			}
		}
		if (this._haas_row_span_child_select) {
			this.haasRemoveRowSpanChildStyle(rowId);
		}
		else if (this._haas_row_span_lvl2_child_select) {
			this.haasRemoveRowSpanLvl2ChildStyle(rowId);
			this.haasRemoveRowSpanChildStyle(rowId);
		}
		else if (this._haas_row_span_lvl3_child_select) {
			this.haasRemoveRowSpanLvl3ChildStyle(rowId);
			this.haasRemoveRowSpanLvl2ChildStyle(rowId);
			this.haasRemoveRowSpanChildStyle(rowId);
		}
		this.clearSelection();

	};
	dhtmlXGridObject.prototype.haasGetRowSpanStart = function(rowId) {
		var startingRow = rowId - 1;
		while (startingRow > 0) {
			if (this._haas_row_span_map[startingRow] >= 1) {
				return startingRow;
			}
			startingRow--;
		}
		return startingRow;
	};
	dhtmlXGridObject.prototype.haasGetRowSpanStartLvl2 = function(rowId) {
		var higherLevelStart = this.haasGetRowSpanStart(rowId);
		var startingRow = rowId - 1;
		while (startingRow > higherLevelStart) {
			if (this._haas_row_span_lvl2_map[startingRow] >= 1) {
				return startingRow;
			}
			startingRow--;
		}
		return startingRow;
	};
	dhtmlXGridObject.prototype.haasGetRowSpanStartLvl3 = function(rowId) {
		var higherLevelStart = this.haasGetRowSpanStartLvl2(rowId);
		var startingRow = rowId - 1;
		while (startingRow > higherLevelStart) {
			if (this._haas_row_span_lvl3_map[startingRow] >= 1) {
				return startingRow;
			}
			startingRow--;
		}
		return startingRow;
	};
	dhtmlXGridObject.prototype.haasGetRowSpanEndIndex = function(rowId) {
		var startingRow = this.haasGetRowSpanStart(rowId);
		return startingRow + this._haas_row_span_map[startingRow];
	};
	dhtmlXGridObject.prototype.haasGetRowSpanEndIndexLvl2 = function(rowId) {
		var startingRow = this.haasGetRowSpanStartLvl2(rowId);
		return startingRow + this._haas_row_span_lvl2_map[startingRow];
	};
	dhtmlXGridObject.prototype.haasGetRowSpanEndIndexLvl3 = function(rowId) {
		var startingRow = this.haasGetRowSpanStartLvl3(rowId);
		return startingRow + this._haas_row_span_lvl3_map[startingRow];
	};
	dhtmlXGridObject.prototype.haasSyncJsonDataWithScreen = function(rowId) {
		var rowIndex = this.getRowIndex(rowId);
		var numCols = this._haas_json_data.rows[0].data.length;
		for ( var colIndex = 0; colIndex < numCols; colIndex++) {
			var theCell = this.cells(rowId, colIndex);
			if (theCell.isCheckbox()) {
				this._haas_json_data.rows[rowIndex].data[colIndex] = theCell.isChecked();
			}
			else {
				this._haas_json_data.rows[rowIndex].data[colIndex] = theCell.getValue();
			}
		}
	};
	dhtmlXGridObject.prototype.haasAddRowToRowSpan = function(newRowData, insertionIndex, parentIndex, lvl2ParentIndex, lvl3ParentIndex) {
		if (this._haas_debug)
			debug("haasAddRowToRowSpan(data," + insertionIndex + "," + parentIndex + "," + lvl2ParentIndex + "," + lvl3ParentIndex + ")");
		var index;

		if (!this._haas_json_data.rows.length) {
			this._haas_json_data.rows[0] = {
				id : this._haas_json_data.rows.length + 1,
				data : newRowData
			};
			this._haas_row_span_map[0] = 1;
			if (this._haas_row_span_lvl2) {
				this._haas_row_span_lvl2_map[0] = 1;
				if (this._haas_row_span_lvl3) {
					this._haas_row_span_lvl3_map[0] = 1;
				}
			}
		}
		else {
			// First sync up any data changes with the screen
			for ( var rowId = 1; rowId <= this._haas_json_data.rows.length; rowId++) {
				if (this.haasRowIsRendered(rowId)) {
					this.haasSyncJsonDataWithScreen(rowId);
				}
			}

			// Insert a blank row at the end of the dataset
			this._haas_json_data.rows[this._haas_json_data.rows.length] = {
				id : this._haas_json_data.rows.length + 1,
				data : []
			};

			// Copy all the row DATA down one from the insertion
			// point
			for (index = this._haas_json_data.rows.length - 1; index >= insertionIndex; index--) {
				try {
					this._haas_json_data.rows[index].data = this._haas_json_data.rows[index - 1].data;
					this._haas_json_data.rows[index]['class'] = this._haas_json_data.rows[index - 1]['class'];
					this._haas_row_span_map[index] = this._haas_row_span_map[index - 1];
					if (this._haas_row_span_lvl2) {
						this._haas_row_span_lvl2_map[index] = this._haas_row_span_lvl2_map[index - 1];
						if (this._haas_row_span_lvl3) {
							this._haas_row_span_lvl3_map[index] = this._haas_row_span_lvl3_map[index - 1];
						}
					}

					if (parentIndex != undefined) {
						this._haas_row_span_class_map[index] = this._haas_row_span_class_map[index - 1];
					}
					else {
						this._haas_row_span_class_map[index] = this._haas_row_span_class_map[index - 1] == 0 ? 1 : 0;
					}
				}
				catch (ex) {
				}
			}

			// Set the insertion row data to the new data
			this._haas_json_data.rows[insertionIndex].data = newRowData;

			if (parentIndex != undefined) {
				this._haas_row_span_map[parentIndex] = this._haas_row_span_map[parentIndex] + 1;
				this._haas_row_span_map[insertionIndex] = 0;
				if (this._haas_row_span_lvl2) {
					if (lvl2ParentIndex != undefined) {
						this._haas_row_span_lvl2_map[lvl2ParentIndex] = this._haas_row_span_lvl2_map[lvl2ParentIndex] + 1;
						this._haas_row_span_lvl2_map[insertionIndex] = 0;
					}
					else {
						this._haas_row_span_lvl2_map[insertionIndex] = 1;
					}
					if (this._haas_row_span_lvl3) {
						if (lvl3ParentIndex != undefined) {
							this._haas_row_span_lvl3_map[lvl3ParentIndex] = this._haas_row_span_lvl3_map[lvl3ParentIndex] + 1;
							this._haas_row_span_lvl3_map[insertionIndex] = 0;
						}
						else {
							this._haas_row_span_lvl3_map[insertionIndex] = 1;
						}
					}
				}
			}
			else {
				this._haas_row_span_map[insertionIndex] = 1;
				if (this._haas_row_span_lvl2) {
					this._haas_row_span_lvl2_map[insertionIndex] = 1;
					if (this._haas_row_span_lvl3) {
						this._haas_row_span_lvl3_map[insertionIndex] = 1;
					}
				}
			}
		}
		this.clearAll();
		this.parse(this._haas_json_data, "json");
	};
	dhtmlXGridObject.prototype.haasDeleteRowFromRowSpan = function(rowId) {
		var deletionIndex = this.getRowIndex(rowId);
		if (this._haas_debug) {
			debug("haasDeleteRowFromRowSpan(" + rowId + ")");
		}

		var index;

		// First sync up any data changes with the screen
		for ( var rowId2 = 1; rowId2 <= this._haas_json_data.rows.length; rowId2++) {
			if (this.haasRowIsRendered(rowId2)) {
				this.haasSyncJsonDataWithScreen(rowId2);
			}
		}
		//
		var rowspanStartIndex = this.haasGetRowSpanStart(rowId);
		var rowspan = this._haas_row_span_map[rowspanStartIndex];

		if (this._haas_row_span_lvl2) {
			var rowspanStartIndex2 = this.haasGetRowSpanStartLvl2(rowId);
			var rowspan2 = this._haas_row_span_lvl2_map[rowspanStartIndex2];
			if (this._haas_row_span_lvl3) {
				var rowspanStartIndex3 = this.haasGetRowSpanStartLvl3(rowId);
				var rowspan3 = this._haas_row_span_lvl3_map[rowspanStartIndex3];
			}
		}

		// Copy all the row DATA up one from the deletion
		// point
		for (index = deletionIndex; index < this._haas_json_data.rows.length - 1; index++) {
			this._haas_json_data.rows[index].data = this._haas_json_data.rows[index + 1].data;
			this._haas_json_data.rows[index]['class'] = this._haas_json_data.rows[index + 1]['class'];
			this._haas_row_span_map[index] = this._haas_row_span_map[index + 1];
			if (this._haas_row_span_lvl2) {
				this._haas_row_span_lvl2_map[index] = this._haas_row_span_lvl2_map[index + 1];
				if (this._haas_row_span_lvl3) {
					this._haas_row_span_lvl3_map[index] = this._haas_row_span_lvl3_map[index + 1];
				}
			}
		}
		if (rowspan > 1) {
			this._haas_row_span_map[rowspanStartIndex] = rowspan - 1;
		}
		if (this._haas_row_span_lvl2 && rowspan2 > 1) {
			this._haas_row_span_lvl2_map[rowspanStartIndex2] = rowspan2 - 1;
		}
		if (this._haas_row_span_lvl3 && rowspan3 > 1) {
			this._haas_row_span_lvl3_map[rowspanStartIndex3] = rowspan3 - 1;
		}
		// Remove the now duplicated last row of data
		this._haas_json_data.rows.splice(this._haas_json_data.rows.length - 1, 1);
		this.clearAll();
		this.parse(this._haas_json_data, "json");
	};
	dhtmlXGridObject.prototype.doClick = function(el, fl, selMethod, show){
//		if (!this.selMultiRows) selMethod=0; //block programmatical multiselecton if mode not enabled explitly
		selMethod=0; //block programmatical multiselecton if mode not enabled explitly
		var psid = this.row ? this.row.idd : 0;

		this.setActive(true);

		if (!selMethod)
			selMethod=0;

//		if (this.cell != null)
//			this.cell.className=this.cell.className.replace(/cellselected/g, "");

		if (el.tagName == "TD"){
			if (this.checkEvent("onSelectStateChanged"))
				var initial = this.getSelectedId();
			var prow = this.row;
		if (selMethod == 1){
				var elRowIndex = this.rowsCol._dhx_find(el.parentNode)
				var lcRowIndex = this.rowsCol._dhx_find(this.lastClicked)

				if (elRowIndex > lcRowIndex){
					var strt = lcRowIndex;
					var end = elRowIndex;
				} else {
					var strt = elRowIndex;
					var end = lcRowIndex;
				}

				for (var i = 0; i < this.rowsCol.length; i++)
					if ((i >= strt&&i <= end)){
						if (this.rowsCol[i]&&(!this.rowsCol[i]._sRow)){
							if (this.rowsCol[i].className.indexOf("rowselected")
								== -1&&this.callEvent("onBeforeSelect", [
								this.rowsCol[i].idd,
								psid
							])){
								this.rowsCol[i].className+=" rowselected";
								this.selectedRows[this.selectedRows.length]=this.rowsCol[i]
							}
						} else {
							this.clearSelection();
							return this.doClick(el, fl, 0, show);
						}
					}
			} else if (selMethod == 2){
				if (el.parentNode.className.indexOf("rowselected") != -1){
					el.parentNode.className=el.parentNode.className.replace(/rowselected/g, "");
					this.selectedRows._dhx_removeAt(this.selectedRows._dhx_find(el.parentNode))
					var skipRowSelection = true;
					show = false;
				}
			}
			this.editStop()
			if (typeof (el.parentNode.idd) == "undefined")
				return true;

			if ((!skipRowSelection)&&(!el.parentNode._sRow)){
				if (this.callEvent("onBeforeSelect", [
					el.parentNode.idd,
					psid
				])){
					if (this.getSelectedRowId() != el.parentNode.idd){
						if (selMethod == 0) {
							if (this._haas_row_span && prow != null) {
								this.haasClearRowSpanSelected(prow.idd);
							}
							else {
								this.clearSelection();
							}
						}
						this.cell=el;
						if ((prow == el.parentNode)&&(this._chRRS))
							fl=false;
						this.row=el.parentNode;
						if (this._haas_row_span) {
							if (this.row.idd > 5) {
								this._haas_background_render_next_data_chunk(this.row.idd - 5);
							}
							this.haasSetRowSpanSelected(this.row.idd);
						}
						else {
//							if (this.row.className.search(/grid_/) >= 0) {
//								this.row.className = this.row.className.replace(/(grid_\w+)/, "$1Selected");
//							}
//							else {
								this.row.className += " rowselected";
//							}
						}
						
						if (this.selectedRows._dhx_find(this.row) == -1)
							this.selectedRows[this.selectedRows.length]=this.row;
						this.selectedRows[0] = this.row;
					} else {
						this.cell=el;
						this.row = el.parentNode;
					}
				} else fl = false;
			} 

//			if (this.cell && this.cell.parentNode.className.indexOf("rowselected") != -1)
//				this.cell.className=this.cell.className.replace(/cellselected/g, "")+" cellselected";
			
			if (selMethod != 1)
				if (!this.row)
					return;
			this.lastClicked=el.parentNode;

			var rid = this.row.idd;
			var cid = this.cell;

			if (fl&& typeof (rid) != "undefined" && cid && !skipRowSelection) {
//				self.onRowSelectTime=setTimeout(function(){
//					if (self.callEvent)
//						self.callEvent("onRowSelect", [
//							rid,
//							cid._cellIndex
//						]);
//				}, 100);
				this.onRowSelectTime=window.setTimeout( ( function ( obj, param1, param2 ) { return function () { obj.callEvent( param1, param2 ); }; } )( this, "onRowSelect",  [rid, cid._cellIndex]), 50 );

			} else this.callEvent("onRowSelectRSOnly",[rid]);

			if (this.checkEvent("onSelectStateChanged")){
				var afinal = this.getSelectedId();

				if (initial != afinal)
					this.callEvent("onSelectStateChanged", [afinal,initial]);
			}
		}
		this.isActive=true;
		if (show !== false && this.cell && this.cell.parentNode.idd)
			this.moveToVisible(this.cell)
	};
	dhtmlXGridCellObject.prototype.setCValue=function(val, val2){
		if(typeof(val) == 'string' && val.indexOf("&") != -1)
			this.cell.innerHTML=val.replace(replaceAmp, "&amp;");
		else
			this.cell.innerHTML=val;

		this.grid.callEvent("onCellChanged", [
			this.cell.parentNode.idd,
			this.cell._cellIndex,
			(arguments.length > 1 ? val2 : val)
		]);
	};
	dhtmlXGridObject.prototype.cells3=function(row, col){
		var cell = null;
		if (row['_childIndexes'] == null) {
			cell = row.childNodes[col];
		}
		else {
			cell = row.childNodes[row._childIndexes[col]];
		}
		if (!cell || cell._cellIndex!=col){
			return this.cells3(row.previousSibling,col);
		}
		return this.cells4(cell);
	};
	dhtmlXGridObject.prototype.cells=function(row_id, col){
		var c = this.getRowById(row_id);
		return this.cells3(c,col);
	};
}

/* Custom sorting */
/* This custom sorting function implements case insensitive sorting. */
function haasStr(a, b, order) { // the name of the function must be > than 5
	// chars
	return (a.toLowerCase() > b.toLowerCase() ? 1 : -1) * (order == "asc" ? 1 : -1);
}

/*
 * This custom sorting function implements column sorting for the column type hcoro.
 */
var haasCoroColIndex = -1;
var haasCoroOptionArray = null;
function haasCoro(a, b, order, aRowId, bRowId, colIndex) {
	var optTexta = a;
	var optTextb = b;
	try {
		if ((haasCoroColIndex != colIndex)) {
			haasCoroOptionArray = null;
			var columnId = haasGrid.getColumnId(colIndex);
			haasCoroOptionArray = eval(columnId);
			haasCoroColIndex = colIndex;
		}
		for ( var i = 0; i < haasCoroOptionArray.length; i++) {
			if (a == haasCoroOptionArray[i]["value"]) {
				optTexta = haasCoroOptionArray[i]["text"];
				break;
			}
		}
		for ( var j = 0; j < haasCoroOptionArray.length; j++) {
			if (b == haasCoroOptionArray[j]["value"]) {
				optTextb = haasCoroOptionArray[j]["text"];
				break;
			}
		}
	}
	catch (err) {
	}
	;
	return (optTexta.toLowerCase() > optTextb.toLowerCase() ? 1 : -1) * (order == "asc" ? 1 : -1);
}

/*
 * This custom sorting function implements column sorting for the column type hch, hchstatus.
 */
function haasHch(a, b, order) { // the name of the function must be > than 5
	// chars
	return ((b + "") > (a + "") ? 1 : -1) * (order == "asc" ? 1 : -1);
}

function setHaasGrid(tmpGrid) {
	// This will eliminate the 'white space' gaps when vertically scrolling
	// in Firefox and IE using the scroll arrows, and not the scroll slider.
	// In Firefox, when using the scroll slider or page down key, no 'white
	// space'
	// gaps are shown when the slider or page down stops. However, this
	// still
	// does not work in IE.
	// This is from DHTMLX Support Team
	if (_isFF) {
		tmpGrid.doOnScroll = function(e, mode) {
			var self = tmpGrid;
			self.hdrBox.scrollLeft = self.objBox.scrollLeft;
			if (self.ftr)
				self.ftr.parentNode.scrollLeft = self.objBox.scrollLeft;

			if (mode)
				return;
			if (self._srnd) {
				self._haas_update_srnd_view();
			}
		}
	}
	else if (_isIE) {
		tmpGrid.doOnScroll = function(e, mode) {
			var self = tmpGrid;
			self.hdrBox.scrollLeft = self.objBox.scrollLeft;
			if (self.ftr)
				self.ftr.parentNode.scrollLeft = self.objBox.scrollLeft;
			if (mode)
				return;
			if (self._srnd) {
				if (self._dLoadTimer)
					window.clearTimeout(self._dLoadTimer);
				if (self._haas_update_srnd_view) {
					self._dLoadTimer = window.setTimeout((function(grid) {
						return function() {
							grid._haas_update_srnd_view();
						};
					})(self), 10);
				}
				else {
					self._dLoadTimer = window.setTimeout((function(grid) {
						return function() {
							grid._update_srnd_view();
						};
					})(self), 10);
				}
			}
		}
	}
	// If smartRender is Enabled setup the rowHeight
	if (tmpGrid._srnd) {
		// Set the row height first
		if (!fontSizePref) {
			fontSizeFactor = 12;
		}
		else {
			switch (fontSizePref) {
				case "Smallest":
					fontSizeFactor = 8;
					break;
				case "Small":
					fontSizeFactor = 10;
					break;
				case "Medium":
					fontSizeFactor = 12;
					break;
				case "Large":
					fontSizeFactor = 14;
					break;
				case "Largest":
					fontSizeFactor = 16;
					break;
				default:
					fontSizeFactor = 12;
			}
		}
		tmpGrid.setAwaitedRowHeight(fontSizeFactor * 2);
	}
	haasGrid = tmpGrid;
}

function stopBackgroundRender() {
	gridStopBackgroundRender(haasGrid);
}

function gridStopBackgroundRender(theGrid) {
	if (theGrid._haas_bg_render_enabled) {
		theGrid.haasDisableBackgroundRender();
	}
}

function renderAllRows() {
	gridRenderAllRows(haasGrid);
}

function gridRenderAllRows(theGrid) {
	gridStopBackgroundRender(theGrid);
	var max = theGrid.getRowsNum() * 1;
	for ( var rowId = 1; rowId <= max; rowId++) {
		if (!theGrid.haasRowIsRendered(rowId)) {
			theGrid.haasRenderRow(rowId);
		}
	}
}

function cell(rowid, columnId) {
	return gridCell(haasGrid, rowid, columnId);
}

function cellValue(rowid, columnId) {
	return gridCellValue(haasGrid, rowid, columnId);
}

function cellJsonValue(rowid, columnId) {
	return gridCellJsonValue(haasGrid, rowid, columnId);
}

function gridCell(theGrid, rowid, columnId) {
	return theGrid.cells(rowid, typeof columnId == "number" ? columnId : theGrid.getColIndexById(columnId));
}

function setCellValue(rowid, columnId, value) {
	setGridCellValue(haasGrid, rowid, columnId, value);
}

function setGridCellValue(theGrid, rowid, columnId, value) {
	if (theGrid.haasRowIsRendered(rowid)) {
		theGrid.cells(rowid, typeof columnId == "number" ? columnId : theGrid.getColIndexById(columnId)).setValue(value);
	}
	else {
		theGrid._haas_json_data.rows[rowid - 1].data[typeof columnId == "number" ? columnId : theGrid.getColIndexById(columnId)] = value;
	}
}

function gridCellValue(theGrid, rowid, columnId) {
	if (theGrid.haasRowIsRendered(rowid)) {
		var cell = gridCell(theGrid, rowid, columnId);
		if (cell != null) {
			return htmlDencodeGrid(cell.getValue());
		}
	}
	// OK, either the row isn't rendered or the cell wasn't found
	return gridCellJsonValue(theGrid, rowid, columnId);
}

function gridCellJsonValue(theGrid, rowid, columnId) {
	return theGrid._haas_json_data.rows[rowid - 1].data[typeof columnId == "number" ? columnId : theGrid.getColIndexById(columnId)];
}

function _onBeforeSorting(ind, type, direction) {
	var columnId = this.getColumnId(ind);
	var colIndex;

	var hiddenColumnName = null;
	if (this.hiddenSortingArray && (hiddenColumnName = this.hiddenSortingArray[columnId])) {
		colIndex = this.getColIndexById(hiddenColumnName);
	}
	else
		return true; // Do not block normal sorting

	this.sortRows(colIndex, type, direction); // sort grid by the
	// column with prepared
	// values
	this.setSortImgState(true, ind, direction); // set a correct sorting
	// image
	return false; // block default sorting
}
function _setPermColumn(id, permColumn) {
	// if ((typeof multiplePermissions != 'undefined') && (typeof
	// permissionColumns != 'undefined')) {
	if (typeof (window['multiplePermissions']) == 'undefined')
		window['multiplePermissions'] = true;
	if (typeof (window['permissionColumns']) == 'undefined')
		window['permissionColumns'] = new Array();
	window['permissionColumns'][id] = permColumn;
}

function gridfalse(a) {
	return !a || (a == 'N') || (a == 'na') || (a == 'false') || (a == 'no');
}

function initGridWithConfig(inputGrid, config, rowSpan, submitDefault, singleClickEdit) {
	/*
	 * This method Does not do loading the data in the grid and attaching events.
	 * 
	 * inputGrid - The variable that refers to the gird you are initializing config - The JSON array that has the values for initialization rowSpan - true or false or -1 - true = rowSpan and it will automatically turn OFF smart rendering and sorting -
	 * false = no rowSpan, but smart rendering and sorting is ON - -1 = no rowSpan and no smart rendering, but soring is ON
	 * 
	 * submitDefault - true or false for submiting values in the column back to the server singleClickEdit - This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on single click.
	 * 
	 * format of JSON for config var config = [ { columnId:, -- Required columnName:, -- If this is not defiend or '' this is set to hidden column type:, -- Default is ro sort:, -- Default is na or based on columnType width:, -- Default is 8, overridden to
	 * 0 if the column is hidden align:, -- Default is left, or right if the column sorting is int size:, -- If columnType is hed then defaults to columnWidth maxlength:, -- Default is nolimit submit:, -- Default is false or over written by submit Default
	 * tooltip:, -- Default is false or true for columns with width >= 20 hiddenSortingColumn:, -- Column to use for sorting, usually used for sorting date values onChange:, -- Name of the javascript function to call when this event is triggered, you can
	 * use onChange for hchStatus column type } ];
	 * 
	 * columnId - Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.
	 * 
	 * columnName - Column Name displayed to the user. Please internationalize this.
	 * 
	 * type - Set column types, you can define editable columns here. More documentation is availabe on Nimbus N:\Tech Center\dhtmlxGrid_Customized_Docs ro -read only hed -editable sinlge line text txt - Multiline edit text (mostly for comments and user
	 * input) date -hcal hcoro -select drop down hlink - link hch -checkbox that is used to denote update this row hchstatus - checkbox to display ture of false information, and distinguish between disabled and unchecked.
	 * 
	 * sort - Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na. sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting. For Date column types you need to write
	 * custom sorting funciton which will be triggered by onBeforeSorting event. For Editable Date column we will not allow sorting, set the sorting to be na. use haasHch for the hchstatus or the hch column types use haasCoro is for the hcoro column type.
	 * int for numbers
	 * 
	 * width -Set initial widths for your columns, set the initial widths based on the number of characters you want displayed in the column. We will proportionally increase the widths based on the user font size.
	 * 
	 * align - You can set column alingments, all string and date values will be left aligned and numbers will be right aligned.
	 * 
	 * size - This is the input element size for columntype hed
	 * 
	 * maxlength - this is the maximum lenght allowed in an input text field.
	 * 
	 * submit - passes this back to the server if set to ture.
	 * 
	 * tooltip - This enables tooltips on a column and on mouseover displays the value of the cell in a tooltip. We will enable tooltips only for columns whose width might be less than the text data in that column. Most likely candidates are packaging,
	 * item desc, part desc, any user comments etc.
	 * 
	 * hiddenSortingColumn - This will allow the column to be sorted by a different( usually hidden ) column, The value here is the columnId of the hidden column. And the value to be sorted in that hidden column. e.g. for unitCost to be sorted by
	 * 'hiddenUnitCost' ... { columnId:"unitCost", columnName:'<fmt:message key="label.cost"/>', sorting:'int', align:'left', hiddenSortingColumn:"hiddenUnitCost" }, { columnId:"hiddenUnitCost", sorting:'int' }, ...
	 * 
	 * onChange: -- Name of the javascrip function to call when this event is triggered, you can use onChange for hchStatus column type
	 * 
	 * attachHeader - set the split header. See the shelflifeexpforcastresult.jsp for example. filterBox: add filter box to the folumn, only valid for ro type.
	 * 
	 */

	var idstr = "";
	var headerstr = "";
	var visistr = "";
	// var valignstr = "";
	var alignstr = "";
	var typestr = "";
	var tooltipstr = "";
	var widthstr = "";
	var submitcol = "";
	var sortstr = "";
	var attachheaderstr = "";
	var attachheaderstrcount = 0;
	var submitarr = new Array();
	var submitD = false; // default is false, don't submit.
	var hasrowspan = false;
	var hiddenSortingArray = new Array();
	var hasHiddenSortingArray = false;

	if (typeof (inputSize) == 'undefined') { // create inputSize
		// global variable if it
		// doesn't exist.
		window["inputSize"] = new Array();
	}

	if (typeof (maxInputLength) == 'undefined') { // create maxInputLength
		// global variable if it
		// doesn't exist.
		window["maxInputLength"] = new Array();
	}

	if (rowSpan != null) {
		hasrowspan = rowSpan;
		debug("Setting rowspan to " + rowSpan);
	}

	if (submitDefault != null)
		submitD = submitDefault;

	for ( var i = 0; i < config.length; i++) {
		idstr += ',' + config[i].columnId; // id has to exist.
		// valignstr += ',middle';

		if ((config[i].attachHeader || '').length) {
			attachheaderstr += ',' + config[i].attachHeader;
			attachheaderstrcount++;
		}
		else
			attachheaderstr += ',' + "#rspan";

		if ((config[i].columnName || '').length) { // / resource
			// has to be a
			// string.
			headerstr += ',' + config[i].columnName;
			visistr += ',false';
			if (config[i].width != null) // can be either 8 or
				// "8" char long
				widthstr += ',' + config[i].width;
			else
				// default width is 8 characters long.
				widthstr += ',8';
		}
		else {
			headerstr += ',';
			visistr += ',true';
			widthstr += ',0';
		}

		if (config[i].hiddenSortingColumn) {
			hiddenSortingArray[config[i].columnId] = config[i].hiddenSortingColumn;
			hasHiddenSortingArray = true;
		}

		if ((config[i].type || '').length) {
			// columnType has to be a string.
			typestr += ',' + config[i].type;
			// to avoid errors, some settings
			switch (config[i].type) {
				case "hed":
					inputSize[config[i].columnId] = (config[i].width == null) ? 8 : config[i].width;
					maxInputLength[config[i].columnId] = 0;
					break;
			}
		}
		else {
			// default columnType is ro
			typestr += ',ro';
			// ro type and have filterBox.
			if (config[i].filterBox) {
				// some trick code here.
				headerstr += "<br/><input class='inputBox' id='" + config[i].columnId + "_' type='text' value='' size=8 onKeyUP='filterBy(\"" + config[i].columnId + "\");'/>";
				// alert(headerstr);
				config[i].sorting = 'na';
			}
		}
		// overwrite size default if defined.
		if (config[i].size != null) // can be 10 or "10"
			inputSize[config[i].columnId] = config[i].size;

		// overwrite maxlength default if defined.
		if (config[i].maxlength != null) // can be 10 or "10"
			maxInputLength[config[i].columnId] = config[i].maxlength;

		if (config[i].align != null)
			alignstr += ',' + config[i].align;
		else {
			if (config[i].sorting == 'int')
				alignstr += ',right';
			else
				alignstr += ',left';
		}

		if (hasrowspan == 1 || hasrowspan == true)
			sortstr += ',na'; // if a grid has rowspans > 1 we
		// do not allow sorting on all
		// columns.
		else {
			// If a sorting type is not defined in the config we
			// assign default values.
			sortS = 'na'; // no sorting for hidden column
			if ((config[i].columnName || '').length) // sorting
				// for
				// none
				// hidden
				// column
				switch (config[i].type) {
					case "hcal":
						if (config[i].hiddenSortingColumn) {
							sortS = (config[i].sorting || "int");
						}
						else {
							sortS = "na";
						}
						break;
					case "hch":
					case "hchstatus":
						if (!gridfalse(config[i].sorting))
							sortS = "haasHch";
						break;
					case "hcoro":
						sortS = config[i].sorting || "haasCoro";
						break;
					default:
						sortS = config[i].sorting || "haasStr"; // using
						// haasStr
						// unless
						// overwritten
				}
			else if (config[i].sorting) // overide default
			// sorting for hidden
			// columns for date and
			// others
			{
				sortS = config[i].sorting;
			}
			sortstr += ',' + sortS;
		}
		// tooltip
		if (config[i].type == "hcoro")
			tooltipstr += ',false';
		else if (config[i].tooltip) // can be either 'true' or true
			tooltipstr += ',' + config[i].tooltip;
		else {
			if (config[i].width != null && config[i].width >= 20)
				tooltipstr += ',true';
			else
				tooltipstr += ',false';
		}
		// multipermission
		if (typeof (config[i].permission) != 'undefined') {
			var pp = config[i].permission + "";
			if (pp == 'true' || pp == 'Y' || pp == '')
				_setPermColumn(config[i].columnId, config[i].columnId + "Permission");
			else if (!(pp == 'false' || pp == 'N' || pp == 'null'))
				_setPermColumn(config[i].columnId, pp);
		}
		// add filter
		if (config[i].filterElement) // default is false.
		{
			submitarr[filterElement.length] = config[i].submit;
		}

		if (config[i].submit != null) // default is false.
		{
			submitarr[submitarr.length] = config[i].submit;
		}
		else {
			submitarr[submitarr.length] = submitD;
		}
		try {
			if (config[i].onChange != null) // default is false.
			{
				if (typeof (config[i].onChange) == 'function')
					_onChangeFunctionArray[config[i].columnId] = config[i].onChange;
				else
					// string
					_onChangeFunctionArray[config[i].columnId] = eval(config[i].onChange);
			}
			else {
			}
		}
		catch (ex) {
		}
		try {
			if (config[i].onCheck != null) // default is false.
			{
				if (typeof (config[i].onCheck) == 'function')
					_onCheckFunctionArray[config[i].columnId] = config[i].onCheck;
				else
					// string
					_onCheckFunctionArray[config[i].columnId] = eval(config[i].onCheck);
			}
			else {
			}
		}
		catch (ex) {
		}

	}

	inputGrid.setImagePath("/dhtmlxGrid/codebase/imgs/");

	// inputGrid.setColVAlign( valignstr.substr(1) ); /*Controlled by CSS
	// not needed.*/
	inputGrid.setHeader(headerstr.substr(1));
	if (attachheaderstrcount)
		inputGrid.attachHeader(attachheaderstr.substr(1));
	/* Sets initial widths for your columns. */
	inputGrid.setInitWidths(widthstr.substr(1));

	/* Sets column alingments. */
	inputGrid.setColAlign(alignstr.substr(1));

	/* Sets column types */
	inputGrid.setColTypes(typestr.substr(1));

	/* Sets column sorting */
	inputGrid.setColSorting(sortstr.substr(1));

	/* This sets tooltips */
	inputGrid.enableTooltips(tooltipstr.substr(1));

	/*
	 * Set columIds. this will be the id you want your dynabean to be. this is the same as setting id attribute on an input element.
	 */

	inputGrid.setColumnIds(idstr.substr(1));
	inputGrid.submitOnlyChanged(false);
	inputGrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");

	/* Defines which columns you want submitted to the server */
	inputGrid.submitColumns(submitarr);

	/* setS setColumnsVisibility */
	inputGrid.setColumnsVisibility(visistr.substr(1));

	// for smart rendering
	try {
		inputGrid.setAwaitedRowHeight(gridRowHeight);
		// If smart rendering has NOT been set on or off yet and we are
		// not in a rowspan situation, turn it on
		if ((typeof inputGrid._srnd == 'undefined' || inputGrid._srnd == null) && !hasrowspan) {
			if (this._haas_debug)
				debug("SmartRender has not been set as of yet, enabling.");
			inputGrid.enableSmartRendering(true);
		}
		if (inputGrid._srnd && hasrowspan == true) {
			if (this._haas_debug)
				debug("SmartRender with rowspan is active.");
			inputGrid._haas_row_span = true;

			// Grab and use the arrays that tell us rowspans and CSS
			// color classes
			if (window['rowSpanMap']) {
				inputGrid._haas_row_span_map = window["rowSpanMap"];
			}
			else {
				inputGrid._haas_row_span_map = window["lineMap"];
			}
			if (window['rowSpanClassMap']) {
				inputGrid._haas_row_span_class_map = window["rowSpanClassMap"];
			}
			else {
				inputGrid._haas_row_span_class_map = window["lineMap3"];
			}
			if (window['rowSpanCols']) {
				inputGrid._haas_row_span_cols = window['rowSpanCols'];
			}
			else {
				inputGrid._haas_row_span_cols = [
					0
				];
			}

			if (window['rowSpanLvl2Map'] && window['rowSpanLvl2Cols']) {
				inputGrid._haas_row_span_lvl2 = true;
				inputGrid._haas_row_span_lvl2_map = window["rowSpanLvl2Map"];
				inputGrid._haas_row_span_lvl2_cols = window['rowSpanLvl2Cols'];
				if (window['rowSpanLvl3Map'] && window['rowSpanLvl3Cols']) {
					inputGrid._haas_row_span_lvl3 = true;
					inputGrid._haas_row_span_lvl3_map = window["rowSpanLvl3Map"];
					inputGrid._haas_row_span_lvl3_cols = window['rowSpanLvl3Cols'];
				}
			}

		}
	}
	catch (ex) {
	}

	inputGrid.setSkin("haas");
	inputGrid.init();

	if (hasHiddenSortingArray) {
		inputGrid.hiddenSortingArray = hiddenSortingArray;
		inputGrid.attachEvent("onBeforeSorting", _onBeforeSorting);
	}

	if (_isIE)
		inputGrid.entBox.onmousewheel = stop_event;

	/* This will update the column headers widths according to font size. */
	updateColumnWidths(inputGrid);
	// reSizeCoLumnWidths(inputGrid);

	/*
	 * This is to copy the ctrl+c to clipboard, and ctrl+v to paste to clipboard.
	 */
	inputGrid.entBox.onselectstart = function() {
		return true;
	};

	/*
	 * This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.
	 */
	if (singleClickEdit)
		inputGrid.enableEditEvents(true, false, false);

	/*
	 * Setting up haasGrid global variable, haasGrid is used as the anonymous grid, used when no grid is specified. When multiple grid exist in same window, only one can be haasGrid, all functions related to the other grid cannot be used anonmyously. E.g.
	 * use gridCellValue(grid,rowId,colId) insead of cellValue(rowId,colId)
	 */
	setHaasGrid(inputGrid);
}

/*
 * This will update the column headers widths according to font size and if it is editable
 */
originalColTotalWidth = 0;
function updateColumnWidths(inputGrid) {
	var newColWidth;

	if (!fontSizePref) {
		fontSizeFactor = 12;
	}
	else {
		switch (fontSizePref) {
			case "Smallest":
				fontSizeFactor = 8;
				break;
			case "Small":
				fontSizeFactor = 10;
				break;
			case "Medium":
				fontSizeFactor = 12;
				break;
			case "Large":
				fontSizeFactor = 14;
				break;
			case "Largest":
				fontSizeFactor = 16;
				break;
			default:
				fontSizeFactor = 12;
		}

		setWindowSizes();
		var divWidth = inputGrid.entBox.clientWidth;
		if (divWidth && divWidth > 0 && divWidth < myWidth) {
			inputGrid._haas_display_width = divWidth;
		}
		else {
			inputGrid._haas_display_width = myWidth;
		}

		if (inputGrid._haas_original_col_total_width == 0) {
			// Get Original Width which is now number of column characters
			// and set new width.
			for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
				inputGrid._haas_original_col_total_width += fontSizeFactor * parseInt(inputGrid.initCellWidth[i]);
			}
		}

		if (((inputGrid._haas_display_width - 30) <= inputGrid._haas_original_col_total_width)) {
			for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
				newColWidth = fontSizeFactor * parseInt(inputGrid.initCellWidth[i])
				inputGrid.setColWidth(i, newColWidth);
			}
		}
		else {
			reSizeCoLumnWidths(inputGrid);
		}
	}// end else
}

/*
 * This is called from resultframegridresie or nosearchresize, by passing a grid reference.
 */
/* This is to re-size column widths based on window size. */
function reSizeCoLumnWidths(inputGrid) {
	if (inputGrid != null) {
		try {

			if (inputGrid._haas_original_col_total_width > 0) {
				if (((inputGrid._haas_display_width - 30) > inputGrid._haas_original_col_total_width)) {
					// Get the total number of character columns in use
					var totalCols = 0;
					for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
						totalCols += parseInt(inputGrid.initCellWidth[i]);
					}
					// Figure out how large a character column should expand to
					var colWidth = (inputGrid._haas_display_width - 34) / totalCols;
					for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
						if (inputGrid.initCellWidth[i] > 0) {
							// Set the column width to the number of character cols * the new width
							inputGrid.setColWidth(i, parseInt(inputGrid.initCellWidth[i]) * colWidth);
						}
					}
					reSizeGridCoLWidthsCount++;
				}
				else if (reSizeGridCoLWidthsCount > 0) {
					updateColumnWidths(inputGrid);
					reSizeGridCoLWidthsCount = 0;
				}
			}
		}
		catch (ex) {
			alert("error in resizeGridColumnWidths:" + ex);
		}
	}
}

function reSizeCoLumnWithGridWidth(inputGrid, gridWidth) {
	if (inputGrid != null) {
		var gridColTotalWidth = 0;
		var totalCols = 0;
		for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
			var newColWidth = fontSizeFactor * parseInt(inputGrid.initCellWidth[i])
			inputGrid.setColWidth(i, newColWidth);
			gridColTotalWidth += newColWidth * 1;
			// Get the total number of character columns in use
			totalCols += parseInt(inputGrid.initCellWidth[i]);
		}
		try {
			if (gridColTotalWidth > 0) {
				if (((gridWidth) > gridColTotalWidth)) {
					// Figure out how large a character column should expand to
					var colWidth = (gridWidth - 42) / totalCols;
					for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
						if (inputGrid.initCellWidth[i] > 0) {
							// Set the column width to the number of character cols * the new width
							inputGrid.setColWidth(i, parseInt(inputGrid.initCellWidth[i]) * colWidth);
						}
					}
					// reSizeGridCoLWidthsCount++;
				}
				else {
					updateColumnWidths(inputGrid);
					// reSizeGridCoLWidthsCount = 0;
				}
			}
		}
		catch (ex) {
			alert("error in resizeGridColumnWidth" + ex);
		}
	}
}

function resizeGridToWidth(inputGrid, newWidth) {
	if (inputGrid != null) {
		try {
			var originalColTotalWidth = 0;
			// Get Original Width which is now number of column characters
			// and set new width.
			for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
				var newColWidth = fontSizeFactor * parseInt(inputGrid.initCellWidth[i])
				inputGrid.setColWidth(i, newColWidth);
				originalColTotalWidth += newColWidth * 1;
			}

			if (inputGrid._haas_original_col_total_width > 0) {
				if (((newWidth - 30) > inputGrid._haas_original_col_total_width)) {
					// Get the total number of character columns in use
					var totalCols = 0;
					for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
						totalCols += parseInt(inputGrid.initCellWidth[i]);
					}
					// Figure out how large a character column should expand to
					var colWidth = (newWidth - 34) / totalCols;
					for ( var i = 0; i < inputGrid.initCellWidth.length; i++) {
						if (inputGrid.initCellWidth[i] > 0) {
							// Set the column width to the number of character cols * the new width
							inputGrid.setColWidth(i, parseInt(inputGrid.initCellWidth[i]) * colWidth);
						}
					}
					reSizeGridCoLWidthsCount++;
				}
				else if (reSizeGridCoLWidthsCount > 0) {
					updateColumnWidths(inputGrid);
					reSizeGridCoLWidthsCount = 0;
				}
			}
		}
		catch (ex) {
			alert("error in resizeGridToWidth" + ex);
		}
	}
}

// You can use this one as standard. For initGridWithGlobal if nothing special
// to customize.
var _gridConfig = {
	divName : 'beanData', // the div id to contain the grid of the data.
	beanData : 'jsonData', // the data variable name for jsonparse, as in
	// grid.parse( beanData ,"json" )
	beanGrid : 'beanGrid', // the grid's name, as in
	// beanGrid.attachEvent...
	config : 'config', // the column config var name, as in var config
	// = { [ columnId:..,columnName...
	rowSpan : false, // this page has rowSpan > 1 or not.
	submitDefault : false, // the fields in grid are defaulted to be
	// submitted or not.
	// remember to call haasGrid.parentFormOnSubmit() before actual submit.
	onRowSelect : null, // the onRowSelect event function to be
	// attached, as in
	// beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick : null, // the onRightClick event function to be
	// attached, as in
	// beanGrid.attachEvent("onRightClick",selectRow)
	onAfterHaasRenderRow : null,// the onAfterHaasRenderRow event
	// function to be
	// attached and called back when a row is rendered (with the rowId as
	// arg), as in
	// beanGrid.attachEvent("onAfterHaasRenderRow",modifyRow)
	onAfterHaasRenderRowSpan : null,

	onAfterHaasGridRendered : null, // Callback function after grid has been completely rendered
	singleClickEdit : false, // This is for single click editing. If
	// it is set to true, txt column type
	// will pop a txt editing box on sligne
	// click.
	backgroundRender : false,// If true this will enable Smart Render AND
	// begin rendering
	// the grid in the background
	selectChild : 0, // 0 == No,
	// 1 = select child rowspan in a darker color
	// 2 = select secondary child rowspan in a darker color
	// 3 = select tertiary child rowspan in a darker color
	// noSmartRender : false
	// If set to true this will disable smart rendering and cause the entire
	// grid to
	// be drawn immediately, default <null>, set to false to enable
	// smartRender when rowSpan == true
	// onBeforeSorting:_onBeforeSorting
	variableHeight : false
};

// using a gridconfig variable that contains all grid init params.
function initGridWithGlobal(gridConfig) {

	var divname = gridConfig.divName;
	var beandatavar = gridConfig.beanData;
	var gridvar = gridConfig.beanGrid;
	var configvar = gridConfig.config;
	var rowSpan = gridConfig.rowSpan;
	var subD = gridConfig.submitDefault;
	var singleClickEdit = false;
	singleClickEdit = gridConfig.singleClickEdit;
	if (singleClickEdit == null)
		singleClickEdit = false;

	if (window["resizeGridWithWindow"] == null) {
		window["resizeGridWithWindow"] = true;
	}

	if (beandatavar == null)
		beandatavar = window['beanData'];
	else if (typeof (beandatavar) == 'string') {
		beandatavar = window[beandatavar];
	}

	if (beandatavar != null) {

		if (gridvar == null)
			gridvar = 'beanGrid';

		if (configvar == null)
			configvar = window['config'];
		else if (typeof (configvar) == 'string') {
			configvar = window[configvar];
		}

		if (typeof (gridvar) == 'string')
			gridvar = window[gridvar] = new dhtmlXGridObject(divname);
		else
			gridvar = new dhtmlXGridObject(divname);

		try {
			$(divname).style["display"] = ""; // fix ie6 bug.
			if (!(typeof ($(divname).offsetHeight) != 'undefined' && $(divname).offsetHeight > 0 && typeof ($(divname).offsetWidth) != 'undefined' && $(divname).offsetWidth > 0) && gridConfig.width != null && gridConfig.height != null)
				$(divname).style.width = gridConfig.width;
			$(divname).style.height = gridConfig.height;
		}
		catch (ex) {
		} // minor error don't break;
		try {
			if (gridConfig.evenoddmap)
				gridvar.setEvenoddmap(gridConfig.evenoddmap);
		}
		catch (ex) {
		} // added feature don't break;

		// Set background rendering
		if (gridConfig.backgroundRender || gridConfig.onAfterHaasGridRendered) {
			gridvar._haas_bg_render_enabled = true;
			gridvar.enableSmartRendering(true);
		}
		// if noSmartRender is true, disable smart rendering
		else if (gridConfig.noSmartRender) {
			// debug("gridConfig.noSmartRender is true: disabling smartRender");
			gridvar.enableSmartRendering(false);
		}
		// Old way of disabling smartRender
		else if (-1 == rowSpan) {
			// debug("gridConfig.rowspan == -1: disabling smartRender");
			gridvar.enableSmartRendering(false);
		}
		// If rowSpan check for noSmartRender == false
		else if (rowSpan && gridConfig.noSmartRender == false) {
			// debug("rowSpan == true && gridConfig.noSmartRender is false: enabling smartRender");
			gridvar.enableSmartRendering(true);
		}
		// Otherwise disable smart rendering if fewer than 50 rows
		else if (beandatavar.rows && beandatavar.rows.length < 50) {
			// debug("beandatavar.rows.length < 50: disabling smartRender");
			gridvar.enableSmartRendering(false);
		}

		// Set secondary row selection in a darker color
		if (gridConfig.selectChild == 1) {
			gridvar._haas_row_span_child_select = true;
		}
		else if (gridConfig.selectChild == 2) {
			gridvar._haas_row_span_lvl2_child_select = true;
		}
		else if (gridConfig.selectChild == 3) {
			gridvar._haas_row_span_lvl3_child_select = true;
		}

		/*
		 * This keeps the row height the same, true will wrap cell content and height of row will change.
		 */
		if (gridConfig.variableHeight)
			gridvar.enableMultiline(true);
		else
			gridvar.enableMultiline(false);

		initGridWithConfig(gridvar, configvar, rowSpan, subD, singleClickEdit);

		if (gridConfig.onRowSelect)
			gridvar.attachEvent("onRowSelect", gridConfig.onRowSelect);
		if (gridConfig.onRightClick)
			gridvar.attachEvent("onRightClick", gridConfig.onRightClick);
		if (gridConfig.onBeforeSorting)
			gridvar.attachEvent("onBeforeSorting", gridConfig.onBeforeSorting);
		if (gridConfig.onCheck)
			gridvar.attachEvent("onCheck", gridConfig.onCheck);
		if (gridConfig.onAfterHaasRenderRow)
			gridvar.attachEvent("onAfterHaasRenderRow", gridConfig.onAfterHaasRenderRow);
		if (gridConfig.onAfterHaasRenderRowSpan)
			gridvar.attachEvent("onAfterHaasRenderRowSpan", gridConfig.onAfterHaasRenderRowSpan);
		if (gridConfig.onAfterHaasGridRendered) {
			gridvar.attachEvent("onAfterHaasGridRendered", gridConfig.onAfterHaasGridRendered);
		}
		gridvar.parse(beandatavar, "json");
	}
}

/*
 * This function will set the row class to the specified input class.
 */
function setRowClass(rowId, rowClass) {
	var row = haasGrid.getRowById(rowId);
	if (row) {
		// Clear any set row style. Style has preference over css.
		haasGrid.setRowTextStyle(rowId, "");
		rowClass = rowClass.replace(/Selected/, " rowselected");
		row.className = row._css = rowClass;
		for ( var i = 0; i < row.childNodes.length; i++)
			row.childNodes[i].className = rowClass;
	}
}

/*
 * This function returns the class of the row identified by the input row id
 */
function getRowClass(rowId) {
	var rowClass;
	var row = haasGrid.getRowById(rowId);
	if (row) {
		rowClass = row._css;
		if (rowClass == undefined) {
			if (row.className) {
				if (row.className.trim().indexOf(' ') == -1) {
					rowClass = row.className.trim();
				}
				else {
					var classes = row.className.trim().split(' ');
					rowClass = classes[classes.length - 1];
				}
			}
		}
	}
	if (rowClass == undefined) {
		if (rowId % 2 == 1) {
			rowClass = 'uneven';
		}
		else {
			rowClass = 'even';
		}
	}
	return rowClass;
}

/*
 * This associative array is an association between a class and its' background color
 */
var EnumClassColors = {
	'grid_red' : '#FF9999',
	'grid_green' : '#d5ffea',
	'grid_yellow' : '#FFFF99',
	'grid_black' : '#727272',
	'grid_pink' : '#ff0fff',
	'grid_orange' : '#ff8000',
	'grid_lightblue' : '#f5f5ff',
	'grid_lightgray' : '#BEBEBE',
	'grid_purple' : '#9999FF',
	'grid_error' : '#FF7070',
	'grid_default' : '#acacff',
	'grid_white' : '#FFFFFF'
};
/*
 * This function sets the row text style background to the background of the specified class. It provides a solution for overriding default css background values. If you then execute statement haasGrid.setRowTextStyle(rowId,"") , the background color is
 * removed and a css can be applied to the row.
 */
function overrideDefaultSelect(rowId, currentRowClass) {
	// This will override the glimpse of default even/odd class highlight
	if (EnumClassColors[currentRowClass])
		haasGrid.setRowTextStyle(rowId, "background-color:" + EnumClassColors[currentRowClass]);
}

// All grid that's not the main grid ( main grid can be use anonymously) should
// do this.
function initGridWithConfigForPopUp(inputGrid, config, rowSpan, submitDefault, singleClickEdit) {
	var oriHaas = haasGrid;
	initGridWithConfig(inputGrid, config, rowSpan, submitDefault, singleClickEdit);
	haasGrid = oriHaas;
}
function initPopupGridWithGlobal(hgridConfig) {
	var oriHaas = haasGrid;
	initGridWithGlobal(hgridConfig);
	haasGrid = oriHaas;
}

function simpleCallToServerWithGrid(url) {

	if (url.indexOf('?') == -1)
		url += '?';
	else
		url += '&'

	url += 'callback=simpleGridPopupCallback';

	callToServer(url);

}

function simpleGridPopupCallback(gridConfig, attachObj) {
	var popupname = gridConfig.name;
	var divwidth = gridConfig.width;
	var divheight = gridConfig.height;

	var winsys = window['popupwinsys'];

	if (winsys == null) {
		winsys = new dhtmlXWindows();
		winsys.setImagePath("/dhtmlxWindows/codebase/imgs/");
		window['popupwinsys'] = winsys;
	}

	var popupCount = window['popupcount'];
	if (popupCount == null) {
		popupCount = 0;
	}
	window['popupcount'] = ++popupCount;

	if (popupname == null)
		popupname = 'Information Window ' + popupCount;

	divname = 'popupdiv_' + popupCount;

	attaObj = attachObj;
	try {
		var tempDiv = document.createElement('div');
		tempDiv.id = divname;
		tempDiv.style.display = 'none';
		tempDiv.style.overflow = 'auto';
		tempDiv.style.border = '0px';
		tempDiv.style.width = divwidth + 'px';
		tempDiv.style.height = divheight + 'px';
		document.body.appendChild(tempDiv);
		if (attachObj == null) { // the attach object is also the
			// grid div.
			gridConfig.divName = divname;
			attaObj = document.getElementById(divname);
		}
		else { // the grid div is contained in the attach obj.
			attaObj = document.getElementById(divname);
			attaObj.innerHTML = attachObj.innerHTML;
		}
		// initGridWithGlobal(hgridConfig);
		initPopupGridWithGlobal(gridConfig);
	}
	catch (ex) {
	}

	if (!winsys.window(popupname)) {
		// create window first time
		var legendWin = winsys.createWindow(popupname, 0, 0, divwidth, divheight);
		legendWin.setText(popupname); // well, this string should be
		// passed from the
		// shownewchemappdetailcallback
		// and internationalize from that screen.
		legendWin.clearIcon(); // hide icon
		legendWin.denyResize(); // deny resizing
		legendWin.denyPark(); // deny parking
		legendWin.attachObject(attaObj);
		legendWin.attachEvent("onClose", function(legendWin) {
			legendWin.hide();
		});
		legendWin.center();
	}
	else {
		// just show
		winsys.window(popupname).show();
	}
}

function simpleResultGridSubmit(act, actEle) {
	if (act == null)
		act = 'update';
	if (actEle == null)
		actEle = 'uAction';
	$(actEle).value = act;
	if (typeof (checkGridForm) != 'undefined' && !checkGridForm())
		return false;
	parent.showPleaseWait();
	haasGrid.parentFormOnSubmit(); // use result main grid, prepare grid
	// for data sending
	document.genericForm.submit();
	return true;
}

function log(msg) {
	if (this.console && typeof console.log != "undefined") {
		console.log(msg);
	}
}
function debug(msg) {
	if (debugOn) {
		log(msg);
	}
}

function filterBy(columnId) {
	var val = $v(columnId + "_").toString().toLowerCase();
	for ( var i = 0; i < haasGrid.getRowsNum(); i++) {
		var rowId = haasGrid.getRowId(i);
		var rval = cellValue(rowId, columnId).toString().toLowerCase(); // largeSrc.search(/nopic/i);
		if (rval.indexOf(val) != -1)// ) && (aVal=="" || aStr.indexOf(aVal)==0)) // rval.search(//i);
			haasGrid.setRowHidden(haasGrid.getRowId(i), false)
		else
			haasGrid.setRowHidden(haasGrid.getRowId(i), true)
	}
}

//this will decode the HTML Encode
function htmlDencodeGrid(s) {
	var str = new String(s);
	str = str.replace(/&amp;/g, "&");
	str = str.replace(/&lt;/g, "<");
	str = str.replace(/&gt;/g, ">");
	str = str.replace(/&quot;/g, "\"");
	str = str.replace(/&#034;/g, "\"");
	str = str.replace(/&#34;/g, "\"");
	str = str.replace(/&#035;/g, "#");
	str = str.replace(/&#038;/g, "&");
	str = str.replace(/&#38;/g, "&");
	str = str.replace(/&#039;/g, "\'");
	str = str.replace(/&#39;/g, "\'");
	str = str.replace(/&#044;/g, ",");
	str = str.replace(/&#043;/g, "+");
	return str;
}

/**
 * Return the editable permission of the given column at the given row in the given grid
 * @param theGrid
 * @param rowId
 * @param colId
 * @returns
 */
function getColPermission(theGrid, rowId, colId) {
	var pColIndex = null;
	if ((typeof multiplePermissions != 'undefined') && (typeof permissionColumns != 'undefined')) {
		if (typeof permissionColumns[colId] != 'undefined') {
			pColIndex = theGrid.getColIndexById(colId + 'Permission');
		} else {
			pColIndex = theGrid.getColIndexById('permission');
		}
	} else {		
		pColIndex = theGrid.getColIndexById('permission');
	}
	var permission;
	if (pColIndex == undefined) {
		permission = 'N';
	} else {
		permission = theGrid.cellById(rowId, pColIndex).getValue();
	}
	
	return permission;
}

function getRowSpanColsArr(columnIdsStr, config) {
	var columnIds = columnIdsStr.split(",");
	var rowSpanCols = new Array();
	for (var i = 0; i < columnIds.length; i++) {
		var curIndex = gridConfigColIndex(columnIds[i].trim(), config);
		if (curIndex != -1)
			rowSpanCols.push(curIndex);
	}
	rowSpanCols.sort(function(a, b){return a - b});
	
	return rowSpanCols;
}

function gridConfigColIndex(columnId, config) {
	var configvar = new Array();
	if (typeof config != 'undefined' && isArray(config))
		configvar = config;
	else if (typeof window['config'] != 'undefined' && isArray(window['config']))
		configvar = window['config'];
	for (var index = 0; index < configvar.length; index++)
		if (configvar[index].columnId === columnId)
			return index;
	
	return -1;
}

function setGridWidthFromColWidth(inputGridConfig, maxWidth) {
	var inputGrid = window[inputGridConfig.beanGrid];
	
	//18 value comes from dhtmlxgrid.js - setSizes() - scrfix variable's value
	var gridColTotalWidth = 18;
	for (var index = 0; index < inputGrid.getColumnsNum(); index++) {
		gridColTotalWidth += inputGrid.getColWidth(index);
	}
	//if maxWidth is provided and grid's total width is more than maxWidth, use maxWidth. Otherwise, use grid's total width.
	$(inputGridConfig.divName).style.width = (maxWidth && gridColTotalWidth > maxWidth ? maxWidth : gridColTotalWidth) + "px";
}

function setGridHeightFromRowHeight(gridConfig, maxRowDisplayed) {
	var inputGrid = window[gridConfig.beanGrid];

	//Note:
	//	- 18 value comes from dhtmlxgrid.js - setSizes() - scrfix variable's value
	//	- _srdh is the default height of smart rendering
	//	- grid's row's offsetHeight is the height of the row
	//	- inputGrid.entBox is the displayed part of the grid, aka the main div that is created in a .jsp file for the grid
	//	- inputGrid.hdrBox is the header part of the grid
	//	- inputGrid.objBox is the detail part of the grid
	var gridDetailHeight = 18;
	if (inputGrid.getRowsNum() == 0) {
		gridDetailHeight = inputGrid._srdh;
	} else {
		for (var index = 0; index < inputGrid.getRowsNum(); index++) {
			if (maxRowDisplayed && index >= maxRowDisplayed) {
				break;
			}
			
			gridDetailHeight += (inputGrid.getRowById(inputGrid.getRowId(index)).offsetHeight || inputGrid._srdh);
		}
	}

	inputGrid.entBox.style.height = (Math.max(parseFloat(inputGrid.hdrBox.style.height), 60) + gridDetailHeight) + "px";
	inputGrid.objBox.style.height = gridDetailHeight + "px";
}