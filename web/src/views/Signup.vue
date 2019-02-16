<template>
  <div class="signup">
    <router-link class="homeEntry" to="/">Back Home</router-link>
    <main role="main">
      <div class="main-inner">
        <div class="signupCard card">
          <form action class="signupForm">
            <div class="formTitle">
              <span class="left">Create your Wallet</span>
              <span class="right">or
                <router-link class="signin" to="/signin">Sign In</router-link>
              </span>
            </div>
            <p class="desc">Sign up for a free wallet below</p>
            <div class="dividerRow"></div>
            <div class="email-container">
              <p class="title">Email Address</p>
              <input
                type="text"
                name="emailAddress"
                class="emailAddress input"
                v-model="emailAddress"
              >
            </div>
            <div class="password-container">
              <p class="title">Password</p>
              <input type="password" name="password" class="password input" v-model="password">
            </div>
            <div class="confirmPassword-container">
              <p class="title">Confirm Password</p>
              <input
                type="password"
                name="confirmPassword"
                class="confirmPassword input"
                v-model="confirmPassword"
              >
            </div>
            <input type="button" class="signup-btn" v-on:click="signup" value="Sign Up">
          </form>
        </div>
      </div>
    </main>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class Signup extends Vue {
  private data() {
    return {
      emailAddress: '',
      password: '',
      confirmPassword: '',
    };
  }
  private signup(): void {
    if (!this.checkEmail(this.$data.emailAddress)) {
      alert('email error');
    } else if (!this.checkPassword(this.$data.password)) {
      alert('password error');
    } else if (this.$data.password !== this.$data.confirmPassword) {
      alert('pw != cpw');
    } else {
      this.requestSignup(this.$data.emailAddress, this.$data.password);
    }
  }
  private requestSignup(e: string, pw: string): void {
    const xmlhttp = new XMLHttpRequest();

    xmlhttp.open('POST', '//localhost:3000/api/sign_up', true);
    xmlhttp.setRequestHeader('Content-type', 'application/json');
    xmlhttp.send(JSON.stringify({
      data: {
        emailaddress: e,
        password: pw,
      },
    }));
    xmlhttp.onreadystatechange = (): void => {
      if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        const response = JSON.parse(xmlhttp.responseText);

        if (response.ack === 'success') {
          alert(response.data.walletid + '\n' + response.data.walletaddress);
        } else {
          alert(response.data.msg);
        }
      }
    };
  }
  private checkEmail(text: string): boolean {
    const reg = new RegExp('^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$');
    let res: boolean = false;

    if (reg.test(text) && text.length >= 5) {
      res = true;
    }

    return res;
  }
  private checkPassword(pw: string): boolean {
    const reg = new RegExp('^(?![^a-zA-Z]+$)(?!D+$)');
    let res: boolean = false;

    if (reg.test(pw) && pw.length >= 6) {
      res = true;
    }

    return res;
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

.formTitle .signin {
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

.signupCard .title {
  font-weight: 600;
  font-size: 14px;
  text-transform: none;
  font-style: normal;
  color: #545456;
  cursor: inherit;
  margin-bottom: 5px;
  margin-top: 12px;
}

.signupCard .input {
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

.signup-btn {
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
</style>
