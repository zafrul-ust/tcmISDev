
//function inboundShipmentDetailObj(trxType, trxId, inboundShipmentId){

/*  
 *overloaded by passing an object as the argument
 * if the object contains existingDtlObj all of it's values are used
 * otherwise the new object is initialized with trxType, trxId, inboundShipmentId
 */
function inboundShipmentDetailObj(argObj){
    debug("inboundShipmentDetailObj");
	//from tcm_ops.inbound_shipment_detail
	if(argObj.existingDtlObj){
    	this.inboundShipmentDetailId = argObj.existingDtlObj.inboundShipmentDetailId;
        this.inboundShipmentId = argObj.existingDtlObj.inboundShipmentId;
        this.receivingPriority = argObj.existingDtlObj.receivingPriority;
        this.dateUpdated = argObj.existingDtlObj.dateUpdated;
        this.dateInserted = argObj.existingDtlObj.dateInserted;
        this.itemList = new itemListObj();
        this.exisitingShipmentDtl = true;
        if(argObj.existingDtlObj.radianPo){
            this.radianPo = argObj.existingDtlObj.radianPo;
            this.trxType = receivingMessages.label_purchaseorder; 
            return;       
        }
        else if(argObj.existingDtlObj.transferRequestId){
            this.transferRequestId = argObj.existingDtlObj.transferRequestId;
            this.trxType = receivingMessages.tablet_transferrequest;
            return;        
        }
        else if(argObj.existingDtlObj.customerRmaId){
            this.customerRmaId = argObj.existingDtlObj.customerRmaId;
            this.trxType = receivingMessages.label_rma;
            return;       
        }
        else if(argObj.existingDtlObj.docNum){
            this.docNum = argObj.existingDtlObj.docNum;
            this.trxType = receivingMessages.tablet_customerownedinventory;
            return;        
        }
        else{
            alert("error creating inbound shipment detail object\ntrxID undefined.");
        }
    }    
	else{
    	this.inboundShipmentDetailId;
    	this.inboundShipmentId;
    	this.receivingPriority;
    	this.radianPo;
    	this.transferRequestId;
    	this.customerRmaId;
    	this.docNum;
    	this.dateUpdated;
    	this.dateInserted;
    	this.trxType;
    	this.exisitingShipmentDtl = false;
    	this.itemList = new itemListObj();
    	this.inboundShipmentId = argObj.inboundShipmentId;
		this.trxType = argObj.trxType;
    	switch(argObj.trxType){
    		case receivingMessages.label_purchaseorder:
    			this.radianPo = argObj.trxId;
    			break;
    		case receivingMessages.tablet_transferrequest:
    			this.transferRequestId = argObj.trxId;
    			break;
            case receivingMessages.label_rma:
    			this.customerRmaId = argObj.trxId;
    			break;
            case receivingMessages.tablet_customerownedinventory:
    			this.docNum = $("#hiddenDocNum").val();
    			break;
	   }
   }
}

inboundShipmentDetailObj.prototype.saveToDb = function() {
	debug("inboundShipmentDetailObj.saveToDb");
	var thisObj = this;
	var callArgs = new Object();
	callArgs.inboundShipmentId = thisObj.inboundShipmentId;
	callArgs.radianPo = thisObj.radianPo;
	callArgs.transferRequestId = thisObj.transferRequestId;
	callArgs.customerRmaId = thisObj.customerRmaId;
	callArgs.docNum = thisObj.docNum;

	// convert call argument object to a query string
	var params = $.param(callArgs);
	$.post('/tcmIS/haas/tabletInboundShipmentDetailsUpdate.do', params, function(data) {
		var updateReturn = $.parseJSON(data);
		if (updateReturn.Status == "OK") {
			debug("inboundShipmentDetailObj.saveToDb returned OK");
			thisObj.inboundShipmentDetailId = updateReturn.inboundShipmentDetailId;
			debug("shipmentDetailId = " + inboundShipment.details[0].inboundShipmentDetailId);
			thisObj.getPoItemList();
		}
		else {
			alert("Error saving shipment detail.\n" + updateReturn.Message);
			resetApp();
		}
	});
	debug("leaving detail save");
}

