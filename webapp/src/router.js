import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path: '/signin',
      name: 'Signin',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () =>
        import(/* webpackChunkName: "about" */ './views/SignIn.vue'),
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: () => import('./views/SignUp.vue'),
    },
    {
      path: '/product',
      name: 'Product',
      component: () => import('./views/Product.vue'),
    },
    {
      path: '/data',
      name: 'Data',
      component: () => import('./views/Data.vue'),
    },
  ],
});
