package com.covid.Model;

public class PatientModel {
    String id;

    String confirmedDate;

    String patientId;

    String region;

    PatientState patientState;

    public String getId() {
        return id;
    }

    public String getConfirmedDate() {
        return confirmedDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getRegion() {
        return region;
    }

    public PatientState getPatientState() {
        return patientState;
    }

    public PatientModel(String id, String confirmedDate, String patientId, String region, PatientState patientState) {
        this.id= id;
        this.confirmedDate = confirmedDate;
        this.patientId = patientId;
        this.region = region;
        this.patientState = patientState;
    }

    public static PatientModel PatientModelBuilder(String id, String confirmedDate, String patientId, String region, String patientState){

        // 지역 값 valid
        if(region == null || region.equals("")){
            region = "기타";
        }

        // patientId 값 valid
        if(patientId == null || patientId.equals("")){
            patientId = "-1";
        }

        PatientState state = PatientState.ENTRANCE;

        if(patientState != null && !patientState.equals("")){
            if(patientState.equals("퇴원")){
                state = PatientState.EXIT;
            }else if(patientState.equals("사망")){
                state = PatientState.DEAD;
            }
        }


        return new PatientModel(id,confirmedDate,patientId,region,state);
    }

}
