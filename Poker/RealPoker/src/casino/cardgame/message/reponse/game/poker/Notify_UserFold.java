//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Notify_UserFold.java
//  @ Date : 8/4/2012
//  @ Author : @khoa
//
//
package casino.cardgame.message.reponse.game.poker;

import casino.cardgame.message.reponse.SFSGameReponse;
import com.smartfoxserver.v2.entities.data.SFSObject;

public class Notify_UserFold extends SFSGameReponse {

    private String userName;

    public Notify_UserFold() {
        super(POKER_REPONSE_NAME.USER_FOLD_RES);
    }

    public SFSObject ToSFSObject() {
        SFSObject obj = new SFSObject();
        obj.putUtfString("user_name", userName);
        return obj;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
