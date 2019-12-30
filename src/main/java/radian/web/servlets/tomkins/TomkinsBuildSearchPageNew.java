package radian.web.servlets.tomkins;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TomkinsBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "TOMKINS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TomkinsServerResourceBundle();
   }
}