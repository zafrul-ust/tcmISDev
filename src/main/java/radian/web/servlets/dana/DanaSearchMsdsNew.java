package radian.web.servlets.dana;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DanaSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
     return "Dana";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DanaServerResourceBundle();
   }
}