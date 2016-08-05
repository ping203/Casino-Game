//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : GetNextCardRequest.java
//  @ Date : 6/7/2012
//  @ Author : sangdn
//
//  @Desc:  
//      + Request to get the next card from the card collection
//



package casino.cardgame.message.request.game;

import casino.cardgame.message.request.SFSGameRequest;
import casino.cardgame.message.request.game.tala.TALA_REQUEST_NAME;
import com.smartfoxserver.v2.entities.data.ISFSObject;


public class ConfirmReadyGameRequest extends casino.cardgame.message.request.SFSGameRequest {

    @Override
    public SFSGameRequest FromSFSObject(ISFSObject isfso) {
        return this;
    }

    @Override
    public String GetRequestName() {
        return TALA_REQUEST_NAME.CONFIRM_READY_GAME;
    }

}
