package radian.web.servlets.baz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAZBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "BAZ";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAZServerResourceBundle();
   }
}