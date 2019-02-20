const read_db = require('../utils/read_db');

const wallets_db_name = 'wallets_db.json';

/**
 * @description list all of the wallets' address in wallets_db
 */
function list_address() {
  read_db(wallets_db_name, 'utf-8')
    .then(data => {
      data.forEach((w, i) => {
        console.log(`wallet ${i+1} address:`);
        console.log(`${w.publickey}\n`);
      });
    })
    .catch(msg => {
      console.log(msg);
    })
}

module.exports = list_address;