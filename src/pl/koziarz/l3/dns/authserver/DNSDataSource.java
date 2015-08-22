/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.authserver;

import java.util.List;

import pl.koziarz.l3.dns.authserver.datasource.DNSDataSourceException;
import pl.koziarz.l3.dns.protocol.DNSQuestion;
import pl.koziarz.l3.dns.protocol.DNSRecord;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public interface DNSDataSource {
	public List<DNSRecord> getData(DNSQuestion q) throws DNSDataSourceException;
}
