package radian.web.servlets.ommc;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMMCBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "OMMC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OMMCServerResourceBundle();
   }
}