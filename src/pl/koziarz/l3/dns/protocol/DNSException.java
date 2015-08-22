/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSException extends Exception {

	public DNSException() {
	}

	public DNSException(String message) {
		super(message);
	}

	public DNSException(Throwable cause) {
		super(cause);
	}

	public DNSException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
