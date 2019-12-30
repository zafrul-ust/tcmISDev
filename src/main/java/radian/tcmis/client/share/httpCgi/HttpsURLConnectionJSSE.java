package radian.tcmis.client.share.httpCgi;

import java.io.*;
import java.net.*;
import java.util.*;
//6-30-02
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.*;
import crysec.SSL.SSLParams;
import java.net.URLConnection;



public class HttpsURLConnectionJSSE extends URLConnection {


  protected Vector keys;
  protected Hashtable headers;
  protected String reqMethod="POST ";
  protected Object sendObject=null;

  protected boolean usingProxy;
  protected String proxyName;
  protected int proxyPort;
  protected String proxyLogon = "";
  protected boolean proxyAuth = false;
  protected String wwwLogon = "";
  protected boolean wwwAuth = false;

  protected int rCode= -1;

  protected SSLSocket s = null; //ssl socket
  //protected Socket s = null;  //clear socket
  protected OutputStream out = null;
  protected InputStream in = null;

  protected SSLParams params = null;

  protected byte[] recvByte = null;

  protected int updatedCount = 0;
  protected int updatedLen = 0;
  protected boolean connected = false;
  protected boolean sentReq = false;

  //6-30-02
  String tunnelHost = "www.tcmis.com";
  int tunnelPort = 443;

  Socket tunnel;

  protected StatusDlg sdlg=null;

  protected String logonVersion = "";
  public void setLogonVersion(String s) {
    logonVersion = s;
  }

  public HttpsURLConnectionJSSE (URL url){
      super(url);
      keys = new Vector();
      headers = new Hashtable();

      this.params = new SSLParams();
      /*
      short cs[] = new short[1];
      cs[0] = (short)Integer.parseInt("3", 16);
      params.setClientCipherSuites(cs);
      */
     short[] cs = new short[]{
       SSLParams.SSL_RSA_WITH_NULL_MD5,
       SSLParams.SSL_RSA_WITH_NULL_SHA,
       SSLParams.SSL_RSA_EXPORT_WITH_RC4_40_MD5,
       SSLParams.SSL_RSA_WITH_RC4_128_MD5,
       SSLParams.SSL_RSA_WITH_RC4_128_SHA,
       SSLParams.SSL_RSA_EXPORT_WITH_DES_40_CBC_SHA,
       SSLParams.SSL_RSA_WITH_DES_CBC_SHA,
       SSLParams.SSL_RSA_WITH_3DES_EDE_CBC_SHA,
       SSLParams.SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA,
       SSLParams.SSL_DHE_DSS_WITH_DES_CBC_SHA,
       SSLParams.SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA,
       SSLParams.SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA,
       SSLParams.SSL_DHE_RSA_WITH_DES_CBC_SHA,
       SSLParams.SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
       SSLParams.SSL_DH_anon_EXPORT_WITH_RC4_40_MD5,
       SSLParams.SSL_DH_anon_WITH_RC4_128_MD5,
       SSLParams.SSL_DH_anon_EXPORT_WITH_DES_40_CBC_SHA,
       SSLParams.SSL_DH_anon_WITH_DES_CBC_SHA,
       SSLParams.SSL_DH_anon_WITH_3DES_EDE_CBC_SHA
     };
     params.setClientCipherSuites(cs);

  }

