package pl.koziarz.l3.dns.protocol.dnsrecorddata;

import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.protocol.DNSRecordClass;
import pl.koziarz.l3.dns.protocol.DNSRecordType;
import pl.koziarz.l3.dns.protocol.io.IPv4;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSRecordA extends DNSRecord {
	private IPv4 ip;

	public DNSRecordA(String name, DNSRecordClass cls, int ttl, IPv4 ip) {
		super(name, DNSRecordType.A, cls, ttl);
		this.ip = ip;
	}

	public IPv4 getIP() {
		return ip;
	}

	@Override
	public String toString() {
		return getName()+" A "+ip;
	}
	
	

}
