<template>
  <div class="AppHeader">
    <header role="banner">
      <div class="AppHeader-inner">
        <router-link class="AppHeader-brand" to="/">SUPBANK</router-link>
        <nav class="AppHeader-nav" role="navigation">
          <router-link to="/product" class="AppHeader-navItem">Product</router-link>
          <router-link to="/data" class="AppHeader-navItem">Data</router-link>
        </nav>
        <div class="AppHeader-searchBar" role="search">
          <form action class="SearchBar-tool">
            <div class="SearchBar-input">
              <input
                type="text"
                name="SearchBar-text"
                id="SearchBar-text"
                class="SearchBar-text"
                placeholder="Lookup blocks, transactions, hash..."
              >
              <button class="SearchBar-btn" type="button" id="SearchBar-btn">
                <svg
                  class="Icon Icon-search"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                  width="18"
                  height="18"
                >
                  <path
                    d="M17.068 15.58a8.377 8.377 0 0 0 1.774-5.159 8.421 8.421 0 1 0-8.42 8.421 8.38 8.38 0 0 0 5.158-1.774l3.879 3.88c.957.573 2.131-.464 1.488-1.49l-3.879-3.878zm-6.647 1.157a6.323 6.323 0 0 1-6.316-6.316 6.323 6.323 0 0 1 6.316-6.316 6.323 6.323 0 0 1 6.316 6.316 6.323 6.323 0 0 1-6.316 6.316z"
                    fill-rule="evenodd"
                  ></path>
                </svg>
              </button>
            </div>
          </form>
        </div>
        <div class="AppHeader-userSignInAndSignUp">
          <router-link to="/signin" class="AppHeader-userSignIn">Sign In</router-link>
          <i class="DividerCol"></i>
          <router-link to="/signup" class="AppHeader-userSignUp">Sign Up</router-link>
        </div>
        <div class="AppHeader-userInfo">
          <router-link to="/people" class="AppHeader-profileEntry">XIE Hui</router-link>
        </div>
      </div>
    </header>
  </div>
</template>

<script>
export default {
  name: 'AppHeader',
};
</script>

<style lang="scss" scoped>
.AppHeader {
  position: relative;
  z-index: 100;
  min-width: 1032px;
  overflow: hidden;
  background: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.2);
  background-clip: content-box;
}

.AppHeader-inner {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  width: 1000px;
  height: 52px;
  padding: 0 16px;
  margin: 0 auto;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.AppHeader-brand {
  font-size: 36px;
  color: #0084ff;
  font-family: Herculanum;
}

.AppHeader-nav {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  margin-left: 40px;
}

.AppHeader-navItem {
  padding: 0 15px;
  font-size: 15px;
  line-height: 30px;
  color: #8590a6;
}

.AppHeader-searchBar {
  margin-left: -80px;
}

.SearchBar-input {
  position: relative;
  display: flex;
  width: 300px;
  height: 34px;
  padding: 4px 10px;
  font-size: 14px;
  background: #f6f6f6;
  border: 1px solid #ebebeb;
  border-radius: 3px;
  box-sizing: border-box;
  transition: background 200ms, border 200ms;
}

.SearchBar-text {
  border: 0;
  width: 260px;
  background: #f6f6f6;
  color: #1a1a1a;
  height: 24px;
  line-height: 24px;
  font-size: 14px;
  overflow: hidden;
  cursor: text;
  outline: none;
}

.SearchBar-btn {
  border: 0;
  background: #f6f6f6;
  cursor: pointer;
  outline: none;
}

.AppHeader-userInfo,
.AppHeader-userSignInAndSignUp {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  -webkit-box-pack: end;
  -ms-flex-pack: end;
  justify-content: flex-end;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.AppHeader-userInfo a,
.AppHeader-userSignInAndSignUp a {
  padding: 0 16px;
  font-size: 16px;
  line-height: 30px;
  color: #444444;
}

.AppHeader-userInfo {
  display: none;
}

/* .AppHeader-userSignInAndSignUp {
    display: none;
} */
</style>
