package security_user;

public class User {
	//数据库字段
	private String id;
	private String email;
	private String pwd;
	private String name;
	private String role;
	private String city;
	private String createTime;
	private String verifyCode;
	
	//传递条件查询用
	private String action;
	private String dbName;
	private String tableName="";
	public User(){
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

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}
