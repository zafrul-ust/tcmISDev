package radian.web.servlets.orlite;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OrliteBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Orlite";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OrliteServerResourceBundle();
   }
}