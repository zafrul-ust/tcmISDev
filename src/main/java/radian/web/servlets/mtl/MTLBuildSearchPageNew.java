package radian.web.servlets.mtl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MTLBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "MTL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MTLServerResourceBundle();
   }
}