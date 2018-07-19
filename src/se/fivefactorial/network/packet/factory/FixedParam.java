package se.fivefactorial.network.packet.factory;

public class FixedParam extends Param {

	private Object object;

	public FixedParam(Object object) {
		super(object.getClass());
		this.object = object;
	}

	@Override
	public Object getInstance() {
		return object;
	}

}
