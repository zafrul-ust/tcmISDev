fixMozillaZIndex=true; //Fixes Z-Index problem  with Mozilla browsers but causes odd scrolling problem, toggle to see if it helps
_menuCloseDelay=500;
_menuOpenDelay=150;
_subOffsetTop=2;
_subOffsetLeft=-2;




with(contextStyle=new mm_style()){
bordercolor="#999999";
borderstyle="solid";
borderwidth=1;
fontfamily="arial, verdana, tahoma";
fontsize="75%";
fontstyle="normal";
headerbgcolor="#4F8EB6";
headerborder=1;
headercolor="#ffffff";
offbgcolor="#ffffff";
offcolor="#000000";
onbgcolor="#ECF4F9";
onborder="1px solid #316AC5";
oncolor="#000000";
outfilter="randomdissolve(duration=0.4)";
overfilter="Fade(duration=0.2);Shadow(color=#777777', Direction=135, Strength=3)";
padding=3;
pagebgcolor="#eeeeee";
pageborder="1px solid #ffffff";
pageimage="http://www.milonic.com/menuimages/db_red.gif";
separatorcolor="#999999";
subimage="http://www.milonic.com/menuimages/black_13x13_greyboxed.gif";
}

with(milonic=new menuname("contextMenu")){
margin=3;
style=contextStyle;
top="offset=2";
aI("image=http://www.milonic.com/menuimages/home.gif;text=Milonic Home Page;url=/;");
aI("image=http://www.milonic.com/menuimages/print.gif;separatorsize=1;text=Print;url=javascript:window.print();");
aI("image=http://www.milonic.com/menuimages/back.gif;text=Back;url=javascript:history.go(-1);");
aI("image=http://www.milonic.com/menuimages/forward.gif;text=Forward;url=javascript:history.go(1);");
aI("image=http://www.milonic.com/menuimages/browser.gif;text=Refresh;url=javascript:history.go(0);");
aI("separatorsize=1;text=View Page Source;url=javascript:Vsrc();");
aI("text=Disable This Menu;url=`javascript:var contextDisabled=true;");
}

with(horizStyle=new mm_style()){
bordercolor="#999999";
borderstyle="solid";
borderwidth=1;
fontfamily="arial, tahoma";
fontsize="72%";
fontstyle="normal";
headerbgcolor="#AFD1B5";
headerborder=1;
headercolor="#000099";
offbgcolor="#CFE2D1";
offcolor="#000000";
onbgcolor="#FEFAD2";
onborder="1px solid #999999";
oncolor="#000000";
onsubimage="http://www.milonic.com/menuimages/on_downboxed.gif";
overbgimage="http://www.milonic.com/menuimages/backon_beige.gif";
padding=3;
pagebgcolor="#CFE2D1";
pagecolor="#000066";
pageimage="http://www.milonic.com/menuimages/db_red.gif";
separatoralign="right";
separatorcolor="#999999";
separatorwidth="85%";
subimage="http://www.milonic.com/menuimages/downboxed.gif";
}

with(vertStyle=new mm_style()){
styleid=1;
bordercolor="#999999";
borderstyle="solid";
borderwidth=1;
fontfamily="arial, tahoma";
fontsize="72%";
fontstyle="normal";
headerbgcolor="#AFD1B5";
headerborder=1;
headercolor="#000099";
image="http://www.milonic.com/menuimages/18_blank.gif";
imagepadding=3;
menubgimage="http://www.milonic.com/menuimages/backoff_green.gif";
offbgcolor="transparent";
offcolor="#000000";
onbgcolor="#FEFAD2";
onborder="1px solid #999999";
oncolor="#000000";
onsubimage="http://www.milonic.com/menuimages/on_13x13_greyboxed.gif";
outfilter="randomdissolve(duration=0.2)";
overfilter="Fade(duration=0.1);Alpha(opacity=95);Shadow(color=#777777', Direction=135, Strength=3)";
padding=3;
pagebgcolor="#CFE2D1";
pagecolor="#000066";
pageimage="http://www.milonic.com/menuimages/db_red.gif";
separatoralign="right";
separatorcolor="#999999";
separatorpadding=1;
separatorwidth="85%";
subimage="http://www.milonic.com/menuimages/black_13x13_greyboxed.gif";
menubgcolor="#F5F5F5";
}

