/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.authserver.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.koziarz.l3.dns.protocol.DNSException;
import pl.koziarz.l3.dns.protocol.DNSQuestion;
import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.protocol.DNSRecordClass;
import pl.koziarz.l3.dns.protocol.DNSRecordType;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordA;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordSOA;
import pl.koziarz.l3.dns.protocol.io.IPv4;
import pl.koziarz.l3.dns.authserver.DNSDataSource;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSSqliteDataSource implements DNSDataSource {

	String dbname;
	Connection c;
	
	public DNSSqliteDataSource(String dbname) throws DNSException {
		this.dbname=dbname;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+dbname);
		} catch (ClassNotFoundException | SQLException ex) {
			//Logger.getLogger(DNSSqliteDataSource.class.getName()).log(Level.SEVERE, null, ex);
			throw new DNSException("Error while setting up DNS Data Source",ex);
		}
		
	}
	
	@Override
	public List<DNSRecord> getData(DNSQuestion q) throws DNSDataSourceException {
		
		// firstly, check if we're authoritary server of domain
		String query = q.name();
		
		DNSRecordSOA soa=null;
		int soaid=0;
		for( int i=-1; i<query.length()-1; ++i ) {
			if( i==-1 || query.charAt(i)=='.' ) {
				String curr = query.substring(i+1);
				//System.out.println("Checking for SOA "+curr);
				try {
					Statement st = c.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM soa WHERE name='"+curr+"'");
					if( rs.next() ) {
						
						soaid = rs.getInt("id");
						soa = new DNSRecordSOA(
							rs.getString("master"),
							rs.getString("responsible"),
							rs.getInt("serial"),
							rs.getInt("refresh"),
							rs.getInt("retry"),
							rs.getInt("expire"),
							rs.getInt("minimum"),
							rs.getString("name"),
							DNSRecordClass.IN,
							rs.getInt("ttl")
							);
						rs.close();
						st.close();
						break;
					}
					rs.close();
					st.close();
				} catch (SQLException ex) {
					throw new DNSDataSourceException(ex);
				}
			}
		}
		if( soa != null && soaid>0 ) {
			String subdomain="";
			if( soa.getName().length() < q.name().length()) {
				subdomain = q.name().substring(0,q.name().length()-soa.getName().length()-1);
			}
			System.out.println("Found SOA: "+soa.getName()+", subdomain: "+subdomain);
			try {
				// now, find record inside found zone
				Statement st = c.createStatement();
				System.out.println("Looking for records "+q.type().name()+" in zone "+soaid);
				ResultSet rs = st.executeQuery("SELECT r.*, s.name as origin FROM rr r, soa s WHERE r.name='"+subdomain+"' AND r.zone=s.id AND r.zone='"+soaid+"' AND type='"+q.type().name()+"';");
				ArrayList<DNSRecord> answers = new ArrayList<>();
				while( rs.next() ) {
					answers.add(buildRecord(subdomain,rs));
				}
				rs.close();
				st.close();
				// if there are no records, check for wildcard
				if( answers.isEmpty() ) {
					System.out.println("None answers found, trying wildcard");
					Statement stw = c.createStatement();
					//System.out.println("SELECT r.*, s.name as origin FROM rr r, soa s WHERE r.name='*' AND r.zone=s.id AND r.zone='"+soaid+"' AND type='"+q.type().name()+"';");
					ResultSet rsw = stw.executeQuery("SELECT r.*, s.name as origin FROM rr r, soa s WHERE r.name='*' AND r.zone=s.id AND r.zone='"+soaid+"' AND type='"+q.type().name()+"';");
					if( rsw.next() ) {
						answers.add(buildRecord(subdomain,rsw));
					}
					rs.close();
					st.close();
				}
				
				return answers;
			} catch (SQLException ex) {
				throw new DNSDataSourceException(ex);
			} catch (DNSException ex) {
				throw new DNSDataSourceException(ex);
			}
		} else {
			throw new DNSDataSourceException("SOA not found in local database.");
		}
	}
	
	private DNSRecord buildRecord(String subdomain, ResultSet rs) throws DNSException, SQLException {
		DNSRecordType type = DNSRecordType.valueOf(rs.getString("type"));
		switch(type) {
			case A:
				String name = subdomain;//rs.getString("name");
				String origin = rs.getString("origin");
				String recordname;
				if( name.length() == 0 ) {
					recordname=origin;
				} else {
					recordname=name+"."+origin;
				}
				
				return new DNSRecordA(
					recordname,
					DNSRecordClass.IN,
					rs.getInt("ttl"),
					new IPv4(rs.getString("data"))
					);
			default:
				throw new DNSException("Unimplemented record "+type+" in Sqlite Data Source");
		}
	}

}
