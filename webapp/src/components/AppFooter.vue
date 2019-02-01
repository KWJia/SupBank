<template>
  <div class="AppFooter">
    <footer role="footer">
      <div class="AppFooter-copyright">
        ©
        <span itemprop="AppFooter-copyrightYear">2019</span>
        <i class="Divider"></i>
        <span class="AppFooter-authorName">SupBank</span>
        <img
          class="Icon Icon-location"
          src="../assets/img/icon-location-64x64.png"
          alt="location icon"
        >
        <span class="AppFooter-locationText">Tianjin - China</span>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'AppFooter',
};
</script>

<style lang="scss" scoped>
.AppFooter {
  text-align: center;
  margin-top: 38px;
  margin-bottom: 74px;
}

.AppFooter-copyright {
  font-size: 12px;
}

.AppFooter-copyright .Divider {
  height: 8px;
  background: #444444;
}

.Icon-location {
  opacity: 1;
  height: 10px;
  width: 10px;
  vertical-align: -1px;
  margin-right: 6px;
  margin-left: 22px;
}
</style>
