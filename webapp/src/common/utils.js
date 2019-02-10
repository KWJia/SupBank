module.exports = {
  getStorage: (item) => {
    return localStorage.getItem(item);
  },
  setStorage: (item, value) => {
    localStorage.setItem(item, value);
  }
}