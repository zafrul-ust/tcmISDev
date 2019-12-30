package radian.web.servlets.boeingcomair;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingComairBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "BOEING_COMAIR";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BoeingComairServerResourceBundle();
   }
}