  public void connect() throws IOException {
        headers = new Hashtable();
        System.out.println("Going connection httpsurlconnectionjsse");
        rCode=-1;
        int port = (url.getPort()==-1)?443:url.getPort();
        //if (usingProxy){
        if (true){
          System.out.println("Using proxy only:"+url.getHost());
          try {
            /*
	          * Let's setup the SSLContext first, as there's a lot of
	          * computations to be done.  If the socket were created
	          * before the SSLContext, the server/proxy might timeout
	          * waiting for the client to actually send something.
	          */
	          SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();

	          /*
	          * Set up a socket to do tunneling through the proxy.
	          * Start it off as a regular socket, then layer SSL
	          * over the top of it.
	          */
                 //System.out.println("--------- here: "+ System.getProperty("https.proxyHost")+"-"+System.getProperty("https.proxyPort"));
	           //tunnelHost = System.getProperty("https.proxyHost");
	           //tunnelPort = Integer.getInteger(System.getProperty("https.proxyPort")).intValue();

	          tunnel = new Socket(tunnelHost, tunnelPort);
	          doTunnelHandshake(tunnel, url.getHost(), port);

	          /*
	          * Ok, let's overlay the tunnel socket with SSL.
	          */
            s = (SSLSocket)factory.createSocket(tunnel, url.getHost(), port, true);


	          /*
	          * register a callback for handshaking completion event
	          */
	          s.addHandshakeCompletedListener(new HandshakeCompletedListener() {
		          public void handshakeCompleted(HandshakeCompletedEvent event) {
			          System.out.println("Handshake finished!");
			          System.out.println("\t CipherSuite:" + event.getCipherSuite());
			          System.out.println("\t SessionId " + event.getSession());
			          System.out.println("\t PeerHost " + event.getSession().getPeerHost());
		          }
		        }
            );

	          /*
	          * send http request
	          *
	          * See SSLSocketClient.java for more information about why
	          * there is a forced handshake here when using PrintWriters.
	          */
	          s.startHandshake();

            connected = true;
          } catch (IOException e){
            if (e.getMessage().indexOf((new Integer(HttpURLConnection.HTTP_PROXY_AUTH)).toString())>-1){
              rCode = HttpURLConnection.HTTP_PROXY_AUTH;
              return;
            } else {
              e.printStackTrace();
              throw e;
            }
          }
        } else {
            //System.out.println("not using proxy");
            //s =  new SSLSocket(url.getHost(), port, params);
            connected = true;
            //s =  new Socket(url.getHost(), 80); //, params);
        }

        //System.out.println("Sending request")

        connected = false;
        sentReq = true;

        sendRequest();

        connected = false;
        sentReq = false;

        //System.out.println("Loading Input stream");
        in = getInputStream();
        //clean rCode, but it can change on readHeaders

        //System.out.println("----- is my socket got a time out: "+s.getSoTimeout());

        rCode=HttpURLConnection.HTTP_OK;

        readHeaders(in);
  }

  /*
  * Tell our tunnel where we want to CONNECT, and look for the
  * right reply.  Throw IOException if anything goes wrong.
  */
  private void doTunnelHandshake(Socket tunnel, String host, int port) throws IOException {
	    OutputStream out = tunnel.getOutputStream();
	    String msg = "CONNECT " + host + ":" + port + " HTTP/1.0\n"
		     + "User-Agent: "
		     + sun.net.www.protocol.http.HttpURLConnection.userAgent
		     + "\r\n\r\n";
	    byte b[];
	    try {
	      /*
	      * We really do want ASCII7 -- the http protocol doesn't change
	      * with locale.
	      */
	      b = msg.getBytes("ASCII7");
	    } catch (UnsupportedEncodingException ignored) {
	      /*
	      * If ASCII7 isn't there, something serious is wrong, but
	      * Paranoia Is Good (tm)
	      */
	      b = msg.getBytes();
	    }
	    out.write(b);
	    out.flush();

	    /*
	    * We need to store the reply so we can create a detailed
	    * error message to the user.
	    */
	    byte		reply[] = new byte[200];
	    int		replyLen = 0;
	    int		newlinesSeen = 0;
	    boolean		headerDone = false;	/* Done on first newline */

	    InputStream	in = tunnel.getInputStream();
	    boolean		error = false;

	    while (newlinesSeen < 2) {
	      int i = in.read();
	      if (i < 0) {
		      throw new IOException("Unexpected EOF from proxy");
	      }
	      if (i == '\n') {
		      headerDone = true;
		      ++newlinesSeen;
        } else if (i != '\r') {
		      newlinesSeen = 0;
		      if (!headerDone && replyLen < reply.length) {
		        reply[replyLen++] = (byte) i;
		      }
	      }
	    }

	    /*
	    * Converting the byte array to a string is slightly wasteful
	    * in the case where the connection was successful, but it's
	    * insignificant compared to the network overhead.
	    */
	    String replyStr;
	    try {
	      replyStr = new String(reply, 0, replyLen, "ASCII7");
	    } catch (UnsupportedEncodingException ignored) {
	      replyStr = new String(reply, 0, replyLen);
	    }

	    /* We asked for HTTP/1.0, so we should get that back */
	    if (!replyStr.startsWith("HTTP/1.0 200")) {
	      throw new IOException("Unable to tunnel through "
		      + tunnelHost + ":" + tunnelPort
		      + ".  Proxy returns \"" + replyStr + "\"");
	    }
    /* tunneling Handshake was successful! */
  }

