package radian.web.servlets.gkn;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GKNBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "GKN";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GKNServerResourceBundle();
   }
}