package com.example.aaryan.xn8;

/**
 * Created by aaryan on 1/4/2017.
 */
import com.google.firebase.database.IgnoreExtraProperties;



@IgnoreExtraProperties
public class userAccountModel {

    private String UserID;
    private String accType;
    private String accNum;
    private String debitCardNum;

    private String ifscCodeNum;
    private String bankName;
    private String branchName;


    // Default constructor required for calls to
    // DataSnapshot.getValue(userProfileModel.class)
    public userAccountModel() { }

    public userAccountModel(String ifscCodeNum, String bankName, double branchName, String accNum, String debitCardNum, String accType, String UID) { }



    public String getUID() {    return UserID;      }
    public void setUID(String UID) {    this.UserID = UID;      }

    public String getAccType() {    return accType;      }
    public void setAccType(String accType) {    this.accType = accType;      }

    public String getDebitCardNum() {    return debitCardNum;      }
    public void setDebitCardNum(String debitCardNum) {    this.debitCardNum = debitCardNum;      }


    public String getIfscCodeNum() {   return ifscCodeNum;    }
    public void setIfscCodeNum(String ifscCodeNum) {     this.ifscCodeNum = ifscCodeNum;  }

    public String getBankName() {   return bankName;    }
    public void setBankName(String bankName) {     this.bankName = bankName;  }

    public String getBranchName() {   return branchName;    }
    public void setBranchName(String branchName) {     this.branchName = branchName;  }

    public String getAccNum() {   return accNum;    }
    public void setAccNum(String accNum) {     this.accNum = accNum;  }

}