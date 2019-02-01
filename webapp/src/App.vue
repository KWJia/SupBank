<template>
  <div id="app">
    <!-- <div id="nav">
      <router-link to="/">Home</router-link>|
      <router-link to="/about">About</router-link>
    </div>
    <router-view/>-->
    <AppHeader/>
    <router-view/>
    <AppFooter/>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';
import AppFooter from '@/components/AppFooter.vue';

export default {
  name: 'app',
  components: {
    AppHeader,
    AppFooter,
  },
};
</script>


<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;
    &.router-link-exact-active {
      color: #42b983;
    }
  }
}

* {
  margin: 0;
  padding: 0;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, PingFang SC,
    Microsoft YaHei, Source Han Sans SC, Noto Sans CJK SC, WenQuanYi Micro Hei,
    sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  font-size: 15px;
  color: #1a1a1a;
  background: #f6f6f6;
}

a {
  color: inherit;
  text-decoration: none;
}

.Icon {
  vertical-align: text-bottom;
  fill: currentColor;
  text-align: center;
}

.Card {
  width: auto;
  background: #fff;
  overflow: hidden;
  border-radius: 2px;
  -webkit-box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

.Divider {
  display: inline-block;
  width: 1px;
  height: 10px;
  margin: 0 8px;
  background: #ebebeb;
}
</style>
