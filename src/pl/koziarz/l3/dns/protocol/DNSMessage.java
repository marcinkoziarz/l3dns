package pl.koziarz.l3.dns.protocol;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

/**
 *
 * @author Marcin Koziarz <marcin@koziarz.pl>
 */
public class DNSMessage {
	private int id;
	private int flags;
	
	private Collection<DNSQuestion> questionlist;
	private Collection<DNSRecord> answerlist;
	private Collection<DNSRecord> authoritylist;
	private Collection<DNSRecord> additionallist;
	
	public DNSMessage(int id, EnumSet<DNSFlags> flags, Collection<DNSQuestion> questionlist, Collection<DNSRecord> answerlist, Collection<DNSRecord> authoritylist, Collection<DNSRecord> additionallist) {
		this.id = id;
		this.flags = DNSFlags.fromEnumSet(flags);
		this.questionlist = questionlist;
		this.answerlist = answerlist;
		this.authoritylist = authoritylist;
		this.additionallist = additionallist;
	}
	
	@Override
	public String toString() {
		return "DNSMessage: id="+id+", flags="+flags+", q="+questionlist.size()+",";
	}
	
	public EnumSet<DNSFlags> getFlags() {
		return DNSFlags.toEnumSet(flags);
	}
	
	public int getRawFlags() {
		return flags;
	}

	public void addQuestion(DNSQuestion q) {
		questionlist.add(q);
	}
	
	public Collection<DNSQuestion> getQuestions() {
		if( questionlist != null)
			return questionlist;
		return Collections.EMPTY_LIST;
	}
	
	public Collection<DNSRecord> getAnswers() {
		if( answerlist != null )
			return answerlist;
		return Collections.EMPTY_LIST;
	}

	public Collection<DNSRecord> getAuthorities() {
		if( authoritylist != null )
			return authoritylist;
		return Collections.EMPTY_LIST;
	}

	public Collection<DNSRecord> getAdditionals() {
		if( additionallist != null )
			return additionallist;
		return Collections.EMPTY_LIST;
	}

	public int getID() {
		return id;
	}
}
