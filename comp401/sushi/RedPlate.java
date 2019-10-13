package comp401.sushi;

public class RedPlate extends PlateImpl {

	public RedPlate(Sushi s) throws PlatePriceException {
		super(s, 1.0, Plate.Color.RED);
	}
}
