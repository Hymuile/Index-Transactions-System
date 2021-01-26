// pages/pos/pos.js
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
  onLoad: function (options) {  //用列表展示公告所有标题
    var that=this;
    wx.request({
      url:"https://www.ylxteach.net/XM09/wechat/get_post.jsp",
      success:function(res){
        var list = res.data.aaData;
        var length = list.length;
        var array = new Array();
        for(let i=0;i<length;i++){
          var info = {};
          info["id"] = i+1;
          info["title"] = list[i][0];
          info["content"] = list[i][1];
          array.push(info);
        }
        that.setData({
          stockArray: array
        });
    }
    })
    setInterval(()=>{
      that.setData({
        stockArray: that.data.stockArray
      });
    },5000)
  },

  onShowStockDetail: function (e) {  //点击标题展示对应的公告内容
    console.log("onShowStockDetail", e)
    if (e.detail.x < 315) {
        var stock = e.currentTarget.dataset.stock
        wx.navigateTo({
          url: `../postContent/postContent?id=${stock.id}&content=${stock.content}` 
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