//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : SpectatorToPlayer.java
//  @ Date : 5/27/2012
//  @ Author : @ThongTrH
//
//



package casino.cardgame.message.event;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;


public class SpectatorToPlayer extends SFSGameEvent {
    private Room m_room;
    private User m_user;
    private Integer m_playerId;
    
    @Override
    public String GetEventName() {
        return SFSEventType.SPECTATOR_TO_PLAYER.toString();
    }
    
    @Override
    public SFSGameEvent FromSFSEvent(ISFSEvent isfse) {
        m_room = (Room) isfse.getParameter(SFSEventParam.ROOM);
        m_user = (User) isfse.getParameter(SFSEventParam.USER);
        m_playerId = (Integer) isfse.getParameter(SFSEventParam.PLAYER_ID);
        return this;
    }

    /**
     * @return the m_room
     */
    public Room getM_room() {
        return m_room;
    }

    /**
     * @return the m_user
     */
    public User getM_user() {
        return m_user;
    }

    /**
     * @return the m_playerId
     */
    public Integer getM_playerId() {
        return m_playerId;
    }
}
