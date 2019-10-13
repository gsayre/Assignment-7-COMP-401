package a7adept;

import java.util.NoSuchElementException;

import a7adept.PlateEvent.EventType;
import comp401.sushi.Plate;

public class Belt extends java.util.Observable{

	private Plate belt[];
	
	public Belt(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Belt size must be greater than zero.");
		}

		belt = new Plate[size];
	}

	public int getSize() {
		return belt.length;
	}

	public Plate getPlateAtPosition(int position) {
		position = normalizePosition(position);
		return belt[position];
	}

	public void setPlateAtPosition(Plate plate, int position) throws BeltPlateException {
		position = normalizePosition(position);

		if (plate == null) {
			throw new IllegalArgumentException("Plate is null");
		}

		if (belt[position] != null) {
			throw new BeltPlateException(position, plate, this);
		}
		setChanged();
		PlateEvent plateEvent = new PlateEvent(EventType.PLATE_PLACED, plate, position);
		notifyObservers(plateEvent);
		belt[position] = plate;
	}

	public void clearPlateAtPosition(int position) {
		position = normalizePosition(position);
		belt[position] = null;
	}

	public Plate removePlateAtPosition(int position) {
		Plate plate = getPlateAtPosition(position);
		if (plate == null) {
			throw new NoSuchElementException();
		}
		clearPlateAtPosition(position);
		setChanged();
		PlateEvent plateEvent = new PlateEvent(EventType.PLATE_REMOVED, plate, position);
		notifyObservers(plateEvent);
		return plate;
	}

	public int setPlateNearestToPosition(Plate plate, int position) throws BeltFullException {
		for (int i=0; i<getSize(); i++) {
			try {
				setPlateAtPosition(plate, position);
				setChanged();
				PlateEvent plateEvent = new PlateEvent(EventType.PLATE_PLACED, plate, position);
				notifyObservers(plateEvent);
				return normalizePosition(position);
			} catch (BeltPlateException e) {
				position += 1;
			}
		}
		throw new BeltFullException(this);
	}

	private int normalizePosition(int position) {
		int normalized_position = position%getSize();

		if (position < 0) {
			normalized_position += getSize();
		}

		return normalized_position;
	}

	public void rotate() {
		Plate last_plate = belt[getSize()-1];
		for (int i=getSize()-1; i>0; i--) {
			belt[i] = belt[i-1];
		}
		belt[0] = last_plate;
	}
}
