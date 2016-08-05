//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Notify_UserBet.java
//  @ Date : 8/4/2012
//  @ Author : @khoa
//
//
package casino.cardgame.message.reponse.game.pokreTournament;

import casino.cardgame.message.reponse.game.poker.*;
import casino.cardgame.message.reponse.SFSGameReponse;
import com.smartfoxserver.v2.entities.data.SFSObject;

public class InviteTourRes extends SFSGameReponse {

    private String _tourName;
    private int _time;

    public InviteTourRes() {
        super(POKER_TOUR_RESPONSE_NAME.INVITE_TOUR_RES);
    }

     public SFSObject ToSFSObject() {
        SFSObject obj = new SFSObject();
        obj.putUtfString("tour_name", _tourName);
        obj.putInt("time", _time);
        return obj;
     }

    public InviteTourRes setTourName(String tourName) {
        this._tourName = tourName;
        return this;
    }

    public InviteTourRes setTime(int time) {
        this._time = time;
        return this;
    }

}
