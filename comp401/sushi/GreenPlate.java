package comp401.sushi;

public class GreenPlate extends PlateImpl {

	public GreenPlate(Sushi s) throws PlatePriceException {
		super(s, 2.0, Plate.Color.GREEN);
	}
}
