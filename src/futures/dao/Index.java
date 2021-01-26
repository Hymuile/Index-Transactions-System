package futures.dao;

public class Index{
	//数据库字段
	private String id;				// 记录ID
	private String indexId;			// 期货指数ID
	private String indexName;		// 期货指数名称
	private String latestPrice;		// 期货指数最新价格
	private Double changeAmount;	// 期货涨跌额
	private String changeRate; 		// 期货涨跌幅度
	private String priceYesterday;	// 昨收
	private String priceToday;		// 今开
	private String highestPrice;	// 最高价格
	private String lowestPrice;		// 最低价格
	private String buyPrice;		// 买入
	private String sellPrice;		// 卖出
	private String inventory;		//持仓量
	
	
	private String user_id;
	private String user_balance;
	private String order_number;
	
	
	private String indexValue;	//
	private String upDown;		//	
	private String riseFall;	//
	private String quantity;	//
	private String price;		// 
	private String creatorId;
	private String creator;
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return user_id;
	}
	public void setUserId(String userId) {
		this.user_id = userId;
	}
	

	public String getUserBalance() {
		return user_balance;
	}
	public void setUserBalance(String Balance) {
		this.user_balance = Balance;
	}
	public String getOrderNumber() {
		return order_number;
	}
	public void setOrderNumber(String number) {
		this.order_number = number;
	}
	
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(String rate) {
		this.changeRate = rate;
	}
	
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getLatestPrice(){
		return latestPrice;
	}
	public void setLatestPrice(String price){
		this.latestPrice = price;
	}
	public Double getChangeAmount(){
		return changeAmount;
	}
	public void setChangeAmount(Double amount){
		this.changeAmount = amount;
	}
	public String getPriceYesterday(){
		return priceYesterday;
	}
	public void setPriceYesterdat(String price){
		this.priceYesterday = price;
	}
	public String getPriceToday(){
		return priceToday;
	}
	public void setPriceToday(String price){
		this.priceToday = price;
	}
	public String getHighestPrice(){
		return highestPrice;
	}
	public void setHighestPrice(String price){
		this.highestPrice = price;
	}
	public String getLowestPrice(){
		return lowestPrice;
	}
	public void setLowestPrice(String price){
		this.lowestPrice = price;
	}
	public String getBuyPrice(){
		return buyPrice;
	}
	public void setBuyPrice(String price){
		this.buyPrice = price;
	}
	public String getSellPrice(){
		return sellPrice;
	}
	public void setSellPrice(String price){
		this.sellPrice = price;
	}
	public String getInventory(){
		return inventory;
	}
	public void setInventory(String Inventory){
		this.inventory = Inventory;
	}
	
	public String getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public String getRiseFall() {
		return riseFall;
	}

	public void setRiseFall(String riseFall) {
		this.riseFall = riseFall;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
