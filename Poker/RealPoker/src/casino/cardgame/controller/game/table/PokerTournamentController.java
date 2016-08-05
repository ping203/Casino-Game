//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : GameController.java
//  @ Date : 5/27/2012
//  @ Author : sangdn
//
//
package casino.cardgame.controller.game.table;

import casino.cardgame.controller.game.*;
import casino.cardgame.controller.game.table.PokerController;
import casino.cardgame.controller.game.table.TaLaController;
import casino.cardgame.controller.game.table.TableController;
import casino.cardgame.entity.game.LevelDetailEntity;
import casino.cardgame.entity.game.TournamentEntity;
import casino.cardgame.entity.game_entity.Desk;
import casino.cardgame.entity.game_entity.poker_tournament.PokerTournamentInfo;
import casino.cardgame.game_enum.RoomVariableDetail;
import casino.cardgame.game_enum.TournamentStatus;
import casino.cardgame.message.IGameRequest;
import casino.cardgame.message.event.*;
import casino.cardgame.message.reponse.GAME_RESPONSE_NAME;
import casino.cardgame.message.reponse.SFSGameReponse;
import casino.cardgame.message.reponse.game.GetListFreeUserResponse;
import casino.cardgame.message.reponse.game.InvitationReplyResponse;
import casino.cardgame.message.reponse.game.InvitationResponse;
import casino.cardgame.message.reponse.game.pokreTournament.*;
import casino.cardgame.message.request.GAME_REQUEST_NAME;
import casino.cardgame.message.request.game.InvitationReplyRequest;
import casino.cardgame.message.request.game.InvitationRequest;
import casino.cardgame.message.request.SFSGameRequest;
import casino.cardgame.message.request.game.ConfirmReadyGameRequest;
import casino.cardgame.message.request.game.pokerTournament.CreateTourRequest;
import casino.cardgame.message.request.game.pokerTournament.ReplyInviteTourRequest;
import casino.cardgame.utils.GlobalValue;
import casino.cardgame.utils.Logger;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.Zone;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.entities.variables.SFSRoomVariable;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.log4j.pattern.ThreadPatternConverter;

public class PokerTournamentController implements IGameController {

    private PokerTournamentInfo m_tournamentInfo;
    protected Timer m_timer;
    protected PreStartTask prestartTask;

    public PokerTournamentController() {
        Logger.trace("Init Game Controller");
    }

