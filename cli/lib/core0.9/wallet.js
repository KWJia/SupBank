const EC = require('elliptic').ec;

const read_db = require('../utils/read_db');
const write_db = require('../utils/write_db');

const wallets_db_name = 'wallets_db.json';

// Create and initialize EC context (better do it once and reuse it)
const ec = new EC('secp256k1');

class Wallet {
  constructor() {
  }

  create_wallet() {
    var self = this;
    // generator a key pair
    const key = ec.genKeyPair();
    self.public_key = key.getPublic('hex');
    self.private_key = key.getPrivate('hex');

    return read_db(wallets_db_name, 'utf-8')
      .then(data => {
        data.push({
          publickey: self.public_key,
          privatekey: self.private_key,
        });
        data = JSON.stringify(data);
        return write_db(wallets_db_name, data, 'utf-8');
      });
  }

  check_address(address) {
    return read_db(wallets_db_name, 'utf-8')
      .then(data => {
        if()
      });
  }
}

module.exports = Wallet;
