window.addEventListener('load', async function () {
    if (window.ethereum) {
        console.log('Web3 Detected! ' + window.ethereum.constructor.name)
        window.web3 = new Web3(window.ethereum);
        try {
            await window.ethereum.enable()
        } catch (error) {
            console.error(error)
        }

    } else {
        console.log('No Web3 Detected... using HTTP Provider')
        // window.web3 = new Web3(new Web3.providers.HttpProvider("https://goerli.infura.io/v3/304127ae5cee4e06b114cb3d57749335"))
        window.web3 = new Web3(new Web3.providers.HttpProvider("https://sepolia.infura.io/v3/304127ae5cee4e06b114cb3d57749335"))
    }
})

// const contractAddress = "0x4BA5F9B346bC1812d48b6E760e03007bef893eC4"
const contractAddress = "0xD52D17B654387cA7C3c1b57cb2e614Fe3C2aEdA4"
// const contractABI = [
//     {
//         "anonymous": false,
//         "inputs": [
//             {
//                 "indexed": true,
//                 "internalType": "bytes32",
//                 "name": "hash",
//                 "type": "bytes32"
//             },
//             {
//                 "indexed": false,
//                 "internalType": "string",
//                 "name": "metadata",
//                 "type": "string"
//             }
//         ],
//         "name": "DocumentAdded",
//         "type": "event"
//     },
//     {
//         "anonymous": false,
//         "inputs": [
//             {
//                 "indexed": true,
//                 "internalType": "bytes32",
//                 "name": "hash",
//                 "type": "bytes32"
//             }
//         ],
//         "name": "DocumentDeleted",
//         "type": "event"
//     },
//     {
//         "inputs": [
//             {
//                 "internalType": "bytes32",
//                 "name": "hash",
//                 "type": "bytes32"
//             },
//             {
//                 "internalType": "string",
//                 "name": "metadata",
//                 "type": "string"
//             }
//         ],
//         "name": "addDocument",
//         "outputs": [],
//         "stateMutability": "nonpayable",
//         "type": "function"
//     },
//     {
//         "inputs": [
//             {
//                 "internalType": "bytes32",
//                 "name": "hash",
//                 "type": "bytes32"
//             }
//         ],
//         "name": "deleteDocument",
//         "outputs": [],
//         "stateMutability": "nonpayable",
//         "type": "function"
//     },
//     {
//         "inputs": [
//             {
//                 "internalType": "bytes32",
//                 "name": "hash",
//                 "type": "bytes32"
//             },
//             {
//                 "internalType": "string",
//                 "name": "metadata",
//                 "type": "string"
//             }
//         ],
//         "name": "verifyDocument",
//         "outputs": [
//             {
//                 "internalType": "bool",
//                 "name": "",
//                 "type": "bool"
//             }
//         ],
//         "stateMutability": "view",
//         "type": "function"
//     }
// ]
const contractABI = [
    {
        "inputs": [
            {
                "internalType": "bytes32",
                "name": "hash",
                "type": "bytes32"
            },
            {
                "internalType": "string",
                "name": "metadata",
                "type": "string"
            }
        ],
        "name": "addDocument",
        "outputs": [],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "inputs": [
            {
                "internalType": "bytes32",
                "name": "hash",
                "type": "bytes32"
            }
        ],
        "name": "deleteDocument",
        "outputs": [],
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": true,
                "internalType": "bytes32",
                "name": "hash",
                "type": "bytes32"
            },
            {
                "indexed": false,
                "internalType": "string",
                "name": "metadata",
                "type": "string"
            }
        ],
        "name": "DocumentAdded",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": true,
                "internalType": "bytes32",
                "name": "hash",
                "type": "bytes32"
            }
        ],
        "name": "DocumentDeleted",
        "type": "event"
    },
    {
        "inputs": [
            {
                "internalType": "bytes32",
                "name": "hash",
                "type": "bytes32"
            },
            {
                "internalType": "string",
                "name": "metadata",
                "type": "string"
            }
        ],
        "name": "verifyDocument",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "stateMutability": "view",
        "type": "function"
    }
]
const METAMASK_PRIVATE_KEY = "318ef5d4db8f9fc118d4361f17b8dc9c8781b3f870c74aa8e102d4333f3064d4"
const gasLimit = 3000000

