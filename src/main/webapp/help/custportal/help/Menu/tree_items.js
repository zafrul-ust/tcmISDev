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

var HELP_TREE_ITEMS = new Array();
var HELP_TREE_ITEMS = 
{id:0,
	item:[ 
		{id:1, text:"HGI Customer Portal", userdata:[{name:"location", content:"./intropage.html"}],
			item:[
				{id:10, text:"Getting started",userdata:[{name:"location", content:"../Home/Home.htm"}]}, 
				{id:20, text:"Navigation",userdata:[{name:"location", content:"../Home/Navigation.htm"}]}, 
				{id:25, text:"Searches",userdata:[{name:"location", content:"../Administration/Searches.htm"}]}, 
				{id:30, text:"Glossary",userdata:[{name:"location", content:"../Glossary/Glossary.htm"}]}, 
				{id:40, text:"Menu",userdata:[{name:"location", content:"./intropage.html"}], 
					item:[ 
						{id:400, text:"Order Tracking",userdata:[{name:"location", content:"../SupplyChain/OrderManagement.htm"}], 
							item:[
								{id:420, text:"Order Tracking",userdata:[{name:"location", content:"../OrderTracking/OrderTracking.htm"}]}
							]},	
						{id:700, text:"Administration",userdata:[{name:"location", content:"../SupplyChain/Admin.htm"}],
							item:[
								{id:701, text:"User Profile",userdata:[{name:"location", content:"../Administration/UserProfile.htm"}]}
							]}
					]
				}//end menu structure	
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
					['Catalog Addition Requests','../CatalogAdd/CatalogAdd.htm'
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
				],
				['Usage',  '../SupplyChain/UsageReport.htm',
					['Ad Hoc Material Matrix','../MaterialMatrix/MaterialMatrix.htm'],
					['Ad Hoc Usage','../AdHocUsage/AdHocUsage.htm'],
				],
			],
			['Supply Chain',  '../SupplyChain/SupplyChain.htm', 
				['Charge Numbers Set Up','../ChargeNumbers/Charge Numbers.htm'],
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