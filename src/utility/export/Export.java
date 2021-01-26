package utility.export;

import org.json.JSONObject;

public class Export{
	private int id;
	private String sessionId;
	private String dbName;
	private String userId;
	private String userName;
	private String filePath;
	private String fileName;
	private String fileUrl;
	private String fileSize;
	private String exportPercent;
	private String exportType;
	private String exportStatus;
	private String downloadCount;
	private String limitTime;
	private String creator;
	private String createTime;
	private String tempFilePath;
	private String zipFileName;
	private String dataFileName;
	private String currentQuerySetup;
	private JSONObject jsonObj;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getExportPercent() {
		return exportPercent;
	}
	public void setExportPercent(String exportPercent) {
		this.exportPercent = exportPercent;
	}
	public String getExportType() {
		return exportType;
	}
	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	public String getExportStatus() {
		return exportStatus;
	}
	public void setExportStatus(String exportStatus) {
		this.exportStatus = exportStatus;
	}
	public String getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(String downloadCount) {
		this.downloadCount = downloadCount;
	}
	public String getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
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
	public String getTempFilePath() {
		return tempFilePath;
	}
	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}
	public String getZipFileName() {
		return zipFileName;
	}
	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}
	public String getDataFileName() {
		return dataFileName;
	}
	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}
	public JSONObject getJsonObj() {
		return jsonObj;
	}
	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}
	public String getCurrentQuerySetup() {
		return currentQuerySetup;
	}
	public void setCurrentQuerySetup(String currentQuerySetup) {
		this.currentQuerySetup = currentQuerySetup;
	}
}
