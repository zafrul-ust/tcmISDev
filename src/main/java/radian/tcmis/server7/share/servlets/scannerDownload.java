package radian.tcmis.server7.share.servlets;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.*;

public class scannerDownload
   extends TcmisServletObj
{
  //Client Version
  String function=null;
  Hashtable mySendObj=null;
  Hashtable inData=null;
  Vector reportfields=new Vector();
  Vector reportData=new Vector();
  int reportsize=0;
  int NoRecords=0;
  TcmISDBModel opsdb = null;

  public static Hashtable ReportTable=new Hashtable();

  public scannerDownload( ServerResourceBundle b,TcmISDBModel d )
  {
    super();
    bundle=b;
    db=d;
    opsdb = new RayTcmISDBModel("Tcm_Ops");
    //opsdb = new RayTcmISDBModel("Tcm_Ops","1");
  }

  protected void resetAllVars()
  {
    function=null;
    inData=null;
  }

  protected void print( TcmisOutputStreamServer out )
  {
    try
    {
      out.sendObject( ( Hashtable ) mySendObj );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
  }

  protected void getResult()
  {
    mySendObj=new Hashtable();
    inData= ( Hashtable ) myRecvObj;
    //System.out.println( "in data: " + inData );
    String function= ( String ) inData.get( "ACTION" );
    if ( function.equalsIgnoreCase( "GET_INITIAL_INFO" ) )
    {
      getinitialInfo();
    }
    else if ( function.equalsIgnoreCase( "GET_CABINET_INFO" ) )
    {
      getcabinetInfo();
    }
    else if ( function.equalsIgnoreCase( "GET_DOWNLOAD_INFO" ) )
    {
      getdownloadInfo();
    }
    else if ( function.equalsIgnoreCase( "DO_DOWNLOAD_UPDATE" ) )
    {
      douploaddata();
    }

    opsdb.close();
  }

  protected void douploaddata()
  {
    try {
      scannerDownloadDBObj ahr=new scannerDownloadDBObj( opsdb );
      Hashtable h=ahr.douploaddata( inData );
      mySendObj.put( "UPLOAD_RESULT",h );
    }catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  protected void getdownloadInfo()
  {
    try
    {
      scannerDownloadDBObj ahr=new scannerDownloadDBObj( opsdb );
      Hashtable h=ahr.getdownloadInfo( inData );
      mySendObj.put( "DOWNLOAD_INFO",h );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
  }

  protected void getinitialInfo()
  {
    try
    {
      scannerDownloadDBObj ahr=new scannerDownloadDBObj( opsdb );
      Hashtable h=ahr.getinitialInfo( inData );
      mySendObj.put( "INITIAL_INFO",h );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
  }
  protected void getcabinetInfo()
  {
    try
    {
      scannerDownloadDBObj ahr=new scannerDownloadDBObj( opsdb );
      Hashtable h=ahr.getcabinetInfo( inData );
      mySendObj.put( "CABINET_INFO",h );
    }
    catch ( Exception e )
    {
      e.printStackTrace();
    }
  }

} //end of class


