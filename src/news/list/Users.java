package news.list;

public class Users {
	//数据库字段
		private String id;
		private String createtime;
		private String title;
		private String descreption;
		private String picurl;
		private String url;

		
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

		public String getCreatetime() {
			return createtime;
		}

		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescreption() {
			return descreption;
		}

		public void setDescreption(String descreption) {
			this.descreption = descreption;
		}

		public String getPicurl() {
			return picurl;
		}

		public void setPicurl(String picurl) {
			this.picurl = picurl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
}
