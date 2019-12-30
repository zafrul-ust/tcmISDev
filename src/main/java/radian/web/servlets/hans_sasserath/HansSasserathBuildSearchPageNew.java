package radian.web.servlets.hans_sasserath;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HansSasserathBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "HansSasserath";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HansSasserathServerResourceBundle();
   }
}