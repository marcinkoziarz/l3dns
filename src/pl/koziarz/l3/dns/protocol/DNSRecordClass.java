/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public enum DNSRecordClass {
	UNKNOWN(0),
	IN(1),
	ANY(255)
	;
	
	DNSRecordClass(int value) {
		this.value=value;
	}
	
	private int value;
	
	public int getValue() {
		return value;
	}
	
	public static DNSRecordClass valueOf(int value) {
		for( DNSRecordClass d : DNSRecordClass.values() ) {
			if( d.value == value )
				return d;
		}
		return UNKNOWN;
	}
}
