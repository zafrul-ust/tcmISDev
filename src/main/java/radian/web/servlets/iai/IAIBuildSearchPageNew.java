package radian.web.servlets.iai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IAIBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "IAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new IAIServerResourceBundle();
   }
}