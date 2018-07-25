package farrout.convenience.beans;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import java.util.ArrayList;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;


/**
 * Factory for encapsulating the registration of MBeans.
 *
 * Add 'BeanFactory.createMBean(this);' to the class implementing an MBean
 * interface.
 *
 * @author greg.farr
 *
 */
public class BeanFactory {

  private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(BeanFactory.class);

	public static final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

	private static ArrayList<ObjectName> register = new ArrayList<ObjectName>();

	/**
	 * Global static variables
	 */
	// public static final String packageName =
	// "com.dvsoft.nexus.service.jrc.ppc.genesysMultimedia.jmx.mbeans";

	private static ObjectName createObjectName(String packageName, String className)
			throws MalformedObjectNameException {
		try {
			return new ObjectName(packageName + ":type=" + className);
		} catch (MalformedObjectNameException e) {
			throw e;
		}
	}

	/**
	 * Create and register an MBean. *
	 *
	 * @param object
	 *            Instance of the object that the MBean will be attached to.
	 */
	public static void createMBean(Object object) {
		try {
			String className = object.getClass().getSimpleName();
			String packageName = object.getClass().getPackage().getName();
			ObjectName name = BeanFactory.createObjectName(packageName, className);
			register.add(name);
			LOGGER.info("Registering MBean: {0}", name.toString());
			BeanFactory.mBeanServer.registerMBean(object, name);
		} catch (InstanceAlreadyExistsException e) {
      LOGGER.warn("Error creating MBean. For debugging, you can ignore",e);
		} catch (MBeanRegistrationException e) {
			LOGGER.warn("Error creating MBean", e);
		} catch (NotCompliantMBeanException e) {
			LOGGER.warn("Error creating MBean", e);
		} catch (MalformedObjectNameException e) {
			LOGGER.warn("Error creating MBean", e);
		}
	}

	/**
	 * Unregisters all MBeans from server.
	 */
	public static void tearDown() {
		LOGGER.info("Unregistering {} MBean(s)", register.size());
		for (ObjectName object : register) {
			try {
				BeanFactory.mBeanServer.unregisterMBean(object);
			} catch (MBeanRegistrationException e) {
				LOGGER.warn("Error unregistering MBeans", e);
			} catch (InstanceNotFoundException e) {
				LOGGER.warn("Error unregistering MBeans", e);
			}
		}
		register.clear();
		LOGGER.info("Finished unregistering {} MBean(s)", register.size());
	}

}
