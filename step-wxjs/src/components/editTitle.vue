<template>
  <div class="app">
    <div class="title">
      <p>标题名称</p>
      <x-input max="15" :value="title" placeholder="请输入标题" @on-focus="clear()" v-model="title"></x-input>
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
      // 标题
      title: '',
      // 弹出框
      show: false
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
    this.getData()
  },
  methods: {
    // 获取 标题
    getData () {
      this.$axios.get(`wx/activity/publish_prep_modify_title?activityPublishId=${this.$route.query.id}`).then(e => {
        // 获取 默认的标题
        this.title = e.data.title
      })
    },
    // 上传 标题
    setData () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 提交 标题
      this.$axios.post(`wx/activity/publish_modify_title`, {activityPublishId: this.$route.query.id, title: this.title}).then(e => {
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
      this.title = ''
    }
  }
}
</script>

<style scoped>
.app {
  margin-top: 5rem;
}
.postion {
  margin-top: 1.5rem;
}
.title p {
  margin-left: .4rem;
}
</style>
