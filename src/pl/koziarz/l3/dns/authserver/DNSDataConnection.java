/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.authserver;

import java.util.Collection;
import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.protocol.DNSRecordType;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public interface DNSDataConnection {
	public DNSRecord getSOA();
	public Collection<DNSRecord> getRecords(DNSRecordType type);
	public void close();
}
