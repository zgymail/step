<template>
  <div class="line flex hCenter">
    <img src="../../static/img/line.png">
    <span @click="edit($event)">修改</span>
  </div>
</template>

<script>
// import { MessageBox } from 'mint-ui'

export default {
  methods: {

    edit (event) {
      switch (event.target.parentNode.parentNode.className) {
        case 'face':
          this.$emit('editFace', true)
          break
        case 'title':
          // this.$emit('editTitle', MessageBox.prompt)
          break
        case 'info':
          this.$emit('editFace', true)
          break
        case 'price':
          // this.$emit('editPrice', MessageBox.prompt)
          break
        case 'time':
          // this.$emit('editTime', false)
          break
        case 'text':
          // this.$emit('editText', MessageBox.prompt)
          break
      }
    }

  }
}
</script>

<style lang="css">
.line {
  position: relative;
  width: 96%;
  height: 2rem;
  margin: -.9rem auto 0 auto;
  overflow: hidden;
}

.line span {
  position: absolute;
  width: 1.5rem;
  height: .8rem;
  line-height: .8rem;
  text-align: center;
  top: .25rem;
  right: 0;
  border-radius: 5px;
  font-size: .4rem;
  background: url(../../static/img/border.png) no-repeat;
  background-size: 100% 100%;
}
</style>
