// pages/home/home.js
Page({

  /**
   * 个人中心的界面
   */
  data: {
    stockArray:[],
    name:'',
    url:''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    setInterval(()=>{     //每1s刷新一次
      that.get_image();
      that.get_weather();
    },1000)

  },
  get_image:function(){     //展示登录用户的信息
    var that = this
    wx.request({
      url:"https://www.ylxteach.net/XM09/wechat/get_login.jsp",
      success:function(res){
        var list = res.data.aaData;
        var length = list.length;  
        that.setData({
          name:list[0][0],     //用户名
          url:'http://www.ylxteach.net'+list[0][1]   //头像url
        });
    }
    })

  },
  get_weather:function(){    //获取天气（通过处理json数据的方法）
    var that = this
    
    wx.request({
      url: 'https://devapi.qweather.com/v7/weather/now?location=101270101&key=dfc6208de6c64457b59e140ee5a0208d&gzip=n',//json数据地址
      headers: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
    
            
          var array = new Array();
          var info = {};
          info["temp"] = res.data.now.temp;
          info["text"] = res.data.now.text;
          info["vis"] = res.data.now.vis;
          array.push(info);
          that.setData({
            stockArray: array
          });
      }
    })

  },
  register:function(){
    wx.navigateTo({    //跳转
      url: '../upload_image/image'
    })
  },
  login:function(){
    wx.navigateTo({
      url: '../login/login'
    })

  },
  post:function(){
    wx.navigateTo({
      url: '../pos/pos'
    })

  },
  news:function(){
    wx.navigateTo({
      url: '../news/news'
    })

  },
  weather:function(){
    wx.navigateTo({
      url: '../weather/weather'
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