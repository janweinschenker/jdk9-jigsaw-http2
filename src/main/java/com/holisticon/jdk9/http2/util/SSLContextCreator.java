package com.holisticon.jdk9.http2.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class SSLContextCreator {

	private static final Logger LOG = Logger
			.getLogger(SSLContextCreator.class.getName());

	private static SSLContext theContext = null;

	public static void main(String[] args) {
		getContext();
	}

	public static SSLContext getContext() {
		if (theContext == null) {
			theContext = createContext();
		}
		return theContext;
	}

	private static SSLContext createContext() {
		LOG.log(Level.INFO, "Testing socket factory with SSLContext:");
		try {
			// SSLContext protocols: TLS, SSL, SSLv3
			SSLContext sc = SSLContext.getInstance("SSLv3");
			LOG.log(Level.INFO, "SSLContext class: " + sc.getClass());
			LOG.log(Level.INFO, "   Protocol: " + sc.getProtocol());
			LOG.log(Level.INFO, "   Provider: " + sc.getProvider());

			// SSLContext algorithms: SunX509
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			LOG.log(Level.INFO, "KeyManagerFactory class: " + kmf.getClass());
			LOG.log(Level.INFO, "   Algorithm: " + kmf.getAlgorithm());
			LOG.log(Level.INFO, "   Provider: " + kmf.getProvider());

			// KeyStore types: JKS
			String ksName = "http2_demo";
			char ksPass[] = "pass4711".toCharArray();
			char ctPass[] = "pass4711".toCharArray();
			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(ksName), ksPass);
			LOG.log(Level.INFO, "KeyStore class: " + ks.getClass());
			LOG.log(Level.INFO, "   Type: " + ks.getType());
			LOG.log(Level.INFO, "   Provider: " + ks.getProvider());
			LOG.log(Level.INFO, "   Size: " + ks.size());

			// Generating KeyManager list
			kmf.init(ks, ctPass);
			KeyManager[] kmList = kmf.getKeyManagers();
			LOG.log(Level.INFO, "KeyManager class: " + kmList[0].getClass());
			LOG.log(Level.INFO, "   # of key manager: " + kmList.length);

			// Generating SSLServerSocketFactory
			sc.init(kmList, null, null);
			return sc;
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return null;
	}

}
