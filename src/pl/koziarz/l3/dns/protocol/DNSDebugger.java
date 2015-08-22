/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.protocol;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSDebugger {
	public static void dumpMessage(DNSMessage m) {
		dumpMessage(m, System.out);
	}
	
	public static void dumpMessage(DNSMessage m, PrintStream out) {
		out.println("Message id="+m.getID());
		out.println("\tFlags:");
		for( DNSFlags f : m.getFlags() ) {
			out.println("\t\t* "+f);
		}
		out.println("\tQuestions ("+m.getQuestions().size()+"):");
		for( DNSQuestion r : m.getQuestions() ) {
			out.println("\t\t* "+r);
		}
		out.println("\tAnswers ("+m.getAnswers().size()+"):");
		for( DNSRecord r : m.getAnswers()) {
			out.println("\t\t* "+r);
		}
		out.println("\tAuthorities ("+m.getAuthorities().size()+"):");
		for( DNSRecord r : m.getAuthorities()) {
			out.println("\t\t* "+r);
		}
		out.println("\tAdditionals ("+m.getAdditionals().size()+"):");
		for( DNSRecord r : m.getAdditionals()) {
			out.println("\t\t* "+r);
		}
	}
	
	public static void dumpArray(byte[] arr) {
		dumpArray(arr,0,arr.length);
	}
	
	public static void dumpArray(byte[] arr, int offset, int length) {
		for( int i=offset; i<offset+length; ++i ) {
			System.out.printf("%02X ",arr[i]);
		}
		System.out.println();
		for( int i=offset; i<offset+length; ++i ) {
			int a = arr[i]&0xFF;
			if( a>= 32 && a<= 126 ) {
				System.out.printf(" %c ",a);
			} else {
				System.out.printf("%02X ",a);
			}
			//System.out.printf("%02X ",arr[i]);
		}
		System.out.println();
	}
}
