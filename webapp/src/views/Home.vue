<template>
  <div class="home">
    <main class="AppMain" role="main">
      <div class="AppMain-inner">
        <div class="AppMain-searchCard Card">
          <div class="SearchCard-title">
            <span class="SearchCard-titleText">Blockchain Browser</span>
          </div>
          <div class="SearchCard-tool" role="search">
            <div class="SearchCard-container">
              <form class="SearchCard-form" action>
                <div class="SearchCard-input">
                  <span class="SearchCard-icon">
                    <svg
                      class="Icon Icon-search"
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
                  <input
                    type="text"
                    name="SearchCard-text"
                    id="SearchCard-text"
                    class="SearchCard-text"
                  >
                </div>
                <button class="SearchCard-btn" id="SearchCard-btn" type="button">Go</button>
              </form>
            </div>
          </div>
          <div class="SearchCard-desc">
            <span class="SearchCard-descText">
              You can lookup address, transactions, blocks or
              hash...
            </span>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'home',
};
</script>

<style lang="scss" scoped>
.AppMain-inner {
  position: relative;
  width: 1000px;
  padding: 0 16px;
  margin: 14px auto;
}

.AppMain-searchCard {
  height: 305px;
  margin-bottom: 15px;
  text-align: center;
}

.SearchCard-title {
  margin-top: 62px;
}

.SearchCard-titleText {
  font-size: 46px;
  color: #444444;
}

.SearchCard-tool {
  margin-top: 24px;
}

.SearchCard-container {
  margin-left: 109px;
  width: 780px;
}

.SearchCard-form {
  display: flex;
}

.SearchCard-input {
  border: 1px solid #b6b6b6;
  border-right: none;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}

.SearchCard-icon {
  margin-left: 8px;
  margin-right: 8px;
}

.SearchCard-text {
  width: 674px;
  height: 48px;
  font-size: 18px;
  outline: none;
  border: none;
}

.SearchCard-btn {
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

.SearchCard-desc {
  margin-top: 18px;
}

.SearchCard-descText {
  font-size: 18px;
  color: #8590a6;
}
</style>
