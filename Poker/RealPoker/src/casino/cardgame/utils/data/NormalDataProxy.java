//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : NormalDataProxy.java
//  @ Date : 5/27/2012
//  @ Author : sangdn
//
//
package casino.cardgame.utils.data;

import casino.cardgame.entity.LeaderBoardInfo;
import casino.cardgame.entity.LoginHistory;
import casino.cardgame.entity.RoomHistory;
import casino.cardgame.entity.RoomInfo;
import casino.cardgame.entity.ServerStateHistory;
import casino.cardgame.entity.TableResult;
import casino.cardgame.entity.TopWinnerInfo;
import casino.cardgame.entity.TransactionInfo;
import casino.cardgame.entity.UserInfo;
import casino.cardgame.entity.game.LevelDetailEntity;
import casino.cardgame.entity.game.TableHistory;
import casino.cardgame.entity.game.TournamentEntity;
import casino.cardgame.game_enum.LevelDetailParams;
import casino.cardgame.game_enum.RoomInfoParams;
import casino.cardgame.game_enum.RoomStatus;
import casino.cardgame.game_enum.ServerState;
import casino.cardgame.utils.GlobalValue;
import casino.cardgame.utils.Logger;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import hirondelle.date4j.DateTime;
import java.util.ArrayList;
import java.util.List;

public class NormalDataProxy implements IDataProxy {

//    protected IMemoryCached m_memCached;
//    protected IDbController m_dbController;
    protected Sfs2xDBController m_dbController;
    protected MyMemCached m_memCached;
    protected static NormalDataProxy m_instance;

    protected NormalDataProxy() {
        m_memCached = new MyMemCached();
        m_dbController = Sfs2xDBController.getInstance();


    }

    public static NormalDataProxy getInstance() {
        if (m_instance == null) {
            m_instance = new NormalDataProxy();
        }
        return m_instance;
    }

    ///*******************************************************************///
    //              NEW DATABASE CONTROLLER SECTION
    ///*******************************************************************///
    @Override
    public void updateUser(UserInfo user) throws Exception {
        //update database
        m_dbController.updateUserInfo(user);

        //update memcache
        m_memCached.updateUserInfo(user);
    }
    
    @Override
    public void updateUserChip(String userName, double chip) throws Exception {
        //update database
        m_dbController.updateUserChip(userName, chip);

        //update memcache
        m_memCached.updateUserChip(userName, chip);
    }

    @Override
    public void deleteUser(String userName) throws Exception {
        //update database
        m_dbController.deleteUser(userName);

        //update memcache
        m_memCached.deleteUserInfo(userName);
    }

    @Override
    public void createRoom(RoomInfo room) throws Exception {
        m_dbController.createNewRoom(room);
    }

    @Override
    public void updateRoom(RoomInfo room) throws Exception {
        m_dbController.updateRoomInfo(room);
    }

    @Override
    public void deleteRoom(String roomName) throws Exception {
        m_dbController.deleteRoom(roomName);
    }

    @Override
    public void createTournament(TournamentEntity tour) throws Exception {
        m_dbController.createNewTournament(tour);
    }

    @Override
    public void updateTournament(TournamentEntity tour) throws Exception {
        m_dbController.updateTournamentInfo(tour);
    }

    @Override
    public void deleteTournament(String tourName) throws Exception {
        m_dbController.deleteTournament(tourName);
    }

    @Override
    public boolean createNewLevel(LevelDetailEntity level) throws Exception {
        return m_dbController.createNewLevel(level);
    }

    @Override
    public boolean updateLevel(LevelDetailEntity level) throws Exception {
        return m_dbController.updateLevel(level);
    }

    @Override
    public boolean deleteLevel(int id) throws Exception {
        return m_dbController.deleteLevel(id);
    }

    @Override
    public ISFSArray getUserList(String name, int index, int numRow) {
        return m_dbController.getUserListByName(name, index, numRow);
    }

    @Override
    public ISFSArray getRoomList(String name, int index, int numRow) {
        return m_dbController.getRoomListByName(name, index, numRow);
    }

