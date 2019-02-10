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
    'no-debugger': 1,
    'no-console': 1,
    'no-dupe-args': 1,
    'default-case': 1
  },
  parserOptions: {
    parser: 'babel-eslint',
  },
};
