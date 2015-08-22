/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.koziarz.l3.dns.authserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.koziarz.l3.dns.protocol.DNSException;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSAuthoritativeServer implements Runnable {

	private Thread thread;
	private int port;
	private final ConcurrentLinkedQueue<DatagramPacket> packetQueue = new ConcurrentLinkedQueue<>();
	private DNSDataSource dnssrc;
	
	public DNSAuthoritativeServer(int port, int workerCount) {
		this.port=port;
		
	}

	public DNSAuthoritativeServer(DNSDataSource dnssrc, int port, int maxWorkerCount) {
		this.port=port;
		this.dnssrc=dnssrc;
	}
	
	public void startServer() {
		if( thread == null ) {
			packetQueue.clear();
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stopServer() {
		if( thread != null && thread.isAlive() ) {
			thread.interrupt();
		}
	}
	
	public boolean isRunning() {
		return (thread != null && thread.isAlive());
	}
	
	@Override
	public void run() {
		Thread workerth=null;
		DatagramSocket sock = null;
		try {
			sock = new DatagramSocket(port);
			sock.setSoTimeout(1000);
			
			DNSWorker worker = new DNSWorker(dnssrc,packetQueue,sock);
			workerth = new Thread(worker, "dns worker");
			workerth.start();
			
			// wait for incoming packets
			while(!Thread.currentThread().isInterrupted()) {
				byte[] inbuff = new byte[2048];
				DatagramPacket input = new DatagramPacket(inbuff, inbuff.length);
				try {
					sock.receive(input);
					synchronized(packetQueue) {
						System.out.println("DNS Server got question, adding to queue...");
						packetQueue.add(input);
						packetQueue.notifyAll();
					}
				} catch (SocketTimeoutException ex) {
					//System.out.println("Did not receive packet in 10 seconds. Waiting again...");
				} catch (IOException ex) {
					Logger.getLogger(DNSAuthoritativeServer.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} catch (SocketException ex) {
			Logger.getLogger(DNSAuthoritativeServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (DNSException ex) {
			Logger.getLogger(DNSAuthoritativeServer.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if( sock != null ) {
				sock.close();
			}
			if( workerth != null ) {
				workerth.interrupt();
			}
			packetQueue.clear();
			thread=null;
		}
	}
	
}
