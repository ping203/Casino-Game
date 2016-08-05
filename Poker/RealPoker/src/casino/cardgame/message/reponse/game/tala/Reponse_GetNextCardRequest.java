//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : Reponse_GetNextCardRequest.java
//  @ Date : 6/7/2012
//  @ Author : sangdn
//
//
package casino.cardgame.message.reponse.game.tala;

import com.smartfoxserver.v2.entities.data.SFSObject;

public class Reponse_GetNextCardRequest extends casino.cardgame.message.reponse.SFSGameReponse {

    protected String m_username;
    protected int m_cardId;
    protected int m_time;

    public Reponse_GetNextCardRequest() {
        super(TALA_REPONSE_NAME.GET_NEXT_CARD_RES);
    }

    public int getM_cardId() {
        return m_cardId;
    }

    public int getM_time() {
        return m_time;
    }

    public String getM_username() {
        return m_username;
    }

    public Reponse_GetNextCardRequest setM_cardId(int m_cardId) {
        this.m_cardId = m_cardId;
        return this;
    }

    public Reponse_GetNextCardRequest setM_time(int m_time) {
        this.m_time = m_time;
        return this;
    }

    public Reponse_GetNextCardRequest setM_username(String m_username) {
        this.m_username = m_username;
        return this;
    }

    public SFSObject ToSFSObject() {
        SFSObject obj = new SFSObject();
        obj.putUtfString("user_name", m_username);
        obj.putInt("card_id", m_cardId);
        obj.putInt("time", m_time);
        return obj;
    }
}
