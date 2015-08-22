/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.authserver.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.koziarz.l3.dns.protocol.DNSException;
import pl.koziarz.l3.dns.protocol.DNSQuestion;
import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.authserver.DNSDataSource;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSJDBCDataSource implements DNSDataSource {

	private String host;
	private String user;
	private String pass;
	private Connection conn;
	
	public DNSJDBCDataSource(String host, String user, String pass) {
		this.host=host;
		this.user=user;
		this.pass=pass;
	}

	@Override
	public List<DNSRecord> getData(DNSQuestion q) {
		try {
			Connection c = getConnection();
			Statement st = c.createStatement();
			return Collections.emptyList();
		} catch (SQLException ex) {
			return Collections.emptyList();
		}
		
	}
	
	private Connection getConnection() throws SQLException {
		if( this.conn == null || this.conn.isClosed() ) {
			this.conn = DriverManager.getConnection(host, user, pass);
		}
		return this.conn;
	}
}
