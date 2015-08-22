/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSQuestion {
	private String question;
	private DNSRecordType type;
	private DNSRecordClass cls;

	public DNSQuestion(String question, DNSRecordType type, DNSRecordClass cls) {
		this.question = question;
		this.type = type;
		this.cls = cls;
	}
	
	public String name() {
		return question;
	}
	
	public DNSRecordType type() {
		return type;
	}
	
	public DNSRecordClass cls() {
		return cls;
	}

	@Override
	public String toString() {
		return "QUESTION "+type.name()+" "+cls.name()+" "+question;
	}

	
}
