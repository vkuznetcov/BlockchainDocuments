let transactionHash = ''

async function processFile() {
    if (fileInput.files.length) {
        const file = fileInput.files[0];
        const reader = new FileReader();
        reader.readAsArrayBuffer(file);

        const buffer = await new Promise((resolve, reject) => {
            reader.addEventListener('load', function () {
                resolve(reader.result);
            });
            reader.addEventListener('error', function () {
                reject(reader.error);
            });
        });
        const hex = web3.utils.soliditySha3(buffer);
        const metadata = file.name + ':' + file.type + ':' + file.size;
        return [hex, metadata];
    } else {
        throw new Error('No file selected');
    }
}

async function sendTransaction(data) {
    const gasPrice = await web3.eth.getGasPrice();
    const accountAddress = (await web3.eth.getAccounts())[0];

    console.log('Current gas price: ', web3.utils.fromWei(gasPrice, 'gwei'), ' GWei')
    console.log('Current commission:', web3.utils.fromWei(gasPrice, 'ether'), ' ETH')

    const transaction = {
        from: accountAddress,
        to: docConsts.CONTRACT_ADDRESS,
        gasPrice: gasPrice,
        gasLimit: metaMaskConsts.GAS_LIMIT,
        value: 0x00,
        data: data
    }

    const signedTx = await web3.eth.accounts.signTransaction(transaction, metaMaskConsts.METAMASK_PRIVATE_KEY)
    await web3.eth.sendSignedTransaction(signedTx.rawTransaction, function (error, hash) {
            if (!error) {
                console.log("Transaction sent!", hash);
                const interval = setInterval(function () {
                    console.log("Attempting to get transaction receipt...");

                    transactionHash = hash
                    document.getElementById("info").innerHTML
                        = 'Транзакция еще выполняется <br/> <a href="https://sepolia.etherscan.io/tx/' + hash + '" target="_blank" rel="noopener noreferrer">Показать на etherscan<a/>';
                    document.getElementById("info").style.display = "block";
                    document.getElementById("info").style.backgroundColor = "white";

                    web3.eth.getTransactionReceipt(hash, function (err, rec) {
                        if (rec) {
                            console.log(rec);
                            if (rec.status)
                                document.getElementById("info").innerHTML = "Транзакция выполнена успешно";
                            else{
                                document.getElementById("info").innerHTML
                                    = 'Транзакция не выполнена <br/> <a href="https://sepolia.etherscan.io/tx/' + hash + '" target="_blank" rel="noopener noreferrer">Показать на etherscan<a/>';
                                document.getElementById("info").style.display = "block";
                                document.getElementById("info").style.backgroundColor = "rgba(245, 112, 112, 0.12)";
                            }
                            clearInterval(interval);
                        }
                    });
                }, 1000);
            } else {
                console.log("Something went wrong while submitting your transaction:", error);
            }
        }
    )
}

async function addDocument() {
    try {
        const [hex, metadata] = await processFile();
        const data = new web3.eth.Contract(docConsts.CONTRACT_ABI, docConsts.CONTRACT_ADDRESS).methods
            .addDocument(hex, metadata, userId).encodeABI();
        await sendTransaction(data);
    } catch (error) {
        console.error(error)
    }
}

async function deleteDocument() {
    try {
        const [hex, metadata] = await processFile();
        const data = new web3.eth.Contract(docConsts.CONTRACT_ABI, docConsts.CONTRACT_ADDRESS).methods
            .deleteDocument(hex, userId).encodeABI();
        await sendTransaction(data);
    } catch (error) {
        console.error(error)
    }
}

async function verifyDocument() {
    try {
        const [hex, metadata] = await processFile();
        const contract = new web3.eth.Contract(docConsts.CONTRACT_ABI, docConsts.CONTRACT_ADDRESS);
        contract.methods.verifyDocument(hex, metadata).call().then(result => {
                console.log(result)
                document.getElementById("info").style.display = "block";
                result === true ? (
                    document.getElementById('info').innerHTML = 'Ваш файл прошел проверку'
                ) : (
                    document.getElementById('info').innerHTML = 'Ваш файл не прошел проверку'
                )
            }
        )
    } catch (error) {
        console.error(error)
    }
}

async function getDocData() {
    const [hex, metadata] = await processFile()
    let info = document.getElementById("info")
    info.innerHTML = "Хэш: " + hex.substring(0, 13) + "... <br/> Метаданные: " + metadata
    info.style.display = "block"
    console.log(hex)
    console.log(metadata)
}