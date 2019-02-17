<template>
  <div class="search"></div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class Search extends Vue {
  @Prop() private q!: string;

  private data() {
    return {
      searchText: this.q,
    };
  }

  private created() {
    const token = localStorage.getItem('token');
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open('GET', '//localhost:3000/api/searchTransaction?searchtext=' + this.q, true);
    xmlhttp.send();

    xmlhttp.onreadystatechange = (): void => {
      if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        const response = JSON.parse(xmlhttp.responseText);

        if (response.ack === 'success') {
          alert(response.data.searchResultList);
        } else {
          alert(response.data.msg);
        }
      }
    };
  }
}
</script>
