<template>
  <div class="toast">
    <p class="title">Toast</p>
    <p class="msg" v-for="(msg, key, index) in msgList">{{msg}}</p>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Toast extends Vue {
  private data() {
    return {
      msgList: [],
    };
  }
}
</script>

<style scoped>

</style>
