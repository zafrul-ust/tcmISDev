package com.tcmis.client.launchgui.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TcmisPrereleaseVersionBean <br>
 * @version: 1.0, Apr 2, 2007 <br>
 *****************************************************************************/


public class TcmisPrereleaseVersionBean extends BaseDataBean {

	private String ipAddress;
	private String username;
	private String version;
	private String restart;
	private String host;
	private String path;
        private String jarFilename;
        private String classPath;
        private String sourceDatabaseProfile;


	//constructor
	public TcmisPrereleaseVersionBean() {
	}

	//setters
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setRestart(String restart) {
		this.restart = restart;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPath(String path) {
		this.path = path;
	}
        public void setJarFilename(String jarFilename) {
          this.jarFilename = jarFilename;
        }
        public void setClassPath(String classPath) {
          this.classPath = classPath;
        }
        public void setSourceDatabaseProfile(String sourceDatabaseProfile) {
          this.sourceDatabaseProfile = sourceDatabaseProfile;
        }

	//getters
	public String getIpAddress() {
		return ipAddress;
	}
	public String getUsername() {
		return username;
	}
	public String getVersion() {
		return version;
	}
	public String getRestart() {
		return restart;
	}
	public String getHost() {
		return host;
	}
	public String getPath() {
		return path;
	}
        public String getJarFilename() {
          return jarFilename;
        }
        public String getClassPath() {
          return classPath;
        }
        public String getSourceDatabaseProfile() {
          return sourceDatabaseProfile;
        }
}