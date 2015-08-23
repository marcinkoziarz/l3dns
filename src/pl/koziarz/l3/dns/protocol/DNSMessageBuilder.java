package pl.koziarz.l3.dns.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSMessageBuilder {
	
	public static DNSMessage build(byte[] data) throws DNSException {
		return DNSMessageBuilder.build(data, 0, data.length);
	}

	public static DNSMessage build(byte[] data, int length) throws DNSException {
		return DNSMessageBuilder.build(data, 0, length);
	}
	
	public static DNSMessage build(byte[] data, int offset, int length) throws DNSException {
		try {
			//DNSMessage a = DNSMessage.decode(dis);
			DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data, offset, length));
			// decode header
			int id = dis.readUnsignedShort();
			int flags = dis.readUnsignedShort();
			int question_count = dis.readUnsignedShort();
			int answer_count = dis.readUnsignedShort();
			int authority_count = dis.readUnsignedShort();
			int additional_count = dis.readUnsignedShort();

		// parse questions
			DNSQuestion[] questions = new DNSQuestion[question_count];
			for( int i=0; i<question_count; ++i ) {
				System.out.println("Parsing Question "+i+" out of "+question_count);
				String name = DNSStringFactory.decode(dis);
				int type=dis.readUnsignedShort();
				DNSRecordType qtype = DNSRecordType.valueOf(type);
				int cls = dis.readUnsignedShort();
				DNSRecordClass qclass = DNSRecordClass.valueOf(cls);				
				questions[i] = new DNSQuestion(name, qtype, qclass);
			}
		// parse answers
			DNSRecord[] answers = new DNSRecord[answer_count];
			for( int i=0; i<answer_count; ++i ) {
				System.out.println("Parsing Answer "+i+" out of "+answer_count);
				answers[i] = DNSRecordFactory.createRecord(dis);
			}
		// parse authorities
			DNSRecord[] authorities = new DNSRecord[authority_count];
			for( int i=0; i<authority_count; ++i ) {
				authorities[i] = DNSRecordFactory.createRecord(dis);
			}
		// parse additionals
			DNSRecord[] additionals = new DNSRecord[additional_count];
			for( int i=0; i<additional_count; ++i ) {
				additionals[i] = DNSRecordFactory.createRecord(dis);
			}
			System.out.println("Finished parsing");
			DNSMessage newmsg = new DNSMessage(id, DNSFlags.toEnumSet(flags), Arrays.asList(questions), Arrays.asList(answers), Arrays.asList(authorities),Arrays.asList(additionals) );
			
			return newmsg;
		} catch (IOException ex) {
			throw new DNSException("Error while building message", ex);
		}
	}

	public static byte[] build(DNSMessage q) throws DNSException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			
			// header
			dos.writeShort(q.getID());
			dos.writeShort(q.getRawFlags());
			
			// field count
			dos.writeShort(q.getQuestions().size());
			dos.writeShort(q.getAnswers().size());
			dos.writeShort(q.getAuthorities().size());
			dos.writeShort(q.getAdditionals().size());
			
			// write all questions
			for(DNSQuestion dq : q.getQuestions()) {
				dos.write(DNSStringFactory.encode(dq.name()));
				dos.writeShort(dq.type().getValue());
				dos.writeShort(dq.cls().getValue());
			}
			
			for( DNSRecord r : q.getAnswers() ) {
				byte[] record = DNSRecordFactory.dumpRecord(r);
				dos.write(record);
			}
			
			dos.close();
			return baos.toByteArray();
		} catch (IOException ex) {
			throw new DNSException(ex);
			//Logger.getLogger(DNSMessageBuilder.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void dumpArray(byte[] a, int begin, int len) {
		System.out.print("Dump array: ");
		for(int i=begin; i<a.length && i<len+begin; ++i ) {
			if(a[i]>47 && a[i]<123) {
				System.out.printf("%c ",a[i]);
			} else {
				System.out.printf("%02X ",a[i]);
			}
		}
		System.out.println();
	}


}
