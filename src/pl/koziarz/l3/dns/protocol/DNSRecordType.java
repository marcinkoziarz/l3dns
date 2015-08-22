/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public enum DNSRecordType {
	A(1),
	AAAA(28),
	AFSDB(18),
	APL(42),
	CAA(257),
	CERT(37),
	CNAME(5),
	DHCID(49),
	DLV(32769),
	DNAME(39),
	DNSKEY(48),
	DS(43),
	HIP(55),
	IPSECKEY(45),
	KEY(25),
	KX(36),
	LOC(29),
	MX(15),
	NAPTR(35),
	NS(2),
	NSEC(47),
	NSEC3(50),
	NSEC3PARAM(51),
	PTR(12),
	RRSIG(46),
	RP(17),
	SIG(24),
	SOA(6),
	SPF(99),
	SRV(33),
	SSHFP(44),
	TA(32768),
	TKEY(249),
	TLSA(52),
	TSIG(250),
	TXT(16),
	
	ANY(255),
	HINFO(13),
	AXFR(252),
	IXFR(251),
	MAILB(253),
	MAILA(254),
	OPT(41)
	;

	private short value;
	
	DNSRecordType(int value) {
		this.value = (short)value;
	}
	
	public short getValue() {
		return value;
	}

	public static DNSRecordType valueOf(int value) throws DNSException {
		for( DNSRecordType d : DNSRecordType.values() ) {
			if( d.value == value )
				return d;
		}
		throw new DNSException("Unknown DNS Resource Record type "+value);
	}
}
