import axios from 'axios'
import qs from 'qs'
import appConfig from './app-config'
import store from '../store'

axios.defaults.timeout =50000;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
//axios.defaults.baseURL =appConfig.debug?'http://apptest.happyrun.cn/step/':'http://app.happyrun.cn/step/';
axios.defaults.baseURL ='http://app.happyrun.cn/step/';
axios.interceptors.request.use(function (config) {
    let getters=store.getters;
    let hasAuthorize=getters["authorize/hasUserAccessToken"];
    if(hasAuthorize){
      let userAccessToken=getters["authorize/getUserAccessToken"];
      config.headers["user_access_token"]=userAccessToken;
    }
    return config;
  }, function (error) {
    return Promise.reject(error);
  });

axios.interceptors.response.use(function (response) {
      if(response.status == '201'){
          store.dispatch('authorize/login');
          return Promise.reject(response);
      }
      let user_access_token_update=response.headers["user_access_token_update"]
      if(user_access_token_update!=null){
           store.dispatch('authorize/updateUserAccessToken',user_access_token_update);
      }
      return response;
  }, function (error) {
    return Promise.reject(error);
  });


  export default {
     install:function(Vue,option){
         Vue.prototype.$axios=axios;

     }
  }
