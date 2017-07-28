<template>
  <div>
    <loading v-model="isLoading"></loading>
    <transition :name="'vux-pop-' + (direction === 'forward' ? 'in' : 'out')">
      <router-view class="router-view" v-wechat-title="$route.meta.title"></router-view>
    </transition>
  </div>
</template>

<script>
import { Loading } from 'vux'
import { mapState } from 'vuex'

export default {
  components: {
    Loading
  },
  name: 'app',
  computed: {
    ...mapState({
      direction: state => state.pageState.direction,
      isLoading: state => state.pageState.isLoading,
      loginOperate: state => state.authorize.loginOperate
    })
  },
  watch: {
    loginOperate: function (newLoginOperate) {
      // router.push('/login-page')
      this.$router.push('/login-page')
    }
  }
}
</script>

<style lang="less">
@import '~vux/src/styles/reset.less';
// @import '~vux/src/styles/1px.less';
// @import '~vux/src/styles/tap.less';

body {
  background-color: #fbf9fe;
}


.router-view {
  width: 100%;
  top: 0;
}
.vux-pop-out-enter-active,
.vux-pop-out-leave-active,
.vux-pop-in-enter-active,
.vux-pop-in-leave-active {
  will-change: transform;
  transition: all 300ms;
  height: 100%;
  top: 0;
  position: absolute;
  backface-visibility: hidden;
  perspective: 1000;
}

.vux-pop-out-enter {
  opacity: 0;
  transform: translate3d(-100%, 0, 0);
}

.vux-pop-out-leave-active {
  opacity: 0;
  transform: translate3d(100%, 0, 0);
}

.vux-pop-in-enter {
  opacity: 0;
  transform: translate3d(100%, 0, 0);
}

.vux-pop-in-leave-active {
  opacity: 0;
  transform: translate3d(-100%, 0, 0);
}
</style>
