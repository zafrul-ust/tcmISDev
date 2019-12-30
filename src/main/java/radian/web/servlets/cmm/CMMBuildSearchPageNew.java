package radian.web.servlets.cmm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CMMBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "CMM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CMMServerResourceBundle();
   }
}