with(milonic=new menuname("Sample mainmenu")){
alwaysvisible=1;
left=10;
margin=2;
orientation="horizontal";
style=horizStyle;
top=10;
aI("text=Home;title=Back to Home Page;url=/;");
aI("showmenu=Sample about;text=About Milonic;");
aI("showmenu=Sample dhtml menu;text=DHTML Menu;");
aI("showmenu=Sample products;text=Products;");
aI("showmenu=Sample support menu;text=Support;");
aI("showmenu=Sample my milonic;text=My Milonic;url=mymilonic.php;");
aI("showmenu=Sample search;text=Search;url=/search.php;");
}

with(milonic=new menuname("Sample about")){
margin=3;
style=vertStyle;
top="offset=2";
aI("image=http://www.milonic.com/menuimages/18_about.gif;text=About Us;url=/aboutus.php;");
aI("image=http://www.milonic.com/menuimages/18_testimonial.gif;text=Testimonials;url=/testimonials.php;");
aI("image=http://www.milonic.com/menuimages/18_corporate.gif;text=Distinguished Clients;url=/corp_customers.php;");
aI("image=http://www.milonic.com/menuimages/18_nonprofit.gif;text=Investing in Non-Profits;url=/nonprofits.php;");
aI("image=http://www.milonic.com/menuimages/18_where.gif;text=Where Are We;url=/location.php;");
aI("image=http://www.milonic.com/menuimages/18_contact.gif;text=Contact Us;url=/contactus.php;");
aI("image=http://www.milonic.com/menuimages/18_privacy.gif;text=Privacy Policy;url=/privacy.php;");
aI("image=http://www.milonic.com/menuimages/18_license.gif;text=Software Licensing Agreement;url=/license.php;");
}

with(milonic=new menuname("Sample dhtml menu")){
margin=3;
style=vertStyle;
top="offset=2";
aI("image=http://www.milonic.com/menuimages/18_purchase.gif;text=Software Purchasing Pages;url=/cbuy.php;");
aI("image=http://www.milonic.com/menuimages/18_lic.gif;text=Licensing;url=/licensing.php;");
aI("image=http://www.milonic.com/menuimages/18_freelic.gif;separatorsize=1;text=Free Licenses;url=/freelicreq.php;");
aI("showmenu=Sample menusamples;text=DHTML Menu Samples;url=/menu.php;");
aI("image=http://www.milonic.com/menuimages/18_product.gif;text=Product Information;url=/productinfo.php;");
aI("image=http://www.milonic.com/menuimages/18_integration.gif;text=Page Integration;url=/integration.php;");
aI("image=http://www.milonic.com/menuimages/18_quick.gif;showmenu=Sample quickref;text=Menu Quick Reference Guides;");
aI("image=http://www.milonic.com/menuimages/18_version.gif;separatorsize=1;text=Menu Version Information;url=/menuvinfo.php;");
aI("text=Frames Based Menu (version 3);url=/menu/frames/;");
aI("image=http://www.milonic.com/menuimages/18_converter.gif;text=Version 3 to Version 5.0 Converter;url=/converter.php;");
aI("text=Menu Logos;url=/logos.php;");
aI("image=http://www.milonic.com/menuimages/18_user.gif;text=List of DHTML Menu users;url=/list/;");
}

with(milonic=new menuname("Sample quickref")){
margin=3;
style=vertStyle;
aI("text=Methods and Functions;url=/menu_methods.php;");
aI("text=Global Variables;url=/menu_variables.php;");
aI("text=Global Properties;url=/global_properties;");
aI("text=Style Properties;url=/styleproperties.php;");
aI("text=Menu Properties;url=/menuproperties.php;");
aI("text=Menu Item Properties;url=/itemproperties.php;");
}

