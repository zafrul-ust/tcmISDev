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
								{id:410, text:"Catalog",userdata:[{name:"location", content:"../Catalog/Catalog.htm"}],
									item:[
										{id:4030, text:"Engineering Evaluation",userdata:[{name:"location", content:"../EngineeringEval/EngineeringEval.htm"}]},
										{id:4100, text:"Catalog Add Requests",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq.htm"}],
											item:[
												{id:4101, text:"CatAddReq-QPL",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-QPL.htm"}]}, 
												{id:4102, text:"CatAddReq-Use Approval",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-UseApproval.htm"}]}, 
												{id:4103, text:"CatAddReq-Spec",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-Spec.htm"}]}, 
												{id:4104, text:"CatAddReq-Flowdown",userdata:[{name:"location", content:"../CatalogAdd/CatAddReq-Flowdown.htm"}]}
											]}
									]},
								{id:420, text:"Order Tracking",userdata:[{name:"location", content:"../OrderTracking/OrderTracking.htm"}],
									item:[
										{id:4200, text:"Material Requests",userdata:[{name:"location", content:"../MaterialRequest/MaterialRequest.htm"}]},
										{id:4250, text:"MR Allocation",userdata:[{name:"location", content:"../MRAllocation/MRAllocation.htm"}]}
									]},
								{id:430, text:"Dropship Receiving",userdata:[{name:"location", content:"../DropshipReceiving/DropshipReceiving.htm"}]},
								{id:460, text:"Catalog Add",userdata:[{name:"location", content:"../SupplyChain/CatalogAdd.htm"}],
									item:[
										{id:4600, text:"Catalog Add Tracking",userdata:[{name:"location", content:"../CatalogAdd/CatalogAddTracking.htm"}]}		  		    	        	    	                  
									]}			
							]},	
						{id:500, text:"Reports",userdata:[{name:"location", content:"../SupplyChain/Reports.htm"}],
							item:[
								{id:510, text:"Deliveries",userdata:[{name:"location", content:"../Deliveries/Deliveries.htm"}]},
								{id:515, text:"Lookup/Run Reports",userdata:[{name:"location", content:"../LookupRunReports/LookupRunReports.htm"}]},
								{id:520, text:"Catalog Report",userdata:[{name:"location", content:"../CatalogReport/CatalogReport.htm"}]},
								{id:530, text:"Cost Report",userdata:[{name:"location", content:"../SupplyChain/CostReport.htm"}],
									item:[
										{id:531, text:"Cost Report",userdata:[{name:"location", content:"../CostReport/CostReport.htm"}]} 
									]},
								{id:540, text:"Inventory",userdata:[{name:"location", content:"../SupplyChain/Inventory.htm"}],
									item:[
										{id:553, text:"Stocked Inventory",userdata:[{name:"location", content:"../StockedInventory/StockedInventory.htm"}]}
									]},
								{id:590, text:"Usage",userdata:[{name:"location", content:"../SupplyChain/UsageReport.htm"}],
									item:[
										{id:591, text:"Ad Hoc Material Matrix",userdata:[{name:"location", content:"../MaterialMatrix/MaterialMatrix.htm"}]},
										{id:594, text:"Ad Hoc Usage",userdata:[{name:"location", content:"../AdHocUsage/AdHocUsage.htm"}]}
									]}			
							]},	
						{id:700, text:"Administration",userdata:[{name:"location", content:"../SupplyChain/Admin.htm"}],
							item:[
								{id:701, text:"User Profile",userdata:[{name:"location", content:"../Administration/UserProfile.htm"}]},
								{id:702, text:"List Management",userdata:[{name:"location", content:"../ListManagement/ListManagement.htm"}]}
							]},
						{id:800, text:"MSDS",userdata:[{name:"location", content:"../MSDS/MSDS.htm"}]	   
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
			['Order Management',  '../SupplyChain/OrderManagement.htm',
				['Catalog','../Catalog/Catalog.htm',
					['Catalog Addition Requests','../CatalogAdd/CatAddReq.htm'
						['CatAddReq-QPL','../CatalogAdd/CatAddReq-QPL.htm'],
						['CatAddReq-Use Approval','../CatalogAdd/CatAddReq-UseApproval.htm'],
						['CatAddReq-Spec','../CatalogAdd/CatAddReq-Spec.htm'],
						['CatAddReq-Flowdown','../CatalogAdd/CatAddReq-Flowdown.htm'],
					],
				],	
				['Order Tracking',  '../OrderTracking/OrderTracking.htm'],
				['Dropship Receiving',  '../DropshipReceiving/DropshipReceiving.htm'],
				['Catalog Add',  '../SupplyChain/CatalogAdd.htm'
					['Catalog Add Tracking',  '../CatalogAdd/CatalogAddTracking.htm'],
				],
			],
			['Reports',  '../SupplyChain/Reports.htm',
				['Deliveries',  '../Deliveries/Deliveries.htm'],
				['Lookup/Run Reports',  '../LookupRunReports/LookupRunReports.htm'],
				['Catalog Report',  '../CatalogReport/CatalogReport.htm'],
				['Inventory',  '../SupplyChain/InventoryReport.htm',
					['Work Area Stock Turns','../WorkAreaStockTurns/WorkAreaStockTurnsReport.htm'],
					['Consigned Inventory Report','../ConsignmentReport/ConsignmentReport.htm'],
					['Stocked Inventory','../StockedInventory/StockedInventory.htm'],
					['Work Area Scan Score Report','../WorkAreaScanScore/WorkAreaScanScoreReport.htm'],
					['Work Area Detail','../WorkAreaDetail/WorkAreaDetail.htm'],
					['Work Area Inventory','../WorkAreaInventory/WorkAreaInventory.htm'],
					['Ad Hoc Inventory','../AdHocInventory/AdHocInventory.htm'],
				],
				['Usage',  '../SupplyChain/UsageReport.htm',
					['Ad Hoc Material Matrix','../MaterialMatrix/MaterialMatrix.htm'],
					['Ad Hoc Usage','../AdHocUsage/AdHocUsage.htm'],
				],
			],
			['Supply Chain',  '../SupplyChain/SupplyChain.htm', 
				['Charge Numbers Set Up','../ChargeNumbers/ChargeNumbers.htm'],
				['Work Area Inventory','../SupplyChain/WorkAreaInventory.htm',
					['Scanner Training','../ScannerTraining/ScannerTraining.htm'],
					['Work Area Count','../WorkAreas/WorkAreaCount.htm'],
					['Work Area Definition','../ListManagement/ListManagement.htm'],
					['Work Area Labels','../WorkAreas/WorkAreaLabels.htm'],
					['Work Area Stock Management','../WorkAreas/WorkAreaManagement.htm'
						['Nonmanaged Materials','../WorkAreas/NonmanagedMaterials.htm'],
						],
					['Work Area Inventory','../WorkAreas/WorkAreaInventory.htm'],
				], 
			], //end supply chain
		['Administration',  '../SupplyChain/Admin.htm',
			['User Profile',  '../Administration/UserProfile.htm'],
			['List Management',  '../Administration/UserProfile.htm'],
		],
		['MSDS', '../MSDS/MSDS.htm'],			
		],//end menu structure
	],//end tcmIS application
];