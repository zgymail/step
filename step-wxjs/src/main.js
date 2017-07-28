// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import VueRouter from 'vue-router'
import axios from './api/axios-config'
import App from './App'
import router from './router'
import store from './vuex'
import { DatetimePlugin, AjaxPlugin, LoadingPlugin, WechatPlugin } from 'vux'

Vue.use(WechatPlugin)
Vue.use(VueRouter)
Vue.use(axios)
Vue.use(DatetimePlugin)
Vue.use(AjaxPlugin)
Vue.use(LoadingPlugin)
Vue.use(require('vue-wechat-title'))

// const wx = Vue.wechat
// const http = Vue.http
// const url = document.location.href

// console.info(Vue.$axios)

// http.post('https://app.happyrun.cn/step/wx/jsapi_ticket?url=' + encodeURIComponent(url)).then(res => {
//   wx.config(res.data)
// })

FastClick.attach(document.body)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app-box')
