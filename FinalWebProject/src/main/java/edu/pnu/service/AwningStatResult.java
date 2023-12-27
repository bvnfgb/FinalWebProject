package edu.pnu.service;

import edu.pnu.persistence.other.AwningIndividualStatus;

public enum AwningStatResult {
    SUCCESS(0, null),
    DEVICE_ID_NULL_OR_BLANK(1, null),
    DEVICE_NOT_FOUND(2, null);

    private final int code;
    private AwningIndividualStatus awningIndividualStatus;

    AwningStatResult(int code, AwningIndividualStatus awningIndividualStatus) {
        this.code = code;
        this.awningIndividualStatus = awningIndividualStatus;
    }

    public int getCode() {
        return code;
    }

    public AwningIndividualStatus getAwningIndividualStatus() {
        return awningIndividualStatus;
    }

	public AwningStatResult withAwningIndividualStatus(AwningIndividualStatus awningIndividualStatus2) {
		// TODO Auto-generated method stub
		this.awningIndividualStatus=awningIndividualStatus2;
		return SUCCESS;
	}

	
}
