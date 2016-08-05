//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : Reponse_GetPlayerCardRequest.java
//  @ Date : 6/7/2012
//  @ Author : sangdn
//
//
package casino.cardgame.message.reponse.game.tala;

import com.smartfoxserver.v2.entities.data.SFSObject;

public class Notify_MoveCard extends casino.cardgame.message.reponse.SFSGameReponse {

    protected String m_username;
    protected String m_fromUser;
    protected int m_cardId;

    public Notify_MoveCard() {
        super(TALA_REPONSE_NAME.MOVE_CARD_RES);
    }

    public int getM_cardId() {
        return m_cardId;
    }

    public String getM_fromUser() {
        return m_fromUser;
    }

    public String getM_username() {
        return m_username;
    }

    public void setM_cardId(int m_cardId) {
        this.m_cardId = m_cardId;
    }

    public void setM_fromUser(String m_fromUser) {
        this.m_fromUser = m_fromUser;
    }

    public void setM_username(String m_username) {
        this.m_username = m_username;
    }

    public SFSObject ToSFSObject() {
        SFSObject obj = new SFSObject();
        obj.putUtfString("user_name", m_username);
        obj.putUtfString("from_user", m_fromUser);
        obj.putInt("card_id", m_cardId);
        return obj;
    }
}
