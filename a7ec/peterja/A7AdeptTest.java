package a7ec.peterja;

import a7adept.*;
import comp401.sushi.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class A7AdeptTest {

	private final double DELTA = 0.005;

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

	private PlateCounter plateCounter;
	private ProfitCounter profitCounter;

	public static String[] getTestNames() {
		return new String[] {
				"testPlateCounterNull",
				"testPlateCounterExistingBelt",
				"testPlateCounts",
				"testProfitCounterNull",
				"testProfitCounterExistingBelt",
				"testGetTotalBeltProfit",
				"testGetAverageBeltProfit",
				"testGetAverageBeltProfitZero"
		};
	}

	@Before
	public void setUp() throws PlatePriceException, BeltPlateException {
		red = new RedPlate(nigiri);
		green = new GreenPlate(sashimi);
		blue = new BluePlate(roll);
		gold = new GoldPlate(roll, 5.0);

		belt = new Belt(10);
		plateCounter = new PlateCounter(belt);
		profitCounter = new ProfitCounter(belt);

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

	@Test(expected = IllegalArgumentException.class)
	public void testPlateCounterNull() throws BeltPlateException {
		new PlateCounter(null);
	}

	@Test
	public void testPlateCounterExistingBelt() {
		PlateCounter counter = new PlateCounter(belt);

		assertEquals(3, counter.getRedPlateCount());
		assertEquals(1, counter.getGreenPlateCount());
		assertEquals(4, counter.getBluePlateCount());
		assertEquals(2, counter.getGoldPlateCount());
	}

	@Test
	public void testPlateCounts() throws BeltPlateException {
		assertEquals(3, plateCounter.getRedPlateCount());
		assertEquals(1, plateCounter.getGreenPlateCount());
		assertEquals(4, plateCounter.getBluePlateCount());
		assertEquals(2, plateCounter.getGoldPlateCount());

		belt.removePlateAtPosition(4);
		assertEquals(3, plateCounter.getBluePlateCount());

		belt.removePlateAtPosition(3);
		assertEquals(0, plateCounter.getGreenPlateCount());

		belt.setPlateAtPosition(red, 3);
		assertEquals(4, plateCounter.getRedPlateCount());

		belt.setPlateAtPosition(green, 4);
		assertEquals(1, plateCounter.getGreenPlateCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProfitCounterNull() {
		new ProfitCounter(null);
	}

	@Test()
	public void testProfitCounterExistingBelt() {
		ProfitCounter counter = new ProfitCounter(belt);

		assertEquals(14.96, counter.getTotalBeltProfit(), DELTA);
		assertEquals(1.50, counter.getAverageBeltProfit(), DELTA);
	}

	@Test
	public void testGetTotalBeltProfit() throws BeltPlateException {
		assertEquals(14.96, profitCounter.getTotalBeltProfit(), DELTA);

		belt.removePlateAtPosition(4);
		assertEquals(13.05, profitCounter.getTotalBeltProfit(), DELTA);

		belt.removePlateAtPosition(3);
		assertEquals(12.69, profitCounter.getTotalBeltProfit(), DELTA);

		belt.setPlateAtPosition(red, 3);
		assertEquals(13.07, profitCounter.getTotalBeltProfit(), DELTA);

		belt.setPlateAtPosition(green, 4);
		assertEquals(13.43, profitCounter.getTotalBeltProfit(), DELTA);
	}

	@Test
	public void testGetAverageBeltProfit() throws BeltPlateException {
		assertEquals(1.50, profitCounter.getAverageBeltProfit(), DELTA);

		belt.removePlateAtPosition(4);
		assertEquals(1.45, profitCounter.getAverageBeltProfit(), DELTA);

		belt.removePlateAtPosition(3);
		assertEquals(1.59, profitCounter.getAverageBeltProfit(), DELTA);

		belt.setPlateAtPosition(red, 3);
		assertEquals(1.45, profitCounter.getAverageBeltProfit(), DELTA);

		belt.setPlateAtPosition(green, 4);
		assertEquals(1.34, profitCounter.getAverageBeltProfit(), DELTA);
	}

	@Test
	public void testGetAverageBeltProfitZero() {
		Belt b = new Belt(3);
		ProfitCounter counter = new ProfitCounter(b);
		assertEquals(0.0, counter.getAverageBeltProfit(), DELTA);
	}
}
