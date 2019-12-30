fixMozillaZIndex=true; //Fixes Z-Index problem  with Mozilla browsers but causes odd scrolling problem, toggle to see if it helps
_menuCloseDelay=250;
_menuOpenDelay=250;
_subOffsetTop=2;
_subOffsetLeft=-2;
horizontalMenuDelay = true;

/*Nawaz 05-02-07 this to change the context right cick menu*/
function toggleContextMenu(contextMenuName)
{
    contextMenu = contextMenuName;
}

function toggleContextMenuToNormal()
{
 //alert("here toggleContextMenuNormal");
 /*Nawaz 05-02-07
   This is triggered every time a onmouseup event happens on the page, as this is declared in the body tag.
   I make the right click context menu to me normal, right after a particular menu is shown in the result section.
   this allows for the right click to become normal when a right click is made out side the results section.
 */
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
/*aI("text=Back;url=javascript:history.go(-1);image=");
aI("text=Forward;url=javascript:history.go(1);image=");
aI("text=Refresh;url=javascript:history.go(0);image=");
aI("text=View Page Source;url=javascript:Vsrc();");*/
/*aI("text=Close Tab;url=javascript:closeTab();");
aI("text=Add Tab To Startup;url=javascript:addTabToStartup();");*/
//aI("text=Disable This Menu;url=`javascript:var contextDisabled=true;closeAllMenus();`");
}

drawMenus();