    @Override
    public RoomInfo getRoomInfo(String name) {

        ISFSArray sfsArr = m_dbController.getRoomByName(name);
        if (sfsArr.size() == 1) {
            ISFSObject item = sfsArr.getSFSObject(0);
            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setBetChip(Double.parseDouble(item.getUtfString(RoomInfoParams.BET_CHIP)));
            roomInfo.setBigBlind(Double.parseDouble(item.getUtfString(RoomInfoParams.BIG_BLIND)));
            roomInfo.setCreateBy(item.getUtfString(RoomInfoParams.CREATE_BY));
            roomInfo.setDisplayName(item.getUtfString(RoomInfoParams.DISPLAY_NAME));
            roomInfo.setMaxBuyin(Double.parseDouble(item.getUtfString(RoomInfoParams.MAX_BUY_IN)));
            roomInfo.setMaxUsers(item.getInt(RoomInfoParams.MAX_USER));
            roomInfo.setMinBuyin(Double.parseDouble(item.getUtfString(RoomInfoParams.MIN_BUY_IN)));
            roomInfo.setNoLimit(item.getInt(RoomInfoParams.NO_LITMIT) != 0);
            roomInfo.setRoomName(item.getUtfString(RoomInfoParams.NAME));
            roomInfo.setPassword(item.getUtfString(RoomInfoParams.PASSWORD));
            roomInfo.setSmallBlind(Double.parseDouble(item.getUtfString(RoomInfoParams.SMALL_BLIND)));
            roomInfo.setStatus(item.getUtfString(RoomInfoParams.STATUS));
            return roomInfo;
        }

        return null;
    }

    @Override
    public ISFSArray getTournamentList(String name, int index, int numRow) {
        return m_dbController.getTournamentListByName(name, index, numRow);
    }

    @Override
    public ISFSArray getTournamentListWithSBBlind(String name, int index, int numRow) {
        return m_dbController.getTournamentListWithSBBlind(name, index, numRow);
    }

    @Override
    public ISFSArray getListLevel(int levelType, int index, int numRow) {
        return m_dbController.getListLevel(levelType, index, numRow);
    }

    @Override
    public ISFSArray getListLevelCollection() {
        return m_dbController.getListLevelCollection();
    }

    public ISFSArray getTournamentInfo(String name) {
        return m_dbController.getTournamentInfo(name);
    }

    @Override
    public ArrayList<LevelDetailEntity> getLevelDetailByType(int levelType, int beginLevel, int endLevel) {
        ArrayList<LevelDetailEntity> listLevel = new ArrayList<LevelDetailEntity>();

        ISFSArray sfsArr = m_dbController.getLevelDetailByType(levelType, beginLevel, endLevel);
        for (int i = 0; i < sfsArr.size(); i++) {
            SFSObject obj = (SFSObject) sfsArr.getSFSObject(i);
            LevelDetailEntity levelEntity = new LevelDetailEntity();

            levelEntity.setId(obj.getInt(LevelDetailParams.ID));
            levelEntity.setLevelType(obj.getInt(LevelDetailParams.LEVEL_TYPE));
            levelEntity.setLevel(obj.getInt(LevelDetailParams.LEVEL));
            levelEntity.setSmallBlind(Double.parseDouble(obj.getUtfString(LevelDetailParams.SMALL_BLIND)));
            levelEntity.setBigBlind(Double.parseDouble(obj.getUtfString(LevelDetailParams.BIG_BLIND)));
            levelEntity.setAnte(Double.parseDouble(obj.getUtfString(LevelDetailParams.ANTE)));
            levelEntity.setTimeLife(obj.getInt(LevelDetailParams.TIME_LIFE));
            listLevel.add(levelEntity);
        }

        return listLevel;
    }

    @Override
    public void changeRoomStatus(String roomName, String status) {
        m_dbController.changeRoomStatus(roomName, status);
    }

    @Override
    public void UpdateTourStatus(String name, String status) throws Exception {
        m_dbController.updateTourStatus(name, status);
    }

    @Override
    public void UpdateRoomStatus(String name, String status) throws Exception {
        m_dbController.updateRoomStatus(name, status);
    }

    @Override
    public void resetTour(String roomName) throws Exception {
        m_dbController.resetTour(roomName);
    }

    @Override
    public void UpdateUserInTour(String tourName, int userCount) throws Exception {
        m_dbController.UpdateUserInTour(tourName, userCount);
    }
    ///*******************************************************************///
    //              END NEW DATABASE CONTROLLER SECTION
    ///*******************************************************************///

    @Override
    public UserInfo GetUserInfo(String strUserName) {
        UserInfo info = m_memCached.GetUserInfo(strUserName);
        if (info == null) {
            info = m_dbController.GetUserInfo(strUserName);
            m_memCached.cacheUserInfo(info);
        }
        return info;
    }

    @Override
    public String GetAdminPassword(String adminName) {
        String pass = m_dbController.GetAdminPassword(adminName);
        return pass;
    }

    @Override
    public String GetUserPassword(String strUserName) {
        String pass = m_memCached.GetUserPassword(strUserName);
        if (pass == null) {
            pass = m_dbController.GetUserPassword(strUserName);
        }
        return pass;
    }

    @Override
    public TableResult GetTableResult(String strTableID) {
        TableResult result = m_memCached.GetTableResult(strTableID);
        if (result == null) {
            result = m_dbController.GetTableResult(strTableID);
        }
        return result;
    }

