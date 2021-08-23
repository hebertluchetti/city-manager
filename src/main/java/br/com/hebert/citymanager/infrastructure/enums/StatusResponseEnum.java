
package br.com.hebert.citymanager.infrastructure.enums;

public enum StatusResponseEnum {
	
	SUCCESS("1","Success"), 
	INFO   ("2","Info"), 
	WARNING("3","Warning"), 
	ERROR  ("4","Error");

	private String code;
	private String label;

	StatusResponseEnum(final String code, final String label) {
		this.code  = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static StatusResponseEnum getByName(String name) {
		return StatusResponseEnum.valueOf(name);
	}

	public static StatusResponseEnum getByCode(String code) {
		StatusResponseEnum[] values = StatusResponseEnum.values();
		StatusResponseEnum result = null;

		for (int i = 0; i < values.length; i++) {
			StatusResponseEnum valueRef = values[i];
			if (valueRef.getCode().equals(code)) {
				result = valueRef;
				break;
			}
		}
		return result;
	}
	
	public String toString() {
		return name().toLowerCase();
	}
}