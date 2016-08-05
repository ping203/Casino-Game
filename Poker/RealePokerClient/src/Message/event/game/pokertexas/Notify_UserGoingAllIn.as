package Message.event.game.pokertexas {

	import Message.SFSGameEvent;
	
	import com.smartfoxserver.v2.core.SFSEvent;
	import com.smartfoxserver.v2.entities.data.SFSObject;

	public class Notify_UserGoingAllIn extends SFSGameEvent {

		private var m_userName: String;
		private var m_chip: Number;

		public function Notify_UserGoingAllIn() {
			super(POKER_RESPONSE_NAME.USER_GOING_ALL_RES);
		}

		override public function FromSFSEvent(sfse:SFSEvent):SFSGameEvent
		{
			if(sfse.type != SFSEvent.EXTENSION_RESPONSE &&
				sfse.params.cmd != POKER_RESPONSE_NAME.USER_GOING_ALL_RES){
				throw new Error("Can't parse from " + sfse.type + " to USER_GOING_ALL_RES");			
			}
			var param : SFSObject = (sfse.params.params as SFSObject).getSFSObject(POKER_RESPONSE_NAME.USER_GOING_ALL_RES) as SFSObject;
			if(param == null){
				throw new Error("USER_GOING_ALL_RES::FromSFSEvent Error");			
			}
			
			m_userName = param.getUtfString("user_name");
			m_chip = param.getDouble("chip");
			return this;
		}
		
		override public function GetEventName():String
		{
			return POKER_RESPONSE_NAME.USER_GOING_ALL_RES;
		}

		public function get UserName():String
		{
			return m_userName;
		}
		
		public function get Chip():Number
		{
			return m_chip;
		}
	} // end class
} // end package