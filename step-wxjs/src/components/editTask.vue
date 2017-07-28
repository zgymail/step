<template>
  <div>
    <p class="task">挑战任务</p>
    <checker
      v-model="demo5"
      default-item-class="demo5-item"
      selected-item-class="demo5-item-selected"
    >
      <checker-item v-for="(item, index) in tasks" :key="index" :value="item.taskId">
        <div class="box flex between">
          <p>{{ item.name }}</p>
          <rater :value="item.star.current" slot="value" active-color="#000" :font-size="15" :max="item.star.max" disabled></rater>
        </div>
        <div class="tasks">
          <div class="taskItem flex center" v-for="(taskItem, index) in item.taskItems">
            <p>{{ taskItem.name }}</p>
            <span>{{ taskItem.desc ? `(${taskItem.desc})` : '' }}</span>
          </div>
        </div>
      </checker-item>
    </checker>
    <div class="postion">
      <x-button type="primary" @click.native="setData" style="width: 94%;">确认修改</x-button>
    </div>
  </div>
</template>

<script>
import { Checker, CheckerItem, XButton, Rater } from 'vux'

export default {
  components: {
    Checker,
    CheckerItem,
    XButton,
    Rater
  },
  data () {
    return {
      demo5: 1,
      maxRater: 5,
      rater1: 0,
      rater2: 0,
      rater3: 0,
      taskId: 1,
      tasks: null
    }
  },
  created () {
    this.$store.state.pageState.isLoading = false
    this.getData()
  },
  methods: {
    getData () {
      this.$axios.get(`wx/activity/publish_prep_modify_task?activityPublishId=${this.$route.query.id}`).then(e => {
        console.info('e', e)
        // 获取 默认选中项
        this.demo5 = e.data.selectedTaskId
        //
        this.tasks = e.data.tasks
      })
    },
    setData () {
      // 显示加载框
      this.$store.state.pageState.isLoading = true
      // 提交 任务
      this.$axios.post('wx/activity/publish_modify_task', {activityPublishId: this.$route.query.id, selectedTaskId: this.demo5}).then(e => {
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

<style>
body {
  background: #f2f2f2;
}
</style>

<style scoped>
.tasks {
  display: block;
  width: 80%;
  margin: 0 auto;
  overflow: hidden;
  padding-bottom: .4rem;
  /*margin-top: .2rem;*/
}
.taskItem {
  position: relative;
  /*margin: .15rem;*/
  margin: .3rem .15rem;
  float: left;
  width: 29%;
  height: 2.9rem;
  background: url(http://www.2e33.com/demo/background.jpg) no-repeat;
}
.taskItem p {
  font-size: .3rem;
}
.taskItem span {
  position: absolute;
  left: 0;
  bottom: -.6rem;
  font-size: .4rem;
}
.vux-rater {
  line-height: inherit;
}
.task {
  margin-left: .2rem;
}
.box {
  height: 1rem;
  padding: 0 .5rem;
}
.box p {
  display: inline-block;
  font-size: .45rem;
  line-height: 1rem;
}
.demo5-item {
  display: block;
  width: 97%;
  /*height: 4rem;*/
  /*line-height: 26px;*/
  /*text-align: center;*/
  border-radius: 3px;
  border: 1px solid #ccc;
  background-color: #fff;
  margin: .2rem auto;
}
.demo5-item-selected {
  background: #ffffff url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAsAAAALCAMAAACecocUAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QTZDOEJBQ0E3NkIxMTFFNEE3MzJFOUJCMEU5QUM0QkIiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QTZDOEJBQ0I3NkIxMTFFNEE3MzJFOUJCMEU5QUM0QkIiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpBNkM4QkFDODc2QjExMUU0QTczMkU5QkIwRTlBQzRCQiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpBNkM4QkFDOTc2QjExMUU0QTczMkU5QkIwRTlBQzRCQiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PnMGp3kAAAAJUExURf9KAP///////4Jqdw0AAAADdFJOU///ANfKDUEAAAAuSURBVHjaTMpBDgAABAPB5f+PlhLUpZMWuQcYMWLEyDN4ymqa5KS4+3G+KAEGACQmAGlKzr56AAAAAElFTkSuQmCC) no-repeat right bottom;
  border-color: #ff4a00;
}
.postion {
  display: block;
  /*position: fixed;*/
  /*bottom: 0;*/
  width: 100%;
  margin: .3rem 0;
}
</style>
