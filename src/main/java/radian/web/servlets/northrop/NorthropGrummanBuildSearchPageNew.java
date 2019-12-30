package radian.web.servlets.northrop;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NorthropGrummanBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "NORTHROP_GRUMMAN";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new NorthropGrummanServerResourceBundle();
   }
}