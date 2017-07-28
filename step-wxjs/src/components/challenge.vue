<template>
  <div id="app">
    <div class="eq">

      <router-link class="eqLogo arrow" :to="{ path: '/editLogo', query: { id: activityPublishId }}">
        <img :src="face">
      </router-link>

      <router-link class="eqTitle arrow" :to="{ path: '/editTitle', query: { id: activityPublishId }}">
        <h1>{{ title }}</h1>
      </router-link>

      <router-link class="eqIntroduce arrow flex center" :to="{ path: '/editIntroduce', query: { id: activityPublishId }}">
        <img :src="introduceImage">
      </router-link>

      <router-link class="eqTask arrow " :to="{ path: '/editTask', query: { id: activityPublishId }}">
        <p class="eqTask__name">任务</p>
        <div class="eqTask__detail flex between">
          <p>{{ taskName }}</p>
          <rater class="eqTask__rater" :value="rater" slot="value" active-color="#000" :font-size="15" :max="maxRater" disabled></rater>
        </div>
        <div class="eqTask__task">
          <div class="eqTask__item flex center" v-for="(item, index) in taskItems">
            <p>{{ item.name }}</p>
            <span>{{ item.desc ? `(${item.desc})` : '' }}</span>
          </div>
        </div>
      </router-link>

      <router-link class="eqPrice arrow" :to="{ path: '/editPrice', query: { id: activityPublishId }}">
        <p class="eqPrice__name">费用</p>
        <span class="eqPrice__detail">
          <p class="eqPrice__num">{{ expend }}</p>
          <p class="eqPrice__yuan">元</p>
        </span>
      </router-link>

      <router-link class="eqTime arrow" :to="{ path: '/editTime', query: { id: activityPublishId }}">
        <p class="eqTime__name">起止时间</p>
        <span class="eqTime__detail">
          <p class="eqTime__date">{{ startStopTime[0] + '，' }}</p>
          <p class="eqTime__clock">{{ startStopTime[1] }}</p>
        </span>
      </router-link>

      <router-link class="eqText arrow" :to="{ path: '/editText', query: { id: activityPublishId }}">
        <p class="eqText__name">分享文字</p>
        <span class="eqText_detail">
          <p class="eqText__text">{{ shareText }}</p>
          <img src="http://step.oss-cn-hangzhou.aliyuncs.com/activity/share_icon_defalut.png">
        </span>
      </router-link>

      <div class="button">
        <x-button @click.native="setData" type="primary">发起挑战</x-button>
      </div>

    </div>

  </div>
</template>

<script>
import { XButton, Rater } from 'vux'

export default {
  components: {
    XButton,
    Rater
  },
  data () {
    return {
      taskImage: 'rs/img/background.jpg',
      maxRater: 5,
      rater: 0,
      // 用户头像
      face: '',
      // 图文介绍
      introduceImage: '',
      popupVisible: false,
      // 标题
      title: '和亲友们来比拼',
      // 分享文字
      shareText: '',
      // 分享图标
      shareIcon: '',
      // 任务名称
      taskName: '',
      // showPopup: false,
      loadingButton: true,
      // 挑战ID
      activityPublishId: 0,
      // 费用
      expend: 0,
      // 时间
      startStopTime: '',
      // 任务
      taskItems: null
    }
  },
  methods: {
    // 获取挑战列表页数据
    fetchData () {
      this.$axios.get('/wx/activity/publish').then(res => {
        console.info('res', res)
        // 设置 用户头像
        this.face = res.data.logo
        // 设置 分享文字
        this.shareText = res.data.share.text
        // 设置 分享图标
        this.shareIcon = res.data.share.icon
        // 设置 标题
        this.title = res.data.title
        // 设置 任务名称
        this.taskName = res.data.task.name
        // 设置图文介绍
        this.introduceImage = res.data.introduceImage
        // 设置挑战ID
        this.activityPublishId = res.data.activityPublishId
        //
        this.maxRater = res.data.task.star.max
        //
        this.rater = res.data.task.star.current
        //
        this.expend = res.data.expend
        //
        this.startStopTime = res.data.startStopTime.split('，')
        //
        this.taskItems = res.data.task.taskItems
        setTimeout(() => {
          document.body.scrollTop = this.$store.state.pageState.top
        }, 400)
      })
    },
    //
    setData () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 开始发布
      this.$axios.post('wx/activity/publish', {
        activityPublishId: this.activityPublishId
      }).then(e => {
        // 发布成功
        if (e.data.success) {
          setTimeout(() => {
            // 隐藏加载框
            this.$store.state.pageState.isLoading = false
            window.alert('发布成功')
          }, 1000)
        }
      })
    }
  },
  created () {
    console.info('challenge.vue')
    this.$store.state.pageState.isLoading = false
    this.fetchData()
  },
  mounted () {
  },
  beforeRouteLeave (to, from, next) {
    this.$store.state.pageState.top = document.body.scrollTop
    // window.alert(this.$store.state.pageState.top)
    // console.info('obj', document.body.scrollTop)
    next()
  }
}
</script>

<style lang="scss" scoped>
/* challenge.vue */
@import "rs/scss/challenge.scss"
</style>
