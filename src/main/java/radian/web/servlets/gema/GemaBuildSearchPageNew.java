package radian.web.servlets.gema;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GemaBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "GEMA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GemaServerResourceBundle();
   }
}