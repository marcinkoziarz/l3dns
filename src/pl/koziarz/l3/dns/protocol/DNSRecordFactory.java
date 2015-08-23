/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordA;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordAAAA;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordCNAME;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordMX;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordNS;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordSOA;
import pl.koziarz.l3.dns.protocol.io.IPv4;
import pl.koziarz.l3.dns.protocol.io.IPv6;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSRecordFactory {
	public static DNSRecord createRecord(DataInputStream dis) throws DNSException {
		//int begin=dis.position();
		try {
			String name = DNSStringFactory.decode(dis);
			int btype = dis.readUnsignedShort();
			DNSRecordType type = DNSRecordType.valueOf(btype);
			int bcls = dis.readUnsignedShort();
			DNSRecordClass cls = DNSRecordClass.valueOf(bcls);
			int ttl = dis.readInt();
			int len = dis.readUnsignedShort();

			switch(type) {
				case A:
					if( len!=4 )
						throw new DNSException("A type record need to have 4 bytes of data, got "+len);
					byte[] ip = new byte[4];
					dis.read(ip);
					return new DNSRecordA(name, cls, ttl, new IPv4(ip));
				case AAAA:
					byte[] ip6 = new byte[16];
					dis.read(ip6);
					IPv6 addr6 = new IPv6(ip6);
					return new DNSRecordAAAA(addr6, name, cls, ttl);
				case CNAME:
					String cname = DNSStringFactory.decode(dis);
					return new DNSRecordCNAME(cname, name, cls, ttl);
				case NS:
					String ns = DNSStringFactory.decode(dis);
					return new DNSRecordNS(ns, name, cls, ttl);
				case SOA:
					String mastername = DNSStringFactory.decode(dis);
					String responsiblename = DNSStringFactory.decode(dis);
					int serial = dis.readInt();
					int refresh = dis.readInt();
					int retry = dis.readInt();
					int expire = dis.readInt();
					int minimum = dis.readInt();
					return new DNSRecordSOA(mastername, responsiblename, serial, refresh, retry, expire, minimum, name, cls, ttl);
				case MX:
					int preference = dis.readUnsignedShort();
					String mxname = DNSStringFactory.decode(dis);
					return new DNSRecordMX(preference, mxname, name, cls, ttl);
				default:
					throw new DNSException("Unimplemented DNS Record Type "+type.name());
			}

		} catch (IOException ex) {
			throw new DNSException("Error while parsing record", ex);
		}
	}

	public static byte[] dumpRecord(DNSRecord r) throws DNSException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			
			dos.write(DNSStringFactory.encode(r.getName()));
			dos.writeShort(r.getType().getValue());
			dos.writeShort(r.getCls().getValue());
			dos.writeInt(r.getTtl());
			
			switch(r.getType()) {
				case A:
					dos.writeShort(4);
					DNSRecordA ra = (DNSRecordA)r;
					dos.write(ra.getIP().getBytes());
				break;
				case SOA:
					DNSRecordSOA rsoa = (DNSRecordSOA)r;
					dos.write(DNSStringFactory.encode(rsoa.getMastername()));
					dos.write(DNSStringFactory.encode(rsoa.getResponsiblename()));
					dos.writeInt(rsoa.getSerial());
					dos.writeInt(rsoa.getRefresh());
					dos.writeInt(rsoa.getRetry());
					dos.writeInt(rsoa.getExpire());
					dos.writeInt(rsoa.getMinimum());
				break;
				default:
					throw new DNSException("Can't output record of type "+r.getType());
			}
			
			dos.close();
			return baos.toByteArray();
		} catch (IOException ex) {
			throw new DNSException(ex);
			//Logger.getLogger(DNSMessageBuilder.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
