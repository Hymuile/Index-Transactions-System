//index.js
//行情信息列表
const app = getApp()
var Util = require('../../utils/util.js')

//let viewmodel = require('viewmodel.js')
Page({
  data:{
    stockArray:[],
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {   //初始化，将数据库中的行情信息以列表的形式展示
    var that=this;
    wx.request({
      url:"https://www.ylxteach.net/XM09/wechat/get_index_data.jsp",
      success:function(res){
        var list = res.data.aaData;
        var length = list.length;
        var array = new Array();
        for(let i=0;i<length;i++){
          var info = {};
          info["id"] = i+1;
          info["indexId"] = list[i][0];
          info["indexName"] = list[i][1];
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
  onShowStockDetail: function (e) {   //点击对应期货跳转新界面展示其详情信息
    console.log("onShowStockDetail", e)
    if (e.detail.x < 315) {
        var stock = e.currentTarget.dataset.stock
        wx.navigateTo({
          url: `../kanpan/kanpan?id=${stock.id}&indexId=${stock.indexId}` 
        })
    }
  },
    // 显示搜索股票
    onMoreInf: function (e) {
      wx.navigateTo({
        url: '../kanpan/kanpan'
      })
    },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
