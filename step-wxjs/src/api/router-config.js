
import VueRouter from 'vue-router'
import store from '../store'
import { sync } from 'vuex-router-sync'
//导入页面
import DetailPage from '../components/DetailPage.vue'
import LoginPage from '../components/LoginPage.vue'
import IndexPage from '../components/IndexPage.vue'

const router = new VueRouter({
  mode: 'hash',
  base: __dirname,
  routes: [
    { path: '/', component: IndexPage },
    { path: '/detail-page', component: DetailPage },
    { path: '/login-page', component: LoginPage }
  ]
})
sync(store, router, { moduleName:"router"} );
export default router;
