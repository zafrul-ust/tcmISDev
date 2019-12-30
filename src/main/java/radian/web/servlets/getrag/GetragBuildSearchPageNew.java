package radian.web.servlets.getrag;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GetragBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Getrag";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GetragServerResourceBundle();
   }
}