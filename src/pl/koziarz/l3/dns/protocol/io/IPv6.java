/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol.io;

import java.util.Arrays;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class IPv6 {
	private byte[] ip;
	
	public IPv6(byte[] addr, int len ) {
		if( len>addr.length )
			throw new IllegalArgumentException("There's more bytes to read ("+len+") than available in array("+addr.length+")");
		ip = new byte[16];
		for( int i=0; i<len; ++i ) {
			ip[i]=addr[i];
		}
	}
	
	public IPv6(byte[] addr) {
		ip = new byte[16];
		for( int i=0; i<ip.length && i<addr.length; ++i ) {
			ip[i]=addr[i];
		}
	}
	
	public byte[] getBytes() {
		return ip;
	}
	
	@Override
	public boolean equals(Object o ) {
		if( o == null )
			return false;
		if( o instanceof IPv6 ) {
			IPv6 c = (IPv6)o;
			for( int i=0; i<ip.length; ++i ) {
				if( c.ip[i] != ip[i] ) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + Arrays.hashCode(this.ip);
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( int i=0; i<16; i+=2 ) {
			sb.append(String.format("%02X%02X", ip[i], ip[i+1] ));
			if( i<i-2 )
				sb.append(":");
		}
		
		return sb.toString();
	}
}
