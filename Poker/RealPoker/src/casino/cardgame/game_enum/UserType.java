//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : casino project
//  @ File Name : UserType.java
//  @ Date : 5/27/2012
//  @ Author : sangdn
//
//



package casino.cardgame.game_enum;


public enum UserType{
	USER("user"),
	BANNED("banned"),
	ADMIN("administrator"),
	MANAGER("manager");
        
        protected String type;
        private UserType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }

}
