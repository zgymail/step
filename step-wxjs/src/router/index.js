import Vue from 'vue'
import Router from 'vue-router'
import index from '@/components/index'
import challenge from '@/components/challenge'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: index
    },
    {
      path: '/challenge',
      name: 'challenge',
      component: challenge
    }
  ]
})