function processFile() {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        const file = document.getElementById('myFile')
        let hex;
        let metadata;

        if (file.files.length) {
            reader.onload = function (e) {
                const buffer = e.target.result;
                hex = web3.utils.soliditySha3(buffer);
                metadata = file.name + ':' + file.type + ':' + file.size
                resolve([hex, metadata])
            };

            reader.readAsBinaryString(file.files[0])
        } else {
            reject('No file selected')
        }
    })
}

async function addDocument() {
    try {
        const [hex, metadata] = await processFile();
        const gasPrice = await web3.eth.getGasPrice();
        const accountAddress = (await web3.eth.getAccounts())[0];
        const contract = new web3.eth.Contract(contractABI, contractAddress);

        console.log('Current gas price: ', web3.utils.fromWei(gasPrice, 'gwei'), ' GWei')
        console.log('Current commission:', web3.utils.fromWei(gasPrice, 'ether'), ' ETH')

        const transaction = {
            from: accountAddress,
            to: contractAddress,
            gasPrice: gasPrice,
            gasLimit: gasLimit,
            value: 0x00,
            data: contract.methods.addDocument(hex, metadata).encodeABI()
        }

        const signedTx = await web3.eth.accounts.signTransaction(transaction, METAMASK_PRIVATE_KEY)
        await web3.eth.sendSignedTransaction(signedTx.rawTransaction).on('transactionHash', console.log).on('error', console.error)

    } catch (error) {
        console.error(error)
    }
}

async function deleteDocument() {
    try {
        const [hex, metadata] = await processFile();
        const gasPrice = await web3.eth.getGasPrice();
        const accountAddress = (await web3.eth.getAccounts())[0];
        const contract = new web3.eth.Contract(contractABI, contractAddress);

        console.log('Current gas price: ', web3.utils.fromWei(gasPrice, 'gwei'), ' GWei')
        console.log('Current commission:', web3.utils.fromWei(gasPrice, 'ether'), ' ETH')

        const transaction = {
            from: accountAddress,
            to: contractAddress,
            gasPrice: gasPrice,
            gasLimit: gasLimit,
            value: 0x00,
            data: contract.methods.deleteDocument(hex).encodeABI()
        }

        const signedTx = await web3.eth.accounts.signTransaction(transaction, METAMASK_PRIVATE_KEY)
        await web3.eth.sendSignedTransaction(signedTx.rawTransaction).on('transactionHash', console.log).on('error', console.error)

    } catch (error) {
        console.error(error)
    }
}

async function verifyDocument() {
    try {
        const [hex, metadata] = await processFile();
        const contract = new web3.eth.Contract(contractABI, contractAddress);
        contract.methods.verifyDocument(hex, metadata).call().then(result => {
                console.log(result)
                result === true ? (
                    document.getElementById('verifyResult').innerHTML = 'Your document is verified'
                ) : (
                    document.getElementById('verifyResult').innerHTML = 'Your document is not verified'
                )
            }
        )
    } catch (error) {
        console.error(error)
    }
}

document.getElementById("addDocument").onclick = async function () {
    await addDocument()
}

document.getElementById("verifyDocument").onclick = async function () {
    await verifyDocument()
}

document.getElementById("deleteDocument").onclick = async function () {
    await deleteDocument()
}

document.getElementById("processDocument").onclick = async function () {
    const [hex, metadata] = await processFile()
    console.log(metadata)
}

document.getElementById("myFile").onchange = async function () {
    await verifyDocument()
}