//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Notify_UserCall.java
//  @ Date : 8/4/2012
//  @ Author : @khoa
//
//
package casino.cardgame.message.reponse.game.poker;

import casino.cardgame.message.reponse.SFSGameReponse;
import com.smartfoxserver.v2.entities.data.SFSObject;

public class Notify_UserGoingAllIn extends SFSGameReponse {

    private String userName;
    private double chip;

    public Notify_UserGoingAllIn() {
        super(POKER_REPONSE_NAME.USER_GOING_ALL_RES);
    }

    public SFSObject ToSFSObject() {
        SFSObject obj = new SFSObject();
        obj.putUtfString("user_name", userName);
        obj.putDouble("chip", chip);
        return obj;
     }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String m_userName) {
        this.userName = m_userName;
    }

    public void setChip(double betChip) {
        this.chip = betChip;
    }
}
