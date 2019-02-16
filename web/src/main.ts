import Vue from 'vue';
import App from './App.vue';
import router from './router';

Vue.config.productionTip = false;

router.afterEach((to, from): void => {
  document.title = to.meta.title || 'SupBank';
});

new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
