<template>
  <div>
    <p class="text">在起止时间内参与健康福利返还</p>
    <div>
      <group>
        <datetime v-model="startTime" @on-change="change" title="开始时间："></datetime>
        <!-- <popup-picker title="在几天内分别返还" v-model="day" :data="dayList"></popup-picker> -->
      </group>
      <group>
        <cell title="结束时间：" :value="endTime"></cell>
      </group>
      <div class="box">
        <p>在</p>
        <x-input class="input" type="text" placeholder="请输入天数" :min="1" :max="3" @on-focus="clear()" v-model="day"></x-input>
        <span>天内分别返还</span>
      </div>
    </div>
    <div class="postion">
      <x-button type="primary" @click.native="setData" style="width: 94%;">确认修改</x-button>
    </div>
  </div>
</template>

<script>
import { XInput, XButton, Datetime, Group, Cell } from 'vux'

export default {
  components: {
    XInput,
    XButton,
    Datetime,
    Group,
    Cell
  },
  data () {
    return {
      day: 0,
      // dayList: [['1天', '2天', '3天', '4天', '5天', '6天', '7天', '8天', '9天']],
      startTime: '',
      endTime: ''
    }
  },
  created () {
    this.$store.state.pageState.isLoading = false
    this.getData()
  },
  methods: {
    clear () {
      this.day = ''
    },
    change (value) {
      console.log('change', value)
    },
    getData () {
      this.$axios.get(`wx/activity/publish_prep_modify_startstoptime?activityPublishId=${this.$route.query.id}`).then(e => {
        console.info('e', e)
        // 获取 默认的开始时间
        this.startTime = `${e.data.startTime.year}-${(e.data.startTime.month + '').length === 1 ? '0' + e.data.startTime.month : e.data.startTime.month}-${(e.data.startTime.day + '').length === 1 ? '0' + e.data.startTime.day : e.data.startTime.day}`
        // 获取 默认的结束时间
        this.endTime = `${e.data.endTime.year}-${(e.data.endTime.month + '').length === 1 ? '0' + e.data.endTime.month : e.data.endTime.month}-${(e.data.endTime.day + '').length === 1 ? '0' + e.data.endTime.day : e.data.endTime.day}`
        // 获取 默认的天数
        this.day = e.data.days
      })
    },
    setData () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 提交 时间
      this.$axios.post('wx/activity/publish_modify_startstoptime', {
        activityPublishId: this.$route.query.id,
        days: this.day,
        startTime: {
          day: this.startTime.split('-')[2],
          month: this.startTime.split('-')[1],
          year: this.startTime.split('-')[0]
        },
        endTime: {
          day: this.endTime.split('-')[2],
          month: this.endTime.split('-')[1],
          year: this.endTime.split('-')[0]
        }
      }).then(e => {
        console.info('ee', e)
        // 修改成功
        if (e.data.success) {
          setTimeout(() => {
            // 隐藏加载框
            this.$store.state.pageState.isLoading = false
            // 返回 上一页
            this.$router.go(-1)
          }, 500)
        }
      })
    }
  }
}
</script>

<style scoped>
.box p {
  /*display: inline-block;*/
  position: absolute;
  top: .3rem;
  left: 1rem;
}
.box span {
  /*display: inline-block;*/
  position: absolute;
  top: .3rem;
  right: .7rem;
}
.input {
  /*display: -webkit-inline-box;*/
  width: 25%;
  /*margin: 0 auto;*/
  margin-left: 2rem;
  border-bottom: 1px solid #ccc;
  font-size: .5rem;
}
.input::before {
  border: none;
}
.box {
  position: relative;
  margin-top: .6rem;
}
.text {
  font-size: .4rem;
  margin: .5rem 0 0 .3rem;
}
.postion {
  /*display: block;
  position: fixed;
  bottom: 0;
  width: 100%;*/
  /*margin: .3rem 0;*/
  margin-top: 1.5rem;
}
</style>
