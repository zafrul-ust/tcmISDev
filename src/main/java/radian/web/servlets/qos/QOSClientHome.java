package radian.web.servlets.qos;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class QOSClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "QOS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new QOSServerResourceBundle();
   }
}