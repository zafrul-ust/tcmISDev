package radian.web.servlets.standardaero;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class StandardAeroBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "STANDARD_AERO";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new StandardAeroServerResourceBundle();
   }
}