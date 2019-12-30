package radian.web.servlets.dana;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DanaBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Dana";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DanaServerResourceBundle();
   }
}