package edu.pnu.service.other;

public enum AddModify {
	 	ADD(1, "추가"),
	    MODIFY(2, "수정");

	    private final int code;
	    private final String description;

	    AddModify(int code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	    public int getCode() {
	        return code;
	    }

	    public String getDescription() {
	        return description;
	    }
}
