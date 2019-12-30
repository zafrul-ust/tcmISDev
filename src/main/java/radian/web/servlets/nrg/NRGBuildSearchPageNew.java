package radian.web.servlets.nrg;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NRGBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "NRG";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new NRGServerResourceBundle();
   }
}