package radian.web.servlets.maeet;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MAEETBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "MAEET";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MAEETServerResourceBundle();
   }
}