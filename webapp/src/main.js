import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import axios from 'axios';

// fat
axios.defaults.baseURL = 'localhost:8080/api';
// prd
//axios.defaults.baseURL = 'https://api.example.com';

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
