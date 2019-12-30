package radian.web.servlets.zwl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZWLClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "ZWL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ZWLServerResourceBundle();
   }
}