/**
 * 
 */
package de.mbrero.see.persistance;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import org.hibernate.Session;
import org.junit.Test;

/**
 * @author massi
 *
 */
public class DBConnectionTest 
{

	/**
	 * Test method for {@link de.mbrero.see.persistance.DBConnection#getNewSession()}.
	 */
	@Test
	public void testGetNewSessionStandardPath() {
		DBConnection conn = new DBConnection();
		Session session = conn.getNewSession();
		
		assertTrue(session instanceof Session);
	}
	
	@Test
	public void testGetNewSessionWithCustomFile() throws IOException {

		URL url = getClass().getClassLoader().getResource("hibernate.cfg.xml");
		DBConnection conn = new DBConnection(url);
		Session session = conn.getNewSession();
		
		assertTrue(session instanceof Session);
	}

}
