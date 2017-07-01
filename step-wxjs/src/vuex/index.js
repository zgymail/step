import Vue from 'vue'
import Vuex from 'vuex'
import authorize from './authorize'
import pageState from './pageState'
Vue.use(Vuex)
const store = new Vuex.Store({
  modules: {
    authorize,
    pageState
  }
})
export default store
