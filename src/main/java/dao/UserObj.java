package dao;

public class UserObj {
	private String id, name, ts, desc, image;

	public UserObj(String id, String name, String ts, String desc, String image) {
		this.id = id;
		this.name = name;
		this.ts = ts;
		this.desc = desc;
		this.image = image;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getTs() {
		return this.ts;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public String getImage() {
		return this.image;
	}
}