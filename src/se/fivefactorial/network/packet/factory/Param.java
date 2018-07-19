package se.fivefactorial.network.packet.factory;

public abstract class Param {
	protected Class<?> target;

	public Param(Class<?> target) {
		this.target = target;
	}

	public abstract Object getInstance();

	public boolean is(Class<?> c) {
		boolean is = c.isAssignableFrom(target);
		//System.out.printf("---\nYou are\t%s\nI am\t%s\nMatch\t%s\n---", c, target, is ? "Yes" : "No");
		return is;
	}
}
