/**
 * Title:        Database Resoource Loader
 * Copyright:    Copyright (c) 2003
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * I am putting a bunch of get resource functions in this so that I dont have to be dependent on the
 * file path and the machine I am working on for database stuff
 *
 *01-09-04 - Chaging the name of userId to correspond to the new resource bundle
 *
 * 3-26-04 adding Boeing to the mix
 *
 * 6-9-04 adding Aero Eco to the mix
 */
package radian.web;

import radian.tcmis.server7.share.db.ResourceLibrary;

public abstract class databaseResourceLoader
{
  private static ResourceLibrary tcmISresource=null;

  public static String getdbDriver()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String dbdriver=tcmISresource.getString( "dbdriver" );

    return dbdriver;
  }

  public static String getproddburl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String proddatabase=tcmISresource.getString( "proddatabase" );

    return proddatabase;
  }

  public static String getqadburl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String proddatabase=tcmISresource.getString( "qadatabase" );

    return proddatabase;
  }

  public static String gethawkdevdburl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String hawkdevdatabase=tcmISresource.getString( "hawkdevdatabase" );

    return hawkdevdatabase;
  }

  public static String getkawktsturl()
  {
    tcmISresource=new ResourceLibrary( "tcmISWebResource" );
    String hawktstdatabase=tcmISresource.getString( "hawktstdatabase" );

    return hawktstdatabase;
  }

  public static String getrayuserid()
  {
    tcmISresource=new ResourceLibrary( "RayTcmISDatabase" );
    String rayuserid=tcmISresource.getString( "database.user" );

    return rayuserid;
  }

  public static String getbaeuserid()
  {
    tcmISresource=new ResourceLibrary( "BAETcmISDatabase" );
    String baeuserid=tcmISresource.getString( "database.user" );
    return baeuserid;
  }

  public static String getdrsuserid()
  {
    tcmISresource=new ResourceLibrary( "DRSTcmISDatabase" );
    String drsuserid=tcmISresource.getString( "database.user" );
    return drsuserid;
  }

  public static String getopsuserid()
  {
    tcmISresource=new ResourceLibrary( "InternalTcmISDatabase" );
    String opsuserid=tcmISresource.getString( "database.user" );

    return opsuserid;
  }

  public static String getlmcouserid()
  {
    tcmISresource=new ResourceLibrary( "LMCOTcmISDatabase" );
    String lmcouserid=tcmISresource.getString( "database.user" );
    return lmcouserid;
  }

  public static String getmilleruserid()
  {
    tcmISresource=new ResourceLibrary( "MillerTcmISDatabase" );
    String milleruserid=tcmISresource.getString( "database.user" );
    return milleruserid;
  }

  public static String getsduserid()
  {
    tcmISresource=new ResourceLibrary( "SDTcmISDatabase" );
    String sduserid=tcmISresource.getString( "database.user" );
    return sduserid;
  }

  public static String getseagateuserid()
  {
    tcmISresource=new ResourceLibrary( "SeagateTcmISDatabase" );
    String seagateuserid=tcmISresource.getString( "database.user" );
    return seagateuserid;
  }

  public static String getswauserid()
  {
    tcmISresource=new ResourceLibrary( "SWATcmISDatabase" );
    String swauserid=tcmISresource.getString( "database.user" );
    return swauserid;
  }

  public static String getutcuserid()
  {
    tcmISresource=new ResourceLibrary( "UTCTcmISDatabase" );
    String utcuserid=tcmISresource.getString( "database.user" );
    return utcuserid;
  }

  public static String getgmpassword()
  {
    tcmISresource=new ResourceLibrary( "GMTcmISDatabase" );
    String gmuserid=tcmISresource.getString( "database.password" );
    return gmuserid;
  }

  public static String getgmuserid()
  {
    tcmISresource=new ResourceLibrary( "GMTcmISDatabase" );
    String gmuserid=tcmISresource.getString( "database.user" );
    return gmuserid;
  }


  public static String getcalpassword()
  {
    tcmISresource=new ResourceLibrary( "CALTcmISDatabase" );
    String gmuserid=tcmISresource.getString( "database.password" );
    return gmuserid;
  }

  public static String getcaluserid()
  {
    tcmISresource=new ResourceLibrary( "CALTcmISDatabase" );
    String caluserid=tcmISresource.getString( "database.user" );
    return caluserid;
  }

  public static String getfecpassword()
  {
    tcmISresource=new ResourceLibrary( "FECTcmISDatabase" );
    String fecuserid=tcmISresource.getString( "database.password" );
    return fecuserid;
  }

  public static String getfecuserid()
  {
    tcmISresource=new ResourceLibrary( "FECTcmISDatabase" );
    String fecuserid=tcmISresource.getString( "database.user" );
    return fecuserid;
  }

  public static String getboeingpassword()
  {
    tcmISresource=new ResourceLibrary( "BoeingTcmISDatabase" );
    String boeinguserid=tcmISresource.getString( "database.password" );
    return boeinguserid;
  }

  public static String getboeinguserid()
  {
    tcmISresource=new ResourceLibrary( "BoeingTcmISDatabase" );
    String boeinguserid=tcmISresource.getString( "database.user" );
    return boeinguserid;
  }

  public static String getaeroecopassword()
  {
    tcmISresource=new ResourceLibrary( "AeroEcoTcmISDatabase" );
    String aeroecouserid=tcmISresource.getString( "database.password" );
    return aeroecouserid;
  }

  public static String getaeroecouserid()
  {
    tcmISresource=new ResourceLibrary( "AeroEcoTcmISDatabase" );
    String aeroecouserid=tcmISresource.getString( "database.user" );
    return aeroecouserid;
  }

  public static String getdanapassword()
  {
    tcmISresource=new ResourceLibrary( "DanaTcmISDatabase" );
    String danauserid=tcmISresource.getString( "database.password" );
    return danauserid;
  }

  public static String getdanauserid()
  {
    tcmISresource=new ResourceLibrary( "DanaTcmISDatabase" );
    String danauserid=tcmISresource.getString( "database.user" );
    return danauserid;
  }

  public static String getablaeropassword()
  {
    tcmISresource=new ResourceLibrary( "ABLaeroTcmISDatabase" );
    String ablaerouserid=tcmISresource.getString( "database.password" );
    return ablaerouserid;
  }

  public static String getablaerouserid()
  {
    tcmISresource=new ResourceLibrary( "ABLaeroTcmISDatabase" );
    String ablaerouserid=tcmISresource.getString( "database.user" );
    return ablaerouserid;
  }

  public static String getdoepassword()
  {
    tcmISresource=new ResourceLibrary( "DOETcmISDatabase" );
    String doeuserid=tcmISresource.getString( "database.password" );
    return doeuserid;
  }

  public static String getdoeuserid()
  {
    tcmISresource=new ResourceLibrary( "DOETcmISDatabase" );
    String doeuserid=tcmISresource.getString( "database.user" );
    return doeuserid;
  }

  public static String getIAIPassword()
  {
    tcmISresource=new ResourceLibrary( "IAITcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getIAIUserId()
  {
    tcmISresource=new ResourceLibrary( "IAITcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getGemaPassword()
  {
    tcmISresource=new ResourceLibrary( "GemaTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getGemaUserId()
  {
    tcmISresource=new ResourceLibrary( "GemaTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getPGEPassword()
  {
    tcmISresource=new ResourceLibrary( "PGETcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getPGEUserId()
  {
    tcmISresource=new ResourceLibrary( "PGETcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getSalesPassword()
  {
    tcmISresource=new ResourceLibrary( "SalesTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getSalesUserId()
  {
    tcmISresource=new ResourceLibrary( "SalesTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getHexcelPassword()
  {
    tcmISresource=new ResourceLibrary( "HexcelTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getHexcelUserId()
  {
    tcmISresource=new ResourceLibrary( "HexcelTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

 public static String getZfPassword()
  {
    tcmISresource=new ResourceLibrary( "ZFTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getZfUserId()
  {
    tcmISresource=new ResourceLibrary( "ZFTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

 public static String getAvcorpPassword()
  {
    tcmISresource=new ResourceLibrary( "AvcorpTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getAvcorpUserId()
  {
    tcmISresource=new ResourceLibrary( "AvcorpTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }
	
 public static String getBaPassword()
  {
    tcmISresource=new ResourceLibrary( "BATcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getBaUserId()
  {
    tcmISresource=new ResourceLibrary( "BATcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getQOSPassword()
  {
    tcmISresource=new ResourceLibrary( "QOSTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getQOSUserId()
  {
    tcmISresource=new ResourceLibrary( "QOSTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getGEPassword()
  {
    tcmISresource=new ResourceLibrary( "GETcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getGEUserId()
  {
    tcmISresource=new ResourceLibrary( "GETcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getDCXPassword()
  {
    tcmISresource=new ResourceLibrary( "DCXTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getDCXUserId()
  {
    tcmISresource=new ResourceLibrary( "DCXTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getFordPassword()
  {
    tcmISresource=new ResourceLibrary( "FordTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getFordUserId()
  {
    tcmISresource=new ResourceLibrary( "FordTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getAlgatPassword()
  {
    tcmISresource=new ResourceLibrary( "AlgatTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getAlgatUserId()
  {
    tcmISresource=new ResourceLibrary( "AlgatTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getBAZPassword()
  {
    tcmISresource=new ResourceLibrary( "BAZTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getBAZUserId()
  {
    tcmISresource=new ResourceLibrary( "BAZTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getCMMPassword()
  {
    tcmISresource=new ResourceLibrary( "CMMTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getCMMUserId()
  {
    tcmISresource=new ResourceLibrary( "CMMTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getKanfitPassword()
  {
    tcmISresource=new ResourceLibrary( "KanfitTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getKanfitUserId()
  {
    tcmISresource=new ResourceLibrary( "KanfitTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getKemfastPassword()
  {
    tcmISresource=new ResourceLibrary( "KemfastTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getKemfastUserId()
  {
    tcmISresource=new ResourceLibrary( "KemfastTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getBLPassword()
  {
    tcmISresource=new ResourceLibrary( "BLTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getBLUserId()
  {
    tcmISresource=new ResourceLibrary( "BLTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getVerasunPassword()
  {
    tcmISresource=new ResourceLibrary( "VerasunTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getVerasunUserId()
  {
    tcmISresource=new ResourceLibrary( "VerasunTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getHrgivonPassword()
  {
    tcmISresource=new ResourceLibrary( "HrgivonTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getHrgivonUserId()
  {
    tcmISresource=new ResourceLibrary( "HrgivonTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getSGDPassword()
  {
    tcmISresource=new ResourceLibrary( "SGDTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getSGDUserId()
  {
    tcmISresource=new ResourceLibrary( "SGDTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getCaterpillarPassword()
  {
    tcmISresource=new ResourceLibrary( "CaterpillarTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getCaterpillarUserId()
  {
    tcmISresource=new ResourceLibrary( "CaterpillarTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getOMAPassword()
  {
    tcmISresource=new ResourceLibrary( "OMATcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getOMAUserId()
  {
    tcmISresource=new ResourceLibrary( "OMATcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getSiemensPassword()
  {
    tcmISresource=new ResourceLibrary( "SiemensTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getSiemensUserId()
  {
    tcmISresource=new ResourceLibrary( "SiemensTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getGetragPassword()
  {
    tcmISresource=new ResourceLibrary( "GetragTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getGetragUserId()
  {
    tcmISresource=new ResourceLibrary( "GetragTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getAlcoaPassword()
  {
    tcmISresource=new ResourceLibrary( "AlcoaTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getAlcoaUserId()
  {
    tcmISresource=new ResourceLibrary( "AlcoaTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getZWLPassword()
  {
    tcmISresource=new ResourceLibrary( "ZWLTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getZWLUserId()
  {
    tcmISresource=new ResourceLibrary( "ZWLTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getHansSasserathPassword()
  {
    tcmISresource=new ResourceLibrary( "HansSasserathTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getHansSasserathUserId()
  {
    tcmISresource=new ResourceLibrary( "HansSasserathTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

	public static String getDDPassword()
  {
    tcmISresource=new ResourceLibrary( "DDTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getDDUserId()
  {
    tcmISresource=new ResourceLibrary( "DDTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

	public static String getGknPassword()
	{
	  tcmISresource=new ResourceLibrary( "GKNTcmISDatabase" );
	  String password =tcmISresource.getString( "database.password" );
	  return password;
	}

	public static String getGknUserId()
	{
	  tcmISresource=new ResourceLibrary( "GKNTcmISDatabase" );
	  String userID=tcmISresource.getString( "database.user" );
	  return userID;
	}

	public static String getAeropiaPassword()
	{
	  tcmISresource=new ResourceLibrary( "AeropiaTcmISDatabase" );
	  String password =tcmISresource.getString( "database.password" );
	  return password;
	}

	public static String getAeropiaUserId()
	{
	  tcmISresource=new ResourceLibrary( "AeropiaTcmISDatabase" );
	  String userID=tcmISresource.getString( "database.user" );
	  return userID;
	}

	public static String getGoodrichPassword()
	{
	  tcmISresource=new ResourceLibrary( "GoodrichTcmISDatabase" );
	  String password =tcmISresource.getString( "database.password" );
	  return password;
	}

	public static String getOMMCUserId()
	{
	  tcmISresource=new ResourceLibrary( "OMMCTcmISDatabase" );
	  String userID=tcmISresource.getString( "database.user" );
	  return userID;
	}

	public static String getOMMCPassword()
	{
	  tcmISresource=new ResourceLibrary( "OMMCTcmISDatabase" );
	  String password =tcmISresource.getString( "database.password" );
	  return password;
	}

	public static String getFBMUserId()
	{
	  tcmISresource=new ResourceLibrary( "FBMTcmISDatabase" );
	  String userID=tcmISresource.getString( "database.user" );
	  return userID;
	}

	public static String getFBMPassword()
	{
	  tcmISresource=new ResourceLibrary( "FBMTcmISDatabase" );
	  String password =tcmISresource.getString( "database.password" );
	  return password;
	}
	
	public static String getAeroczUserId()
	{
	  tcmISresource=new ResourceLibrary( "AeroczTcmISDatabase" );
	  String userID=tcmISresource.getString( "database.user" );
	  return userID;
	}

	public static String getAeroczPassword()
	{
	  tcmISresource=new ResourceLibrary( "AeroczTcmISDatabase" );
	  String password =tcmISresource.getString( "database.password" );
	  return password;
	}

	public static String getGoodrichUserId()
	{
	  tcmISresource=new ResourceLibrary( "GoodrichTcmISDatabase" );
	  String userID=tcmISresource.getString( "database.user" );
	  return userID;
	}

  public static String getHaargazPassword()
  {
    tcmISresource=new ResourceLibrary( "HaargazTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getHaargazUserId()
  {
    tcmISresource=new ResourceLibrary( "HaargazTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getCyclonePassword()
  {
    tcmISresource=new ResourceLibrary( "CycloneTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getCycloneUserId()
  {
    tcmISresource=new ResourceLibrary( "CycloneTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getVolvoPassword()
  {
    tcmISresource=new ResourceLibrary( "VolvoTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getVolvoUserId()
  {
    tcmISresource=new ResourceLibrary( "VolvoTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getNalcoPassword()
  {
    tcmISresource=new ResourceLibrary( "NalcoTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getNalcoUserId()
  {
    tcmISresource=new ResourceLibrary( "NalcoTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getPepsiPassword()
  {
    tcmISresource=new ResourceLibrary( "PepsiTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getPepsiUserId()
  {
    tcmISresource=new ResourceLibrary( "PepsiTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getHALPassword()
  {
    tcmISresource=new ResourceLibrary( "HALTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getHALUserId()
  {
    tcmISresource=new ResourceLibrary( "HALTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getABMPassword()
  {
    tcmISresource=new ResourceLibrary( "ABMTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getABMUserId()
  {
    tcmISresource=new ResourceLibrary( "ABMTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getMTLPassword()
  {
    tcmISresource=new ResourceLibrary( "MTLTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getMTLUserId()
  {
    tcmISresource=new ResourceLibrary( "MTLTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getTimkenPassword()
  {
    tcmISresource=new ResourceLibrary( "TimkenTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getTimkenUserId()
  {
    tcmISresource=new ResourceLibrary( "TimkenTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getNRGPassword()
  {
    tcmISresource=new ResourceLibrary( "NRGTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getNRGUserId()
  {
    tcmISresource=new ResourceLibrary( "NRGTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getMAEETPassword()
  {
    tcmISresource=new ResourceLibrary( "MAEETTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getMAEETUserId()
  {
    tcmISresource=new ResourceLibrary( "MAEETTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getIMCOPassword()
  {
    tcmISresource=new ResourceLibrary( "IMCOTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getIMCOUserId()
  {
    tcmISresource=new ResourceLibrary( "IMCOTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getFedcoPassword()
  {
    tcmISresource=new ResourceLibrary( "FedcoTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getFedcoUserId()
  {
    tcmISresource=new ResourceLibrary( "FedcoTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getGDPassword()
  {
    tcmISresource=new ResourceLibrary( "GDTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getGDUserId()
  {
    tcmISresource=new ResourceLibrary( "GDTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getAMPassword()
  {
    tcmISresource=new ResourceLibrary( "AMTcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getAMUserId()
  {
    tcmISresource=new ResourceLibrary( "AMTcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }


  public static String getL3Password()
  {
    tcmISresource=new ResourceLibrary( "L3TcmISDatabase" );
    String password =tcmISresource.getString( "database.password" );
    return password;
  }

  public static String getL3UserId()
  {
    tcmISresource=new ResourceLibrary( "L3TcmISDatabase" );
    String userID=tcmISresource.getString( "database.user" );
    return userID;
  }

  public static String getraypassword()
  {
    tcmISresource=new ResourceLibrary( "RayTcmISDatabase" );
    String raypassword=tcmISresource.getString( "database.password" );

    return raypassword;
  }

  public static String getbaepassword()
  {
    tcmISresource=new ResourceLibrary( "BAETcmISDatabase" );
    String baepassword=tcmISresource.getString( "database.password" );

    return baepassword;
  }

  public static String getdrspassword()
  {
    tcmISresource=new ResourceLibrary( "DRSTcmISDatabase" );
    String drspassword=tcmISresource.getString( "database.password" );

    return drspassword;
  }

  public static String getopspassword()
  {
    tcmISresource=new ResourceLibrary( "InternalTcmISDatabase" );
    String opspassword=tcmISresource.getString( "database.password" );

    return opspassword;
  }

  public static String getlmcopassword()
  {
    tcmISresource=new ResourceLibrary( "LMCOTcmISDatabase" );
    String lmcopassword=tcmISresource.getString( "database.password" );

    return lmcopassword;
  }

  public static String getmillerpassword()
  {
    tcmISresource=new ResourceLibrary( "MillerTcmISDatabase" );
    String millerpassword=tcmISresource.getString( "database.password" );

    return millerpassword;
  }

  public static String getsdpassword()
  {
    tcmISresource=new ResourceLibrary( "SDTcmISDatabase" );
    String sdpassword=tcmISresource.getString( "database.password" );

    return sdpassword;
  }

  public static String getseagatepassword()
  {
    tcmISresource=new ResourceLibrary( "SeagateTcmISDatabase" );
    String seagatepassword=tcmISresource.getString( "database.password" );

    return seagatepassword;
  }

  public static String getswapassword()
  {
    tcmISresource=new ResourceLibrary( "SWATcmISDatabase" );
    String swapassword=tcmISresource.getString( "database.password" );

    return swapassword;
  }

  public static String getutcpassword()
  {
    tcmISresource=new ResourceLibrary( "UTCTcmISDatabase" );
    String utcpassword=tcmISresource.getString( "database.password" );

    return utcpassword;
  }

}