/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.authserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.koziarz.l3.dns.authserver.datasource.DNSDataSourceException;
import pl.koziarz.l3.dns.protocol.DNSDebugger;
import pl.koziarz.l3.dns.protocol.DNSException;
import pl.koziarz.l3.dns.protocol.DNSFlags;
import pl.koziarz.l3.dns.protocol.DNSMessage;
import pl.koziarz.l3.dns.protocol.DNSMessageBuilder;
import pl.koziarz.l3.dns.protocol.DNSRecordClass;
import pl.koziarz.l3.dns.protocol.DNSQuestion;
import pl.koziarz.l3.dns.protocol.DNSRecord;
import pl.koziarz.l3.dns.protocol.dnsrecorddata.DNSRecordA;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSWorker implements Runnable {

	private final ConcurrentLinkedQueue<DatagramPacket> packetQueue;
	private DatagramSocket socket;
	private DNSDataSource dnssrc;

	public DNSWorker(DNSDataSource dnssrc, ConcurrentLinkedQueue<DatagramPacket> packetQueue, DatagramSocket output) throws DNSException {
		if( dnssrc == null ) {
			throw new DNSException("DNS Data Source must not be null.");
		}
		this.packetQueue = packetQueue;
		this.socket = output;
		this.dnssrc = dnssrc;
	}
	
	@Override
	public void run() {
		//System.out.println("Worker started");
		while( !Thread.currentThread().isInterrupted() ) {
			// wait for information from parent thread
			synchronized(packetQueue) {
				try {
					System.out.println("Worker waiting...");
					packetQueue.wait();
				} catch (InterruptedException ex) {
					//Logger.getLogger(DNSWorker.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			// work, while packetQueue has elements
			while( !packetQueue.isEmpty() ) {
				DatagramPacket packet = packetQueue.poll();
				System.out.println("Worker is parsing queue element");
				if( packet != null ) {
					DNSMessage response=null;
					try {
						DNSMessage query = DNSMessageBuilder.build(packet.getData(), packet.getLength());
						//System.out.println("Worker got QUERY\n===================================");
						//DNSDebugger.dumpMessage(query);
						//DNSDebugger.dumpArray(packet.getData(), 0, packet.getLength());

						// decoding OK, ask database
						DNSQuestion question = query.getQuestions().iterator().next();
						if( question == null ) {
							throw new DNSException("Question field size=0");
						}
						List<DNSRecord> recordcollection = dnssrc.getData(question);

						// build response

						response = new DNSMessage(
							query.getID(), 
							EnumSet.of(DNSFlags.QUERY_RESPONSE,DNSFlags.RECURSION_DESIRED,DNSFlags.AUTHORITATIVE_ANSWER),
							query.getQuestions(),
							recordcollection,
							null,
							null);
						// send response
						//throw new DNSException();
					} catch( DNSException | DNSDataSourceException e) {
						// decoding error.
						// build response
						byte[] data = packet.getData();
						int id = (int)((data[0]&0xFF)<<8)+(data[1]&0xFF);
						response = new DNSMessage(
							id,
							EnumSet.of(DNSFlags.QUERY_RESPONSE, DNSFlags.RC_FORMATERROR),
							null, null, null, null
							);
					}
					// send response - at least error info :)
					//System.out.println("Worker sent response\n===================================");
					//DNSDebugger.dumpMessage(response);

					byte[] out=null;
					try {
						out = DNSMessageBuilder.build(response);
						DNSDebugger.dumpArray(out, 0, out.length);
						DatagramPacket rpacket = new DatagramPacket(out, out.length, packet.getAddress(), packet.getPort());
						socket.send(rpacket);
					} catch (DNSException ex) {
						Logger.getLogger(DNSWorker.class.getName()).log(Level.SEVERE, null, ex);
					} catch (IOException ex) {
						Logger.getLogger(DNSWorker.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
			}
			
		} // end main loop
		System.out.println("Thread worker "+Thread.currentThread().getName()+" finished its work.");
		
	}
	
}
