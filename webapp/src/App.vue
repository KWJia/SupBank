<template>
  <div id="app">
    <AppHeader/>
    <router-view/>
    <AppFooter/>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader.vue';
import AppFooter from '@/components/AppFooter.vue';
import axios from 'axios';
import utils from './common/utils.js';

export default {
  name: 'app',
  components: {
    AppHeader,
    AppFooter
  },
  created() {
    const checkSignined = () => {
      axios.get('/checkExpired', utils.getStorage(auth))
      .then(function(response) {
        cosnole.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
    };

    checkSignined();
  }
};
</script>

<style lang="scss">
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
  cursor: pointer;
}

button {
  cursor: pointer;
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
  padding: 36px;
}

.DividerCol {
  display: inline-block;
  width: 1px;
  height: 10px;
  margin: 0 8px;
  background: #ebebeb;
}

.DividerRow {
  width: 100%;
  height: 1px;
  background-color: rgb(204, 204, 204);
  margin: 10px 0px;
}
</style>
