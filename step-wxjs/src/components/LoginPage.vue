<template>
  <div>
    <div>
      {{msg}}
    </div>
    <div>
      <button v-on:click='authorize'>重试</button>
    </div>
  </div>
</template>

<script>
// import Vue from 'vue'
import store from '../vuex'
import axios from 'axios'
export default {
  data () {
    return {
      msg: '',
      fromPath: '',
      routerPath: '',
      pagePath: ''
    }
  },
  created: function () {
    let router = store.state.router
    let query = router.query

    if (query !== null && query['user_access_token'] !== undefined) {
      this.fromPath = query['fromPath']
      this.routerPath = router.fullPath
      if (query['user_access_token'] === 'null') {
        this.msg = query['msg']
      } else {
        let userAccessToken = query['user_access_token']
        let promise = store.dispatch('authorize/updateUserAccessToken', userAccessToken)
        let mythis = this
        promise.then(function (value) {
          mythis.$router.replace(mythis.fromPath)
        })
      }
    } else {
      let from = router.from
      this.fromPath = from.fullPath
      this.routerPath = router.fullPath
      this.authorize()
    }
  },
  methods: {
    authorize () {
      let routerPath = this.routerPath
      let fromPath = this.fromPath

      let index = routerPath.indexOf('?')
      if (index !== -1) {
        routerPath = routerPath.substring(0, index)
      }
      index = fromPath.indexOf('?')
      if (index !== -1) {
        fromPath = fromPath.substring(0, index)
      }
      let url = axios.defaults.baseURL + 'wx/authorize?routerPath=' + routerPath + '&fromPath=' + fromPath
      if (this.pagePath !== '') {
        url += '&pagePath=' + this.pagePath
      }
      window.location.href = encodeURI(url)
    }
  }
}
</script>
