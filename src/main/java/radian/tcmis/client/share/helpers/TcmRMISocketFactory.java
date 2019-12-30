
package radian.tcmis.client.share.helpers;

import java.rmi.server.*;
import java.net.*;
import java.io.*;



public class TcmRMISocketFactory extends RMISocketFactory {

  int port = 1099;

  public TcmRMISocketFactory(int p){
     super();
     port = p;
  }

  public Socket createSocket(String host, int p) throws IOException{
     return new Socket(host,p);
  }


  public ServerSocket createServerSocket(int p) throws IOException {
     if (p == 0)
        p = port;
     return new ServerSocket(p);

  }


}


























