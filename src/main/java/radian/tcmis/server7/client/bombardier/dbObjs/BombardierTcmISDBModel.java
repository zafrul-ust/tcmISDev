package radian.tcmis.server7.client.bombardier.dbObjs;

	import radian.tcmis.server7.share.dbObjs.*;
	import radian.tcmis.server7.share.helpers.*;
	import radian.tcmis.server7.client.bombardier.helpers.BombardierServerResourceBundle;

	public class BombardierTcmISDBModel  extends TcmISDBModel {
	   public BombardierTcmISDBModel(){
	      this("BOMBARDIER");
	   }

	   public BombardierTcmISDBModel(String client){
	      super(client);
	   }

	   public BombardierTcmISDBModel(String client,String logonVersion){
	      super(client,logonVersion);
	   }

	   public BombardierTcmISDBModel(String client,String logonVersion,String locale){
	      super(client,logonVersion,locale);
	   }

		protected ServerResourceBundle getBundle(){
	      return (ServerResourceBundle) new BombardierServerResourceBundle();
	   }

	}
