package radian.web.servlets.zwl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZWLBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "ZWL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ZWLServerResourceBundle();
   }
}