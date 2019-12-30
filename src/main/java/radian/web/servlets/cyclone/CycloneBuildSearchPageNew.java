package radian.web.servlets.cyclone;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CycloneBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Cyclone";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CycloneServerResourceBundle();
   }
}