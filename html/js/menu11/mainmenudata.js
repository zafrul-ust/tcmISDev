fixMozillaZIndex=true; //Fixes Z-Index problem  with Mozilla browsers but causes odd scrolling problem, toggle to see if it helps
_menuCloseDelay=500;
_menuOpenDelay=150;
_subOffsetTop=2;
_subOffsetLeft=-2;
//horizontalMenuDelay = true;

/*Nawaz 05-02-07 this to change the context right cick menu*/
function toggleContextMenu(contextMenuName)
{
	contextMenu = contextMenuName;
}

function toggleContextMenuToNormal()
{
	setTimeout('setContextMenuNormal()',50);
	/*To reset the timmer for the time out*/
	try{
	 resetTimer();
	}
	catch (ex)
	{
	}
}

function setContextMenuNormal()
{
 contextMenu = "contextMenu";
}

with(contextStyle=new mm_style)
{
fontfamily="Verdana, Tahoma, Arial";
fontsize="11px";
fontweight="normal";
bordercolor ="#787683";
borderstyle="solid";
borderwidth ="1";
onbgcolor = "#DCEAEC"; /*"#e3ffff";*/
offbgcolor = "#ffffff"; /*"#FDFAF1";*/
offcolor="#333";
oncolor="#000";
separatorcolor="#E8E5DF";
separatorsize = "1";
separatorwidth ="100%";
rawcss="padding-left:5px;padding-right:5px";
subimage="/images/mmenu/arrow.gif";
subimagepadding=2;
itemheight=20;
itemwidth=200;
}

with(contextWideStyle=new mm_style)
{
fontfamily="Verdana, Tahoma, Arial";
fontsize="11px";
fontweight="normal";
bordercolor ="#787683";
borderstyle="solid";
borderwidth ="1";
onbgcolor = "#DCEAEC"; /*"#e3ffff";*/
offbgcolor = "#ffffff"; /*"#FDFAF1";*/
offcolor="#333";
oncolor="#000";
separatorcolor="#E8E5DF";
separatorsize = "1";
separatorwidth ="100%";
rawcss="padding-left:5px;padding-right:5px";
subimage="/images/mmenu/arrow.gif";
subimagepadding=2;
itemheight=20;
itemwidth=300;
}

// Main Menu
with(menuStyle=new mm_style()){
fontfamily="Verdana, Tahoma, Arial";
fontsize="11px";
fontweight="normal";
offcolor="#fff";
oncolor="#000";
bgimage="/images/mmenu/blue_menu_tile5.gif";
overbgimage="/images/mmenu/menu_selected_tile1.gif";
offbgimage="/images/mmenu/blue_menu_tile5.gif";
rawcss="padding-left:5px;padding-right:5px";
separatorimage="/images/mmenu/menu_div2.gif";
separatorsize=1;
itemheight=20;
}

with(submenuStyle=new mm_style()){
fontfamily="Verdana, Tahoma, Arial";
fontsize="11px";
fontweight="normal";
bordercolor ="#787683";
borderstyle="solid";
borderwidth ="1";
onbgcolor = "#DCEAEC"; /*"#e3ffff";*/
offbgcolor = "#ffffff"; /*"#FDFAF1";*/
offcolor="#333";
oncolor="#000";
separatorcolor="#E8E5DF";
separatorsize = "1";
separatorwidth ="100%";
rawcss="padding-left:5px;padding-right:5px";
subimage="/images/mmenu/arrow.gif";
subimagepadding=2;
itemheight=20;
}

with(milonic=new menuname("contextMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=Print;url=javascript:window.print();image=");
}

drawMenus();