//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : PendingController.java
//  @ Date : 5/27/2012
//  @ Author : sangdn
//
//



package casino.cardgame.controller.server;


import casino.cardgame.message.event.SFSGameEvent;
import casino.cardgame.message.request.SFSGameRequest;


public class PendingController implements IServerController {
    //Singleton Implement **************************************************
    //
    protected static PendingController m_instance;
    protected PendingController(){
        
    }
    public static PendingController getInstance(){
        if(m_instance == null){
            m_instance = new PendingController();
        }
        return m_instance;
    }
    //
    //End Singleton Implement **************************************************
    @Override
    public void HandleGameMessage(SFSGameRequest sfsreq) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleUserLogin(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandlePlayerToSpectator(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandlePrivateMessage(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandlePublicMessage(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleRoomAdded(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleRoomRemove(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleRoomVariableUpdate(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleSpectatorToPlayer(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleServerReady(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleUserDisconect(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleUserJoinRoom(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleUserLeaveRoom(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleUserLogOut(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleUserVariableUpdate(SFSGameEvent sfse) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void HandleUserJoinZone(SFSGameEvent joinZoneEvt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void HandleAdminMessage(SFSGameRequest sfsreq) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
