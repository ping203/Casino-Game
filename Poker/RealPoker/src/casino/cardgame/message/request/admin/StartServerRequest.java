//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : StartServerRequest.java
//  @ Date : 5/27/2012
//  @ Author : sangdn
//
//



package casino.cardgame.message.request.admin;


import casino.cardgame.message.request.SFSGameRequest;
import com.smartfoxserver.v2.entities.data.ISFSObject;


public class StartServerRequest extends casino.cardgame.message.request.SFSGameRequest {

   @Override
    public SFSGameRequest FromSFSObject(ISFSObject isfso) {
        return this;
    }

    @Override
    public String GetRequestName() {
        return ADMIN_REQUEST_NAME.START_SERVER_REQ;
    }
}
