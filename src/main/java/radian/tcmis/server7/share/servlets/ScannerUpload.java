package radian.tcmis.server7.share.servlets;

  //Nawaz 12-11-00
import java.io.IOException;
import java.util.Hashtable;
import javax.servlet.ServletException;

import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.scannerDownloadDBObj;

/**
* <p>Title: All the code</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2003</p>
* <p>Company: Haas TCM</p>
* @author Nawaz Shaik
* @version 1.0
*
*/

  public class ScannerUpload extends TcmISServletReportObj
  {
    private Hashtable mySendObj=null;
    private Hashtable inData=null;
    private String userId=null;
    TcmISDBModel opsdb = null;
    public void doPost() throws ServletException,IOException
    {

    }

    public ScannerUpload( ServerResourceBundle b,TcmISDBModel d )
    {
      super();
      bundle=b;
      db=d;
      opsdb = new RayTcmISDBModel("Tcm_Ops");
      //opsdb = new RayTcmISDBModel("Tcm_Ops","1");
    }

    protected void resetAllVars()
    {
      inData=null;
    }

    protected void print( TcmisOutputStreamServer out )
    {
      mySendObj=new Hashtable();
      Hashtable h = new Hashtable(2);
      h.put("SUCCESS",new Boolean(true));
      h.put("MSG","Your request was successfully submitted to the server.\ntcmIS will send you an email upon completion of this upload.");
      mySendObj.put( "UPLOAD_RESULT",h );
      try
      {
        out.sendObject( ( Hashtable ) mySendObj );
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
    }

    protected void setInData( Hashtable myRecvObj )
    {
      inData=myRecvObj;
    }

    protected void getResult() {
      try {
        inData= ( Hashtable ) myRecvObj;
        //System.out.println( "in data: " + inData );
        String function = (String) inData.get("ACTION");
        if (function.equalsIgnoreCase("DO_UPLOAD_UPDATE")) {
          doUploadData();
        }
        opsdb.close();
      }catch (Exception E) {
        E.printStackTrace();
      }
    } //end of method

    protected void doUploadData()
    {
      try {
        scannerDownloadDBObj ahr=new scannerDownloadDBObj( opsdb );
        Hashtable h=ahr.douploaddata( inData );
      }catch ( Exception e ) {
        e.printStackTrace();
      }
    }

} //end of class

