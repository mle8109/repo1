package CallbackPattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventNotifier {
	Map<String, EventListener> listeners = new HashMap<String, EventListener>(); 
	public EventNotifier() {
//		doSomeWork();
	}
	
	public void setListeners(String key, EventListener listener) {
		if (listener != null) {
			if (!listeners.containsKey(key)) {
				listeners.put(key, listener);
			}
			else {
				listeners.remove(key);
			}
		}
	}
	
	
	public void doSomeWork() {
		Random rand = new Random();

		int  n = rand.nextInt(50) + 1;
		for (EventListener listener: listeners.values()) {
			listener.onResult(Integer.toString(n));
		}
	}
	
	
}
