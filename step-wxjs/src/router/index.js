import Router from 'vue-router'
import store from '../vuex'
// sync方法：主要是把 vue-router的状态放进vuex的state中，这样就可以通过改变state来进行路由的一些操作
import { sync } from 'vuex-router-sync'

// 导入页面
import index from '../components/index'
import challenge from '../components/challenge'
import LoginPage from '../components/LoginPage'
import editLogo from '../components/editLogo'
import editTitle from '../components/editTitle'
import editIntroduce from '../components/editIntroduce'
import editTask from '../components/editTask'
import editPrice from '../components/editPrice'
import editTime from '../components/editTime'
import editText from '../components/editText'

let router = new Router({
  routes: [
    {
      path: '/',
      component: index,
      meta: {
        title: '挑战列表'
      }
    },
    {
      path: '/challenge',
      component: challenge,
      meta: {
        title: '挑战发布'
      }
    },
    {
      path: '/login-page',
      component: LoginPage,
      meta: {
        title: '自动登录'
      }
    },
    {
      path: '/editLogo',
      component: editLogo,
      meta: {
        title: '修改头像'
      }
    },
    {
      path: '/editTitle',
      component: editTitle,
      meta: {
        title: '修改标题'
      }
    },
    {
      path: '/editIntroduce',
      component: editIntroduce,
      meta: {
        title: '修改介绍'
      }
    },
    {
      path: '/editTask',
      component: editTask,
      meta: {
        title: '修改任务'
      }
    },
    {
      path: '/editPrice',
      component: editPrice,
      meta: {
        title: '修改费用'
      }
    },
    {
      path: '/editTime',
      component: editTime,
      meta: {
        title: '修改时间'
      }
    },
    {
      path: '/editText',
      component: editText,
      meta: {
        title: '修改分享文字'
      }
    }
  ]
})

sync(store, router, {moduleName: 'router'})

// 简单的历史管理

// 缓存 Storage对象
const history = window.sessionStorage
// 初始化，清空数据
history.clear()
// 历史页面的总数
let historyCount = history.getItem('count') * 1 || 0
// 增加 '/'属性，默认值是0，就是指首页
history.setItem('/', 0)

// 注册一个全局的 before 钩子，在路由切换开始时调用
router.beforeEach(function (to, from, next) {
  // 显示 isLoading动画
  store.commit('pageState/updateLoadingStatus', {
    isLoading: true
  })
  // 即将进入的页面是首页
  const toIndex = history.getItem(to.path)
  // 即将离开的页面是首页
  const fromIndex = history.getItem(from.path)

  // 即将进入的页面是首页，!!'0' === true
  if (toIndex) {
    // 即将离开的页面不是首页，或者从即将离开的页面进入即将进入的页面(后退)，或者当前页面就是首页
    if (!fromIndex || parseInt(toIndex, 10) > parseInt(fromIndex, 10) || (toIndex === '0' && fromIndex === '0')) {
      // 设置 前进动画
      store.commit('pageState/updateDirection', {
        direction: 'forward'
      })
    } else {
      // 设置 后退动画
      store.commit('pageState/updateDirection', {
        direction: 'reverse'
      })
    }
  // 即将进入的页面不是首页，!!null === false
  } else {
    // 递增
    ++historyCount
    // 设置历史页面总数
    history.setItem('count', historyCount)
    // 即将进入的页面不是首页，设置当前页面的层级，默认值是0
    to.path !== '/' && history.setItem(to.path, historyCount)
    // 设置 前进动画
    store.commit('pageState/updateDirection', {
      direction: 'forward'
    })
  }

  // 即将进入的页面路径中有'/http'字符串，例子：'/http:www.2e33.com'
  if (/\/http/.test(to.path)) {
    // 取出 URL
    let url = to.path.split('http')[1]
    // 导航到 URL
    window.location.href = `http${url}`
  } else {
    // 进入下一个钩子
    next()
  }
})

// router.afterEach(function (to) {
//   // setTimeout(() => {
//   //   store.commit('updateLoadingStatus', {isLoading: false})
//   // }, 800)
//   // window.alert(document.body.scrollTop)
//   store.commit('pageState/UPDATE_TOP', {
//     top: document.body.scrollTop
//   })
//   console.info(document.body.scrollTop)
//   console.info(store.state.pageState.top)
// })

export default router
