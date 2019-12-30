package radian.web.servlets.kemfast;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KemfastClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Kemfast";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new KemfastServerResourceBundle();
   }
}