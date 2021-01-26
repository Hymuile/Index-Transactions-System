// pages/my/my.js
Page({

  /**
   * 个人仓库
   */
  data: {
    stockArray:[],
    sellnum:'',
    havenum:'',
    indexName:'',
    myhold:''


  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {  //初始化展示个人仓库中的持有期货
    var that=this;
    wx.request({
      url:"https://www.ylxteach.net/XM09/wechat/load_menu.jsp",
      success:function(res){
        var list = res.data.aaData;
        console.log(res.data.aaData)
        var length = list.length;
        var array = new Array();
        for(let i=0;i<length;i++){
          var info = {};
          info["id"] = i+1;
          info["indexName"] = list[i][0];
          info["latestPrice"] = list[i][1];
          info["hold"] = list[i][2];
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

    },1000)
  },
  numInput:function(e){  //获取要卖出的数量
    this.setData({
      sellnum:e.detail.value
    });
  },
  nameInput:function(e){  //获取要卖出的期货名
    this.setData({
      indexName:e.detail.value
    });
  },
  sure:function(){  //确认输入，并验证是否可执行
    var that = this
    wx.request({
      url:'https://www.ylxteach.net/XM09/HoldDao',
      method:"POST",
      data:{
       index_name:that.data.indexName,
      },
      header:{
       "Content-Type":"application/x-www-form-urlencoded;charset=utf-8"
      },
      success:function(res){
        console.log("123");
        console.log(res.data.aaData[0][0])
        that.setData({
          //havenum:res.data.aaData[0][0]
          myhold:res.data.aaData[0][0]-that.data.sellnum
        })
        wx.showToast({
          title: '已确认',
          icon:"success",
          duration:2000
        })
      }
    })
    //console.log(that.data.havenum)
    //console.log(that.data.sellnum)
  },
  submit:function(){  //若验证通过，则提交，卖出成功
    let that=this;
    var have = that.data.myhold
    console.log(have)
    
    if(have>0){  //卖出后还有剩余
      console.log("have>0")
      wx.request({
        url:'https://www.ylxteach.net/XM09/SoldDao',
        method:"POST",
        data:{
         index_name:that.data.indexName,
         hold:have
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
          that.onLoad();
        }
      })
    }
    if(have==0){  //刚好卖光
      console.log("have==0")
      wx.request({
        url:'https://www.ylxteach.net/XM09/SellDao',
        method:"POST",
        data:{
         index_name:that.data.indexName
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
          that.onLoad();
        }
      })
    }
    if(have<0)  //卖出的数量大于了拥有数，无法操作
    {
      console.log("have<0")
      wx.showToast({
        title: '无法操作',
        icon:"success",
        image:'../../images/icon_optional_del.png',
        duration:2000
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