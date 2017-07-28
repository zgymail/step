<template>
  <div>
    <div v-if="showdefaultImage">
      <img class="logo" :src="defaultImage">
      <x-button type="primary"  @click.native="clickData()" style="width: 20%;margin-top: 1rem;">拍照</x-button>
    </div>

    <img v-if="!showdefaultImage" :src="base64">

    <box gap=".5rem .3rem">
      <flexbox v-if="!showdefaultImage">
        <flexbox-item>
          <x-button type="warn" @click.native="cancel">重拍</x-button>
        </flexbox-item>
        <flexbox-item>
          <x-button type="primary" @click.native="setImage">确定</x-button>
        </flexbox-item>
      </flexbox>
    </box>
  </div>
</template>

<script>
import {XButton, Flexbox, FlexboxItem, Box} from 'vux'

export default {
  components: {
    XButton,
    Flexbox,
    FlexboxItem,
    Box
  },
  data () {
    return {
      showdefaultImage: true,
      defaultImage: '',
      base64: ''
    }
  },
  created () {
    this.$store.state.pageState.isLoading = false
    this.getImage()
    this.fetchData()
  },
  methods: {
    cancel () {
      this.clickData()
    },
    clickData () {
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
              // 安卓设备
              if (navigator.userAgent.match(/Android/i)) {
                //
                this.base64 = 'data:image/jgp;base64,' + res.localData
              } else {
                //
                this.base64 = res.localData
              }
              // 隐藏默认图片
              this.showdefaultImage = false
            }
          })
        }
      })
    },
    fetchData () {
      this.$axios.get('wx/jsapi_ticket?url=' + encodeURIComponent(window.location.href.split('#')[0])).then(e => {
        // 需要获取的权限
        e.data.jsApiList = ['chooseImage']
        // 注入权限验证配置，是一个异步操作
        this.$wechat.config(e.data)
      })
    },
    // 下载图片
    getImage () {
      this.$axios.get(`wx/activity/publish_prep_modify_introduce?activityPublishId=${this.$route.query.id}`).then(e => {
        console.info('introduceImage', e)
        // 获取 默认的图片
        this.defaultImage = e.data.introduceImage
      })
    },
    // 上传图片
    setImage () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 开始上传
      this.$axios.post('aliyun/upload', {extName: 'jpeg', imageBase64: this.base64.split(';base64,')[1]}).then(e => {
        // 获取 图片的路径
        let stringValue = e.data.stringValue
        // 提交图片
        this.$axios.post('wx/activity/publish_modify_introduce', {activityPublishId: this.$route.query.id, introduceImage: stringValue}).then(e => {
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
            // do something
          }
        })
      })
    }
  }
}
</script>

<style scoped>
img {
  display: block;
  width: 80%;
  margin: .8rem auto 0 auto;
}
</style>
