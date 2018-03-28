package CallbackPattern;

public class EventReceiver implements EventListener{
	EventNotifier en;
	
	public EventReceiver() {
		en = new EventNotifier();
		en.setListeners("lis1", this);
		en.doSomeWork();
	}
	
	@Override
	public void onResult(String result) {
		System.out.println("Received result: " + result);
	}

}
