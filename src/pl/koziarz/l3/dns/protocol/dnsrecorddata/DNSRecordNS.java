/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol.dnsrecorddata;

import pl.koziarz.l3.dns.protocol.DNSRecordClass;
import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.protocol.DNSRecordType;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSRecordNS extends DNSRecord {
	private String nameserver;

	public DNSRecordNS(String nameserver, String name, DNSRecordClass cls, int ttl) {
		super(name, DNSRecordType.NS, cls, ttl);
		this.nameserver = nameserver;
	}

	public String getNameServer() {
		return nameserver;
	}

	@Override
	public String toString() {
		return getName()+" NS: "+nameserver;
	}
	
	

}
