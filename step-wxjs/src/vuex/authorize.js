import cookie from '../api/cookie.js'
// initial state
const state = {
  userAccessToken: cookie.getCookie('userAccessToken'),
  loginOperate: 0
}

// getters
const getters = {
  getUserAccessToken: state => state.userAccessToken,
  hasUserAccessToken: state => {
    return state.userAccessToken !== null && state.userAccessToken !== ''
  }

}

// actions
const actions = {
  updateUserAccessToken ({ commit }, userAccessToken) {
    commit('updateUserAccessToken', userAccessToken)
  },
  login ({ commit }) {
    commit('updateLoginOperate')
  },
  checkUserAccessToken: ({ commit, context }) => {
    let state = context.state
    let ret = state.userAccessToken !== null && state.userAccessToken !== ''
    if (!ret) {
      commit('updateLoginOperate')
    }
    return ret
  }
}
// mutations
const mutations = {
  updateUserAccessToken (state, userAccessToken) {
    state.userAccessToken = userAccessToken
    cookie.setCookie('userAccessToken', userAccessToken)
    return new Promise((resolve, reject) => {
      resolve(userAccessToken)
    })
  },
  updateLoginOperate (state) {
    console.info('obj2')
    state.loginOperate += 1
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
