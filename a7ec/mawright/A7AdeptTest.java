package a7ec.mawright;

import a7adept.*;

import comp401.sushi.*;
import comp401.sushi.Nigiri.NigiriType;
import comp401.sushi.Sashimi.SashimiType;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class A7AdeptTest {

	// Belt object with six positions created for testing
	private Belt testBelt;

	private double precision;

	private Sushi testContentsRed;
	private Sushi testContentsGreen;
	private Sushi testContentsBlue;
	private Sushi testContentsGold;

	private Plate redPlate;
	private Plate greenPlate;
	private Plate bluePlate;
	private Plate goldPlate;

	private double redPlateProfit;
	private double greenPlateProfit;
	private double bluePlateProfit;
	private double goldPlateProfit;

	private PlateCounter plateCounter;
	private ProfitCounter profitCounter;

	@Before
	public void SetUp() throws PlatePriceException {
		// declares Belt object with six positions for testing
		testBelt = new Belt(6);

		precision =  0.0000001;

		// creates Sashimi and Nigiri Sushi objects to set Plate contents
		testContentsRed = new Nigiri(NigiriType.CRAB); // costs $0.62
		testContentsGreen = new Sashimi(SashimiType.EEL);  // costs $1.64

		// creates Ingredients to be added to Rolls
		// Ikura Gunkan
		SalmonPortion salmon = new SalmonPortion(.75);
		SeaweedPortion seaweedMedium = new SeaweedPortion(.3);
		// Tekkamaki
		TunaPortion tuna = new TunaPortion(1.2);
		RicePortion rice = new RicePortion(.6);
		SeaweedPortion seaweedSmall = new SeaweedPortion(.15);

		// creates IngredientPortion arrays to be added to Rolls
		IngredientPortion[] ikuraGunkanIngredients = {salmon, seaweedMedium};
		IngredientPortion[] tekkamakiIngredients = {tuna, rice, seaweedSmall};

		// creates two Roll Sushi objects to set Plate contents
		testContentsBlue = new Roll("Ikura Gunkan", ikuraGunkanIngredients); // costs $1.43
		testContentsGold = new Roll("Ebi Nigiri", tekkamakiIngredients);	 // costs $2.64

		// constructs test Plates
		redPlate = new RedPlate(testContentsRed);
		greenPlate = new GreenPlate(testContentsGreen);
		bluePlate = new BluePlate(testContentsBlue);
		goldPlate = new GoldPlate(testContentsGold, 5.0);

		// sets Plate contents
		redPlate.setContents(testContentsRed);
		greenPlate.setContents(testContentsGreen);
		bluePlate.setContents(testContentsBlue);
		goldPlate.setContents(testContentsGold);

		// sets the Plate profits
		redPlateProfit = 1.0 - (Math.round(((.75 * .75) + (.5 * .12)) * 100.0)) / 100.0;
		greenPlateProfit = 2.0 - (Math.round((.75 * 2.18) * 100.0)) / 100.0;
		bluePlateProfit = 4.0 - (Math.round(((.75 * .72) + (.3 * 2.95)) * 100.0)) / 100.0;
		goldPlateProfit = 5.0 - (Math.round(((1.2 * 1.77) + (.6 * .12) + (.15 * 2.95)) * 100.0)) / 100.0;

		// creates observer classes that observe the testBelt
		plateCounter = new PlateCounter(testBelt);
		profitCounter = new ProfitCounter(testBelt);
	}

	@Rule // used for detecting Exceptions
	public final ExpectedException exception = ExpectedException.none();

	public static String[] getTestNames() {

		String[] test_names = new String[6];

		test_names[0] = "countObservers";
		test_names[1] = "testConstructors";
		test_names[2] = "testPlateCounterPlaced";
		test_names[3] = "testPlateCounterRemoved";
		test_names[4] = "testProfitCounterPlaced";
		test_names[5] = "testProfitCounterRemoved";

		return test_names;
	}

	@Test
	public void countObservers() {
		// makes sure both observer classes are properly notified in the Belt class
		assertEquals(2, testBelt.countObservers());
	}

	@Test
	public void testConstructors() {
		// anticipates Exception using predefined Rule
		exception.expect(IllegalArgumentException.class);
		
		// makes sure the PlateCounter and ProfitCounter constructors will throw exceptions
		Belt nullBelt = null;
		
		assertEquals(new IllegalArgumentException(), new PlateCounter(nullBelt));
		assertEquals(new IllegalArgumentException(), new ProfitCounter(nullBelt));
	}

	@Test
	public void testPlateCounterPlaced() throws BeltPlateException {

		// sets one of each Plate at a different position
		testBelt.setPlateAtPosition(redPlate, 0);
		testBelt.setPlateAtPosition(greenPlate, 1);
		testBelt.setPlateAtPosition(bluePlate, 2);
		testBelt.setPlateAtPosition(goldPlate, 3);

		// checks that each plate count instance variable is 1
		assertEquals(1, plateCounter.getRedPlateCount());
		assertEquals(1, plateCounter.getGreenPlateCount());
		assertEquals(1, plateCounter.getBluePlateCount());
		assertEquals(1, plateCounter.getGoldPlateCount());

		// adds two more plates
		testBelt.setPlateAtPosition(bluePlate, 4);
		testBelt.setPlateAtPosition(redPlate, 5);

		// checks that each plate count instance variable is correct
		assertEquals(2, plateCounter.getRedPlateCount());
		assertEquals(1, plateCounter.getGreenPlateCount());
		assertEquals(2, plateCounter.getBluePlateCount());
		assertEquals(1, plateCounter.getGoldPlateCount());
	}

	@Test
	public void testPlateCounterRemoved() throws BeltPlateException {

		// sets a Plate at every position
		testBelt.setPlateAtPosition(redPlate, 0);
		testBelt.setPlateAtPosition(greenPlate, 1);
		testBelt.setPlateAtPosition(bluePlate, 2);
		testBelt.setPlateAtPosition(goldPlate, 3);
		testBelt.setPlateAtPosition(bluePlate, 4);
		testBelt.setPlateAtPosition(redPlate, 5);

		// removes some plates
		testBelt.removePlateAtPosition(3);
		testBelt.removePlateAtPosition(5);

		// checks that each plate count instance variable is correct
		assertEquals(1, plateCounter.getRedPlateCount());
		assertEquals(1, plateCounter.getGreenPlateCount());
		assertEquals(2, plateCounter.getBluePlateCount());
		assertEquals(0, plateCounter.getGoldPlateCount());

		// removes the rest of the plates
		testBelt.removePlateAtPosition(0);
		testBelt.removePlateAtPosition(1);
		testBelt.removePlateAtPosition(2);
		testBelt.removePlateAtPosition(4);

		// checks that each plate count instance variable is correct
		assertEquals(0, plateCounter.getRedPlateCount());
		assertEquals(0, plateCounter.getGreenPlateCount());
		assertEquals(0, plateCounter.getBluePlateCount());
		assertEquals(0, plateCounter.getGoldPlateCount());
	}

	@Test
	public void testProfitCounterPlaced() throws BeltPlateException {

		// sets one of each Plate at a different position
		testBelt.setPlateAtPosition(redPlate, 0);
		testBelt.setPlateAtPosition(greenPlate, 1);
		testBelt.setPlateAtPosition(bluePlate, 2);
		testBelt.setPlateAtPosition(goldPlate, 3);

		// variable that holds the expected plateProfit using instanced variable values
		double expectedPlateProfit = redPlateProfit + greenPlateProfit + bluePlateProfit + goldPlateProfit;

		// checks that the total and average profit of the Belt are correct 
		assertEquals(expectedPlateProfit, profitCounter.getTotalBeltProfit(), precision);
		assertEquals(expectedPlateProfit / 4, profitCounter.getAverageBeltProfit(), precision);

		// adds two more plates
		testBelt.setPlateAtPosition(bluePlate, 4);
		testBelt.setPlateAtPosition(redPlate, 5);

		// updates the variable that holds the expected plateProfit using instanced variable values
		expectedPlateProfit = (redPlateProfit * 2) + greenPlateProfit + (bluePlateProfit * 2) + goldPlateProfit;

		// checks that the total and average profit of the Belt are correct 
		assertEquals(expectedPlateProfit, profitCounter.getTotalBeltProfit(), precision);
		assertEquals(expectedPlateProfit / 6, profitCounter.getAverageBeltProfit(), precision);
	}

	@Test
	public void testProfitCounterRemoved() throws BeltPlateException {

		// sets a Plate at every position
		testBelt.setPlateAtPosition(redPlate, 0);
		testBelt.setPlateAtPosition(greenPlate, 1);
		testBelt.setPlateAtPosition(bluePlate, 2);
		testBelt.setPlateAtPosition(goldPlate, 3);
		testBelt.setPlateAtPosition(bluePlate, 4);
		testBelt.setPlateAtPosition(redPlate, 5);

		// removes some plates
		testBelt.removePlateAtPosition(4);
		testBelt.removePlateAtPosition(5);

		// variable that holds the expected plateProfit using instanced variable values
		double expectedPlateProfit = redPlateProfit + greenPlateProfit + bluePlateProfit + goldPlateProfit;

		// checks that the total and average profit of the Belt are correct 
		assertEquals(expectedPlateProfit, profitCounter.getTotalBeltProfit(), precision);
		assertEquals(expectedPlateProfit / 4, profitCounter.getAverageBeltProfit(), precision);

		// removes the rest of the plates
		testBelt.removePlateAtPosition(0);
		testBelt.removePlateAtPosition(1);
		testBelt.removePlateAtPosition(2);
		testBelt.removePlateAtPosition(3);

		// checks that the total and average profit of the Belt are correct 
		assertEquals(0.0, profitCounter.getTotalBeltProfit(), precision);
		assertEquals(0.0, profitCounter.getAverageBeltProfit(), precision);

	}
}