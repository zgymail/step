<template>
  <div class="app">
    <p>分享文本</p>

    <group class="text">
      <x-textarea class="textarea" :max="30" placeholder="请输入分享文本" @on-focus="clear()" v-model="shareText"></x-textarea>
    </group>

    <div class="postion">
      <x-button type="primary" @click.native="setData" style="width: 94%;">确认修改</x-button>
    </div>

    <alert v-model="show" title="错误">修改失败</alert>
  </div>
</template>

<script>
import { XTextarea, Group, XButton, Alert } from 'vux'

export default {
  components: {
    XTextarea,
    XButton,
    Group,
    Alert
  },
  data () {
    return {
      shareText: ''
    }
  },
  created () {
    this.$store.state.pageState.isLoading = false
    this.getData()
  },
  methods: {
    setData () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 提交 分享文本
      this.$axios.post('wx/activity/publish_modify_share', {activityPublishId: this.$route.query.id, shareText: this.shareText}).then(e => {
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
    },
    getData () {
      this.$axios.get(`wx/activity/publish_prep_modify_share?activityPublishId=${this.$route.query.id}`).then(e => {
        this.shareText = e.data.shareText
      })
    },
    // 清除默认数据
    clear () {
      this.shareText = ''
    }
  }
}
</script>

<style scoped>
.app {
  margin-top: 4rem;
}
p {
  margin: 0 0 -.2rem .6rem;
  font-size: .5rem;
}
.text {
  display: block;
  width: 90%;
  margin: 0 auto;
}
.textarea {
  border: 1px solid #000;
  border-radius: 6px;
}
.postion {
  width: 100%;
  margin-top: 1rem;
}
</style>
