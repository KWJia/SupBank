var jwt = require('jsonwebtoken');

var response = {
  ack: '',
  data: {
    msg: '',
    pages: 0,
    resultList: []
  }
}

function search(req, res) {
  var query_key = req.query.k;
  var query_token = req.query.token;

  var decode = jwt.verify(query_token, 'shhhhh');

  console.log(decode.foo);
}

module.exports = search;
