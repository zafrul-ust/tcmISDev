package radian.web.servlets.orlite;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OrliteSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Orlitesearchmsds check it is here....");
      return "Orlite";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OrliteServerResourceBundle();
   }
}