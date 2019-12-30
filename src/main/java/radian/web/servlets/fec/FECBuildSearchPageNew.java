package radian.web.servlets.fec;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FECBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "FEC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FECServerResourceBundle();
   }
}