package radian.web.servlets.team;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TeamBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Team";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TeamServerResourceBundle();
   }
}