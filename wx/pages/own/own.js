// pages/own/own.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indexId:'',
    stockArray:[],
    indexName:'',
    latestPrice:'',
    number:''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {  //初始化开仓界面，包括期货详情和开仓操作
    if (options.hasOwnProperty('indexName')&&options.hasOwnProperty('latestPrice')&&options.hasOwnProperty('id')&&options.hasOwnProperty('indexId')) {
      var that=this;
      wx.request({
      url:"https://www.ylxteach.net/XM09/wechat/get_index_data.jsp",
      success:function(res){
        var list = res.data.aaData;
        var length = list.length;
        var array = new Array();
        var info = {};
        info["latestPrice"] = list[options.id-1][2];
        info["priceYes"] = list[options.id-1][5];
        info["priceTo"] = list[options.id-1][6];
        info["high"] = list[options.id-1][7];
        info["low"] = list[options.id-1][8];
        info["indexName"] = list[options.id-1][1];
        info["buy"] = list[options.id-1][9];
        info["sell"] = list[options.id-1][10];
        info["have"] = list[options.id-1][11];
        info["id"] = options.id;
        info["indexId"] = options.indexId;
        array.push(info);
        that.setData({
          stockArray: array
        });
    }
    })
      this.setData({
        indexId: options.indexId,
        indexName: options.indexName,
        latestPrice: options.latestPrice
      })
      setInterval(()=>{
        that.setData({
          stockArray: that.data.stockArray
        });
  
      },5000)
      //that.goK(options.id);
    }

  },
  numInput:function(e){  //获取要开仓的数量
    this.setData({
      number:e.detail.value
    });
  },

  submit:function(){  //提交结果
    let that=this;
    wx.request({
      url:'https://www.ylxteach.net/XM09/BuyDao',
      method:"POST",
      data:{
       index_name:that.data.indexName,
       latest_price:that.data.latestPrice,
       hold:that.data.number
      },
      header:{
       "Content-Type":"application/x-www-form-urlencoded;charset=utf-8"
      },
      success:function(res){
        console.log(res.data);
        wx.showToast({
          title: '提交成功',
          icon:"success",
          duration:2000
        })
        wx.switchTab({
         url: '../index/index'
       })
      }
    })
    
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