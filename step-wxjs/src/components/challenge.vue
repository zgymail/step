<template>
  <div id="app">
    <div>
      <group>
        <cell title="头像" value="value">
          <img class="face" :src="face" alt="">
        </cell>
        <x-input title="标题" placeholder="请输入标题" :value="title" :max="20"></x-input>
        <cell title="任务" :value="type" is-link @click.native="showPopup=true"></cell>
        <popup-picker title="费用" v-model="price" :data="priceList"></popup-picker>
        <cell title="开始时间" :value="startDay" is-link @click.native="setStartDay"></cell>
        <cell title="结束时间" :value="endDay" is-link @click.native="setEndDay"></cell>
        <popup-picker title="几期返还" v-model="day" :data="dayList"></popup-picker>
        <x-textarea :max="20" placeholder="请输入分享文字" :value="text"></x-textarea>
      </group>

      <div class="button">
        <x-button type="primary" show-loading disabled v-if="!loadingButton">发起挑战</x-button>
        <x-button type="primary" v-if="loadingButton" @click.native="postData">发起挑战</x-button>
      </div>
    </div>

    <popup v-model="showPopup" position="bottom" height="60%">
      <checker default-item-class="" selected-item-class="demo5-item-selected">
        <!-- <checker-item v-for="i in [1, 2, 3]" :key="i" :value="i">￥{{i*300}}</checker-item> -->
        <div class="img">
          <checker-item>
            <img src="/rs/img/1.png" alt="">
          </checker-item>
          <checker-item>
            <img src="/rs/img/2.png" alt="">
          </checker-item>
          <checker-item>
            <img src="/rs/img/3.png" alt="">
          </checker-item>
        </div>
      </checker>
    </popup>
  </div>
</template>

<script>
import { Group, Cell, Datetime, PopupPicker, XInput, XTextarea, Popup, Checker, CheckerItem, XButton } from 'vux'

export default {
  components: {
    Group,
    Cell,
    Datetime,
    PopupPicker,
    XInput,
    XTextarea,
    Popup,
    Checker,
    CheckerItem,
    XButton
  },
  data () {
    return {
      face: '/rs/img/face.png',
      popupVisible: false,
      // title: '和亲友们来比拼',
      title: '',
      price: ['100元'],
      priceList: [['10元', '50元', '100元', '200元', '500元', '1000元', '2000元', '5000元', '10000元']],
      // text: '为健康我也是拼了，不服来挑战',
      text: '',
      main: true,
      time_dialog: true,
      startDay: '2017-06-29',
      endDay: '2017-06-29',
      day: ['10期'],
      dayList: [['1期', '2期', '3期', '4期', '5期', '6期', '7期', '8期', '9期', '10期']],
      birthday: null,
      type: '自律派',
      showPopup: false,
      loadingButton: true
    }
  },
  methods: {
    setStartDay () {
      this.$vux.datetime.show({
        cancelText: '取消',
        confirmText: '确定',
        format: 'YYYY-MM-DD',
        value: '2017-06-29',
        // 点击确定后的回调
        onConfirm: (val) => {
          // 设置 默认的开始时间
          this.startDay = val
        },
        onShow () {
          // console.log('plugin show')
        },
        onHide () {
          // console.log('plugin hide')
        }
      })
    },
    setEndDay () {
      this.$vux.datetime.show({
        cancelText: '取消',
        confirmText: '确定',
        format: 'YYYY-MM-DD',
        value: '2017-06-29',
        // 点击确定后的回调
        onConfirm: (val) => {
          // 设置 默认的开始时间
          this.endDay = val
        },
        onShow () {
          // console.log('plugin show')
        },
        onHide () {
          // console.log('plugin hide')
        }
      })
    },
    // 获取挑战列表页数据
    // fetchData () {
    //   this.$http.get('/static/js/category.json').then((e) => {
    //     console.info(this.$store.state.vux.isLoading)
    //     this.$store.state.vux.isLoading = false
    //   }, (e) => {
    //     console.info(e)
    //   })
    // },
    postData () {
      this.loadingButton = false
    }
  },
  created () {
    console.info('challenge.vue')
    document.body.scrollTop = 0
    this.$store.state.vux.isLoading = false
    // this.fetchData()
  }
}
</script>

<style lang="scss" scoped>
/* challenge.vue */
@import "rs/scss/challenge.scss"
</style>
