package radian.web.servlets.optimetal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OptimetalBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Optimetal";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OptimetalServerResourceBundle();
   }
}