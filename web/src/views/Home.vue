<template>
  <div class="home">
    <AppHeader/>
    <div class="main">
      <main role="main">
        <div class="main-inner">
          <div class="searchCard card">
            <div class="title">
              <span>Blockchain Browser</span>
            </div>
            <div class="search">
              <form action class="searchForm">
                <div class="search-inner">
                  <div class="input-container">
                    <span class="search-icon icon">
                      <svg
                        class="icon"
                        fill="currentColor"
                        viewBox="-2 -2 24 24"
                        width="24"
                        height="24"
                      >
                        <path
                          d="M17.068 15.58a8.377 8.377 0 0 0 1.774-5.159 8.421 8.421 0 1 0-8.42 8.421 8.38 8.38 0 0 0 5.158-1.774l3.879 3.88c.957.573 2.131-.464 1.488-1.49l-3.879-3.878zm-6.647 1.157a6.323 6.323 0 0 1-6.316-6.316 6.323 6.323 0 0 1 6.316-6.316 6.323 6.323 0 0 1 6.316 6.316 6.323 6.323 0 0 1-6.316 6.316z"
                          fill-rule="evenodd"
                        ></path>
                      </svg>
                    </span>
                    <input type="text" class="search-input" name="search-input">
                  </div>
                  <input type="button" class="search-btn" value="Go">
                </div>
              </form>
            </div>
            <div class="desc">
              <span>You can lookup address, transactions, blocks or hash...</span>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import AppHeader from '@/components/AppHeader.vue';

@Component({
  components: {
    AppHeader,
  },
})
export default class Home extends Vue {}
</script>

<style scoped>
.main-inner {
  position: relative;
  width: 1000px;
  padding: 0 16px;
  margin: 14px auto;
}

.main .searchCard {
  text-align: center;
}

.searchCard .title {
  margin-top: 26px;
  font-size: 46px;
  color: #444444;
}

.searchCard .search {
  margin-top: 24px;
  margin-left: 109px;
  width: 780px;
}

.searchCard .search-inner {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
}

.searchCard .input-container {
  border: 1px solid #b6b6b6;
  border-right: none;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}

.searchCard .search-icon {
  margin-left: 8px;
  margin-right: 8px;
}

.searchCard .search-input {
  width: 674px;
  height: 48px;
  font-size: 18px;
  outline: none;
  border: none;
}

.searchCard .search-btn {
  width: 58px;
  height: 52px;
  border: none;
  font-size: 22px;
  cursor: pointer;
  background: #3385ff;
  color: #fff;
  letter-spacing: 1px;
  font-family: Montserrat, sans-serif;
  font-weight: 600;
  border-bottom-right-radius: 4px;
  border-top-right-radius: 4px;
  outline: none;
}

.searchCard .desc {
  margin-top: 18px;
  margin-bottom: 30px;
  font-size: 18px;
  color: #8590a6;
}
</style>
