export default {
    vm:null,
    debug:process.env.NODE_ENV !== 'production',
    install:function(Vue){
          console.log("appconfig");
          Vue.prototype.$appConfig=this;
    }
}
