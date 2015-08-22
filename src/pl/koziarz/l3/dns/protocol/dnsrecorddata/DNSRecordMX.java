/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol.dnsrecorddata;

import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.protocol.DNSRecordClass;
import pl.koziarz.l3.dns.protocol.DNSRecordType;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSRecordMX extends DNSRecord {
	private int preference;
	private String mxname;

	public DNSRecordMX(int preference, String mxname, String name, DNSRecordClass cls, int ttl) {
		super(name, DNSRecordType.MX, cls, ttl);
		this.preference = preference;
		this.mxname = mxname;
	}

	public int getPreference() {
		return preference;
	}

	public String getMxName() {
		return mxname;
	}

	@Override
	public String toString() {
		return getName()+" MX "+this.getCls()+" "+preference+" "+mxname;
	}
	
}
