package radian.web.servlets.gm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GMBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "GM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GMServerResourceBundle();
   }
}