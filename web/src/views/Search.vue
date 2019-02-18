<template>
  <div class="search">
    <div class="transactionListCard card">
      <table class="listTable">
        <caption>transaction</caption>
        <tr>
          <th>Height</th>
          <th>Hash</th>
          <th>Amount</th>
          <th>Time</th>
        </tr>
        <tr v-for="(t, k, i) in searchResultList">
          <div v-if="i <= 6">
            <td>t.height</td>
            <td>t.transactionhash</td>
            <td>t.amount</td>
            <td>t.timestamp</td>
          </div>
        </tr>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import axios from 'axios';

@Component
export default class Search extends Vue {
  private data() {
    return {
      searchText: this.$route.query.q,
      searchResultList: [],
      pages: 1,
      page: 1,
    };
  }

  private created() {
    const searchText = this.$route.query.q;

    axios.get('//localhost:3000/api/searchTransaction', {
      params: {
        searchtext: searchText,
      },
    }).then((response) => {
      const data = response.data;
      if (data.ack === 'success') {
        this.$data.searchResultList = data.data.searchResultList;
      } else {
        alert(data.data.msg);
      }
    }).catch((error) => {
      alert(error);
    });
  }
}
</script>

<style scoped>
.searchCard .desc {
  margin-top: 18px;
  margin-bottom: 30px;
  font-size: 18px;
  color: #8590a6;
}

.listTable {
  display: flex;
  display: -webkit-box;
  display: -ms-flexbox;
  align-items: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  justify-content: center;
}
</style>