inboundShipmentDetailObj.prototype.getTrxDetail = function(){
	// returns trx type po,rma, etc and trx ID
    debug("inboundShipmentDetailObj.getTrxDetail");
	if(this.radianPo){
		return {trxType : receivingMessages.label_purchaseorder, trxId : this.radianPo};}
	if(this.transferRequestId){
		return { trxType : receivingMessages.tablet_transferrequest, trxId : this.transferRequestId};}
	if(this.customerRmaId){
		return { trxType : receivingMessages.label_rma, trxId : this.customerRmaId};}
	if(this.docNum){
		return { trxType : receivingMessages.tablet_customerownedinventory, trxId : this.docNum};}
}

inboundShipmentDetailObj.prototype.getPoItemList = function() {
	var thisObj = this;
	debug("inboundShipmentDetailObj.getPoItemList");
	var callArgs = new Object();
	callArgs.inboundShipmentDetailId = thisObj.inboundShipmentDetailId;
	var params = $.param(callArgs);
	$.post('/tcmIS/haas/tabletPOItems.do', params, function(data) {
		var tabletPOItemsReturn = $.parseJSON(data);
		if (tabletPOItemsReturn.Status == "OK") {
			debug("inboundShipmentDetailObj.getPoItemList returned OK");
			if (tabletPOItemsReturn.Items.length > 0) {
				var componentBuffer = new Array();
				for ( var x = 0; x < tabletPOItemsReturn.Items.length; x++) {
					var newItemObj = new componentObj(tabletPOItemsReturn.Items[x]);
					componentBuffer.push(newItemObj);
					//thisObj.itemList.items.push(newItemObj);
				}
				//sort the components by itemId, componentId 
				componentBuffer.sort(function(a, b) {
					if (a.itemId - b.itemId != 0) {
						return a.itemId - b.itemId;
					}
					else {
						return a.component - b.component;
					}
				});

				for ( var x = 0; x < componentBuffer.length; x++) {
					var componentCounter = 0;
					var currentItemId = componentBuffer[x].itemId;
					// count number of records for each item Id        
					while (componentBuffer[x + componentCounter].itemId == currentItemId) {
						componentCounter++;
						if (x + componentCounter >= componentBuffer.length) {
							//                counter--;
							break;
						}
					}
					var componentList = new Array();
					for ( var y = 0; y < componentCounter; y++) {
						var newComponent = new componentObj(componentBuffer[x + y]);
						componentList.push(newComponent);
					}
					var newItem = new itemObj(componentList);
					thisObj.itemList.items.push(newItem);
					newItem.getTrxLines(thisObj.inboundShipmentDetailId);
					// confusing - x is # components for all items 
					// being incremented for the total # components processed for this item 
					x += (componentCounter - 1);
				}

				//if no documents scanned for this shipment go to the docs page
				thisObj.itemList.items[0].display();
				setupItemViewPage();
				
				//if (inboundShipment.documents.length == 0 && inboundShipment.details[inboundShipment.currentDetail].exisitingShipmentDtl == true) {
				//	$.mobile.changePage("#documentsPage");
				//}
				//else {
				//	$.mobile.changePage("#itemViewPage");
				//}
				//debug("deferred4 resolved");
				//$.mobile.loading("hide");
			}
			else {
				debug("0 items returned");
				alert("There are no items to receive against this transaction.");
				$("#poNum").val("");
				$("#incomingShipmentPoList").popup("open");
				$("#poNumListButton").button("enable");
			}
		}
		else {
			alert("Error retrieving items\n" + tabletPOItemsReturn.Message);
		}
	});
	debug("leaving get itemlist");
}



























