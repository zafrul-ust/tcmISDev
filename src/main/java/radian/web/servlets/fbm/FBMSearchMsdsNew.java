package radian.web.servlets.fbm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FBMSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ FBMsearchmsds check it is here....");
      return "FBM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FBMServerResourceBundle();
   }
}