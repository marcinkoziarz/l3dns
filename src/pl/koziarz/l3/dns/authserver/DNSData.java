/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.authserver;

import java.util.Collection;
import pl.koziarz.l3.dns.protocol.DNSRecord;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSData {
	final private Collection<DNSRecord> answers;
	final private Collection<DNSRecord> authorities;
	final private Collection<DNSRecord> additionals;

	public DNSData(Collection<DNSRecord> answers, Collection<DNSRecord> authorities, Collection<DNSRecord> additionals) {
		this.answers = answers;
		this.authorities = authorities;
		this.additionals = additionals;
	}

	public Collection<DNSRecord> getAdditionals() {
		return additionals;
	}

	public Collection<DNSRecord> getAnswers() {
		return answers;
	}

	public Collection<DNSRecord> getAuthorities() {
		return authorities;
	}
	
	
}
