j$(document).ready(function() {

	mfrNotification.finishLoading();
	preSelectCategories(categorySelections);
	
	j$("#tabSelect").focus(function() {
		j$("#tabList").data("previousSelection", j$("#tabList").data("currentSelection"));
		toggleTab("selector");
	});
	
	j$("#tabSelect").blur(function() {
		var previousSelection = j$("#tabList").data("previousSelection");
		if (previousSelection != null && previousSelection !== undefined && previousSelection.length > 0) {
			toggleTab(previousSelection);
			j$("#"+previousSelection+"Link").focus();
			j$("#tabList").data("previousSelection", "");
		}
	});
	
	j$("#tabSelect > option:first").click(function() {
		j$("#tabSelect").blur();
	});
	
	j$("#tabSelect").change(function() {
		if (this.selectedIndex > 0) {
			var newTabId = this.value;
			var tab = generateTab(newTabId, j$(this).find(":selected").text());
			
			toggleTab("");
			j$("#"+newTabId+"Div").show();
			j$("#tabList").data("currentSelection", newTabId);
			j$("#tabList").data("previousSelection", null);
			mfrNotification.setSelected(newTabId, true);
			mfrNotification.selectTab(newTabId);
			j$(this).parentsUntil("ul", "li").before(tab);
			j$(this).find(":nth-child("+(1+this.selectedIndex)+")").attr("disabled",true);
			j$("#"+newTabId+"Link").focus();
			this.selectedIndex = 0;
		}
	});
	
	function generateTab(tabId, tabText) {
		var tab = j$("<li></li>");
		var tabLink = j$("<a></a>");
		tabLink.attr("id", tabId+"Link");
		tabLink.attr("href", "#");
		tabLink.addClass("selectedTab");
		tabLink.click(function() {
			toggleTab(tabId);
		});
		var tabSpan = j$("<span></span>").text(tabText + "  ");
		tabSpan.attr("id", tabId+"LinkSpan");
		tabSpan.addClass("selectedSpanTab");
		
		var spacer = j$("<img/>");
		spacer.attr("src", "/images/spacer14.gif");
		spacer.addClass("iconImage");
		
		var closeX = j$("<img/>");
		closeX.attr("src", "/images/closex.gif");
		closeX.attr("alt", "Close Tab");
		closeX.attr("title", "Close Tab");
		closeX.click(function() {
			closeTabManually(tabId);
		});
		
		tabSpan.prepend(spacer);
		tabSpan.append(closeX);
		
		tabLink.append(tabSpan);
		tab.append(tabLink);
		
		return tab;
	}
	
	function toggleTab(tabId) {
		if (tabId.length == 0 || j$("#"+tabId+"Link").length > 0) {
			j$("#tabList > li").children().removeClass("selectedTab");
			j$("#tabList > li").children().addClass("tabLeftSide");
			j$("#tabList > li").children().children().removeClass("selectedSpanTab");
			j$("#tabList > li").children().children().addClass("tabRightSide");
			j$(".haasTabs").siblings(":not(.boxhead)").hide();
			if (tabId.length > 0) {
				j$("#tabList").data("currentSelection", tabId);
				j$("#"+tabId+"Link").removeClass("tabLeftSide");
				j$("#"+tabId+"Link").addClass("selectedTab");
				j$("#"+tabId+"LinkSpan").removeClass("tabRightSide");
				j$("#"+tabId+"LinkSpan").addClass("selectedSpanTab");
				j$("#"+tabId+"Div").show();
				j$("#"+tabId+"MfrResultDiv").show();
				j$("#"+tabId+"MaterialResultDiv").show();
				j$("#"+tabId+"ItemResultDiv").show();
				mfrNotification.selectTab(tabId);
			}
		}
	}
	
	function closeTabManually(tabId) {
		var tab = j$("#"+tabId+"Link").parentsUntil("ul", "li");
		if (tabId === j$("#tabList").data("currentSelection")) {
			var nextTab = tab.next();
			var id = nextTab.children().first().attr("id");
			id = id.slice(0, -4);
			toggleTab(id);
		}
		tab.remove();
		var opt = j$("#tabSelect").children("option[value="+tabId+"]");
		j$("#tabSelect").children("option[value="+tabId+"]").attr("disabled",false);
		mfrNotification.setSelected(tabId, false);
	}
	
	function preSelectCategories(selections) {
		var tab = null;
		var tabId = "";
		for (var s in selections) {
			if (selections[s].selected) {
				tabId = selections[s].id;
				tab = generateTab(selections[s].id, selections[s].desc);
				j$("#tabList").children().first().before(tab);
			}
		}
		if (tab != null) {
			toggleTab(tabId);
			mfrNotification.initializeGrids(selections);
		}
	}
	
	j$("#internalCommentBtn").click(function() {
		j$("#internalCommentPopup").toggle();
	});
	
	j$("body").click(function(event) {
		if ( ! (j$(event.target).is("#internalCommentBtn") || j$(event.target).is("#internalCommentPopup") 
				|| j$(event.target).is("#internalComments"))) {
			j$("#internalCommentPopup").hide();
		}
	});
});

