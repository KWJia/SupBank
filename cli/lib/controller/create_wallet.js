const EC = require('elliptic').ec;
const read_db = require('../utils/read_db');
const write_db = require('../utils/write_db');

// Create and initialize EC context (better do it once and reuse it)
const ec = new EC('secp256k1');

const wallets_db_name = 'wallets_db.json';

/**
 * @description create a pair key as a wallet and store in wallets_db
 */
function create_wallet() {
  // generator a key pair
  const key = ec.genKeyPair();
  const public_key = key.getPublic('hex');
  const private_key = key.getPrivate('hex');

  read_db(wallets_db_name, 'utf-8')
    .then(data => {
      data.push({
        publickey: public_key,
        privatekay: private_key,
      });
      data = JSON.stringify(data);
      write_db(wallets_db_name, data, 'utf-8')
        .then(() => {
          console.log('Create wallet success!\n');
          console.log(`this is your address (public key):\n${public_key}\n`);
          console.log(`this is your private key (keep this secret! To sign transactions):\n${private_key}`);
        })
        .catch(msg => {
          console.log(msg);
        });
    })
    .catch(msg => {
      console.log(msg);
    });
}

module.exports = create_wallet;
