var express = require('express');
var router = express.Router();

var router_list = [
  {
    options: 'post',
    url: '/signin',
    controller: '../controller/signin',
  },
  {
    options: 'post',
    url: '/signup',
    controller: '../controller/signup',
  },
  {
    options: 'post',
    url: '/search',
    controller: '../controller/search',
  },
  {
    options: 'get',
    url: '/get_some_tx',
    controller: '../controller/get_some_tx',
  },
];

var routes = router_list.map(v => {
  router[v.options](v.url, require(v.controller));
  return router;
});

module.exports = routes;
