package radian.web.servlets.sd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SDSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
     return "SD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SDServerResourceBundle();
   }
}