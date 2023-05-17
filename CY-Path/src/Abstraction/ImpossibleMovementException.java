package Abstraction;

public class ImpossibleMovementException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ImpossibleMovementException(String msg) {
		super(msg);
	}
}
