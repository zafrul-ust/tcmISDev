package radian.web.servlets.boeing;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Boeing";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BoeingServerResourceBundle();
   }
}