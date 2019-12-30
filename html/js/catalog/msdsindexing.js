(function(msdsIndex) {
	msdsIndex.component = 0;
	msdsIndex.showErrorMessage = false;
	msdsIndex.totalComponents = 1;
	msdsIndex.territoryList = [];
	var outofscope = "OUT OF SCOPE";
	var cannotfulfill = "CANNOT FULFILL";
	var msdsAuthoringType = "Authoring";
	var awaitingSave = false;
	var awaitingApproval = false;
	var awaitingRejection = false;
	var rejectionReason = "";
	var maxComponent = 1;
	var componentsLoaded = 1;
	var selectedRowId = 1;
	var componentRowId = [];
	var transitReload = false;
	var isKitMfg = false;
	var isKitSdsNumber = false;
	var saveCustomerOverride = [];
	var densityUnitConversion = {
		"":NaN,
		"g/L":0.001,
		"g/cm3":1,
		"g/mL":1,
		"kg/L":1,
		"kg/m3":0.001,
		"lb/ft3":0.016082509,
		"lb/gal":0.120306,
		"lb/in3":27.79058538,
		"kg/dm3":1
	};

	var vocUnitConversion = {
		"":NaN,
		"g/L":0.001,
		"lb/gal":0.12017858162272
	};
	msdsIndex.sdsNotRequired = {
		"da_DK": "https://www.tcmis.com/MSDS/not_required_da_DK.pdf",
		"de_DE": "https://www.tcmis.com/MSDS/nicht_erforderlich.html",
		"en_US": "https://www.tcmis.com/MSDS/not_required.html",
		"es_ES": "https://www.tcmis.com/MSDS/not_required_es_ES.pdf",
		"fr_FR": "https://www.tcmis.com/MSDS/not_required_fr_FR.pdf",
		"it_IT": "https://www.tcmis.com/MSDS/not_required_it_IT.pdf",
		"nl_NL": "https://www.tcmis.com/MSDS/not_required_nl_NL.pdf",
		"no_NO": "https://www.tcmis.com/MSDS/not_required_no_NO.pdf",
		"pl_PL": "https://www.tcmis.com/MSDS/not_required_pl_PL.pdf",
		"pt_PT": "https://www.tcmis.com/MSDS/not_required_pt_PT.pdf",
		"sv_SE": "https://www.tcmis.com/MSDS/not_required_sv_SE.pdf",
		"tr_TR": "https://www.tcmis.com/MSDS/not_required_tr_TR.pdf"
	};


	/* Page Functions */


	msdsIndex.doNothing = function() {
		return true;
	}

	msdsIndex.loadit = function() {
		msdsIndex.resizeMainPage();
		msdsIndex.showError();

		if ($("msdsDetailDiv"+msdsIndex.component).children.length > 0) {
			$("msdsDetailDiv"+msdsIndex.component).style.display = "block";
			if ($("compositionData"+msdsIndex.component)) {
				msdsIndex.initializeCompositionGrids();
			}
		}

		setupAuthorAutocomplete();
		msdsIndex.countryChanged();
		toggleBlocks();
		toggleQcBlocks();
		storeSelectOldVal(msdsIndex.getMSDSfield("idOnly"));

		bindCoChange();

		if (msdsIndex.totalComponents == 1) {
			if ( ! isSdsRequired()) {
				disableAuthorInput(true);
			}
		}
		else {
			if ($("itemLink0") != null) {
				$("itemLink0").style.display = "block";
			}
			// start at 1, not 0; first component is already loaded
			loadItem(1);
		}
	}

	msdsIndex.calendarDateChangedCallBack = function(componentId) {
		j$.ajax({
			url:"msdsindexingresults.do",
			cache:false,
			dataType:"json",
			data:{uAction: "newRevision",
				materialId: msdsIndex.getMSDSfieldValue("materialIdSearch"),
				revisionDate: msdsIndex.getMSDSfieldValue("revisionDate"),
				qLocaleCode: sdsLocaleCode(),
				sdsRequired: msdsIndex.getMSDSfieldValue("sdsRequired")
			},
			success: function(data) {
				var nrContent = data.content;
				var nrLocale = data.imageLocaleCode;
				if (nrContent.length > 0) {
					msdsIndex.getMSDSfield("content").value = nrContent;
					msdsIndex.getMSDSfield("imageLocaleCode").value = nrLocale;
					msdsIndex.getMSDSfield("revisionDate").value = data.revisionDate;
					var currentDate = data.revisionDate.split(" ");
					var localeDisplay = j$(msdsIndex.getMSDSfield("materialLocaleProperties")).children("legend").html();
					var revisionDateDisplay = currentDate[0];
					if (localeDisplay != null && localeDisplay.trim().length > 0) {
						revisionDateDisplay += " - " + localeDisplay;
					}
					msdsIndex.getMSDSfield("revisionDateDisp").value = revisionDateDisplay;
				}
				msdsIndex.setNewRevisionDate(data.revisionDate, msdsIndex.getMSDSfieldValue("revisionDate"));
			}
		});
	}

	msdsIndex.resizeMainPage = function() {
		var wt = window.innerWidth
			|| document.documentElement.clientWidth
			|| document.body.clientWidth;
		resizeUpdate(wt);
		matchPropertyColumns();
	}

	msdsIndex.getMSDSfield = function(fieldName, isCo) {
		var prefix = isCo?"co["+msdsIndex.component+"].":"msds["+msdsIndex.component+"].";
		return $(prefix + fieldName);
	}

	msdsIndex.getMSDSfieldValue = function(fieldName, isCo) {
		var value = "";
		var field = msdsIndex.getMSDSfield(fieldName, isCo);
		if (field) {
			value = field.value;
		}
		return value.trim();
	}

	msdsIndex.viewProblemHistory = function() {
		var qId = $v("qId");
		var loc = "problemqueuereason.do?uAction=showHistory&qId="+qId;
		children[children.length] = openWinGeneric(loc,"problemHistory","800","700","yes","50","50","20","20","no");
	}

	msdsIndex.createViewContactLog = function() {
		var materialId = msdsIndex.getMSDSfieldValue("materialId");
		var revisionDate = msdsIndex.getMSDSfieldValue("revisionDate");
		var loc = "contactlog.do?uAction=view&materialId="+materialId;
		loc += "&revisionDate=" + encodeURIComponent(revisionDate);
		msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.material));
		children[children.length] = openWinGeneric(loc,"contactlog","800","700","yes","50","50","20","20","no");
	}

	msdsIndex.createViewWorkQueueContactLog = function() {
		var qId = $v("qId");
		var loc = "vendorcontactlogmain.do?uAction=search&qId="+qId+"&qItemStatus="+$v("catalogQueueRowStatus");
		//msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.material));
		children[children.length] = openWinGeneric(loc,"contactlog","800","700","yes","50","50","20","20","no");
	}

	msdsIndex.authorSds = function() {
		var materialId = msdsIndex.getMSDSfieldValue("materialId");
		var revisionDate = msdsIndex.getMSDSfieldValue("revisionDate");

		var loc = "sdsauthoringselectionmain.do?uAction=select&materialId="+materialId+"&revisionDate=" + encodeURIComponent(revisionDate);
		//msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.locale));
		children[children.length] = openWinGeneric(loc, "authorSds","400","550","yes","50","50","20","20","no");
	}

	msdsIndex.showMsdsDocuments = function(companyId) {
		var materialId = msdsIndex.getMSDSfieldValue("materialId");
		if (materialId) {
			var catalogQueueRowStatus = $v("catalogQueueRowStatus")
			var readonlyStatus = ("Pending Approval" == catalogQueueRowStatus
				|| "Approved" == catalogQueueRowStatus
				|| "Closed" == catalogQueueRowStatus)?"Y":"N";
			var url = 'showmsdsdocuments.do?showDocuments=Yes&materialId='+materialId+"&readonly="+readonlyStatus;
			if (companyId) {
				url = url + '&companyId='+msdsIndex.getMSDSfieldValue("companyId");
			}
			url += '&documentTypeSource=Catalog';
			children[children.length] = openWinGeneric(url,"showMsdsDocuments","800","350",'yes' );
		}
	}

	msdsIndex.showCoMsdsDocuments = function() {
		children[children.length] = openWinGeneric('showmsdsdocuments.do?showDocuments=Yes&materialId='+ msdsIndex.getMSDSfieldValue("materialId")+'&companyId='+msdsIndex.getMSDSfieldValue("companyId")+'&documentTypeSource=Catalog',"showMsdsDocuments","800","350",'yes' );
	}

	msdsIndex.loadParent = function() {
		var qId = $v("qId");
		if (qId.length > 0 && $v("catalogQueueRowStatus") != "Pending Approval") {
			try {
				parent.catAddVendorQueueframe.resultFrame.doRefresh("ChemTask"+qId);
			} catch(e) {
				try {
					parent.catalogAddProcessframe.resultFrame.doRefresh("ChemTask"+qId);
				} catch (ex) {
					parent.opener.doRefresh("ChemTask"+qId);
					window.close();
				}
			}
		}
		else {
			parent.catalogAddProcessframe.resultFrame.doRefresh("ChemReq"+$v("requestId")+"-"+$v("lineItem"));
		}
	}

	msdsIndex.showError = function() {
		if (msdsIndex.showErrorMessage) {
			var errorMessagesArea = $("errorMessagesArea");
			errorMessagesArea.style.display="";

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
				// errorWin.button("close").hide();
				errorWin.setModal(true);
			}else{
				// just show
				errorWin.setModal(true);
				dhxWins.window("errorMessagesArea").show();
			}
		}
	}

	msdsIndex.reportProblem = function(reAssignTo) {
		var url = 'problemqueuereason.do?uAction=open&qId='
			+msdsIndex.getMSDSfieldValue("qId")
			+"&task="+$v("catalogQueueRowTask")
			+"&status="+$v("catalogQueueRowStatus");
		if (reAssignTo)
			url += "&reAssignTo=" + reAssignTo;
		children[children.length] = openWinGeneric(url,"reportProblem","400","300",'yes');
	}

	msdsIndex.problemReported = function() {
		msdsIndex.loadParent();
	}

	msdsIndex.openRevisionDateCalendar = function(index) {
		var revDateSearch =  msdsIndex.getMSDSfield("revisionDateSearch");
		if(revDateSearch == null || revDateSearch.value.trim().length == 0 || confirm(messagesData.changes)) {
			var msdsId = msdsIndex.getMSDSfieldValue("msdsId");

			var today = new Date();
			getCalendar(document.getElementById('msds['+index+'].revisionDate'), null, null, null, $v("todaysDate"));
			msdsIndex.getMSDSfield("content").value="";
		}
	}

	function matchPropertyColumns() {
		var prefix = "msds\\["+msdsIndex.component+"\\]\\.";
		var coPrefix = "co\\["+msdsIndex.component+"\\]\\.";

		var sdsHeight = j$("#"+prefix+"msdsfields").height();
		j$("#"+coPrefix+"msdsfields").height(sdsHeight);

		var coHeaderHeight = j$("#"+coPrefix+"propertiesColumn > .sdsColumnHeader").height();
		j$("#"+prefix+"propertiesColumn > .sdsColumnHeader").height(coHeaderHeight);
	}

	function resizeUpdate(wt) {
		var prefix = "msds\\["+msdsIndex.component+"\\]\\.";
		var coPrefix = "co\\["+msdsIndex.component+"\\]\\.";
		var columns = j$("#msdsContent"+msdsIndex.component+" > .column:not(#"+prefix+"qInfo), #msdsDetailDiv"+msdsIndex.component+" > .column");
		if (wt < 675) {
			columns.css("width", "96%");
		}
		else if (wt < 925) {
			columns.filter(".halfWidth").css("width", "48%");
			columns.filter(".thirdWidth").css("width", "32%");
			if (msdsIndex.getMSDSfield("propertiesColumn", true)) {
				var propertiesColumns = columns.filter("#"+prefix+"propertiesColumn"
					+ ", #"+coPrefix+"propertiesColumn");

				propertiesColumns.css("width", "48%");

				columns.filter("#"+prefix+"sdsIdentificationColumn").css("width", "96%");
			}
		}
		else {
			columns.filter(".thirdWidth").css("width","32%");
			columns.filter(".halfWidth").css("width", "48%");
		}
	}

	function resizeTextareas(section) {
		var parent = "";
		if (section && section.length > 0) {
			parent = section+" ";
		}
		j$("textarea[readonly]").each(function(index, element) {
			//setTimeout( function() {
			element.style.height = element.scrollHeight+"px";
			//});
		});
	}

	function hasMultipleComponents() {
		if (msdsIndex.multipleComponents === undefined) {
			msdsIndex.multipleComponents = j$("a [id^='itemLink']").length > 1;
		}
		return msdsIndex.multipleComponents;
	}

	function closeAllchildren() {
		try {
			for(var n=0;n<children.length;n++) {
				try {
					children[n].closeAllchildren();
				} catch(ex){}
				children[n].close();
			}
		} catch(ex){}
		children = new Array();
	}

	function disablePage() {
		j$("input:button:not([id$=viewFile], [id$=viewSrcFile])").attr("disabled","disabled");
		j$("input:checkbox").attr("readonly","readonly");
		j$("input:checkbox,input:radio").click(function() {
			return false;
		});
		j$("input:radio:not(:checked)").attr("readonly","readonly");
		var textInputs = j$("input:text,textarea");
		textInputs.attr("readonly","readonly");
		textInputs.addClass("msdsReadOnlyField optionTitleBold");
		j$("select").attr("disabled", "disabled");
		//j$("select option:not(:selected)").attr("disabled","disabled");
		j$(".pictogram").addClass("pictogram-disabled");

		j$("#msds\\["+msdsIndex.component+"\\]\\.msdsAuthorDesc").unbind();
	}

	function disableMfrInputs() {
		var prefix = "msds\\["+msdsIndex.component+"\\]\\.";
		var fields = j$("#"+prefix+"mfgfields input:text");
		fields.attr("readonly","readonly");
		fields.addClass("msdsReadOnlyField optionTitleBold");
	}

	function disableMaterialInputs() {
		var prefix = "msds\\["+msdsIndex.component+"\\]\\.";
		var fields = j$("#"+prefix+"materialfields input:text:not(#"+prefix+"materialLocaleProperties input,#"+prefix+"componentMsds)");
		fields.attr("readonly","readonly");
		fields.addClass("msdsReadOnlyField optionTitleBold");
	}

	function setupAuthorAutocomplete() {
		function log(event, data, formatted) {
			var prefix = "msds\\["+msdsIndex.component+"\\]\\.";
			if(j$('#'+prefix+'msdsAuthorId').val != null && formatted != null) {
				j$('#'+prefix+'msdsAuthorId').val(formatted.split("::")[0]);
				j$('#'+prefix+'msdsAuthorCountryAbbrev').val(formatted.split("::")[2]);
				var notes = formatted.split("::")[3];
				j$('#'+prefix+'msdsAuthorNotes').val(notes==null||notes=="null"?"":notes);
				msdsIndex.getMSDSfield("msdsAuthorDesc").className = "inputBox";
			}
		}

		for (var i = 0; i < msdsIndex.totalComponents; i++) {
			var authorDescId = "#msds\\["+i+"\\]\\.msdsAuthorDesc";
			j$(authorDescId).autocomplete("msdsauthorsearch.do",{
				extraParams: {msdsAuthorDesc: function() { return j$(authorDescId).val(); } },
				width: 400,
				max: 100,  // default value is 10
				cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
				scrollHeight: 300,
				formatItem: function(data, i, n, value) {
					var author = value.split("::");
					return  author[0]+" "+author[1];
				},
				formatResult: function(data, value) {
					return value.split("::")[1];
				}
			});

			j$(authorDescId).bind('keyup',(function(e) {
				var keyCode = (e.keyCode ? e.keyCode : e.which);

				if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
					invalidateAuthor();
			}));

			j$(authorDescId).result(log).next().click(function() {
				j$(this).prev().search();
			});
		}
	}

	function invalidateAuthor() {
		var msdsAuthorDesc  =  msdsIndex.getMSDSfield("msdsAuthorDesc");
		var msdsAuthorId  =  msdsIndex.getMSDSfield("msdsAuthorId");
		if (msdsAuthorDesc.value.length == 0) {
			msdsAuthorDesc.className = "inputBox";
		}else {
			msdsAuthorDesc.className = "inputBox red";
		}
		//set to empty
		msdsAuthorId.value = "";
	}


	/* End of Page Function */
	/* Update Functions */


	msdsIndex.submitSave = function() {
		if (msdsIndex.totalComponents == componentsLoaded) {
			awaitingSave = false;
			var intermediateApproval = $v("catalogQueueRowStatus")=="Pending QC";
			if (validateForm(intermediateApproval)) {
				msdsIndex.showWait(messagesData.saving);
				for (var index = 0; index < msdsIndex.totalComponents; index++) {
					if (window["compositionGrid"+index] != null) {
						window["compositionGrid"+index].parentFormOnSubmit();
					}
				}
				$('uAction').value = "update";
				document.genericForm.submit();
			}
		}
		else {
			awaitingSave = true;
			msdsIndex.showWait(messagesData.saving);
		}
		//return false;
	}

	msdsIndex.submitApprove = function(isExpressApproval) {
		if (msdsIndex.totalComponents == componentsLoaded) {
			awaitingApproval = false;
			if(validateForm(true)){
				msdsIndex.showWait(messagesData.approving);
				for (var index = 0; index < msdsIndex.totalComponents; index++) {
					if (window["compositionGrid"+index] != null) {
						window["compositionGrid" + index].parentFormOnSubmit();
					}
				}
				$('uAction').value = "update";
				$('approve').value = "true";
				//If flag is Y and the work queue is in 'Assigned' status, skip Pending QC and Pending Approval and goes directly to Approved
				$('expressApproval').value = isExpressApproval == "Y" ? "true" : "false";
				document.genericForm.submit();
			}
		}
		else {
			awaitingApproval = true;
			msdsIndex.showWait(messagesData.approving);
		}
		//return false;
	}

	msdsIndex.submitSourcingApprove = function() {
		if (msdsIndex.totalComponents == componentsLoaded) {
			awaitingApproval = false;
			if(validateSmallForm(true)){
				msdsIndex.showWait(messagesData.approving);
				for (var index = 0; index < msdsIndex.totalComponents; index++) {
					if (window["compositionGrid"+index] != null) {
						window["compositionGrid" + index].parentFormOnSubmit();
					}
				}
				$('uAction').value = "update";
				$('approve').value = "true";
				document.genericForm.submit();
			}
		}
		else {
			awaitingApproval = true;
			msdsIndex.showWait(messagesData.approving);
		}
	}

	function submitRejection() {
		if (rejectionReason == outofscope) {
			msdsIndex.submitRejectOutOfScope();
		}
		else if (rejectionReason == cannotfulfill) {
			msdsIndex.submitRejectCannotFulfill();
		}
	}

	msdsIndex.submitRejectOutOfScope = function() {
		if (msdsIndex.totalComponents == componentsLoaded) {
			awaitingRejection = false;
			msdsIndex.showWait(messagesData.rejecting);
			for (var index = 0; index < msdsIndex.totalComponents; index++) {
				if (window["compositionGrid"+index] != null) {
					window["compositionGrid" + index].parentFormOnSubmit();
				}
			}
			$('uAction').value = "rejectOutOfScope";
			document.genericForm.submit();
		}
		else {
			awaitingRejection = true;
			rejectionReason = outofscope;
			msdsIndex.showWait(messagesData.rejecting);
		}
		//return false;
	}

	msdsIndex.submitRejectCannotFulfill = function() {
		if ($v("rejectionComments").length == 0) {
			alert(messagesData.pleaseentervaluefor.replace('{0}',messagesData.rejectioncomments));
			return false;
		}
		if (msdsIndex.totalComponents == componentsLoaded) {
			awaitingRejection = false;
			msdsIndex.showWait(messagesData.rejecting);
			for (var index = 0; index < msdsIndex.totalComponents; index++) {
				if (window["compositionGrid"+index] != null) {
					window["compositionGrid" + index].parentFormOnSubmit();
				}
			}
			$('uAction').value = "rejectCannotFulfill";
			document.genericForm.submit();
		}
		else {
			awaitingRejection = true;
			rejectionReason = cannotfulfill;
			msdsIndex.showWait(messagesData.rejecting);
		}
		return true;
	}

	msdsIndex.toggleRejectionPopup = function() {
		j$("#rejectCommentPopup").toggle();
	}

	msdsIndex.submitForQC = function() {
		if (validateForm(true)) {
			msdsIndex.showWait(messagesData.saving);
			for (var index = 0; index < msdsIndex.totalComponents; index++) {
				if (window["compositionGrid"+index] != null) {
					window["compositionGrid"+index].parentFormOnSubmit();
				}
			}
			$('uAction').value = "update";
			$('submitForQc').value = "true";
			document.genericForm.submit();
		}
	}

	msdsIndex.uploadMsds = function(authored) {
		msdsAuthoringType = authored?"Authoring":"Sourcing";

		var date = msdsIndex.getMSDSfieldValue("revisionDate");
		if (date == "NEW") {
			alert(messagesData.validvalues +"\n"+ messagesData.errorRevDate.replace("{0}",messagesData.revDate));
		}
		else if (date.trim().length > 0){
			msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.uploadmsdsfile));
			var calendarDate = date && new Date(Date.parse(date.replace(/-/g, " ")));
			//month+date+year+_+hours+minutes
			var suffix = padNumbers(calendarDate.getMonth()+1,2) +
				padNumbers(calendarDate.getDate(),2) +
				padNumbers(calendarDate.getYear(),2) + "_" +
				padNumbers(calendarDate.getHours(),2) +
				padNumbers(calendarDate.getMinutes(),2);
			var fileName = msdsIndex.getMSDSfieldValue("materialId") + "_" + suffix;
			var queueLocale = $v("qLocaleCode");
			var localeLocked = (queueLocale.length > 0)?"Y":"N";
			var url = 'uploadfile.do?fileName='+fileName+"&modulePath=MSDS&localeLocked="+localeLocked+"&locale="+queueLocale;
			children[children.length] = openWinGeneric(url,"uploadfile",500,200,'yes' );
		}
		else {
			alert(messagesData.validvalues +"\n"+ messagesData.errorRevDate.replace("{0}",messagesData.revDate));
		}
	}

	msdsIndex.updateContent = function(file,imageLocaleCode, localeDisplay) {
		msdsIndex.getMSDSfield("content").value = file;
		msdsIndex.getMSDSfield("idOnly").disabled = false;
		msdsIndex.getMSDSfield("imageLocaleCode").value = imageLocaleCode;
		var revDateDisp = msdsIndex.getMSDSfield("revisionDateDisp");
		revDateDisp.value = revDateDisp.value.split(" - ")[0] + " - "+ localeDisplay;

		var revDateSearch = msdsIndex.getMSDSfield("revisionDateSearch");
		if (revDateSearch && revDateSearch.options) {
			revDateSearch.options[revDateSearch.selectedIndex].text = revDateDisp.value;
		}

		var authoringTypeField = msdsIndex.getMSDSfield("authoringType");
		if (authoringTypeField) {
			authoringTypeField.value = msdsAuthoringType;
		}

		if (imageLocaleCode == "en_US") {
			j$(msdsIndex.getMSDSfield("materialLocaleProperties")).hide();
		}
		else {
			j$(msdsIndex.getMSDSfield("materialLocaleProperties")).children("legend").html(localeDisplay);
			j$(msdsIndex.getMSDSfield("materialLocaleProperties")).show();
		}
	}

	function revisionDateOnly() {
		msdsIndex.getMSDSfieldValue("revisionDateDisp");
	}

	// helper function
	function padNumbers(num, length) {
		var paddedNum = num.toString();
		if (paddedNum.length > length) {
			paddedNum = paddedNum.substring(paddedNum.length-length);
		}
		else if (paddedNum.length < length){
			var zeroString = Math.pow(10,length-paddedNum.length).toString().substring(1);
			paddedNum = zeroString+paddedNum;
		}
		return paddedNum;
	}


	/* End of Update Function */
	/* Validation */


	msdsIndex.limitText = function(limitField, limitNum) {
		if(limitField.value.replace(/%[A-F\d]{2,6}/g, 'U').length > limitNum){
			alert(messagesData.maxinputlength);
		}
	}

	msdsIndex.checkDataEntryComplete = function() {
		var physicalState = msdsIndex.getMSDSfield("physicalState").value != "";
		var flashPointDetect = msdsIndex.getMSDSfield("flashPointDetect").value != "";
		var vaporPressureDetect = msdsIndex.getMSDSfield("vaporPressureDetect").value != "";
		var dataEntryComplete = msdsIndex.getMSDSfield("dataEntryComplete");

		if(physicalState && flashPointDetect && vaporPressureDetect) {
			dataEntryComplete.checked = true;
			dataEntryComplete.value = "Y";
		}
		else {
			dataEntryComplete.checked = false;
			dataEntryComplete.value = "N";
		}

		if (msdsIndex.getMSDSfield("propertiesColumn", true) != null) {
			msdsIndex.checkCODataEntryComplete();
		}
	}

	msdsIndex.checkCODataEntryComplete = function() {
		var coHealth = msdsIndex.getMSDSfield("health",true).value != "";
		var coFlammability = msdsIndex.getMSDSfield("flammability",true).value != "";
		var coReactivity = msdsIndex.getMSDSfield("reactivity",true).value != "";
		var coHmisHealth = msdsIndex.getMSDSfield("hmisHealth",true).value != "";
		var coHmisFlammability = msdsIndex.getMSDSfield("hmisFlammability",true).value != "";
		var coHmisReactivity = msdsIndex.getMSDSfield("hmisReactivity",true).value != "";

		var dataEntryComplete = msdsIndex.getMSDSfield("dataEntryComplete").checked;
		var coDataEntryComplete = msdsIndex.getMSDSfield("dataEntryComplete",true);

		if(dataEntryComplete && coHealth && coFlammability && coReactivity && coHmisHealth && coHmisFlammability && coHmisReactivity) {
			coDataEntryComplete.checked = true;
			coDataEntryComplete.value = "Y";
		}
		else {
			coDataEntryComplete.checked = false;
			coDataEntryComplete.value = "N";
		}
	}

	// validates the Sourcing Form, which include only the Mfr, Material, and SDS sections
	function validateSmallForm(approve) {
		var storedComp = msdsIndex.component;
		for (var tab = 1; tab <= msdsIndex.totalComponents; tab++) {
			// switch component so that getMSDSfield works properly
			msdsIndex.component = tab-1;
			// do not attempt to do any validation on a component if it has not been loaded yet
			if ($("itemDiv"+msdsIndex.component) == null)
				continue;

			var message = "";

			message = validateRevision(message, approve);
			message = validateMaterialDesc(message, approve);

			if (message.length > 0) {
				msdsIndex.component = storedComp;
				var compIndicator = ((tab-1)!=msdsIndex.component)?messagesData.component+" "+tab+":\n":"";
				alert(compIndicator + message);
				return false;
			}
		}

		msdsIndex.component = storedComp;
		return true;
	}

	function validateRevision(message, requireFields) {
		var dateVal = msdsIndex.getMSDSfieldValue("revisionDate");
		var content = msdsIndex.getMSDSfieldValue("content");
		if (dateVal == null || dateVal.trim().length == 0) {
			if (requireFields) {
				message += messagesData.errorRevDate + '\n';
				message += messagesData.uploadmsdsfile+'\n';
			}
		}
		else {
			var date = new Date(Date.parse(dateVal.toString().replace(/-/g, " ")));
			if (isNaN(date.getTime())) {
				message += messagesData.errorRevDate + '\n';
			}
			else {
				if(content == null || content.length == 0)
					message += messagesData.uploadmsdsfile+'\n';
			}
		}

		var requestedLanguage = $v("qLocaleCode");
		var actualLanguage = msdsIndex.getMSDSfieldValue("imageLocaleCode");
		if (requireFields && isSdsRequired()) {
			var msdsAuthorId = msdsIndex.getMSDSfieldValue("msdsAuthorId");
			if (msdsAuthorId == null || msdsAuthorId.trim().length == 0)
				message += messagesData.pleaseentervaluefor.replace('{0}',messagesData.msdsauthor)+"\n";
		}

		if (isSdsRequired() && content != null && content.trim().length > 0) {
			if (requestedLanguage.length > 0 && requestedLanguage != actualLanguage) {
				message += messagesData.languagemismatch+'\n';
			}
		}
		return message;
	}

	function sdsLocaleCode() {
		var requestedLanguage = $v("qLocaleCode");
		var actualLanguage = msdsIndex.getMSDSfieldValue("imageLocaleCode");
		return requestedLanguage?requestedLanguage:actualLanguage;
	}

	function isSdsRequired() {
		var locale = sdsLocaleCode();
		if ( ! locale) {
			locale = "en_US";
		}
		var content = msdsIndex.getMSDSfieldValue("content");
		var localeContent = msdsIndex.sdsNotRequired[locale]?msdsIndex.sdsNotRequired[locale]:msdsIndex.sdsNotRequired["en_US"];
		return content !== localeContent;
	}

	function validateForm(approve) {
		var valid = false;
		if ($v("catalogQueueRowTask") == "SDS Indexing" || $v("catalogQueueRowStatus") === "" || $v("catalogQueueRowStatus") == 'Closed') {
			valid = validateFullForm(approve);
		}
		else {
			valid = validateSmallForm(approve);
		}
		return valid;
	}

	function validateFullForm(approve) {
		// save the currently selected component so we can restore it later
		var storedComp = msdsIndex.component;
		for (var tab = 1; tab <= msdsIndex.totalComponents; tab++) {
			// switch component so that getMSDSfield works properly
			msdsIndex.component = tab-1;
			// do not attempt to do any validation on a component if it has not been loaded yet
			if ($("itemDiv"+msdsIndex.component) == null)
				continue;

			var message = "";
			var idOnly = msdsIndex.getMSDSfieldValue("idOnly") == "Y";
			var comments = msdsIndex.getMSDSfieldValue("comments");
			var requireFields = (comments==null)?true:(comments.trim().length==0);
			if (approve) {
				requireFields = approve;
			}

			//if approving for MSDS Indexing then user cannot send data to Maxcom
			if ($v("approvalRole") == "MSDS Indexing" && msdsIndex.getMSDSfieldValue("idOnly") && msdsIndex.getMSDSfieldValue("idOnly") != "N" && approve) {
				alert(messagesData.idOnlyEqualN);
				return false;
			}
			//if approving for Custom Indexing or SDS QC then user cannot send data to Maxcom
			if (($v("approvalRole") == "Custom Indexing" || $v("approvalRole") == "SDS QC") && msdsIndex.getMSDSfieldValue("idOnly") == "Y" && approve) {
				alert(messagesData.invalidIdOnly);
				return false;
			}


			// Validate Kit data
			var kitTableVar = $('kitTable');

			if(tab == 1 && kitTableVar != null && typeof(kitTableVar) != 'undefined')
			{
				if (($v('mixturePhysicalState') == "solid") &&
					$v("mixtureVoc").trim().length == 0 &&
					$v("mixtureVocLwes").trim().length == 0) {
					$("mixtureVoc",isCo).value = "0";
					$("mixtureVocUnit",isCo).value = "g/L";
					$("mixtureVocSource",isCo).value = "composition";
					$("mixtureVocLwes",isCo).value = "0";
					$("mixtureVocLwesUnit",isCo).value = "g/L";
					$("mixtureVocLwesSource",isCo).value = "composition";
				}

				var voclwes = $v("mixtureVocLwes");
				var vocVal = $v("mixtureVoc");
				if ( ! isFloat(vocVal, true) || ! isEmpty(vocVal) && ! (vocVal * 1.0 > 0.0))
					message += formatMessage(messagesData.positiveError, messagesData.voc) + "\n";
				if ( ! isFloat($v("mixtureVocLower"), true))
					message += formatMessage(messagesData.floatError, messagesData.vocLower) + "\n";
				if ( ! isFloat($v("mixtureVocUpper"), true))
					message += formatMessage(messagesData.floatError, messagesData.vocUpper) + "\n";
				if(validateMixtureRange('mixtureVoc'))
					message += messagesData.range.replace('{0}',messagesData.voc) + '\n';
				if(validateMixtureUnits('mixtureVoc'))
					message += messagesData.nounit.replace('{0}',messagesData.voc) + '\n';

				if ( ! isFloat(voclwes, true) || ! isEmpty(voclwes) && ! (voclwes * 1.0 > 0.0))
					message += formatMessage(messagesData.positiveError, messagesData.vocLessH2oExempt) + "\n";
				if ( ! isFloat($v("mixtureVocLwesLower"), true))
					message += formatMessage(messagesData.floatError, messagesData.vocLessH2oExemptLower) + "\n";
				if ( ! isFloat($v("mixtureVocLwesUpper"), true))
					message += formatMessage(messagesData.floatError, messagesData.vocLessH2oExemptUpper) + "\n";
				if(validateMixtureRange('mixtureVocLwes'))
					message += messagesData.range.replace('{0}',messagesData.vocLessH2oExempt) + '\n';
				if(validateMixtureUnits('mixtureVocLwes'))
					message += messagesData.nounit.replace('{0}',messagesData.vocLessH2oExempt) + '\n';

				var vocUnit = $v("mixtureVocUnit");
				var voclwesUnit = $v("mixtureVocLwesUnit");

				if (vocUnit != "%(w/w)") {
					if (vocUnit == "lb/gal")
						vocVal = vocVal*vocUnitConversion["lb/gal"]/vocUnitConversion["g/L"];

					if (voclwesUnit == "lb/gal")
						voclwes = voclwes*vocUnitConversion["lb/gal"]/vocUnitConversion["g/L"];

					if (vocVal < 0 || vocVal > voclwes)
						message += formatMessage(messagesData.rangeError, messagesData.voc, 0.0, messagesData.vocLessH2oExempt) + '\n';
				}
				else {
					if (vocVal < 0 || vocVal > 100)
						formatMessage(messagesData.rangeError, messagesData.voc, 0, 100) + "\n";
				}

				if (approve) {
					if($v('mixtureDesc') == '' && $v("requireMixtureIndexing") == 'true')
						message += messagesData.validvalues + ' ' + messagesData.kitDescription + '\n';
					if($v('mixtureMfgId') == '' && $v("requireMixtureIndexing") == 'true')
						message += messagesData.validvalues + ' ' + messagesData.kitManufacturer + '\n';
					if($v('mixturePhysicalState') == '' && $v("requireMixtureIndexing") == 'true')
						message += messagesData.validvalues + ' ' + messagesData.kitPhysicalState + '\n';
				}
			}

			// validate composition table
			var compositionGrid = window["compositionGrid"+msdsIndex.component];
			var requiredRowInfo = false;
			var blankFound = false;
			var totalRows = compositionGrid.getRowsNum();
			var existingNames = [];
			for (var rowId = 1; rowId <= totalRows; rowId++) {
				// validate percents and cas numbers
				if( ! validateCasNumber(rowId) ||
					! msdsIndex.checkPercent(rowId, "percent") ||
					! msdsIndex.checkPercent(rowId, "percentLower") ||
					! msdsIndex.checkPercent(rowId, "percentUpper")) {
					return false;
				}

				var percentLower = getGridElement(rowId, "percentLower");
				var percentUpper = getGridElement(rowId, "percentUpper");
				if (percentLower != null && percentUpper != null && parseFloat(percentLower.value)>=parseFloat(percentUpper.value)) {
					alert(messagesData.composition+": "+formatMessage(messagesData.rangeError, messagesData.percentLower, 0, messagesData.percentUpper));
					return false;
				}

				// check for duplicate entries
				var currentName = gridCell(compositionGrid,rowId,"msdsChemicalName").getValue();
				if (currentName != null && currentName.length > 0) {
					if (arrayIndexOf(existingNames,currentName) >= 0) {
						getGridElement(rowId, "msdsChemicalName").focus();
						alert(formatMessage(messagesData.duplicate, currentName));
						return false;
					}
					existingNames.push(currentName);
				}

				// ensure there is at least one valid entry
				// there must be a CAS # and a msdsChemicalName
				if ((getGridElement(rowId, "casNumber").value.length > 0 &&
					getGridElement(rowId, "msdsChemicalName").value.length > 0) ||
					! requireFields) {
					if ( ! blankFound) {
						requiredRowInfo = true;
					}
					else {
						requiredRowInfo = false;
					}
				}
				else {
					if (getGridElement(rowId, "casNumber").value.length > 0 ||
						getGridElement(rowId, "msdsChemicalName").value.length > 0) {
						requiredRowInfo = false;
					}
					blankFound = true;
				}
			}
			var msdsNotRequired = ! isSdsRequired();
			if( ! idOnly && ! requiredRowInfo && ! msdsNotRequired )
				message += messagesData.comprow + '\n';

			// validate revision date
			message = validateRevision(message, requireFields);

			// if this is already a GHS Compliant Image, and it is a hazardous material (i.e. Non-hazardous is not checked), the following are required:
			//  1. GHS Company Name
			//  2. GHS Emergency Phone #
			//  3. GHS Address, at least line 1
			//  4. GHS City (Country cannot be null, State is not required because there are foreign countries with no states/territories)
			//  5. At least one hazard statement OR one precautionary statement
			// if Non-hazardous, none of the above are required
			if (msdsIndex.getMSDSfield("ghsCompliantImage").checked && ( ! msdsIndex.getMSDSfield("ghsHazard").checked)) {
				if ( ! msdsIndex.getMSDSfield("labelCompanyName").value)
					message += messagesData.pleaseentervaluefor.replace('{0}',messagesData.company)+'\n';
				if ( ! msdsIndex.getMSDSfield("labelPhone").value)
					message += messagesData.pleaseentervaluefor.replace('{0}',messagesData.emergencyNumber)+'\n';
				if ( ! msdsIndex.getMSDSfield("labelAddressLine1").value)
					message += messagesData.pleaseentervaluefor.replace('{0}',messagesData.addressLine1)+'\n';
				if ( ! msdsIndex.getMSDSfield("labelCity").value)
					message += messagesData.pleaseentervaluefor.replace('{0}',messagesData.city)+'\n';
				var selectedHazards = j$(msdsIndex.getMSDSfield("ghsHazards")).find("input[id$='selectHazard']:checked");
				var selectedPrecautions = j$(msdsIndex.getMSDSfield("ghsPrecautions")).find("input[id$='selectPrecaution']:checked");
				var selectedStmts = selectedHazards.add(selectedPrecautions);
				if (selectedStmts.length == 0)
					message += messagesData.atleastonerequired+" "+messagesData.hazardStatement+', '+messagesData.precautionaryStatement+'\n';
			}

			var sectionMsg = validateSections(msdsNotRequired, idOnly, requireFields);
			if (sectionMsg === false)
				return false;
			else
				message += sectionMsg;

			// Validate Supplemental
			if(encodeURI(msdsIndex.getMSDSfieldValue("vaporDensity").replace(/%[A-F\d]{2,6}/g, 'U')).length > 10)
				message += messagesData.maxinputlength + ': ' + messagesData.vaporDensity + '\n';
			if(encodeURI(msdsIndex.getMSDSfieldValue("evaporationRate").replace(/%[A-F\d]{2,6}/g, 'U')).length > 20)
				message += messagesData.maxinputlength + ': ' + messagesData.evaporationRate + '\n';
			if(encodeURI(msdsIndex.getMSDSfieldValue("remark").replace(/%[A-F\d]{2,6}/g, 'U')).length > 300)
				message += messagesData.maxinputlength + ': ' + messagesData.remark + '\n';
			if(encodeURI(msdsIndex.getMSDSfieldValue("altDataSource").replace(/%[A-F\d]{2,6}/g, 'U')).length > 200)
				message += messagesData.maxinputlength + ': ' + messagesData.altDatasource + '\n';


			if (msdsIndex.getMSDSfield("propertiesColumn", true) != null) {
				// Validate CO Data
				var coMessage = validateSections(msdsNotRequired, idOnly, requireFields, true);

				if ($v("allowMixture") == "true") {
					if( ! (msdsIndex.getMSDSfield('asMixedY').checked || msdsIndex.getMSDSfield('asMixedN').checked))
						coMessage += messagesData.pleaseselect.replace('{0}',messagesData.asMixed) + '\n';
				}

				if (coMessage.trim().length > 0) {
					message = message + messagesData.customerOverride + ":\n" + coMessage;
				}
			}

			if (message.length > 0) {
				msdsIndex.component = storedComp;
				var compIndicator = ((tab-1)!=msdsIndex.component)?messagesData.component+" "+tab+":\n":"";
				alert(compIndicator + message);
				return false;
			}
		}

		msdsIndex.component = storedComp;
		return true;
	}

	function validateApproveForm() {
		return validateForm(true);
	}

	function validateMaterialDesc(message, requireFields) {
		var mfr = msdsIndex.getMSDSfieldValue("mfgId");
		if ( ! (mfr == null || mfr.trim().length == 0)) {
			var mfrDesc = msdsIndex.getMSDSfieldValue("manufacturer");
			if (mfrDesc.trim().length == 0) {
				message += messagesData.mfrDescRequired + '\n';
			}
		}

		var material = msdsIndex.getMSDSfieldValue("materialId");
		if ( ! (material == null || material.trim().length == 0)) {
			var materialDesc = msdsIndex.getMSDSfieldValue("materialDesc");
			if (materialDesc.trim().length == 0) {
				message += messagesData.materialDescRequired + '\n';
			}
		}

		if (requireFields && j$(msdsIndex.getMSDSfield("materialLocaleProperties")).is(":visible")) {
			var localizedMaterialDesc = msdsIndex.getMSDSfield("localizedMaterialDesc");
			if (localizedMaterialDesc && localizedMaterialDesc.value.trim().length == 0) {
				message += messagesData.localizedMaterialDescRequired + '\n';
			}

			var localizedTradeName = msdsIndex.getMSDSfield("localizedMfgTradeName");
			if (localizedTradeName && localizedTradeName.value.trim().length == 0) {
				message += messagesData.localizedTradeNameRequired + '\n';
			}
		}
		return message;
	}

	// validates all fields under the
	// NFPA, HMIS, pH, Physical State, Density, Flashpoint, Boiling Point, Solids,
	// Specific Gravity, Vapor Pressure, VOC, and VOC Less Water and Exempt sections
	function validateSections(msdsNotRequired, idOnly, requireFields, isCo) {
		var message = "";

		// Validate Material Description
		if ( ! isCo) {
			message = validateMaterialDesc(message, requireFields);
		}

		// Validate Physical State
		var physicalState = msdsIndex.getMSDSfieldValue("physicalState",isCo);
		if (physicalState == '') {
			if(requireFields && ! isCo && ! idOnly)
				message += messagesData.pleaseselect.replace('{0}',messagesData.physicalState) + '\n';
			// if no CO physical state, use global
			else if(isCo)
				physicalState = msdsIndex.getMSDSfieldValue("physicalState");
		}

		// Validate NFPA and HMIS
		message += checkInteger("health", messagesData.nfpaHealth,0,4,null,isCo);
		message += checkInteger("flammability", messagesData.nfpaFlammability,0,4,null,isCo);
		message += checkInteger("reactivity", messagesData.nfpaReactivity,0,4,null,isCo);

		message += checkInteger("hmisHealth", messagesData.hmisHealth,0,4,'*',isCo);
		message += checkInteger("hmisFlammability", messagesData.hmisFlammability,0,4,null,isCo);
		message += checkInteger("hmisReactivity", messagesData.hmisReactivity,0,4,null,isCo);

		// Validate Specific Gravity
		if(msdsNotRequired && msdsIndex.getMSDSfieldValue("specificGravityLower").trim().length == 0) {
			msdsIndex.getMSDSfield("specificGravityLower").value = "1";
			msdsIndex.getMSDSfield("specificGravityLower").disabled = false;
			msdsIndex.getMSDSfield("specificGravitySource").value = "estimate";
			if (msdsIndex.getMSDSfieldValue("specificGravityDetect").trim().length == 0) {
				msdsIndex.getMSDSfield("specificGravityDetect").value = "=";
				msdsIndex.getMSDSfield("specificGravityBasisWater").checked = true;
			}
		}
		var specificGravityBasis = j$("input[name$='specificGravityBasis']:checked");
		var specificGravityBasisVal = null;
		if (specificGravityBasis != null && specificGravityBasis.length > 0) {
			var specificGravityBasisCo = null;
			if (isCo)
				specificGravityBasisCo = specificGravityBasis.filter("input[name^='co']").eq(0);
			if (specificGravityBasisCo == null || specificGravityBasisCo.length == 0)
				specificGravityBasis = specificGravityBasis.filter("input[name^='msds']").eq(0);
			else
				specificGravityBasis = specificGravityBasisCo;
			if (specificGravityBasis != null && specificGravityBasis.length > 0)
				specificGravityBasisVal = specificGravityBasis.eq(0).val();
		}

		if (requireFields && ! isCo && ! msdsNotRequired && ! idOnly && ! msdsIndex.getMSDSfield("specificGravityLower").disabled &&
			(specificGravityBasis == null || specificGravityBasis.length == 0))
			message += messagesData.pleaseselect.replace('{0}',messagesData.specificGravityBasis) + '\n';

		if( ! msdsIndex.getMSDSfield('specificGravityLower',isCo).disabled) {
			message += checkPositiveFloat("specificGravityLower", messagesData.specificGravity,isCo,false);
			if(specificGravityBasisVal == "W") {
				if(physicalState == "gas") {
					var msg = checkFloat("specificGravityLower", messagesData.specificGravity, 0.0, 0.1,isCo);
					if (msg.length > 0) {
						if (isCo)
							msg = messagesData.customerOverride + " - " + msg;
						var confirmSpecGrav = confirm(msg+"\n"+messagesData.wanttocontinue);
						if( ! confirmSpecGrav)
							return false;
					}
				}
				else if(physicalState == "liquid") {
					var msg = checkFloat("specificGravityLower", messagesData.specificGravity, 0.5, Number.POSITIVE_INFINITY,isCo);
					if (msg.length > 0) {
						if (isCo)
							msg = messagesData.customerOverride + " - " + msg;
						var confirmSpecGrav = confirm(msg+"\n"+messagesData.wanttocontinue);
						if ( ! confirmSpecGrav)
							return false;
					}
				}
			}
			if(physicalState == "solid") {
				var msg = checkFloat("specificGravityLower", messagesData.specificGravity, 0.0, 14.0,isCo);
				if (msg.length > 0) {
					if (isCo)
						msg = messagesData.customerOverride + " - " + msg;
					var confirmSpecGrav = confirm(msg+"\n"+messagesData.wanttocontinue);
					if( ! confirmSpecGrav)
						return false;
				}
			}
		}
		if( ! msdsIndex.getMSDSfield('specificGravityUpper',isCo).disabled)
			message += checkPositiveFloat("specificGravityUpper", messagesData.specificGravity,isCo,false);
		if(validateRange('specificGravity',isCo))
			message += messagesData.range.replace('{0}',messagesData.specificGravity) + '\n';

		// Validate Density
		if( ! msdsIndex.getMSDSfield('density',isCo).disabled)
			message += checkPositiveFloat("density", messagesData.density,isCo,false);
		if( ! msdsIndex.getMSDSfield('densityUpper',isCo).disabled)
			message += checkPositiveFloat("densityUpper", messagesData.density,isCo,false);
		if(validateRange('density',isCo))
			message += messagesData.range.replace('{0}',messagesData.density) + '\n';
		if(validateUnits('density',isCo))
			message += messagesData.nounit.replace('{0}',messagesData.density) + '\n';
		var densityUnits = msdsIndex.getMSDSfieldValue("densityUnit",isCo);
		if (densityUnits != "") {
			var msg = checkFloat("density", messagesData.density,0.0,22.65/densityUnitConversion[densityUnits],isCo);
			if (msg.length > 0) {
				if (isCo)
					msg = messagesData.customerOverride + " - " + msg;
				var conf = confirm(msg+"\n"+messagesData.wanttocontinue);
				if ( ! conf)
					return false;
			}
		}

		// Validate Flash Point
		if(requireFields && ! isCo && ! msdsNotRequired && !idOnly && msdsIndex.getMSDSfieldValue("flashPointDetect") == '')
			message += messagesData.pleaseselectfor.replace('{0}',messagesData.detect).replace('{1}',messagesData.flashPoint)  + '\n';
		else {
			if( ! msdsIndex.getMSDSfield('flashPointLower',isCo).disabled)
				message += checkSignedFloat("flashPointLower", messagesData.flashPoint);
			if( ! msdsIndex.getMSDSfield('flashPointUpper',isCo).disabled)
				message += checkSignedFloat("flashPointUpper", messagesData.flashPoint);
			if(validateRange('flashPoint',isCo))
				message += messagesData.range.replace('{0}',messagesData.flashPoint) + '\n';
			if(validateUnits('flashPoint',isCo))
				message += messagesData.nounit.replace('{0}',messagesData.flashPoint) + '\n';
		}

		// Validate Boiling Point
		if( ! msdsIndex.getMSDSfield('boilingPointLower',isCo).disabled)
			message += checkSignedFloat("boilingPointLower", messagesData.boilingPoint);
		if( ! msdsIndex.getMSDSfield('boilingPointUpper',isCo).disabled)
			message += checkSignedFloat("boilingPointUpper", messagesData.boilingPoint);
		if(validateRange('boilingPoint',isCo))
			message += messagesData.range.replace('{0}',messagesData.boilingPoint) + '\n';
		if(validateUnits('boilingPoint',isCo))
			message += messagesData.nounit.replace('{0}',messagesData.boilingPoint) + '\n';

		// Validate pH
		var ph = msdsIndex.getMSDSfield('ph',isCo);
		var phUpper = msdsIndex.getMSDSfield('phUpper',isCo);
		if( ! ph.disabled) {
			message += checkFloat("ph", messagesData.ph,0,14,isCo);
		}
		if( ! phUpper.disabled) {
			message += checkFloat("phUpper", messagesData.ph,0,14,isCo);
		}
		if(validateRange('ph',isCo))
			message += messagesData.range.replace('{0}',messagesData.ph) + '\n';
		if(encodeURI(msdsIndex.getMSDSfieldValue("phDetail",isCo).replace(/%[A-F\d]{2,6}/g, 'U')).length > 50)
			message += messagesData.maxinputlength + ': ' + messagesData.ph + ' ' + messagesData.detail + '\n';

		// Validate Vapor Pressure
		if(requireFields && ! isCo && ! msdsNotRequired && ! idOnly && msdsIndex.getMSDSfieldValue("vaporPressureDetect",isCo) == '')
			message += messagesData.pleaseselectfor.replace('{0}',messagesData.detect).replace('{1}',messagesData.vaporPressure)  + '\n';
		else {
			if( ! msdsIndex.getMSDSfield('vaporPressure',isCo).disabled)
				message += checkPositiveFloat("vaporPressure", messagesData.vaporPressure,isCo,true);
			if( ! msdsIndex.getMSDSfield('vaporPressureUpper',isCo).disabled)
				message += checkPositiveFloat("vaporPressureUpper", messagesData.vaporPressure,isCo,true);
			if( ! msdsIndex.getMSDSfield('vaporPressureTemp',isCo).disabled)
				message += checkFloat("vaporPressureTemp", messagesData.vaporPressureTemp,null,null,isCo);
			if(validateRange('vaporPressure',isCo))
				message += messagesData.range.replace('{0}',messagesData.vaporPressure) + '\n';
			if(validateUnits('vaporPressure',isCo))
				message += messagesData.nounit.replace('{0}',messagesData.vaporPressure) + '\n';
			if(validateUnits('vaporPressureTemp',isCo))
				message += messagesData.nounit.replace('{0}',messagesData.vaporPressureTemp) + '\n';
		}

		// Validate VOC
		if ((physicalState == "solid" || msdsNotRequired) &&
			msdsIndex.getMSDSfieldValue("voc").trim().length == 0 &&
			msdsIndex.getMSDSfieldValue("vocLessH2oExempt").trim().length == 0) {
			msdsIndex.getMSDSfield("voc",isCo).value = "0";
			msdsIndex.getMSDSfield("vocUnit",isCo).value = "g/L";
			msdsIndex.getMSDSfield("vocSource",isCo).value = "composition";
			msdsIndex.getMSDSfield("vocLessH2oExempt",isCo).value = "0";
			msdsIndex.getMSDSfield("vocLessH2oExemptUnit",isCo).value = "g/L";
			msdsIndex.getMSDSfield("vocLessH2oExemptSource",isCo).value = "composition";
		}

		var coCompare = false;
		var voclwes = msdsIndex.getMSDSfieldValue("vocLessH2oExempt",isCo);
		var voclwesUnit = msdsIndex.getMSDSfieldValue("vocLessH2oExemptUnit",isCo);
		if (isCo && (voclwes == null || voclwes.trim().length == 0)) {
			voclwes = msdsIndex.getMSDSfieldValue("vocLessH2oExempt");
			voclwesUnit = msdsIndex.getMSDSfieldValue("vocLessH2oExemptUnit");
		}
		else {
			coCompare = true;
		}
		var vocVal = msdsIndex.getMSDSfieldValue("voc", isCo);
		var vocUnit = msdsIndex.getMSDSfieldValue("vocUnit", isCo);
		var vocDensity = msdsIndex.getMSDSfieldValue("density", isCo);
		var vocDensityUnit = msdsIndex.getMSDSfieldValue("densityUnit",isCo);
		var vocSpecificGrav = msdsIndex.getMSDSfieldValue("specificGravityLower",isCo);
		if (isCo && (vocVal == null || vocVal.trim().length == 0)) {
			vocVal = msdsIndex.getMSDSfieldValue("voc");
			vocUnit = msdsIndex.getMSDSfieldValue("vocUnit");
			vocDensity = msdsIndex.getMSDSfieldValue("density");
			vocDensityUnit = msdsIndex.getMSDSfieldValue("densityUnit");
			vocSpecificGrav = msdsIndex.getMSDSfieldValue("specificGravityLower");
		}
		else {
			coCompare = true;
		}

		message += checkFloat("vocLower", messagesData.vocLower,null,null,isCo);
		message += checkFloat("vocUpper", messagesData.vocUpper,null,null,isCo);
		if(validateRange('voc',isCo))
			message += messagesData.range.replace('{0}',messagesData.voc) + '\n';
		if(validateUnits('voc',isCo))
			message += messagesData.nounit.replace('{0}',messagesData.voc) + '\n';
		if (vocUnit == "%(w/w)")
			message += checkFloat("voc", messagesData.voc,0.0,100.0,isCo);

		message += checkFloat("vocLessH2oExempt", messagesData.vocLessH2oExempt,null,null,isCo);
		message += checkFloat("vocLessH2oExemptLower", messagesData.vocLessH2oExemptLower,null,null,isCo);
		message += checkFloat("vocLessH2oExemptUpper", messagesData.vocLessH2oExemptUpper,null,null,isCo);
		if(validateRange('vocLessH2oExempt',isCo))
			message += messagesData.range.replace('{0}',messagesData.vocLessH2oExempt) + '\n';
		if(validateUnits('vocLessH2oExempt',isCo))
			message += messagesData.nounit.replace('{0}',messagesData.vocLessH2oExempt) + '\n';

		if (vocVal != null && vocVal.trim().length > 0 &&
			voclwes != null && voclwes.trim().length > 0 &&
			vocUnit.trim().length > 0 && voclwesUnit.trim().length > 0 &&
			(coCompare || ! isCo)) {
			if (vocUnit == voclwesUnit) {
				if (parseFloat(vocVal) > parseFloat(voclwes))
					message += formatMessage(messagesData.rangeError, messagesData.voc, 0.0, messagesData.vocLessH2oExempt) + '\n';
			}
			else {
				message = validateVOCUnits(vocVal, vocUnit, voclwes, voclwesUnit, vocDensity, vocDensityUnit, vocSpecificGrav, message);
			}
		}

		message = validateVOCVLessDensity(message,isCo);

		// Validate Solids
		message += checkFloat("solids", messagesData.solids,null,null,isCo);
		message += checkFloat("solidsLower", messagesData.solidsLower,null,null,isCo);
		message += checkFloat("solidsUpper", messagesData.solidsUpper,null,null,isCo);
		if(validateRange('solids',isCo))
			message += messagesData.range.replace('{0}',messagesData.solids) + '\n';
		if(validateUnits('solids',isCo))
			message += messagesData.nounit.replace('{0}',messagesData.solids) + '\n';
		var solidsUnits = msdsIndex.getMSDSfieldValue("solidsUnit",isCo);
		if (solidsUnits == "%(w/w)")
			message += checkFloat("solids", messagesData.solids,0.0,100.0,isCo);

		return message;
	}

	// precondition: voc, vocUnit, voclwes, and voclwesUnit are not null or empty
	function validateVOCUnits(voc, vocUnit, voclwes,voclwesUnit, vocDensity, vocDensityUnit, vocSpecificGrav, message) {
		if (vocUnit == "%(w/w)") {
			var specific = vocSpecificGrav;
			if (specific == null || specific.trim().length == 0) {
				specific = msdsIndex.getMSDSfieldValue("specificGravityLower");
			}
			if (specific == null || specific.trim().length == 0) {
				var density = vocDensity;
				if (density == null || density.trim().length == 0) {
					density = msdsIndex.getMSDSfieldValue("density");
				}
				if (density == null || density.trim().length == 0) {
					//console.log("Cannot convert units of VOC value");
					return message;
				}
				else {
					var densityUnit = vocDensityUnit;
					if (densityUnit == null || densityUnit.trim().length == 0) {
						densityUnit = msdsIndex.getMSDSfieldValue("densityUnit");
					}
					if (densityUnit == null || densityUnit.trim().length == 0) {
						//console.log("Cannot convert units of VOC value");
						return message;
					}
					else {
						// get density in lb/gal
						density = density*densityUnitConversion[densityUnit]/densityUnitConversion["lb/gal"];
						// get VOC in g/mL
						voc = vocUnitConversion["lb/gal"]*((density*voc)/100);
						// then convert to g/L
						voc = voc/vocUnitConversion["g/L"];
					}
				}
			}
			else {
				voc = ((specific*voc)/100)*1000;
			}
		}
		else if (vocUnit == "lb/gal") {
			voc = voc*vocUnitConversion["lb/gal"]/vocUnitConversion["g/L"];
		}

		if (voclwesUnit == "lb/gal") {
			voclwes = voclwes*vocUnitConversion["lb/gal"]/vocUnitConversion["g/L"];
		}

		if (parseFloat(voc) < 0 || parseFloat(voc) > parseFloat(voclwes))
			message += formatMessage(messagesData.rangeError, messagesData.voc, 0.0, messagesData.vocLessH2oExempt) + '\n';

		return message;
	}

	function validateVOCVLessDensity(message,isCo) {
		var vocUnit = msdsIndex.getMSDSfieldValue("vocUnit",isCo);
		var vocLessH2oExemptUnit = msdsIndex.getMSDSfieldValue("vocLessH2oExemptUnit",isCo);
		var densityUnit = null;
		var checkDensity = false;
		if(msdsIndex.getMSDSfield("densityUnit",isCo).disabled == false) {
			densityUnit = msdsIndex.getMSDSfieldValue("densityUnit",isCo);
			checkDensity = true;
		}
		if((vocUnit != ""  && vocLessH2oExemptUnit != "" && vocUnit == vocLessH2oExemptUnit) ||
			(checkDensity && densityUnit != "" && vocUnit != "" && vocUnit == densityUnit) ||
			(checkDensity && densityUnit != "" && vocLessH2oExemptUnit != "" && vocLessH2oExemptUnit == densityUnit)) {
			var vocLessH2oExempt = null;
			var voc = null;
			var density = null;

			if(checkDensity) {
				if(msdsIndex.getMSDSfieldValue("densityUpper",isCo) != "")
					density = msdsIndex.getMSDSfieldValue("densityUpper",isCo);
				else if(msdsIndex.getMSDSfieldValue("density",isCo) != "")
					density = msdsIndex.getMSDSfieldValue("density",isCo);
			}

			if(msdsIndex.getMSDSfieldValue("vocLessH2oExemptUpper",isCo) != "")
				vocLessH2oExempt = msdsIndex.getMSDSfieldValue("vocLessH2oExemptUpper",isCo);
			else if(msdsIndex.getMSDSfieldValue("vocLessH2oExempt",isCo) != "")
				vocLessH2oExempt = msdsIndex.getMSDSfieldValue("vocLessH2oExempt",isCo);
			else if(msdsIndex.getMSDSfieldValue("vocLessH2oExemptLower",isCo) != "")
				vocLessH2oExempt = msdsIndex.getMSDSfieldValue("vocLessH2oExemptLower",isCo);

			if(msdsIndex.getMSDSfieldValue("vocUpper",isCo) != "")
				voc = msdsIndex.getMSDSfieldValue("vocUpper",isCo);
			else if(msdsIndex.getMSDSfieldValue("voc",isCo) != "")
				voc = msdsIndex.getMSDSfieldValue("voc",isCo);
			else if(msdsIndex.getMSDSfieldValue("vocLower",isCo) != "")
				voc = msdsIndex.getMSDSfieldValue("vocLower",isCo);

			if(checkDensity && density != "") {
				var densityDetect = msdsIndex.getMSDSfieldValue("densityDetect",isCo);
				if(densityDetect == ">" || densityDetect == ">=" || densityDetect == "=" || densityDetect == ">>")
				{
					if(densityUnit == vocUnit && voc != "" && parseInt(density) < parseInt(voc))
						message += messagesData.compare.replace('{0}',messagesData.voc).replace('{1}',messagesData.density) + '\n';
					if(densityUnit == vocLessH2oExemptUnit && vocLessH2oExempt != "" && parseInt(density) < parseInt(vocLessH2oExempt) )
						message += messagesData.compare.replace('{0}',messagesData.vocLessH2oExempt).replace('{1}',messagesData.density) + '\n';
				}
			}

			if(vocUnit == vocLessH2oExemptUnit && vocLessH2oExempt != "" && voc != "" && parseInt(vocLessH2oExempt) < parseInt(voc))
				message += messagesData.compare.replace('{0}',messagesData.voc).replace('{1}',messagesData.vocLessH2oExempt) + '\n';
		}
		return message;
	}

	function validateMixtureRange(block) {
		var val = $(block);
		var upper = $(block + 'Upper');
		var lower = $(block + 'Lower');

		if(val != null && val.disabled != true &&
			lower != null && lower.disabled != true &&
			parseInt(lower.value) > parseInt(val.value))
			return true;
		else if (val != null && val.disabled != true &&
			upper != null && upper.disabled != true &&
			parseInt(val.value) > parseInt(upper.value))
			return true;
		else if(lower != null && lower.disabled != true &&
			upper != null && upper.disabled != true &&
			parseInt(lower.value) > parseInt(upper.value))
			return true;
		else
			return false;
	}

	function validateRange(block, isCo) {
		var val = msdsIndex.getMSDSfield(block, isCo);
		var upper = msdsIndex.getMSDSfield(block + 'Upper',isCo);
		var lower = msdsIndex.getMSDSfield(block + 'Lower',isCo);

		if(val != null && val.disabled != true &&
			lower != null && lower.disabled != true &&
			parseInt(lower.value) > parseInt(val.value))
			return true;
		else if (val != null && val.disabled != true &&
			upper != null && upper.disabled != true &&
			parseInt(val.value) > parseInt(upper.value))
			return true;
		else if(lower != null && lower.disabled != true &&
			upper != null && upper.disabled != true &&
			parseInt(lower.value) > parseInt(upper.value))
			return true;
		else
			return false;
	}

	function validateMixtureUnits(block) {
		var val = msdsIndex.getMSDSfield(block);
		var lower = msdsIndex.getMSDSfield(block + 'Upper');
		var upper = msdsIndex.getMSDSfield(block + 'Lower');
		var unit = msdsIndex.getMSDSfield(block + 'Unit');

		if(val != null && val.disabled != true &&
			val.value != '' && unit.value == '')
			return true;
		else if(lower != null && lower.disabled != true &&
			lower.value != '' && unit.value == '')
			return true;
		else if (upper != null && upper.disabled != true &&
			upper.value != '' && unit.value == '')
			return true;
		else
			return false;
	}

	function validateUnits(block, isCo) {
		var val = msdsIndex.getMSDSfield(block, isCo);
		var lower = msdsIndex.getMSDSfield(block + 'Upper', isCo);
		var upper = msdsIndex.getMSDSfield(block + 'Lower', isCo);
		var unit = msdsIndex.getMSDSfield(block + 'Unit', isCo);

		if(val != null && val.disabled != true &&
			val.value != '' && unit.value == '')
			return true;
		else if(lower != null && lower.disabled != true &&
			lower.value != '' && unit.value == '')
			return true;
		else if (upper != null && upper.disabled != true &&
			upper.value != '' && unit.value == '')
			return true;
		else
			return false;
	}

	function validateCasNumber(rowId) {
		var casNumberPattern = /^\d+-\d\d-\d$/;
		var casNumber = gridCell(window["compositionGrid"+msdsIndex.component],rowId,"casNumber").getValue();
		if (casNumber != null && casNumber.length > 1 && !casNumber.startsWith("TS") && !casNumber.startsWith("NI")) {
			if (!casNumber.match(casNumberPattern)) {
				getGridElement(rowId, "casNumber").focus();
				alert(messagesData.invalidcasnumber + " " + casNumber);
				return false;
			}
			else {
				var casPieces = casNumber.split("-");
				var checksum = (casPieces[1].charAt(1) * 1) + (casPieces[1].charAt(0) * 2);
				var cntr = 3;
				for (var index = casPieces[0].length - 1; index >= 0; index--) {
					checksum += casPieces[0].charAt(index) * cntr++;
				}
				checksum %= 10;
				if (checksum != (casPieces[2] * 1) || ((casPieces[0] + casPieces[1]) * 1) < 5000) {
					alert(messagesData.invalidcasnumber + " " + casNumber);
					return false;
				}
			}
		}
		return true;
	}

	function phCheck(ph) {
		if(ph != '' && (parseFloat(ph.value) > 14 || parseFloat(ph.value) < 0))
			return messagesData.phRange.replace('{0}', messagesData.ph).replace('{1}', '1').replace('{2}', '14') + '\n';
		else
			return "";
	}

	function checkFloat(field, name, low, high, isCo) {
		//var materialQcPrefix = hasMultipleComponents()?messagesData.component + " " + msdsIndex.component + " - ":"";
		var value = msdsIndex.getMSDSfieldValue(field, isCo);
		if ( ! isFloat(value, true)) {
			//return materialQcPrefix + formatMessage(messagesData.floatError, name) + "\n";
			return formatMessage(messagesData.floatError, name) + "\n";
		}
		else if (low != null && high != null) {
			value = value * 1.0;
			if (value < low || value > high) {
				//return materialQcPrefix + formatMessage(messagesData.rangeError, name, low, high) + "\n";
				return formatMessage(messagesData.rangeError, name, low, high) + "\n";
			}
		}
		return "";
	}

	function checkSignedFloat(field, name, low, high, isCo) {
		//var materialQcPrefix = hasMultipleComponents()?messagesData.component + " " + msdsIndex.component + " - ":"";
		var value = msdsIndex.getMSDSfieldValue(field, isCo);
		if ( ! isSignedFloat(value, true)) {
			//return materialQcPrefix + formatMessage(messagesData.floatError, name) + "\n";
			return formatMessage(messagesData.floatError, name) + "\n";
		}
		else if (low != null) {
			value = value * 1.0;
			if (value < low || value > high) {
				//return materialQcPrefix + formatMessage(messagesData.rangeError, name, low, high) + "\n";
				return formatMessage(messagesData.rangeError, name, low, high) + "\n";
			}
		}
		return "";
	}

	function checkPositiveFloat(field, name, isCo, includeZero) {
		//var materialQcPrefix = hasMultipleComponents()?messagesData.component + " " + msdsIndex.component + " - ":"";
		var value = msdsIndex.getMSDSfieldValue(field,isCo);
		if ( ! (isFloat(value, true) && (isEmpty(value) || (includeZero && value * 1.0 >= 0.0) || (value * 1.0 > 0.0)))) {
			//return materialQcPrefix + formatMessage(messagesData.floatError, name) + "\n";
			return formatMessage(messagesData.positiveError, name) + "\n";
		}
		return "";
	}

	function checkInteger(field, name, low, high, ignoreString, isCo) {
		//var materialQcPrefix = hasMultipleComponents()?messagesData.component + " " + msdsIndex.component + " - ":"";
		var value = msdsIndex.getMSDSfieldValue(field,isCo);
		if (ignoreString != null) {
			value = value.replace(ignoreString, "");
		}
		if ( ! isInteger(value, true)) {
			//return materialQcPrefix + formatMessage(messagesData.integerError, name) + "\n";
			return formatMessage(messagesData.integerError, name) + "\n";
		}
		else if (low != null) {
			value = value * 1;
			if (value < low || value > high) {
				//return materialQcPrefix + formatMessage(messagesData.rangeError, name, low, high) + "\n";
				return formatMessage(messagesData.rangeError, name, low, high) + "\n";
			}
		}
		return "";
	}

	function arrayIndexOf(array, obj) {
		for (var i = 0; i < array.length; i++) {
			if (array[i] == obj)
				return i;
		}
		return -1;
	}


	/* End of Validation */
	/* Transit Window */


	msdsIndex.showWait = function(message){
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
			transitWin.setPosition(328, 131);
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

	msdsIndex.stopWaiting = function() {
		if (dhxWins != null) {
			if (dhxWins.window("transitDailogWin")) {
				dhxWins.window("transitDailogWin").setModal(false);
				dhxWins.window("transitDailogWin").hide();
				if (transitReload) {
					msdsIndex.showWait();
				}
				transitReload = false;
			}
		}
		return true;
	}


	/* End of Transit Window */
	/* Search Functions */


	msdsIndex.lookupManufacturer = function(isKit) {
		isKitMfg = isKit;
		var manufacturer = "";
		try {
			if (isKit) {
				manufacturer = $v("msds[0].mixtureManufacturer");
			}
			else {
				manufacturer = msdsIndex.getMSDSfieldValue("manufacturer");
			}
		}
		finally {
			if (manufacturer == null || manufacturer == "") {
				manufacturer = msdsIndex.getMSDSfieldValue("mfgIdSearch");
			}
		}
		var loc = "manufacturersearchmain.do?userAction=search&searchArgument="+encodeURIComponent(manufacturer);
		msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.mfg));
		children[children.length] = openWinGeneric(loc,"manufacturersearch","600","500","yes","50","50","20","20","no");
	}

	msdsIndex.lookupMaterial = function() {
		var materialDescription = "";
		try {
			materialDescription = msdsIndex.getMSDSfieldValue("materialDesc");
		}
		finally {
			if (materialDescription == null || materialDescription == "") {
				materialDescription = msdsIndex.getMSDSfieldValue("materialIdSearch");
			}
		}
		var mfgId = msdsIndex.getMSDSfieldValue("mfgIdSearch") || msdsIndex.getMSDSfieldValue("mfgId");
		var qId = $v("qId");
		materialDescription = materialDescription.replace(/\\/g, "\\\\");
		var loc = "materialsearchmain.do?userAction=init&searchArgument="+encodeURIComponent(materialDescription);
		loc += "&mfgId=" + mfgId + "&qId=" + qId;
		msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.material));
		children[children.length] = openWinGeneric(loc,"materialsearch","700","500","yes","50","50","20","20","no");
	}

	msdsIndex.manufacturerChanged = function(newId, newDesc, shortName, phone, email, contact, notes, url) {
		if (isKitMfg) {
			$("mixtureManufacturer").value = htmlDencode(newDesc);
			$("mixtureMfgId_disp").innerHTML = newId;
			$("mixtureMfgId").value = newId;
		}
		else {
			transitReload = true;
			msdsIndex.getMSDSfield("mfgIdSearch").value = newId;
			msdsIndex.getMSDSfield("mfrSearch").value = newDesc;
			msdsIndex.lookupMaterial();
		}
	}

	msdsIndex.materialChanged = function(newId, newDesc, tradeName, mfgId, productCode, customerOnlyMsds, isNewMaterial) {
		transitReload = true;
		msdsIndex.getMSDSfield("materialIdSearch").value = newId;
		msdsIndex.getMSDSfield("materialSearch").value = newDesc;
		if (newId != null) {
			msdsIndex.getMSDSfield("revisionDateNew").className = "";
		}
		submitMsdsSearch(msdsIndex.component);
	}

	msdsIndex.revisionDateChanged = function() {
		var revDateSearch = msdsIndex.getMSDSfieldValue("revisionDateSearch");
		if (revDateSearch != msdsIndex.getMSDSfieldValue("revisionDate")) {
			if(revDateSearch.trim().length == 0 || confirm(messagesData.changes)) {
				if (revDateSearch == messagesData.newRevision) {
					msdsIndex.getMSDSfield("revisionDateSearch").value = msdsIndex.getMSDSfieldValue("revisionDate");
					msdsIndex.getMSDSfield("content").value="";
					getNewRevisionDate();
				}
				else if (revDateSearch == "NOT_REQUIRED") {
					msdsIndex.getMSDSfield("sdsRequired").value = "N";
					msdsIndex.calendarDateChangedCallBack(msdsIndex.component);
				}
				else {
					msdsIndex.showWait(messagesData.loading);
					j$.ajax({
						url:"msdsindexingresults.do",
						cache:false,
						data:{uAction: "getRevision",
							materialId: msdsIndex.getMSDSfieldValue("materialIdSearch"),
							revisionDate: revDateSearch,
							mfgId: msdsIndex.getMSDSfieldValue("mfgIdSearch"),
							requestId: $v("requestId")||"",
							lineItem: $v("lineItem")||"",
							component: msdsIndex.component,
							qId: $v("qId")
						},
						success: function(data) {
							j$("#msdsDetailDiv"+msdsIndex.component).html(data);
							if ($v("masterData") != "Y") {
								j$("#gridUpdateLinks0").css("display","inline-block");
							}
							toggleQcBlocks();
							toggleBlocks();
							bindCoChange();
							msdsIndex.countryChanged();
							//msdsIndex.checkDataEntryComplete();
							storeSelectOldVal(msdsIndex.getMSDSfield("idOnly"));
							if (msdsIndex.getMSDSfield("compositionColumn")) {
								setTimeout(function(){msdsIndex.initializeCompositionGrids();});
							}
							setupAuthorAutocomplete();
							if (isSdsRequired()) {
								disableAuthorInput(false);
							}
						},
						complete: function() {
							msdsIndex.resizeMainPage();
							msdsIndex.stopWaiting();
						}
					});
				}
			}
			else {
				msdsIndex.getMSDSfield("revisionDateSearch").value = msdsIndex.getMSDSfieldValue("revisionDate");
			}
		}
	}

	msdsIndex.setNewRevisionDate = function(newDate, newDateDisplay) {
		if (newDateDisplay == null || newDateDisplay.length == 0) {
			alert("Selected Date is not a Valid Revision Date.");
		}
		else {
			var msdsId = msdsIndex.getMSDSfieldValue("msdsId");
			var revDateSelectBox = msdsIndex.getMSDSfield("revisionDateSearch");
			if (revDateSelectBox) {
				if (revDateSelectBox.options) {
					if (revDateSelectBox != null && (msdsId == null || msdsId.length == 0)) {
						revDateSelectBox.remove(0);
					}
					var newDateOption = document.createElement("option");
					newDateOption.value = newDate;
					newDateOption.text = newDateDisplay;
					if(revDateSelectBox.options.length > 0 && revDateSelectBox.options[0].value.trim().length > 0) {
						revDateSelectBox.add(newDateOption,0);
					}
					else {
						revDateSelectBox.options[0] = newDateOption;
					}
					revDateSelectBox.selectedIndex = 0;
				}
				else {
					revDateSelectBox.value = newDate;
				}
			}

			msdsIndex.getMSDSfield("revisionDate").value = newDate;
			if (isSdsRequired()) {
				var revDateDisp = msdsIndex.getMSDSfield("revisionDateDisp");
				if (revDateDisp.value.length == 0) {
					revDateDisp.value = newDateDisplay;
				}
				else {
					revDateDispValue = revDateDisp.value.split(" - ");
					var updatedValue = newDateDisplay;
					if (revDateDispValue.length > 1) {
						updatedValue += " - " + revDateDispValue[1];
					}
					revDateDisp.value = updatedValue;
				}

				var url = window.location;
				msdsIndex.getMSDSfield("idOnly").value = "N";
				msdsIndex.getMSDSfield("idOnly").disabled=true;
				msdsIndex.getMSDSfield("msdsIndexingPriorityId").value=$v("maintenance")=="Y"?"":"1";
				msdsIndex.getMSDSfield("content").value="";
				msdsIndex.getMSDSfield("msdsId").value = "";
				msdsIndex.getMSDSfield("msdsAuthorId").value = "";
				msdsIndex.getMSDSfield("msdsAuthorDesc").value = "";
				msdsIndex.getMSDSfield("msdsAuthorCountryAbbrev").value = "";
				msdsIndex.getMSDSfield("msdsAuthorNotes").value = "";
				msdsIndex.getMSDSfield("sdsRequired").value = "Y";

				if ($v("catalogQueueRowTask") != "SDS Sourcing" && $v("catalogQueueRowTask") != "SDS Authoring") {
					msdsIndex.getMSDSfield("dataEntryComplete").checked = false;
					var coDec = msdsIndex.getMSDSfield("dataEntryComplete",true);
					if (coDec) {
						coDec.checked = false;
					}
					msdsIndex.getMSDSfield("qcApproved").checked = false;
					var coQcApproved = msdsIndex.getMSDSfield("qcApproved",true);
					if (coQcApproved) {
						coQcApproved.checked = false;
					}

					msdsIndex.getMSDSfield("ghsCompliantImage").checked = false;
					var numRows = window["compositionGrid"+msdsIndex.component].getRowsNum();
					for (var rowId = 1; rowId <= numRows; rowId++) {
						var sectionDropdown = getGridElement(rowId, "sdsSectionId");
						if (sectionDropdown.value == '') {
							sectionDropdown.options[1].selected = true;
						}
					}

					clearMSDS([],"ghsfields");
					clearMSDS([],"ghspictograms");
					var exclusions = j$("textarea[id^='msdshazards']").toArray();
					clearMSDS(exclusions,"hazardstatements");
					exclusions = j$("textarea[id^='msdsprecautions']").toArray();
					clearMSDS(exclusions,"precautionarystatements");
					clearCoData([]);
					msdsIndex.getMSDSfield("qcStatus").value = "na";
					var coQc = msdsIndex.getMSDSfield("qcStatus",true);
					if (coQc != null) {
						coQc.value = "na";
					}
				}
				disableAuthorInput(false);
			}
			else {
				disableAuthorInput(true);
				msdsIndex.getMSDSfield("sdsRequired").value = "N";
			}
			toggleQcBlocks();
			hideQcDropdowns();
		}
	}

	function disableAuthorInput(disable) {
		var authorDesc = msdsIndex.getMSDSfield("msdsAuthorDesc")
		authorDesc.disabled = disable;
		msdsIndex.getMSDSfield("uploadFile").disabled = disable;
		if (disable) {
			msdsIndex.getMSDSfield("newAuthor").style.display = "none";
			msdsIndex.getMSDSfield("mfrAuthor").style.display = "none";
			authorDesc.value = "";
			authorDesc.className = "optionTitleBold msdsReadonlyField";
			msdsIndex.getMSDSfield("msdsAuthorId").value = "";
		}
		else {
			msdsIndex.getMSDSfield("newAuthor").style.display = "";
			msdsIndex.getMSDSfield("mfrAuthor").style.display = "";
			authorDesc.className = "inputBox";
		}
	}

	msdsIndex.createAuthor = function(idx) {
		var loc = "newrevisiondate.do?uAction=newmsdsauthor&directCall=Y";
		msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.author));
		children[children.length] = openWinGeneric(loc, "newSdsAuthor","600","500","yes","50","50","20","20","no");
	}

	msdsIndex.setAuthorToMfr = function() {
		msdsIndex.getMSDSfield("msdsAuthorId").value = msdsIndex.getMSDSfieldValue("mfrAuthorId");
		msdsIndex.getMSDSfield("msdsAuthorDesc").value = msdsIndex.getMSDSfieldValue("mfrAuthorDesc");
		msdsIndex.getMSDSfield("msdsAuthorCountryAbbrev").value = msdsIndex.getMSDSfieldValue("mfrAuthorCountryAbbrev");
		msdsIndex.getMSDSfield("msdsAuthorNotes").value = msdsIndex.getMSDSfieldValue("mfrAuthorNotes");
	}

	msdsIndex.setNewMsdsAuthor = function(authorId, authorDesc, authorCountry, authorNotes) {
		msdsIndex.getMSDSfield("msdsAuthorId").value = authorId;
		msdsIndex.getMSDSfield("msdsAuthorDesc").value = authorDesc;
		msdsIndex.getMSDSfield("msdsAuthorCountryAbbrev").value = authorCountry;
		msdsIndex.getMSDSfield("msdsAuthorNotes").value = authorNotes;
	}

	// helper function for new revision dates
	function hideQcDropdowns() {
		hideQcDropdown("alloyQcBlock");
		hideQcDropdown("boilingPointQcBlock");
		hideQcDropdown("carcinogenQcBlock");
		hideQcDropdown("chronicQcBlock");
		hideQcDropdown("compositionQcBlock");
		hideQcDropdown("contentQcBlock");
		hideQcDropdown("corrosiveQcBlock");
		hideQcDropdown("densityQcBlock");
		hideQcDropdown("evaporationRateQcBlock");
		hideQcDropdown("fireConditionsToAvoidQcBlock");
		hideQcDropdown("flashPointQcBlock");
		hideQcDropdown("ghsPictogramsQcBlock");
		hideQcDropdown("hazardStatementsQcBlock");
		hideQcDropdown("healthEffectsQcBlock");
		hideQcDropdown("hmisQcBlock");
		hideQcDropdown("incompatibleQcBlock");
		hideQcDropdown("labelAddressQcBlock");
		hideQcDropdown("manufacturerQcBlock");
		hideQcDropdown("materialDescriptionQcBlock");
		hideQcDropdown("msdsAuthorQcBlock");
		hideQcDropdown("nfpaQcBlock");
		hideQcDropdown("oxidizerQcBlock");
		hideQcDropdown("phQcBlock");
		hideQcDropdown("physicalStateQcBlock");
		hideQcDropdown("polymerizeQcBlock");
		hideQcDropdown("precautionaryStatementsQcBlock");
		hideQcDropdown("productCodeQcBlock");
		hideQcDropdown("revisionDateQcBlock");
		hideQcDropdown("saraQcBlock");
		hideQcDropdown("signalWordQcBlock");
		hideQcDropdown("solidsQcBlock");
		hideQcDropdown("specificGravityQcBlock");
		hideQcDropdown("stableQcBlock");
		hideQcDropdown("tradeNameQcBlock");
		hideQcDropdown("tscaQcBlock");
		hideQcDropdown("vaporDensityQcBlock");
		hideQcDropdown("vaporPressureQcBlock");
		hideQcDropdown("vocQcBlock");
		hideQcDropdown("vocLessH2oExemptQcBlock");
		hideQcDropdown("waterReactiveQcBlock");
	}

	// helper function for hideQcDropdowns
	function hideQcDropdown(field) {
		var dd = msdsIndex.getMSDSfield(field);
		if (dd != null) {
			j$(dd).addClass("invisible");
		}

		dd = msdsIndex.getMSDSfield(field, true);
		if (dd != null) {
			j$(dd).addClass("invisible");
		}
	}

	function getNewRevisionDate(){
		var materialId = msdsIndex.getMSDSfieldValue("materialId");
		var mfgId = msdsIndex.getMSDSfieldValue("mfgId");

		var loc = "newrevisiondate.do?uAction=newrevdate&materialId=" + materialId + "&mfgId=" + mfgId;
		msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.newrevdate));
		children[children.length] = openWinGeneric(loc,"newrevisiondate","600","500","yes","50","50","20","20","no");
		/*children[children.length-1].onunload = function() {
            opener.console.log("Hello");
        };*/
	}

	function submitMsdsSearch(idx) {
		msdsIndex.showWait(messagesData.loading);
		var requestIdValue = $v("requestId")||$v("catalogAddRequestId")||"";
		var lineItemValue = $v("lineItem")||$v("catalogAddRequestLineItem")||"";
		j$.ajax({
			url:"msdsindexingsearch.do",
			cache:false,
			data:{uAction: "getMsds",
				materialId: msdsIndex.getMSDSfieldValue("materialIdSearch"),
				mfgId: msdsIndex.getMSDSfieldValue("mfgIdSearch"),
				requestId: requestIdValue,
				lineItem: lineItemValue,
				component: msdsIndex.component,
				qId: $v("qId")
			},
			success: function(data) {
				var itemDiv = j$("#itemDiv"+msdsIndex.component);
				itemDiv.html(data);
				itemDiv.css("display","block");
				j$("#gridUpdateLinks").css("display","inline-block");
				toggleQcBlocks();
				toggleBlocks();
				bindCoChange();
				if (msdsIndex.getMSDSfield("attachedDocuments")) {
					msdsIndex.getMSDSfield("attachedDocuments").className = "inputBtns";
				}
				msdsIndex.countryChanged();
				storeSelectOldVal(msdsIndex.getMSDSfield("idOnly"));
				setupAuthorAutocomplete();
				setTimeout(function(){msdsIndex.initializeCompositionGrids();});
			},
			complete: function() {
				msdsIndex.resizeMainPage();
				transitReload = false;
				msdsIndex.stopWaiting();
			}
		});
	}
	
	msdsIndex.toggleChangeLocale = function() {
		var currentLocale = $v("qLocaleCode");
		var currentLocaleOption = $(currentLocale);
		if (currentLocaleOption) {
			currentLocaleOption.disabled = true;
		}
		jQuery(msdsIndex.getMSDSfield("changeLocalePopup")).toggle();
	};
	
	msdsIndex.submitChangeLocale = function() {
		$("uAction").value = "changeLocale";
		msdsIndex.showWait(messagesData.approving);
		document.genericForm.submit();
	};


	/* End of Search Functions */
	/* Section Functions */


	msdsIndex.clearRadio = function(radioName, co) {
		var chk = msdsIndex.getMSDSfield(radioName + "Y", co);
		if(chk != null)
			chk.checked = false;
		chk = msdsIndex.getMSDSfield(radioName + "N", co);
		if(chk != null)
			chk.checked = false;
		chk = msdsIndex.getMSDSfield(radioName + "X", co);
		if(chk != null)
			chk.checked = false;
		chk = msdsIndex.getMSDSfield(radioName + "Air", co);
		if(chk != null)
			chk.checked = false;
		chk = msdsIndex.getMSDSfield(radioName + "Water", co);
		if(chk != null)
			chk.checked = false;
		chk = msdsIndex.getMSDSfield(radioName + "U", co);
		if(chk != null)
			chk.checked = false;
		chk = msdsIndex.getMSDSfield(radioName + "NA", co);
		if (chk != null)
			chk.checked = false;
		chk = msdsIndex.getMSDSfield(radioName + "NL", co);
		if (chk != null)
			chk.checked = false;
	}

	msdsIndex.editHazards = function(edit) {
		var _pre = "#msds\\["+msdsIndex.component+"\\]\\.";
		if (edit) {
			j$(_pre+"ghsHazards input[type='checkbox']:not(:disabled)").css("visibility","visible");
			var listItems = j$(_pre+"ghsHazards li");
			listItems.show();
			listItems.bind("mouseover",function() {
				j$(this).css('background-color','white');
			});
			listItems.bind("mouseout",function() {
				j$(this).css('background-color','transparent');
			});
			listItems.bind("click",function() {
				return msdsIndex.selectStatement(j$(this).index()+1,"msdshazards");
			});
			listItems.css("cursor","pointer");

			j$("input[id$='selectHazard']").each(function(index, element){
				j$(element).siblings("input[id$='original']").get(0).checked = element.checked;
			});

			j$("input[id^='msdshazards'][id$='isFromMsds']").each(function(index, element){
				j$(element).siblings("input[id$='originalIsFromMsds']").get(0).value = element.value;
			});
		}
		else {
			j$(_pre+"ghsHazards input:checkbox").css("visibility","hidden");
			j$(_pre+"ghsHazards li:has(input:checkbox:not(:checked):not(:disabled))").hide();
			j$(_pre+"ghsHazards li").unbind("click mouseover mouseout");
			j$(_pre+"ghsHazards li").css("cursor","auto");
		}

		j$(_pre+"editHazards").toggle();
		j$(_pre+"finishEditHazards").toggle();
		j$(_pre+"clearHazards").toggle();
		j$(_pre+"resetHazards").toggle();
	}

	msdsIndex.editPrecautions = function(edit) {
		var _pre = "#msds\\["+msdsIndex.component+"\\]\\.";
		if (edit) {
			j$(_pre+"ghsPrecautions input[type='checkbox']:not(:disabled)").css("visibility","visible");
			var listItems = j$(_pre+"ghsPrecautions li");
			listItems.show();
			listItems.bind("mouseover",function() {
				j$(this).css('background-color','white');
			});
			listItems.bind("mouseout",function() {
				j$(this).css('background-color','transparent');
			});
			listItems.bind("click",function() {
				return msdsIndex.selectStatement(j$(this).index()+1,"msdsprecautions");
			});
			listItems.css("cursor","pointer");

			j$("input[id$='selectPrecaution']").each(function(index, element){
				j$(element).siblings("input[id$='original']").get(0).checked = element.checked;
			});

			j$("input[id^='msdsprecautions'][id$='isFromMsds']").each(function(index, element){
				j$(element).siblings("input[id$='originalIsFromMsds']").get(0).value = element.value;
			});
		}
		else {
			j$(_pre+"ghsPrecautions input:checkbox").css("visibility","hidden");
			j$(_pre+"ghsPrecautions li:has(input:checkbox:not(:checked):not(:disabled))").hide();
			j$(_pre+"ghsPrecautions li").unbind("click mouseover mouseout");
			j$(_pre+"ghsPrecautions li").css("cursor","auto");
		}

		j$(_pre+"editPrecautions").toggle();
		j$(_pre+"finishEditPrecautions").toggle();
		j$(_pre+"clearPrecautions").toggle();
		j$(_pre+"resetPrecautions").toggle();
	}

	msdsIndex.selectStatement = function(idx, prefix) {
		//	if (event.srcElement.nodeName != "INPUT" && event.srcElement.nodeName != "LABEL") {
		if ($(prefix+msdsIndex.component+"["+idx+"].isFromMsds").value == 0) {
			var conf = confirm(messagesData.maxcomestimatepicalert);
			if (conf) {
				$(prefix+msdsIndex.component+"["+idx+"].isFromMsds").value = 1;
				$(prefix+msdsIndex.component+"["+idx+"].isFromMsdsMsg").style.display = "none";
				chkBox.checked = false;
			}
			else {
				var chkBox = $(prefix+msdsIndex.component+"["+idx+"].selectHazard");
				chkBox = chkBox || $(prefix+msdsIndex.component+"["+idx+"].selectPrecaution");
				chkBox.checked = true;
			}
			return conf;
		}
		else {
			if (event.srcElement.nodeName != "INPUT" && event.srcElement.nodeName != "LABEL") {
				var chkBox = $(prefix+msdsIndex.component+"["+idx+"].selectHazard");
				chkBox = chkBox || $(prefix+msdsIndex.component+"["+idx+"].selectPrecaution");
				chkBox.checked = ! chkBox.checked;
				//msdsIndex.getMSDSfield(id+"Sticky").checked = ! chkBox.checked;
				//event.stopPropagation();
			}
		}
		//	}
	}

	msdsIndex.clearHazards = function() {
		if (j$(msdsIndex.getMSDSfield("ghsHazards")).find("input[id$='isFromMsds']:not(:disabled)[value='0']").length > 0) {
			alert(messagesData.maxcomclearstmtalert);
			return false;
		}
		j$(msdsIndex.getMSDSfield("ghsHazards")).find("input[id$='selectHazard']").attr("checked",false);
	}

	msdsIndex.clearPrecautions = function() {
		if (j$(msdsIndex.getMSDSfield("ghsPrecautions")).find("input[id$='isFromMsds']:not(:disabled)[value='0']").length > 0) {
			alert(messagesData.maxcomclearstmtalert);
			return false;
		}
		j$(msdsIndex.getMSDSfield("ghsPrecautions")).find("input[id$='selectPrecaution']").attr("checked",false);
	}

	msdsIndex.resetHazards = function() {
		var componentStmtList = msdsIndex.getMSDSfield("ghsHazards");
		j$(componentStmtList).find("[id$=selectHazard]").each(function(index, element){
			try {
				element.checked = $("msdshazards"+msdsIndex.component+"["+(index+1)+"].original").checked;
				var originalOnSds = $v("msdshazards"+msdsIndex.component+"["+(index+1)+"].originalIsFromMsds");
				$("msdshazards"+msdsIndex.component+"["+(index+1)+"].isFromMsds").value = originalOnSds;
				if (originalOnSds === "0") {
					$("msdshazards"+msdsIndex.component+"["+(index+1)+"].isFromMsdsMsg").style.display = "inline";
				}
			}
			catch(ex) {
				console.log("Error on Hazard Statements. Could not reset index "+index);
			}
		});
		msdsIndex.editHazards(false);
	}

	msdsIndex.resetPrecautions = function() {
		var componentStmtList = msdsIndex.getMSDSfield("ghsPrecautions");
		j$(componentStmtList).find("[id$=selectPrecaution]").each(function(index, element){
			try {
				element.checked = $("msdsprecautions"+msdsIndex.component+"["+(index+1)+"].original").checked;
				var originalOnSds = $v("msdsprecautions"+msdsIndex.component+"["+(index+1)+"].originalIsFromMsds");
				$("msdsprecautions"+msdsIndex.component+"["+(index+1)+"].isFromMsds").value = originalOnSds;
				if (originalOnSds === "0") {
					$("msdsprecautions"+msdsIndex.component+"["+(index+1)+"].isFromMsdsMsg").style.display = "inline";
				}
			}
			catch(ex) {
				console.log("Error on Precautionary Statements. Could not reset index "+index);
			}
		});
		msdsIndex.editPrecautions(false);
	}

	msdsIndex.selectPictogram = function(id) {
		var chkBox = msdsIndex.getMSDSfield(id+"Pic");
		if (chkBox.readonly) {
			return;
		}
		if (msdsIndex.getMSDSfield(id+"OnSds") == null || msdsIndex.getMSDSfield(id+"OnSds").checked) {
			var ch = chkBox.checked;
			if (event.srcElement.nodeName != "INPUT" && event.srcElement.nodeName != "LABEL") {
				msdsIndex.getMSDSfield(id+"Pic").checked = ! ch;
				msdsIndex.getMSDSfield(id+"Sticky").checked = ! ch;
				//event.stopPropagation();
			}
			else {
				if (id == 'not required') {
					// NOTE: checkbox is already changed at this point
					var noPics = msdsIndex.getMSDSfield(id+"Pic").checked;

					if (j$(msdsIndex.getMSDSfield("ghspictograms")).find("input[id$='Sticky']:checked").length > 0) {
						if (j$("#msds\\["+msdsIndex.component+"\\]\\.ghspictograms input[id$='OnSds']:not(:checked):not(:disabled)").length > 0) {
							alert(messagesData.maxcomclearpicalert);
							return false;
						}
						else {
							j$("#msds\\["+msdsIndex.component+"\\]\\.ghspictograms input:checkbox[id$='Pic']").each(function(index, element){
								var picId = element.value;
								if (picId != "not required") {
									// set all checkboxes to the opposite of "not required"
									element.checked = ! noPics && msdsIndex.getMSDSfield(picId+"Sticky").checked;
								}
							});
						}
					}
					else if ( ! noPics) {
						alert(messagesData.pleaseselectpictogram);
						return false;
					}
				}
				else {
					msdsIndex.getMSDSfield(id+"Sticky").checked = ch;
				}
			}
			if (id != 'not required') {
				if (j$(msdsIndex.getMSDSfield("ghspictograms")).find("input[id$='Pic']:checked").length == 0) {
					msdsIndex.getMSDSfield("not requiredPic").checked = true;
				}
				else {
					msdsIndex.getMSDSfield("not requiredPic").checked = false;
				}
			}
			return true;
		}
		else {
			var conf = confirm(messagesData.maxcomestimatepicalert);
			if (conf) {
				msdsIndex.getMSDSfield(id+"OnSds").checked = true;
				msdsIndex.getMSDSfield(id+"OnSdsMsg").style.display = "none";
				msdsIndex.getMSDSfield(id+"Pic").checked = false;
				msdsIndex.getMSDSfield(id+"Sticky").checked = false;
			}
			return conf;
		}
	}

	msdsIndex.resetPictograms = function() {
		var pictogramOnSds = false;
		j$("#msds\\["+msdsIndex.component+"\\]\\.ghspictograms input:checkbox[id$='Pic']").each(function(index, element){
			var picId = element.value;
			var originalChecked = msdsIndex.getMSDSfield(picId+"Original").checked;
			element.checked = originalChecked;
			if (picId != "not required") {
				msdsIndex.getMSDSfield(picId+"Sticky").checked = originalChecked;
				msdsIndex.getMSDSfield(picId+"OnSds").checked = msdsIndex.getMSDSfield(picId+"OriginalOnSds").checked;
				if ( ! msdsIndex.getMSDSfield(picId+"OnSds").checked) {
					msdsIndex.getMSDSfield(picId+"OnSdsMsg").style.display = "inline";
				}
				pictogramOnSds = pictogramOnSds || originalChecked;
			}
		});

		if ( ! pictogramOnSds) {
			msdsIndex.getMSDSfield("not requiredPic").checked = true;
			msdsIndex.getMSDSfield("not requiredOriginal").checked = true;
		}
	}

	msdsIndex.countryChanged = function() {
		var stateSelect = msdsIndex.getMSDSfield("labelStateAbbrev");
		if (stateSelect != null) {
			var cty = msdsIndex.getMSDSfieldValue("labelCountryAbbrev");
			var selectedState = msdsIndex.getMSDSfieldValue("selectedState");

			var leng = stateSelect.length;

			var states = msdsIndex.territoryList[cty];

			if (states.length == 1) {
				stateSelect.disabled = true;
			}
			else {
				stateSelect.disabled = false;
			}
			for (var i = 0;i<leng;i++) {
				stateSelect.remove(0);
			}

			if (states.length > 1) {
				var opt = document.createElement("option");
				opt.value = "";
				opt.text = messagesData.selectOne;
				stateSelect.add(opt);
			}

			for (curState in states) {
				var opt = document.createElement("option");
				opt.value = states[curState].stateAbbrev;
				opt.text = states[curState].state;
				if (states[curState].stateAbbrev == selectedState) {
					opt.selected = true;
				}
				else if (stateSelect.readonly == "readonly") {
					opt.disabled = "disabled";
				}
				stateSelect.add(opt);
			}
		}
	}

	msdsIndex.setSpecGrav = function(value,customerOverride) {
		if(value == "gas") {
			msdsIndex.getMSDSfield('specificGravityBasisAir',customerOverride).checked = true;
		}
		else if(value == "liquid") {
			msdsIndex.getMSDSfield('specificGravityBasisWater',customerOverride).checked = true;
		}
	}

	msdsIndex.launchFileView = function() {
		var loc = msdsIndex.getMSDSfieldValue("content");
		if (loc.length > 1) {
			children[children.length] = openWinGeneric(loc,"VIEW_FILE","800","650","yes");
		}
		else {
			msdsIndex.launchSrcFileView();
		}
	}

	msdsIndex.launchSrcFileView = function() {
		var srcLoc = msdsIndex.getMSDSfieldValue("sourceContent");
		if (srcLoc.length > 1) {
			children[children.length] = openWinGeneric(srcLoc,"VIEW_FILE","800","650","yes");
		}
	}

	msdsIndex.setIdOnly = function() {
		if (msdsIndex.getMSDSfieldValue("idOnly") == "Y") {
			var clearMsds = true;
			clearMsds = confirm ("Clear all MSDS fields for this Revision Date?");

			if (clearMsds) {
				var exclusions = j$("textarea[id^='msdshazards'],textarea[id^='msdsprecautions']").toArray();
				var addlExclusions = [
					msdsIndex.getMSDSfield("mfgId"),
					msdsIndex.getMSDSfield("manufacturer"),
					msdsIndex.getMSDSfield("mfgShortName"),
					msdsIndex.getMSDSfield("mfgUrl"),
					msdsIndex.getMSDSfield("contact"),
					msdsIndex.getMSDSfield("phone"),
					msdsIndex.getMSDSfield("email"),
					msdsIndex.getMSDSfield("notes"),
					msdsIndex.getMSDSfield("materialId"),
					msdsIndex.getMSDSfield("componentMsds"),
					msdsIndex.getMSDSfield("materialDesc"),
					msdsIndex.getMSDSfield("mfgTradeName"),
					msdsIndex.getMSDSfield("productCode"),
					msdsIndex.getMSDSfield("customerOnlyMsds"),
					msdsIndex.getMSDSfield("comments"),
					msdsIndex.getMSDSfield("localizedMaterialDesc"),
					msdsIndex.getMSDSfield("localizedMfgTradeName"),
					msdsIndex.getMSDSfield("msdsAuthorId"),
					msdsIndex.getMSDSfield("revisionDate"),
					msdsIndex.getMSDSfield("revisionDateDisp"),
					msdsIndex.getMSDSfield("msdsAuthorDesc"),
					msdsIndex.getMSDSfield("msdsAuthorCountryAbbrev"),
					msdsIndex.getMSDSfield("msdsAuthorNotes"),
					msdsIndex.getMSDSfield("content"),
					msdsIndex.getMSDSfield("idOnly"),
					msdsIndex.getMSDSfield("msdsIndexingPriorityId"),
					msdsIndex.getMSDSfield("labelCountryAbbrev"),
					msdsIndex.getMSDSfield("msdsId"),
					msdsIndex.getMSDSfield("not requiredPic"),
					msdsIndex.getMSDSfield("not requiredOriginal"),
					msdsIndex.getMSDSfield("companyId")
				];
				exclusions = exclusions.concat(addlExclusions);
				clearMSDS(exclusions);

				saveCustomerOverride[msdsIndex.component] = false;
				toggleQcBlocks();
			}
			else {
				var idOnly = msdsIndex.getMSDSfield("idOnly");
				idOnly.value = j$(idOnly).data("prev");
			}
		}
		else {
			toggleQcBlocks();
		}
	}

	msdsIndex.blockDisplays = function(block,value,co) {
		if(value == "N/L" || value == "N/A" || value ==  "" || value == "N/F") {
			disableField(block,true,true,co);
		}
		else if(value == ">>" || value == ">" || value == ">=" || value == "<" || value == "<=") {
			disableField(block,false,true,co);
		}
		else {
			disableField(block,false,false,co);

			var basisWater = msdsIndex.getMSDSfield('specificGravityBasisWater',co);
			var basisAir = msdsIndex.getMSDSfield('specificGravityBasisAir',co);
			if(basisWater != null && basisAir != null && ! (basisWater.checked || basisAir.checked)) {
				basisWater.checked = true;
			}
		}

		var s = msdsIndex.getMSDSfield(block +'Source',co);
		if(s != null && s.value == "") {
			s.selectedIndex = 1;
		}
	}

	function storeSelectOldVal(selectNode) {
		j$(selectNode).focus(function() {
			j$(this).data("prev",this.value);
		})
	}

	/**
	 * exclusions: an array of specific fields that will not be cleared
	 * section: the MSDS field id of the section to be cleared; if undefined, everything on the page is cleared
	 */
	function clearMSDS(exclusions,section) {
		if (section == undefined) {
			section = "msdsDetailDiv";
		}
		var resultsFrame = $(section+msdsIndex.component);
		j$(resultsFrame).find("input:text,select").not(exclusions).val("");
		j$(resultsFrame).find("input:checkbox,input:radio").not(exclusions).attr("checked",false);
		j$(resultsFrame).find("textarea").not(exclusions).html("");

		if (section == "msdsDetailDiv" || section == "ghsfields") {
			msdsIndex.getMSDSfield("labelCountryAbbrev").value = "USA";
		}
		if (section == "msdsDetailDiv" || section == "ghspictograms") {
			msdsIndex.getMSDSfield("not requiredPic").checked = true;
			msdsIndex.getMSDSfield("not requiredOriginal").checked = true;
			j$("input:checkbox[name$='onSds']").attr("checked", true);
			j$("input:checkbox[id$='OriginalOnSds']").attr("checked", true);
			j$("label[id$='OnSdsMsg']").hide();
		}
		if (section == "msdsDetailDiv" || section == "hazardstatements" || section == "precautionarystatements") {
			var stmts = null;

			var j = j$("input[id$='isFromMsds']");
			j = j.add("input[id$='originalIsFromMsds']");

			var l = j$("label[id$='isFromMsdsMsg']");

			if (section == "hazardstatements") {
				j = j.filter("input[id^='msdshazards']");
				l = l.filter("label[id^='msdshazards']");
				stmts = j$(msdsIndex.getMSDSfield("ghsHazards")).children("li");
				j$(msdsIndex.getMSDSfield("editHazards")).show();
				j$(msdsIndex.getMSDSfield("finishEditHazards")).hide();
				j$(msdsIndex.getMSDSfield("clearHazards")).hide();
				j$(msdsIndex.getMSDSfield("resetHazards")).hide();
			}
			else if (section == "precautionarystatements") {
				j = j.filter("input[id^='msdsprecautions']");
				l = l.filter("label[id^='msdsprecautions']");
				stmts = j$(msdsIndex.getMSDSfield("ghsPrecautions")).children("li");
				j$(msdsIndex.getMSDSfield("editPrecautions")).show();
				j$(msdsIndex.getMSDSfield("finishEditPrecautions")).hide();
				j$(msdsIndex.getMSDSfield("clearPrecautions")).hide();
				j$(msdsIndex.getMSDSfield("resetPrecautions")).hide();
			}
			else if (section == "msdsDetailDiv"){
				stmts = j$(msdsIndex.getMSDSfield("ghsHazards")).children("li");
				var pStmts = j$(msdsIndex.getMSDSfield("ghsPrecautions")).children("li");
				stmts = stmts.add(pStmts);
				j$(msdsIndex.getMSDSfield("editHazards")).show();
				j$(msdsIndex.getMSDSfield("finishEditHazards")).hide();
				j$(msdsIndex.getMSDSfield("clearHazards")).hide();
				j$(msdsIndex.getMSDSfield("resetHazards")).hide();
				j$(msdsIndex.getMSDSfield("editPrecautions")).show();
				j$(msdsIndex.getMSDSfield("finishEditPrecautions")).hide();
				j$(msdsIndex.getMSDSfield("clearPrecautions")).hide();
				j$(msdsIndex.getMSDSfield("resetPrecautions")).hide();
			}

			j.val("1");
			l.hide();
			stmts.hide();
		}

		if (section == "msdsDetailDiv") {
			window["compositionGrid"+msdsIndex.component].clearAll();
			msdsIndex.addCompositionRow();
		}
	}

	function clearCoData(exclusions) {
		var resultsFrame = msdsIndex.getMSDSfield("propertiesColumn", true);
		j$(resultsFrame).find("input:text,select").not(exclusions).val("");
		j$(resultsFrame).find("input:checkbox,input:radio").not(exclusions).attr("checked",false);
		j$(resultsFrame).find("textarea").not(exclusions).html("");

	}

	function toggleQcBlocks() {
		var globalQcStatus = msdsIndex.getMSDSfieldValue("qcStatus");
		var coQcStatus = msdsIndex.getMSDSfieldValue("qcStatus",true);
		var globalDEC = msdsIndex.getMSDSfield("dataEntryComplete") && msdsIndex.getMSDSfield("dataEntryComplete").checked;
		var coDEC = true;
		if (msdsIndex.getMSDSfield("dataEntryComplete",true)) {
			coDEC = msdsIndex.getMSDSfield("dataEntryComplete",true).checked && coDataUpToStandard;
		}

		try {
			msdsIndex.getMSDSfield("submitQcButton").style.display = "none";
			msdsIndex.getMSDSfield("rejectButton").style.display = "none";
			msdsIndex.getMSDSfield("rejectOutOfScopeButton").style.display = "none";
			msdsIndex.getMSDSfield("approveQcButton").style.display = "none";
			msdsIndex.getMSDSfield("submitCustomerQcButton").style.display = "none";
			msdsIndex.getMSDSfield("approveGlobalQcButton").style.display = "none";
			msdsIndex.getMSDSfield("approveCustomerQcButton").style.display = "none";
			msdsIndex.getMSDSfield("reportProblemButton").style.display = "none";
			msdsIndex.getMSDSfield("reAssignToVendorButton").style.display = "none";
			msdsIndex.getMSDSfield("authorSdsButton").style.display = "none";
		} catch(x) {}

		var vendorTask = $v("vendorTask");
		var maintenance = $v("maintenance");
		var masterData = $v("masterData");
		var catalogQueueRowTask = msdsIndex.getMSDSfieldValue("task");
		var catalogQueueRowStatus = $v("catalogQueueRowStatus");
		var isWescoModule = $v("isWescoModule");
		var wescoIsSupplier = $v("supplierIsWesco");
		
		if (masterData == "Y" || (vendorTask == "Y" && isWescoModule == "Y" && wescoIsSupplier == "Y")) {
			j$(msdsIndex.getMSDSfield("componentMsds")).parent().css("display","block");
			j$(msdsIndex.getMSDSfield("comments")).parent().css("display","block");
			if ($v("beforeOrAfterSdsQc") == "BEFORE") {
				hideQcDropdowns();
			}
			var requestStatus = $v("requestStatus");
			var companyInput = $("companyId");
			var companyId = "";
			if (companyInput) {
				companyId = companyInput.value;
			}
			if (requestStatus == 6 || (companyId=="SEAGATE" && requestStatus == 5)) {
				var approvalPrivilege = true;
				// check to see if any components are pending approval where current user is submitter
				for (var index = 0; index < msdsIndex.totalComponents; index++) {
					var globalQcStatus = $v("msds["+index+"].qcStatus");
					var coQcStatus = $v("co["+index+"].qcStatus");
					if (globalQcStatus == "pendingQc" || coQcStatus == "pendingQc") {
						approvalPrivilege = false;
						break;
					}
				}
				if ( ! approvalPrivilege) {
					$("requestApprovalButton").style.display = "none";
				}
			}
		}

		if (maintenance == "Y") {
			var material = msdsIndex.getMSDSfieldValue("materialIdSearch");
			var revision = msdsIndex.getMSDSfieldValue("revisionDateSearch");
			if (msdsIndex.getMSDSfieldValue("content").length > 0) {
				msdsIndex.getMSDSfield("authorSdsButton").style.display = "inline";
			}
			j$("#gridUpdateLinks"+msdsIndex.component).css("display","inline-block");
			if (material != null && material.trim().length > 0 &&
				revision != null && revision.trim().length > 0 &&
				msdsIndex.getMSDSfieldValue("idOnly") != "Y") {
				if (globalDEC) {
					if (globalQcStatus == "unapproved") {
						if (coQcStatus == "unapproved") {
							msdsIndex.getMSDSfield("approveQcButton").style.display = "inline";
						}
						else {
							msdsIndex.getMSDSfield("approveGlobalQcButton").style.display = "inline";
						}
					}
					else if (coQcStatus == "unapproved") {
						msdsIndex.getMSDSfield("approveCustomerQcButton").style.display = "inline";
					}
					else if (coQcStatus == "pendingQc" || coQcStatus == "complete" || coDEC){
						// do Nothing
					}
					else {
						msdsIndex.getMSDSfield("submitCustomerQcButton").style.display = "inline";
					}
				}
				else {
					if (globalQcStatus == "unapproved") {
						if (coQcStatus == "unapproved") {
							msdsIndex.getMSDSfield("approveQcButton").style.display = "inline";
						}
						else if (coQcStatus == "pendingQc" || coQcStatus == "complete" || coDEC) {
							msdsIndex.getMSDSfield("approveGlobalQcButton").style.display = "inline";
						}
						else {
							msdsIndex.getMSDSfield("submitCustomerQcButton").style.display = "inline";
							msdsIndex.getMSDSfield("approveGlobalQcButton").style.display = "inline";
						}
					}
					else if(globalQcStatus == "pendingQc" || globalQcStatus == "complete") {
						if (coQcStatus == "unapproved") {
							//do nothing
						}
						else if (coQcStatus == "pendingQc" || coQcStatus == "complete" || coDEC) {
							// do Nothing
						}
						else {
							msdsIndex.getMSDSfield("submitCustomerQcButton").style.display = "inline";
						}
					}else {
						if (coQcStatus == "unapproved") {
							// do nothing
						}
						else {
							msdsIndex.getMSDSfield("submitQcButton").style.display = "inline";
						}
					}
				}
			}else {
				msdsIndex.getMSDSfield("submitQcButton").style.display = "inline";
			}
		}
		else if (masterData != "Y") {
			var userIsAssignee = $v("currentUser") == $v("qAssignee")
			var isWescoTaskInWescoModule = $v("isWescoModule") == "Y" && $v("supplierIsWesco") == "Y";
			j$("#gridUpdateLinks"+msdsIndex.component).css("display","inline-block");
			hideQcDropdowns();
			if (catalogQueueRowStatus == "Assigned") {
				if ( ! userIsAssignee) {
					msdsIndex.getMSDSfield("saveButton").style.display = "none";
					disablePage();
				}
				else {
					if (catalogQueueRowTask != "SDS Sourcing") {
						disableMfrInputs();
						disableMaterialInputs();
					}
					if ($v("hasAltSupplier") == 'Y') {
						msdsIndex.getMSDSfield("reAssignToVendorButton").style.display = "inline";
					}
					msdsIndex.getMSDSfield("submitQcButton").style.display = "inline";
					if (isWescoTaskInWescoModule) {
						if ($v("catalogAddMSDSPermission") == "Y")
							msdsIndex.getMSDSfield("rejectButton").style.display = "inline";
						if ($v("hasOutOfScopeFeature") == "Y")
							msdsIndex.getMSDSfield("rejectOutOfScopeButton").style.display = "inline";
					} else if ($v("supplierIsWesco") != "Y")
						msdsIndex.getMSDSfield("reportProblemButton").style.display = "inline";
				}
			}
			else if (catalogQueueRowStatus == "Pending QC") {
				if (isWescoTaskInWescoModule || (isWescoModule != 'Y' && !userIsAssignee)) {
					if (catalogQueueRowTask != "SDS Sourcing") {
						disableMfrInputs();
						disableMaterialInputs();
					}
					msdsIndex.getMSDSfield("approveQcButton").style.display = "inline";
					if (!isWescoTaskInWescoModule)
						msdsIndex.getMSDSfield("reportProblemButton").style.display = "inline";
				}
				else {
					msdsIndex.getMSDSfield("saveButton").style.display = "none";
					disablePage();
				}
			}
			else {
				disablePage();
				msdsIndex.getMSDSfield("saveButton").style.display = "none";
				if (catalogQueueRowStatus == "Pending Approval") {
					if (!isWescoTaskInWescoModule)
						msdsIndex.getMSDSfield("reportProblemButton").style.display = "inline";
					msdsIndex.getMSDSfield("attachedDocuments").disabled = false;
				}
				else if (catalogQueueRowStatus == "Approved" || catalogQueueRowStatus == "Closed") {
					msdsIndex.getMSDSfield("attachedDocuments").disabled = false;
				}
			}
		}
		else {
			try {
				msdsIndex.getMSDSfield("saveButton").style.display = "none";
			} catch(x) {}
			j$("#gridUpdateLinks"+msdsIndex.component).css("display","inline-block");
		}
	}

	function toggleBlocks(isCo) {
		msdsIndex.blockDisplays('specificGravity',msdsIndex.getMSDSfieldValue("specificGravityDetect",isCo),isCo);
		msdsIndex.blockDisplays('density',msdsIndex.getMSDSfieldValue('densityDetect',isCo),isCo);
		msdsIndex.blockDisplays('flashPoint',msdsIndex.getMSDSfieldValue('flashPointDetect',isCo),isCo);
		msdsIndex.blockDisplays('boilingPoint',msdsIndex.getMSDSfieldValue('boilingPointDetect',isCo),isCo);
		msdsIndex.blockDisplays('ph',msdsIndex.getMSDSfieldValue('phDetect',isCo),isCo);
		msdsIndex.blockDisplays('vaporPressure',msdsIndex.getMSDSfieldValue('vaporPressureDetect',isCo),isCo);
		// if not for CO, then toggle CO Blocks also
		if ( ! isCo) {
			try {
				toggleBlocks(true);
			}
			catch(ex) {
				// do nothing; sections probably don't exist, so just ignore
			}
		}
	}

	function disableField(block,value, limit,co) {
		var val = msdsIndex.getMSDSfield(block,co);
		if(val != null) {
			if (value) {
				j$(val.parentElement).addClass("invisible");
			}
			else {
				j$(val.parentElement).removeClass("invisible");
			}
			val.disabled = value;
		}
		var units = msdsIndex.getMSDSfield(block + 'Unit',co);
		if(units != null) {
			if (value) {
				j$(units.parentElement).addClass("invisible");
			}
			else {
				j$(units.parentElement).removeClass("invisible");
			}
			units.disabled =  value;
		}
		var basis = msdsIndex.getMSDSfield(block + 'BasisAir',co);
		if(basis != null) {
			if (value) {
				j$(basis.parentElement).addClass("invisible");
			}
			else {
				j$(basis.parentElement).removeClass("invisible");
			}

			if (basis.readonly == "readonly") {
				basis.disabled = "disabled";
			}
			else {
				basis.disabled= value;
			}
		}
		basis = msdsIndex.getMSDSfield(block + 'BasisWater',co);
		if(basis != null) {
			if (value) {
				j$(basis.parentElement).addClass("invisible");
			}
			else {
				j$(basis.parentElement).removeClass("invisible");
			}
			if (basis.readonly == "readonly") {
				basis.disabled = "disabled";
			}
			else {
				basis.disabled = value;
			}
		}
		var temp = msdsIndex.getMSDSfield(block + 'Temp',co);
		if(temp != null) {
			if (value) {
				j$(temp.parentElement).addClass("invisible");
			}
			else {
				j$(temp.parentElement).removeClass("invisible");
			}
			temp.disabled = value;
		}
		var tempUnit = msdsIndex.getMSDSfield(block + 'TempUnit',co);
		if(tempUnit != null) {
			if (value) {
				j$(tempUnit.parentElement).addClass("invisible");
			}
			else {
				j$(tempUnit.parentElement).removeClass("invisible");
			}
			tempUnit.disabled = value;
		}

		var upper = msdsIndex.getMSDSfield(block + 'Upper',co);
		if(upper != null) {
			if (limit) {
				j$(upper.parentElement).addClass("invisible");
			}
			else {
				j$(upper.parentElement).removeClass("invisible");
			}
			upper.disabled = limit;
		}
		var lower = msdsIndex.getMSDSfield(block + 'Lower',co);
		if(val == null && lower != null) {
			if (value) {
				j$(lower.parentElement).addClass("invisible");
			}
			else {
				j$(lower.parentElement).removeClass("invisible");
			}
			lower.disabled = value;
		}
		else if(lower != null) {
			if (limit) {
				j$(lower.parentElement).addClass("invisible");
			}
			else {
				j$(lower.parentElement).removeClass("invisible");
			}
			lower.disabled = limit;
		}
	}

	function setRadio(radioName, value, co) {
		var msdsField = null;
		switch(value) {
			case "Y":
				msdsField = msdsIndex.getMSDSfield(radioName + "Y",co);break;
			case "N":
				msdsField = msdsIndex.getMSDSfield(radioName + "N",co);break;
			case "X":
				msdsField = msdsIndex.getMSDSfield(radioName + "X",co);break;
			case "A":
				msdsField = msdsIndex.getMSDSfield(radioName + "Air",co);break;
			case "W":
				msdsField = msdsIndex.getMSDSfield(radioName + "Water",co);break;
			case "U":
				msdsField = msdsIndex.getMSDSfield(radioName + "U",co);break;
			case "NA":
				msdsField = msdsIndex.getMSDSfield(radioName + "NA",co);break;
			case "NL":
				msdsField = msdsIndex.getMSDSfield(radioName + "NL",co);break;
			default:
				msdsField = null;
		}

		if (msdsField != null) {
			msdsField.checked = true;
		}
	}


	/* End of Section Functions */
	/* Material QC Page */


	/*msdsIndex.toggleItem = function(idx) {
        $("itemDiv"+msdsIndex.component).style.display = "none";
        $("itemLink"+msdsIndex.component).className = "tabLeftSide";
        $("itemLink"+idx).className = "selectedTab";
        $("itemLinkSpan"+msdsIndex.component).className = "tabRightSide";
        $("itemLinkSpan"+idx).className = "selectedSpanTab";
        selectedRowId = componentRowId[msdsIndex.component];
        var prevIdx = msdsIndex.component;
        msdsIndex.component = idx;
        maxComponent = (maxComponent < (idx+1))?(idx+1):maxComponent;

        if ($("itemDiv"+idx) == null) {
            msdsIndex.showWait(messagesData.loading);
            j$.ajax({
                url:"msdsindexingsearch.do",
                cache:false,
                data:{uAction: "getComponent", requestId: $v("requestId"), lineItem: $v("lineItem"), component: idx},
                success: function(data) {
                    j$("#itemDiv"+prevIdx).after(data);
                    toggleQcBlocks();
                    toggleBlocks();
                    bindCoChange();
                    msdsIndex.countryChanged();
                    //msdsIndex.checkDataEntryComplete();
                    storeSelectOldVal(msdsIndex.getMSDSfield("idOnly"));
                    setTimeout(function(){msdsIndex.initializeCompositionGrids();});
                },
                complete: function() {
                    msdsIndex.resizeMainPage();
                    msdsIndex.stopWaiting();
                }
            });
        }
        else {
            $("itemDiv"+idx).style.display = "block";
            msdsIndex.resizeMainPage();
        }

        return false;
    }*/

	msdsIndex.toggleItem = function(idx) {
		$("itemDiv"+msdsIndex.component).style.display = "none";
		$("itemLink"+msdsIndex.component).className = "tabLeftSide";
		$("itemLink"+idx).className = "selectedTab";
		$("itemLinkSpan"+msdsIndex.component).className = "tabRightSide";
		$("itemLinkSpan"+idx).className = "selectedSpanTab";
		selectedRowId = componentRowId[msdsIndex.component];
		msdsIndex.component = idx;
		maxComponent = (maxComponent < (idx+1))?(idx+1):maxComponent;

		if ($("itemDiv"+idx) != null) {
			toggleQcBlocks();
			toggleBlocks();
			bindCoChange();
			msdsIndex.countryChanged();
			//msdsIndex.checkDataEntryComplete();
			storeSelectOldVal(msdsIndex.getMSDSfield("idOnly"));
			//setTimeout(function(){msdsIndex.initializeCompositionGrids();});
			$("itemDiv"+idx).style.display = "block";
			msdsIndex.resizeMainPage();
		}
		//else {
		//}

		return false;
	}

	function loadItem(idx) {
		if ($("itemLink"+idx) != null) {
			j$.ajax({
				url:"msdsindexingcomponent.do",
				cache:false,
				data:{uAction: "getComponent", requestId: $v("requestId"), lineItem: $v("lineItem"), component: idx, generateSdsFromSequence: $v("generateSdsFromSequence")},
				success: function(data) {
					j$("#itemDiv0").after(data);
					j$("#itemDiv"+idx).css("display","none");
				},
				complete: function() {
					if (msdsIndex.getMSDSfield("compositionColumn")) {
						msdsIndex.initializeCompGrids(idx);
					}
					if ( ! isSdsRequired()) {
						disableAuthorInput(true);
					}
					$("itemLink"+idx).style.display = "block";
					componentsLoaded++;
					if (componentsLoaded == msdsIndex.totalComponents) {
						if (awaitingSave) {
							msdsIndex.submitSave();
						}
						else if (awaitingApproval) {
							msdsIndex.submitApprove();
						}
						else if (awaitingRejection) {
							msdsIndex.submitRejection();
						}
					}
					/*else {
                        loadItem(idx+1);
                    }*/
				}
			});
			loadItem(idx+1);
		}
	}

	msdsIndex.viewRequest = function() {
		var requestId = $v('requestId') ? $v('requestId') : $v('catalogAddRequestId') ? $v('catalogAddRequestId') : '';
		var lineItem = $v('lineItem') ? $v('lineItem') : $v('catalogAddRequestLineItem') ? $v('catalogAddRequestLineItem') : '';
		var loc = "msdsindexingmain.do?uAction=viewRequest";
		loc += "&requestId="+requestId;
		loc += "&lineItem="+lineItem;
		loc += "&companyId="+$v('companyId');
		loc += "&workItemTask="+encodeURIComponent($v('catalogQueueRowTask'));
		children[children.length] = openWinGeneric(loc,"OriginalRequest","650","530","yes","50","50","20","20","no");
	}

	msdsIndex.viewKitDocs = function() {
		var loc = "catalogaddreqmsdsmain.do?uAction=viewKitDocs&requestId="+$v("requestId")+"&companyId="+msdsIndex.getMSDSfieldValue("companyId")+"&lineItem="+$v("lineItem");
		children[children.length] = openWinGeneric(loc,"showFilesList","650","500","yes","50","50","20","20","no");
	}

	msdsIndex.uploadKitDocs = function() {
		msdsIndex.showWait("Please wait...");
		var fileName = $v("requestId");
		var url = 'uploadfile.do?fileName='+fileName+"&modulePath=catalogAddKitMsds&allowMultiple=true&requestId="+$v("requestId")+"&companyId="+msdsIndex.getMSDSfieldValue("companyId")+"&lineItem="+$v("lineItem");
		children[children.length] = openWinGeneric(url,"uploadfile",500,200,'yes' );
	}

	msdsIndex.generateSdsNumber = function(isKit) {
		isKitSdsNumber = isKit;
		var url = 'getsdsfromsequence.do';
		children[children.length] = openWinGeneric(url,"getsdsfromsequence",500,200,'yes' );
	}

	msdsIndex.generateSdsNumberChanged = function(newId) {
		if (isKitSdsNumber)
			$("customerMixtureNumber").value = newId;
		else
			msdsIndex.getMSDSfield("componentMsds").value = newId;
	}


	/* End of Material QC Page */
	/* Customer Override */


	msdsIndex.changeCustomerCompany = function() {
		msdsIndex.showWait(messagesData.loading);
		j$.ajax({
			url:"msdsindexingcomsdsdetail.do",
			cache:false,
			data:{uAction: "getCoData",
				materialId: msdsIndex.getMSDSfieldValue("materialIdSearch"),
				revisionDate: msdsIndex.getMSDSfieldValue("revisionDateSearch"),
				companyId: msdsIndex.getMSDSfieldValue("companyId"),
				mfgId: msdsIndex.getMSDSfieldValue("mfgIdSearch"),
				component: msdsIndex.component
			},
			success: function(data) {
				j$(msdsIndex.getMSDSfield("propertiesColumn", true)).html(data);
				j$("#gridUpdateLinks").css("display","inline-block");
				msdsIndex.getMSDSfield("dataEntryComplete",true).checked = (coDataEntryComplete === 'true');
				msdsIndex.getMSDSfield("dataEntryStandard",true).value = coDataEntryStandard;
				toggleBlocks(true);
				toggleQcBlocks();
				bindCoChange();
				showGlobalQcFields(msdsIndex.getMSDSfieldValue("qcStatus",true) != "unapproved");
				var qcApprovedField = msdsIndex.getMSDSfield("qcApproved", true);
				if (qcApprovedField) {
					qcApprovedField.checked = (msdsIndex.getMSDSfieldValue("qcStatus", true) == "complete");
				}
				//msdsIndex.checkCODataEntryComplete();
			},
			complete: function() {
				msdsIndex.resizeMainPage();
				msdsIndex.stopWaiting();
			}
		});
	}

	function bindCoChange() {
		j$("input[id^='co["+msdsIndex.component+"]']").bind("change", function() {
			saveCustomerOverride[msdsIndex.component] = true;
			msdsIndex.getMSDSfield("saveCustomerOverride").value = "true";
		});
		// the clear buttons do not have IDs so they must be handled separately
		j$("input[id^='co["+msdsIndex.component+"]'] ~ input:button").bind("click", function() {
			saveCustomerOverride[msdsIndex.component] = true;
			msdsIndex.getMSDSfield("saveCustomerOverride").value = "true";
		});
		j$("select[id^='co["+msdsIndex.component+"]']").bind("change", function() {
			saveCustomerOverride[msdsIndex.component] = true;
			msdsIndex.getMSDSfield("saveCustomerOverride").value = "true";
		});
	}

	function setCoDataEntryComplete(){
		if(msdsIndex.getMSDSfield("dataEntryComplete",true).checked) {
			msdsIndex.getMSDSfield("dataEntryComplete",true).checked = true;
		}
		else {
			msdsIndex.getMSDSfield("dataEntryComplete",true).checked = false;
		}
	}

	function showGlobalQcFields(disable) {
		var qcFields = [
			"manufacturer",
			"materialDescription",
			"tradeName",
			"productCode",
			"revisionDate",
			"msdsAuthor",
			"content",
			"labelAddress",
			"signalWord",
			"hazardStatements",
			"precautionaryStatements",
			"ghsPictograms",
			"nfpa",
			"hmis",
			"ph",
			"physicalState",
			"vaporDensity",
			"evaporationRate",
			"sara",
			"density",
			"flashPoint",
			"boilingPoint",
			"specificGravity",
			"vaporPressure",
			"solids",
			"voc",
			"vocLessH2oExempt"
			//"vocLessH2oExempt",
			//"composition"
		];

		for (i in qcFields) {
			j$(msdsIndex.getMSDSfield(qcFields[i]+"QcBlock")).removeClass("invisible");
			msdsIndex.getMSDSfield(qcFields[i]+"QcErrorType").disabled = disable;
		}

		msdsIndex.getMSDSfield("compositionQcBlock").style.display = "inline-block";
		msdsIndex.getMSDSfield("compositionQcErrorType").disabled = disable;
	}


	/* End of Customer Override */
	/* Composition Table */


	msdsIndex.lookupCasNumber = function() {
		var requestId = $v("requestId");
		var loc = "casnumbersearchmain.do";
		msdsIndex.showWait(formatMessage(messagesData.waitingFor, messagesData.cas));
		children[children.length] = openWinGeneric(loc,"cassearch","650","500","yes","50","50","20","20","no");
	}

	msdsIndex.casNumberChanged = function(casNum, chemicalName) {
		var chemName = chemicalName;
		if (chemName != null && chemName.length > 160) {
			chemName = chemName.substring(0,160);
		}
		//setCellValue("casNumber", casNum );
		//setCellValue("msdsChemicalName", chemName );
		getCell("casNumber").setValue(casNum);
		getCell("msdsChemicalName").setValue(chemName);
		msdsIndex.stopWaiting();
	}

	msdsIndex.addCompositionRow = function() {
		var selectedGrid = window["compositionGrid"+msdsIndex.component];
		var id = selectedGrid.getRowsNum() + 1;
		selectedGrid.addRow(id, ['Y', 'Y', 'Y', 'Y', '', '', '', '', '', '',  false, '', ''], id - 1);
		msdsIndex.addLookupToRow(selectedGrid, id);
		selectedGrid.selectRowById(id);
		var sectionDropdown = getGridElement(id, "sdsSectionId");
		sectionDropdown.options[1].selected = true;
	}

	msdsIndex.clearCompositionRow = function(rowId) {
		setRowCellValue(rowId, 1, 'Y');
		setRowCellValue(rowId, 2, 'Y');
		setRowCellValue(rowId, 3, 'Y');
		setRowCellValue(rowId, 4, '');
		// setRowCellValue(rowId, 5, '');
		setRowCellValue(rowId, 6, '');
		setRowCellValue(rowId, 7, '');
		setRowCellValue(rowId, 8, '');
		setRowCellValue(rowId, 9, '');
		setRowCellValue(rowId, 10, false);
		setRowCellValue(rowId, 11, '');
		setRowCellValue(rowId, 12, '');
		var sectionDropdown = getGridElement(rowId, "sdsSectionId");
		sectionDropdown.options[1].selected = true;
	}

	msdsIndex.initializeCompGrids = function(component) {
		var config = window["compositionGridConfig" + component];
		if (config) {
			initGridWithGlobal(config);
			msdsIndex.addAllLookupsForGrid(component);

			reSizeCoLumnWidths(window["compositionGrid" + component]);
		}
	}

	msdsIndex.initializeCompositionGrids = function() {
		msdsIndex.initializeCompGrids(msdsIndex.component);
		//j$("#msdsComposition"+msdsIndex.component+" div").css("position","static");
	}

	msdsIndex.addLookupToRow = function(theGrid, rowId) {
		var caslookuphtml = '<input name="searchForCAS" type="button" value="..." class="lookupBtn" onmouseover="this.className=\'lookupBtn lookupBtnOver\'" onmouseout="this.className=\'lookupBtn\'"  onclick="msdsIndex.lookupCasNumber();">' +
			'&nbsp;<input class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onmouseout="this.className=\'smallBtns\'" name="clearradio1" value="' + messagesData.clear + '" onclick="msdsIndex.clearCompositionRow(' + rowId + ')" type="button"/>';
		gridCell(theGrid, rowId, "casLookup").setCValue(caslookuphtml);
	}

	msdsIndex.addAllLookupsForGrid = function(component) {
		var theGrid = window["compositionGrid" + component];
		var numRows = theGrid.getRowsNum();
		for (var rowId = 1; rowId <= numRows; rowId++) {
			msdsIndex.addLookupToRow(theGrid, rowId);
		}
	}

	msdsIndex.traceToggled = function(rowId, colId) {
		var checkBox = getGridElement(rowId, "trace");
		if (checkBox != null && checkBox.checked != null && checkBox.checked) {
			setRowCellValue(rowId,"percentPermission", "N");
			setRowCellValue(rowId,"percentUpperPermission", "N");
			setRowCellValue(rowId,"percentLowerPermission", "N");
		}
		else {
			setRowCellValue(rowId,"percentPermission", "Y");
			setRowCellValue(rowId,"percentUpperPermission", "Y");
			setRowCellValue(rowId,"percentLowerPermission", "Y");
		}
		setRowCellValue(rowId,"percent", "");
		setRowCellValue(rowId,"percentUpper", "");
		setRowCellValue(rowId,"percentLower", "");
	}

	msdsIndex.checkPercent = function(rowId, colId, returnError) {
		var percentage = gridCell(window["compositionGrid"+msdsIndex.component],rowId,colId).getValue();
		var field = colId == 'percent' ? messagesData.percent : (colId == 'percentLower' ? messagesData.percentLower : messagesData.percentUpper);
		if (!isFloat(percentage, true)&& percentage != '&nbsp;') {
			getGridElement(rowId, colId).focus();
			alert(formatMessage(messagesData.floatError, messagesData.composition + " " + field));
			return false;
		}
		else if (percentage < 0 || percentage > 100) {
			getGridElement(rowId, colId).focus();
			alert(formatMessage(messagesData.rangeError, field, '0', '100'));
			return false;
		}
		return true;
	}

	msdsIndex.rowSelected = function(rowId, colId) {
		componentRowId[msdsIndex.component] = rowId;
		selectedRowId = rowId;
	}

	msdsIndex.getCasNumber = function() {
		return getCellValue("casNumber");
	}

	msdsIndex.getMsdsChemicalName = function () {
		return getCellValue("msdsChemicalName");
	}

	function setRowCellValue(rowId, colId, value) {
		gridCell(window["compositionGrid"+msdsIndex.component], rowId, colId).setValue(value);
	}

	function getGridElement(rowId, colId) {
		//Get by Id won't work with multiple grids, and get by name will return as many as there are grids with
		// at least as many rows as the rowId selected
		return j$("#compositionData"+msdsIndex.component).find("#"+colId+rowId).get(0);
	}

	function getCell(colId) {
		return gridCell(window["compositionGrid"+msdsIndex.component],selectedRowId,colId);
	}

	function getCellValue(colId) {
		return getCell(colId).getValue();
	}

	function setCellValue(colId, value) {
		getCell(colId).setValue(value);
	}

	function updateGrid(gridData) {
		var selectedGrid = window["compositionGrid"+msdsIndex.component];
		selectedGrid.clearAll();
		selectedGrid.parse(gridData,"json");
		var numRows = selectedGrid.getRowsNum();
		msdsIndex.addAllLookupsForGrid(msdsIndex.component);
	}

	msdsIndex.viewUploadedSDS = function() {
		// hidden input removed
		var requestId = $v('requestId') ? $v('requestId') : $v('catalogAddRequestId') ? $v('catalogAddRequestId') : '';
		children[children.length] = openWinGeneric("managecataddsds.do?requestId=" + requestId + "&editable=N", "problemHistory", "500","300");
	}
	/* End of Composition Table */

}(window.msdsIndex = window.msdsIndex || {}));

manufacturerChanged = msdsIndex.manufacturerChanged;
materialChanged = msdsIndex.materialChanged;
showTransitWin = msdsIndex.showWait;
stopShowingWait = msdsIndex.stopWaiting;
closeTransitWin = msdsIndex.stopWaiting;
casNumberChanged = msdsIndex.casNumberChanged;
getCasNumber = msdsIndex.getCasNumber;
getMsdsChemicalName = msdsIndex.getMsdsChemicalName;
setNewRevisionDate = msdsIndex.setNewRevisionDate;
updateContent = msdsIndex.updateContent;
problemReported = msdsIndex.problemReported;
calendarDateChangedCallBack  = msdsIndex.calendarDateChangedCallBack;
setNewMsdsAuthor = msdsIndex.setNewMsdsAuthor;
confirmReject = msdsIndex.confirmReject;

//window.msdsIndex = window.msdsIndex || {};