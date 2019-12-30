package radian.web.servlets.fbm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FBMClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "FBM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FBMServerResourceBundle();
   }
}