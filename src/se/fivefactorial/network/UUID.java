package se.fivefactorial.network;

public class UUID {

	private String uuid;

	public UUID() {
		uuid = java.util.UUID.randomUUID().toString();
	}

	public UUID(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return uuid;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UUID) {
			return ((UUID) obj).uuid.equals(uuid);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

}
