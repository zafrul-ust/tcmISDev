package radian.web.servlets.haargaz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HaargazBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Haargaz";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HaargazServerResourceBundle();
   }
}