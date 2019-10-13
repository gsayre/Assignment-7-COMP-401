package a7ec.peterja;

import a7jedi.*;
import comp401.sushi.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class A7JediTest {

	private SalmonPortion salmon = new SalmonPortion(1.5);
	private SeaweedPortion seaweed = new SeaweedPortion(0.3);
	private RicePortion rice = new RicePortion(1.0);
	private IngredientPortion[] ingredients = new IngredientPortion[] { salmon,
			seaweed, rice };
	private Nigiri nigiri = new Nigiri(Nigiri.NigiriType.CRAB);
	private Sashimi sashimi = new Sashimi(Sashimi.SashimiType.EEL);
	private Roll roll = new Roll("Sushi Roll", ingredients);

	private RedPlate red;
	private GreenPlate green;
	private BluePlate blue;
	private GoldPlate gold;

	private Belt belt;

	private Customer customer = new CustomerTestable();

	public static String[] getTestNames() {
		return new String[] {
				"testRegisterCustomerAtPosition",
				"testRegisterCustomerNull",
				"testRegisterCustomerAtFullPosition",
				"testRegisterCustomerTwice",
				"testUnregisterCustomerAtPosition",
				"testUnregisterCustomerAtEmptyPosition"
		};
	}

	@Before
	public void setUp() throws PlatePriceException, BeltPlateException {
		red = new RedPlate(nigiri);
		green = new GreenPlate(sashimi);
		blue = new BluePlate(roll);
		gold = new GoldPlate(roll, 5.0);

		belt = new Belt(10);
		belt.setPlateAtPosition(red, 0);
		belt.setPlateAtPosition(red, 1);
		belt.setPlateAtPosition(red, 2);
		belt.setPlateAtPosition(green, 3);
		belt.setPlateAtPosition(blue, 4);
		belt.setPlateAtPosition(blue, 5);
		belt.setPlateAtPosition(blue, 6);
		belt.setPlateAtPosition(blue, 7);
		belt.setPlateAtPosition(gold, 8);
		belt.setPlateAtPosition(gold, 9);
	}

	@Test
	public void testRegisterCustomerAtPosition() {
		CustomerTestable c = (CustomerTestable) customer;

		assertEquals(0, c.getCallCount());
		belt.registerCustomerAtPosition(c, 0);
		belt.rotate();
		assertEquals(1, c.getCallCount());

		belt.unregisterCustomerAtPosition(0);
		c.resetCallCount();

		assertEquals(0, c.getCallCount());
		belt.registerCustomerAtPosition(c, -1);
		belt.rotate();
		assertEquals(1, c.getCallCount());

		belt.unregisterCustomerAtPosition(9);
		c.resetCallCount();

		assertEquals(0, c.getCallCount());
		belt.registerCustomerAtPosition(c, 10);
		belt.rotate();
		assertEquals(1, c.getCallCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterCustomerNull() {
		belt.registerCustomerAtPosition(null, 0);
	}

	@Test(expected = RuntimeException.class)
	public void testRegisterCustomerAtFullPosition() {
		Customer customer2 = new CustomerTestable();

		belt.registerCustomerAtPosition(customer, 0);
		belt.registerCustomerAtPosition(customer2, 0);
	}

	@Test(expected = RuntimeException.class)
	public void testRegisterCustomerTwice() {
		belt.registerCustomerAtPosition(customer, 0);
		belt.registerCustomerAtPosition(customer, 1);
	}

	@Test
	public void testUnregisterCustomerAtPosition() {
		CustomerTestable c = (CustomerTestable) customer;

		assertEquals(0, c.getCallCount());
		belt.registerCustomerAtPosition(c, 0);
		belt.rotate();
		assertEquals(1, c.getCallCount());

		belt.unregisterCustomerAtPosition(0);
		c.resetCallCount();

		assertEquals(0, c.getCallCount());
		belt.rotate();
		assertEquals(0, c.getCallCount());
	}

	@Test
	public void testUnregisterCustomerAtEmptyPosition() {
		assertNull(belt.unregisterCustomerAtPosition(0));
	}
}
