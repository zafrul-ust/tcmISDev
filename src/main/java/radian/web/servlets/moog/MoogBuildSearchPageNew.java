package radian.web.servlets.moog;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MoogBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "MOOG";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MoogServerResourceBundle();
   }
}