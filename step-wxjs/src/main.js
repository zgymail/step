// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import VueRouter from 'vue-router'
import { sync } from 'vuex-router-sync'
import axios from './api/axios-config'
import App from './App'
import index from './components/index'
import challenge from './components/challenge'
import foodsPush from './components/foodsPush'
import demo from './components/demo'
import LoginPage from './components/LoginPage.vue'
import IndexPage from './components/IndexPage.vue'

// import Vuex from 'vuex'
import store from './vuex'
// Vue.use(Vuex)

Vue.use(VueRouter)
Vue.use(axios)
import { DatetimePlugin, AjaxPlugin, LoadingPlugin } from 'vux'

Vue.use(DatetimePlugin)
Vue.use(AjaxPlugin)
Vue.use(LoadingPlugin)

const routes = [
  {
    path: '/',
    component: index
  },
  {
    path: '/challenge',
    component: challenge
  },
  {
    path: '/foods',
    component: foodsPush
  },
  {
    path: '/demo',
    component: demo
  },
  {
    path: '/login-page',
    component: LoginPage
  },
  {
    path: '/IndexPage',
    component: IndexPage
  }
]

const router = new VueRouter({
  routes
})

sync(store, router, {moduleName: 'router'})

FastClick.attach(document.body)

Vue.config.productionTip = false

// simple history management
const history = window.sessionStorage
history.clear()
let historyCount = history.getItem('count') * 1 || 0
history.setItem('/', 0)

router.beforeEach(function (to, from, next) {
  store.commit('pageState/updateLoadingStatus', {isLoading: true})
  const toIndex = history.getItem(to.path)
  const fromIndex = history.getItem(from.path)

  if (toIndex) {
    if (!fromIndex || parseInt(toIndex, 10) > parseInt(fromIndex, 10) || (toIndex === '0' && fromIndex === '0')) {
      store.commit('pageState/updateDirection', {direction: 'forward'})
    } else {
      store.commit('pageState/updateDirection', {direction: 'reverse'})
    }
  } else {
    ++historyCount
    history.setItem('count', historyCount)
    to.path !== '/' && history.setItem(to.path, historyCount)
    store.commit('pageState/updateDirection', {direction: 'forward'})
  }

  if (/\/http/.test(to.path)) {
    let url = to.path.split('http')[1]
    window.location.href = `http${url}`
  } else {
    next()
  }
})

// router.afterEach(function (to) {
//   setTimeout(() => {
//     store.commit('updateLoadingStatus', {isLoading: false})
//   }, 800)
// })

/* eslint-disable no-new */
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app-box')
