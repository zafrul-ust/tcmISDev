var overViewHelpTree;
var helpTree;
var overViewPrevSelectedId = null;
var prevSelectedId=null;
function onOverViewHelpNodeClick(nodeId) {
	 if (overViewPrevSelectedId != null)
		 overViewHelpTree.setItemStyle(overViewPrevSelectedId,"font-weight:normal");
	 overViewHelpTree.setItemStyle(nodeId,"font-weight:bold");
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
		 helpTree.setItemStyle(prevSelectedId,"font-weight:normal");
	 helpTree.setItemStyle(nodeId,"font-weight:bold");
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
{id:0,
	item:[ 
		{id:1, text:"tcmIS Home", userdata:[{name:"location", content:"../Home/Home.htm"}],
			item:[
				{id:2, text:"Bulletin",userdata:[{name:"location", content:"../Home/Bulletin.htm"}]}, 
				{id:3, text:"MSDS",userdata:[{name:"location", content:"../MSDS/MSDS.htm"}]}, 
				{id:4, text:"Start tcmIS",userdata:[{name:"location", content:"../Home/Start.htm"}]} 
		   ]
		 }
	 ] 
};
var HELP_TREE_ITEMS = new Array();
var HELP_TREE_ITEMS = 
{id:0,
	item:[ 
		{id:1, text:"tcmIS Application", userdata:[{name:"location", content:"./intropage.html"}],
			item:[
				{id:2, text:"Glossary",userdata:[{name:"location", content:"../Glossary/Glossary.htm"}]}, 
				{id:3, text:"Navigation",userdata:[{name:"location", content:"../Home/Navigation.htm"}]}, 
				{id:4, text:"Menu Structure",userdata:[{name:"location", content:"./intropage.html"}], 
					item:[ 
						{id:400, text:"Order Management",userdata:[{name:"location", content:"../SupplyChain/OrderManagement.htm"}], 
							item:[		    
								{id:410, text:"Catalog Lookup",userdata:[{name:"location", content:"../CatalogLookup/CatalogLookup.htm"}]},	        	 
								{id:420, text:"Catalog",userdata:[{name:"location", content:"../Catalog/Catalog.htm"}],
									item:[
										{id:4210, text:"Search Catalog, Create<br />&nbsp;&nbsp;MR video tutorial",userdata:[{name:"location", content:"../../tutorials/Search%20Catalog%20and%20Create%20MR.htm"}]},
										{id:4230, text:"Engineering Evaluation",userdata:[{name:"location", content:"../EngineeringEval/EngineeringEval.htm"}]},
										{id:4250, text:"Catalog Add Requests",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq.htm"}],
											item:[
												{id:4251, text:"CatAddReq-QPL",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-QPL.htm"}]}, 
												{id:4252, text:"CatAddReq-Use Approval",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-UseApproval.htm"}]}, 
												{id:4253, text:"CatAddReq-Spec",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-Spec.htm"}]}, 
												{id:4254, text:"CatAddReq-Flowdown",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-Flowdown.htm"}]}
											]}
									]},
								{id:430, text:"Order Tracking",userdata:[{name:"location", content:"../OrderTracking/OrderTracking.htm"}],
									item:[
										{id:4310, text:"Order Tracking<br />&nbsp;&nbsp;video tutorial",userdata:[{name:"location", content:"../../tutorials/Order%20Tracking%20and%20Approval.htm"}]},
										{id:4330, text:"Material Requests",userdata:[{name:"location", content:"../MaterialRequest/MaterialRequest.htm"}]},
										{id:4350, text:"MR Allocation",userdata:[{name:"location", content:"../MRAllocation/MRAllocation.htm"}]}
									]},
								{id:440, text:"Work Area Management",userdata:[{name:"location", content:"../WorkAreaMgt/WorkAreaMgt.htm"}]},
								{id:450, text:"Catalog Add",userdata:[{name:"location", content:"../SupplyChain/CatalogAdd.htm"}],
									item:[
										{id:4501, text:"Catalog Add Tracking",userdata:[{name:"location", content:"../CatalogAdd/CatalogAddTracking.htm"}]}
									]},
								{id:460, text:"Dropship Receiving",userdata:[{name:"location", content:"../DropshipReceiving/DropshipReceiving.htm"}]},
								{id:470, text:"Spec Update",userdata:[{name:"location", content:"../SpecUpdate/SpecUpdate.htm"}]}
							]},//end order management
						{id:500, text:"Reports",userdata:[{name:"location", content:"../SupplyChain/Reports.htm"}],
							item:[
								{id:520, text:"Deliveries",userdata:[{name:"location", content:"../Deliveries/Deliveries.htm"}]},
								{id:530, text:"Lookup/Run Reports",userdata:[{name:"location", content:"../LookupRunReports/LookupRunReports.htm"}]},
								{id:540, text:"Cost Report",userdata:[{name:"location", content:"../SupplyChain/CostReport.htm"}],
									item:[
										{id:5401, text:"Cost Report",userdata:[{name:"location", content:"../CostReport/CostReport.htm"}]}
									]},//end cost report
								{id:550, text:"Inventory",userdata:[{name:"location", content:"../SupplyChain/Inventory.htm"}],
									item:[
										{id:5501, text:"Stocked Inventory",userdata:[{name:"location", content:"../StockedInventory/StockedInventory.htm"}]}	        	    	        	       
									]}, //end inventory
								{id:560, text:"Usage",userdata:[{name:"location", content:"../SupplyChain/UsageReport.htm"}],
									item:[
										{id:5601, text:"Ad Hoc Material Matrix",userdata:[{name:"location", content:"../MaterialMatrix/MaterialMatrix.htm"}]},
										{id:5611, text:"Ad Hoc Usage",userdata:[{name:"location", content:"../AdHocUsage/AdHocUsage.htm"}]},
										{id:5621, text:"Ad Hoc Waste",userdata:[{name:"location", content:"../AdHocWaste/AdHocWaste.htm"}]},	  	        	    	        	       
										{id:5631, text:"Work Area Usage",userdata:[{name:"location", content:"../WorkAreaUsage/WorkAreaUsage.htm"}]}	  	        	    	        	       
									]}//end usage		    	        	    	                  
							]},//end reports	
  		    	      {id:600, text:"Supply Chain",userdata:[{name:"location", content:"../SupplyChain/SupplyChain.htm"}],
  		    	        	item:[
  		    	        	    {id:610, text:"Charge Number Set Up",userdata:[{name:"location", content:"../ChargeNumbers/ChargeNumbers.htm"}]},
								{id:640, text:"Work Area Inventory",userdata:[{name:"location", content:"../SupplyChain/WorkAreaInventory.htm"}],
									item:[
										{id:641, text:"Work Area Definition",userdata:[{name:"location", content:"../WorkAreas/WorkAreaDefinition.htm"}]}  
									]}//end work area inventory		    	        	    	                  
  		    	        	]}, //end supply chain
						{id:700, text:"Administration",userdata:[{name:"location", content:"../SupplyChain/Admin.htm"}],
		    	        	item:[
								{id:710, text:"User Profile",userdata:[{name:"location", content:"../Administration/UserProfile.htm"}]},
								{id:720, text:"List Management",userdata:[{name:"location", content:"../ListManagement/ListManagement.htm"}]}
		    	        	]},//end administration
						{id:800, text:"MSDS",userdata:[{name:"location", content:"../MSDS/MSDS.htm"}]}	   
					]}//end menu structure	
		   ]}	//end tcmIS app
   ] 
};

var TREE_ITEMS = [ 
	['Raytheon tcmIS Home', '../Home/Home.htm', 
		['Bulletin', '../Bulletin/Bulletin.htm'],
		['MSDS', '../MSDS/MSDS.htm'],
		['Start tcmIS', '../Home/Start.htm',],
	],
	['tcmIS Application', './intropage.html',
		['Glossary', '../Glossary/Glossary.htm'],
		['Navigation', '../Home/Navigation.htm'],
		['Menu Structure', './intropage.html',
			['JavaApp','../JavaApp/JavaApp.htm'],
			['Order Management',  '../SupplyChain/OrderManagement.htm',
					['Catalog','../Catalog/Catalog.htm'],
					['Order Tracking','../OrderTracking/OrderTracking.htm'],
					['Dropship Receiving','../DropshipReceiving/DropshipReceiving.htm'],
					['Spec Update','../SpecUpdate/SpecUpdate.htm'],	
			],//end order managmeent
			['Reports',  '../SupplyChain/Reports.htm',
					['Deliveries','../Deliveries/Deliveries.htm'],
					['Cost Report',  '../SupplyChain/CostReport.htm',
						['Cost Report','../CostReport/CostReport.htm'],
					],
					['Inventory',  '../SupplyChain/InventoryReport.htm', 
						['Stocked Inventory','../StockedInventory/StockedInventory.htm'],
					],
					['Usage',  '../SupplyChain/UsageReport.htm',
						['Ad Hoc Material Matrix','../MaterialMatrix/MaterialMatrix.htm'],
						['Ad Hoc Usage','../AdHocUsage/AdHocUsage.htm'],
						['Ad Hoc Waste','../AdHocWaste/AdHocWaste.htm'],
					],
			],//end reports
			['Supply Chain',  '../SupplyChain/SupplyChain.htm', 
				['Stock Room Operations', '../SupplyChain/StockRoomOperations.htm',
					['Counting', '../SupplyChain/Counting.htm',
						['Item Management','../ItemManagement/ItemManagement.htm'],
					],
				],
			], //end supply chain
			['Administration',  '../SupplyChain/Admin.htm',
				['User Profile',  '../Administration/UserProfile.htm'],
			],//end Administration
			['MSDS', '../MSDS/MSDS.htm'],			
		],//end menu structure
	],//end tcmIS application
];