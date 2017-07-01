<template>
  <div>
    <loading v-model="isLoading"></loading>
    <transition :name="'vux-pop-' + (direction === 'forward' ? 'in' : 'out')">
      <router-view class="router-view"></router-view>
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

body {
  background-color: #fbf9fe;
}

.router-view {
  width: 100%;
}

.vux-pop-out-enter-active,
.vux-pop-out-leave-active,
.vux-pop-in-enter-active,
.vux-pop-in-leave-active {
  position: absolute;
  top: 0;
  will-change: transform;
  transition: all 200ms;
  height: 100%;
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