    @Override
    public TransactionInfo GetTransactionInfo(String strOwnerName) {
        TransactionInfo trans = m_memCached.GetTransactionInfo(strOwnerName);
        if (trans == null) {
            trans = m_dbController.GetTransactionInfo(strOwnerName);
        }
        return trans;
    }

    @Override
    public List<TransactionInfo> GetTransactionHistory(String strOwnerName, DateTime fromDate, DateTime toDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TransactionInfo> GetTransactionHistory(String strOwnerName, int fromIndex, int numRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public TableHistory GetTableHistory(int tableID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LoginHistory> GetLoginHistory(String strUserName, DateTime fromDate, DateTime toDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<LoginHistory> GetLoginHistory(String strUserName, int fromIndex, int numRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoomHistory> GetCreatedRoomHistory(DateTime fromDate, DateTime toDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RoomHistory> GetCreatedRoomHistory(int fromIndex, int numRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ServerStateHistory> GetServerStateHistory(DateTime fromDate, DateTime toDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ServerStateHistory> GetServerStateHistory(int fromIndex, int numRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void logTransactionWinChip(String userName, String fromUser, double chip) {
    }

    @Override
    public List<LeaderBoardInfo> GetLeaderBoard() {
        List<LeaderBoardInfo> list = m_memCached.GetLeaderBoard();
        if (list == null) {
            list = m_dbController.GetLeaderBoard();
            //Cache To Memcache
            if (list != null) {
                m_memCached.cacheLeaderBoardInfo(list);
            }
        }
        return list;
    }

    @Override
    public List<TopWinnerInfo> GetTopWiner() {
        List<TopWinnerInfo> list = m_memCached.GetTopWiner();
        if (list == null) {
            list = m_dbController.GetTopWiner();
            if (list != null) {
                m_memCached.cacheTopWinnerInfo(list);
            }
        }
        return list;
    }
    //************************************************************
    //  SANGDN ----------------ADD ENTITY FUNCTION ---------------
    //*************************************************************

    //khoatd edited
    @Override
    public boolean addUserInfo(UserInfo info) throws Exception {
//        try {
        m_memCached.cacheUserInfo(info);
        return m_dbController.createNewUser(info);
//        } catch (Exception ex) {
//            Logger.error(this.getClass(), ex);
//            return false;
//        }

    }

    @Override
    public void addUserPass(String strUserName, String pass) {
        try {
            m_memCached.cacheUserPass(strUserName, pass);
            m_dbController.addUserPass(strUserName, pass);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void addTableResult(TableResult result) {
        try {
            m_memCached.cacheTableResult(result);
            m_dbController.addTableResult(result);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void addTransactionInfo(TransactionInfo trans) throws Exception {
//        try {
        m_memCached.cacheTransactionInfo(trans);
        m_dbController.addTransactionInfo(trans);
//        } catch (Exception ex) {
//            Logger.error(this.getClass(), ex);
//        }
    }

    @Override
    public void addTableHistory(TableHistory tblHistory) {
        try {
            m_memCached.cacheTableHistory(tblHistory);
            m_dbController.addTableHistory(tblHistory);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void addLoginHistory(LoginHistory loginHistory) {
        try {
            m_memCached.cacheLoginHistory(loginHistory);
            m_dbController.addLoginHistory(loginHistory);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void addRoomHistory(RoomHistory room) {
        try {
            m_memCached.cacheRoomHistory(room);
            m_dbController.addRoomHistory(room);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void addTopWinner(List<TopWinnerInfo> topWinner) {
        try {
            m_memCached.cacheTopWinnerInfo(topWinner);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void addLeaderBoard(List<LeaderBoardInfo> topLeader) {
        try {
            m_memCached.cacheLeaderBoardInfo(topLeader);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    //khoatd
    @Override
    public void updateUserGameChip(double gameChip) {
        try {
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public double checkCard(String serialNumber, String cardPass) {
        try {
            return 0;
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
            return -1;
        }
    }

    @Override
    public void addUserToMemCach(UserInfo info) throws Exception {
        try {
            m_memCached.cacheUserInfo(info);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void addChipForUser(String userName, double amount) throws Exception {
        //update memcache
        UserInfo user = m_memCached.GetUserInfo(userName);
        Double chip = user.getChip();
        user.setChip(chip + amount);
        m_memCached.updateUserInfo(user);
        //update database
        m_dbController.addChipForUser(userName, amount);
    }

    @Override
    public ISFSArray getTransaction(String userName, String byAdmin, String fromDate, String toDate, int index, int numRow) {
        return m_dbController.getTransaction(userName, byAdmin, fromDate, toDate, index, numRow);
    }
}
