package radian.tcmis.server7.client.triumph.dbObjs;

	import radian.tcmis.server7.share.dbObjs.*;
	import radian.tcmis.server7.share.helpers.*;
	import radian.tcmis.server7.client.triumph.helpers.TriumphServerResourceBundle;

	public class TriumphTcmISDBModel  extends TcmISDBModel {
	   public TriumphTcmISDBModel(){
	      this("TRIUMPH");
	   }

	   public TriumphTcmISDBModel(String client){
	      super(client);
	   }

	   public TriumphTcmISDBModel(String client,String logonVersion){
	      super(client,logonVersion);
	   }

	   public TriumphTcmISDBModel(String client,String logonVersion,String locale){
	      super(client,logonVersion,locale);
	   }

		protected ServerResourceBundle getBundle(){
	      return (ServerResourceBundle) new TriumphServerResourceBundle();
	   }

	}
