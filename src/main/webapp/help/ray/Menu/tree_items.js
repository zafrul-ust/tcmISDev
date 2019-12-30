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
		       {id:1, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Raytheon tcmIS Home", userdata:[{name:"location", content:"../Home/Home.htm"}],
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
		    	        	     {id:6, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Order Management",userdata:[{name:"location", content:"../SupplyChain/OrderManagement.htm"}], 
		    	        	    	   item:[		    
									 		 {id:28, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Catalog",userdata:[{name:"location", content:"../Catalog/Catalog.htm"}]},	        	 
									 		 {id:7, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Order Tracking",userdata:[{name:"location", content:"../OrderTracking/OrderTracking.htm"}]},
		    	        	    	         {id:8, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Dropship Receiving",userdata:[{name:"location", content:"../DropshipReceiving/DropshipReceiving.htm"}]},
											 {id:12, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Spec Update",userdata:[{name:"location", content:"../SpecUpdate/SpecUpdate.htm"}]}
											 ]},//end order management
  		    	        	       {id:13, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Reports",userdata:[{name:"location", content:"../SupplyChain/Reports.htm"}],
  		    	        	    	   item:[
  		    	        	    	         {id:14, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Deliveries",userdata:[{name:"location", content:"../Deliveries/Deliveries.htm"}]},
  		    	        	    	         {id:15, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cost Report",userdata:[{name:"location", content:"../SupplyChain/CostReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:16, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Cost Report",userdata:[{name:"location", content:"../CostReport/CostReport.htm"}]}
													 ]},//end cost report
  		    	        	    	         {id:17, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Inventory",userdata:[{name:"location", content:"../SupplyChain/InventoryReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:18, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Stocked Inventory",userdata:[{name:"location", content:"../StockedInventory/StockedInventory.htm"}]}	        	    	        	       
  		    	        	    	                  ]}, //end inventory
											 {id:19, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Usage",userdata:[{name:"location", content:"../SupplyChain/UsageReport.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:20, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Ad Hoc Material Matrix",userdata:[{name:"location", content:"../MaterialMatrix/MaterialMatrix.htm"}]},
													 {id:21, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Ad Hoc Usage",userdata:[{name:"location", content:"../AdHocUsage/AdHocUsage.htm"}]},
													 {id:30, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Ad Hoc Waste",userdata:[{name:"location", content:"../AdHocWaste/AdHocWaste.htm"}]}	  	        	    	        	       
  		    	        	    	                  ]}//end usage		    	        	    	                  
  		    	        	    	         ]},//end reports	
  		    	        	     {id:22, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Supply Chain",userdata:[{name:"location", content:"../SupplyChain/SupplyChain.htm"}],
  		    	        	    	   item:[
  		    	        	    	         {id:23, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Stock Room Operations",userdata:[{name:"location", content:"../SupplyChain/StockRoomOperations.htm"}],
  		    	        	    	        	 item:[
  		    	        			    	         {id:24, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Counting",userdata:[{name:"location", content:"../SupplyChain/Counting.htm"}],
  		    	        			    	        	 item:[
  		  		    	        			    	         {id:25, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Item Management",userdata:[{name:"location", content:"../ItemManagement/ItemManagement.htm"}]}		    	        	    	        	       
															]}    //
														]}
  		    	        	    	         ]}, //end supply chain
							{id:26, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"Administration",userdata:[{name:"location", content:"../SupplyChain/Admin.htm"}],
		    	        	    	   item:[
		    	        	    	         {id:27, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"User Profile",userdata:[{name:"location", content:"../Administration/UserProfile.htm"}]}
		    	        	    	         ]},//end administration
							{id:29, style:"font-family:Tahoma,Verdana,sans-serif;font-size:9px", text:"MSDS",userdata:[{name:"location", content:"../MSDS/MSDS.htm"}]}	   
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