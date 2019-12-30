package radian.web.servlets.usgov;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class USGOVClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "USGOV";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new USGOVServerResourceBundle();
   }
}