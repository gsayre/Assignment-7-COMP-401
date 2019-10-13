package a7ec.peterja;

import a7jedi.*;
import comp401.sushi.*;

public class CustomerTestable implements Customer {
	public int callCount = 0;

	public CustomerTestable() {
	}

	public void observePlateOnBelt(Belt b, Plate p, int position) {
		callCount += 1;
	}

	public int getCallCount() {
		return callCount;
	}

	public void resetCallCount() {
		callCount = 0;
	}
}
