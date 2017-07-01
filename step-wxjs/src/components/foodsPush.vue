<template>
  <div class="app">
    <img class="face" :src="face">
    <div class="content">
      <p class="title">早餐</p>
      <div @click="show=true" class="photo flex center">
        <p>拍照</p>
      </div>
      <div class="button">
        <x-button type="primary" link="">打卡</x-button>
      </div>
    </div>



    <actionsheet v-model="show" :menus="menus" show-cancel @on-click-menu="click"></actionsheet>
  </div>
</template>

<script>
import { Actionsheet, XButton } from 'vux'

export default {
  components: {
    Actionsheet,
    XButton
  },
  data () {
    return {
      face: '../../static/img/face.png',
      show: false,
      menus: {
        menu1: '拍照',
        menu2: '从相册选择'
      }
    }
  },
  created () {
    this.$store.state.vux.isLoading = false
  },
  methods: {
    click (e) {
      console.info('e', e)
    }
  }
}
</script>

<style scoped>
.face {
  display: block;
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  margin: .5rem auto 0 auto;
}

.content {
  width: 90%;
  height: 20rem;
  margin: 1rem auto 0 auto;
  padding-top: .5rem;
  background: rgb(255, 255, 204);
}

.title {
  text-align: center;
}

.photo {
  width: 4rem;
  height: 4rem;
  /*line-height: 4rem;
  text-align: center;*/
  margin: .5rem auto 0 auto;
  border: 1px dashed #000;
}

.photo p {
  display: block;
  width: 2rem;
  height: 1.2rem;
  line-height: 1.2rem;
  text-align: center;
  border-radius: 5px;
  background: #ffffe2;
  border: 1px dashed #000;
}



.button {
  width: 90%;
  margin: .5rem auto .2rem auto;
}

</style>
