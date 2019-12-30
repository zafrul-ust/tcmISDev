package radian.web.servlets.gd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GDBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "GD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GDServerResourceBundle();
   }
}