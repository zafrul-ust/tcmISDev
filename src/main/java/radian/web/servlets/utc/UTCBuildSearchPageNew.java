package radian.web.servlets.utc;

import radian.tcmis.server7.share.helpers.*;

public class UTCBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "UTC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new UTCServerResourceBundle();
   }
}