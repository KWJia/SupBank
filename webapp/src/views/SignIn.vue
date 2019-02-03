<template>
  <div class="SignIn">
    <main class="AppMain" role="main">
      <div class="AppMain-inner">
        <div class="AppMain-signInCard Card">
          <form class="SignInCard-form" action>
            <div class="SignInCard-title">
              <span class="SignInCard-titleTextLeft">Welcome Back!</span>
              <span class="SignInCard-titleTextRight">or
                <router-link to="/signup">Sign Up</router-link>
              </span>
            </div>
            <div class="SignInCard-titleDesc">
              <span class="SignInCard-titleDescText">Sign in to your wallet below</span>
            </div>
            <div class="DividerRow"></div>
            <div class="SignInCard-account">
              <p class="SignInCard-accountTitle">Wallet ID</p>
              <input type="text" name id>
              <p
                class="SignInCard-accountDesc"
              >Find the SignIn link in your email, e.g. blockchain.info/wallet/1111-222-333... The series of numbers and dashes at the end of the link is your Wallet ID.</p>
            </div>
            <div class="SignInCard-password">
              <p class="SignInCard-passwordTitle">Password</p>
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
  width: 580px;
  padding: 0 16px;
  margin: 14px auto;
}

.AppMain-signInCard {
  height: 600px;
  margin-bottom: 15px;
  text-align: center;
}
</style>
