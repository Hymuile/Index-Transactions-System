// pages/upload/upload.js
Page({
 
  /**
   * 注册界面（上传头像）
   */
  data: {
    imgs: [],
    imageId:"",
    wechatUserName:"",
    attachmentFileUrl:"",

    placeholder: '请选择',
    multiIndex: [0, 0, 0],
    date: '2020-11-30',
    time: '12:00',
    region: ['四川省', '成都市', '双流区'],
    customItem: '全部'
  },
  // 上传图片
  chooseImg: function (e) {
    console.log("进入chooseImg");
    var that = this;
    var imgs = this.data.imgs;
    if (imgs.length >= 9) {
      this.setData({
        lenMore: 1
      });
      setTimeout(function () {
        that.setData({
          lenMore: 0
        });
      }, 2500);
      return false;
    }
    wx.chooseImage({
      // count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        var imgs = that.data.imgs;
        // console.log(tempFilePaths + '----');
        for (var i = 0; i < tempFilePaths.length; i++) {
          if (imgs.length >= 9) {
            that.setData({
              imgs: imgs
            });
            return false;
          } else {
            imgs.push(tempFilePaths[i]);
          }
        }
        // console.log(imgs);
        that.setData({
          imgs: imgs
        });
        that.uploadWXFiles();
      }
    });
  },
  uploadWXFiles:function(){
    var that = this;
    console.log(this.data.imgs[0]);
    wx.uploadFile({
      filePath: this.data.imgs[0],
      name: 'file',
      url: 'https://www.ylxteach.net/XM09/file_receiver',
      header:{
        'content-type':'multipart/form-data'
      },
      formData:null,
      success:function(res){
        console.log(res.data);
        var data=JSON.parse(res.data);
        var imgs=[];
        console.log("var imgs=[]");
        imgs.push("https://www.ylxteach.net"+data.attachment_file_url);
        that.setData({
          imgs:imgs,
          attachmentFileUrl:data.attachment_file_url
        });
        console.log("+++attachment_file_url="+data.attachment_file_url)
      }
    })
  },
  // 删除图片
  deleteImg: function (e) {
    var imgs = this.data.imgs;
    var index = e.currentTarget.dataset.index;
    imgs.splice(index, 1);
    this.setData({
      imgs: imgs
    });
  },
  // 预览图片
  previewImg: function (e) {
    //获取当前图片的下标
    var index = e.currentTarget.dataset.index;
    //所有图片
    var imgs = this.data.imgs;
    wx.previewImage({
      //当前显示图片
      current: imgs[index],
      //所有图片
      urls: imgs
    })
  },
 
 
 
  bindPickerChange(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  clearFont() {
    this.setData({
      placeholder: ''
    })
  },
 
  bindRegionChange(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      region: e.detail.value
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
 
  },
  submitRegister:function(){  //提交，注册
     let that=this;
     console.log("submitRegister/that.data.attachment_file_url="+that.data.attachmentFileUrl);
     wx.request({
       url:'https://www.ylxteach.net/XM09/file_dao',
       method:"POST",
       data:{
        image_id:that.data.imageId,
        wechat_user_name:that.data.wechatUserName,
        attachment_file_url:that.data.attachmentFileUrl
       },
       header:{
        "Content-Type":"application/x-www-form-urlencoded"
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
  telInput:function(e){  //获取电话号码
    this.setData({
      imageId:e.detail.value
    });
  },
  nameInput:function(e){  //获取用户名
    this.setData({
      wechatUserName:e.detail.value
    });
  }
})