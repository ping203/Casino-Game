//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : Notify_Start.java
//  @ Date : 6/7/2012
//  @ Author : sangdn
//  @ Description : send init cards for each player
//
//
package casino.cardgame.message.reponse.admin;

import casino.cardgame.message.reponse.game.poker.*;
import casino.cardgame.message.reponse.game.tala.*;
import com.smartfoxserver.v2.entities.data.SFSObject;
import java.util.ArrayList;

public class CreateErrorResponse extends casino.cardgame.message.reponse.SFSGameReponse {

    private String _message; 
    
    public CreateErrorResponse() {
        super(ADMIN_RESPONSE_TYPE.CREATE_ERROR_RES);
    }

    public SFSObject ToSFSObject() {
        SFSObject obj = new SFSObject();
        obj.putUtfString("message", _message);
        return obj;
    }

    public void setMessage(String message) {
        this._message = message;
    }

}
