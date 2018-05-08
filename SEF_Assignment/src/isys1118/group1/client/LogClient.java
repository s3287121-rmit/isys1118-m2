package isys1118.group1.client;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogClient {
	
	public static Logger logger;
	
	static {
		logger = Logger.getLogger("client");
		for (Handler h : logger.getHandlers()) {
			h.setFormatter(new Formatter() {
				
				private final StringBuilder sb = new StringBuilder();

				@Override
				public String format(LogRecord rec) {
				    synchronized (sb) {
				        sb.setLength(0);
				        sb.append(new Date(rec.getMillis()).toString());
				        sb.append(": ");
				        sb.append(rec.getMessage());
				        sb.append("\n");
				        return sb.toString();
				    }
				}
				
			});
			h.setLevel(Level.INFO);
		}
	}
	
	public static void logMessage(String message) {
		logger.log(Level.INFO, message);
	}
	
	public static void logWarning(String message) {
		logger.log(Level.WARNING, message);
	}
	
	public static void logError(String message, Throwable error) {
		logger.log(Level.SEVERE, message, error);
	}
	
}