(function(mfrNotification) {
	mfrNotification.showErrorMessage = false;
	mfrNotification.acquiredMfr = false;
	mfrNotification.selectedTab = "";
	mfrNotification.selectedRow = {};
	var transitReload = false;
	var selectedMaterialQueue = [];
	var selectedItemQueue = [];
	
	mfrNotification.finishLoading = function() {
		if (mfrNotification.showErrorMessage) {
			mfrNotification.showError();
		}
		
		var reqApproveDate = $v("requestApproveDate");
		var reqApproveUser = $v("requestApproveUser");
		var reqSubmitDate = $v("dateSubmitted");
		var reqSubmitUser = $v("insertedBy");
		if (reqApproveDate.length > 0 && reqApproveUser.length > 0) {
			if ($v("requestApproved") == "Y") {
				$("saveMessage").innerHTML = "Request Approved on "+reqApproveDate;
			}
			else if ($v("requestRejected") == "Y") {
				$("saveMessage").innerHTML = "Request Rejected on "+reqApproveDate;
			}
			$("saveMessage").style.display = "";
		}
		else if (reqSubmitDate.length > 0 && reqSubmitUser.length > 0) {
			$("saveMessage").innerHTML = "Request Submitted on "+reqSubmitDate;
			$("saveMessage").style.display = "";
		}
		
		var selections = getSelectedCategories();
		for (var i in selections) {
			try {
				$(selections[i]+"MaterialFooter").innerHTML = messagesData.recordsFound + ": " + $v(selections[i]+"MaterialCount");
			} catch(e) {}
			try {
				$(selections[i]+"ItemFooter").innerHTML = messagesData.recordsFound + ": " + $v(selections[i]+"ItemCount");
			} catch(e) {}
		}
	}
	
	function snapshotId(element) {
		var id = element.name;
		if (typeof id == "undefined") {
			id = element.id;
		}
		return id;
	}
	
	function takeSnapshot(tab, tableFunction) {
		var snapshot = {};
		
		var inputs = j$("#"+tab+" [snap]").get();
		
		for (var i in inputs) {
			var peeledId = snapshotId(inputs[i]);
			var peeledId = peeledId.substring(peeledId.search("\\.")+1);
			if (j$(inputs[i]).is("div")) {
				if (tableFunction == null) {
					tableFunction = function(tableDiv) { return takeTableSnapshot(tableDiv); };
				}
				snapshot[peeledId] = tableFunction(inputs[i]);
			}
			else {
				if ( ! snapshot[peeledId]) {
					snapshot[peeledId] = determineElementValue(inputs[i]);
				}
			}
		}
		
		return snapshot;
	}
	
	function takeTableSnapshot(tableDiv) {
		var tableId = tableDiv.id;
		var snapshotJsonData = {"rows":[]};
		jQuery(tableDiv).find(".objbox > table").find("tr").each(function(row) {
			var rowSpanOffset = 0;
			jQuery(this).children("td").each(function(cell) {
				var cellValue = determineTableCellValue(this);
				var rowspan = this.rowSpan;
				if ( ! rowspan) {
					rowspan = 1;
				}
				var rowAdd = 0;
				while (rowAdd < rowspan) {
					var rowIdx = row+rowAdd;
					if (snapshotJsonData["rows"].length <= rowIdx) {
						snapshotJsonData["rows"][rowIdx] = {"id":(1+rowIdx), "data":[]};
					}
					while (snapshotJsonData["rows"][rowIdx]["data"][cell+rowSpanOffset] != null) {
						rowSpanOffset++;
					}
					snapshotJsonData["rows"][rowIdx]["data"][cell+rowSpanOffset] = cellValue;
					rowAdd++;
				}
			});
		});
		return snapshotJsonData;
	}
	
	function takeTableSnapshotWithNames(tableDiv, colConfig, columnFilter, rowFilter) {
		var tableId = tableDiv.id;
		var snapshotJsonData = {"rows":[]};
		if (colConfig) {
			var tableRows = j$("#"+tableId).find(".objbox > table").find("tr").get();
			for (var i in tableRows) {
				// first row has table headers
				var rowId = i-1;
				var rowSpanOffset = 0;
				var tableCells = j$(tableRows[i]).children("td").get();
				for (var j in tableCells) {
					var cellValue = determineTableCellValue(tableCells[j]);
					var rowspan = tableCells[j].rowSpan;
					if ( ! rowspan) {
						rowspan = 1;
					}
					var rowAdd = 0;
					while (rowAdd < rowspan) {
						var rowIdx = rowId+rowAdd;
						//var cellIdx = 1*j;
						var cellIdx = tableCells[j]["_cellIndex"];
						if (snapshotJsonData["rows"].length <= rowIdx) {
							snapshotJsonData["rows"][rowIdx] = {};
						}
						//while (snapshotJsonData["rows"][rowIdx][colConfig[cellIdx+rowSpanOffset].columnId] != null) {
						//	rowSpanOffset++;
						//}
						var includeColumn = true;
						try {
							//includeColumn = columnFilter(colConfig, cellIdx+rowSpanOffset);
							includeColumn = columnFilter(colConfig, cellIdx);
						} catch(e) {}
						if (includeColumn) {
							//snapshotJsonData["rows"][rowIdx][colConfig[cellIdx+rowSpanOffset].columnId] = cellValue;
							snapshotJsonData["rows"][rowIdx][colConfig[cellIdx].columnId] = cellValue;
						}
						rowAdd++;
					}
				}
				if (rowId >= 0) {
					var includeRow = true;
					try {
						includeRow = rowFilter(snapshotJsonData["rows"][rowId]);
					} catch(e){}
					if ( ! includeRow) {
						snapshotJsonData["rows"][rowId] = null;
					}
				}
			}
		}
		return snapshotJsonData;
	}
	
	function determineElementValue(element) {
		var value = "";
		var jElement = j$(element);
		if (jElement.is("input:text, input:radio:checked, select, textarea")) {
			value = jElement.val();
		}
		else if (jElement.is("input:checkbox")) {
			value = jElement.prop("checked");
		}
		else if (jElement.is("span")) {
			value = jElement.text();
		}
		else if (element.length > 0){
			value = element;
		}
		return value;
	}
	
	function determineTableCellValue(tableCell) {
		var value = "";
		var element = jQuery(tableCell).children("input:text, select");
		if (element.length == 0) {
			element = jQuery(tableCell).children("input:checkbox");
			if (element.length == 0) {
				element = jQuery(tableCell).text();
				value = element;
			}
			else {
				value = element.prop("checked");
			}
		}
		else if (element.length > 0){
			value = element.val();
		}
		else {
			value = jQuery(tableCell).text();
		}
		return value;
	}
	
	function getFormattedDateTime(jsDate) {
		var dateString = "";
		dateString += jsDate.getDate();
		dateString +="-"+month_abbrev_Locale_Java["en_US"][jsDate.getMonth()];
		dateString +="-"+jsDate.getFullYear()+ " ";
		var ampm = "AM";
		if (jsDate.getHours() == 0) {
			dateString += "12";
		}
		else if (jsDate.getHours() > 12) {
			dateString += (jsDate.getHours()-12);
			ampm = "PM";
		}
		else {
			dateString += jsDate.getHours()
		}
		dateString += ":"+jsDate.getMinutes();
		dateString += " "+ampm;
		
		return dateString;
	}
	
	mfrNotification.showWait = function(message){
		if (message) {
			$("transitLabel").innerHTML = message;
		}

		if (dhxWins == null) {
			dhxWins = new dhtmlXWindows();
			dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
		}

		if (!dhxWins.window("transitDailogWin")) {
			// create window first time
			transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
			transitWin.setText("");
			transitWin.clearIcon();  // hide icon
			transitWin.denyResize(); // deny resizing
			transitWin.denyPark();   // deny parking

			transitWin.attachObject("transitDailogWin");
			//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
			transitWin.center();
			// setting window position as default x,y position doesn't seem to work, also hidding buttons.
			transitWin.setPosition(328, 231);
			transitWin.button("minmax1").hide();
			transitWin.button("park").hide();
			transitWin.button("close").hide();
			transitWin.setModal(true);
		}else{
			// just show
			//if (transitWin.isModal()) {
			//	transitReload = true;
			//}
			//else {
				transitWin.setModal(true);
				dhxWins.window("transitDailogWin").show();
			//}
		}
	}
	
	mfrNotification.stopWaiting = function() {
		if (dhxWins != null) {
			if (dhxWins.window("transitDailogWin")) {
				dhxWins.window("transitDailogWin").setModal(false);
				dhxWins.window("transitDailogWin").hide();
				if (transitReload) {
					mfrNotification.showWait();
				}
				transitReload = false;
			}
		}
		return true;
	}
	
	mfrNotification.showError = function() {
		var errorMessagesArea = $("errorMessagesArea");
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

			errorWin.attachObject("errorMessagesArea");
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
	}
	
	mfrNotification.saveRequest = function() {
		var selections = getSelectedCategories();
		if (selections.length == 0) {
			alert(messagesData.categoryrequired);
			return false;
		}
		else if (slstValidation()){
			var pageUploadData = serializeData();
	
			j$.ajax({
				url:"mfrnotificationmain.do",
				type:"POST",
				cache:false,
				data:{"uAction": "save", 
					"notificationId": j$("#notificationId").val(),
					"internalComments": j$("#internalComments").val(),
					"pageUploadCode": JSON.stringify(pageUploadData),
					"selectedCategories": String(selections)
				},
				success: function(data) {
					if (data.length > 0) {
						var jso = j$.parseJSON(data);
						alert(jso["tcmisError"]);
					}
					else {
						var savedMsg = messagesData.requestSavedOn.replace('{1}',getFormattedDateTime(new Date()));
						$("saveMessage").innerHTML = savedMsg;
						$("saveMessage").style.display = "";
						alert(savedMsg);
					}
				}
			});
			return true;
		}
	}
	
	mfrNotification.submitRequest = function() {
		var selections = getSelectedCategories();
		if (selections.length == 0) {
			alert(messagesData.categoryrequired);
			return false;
		}
		else if (validateMfrNotification()) {
			var pageUploadData = serializeData();
	
			j$.ajax({
				url:"mfrnotificationmain.do",
				type:"POST",
				cache:false,
				data:{"uAction": "submit", 
					"notificationId": j$("#notificationId").val(),
					"internalComments": j$("#internalComments").val(),
					"pageUploadCode": JSON.stringify(pageUploadData),
					"selectedCategories": String(selections)
				},
				success: function(data) {
					if (data.length > 0) {
						var json = j$.parseJSON(data);
						alert(json["tcmisError"]);
					}
					else {
						var loc = "mfrnotificationmain.do?uAction=reload&notificationId="+j$("#notificationId").val();
						location.replace(loc);
					}
				}
			});
			return true;
		}
		else {
			return false;
		}
	}
	
	mfrNotification.approveRequest = function() {
		if (validateMfrNotification()) {
			document.genericForm.target='';
			$('uAction').value = 'approve';
	
			mfrNotification.showWait(messagesData.approving);
			var selections = getSelectedCategories();
			$("selectedCategories").value = String(selections);
			for (var i in selections) {
				if (isMaterialData(selections[i])) {
					var matGrid = window[selections[i]+"MaterialBean"];
					if (mfrNotification.selectedRow[selections[i]] > 0 && isItemData(selections[i])) {
						mfrNotification.renderAllRowsInGrid(window[selections[i]+"ItemBean"]);
						var itemData = takeSnapshot(selections[i]+"ItemResultDiv", function(tableDiv) { 
							return takeTableSnapshotWithNames(tableDiv, 
									window[selections[i]+"ItemConfig"],
									function(config, column) { return config[column].snap == "snap"; },
									function(row) { return true; }); 
						});
					
						var itemJsonData = {"item":itemData};
						setGridCellValue(matGrid, mfrNotification.selectedRow[selections[i]], "pageUploadCode", JSON.stringify(itemJsonData));
					}
					mfrNotification.renderAllRowsInGrid(matGrid);
					matGrid.parentFormOnSubmit();
				}
				else if (isItemData(selections[i])) {
					var iGrid = window[selections[i]+"ItemBean"];
					mfrNotification.renderAllRowsInGrid(iGrid);
					iGrid.parentFormOnSubmit();
				}
			}
			
			document.genericForm.submit();
			return true;
		}
		else {
			return false;
		}
	}
	
	function slstValidation() {
		var msg = "";
		for (var i in categorySelections) {
			if (categorySelections[i].selected && categorySelections[i].id == "category11") {
				msg += validateItem(itemGrid(), categorySelections[i]);
			}
		}
		if (msg.length == 0) {
			return true;
		}
		else {
			alert(msg);
			return false;
		}
	}
	
	function validateMfrNotification() {
		var msg = "";
		for (var i in categorySelections) {
			if (categorySelections[i].selected) {
				// if category is mfr driven
				if (categorySelections[i].id == "category1" || categorySelections[i].id == "category2" || categorySelections[i].id == "category3") {
					if ($v(categorySelections[i].id+"[0].mfgId").length== 0) {
						msg += categorySelections[i].desc+": "+messagesData.mfrrequired;
					}
					else {
						msg += validateMfr(categorySelections[i]);
					}
				}
				// if category is item driven
				else if (categorySelections[i].id == "category10" || categorySelections[i].id == "category11" || categorySelections[i].id == "category12") {
					var itemGrd = window[categorySelections[i].id+"ItemBean"];
					if (itemGrd) {
						if (itemGrd.getRowsNum() == 0) {
							msg += categorySelections[i].desc+": "+messagesData.itemrequired;
						}
						else {
							msg += validateItem(itemGrd, categorySelections[i]);
						}
					}
					else {
						msg += categorySelections[i].desc+": "+messagesData.itemrequired;
					}
				}
				// if category is material driven
				else {
					var matGrid = window[categorySelections[i].id+"MaterialBean"];
					var itemGrd = window[categorySelections[i].id+"ItemBean"];
					if (matGrid) {
						if (matGrid.getRowsNum() == 0) {
							msg += categorySelections[i].desc+": "+messagesData.materialrequired;
						}
						else {
							msg += validateMaterial(categorySelections[i], matGrid, itemGrd);
						}
					}
					else {
						msg += categorySelections[i].desc+": "+messagesData.materialrequired;
					}
				}
			}
		}
		if (msg.length == 0) {
			return true;
		}
		else {
			alert(msg);
			return false;
		}
	}
	
	function validateMfr(category) {
		var msg = "";
		var tooLongMsg = "";
		var mfgUrlLimit = 200;
		var contactLimit = 50;
		var emailLimit = 100;
		var phoneLimit = 40;
		
		if ($(category.id+"[0].mfgUrl")) {
			var mfgUrlLength = $v(category.id+"[0].mfgUrl").length;
			if (mfgUrlLength > mfgUrlLimit) {
				tooLongMsg += buildLimitMsg(category.desc, messagesData.mfgUrl, mfgUrlLength, mfgUrlLimit);
			}
		}
		
		if ($(category.id+"[0].phone")) {
			var phoneLength = $v(category.id+"[0].phone").length;
			if (phoneLength > phoneLimit) {
				tooLongMsg += buildLimitMsg(category.desc, messagesData.phone, phoneLength, phoneLimit);
			}
		}
		
		if ($(category.id+"[0].contact")) {
			var contactLength = $v(category.id+"[0].contact").length;
			if (contactLength > contactLimit) {
				tooLongMsg += buildLimitMsg(category.desc, messagesData.contact, contactLength, contactLimit);
			}
		}
		
		if ($(category.id+"[0].email")) {
			var emailLength = $v(category.id+"[0].email").length;
			if (emailLength > emailLimit) {
				tooLongMsg += buildLimitMsg(category.desc, messagesData.mfgUrl, emailLength, emailLimit);
			}
		}

		// if Mfr Acquisition
		if (category.id == "category1" && $v(category.id+"[0].acquiredMfrId").length == 0) {
			msg += category.desc+": "+messagesData.acquiredmfrrequired;
		}
		
		if (tooLongMsg.length > 0) {
			msg = messagesData.tooLong + "\n" + tooLongMsg;
		}
		
		return msg;
	}
	
	function validateMaterial(category, matGrid, itemGrd) {
		var msg = "";
		var tooLongMsg = "";
		var materialDescLimit = 250;
		var tradeNameLimit = 600;
		var productCodeLimit = 200;

		var mfgId = 0;
		for (var i = 1; i <= matGrid.getRowsNum(); i++) {
			var rowMfgId = gridCellValue(matGrid, i, "mfgId");
			if (mfgId == 0) {
				mfgId = rowMfgId;
			}
			else if (mfgId != rowMfgId && msg.length == 0) {
				msg += messagesData.multipleMfrError;
			}
			// if Rebranded(5), Product Code Change(7), Spec Change (8)
			if (matGrid && (category.id == "category5" || category.id == "category7" || category.id == "category8")) {
				var materialDescLength = gridCellValue(matGrid, i, "materialDesc").length;
				var tradeNameLength = gridCellValue(matGrid, i, "tradeName").length;
				var currentTradeNameLength = gridCellValue(matGrid, i, "currentTradeName").length;
				var productCodeLength = gridCellValue(matGrid, i, "productCode").length;
				var localeMaterialDescLength = gridCellValue(matGrid, i, "localeMaterialDesc").length;
				var localeTradeNameLength = gridCellValue(matGrid, i, "localeTradeName").length;
				
				if (materialDescLength > materialDescLimit) {
					tooLongMsg += buildLimitMsg2(category.desc, messagesData.materialDesc, gridCellValue(matGrid, i, "materialId"), materialDescLength, materialDescLimit);
				}
				if (tradeNameLength > tradeNameLimit) {
					tooLongMsg += buildLimitMsg2(category.desc, messagesData.tradeName, gridCellValue(matGrid, i, "materialId"), tradeNameLength, tradeNameLimit);
				}
				if (productCodeLength > productCodeLimit) {
					tooLongMsg += buildLimitMsg2(category.desc, messagesData.productCode, gridCellValue(matGrid, i, "materialId"), productCodeLength, productCodeLimit);
				}
				if (localeMaterialDescLength > materialDescLimit) {
					tooLongMsg += buildLimitMsg2(category.desc, messagesData.locale + " " + messagesData.materialDesc, gridCellValue(matGrid, i, "materialId") + " (" + gridCellValue(matGrid, i, "locale") + ")", localeMaterialDescLength, materialDescLimit);
				}
				if (localeTradeNameLength > tradeNameLimit) {
					tooLongMsg += buildLimitMsg2(category.desc, messagesData.locale + " " + messagesData.tradeName, gridCellValue(matGrid, i, "materialId")  + " (" + gridCellValue(matGrid, i, "locale") + ")", localeTradeNameLength, tradeNameLimit);
				}
				
				if (category.id == "category5" && materialDescLength == 0) {
					msg += gridCellValue(matGrid, i, "materialId") + ": " + messagesData.rebrandeddescrequired;
				}
			}
			
			if (itemGrd) {
				validateItem(itemGrd, category);
			}
		}
		
		if (tooLongMsg.length > 0) {
			msg = messagesData.tooLong + "\n" + tooLongMsg;
		}
		
		if (msg.length > 0) {
			msg = category.desc + ":\n" + msg;
		}
		
		return msg;
	}
	
	function validateItem(itemGrd, category) {
		var msg = "";
		var tooLongMsg = "";
		var mfgPartNoLimit = 30;
		var gradeLimit = 200;
		
		var categoryId = category?category.id:mfrNotification.selectedTab;
		var categoryDesc = category?category.desc:(parent.getTabName("mfrnotification"+$v("notificationId")));

		var mfgId = 0;
		if (itemGrd) {
			for (var i = 1; i <= itemGrd.getRowsNum(); i++) {
				var rowMfgId = gridCellValue(itemGrd, i, "mfgId");
				if (mfgId == 0) {
					mfgId = rowMfgId;
				}
				else if (mfgId != rowMfgId && msg.length == 0) {
					msg += messagesData.multipleMfrError;
				}
				// if Product Code Change(7), Spec Change (8)
				if (itemGrd && (categoryId == "category7" || categoryId == "category8" || categoryId == "category11")) {
					var mfgPartNoLength = gridCellValue(itemGrd, i, "mfgPartNo").length;
					var gradeLength = gridCellValue(itemGrd, i, "grade").length;
					var localeMfgPartNoLength = gridCellValue(itemGrd, i, "localeMfgPartNo").length;
					var localeGradeLength = gridCellValue(itemGrd, i, "localeGrade").length;
					
					if (mfgPartNoLength > mfgPartNoLimit) {
						tooLongMsg += buildLimitMsg2(categoryDesc, messagesData.mfgPartNo, gridCellValue(itemGrd, i, "itemId"), mfgPartNoLength, mfgPartNoLimit);
					}
					if (gradeLength > gradeLimit) {
						tooLongMsg += buildLimitMsg2(categoryDesc, messagesData.grade, gridCellValue(itemGrd, i, "itemId"), gradeLength, gradeLimit);
					}
					if (localeMfgPartNoLength > mfgPartNoLimit) {
						tooLongMsg += buildLimitMsg2(categoryDesc, messages.locale + " " + messagesData.mfgPartNo, gridCellValue(itemGrd, i, "itemId") + " (" + gridCellValue(matGrid, i, "locale") + ")", localeMfgPartNoLength, mfgPartNoLimit);
					}
					if (localeGradeLength > gradeLimit) {
						tooLongMsg += buildLimitMsg2(categoryDesc, messages.locale + " " + messagesData.grade, gridCellValue(itemGrd, i, "itemId") + " (" + gridCellValue(matGrid, i, "locale") + ")", localeGradeLength, gradeLimit);
					}
					if (categoryId == "category11") {
						msg += validateTemperatures(itemGrd, i);
					}
				}
			}
		}
		
		if (tooLongMsg.length > 0) {
			msg = messagesData.tooLong + "\n" + tooLongMsg;
		}
		
		if (msg.length > 0) {
			msg = categoryDesc + ":\n" + msg;
		}
		
		return msg;
	}
	
	function validateTemperatures(itemGrd, index) {
		var msg = "";
		
		var maxTemp = gridCellValue(itemGrd, index, "maxTemp");
		var currentMaxTemp = gridCellValue(itemGrd, index, "currentMaxTemp");
		var minTemp = gridCellValue(itemGrd, index, "minTemp");
		var currentMinTemp = gridCellValue(itemGrd, index, "currentMinTemp")
		var shelfLifeDays = gridCellValue(itemGrd, index, "shelfLifeDays");
		var localeMaxTemp = gridCellValue(itemGrd, index, "localeMaxTemp");
		var localeMinTemp = gridCellValue(itemGrd, index, "localeMinTemp");
		
		var maxTempNumVal = Number(maxTemp);
		var minTempNumVal = Number(minTemp);
		var shelfLifeNumVal = Number(shelfLifeDays);
		var localeMaxTempNumVal = Number(localeMaxTemp);
		var localeMinTempNumVal = Number(localeMinTemp);
		
		if (isNaN(maxTempNumVal)) {
			msg += messagesData.maxTemp + "; ";
		}
		if (isNaN(minTempNumVal)) {
			msg += messagesData.minTemp + "; ";
		}
		if (isNaN(localeMaxTempNumVal)) {
			msg += messagesData.locale + " " +messagesData.maxTemp + "; ";
		}
		if (isNaN(localeMinTempNumVal)) {
			msg += messagesData.locale + " " + messagesData.minTemp + "; ";
		}
		if (isNaN(shelfLifeNumVal)) {
			msg += messagesData.shelfLife + "; ";
		}
		
		if (msg.length > 0) {
			msg = messagesData.invalidValues + " " + msg + "\n";
		}
		
		if (maxTemp.length == 0 && minTemp.length == 0) {
			if (currentMaxTemp.length != 0 || currentMinTemp.length != 0) {
				msg += messagesData.fieldsRequired + " " + messagesData.minTemp + " " + messagesData.or + " " + messagesData.maxTemp+"\n";						
			}
		}
		else {
			if (maxTemp.length > 0 && minTemp.length > 0 && maxTempNumVal < minTempNumVal) {
				msg += messagesData.maxLessThanMin+"\n";
			}
			if (gridCellValue(itemGrd, index, "tempUnits").length == 0) {
				msg += messagesData.fieldsRequired + " " + messagesData.tempUnits+"\n";
			}
		}
		
		if (localeMaxTemp.length > 0 && localeMinTemp.length > 0 
				&& localeMaxTempNumVal < localeMinTempNumVal) {
			msg += messagesData.locale + " " + messagesData.maxLessThanMin+"\n";
		}
		
		if (msg.length > 0) {
			msg = gridCellValue(itemGrd, index, "itemId") + " " + msg;
		}
		
		return msg;
	}
	
	function buildLimitMsg(categoryDesc, field, actualSize, limit) {
		return field + " - "+actualSize+"; "+messagesData.limit+" "+limit+"\n";
	}
	
	function buildLimitMsg2(categoryDesc, field, fieldId, actualSize, limit) {
		var fullField = (fieldId.length == 0)?field:(field+" "+fieldId);
		return buildLimitMsg(categoryDesc, fullField, actualSize, limit);
	}
	
	function serializeData() {
		var pageUploadData = {};
		var selections = getSelectedCategories();
		for (var i in selections) {
			var mfrData = takeSnapshot(selections[i]+"MfrResultDiv");
			mfrData.comments = $v(selections[i]+"[0].comments");
			mfrNotification.renderAllRowsInGrid(window[selections[i]+"ItemBean"]);
			var itemData = takeSnapshot(selections[i]+"ItemResultDiv", function(tableDiv) { 
				return takeTableSnapshotWithNames(tableDiv, 
						window[selections[i]+"ItemConfig"],
						function(config, column) { return config[column].snap == "snap"; },
						function(row) { return true; }); 
			});
			if (mfrNotification.selectedRow[selections[i]] > 0 && isMaterialData(selections[i]) && isItemData(selections[i])) {
				var itemJsonData = {"item":itemData};
				setGridCellValue(window[selections[i]+"MaterialBean"], mfrNotification.selectedRow[selections[i]], "pageUploadCode", JSON.stringify(itemJsonData));
				itemData = {};
			}
			mfrNotification.renderAllRowsInGrid(window[selections[i]+"MaterialBean"]);
			var materialData = takeSnapshot(selections[i]+"MaterialResultDiv", function(tableDiv) { 
				return takeTableSnapshotWithNames(tableDiv, 
						window[selections[i]+"MaterialConfig"],
						function(config, column) { return config[column].snap == "snap"; },
						function(row) { return true; }); 
			});
			pageUploadData[selections[i]] = {
				"mfr": mfrData,
				"material": materialData,
				"item": itemData
			};
		}
		return pageUploadData;
	}
	
	mfrNotification.initializeGrids = function(selectedCategories) {
		for (var cat in selectedCategories) {
			var config = window[selectedCategories[cat].id+"MaterialGridConfig"];
			var data = window[selectedCategories[cat].id+"MaterialGridData"];
			if (typeof data != "undefined" && data.rows.length == 0) {
				try {
					j$("#"+selectedCategories[cat].id+"MaterialGrid").hide();
				} finally{
					j$("#"+selectedCategories[cat].id+"MaterialsNotFound").show();
				}
			}
			else if (config) {
				window["rowSpanMap"] = window[selectedCategories[cat].id+"MaterialRowSpanMap"];
				window["rowSpanClassMap"] = window[selectedCategories[cat].id+"MaterialRowSpanClassMap"];
				window["rowSpanCols"] = window["materialRowSpanCols"];
				window["rowSpanLvl2Map"] = null;
				window["rowSpanLvl2Cols"] = null;
				initGridWithGlobal(config);
			}
			config = window[selectedCategories[cat].id+"ItemGridConfig"];
			data = window[selectedCategories[cat].id+"ItemGridData"];
			if (typeof data != "undefined" && data.rows.length == 0) {
				try {
					j$("#"+selectedCategories[cat].id+"ItemGrid").hide();
				} finally{
					j$("#"+selectedCategories[cat].id+"ItemsNotFound").show();
				}
			}
			else if (config) {
				window["rowSpanMap"] = window[selectedCategories[cat].id+"ItemRowSpanMap"];
				window["rowSpanClassMap"] = window[selectedCategories[cat].id+"ItemRowSpanClassMap"];
				window["rowSpanCols"] = window["itemRowSpanCols"];
				window["rowSpanLvl2Map"] = window[selectedCategories[cat].id+"ItemRowSpanLvl2Map"];
				window["rowSpanLvl2Cols"] = window["itemRowSpanLvl2Cols"];
				initGridWithGlobal(config);
			}
		}
	}
	
	mfrNotification.openMaterial = function(row) {
		var iGrid = itemGrid();
		var invalidItemMsg = "";
		if (iGrid) {
			invalidItemMsg = validateItem(iGrid);
		}
		if (invalidItemMsg.length > 0) {
			materialGrid().setSelectedRow(mfrNotification.selectedRow[mfrNotification.selectedTab]);
			alert(invalidItemMsg);
		}
		else if (mfrNotification.selectedRow[mfrNotification.selectedTab] != row) {
			j$("#"+mfrNotification.selectedTab+"ItemResultDiv").hide();
			j$("#"+mfrNotification.selectedTab+"ItemTransitPage").show();
			if (mfrNotification.selectedRow[mfrNotification.selectedTab] > 0) {
				var itemData = {};
				if (iGrid) {
					mfrNotification.renderAllRowsInGrid(iGrid);
					itemData["item"] = takeSnapshot(mfrNotification.selectedTab+"ItemResultDiv", function(tableDiv) {
						return takeTableSnapshotWithNames(tableDiv, 
								window[mfrNotification.selectedTab+"ItemConfig"],
								function(config, column) { return config[column].snap == "snap"; },
								function(row) { return true; }); 
					});
				}
				setGridCellValue(materialGrid(), mfrNotification.selectedRow[mfrNotification.selectedTab], "pageUploadCode", JSON.stringify(itemData));
			}
			mfrNotification.selectedRow[mfrNotification.selectedTab] = row;
			var tbl = materialGrid();
			var materialId = gridCellValue(tbl, row, "materialId");
			var revisedData = gridCellValue(tbl, row, "pageUploadCode");
			j$.ajax({
				url:"mfrnotificationmain.do",
				type:"POST",
				cache:false,
				data:{"uAction": "selectMaterial", 
					"notificationId": j$("#notificationId").val(),
					"mfrReqCategoryId": j$("#mfrReqCategoryId").val(),
					"materialId": materialId, 
					"pageUploadCode": revisedData,
					"status": $v("requestApproved")=="Y"?"Approved":"NotApproved"
				},
				success: function(data) {
					var isError = false;
					try {
						var json = j$.parseJSON(data);
						if (json["tcmisError"]) {
							isError = true;
							alert(json["tcmisError"]);
						}
					} catch(e){}
					if ( ! isError) {
						j$("#"+mfrNotification.selectedTab+"ItemGridData").replaceWith(data);
	
						var config = window[mfrNotification.selectedTab+"ItemGridConfig"];
						var gridData = window[mfrNotification.selectedTab+"ItemGridData"];
						if (gridData.rows.length == 0) {
							j$("#"+mfrNotification.selectedTab+"ItemGrid").hide();
							j$("#"+mfrNotification.selectedTab+"ItemsNotFound").show();
						}
						else if (config) {
							window["rowSpanMap"] = window[mfrNotification.selectedTab+"ItemRowSpanMap"];
							window["rowSpanClassMap"] = window[mfrNotification.selectedTab+"ItemRowSpanClassMap"];
							window["rowSpanCols"] = window["itemRowSpanCols"];
							window["rowSpanLvl2Map"] = window[mfrNotification.selectedTab+"ItemRowSpanLvl2Map"];
							window["rowSpanLvl2Cols"] = window["itemRowSpanLvl2Cols"];
							initGridWithGlobal(config);
							j$("#"+mfrNotification.selectedTab+"ItemsNotFound").hide();
						}
						j$("#"+mfrNotification.selectedTab+"ItemResultDiv").show();
						$(mfrNotification.selectedTab+"ItemFooter").innerHTML = messagesData.recordsFound + ": " + $v(mfrNotification.selectedTab+"ItemCount");
					}
				},
				complete: function() {
					j$("#"+mfrNotification.selectedTab+"ItemTransitPage").hide();
				}
			});
		}
	}
	
	mfrNotification.lookupManufacturer = function(acquired) {
		var x = $v(mfrNotification.selectedTab+"[0].mfgId");
		var selectedMfr = $v(mfrNotification.selectedTab+"[0].mfgId");
		if (acquired) {
			mfrNotification.acquiredMfr = true;
			selectedMfr = $v(mfrNotification.selectedTab+"[0].acquiredMfrId");
		}
		var loc = "manufacturersearchmain.do?userAction=search&allowNew=Y";
		if (selectedMfr != null) {
			loc += "&searchArgument="+encodeURIComponent(selectedMfr);
		}
		mfrNotification.showWait(formatMessage(messagesData.waitingFor, messagesData.mfr));
		children[children.length] = openWinGeneric(loc,"manufacturersearch","600","500","yes","50","50","20","20","no");
	}
	
	mfrNotification.lookupMaterial = function() {
		var mfgId = $v(mfrNotification.selectedTab+"[0].mfrLookupSelection");
		var loc = "materialsearchmain.do?userAction=init";
		loc += "&mfgId=" + mfgId + "&multiselect=Y&allowNew=N";
		
		var matGrid = materialGrid();
		if (matGrid) {
			var matIds = "";
			for (var i = 1; i <= matGrid.getRowsNum(); i++) {
				if (i > 1) {
					matIds += ",";
				}
				matIds += gridCellValue(matGrid, i, "materialId");
			}
			if (matIds.length > 0) {
				loc += "&excludeMaterialIds="+encodeURIComponent(matIds);
			}
		}
		
		mfrNotification.showWait(formatMessage(messagesData.waitingFor, messagesData.material));
		children[children.length] = openWinGeneric(loc,"materialsearch","700","500","yes","50","50","20","20","no");
	}
	
	mfrNotification.lookupItem = function() {
		var mfgId = $v(mfrNotification.selectedTab+"[0].mfrLookupSelection");
		var loc = "itemsearchmain.do?uAction=itemLookup";
		loc += "&mfgId=" + mfgId;
		
		var itemGrd = itemGrid();
		if (itemGrd) {
			var itemIds = "";
			for (var i = 1; i <= itemGrd.getRowsNum(); i++) {
				if (i > 1) {
					itemIds += ",";
				}
				itemIds += gridCellValue(itemGrd, i, "itemId");
			}
			if (itemIds.length > 0) {
				loc += "&excludeItemIds="+encodeURIComponent(itemIds);
			}
		}
		
		mfrNotification.showWait(formatMessage(messagesData.waitingFor, messagesData.item));
		children[children.length] = openWinGeneric(loc,"itemlookup","700","500","yes","50","50","20","20","no");
	}
	
	mfrNotification.manufacturerChanged = function(newId, newDesc, shortName, phone, email, contact, notes, url) {
		if ($v(mfrNotification.selectedTab+"[0].itemSearchAvailable") === "true") {
			$(mfrNotification.selectedTab+"[0].mfrLookupSelection").value = newId;
			mfrNotification.lookupItem();
		}
		else if ($v(mfrNotification.selectedTab+"[0].materialSearchAvailable") === "true") {
			$(mfrNotification.selectedTab+"[0].mfrLookupSelection").value = newId;
			mfrNotification.lookupMaterial();
		}
		else if (mfrNotification.acquiredMfr) {
			var addIdx = selectedMaterialQueue.length;
			mfrNotification.acquiredMfr = false;
			$(mfrNotification.selectedTab+"[0].acquiredMfrId").value = newId;
			$(mfrNotification.selectedTab+"[0].acquiredMfrDesc").value = newDesc;
			j$.ajax({
				url:"mfrnotificationmain.do",
				cache:false,
				data:{"uAction": "loadMaterials",
					"notificationId": j$("#notificationId").val(),
					"mfrReqCategoryId": j$("#mfrReqCategoryId").val(),
					"acquiredMfrId": newId
				},
				success: function(data) {
					j$("#"+mfrNotification.selectedTab+"MaterialGridDataDiv").replaceWith(data);
				},
				complete: function() {
					var config = window[mfrNotification.selectedTab+"MaterialGridConfig"];
					var data = window[mfrNotification.selectedTab+"MaterialGridData"];
					if (data && data.rows.length == 0) {
						j$("#"+mfrNotification.selectedTab+"MaterialGrid").hide();
						j$("#"+mfrNotification.selectedTab+"MaterialsNotFound").show();
						j$("#"+mfrNotification.selectedTab+"MaterialResultDiv").show();
					}
					else if (config) {
						initGridWithGlobal(config);
						j$("#"+mfrNotification.selectedTab+"MaterialGrid").show();
						j$("#"+mfrNotification.selectedTab+"MaterialsNotFound").hide();
						j$("#"+mfrNotification.selectedTab+"MaterialResultDiv").show();
					}
					j$("#"+mfrNotification.selectedTab+"MfrResultDiv").show();
					mfrNotification.enableMaterialGrab($(mfrNotification.selectedTab+"[0].divisionAcquired").checked);
					var materialCount = j$("#"+mfrNotification.selectedTab+"MaterialCount");
					var itemCount = j$("#"+mfrNotification.selectedTab+"ItemCount");
					if (materialCount.length > 0) {
						j$("#"+mfrNotification.selectedTab+"MaterialFooter").html(messagesData.recordsFound + ": " + materialCount.val());
					}
					if (itemCount.length > 0) {
						j$("#"+mfrNotification.selectedTab+"ItemFooter").html(messagesData.recordsFound + ": " + itemCount.val());
					}
				}
			});
		}
		else {
			j$.ajax({
				url:"mfrnotificationmain.do",
				cache:false,
				data:{"uAction": "loadMfr", 
					"notificationId": j$("#notificationId").val(),
					"mfrReqCategoryId": j$("#mfrReqCategoryId").val(),
					"mfgId": newId
				},
				success: function(data) {
					j$("#"+mfrNotification.selectedTab+"MfrResultDiv").replaceWith(data);
				},
				complete: function() {
					var config = window[mfrNotification.selectedTab+"MaterialGridConfig"];
					var data = window[mfrNotification.selectedTab+"MaterialGridData"];
					if (data && data.rows.length == 0) {
						j$("#"+mfrNotification.selectedTab+"MaterialGrid").hide();
						j$("#"+mfrNotification.selectedTab+"MaterialsNotFound").show();
						j$("#"+mfrNotification.selectedTab+"MaterialResultDiv").show();
					}
					else if (config) {
						initGridWithGlobal(config);
						j$("#"+mfrNotification.selectedTab+"MaterialGrid").show();
						j$("#"+mfrNotification.selectedTab+"MaterialsNotFound").hide();
						j$("#"+mfrNotification.selectedTab+"MaterialResultDiv").show();
					}
					j$("#"+mfrNotification.selectedTab+"MfrResultDiv").show();
				}
			});
		}
	}
	
	mfrNotification.materialChanged = function(currentMaterial,currentMaterialDesc,currentTradeName,currentMfgId,productCode,customerOnlyMsds,newMaterial) {
		selectedMaterialQueue.unshift(currentMaterial);
		$(mfrNotification.selectedTab+"MaterialsToAdd").style["display"] = "block";
		$(mfrNotification.selectedTab+"[0].materialsToAdd").value = String(selectedMaterialQueue);
	}

	mfrNotification.finishMaterialLookup = function() {
		if (isMaterialData()) {
			if ($v(mfrNotification.selectedTab+"[0].materialsToAdd").length > 0) {
				document.genericForm.target='';
				document.getElementById('uAction').value = 'addMaterials';
				document.getElementById('pageUploadCode').value = JSON.stringify(serializeData());
				document.getElementById('selectedCategories').value = String(getSelectedCategories());
		
				document.genericForm.submit();
			}
		}
		else if (isItemData()) {
			if ($v(mfrNotification.selectedTab+"[0].materialsToAdd").length > 0) {
				document.genericForm.target='';
				document.getElementById('uAction').value = 'addItems';
				document.getElementById('pageUploadCode').value = JSON.stringify(serializeData());
				document.getElementById('selectedCategories').value = String(getSelectedCategories());
		
				document.genericForm.submit();
			}
		}
	}
		
	mfrNotification.itemChanged = function(currentItem) {
		selectedItemQueue.unshift(currentItem);
		$(mfrNotification.selectedTab+"ItemsToAdd").style["display"] = "block";
		$(mfrNotification.selectedTab+"[0].itemsToAdd").value = String(selectedItemQueue);
	}
	
	mfrNotification.finishItemLookup = function() {
		if (isItemData()) {
			if ($v(mfrNotification.selectedTab+"[0].itemsToAdd").length > 0) {
				document.genericForm.target='';
				document.getElementById('uAction').value = 'addItems';
				document.getElementById('pageUploadCode').value = JSON.stringify(serializeData());
				document.getElementById('selectedCategories').value = String(getSelectedCategories());
		
				document.genericForm.submit();
			}
		}
	}
	
	function materialGrid() {
		return window[mfrNotification.selectedTab+"MaterialBean"];
	}
	
	function itemGrid() {
		return window[mfrNotification.selectedTab+"ItemBean"];
	}
	
	mfrNotification.selectTab = function(tabId) {
		mfrNotification.selectedTab = tabId;
		mfrNotification.selectedRow[tabId] = 0;
		var idNumber = tabId.slice(8);
		$("mfrReqCategoryId").value = idNumber;
	}
	
	mfrNotification.enableMaterialGrab = function(enable) {
		var matGrid = materialGrid();
		if (matGrid) {
			var length = matGrid.getRowsNum();
			for (var i = 1; i <= length; i++) {
				matGrid.cellById(i, matGrid.getColIndexById("grab")).cell.children[0].disabled = ! enable;
			}
		}
	}
	
	mfrNotification.setSelected = function(tabId, selected) {
		for (var i in categorySelections) {
			if (categorySelections[i].id == tabId) {
				categorySelections[i].selected = selected;
				break;
			}
		}
	}
	
	mfrNotification.openUploadWindow = function() {
		var notificationId = $v("notificationId");
		var mfrReqCategoryId = $v("mfrReqCategoryId");
		var qcComplete = $v("requestApproved")=="Y"?$v("requestApproved"):$v("requestRejected");
		var loc = "mfrnotificationdocviewer.do?uAction=view";
		loc += "&notificationId=" + notificationId + "&mfrReqCategoryId=" + mfrReqCategoryId + "&requestApproved="+qcComplete;
		mfrNotification.showWait(formatMessage(messagesData.waitingFor, messagesData.uploaddocument));
		children[children.length] = openWinGeneric(loc,"mfrNotDocumentUpload","900","320","yes","50","50","20","20","no");
	}
	
	function getSelectedCategories() {
		var selections = [];
		for (var i in categorySelections) {
			if (categorySelections[i].selected) {
				selections[selections.length] = categorySelections[i].id;
			}
		}
		return selections;
	}
	
	function isMfrData(tab) {
		if (tab == null) {
			tab = mfrNotification.selectedTab;
		}
		return $(tab+"MfrResultDiv") != null;
	}
	
	function isMaterialData(tab) {
		if (tab == null) {
			tab = mfrNotification.selectedTab;
		}
		return $(tab+"MaterialResultDiv") != null;
	}
	
	function isItemData(tab) {
		if (tab == null) {
			tab = mfrNotification.selectedTab;
		}
		return $(tab+"ItemResultDiv") != null;
	}
	
	mfrNotification.deleteMaterials = function() {
		document.genericForm.target='';
		$('uAction').value = 'deleteMaterials';
		$('pageUploadCode').value = JSON.stringify(serializeData());
		$('selectedCategories').value = String(getSelectedCategories());

		mfrNotification.showWait(messagesData.deleting);

		// Call this function to send the data from grid back to the server
		var theGrid = materialGrid();
		if (theGrid != null) {
			theGrid.parentFormOnSubmit();
		}

		document.genericForm.submit();
	}
	
	mfrNotification.deleteItems = function() {
		document.genericForm.target='';
		$('uAction').value = 'deleteItems';
		$('pageUploadCode').value = JSON.stringify(serializeData());
		$('selectedCategories').value = String(getSelectedCategories());

		mfrNotification.showWait(messagesData.deleting);

		// Call this function to send the data from grid back to the server
		var theGrid = itemGrid();
		if (theGrid != null) {
			theGrid.parentFormOnSubmit();
		}

		document.genericForm.submit();
	}
	
	mfrNotification.rejectNotification = function() {
		document.genericForm.target = '';
		$("uAction").value = "reject"
		document.genericForm.submit(messagesData.rejecting);
	}
	
	mfrNotification.deleteNotification = function() {
		j$.ajax({
			url:"mfrnotificationmain.do",
			cache:false,
			data:{"uAction": "deleteRequest", 
				"notificationId": j$("#notificationId").val()
			},
			success: function(data) {
				parent.closeTab();
			}
		});
	}
	
	mfrNotification.renderAllRowsInGrid = function(theGrid) {
		if (theGrid) {
			for (var i = 1; i <= theGrid.getRowsNum(); i++) {
				theGrid.haasRenderRow(i);
			}
		}
	}
	
}(window.mfrNotification = window.mfrNotification || {}));

