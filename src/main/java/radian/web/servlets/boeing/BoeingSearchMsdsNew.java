package radian.web.servlets.boeing;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
     return "Boeing";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BoeingServerResourceBundle();
   }
}