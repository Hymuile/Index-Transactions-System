// pages/kanpan/kanpan.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    indexId:'',
    stockArray:[]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {   //展示期货的详情信息
    if (options.hasOwnProperty('id')&&options.hasOwnProperty('indexId')) {
      //console.log(options.indexId)
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
      })
      setInterval(()=>{
        that.setData({
          stockArray: that.data.stockArray
        });
  
      },5000)
      //that.goK(options.id);
    }
    
  },
  goFen:function(e)   //分时图
  {
    console.log("goFen", e)
    if (e.detail.x < 315) {
        var stock = e.currentTarget.dataset.stock
        //console.log(stock.indexId);
        wx.navigateTo({
          url: `../pic/pic?indexId=${stock.indexId}` 
        })
    }
  },
  goK:function(e)  //日k图
  {
    console.log("goK", e)
    if (e.detail.x < 315) {
        var stock = e.currentTarget.dataset.stock
        //console.log(stock.indexId);
        wx.navigateTo({
          url: `../kline/kline?indexId=${stock.indexId}` 
        })
    }
  },
  goM:function(e)   //月k图
  {
    console.log("goM", e)
    if (e.detail.x < 315) {
        var stock = e.currentTarget.dataset.stock
        //console.log(stock.indexId);
        wx.navigateTo({
          url: `../month/month?indexId=${stock.indexId}` 
        })
    }
  },
  goY:function(e)  //年k图
  {
    console.log("goY", e)
    if (e.detail.x < 315) {
        var stock = e.currentTarget.dataset.stock
        //console.log(stock.indexId);
        wx.navigateTo({
          url: `../year/year?indexId=${stock.indexId}` 
        })
    }
  },
  own:function(e)  //开仓
  {
    console.log("own", e)
    if (e.detail.x < 315) {
        var stock = e.currentTarget.dataset.stock
        //console.log(stock.indexId);
        wx.navigateTo({
          url: `../own/own?indexName=${stock.indexName}&latestPrice=${stock.latestPrice}&id=${stock.id}&indexId=${stock.indexId}` 
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