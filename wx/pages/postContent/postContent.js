// pages/postContent/postContent.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    stockArray:[]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {  //获取展示公告的内容
    var that = this
    if (options.hasOwnProperty('id')&&options.hasOwnProperty('content')) {
        
      var array = new Array();
        var info = {};
        info["id"] = options.id;
        info["content"] = options.content;
        array.push(info);
        that.setData({
          stockArray: array
        });
    }
    setInterval(()=>{
      that.setData({
        stockArray: that.data.stockArray
      });

    },5000)

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})