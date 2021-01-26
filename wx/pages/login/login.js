// pages/login/login.js
Page({

  /**
   * 登陆界面
   */
  data: {
    Tel:'',
    Name:'',
    Url:''

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    

  },
  telInput:function(e){  //获取输入的电话号码
    this.setData({
      Tel:e.detail.value
    });
  },
  confirm:function(){
    //输入电话，获得对应的名字和图片url，并验证是否正确
    let that=this;
    wx.request({
      url:'https://www.ylxteach.net/XM09/TelDao',
      method:"POST",
      data:{
       tel:that.data.Tel,
      },
      header:{
       "Content-Type":"application/x-www-form-urlencoded;charset=utf-8"
      },
      success:function(res){
        console.log(res.data.aaData[0][1]);
        that.setData({
          Name: res.data.aaData[0][0],
          Url:res.data.aaData[0][1]

        })
        wx.showToast({
          title: '提交成功',
          icon:"success",
          duration:2000
        })
      }
    })
    
  },
  submit:function(){  //提交结果。验证通过后提交即可登录成功
    let that=this;
    wx.request({
      url:'https://www.ylxteach.net/XM09/LoginDao',
      method:"POST",
      data:{
       tel:that.data.Tel,
       name:that.data.Name,
       url:that.data.Url
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
          url: '../home/home'
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