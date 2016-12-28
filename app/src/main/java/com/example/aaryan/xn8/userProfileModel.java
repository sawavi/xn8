package com.example.aaryan.xn8;


import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class userProfileModel {

    private String UserID;
    private String firstName;
    private String lastName;
    private double mobileNumber;
    private String emailId;
    private String panCard;
    private String aadhaarCard;
    private String yourIdIs;


    // Default constructor required for calls to
    // DataSnapshot.getValue(userProfileModel.class)
    public userProfileModel() { }

    public userProfileModel(String firstName, String lastName, double mobileNumber, String emailId, String panCard, String aadhaarCard) { }



    public String getUID() {    return UserID;      }
    public void setUID(String UID) {    this.UserID = UID;      }

    public String getYourIdIs() {    return yourIdIs;      }
    public void setYourIdIs(String yourIdIs) {    this.yourIdIs = yourIdIs;      }


    public String getFirstName() {   return firstName;    }
    public void setFirstName(String name) {     this.firstName = name;  }

    public String getLastName() {   return lastName;    }
    public void setLastName(String name) {     this.lastName = name;  }

    public Double getMobileNumber() {   return mobileNumber;    }
    public void setMobileNumber(Double mNumber) {     this.mobileNumber = mNumber;  }

    public String getEmailId() {   return emailId;    }
    public void setEmailId(String mailId) {     this.emailId = mailId;  }

    public String getPanCard() {   return panCard;    }
    public void setPanCard(String vPanCard) {     this.panCard = vPanCard;  }

    public String getAadhaarCard() {   return aadhaarCard;    }
    public void setAadhaarCard(String vAadhaarCard) {     this.aadhaarCard = vAadhaarCard;  }

}