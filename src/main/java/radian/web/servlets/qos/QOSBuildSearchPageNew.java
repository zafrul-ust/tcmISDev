package radian.web.servlets.qos;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class QOSBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
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