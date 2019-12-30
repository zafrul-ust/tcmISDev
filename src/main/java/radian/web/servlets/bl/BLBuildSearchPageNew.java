package radian.web.servlets.bl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BLBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "BL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BLServerResourceBundle();
   }
}