// config is either the text you want to display in the dialog
// or a config object having any of the following
// { text: "", // text to display in the body of the dialog 
//   modal: t/f, // whether dialog is modal or not (not implemented) 
//   severity: "Success"/"Info"/"Warning"/"Error", // changes color of dialog
//   dialogType: "alert"/"confirm"/"loader", // one or two buttons
//   headerText: "", // text to display in dialog header
//   continueText:"", // text to display on continue or confirm button (only button if dialogType = "alert")
//   cancelText: "", // text to display on cancel button
//   onCancel: function() { }, // action for cancel button 
//   onContinue: function() { } // action for continue button 
// }
function customDialog(config) {
	this.modalText = config;
	this.modal = true;
	this.dialogType = "alert";
	this.severity = "Info";
	this.headerText = "Confirm";
	this.continueText = "Continue";
	this.cancelText = "Cancel";
	this.cancelAction = function() {};
	this.continueAction = function() {};
	var continueFunction = "";
	var cancelFunction = "";
	
	if (config.text) {
		this.modalText = config.text;
		if (config.header) {
			this.headerText = config.header;
		}
		if (config.continueText) {
			this.continueText = config.continueText;
		}
		if (config.cancelText) {
			this.cancelText = config.cancelText;
		}
		if (config.onCancel) {
			this.cancelAction = config.onCancel;
		}
		if (config.onContinue) {
			this.continueAction = config.onContinue;
		}
		if (config.modal !== undefined) {
			this.modal = config.modal;
		}
		if (config.dialogType) {
			this.dialogType = config.dialogType;
		}
		if (config.severity) {
			this.severity = config.severity;
		}
	}
	
	try {
		displayOrCreateModal();
	}
	catch(e) {
		hideDialog();
		if (dialogType == "alert") {
			alert(modalText);
		}
		else if (dialogType == "confirm") {
			if (confirm(modalText)) {
				continueAction();
			}
		}
	}

	function displayOrCreateModal() {
		if (this.modalText == "destroy") {
			destroy();
		}
		else { 
			if (parent.document.getElementById("modality") == null) {
				createModal();
			}
			else {
				var textClassName = "modal-text";
				var headerClassName = "modal-header";
				if (dialogType == "loader") {
					textClassName += " interstitial";
					parent.document.getElementById("modalText").innerHTML = "";
					parent.document.getElementById("modalHeader").innerHTML = this.modalText;
				}
				else {
					parent.document.getElementById("modalText").innerHTML = this.modalText;
					parent.document.getElementById("modalHeader").innerHTML = this.headerText;

					if (this.severity == "Warning") {
						headerClassName += " warning";
					}
					else if (this.severity == "Success") {
						headerClassName += " success";
					}
					else if (this.severity == "Error") {
						headerClassName += " error";
					}
				}
				parent.document.getElementById("modalText").className = textClassName;
				parent.document.getElementById("modalHeader").className = headerClassName;
				var modalContent = parent.document.getElementById("modalDialog");
				
				modalContent.removeChild(parent.document.getElementById("modalCancelBtn"));
				var cancelButton = createCancelButton().get(0);
				modalContent.appendChild(cancelButton);
				
				modalContent.removeChild(parent.document.getElementById("modalContinueBtn"));
				var continueButton = createContinueButton().get(0);
				modalContent.appendChild(continueButton);
			}
			parent.document.getElementById("modality").style.display = "block";
			parent.document.getElementById("modalDialog").style.display = "block";
		}
	}

	function createModal() {
		var modal = jQuery("<div></div>");
		modal.addClass("modal");
		modal.attr("id","modality");
		modal.appendTo(parent.document.body);
		createModalContent().appendTo(parent.document.body);
	}

	function createModalHeader() {
		var modalHeader = jQuery("<div></div>");
		if (dialogType == "loader") {
			modalHeader = modalHeader.text(this.modalText);
		}
		else {
			modalHeader = modalHeader.text(this.headerText);
			if (this.severity == "Warning") {
				modalHeader.addClass("warning");
			}
			else if (this.severity == "Success") {
				modalHeader.addClass("success");
			}
			else if (this.severity == "Error") {
				modalHeader.addClass("error");
			}
		}
		modalHeader.addClass("modal-header");
		modalHeader.attr("id", "modalHeader");
		return modalHeader;
	}

	function createModalContent() {
		var modalContent = jQuery("<div></div>");
		modalContent.addClass("modal-content");
		modalContent.attr("id", "modalDialog");
		var modalContentP = jQuery("<p></p>");
		if (this.dialogType == "loader") {
			modalContentP = modalContentP.html("&nbsp;");
			modalContentP.addClass("interstitial");
		}
		else {
			modalContentP = modalContentP.html(this.modalText);
			modalContentP.removeClass("interstitial");
		}
		modalContentP.attr("id", "modalText");
		modalContentP.addClass("modal-text");
		modalContent.append(createModalHeader());
		modalContent.append(modalContentP);
		modalContent.append(createCancelButton());
		modalContent.append(createContinueButton());
		return modalContent;
	}

	function createCancelButton() {
		var button = jQuery("<button></button>").text(this.cancelText);
		if (this.dialogType == "alert") {
			button.css("display","none");
		}
		else if (this.dialogType == "confirm" || this.dialogType == "loader") {
			button.css("display","inline");
		}
		button.attr("id", "modalCancelBtn");
		button.addClass("modalBtns");
		button.css("float","right");
		button.click(function() {
			hideDialog();
			cancelAction();
		});
		return button;
	}

	function createContinueButton() {
		var button = jQuery("<button></button>").text(this.continueText);
		if (this.dialogType == "loader") {
			button.css("display","none");
		}
		else if (this.dialogType == "confirm" || this.dialogType == "alert") {
			button.css("display","inline");
		}
		button.attr("id", "modalContinueBtn");
		button.addClass("modalBtns");
		button.css("float","right");
		button.click(function() {
			hideDialog();
			continueAction();
		});
		return button;
	}
	
	function hideDialog() {
		/*var modalLoader = parent.document.getElementById("modalLoader");
		try {
			modalLoader.style.display = "none";
		} catch(e) {}*/
		var modalDialog = parent.document.getElementById("modalDialog");
		try {
			modalDialog.style.display = "none";
		} catch(e) {}
		var modality = parent.document.getElementById("modality");
		try {
			modality.style.display = "none";
		} catch(e) {}
	}
	
	function destroy() {
		var modality = parent.document.getElementById("modality");
		if (modality) {
			//parent.document.body.removeChild(parent.document.getElementById("modalLoader"));
			parent.document.body.removeChild(parent.document.getElementById("modalDialog"));
			parent.document.body.removeChild(parent.document.getElementById("modality"));
		}
	};
}
