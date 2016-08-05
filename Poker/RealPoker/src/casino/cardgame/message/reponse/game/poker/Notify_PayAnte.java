//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Notify_userTurn.java
//  @ Date : 8/4/2012
//  @ Author : @khoa
//
//
package casino.cardgame.message.reponse.game.poker;

import casino.cardgame.message.reponse.SFSGameReponse;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import java.util.ArrayList;

public class Notify_PayAnte extends SFSGameReponse {

    private double _ante;
    private ArrayList<String> _listUser;

    public Notify_PayAnte() {
        super(POKER_REPONSE_NAME.PAY_ANTE_RES);
    }

    public SFSObject ToSFSObject() {
        SFSObject obj = new SFSObject();
        obj.putDouble("ante", _ante);

        SFSArray arrUser = new SFSArray();
        for (String userName : _listUser) {
            arrUser.addUtfString(userName);
        }
        obj.putSFSArray("list_user", arrUser);

        return obj;
    }

    public Notify_PayAnte setAnte(double ante) {
        this._ante = ante;
        return this;
    }

    public Notify_PayAnte setListUser(ArrayList<String> listUser) {
        this._listUser = listUser;
        return this;
    }
}
