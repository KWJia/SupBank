module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: ['plugin:vue/essential'],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'quotes': [1, 'single'],
    'no-debugger': true,
    'no-console': 1,
    'no-dupe-args': true,
    'no-dupe-key': true,
    'default-case': true
  },
  parserOptions: {
    parser: 'babel-eslint',
  },
};
