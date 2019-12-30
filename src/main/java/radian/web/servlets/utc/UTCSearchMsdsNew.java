package radian.web.servlets.utc;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class UTCSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ UTCsearchmsds check it is here....");
      return "UTC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new UTCServerResourceBundle();
   }
}