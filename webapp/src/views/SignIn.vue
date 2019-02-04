<template>
  <div class="SignIn">
    <main class="AppMain" role="main">
      <div class="AppMain-inner">
        <div class="AppMain-signInCard Card">
          <form class="SignInCard-form" action>
            <div class="SignInCard-title">
              <span class="SignInCard-titleTextLeft">Welcome Back!</span>
              <span class="SignInCard-titleTextRight">or
                <router-link class="SignInCard-signInLink" to="/signup">Sign Up</router-link>
              </span>
            </div>
            <div class="SignInCard-titleDesc">
              <span class="SignInCard-titleDescText">Sign in to your wallet below</span>
            </div>
            <div class="DividerRow"></div>
            <div class="SignInCard-account">
              <p class="SignInCardTitle">Wallet ID</p>
              <input type="text" name id>
              <p
                class="SignInCard-accountDesc"
              >Find the SignIn link in your email, e.g. blockchain.info/wallet/1111-222-333... The series of numbers and dashes at the end of the link is your Wallet ID.</p>
            </div>
            <div class="SignInCard-password">
              <p class="SignInCardTitle">Password</p>
              <input type="text" name id>
            </div>
            <div class="SignInCard-signIn">
              <button class="SignInCard-signInBtn">Sign In</button>
            </div>
            <div class="SignInCard-forgetPassword">
              Having some trouble?
              <button class="SignInCard-forgetPasswordBtn">Retrieve Password</button>
            </div>
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'SignIn',
};
</script>

<style lang="scss" scoped>
.AppMain-inner {
  position: relative;
  width: 550px;
  padding: 0 16px;
  margin: 14px auto;
}

.AppMain-signInCard {
  margin-top: 108px;
  height: 420px;
  margin-bottom: 15px;
  text-align: left;
  white-space: normal;

  .SignInCardTitle {
    font-weight: 600;
    font-size: 14px;
    text-transform: none;
    font-style: normal;
    color: rgb(84, 84, 86);
    cursor: inherit;
    margin-bottom: 5px;
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

.SignInCard-title {
  display: -webkit-flex; /* Safari */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.SignInCard-titleTextLeft {
  font-weight: 400;
  font-size: 24px;
  text-transform: capitalize;
  font-style: normal;
  color: rgb(84, 84, 86);
  cursor: inherit;
}

.SignInCard-titleTextRight {
  font-size: 13px;
  font-weight: 400;
  text-transform: none;
  font-style: normal;
  color: rgb(84, 84, 86);
  cursor: inherit;

  .SignInCard-signInLink {
    font-size: 13px;
    font-weight: 500;
    color: rgb(16, 173, 228);
    text-transform: none;
    cursor: pointer;
  }
}

.SignInCard-titleDescText {
  font-weight: 400;
  font-size: 14px;
  text-transform: none;
  font-style: normal;
  color: rgb(84, 84, 86);
  cursor: inherit;
}

.SignInCard-accountDesc {
  font-weight: 400;
  font-size: 12px;
  text-transform: none;
  font-style: normal;
  color: rgb(153, 155, 158);
  cursor: inherit;
  margin-top: 3px;
  margin-bottom: 10px;
}

.SignInCard-signInBtn {
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

.SignInCard-forgetPassword {
  margin-top: 20px;
  text-align: right;
  font-weight: 500;
  font-size: 13px;
  text-transform: none;
  font-style: normal;
  color: rgb(84, 84, 86);
  cursor: inherit;

  .SignInCard-forgetPasswordBtn {
    font-size: 13px;
    font-weight: 500;
    color: rgb(16, 173, 228);
    text-transform: none;
    cursor: pointer;
    border: none;
    background: none;
  }
}
</style>
