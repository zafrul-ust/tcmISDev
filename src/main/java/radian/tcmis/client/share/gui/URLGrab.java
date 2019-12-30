package radian.tcmis.client.share.gui;
import java.net.*;

public class URLGrab {

  public static final int MSDS = 0;
  public static final int COMP = 1;
  public static final int STOR = 2;
  public static final int AIR = 3;
  public static final int LAND = 4;
  public static final int PROP = 5;
  public static final int SPEC = 6;
  public static final int PROFILE = 7;
  public static final int WPROP = 8;
  public static final int DISP = 9;
  public static final int LABEL = 10;
  public static final int PRINTSCREEN = 11;
  public static final int PROCESSDETAIL = 12;

  private String url;
  private URL U;
  CmisApp parent ;
  public URLGrab(int code,String qstr,CmisApp p) {
    parent = p;
    // url = new String(server);

    //trong 9-12 new
    url = parent.getResourceBundle().getString("MSDS_URL").toString();


    //orginal url trong 9-12
    //url = new String("/servlet/radian.web.servlets.ray.RaymsdsSideView?");
    String qtmp = new String("");

    switch(code) {
      case MSDS:
        qtmp = new String("act=msds&" + qstr);
        break;
      case COMP:
        qtmp = new String("act=comp&" + qstr);
        break;
      case STOR:
        qtmp = new String("act=store&" + qstr);
        break;
      case AIR:
        qtmp = new String("act=air&" + qstr);
        break;
      case LAND:
        qtmp = new String("act=land&" + qstr);
        break;
      case PROP:
        qtmp = new String("act=prop&" + qstr);
        break;
      case SPEC:
        qtmp = new String("act=spec&" + qstr);
        break;
      case PROFILE:
        qtmp = new String("act=profile&" + qstr);
        url = parent.getResourceBundle().getString("WASTE_URL").toString();
        break;
      case WPROP:
        qtmp = new String("act=prop&" + qstr);
        url = parent.getResourceBundle().getString("WASTE_URL").toString();
        break;
      case DISP:
        qtmp = new String("act=disp&" + qstr);
        url = parent.getResourceBundle().getString("WASTE_URL").toString();
        break;
      case LABEL:
        qtmp = new String(qstr);
        url = parent.getResourceBundle().getString("PRINT_WASTE_LABEL_URL").toString();
        break;
      case PRINTSCREEN:
        qtmp = new String(qstr);
        url = parent.getResourceBundle().getString("PRINT_SCREEN_URL").toString();
        break;
      case PROCESSDETAIL:
        qtmp = new String(qstr);
        url = parent.getResourceBundle().getString("PROCESS_DETAIL_URL").toString();
        break;
      default:
        qtmp = new String("");
    }
    url = url + qtmp;

    try {
      // From the stand alone application
      new URLCall(url,parent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}




























