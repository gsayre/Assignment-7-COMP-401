package comp401.sushi;


public class BluePlate extends PlateImpl {

	public BluePlate(Sushi s) throws PlatePriceException {
		super(s, 4.0, Plate.Color.BLUE);
	}
}
