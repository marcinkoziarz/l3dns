package pl.koziarz.l3.dns;

import java.util.logging.Level;
import java.util.logging.Logger;
import pl.koziarz.l3.dns.authserver.datasource.DNSSqliteDataSource;
import pl.koziarz.l3.dns.authserver.DNSDataSource;
import pl.koziarz.l3.dns.authserver.DNSAuthoritativeServer;
import pl.koziarz.l3.dns.protocol.DNSException;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class L3dns {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			DNSDataSource dnssrc = new DNSSqliteDataSource("l3dns.sqlite");
			DNSAuthoritativeServer serv = new DNSAuthoritativeServer(dnssrc, 5353, 4);
			serv.startServer();
		} catch (DNSException ex) {
			Logger.getLogger(L3dns.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
}
