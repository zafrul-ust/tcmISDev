package radian.tcmis.client.share.httpCgi;

import java.awt.Cursor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import radian.tcmis.client.share.gui.AuthDlg;
import radian.tcmis.client.share.gui.CmisApp;
import radian.tcmis.client.share.gui.CursorChange;
import radian.tcmis.client.share.gui.Main;
import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class TcmisHttpConnection {

  public static final int CHECK_CLIENT = 1;
  public static final int CHECK_LOGON = 2;
  public static final int CHECK_VERSION = 3;
  public static final int CATALOG = 4;
  public static final int REQUEST = 5;
  public static final int TRACK = 6;
  public static final int PERSONNEL = 7;
  public static final int INVENTORY = 8;
  public static final int USER_PROFILE = 9;
  public static final int SEARCH_INFO = 10;
  public static final int CATALOG_TABLE = 11;
  public static final int NEW_CHEM = 12;
  public static final int ADMIN = 13;
  public static final int NEW_CHEM_TRACKING = 14;
  public static final int REPORT = 15;
  public static final int PROXY = 16;
  public static final int MATERIAL_REQUEST = 17;
  public static final int WASTE = 18;
  public static final int WASTE_TEST = 19;

  protected int cgi_name = -99;

  protected String cgi = null;

  protected Hashtable result = new Hashtable();

  protected String host = (new ClientResourceBundle()).getString("HOST_NAME");

  protected int port = (new Integer( (new ClientResourceBundle()).getString("SERVLET_PORT"))).intValue();

  protected String cgi_dir = (new ClientResourceBundle()).getString("SERVLET_DIR");

  protected Hashtable dataSend = null;

  protected URLConnection urlC = null;
  protected HttpURLConnection httpsUrlC = null;

  protected boolean gotCgi = false;

  protected CmisApp parent = null;

  protected StatusDlg sdlg = null;

  public TcmisHttpConnection() {
  }

  public TcmisHttpConnection(int c, CmisApp parent) {
    this.cgi = null;
    this.cgi_name = c;
    this.parent = parent;
    parent.registerConnetion(this);
  }

  public TcmisHttpConnection(String s, CmisApp parent) {
    this.cgi = s;
    this.cgi_name = -99;
    this.parent = parent;
    parent.registerConnetion(this);
  }

  /**
   * Method getResultHash.
   * Initiates a connection to the server and returns the Hashtable returned
   * by the server.
   *
   * @param ds is the Hashtable containing the data to send to the server.
   * @return Hashtable
   */
  public synchronized Hashtable getResultHash(Hashtable ds) {
    Hashtable resultHash = null;
    Thread progressBar = null;
    try {
      dataSend = ds;
      if (parent.getMain() != null) {
        progressBar =
            new Thread(new MoveProgressBar(parent.getMain()));
        progressBar.start();
      }

      if (buildConnection(null)) {
        resultHash = getData();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (progressBar != null) {
      progressBar.interrupt();
    }
    return resultHash;
  }

  /**
   * Method buildConnection.
   * Build a servlet URL, connect to the server and send the data.
   *
   * @param u is the URL to use. If NULL a URL is constructed from
   * ClientResourceBundle properties.
   * @return boolean <code>true</tt> if the data is sent successfully.
   */
  public boolean buildConnection(URL u) {
    try {
      gotCgi = false;

      URL url = null;
      if (u == null) {
        if (port == 80) {
          url = new URL("http", host, cgi_dir + getCgiToExec());
        } else if (port == 443) {
          url = new URL("https", host, cgi_dir + getCgiToExec());
        } else {
          url = new URL("http", host, port, cgi_dir + getCgiToExec());
        }
      } else {
        url = u;
      }

      System.out.println("------ build connection: " + url.getHost() + "-" + url.getProtocol() + "-" + url.getPort() + "--" + url.toExternalForm());

      urlC = url.openConnection();
      System.out.println("-------- GOT HERE: ");
      //10-27-03 urlC.setAllowUserInteraction(true);
      if (urlC instanceof HttpURLConnection) {
        httpsUrlC = (HttpURLConnection) urlC;

      }
      if (!sendObjectToServer(url, dataSend)) {
        return false;
      }

      return true;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Method getCgiToExec.
   * Returns the pathname for servlet URL. The servlet name is set in the
   * constuctor of this class.
   *
   * @return String URL for a servlet
   */
  protected String getCgiToExec() {
    if (gotCgi) {
      return cgi;
    }
    gotCgi = true;
    String pack = parent.getResourceBundle().getString("SERVLET_PACKAGE") + parent.getResourceBundle().getString("CLIENT_INITIALS");
    if (cgi != null && cgi.length() > 0 && cgi_name == -99) {
      cgi = pack + cgi;

      return cgi;
    }

    switch (cgi_name) {
      case CHECK_CLIENT:
        cgi = new String(pack + "CheckClient");
        break;
      case CHECK_LOGON:
        cgi = new String(pack + "CheckLogon");
        break;
      case CHECK_VERSION:
        cgi = new String(pack + "CheckVersion");
        break;
      case CATALOG:
        cgi = new String(pack + "Catalog");
        break;
      case REQUEST:
        cgi = new String(pack + "Request");
        break;
      case TRACK:
        cgi = new String(pack + "Track");
        break;
      case PERSONNEL:
        cgi = new String(pack + "PersonnelInfo");
        break;
      case INVENTORY:
        cgi = new String(pack + "Inventory");
        break;
      case USER_PROFILE:
        cgi = new String(pack + "UserProfile");
        break;
      case SEARCH_INFO:
        cgi = new String(pack + "SearchInfo");
        break;
      case CATALOG_TABLE:
        cgi = new String(pack + "CatalogTable");
        break;
      case NEW_CHEM:
        cgi = new String(pack + "NewChemical");
        break;
      case ADMIN:
        cgi = new String(pack + "Admin");
        break;
      case NEW_CHEM_TRACKING:
        cgi = new String(pack + "NewChemTrack");
        break;
      case REPORT:
        cgi = new String(pack + "Report");
        break;
      case PROXY:
        cgi = new String(pack + "Proxy");
        break;
      case MATERIAL_REQUEST:
        cgi = new String(pack + "MaterialRequest");
        break;
      case WASTE:
        cgi = new String(pack + "Waste");
        break;
      case WASTE_TEST:
        cgi = new String(pack + "Test");
        break;
      default:
    }

    return cgi;
  }

  /**
   * Method sendObjectToServer.
   * If the specified Object is not NULL then set the HTTP request method to
   * POST, then construct a Hashtable containing the host IP address, an
   * identifying TOKEN received from the CHECK_CLIENT servlet or the String
   * NONE if a TOKEN has not been received yet, and the specified data Object.
   * <p>
   * If the specified Object is NULL then set the HTTP request method to GET.
   * </p><p>
   * Then the constructed Hashtable is sent to the server and the return code
   * is interpreted to determine if the server requested user authentication.
   * If so the user identity is acquired and sent to the server with the data.
   * </p><p>
   * If the user identity is required for authentication, but cannot be acquired
   * then <code>false</code> is returned. Otherwise <code>true</code> is returned.
   *
   * @param url is target URL
   * @param o is the data Object to send
   * @return <code>false</code> if the user cannot be authenticated and
   * <code>true</code> otherwise.
   * @throws Exception
   */
  public boolean sendObjectToServer(URL url, Object o) throws Exception {

    Hashtable sendObj = null;
    String method = null;
    if (o != null) {
      method = new String("POST");
      sendObj = new Hashtable();
      sendObj.put("CLIENT_IP", new String(InetAddress.getLocalHost().getHostAddress()));
      sendObj.put("TOKEN", (parent.getToken() == null ? "NONE" : parent.getToken()));
      sendObj.put("OBJECT", o);
    } else {
      method = new String("GET");
    }

    try {
      sendRequest(sendObj, method);
      String auth = null;
      AuthDlg ad = null;

      while (httpsUrlC.getResponseCode() == HttpURLConnection.HTTP_PROXY_AUTH) {
        String realm = httpsUrlC.getHeaderField("Proxy-authenticate");
        auth = getIdentity(realm, "Proxy/Firewall Authorization");
        if (auth == null) {
          return false;
        }
        parent.setProxyAuth(auth);
        httpsUrlC.setRequestProperty("Authorization", auth);
        sendRequest(sendObj, method);
      }

      while (httpsUrlC.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
        String realm = httpsUrlC.getHeaderField("WWW-Authenticate");
        auth = getIdentity(realm, "Web Server Authorization");
        if (auth == null) {
          return false;
        }
        parent.setWwwAuth(auth);
        httpsUrlC.setRequestProperty("Authorization", auth);
        sendRequest(sendObj, method);
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

  }

  /**
   * Method getIdentity.
   * Creates a logon dialog and gets the user's login ID and password. The
   * login ID and password are then combined with a colon (:) separator and
   * BASE64 encoded for use in an HTTP Authorization header.
   *
   * @param realm is the realm ID returned by a server in the
   * Proxy-authenticate or WWW-Authenticate header fields.
   * @param target is a string describing which identity the user should provide.
   * @return A BASE64 encoded String
   */
  protected String getIdentity(String realm, String target) {
    String identity = null;
    AuthDlg ad = new AuthDlg(parent.getMain(), target, true);
    if (realm != null) {
      realm = realm.substring(realm.indexOf("\""));
      ad.setTitle(realm);
    }
    ad.setVisible(true);
    if (ad.getConfirmationFlag()) {
      if (ad.isSavePassword()) {
        parent.setSaveProxyPass(true);
      }
      sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
      identity =
          enc.encodeBuffer(
          (ad.getUser().trim() + ':' + ad.getPass().trim())
          .getBytes());

    }
    return identity;
  }

  /**
   * Method sendRequest.
   * If POST is specified then marshall the Object parameter and send it
   * through the URLConnection. If GET is specified then just connect to the
   * URLConnection.
   *
   * @param sendObj is the Object to send to the server
   * @param method is the HTTP connection method (GET/POST) to use
   * @throws ProtocolException
   * @throws IOException
   */
  protected void sendRequest(Object sendObj, String method) throws ProtocolException, IOException {
    if (method.equalsIgnoreCase("POST")) {
      httpsUrlC.setRequestMethod("POST");
      httpsUrlC.setDoInput(true);
      httpsUrlC.setDoOutput(true);
      httpsUrlC.setUseCaches(false);
      httpsUrlC.setRequestProperty("Content-Type", "application/octet-stream");
      httpsUrlC.setRequestProperty("Logon-Version", parent.getLogonVersion());

      // serialize and write the data
      OutputStream os = httpsUrlC.getOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(os);
      oos.writeObject(sendObj);
      oos.flush();
      oos.close();
    } else {
      httpsUrlC.setRequestMethod("GET");
    }

    httpsUrlC.connect();
  }

  /**
   * Method getData.
   * Unmarshall the server data into a Hashtable and return it.
   *
   * @return Hashtable
   * @throws Exception
   */
  protected Hashtable getData() throws Exception {

    Hashtable resultHash = null;

    //int len = Integer.parseInt(httpsUrlC.getHeaderField("Content-Length"));
    //System.out.println("Received " + len + " bytes from server.");

    ObjectInputStream objIn =
        new ObjectInputStream(httpsUrlC.getInputStream());
    Object oo = new Object();
    oo = objIn.readObject();

    if (oo instanceof java.util.Hashtable) {
      resultHash = (Hashtable) oo;

    }
    return resultHash;
  }

  public void registerObject(StatusDlg obj) {
    this.sdlg = obj;
  }

}

class MoveProgressBar implements Runnable {
  public final static int MIN = 0;
  public final static int MAX = 100;

  int progress = MIN;

  private Main main;

  public MoveProgressBar(Main main) {
    this.main = main;
    synchronized (main.progBar) {
      main.progBar.setMinimum(MIN);
      main.progBar.setMaximum(MAX);
    }
  }

  public void run() {
    int p = 0;
    main.startImage();
    CursorChange.setCursor(main, Cursor.WAIT_CURSOR);
    try {
      while (p < MAX) {
        if (p >= 0) {
          synchronized (main.progBar) {
            main.progBar.setValue(progress);
          }
        }
        synchronized (this) {
          p = (progress < 90) ? progress++ : 90;
          wait( (long) 200);
        }
      }
    } catch (InterruptedException ignore) {
    }
    synchronized (main.progBar) {
      main.progBar.setValue(MAX);
    }
    CursorChange.setCursor(main, Cursor.DEFAULT_CURSOR);
    main.stopImage();
  }
}