with(milonic=new menuname("Sample support menu")){
margin=3;
style=vertStyle;
top="offset=2";
aI("image=http://www.milonic.com/menuimages/18_testimonial.gif;text=Menu Discussion Forum;url=/forum/;");
aI("text=Customer Tech Support System;url=/support/;");
aI("image=http://www.milonic.com/menuimages/18_news.gif;separatorsize=1;text=Newsletter Subscription;url=/newsletter.php;");
aI("text=Removing the Forced link to Milonic;url=/removelink.php;");
aI("image=http://www.milonic.com/menuimages/18_googlemenu.gif;text=Search Engines and the Menu;url=/searchengines_milonic.php;");
aI("image=http://www.milonic.com/menuimages/18_tablecell.gif;text=Embedding a Menu inside a Table Cell;url=/tablemenu.php;");
aI("text=Adding Multiple Menus to a Web Page;url=/multiplemenus.php;");
aI("image=http://www.milonic.com/menuimages/18_css.gif;text=CSS Based Menus;url=/cssbasedmenus.php;");
aI("image=http://www.milonic.com/menuimages/18_faq.gif;separatorsize=1;showmenu=Sample faq;text=FAQ;");
aI("text=Apache Web Server Installation;url=/apachesetup.php;");
aI("image=http://www.milonic.com/menuimages/18_iis.gif;text=Internet Information Server Setup;url=/iissetup.php;");
}

with(milonic=new menuname("Sample faq")){
margin=3;
style=vertStyle;
aI("text=DHTML Menu;url=/menufaq.php;");
aI("text=Website & Loggin In;url=/websitefaq.php;");
aI("text=Licensing;url=/licfaq.php;");
aI("text=Purchasing;url=/purfaq.php;");
}

with(milonic=new menuname("Sample menusamples")){
margin=3;
overflow="scroll";
style=vertStyle;
aI("text=Plain Text Horizontal Style DHTML Menu Bar;url=http://www.milonic.com/menusample1.php;")
aI("text=Vertical Plain Text Menu;url=http://www.milonic.com/menusample2.php;")
aI("text=All Horizontal Menus;url=http://www.milonic.com/menusample25.php;")
aI("text=Using The Popup Menu Function Positioned by Images;url=http://www.milonic.com/menusample24.php;")
aI("text=Classic XP Style Menu;url=http://www.milonic.com/menusample82.php;")
aI("text=XP Style;url=http://www.milonic.com/menusample86.php;")
aI("text=XP Taskbar Style Menu;url=http://www.milonic.com/menusample83.php;")
aI("text=Office XP Style Menu;url=http://www.milonic.com/menusample47.php;")
aI("text=Office 2003 Style Menu;url=http://www.milonic.com/menusample46.php;")
aI("text=Apple Mac Style Menu;url=http://www.milonic.com/menusample72.php;")
aI("text=Amazon Style Tab Menu;url=http://www.milonic.com/menusample74.php;")
aI("text=Milonic Home Menu;url=http://www.milonic.com/menusample78.php;")
aI("text=Windows 98 Style Menu;url=http://www.milonic.com/menusample13.php;")
aI("text=Multiple Styles Menu;url=http://www.milonic.com/menusample5.php;")
aI("text=Multi Colored Menu Items;url=http://www.milonic.com/menusample80.php;")
aI("text=Multi Colored Menus Using Styles;url=http://www.milonic.com/menusample7.php;")
aI("text=Multi Tab;url=http://www.milonic.com/menusample50.php;")
aI("text=Tab Top;url=http://www.milonic.com/menusample52.php;")
aI("text=Multi Columns;url=http://www.milonic.com/menusample73.php;")
aI("text=100% Width Span Menu;url=http://www.milonic.com/menusample26.php;")
aI("text=Follow Scrolling Style Menu;url=http://www.milonic.com/menusample10.php;")
aI("text=Menu Positioning With Offsets;url=http://www.milonic.com/menusample23.php;")
aI("text=Table Based (Relative) Menus;url=http://www.milonic.com/menusample9.php;")
aI("text=Opening Windows and Frames with the Menu;url=http://www.milonic.com/menusample11.php;")
aI("text=Menus Over Form Selects, Flash and Java Applets;url=http://www.milonic.com/menusample14.php;")
aI("text=Activating Functions on Mouseover;url=http://www.milonic.com/menusample15.php;")
aI("text=Drag Drop Menus;url=http://www.milonic.com/menusample22.php;")
aI("text=Menus with Header Type Items;url=http://www.milonic.com/menusample8.php;")
aI("text=Right To Left Openstyle;url=http://www.milonic.com/menusample65.php;")
aI("text=Up Openstyle Featuring Headers;url=http://www.milonic.com/menusample33.php;")
aI("text=DHTML Menus and Tooltips;url=http://www.milonic.com/menusample6.php;")
aI("text=Unlimited Level Menus Example;url=http://www.milonic.com/menusample67.php;")
aI("text=Context Right Click Menu;url=http://www.milonic.com/menusample27.php;")
aI("text=Menus built entirely from images;url=http://www.milonic.com/menusample18.php;")
aI("text=Static Images Sample;url=http://www.milonic.com/menusample16.php;")
aI("text=Rollover Swap Images Sample;url=http://www.milonic.com/menusample17.php;")
aI("text=Background Item Images;url=http://www.milonic.com/menusample20.php;")
aI("text=Background Image Buttons;url=http://www.milonic.com/menusample89.php;")
aI("text=Menu Background Images;url=http://www.milonic.com/menusample76.php;")
aI("text=Creating Texture with Menu Background Images;url=http://www.milonic.com/menusample19.php;")
aI("text=Static Background Item Images;url=http://www.milonic.com/menusample71.php;")
aI("text=Vertical Static Background Item Images;url=http://www.milonic.com/menusample87.php;")
aI("text=World Map Sample;url=http://www.milonic.com/menusample92.php;")
aI("text=US Map Sample;url=http://www.milonic.com/menusample91.php;")
aI("text=Image Map Sample;url=http://www.milonic.com/menusample4.php;")
aI("text=Rounded Edges Imperial Style;url=http://www.milonic.com/menusample21.php;")
aI("text=Corporation;url=http://www.milonic.com/menusample40.php;")
aI("text=International;url=http://www.milonic.com/menusample70.php;")
aI("text=Clean;url=http://www.milonic.com/menusample32.php;")
aI("text=3D Gradient Block;url=http://www.milonic.com/menusample57.php;")
aI("text=Descreet;url=http://www.milonic.com/menusample42.php;")
aI("text=Agriculture;url=http://www.milonic.com/menusample41.php;")
aI("text=Breeze;url=http://www.milonic.com/menusample29.php;")
aI("text=Chart;url=http://www.milonic.com/menusample66.php;")
aI("text=Cartoon;url=http://www.milonic.com/menusample77.php;")
aI("text=Start Button Menu;url=http://www.milonic.com/menusample69.php;")
aI("text=Tramline;url=http://www.milonic.com/menusample54.php;")

}

