var overViewHelpTree;
var helpTree;
var overViewPrevSelectedId = null;
var prevSelectedId=null;
function onOverViewHelpNodeClick(nodeId) {
	 if (overViewPrevSelectedId != null)
		 overViewHelpTree.setItemStyle(overViewPrevSelectedId,"font-family:Tahoma,Verdana,sans-serif;font-size:9px;font-weight:normal");
	 overViewHelpTree.setItemStyle(nodeId,"font-family:Tahoma,Verdana,sans-serif;font-size:9px;font-weight:bold");
	// Get user data
	 var location = overViewHelpTree.getUserData(nodeId,"location");
	// Show new page in frame
	 top.frames['content'].location.href = location;
	 overViewPrevSelectedId = nodeId;
}
function onOverViewHelpMouseIn(nodeId) {
	var itemText = overViewHelpTree.getItemText(nodeId);
	var userData = overViewHelpTree.getUserData(nodeId,"location");
	// Update window status
	window.setTimeout('window.status="' + itemText + ' (' + userData + ')"', 10);	
}
function onHelpNodeClick(nodeId) {
	 if (prevSelectedId != null)
		 helpTree.setItemStyle(prevSelectedId,"font-family:Tahoma,Verdana,sans-serif;font-size:9px;font-weight:normal");
	 helpTree.setItemStyle(nodeId,"font-family:Tahoma,Verdana,sans-serif;font-size:9px;font-weight:bold");
	// Get user data
	 var location = helpTree.getUserData(nodeId,"location");
	// Show new page in frame
	 top.frames['content'].location.href = location; 
	 prevSelectedId = nodeId;
}
function onHelpMouseIn(nodeId) {
	var itemText = helpTree.getItemText(nodeId);
	var userData = helpTree.getUserData(nodeId,"location");
	// Update window status
	window.setTimeout('window.status="' + itemText + ' (' + userData + ')"', 10);	
}
var OVERVIEW_HELP_TREE_ITEMS = new Array();
var OVERVIEW_HELP_TREE_ITEMS = 
{id:0, style:"font-family : Tahoma, Verdana, sans-serif;font-size:9px",
		item:[ 
		       {id:1, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"tcmIS Haas Home", userdata:[{name:"location", content:"../Home/Home.htm"}],
		    	   item:[
		    	         {id:2, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Bulletin",userdata:[{name:"location", content:"../Bulletin/Bulletin.htm"}]}, 
		    	         {id:3, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"MSDS",userdata:[{name:"location", content:"../MSDS/MSDS.htm"}]}, 
		    	         {id:4, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Start tcmIS",userdata:[{name:"location", content:"../Home/Start.htm"}]} 
		    	         ]
		       }
			 ] 
};
var HELP_TREE_ITEMS = new Array();
var HELP_TREE_ITEMS = 
{id:0, style:"font-family : Tahoma, Verdana, sans-serif;font-size:9px",
		item:[ 
		       {id:1, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"tcmIS Application", userdata:[{name:"location", content:"./intropage.html"}],
		    	   item:[
		    	         {id:2, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Glossary",userdata:[{name:"location", content:"../Glossary/Glossary.htm"}]}, 
		    	         {id:3, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Navigation",userdata:[{name:"location", content:"../Home/Navigation.htm"}]}, 
		    	         {id:4, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Menu Structure",userdata:[{name:"location", content:"./intropage.html"}], 
		    	        	 item:[ 
		  		    	           {id:5, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"JavaApp",userdata:[{name:"location", content:"../JavaApp/JavaApp.htm"}]}, 		    	        	       
		    	        	       {id:6, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Accounts Payable",userdata:[{name:"location", content:"../SupplyChain/AP.htm"}],
		    	        	    	   item:[
		    	        	    	         {id:7, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Accounts Payable",userdata:[{name:"location", content:"../AccountsPayable/AccountsPayable.htm"}]},
		    	        	    	         {id:8, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Supplier Invoice Report",userdata:[{name:"location", content:"../SupplierInvoiceReport/SupplierInvoiceReport.htm"}]}
		    	        	    	         ]}, //end AP
								     {id:700, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Distribution",userdata:[{name:"location", content:"../SupplyChain/Distribution.htm"}],
										item:[
											{id:701, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Order Entry",userdata:[{name:"location", content:"../OrderEntry/OrderEntry.htm"}],
												item:[
													{id:702, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Scratch Pad",userdata:[{name:"location", content:"../OrderEntry/ScratchPad.htm"}]},  
													{id:703, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Order Entry Lookup",userdata:[{name:"location", content:"../OrderEntry/OrderEntryLookup.htm"}]},  	         
													{id:704, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Customer Lookup",userdata:[{name:"location", content:"../CustomerSearch/CustomerLookup.htm"}]},
													{id:705, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cash Sales",userdata:[{name:"location", content:"../OrderEntry/CashSales.htm"}]},
													{id:706, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Add Customer",userdata:[{name:"location", content:"../CustomerSearch/CustomerAdd.htm"}]},
													{id:707, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Customer Contact Lookup",userdata:[{name:"location", content:"../OrderEntry/CustomerContactLookup.htm"}]},
													{id:708, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"New Customer Contact",userdata:[{name:"location", content:"../OrderEntry/NewCustomerContact.htm"}]},
													{id:709, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"New Ship To Address",userdata:[{name:"location", content:"../OrderEntry/NewShipTo.htm"}]},
													{id:710, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Add Line",userdata:[{name:"location", content:"../OrderEntry/AddLine.htm"}],
														item:[
															{id:711, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"New Material",userdata:[{name:"location", content:"../OrderEntry/NewMaterial.htm"}]},
															{id:727, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"MV Items",userdata:[{name:"location", content:"../OrderEntry/MVItems.htm"}]}
															]}, //add line 	       
													{id:712, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Specifications",userdata:[{name:"location", content:"../OrderEntry/Specifications.htm"}]},
													{id:713, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Additional Charges",userdata:[{name:"location", content:"../OrderEntry/AdditionalCharges.htm"}]},
													{id:714, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Inventory Allocation",userdata:[{name:"location", content:"../OrderEntry/Allocation.htm"}]},	
													{id:715, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Create Quote",userdata:[{name:"location", content:"../OrderEntry/CreateQuote.htm"}]},
													{id:716, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Create Material Request",userdata:[{name:"location", content:"../OrderEntry/CreateMR.htm"}]},
													{id:717, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Credit Control",userdata:[{name:"location", content:"../OrderEntry/CreditControl.htm"}]},
													{id:725, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Customer Return Request",userdata:[{name:"location", content:"../CustomerReturnRequest/CustomerReturnRequest.htm"}]}
													]},//end Order Entry
										{id:721, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Customer Search",userdata:[{name:"location", content:"../CustomerSearch/CustomerSearch.htm"}],
											item:[
												{id:722, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Edit Customer",userdata:[{name:"location", content:"../CustomerSearch/EditCustomer.htm"}]}
												]}, //end customer search
										{id:723, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"New Customer Tracking",userdata:[{name:"location", content:"../NewCustomerTracking/NewCustomerTracking.htm"}]},
										{id:719, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Customer Return Tracking",userdata:[{name:"location", content:"../CustomerReturnRequest/CustomerReturnTracking.htm"}]},
										{id:718, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"MR Release",userdata:[{name:"location", content:"../MRRelease/MRRelease.htm"}]},
										{id:720, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Price List",userdata:[{name:"location", content:"../PriceList/PriceList.htm"}]},
										{id:724, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Invoice Print",userdata:[{name:"location", content:"../InvoicePrint/InvoicePrint.htm"}],
										item:[
												{id:728, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Invoice Correction",userdata:[{name:"location", content:"../InvoiceCorrection/InvoiceCorrection.htm"}]}
												]}, //end invoice correction
										{id:726, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Order Tracking",userdata:[{name:"location", content:"../OrderTracking/OrderTracking.htm"}]},				
										{id:730, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Quick Customer View",userdata:[{name:"location", content:"../QuickCustomerView/QuickCustomerView.htm"}]},				
										{id:731, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Quick Item View",userdata:[{name:"location", content:"../QuickItemView/QuickItemView.htm"}]}				
										]},		 //end Distribution	
		    	        	       {id:9, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Order Management",userdata:[{name:"location", content:"../SupplyChain/OrderManagement.htm"}], 
		    	        	    	   item:[
		    	        	    	         {id:10, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Dropship Receiving",userdata:[{name:"location", content:"../DropshipReceiving/DropshipReceiving2.htm"}]}
		    	        	    	         ]},
  		    	        	       {id:11, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Reports",userdata:[{name:"location", content:"../SupplyChain/Reports.htm"}],
  		    	        	    	   item:[
  		    	        	    	         {id:12, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"SQL Report",userdata:[{name:"location", content:"../SQLReport/SQLReport.htm"}]},
  		    	        	    	         {id:13, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cost Report",userdata:[{name:"location", content:"../SupplyChain/CostReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:14, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cost Report",userdata:[{name:"location", content:"../CostReport/CostReport.htm"}]}, 
  		    	        			    	         {id:15, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cost Book Usage",userdata:[{name:"location", content:"../CostBookUsage/CostBookUsage.htm"}]}, 
  		    	        			    	         {id:16, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Pass Through Report",userdata:[{name:"location", content:"../PassThroughReport/PassThroughReport.htm"}]} 		    	        	    	        	       
  		    	        	    	                  ]},
  		    	        	    	         {id:17, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Inventory",userdata:[{name:"location", content:"../SupplyChain/InventoryReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:18, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Ops Invoice Inventory Detail",userdata:[{name:"location", content:"../InvoiceInventoryDetail/InventoryDetail.htm"}]}, 
  		    	        			    	         {id:19, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Consigned Inventory Report",userdata:[{name:"location", content:"../ConsignmentReport/ConsignmentReport.htm"}]}, 
													 {id:190, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Inventory",userdata:[{name:"location", content:"../CabinetInventory/CabinetInventory.htm"}]}, 
													 {id:191, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Inventory By Receipt",userdata:[{name:"location", content:"../InventoryByReceipt/InventoryByReceipt.htm"}]}, 
  		    	        			    	         {id:20, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Daily Inventory Report",userdata:[{name:"location", content:"../DailyInventoryReport/DailyInventoryReport.htm"}]},
													 {id:200, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Low Stock",userdata:[{name:"location", content:"../LowStock/LowStock.htm"}]},
													 {id:2000, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Excess Inventory",userdata:[{name:"location", content:"../ExcessInventory/ExcessInventory.htm"}]},
													 {id:201, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Expiration Forecast",userdata:[{name:"location", content:"../ExpirationForecast/ExpirationForecast.htm"}]},
													 {id:202, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"No Sales",userdata:[{name:"location", content:"../NoSales/NoSales.htm"}]}  		    	        	    	         		    	        	    	        	       
  		    	        	    	                  ]},
											{id:203, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Item",userdata:[{name:"location", content:"../SupplyChain/ItemReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:204, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Harmonized Trade Code",userdata:[{name:"location", content:"../HarmonizedTradeCode/HarmonizedTradeCode.htm"}]} 		  		    	        	    	                  
  		    	        	    	         			]},
											{id:205, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Orders",userdata:[{name:"location", content:"../SupplyChain/OrderReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:206, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Allocation Analysis",userdata:[{name:"location", content:"../Allocation/AllocationAnalysis.htm"}]},
													 {id:207, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Open Picks",userdata:[{name:"location", content:"../OpenPicks/OpenPicks.htm"}]},
													 {id:213, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Transfers",userdata:[{name:"location", content:"../Transfers/Transfers.htm"}]},
													 {id:214, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"MR History",userdata:[{name:"location", content:"../MRHistory/MRHistory.htm"}]}		  		    	        	    	                  
  		    	        	    	         			]},
											{id:208, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Purchasing",userdata:[{name:"location", content:"../SupplyChain/PurchasingReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:209, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Buy Orders",userdata:[{name:"location", content:"../Purchasing/BuyOrders.htm"}]},
													 {id:210, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Open POs",userdata:[{name:"location", content:"../Purchasing/OpenPOs.htm"}]},
													 {id:211, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Purchase History",userdata:[{name:"location", content:"../PurchaseHistory/PurchaseHistory.htm"}]},
													 {id:212, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Supplier Search",userdata:[{name:"location", content:"../Purchasing/SupplierSearch.htm"}]}		  		    	        	    	                  
  		    	        	    	         			]}			
											 ]},	
  		    	        	       {id:21, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Supply Chain",userdata:[{name:"location", content:"../SupplyChain/SupplyChain.htm"}],
  		    	        	    	   item:[
  		    	        	    	         {id:22, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Allocation Analysis",userdata:[{name:"location", content:"../Allocation/AllocationAnalysis.htm"}]},
  		    	        	    	         {id:23, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Transfer Request",userdata:[{name:"location", content:"../TransferRequest/TransferRequest.htm"}]},
											  {id:230, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Force A Buy Order",userdata:[{name:"location", content:"../ForceBuy/ForceBuy.htm"}]},
  		    	        	    	         {id:24, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Stock Room Operations",userdata:[{name:"location", content:"../SupplyChain/StockRoomOperations.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:25, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Counting",userdata:[{name:"location", content:"../SupplyChain/Counting.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:26, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Item Management",userdata:[{name:"location", content:"../ItemManagement/ItemManagement.htm"}]}, 
  		  		    	        			    	         {id:27, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Item Count",userdata:[{name:"location", content:"../ItemCount/ItemCount.htm"}]}, 
  		  		    	        			    	         {id:28, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Distributed Counts",userdata:[{name:"location", content:"../DistributedCounts/DistributedCounts.htm"}]}, 
  		  		    	        			    	         {id:285, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Consignment Processing",userdata:[{name:"location", content:"../ConsignmentProcessing/ConsignmentProcessing.htm"}]} 		    	        	    	        	       
  		  		    	        	    	                  ]},
		    	        	    	                    {id:29, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Delivery",userdata:[{name:"location", content:"../SupplyChain/Delivery.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:30, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Freight Advice",userdata:[{name:"location", content:"../FreightAdvice/FreightAdvice.htm"}]}, 
  		  		    	        			    	         {id:31, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Picking",userdata:[{name:"location", content:"../Picking/Picking2.htm"}]}, 
  		  		    	        			    	         {id:32, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Picking Movie",userdata:[{name:"location", content:"../Picking/PickingMovie.htm"}]},
															 {id:33, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"No Picklist Picking",userdata:[{name:"location", content:"../NoPicklistPicking/NoPicklistPicking.htm"}]},  
															 {id:34, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Picking QC",userdata:[{name:"location", content:"../PickingQC/PickingQC.htm"}]},
															 {id:35, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Picking QC Movie",userdata:[{name:"location", content:"../PickingQC/PickingQCMovie.htm"}]},  
															 {id:36, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Pack Ship Confirm",userdata:[{name:"location", content:"../PackShipConfirm/PackShipConfirm.htm"}]},
															 {id:37, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Ship Confirm",userdata:[{name:"location", content:"../ShipConfirm/ShipConfirm2.htm"}]},
															 {id:38, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Ship Confirm Movie",userdata:[{name:"location", content:"../ShipConfirm/ShipConfirmMovie.htm"}]},
															 {id:39, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Shipment History",userdata:[{name:"location", content:"../ShipmentHistory/ShipmentHistory.htm"}]},
															 {id:40, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Customer Returns",userdata:[{name:"location", content:"../CustomerReturns/CustomerReturns.htm"}]},
															 {id:41, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Generic Bill of Lading",userdata:[{name:"location", content:"../GenericBOL/GenericBOL.htm"}]}		    	        	    	        	       
  		  		    	        	    	                  ]},
														{id:42, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Receiving",userdata:[{name:"location", content:"../SupplyChain/Receiving.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:43, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"List Cust/SAIC Jackets",userdata:[{name:"location", content:"../ListPOJackets/ListCustSAICJackets.htm"}]}, 
  		  		    	        			    	         {id:44, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"List Haas PO Jackets",userdata:[{name:"location", content:"../ListPOJackets/ListPOJackets.htm"}]}, 
  		  		    	        			    	         {id:45, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Receiving",userdata:[{name:"location", content:"../Receiving/Receiving2.htm"}]},
															 {id:46, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Receiving Movie",userdata:[{name:"location", content:"../Receiving/ReceivingMovie.htm"}]},  
															 {id:47, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Receiving QC",userdata:[{name:"location", content:"../ReceivingQC/ReceivingQC2.htm"}]},
															 {id:48, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Receiving QC Movie",userdata:[{name:"location", content:"../ReceivingQC/ReceivingQCMovie.htm"}]},  
															 {id:49, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Repackaging",userdata:[{name:"location", content:"../Repackaging/Repackaging.htm"}]},
															 {id:50, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"List Old Jackets",userdata:[{name:"location", content:"../ListPOJackets/ListOldJackets.htm"}]},
															 {id:51, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Lookup Receiving Info",userdata:[{name:"location", content:"../ReceivingInfo/LookupReceivingInfo.htm"}]},
															 {id:52, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Supplier Return",userdata:[{name:"location", content:"../RMAProcess/SupplierReturn.htm"}]},
															 {id:53, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Enter Receiving Info",userdata:[{name:"location", content:"../ReceivingInfo/EnterReceivingInfo.htm"}]},
															 {id:54, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Customer Receiving History",userdata:[{name:"location", content:"../CustomerReceivingHistory/CustomerReceivingHistory.htm"}]}		    	        	    	        	       
  		  		    	        	    	                  ]}
  		    	        			    	          ]},
											 {id:55, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Stock Room Inventory",userdata:[{name:"location", content:"../SupplyChain/StockRoomInventory.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:56, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Inventory",userdata:[{name:"location", content:"../SupplyChain/Inventory.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:57, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Logistics",userdata:[{name:"location", content:"../Logistics/Logistics2.htm"}]}, 
  		  		    	        			    	         {id:58, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Transactions",userdata:[{name:"location", content:"../Transactions/Transactions.htm"}]}, 
  		  		    	        			    	         {id:59, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Min Max Levels",userdata:[{name:"location", content:"../MinMaxLevels/MinMaxLevels2.htm"}]},
															 {id:60, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Level Management",userdata:[{name:"location", content:"../LevelManagement/LevelManagement.htm"}]},
															 {id:601, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Bin Management",userdata:[{name:"location", content:"../BinManagement/BinManagement.htm"}]}  		    	        	    	        	       
  		  		    	        	    	                  ]},
													{id:61, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Reconciliation",userdata:[{name:"location", content:"../SupplyChain/Reconciliation.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:62, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Reconciliation",userdata:[{name:"location", content:"../Reconciliation/Reconciliation.htm"}]}, 
  		  		    	        			    	         {id:63, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Bins To Scan",userdata:[{name:"location", content:"../BinsToScan/BinsToScan.htm"}]}, 
  		  		    	        			    	         {id:64, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Inventory Count Upload",userdata:[{name:"location", content:"../InventoryCountUpload/InventoryCountUpload.htm"}]},
															 {id:65, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Bin Labels",userdata:[{name:"location", content:"../BinLabels/BinLabels.htm"}]} 		    	        	    	        	       
  		  		    	        	    	                  ]} 		    	        	    	         
		    	        	    	         	    ]},	
												{id:66, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Purchasing",userdata:[{name:"location", content:"../SupplyChain/Purchasing.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:67, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Buy Orders",userdata:[{name:"location", content:"../Purchasing/BuyOrders.htm"}]}, 
  		  		    	        			    	         {id:68, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Purchase Order",userdata:[{name:"location", content:"../Purchasing/Purchasing2.htm"}]}, 
  		  		    	        			    	         {id:69, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"New/Modify Supplier Request",userdata:[{name:"location", content:"../Purchasing/NewModifySupplierRequest.htm"}]},
															 {id:699, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Supplier Price List",userdata:[{name:"location", content:"../SupplierPriceList/SupplierPriceList.htm"}]},
															 {id:90, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"New Remit To Requests",userdata:[{name:"location", content:"../Purchasing/NewRemitToRequests.htm"}]},
															 {id:70, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"EDI Order Status",userdata:[{name:"location", content:"../Purchasing/EDIOrderStatus.htm"}]},  
															 {id:71, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Printed PO List",userdata:[{name:"location", content:"../Purchasing/PrintedPOList.htm"}]},
															 {id:72, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Purchase History",userdata:[{name:"location", content:"../PurchaseHistory/PurchaseHistory.htm"}]},  
															 {id:73, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Supplier Search",userdata:[{name:"location", content:"../Purchasing/SupplierSearch.htm"}]},
															 {id:74, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Buyer Item Notes",userdata:[{name:"location", content:"../Purchasing/BuyerItemNotes.htm"}]},
															 {id:75, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Open POs",userdata:[{name:"location", content:"../Purchasing/OpenPOs.htm"}]},
														     {id:76, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Price Quote",userdata:[{name:"location", content:"../SupplyChain/PriceQuote.htm"}],
  		    	        			    	        	 			item:[
  		  		    	        			    	         			{id:77, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Price Quote",userdata:[{name:"location", content:"../PriceQuote/PriceQuote.htm"}]}, 
  		  		    	        			    	         			{id:78, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Request Price Quote",userdata:[{name:"location", content:"../PriceQuote/RequestPriceQuote.htm"}]}, 
  		  		    	        			    	         			{id:79, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Price Quote History",userdata:[{name:"location", content:"../PriceQuote/PriceQuoteHistory.htm"}]} 		    	        	    	        	       
  		  		    	        	    	                  		]}
  		  		    	        	    	                  ]},   
											     {id:80, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Inventory",userdata:[{name:"location", content:"../SupplyChain/CabinetInventory.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:81, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Management",userdata:[{name:"location", content:"../Cabinets/CabinetManagement.htm"}]}, 
  		  		    	        			    	         {id:82, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Labels",userdata:[{name:"location", content:"../Cabinets/CabinetLabels.htm"}]}, 
  		  		    	        			    	         {id:83, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Scanning",userdata:[{name:"location", content:"../Cabinets/CabinetScanning.htm"}]},
															 {id:84, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Count",userdata:[{name:"location", content:"../Cabinets/CabinetCount.htm"}]},  
															 {id:85, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Inventory",userdata:[{name:"location", content:"../Cabinets/CabinetInventory.htm"}]},
															 {id:86, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cabinet Definitions",userdata:[{name:"location", content:"../Cabinets/CabinetDefinitions.htm"}]}
  		  		    	        	    	                  ]}  	    	         
		    	        	    	         
  		    	        	    	         ]}, //end supply chain
							{id:87, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Administration",userdata:[{name:"location", content:"../SupplyChain/Admin.htm"}],
		    	        	    	   item:[
		    	        	    	         {id:88, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"User Profile",userdata:[{name:"location", content:"../Administration/UserProfile.htm"}]},
											 {id:729, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Additional Charge Setup",userdata:[{name:"location", content:"../Administration/AdditionalChargeSetup.htm"}]}
		    	        	    	         ]},
							{id:89, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"MSDS",userdata:[{name:"location", content:"../MSDS/MSDS.htm"}]	   
								}	
  		    	        	   ]}//end menu structure	
		    	         ]}	//end tcmIS app
			 ] 
};

var TREE_ITEMS = [ 
	['tcmIS Haas Home', '../Home/Home.htm', 
		['Bulletin', '../Bulletin/Bulletin.htm'],
		['MSDS', '../MSDS/MSDS.htm'],
		['Start tcmIS', '../Home/Start.htm',],
	],	
	['tcmIS Application', './intropage.html',
		['Glossary', '../Glossary/Glossary.htm'],
		['Navigation', '../Home/Navigation.htm'],
		['Menu Structure', './intropage.html',
			['JavaApp','../JavaApp/JavaApp.htm'],
			['Accounts Payable',  '../SupplyChain/AP.htm',
					['Accounts Payable','../AccountsPayable/AccountsPayable.htm'],
					['Supplier Invoice Report','../SupplierInvoiceReport/SupplierInvoiceReport.htm'],
			],
			['Distribution',  '../SupplyChain/Distribution.htm',
					['Order Entry','../OrderEntry/OrderEntry.htm',
							['Scratch Pad','../OrderEntry/ScratchPad.htm'],
							['Order Entry Lookup','../OrderEntry/OrderEntryLookup.htm'],
							['Customer Lookup','../CustomerSearch/CustomerLookup.htm'],
							['Cash Sales','../OrderEntry/CashSales.htm'],
							['Add Customer','../CustomerSearch/CustomerAdd.htm'],
							['Customer Contact Lookup','../OrderEntry/CustomerContactLookup.htm'],
							['New Customer Contact','../OrderEntry/NewCustomerContact.htm'],
							['New Ship To Address','../OrderEntry/NewShipTo.htm'],
							['Add Line','../OrderEntry/AddLine.htm',
								['New Material','../OrderEntry/NewMaterial.htm'],
								['MV Items','../OrderEntry/MVItems.htm'],
							],
							['Specifications','../OrderEntry/Specifications.htm'],
							['Additional Charges','../OrderEntry/AdditionalCharges.htm'],
							['Inventory Allocation','../OrderEntry/Allocation.htm'],
							['Create Quote','../OrderEntry/CreateQuote.htm'],
							['Create Material Request','../OrderEntry/CreateMR.htm'],
							['Credit Control ','../OrderEntry/CreditControl.htm'],
							['Customer Return Request','../CustomerReturnRequest/CustomerReturnRequest.htm'],
					],
					['Customer Search','../CustomerSearch/CustomerSearch.htm',
						['Edit Customer','../CustomerSearch/EditCustomer.htm'],
					],
					['New Customer Tracking','../NewCustomerTracking/NewCustomerTracking.htm'],
					['Customer Return Tracking','../CustomerReturnRequest/CustomerReturnTracking.htm'],
					['MR Release','../MRRelease/MRRelease.htm'],
					['Price List','../PriceList/PriceList.htm'],
					['Invoice Print','../InvoicePrint/InvoicePrint.htm'
						['Invoice Correction','../InvoiceCorrection/InvoiceCorrection.htm'],
					],
					['Order Tracking','../OrderTracking/OrderTracking.htm'],	
			],
			['Order Management',  '../SupplyChain/OrderManagement.htm',
					['Dropship Receiving','../DropshipReceiving/DropshipReceiving2.htm'],	
			],
			['Reports',  '../SupplyChain/Reports.htm',
					['SQL Report',  '../SQLReport/SQLReport.htm'],
					['Cost Report',  '../SupplyChain/CostReport.htm',
						['Cost Report','../CostReport/CostReport.htm'],
						['Cost Book Usage','../CostBookUsage/CostBookUsage.htm'],
						['Pass Through Report','../PassThroughReport/PassThroughReport.htm'],
					],
					['Inventory',  '../SupplyChain/InventoryReport.htm',
						['Ops Invoice Inventory Detail','../InvoiceInventoryDetail/InventoryDetail.htm'],
						['Consigned Inventory Report','../ConsignmentReport/ConsignmentReport.htm'],
						['Cabinet Inventory','../CabinetInventory/CabinetInventory.htm'],
						['Inventory By Receipt','../InventoryByReceipt/InventoryByReceipt.htm'],
						['Daily Inventory Report','../DailyInventoryReport/DailyInventoryReport.htm'],
						['Low Stock','../LowStock/LowStock.htm'],
						['Excess Inventory','../ExcessInventory/ExcessInventory.htm'],
						['Expiration Forecast','../ExpirationForecast/ExpirationForecasst.htm'],
						['No Sales','../NoSales/NoSales.htm'],
					],
					['Item',  '../SupplyChain/ItemReport.htm',
						['Harmonized Trade Code','../HarmonizedTradeCode/HarmonizedTradeCode.htm'],
					],
					['Orders',  '../SupplyChain/OrderReport.htm',
						['Allocation Analysis','../Allocation/AllocationAnalysis.htm'],
						['Open Picks','../OpenPicks/OpenPicks.htm'],
						['Transfers','../Transfers/Transfers.htm'],
						['MR History','../MRHistory/MRHistory.htm'],
					],
					['Purchasing',  './intropage.html',
						['Buy Orders','../Purchasing/BuyOrders.htm'],
						['Open POs','../Purchasing/OpenPOs.htm'],
						['Purchase History','../PurchaseHistory/PurchaseHistory.htm'],
						['Supplier Search','../Purchasing/SupplierSearch.htm'],
					],
			],
			['Supply Chain',  '../SupplyChain/SupplyChain.htm', 
				['Allocation Analysis','../Allocation/AllocationAnalysis.htm'],
				['Transfer Request','../TransferRequest/TransferRequest.htm'],
				['Force A Buy Order','../ForceBuy/ForceBuy.htm'],
				['Stock Room Operations', '../SupplyChain/StockRoomOperations.htm',
					['Counting', '../SupplyChain/Counting.htm',
						['Item Management','../ItemManagement/ItemManagement.htm'],
						['Item Count','../ItemCount/ItemCount.htm'],
						['Distributed Counts','../DistributedCounts/DistributedCounts.htm'],
						['Consignment Processing','../ConsignmentProcessing/ConsignmentProcessing.htm'],
					],
					['Delivery', '../SupplyChain/Delivery.htm',
						['Freight Advice','../FreightAdvice/FreightAdvice.htm'],
						['Picking','../Picking/Picking2.htm'],
						['Picking Movie','../Picking/PickingMovie.htm'],
						['No Picklist Picking', '../NoPicklistPicking/NoPicklistPicking.htm'],
						['Picking QC', '../PickingQC/PickingQC.htm'],
						['Picking QC Movie','../PickingQC/PickingQCMovie.htm'],
						['Pack Ship Confirm', '../PackShipConfirm/PackShipConfirm.htm'],
						['Ship Confirm', '../ShipConfirm/ShipConfirm2.htm'],
						['Ship Confirm Movie','../ShipConfirm/ShipConfirmMovie.htm'],
						['Shipment History', '../ShipmentHistory/ShipmentHistory.htm'],
						['Customer Returns', '../CustomerReturns/CustomerReturns.htm'],
						['Generic Bill of Lading', '../GenericBOL/GenericBOL.htm'],
					],
					['Receiving','../SupplyChain/Receiving.htm',
						['List Cust/SAIC Jackets','../ListPOJackets/ListCustSAICJackets.htm'],
						['List Haas PO Jackets','../ListPOJackets/ListPOJackets.htm'],
						['Receiving','../Receiving/Receiving2.htm'],
						['Receiving Movie','../Receiving/ReceivingMovie.htm'],
						['Receiving QC', '../ReceivingQC/ReceivingQC2.htm'],
						['Receiving QC Movie','../ReceivingQC/ReceivingQCMovie.htm'],
						['Repackaging', '../Repackaging/Repackaging.htm'],
						['List Old Jackets', '../ListPOJackets/ListOldJackets.htm'],
						['Lookup Receiving Info', '../ReceivingInfo/LookupReceivingInfo.htm'],
						['Supplier Return', '../RMAProcess/SupplierReturn.htm'],
						['Enter Receiving Info', '../ReceivingInfo/EnterReceivingInfo.htm'],
						['Customer Receiving History', '../CustomerReceivingHistory/CustomerReceivingHistory.htm'],
					],				
				],
				['Stock Room Inventory','../SupplyChain/StockRoomInventory.htm',
						['Inventory',  '../SupplyChain/Inventory.htm',
							['Logistics','../Logistics/Logistics2.htm'],
							['Transactions','../Transactions/Transactions.htm'],
							['Min Max Levels','../MinMaxLevels/MinMaxLevels2.htm'],
							['Level Management','../LevelManagement/LevelManagement.htm'],
							['Bin Management','../BinManagement/BinManagement.htm'],
						],
						['Reconciliation',  '../SupplyChain/Reconciliation.htm',
							['Reconciliation','../Reconciliation/Reconciliation.htm'],
							['Bins To Scan','../BinsToScan/BinsToScan.htm'],
							['Inventory Count Upload','../InventoryCountUpload/InventoryCountUpload.htm'],
							['Bin Labels','../BinLabels/BinLabels.htm'],
							['Inventory Adjustments','../InventoryAdjustment/InventoryAdjustment.htm'],
						],
				], 
				['Purchasing','../SupplyChain/Purchasing.htm', 
						['Buy Orders','../Purchasing/BuyOrders.htm'],
						['Purchase Order','../Purchasing/Purchasing2.htm'],
						['New/Modify Supplier Request','../Purchasing/NewModifySupplierRequest.htm'],
						['Supplier Price List','../SupplierPriceList/SupplierPriceList.htm'],
						['New Remit To Requests','../Purchasing/NewRemitToRequests.htm'],
						['EDI Order Status','../Purchasing/EDIOrderStatus.htm'],
						['Printed PO List','../Purchasing/PrintedPOList.htm'],
						['Purchase History','../PurchaseHistory/PurchaseHistory.htm'],
						['Supplier Search','../Purchasing/SupplierSearch.htm'],
						['Buyer Item Notes','../Purchasing/BuyerItemNotes.htm'],
						['Open POs','../Purchasing/OpenPOs.htm'],
						['Price Quote',  '../SupplyChain/PriceQuote.htm',
							['Price Quote','../PriceQuote/PriceQuote.htm'],
							['Request Price Quote','../PriceQuote/RequestPriceQuote.htm'],
							['Price Quote History','../PriceQuote/PriceQuoteHistory.htm'],
						],
				], 
				['Cabinet Inventory','../SupplyChain/CabinetInventory.htm',
						['Cabinet Management','../Cabinets/CabinetManagement.htm'],
						['Cabinet Labels','../Cabinets/CabinetLabels.htm'],
						['Cabinet Scanning','../Cabinets/CabinetScanning.htm'],
						['Cabinet Count','../Cabinets/CabinetCount.htm'],
						['Cabinet Inventory','../Cabinets/CabinetInventory.htm'],
						['Cabinet Definitions','../Cabinets/CabinetDefinitions.htm'],
				], 
			], //end supply chain
		['Administration',  '../SupplyChain/Admin.htm',
			['User Profile',  '../Administration/UserProfile.htm'],
			['Additional Charge Setup',  '../Administration/AdditionalChargeSetup.htm'],
		],
		['MSDS', '../MSDS/MSDS.htm'],			
		],//end menu structure
	],//end tcmIS application
];
