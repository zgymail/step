/*
import axios from 'axios'
// import appConfig from './app-config'
import store from '../vuex'

// 设置默认的超时时间
axios.defaults.timeout = 50000
// 设置默认的请求内容类型
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
// axios.defaults.baseURL = appConfig.debug?'http://apptest.happyrun.cn/step/':'http://app.happyrun.cn/step/';
// 设置默认的网站域名
axios.defaults.baseURL = 'https://app.happyrun.cn/step/'
// 添加一个请求拦截器
axios.interceptors.request.use(function (config) {
  // 在请求发送之前做一些事...
  // 获取 Getters对象
  let getters = store.getters
  // userAccessToken 是否存在
  if (getters['authorize/hasUsercToken']) {
    // 获取 userAccessToken
    let userAccessToken = getters['authorize/getUserAccessToken']
    // 在http header中设置一个'user_access_token'属性
    config.headers['user_access_token'] = userAccessToken
  }
  return config
}, function (error) {
  // 当出现请求错误是做一些事...
  return Promise.reject(error)
})

// 添加一个返回拦截器
axios.interceptors.response.use(function (response) {
  // 未授权
  if (response.status === 201) {
    // 异步 执行updateLoginOperate方法
    store.dispatch('authorize/login')
    // 中止执行后面的回调，并返回 response对象
    return Promise.reject(response)
  // 已授权
  } else {
    // 获取 Token
    let userAccessTokenUpdate = response.headers['user_access_token_update']
    // Token不为空
    if (userAccessTokenUpdate !== null) {
      // 异步 执行updateUserAccessToken方法
      store.dispatch('authorize/updateUserAccessToken', userAccessTokenUpdate)
    }
    return response
  }
}, function (error) {
  return Promise.reject(error)
})

export default {
  // 用来注入axios插件
  install: function (Vue, option) {
    Vue.prototype.$axios = axios
  }
}
*/
import axios from 'axios'
// import appConfig from './app-config'
import store from '../vuex'

axios.defaults.timeout = 50000
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
// axios.defaults.baseURL =appConfig.debug?'http://apptest.happyrun.cn/step/':'http://app.happyrun.cn/step/';
axios.defaults.baseURL = 'https://app.happyrun.cn/step/'
axios.interceptors.request.use(function (config) {
  let getters = store.getters
  let hasAuthorize = getters['authorize/hasUserAccessToken']
  if (hasAuthorize) {
    let userAccessToken = getters['authorize/getUserAccessToken']
    config.headers['user_access_token'] = userAccessToken
    console.info('hasuserAccessToken', userAccessToken)
  }
  return config
}, function (error) {
  return Promise.reject(error)
})

axios.interceptors.response.use(function (response) {
  console.info('status', response.status)
  if (response.status === 201) {
    store.dispatch('authorize/login')
    // return Promise.reject(response)
    return response
  }
  let userAccessTokenUpdate = response.headers['user_access_token_update']
  // console.info('userAccessTokenUpdateType', typeof userAccessTokenUpdate)
  if (userAccessTokenUpdate !== undefined) {
    store.dispatch('authorize/updateUserAccessToken', userAccessTokenUpdate)
  }
  return response
}, function (error) {
  return Promise.reject(error)
})

export default {
  install: function (Vue, option) {
    Vue.prototype.$axios = axios
  }
}
