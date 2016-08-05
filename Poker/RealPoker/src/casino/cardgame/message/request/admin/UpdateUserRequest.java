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

import casino.cardgame.entity.UserInfo;
import casino.cardgame.message.request.SFSGameRequest;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Timer;


public class UpdateUserRequest extends casino.cardgame.message.request.SFSGameRequest {
    
    private UserInfo _user;
    
    @Override
    public SFSGameRequest FromSFSObject(ISFSObject isfso) {
        String userName = isfso.getUtfString("user_name");
        String displayName = isfso.getUtfString("display_name");
        String password = isfso.getUtfString("password");
        String email = isfso.getUtfString("email");
        Double chip = isfso.getDouble("chip");
        Double tourChip = isfso.getDouble("tour_chip");
        Double buyIn = isfso.getDouble("buy_in");
        String location = isfso.getUtfString("location");
        String avartar = isfso.getUtfString("avartar");
        String status = "";
        String title = "";
        
        _user  = new UserInfo(userName, password, email, displayName, chip, tourChip, buyIn, 0.0, null, null, 0.0, title, status, 0, 0, 0, location, avartar, "", null);
        
        return this;
    }

    @Override
    public String GetRequestName() {
        return ADMIN_REQUEST_NAME.UPDATE_USER_REQ;
    }

    public UserInfo getUser() {
        return _user;
    }
}
