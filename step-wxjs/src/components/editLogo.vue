<template>
  <div>
    <div v-if="showDefaultLogo">
      <img class="logo" :src="defaultLogo">
      <x-button type="primary"  @click.native="clickData()" style="width: 20%;margin-top: 1rem;">拍照</x-button>
    </div>


    <box gap=".5rem .3rem">
      <flexbox v-if="update">
        <flexbox-item>
          <x-button type="warn" @click.native="cancel">重拍</x-button>
        </flexbox-item>
        <flexbox-item>
          <x-button type="primary" @click.native="upload">确定</x-button>
        </flexbox-item>
      </flexbox>
    </box>

    <div class="warp">
      <div class="warp2">
        <div class="mark"></div>
        <canvas id="canvas1"></canvas>
      </div>
    </div>
    <canvas id="canvas2" style="display: none;"></canvas>
    <canvas id="canvas3" style="display: none;" width="200" height="200"></canvas>
    <alert v-model="show" title="错误">修改失败</alert>
  </div>
</template>

<script>
import {XButton, Flexbox, FlexboxItem, Box, Alert} from 'vux'

export default {
  components: {
    XButton,
    Flexbox,
    FlexboxItem,
    Box,
    Alert
  },
  data () {
    return {
      showDefaultLogo: true,
      update: false,
      str: 'data:image/jgp;base64,',
      canvas1: null,
      canvas2: null,
      canvas3: null,
      cxt1: null,
      cxt2: null,
      cxt3: null,
      oMark: null,
      img: null,
      imgWidth: 0,
      imgHeight: 0,
      percent: 1,
      screenWidth: 0,
      isVertical: false,
      isHorizontal: false,
      touchs: {
        startx: 0,
        starty: 0
      },
      canvasWidth: 0,
      canvasHeight: 0,
      base64: '',
      defaultLogo: '',
      show: false
    }
  },
  created () {
    // console.info('ID', this.$route.query)
    this.$store.state.pageState.isLoading = false

    this.getLogo()

    this.fetchData()
  },
  mounted () {
    // 获取 canvas元素
    this.canvas1 = window.document.querySelector('#canvas1')
    this.canvas2 = window.document.querySelector('#canvas2')
    this.canvas3 = window.document.querySelector('#canvas3')
    // 获取 CanvasRenderingContext2D对象
    this.cxt1 = this.canvas1.getContext('2d')
    this.cxt2 = this.canvas2.getContext('2d')
    this.cxt3 = this.canvas3.getContext('2d')
    // 获取 oMark元素
    this.oMark = window.document.querySelector('.mark')
    // 实例化 Image对象，等同于 document.createElement('img')
    this.img = new Image()
    // 获取 屏幕的宽度
    this.screenWidth = window.screen.width
    // 绑定 touchstart事件
    this.oMark.addEventListener('touchstart', this.markTouchStart, false)
    // 绑定 touchmove事件
    this.oMark.addEventListener('touchmove', this.markTouchMove, false)
    // 绑定 touchend事件
    this.oMark.addEventListener('touchend', this.markTouchEnd, false)
  },
  methods: {
    // 获取头像
    getLogo () {
      this.$axios.get(`wx/activity/publish_prep_modify_logo?activityPublishId=${this.$route.query.id}`).then(e => {
        // 获取 默认的头像
        this.defaultLogo = e.data.logo
      })
    },
    // 上传头像
    upload () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 上传图片
      this.$axios.post('aliyun/upload', {extName: 'jpeg', imageBase64: this.base64.split(';base64,')[1]}).then(e => {
        // 获取 图片的路径
        let stringValue = e.data.stringValue
        // 提交图片
        this.$axios.post('wx/activity/publish_modify_logo', {activityPublishId: this.$route.query.id, logo: stringValue}).then(e => {
          // 修改成功
          if (e.data.success) {
            setTimeout(() => {
              // 隐藏加载框
              this.$store.state.pageState.isLoading = false
              // 返回 上一页
              this.$router.go(-1)
            }, 500)
          // 修改失败
          } else {
            // 隐藏加载框
            this.$store.state.pageState.isLoading = false
            // 显示弹出框
            this.show = true
          }
        }).catch(e => {
          // 隐藏加载框
          this.$store.state.pageState.isLoading = false
          // 显示弹出框
          this.show = true
        })
      }).catch(e => {
        // 隐藏加载框
        this.$store.state.pageState.isLoading = false
        // 显示弹出框
        this.show = true
      })
    },
    cancel () {
      this.oMark.style.left = 0
      this.oMark.style.top = 0
      this.isVertical = false
      this.isHorizontal = false
      this.update = false
      this.clickData()
    },
    fetchData () {
      this.$axios.get('wx/jsapi_ticket?url=' + encodeURIComponent(window.location.href.split('#')[0])).then(e => {
        // 需要获取的权限
        e.data.jsApiList = ['chooseImage']
        // 注入权限验证配置，是一个异步操作
        this.$wechat.config(e.data)
      })
    },
    clickData () {
      this.showDefaultLogo = false
      // 拍照或从手机相册中选图
      this.$wechat.chooseImage({
        // 能够选中的图片的数量
        count: 1,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        // 成功后的回调
        success: res => {
          // 本地图片的ID数组
          let localIds = res.localIds
          // 预览图片
          this.$wechat.getLocalImgData({
            // 本地图片的ID
            localId: localIds[0],
            // 成功后的回调
            success: res => {
              if (navigator.userAgent.match(/Android/i)) {
                this.img.src = this.str + res.localData
              } else {
                // 将Base64数据赋给src属性
                this.img.src = res.localData
              }
              // 图片加载完后的回调
              this.img.onload = () => {
                // 获取 图片的宽度
                this.imgWidth = this.img.width
                // 获取 图片的高度
                this.imgHeight = this.img.height
                // 获取 图片宽度与屏幕宽度的比例
                this.percent = this.imgWidth / this.screenWidth
                // 图片是竖图
                if (Math.floor(this.imgWidth / this.imgHeight) === 0) {
                  // 标记为竖图
                  this.isVertical = true
                  // 设置 选区的宽度和高度
                  this.oMark.style.width = this.oMark.style.height = this.screenWidth + 'px'
                // 图片是横图
                } else if (Math.floor(this.imgWidth / this.imgHeight) > 0) {
                  // 标记为横图
                  this.isHorizontal = true
                  // 设置 选区的宽度和高度
                  this.oMark.style.width = this.oMark.style.height = this.imgHeight / this.percent + 'px'
                }
                // 设置 画布的宽度
                this.canvas1.width = this.canvasWidth = this.imgWidth / this.percent
                // 设置 画布的高度
                this.canvas1.height = this.canvasHeight = this.imgHeight / this.percent
                // 将图片绘制到画布上
                this.cxt1.drawImage(this.img, 0, 0, this.imgWidth, this.imgHeight, 0, 0, this.imgWidth / this.percent, this.imgHeight / this.percent)
                // 获得 Base64编码的图片
                this.getBase64()
                // 显示上传按钮
                this.update = true
              }
            }
          })
        },
        // 取消时的回调
        cancel: () => {
          // 返回 上一页
          this.$router.go(-1)
        }
      })
    },
    getBase64 () {
      // oMark元素在画布中 X轴方向的偏移量
      let srcX = this.oMark.offsetLeft
      // oMark元素在画布中 Y轴方向的偏移量
      let srcY = this.oMark.offsetTop
      // oMark元素的宽度
      let markWidth = this.oMark.offsetWidth
      // oMark元素的高度
      let markHeight = this.oMark.offsetHeight
      // 设置 画布的宽度
      this.canvas2.width = markWidth
      // 设置 画布的高度
      this.canvas2.height = markHeight
      // 获取 图像数据 (复制画布上指定矩形的像素数据，包含像素值的数组的ImageData对象)
      let imageData = this.cxt1.getImageData(srcX, srcY, markWidth, markHeight)
      // 将图像数据（从指定的ImageData对象）写入画布
      this.cxt2.putImageData(imageData, 0, 0, 0, 0, this.canvas2.width, this.canvas2.height)
      // 将画布转换为Base64数据
      let img2 = this.canvas2.toDataURL('image/jpeg', 1.0)
      // 实例化Image对象
      let img3 = new Image()
      // 将Base64数据赋给src属性
      img3.src = img2
      // 图片加载完后的回调
      img3.onload = () => {
        // 将图片绘制到画布上
        this.cxt3.drawImage(img3, 0, 0, 200, 200)
        // 将画布转换为Base64数据
        this.base64 = this.canvas3.toDataURL('image/jpeg', 1.0)
      }
    },
    markTouchStart (e) {
      e.preventDefault()

      if (this.isVertical) this.touchs.starty = e.touches[0].pageY - +getComputedStyle(this.oMark, '').getPropertyValue('top').split('px')[0]

      if (this.isHorizontal) this.touchs.startx = e.touches[0].pageX - +getComputedStyle(this.oMark, '').getPropertyValue('left').split('px')[0]
    },
    markTouchMove (e) {
      if (this.isVertical) this.oMark.style.top = (e.touches[0].clientY - this.touchs.starty) + 'px'

      if (this.isHorizontal) this.oMark.style.left = (e.touches[0].clientX - this.touchs.startx) + 'px'

      this.getBase64()
    },
    markTouchEnd (e) {
      if (this.isVertical) {
        // 当选区离开画布上方
        if (+getComputedStyle(this.oMark, '').getPropertyValue('top').split('px')[0] < 0) {
          // 将选区重置至画布的顶部
          this.oMark.style.top = 0
          setTimeout(() => {
            this.getBase64()
          }, 100)
        }
        // 当选区离开画布下方
        if (+getComputedStyle(this.oMark, '').getPropertyValue('top').split('px')[0] > (this.canvasHeight - this.screenWidth)) {
          // 将选区重置至画布的底部
          this.oMark.style.top = (this.canvasHeight - this.screenWidth) + 'px'
          setTimeout(() => {
            this.getBase64()
          }, 100)
        }
      }

      if (this.isHorizontal) {
        // 当选区离开画布左侧
        if (+getComputedStyle(this.oMark, '').getPropertyValue('left').split('px')[0] < 0) {
          // 将选区重置至画布的左侧
          this.oMark.style.left = 0
          setTimeout(() => {
            this.getBase64()
          }, 100)
        }
        // 当选区离开画布右侧
        if (+getComputedStyle(this.oMark, '').getPropertyValue('left').split('px')[0] > (this.screenWidth - this.canvasHeight)) {
          // 将选区重置至画布的右侧
          this.oMark.style.left = (this.screenWidth - this.canvasHeight) + 'px'
          setTimeout(() => {
            this.getBase64()
          }, 100)
        }
      }
    }
  }
}
</script>

<style scoped>
#canvas1 {
  display: block;
  z-index: 1
}
#canvas2 {
  display: none;
}
#canvas3 {
  display: none;
}
.mark {
  position:absolute;
  left:0;
  top: 1px;
  background: #fff;
  opacity: 0.5;
}
.warp {

}
.warp2 {
  position: absolute;
}
.logo {
  display: block;
  width: 4rem;
  height: 4rem;
  border-radius: 10px;
  margin: 1rem auto 0 auto;

}
</style>
