package com.example.aaryan.xn8;

/**
 * Created by aaryan on 1/29/2017.
 */

public class userTokenModel {


    private String ifscCodeNum;



    private String UserID;
    private String pushID;
    private String accType;
    private String accName;
    private String accNum;
    private String debitCardNum;
    private String panNum;
    private String totalAmount;


    private String bankName;
    private String branchName;


    public userTokenModel() { }

    public userTokenModel(String ifscCodeNum, String bankName, double branchName, String accNum, String debitCardNum, String accType, String UID) { }




    public String getIfscCodeNum() {   return ifscCodeNum;    }
    public void setIfscCodeNum(String ifscCodeNum) {     this.ifscCodeNum = ifscCodeNum;  }



    public String getPushID() {    return pushID;      }
    public void setPushID(String pushID) {    this.pushID = pushID;      }

    public String getUID() {    return UserID;      }
    public void setUID(String UID) {    this.UserID = UID;      }

    public String getAccName() {   return accName;    }
    public void setAccName(String name) {     this.accName = name;  }

    public String getAccType() {    return accType;      }
    public void setAccType(String accType) {    this.accType = accType;      }

    public String getDebitCardNum() {    return debitCardNum;      }
    public void setDebitCardNum(String debitCardNum) {    this.debitCardNum = debitCardNum;      }

    public String getBankName() {   return bankName;    }
    public void setBankName(String bankName) {     this.bankName = bankName;  }

    public String getBranchName() {   return branchName;    }
    public void setBranchName(String branchName) {     this.branchName = branchName;  }

    public String getAccNum() {   return accNum;    }
    public void setAccNum(String accNum) {     this.accNum = accNum;  }

    public String getPanNum() {   return panNum;    }
    public void setPanNum(String vPanNum) {     this.panNum = vPanNum;  }

    public String getTotalAmount() {   return totalAmount;    }
    public void setTotalAmount(String vTotalAmount) {     this.totalAmount = vTotalAmount;  }



}
