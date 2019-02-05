<template>
  <div class="SignUp">
    <main class="AppMain" role="main">
      <div class="AppMain-inner">
        <div class="AppMain-signUpCard Card">
          <form class="SignUpCard-form" action>
            <div class="SignUpCard-title">
              <span class="SignUpCard-titleTextLeft">Create your Wallet</span>
              <span class="SignUpCard-titleTextRight">or
                <router-link class="SignUpCard-signInLink" to="/signin">Sign In</router-link>
              </span>
            </div>
            <div class="SignUpCard-titleDesc">
              <span class="SignUpCard-titleDescText">Sign up for a free wallet below</span>
            </div>
            <div class="DividerRow"></div>
            <div class="SignUpCard-account">
              <p class="SignUpCardTitle">Email</p>
              <input type="text" name id>
            </div>
            <div class="SignUpCard-password">
              <p class="SignUpCardTitle">Password</p>
              <input type="text" name id>
            </div>
            <div class="SignUpCard-confirmPassword">
              <p class="SignUpCardTitle">Confirm Password</p>
              <input type="text" name id>
            </div>
            <div class="SignUpCard-continue">
              <button class="SignUpCard-continueBtn">Continue</button>
            </div>
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'SignUp',
};
</script>

<style lang="scss" scoped>
.AppMain-inner {
  position: relative;
  width: 550px;
  padding: 0 16px;
  margin: 14px auto;
}

.AppMain-signUpCard {
  margin-top: 108px;
  height: 420px;
  margin-bottom: 15px;
  text-align: left;
  white-space: normal;

  .SignUpCardTitle {
    font-weight: 600;
    font-size: 14px;
    text-transform: none;
    font-style: normal;
    color: rgb(84, 84, 86);
    cursor: inherit;
    margin-bottom: 5px;
    margin-top: 12px;
  }

  input {
    display: block;
    width: 100%;
    height: 38px;
    padding: 6px 12px;
    box-sizing: border-box;
    font-size: 14px;
    font-weight: 300;
    color: rgb(84, 84, 86);
    background-color: rgb(255, 255, 255);
    font-family: 'Montserrat', Helvetica, sans-serif;
    background-image: none;
    outline-width: 0px;
    -moz-user-select: text;
    border: 1px solid rgb(204, 204, 204);
  }
}

.SignUpCard-title {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.SignUpCard-titleTextLeft {
  font-weight: 400;
  font-size: 24px;
  text-transform: capitalize;
  font-style: normal;
  color: rgb(84, 84, 86);
  cursor: inherit;
}

.SignUpCard-titleTextRight {
  font-size: 13px;
  font-weight: 400;
  text-transform: none;
  font-style: normal;
  color: rgb(84, 84, 86);
  cursor: inherit;

  .SignUpCard-signInLink {
    font-size: 13px;
    font-weight: 500;
    color: rgb(16, 173, 228);
    text-transform: none;
    cursor: pointer;
  }
}

.SignUpCard-titleDescText {
  font-weight: 400;
  font-size: 14px;
  text-transform: none;
  font-style: normal;
  color: rgb(84, 84, 86);
  cursor: inherit;
}

.SignUpCard-accountDesc {
  font-weight: 400;
  font-size: 12px;
  text-transform: none;
  font-style: normal;
  color: rgb(153, 155, 158);
  cursor: inherit;
  margin-top: 3px;
  margin-bottom: 10px;
}

.SignUpCard-continueBtn {
  width: 100%;
  min-width: 140px;
  height: 40px;
  margin-top: 26px;
  padding: 10px 15px;
  letter-spacing: normal;
  white-space: nowrap;
  line-height: 1;
  text-transform: none;
  font-size: 15px;
  font-weight: 500;
  color: rgb(255, 255, 255);
  opacity: 0.7;
  background-color: rgb(16, 173, 228);
  border-radius: 3px;
  border-style: solid;
  border-width: 1px;
  border-color: rgb(16, 173, 228);
}
</style>
