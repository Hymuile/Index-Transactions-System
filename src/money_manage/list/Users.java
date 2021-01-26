package money_manage.list;

public class Users {
	//数据库字段
		private String id;
		private String city;
		private String obstime;
		private String temp;
		private String icon;
		private String text;
		private String winddir;
		private String windscale;
		private String windspeed;
		private String vis;
		
		private String user_email;
		private String trading_money;
		private String op_type;
		//排序条件
		private String sortIndex;
		private String orderBy;
		
		private String name;
		private String role;
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
		
		public String getId() {
			return id;
		}
		public void setId(String id){
			this.id = id;
		}
		
		public String getCity(){
			return city;
		}
		public void setCity(String city){
			this.city = city;
		}
		
		public String getObstime(){
			return obstime;
		}
		public void setObstime(String obstime){
			this.obstime = obstime;
		}
		
		public String getTemp(){
			return temp;
		}
		public void setTemp(String temp){
			this.temp= temp;
		}
		
		public String getIcon(){
			return icon;
		}
		public void setIcon(String icon){
			this.icon = icon;
		}
		
		public String getText(){
			return text;
		}
		public void setText(String text){
			this.text = text;
		}
		
		public String getWinddir(){
			return winddir;
		}
		public void setWinddir(String winddir){
			this.winddir = winddir;
		}
		
		public String getWindscale(){
			return windscale;
		}
		public void setWindscale(String windscale){
			this.windscale = windscale;
		}
		
		public String getWindspeed(){
			return windspeed;
		}
		public void setWindspeed(String windspeed){
			this.windspeed = windspeed;
		}
		
		public String getVis(){
			return vis;
		}
		public void setVis(String vis){
			this.vis = vis;
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

		public String get_user_email() {
			return user_email;
		}

		public void set_user_email(String user_email) {
			this.user_email = user_email;
		}

		public String getOrderBy() {
			return orderBy;
		}

		public void setOrderBy(String orderBy) {
			this.orderBy = orderBy;
		}

		public String getSortIndex() {
			return sortIndex;
		}

		public void setSortIndex(String sortIndex) {
			this.sortIndex = sortIndex;
		}

		public String get_trading_money() {
			return trading_money;
		}

		public void set_trading_money(String trading_money) {
			this.trading_money = trading_money;
		}

		public String get_op_type() {
			return op_type;
		}

		public void set_op_type(String op_type) {
			this.op_type = op_type;
		}
		
}
