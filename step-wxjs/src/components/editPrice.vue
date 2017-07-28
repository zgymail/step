<template>
  <div class="app">
    <div class="title">
      <p>参与挑战需要支付的费用（最多99元）</p>
      <div class="box">
        <x-input class="input" title="" :value="expend" type="text" placeholder="请输入金额" :min="1" :max="3" @on-focus="clear()" v-model="expend"></x-input>
        <p>元</p>
      </div>
    </div>
    <div class="postion">
      <x-button type="primary" @click.native="setData" style="width: 94%;">确认修改</x-button>
    </div>

    <alert v-model="show" title="错误">修改失败</alert>
  </div>
</template>

<script>
import { XInput, XButton, Box, Alert } from 'vux'

export default {
  data () {
    return {
      // 金额
      expend: '',
      // 弹出框
      show: false
    }
  },
  watch: {
    expend (val, oldVal) {
      // window.alert(val)
      if (Number.isNaN(+val)) {
        setTimeout(() => {
          this.expend = ''
        }, 500)
      }
      if (+val > 99) {
        setTimeout(() => {
          this.expend = '99'
        }, 200)
      }
    }
  },
  components: {
    XInput,
    XButton,
    Box,
    Alert
  },
  created () {
    this.$store.state.pageState.isLoading = false
    setTimeout(() => {
      this.display = true
    }, 200)
    this.getData()
  },
  methods: {
    // 获取 标题
    getData () {
      this.$axios.get(`wx/activity/publish_prep_modify_expend?activityPublishId=${this.$route.query.id}`).then(e => {
        // 获取 默认的金额
        this.expend = e.data.expend
      })
    },
    // 上传 标题
    setData () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 提交 标题
      this.$axios.post('wx/activity/publish_modify_expend', {activityPublishId: this.$route.query.id, expend: +this.expend}).then(e => {
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
    },
    // 清除默认数据
    clear () {
      this.expend = ''
    }
  }
}
</script>

<style scoped>
.app {
  margin-top: 5rem;
}
.box {
  position: relative;
  /*margin-top: .5rem;*/
}
.box p {
  position: absolute;
  top: .2rem;
  left: 7rem;
  font-size: .8rem;
}
.input {
  width: 30%;
  margin: 0 auto;
  border-bottom: 1px solid #ccc;
}
.postion {
  margin-top: 2rem;
}
.title > p {
  margin-left: .4rem;
  font-size: .5rem;
}
</style>
