package radian.web.servlets.ford;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FordBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Ford";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FordServerResourceBundle();
   }
}