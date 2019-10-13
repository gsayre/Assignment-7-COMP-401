package comp401.sushi;

public class GoldPlate extends PlateImpl {

	public GoldPlate(Sushi s, double price) throws PlatePriceException {
		super(s, check_price(price), Plate.Color.GOLD);
	}
	
	private static double check_price(double price) {
		if (price < 5.0) {
			throw new IllegalArgumentException();
		}
		return price;
	}
}
