package radian.tcmis.server7.share.servlets;

import java.io.*;
import java.net.*;
import crysec.SSL.SSLSocket;
import crysec.SSL.SSLParams;

public class SSLURLConnection extends URLConnection {

  protected SSLSocket s = null; //ssl socket
  protected OutputStream out = null;
  protected InputStream in = null;
  protected SSLParams params = null;

  public SSLURLConnection (URL url){
      super(url);
      this.params = new SSLParams();
      //params.setDebug(true);
      short cs[] = new short[1];
      cs[0] = (short)Integer.parseInt("3", 16);
      params.setClientCipherSuites(cs);
  }

  public void connect() throws IOException {
        int port = (url.getPort()==-1)?443:url.getPort();
        s =  new SSLSocket(url.getHost(), port, params);
        in = getInputStream();
        out = getOutputStream();

  }

  public void sendData(String method, byte[] dBytes) throws IOException {
        String request = method+" "+url.getFile().trim()+" HTTP/1.0";
        String crlf =   "\r\n";

        // Write the POST Header
        if (method.equalsIgnoreCase("POST")){
           String ctype =  "Content-Type: application/octet-stream";
           String len =    "Content-Length: ";
           String uag =    "User-Agent: tcmIS";
           int length = dBytes.length;
           String clen = len + length;
           out.write(request.getBytes());out.write(crlf.getBytes());
           out.write(ctype.getBytes());out.write(crlf.getBytes());
           out.write(clen.getBytes());out.write(crlf.getBytes());
           out.write(uag.getBytes());out.write(crlf.getBytes());
           out.write(crlf.getBytes());
           //System.out.println(request);
           //System.out.println(ctype);
           //System.out.println(clen);
           //System.out.println(uag);
           // Write the POST body
           //System.out.println("*****  STARTING DATA *****");
           //System.out.println(new String(dBytes));
           //System.out.println("*****  END DATA *****");
           out.write(dBytes);
        } else {
           out.write(request.getBytes());
           out.write(crlf.getBytes());
           out.write(crlf.getBytes());
        }
        //
        //out.write(crlf.getBytes());
        out.flush();
        out.close();
  }

  public InputStream getInputStream() throws IOException {
     if (in==null) in = s.getInputStream();
     return in;
  }

  public OutputStream getOutputStream() throws IOException {
     if (out==null) out = s.getOutputStream();
     return out;
  }

  public void disconnect() throws IOException {
    s.close();
  }
}