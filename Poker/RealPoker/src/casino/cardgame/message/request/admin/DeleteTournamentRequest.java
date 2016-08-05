//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : GetPlayerCardRequest.java
//  @ Date : 6/7/2012
//  @ Author : khoatd
//
//  @ Desc: Request to get card from the previous player



package casino.cardgame.message.request.admin;

import casino.cardgame.entity.RoomInfo;
import casino.cardgame.entity.UserInfo;
import casino.cardgame.message.request.SFSGameRequest;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Timer;


public class DeleteTournamentRequest extends SFSGameRequest {
    
    private String _tourName;
    
    @Override
    public SFSGameRequest FromSFSObject(ISFSObject isfso) {
        _tourName = isfso.getUtfString("name");
        return this;
    }

    @Override
    public String GetRequestName() {
        return ADMIN_REQUEST_NAME.DELETE_TOUR_REQ;
    }

    public String getTournamentName() {
        return _tourName;
    }

}
