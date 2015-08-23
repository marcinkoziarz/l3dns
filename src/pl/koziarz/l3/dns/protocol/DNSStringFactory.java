package pl.koziarz.l3.dns.protocol;

import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSStringFactory {
	public static byte[] encode(String name) {
		// check last dot, eventually create space for it
		int len;
		if( name.charAt(name.length()-1) == '.') {
			len=name.length()+1;
		} else {
			len=name.length()+2;
		}
		byte[] ret = new byte[len];
		
		int prevdot = -1;
		int i;
		for( i=0; i<name.length(); ++i ) {
			if( name.codePointAt(i) == '.' ) {
				ret[prevdot+1] = (byte)(i-prevdot-1);
				prevdot=i;
			} else {
				ret[i+1]=(byte) name.charAt(i);
			}
		}
		// place last number
		ret[prevdot+1] = (byte)(i-prevdot-1);
		
		ret[ret.length-1]=0;
		
		return ret;
	}
	
	public static String decode(DataInputStream dis) throws DNSException, IOException {
		StringBuilder sb = new StringBuilder();

		int count;
		do {
			count=dis.readUnsignedByte();
			//System.out.println("DNS string count "+count);
			if( count<=0 ) {
				break;
			}
			if( count>= 192 ) {
				throw new DNSException("Pointers in strings are not yet supported");
				//int ptr = dis.readUnsignedByte();
				//System.out.println("_from_pointer_");
				//System.out.flush();
				//return "_from_pointer_";
				//ByteArrayDataInputStream badis = dis.clone();
				//badis.reset();
				//badis.skipBytes(ptr);
				//String str = decode(badis);
				//return sb.toString()+str;
			}
			for( int i=0; i<count; ++i ) {
				int a = dis.readUnsignedByte();
				sb.append((char)a);
			}
			sb.append('.');
		} while( count>0 );

		return sb.toString();

	}
}