  protected void sendRequest() throws IOException {
       String file;
       OutputStream os = getOutputStream();

       String request = reqMethod+" "+url.getFile().trim()+" HTTP/1.0";
       String crlf =   "\r\n";
       String auth =   "Authorization: Basic "+ wwwLogon.trim();
       String ctype =  "Content-Type: application/octet-stream";
       String len =    "Content-Length: ";
       String uag =    "User-Agent: tcmIS";
       String ver =    "Logon-Version: "+logonVersion;      //new test

       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(bos);
       int length = 0;

       if (sendObject!=null){
          oos.writeObject(sendObject);
          oos.flush();
          oos.close();
          length = bos.size();
       } else {
          length = 0;
       }
       String clen = len + length;

       // Write the POST Header
       os.write(request.getBytes());os.write(crlf.getBytes());
       os.write(ctype.getBytes());os.write(crlf.getBytes());
       os.write(clen.getBytes());os.write(crlf.getBytes());
       os.write(uag.getBytes());os.write(crlf.getBytes());
       os.write(ver.getBytes());os.write(crlf.getBytes());     //new test
       if (wwwAuth) os.write(auth.getBytes());os.write(crlf.getBytes());
       //os.write(crlf.getBytes());
       //System.out.println(request);
       //System.out.println(ctype);
      // System.out.println(clen);
       /*
       System.out.println("----- Starting POST -----");
       ByteArrayOutputStream bosTest = new ByteArrayOutputStream();
       ObjectOutputStream oosTest = new ObjectOutputStream(bosTest);
       if (sendObject!=null){
          oosTest.writeObject(sendObject);
          oosTest.flush();
          oosTest.close();
       }
       bosTest.writeTo(System.out);
       System.out.println("----- End POST -----");
       */
       // Write the POST body
       if (length>0) bos.writeTo(os); //os.write(crlf.getBytes());
       //os.write(crlf.getBytes());
       bos.close();
       os.flush();
       //os.close();
  }

