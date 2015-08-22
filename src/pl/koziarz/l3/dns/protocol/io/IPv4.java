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
public class IPv4 {
	private byte[] ip;
	
	public IPv4(String addr) {
		String[] parts = addr.split("\\.");
		if( parts.length != 4 ) {
			throw new IllegalArgumentException("Supplied IPv4 address has wrong format");
		}
		ip = new byte[4];
		for( int i=0; i<4; ++i ) {
			ip[i]=(byte) (Integer.parseInt(parts[i])&0xFF);
		}
	}
	
	public IPv4(int addr) {
		ip=new byte[4];
		ip[0]=(byte) ((addr>>24)&0xFF);
		ip[1]=(byte) ((addr>>16)&0xFF);
		ip[2]=(byte) ((addr>>8)&0xFF);
		ip[3]=(byte) ((addr>>0)&0xFF);
	}
	
	public IPv4(byte[] addr) {
		ip = new byte[4];
		for( int i=0; i<4; ++i )
			ip[i]=addr[i];
	}
	
	public IPv4(int a, int b, int c, int d) {
		if( (a|b|c|d) > 255 ) {
			throw new IllegalArgumentException("IP address parts bigger than 255");
		}
		ip = new byte[4];
		ip[0]=(byte) (a&0xFF);
		ip[1]=(byte) (b&0xFF);
		ip[2]=(byte) (c&0xFF);
		ip[3]=(byte) (d&0xFF);
		
	}
	
	public byte[] getBytes() {
		return ip;
	}
	
	@Override
	public boolean equals(Object o ) {
		if( o == null )
			return false;
		if( o instanceof IPv4 ) {
			IPv4 c = (IPv4)o;
			for( int i=0; i<4; ++i ) {
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
		return (ip[0]&0xFF)+"."+(ip[1]&0xFF)+"."+(ip[2]&0xFF)+"."+(ip[3]&0xFF);
	}
}
