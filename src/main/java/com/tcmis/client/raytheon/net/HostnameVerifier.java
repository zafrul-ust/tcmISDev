package com.tcmis.client.raytheon.net;

import javax.net.ssl.SSLSession;

/******************************************************************************
 * CLASSNAME: HostnameVerifier <br>
 * @version: 1.0, May 26, 2005 <br>
 * This class is used to get around the fact that Exostars host name does not
 * match their certificate.
 *****************************************************************************/

public class HostnameVerifier
    implements com.sun.net.ssl.HostnameVerifier, 
               javax.net.ssl.HostnameVerifier {
    //implements javax.net.ssl.HostnameVerifier {
  public HostnameVerifier() {
  }

  /*
   * This method will always return true, in effect disabling the ssl verification
  */
  public boolean verify(String urlHostname, SSLSession session) {
    //System.out.println("Verifying hostname:"  + session.getPeerHost());
    //it probably would be good to match the cert host name to the host name
    //it supposedly has: "machinelink2.exostar.com"
    return true;
  }

  /*
   * This method will always return true, in effect disabling the ssl verification
  */
  public boolean verify(String urlHostname, String certHostname) {
    return true;
  }

}