  protected void readHeaders(InputStream is) throws IOException {
    keys = new Vector();
    headers = new Hashtable();

    String line;
    boolean headerDone=false;
    boolean headerCheck=true;
    int len = 0;
    byte[] b = null;
    int count = 0;
    int c=0;
    int j=0;
    try {
    while(true){
        while (true){
          line = "";
          while (true){
	          c = is.read();
            //System.out.println("----- inputStream read: "+c);
	          if (c == -1){
	            break;
            }
            if (headerDone) break;

	          if (c == '\n'){
	            if (!line.endsWith("\r")){
	              break;
	            } else  {
	              line = line.substring(0, line.length()-1);
                break;
              }
	          } else {
	            line += (char)c;
            }
          }
          if (c==-1) break;
          if (line.trim().equals("")||headerDone) break;

          int colon = line.indexOf(":");
          // check for unathorized
          if (line.indexOf("HTTP/")==0){
             if (line.indexOf("401") > 0){
               //unathorized
               rCode = HttpURLConnection.HTTP_UNAUTHORIZED;
             }
          }
          if (colon>0){
             String key = line.substring(0,colon).trim();
             String header = line.substring(colon+1).trim();
             keys.addElement(key);
             headers.put(key.toLowerCase(),header);
          }

          //System.out.println("Line:"+line);
        }

        if (c==-1) break;


        if (!headerDone) {

          headerDone=true;

          if (c == '\n') continue;

        }


        if (headerCheck){

          if (rCode != HttpURLConnection.HTTP_OK) return;
          //System.out.println("********* Starting reading post data: Header: "+headers);
          len = Integer.parseInt(getHeaderField("Content-Length"));
          updatedLen=len;
          if(this.sdlg!=null) sdlg.setLen(len);
          //System.out.println("********* Starting reading post data: supposed"+len+" bytes");
          // read return
          b = new byte[len];
          count = 0;
          headerCheck = false;
        }
        //System.out.println("Char on count("+count+"):"+(char)c);
        b[count++] = (byte)c;

        // update bottom bar
        // count: number read
        // len: number to be read
        updatedCount=count;
        if(getSdlg()!=null && (count%1000==0) ) {
            getSdlg().setCount(count);
        }
        /*
        if ((count%5000)==0){
          System.out.println("Updated count is 0 :"+this.getUpdatedCount());
        }
        */

        if (count > len-1) {
          break;
        }
    }

    }catch(Throwable e) {
      e.printStackTrace();
    }


    //System.out.println("********* Read "+count+" of "+len);
    recvByte = b;
    //System.out.println("********* headers:"+headers);
  }


  //6-30-02
  public InputStream getInputStream() throws IOException {
    if (in==null) in = s.getInputStream();
    return in;
  }

  public OutputStream getOutputStream() throws IOException {
    if (out==null) out = s.getOutputStream();
    return out;
  }
  /*
  public InputStream getInputStream() throws IOException {
     if (usingProxy){
          if (in==null) in = st.getInputStream();
     } else {
          if (in==null) in = s.getInputStream();
     }
     return in;
  }

  public OutputStream getOutputStream() throws IOException {
      if (usingProxy){
          if (out==null) out = st.getOutputStream();
     } else {
          if (out==null) out = s.getOutputStream();
     }
     return out;
  }*/

  public String getHeaderFieldKey(int n){
     if (n<keys.size()){
        return (String) keys.elementAt(n);
     } else {
        return null;
     }

  }

  public String getHeaderField(int n){
     if (n<keys.size()){
        return getHeaderField((String) keys.elementAt(n));
     } else {
        return null;
     }
  }

  public String getHeaderField(String  n){
        return (String) headers.get(n.toLowerCase());
  }

  public void setUsingProxy(boolean p){
        usingProxy = p;

  }

  public void setProxyName(String p){
        proxyName = p;
  }

  public void setProxyPort(int p){
        proxyPort = p;
  }

  public void setProxyLogon(String p){
        proxyLogon = p;
  }

  public void setProxyAuth(boolean p){
        proxyAuth = p;
  }

  public void setWWWLogon(String p){
        wwwLogon = p;
  }

  public void setWWWAuth(boolean p){
        wwwAuth = p;
  }

  public int getResponseCode(){
        return rCode;
  }

  public void setRequestMethod(String p){
        reqMethod = p;
  }

  public void setSendObject(Object p){
       //System.out.println("Setting obj:"+p);
       sendObject = p;
  }

  public void disconnect() throws IOException {
    s.close();
    tunnel.close();
  }
  /*
  public void disconnect() throws IOException {
      //System.out.println("Ran disconnect");
      if (usingProxy){
          st.close();
      } else {
          s.close();
      }
  }  */

  public byte[] getRecvByte(){
     return recvByte;
  }

  public int getUpdatedCount(){
    return updatedCount;
  }

  public int getUpdatedLen(){
    return updatedLen;
  }

  public boolean isConnected(){
    return connected;
  }

  public boolean isSentReq(){
    return sentReq;
  }

  public void registerObject(StatusDlg obj){
    this.sdlg = obj;
  }

  protected StatusDlg getSdlg(){
    return sdlg;
  }

}
