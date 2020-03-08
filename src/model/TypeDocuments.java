package model;

public enum TypeDocuments {
	
	ACARD("Adult Identity Card"), ICARD("Identity Card"), CIVILREGIST("Civilzen Registration"),
	PASSPORT("Passport"), FOREIGNCARD("Foreign Card");
	
	private final String typeDoc;
	
	private TypeDocuments(String t) {
		typeDoc=t;
	}
	
	public String getType() {
		return typeDoc;
	}
}
