package radian.web.servlets.aerocz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroczSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Aeroczsearchmsds check it is here....");
      return "Aerocz";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AeroczServerResourceBundle();
   }
}