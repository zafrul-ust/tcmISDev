package radian.web.servlets.dd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DDBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "DD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DDServerResourceBundle();
   }
}