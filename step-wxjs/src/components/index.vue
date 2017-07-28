<template>
  <div id="app">
    <div class="header flex center">
      <p>为“健康”而挑战</p>
      <div class="header__content">
        <p>当前<b class="header__content--color">{{ number }}</b>人参与</p>
      </div>
    </div>

    <div class="theme">
      <img :src="theme" />
    </div>

    <div class="public">

      <p>赌你做不到不妨来挑战</p>

      <div class="public__item flex center">
        <div class="public__wrap">
          <p class="public__title">自律派早起</p>
          <p class="public__content">15068人已坚持360天以上</p>
        </div>
      </div>

      <div class="public__item flex center">
        <div class="public__wrap">
          <p class="public__title">动起来更健康</p>
          <p class="public__content">1555人已坚持120天以上</p>
        </div>
      </div>

      <div class="public__item flex center">
        <div class="public__wrap">
          <p class="public__title">轻松享瘦挑战</p>
          <p class="public__content">150人已减重10公斤</p>
        </div>
      </div>

    </div>

    <div class="private">

      <p></p>

      <div class="private__wrap">
        <img class="private__img" src="rs/img/health.jpg" alt="">
        <div class="private__content flex center">
          <div>
            <p class="public__title">自律派早起挑战</p>
            <p class="public__content">15068人已坚持360天以上</p>
            <p class="public__content">当前30243人参与100230元奖励</p>
          </div>
        </div>
      </div>

      <div class="private__wrap">
        <img class="private__img" src="rs/img/health.jpg" alt="">
        <div class="private__content flex center">
          <div>
            <p class="public__title">动起来更健康</p>
            <p class="public__content">1555人已坚持120天以上</p>
            <p class="public__content">当前30586人参与1002300元奖励</p>
          </div>
        </div>
      </div>

      <div class="private__wrap">
        <img class="private__img" src="rs/img/health.jpg" alt="">
        <div class="private__content flex center">
          <div>
            <p class="public__title">轻松减挑战</p>
            <p class="public__content">150人已减重10公斤</p>
            <p class="public__content">当前3058人参与10023元奖励</p>
          </div>
        </div>
      </div>

    </div>

    <div class="button">
      <x-button type="primary" link="/challenge">发起挑战 和亲友们来比拼</x-button>
    </div>
  </div>

</template>

<script>
import { XButton } from 'vux'

export default {
  components: {
    XButton
  },
  data () {
    return {
      number: 1000,
      theme: 'rs/img/health.jpg'
    }
  },
  created () {
    this.$store.state.pageState.isLoading = false

    this.loadData()
    // this.fetchData()
  },
  mounted () {
    setTimeout(() => {
      document.body.scrollTop = 0
    }, 100)
  },
  methods: {
    // 获取挑战列表页数据
    fetchData () {
      // this.$http.get('step/rs/js/category.json').then((e) => {
      //   // console.info(this.$store.state.vux.isLoading)
      //   this.$store.state.pageState.isLoading = false
      // }, (e) => {
      //   console.info(e)
      // })
    },
    loadData: function () {
      this.$axios.get('wx/activity/index')

      this.$axios.get('wx/jsapi_ticket?url=' + encodeURIComponent(window.location.href.split('#')[0])).then(e => {
        // console.info('e', e)
        e.data.jsApiList = ['onMenuShareTimeline', 'onMenuShareAppMessage']
        this.$wechat.config(e.data)
        // console.info(this.$wechat)
        this.$wechat.ready(() => {
          // console.log('wechat ready')
          // 分享给朋友
          this.$wechat.onMenuShareAppMessage({
            title: '脚印-好友',
            desc: '给朋友分享健康',
            link: 'https://app.happyrun.cn/step/#/',
            imgUrl: 'https://static.vux.li/logo_520.png'
          })
          // 分享到朋友圈
          this.$wechat.onMenuShareTimeline({
            title: '脚印-朋友圈',
            link: 'https://www.mi.com/',
            imgUrl: 'https://static.vux.li/logo_520.png'
          })
        })
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss">
/* index.vue */
@import "rs/scss/index.scss";
@import "rs/scss/global.scss";
@import "rs/scss/flexbox.scss";
</style>
