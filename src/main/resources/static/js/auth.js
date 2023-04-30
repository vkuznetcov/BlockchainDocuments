let auth = false

function setAuth(){
    auth = true;
    localStorage.setItem("auth", auth);
}

async function addUser(){

    let login = document.getElementById("login").value;
    let password = document.getElementById("password").value;
    let authKey = document.getElementById("authKey").value;

    const gasPrice = await web3.eth.getGasPrice();
    const accountAddress = (await web3.eth.getAccounts())[0];
    const contract = new web3.eth.Contract(authConsts.CONTRACT_ABI, authConsts.CONTRACT_ADDRESS);

    console.log('Current gas price: ', web3.utils.fromWei(gasPrice, 'gwei'), ' GWei')
    console.log('Current commission:', web3.utils.fromWei(gasPrice, 'ether'), ' ETH')


    const transaction = {
        from: accountAddress,
        to: authConsts.CONTRACT_ADDRESS,
        gasPrice: gasPrice,
        gasLimit: metaMaskConsts.GAS_LIMIT,
        value: 0x00,
        data: contract.methods.addUser(login, password, authKey).encodeABI()
    }

    const signedTx = await web3.eth.accounts.signTransaction(transaction, metaMaskConsts.METAMASK_PRIVATE_KEY)
    await web3.eth.sendSignedTransaction(signedTx.rawTransaction)
        .on('transactionHash', console.log)
        .on('receipt', function(receipt) {
            console.log(receipt);
        })
        .on('error', function(error, receipt) {
            console.error(error);
        });

    const userId = await contract.methods.getUserId(login, password).call();
    console.log("User ID:", userId);
    addUserId(userId)
}

function addUserId(userId){
    localStorage.setItem('userId', userId)
}

async function checkUser(){
    let login = document.getElementById("login").value;
    let password = document.getElementById("password").value;

    const contract = new web3.eth.Contract(authConsts.CONTRACT_ABI, authConsts.CONTRACT_ADDRESS);

    try {
        const userId = await contract.methods.getUserId(login, password).call();
        if(userId > 0) {
            addUserId(userId)
            setAuth()
            window.location.replace("http://localhost:8080/docblock");
        }
    }
    catch (e) {
        console.error(e)
    }
}

