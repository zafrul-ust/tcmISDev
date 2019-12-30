/**
 * Title:        Resoource Loader
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * I am putting a bunch of get resource functions in this so that I dont have to be dependent on the
 * file path and the machine I am working on
 *
 */
package radian.web;

import radian.tcmis.server7.share.db.ResourceLibrary;

public abstract class tcmisResourceLoader
{
  private static ResourceLibrary tcmISresource=null;

  public static String getjnlpfilepath()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String filepathtojnlpfile=tcmISresource.getString( "filepathtojnlpfile" );

    return filepathtojnlpfile;
  }

  public static String getpossfilepath()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String possFileUploadPath=tcmISresource.getString( "possFileUploadPath" );

    return possFileUploadPath;
  }


  public static String getjnlpcodebase()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String jnlpcodebase=tcmISresource.getString( "jnlpcodebase" );

    return jnlpcodebase;
  }

  public static String gethosturl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String hosturl=tcmISresource.getString( "hosturl" );

    return hosturl;
  }

  public static String gethttphosturl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String httphosturl=tcmISresource.getString( "httphosturl" );

    return httphosturl;
  }

  public static String getcabinetmgmturl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String cabinetmgmturl=tcmISresource.getString( "cabinetmgmturl" );

    return cabinetmgmturl;
  }

  public static String getcabinetlabelurl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String cabinetlabelurl=tcmISresource.getString( "cabinetlabelurl" );

    return cabinetlabelurl;
  }

  public static String getcabinetbinlabelurl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String cabinetbinlabelurl=tcmISresource.getString( "cabinetbinlabelurl" );

    return cabinetbinlabelurl;
  }

  public static String getprerelase14dir()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String tprerelase14dir=tcmISresource.getString( "prerelease14dir" );

    return tprerelase14dir;
  }

  public static String getcabinetchglvlurl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String getcabinetchglvlurl=tcmISresource.getString( "cabinetchglvlurl" );

    return getcabinetchglvlurl;
  }

  public static String getfontpropertiespath()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String fontpropertiespath=tcmISresource.getString( "fontpropertiespath" );

    return fontpropertiespath;
  }

  public static String getsavelabelpath()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String savelabelpath=tcmISresource.getString( "savelabelpath" );

    return savelabelpath;
  }

  public static String getlabeltempplatepath()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String labeltempplatepath=tcmISresource.getString( "labeltempplatepath" );

    return labeltempplatepath;
  }

  public static String getcabinetdefurl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String cabinetdefurl=tcmISresource.getString( "cabinetdefurl" );

    return cabinetdefurl;
  }

  public static String hubitemcounturl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String hubitemcounturl=tcmISresource.getString( "hubitemcounturl" );

    return hubitemcounturl;
  }

  public static String getpologourl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String pologourl=tcmISresource.getString( "pologourl" );

    return pologourl;
  }

  public static String getsavepopath()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String savepopath=tcmISresource.getString( "savepopath" );

    return savepopath;
  }

  public static String getpotemplatepath()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String potemplatepath=tcmISresource.getString( "potemplatepath" );

    return potemplatepath;
  }

  public static String getpofinalurl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String pofinalurl=tcmISresource.getString( "pofinalurl" );

    return pofinalurl;
  }

  public static String getbuyerimageloc()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String buyerimageloc=tcmISresource.getString( "buyerimageloc" );

    return buyerimageloc;
  }

  public static String getgenlableinfourl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String genlableinfourl=tcmISresource.getString( "genlableinfourl" );

    return genlableinfourl;
 }

 public static String getreportstempplatepath()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String reportstempplatepath=tcmISresource.getString( "reportstempplatepath" );

   return reportstempplatepath;
 }

 public static String getsavelreportpath()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String savelreportpath=tcmISresource.getString( "savelreportpath" );

   return savelreportpath;
 }

 public static String getsaveltempreportpath()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String saveltempreportpath=tcmISresource.getString( "saveltempreportpath" );

   return saveltempreportpath;
 }

 public static String getreporturl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String reporturl=tcmISresource.getString( "reporturl" );

   return reporturl;
 }

 public static String gettempreporturl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String tempreporturl=tcmISresource.getString( "tempreporturl" );

   return tempreporturl;
 }

 public static String getdotshipnameurl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String dotshipnameurl=tcmISresource.getString( "dotshipnameurl" );

   return dotshipnameurl;
 }

  public static String geteditmsdsurl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String editmsdsurl=tcmISresource.getString( "editmsdsurl" );

    return editmsdsurl;
  }

  public static String getdeveloperemailaddress()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String developeremailadd=tcmISresource.getString( "developeremail" );

    return developeremailadd;
  }

  public static String getdatabaseHelpemails()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String databasedebughelp=tcmISresource.getString( "databasedebughelp" );

    return databasedebughelp;
  }

  public static String getnawazemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String nawazemail=tcmISresource.getString( "nawazemail" );

    return nawazemail;
  }

  public static String gettrongemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String trongemail=tcmISresource.getString( "trongemail" );

    return trongemail;
  }

  public static String getsteveemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String steveemail=tcmISresource.getString( "steveemail" );

    return steveemail;
  }

  public static String getnishemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String nishemail=tcmISresource.getString( "nishemail" );

    return nishemail;
  }

  public static String getmsdshelpemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String msdshelpemail=tcmISresource.getString( "msdshelpemail" );

    return msdshelpemail;
  }

  public static String getcatalogemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String catalogemail=tcmISresource.getString( "catalogemail" );

    return catalogemail;
  }

  public static String getdaveemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String daveemail=tcmISresource.getString( "daveemail" );

    return daveemail;
  }

  public static String getchuckemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String chuckemail=tcmISresource.getString( "chuckemail" );

    return chuckemail;
  }

  public static String getronemail()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String ronemail=tcmISresource.getString( "ronemail" );

    return ronemail;
  }

  public static String getmsdserrorurl()
  {
	tcmISresource=new ResourceLibrary( "tcmISWebResource" );
	String msdsspecerr=tcmISresource.getString( "msdsspecerr" );

	return msdsspecerr;
  }

  public static String getSeagateCRAURL()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    return (tcmISresource.getString( "seagateCRAURL" ));
  }

  public static String getnewsupplierurl()
  {
	tcmISresource=new ResourceLibrary( "tcmISWebResource" );
	String newsupplier=tcmISresource.getString( "newsupplier" );

	return newsupplier;
  }

  public static String getrepackagingurl()
  {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String repackaging=tcmISresource.getString( "repackaging" );

   return repackaging;
  }

  public static String getnewtapurl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String newtapurl=tcmISresource.getString( "newtapurl" );

  return newtapurl;
  }

  public static String getundotapurl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String undotapurl=tcmISresource.getString( "undotapurl" );

  return undotapurl;
  }

  public static String getclosetapurl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String closetapurl=tcmISresource.getString( "closetapurl" );

  return closetapurl;
  }

  public static String getforcerepackurl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String forcerepackurl=tcmISresource.getString( "forcerepackurl" );

  return forcerepackurl;
  }

  public static String getpropshipnamesurl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String propshipnames=tcmISresource.getString( "propshipnames" );

  return propshipnames;
  }

  public static String getcabinetcounturl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String cabinetcounturl=tcmISresource.getString( "cabinetcounturl" );

  return cabinetcounturl;
  }

  public static String getpinrtpolisturl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String pinrtedpolist=tcmISresource.getString( "pinrtedpolist" );

  return pinrtedpolist;
  }

  public static String getadditem2Hubcounturl()
  {
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String additem2Hubcount=tcmISresource.getString( "additem2Hubcount" );

  return additem2Hubcount;
  }

  public static String getinvtransferrequrl()
  {
	tcmISresource=new ResourceLibrary( "tcmISWebResource" );
	String invtransferreq=tcmISresource.getString( "invtransferreq" );

	return invtransferreq;
  }

  public static String getcanbinvurl()
  {
	tcmISresource=new ResourceLibrary( "tcmISWebResource" );
	String cabinventory=tcmISresource.getString( "cabinventory" );

	return cabinventory;
  }

  public static String getcabupconfirmurl()
  {
	tcmISresource=new ResourceLibrary( "tcmISWebResource" );
	String cabupconfirmurl=tcmISresource.getString( "cabupconfirmurl" );

	return cabupconfirmurl;
  }

  public static String getiteminvenurl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String iteminvenurl=tcmISresource.getString( "iteminvenurl" );

   return iteminvenurl;
 }

 public static String getitemshiptourl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String itemshiptourl=tcmISresource.getString( "itemshiptourl" );

   return itemshiptourl;
 }

 public static String getassigninvgrpwkareaurl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String assigninvgrpwkareaurl=tcmISresource.getString( "assigninvgrpwkareaurl" );

   return assigninvgrpwkareaurl;
 }

 public static String getassignaddinvgrpurl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String assignaddinvgrpurl=tcmISresource.getString( "assignaddinvgrpurl" );

   return assignaddinvgrpurl;
 }

 public static String gethublabelurl()
 {
 tcmISresource=new ResourceLibrary( "tcmISWebResource" );
 String hublabelurl=tcmISresource.getString( "hublabelurl" );

 return hublabelurl;
 }

 public static String gethubcountreporturl()
 {
   tcmISresource=new ResourceLibrary( "tcmISWebResource" );
   String hubcntreporturl=tcmISresource.getString( "hubcntreporturl" );

   return hubcntreporturl;
 }

 public static String getbintoscanurl()
{
  tcmISresource=new ResourceLibrary( "tcmISWebResource" );
  String bintoscanurl=tcmISresource.getString( "bintoscanurl" );

  return bintoscanurl;
}

public static String getminmaxurl()
{
 tcmISresource=new ResourceLibrary( "tcmISWebResource" );
 String minmaxurl=tcmISresource.getString( "minmaxurl" );

 return minmaxurl;
}

public static String getsuppliershipping()
{
tcmISresource=new ResourceLibrary( "tcmISWebResource" );
String suppliershipping=tcmISresource.getString( "suppliershipping" );

return suppliershipping;
}

public static String getseagatepickingurl()
{
 tcmISresource=new ResourceLibrary( "tcmISWebResource" );
 String seagatepickingurl=tcmISresource.getString( "seagatepickingurl" );

 return seagatepickingurl;
}

}
