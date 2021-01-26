// pages/month/month.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    gifUrl:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {  //展示期货对应的月k图
    if (options.hasOwnProperty('indexId')) {
      var that = this
      console.log("月k图")
      that.setData({
        gifUrl: 'http://image.sinajs.cn/newchart/v5/futures/global/mline/1m/'+options.indexId+'.gif?1275928289390'
      })
      
      
    }

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