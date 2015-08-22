/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
abstract public class DNSRecord {
	
	private String name;
	private DNSRecordType type;
	private DNSRecordClass cls;
	private int ttl;

	public DNSRecord(String name, DNSRecordType type, DNSRecordClass cls, int ttl ) {
		this.name = name;
		this.type = type;
		this.cls = cls;
		this.ttl = ttl;
	}


	public String getName() {
		return name;
	}

	public DNSRecordClass getCls() {
		return cls;
	}

	public DNSRecordType getType() {
		return type;
	}

	public int getTtl() {
		return ttl;
	}
	
}
