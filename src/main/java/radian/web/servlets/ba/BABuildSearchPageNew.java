package radian.web.servlets.ba;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BABuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "BA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAServerResourceBundle();
   }
}