with(milonic=new menuname("Sample my milonic")){
margin=3;
style=vertStyle;
top="offset=2";
aI("text=View/Edit My Details;url=/mydetails.php;");
aI("text=Change My Password;url=/changepw.php;");
aI("text=My Licenses;url=/mylicenses.php;");
aI("text=My Invoices;url=/myinvoices.php;");
aI("text=Pay My Invoices;url=/paymyinvoices.php;");
aI("text=My Discounts;url=/mydiscounts.php;");
aI("text=Purchase a new license;url=/purnewlicense.php;");
aI("text=My Orders;url=/myorders.php;");
}

with(milonic=new menuname("Sample search")){
margin=3;
style=vertStyle;
top="offset=2";
aI("text=SEARCH;");
}

with(milonic=new menuname("Sample products")){
margin=3;
style=vertStyle;
top="offset=2";
aI("image=http://www.milonic.com/menuimages/18_purchase.gif;text=Product Pricing & Purchasing;url=/pricing.php;");
aI("text=SCombo;url=/scombo/index.php;");
aI("text=Tooltips (Coming Soon);url=/tooltips.php;");
aI("image=http://www.milonic.com/menuimages/18_color.gif;text=Color Picker;url=/colorpicker.php;");
aI("image=http://www.milonic.com/menuimages/calendar.gif;text=Date Picker/Calendar;url=/datepicker.php;");
aI("showmenu=Sample tools;text=Free Tools;");
}

with(milonic=new menuname("Sample tools")){
margin=3;
style=vertStyle;
aI("image=http://www.milonic.com/menuimages/18_compress.gif;text=JavaScript Compressor;url=/jscompactor.php;");
aI("image=http://www.milonic.com/menuimages/18_probe.gif;text=Web Server Probe;url=/probe.php;");
aI("image=http://www.milonic.com/menuimages/18_whois.gif;text=Whois Domain Name Lookup;url=/whois.php;");
}
drawMenus();

