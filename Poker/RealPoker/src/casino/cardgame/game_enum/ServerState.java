//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : ServerState.java
//  @ Date : 5/27/2012
//  @ Author : sangdn
//
//



package casino.cardgame.game_enum;


public enum ServerState {
	RUNNING(1),
	STOP(2),
	PENDING(3),
        TESTING(4);
        private int code;
        private ServerState(int x){            
            code = x;
        }
        public int getCode(){
            return code;
        }
}
