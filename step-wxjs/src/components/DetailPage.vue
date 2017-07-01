<template>
  <div>
    <div>
     {{msg}}
    </div>

    <div>
      path :{{path}}
    </div>
  </div>

</template>

<script>

import { mapState } from 'vuex'
import store from '../store'

export default {
  data () {
    return {
      msg: ''
    }
  },
  created: function () {
      this.loadData();
 },
 computed: mapState("router",{
        path: 'fullPath',
 }),
 methods:{
    loadData:function(){
        this.$axios.get("wx/activity/index")
  				.then((response) => {
              initUI();
  				})
  				.catch((response)=>{
  					  console.log(response)
  				})
    },
    initUI:function(){
       this.msg='详情页面';
    }
 }

}
</script>
