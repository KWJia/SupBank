<template>
  <div class="signin">
    <router-link class="homeEntry" to="/">Back Home</router-link>
    <main role="main">
      <div class="main-inner">
        <div class="signinCard card">
          <form action class="signinForm">
            <div class="formTitle">
              <span class="left">Welcome Back!</span>
              <span class="right">or
                <router-link class="signup" to="/signup">Sign Up</router-link>
              </span>
            </div>
            <p class="desc">Sign in to your wallet below</p>
            <div class="dividerRow"></div>
            <div class="walletID-container">
              <p class="title">Wallet ID</p>
              <input type="text" name="walletID" class="walletID input" v-model="walletID">
              <p
                class="desc"
              >Find the SignIn link in your email, e.g. blockchain.info/wallet/1111-222-333... The series of numbers and dashes at the end of the link is your Wallet ID.</p>
            </div>
            <div class="password-container">
              <p class="title">Password</p>
              <input type="password" name="password" class="password input" v-model="password">
            </div>
            <input type="button" class="signin-btn" value="Sign In" v-on:click="signin">
          </form>
          <div class="forgetPassword">
            Having some trouble?
            <button class="forgetPassword-btn">Retrieve Password</button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Signin extends Vue {
  private data() {
    return {
      walletID: '',
      password: '',
    };
  }
  private signin(): void {
    if (this.$data.walletID.length <= 0) {
      alert('walletID error');
    } else if (this.$data.password.length < 6) {
      alert('password error');
    } else {
      this.requestSignin(this.$data.walletID, this.$data.password);
    }
  }
  private requestSignin(id: string, pw: string): void {
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open('POST', '//localhost:3000/api/sign_in', true);
    xmlhttp.setRequestHeader('Content-type', 'application/json');
    xmlhttp.send(JSON.stringify({
      data: {
        walletid: id,
        password: pw,
      },
    }));
    xmlhttp.onreadystatechange = (): void => {
      if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        const response = JSON.parse(xmlhttp.responseText);

        if (response.ack === 'success') {
          alert(response.data.token + '\n' + response.data.walletaddress);
        } else {
          alert(response.data.msg);
        }
      }
    };
  }
}
</script>

<style scoped>
.homeEntry {
  position: fixed;
  top: 12px;
  left: 12px;
}

.main-inner {
  position: relative;
  width: 550px;
  padding: 0 16px;
  margin: 100px auto;
}

.formTitle {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: justify;
  -ms-flex-pack: justify;
  justify-content: space-between;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.formTitle .left {
  font-weight: 400;
  font-size: 24px;
  text-transform: capitalize;
  font-style: normal;
  color: #545456;
  cursor: inherit;
}

.formTitle .right {
  font-size: 13px;
  font-weight: 400;
  text-transform: none;
  font-style: normal;
  color: #545456;
  cursor: inherit;
}

.formTitle .signup {
  font-size: 13px;
  font-weight: 500;
  color: #10ade4;
  text-transform: none;
  cursor: pointer;
}

.desc {
  font-weight: 400;
  font-size: 14px;
  text-transform: none;
  font-style: normal;
  color: #545456;
  cursor: inherit;
}

.signinCard .title {
  font-weight: 600;
  font-size: 14px;
  text-transform: none;
  font-style: normal;
  color: #545456;
  cursor: inherit;
  margin-bottom: 5px;
}

.signinCard .input {
  display: block;
  width: 100%;
  height: 38px;
  padding: 6px 12px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  font-size: 14px;
  font-weight: 300;
  color: #545456;
  background-color: white;
  font-family: 'Montserrat', Helvetica, sans-serif;
  background-image: none;
  outline-width: 0px;
  -moz-user-select: text;
  border: 1px solid #cccccc;
}

.walletID-container .desc {
  font-weight: 400;
  font-size: 12px;
  text-transform: none;
  font-style: normal;
  color: #999b9e;
  cursor: inherit;
  margin-top: 3px;
  margin-bottom: 10px;
}

.signin-btn {
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
  color: white;
  opacity: 0.7;
  background-color: #10ade4;
  border-radius: 3px;
  border-style: solid;
  border-width: 1px;
  border-color: #10ade4;
  cursor: pointer;
}

.forgetPassword {
  margin-top: 20px;
  text-align: right;
  font-weight: 500;
  font-size: 13px;
  text-transform: none;
  font-style: normal;
  color: #545456;
  cursor: inherit;
}

.forgetPassword-btn {
  font-size: 13px;
  font-weight: 500;
  color: #10ade4;
  text-transform: none;
  cursor: pointer;
  border: none;
  background: none;
}
</style>
