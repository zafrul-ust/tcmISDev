var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;
var closeOpenerTransitWin = true;

function $(a) {
	return document.getElementById(a);
}

function editOnLoad(action) {
	closeTransitWin();
	$("uAction").value = action;
	bindACkeys();
	if ((action == 'submitEditNewMaterial' || action == 'addMaterial' || action == 'submitNewComponent') && !showErrorMessage) {
		opener.addNewMaterialToList();
		/*try {
             opener.parent.closeOpenerTransitWin = false;
        }catch(e) {}*/
		closeOpenerTransitWin = false;
		window.close();
	}else {
		if (showErrorMessage) {
			showMessageDialog();
		}
	}
}

function closeThisWindow() {
	try {
		if (closeOpenerTransitWin) {
			opener.closeTransitWin();
		}
	}catch(e){}
}

function checkDeleteLine()
{
	var uAction = $v('uAction');
	var calledFrom = $v('calledFrom');
	if( (calledFrom == 'searchMsds' || calledFrom == 'catalogAddMsdsRequest') && uAction != 'submitEditNewMaterial' && uAction != 'addMaterial')
		if(opener.parent.opener.deleteNewMaterialLine != null && typeof(opener.parent.opener.deleteNewMaterialLine) != 'undefined')
			opener.parent.opener.deleteNewMaterialLine($v("requestId"),$v("lineItem"),$v("partId"),"Y");
		else
			opener.deleteNewMaterialLine($v("requestId"),$v("lineItem"),$v("partId"),"N");
}

function cancel()
{
	checkDeleteLine();
	window.close();
}

function submitUpdate() {
	if (auditData()) {
		showTransitWin();
		if ($v("calledFrom") == 'viewRequestPopup')
			$("uAction").value = 'submitNewComponent';
		else
			$("uAction").value = 'submitEditNewMaterial';
		document.genericForm.submit();
	}else {
		return false;
	}
}

function auditReplaceMsds() {
	var validMsds = true;
	var replaceMsds = j$("#replacesMsds").val();
	if(typeof (replaceMsds) != 'undefined')
	{
		var replaceMsdsValidator = j$("#replacesMsdsValidator").val();
		validMsds = (replaceMsds == replaceMsdsValidator);
	}
	return validMsds;
}

function auditData() {
	var result = true;

	var missingFields = "";
	//material data
	/*
    if ($v("customerMsdsNumber").trim().length == 0) {
        missingFields += "\t"+messagesData.lmMsds+"\n";
    }*/

	if ($v("materialDesc").trim().length == 0) {
		missingFields += "\t"+messagesData.materialDescription+"\n";
	}

	if ($v("manufacturer").trim().length == 0) {
		missingFields += "\t"+messagesData.manufacturer+"\n";
	}

	if ($v("mfgTradeName").trim().length == 0) {
		missingFields += "\t"+messagesData.mfgTradeName+"\n";
	}
	/*
    if ($v("partSize").trim().length == 0) {
        missingFields += "\t"+messagesData.size+"\n";
    }
    if ($v("sizeUnit").length == 0) {
        missingFields += "\t"+messagesData.unit+"\n";
    }
    */

	if (missingFields.length > 0) {
		alert(messagesData.validvalues+"\n"+missingFields);
		result = false;
	}
	if (!auditReplaceMsds()) {
		alert(messagesData.invalidReplaceMsds);
		result = false;
	}
	return result;
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function isArray(testObject) {
	return testObject &&
		!( testObject.propertyIsEnumerable('length')) &&
		typeof testObject === 'object' &&
		typeof testObject.length === 'number';
}

function regExcape(str) {
// if more special cases, need more lines.
	return str.replace(new RegExp("[([]","g"),"[$&]");
}

function lookupManufacturer() {
	var manufacturer = $v("manufacturer").trim();
	var loc = "manufacturersearchmain.do?userAction=search&searchArgument="+manufacturer+"&allowNew=N";
	children[children.length] = openWinGeneric(loc,"manufacturersearch","500","500","yes","50","50","20","20","no");
}

function manufacturerChanged(newId,newDesc) {
	$("mfgId").value = newId;
	$("manufacturer").value = newDesc;
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
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
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function showMessageDialog()
{
	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showMessageDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 150);
		inputWin.setText(messagesData.errors);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject("messageDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("showMessageDialog").show();
	}
}

function closeMessageWin() {
	dhxWins.window("showMessageDialog").setModal(false);
	dhxWins.window("showMessageDialog").hide();
}

function closeAllchildren()
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
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


function bindACkeys() {
	var replacesMsds = "replacesMsds";
	var replacesMsdsValidator = "replacesMsdsValidator";
	// check the flag set in qplBind so that the function is not called every time the row renders
	if (j$("#" + replacesMsds).data("boundToKeystroke") !== true) {
		msdsBind(replacesMsds, replacesMsdsValidator);
	}
}

function msdsBind(el1, el2) {
	j$().ready(function() {
		function log(event, data, formatted) {
			j$("#" + el2).val(formatted.split(":")[0]);
			$(el1).className = "inputBox";
		}

		j$("#"+el1).result(log).next().click(function() {
			j$(this).prev().search();
		});

		j$("#" + el1).autocomplete("catalogaddrequest.do?uAction=msdsRequest",{
			extraParams: {
				requestId: function() { return j$("#requestId").val(); }
			},
			width: 500,
			max: 20,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 150,
			formatItem: function(data, i, n, value) {
				return  value;
			},
			formatResult: function(data, value) {
				return value;
			},
			parse: function(data) {
				var results = jQuery.parseJSON(data);
				var parsed = new Array();
				for (var row in results.CustomerMSDSNumberResults){
					parsed[parsed.length] = {
						data: results.CustomerMSDSNumberResults[row].customerMsdsNumber,
						value: results.CustomerMSDSNumberResults[row].customerMsdsNumber,
						result: results.CustomerMSDSNumberResults[row].customerMsdsNumber
					};
				}
				return parsed;
			}
		});

		j$("#" + el1).bind('keyup',(function(e) {
			var keyCode = (e.keyCode ? e.keyCode : e.which);

			if (keyCode != 13 /* Enter */ && keyCode != 9 /* Tab */) {
				invalidateRequestor(el1, el2);
			}
		}));
		// set a flag so that this function does not get called every time the row renders
		j$("#" + el1).data("boundToKeystroke", true);

		j$("#" + el1).result(log).next().click(function() {
			j$(this).prev().search();
		});

	});
}

function invalidateRequestor(el1, el2) {
	var requestorName = document.getElementById(el1);
	if (requestorName.value.length == 0) {
		requestorName.className = "inputBox";
	}
	else {
		requestorName.className = "inputBox red";
	}
	if (el2 != null) {
		var validator = j$("#"+el2);
		j$("#"+el2).val("");
	}
}