//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : StopServerRequest.java
//  @ Date : 5/27/2012
//  @ Author : sangdn
//
//



package casino.cardgame.message.request.admin;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import casino.cardgame.message.request.SFSGameRequest;


public class StopServerRequest extends casino.cardgame.message.request.SFSGameRequest {

    @Override
    public SFSGameRequest FromSFSObject(ISFSObject isfso) {
        return this;
    }

    @Override
    public String GetRequestName() {
        return ADMIN_REQUEST_NAME.STOP_SERVER_REQ;
    }
}
