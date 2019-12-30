/*
 * Created on May 5, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package radian.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.MissingResourceException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * The RootServlet is started first and performs all initializations used by other servlets.
 * This includes setting up the Log4J logger, the email subsystem, the property
 * Registry and the ThreadLocal registry.
 *
 * @author chuckls
 */
public class RootServlet extends HttpServlet {
	private static org.apache.log4j.Logger log;
	//private Registry registry;
	private ServletContext context;

	//static private Thread officeManagerThread;
	//static private OfficeManager officeManager;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

		context = config.getServletContext();

		try {
			//registry = new Registry();
			// save a reference for other objects in this thread
			//ThreadRegistry.get().put("/servlet", this);

			/*Utensils.loadConfigFiles(context, null);
			Utensils.loadTemplateFiles(context, null);*/
			//registry.set("/systemconfigfiles", "__LOADED__");

			// configure log4j
			/*Properties p=registry.getProperties( "//log4j",null,'.' );
			Logger.newInstance( p );
			log=org.apache.log4j.Logger.getLogger( getClass() );*/

			URL url=getClass().getClassLoader().getResource( "log4j.properties" );
			Logger.newInstance( url );
			log=org.apache.log4j.Logger.getLogger( getClass() );

			/*// load connection pools
			Utensils.loadConnectionPools();
			Utensils.loadObjectPools();

			// enable SSL
			Utensils.enableSSL();

			// configure email
			Utensils.init();*/

			//startOpenOffice();

			log.debug("RootServlet init complete");
			System.gc();

		} /*catch (MessagingException me) {
			log.fatal("Cannot configure email interface", me);
			throw new ServletException(
				"Servlet configuration error: cannot configure email interface",
				me);
		} */catch (MissingResourceException mre) {
			System.err.println("Cannot configure logging");
			throw new ServletException(
				"Servlet configuration error: cannot configure logging",
				mre);
		} catch (Exception e) {
			log.fatal("Configuration error", e);
			throw new ServletException("Servlet configuration error", e);
		}
    }

	public void destroy()
	{
		/*officeManager.terminate();
		synchronized(officeManagerThread) {
			officeManagerThread.interrupt();
		}
		int count = 50;
		while(officeManagerThread.isAlive() && --count > 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}*/
	}

	/*static public Process getOfficeProcess() {
		if( officeManager != null && officeManagerThread.isAlive() )
			return officeManager.getOfficeProcess();
		return null;
	}

	protected void startOpenOffice() throws UnknownHostException {
		officeManagerThread =
			new Thread(officeManager = new OfficeManager(), "OfficeManager");
		officeManagerThread.setDaemon(true);
		officeManagerThread.start();
	}*/

	/**
	 * This class is used to keep OpenOffice available. If OpenOffice exits it is
	 * restarted. If a Command object fails because OpenOffice failed to load or
	 * a connection to OpenOffice could not be made then OpenOffice is restarted.
	 *
	 * @author chuckls
	 */
	protected class OfficeManager implements Runnable {
		private Process officeProcess = null;
		private boolean runFlag = true;

		String[] env =
			new String[] {
				"HOSTNAME=" + InetAddress.getLocalHost().getHostName(),
				"USER=" + System.getProperty("user.name"),
				"LOGNAME=" + System.getProperty("user.name"),
				"HOME=" + System.getProperty("user.home"),
				"JAVA_HOME=" + System.getProperty("java.home"),
				"DISPLAY=:0" };

		public OfficeManager() throws UnknownHostException {
		}

		public void terminate() {
			this.runFlag = false;
		}

		public Process getOfficeProcess() {
			return officeProcess;
		}

		public void run() {
			while(runFlag) {
				try {
					// start office
					log.info("Starting OpenOffice");
					officeProcess =
						Runtime.getRuntime().exec(
							"/bin/sh "+System.getProperty("user.home")+"/OpenOffice.org1.0.2/program/soffice",
							env);
					log.info("Started OpenOffice");
					while(runFlag) {
						try {
							new Thread(
								new Runnable() {
									Thread threadToWakeUp = null;
									int nSecs;
									public Runnable interrupt(Thread toWakeUp, int numberOfSecs) {
										threadToWakeUp = toWakeUp;
										nSecs = numberOfSecs;
										return this;
									}
									public void run() {
										try {
											synchronized(this) {
												wait(1000 * nSecs);
											}
										} catch (InterruptedException ie) {
										}
										synchronized(threadToWakeUp) {
											threadToWakeUp.interrupt();
										}
									}
								}.interrupt(Thread.currentThread(), 60),
							"OfficeInterrupterThread").start();
							officeProcess.waitFor();
							// if the next instruction is executed it means -
							// the office process has exited and should be restarted
							break;
						} catch (InterruptedException e) {
						}
					}
				} catch (IOException e) {
					log.error("Unable to start/restart OpenOffice process", e);
				}
			}
			log.debug("OfficeManager terminating");
			officeProcess.destroy();
		}
	}

}
