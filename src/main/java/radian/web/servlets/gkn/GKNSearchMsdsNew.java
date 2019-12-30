package radian.web.servlets.gkn;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GKNSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ GKNsearchmsds check it is here....");
      return "GKN";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GKNServerResourceBundle();
   }
}