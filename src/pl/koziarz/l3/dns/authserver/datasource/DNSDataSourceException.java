package pl.koziarz.l3.dns.authserver.datasource;

public class DNSDataSourceException extends Exception {

	private static final long serialVersionUID = 9060560235248781114L;

	public DNSDataSourceException() {
	}

	public DNSDataSourceException(String arg0) {
		super(arg0);
	}

	public DNSDataSourceException(Throwable arg0) {
		super(arg0);
	}

	public DNSDataSourceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DNSDataSourceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
