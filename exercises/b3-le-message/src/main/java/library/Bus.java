package library;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Bus {

    private final List<Consumer<LoanReturned>> subscribers = new ArrayList<>();

    public void subscribe(Consumer<LoanReturned> subscriber) {
        subscribers.add(subscriber);
    }

    public void publish(LoanReturned event) {
        subscribers.forEach(subscriber -> subscriber.accept(event));
    }
}
