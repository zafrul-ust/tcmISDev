package radian.web.servlets.ablaero;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABLaeroBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "ABLaero";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ABLaeroServerResourceBundle();
   }
}