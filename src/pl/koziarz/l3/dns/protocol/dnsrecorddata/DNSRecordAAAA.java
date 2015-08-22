/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol.dnsrecorddata;

import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.protocol.DNSRecordClass;
import pl.koziarz.l3.dns.protocol.DNSRecordType;
import pl.koziarz.l3.dns.protocol.io.IPv6;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSRecordAAAA extends DNSRecord {
	private IPv6 ip;

	public DNSRecordAAAA(IPv6 ip, String name, DNSRecordClass cls, int ttl) {
		super(name, DNSRecordType.AAAA, cls, ttl);
		this.ip = ip;
	}

	public IPv6 getIP() {
		return ip;
	}

	@Override
	public String toString() {
		/*int a = (ip>>24)&0xFF;
		int b = (ip>>16)&0xFF;
		int c = (ip>>8)&0xFF;
		int d = (ip>>0)&0xFF;*/
		return "AAAA ip="+ip;//a+"."+b+"."+c+"."+d;
	}
	
	

}