    protected void printDataOfInfo() {
        try {
            int i;
            for (i = 0; i < m_tournamentInfo.getListRegisterName().size(); i++) {
                Logger.trace(m_tournamentInfo.getListRegisterName().get(i));
            }
            for (i = 0; i < m_tournamentInfo.getListRoom().size(); i++) {
                Logger.trace(m_tournamentInfo.getListRoom().get(i).getName() + " room");
            }
            for (i = 0; i < m_tournamentInfo.getListRoomName().size(); i++) {
                Logger.trace(m_tournamentInfo.getListRoomName().get(i));
            }
            for (i = 0; i < m_tournamentInfo.getListUserPlaying().size(); i++) {
                Logger.trace(m_tournamentInfo.getListUserPlaying().get(i) + " user");
            }

        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void handleUpdateTour(TournamentEntity tourEntity) {
        int levelType = tourEntity.getLevelType();
        int beginLevel = tourEntity.getBeginLevel();
        int endLevel = tourEntity.getEndLevel();
        ArrayList<LevelDetailEntity> listLevel = GlobalValue.dataProxy.getLevelDetailByType(levelType, beginLevel, endLevel);
        m_tournamentInfo = new PokerTournamentInfo(tourEntity, listLevel);
    }

    public void handleActiveTour(boolean isActive) {
        try {
            Logger.trace("Enter PokerTournamentController:: handleActiveTour");
//            if (status.equals(TournamentStatus.STOPPING)) {
//                m_tournamentInfo.activeTour(status);
//            } else {
//                //unregistry for users who registed.
//                for (int i = 0; i < m_tournamentInfo.getListUserRegisted().size(); i++) {
//                    User user = m_tournamentInfo.getListUserRegisted().get(i);
//                    processUserUnregistry(user);
//                }
//
//                m_tournamentInfo.processReplayTournament();
//                m_tournamentInfo.processWaitingTour();
//            }

            if (isActive) {
                m_tournamentInfo.processWaitingTour();
            } else {
                processRemoveAllSFSRoom();
                //unregistry for users who registed.
                for (int i = 0; i < m_tournamentInfo.getListUserRegisted().size(); i++) {
                    User user = m_tournamentInfo.getListUserRegisted().get(i);
                    processUserUnregistry(user);
                }

                m_tournamentInfo.processReplayTournament();
                m_tournamentInfo.processStopTour();

            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleGameMessage(SFSGameRequest request) {
        try {
            User sender = request.getM_user();
            TableController tbl = m_tournamentInfo.getMapPokerController(sender.getLastJoinedRoom().getName());
            tbl.HandleGameMessage(sender, request);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void handleCreateTournament(SFSGameRequest request) {
        try {
            Logger.trace("Enter PokerTournamentController:: handleCreateTournament");
            CreateTourRequest req = (CreateTourRequest) request;
            TournamentEntity tour = new TournamentEntity();
            tour.setName(req.getTournamentName());
            tour.setCapacity(req.getPlayerCapacity());
            tour.setFee(req.getFee());
            tour.setStartingChip(req.getStartingChip());
            tour.setFirstPrize(req.getFirstPrize());
            tour.setSecondPrize(req.getSecondPrize());
            tour.setThirdPrize(req.getThirdPrize());

            m_tournamentInfo = new PokerTournamentInfo(tour, new ArrayList<LevelDetailEntity>());
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void processCreateTournament(TournamentEntity tour) {
        try {
            Logger.trace("Enter PokerTournamentController::processCreateTournament");
            int levelType = tour.getLevelType();
            int beginLevel = tour.getBeginLevel();
            int endLevel = tour.getEndLevel();
            Logger.trace("get list level params: " + levelType + "; " + beginLevel + ";" + endLevel);
            ArrayList<LevelDetailEntity> listLevel = GlobalValue.dataProxy.getLevelDetailByType(levelType, beginLevel, endLevel);
            Logger.trace("list level: " + listLevel.size());
            m_tournamentInfo = new PokerTournamentInfo(tour, listLevel);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void handleUserRegistry(SFSGameRequest request) {
        try {
            User register = request.getM_user();
            if (m_tournamentInfo.getTourStatus().equals(TournamentStatus.WAITING)
                    && m_tournamentInfo.getListUserRegisted().contains(register) == false) {

                Logger.trace("Enter PokerTournamentController:: handleUserRegistry");

                //apply fee for user
                String userName = register.getName();
                double fee = m_tournamentInfo.getFee();
                double userChip = GlobalValue.dataProxy.GetUserInfo(userName).getChip();
                if (fee > userChip) {
                    responseUserRegisterError(register, "Not enough chip for register!!!");
                    return;
                }
                double TChip = m_tournamentInfo.getStartingChip();
                GlobalValue.dataProxy.updateUserChip(userName, userChip - fee);
                GlobalValue.dataProxy.GetUserInfo(userName).setTourChip(TChip);


                m_tournamentInfo.handleUserRegistry(register);

                if (m_tournamentInfo.isBeginTournament()) {
                    handlePreStartTour();
                }

                //increase user in tournament
                try {
                    GlobalValue.dataProxy.UpdateUserInTour(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getListUserRegisted().size());
                    processUserCountChangeResponse(m_tournamentInfo.getListUserRegisted().size());
                } catch (Exception ex) {
                    Logger.error(this.getClass(), ex);
                }

                ///response for user.
                RegistrationRes response = new RegistrationRes();
                response.setTourName(m_tournamentInfo.getTournamentName());
                response.setUserName(register.getName());
                response.setFee(TChip);
                response.AddParam(POKER_TOUR_RESPONSE_NAME.REGISTRATION_RES, response.ToSFSObject());
                response.AddReceiver(register);
                GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
            } else {
                responseUserRegisterError(register, "Can not registry, please select other tournament!");
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    protected void responseUserRegisterError(User regisUser, String message) {
        Logger.trace("PokerTournamentController:: registry error");
        RegistrationErrorRes response = new RegistrationErrorRes();
        response.setMessage(message);
        response.AddParam(POKER_TOUR_RESPONSE_NAME.REGISTRATION_ERROR_RES, response.ToSFSObject());
        response.AddReceiver(regisUser);
        GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
    }

    public void handleUserUnregistry(SFSGameRequest request) {
        User user = request.getM_user();
        if (m_tournamentInfo.getTourStatus().equals(TournamentStatus.WAITING)) {
            processUserUnregistry(user);
        }
    }

    public void processUserUnregistry(User user) {
        try {
            Logger.trace("Enter PokerTournamentController:: processUserUnregistry");
            if (m_tournamentInfo.getListRegisterName().contains(user.getName())) {

                m_tournamentInfo.handleUserUnregistry(user);

                //return fee for user.
                String userName = user.getName();
                double fee = m_tournamentInfo.getFee();
                double userChip = GlobalValue.dataProxy.GetUserInfo(userName).getChip();
                GlobalValue.dataProxy.updateUserChip(userName, userChip + fee);
                GlobalValue.dataProxy.GetUserInfo(userName).setTourChip(0.0);

                //decrease user in tournament
                try {
                    GlobalValue.dataProxy.UpdateUserInTour(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getListUserRegisted().size());
                    processUserCountChangeResponse(m_tournamentInfo.getListUserRegisted().size());
                } catch (Exception ex) {
                    Logger.error(this.getClass(), ex);
                }

                UnregistrationRes response = new UnregistrationRes();
                response.setUserName(user.getName()).setTourName(m_tournamentInfo.getTournamentName()).setTourStatus(m_tournamentInfo.getTourStatus()).setFee(fee);
                response.AddParam(POKER_TOUR_RESPONSE_NAME.UNREGISTRATION_RES, response.ToSFSObject());
                response.AddReceiver(user);
                GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void processTournamentStatusChangeRes(String status) {
        TournamentChangeStatusRes response = new TournamentChangeStatusRes();
        response.setName(m_tournamentInfo.getTournamentName()).setStatus(status);
        response.AddParam(POKER_TOUR_RESPONSE_NAME.TOURNAMENT_STATUS_CHANGE_RES, response.ToSFSObject());
        responseForAllUser(response);
    }

    public void handlePreStartTour() {
        try {
            Logger.trace("Enter PokerTournamentController:: handlePreStartTour");
            PreStartTourRes response = new PreStartTourRes();
            response.setTourName(m_tournamentInfo.getTournamentName()).setTime(m_tournamentInfo.PRESTART_TIME);
            response.AddParam(POKER_TOUR_RESPONSE_NAME.PRESTART_TOUR_RES, response.ToSFSObject());
            responseToAllUserRegisted(response);

            m_tournamentInfo.processPrestartTour();

            //update status to DB
            GlobalValue.dataProxy.UpdateTourStatus(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getTourStatus());

            processTournamentStatusChangeRes(m_tournamentInfo.getTourStatus());

            prestartTask = new PreStartTask();
            StartTimer(prestartTask, m_tournamentInfo.PRESTART_TIME);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void handleGetTournamentDetail(SFSGameRequest request) {
        try {
            Logger.trace("Enter PokerTournamentController:: handleGetlTournamentDetai");
            GetDetailTourRes response = new GetDetailTourRes();
            response.setName(m_tournamentInfo.getTournamentName()).setDisplayName(m_tournamentInfo.getDisplayName()).setBetChip(m_tournamentInfo.getSmallBlind()).setFee(m_tournamentInfo.getFee()).setStartingChip(m_tournamentInfo.getStartingChip()).setNumRegister(m_tournamentInfo.getNumberUserRegisted()).setCapacity(m_tournamentInfo.getPlayerCapacity()).setStatus(m_tournamentInfo.getTourStatus()).setFirstPrize(m_tournamentInfo.getFirstPrize()).setSecondPrize(m_tournamentInfo.getSecondPrize()).setThirdPrize(m_tournamentInfo.getThirdPrize()).setListRegister(m_tournamentInfo.getListRegisterName());
            response.AddParam(POKER_TOUR_RESPONSE_NAME.GET_DETAIL_TOUR_RES, response.ToSFSObject());
            response.AddReceiver(request.getM_user());
            GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void handleReplyInvitation(User sender, SFSGameRequest request) {
        try {
            Logger.trace("Enter PokerTournamentController:: handleReplyInvitation");
            ReplyInviteTourRequest req = (ReplyInviteTourRequest) request;
            boolean isAccept = req.isAccept();

            if (isAccept) {
                m_tournamentInfo.handleUserAccept(sender);
            } else {
                m_tournamentInfo.handleUserRefuse(sender);
            }

            if (m_tournamentInfo.getNumUserReply() == m_tournamentInfo.getListUserRegisted().size()
                    && m_tournamentInfo.getTourStatus().equals(TournamentStatus.PRESTART)) {
                handleBeginTournament();
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    protected void HandleReplayTournament() {
        try {
            Logger.trace("Enter PokerTournamentController:: processReplayTournament");

            processRemoveAllSFSRoom();

            m_tournamentInfo.processReplayTournament();
            m_tournamentInfo.processWaitingTour();

            //update status to DB
            GlobalValue.dataProxy.UpdateTourStatus(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getTourStatus());

            processTournamentStatusChangeRes(m_tournamentInfo.getTourStatus());
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    //khoatd: add all user to response
    protected void responseToAllUserRegisted(SFSGameReponse response) {
        for (int i = 0; i < m_tournamentInfo.getListUserRegisted().size(); i++) {
            User receiver = m_tournamentInfo.getListUserRegisted().get(i);
            if (receiver != null) {
                response.AddReceiver(receiver);
            }
        }
        GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
    }

    private void responseForAllUser(SFSGameReponse response) {
        Zone currentZone = GlobalValue.smartfoxServer.getZoneManager().getZoneByName("RealPokerServer");
        List<User> listUser = (List) currentZone.getUserList();
        for (User user : listUser) {
            response.AddReceiver(user);
        }
        GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
    }

    private void processSendInviteGame() {
        try {
            Logger.trace("Enter PokerTournamentController:: processSendInviteGame");
            //send invitation to user.
            InviteTourRes response = new InviteTourRes();
            response.setTourName(m_tournamentInfo.getTournamentName()).setTime(m_tournamentInfo.BEGIN_TIME);
            response.AddParam(POKER_TOUR_RESPONSE_NAME.INVITE_TOUR_RES, response.ToSFSObject());
            responseToAllUserRegisted(response);

            StartTimer(new BeginTourTask(), m_tournamentInfo.BEGIN_TIME);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    private void handleBeginTournament() {
        try {
            Logger.trace("Enter PokerTournamentController:: handleBeginTournament");
            //create list room

            StopTimer();
            int numValidUser = m_tournamentInfo.getListUserAccepted().size();
            if (numValidUser >= 3) {
                int temp = numValidUser % m_tournamentInfo.NUMBER_DESK;
                int numRoom;
                if (temp == 0) {
                    numRoom = m_tournamentInfo.getListUserAccepted().size() / m_tournamentInfo.NUMBER_DESK;
                } else {
                    numRoom = m_tournamentInfo.getListUserAccepted().size() / m_tournamentInfo.NUMBER_DESK + 1;
                }
                processCreateRooms(numRoom);

                //separate all user to list of room
                m_tournamentInfo.handleGroupUserIntoRoom();

                //join users into rooms.
                processJoinUser();

                //set total pot
                m_tournamentInfo.initTotalPot();

                m_tournamentInfo.processBeginTour();

                //update user in tournament
                try {
                    GlobalValue.dataProxy.UpdateUserInTour(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getListUserAccepted().size());
                    processUserCountChangeResponse(m_tournamentInfo.getListUserAccepted().size());
                } catch (Exception ex) {
                    Logger.error(this.getClass(), ex);
                }
            } else {
                m_tournamentInfo.processWaitingTour();

//                for (int i = 0; i < m_tournamentInfo.getListUserAccepted().size(); i++) {
//                    User user = m_tournamentInfo.getMapUser(m_tournamentInfo.getListUserAccepted().get(i));
                for (int i = 0; i < m_tournamentInfo.getListUserRegisted().size(); i++) {
                    User user = m_tournamentInfo.getListUserRegisted().get(i);
                    if (user != null) {
                        processUserUnregistry(user);
                    }
                }

                HandleReplayTournament();
            }

            //update status to DB
            GlobalValue.dataProxy.UpdateTourStatus(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getTourStatus());

            processTournamentStatusChangeRes(m_tournamentInfo.getTourStatus());

        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public CreateRoomSettings createSettingRoom(String roomName, double betChip, int roomIndex) {
        CreateRoomSettings setting = new CreateRoomSettings();
        setting.setName(roomName);
        setting.setMaxUsers(m_tournamentInfo.NUMBER_DESK);
        setting.setMaxVariablesAllowed(20);
        setting.setGame(true);

        String displayName = m_tournamentInfo.getTournamentName() + "/Room" + roomIndex;
        ArrayList<RoomVariable> roomVariable = new ArrayList<RoomVariable>();
        roomVariable.add(new SFSRoomVariable(RoomVariableDetail.DISPLAY_NAME, displayName));
        roomVariable.add(new SFSRoomVariable(RoomVariableDetail.BET_CHIP, betChip));

        setting.setRoomVariables(roomVariable);

        return setting;
    }

    private void processCreateRooms(int numRoom) {
        try {
            Logger.trace("Enter PokerTournamentController:: processCreateRooms");
            String preRoomName = "tour_" + m_tournamentInfo.getTournamentName() + "_";//ex: tour_sitngo1_room1
            for (int i = 0; i < numRoom; i++) {
                String newRoomName = preRoomName + "room" + i;
                CreateRoomSettings setting = createSettingRoom(newRoomName, m_tournamentInfo.getSmallBlind(), i);

                Zone currentZone = GlobalValue.smartfoxServer.getZoneManager().getZoneByName("RealPokerServer");

                try {
                    Logger.trace("create room: " + newRoomName);
                    Room newRoom = GlobalValue.smartfoxServer.getAPIManager().getSFSApi().createRoom(currentZone, setting, null);
                    m_tournamentInfo.addNewRoom(newRoom);
                } catch (SFSCreateRoomException exc) {
                    Logger.error(exc);
                }
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    //join user into room
    private void processJoinUser() {
        try {
            Logger.trace("Enter PokerTournamentController:: processJoinUser");
            for (int i = 0; i < m_tournamentInfo.getListRoom().size(); i++) {
                Room room = m_tournamentInfo.getListRoom().get(i);
                String roomName = room.getName();
                ArrayList<User> listUser = m_tournamentInfo.getMapRoomUser().get(roomName);
                for (int j = 0; j < listUser.size(); j++) {
                    User user = listUser.get(j);

                    //m_tournamentInfo.handleUserJoin(user, room);

                    GlobalValue.smartfoxServer.getAPIManager().getSFSApi().joinRoom(user, room);

                    Logger.trace("join user: " + user.getName());
                }
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
//            printDataOfInfo();
        }
    }

    //điều phối số lượng player trong room( gọp room, ...)
    protected synchronized void handleUserInRoom() {
        try {
            Logger.trace("Enter PokerTournamentController:: handleUserInRoom");

            //remove all room have user in room == 0;
            processRemoveEmptyRoom();

            int numRoom = m_tournamentInfo.getListRoom().size();
            if (numRoom > 1) {
                //if number of user in room is one, join this user to another room, remove leaved room.
                ArrayList<String> listSortedRoom = m_tournamentInfo.sortRoom();

                int min = m_tournamentInfo.getMapRoomUser(listSortedRoom.get(0)).size();
                int next = m_tournamentInfo.getMapRoomUser(listSortedRoom.get(1)).size();//number of user in next room
                if (min == 1 && next < m_tournamentInfo.NUMBER_DESK) {
                    User user = m_tournamentInfo.getMapRoomUser(listSortedRoom.get(0)).get(0);
                    String roomName1 = listSortedRoom.get(0);
                    String roomName2 = listSortedRoom.get(1);

                    handleMoveUser(user, roomName1, roomName2);
                }
            } else if (numRoom == 1) {
                //only one room is exist.
                String uniqueRoom = m_tournamentInfo.getListRoomName().get(0);
                int userInRoom = m_tournamentInfo.getMapRoomUser(uniqueRoom).size();
                if (userInRoom <= 1) {
                    //finish tournament
                    handleFinishTournament(uniqueRoom);
                }
            }
//            else if(numRoom < 1 && m_tournamentInfo.getTourStatus().equals(PokerTournamentInfo.PLAYING_STATUS)){
//                HandleReplayTournament();
//            }

            //WARNING
            //Quick Fix: error: user = 0, room = 2;
            if (m_tournamentInfo.getListUserPlaying().isEmpty()
                    && m_tournamentInfo.getTourStatus().equals(TournamentStatus.PLAYING)) {
                HandleReplayTournament();
            } else {
                Logger.trace("NUMB OF USERS: " + m_tournamentInfo.getListUserPlaying().size()
                        + " TOURNAMENT STATUS: " + m_tournamentInfo.getTourStatus());
            }

            //update user in tournament
            try {
                GlobalValue.dataProxy.UpdateUserInTour(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getListUserPlaying().size());
                processUserCountChangeResponse(m_tournamentInfo.getListUserPlaying().size());
            } catch (Exception ex) {
                Logger.error(this.getClass(), ex);
            }

            Logger.trace("num user: " + m_tournamentInfo.getListUserPlaying().size()
                    + " num room: " + m_tournamentInfo.getListRoom().size());
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    protected void processRemoveEmptyRoom() {
        for (int i = 0; i < m_tournamentInfo.getListRoom().size(); i++) {
            try {
                Room room = m_tournamentInfo.getListRoom().get(i);
                if (room.getUserList().isEmpty()) {
                    Logger.trace("processRemoveEmptyRoom:: remove room: " + room.getName()
                            + " && user in room: " + room.getUserList().size());
                    GlobalValue.smartfoxServer.getAPIManager().getSFSApi().removeRoom(room);

                    m_tournamentInfo.processRemoveRoom(room);
                }
            } catch (Exception ex) {
                Logger.error(this.getClass(), ex);
            }
        }
    }

    protected void processRemoveAllSFSRoom() {
        Logger.trace("Enter PokerTournamentController:: processRemoveSFSRoom");
        for (int i = 0; i < m_tournamentInfo.getListRoom().size(); i++) {
            try {
                Room room = m_tournamentInfo.getListRoom().get(i);
                GlobalValue.smartfoxServer.getAPIManager().getSFSApi().removeRoom(room);
            } catch (Exception ex) {
                Logger.error(this.getClass(), ex);
            }
        }
    }

    private void handleMoveUser(User user, String roomName1, String roomName2) {
        try {
            Logger.trace("Enter PokerTournamentController:: handleMoveUser");
            Room room1 = m_tournamentInfo.getMapRoom().get(roomName1);
            Room room2 = m_tournamentInfo.getMapRoom().get(roomName2);

            Logger.trace("move user: " + user.getName() + " to room: " + room2.getName());
            m_tournamentInfo.processMoveUser(user, room1, room2);

            //join user in a min room to other room.
            //GlobalValue.smartfoxServer.getAPIManager().getSFSApi().leaveRoom(user, room1);

            try {
                GlobalValue.smartfoxServer.getAPIManager().getSFSApi().joinRoom(user, room2);
            } catch (Exception ex) {
                Logger.error(this.getClass(), ex);
            }

            try {
                GlobalValue.smartfoxServer.getAPIManager().getSFSApi().removeRoom(room1);
            } catch (Exception ex) {
                Logger.error(this.getClass(), ex);
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    //finish khi chỉ còn 1 user trong 1 phòng
    private void handleFinishTournament(String uniqueRoom) {
        try {
            Logger.trace("Enter PokerTournamentController:: handleFinishTournament");
            User lastUser = m_tournamentInfo.getMapRoomUser(uniqueRoom).get(0);
            Room lastRoom = m_tournamentInfo.getMapRoom().get(uniqueRoom);
            //update chip for winners to database.

            //process in tournanmentInfo.
            m_tournamentInfo.processFinishTournament(lastUser, lastRoom.getName());

            try {
                //leave last user 
                GlobalValue.smartfoxServer.getAPIManager().getSFSApi().leaveRoom(lastUser, lastRoom, true, true);
            } catch (Exception ex) {
                Logger.error(this.getClass(), ex);
            }

            try {
                //remove last room.
                m_tournamentInfo.processRemoveRoom(lastRoom);
                GlobalValue.smartfoxServer.getAPIManager().getSFSApi().removeRoom(lastRoom);
            } catch (Exception ex) {
                Logger.error(this.getClass(), ex);
            }

            handleRewardForUser();

            //update status to DB
            GlobalValue.dataProxy.UpdateTourStatus(m_tournamentInfo.getTournamentName(), m_tournamentInfo.getTourStatus());

            processTournamentStatusChangeRes(m_tournamentInfo.getTourStatus());

            Logger.trace("start Replay timer");
            StartTimer(new ReplayTournamentTask(), m_tournamentInfo.REPLAY_TOURNAMENT_TIME);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    protected void handleRewardForUser() {
        try {
            Logger.trace("Enter PokerTournamentController:: handleRewardForUser");

            //m_tournamentInfo.processRewardForUser();

            ArrayList<User> listWinner = m_tournamentInfo.getListWinner();
            for (int i = 0; i < listWinner.size(); i++) {
                User winner = listWinner.get(i);
                double prize = m_tournamentInfo.getMapUserPrize(winner.getName());

                //apply prize for winner.
                double userChip = GlobalValue.dataProxy.GetUserInfo(winner.getName()).getChip();
                GlobalValue.dataProxy.updateUserChip(winner.getName(), userChip + prize);
                GlobalValue.dataProxy.GetUserInfo(winner.getName()).setTourChip(0.0);

                UserRewardRes response = new UserRewardRes();
                response.setPrize(prize);
                response.setTourName(m_tournamentInfo.getTournamentName());
                response.AddParam(POKER_TOUR_RESPONSE_NAME.USER_REWARD_RES, response.ToSFSObject());
                response.AddReceiver(winner);
                GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }
    
    protected void processUserCountChangeResponse(int userCount) {
        try {
            List<User> listUser = GlobalValue.smartfoxServer.getUserManager().getAllUsers();
            
            UserCountChangeRes response = new UserCountChangeRes();
            response.setTourName(m_tournamentInfo.getTournamentName());
            response.setUserCount(userCount);
            response.AddParam(POKER_TOUR_RESPONSE_NAME.USER_COUNT_CHANGE_RES, response.ToSFSObject());
            for (int i = 0; i < listUser.size(); i++) {
                User user = listUser.get(i);
                response.AddReceiver(user);
            }
            GlobalValue.sfsServer.send(response.GetReponseName(), response.GetParam(), response.GetListReceiver());
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandlerUserJoinRoom(SFSGameEvent evt) {
        try {
            UserJoinRoom joinEvt = (UserJoinRoom) evt;
            Logger.trace("Enter PokerTournamentController::/HandlerUserJoinRoom " + joinEvt.getM_user().getName());
            String roomName = joinEvt.getM_room().getName();//tour_sitngo1_room1

            //forward to tableController
            m_tournamentInfo.getMapPokerController(roomName).HandleUserJoinRoom(evt);
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleUserLeaveRoom(SFSGameEvent evt) {
        try {
            UserExitRoom exitEvt = (UserExitRoom) evt;
            User user = exitEvt.getM_user();
            Logger.trace("Enter PokerTournamentController::/HandleUserLeaveRoom " + user.getName());
            String roomName = exitEvt.getM_room().getName();//tour_sitngo1_room1

            //forward to tableController
            m_tournamentInfo.getMapPokerController(roomName).HandleUserLeaveRoom(evt);

            //tránh trường hợp user leave game vì bị move qua bàn khác.
            String roomName2 = m_tournamentInfo.getMapUserRoom(user.getName());
            if (roomName2.equals(roomName)) {
                m_tournamentInfo.processUserLeaveGame(user, roomName);
                handleUserInRoom();
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
//            printDataOfInfo();
        }
    }

    @Override
    public void HandleUserDisconnect(SFSGameEvent evt) {
        try {
            Logger.trace("Enter PokerTournamentController::/HandleUserDisconnect");
            UserDisconnect disconnectEvt = (UserDisconnect) evt;

            //unregistry for disconnected user
            if (m_tournamentInfo.getTourStatus().equals(TournamentStatus.WAITING)) {
                processUserUnregistry(disconnectEvt.getM_user());
            }

            //get last room user joined
            if (disconnectEvt.getM_listJoinedRoom().size() > 0) {
                String roomName = disconnectEvt.getM_listJoinedRoom().get(disconnectEvt.getM_listJoinedRoom().size() - 1).getName();
                m_tournamentInfo.getMapPokerController(roomName).HandleUserDisconnect(evt);

                //process user leave room in tournament controller
                m_tournamentInfo.processUserLeaveGame(disconnectEvt.getM_user(), roomName);
                handleUserInRoom();
            }

        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandlePlayerToSpectator(SFSGameEvent evt) {
        try {
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleRoomAdded(SFSGameEvent evt) {
        try {
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleRoomRemove(SFSGameEvent evt) {
        try {
            RoomRemove roomEvt = (RoomRemove) evt;
            String roomName = roomEvt.getM_room().getName();
            Logger.trace("remove room: " + roomName);

            TableTournamentController ctrl = m_tournamentInfo.getMapPokerController(roomName);
            if (ctrl != null) {
                ctrl.HandleRoomRemove(evt);
            }
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleRoomVariableUpdate(SFSGameEvent evt) {
        try {
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleSpectatorToPlayer(SFSGameEvent evt) {
        try {
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleUserLogout(SFSGameEvent evt) {
        try {
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    @Override
    public void HandleUserVariableUpdate(SFSGameEvent evt) {
        try {
        } catch (Exception ex) {
            Logger.error(this.getClass(), ex);
        }
    }

    public void destroyTour() {
        m_tournamentInfo.processReplayTournament();
        m_tournamentInfo.processWaitingTour();
        if (m_timer != null) {
            StopTimer();
        }
    }

    protected void StartTimer(TimerTask task, int time) {
        m_timer = new Timer();
        m_timer.schedule(task, time);
    }

    private void StopTimer() {
        m_timer.cancel();
        m_timer.purge();
    }

    public PokerTournamentInfo getTournamentInfo() {
        return m_tournamentInfo;
    }

    public void setTournamentInfo(PokerTournamentInfo m_tournamentInfo) {
        this.m_tournamentInfo = m_tournamentInfo;
    }

    @Override
    public void HandleServerReady(SFSGameEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    class PreStartTask extends TimerTask {

        @Override
        public void run() {
            RemoveTimer();
            processSendInviteGame();
        }

        public int getMilisecondsLeft() {
            long time = System.currentTimeMillis() - prestartTask.scheduledExecutionTime();
            int miliSecondesLeft = (int) (-time);
            return miliSecondesLeft;
        }

        private void RemoveTimer() {
            m_timer.cancel();
            m_timer.purge();
        }
    }

    class BeginTourTask extends TimerTask {

        @Override
        public void run() {
            RemoveTimer();
            handleBeginTournament();
        }

        private void RemoveTimer() {
            m_timer.cancel();
            m_timer.purge();
        }
    }

    class ReplayTournamentTask extends TimerTask {

        @Override
        public void run() {
            RemoveTimer();
            HandleReplayTournament();
        }

        private void RemoveTimer() {
            m_timer.cancel();
            m_timer.purge();
        }
    }
}
