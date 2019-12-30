package radian.web.servlets.abm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABMBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "ABM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ABMServerResourceBundle();
   }
}