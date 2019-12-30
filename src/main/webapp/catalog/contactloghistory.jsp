<script language="JavaScript" type="text/javascript">
<!--

var logHistoryGridConfig = {
		divName:'logHistoryDataDiv',
		beanData:'logHistoryJsonData',
		beanGrid:'logHistoryGrid',
		config:'logHistoryConfig',
		rowSpan:false,
		onRowSelect: updateDocumentMenu, 
		onRightClick:showViewDocumentMenu,
		submitDefault:true
};

<c:choose>
<c:when test="${param.calledFrom == 'viewMsds'}">
	var externalNoteHeader = "<fmt:message key='label.note'/>";
</c:when>
<c:otherwise>
	var externalNoteHeader = "<fmt:message key='label.externalnote'/>";
</c:otherwise>
</c:choose>
var logHistoryConfig = [
    {columnId:"permission", submit:false},
    {columnId:"personnelName"<c:if test="${param.calledFrom != 'viewMsds'}">, columnName:'<fmt:message key="label.enteredby"/>'</c:if>},
    {columnId:"contactDateSort"},
    {columnId:"contactDate", columnName:'<fmt:message key="label.date"/>', sorting:"int", hiddenSortingColumn:"contactDateSort", width:11},
    {columnId:"contactStatus", columnName:'<fmt:message key="label.status"/>', width:5},
    {columnId:"contactPurpose", columnName:'<fmt:message key="label.purpose"/>', tooltip:"Y", width:11},
    {columnId:"contactReference", columnName:externalNoteHeader, tooltip:"Y", width:30},
    {columnId:"notes", <c:if test="${empty param.calledFrom}">columnName:'<fmt:message key="label.internalnote"/>',</c:if> tooltip:"Y", width:30},
    {columnId:"contactName"<c:if test="${param.calledFrom != 'viewMsds'}">, columnName:'<fmt:message key="label.name"/>'</c:if>},    
    {columnId:"documentNames"},
    {columnId:"documentUrls"}
];

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

function myUnload() {
	parent.opener.stopShowingWait();
	closeAllchildren();
}

function updateDocumentMenu() {
	
		var menus = new Array();
		menus[menus.length] = "showmenu=documentMenu;text=View Documents;image=";
		
		var docNameList = new Array();
		
		if (logHistoryGrid.getSelectedRowId() !== undefined &&
				logHistoryGrid.getSelectedRowId() != null) {
			var documentNames = gridCellValue(logHistoryGrid,logHistoryGrid.getSelectedRowId(),"documentNames");
			var documentUrls = gridCellValue(logHistoryGrid,logHistoryGrid.getSelectedRowId(),"documentUrls");
		
			var docNameList = documentNames.split(";");
			var docUrlList = documentUrls.split(";");
		}
		
		var docs = new Array();
		
		var docIndex = 0;
		
		for (x in docNameList) {
			if (docUrlList[x] !== undefined && docNameList[docIndex] !== "") {
				docs[docIndex] = "text="+docNameList[docIndex]+
					";url=javascript:viewDocument('"+docUrlList[docIndex]+"');";
				docIndex++;
			}
		}
		
		if (docs.length == 0) {
			docs[docIndex] = "text=<font color='gray'>No Documents</font>"+
				";url=javascript:doNothing();";
		}
		
		replaceMenu('documentMenu', docs);
		replaceMenu('viewDocumentMenu', menus);
}

function replaceMenu(menuname,menus){
	var i = mm_returnMenuItemCount(menuname);

	for(;i> 1; i-- )
		mm_deleteItem(menuname,i);

	for( i = 0; i < menus.length; ){
		var str = menus[i];

		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
		if( i == 1 ) mm_deleteItem(menuname,1);
	}
}

function viewDocument(doc) {
    if (doc.length > 1) {
	    children[children.length] = openWinGeneric(doc,"VIEW_FILE","800","650","yes");
    }
}

function showViewDocumentMenu(rowId, colId) {
	if (logHistoryGrid.getSelectedRowId() && gridCellValue(logHistoryGrid,logHistoryGrid.getSelectedRowId(),"contactStatus")) {
		updateDocumentMenu();
		toggleContextMenu('viewDocumentMenu');
	}
}

with ( milonic=new menuname("viewDocumentMenu") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "showmenu=documentMenu ;text=View Documents ;image=" );
}

with ( milonic=new menuname("documentMenu") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "text=Document ;url=javascript:viewDocument();" );
}

drawMenus();

// -->
</script>
