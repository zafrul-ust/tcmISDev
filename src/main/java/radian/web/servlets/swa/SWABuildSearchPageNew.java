package radian.web.servlets.swa;

import radian.tcmis.server7.share.helpers.*;

public class SWABuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "SWA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SWAServerResourceBundle();
   }
}