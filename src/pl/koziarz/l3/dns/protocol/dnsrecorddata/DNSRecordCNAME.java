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
public class DNSRecordCNAME extends DNSRecord {
	
	private String cname;

	public DNSRecordCNAME(String cname, String name, DNSRecordClass cls, int ttl) {
		super(name, DNSRecordType.CNAME, cls, ttl);
		this.cname = cname;
	}

	public String getCName() {
		return cname;
	}
	
	@Override
	public String toString() {
		return getName()+" CNAME "+cname;
	}
}