(function(JSON) {
	JSON.stringify = JSON.stringify || function(jsObject) {
		var str = "";
		if (typeof jsObject == "undefined" || jsObject == null) {
			str = str + "\"null\"";
		}
		else if (jsObject instanceof Array) {
			var arrayStr = "";
			for (var idx in jsObject) {
				if (arrayStr.length == 0) {
					arrayStr = arrayStr + "[";
				}
				else {
					arrayStr = arrayStr + ", ";
				}
				arrayStr = arrayStr + JSON.stringify(jsObject[idx]);
			}
			if (arrayStr.length == 0) {
				arrayStr = "[";
			}
			str = str + arrayStr + "]";
		}
		else if (jsObject instanceof Object && ! (jsObject instanceof Function)) {
			var objStr = "";
			for (var key in jsObject) {
				if (objStr.length == 0) {
					objStr = "{";
				}
				else {
					objStr = objStr + ", ";
				}
				objStr = objStr + "\""+key+"\":" + JSON.stringify(jsObject[key]);
			}
			if (objStr.length == 0) {
				objStr = "{";
			}
			str = str + objStr + "}";
		}
		else if (jsObject.length == 0 || isNaN(jsObject)) {
			str = str + "\""+String(jsObject)
			.replace(/\r\n/g, "\\n")
			.replace(/\\\"|\"/g, function(quote) {
				if (quote == "\\\"") {
					return "\\\\\\\"";
				}
				else {
					return "\\\"";
				}
			})+"\"";
		}
		else {
			var jsString = String(jsObject).replace(/\W/g,"");
			if (jsString.replace(/\W/g, "").length == 0) {
				str = str + "\""+jsString+"\"";
			}
			else {
				str = str + jsObject;
			}
		}
		return str;
	}
}(window.JSON = window.JSON || {}));

manufacturerChanged = mfrNotification.manufacturerChanged;
materialChanged = mfrNotification.materialChanged;
itemChanged = mfrNotification.itemChanged;
stopShowingWait = mfrNotification.stopWaiting;
finishMaterialLookup = mfrNotification.finishMaterialLookup;
finishItemLookup = mfrNotification.finishItemLookup;
closeAllChildren = function() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren(); 
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array(); 
};