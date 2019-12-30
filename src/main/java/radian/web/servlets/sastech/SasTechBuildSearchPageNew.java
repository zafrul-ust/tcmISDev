package radian.web.servlets.sastech;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SasTechBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "SAS_TECH";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SasTechServerResourceBundle();
   }
}