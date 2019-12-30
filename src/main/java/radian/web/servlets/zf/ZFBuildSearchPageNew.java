package radian.web.servlets.zf;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZFBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "ZF";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ZFServerResourceBundle();
   }
}