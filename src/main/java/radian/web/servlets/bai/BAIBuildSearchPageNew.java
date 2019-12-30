package radian.web.servlets.bai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAIBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "BAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAIServerResourceBundle();
   }
}