package users.list;

public class Users {
	//数据库字段
		private String id;
		private String email;
		private String pwd;
		private String name;
		private String role;
		private String balance;
		private String margin;
		private String city;
		private String createTime;
		//传递条件查询用
		private String action;
		private String dbName;
		private String tableName="";
		private String type="";
		private String timeFrom="";
		private String timeTo="";
		private String timeSelect="";
		private String groupId="";
		private String groupSelect="";
		private String timeInterval="";
		private String statisticType="";
		
		public Users(){
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id){
			this.id = id;
		}
		public String getEmail(){
			return email;
		}
		public void setEmail(String email){
			this.email = email;
		}
		public String getPwd(){
			return pwd;
		}
		public void setPwd(String pwd){
			this.pwd= pwd;
		}
		public String getName(){
			return name;
		}
		public void setName(String name){
			this.name = name;
		}
		public String getRole(){
			return role;
		}
		public void setRole(String role){
			this.role = role;
		}
		public String getBalance(){
			return balance;
		}
		public void setBalance(String balance){
			this.balance = balance;
		}
		
		public String getMargin(){
			return margin;
		}
		public void setMargin(String margin){
			this.margin = margin;
		}
		public String getCity(){
			return city;
		}
		public void setCity(String city){
			this.city = city;
		}
		
		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getDbName() {
			return dbName;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTimeFrom() {
			return timeFrom;
		}

		public void setTimeFrom(String timeFrom) {
			this.timeFrom = timeFrom;
		}

		public String getTimeTo() {
			return timeTo;
		}

		public void setTimeTo(String timeTo) {
			this.timeTo = timeTo;
		}

		public String getTimeSelect() {
			return timeSelect;
		}

		public void setTimeSelect(String timeSelect) {
			this.timeSelect = timeSelect;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getGroupSelect() {
			return groupSelect;
		}

		public void setGroupSelect(String groupSelect) {
			this.groupSelect = groupSelect;
		}

		public String getTimeInterval() {
			return timeInterval;
		}

		public void setTimeInterval(String timeInterval) {
			this.timeInterval = timeInterval;
		}

		public String getStatisticType() {
			return statisticType;
		}

		public void setStatisticType(String statisticType) {
			this.statisticType = statisticType;
		}
		
}
