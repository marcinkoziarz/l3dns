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
public class DNSRecordSOA extends DNSRecord {
	
	private String mastername;
	private String responsiblename;
	private int serial;
	private int refresh;
	private int retry;
	private int expire;
	private int minimum;

	public DNSRecordSOA(String mastername, String responsiblename, int serial, int refresh, int retry, int expire, int minimum, String name, DNSRecordClass cls, int ttl) {
		super(name, DNSRecordType.SOA, cls, ttl);
		this.mastername = mastername;
		this.responsiblename = responsiblename;
		this.serial = serial;
		this.refresh = refresh;
		this.retry = retry;
		this.expire = expire;
		this.minimum = minimum;
	}

	public int getExpire() {
		return expire;
	}

	public String getMastername() {
		return mastername;
	}

	public int getMinimum() {
		return minimum;
	}

	public int getRefresh() {
		return refresh;
	}

	public String getResponsiblename() {
		return responsiblename;
	}

	public int getRetry() {
		return retry;
	}

	public int getSerial() {
		return serial;
	}

	@Override
	public String toString() {
		return getName()+" SOA "+mastername+" "+responsiblename+" "+serial+" "+refresh+" "+retry+" "+expire+" "+minimum;
	}
	
	
	
}
